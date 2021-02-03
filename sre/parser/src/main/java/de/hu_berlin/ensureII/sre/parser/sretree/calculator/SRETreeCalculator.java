package de.hu_berlin.ensureII.sre.parser.sretree.calculator;

import java.util.List;

import de.hu_berlin.ensureII.sre.parser.attributes.ICalculator;
import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;
import de.hu_berlin.ensureII.sre.parser.attributes.verification.AllCalculator;
import de.hu_berlin.ensureII.sre.parser.sretree.SREAction;
import de.hu_berlin.ensureII.sre.parser.sretree.SREChoice;
import de.hu_berlin.ensureII.sre.parser.sretree.SREConcat;
import de.hu_berlin.ensureII.sre.parser.sretree.SREKleene;
import de.hu_berlin.ensureII.sre.parser.sretree.SREPlusClosure;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;

public class SRETreeCalculator implements ISRETreeCalculator{

/*****************************************************************************
** Constructors
*****************************************************************************/
    
    public SRETreeCalculator(List<String> searchedMatching){
        this.searchedMatching = searchedMatching;
    }
    
/*****************************************************************************
** Implement ISRETreeCalculator
*****************************************************************************/
    
    @Override
    public SRENodeData calculateNodeData(SRETreeNode treeNode) {
        SRENodeData data = treeNode.calculateNodeData(this);
        treeNode.setData(data);
        return data;
    }
    
    @Override
    public SRENodeData calculateNodeData_(SREConcat concatNode) {
        SRENodeData concatData = new SRENodeData();
        
        SRETreeNode left = concatNode.getChildren().get(0);
        if(left.getData() == null) {
            calculateNodeData(left);
        }
        SRETreeNode right = concatNode.getChildren().get(1);
        if(right.getData() == null) {
            calculateNodeData(right);
        }
        
        semanticCalculator.concat(left.getData(), right.getData(), concatData);
        return concatData;
    }

    @Override
    public SRENodeData calculateNodeData_(SREKleene kleeneNode) {
        SRENodeData kleeneData = new SRENodeData();
        SRENodeData prob = new SRENodeData();
        
        SRETreeNode operand = kleeneNode.getChild();
        if(operand.getData() == null) {
            calculateNodeData(operand);
        }
        
        prob.setBasicProbability(kleeneNode.getRepetitionRate());
        semanticCalculator.initialSetupProbability(prob);
        semanticCalculator.kleenClosure(operand.getData(), prob, kleeneData);
        
        return kleeneData;
    }

    @Override
    public SRENodeData calculateNodeData_(SREPlusClosure plusClosureNode) {
        SRENodeData plusClosureData = new SRENodeData();
        SRENodeData prob = new SRENodeData();
        
        SRETreeNode operand = plusClosureNode.getChild();
        if(operand.getData() == null) {
            calculateNodeData(operand);
        }
        
        prob.setBasicProbability(plusClosureNode.getRepetitionRate());
        semanticCalculator.initialSetupProbability(prob);
        semanticCalculator.plusClosure(operand.getData(), prob, plusClosureData);
        
        return plusClosureData;
    }

    @Override
    public SRENodeData calculateNodeData_(SREChoice choiceNode) {
        SRENodeData choiceData = new SRENodeData();
        
        SRETreeNode left = choiceNode.getChildren().get(0);
        calculateWeightedOperand(left);

        SRETreeNode right = choiceNode.getChildren().get(1);
        calculateWeightedOperand(right);
        
        semanticCalculator.choice(left.getData(), right.getData(), choiceData);
        
        return choiceData;
    }

    @Override
    public SRENodeData calculateNodeData_(SREAction actionNode) {
        SRENodeData actionData = new SRENodeData();
        actionData.setTerminal(actionNode.getCharacter());
        actionData.setSearchedMatching(searchedMatching);
        actionData.setN(boundedN);
        semanticCalculator.initialSetupAction(actionData);
        
        return actionData;
    }
    
    @Override
    public SRENodeData calculateWeightedOperand(SRETreeNode node) {
        SRENodeData weightedOperandData = new SRENodeData();
        SRENodeData rateNode = new SRENodeData();
        
        if(node.getData() == null) {
            calculateNodeData(node);
        }
        
        rateNode.setChoiceRate(node.getRate());
        semanticCalculator.choiceElem(node.getData(), rateNode, weightedOperandData);
        
        //patching some bug
        weightedOperandData.setChoiceRate(rateNode.getChoiceRate());
        //
        node.setData(weightedOperandData);

        return weightedOperandData;
    }
    
/*****************************************************************************
** Attributes
*****************************************************************************/
    
    protected List<String> searchedMatching;

    private int boundedN = 50;

    protected ICalculator semanticCalculator = new AllCalculator();

}
