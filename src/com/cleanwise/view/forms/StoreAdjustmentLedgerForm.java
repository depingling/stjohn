package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import com.cleanwise.service.api.value.*;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.HashMap;
import java.math.BigDecimal;

/**
 * @author Alexander Chikin
 * Date: 06.10.2006
 * Time: 9:29:42
 */
public class StoreAdjustmentLedgerForm extends ActionForm {

   private String siteId="";
   private String accountId="";
   private String siteName="";
   private String accountName="";
   private  int currentBudgetYear=0;
   private int budgetYear=0;
   private String budgetPeriodType="";
   private HashMap<Integer, String> budgetPeriods=null;
   private String budgetAdjustment="";
   private String comments="";
   private String selectedBudgetYear="";
   private String selectedBudgetPeriod="";
   private String selectedBudgetPeriodType="";
   private String selectedCostCenter="";
   private String budgetTypeCd="";
   private FiscalCalenderViewVector fiscalCalendarCollection=null;
   private CostCenterDataVector costCentersCollection=null;
   private Hashtable<String,HashMap<Integer,BigDecimal>> htAdjustments=new Hashtable<String,HashMap<Integer,BigDecimal>>();
   private Hashtable<String, HashMap<Integer,Boolean>> htChangesAdjustmentsFl=new Hashtable<String,HashMap<Integer,Boolean>>();
   private Hashtable<String,HashMap<Integer,String>> htComments=new Hashtable<String,HashMap<Integer,String>>();
    private BudgetView budget;
    private BudgetViewVector siteBudgets = null;


    public int getCurrentBudgetYear() {
        return currentBudgetYear;
    }

    public void setCurrentBudgetYear(int currentBudgetYear) {
        this.currentBudgetYear = currentBudgetYear;
    }

    public int getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(int budgetYear) {
        this.budgetYear = budgetYear;
    }

    public HashMap<Integer, String> getBudgetPeriods() {
        return budgetPeriods;
    }

    public void setBudgetPeriods(HashMap<Integer,String> budgetPeriods) {
        this.budgetPeriods = budgetPeriods;
    }

    public String getBudgetPeriodType() {
        return budgetPeriodType;
    }

    public void setBudgetPeriodType(String budgetPeriodType) {
        this.budgetPeriodType = budgetPeriodType;
    }

    public String getBudgetAdjustment() {
        return budgetAdjustment;
    }

    public void setBudgetAdjustment(String bufgetAdjustment) {
        this.budgetAdjustment = bufgetAdjustment;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setSelectedBudgetYear(String selectedBudgetYear) {
        this. selectedBudgetYear = selectedBudgetYear;
    }

    public String getSelectedBudgetYear() {
        return selectedBudgetYear;
    }

    public void setSelectedBudgetPeriod(String selectedBudgetPeriod) {
        this.selectedBudgetPeriod = selectedBudgetPeriod;
    }

    public String getSelectedBudgetPeriod() {
        return selectedBudgetPeriod;
    }

    public void setSelectedBudgetPeriodType(String selectedBudgetPeriodType) {
        this.selectedBudgetPeriodType=selectedBudgetPeriodType;
    }

    public String getSelectedBudgetPeriodType() {
        return selectedBudgetPeriodType;
    }

    public void setSelectedCostCenter(String selectedCostCenter) {
        this.selectedCostCenter=selectedCostCenter;
    }

    public String getSelectedCostCenter() {
        return selectedCostCenter;
    }

    public void setFiscalCalendarCollection(FiscalCalenderViewVector fiscalCalendarCollection) {
        this.fiscalCalendarCollection = fiscalCalendarCollection;
    }

    public FiscalCalenderViewVector getFiscalCalendarCollection() {
        return fiscalCalendarCollection;
    }

    public CostCenterDataVector getCostCentersCollection() {
        return costCentersCollection;
    }

    public void setCostCentersCollection(CostCenterDataVector costCentersCollection) {
        this.costCentersCollection = costCentersCollection;
    }

    public Hashtable<String,HashMap<Integer,BigDecimal>> getHtAdjustments() {
        return htAdjustments;
    }

    public void setHtAdjustments(Hashtable<String, HashMap<Integer,BigDecimal>> htAdjustments) {
        this.htAdjustments = htAdjustments;
    }
    public void setBudgetTypeCd(String budgetTypeCd){
       this.budgetTypeCd=budgetTypeCd;
    }

    public String getBudgetTypeCd() {
        return budgetTypeCd;
    }

    public void setHtChangesAdjustmentsFl(Hashtable<String, HashMap<Integer,Boolean>> htChangesAdjustmentsFl) {
        this.htChangesAdjustmentsFl = htChangesAdjustmentsFl;
    }

    public Hashtable<String, HashMap<Integer,Boolean>> getHtChangesAdjustmentsFl() {
        return htChangesAdjustmentsFl;
    }

    public void setHtComments(Hashtable<String,HashMap<Integer,String>> htComments) {
        this.htComments=htComments;
    }

    public Hashtable<String,HashMap<Integer,String>> getHtComments() {
        return htComments;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setBudget(BudgetView budget) {
        this.budget = budget;
    }


    public BudgetView getBudget() {
        return budget;
    }

    public void setSiteBudgets(BudgetViewVector siteBudgets) {
        this.siteBudgets = siteBudgets;
    }

    public BudgetViewVector getSiteBudgets() {
        return siteBudgets;
    }
}
