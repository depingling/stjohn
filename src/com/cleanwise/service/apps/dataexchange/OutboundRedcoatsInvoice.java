package com.cleanwise.service.apps.dataexchange;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;



import org.apache.log4j.Logger;





import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.InvoiceAbstractionView;
import com.cleanwise.service.api.value.InvoiceCustDetailRequestData;
import com.cleanwise.service.api.value.PropertyData;

public class OutboundRedcoatsInvoice extends InterchangeOutboundSuper implements OutboundTransaction{
	protected Logger log = Logger.getLogger(this.getClass());

	private StringBuffer stringBuffer;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private static char SEPERATOR = '\t'; 
	private static BigDecimal ZERO = new BigDecimal(0.00);
	private BigDecimal mFileWrittenTotal = ZERO;
	private BigDecimal mFileCalcTotal = ZERO;
	private HashMap ccMap = new HashMap();

	public void buildInterchangeHeader()
	throws Exception
	{
		super.buildInterchangeHeader();
		stringBuffer = new StringBuffer();
	}    

	private void writeData(InvoiceAbstractionView inv,String company ,String job, BigDecimal tax, BigDecimal amount, String erpCode){
		stringBuffer.append(Utility.padLeft(company,' ',3));
		stringBuffer.append(SEPERATOR);
		stringBuffer.append(Utility.padLeft(job,'0',10));
		stringBuffer.append(SEPERATOR);
		stringBuffer.append(dateFormat.format(inv.getInvoiceDate()));
		stringBuffer.append(SEPERATOR);
		stringBuffer.append(inv.getErpPoNum());
		stringBuffer.append(SEPERATOR);
		stringBuffer.append(inv.getInvoiceNum());
		stringBuffer.append(SEPERATOR);
		stringBuffer.append(tax);
		stringBuffer.append(SEPERATOR);
		stringBuffer.append(currOutboundReq.getDistributorCustomerReferenceCode());
		stringBuffer.append(SEPERATOR);
		stringBuffer.append(amount.abs());
		stringBuffer.append(SEPERATOR);
		stringBuffer.append(erpCode);
		stringBuffer.append(SEPERATOR);
		if(RefCodeNames.INVOICE_TYPE_CD.CR.equals(inv.getInvoiceType())){
			stringBuffer.append("C");
		}else{
			stringBuffer.append(" ");
		}

		stringBuffer.append(SEPERATOR);
		stringBuffer.append(inv.getGrossDue().abs());        

		mFileWrittenTotal = Utility.addAmt(mFileWrittenTotal,tax);
		mFileWrittenTotal = Utility.addAmt(mFileWrittenTotal,amount);
	}

	/**
	 * Writes out an invoicefile of the form:
	 * company, job, invoice date, po, invoice, tax, vendor number, amount, erp code
	 * grouped by erp code, for example:
	 * 1	120022600	9/25/2006	#305254-00	413664	3.69	4	828.54	Expendables    C    13.69
	 * 1	120022600	9/25/2006	#305254-00	413664	10.00	4	2284.31	Paper         13.69
	 */
	public void buildTransactionContent()
	throws Exception {
		log.info("Processing invoice: "+currOutboundReq.getInvoiceData().getInvoiceNum());

		ccMap.clear();
		mFileCalcTotal = Utility.addAmt(mFileCalcTotal,currOutboundReq.getInvoiceData().getGrossDue());
		InvoiceAbstractionView inv = currOutboundReq.getInvoiceData();

		String company = getCompanyIdentifier();
		String job = Utility.getPropertyValue(currOutboundReq.getSiteProperties(),RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
		if(!Utility.isSet(company)){
			throw new RuntimeException("Could not find Company Identfier property");
		}
		if(!Utility.isSet(job)){
			throw new RuntimeException("Could not find Site Reference Number property");
		}			

		if(currOutboundReq.getOrderD() != null && job.endsWith("00") && RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(currOutboundReq.getOrderD().getOrderBudgetTypeCd())){
			job = job.substring(0,job.length() -2)+"30";
		}            

		//add in freight and misc charges into the same bucket
		if(Utility.isSet(currOutboundReq.getInvoiceData().getFreight())){ 
			addToErpAccountCode(xlateErpAccountCode("freight"),null,currOutboundReq.getInvoiceData().getFreight());
		}
		if(Utility.isSet(currOutboundReq.getInvoiceData().getMiscCharges())){ 
			addToErpAccountCode(xlateErpAccountCode("freight"),null,currOutboundReq.getInvoiceData().getMiscCharges());
		}            

		//add in items
		Iterator itmIt = currOutboundReq.getInvoiceDetailDV().iterator();
		while(itmIt.hasNext()){
			InvoiceCustDetailRequestData det = (InvoiceCustDetailRequestData) itmIt.next();
			if(!Utility.isSet(det.getInvoiceDetailD().getERPAccountCode())){
				throw new RuntimeException("ERP Account Code (GL) on detail line not set for invoice id: "+inv.getInvoiceId());
			}
			String erpCd = xlateErpAccountCode(det.getInvoiceDetailD().getERPAccountCode());
			addToErpAccountCode(erpCd, det,null);
		}

		boolean first = true;
		itmIt = ccMap.keySet().iterator();
		while(itmIt.hasNext()){
			InvoiceItemGroup iig = (InvoiceItemGroup) ccMap.get(itmIt.next());
			BigDecimal tax = ZERO;
			if(first){
				tax = inv.getSalesTax();
				first = false;
			}
			writeData(inv,company,job,tax,iig.getSumAmount(),iig.erpCode);
			stringBuffer.append("\r\n");
		}

		if(first){
			//tax only invoice
			BigDecimal tax = inv.getSalesTax();
			writeData(inv,company,job,tax,ZERO,xlateErpAccountCode("paper"));
			stringBuffer.append("\r\n");
		}

		currOutboundReq.getInvoiceData().markAcknowledged();
		appendIntegrationRequest(currOutboundReq.getInvoiceData());

	}    

	public void buildInterchangeTrailer() throws Exception {
		translator.writeOutputStream(stringBuffer.toString());
		// assert that the written total is the same as the calculated total
		if(!mFileCalcTotal.equals(mFileWrittenTotal)){
			throw new Exception("Failed sum check, calculated total "+mFileCalcTotal+"!= written total "+mFileWrittenTotal);
		}
		super.buildInterchangeTrailer();
	}

	private void addToErpAccountCode(String erpAccountCd, InvoiceCustDetailRequestData det, BigDecimal amount){
		if((amount == null || ZERO.equals(amount)) && det == null){
			//nothing to do
			return;
		}
		InvoiceItemGroup iig = (InvoiceItemGroup) ccMap.get(erpAccountCd);
		if(iig == null){
			iig = new InvoiceItemGroup(erpAccountCd);
			ccMap.put(erpAccountCd,iig);
		}
		iig.addItem(det);
		iig.addAmount(amount);
	}	

	private String xlateErpAccountCode(String erp){
		erp =erp.toLowerCase();
		erp = erp.trim();
		if(erp.startsWith("e")){
			if(erp.indexOf('q') > 0){
				return "equipment";
			}else{
				return "expendables";
			}
		}

		if(erp.startsWith("p")){
			return "paper";
		}

		if(erp.startsWith("fr")){
			return "frieght";
		}
		throw new RuntimeException("Unknown erp system code: "+erp+" invoice: "+currOutboundReq.getInvoiceData().getInvoiceNum());
	}	

	/**
	 * Rturns the company identifier property, assumed to be a account data field property.
	 */
	private String getCompanyIdentifier(){
		Utility.getPropertyValue(currOutboundReq.getAccountProperties(),"Company Identfier");
		Iterator it = currOutboundReq.getAccountProperties().iterator();
		while(it.hasNext()){
			PropertyData prop = (PropertyData) it.next();
			if(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FIELD_CD.equals(prop.getPropertyTypeCd()) && "Company Identfier".equals(prop.getShortDesc())){
				return prop.getValue();
			}
		}
		return null;
	}

	public String getFileExtension(){
		return ".txt";
	}

	private class InvoiceItemGroup{
		String erpCode;
		ArrayList items = new ArrayList();
		BigDecimal otherCharges = null;	   

		private void addItem(InvoiceCustDetailRequestData itm){
			if(itm!=null){
				items.add(itm);
			}
		}

		private void addAmount(BigDecimal pAmount){
			otherCharges = Utility.addAmt(otherCharges, pAmount);
		}   

		private InvoiceItemGroup(String pErpCode){
			erpCode = pErpCode;
		}

		private BigDecimal getSumAmount(){
			Iterator it = items.iterator();
			BigDecimal amount = null;
			while(it.hasNext()){
				InvoiceCustDetailRequestData item = (InvoiceCustDetailRequestData) it.next();
				amount = Utility.addAmt(amount,item.getInvoiceDetailD().getLineTotal());
			}
			return Utility.addAmt(otherCharges, amount);
		}
	}
}

