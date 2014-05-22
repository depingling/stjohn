/*
 * PdfOrder.java
 *
 * Created on July 15, 2002, 5:36 PM
 */

package com.cleanwise.view.utils.pdf;

import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;


import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *Constructs a pdf document in the form of a pdf document.  It understands the various
 *propriatary objects, and uses them to construct a purchase order document.
 * @author  bstevens
 */
public class PdfOrderStatusNetSupply extends PdfOrderStatus {
    private static int SKU_COLUMN_WDTH_DEFAULT = 8;
    private static int MFG_SKU_COLUMN_WDTH_DEFAULT = 9;

    /** Creates a new instance of PdfOrder */
    public PdfOrderStatusNetSupply() {
      super();
    }


    public void drawOrderHeader
    (Document document, CleanwiseUser appUser, OrderOpDetailForm sForm,
     String pImageName, OrderStatusDescData pOrderStatusDesc)
    throws DocumentException {
        //add the logo
        try
        {
            Image i = Image.getInstance(pImageName);
            float x = document.leftMargin();
            float y = document.getPageSize().getHeight() - i.getHeight() - 5;
            i.setAbsolutePosition(x,y);
            document.add(i);
        }
        catch (Exception e)
        {
            //e.printStackTrace();
        }


        PdfPTable header = new PdfPTable(1);
        header.setWidthPercentage(100);
        header.getDefaultCell().setBorder(borderType);
        header.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

        //Display the contact info
        PdfPTable orderInfo = new PdfPTable(2);
        orderInfo.setWidthPercentage(100);
        orderInfo.setWidths(new int[] {20,80});
        orderInfo.getDefaultCell().setBorder(borderType);
        orderInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

        String  orderNumberStr = ClwI18nUtil.getMessage(getRequest(),"shop.orderStatus.text.orderNumber:",null);
        orderInfo.addCell(makePhrase(orderNumberStr+" ",normal,false));
        OrderData orderD = pOrderStatusDesc.getOrderDetail();
        String orderNumber = orderD.getOrderNum();
        orderInfo.addCell(makePhrase(orderNumber,normal,true));

        OrderData consolidatedToOrderD = pOrderStatusDesc.getConsolidatedOrder();
        if(consolidatedToOrderD!=null) {
          String  consolidatedToOrderStr =
                  ClwI18nUtil.getMessage(getRequest(),"shop.orderStatus.text.consolidatedToOrder:",null);
          orderInfo.addCell(makePhrase(consolidatedToOrderStr+" ",normal,false));
          String consolidatedByOrderNumber = consolidatedToOrderD.getOrderNum();
          orderInfo.addCell(makePhrase(consolidatedByOrderNumber,normal,true));

        }

        OrderData refOrderD = pOrderStatusDesc.getRefOrder();
        if(refOrderD!=null){
          String refOrderNum = refOrderD.getOrderNum();
          String  referenceOrderNumStr =
                  ClwI18nUtil.getMessage(getRequest(),"shop.orderStatus.text.referenceOrderNum:",null);
          orderInfo.addCell(makePhrase(referenceOrderNumStr+" ",normal,false));
          orderInfo.addCell(makePhrase(refOrderNum,normal,true));
        }

        // account number (customer major and customer minor)
        String custMaj = (String)getMiscProperty(RefCodeNames.PROPERTY_TYPE_CD.CUST_MAJ, "na");
        String locationNumber = "";
        PropertyData prop = appUser.getSite().getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
        if (prop != null) {
            locationNumber = prop.getValue();
        }
        String accountNumber = custMaj + "_" + locationNumber;
        String accountNumberStr =
                ClwI18nUtil.getMessage(getRequest(),"shop.orderStatus.text.accountNumber:",null);
        orderInfo.addCell(makePhrase(accountNumberStr+" ",normal,false));
        orderInfo.addCell(makePhrase(accountNumber,normal,true));


        // account name (site name)
        String siteName = orderD.getOrderSiteName();
        String siteNameStr = ClwI18nUtil.getMessage(getRequest(),"shop.orderStatus.text.accountName:",null);
        orderInfo.addCell(makePhrase(siteNameStr+" ",normal,false));
        orderInfo.addCell(makePhrase(siteName,normal,true));

        // distributor name (member name, member major and member minor)
        String distrName = (String)getMiscProperty(Constants.DISTRIBUTOR_NAME);
        String  distrNameStr =
                  ClwI18nUtil.getMessage(getRequest(),"shop.orderStatus.text.distributorName:",null);
        orderInfo.addCell(makePhrase(distrNameStr+" ",normal,false));
        orderInfo.addCell(makePhrase(distrName,normal,true));

        // po number
        String  poNumberStr =
                  ClwI18nUtil.getMessage(getRequest(),"shop.orderStatus.text.poNumber:",null);
        orderInfo.addCell(makePhrase(poNumberStr+" ",normal,false));
        orderInfo.addCell(makePhrase(orderD.getRequestPoNum(),normal,true));

        // order date
        /*
        String dateS = ClwI18nUtil.formatDateInp(getRequest(),orderD.getOriginalOrderDate());
        String  orderDateStr =
                  ClwI18nUtil.getMessage(getRequest(),"shop.orderStatus.text.orderDate:",null);
        orderInfo.addCell(makePhrase(orderDateStr+" ",normal,false));
        orderInfo.addCell(makePhrase(dateS,normal,true));
        */
        // order date
        TimeZone timeZone =  Utility.getTimeZoneFromUserData(appUser.getSite());
        String pattern = ClwI18nUtil.getDatePattern(getRequest());
        SimpleDateFormat sdf = new SimpleDateFormat(pattern + " " + Constants.SIMPLE_TIME_PATTERN);
        if (timeZone != null){
            sdf.setTimeZone(timeZone);
        }

        Date orderDate;
        Date orderTime;
        /*
        if (orderD.getRevisedOrderDate() != null) {
            orderDate = orderD.getRevisedOrderDate();
            if (orderD.getRevisedOrderTime() != null) {
                orderTime = orderD.getRevisedOrderTime();
            } else {
               orderTime = orderD.getRevisedOrderDate();
            }
        } else {
        */
            orderDate = orderD.getOriginalOrderDate();
            orderTime = orderD.getOriginalOrderTime();
        //}
        orderDate = Utility.getDateTime(orderDate, orderTime);
        String orderDateS = sdf.format(orderDate);

        String orderDateStr =
        ClwI18nUtil.getMessage(getRequest(), "shop.orderStatus.text.orderDate:", null);
        orderInfo.addCell(makePhrase(orderDateStr + " ", normal, false));
        orderInfo.addCell(makePhrase(orderDateS, normal, true));

        // delivery date
        String reqShipDateSU = pOrderStatusDesc.getRequestedShipDate();
        String reqShipDateS = null;
        try {
            Date reqShipDate = ClwI18nUtil.parseDateInp(getRequest(), reqShipDateSU);
            reqShipDateS = ClwI18nUtil.formatDateInp(getRequest(),reqShipDate);
        } catch (Exception exc){
            exc.printStackTrace();
            reqShipDateS = reqShipDateSU;
        }
        String  requestedShipDateStr =
                  ClwI18nUtil.getMessage(getRequest(),"shop.orderStatus.text.deliveryDate:",null);
        orderInfo.addCell(makePhrase(requestedShipDateStr+" ",normal,false));
        orderInfo.addCell(makePhrase(reqShipDateS,normal,true));

        // shipping comments
        String  commentsStr =
              ClwI18nUtil.getMessage(getRequest(),"shop.orderStatus.text.shippingComments:",null);
        orderInfo.addCell(makePhrase(commentsStr+" ",normal,false));
        orderInfo.addCell(makePhrase(orderD.getComments(),normal,true));


        header.addCell(orderInfo);
        document.add(header);
    }


    public int getSkuColumnLength(CleanwiseUser appUser, OrderItemDescDataVector items, StoreData pStore){
        int result = SKU_COLUMN_WDTH_DEFAULT;
        for (int j=0; j<items.size(); j++) {
          OrderItemDescData item = (OrderItemDescData)items.get(j);
          String sku = Utility.getActualSkuNumber(pStore.getStoreType().getValue(),item.getOrderItem());
          int len = sku.length();
          if (len > result) {
              result = len;
          }
        }
        return result;
    }

    public int getMfgSkuColumnLength(CleanwiseUser appUser, OrderItemDescDataVector items, StoreData pStore){
        int result = MFG_SKU_COLUMN_WDTH_DEFAULT;
        for (int j=0; j<items.size(); j++) {
          OrderItemDescData item = (OrderItemDescData)items.get(j);
          String sku = item.getOrderItem().getManuItemSkuNum();
          int len = sku.length();
          if (len > result) {
            result = len;
          }
        }
        return result;
    }


    public void sortItems(OrderItemDescDataVector items) {
        // sort by clw_order_item.CUST_LINE_NUM
        Collections.sort(items, CUST_LINE_NUM);
    }

    static final Comparator CUST_LINE_NUM = new Comparator() {
            public int compare(Object o1, Object o2)
            {
                int num1 = ((OrderItemDescData)o1).getOrderItem().getCustLineNum();
                int num2 = ((OrderItemDescData)o2).getOrderItem().getCustLineNum();
                return num1 - num2;
            }
        };

}

