package de.hu_berlin.ensureII.sre.parser.sretree;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;
import de.hu_berlin.ensureII.sre.parser.sretree.calculator.ISRETreeCalculator;

public class SREKleene extends SRETreeNode {

/*****************************************************************************
** Constructors
*****************************************************************************/
	
	/**
	 * Constructor.
	 * 
	 * @param sre
	 *            The SRE to iterate
	 * @param rate
	 *            the probability of iteration.
	 */
	public SREKleene(SRETreeNode sre, double rate) {
	    
	    /****************************************************************************/
        if(sre == null) throw new IllegalArgumentException("sre must be non null.");
        if(rate < 0 || rate > 1) throw new IllegalArgumentException("rate must be a probability.");
        /****************************************************************************/
	    
		this.setChildren(new ArrayList<SRETreeNode>(Arrays.asList(sre)));
		this.repetitionRate = rate;
		
		calculateHash();
		
		/****************************************************************************/
        assert(sre != null);
        assert(rate >= 0 && rate <= 1);
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
        return Type.KLEENE;
    }
	
	/**
     * Using Dan Bernstein's djb2 hashing
     */
    @Override
    public void calculateHash() {
        
        hash = 5381;
        int precision = 1000; //for using the repetitionRate in the hash calculation
        char kleene = '*';
        
        hash = hash + getChild().hashCode();
        hash = hash * 33 + kleene;
        hash = hash * 33 + (int)(getRepetitionRate() * precision);
            
    }
    
    @Override
    public boolean ifEquals(SRETreeNode other) {
        if (other == null) {
            return false;
        } else if (this.getClass().isAssignableFrom(other.getClass())) {
            SREKleene otherConverted = (SREKleene) other;
            return otherConverted.getChild().ifEquals(this.getChild())
                    && Math.abs(otherConverted.getRepetitionRate() - this.getRepetitionRate()) < Math.ulp(otherConverted.getRepetitionRate()) + Math.ulp(this.getRepetitionRate());
        } else {
            return false;
        }
    }
    
    @Override
    public List<SRETreeNode> getChildren(){
        List<SRETreeNode> children = new ArrayList<SRETreeNode>();
        if(this.getChild() != null) {
            children.add(this.getChild());
        }
        return children;
    }
    
    @Override
    public void setChildren(List<SRETreeNode> children){
        
        /****************************************************************************/
        if(children == null) throw new IllegalArgumentException("Children must be non null.");
        if(children.isEmpty()) throw new IllegalArgumentException("No children to add.");
        if(children.size() > 1) throw new IllegalArgumentException("Kleene can only have one child");
        /****************************************************************************/
        
        setChild(children.get(0));
        
        /****************************************************************************/
        assert(this.sre != null);
        /****************************************************************************/
        
    }
    
    @Override
    public void setChild(int index, SRETreeNode node) {
        
        /****************************************************************************/
        if(node == null) throw new IllegalArgumentException("node == null : Node must be non null.");
        if(index != 0) throw new IllegalArgumentException("index != 0 : Kleene has only 1 child");
        /****************************************************************************/
        
        setChild(node);
        
        /****************************************************************************/
        assert(this.sre != null);
        /****************************************************************************/
        
    }
    
    @Override
    public void addChild(SRETreeNode child) {
        //do nothing
    }
    
    @Override
    public void insertChild(SRETreeNode child, int index) {
        //do nothing
    }
    
    @Override
    public void removeChild(int index) {
        
        /****************************************************************************/
        if(index != 0) throw new IllegalArgumentException("No child at position " + index + ".");
        /****************************************************************************/
        
        SRETreeNode child = getChildren().get(index);
        child.setParent(null);
        sre = null;
        
        /****************************************************************************/
        assert(sre == null);
        /****************************************************************************/
        
    }
    
    @Override
    public void removeChild(SRETreeNode child) {
        
        /****************************************************************************/
        if(child == null) throw new IllegalArgumentException("Child must be non null.");
        /****************************************************************************/
        
        removeChild(getChildren().indexOf(child));
        
        /****************************************************************************/
        assert(sre == null);
        /****************************************************************************/
        
    }
    
    @Override
    public boolean hasCorrectStructure() {
        return getChild() != null && (repetitionRate >= 0 && repetitionRate <= 1);
    }
    
    @Override
    public String dotNodeLabel() {
        String s = "*" + new BigDecimal(getRepetitionRate()).setScale(4, RoundingMode.HALF_UP).doubleValue();
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
		return "";//"(" + sre.toString() + "*" + repetitionRate + ")";
	}

	@Override
	public SRETreeNode clone() {
		return new SREKleene(getChild().clone(), repetitionRate);
	}

/*****************************************************************************
** Attributes
*****************************************************************************/
	
	private SRETreeNode sre;
	
    private double repetitionRate;
    
/*****************************************************************************
** Setter and Getter
*****************************************************************************/
	
    /**
     * Get the iterated SRE.
     * 
     * @return An SRE.
     */
    public SRETreeNode getChild() {
        return sre;
    }
    
    public void setChild(SRETreeNode node) {
        this.sre = node;
        node.setParent(this);
        node.setBirthOrder(0);
    }
    
    /**
     * Get the probability of another repetition.
     * 
     * @return The repetition rate.
     */
    public double getRepetitionRate() {
        return repetitionRate;
    }
    
    public void setRepetionRate(double prob) {
        this.repetitionRate = prob;
    }
    
}
