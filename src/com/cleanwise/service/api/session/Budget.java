package com.cleanwise.service.api.session;

/**
 * Title:        Budget
 * Description:  Remote Interface for Budget Stateless Session Bean
 * Purpose:      Provides access to the table-level Budget methods (add by, add date, mod by, mod date)
 * Copyright:    Copyright (c) 2005
 * Company:      Cleanwise, Inc.
 */

import java.rmi.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.math.BigDecimal;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.BudgetSpentCriteria;

public interface Budget extends javax.ejb.EJBObject
{

  /**
   * Adds the Budget information values to be used by the request.
   * @param pUpdateBudgetData  the Budget data.
   * @param pUserName  the user name
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public BudgetData updateBudgetData(BudgetData pUpdateBudgetData,String pUserName)
      throws RemoteException;

   public BudgetView updateBudget(BudgetView pBudget, String pUserName) throws RemoteException;
   
   public BudgetViewVector updateBudgets(BudgetViewVector pBudgets, String pUserName) throws RemoteException;

  /**
   *Fetches the budget for the supplied bus entity
   *@param pBusEntityId the bus entity id of the budget
   *@param pCostCenterId the cost center id of the budget
   *@throws            RemoteException Required by EJB 1.0
   */
   /* public BudgetDataVector fetchBusEntityBudget(int pBusEntityId, int pCostCenterId)
                               throws RemoteException;  */

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
          throws RemoteException;

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
                                               boolean pShowInactive) throws RemoteException;

    /**
   *Saves cost center
   *@param pCostCenter cost center object
   *@param pUser the user login name
   *@return the CostCenterData object
   *@throws            RemoteException Required by EJB 1.0
   *
   */
  public CostCenterData saveCostCenter(CostCenterData pCostCenter, String pUser)
    throws RemoteException;
   
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
    throws RemoteException;


  public int getAccountBudgetPeriod(int pAccountId, 
                                    int pSiteId,
                                    java.util.Date pDate) throws RemoteException;


  public BudgetViewVector getWorkOrderBudgets(int busEntityId, int costCenterId, int pBudgetYear) throws RemoteException;

  public BudgetView getWorkOrderBudget(int busEntityId, int costCenterId, int pBudgetYear) throws RemoteException, DataNotFoundException;

  public BudgetData getWorkOrderBudgetData(int busEntityId, int costCenterId, int pBudgetYear) throws RemoteException, DataNotFoundException;

  public BudgetSpentShortViewVector getWorkOrderBudgetSpendInfo(BudgetSpentCriteria criteria) throws RemoteException;

  public String getWorkOrderBudgetType(int siteId, int pBudgetYear) throws RemoteException;

  public String getWorkOrderBudgetType(int storeId,
                                       int accountId,
                                       int siteId,
                                       int pBudgetYear) throws RemoteException;

  public CostCenterDataVector getWorkOrderCostCenters(int siteId, int pBudgetYear, boolean showInactive) throws RemoteException;

  public CostCenterDataVector getWorkOrderCostCenters(int storeId,
                                                      int accountId,
                                                      int siteId,
                                                      int pBudgetYear,
                                                      boolean showInactive) throws RemoteException;

  public BudgetViewVector getBudgets(int pBusEntityId, int pCostCenterId, int pBudgetYear) throws RemoteException;

  public BudgetViewVector getBudgetsForSite(int pAccountId, int pSiteId, int pCostCenterId, int pBudgetYear) throws RemoteException;

  public boolean isBudgetExistsForAccount(int pAccountId, int pBudgetYear) throws RemoteException;
  
  public BudgetViewVector getAllBudgetsForSites(ArrayList pSiteIds, int pBudgetYear) throws RemoteException;
  
  public CostCenterData getCostCenterData(int costCenterId) throws RemoteException;
  public HashMap getAllBudgetSpentForSites(ArrayList pSiteIds, int pPeriod, int pYear, ArrayList pOrderStatus) throws Exception;
  public boolean isAccountBudgetExistsForAccount(int pAccountId, int pBudgetYear) throws RemoteException ;
  public BigDecimal getAllBudgetSpentForSiteCostCenter(int pSiteId,int pCostCenterId, int pPeriod, int pYear, ArrayList pOrderStatus) throws Exception;
}
