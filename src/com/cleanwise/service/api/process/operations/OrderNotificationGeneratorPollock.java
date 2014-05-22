package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;

import java.math.BigDecimal;
import java.util.*;
import java.text.SimpleDateFormat;


public class OrderNotificationGeneratorPollock extends OrderNotificationGenerator {

    public void writeBodyTXT(StringBuffer sb, ItemInfoViewVector items) {
        String headerTable = "";

        line.append(getCharLine('_', DOC_LENGTH));
        nextLine(sb);
        nextLine(sb);

        headerTable += normalizeString("Sku", ' ', 9, LEFT);
        headerTable += normalizeString("Product Name", ' ', 22, CENTER);
        headerTable += normalizeString("Pack", ' ', 5, CENTER);
        headerTable += normalizeString("Uom", ' ', 5, CENTER);
        headerTable += normalizeString("Price", ' ', 9, CENTER);
        headerTable += normalizeString("Qty", ' ', 5, CENTER);
        headerTable += normalizeString("Ext.Price", ' ', 12, RIGTH);

        line.append(align(headerTable, CENTER));
        nextLine(sb);

        line.append(getCharLine('_', DOC_LENGTH));
        nextLine(sb);
        nextLine(sb);

        PairViewVector columnParam = new PairViewVector();

        columnParam.add(new PairView(9, LEFT));
        columnParam.add(new PairView(22, CENTER));
        columnParam.add(new PairView(5, CENTER));
        columnParam.add(new PairView(5, CENTER));
        columnParam.add(new PairView(9, CENTER));
        columnParam.add(new PairView(5, CENTER));
        columnParam.add(new PairView(12, RIGTH));

        for (Object oItem : items) {
            ItemInfoView item = (ItemInfoView) oItem;

            ArrayList colsArray = new ArrayList();
            colsArray.add(parseDelim(getActualSkuNum(item), " ", 10 - TABLE_COLUMN_SPACE));
            colsArray.add(parseDelim(item.getItemName(), " ", 20 - TABLE_COLUMN_SPACE));
            colsArray.add(parseDelim(item.getPack(), " ", 5 - TABLE_COLUMN_SPACE));
            colsArray.add(parseDelim(item.getUom(), null, 5 - TABLE_COLUMN_SPACE));
            colsArray.add(parseDelim(getActualCost(item).toString(), null, 10 - TABLE_COLUMN_SPACE));
            colsArray.add(parseDelim(item.getQty().toString(), null, 5 - TABLE_COLUMN_SPACE));
            colsArray.add(parseDelim(item.getQty().multiply(getActualCost(item)).toString(), null, 12 - TABLE_COLUMN_SPACE));

            writeArrayAsTableStyle(colsArray, sb, columnParam, CENTER);

            nextLine(sb);
        }
        line.append(getCharLine('_', DOC_LENGTH));
        nextLine(sb);
    }

    public String getSubject(Object data){
       OrderInfoDataView order = (OrderInfoDataView) data;

       Date ruleDate = null;
       IdVector ruleIdV = new IdVector();
       for(Iterator iter=order.getInternalComments().iterator(); iter.hasNext();) {
           OrderPropertyData opD = (OrderPropertyData) iter.next();
           if(ruleDate==null) ruleDate = opD.getAddDate();
           int ruleId = opD.getWorkflowRuleId();
           ruleIdV.add(new Integer(ruleId));
       }
       String ruleDateS = "";
       SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
       if(ruleDate!=null) ruleDateS = sdf.format(ruleDate);

        String subject = " -- Pollock Paper Order approval notification. "+
        " Location: " + order.getOrderInfo().getOrderSiteName() +
            ", Order Number: " + order.getOrderInfo().getOrderNum() +
            " ("+ruleDateS+") -- ";
        return subject;
    }

    public boolean isStoreFromEmail() {
        return true;
    }

    public boolean isUseSiteName() {
        return true;
    }


   public void writeHeaderTXT(StringBuffer sb, OrderInfoDataView orderInfo, String subNote, String title) {
        nextLine(sb);
        line.append("");
        nextLine(sb);
        line.append("");

        OrderInfoView order = orderInfo.getOrderInfo();

        if (order.getOrderStatusCd().equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)) {
            String siteName = order.getOrderSiteName();

            line.append("This Email is to inform you that you have an order for " + siteName + 
                    " that requires your approval.");

            nextLine(sb);
            line.append("");

            String poNum = (order.getPoNumber() == null)?" N/A ":order.getPoNumber();

            AddressInfoView shipAddr = orderInfo.getShippingAddress();
            if (shipAddr == null) {
                shipAddr = new AddressInfoView();
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            String contactName = (order.getContactName() == null)?"":order.getContactName();
            String comments = (order.getComments() == null)?"":order.getComments();

            nextLine(sb);
            line.append("Web Order Number      : " + order.getOrderNum());
            nextLine(sb);
            line.append("Order Date            : " + sdf.format(order.getOrderDate()));
            nextLine(sb);
            line.append("PO Number             : " + poNum);
            nextLine(sb);
            line.append("Location              : " + siteName);
            nextLine(sb);
            line.append("Address1              : " + shipAddr.getAddress1());
            nextLine(sb);
            line.append("State                 : " + shipAddr.getStateProvinceCd());
            nextLine(sb);
            line.append("Postal Code           : " + shipAddr.getPostalCode());
            nextLine(sb);
            line.append("Contact Name (User)   : " + contactName);
            nextLine(sb);
            line.append("Order Total           : " + order.getToatal());
            nextLine(sb);
            line.append("Shipping Comments     :" + comments);
            nextLine(sb);
        }

        nextLine(sb);
        line.append(getCharLine('=',DOC_LENGTH));
    }

    public void writeFooterTXT(StringBuffer sb, OrderInfoDataView orderInfo) {
        String link = "http://pollock.espendwise.com/defst/userportal/logon.do";
        nextLine(sb);
        line.append("To approve this order, go to ");
        line.append(link);
        nextLine(sb);
        line.append("Click on Pending Orders link.");
        nextLine(sb);
    }




}
