/**
 */
package de.hu_berlin.ensureII.sre.model.sre.impl;

import de.hu_berlin.ensureII.sre.model.sre.Node;
import de.hu_berlin.ensureII.sre.model.sre.SRE;
import de.hu_berlin.ensureII.sre.model.sre.SrePackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>SRE</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.ensureII.sre.model.sre.impl.SREImpl#getRoot <em>Root</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SREImpl extends MinimalEObjectImpl.Container implements SRE {
    /**
     * The cached value of the '{@link #getRoot() <em>Root</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRoot()
     * @generated
     * @ordered
     */
    protected Node root;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SREImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SrePackage.Literals.SRE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Node getRoot() {
        return root;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRoot(Node newRoot, NotificationChain msgs) {
        Node oldRoot = root;
        root = newRoot;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SrePackage.SRE__ROOT, oldRoot, newRoot);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRoot(Node newRoot) {
        if (newRoot != root) {
            NotificationChain msgs = null;
            if (root != null)
                msgs = ((InternalEObject)root).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SrePackage.SRE__ROOT, null, msgs);
            if (newRoot != null)
                msgs = ((InternalEObject)newRoot).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SrePackage.SRE__ROOT, null, msgs);
            msgs = basicSetRoot(newRoot, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SrePackage.SRE__ROOT, newRoot, newRoot));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case SrePackage.SRE__ROOT:
                return basicSetRoot(null, msgs);
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
            case SrePackage.SRE__ROOT:
                return getRoot();
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
            case SrePackage.SRE__ROOT:
                setRoot((Node)newValue);
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
            case SrePackage.SRE__ROOT:
                setRoot((Node)null);
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
            case SrePackage.SRE__ROOT:
                return root != null;
        }
        return super.eIsSet(featureID);
    }

} //SREImpl
