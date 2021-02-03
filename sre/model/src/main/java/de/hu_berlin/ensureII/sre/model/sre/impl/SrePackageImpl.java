/**
 */
package de.hu_berlin.ensureII.sre.model.sre.impl;

import de.hu_berlin.ensureII.sre.model.sre.Action;
import de.hu_berlin.ensureII.sre.model.sre.Choice;
import de.hu_berlin.ensureII.sre.model.sre.Concat;
import de.hu_berlin.ensureII.sre.model.sre.KleeneClosure;
import de.hu_berlin.ensureII.sre.model.sre.Node;
import de.hu_berlin.ensureII.sre.model.sre.PlusClosure;
import de.hu_berlin.ensureII.sre.model.sre.SreFactory;
import de.hu_berlin.ensureII.sre.model.sre.SrePackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SrePackageImpl extends EPackageImpl implements SrePackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass nodeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass actionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass choiceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass concatEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass kleeneClosureEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass plusClosureEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass sreEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see de.hu_berlin.ensureII.sre.model.sre.SrePackage#eNS_URI
     * @see #init()
     * @generated
     */
    private SrePackageImpl() {
        super(eNS_URI, SreFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link SrePackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static SrePackage init() {
        if (isInited) return (SrePackage)EPackage.Registry.INSTANCE.getEPackage(SrePackage.eNS_URI);

        // Obtain or create and register package
        SrePackageImpl theSrePackage = (SrePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SrePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SrePackageImpl());

        isInited = true;

        // Create package meta-data objects
        theSrePackage.createPackageContents();

        // Initialize created meta-data
        theSrePackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theSrePackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(SrePackage.eNS_URI, theSrePackage);
        return theSrePackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getNode() {
        return nodeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNode_Id() {
        return (EAttribute)nodeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNode_Rate() {
        return (EAttribute)nodeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNode_SrcState() {
        return (EAttribute)nodeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNode_TgtState() {
        return (EAttribute)nodeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAction() {
        return actionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAction_Action() {
        return (EAttribute)actionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getChoice() {
        return choiceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChoice_Subnodes() {
        return (EReference)choiceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getConcat() {
        return concatEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getConcat_Subnodes() {
        return (EReference)concatEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getKleeneClosure() {
        return kleeneClosureEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getKleeneClosure_LoopProbability() {
        return (EAttribute)kleeneClosureEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getKleeneClosure_ToLoop() {
        return (EReference)kleeneClosureEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPlusClosure() {
        return plusClosureEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPlusClosure_LoopProbability() {
        return (EAttribute)plusClosureEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPlusClosure_ToLoop() {
        return (EReference)plusClosureEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSRE() {
        return sreEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSRE_Root() {
        return (EReference)sreEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SreFactory getSreFactory() {
        return (SreFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        nodeEClass = createEClass(NODE);
        createEAttribute(nodeEClass, NODE__ID);
        createEAttribute(nodeEClass, NODE__RATE);
        createEAttribute(nodeEClass, NODE__SRC_STATE);
        createEAttribute(nodeEClass, NODE__TGT_STATE);

        actionEClass = createEClass(ACTION);
        createEAttribute(actionEClass, ACTION__ACTION);

        choiceEClass = createEClass(CHOICE);
        createEReference(choiceEClass, CHOICE__SUBNODES);

        concatEClass = createEClass(CONCAT);
        createEReference(concatEClass, CONCAT__SUBNODES);

        kleeneClosureEClass = createEClass(KLEENE_CLOSURE);
        createEAttribute(kleeneClosureEClass, KLEENE_CLOSURE__LOOP_PROBABILITY);
        createEReference(kleeneClosureEClass, KLEENE_CLOSURE__TO_LOOP);

        plusClosureEClass = createEClass(PLUS_CLOSURE);
        createEAttribute(plusClosureEClass, PLUS_CLOSURE__LOOP_PROBABILITY);
        createEReference(plusClosureEClass, PLUS_CLOSURE__TO_LOOP);

        sreEClass = createEClass(SRE);
        createEReference(sreEClass, SRE__ROOT);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        actionEClass.getESuperTypes().add(this.getNode());
        choiceEClass.getESuperTypes().add(this.getNode());
        concatEClass.getESuperTypes().add(this.getNode());
        kleeneClosureEClass.getESuperTypes().add(this.getNode());
        plusClosureEClass.getESuperTypes().add(this.getNode());

        // Initialize classes, features, and operations; add parameters
        initEClass(nodeEClass, Node.class, "Node", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getNode_Id(), ecorePackage.getEInt(), "id", "0", 0, 1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getNode_Rate(), ecorePackage.getEInt(), "rate", null, 0, 1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getNode_SrcState(), ecorePackage.getEInt(), "srcState", "0", 0, 1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getNode_TgtState(), ecorePackage.getEInt(), "tgtState", "0", 0, 1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(actionEClass, Action.class, "Action", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAction_Action(), ecorePackage.getEString(), "action", null, 0, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(choiceEClass, Choice.class, "Choice", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getChoice_Subnodes(), this.getNode(), null, "subnodes", null, 1, -1, Choice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(concatEClass, Concat.class, "Concat", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getConcat_Subnodes(), this.getNode(), null, "subnodes", null, 1, -1, Concat.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(kleeneClosureEClass, KleeneClosure.class, "KleeneClosure", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getKleeneClosure_LoopProbability(), ecorePackage.getEDouble(), "loopProbability", "0", 0, 1, KleeneClosure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getKleeneClosure_ToLoop(), this.getNode(), null, "toLoop", null, 1, 1, KleeneClosure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(plusClosureEClass, PlusClosure.class, "PlusClosure", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getPlusClosure_LoopProbability(), ecorePackage.getEDouble(), "loopProbability", "0", 0, 1, PlusClosure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getPlusClosure_ToLoop(), this.getNode(), null, "toLoop", null, 1, 1, PlusClosure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(sreEClass, de.hu_berlin.ensureII.sre.model.sre.SRE.class, "SRE", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getSRE_Root(), this.getNode(), null, "root", null, 1, 1, de.hu_berlin.ensureII.sre.model.sre.SRE.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Create resource
        createResource(eNS_URI);
    }

} //SrePackageImpl
