/**
 */
package de.hu_berlin.ensureII.sre.model.sre;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Plus Closure</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.ensureII.sre.model.sre.PlusClosure#getLoopProbability <em>Loop Probability</em>}</li>
 *   <li>{@link de.hu_berlin.ensureII.sre.model.sre.PlusClosure#getToLoop <em>To Loop</em>}</li>
 * </ul>
 *
 * @see de.hu_berlin.ensureII.sre.model.sre.SrePackage#getPlusClosure()
 * @model
 * @generated
 */
public interface PlusClosure extends Node {
    /**
     * Returns the value of the '<em><b>Loop Probability</b></em>' attribute.
     * The default value is <code>"0"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Loop Probability</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Loop Probability</em>' attribute.
     * @see #setLoopProbability(double)
     * @see de.hu_berlin.ensureII.sre.model.sre.SrePackage#getPlusClosure_LoopProbability()
     * @model default="0"
     * @generated
     */
    double getLoopProbability();

    /**
     * Sets the value of the '{@link de.hu_berlin.ensureII.sre.model.sre.PlusClosure#getLoopProbability <em>Loop Probability</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Loop Probability</em>' attribute.
     * @see #getLoopProbability()
     * @generated
     */
    void setLoopProbability(double value);

    /**
     * Returns the value of the '<em><b>To Loop</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>To Loop</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>To Loop</em>' containment reference.
     * @see #setToLoop(Node)
     * @see de.hu_berlin.ensureII.sre.model.sre.SrePackage#getPlusClosure_ToLoop()
     * @model containment="true" required="true"
     * @generated
     */
    Node getToLoop();

    /**
     * Sets the value of the '{@link de.hu_berlin.ensureII.sre.model.sre.PlusClosure#getToLoop <em>To Loop</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>To Loop</em>' containment reference.
     * @see #getToLoop()
     * @generated
     */
    void setToLoop(Node value);

} // PlusClosure
