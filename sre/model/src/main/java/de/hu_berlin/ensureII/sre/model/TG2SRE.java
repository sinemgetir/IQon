package de.hu_berlin.ensureII.sre.model;

import java.io.File;

import de.hu_berlin.ensureII.sre.model.conversion.EmfToSre;
import de.hu_berlin.ensureII.sre.model.conversion.SreToEmf;
import de.hu_berlin.ensureII.sre.model.conversion.Tra2Emf;
import de.hu_berlin.ensureII.sre.model.emf.EmfStorage;
import de.hu_berlin.ensureII.sre.model.sre.SRE;
import de.hu_berlin.ensureII.sre.model.transformation.StateElimination;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;
import transitiongraph.TransitionGraph;

public class TG2SRE {

    public static void main(String[] args) {
        File f = new File("src/main/resources/tra/leader/leader3_2.tra");
        TransitionGraph tg = Tra2Emf.tra2emf(f);
        //TransitionGraph tg = EmfStorage.loadFromXMI("src/main/resources/xmi/leader/leader3_4.xmi");
        StateElimination foo = new StateElimination(tg);
        foo.run();
        System.out.println("done");
        SRE sre = SreToEmf.convertSre(foo.getSRE());
        EmfStorage.storeSreEMFasXMI("src/main/resources/xmi/sre/leader", "leader3_2sre", sre);
        
        getSreFromXMI();
    }

    /**
     * Example for reading an SRE from an XMI file and turning it into 
     * an SRETreeNode from the parser
     */
    private static void getSreFromXMI() {
        SRE sre = EmfStorage.loadSreFromXMI("src/main/resources/xmi/sre/leader/leader3_2sre.xmi");
        SRETreeNode sreTree = EmfToSre.convertSre(sre);
        System.out.println(sreTree);
    }
    
}
