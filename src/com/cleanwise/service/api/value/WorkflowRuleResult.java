package com.cleanwise.service.api.value;

import com.cleanwise.service.api.util.RefCodeNames;

/**
 * Describe class <code>WorkflowRuleResult</code> here.
 *
 * @author <a href="mailto:dvieira@DVIEIRA"></a>
 */
public class WorkflowRuleResult {

    // Workflow processing constants.
    public final static int
        OK = 100,
        GOTO_NEXT_RULE = 101,
        PENDING_APPROVAL = 200,
        PENDING_ORDER_REVIEW = 201,
        FAIL = 300,
        BUDGET_RULE_FAIL = 301,
        ORDER_TOTAL_RULE_FAIL = 302,
        ORDER_SKU_RULE_FAIL = 303,
        ORDER_VELOCITY_RULE_FAIL = 304
        ;

    private int mStatus = 0;
    private int mCostCenterId = 0;
    private WorkflowRuleData mRule = WorkflowRuleData.createValue();
    private String mCostCenterName;
    
    /**
     * Get the value of CostCenterName.
     * @return value of CostCenterName.
     */
    public String getCostCenterName() {
	return mCostCenterName;
    }
    
    /**
     * Set the value of CostCenterName.
     * @param v  Value to assign to CostCenterName.
     */
    public void setCostCenterName(String  v) {
	this.mCostCenterName = v;
    }
    
    /**
     * Get the value of CostCenterId.
     * @return value of CostCenterId.
     */
    public int getCostCenterId() {
	return mCostCenterId;
    }
    
    /**
     * Set the value of CostCenterId.
     * @param v  Value to assign to CostCenterId.
     */
    public void setCostCenterId(int  v) {
	this.mCostCenterId = v;
    }
    
    public String toString() {
	return "[ " + "mStatus=" + mStatus +
	    ", " + "mRule=" + mRule + " ]";
    }

    public boolean isOK() {
        return mStatus == OK;
    }
    public boolean isGotoNextRule() {
        return mStatus == GOTO_NEXT_RULE;
    }
    public boolean isPending() {
        return mStatus == PENDING_APPROVAL;
    }
    public boolean isPendingReview() {
        return mStatus == PENDING_ORDER_REVIEW;
    }
    public boolean isBudgetFail() {
        return mStatus == BUDGET_RULE_FAIL;
    }
    public boolean isOrderTotalFail() {
        return mStatus == ORDER_TOTAL_RULE_FAIL;
    }
    public boolean isOrderSkuFail() {
        return mStatus == ORDER_SKU_RULE_FAIL;
    }
    public boolean isOrderVelocityFail() {
        return mStatus == ORDER_VELOCITY_RULE_FAIL;
    }

    private boolean mRuleIsActive = true;
    public void setRuleIsActive(boolean v)
    { mRuleIsActive = v ; }
    
    public boolean approveOrder() {
        if (isBudgetFail()) return false;    
        if (isOrderTotalFail()) return false;
        if (isOrderSkuFail()) return false;
        if (isOrderVelocityFail()) return false;
        return true;
    }

    // Both of the following require that queue entries be made
    // for email notification.
    public boolean needsFollowUp() {

        if ( mRuleIsActive == false ) return false;

        if (mRule.getRuleAction().equals
             (RefCodeNames.WORKFLOW_RULE_ACTION.SEND_EMAIL )) {
            return true;
        }

        if ( isOK() ) {
            return false; // no more processing needed.
        }

        if (mRule.getRuleAction().equals
            (RefCodeNames.WORKFLOW_RULE_ACTION.FWD_FOR_APPROVAL ) ) {
            return true;
        }

        return false; // no more processing needed.
		
    }

    /**
     * Describe <code>getStatus</code> method here.
     *
     * @return an <code>int</code> value
     */
    public int getStatus() {
        return mStatus;
    }
    /**
     * Describe <code>setStatus</code> method here.
     *
     * @param v an <code>int</code> value
     */
    public void setStatus( int v) {
        mStatus = v;
    }

    /**
     * Get the value of Rule.
     * @return value of Rule.
     */
    public WorkflowRuleData getRule() {
        return mRule;
    }

    /**
     * Set the value of Rule.
     * @param v  Value to assign to Rule.
     */
    public void setRule(WorkflowRuleData  v) {
        this.mRule = v;
    }

    public String getMessage() {
        String m = "";
        if (isBudgetFail())
        {
            m += " Budget exceeded. ";
        }
        else if (isOrderTotalFail())
        {
            m += " Order total cannot be "
            + mRule.getRuleExp() + " " 
            + mRule.getRuleExpValue();
        }
        else if (isOrderSkuFail())
        {
            m += " Order contains SKU "
            + mRule.getRuleExpValue();
        }
        else if (isOrderVelocityFail())
        {
            m += " The last order was placed "
            + mRule.getRuleExp() + " " 
            + mRule.getRuleExpValue() + " days ago.";
        }
        return m;
    }
}
