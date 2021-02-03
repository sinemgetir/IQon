package de.hu_berlin.ensureII.sre.parser.sretree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;
import de.hu_berlin.ensureII.sre.parser.sretree.calculator.ISRETreeCalculator;

public class SREChoice extends SRETreeNode {

/*****************************************************************************
** Constructors
*****************************************************************************/
	
	/**
	 * Constructor
	 * 
	 * @param sres
	 *            The sub SREs, each coupled with the rate that it gets
	 *            chosen. Note that each rate needs to be nonnegative.
	 */
	public SREChoice(List<Tuple<SRETreeNode, Integer>> sres) {
	    
	    /****************************************************************************/
        if(sres == null) throw new IllegalArgumentException("sres must be non null.");
        /****************************************************************************/
	    
	    setChildrenAndRates(sres);
		
		calculateHash();
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
        return Type.CHOICE;
    }
	
	/**
     * Using Dan Bernstein's djb2 hashing
     */
	@Override
    public void calculateHash() {
        
        hash = 5381;
        char choice = '+';
        
        for(int i=0; i<children.size(); i++) {
            hash = hash + children.get(i).hashCode();
            hash = hash * 33 + children.get(i).getRate();
            
            if(i<children.size() -1) {
                hash = hash * 33 + choice;
            }
        }
    }
	
	@Override
    public boolean ifEquals(SRETreeNode o) {
        if (o == null) {
            return false;
        } else if (this.getClass().isAssignableFrom(o.getClass())) {
            SREChoice other = (SREChoice) o;
            boolean equal = true;
            for(int i=0; i<other.getChildren().size(); i++) {
                equal = other.getChildren().get(i).ifEquals(this.children.get(i));
                if(!equal) break;
            }
            return equal;
        } else {
            return false;
        }
    }
	
	@Override
    public List<SRETreeNode> getChildren(){
        return children;
    }
	
	/**
	 * Sets the new children for this node. Also takes over the rates of each child,
	 * i.e {@link #rates} are overwritten.
	 */
	@Override
    public void setChildren(List<SRETreeNode> children){
	    
	    /****************************************************************************/
	    if(children == null) throw new IllegalArgumentException("Children must be non null.");
	    /****************************************************************************/
	    
        this.children = children;
        this.rates = new ArrayList<Integer>();
        
        for(int i=0; i<children.size(); i++) {
            this.children.get(i).setParent(this);
            this.children.get(i).setBirthOrder(i);
            this.rates.add(children.get(i).getRate());
        }
        
        /****************************************************************************/
        assert(checkRates());
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
        rates.set(index, node.getRate());
        
        /****************************************************************************/
        assert(checkRates());
        /****************************************************************************/
        
    }
	
	@Override
	public void addChild(SRETreeNode child) {
        
        /****************************************************************************/
        if(child == null) throw new IllegalArgumentException("Child must be non null.");
        /****************************************************************************/
        
        children.add(child);
        rates.add(child.getRate());
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
        rates.add(index, child.getRate());
        child.setParent(this);
        
        for(int i=0; i<children.size(); i++) {
            children.get(i).setBirthOrder(i);
        }
        
        /****************************************************************************/
        assert(hasCorrectStructure());
        /****************************************************************************/
	    
	}
	
	@Override 
	public void removeAllChildren() {
	    for(SRETreeNode child : children) {
	        child.setParent(null);
	        child.setBirthOrder(0);
	    }
	    children = new ArrayList<SRETreeNode>();
	    rates = new ArrayList<Integer>();
	}
	
	@Override
    public void removeChild(int index) {
	    
	    /****************************************************************************/
	    if(index < 0 || index >= children.size()) throw new IllegalArgumentException("No child at position " + index + ".");
	    /****************************************************************************/
	    
        SRETreeNode child = getChildren().get(index);
        child.setParent(null);
        children.remove(index);
        rates.remove(index);
        
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
        if(children == null) throw new IllegalArgumentException("Child must be non null.");
        /****************************************************************************/
	    
        removeChild(children.indexOf(child));
        
        /****************************************************************************/
        //
        /****************************************************************************/
        
    }
	
	@Override
    public boolean hasCorrectStructure() {
        return children.size() > 1 && checkRates();
    }
	
	@Override
    public String dotNodeLabel() {
	    String s = "+";
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
        return "";
//        '(' + IntStream.range(0, children.size())
//                .mapToObj(i -> children.get(i).toString() + '[' + rates.get(i) + ']').collect(Collectors.joining(" + "))
//                + ')';
    }

    @Override
    public SRETreeNode clone() {
        
        List<Tuple<SRETreeNode, Integer>> ratedChildren = new ArrayList<Tuple<SRETreeNode, Integer>>();
        Tuple<SRETreeNode, Integer> ratedChild;
        
        for(int i=0; i < children.size(); i++) {
            ratedChild = new Tuple<SRETreeNode, Integer>(children.get(i).clone(), rates.get(i));
            ratedChildren.add(ratedChild);
        }
        
        return new SREChoice(ratedChildren);
    }
	
/*****************************************************************************
** Set rated children
*****************************************************************************/
    
    public void setChildrenAndRates(List<Tuple<SRETreeNode, Integer>> ratedChildren) {
        List<SRETreeNode> children = new ArrayList<SRETreeNode>();
        List<Integer> rates = new ArrayList<Integer>();
        
        for(Tuple<SRETreeNode, Integer> tuple : ratedChildren) {
            children.add(tuple.x);
            rates.add(tuple.y);
        }
        
        this.setChildrenAndRates(children, rates);
    }
    
    public void setChildrenAndRates(List<SRETreeNode> children, List<Integer> rates) {
        this.setChildren(children);
        this.setRates(rates);
    }
    
/*****************************************************************************
** Edit operations
*****************************************************************************/
    
    public void updateRate(int toUpdate, int rate) {
        rates.set(toUpdate, rate);
        children.get(toUpdate).setRate(rate);
    }
    
/*****************************************************************************
** Attributes
*****************************************************************************/
	
	private List<SRETreeNode> children;
	
    private List<Integer> rates;
	
/*****************************************************************************
** Setter and Getter
*****************************************************************************/
    
    /**
     * Get the rates for the choices. The indices of the SREs from
     * {@link #children} will match the indices of the returned array.
     * 
     * @return read only list of non negative integers
     */
    public List<Integer> getRates() {
        return Collections.unmodifiableList(rates);
    }
    
    public void setRates(List<Integer> rates) {
        
        /****************************************************************************/
        if(rates == null) throw new IllegalArgumentException("rates must be non null.");
        if(rates.size() != this.children.size()) throw new IllegalArgumentException("Number of rates must match number of children.");
        /****************************************************************************/
        
        this.rates = new ArrayList<Integer>();
        for(int i=0; i < rates.size(); i++) {
            if(rates.get(i) < 0) throw new IllegalArgumentException("Rates must be non negative."); 
            this.rates.add(rates.get(i));
            this.children.get(i).setRate(rates.get(i));
        }
        
        /****************************************************************************/
        assert(checkRates());
        /****************************************************************************/
        
    }
    
/*****************************************************************************
** Assertions
*****************************************************************************/
    
    boolean checkRates() {
        
        boolean ratesOk = true;
        
        for(int i=0; i < children.size(); i++) {
            ratesOk = ratesOk && (children.get(i).getRate() == rates.get(i));
        }
        
        return ratesOk;
        
    }
	
}
