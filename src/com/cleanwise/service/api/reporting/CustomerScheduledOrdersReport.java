package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.tree.ReportItem;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.session.AutoOrder;
import com.cleanwise.service.api.session.OrderGuide;

import java.util.Map;
import java.util.Iterator;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 21.06.2007
 * Time: 0:31:03
 * @author Evgeny Vlassov
 * @compay CleanWise
 */
public class CustomerScheduledOrdersReport implements GenericReport {

    private static String className = "CustomerScheduledOrdersReport";

    public final static String CUSTOMER   = "CUSTOMER";
    public final static String BEG_DATE   = "BEG_DATE";
    public final static String END_DATE   = "END_DATE";

    public GenericReportResultView process(ConnectionContainer pCons,
                              GenericReportData pReportData, Map pParams) throws Exception {

        String userIdsS;
        String begDateS;
        String endDateS;

        GenericReportResultView result = GenericReportResultView.createValue();
        result.setTable(new ArrayList());

        APIAccess factory = APIAccess.getAPIAccess();

        try {

            begDateS = (String) pParams.get(BEG_DATE);
            endDateS = (String) pParams.get(END_DATE);
            userIdsS = (String) pParams.get(CUSTOMER);

            AutoOrder autoOrderBean = factory.getAutoOrderAPI();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            OrderScheduleViewVector orders = autoOrderBean.getOrderSchedules(
                    Integer.parseInt(userIdsS),
                    sdf.parse(begDateS),
                    sdf.parse(endDateS)
            );

            Iterator itOrderScheduleViewVector = orders.iterator();

            OrderGuide autoOrderGuide = factory.getOrderGuideAPI();
            while(itOrderScheduleViewVector.hasNext()){

                OrderScheduleView orderScheduleView = (OrderScheduleView) itOrderScheduleViewVector.next();

                OrderGuideData orderGuide = autoOrderGuide.getOrderGuide(orderScheduleView.getOrderGuideId());
                OrderGuideItemDescDataVector orderGuideItemsDesc = autoOrderGuide.getOrderGuideItemsDesc(orderGuide.getCatalogId(), orderScheduleView.getOrderGuideId());

                Iterator itOrderGuideItemDescDataVector = orderGuideItemsDesc.iterator();
                while(itOrderGuideItemDescDataVector.hasNext()){
                    OrderGuideItemDescData orderGuideItemDescData = (OrderGuideItemDescData) itOrderGuideItemDescDataVector.next();

                    ArrayList list = new ArrayList();

                    list.add(new Integer(orderScheduleView.getOrderScheduleId()));
                    list.add(orderScheduleView.getAccountName());
                    list.add(orderScheduleView.getOrderGuideName());
                    list.add(orderScheduleView.getSiteName());
                    list.add(orderScheduleView.getEffDate());
                    list.add(orderScheduleView.getExpDate());

                    list.add(orderGuideItemDescData.getShortDesc());
                    list.add(orderGuideItemDescData.getManufacturerSKU());
                    list.add(new Integer(orderGuideItemDescData.getQuantity()));
                    list.add(orderGuideItemDescData.getPrice());

                    result.getTable().add(list);
                }
            }


        } catch (RuntimeException e) {
           e.printStackTrace();
           throw e;
        }
        try {

            GenericReportColumnViewVector reportHeader = getReportHeader();
            result.setColumnCount(reportHeader.size());
            result.setHeader(reportHeader);
            result.setName("Customer Scheduled Orders");

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private GenericReportColumnViewVector getReportHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer",    "#Scheduled Order", 0, 38,  "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",     "Account Name",     0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",     "Order Guide Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",     "Site Name",        0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp",   "Eff Date",         0, 0,   "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp",   "Exp Date",         0, 0,   "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",     "Item Short Desc",  0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",     "Manufacturer SKU", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer",    "Quantity",         0, 38,  "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Price",            2, 20,  "NUMBER"));

        return header;
    }
}
