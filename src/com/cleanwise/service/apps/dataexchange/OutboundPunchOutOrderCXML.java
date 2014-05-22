/*
 * OutboundPunchOutOrderCXML.java
 *
 * Created on July 28, 2004, 5:39 PM
 */

package com.cleanwise.service.apps.dataexchange;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.view.forms.CheckoutForm;
import java.util.Iterator;
import java.util.Map;
import java.text.*;
import org.dom4j.*;
import java.io.*;
import java.math.*;
/**
 *
 * @author  bstevens
 */
public class OutboundPunchOutOrderCXML extends cXML implements OutboundTransaction{
    private OutboundTranslate mHandler;
    private OutputStream mOutputStream;
    private StringBuffer mReport = new StringBuffer();
    private TradingProfileData mProfile;
    private TradingPartnerData mTradingPartner;
    OutboundEDIRequestDataVector mOutboundTransactionsToProcess;
    OrderData orderD = new OrderData();
    
    public String getLanguage(){
    	String lang = "";
    	if(orderD.getLocaleCd() != null){
    		lang = orderD.getLocaleCd().replace('_','-');
            lang = lang.toLowerCase();
    	}
    	
    	return lang;
    }
    
    public String getCurrencyCode(){
    	return orderD.getCurrencyCd();
    }
    
    public boolean getRequiresSharedSecret(){
    	return false;
    }
    
    private void buildSingleOrderCXMLDoc(OutboundEDIRequestData currReq) throws Exception{
        
        OrderData currOrd = currReq.getOrderD();
        orderD = currOrd;
        
        NumberFormat nf = NumberFormat.getCurrencyInstance(Utility.parseLocaleCode(currOrd.getLocaleCd()));
        //String lang = currOrd.getLocaleCd().replace('_','-');
        //lang = lang.toLowerCase();
        String lang = getLanguage();
        
        Element rootEle = createEmptyCXMLDocument();
        Element hdrEle = rootEle.addElement("Header");
        Element credEle = hdrEle.addElement("From").addElement("Credential");
        credEle.addAttribute("domain",mProfile.getInterchangeSenderQualifier());
        credEle.addElement("Identity").addText(mProfile.getGroupReceiver());//us
        
        credEle = hdrEle.addElement("To").addElement("Credential");
        credEle.addAttribute("domain",mProfile.getInterchangeSenderQualifier());
        credEle.addElement("Identity").addText(mProfile.getGroupSender());//them
       
        credEle = hdrEle.addElement("Sender");
        Element sElem = credEle.addElement("Credential");
        sElem.addAttribute("domain",mProfile.getInterchangeSenderQualifier());
        sElem.addElement("Identity").addText(mProfile.getGroupReceiver());
        if(getRequiresSharedSecret()){
        	sElem.addElement("SharedSecret").addText(mProfile.getSecurityInfo());
        }
        credEle.addElement("UserAgent").addText("cXML 1.0");
        
        Element msgEle = rootEle.addElement("Message");
        Element punchOutOrderEle = msgEle.addElement("PunchOutOrderMessage");
        OrderPropertyData opd = Utility.getOrderProperty(currReq.getOrderPropertyDV(),RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_ID);
        if(opd == null || !Utility.isSet(opd.getValue())){
            throw new Exception("No value for buyer cookie");
        }
        punchOutOrderEle.addElement("BuyerCookie").addText(opd.getValue());
        Element poOrdMsgEle = punchOutOrderEle.addElement("PunchOutOrderMessageHeader");
        poOrdMsgEle.addAttribute("operationAllowed","create");
        //do the money conversion
        Element moneyEle = poOrdMsgEle.addElement("Total").addElement("Money");
        moneyEle.addAttribute("currency", getCurrencyCode());
       // moneyEle.addText(nf.format(currOrd.getTotalPrice()));
        BigDecimal totPrice = currOrd.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP);
        moneyEle.addText(totPrice.toString());
        
        //null it out so we don't use it by mistake
        poOrdMsgEle = null;
        Element scratchEle = null;
        Iterator it = currReq.getOrderItemDV().iterator();
        while(it.hasNext()){
            OrderItemData ordI = (OrderItemData) it.next();
            Element itemInEle = punchOutOrderEle.addElement("ItemIn");
            itemInEle.addAttribute("quantity",Integer.toString(ordI.getTotalQuantityOrdered()));
            Element itemIDEle = itemInEle.addElement("ItemID");
            if(mTradingPartner.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.CUSTOMER) && Utility.isSet(ordI.getCustItemSkuNum())){
                itemIDEle.addElement("SupplierPartID").addText(ordI.getCustItemSkuNum());
            }else if(mTradingPartner.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.MANUFACTURER) && Utility.isSet(ordI.getManuItemSkuNum())){
                itemIDEle.addElement("SupplierPartID").addText(ordI.getManuItemSkuNum());
            }else if(mTradingPartner.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.DISTRIBUTOR) && Utility.isSet(ordI.getDistItemSkuNum())){
                itemIDEle.addElement("SupplierPartID").addText(ordI.getDistItemSkuNum());
            }else{
                itemIDEle.addElement("SupplierPartID").addText(Integer.toString(ordI.getItemSkuNum()));
            }
            itemIDEle.addElement("SupplierPartAuxiliaryID").addText(Integer.toString(ordI.getItemSkuNum()));
            Element itemDetailEle = itemInEle.addElement("ItemDetail");
            moneyEle = itemDetailEle.addElement("UnitPrice").addElement("Money");
            moneyEle.addAttribute("currency", getCurrencyCode());
            BigDecimal itmPrice = ordI.getCustContractPrice().setScale(2, BigDecimal.ROUND_HALF_UP);
            moneyEle.addText(itmPrice.toString());
           // moneyEle.addText(nf.format(ordI.getCustContractPrice()));
            
            scratchEle = itemDetailEle.addElement("Description");
            scratchEle.addAttribute("xml:lang", lang);
            scratchEle.addText(ordI.getItemShortDesc());
            scratchEle.addElement("ShortName").addText(ordI.getItemShortDesc());

            String uom;
            if(mTradingPartner.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.CUSTOMER) && Utility.isSet(ordI.getCustItemUom())){
                uom = ordI.getCustItemUom();
            }else if(mTradingPartner.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.MANUFACTURER) && Utility.isSet(ordI.getItemUom())){
                uom = ordI.getItemUom();
            }else if(mTradingPartner.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.DISTRIBUTOR) && Utility.isSet(ordI.getDistItemUom())){
                uom = ordI.getDistItemUom();
            }else{
                uom = ordI.getItemUom();
            }
            
            itemDetailEle.addElement("UnitOfMeasure").addText(uom);
            scratchEle = itemDetailEle.addElement("Classification");
            scratchEle.addAttribute("domain","UNSPSC");
            String unspscCd = (String) currReq.getGenericMap().get("UNSPSC_CD"+":" + ordI.getItemId());
            scratchEle.addText(getUnspscCd(unspscCd));
        }
        String xml = rootEle.getDocument().asXML();
        
        mOutputStream.write(xml.getBytes());
        mOutputStream.flush();
    }    
    
    protected String getUnspscCd(String unspscCd) {
    	if (Utility.isSet(unspscCd))
    		return unspscCd;
		return "";
	}

	public ElectronicTransactionData createTransactionObject() {
        return null;
    }
    
    public String getTranslationReport() {
        return mReport.toString();
    }
    
    public void setParameter(OutboundTranslate handler) {
        mHandler = handler;
        mOutputStream = handler.getOutputStream();
        mTradingPartner = mHandler.getPartner();
        mProfile = mHandler.getProfile();
    }
    
    public void setProfile(TradingProfileData pProfile) {
        mProfile = pProfile;
    }

	public void buildInterchangeContent() throws Exception {
		
		//OutboundEDIRequestDataVector lRequests = getOutboundTransactionsToProcess();
		OutboundEDIRequestDataVector lRequests = getTransactionsToProcess();
        Iterator it = lRequests.iterator();
        while(it.hasNext()){
            OutboundEDIRequestData currReq = (OutboundEDIRequestData) it.next();
            buildSingleOrderCXMLDoc(currReq);
        }
	}

	public void buildInterchangeHeader() throws Exception {
		
	}

	public void buildInterchangeTrailer() throws Exception {
		
	}

	public String getFileExtension() throws Exception {
		return "xml";
	}

	public String getFileName() throws Exception {
		return "cxmlTest";
	}

	public void setTransactionsToProcess(OutboundEDIRequestDataVector transactions) {
		
		//setOutboundTransactionsToProcess(transactions);
		mOutboundTransactionsToProcess = transactions;
	}
	
	public OutboundEDIRequestDataVector getTransactionsToProcess(){
		//return getOutboundTransactionsToProcess();
		return mOutboundTransactionsToProcess;
	}

	public InterchangeData createInterchangeObject() {
		return null;
	}

	public void setTranslator(Translator translator) {
		setParameter( (OutboundTranslate) translator);
	}

	public IntegrationRequestsVector getRequestsToProcess() {
		return null;
	}

	//@Override
	public String getCounterStr() {
		return null;
	}
    
}
