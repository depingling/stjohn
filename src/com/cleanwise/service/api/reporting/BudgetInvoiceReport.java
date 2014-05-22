package com.cleanwise.service.api.reporting;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.InvoiceDistDataAccess;
import com.cleanwise.service.api.dao.JoinDataAccess;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.UserAssocDataAccess;
import com.cleanwise.service.api.util.BudgetUtil;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.wrapper.BudgetViewWrapper;

public class BudgetInvoiceReport  implements GenericReport{
    protected static final int SITE_MODE = 0;
    protected static final int ACCOUNT_MODE = 1;
    private static final BigDecimal ZERO = new BigDecimal(0.00);
    
    /**
     * Method to be overidden to detemine how this report runs
     */
    protected int getMode(){
        return SITE_MODE;
    }
    
	Connection mCon;
    int budYear;
    int budPeriod;
    HashMap distCache = new HashMap();
    HashMap accountFCCache = new HashMap();
    ArrayList tableData = new ArrayList();
    
	/**
     *Should return a populated GenericReportResultView object.  At a minimum the header should
     *be set so an empty report may be generated to the user.
     */
    public GenericReportResultView process(ConnectionContainer pCons,GenericReportData pReportData,Map pParams)
    throws Exception{
    	mCon = pCons.getDefaultConnection();
    	String siteS = (String)ReportingUtils.getParam(pParams,"SITE");
    	String budYearS = (String)ReportingUtils.getParam(pParams,"BUDGET_YEAR");
    	String budPeriodS = (String)ReportingUtils.getParam(pParams,"BUDGET_PERIOD");
        String customerS = (String)ReportingUtils.getParam(pParams,"CUSTOMER");
    	
    	
    	int siteParam = 0;
    	int customer = 0;
        if(getMode() == SITE_MODE){
        	try{
                siteParam = Integer.parseInt(siteS);
        	}catch(Exception e){
        		String errorMess = "^clw^SITE must be an integer value^clw^";
                throw new Exception(errorMess);
        	}
        }else if(getMode() == ACCOUNT_MODE){
            //nothing to do...program will grab all sites if siteParam == 0
        }else{
            throw new Exception("Unknown mode: "+getMode());
        }
    	try{
    		budYear = Integer.parseInt(budYearS);
    	}catch(Exception e){
    		String errorMess = "^clw^BUDGET_YEAR must be an integer value^clw^";
            throw new Exception(errorMess);
    	}
    	try{
    		budPeriod = Integer.parseInt(budPeriodS);
    	}catch(Exception e){
    		String errorMess = "^clw^BUDGET_PERIOD must be an integer value^clw^";
            throw new Exception(errorMess);
    	}
        if(getMode() == ACCOUNT_MODE){
            try{
                customer = Integer.parseInt(customerS);
            }catch(Exception e){
                String errorMess = "^clw^CUSTOMER must be an integer value^clw^";
                throw new Exception(errorMess);
            }
        }
        IdVector siteList = null;
        if(siteParam > 0){
            siteList = new IdVector();
            siteList.add(new Integer(siteParam));
        }else{
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(UserAssocDataAccess.USER_ID, customer);
            crit.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.SITE);
            siteList = UserAssocDataAccess.selectIdOnly(mCon,UserAssocDataAccess.BUS_ENTITY_ID,crit);
        }
        Iterator it = siteList.iterator();
        while(it.hasNext()){
            Integer site = (Integer) it.next();
            generateRow(site.intValue());
        }
    	
    	
    	GenericReportResultView results = GenericReportResultView.createValue();
    	results.setTable(tableData);
    	results.setColumnCount(getReportHeader().size());
    	results.setHeader(getReportHeader());
    	results.setName("Budget Invoice");
    	return results;
    }
    
    private BigDecimal nvl(BigDecimal val){
        if(val == null){
            return ZERO;
        }
        return val;
    }
    
    private void generateRow(int site) throws Exception{
        int account = BusEntityDAO.getAccountForSite(mCon,site);
        
        BudgetUtil bu = new BudgetUtil(mCon);
        BusEntityDAO lBDAO = new BusEntityDAO();
        
        //xxx should be cached
        Integer accountI = new Integer(account);
        FiscalCalenderView fiscCal;
        if(accountFCCache.containsKey(accountI)){
            fiscCal = (FiscalCalenderView) accountFCCache.get(accountI);
        }else{
            fiscCal = lBDAO.getFiscalCalenderVForYear(mCon,account,budYear);
            accountFCCache.put(accountI,fiscCal);
        }
        if(fiscCal ==null){
            String errorMess = "^clw^no fiscal calender setup for year " + budYear+"^clw^";
            throw new Exception(errorMess);
        }
        FiscalCalendarInfo fiscalInfo = new FiscalCalendarInfo(fiscCal);
        FiscalCalendarInfo.BudgetPeriodInfo period = fiscalInfo.getBudgetPeriod(budPeriod);
        if(period ==null){
            String errorMess = "^clw^no info for the given budget period " + budPeriod+"^clw^";
            throw new Exception(errorMess);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        SiteData siteD = BusEntityDAO.getSiteData(mCon,site);
        siteD.setSpendingInfo(bu.getAllBudgetSpentForSite(account,site,null)); //workaround as this is not initialized in the BusEntityDAO siteData

        String invT = InvoiceDistDataAccess.CLW_INVOICE_DIST;
        String ordT = OrderDataAccess.CLW_ORDER;
        DBCriteria crit = new DBCriteria();
        crit.addJoinTableGreaterOrEqual(invT,InvoiceDistDataAccess.INVOICE_DATE,period.getStartDate());
        crit.addJoinTableLessOrEqual(invT,InvoiceDistDataAccess.INVOICE_DATE,period.getEndDate());
        crit.addJoinCondition(invT,InvoiceDistDataAccess.ORDER_ID,ordT,OrderDataAccess.ORDER_ID);
        crit.addJoinTableEqualTo(ordT,OrderDataAccess.SITE_ID,site);
        
        crit.addDataAccessForJoin(new OrderDataAccess());
        crit.addDataAccessForJoin(new InvoiceDistDataAccess());
        List twoD = JoinDataAccess.select(mCon,crit);

        BigDecimal spent = null;

        if(twoD != null){
            Iterator it = twoD.iterator();
            boolean foundInvoices = false;
            while(it.hasNext()){
                foundInvoices = true;
                ArrayList tableDataRow = new ArrayList();
                List row = (List) it.next();
                //OrderData ord = (OrderData) row.get(0);
                InvoiceDistData inv = (InvoiceDistData) row.get(1);
                tableDataRow.add(siteD.getBusEntity().getShortDesc());
                tableDataRow.add(siteD.getSiteAddress().getCity());
                tableDataRow.add(siteD.getSiteAddress().getStateProvinceCd());
                tableDataRow.add(inv.getErpPoNum());
                tableDataRow.add(inv.getInvoiceNum());
                tableDataRow.add(inv.getInvoiceDate());
                Integer key = new Integer(inv.getBusEntityId());
                BusEntityData dist = null;
                if(distCache.containsKey(key)){
                    dist = (BusEntityData) distCache.get(key);
                }else{
                    if(inv.getBusEntityId() > 0){
                        try{
                            dist = BusEntityDataAccess.select(mCon,inv.getBusEntityId());
                        }catch(Exception e){}
                        distCache.put(key,dist);
                    }
                }
                String distName;
                if(dist != null){
                    distName = dist.getShortDesc();
                }else{
                    distName = "";
                }
                tableDataRow.add(distName);//vendor
                tableDataRow.add(nvl(inv.getSubTotal()));
                tableDataRow.add(nvl(inv.getDiscounts()));
                tableDataRow.add(nvl(inv.getFreight()));
                tableDataRow.add(nvl(inv.getMiscCharges()));
                tableDataRow.add(nvl(inv.getSalesTax()));
                //xxx for now assume all this is budgeted for.  Eventually this should all be
                //done in the site ledger table and it is hoped that by the time this report
                //needs to be revised it can just use that and not duplicate the logic here
                BigDecimal total = Utility.addAmt(inv.getSubTotal(),inv.getFreight());
                Utility.addAmt(total,inv.getMiscCharges());
                Utility.addAmt(total,inv.getSalesTax());
                tableDataRow.add(nvl(total)); //total
                tableDataRow.add(null); //budget allocate
                tableDataRow.add(null); //budget varience
                tableData.add(tableDataRow);
                
                //calc spent
                //XXX should look at cost centers, again hope that the 
                //ledger table can be used long term as there may be items 
                //that have no cost centers
                spent = Utility.addAmt(spent,inv.getSubTotal());
                if(siteD.isAllocateFreightToBudget()){
                    spent = Utility.addAmt(spent,inv.getMiscCharges());
                    spent = Utility.addAmt(spent,inv.getFreight());
                }
                //just add in sales tax for now, again sales tax
                //may be budgetd in different ways.
                spent = Utility.addAmt(spent,inv.getSalesTax());
            }
            if(!foundInvoices){
                //add dummy row to ropert so user knows what they are looking at.
                ArrayList tableDataRow = new ArrayList();
                tableDataRow.add(siteD.getBusEntity().getShortDesc());//site name
                tableDataRow.add(siteD.getSiteAddress().getCity());//site city
                tableDataRow.add(siteD.getSiteAddress().getStateProvinceCd());//site State
                tableDataRow.add(null); //po
                tableDataRow.add(null); //inv
                tableDataRow.add(null); //inv date
                tableDataRow.add(null); //vendor
                tableDataRow.add(ZERO); //subtotal
                tableDataRow.add(ZERO); //discounts
                tableDataRow.add(ZERO); //frt
                tableDataRow.add(ZERO); //misc
                tableDataRow.add(ZERO); //tax
                tableDataRow.add(ZERO); //total
                tableDataRow.add(null); //budget allocate
                tableDataRow.add(null); //budget varience
                tableData.add(tableDataRow);
            }
            ArrayList tableDataRow = new ArrayList();
            tableDataRow.add(null); //site name
            tableDataRow.add(null); //site city
            tableDataRow.add(null); //site State
            tableDataRow.add(null); //po
            tableDataRow.add(null); //inv
            tableDataRow.add(null); //inv date
            tableDataRow.add(null); //vendor
            tableDataRow.add(null); //subtotal
            tableDataRow.add(null); //discounts
            tableDataRow.add(null); //frt
            tableDataRow.add(null); //misc
            tableDataRow.add(null); //tax
            
            
            BigDecimal allocated = null;
            BudgetViewVector bvv = siteD.getBudgets();
            if(bvv != null){
                Iterator budit = bvv.iterator();
                while(budit.hasNext()){
                    BudgetView bv = (BudgetView) budit.next();
                    BudgetViewWrapper bvw = new BudgetViewWrapper(bv);
                    BigDecimal amt = bvw.getAmount(budPeriod);
                    allocated = Utility.addAmt(allocated,amt);
                }
            }
            if(allocated == null){
                allocated = new BigDecimal(0.00);
            }
            if(spent == null){
                spent = new BigDecimal(0.00);
            }
            tableDataRow.add(nvl(spent)); //total
            tableDataRow.add(nvl(allocated)); //budget allocate
            tableDataRow.add(nvl(spent.subtract(allocated))); //budget varience
            tableData.add(tableDataRow);
            
        }
    }
    
    private GenericReportColumnViewVector getReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","City",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","State",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Oubound PO Number",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Invoice",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Invoice Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Vendor",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Sub Total$",2,20,"NUMBER","*",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Discounts$",2,20,"NUMBER","*",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Freight$",2,20,"NUMBER","*",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Misc$",2,20,"NUMBER","*",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Tax$",2,20,"NUMBER","*",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total$",2,20,"NUMBER","*",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Budget Allocated$",2,20,"NUMBER","*",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Budget Varience$",2,20,"NUMBER","*",false));
        return header;
    }
}
