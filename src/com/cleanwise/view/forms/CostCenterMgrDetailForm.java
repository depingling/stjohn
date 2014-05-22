/**
 *  Title: CostCenterMgrDetailForm 
 *  Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     tbesser
 */
package com.cleanwise.view.forms;


import com.cleanwise.service.api.value.BudgetData;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


/**
 *  Form bean for the cost center detail page.
 *
 *@author     tbesser
 *@created    August 27, 2001
 */
public final class CostCenterMgrDetailForm
    extends Base2DetailForm {

    private String mBudgetAmount;

    private String mBudgetTotal;
    private String mBudgetPeriod;
    private String mBudgetStartDate;
    private String mCity;
    private String mState;
    private String mSearchField;
    private String mSearchType;
    private ArrayList mSiteBudgetList;

    /**
     *  Sets the BudgetAmount attribute of the CostCenterMgrDetailForm object
     *
     *@param  mBudgetAmount  The new BudgetAmount value
     */
    public void setBudgetAmount(String v) {
        this.mBudgetAmount = v;
    }


    /**
     *  Gets the BudgetAmount attribute of the CostCenterMgrDetailForm object
     *
     *@return    The BudgetAmount value
     */
    public String getBudgetAmount() {

        return mBudgetAmount;
    }

    /**
     *  Sets the BudgetTotal attribute of the CostCenterMgrDetailForm object
     *
     *@param  mBudgetTotal  The new BudgetTotal value
     */
    public void setBudgetTotal(String pBudgetTotal) {
        this.mBudgetTotal = pBudgetTotal;
    }

    /**
     *  Gets the BudgetTotal attribute of the CostCenterMgrDetailForm object
     *
     *@return    The BudgetTotal value
     */
    public String getBudgetTotal() {

        return mBudgetTotal;
    }

    /**
     *  Sets the BudgetPeriod attribute of the CostCenterMgrDetailForm object
     *
     *@param  mBudgetPeriod  The new BudgetPeriod value
     */
    public void setBudgetPeriod(String pBudgetPeriod) {
        this.mBudgetPeriod = pBudgetPeriod;
    }

    /**
     *  Gets the BudgetPeriod attribute of the CostCenterMgrDetailForm object
     *
     *@return    The BudgetPeriod value
     */
    public String getBudgetPeriod() {

        if (null == mBudgetPeriod)

            return "";

        return mBudgetPeriod;
    }

    /**
     *  Sets the BudgetStartDate value of the CostCenterMgrDetailForm object
     *
     *@param  mBudgetStartDate  The new BudgetStartDate value
     */
    public void setBudgetStartDate(String pBudgetStartDate) {
        this.mBudgetStartDate = pBudgetStartDate;
    }

    /**
     *  Gets the BudgetStartDate value of the CostCenterMgrDetailForm object
     *
     *@return    The BudgetStartDate value
     */
    public String getBudgetStartDate() {

        return mBudgetStartDate;
    }

    /**
     *  Sets the City value of the CostCenterMgrDetailForm object
     *
     *@param  mCity  The new City value
     */
    public void setCity(String pCity) {
        this.mCity = pCity;
    }

    /**
     *  Gets the City value of the CostCenterMgrDetailForm object
     *
     *@return    The City value
     */
    public String getCity() {

        return mCity;
    }

    /**
     *  Sets the State value of the CostCenterMgrDetailForm object
     *
     *@param  mState  The new State value
     */
    public void setState(String pState) {
        this.mState = pState;
    }

    /**
     *  Gets the State value of the CostCenterMgrDetailForm object
     *
     *@return    The State value
     */
    public String getState() {

        return mState;
    }

    /**
     *  Sets the SearchField value of the CostCenterMgrDetailForm object
     *
     *@param  mSearchField  The new SearchField value
     */
    public void setSearchField(String pSearchField) {
        this.mSearchField = pSearchField;
    }

    /**
     *  Gets the SearchField value of the CostCenterMgrDetailForm object
     *
     *@return    The SearchField value
     */
    public String getSearchField() {

        return mSearchField;
    }

    /**
     *  Sets the SearchType value of the CostCenterMgrDetailForm object
     *
     *@param  mSearchType  The new SearchType value
     */
    public void setSearchType(String pSearchType) {
        this.mSearchType = pSearchType;
    }

    /**
     *  Gets the SearchType value of the CostCenterMgrDetailForm object
     *
     *@return    The SearchType value
     */
    public String getSearchType() {

        return mSearchType;
    }

    /**
     *  Sets the BudgetAmount attribute of the CostCenterMgrDetailForm object
     *
     *@param  mBudgetAmount  The new BudgetAmount value
     */
    public void setSiteBudgetList(ArrayList pSiteBudgetList) {
        this.mSiteBudgetList = pSiteBudgetList;
    }

    public SiteBudget getSiteBudget(int index) {

        return (SiteBudget)mSiteBudgetList.get(index);
    }

    /**
     *  Gets the BudgetAmount attribute of the CostCenterMgrDetailForm object
     *
     *@return    The BudgetAmount value
     */
    public ArrayList getSiteBudgetList() {

        return mSiteBudgetList;
    }

    /**
     *  <code>reset</code> method, set the search fields to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {

        return;
    }

    /**
     *  <code>validate</code> method is a stub.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     *@return          an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping, 
                                 HttpServletRequest request) {

        // Validation happens in the logic bean.
        return super.validate(mapping, request);
    }

    private String mBudgetMMDD1;

    public void setBudgetMMDD1(String v) {
        this.mBudgetMMDD1 = v;
    }

    public String getBudgetMMDD1() {

        if (null == mBudgetMMDD1)

            return "";

        return mBudgetMMDD1;
    }

    private String mBudgetMMDD2;

    public void setBudgetMMDD2(String v) {
        this.mBudgetMMDD2 = v;
    }

    public String getBudgetMMDD2() {

        if (null == mBudgetMMDD2)

            return "";

        return mBudgetMMDD2;
    }

    private String mBudgetMMDD3;

    public void setBudgetMMDD3(String v) {
        this.mBudgetMMDD3 = v;
    }

    public String getBudgetMMDD3() {

        if (null == mBudgetMMDD3)

            return "";

        return mBudgetMMDD3;
    }

    private String mBudgetMMDD4;

    public void setBudgetMMDD4(String v) {
        this.mBudgetMMDD4 = v;
    }

    public String getBudgetMMDD4() {

        if (null == mBudgetMMDD4)

            return "";

        return mBudgetMMDD4;
    }

    private String mBudgetMMDD5;

    public void setBudgetMMDD5(String v) {
        this.mBudgetMMDD5 = v;
    }

    public String getBudgetMMDD5() {

        if (null == mBudgetMMDD5)

            return "";

        return mBudgetMMDD5;
    }

    private String mBudgetMMDD6;

    public void setBudgetMMDD6(String v) {
        this.mBudgetMMDD6 = v;
    }

    public String getBudgetMMDD6() {

        if (null == mBudgetMMDD6)

            return "";

        return mBudgetMMDD6;
    }

    private String mBudgetMMDD7;

    public void setBudgetMMDD7(String v) {
        this.mBudgetMMDD7 = v;
    }

    public String getBudgetMMDD7() {

        if (null == mBudgetMMDD7)

            return "";

        return mBudgetMMDD7;
    }

    private String mBudgetMMDD8;

    public void setBudgetMMDD8(String v) {
        this.mBudgetMMDD8 = v;
    }

    public String getBudgetMMDD8() {

        if (null == mBudgetMMDD8)

            return "";

        return mBudgetMMDD8;
    }

    private String mBudgetMMDD9;

    public void setBudgetMMDD9(String v) {
        this.mBudgetMMDD9 = v;
    }

    public String getBudgetMMDD9() {

        if (null == mBudgetMMDD9)

            return "";

        return mBudgetMMDD9;
    }

    private String mBudgetMMDD10;

    public void setBudgetMMDD10(String v) {
        this.mBudgetMMDD10 = v;
    }

    public String getBudgetMMDD10() {

        if (null == mBudgetMMDD10)

            return "";

        return mBudgetMMDD10;
    }

    private String mBudgetMMDD11;

    public void setBudgetMMDD11(String v) {
        this.mBudgetMMDD11 = v;
    }

    public String getBudgetMMDD11() {

        if (null == mBudgetMMDD11)

            return "";

        return mBudgetMMDD11;
    }

    private String mBudgetMMDD12;

    public void setBudgetMMDD12(String v) {
        this.mBudgetMMDD12 = v;
    }

    public String getBudgetMMDD12() {

        if (null == mBudgetMMDD12)

            return "";

        return mBudgetMMDD12;
    }

    private String mBudgetMMDD13;
    /**
     * Holds value of property costCenterTypeCd.
     */
    private String costCenterTypeCd;

    /**
     * Holds value of property budgetAmount1.
     */
    private String budgetAmount1;

    /**
     * Holds value of property budgetAmount2.
     */
    private String budgetAmount2;

    /**
     * Holds value of property budgetAmount3.
     */
    private String budgetAmount3;

    /**
     * Holds value of property budgetAmount4.
     */
    private String budgetAmount4;

    /**
     * Holds value of property budgetAmount5.
     */
    private String budgetAmount5;

    /**
     * Holds value of property budgetAmount6.
     */
    private String budgetAmount6;

    /**
     * Holds value of property budgetAmount7.
     */
    private String budgetAmount7;

    /**
     * Holds value of property budgetAmount8.
     */
    private String budgetAmount8;

    /**
     * Holds value of property budgetAmount9.
     */
    private String budgetAmount9;

    /**
     * Holds value of property budgetAmount10.
     */
    private String budgetAmount10;

    /**
     * Holds value of property budgetAmount11.
     */
    private String budgetAmount11;

    /**
     * Holds value of property budgetAmount12.
     */
    private String budgetAmount12;

    /**
     * Holds value of property budgetAmount13.
     */
    private String budgetAmount13;

    /**
     * Holds value of property budget.
     */
    private BudgetData budget;


    public void setBudgetMMDD13(String v) {
        this.mBudgetMMDD13 = v;
    }

    public String getBudgetMMDD13() {

        if (null == mBudgetMMDD13)

            return "";

        return mBudgetMMDD13;
    }

    /**
     * Getter for property costCenterTypeCd.
     * @return Value of property costCenterTypeCd.
     */
    public String getCostCenterTypeCd() {

        return this.costCenterTypeCd;
    }

    /**
     * Setter for property costCenterTypeCd.
     * @param costCenterTypeCd New value of property costCenterTypeCd.
     */
    public void setCostCenterTypeCd(String costCenterTypeCd) {

        this.costCenterTypeCd = costCenterTypeCd;
    }

    /**
     * Getter for property budgetAmount1.
     * @return Value of property budgetAmount1.
     */
    public String getBudgetAmount1() {

        return this.budgetAmount1;
    }

    /**
     * Setter for property budgetAmount1.
     * @param budgetAmount1 New value of property budgetAmount1.
     */
    public void setBudgetAmount1(String budgetAmount1) {

        this.budgetAmount1 = budgetAmount1;
    }

    /**
     * Getter for property budgetAmount2.
     * @return Value of property budgetAmount2.
     */
    public String getBudgetAmount2() {

        return this.budgetAmount2;
    }

    /**
     * Setter for property budgetAmount2.
     * @param budgetAmount2 New value of property budgetAmount2.
     */
    public void setBudgetAmount2(String budgetAmount2) {

        this.budgetAmount2 = budgetAmount2;
    }

    /**
     * Getter for property budgetAmount3.
     * @return Value of property budgetAmount3.
     */
    public String getBudgetAmount3() {

        return this.budgetAmount3;
    }

    /**
     * Setter for property budgetAmount3.
     * @param budgetAmount3 New value of property budgetAmount3.
     */
    public void setBudgetAmount3(String budgetAmount3) {

        this.budgetAmount3 = budgetAmount3;
    }

    /**
     * Getter for property budgetAmount4.
     * @return Value of property budgetAmount4.
     */
    public String getBudgetAmount4() {

        return this.budgetAmount4;
    }

    /**
     * Setter for property budgetAmount4.
     * @param budgetAmount4 New value of property budgetAmount4.
     */
    public void setBudgetAmount4(String budgetAmount4) {

        this.budgetAmount4 = budgetAmount4;
    }

    /**
     * Getter for property budgetAmount5.
     * @return Value of property budgetAmount5.
     */
    public String getBudgetAmount5() {

        return this.budgetAmount5;
    }

    /**
     * Setter for property budgetAmount5.
     * @param budgetAmount5 New value of property budgetAmount5.
     */
    public void setBudgetAmount5(String budgetAmount5) {

        this.budgetAmount5 = budgetAmount5;
    }

    /**
     * Getter for property budgetAmount6.
     * @return Value of property budgetAmount6.
     */
    public String getBudgetAmount6() {

        return this.budgetAmount6;
    }

    /**
     * Setter for property budgetAmount6.
     * @param budgetAmount6 New value of property budgetAmount6.
     */
    public void setBudgetAmount6(String budgetAmount6) {

        this.budgetAmount6 = budgetAmount6;
    }

    /**
     * Getter for property budgetAmount7.
     * @return Value of property budgetAmount7.
     */
    public String getBudgetAmount7() {

        return this.budgetAmount7;
    }

    /**
     * Setter for property budgetAmount7.
     * @param budgetAmount7 New value of property budgetAmount7.
     */
    public void setBudgetAmount7(String budgetAmount7) {

        this.budgetAmount7 = budgetAmount7;
    }

    /**
     * Getter for property budgetAmount8.
     * @return Value of property budgetAmount8.
     */
    public String getBudgetAmount8() {

        return this.budgetAmount8;
    }

    /**
     * Setter for property budgetAmount8.
     * @param budgetAmount8 New value of property budgetAmount8.
     */
    public void setBudgetAmount8(String budgetAmount8) {

        this.budgetAmount8 = budgetAmount8;
    }

    /**
     * Getter for property budgetAmount9.
     * @return Value of property budgetAmount9.
     */
    public String getBudgetAmount9() {

        return this.budgetAmount9;
    }

    /**
     * Setter for property budgetAmount9.
     * @param budgetAmount9 New value of property budgetAmount9.
     */
    public void setBudgetAmount9(String budgetAmount9) {

        this.budgetAmount9 = budgetAmount9;
    }

    /**
     * Getter for property budgetAmount10.
     * @return Value of property budgetAmount10.
     */
    public String getBudgetAmount10() {

        return this.budgetAmount10;
    }

    /**
     * Setter for property budgetAmount10.
     * @param budgetAmount10 New value of property budgetAmount10.
     */
    public void setBudgetAmount10(String budgetAmount10) {

        this.budgetAmount10 = budgetAmount10;
    }

    /**
     * Getter for property budgetAmount11.
     * @return Value of property budgetAmount11.
     */
    public String getBudgetAmount11() {

        return this.budgetAmount11;
    }

    /**
     * Setter for property budgetAmount11.
     * @param budgetAmount11 New value of property budgetAmount11.
     */
    public void setBudgetAmount11(String budgetAmount11) {

        this.budgetAmount11 = budgetAmount11;
    }

    /**
     * Getter for property budgetAmount12.
     * @return Value of property budgetAmount12.
     */
    public String getBudgetAmount12() {

        return this.budgetAmount12;
    }

    /**
     * Setter for property budgetAmount12.
     * @param budgetAmount12 New value of property budgetAmount12.
     */
    public void setBudgetAmount12(String budgetAmount12) {

        this.budgetAmount12 = budgetAmount12;
    }

    /**
     * Getter for property budgetAmount13.
     * @return Value of property budgetAmount13.
     */
    public String getBudgetAmount13() {

        return this.budgetAmount13;
    }

    /**
     * Setter for property budgetAmount13.
     * @param budgetAmount13 New value of property budgetAmount13.
     */
    public void setBudgetAmount13(String budgetAmount13) {

        this.budgetAmount13 = budgetAmount13;
    }

    /**
     * Getter for property budget.
     * @return Value of property budget.
     */
    public BudgetData getBudget() {

        return this.budget;
    }

    /**
     * Setter for property budget.
     * @param budget New value of property budget.
     */
    public void setBudget(BudgetData budget) {

        this.budget = budget;
    }

    

}
