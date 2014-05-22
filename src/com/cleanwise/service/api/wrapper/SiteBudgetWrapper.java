/*
 * SiteBudgetWrapper.java
 *
 * Created on April 1, 2005, 3:26 PM
 */

package com.cleanwise.service.api.wrapper;

import java.math.BigDecimal;

import java.util.*;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.Constants;

import org.apache.log4j.Logger;


/**
 * value uobject for use in manipulating a budget data within the Ui
 *
 */
public class SiteBudgetWrapper {

    private static final Logger log = Logger.getLogger(SiteBudgetWrapper.class);

    private BudgetView budget;
    private CostCenterData costCenterData;
    private boolean negativeAmount = false;
    private boolean inCorrectAmount = false;

    private ArrayList StrAmounts;


    /**
     * Creates a new instance of SiteBudgetWrapper
     */
    public SiteBudgetWrapper() {
    	setNegativeAmount(false);
    	setInCorrectAmount(false);
    }


    public String getBudgetAmount(int pPeriod) {
        try {
           for (Object oBudgetDetail : budget.getDetails()) {
                BudgetDetailData detailData = (BudgetDetailData) oBudgetDetail;
                if (pPeriod == detailData.getPeriod()) {
                    if (detailData.getAmount() != null) {
                        return detailData.getAmount().toString();
                    } else {
                        return detailData.getAmountStr() != null ? detailData.getAmountStr() : "";
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("getBudgetAmount exception: " + e);
        }
        return "";
    }

    /**
     * Sets the value of budget
     *
     * @param pBudgetAmount Value to assign to this.siteBudget
     * @param pPeriod       budget period
     */
    public void setBudgetAmount(int pPeriod, String pBudgetAmount) {
        try {
            boolean isSet = false;
            for (Object oBudgetDetail : budget.getDetails()) {
                BudgetDetailData detailData = (BudgetDetailData) oBudgetDetail;
                if (pPeriod == detailData.getPeriod()) {
                    if (Utility.isSet(pBudgetAmount)) {
                        detailData.setAmount(new BigDecimal(pBudgetAmount));
                        isSet = true;
                    } else {
                        detailData.setAmount(null);
                        isSet = true;
                    }
                    detailData.setAmountStr(pBudgetAmount);
                }
            }
            if (!isSet) {
                BudgetDetailData bdd = BudgetDetailData.createValue();
                bdd.setPeriod(pPeriod);
                if (Utility.isSet(pBudgetAmount)) {
                    bdd.setAmount(new BigDecimal(pBudgetAmount));
                } else {
                    bdd.setAmount(null);
                }
                bdd.setAmountStr(pBudgetAmount);
                budget.getDetails().add(bdd);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("setBudgetAmount exception: " + e);
        }
    }

    public String getBudgetThreshold() {
        return getBudgetView().getBudgetData().getBudgetThreshold();
    }

    public void setBudgetThreshold(String pValue) {
        getBudgetView().getBudgetData().setBudgetThreshold(pValue);
    }

    /**
     * Gets the value of budget
     *
     * @return the value of budget
     */
    public BudgetView getBudgetView() {
        return this.budget;
    }

    /**
     * Sets the value of budget
     *
     * @param pBudget Value to assign to this.siteBudget
     */
    public void setBudgetView(BudgetView pBudget) {
        this.budget = pBudget;
    }

    /**
     * Getter for property costCenterData.
     *
     * @return Value of property costCenterData.
     */
    public com.cleanwise.service.api.value.CostCenterData getCostCenterData() {
        return costCenterData;
    }

    /**
     * Setter for property costCenterData.
     *
     * @param costCenterData New value of property costCenterData.
     */
    public void setCostCenterData(com.cleanwise.service.api.value.CostCenterData costCenterData) {
        this.costCenterData = costCenterData;
    }

    public String getSiteBudgetAmount(int pPeriod) {
        try {
           for (Object oBudgetDetail : budget.getDetails()) {
                BudgetDetailData detailData = (BudgetDetailData) oBudgetDetail;
                if (pPeriod == detailData.getPeriod()) {
                    if (detailData.getAmount() != null) {
                        return detailData.getAmount().toString();
                    } else if (detailData.getAmount() == null) {
                        // STJ-4347 if a new budget for new cost center do not show Unlimited when error
                        if (budget.getBudgetData().getBudgetId() > 0) {
                           //return Constants.UNLIMITED;
                           return detailData.getAmountStr() != null ? detailData.getAmountStr() : Constants.UNLIMITED;
                        } else {
                           return detailData.getAmountStr() != null ? detailData.getAmountStr() : "";
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("getBudgetAmount exception: " + e);
        }
        return "";

    }

    /**
     * Sets the value of budget
     *
     * @param pBudgetAmount Value to assign to this.siteBudget
     * @param pPeriod       budget period
     */
    public void setSiteBudgetAmount(int pPeriod, String pBudgetAmount) {
        try {
            boolean isSet = false;
            for (Object oBudgetDetail : budget.getDetails()) {
                BudgetDetailData detailData = (BudgetDetailData) oBudgetDetail;
                if (pPeriod == detailData.getPeriod()) {
                    if (Utility.isSet(pBudgetAmount)) {
                    	if(pBudgetAmount.equalsIgnoreCase(Constants.UNLIMITED)) { // STJ - 3628
                    		detailData.setAmount(null);
                    	} else {
                    		try { // STJ - 3910
                    			double amount = Double.parseDouble(pBudgetAmount);
                    			if(amount<0) {
                    				setNegativeAmount(true);
                    			}
                    			detailData.setAmount(new BigDecimal(pBudgetAmount));
                    		}catch(Exception e) {
                    			setInCorrectAmount(true);
                    			detailData.setAmount(null);
                    		}
                    	}
                    	//detailData.setAmount(new BigDecimal(pBudgetAmount));
                    	isSet = true;
                    } else {
                        detailData.setAmount(null);
                        isSet = true;
                    }
                    detailData.setAmountStr(pBudgetAmount);
                }
            }
            if (!isSet) {
                BudgetDetailData bdd = BudgetDetailData.createValue();
                bdd.setPeriod(pPeriod);
                if (Utility.isSet(pBudgetAmount)) {
                	//bdd.setAmount(new BigDecimal(pBudgetAmount));
                	if(pBudgetAmount.equalsIgnoreCase(Constants.UNLIMITED)) { // STJ - 3628
                		bdd.setAmount(null);
                	} else {
                		try { // STJ - 3910
                			double amount = Double.parseDouble(pBudgetAmount);
                			if(amount<0) {
                				setNegativeAmount(true);
                			}
                			bdd.setAmount(new BigDecimal(pBudgetAmount));
                		}catch(Exception e) {
                			setInCorrectAmount(true);
                			bdd.setAmount(null);
                		}
                	}
                } else {
                    bdd.setAmount(null);
                }
                bdd.setAmountStr(pBudgetAmount);
                budget.getDetails().add(bdd);
            }
        }
        catch (Exception e) {
            //e.printStackTrace();
            log.error("setBudgetAmount exception: " + e.getMessage());
        }
    }


	public boolean isNegativeAmount() {
		return negativeAmount;
	}


	public void setNegativeAmount(boolean negativeAmount) {
		this.negativeAmount = negativeAmount;
	}


	public boolean isInCorrectAmount() {
		return inCorrectAmount;
	}


	public void setInCorrectAmount(boolean inCorrectAmount) {
		this.inCorrectAmount = inCorrectAmount;
	}
    
    
}
