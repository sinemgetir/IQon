package de.hu_berlin.ensureII.sre.parser.sretree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;
import de.hu_berlin.ensureII.sre.parser.sretree.calculator.ISRETreeCalculator;


public class SREConcat extends SRETreeNode {

/*****************************************************************************
** Constructors
*****************************************************************************/

	/**
	 * Constructor
	 * 
	 * @param children
	 *            The SREs to concatenate. Need to be at least 2.
	 */
	public SREConcat(List<SRETreeNode> children) {
	    
	    /****************************************************************************/
        if(children == null) throw new IllegalArgumentException("Children must be non null.");
        /****************************************************************************/
		
		setChildren(children);
		calculateHash();
		
		/****************************************************************************/
        assert(getChildren() != null);
        /****************************************************************************/
		
	}
	
/*****************************************************************************
** Semantic calculation
*****************************************************************************/
	    
	@Override
	public SRENodeData calculateNodeData(ISRETreeCalculator calc) {
	    
	    /****************************************************************************/
        if(calc == null) throw new IllegalArgumentException("Calc must be non null.");
        /****************************************************************************/
	    
	    return calc.calculateNodeData_(this);
    }
	
/*****************************************************************************
** Override SRETreeNode
*****************************************************************************/

	@Override
    public Type getType() {
        return Type.CONCAT;
    }
	
	/**
     * Using Dan Bernstein's djb2 hashing
     */
	@Override
    public void calculateHash() {
        
        hash = 5381;
        char concat = ':';
        List<SRETreeNode> children = getChildren();
        
        for(int i=0; i<children.size(); i++) {
            hash = hash + children.get(i).hashCode();
            
            if(i<children.size() - 1) {
                hash = hash * 33 + concat;
            }
        }
    }
	
	@Override
    public boolean ifEquals(SRETreeNode o) {
        if (o == null) {
            return false;
        } else if (this.getClass().isAssignableFrom(o.getClass())) {
            SREConcat other = (SREConcat) o;
            boolean equal = true;
            for(int i=0; i<other.getChildren().size(); i++) {
                equal = other.getChildren().get(i).ifEquals(this.getChildren().get(i));
                if(!equal) break;
            }
            return equal;
        } else {
            return false;
        }
    }
	
	@Override
    public List<SRETreeNode> getChildren(){
        return Collections.unmodifiableList(children);
    }
	
	@Override
    public void setChildren(List<SRETreeNode> children){
	    
	    /****************************************************************************/
        if(children == null) throw new IllegalArgumentException("Children must be non null.");
        /****************************************************************************/
	    
        this.children = children;
        for(int i=0; i<children.size(); i++) {
            this.children.get(i).setParent(this);
            this.children.get(i).setBirthOrder(i);
        }
        
        /****************************************************************************/
        assert(getChildren() != null);
        /****************************************************************************/
        
    }
	
	@Override
    public void setChild(int index, SRETreeNode node) {
        
        /****************************************************************************/
        if(node == null) throw new IllegalArgumentException("Node must be non null.");
        /****************************************************************************/
        
        children.set(index, node);
        node.setParent(this);
        node.setBirthOrder(index);
        
        /****************************************************************************/
        //
        /****************************************************************************/
        
    }
	
	@Override
    public void addChild(SRETreeNode child) {
        
        /****************************************************************************/
        if(child == null) throw new IllegalArgumentException("Child must be non null.");
        /****************************************************************************/
        
        children.add(child);
        child.setParent(this);
        child.setBirthOrder(children.size() - 1);
        
        /****************************************************************************/
        assert(hasCorrectStructure());
        /****************************************************************************/
        
    }
    
    @Override
    public void insertChild(SRETreeNode child, int index) {
        
        /****************************************************************************/
        if(child == null) throw new IllegalArgumentException("Child must be non null.");
        /****************************************************************************/
        
        children.add(index, child);
        child.setParent(this);
        
        for(int i=0; i<children.size(); i++) {
            children.get(i).setBirthOrder(i);
        }
        
        /****************************************************************************/
        assert(hasCorrectStructure());
        /****************************************************************************/
        
    }
	
	@Override
    public void removeChild(int index) {
	    
	    /****************************************************************************/
        if(index < 0 || index >= children.size()) throw new IllegalArgumentException("No child at position " + index + ".");
        /****************************************************************************/
	    
        SRETreeNode child = getChildren().get(index);
        child.setParent(null);
        children.remove(index);
        
        for(int i=0; i<children.size(); i++) {
            children.get(i).setBirthOrder(i);
        }
        
        /****************************************************************************/
        //
        /****************************************************************************/
        
    }
	
	@Override
    public void removeChild(SRETreeNode child) {
	    
	    /****************************************************************************/
        if(child == null) throw new IllegalArgumentException("Child must be non null.");
        /****************************************************************************/
	    
        removeChild(children.indexOf(child));
        
        /****************************************************************************/
        //
        /****************************************************************************/
        
    }
	
	@Override
    public boolean hasCorrectStructure() {
        return children.size() > 1;
    }
	
	@Override
    public String dotNodeLabel() {
	    String s = ":";
        if(this.getRate() != 0) {
            s = s + " \\[" + this.getRate() + "\\]";
        }
        return s;
    }
	
/*****************************************************************************
** Override Object
*****************************************************************************/

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(children.get(0).toString());
		for (int i = 1; i < children.size(); i++) {
			builder.append(" : ");
			builder.append(children.get(i).toString());
		}
		return "";//"(" + builder.toString() + ")";
	}

	@Override
	public SRETreeNode clone() {
		List<SRETreeNode> sres = new ArrayList<SRETreeNode>();
		for (int i = 0; i < children.size(); i++) {
			sres.add(i, children.get(i).clone());;
		}
		return new SREConcat(sres);
	}
	
/*****************************************************************************
** Edit operations
*****************************************************************************/
	    
	
	
/*****************************************************************************
** Fields
*****************************************************************************/

	private List<SRETreeNode> children;
    
/*****************************************************************************
** Setter and Getter
*****************************************************************************/
	
}
