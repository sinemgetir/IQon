/**
 */
package de.hu_berlin.ensureII.sre.model.sre.impl;

import de.hu_berlin.ensureII.sre.model.sre.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SreFactoryImpl extends EFactoryImpl implements SreFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SreFactory init() {
        try {
            SreFactory theSreFactory = (SreFactory)EPackage.Registry.INSTANCE.getEFactory(SrePackage.eNS_URI);
            if (theSreFactory != null) {
                return theSreFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new SreFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SreFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case SrePackage.NODE: return createNode();
            case SrePackage.ACTION: return createAction();
            case SrePackage.CHOICE: return createChoice();
            case SrePackage.CONCAT: return createConcat();
            case SrePackage.KLEENE_CLOSURE: return createKleeneClosure();
            case SrePackage.PLUS_CLOSURE: return createPlusClosure();
            case SrePackage.SRE: return createSRE();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Node createNode() {
        NodeImpl node = new NodeImpl();
        return node;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Action createAction() {
        ActionImpl action = new ActionImpl();
        return action;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Choice createChoice() {
        ChoiceImpl choice = new ChoiceImpl();
        return choice;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Concat createConcat() {
        ConcatImpl concat = new ConcatImpl();
        return concat;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public KleeneClosure createKleeneClosure() {
        KleeneClosureImpl kleeneClosure = new KleeneClosureImpl();
        return kleeneClosure;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PlusClosure createPlusClosure() {
        PlusClosureImpl plusClosure = new PlusClosureImpl();
        return plusClosure;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SRE createSRE() {
        SREImpl sre = new SREImpl();
        return sre;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SrePackage getSrePackage() {
        return (SrePackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static SrePackage getPackage() {
        return SrePackage.eINSTANCE;
    }

} //SreFactoryImpl
