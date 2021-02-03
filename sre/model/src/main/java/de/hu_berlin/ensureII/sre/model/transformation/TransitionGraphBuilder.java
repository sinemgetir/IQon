package de.hu_berlin.ensureII.sre.model.transformation;

import java.util.HashMap;
import java.util.List;

import transitiongraph.State;
import transitiongraph.Transition;
import transitiongraph.TransitionGraph;
import transitiongraph.TransitiongraphFactory;

public class TransitionGraphBuilder {

    public static TransitionGraph build(List<State> states, 
            List<Integer> initialIds, List<Integer> finalIds, List<Transition> transitions) {
        
        TransitionGraph tg = TransitiongraphFactory.eINSTANCE.createTransitionGraph();
        HashMap<Integer, State> id2state = new HashMap<Integer, State>();
        
        State newState;
        Transition newTransition;
        
        for(State origState : states) {
            newState = TransitiongraphFactory.eINSTANCE.createState();
            newState.setId(origState.getId());
            newState.setIsInitial(false);
            newState.setIsFinal(false);
            id2state.put(newState.getId(), newState);
            tg.getStates().add(newState);
        }
        
        for(Transition origTransition : transitions) {
            newTransition = TransitiongraphFactory.eINSTANCE.createTransition();
            newTransition.setSource(id2state.get(origTransition.getSource().getId()));
            newTransition.setTarget(id2state.get(origTransition.getTarget().getId()));
            newTransition.setLabel(origTransition.getLabel());
            newTransition.setProbability(origTransition.getProbability());
            tg.getTransitions().add(newTransition);
        }
        
        for(Integer id : initialIds) {
            id2state.get(id).setIsInitial(true);
        }
        
        for(Integer id : finalIds) {
            id2state.get(id).setIsFinal(true);
        }
        
        return tg;
    }
    
}
