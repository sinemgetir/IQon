package de.hu_berlin.ensureII.sre.parser.sretree.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import de.hu_berlin.ensureII.sre.grammar.Symbol;
import de.hu_berlin.ensureII.sre.grammar.Token;
import de.hu_berlin.ensureII.sre.parser.sretree.SREAction;
import de.hu_berlin.ensureII.sre.parser.sretree.SREChoice;
import de.hu_berlin.ensureII.sre.parser.sretree.SREConcat;
import de.hu_berlin.ensureII.sre.parser.sretree.SREKleene;
import de.hu_berlin.ensureII.sre.parser.sretree.SREPlusClosure;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETree;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;
import de.hu_berlin.ensureII.sre.parser.sretree.Tuple;

public class SRETreeBuilder implements ISRETreeBuilder {


/*****************************************************************************
** Constructors
*****************************************************************************/  

    public SRETreeBuilder(){
        super();
        this.setTree(new SRETree());
    }

/*****************************************************************************
** Implement ISRETreeBuilder
*****************************************************************************/

    @Override
    public SRETreeNode buildSRETreeFromProductionRule(int ruleId, List<Symbol> handle, Stack<SRETreeNode> astStack) {
        
        /****************************************************************************/
        if(handle == null){
            throw new IllegalArgumentException("Handle must be non null.");
        }
        if(astStack == null){
            throw new IllegalArgumentException("AstStack must be non null.");
        }
        /****************************************************************************/
        
        SRETreeNode treeNode;
        SRETreeNode operand, left, right;
        Token weightToken, probToken;
        int weight;
        double probability;
        
        switch(ruleId){
        case 0: //S -> E
        treeNode = astStack.pop();
        treeNode = passOnTreeNode(treeNode);

        break;
        case 1: //E -> E1 : E2
            right = astStack.pop(); //E2
            left = astStack.pop(); //E1
            treeNode = buildConcat(left, right);
            right.setParent(treeNode);
            left.setParent(treeNode);

            tree.addNode(treeNode);


            break;
        case 2: //E -> E1*p
            operand = astStack.pop(); //E1
            probToken = ((Token)handle.get(2));
            probability = Double.parseDouble(probToken.value());
            treeNode = buildKleene(operand, probability);
            operand.setParent(treeNode);
           
            tree.addNode(treeNode);


            break;
        case 3: //E -> E1#p
            operand = astStack.pop(); //E1
            probToken = ((Token)handle.get(2));
            probability = Double.parseDouble(probToken.value());
            treeNode = buildPlusClosure(operand, probability);
            operand.setParent(treeNode);

            tree.addNode(treeNode);


            break;
        case 4: //E -> (E1)
            treeNode = astStack.pop();
            treeNode = passOnTreeNode(treeNode);

            break;
        case 5: //E -> (C)
            treeNode = astStack.pop();
            treeNode = passOnTreeNode(treeNode);

            break;
        case 6: //E -> action
            String action = ((Token) handle.get(0)).value();
            treeNode = buildAction(action);
            
            tree.addNode(treeNode);


            break;
        case 7: //C -> C1 + C2 
            right = astStack.pop(); //C2
            left = astStack.pop(); //C1
            treeNode = buildChoice(left, right);
            left.setParent(treeNode);
            right.setParent(treeNode);
            
            tree.addNode(treeNode);
            


            break;
        case 8: //C -> E[w]
            operand = astStack.pop();
            weightToken = ((Token)handle.get(2));
            weight = Integer.parseInt(weightToken.value());
            //bad, change it later
            //double rateValue = (double)weight;
            treeNode = buildWeightedOperand(operand, weight);

            break;
        default:
            throw new IllegalArgumentException("No rule with id " + ruleId + ".");
        }   
        
        return treeNode;
    }
    
    @Override
    public SRETreeNode passOnTreeNode(SRETreeNode treeNode) {
        return treeNode;
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
        children.add(left);
        children.add(right);
        SREConcat concatNode = new SREConcat(children);
        
        //Not a good check for children now, since it checks whether the children are the same object.
        //Preferably we want to check only for equivalence, but that would require a costly tree compare.
        /****************************************************************************/
        assert(concatNode.getType().equals(SRETreeNode.Type.CONCAT));
        assert(concatNode.getChildren().contains(left));
        assert(concatNode.getChildren().contains(right));
        assert(concatNode.hasCorrectStructure());
        /****************************************************************************/
        
        return concatNode;
    }

    @Override
    public SRETreeNode buildKleene(SRETreeNode operand, double probability) {
        
        /****************************************************************************/
        if(operand == null){
            throw new IllegalArgumentException("Operand must be non null.");
        }
        if(probability < 0 || probability > 1){
            throw new IllegalArgumentException("Probability must be between 0 and 1.");
        }
        /****************************************************************************/
        
        List<SRETreeNode> children = new ArrayList<SRETreeNode>();
        children.add(operand);
        SREKleene kleeneNode = new SREKleene(operand, probability);

        //Not a good check for children now, since it checks whether the children are the same object.
        //Preferably we want to check only for equivalence, but that would require a costly tree compare.
        /****************************************************************************/
        assert(kleeneNode.getType().equals(SRETreeNode.Type.KLEENE));
        assert((Math.abs(kleeneNode.getRepetitionRate() - probability)) < 0.00001);
        assert(kleeneNode.getChildren().size() == 1);
        assert(kleeneNode.getChild() == operand);
        assert(kleeneNode.hasCorrectStructure());
        /****************************************************************************/
        
        return kleeneNode;
    }

    @Override
    public SRETreeNode buildPlusClosure(SRETreeNode operand, double probability) {
        
        /****************************************************************************/
        if(operand == null){
            throw new IllegalArgumentException("Operand must be non null.");
        }
        if(probability < 0 || probability > 1){
            throw new IllegalArgumentException("Probability must be between 0 and 1.");
        }
        /****************************************************************************/
        
        List<SRETreeNode> children = new ArrayList<SRETreeNode>();
        children.add(operand);
        SREPlusClosure plusClosureNode = new SREPlusClosure(operand, probability);
        
        //Not a good check for children now, since it checks whether the children are the same object.
        //Preferably we want to check only for equivalence, but that would require a costly tree compare.
        /****************************************************************************/
        assert(plusClosureNode.getType().equals(SRETreeNode.Type.CLOSURE));
        assert((Math.abs(plusClosureNode.getRepetitionRate() - probability)) < 0.00001);
        assert(plusClosureNode.getChildren().size() == 1);
        assert(plusClosureNode.getChild() == operand);
        assert(plusClosureNode.hasCorrectStructure());
        /****************************************************************************/

        return plusClosureNode;
    }

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
        
        List<Tuple<SRETreeNode, Integer>> ratedChildren = new ArrayList<Tuple<SRETreeNode, Integer>>();
        Tuple<SRETreeNode, Integer> ratedChild;

        ratedChild = new Tuple<SRETreeNode, Integer> (left, left.getRate());
        ratedChildren.add(ratedChild);
        ratedChild = new Tuple<SRETreeNode, Integer> (right, right.getRate());
        ratedChildren.add(ratedChild);
        
        SREChoice choiceNode = new SREChoice(ratedChildren);
        
        //Not a good check for children now, since it checks whether the children are the same object.
        //Preferably we want to check only for equivalence, but that would require a costly tree compare.
        /****************************************************************************/
        assert(choiceNode.getType().equals(SRETreeNode.Type.CHOICE)) ;
        assert(choiceNode.getChildren().contains(left));
        assert(choiceNode.getChildren().contains(right));
        assert(choiceNode.hasCorrectStructure());
        /****************************************************************************/

        return choiceNode;
    }
    @Override
	public SRETreeNode buildChoice(List<SRETreeNode> nodes) {
		
        List<SRETreeNode> children = new ArrayList<SRETreeNode>();
        
       for (SRETreeNode subNode : nodes) {
    	 
           if(subNode == null){
               throw new IllegalArgumentException("Subnode must be non null.");
           }
    	   children.add(subNode);
       	}
        
       int nrOfKids = children.size();
        List<Tuple<SRETreeNode, Integer>> Children = new ArrayList<Tuple<SRETreeNode, Integer>>();
        Tuple<SRETreeNode, Integer> child;
        int rate;

        for(int i=0; i < nrOfKids; i++) {
            rate = children.get(i).getRate();
            child = new Tuple<SRETreeNode, Integer> (children.get(i), rate);
            Children.add(child);
        }

        SREChoice choiceNode = new SREChoice(Children);
        
        //Not a good check for children now, since it checks whether the children are the same object.
        //Preferably we want to check only for equivalence, but that would require a costly tree compare.
        /****************************************************************************/
        assert(choiceNode.getType().equals(SRETreeNode.Type.CHOICE)) ;
        assert(choiceNode.hasCorrectStructure());
        /****************************************************************************/

        return choiceNode;
	}
    
    @Override
    public SRETreeNode buildWeightedOperand(SRETreeNode operand, int weight) {
        
        /****************************************************************************/
        if(operand == null){
            throw new IllegalArgumentException("Left must be non null.");
        }
        if(weight < 0){
            throw new IllegalArgumentException("Weight must be non negative.");
        }
        /****************************************************************************/
        
        operand.setRate(weight);
        
        /****************************************************************************/
        assert(operand.getRate() == weight);
        /****************************************************************************/

        return operand;
    }

    @Override
    public SRETreeNode buildAction(String action){
        
        /****************************************************************************/
        if(action == null){
            throw new IllegalArgumentException("Action must be non null.");
        }
        /****************************************************************************/
        
        SREAction actionNode = new SREAction(action);
        
        /****************************************************************************/
        assert(actionNode.getType().equals(SRETreeNode.Type.ACTION)
                && actionNode.getCharacter().equals(action));
        /****************************************************************************/

        return actionNode;
    }
    
    //a patch to access set the tree root from parser //TODO Refactor 
    @Override
    public SRETree getTree() {
		return tree;
	}
    @Override
	public void setTree(SRETree tree) {
		this.tree = tree;
	}

/*****************************************************************************
** Attributes
*****************************************************************************/
    
    private SRETree tree;

}
