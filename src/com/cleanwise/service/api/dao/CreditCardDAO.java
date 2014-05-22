/*
 * CreditCardDAO.java
 *
 * Created on November 3, 2004, 12:50 PM
 */

package com.cleanwise.service.api.dao;
import java.sql.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.log4j.Category;

/**
 * Centralizes all of the database interactions that need to happen when dealing with
 * the credit card system.  This class is generally shared with the credit card processing
 * application and the stJohn application.
 * @author  bstevens
 */
public class CreditCardDAO {
    private Connection mCon;
    private static Category log = Category.getInstance(CreditCardDAO.class.getName());

    /** Creates a new instance of CreditCardDAO */
    public CreditCardDAO(Connection pConnection) {
        mCon = pConnection;
    }

    /**
     *fetches the order creditcard desc data object based off the order credit card id
     */
    public OrderCreditCardDescData getOrderCreditCardDesc(int pOrderCreditCreditCardId)
    throws SQLException,DataNotFoundException{
        OrderCreditCardData ocd = OrderCreditCardDataAccess.select(mCon,pOrderCreditCreditCardId);
        return populateOrderCreditCardDesc(ocd,0);
    }

    public OrderCreditCardDescData getOrderCreditCardDescAndInvoice(int pCreditCardTransDataId)
    throws SQLException,DataNotFoundException{
        CreditCardTransData cctd = CreditCardTransDataAccess.select(mCon, pCreditCardTransDataId);
        OrderCreditCardData ocd = OrderCreditCardDataAccess.select(mCon,cctd.getOrderCreditCardId());
        return populateOrderCreditCardDesc(ocd, cctd.getInvoiceCustId());
    }
    
    /**
     *populates a InvoiceCreditCardTransViewVector for the supplied 
     *pOrderCreditCreditCardId
     */
    public InvoiceCreditCardTransViewVector getInvoiceCCTransList(int pOrderCreditCreditCardId)
    throws SQLException,DataNotFoundException{
    	String sql = "select ic.invoice_num, cct.add_date, cct.transaction_type_cd, cct.amount, "+
    			" cct.transaction_reference,  cct.auth_code, ic.invoice_cust_id  " +
    			" FROM CLW_CREDIT_CARD_TRANS cct, CLW_INVOICE_CUST ic " +
    			" where cct.order_credit_card_id = ? and " +
    			" cct.INVOICE_CUST_ID = ic.INVOICE_CUST_ID" ;
        PreparedStatement stmt = mCon.prepareStatement(sql);
    	stmt.setInt(1,pOrderCreditCreditCardId);
    	if (log.isDebugEnabled()) {
            log.debug("SQL:   pOrderCreditCreditCardId="+pOrderCreditCreditCardId);
            log.debug("SQL: " + sql);
        }
    	 ResultSet rs = stmt.executeQuery();
    	 
    	 InvoiceCreditCardTransViewVector invCCTransList = 
    		 new InvoiceCreditCardTransViewVector();
    	 while(rs.next()){
    		 InvoiceCreditCardTransView invCCTrans = new InvoiceCreditCardTransView();
    		 invCCTrans.setInvoiceNum(rs.getString(1));
    		 invCCTrans.setAddDate(rs.getDate(2));
    		 invCCTrans.setTransactionTypeCd(rs.getString(3));
    		 invCCTrans.setAmount(rs.getBigDecimal(4));
    		 invCCTrans.setTransactionReference(rs.getString(5));
    		 invCCTrans.setAuthCode(rs.getString(6));
    		 invCCTrans.setInvoiceCustId(rs.getInt(7));
    		 
    		 invCCTransList.add(invCCTrans);
    	 }
        return invCCTransList;
    }

    /**
     *populates a OrderCreditCardDescData from the supplied OrderCreditCard object
     */
    public OrderCreditCardDescData populateOrderCreditCardDesc(OrderCreditCardData pOrderCreditCreditCard, int pInvoiceCustId)
    throws SQLException,DataNotFoundException{

        OrderCreditCardDescData desc = new OrderCreditCardDescData();
        desc.setTransactionHistory(new CreditCardTransDataVector());
        desc.setOrderCreditCardData(pOrderCreditCreditCard);
        OrderData ord = OrderDataAccess.select(mCon,pOrderCreditCreditCard.getOrderId());
        desc.setOrderData(ord);
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(CreditCardTransDataAccess.ORDER_CREDIT_CARD_ID,
        pOrderCreditCreditCard.getOrderCreditCardId());
        if (pInvoiceCustId > 0 ) {
          crit.addEqualTo(CreditCardTransDataAccess.INVOICE_CUST_ID,
          pInvoiceCustId);
          InvoiceCustData icd = InvoiceCustDataAccess.select(mCon, pInvoiceCustId);
          desc.setInvoiceCustData(icd);
        }
        CreditCardTransDataVector ccTrans = CreditCardTransDataAccess.select(mCon,crit);
        Iterator it = ccTrans.iterator();
        while(it.hasNext()){
            CreditCardTransData aTrans = (CreditCardTransData) it.next();
            if(pInvoiceCustId > 0 && aTrans.getInvoiceCustId() == pInvoiceCustId){
                if(desc.getInvoiceTransactionData() == null){
                    desc.setInvoiceTransactionData(aTrans);
                }else{
                    throw new SQLException("Found multiple invoice transactions for invoice id "+pInvoiceCustId);
                }
            }else if(RefCodeNames.CREDIT_CARD_TRANS_TYPE_CD.AUTHORIZATION.equals(aTrans.getTransactionTypeCd())){
                if(desc.getApprovalTransactionData() == null){
                    desc.setApprovalTransactionData(aTrans);
                }else{
                    throw new SQLException("Found multiple authorization transactions for order credit card id: "+pOrderCreditCreditCard.getOrderCreditCardId());
                }
            }else{
                desc.getTransactionHistory().add(aTrans);
            }
        }
        return desc;
    }


    /**
     *Returns a list of @see OrderCreditCardDesc objects with the invoice not populated
     *that need authorization
     *@throws DataNotFoundException when the order that a cc relates to cannot be found
     */
    public List getOrderCreditCardsNeedingApproval(int pStoreId) throws SQLException,DataNotFoundException{
        List retList = new ArrayList();
        DBCriteria crit = new DBCriteria();
        crit.addJoinCondition(OrderDataAccess.CLW_ORDER,OrderDataAccess.ORDER_ID,
                OrderCreditCardDataAccess.CLW_ORDER_CREDIT_CARD,OrderCreditCardDataAccess.ORDER_ID);
        crit.addJoinTableEqualTo(OrderCreditCardDataAccess.CLW_ORDER_CREDIT_CARD,OrderCreditCardDataAccess.AUTH_STATUS_CD, RefCodeNames.CREDIT_CARD_AUTH_STATUS.PENDING);
        ArrayList goodOrderStatusCds = new ArrayList();
        goodOrderStatusCds.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
        goodOrderStatusCds.add(RefCodeNames.ORDER_STATUS_CD.INVOICED);
        goodOrderStatusCds.add(RefCodeNames.ORDER_STATUS_CD.ORDERED);
        goodOrderStatusCds.add(RefCodeNames.ORDER_STATUS_CD.PRE_PROCESSED);
        goodOrderStatusCds.add(RefCodeNames.ORDER_STATUS_CD.PROCESS_ERP_PO);
        goodOrderStatusCds.add(RefCodeNames.ORDER_STATUS_CD.READY_TO_SEND_TO_CUST_SYS);
        goodOrderStatusCds.add(RefCodeNames.ORDER_STATUS_CD.SENT_TO_CUST_SYSTEM);
        goodOrderStatusCds.add(RefCodeNames.ORDER_STATUS_CD.WAITING_FOR_ACTION);
        crit.addJoinTableEqualTo(OrderDataAccess.CLW_ORDER,OrderDataAccess.STORE_ID,pStoreId);
        crit.addJoinTableOneOf(OrderDataAccess.CLW_ORDER,OrderDataAccess.ORDER_STATUS_CD,goodOrderStatusCds);
        crit.addJoinTableEqualTo(OrderDataAccess.CLW_ORDER,OrderDataAccess.STORE_ID,pStoreId);
        crit.addDataAccessForJoin(new OrderCreditCardDataAccess());
        List creditCards = JoinDataAccess.select(mCon,crit);
        Iterator it = creditCards.iterator();
        while(it.hasNext()){
            OrderCreditCardData ocd = (OrderCreditCardData) ((List)it.next()).get(0);
            retList.add(populateOrderCreditCardDesc(ocd,0));
        }
        return retList;
    }

    /**
     *Returns a list of @see OrderCreditCardDesc objects with the invoice populated
     *that need charging or crediting.
     *@throws DataNotFoundException when the order that a cc relates to cannot be found
     */
    public List getOrderCreditCardsNeedingCharging(int pStoreId) throws SQLException,DataNotFoundException{
        List retList = new ArrayList();
        DBCriteria crit = new DBCriteria();
        crit.addJoinCondition(OrderCreditCardDataAccess.CLW_ORDER_CREDIT_CARD,
                OrderCreditCardDataAccess.ORDER_ID,
                InvoiceCustDataAccess.CLW_INVOICE_CUST,
                InvoiceCustDataAccess.ORDER_ID);
        crit.addJoinTableEqualTo(InvoiceCustDataAccess.CLW_INVOICE_CUST,
                InvoiceCustDataAccess.INVOICE_STATUS_CD,
                RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED);

        crit.addJoinCondition(OrderDataAccess.CLW_ORDER,OrderDataAccess.ORDER_ID,
                OrderCreditCardDataAccess.CLW_ORDER_CREDIT_CARD,OrderCreditCardDataAccess.ORDER_ID);
        crit.addJoinTableEqualTo(OrderDataAccess.CLW_ORDER,OrderDataAccess.STORE_ID,pStoreId);
        crit.addDataAccessForJoin(new InvoiceCustDataAccess());
        crit.addDataAccessForJoin(new OrderCreditCardDataAccess());
        List results = JoinDataAccess.select(mCon, crit);
        Iterator it = results.iterator();
        while(it.hasNext()){
            Iterator subIt = ((List)it.next()).iterator();
            InvoiceCustData inv = (InvoiceCustData) subIt.next();
            OrderCreditCardData cc = (OrderCreditCardData) subIt.next();
            OrderCreditCardDescData desc = populateOrderCreditCardDesc(cc,inv.getInvoiceCustId());
            desc.setInvoiceCustData(inv);
            retList.add(desc);
        }
        return retList;
    }

    /**
     *Updates the order credit card data and inserts a note relating to this order credit card
     */
    public void processOrderCreditCardDataUpdate
            (String pMessage, OrderCreditCardDescData pOcd)
            throws SQLException{
        if(pMessage != null){
            insertOrderCreditCardNote(pMessage, pOcd.getOrderCreditCardData());
        }
        OrderCreditCardDataAccess.update(mCon, pOcd.getOrderCreditCardData());
        if(pOcd.getApprovalTransactionData() != null){
            pOcd.getApprovalTransactionData().setOrderCreditCardId(pOcd.getOrderCreditCardData().getOrderCreditCardId());
            if(pOcd.getApprovalTransactionData().getCreditCardTransId() == 0){
                CreditCardTransDataAccess.insert(mCon,pOcd.getApprovalTransactionData());
            }else{
                CreditCardTransDataAccess.update(mCon,pOcd.getApprovalTransactionData());
            }
        }
        if(pOcd.getInvoiceTransactionData() != null){
            pOcd.getInvoiceTransactionData().setOrderCreditCardId(pOcd.getOrderCreditCardData().getOrderCreditCardId());
            if(pOcd.getInvoiceTransactionData().getInvoiceCustId() == 0){
                pOcd.getInvoiceTransactionData().setInvoiceCustId(pOcd.getInvoiceCustData().getInvoiceCustId() );
            }else if(pOcd.getInvoiceTransactionData().getInvoiceCustId() != pOcd.getInvoiceCustData().getInvoiceCustId()){
                throw new IllegalStateException("Invoice in desc data does not match invoice referenced in invoice transcation data");
            }

            if(pOcd.getInvoiceTransactionData().getCreditCardTransId() == 0){
                CreditCardTransDataAccess.insert(mCon,pOcd.getInvoiceTransactionData());
            }else{
                CreditCardTransDataAccess.update(mCon,pOcd.getInvoiceTransactionData());
            }
        }
        if(pOcd.getInvoiceCustData() != null && pOcd.getInvoiceCustData().isDirty()){
            if(pOcd.getInvoiceCustData().getInvoiceCustId() == 0){
                throw new IllegalStateException("Found dirty invoice cust data reference that does not exist in data base!");
            }else{
                InvoiceCustDataAccess.update(mCon, pOcd.getInvoiceCustData());
            }
        }
    }

    /**
     *Inserts an error note and attaches it to the order.  As long as there is only one cc allowed per order
     *this works fine as it is implicitly attached to the OrderCreditCard data.
     */
    private void insertOrderCreditCardNote(String pMessage, OrderCreditCardData pOcd)
    throws SQLException{
        OrderPropertyData prop = OrderPropertyData.createValue();
        prop.setOrderId(pOcd.getOrderId());
        prop.setAddBy("credit card DAO");
        prop.setModBy("credit card DAO");
        prop.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        prop.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        prop.setShortDesc("Credit Card Note");
        prop.setValue(pMessage);
        OrderPropertyDataAccess.insert(mCon, prop);
    }

    /**
     *Returns the order credit card data object for the supplied order id.  If there are no
     *order credit card recorecs attached to the supplied order returns null.
     *@throws SQLException if something goes wrng with the database, or there are multiple
     *  order credit card records for the supplied order id.
     */
    public OrderCreditCardData getOrderCreditCardDataFromOrderId(int pOrderId)
    throws SQLException{
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(OrderCreditCardDataAccess.ORDER_ID,pOrderId);
        OrderCreditCardDataVector ov = OrderCreditCardDataAccess.select(mCon,crit);
        if(ov.isEmpty()){
            return null;
        }else if(ov.size() == 1){
            return (OrderCreditCardData) ov.get(0);
        }else{
            throw new SQLException("Found multiple ("+ov.size()+") OrderCreditCardData objects for order id: "+pOrderId);
        }

    }

    /**
     *Interfaces into the remittance sub system to close the invoice out in the erp system, as when the card was charged we
     *have effectivly closed this invoice.
     */
    public void closeOutInvoice(OrderCreditCardDescData pOcd) throws Exception{

        java.util.Date transactionDate = pOcd.getInvoiceTransactionData().getModDate();
        RemittanceData hdr = RemittanceData.createValue();
        RemittanceDetailData det = RemittanceDetailData.createValue();

        //now populate the header record
        hdr.setCheckDate(transactionDate);
        try{
          BusEntityData acct = BusEntityDataAccess.select(mCon,pOcd.getInvoiceCustData().getAccountId());
          hdr.setPayeeErpAccount(acct.getErpNum());

        }catch(Exception e){
          //just catch exception, if there is no erp number this will be handled better by the exception processing latter
        }
        hdr.setPaymentPostDate(transactionDate);
        String transactionNumber = "CC-";
        //shouldn't be 0, but on the off chance that it is use the order credit card id.
        //This is not really a critical number, just useful for reference
        if(pOcd.getInvoiceTransactionData().getCreditCardTransId() == 0){
            transactionNumber+="OCD-"+pOcd.getOrderCreditCardData().getOrderCreditCardId();
        }else{
            transactionNumber+=pOcd.getInvoiceTransactionData().getCreditCardTransId();
        }

        hdr.setPaymentReferenceNumber(transactionNumber);

        hdr.setRemittanceStatusCd(RefCodeNames.REMITTANCE_STATUS_CD.RECIEVED);
        hdr.setStoreId(pOcd.getInvoiceCustData().getStoreId());
        hdr.setTotalPaymentAmount(pOcd.getInvoiceTransactionData().getAmount());
        hdr.setTransactionDate(transactionDate);
        hdr.setTransactionReference("CreditCardDAO");

        RemittanceData hdr2 = 	checkForTransHeader(pOcd, transactionNumber);
        if ( null == hdr2 ) {
          //insert the header record
          hdr.setAddBy("CreditCardDAO");
          hdr.setModBy("CreditCardDAO");
          hdr = RemittanceDataAccess.insert(mCon, hdr);
        } else {
          hdr = hdr2;
        }

        //now populate the detail record
        det.setAddBy("CreditCardDAO");
        det.setModBy("CreditCardDAO");
        det.setInvoiceNumber(pOcd.getInvoiceCustData().getInvoiceNum());
	String invtype = pOcd.getInvoiceCustData().getInvoiceType();
	if ( null == invtype ) {
	    invtype = "";
	}
	if ( invtype.equals(RefCodeNames.INVOICE_TYPE_CD.IN)){
	    det.setInvoiceType(RefCodeNames.REMITTANCE_INVOICE_TYPE_CD.INVOICE);
	} else if (invtype.equals(RefCodeNames.INVOICE_TYPE_CD.CR)){
	    det.setInvoiceType(RefCodeNames.REMITTANCE_INVOICE_TYPE_CD.CREDIT);
	} else {
	    det.setInvoiceType("-");
	}

        if(pOcd.getInvoiceTransactionData().getAmount() != null){
            det.setNetAmount(pOcd.getInvoiceTransactionData().getAmount().abs());
        }
        det.setRemittanceId(hdr.getRemittanceId());
        det.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.RECIEVED);
        if(RefCodeNames.INVOICE_TYPE_CD.CR.equals(pOcd.getInvoiceCustData().getInvoiceType())){
            det.setReferenceInvoiceNumber(pOcd.getInvoiceCustData().getOriginalInvoiceNum());
        }

        if ( ccInvoiceAlreadyThere(pOcd, hdr.getRemittanceId()) ) {
          return;
        }
        //insert the detail record
        det = RemittanceDetailDataAccess.insert(mCon,det);
    }

    private boolean ccInvoiceAlreadyThere(OrderCreditCardDescData pOcd,
    int pHeaderId)  throws Exception  {
      DBCriteria  crit = new DBCriteria();
      crit.addEqualTo(RemittanceDetailDataAccess.INVOICE_NUMBER,
      pOcd.getInvoiceCustData().getInvoiceNum());
      crit.addEqualTo(RemittanceDetailDataAccess.REMITTANCE_ID, pHeaderId );
      RemittanceDetailDataVector v = RemittanceDetailDataAccess.select(mCon, crit);
      if ( null != v && v.size() > 0 ) {
        return true;
      }
      return false;
    }

    private RemittanceData checkForTransHeader(OrderCreditCardDescData pOcd,
    String transactionNumber) throws Exception
		{
      DBCriteria  crit = new DBCriteria();
      crit.addEqualTo(RemittanceDataAccess.PAYMENT_REFERENCE_NUMBER,
      transactionNumber);
      crit.addEqualTo(RemittanceDataAccess.STORE_ID,
      pOcd.getInvoiceCustData().getStoreId());
      RemittanceDataVector v = RemittanceDataAccess.select(mCon, crit);
      if ( null != v && v.size() > 0 ) {
        return (RemittanceData)v.get(0);
      }
      return null;
    }

}
