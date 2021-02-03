/**
 */
package de.hu_berlin.ensureII.sre.model.sre;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.ensureII.sre.model.sre.Node#getId <em>Id</em>}</li>
 *   <li>{@link de.hu_berlin.ensureII.sre.model.sre.Node#getRate <em>Rate</em>}</li>
 *   <li>{@link de.hu_berlin.ensureII.sre.model.sre.Node#getSrcState <em>Src State</em>}</li>
 *   <li>{@link de.hu_berlin.ensureII.sre.model.sre.Node#getTgtState <em>Tgt State</em>}</li>
 * </ul>
 *
 * @see de.hu_berlin.ensureII.sre.model.sre.SrePackage#getNode()
 * @model
 * @generated
 */
public interface Node extends EObject {
    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute.
     * The default value is <code>"0"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(int)
     * @see de.hu_berlin.ensureII.sre.model.sre.SrePackage#getNode_Id()
     * @model default="0"
     * @generated
     */
    int getId();

    /**
     * Sets the value of the '{@link de.hu_berlin.ensureII.sre.model.sre.Node#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(int value);

    /**
     * Returns the value of the '<em><b>Rate</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Rate</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Rate</em>' attribute.
     * @see #setRate(int)
     * @see de.hu_berlin.ensureII.sre.model.sre.SrePackage#getNode_Rate()
     * @model
     * @generated
     */
    int getRate();

    /**
     * Sets the value of the '{@link de.hu_berlin.ensureII.sre.model.sre.Node#getRate <em>Rate</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Rate</em>' attribute.
     * @see #getRate()
     * @generated
     */
    void setRate(int value);

    /**
     * Returns the value of the '<em><b>Src State</b></em>' attribute.
     * The default value is <code>"0"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Src State</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Src State</em>' attribute.
     * @see #setSrcState(int)
     * @see de.hu_berlin.ensureII.sre.model.sre.SrePackage#getNode_SrcState()
     * @model default="0"
     * @generated
     */
    int getSrcState();

    /**
     * Sets the value of the '{@link de.hu_berlin.ensureII.sre.model.sre.Node#getSrcState <em>Src State</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Src State</em>' attribute.
     * @see #getSrcState()
     * @generated
     */
    void setSrcState(int value);

    /**
     * Returns the value of the '<em><b>Tgt State</b></em>' attribute.
     * The default value is <code>"0"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Tgt State</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Tgt State</em>' attribute.
     * @see #setTgtState(int)
     * @see de.hu_berlin.ensureII.sre.model.sre.SrePackage#getNode_TgtState()
     * @model default="0"
     * @generated
     */
    int getTgtState();

    /**
     * Sets the value of the '{@link de.hu_berlin.ensureII.sre.model.sre.Node#getTgtState <em>Tgt State</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Tgt State</em>' attribute.
     * @see #getTgtState()
     * @generated
     */
    void setTgtState(int value);

} // Node
