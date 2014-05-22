package com.cleanwise.service.apps.edi;

import java.io.*;
import java.text.SimpleDateFormat;
import java.math.*;
import java.rmi.*;
import java.util.*;

import com.americancoders.edi.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.apps.dataexchange.*;

/**
 * Super class for build outbound EDI document for a particular set.
 * This is the super class for all *EDI* documents, all a outbound
 * document must do to be understood is to implement the OutboundTransaction
 * implementation.
 * @author Deping
 */
public abstract class OutboundSuper extends Edi implements OutboundTransaction{
    protected Writer mOutputStream;
    //protected OutboundTranslate translator;
    protected String mErpPoNum;
    protected OrderData currOrder;
    protected OrderItemData currItem;
    protected OrderItemDataVector items = null;
    protected ManifestItemView currItemManifest = null;
    protected InvoiceAbstractionView currInvoice = null;
    protected List invoiceItems = null;
    protected InvoiceAbstractionDetailView currInvoiceItem;
    protected OrderAddressData mCustShipAddr;
    protected OutboundEDIRequestData currEDIRequestD;

    private HashSet excludeMappedHeaders = new HashSet();
    private HashSet managedMappedHeaders = new HashSet();
    /** constructor for class OutboundSuper
     */
    OutboundSuper(){
        direction = OUTBOUND;

    }

    OutboundEDIRequestDataVector mOutboundTransactionsToProcess;
    /**
     * Sets the outbound transactions that this builder will be processing
     */
	public void setOutboundTransactionsToProcess(OutboundEDIRequestDataVector pOutboundToProcess){
		mOutboundTransactionsToProcess = pOutboundToProcess;
	}

	/**
	 * Returns the requests that need to be rendered into the file.
	 * This is shorthand for getHander().getOutboundReqOrderDV()
	 */
	protected OutboundEDIRequestDataVector getOutboundTransactionsToProcess(){
		return mOutboundTransactionsToProcess;
	}

    public void setParameter(OutboundTranslate handler) {
        System.out.println("setting handler::"+handler);
        translator = handler;
    }

    public abstract void buildTransactions(int incomingProfileId)
    throws OBOEException, RemoteException;

    public void buildTransaction() throws OBOEException {
        buildTransactionHeader();
        buildTransactionContent();
        buildTransactionTrailer();
    }


    public abstract void buildTransactionContent()
    throws OBOEException;
    public abstract void buildTransactionTrailer()
    throws OBOEException;


    public ElectronicTransactionData createTransactionObject() {
        String setControlNumberString = Integer.toString(profile.getGroupControlNum()) + Utility.padLeft(Integer.toString(fg.getTransactionSetCount()+1), '0', 3);

        transactionD = super.createTransactionObject();
        transactionD.setSetControlNumber(Integer.parseInt(setControlNumberString));
        transactionD.setSetStatus(SEND_MSG);
        return transactionD;
    }

    /**
     *No special rules for generating file names
     */
    public String getFileName(OutboundTranslate pHandler){
        return null;
    }

    /**
     *Returns the trading partner
     */
    protected TradingPartnerData getTradingPartner(){
        return ((OutboundTranslate)translator).getTradingPartner();
    }

    /**
     *Returns the proper TradingPropertyMapData given the specified criteria.  This finds the
     *first mapping, if there are multiple only the first is returned
     */
    TradingPropertyMapData getReferenceTradingPropertyMap(String segment){
        TradingPropertyMapDataVector mapping = ((OutboundTranslate)translator).getTradingPropertyMapDataVector();
        Iterator it = mapping.iterator();
        while(it.hasNext()){
            TradingPropertyMapData map = (TradingPropertyMapData) it.next();
            if(RefCodeNames.TRADING_PROPERTY_MAP_CD.REFERENCE.equals(map.getTradingPropertyMapCode())){
                if(segment.equals(map.getUseCode())){
                    return map;
                }
            }
        }
        return null;
    }

    /**
     *Checks if a given segment is going to be mapped later on.  This is needed
     *when a documnet has looping requierments that are not represented in the
     *mapping table currently.
     */
    boolean isSegmentInMapping(String pSegmentName){

        TradingPropertyMapDataVector mapping = ((OutboundTranslate)translator).getTradingPropertyMapDataVector();

	if ( null == mapping ) {
	    System.out.println("2005.5.24 mapping is null, pSegmentName="
			       +pSegmentName);
	    return false;
	}

        Iterator it = mapping.iterator();
        boolean retValue=false;
        while(it.hasNext()){
            TradingPropertyMapData map = (TradingPropertyMapData) it.next();
	    if ( map.getUseCode() == null ) {
		System.out.println("2005.5.24 usecode is null, pSegmentName="
				   +pSegmentName
				   +" map=" + map );
		continue;
	    }
            if(map.getUseCode().equalsIgnoreCase(pSegmentName)){
                if(RefCodeNames.TRADING_PROPERTY_MAP_CD.DYNAMIC.equals(map.getTradingPropertyMapCode())){
                    String value = getValueFromMapping(map);
                    if (Utility.isSet(value)){
                        retValue = true;
                    }
                }
            }
        }
        return retValue;
    }


    /**
     *If a structure element is managed by the implementing class then that element
     *should be indicated that it is managed here so the it is not rendered twice.
     *Depending on where the element is you may or may not need to do this, it depends
     *on where the element is located.  If it is after the mapped header are rendered
     *then you will need to make a call to this method.
     */
    void addManagedMappedHeader(String pUseCode){
        managedMappedHeaders.add(pUseCode);
    }

    /**
     *Builds table mappings at the header level
     */
    void buildAllMappedHeader(Table inTable){
        excludeMappedHeaders.addAll(managedMappedHeaders);
        buildMappedHeadersWorker(inTable, (String) null);
        //clear the excluded mappings as at this point everything has been rendered
        //this way we don't skip things necessary on new calls.  This would happen if
        //a mapped header was requiered in one edi transaction doc, and was rendered
        //through the default mechanism in anouther.  Currently there are no examples
        //where this is the case.
        excludeMappedHeaders.clear();
    }

    /**
     *Builds table mappings at the header level
     */
    void buildMappedHeaders(Loop inLoop, String pFilter){
        if(pFilter==null){
            throw new NullPointerException("pFilter was null");
        }
        buildMappedHeadersWorker(inLoop, pFilter);
    }

    /**
     *Builds table mappings at the header level
     */
    void buildMappedHeaders(Table inTable, String pFilter){
        if(pFilter==null){
            throw new NullPointerException("pFilter was null");
        }
        buildMappedHeadersWorker(inTable, pFilter);
    }

    /**
     *Builds table mappings at the header level
     */
    private void buildMappedHeadersWorker(Object inTableOrSegment, String pFilter){
        if(pFilter!=null){
            excludeMappedHeaders.add(pFilter);
        }
        TradingPropertyMapDataVector mapping = ((OutboundTranslate)translator).getTradingPropertyMapDataVector();
        Iterator it = mapping.iterator();
        while(it.hasNext()){
            TradingPropertyMapData map = (TradingPropertyMapData) it.next();
            //if we are filtering the results for a particular use code then only render those values
            if(pFilter != null && !pFilter.equals(map.getUseCode())){
                continue;
            }
            //if we are rendering all the mapped headers at this time, and a header was mapped
            //previously using the filter then don't render it now.  This is used when we are mapping
            //a value that needs its structure managed outside the data representation of the mappings
            //that is loops and other such structural elements.
            if(pFilter==null && excludeMappedHeaders.contains(map.getUseCode())){
                continue;
            }
            if(RefCodeNames.TRADING_PROPERTY_MAP_CD.DYNAMIC.equals(map.getTradingPropertyMapCode())){
                buildMappedHeader(inTableOrSegment,map);
            }else if(map.getTradingPropertyMapCode() == null){
                throw new NullPointerException("Property tradingPropertyMapCode of TradingPropertyMapData id: "
                        + map.getTradingPropertyMapId() + " cannot be null");
            }
        }

    }




    /**
     *Create a simple mapped header segment
     */
    private void buildMappedHeader(Object inTableOrSegment,TradingPropertyMapData pMapping)
    throws OBOEException {
        String value = getValueFromMapping(pMapping);


        if (!Utility.isSet(value)){
            if(Utility.isTrue(pMapping.getMandatory())){
                throw new RuntimeException("Property is mandatory and not present: "+pMapping);
            }else{
                return;
            }
        }
        if(inTableOrSegment instanceof Table){
            Segment segment = ((Table)inTableOrSegment).createSegment(pMapping.getUseCode());
            ((Table)inTableOrSegment).addSegment(segment);
            DataElement de;
            //if the qualifier is set then set the qualifier and then the identifier.
            //otherwise assume that the identifier goes in the first position
            if(Utility.isSet(pMapping.getQualifierCode())){
                de = (DataElement) segment.buildDE(1);  // 128 Qualifier
                de.set(pMapping.getQualifierCode());
                de = (DataElement) segment.buildDE(2);  // 127 Identification
                de.set(value);
            }else{
                de = (DataElement) segment.buildDE(1);  // 127 Identification
                de.set(value);
            }
         }else if(inTableOrSegment instanceof Loop){
            Segment segment = ((Loop)inTableOrSegment).createSegment(pMapping.getUseCode());
            ((Loop)inTableOrSegment).addSegment(segment);
            DataElement de;
            //if the qualifier is set then set the qualifier and then the identifier.
            //otherwise assume that the identifier goes in the first position
            if(Utility.isSet(pMapping.getQualifierCode())){
                de = (DataElement) segment.buildDE(1);  // 128 Qualifier
                de.set(pMapping.getQualifierCode());
                de = (DataElement) segment.buildDE(2);  // 127 Identification
                de.set(value);
            }else{
                de = (DataElement) segment.buildDE(1);  // 127 Identification
                de.set(value);
            }
         }else{
            throw new RuntimeException("inTableOrSegment must implement Loop or Table");
         }
    }


    /**
     *Based off the current edi request will return the value that is specified by this mapping
     *data. Order data, site data, site property etc etc.
     */
    String getValueFromMapping(TradingPropertyMapData pMapping){
        String value=null;
	String prefixToValue = pMapping.getHardValue();
	if ( null == prefixToValue ||
	     prefixToValue.length() == 0 ) {
	    prefixToValue = "";
	}

        if(RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE.equals(pMapping.getEntityProperty())){
            value = pMapping.getHardValue();
	    prefixToValue = "";
        }else if(RefCodeNames.ENTITY_PROPERTY_TYPE.ORDER_PROPERTY.equals(pMapping.getEntityProperty())){
            Iterator it = currEDIRequestD.getOrderPropertyDV().iterator();
            while(it.hasNext()){
                OrderPropertyData prop = (OrderPropertyData) it.next();
                if(prop.getOrderPropertyTypeCd().equalsIgnoreCase(pMapping.getPropertyTypeCd())){
                    value = prop.getValue();
                    break;
                }
            }
        }else if(RefCodeNames.ENTITY_PROPERTY_TYPE.SITE_FIELD_PROPERTY.equals(pMapping.getEntityProperty())){
            Iterator it = currEDIRequestD.getSiteProperties().iterator();
            while(it.hasNext()){
                PropertyData prop = (PropertyData) it.next();
                if(prop.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD)
                && prop.getShortDesc().equalsIgnoreCase(pMapping.getPropertyTypeCd())){
                    value = prop.getValue();
                    break;
                }
            }
        }else if(RefCodeNames.ENTITY_PROPERTY_TYPE.SITE_PROPERTY.equals(pMapping.getEntityProperty())){
            PropertyData prop = Utility.getProperty(currEDIRequestD.getSiteProperties(), pMapping.getPropertyTypeCd());
            if(prop != null){
                value = prop.getValue();
            }
        }else if(RefCodeNames.ENTITY_PROPERTY_TYPE.ORDER_COLUMN.equals(pMapping.getEntityProperty())){
            try{
                StringBuffer methodName = new StringBuffer(pMapping.getPropertyTypeCd());
                char c = Character.toUpperCase(methodName.charAt(0));
                methodName.setCharAt(0,c);
                methodName.insert(0,"get");
                OrderData o = currEDIRequestD.getOrderD();
                Object obj = o.getClass().getMethod(methodName.toString(),null).invoke(o, null);
                value = obj.toString();
            }catch(Exception e){
                e.printStackTrace();
                value = null;
            }
        }else if(RefCodeNames.ENTITY_PROPERTY_TYPE.ACCOUNT_FIELD_PROPERTY.equals(pMapping.getEntityProperty())){
            if(currEDIRequestD.getAccountProperties() == null){
                throw new NullPointerException("Account Properties is null, possibly not implemented for this transaction type.");
            }
            Iterator it = currEDIRequestD.getAccountProperties().iterator();
            while(it.hasNext()){
                PropertyData prop = (PropertyData) it.next();
                if(prop.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FIELD_CD)
                && prop.getShortDesc().equalsIgnoreCase(pMapping.getPropertyTypeCd())){
                    value = prop.getValue();
                    break;
                }
            }
        }else if(RefCodeNames.ENTITY_PROPERTY_TYPE.ACCOUNT_PROPERTY.equals(pMapping.getEntityProperty())){
            if(currEDIRequestD.getAccountProperties() == null){
                throw new NullPointerException("Account Properties is null, possibly not implemented for this transaction type.");
            }
            PropertyData prop = Utility.getProperty(currEDIRequestD.getAccountProperties(), pMapping.getPropertyTypeCd());
            if(prop != null){
                value = prop.getValue();
            }
        }

	if ( null == prefixToValue || prefixToValue.equals(value) ) {
	    prefixToValue = "";
	}

        return prefixToValue + value;
    }


    String getValueFromMappingByPropertyTypeCd(String pPropertyTypeCd){
        String value=null;

	if ( null == pPropertyTypeCd || pPropertyTypeCd.length() == 0 ) {
	    return null;
	}

        TradingPropertyMapDataVector mapping = ((OutboundTranslate)translator).getTradingPropertyMapDataVector();
        Iterator it = mapping.iterator();
        while(it.hasNext()){
            TradingPropertyMapData map = (TradingPropertyMapData) it.next();
	    if ( pPropertyTypeCd.equals(map.getPropertyTypeCd())
		 && map.getHardValue() != null
		 ) {
		return map.getHardValue();
	    }
	}

        return null;
    }

    /**
     *Sets a DataElement value, trims the value to the desired length by truncating anything
     *over the desired length.
     */
    protected void deSet(DataElement pDe, String pStr,int pLength){
        deSet(pDe, pStr, null, pLength, false);
    }

    /**
     *Sets a DataElement value, trims the value to the desired length by truncating anything
     *over the desired length.  If the value is not set uses the default value.
     */
    protected void deSet(DataElement pDe, String pStr, String pDefaultVal,int pLength){
        deSet(pDe, pStr, pDefaultVal, pLength, false);
    }

    /**
     *Sets a DataElement value, trims the value to the desired length by truncating anything
     *over the desired length.  If the value is not set uses the default value. Filters out any
     *bad charachters that are not suitable for EDI transmission.
     */
    protected void deSet(DataElement pDe, String pStr, String pDefaultVal,int pLength,boolean filterChars){
        //set as default value
        if(pStr == null || pStr.trim().length() == 0){
            pStr = pDefaultVal;
        }

        //filter anything not suitable for EDI
        if(filterChars){
            String[] search = {"\n","\f","\t","\r","\""};
            String[] replace = {"","","","",""};
            for(int j=0; j < search.length;j++){
                pStr = pStr.replaceAll(search[j], replace[j]);
            }
        }
        //set the length
        if(pStr.length() > pLength){
            pStr = pStr.substring(0,pLength);
        }
        //trim any whitespace and set the data element
        pStr = pStr.trim();
        pDe.set(pStr);
    }

    /**
     *Removes the decimal places from a bigdecimal.  for example if 5.25 is passed in 525 is returned
     *if 5 is passed in 500 is returned.  This is suitable for edi docs where they do not want any decimals
     */
    BigDecimal convertToNoDecimals(BigDecimal val){
        val = val.setScale(2, BigDecimal.ROUND_HALF_UP);
        val = val.multiply(new BigDecimal(100));
        return val;
    }

    public String getFileExtension(OutboundTranslate pHandler) throws Exception {
        return null;
    }


    protected void logShipToAddress(com.cleanwise.service.apps.dataexchange.CompressedAddress pShipTo ) {
	System.out.println( "     = ST = \n" + pShipTo );
	String logstr =  currOrder.getSiteId() + "\t";

	String t = currOrder.getUserFirstName();
        if ( null == t ) t = ".";
	logstr += t + "\t";

	t = currOrder.getUserLastName();
        if ( null == t ) t = "..";
	logstr += t + "\t";

	t = pShipTo.getAddress1();
	if ( t.length() == 0 ) {
	    t += " ";
	}
	t += "\t";
	logstr += t;

	t = "";
	if ( pShipTo.getAddress2() != null ) {
	    t += pShipTo.getAddress2();
	}
	if ( pShipTo.getAddress3() != null ) {
	    if ( t.length() > 0 ) t += "/";
	    t += pShipTo.getAddress3();
	}
	if ( pShipTo.getAddress4() != null ) {
	    if ( t.length() > 0 ) t += "/";
	    t += pShipTo.getAddress4();
	}
	if ( t.length() == 0 ) {
	    t += " ";
	}
	t += "\t";

	logstr += t;
	logstr += pShipTo.getCity();
	logstr += "\t";
	logstr += pShipTo.getStateProvinceCode();
	logstr += "\t";
	logstr += pShipTo.getPostalCode();
	logstr += "\t";
	logstr += mErpPoNum + "\t";
	logstr += "\t";
	logstr += "EOR" + "\n";
	System.out.println( "     = logstr = \n" + logstr );

	try {
	    t = java.lang.System.getProperty("addressLogFile");
	    if ( t != null && t.length() > 0 ) {
		FileOutputStream addrFileOut = new FileOutputStream( new File(t), true );
		addrFileOut.write(logstr.getBytes());
		addrFileOut.close();
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    protected String mkHourString(java.util.Date pInDate) {
    	SimpleDateFormat dateFormatter = new SimpleDateFormat("HHmm");
    	return dateFormatter.format(pInDate);
    }

    protected String mkDateString(java.util.Date pInDate) {
    	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
    	return dateFormatter.format(pInDate);
    }

    protected String getOrderApprovalDateString() {
	if ( null == currOrder ) {
	    return "";
	}
	// For JCP, the order gets revised when JCP sends us a PO number.
 	if ( null != currOrder.getRevisedOrderDate() ) {
	    return mkDateString(currOrder.getRevisedOrderDate());
	}

	// Fall back to the order date.
 	if ( null != currOrder.getOriginalOrderDate() ) {
	    return mkDateString(currOrder.getOriginalOrderDate());
	}

	return "";
    }

    protected String stripEPRO(String pInString) {

	if ( pInString  == null || pInString.length() == 0 ) {
	    return pInString;
	}

	if ( !pInString.startsWith("EPRO") ) {
	    return pInString;
	}

	String t = pInString;
	// JCP does not want the prefix we have added
	// to the PO number when they get the invoice.
	t = t.replaceFirst("EPRO", "");
	System.out.println("EPRO PO NUMBER t=" + t);
	t = t.substring(0,8);
	System.out.println("FINAL EPRO PO NUMBER t=" + t);
	return t;
    }
}







