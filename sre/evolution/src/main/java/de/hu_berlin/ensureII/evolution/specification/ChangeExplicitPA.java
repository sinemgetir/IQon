
package de.hu_berlin.ensureII.evolution.specification;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import de.hu_berlin.ensureII.sre.model.matrix.TransitionMatrix;
import de.hu_berlin.ensureII.sre.parser.sretree.Tuple;
public class ChangeExplicitPA extends Change {
 /*****************************************************************************
 ** Constructors
 *****************************************************************************/
     public ChangeExplicitPA(TransitionMatrix pa) {
        
         this.pa = pa;
         stateIdToSreIdMap = new HashMap<Integer, List<Integer>>();
        
         for(int i=0; i<pa.getNrOfStates(); i++) {
             stateIdToSreIdMap.put(i, new ArrayList<Integer>());
         }
        
     }
 @Override
 public boolean addTask(int sourceId, String event2add1, String event2add2, int targetId) {
  pa.removeTransition(sourceId, targetId);
        pa.addState();
        int newState = pa.getNrOfStates() - 1;
        pa.addTransition(sourceId, newState, 1.0);
        pa.addTransition(newState, targetId, 1.0);
  return false;
 }
 @Override
 public boolean addTask(int sourceId, String event2add, int targetId) {
   pa.addTransition(sourceId, targetId, 1.0);
  return false;
 }
 @Override
 public boolean changePath(int sourceId, int oldTargetId, int newTargetId) {
  double prob = pa.getTransitionProb(sourceId, oldTargetId);
        pa.removeTransition(sourceId, oldTargetId);
        pa.addTransition(sourceId, newTargetId, prob);
        return false;
 }
 @Override
 public boolean updateDistribution(int sourceId, List<Tuple<Integer, Double>> targetIdsAndProbabilities) {
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
 @Override
 public boolean loop2choice(int stateId4loop, int targetId) {
   double prob = pa.getTransitionProb(stateId4loop, stateId4loop);
         pa.removeTransition(stateId4loop, stateId4loop);
         pa.addTransition(stateId4loop, targetId, prob);
  return false;
 }
 @Override
 public boolean choice2loop(int stateId4loop, int targetId) {
  double prob = pa.getTransitionProb(stateId4loop, stateId4loop);
        pa.removeTransition(stateId4loop, stateId4loop);
        pa.addTransition(stateId4loop, stateId4loop, prob);
  return false;
 }
 @Override
 public boolean addAlternation(int sourceId, int targetId2add, double prob) {
   pa.addTransition(sourceId, targetId2add, prob);
   return false;
 }
 @Override
 public boolean removeAlternation(int sourceId, int targetId2remove) {
  pa.removeTransition(sourceId, targetId2remove);
  return false;
 }
 @Override
 public boolean addSelfLoop(int stateId, double prob) {
        pa.addTransition(stateId, stateId, prob);
  return false;
 }
 @Override
 public boolean removeSelfLoop(int stateId) {
  pa.removeTransition(stateId, stateId);
  return false;
 }
 @Override
 public boolean reorderEvents(int sourceId1, String event1, int targetId1, int sourceId2, String event2,
   int targetId2) {
  // TODO Auto-generated method stub
  return false;
 }
 @Override
 public boolean moveEvents(int sourceId, String action, int step) {
  // TODO Auto-generated method stub
  return false;
 }
 /*****************************************************************************
 ** Fields
 *****************************************************************************/
    
     private TransitionMatrix pa;
         
     private HashMap<Integer, List<Integer>> stateIdToSreIdMap;
         
     /*****************************************************************************
     ** Setter and Getter
     *****************************************************************************/
        
         public TransitionMatrix getPa() {
             return pa;
         }
         public void setPa(TransitionMatrix pa) {
             this.pa = pa;
         }
         public HashMap<Integer, List<Integer>> getStateIdToSreIdMap() {
             return stateIdToSreIdMap;
         }
         public void setStateIdToSreIdMap(HashMap<Integer, List<Integer>> stateIdToSreIdMap) {
             this.stateIdToSreIdMap = stateIdToSreIdMap;
         }
   @Override
   public ModelType getModelType() {
    return ModelType.PA;
   }
    
}