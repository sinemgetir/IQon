package de.hu_berlin.ensureII.sre.parser.sretree;


import java.util.HashMap;
import java.util.List;

import de.hu_berlin.ensureII.sre.parser.sretree.builder.SRETreeBuilder;

public class SRETree {

/*****************************************************************************
** Constructors
*****************************************************************************/
	
	public SRETree() {
	    this.nodes = new HashMap<Integer, SRETreeNode>();
	    maxId = 0;
	}
	    
	public SRETree(SRETreeNode root, HashMap<Integer, SRETreeNode> nodes) {
		this.nodes = nodes;
        this.root = root;
        maxId = 0;
    }
    
/*****************************************************************************
** Edit operation
*****************************************************************************/
	
	public void addNode(SRETreeNode treeNode) {
	    
	    /****************************************************************************/
	    if(treeNode == null) throw new IllegalArgumentException("Node must be non null.");
	    //assert(Invariant());
	    /****************************************************************************/
	    
	    int id = getUnusedId();
	    treeNode.setID(id);
        nodes.put(id, treeNode);
        maxId++;
        
        /****************************************************************************/
        assert(treeNode.getId() == id);
        assert(nodeExists(id) == true);
        assert(nodes.get(id).equals(treeNode));
        assert(nodeExists(getUnusedId()) == false);
        assert(Invariant());
        /****************************************************************************/
        
    }
	
	private void addNode_(SRETreeNode treeNode) {
	    
	    /****************************************************************************/
	    assert(treeNode != null);
	    /****************************************************************************/
	    
	    int id = getUnusedId();
        treeNode.setID(id);
        nodes.put(id, treeNode);
        maxId++;
        
        /****************************************************************************/
        assert(treeNode.getId() == id);
        assert(nodeExists(id) == true);
        assert(nodes.get(id).equals(treeNode));
        assert(nodeExists(getUnusedId()) == false);
        /****************************************************************************/
        
	}
	
	public void addSubtree(SRETreeNode treeNode) {
        
        /****************************************************************************/
	    if(treeNode == null) throw new IllegalArgumentException("Node must be non null.");
        assert(Invariant());
        /****************************************************************************/
        
        addSubtree_(treeNode);
        
        /****************************************************************************/
        assert(Invariant());
        /****************************************************************************/
        
    }
	
	private void addSubtree_(SRETreeNode treeNode) {
	    
	    /****************************************************************************/
        assert(treeNode != null);
        /****************************************************************************/

	    addNode_(treeNode);
        for(SRETreeNode child : treeNode.getChildren()) {
            addSubtree_(child);
        }
        
        /****************************************************************************/
        //
        /****************************************************************************/
	    
	}
	
	public void appendSubnode(SRETreeNode toAdd, int parentId) {
	    
	    /****************************************************************************/
        if(toAdd == null) throw new IllegalArgumentException("Node to add must be non null.");
        if(nodeExists(parentId) == false) throw new IllegalArgumentException("No parent with id " + parentId + " exists.");
        if(getNode(parentId).getType() != SRETreeNode.Type.CHOICE && getNode(parentId).getType() != SRETreeNode.Type.CONCAT) throw new IllegalArgumentException("Parent node is not of type choice or concat.");
        assert(Invariant());
        /****************************************************************************/
        
        SRETreeNode parent = getNode(parentId);
        parent.addChild(toAdd);
        addNode_(toAdd);
        
        /****************************************************************************/
        assert(getNode(toAdd.getId()).equals(toAdd));
        assert(Invariant());
        /****************************************************************************/
	    
	}
	
    public void insertSubnode(SRETreeNode toInsert, int parentId, int insertPos) {
        
        /****************************************************************************/
        if(toInsert == null) throw new IllegalArgumentException("Node to add must be non null.");
        if(nodeExists(parentId) == false) throw new IllegalArgumentException("No parent with id " + parentId + " exists.");
        if(getNode(parentId).getType() != SRETreeNode.Type.CHOICE && getNode(parentId).getType() != SRETreeNode.Type.CONCAT) throw new IllegalArgumentException("Parent node is not of type coice or concat.");
        assert(Invariant());
        /****************************************************************************/

        SRETreeNode parent =  getNode(parentId);
        parent.insertChild(toInsert, insertPos);
        addNode_(toInsert);
        
        /****************************************************************************/
        assert(getNode(toInsert.getId()).equals(toInsert));
        assert(Invariant());
        /****************************************************************************/
        
    }
	
	/**
	 * 
	 * @param nodeId
	 * @return
	 *     the id of the first node that has the correct structure again after
	 *     deleting a subtree from the tree
	 */
    public int deleteSubtree(int nodeId) {
        
        /****************************************************************************/
        if(nodeExists(nodeId) == false) throw new IllegalArgumentException("No node with this id.");
        assert(Invariant());
        /****************************************************************************/
        
    	SRETreeNode toRemove = nodes.get(nodeId);
    	SRETreeNode parent = toRemove.getParent();
    	for(SRETreeNode child : toRemove.getChildren()) {
    	    deleteSubtree_(child.getId());
    	}
    	deleteNode(nodeId);
    	
    	int correctNodeId = -1;
    	if(parent != null) {
    	    correctNodeId = correctStructure(parent.getId());
    	}
    	
    	/****************************************************************************/
    	assert(nodeExists(nodeId) == false);
    	assert(Invariant());
    	assert(correctNodeId == -1 || getNode(correctNodeId).hasCorrectStructure());
    	/****************************************************************************/
    	
    	return correctNodeId;
    	
	}
    
    private void deleteSubtree_(int nodeId) {
        
        /****************************************************************************/
        assert(nodeExists(nodeId) == true);
        /****************************************************************************/
        
        SRETreeNode toRemove = nodes.get(nodeId);
        List<SRETreeNode> children = toRemove.getChildren();
        
        for(SRETreeNode child : children) {
            deleteSubtree_(child.getId());
        }
        nodes.remove(nodeId);
        
        /****************************************************************************/
        assert(nodeExists(nodeId) == false);
        /****************************************************************************/
        
    }
	
    private void deleteNode(int nodeId) {
        
        /****************************************************************************/
        assert(nodeExists(nodeId) == true);
        /****************************************************************************/
        
        SRETreeNode toRemove = nodes.get(nodeId);
        SRETreeNode parent = toRemove.getParent();
        
        nodes.remove(nodeId);
        
        if(parent != null) {
            parent.removeChild(toRemove.getBirthOrder());
            toRemove.setParent(null);
        }
        
        /****************************************************************************/
        assert(nodeExists(nodeId) == false);
        /****************************************************************************/
        
    }
    
    public void replaceNode(int toReplaceId, SRETreeNode newNode) {
        
        /****************************************************************************/
        if(nodeExists(toReplaceId) == false) throw new IllegalArgumentException("No node with this id.");
        if(newNode == null) throw new IllegalArgumentException("Node must be non null.");
        assert(Invariant());
        /****************************************************************************/
        
        SRETreeNode toReplace = getNode(toReplaceId);
        SRETreeNode parent = toReplace.getParent();
        
        newNode.setChildren(toReplace.getChildren());
        
        if(parent != null) {
            parent.setChild(toReplace.getBirthOrder(), newNode);
            toReplace.setParent(null);
        }
        
        toReplace.setParent(null);
        newNode.setParent(parent);
        newNode.setID(toReplaceId);
        newNode.setSourceState(toReplace.getSourceState());
        newNode.setTargetState(toReplace.getTargetState());
        nodes.put(toReplaceId, newNode);
        
        /****************************************************************************/
        assert(nodeExists(toReplaceId) == true);
        assert(Invariant());
        /****************************************************************************/
        
    }
    
    public void replaceSubtree(int toReplaceId, SRETreeNode newNode) {
        
        /****************************************************************************/
        if(nodeExists(toReplaceId) == false) throw new IllegalArgumentException("No node with this id.");
        if(newNode == null) throw new IllegalArgumentException("Node must be non null.");
        assert(Invariant());
        /****************************************************************************/
        
        SRETreeNode toReplace = getNode(toReplaceId);
        SRETreeNode parent = toReplace.getParent();
        
        deleteSubtree_(toReplaceId);
        
        if(parent != null) {
            parent.setChild(toReplace.getBirthOrder(), newNode);
        }
        
        newNode.setParent(parent);
        newNode.setID(toReplaceId);
        nodes.put(toReplaceId, newNode);
        for(SRETreeNode child : newNode.getChildren()) {
            addSubtree_(child);
        }
        
        /****************************************************************************/
        assert(nodeExists(toReplaceId) == true);
        assert(Invariant());
        /****************************************************************************/
        
    }
    
    /**
     * If concat, choice, kleene and plusclos dont have enough children after a delete operation
     * ,correct the tree structure.
     * 
     * @param nodeId
     * @return
     *      the id of the first node that has the correct structure again while
     *      correcting the tree structure bottom up
     */
    private int correctStructure(int nodeId) {

        /****************************************************************************/
        assert(nodeExists(nodeId));
        /****************************************************************************/
        
        SRETreeNode node = getNode(nodeId);
        SRETreeNode parent = node.getParent();
        int correctNodeId;
        
        if(node.hasCorrectStructure() == false) {            
            if(node.getChildren().isEmpty()) {
                //can't correct anything, just delete this node
            }else {
                assert(node.getChildren().size() == 1);
                SRETreeNode child = node.getChildren().get(0);
                if(parent != null) {
                    child.setRate(node.getRate());
                    parent.setChild(node.getBirthOrder(), child);
                    child.setParent(parent);
                    node.setParent(null);
                }else {
                    setRoot(child);
                    child.setParent(null);
                }
            }
            
            deleteNode(nodeId);

            if(parent != null) {
                correctNodeId = correctStructure(parent.getId());
            }else {
                if(nodes.isEmpty()) {
                    correctNodeId = -1;
                }else {
                    correctNodeId = getRoot().getId();
                }
            }
            
        }else {
            correctNodeId = nodeId;
        }
        
        /****************************************************************************/
        assert(Invariant());
        assert(correctNodeId == -1 || getNode(correctNodeId).hasCorrectStructure());
        /****************************************************************************/
        
        return correctNodeId;
        
    }
    
    public void addLoop(int nodeId, double probability) {
        
        /****************************************************************************/
        if(1 <= probability || probability <= 0) throw new IllegalArgumentException("Probability must be between 0 and 1.");
        assert(Invariant());
        /****************************************************************************/
        
        SRETreeNode node = getNode(nodeId);
        SRETreeNode parent = node.getParent();
        SREKleene kleene = new SREKleene(node, probability);
        kleene.setParent(parent);
        
        if(parent == null) {
            setRoot(kleene);
        }
        
        /****************************************************************************/
        assert(Invariant());
        /****************************************************************************/
        
    }
    
    public void removeLoop(int nodeId) {
        
        /****************************************************************************/
        if(getNode(nodeId).getType() != SRETreeNode.Type.KLEENE && getNode(nodeId).getType() != SRETreeNode.Type.CLOSURE) throw new IllegalArgumentException("Not a loop node.");
        assert(Invariant());
        /****************************************************************************/
        
        SRETreeNode node = getNode(nodeId);
        SRETreeNode parent = node.getParent();

        SRETreeNode looped = node.getChildren().get(0);
        looped.setParent(parent);
        
        if(parent == null) {
            setRoot(looped);
        }else {
            parent.setChild(node.getBirthOrder(), looped);
        }
        
        /****************************************************************************/
        assert(Invariant());
        /****************************************************************************/
        
    }
    
    public void addChoice(int nodeId, SRETreeNode choiceToAdd) {
        
        /****************************************************************************/
        assert(Invariant());
        /****************************************************************************/
        
        SRETreeNode node = getNode(nodeId);
        SRETreeNode parent = node.getParent();
        
        SRETreeNode choice = new SRETreeBuilder().buildChoice(node, choiceToAdd);
        addSubtree_(choiceToAdd);
        addNode_(choice);
        
        choice.setParent(parent);
        
        if(parent == null) {
            setRoot(choice);
        }else {
            parent.setChild(node.getBirthOrder(), choice);
        }
        
        /****************************************************************************/
        assert(Invariant());
        /****************************************************************************/
        
    }
	
	public boolean nodeExists(int nodeId) {
	    return nodes.containsKey(nodeId);
	}
	
/*****************************************************************************
** Attributes
*****************************************************************************/
	
    private SRETreeNode root;
    
    private int maxId;
    
    private HashMap<Integer, SRETreeNode> nodes;
    
/*****************************************************************************
** Setter and Getter
******************************************************************************/
        
    public SRETreeNode getRoot() {
        return root;
    }

    public void setRoot(SRETreeNode root){
        this.root = root;
    }

    public HashMap<Integer, SRETreeNode> getNodes(){
        return nodes;
    }
	
    public int getUnusedId() {
        
        /****************************************************************************/
        //
        /****************************************************************************/
        
        int unusedId = maxId + 1;
        
        /****************************************************************************/
        assert(nodeExists(unusedId) == false);
        /****************************************************************************/
        
        return unusedId;
        
    }
    
    public SRETreeNode getNode(int id) {
        
        /****************************************************************************/
        if(nodeExists(id) == false) throw new IllegalArgumentException("No node with that id.");
        /****************************************************************************/
        
        SRETreeNode node = nodes.get(id);
        
        /****************************************************************************/
        assert(nodeExists(id) == true);
        assert(node.getId() == id);
        /****************************************************************************/
        
        return node;
        
    }
    
/*****************************************************************************
** Invariants
*****************************************************************************/
    
    public boolean Invariant() {
        //assert(isConnected()); not used here, since the parser also uses this, but builds the tree bottom up which is not necessarily connected
        assert(noChildrenOutsideTree());
        assert(correctParentChildRelation());
        assert(hasCorrectStructure());
        return true;
    }
    
    public boolean isConnected() {
        for(SRETreeNode node : nodes.values()) {
            if(node == root) {
                continue;
            }
            if(node.getParent() == null || !(node.getParent().equals(nodes.get(node.getParent().getId())))) {
                return false;
            }
        }
        return true;    
    }
    
    public boolean noChildrenOutsideTree() {
        for(SRETreeNode node : nodes.values()) {
            for(SRETreeNode child : node.getChildren()) {
                if(nodes.get(child.getId()) != child) {
                    return false;
                }
            }
        }
        return true;  
    }
    
    public boolean correctParentChildRelation() {
        for(SRETreeNode node : nodes.values()) {
            for(SRETreeNode child : node.getChildren()) {
                if(child.getParent() != node) {
                    return false;
                }
            }
        }
        return true; 
    }
    
    public boolean hasCorrectStructure() {
        for(SRETreeNode node : nodes.values()) {
            if(node.hasCorrectStructure() == false) {
                return false;
            }
        }
        return true;  
    }
    
}
