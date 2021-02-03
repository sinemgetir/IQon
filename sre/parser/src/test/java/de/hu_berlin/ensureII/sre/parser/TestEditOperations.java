package de.hu_berlin.ensureII.sre.parser;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.hu_berlin.ensureII.sre.parser.attributes.SREConfig;
import de.hu_berlin.ensureII.sre.parser.sretree.SREChoice;
import de.hu_berlin.ensureII.sre.parser.sretree.SREConcat;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETree;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;
import de.hu_berlin.ensureII.sre.parser.sretree.Tuple;
import de.hu_berlin.ensureII.sre.parser.sretree.builder.SRETreeBuilder;
import de.hu_berlin.ensureII.sre.parser.sretree.edit.ISRETreeEdit;
import de.hu_berlin.ensureII.sre.parser.sretree.edit.SRETreeEdit;

public class TestEditOperations {
	
List<String> searchedString = Arrays.asList("a","b"); 
	
    SREConfig cfg = new SREConfig();
	SRE_Parser parserNormal = new SRE_Parser(searchedString, cfg, new SRETreeBuilder());
	ISRETreeEdit edit = new SRETreeEdit();
	
	
	@Test
	public void testSubnodeAdditionConcat() {

	    String sre = "(a:b:c)";
	    parserNormal.parse(sre);
	    SRETree tConcat = parserNormal.sreTreeBuilder.getTree();
	    System.out.print(tConcat.getNodes() + " ---add(d)---> ");
	    
	    String toAdd = "d";
	    SRETreeNode newSubNode = parserNormal.parse(toAdd);
	    assertNotNull(newSubNode);
	    
	    edit.insertSubnode(tConcat, newSubNode, tConcat.getRoot().getId(), tConcat.getRoot().getChildren().size());
	    
	    System.out.println(tConcat.getNodes());

	}
	
	@Test
	public void testSubnodeAdditionChoice() {
	    
	    String sre = "((a*0.5)[5]+b[8])";
	    parserNormal.parse(sre);
	    SRETree tChoice = parserNormal.sreTreeBuilder.getTree();
	    System.out.print(tChoice.getNodes() + " ---add(c)---> ");
	    
	    String toAdd = "c";
	    SRETreeNode newSubNode = parserNormal.parse(toAdd);
	    newSubNode.setRate(28);
	    
	    edit.addSubnode(tChoice, newSubNode, tChoice.getRoot().getId());
        
	    System.out.println(tChoice.getNodes());

	}
	
	@Test
	public void testChoiceSubnodeDeletion() {
	    
	    String sre = "((a*0.5)[5]+b[8]+c[10])";
        parserNormal.parse(sre);
        SRETree tChoice = parserNormal.sreTreeBuilder.getTree();
        System.out.print(tChoice.getNodes() + " ---delete(b)---> ");
        
        edit.deleteSubtree(tChoice, 3);
        
        System.out.println(tChoice.getNodes());
	    
	}
	
	@Test
    public void testChoiceSubnodeDeletionIntoNewRoot() {
        
        String sre = "((a*0.5)[5]+b[8])";
        parserNormal.parse(sre);
        SRETree tChoice = parserNormal.sreTreeBuilder.getTree();
        System.out.print(tChoice.getNodes() + " ---delete(b)---> ");
        
        edit.deleteSubtree(tChoice, 3);
        
        System.out.println(tChoice.getNodes());
        
    }
	
	@Test
    public void testConcatSubnodeDeletion() {
        
        String sre = "(a:b:c)";
        parserNormal.parse(sre);
        SRETree tConcat = parserNormal.sreTreeBuilder.getTree();
        System.out.print(tConcat.getNodes() + " ---delete(a)---> ");
        
        edit.deleteSubtree(tConcat, 1);
        
        System.out.println(tConcat.getNodes());
        
    }
	
	@Test
    public void testKleeneSubnodeDeletion() {
        
        String sre = "a*0.5";
        parserNormal.parse(sre);
        SRETree tChoice = parserNormal.sreTreeBuilder.getTree();
        System.out.print(tChoice.getNodes() + " ---delete(a)---> ");
        
        edit.deleteSubtree(tChoice, 1);
        
        System.out.println(tChoice.getNodes());
        
    }
	
	@Test
    public void testKleeneSubnodeDeletion2() {
        
        String sre = "((a*0.5):b)";
        parserNormal.parse(sre);
        SRETree tChoice = parserNormal.sreTreeBuilder.getTree();
        System.out.print(tChoice.getNodes() + " ---delete(b)---> ");
        
        edit.deleteSubtree(tChoice, 3);
        
        System.out.println(tChoice.getNodes());
        
    }
	
	@Test
    public void testPlusClosureSubnodeDeletion() {
        
        String sre = "a#0.5";
        parserNormal.parse(sre);
        SRETree tChoice = parserNormal.sreTreeBuilder.getTree();
        System.out.print(tChoice.getNodes() + " ---delete(a)---> ");
        
        edit.deleteSubtree(tChoice, 1);
        
        System.out.println(tChoice.getNodes());
        
    }
	
	@Test
    public void testReplaceChoice() {
        
        String sre = "((a[5]+b[9])[14]+c[1])";
        parserNormal.parse(sre);
        SRETree tConcat = parserNormal.sreTreeBuilder.getTree();
        System.out.print(tConcat.getNodes() + " ---replace((a+b)+c, (a+b):c)---> ");
        
        edit.replaceNode(tConcat, 5, new SREConcat(new ArrayList<SRETreeNode>()));
        
        System.out.println(tConcat.getNodes());
        
    }
	
	@Test
    public void testReplaceConcat() {
        
        String sre = "(a:b:c)";
        parserNormal.parse(sre);
        SRETree tConcat = parserNormal.sreTreeBuilder.getTree();
        System.out.print(tConcat.getNodes() + " ---replace(a:b, a+b)---> ");
        
        edit.replaceNode(tConcat, 3, new SREChoice(new ArrayList<Tuple<SRETreeNode,Integer>>()));
        
        System.out.println(tConcat.getNodes());
        
    }
	
	@Test
	public void testReplaceSubtree() {
	    
	    String sre = "(a:b:c)";
        parserNormal.parse(sre);
        SRETree tConcat = parserNormal.sreTreeBuilder.getTree();
        System.out.print(tConcat.getNodes() + " ---replace(a:b, ((d*0.8)[5]+(r:a)[7]))---> ");
        
        parserNormal.parse("((d*0.8)[5]+(r:a)[7])");
        SRETreeNode replacement = parserNormal.currentAST();
        
        edit.replaceSubtree(tConcat, 3, replacement);
        
        System.out.println(tConcat.getNodes());
	    
	}
	
	@Test
	public void testUpdateLoop() {
	    
	    String sre = "a*0.5";
        parserNormal.parse(sre);
        SRETree tChoice = parserNormal.sreTreeBuilder.getTree();
        System.out.print(tChoice.getNodes() + " ---updateLoop(0.7)---> ");
        
        edit.updateLoopProbability(tChoice, 2, 0.7);
        
        System.out.println(tChoice.getNodes());
        
	}
	
	@Test
    public void testUpdateRate() {
        
        String sre = "(a[5]+b[7])";
        parserNormal.parse(sre);
        SRETree tChoice = parserNormal.sreTreeBuilder.getTree();
        System.out.print(tChoice.getNodes() + " ---updateRate(a[5],a[47])---> ");
        
        edit.updateRate(tChoice, 1, 47);
        
        System.out.println(tChoice.getNodes());
        
    }
	
	@Test
    public void testAddChoice() {
        
        String sre = "(a[5]+b[7])";
        parserNormal.parse(sre);
        SRETree tChoice = parserNormal.sreTreeBuilder.getTree();
        System.out.print(tChoice.getNodes() + " ---updateRate(a[5],a[47])---> ");
        
        tChoice.addChoice(3, parserNormal.parse("c:d"));
        
        System.out.println(tChoice.getNodes());
        
    }

}
