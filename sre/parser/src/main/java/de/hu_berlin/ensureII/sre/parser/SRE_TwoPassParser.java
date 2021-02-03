package de.hu_berlin.ensureII.sre.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import de.hu_berlin.ensureII.sre.parser.attributes.SREConfig;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;
import de.hu_berlin.ensureII.sre.parser.sretree.builder.ISRETreeBuilder;
import de.hu_berlin.ensureII.sre.parser.sretree.builder.SRETreeBuilder;
import de.hu_berlin.ensureII.sre.parser.sretree.calculator.ISRETreeCalculator;
import de.hu_berlin.ensureII.sre.parser.sretree.calculator.SRETreeCalculator;

public class SRE_TwoPassParser extends SRE_Parser {

    /**
     * Parse the whole expression first and do semantic calculation afterwards
     */
    
/*****************************************************************************
** Constructors
*****************************************************************************/
    
    public SRE_TwoPassParser(List<String> searchString, SREConfig cfg) {
        super(searchString, cfg, new SRETreeBuilder());
        calc = new SRETreeCalculator(searchString);
    }
    
    public SRE_TwoPassParser(List<String> searchString, SREConfig cfg, ISRETreeBuilder builderForThreads) {
        super(searchString, cfg, builderForThreads);
        calc = new SRETreeCalculator(searchString);
    }
    
    public SRE_TwoPassParser(List<String> searchString, SREConfig cfg, ISRETreeCalculator calcForThreads) {
        super(searchString, cfg, new SRETreeBuilder());
        calc = new SRETreeCalculator(searchString);
    }
    
/*****************************************************************************
** Override SRE_Parser
*****************************************************************************/
    
    @Override
    public SRETreeNode parse(String input) {
        
        List<String> splittedString = splitInput(input);
        joinChildTrees(parent, parseListOfStrings(splittedString));
        calc.calculateNodeData(currentAST);
        
        return currentAST;
    }
    
/*****************************************************************************
** split String and parse the parts in parallel
*****************************************************************************/
    
    /**
     * 
     * @return
     *      The List of the components when the String is split on the first encounter 
     *      of a '+' or ':' going top-down the syntax tree
     */
    private List<String> splitInput(String input) {
        
        SRETreeNode root = super.parse(input);
        List<String> splittedString = new ArrayList<String>();
        childRates = new ArrayList<Integer>();
        List<SRETreeNode> children;
        
        parent = root;
        while(parent.getChildren().isEmpty() == false) {
            children = parent.getChildren();
            if(children.size() > 1) {
                for(SRETreeNode child : children) {
                    childRates.add(child.getRate());
                    splittedString.add(child.toString());
                }
                break;
            }
            parent = children.get(0);
        }
        
        if(splittedString.isEmpty()) {
            splittedString.add(root.toString());
        }
        return splittedString;
    }
    
    private List<Future<SRETreeNode>> parseListOfStrings(List<String> listOfInputs) {
        List<SRE_Parser> listOfParsers = new ArrayList<SRE_Parser>();
        for(String input : listOfInputs) {
            listOfParsers.add(new SRE_Parser(input, searchString, cfg, new SRETreeBuilder(), new SRETreeCalculator(searchString)));
        }
        
        executorService = Executors.newFixedThreadPool(Math.min(listOfParsers.size(), 4));
        List<Future<SRETreeNode>> listOfFutures;
        try {
            listOfFutures = executorService.invokeAll(listOfParsers);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        
        return listOfFutures;
    }
    
    private void joinChildTrees(SRETreeNode parent, List<Future<SRETreeNode>> listOfParallelChildCalculations)
    {
        List<SRETreeNode> children = new ArrayList<SRETreeNode>();
        try {
            SRETreeNode child;
            int i=0;
            
            for(Future<SRETreeNode> childCalculation : listOfParallelChildCalculations) {
                child = childCalculation.get();
                child.setRate(childRates.get(i++));
                children.add(child);
            }
            parent.setChildren(children);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }
        
    }
    
/*****************************************************************************
** Attributes
*****************************************************************************/
    
    ExecutorService executorService;
    
    private ISRETreeCalculator calc;
    
    /*
     * track the parent and the rates of the children where the string
     * split happened
     */
    private SRETreeNode parent;
    
    private List<Integer> childRates;
}
