package de.hu_berlin.ensureII.sre.parser.sretree.calculator;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;
import de.hu_berlin.ensureII.sre.parser.sretree.SREChoice;
import de.hu_berlin.ensureII.sre.parser.sretree.SREConcat;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;

public class SRETreeCalculatorParallel extends SRETreeCalculator implements ISRETreeCalculator, Callable<SRENodeData> {

/*****************************************************************************
** Constructors
*****************************************************************************/
    
    /**
     * 
     * @param searchedMatching
     * @param nrOfTasksToSplit
     *      Number of times that new threads should handle the calculation in a calculation
     *      of a node with more than one child.
     */
    public SRETreeCalculatorParallel(List<String> searchedMatching, int nrOfTasksToSplit) {
        super(searchedMatching);
        this.nrOfTasksToSplit = nrOfTasksToSplit;
    }
    
    /**
     * 
     * @param searchedMatching
     * @param nrOfTasksToSplit
     *      Number of times that new threads should handle the calculation in a calculation
     *      of a node with more than one child.
     * @param nodeToCalculate
     *      Argument for when we execute call of the Callable interface.
     */
    private SRETreeCalculatorParallel(List<String> searchedMatching, int nrOfTasksToSplit, SRETreeNode nodeToCalculate) {
        super(searchedMatching);
        this.nrOfTasksToSplit = nrOfTasksToSplit;
        this.nodeToCalculate = nodeToCalculate;
    }
    
/*****************************************************************************
** Implement ISRETreeCalculator
*****************************************************************************/
    
    @Override
    public SRENodeData calculateNodeData_(SREConcat concatNode) {
        if(nrOfTasksSplitted < nrOfTasksToSplit) {
            nrOfTasksSplitted++;
            
            SRETreeNode left = concatNode.getChildren().get(0);
            SRETreeNode right = concatNode.getChildren().get(1);
            calculateNodeDataParallel(left, right);
            SRENodeData concatData = new SRENodeData();
            semanticCalculator.choice(left.getData(), right.getData(), concatData);
            
            return concatData;
        }else {
            return super.calculateNodeData_(concatNode);
        }
    }
    
    @Override
    public SRENodeData calculateNodeData_(SREChoice choiceNode) {
        if(nrOfTasksSplitted < nrOfTasksToSplit) {
            nrOfTasksSplitted++;
            
            SRETreeNode left = choiceNode.getChildren().get(0);
            SRETreeNode right = choiceNode.getChildren().get(1);
            calculateNodeDataParallel(left, right);
            SRENodeData choiceData = new SRENodeData();
            semanticCalculator.choice(left.getData(), right.getData(), choiceData);
            
            return choiceData;
        }else {
            return super.calculateNodeData_(choiceNode);
        }
    }
    
/*****************************************************************************
** MultiThreading
*****************************************************************************/
    
    /**
     * Create two threads to calculate and set the NodeData for the two input nodes.
     * 
     * @param node1
     * @param node2
     */
    private void calculateNodeDataParallel(SRETreeNode node1, SRETreeNode node2) {     
        SRETreeCalculatorParallel calculator1 = new SRETreeCalculatorParallel(searchedMatching, nrOfTasksToSplit, node1);
        FutureTask<SRENodeData> futureTask1 = new FutureTask<SRENodeData>(calculator1);
        Thread t1 = new Thread(futureTask1);
        if(node1.getData() == null) {
            t1.start();
        }
        SRETreeCalculatorParallel calculator2 = new SRETreeCalculatorParallel(searchedMatching, nrOfTasksToSplit, node2);
        FutureTask<SRENodeData> futureTask2 = new FutureTask<SRENodeData>(calculator2);
        Thread t2 = new Thread(futureTask2);
        if(node2.getData() == null) {
            t2.start();
        }
        
        try{
            SRENodeData leftData = futureTask1.get();
            if(leftData != null) {
                node1.setData(leftData);
            }
            
            SRENodeData rightData = futureTask2.get();
            if(rightData != null) {
                node2.setData(rightData);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        } 
        
    }
    
/*****************************************************************************
** Implement Callable
*****************************************************************************/
    
    @Override
    public SRENodeData call() {
        return calculateNodeData(nodeToCalculate);
    }
    
/*****************************************************************************
** Attributes, Setter and Getter
*****************************************************************************/
    
    private int nrOfTasksToSplit;
    
    private static volatile int nrOfTasksSplitted = 0;
    
    private SRETreeNode nodeToCalculate;
    
}
