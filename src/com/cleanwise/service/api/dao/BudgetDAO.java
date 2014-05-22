package com.cleanwise.service.api.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BudgetData;
import com.cleanwise.service.api.value.BudgetDataVector;
import com.cleanwise.service.api.value.BudgetDetailData;
import com.cleanwise.service.api.value.BudgetDetailDataVector;
import com.cleanwise.service.api.value.BudgetView;
import com.cleanwise.service.api.value.BudgetViewVector;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.CostCenterDataVector;
import com.cleanwise.service.api.value.FiscalCalenderData;
import com.cleanwise.service.api.value.IdVector;


public class BudgetDAO {

    private static final Logger log = Logger.getLogger(BudgetDAO.class);
    private static final Comparator<BudgetData> BUDGET_ADD_DATE_COMPARE = new Comparator<BudgetData>() {
        public int compare(BudgetData o1, BudgetData o2) {
            Date date1 = o1.getAddDate();
            Date date2 = o2.getAddDate();
            return date1.compareTo(date2);
        }
    };

    public static BudgetDataVector getBudgetDataVector(Connection pCon,
                                                       int pBusEntityId,
                                                       int pCostCenterId,
                                                       int pBudgetYear,
                                                       List pBudgetTypes) throws SQLException {
        return getBudgetDataVector(pCon,
                pBusEntityId,
                pCostCenterId,
                pBudgetYear,
                pBudgetTypes,
                Utility.getAsList(RefCodeNames.BUDGET_STATUS_CD.ACTIVE));

    }

    public static BudgetDataVector getBudgetDataVector(Connection pCon,
                                                       int pBusEntityId,
                                                       int pCostCenterId,
                                                       int pBudgetYear,
                                                       List pBudgetTypes,
                                                       List pStatusCds) throws SQLException {

        DBCriteria crit = new DBCriteria();

        crit.addEqualTo(BudgetDataAccess.BUS_ENTITY_ID, pBusEntityId);
        crit.addEqualTo(BudgetDataAccess.BUDGET_YEAR, pBudgetYear);

        if (pCostCenterId > 0) {
            crit.addEqualTo(BudgetDataAccess.COST_CENTER_ID, pCostCenterId);
        }

        if (pStatusCds != null && !pStatusCds.isEmpty()) {
            crit.addOneOf(BudgetDataAccess.BUDGET_STATUS_CD, pStatusCds);
        }

        if (pBudgetTypes != null && !pBudgetTypes.isEmpty()) {
            crit.addOneOf(BudgetDataAccess.BUDGET_TYPE_CD, pBudgetTypes);
        }

        log.debug("getBudgetDataVector => SQL: " + BudgetDataAccess.getSqlSelectIdOnly("*", crit));

        return BudgetDataAccess.select(pCon, crit);

    }
    
    public static BudgetViewVector getAllBudgetsForSites(Connection pCon, ArrayList sites, int year) throws SQLException {
    	
    	BudgetViewVector budgetViewV = new BudgetViewVector();
    	
    	DBCriteria dbc = new DBCriteria();
    	dbc.addOneOf(BudgetDataAccess.BUS_ENTITY_ID, sites);
    	dbc.addEqualTo(BudgetDataAccess.BUDGET_YEAR, year);
    	
    	BudgetDataVector siteBudgets = BudgetDataAccess.select(pCon, dbc);
    	BudgetViewVector sitesBudgetsView = getBudgets(pCon, siteBudgets);
    	if(sitesBudgetsView != null && sitesBudgetsView.size()>0){
    		budgetViewV.addAll(sitesBudgetsView);
    	}
    	
    	ArrayList accounts = BusEntityDAO.getAccountsForSites(pCon, sites);
    	
    	dbc = new DBCriteria();
    	dbc.addOneOf(BudgetDataAccess.BUS_ENTITY_ID, accounts);
    	dbc.addEqualTo(BudgetDataAccess.BUDGET_YEAR, year);
    	
    	BudgetDataVector acctBudgets = BudgetDataAccess.select(pCon, dbc);
    	BudgetViewVector acctBudgetsView = getBudgets(pCon, acctBudgets);
    	
    	if(acctBudgetsView != null && acctBudgetsView.size()>0){
    		budgetViewV.addAll(acctBudgetsView);
    	}
    	
    	return budgetViewV;
    }

    public static BudgetViewVector getBudgets(Connection pCon,
                                              int pBusEntityId,
                                              int pCostCenterId,
                                              int pBudgetYear,
                                              List pBudgetTypes) throws SQLException {

        BudgetDataVector budgets = getBudgetDataVector(pCon, pBusEntityId, pCostCenterId, pBudgetYear, pBudgetTypes);
        return getBudgets(pCon, budgets);
    }

    public static BudgetView getBudget(Connection pCon, BudgetData budget) throws SQLException {

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(BudgetDetailDataAccess.BUDGET_ID, budget.getBudgetId());
        dbc.addOrderBy(BudgetDetailDataAccess.BUDGET_ID);
        dbc.addOrderBy(BudgetDetailDataAccess.PERIOD);

        BudgetDetailDataVector details = BudgetDetailDataAccess.select(pCon, dbc);

        return new BudgetView(budget, details);
    }

    public static BudgetViewVector getBudgets(Connection pCon, BudgetDataVector pBudgets) throws SQLException {

        BudgetViewVector result = null;

        if (pBudgets == null) {
            return result;
        }

        result = new BudgetViewVector();

        if (!pBudgets.isEmpty()) {

            IdVector budgetIds = Utility.toIdVector(pBudgets);

            HashMap<Integer, BudgetDetailDataVector> budgetDetailsMap = new HashMap<Integer, BudgetDetailDataVector>();

            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(BudgetDetailDataAccess.BUDGET_ID, budgetIds);
            dbc.addOrderBy(BudgetDetailDataAccess.BUDGET_ID);
            dbc.addOrderBy(BudgetDetailDataAccess.PERIOD);

            BudgetDetailDataVector budgetDetailDataVector = BudgetDetailDataAccess.select(pCon, dbc);

            //prepare hash map for quick search of the detail data of budget
            for (Object oBudgetDetailData : budgetDetailDataVector) {
                BudgetDetailData budgetDetail = (BudgetDetailData) oBudgetDetailData;
                int budgetId = budgetDetail.getBudgetId();
                BudgetDetailDataVector budgetDetails = budgetDetailsMap.get(budgetId);
                if (budgetDetails == null) {
                    budgetDetails = new BudgetDetailDataVector();
                    budgetDetailsMap.put(budgetDetail.getBudgetId(), budgetDetails);
                }
                budgetDetails.add(budgetDetail);
            }

            // to merge the data of the budget with details and add to results
            for (Object oBudget : pBudgets) {
                BudgetData bData = (BudgetData) oBudget;
                BudgetDetailDataVector details = budgetDetailsMap.get(bData.getBudgetId());
                if (details == null) {
                    details = new BudgetDetailDataVector();
                }
                result.add(new BudgetView(bData, details));
            }

        }

        return result;

    }

    /**
     * Retrieves the budget data for this site.  Includes account level budgets if that is what this site is configured for.
     *
     * @param pCon          connection object
     * @param pSiteId       the site id
     * @param pAccountId    the account id (needed to find the account elvel budgeting information)
     * @param pCostCenterId the optional pCostCenterId if the results should be restricted by cost center, if not pass in 0
     * @param pBudgetYear   the budget year
     * @return the budget data for this site
     * @throws SQLException if an SqlException
     */
    public static BudgetDataVector getBudgetDataForSite(Connection pCon,
                                                        int pAccountId,
                                                        int pSiteId,
                                                        int pCostCenterId,
                                                        int pBudgetYear) throws SQLException {

        BudgetDataVector budgets = new BudgetDataVector();

        BudgetDataVector siteBudgets = getBudgetDataVector(pCon,
                pSiteId,
                pCostCenterId,
                pBudgetYear,
                Utility.getAsList(RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET));
        
        if (pAccountId == 0 && pSiteId > 0) {
            pAccountId = BusEntityDAO.getAccountForSite(pCon, pSiteId);
        }
        
        /** Currently there is bug that site budget do not get removed from cost center 
         * when the cost center is removed from the account. So add additional filter from account
         * cost center list
         */
        if (pCostCenterId == 0){ 
        	Account accountBean;
			try {
				accountBean = APIAccess.getAPIAccess().getAccountAPI();
				CostCenterDataVector costCenters =
	                accountBean.getAllCostCenters(pAccountId, Account.ORDER_BY_NAME);
				List costCenterIds = new ArrayList();
				for (int i = 0; i < costCenters.size(); i++){
					costCenterIds.add(((CostCenterData)costCenters.get(i)).getCostCenterId());
				}
				for (int i = 0; i < siteBudgets.size(); i++){
					BudgetData siteBudget = (BudgetData) siteBudgets.get(i);
					if (costCenterIds.contains(siteBudget.getCostCenterId())){
						budgets.add(siteBudget);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
        }else{        
        	budgets.addAll(siteBudgets);
        }


        BudgetDataVector accountBudgets = getBudgetDataVector(pCon,
                pAccountId,
                pCostCenterId,
                pBudgetYear,
                Utility.getAsList(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET));

        budgets.addAll(accountBudgets);


        return budgets;
    }

    public static BudgetViewVector getBudgetsForSite(Connection pCon,
                                                     int pAccountId,
                                                     int pSiteId,
                                                     int pCostCenterId,
                                                     int pBudgetYear) throws SQLException {
        BudgetDataVector budgets = getBudgetDataForSite(pCon, pAccountId, pSiteId, pCostCenterId, pBudgetYear);
        return getBudgets(pCon, budgets);
    }

    public static BudgetView updateBudget(Connection pCon, BudgetView pBudget, String pUserName) throws Exception {

        log.info("updateBudget => BEGIN.BudgetId:" + pBudget.getBudgetData().getBudgetId());

        if (readyForSave(pBudget)) {

            if (pBudget.getBudgetData().getBudgetId() > 0) {

                BudgetView currentBudget = getBudgetById(pCon, pBudget.getBudgetData().getBudgetId());
                log.info("updateBudget => updating current budget.new data:" + pBudget.getBudgetData());
                pBudget = updateBudget(pCon, pBudget, currentBudget, pUserName);

            } else {

                BudgetDataVector currentBudgets = getBudgetDataVector(pCon,
                        pBudget.getBudgetData().getBusEntityId(),
                        pBudget.getBudgetData().getCostCenterId(),
                        pBudget.getBudgetData().getBudgetYear(),
                        Utility.getAsList(pBudget.getBudgetData().getBudgetTypeCd()),
                        Utility.getAsList(RefCodeNames.BUDGET_STATUS_CD.ACTIVE, RefCodeNames.BUDGET_STATUS_CD.INACTIVE));


                if (currentBudgets.isEmpty()) {
                    log.info("updateBudget => creating new budget:" + pBudget.getBudgetData());
                    pBudget = insertBudget(pCon, pBudget, pUserName);
                } else {
                    Collections.sort(currentBudgets, BUDGET_ADD_DATE_COMPARE);

                    //refer to the last created budget and refresh it
                    BudgetData lastCreatedBudgetData = (BudgetData) currentBudgets.remove(0);
                    pBudget.getBudgetData().setBudgetId(lastCreatedBudgetData.getBudgetId());
                    pBudget.getBudgetData().setAddDate(new Date(System.currentTimeMillis()));
                    pBudget.getBudgetData().setAddBy(pUserName);

                    log.info("updateBudget => updating last created budget:" + lastCreatedBudgetData);

                    BudgetView lastCreatedBudget = getBudget(pCon, lastCreatedBudgetData);

                    pBudget = updateBudget(pCon, pBudget, lastCreatedBudget, pUserName);

                    //remove any other budgets
                    log.info("updateBudget => removing any other budgets.");
                    for (Object oBudget : currentBudgets) {
                        BudgetData budgetData = ((BudgetData) oBudget);
                        if (RefCodeNames.BUDGET_STATUS_CD.ACTIVE.equals(budgetData.getBudgetStatusCd())) {
                            budgetData.setBudgetStatusCd(RefCodeNames.BUDGET_STATUS_CD.INACTIVE);
                            updateBudgetData(pCon, budgetData, pUserName);
                        }
                    }
                }
            }
        }

        log.info("updateBudget => END.BudgetId:" + pBudget.getBudgetData().getBudgetId());

        return pBudget;
    }

    private static boolean readyForSave(BudgetView pBudget) {
        return pBudget.getBudgetData().getBusEntityId() > 0 &&
                pBudget.getBudgetData().getCostCenterId() > 0 &&
                pBudget.getBudgetData().getBudgetYear() > 0 &&
                Utility.isSet(pBudget.getBudgetData().getBudgetTypeCd());
    }

    private static BudgetView getBudgetById(Connection pCon, int pBudgetId) throws Exception {

        BudgetData bd = BudgetDataAccess.select(pCon, pBudgetId);

        return getBudget(pCon,bd);
    }


    private static BudgetView updateBudget(Connection pCon, BudgetView pBudgetNew, BudgetView pBudgetFound, String pUserName) throws SQLException {

        BudgetData budgetData = pBudgetNew.getBudgetData();
        budgetData = updateBudgetData(pCon, budgetData, pUserName);
        BudgetDetailDataVector newDetails = updateBudgetDetailData(pCon, budgetData, pBudgetNew.getDetails(), pBudgetFound.getDetails(), pUserName);

        pBudgetNew.setBudgetData(budgetData);
        pBudgetNew.setDetails(newDetails);

        return pBudgetNew;
    }

    private static BudgetDetailDataVector updateBudgetDetailData(Connection pCon,
                                                           BudgetData pBudget,
                                                           BudgetDetailDataVector pNewDetails,
                                                           BudgetDetailDataVector pExistsDetails,
                                                           String pUserName) throws SQLException {
        Iterator existsDetailsIt;
        Iterator newDetailsIt;

        existsDetailsIt = pExistsDetails.iterator();
        while (existsDetailsIt.hasNext()) {
            BudgetDetailData existsDetailData = (BudgetDetailData) existsDetailsIt.next();
            newDetailsIt = pNewDetails.iterator();
            boolean found = false;
            while (newDetailsIt.hasNext()) {
                BudgetDetailData newDetailData = (BudgetDetailData) newDetailsIt.next();
                if (existsDetailData.getPeriod() == newDetailData.getPeriod()) {
                    newDetailData.setBudgetDetailId(existsDetailData.getBudgetDetailId());
                    newDetailData.setAddDate(existsDetailData.getAddDate());
                    newDetailData.setAddBy(existsDetailData.getAddBy());
                    found = true;
                    break;
                }
            }
            if (found) {
                existsDetailsIt.remove();
            }
        }

        existsDetailsIt = pExistsDetails.iterator();
        while (existsDetailsIt.hasNext()) {
            BudgetDetailData existsDetailData = (BudgetDetailData) existsDetailsIt.next();
            BudgetDetailDataAccess.remove(pCon, existsDetailData.getBudgetDetailId());
        }


        newDetailsIt = pNewDetails.iterator();
        while (newDetailsIt.hasNext()) {
            BudgetDetailData newDetailData = (BudgetDetailData) newDetailsIt.next();
            if (newDetailData.isDirty()) {
                newDetailData.setBudgetId(pBudget.getBudgetId());
                if (newDetailData.getBudgetDetailId() > 0) {
                    newDetailData.setModBy(pUserName);
                    BudgetDetailDataAccess.update(pCon, newDetailData);
                } else {
                    newDetailData.setAddBy(pUserName);
                    newDetailData.setModBy(pUserName);
                    newDetailData = BudgetDetailDataAccess.insert(pCon, newDetailData);
                }
            }
        }

        return pNewDetails;

    }

    private static BudgetData updateBudgetData(Connection pCon, BudgetData pBudgetData, String pUserName) throws SQLException {
        if (pBudgetData.isDirty()) {
            pBudgetData.setModBy(pUserName);
            BudgetDataAccess.update(pCon, pBudgetData);
        }
        return pBudgetData;
    }

    private static BudgetView insertBudget(Connection pCon, BudgetView budget, String pUserName) throws SQLException {

        BudgetData budgetData = budget.getBudgetData();

        if (budgetData.isDirty()) {

            if (!Utility.isSet(budgetData.getBudgetStatusCd())) {
                budgetData.setBudgetStatusCd(RefCodeNames.BUDGET_STATUS_CD.ACTIVE);
            }

            budgetData.setAddBy(pUserName);
            budgetData.setModBy(pUserName);

            budgetData = BudgetDataAccess.insert(pCon, budgetData);
            budget.setBudgetData(budgetData);

            for (Object o : budget.getDetails()) {
                BudgetDetailData detail = (BudgetDetailData) o;
                detail.setBudgetId(budgetData.getBudgetId());
                detail.setAddBy(pUserName);
                detail.setModBy(pUserName);
                detail = BudgetDetailDataAccess.insert(pCon, detail);
            }
        }

        return budget;

    }

    public static Integer getCurrentBudgetYear(Connection pCon, int pAccountId) throws Exception {

        log.debug("getCurrentBudgetYear => BEGIN.pAccountId:" + pAccountId);

        BusEntityDAO bDao = new BusEntityDAO();

        FiscalCalenderData fiscalCalender = bDao.getCurrentFiscalCalender(pCon, pAccountId);
        if (fiscalCalender == null) {
            log.debug("getCurrentBudgetYear => No fiscal calendar found.AccountId => " + pAccountId);
            return null;
        }

        int fiscalCalId = fiscalCalender.getFiscalCalenderId();
        int budgetYear = fiscalCalender.getFiscalYear();

        log.debug("getCurrentBudgetYear => END.pFiscalCalendarId: " + fiscalCalId + ", pBudgetYear: " + budgetYear);

        return budgetYear;
    }

}
