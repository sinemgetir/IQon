package de.hu_berlin.ensureII.sre.parser.sretree.calculator;

import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;
import de.hu_berlin.ensureII.sre.parser.sretree.SREAction;
import de.hu_berlin.ensureII.sre.parser.sretree.SREChoice;
import de.hu_berlin.ensureII.sre.parser.sretree.SREConcat;
import de.hu_berlin.ensureII.sre.parser.sretree.SREKleene;
import de.hu_berlin.ensureII.sre.parser.sretree.SREPlusClosure;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;

public class SRETreeCalculatorNull implements ISRETreeCalculator {

    @Override
    public SRENodeData calculateNodeData(SRETreeNode treeNode) {
        return null;
    }

    @Override
    public SRENodeData calculateNodeData_(SREConcat concatNode) {
        return null;
    }

    @Override
    public SRENodeData calculateNodeData_(SREKleene kleeneNode) {
        return null;
    }

    @Override
    public SRENodeData calculateNodeData_(SREPlusClosure plusClosureNode) {
        return null;
    }

    @Override
    public SRENodeData calculateNodeData_(SREChoice choiceNode) {
        return null;
    }

    @Override
    public SRENodeData calculateNodeData_(SREAction actionNode) {
        return null;
    }

    @Override
    public SRENodeData calculateWeightedOperand(SRETreeNode node) {
        return null;
    }

}
