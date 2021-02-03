/**
 */
package de.hu_berlin.ensureII.sre.model.sre.impl;

import de.hu_berlin.ensureII.sre.model.sre.Concat;
import de.hu_berlin.ensureII.sre.model.sre.Node;
import de.hu_berlin.ensureII.sre.model.sre.SrePackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Concat</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.ensureII.sre.model.sre.impl.ConcatImpl#getSubnodes <em>Subnodes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConcatImpl extends NodeImpl implements Concat {
    /**
     * The cached value of the '{@link #getSubnodes() <em>Subnodes</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSubnodes()
     * @generated
     * @ordered
     */
    protected EList<Node> subnodes;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ConcatImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SrePackage.Literals.CONCAT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Node> getSubnodes() {
        if (subnodes == null) {
            subnodes = new EObjectContainmentEList<Node>(Node.class, this, SrePackage.CONCAT__SUBNODES);
        }
        return subnodes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case SrePackage.CONCAT__SUBNODES:
                return ((InternalEList<?>)getSubnodes()).basicRemove(otherEnd, msgs);
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
            case SrePackage.CONCAT__SUBNODES:
                return getSubnodes();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case SrePackage.CONCAT__SUBNODES:
                getSubnodes().clear();
                getSubnodes().addAll((Collection<? extends Node>)newValue);
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
            case SrePackage.CONCAT__SUBNODES:
                getSubnodes().clear();
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
            case SrePackage.CONCAT__SUBNODES:
                return subnodes != null && !subnodes.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //ConcatImpl
