package de.hu_berlin.ensureII.sre.model.emf;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import de.hu_berlin.ensureII.sre.model.sre.SRE;
import de.hu_berlin.ensureII.sre.model.sre.SrePackage;
import transitiongraph.TransitionGraph;
import transitiongraph.TransitiongraphPackage;

public class EmfStorage {

    public static void storeTransitionGraphEMFasXMI(String outputDir, String outputFilename, TransitionGraph toStore) {
        // Initialize the model
        TransitiongraphPackage.eINSTANCE.eClass();

        // Register the XMI resource factory for the .xmi extension
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("xmi", new XMIResourceFactoryImpl());
        
        ResourceSet resSet = new ResourceSetImpl();
        Resource resource = resSet.createResource(URI.createURI(outputDir + File.separator
                + outputFilename + ".xmi"));
        resource.getContents().add(toStore);
        try {
            resource.save(Collections.EMPTY_MAP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void storeSreEMFasXMI(String outputDir, String outputFilename, SRE toStore) {
        // Initialize the model
        SrePackage.eINSTANCE.eClass();

        // Register the XMI resource factory for the .xmi extension
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("xmi", new XMIResourceFactoryImpl());
        
        ResourceSet resSet = new ResourceSetImpl();
        Resource resource = resSet.createResource(URI.createURI(outputDir + File.separator
                + outputFilename + ".xmi"));
        resource.getContents().add(toStore);
        try {
            resource.save(Collections.EMPTY_MAP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static TransitionGraph loadTransitionGraphFromXMI(String xmiFile) {
        // Initialize the model
        TransitiongraphPackage.eINSTANCE.eClass();

        // Register the XMI resource factory for the .xmi extension
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("xmi", new XMIResourceFactoryImpl());

        // Obtain a new resource set
        ResourceSet resSet = new ResourceSetImpl();

        // Get the resource
        Resource resource = resSet.getResource(URI.createURI(xmiFile), true);
        
        // Get the first model element and cast it to the right type, in my
        // example everything is hierarchical included in this first node
        TransitionGraph tg = (TransitionGraph) resource.getContents().get(0);
        return tg;
    }
    
    public static SRE loadSreFromXMI(String xmiFile) {
        // Initialize the model
        SrePackage.eINSTANCE.eClass();

        // Register the XMI resource factory for the .xmi extension
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("xmi", new XMIResourceFactoryImpl());

        // Obtain a new resource set
        ResourceSet resSet = new ResourceSetImpl();

        // Get the resource
        Resource resource = resSet.getResource(URI.createURI(xmiFile), true);
        
        // Get the first model element and cast it to the right type, in my
        // example everything is hierarchical included in this first node
        SRE sre = (SRE) resource.getContents().get(0);
        return sre;
    }
    
}
