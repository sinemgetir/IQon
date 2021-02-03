/**
 */
package transitiongraph.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import transitiongraph.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TransitiongraphFactoryImpl extends EFactoryImpl implements TransitiongraphFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static TransitiongraphFactory init() {
        try {
            TransitiongraphFactory theTransitiongraphFactory = (TransitiongraphFactory)EPackage.Registry.INSTANCE.getEFactory(TransitiongraphPackage.eNS_URI);
            if (theTransitiongraphFactory != null) {
                return theTransitiongraphFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new TransitiongraphFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TransitiongraphFactoryImpl() {
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
            case TransitiongraphPackage.TRANSITION_GRAPH: return createTransitionGraph();
            case TransitiongraphPackage.STATE: return createState();
            case TransitiongraphPackage.TRANSITION: return createTransition();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TransitionGraph createTransitionGraph() {
        TransitionGraphImpl transitionGraph = new TransitionGraphImpl();
        return transitionGraph;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public State createState() {
        StateImpl state = new StateImpl();
        return state;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Transition createTransition() {
        TransitionImpl transition = new TransitionImpl();
        return transition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TransitiongraphPackage getTransitiongraphPackage() {
        return (TransitiongraphPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static TransitiongraphPackage getPackage() {
        return TransitiongraphPackage.eINSTANCE;
    }

} //TransitiongraphFactoryImpl
