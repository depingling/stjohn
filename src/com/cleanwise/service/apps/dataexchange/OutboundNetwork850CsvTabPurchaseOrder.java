package com.cleanwise.service.apps.dataexchange;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

import org.dom4j.io.OutputFormat;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

public class OutboundNetwork850CsvTabPurchaseOrder extends InterchangeOutboundSuper implements OutboundTransaction{
	protected Logger log = Logger.getLogger(this.getClass());
	private StringBuffer orderDoc;
    private final OutputFormat outputFormat;
 //   String mEdiRoutingCd;
 //   protected FreightHandlerView mShipVia;
//    private static final BigDecimal ZERO = new BigDecimal(0);
    
    public OutboundNetwork850CsvTabPurchaseOrder(){
        seperateFileForEachOutboundOrder = true;
        outputFormat = new OutputFormat();
        outputFormat.setIndent(true);
        outputFormat.setIndentSize(4);
        outputFormat.setNewlines(true);
    }
    
    
    
    
    /*
    private String getProperty(TradingPropertyMapDataVector properties, String key) {
        for (int i = 0; properties != null && i < properties.size(); i++) {
            TradingPropertyMapData item = (TradingPropertyMapData) properties.get(i);
            if (RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE.equals(item.getEntityProperty())
            && RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP.equals(item.getTradingPropertyMapCode())
            && key.equals(item.getPropertyTypeCd())) {
                return item.getHardValue();
            }
        }
        return null;
    }
    */
    
    public void buildTransactionContent()
    throws Exception {
        orderDoc = new StringBuffer();
        // Building header
        currOrder = currOutboundReq.getOrderD();
        PurchaseOrderData poD = currOutboundReq.getPurchaseOrderD();      
        String oNum = currOrder.getOrderNum();
        oNum = paddZeroLeft(oNum,8);

        getTranslator().setOutputFileName("NSC"+oNum+".ord");
        PropertyDataVector acctPropDV = currOutboundReq.getAccountProperties();
        String custMaj = null;
        for(Iterator iter = acctPropDV.iterator(); iter.hasNext();) {
            PropertyData pD = (PropertyData) iter.next();
            if("CUST_MAJ".equals(pD.getShortDesc())) {
                custMaj = pD.getValue();
                break;
            }
        }
        custMaj = paddZeroLeft(custMaj,3);
        
        PropertyDataVector sitePropDV = currOutboundReq.getSiteProperties();        
        String siteRefNum = Utility.getPropertyValue(sitePropDV,RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
        siteRefNum = paddZeroLeft(siteRefNum,10);
                
        String customerNumber = "NSC" + custMaj+siteRefNum + "0000";
        String orderEntryDateS = "";
        String orderEntryTimeS = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        /*
        if(revDate!=null) {
            orderEntryDateS = dateFormat.format(revDate);
            if(currOrder.getRevisedOrderTime()!=null) {
                orderEntryTimeS = timeFormat.format(currOrder.getRevisedOrderTime());
            } else {
                orderEntryTimeS = timeFormat.format(revDate);
            }
        } else {
            if(currOrder.getOriginalOrderDate()!=null) {
                orderEntryDateS = dateFormat.format(currOrder.getOriginalOrderDate());
            }
            if(currOrder.getOriginalOrderTime()!=null) {
                orderEntryTimeS = timeFormat.format(currOrder.getOriginalOrderTime());
            }
        }
        */
        Date poDate = poD.getAddDate();
        orderEntryDateS = dateFormat.format(poDate);
        orderEntryTimeS = timeFormat.format(poDate);
        
        String custPoNum = currOrder.getRequestPoNum();
        if("N/A".equals(custPoNum)) {
            custPoNum = "   ";
        }
        custPoNum = paddSpace(custPoNum,20,false);
                
        String requestedShipDate = "          "; //10 spaces
        double smallOrderFee = 0;
        double fuelSurcharge = 0;
                
        OrderMetaDataVector omdv = currOutboundReq.getOrderMetaDV();
        if(omdv != null){
            for(Iterator iter=omdv.iterator(); iter.hasNext();){
                OrderMetaData opd = (OrderMetaData) iter.next();
                if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.REQUESTED_SHIP_DATE.equals(opd.getName())){
                    try{
                        requestedShipDate = dateFormat.format(dateFormat.parse(opd.getValue()));
log.info("OutboundNetwork850CsvTabPurchaseOrder tttttttttttttttttt requestedShipDate: "+requestedShipDate);                
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                } else if("FUEL_SURCHARGE".equals(opd.getName())){
                    try{
                        fuelSurcharge = Double.parseDouble(opd.getValue());
                    }catch(Exception e){}
                } else if("SMALL_ORDER_FEE".equals(opd.getName())){
                    try{
                        smallOrderFee = Double.parseDouble(opd.getValue());
                    }catch(Exception e){}
                }
            }
        }  
                
        
        orderDoc.append("H"); //Header
        orderDoc.append(oNum); // Order number
        //orderDoc.append('\t');
        orderDoc.append(customerNumber); //Customer number (account + site)
        //orderDoc.append('\t');
        orderDoc.append(orderEntryDateS);
        //orderDoc.append('\t');
        orderDoc.append(orderEntryTimeS);
        //orderDoc.append('\t');
        orderDoc.append(requestedShipDate); //requested delivery date
        //orderDoc.append('\t');
        orderDoc.append(custPoNum); //Customer's PO numbere (if provided)
        //orderDoc.append('\t');
        orderDoc.append(paddSpace(null,25,false)); //Distributor order number (not used)
        
        
        // Items 
                
        items = currOutboundReq.getOrderItemDV();
        if(items!=null) {
        	
        	//Tax
        	BigDecimal totalTax = new BigDecimal(0);
        	String taxableS = "N";
        	String totalTaxS = "";
        	for (Iterator iter=items.iterator(); iter.hasNext();) {
        		OrderItemData oid = (OrderItemData) iter.next();
        	
        		if(Utility.isSet(oid.getTaxExempt())){
        			
        			if(oid.getTaxExempt().equalsIgnoreCase("false")){
        				taxableS = "Y";
        				
        				if(Utility.isSet(oid.getTaxAmount())){
        					totalTax = totalTax.add(oid.getTaxAmount());
        				}
        			}else{
        				taxableS = "N";
        			}
        		}
        	}
        	
        	orderDoc.append(taxableS);
        	if(totalTax!=null && totalTax.compareTo(new BigDecimal(0))>0){
        		totalTaxS = totalTax.toString();
        	}
        	orderDoc.append(paddSpace(totalTaxS,8,false));
        	orderDoc.append("\r\n");
        	
            for (Iterator iter=items.iterator(); iter.hasNext();) {
                currItem = (OrderItemData) iter.next();
                String distSkuNum = currItem.getDistItemSkuNum();
                distSkuNum = paddSpace(distSkuNum,20,false);
                
                String qty = ""+currItem.getTotalQuantityOrdered();
                qty = paddZeroLeft(qty,4);
                
                String uom = currItem.getDistItemUom();
                uom = paddSpace(uom,3,false);
                
                BigDecimal price = currItem.getDistItemCost();
                String priceS = "";
                if(price!=null) {
                    priceS = price.setScale(2,BigDecimal.ROUND_HALF_UP).toString();
                }
                priceS = paddSpace(priceS,9,true);
                
                orderDoc.append("D"); //detail
                //orderDoc.append('\t');
                orderDoc.append(oNum); //order number
                //orderDoc.append('\t');
                orderDoc.append(distSkuNum); //NSC item number
                //orderDoc.append('\t');
                orderDoc.append(qty); //quantity ordered
                //orderDoc.append('\t');
                orderDoc.append("N"); //Split Indicator: Constant
                //orderDoc.append('\t');
                orderDoc.append(uom); //Unit of measure
                //orderDoc.append('\t');
                orderDoc.append(paddSpace(null,20,true)); //Original item number
                //orderDoc.append('\t');
                orderDoc.append("0000"); //Original quantity
                //orderDoc.append('\t');
                orderDoc.append("N"); //Original split
                //orderDoc.append('\t');
                orderDoc.append(priceS); //Pirce
                orderDoc.append("\r\n");            
///////////////////
                currItem.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
                appendIntegrationRequest(currItem); // for update the status
/////////////////////                
            }
            
            if(fuelSurcharge>0.009999 || fuelSurcharge<-0.009999) {
            	buildAddOnCharge(orderDoc, oNum, "998100", new BigDecimal(fuelSurcharge).setScale(2,BigDecimal.ROUND_HALF_UP));
            }
            if(smallOrderFee>0.009999 || smallOrderFee<-0.009999) {
            	buildAddOnCharge(orderDoc, oNum, "998812", new BigDecimal(smallOrderFee).setScale(2,BigDecimal.ROUND_HALF_UP));
            }
            if (currOutboundReq.getDiscount() != null) {
            	double discount = currOutboundReq.getDiscount().doubleValue();
            	if(discount>0.009999 || discount<-0.009999) {
            		if (discount > 0.0)
            			discount = discount*(-1);
                	buildAddOnCharge(orderDoc, oNum, "998813", new BigDecimal(discount).setScale(2,BigDecimal.ROUND_HALF_UP));
                }            	
            }
        }

        //Special instructions
        String comments = getComments();
        
        if(Utility.isSet(comments)) {
        	int len = comments.length();
        	for (int i = 0, ix = 1; i < len; i+=62){
        		int j = Math.min(len, i+62);
        		orderDoc.append("I"); //instructions
                orderDoc.append(oNum); //Order number
                orderDoc.append(Utility.padLeft(ix++, '0', 2)); //Sequence
                String temp = Utility.padRight(comments.substring(i, j), ' ', 62);;
                orderDoc.append(temp);
                orderDoc.append("\r\n"); 
        	} 
        }
        
////////////////////////
        currOutboundReq.getPurchaseOrderD().setPurchaseOrderStatusCd(
                RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
        appendIntegrationRequest(currOutboundReq.getPurchaseOrderD());
        /*
        transactionD.setKeyString("OrderNum: " + currOrder.getOrderNum() +
                ", ErpOrderNumber: " + currOrder.getErpOrderNum() +
                ", ErpPoNumber: " + currItem.getErpPoNum() +
                ", CustomerPoNumber: " + currOrder.getRequestPoNum() +
                ", mEdiRoutingCd: " + mEdiRoutingCd);
        */
///////////////////////////        
        getTranslator().writeOutputStream(orderDoc.toString());
        log.info(orderDoc.toString());
        
    }
    private void buildAddOnCharge(StringBuffer orderDoc, String oNum, String addOnSku, BigDecimal amount) {
    	addOnSku = paddSpace(addOnSku,20,false);                
        String qty = "1";
        qty = paddZeroLeft(qty,4);
        
        String uom = "EA";
        uom = paddSpace(uom,3,false);
        
        String priceS = amount.setScale(2,BigDecimal.ROUND_HALF_UP).toString();                
        priceS = paddSpace(priceS,9,true);
        
        orderDoc.append("D"); //detail
        orderDoc.append(oNum); //order number
        orderDoc.append(addOnSku); //NSC item number
        orderDoc.append(qty); //quantity ordered
        orderDoc.append("N"); //Split Indicator: Constant
        orderDoc.append(uom); //Unit of measure
        orderDoc.append(paddSpace(null,20,true)); //Original item number
        orderDoc.append("0000"); //Original quantity
        orderDoc.append("N"); //Original split
        orderDoc.append(priceS); //Pirce
        orderDoc.append("\r\n");
	}
	char[] paddingA = null;
    int maxLen = 100;
    String paddSpace(String pStr, int pLen, boolean leftFl) {
        if(paddingA==null || pLen>maxLen) {
            if(pLen>maxLen) maxLen = pLen + 1;
            paddingA = new char[maxLen];
            for(int ii=0; ii<maxLen; ii++) {
                paddingA[ii] = ' ';
            }            
        }
        if(pStr==null) return new String(paddingA,0,pLen);
        if(pStr.length()>=pLen) return pStr;
        if(leftFl) {
            return (new String(paddingA,0,pLen-pStr.length()))+pStr;
        }
        return pStr+(new String(paddingA,0,pLen-pStr.length()));
    }
    
    char[] paddingZeroA = {'0','0','0','0','0','0','0','0','0','0'};
    int maxZeroLen = 10;
    String paddZeroLeft(String pStr, int pLen) {
        if(pLen>maxZeroLen) {
            maxZeroLen = pLen + 1;
            paddingZeroA = new char[maxZeroLen];
            for(int ii=0; ii<maxZeroLen; ii++) {
                paddingZeroA[ii] = '0';
            }            
        }
        if(pStr==null) return new String(paddingZeroA,0,pLen);
        if(pStr.length()>=pLen) return pStr;
        return (new String(paddingZeroA,0,pLen-pStr.length()))+pStr;        

    }
    
    private String getComments(){
    	String msg = currOrder.getComments();
        PropertyData pd = Utility.getProperty(currOutboundReq.getSiteProperties(),
                RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG);
        String siteComments = null;
        if(pd != null){
            siteComments = pd.getValue();
        }
        if (!Utility.isSet(msg)){
            msg = siteComments;
        }else{
            if(Utility.isSet(siteComments)){
                msg = msg + ". " + siteComments;
            }
        }
        return msg;
        
    }
}
