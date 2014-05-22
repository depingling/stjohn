package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.value.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.math.BigDecimal;


public class OrderNotificationGeneratorXpedxDef 
extends OrderNotificationGeneratorBase 
implements FileGenerator {

    protected String text= "Order has been placed.";

    public String genTXT(OrderInfoDataView data) {
        StringBuffer sb = new StringBuffer();
//        writeHeaderTXT(sb,data.getOrderInfo());
        writeHeaderTXT(sb,data, "", "");
        writeBodyTXT(sb,data.getItems());
        writeFooterTXT(sb,data);
        return sb.toString();
    }

    public void writeTitle(StringBuffer sb) {}

    public OrderInfoDataView getData(int orderId) throws Exception {
        APIAccess factory = new APIAccess();
        Order orderEjb = factory.getOrderAPI();
        return orderEjb.getOrderInfoData(orderId);
    }

    public void writeHeaderTXT(StringBuffer sb, OrderInfoDataView pOrderInfo, String subNote, String title) {
        OrderInfoView orderInfo = pOrderInfo.getOrderInfo();
        nextLine(sb);
        nextLine(sb);

        if (orderInfo.getRefOrderId() > 0) {
            line.append(align("Order Modified and Approved", CENTER));
            nextLine(sb);
            line.append(align("Order #" + orderInfo.getOrderNum() + " replaces order #" + orderInfo.getRefOrderNum(), CENTER));
        } else {
            line.append(align("Order Notification", CENTER));
            nextLine(sb);
            line.append(align("Order #" + orderInfo.getOrderNum(), CENTER));
        }

        nextLine(sb);
        line.append(align("Placed by: "+orderInfo.getPlacedBy()+" on "+orderInfo.getOrderDate(),CENTER));
        nextLine(sb);
        nextLine(sb);
        nextLine(sb);
        String comments = orderInfo.getComments();
        comments=comments==null?"Comments: ":"Comments: "+comments;
        line.append(comments);
        nextLine(sb);

    }

    public void writeJHeaderTXT(StringBuffer sb, OrderInfoView orderInfo) {

        nextLine(sb);
        line.append(getCharLine('*',DOC_LENGTH));
        nextLine(sb);

        String jHeadetTxt = "Order Notification";//default
        if(orderInfo.getRefOrderId()>0){
            jHeadetTxt = "Order Modified and Approved";
        }

        String tempStr=normalizeString(jHeadetTxt,' ',DOC_LENGTH,CENTER);
        tempStr="*"+tempStr.substring(1);
        tempStr=tempStr.substring(0,tempStr.length()-1)+"*";
        line.append(tempStr);
        nextLine(sb);

        jHeadetTxt = "Order #" + orderInfo.getOrderNum();
        if (orderInfo.getRefOrderId() > 0) {
            jHeadetTxt = "Order #" + orderInfo.getOrderNum() + " replaces order #" + orderInfo.getRefOrderNum();
        }

        tempStr=normalizeString(jHeadetTxt,' ',DOC_LENGTH,CENTER);
        tempStr="*"+tempStr.substring(1);
        tempStr=tempStr.substring(0,tempStr.length()-1)+"*";
        line.append(tempStr);
        nextLine(sb);

        tempStr=normalizeString("Placed by: "+orderInfo.getPlacedBy()+" on "+orderInfo.getOrderDate(),' ',DOC_LENGTH,CENTER);
        tempStr="*"+tempStr.substring(1);
        tempStr=tempStr.substring(0,tempStr.length()-1)+"*";
        line.append(tempStr);
        nextLine(sb);

        line.append(getCharLine('*',DOC_LENGTH));
        nextLine(sb);

        nextLine(sb);

        String comments = orderInfo.getComments();
        comments=comments==null?"Comments: ":"Comments: "+comments;
        line.append(comments);
        nextLine(sb);

    }

    public void writeBodyTXT(StringBuffer sb, ItemInfoViewVector items) {

        String headerTable = "";

        line.append(getCharLine('_',DOC_LENGTH));
        nextLine(sb);
        nextLine(sb);

        headerTable += normalizeString("Sku",' ',9,LEFT);
        headerTable += normalizeString("Product Name",' ',22,CENTER);
        headerTable += normalizeString("Pack",' ',5,CENTER);
        headerTable += normalizeString("Uom",' ',5,CENTER);
        headerTable += normalizeString("Price",' ',9,CENTER);
        headerTable += normalizeString("Qty",' ',5,CENTER);
        headerTable += normalizeString("Ext.Price",' ',12,RIGTH);

        line.append(align(headerTable,CENTER));
        nextLine(sb);

        line.append(getCharLine('_',DOC_LENGTH));
        nextLine(sb);
        nextLine(sb);

        PairViewVector columnParam=new PairViewVector();

        columnParam.add(new PairView(new Integer(9), new Integer(LEFT)));
        columnParam.add(new PairView(new Integer(22), new Integer(CENTER)));
        columnParam.add(new PairView(new Integer(5), new Integer(CENTER)));
        columnParam.add(new PairView(new Integer(5), new Integer(CENTER)));
        columnParam.add(new PairView(new Integer(9), new Integer(CENTER)));
        columnParam.add(new PairView(new Integer(5), new Integer(CENTER)));
        columnParam.add(new PairView(new Integer(12), new Integer(RIGTH)));

        Iterator it = items.iterator();
        while (it.hasNext()) {
            ItemInfoView item = (ItemInfoView) it.next();
            ArrayList colsArray = new ArrayList();
            colsArray.add(parseDelim(getActualSkuNum(item)," ", 10-TABLE_COLUMN_SPACE));
            ArrayList itemNameAL = parseDelim(item.getItemName()," ", 20-TABLE_COLUMN_SPACE);
            colsArray.add(itemNameAL);
            colsArray.add(parseDelim(item.getPack()," ", 5-TABLE_COLUMN_SPACE));
            colsArray.add(parseDelim(item.getUom(),null, 5-TABLE_COLUMN_SPACE));
            BigDecimal serviceFee = item.getServiceFee();
            BigDecimal itemCost = getActualCost(item);
            if(serviceFee!=null && itemCost!=null) {
               itemCost = itemCost.subtract(serviceFee);
            }
            colsArray.add(parseDelim(itemCost.toString(),null, 10-TABLE_COLUMN_SPACE));
            colsArray.add(parseDelim(item.getQty().toString(),null, 5-TABLE_COLUMN_SPACE));
            colsArray.add(parseDelim(item.getQty().multiply(itemCost).toString(),null, 12-TABLE_COLUMN_SPACE));          
            writeArrayAsTableStyle(colsArray,sb,columnParam,CENTER);

            nextLine(sb);
        }
        line.append(getCharLine('_',DOC_LENGTH));
        nextLine(sb);
    }


    public void writeFooterTXT(StringBuffer sb, OrderInfoDataView orderInfo) {
        writeSummaryInfo(sb,orderInfo.getOrderInfo(), orderInfo.getItems());
        writeAddressInfo(sb,orderInfo.getBillingAddress(),orderInfo.getShippingAddress());
    }

    private void writeAddressInfo(StringBuffer sb, AddressInfoView billingAddress, AddressInfoView shippingAddress) {

        String headerTable = "";
        nextLine(sb);
        nextLine(sb);
        headerTable += normalizeString("Billing Information", ' ', DOC_LENGTH/2, LEFT);
        headerTable += normalizeString("Shipping Information",' ',DOC_LENGTH/2,LEFT);

        line.append(align(headerTable,CENTER));
        nextLine(sb);
        nextLine(sb);
        ArrayList colsArray = new ArrayList();
        PairViewVector columnParam=new PairViewVector();
        columnParam.add(new PairView(new Integer(DOC_LENGTH/2-TABLE_COLUMN_SPACE), new Integer(LEFT)));
        columnParam.add(new PairView(new Integer(DOC_LENGTH/2-TABLE_COLUMN_SPACE), new Integer(LEFT)));
        colsArray.add(parseDelim(billingAddress.getAccountErpNum()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        colsArray.add(parseDelim(shippingAddress.getAccountErpNum()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        writeArrayAsTableStyle(colsArray,sb,columnParam,CENTER);
        colsArray.clear();

        colsArray.add(parseDelim(billingAddress.getStreetAddress()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        colsArray.add(parseDelim(shippingAddress.getStreetAddress()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        writeArrayAsTableStyle(colsArray,sb,columnParam,CENTER);
        colsArray.clear();

        colsArray.add(parseDelim(billingAddress.getCity()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        colsArray.add(parseDelim(shippingAddress.getCity()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        writeArrayAsTableStyle(colsArray,sb,columnParam,CENTER);
        colsArray.clear();

        colsArray.add(parseDelim(billingAddress.getStateProvinceCd()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        colsArray.add(parseDelim(shippingAddress.getStateProvinceCd()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        writeArrayAsTableStyle(colsArray,sb,columnParam,CENTER);
        colsArray.clear();

        colsArray.add(parseDelim(billingAddress.getPostalCode()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        colsArray.add(parseDelim(shippingAddress.getPostalCode()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        writeArrayAsTableStyle(colsArray,sb,columnParam,CENTER);
        colsArray.clear();

        colsArray.add(parseDelim(billingAddress.getCountry()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        colsArray.add(parseDelim(shippingAddress.getCountry()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        writeArrayAsTableStyle(colsArray,sb,columnParam,CENTER);
        colsArray.clear();

        nextLine(sb);
    }

    private void writeSummaryInfo(StringBuffer sb, OrderInfoView orderInfo, ItemInfoViewVector items) {
        BigDecimal orderServiceFee = new BigDecimal(0);
        if(items!=null) {
            for (Iterator it=items.iterator(); it.hasNext();) {
                ItemInfoView item = (ItemInfoView) it.next();
                BigDecimal serviceFee = item.getServiceFee();
                BigDecimal qty = item.getQty();
                if(serviceFee!=null && qty != null) {
                   serviceFee = serviceFee.multiply(qty);
                   orderServiceFee = orderServiceFee.add(serviceFee);
                }
            }
        }
        nextLine(sb);
        int maxLenPh = "Misc Charge: ".length();
        int maxDigitinLine=12;
        BigDecimal subtotal = orderInfo.getSubtotal();
        subtotal = subtotal.subtract(orderServiceFee);
        subtotal = subtotal.setScale(2, BigDecimal.ROUND_HALF_UP);
        line.append(align(normalizeString("SubTotal: ",' ',maxLenPh,LEFT)
                +normalizeString(subtotal.toString(),' ',maxDigitinLine,LEFT),
                RIGTH));

        nextLine(sb);

        line.append(align(normalizeString("Freight: ",' ',maxLenPh,LEFT)
                +normalizeString(orderInfo.getFreight().toString(),' ',maxDigitinLine,LEFT),
                RIGTH));
        nextLine(sb);

        line.append(align(normalizeString("Misc Charge: ",' ',maxLenPh,LEFT)
                +normalizeString(orderInfo.getMiscCharge().toString(),' ',maxDigitinLine,LEFT),
                RIGTH));
        nextLine(sb);

        line.append(align(normalizeString("Service Fee: ",' ',maxLenPh,LEFT)
                +normalizeString(orderServiceFee.toString(),' ',maxDigitinLine,LEFT),
                RIGTH));
        nextLine(sb);

        line.append(align(normalizeString("Tax: ",' ',maxLenPh,LEFT)
                +normalizeString(orderInfo.getTax().toString(),' ',maxDigitinLine,LEFT),
                RIGTH));
        nextLine(sb);

        BigDecimal discount = orderInfo.getDiscount();
        if (discount != null && discount.doubleValue() != 0) {
            line.append(align(normalizeString("Discount: ",' ',maxLenPh,LEFT)
                    +normalizeString(discount.toString(),' ',maxDigitinLine,LEFT),
                    RIGTH));
            nextLine(sb);
        }

        BigDecimal total = orderInfo.getToatal();
        if (discount != null && discount.doubleValue() != 0) {
            total = total.add(discount);
        }

        line.append(align(normalizeString("Total: ",' ',maxLenPh,LEFT)
                +normalizeString(total.toString(),' ',maxDigitinLine,LEFT),
                RIGTH));
        nextLine(sb);
        line.append(align(getCharLine('_',maxDigitinLine+maxLenPh),RIGTH));
        nextLine(sb);
    }

    public static void main (String argv[]) {   //for test
        try {
            OrderNotificationGenerator generator = new OrderNotificationGenerator();
            generator.generate(new Integer(233899),"G:/test/tmp/order_txt_233899.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

