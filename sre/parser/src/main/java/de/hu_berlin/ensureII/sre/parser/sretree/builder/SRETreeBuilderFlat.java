package de.hu_berlin.ensureII.sre.parser.sretree.builder;

import java.util.ArrayList;
import java.util.List;

import de.hu_berlin.ensureII.sre.parser.sretree.SREChoice;
import de.hu_berlin.ensureII.sre.parser.sretree.SREConcat;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;
import de.hu_berlin.ensureII.sre.parser.sretree.Tuple;
import de.hu_berlin.ensureII.sre.parser.sretree.calculator.ISRETreeCalculator;

public class SRETreeBuilderFlat extends SRETreeBuilder {

/*****************************************************************************
** Constructors
*****************************************************************************/  

    public SRETreeBuilderFlat(){
        super();
    }

/*****************************************************************************
** Override SRETreeBuilder
*****************************************************************************/

    @Override
    public SRETreeNode buildChoice(SRETreeNode left, SRETreeNode right) {
        
        /****************************************************************************/
        if(left == null){
            throw new IllegalArgumentException("Left must be non null.");
        }
        if(right == null){
            throw new IllegalArgumentException("Right must be non null.");
        }
        /****************************************************************************/
        
        List<SRETreeNode> children = new ArrayList<SRETreeNode>();
        
        if(left.getType().equals(SRETreeNode.Type.CHOICE)) {
            children.addAll(left.getChildren());
        }else {
            children.add(left);
        }
        
        //dont merge children of right if it's a choice node, because
        //if right is a choice node it must have been inside
        //parenthesis', so the structure should not be broken up
        children.add(right);
        
        List<Tuple<SRETreeNode, Integer>> ratedChildren = new ArrayList<Tuple<SRETreeNode, Integer>>();
        Tuple<SRETreeNode, Integer> ratedChild;

        for(SRETreeNode child : children) {
            ratedChild = new Tuple<SRETreeNode, Integer> (child,  child.getRate());
            ratedChildren.add(ratedChild);
        }

        SREChoice choiceNode = new SREChoice(ratedChildren);
        
        //Not a good check for children now, since it checks whether the children are the same object.
        //Preferably we want to check only for equivalence, but that would require a costly tree compare.
        /****************************************************************************/
        assert(choiceNode.getType().equals(SRETreeNode.Type.CHOICE)) ;
        assert(choiceNode.getChildren().contains(left));
        assert(choiceNode.getChildren().contains(right));
        /****************************************************************************/

        return choiceNode;
    }
    
    @Override
    public SRETreeNode buildConcat(SRETreeNode left, SRETreeNode right) {
    	/****************************************************************************/
        if(left == null){
            throw new IllegalArgumentException("Left must be non null.");
        }
        if(right == null){
            throw new IllegalArgumentException("Right must be non null.");
        }
        /****************************************************************************/
        
        List<SRETreeNode> children = new ArrayList<SRETreeNode>();
        
        if(left.getType().equals(SRETreeNode.Type.CONCAT)) {
            children.addAll(left.getChildren());
        }else {
            children.add(left);
        }
        
        //dont merge children of right if it's a choice node, because
        //if right is a choice node it must have been inside
        //parenthesis', so the structure should not be broken up
        children.add(right);
        
//        List<Tuple<SRETreeNode, Integer>> ratedChildren = new ArrayList<Tuple<SRETreeNode, Integer>>();
//        Tuple<SRETreeNode, Integer> ratedChild;

//        for(SRETreeNode child : children) {
//            ratedChild = new Tuple<SRETreeNode, Integer> (child,  child.rate());
//            ratedChildren.add(ratedChild);
//        }

        SREConcat concatNode = new SREConcat(children);
        
        //Not a good check for children now, since it checks whether the children are the same object.
        //Preferably we want to check only for equivalence, but that would require a costly tree compare.
        /****************************************************************************/
        assert(concatNode.getType().equals(SRETreeNode.Type.CONCAT)) ;
   //     assert(concatNode.children().contains(left));
   //     assert(concatNode.children().contains(right));
        /****************************************************************************/

        return concatNode;
    }
}
