package de.hu_berlin.ensureII.evolution.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hu_berlin.ensureII.sre.model.matrix.TransitionMatrix;
import de.hu_berlin.ensureII.sre.parser.sretree.SREAction;
import de.hu_berlin.ensureII.sre.parser.sretree.SREKleene;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETree;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;
import de.hu_berlin.ensureII.sre.parser.sretree.Tuple;
import de.hu_berlin.ensureII.sre.parser.sretree.builder.SRETreeBuilder;
import de.hu_berlin.ensureII.sre.parser.sretree.edit.SRETreeEdit;

public class System {

/*****************************************************************************
** Constructors
*****************************************************************************/

    public System(SRETree sre, TransitionMatrix pa) {
        
        this.sre = sre;
        this.pa = pa;
        stateIdToSreIdMap = new HashMap<Integer, List<Integer>>();
        
        for(int i=0; i<pa.getNrOfStates(); i++) {
            stateIdToSreIdMap.put(i, new ArrayList<Integer>());
        }
        
        for(SRETreeNode node : sre.getNodes().values()) {
            stateIdToSreIdMap.get(node.getSourceState()).add(node.getId());
        }
        
    }
    
/*****************************************************************************
** Change
*****************************************************************************/
   
    public boolean addTask(int sourceId, String event2add1, String event2add2, int targetId) {
        
        /**SRE**/
        SRETreeNode event1 = builder.buildAction(event2add1);
        SRETreeNode event2 = builder.buildAction(event2add2);
        SRETreeNode concat = builder.buildConcat(event1, event2);
        
        for(Integer nodeId : stateIdToNodeIds(sourceId, targetId)) {
            SRETreeNode nodeToReplace = sre.getNode(nodeId);
            
            SRETreeNode concat2 = builder.buildConcat(concat, nodeToReplace.clone());
            
            edit.replaceSubtree(sre, nodeId, concat2);
        }
        
        /*******/
              
        
        /**PA***/
        pa.removeTransition(sourceId, targetId);
        pa.addState();
        int newState = pa.getNrOfStates() - 1;
        pa.addTransition(sourceId, newState, 1.0);
        pa.addTransition(newState, targetId, 1.0);
        /*******/
        
        
        return false;
    }
    
    public boolean addTask(int sourceId, String event2add, int targetId) {
        
        /**SRE**/
        SRETreeNode event = builder.buildAction(event2add);
        
        for(Integer nodeId : stateIdToNodeIds(sourceId, targetId)) {
            SRETreeNode nodeToReplace = sre.getNode(nodeId);
            
            SRETreeNode concat = builder.buildConcat(event, nodeToReplace.clone());
            
            edit.replaceSubtree(sre, nodeId, concat);
        }
        /*******/
        
        
        /**PA***/
        pa.addTransition(sourceId, targetId, 1.0);
        /*******/
        
        return false;
    }
    
    public boolean changePath(int sourceId,int oldTargetId,int newTargetId) {
        
        /**SRE**/

        /*******/
        
        
        /**PA***/
        double prob = pa.getTransitionProb(sourceId, oldTargetId);
        pa.removeTransition(sourceId, oldTargetId);
        pa.addTransition(sourceId, newTargetId, prob);
        /*******/
        
        return false;
    }
    
    public boolean updateDistribution(int sourceId, List<Tuple<Integer, Double>> targetIdsAndProbabilities) {
        
        /**SRE**/
        //nodes need to have the same choice parent
        for(Tuple<Integer, Double> tuple : targetIdsAndProbabilities) {
            for(Integer nodeId : stateIdToNodeIds(sourceId, tuple.x)) {
                edit.updateRate(sre, nodeId, (int) (tuple.y * 1000));
            }
        }
        /*******/
        
        /**PA***/
        ArrayList<Double> probabilities = new ArrayList<Double>(pa.getNrOfStates());
        for(int i=0; i<pa.getNrOfStates(); i++) {
            probabilities.add(0.0);
        }
        for(Tuple<Integer, Double> tuple : targetIdsAndProbabilities) {
            probabilities.set(tuple.x, tuple.y);
        }
        pa.updateDistribution(sourceId, probabilities);
        /*******/
        
        return false;
    }
    
    public boolean loop2choice(int stateId4loop,int targetId) {
        
        /**SRE**/
        for(Integer nodeId : stateIdToNodeIds(stateId4loop, stateId4loop)) {
            
            if(sre.getNode(nodeId).getType() != SRETreeNode.Type.KLEENE) {
                continue;
            }
            
            SRETreeNode child = sre.getNode(nodeId).getChildren().get(0);
            SRETreeNode event = new SREAction("s" + targetId);
            event.setRate((int) (((SREKleene)(sre.getNode(nodeId))).getRepetitionRate() * 100));
            sre.removeLoop(nodeId);
            edit.addChoice(sre, child.getId(), event);
        }
        /*******/
        
        /**PA***/
        double prob = pa.getTransitionProb(stateId4loop, stateId4loop);
        pa.removeTransition(stateId4loop, stateId4loop);
        pa.addTransition(stateId4loop, targetId, prob);
        /*******/
        
        return false;
    }
    
    public boolean choice2loop(int stateId4loop,int targetId) {
        
        /**SRE**/
        for(Integer nodeId : stateIdToNodeIds(stateId4loop, targetId)) {
            SRETreeNode node = sre.getNode(nodeId);
            SRETreeNode parent = node.getParent();
            
            int rateSum = 0;
            for(SRETreeNode child : parent.getChildren()) {
                rateSum += child.getRate();
            }
            edit.addLoop(sre, nodeId, node.getRate()/rateSum);
            edit.replaceNode(sre, parent.getId(), builder.buildConcat(new SREAction("dummy"), new SREAction("dummy")));
        }
        /*******/
        
        /**PA***/
        double prob = pa.getTransitionProb(stateId4loop, stateId4loop);
        pa.removeTransition(stateId4loop, stateId4loop);
        pa.addTransition(stateId4loop, stateId4loop, prob);
        /*******/
        
        return false;
    }
    
    public boolean addAlternation(int sourceId,int targetId2add, double prob) {
        
        /**SRE**/
        for(Integer nodeId : stateIdToNodeIds(sourceId, targetId2add)) {
            SRETreeNode event = new SREAction("s" + targetId2add);
            event.setRate((int) (prob * 100));
            edit.addChoice(sre, nodeId, event);
        }
        /*******/
        
        /**PA***/
        pa.addTransition(sourceId, targetId2add, prob); 
        /*******/
        
        return false;
    }
    
    public boolean removeAlternation(int sourceId,int targetId2remove) {
        
        /**SRE**/
        for(Integer nodeId : stateIdToNodeIds(sourceId, targetId2remove)) {
            edit.deleteSubtree(sre, nodeId);
        }
        /*******/
        
        /**PA***/
        pa.removeTransition(sourceId, targetId2remove);
        /*******/
        
        return false;
    }
    
    public boolean addSelfLoop(int stateId, double prob) {
        
        /**SRE**/
        for(Integer nodeId : stateIdToNodeIds(stateId, stateId)) {
            edit.addLoop(sre, nodeId, prob);
        }
        /*******/
        
        /**PA***/
        pa.addTransition(stateId, stateId, prob);
        /*******/
        
        return false;
    }
    public boolean removeSelfLoop(int stateId) {
        
        /**SRE**/
        for(Integer nodeId : stateIdToNodeIds(stateId, stateId)) {
            edit.removeLoop(sre, nodeId);
        }
        /*******/
        
        /**PA***/
        pa.removeTransition(stateId, stateId);
        /*******/
        
        return false;
    }
    
    public boolean reorderEvents(int sourceId1, String event1, int targetId1,int sourceId2, String event2, int targetId2) {
        
        /**SRE**/
        
        /*******/
        
        /**PA***/
        
        /*******/
        
        return false;
    }
    
    public boolean moveEvents(int sourceId, String action, int step) {
        
        /**SRE**/
        
        /*******/
        
        /**PA***/
        
        /*******/
        
        return false;
    }
    
    private List<Integer> stateIdToNodeIds(int src, int dst) {
        
        int nodeId = 1;
        List<Integer> nodeIds = new ArrayList<Integer>();
        
        List<Integer> nodes = stateIdToSreIdMap.get(src);
        for(int i=0; i<nodes.size(); i++) {
            nodeId = i;
            if(sre.getNode(i).getTargetState() == dst) {
                nodeIds.add(nodeId);
            }
        }
        
        return nodeIds;
    }
    
/*****************************************************************************
** Fields
*****************************************************************************/
    
    private TransitionMatrix pa;
    
    private SRETree sre;
    
    private HashMap<Integer, List<Integer>> stateIdToSreIdMap;
    
    private SRETreeBuilder builder = new SRETreeBuilder();
    
    private SRETreeEdit edit = new SRETreeEdit();
    
/*****************************************************************************
** Setter and Getter
*****************************************************************************/
    
    public TransitionMatrix getPa() {
        return pa;
    }

    public void setPa(TransitionMatrix pa) {
        this.pa = pa;
    }

    public SRETree getSre() {
        return sre;
    }

    public void setSre(SRETree sre) {
        this.sre = sre;
    }

    public HashMap<Integer, List<Integer>> getStateIdToSreIdMap() {
        return stateIdToSreIdMap;
    }

    public void setStateIdToSreIdMap(HashMap<Integer, List<Integer>> stateIdToSreIdMap) {
        this.stateIdToSreIdMap = stateIdToSreIdMap;
    }
    
}
