package com.cleanwise.service.api.wrapper;

import com.cleanwise.service.api.value.InventoryLevelDetailDataVector;
import com.cleanwise.service.api.value.InventoryLevelDetailData;
import com.cleanwise.service.api.value.InventoryLevelView;
import com.cleanwise.service.api.value.InventoryLevelData;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;
import java.util.Date;

/**
 * Title:        InventoryLevelViewWrapper
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         16.10.2009
 * Time:         14:27:55
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class InventoryLevelViewWrapper {

    private InventoryLevelView mInventoryLevelView;

    public InventoryLevelView getInventoryLevelView() {
        return mInventoryLevelView;
    }

    public void setInventoryLevelView(InventoryLevelView pInventoryLevelView) {
        this.mInventoryLevelView = pInventoryLevelView;
    }

    public InventoryLevelViewWrapper(InventoryLevelView pValue) {
        this.mInventoryLevelView = pValue;
    }

    public void setParValue(int pPeriod, Integer pValue) {

        if (this.mInventoryLevelView != null) {

            InventoryLevelDetailDataVector parValues = this.mInventoryLevelView.getParValues();
            for (Object oData : parValues) {
                InventoryLevelDetailData d = (InventoryLevelDetailData) oData;
                if (d.getPeriod() == pPeriod) {
                    d.setValue(pValue);
                    return;
                }
            }

            InventoryLevelDetailData parValueData = InventoryLevelDetailData.createValue();
            parValueData.setInventoryLevelId(this.mInventoryLevelView.getInventoryLevelData().getInventoryLevelId());
            parValueData.setPeriod(pPeriod);
            parValueData.setValue(pValue);
            parValues.add(parValueData);
        }

    }

    public Integer getParValue(int pPeriod) {

        if (this.mInventoryLevelView != null) {

            InventoryLevelDetailDataVector parValues = this.mInventoryLevelView.getParValues();
            for (Object oData : parValues) {
                InventoryLevelDetailData d = (InventoryLevelDetailData) oData;
                if (d.getPeriod() == pPeriod) {
                    return d.getValue();
                }
            }
        }

        return null;
    }

    public void setInventoryLevelId(int pInventoryLevelId) {
        this.mInventoryLevelView.getInventoryLevelData().setInventoryLevelId(pInventoryLevelId);
    }

    public int getInventoryLevelId() {
        return this.mInventoryLevelView.getInventoryLevelData().getInventoryLevelId();
    }

    public void setBusEntityId(int pBusEntityId) {
        this.mInventoryLevelView.getInventoryLevelData().setBusEntityId(pBusEntityId);
    }

    public int getBusEntityId() {
        return this.mInventoryLevelView.getInventoryLevelData().getBusEntityId();
    }

    public void setItemId(int pItemId) {
        this.mInventoryLevelView.getInventoryLevelData().setItemId(pItemId);
    }

    public int getItemId() {
        return this.mInventoryLevelView.getInventoryLevelData().getItemId();
    }

    public void setQtyOnHand(String pQtyOnHand) {
        this.mInventoryLevelView.getInventoryLevelData().setQtyOnHand(pQtyOnHand);
    }

    public String getQtyOnHand() {
        return this.mInventoryLevelView.getInventoryLevelData().getQtyOnHand();
    }

    public void setAddDate(Date pAddDate) {
        this.mInventoryLevelView.getInventoryLevelData().setAddDate(pAddDate);
    }

    public Date getAddDate() {
        return this.mInventoryLevelView.getInventoryLevelData().getAddDate();
    }

    public void setAddBy(String pAddBy) {
        this.mInventoryLevelView.getInventoryLevelData().setAddBy(pAddBy);

    }

    public String getAddBy() {
        return this.mInventoryLevelView.getInventoryLevelData().getAddBy();
    }


    public void setModDate(Date pModDate) {
        this.mInventoryLevelView.getInventoryLevelData().setModDate(pModDate);
    }

    public Date getModDate() {
        return this.mInventoryLevelView.getInventoryLevelData().getModDate();
    }

    public void setModBy(String pModBy) {
        this.mInventoryLevelView.getInventoryLevelData().setModBy(pModBy);

    }

    public String getModBy() {
        return this.mInventoryLevelView.getInventoryLevelData().getModBy();
    }

    public void setOrderQty(String pOrderQty) {
        this.mInventoryLevelView.getInventoryLevelData().setOrderQty(pOrderQty);
    }

    public String getOrderQty() {
        return this.mInventoryLevelView.getInventoryLevelData().getOrderQty();
    }

    public void setInitialQtyOnHand(String pInitialQtyOnHand) {
        this.mInventoryLevelView.getInventoryLevelData().setInitialQtyOnHand(pInitialQtyOnHand);
    }

    public String getInitialQtyOnHand() {
        return this.mInventoryLevelView.getInventoryLevelData().getInitialQtyOnHand();
    }

    public void setParsModDate(Date pParsModDate) {
        this.mInventoryLevelView.getInventoryLevelData().setParsModDate(pParsModDate);
    }

    public Date getParsModDate() {
        return this.mInventoryLevelView.getInventoryLevelData().getParsModDate();
    }

    public void setParsModBy(String pParsModBy) {
        this.mInventoryLevelView.getInventoryLevelData().setParsModBy(pParsModBy);
    }

    public String getParsModBy() {
        return this.mInventoryLevelView.getInventoryLevelData().getParsModBy();
    }

    public void setInventoryLevelData(InventoryLevelData pInventoryLevelData) {
        this.mInventoryLevelView.setInventoryLevelData(pInventoryLevelData);
    }

    public InventoryLevelData getInventoryLevelData() {
        return this.mInventoryLevelView.getInventoryLevelData();
    }

    public void setParValues(InventoryLevelDetailDataVector pParValues) {
        this.mInventoryLevelView.setParValues(pParValues);
    }

    public InventoryLevelDetailDataVector getParValues() {
        return this.mInventoryLevelView.getParValues();
    }


}
