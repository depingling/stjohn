package com.cleanwise.service.api.pipeline;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.OrderAddOnChargeDataAccess;
import com.cleanwise.service.api.eventsys.FileAttach;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.EventData;
import com.cleanwise.service.api.value.EventEmailDataView;
import com.cleanwise.service.api.value.EventEmailView;
import com.cleanwise.service.api.value.OrderAddOnChargeData;
import com.cleanwise.service.api.value.OrderAddOnChargeDataVector;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderMetaData;
import com.cleanwise.service.api.value.OrderMetaDataVector;
import com.cleanwise.service.apps.ApplicationsEmailTool;

public class NscUniccoOrderNotificationPipeline implements OrderPipeline {

    private  static final String className="NscUniccoOrderNotificationPipeline";

    public OrderPipelineBaton process(OrderPipelineBaton pBaton, OrderPipelineActor pActor, Connection pCon, APIAccess pFactory) 
    throws PipelineException 
    {

        String message;
        File[] attachments;
        File tmp = null;
        Order orderEjb = null;

        try {
            tmp = File.createTempFile("Attachment", ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            OrderData orderData = pBaton.getOrderData();

            Event eventEjb = pFactory.getEventAPI();
            User userEjb = pFactory.getUserAPI();
            Account accountEjb = pFactory.getAccountAPI();
            Site siteEjb = pFactory.getSiteAPI();
            EmailClient emailClientEjb = pFactory.getEmailClientAPI();

            int storeId = orderData.getStoreId();
            int accountId = orderData.getAccountId();
            int siteId = orderData.getSiteId();
            
            String siteEmails = pBaton.getBusEntityPropertyCached(siteId, "EMAIL",pCon);
            if(siteEmails==null || !siteEmails.contains("@")) {
                pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
                return pBaton;
            }
            siteEmails = siteEmails.replace(';', ',');
            
            
            String custMaj = 
                    pBaton.getBusEntityPropertyCached(accountId, 
                        RefCodeNames.PROPERTY_TYPE_CD.CUST_MAJ, pCon);
            String locationNumber = 
                    pBaton.getBusEntityPropertyCached(siteId, 
                        RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER, pCon);            
            
            String accountName = pBaton.getBillToData().getShortDesc();            
            
            StringBuffer messageSB = new StringBuffer();
            
            OrderAddOnChargeDataVector addOnCharges = new OrderAddOnChargeDataVector();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderAddOnChargeDataAccess.ORDER_ID, orderData.getOrderId());
            addOnCharges = OrderAddOnChargeDataAccess.select(pCon, crit);
            
            //remove cancelled items
            OrderItemDataVector oiDV = pBaton.getOrderItemDataVector();
            Iterator it = oiDV.iterator();
            while(it.hasNext()){
            	OrderItemData oiD = (OrderItemData)it.next();
            	if(oiD.getOrderItemStatusCd().equalsIgnoreCase(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED)){
            		it.remove();
            	}
            }
            
            if(oiDV!=null && oiDV.size()>0){
            	//sort based on cust line num
            	Collections.sort(oiDV, ORDER_ITEM_ERP_LINE_NUM_COMPARE);
            }
            
            generateMessage(messageSB,
                pBaton.getOrderData(), pBaton.getShipToData(),
                pBaton.getOrderMetaDataVector(), oiDV,
                accountName, custMaj, locationNumber, addOnCharges);
            
            
                

//            String fromEmail = 
//                    BusEntityDAO.getOutboundFromEmailAddress(pCon,accountId,storeId);
			String fromEmail = pFactory.getAccountAPI().getDefaultEmail(
					accountId, storeId);

            String subject = "Order "+pBaton.getOrderData().getOrderNum()+" has been placed";

            message = messageSB.toString();
                        
            tmp = File.createTempFile("Attachment", ".txt");
            writeToFile(message,tmp);

            attachments = new File[]{tmp};
            FileAttach[] fileAttach;

            if(attachments!=null){
                fileAttach = (new ApplicationsEmailTool()).fromFilesToAttachs(attachments);
            } else{
                fileAttach = new FileAttach[0];
            }

//            EventData eventData = new EventData(0, Event.STATUS_READY, Event.TYPE_EMAIL, null, null, 1);
//                        eventData = eventEjb.addEventToDB(eventData);
            EventData eventData = EventData.createValue();
            eventData.setStatus(Event.STATUS_READY);
            eventData.setType(Event.TYPE_EMAIL);
            eventData.setAttempt(1);
            
            EventEmailDataView eventEmailData = new EventEmailDataView();
            eventEmailData.setEventEmailId(0);
            eventEmailData.setToAddress(siteEmails);
            eventEmailData.setFromAddress(fromEmail);
            eventEmailData.setSubject(subject);
            eventEmailData.setText(message);
            eventEmailData.setAttachments(fileAttach);
            eventEmailData.setEventId(eventData.getEventId());
            eventEmailData.setEmailStatusCd(Event.STATUS_READY);
            eventEmailData.setModBy("OrderNotification");
            eventEmailData.setAddBy("OrderNotification");
//            eventEjb.addEventEmail(eventEmailData);

            if(!emailClientEjb.wasThisEmailSent(subject, siteEmails)){
            	EventEmailView eev = new EventEmailView(eventData, eventEmailData); 
            	eventEjb.addEventEmail(eev, "OrderNotification");
            }
            
            pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
         

            return pBaton;

        } catch (Exception e) {
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
    }

    private void writeToFile(String text, File file) throws Exception {
        BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(file));
        outStream.write(text.getBytes());
        outStream.flush();
        outStream.close();

    }

    
    private void generateMessage(StringBuffer messageSB,
                OrderData order, OrderAddressData shipTo,
                OrderMetaDataVector orderMetaV, OrderItemDataVector items,
                String accountName, String accountMaj, String siteRefNum,
                OrderAddOnChargeDataVector addOnCharges
    ) 
    {
        //Header
        messageSB.append("\r\n");
        
        String line1 = 
                "This email is auto generated. DO NOT REPLY to this mail. " +
                "If you have questions about this order please contact your sales representative.";
        messageSB.append(line1);
        messageSB.append("\r\n");
        messageSB.append("\r\n");
        messageSB.append("------------------------------------------------\r\n");
        messageSB.append("Account #: "+accountMaj+"\r\n");
        messageSB.append("Account Name: "+accountName+"\r\n");
        messageSB.append("\r\n");
        messageSB.append("Your order will be shipped to the following location:\r\n");
        messageSB.append("------------------------------------------------\r\n");
        messageSB.append("Site Name: "+shipTo.getShortDesc()+"\r\n");
        messageSB.append("Site #: "+siteRefNum+"\r\n");
        messageSB.append("Address: "+shipTo.getAddress1()+"\r\n");
        messageSB.append("City: "+shipTo.getCity()+"\r\n");
        messageSB.append("State: "+shipTo.getStateProvinceCd()+"\r\n");
        messageSB.append("Zip: "+shipTo.getPostalCode()+"\r\n");
        messageSB.append("------------------------------------------------\r\n");
        messageSB.append("\r\n");
        messageSB.append("Your Web Order #: "+order.getOrderNum()+"\r\n");
        String poNum = order.getRequestPoNum();
        if (poNum == null) {
            poNum = "";
        }
        messageSB.append("Your Purchase Order #:"+poNum+"\r\n");
        messageSB.append("------------------------------------------------\r\n");
        messageSB.append("Status:\r\n");
        Date orderDate = order.getRevisedOrderDate();
        Date orderTime = order.getRevisedOrderTime();
        //if(orderDate==null) {
            orderDate = order.getOriginalOrderDate();
            orderTime = order.getOriginalOrderTime();
        //}
        String orderDateTimeS = "Unknown";
        if(orderDate!=null) {
            if(orderTime!=null) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String orderDateS = sdf.format(orderDate);
                SimpleDateFormat stf = new SimpleDateFormat("h:mm a z");
                String orderTimeS = stf.format(orderTime);
                orderDateTimeS = orderDateS+" "+orderTimeS;
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm a z");
                orderDateTimeS = sdf.format(orderDate);
            }
        }
        messageSB.append("Date Placed: "+orderDateTimeS+"\r\n");
        String deilveryDateS = "Unknown";
        BigDecimal fuelSurcharge = new BigDecimal(0);
        BigDecimal smallOrderFee = new BigDecimal(0);
        BigDecimal discount = new BigDecimal(0);
        if(orderMetaV!=null) {
            for(Iterator iter=orderMetaV.iterator(); iter.hasNext();) {
                OrderMetaData omD = (OrderMetaData) iter.next();                
                if("Requested Ship Date".equals(omD.getName())) {
                    String sss = omD.getValue();
                    if(sss!=null) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                            deilveryDateS = sdf.format(sdf.parse(sss));
                        } catch (Exception exc){}
                    }
                }
                if("FUEL_SURCHARGE".equals(omD.getName())) {
                    String sss = omD.getValue();
                    if(sss!=null) {
                        try {
                            double amt = Double.parseDouble(sss);
                            fuelSurcharge = (new BigDecimal(amt)).setScale(2, BigDecimal.ROUND_HALF_UP);
                        } catch (Exception exc){}
                    }
                }
                if("SMALL_ORDER_FEE".equals(omD.getName())) {
                    String sss = omD.getValue();
                    if(sss!=null) {
                        try {
                            double amt = Double.parseDouble(sss);
                            smallOrderFee = (new BigDecimal(amt)).setScale(2, BigDecimal.ROUND_HALF_UP);
                        } catch (Exception exc){}
                    }
                }
            }
        }
        
        if(addOnCharges!=null && addOnCharges.size()>0){
        	for(int j=0; j<addOnCharges.size(); j++){
        		OrderAddOnChargeData charge = (OrderAddOnChargeData)addOnCharges.get(j);
        		if(charge.getDistFeeChargeCd().equals(RefCodeNames.CHARGE_CD.DISCOUNT)){
        			discount = charge.getAmount();      			
        			if(discount!=null){
        				discount = discount.setScale(2, BigDecimal.ROUND_HALF_UP);
        			}
        		}
        	}
        }
        messageSB.append("Routing Date: "+deilveryDateS+"\r\n");
        
        //Items total
        BigDecimal orderTotal = new BigDecimal(0);
        BigDecimal itemsTaxTotal = new BigDecimal(0);
        for(Iterator iter = items.iterator(); iter.hasNext();) {
            OrderItemData oiD = (OrderItemData) iter.next();
            int qty = oiD.getTotalQuantityOrdered();
            BigDecimal extendedPrice = oiD.getCustContractPrice().multiply(new BigDecimal(qty)).setScale(2,BigDecimal.ROUND_HALF_UP);
            orderTotal = orderTotal.add(extendedPrice);  
            
            BigDecimal itemTax = oiD.getTaxAmount();
            if (itemTax == null) {
            	itemTax = new BigDecimal(0);
            }
            itemsTaxTotal = itemsTaxTotal.add(itemTax);
        }
        orderTotal = orderTotal.add(fuelSurcharge);            
        orderTotal = orderTotal.add(smallOrderFee);            
        messageSB.append("Sub Total: "+orderTotal.toString()+"\r\n");
        
        //Tax
        String itemsTaxTotalS = "";
        if(itemsTaxTotal!=null && itemsTaxTotal.compareTo(new BigDecimal(0))>0){
        	itemsTaxTotalS = itemsTaxTotal.toString();
        	orderTotal = orderTotal.add(itemsTaxTotal);
        }
        messageSB.append("Tax: "+itemsTaxTotalS+"\r\n");
        
        messageSB.append("Order Total: "+orderTotal.toString()+"\r\n");
        
        //Items detail
        messageSB.append("\r\n");
        messageSB.append("------------------------------------------------\r\n");
        messageSB.append("Contact Name :"
                + Utility.strNN(order.getOrderContactName()) + "\r\n");
        messageSB.append("Contact Phone:"
                + Utility.strNN(order.getOrderContactPhoneNum()) + "\r\n");
        messageSB.append("Contact Email:"
                + Utility.strNN(order.getOrderContactEmail()) + "\r\n");
        messageSB.append("Shipping comments: "+Utility.strNN(order.getComments())+"\r\n");
        messageSB.append("\r\n");
        messageSB.append("Products on this order\r\n");
        messageSB.append("------------------------------------------------\r\n");
        
        messageSB.append("  Item  |  Description                               |    Pack     |   Price    |   Qty   |    Ext. Price\r\n");
        //                12345678 12345678901234567890123456789012345678901234 1234567890123 123456789012 123456789 12345678901234
        for(Iterator iter = items.iterator(); iter.hasNext();) {
            OrderItemData oiD = (OrderItemData) iter.next();
            
            //String custSkuNum = padd(" "+oiD.getCustItemSkuNum(),9);
            String distSkuNum = padd(" "+oiD.getDistItemSkuNum(),9);
            
            String custProdName = padd(oiD.getCustItemShortDesc(),49);
            String pack = padd(oiD.getDistItemPack(),15);
            BigDecimal itemCost = oiD.getCustContractPrice().setScale(2,BigDecimal.ROUND_HALF_UP);
            String itemCostS = padd("$"+itemCost.toString(),14);
            int qty = oiD.getTotalQuantityOrdered();
            String qtyS = padd(""+qty,10);
            BigDecimal extendedPrice = 
                    itemCost.multiply(new BigDecimal(qty)).setScale(2,BigDecimal.ROUND_HALF_UP);
            String extendedPriceS = "$"+extendedPrice.toString();
            String lineS = distSkuNum + custProdName + pack + itemCostS + qtyS + extendedPriceS;
            messageSB.append(lineS+"\r\n");
        }
        if(fuelSurcharge.abs().doubleValue()>=0.009999) {
            String custSkuNum = padd("998100",9);
            String custProdName = padd("FUEL SURCHARGE",49);
            String pack = padd(" ",15);
            String fuelSurchargeS = padd("$"+fuelSurcharge.toString(),14);
            String qtyS = padd(" ",10);
            String lineS = custSkuNum + custProdName + pack + fuelSurchargeS + qtyS + fuelSurchargeS;
            messageSB.append(lineS+"\r\n");
        }        
        if(smallOrderFee.abs().doubleValue()>=0.009999) {
            String custSkuNum = padd("998812",9);
            String custProdName = padd("SMALL ORDER FEE",49);
            String pack = padd(" ",15);
            String fuelSurchargeS = padd("$"+smallOrderFee.toString(),14);
            String qtyS = padd(" ",10);
            String lineS = custSkuNum + custProdName + pack + fuelSurchargeS + qtyS + fuelSurchargeS;
            messageSB.append(lineS+"\r\n");
        } 
        if(discount.abs().doubleValue()>=0.009999){
        	String custSkuNum = padd("998813",9);
            String custProdName = padd("DISCOUNT",49);
            String pack = padd(" ",15);
            String discountS = padd("$"+discount.toString(),14);
            String qtyS = padd(" ",10);
            String lineS = custSkuNum + custProdName + pack + discountS + qtyS + discountS;
            messageSB.append(lineS+"\r\n");
        }
    }

    String paddingString = "                                                                        ";
    private String padd(String val, int len) {
        if(val==null) return val;
        int ll = val.length();
        if(ll<len) {
            val+=paddingString.substring(0,len-ll);
        }
        return val;
    }

	
    private Comparator ORDER_ITEM_ERP_LINE_NUM_COMPARE = new Comparator() {
        public int compare(Object o1, Object o2) {

            OrderItemData t1 = (OrderItemData)o1;
            OrderItemData t2 = (OrderItemData)o2;
            
            int t_line1 = t1.getErpPoLineNum();
            int t_line2 = t2.getErpPoLineNum();
            
            if(t_line1 > t_line2){
            	return 1;
            }else if(t_line1 < t_line2){
            	return -1;
            }else{
            	return 0;
            }
        }
    };

}
