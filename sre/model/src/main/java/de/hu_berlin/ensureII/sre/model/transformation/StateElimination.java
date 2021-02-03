package de.hu_berlin.ensureII.sre.model.transformation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hu_berlin.ensureII.sre.model.conversion.SreToEmf;
import de.hu_berlin.ensureII.sre.model.conversion.Tra2Emf;
import de.hu_berlin.ensureII.sre.model.sre.SRE;
import de.hu_berlin.ensureII.sre.parser.sretree.SREAction;
import de.hu_berlin.ensureII.sre.parser.sretree.SREChoice;
import de.hu_berlin.ensureII.sre.parser.sretree.SREConcat;
import de.hu_berlin.ensureII.sre.parser.sretree.SREKleene;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;
import de.hu_berlin.ensureII.sre.parser.sretree.Tuple;
import transitiongraph.State;
import transitiongraph.Transition;
import transitiongraph.TransitionGraph;
import transitiongraph.TransitiongraphFactory;

public class StateElimination {

    public static void main(String[] args) {
        File f = new File("src/main/resources/tra/leader/leader3_2.tra");
        TransitionGraph tg = Tra2Emf.tra2emf(f);
        //TransitionGraph tg = EmfStorage.loadFromXMI("src/main/resources/xmi/leader/leader3_4.xmi");
        StateElimination foo = new StateElimination(tg);
        foo.run();
        System.out.println(foo.getSRE());
        System.out.println("done");
        SRE sre = SreToEmf.convertSre(foo.getSRE());
        System.out.println(sre.getRoot().getSrcState() + "-" + sre.getRoot().getTgtState());
    }
    
/*****************************************************************************
** Constructors
*****************************************************************************/
    
    /**
     * Transform a transition graph into a stochastic regular expression
     * using state elimination.
     * 
     * @param tg
     */
    public StateElimination(TransitionGraph tg) {
        transitionGraph = tg;
        hundredPercents = new HashMap<Integer, Double>();
        transitionLabels = new HashMap<Transition, SRETreeNode>();
        eliminatedStates = new boolean[tg.getStates().size()+2]; //default false
    }
    
/*****************************************************************************
** Methods
*****************************************************************************/
    
    public void run() {
        uniforming(transitionGraph);
        hundredPercent();
        
        while(transitionGraph.getStates().size() > 2) {
            eliminateOneState();
        }
        
        assert(transitionGraph.getStates().size() == 2);
        assert(transitionGraph.getTransitions().size() == 1);
        assert((transitionGraph.getStates().get(0).isIsInitial() && transitionGraph.getStates().get(1).isIsFinal())
                || (transitionGraph.getStates().get(1).isIsInitial() && transitionGraph.getStates().get(0).isIsFinal()));
        
    }
    
    /**
     * Uniform a transition graph by transforming it into an equivalent one with
     * only one initial and one final state.
     * 
     * @param tg
     */
    private void uniforming(TransitionGraph tg) {
        
        List<State> initialStates = new ArrayList<State>();
        
        for(State state : tg.getStates()) {
            if(state.isIsInitial()) {
                initialStates.add(state);
            }
        }

        //create new start
        State newInitial = TransitiongraphFactory.eINSTANCE.createState();
        newInitial.setId(tg.getStates().size());
        newInitial.setIsInitial(true);
        newInitial.setIsFinal(false);
        tg.getStates().add(newInitial);

        //transition from new start to old starts
        for(State state : initialStates) {
            state.setIsInitial(false);
            Transition newTransition = TransitiongraphFactory.eINSTANCE.createTransition();
            newTransition.setLabel("");
            newTransition.setSource(newInitial);
            newTransition.setTarget(state);
            newTransition.setProbability(1.0 / (double)initialStates.size());
            //if(newTransition.getProbability() == 1.0) newTransition.setProbability(0); //if its 1 it gives wrong probabilities
            tg.getTransitions().add(newTransition);
        }
        
        List<State> finalStates = new ArrayList<State>();
        
        for(State state : tg.getStates()) {
            if(state.isIsFinal()) {
                finalStates.add(state);
            }
        }
        
        //create new final
        State newFinal = TransitiongraphFactory.eINSTANCE.createState();
        newFinal.setId(tg.getStates().size());
        newFinal.setIsInitial(false);
        newFinal.setIsFinal(true);
        tg.getStates().add(newFinal);

        //transition from old finals to new final
        for(State state : finalStates) {
            state.setIsFinal(false);
            Transition newTransition = TransitiongraphFactory.eINSTANCE.createTransition();
            newTransition.setLabel("");
            newTransition.setSource(state);
            newTransition.setTarget(newFinal);
            newTransition.setProbability(0);
            tg.getTransitions().add(newTransition);
        }
        
    }
    
    /**
     * Ignoring loops of a state, the remaining outgoing transitions make up
     * 100%. E.g. say we have a loop with prob. 0.5 and two other outgoing
     * transitions t1,t2 with prob. 0.3 and 0.2. Then ignoring the loop leads to
     * t1 with prob. 0.3/0.5 = 0.6 and t2 with prob. 0.2/0.5 = 0.4.
     * With that we have "t1 + t2 = 1".
     * For now we only save the probability of adding all outgoing trans. prob. without
     * loops. In the calculations later we will retrieve the value.
     */
    private void hundredPercent() {
        
        double hundredPercent;
        
        for(State state : transitionGraph.getStates()) {
            hundredPercent = 0;
            for(Transition transition : state.getOutgoing()) {
                if(!(transition.getSource() == transition.getTarget()) && !transition.getLabel().isEmpty()) {
                    hundredPercent += transition.getProbability();
                }
            }
            hundredPercents.put(state.getId(), hundredPercent);
        }

        //System.out.println("adjusted probabilities for each state : " + hundredPercents);
        
    }
    
    /**
     * Choose a state K that is not initial or final and eliminate it by
     * looking at all pairs (P,Q) with "P->K" and "K->Q" and creating
     * a new transition labeled "(PK*Q + PQ)".
     */
    private void eliminateOneState() {
        State K = selectNonFinalNonInitialState();
        
        assert(K != null);
        assert(eliminatedStates[K.getId()] == false);
        
        //System.out.println("Eliminating state " + K.getId());
        List<Transition> incomingK = K.getIncoming();
        List<Transition> outgoingK = K.getOutgoing();
        
        //kk* self loop
        SREKleene loopSRE = null;
        List<Transition> loops = selectLoops(incomingK);
        
        if(loops.size() > 0) {
            assert(loops.size() == 1); //assuming no multigraphs
            Transition loop = loops.get(0);
            if(loop.getLabel().isEmpty() == false) {
                
                loopSRE = new SREKleene(getSRETree(loop), loop.getProbability());
                loopSRE.setSourceState(K.getId());
                loopSRE.setTargetState(K.getId());
                
            }
            transitionGraph.getTransitions().remove(loop);
            incomingK.remove(loop);
            outgoingK.remove(loop);
            transitionLabels.remove(loop);
        }
        
        
        //Determine all P and Q
        List<State> statesP = new ArrayList<State>();
        List<State> statesQ = new ArrayList<State>();
        
        for(Transition transition : K.getIncoming()) {
            if(transition.getSource() != null && eliminatedStates[transition.getSource().getId()] == false) { //deleting states can make the source/target undefined
                statesP.add(transition.getSource());
            }
        }
        for(Transition transition : K.getOutgoing()) {
            if(transition.getTarget() != null && eliminatedStates[transition.getTarget().getId()] == false) {
                statesQ.add(transition.getTarget());
            }
        }
        
        double probabilityPQ, probabilityPK, probabilityKQ, probabilityPKQ;
        SRETreeNode srePQ, srePKQ, sreTotal;
        List<SRETreeNode> children;
        List<Tuple<SRETreeNode, Integer>> weightedChildren;
        
        for(State P : statesP) {
            for(State Q : statesQ) {
                
                //PQ
                srePQ = null;
                probabilityPQ = 0;
                assert(getTransitionsForTarget(P.getOutgoing(), Q).size() <= 1);
                for(Transition transitionPQ : getTransitionsForTarget(P.getOutgoing(), Q)) {
                    //System.out.println(P.getId() + " " + Q.getId() + " " + transitionPQ);
                    probabilityPQ += transitionPQ.getProbability();
                    
                    srePQ = getSRETree(transitionPQ);
                    srePQ.setSourceState(P.getId());
                    srePQ.setTargetState(Q.getId());
                    
                    transitionGraph.getTransitions().remove(transitionPQ);
                    P.getOutgoing().remove(transitionPQ);
                    Q.getIncoming().remove(transitionPQ);
                    transitionLabels.remove(transitionPQ);
                }
                
                //PKQ
                srePKQ = null;
                probabilityPK = 0;
                probabilityKQ = 0;
                probabilityPKQ = 0;
                for(Transition transitionPK : getTransitionsForTarget(P.getOutgoing(), K)) {
                    assert(getTransitionsForTarget(P.getOutgoing(), K).size() == 1); 
                    for(Transition transitionKQ : getTransitionsForSource(Q.getIncoming(), K)) {
                        assert(getTransitionsForSource(Q.getIncoming(), K).size() == 1);
                        
                        /*****/
                        children = new ArrayList<SRETreeNode>();
                        children.add(getSRETree(transitionPK));
                        if(loopSRE != null) {
                            children.add(loopSRE);
                        }
                        children.add(getSRETree(transitionKQ));
                        srePKQ = new SREConcat(children);
                        srePKQ.setSourceState(P.getId());
                        srePKQ.setTargetState(Q.getId());
                        /*****/
                        
                        probabilityPK = transitionPK.getProbability();
                        probabilityKQ = transitionKQ.getProbability() / hundredPercents.get(K.getId());

                        
                        if(!(probabilityPK == 0) && !(probabilityKQ == 0)){
                            probabilityPKQ = probabilityPK * probabilityKQ;
                        }else if(probabilityPK == 0){
                            probabilityPKQ = probabilityKQ;
                        }else{
                            probabilityPKQ = probabilityPK;
                        }
                        
                    }
                }

                //PQ + PKQ   
                sreTotal = null;
                /*****/
                if(srePQ != null && srePKQ != null) {
                    weightedChildren = new ArrayList<Tuple<SRETreeNode, Integer>>();
                    weightedChildren.add(new Tuple<SRETreeNode, Integer>(srePQ, new Double(probabilityPQ*1000).intValue()));
                    weightedChildren.add(new Tuple<SRETreeNode, Integer>(srePKQ, new Double(probabilityPKQ*1000).intValue()));
                    sreTotal = new SREChoice(weightedChildren);
                    sreTotal.setSourceState(P.getId());
                    sreTotal.setTargetState(Q.getId());
                }else if(srePQ != null && srePKQ == null) {
                    sreTotal = srePQ;
                }else {
                    sreTotal = srePKQ;
                }
                assert(sreTotal != null);
                /*****/

                //probability PQ + PKQ
                Transition newTransition = TransitiongraphFactory.eINSTANCE.createTransition();
                newTransition.setLabel(""); //empty label here since the label will be encoded in the sre tree
                newTransition.setProbability(probabilityPQ + probabilityPKQ); 
                newTransition.setSource(P); 
                newTransition.setTarget(Q);
                P.getOutgoing().add(newTransition);
                Q.getIncoming().add(newTransition); 
                transitionGraph.getTransitions().add(newTransition);
                transitionLabels.put(newTransition, sreTotal);
                
                //System.out.println("sre " + P.getId() + "->" + Q.getId() + " := " + srePQ);
                //System.out.println("sre " + P.getId() + "->" + K.getId() + "->" + Q.getId() + " := " + srePKQ);
                //System.out.println("sre total := " + sreTotal + "\n");
                
                //assert(probabilityPQ <= 1);
                //assert(probabilityPKQ <= 1);
                //assert(probabilityPQ + probabilityPKQ <= 1);
                
            }
        }
        
        transitionGraph.getTransitions().removeAll(K.getOutgoing());
        transitionGraph.getTransitions().removeAll(K.getIncoming());
        transitionGraph.getStates().remove(K);
        eliminatedStates[K.getId()] = true;
        
    }
    
/*****************************************************************************
** Helper Methods
*****************************************************************************/
    
    private SRETreeNode getSRETree(Transition transition) {
        
        SRETreeNode sre;
        
        if(transitionLabels.containsKey(transition)) {
            sre = transitionLabels.get(transition);
        }else {
            sre = new SREAction(transition.getLabel());
        }
        
        return sre;
    }
    
    private State selectNonFinalNonInitialState() {
        for(State state : transitionGraph.getStates()) {
            if((state.isIsFinal() || state.isIsInitial()) == false) {
                return state;
            }
        }
        return null;
    }
    
    private List<Transition> selectLoops(List<Transition> transitions) {
        List<Transition> loops = new ArrayList<Transition>();
        for(Transition transition : transitions) {
            if(transition.getSource() == transition.getTarget()) {
                loops.add(transition);
            }
        }
        return loops;
    }
    
    private List<Transition> getTransitionsForTarget(List<Transition> transitions, State target) {
        List<Transition> result = new ArrayList<Transition>();
        for(Transition transition : transitions) {
            if(transition.getTarget() == target) {
                result.add(transition);
            }
        }
        return result;
    }
    
    private List<Transition> getTransitionsForSource(List<Transition> transitions, State source) {
        List<Transition> result = new ArrayList<Transition>();
        for(Transition transition : transitions) {
            if(transition.getSource() == source) {
                result.add(transition);
            }
        }
        return result;
    }
   
/*****************************************************************************
** Setter and Getter
*****************************************************************************/
    
    public TransitionGraph getTransitionGraph() {
        return transitionGraph;
    }
    
    public SRETreeNode getSRE() {
        return transitionLabels.get(transitionGraph.getTransitions().get(0));
    }
    
/*****************************************************************************
** Fields
*****************************************************************************/
    
    private TransitionGraph transitionGraph;
    
    private HashMap<Integer, Double> hundredPercents;
    
    private HashMap<Transition, SRETreeNode> transitionLabels;
    
    private boolean eliminatedStates[];
    
}
