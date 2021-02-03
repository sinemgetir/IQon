/**
 */
package de.hu_berlin.ensureII.sre.model.sre;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.hu_berlin.ensureII.sre.model.sre.SreFactory
 * @model kind="package"
 * @generated
 */
public interface SrePackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "sre";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "sre";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "sre";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    SrePackage eINSTANCE = de.hu_berlin.ensureII.sre.model.sre.impl.SrePackageImpl.init();

    /**
     * The meta object id for the '{@link de.hu_berlin.ensureII.sre.model.sre.impl.NodeImpl <em>Node</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.hu_berlin.ensureII.sre.model.sre.impl.NodeImpl
     * @see de.hu_berlin.ensureII.sre.model.sre.impl.SrePackageImpl#getNode()
     * @generated
     */
    int NODE = 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE__ID = 0;

    /**
     * The feature id for the '<em><b>Rate</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE__RATE = 1;

    /**
     * The feature id for the '<em><b>Src State</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE__SRC_STATE = 2;

    /**
     * The feature id for the '<em><b>Tgt State</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE__TGT_STATE = 3;

    /**
     * The number of structural features of the '<em>Node</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE_FEATURE_COUNT = 4;

    /**
     * The number of operations of the '<em>Node</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link de.hu_berlin.ensureII.sre.model.sre.impl.ActionImpl <em>Action</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.hu_berlin.ensureII.sre.model.sre.impl.ActionImpl
     * @see de.hu_berlin.ensureII.sre.model.sre.impl.SrePackageImpl#getAction()
     * @generated
     */
    int ACTION = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTION__ID = NODE__ID;

    /**
     * The feature id for the '<em><b>Rate</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTION__RATE = NODE__RATE;

    /**
     * The feature id for the '<em><b>Src State</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTION__SRC_STATE = NODE__SRC_STATE;

    /**
     * The feature id for the '<em><b>Tgt State</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTION__TGT_STATE = NODE__TGT_STATE;

    /**
     * The feature id for the '<em><b>Action</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTION__ACTION = NODE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Action</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTION_FEATURE_COUNT = NODE_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Action</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTION_OPERATION_COUNT = NODE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link de.hu_berlin.ensureII.sre.model.sre.impl.ChoiceImpl <em>Choice</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.hu_berlin.ensureII.sre.model.sre.impl.ChoiceImpl
     * @see de.hu_berlin.ensureII.sre.model.sre.impl.SrePackageImpl#getChoice()
     * @generated
     */
    int CHOICE = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHOICE__ID = NODE__ID;

    /**
     * The feature id for the '<em><b>Rate</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHOICE__RATE = NODE__RATE;

    /**
     * The feature id for the '<em><b>Src State</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHOICE__SRC_STATE = NODE__SRC_STATE;

    /**
     * The feature id for the '<em><b>Tgt State</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHOICE__TGT_STATE = NODE__TGT_STATE;

    /**
     * The feature id for the '<em><b>Subnodes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHOICE__SUBNODES = NODE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Choice</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHOICE_FEATURE_COUNT = NODE_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Choice</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHOICE_OPERATION_COUNT = NODE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link de.hu_berlin.ensureII.sre.model.sre.impl.ConcatImpl <em>Concat</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.hu_berlin.ensureII.sre.model.sre.impl.ConcatImpl
     * @see de.hu_berlin.ensureII.sre.model.sre.impl.SrePackageImpl#getConcat()
     * @generated
     */
    int CONCAT = 3;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONCAT__ID = NODE__ID;

    /**
     * The feature id for the '<em><b>Rate</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONCAT__RATE = NODE__RATE;

    /**
     * The feature id for the '<em><b>Src State</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONCAT__SRC_STATE = NODE__SRC_STATE;

    /**
     * The feature id for the '<em><b>Tgt State</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONCAT__TGT_STATE = NODE__TGT_STATE;

    /**
     * The feature id for the '<em><b>Subnodes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONCAT__SUBNODES = NODE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Concat</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONCAT_FEATURE_COUNT = NODE_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Concat</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONCAT_OPERATION_COUNT = NODE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link de.hu_berlin.ensureII.sre.model.sre.impl.KleeneClosureImpl <em>Kleene Closure</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.hu_berlin.ensureII.sre.model.sre.impl.KleeneClosureImpl
     * @see de.hu_berlin.ensureII.sre.model.sre.impl.SrePackageImpl#getKleeneClosure()
     * @generated
     */
    int KLEENE_CLOSURE = 4;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int KLEENE_CLOSURE__ID = NODE__ID;

    /**
     * The feature id for the '<em><b>Rate</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int KLEENE_CLOSURE__RATE = NODE__RATE;

    /**
     * The feature id for the '<em><b>Src State</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int KLEENE_CLOSURE__SRC_STATE = NODE__SRC_STATE;

    /**
     * The feature id for the '<em><b>Tgt State</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int KLEENE_CLOSURE__TGT_STATE = NODE__TGT_STATE;

    /**
     * The feature id for the '<em><b>Loop Probability</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int KLEENE_CLOSURE__LOOP_PROBABILITY = NODE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>To Loop</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int KLEENE_CLOSURE__TO_LOOP = NODE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Kleene Closure</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int KLEENE_CLOSURE_FEATURE_COUNT = NODE_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Kleene Closure</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int KLEENE_CLOSURE_OPERATION_COUNT = NODE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link de.hu_berlin.ensureII.sre.model.sre.impl.PlusClosureImpl <em>Plus Closure</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.hu_berlin.ensureII.sre.model.sre.impl.PlusClosureImpl
     * @see de.hu_berlin.ensureII.sre.model.sre.impl.SrePackageImpl#getPlusClosure()
     * @generated
     */
    int PLUS_CLOSURE = 5;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PLUS_CLOSURE__ID = NODE__ID;

    /**
     * The feature id for the '<em><b>Rate</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PLUS_CLOSURE__RATE = NODE__RATE;

    /**
     * The feature id for the '<em><b>Src State</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PLUS_CLOSURE__SRC_STATE = NODE__SRC_STATE;

    /**
     * The feature id for the '<em><b>Tgt State</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PLUS_CLOSURE__TGT_STATE = NODE__TGT_STATE;

    /**
     * The feature id for the '<em><b>Loop Probability</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PLUS_CLOSURE__LOOP_PROBABILITY = NODE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>To Loop</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PLUS_CLOSURE__TO_LOOP = NODE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Plus Closure</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PLUS_CLOSURE_FEATURE_COUNT = NODE_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Plus Closure</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PLUS_CLOSURE_OPERATION_COUNT = NODE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link de.hu_berlin.ensureII.sre.model.sre.impl.SREImpl <em>SRE</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.hu_berlin.ensureII.sre.model.sre.impl.SREImpl
     * @see de.hu_berlin.ensureII.sre.model.sre.impl.SrePackageImpl#getSRE()
     * @generated
     */
    int SRE = 6;

    /**
     * The feature id for the '<em><b>Root</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SRE__ROOT = 0;

    /**
     * The number of structural features of the '<em>SRE</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SRE_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>SRE</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SRE_OPERATION_COUNT = 0;


    /**
     * Returns the meta object for class '{@link de.hu_berlin.ensureII.sre.model.sre.Node <em>Node</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Node</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.Node
     * @generated
     */
    EClass getNode();

    /**
     * Returns the meta object for the attribute '{@link de.hu_berlin.ensureII.sre.model.sre.Node#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.Node#getId()
     * @see #getNode()
     * @generated
     */
    EAttribute getNode_Id();

    /**
     * Returns the meta object for the attribute '{@link de.hu_berlin.ensureII.sre.model.sre.Node#getRate <em>Rate</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Rate</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.Node#getRate()
     * @see #getNode()
     * @generated
     */
    EAttribute getNode_Rate();

    /**
     * Returns the meta object for the attribute '{@link de.hu_berlin.ensureII.sre.model.sre.Node#getSrcState <em>Src State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Src State</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.Node#getSrcState()
     * @see #getNode()
     * @generated
     */
    EAttribute getNode_SrcState();

    /**
     * Returns the meta object for the attribute '{@link de.hu_berlin.ensureII.sre.model.sre.Node#getTgtState <em>Tgt State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Tgt State</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.Node#getTgtState()
     * @see #getNode()
     * @generated
     */
    EAttribute getNode_TgtState();

    /**
     * Returns the meta object for class '{@link de.hu_berlin.ensureII.sre.model.sre.Action <em>Action</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Action</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.Action
     * @generated
     */
    EClass getAction();

    /**
     * Returns the meta object for the attribute '{@link de.hu_berlin.ensureII.sre.model.sre.Action#getAction <em>Action</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Action</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.Action#getAction()
     * @see #getAction()
     * @generated
     */
    EAttribute getAction_Action();

    /**
     * Returns the meta object for class '{@link de.hu_berlin.ensureII.sre.model.sre.Choice <em>Choice</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Choice</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.Choice
     * @generated
     */
    EClass getChoice();

    /**
     * Returns the meta object for the containment reference list '{@link de.hu_berlin.ensureII.sre.model.sre.Choice#getSubnodes <em>Subnodes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Subnodes</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.Choice#getSubnodes()
     * @see #getChoice()
     * @generated
     */
    EReference getChoice_Subnodes();

    /**
     * Returns the meta object for class '{@link de.hu_berlin.ensureII.sre.model.sre.Concat <em>Concat</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Concat</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.Concat
     * @generated
     */
    EClass getConcat();

    /**
     * Returns the meta object for the containment reference list '{@link de.hu_berlin.ensureII.sre.model.sre.Concat#getSubnodes <em>Subnodes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Subnodes</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.Concat#getSubnodes()
     * @see #getConcat()
     * @generated
     */
    EReference getConcat_Subnodes();

    /**
     * Returns the meta object for class '{@link de.hu_berlin.ensureII.sre.model.sre.KleeneClosure <em>Kleene Closure</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Kleene Closure</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.KleeneClosure
     * @generated
     */
    EClass getKleeneClosure();

    /**
     * Returns the meta object for the attribute '{@link de.hu_berlin.ensureII.sre.model.sre.KleeneClosure#getLoopProbability <em>Loop Probability</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Loop Probability</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.KleeneClosure#getLoopProbability()
     * @see #getKleeneClosure()
     * @generated
     */
    EAttribute getKleeneClosure_LoopProbability();

    /**
     * Returns the meta object for the containment reference '{@link de.hu_berlin.ensureII.sre.model.sre.KleeneClosure#getToLoop <em>To Loop</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>To Loop</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.KleeneClosure#getToLoop()
     * @see #getKleeneClosure()
     * @generated
     */
    EReference getKleeneClosure_ToLoop();

    /**
     * Returns the meta object for class '{@link de.hu_berlin.ensureII.sre.model.sre.PlusClosure <em>Plus Closure</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Plus Closure</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.PlusClosure
     * @generated
     */
    EClass getPlusClosure();

    /**
     * Returns the meta object for the attribute '{@link de.hu_berlin.ensureII.sre.model.sre.PlusClosure#getLoopProbability <em>Loop Probability</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Loop Probability</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.PlusClosure#getLoopProbability()
     * @see #getPlusClosure()
     * @generated
     */
    EAttribute getPlusClosure_LoopProbability();

    /**
     * Returns the meta object for the containment reference '{@link de.hu_berlin.ensureII.sre.model.sre.PlusClosure#getToLoop <em>To Loop</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>To Loop</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.PlusClosure#getToLoop()
     * @see #getPlusClosure()
     * @generated
     */
    EReference getPlusClosure_ToLoop();

    /**
     * Returns the meta object for class '{@link de.hu_berlin.ensureII.sre.model.sre.SRE <em>SRE</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>SRE</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.SRE
     * @generated
     */
    EClass getSRE();

    /**
     * Returns the meta object for the containment reference '{@link de.hu_berlin.ensureII.sre.model.sre.SRE#getRoot <em>Root</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Root</em>'.
     * @see de.hu_berlin.ensureII.sre.model.sre.SRE#getRoot()
     * @see #getSRE()
     * @generated
     */
    EReference getSRE_Root();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    SreFactory getSreFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each operation of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link de.hu_berlin.ensureII.sre.model.sre.impl.NodeImpl <em>Node</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see de.hu_berlin.ensureII.sre.model.sre.impl.NodeImpl
         * @see de.hu_berlin.ensureII.sre.model.sre.impl.SrePackageImpl#getNode()
         * @generated
         */
        EClass NODE = eINSTANCE.getNode();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NODE__ID = eINSTANCE.getNode_Id();

        /**
         * The meta object literal for the '<em><b>Rate</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NODE__RATE = eINSTANCE.getNode_Rate();

        /**
         * The meta object literal for the '<em><b>Src State</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NODE__SRC_STATE = eINSTANCE.getNode_SrcState();

        /**
         * The meta object literal for the '<em><b>Tgt State</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NODE__TGT_STATE = eINSTANCE.getNode_TgtState();

        /**
         * The meta object literal for the '{@link de.hu_berlin.ensureII.sre.model.sre.impl.ActionImpl <em>Action</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see de.hu_berlin.ensureII.sre.model.sre.impl.ActionImpl
         * @see de.hu_berlin.ensureII.sre.model.sre.impl.SrePackageImpl#getAction()
         * @generated
         */
        EClass ACTION = eINSTANCE.getAction();

        /**
         * The meta object literal for the '<em><b>Action</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ACTION__ACTION = eINSTANCE.getAction_Action();

        /**
         * The meta object literal for the '{@link de.hu_berlin.ensureII.sre.model.sre.impl.ChoiceImpl <em>Choice</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see de.hu_berlin.ensureII.sre.model.sre.impl.ChoiceImpl
         * @see de.hu_berlin.ensureII.sre.model.sre.impl.SrePackageImpl#getChoice()
         * @generated
         */
        EClass CHOICE = eINSTANCE.getChoice();

        /**
         * The meta object literal for the '<em><b>Subnodes</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHOICE__SUBNODES = eINSTANCE.getChoice_Subnodes();

        /**
         * The meta object literal for the '{@link de.hu_berlin.ensureII.sre.model.sre.impl.ConcatImpl <em>Concat</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see de.hu_berlin.ensureII.sre.model.sre.impl.ConcatImpl
         * @see de.hu_berlin.ensureII.sre.model.sre.impl.SrePackageImpl#getConcat()
         * @generated
         */
        EClass CONCAT = eINSTANCE.getConcat();

        /**
         * The meta object literal for the '<em><b>Subnodes</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CONCAT__SUBNODES = eINSTANCE.getConcat_Subnodes();

        /**
         * The meta object literal for the '{@link de.hu_berlin.ensureII.sre.model.sre.impl.KleeneClosureImpl <em>Kleene Closure</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see de.hu_berlin.ensureII.sre.model.sre.impl.KleeneClosureImpl
         * @see de.hu_berlin.ensureII.sre.model.sre.impl.SrePackageImpl#getKleeneClosure()
         * @generated
         */
        EClass KLEENE_CLOSURE = eINSTANCE.getKleeneClosure();

        /**
         * The meta object literal for the '<em><b>Loop Probability</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute KLEENE_CLOSURE__LOOP_PROBABILITY = eINSTANCE.getKleeneClosure_LoopProbability();

        /**
         * The meta object literal for the '<em><b>To Loop</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference KLEENE_CLOSURE__TO_LOOP = eINSTANCE.getKleeneClosure_ToLoop();

        /**
         * The meta object literal for the '{@link de.hu_berlin.ensureII.sre.model.sre.impl.PlusClosureImpl <em>Plus Closure</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see de.hu_berlin.ensureII.sre.model.sre.impl.PlusClosureImpl
         * @see de.hu_berlin.ensureII.sre.model.sre.impl.SrePackageImpl#getPlusClosure()
         * @generated
         */
        EClass PLUS_CLOSURE = eINSTANCE.getPlusClosure();

        /**
         * The meta object literal for the '<em><b>Loop Probability</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PLUS_CLOSURE__LOOP_PROBABILITY = eINSTANCE.getPlusClosure_LoopProbability();

        /**
         * The meta object literal for the '<em><b>To Loop</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PLUS_CLOSURE__TO_LOOP = eINSTANCE.getPlusClosure_ToLoop();

        /**
         * The meta object literal for the '{@link de.hu_berlin.ensureII.sre.model.sre.impl.SREImpl <em>SRE</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see de.hu_berlin.ensureII.sre.model.sre.impl.SREImpl
         * @see de.hu_berlin.ensureII.sre.model.sre.impl.SrePackageImpl#getSRE()
         * @generated
         */
        EClass SRE = eINSTANCE.getSRE();

        /**
         * The meta object literal for the '<em><b>Root</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SRE__ROOT = eINSTANCE.getSRE_Root();

    }

} //SrePackage
