/**
 */
package de.hu_berlin.ensureII.sre.model.sre.util;

import de.hu_berlin.ensureII.sre.model.sre.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.hu_berlin.ensureII.sre.model.sre.SrePackage
 * @generated
 */
public class SreAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static SrePackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SreAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = SrePackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject)object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SreSwitch<Adapter> modelSwitch =
        new SreSwitch<Adapter>() {
            @Override
            public Adapter caseNode(Node object) {
                return createNodeAdapter();
            }
            @Override
            public Adapter caseAction(Action object) {
                return createActionAdapter();
            }
            @Override
            public Adapter caseChoice(Choice object) {
                return createChoiceAdapter();
            }
            @Override
            public Adapter caseConcat(Concat object) {
                return createConcatAdapter();
            }
            @Override
            public Adapter caseKleeneClosure(KleeneClosure object) {
                return createKleeneClosureAdapter();
            }
            @Override
            public Adapter casePlusClosure(PlusClosure object) {
                return createPlusClosureAdapter();
            }
            @Override
            public Adapter caseSRE(SRE object) {
                return createSREAdapter();
            }
            @Override
            public Adapter defaultCase(EObject object) {
                return createEObjectAdapter();
            }
        };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return modelSwitch.doSwitch((EObject)target);
    }


    /**
     * Creates a new adapter for an object of class '{@link de.hu_berlin.ensureII.sre.model.sre.Node <em>Node</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see de.hu_berlin.ensureII.sre.model.sre.Node
     * @generated
     */
    public Adapter createNodeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link de.hu_berlin.ensureII.sre.model.sre.Action <em>Action</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see de.hu_berlin.ensureII.sre.model.sre.Action
     * @generated
     */
    public Adapter createActionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link de.hu_berlin.ensureII.sre.model.sre.Choice <em>Choice</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see de.hu_berlin.ensureII.sre.model.sre.Choice
     * @generated
     */
    public Adapter createChoiceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link de.hu_berlin.ensureII.sre.model.sre.Concat <em>Concat</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see de.hu_berlin.ensureII.sre.model.sre.Concat
     * @generated
     */
    public Adapter createConcatAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link de.hu_berlin.ensureII.sre.model.sre.KleeneClosure <em>Kleene Closure</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see de.hu_berlin.ensureII.sre.model.sre.KleeneClosure
     * @generated
     */
    public Adapter createKleeneClosureAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link de.hu_berlin.ensureII.sre.model.sre.PlusClosure <em>Plus Closure</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see de.hu_berlin.ensureII.sre.model.sre.PlusClosure
     * @generated
     */
    public Adapter createPlusClosureAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link de.hu_berlin.ensureII.sre.model.sre.SRE <em>SRE</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see de.hu_berlin.ensureII.sre.model.sre.SRE
     * @generated
     */
    public Adapter createSREAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} //SreAdapterFactory
