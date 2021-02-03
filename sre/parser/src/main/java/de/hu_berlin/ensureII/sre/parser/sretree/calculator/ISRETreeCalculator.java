package de.hu_berlin.ensureII.sre.parser.sretree.calculator;

import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;
import de.hu_berlin.ensureII.sre.parser.sretree.SREAction;
import de.hu_berlin.ensureII.sre.parser.sretree.SREChoice;
import de.hu_berlin.ensureII.sre.parser.sretree.SREConcat;
import de.hu_berlin.ensureII.sre.parser.sretree.SREKleene;
import de.hu_berlin.ensureII.sre.parser.sretree.SREPlusClosure;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;

public interface ISRETreeCalculator {
    
    public SRENodeData calculateNodeData(SRETreeNode treeNode);
    
    public SRENodeData calculateNodeData_(SREConcat concatNode);
    
    public SRENodeData calculateNodeData_(SREKleene kleeneNode);
    
    public SRENodeData calculateNodeData_(SREPlusClosure plusClosureNode);
    
    public SRENodeData calculateNodeData_(SREChoice choiceNode);
    
    public SRENodeData calculateNodeData_(SREAction actionNode);
    
    public SRENodeData calculateWeightedOperand(SRETreeNode node);
}
