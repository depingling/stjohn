/*
 * BudgetReport.java
 *
 * Created on April 25, 2005, 2:59 PM
 */

package com.cleanwise.service.api.reporting;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BudgetSpendView;
import com.cleanwise.service.api.value.BudgetSumView;
import com.cleanwise.service.api.value.FiscalPeriodView;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.UserData;

/**
 *
 * @author bstevens
 */
public class BudgetReport implements GenericReportMulti{

    private static final Logger log = Logger.getLogger(BudgetReport.class);

    private static final BigDecimal ZERO = new BigDecimal(0.00);
    private static final String EMPTY_STRING = "";
    boolean singleSiteQuery;
    protected static int ACCOUNT_TYPE = 0;
    protected static int USER_TYPE = 1;
    protected static int SITE_TYPE = 2;

    protected static int BUDGET_REPORT = 0;
    protected static int ACCOUNT_BUDGET_REPORT = 1;
    protected static int BUDGETS_EXCEEDED_REPORT = 2;

    protected static interface ENTRY_TYPE {
        public static String SITE = "SITE";
        public static String ACCOUNT = "ACCOUNT";
    }

    boolean exceededOnly = false;

    private BusEntityDAO mBDAO = new BusEntityDAO();
    private int currBudgetPeriod = 0;
    private int currBudgetYear = 0;
    private int currAccountId = 0;
    protected HashMap<Integer,String> accountMap = new HashMap<Integer,String>();
    protected List<BudgetPeriod> selBudgetPeriods = null;
    private Map<Integer, List<BudgetPeriod>> periodsByYear = new HashMap<Integer, List<BudgetPeriod>>();

    /**
     * Returns the account name for the account id supplied
     * @param accountId
     */
    protected String getAccountName(int accountId){
    	return (String) accountMap.get(new Integer(accountId));
    }

    /**
     *Different reports can override this method and it will act to get its criteria differently.
     *The data itself is the same, so the various different overriding classes
     *are really just ways of parameterizing this classes behavior.
     */
    protected int getType(){
        return SITE_TYPE;
    }
    /**
     *Different reports can override this method and it will act to render different "tabs"
     *The data itself is the same, so the various different overriding classes
     *are really just ways of parameterizing this classes behavior.
     */
    protected int getRenderType(){
        return BUDGET_REPORT;
    }

    protected boolean accountHasGsf(Connection pCon,
    		String pAccountSql ) throws Exception {

    	return BusEntityDAO.accountHasProp(pCon,
    			pAccountSql,
    	"Gross Square Footage");
    }
    private boolean accountHasRankIndex(Connection pCon,
    		String pAccountSql ) throws Exception {

    	return BusEntityDAO.accountHasProp(pCon,
    			pAccountSql,
    	"Rank Index");
    }

    protected boolean
    addTheGsfColumn = false,
    addTheRankIndexColumn = false;

    private static String
    kGsfSql = " ( select prop.gsf from clv_site_props prop where prop.bus_entity_id = rpt.site_id) gsf  "
    	, kRankIndexSql = " ( select prop.rank_index from clv_site_props prop where prop.bus_entity_id = rpt.site_id) rank_index  ";
    /**
     * Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     */
    boolean mMonthFirst = true;
    String dateFmt = null; 

    public GenericReportResultViewVector
    process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams)
    throws Exception {

    	dateFmt = (String) pParams.get("DATE_FMT");
    	mMonthFirst = true;
    	if ( null == dateFmt ) dateFmt = "mm/dd/yyyy";
    	if ( !dateFmt.toLowerCase().startsWith("mm") ) {
    		mMonthFirst = false;
    	}
    	exceededOnly = (getRenderType() == BUDGETS_EXCEEDED_REPORT);

    	Connection con = pCons.getDefaultConnection(); 
    	//get either year or budget period
        String year = (String)ReportingUtils.getParam(pParams,"BUDGET_YEAR");
        String budgetPeriodParams = (String)ReportingUtils.getParam(pParams, "BUDGET_PERIOD_INFO");
        String[] budgetPeriodsParams = (String[])ReportingUtils.getParam(pParams, "BUDGET_PERIODS_INFO");
        Hashtable props = new Hashtable();
        if(year == null){
        	if (!Utility.isSet(budgetPeriodParams)) {
        		if (budgetPeriodsParams == null || budgetPeriodsParams.length == 0) {
        			String errorMess = "^clwKey^report.errors.budgetPeriodRequiresValue^clwKey^";
        			throw new Exception(errorMess);
        		}else{
        			for (int i = 0; i < budgetPeriodsParams.length; i++){
        				BudgetPeriod budgetPeriod = getBudgetPeriod(budgetPeriodsParams[i]);
        				Integer yearInt = new Integer(budgetPeriod.year);
        				List<BudgetPeriod> periods = periodsByYear.get(yearInt);
        				if (periods == null){
        					periods = new ArrayList<BudgetPeriod>();
        					periodsByYear.put(yearInt, periods);
        				}
        				periods.add(budgetPeriod);
        			}
        		}
            } else {
            	BudgetPeriod budgetPeriod = getBudgetPeriod(budgetPeriodParams);
            	List<BudgetPeriod> periods = new ArrayList<BudgetPeriod>();
            	periods.add(budgetPeriod);
            	periodsByYear.put(new Integer(budgetPeriod.year), periods);

            }
        }else{
        	try{
        		BudgetPeriod budgetPeriod = new BudgetPeriod(Integer.parseInt(year), 0, null, null);
        		List<BudgetPeriod> periods = new ArrayList<BudgetPeriod>();
            	periods.add(budgetPeriod);
            	periodsByYear.put(new Integer(budgetPeriod.year), periods);
        	}catch(NumberFormatException e){
        		String errorMess = null;
        		if (!Utility.isSet(year)) {
            		errorMess = "^clwKey^report.errors.yearRequiresValue^clwKey^";
        		}
        		else {
            		errorMess = "^clwKey^report.errors.yearIntegerValue^clwKey^";
        		}
        		throw new Exception(errorMess);
        	}

        }

        String accountStr = (String)ReportingUtils.getParam(pParams,"ACCOUNT");
        String siteStr = (String)ReportingUtils.getParam(pParams,"SITE");
        String customerStr = (String)ReportingUtils.getParam(pParams,"CUSTOMER");
        int site=0;
        if(Utility.isSet(siteStr)){
            site = Integer.parseInt(siteStr);
        }
        int account=0;
        if(Utility.isSet(accountStr)){
            account = Integer.parseInt(accountStr);
        }
        int customer=0;
        UserData user = null;
        if(Utility.isSet(customerStr)){
            customer = Integer.parseInt(customerStr);
            user =UserDataAccess.select(pCons.getDefaultConnection(),customer);
        }

        try {
        	GenericReportResultViewVector result = new GenericReportResultViewVector();
        	Map<String, GenericReportResultView> resultViewMap = new HashMap<String, GenericReportResultView>();
        	Iterator<Integer> iter = periodsByYear.keySet().iterator();
        	for (int i = 0; iter.hasNext(); i++){
        		Integer yearInt = iter.next();
        		GenericReportResultViewVector currResult = getResultView(con, account, site, customer, user, yearInt.intValue(), periodsByYear.get(yearInt));
        		if (periodsByYear.size() > 1){
        			appendResultTable(resultViewMap, currResult);
        			if (i == 0)
        				result = currResult;
        		}else{
        			result = currResult;
        			break;
        		}
        	}

        	for (int i = 0; i < result.size(); i++) {
                GenericReportResultView report = (GenericReportResultView) result.get(i);
                report.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.A4);
                report.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.PORTRAIT);
            }
        	return result;
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RemoteException("getSpendingData failed: " +e.getMessage());
        }
    }

	private void appendResultTable(Map<String, GenericReportResultView> resultViewMap, GenericReportResultViewVector results){
    	for (int i = 0; i < results.size(); i++){
    		GenericReportResultView resultView = (GenericReportResultView) results.get(i);
    		GenericReportResultView prevResultView = resultViewMap.get(resultView.getName());
        	if (prevResultView != null)
        		prevResultView.getTable().addAll(resultView.getTable());
        	else
        		resultViewMap.put(resultView.getName(), resultView);
    	}

    }
    //************************************* METHODS TO GENERATE THE REPORT DATA *********************************************

    private BudgetPeriod getBudgetPeriod(String budgetPeriodStr) throws Exception {
    	// Parse the line
        String[] tokens = budgetPeriodStr.split("&", -1);
        String[] parVal;
        BudgetPeriod budgetPeriod = new BudgetPeriod();
        for (int i = 0; i < tokens.length; i++) {
            parVal = tokens[i].split("=", -1);
            if (parVal[0].equals("year")){
            	try{
            		budgetPeriod.year = Integer.parseInt(parVal[1]);
            	}catch(NumberFormatException e){
            		String errorMess = "^clwKey^report.errors.yearIntegerValue^clwKey^";
            		throw new Exception(errorMess);
            	}
            }else if (parVal[0].equals("budget_period")){
            	try{
            		budgetPeriod.period = Integer.parseInt(parVal[1]);
            	}catch(NumberFormatException e){
            		String errorMess = "^clwKey^report.errors.budgetPeriodIntegerValue^clwKey^";
            		throw new Exception(errorMess);
            	}
            }else if (parVal[0].equals("start_date")){
            	try{
            		budgetPeriod.start = parVal[1];
            	}catch(NumberFormatException e){
            		String errorMess = "^clwKey^report.errors.budgetPeriodIntegerValue^clwKey^";
            		throw new Exception(errorMess);
            	}
            }else if (parVal[0].equals("end_date")){
            	try{
            		budgetPeriod.end = parVal[1];
            	}catch(NumberFormatException e){
            		String errorMess = "^clwKey^report.errors.budgetPeriodIntegerValue^clwKey^";
            		throw new Exception(errorMess);
            	}
            }
        }
        return budgetPeriod;
	}

    private GenericReportResultViewVector getResultView(Connection con, int account, int site, int customer, UserData user,
    		int year, List<BudgetPeriod> periods) throws Exception{
    	Statement stmt = null;
        ResultSet rs = null;

        String subquery = EMPTY_STRING;

        	GregorianCalendar bstart = new GregorianCalendar();
            String accountSql = null;
        	if(account == 0 && site != 0){
            	account = BusEntityDAO.getAccountForSite(con, site);
            }

            // get the accounts that this user has access to.
        	if (RefCodeNames.USER_TYPE_CD.MSB.equals(user.getUserTypeCd()) || RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(user.getUserTypeCd())) {
        		accountSql = "Select bus_entity_id from clw_user_assoc ua where ua.user_id = "
        			+ customer + " and ua.user_assoc_cd = 'ACCOUNT'";
        	} else {
            	if (account != 0) {
            		accountSql = Integer.toString(account);
            	}
            }

        	addTheGsfColumn = accountHasGsf(con,accountSql);
        	addTheRankIndexColumn = accountHasRankIndex(con,accountSql);

            // January is the 0th month.
        	singleSiteQuery = false;

            //The passed in PIdType can be all kinds of things based off what
            //the caller is interested in.  Don't assume it is
            //a user type code, may in fact be a bus entity type code.
        	if (RefCodeNames.USER_TYPE_CD.MSB.equals(user.getUserTypeCd()) ||
        			RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(user.getUserTypeCd())
        	) {

        		if ( site > 0
        				&& getType() == SITE_TYPE    ) {
        			singleSiteQuery = true;
        			subquery += " rpt.site_id = " + site;
        		} else {
        			subquery += " rpt.site_id in ( select ua.bus_entity_id " +
        			" from clw_user_assoc ua where " +
        			" ua.user_id = " + customer + " and ua.user_assoc_cd = 'SITE') ";
        		}
        	}
        	else {
        		//assume busEntity type code was passed in
        		if (getType() == ACCOUNT_TYPE) {
        			subquery += " rpt.site_id in ( select ba.bus_entity1_id " +
        			" from clw_bus_entity_assoc ba where " +
        			" ba.bus_entity2_id = " + account + " ) ";
        		} else { //assume type of site
        			singleSiteQuery = true;
        			subquery += " rpt.site_id = " + site;
        		}
        	}

        	String query;
        	String yr_subq;

        	if (year > 0){
        		yr_subq = year+"";
        	}else{
    			yr_subq = "( select max(r2.BUDGET_YEAR) "
    				+ " from tclw_acctbudget_report r2 "
    				+ " where r2.site_id = rpt.site_id )";
        	}


        	String sqlcostcentertyp = "SELECT DISTINCT cost_center_type_cd FROM  tclw_acctbudget_report WHERE account_id IN ( select ua.bus_entity_id  from clw_user_assoc ua where  ua.user_id = "+user.getUserId()+" and ua.user_assoc_cd = 'ACCOUNT')";
            stmt = con.createStatement();
            log.info(getClass().getName() + " sqlcostcentertyp SQL: " + sqlcostcentertyp);
            rs = stmt.executeQuery(sqlcostcentertyp);
            boolean justOne = true;
            String budgetTypeCode="";
            if(rs.next()){
            	log.info("got 1");
            	budgetTypeCode = rs.getString(1);
            	if(rs.next()){
            		log.info("got 2");
            		justOne=false;
            	}
            }
            log.info("budgetTypeCode = "+budgetTypeCode);

            String query2 = null;
            if(justOne){
            	if(RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET.equals(budgetTypeCode)){
            		query2 = " ((rpt.cost_center_type_cd = '"+RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET
            		+"' and ("+subquery+")))";
            	}else if(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET.equals(budgetTypeCode)){
            		query2 = "(rpt.cost_center_type_cd = '";
            		if(getType() == SITE_TYPE){
            			query2+=RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET+"' and rpt.account_id IN (" + account +"))";
            		}else{
            			query2+=RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET+"' and rpt.account_id IN (" + accountSql +"))";
            		}
            	}
            }
            if(query2 == null){
            	query2 = " ((rpt.cost_center_type_cd = '"+RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET
            	+"' and ("+subquery+"))"+
            	" OR  (rpt.cost_center_type_cd = '";
            	if(getType() == SITE_TYPE){
            		query2+=RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET+"' and rpt.account_id IN (" + account +")))";
            	}else{
            		query2+=RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET+"' and rpt.account_id IN (" + accountSql +")))";
            	}
            }

            query = " select rpt.budget_year, rpt.budget_period, " +
            " rpt.cost_center_id, rpt.cost_center_name, " +
            " rpt.site_id, " +
            " rpt.account_id, " +
            " rpt.site_short_desc, " +
            " rpt.period_start_date , " +
            " rpt.period_end_date , " +
            " rpt.amount_allocated , " +
            " rpt.amount_spent as total_amount_spent, " +
            " rpt.city, rpt.state_code, rpt.postal_code, rpt.bsc_name, rpt.entry_type, "
            + " rpt.cost_center_type_cd, site.bus_entity_status_cd, to_char(site.exp_date, '" + dateFmt + "') exp_date, sref.clw_value as sref " ;
            if ( addTheGsfColumn ) {
            	query += "," + kGsfSql;
            }
            if ( addTheRankIndexColumn ) {
            	query += "," + kRankIndexSql;
            }
            query += " from  " +
            " tclw_acctbudget_report rpt, clw_bus_entity site,  clw_bus_entity account, (select * from clw_property where short_desc = '"+RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER+"') sref " +
            " where site.bus_entity_id(+) = rpt.site_id" +
            " and account.bus_entity_id(+) = rpt.site_id" +
            " and sref.bus_entity_id(+) = rpt.site_id" +
            " and " + query2 +
            " and rpt.budget_year = " + yr_subq;

            query +=" order by account_id, rpt.budget_year, rpt.budget_period, " +
            	" rpt.cost_center_id, rpt.site_short_desc, " +
            	" rpt.city, rpt.state_code, rpt.postal_code ";

            long timer = System.currentTimeMillis();
            stmt = con.createStatement();
            log.info(getClass().getName() + " getSpendingData SQL: " + query);
            rs = stmt.executeQuery(query);
            log.info("Query was executed in: "+((System.currentTimeMillis()-timer)/1000) +" Seconds");
            timer = System.currentTimeMillis();

        List<BudgetSqlResultRow> resultRows = new ArrayList<BudgetSqlResultRow>();

        List<BudgetSpendViewStruct> totalsEntries = new ArrayList<BudgetSpendViewStruct>();
        List<BudgetSpendViewStruct> detailEntries = new ArrayList<BudgetSpendViewStruct>();

        int ct = 0;
        selBudgetPeriods = periods;
        boolean siteBudgetFl = false;

        while (rs.next()) {
            BudgetSqlResultRow row = parseResultSet(rs);
            resultRows.add(row);
        }

        rs.close();
        stmt.close();
        stmt = null;
        rs = null;


        List<Integer> siteIds = getSiteIdsOnly(resultRows);

        boolean applyFilterByCostCenter = false;
        if (siteIds != null) {
            applyFilterByCostCenter = true; // only if siteIds!=null
        }

        // is empty if !applyFilterByCostCenter
        Map<Integer/*siteId*/, Set<Integer/*costCenterIds*/>> siteCostCenterMap = getSiteCostCenterMap(con, siteIds);

        log.info("process()=> applyFilterByCostCenter: " + applyFilterByCostCenter);

        for (BudgetSqlResultRow resultRow : resultRows) {

            ct++;
            if (ct == 1000) {
                log.info("processed 1000 records in: " + ((System.currentTimeMillis() - timer) / 1000) + " Seconds");
                ct = 0;
            }

            // is null if !applyFilterByCostCenter
            Set<Integer> costCenters = siteCostCenterMap.get(resultRow.getSiteId());

            if (!applyFilterByCostCenter
                    || (costCenters != null
                    && costCenters.contains(resultRow.getCostCenterId()))) {

                int recSiteId = resultRow.getSiteId();

                String entryType = resultRow.getEntryType();
                String costCtrType = resultRow.getCostCenterType();

                if (RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET.equals(costCtrType)) {
                    if ("SITE".equals(entryType)) {
                        if (singleSiteQuery && recSiteId == site) {
                            //We want to add this row to the detail
                            detailEntries.add(createReportEntry(con, resultRow));
                        } else if (!singleSiteQuery) {
                            //We want to add this row to the detail
                            detailEntries.add(createReportEntry(con, resultRow));
                        }
                    } else {
                        //We want to add to the totals
                        BudgetSpendViewStruct totalRec = createReportEntry(con, resultRow);
                        totalsEntries.add(totalRec);
                    }
                } else if ("SITE".equals(entryType)) {
                    //We want to add this row to the totals and to the detail
                    siteBudgetFl = true;
                    totalsEntries.add(createReportEntry(con, resultRow));
                    detailEntries.add(createReportEntry(con, resultRow));
                }

            }
        }

        //logDebug("=== 2 Added up all spending.");
        log.info("Rendering the report");

        GenericReportResultViewVector retVal = new GenericReportResultViewVector();
        //Now create the appropriate genericReportResults
        if (getRenderType() == BUDGET_REPORT) {
            retVal.add(generateCostCenterDetails(totalsEntries, detailEntries, siteBudgetFl));
            retVal.add(generateTotals(totalsEntries, detailEntries));
        } else if (getRenderType() == ACCOUNT_BUDGET_REPORT || getRenderType() == BUDGETS_EXCEEDED_REPORT) {
            retVal.add(generateCostCenterDetails(totalsEntries, detailEntries, siteBudgetFl));
            if (siteBudgetFl) {
                retVal.add(generateSiteDetails(totalsEntries, detailEntries));
            }
            Object o = generateBSCDetails(totalsEntries, detailEntries);
            if (o != null) {
                retVal.add(o);
            }
            retVal.add(generateTotals(totalsEntries, detailEntries));
        } else {
            throw new Exception("wrong render type");
        }

        return retVal;
    }

    private Map<Integer, Set<Integer>> getSiteCostCenterMap(Connection pCon, List<Integer> pSiteIds) throws Exception {

        Map<Integer, Set<Integer>> result = new HashMap<Integer, Set<Integer>>();

        if (pSiteIds != null) {

            //init map
            for (Integer siteId : pSiteIds) {
                result.put(siteId, new HashSet<Integer>());
            }

            List<List> pckgs = Utility.createPackages(pSiteIds, 500);

            for (List pack : pckgs) {

                String query = "SELECT DISTINCT BE.BUS_ENTITY_ID, BU.COST_CENTER_ID \n" +
                        "FROM \n" +
                        "  CLW_BUS_ENTITY BE, \n" +
                        "  CLW_BUDGET BU \n" +
                        "WHERE  BE.BUS_ENTITY_ID IN (" + IdVector.toCommaString(pack) + ") \n" +
                        "  AND BE.BUS_ENTITY_TYPE_CD = '" + RefCodeNames.BUS_ENTITY_TYPE_CD.SITE + "' \n" +
                        "  AND BU.BUS_ENTITY_ID = BE.BUS_ENTITY_ID \n" +
                        "  AND BU.BUDGET_TYPE_CD = '" + RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET + "' \n" +
                        "  AND BUDGET_STATUS_CD = '" + RefCodeNames.BUDGET_STATUS_CD.ACTIVE + "' \n";;

                log.info("getSiteCostCenterMap()=> SQL: " + query);

                Statement stmt = null;
                ResultSet rs = null;

                try {
                    stmt = pCon.createStatement();
                    rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        int siteId = rs.getInt(1);
                        int cosCenterId = rs.getInt(2);
                        result.get(siteId).add(cosCenterId);
                    }
                } finally {
                    close(rs);
                    close(stmt);
                }

            }
        }

        return result;

    }

    public void  close(ResultSet rs) throws Exception {
         if (rs != null) {
             rs.close();
         }
    }

    public void  close(Statement stmt) throws Exception {
         if (stmt != null) {
             stmt.close();
         }
    }

    private List<Integer> getSiteIdsOnly(List<BudgetSqlResultRow> pResultRows) {
        Set<Integer> siteIdSet = new HashSet<Integer>();
        for (BudgetSqlResultRow row : pResultRows) {
            if (ENTRY_TYPE.ACCOUNT.equalsIgnoreCase(row.getEntryType())) {
                log.info("getSiteIdsOnly()=> '"+ENTRY_TYPE.ACCOUNT+"' Entry Type has been found. Row: " + row+ ".");
                log.info("getSiteIdsOnly()=>  Filter by cost center will be ignored.");
                return null;
            } else {
                siteIdSet.add(row.getSiteId());
            }
        }
        return new ArrayList<Integer>(siteIdSet);
    }

    /**
     *Generates a report tab for the CostCenterDetails tab.
     */
    protected GenericReportResultView generateCostCenterDetails
  (List totalsEntries,List detailEntries, boolean pSiteBudgetFl){

        boolean multiAccount = (accountMap.keySet().size() > 1);
        ArrayList results = new ArrayList();
        Iterator it = totalsEntries.iterator();
        while(it.hasNext()){

            BudgetSpendViewStruct bsvSt = (BudgetSpendViewStruct) it.next();
            BudgetSpendView bsv = bsvSt.budgetSpendView;
            ArrayList aRow = new ArrayList();
            BigDecimal sumamt = bsv.getAmountSpent();
            BigDecimal sumamtalloc = bsv.getAmountAllocated();
            if(sumamt == null){
                sumamt = ZERO;
            }
            if(sumamtalloc == null){
                sumamtalloc = ZERO;
            }
            String prevBudgetStart = "0";
            BigDecimal amtdiff = sumamtalloc.subtract(sumamt) ;
            if (exceededOnly && amtdiff.doubleValue() > 0) {
              // This budget has not been exceeded.
              continue;
            }

            String thisBudgetPeriod = bsv.getBudgetPeriodStart();
            if ( null == thisBudgetPeriod ) thisBudgetPeriod = "none";

            if ( prevBudgetStart.length() > 0 && !thisBudgetPeriod.equals(prevBudgetStart) ) {
                //add empty row?
            }
            if (prevBudgetStart.length() == 0 ) {
                prevBudgetStart = thisBudgetPeriod;
            }


            aRow.add(new Integer(bsv.getBudgetYear()));
            aRow.add(mkPeriodString(bsv.getBudgetPeriodStart())
         + "-" +
         mkPeriodString(bsv.getBudgetPeriodEnd())
         );

            aRow.add(bsv.getCostCenterName());
            if(multiAccount){
                aRow.add(accountMap.get(new Integer(bsvSt.accountId)));
            }
            if(pSiteBudgetFl){
            	 aRow.add(bsv.getBusEntityName());
                 aRow.add(bsv.getSiteReferenceNum());
            }
            aRow.add(bsv.getAmountSpent());
            aRow.add(bsv.getAmountAllocated());
            aRow.add(amtdiff);
            results.add(aRow);

        }

        GenericReportResultView repResults = GenericReportResultView.createValue();
        repResults.setTable(results);
        repResults.setHeader(getCostCenterDetailsHeader(multiAccount, pSiteBudgetFl));
        repResults.setColumnCount(repResults.getHeader().size());
        repResults.setName("Cost Center Details");
        return repResults;
    }


    /**
     *Generates a report tab for the CostCenterDetails tab.
     */
    private GenericReportResultView generateBSCDetails(List totalsEntries,List detailEntries){
        ArrayList results = new ArrayList();

        BigDecimal spent = null;
        BigDecimal ytdalloc = null;
        BigDecimal alloc = null;
        String bscname = null;

        Hashtable rstbl = new Hashtable();
        Iterator it = detailEntries.iterator();
        while(it.hasNext()){
            BudgetSpendViewStruct bsvSt = (BudgetSpendViewStruct) it.next();
            BudgetSpendView bsd = bsvSt.budgetSpendView;
            bscname = bsd.getBscName();
            if (null == bscname || bscname.trim().length() == 0) {
                bscname = "none";
            }

            if (rstbl.containsKey(bscname)) {
                ExpSiteBudgetRep br = (ExpSiteBudgetRep) rstbl.get(bscname);
                BudgetSpendView cbsd = br.mBSD.budgetSpendView;
                cbsd.setAmountSpent(bsd.getAmountSpent().add(cbsd.getAmountSpent()));
                cbsd.setAmountAllocated
                        (bsd.getAmountAllocated().add(cbsd.getAmountAllocated()));
                if (isForThisPeriod(bsd.getBudgetYear(),
                        bsd.getCurrentBudgetYear(),
                        bsd.getBudgetPeriod(),
                        bsd.getCurrentBudgetPeriod())
                        ) {
                    br.mYTDAlloc = br.mYTDAlloc.add(bsd.getAmountAllocated());
                }
            } else {
                ExpSiteBudgetRep br = new ExpSiteBudgetRep();
                br.mBSD = new BudgetSpendViewStruct(bsd.copy(),bsvSt.accountId);
                if (isForThisPeriod(bsd.getBudgetYear(),
                        bsd.getCurrentBudgetYear(),
                        bsd.getBudgetPeriod(),
                        bsd.getCurrentBudgetPeriod())
                        ) {
                    br.mYTDAlloc = br.mYTDAlloc.add(bsd.getAmountAllocated());
                }
                rstbl.put(bscname, br);
            }

        }
        HashSet bscNames = new HashSet();

        Enumeration e = rstbl.elements();
        while (e.hasMoreElements()) {
            ArrayList aRow = new ArrayList();
            ExpSiteBudgetRep br = (ExpSiteBudgetRep) e.nextElement();
            BudgetSpendView bsd = br.mBSD.budgetSpendView;
            spent = bsd.getAmountSpent();
            alloc = bsd.getAmountAllocated();
            ytdalloc = br.mYTDAlloc;

            BigDecimal diff = ytdalloc.subtract(spent);
            if(Utility.isSet(bsd.getBscName())){
                bscNames.add(bsd.getBscName());
            }
            aRow.add(bsd.getBscName());
            aRow.add(String.valueOf(bsd.getBudgetYear()));
            aRow.add(spent);
            aRow.add(ytdalloc);
            aRow.add(diff);
            aRow.add(alloc);
            aRow.add(alloc.subtract(spent));
            if (alloc != null && alloc.doubleValue() > 0) {
                aRow.add(spent.divide(alloc, 2, BigDecimal.ROUND_HALF_UP));
            }else{
                aRow.add(ZERO);
            }
            results.add(aRow);
        }
        if(bscNames.isEmpty()){
           return null;
        }
  if(!getRenderBSCTab()){
    return null;
  }
        GenericReportResultView repResults = GenericReportResultView.createValue();
        repResults.setTable(results);
        repResults.setHeader(getBSCDetailsHeader());
        repResults.setColumnCount(repResults.getHeader().size());
        repResults.setName("BSC Details");
        return repResults;
    }

    protected boolean getRenderBSCTab(){
  return true;
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
	        log.info("sc=" + sc);
	        ArrayList sumz = sc.getSumz();
	        Collections.sort(sumz,new TotalsCompare(multiAccount,accountMap));
	        Iterator it = sumz.iterator();
	        //Iterator it = sc.getSumz().iterator();
	        while(it.hasNext()){
	            BudgetPerCostCenterSumView sv = (BudgetPerCostCenterSumView) it.next();
	            ArrayList aRow = new ArrayList();
	            sv.setDefaults();
	            aRow.add(sv.costCenterName);
	            if(multiAccount){
	                aRow.add(accountMap.get(new Integer(sv.accountId)));
	            }
	            aRow.add(sv.ytdAmountSpent);
	            aRow.add(sv.ytdAmountAllocated);
	            aRow.add(sv.ytdDifference);
	            aRow.add(sv.annualAmountAllocated);
	            aRow.add(sv.annualDifference);
	            aRow.add(sv.getAnnualPercentageSpent());
	            results.add(aRow);
	        }
	        //now generate a totals row
	        ArrayList aRow = new ArrayList();
	        aRow.add(EMPTY_STRING);
	        if(multiAccount){
	            aRow.add(EMPTY_STRING);
	        }
	        aRow.add(sc.mYTDSpent);
	        aRow.add(sc.mYTDAllocated);
	        aRow.add(sc.mYTDAllocated.subtract(sc.mYTDSpent));
	        aRow.add(sc.mAllocated);
	        aRow.add(sc.mAllocated.subtract(sc.mSpent));
	        //calc % spent
	        if (sc.mAllocated.intValue() != 0 ) {
	            aRow.add(sc.mYTDSpent.divide(sc.mAllocated, 2, BigDecimal.ROUND_HALF_UP));
	        } else {
	            aRow.add(ZERO);
	        }
	        log.info("calculateBudgetTotals done" );

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
        BigDecimal ytdalloc = null;
        BigDecimal alloc = null;
        String sitename = null;
        String siteRefNum = null;

        Hashtable rstbl = new Hashtable();
        Iterator it = detailEntries.iterator();
        while(it.hasNext()){
            BudgetSpendViewStruct bsvSt = (BudgetSpendViewStruct) it.next();
            BudgetSpendView bsd = bsvSt.budgetSpendView;
            sitename = bsd.getBusEntityName();
            siteRefNum = bsd.getSiteReferenceNum();
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
            ytdalloc = sbr.mYTDAlloc;

            BigDecimal diff = ytdalloc.subtract(spent);

            if (exceededOnly && diff.doubleValue() > 0) {
                // This budget has not been exceeded.
                continue;
            }
            if(multiAccount){
                aRow.add(nullCheck((String) accountMap.get(new Integer(sbr.mBSD.accountId))));
            }
            aRow.add(nullCheck(bsd.getBusEntityName()));
            aRow.add(nullCheck(bsd.getSiteReferenceNum()));
            aRow.add(nullCheck(bsd.getBscName()));
      if ( bsd.getHasGsfColumn() ) {
    aRow.add(nullCheck(bsd.getGsfValue()));
      }
      if ( bsd.getHasRankIndexColumn() ) {
    aRow.add(nullCheck(bsd.getRankIndexValue()));
      }
            aRow.add(nullCheck(bsd.getCity()));
            aRow.add(nullCheck(bsd.getState()));
            aRow.add(nullCheck(bsd.getPostalCode()));
            aRow.add(String.valueOf(bsd.getBudgetYear()));
            aRow.add(nullCheck(spent));
            aRow.add(nullCheck(ytdalloc));
            aRow.add(nullCheck(ytdalloc.subtract(spent)));
            aRow.add(nullCheck(alloc));
            aRow.add(nullCheck(alloc.subtract(spent)));
            if (alloc != null && alloc.doubleValue() > 0) {
                aRow.add(nullCheck(spent.divide(alloc, 2, BigDecimal.ROUND_HALF_UP)));
            }else{
                aRow.add(ZERO);
            }
            aRow.add(nullCheck(bsd.getSiteStatus()));
            aRow.add(nullCheck(bsd.getExpDate()));
            results.add(aRow);
        }


        GenericReportResultView repResults = GenericReportResultView.createValue();
        repResults.setTable(results);
        repResults.setHeader(getSiteDetailsHeader(multiAccount));
        repResults.setColumnCount(repResults.getHeader().size());
        repResults.setName("Site Details");
        return repResults;
    }

    //************************************* END METHODS TO GENERATE THE REPORT DATA *********************************************

    //************************************* METHODS TO GENERATE THE HEADERS *********************************************
    /**
     *Generates the header for the bsc details tab
     */
    private GenericReportColumnViewVector getBSCDetailsHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","BSC_Name",0,255,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Year",0,255,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD_Budget_Spent_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD_Budget_Allocated_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD_Budget_Difference_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Annual_Budget_Allocated_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Annual_Budget_Remaining_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Annual_Spent_pct",2,20,"NUMBER", "100", false));
        return header;
    }

    /**
     *Generates the header for the cost center details tab
     */
    protected GenericReportColumnViewVector getCostCenterDetailsHeader(boolean accountName, boolean pSiteBudgetFl) {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Fiscal_Year",0,255,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Budget_Period",0,255,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Cost_Center_Name",0,255,"VARCHAR2", "100", false));
        if(accountName){
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account_Name",0,255,"VARCHAR2", "100", false));
        }
        if(pSiteBudgetFl){
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site_Name",0,255,"VARCHAR2", "100", false));
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Budget Ref Number",0,255,"VARCHAR2", "100", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Amount_Spent_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Amount_Allocated_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Difference_money",2,20,"NUMBER", "100", false));
        return header;
    }

    /**
     *Generates the header for the totals tab
     */
    protected GenericReportColumnViewVector getTotalsReportHeader(boolean accountName) {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Budget_Name",0,255,"VARCHAR2", "100", false));
        if(accountName){
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account_Name",0,255,"VARCHAR2", "100", false));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD_Budget_Spent_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD_Budget_Allocated_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD_Budget_Difference_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Annual_Budget_Allocated_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Annual_Budget_Remaining_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Annual_Spent_pct",2,20,"NUMBER", "100", false));
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
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Budget Ref Number",0,255,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","BSC_Name",0,255,"VARCHAR2", "100", false));
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
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD_Budget_Spent_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD_Budget_Allocated_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD_Budget_Difference_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Annual_Budget_Allocated_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Annual_Budget_Remaining_money",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Annual_Spent_pct",2,20,"NUMBER", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Status",2,20,"VARCHAR2", "100", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Expiration Date",2,20,"VARCHAR2", "100", false));
        return header;
    }

    //************************************* END METHODS TO GENERATE THE HEADERS *********************************************


    //************************************* Utility Helper Methods *********************************************
    /**
     *Parses the result set into a BudgetSpendView that we can operate off of.  This is part legacy of the old BudgetFormatter code.
     */
    private BudgetSpendViewStruct createReportEntry(Connection pCon, BudgetSqlResultRow pRow) throws Exception {

        //String costCenterTypeCd = rs.getString("COST_CENTER_TYPE_CD");

        BudgetSpendView b = BudgetSpendView.createValue();

        b.setBudgetYear(pRow.getBudgetYesr());

        int budgetPeriod = pRow.getBudgetPeriod();
        if (budgetPeriod <= 0) {
            budgetPeriod = 1;
        }
        b.setBudgetPeriod(budgetPeriod);

        int accountId = pRow.getAccountId();
        if (!accountMap.containsKey(accountId) && accountId != 0) {
            String accountName;
            try {
                accountName = BusEntityDataAccess.select(pCon, accountId).getShortDesc();
            } catch (Exception e) {
                accountName = EMPTY_STRING;
            }
            accountMap.put(accountId, accountName);
        }

        if (currBudgetPeriod == 0 || currBudgetYear == 0 || accountId != currAccountId) {
            log.info("Fetching the fiscal period view");
            FiscalPeriodView fpv = mBDAO.getFiscalInfo(pCon, accountId);
            currBudgetPeriod = fpv.getCurrentFiscalPeriod();
            currBudgetYear = fpv.getFiscalCalenderView().getFiscalCalender().getFiscalYear();
            currAccountId = accountId;
        }

        b.setCurrentBudgetPeriod(currBudgetPeriod);
        b.setCurrentBudgetYear(currBudgetYear);

        b.setCostCenterId(pRow.getCostCenterId());
        b.setCostCenterName(pRow.getCostCenterName());
        b.setBusEntityName(pRow.getSiteShortDesc());
        b.setAmountSpent(new BigDecimal(pRow.geTotalAmountSpent()));
        b.setBudgetPeriodStart(pRow.getPeriodStartDate());
        b.setBudgetPeriodEnd(pRow.getPeriodEndDate());
        b.setCity(pRow.getCity());
        b.setState(pRow.getStateCode());
        b.setPostalCode(pRow.getPostalCode());
        b.setBscName(pRow.getBscName());
        b.setAmountAllocated(new BigDecimal(pRow.getAmountAllocated()));
        b.setBusEntityId(pRow.getSiteId());
        b.setSiteStatus(pRow.getBusEntityStatusCd());
        b.setExpDate(pRow.getExpDate());
        b.setSiteReferenceNum(pRow.getSRef());

        b.setHasGsfColumn(addTheGsfColumn);
        if (addTheGsfColumn) {
            b.setGsfValue(pRow.getGsf());
        }

        b.setHasRankIndexColumn(addTheRankIndexColumn);
        if (addTheRankIndexColumn) {
            b.setRankIndexValue(pRow.getRankIndex());
        }

        return new BudgetSpendViewStruct(b, accountId);
    }

    private BudgetSqlResultRow parseResultSet(ResultSet rs) throws Exception {

        BudgetSqlResultRow row = new BudgetSqlResultRow();

        row.setEntryType(rs.getString("entry_type"));
        row.setBudgetYear(rs.getInt("budget_year"));
        row.setBudgetPeriod(rs.getInt("budget_period"));
        row.setAccountId(rs.getInt("account_id"));
        row.setCostCenterId(rs.getInt("cost_center_id"));
        row.setCostCenterName(rs.getString("cost_center_name"));
        row.setCostCenterType(rs.getString("cost_center_type_cd"));
        row.setSiteShortDesc(rs.getString("site_short_desc"));
        row.setTotalAmountSpent(rs.getDouble("total_amount_spent"));
        row.setPeriodStartDate(rs.getString("period_start_date"));
        row.setPeriodEndDate(rs.getString("period_end_date"));
        row.setCity(rs.getString("city"));
        row.setStateCode(rs.getString("state_code"));
        row.setPostalCode(rs.getString("postal_code"));
        row.setBscName(rs.getString("bsc_name"));
        row.setAmountAllocated(rs.getDouble("amount_allocated"));
        row.setSiteId(rs.getInt("site_id"));
        row.setBusEntityStatusCd(rs.getString("bus_entity_status_cd"));
        row.setExpDate(rs.getString("exp_date"));
        row.setSRef(rs.getString("sref"));
        row.setGsf(addTheGsfColumn ? rs.getString("gsf") : null);
        row.setRankIndex(addTheRankIndexColumn ? rs.getString("rank_index") : null);

        return row;
    }


    /**
     *Takes in a String and if null will convert it to ""
     */
    protected BigDecimal nullCheck(BigDecimal o){
        if(o == null){
            return ZERO;
        }
        return o;
    }

    /**
     *Takes in a String and if null will convert it to ""
     */
    protected String nullCheck(String o){
        if(o == null){
            return EMPTY_STRING;
        }
        return o;
    }


    /**
     *moved from the old BudgetFormatter.
     *Returns true if the passed in paramters are for the current period in a YTD way.  Ignores the actual budget type
     *as the reports display both by period and YTD.
     */
    protected boolean isForThisPeriod(int pYear,
            int pThisYr,
            int pAllocPeriod,
            int pThisPeriod) {
        if (pYear == pThisYr && pAllocPeriod <= pThisPeriod) {
            return true;
        }

        if (pThisYr > pYear && pYear != 0) {
            return true;
        }
        return false;
    }

    /**
     *Container class to hold a budget spend view as well as some aggregated totals
     */
    class ExpSiteBudgetRep {
        public BudgetSpendViewStruct mBSD = null;
        public BigDecimal mYTDAlloc = new BigDecimal(0);
        public BigDecimal mYTDSpent = new BigDecimal(0);
    }
    ;

    /**
     *Container class to hold some temp values for us while we manipulte them
     */
    protected class BudgetPerCostCenterSumView{
        private int costCenterId;
        protected String costCenterName;
        protected int budgetYear;
        protected int currentBudgetPeriod;
        protected String budgetPeriodStart;
        protected String budgetPeriodEnd;
        protected BigDecimal ytdAmountSpent;
        protected BigDecimal ytdAmountAllocated;
        protected BigDecimal ytdDifference;
        protected BigDecimal annualAmountSpent;
        protected BigDecimal annualAmountAllocated;
        protected BigDecimal annualDifference;
        protected int accountId;

        /**
         *Calulates the annual percentage spent based off the currently set values
         */
        protected BigDecimal getAnnualPercentageSpent(){
            if (annualAmountAllocated.intValue() != 0 ) {
                java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                return(ytdAmountSpent.divide(annualAmountAllocated, 2, BigDecimal.ROUND_HALF_UP));
            }
            return ZERO;
        }
        //sets anything that is null to a defaut value ("", or 0)
        void setDefaults(){
            if(costCenterName == null){
                costCenterName = EMPTY_STRING;
            }
            if(ytdAmountSpent == null){
                ytdAmountSpent = ZERO;
            }
            if(ytdAmountAllocated == null){
                ytdAmountAllocated = ZERO;
            }
            if(ytdDifference == null){
                ytdDifference = ZERO;
            }
            if(annualAmountSpent == null){
                annualAmountSpent = ZERO;
            }
            if(annualAmountAllocated == null){
                annualAmountAllocated = ZERO;
            }
            if(annualDifference == null){
                annualDifference = ZERO;
            }
        }
    }

    /**
     *Inner class holds a budget spend view along with an account id.
     */
    protected class BudgetSpendViewStruct{
    	protected BudgetSpendView budgetSpendView;
    	protected int accountId;
        protected BudgetSpendViewStruct(BudgetSpendView val, int val2){
            budgetSpendView = val;
            accountId = val2;
        }
    }

    /**
     *Inner class moved from the old BudgetFormatter
     */
    protected class SumCostCenters {
        public SumCostCenters(List pV, BudgetPeriod budgetPeriod) {
            for (int i = 0; i < pV.size(); i++){
                sumEntry((BudgetSpendViewStruct)pV.get(i), budgetPeriod);
            }
        }

        public java.math.BigDecimal mSpent = ZERO;
        public java.math.BigDecimal mYTDSpent = ZERO;
        public java.math.BigDecimal mYTDAllocated = ZERO;
        public java.math.BigDecimal mAllocated = ZERO;
        GregorianCalendar mNow = new GregorianCalendar();
        int tmonth = mNow.get(Calendar.MONTH) + 1;
        private String thismmdd = EMPTY_STRING + tmonth + "/" +
                mNow.get(Calendar.DAY_OF_MONTH);
        private java.util.Hashtable mSumEachCostCenter = new java.util.Hashtable();

        public java.util.ArrayList getSumz() {

            java.util.ArrayList al = new java.util.ArrayList(mSumEachCostCenter.values());

            return al;
        }


        private BigDecimal sumAmt(BigDecimal v1, BigDecimal v2) {

            if (v1 == null)
                v1 = new BigDecimal(0);

            if (v2 == null)
                v2 = new BigDecimal(0);

            return v1.add(v2);
        }

        public void sumEntry(BudgetSpendViewStruct pSpendView, BudgetPeriod budgetPeriod) {
            BudgetSpendView e = pSpendView.budgetSpendView;
            if (budgetPeriod.period > 0){
            	e.setCurrentBudgetYear(budgetPeriod.year);
            	e.setCurrentBudgetPeriod(budgetPeriod.period);
            	e.setCurrentBudgetPeriodStart(budgetPeriod.start);
            	e.setCurrentBudgetPeriodEnd(budgetPeriod.end);
            }

            if ( null == e.getAmountSpent() ) {
                e.setAmountSpent(new BigDecimal(0));
            }
            mSpent = mSpent.add(e.getAmountSpent());
            if ( null == e.getAmountAllocated() ) {
                e.setAmountAllocated(new BigDecimal(0));
            }
            mAllocated = mAllocated.add(e.getAmountAllocated());

            if (isForThisPeriod(e.getBudgetYear(),
                    e.getCurrentBudgetYear(),
                    e.getBudgetPeriod(),
                    e.getCurrentBudgetPeriod())) {
                mYTDAllocated = mYTDAllocated.add(e.getAmountAllocated());
                mYTDSpent = mYTDSpent.add(e.getAmountSpent());
            }

            //Integer ccid = new Integer(e.getCostCenterId());
            String key = e.getCostCenterName() + "::" + pSpendView.accountId;
            BudgetPerCostCenterSumView lb = null;

            if (mSumEachCostCenter.containsKey(key)) {
                lb = (BudgetPerCostCenterSumView)mSumEachCostCenter.get(key);
            }

            if (lb == null) {
                lb = new BudgetPerCostCenterSumView();
                lb.costCenterId = e.getCostCenterId();
                lb.costCenterName = e.getCostCenterName();
                lb.accountId = pSpendView.accountId;
                mSumEachCostCenter.put(key, lb);
            }

            lb.annualAmountSpent=sumAmt(lb.annualAmountSpent,e.getAmountSpent());
            lb.annualAmountAllocated=sumAmt(lb.annualAmountAllocated,e.getAmountAllocated());
            lb.annualDifference=lb.annualAmountAllocated.subtract(lb.annualAmountSpent);
            lb.budgetYear = e.getCurrentBudgetYear();
        	lb.budgetPeriodStart = e.getCurrentBudgetPeriodStart();
        	lb.budgetPeriodEnd = e.getCurrentBudgetPeriodEnd();

            if (isForThisPeriod(e.getBudgetYear(),
                    e.getCurrentBudgetYear(),
                    e.getBudgetPeriod(),
                    e.getCurrentBudgetPeriod())) {
            	lb.ytdAmountSpent = sumAmt(lb.ytdAmountSpent,e.getAmountSpent());
                lb.ytdAmountAllocated = sumAmt(lb.ytdAmountAllocated,e.getAmountAllocated());
                lb.ytdDifference = lb.ytdAmountAllocated.subtract(lb.ytdAmountSpent);
            }

            return;
        }

        public String toString() {

            return " total of " + mSumEachCostCenter.size() + " entries ";
        }
    }



    public class TotalsCompare implements Comparator{
        boolean multiAccount;
        Map accountMap;
        TotalsCompare(boolean multiAccount, Map accountMap) {
          if(accountMap == null){
            accountMap = new HashMap();
          }
            this.multiAccount = multiAccount;
            this.accountMap = accountMap;
        }

      public int compare(Object o1, Object o2)
      {
        if(o1 == null || o2 == null){
          //should not happen
          return 0;
        }
                BudgetPerCostCenterSumView sv1 = (BudgetPerCostCenterSumView)o1;
                BudgetPerCostCenterSumView sv2 = (BudgetPerCostCenterSumView)o2;
                if(multiAccount){
                    int id1 = sv1.accountId;
                    int id2 = sv2.accountId;
                    if(id1 != id2){
                        String acctN1 = (String) accountMap.get(new Integer(sv1.accountId));
                        String acctN2 = (String) accountMap.get(new Integer(sv2.accountId));
                        if(acctN1 == null){acctN1 = "";}
                        if(acctN2 == null){acctN2 = "";}
                        return acctN1.compareTo(acctN2);

                    }
                }
                String cc1 = sv1.costCenterName;
                String cc2 = sv2.costCenterName;
                if(cc1 == null){cc1 = "";}
                if(cc2 == null){cc2 = "";}
    return cc1.compareTo(cc2);
      }
  };

    protected String mkPeriodString(String pStrIn) {
  if ( null == pStrIn ) return pStrIn;

  // This is the default format, leave the string alone.
  if (mMonthFirst) return pStrIn;

  if (pStrIn.indexOf("/") == -1 ) return pStrIn;

  String [] p = pStrIn.split("/");
  if ( p.length > 1 ) {
      // Flip the date to be dd/mm
      return p[1]+"/"+p[0];
  }
  return pStrIn;
    }

    class BudgetPeriod{
    	int year = 0;
    	int period = 0;
    	String start = null;
    	String end = null;

    	public BudgetPeriod(){}
    	public BudgetPeriod(int year, int period, String periodStart, String periodEnd){
    		this.year = year;
    		this.period = period;
    		this.start = periodStart;
    		this.end = periodEnd;
    	}
    }

    private class BudgetSqlResultRow {

        private int mBudgetYear;
        private int mBudgetPeriod;
        private int mAccountId;
        private int mCostCenterId;
        private String mCostCenterName;
        private String mCostCenterType;
        private String mSiteShortDesc;
        private double mTotalAmountSpent;
        private String mPeriodStartDate;
        private String mPeriodEndDate;
        private String mCity;
        private String mStateCode;
        private String mPostalCode;
        private String mBscName;
        private double mAmountAllocated;
        private int mSiteId;
        private String mBusEntityStatusCd;
        private String mExpDate;
        private String mSRef;
        private String mGsf;
        private String mRankIndex;
        private String mEntryType;

        public int getBudgetYesr() {
            return mBudgetYear;
        }

        public void setBudgetYear(int pBudgetYear) {
            this.mBudgetYear = pBudgetYear;
        }

        public int getBudgetPeriod() {
            return mBudgetPeriod;
        }

        public void setBudgetPeriod(int pBudgetPeriod) {
            this.mBudgetPeriod = pBudgetPeriod;
        }

        public int getCostCenterId() {
            return mCostCenterId;
        }

        public void setCostCenterId(int pCostCenterId) {
            this.mCostCenterId = pCostCenterId;
        }

        public int getAccountId() {
            return mAccountId;
        }

        public void setAccountId(int pAccountId) {
            this.mAccountId = pAccountId;
        }

        public String getCostCenterName() {
            return mCostCenterName;
        }

        public void setCostCenterName(String pCostCenterName) {
            this.mCostCenterName = pCostCenterName;
        }

        public String getSiteShortDesc() {
            return mSiteShortDesc;
        }

        public void setSiteShortDesc(String pSiteShortDesc) {
            this.mSiteShortDesc = pSiteShortDesc;
        }

        public double geTotalAmountSpent() {
            return mTotalAmountSpent;
        }

        public void setTotalAmountSpent(double pTotalAmountSpent) {
            this.mTotalAmountSpent = pTotalAmountSpent;
        }

        public String getPeriodStartDate() {
            return mPeriodStartDate;
        }

        public void setPeriodStartDate(String pPeriodStartDate) {
            this.mPeriodStartDate = pPeriodStartDate;
        }

        public String getPeriodEndDate() {
            return mPeriodEndDate;
        }

        public void setPeriodEndDate(String pPeriodEndDate) {
            this.mPeriodEndDate = pPeriodEndDate;
        }

        public String getCity() {
            return mCity;
        }

        public void setCity(String pCity) {
            this.mCity = pCity;
        }

        public String getStateCode() {
            return mStateCode;
        }

        public void setStateCode(String pStateCode) {
            this.mStateCode = pStateCode;
        }

        public String getPostalCode() {
            return mPostalCode;
        }

        public void setPostalCode(String pPostalCode) {
            this.mPostalCode = pPostalCode;
        }

        public String getBscName() {
            return mBscName;
        }

        public void setBscName(String pBscName) {
            this.mBscName = pBscName;
        }

        public double getAmountAllocated() {
            return mAmountAllocated;
        }

        public void setAmountAllocated(double pAmountAllocated) {
            this.mAmountAllocated = pAmountAllocated;
        }

        public int getSiteId() {
            return mSiteId;
        }

        public void setSiteId(int pSiteId) {
            this.mSiteId = pSiteId;
        }

        public String getBusEntityStatusCd() {
            return mBusEntityStatusCd;
        }

        public void setBusEntityStatusCd(String pBusEntityStatusCd) {
            this.mBusEntityStatusCd = pBusEntityStatusCd;
        }

        public String getExpDate() {
            return mExpDate;
        }

        public void setExpDate(String pExpDate) {
            this.mExpDate = pExpDate;
        }

        public String getSRef() {
            return mSRef;
        }

        public void setSRef(String pSRef) {
            this.mSRef = pSRef;
        }

        public String getGsf() {
            return mGsf;
        }

        public void setGsf(String pGsf) {
            this.mGsf = pGsf;
        }

        public String getRankIndex() {
            return mRankIndex;
        }

        public void setRankIndex(String pRankIndex) {
            this.mRankIndex = pRankIndex;
        }

        public String getCostCenterType() {
            return mCostCenterType;
        }

        public void setCostCenterType(String pCostCenterType) {
            this.mCostCenterType = pCostCenterType;
        }

        public void setEntryType(String pEntryType) {
            this.mEntryType = pEntryType;
        }

        public String getEntryType() {
            return mEntryType;
        }
    }

}
