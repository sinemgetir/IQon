/**
 */
package de.hu_berlin.ensureII.sre.model.sre;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Choice</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.ensureII.sre.model.sre.Choice#getSubnodes <em>Subnodes</em>}</li>
 * </ul>
 *
 * @see de.hu_berlin.ensureII.sre.model.sre.SrePackage#getChoice()
 * @model
 * @generated
 */
public interface Choice extends Node {
    /**
     * Returns the value of the '<em><b>Subnodes</b></em>' containment reference list.
     * The list contents are of type {@link de.hu_berlin.ensureII.sre.model.sre.Node}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Subnodes</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Subnodes</em>' containment reference list.
     * @see de.hu_berlin.ensureII.sre.model.sre.SrePackage#getChoice_Subnodes()
     * @model containment="true" required="true"
     * @generated
     */
    EList<Node> getSubnodes();

} // Choice
