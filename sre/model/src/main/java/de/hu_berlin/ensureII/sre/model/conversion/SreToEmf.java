package de.hu_berlin.ensureII.sre.model.conversion;

import de.hu_berlin.ensureII.sre.model.sre.Action;
import de.hu_berlin.ensureII.sre.model.sre.Choice;
import de.hu_berlin.ensureII.sre.model.sre.Concat;
import de.hu_berlin.ensureII.sre.model.sre.KleeneClosure;
import de.hu_berlin.ensureII.sre.model.sre.Node;
import de.hu_berlin.ensureII.sre.model.sre.PlusClosure;
import de.hu_berlin.ensureII.sre.model.sre.SRE;
import de.hu_berlin.ensureII.sre.model.sre.SreFactory;
import de.hu_berlin.ensureII.sre.parser.sretree.SREAction;
import de.hu_berlin.ensureII.sre.parser.sretree.SREChoice;
import de.hu_berlin.ensureII.sre.parser.sretree.SREConcat;
import de.hu_berlin.ensureII.sre.parser.sretree.SREKleene;
import de.hu_berlin.ensureII.sre.parser.sretree.SREPlusClosure;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;

public class SreToEmf {

    public static SRE convertSre(SRETreeNode sre) {
        SRE emfSre = SreFactory.eINSTANCE.createSRE();
        emfSre.setRoot(convert(sre));
        return emfSre;
    }
    
    private static Node convert(SRETreeNode sreTreeNode) {
        Node node;
        switch(sreTreeNode.getType()) {
        case ACTION:
            node = convert_((SREAction)sreTreeNode);
            break;
        case CHOICE:
            node = convert_((SREChoice)sreTreeNode);
            break;
        case CONCAT:
            node = convert_((SREConcat)sreTreeNode);
            break;
        case KLEENE:
            node = convert_((SREKleene)sreTreeNode);
            break;
        case CLOSURE:
            node = convert_((SREPlusClosure)sreTreeNode);
            break;
        default:
           node = null;
           System.err.println("node type unknown");
           System.exit(-1);
        }
        node.setSrcState(sreTreeNode.getSourceState());
        node.setTgtState(sreTreeNode.getTargetState());
        return node;
    }
    
    private static Action convert_(SREAction node) {
        Action action = SreFactory.eINSTANCE.createAction();
        action.setAction(node.getCharacter());
        action.setId(node.getId());
        return action;
    }
    
    private static Choice convert_(SREChoice node) {
        Choice choice = SreFactory.eINSTANCE.createChoice();
        Node child;
        for(SRETreeNode treeChild : node.getChildren()) {
            child = convert(treeChild);
            child.setRate(treeChild.getRate());
            choice.getSubnodes().add(child);
        }
        choice.setId(node.getId());
        return choice;
    }
    
    private static Concat convert_(SREConcat node) {
        Concat concat = SreFactory.eINSTANCE.createConcat();
        Node child;
        for(SRETreeNode treeChild : node.getChildren()) {
            child = convert(treeChild);
            concat.getSubnodes().add(child);
        }
        concat.setId(node.getId());
        return concat;
    }
    
    private static KleeneClosure convert_(SREKleene node) {
        KleeneClosure kleene = SreFactory.eINSTANCE.createKleeneClosure();
        Node child = convert(node.getChild());
        kleene.setToLoop(child);
        kleene.setLoopProbability(node.getRepetitionRate());
        kleene.setId(node.getId());
        return kleene;
    }
    
    private static PlusClosure convert_(SREPlusClosure node) {
        PlusClosure plusClos = SreFactory.eINSTANCE.createPlusClosure();
        Node child = convert(node.getChild());
        plusClos.setToLoop(child);
        plusClos.setLoopProbability(node.getRepetitionRate());
        plusClos.setId(node.getId());
        return plusClos;
    }
    
}
