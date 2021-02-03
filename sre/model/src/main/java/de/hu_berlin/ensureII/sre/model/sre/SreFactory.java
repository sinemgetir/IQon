/**
 */
package de.hu_berlin.ensureII.sre.model.sre;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.hu_berlin.ensureII.sre.model.sre.SrePackage
 * @generated
 */
public interface SreFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    SreFactory eINSTANCE = de.hu_berlin.ensureII.sre.model.sre.impl.SreFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Node</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Node</em>'.
     * @generated
     */
    Node createNode();

    /**
     * Returns a new object of class '<em>Action</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Action</em>'.
     * @generated
     */
    Action createAction();

    /**
     * Returns a new object of class '<em>Choice</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Choice</em>'.
     * @generated
     */
    Choice createChoice();

    /**
     * Returns a new object of class '<em>Concat</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Concat</em>'.
     * @generated
     */
    Concat createConcat();

    /**
     * Returns a new object of class '<em>Kleene Closure</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Kleene Closure</em>'.
     * @generated
     */
    KleeneClosure createKleeneClosure();

    /**
     * Returns a new object of class '<em>Plus Closure</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Plus Closure</em>'.
     * @generated
     */
    PlusClosure createPlusClosure();

    /**
     * Returns a new object of class '<em>SRE</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>SRE</em>'.
     * @generated
     */
    SRE createSRE();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    SrePackage getSrePackage();

} //SreFactory
