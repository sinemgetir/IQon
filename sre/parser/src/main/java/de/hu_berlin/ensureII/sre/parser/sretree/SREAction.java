package de.hu_berlin.ensureII.sre.parser.sretree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;
import de.hu_berlin.ensureII.sre.parser.sretree.calculator.ISRETreeCalculator;


public class SREAction extends SRETreeNode {

/*****************************************************************************
** Constructors
*****************************************************************************/

    /**
     * Constructor.
     *
     * @param c
     *            The character to recognize. An empty string recognizes the
     *            empty String. Must not be null.
     */
    public SREAction(String c) {
        
        /****************************************************************************/
        if(c == null) throw new IllegalArgumentException("c must be non null.");
        /****************************************************************************/
        
        this.c = c;
        calculateHash();
        
        /****************************************************************************/
        assert(getCharacter() == c);
        /****************************************************************************/
        
    }

    /**
     * Factory method for an epsilon SRE
     * 
     * @return a newly constructed epsilon SRE
     */
    public static SREAction EPSILON() {
        return new SREAction("");
    }
    
/*****************************************************************************
** Semantic calculation
*****************************************************************************/
    
    @Override
    public SRENodeData calculateNodeData(ISRETreeCalculator calc) {
        return calc.calculateNodeData_(this);
    }
    
/*****************************************************************************
** Override SRETreeNode
*****************************************************************************/

    @Override
    public Type getType() {
        return Type.ACTION;
    }
    
    /**
     * Using Dan Bernstein's djb2 hashing
     */
    @Override
    public void calculateHash() {
        hash = 5381;
        
        for(int i=0; i<c.length(); i++) {
            hash = hash * 33 + c.charAt(i);
        }
        
    }
    
    @Override
    public boolean ifEquals(SRETreeNode other) {
        if (other == null) {
            return false;
        } else if (this.getClass().isAssignableFrom(other.getClass())) {
            String oc = ((SREAction) other).getCharacter();
            return Objects.equals(oc, this.getCharacter());
        } else {
            return false;
        }
    }
    
    @Override
    public List<SRETreeNode> getChildren(){
        return new ArrayList<SRETreeNode>();
    }
    
    @Override
    public void setChildren(List<SRETreeNode> children){
        //do nothing
    }
    
    @Override
    public void setChild(int index, SRETreeNode node) {
        //do nothing
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
        //do nothing
    }
    
    @Override
    public void removeChild(SRETreeNode child) {
        //do nothing
    }
    
    @Override
    public boolean hasCorrectStructure() {
        return true;
    }
    
    @Override
    public String dotNodeLabel() {
        String s = getCharacter();
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
        return getCharacter();
    }

    @Override
    public SRETreeNode clone() {
        return new SREAction(getCharacter());
    }
    
/*****************************************************************************
** Fields
*****************************************************************************/
    
    private String c;
    
/*****************************************************************************
** Setter and Getter
*****************************************************************************/
 
    /**
     * get the transition character.
     * 
     * @return the transition character.
     */
    public String getCharacter() {
        return c;
    }
    
}


