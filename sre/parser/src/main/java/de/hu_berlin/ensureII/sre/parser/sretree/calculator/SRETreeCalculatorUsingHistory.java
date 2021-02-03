package de.hu_berlin.ensureII.sre.parser.sretree.calculator;

import java.util.HashMap;
import java.util.List;

import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;

public class SRETreeCalculatorUsingHistory extends SRETreeCalculator {

    /**
     * Check the HashMap if the value for the node has already been calculated in the past.
     */
    
/*****************************************************************************
** Constructors
*****************************************************************************/
    
    public SRETreeCalculatorUsingHistory(List<String> searchedMatching) {
        super(searchedMatching);
        nodeHistory = new HashMap<Integer, SRETreeNode>();
        hitrate = 0;
        falseHit = 0;
    }
    
/*****************************************************************************
** Implement ISRETreeCalculator
*****************************************************************************/
    
    @Override
    public SRENodeData calculateNodeData(SRETreeNode treeNode) {
        
        SRENodeData nodeData = new SRENodeData();
        
        if(nodeHistory.containsKey(treeNode.hashCode()) && treeNode.ifEquals(nodeHistory.get(treeNode.hashCode()))) {
            nodeData = nodeHistory.get(treeNode.hashCode()).getData();
            hitrate++;
        }else {
            if(nodeHistory.get(treeNode.hashCode()) != null) {
                falseHit++;
            }
            nodeData = treeNode.calculateNodeData(this);
            nodeHistory.put(treeNode.hashCode(), treeNode);
        }
        
        treeNode.setData(nodeData);
        
        return nodeData;
    }
    
/*****************************************************************************
** Attributes, Setter and Getter
*****************************************************************************/

    /**
     * History of nodes parsed and their calculated data.
     */
    private HashMap<Integer, SRETreeNode> nodeHistory;
    
    /**
     * Number of times the same node was found in the map.
     */
    public long hitrate;
    
    /**
     * Number of times a different node with the same hash was found.
     */
    public long falseHit;
    
}
