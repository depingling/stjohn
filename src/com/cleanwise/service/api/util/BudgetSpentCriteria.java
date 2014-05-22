package com.cleanwise.service.api.util;

import com.cleanwise.service.api.value.IdVector;

import java.util.*;
import java.math.BigDecimal;
import java.io.Serializable;

/**
 */
public class BudgetSpentCriteria implements Serializable {

    private int mBudgetYear;
    private IdVector mCostCenters;
    private int mBusEntityId;
    private String mBudgetTypeCd;
    private int mNumberOfBudgetPeriods;


    public BudgetSpentCriteria() {
        mCostCenters = new IdVector();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        mBudgetYear = cal.get(Calendar.YEAR);
    }

    public int getBudgetYear() {
        return mBudgetYear;
    }

    public void setBudgetYear(int mBudgetYear) {
        this.mBudgetYear = mBudgetYear;
    }

    public IdVector getCostCenters() {
        return mCostCenters;
    }

    public void setCostCenters(IdVector mCostCenters) {
        this.mCostCenters = mCostCenters;
    }

    public int getBusEntityId() {
        return mBusEntityId;
    }

    public void setBusEntityId(int mBusEntityId) {
        this.mBusEntityId = mBusEntityId;
    }

    public String getBudgetTypeCd() {
        return mBudgetTypeCd;
    }

    public void setBudgetTypeCd(String mBudgetTypeCd) {
        this.mBudgetTypeCd = mBudgetTypeCd;
    }

    public void setNumberOfBudgetPeriods(int mNumberOfBudgetPeriods) {
        this.mNumberOfBudgetPeriods = mNumberOfBudgetPeriods;
    }

    public int getNumberOfBudgetPeriods() {
        return mNumberOfBudgetPeriods;
    }
}
