
package de.hu_berlin.ensureII.evolution.specification;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import de.hu_berlin.ensureII.sre.parser.sretree.SREAction;
import de.hu_berlin.ensureII.sre.parser.sretree.SREKleene;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETree;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;
import de.hu_berlin.ensureII.sre.parser.sretree.Tuple;
import de.hu_berlin.ensureII.sre.parser.sretree.builder.SRETreeBuilder;
import de.hu_berlin.ensureII.sre.parser.sretree.edit.SRETreeEdit;
public class ChangeSRE extends Change {
 /*****************************************************************************
 ** Constructors
 *****************************************************************************/
     public ChangeSRE(SRETree sre) {
        
         this.sre = sre;
 
        
     }
 @Override
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
       
        return false;
 }
 @Override
 public boolean addTask(int sourceId, String event2add, int targetId) {
    SRETreeNode event = builder.buildAction(event2add);
        
         for(Integer nodeId : stateIdToNodeIds(sourceId, targetId)) {
             SRETreeNode nodeToReplace = sre.getNode(nodeId);
            
             SRETreeNode concat = builder.buildConcat(event, nodeToReplace.clone());
            
             edit.replaceSubtree(sre, nodeId, concat);
         }
  return false;
 }
 @Override
 public boolean changePath(int sourceId, int oldTargetId, int newTargetId) {
  // TODO Auto-generated method stub
  return false;
 }
 @Override
 public boolean updateDistribution(int sourceId, List<Tuple<Integer, Double>> targetIdsAndProbabilities) {
  //nodes need to have the same choice parent
        for(Tuple<Integer, Double> tuple : targetIdsAndProbabilities) {
            for(Integer nodeId : stateIdToNodeIds(sourceId, tuple.x)) {
                edit.updateRate(sre, nodeId, (int) (tuple.y * 1000));
            }
        }
  return false;
 }
 @Override
 public boolean loop2choice(int stateId4loop, int targetId) {
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
  return false;
 }
 @Override
 public boolean choice2loop(int stateId4loop, int targetId) {
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
  return false;
 }
 @Override
 public boolean addAlternation(int sourceId, int targetId2add, double prob) {
   for(Integer nodeId : stateIdToNodeIds(sourceId, targetId2add)) {
             SRETreeNode event = new SREAction("s" + targetId2add);
             event.setRate((int) (prob * 100));
             edit.addChoice(sre, nodeId, event);
         }
  return false;
 }
 @Override
 public boolean removeAlternation(int sourceId, int targetId2remove) {
    for(Integer nodeId : stateIdToNodeIds(sourceId, targetId2remove)) {
             edit.deleteSubtree(sre, nodeId);
         }
  return false;
 }
 @Override
 public boolean addSelfLoop(int stateId, double prob) {
    for(Integer nodeId : stateIdToNodeIds(stateId, stateId)) {
             edit.addLoop(sre, nodeId, prob);
         }
  return false;
 }
 @Override
 public boolean removeSelfLoop(int stateId) {
   for(Integer nodeId : stateIdToNodeIds(stateId, stateId)) {
             edit.removeLoop(sre, nodeId);
         }
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
     private SRETree sre;
    
     private HashMap<Integer, List<Integer>> stateIdToSreIdMap;
    
     private SRETreeBuilder builder = new SRETreeBuilder();
    
     private SRETreeEdit edit = new SRETreeEdit();
    
     /*****************************************************************************
     ** Setter and Getter
     *****************************************************************************/
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
   @Override
   public ModelType getModelType() {
    return ModelType.SRE;
   }
       

}