/**
 */
package de.hu_berlin.ensureII.sre.model.sre.impl;

import de.hu_berlin.ensureII.sre.model.sre.Action;
import de.hu_berlin.ensureII.sre.model.sre.SrePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.ensureII.sre.model.sre.impl.ActionImpl#getAction <em>Action</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ActionImpl extends NodeImpl implements Action {
    /**
     * The default value of the '{@link #getAction() <em>Action</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAction()
     * @generated
     * @ordered
     */
    protected static final String ACTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAction() <em>Action</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAction()
     * @generated
     * @ordered
     */
    protected String action = ACTION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ActionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SrePackage.Literals.ACTION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAction() {
        return action;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAction(String newAction) {
        String oldAction = action;
        action = newAction;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SrePackage.ACTION__ACTION, oldAction, action));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case SrePackage.ACTION__ACTION:
                return getAction();
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
            case SrePackage.ACTION__ACTION:
                setAction((String)newValue);
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
            case SrePackage.ACTION__ACTION:
                setAction(ACTION_EDEFAULT);
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
            case SrePackage.ACTION__ACTION:
                return ACTION_EDEFAULT == null ? action != null : !ACTION_EDEFAULT.equals(action);
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
        result.append(" (action: ");
        result.append(action);
        result.append(')');
        return result.toString();
    }

} //ActionImpl
