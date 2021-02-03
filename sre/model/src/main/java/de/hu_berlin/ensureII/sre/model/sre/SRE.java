/**
 */
package de.hu_berlin.ensureII.sre.model.sre;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>SRE</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.ensureII.sre.model.sre.SRE#getRoot <em>Root</em>}</li>
 * </ul>
 *
 * @see de.hu_berlin.ensureII.sre.model.sre.SrePackage#getSRE()
 * @model
 * @generated
 */
public interface SRE extends EObject {
    /**
     * Returns the value of the '<em><b>Root</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Root</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Root</em>' containment reference.
     * @see #setRoot(Node)
     * @see de.hu_berlin.ensureII.sre.model.sre.SrePackage#getSRE_Root()
     * @model containment="true" required="true"
     * @generated
     */
    Node getRoot();

    /**
     * Sets the value of the '{@link de.hu_berlin.ensureII.sre.model.sre.SRE#getRoot <em>Root</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Root</em>' containment reference.
     * @see #getRoot()
     * @generated
     */
    void setRoot(Node value);

} // SRE
