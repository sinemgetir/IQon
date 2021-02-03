/**
 */
package de.hu_berlin.ensureII.sre.model.sre.impl;

import de.hu_berlin.ensureII.sre.model.sre.KleeneClosure;
import de.hu_berlin.ensureII.sre.model.sre.Node;
import de.hu_berlin.ensureII.sre.model.sre.SrePackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Kleene Closure</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.ensureII.sre.model.sre.impl.KleeneClosureImpl#getLoopProbability <em>Loop Probability</em>}</li>
 *   <li>{@link de.hu_berlin.ensureII.sre.model.sre.impl.KleeneClosureImpl#getToLoop <em>To Loop</em>}</li>
 * </ul>
 *
 * @generated
 */
public class KleeneClosureImpl extends NodeImpl implements KleeneClosure {
    /**
     * The default value of the '{@link #getLoopProbability() <em>Loop Probability</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLoopProbability()
     * @generated
     * @ordered
     */
    protected static final double LOOP_PROBABILITY_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getLoopProbability() <em>Loop Probability</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLoopProbability()
     * @generated
     * @ordered
     */
    protected double loopProbability = LOOP_PROBABILITY_EDEFAULT;

    /**
     * The cached value of the '{@link #getToLoop() <em>To Loop</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getToLoop()
     * @generated
     * @ordered
     */
    protected Node toLoop;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected KleeneClosureImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SrePackage.Literals.KLEENE_CLOSURE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getLoopProbability() {
        return loopProbability;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLoopProbability(double newLoopProbability) {
        double oldLoopProbability = loopProbability;
        loopProbability = newLoopProbability;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SrePackage.KLEENE_CLOSURE__LOOP_PROBABILITY, oldLoopProbability, loopProbability));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Node getToLoop() {
        return toLoop;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetToLoop(Node newToLoop, NotificationChain msgs) {
        Node oldToLoop = toLoop;
        toLoop = newToLoop;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SrePackage.KLEENE_CLOSURE__TO_LOOP, oldToLoop, newToLoop);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setToLoop(Node newToLoop) {
        if (newToLoop != toLoop) {
            NotificationChain msgs = null;
            if (toLoop != null)
                msgs = ((InternalEObject)toLoop).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SrePackage.KLEENE_CLOSURE__TO_LOOP, null, msgs);
            if (newToLoop != null)
                msgs = ((InternalEObject)newToLoop).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SrePackage.KLEENE_CLOSURE__TO_LOOP, null, msgs);
            msgs = basicSetToLoop(newToLoop, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SrePackage.KLEENE_CLOSURE__TO_LOOP, newToLoop, newToLoop));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case SrePackage.KLEENE_CLOSURE__TO_LOOP:
                return basicSetToLoop(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case SrePackage.KLEENE_CLOSURE__LOOP_PROBABILITY:
                return getLoopProbability();
            case SrePackage.KLEENE_CLOSURE__TO_LOOP:
                return getToLoop();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case SrePackage.KLEENE_CLOSURE__LOOP_PROBABILITY:
                setLoopProbability((Double)newValue);
                return;
            case SrePackage.KLEENE_CLOSURE__TO_LOOP:
                setToLoop((Node)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case SrePackage.KLEENE_CLOSURE__LOOP_PROBABILITY:
                setLoopProbability(LOOP_PROBABILITY_EDEFAULT);
                return;
            case SrePackage.KLEENE_CLOSURE__TO_LOOP:
                setToLoop((Node)null);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case SrePackage.KLEENE_CLOSURE__LOOP_PROBABILITY:
                return loopProbability != LOOP_PROBABILITY_EDEFAULT;
            case SrePackage.KLEENE_CLOSURE__TO_LOOP:
                return toLoop != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (loopProbability: ");
        result.append(loopProbability);
        result.append(')');
        return result.toString();
    }

} //KleeneClosureImpl
