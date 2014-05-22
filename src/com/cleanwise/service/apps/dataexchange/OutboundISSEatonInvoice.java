package com.cleanwise.service.apps.dataexchange;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.CostCenterDataAccess;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.CostCenterDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.InvoiceAbstractionDetailView;
import com.cleanwise.service.api.value.InvoiceAbstractionView;
import com.cleanwise.service.api.value.InvoiceCustDetailRequestData;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.PropertyData;

public class OutboundISSEatonInvoice extends InterchangeOutboundSuper implements
        OutboundTransaction {
	protected Logger log = Logger.getLogger(this.getClass());
    private String costCenterTok;

    private InvoiceAbstractionView currInvoice;

    private CostCenterGroup currCostCenterGroup;

    BigDecimal curTotalCheck;

    private APIAccess api;
    private Order orderAPI;

    private final StringBuffer buffer = new StringBuffer();

    private final static SimpleDateFormat sdfDate = new SimpleDateFormat(
            "dd/MM/yyyy");

    private final static NumberFormat nf = new DecimalFormat("#0.00");

    public OutboundISSEatonInvoice() throws Exception {
        seperateFileForEachOutboundOrder = false;
        api = APIAccess.getAPIAccess();
        orderAPI = api.getOrderAPI();
    }

    public void buildInterchangeHeader() throws Exception {
        super.buildInterchangeHeader();
        buffer.setLength(0);
    }
    
    /**
     * Get the vendor code based off the current vendor code and the company code.  This is necessary for Ireland
     * as they map the vendor based on the company and n company codes.  Here is the current mapping per Andy:
     * Company Code        Company N Segment        Customer Reference Code
      310                  310                      2018
      310                  900                      3856
      322                  322                      8954
      309                  309                      6084
      312                  312                      1009
      333                  333                      8954
      344                  344                      8954
      319                  319                      2737
     */
    private String getVendorCode(String companyCode, String comanyNCode, String vendorCode){
    	if(companyCode.equals("310") && comanyNCode.equals("900") && vendorCode.equals("2123")){
    		return "2123";
    	}

    	if("1001".equals(vendorCode)){
    		vendorCode = "BUNZL";
    	}
    	if(!"BUNZL".equals(vendorCode) && !"2123".equals(vendorCode)){
    		throw new RuntimeException("Mapping of vendor code only setup for BUNZL and 1001.  1001 is an alias for BUNZL, and " +
    				"Western Hygiene Supplies 2123.");
    	}
    	
    	if("310".equals(companyCode) && "310".equals(comanyNCode)){
    		return "2018";
    	}else if("310".equals(companyCode) && "900".equals(comanyNCode)){
    		return "3856";
    	}else if(("322".equals(companyCode)) && ("322".equals(comanyNCode))){
    		return "8954";
    	}else if(("309".equals(companyCode)) && ("309".equals(comanyNCode))){
    		return "6084";
    	}else if(("312".equals(companyCode)) && ("312".equals(comanyNCode))){
    		return "1009";
    	}else if(("333".equals(companyCode)) && ("333".equals(comanyNCode))){
    		return "8954";
    	}else if(("344".equals(companyCode)) && ("344".equals(comanyNCode))){
    		return "8954";
    	}else if(("319".equals(companyCode)) && ("319".equals(comanyNCode))){
    		return "2737";
    	}
    	throw new RuntimeException("Could not determine vendor code from company code (should be BUNZL for distributor customer reference code and 322, 310, or 900 for Company Code at the account)" +
    			" or Western Hygiene Supplies 2123 with company code 310");
    }
    
    /**
     * Gets the tax code based off the company.  We don't really understand the logic here as it is based off
     * something to do with VAT, so for now we are just going to key off the company.
     */
    private String getTaxCode(String companyCode){
    	if(("322".equals(companyCode)) 
    			|| ("309".equals(companyCode))
    			|| ("312".equals(companyCode))
    			|| ("333".equals(companyCode))
    			|| ("344".equals(companyCode))
    			|| ("319".equals(companyCode))){
    		return "\"S\"";
    	}else if("310".equals(companyCode) || "900".equals(companyCode)){
    		return "\"3\"";
    	}
    	throw new RuntimeException("Could not determine Tax code for company: ["+companyCode+ "] should be 310, 900 or 322");
    }

    public void buildTransactionContent() throws Exception {
        currInvoice = currOutboundReq.getInvoiceData();
        log.info("Processing invoice: " + currInvoice.getInvoiceNum());
        InvoiceDistData invoiceDistD = currOutboundReq.getInvoiceData()
                .getInvoiceDistData();
        List headerRow = new ArrayList();
        headerRow.add("\"H\"");
        headerRow.add(getCompanyCode());  //Company code
        BigDecimal result = invoiceDistD.getSubTotal();
        result = Utility.addAmt(result,invoiceDistD.getFreight());
        result = Utility.addAmt(result,invoiceDistD.getSalesTax());
        if (result.compareTo(new BigDecimal(0.0)) > 0) {
            headerRow.add("\"PLINV\"");
        } else {
            headerRow.add("\"PLCRD\"");
        }
        headerRow.add("\"" + invoiceDistD.getInvoiceNum() + "\"");
        OrderData orderData = orderAPI.getOrder(invoiceDistD.getOrderId());
        headerRow.add("\"" + orderData.getCurrencyCd() + "\"");
        BigDecimal finalResult = result.setScale(2, BigDecimal.ROUND_HALF_UP);
        headerRow.add("" + nf.format(finalResult.doubleValue()));
        headerRow.add("\"" + getVendorCode(getCompanyCode(),getCompanyNSegmentCode(), currOutboundReq.getDistributorCustomerReferenceCode()) + "\"");
        String processDate = sdfDate.format(new java.util.Date());
        headerRow.add("");
        headerRow.add("9999");
        headerRow.add("");
        headerRow.add(sdfDate.format(invoiceDistD.getInvoiceDate()));  //invoice date
        //tax amount
        if(invoiceDistD.getSalesTax() != null){
        	String tax = nf.format(invoiceDistD.getSalesTax());
        	headerRow.add(tax); 
        	headerRow.add(tax);
        }else{
        	headerRow.add(nf.format(0)); 
        	headerRow.add(nf.format(0)); 
        }
                
        headerRow.add("");
        headerRow.add("");
        headerRow.add("");
        headerRow.add("");
        headerRow.add(nf.format(invoiceDistD.getSubTotal()));//Goods Values
        headerRow.add("");
        headerRow.add("");
        headerRow.add("");
        headerRow.add("");
        headerRow.add(getTaxCode(getCompanyCode())); //Tax Codes
        headerRow.add("");
        headerRow.add("");
        headerRow.add("");
        headerRow.add("");
        headerRow.add("");
        
        /*for (int i = 9; i <= 27; i++) {
            headerRow.add("");
        }*/
        
        List errors = new ArrayList();
        String headerString = join(headerRow, "|", errors, HEADER_MAX_LENGTH);
        StringBuffer detailString = new StringBuffer();
        Map costCenterMap = new HashMap();
        List items = currOutboundReq.getInvoiceDetailDV();
        Iterator itmIt = items.iterator();
        while (itmIt.hasNext()) {
            InvoiceCustDetailRequestData oitem = (InvoiceCustDetailRequestData) itmIt
                    .next();
            InvoiceAbstractionDetailView item = oitem.getInvoiceDetailD();
            if (item.isInvoiceDist()) {
                String costCenterTok = item.getInvoiceDistDetailData().getErpAccountCode();
                if (Utility.isSet(costCenterTok)) {
                    
                    costCenterTok = costCenterTok.trim();
                    CostCenterGroup grp = getCostCenterGroup(costCenterTok,costCenterMap);
                    grp.addItem(item);
                }else{
                	costCenterTok = "NOT SET";
                    CostCenterGroup grp = getCostCenterGroup(costCenterTok,costCenterMap);
                    grp.addItem(item);
                }
            }
        }
        curTotalCheck = new BigDecimal(0);
        Iterator ccmIt = costCenterMap.keySet().iterator();
        List detailRow = new ArrayList();
        while (ccmIt.hasNext()) {
            detailRow.clear();
            currCostCenterGroup = (CostCenterGroup) costCenterMap.get(ccmIt
                    .next());
            costCenterTok = currCostCenterGroup.costCenterKey;
            detailRow.add("\"N\"");
            //Inter Company code (same as company code from above)
            detailRow.add(getCompanyNSegmentCode()); //Company code
            String costCode = Utility.getPropertyValue(currOutboundReq
                    .getSiteProperties(),
                    RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
            detailRow.add("\"" + costCode + "\"");
            
            StringBuffer costCenterTokBuf = new StringBuffer(costCenterTok);
            //reverse to pull off the last token, we will then have to un-reverse it to get
            //everything in the right order again.
            costCenterTokBuf.reverse();
            char[] allowedCodeTokens = new char[1];
            allowedCodeTokens[0] = '-';
            String expenseCode = Utility.getFirstToken(costCenterTokBuf,allowedCodeTokens);
            //un reverse the 2 tokens
            costCenterTokBuf.reverse();
            expenseCode = (new StringBuffer(expenseCode)).reverse().toString();
            
            String costCenterName = costCenterTokBuf.toString();
            log.info("costCenterName="+costCenterName);
            log.info("expenseCode="+expenseCode);
            log.info("costCenterTok="+costCenterTok);
            expenseCode = expenseCode.trim();
            costCenterName = costCenterName.trim();
            
            if(!verifyCostCenterInfo(costCenterName,expenseCode)){
            	errors.add("Cost center name: "+costCenterName +" and cost center code (expense code) "+expenseCode+" are not valid according to cost centers for store" );
            }
            
            detailRow.add("\"" + expenseCode + "\"");
            //detailRow.add("\"" + costCenterName + "\"");
            
            //outbound PO + sitename in place of cost center name
            String siteName = currOutboundReq.getOrderD().getOrderSiteName();
            if(siteName.indexOf("|")>=0){
            	throw new Exception("Site Name contains | character: "+siteName);
            }
            String outboundPO = currInvoice.getErpPoNum();
            if(outboundPO.indexOf("|")>=0){
            	throw new Exception("PO Number contains | character: "+outboundPO);
            }
            
            String poNum_siteName = outboundPO + " " + siteName;
            //detailRow.add("\"" + poNum_siteName + "\"");
            detailRow.add("\"" + outboundPO + "\"");
            
            detailRow.add("" + getFormattedCostCenterTotal() + "");
            detailRow.add(getTaxCode(getCompanyCode()));
            curTotalCheck = Utility.addAmt(curTotalCheck,
                    getUnFormattedCostCenterTotal());
            detailString.append(join(detailRow, "|", errors, DETAIL_MAX_LENGTH)
                    + "\n");
        }
        if (errors.size() > 0) {
            invoiceDistD
                    .setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW);
            OrderPropertyData note = OrderPropertyData.createValue();
            note.setOrderId(invoiceDistD.getOrderId());
            note.setInvoiceDistId(invoiceDistD.getInvoiceDistId());
            note.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
            String noteText = "Status updated to: "
                    + RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW;
            noteText += "\n" + join(errors, "\n");
            if (noteText.length() > 2000) {
                noteText = noteText.substring(0, 2000);
            }
            note.setValue(noteText);
            note.setOrderPropertyStatusCd("ACTIVE");
            note
                    .setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
            appendIntegrationRequest(note);
        } else {
            buffer.append(headerString + "\n");
            buffer.append(detailString.toString());
            currInvoice.markAcknowledged();
        }
        appendIntegrationRequest(currInvoice);
    }


    Connection con;
    HashMap<String,Boolean> verifyCostCenterInfoHashMap = new HashMap();
    
    /**
     * Verifies that this cost center is valid for this store.  Current implementation is based off of store only, not account.
     * @param costCenterName
     * @param expenseCode
     */
    private boolean verifyCostCenterInfo(String costCenterName, String expenseCode) throws SQLException{
    	
    	String key = costCenterName + "::"+expenseCode;
    	Boolean goodValue = verifyCostCenterInfoHashMap.get(key);
    	if(goodValue == null){
    		if(con == null){
        		con = getConnection();
        	}
    		DBCriteria crit = new DBCriteria();
    		crit.addEqualTo(CostCenterDataAccess.STORE_ID, currOutboundReq.getStoreIdIfPresent());
    		crit.addLikeIgnoreCase(CostCenterDataAccess.SHORT_DESC, costCenterName+"%");
    		crit.addEqualTo(CostCenterDataAccess.COST_CENTER_CODE, expenseCode);
    		log.info("Validating that the cost center is good:");
    		log.info(CostCenterDataAccess.getSqlSelectIdOnly("*", crit));
    		IdVector ids = CostCenterDataAccess.selectIdOnly(con, crit);
    		goodValue = ids.size() > 0; //greater than 1 is intentional, multiples are ok.
    		verifyCostCenterInfoHashMap.put(key, goodValue);
    	}
    	if(goodValue == Boolean.TRUE){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    
    //used for caching company code lookups
    private int acctIdCompanyCodeCache = 0;
    private String companyCodeCache;
    
    /**
     * Gets the company code from the account.  Should be defined in a property called:
     * "Company (REQUIERED FOR INTEGRATION)"
     * @return the company code
     * @throws Exception if code is not defined
     */
    private String getCompanyCode() throws Exception{
    	//use cached value
    	if (acctIdCompanyCodeCache != 0 && acctIdCompanyCodeCache == currOutboundReq.getAccountIdIfPresent()){
    		return companyCodeCache;
    	}
    	if(currOutboundReq.getAccountProperties() != null){
            Iterator<PropertyData> it =  currOutboundReq.getAccountProperties().iterator();
            while(it.hasNext()){
            	PropertyData apd = (PropertyData) it.next();
            	if("Company (REQUIERED FOR INTEGRATION)".equals(apd.getShortDesc()) && Utility.isSet(apd.getValue())){
            		companyCodeCache = apd.getValue();
            		return companyCodeCache;
            	}
            
            }
    	}
    	throw new Exception("Company code could not be determined for account: "+currOutboundReq.getAccountName()+".  Expected to be definied in account data property named: \"Company (REQUIERED FOR INTEGRATION)\"");
    }
    
    
    //used for caching company code lookups
    private int acctIdCompanyNCodeCache = 0;
    private String companyNCodeCache;
    
    /**
     * Gets the company code from the account.  Should be defined in a property called:
     * "Company N Segment (REQUIERED FOR INTEGRATION)"
     * @return the company code
     * @throws Exception if code is not defined
     */
    private String getCompanyNSegmentCode() throws Exception{
    	//use cached value
    	if (acctIdCompanyNCodeCache != 0 && acctIdCompanyNCodeCache == currOutboundReq.getAccountIdIfPresent()){
    		return companyNCodeCache;
    	}
    	if(currOutboundReq.getAccountProperties() != null){
            Iterator<PropertyData> it =  currOutboundReq.getAccountProperties().iterator();
            while(it.hasNext()){
            	PropertyData apd = (PropertyData) it.next();
            	if("Company N Segment (REQUIERED FOR INTEGRATION)".equals(apd.getShortDesc()) && Utility.isSet(apd.getValue())){
            		companyNCodeCache = apd.getValue();
            		return companyNCodeCache;
            	}
            
            }
    	}
    	throw new Exception("Company N Segment code could not be determined for account: "+currOutboundReq.getAccountName()+".  Expected to be definied in account data property named: \"Company N Segment (REQUIERED FOR INTEGRATION)\"");
    }
    
    public void buildInterchangeTrailer() throws Exception {
        if (buffer.length() > 0) {
            getTranslator().writeOutputStream(buffer.toString());
            super.buildInterchangeTrailer();
        }
    }

    

    /**
     * Retrieves the CostCenterGroup from the mapping, adding it if it does not
     * already exist
     */
    private CostCenterGroup getCostCenterGroup(String costCenterTok,
            Map costCenterMap) {
        CostCenterGroup grp = (CostCenterGroup) costCenterMap
                .get(costCenterTok);
        if (grp == null) {
            grp = new CostCenterGroup(costCenterTok);
            costCenterMap.put(costCenterTok, grp);
        }
        return grp;
    }

    /**
     * Returns "" if the passed in string is null, otherwise returns the passed
     * in string
     */
    private String nullWrt(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

    private String getFormattedNumber(BigDecimal theNumber) {
        theNumber = theNumber.abs();
        return theNumber.toString();
    }

    /**
     * Returns the subtotal of the current cost center items/other amounts etc.
     */
    private BigDecimal getUnFormattedCostCenterTotal() {
        BigDecimal theNumber = null;
        if (currCostCenterGroup.items != null) {
            Iterator it = currCostCenterGroup.items.iterator();
            while (it.hasNext()) {
                InvoiceAbstractionDetailView det = (InvoiceAbstractionDetailView) it
                        .next();
                theNumber = Utility.addAmt(theNumber, det.getLineTotal());
            }
        }
        if (currCostCenterGroup.otherAmt != null) {
            theNumber = Utility.addAmt(theNumber, currCostCenterGroup.otherAmt);
        }
        return theNumber;
    }

    /**
     * Returns the subtotal of the current cost center items/other amounts etc.
     * as a formatted string.
     */
    private String getFormattedCostCenterTotal() {
        return getFormattedNumber(getUnFormattedCostCenterTotal());
    }

    public String getFileExtension() throws Exception {
        return ".txt";
    }

    private class CostCenterGroup {
        String costCenterKey;

        List items;

        BigDecimal otherAmt;

        private CostCenterGroup(String pCostCenterKey) {
            costCenterKey = pCostCenterKey;
        }

        private void addItem(InvoiceAbstractionDetailView pItem) {
            if (items == null) {
                items = new ArrayList();
            }
            items.add(pItem);
        }
    }

    private static String join(List list, String delimiter) {
        return join(list, delimiter, null, null);
    }

    private static String join(List list, String delimiter, List errors,
            int length[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; list != null && i < list.size(); i++) {
            if (i > 0) {
                sb.append(delimiter);
            }
            Object obj = list.get(i);
            String val = obj != null ? obj.toString() : "";
            sb.append(val);
            if (errors != null && length != null && i < length.length
                    && length[i] > 0 && val.length() > 0) {
                boolean isString = (val.charAt(0) == '"')
                        && (val.charAt(val.length() - 1) == '"');
                int maxLength = length[i];
                if (isString) {
                    maxLength += 2;
                }
                if (length[i] > 0 && val.length() > maxLength) {
                    errors.add("Long value '" + val + "' in " + (i + 1)
                            + " column. Max is " + length[i] + " chars.");
                }
            }
        }
        return sb.toString();
    }

    private int HEADER_MAX_LENGTH[] = { 1, 4, 6, 30, 4, 15, 10, -1, 4, 2, -1,
            15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 4, 4, 4, 4, 4, 3 };

    private int DETAIL_MAX_LENGTH[] = { 1, 4, 15, 15, 40, 15, 4 };
}
