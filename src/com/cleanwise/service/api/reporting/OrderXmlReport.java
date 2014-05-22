package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.tree.ReportItem;
import com.cleanwise.service.api.tree.transformer.Java2ReportItemTransformer;
import com.cleanwise.service.api.util.ConnectionContainer;

import java.sql.*;
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
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Picks up all catalog items and adds year to date trade infrmation Adapted
 * from the ReportOrderBean to the new GenericReport Framework
 */
public class OrderXmlReport implements DomUniversalReport {
    public final static String ORDER_NUM = "ORDER_NUM";

    public ReportItem process(ConnectionContainer pCons,
            GenericReportData pReportData, Map pParams) throws Exception {
        ReportItem tradingPartnerItem = null;
        String pOrderNum = (String) pParams.get(ORDER_NUM);
        if(pOrderNum==null) {
            String errorMess = "^clw^No Invoice Number found^clw^";
            throw new Exception(errorMess);
        }

        Connection con = pCons.getDefaultConnection();

        String orderSql =
            "select ORDER_NUM, "+
            " nvl(ORIGINAL_ORDER_DATE,REVISED_ORDER_DATE) ORDER_DATE,  "+
            " REQUEST_PO_NUM PO_NUMBER,  "+
            " order_source_cd SOURCE, "+
            " order_contact_name CONTACT_NAME,  "+
            " order_contact_phone_num CONTACT_PHONE,  "+
            " order_contact_email CONTACT_EMAIL,  "+
            " user_first_name||' '||user_last_name as PLACED_BY, "+
            " COMMENTS,  "+
            " ORIGINAL_AMOUNT SUBTOTAL, "+
            " TOTAL_FREIGHT_COST FREIGHT ,  "+
            " TOTAL_MISC_COST MISC_CHARGE,  "+
            " TOTAL_TAX_COST TAX,  "+
            " TOTAL_PRICE TOTAL, "+
            " order_id,  "+
            " order_status_cd "+
            " from clw_order o where order_num  =  ?";//+pOrderNum;

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        PreparedStatement stmt = con.prepareStatement(orderSql);
        stmt.setString(1, pOrderNum);
        ResultSet  rs = stmt.executeQuery();
        ReportItem rootRI = ReportItem.createValue("root");
        ReportItem orderRI = ReportItem.createValue("Order");
        rootRI.addChild(orderRI);
        int orderId = 0;
        ArrayList statusAL = new ArrayList();
        while (rs.next()) {
            String status = rs.getString("order_status_cd");
            if(RefCodeNames.ORDER_STATUS_CD.INVOICED.equals(status) ||
               RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED.equals(status)) {
                orderId = rs.getInt("order_id");
            } else {
                statusAL.add(status);
                continue;
            }

            String orderNum = rs.getString("ORDER_NUM");
            ReportItem orderNumRI = ReportItem.createValue("ORDER_NUM",orderNum);
            orderRI.addChild(orderNumRI);

            Date orderDate = rs.getDate("ORDER_DATE");
            ReportItem orderDateRI = ReportItem.createValue("ORDER_DATE",sdf.format(orderDate));
            orderRI.addChild(orderDateRI);

            String poNum = rs.getString("PO_NUMBER");
            ReportItem poNumRI = ReportItem.createValue("PO_NUMBER",poNum);
            orderRI.addChild(poNumRI);

            String source = rs.getString("SOURCE");
            ReportItem sourceRI = ReportItem.createValue("SOURCE",source);
            orderRI.addChild(sourceRI);

            String contactName = rs.getString("CONTACT_NAME");
            ReportItem contactNameRI = ReportItem.createValue("CONTACT_NAME",contactName);
            orderRI.addChild(contactNameRI);

            String contactPhone = rs.getString("CONTACT_PHONE");
            ReportItem contactPhoneRI = ReportItem.createValue("CONTACT_PHONE",contactPhone);
            orderRI.addChild(contactPhoneRI);

            String contactEmail = rs.getString("CONTACT_EMAIL");
            ReportItem contactEmailRI = ReportItem.createValue("CONTACT_EMAIL",contactEmail);
            orderRI.addChild(contactEmailRI);

            String placedBy = rs.getString("PLACED_BY");
            ReportItem placedByRI = ReportItem.createValue("PLACED_BY",placedBy);
            orderRI.addChild(placedByRI);

            String comments = rs.getString("COMMENTS");
            ReportItem commentsRI = ReportItem.createValue("COMMENTS",comments);
            orderRI.addChild(commentsRI);

            BigDecimal subTotal = rs.getBigDecimal("SUBTOTAL");
            if(subTotal!=null) subTotal = subTotal.setScale(2,BigDecimal.ROUND_HALF_UP);
            String subTotalS = (subTotal==null)? null:subTotal.toString();
            ReportItem subTotalRI = ReportItem.createValue("SUBTOTAL",subTotalS);
            orderRI.addChild(subTotalRI);

            BigDecimal freight = rs.getBigDecimal("FREIGHT");
            if(freight!=null) freight = freight.setScale(2,BigDecimal.ROUND_HALF_UP);
            String freightS = (freight==null)? null:freight.toString();
            ReportItem freightRI = ReportItem.createValue("FREIGHT",freightS);
            orderRI.addChild(freightRI);

            BigDecimal miscCharge = rs.getBigDecimal("MISC_CHARGE");
            if(miscCharge!=null) miscCharge = miscCharge.setScale(2,BigDecimal.ROUND_HALF_UP);
            String miscChargeS = (miscCharge==null)? null:miscCharge.toString();
            ReportItem miscChargeRI = ReportItem.createValue("MISC_CHARGE",miscChargeS);
            orderRI.addChild(miscChargeRI);

            BigDecimal tax = rs.getBigDecimal("TAX");
            if(tax!=null) tax = tax.setScale(2,BigDecimal.ROUND_HALF_UP);
            String taxS = (tax==null)? null:tax.toString();
            ReportItem taxRI = ReportItem.createValue("TAX",taxS);
            orderRI.addChild(taxRI);

            BigDecimal total = rs.getBigDecimal("TOTAL");
            if(total!=null) total = total.setScale(2,BigDecimal.ROUND_HALF_UP);
            String totalS = (total==null)? null:total.toString();
            ReportItem totalRI = ReportItem.createValue("TOTAL",totalS);
            orderRI.addChild(totalRI);

            break;
        }
        rs.close();
        stmt.close();

        if(orderId==0) {
            if(statusAL.isEmpty()) {
                String errorMess = "^clw^No order found. Order number = "+pOrderNum+"^clw^";
                throw new Exception(errorMess);
            } else {
                String errorMess = "^clw^No processed  order found. Order number = "+pOrderNum+
                        " Found orders with status: ";
                for(int ii=0; ii<statusAL.size(); ii++) {
                    if(ii>0) errorMess += ", ";
                    errorMess += statusAL.get(ii);
                }
                errorMess +="^clw^";
                throw new Exception(errorMess);
            }
        }

        String addressSql =
           "select "+
           " oa.ADDRESS_TYPE_CD,  "+
           " o.ACCOUNT_ERP_NUM as NAME_NUM, "+
           " address1 ||' '||address2||' '||address3||' '||address4 as  STREET_ADDRESS,  "+
           " CITY, " +
           " STATE_PROVINCE_CD," +
           " POSTAL_CODE," +
           " nvl(COUNTRY_CD,'UNITED STATES') COUNTRY  "+
           " from clw_order_address oa, clw_order o  "+
           " where o.order_id = ? "+
           " and o.order_id = oa.order_id "+
           " and address_type_cd in ('BILLING') "+
           " union all "+
           " select oa.address_type_cd, oa.short_desc shipto_name, "+
           " address1 ||' '||address2||' '||address3||' '||address4 as  street_address,  "+
           " city, STATE_PROVINCE_CD, POSTAL_CODE, nvl(COUNTRY_CD,'UNITED STATES') country  "+
           " from clw_order_address oa, clw_order o  "+
           " where o.order_id = ? "+
           " and o.order_id = oa.order_id "+
           " and address_type_cd in ('SHIPPING') ";

        stmt = con.prepareStatement(addressSql);
        stmt.setInt(1, orderId);
        stmt.setInt(2, orderId);
        rs = stmt.executeQuery();

        ReportItem addressRI = ReportItem.createValue("Address");
        orderRI.addChild(addressRI);

        while (rs.next()) {
            ReportItem addressLineRI = ReportItem.createValue("Address_line");
            addressRI.addChild(addressLineRI);

            String addressTypeCd = rs.getString("ADDRESS_TYPE_CD");
            ReportItem addressTypeCdRI = ReportItem.createValue("ADDRESS_TYPE_CD",addressTypeCd);
            addressLineRI.addChild(addressTypeCdRI);

            String nameNum = rs.getString("NAME_NUM");
            ReportItem nameNumRI = ReportItem.createValue("NAME_NUM",nameNum);
            addressLineRI.addChild(nameNumRI);

            String streetAddress = rs.getString("STREET_ADDRESS");
            ReportItem streetAddressRI = ReportItem.createValue("STREET_ADDRESS",streetAddress);
            addressLineRI.addChild(streetAddressRI);

            String city = rs.getString("CITY");
            ReportItem cityRI = ReportItem.createValue("CITY",city);
            addressLineRI.addChild(cityRI);

            String stateProvinceCd = rs.getString("STATE_PROVINCE_CD");
            ReportItem stateProvinceRI = ReportItem.createValue("STATE_PROVINCE_CD",stateProvinceCd);
            addressLineRI.addChild(stateProvinceRI);

            String postalCode = rs.getString("POSTAL_CODE");
            ReportItem postalCodeRI = ReportItem.createValue("POSTAL_CODE",postalCode);
            addressLineRI.addChild(postalCodeRI);

            String country = rs.getString("COUNTRY");
            ReportItem countryRI = ReportItem.createValue("COUNTRY",country);
            addressLineRI.addChild(countryRI);

        }
        rs.close();
        stmt.close();

        String itemsSql =
            "select "+
            " dist_item_sku_num SKU_NUM, "+
            " item_short_desc ITEM_NAME, "+
            " mt.clw_value ITEM_SIZE,  "+
            " dist_item_uom UOM,  "+
            " dist_item_pack PACK,  "+
            " dist_item_cost COST,  "+
            " dist_item_quantity QTY, "+
            " manu_item_short_desc MANUFACTURER "+
            " from clw_order_item oi, clw_item_meta mt "+
            " where order_id = ? "+
            " and oi.item_id = mt.item_id (+)"+
            " and mt.name_value (+) = 'SIZE' "+
            " and lower(ORDER_ITEM_STATUS_CD)!='cancelled' "+
            " order by order_line_num ";


        stmt = con.prepareStatement(itemsSql);
        stmt.setInt(1, orderId);
        rs = stmt.executeQuery();

        ReportItem itemsRI = ReportItem.createValue("Items");
        orderRI.addChild(itemsRI);

        while (rs.next()) {
            ReportItem itemsLineRI = ReportItem.createValue("Items_line");
            itemsRI.addChild(itemsLineRI);


            String skuNum = rs.getString("SKU_NUM");
            ReportItem skuNumRI = ReportItem.createValue("SKU_NUM",skuNum);
            itemsLineRI.addChild(skuNumRI);

            String itemName = rs.getString("ITEM_NAME");
            ReportItem itemNameRI = ReportItem.createValue("ITEM_NAME",itemName);
            itemsLineRI.addChild(itemNameRI);

            String itemSize = rs.getString("ITEM_SIZE");
            ReportItem itemSizeRI = ReportItem.createValue("ITEM_SIZE",itemSize);
            itemsLineRI.addChild(itemSizeRI);

            String uom = rs.getString("UOM");
            ReportItem uomRI = ReportItem.createValue("UOM",uom);
            itemsLineRI.addChild(uomRI);

            String pack = rs.getString("PACK");
            ReportItem packRI = ReportItem.createValue("PACK",pack);
            itemsLineRI.addChild(packRI);

            BigDecimal cost = rs.getBigDecimal("COST");
            if(cost!=null) cost = cost.setScale(2,BigDecimal.ROUND_HALF_UP);
            String costS = (cost==null)? null:cost.toString();
            ReportItem costRI = ReportItem.createValue("COST",costS);
            itemsLineRI.addChild(costRI);

            int qty = rs.getInt("QTY");
            ReportItem qtyRI = ReportItem.createValue("QTY",qty);
            itemsLineRI.addChild(qtyRI);

            String manufacturer = rs.getString("MANUFACTURER");
            ReportItem manufacturerRI = ReportItem.createValue("MANUFACTURER",manufacturer);
            itemsLineRI.addChild(manufacturerRI);
        }
        rs.close();
        stmt.close();
        return rootRI;

    }
}
