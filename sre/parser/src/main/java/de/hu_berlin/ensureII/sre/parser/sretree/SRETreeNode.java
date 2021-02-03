package de.hu_berlin.ensureII.sre.parser.sretree;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;
import de.hu_berlin.ensureII.sre.parser.sretree.calculator.ISRETreeCalculator;

public abstract class SRETreeNode{
    
	public enum Type {
		CHOICE, CONCAT, KLEENE, ACTION, CLOSURE
	}
	
/*****************************************************************************
** Constructors
*****************************************************************************/

	public SRETreeNode() {
	    birthOrder = 0;
	}
	
/*****************************************************************************
** Methods
*****************************************************************************/

	public boolean isRoot() {
	    return parent == null;
	}
	
	public void removeAllChildren() {
	    for(SRETreeNode child : this.getChildren()) {
	        child.setParent(null);
	    }
	    this.setChildren(new ArrayList<SRETreeNode>());
	}

/*****************************************************************************
** Abstract methods
*****************************************************************************/

    public abstract Type getType();

    public abstract SRENodeData calculateNodeData(ISRETreeCalculator calc);

    public abstract boolean ifEquals(SRETreeNode o);

    public abstract SRETreeNode clone();

    public abstract void calculateHash();

    public abstract List<SRETreeNode> getChildren();

    public abstract void setChildren(List<SRETreeNode> children);
    
    public abstract void setChild(int index, SRETreeNode node);
    
    public abstract void addChild(SRETreeNode node);
    
    public abstract void insertChild(SRETreeNode node, int index);
    
    public abstract void removeChild(int index);
    
    public abstract void removeChild(SRETreeNode child);
    
    public abstract boolean hasCorrectStructure();

/*****************************************************************************
** Override Object
*****************************************************************************/
	
	@Override
	public int hashCode() {
	    return hash;
	}
	
/*****************************************************************************
**  Print dot file
*****************************************************************************/
	
	public void printToDot(String filePath) {
	    try{
	        PrintWriter pw = new PrintWriter(filePath);
	        StringBuilder sb = new StringBuilder();
	        sb.append("graph G {\n");
	        sb.append(addDotNodeDefinitions());
	        sb.append(addDotArcs());
	        sb.append("}");
	        pw.write(sb.toString());
	        pw.close();
	    }catch(Exception e) {
	        e.printStackTrace();
	    }    
	}
	
	public String addDotNodeDefinitions() {
	    StringBuilder sb = new StringBuilder();
	    sb.append(dotNodeRepresentation());
	    
	    for(SRETreeNode child : getChildren()) {
	        sb.append(child.dotNodeRepresentation());
	        sb.append(child.addDotNodeDefinitions());
	    }
	    
	    return sb.toString();
	}
	
	public String addDotArcs() {
	    
	    StringBuilder sb = new StringBuilder();
        
        for(SRETreeNode child : getChildren()) {
            sb.append(getId() + "--" + child.getId() + ";\n");
            sb.append(child.addDotArcs());
        }
        
        return sb.toString();
	}
	
	public String dotNodeRepresentation() {
	    return "    " + getId() + "[label=\"" + dotNodeLabel() + "\"];\n";
	}
	
	/**
	 * 
	 * @return
	 *     The Label for the node in a dot file.
	 */
	public abstract String dotNodeLabel();
	
/*****************************************************************************
** Fields
*****************************************************************************/
	
	private SRENodeData data;
	
	private int rate;
	
	private int id;
	
	protected int hash;
	
	private SRETreeNode parent;
	
	/**
	 * Birth order is the order in the list of children of the parent.
	 */
	private int birthOrder;
	
	/**
	 * in the original automaton the source state of the transition/sequence of transitions
	 * corresponding to this sre
	 */
	private int sourceState;
	
	/**
     * in the original automaton the target state of the transition/sequence of transitions
     * corresponding to this sre
     */
	private int targetState;
	
/*****************************************************************************
** Setter and Getter
******************************************************************************/
	
	/**
	 * 
	 * @return
	 *     data of the node
	 */
	public SRENodeData getData() {
        return data;
    }

    public void setData(SRENodeData data) {
        this.data = data;
    }
	
    /**
     * 
     * @return
     *      rate of the node
     */
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
    
    public void setID(int id) {
        this.id = id;
    }
    
    /**
     * 
     * @return
     *      id of the node
     */
    public int getId() {
        return id;
    }
    
    public SRETreeNode getParent() {
        return parent;
    }
    
    public void setParent(SRETreeNode parent) {
        this.parent = parent;
    }
    
    /**
     * 
     * @return
     *      Index of this node in the children list of this node's parent.
     */
    public int getBirthOrder() {
        return birthOrder;
    }
    
    public void setBirthOrder(int birthOrder) {
        this.birthOrder = birthOrder;
    }
    
    public int getSourceState() {
        return sourceState;
    }
    
    public void setSourceState(int stateId) {
        sourceState = stateId;
    }
    
    public int getTargetState() {
        return targetState;
    }
    
    public void setTargetState(int stateId) {
        targetState = stateId;
    }
    
}
