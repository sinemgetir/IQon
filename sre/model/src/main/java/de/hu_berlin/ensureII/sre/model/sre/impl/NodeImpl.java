/**
 */
package de.hu_berlin.ensureII.sre.model.sre.impl;

import de.hu_berlin.ensureII.sre.model.sre.Node;
import de.hu_berlin.ensureII.sre.model.sre.SrePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.ensureII.sre.model.sre.impl.NodeImpl#getId <em>Id</em>}</li>
 *   <li>{@link de.hu_berlin.ensureII.sre.model.sre.impl.NodeImpl#getRate <em>Rate</em>}</li>
 *   <li>{@link de.hu_berlin.ensureII.sre.model.sre.impl.NodeImpl#getSrcState <em>Src State</em>}</li>
 *   <li>{@link de.hu_berlin.ensureII.sre.model.sre.impl.NodeImpl#getTgtState <em>Tgt State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NodeImpl extends MinimalEObjectImpl.Container implements Node {
    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final int ID_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected int id = ID_EDEFAULT;

    /**
     * The default value of the '{@link #getRate() <em>Rate</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRate()
     * @generated
     * @ordered
     */
    protected static final int RATE_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getRate() <em>Rate</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRate()
     * @generated
     * @ordered
     */
    protected int rate = RATE_EDEFAULT;

    /**
     * The default value of the '{@link #getSrcState() <em>Src State</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSrcState()
     * @generated
     * @ordered
     */
    protected static final int SRC_STATE_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getSrcState() <em>Src State</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSrcState()
     * @generated
     * @ordered
     */
    protected int srcState = SRC_STATE_EDEFAULT;

    /**
     * The default value of the '{@link #getTgtState() <em>Tgt State</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTgtState()
     * @generated
     * @ordered
     */
    protected static final int TGT_STATE_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getTgtState() <em>Tgt State</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTgtState()
     * @generated
     * @ordered
     */
    protected int tgtState = TGT_STATE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected NodeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SrePackage.Literals.NODE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setId(int newId) {
        int oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SrePackage.NODE__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getRate() {
        return rate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRate(int newRate) {
        int oldRate = rate;
        rate = newRate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SrePackage.NODE__RATE, oldRate, rate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getSrcState() {
        return srcState;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSrcState(int newSrcState) {
        int oldSrcState = srcState;
        srcState = newSrcState;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SrePackage.NODE__SRC_STATE, oldSrcState, srcState));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getTgtState() {
        return tgtState;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTgtState(int newTgtState) {
        int oldTgtState = tgtState;
        tgtState = newTgtState;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SrePackage.NODE__TGT_STATE, oldTgtState, tgtState));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case SrePackage.NODE__ID:
                return getId();
            case SrePackage.NODE__RATE:
                return getRate();
            case SrePackage.NODE__SRC_STATE:
                return getSrcState();
            case SrePackage.NODE__TGT_STATE:
                return getTgtState();
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
            case SrePackage.NODE__ID:
                setId((Integer)newValue);
                return;
            case SrePackage.NODE__RATE:
                setRate((Integer)newValue);
                return;
            case SrePackage.NODE__SRC_STATE:
                setSrcState((Integer)newValue);
                return;
            case SrePackage.NODE__TGT_STATE:
                setTgtState((Integer)newValue);
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
            case SrePackage.NODE__ID:
                setId(ID_EDEFAULT);
                return;
            case SrePackage.NODE__RATE:
                setRate(RATE_EDEFAULT);
                return;
            case SrePackage.NODE__SRC_STATE:
                setSrcState(SRC_STATE_EDEFAULT);
                return;
            case SrePackage.NODE__TGT_STATE:
                setTgtState(TGT_STATE_EDEFAULT);
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
            case SrePackage.NODE__ID:
                return id != ID_EDEFAULT;
            case SrePackage.NODE__RATE:
                return rate != RATE_EDEFAULT;
            case SrePackage.NODE__SRC_STATE:
                return srcState != SRC_STATE_EDEFAULT;
            case SrePackage.NODE__TGT_STATE:
                return tgtState != TGT_STATE_EDEFAULT;
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
        result.append(" (id: ");
        result.append(id);
        result.append(", rate: ");
        result.append(rate);
        result.append(", srcState: ");
        result.append(srcState);
        result.append(", tgtState: ");
        result.append(tgtState);
        result.append(')');
        return result.toString();
    }

} //NodeImpl
