package com.cleanwise.service.api.reporting;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.value.*;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.dao.UniversalDAO.dbrow;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.util.Utility;
import java.util.Map;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Hashtable;


public class OrderInformationReport implements GenericReportMulti {


public GenericReportResultViewVector process(   ConnectionContainer pCons, 
                                                GenericReportData pReportData,
                                                Map pParams) throws Exception {
        
        Hashtable props = new Hashtable();
   
        try {            
            String dateFmt = (String) pParams.get("DATE_FMT");
            
            if (null == dateFmt) {
                dateFmt = "MM/dd/yyyy";
            }
            
            String accountIdStr = (String)ReportingUtils.getParam(pParams, "ACCOUNT");
            String budgetPeriodParams = (String)ReportingUtils.getParam(pParams, "BUDGET_PERIOD_INFO");
            String userIdStr = (String)ReportingUtils.getParam(pParams, "CUSTOMER");
            
            int userId = 0;
            if (Utility.isSet(userIdStr)) {
                userId = Integer.parseInt(userIdStr);
            }
            int accountId = 0;
            if (Utility.isSet(accountIdStr)) {
                accountId = Integer.parseInt(accountIdStr);
            }
            if (!Utility.isSet(budgetPeriodParams)) {
                String errorMess = "^clw^BUDGET PERIOD requires a value^clw^";
                throw new Exception(errorMess);
            } else {
                // Parse the line
                String[] tokens = budgetPeriodParams.split("&", -1);
                String[] parVal;
                for (int i = 0; i < tokens.length; i++) {
                    parVal = tokens[i].split("=", -1);
                    props.put(parVal[0], parVal[1]);
                }
            }
            
            String byearStr = (String)ReportingUtils.getParam(props,"year");
            String bperiodStr = (String)ReportingUtils.getParam(props,"budget_period");
            
            int byear = 0;
            if (!Utility.isSet(byearStr)) {
                String errorMess = "^clw^Internal system error: parameter - year not set^clw^";
                throw new Exception(errorMess);
            } else {
                byear = Integer.parseInt(byearStr);
            }
            int bperiod = 0;
            if (!Utility.isSet(bperiodStr)) {
                String errorMess = "^clw^Internal system error: parameter - budget_period not set^clw^";
                throw new Exception(errorMess);
            } else {
                bperiod = Integer.parseInt(bperiodStr);
            }
                                    
            UserData currentUserData = UserDataAccess.select(pCons.getDefaultConnection(), userId);
            
            boolean userMSB = false;
            if (RefCodeNames.USER_TYPE_CD.MSB.equals(currentUserData.getUserTypeCd()) ||
                RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(currentUserData.getUserTypeCd())) {
                userMSB = true;
            }
            
            APIAccess factory = new APIAccess();
            Site siteEjb = factory.getSiteAPI();
            
            java.util.ArrayList v = null;
            if (userMSB) {

              // Limit the information to those sites
              // tied to the user.
              v = siteEjb.getOrderReport(RefCodeNames.USER_TYPE_CD.MSB,
                                         userId,
                                         byear,
                                         bperiod);
            } else {
              v = siteEjb.getOrderReport (
                            RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT,
                            accountId,
                            byear,
                            bperiod);
            }

            GenericReportResultViewVector resultV = new GenericReportResultViewVector();
            GenericReportResultView result = GenericReportResultView.createValue();
            result.setTable(new ArrayList());
            
            
            ArrayList tmpRowList;
            dbrow tmpRow;
            String orderDateStr;
            String approvedDateStr;
            for (int i = 0; i < v.size(); i++) {
                tmpRowList = new ArrayList();
                tmpRow = (dbrow)v.get(i);
                
                orderDateStr = tmpRow.getStringValue(0);
                if (!ReportingUtils.isValidDate(orderDateStr)) {
                    orderDateStr = "--/--/----";
                }
                tmpRowList.add(orderDateStr);
                tmpRowList.add(tmpRow.getStringValue(1));
                tmpRowList.add(new BigDecimal(tmpRow.getStringValue(2)));
                tmpRowList.add(tmpRow.getStringValue(3));
                tmpRowList.add(tmpRow.getStringValue(4));
                tmpRowList.add(tmpRow.getStringValue(5));
                tmpRowList.add(new BigDecimal(tmpRow.getStringValue(6)));
                tmpRowList.add(new BigDecimal(tmpRow.getStringValue(7)));
                tmpRowList.add(new BigDecimal(tmpRow.getColumn("APPROVED_AMT").colVal));

                BigDecimal tmpNumber = new BigDecimal(tmpRow.getStringValue(8));
                tmpRowList.add(tmpNumber);

                if ((tmpNumber.doubleValue() > 0) && (tmpRow.getColumn("REVISED_DATE").colVal != null)) {
                    approvedDateStr = tmpRow.getColumn("REVISED_DATE").colVal;
                    if (!ReportingUtils.isValidDate(approvedDateStr)) {
                        approvedDateStr = "--/--/----";
                    }
                    tmpRowList.add(approvedDateStr);
                } else if (tmpNumber.doubleValue() > 0 ) { 
                    tmpRowList.add(orderDateStr);
                } else {
                    tmpRowList.add("");
                }    
                tmpRowList.add("rowInfo_currencyCd="
                        + tmpRow.getColumn("CURRENCY_CD").colVal);
                result.getTable().add(tmpRowList);
            }

            GenericReportColumnViewVector oirHeader = getOrderInformationReportHeader();
            result.setColumnCount(oirHeader.size());
            result.setHeader(oirHeader);
            result.setName("Order Information Report");
            result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
            result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);

            resultV.add(result);

            return resultV;
        } catch (NumberFormatException e) {
            throw new Exception(e.toString());
        }
    }

    private GenericReportColumnViewVector getOrderInformationReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "ORDER DATE", 0, 0,"DATE", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "STORE", 0, 255, "VARCHAR2", "30", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "YTD ACTUAL", 0, 255, "NUMBER", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "RANK", 0, 255, "VARCHAR2", "6", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "NSF", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "BSC", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","REQUESTED$", 2, 20, "NUMBER", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","YTD REMAINING BUDGET", 2, 20, "NUMBER", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","APPROVED$", 2, 20, "NUMBER", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","COMMITED$", 2, 20, "NUMBER", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "DATE APPROVED", 0, 0,"DATE", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","rowInfo_Currency Code",0,255,"VARCHAR2"));
        return header;
    }        
}
