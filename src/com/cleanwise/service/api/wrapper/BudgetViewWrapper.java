package com.cleanwise.service.api.wrapper;

import com.cleanwise.service.api.value.BudgetView;
import com.cleanwise.service.api.value.BudgetData;
import com.cleanwise.service.api.value.BudgetDetailData;
import com.cleanwise.service.api.framework.ValueObject;

import java.math.BigDecimal;

/**
 * Title:        BudgetViewWrapper
 * Description:  Wrapper of BudgetView object
 * Purpose       This value object provides methods of accessing data from the underlying budget data object.
 * * Copyright:  Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         30.07.2009
 * Time:         13:23:00
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class BudgetViewWrapper extends ValueObject {

    BudgetView mBudget;

    public BudgetViewWrapper(BudgetView pBudget) {
        mBudget = pBudget;
    }

    public BudgetData getBudgetData() {
        return mBudget.getBudgetData();
    }

    public BudgetView getBudget() {
        return mBudget;
    }

    public void setAmount(int pPeriod, BigDecimal pAmount) {

        if (mBudget.getDetails() != null) {

            boolean isSet = false;

            for (Object oBudgetDetail : mBudget.getDetails()) {
                BudgetDetailData budgetDetail = (BudgetDetailData) oBudgetDetail;
                if (budgetDetail.getPeriod() == pPeriod) {
                    budgetDetail.setAmount(pAmount);
                    isSet = true;
                    break;
                }
            }

            if(!isSet) {
                BudgetDetailData budgetDetail =  BudgetDetailData.createValue();
                budgetDetail.setPeriod(pPeriod);
                budgetDetail.setAmount(pAmount);
                mBudget.getDetails().add(budgetDetail);
            }

        }
    }

    public BigDecimal getAmount(int pPeriod) {
        BigDecimal toReturn = null;
        if (mBudget.getDetails() != null) {
            for (Object oBudgetDetail : mBudget.getDetails()) {
                BudgetDetailData budgetDetail = (BudgetDetailData) oBudgetDetail;
                if (budgetDetail.getPeriod() == pPeriod) {
                    toReturn = budgetDetail.getAmount();
                    break;
                }
            }
        }
        return toReturn;
    }
    
    //Location Budget
    
    /**
     * Gets the Budget's current period amount
     * @param pPeriod
     * @return BigDecimal
     */
    public BigDecimal getCurrentPeriodAmount(int pPeriod) {
        BigDecimal toReturn = new BigDecimal(-1);
        if (mBudget.getDetails() != null) {
            for (Object oBudgetDetail : mBudget.getDetails()) {
                BudgetDetailData budgetDetail = (BudgetDetailData) oBudgetDetail;
                if (budgetDetail.getPeriod() == pPeriod) {
                    toReturn = budgetDetail.getAmount();
                    break;
                }
            }
        }
        return toReturn;
    }

}
