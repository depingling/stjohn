package com.cleanwise.service.api.pipeline;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.SiteLedgerDataAccess;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.util.PipelineCalculationOperations;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.FiscalCalenderData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderDataVector;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.SiteLedgerData;
import com.cleanwise.service.api.value.SiteLedgerDataVector;

/**
 * Title:        ReCalculateOrdersPipeline
 * Description: Class contains methods  which recalculates orders
 * Changes will be probably made in the table : CLW_SITELEDGER
 * Purpose:      Recalculate of orders
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date:         20.11.2006
 * Time:         14:54:52
 * @author       Alexander Chickin, TrinitySoft, Inc.
 */

public class ReCalculateOrdersPipeline implements MultiOrderPipeline {
    private static final Logger logger = Logger.getLogger(ReCalculateOrdersPipeline.class);

    public static final String PAIRS_ACCOUNT_ID_AND_ORDER_DATE = "PAIRS_ACCOUNT_ID_AND_ORDER_DATE";
    public static final String ACCOUNT_IDS = "ACCOUNT_IDS";
    public static final String ORDER_IDS ="ORDER_IDS" ;
    public static final String COST_CENTER_INFO ="COST_CENTER_INFO";
    public static final String FISCAL_CALENDAR_INFO = "FISCAL_CALENDAR_INFO";
    public static final String  SITE_LEDGER_ENTERIES = "SITE_LEDGER_ENTERIES";


    public static final String IN_USE="IN_USE";
    public static final String  NOT_IN_USE="NOT_IN_USE";
    public static final String NEW="NEW";
    public static final String UNKNOWN="UNKNOWN";

    private static final int DB_ABORT_LIMIT = 1;
    private static final int ABORT_LIMIT = 1;

    static final Comparator SITE_LEDGER_ID_COMPARE = new Comparator() {
	    public int compare(Object o1, Object o2)
	    {
		int val1 =
		    ((SiteLedgerData)o1).getSiteLedgerId();
		int  val2 =
		    ((SiteLedgerData)o2).getSiteLedgerId();

                return val1- val2;
	    }
	};

    private TimedCache cache=null;
    private HashMap hmSiteLedgerDV=null;
    private PrivateLog log=null;
    private int numException=0;
    private boolean done=false;



    public void process(MultiOrderPipelineBaton pMBaton, MultiOrderPipelineActor pActor,
                        Connection pCon, APIAccess pFactory) throws PipelineException {
        long startAMT=System.currentTimeMillis();
       try {

        long startMT =startAMT  ;
        
        log=new PrivateLog();
        setExecuteLogParametr(log,pMBaton.getParametrs());
        log.printInfoAboutSettings();
        log.logln("             ***************************************************************************");
        log.logln();
        log.logln("                   <ReCalculateMultiOrder> started at  "+ Utility.getFormatTime(startMT,0));
        log.logln();
        log.logln("             ***************************************************************************");

        log.logln("Init variables...",PrivateLog.MESSAGE);
        cache=new TimedCache();
        hmSiteLedgerDV=new HashMap();
        log.logln("Init variables OK!",PrivateLog.MESSAGE);


        log.logln("Load Cache...",PrivateLog.MESSAGE);
        loadCache(pMBaton.getOrderDataVector(), pCon,cache);
        log.logln("Load Cache.OK!.process time at : "+
                (Utility.getFormatTime(-startMT+System.currentTimeMillis(),4))+" ms",PrivateLog.MESSAGE);

        log.logln("Recalculate orders ",PrivateLog.MESSAGE);
        startMT=System.currentTimeMillis();
        recalculate(pMBaton.getOrderDataVector(), pCon,pMBaton.getUserName());
        log.logln("Recalculate orders.OK!process time at : "+
                (Utility.getFormatTime(-startMT+System.currentTimeMillis(),4))+" ms",PrivateLog.MESSAGE);

        log.logln("Excecute Data Base Operation",PrivateLog.MESSAGE);
        startMT=System.currentTimeMillis();
        String dbOperation=(String)pMBaton.getParametrs().get("dbOperation");
        boolean dbOpFl=false;
           if(Utility.isSet(dbOperation))
        {
         if(dbOperation.equals("Y"))
            dbOpFl=true;
         else  if(dbOperation.equals("N"))
            dbOpFl=false;
        }
        executeDBOperation(pCon,hmSiteLedgerDV,dbOpFl);

        log.logln("Execute Data Base Operation.OK!process time at :"+
                (Utility.getFormatTime(-startMT+System.currentTimeMillis(),4))+" ms",PrivateLog.MESSAGE);
        log.logln("updateLastExecuteDateRange");
        updateLastExecuteDateRange(pFactory,pMBaton.getParametrs(),pMBaton.getUserName());
        log.logln("updateLastExecuteDateRange.Ok!");
        pMBaton.setWhatNext(MultiOrderPipelineBaton.GO_NEXT);
        done=true;
        } catch (Exception e) {
        log.logExc("G",(++numException),e.getMessage(),"<ReCalculateOrdersPipeline.process>");
        pMBaton.setWhatNext(MultiOrderPipelineBaton.STOP);
        throw new PipelineException(e.getMessage());
       }
        finally{
           long endAMT=System.currentTimeMillis();
           log.logln("             ***************************************************************************");
           log.logln();
           log.logln("                   <ReCalculateMultiOrder> endeded at : "+ Utility.getFormatTime(endAMT,0));
           log.logln("                                   processed time at :"+ Utility.getFormatTime(endAMT-startAMT,4));
           log.logln();
           log.logln("             ***************************************************************************");
           log.printBuffer();
       }

    }

    private void updateLastExecuteDateRange(APIAccess pFactory, Hashtable parametrs,String pName) throws Exception {

       SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
       Date bDate= ((Date) parametrs.get("bDate"));
       Date eDate= ((Date) parametrs.get("eDate"));
       String begDate=sdf.format(bDate);
       String endDate=sdf.format(eDate);
       PropertyService propertyServiceBean;
        try {
            propertyServiceBean = pFactory.getPropertyServiceAPI();
            propertyServiceBean.setProperty(pName,endDate);
        } catch (Exception e) {
            String msg = "EJB problem.Can't update execute last date.";
            throw new Exception(msg+e.getMessage());
        }


    }

    private void setExecuteLogParametr(ReCalculateOrdersPipeline.PrivateLog log, Hashtable parametrs) {

        String logOn=(String)parametrs.get("logOn");
        String logLevel=(String)parametrs.get("logLevel");
        String logMode= (String)parametrs.get("logMode");
        String logOut=(String)parametrs.get("logOut");
        String logFileName=(String)parametrs.get("logFileName");
        String jbossLog=(String)parametrs.get("jbossLog");
        if(Utility.isSet(logOn))
        {
            if(logOn.equals("Y"))
                log.setLogOn(true);
            else  if(logOn.equals("N"))
                log.logOn = false;
        }

        if(Utility.isSet(logLevel))
        {
            try {
                log.setLevel(Integer.parseInt(logLevel));
            } catch (NumberFormatException e) {
                logger.info("Not a known command <logLevel>.Using default value '1'");
            }
        }

        if(Utility.isSet(logMode))
        {
            if(logMode.equals(PrivateLog.AFTER_PROCESS)
                    ||logMode.equals(PrivateLog.IN_PROCESS))
                log.setMode(logMode);
        }

        if(Utility.isSet(logOut))
        {
            if(logOut.indexOf(PrivateLog.CONSOLE_OUT)!=-1
                    ||logOut.indexOf(PrivateLog.FILE_OUT)!=-1)
                log.setReceiverOut (logOut);
        }

        if(Utility.isSet((logFileName)))
        {
            log.setFileName(logFileName);
        }
        else
        {
            logger.info("Not set  <logFileName>.log off");
            log.setLogOn(false);
        }

  }

    private void loadCache(OrderDataVector orderDataVector, Connection pCon, TimedCache cache) throws Exception {

        Iterator it= orderDataVector.iterator();
        PairViewVector pairsAccountIdAndOrderDate=new PairViewVector();
        IdVector accountIds=new IdVector();
        IdVector orderIds=new IdVector();

        long startMT=System.currentTimeMillis();
        log.logln("Load Pairs,AccountIds,OrderIds",PrivateLog.MESSAGE);

        while(it.hasNext())   {
            OrderData orderD=(OrderData)it.next();
            int orderId = orderD.getOrderId();
            int siteId = orderD.getSiteId();
            int accountId = orderD.getAccountId();
            int storeId = orderD.getStoreId();
            if(siteId>0 && orderId>0 && accountId >0) {
                java.util.Date ordDate = orderD.getRevisedOrderDate();
                if(ordDate == null){
                    ordDate = orderD.getOriginalOrderDate();
                }
                pairsAccountIdAndOrderDate.add(new PairView(new Integer(orderD.getAccountId()),ordDate));
                accountIds.add(new Integer(orderD.getAccountId()));
                orderIds.add(new Integer(orderD.getOrderId()));

            }
        }
        log.logln("Load Pairs,AccountIds,OrderIds.OK!process time at : "
                +(Utility.getFormatTime(-startMT+System.currentTimeMillis(),4))+" ms",PrivateLog.MESSAGE);

        startMT=System.currentTimeMillis();
        log.logln("Load Cost Center Info.",PrivateLog.MESSAGE);
        HashMap hmCostCentrData = PipelineCalculationOperations.loadCostCentersInfo(orderIds, pCon);
        log.logln("Load Cost Center Info.OK!process time at : "+(Utility.getFormatTime(-startMT+System.currentTimeMillis(),4))+" ms",PrivateLog.MESSAGE);

        startMT=System.currentTimeMillis();
        log.logln("Load Fiscal Calendar Info.",PrivateLog.MESSAGE);
        HashMap fiscalCalendarInfo=loadFiscalCalendarInfo(pairsAccountIdAndOrderDate,pCon);
        log.logln("Load Fiscal Calendar Info.OK!process time at : "+(Utility.getFormatTime(-startMT+System.currentTimeMillis(),4))+" ms",PrivateLog.MESSAGE);

        startMT=System.currentTimeMillis();
        log.logln("Load Site Ledger Entries",PrivateLog.MESSAGE);
        HashMap hmSiteLDV=loadSiteLedgerEntries(orderIds,pCon);
        log.logln("Load Site Ledger Entries.OK!process time at : "+(Utility.getFormatTime(-startMT+System.currentTimeMillis(),4))+" ms",PrivateLog.MESSAGE);

        log.logln("Put loaded data in cache.",PrivateLog.MESSAGE);

        cache.put(PAIRS_ACCOUNT_ID_AND_ORDER_DATE,pairsAccountIdAndOrderDate);
        cache.put(ACCOUNT_IDS,accountIds);
        cache.put(ORDER_IDS,orderIds);
        cache.put(COST_CENTER_INFO,hmCostCentrData);
        cache.put(FISCAL_CALENDAR_INFO,fiscalCalendarInfo);
        cache.put(SITE_LEDGER_ENTERIES,hmSiteLDV);


    }


    private HashMap loadFiscalCalendarInfo(PairViewVector pairs, Connection pCon) throws Exception {
        log.logln("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS",PrivateLog.DEBUG);
        int lNumException=0;
        BusEntityDAO bdao = new BusEntityDAO();
        Iterator it= pairs.iterator();
        HashMap hmFiscal=new HashMap();
        for(;it.hasNext();)
        {
            PairView pair=(PairView)it.next();
            log.logln("PAIR(AccountId,OrderDate) : "+pair.toString(),PrivateLog.DEBUG);
            int accountId = ((Integer) pair.getObject1()).intValue();
            java.util.Date ordDate= (java.util.Date) pair.getObject2();
            FiscalCalenderData fiscCal = null;
            boolean duplicateFl=false;
            try {
                if(!hmFiscal.containsKey(pair.toString())){

                    fiscCal = bdao.getFiscalCalender (pCon, accountId, ordDate);
                    log.logln("Result  Fiscal Calendar :"+fiscCal,PrivateLog.DEBUG);
                    duplicateFl=false;

                } else {

                    log.logln("Fiscal Calendar contains in cache.GET NEXT ",PrivateLog.DEBUG);
                    duplicateFl=true;

                }
           if(fiscCal == null){
                log.logln("Fiscal Calendar not found.Creating Fiscal Calendar",PrivateLog.DEBUG);
                fiscCal=FiscalCalenderData.createValue();
                Calendar cal = Calendar.getInstance();
                cal.setTime(ordDate);
                fiscCal.setFiscalYear(cal.get(Calendar.YEAR));
                fiscCal.setFiscalCalenderId(0);
                log.logln("Result  Fiscal Calendar :"+fiscCal,PrivateLog.DEBUG);
            }
            if(!duplicateFl)
               hmFiscal.put(pair.toString(), fiscCal);

            }  catch (Exception e) {
                if(++lNumException>ABORT_LIMIT)
                throw new Exception("Many Exception in method");
                log.logExc("L",(lNumException),e.getMessage(),"<ReCalculateOrdersPipeline.loadFiscalCalendarInfo>");
   }
        }
        log.logln("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF",PrivateLog.DEBUG);


        return hmFiscal;
    }



    public PairViewVector getPairsAccountIdAndOrderDate(OrderDataVector orderDataVector)
    {

        Iterator it= orderDataVector.iterator();
        PairViewVector pairs=new PairViewVector();
        while(it.hasNext())   {
            OrderData orderD=(OrderData)it.next();
            int orderId = orderD.getOrderId();
            int siteId = orderD.getSiteId();
            int accountId = orderD.getAccountId();
            int storeId = orderD.getStoreId();
            if(siteId>0 && orderId>0 && accountId >0) {
                java.util.Date ordDate = orderD.getRevisedOrderDate();
                if(ordDate == null){
                    ordDate = orderD.getOriginalOrderDate();
                }
                pairs.add(new PairView(new Integer(orderD.getAccountId()),ordDate));
            }
        }
        return pairs;
    }


    private HashMap loadSiteLedgerEntries(IdVector orderIds, Connection pCon) throws Exception {


        int lNumException=0;
        log.logln("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS",PrivateLog.DEBUG);

        if(orderIds == null){
            return null;
        }
        HashMap hmSiteLDV=new HashMap();
        SiteLedgerDataVector result = new SiteLedgerDataVector();
        String queryOrderIds;
        int i=0;
        int pi=0;
        log.logln("OrdetIds size:"+orderIds.size(),PrivateLog.DEBUG);
        for(int j=0;j<=(int)(orderIds.size()/1000);j++)
        {
        pi=0;
        queryOrderIds=new String();
        IdVector pIds=new IdVector();
        log.logln("num query : "+j,PrivateLog.DEBUG);
       for(int idx=0;idx <(j==(int)(orderIds.size()/1000)?(orderIds.size()%1000):1000);idx++)
        {
         pIds.add(orderIds.get((j*1000)+idx));
        }
        log.logln("query size :"+pIds.size(),PrivateLog.DEBUG);
        Iterator it=pIds.iterator();
        for(i=0;it.hasNext();i++)
        {
            int id = ((Integer) it.next()).intValue();
            if(id>0){
                if(i!=0) queryOrderIds+=",";
                queryOrderIds+=String.valueOf(id);
            }
        }

        StringBuffer sqlBuf = new StringBuffer("SELECT COST_CENTER_ID,FISCAL_CALENDER_ID,ORDER_ID," +
                "SITE_ID,SITE_LEDGER_ID,ADD_BY,ADD_DATE,AMOUNT,BUDGET_PERIOD,BUDGET_YEAR,COMMENTS," +
                "ENTRY_TYPE_CD,MOD_BY,MOD_DATE FROM CLW_SITE_LEDGER " +
                "WHERE ORDER_ID IN ( "+queryOrderIds+" ) AND ENTRY_TYPE_CD='ORDER' " );

        log.logln("Get SiteLedgerDataVector Info : " +sqlBuf,PrivateLog.DEBUG);
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String sql = sqlBuf.toString();
            stmt = pCon.createStatement();
            rs=stmt.executeQuery(sql);
            SiteLedgerData x=null;
            while (rs.next()) {
                x=SiteLedgerData.createValue();

                x.setCostCenterId(rs.getInt(1));
                x.setFiscalCalenderId(rs.getInt(2));
                x.setOrderId(rs.getInt(3));
                x.setSiteId(rs.getInt(4));
                x.setSiteLedgerId(rs.getInt(5));
                x.setAddBy(rs.getString(6));
                x.setAddDate(rs.getTimestamp(7));
                x.setAmount(rs.getBigDecimal(8));
                x.setBudgetPeriod(rs.getInt(9));
                x.setBudgetYear(rs.getInt(10));
                x.setComments(rs.getString(11));
                x.setEntryTypeCd(rs.getString(12));
                x.setModBy(rs.getString(13));
                x.setModDate(rs.getTimestamp(14));
                if(hmSiteLDV.containsKey(String.valueOf(x.getOrderId())))
                {

                    SiteLedgerDataVector resultDV = (SiteLedgerDataVector)hmSiteLDV.get(String.valueOf(x.getOrderId()));
                    resultDV.add(x);

                    hmSiteLDV.put(String.valueOf(x.getOrderId()),resultDV);
                }
                else{

                    SiteLedgerDataVector resultDV = new SiteLedgerDataVector();
                    resultDV.add(x);
                    hmSiteLDV.put(String.valueOf(x.getOrderId()),resultDV);

                }
                pi++;
            }
            log.logln("processed :"+pi,PrivateLog.DEBUG);

        }  catch (Exception e) {

            log.logExc("L",(lNumException),e.getMessage(),"<ReCalculateOrdersPipeline.loadSiteLedgerEntries>");
            if(++lNumException>=((int)(orderIds.size()/1000)))  throw new Exception("many data can't be processed");


        }
        }
        log.logln("Result loaded SiteLedgerDataVector : "+hmSiteLDV,PrivateLog.DEBUG);
        log.logln("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF",PrivateLog.DEBUG);

        return hmSiteLDV;
    }


    private void recalculate(OrderDataVector orderDV, Connection pCon, String pUserName) throws Exception {

        log.logln("                                                                               ");
        log.logln("                                      START RECALCULATE.ORDERS COUNT:"+orderDV.size()+"                   ");
        log.logln("                                                                               ");
        long startAMT=System.currentTimeMillis();
        int index=0;
        Hashtable exceptions=new Hashtable();
        long  sT=0;
        long  eT=0;
        int passIdx=0;
        int exceptionIdx=0;
        int allIdx=0;
        int cancelIdx=0;

        String comment="";
        String status="UNKNOWN";
        OrderData orderData=null;

        for(Iterator it=orderDV.iterator();it.hasNext();index++)
        {   try {
            sT=System.currentTimeMillis();

            status="UNKNOWN";
            comment="N";

            orderData=(OrderData)it.next();

            HashMap ccSum = new HashMap();

            Date ordDate = orderData.getRevisedOrderDate();
            if(ordDate == null) ordDate = orderData.getOriginalOrderDate();

            HashMap hmCostCenterData=(HashMap)cache.get(COST_CENTER_INFO);
            ArrayList ccParams = (ArrayList)hmCostCenterData.get(String.valueOf(orderData.getOrderId()));
            if(ccParams!=null){
            	ccSum = PipelineCalculationOperations.recalculteCostCenterInfo(pCon,orderData,ccParams,true) ;
                FiscalCalenderData fiscalCD= (FiscalCalenderData) ((HashMap)(cache.get(FISCAL_CALENDAR_INFO)))
                        .get(new PairView(String.valueOf(orderData.getAccountId()),ordDate).toString());
                log.logln(new PairView(String.valueOf(orderData.getAccountId()),ordDate).toString(),PrivateLog.DEBUG);
                SiteLedgerDataVector existingLedgers = (SiteLedgerDataVector) ((HashMap)cache.get(SITE_LEDGER_ENTERIES))
                        .get( String.valueOf(orderData.getOrderId()));
                SiteLedgerDataVector rD=getRecalculateDataForOrder(pCon,orderData,existingLedgers,fiscalCD,ccSum,pUserName);
                // updateRecalculateData(pCon,rD);
                // updateBeingApproved( pCon, orderData,  fiscalCD);
                status="PROCESSED";
                passIdx++;
            }

            else{
                comment="No cost center params";
                status="CANCELED";
                cancelIdx++;
            }

        } catch (Exception e) {
            exceptionIdx++;
            status="EXCEPTION";
            exceptions.put(new Integer(index),e.getMessage());
            if(exceptionIdx>ABORT_LIMIT)
                throw new Exception("Many Exception in method");
            log.logExc("L",(exceptionIdx),e.getMessage(),"<ReCalculateOrdersPipeline.recalculate>");
        } finally {
            allIdx++;
            eT=System.currentTimeMillis();
            int orderId=-1;
            int accountId=-1;
            int siteId=-1;
            if(orderData!=null)
            {
                orderId= orderData.getOrderId();
                accountId=orderData.getAccountId();
                siteId=orderData.getSiteId();
            }
            String strExcception= (String) (exceptions.get(new Integer(index))==null?"N":exceptions.get(new Integer(index)));

            log.logln("xxx LOOP : "+index+   " xxx START : "+Utility.getFormatTime(sT,2)+" xxx END : "+Utility.getFormatTime(eT,2)+      " xxx PROCESS : "+Utility.getFormatTime(eT-sT,4)+" ms xxx",PrivateLog.REPORT);
            log.logln("xxx ORDERID : "+orderId+ " xxx ACCOUNTID : "+accountId+     " xxx SITEID  : "+siteId + " xxx",PrivateLog.REPORT);
            log.logln("xxx STATUS : "+ status +" xxx EXCEPTION : "+strExcception+ " xxx COMMENT : "+comment +" xxx",PrivateLog.REPORT);
            log.logln("",PrivateLog.REPORT);

        }
        }

        log.logln(" ");
        log.logln("                                            END RECALCULATE                ");
        log.logln("                                PROCESS  TIME : "+(System.currentTimeMillis()-startAMT)+"    AVG TIME : "+((System.currentTimeMillis()-startAMT)/(long)index) +" ");
        log.logln("                           PROCESSED :"+passIdx+" CANCELED : "+cancelIdx+" EXCEPTION : "+exceptionIdx+" TOTAL : "+(allIdx));
        log.logln();

    }





    public  SiteLedgerDataVector  getRecalculateDataForOrder(Connection pCon, OrderData orderData,
                                                             SiteLedgerDataVector existingLedgers,FiscalCalenderData fiscalCD,
                                                             HashMap ccSum, String pUserName) throws Exception {
        log.logln("Start getRecalculateDataForOrder.",PrivateLog.MESSAGE);
        long startMT=System.currentTimeMillis();
        String code_key;
        BusEntityDAO bd = new BusEntityDAO();
        SiteLedgerDataVector recalculateData=new SiteLedgerDataVector();
        Date ordDate = orderData.getRevisedOrderDate();
        if(ordDate == null) ordDate = orderData.getOriginalOrderDate();
        Iterator it = ccSum.keySet().iterator();
        while(it.hasNext()){
            code_key=UNKNOWN;
            Integer thisCostCenterIdKey = (Integer) it.next();
            int thisCostCenterId = thisCostCenterIdKey.intValue();
            BigDecimal thisCostCenterSum = (BigDecimal) ccSum.get(thisCostCenterIdKey);
            int budPeriod = bd.getAccountBudgetPeriod(pCon, orderData.getAccountId(), orderData.getSiteId(), ordDate);
            SiteLedgerData le = null;
            if(existingLedgers != null){
               Iterator sit = existingLedgers.iterator();
                while(sit.hasNext()){
                    SiteLedgerData ale = (SiteLedgerData) sit.next();
                    if(ale!=null&&ale.getCostCenterId() == thisCostCenterId){
                      le = ale;  code_key=IN_USE;
                       sit.remove(); //we will set any leftovers to 0

                    }
                }
            }
           if(le == null){
                le = SiteLedgerData.createValue();
                le.setAddBy(pUserName);
                code_key=NEW;
            }
            //reset the budget period if it is not set


            le.setBudgetPeriod(budPeriod);
            le.setFiscalCalenderId(fiscalCD.getFiscalCalenderId());
            le.setBudgetYear(fiscalCD.getFiscalYear());

            le.setOrderId(orderData.getOrderId());
            le.setSiteId(orderData.getSiteId());
            le.setCostCenterId(thisCostCenterId);

            le.setAmount(thisCostCenterSum.setScale(2,BigDecimal.ROUND_HALF_UP));
            le.setEntryTypeCd(RefCodeNames.LEDGER_ENTRY_TYPE_CD.ORDER);
            le.setModBy(pUserName);


    if(hmSiteLedgerDV.containsKey(code_key))
            {
                SiteLedgerDataVector sldv = ((SiteLedgerDataVector) hmSiteLedgerDV.get(code_key));
                sldv.add(le);
                hmSiteLedgerDV.put(code_key,sldv);
            }
            else
            {
                SiteLedgerDataVector sldv=new SiteLedgerDataVector();
                sldv.add(le);
                hmSiteLedgerDV.put(code_key,sldv);
            }
            recalculateData.add(le);

        }  //loop through any leftover ledger entries and set them to be empty

        if(existingLedgers!=null)
        {         it = existingLedgers.iterator();
            code_key=NOT_IN_USE;
            while(it.hasNext()){
                SiteLedgerData le = (SiteLedgerData) it.next();
                le.setAmount(new BigDecimal(0.00));
                if(hmSiteLedgerDV.containsKey(code_key))
                {
                    SiteLedgerDataVector sldv = ((SiteLedgerDataVector) hmSiteLedgerDV.get(code_key));
                    sldv.add(le);
                    hmSiteLedgerDV.put(code_key,sldv);
                }
                else
                {
                    SiteLedgerDataVector sldv=new SiteLedgerDataVector();
                    sldv.add(le);
                    hmSiteLedgerDV.put(code_key,sldv);
                }
                recalculateData.add(le);
            }
        }

        log.logln("RecalculateData.OK!.process time at :"+Utility.getFormatTime(System.currentTimeMillis()-startMT,4),PrivateLog.MESSAGE);
        return    recalculateData;

    }


    public PairViewVector getOrderCCPairIds(SiteLedgerDataVector sldv)
    {
     PairViewVector pairs=new PairViewVector();
     for(int i=0;i<sldv.size();i++)
     pairs.add(new PairView(String.valueOf(((SiteLedgerData)sldv.get(i)).getOrderId()),String.valueOf(((SiteLedgerData)sldv.get(i)).getCostCenterId())));
     return pairs;
    }

    public void printRecalculateCollection(){
    Iterator it= hmSiteLedgerDV.keySet().iterator();
    log.logln("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$",PrivateLog.REPORT);
    log.logln("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ RECALCULATED COLLECTION $$$$$$$$$$$$$$$$$$$$$$$$$$$$",PrivateLog.REPORT);
    log.logln("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$",PrivateLog.REPORT);
    while(it.hasNext())
    {
        String key = (String) it.next();
        SiteLedgerDataVector object = (SiteLedgerDataVector) hmSiteLedgerDV.get(key);
        PairViewVector pairs=null;
          if(object!=null){
        Iterator it2=object.iterator();
        while(it2.hasNext())
        {
        log.logln(key + " : " + it2.next(),PrivateLog.REPORT);
        }
log.logln("llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll",PrivateLog.REPORT);
log.logln("llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll",PrivateLog.REPORT);
log.logln("llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll",PrivateLog.REPORT);
          }
    }
    }

   public void  executeDBOperation(Connection pCon,HashMap recalculateData,boolean on ) throws Exception  {

   long st=System.currentTimeMillis();
   if(on){

   log.logln("                                                                               ");
   log.logln("                                           START DB OPERATIONS                 ");
   log.logln("                                                                               ");


   deleteDBData(pCon,(SiteLedgerDataVector)recalculateData.get(NOT_IN_USE));
   updateDBData(pCon,(SiteLedgerDataVector)recalculateData.get(IN_USE));
   insertDBData(pCon,(SiteLedgerDataVector)recalculateData.get(NEW));

   log.logln(" ");
   log.logln("                                           END DB OPERATIONS                ");
   log.logln("                                          PROCESS  TIME : "+(System.currentTimeMillis()-st));
   log.logln();
   } else
   {
       printRecalculateCollection();
   }
  }

   private void insertDBData(Connection pCon, SiteLedgerDataVector siteLedgerDataVector) throws RemoteException {

        if(siteLedgerDataVector!=null)
        {
         Iterator it=siteLedgerDataVector.iterator();
         int i=0;
         int error=0;
         int pass=0;
         ArrayList errorMessages=new ArrayList();
         SiteLedgerData siteLedgerData=null;
         log.logln("                                            INSERT.SIZE : "+siteLedgerDataVector.size(),PrivateLog.REPORT);
         log.logln("",PrivateLog.REPORT);
         long executeprocessTime=System.currentTimeMillis();
         long avgprocessTime=0;
         long allTime=0;
          while(it.hasNext())
            {
                try {

             siteLedgerData=(SiteLedgerData)it.next();
             SiteLedgerDataAccess.insert(pCon,siteLedgerData,true);
             pass++;

            } catch (Exception e) {
            error++;
            if(error>=DB_ABORT_LIMIT) throw new RemoteException("Many exception in method <ReCalculateOrdersPipeline.insertDBData>");
            log.logExc("DB",error,e.getMessage(),"INSERT DB");
         } finally {
             i++;
             allTime=System.currentTimeMillis()-executeprocessTime;
             avgprocessTime=allTime/i;
            } }

         log.logln("                      @@@@@@@@@@@@@@@@@@@@@@@@ OPERATION : INSERT @@@@@@@@@@@@@@@@@@@@@@",PrivateLog.REPORT);
         log.logln("                      @@@@@@@@@ Pass : "+pass +" @@@@@@@@@ Error : "+error+" @@@@@@@@ All :"+i,PrivateLog.REPORT);
         log.logln("                      @@@@@@@@@ process Time : "+allTime+" ms  @@@@@@@@@ Avg process Time "+avgprocessTime+" ms",PrivateLog.REPORT);
         log.logln("                      @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",PrivateLog.REPORT);
         log.logln("",PrivateLog.REPORT);

        }
    }

   private void updateDBData(Connection pCon, SiteLedgerDataVector siteLedgerDataVector) throws Exception {

        long sT=System.currentTimeMillis();
        log.logln("getOnlyChangeData :",PrivateLog.MESSAGE);
        SiteLedgerDataVector siteLDV=getOnlyChangeData(pCon,siteLedgerDataVector);
        long eT=System.currentTimeMillis();
        int diffDV=siteLedgerDataVector.size()-siteLDV.size();
       if(siteLDV.size()!=0)
       {
           log.logln("Dropped :"+((1-((double)siteLDV.size()/(double)siteLedgerDataVector.size()))*100) +"%",PrivateLog.MESSAGE);
       }
       log.logln("getOnlyChangeData.OK!.processed time at :"+Utility.getFormatTime(eT-sT,4)+"ms",PrivateLog.MESSAGE);

        if(siteLDV!=null)
        {
         Iterator it=siteLDV.iterator();
         int i=0;
         int error=0;
         int pass=0;
         ArrayList errorMessages=new ArrayList();
         SiteLedgerData siteLedgerData=null;

         log.logln("                                            UPDATE.SIZE : "+siteLDV.size(),PrivateLog.REPORT);
         log.logln("",PrivateLog.REPORT);
         long executeprocessTime=System.currentTimeMillis();
         long avgprocessTime=0;
         long allTime=0;
          while(it.hasNext())
            {
                try {

             siteLedgerData=(SiteLedgerData)it.next();
             SiteLedgerDataAccess.update(pCon,siteLedgerData,true);
             pass++;

            } catch (Exception e) {
            error++;
            if(error>=DB_ABORT_LIMIT) throw new RemoteException("Many exception in method <ReCalculateOrdersPipeline.updateDBData>");
            log.logExc("DB",error,e.getMessage(),"UPDATE DB");
            //errorMessages.add(errMess);
         } finally {
             i++;
             allTime=System.currentTimeMillis()-executeprocessTime;
             avgprocessTime=allTime/i;

            }
            }

         log.logln("                      @@@@@@@@@@@@@@@@@@@@@@@@ OPERATION : UPDATE @@@@@@@@@@@@@@@@@@@@@@",PrivateLog.REPORT);
         log.logln("                      @@@@@@@@@ Pass : "+pass +" @@@@@@@@@ Error : "+error+" @@@@@@@@ All :"+i,PrivateLog.REPORT);
         log.logln("                      @@@@@@@@@ Process : "+allTime+" ms  @@@@@@@@@ Avg Process Time : "+avgprocessTime+" ms",PrivateLog.REPORT);
         log.logln("                      @@@@@@@@@ Optimize : " +Utility.getFormatTime(eT-sT,4)+"ms"+" @@@ Drop : "
                   +diffDV+" @@@ Effect : "+Utility.getFormatTime((avgprocessTime*diffDV)-(eT-sT),4)+ " ms",PrivateLog.REPORT);
         log.logln("                      @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",PrivateLog.REPORT);
         log.logln("",PrivateLog.REPORT);

       }
    }

   private SiteLedgerDataVector getOnlyChangeData(Connection pCon,SiteLedgerDataVector siteLedgerDataVector) throws Exception {

       log.logln("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS",PrivateLog.DEBUG);

       // IdVector siteLedgerIds= getSiteLedgerIds(siteLedgerDataVector);
        if(siteLedgerDataVector==null){
            return null;
        }

        SiteLedgerDataVector result = new SiteLedgerDataVector();
        String queryOrderIds;
        int exc=0;
        int i=0;
        int pi=0;
        log.logln("siteLedgerDataVector size:"+siteLedgerDataVector.size(),PrivateLog.DEBUG);
        for(int j=0;j<=(int)(siteLedgerDataVector.size()/1000);j++)
        {
        pi=0;
        queryOrderIds= "";
        SiteLedgerDataVector pSLDV=new SiteLedgerDataVector();
        log.logln("num query : "+j,PrivateLog.DEBUG);
        PairViewVector navigateInfo=new PairViewVector();
        for(int idx=0;idx <(j==(int)(siteLedgerDataVector.size()/1000)?(siteLedgerDataVector.size()%1000):1000);idx++)
        {
         pSLDV.add(siteLedgerDataVector.get((j*1000)+idx));
        // navigateInfo.add(new PairView(new Integer(idx),siteLedgerDataVector.get((j*1000)+idx)));
        }
        log.logln("query size :"+pSLDV.size(),PrivateLog.DEBUG);
        Collections.sort(pSLDV,SITE_LEDGER_ID_COMPARE );
        Iterator it=pSLDV.iterator();
        for(i=0;it.hasNext();i++)
        {
            int id = ((SiteLedgerData) it.next()).getSiteLedgerId();
            if(id>0){
                if(i!=0) queryOrderIds+=",";
                queryOrderIds+=String.valueOf(id);
            }
        }

        StringBuffer sqlBuf = new StringBuffer("SELECT COST_CENTER_ID,FISCAL_CALENDER_ID,ORDER_ID," +
                "SITE_ID,SITE_LEDGER_ID,ADD_BY,ADD_DATE,AMOUNT,BUDGET_PERIOD,BUDGET_YEAR,COMMENTS," +
                "ENTRY_TYPE_CD,MOD_BY,MOD_DATE FROM CLW_SITE_LEDGER " +
                "WHERE SITE_LEDGER_ID IN ( "+queryOrderIds+" ) ORDER BY SITE_LEDGER_ID") ;

        log.logln("Get SiteLedgerDataVector Info : " +sqlBuf.toString(),PrivateLog.DEBUG);
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String sql = sqlBuf.toString();
            stmt = pCon.createStatement();
            rs=stmt.executeQuery(sql);
            SiteLedgerData x;
            SiteLedgerDataVector xDV=new  SiteLedgerDataVector();
            while (rs.next()) {
                x=SiteLedgerData.createValue();

                x.setCostCenterId(rs.getInt(1));
                x.setFiscalCalenderId(rs.getInt(2));
                x.setOrderId(rs.getInt(3));
                x.setSiteId(rs.getInt(4));
                x.setSiteLedgerId(rs.getInt(5));
                x.setAddBy(rs.getString(6));
                x.setAddDate(rs.getTimestamp(7));
                x.setAmount(rs.getBigDecimal(8));
                x.setBudgetPeriod(rs.getInt(9));
                x.setBudgetYear(rs.getInt(10));
                x.setComments(rs.getString(11));
                x.setEntryTypeCd(rs.getString(12));
                x.setModBy(rs.getString(13));
                x.setModDate(rs.getTimestamp(14));
                xDV.add(x);


                 pi++;
                }
              if(xDV.size()!=pSLDV.size())
                  throw new Exception("Arrays size not equal");

              result.addAll(findNotEqual(pSLDV,xDV));
              log.logln("processed :"+pi,PrivateLog.DEBUG);
            }
           catch (Exception e) {
             log.logExc("L",++exc,e.getMessage(),"GETONLYCHANGEDATA");
             result.addAll(pSLDV);
        }
        }
        log.logln("result filtered SiteLedgerDataVector : "+result,PrivateLog.DEBUG);
        log.logln("result size :"+result.size(),PrivateLog.DEBUG);
        log.logln("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF",PrivateLog.DEBUG);

        return result;
        }

   private SiteLedgerDataVector findNotEqual(SiteLedgerDataVector sldv, SiteLedgerDataVector xDV) {

        log.logln("findNotEqual",PrivateLog.DEBUG);
        Iterator it=sldv.iterator();
        SiteLedgerDataVector result=new SiteLedgerDataVector();
        int i=0;
        while(it.hasNext())
        {
         //PairView pv=(PairView)it.next();
         //int index=((Integer)pv.getObject1()).intValue();
         //log.logln("INDEX :"+index,PrivateLog.DEBUG);
         SiteLedgerData nsld=(SiteLedgerData)it.next();
         SiteLedgerData sld= (SiteLedgerData)xDV.get(i);
         log.logln("NEW  : "+nsld,PrivateLog.DEBUG);
         log.logln("OLD  : "+sld,PrivateLog.DEBUG);
         log.logln("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC",PrivateLog.DEBUG);
         log.logln("COMPAREEEE : "+sld.getAmount().compareTo(nsld.getAmount()),PrivateLog.DEBUG);
         log.logln("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC",PrivateLog.DEBUG);
            if(sld.getOrderId()==nsld.getOrderId() && sld.getSiteId()==nsld.getSiteId()&&
             sld.getCostCenterId()==nsld.getCostCenterId()&&sld.getFiscalCalenderId()==nsld.getFiscalCalenderId()&&
                 sld.getBudgetPeriod()==nsld.getBudgetPeriod()&&
                     sld.getBudgetYear()==nsld.getBudgetYear()&&(sld.getAmount().compareTo(nsld.getAmount())==0))

        {
         log.logln("EQUAL",PrivateLog.DEBUG);
        }
        else result.add(nsld);
            i++;
       }
        return result;
    }

   private void deleteDBData(Connection pCon,SiteLedgerDataVector siteLedgerDataVector) throws RemoteException {

        if(siteLedgerDataVector!=null)
        {
            Iterator it=siteLedgerDataVector.iterator();
            int i=0;
            int error=0;
            int pass=0;
            ArrayList errorMessages=new ArrayList();
            SiteLedgerData siteLedgerData=null;
            log.logln("                                            DELETE.SIZE : "+siteLedgerDataVector.size(),PrivateLog.REPORT);
            log.logln("",PrivateLog.REPORT);
            long executeprocessTime=System.currentTimeMillis();
            long avgprocessTime=0;
            long allTime=0;
            while(it.hasNext())
            {
                try {

                    siteLedgerData=(SiteLedgerData)it.next();
                    SiteLedgerDataAccess.remove(pCon,siteLedgerData.getSiteLedgerId(),true);
                    pass++;

                } catch (Exception e) {
                    error++;
                    if(error>=DB_ABORT_LIMIT) throw new RemoteException("Many exception in method <ReCalculateOrdersPipeline.deleteDBData>");
                    log.logExc("DB",error,e.getMessage(),"DELETE DB");
                } finally {
                    i++;
                    allTime=System.currentTimeMillis()-executeprocessTime;
                    avgprocessTime=allTime/i;
                }
            }

            log.logln("                      @@@@@@@@@@@@@@@@@@@@@@@@ OPERATION : DELETE @@@@@@@@@@@@@@@@@@@@@@",PrivateLog.REPORT);
            log.logln("                      @@@@@@@@@ Pass : "+pass +" @@@@@@@@@ Fail : "+error+" @@@@@@@@ All :"+i,PrivateLog.REPORT);
            log.logln("                      @@@@@@@@@ process Time : "+allTime+" ms  @@@@@@@@@ Avg process Time "+avgprocessTime+" ms",PrivateLog.REPORT);
            log.logln("                      @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",PrivateLog.REPORT);
            log.logln("",PrivateLog.REPORT);

        }
    }

   private class PrivateLog {

      private static final int LIMIT_SIZE =100*1024 ;

      public static final int REPORT =2;
      public static final int EXCEPTION=1;
      public static final int MESSAGE=3;
      public static final int DEBUG=4;

      public static final String IN_PROCESS="IN_PROCESS";
      public static final String AFTER_PROCESS="AFTER_PROCESS";
      private  String mode  = IN_PROCESS;


      public static final String CONSOLE_OUT="System";
      public static final String FILE_OUT="File";
      public static final String CONSOLE_OUT_AND_FILE_OUT=CONSOLE_OUT+":"+FILE_OUT;

      private  int level=0;
      private  boolean logOn=true;
      private String receiverOut =FILE_OUT;
      private String lineSeparator="/r/n";
      private IOUtilities io=null;
      private StringBuffer logBuffer=null;
      private boolean limit=false;
      private boolean systemInfo=false;
      private String fileName="recalculate_orders.log";
      private String rootDir=File.separator;


       private PrivateLog() {
            receiverOut =FILE_OUT;
            level=EXCEPTION;
            rootDir="";
            logOn=true;
            lineSeparator=System.getProperty("line.separator");
            io=new IOUtilities();
            logBuffer=new StringBuffer();
        }

        private void logExc(String level, int numException, String message, String operation) {
           logln("             EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE",PrivateLog.EXCEPTION);
           logln("             EEEEEEEEEEEEEEEEEEEEEEEEEEEEEE EXCEPTION : "+level+ (numException),PrivateLog.EXCEPTION);
           logln("             EEEEEEEEEEEEEEEEEEEEEEEEEEEEEE MESSAGE   : "+message,PrivateLog.EXCEPTION);
           logln("             EEEEEEEEEEEEEEEEEEEEEEEEEEEEEE OPERATION : "+operation,PrivateLog.EXCEPTION);
           logln("             EEEEEEEEEEEEEEEEEEEEEEEEEEEEEE TIME      : "+Utility.getFormatTime(System.currentTimeMillis(),0),PrivateLog.EXCEPTION);
           logln("             EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE",PrivateLog.EXCEPTION);

       }
        public void  log(String str)  {
            if(((logBuffer.length())<(LIMIT_SIZE+1024))||systemInfo){
                logBuffer.append(str);
                if(IN_PROCESS.equals(mode))
                {
                    printBuffer();
                    logBuffer=new  StringBuffer();
                }
            }
            else{
                if(!limit)
                {
                    systemInfo=true;
                    log.logln("SYSTEM  MESSAGE [ERROR]: LIMIT LOG BUFFER SIZE");
                    systemInfo=false;
                }
                limit=true;

            }
        }
        public void logln(String str) {

            if(((logBuffer.length())<(LIMIT_SIZE+1024))||systemInfo){
                logBuffer.append(str);
                logBuffer.append(lineSeparator);
                if(IN_PROCESS.equals(mode))
                {
                    printBuffer();
                    logBuffer=new  StringBuffer();
                }
            }  else{
                if(!limit)
                {
                    systemInfo=true;
                    log.logln("SYSTEM  MESSAGE [ERROR]: LIMIT LOG BUFFER SIZE");
                    systemInfo=false;
                }
                limit=true;

            }

        }
        public void  logln(String str,int prior) {

                if (prior<=level) logln(str);

        }
        public void logln() {
            logln("");
        }
        public void  log(String str,int prior) {

                if(prior<=level) log(str);

        }
        public void  printBuffer() {
            if(logOn&&logBuffer.length()>0)
            {
                String strlogBuffer=logBuffer.toString();
                int operation=-1;
                if(AFTER_PROCESS.equals(mode)) operation=IOUtilities.WRITE_AND_CLOSE;
                else if( IN_PROCESS.equals(mode))  operation=IOUtilities.WRITE;

                boolean parseFl=false;
                ArrayList dists=new ArrayList();
                dists.add(receiverOut );
                int num=0;
                do{
                    if(CONSOLE_OUT_AND_FILE_OUT.equals((String)dists.get(num))){
                        logger.info(strlogBuffer);
                        io.writeInFile(fileName,strlogBuffer, operation);
                        // break;
                    }
                    else if (CONSOLE_OUT.equals((String)dists.get(num))){
                        logger.info(strlogBuffer);
                    }
                    else if  (FILE_OUT.equals((String)dists.get(num)))
                    {
                        io.writeInFile(fileName,strlogBuffer, operation);
                    }
                    else
                    {
                        if(!parseFl){
                            ArrayList parseOp= parseDistOut((String)dists.get(num));
                            dists.clear();
                            dists.addAll(parseOp);
                            num=-1;
                            parseFl=true;
                        }
                        else
                        {
                            logger.info("Can't build log.logln .Not correct <OUT> operation : "+dists);
                            num=dists.size();
                        }
                    }
                    num++;
                } while(dists.size()>num);
            }

        }

        private ArrayList parseDistOut(String s) {
            StringTokenizer st=new StringTokenizer(s,":");
            ArrayList dists=new ArrayList();
            while(st.hasMoreElements())
            {
                dists.add((String)st.nextElement());
            }
            return dists;
        }


        public void printInfoAboutSettings()   {

            this.logln("             :::::::::::::::::::::::::::LOGSETINGS::::::::::::::::::::::::::::::::::::::");
            this.logln("   "+"MODE : "+mode +" LIMIT_SIZE : "+LIMIT_SIZE +" LEVEL : "+level+" RECIVER_OUT : "+receiverOut+ " FILE_NAME : "+fileName);
            this.logln("             :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");



        }

        public int getLevel() {
            return level;
        }
        public void setLevel(int level) {
            this.level = level;
        }

        public String getMode() {
            return mode;
        }
        public void setMode(String mode) {
            this.mode = mode;
        }

        public boolean isLogOn() {
            return logOn;
        }
        public void setLogOn(boolean logOn) {
            this.logOn = logOn;
        }

        public String getReceiverOut () {
            return receiverOut ;
        }
        public void setReceiverOut (String distance_Out) {
            this.receiverOut  = distance_Out;
        }

        protected void finalize() throws Throwable {
            //close stream
            io.closeStreams();
            super.finalize();
        }

       public void setFileName(String logFileName) {
           this.fileName = logFileName;
       }

       public void setRootDir(String rootDir) {
           this.rootDir = rootDir;
       }

       public String getRootDir() {
           return rootDir;
       }

       public String getFileName() {
           return fileName;
       }

       private class IOUtilities{

            private static final int WRITE_AND_CLOSE = 2;
            private static final int CLOSE = 0;
            private static final int WRITE = 1;
            private int streamStatus=CLOSE;
            private FileOutputStream fos=null;
            private BufferedOutputStream bos=null;
            private PrintWriter pw=null;
           int num=0;

            public  FileOutputStream createFileOutputStream(String nameFile)    {
                FileOutputStream os=null;
                try {
                    logger.info("create stream...");
                    os= new FileOutputStream(nameFile);
                } catch (FileNotFoundException e) {
                    logger.info(e.getMessage());
                    logger.info("System attempt create file : "+nameFile);
                    if(createFile(nameFile)==0)
                    {    try {
                        logger.info("create stream...");
                        os= new FileOutputStream(nameFile);
                        num=0;
                    } catch (FileNotFoundException e1) {
                        logger.info(e.getMessage());
                        return null;
                    }
                    }
                    else
                    {
                        logger.info("Attempt create file "+nameFile+"  failed");
                        return null;
                    }
                }
                return os;
            }

            public int writeInFile(String nameFile,String  data,int operation) {
                try {
                    data=(num++)+" : "+data;
                    if(operation<0)
                    {
                        return -1;
                    }
                    if(streamStatus==CLOSE&&operation==CLOSE)
                    {
                        return 0;
                    }
                    if(streamStatus!=CLOSE&&operation==CLOSE)
                    {
                        closeStreams();
                        streamStatus=CLOSE;
                        return 0;
                    }
                    if(streamStatus==CLOSE&&(WRITE_AND_CLOSE==operation||WRITE==operation))
                    {
                        fos = createFileOutputStream(nameFile);
                        bos=new BufferedOutputStream(fos);
                        pw=new PrintWriter(bos);
                        streamStatus=WRITE;
                    }
                    if(pw!=null&&streamStatus==WRITE)
                    {
                        pw.print(data);
                        streamStatus=WRITE;
                    }
                    else
                    {
                        closeStreams();
                        streamStatus=CLOSE;
                        return -1;
                    }
                    if(operation == WRITE_AND_CLOSE)
                    {
                        closeStreams();
                        streamStatus=CLOSE;
                    }
                    return 0;
                }
                catch (IOException e) {
                    logger.info(e.getMessage());
                    streamStatus=CLOSE;
                    return -1;
                }

            }

            public int createFile(String nameFile) {
                if(nameFile.length()==0)
                {
                    return -1;
                }
                try {
                    new File(nameFile).createNewFile();
                    return 0;
                } catch (IOException e) {
                    logger.info(e.getMessage());
                    return -1;
                }
            }

            public void closeStreams() throws IOException {
                if(pw!=null) pw.close();
                if(bos!=null) bos.close();
                if(fos!=null) fos.close();
                num=0;
                streamStatus=CLOSE;

            }

            protected void finalize() throws Throwable {
                closeStreams();
                super.finalize();
            }

        }


    }

    public class TimedCache extends HashMap {

    }

}


