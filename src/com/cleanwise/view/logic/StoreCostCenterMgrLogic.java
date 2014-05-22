/*
 * StoreCostCenterMgrLogic.java
 *
 * Created on June 15, 2005, 5:32 PM
 */

package com.cleanwise.view.logic;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import com.cleanwise.view.forms.*;
import com.cleanwise.view.utils.*;

import java.math.BigDecimal;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.log4j.Logger;



/**
 *
 * @author Ykupershmidt
 */
public class StoreCostCenterMgrLogic {

    private static final Logger log = Logger.getLogger(StoreCostCenterMgrLogic.class);

    private static ActionErrors getSiteBudgets(int accountId, int costCenterId,
                                       StoreCatalogMgrForm pForm,
                                       String pOptionalSiteId,
                                       HttpServletRequest pRequest)
                                throws Exception {
        ActionErrors ae = new ActionErrors();
        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        SiteDataVector sd = null;

        if (null != pOptionalSiteId) {

            SiteData thisSite = siteBean.getSite(Integer.parseInt(
                                                         pOptionalSiteId),
                                                 accountId,
                                                 false, SessionTool.getCategoryToCostCenterView(pRequest.getSession(), Integer.parseInt(pOptionalSiteId)));
            sd = new SiteDataVector();
            sd.add(thisSite);
        } else {

            sd = new SiteDataVector();
            String searchField = pForm.getSearchField();
            String searchType = pForm.getSearchType();
            if("id".equalsIgnoreCase(searchType)) {
              if(Utility.isSet(searchField)) {
                try {
                  Integer.parseInt(searchField);
                }catch (Exception exc) {
                  ae.add("error",new ActionError("error.simpleGenericError","Not integer site id"));
                  return ae;
                }
              } else {
                searchType = "";
              }
            }
            // first, get the site view vector from the site mgr logic
            SiteViewVector dv = SiteMgrLogic.search(pRequest, searchField, searchType, Integer.toString(accountId),
                    pForm.getCity(), pForm.getCounty(), pForm.getState(), pForm.getPostalCode());

            //now that we have SiteViewVector dv, fill sd
            for (int ii = 0; ii < dv.size(); ii++) {

                if (((SiteView)dv.get(ii)).getAccountId() == accountId)
                    sd.add(siteBean.getSite(((SiteView)dv.get(ii)).getId(),0, false, SessionTool.getCategoryToCostCenterView(pRequest.getSession(), ((SiteView)dv.get(ii)).getId())));
            }
        }

        BigDecimal budgetTotal = new BigDecimal(0);
        ArrayList budgets = new ArrayList();
        Iterator siteIter = sd.iterator();

        while (siteIter.hasNext()) {

            SiteBudget budgetInfo = new SiteBudget();
            SiteData site = (SiteData)siteIter.next();
            budgetInfo.setId(String.valueOf(site.getBusEntity().getBusEntityId()));
            budgetInfo.setName(site.getBusEntity().getShortDesc());

            AddressData address = site.getSiteAddress();
            budgetInfo.setCity(address.getCity());
            budgetInfo.setState(address.getStateProvinceCd());

            BudgetView budgetView = null;
            BudgetViewVector budgetv = site.getBudgets();
            Iterator budgetIter = budgetv.iterator();

            while (budgetIter.hasNext()) {

                 BudgetView budget = (BudgetView)budgetIter.next();

                if (budget.getBudgetData().getCostCenterId() == costCenterId) {

                    // Calculate the remaining budget amount.
                    BudgetSpendView spent = siteBean.getBudgetSpent(site.getBusEntity().getBusEntityId(),costCenterId);
                    budgetView = budget;
                    budgetInfo.setSiteBudgetRemaining(spent.getAmountSpent());

                    break;
                }
            }

            // if it doesn't yet have an explicit budget, it's zero
            if (budgetView == null) {

                BudgetData budgetData = BudgetData.createValue();
                budgetData.setBusEntityId(site.getBusEntity().getBusEntityId());
                budgetData.setCostCenterId(costCenterId);

                budgetView = new BudgetView(budgetData, new BudgetDetailDataVector());

                BigDecimal zero = new BigDecimal(0);
                budgetInfo.setSiteBudgetRemaining(zero);
            }

            budgetInfo.setBudgetView(budgetView);
            budgets.add(budgetInfo);
        }

        pForm.setSiteBudgetList(budgets);
        pForm.setBudgetTotal(CurrencyFormat.format(budgetTotal));
        return ae;
    }

    private static void getSiteBudgets(int accountId, int costCenterId,
                                       StoreCatalogMgrForm pForm,
                                       String pOptionalSiteId,
                                       HttpSession pSession)
                                throws Exception {

        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        SiteDataVector sd = null;

        if (null != pOptionalSiteId) {

            SiteData thisSite = siteBean.getSite
                (Integer.parseInt(pOptionalSiteId),accountId, false, SessionTool.getCategoryToCostCenterView(pSession, Integer.parseInt(pOptionalSiteId)));
            sd = new SiteDataVector();
            sd.add(thisSite);
        } else {
            sd = siteBean.getAllSites(accountId, Site.ORDER_BY_NAME, SessionTool.getCategoryToCostCenterViewByAcc(pSession, accountId));
        }

        BigDecimal budgetTotal = new BigDecimal(0);
        ArrayList budgets = new ArrayList();
        Iterator siteIter = sd.iterator();

        while (siteIter.hasNext()) {

            SiteBudget budgetInfo = new SiteBudget();
            SiteData site = (SiteData)siteIter.next();
            budgetInfo.setId(String.valueOf(site.getBusEntity().getBusEntityId()));
            budgetInfo.setName(site.getBusEntity().getShortDesc());

            AddressData address = site.getSiteAddress();
            budgetInfo.setCity(address.getCity());
            budgetInfo.setState(address.getStateProvinceCd());

            BudgetView budgetView = null;
            BudgetViewVector budgetv = site.getBudgets();
            Iterator budgetIter = budgetv.iterator();

            while (budgetIter.hasNext()) {

                BudgetView budget = (BudgetView) budgetIter.next();

                if (budget.getBudgetData().getCostCenterId() == costCenterId) {

                    // Calculate the remaining budget amount.
                    BudgetSpendView spent = siteBean.getBudgetSpent(site.getBusEntity().getBusEntityId(), costCenterId);
                    budgetView = budget;
                    budgetInfo.setSiteBudgetRemaining(spent.getAmountSpent());

                    break;
                }
            }

            // if it doesn't yet have an explicit budget, it's zero
            if (budgetView == null) {

                BudgetData budgetData = BudgetData.createValue();
                budgetData.setBusEntityId(site.getBusEntity().getBusEntityId());
                budgetData.setCostCenterId(costCenterId);

                budgetView = new BudgetView(budgetData, new BudgetDetailDataVector());

                BigDecimal zero = new BigDecimal(0);
                budgetInfo.setSiteBudgetRemaining(zero);
            }

            budgetInfo.setBudgetView(budgetView);
            budgets.add(budgetInfo);
        }

        pForm.setSiteBudgetList(budgets);
        pForm.setBudgetTotal(CurrencyFormat.format(budgetTotal));
    }

    /**
     *  <code>getAll</code> cost centers, note that the CostCenter list
     *  returned will be limited to a certain amount of records. It is
     *  up to the jsp page to detect this and to issued a subsequent call
     *  to get more records.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void getAll(HttpServletRequest request, ActionForm form)
                       throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        initFormVectors(request);

        APIAccess factory = new APIAccess();
        CatalogInformation catInfEjb = factory.getCatalogInformationAPI();
        Account accountBean = factory.getAccountAPI();
        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
        int catalogId = pForm.getCatalogDetail().getCatalogId();

        CostCenterAssocDataVector costCenterAssocDV = catInfEjb.getAllCostCenterAssoces(catalogId);
        Map<Integer, CostCenterAssocData> assocByCostCenterId = new TreeMap<Integer, CostCenterAssocData>();
        for (int i = 0; costCenterAssocDV != null && i < costCenterAssocDV.size(); i++) {
            CostCenterAssocData costCenterAssocD = (CostCenterAssocData) costCenterAssocDV.get(i);
            assocByCostCenterId.put(costCenterAssocD.getCostCenterId(), costCenterAssocD);
        }

        CostCenterDataVector dv = catInfEjb.getAllCostCenters
            (catalogId, Account.ORDER_BY_NAME);
        pForm.setFreightCostCenterId("0");
        String[] budgetThreshold = new String[0];
        if(dv != null){
            int index = 0;
            budgetThreshold = new String[dv.size()];
            Iterator it = dv.iterator();
            while(it.hasNext()){
                CostCenterData cc = (CostCenterData)  it.next();
                if(Utility.isTrue(cc.getAllocateFreight())){
                    pForm.setFreightCostCenterId(Integer.toString(cc.getCostCenterId()));
                }
                CostCenterAssocData costCenterAssoc = assocByCostCenterId.get(cc.getCostCenterId());
                budgetThreshold[index] = (costCenterAssoc == null || costCenterAssoc.getBudgetThreshold() == null) ? "" : costCenterAssoc.getBudgetThreshold();
                index++;
            }
        }

        //session.setAttribute("CostCenter.found.vector", dv);
        pForm.setCostCentersFound(dv);
        session.setAttribute("CostCenter.found.total",String.valueOf(dv.size()));
        pForm.setBudgetThreshold(budgetThreshold);
        CategoryToCostCenterViewVector ctoccv = new CategoryToCostCenterViewVector();
        session.setAttribute("CategoryToCostCenter.found.vector", ctoccv);
        ctoccv = catInfEjb.getAllCategoryToCostCenters(catalogId);
        session.setAttribute("CategoryToCostCenter.found.vector", ctoccv);
        ArrayList itoccv = catInfEjb.getAllItemToCostCenters(catalogId);
        session.setAttribute("ItemToCostCenter.found.vector", itoccv);

        CatalogFiscalPeriodViewVector cfpVwV = catInfEjb.getFiscalInfo(catalogId);
        pForm.setDisplayBudgetThreshold(isDispBudgetThr(request, cfpVwV));
        pForm.setCatalogFiscalPeriods(cfpVwV);
        pForm.setSiteBudgetList(null);

        //FiscalPeriodView fpv =
        //    accountBean.getFiscalInfo(accountId);
        //session.setAttribute("Account.fiscalInfo", fpv);

    }

    public static void getAllPollock(HttpServletRequest request, ActionForm form)
                       throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        initFormVectors(request);

        APIAccess factory = new APIAccess();
        CatalogInformation catInfEjb = factory.getCatalogInformationAPI();
        Account accountBean = factory.getAccountAPI();
        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
        int catalogId = pForm.getCatalogDetail().getCatalogId();

        CostCenterAssocDataVector costCenterAssocDV = catInfEjb.getAllCostCenterAssoces(catalogId);
        Map<Integer, CostCenterAssocData> assocByCostCenterId = new TreeMap<Integer, CostCenterAssocData>();
        for (int i = 0; costCenterAssocDV != null && i < costCenterAssocDV.size(); i++) {
            CostCenterAssocData costCenterAssocD = (CostCenterAssocData) costCenterAssocDV.get(i);
            assocByCostCenterId.put(costCenterAssocD.getCostCenterId(), costCenterAssocD);
        }

        CostCenterDataVector dv = catInfEjb.getAllCostCenters
            (catalogId, Account.ORDER_BY_NAME);
        pForm.setFreightCostCenterId("0");
        String[] budgetThreshold = new String[0];
        if(dv != null){
            int index = 0;
            budgetThreshold = new String[dv.size()];
            Iterator it = dv.iterator();
            while(it.hasNext()){
                CostCenterData cc = (CostCenterData)  it.next();
                if(Utility.isTrue(cc.getAllocateFreight())){
                    pForm.setFreightCostCenterId(Integer.toString(cc.getCostCenterId()));
                }
                CostCenterAssocData costCenterAssoc = assocByCostCenterId.get(cc.getCostCenterId());
                budgetThreshold[index] = (costCenterAssoc == null || costCenterAssoc.getBudgetThreshold() == null) ? "" : costCenterAssoc.getBudgetThreshold();
                index++;
            }
        }

        //session.setAttribute("CostCenter.found.vector", dv);
        pForm.setCostCentersFound(dv);
        session.setAttribute("CostCenter.found.total",String.valueOf(dv.size()));
        pForm.setBudgetThreshold(budgetThreshold);
        CategoryToCostCenterViewVector ctoccv = new CategoryToCostCenterViewVector();
        session.setAttribute("CategoryToCostCenter.found.vector", ctoccv);
        ctoccv = catInfEjb.getCatalogCategoryToCostCenters(catalogId);
        session.setAttribute("CategoryToCostCenter.found.vector", ctoccv);
        ArrayList itoccv = catInfEjb.getCatalogItemToCostCenters(catalogId);
        session.setAttribute("ItemToCostCenter.found.vector", itoccv);

        CatalogFiscalPeriodViewVector cfpVwV = catInfEjb.getFiscalInfo(catalogId);
        pForm.setDisplayBudgetThreshold(isDispBudgetThr(request, cfpVwV));
        pForm.setCatalogFiscalPeriods(cfpVwV);
        pForm.setSiteBudgetList(null);

        //FiscalPeriodView fpv =
        //    accountBean.getFiscalInfo(accountId);
        //session.setAttribute("Account.fiscalInfo", fpv);

    }

    private static boolean isDispBudgetThr(HttpServletRequest request, CatalogFiscalPeriodViewVector pList) throws Exception {

        APIAccess factory = APIAccess.getAPIAccess();
        PropertyService propServEjb = factory.getPropertyServiceAPI();

        SessionTool st = new SessionTool(request);
        StoreData store = st.getUserData().getUserStore();

        String budgetThrFlag = propServEjb.checkBusEntityProperty(store.getStoreId(), RefCodeNames.PROPERTY_TYPE_CD.BUDGET_THRESHOLD_FL);
        if (!Utility.isTrue(budgetThrFlag)) {
            return false;
        } else {
            for (Object o : pList) {
                CatalogFiscalPeriodView cfpVw = (CatalogFiscalPeriodView) o;
                IdVector accountIds = Utility.toIdVector(cfpVw.getAccounts());
                for (Object oAccountId : accountIds) {
                    String thrType = propServEjb.checkBusEntityProperty((Integer) oAccountId, RefCodeNames.PROPERTY_TYPE_CD.BUDGET_THRESHOLD_TYPE);
                    if (!RefCodeNames.BUDGET_THRESHOLD_TYPE.ACCOUNT_BUDGET_THRESHOLD.equalsIgnoreCase(thrType)) {
                        return false;
                    }
                }
            }
        }

        return true;

    }

    public static void resetCostCenters(HttpServletRequest request,
                                        ActionForm form)
        throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        initFormVectors(request);

        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        String userName = appUser.getUser().getUserName();

        APIAccess factory = new APIAccess();
        Catalog catalogEjb = factory.getCatalogAPI();

        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
        int catalogId = pForm.getCatalogDetail().getCatalogId();
        catalogEjb.resetCostCenters(catalogId,userName);
        getAll(request, form);
    }
	
    public static void resetCostCentersPollock(HttpServletRequest request,
                                        ActionForm form)
        throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        initFormVectors(request);

        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        String userName = appUser.getUser().getUserName();

        APIAccess factory = new APIAccess();
        Catalog catalogEjb = factory.getCatalogAPI();

        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
        int catalogId = pForm.getCatalogDetail().getCatalogId();
        catalogEjb.resetAccountCostCenters(catalogId,userName);
        getAllPollock(request, form);
    }
	

    /**
     *  <code>getAll</code> Sites
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void getAllSites(HttpServletRequest request, ActionForm form)
                            throws Exception {
        HttpSession session = request.getSession();
        initFormVectors(request);

        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;

        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();

        CostCenterData dd = pForm.getCostCenter();
        int accountId = pForm.getBudgetAccountIdInp();

        getSiteBudgets(accountId, dd.getCostCenterId(), pForm, null, session);

    }

    /**
     *formats the big decimal for displaying in the budgeting screen
     */
    private static String formatBigDecimal(BigDecimal pBigDecimal){
        if(pBigDecimal == null){
            return "";
        }
        return String.valueOf(pBigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    /**
     *  Describe <code>getDetail</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors getDetail(HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        initFormVectors(request);

        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
        int catalogId = pForm.getCatalogDetail().getCatalogId();

        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        CatalogInformation catInfEjb = factory.getCatalogInformationAPI();
        Budget budBean = factory.getBudgetAPI();
        String fieldValue = request.getParameter("searchField");

        if (null == fieldValue) {
            fieldValue = "0";
        }

        String siteidValue = request.getParameter("siteId");
        int costcenterid = Integer.parseInt(fieldValue);

        pForm.setSearchField("");
        CostCenterData dd = accountBean.getCostCenter(costcenterid, 0);
        pForm.setCostCenter(dd);

        CatalogFiscalPeriodViewVector cfpVwV = pForm.getCatalogFiscalPeriods();

        if(cfpVwV==null && cfpVwV.size()==0) {
           ae.add("error",new ActionError("error.simpleGenericError","No budget found"));
           return ae;
        }
        BusEntityDataVector accounts = new BusEntityDataVector();
        for(Iterator iter=cfpVwV.iterator(); iter.hasNext();) {
          CatalogFiscalPeriodView cfpVw = (CatalogFiscalPeriodView) iter.next();
          BusEntityDataVector aDV = cfpVw.getAccounts();
          accounts.addAll(aDV);
        }

        if(accounts.size()>1) {
          accounts = sortBusEntityByName(accounts);
        }

        pForm.setBudgetAccounts(accounts);
        int accountId = pForm.getBudgetAccountIdInp();
        boolean foundFl = false;
        for(Iterator iter=accounts.iterator(); iter.hasNext();) {
          BusEntityData beD = (BusEntityData) iter.next();
          if(accountId==beD.getBusEntityId()) {
            foundFl =true;
            pForm.setBudgetAccount(beD);
            break;
          }
        }
        if(!foundFl) {
          BusEntityData beD = (BusEntityData) accounts.get(0);
          accountId = beD.getBusEntityId();
          pForm.setBudgetAccount(beD);
          pForm.setBudgetAccountIdInp(accountId);
        }

        mmm:
        for(Iterator iter=cfpVwV.iterator(); iter.hasNext();) {
          CatalogFiscalPeriodView cfpVw = (CatalogFiscalPeriodView) iter.next();
          BusEntityDataVector beDV = cfpVw.getAccounts();
          for(Iterator iter1=beDV.iterator(); iter1.hasNext();) {
            BusEntityData beD = (BusEntityData) iter1.next();
            int beId = beD.getBusEntityId();
            if(beId==accountId) {
              pForm.setFiscalCalenderView(cfpVw.getFiscalCalenderView());
              break mmm;
            }
          }
        }

        //fetch the budget information for this account
        if(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET.equals(dd.getCostCenterTypeCd())){

            int budgetYear =  pForm.getFiscalCalenderView().getFiscalCalender().getFiscalYear();
            BudgetViewVector budgets = budBean.getBudgets(accountId, dd.getCostCenterId(), budgetYear);

            BudgetView theBudget = null;
            if (budgets.size() > 1) {
                throw new Exception("Multiple budget found. AccountId:"+accountId+", CostCenterId:"+dd.getCostCenterId()+", BudgetYear:"+budgetYear);
            } else if (budgets.size() > 0) {
                theBudget = (BudgetView) budgets.get(0);
            }

            pForm.setBudget(theBudget);
            pForm.setBudgetAmounts(toBudgetAmounts(theBudget));

        }

        pForm.setSiteBudgetList(null);

        return ae;
    }

    private static BusEntityDataVector sortBusEntityByName(BusEntityDataVector pEntities) {
      if(pEntities==null || pEntities.size()<=1) {
        return pEntities;
      }
      Object[] entA = pEntities.toArray();
      for(int ii=0; ii<entA.length-1; ii++) {
        boolean exitFl = true;
        for(int jj=0; jj<entA.length-ii-1; jj++){
          BusEntityData be1 = (BusEntityData) entA[jj];
          BusEntityData be2 = (BusEntityData) entA[jj+1];
          String sd1 = be1.getShortDesc();
          if(sd1==null) sd1 = "";
          String sd2 = be2.getShortDesc();
          if(sd2==null) sd2 = "";
          int comp = sd1.compareTo(sd2);
          if(comp>0) {
            exitFl = false;
            entA[jj] = be2;
            entA[jj+1] = be1;
          }
        }
        if(exitFl) break;
      }
      BusEntityDataVector beDV = new BusEntityDataVector();
      for(int ii=0; ii<entA.length; ii++) {
        BusEntityData be = (BusEntityData) entA[ii];
        beDV.add(be);
      }
      return beDV;

    }

    public static ActionErrors changeBudgetAccount(HttpServletRequest request, ActionForm form)
                          throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        Budget budBean = factory.getBudgetAPI();


        CostCenterData dd = pForm.getCostCenter();
        CatalogFiscalPeriodViewVector cfpVwV = pForm.getCatalogFiscalPeriods();

        BusEntityDataVector accounts = pForm.getBudgetAccounts();
        int accountId = pForm.getBudgetAccountIdInp();
        boolean foundFl = false;
        for(Iterator iter=accounts.iterator(); iter.hasNext();) {
          BusEntityData beD = (BusEntityData) iter.next();
          if(accountId==beD.getBusEntityId()) {
            foundFl =true;
            pForm.setBudgetAccount(beD);
            break;
          }
        }
        if(!foundFl) {
          BusEntityData beD = (BusEntityData) accounts.get(0);
          accountId = beD.getBusEntityId();
          pForm.setBudgetAccount(beD);
          pForm.setBudgetAccountIdInp(accountId);
        }

        mmm:
        for(Iterator iter=cfpVwV.iterator(); iter.hasNext();) {
          CatalogFiscalPeriodView cfpVw = (CatalogFiscalPeriodView) iter.next();
          BusEntityDataVector beDV = cfpVw.getAccounts();
          for(Iterator iter1=beDV.iterator(); iter1.hasNext();) {
            BusEntityData beD = (BusEntityData) iter1.next();
            int beId = beD.getBusEntityId();
            if(beId==accountId) {
              pForm.setFiscalCalenderView(cfpVw.getFiscalCalenderView());
              break mmm;
            }
          }
        }
        //fetch the budget information for this account
        if (RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET.equals(dd.getCostCenterTypeCd())) {

            int budgetYear =  pForm.getFiscalCalenderView().getFiscalCalender().getFiscalYear();
            BudgetViewVector budgets = budBean.getBudgets(accountId, dd.getCostCenterId(), budgetYear);

            BudgetView theBudget = null;
            if (budgets.size() > 1) {
                throw new Exception("Multiple budget found. AccountId:"+accountId+", CostCenterId:"+dd.getCostCenterId()+", BudgetYear:"+budgetYear);
            } else if (budgets.size() > 0) {
                theBudget = (BudgetView) budgets.get(0);
            }

            pForm.setBudget(theBudget);
            pForm.setBudgetAmounts(toBudgetAmounts(theBudget));

        }

        if(RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET.equals(dd.getCostCenterTypeCd())){
          pForm.setSiteBudgetList(null);
        }

        return ae;
    }

    public static ActionErrors changeBudgetType(HttpServletRequest request, ActionForm form) throws Exception {

        log.info("changeBudgetType => BEGIN");

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        Budget budBean = factory.getBudgetAPI();


        CostCenterData dd = pForm.getCostCenter();
        accountBean.setBudgetTypeStatus(dd);

        int accountId = pForm.getBudgetAccountIdInp();

        //fetch the budget information for this account
        if(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET.equals(dd.getCostCenterTypeCd())){

            int budgetYear = pForm.getFiscalCalenderView().getFiscalCalender().getFiscalYear();
            BudgetViewVector budgets = budBean.getBudgets(accountId, dd.getCostCenterId(), budgetYear);

            BudgetView theBudget = null;
            if (budgets != null && budgets.size() > 1) {
                throw new Exception("Multiple budget found. AccountId:"+accountId+", CostCenterId:"+dd.getCostCenterId()+", BudgetYear:"+budgetYear);
            } else if (budgets != null && !budgets.isEmpty()) {
                theBudget = (BudgetView) budgets.get(0);
            }

            pForm.setBudget(theBudget);
            pForm.setBudgetAmounts(toBudgetAmounts(theBudget));
        }

        log.info("changeBudgetType => END.");

        pForm.setSiteBudgetList(null);
        return ae;
    }

    /**
     *  <code>init</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void init(HttpServletRequest request, ActionForm form)
                     throws Exception {
        initFormVectors(request);

        // Get all the cost centers for an account.
        getAll(request, form);
        return;
    }

    public static void initPollock(HttpServletRequest request, ActionForm form)
                     throws Exception {
        initFormVectors(request);

        // Get all the cost centers for an account.
        //getAll(request, form);
		getAllPollock(request, form);

        return;
    }

    public static void initFormVectors(HttpServletRequest request)
                                throws Exception {

        HttpSession session = request.getSession();

        // Cache the lists needed for CostCenters.
        APIAccess factory = new APIAccess();
        ListService lsvc = factory.getListServiceAPI();

        // Set up the site status list.
        if (null == session.getAttribute("Account.status.vector")) {

            RefCdDataVector statusv = lsvc.getRefCodesCollection(
                                              "BUS_ENTITY_STATUS_CD");
            session.setAttribute("Account.status.vector", statusv);
        }

        // Set up the budget period list.
        if (null == session.getAttribute("CostCenter.period.vector")) {

            RefCdDataVector periodv = lsvc.getRefCodesCollection(
                                              "BUDGET_PERIOD_CD");
            session.setAttribute("CostCenter.period.vector", periodv);
        }

        if (null == session.getAttribute("Budget.type.cd")) {

            RefCdDataVector periodv = lsvc.getRefCodesCollection(
                                              "BUDGET_TYPE_CD");
            session.setAttribute("Budget.type.cd", periodv);
        }

    }

    /**
     *  <code>search</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors searchSites(HttpServletRequest request, ActionForm form)
                            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        initFormVectors(request);

        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;

        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        CostCenterData costCenterD = pForm.getCostCenter();

        int costCenterId = costCenterD.getCostCenterId();
        if(costCenterId==0) {
          return ae;
        }
        int accountId = pForm.getBudgetAccountIdInp();
        ae = getSiteBudgets(accountId, costCenterId, pForm, null, request);
        return ae;

    }

    /**
     *  <code>sort</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */

    public static void sort(HttpServletRequest request, ActionForm form)
                     throws Exception {
      if(!(form instanceof StoreCostCenterMgrForm)) {
        return;
      }
      StoreCostCenterMgrForm pForm = (StoreCostCenterMgrForm) form;

      // Get a reference to the admin facade
      HttpSession session = request.getSession();
      CostCenterDataVector costcenters = pForm.getSelectedCostCenters();
      String sortField = request.getParameter("sortField");
      DisplayListSort.sort(costcenters, sortField);
    }

    /**
     *  <code>sortConfig</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sortConfig (HttpServletRequest request, ActionForm form)
                     throws Exception {
      if(!(form instanceof StoreCostCenterMgrForm)) {
        return;
      }
      StoreCostCenterMgrForm pForm = (StoreCostCenterMgrForm) form;

      // Get a reference to the admin facade
      HttpSession session = request.getSession();

      CatalogDataVector catalogs = (CatalogDataVector)
              session.getAttribute("costCenter.catalogs.vector");
      if(catalogs==null) {
        return;
      }
      String sortField = request.getParameter("sortField");
      DisplayListSort.sort(catalogs, sortField);
    }

    /**
     *  Describe <code>addCostCenter</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    /*
    public static ActionErrors  addCostCenter(HttpServletRequest request,
                                     ActionForm form)
                              throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        initFormVectors(request);

        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
        int catalogId = pForm.getCatalogDetail().getCatalogId();

        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        CatalogInformation catInfEjb = factory.getCatalogInformationAPI();
        Budget budBean = factory.getBudgetAPI();
        CostCenterData dd = CostCenterData.createValue();
        dd.setShortDesc("");
        dd.setCatalogId(catalogId);
        dd.setBudgetAmount(new BigDecimal(0));
        dd.setCostCenterStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        dd.setCostCenterTypeCd(RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET);
        pForm.setCostCenter(dd);

        CatalogFiscalPeriodViewVector cfpVwV = pForm.getCatalogFiscalPeriods();
        if(cfpVwV==null || cfpVwV.size()==0) {
           ae.add("error",new ActionError("error.simpleGenericError","No budget found"));
           return ae;
        }
        BusEntityDataVector accounts = new BusEntityDataVector();
        for(Iterator iter=cfpVwV.iterator(); iter.hasNext();) {
          CatalogFiscalPeriodView cfpVw = (CatalogFiscalPeriodView) iter.next();
          BusEntityDataVector aDV = cfpVw.getAccounts();
          accounts.addAll(aDV);
        }

        if(accounts.size()>1) {
          accounts = sortBusEntityByName(accounts);
        }

        pForm.setBudgetAccounts(accounts);
        int accountId = pForm.getBudgetAccountIdInp();
        boolean foundFl = false;
        for(Iterator iter=accounts.iterator(); iter.hasNext();) {
          BusEntityData beD = (BusEntityData) iter.next();
          if(accountId==beD.getBusEntityId()) {
            foundFl =true;
            pForm.setBudgetAccount(beD);
            break;
          }
    }
        if(!foundFl) {
          if(accounts.isEmpty()){
              ae.add("error",new ActionError("error.simpleGenericError","No accounts found with fiscal calendars for this catalog"));
              return ae;
          }
          BusEntityData beD = (BusEntityData) accounts.get(0);
          accountId = beD.getBusEntityId();
          pForm.setBudgetAccount(beD);
          pForm.setBudgetAccountIdInp(accountId);
        }

        mmm:
        for(Iterator iter=cfpVwV.iterator(); iter.hasNext();) {
          CatalogFiscalPeriodView cfpVw = (CatalogFiscalPeriodView) iter.next();
          BusEntityDataVector beDV = cfpVw.getAccounts();
          for(Iterator iter1=beDV.iterator(); iter1.hasNext();) {
            BusEntityData beD = (BusEntityData) iter1.next();
            int beId = beD.getBusEntityId();
            if(beId==accountId) {
              pForm.setFiscalCalender(cfpVw.getFiscalCalender());
              break mmm;
            }
          }
        }
        pForm.setBudgetAmount1("");
        pForm.setBudgetAmount2("");
        pForm.setBudgetAmount3("");
        pForm.setBudgetAmount4("");
        pForm.setBudgetAmount5("");
        pForm.setBudgetAmount6("");
        pForm.setBudgetAmount7("");
        pForm.setBudgetAmount8("");
        pForm.setBudgetAmount9("");
        pForm.setBudgetAmount10("");
        pForm.setBudgetAmount11("");
        pForm.setBudgetAmount12("");
        pForm.setBudgetAmount13("");
        pForm.setSiteBudgetList(null);
        return ae;
     }
    */

    private static boolean verifyBudgetDate(String pmmdd) {

        if (pmmdd == null)

            return false;

        if (pmmdd.length() < 3)

            return false;

        StringTokenizer st = new StringTokenizer(pmmdd, "/");
        int[] ra = new int[2];

        for (int i = 0; st.hasMoreTokens() && i < 2; i++) {
            ra[i] = Integer.parseInt(st.nextToken());
        }

        if (ra[0] <= 0 || ra[0] > 31)

            return false;

        if (ra[1] <= 0 || ra[1] > 31)

            return false;

        return true;
    }


    /**
     *  Describe <code>updateCostCenter</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@return                an <code>ActionErrors</code> value
     *@exception  Exception  if an error occurs
     */

    public static ActionErrors updateCatalogCostCenter(HttpServletRequest request,
                                                ActionForm form)
                                         throws Exception {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm)form;
        CostCenterData costCenterD = pForm.getCostCenter();

            // Verify the form information submitted.
        if (!Utility.isSet(costCenterD.getShortDesc())) {
                lUpdateErrors.add("name",
                                  new ActionError("variable.empty.error",
                                                  "Name"));
        }

        if (!Utility.isSet(costCenterD.getCostCenterStatusCd())) {
                lUpdateErrors.add("statusCd",
                                  new ActionError("variable.empty.error",
                                                  "Status"));
        }

        if (!Utility.isSet(costCenterD.getCostCenterTypeCd())) {
                lUpdateErrors.add("costCenterTypeCd",
                                  new ActionError("variable.empty.error",
                                                  "Cost Center Type"));
        }

        if (lUpdateErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lUpdateErrors;
        }

        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        int costCenterId = costCenterD.getCostCenterId();


        // Get the current information for this Account.
        int accountId = pForm.getBudgetAccountIdInp();


        String cuser = (String)session.getAttribute(Constants.USER_NAME);

        FiscalPeriodView fpv = (FiscalPeriodView)
            session.getAttribute("Account.fiscalInfo");
        //FiscalCalenderData fcD = pForm.getFiscalCalender();
        //costCenterD.setBudgetPeriodCd(fcD.getPeriodCd());
        costCenterD.setModBy(cuser);


        if (costCenterD.getCostCenterId() == 0) {
            costCenterD.setAddBy(cuser);
            try {
                if(!Utility.isSet(costCenterD.getCostCenterTaxType())){
                    costCenterD.setCostCenterTaxType(RefCodeNames.COST_CENTER_TAX_TYPE.DONT_ALLOCATE_SALES_TAX);
                }
                //costCenterD = accountBean.addCostCenter(costCenterD, accountId);
                costCenterD = accountBean.updateCostCenter(costCenterD);
                pForm.setCostCenter(costCenterD);
            } catch (DuplicateNameException ne) {
                lUpdateErrors.add("name",
                                  new ActionError("error.field.notUnique",
                                                  "Name"));

                return lUpdateErrors;
            }
        } else {
            try {
                accountBean.updateCostCenter(costCenterD);
            } catch (DuplicateNameException ne) {
                lUpdateErrors.add("name",
                                  new ActionError("error.field.notUnique",
                                                  "Name"));

                return lUpdateErrors;
            }
        }

        return lUpdateErrors;
    }

    /**
     *  Describe <code>updateBudgets</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@return                an <code>ActionErrors</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors updateBudgets(HttpServletRequest request, ActionForm form) throws Exception {
       log.info("updateBudgets => BEGIN");

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();

       StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;

        String cuser = (String) session.getAttribute(Constants.USER_NAME);
        APIAccess factory = new APIAccess();
        CostCenterData costCenterD = pForm.getCostCenter();
        String ccType = costCenterD.getCostCenterTypeCd();
        FiscalCalenderView fcV = pForm.getFiscalCalenderView();

        String[] budgetMMddA = new String[FiscalCalendarUtility.getNumberOfBudgetPeriods(fcV) + 1];
        for (int i = 1; i < budgetMMddA.length; i++) {
            budgetMMddA[i] = FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), i);
        }

        //handle sites
        if (RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET.equals(ccType)) {

            Budget budBean = factory.getBudgetAPI();

            // Iterate thru the site budgets looking for any that have changed
            ArrayList budgets = pForm.getSiteBudgetList();

            for (Iterator iter = budgets.iterator(); iter.hasNext();) {

                SiteBudget siteBudget = (SiteBudget) iter.next();
                BudgetView budgetView = siteBudget.getBudgetView();

                if (budgetView.getBudgetData().getBudgetId() == 0) {
                    budgetView.getBudgetData().setAddBy(cuser);
                }

                budgetView.getBudgetData().setModBy(cuser);
                budgetView.getBudgetData().setBudgetTypeCd(RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET);
                budgetView.getBudgetData().setBudgetStatusCd(RefCodeNames.BUDGET_STATUS_CD.ACTIVE);
                budgetView.getBudgetData().setBudgetYear(fcV.getFiscalCalender().getFiscalYear());

                budgetView = budBean.updateBudget(budgetView, cuser);

                siteBudget.setBudgetView(budgetView);
            }
        }
        //handle accounts

        if (RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET.equals(ccType)) {

            log.info("updateBudgets => budgetAmounts:" + pForm.getBudgetAmounts());

            String[] budgetAmountSA = new String[pForm.getBudgetAmounts().size() + 1];
            for (int i = 1; i < budgetMMddA.length; i++) {
                budgetAmountSA[i] = pForm.getBudgetAmounts(i);
            }

            double[] budgetAmoutnDbA = new double[budgetAmountSA.length];
            for (int i = 1; i < budgetAmountSA.length; i++) {
                budgetAmoutnDbA[i] = 0;
                if(i < budgetMMddA.length){
	                if (Utility.isSet(budgetMMddA[i])) {
	                    if (Utility.isSet(budgetAmountSA[i])) {
	                        try {
	                        	BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(budgetAmountSA[i]));
	                            budgetAmoutnDbA[i] =
	                            	amount.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
	                        } catch (Exception exc) {
	                            String errorMess = "Illegal budget amount  " + budgetAmountSA[i];
	                            lUpdateErrors.add("error", new ActionError("error.simpleGenericError", errorMess));
	                        }
	                    }
	                }
                }
            }


            if (lUpdateErrors.size() > 0) {
                return lUpdateErrors;
            }

            BudgetView acctBudget = pForm.getBudget();
            if (acctBudget == null) {
                acctBudget = new BudgetView(BudgetData.createValue(), new BudgetDetailDataVector());
            }

            for (int i = 1; i < budgetAmountSA.length; i++) {

                BudgetDetailData bdd = BudgetUtil.getBudgetDetail(acctBudget.getDetails(), i);

                if (bdd == null) {
                    bdd = BudgetDetailData.createValue();
                    bdd.setPeriod(i);
                    acctBudget.getDetails().add(bdd);
                }
                if (Utility.isSet(budgetAmountSA[i])) {
                	BigDecimal amount = BigDecimal.valueOf(budgetAmoutnDbA[i]);
                	bdd.setAmount(amount.setScale(3,BigDecimal.ROUND_HALF_UP));
                } else {
                    bdd.setAmount(null);
                }

            }

            acctBudget.getBudgetData().setBudgetTypeCd(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET);
            acctBudget.getBudgetData().setBusEntityId(pForm.getBudgetAccountIdInp());
            acctBudget.getBudgetData().setCostCenterId(costCenterD.getCostCenterId());
            acctBudget.getBudgetData().setBudgetStatusCd(RefCodeNames.BUDGET_STATUS_CD.ACTIVE);

            int fYear = fcV.getFiscalCalender().getFiscalYear();
            if(fYear == 0){
            	Calendar cal=Calendar.getInstance();
                fYear=cal.get(Calendar.YEAR);
            }

            acctBudget.getBudgetData().setBudgetYear(fYear);

            Budget budBean = factory.getBudgetAPI();

            pForm.setBudget(budBean.updateBudget(acctBudget, cuser));
            pForm.setBudgetAmounts(toBudgetAmounts(pForm.getBudget()));

        }

        log.info("updateBudgets => END.");

        return lUpdateErrors;
    }

    /**
     *  The <code>delete</code> method removes the database entries defining
     *  this account if no other database entry is dependent on it.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     *@see                   com.cleanwise.service.api.session.Account
     */
    public static ActionErrors delete(HttpServletRequest request,
                                      ActionForm form)
                               throws Exception {

        ActionErrors deleteErrors = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;

        CostCenterData dd = pForm.getCostCenter();

        if (dd.getCostCenterId()>0) {
            try {
                accountBean.removeCostCenter(dd);
            } catch (Exception exc) {
                String errorMess = exc.getMessage();
                int ind1 = -1;
                int ind2 = -1;
                if(errorMess==null) throw exc;
                ind1 = errorMess.indexOf("^clw^");
                if(ind1>=0) {
                  ind2 = errorMess.indexOf("^clw^", ind1+3);
                  if(ind2>0) {
                   errorMess = errorMess.substring(ind1+5,ind2);
                   deleteErrors.add("error",new ActionError("error.simpleGenericError",errorMess));
                   return deleteErrors;
                  }
                }
                throw exc;
            }
            //session.removeAttribute("CostCenter.found.vector");
            pForm.setCostCentersFound(null);
        }

        return deleteErrors;
    }


    /**
     *Updates which cost center is the designated "freight" cost center.  Will only ever set one distinct
     *cost center out of the session attribute CostCenter.found.vector as the freight cost center.
     *It is assumed that this will satisfy the requierment that there only be a single cost center for freigt when an
     *order is placed.
     */
    public static ActionErrors updateCostCenterList(HttpServletRequest request, ActionForm form)throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        StoreCatalogMgrForm sForm = (StoreCatalogMgrForm) form;
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        String userName = appUser.getUser().getUserName();
        String ccidStr = sForm.getFreightCostCenterId();
        int ccid = Integer.parseInt(ccidStr);
        CostCenterDataVector ccdv = sForm.getCostCentersFound();
                //(CostCenterDataVector) session.getAttribute("CostCenter.found.vector");
        Iterator it = ccdv.iterator();
        boolean foundMastSalesTax = false;
        while(it.hasNext()){
            //figure out if there needs to be resetting of the cost center tax type
            CostCenterData cc = (CostCenterData) it.next();
            if(RefCodeNames.COST_CENTER_TAX_TYPE.MASTER_SALES_TAX_COST_CENTER.equals(cc.getCostCenterTaxType())){
                if(foundMastSalesTax){
                    ae.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("costcenter.error.masterSalesTaxSingularity"));
                }
                foundMastSalesTax = true;
            }
        }
        if(ae.size() > 0){
            return ae;
        }

        it = ccdv.iterator();
        while(it.hasNext()){
            CostCenterData cc = (CostCenterData) it.next();
            //if there is a master sales tax cost center and this particular cost center is not it
            //then it is implied that this cost center should not alocate sales tax.
            if(foundMastSalesTax && !RefCodeNames.COST_CENTER_TAX_TYPE.MASTER_SALES_TAX_COST_CENTER.equals(cc.getCostCenterTaxType())){
                cc.setCostCenterTaxType(RefCodeNames.COST_CENTER_TAX_TYPE.DONT_ALLOCATE_SALES_TAX);
            }
            if(cc.getCostCenterId() == ccid){
                cc.setAllocateFreight(Boolean.TRUE.toString());
                cc.setModBy(userName);
                accountBean.updateCostCenter(cc);
            }else if(Utility.isTrue(cc.getAllocateFreight())){
                cc.setAllocateFreight(Boolean.FALSE.toString());
                cc.setModBy(userName);
                accountBean.updateCostCenter(cc);
            }else{
                accountBean.updateCostCenter(cc);
            }
        }
        return ae;
    }

    public static ActionErrors updateCategoryCostCenter(HttpServletRequest request,
                                                        ActionForm form)
                                                 throws Exception {

        ActionErrors ae = new ActionErrors();
        String newcostcenter = request.getParameter("newCostCenter");
        String forCategoryName = request.getParameter("categoryName");
        HttpSession session = request.getSession();

        if (newcostcenter == null || newcostcenter.trim().length() == 0) {
            ae.add("error",
                   new ActionError("error.badRequest", "Cost Center Info"));

            return ae;
        }

        int newcostcenterId = Integer.parseInt(newcostcenter.trim());

        if (forCategoryName == null ||
            forCategoryName.trim().length() == 0) {
            ae.add("error",
                   new ActionError("error.badRequest", "Category Name"));

            return ae;
        }


        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        String userName = appUser.getUser().getUserName();
        APIAccess factory = new APIAccess();
        Catalog catalogEjb = factory.getCatalogAPI();
        Account accountBean = factory.getAccountAPI();
        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
        int catalogId = pForm.getCatalogDetail().getCatalogId();
        catalogEjb.setCategoryCostCenter(catalogId, forCategoryName,
                                              newcostcenterId,userName);

        return ae;
    }

    public static ActionErrors updateCategoryCostCenterPollock(HttpServletRequest request,
                                                        ActionForm form)
                                                 throws Exception {

        ActionErrors ae = new ActionErrors();
        String newcostcenter = request.getParameter("newCostCenter");
        String forCategoryName = request.getParameter("categoryName");
        HttpSession session = request.getSession();

        if (newcostcenter == null || newcostcenter.trim().length() == 0) {
            ae.add("error",
                   new ActionError("error.badRequest", "Cost Center Info"));

            return ae;
        }

        int newcostcenterId = Integer.parseInt(newcostcenter.trim());

        if (forCategoryName == null ||
            forCategoryName.trim().length() == 0) {
            ae.add("error",
                   new ActionError("error.badRequest", "Category Name"));

            return ae;
        }


        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        String userName = appUser.getUser().getUserName();
        APIAccess factory = new APIAccess();
        Catalog catalogEjb = factory.getCatalogAPI();
        Account accountBean = factory.getAccountAPI();
        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
        int catalogId = pForm.getCatalogDetail().getCatalogId();
        catalogEjb.setAccountCategoryCostCenter(catalogId, forCategoryName,
                                              newcostcenterId,userName);

        return ae;
    }

    public static ActionErrors setCatalogFilter(HttpServletRequest request,
                                                        ActionForm form)
                                                 throws Exception {

        ActionErrors ae = new ActionErrors();
        if(!(form instanceof StoreCostCenterMgrForm)) {
           ae.add("error",new ActionError("error.simpleGenericError","Misplaced Request. Call System Adminstrator"));
           return ae;
        }
        StoreCostCenterMgrForm pForm = (StoreCostCenterMgrForm) form;
        CatalogDataVector catalogDV = pForm.getLocateStoreCatalogForm().getCatalogsToReturn();
        pForm.setFilterCatalogs(catalogDV);


        return ae;
    }

    public static ActionErrors clearCatalogFilter(HttpServletRequest request,
                                                        ActionForm form)
                                                 throws Exception {

        ActionErrors ae = new ActionErrors();
        if(!(form instanceof StoreCostCenterMgrForm)) {
           ae.add("error",new ActionError("error.simpleGenericError","Misplaced Request. Call System Adminstrator"));
           return ae;
        }
        StoreCostCenterMgrForm pForm = (StoreCostCenterMgrForm) form;
        pForm.setFilterCatalogs(null);

        return ae;
    }

    /**
     *  <code>search</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */

    public static ActionErrors search(HttpServletRequest request, ActionForm form)
    throws Exception {

      // Get a reference to the admin facade
      ActionErrors ae = new ActionErrors();
      if(!(form instanceof StoreCostCenterMgrForm)) {
        ae.add("error",new ActionError("error.simpleGenericError",
                "Misplaced Request. Call System Adminstrator"));
        return ae;
      }
      StoreCostCenterMgrForm pForm = (StoreCostCenterMgrForm) form;

      HttpSession session = request.getSession();
      APIAccess factory = new APIAccess();
      Budget budgetBean = factory.getBudgetAPI();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      int storeId = appUser.getUserStore().getBusEntity().getBusEntityId();

      String fieldValue = pForm.getSearchField();
      String fieldSearchType = pForm.getSearchType();

      if ("costCenterId".equals(fieldSearchType) && Utility.isSet(fieldValue)) {
        try {
          Integer.parseInt(fieldValue);
        }catch(Exception exc) {
          String errorMess = "Wrong cost center id format: "+fieldValue;
          ae.add("error",new ActionError("error.simpleGenericError",errorMess));
          return ae;
        }
      }
      IdVector catalogIdV = null;
      CatalogDataVector catalogDV = pForm.getFilterCatalogs();
      if(catalogDV!=null && !catalogDV.isEmpty()) {
        catalogIdV = new IdVector();
        for(Iterator iter=catalogDV.iterator(); iter.hasNext();) {
          CatalogData cD = (CatalogData) iter.next();
          catalogIdV.add(new Integer(cD.getCatalogId()));
        }
      }
      CostCenterDataVector costCenterDV =
        budgetBean.getCostCenters(storeId, fieldValue, fieldSearchType,
                                        catalogIdV,pForm.getShowInactiveFlag());
      for(Iterator iter=costCenterDV.iterator(); iter.hasNext();) {
        CostCenterData costCenterD = (CostCenterData) iter.next();
        if(!"true".equals(costCenterD.getAllocateFreight())){
          costCenterD.setAllocateFreight("false");
        }
        if(!"true".equals(costCenterD.getAllocateDiscount())){
          costCenterD.setAllocateDiscount("false");
        }
      }

      pForm.setSelectedCostCenters(costCenterDV);
      return ae;
    }

    public static ActionErrors editCostCenter(HttpServletRequest request, ActionForm form)
    throws Exception {

      // Get a reference to the admin facade
      ActionErrors ae = new ActionErrors();
      if(!(form instanceof StoreCostCenterMgrForm)) {
        ae.add("error",new ActionError("error.simpleGenericError",
                "Misplaced Request. Call System Adminstrator"));
        return ae;
      }
      StoreCostCenterMgrForm pForm = (StoreCostCenterMgrForm) form;

      HttpSession session = request.getSession();
      APIAccess factory = new APIAccess();
      Budget budgetBean = factory.getBudgetAPI();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      int storeId = appUser.getUserStore().getBusEntity().getBusEntityId();

      String costCenterIdS = request.getParameter("id");
      String fieldSearchType = "costCenterId";

      CostCenterDataVector costCenterDV =
        budgetBean.getCostCenters(storeId, costCenterIdS,"costCenterId",null,true);

      if(costCenterDV.size()==0) {
        pForm.setCostCenterDetail(CostCenterData.createValue());
        String errorMess = "Cost center not found. Cost center id: "+costCenterIdS;
        ae.add("error",new ActionError("error.simpleGenericError",errorMess));
        return ae;
      } else {
        CostCenterData costCenterD = (CostCenterData) costCenterDV.get(0);
        if(!"true".equals(costCenterD.getAllocateFreight())){
          costCenterD.setAllocateFreight("false");
        }
        if(!"true".equals(costCenterD.getAllocateDiscount())){
          costCenterD.setAllocateDiscount("false");
        }
        if(!"true".equals(costCenterD.getNoBudget())){
            costCenterD.setNoBudget("false");
          }
        pForm.setCostCenterDetail(costCenterD);
      }
      session.removeAttribute("costCenter.catalogs.vector");
      return ae;
    }

    public static ActionErrors addCostCenter(HttpServletRequest request, ActionForm form)
    throws Exception {

      // Get a reference to the admin facade
      ActionErrors ae = new ActionErrors();
      if(!(form instanceof StoreCostCenterMgrForm)) {
        ae.add("error",new ActionError("error.simpleGenericError",
                "Misplaced Request. Call System Adminstrator"));
        return ae;
      }
      HttpSession session = request.getSession();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      int storeId = appUser.getUserStore().getBusEntity().getBusEntityId();
      StoreCostCenterMgrForm pForm = (StoreCostCenterMgrForm) form;

      CostCenterData costCenterD = CostCenterData.createValue();
      costCenterD.setAllocateFreight("false");
      costCenterD.setAllocateDiscount("false");
      costCenterD.setNoBudget("false");
      costCenterD.setStoreId(storeId);
      pForm.setCostCenterDetail(costCenterD);
      return ae;
    }

    public static ActionErrors updateCostCenter(HttpServletRequest request,
                                                ActionForm form)
    throws Exception {
      ActionErrors ae = new ActionErrors();
      if(!(form instanceof StoreCostCenterMgrForm)) {
        ae = updateCatalogCostCenter(request,form);
      } else {
        ae = updateStoreCostCenter(request,form);
      }
      return ae;
    }

    public static ActionErrors updateStoreCostCenter(HttpServletRequest request,
       ActionForm form)
    throws Exception {
      ActionErrors ae = new ActionErrors();
      StoreCostCenterMgrForm pForm = (StoreCostCenterMgrForm) form;
      CostCenterData costCenterD = pForm.getCostCenterDetail();
      String costCenterIdS = request.getParameter("costCenterId");
      int costCenterId = 0;
      try {
        costCenterId = Integer.parseInt(costCenterIdS);
      }catch (Exception exc) {}

      if(costCenterId != costCenterD.getCostCenterId()) {
        String errorMess = "Inconsistency. Found cost center id = "+costCenterId+
                " Expected cost center id = "+costCenterD.getCostCenterId();
        ae.add("error",new ActionError("error.simpleGenericError",errorMess));
        return ae;
      }
      if(!Utility.isSet(costCenterD.getShortDesc())) {
        String errorMess = "Field Cost Center Name is empty";
        ae.add("errorName",new ActionError("error.simpleGenericError",errorMess));
      }
      if(!Utility.isSet(costCenterD.getCostCenterTypeCd())) {
        String errorMess = "Field Cost Center Type is empty";
        ae.add("errorType",new ActionError("error.simpleGenericError",errorMess));
      }
      if(!Utility.isSet(costCenterD.getCostCenterStatusCd())) {
        String errorMess = "Field Cost Center Status is empty";
        ae.add("errorStatus",new ActionError("error.simpleGenericError",errorMess));
      }
      if(!Utility.isSet(costCenterD.getCostCenterTaxType())) {
        String errorMess = "Field Cost Center Tax Type is empty";
        ae.add("errorTax",new ActionError("error.simpleGenericError",errorMess));
      }
      String allocateDiscount = costCenterD.getAllocateDiscount();
      if (!Utility.isSet(allocateDiscount)) {
          String errorMess = "Field Allocate Discount is empty";
          ae.add("errorDiscount", new ActionError("error.simpleGenericError", errorMess));
      }
      if (!"true".equals(allocateDiscount) && !"false".equals(allocateDiscount)) {
          String errorMess = "Wrong Allocate Discount value: " + allocateDiscount;
          ae.add("errorDiscount", new ActionError("error.simpleGenericError", errorMess));
      }
      String allocateFreight = costCenterD.getAllocateFreight();
      if(!Utility.isSet(allocateFreight)) {
        String errorMess = "Field Allocate Freight is empty";
        ae.add("errorFreight",new ActionError("error.simpleGenericError",errorMess));
      }
      if(!"true".equals(allocateFreight) && !"false".equals(allocateFreight)) {
        String errorMess = "Wrong Allocate Freight value: "+allocateFreight;
        ae.add("errorFreight",new ActionError("error.simpleGenericError",errorMess));
      }
      if(ae.size()>0) {
        return ae;
      }

      HttpSession session = request.getSession();
      APIAccess factory = new APIAccess();
      Budget budgetBean = factory.getBudgetAPI();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      int storeId = appUser.getUserStore().getBusEntity().getBusEntityId();

      if(costCenterId!=0) {
        if(costCenterD.getStoreId()!=storeId ) {
          String errorMess = "Wrong store value. Expected "+storeId+" Found "+
                  costCenterD.getStoreId()+" Call Technical Support.";
          ae.add("errorFreight",new ActionError("error.simpleGenericError",errorMess));
          return ae;
        }
      }

      costCenterD = budgetBean.saveCostCenter(costCenterD,appUser.getUser().getUserName());
      pForm.setCostCenterDetail(costCenterD);
      CostCenterDataVector costCenterDV = pForm.getSelectedCostCenters();
      if(costCenterId==0) {
         if(costCenterDV==null) {
           costCenterDV = new CostCenterDataVector();
           pForm.setSelectedCostCenters(costCenterDV);
         }
         costCenterDV.add(0,costCenterD);

      } else {
        if(costCenterDV!=null) {
          int index = 0;
          for(Iterator iter=costCenterDV.iterator(); iter.hasNext();index++) {
            CostCenterData ccD = (CostCenterData) iter.next();
            if(ccD.getCostCenterId()==costCenterId) {
              iter.remove();
              costCenterDV.add(index,costCenterD);
              break;
            }
          }
        }
      }

      return ae;
    }

    public static ActionErrors searchInit(HttpServletRequest request, ActionForm form)
    throws Exception {

      // Get a reference to the admin facade
      ActionErrors ae = new ActionErrors();
      if(!(form instanceof StoreCostCenterMgrForm)) {
        ae.add("error",new ActionError("error.simpleGenericError",
                "Misplaced Request. Call System Adminstrator"));
        return ae;
      }
      StoreCostCenterMgrForm pForm = (StoreCostCenterMgrForm) form;

      HttpSession session = request.getSession();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      int storeId = appUser.getUserStore().getBusEntity().getBusEntityId();
      CostCenterDataVector costCenterDV = pForm.getSelectedCostCenters();
      if(costCenterDV!=null && costCenterDV.size()>0) {
        CostCenterData costCenterD = (CostCenterData) costCenterDV.get(0);
        if(costCenterD.getStoreId()!=storeId) {
          pForm.setSelectedCostCenters(null);
        }
      }
      return ae;
    }

    public static ActionErrors configInit(HttpServletRequest request, ActionForm form)
    throws Exception {

      // Get a reference to the admin facade
      ActionErrors ae = new ActionErrors();
      if(!(form instanceof StoreCostCenterMgrForm)) {
        ae.add("error",new ActionError("error.simpleGenericError",
                "Misplaced Request. Call System Adminstrator"));
        return ae;
      }
      StoreCostCenterMgrForm pForm = (StoreCostCenterMgrForm) form;

      HttpSession session = request.getSession();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      int storeId = appUser.getUserStore().getBusEntity().getBusEntityId();
      //nothing so far
      return ae;
    }

    public static ActionErrors searchConfCatalog(HttpServletRequest request, ActionForm form)
    throws Exception {

      // Get a reference to the admin facade
      ActionErrors ae = new ActionErrors();
      if(!(form instanceof StoreCostCenterMgrForm)) {
        ae.add("error",new ActionError("error.simpleGenericError",
                "Misplaced Request. Call System Adminstrator"));
        return ae;
      }
      StoreCostCenterMgrForm pForm = (StoreCostCenterMgrForm) form;

      HttpSession session = request.getSession();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      int storeId = appUser.getUserStore().getBusEntity().getBusEntityId();
      //nothing so far
  	  APIAccess factory = new APIAccess();
      CatalogInformation catInfBean = factory.getCatalogInformationAPI();
      int costCenterId = pForm.getCostCenterDetail().getCostCenterId();
      String searchField = pForm.getConfSearchField();
      String searchType = pForm.getConfSearchType();

      CatalogInformation cati = factory.getCatalogInformationAPI();
      CatalogDataVector configuredCatalogDV =
        catInfBean.getAccountCatalogs(storeId,searchField,searchType,costCenterId,
              pForm.getConfShowInactiveFl());
      int[] assocIds = new int[configuredCatalogDV.size()];
      int index = 0;
      for(Iterator iter=configuredCatalogDV.iterator(); iter.hasNext();) {
        CatalogData cD = (CatalogData) iter.next();
        assocIds[index++] = cD.getCatalogId();
      }
      pForm.setAssocIds(assocIds);


      if(pForm.getConfShowConfguredOnlyFl()) {
        session.setAttribute("costCenter.catalogs.vector",configuredCatalogDV);
      } else {
        CatalogDataVector catalogDV =
          catInfBean.getAccountCatalogs(storeId,searchField,searchType,0,
                pForm.getConfShowInactiveFl());
        session.setAttribute("costCenter.catalogs.vector",catalogDV);
      }
    return ae;
  }
  public static ActionErrors saveCostCenterAssoc(HttpServletRequest request, ActionForm form)
  throws Exception {
    ActionErrors ae = new ActionErrors();
    if(!(form instanceof StoreCostCenterMgrForm)) {
      ae.add("error",new ActionError("error.simpleGenericError",
              "Misplaced Request. Call System Adminstrator"));
      return ae;
    }
    StoreCostCenterMgrForm pForm = (StoreCostCenterMgrForm) form;
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = appUser.getUserStore().getBusEntity().getBusEntityId();
    String userName = appUser.getUser().getUserName();

    APIAccess factory = new APIAccess();
    IdVector catalogIdV = new IdVector();
    CatalogDataVector catalogDV = (CatalogDataVector) session.getAttribute("costCenter.catalogs.vector");
    if(catalogDV == null) {
      ae.add("error",new ActionError("error.simpleGenericError",
              "Catalog list is not defined. Call System Adminstrator"));
      return ae;
    }
    for(Iterator iter=catalogDV.iterator(); iter.hasNext();) {
      CatalogData cD = (CatalogData) iter.next();
      catalogIdV.add(new Integer(cD.getCatalogId()));
    }
    Budget  budgetBean = factory.getBudgetAPI();
    int costCenterId = pForm.getCostCenterDetail().getCostCenterId();
    try {
      budgetBean.saveCostCenterCatalogAssoc(costCenterId,catalogIdV,pForm.getAssocIds(),userName);
    } catch(Exception exc) {
      String mess = exc.getMessage();
      if(mess!=null) {
        int ind1 = mess.indexOf("^clw^");
        if(ind1>=0) {
          int ind2 = mess.indexOf("^clw^",ind1+5);
          if(ind2>=0) {
            String mess1 = mess.substring(ind1+5,ind2);
            ae.add("error",new ActionError("error.simpleGenericError",mess1));
            return ae;
          } else {
            throw exc;
          }
        }
      }
    }
    return ae;
  }

    public static ActionErrors updateBudgets(HttpServletRequest request, StoreCostCenterMgrForm pForm) throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();

        String cuser = (String) session.getAttribute(Constants.USER_NAME);

        APIAccess factory = new APIAccess();
        Account accountEjb = factory.getAccountAPI();

        CostCenterData costCenterD = pForm.getCostCenter();
        String ccType = costCenterD.getCostCenterTypeCd();

        FiscalCalenderView fcV = pForm.getFiscalCalenderView();

        String[] budgetMMddA = new String[FiscalCalendarUtility.getNumberOfBudgetPeriods(fcV) + 1];
        for (int i = 1; i < budgetMMddA.length; i++) {
            budgetMMddA[i] = FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), i);
        }

        accountEjb.setBudgetTypeStatus(costCenterD);

        //handle sites
        if (RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET.equals(ccType)) {

            Budget budBean = factory.getBudgetAPI();
            // Iterate thru the site budgets looking for any that have changed
            ArrayList budgets = pForm.getSiteBudgetList();
            if (budgets == null || budgets.isEmpty()) {
                String errorMess = "No budget data";
                lUpdateErrors.add("error", new ActionError("error.simpleGenericError", errorMess));
                return lUpdateErrors;
            }

            for (Iterator iter = budgets.iterator(); iter.hasNext();) {

                SiteBudget siteBudget = (SiteBudget) iter.next();
                BudgetView budget = siteBudget.getBudgetView();

                if (budget.getBudgetData().getBudgetId() == 0) {
                    budget.getBudgetData().setAddBy(cuser);
                }

                budget.getBudgetData().setModBy(cuser);
                budget.getBudgetData().setBudgetTypeCd(RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET);
                budget.getBudgetData().setBudgetStatusCd(RefCodeNames.BUDGET_STATUS_CD.ACTIVE);
                budget.getBudgetData().setBudgetYear(fcV.getFiscalCalender().getFiscalYear());

                budget = budBean.updateBudget(budget, cuser);
                siteBudget.setBudgetView(budget);

                eraseBudgetAmountFields(pForm);
            }
        }
        //handle accounts

        if (RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET.equals(ccType)) {


            log.info("updateBudgets => budgetAmounts:" + pForm.getBudgetAmounts());

            String[] budgetAmountSA = new String[pForm.getBudgetAmounts().size() + 1];
            for (int i = 1; i < budgetMMddA.length; i++) {
                budgetAmountSA[i] = pForm.getBudgetAmounts(i);
            }

            double[] budgetAmoutnDbA = new double[budgetAmountSA.length];
            for (int i = 1; i < budgetAmountSA.length; i++) {
                budgetAmoutnDbA[i] = 0;
                if (Utility.isSet(budgetMMddA[i])) {
                    if (!Utility.isSet(budgetAmountSA[i])) {
                        String errorMess = "No budget amount for period: " + budgetMMddA[i];
                        lUpdateErrors.add("error", new ActionError("error.simpleGenericError", errorMess));
                    } else {
                        try {
                            budgetAmoutnDbA[i] = Double.parseDouble(budgetAmountSA[i]);
                        } catch (Exception exc) {
                            String errorMess = "Illegal budget amount  " + budgetAmountSA[i];
                            lUpdateErrors.add("error", new ActionError("error.simpleGenericError", errorMess));
                        }
                    }
                }
            }

            if (lUpdateErrors.size() > 0) {
                return lUpdateErrors;
            }

            BudgetView acctBudget = pForm.getBudget();

            if (acctBudget == null) {
                acctBudget = new BudgetView(BudgetData.createValue(), new BudgetDetailDataVector());
            }

            for (int i = 1; i < budgetAmountSA.length; i++) {
                BudgetDetailData bdd = BudgetUtil.getBudgetDetail(acctBudget.getDetails(), i);
                if (bdd == null) {
                    bdd = BudgetDetailData.createValue();
                    bdd.setPeriod(i);
                    acctBudget.getDetails().add(bdd);
                } else {
                	BigDecimal amount = BigDecimal.valueOf(budgetAmoutnDbA[i]).setScale(3,BigDecimal.ROUND_HALF_UP);
                    bdd.setAmount(amount);
                }
                if (Utility.isSet(budgetAmountSA[i])) {
                	BigDecimal amount = BigDecimal.valueOf(budgetAmoutnDbA[i]).setScale(3,BigDecimal.ROUND_HALF_UP);
                    bdd.setAmount(amount);
                } else {
                    bdd.setAmount(null);
                }
            }

            acctBudget.getBudgetData().setBudgetTypeCd(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET);
            acctBudget.getBudgetData().setBusEntityId(pForm.getBudgetAccountIdInp());
            acctBudget.getBudgetData().setCostCenterId(costCenterD.getCostCenterId());
            acctBudget.getBudgetData().setBudgetStatusCd(RefCodeNames.BUDGET_STATUS_CD.ACTIVE);
            acctBudget.getBudgetData().setBudgetYear(fcV.getFiscalCalender().getFiscalYear());

            Budget budgetEjb = factory.getBudgetAPI();
            acctBudget = budgetEjb.updateBudget(acctBudget, cuser);
            pForm.setBudget(acctBudget);
            if (acctBudget != null) {
                pForm.setBudgetAmounts(toBudgetAmounts(acctBudget));
            } else {
                eraseBudgetAmountFields(pForm);
            }
        }
        return lUpdateErrors;
    }

    public static ActionErrors changeBudgetAccount(HttpServletRequest request, StoreCostCenterMgrForm pForm) throws Exception {
        int accountId = pForm.getBudgetAccountIdInp();
        return getAccountBudgets(pForm, String.valueOf(accountId));
    }

    public static ActionErrors changeBudgetSite(HttpServletRequest request, StoreCostCenterMgrForm pForm) throws Exception {
        int siteId = pForm.getBudgetSiteIdInp();
        String siteIdStr = siteId>0?String.valueOf(siteId):null;
        return getSiteBudgets(pForm.getCostCenter().getCostCenterId(), pForm,siteIdStr);
    }

    public static ActionErrors changeBudgetType(HttpServletRequest request, StoreCostCenterMgrForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();

        CostCenterData dd = pForm.getCostCenter();
        if (RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET.equals(dd.getCostCenterTypeCd())) {
            return getAccountBudgets(pForm, null);
        } else if (RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET.equals(dd.getCostCenterTypeCd())) {
            return getSiteBudgets(dd.getCostCenterId(), pForm, null);
        }

        return ae;
    }

    private static ActionErrors getSiteBudgets(int costCenterId,
                                               StoreCostCenterMgrForm pForm,
                                               String pOptionalSiteId) throws Exception {

        ActionErrors ae = new ActionErrors();
        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        Budget budgetEjb = factory.getBudgetAPI();

        SiteViewVector sv = new SiteViewVector();

        if (null != pOptionalSiteId) {
            QueryRequest query = new QueryRequest();
            query.filterBySiteId(Integer.parseInt(pOptionalSiteId));
            SiteViewVector thisSite = siteBean.getSiteCollection(query);
            sv.addAll(thisSite);

        } else {

            sv = pForm.getSiteFilter();
        }

        if (sv == null || sv.isEmpty()) {
            ae.add("siteBudget", new ActionError("error.simpleGenericError", "No site selected"));
            return ae;
        }

        BigDecimal budgetTotal = new BigDecimal(0);
        ArrayList budgets = new ArrayList();
        int budgetYear = pForm.getFiscalCalenderView().getFiscalCalender().getFiscalYear();

        Iterator siteIter = sv.iterator();

        while (siteIter.hasNext()) {

            SiteBudget budgetInfo = new SiteBudget();
            SiteView site = (SiteView) siteIter.next();
            budgetInfo.setId(String.valueOf(site.getId()));
            budgetInfo.setName(site.getName());

            budgetInfo.setCity(site.getCity());
            budgetInfo.setState(site.getPostalCode());

            BudgetView budgetView = null;
            BudgetViewVector budgetv = budgetEjb.getWorkOrderBudgets(site.getId(), costCenterId, budgetYear);

            for (Object aBudgetv : budgetv) {
                BudgetView budget = (BudgetView) aBudgetv;
                if (budget.getBudgetData().getCostCenterId() == costCenterId) {
                    budgetView = budget;
                    break;
                }
            }

            // if it doesn't yet have an explicit budget, it's zero
            if (budgetView == null) {
                BudgetData budgetData = BudgetData.createValue();
                budgetData.setBusEntityId(site.getId());
                budgetData.setCostCenterId(costCenterId);

                budgetView = new BudgetView(budgetData, new BudgetDetailDataVector());

                BigDecimal zero = new BigDecimal(0);
                budgetInfo.setSiteBudgetRemaining(zero);
            }

            budgetInfo.setBudgetView(budgetView);
            budgets.add(budgetInfo);
        }

        pForm.setSiteBudgetList(budgets);
        pForm.setBudgetTotal(CurrencyFormat.format(budgetTotal));

        eraseBudgetAmountFields(pForm);

        return ae;

    }


    public static ActionErrors getAccountBudgets(StoreCostCenterMgrForm pForm, String optionalAccountId) throws Exception {

        ActionErrors ae = new ActionErrors();

        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        Budget budgetEjb = factory.getBudgetAPI();

        if (Utility.isSet(optionalAccountId)) {

            int accountId = pForm.getBudgetAccountIdInp();
            BusEntityData account = accountBean.getAccountBusEntity(accountId);

            FiscalPeriodView fiscalInfo = accountBean.getFiscalInfo(pForm.getBudgetAccount().getBusEntityId());

            int budgetYear = fiscalInfo.getFiscalCalenderView().getFiscalCalender().getFiscalYear();

            BudgetViewVector theBudget = budgetEjb.getWorkOrderBudgets(accountId, pForm.getCostCenter().getCostCenterId(), budgetYear);
            if (!theBudget.isEmpty()) {
                pForm.setBudget((BudgetView) theBudget.get(0));
            } else {
                pForm.setBudget(null);
            }

            pForm.setBudgetAccount(account);
            pForm.setBudgetAccountIdInp(account.getBusEntityId());
            pForm.setFiscalCalenderView(fiscalInfo.getFiscalCalenderView());

        } else if (pForm.getAccountFilter() != null && !pForm.getAccountFilter().isEmpty()) {

            BusEntityDataVector accounts = new BusEntityDataVector();
            Iterator it = pForm.getAccountFilter().iterator();
            for (Object o : pForm.getAccountFilter()) {
                accounts.add(((AccountData) o).getBusEntity());
            }

            if (accounts.size() > 1) {
                accounts = sortBusEntityByName(accounts);
            }

            BusEntityData account = (BusEntityData) accounts.get(0);

            pForm.setBudgetAccounts(accounts);
            pForm.setBudgetAccount(account);
            if (account != null) {

                FiscalPeriodView fiscalInfo = accountBean.getFiscalInfo(pForm.getBudgetAccount().getBusEntityId());

                pForm.setBudgetAccountIdInp(account.getBusEntityId());
                pForm.setFiscalCalenderView(fiscalInfo.getFiscalCalenderView());

                int budgetYear = fiscalInfo.getFiscalCalenderView().getFiscalCalender().getFiscalYear();

                BudgetViewVector theBudget = budgetEjb.getWorkOrderBudgets(account.getBusEntityId(), pForm.getCostCenter().getCostCenterId(), budgetYear);
                if (!theBudget.isEmpty()) {
                    pForm.setBudget((BudgetView) theBudget.get(0));
                } else {
                    pForm.setBudget(null);
                }
            }
        }

        if(pForm.getBudget() != null){
            //setup the form
            BudgetView theBudget = pForm.getBudget();
            pForm.setBudgetAmounts(toBudgetAmounts(theBudget));
        } else {
            eraseBudgetAmountFields(pForm);
        }

        pForm.setSiteBudgetList(null);

        return ae;
    }

    public static ActionErrors initBudgetConfig(HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();

        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();

        initFormVectors(request);

        StoreCostCenterMgrForm pForm = (StoreCostCenterMgrForm) form;

        pForm.setLocateStoreSiteForm(null);
        pForm.setLocateStoreAccountForm(null);
        pForm.setSiteBudgetList(null);
        pForm.setFiscalCalenderView(null);
        pForm.setBudget(null);
        pForm.setBudgetAccount(null);
        pForm.setBudgetAccountIdInp(0);
        pForm.setBudgetAccounts(new BusEntityDataVector());

        eraseBudgetAmountFields(pForm);

        int costcenterid = pForm.getCostCenterDetail().getCostCenterId();
        CostCenterData dd = accountBean.getCostCenter(costcenterid, 0);
        pForm.setCostCenter(dd);

        return ae;
    }

    public static ActionErrors getBudgetConfig(HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();

        initFormVectors(request);

        StoreCostCenterMgrForm pForm = (StoreCostCenterMgrForm) form;

        CostCenterData dd = pForm.getCostCenter();
        if (RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET.equals(dd.getCostCenterTypeCd())) {
            return getAccountBudgets(pForm, null);
        } else if (RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET.equals(dd.getCostCenterTypeCd()) && pForm.getSiteFilter() != null) {
            return getSiteBudgets(dd.getCostCenterId(), pForm, null);
        }

        return ae;
    }

    public static void addAccountFilterToSite(StoreCostCenterMgrForm pForm) {
        if (pForm.getLocateStoreSiteForm() != null) {
            pForm.getLocateStoreSiteForm().setAccountIds(Utility.toIdVector(pForm.getAccountFilter()));
        } else {
            pForm.setLocateStoreSiteForm(new LocateStoreSiteForm());
            pForm.getLocateStoreSiteForm().setAccountIds(Utility.toIdVector(pForm.getAccountFilter()));
        }
    }

    public static void removeAccountFilterOfSite(StoreCostCenterMgrForm pForm) {
        if (pForm.getLocateStoreSiteForm() != null) {
            pForm.getLocateStoreSiteForm().setAccountIds(null);
        }
    }

    public static void eraseBudgetAmountFields(StoreCostCenterMgrForm pForm) {
        pForm.setBudgetAmounts(new HashMap<Integer, String>());
    }


    private static HashMap<Integer, String> toBudgetAmounts(BudgetView theBudget) {

        HashMap<Integer, String> budgetAmounts = new HashMap<Integer, String>();

        if (theBudget != null) {
            BudgetDetailDataVector budgetDetails = theBudget.getDetails();
            for (int j = 0; j < budgetDetails.size(); j++) {
                budgetAmounts.put((j + 1),formatBigDecimal(BudgetUtil.getAmount(budgetDetails, (j + 1))));
            }
        }
        return budgetAmounts;
    }

    public static ActionErrors updateBudgetThreshold(
            HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
        int catalogId = pForm.getCatalogDetail().getCatalogId();
        APIAccess factory = new APIAccess();
        CatalogInformation catInfEjb = factory.getCatalogInformationAPI();
        CostCenterAssocDataVector costCenterAssocDV = catInfEjb.getAllCostCenterAssoces(catalogId);
        Map<Integer, CostCenterAssocData> assocByCostCenterId = new TreeMap<Integer, CostCenterAssocData>();
        for (int i = 0; costCenterAssocDV != null && i < costCenterAssocDV.size(); i++) {
            CostCenterAssocData costCenterAssocD = (CostCenterAssocData) costCenterAssocDV.get(i);
            assocByCostCenterId.put(costCenterAssocD.getCostCenterId(), costCenterAssocD);
        }
        CostCenterAssocDataVector costCenterAssocDVForUpdate = new CostCenterAssocDataVector();
        CostCenterDataVector costCenterDV = pForm.getCostCentersFound();
        Set<Integer> costCenterWithWrongBT =  new TreeSet<Integer>();
        for (int i = 0; costCenterDV != null && i < costCenterDV.size(); i++) {
            CostCenterData costCenterD = (CostCenterData) costCenterDV.get(i);
            int costCenterId = costCenterD.getCostCenterId();
            CostCenterAssocData costCenterAssocD = assocByCostCenterId.get(costCenterId);
            try {
                String budgetThresholdS = pForm.getBudgetThreshold()[i];
                if (Utility.isSet(budgetThresholdS) == true) {
                    int budgetThreshold = Integer.parseInt(budgetThresholdS);
                    if (budgetThreshold >= -100 && budgetThreshold <= 100) {
                        costCenterAssocD.setBudgetThreshold(""
                                + budgetThreshold);
                    } else {
                        costCenterWithWrongBT.add(costCenterId);
                    }
                } else {
                    costCenterAssocD.setBudgetThreshold(null);
                }
            } catch (Exception e) {
                costCenterWithWrongBT.add(costCenterId);
            }
            costCenterAssocDVForUpdate.add(costCenterAssocD);
        }
        if (costCenterWithWrongBT.size() > 0) {
            lUpdateErrors.add("error", new ActionError("error.simpleGenericError", "Wrong Budget Threshold for CostCenters:"
                    + costCenterWithWrongBT + ". Must be integer between -100 and 100."));
        }
        if (lUpdateErrors.size() == 0 && costCenterAssocDVForUpdate.size() > 0) {
            catInfEjb.updateCostCenterAssoces(costCenterAssocDVForUpdate);
        }
        return lUpdateErrors;
    }
}
