package de.hu_berlin.ensureII.sre.parser.sretree.builder;

import java.util.List;
import java.util.Stack;

import de.hu_berlin.ensureII.sre.grammar.Symbol;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETree;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;

public interface ISRETreeBuilder {

	/*
	 * 0 S -> E
	 * 1 E -> E:E
	 * 2 E -> E*p
	 * 3 E -> E#p 
	 * 4 E -> (E) 
	 * 5 E -> (C) 
	 * 6 E -> action
	 * 7 C -> C+C
	 * 8 C -> E[w]
	 */
	
	/**
	 * Build an abstract syntax tree according to the production rules of the grammar. 
	 * 
	 * @param ruleID
	 * 			The id of the rule for this construction.
	 * @param handle
	 * 			The handle of the reduce operation from the parser, i.e. the rhs of the rule.
	 * @param nodeStack
	 * 			The stack of TreeNodes we already constructed earlier.
	 * @param nodeMap
	 *          A Map with nodes which were built in the past for checking if this nodes was already built once.
	 * @return
	 * 			The abstract syntax tree according to the provided rule.
	 */
	public SRETreeNode buildSRETreeFromProductionRule(int ruleID, List<Symbol> handle, Stack<SRETreeNode> nodeStack);
	
	/**
	 * No construction or calculation is happening here, just return the TreeNode.
	 * 
	 * @param TreeNode
	 * 			The TreeNode to be passed on.
	 * @return
	 * 			The input TreeNode.
	 */
	public SRETreeNode passOnTreeNode(SRETreeNode TreeNode); 
	
	public SRETreeNode buildConcat(SRETreeNode left, SRETreeNode right);
	
	public SRETreeNode buildKleene(SRETreeNode operand, double probability);
	
	public SRETreeNode buildPlusClosure(SRETreeNode operand, double probability);
	
	/**
	 * Build a choice node by supplying the two children. If a child is itself a
	 * choice node then the children are added instead
	 * 
	 * @param left
	 *     left child of the choice parent node
	 * @param right
	 *     right child of the choice parent node
	 * @return
	 *     choice node with left and right as the children, or respectively their
	 *     children directly if they are choice nodes themselves
	 */
	public SRETreeNode buildChoice(SRETreeNode left, SRETreeNode right);
	
	public SRETreeNode buildWeightedOperand(SRETreeNode operand, int weight);
	
	public SRETreeNode buildAction(String action);

	public SRETreeNode buildChoice(List<SRETreeNode> nodes);
	
	public SRETree getTree();

	public void setTree(SRETree tree);
	
}
