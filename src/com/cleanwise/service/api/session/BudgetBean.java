package com.cleanwise.service.api.session;

/**
 * Title:        BudgetBean
 * Description:  Bean implementation for Budget Stateless Session Bean
 * Purpose:      Provides access to the table-level Budget methods (add by, add date, mod by, mod date)
 * Copyright:    Copyright (c) 2005
 * Company:      Cleanwise, Inc.
 */

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.framework.UtilityServicesAPI;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.wrapper.BudgetViewWrapper;

import javax.ejb.CreateException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class BudgetBean extends UtilityServicesAPI
{

  /**
   *Requiered by EJB
   */
  public void ejbCreate() throws CreateException, RemoteException {}



  /**
   * Adds the Budget information values to be used by the request.
   * @param pBudgetData  the Budget data.
   * @param pUserName  the user name
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public BudgetData updateBudgetData(BudgetData pBudgetData,String pUserName)
      throws RemoteException{
	  Connection conn = null;

        try {
            conn = getConnection();

            if (pBudgetData.isDirty()) {
				pBudgetData.setModBy(pUserName);
                if (pBudgetData.getBudgetId() == 0) {
					pBudgetData.setAddBy(pUserName);
                    pBudgetData.setBudgetStatusCd(RefCodeNames.BUDGET_STATUS_CD.ACTIVE);
                    //pBudgetData.setBudgetTypeCd(RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET);
                    pBudgetData = BudgetDataAccess.insert(conn, pBudgetData);
                } else {
                    BudgetDataAccess.update(conn, pBudgetData);
                }
            }
        } catch (Exception e) {
			throw processException(e);
        } finally {
			closeConnection(conn);
        }

        return pBudgetData;
  }

    public BudgetView updateBudget(BudgetView pBudget, String pUserName) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return BudgetDAO.updateBudget(conn, pBudget, pUserName);
        } catch (Exception e) {
            e.printStackTrace();
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    public BudgetViewVector updateBudgets(BudgetViewVector pBudgets, String pUserName) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            final int count = (pBudgets == null) ? 0 : pBudgets.size();
            BudgetViewVector resultBudgetViewV = new BudgetViewVector();
            for (int i = 0; i < count; i++) {
                BudgetView item = (BudgetView) pBudgets.get(i);
                BudgetView budgetV = BudgetDAO.updateBudget(conn, item, pUserName);
                resultBudgetViewV.add(budgetV);
            }
            return resultBudgetViewV;
        } catch (Exception e) {
            e.printStackTrace();
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }


  /**
   *Fetches the budget for the supplied bus entity
   * @param pBusEntityId the bus entity id of the budget
   * @param pCostCenterId the cost center id of the budget
   *@throws            RemoteException Required by EJB 1.0
   */
  /* public BudgetDataVector fetchBusEntityBudget(int pBusEntityId, int pCostCenterId)
                               throws RemoteException{
      Connection conn = null;
        BudgetData lBudgetData = null;

        try {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(BudgetDataAccess.BUS_ENTITY_ID, pBusEntityId);
            ArrayList budgetTypes = new ArrayList();
            budgetTypes.add(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET);
            budgetTypes.add(RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET);
            crit.addOneOf(BudgetDataAccess.BUDGET_TYPE_CD,budgetTypes);
            crit.addEqualTo(BudgetDataAccess.BUDGET_STATUS_CD,RefCodeNames.BUDGET_STATUS_CD.ACTIVE);
            if (pCostCenterId > 0) {
                crit.addEqualTo(BudgetDataAccess.COST_CENTER_ID, pCostCenterId);
            }

            BudgetDataVector v = BudgetDataAccess.select(conn, crit);

            if (v.size() == 0) {
                logError("no budget defined for pBusEntityId=" + pBusEntityId
                + " pCostCenterId=" + pCostCenterId );
                return null;
            }


            return  v;

        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

   } */

  /**
   *Gets cost centers
   *@param pStoreId the store id
   *@param pSearchField could be cost center id or cost center name template
   *@param pSearchType type of the pSearchField value:
   * costCenterId,costCenterNameStarts,costCenterNameContains
   *@param pCatalogIds list of account catalog ids
   *@param pShowInactive filters out inactive catalogs if false
   *@return a set of CostCenterData objects
   *@throws            RemoteException Required by EJB 1.0
   *
   */
  public CostCenterDataVector getCostCenters(int pStoreId,
          String pSearchField, String pSearchType, IdVector pCatalogIds, boolean pShowInactive)
          throws RemoteException{
    Connection conn = null;
    try {
      conn = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CostCenterDataAccess.STORE_ID,pStoreId);
      if(Utility.isSet(pSearchField)) {
        if("costCenterId".equals(pSearchType)) {
          int costCenterId = 0;
          try {
            costCenterId = Integer.parseInt(pSearchField);
          } catch (Exception exc) {} // not interger value
          dbc.addEqualTo(CostCenterDataAccess.COST_CENTER_ID,costCenterId);
        } else if ("costCenterNameStarts".equals(pSearchType)) {
          dbc.addBeginsWithIgnoreCase(CostCenterDataAccess.SHORT_DESC,pSearchField);
        } else if ("costCenterNameContains".equals(pSearchType)) {
          dbc.addContainsIgnoreCase(CostCenterDataAccess.SHORT_DESC,pSearchField);
        } else {
          dbc.addCondition("1=2"); //unknown type
        }
      }
      if(!pShowInactive) {
        dbc.addNotEqualTo(CostCenterDataAccess.COST_CENTER_STATUS_CD,
                RefCodeNames.COST_CENTER_STATUS_CD.INACTIVE);
      }
      if(pCatalogIds!=null && pCatalogIds.size()>0) {
        DBCriteria dbc1 = new DBCriteria();
        dbc1.addOneOf(CostCenterAssocDataAccess.CATALOG_ID,pCatalogIds);
        dbc1.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD,
                RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
        String costCenterCatalogAssocReq =
            CostCenterAssocDataAccess.
                getSqlSelectIdOnly(CostCenterAssocDataAccess.COST_CENTER_ID, dbc1);
        dbc.addOneOf(CostCenterDataAccess.COST_CENTER_ID, costCenterCatalogAssocReq);
      }
      dbc.addOrderBy(CostCenterDataAccess.SHORT_DESC);
      CostCenterDataVector costCenterDV =
              CostCenterDataAccess.select(conn,dbc);
      return costCenterDV;
    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(conn);
    }

  }


    /**
     * Gets cost centers
     *
     * @param pStoreId      the store id
     * @param pTypeCd       budget type cd
     * @param pCatalogIds   optional catalog ids
     * @param pShowInactive filters out inactive cost center if false
     * @return a set of CostCenterData objects
     * @throws RemoteException Required by EJB 1.0
     */
    public CostCenterDataVector getCostCenters(int pStoreId,
                                               String pTypeCd,
                                               IdVector pCatalogIds,
                                               boolean pShowInactive) throws RemoteException {
        Connection conn = null;
        CostCenterDataVector result = new CostCenterDataVector();
        try {

            conn = getConnection();
            DBCriteria dbc = new DBCriteria();

            dbc.addEqualTo(CostCenterDataAccess.STORE_ID, pStoreId);
            if (!pShowInactive) {
                dbc.addNotEqualTo(CostCenterDataAccess.COST_CENTER_STATUS_CD,
                        RefCodeNames.COST_CENTER_STATUS_CD.INACTIVE);
            }

            if (pTypeCd != null) {
                dbc.addEqualTo(CostCenterDataAccess.COST_CENTER_TYPE_CD, pTypeCd);
            }

            if (pCatalogIds != null && pCatalogIds.size() > 0) {
                DBCriteria dbc1 = new DBCriteria();
                dbc1.addOneOf(CostCenterAssocDataAccess.CATALOG_ID, pCatalogIds);
                dbc1.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD, RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
                String costCenterCatalogAssocReq = CostCenterAssocDataAccess.getSqlSelectIdOnly(CostCenterAssocDataAccess.COST_CENTER_ID, dbc1);
                dbc.addOneOf(CostCenterDataAccess.COST_CENTER_ID, costCenterCatalogAssocReq);
            }

            dbc.addOrderBy(CostCenterDataAccess.SHORT_DESC);

            result = CostCenterDataAccess.select(conn, dbc);

        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return result;
  }

  /**
   *Saves cost center
   *@param pCostCenter cost center object
   *@param pUser the user login name
   *@return the CostCenterData object
   *@throws            RemoteException Required by EJB 1.0
   *
   */
  public CostCenterData saveCostCenter(CostCenterData pCostCenter, String pUser)
    throws RemoteException{
    Connection conn = null;
    try {
      conn = getConnection();
      int costCenterId = pCostCenter.getCostCenterId();
      if(costCenterId == 0) {
        pCostCenter.setAddBy(pUser);
        pCostCenter.setModBy(pUser);
        pCostCenter = CostCenterDataAccess.insert(conn,pCostCenter);
      } else {
        pCostCenter.setModBy(pUser);
        CostCenterDataAccess.update(conn,pCostCenter);
      }
      return pCostCenter;
    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(conn);
    }

  }

  /**
   *Saves cost center - catalog associations
   *@param pCostCenterId cost center identifier
   *@param pCatalogIds catalog identifiers to process
   *@param pAssignCatIds catalog idntifiers to assign
   *@param pUser user login name
   *@throws            RemoteException Required by EJB 1.0
   *
   */
  public void saveCostCenterCatalogAssoc(int pCostCenterId,
          IdVector pCatalogIds, int[] pAssignCatIds, String pUser)
    throws RemoteException{
    Connection conn = null;
    try {
      conn = getConnection();
      CostCenterData costCenterD = CostCenterDataAccess.select(conn,pCostCenterId);
      String name = costCenterD.getShortDesc();

      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ID,pCostCenterId);
      dbc.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD,
              RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
      dbc.addOneOf(CostCenterAssocDataAccess.CATALOG_ID, pCatalogIds);
      CostCenterAssocDataVector costCenterAssocDV =
                                   CostCenterAssocDataAccess.select(conn,dbc);

      //Remove
      IdVector assocToRemoveIdV = new IdVector();
      for(Iterator iter=costCenterAssocDV.iterator(); iter.hasNext();) {
        CostCenterAssocData ccaD = (CostCenterAssocData) iter.next();
        int catId = ccaD.getCatalogId();
        boolean foundFl = false;
        for(int ii=0; ii<pAssignCatIds.length; ii++) {
          if(pAssignCatIds[ii]==catId) {
            foundFl = true;
            break;
          }
        }
        if(!foundFl) {
          assocToRemoveIdV.add(new Integer(ccaD.getCostCenterAssocId()));
        }
      }
      dbc = new DBCriteria();
      dbc.addOneOf(CostCenterAssocDataAccess.COST_CENTER_ASSOC_ID, assocToRemoveIdV);
      CostCenterAssocDataAccess.remove(conn,dbc);

      //Add
      IdVector catToAssignIdV = new IdVector();
      int[] catToAssign = new int[pAssignCatIds.length];
      for(int ii=0; ii<pAssignCatIds.length; ii++) {
        int catId = pAssignCatIds[ii];
        catToAssignIdV.add(new Integer(catId));
        catToAssign[ii] = catId;
      }

      dbc = new DBCriteria();
      dbc.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ID,pCostCenterId);
      dbc.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD,
              RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
      dbc.addOneOf(CostCenterAssocDataAccess.CATALOG_ID, catToAssignIdV);
      costCenterAssocDV = CostCenterAssocDataAccess.select(conn,dbc);

      for(Iterator iter=costCenterAssocDV.iterator(); iter.hasNext();) {
        CostCenterAssocData ccaD = (CostCenterAssocData) iter.next();
        int catId = ccaD.getCatalogId();
        boolean foundFl = false;
        for(int ii=0; ii<catToAssign.length; ii++) {
          if(catToAssign[ii]==catId) {
            catToAssign[ii] = 0;
          }
        }
      }

      IdVector newCatToAssignIdV = new IdVector();
      for(int ii=0; ii<catToAssign.length; ii++) {
        if(catToAssign[ii]>0) {
          newCatToAssignIdV.add(new Integer(catToAssign[ii]));
        }
      }

      dbc = new DBCriteria();
      dbc.addOneOf(CostCenterAssocDataAccess.CATALOG_ID, newCatToAssignIdV);
      dbc.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD,
              RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
      IdVector costCenterAssignedIdV =
         CostCenterAssocDataAccess.selectIdOnly(conn,CostCenterAssocDataAccess.COST_CENTER_ID,dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(CostCenterDataAccess.COST_CENTER_ID,costCenterAssignedIdV);
      dbc.addEqualTo(CostCenterDataAccess.SHORT_DESC,name);
      IdVector duplNameCostCenterIdV =
         CostCenterDataAccess.selectIdOnly(conn,CostCenterDataAccess.COST_CENTER_ID,dbc);
      if(duplNameCostCenterIdV.size()>0) {
        dbc = new DBCriteria();
        dbc.addOneOf(CostCenterAssocDataAccess.COST_CENTER_ID,duplNameCostCenterIdV);
        dbc.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD,
                RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
        IdVector catalogsDuplCostCenterIdV =
           CostCenterAssocDataAccess.selectIdOnly(conn,CostCenterAssocDataAccess.CATALOG_ID,dbc);
        String errorMess = "^clw^Some catalogs already have cost center "+name+
           " Catalog id(s): "+IdVector.toCommaString(catalogsDuplCostCenterIdV)+"^clw^";
        throw new Exception(errorMess);
      }

      for(int ii=0; ii<catToAssign.length; ii++) {
        if(catToAssign[ii]>0) {
          CostCenterAssocData ccaD = CostCenterAssocData.createValue();
          ccaD.setCatalogId(pAssignCatIds[ii]);
          ccaD.setCostCenterId(pCostCenterId);
          ccaD.setCostCenterAssocCd(RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
          ccaD.setAddBy(pUser);
          ccaD.setModBy(pUser);
          CostCenterAssocDataAccess.insert(conn,ccaD);
        }
      }

    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(conn);
    }

  }

    public int getAccountBudgetPeriod(int pAccountId, int pSiteId, java.util.Date pDate) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            BusEntityDAO bd = new BusEntityDAO();
            return bd.getAccountBudgetPeriod(conn, pAccountId, pSiteId, pDate);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

    }


    public BudgetData getWorkOrderBudgetData(int pBusEntityId, int pCostCenterId, int pBudgerYear) throws RemoteException, DataNotFoundException {
        BudgetDataVector budgets = getWorkOrderBudgetDataVector(pBusEntityId, pCostCenterId, pBudgerYear);
        if (budgets.isEmpty()) {
            throw new DataNotFoundException("Work Order Budget not found.pBusEntityId => " + pBusEntityId + ", pCostCenterId => " + pCostCenterId + ", pBudgerYear => " + pBudgerYear);
        } else if (budgets.size() > 1) {
            throw new RemoteException("Multiple Work Order Budget.pBusEntityId => " + pBusEntityId + ", pCostCenterId => " + pCostCenterId + ", pBudgerYear => " + pBudgerYear);
        }
        return (BudgetData) budgets.get(0);
    }

    public BudgetDataVector getWorkOrderBudgetDataVector(int pBusEntityId, int pCostCenterId, int pBudgetYear) throws RemoteException {
        Connection conn = null;
        try {

            conn = getConnection();

            ArrayList<String> budgetTypes = new ArrayList<String>();
            budgetTypes.add(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET);
            budgetTypes.add(RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET);

            return BudgetDAO.getBudgetDataVector(conn, pBusEntityId, pCostCenterId, pBudgetYear, budgetTypes);

        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    public BudgetView getWorkOrderBudget(int busEntityId, int costCenterId,int pBudgerYear) throws RemoteException, DataNotFoundException {
        BudgetData budget = getWorkOrderBudgetData(busEntityId, costCenterId, pBudgerYear);
        return getBudget(budget);
    }

    private BudgetView getBudget(BudgetData budget) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return BudgetDAO.getBudget(conn, budget);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    public BudgetViewVector getWorkOrderBudgets(int busEntityId, int costCenterId,int pBudgerYear) throws RemoteException {
        Connection conn = null;
        try {

            conn = getConnection();

            BudgetDataVector budgetDV = getWorkOrderBudgetDataVector(busEntityId, costCenterId, pBudgerYear);

            return BudgetDAO.getBudgets(conn, budgetDV);

        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }


    public BudgetSpentShortViewVector getWorkOrderBudgetSpendInfo(BudgetSpentCriteria criteria) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getWorkOrderBudgetSpendInfo(conn,criteria);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    private BudgetSpentShortViewVector getWorkOrderBudgetSpendInfo(Connection conn, BudgetSpentCriteria criteria) throws Exception {

        BudgetSpentShortViewVector infoList = new BudgetSpentShortViewVector();

        Iterator it = criteria.getCostCenters().iterator();
        BudgetUtil budgetUtil = new BudgetUtil(conn);
        while (it.hasNext()) {

            int ccId = (Integer) it.next();

            BudgetView budget = null;
            try {
                budget = getWorkOrderBudget(criteria.getBusEntityId(), ccId, criteria.getBudgetYear());
            }  catch (DataNotFoundException e) {
                logInfo(e.getMessage());
//                infoList.clear();
//                return infoList;
            }

            	
            

            for (int period = 1;  period <= criteria.getNumberOfBudgetPeriods();  period++) {

                BudgetSpentShortView budgetSpentShortView = new BudgetSpentShortView();

                BigDecimal spend = new BigDecimal(0);

                if (RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET.equals(criteria.getBudgetTypeCd())) {
                    spend = budgetUtil._calculateWorkOrderAccountAmountSpent(criteria.getBusEntityId(), ccId, criteria.getBudgetYear(), period);
                } else if (RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET.equals(criteria.getBudgetTypeCd())) {
                    spend = budgetUtil._calculateWorkOrderSiteAmountSpent(criteria.getBusEntityId(), ccId, criteria.getBudgetYear(), period);
                }

                budgetSpentShortView.setBudgetPeriod(period);
                budgetSpentShortView.setBudgetTypeCd(criteria.getBudgetTypeCd());
                budgetSpentShortView.setBudgetYear(criteria.getBudgetYear());
                budgetSpentShortView.setBusEntityId(criteria.getBusEntityId());
                budgetSpentShortView.setCostCenterId(ccId);
                budgetSpentShortView.setAmountSpent(spend);

                if(budget == null) {
	                budgetSpentShortView.setAmountAllocated(null);
                }
                else
                {
	                BudgetViewWrapper budgetWrapper = new BudgetViewWrapper(budget);
	                budgetSpentShortView.setAmountAllocated(budgetWrapper.getAmount(period));
                }

                infoList.add(budgetSpentShortView);
            }

        }
        return infoList;
    }

    public CostCenterDataVector getWorkOrderCostCenters(int siteId,
                                                        int pBudgetYear,
                                                        boolean showInactive) throws RemoteException {
        try {
            Store storeEjb = APIAccess.getAPIAccess().getStoreAPI();
            Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();

            int accountId = accountEjb.getAccountIdForSite(siteId);
            int storeId = storeEjb.getStoreIdByAccount(accountId);

            return getWorkOrderCostCenters(storeId, accountId, siteId, pBudgetYear , showInactive);

        } catch (Exception e) {
            throw processException(e);
        }
    }

    public CostCenterDataVector getWorkOrderCostCenters(int storeId,
                                                        int accountId,
                                                        int siteId,
                                                        int pBudgetYear,
                                                        boolean showInactive) throws RemoteException {

        CostCenterDataVector result = new CostCenterDataVector();

        String budgetTypeCd = getWorkOrderBudgetType(siteId, pBudgetYear);
        if (RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET.equals(budgetTypeCd)) {
            result = getCostCenters(storeId,
                    RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET,
                    null,
                    showInactive);
        } else if (RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET.equals(budgetTypeCd)) {
            result = getCostCenters(storeId,
                    RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET,
                    null,
                    showInactive);
        }
        return result;
    }

    public String getWorkOrderBudgetType(int siteId, int pBudgetYear) throws RemoteException {
        try {
            Store storeEjb = APIAccess.getAPIAccess().getStoreAPI();
            Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();

            int accountId = accountEjb.getAccountIdForSite(siteId);
            int storeId = storeEjb.getStoreIdByAccount(accountId);

            return getWorkOrderBudgetType(storeId, accountId, siteId, pBudgetYear);

        } catch (Exception e) {
            throw processException(e);
        }
    }

    public String getWorkOrderBudgetType(int storeId, int accountId, int siteId, int pBudgetYear) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getWorkOrderBudgetType(conn, storeId, accountId, siteId, pBudgetYear);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    private String getWorkOrderBudgetType(Connection conn, int storeId, int accountId, int siteId, int pBudgetYear) throws Exception {

        DBCriteria dbCriteria;


        {
            dbCriteria = new DBCriteria();

            dbCriteria.addEqualTo(BudgetDataAccess.BUS_ENTITY_ID, siteId);
            dbCriteria.addEqualTo(BudgetDataAccess.BUDGET_TYPE_CD, RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET);
            dbCriteria.addEqualTo(BudgetDataAccess.BUDGET_STATUS_CD, RefCodeNames.BUDGET_STATUS_CD.ACTIVE);
            dbCriteria.addEqualTo(BudgetDataAccess.BUDGET_YEAR, pBudgetYear);

            dbCriteria.addJoinTableEqualTo(CostCenterDataAccess.CLW_COST_CENTER, CostCenterDataAccess.STORE_ID, storeId);
            dbCriteria.addJoinTableEqualTo(CostCenterDataAccess.CLW_COST_CENTER, CostCenterDataAccess.COST_CENTER_STATUS_CD, RefCodeNames.COST_CENTER_STATUS_CD.ACTIVE);
            dbCriteria.addJoinTableEqualTo(CostCenterDataAccess.CLW_COST_CENTER, CostCenterDataAccess.COST_CENTER_TYPE_CD, RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET);
            dbCriteria.addJoinCondition(BudgetDataAccess.COST_CENTER_ID, CostCenterDataAccess.CLW_COST_CENTER, CostCenterDataAccess.COST_CENTER_ID);

            BudgetDataVector budgets = BudgetDataAccess.select(conn, dbCriteria);
            if (!budgets.isEmpty()) {
                return RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET;
            }
        }

        {
            dbCriteria = new DBCriteria();

            dbCriteria.addEqualTo(BudgetDataAccess.BUS_ENTITY_ID, accountId);
            dbCriteria.addEqualTo(BudgetDataAccess.BUDGET_TYPE_CD, RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET);
            dbCriteria.addEqualTo(BudgetDataAccess.BUDGET_STATUS_CD, RefCodeNames.BUDGET_STATUS_CD.ACTIVE);
            dbCriteria.addEqualTo(BudgetDataAccess.BUDGET_YEAR, pBudgetYear);

            dbCriteria.addJoinTableEqualTo(CostCenterDataAccess.CLW_COST_CENTER, CostCenterDataAccess.STORE_ID, storeId);
            dbCriteria.addJoinTableEqualTo(CostCenterDataAccess.CLW_COST_CENTER, CostCenterDataAccess.COST_CENTER_STATUS_CD, RefCodeNames.COST_CENTER_STATUS_CD.ACTIVE);
            dbCriteria.addJoinTableEqualTo(CostCenterDataAccess.CLW_COST_CENTER, CostCenterDataAccess.COST_CENTER_TYPE_CD, RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET);
            dbCriteria.addJoinCondition(BudgetDataAccess.COST_CENTER_ID, CostCenterDataAccess.CLW_COST_CENTER, CostCenterDataAccess.COST_CENTER_ID);

            BudgetDataVector budgets = BudgetDataAccess.select(conn, dbCriteria);
            if (!budgets.isEmpty()) {
                return RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET;
            }
        }

        return null;
    }

    public BudgetViewVector getBudgets(int pBusEntityId, int pCostCenterId, int pBudgerYear) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();

            ArrayList<String> budgetTypes = new ArrayList<String>();

            budgetTypes.add(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET);
            budgetTypes.add(RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET);

            return BudgetDAO.getBudgets(conn, pBusEntityId, pCostCenterId, pBudgerYear, budgetTypes);

        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    public BudgetViewVector getBudgetsForSite(int pAccountId, int pSiteId, int pCostCenterId, int pBudgetYear) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return BudgetDAO.getBudgetsForSite(conn, pAccountId, pSiteId, pCostCenterId, pBudgetYear);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }
    
    public BudgetViewVector getAllBudgetsForSites(ArrayList pSiteIds, int pBudgetYear) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return BudgetDAO.getAllBudgetsForSites(conn, pSiteIds, pBudgetYear);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }
    
    public HashMap getAllBudgetSpentForSites(ArrayList pSiteIds, int pPeriod, int pYear, ArrayList pOrderStatus) throws Exception {
    	
    	HashMap siteCCMap = new HashMap();
    	ArrayList locales = new ArrayList();
    	Connection conn = null;
    	PreparedStatement stmt=null;
    	ResultSet rs = null;
    	
        try {
            conn = getConnection();
            
            String sql = "select sl.cost_center_id,sum(sl.amount),o.locale_cd "+
            				"from clw_site_ledger sl join clw_order o "+
            				"on sl.order_id = o.order_id "+
            				"where "+
            				"sl.site_id IN ("+Utility.toCommaSting(pSiteIds)+") "+
            				"and o.order_status_cd in ("+Utility.toCommaSting(pOrderStatus,new Character('\''))+") "+
                        	"and (o.order_budget_type_cd not in ('" + RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE + "', '" + RefCodeNames.ORDER_BUDGET_TYPE_CD.REBILL + "') or o.order_budget_type_cd is null) "+
            				"and sl.budget_year="+pYear;
            					
            
            if(pPeriod > 0){
            	sql = sql + "and sl.BUDGET_PERIOD = "+pPeriod;
            }
            
            sql = sql + " group by sl.site_id, sl.cost_center_id, o.locale_cd";	
       	     	
            stmt = conn.prepareStatement(sql);
            rs=stmt.executeQuery();

            
    		while (rs.next()) {

    			int costCenterId = rs.getInt(1);
    			BigDecimal amount =rs.getBigDecimal(2);
    			String localeCd = rs.getString(3);
    		
    			if(!locales.contains(localeCd)){
    				locales.add(localeCd);
    			}
    			if(siteCCMap.containsKey(costCenterId)){
    				BigDecimal totalAmt = (BigDecimal)siteCCMap.get(costCenterId);
    				siteCCMap.put(costCenterId, totalAmt.add(amount));
    			}else{
    				siteCCMap.put(costCenterId, amount);
    			}	
    			
    		}
            
    		if(locales.size()>1){
    			throw new RemoteException("reporting.error.multipleCurrencies");
    		}
    		
        } catch (Exception e) {
            throw processException(e);
        } finally {
        	rs.close();
    		stmt.close();
            closeConnection(conn);
        }
    	return siteCCMap;
    }
    
    public CostCenterData getCostCenterData(int costCenterId) throws RemoteException {
    	Connection conn = null;
        try {
            conn = getConnection();
            
            return CostCenterDataAccess.select(conn, costCenterId);
            
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }
    
    public boolean isAccountBudgetExistsForAccount(int pAccountId, int pBudgetYear) throws RemoteException {
    	Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(FiscalCalenderDataAccess.BUS_ENTITY_ID,pAccountId);
            dbc.addEqualTo(FiscalCalenderDataAccess.FISCAL_YEAR,pBudgetYear);
            IdVector fcDV = FiscalCalenderDataAccess.selectIdOnly(conn,dbc);
            if(fcDV.size()==0) {
                return false;
            }
            CatalogData accountCatalogD = BusEntityDAO.getAccountCatalog(conn, pAccountId);
            if(accountCatalogD==null) {
                return false;
            }
            int accountCatalogId = accountCatalogD.getCatalogId();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(CostCenterAssocDataAccess.CATALOG_ID,accountCatalogId);
            crit.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD,
                        RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
            String selCostCenter = CostCenterAssocDataAccess.getSqlSelectIdOnly(CostCenterAssocDataAccess.COST_CENTER_ID,crit);
            
        	// check account budget. return true if exists
            crit = new DBCriteria();
            crit.addEqualTo(BudgetDataAccess.BUS_ENTITY_ID, pAccountId);
            crit.addEqualTo(BudgetDataAccess.BUDGET_YEAR, pBudgetYear);
            crit.addEqualTo(BudgetDataAccess.BUDGET_STATUS_CD, RefCodeNames.BUDGET_STATUS_CD.ACTIVE);
            crit.addEqualTo(BudgetDataAccess.BUDGET_TYPE_CD, RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET);
            crit.addOneOf(BudgetDataAccess.COST_CENTER_ID, selCostCenter);

            logInfo("isAccountBudgetExistsForAccount => SQL: " + BudgetDataAccess.getSqlSelectIdOnly("*", crit));
            IdVector budgetIds = BudgetDataAccess.selectIdOnly(conn, crit);
            if (budgetIds.size() > 0){
            	return true;
            }else{
            	return false;
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }
    
    public boolean isBudgetExistsForAccount(int pAccountId, int pBudgetYear) throws RemoteException {
    	Connection conn = null;
        try {
            conn = getConnection();
            //if no fiscal calender return false.
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(FiscalCalenderDataAccess.BUS_ENTITY_ID,pAccountId);
            //dbc.addEqualTo(FiscalCalenderDataAccess.FISCAL_YEAR,pBudgetYear);
            IdVector fcDV = FiscalCalenderDataAccess.selectIdOnly(conn,dbc);
            if(fcDV.size()==0) {
                return false;
            }
            CatalogData accountCatalogD = BusEntityDAO.getAccountCatalog(conn, pAccountId);
            if(accountCatalogD==null) {
                return false;
            }
            int accountCatalogId = accountCatalogD.getCatalogId();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(CostCenterAssocDataAccess.CATALOG_ID,accountCatalogId);
            crit.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD,
                        RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
            String selCostCenter = CostCenterAssocDataAccess.getSqlSelectIdOnly(CostCenterAssocDataAccess.COST_CENTER_ID,crit);
            
        	// check account budget. return true if exists
            crit = new DBCriteria();
            crit.addEqualTo(BudgetDataAccess.BUS_ENTITY_ID, pAccountId);
            crit.addEqualTo(BudgetDataAccess.BUDGET_YEAR, pBudgetYear);
            crit.addEqualTo(BudgetDataAccess.BUDGET_STATUS_CD, RefCodeNames.BUDGET_STATUS_CD.ACTIVE);
            crit.addEqualTo(BudgetDataAccess.BUDGET_TYPE_CD, RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET);
            crit.addOneOf(BudgetDataAccess.COST_CENTER_ID, selCostCenter);

            logInfo("isBudgetExistsForAccount => SQL: " + BudgetDataAccess.getSqlSelectIdOnly("*", crit));
            IdVector budgetIds = BudgetDataAccess.selectIdOnly(conn, crit);
            if (budgetIds.size() > 0)
            	return true;
            
            // check site budgets, return true if exists.
            crit = new DBCriteria();
            crit.addEqualTo(BudgetDataAccess.BUDGET_YEAR, pBudgetYear);
            crit.addEqualTo(BudgetDataAccess.BUDGET_STATUS_CD, RefCodeNames.BUDGET_STATUS_CD.ACTIVE);
            crit.addEqualTo(BudgetDataAccess.BUDGET_TYPE_CD, RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET);
            crit.addOneOf(BudgetDataAccess.COST_CENTER_ID, selCostCenter);
            
            crit.addDataAccessForJoin(new BudgetDataAccess());
            crit.addJoinCondition(BudgetDataAccess.CLW_BUDGET, BudgetDataAccess.BUS_ENTITY_ID, BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY1_ID);
            crit.addJoinTableEqualTo(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY2_ID,pAccountId);
            crit.addJoinTableEqualTo(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
            IdVector siteBudgetIds = JoinDataAccess.selectIdOnly(conn,BudgetDataAccess.CLW_BUDGET, BudgetDataAccess.BUDGET_ID, crit, 0);
        	return siteBudgetIds.size() > 0;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    	
    }
	 public BigDecimal getAllBudgetSpentForSiteCostCenter(int pSiteId,int pCostCenterId, int pPeriod, int pYear, ArrayList pOrderStatus) throws Exception {
			
			//HashMap siteCCMap = new HashMap();
			BigDecimal ccAmount = new BigDecimal(0);  
			ArrayList locales = new ArrayList();
			Connection conn = null;
			PreparedStatement stmt=null;
			ResultSet rs = null;
			
			try {
				conn = getConnection();
				
				String sql = "select sl.cost_center_id,sum(sl.amount),o.locale_cd "+
								"from clw_site_ledger sl join clw_order o "+
								"on sl.order_id = o.order_id "+
								"where "+
								"sl.site_id IN ("+pSiteId+") "+
								"and o.order_status_cd in ("+Utility.toCommaSting(pOrderStatus,new Character('\''))+") "+
								"and (o.order_budget_type_cd not in ('" + RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE + "', '" + RefCodeNames.ORDER_BUDGET_TYPE_CD.REBILL + "')  or o.order_budget_type_cd is null) "+
								"and sl.budget_year="+pYear;
									
				
				if(pPeriod > 0){
					sql = sql + "and sl.BUDGET_PERIOD = "+pPeriod;
				}
				
				sql = sql + " group by sl.site_id, sl.cost_center_id, o.locale_cd";	
					
				stmt = conn.prepareStatement(sql);
				rs=stmt.executeQuery();

				
				while (rs.next()) {

					int costCenterId = rs.getInt(1);
					BigDecimal amount =rs.getBigDecimal(2);
					String localeCd = rs.getString(3);
				
					if(!locales.contains(localeCd)){
						locales.add(localeCd);
					}
					if(costCenterId == pCostCenterId){
						ccAmount = ccAmount.add(amount);
					}	
					
				}
				
				if(locales.size()>1){
					throw new RemoteException("reporting.error.multipleCurrencies");
				}
				
			} catch (Exception e) {
				throw processException(e);
			} finally {
				rs.close();
				stmt.close();
				closeConnection(conn);
			}
			return ccAmount;
		}


}
