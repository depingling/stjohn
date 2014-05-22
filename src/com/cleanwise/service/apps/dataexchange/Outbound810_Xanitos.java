/**
 * Outbound810_Xanitos.java
 */
package com.cleanwise.service.apps.dataexchange;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.naming.NamingException;
import javax.xml.soap.SOAPException;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.log4j.Category;

import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.util.InvalidLoginException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.InvoiceAbstractionView;
import com.cleanwise.service.api.value.InvoiceCustDetailRequestData;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.TradingPropertyMapData;
import com.cleanwise.service.api.value.TradingPropertyMapDataVector;
import com.netsuite.webservices.lists.accounting_2009_2.Account;
import com.netsuite.webservices.lists.accounting_2009_2.AccountSearch;
import com.netsuite.webservices.lists.accounting_2009_2.AccountingPeriod;
import com.netsuite.webservices.lists.accounting_2009_2.AccountingPeriodSearch;
import com.netsuite.webservices.lists.accounting_2009_2.Location;
import com.netsuite.webservices.lists.accounting_2009_2.LocationSearch;
import com.netsuite.webservices.lists.relationships_2009_2.Customer;
import com.netsuite.webservices.lists.relationships_2009_2.CustomerSearch;
import com.netsuite.webservices.lists.relationships_2009_2.Vendor;
import com.netsuite.webservices.lists.relationships_2009_2.VendorSearch;
import com.netsuite.webservices.platform.common_2009_2.AccountSearchBasic;
import com.netsuite.webservices.platform.common_2009_2.AccountingPeriodSearchBasic;
import com.netsuite.webservices.platform.common_2009_2.CustomerSearchBasic;
import com.netsuite.webservices.platform.common_2009_2.LocationSearchBasic;
import com.netsuite.webservices.platform.common_2009_2.TransactionSearchBasic;
import com.netsuite.webservices.platform.common_2009_2.VendorSearchBasic;
import com.netsuite.webservices.platform.core_2009_2.Passport;
import com.netsuite.webservices.platform.core_2009_2.Record;
import com.netsuite.webservices.platform.core_2009_2.RecordList;
import com.netsuite.webservices.platform.core_2009_2.RecordRef;
import com.netsuite.webservices.platform.core_2009_2.SearchBooleanField;
import com.netsuite.webservices.platform.core_2009_2.SearchDateField;
import com.netsuite.webservices.platform.core_2009_2.SearchEnumMultiSelectField;
import com.netsuite.webservices.platform.core_2009_2.SearchMultiSelectField;
import com.netsuite.webservices.platform.core_2009_2.SearchResult;
import com.netsuite.webservices.platform.core_2009_2.SearchStringField;
import com.netsuite.webservices.platform.core_2009_2.types.RecordType;
import com.netsuite.webservices.platform.core_2009_2.types.SearchEnumMultiSelectFieldOperator;
import com.netsuite.webservices.platform.core_2009_2.types.SearchMultiSelectFieldOperator;
import com.netsuite.webservices.platform.core_2009_2.types.SearchStringFieldOperator;
import com.netsuite.webservices.platform.core_2009_2.types.SearchDateFieldOperator;
import com.netsuite.webservices.platform.faults_2009_2.InvalidCredentialsFault;
import com.netsuite.webservices.platform.messages_2009_2.WriteResponse;
import com.netsuite.webservices.platform_2009_2.NetSuiteBindingStub;
import com.netsuite.webservices.platform_2009_2.NetSuitePortType;
import com.netsuite.webservices.platform_2009_2.NetSuiteServiceLocator;
import com.netsuite.webservices.transactions.purchases_2009_2.VendorBill;
import com.netsuite.webservices.transactions.purchases_2009_2.VendorBillExpense;
import com.netsuite.webservices.transactions.purchases_2009_2.VendorBillExpenseList;
import com.netsuite.webservices.transactions.sales_2009_2.TransactionSearch;
import com.netsuite.webservices.transactions.sales_2009_2.types.TransactionType;
/**
 * @author ssharma
 *
 */
public class Outbound810_Xanitos extends InterchangeOutboundSuper implements OutboundTransaction{
	private static final Category log = Category.getInstance(InterchangeOutboundSuper.class);
	
	private StringBuffer stringBuffer;
	private NetSuitePortType _port;
	private Map ccMap = new HashMap();
	
	public void buildInterchangeHeader() throws Exception{
		super.buildInterchangeHeader();
		stringBuffer = new StringBuffer();	
	}    
	
	public void buildTransactionContent()
	throws Exception {
		
		System.setProperty("axis.socketSecureFactory","org.apache.axis.components.net.SunFakeTrustSocketFactory");
		
		// Locate the NetSuite web service.
		NetSuiteServiceLocator service = new NetSuiteServiceLocator();
		// Get the service port	
		
		String _url ="";
		String urlIndicator = translator.getProfile().getTestIndicator();
		if(urlIndicator.equals("T")){
			_url = "https://webservices.sandbox.netsuite.com/services/NetSuitePort_2009_2";
		}else{
			_url = "https://webservices.netsuite.com/services/NetSuitePort_2009_2";
		}
		
		_port = service.getNetSuitePort(new URL(_url));
		((NetSuiteBindingStub) _port).setTimeout(1000 * 60 * 60 * 2);
		
		try{
			setLogin();
			addInvoice();
		} catch(InvalidLoginException ex){
			OrderPropertyData opd = OrderPropertyData.createValue();
	        opd.setAddBy("system");
	        opd.setModBy("system");
	        opd.setInvoiceDistId(currOutboundReq.getInvoiceData().getInvoiceDistData().getInvoiceDistId());
	        opd.setOrderId(currOutboundReq.getInvoiceData().getOrderId());
	        opd.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
	        opd.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
	        opd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
			opd.setValue(ex.getMessage());
			appendIntegrationRequest(opd);
			currOutboundReq.getInvoiceData().setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW);
			appendIntegrationRequest(currOutboundReq.getInvoiceData());
			
			log.info("\nException: " + ex.getMessage());
		} catch (Exception ex) {
			
			OrderPropertyData opd = OrderPropertyData.createValue();
	        opd.setAddBy("system");
	        opd.setModBy("system");
	        opd.setInvoiceDistId(currOutboundReq.getInvoiceData().getInvoiceDistData().getInvoiceDistId());
	        opd.setOrderId(currOutboundReq.getInvoiceData().getOrderId());
	        opd.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
	        opd.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
	        opd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
			opd.setValue(ex.getMessage());
			appendIntegrationRequest(opd);
			currOutboundReq.getInvoiceData().setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW);
			appendIntegrationRequest(currOutboundReq.getInvoiceData());
			
			log.info("\nException: " + ex.getMessage());
		} finally {
		}
		
	}
	
	private void setLogin() throws SOAPException {
		NetSuiteBindingStub stub = (NetSuiteBindingStub) _port;
		stub.clearHeaders();
		
		SOAPHeaderElement userPassportHeader = new SOAPHeaderElement("urn:messages_2009_2.platform.webservices.netsuite.com", "passport");
		Passport passport = new Passport();
		RecordRef role = new RecordRef();
		
		String userName="";
		String pass = "";
		String acctNum = "";
		
		TradingPropertyMapDataVector propMap =this.getTranslator().getTradingPropertyMapDataVector();
		Iterator it = propMap.iterator();
		while(it.hasNext()){
			TradingPropertyMapData data = (TradingPropertyMapData) it.next();
			if((RefCodeNames.ENTITY_PROPERTY_TYPE.USERNAME).equals(data.getPropertyTypeCd())){
				userName = data.getHardValue();
			}else if((RefCodeNames.ENTITY_PROPERTY_TYPE.PASSWORD).equals(data.getPropertyTypeCd())){
				pass = data.getHardValue();
			}else if((RefCodeNames.ENTITY_PROPERTY_TYPE.ACCOUNT_NUM).equals(data.getPropertyTypeCd())){
				acctNum = data.getHardValue();
			}
		}
	
		passport.setEmail(userName);
		passport.setPassword(pass);
		passport.setAccount(acctNum);
		
		userPassportHeader.setObjectValue(passport);
		stub.setHeader(userPassportHeader);
	}

	public void addInvoice() throws Exception {
		ccMap.clear();
		
		log.info("Processing invoice: "+currOutboundReq.getInvoiceData().getInvoiceNum());
		try{
		VendorBill inv = new VendorBill();
		
		//invoice data
		InvoiceAbstractionView invoiceD = currOutboundReq.getInvoiceData();
		
		Calendar invDate = Calendar.getInstance();
		invDate.setTime(invoiceD.getInvoiceDate());
		//accomodating time zone difference
		invDate.add(Calendar.HOUR, +3);
		inv.setTranDate(invDate);
		inv.setDueDate(invDate);
		
		//memo
		String po_num ="";
		if((currOutboundReq.getOrderD().getRequestPoNum() != null)){
			po_num = currOutboundReq.getOrderD().getRequestPoNum();
			
		}else if((currOutboundReq.getPurchaseOrderD() != null ) && 
				(currOutboundReq.getPurchaseOrderD().getErpPoNum() != null)){
			po_num = currOutboundReq.getPurchaseOrderD().getErpPoNum();
			
		}
		if((!Utility.isSet(po_num)) || (po_num.equalsIgnoreCase("N/A"))){
			if(currOutboundReq.getInvoiceDetailDV() != null && !currOutboundReq.getInvoiceDetailDV().isEmpty()){
				InvoiceCustDetailRequestData item = 
					(InvoiceCustDetailRequestData)currOutboundReq.getInvoiceDetailDV().get(0);
				if(item != null &&  item.getOrderItemD() != null){
					po_num = item.getOrderItemD().getOutboundPoNum();
				}
			}
		}
		inv.setMemo(po_num);
		
		AccountingPeriodSearch apSearch = new AccountingPeriodSearch();
		
		SearchBooleanField apLockedBF = new SearchBooleanField();
		apLockedBF.setSearchValue(false);
		
		SearchBooleanField apQuarterBF = new SearchBooleanField();
		apQuarterBF.setSearchValue(false);
		
		SearchBooleanField apYearBF = new SearchBooleanField();
		apYearBF.setSearchValue(false);
		
		SearchDateField endDateSF = new SearchDateField();
		endDateSF.setSearchValue(invDate);
		endDateSF.setOperator(SearchDateFieldOperator.onOrAfter);
		
		AccountingPeriodSearchBasic apSearchBasic = new AccountingPeriodSearchBasic();
		apSearchBasic.setApLocked(apLockedBF);
		apSearchBasic.setIsQuarter(apQuarterBF);
		apSearchBasic.setIsYear(apYearBF);
		apSearchBasic.setEndDate(endDateSF);
		
		apSearch.setBasic(apSearchBasic);
		SearchResult aPeriodRes = _port.search(apSearch);
		
		if (aPeriodRes.getStatus().isIsSuccess()) {
			Record record;
			Record postigPeriodRecord = null;
			int aPeriodResultSize = aPeriodRes.getTotalRecords().intValue();
			log.info(
					"\nNumber of Accounting periods found: " + aPeriodResultSize);
			if (aPeriodResultSize > 0) {
				postigPeriodRecord = aPeriodRes.getRecordList().getRecord(0);
			}
			if(postigPeriodRecord != null){
				RecordRef postigPeriodRecordRef = new RecordRef();
				postigPeriodRecordRef.setType(RecordType.accountingPeriod);
				String name = ((AccountingPeriod)postigPeriodRecord).getPeriodName();
				postigPeriodRecordRef.setName(name);
				postigPeriodRecordRef.setInternalId(((AccountingPeriod)postigPeriodRecord).getInternalId());
				inv.setPostingPeriod(postigPeriodRecordRef);
			}
		} else {
			String err = aPeriodRes.getStatus().getStatusDetail(0).getMessage();
			throw new Exception("Could not find Open Accouting periods in Netsuite: "+" >> "+err);
		}
		
		//location
		String siteRef = getSiteProperty(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
		RecordRef locRef = new RecordRef();
		
		if(siteRef!=null){
			log.info("Site:"+siteRef);
			LocationSearch lSearch = new LocationSearch();	
			LocationSearchBasic lSearchBasic = new LocationSearchBasic();
			SearchStringField entityId3 = new SearchStringField();
			entityId3.setSearchValue(siteRef);
			entityId3.setOperator(SearchStringFieldOperator.contains);
			lSearchBasic.setName(entityId3);
			lSearch.setBasic(lSearchBasic);
				
			SearchResult locRes = _port.search(lSearch);
			
			if (locRes.getStatus().isIsSuccess()) {
				Record record;
				//log.info("\nNumber of locations found: " + locRes.getTotalRecords());
				if (locRes.getTotalRecords().intValue() > 0) {
					record = locRes.getRecordList().getRecord(0);
					Location locD = (Location)record;
						  
					 
					locRef.setName(locD.getName());
					locRef.setType(RecordType.location);
					locRef.setInternalId(locD.getInternalId());		
					 
				}
			} else {
				String err = locRes.getStatus().getStatusDetail(0).getMessage();
				throw new Exception("Could not find location in Netsuite: "+siteRef+" >> "+err);
			}
		}else{
			throw new Exception("Site reference number not set for invoiceId: "+invoiceD.getInvoiceId());
		}
		
		 
		//customer
		String customerNum = getSiteProperty("Customer Number:");
		RecordRef custRef = new RecordRef();
		 
		if(customerNum!=null){
			log.info("customerNum:"+customerNum);
			CustomerSearch cSearch = new CustomerSearch();	
			CustomerSearchBasic cSearchBasic = new CustomerSearchBasic();
			SearchStringField entityId4 = new SearchStringField();
			entityId4.setSearchValue(customerNum+" ");
			entityId4.setOperator(SearchStringFieldOperator.startsWith);
			cSearchBasic.setEntityId(entityId4);
			cSearch.setBasic(cSearchBasic);
				
			SearchResult custRes = _port.search(cSearch);
			 
			if (custRes.getStatus().isIsSuccess()) {
				Record record;
				//log.info("\nNumber of customers found: " + custRes.getTotalRecords());
				if (custRes.getTotalRecords().intValue() > 0) {
				
					RecordList recordList;
					boolean foundF1=false;
					for (int i = 1; custRes.getTotalPages() != null && i <= custRes.getTotalPages().intValue(); i++) {
						recordList = custRes.getRecordList();
						
						Customer custD = (Customer) (recordList.getRecord(0));
						if(custD.getEntityId()!=null){
							log.info("customer "+custD.getEntityId());
									
							custRef.setName(custD.getEntityId());
							custRef.setType(RecordType.customer);
							custRef.setInternalId(custD.getInternalId());
							foundF1 = true;
						}
					
					}
					
					if(!foundF1){
						String err = custRes.getStatus().getStatusDetail(0).getMessage();
						throw new Exception("Could not find customer in Netsuite: "+customerNum+" >> "+err);
					}
					 
				}
			} else {
				String err = custRes.getStatus().getStatusDetail(0).getMessage();
				throw new Exception("Could not find customer in Netsuite: "+customerNum+" >> "+err);
			}
		}else{
			throw new Exception("Customer Number: not set for invoiceId: "+invoiceD.getInvoiceId());
		}
	 
		//distributor/vendor
		String distRefCode = currOutboundReq.getDistributorCustomerReferenceCode(); 
		
		if(distRefCode!=null){
			VendorSearch vSearch = new VendorSearch();	
			VendorSearchBasic vSearchBasic = new VendorSearchBasic();
			SearchStringField entityId = new SearchStringField();
			entityId.setSearchValue(distRefCode);
			entityId.setOperator(SearchStringFieldOperator.is);
			vSearchBasic.setEntityId(entityId);
			vSearch.setBasic(vSearchBasic);
		
			SearchResult vendorRes = _port.search(vSearch);
		
			if (vendorRes.getStatus().isIsSuccess()) {
				Record record;
				log.info("\nNumber of vendors found: " + vendorRes.getTotalRecords());
				if (vendorRes.getTotalRecords().intValue() > 0) {
					
					RecordList recordList;
					boolean foundF1=false;
					for (int i = 1; vendorRes.getTotalPages() != null && i <= vendorRes.getTotalPages().intValue(); i++) {
						recordList = vendorRes.getRecordList();
						
						for (int j = 0; j < recordList.getRecord().length; j++) {
							if (recordList.getRecord(j) instanceof Vendor) {
								Vendor vendD = (Vendor) (recordList.getRecord(j));
								if(vendD.getEntityId()!=null){
									
									String vName = vendD.getEntityId();
									if(vName.equals(distRefCode)){
										//log.info("vName="+vName);
										log.info("distRefCode="+distRefCode);
										foundF1=true;
										RecordRef vendorRef = new RecordRef();
										vendorRef.setName(vendD.getEntityId());
										vendorRef.setType(RecordType.vendor);
										vendorRef.setInternalId(vendD.getInternalId());		
										inv.setEntity( vendorRef );
										break;
									}
								}
							}
						}
					
					}
					
					if(!foundF1){
						String err = vendorRes.getStatus().getStatusDetail(0).getMessage();
						throw new Exception("Could not find vendor in Netsuite: "+distRefCode+" >> "+err);
					}
					
				}
			} else {
				String err = vendorRes.getStatus().getStatusDetail(0).getMessage();
				throw new Exception("Could not find vendor in Netsuite: "+distRefCode+" >> "+err);
			}
		}else{
			throw new Exception("Dist customer reference code not set for invoice id: "+invoiceD.getInvoiceId());
		}

		//iterating through items and summing up cost center amounts
		Iterator itmIt = currOutboundReq.getInvoiceDetailDV().iterator();
		while(itmIt.hasNext()){
			InvoiceCustDetailRequestData det = (InvoiceCustDetailRequestData) itmIt.next();
			if(!Utility.isSet(det.getInvoiceDetailD().getERPAccountCode())){
				//throw new RuntimeException("ERP Account Code not set for invoice id: "+invoiceD.getInvoiceId());
				continue;
			}else{
				String erpCd = getCostCenterCode(det.getInvoiceDetailD().getERPAccountCode());
			
				addToCostCenter(erpCd, det,null);
			}
		}
		
		//sales tax
		if(Utility.isSet(currOutboundReq.getInvoiceData().getSalesTax())){ 
			addToCostCenter("6071", null,currOutboundReq.getInvoiceData().getSalesTax());
		}
		
		//freight+misc
		if(Utility.isSet(currOutboundReq.getInvoiceData().getFreight())){ 
			addToCostCenter("6070", null,currOutboundReq.getInvoiceData().getFreight());
		}
		if(Utility.isSet(currOutboundReq.getInvoiceData().getMiscCharges())){ 
			addToCostCenter("6070", null,currOutboundReq.getInvoiceData().getMiscCharges());
		}            

		 inv.setItemList(null);
		 
		 //Reference number= invoice num
		 String refNum = invoiceD.getInvoiceNum();
		 if(refNum!=null){
			 inv.setTranId(refNum);
		 }
		 
		 VendorBillExpense[] vExpenseA = new VendorBillExpense[ccMap.entrySet().size()];
		 itmIt = ccMap.keySet().iterator();
		 int count = 0;
		 while(itmIt.hasNext()){
			 InvoiceItemGroup iig = (InvoiceItemGroup) ccMap.get(itmIt.next());
			 VendorBillExpense vExpense = new VendorBillExpense();
			 
			 String accountCode = iig.erpCode;
			 //look up for cost center
			 AccountSearch aSearch = new AccountSearch();	
			 AccountSearchBasic aSearchBasic = new AccountSearchBasic();
			 SearchStringField entityId2 = new SearchStringField();
			 entityId2.setSearchValue(accountCode);
			 entityId2.setOperator(SearchStringFieldOperator.startsWith);
			 aSearchBasic.setNumber(entityId2);
			 aSearch.setBasic(aSearchBasic);
			 
			 SearchResult accRes = _port.search(aSearch);
			 
			 if (accRes.getStatus().isIsSuccess()) {
				 Record record;
				 log.info("\nNumber of accounts found: " + accRes.getTotalRecords());
				 if (accRes.getTotalRecords().intValue() > 0) {
					 record = accRes.getRecordList().getRecord(0);
					 Account accD = (Account)record;

					 RecordRef accRef = new RecordRef();
					 accRef.setName(accD.getAcctName());
					 accRef.setType(RecordType.account);
					 accRef.setInternalId(accD.getInternalId());		
					 vExpense.setAccount(accRef);
					 
					 BigDecimal thisAccAmount = iig.getSumAmount();
					 vExpense.setAmount(thisAccAmount.doubleValue());
					 
					 vExpense.setLocation(locRef);
					 vExpense.setCustomer(custRef);
				 }
			 } else {
				 String err = accRes.getStatus().getStatusDetail(0).getMessage();
				 throw new Exception("Could not find account in Netsuite: "+accountCode+" >> "+err);
			 }
			 
			 vExpense.setMemo(po_num);
			 vExpenseA[count] = vExpense;
			 count++;
		 }
		
		 VendorBillExpenseList expenseList = new VendorBillExpenseList();
		 expenseList.setExpense(vExpenseA);
		 inv.setExpenseList(expenseList);
		 
		 //check to see if there is a record for this dist and invoice num
		 boolean duplicateInvoice = searchInvoice(inv);
		 
		 if(!duplicateInvoice){
			 WriteResponse writeRes = _port.add(inv);
			 if (writeRes.getStatus().isIsSuccess()) {
				 log.info("\nThe invoice "+invoiceD.getInvoiceNum()+" has been added successfully.");
				 //RecordRef _ref = (RecordRef)(writeRes.getBaseRef());
			 
				 currOutboundReq.getInvoiceData().markAcknowledged();
				 appendIntegrationRequest(currOutboundReq.getInvoiceData());

			 } else {
				 String err = writeRes.getStatus().getStatusDetail(0).getMessage();
				 throw new Exception("Problem addding invoice >> "+err);
			 }
		 }else {
			 throw new Exception("Duplicate Invoice");
		 }
		}catch(InvalidCredentialsFault exc){
			exc.printStackTrace();
			throw new InvalidLoginException ("Invalid credentials: check username/password/account number");
		}catch(Exception exc){
			//exc.printStackTrace();
			throw new Exception (exc.getMessage());
		}
	}
	
	public boolean searchInvoice(VendorBill pInv) throws Exception {
		
		boolean found = false;
		try{
		TransactionSearch tSearch = new TransactionSearch();
		TransactionSearchBasic tSearchBasic = new TransactionSearchBasic();
		
		SearchEnumMultiSelectField type = new SearchEnumMultiSelectField();
		String[] inv = new String[1];
		inv[0] = TransactionType.__vendorBill;			
		type.setOperator(SearchEnumMultiSelectFieldOperator.anyOf);
		type.setSearchValue(inv);
		tSearchBasic.setType(type);
		
		if(pInv.getEntity() != null){
			SearchMultiSelectField ent = new SearchMultiSelectField();
			RecordRef[] rref = new RecordRef[1];
			rref[0] = pInv.getEntity();
			ent.setSearchValue(rref);
			ent.setOperator(SearchMultiSelectFieldOperator.anyOf);
			tSearchBasic.setEntity(ent);
		}
		
		if(pInv.getTranId() != null){
			SearchStringField tranId = new SearchStringField();
			tranId.setSearchValue(pInv.getTranId());
			tranId.setOperator(SearchStringFieldOperator.is);
			tSearchBasic.setTranId(tranId);
		}
		
		tSearch.setBasic(tSearchBasic);

		SearchResult res = _port.search(tSearch);
		if (res.getStatus().isIsSuccess()) {
			RecordList recordList;
			log.info("\nNumber of vendor bills found: " + res.getTotalRecords());
			if (res.getTotalRecords().intValue() > 0) {
		
				for (int i = 1; res.getTotalPages() != null && i <= res.getTotalPages().intValue(); i++) {
					recordList = res.getRecordList();
			
					for (int j = 0; j < recordList.getRecord().length; j++) {
						if (recordList.getRecord(j) instanceof VendorBill) {
							VendorBill invoice = (VendorBill) (recordList.getRecord(j));
					
							if(invoice.getTranId()!=null){
								log.info("refNum:    "+invoice.getTranId());
							}
							if(invoice.getEntity().getName()!=null){
								log.info("Vendor:    "+invoice.getEntity().getName());
							}
						}
					}
				}
				found = true;
			}
			
		} 
		}catch(Exception exc){
			exc.printStackTrace();
			throw new Exception(exc.getMessage());
		}
		return found;
	}
	
	private String getCostCenterCode(String erpAccountCd) throws APIServiceAccessException, NamingException{
		
		String ccCode = null;
		try{
			ccCode = erpAccountCd.substring(erpAccountCd.lastIndexOf(' ')+1);
			log.info("ccCode = "+ccCode);
			
		}catch(Exception exc){
			exc.printStackTrace();
		}
		return ccCode;
	}
	
	private void addToCostCenter(String erpAccountCd, InvoiceCustDetailRequestData det, BigDecimal amount){
		if((amount == null || amount.compareTo(new BigDecimal(0.00))<=0) && det == null){
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
	
	private String getSiteProperty(String desc){
		
		Iterator it = currOutboundReq.getSiteProperties().iterator();
		while(it.hasNext()){
			PropertyData prop = (PropertyData) it.next();
			if(desc.equals(prop.getShortDesc())){
				return prop.getValue();
			}
		}
		return null;
	}
	
	public void buildInterchangeTrailer() throws Exception {
		translator.writeOutputStream(stringBuffer.toString());
		super.buildInterchangeTrailer();
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
			//log.info("Amount "+amount);
			return Utility.addAmt(otherCharges, amount);
		}
	}

}
