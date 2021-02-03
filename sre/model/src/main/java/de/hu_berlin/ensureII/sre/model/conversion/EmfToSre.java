package de.hu_berlin.ensureII.sre.model.conversion;

import java.util.ArrayList;

import de.hu_berlin.ensureII.sre.model.sre.Action;
import de.hu_berlin.ensureII.sre.model.sre.Choice;
import de.hu_berlin.ensureII.sre.model.sre.Concat;
import de.hu_berlin.ensureII.sre.model.sre.KleeneClosure;
import de.hu_berlin.ensureII.sre.model.sre.Node;
import de.hu_berlin.ensureII.sre.model.sre.PlusClosure;
import de.hu_berlin.ensureII.sre.model.sre.SRE;
import de.hu_berlin.ensureII.sre.parser.sretree.SREAction;
import de.hu_berlin.ensureII.sre.parser.sretree.SREChoice;
import de.hu_berlin.ensureII.sre.parser.sretree.SREConcat;
import de.hu_berlin.ensureII.sre.parser.sretree.SREKleene;
import de.hu_berlin.ensureII.sre.parser.sretree.SREPlusClosure;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;
import de.hu_berlin.ensureII.sre.parser.sretree.Tuple;

public class EmfToSre {

    public static SRETreeNode convertSre(SRE sre) {
        SRETreeNode sreTree = convert(sre.getRoot());
        return sreTree;
    }
    
    private static SRETreeNode convert(Node sre) {
        SRETreeNode sreTree;
        
        if(sre instanceof Action) {
            sreTree = convert_((Action)sre);
        }else if(sre instanceof Choice) {
            sreTree = convert_((Choice)sre);
        }else if(sre instanceof Concat) {
            sreTree = convert_((Concat)sre);
        }else if(sre instanceof KleeneClosure) {
            sreTree = convert_((KleeneClosure)sre);
        }else if(sre instanceof PlusClosure) {
            sreTree = convert_((PlusClosure)sre);
        }else {
            sreTree = null;
            System.err.println("node type unknown");
            System.exit(-1);
        }
        sreTree.setSourceState(sre.getSrcState());
        sreTree.setTargetState(sre.getTgtState());
        return sreTree;
    }
    
    private static SREAction convert_(Action node) {
        SREAction action = new SREAction(node.getAction());
        action.setID(node.getId());
        return action;
    }
    
    private static SREChoice convert_(Choice node) {
        
        SRETreeNode sreChild;
        Tuple<SRETreeNode, Integer> ratedChild;
        ArrayList<Tuple<SRETreeNode, Integer>> children = new ArrayList<Tuple<SRETreeNode, Integer>>();

        for(Node child : node.getSubnodes()) {
            sreChild = convert(child);
            ratedChild = new Tuple<SRETreeNode, Integer>(sreChild, child.getRate());
            children.add(ratedChild);
        }
        
        SREChoice choice = new SREChoice(children);
        choice.setID(node.getId());
                
        return choice;
    }
    
    private static SREConcat convert_(Concat node) {
        
        ArrayList<SRETreeNode> children = new ArrayList<SRETreeNode>();
        for(Node child : node.getSubnodes()) {
            children.add(convert(child));
        }
        
        SREConcat concat = new SREConcat(children);
        concat.setID(node.getId());
        
        return concat;
    }
    
    private static SREKleene convert_(KleeneClosure node) {
        SREKleene kleene = new SREKleene(convert(node.getToLoop()), node.getLoopProbability());
        kleene.setID(node.getId());
        return kleene;
    }
    
    private static SREPlusClosure convert_(PlusClosure node) {
        SREPlusClosure plusClos = new SREPlusClosure(convert(node.getToLoop()), node.getLoopProbability());
        plusClos.setID(node.getId());
        return plusClos;
    }
    
}
