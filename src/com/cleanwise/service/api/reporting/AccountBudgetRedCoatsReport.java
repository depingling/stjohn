/*
 * AccountBudgetReport.java
 *
 * Created on April 27, 2005, 10:21 AM
 */

package com.cleanwise.service.api.reporting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BudgetSpendView;
import com.cleanwise.service.api.value.BudgetSumView;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportResultView;

/**
 *
 * @author bstevens
 */
public class AccountBudgetRedCoatsReport extends BudgetReport {
	private static final BigDecimal ZERO = new BigDecimal(0.00);
	private static final String EMPTY_STRING = "";

	protected int getType() {
		return ACCOUNT_TYPE;
	}

	protected int getRenderType() {
		return ACCOUNT_BUDGET_REPORT;
	}

	/**
	 *Generates a report tab for the CostCenterDetails tab.
	 */
	protected GenericReportResultView generateCostCenterDetails(
			List totalsEntries, List detailEntries) {
		int[] arrayToCheck = null;
		boolean checkPeriod = selBudgetPeriods.get(0).period > 0;
		if (checkPeriod){
			arrayToCheck = new int[selBudgetPeriods.size()];
			for (int i = 0; i < arrayToCheck.length; i++){
				BudgetPeriod period = selBudgetPeriods.get(i);
				arrayToCheck[i] = period.period;
			}
		}
		Collections.sort(totalsEntries, new CostCenterDetailCompare());
		ArrayList results = new ArrayList();
		Iterator it = totalsEntries.iterator();
		while (it.hasNext()) {
			BudgetSpendViewStruct bsvSt = (BudgetSpendViewStruct) it.next();
			BudgetSpendView bsv = bsvSt.budgetSpendView;
			ArrayList aRow = new ArrayList();
			BigDecimal sumamt = bsv.getAmountSpent();
			BigDecimal sumamtalloc = bsv.getAmountAllocated();
			if (sumamt == null) {
				sumamt = ZERO;
			}
			if (sumamtalloc == null) {
				sumamtalloc = ZERO;
			}
			String prevBudgetStart = "0";
			BigDecimal amtdiff = sumamtalloc.subtract(sumamt);
			if (exceededOnly && amtdiff.doubleValue() > 0) {
				// This budget has not been exceeded.
				continue;
			}

			if (checkPeriod && !Utility.isInArray(bsv.getBudgetPeriod(), arrayToCheck)){
				continue;
			}
			String thisBudgetPeriod = bsv.getBudgetPeriodStart();
			if (null == thisBudgetPeriod)
				thisBudgetPeriod = "none";

			if (prevBudgetStart.length() > 0
					&& !thisBudgetPeriod.equals(prevBudgetStart)) {
				// add empty row?
			}
			if (prevBudgetStart.length() == 0) {
				prevBudgetStart = thisBudgetPeriod;
			}

			aRow.add(new Integer(bsv.getBudgetYear()));
			aRow.add(mkPeriodString(bsv.getBudgetPeriodStart()) + "-"
					+ mkPeriodString(bsv.getBudgetPeriodEnd()));

			aRow.add(bsv.getCostCenterName());
			aRow.add(getAccountName(bsvSt.accountId));
			aRow.add(bsv.getSiteReferenceNum());
			aRow.add(bsv.getBusEntityName());

			aRow.add(bsv.getAmountAllocated());
			aRow.add(bsv.getAmountSpent());			
			aRow.add(amtdiff.negate()); // <-- Request by Sunny at Redcoats!
			results.add(aRow);

		}

		GenericReportResultView repResults = GenericReportResultView.createValue();
		repResults.setTable(results);
		repResults.setHeader(getCostCenterDetailsHeader());
		repResults.setColumnCount(repResults.getHeader().size());
		repResults.setName("Cost Center Details");
		return repResults;
	}

	/**
     *Generates the header for the cost center details tab
     */
    protected GenericReportColumnViewVector getCostCenterDetailsHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Fiscal_Year",0,255,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Budget_Period",0,255,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Cost_Center_Name",0,255,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account_Name",0,255,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Job Number",0,255,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site_Name",0,255,"VARCHAR2", "100", false));

        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Amount_Allocated_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Amount_Spent_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Difference_money",2,20,"NUMBER", "100", false));
        return header;
    }

	private class CostCenterDetailCompare implements Comparator {

		public int compare(Object o1, Object o2) {
			if (o1 == null || o2 == null) {
				// should not happen
				return 0;
			}
			BudgetSpendViewStruct sv1 = (BudgetSpendViewStruct) o1;
			BudgetSpendViewStruct sv2 = (BudgetSpendViewStruct) o2;
			
			String str1 = sv1.budgetSpendView.getSiteReferenceNum();
			String str2 = sv2.budgetSpendView.getSiteReferenceNum();
			if (str1 == null) {
				str1 = "";
			}
			if (str2 == null) {
				str2 = "";
			}
			int siteCompRes = str1.compareTo(str2);
			if (siteCompRes != 0) {
				return siteCompRes;
			}
			
			int id1 = sv1.accountId;
			int id2 = sv2.accountId;
			if (id1 != id2) {
				String acctN1 = getAccountName(sv1.accountId);
				String acctN2 = getAccountName(sv2.accountId);
				if (acctN1 == null) {
					acctN1 = "";
				}
				if (acctN2 == null) {
					acctN2 = "";
				}
				int acctCompRes = acctN1.compareTo(acctN2);
				if (acctCompRes != 0) {
					return acctCompRes;
				}
			}
			id1 = sv1.budgetSpendView.getBudgetPeriod();
			id2 = sv2.budgetSpendView.getBudgetPeriod();
			if (id1 != id2) {
				return id1 - id2;
			}			

			String cc1 = sv1.budgetSpendView.getCostCenterName();
			String cc2 = sv2.budgetSpendView.getCostCenterName();
			if (cc1 == null) {
				cc1 = "";
			}
			if (cc2 == null) {
				cc2 = "";
			}
			return cc1.compareTo(cc2);
		}
	};

	private class SiteDetailCompare implements Comparator {

		public int compare(Object o1, Object o2) {
			if (o1 == null || o2 == null) {
				// should not happen
				return 0;
			}
			
			ArrayList a1 = (ArrayList)o1;
			ArrayList a2 = (ArrayList)o2;
			
			String s1 = (String)a1.get(2);
			String s2 = (String)a2.get(2);
		
			return s1.compareToIgnoreCase(s2);
		}
	};


	/**
     *Generates the header for the totals tab
     */
	protected GenericReportColumnViewVector getTotalsReportHeader(boolean accountName) {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Fiscal_Year",0,255,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Budget_Period",0,255,"VARCHAR2", "100", false));
        
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Budget_Name",0,255,"VARCHAR2", "100", false));
        if(accountName){
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account_Name",0,255,"VARCHAR2", "100", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD_Budget_Allocated_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD_Budget_Spent_money",2,20,"NUMBER", "100", false));
        
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD_Budget_Difference_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Annual_Budget_Allocated_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Annual_Budget_Spent_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Annual_Budget_Difference_money",2,20,"NUMBER", "100", false));

        return header;
    }

    /**
     *Generates the site details tab
     */
    protected GenericReportColumnViewVector getSiteDetailsHeader(boolean accountName) {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        
        if(accountName){
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account_Name",0,255,"VARCHAR2", "100", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site_Name",0,255,"VARCHAR2", "100", false));
        //header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","BSC_Name",0,255,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Budget Ref Number",0,255,"VARCHAR2", "100", false));
        
        if ( addTheGsfColumn ) {
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","GSF",0,255,"VARCHAR2", "100", false));
  }
  if (addTheRankIndexColumn) {
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","RANK_INDEX",0,255,"VARCHAR2", "100", false));
  }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","City",0,255,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","State",0,255,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Zip",0,255,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Year",0,255,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Budget_Period",0,255,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD_Budget_Allocated_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD_Budget_Spent_money",2,20,"NUMBER", "100", false));
        
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD_Budget_Difference_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Annual_Budget_Allocated_money",2,20,"NUMBER", "100", false));

        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Annual_Budget_Spent_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Annual_Budget_Difference_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Status",2,20,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Expiration Date",2,20,"VARCHAR2", "100", false));
        return header;
    }


    /**
     *Generates a report tab that looks like a CostCenterDetails tab.
     */
    protected GenericReportResultView generateTotals(List totalsEntries,List detailEntries){
        boolean multiAccount = (accountMap.keySet().size() > 1);

        ArrayList results = new ArrayList();
        for (BudgetPeriod period : selBudgetPeriods){
	        BudgetSumView bs = BudgetSumView.createValue();
	        SumCostCenters sc = new SumCostCenters(totalsEntries, period);
	        ArrayList sumz = sc.getSumz();
	        Collections.sort(sumz,new TotalsCompare(multiAccount,accountMap));
	        Iterator it = sumz.iterator();
	        //Iterator it = sc.getSumz().iterator();
	        int budgetYear = 0;
	        String budgetPeriodStart = null;
	        String budgetPeriodEnd = null;
	        while(it.hasNext()){
	            BudgetPerCostCenterSumView sv = (BudgetPerCostCenterSumView) it.next();
	            if (budgetYear <= 0){
	            	budgetYear = sv.budgetYear;
	            	budgetPeriodStart = sv.budgetPeriodStart;
	            	budgetPeriodEnd = sv.budgetPeriodEnd;            	
	            }
	            ArrayList aRow = new ArrayList();
	            sv.setDefaults();
	            aRow.add(new Integer(sv.budgetYear));
	            aRow.add(mkPeriodString(sv.budgetPeriodStart + "-"
						+ mkPeriodString(sv.budgetPeriodEnd)));
	            aRow.add(sv.costCenterName);
	            if(multiAccount){
	                aRow.add(accountMap.get(new Integer(sv.accountId)));
	            }
	            aRow.add(sv.ytdAmountAllocated);
	            aRow.add(sv.ytdAmountSpent);            
	            aRow.add(sv.ytdDifference.negate());
	            aRow.add(sv.annualAmountAllocated);
	            aRow.add(sv.annualAmountSpent);
	            aRow.add(sv.annualDifference.negate());
	            results.add(aRow);
	        }
	        //now generate a totals row
	        ArrayList aRow = new ArrayList();
	        aRow.add(budgetYear);
	        aRow.add(mkPeriodString(budgetPeriodStart + "-"
					+ mkPeriodString(budgetPeriodEnd)));
	        aRow.add(EMPTY_STRING);
	        if(multiAccount){
	            aRow.add(EMPTY_STRING);
	        }
	        aRow.add(sc.mYTDAllocated);
	        aRow.add(sc.mYTDSpent);        
	        aRow.add(sc.mYTDAllocated.subtract(sc.mYTDSpent).negate());
	        aRow.add(sc.mAllocated);
	        aRow.add(sc.mSpent);
	        aRow.add(sc.mAllocated.subtract(sc.mSpent).negate());
	                

	        results.add(aRow);
    	}

        GenericReportResultView repResults = GenericReportResultView.createValue();
        repResults.setFancyDisplay(true);
        repResults.setTable(results);
        //repResults.setTable(new ArrayList());
        repResults.setHeader(getTotalsReportHeader(multiAccount));
        repResults.setColumnCount(repResults.getHeader().size());
        repResults.setName("Totals");
        return repResults;
    }


    /**
     *Generates a report tab that looks like a CostCenterDetails tab.
     */
    protected GenericReportResultView generateSiteDetails(List totalsEntries,List detailEntries){

        ArrayList results = new ArrayList();
        boolean multiAccount = (accountMap.keySet().size() > 1);
        int idx = 0;
        BigDecimal spent = null;
        BigDecimal alloc = null;
        String sitename = null;

        Hashtable rstbl = new Hashtable();
        for (BudgetPeriod budgetPeriod : selBudgetPeriods){
	        Iterator it = detailEntries.iterator();
	        while(it.hasNext()){
	            BudgetSpendViewStruct bsvSt = (BudgetSpendViewStruct) it.next();
	            BudgetSpendView bsd = bsvSt.budgetSpendView;
	            if (budgetPeriod.period > 0){
	            	bsd.setCurrentBudgetYear(budgetPeriod.year);
	            	bsd.setCurrentBudgetPeriod(budgetPeriod.period);
	            	bsd.setCurrentBudgetPeriodStart(budgetPeriod.start);
	            	bsd.setCurrentBudgetPeriodEnd(budgetPeriod.end);
	            }
	            sitename = bsd.getBusEntityName();
	            if (rstbl.containsKey(sitename)) {
	                ExpSiteBudgetRep sbr = (ExpSiteBudgetRep) rstbl.get(sitename);
	                BudgetSpendView cbsd = sbr.mBSD.budgetSpendView;
	                cbsd.setAmountSpent(bsd.getAmountSpent().add(cbsd.getAmountSpent()));
	                cbsd.setAmountAllocated
	                        (bsd.getAmountAllocated().add(cbsd.getAmountAllocated()));
	                if (isForThisPeriod(bsd.getBudgetYear(),
	                        bsd.getCurrentBudgetYear(),
	                        bsd.getBudgetPeriod(),
	                        bsd.getCurrentBudgetPeriod())
	                        ) {
	                    sbr.mYTDAlloc = sbr.mYTDAlloc.add(bsd.getAmountAllocated());
	                    sbr.mYTDSpent = sbr.mYTDSpent.add(bsd.getAmountSpent());
	                }
	            } else {
	                ExpSiteBudgetRep sbr = new ExpSiteBudgetRep();
	                sbr.mBSD = new BudgetSpendViewStruct(bsd.copy(),bsvSt.accountId);
	                if (isForThisPeriod(bsd.getBudgetYear(),
	                        bsd.getCurrentBudgetYear(),
	                        bsd.getBudgetPeriod(),
	                        bsd.getCurrentBudgetPeriod())
	                        ) {
	                    sbr.mYTDAlloc = sbr.mYTDAlloc.add(bsd.getAmountAllocated());
	                    sbr.mYTDSpent = sbr.mYTDSpent.add(bsd.getAmountSpent());
	                }
	                rstbl.put(sitename, sbr);
	            }
	        }
	        Enumeration e = rstbl.elements();
	        while (e.hasMoreElements()) {
	            ArrayList aRow = new ArrayList();
	            ExpSiteBudgetRep sbr = (ExpSiteBudgetRep) e.nextElement();
	            BudgetSpendView bsd = sbr.mBSD.budgetSpendView;
	            spent = bsd.getAmountSpent();
	            alloc = bsd.getAmountAllocated();
	
	            BigDecimal diff = alloc.subtract(spent);
	
	            if (exceededOnly && diff.doubleValue() > 0) {
	                // This budget has not been exceeded.
	                continue;
	            }
	            if(multiAccount){
	                aRow.add(nullCheck((String) accountMap.get(new Integer(sbr.mBSD.accountId))));
	            }
	            aRow.add(nullCheck(bsd.getBusEntityName()));
	            aRow.add(nullCheck(bsd.getSiteReferenceNum()));
	           // aRow.add(nullCheck(bsd.getBscName()));
	      if ( bsd.getHasGsfColumn() ) {
	    aRow.add(nullCheck(bsd.getGsfValue()));
	      }
	      if ( bsd.getHasRankIndexColumn() ) {
	    aRow.add(nullCheck(bsd.getRankIndexValue()));
	      }
	            aRow.add(nullCheck(bsd.getCity()));
	            aRow.add(nullCheck(bsd.getState()));
	            aRow.add(nullCheck(bsd.getPostalCode()));
	            aRow.add(String.valueOf(bsd.getCurrentBudgetYear()));
	            aRow.add(mkPeriodString(bsd.getCurrentBudgetPeriodStart()) + "-"
						+ mkPeriodString(bsd.getCurrentBudgetPeriodEnd()));
	            aRow.add(nullCheck(sbr.mYTDAlloc));
	            aRow.add(nullCheck(sbr.mYTDSpent));
	            aRow.add(nullCheck(sbr.mYTDAlloc.subtract(sbr.mYTDSpent).negate()));
	            aRow.add(nullCheck(alloc));
	            aRow.add(nullCheck(spent));
	            aRow.add(nullCheck(alloc.subtract(spent).negate()));
	            aRow.add(nullCheck(bsd.getSiteStatus()));
	            aRow.add(nullCheck(bsd.getExpDate()));
	            results.add(aRow);
	        }
        }

        Collections.sort(results, new SiteDetailCompare());
        GenericReportResultView repResults = GenericReportResultView.createValue();
        repResults.setTable(results);
        repResults.setHeader(getSiteDetailsHeader(multiAccount));
        repResults.setColumnCount(repResults.getHeader().size());
        repResults.setName("Site Details");
        return repResults;
    }
}
