package com.cleanwise.service.api.dto;

import java.util.ArrayList;
import java.util.Set;

public class LocationBudgetChartDto {
	private ArrayList _allocatedBudget = new ArrayList();
	private ArrayList _spentAmount = new ArrayList();
	private ArrayList _pendingAppr= new ArrayList();
	private ArrayList _shoppingCart= new ArrayList();
	private ArrayList _overBudget= new ArrayList();
	private ArrayList _unusedBudget= new ArrayList();
	private boolean _accountHasBudget;
	private String _currencyCode;
	private ArrayList _costCenterNames= new ArrayList();
	private String _totalBD;
	private String _totalCart;
	private String _totalPending;
	private String _totalUnused;
	private String _totalOver;
	private String _totalSpent;
	private String _budgetPeriod;
	private String _siteName;
	private Set<Integer> _siteWithNoDataList; 
	private ArrayList<Integer> _noLastFiscalYearList;
	private ArrayList _unbudgetedSpentList = new ArrayList();
	private ArrayList _unbudgetedPendingList = new ArrayList();
	private ArrayList _unbudgetedCartList = new ArrayList();
	private ArrayList _unbudgetedCostCenterNamesList = new ArrayList();
	private String _unbudgetedTotal;
	
	private String _combinedTotalSpent;
	private String _combinedTotalPending;
	private String _combinedTotalCart;
	

	private ArrayList _combinedSpentList = new ArrayList();
	private ArrayList _combinedPendingList = new ArrayList();
	private ArrayList _combinedCartList = new ArrayList();
	private ArrayList _combinedCostCenterNamesList = new ArrayList();
	
	private boolean _existsBudgetedAmounts;
	private boolean _existsNonBudgetedAmounts;
	private boolean _existsCombinedAmounts;
	
	
	private String _unbudgetedTotalCart;
	private String _unbudgetedTotalPending;
	private String _unbudgetedTotalUnused;
	private String _unbudgetedTotalOver;
	private String _unbudgetedTotalSpent;
	
	/**
	 * @return siteName
	 */
	public String getSiteName() {
		return _siteName;
	}

	/**
	 * @param siteName the siteName to set
	 */
	public void setSiteName(String siteName) {
		this._siteName = siteName;
	}

	/**
	 * @return budgetPeriod
	 */
	public String getBudgetPeriod() {
		return _budgetPeriod;
	}

	/**
	 * @param budgetPeriod the budgetPeriod to set
	 */
	public void setBudgetPeriod(String budgetPeriod) {
		this._budgetPeriod = budgetPeriod;
	}

	/**
	 * @return totalSpent
	 */
	public String getTotalSpent() {
		return _totalSpent;
	}

	/**
	 * @param totalSpent the totalSpent to set
	 */
	public void setTotalSpent(String totalSpent) {
		this._totalSpent = totalSpent;
	}

	/**
	 * @return totalBD
	 */
	public String getTotalBD() {
		return _totalBD;
	}

	/**
	 * @param totalBD the totalBD to set
	 */
	public void setTotalBD(String totalBD) {
		this._totalBD = totalBD;
	}

	/**
	 * @return totalCart
	 */
	public String getTotalCart() {
		return _totalCart;
	}

	/**
	 * @param totalCart the totalCart to set
	 */
	public void setTotalCart(String totalCart) {
		this._totalCart = totalCart;
	}

	/**
	 * @return totalPending
	 */
	public String getTotalPending() {
		return _totalPending;
	}

	/**
	 * @param totalPending the totalPending to set
	 */
	public void setTotalPending(String totalPending) {
		this._totalPending = totalPending;
	}

	/**
	 * @return totalUnused
	 */
	public String getTotalUnused() {
		return _totalUnused;
	}

	/**
	 * @param totalUnused the totalUnused to set
	 */
	public void setTotalUnused(String totalUnused) {
		this._totalUnused = totalUnused;
	}

	/**
	 * @return totalOver
	 */
	public String getTotalOver() {
		return _totalOver;
	}


	/**
	 * @param totalOver the totalOver to set
	 */
	public void setTotalOver(String totalOver) {
		this._totalOver = totalOver;
	}
	
	
	public String getCurrencyCode() {
		return _currencyCode;
	}
	
	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		_currencyCode = currencyCode;
	}
	
	/**
	 * 
	 * @return accountHasBudget
	 */
	public boolean isAccountHasBudget() {
		return _accountHasBudget;
	}
	
	/**
	 * @param accountHasBudget the accountHasBudget to set
	 */
	public void setAccountHasBudget(boolean accountHasBudget) {
		_accountHasBudget = accountHasBudget;
	}
	
	
	/**
	 * 
	 * @return the unusedBudget
	 */
	public ArrayList getUnusedBudget() {
		return _unusedBudget;
	}
	

	/**
	 * @param unusedBudget the unusedBudget to set
	 */
	public void setUnusedBudget(ArrayList unusedBudget) {
		_unusedBudget = unusedBudget;
	}
	
	
	/**
	 * 
	 * @return the allocatedBudget
	 */
	public ArrayList getAllocatedBudget() {
		return _allocatedBudget;
	}
	
	/**
	 * @param allocatedBudget the allocatedBudget to set
	 */
	public void setAllocatedBudget(ArrayList allocatedBudget) {
		_allocatedBudget = allocatedBudget;
	}
	
	/**
	 * 
	 * @return the spentAmount
	 */
	public ArrayList getSpentAmount() {
		return _spentAmount;
	}
	
	/**
	 * @param spentAmount the spentAmount to set
	 */
	public void setSpentAmount(ArrayList spentAmount) {
		_spentAmount = spentAmount;
	}
	
	/**
	 * 
	 * @return the pendingAppr
	 */
	public ArrayList getPendingAppr() {
		return _pendingAppr;
	}
	
	/**
	 * @param pendingAppr the pendingAppr to set
	 */
	public void setPendingAppr(ArrayList pendingAppr) {
		_pendingAppr = pendingAppr;
	}
	
	/**
	 * 
	 * @return the shoppingCart
	 */
	public ArrayList getShoppingCart() {
		return _shoppingCart;
	}
	
	/**
	 * @param shoppingCart the shoppingCart to set
	 */
	public void setShoppingCart(ArrayList shoppingCart) {
		_shoppingCart = shoppingCart;
	}
	
	/**
	 * 
	 * @return the overBudget
	 */
	public ArrayList getOverBudget() {
		return _overBudget;
	}
	
	/**
	 * @param overBudget the overBudget to set
	 */
	public void setOverBudget(ArrayList overBudget) {
		_overBudget = overBudget;
	}
	
	/**
	 * @return the costCenterNames
	 */
	public ArrayList getCostCenterNames() {
		return _costCenterNames;
	}
	
	/**
	 * @param costCenterNames the costCenterNames to set
	 */
	public void setCostCenterNames(ArrayList costCenterNames) {
		_costCenterNames = costCenterNames;
	}

	/**
	 * @return the existsCombinedAmounts
	 */
	public boolean isExistsCombinedAmounts() {
		return _existsCombinedAmounts;
	}

	/**
	 * @param existsCombinedAmounts the existsCombinedAmounts to set
	 */
	public void setExistsCombinedAmounts(boolean existsCombinedAmounts) {
		_existsCombinedAmounts = existsCombinedAmounts;
	}

	/**
	 * @return the existsNonBudgetedAmounts
	 */
	public boolean isExistsNonBudgetedAmounts() {
		return _existsNonBudgetedAmounts;
	}

	/**
	 * @param existsNonBudgetedAmounts the existsNonBudgetedAmounts to set
	 */
	public void setExistsNonBudgetedAmounts(boolean existsNonBudgetedAmounts) {
		_existsNonBudgetedAmounts = existsNonBudgetedAmounts;
	}

	/**
	 * @return the existsBudgetedAmounts
	 */
	public boolean isExistsBudgetedAmounts() {
		return _existsBudgetedAmounts;
	}

	/**
	 * @param existsBudgetedAmounts the existsBudgetedAmounts to set
	 */
	public void setExistsBudgetedAmounts(boolean existsBudgetedAmounts) {
		_existsBudgetedAmounts = existsBudgetedAmounts;
	}

	/**
	 * @return the combinedSpentList
	 */
	public ArrayList getCombinedSpentList() {
		return _combinedSpentList;
	}

	/**
	 * @param combinedSpentList the combinedSpentList to set
	 */
	public void setCombinedSpentList(ArrayList combinedSpentList) {
		_combinedSpentList = combinedSpentList;
	}

	/**
	 * @return the combinedPendingList
	 */
	public ArrayList getCombinedPendingList() {
		return _combinedPendingList;
	}

	/**
	 * @param combinedPendingList the combinedPendingList to set
	 */
	public void setCombinedPendingList(ArrayList combinedPendingList) {
		_combinedPendingList = combinedPendingList;
	}

	/**
	 * @return the combinedCartList
	 */
	public ArrayList getCombinedCartList() {
		return _combinedCartList;
	}

	/**
	 * @param combinedCartList the combinedCartList to set
	 */
	public void setCombinedCartList(ArrayList combinedCartList) {
		_combinedCartList = combinedCartList;
	}

	/**
	 * @return the combinedCostCenterNamesList
	 */
	public ArrayList getCombinedCostCenterNamesList() {
		return _combinedCostCenterNamesList;
	}

	/**
	 * @param combinedCostCenterNamesList the combinedCostCenterNamesList to set
	 */
	public void setCombinedCostCenterNamesList(ArrayList combinedCostCenterNamesList) {
		this._combinedCostCenterNamesList = combinedCostCenterNamesList;
	}

	/**
	 * @return the combinedTotalSpent
	 */
	public String getCombinedTotalSpent() {
		return _combinedTotalSpent;
	}

	/**
	 * @param combinedTotalSpent the combinedTotalSpent to set
	 */
	public void setCombinedTotalSpent(String combinedTotalSpent) {
		_combinedTotalSpent = combinedTotalSpent;
	}

	/**
	 * @return the combinedTotalPending
	 */
	public String getCombinedTotalPending() {
		return _combinedTotalPending;
	}

	/**
	 * @param combinedTotalPending the combinedTotalPending to set
	 */
	public void setCombinedTotalPending(String combinedTotalPending) {
		_combinedTotalPending = combinedTotalPending;
	}

	/**
	 * @return the combinedTotalCart
	 */
	public String getCombinedTotalCart() {
		return _combinedTotalCart;
	}

	/**
	 * @param combinedTotalCart the combinedTotalCart to set
	 */
	public void setCombinedTotalCart(String combinedTotalCart) {
		_combinedTotalCart = combinedTotalCart;
	}

	/**
	 * @return the unbudgetedTotal
	 */
	public String getUnbudgetedTotal() {
		return _unbudgetedTotal;
	}

	/**
	 * @param unbudgetedTotal the unbudgetedTotal to set
	 */
	public void setUnbudgetedTotal(String _unbudgetedTotal) {
		this._unbudgetedTotal = _unbudgetedTotal;
	}

	/**
	 * @return the unbudgetedTotalCart
	 */
	public String getUnbudgetedTotalCart() {
		return _unbudgetedTotalCart;
	}

	/**
	 * @param unbudgetedTotalCart the unbudgetedTotalCart to set
	 */
	public void setUnbudgetedTotalCart(String _unbudgetedTotalCart) {
		this._unbudgetedTotalCart = _unbudgetedTotalCart;
	}

	/**
	 * @return the unbudgetedTotalPending
	 */
	public String getUnbudgetedTotalPending() {
		return _unbudgetedTotalPending;
	}

	/**
	 * @param unbudgetedTotalPending the unbudgetedTotalPending to set
	 */
	public void setUnbudgetedTotalPending(String _unbudgetedTotalPending) {
		this._unbudgetedTotalPending = _unbudgetedTotalPending;
	}

	/**
	 * @return the unbudgetedTotalUnused
	 */
	public String getUnbudgetedTotalUnused() {
		return _unbudgetedTotalUnused;
	}

	/**
	 * @param unbudgetedTotalUnused the unbudgetedTotalUnused to set
	 */
	public void setUnbudgetedTotalUnused(String unbudgetedTotalUnused) {
		this._unbudgetedTotalUnused = unbudgetedTotalUnused;
	}

	/**
	 * @return the unbudgetedTotalOver
	 */
	public String getUnbudgetedTotalOver() {
		return _unbudgetedTotalOver;
	}

	/**
	 * @param unbudgetedTotalOver the unbudgetedTotalOver to set
	 */
	public void setUnbudgetedTotalOver(String unbudgetedTotalOver) {
		this._unbudgetedTotalOver = unbudgetedTotalOver;
	}

	/**
	 * @return the unbudgetedTotalSpent
	 */
	public String getUnbudgetedTotalSpent() {
		return _unbudgetedTotalSpent;
	}

	/**
	 * @param unbudgetedTotalSpent the unbudgetedTotalSpent to set
	 */
	public void setUnbudgetedTotalSpent(String unbudgetedTotalSpent) {
		this._unbudgetedTotalSpent = unbudgetedTotalSpent;
	}


	/**
	 * @return the unbudgetedSpentList
	 */
	public ArrayList getUnbudgetedSpentList() {
		return _unbudgetedSpentList;
	}

	/**
	 * @param unbudgetedSpentList the unbudgetedSpentList to set
	 */
	public void setUnbudgetedSpentList(ArrayList unbudgetedSpentList) {
		_unbudgetedSpentList = unbudgetedSpentList;
	}

	/**
	 * @return the unbudgetedPendingList
	 */
	public ArrayList getUnbudgetedPendingList() {
		return _unbudgetedPendingList;
	}

	/**
	 * @param unbudgetedPendingList the unbudgetedPendingList to set
	 */
	public void setUnbudgetedPendingList(ArrayList unbudgetedPendingList) {
		_unbudgetedPendingList = unbudgetedPendingList;
	}

	/**
	 * @return the unbudgetedCartList
	 */
	public ArrayList getUnbudgetedCartList() {
		return _unbudgetedCartList;
	}

	/**
	 * @param unbudgetedCartList the unbudgetedCartList to set
	 */
	public void setUnbudgetedCartList(ArrayList unbudgetedCartList) {
		_unbudgetedCartList = unbudgetedCartList;
	}

	/**
	 * @return the unbudgetedCostCenterNamesList
	 */
	public ArrayList getUnbudgetedCostCenterNamesList() {
		return _unbudgetedCostCenterNamesList;
	}

	/**
	 * @param unbudgetedCostCenterNamesList the unbudgetedCostCenterNamesList to set
	 */
	public void setUnbudgetedCostCenterNamesList(
			ArrayList _unbudgetedCostCenterNamesList) {
		this._unbudgetedCostCenterNamesList = _unbudgetedCostCenterNamesList;
	}

	/**
	 * @return the noLastFiscalYearList
	 */
	public ArrayList<Integer> getNoLastFiscalYearList() {
		return _noLastFiscalYearList;
	}

	/**
	 * @param noLastFiscalYearList the noLastFiscalYearList to set
	 */
	public void setNoLastFiscalYearList(ArrayList<Integer> noLastFiscalYearList) {
		_noLastFiscalYearList = noLastFiscalYearList;
	}

	/**
	 * @return the siteWithNoDataList
	 */
	public Set<Integer> getSiteWithNoDataList() {
		return _siteWithNoDataList;
	}

	/**
	 * @param siteWithNoDataList the siteWithNoDataList to set
	 */
	public void setSiteWithNoDataList(Set<Integer> siteWithNoDataList) {
		_siteWithNoDataList = siteWithNoDataList;
	}
	
}
