package de.hu_berlin.ensureII.sre.parser.sretree.edit;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.RandomUtils;
import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;
import de.hu_berlin.ensureII.sre.parser.sretree.SREAction;
import de.hu_berlin.ensureII.sre.parser.sretree.SREChoice;
import de.hu_berlin.ensureII.sre.parser.sretree.SREKleene;
import de.hu_berlin.ensureII.sre.parser.sretree.SREPlusClosure;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETree;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode.Type;
import de.hu_berlin.ensureII.sre.parser.sretree.calculator.SRETreeCalculator;
public class SRETreeEdit implements ISRETreeEdit {
/*****************************************************************************
** Implement ISREEdit
*****************************************************************************/
 public enum Edits{
  addSubNode, insertSubNode, replaceNode, replaceSubNode, replaceSubTree, deleteSubTree,
  UpdateRate, UpdateLoopProbability, addLoop, removeLoop, addChoice
 }
 
 private int updateCalls=0;
 private int updateAllCalls=0;
 
    @Override
    public void addSubnode(SRETree tree, SRETreeNode toAdd, int parentId) {
       
        /****************************************************************************/
        if(tree == null) {
            throw new IllegalArgumentException("Tree must be non null.");
        }
        if(toAdd == null) {
            throw new IllegalArgumentException("Node to add must be non null.");
        }
        if(tree.getNodes().get(parentId) == null) {
            throw new IllegalArgumentException("No parent with id " + parentId + " exists.");
        }
        assert(tree.Invariant());
        /****************************************************************************/
       
        if((tree.getNode(parentId).getType() != SRETreeNode.Type.CHOICE) || (tree.getNode(parentId).getType() != SRETreeNode.Type.CONCAT)) {
            return;
        }else {
            tree.appendSubnode(toAdd, parentId);
        }
        updateTree(tree, toAdd);
       
        /****************************************************************************/
        assert(tree.getNodes().get(toAdd.getId()).equals(toAdd));
        assert(tree.Invariant());
        /****************************************************************************/
    }
    @Override
    public void insertSubnode(SRETree tree, SRETreeNode toInsert, int parentId, int insertPos) {
       
        /****************************************************************************/
        if(tree == null) {
            throw new IllegalArgumentException("Tree must be non null.");
        }
        if(toInsert == null) {
            throw new IllegalArgumentException("Node to add must be non null.");
        }
        if(tree.getNodes().get(parentId) == null) {
            throw new IllegalArgumentException("No parent with id " + parentId + " exists.");
        }
        assert(tree.Invariant());
        /****************************************************************************/
        if((tree.getNode(parentId).getType() != SRETreeNode.Type.CHOICE) || (tree.getNode(parentId).getType() != SRETreeNode.Type.CONCAT)) {
            return;
        }else {
            tree.appendSubnode(toInsert, parentId);
        }
        updateTree(tree, toInsert);
       
        /****************************************************************************/
        assert(tree.getNodes().get(toInsert.getId()).equals(toInsert));
        assert(tree.Invariant());
        /****************************************************************************/
       
    }
    @Override
    public void replaceNode(SRETree tree, int toReplaceId, SRETreeNode newNode) {
        tree.replaceNode(toReplaceId, newNode);
        updateTree(tree, newNode);
    }
   
    @Override
    public void replaceSubtree(SRETree tree, int toReplaceId, SRETreeNode subtreeRoot) {
        tree.replaceSubtree(toReplaceId, subtreeRoot);
        updateTree(tree, subtreeRoot);
    }
   
    @Override
    public void deleteSubtree(SRETree tree, int rootToDeleteId) {
       
        /****************************************************************************/
        assert(tree.Invariant());
        /****************************************************************************/
       
        if(tree.nodeExists(rootToDeleteId) == false) {
            return;
        }
       
        int correctNodeId = -1;
       
        correctNodeId = tree.deleteSubtree(rootToDeleteId);
       
        if(correctNodeId == -1) {
            // no nodes left
            assert(tree.getNodes().isEmpty());
        }else {
            updateTree(tree, tree.getNode(correctNodeId));
        }
       
        /****************************************************************************/
        assert(tree.Invariant());
        /****************************************************************************/
       
    }
   
    @Override
    public void updateRate(SRETree tree, int id, int rate) {
       
        /****************************************************************************/
        if(tree == null) throw new IllegalArgumentException("Tree must be non null.");
        assert(tree.Invariant());
        /****************************************************************************/
       
        if(tree.nodeExists(id) == false) {
            return;
        }
       
        SRETreeNode node = tree.getNode(id);
        node.setRate(rate);
        if(node.getParent().getType() == SRETreeNode.Type.CHOICE) {
            ((SREChoice)node.getParent()).updateRate(node.getBirthOrder(), rate);
        }
        updateTree(tree, tree.getNode(id));
       
        /****************************************************************************/
        assert(tree.Invariant());
        /****************************************************************************/
       
    }
    @Override
    public void updateLoopProbability(SRETree tree, int id, double probability) {
       
        /****************************************************************************/
        if(tree == null) {
            throw new IllegalArgumentException("Tree must be non null.");
        }
        if(tree.getNodes().get(id) == null) {
            throw new IllegalArgumentException("No node with id " + id + " exists.");
        }
        if((tree.getNode(id).getType() != SRETreeNode.Type.KLEENE) && (tree.getNode(id).getType() != SRETreeNode.Type.CLOSURE)) {
            throw new IllegalArgumentException("node is not a loop node.");
        }
        if(1 <= probability || probability <= 0) {
            throw new IllegalArgumentException("Probability must be between 0 and 1.");
        }
        assert(tree.Invariant());
        /****************************************************************************/
       
        SRETreeNode loop = tree.getNode(id);
        if(loop.getType() == SRETreeNode.Type.KLEENE) {
            ((SREKleene) loop).setRepetionRate(probability);
        }else {
            ((SREPlusClosure) loop).setRepetionRate(probability);
        }
        updateTree(tree, tree.getNode(id));
       
        /****************************************************************************/
        assert(tree.Invariant());
        /****************************************************************************/
       
    }
   
    @Override
    public void addLoop(SRETree tree, int id, double probability) {
       
        /****************************************************************************/
        if(tree == null) {
            throw new IllegalArgumentException("Tree must be non null.");
        }
        if(tree.getNodes().get(id) == null) {
            throw new IllegalArgumentException("No node with id " + id + " exists.");
        }
        if((tree.getNode(id).getType() != SRETreeNode.Type.KLEENE) && (tree.getNode(id).getType() != SRETreeNode.Type.CLOSURE)) {
            throw new IllegalArgumentException("node is not a loop node.");
        }
        if(1 <= probability || probability <= 0) {
            throw new IllegalArgumentException("Probability must be between 0 and 1.");
        }
        assert(tree.Invariant());
        /****************************************************************************/
       
        tree.addLoop(id, probability);
        updateTree(tree, tree.getNode(id));
       
        /****************************************************************************/
        assert(tree.Invariant());
        /****************************************************************************/
       
    }
   
    @Override
    public void removeLoop(SRETree tree, int id) {
       
        /****************************************************************************/
        if(tree == null) {
            throw new IllegalArgumentException("Tree must be non null.");
        }
        if(tree.getNodes().get(id) == null) {
            throw new IllegalArgumentException("No node with id " + id + " exists.");
        }
        assert(tree.Invariant());
        /****************************************************************************/
       
        tree.removeLoop(id);
        updateTree(tree, tree.getNode(id));
       
        /****************************************************************************/
        assert(tree.Invariant());
        /****************************************************************************/
       
    }
   
    @Override
    public void addChoice(SRETree tree, int id, SRETreeNode choiceToAdd) {
       
        /****************************************************************************/
        if(tree == null) {
            throw new IllegalArgumentException("Tree must be non null.");
        }
        if(tree.getNodes().get(id) == null) {
            throw new IllegalArgumentException("No node with id " + id + " exists.");
        }
        if(choiceToAdd == null) {
            throw new IllegalArgumentException("choiceToAdd must be non null.");
        }
        assert(tree.Invariant());
        /****************************************************************************/
       
        tree.addChoice(id, choiceToAdd);
        updateTree(tree, tree.getNode(id));
       
        /****************************************************************************/
        assert(tree.Invariant());
        /****************************************************************************/
       
    }
    @Override
    public void updateTree(SRETree tree, SRETreeNode node) {
       
    	updateCalls++;
        /****************************************************************************/
        assert(tree.nodeExists(node.getId()));
        assert(tree.Invariant());
        /****************************************************************************/
       
        while (node !=null) {
            node.calculateNodeData(new SRETreeCalculator(tree.getRoot().getData().getSearchedMatching()));
            node = node.getParent();
		}
//        if(node.getParent() != null) {
//            updateTree(tree, node.getParent());
//        }
//       
        /****************************************************************************/
        assert(tree.Invariant());
        /****************************************************************************/
       
    }
    
    public void updateAll(SRETreeNode root) {
    	updateAllCalls++;
    	
    	 /****************************************************************************/
        assert((root!=null));
        /****************************************************************************/
        
//        Deque<SRETreeNode> s1 = new ArrayDeque<SRETreeNode>();
//        Deque<SRETreeNode> s2 = new ArrayDeque<SRETreeNode>();
//        
//
//        if (root == null) 
//            return; 
//        // push root to first stack 
//        s1.push(root); 
//        
//        while (!s1.isEmpty()) { 
//            // Pop an item from s1 and push it to s2 
//            SRETreeNode temp = s1.pop(); 
//            s2.push(temp); 
//  
//            // Push left and right children of 
//            // removed item to s1 
//        	List<SRETreeNode> children = root.getChildren();
//
//            for (Iterator<SRETreeNode> iterator = children.iterator(); iterator.hasNext();) {
//    			SRETreeNode child = (SRETreeNode) iterator.next();
//    			s1.push(child);
//    		}
//            
//        } 
//        while (!s2.isEmpty()) { 
//            SRETreeNode temp = s2.pop(); 
//            temp.calculateNodeData(new SRETreeCalculator(root.getData().getSearchedMatching()));
//        } 
//  
        //recursive method

        root.calculateNodeData(new SRETreeCalculator(root.getData().getSearchedMatching()));

    	List<SRETreeNode> children = root.getChildren();
    	for (Iterator<SRETreeNode> iterator = children.iterator(); iterator.hasNext();) {
			SRETreeNode child = (SRETreeNode) iterator.next();
			updateAll(child);
		}
    }
   
    public void runRandomChange(SRETree tree){
     
     List<Integer> exisitingNodes = new ArrayList<Integer>();
     exisitingNodes.addAll(tree.getNodes().keySet());
     int size = exisitingNodes.size();
     int nodeId = RandomUtils.nextInt(size/5, size/2);
     SRETreeNode node = tree.getNodes().get(exisitingNodes.get(nodeId));
     boolean check = RandomUtils.nextBoolean();
     
     SREAction newnode = new SREAction("a");
     SRENodeData data = newnode.calculateNodeData(new SRETreeCalculator(node.getData().getSearchedMatching()) );
     newnode.setData(data);
     
     if (node.getType() == Type.ACTION) node = node.getParent();
     
     switch (node.getType()) {
     case CHOICE: updateRate(tree, node.getId(), RandomUtils.nextInt(1, 100)); 
   break;
  case CLOSURE:
   break;
  case CONCAT: if (check == false )insertSubnode(tree, newnode, node.getId(), 0);
  			 else deleteSubtree(tree, nodeId);
   break;
  case KLEENE: updateLoopProbability(tree, node.getId(), RandomUtils.nextDouble(0.001, 0.005));
   break;
  default:
   break;
     }
     RandomUtils.nextInt(0, 10);
     
     
  
 }
	public int getUpdateCalls() {
		return updateCalls;
	}
	public void setUpdateCalls(int updateCalls) {
		this.updateCalls = updateCalls;
	}
	public int getUpdateAllCalls() {
		return updateAllCalls;
	}
	public void setUpdateAllCalls(int updateAllCalls) {
		this.updateAllCalls = updateAllCalls;
	}
   
    
/*****************************************************************************
** Private
*****************************************************************************/
   
   
}