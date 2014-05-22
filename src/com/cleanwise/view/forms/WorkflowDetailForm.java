/**
 *  Title: WorkflowDetailForm
 *  Description: This is the Struts
 *  ActionForm class for Workflow instance management.
 *  Purpose: Strut support
 *  to create and modify a Wrkflow.
 *  Copyright: Copyright (c) 2001
 *  Company: CleanWise, Inc.
 *
 *@author     durval
 */

package com.cleanwise.view.forms;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.SiteBudget;

/**
 *  Form bean for the workflow detail page.
 *
 *@author     durval
 *@created    December 11, 2001
 */
public final class WorkflowDetailForm extends Base2DetailForm {

    private GroupDataVector mUserGroups = null;
    private int mApproverGroupId = 0;
    private int mEmailGroupId = 0;
    private UserDataVector mApproverUsers = null;
    private UserDataVector mEmailUsers = null;

    private int mApplyRuleGroupId = 0;
    private boolean mApplySkipFl = true; //Apply
    
    
    private int mBusEntityId = 0;
    private WorkflowData mOriginalData = null;
    //private WorkflowRuleData mNewRule = WorkflowRuleData.createValue();
    //private String mNewRuleTypeCd = "";
    private String mRuleTypeCd = "";
    
    private String mBudgetExp = "";
    private String mBudgetValue = "";
    //private String mBudgetAction = "";

    private String mTotalExp = "";
    private String mTotalValue = "";
    //private String mTotalAction = "";
    private String mRuleAction = "";

    private String mTimespanInDays = "";
    //mVelocityAction = "";

    private String mOrderSku = "";
    //mSkuAction = "";
    private String mDistId = "";
    private String mDistName = "";
    private String mStateList = "";
        
    private int mDaysUntilNextAction = 0;
    private String mNextActionCd = "";

    private int mEmailUserId = 0;
    private String mEmailUserName = "";
    
    private int mRuleSeq = 0;
    private String mRuleGroup = "";
    

    public GroupDataVector getUserGroups() {return mUserGroups;}
    public void setUserGroups(GroupDataVector  v) {mUserGroups = v;}
   
    public int getApproverGroupId() {return mApproverGroupId;}
    public void setApproverGroupId(int  v) {mApproverGroupId = v;}
   
    public int getEmailGroupId() {return mEmailGroupId;}
    public void setEmailGroupId(int  v) {mEmailGroupId = v;}
   
    public UserDataVector getApproverUsers() {return mApproverUsers;}
    public void setApproverUsers(UserDataVector  v) {mApproverUsers = v;}
   
    public UserDataVector getEmailUsers() {return mEmailUsers;}
    public void setEmailUsers(UserDataVector  v) {mEmailUsers = v;}
   
    public int getApplyRuleGroupId() {return mApplyRuleGroupId;}
    public void setApplyRuleGroupId(int  v) {mApplyRuleGroupId = v;}

    public boolean getApplySkipFl() {return mApplySkipFl;}
    public void setApplySkipFl(boolean  v) {mApplySkipFl = v;}

    /**
     * Get the value of EmailUserName.
     * @return value of EmailUserName.
     */
    public String getEmailUserName() {
        return mEmailUserName;
    }
    
    /**
     * Set the value of EmailUserName.
     * @param v  Value to assign to EmailUserName.
     */
    public void setEmailUserName(String  v) {
        this.mEmailUserName = v;
    }
    
    /**
     * Get the value of EmailUserId.
     * @return value of EmailUserId.
     */
    public int getEmailUserId() {
        return mEmailUserId;
    }
    
    /**
     * Set the value of EmailUserId.
     * @param v  Value to assign to EmailUserId.
     */
    public void setEmailUserId(int  v) {
        this.mEmailUserId = v;
    }
    
    /**
     * Get the value of DaysUntilNextAction.
     * @return value of DaysUntilNextAction.
     */
    public int getDaysUntilNextAction() {
        return mDaysUntilNextAction;
    }
    
    /**
     * Set the value of DaysUntilNextAction.
     * @param v  Value to assign to DaysUntilNextAction.
     */
    public void setDaysUntilNextAction(int  v) {
        this.mDaysUntilNextAction = v;
    }
    
    /**
     * Get the value of RuleTypeCd.
     * @return value of RuleTypeCd.
     */
    public String getRuleTypeCd() {
        return mRuleTypeCd;
    }
    
    /**
     * Set the value of RuleTypeCd.
     * @param v  Value to assign to RuleTypeCd.
     */
    public void setRuleTypeCd(String  v) {
        this.mRuleTypeCd = v;
    }

    /**
     * Get the value of NewRule.
     * @return value of NewRule.
     */
//    public WorkflowRuleData getNewRule() {
//        return mNewRule;
//    }
    
    /**
     * Set the value of NewRule.
     * @param v  Value to assign to NewRule.
     */
//    public void setNewRule(WorkflowRuleData  v) {
//        this.mNewRule = v;
//    }
    
    /**
     * Get the value of OriginalData.
     * @return value of OriginalData.
     */
    public WorkflowData getOriginalData() {
        if ( null == mOriginalData ) {
            mOriginalData = WorkflowData.createValue();
        }
        return mOriginalData;
    }
    
    /**
     * Set the value of OriginalData.
     * @param v  Value to assign to OriginalData.
     */
    public void setOriginalData(WorkflowData  v) {
        this.mOriginalData = v;
    }
    
    /**
     * Get the value of BusEntityId.
     * @return value of BusEntityId.
     */
    public int getBusEntityId() {
        return mBusEntityId;
    }

    /**
     * Holds value of property nonOrderGuideItemAction.
     */
    private String nonOrderGuideItemAction;
    
    /**
     * Set the value of BusEntityId.
     * @param v  Value to assign to BusEntityId.
     */
    public void setBusEntityId(int  v) {
        this.mBusEntityId = v;
    }
    
    private ArrayList mRules = new ArrayList();

    /**
     * Get the value of Rules.
     * @return value of Rules.
     */
    public ArrayList getRules() {
        return mRules;
    }
    
    /**
     * Set the value of Rules.
     * @param v  Value to assign to Rules.
     */
    public void setRules(ArrayList  v) {
        this.mRules = v;
    }
    
    /** Getter for property BudgetExp.
     * @return Value of property BudgetExp.
     */
    public java.lang.String getBudgetExp() {
        return mBudgetExp;
    }
    
    /** Setter for property BudgetExp.
     * @param BudgetExp New value of property BudgetExp.
     */
    public void setBudgetExp(java.lang.String BudgetExp) {
        this.mBudgetExp = BudgetExp;
    }
    
    /** Getter for property BudgetValue.
     * @return Value of property BudgetValue.
     */
    public java.lang.String getBudgetValue() {
        return mBudgetValue;
    }
    
    /** Setter for property BudgetValue.
     * @param BudgetValue New value of property BudgetValue.
     */
    public void setBudgetValue(java.lang.String BudgetValue) {
        this.mBudgetValue = BudgetValue;
    }
    
    
    /** Getter for property BudgetAction.
     * @return Value of property BudgetAction.
     */
//    public java.lang.String getBudgetAction() {
//        return mBudgetAction;
//    }
    
    /** Setter for property BudgetAction.
     * @param BudgetAction New value of property BudgetAction.
     */
//    public void setBudgetAction(java.lang.String BudgetAction) {
//        this.mBudgetAction = BudgetAction;
//    }
    
    /** Getter for property TotalExp.
     * @return Value of property TotalExp.
     */
    public java.lang.String getTotalExp() {
        return mTotalExp;
    }
    
    /** Setter for property TotalExp.
     * @param TotalExp New value of property TotalExp.
     */
    public void setTotalExp(java.lang.String TotalExp) {
        this.mTotalExp = TotalExp;
    }
    
    
    /** Getter for property TotalValue.
     * @return Value of property TotalValue.
     */
    public java.lang.String getTotalValue() {
        return mTotalValue;
    }
    
    /** Setter for property TotalValue.
     * @param TotalValue New value of property TotalValue.
     */
    public void setTotalValue(java.lang.String TotalValue) {
        this.mTotalValue = TotalValue;
    }
    
    /** Getter for property TotalAction.
     * @return Value of property TotalAction.
     */
//    public java.lang.String getTotalAction() {
//        return mTotalAction;
//    }
    
    /** Setter for property TotalAction.
     * @param TotalAction New value of property TotalAction.
     */
//    public void setTotalAction(java.lang.String TotalAction) {
//        this.mTotalAction = TotalAction;
//    }

    /** Getter for property RuleAction.
     * @return Value of property RuleAction.
     */
    public java.lang.String getRuleAction() {
        return mRuleAction;
    }
    
    /** Setter for property RuleAction.
     * @param RuleAction New value of property RuleAction.
     */
    public void setRuleAction(java.lang.String RuleAction) {
        this.mRuleAction = RuleAction;
    }
    
    /** Getter for property NextActionCd.
     * @return Value of property NextActionCd.
     */
    public java.lang.String getNextActionCd() {
        return mNextActionCd;
    }
    
    /** Setter for property NextActionCd.
     * @param NextActionCd New value of property NextActionCd.
     */
    public void setNextActionCd(java.lang.String NextActionCd) {
        this.mNextActionCd = NextActionCd;
    }

    public java.lang.String getTimespanInDays() {
        return mTimespanInDays;
    }
    
    public void setTimespanInDays(java.lang.String TimespanInDays) {
        this.mTimespanInDays = TimespanInDays;
    }
 
//    public java.lang.String getVelocityAction() {
//        return mVelocityAction;
//    }
    
//    public void setVelocityAction(java.lang.String VelocityAction) {
//        this.mVelocityAction = VelocityAction;
//    }
        

    public java.lang.String getOrderSku() {
        return mOrderSku;
    }
    
    public void setOrderSku(java.lang.String OrderSku) {
        this.mOrderSku = OrderSku;
    }
    
//    public java.lang.String getSkuAction() {
//        return mSkuAction;
//    }
    
//    public void setSkuAction(java.lang.String SkuAction) {
//        this.mSkuAction = SkuAction;
//    }
    
    public int getRuleSeq() {
        return mRuleSeq;
    }
    
    public void setRuleSeq(int pRuleSeq) {
        this.mRuleSeq = pRuleSeq;
    }

    public String getRuleGroup() {
        return mRuleGroup;
    }
    
    public void setRuleGroup(String pRuleGroup) {
        this.mRuleGroup = pRuleGroup;
    }

    public String getDistId() {return mDistId;}
    public void setDistId( String pVal)  {mDistId = pVal;}

    public String getDistName() {return mDistName;}
    public void setDistName( String pVal)  {mDistName = pVal;}

    /**
     * Getter for property nonOrderGuideItemAction.
     * @return Value of property nonOrderGuideItemAction.
     */
//    public String getNonOrderGuideItemAction() {
//        return this.nonOrderGuideItemAction;
//    }

    /**
     * Setter for property nonOrderGuideItemAction.
     * @param nonOrderGuideItemAction New value of property nonOrderGuideItemAction.
     */
//    public void setNonOrderGuideItemAction(String nonOrderGuideItemAction) {
//        this.nonOrderGuideItemAction = nonOrderGuideItemAction;
//    }
   
    public String getStateList() {return mStateList;}
    public void setStateList( String pVal)  {mStateList = pVal;}
}

