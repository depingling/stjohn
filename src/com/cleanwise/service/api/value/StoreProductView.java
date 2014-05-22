
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        StoreProductView
 * Description:  This is a ViewObject class for UI.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ViewObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import org.w3c.dom.*;

/**
 * <code>StoreProductView</code> is a ViewObject class for UI.
 */
public class StoreProductView
extends ValueObject
{    
    private StoreData store = null;
    private ProductData product = null;

    // the checkbox values for the linked items of ENTERPRISE store
    private boolean updateShortDesc;
    private boolean updateOtherDesc;
    private boolean updateImage;
    private boolean updateLongDesc;
    private boolean updateManufacturer;
    private boolean updateManufacturerSku;
    private boolean updateUpc;
    private boolean updatePkgUpc;
    private boolean updateMsds;
    private boolean updateUnspscCd;
    private boolean updateColor;
    private boolean updateSize;
    private boolean updateScent;
    private boolean updateDed;
    private boolean updateShipWeight;
    private boolean updateCubeSize;
    private boolean updateListPrice;
    private boolean updatePsn;
    private boolean updateSpec;
    private boolean updateNsn;
    private boolean updateUom;
    private boolean updatePackProblemSku;
    private boolean updatePack;
    private boolean updateCostPrice;    
    private boolean updateHazmat;
    private boolean updateCertifiedCompanies;


    /**
     * Constructor.
     */
    public StoreProductView ()
    {
        setStore(StoreData.createValue());
        setProduct(new ProductData());
    }

    /**
     * Constructor. 
     */
    public StoreProductView(StoreData parm1, ProductData parm2)
    {
        setStore(parm1);
        setProduct(parm2);
    }

    /**
     * Creates a new StoreProductView
     *
     * @return
     *  Newly initialized StoreProductView object.
     */
    public static StoreProductView createValue () 
    {
        StoreProductView valueView = new StoreProductView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this StoreProductView object
     */
    public String toString()
    {
        return "[" + "store=" + getStore().toString() + ", product=" + getProduct().toString() + "]";
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public StoreProductView copy()  {
      StoreProductView obj = new StoreProductView();
      obj.setStore(getStore());
      obj.setProduct(getProduct());
      
      return obj;
    }

    
    /**
     * Sets the store property.
     *
     * @param pStore
     *  StoreData to use to update the property.
     */
    public void setStore(StoreData pStore){
        this.store = pStore;
    }
    /**
     * Retrieves the store property.
     *
     * @return
     *  StoreData containing the store property.
     */
    public StoreData getStore(){
        return store;
    }


    /**
     * Sets the product property.
     *
     * @param pProduct
     *  productData to use to update the property.
     */
    public void setProduct(ProductData pProduct){
        this.product = pProduct;
    }
    /**
     * Retrieves the product property.
     *
     * @return
     *  ProductData containing the product property.
     */
    public ProductData getProduct(){
        return product;
    }

    public boolean isUpdateShortDesc() {
        return updateShortDesc;
    }

    public void setUpdateShortDesc(boolean updateShortDesc) {
        this.updateShortDesc = updateShortDesc;
    }

    public boolean isUpdateOtherDesc() {
        return updateOtherDesc;
    }

    public void setUpdateOtherDesc(boolean updateOtherDesc) {
        this.updateOtherDesc = updateOtherDesc;
    }

    public boolean isUpdateImage() {
        return updateImage;
    }

    public void setUpdateImage(boolean updateImage) {
        this.updateImage = updateImage;
    }

    public boolean isUpdateLongDesc() {
        return updateLongDesc;
    }

    public void setUpdateLongDesc(boolean updateLongDesc) {
        this.updateLongDesc = updateLongDesc;
    }

    public boolean isUpdateManufacturer() {
        return updateManufacturer;
    }

    public void setUpdateManufacturer(boolean updateManufacturer) {
        this.updateManufacturer = updateManufacturer;
    }

    public boolean isUpdateManufacturerSku() {
        return updateManufacturerSku;
    }

    public void setUpdateManufacturerSku(boolean updateManufacturerSku) {
        this.updateManufacturerSku = updateManufacturerSku;
    }

    public boolean isUpdateUpc() {
        return updateUpc;
    }

    public void setUpdateUpc(boolean updateUpc) {
        this.updateUpc = updateUpc;
    }

    public boolean isUpdatePkgUpc() {
        return updatePkgUpc;
    }

    public void setUpdatePkgUpc(boolean updatePkgUpc) {
        this.updatePkgUpc = updatePkgUpc;
    }

    public boolean isUpdateMsds() {
        return updateMsds;
    }

    public void setUpdateMsds(boolean updateMsds) {
        this.updateMsds = updateMsds;
    }

    public boolean isUpdateUnspscCd() {
        return updateUnspscCd;
    }

    public void setUpdateUnspscCd(boolean updateUnspscCd) {
        this.updateUnspscCd = updateUnspscCd;
    }

    public boolean isUpdateColor() {
        return updateColor;
    }

    public void setUpdateColor(boolean updateColor) {
        this.updateColor = updateColor;
    }

    public boolean isUpdateSize() {
        return updateSize;
    }

    public void setUpdateSize(boolean updateSize) {
        this.updateSize = updateSize;
    }

    public boolean isUpdateScent() {
        return updateScent;
    }

    public void setUpdateScent(boolean updateScent) {
        this.updateScent = updateScent;
    }

    public boolean isUpdateDed() {
        return updateDed;
    }

    public void setUpdateDed(boolean updateDed) {
        this.updateDed = updateDed;
    }

    public boolean isUpdateShipWeight() {
        return updateShipWeight;
    }

    public void setUpdateShipWeight(boolean updateShipWeight) {
        this.updateShipWeight = updateShipWeight;
    }

    public boolean isUpdateCubeSize() {
        return updateCubeSize;
    }

    public void setUpdateCubeSize(boolean updateCubeSize) {
        this.updateCubeSize = updateCubeSize;
    }

    public boolean isUpdateListPrice() {
        return updateListPrice;
    }

    public void setUpdateListPrice(boolean updateListPrice) {
        this.updateListPrice = updateListPrice;
    }

    public boolean isUpdatePsn() {
        return updatePsn;
    }

    public void setUpdatePsn(boolean updatePsn) {
        this.updatePsn = updatePsn;
    }

    public boolean isUpdateSpec() {
        return updateSpec;
    }

    public void setUpdateSpec(boolean updateSpec) {
        this.updateSpec = updateSpec;
    }

    public boolean isUpdateNsn() {
        return updateNsn;
    }

    public void setUpdateNsn(boolean updateNsn) {
        this.updateNsn = updateNsn;
    }

    public boolean isUpdateUom() {
        return updateUom;
    }

    public void setUpdateUom(boolean updateUom) {
        this.updateUom = updateUom;
    }

    public boolean isUpdatePackProblemSku() {
        return updatePackProblemSku;
    }

    public void setUpdatePackProblemSku(boolean updatePackProblemSku) {
        this.updatePackProblemSku = updatePackProblemSku;
    }

    public boolean isUpdatePack() {
        return updatePack;
    }

    public void setUpdatePack(boolean updatePack) {
        this.updatePack = updatePack;
    }

    public boolean isUpdateCostPrice() {
        return updateCostPrice;
    }

    public void setUpdateCostPrice(boolean updateCostPrice) {
        this.updateCostPrice = updateCostPrice;
    }

    public boolean isUpdateHazmat() {
        return updateHazmat;
    }

    public void setUpdateHazmat(boolean updateHazmat) {
        this.updateHazmat = updateHazmat;
    }

    public boolean isUpdateCertifiedCompanies() {
        return updateCertifiedCompanies;
    }

    public void setUpdateCertifiedCompanies(boolean updateCertifiedCompanies) {
        this.updateCertifiedCompanies = updateCertifiedCompanies;
    }
}
