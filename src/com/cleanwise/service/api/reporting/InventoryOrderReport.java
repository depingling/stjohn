/*
 * VendorReconciliationReport.java
 *
 *
 */

package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.*;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.StringWriter;
import java.io.PrintWriter;

public class InventoryOrderReport implements GenericReportMulti {

    private static String className="InventoryOrderReport";


    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {

        Connection con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        Statement stmt;
        ResultSet rs;

		
        String accountIdS = (String) pParams.get("ACCOUNT_OPT");
        String siteIdS = (String) pParams.get("SITE_OPT");
        String daysAheadS = (String) pParams.get("DAYS_OPT");        
        int daysAhead = 5;
        if(Utility.isSet(daysAheadS)) {
            try { 
                daysAhead = Integer.parseInt(daysAheadS);
            } catch (Exception exc) {}
        } 
        
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(GregorianCalendar.DATE,daysAhead);
        java.util.Date latestDate = gc.getTime();
        
        APIAccess apiAccess = new APIAccess();
        Site siteEjb = apiAccess.getSiteAPI();
        ShoppingServices shopServEjb = apiAccess.getShoppingServicesAPI();

        //IdVector siteIdV = siteEjb.getInventorySiteCollection(true);
        IdVector siteIdV = siteEjb.getInventorySiteCollection();
        //Split siteIdV
        ArrayList siteGroupAL = new ArrayList();
        int count = 0;
        IdVector wrkIdV = new IdVector();
        for(Iterator iter = siteIdV.iterator(); iter.hasNext();) {
            wrkIdV.add(iter.next());
            count++;
            if(count>998) {
                siteGroupAL.add(wrkIdV);
                wrkIdV = new IdVector();
                count = 0;
            }
        }
        if(wrkIdV.size()>0) siteGroupAL.add(wrkIdV);

        ArrayList allInvOrderAL = new ArrayList();
        for(Iterator iter=siteGroupAL.iterator(); iter.hasNext();) {
            wrkIdV = (IdVector) iter.next();
            String sql =         
               "select " +
                "a.bus_entity_id acct_id, " +
                "a.short_desc acct_name, " +
                "s.bus_entity_id site_id, " +
                "s.short_desc site_name, " +
                "s.bus_entity_status_cd site_status " +
                " from clw_bus_entity s, clw_bus_entity a, clw_bus_entity_assoc bea " +
                " where s.bus_entity_id = bea.bus_entity1_id  " +
		" and s.bus_entity_status_cd = 'ACTIVE' "+
                " and a.bus_entity_id = bea.bus_entity2_id " +
                " and bea.bus_entity_assoc_cd = 'SITE OF ACCOUNT' " +
                " and bea.bus_entity1_id in ("+IdVector.toCommaString(wrkIdV)+")" +
                ((Utility.isSet(accountIdS))?
                    " and bea.bus_entity2_id in ("+accountIdS+")":"") +
                ((Utility.isSet(siteIdS))?
                    " and bea.bus_entity1_id in ("+siteIdS+")":"") ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                InvOrder io = new InvOrder();             
                io.acctId = rs.getInt("acct_id");
                io.acctName = rs.getString("acct_name");
                io.siteId = rs.getInt("site_id");
                io.siteName = rs.getString("site_name");
                io.siteStatus = rs.getString("site_status");
                allInvOrderAL.add(io);
            }
            rs.close();
            stmt.close();
        }

        ArrayList invOrderAL = new ArrayList();
        count = 0;
        for(Iterator iter=allInvOrderAL.iterator(); iter.hasNext();) {
            InvOrder io = (InvOrder) iter.next();
            ScheduleOrderDates sod = 
                siteEjb.calculateNextOrderDates(io.siteId, io.acctId,0);
            count++;
            if(sod==null &&
               !RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE.equals(io.siteStatus)) {
                continue;
            }
            if(sod!=null) {
                io.cutoffDate = sod.getNextOrderCutoffDate();
                io.deliveryDate = sod.getNextOrderDeliveryDate();
                if(io.cutoffDate.after(latestDate)) {
                    continue;
                }
            }
            invOrderAL.add(io);
        }


        ////////////////////////////////////////////
        // Orders to be placed
        int pageQty = 1;
        for(Iterator iter=invOrderAL.iterator(); iter.hasNext();) {
            if(pageQty>20) {
                break;
            }
            pageQty++;
            InvOrder io = (InvOrder) iter.next();
            GenericReportResultView detailSheet = GenericReportResultView.createValue();
            resultV.add(detailSheet);
            ArrayList orderLineAL = new ArrayList();
            detailSheet.setTable(orderLineAL);

            SiteInventoryConfigViewVector sicVwV = 
                   siteEjb.lookupSiteInventory(io.siteId);

            UserData userD = UserData.createValue();
            userD.setUserName("Report User");
            userD.setUserRoleCd("^" + Constants.UserRole.CONTRACT_ITEMS_ONLY);

            SiteData siteD = siteEjb.getSite(io.siteId);

            ShoppingCartItemDataVector sciDV = new ShoppingCartItemDataVector();
            if(siteD.getContractData()!=null) {            
                ShoppingCartData scD = 
                    shopServEjb.getShoppingCart(null, userD,siteD,
                        siteD.getContractData().getCatalogId(),
                        siteD.getContractData().getContractId());
                sciDV = scD.getItems();       
            }
    
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(InventoryItemsDataAccess.BUS_ENTITY_ID, io.acctId);
            //dbc.addEqualToIgnoreCase(InventoryItemsDataAccess.ENABLE_AUTO_ORDER,"Y");
            InventoryItemsDataVector invItemsDV = 
                                       InventoryItemsDataAccess.select(con,dbc);

            for(Iterator iter1 = sicVwV.iterator();iter1.hasNext();) {
                SiteInventoryInfoView siiVw = (SiteInventoryInfoView)iter1.next();
                int qtyOnHand = 0;
                try {
                    if(siiVw.getQtyOnHand()!=null ) {
                        qtyOnHand = Integer.parseInt(siiVw.getQtyOnHand());
                    }
                } catch (Exception exc) {}
                if(siiVw.getParValue()<=0 && qtyOnHand==0) continue;
                ProductData pD = siiVw.getProductData();
                OrderLine ol = new OrderLine();
                ol.invOrder = io;
                int itemId = pD.getProductId();
                ol.itemId = new Integer(itemId);
                ol.skuNum = pD.getCatalogSkuNum();
                ol.itemName = pD.getItemData().getShortDesc();
                ol.invParValue = new Integer(siiVw.getParValue());
                ol.invQtyOnHand = siiVw.getQtyOnHand();
                for(Iterator iter2 = sciDV.iterator(); iter2.hasNext();) {
                    ShoppingCartItemData sciD = (ShoppingCartItemData) iter2.next();
                    if(itemId == sciD.getItemId()) {
                        ol.cartQty = new Integer(sciD.getQuantity());
                        ol.cartParValue = new Integer(sciD.getInventoryParValue());
                        ol.cartQtyOnHand = new Integer(sciD.getInventoryQtyOnHand());
                        ol.cartSource = sciD.getQtySourceCd();
                        iter2.remove();
                        break;
                    }
                }          
                for(Iterator iter3 = invItemsDV.iterator(); iter3.hasNext(); ) {
                    InventoryItemsData iiD = (InventoryItemsData) iter3.next();
                    if(iiD.getItemId()==itemId) {
                        ol.autoOrder = iiD.getEnableAutoOrder();
                        iter3.remove();
                        break;
                    }                    
                }
                orderLineAL.add(ol.toList());
            }
            
            for(Iterator iter2 = sciDV.iterator(); iter2.hasNext();) {
                ShoppingCartItemData sciD = (ShoppingCartItemData) iter2.next();
                OrderLine ol = new OrderLine();
                ol.invOrder = io;
                ol.itemId = new Integer(sciD.getItemId());
                ol.skuNum = sciD.getActualSkuNum();
                ol.itemName = sciD.getProduct().getItemData().getShortDesc();
                ol.cartQty = new Integer(sciD.getQuantity());
                ol.cartParValue = new Integer(sciD.getInventoryParValue());
                ol.cartQtyOnHand = new Integer(sciD.getInventoryQtyOnHand());
                ol.cartSource = sciD.getQtySourceCd();
                for(Iterator iter3 = invItemsDV.iterator(); iter3.hasNext(); ) {
                    InventoryItemsData iiD = (InventoryItemsData) iter3.next();
                    if(iiD.getItemId()==sciD.getItemId()) {
                        ol.autoOrder = iiD.getEnableAutoOrder();
                        iter3.remove();
                    }                    
                }
                orderLineAL.add(ol.toList());
                iter2.remove();
            }
            GenericReportColumnViewVector detailHeader = getOrderLineHeader();
            detailSheet.setColumnCount(detailHeader.size());
            detailSheet.setHeader(detailHeader);
            detailSheet.setName(String.valueOf(io.siteId));                   
        }
        
        GenericReportResultView result = GenericReportResultView.createValue();
        result.setTable(new ArrayList());
        HashSet hs=new HashSet();
        for (Iterator iter1=invOrderAL.iterator(); iter1.hasNext();) {
          InvOrder io = (InvOrder) iter1.next();
          result.getTable().add(io.toList());
        }
        GenericReportColumnViewVector mainHeader = getMainHeader();
        result.setColumnCount(mainHeader.size());
        result.setHeader(mainHeader);
        result.setName("Inv Orders");
        resultV.add(result);
        return resultV;

    }


    private GenericReportColumnViewVector getMainHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Account Id", 0,38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Site Id", 0,38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site Status", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Cutoff",0,0,"DATE"));        
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Delivery",0,0,"DATE"));        
        return header;
    }

    private GenericReportColumnViewVector getOrderLineHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Item Id", 0,38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Sku Num", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Prod Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Par Value", 0,38, "NUMBER"));
        //header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Cart Par", 0,38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "On Hand", 0,38, "NUMBER"));
        //header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Cart On Hand", 0,38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Cart Qty", 0,38, "NUMBER"));
        //header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Order Qty", 0,38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Source", 0,38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Auto Order", 0,38, "NUMBER"));
        return header;
    }

    private class InvOrder {
        int acctId;
        String acctName;
        int siteId;
        String siteName;
        String siteStatus;
        java.util.Date cutoffDate;
        java.util.Date deliveryDate;
        
        private ArrayList toList() {
            ArrayList list = new ArrayList();
            list.add(new Integer(acctId));
            list.add(acctName);
            list.add(new Integer(siteId));
            list.add(siteName);
            list.add(siteStatus);
            list.add(cutoffDate);
            list.add(deliveryDate);
            return list;
        }
    }

     private class OrderLine {
        InvOrder invOrder;   
        Integer itemId;
        String skuNum;
        String itemName;
        Integer invParValue;
        Integer cartParValue;
        String invQtyOnHand;
        Integer cartQtyOnHand;
        Integer cartQty;
        Integer orderQty;
        String cartSource;
        String autoOrder;
 
        private ArrayList toList() {
            ArrayList list = new ArrayList();
            list.add(itemId);
            list.add(skuNum);
            list.add(itemName);
            list.add(invParValue);
            //list.add(cartParValue);
            list.add(invQtyOnHand);
            //list.add(cartQtyOnHand);
            list.add(cartQty);
            //list.add(orderQty);
            list.add(cartSource);
            list.add(autoOrder);
            return list;
        }
    }

}
