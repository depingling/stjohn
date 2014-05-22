package com.cleanwise.service.api.session;

import javax.naming.NamingException;
import javax.ejb.*;
import java.rmi.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;

/*
 *The <code>RemittanceBean</code>
 *@see com.cleanwise.service.api.session.Remittance
 * Remittance.java
 *
 * @author  bstevens
 */
public class RemittanceBean extends UtilityServicesAPI {
    /**
     *  Default <code>ejbCreate</code> method.
     *
     *@exception  CreateException  if an error occurs
     *@exception  RemoteException  if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException { }
    
    /** Creates a new instance of PurchaseOrderBean */
    public RemittanceBean() {
    }
    
    
    /**
     * Adds a purchase order to the database
     *
     * @param pRemittanceData a <code>RemittanceData</code> value
     * @param pUser username
     * @return a <code>RemittanceData</code> value
     * @exception RemoteException if an error occurs
     */
    public RemittanceData addRemittance(RemittanceData pRemittanceData, String pUser)
    throws RemoteException {
        return updateRemittance(pRemittanceData,pUser);
    }
    
    /**
     * Updates the Remittance information values to be used by the request.
     * @param pRemittanceData  the RemittanceData .
     * @param pUser username
     * @return a <code>RemittanceData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public RemittanceData updateRemittance(RemittanceData pRemittanceData,String pUser)
    throws RemoteException {
        logDebug("in updateRemittance method");
        Connection conn = null;
        try {
            conn = getConnection();
            
            logDebug("dirty flag: " + pRemittanceData.isDirty());
            if (pRemittanceData.isDirty()) {
                if (pRemittanceData.getRemittanceId() == 0) {
                    pRemittanceData.setAddBy(pUser);
                    pRemittanceData = RemittanceDataAccess.insert(conn, pRemittanceData);
                } else {
                    pRemittanceData.setModBy(pUser);
                    pRemittanceData.setModDate(new Date());
                    RemittanceDataAccess.update(conn, pRemittanceData);
                }
            }
            
        } catch (Exception e) {
            throw new RemoteException("updateRemittance: "+e.getMessage());
        } finally {
            closeConnection(conn);
        }
        
        return pRemittanceData;
    }
    
    /**
     *Return remittanceProperty objects that have a remittance, but no remittance
     *detail associations.  i.e. the remittanceDetailId = 0 and the 
     *remittanceId = pRemittanceId
     *@param pRemittanceId the remittance id
     *@param pPropertyType the property type you want to filter for, may be left null
     *      to return all properties.
     *@return a <code>RemittancePropertyDataVector</code> value
     *@throws            RemoteException Required by EJB 1.0
     */
    public RemittancePropertyDataVector getRemittanceOnlyPropertiesByRemittanceId(int pRemittanceId,String pPropertyType)
    throws RemoteException{
        Connection conn = null;
        try{
            conn = getConnection();
            DBCriteria crit=new DBCriteria();
            crit.addEqualTo(RemittancePropertyDataAccess.REMITTANCE_ID,pRemittanceId);
            crit.addEqualTo(RemittancePropertyDataAccess.REMITTANCE_DETAIL_ID,0);
            if (pPropertyType != null){
                crit.addEqualTo(RemittancePropertyDataAccess.REMITTANCE_PROPERTY_TYPE_CD,pPropertyType);
            }
            //only grab active ones
            crit.addEqualTo(RemittancePropertyDataAccess.REMITTANCE_PROPERTY_STATUS_CD,
                RefCodeNames.REMITTANCE_PROPERTY_STATUS_CD.ACTIVE);
            return RemittancePropertyDataAccess.select(conn,crit);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }
    
    /**
     *Return a populated RemittanceDescViewVector object
     *@param pRemittanceCriteria a RemittanceCriteriaView object
     *@param pPopulateDetail if true the RemittanceDetailDataVector propertie
     *  will be populated, otherwise it will be left as a zero length list.
     *@return a <code>RemittanceDescViewVector</code> value
     *@throws            RemoteException Required by EJB 1.0
     */
    public RemittanceDescViewVector getRemittanceDescList
    (RemittanceCriteriaView pRemittanceCriteria,boolean pPopulateDetail)
    throws RemoteException{
        Connection conn=null;
        RemittanceDescViewVector result = new RemittanceDescViewVector();
        try{
            conn=getConnection();
            RemittanceDataVector rdv = getRemittanceData(pRemittanceCriteria);
            for(int i=0,len=rdv.size();i<len;i++){
                RemittanceData rd = (RemittanceData) rdv.get(i);
                RemittanceDescView desc = RemittanceDescView.createValue();
                desc.setRemittanceData(rd);
                if (pPopulateDetail){
                    //make sure this value is populated for the detail
                    pRemittanceCriteria.setRemittanceId(rd.getRemittanceId());
                    DBCriteria crit = constructRemittanceDetailCrit(pRemittanceCriteria);
                    desc.setRemittanceDetailDataVector(RemittanceDetailDataAccess.select(conn,crit));
                }
                desc.setRemittanceProperties(getRemittanceOnlyPropertiesByRemittanceId(rd.getRemittanceId(),null));
                result.add(desc);
            }
            return result;
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }

    
    /**
     *Return a populated RemittanceDescView object
     *@param pRemittanceId a remittance id
     *@return a <code>RemittanceDescView</code> value
     *@throws            RemoteException Required by EJB 1.0
     */
    public RemittanceDescView getRemittanceDescById(int pRemittanceId)
    throws RemoteException,DataNotFoundException{
        Connection conn=null;
        try{
            RemittanceDescView result = RemittanceDescView.createValue();
            conn=getConnection();
            result.setRemittanceData(RemittanceDataAccess.select(conn,pRemittanceId));
            result.setRemittanceDetailDataVector(getRemittanceDetailDataByRemitId(pRemittanceId,conn));
            result.setRemittanceProperties(getRemittanceOnlyPropertiesByRemittanceId(pRemittanceId,null));
            return result;
        }catch (DataNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }
   
    
    private RemittanceDetailDataVector getRemittanceDetailDataByRemitId(int pRemittanceId,Connection pConn)
    throws SQLException {
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(RemittanceDetailDataAccess.REMITTANCE_ID,pRemittanceId);
        return RemittanceDetailDataAccess.select(pConn,crit);
    }
    
    private DBCriteria constructRemittanceCrit(RemittanceCriteriaView pRemittanceCriteria){
        boolean setCriteria = false;
        DBCriteria remCrit = new DBCriteria();
        if (Utility.isSet(pRemittanceCriteria.getRemittanceAddBy())){
            remCrit.addEqualToIgnoreCase(RemittanceDataAccess.ADD_BY,pRemittanceCriteria.getRemittanceAddBy());
            setCriteria = true;
        }
        if (pRemittanceCriteria.getRemittanceAddDate() != null){
            remCrit.addGreaterOrEqual(RemittanceDataAccess.ADD_DATE,pRemittanceCriteria.getRemittanceAddDate());
            Calendar cal = Calendar.getInstance();
            cal.setTime(pRemittanceCriteria.getRemittanceAddDate());
            cal.add(Calendar.DAY_OF_YEAR, 1);
            remCrit.addLessThan(RemittanceDataAccess.ADD_DATE,cal.getTime());
            remCrit.addEqualTo(RemittanceDataAccess.ADD_DATE,pRemittanceCriteria.getRemittanceAddDate());
            setCriteria = true;
        }
        if (pRemittanceCriteria.getRemittanceStartAddDate() != null){
            remCrit.addGreaterOrEqual(RemittanceDataAccess.ADD_DATE,pRemittanceCriteria.getRemittanceStartAddDate());
        }
        if (pRemittanceCriteria.getRemittanceEndAddDate() != null){
            remCrit.addLessOrEqual(RemittanceDataAccess.ADD_DATE,pRemittanceCriteria.getRemittanceStartAddDate());
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittanceModBy())){
            remCrit.addEqualToIgnoreCase(RemittanceDataAccess.MOD_BY,pRemittanceCriteria.getRemittanceModBy());
            setCriteria = true;
        }
        if (pRemittanceCriteria.getRemittanceModDate() != null){
            remCrit.addEqualTo(RemittanceDataAccess.MOD_DATE,pRemittanceCriteria.getRemittanceModDate());
            setCriteria = true;
        }
        if (pRemittanceCriteria.getRemittanceCheckDate() != null){
            remCrit.addEqualTo(RemittanceDataAccess.CHECK_DATE,pRemittanceCriteria.getRemittanceCheckDate());
            setCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittanceCreditDebit())){
            remCrit.addEqualToIgnoreCase(RemittanceDataAccess.CREDIT_DEBIT,pRemittanceCriteria.getRemittanceCreditDebit());
            setCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittanceHandlingCode())){
            remCrit.addEqualToIgnoreCase(RemittanceDataAccess.HANDLING_CODE,pRemittanceCriteria.getRemittanceHandlingCode());
            setCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittanceStatusCd())){
            remCrit.addEqualToIgnoreCase(RemittanceDataAccess.REMITTANCE_STATUS_CD,pRemittanceCriteria.getRemittanceStatusCd());
            setCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittancePayeeBank())){
            remCrit.addEqualToIgnoreCase(RemittanceDataAccess.PAYEE_BANK,pRemittanceCriteria.getRemittancePayeeBank());
            setCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittancePayeeBankAccount())){
            remCrit.addEqualToIgnoreCase(RemittanceDataAccess.PAYEE_BANK_ACCOUNT,pRemittanceCriteria.getRemittancePayeeBankAccount());
            setCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittancePayeeErpAccount())){
            remCrit.addEqualTo(RemittanceDataAccess.PAYEE_ERP_ACCOUNT,pRemittanceCriteria.getRemittancePayeeErpAccount());
            setCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittancePayerBank())){
            remCrit.addEqualToIgnoreCase(RemittanceDataAccess.PAYER_BANK,pRemittanceCriteria.getRemittancePayerBank());
            setCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittancePayerBankAccount())){
            remCrit.addEqualToIgnoreCase(RemittanceDataAccess.PAYER_BANK_ACCOUNT,pRemittanceCriteria.getRemittancePayerBankAccount());
            setCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittancePayerId())){
            remCrit.addEqualToIgnoreCase(RemittanceDataAccess.PAYER_ID,pRemittanceCriteria.getRemittancePayerId());
            setCriteria = true;
        }
        if (pRemittanceCriteria.getRemittancePaymentPostDate() != null){
            remCrit.addEqualTo(RemittanceDataAccess.PAYMENT_POST_DATE,pRemittanceCriteria.getRemittancePaymentPostDate());
            setCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittancePaymentReferenceNumber())){
            remCrit.addEqualToIgnoreCase(RemittanceDataAccess.PAYMENT_REFERENCE_NUMBER,pRemittanceCriteria.getRemittancePaymentReferenceNumber());
            setCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittancePaymentReferenceNumberType())){
            remCrit.addEqualToIgnoreCase(RemittanceDataAccess.PAYMENT_REFERENCE_NUMBER_TYPE,pRemittanceCriteria.getRemittancePaymentReferenceNumberType());
            setCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittancePaymentType())){
            remCrit.addEqualToIgnoreCase(RemittanceDataAccess.PAYMENT_TYPE,pRemittanceCriteria.getRemittancePaymentType());
            setCriteria = true;
        }
        if (pRemittanceCriteria.getRemittanceId() != 0){
            remCrit.addEqualTo(RemittanceDataAccess.REMITTANCE_ID,pRemittanceCriteria.getRemittanceId());
            setCriteria = true;
        }
        if (pRemittanceCriteria.getRemittanceStoreId() != 0){
            remCrit.addEqualTo(RemittanceDataAccess.STORE_ID,pRemittanceCriteria.getRemittanceStoreId());
            setCriteria = true;
        }
        if (pRemittanceCriteria.getRemittanceTotalPaymentAmount() != null){
            remCrit.addEqualTo(RemittanceDataAccess.TOTAL_PAYMENT_AMOUNT,pRemittanceCriteria.getRemittanceTotalPaymentAmount());
            setCriteria = true;
        }
        if (pRemittanceCriteria.getRemittanceTransactionDate() != null){
            Calendar cal = Calendar.getInstance();
            cal.setTime(pRemittanceCriteria.getRemittanceTransactionDate());
            cal.add(Calendar.DAY_OF_YEAR, 1);
            SimpleDateFormat sdf = new SimpleDateFormat("ddMM");
            String lDateS = sdf.format(pRemittanceCriteria.getRemittanceTransactionDate());
            remCrit.addEqualTo("TO_CHAR("+RemittanceDataAccess.TRANSACTION_DATE+",'DDMM')",lDateS);
            
            //because the transaction date is only a day and month presision
            //we want to add in a year restrivtion if the add date was not specified
            //as we are going to use that as a 6 month on either side window.
            if (pRemittanceCriteria.getRemittanceAddDate() == null){
                cal.setTime(pRemittanceCriteria.getRemittanceTransactionDate());
		cal.add(Calendar.MONTH,-6);
                java.util.Date begAddDate = cal.getTime();
                cal.setTime(pRemittanceCriteria.getRemittanceTransactionDate());
                cal.add(Calendar.MONTH,6);
                java.util.Date endAddDate = cal.getTime();
                remCrit.addGreaterThan(RemittanceDataAccess.ADD_DATE,begAddDate);
                remCrit.addLessOrEqual(RemittanceDataAccess.ADD_DATE,endAddDate);
            }
            setCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittanceTransactionReference())){
            remCrit.addEqualToIgnoreCase(RemittanceDataAccess.TRANSACTION_REFERENCE,pRemittanceCriteria.getRemittanceTransactionReference());
            setCriteria = true;
        }
        if (pRemittanceCriteria.getRemittanceInErrorState() != null){
            ArrayList errStatus = new ArrayList();
            errStatus.add(RefCodeNames.REMITTANCE_STATUS_CD.RECIEVED_ERROR);
            errStatus.add(RefCodeNames.REMITTANCE_STATUS_CD.PROCESS_ERP_ERROR);
            errStatus.add(RefCodeNames.REMITTANCE_STATUS_CD.INFORMATION_ONLY);
            errStatus.add(RefCodeNames.REMITTANCE_STATUS_CD.PROCESSING);
            if(pRemittanceCriteria.getRemittanceInErrorState().equals(Boolean.TRUE)){
                
                DBCriteria remCrit2 = new DBCriteria();
                remCrit2.addOneOf(RemittanceDataAccess.REMITTANCE_STATUS_CD,errStatus);
                RemittanceCriteriaView tmpRCV = RemittanceCriteriaView.createValue();
                tmpRCV.setRemittanceDetailInErrorState(Boolean.TRUE);
                String sql = "SELECT " + RemittanceDetailDataAccess.REMITTANCE_ID + " FROM " +
                    RemittanceDetailDataAccess.CLW_REMITTANCE_DETAIL+ " WHERE " + 
                    constructRemittanceDetailCrit(tmpRCV).getSqlClause();
                DBCriteria orCrit = new DBCriteria();
                orCrit.addOneOf(RemittanceDataAccess.REMITTANCE_ID,sql);
                remCrit2.addOrCriteria(orCrit);
                
                remCrit.addCondition("("+remCrit2.getWhereClause()+")");
            }else{
                remCrit.addNotOneOf(RemittanceDataAccess.REMITTANCE_STATUS_CD,errStatus);
            }
            setCriteria = true;
        }
        if (!setCriteria){
            return null;
        }
        return remCrit;
    }
    
    private DBCriteria constructRemittanceDetailCrit(RemittanceCriteriaView pRemittanceCriteria){
        boolean setDetailCriteria = false;
        DBCriteria detCrit = new DBCriteria();
        if (Utility.isSet(pRemittanceCriteria.getRemittanceDetailAddBy())){
            detCrit.addEqualToIgnoreCase(RemittanceDetailDataAccess.ADD_BY,pRemittanceCriteria.getRemittanceDetailAddBy());
            setDetailCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittanceDetailModBy())){
            detCrit.addEqualToIgnoreCase(RemittanceDetailDataAccess.MOD_BY,pRemittanceCriteria.getRemittanceDetailModBy());
            setDetailCriteria = true;
        }
        if (pRemittanceCriteria.getRemittanceDetailModDate() != null){
            detCrit.addEqualTo(RemittanceDetailDataAccess.MOD_DATE,pRemittanceCriteria.getRemittanceDetailModDate());
            setDetailCriteria = true;
        }
        if (pRemittanceCriteria.getRemittanceDetailAddDate() != null){
            detCrit.addEqualTo(RemittanceDetailDataAccess.ADD_DATE,pRemittanceCriteria.getRemittanceDetailAddDate());
            setDetailCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittanceDetailStatusCd())){
            detCrit.addEqualToIgnoreCase(RemittanceDetailDataAccess.REMITTANCE_DETAIL_STATUS_CD,pRemittanceCriteria.getRemittanceDetailStatusCd());
            setDetailCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittanceDetailCustomerSupplierNumber())){
            detCrit.addEqualToIgnoreCase(RemittanceDetailDataAccess.CUSTOMER_SUPPLIER_NUMBER,pRemittanceCriteria.getRemittanceDetailCustomerSupplierNumber());
            setDetailCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittanceDetailCustomerPoNumber())){
            detCrit.addEqualToIgnoreCase(RemittanceDetailDataAccess.CUSTOMER_PO_NUMBER,pRemittanceCriteria.getRemittanceDetailCustomerPoNumber());
            setDetailCriteria = true;
        }
        if (pRemittanceCriteria.getRemittanceDetailOrigInvoiceAmount() != null){
            detCrit.addEqualTo(RemittanceDetailDataAccess.ORIG_INVOICE_AMOUNT,pRemittanceCriteria.getRemittanceDetailOrigInvoiceAmount());
            setDetailCriteria = true;
        }
        if (pRemittanceCriteria.getRemittanceDetailNetAmount() != null){
            detCrit.addEqualTo(RemittanceDetailDataAccess.NET_AMOUNT,pRemittanceCriteria.getRemittanceDetailNetAmount());
            setDetailCriteria = true;
        }
        if (pRemittanceCriteria.getRemittanceDetailDiscountAmount() != null){
            detCrit.addEqualTo(RemittanceDetailDataAccess.DISCOUNT_AMOUNT,pRemittanceCriteria.getRemittanceDetailDiscountAmount());
            setDetailCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittanceDetailInvoiceType())){
            detCrit.addEqualToIgnoreCase(RemittanceDetailDataAccess.INVOICE_TYPE,pRemittanceCriteria.getRemittanceDetailInvoiceType());
            setDetailCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittanceDetailInvoiceNumber())){
            detCrit.addEqualToIgnoreCase(RemittanceDetailDataAccess.INVOICE_NUMBER,pRemittanceCriteria.getRemittanceDetailInvoiceNumber());
            setDetailCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittanceDetailSiteReference())){
            detCrit.addEqualToIgnoreCase(RemittanceDetailDataAccess.SITE_REFERENCE,pRemittanceCriteria.getRemittanceDetailSiteReference());
            setDetailCriteria = true;
        }
        if (pRemittanceCriteria.getRemittanceDetailId() != 0){
            detCrit.addEqualTo(RemittanceDetailDataAccess.REMITTANCE_DETAIL_ID,pRemittanceCriteria.getRemittanceDetailId());
            setDetailCriteria = true;
        }
        if (pRemittanceCriteria.getRemittanceId() != 0){
            detCrit.addEqualTo(RemittanceDetailDataAccess.REMITTANCE_ID,pRemittanceCriteria.getRemittanceId());
            setDetailCriteria = true;
        }
	if (pRemittanceCriteria.getRemittanceDetailInErrorState() != null){
            ArrayList errStatus = new ArrayList();
            errStatus.add(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.PROCESS_ERP_ERROR);
            errStatus.add(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.RECIEVED_ERROR);
            errStatus.add(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.INFORMATION_ONLY);
            if(pRemittanceCriteria.getRemittanceDetailInErrorState().equals(Boolean.TRUE)){
                setDetailCriteria = true;
                detCrit.addOneOf(RemittanceDetailDataAccess.REMITTANCE_DETAIL_STATUS_CD,errStatus);
            }else{
                
                detCrit.addNotOneOf(RemittanceDetailDataAccess.REMITTANCE_DETAIL_STATUS_CD,errStatus);
            }
        }
        if (!setDetailCriteria){
            return null;
        }
        return detCrit;
    }
        
    private DBCriteria constructRemittancePropertyCrit(RemittanceCriteriaView pRemittanceCriteria){
        boolean setCriteria = false;
        DBCriteria remPropCrit = new DBCriteria();
        if (Utility.isSet(pRemittanceCriteria.getRemittancePropertyAddBy())){
            remPropCrit.addEqualToIgnoreCase(RemittancePropertyDataAccess.ADD_BY,pRemittanceCriteria.getRemittancePropertyAddBy());
            setCriteria = true;
        }
        if (pRemittanceCriteria.getRemittancePropertyAddDate() != null){
            remPropCrit.addEqualTo(RemittancePropertyDataAccess.ADD_DATE,pRemittanceCriteria.getRemittancePropertyAddDate());
            setCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittancePropertyModBy())){
            remPropCrit.addEqualToIgnoreCase(RemittancePropertyDataAccess.MOD_BY,pRemittanceCriteria.getRemittancePropertyModBy());
            setCriteria = true;
        }
        if (pRemittanceCriteria.getRemittancePropertyModDate() != null){
            remPropCrit.addEqualTo(RemittancePropertyDataAccess.MOD_DATE,pRemittanceCriteria.getRemittancePropertyModDate());
            setCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittancePropertyRemittancePropertyTypeCd())){
            remPropCrit.addEqualToIgnoreCase(RemittancePropertyDataAccess.REMITTANCE_PROPERTY_TYPE_CD,pRemittanceCriteria.getRemittancePropertyRemittancePropertyTypeCd());
            setCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittancePropertyRemittancePropertyStatusCd())){
            remPropCrit.addEqualToIgnoreCase(RemittancePropertyDataAccess.REMITTANCE_PROPERTY_STATUS_CD,pRemittanceCriteria.getRemittancePropertyRemittancePropertyStatusCd());
            setCriteria = true;
        }
        if (Utility.isSet(pRemittanceCriteria.getRemittancePropertyClwValue())){
            remPropCrit.addEqualToIgnoreCase(RemittancePropertyDataAccess.CLW_VALUE,pRemittanceCriteria.getRemittancePropertyClwValue());
            setCriteria = true;
        }
        if (pRemittanceCriteria.getRemittancePropertyId()>0){
            remPropCrit.addEqualTo(RemittancePropertyDataAccess.REMITTANCE_PROPERTY_ID,pRemittanceCriteria.getRemittancePropertyId());
            setCriteria = true;
        }
        if (!setCriteria){
            return null;
        }
        return remPropCrit;
    }
    
    private DBCriteria constructMasterRemittanceCrit(RemittanceCriteriaView pRemittanceCriteria){
        boolean critSet = false;
        DBCriteria rCrit = this.constructRemittanceCrit(pRemittanceCriteria);
        //RemittanceCriteriaView detErrCritV = RemittanceCriteriaView.createValue();
        //detErrCritV.setRemittanceDetailInErrorState(pRemittanceCriteria.getRemittanceDetailInErrorState());
        //DBCriteria detErrCrit = this.constructRemittanceDetailCrit(detErrCritV);
        
        DBCriteria dCrit = this.constructRemittanceDetailCrit(pRemittanceCriteria);
        DBCriteria pCrit = this.constructRemittancePropertyCrit(pRemittanceCriteria);
        
        logDebug("det err state::"+pRemittanceCriteria.getRemittanceDetailInErrorState());
        
        if (rCrit == null && dCrit == null && pCrit == null){
            throw new IllegalArgumentException("Empty Search criteria is not allowed");
        }
        //we will use this as the main criteria
        if (rCrit == null){
            rCrit = new DBCriteria();
        }else{
            critSet = true;
        }
        
        //if we want everything that is not in an error state we must filter it out
        //here specifically, this has the effect of moving the parens around
        /*if (pRemittanceCriteria.getRemittanceInErrorState() != null && pRemittanceCriteria.getRemittanceInErrorState().equals(Boolean.FALSE)){
            DBCriteria errCrit = new DBCriteria();
            errCrit.addCondition(" AND ("+rCrit.getSqlClause()+")");
            ArrayList errStatus = new ArrayList();
            errStatus.add(RefCodeNames.REMITTANCE_STATUS_CD.RECIEVED_ERROR);
            errStatus.add(RefCodeNames.REMITTANCE_STATUS_CD.PROCESS_ERP_ERROR);
            errStatus.add(RefCodeNames.REMITTANCE_STATUS_CD.INFORMATION_ONLY);
            errStatus.add(RefCodeNames.REMITTANCE_STATUS_CD.PROCESSING);
            errCrit.addNotOneOf(RemittanceDataAccess.REMITTANCE_STATUS_CD,errStatus);
        }*/
        
        if (dCrit != null){
            critSet = true;
            String detSelect = "SELECT " + RemittanceDetailDataAccess.REMITTANCE_ID +
                " FROM " + RemittanceDetailDataAccess.CLW_REMITTANCE_DETAIL + 
                " WHERE " + dCrit.getSqlClause();
            rCrit.addOneOf(RemittanceDataAccess.REMITTANCE_ID,detSelect);
        }
        
        /*if(detErrCrit != null){
            DBCriteria tmpCrit = new DBCriteria();
            String detSelect = "SELECT " + RemittanceDetailDataAccess.REMITTANCE_ID +
                " FROM " + RemittanceDetailDataAccess.CLW_REMITTANCE_DETAIL + 
                " WHERE " + detErrCrit.getSqlClause();
            tmpCrit.addOneOf(RemittanceDataAccess.REMITTANCE_ID,detSelect);
            if(critSet){
                rCrit.addOrCriteria(tmpCrit);
            }else{
                critSet = true;
                rCrit.addCondition(tmpCrit.getSqlClause());
            }
        }*/
        
        if (pCrit != null){
            DBCriteria tmpCrit = new DBCriteria();
            String propSelect = "SELECT " + RemittancePropertyDataAccess.REMITTANCE_ID +
                " FROM " + RemittancePropertyDataAccess.CLW_REMITTANCE_PROPERTY + 
                " WHERE " + pCrit.getSqlClause();
            tmpCrit.addOneOf(RemittanceDataAccess.REMITTANCE_ID,propSelect);
            if(critSet){
                rCrit.addOrCriteria(tmpCrit);
            }else{
                critSet = true;
                rCrit.addCondition(tmpCrit.getSqlClause());
            }
        }
        
        logInfo("Remit where clause"+rCrit.getWhereClause());
        return (rCrit);
    }
    
    private DBCriteria constructMasterRemittanceDetailCrit(RemittanceCriteriaView pRemittanceCriteria){
        DBCriteria rCrit = this.constructRemittanceCrit(pRemittanceCriteria);
        DBCriteria dCrit = this.constructRemittanceDetailCrit(pRemittanceCriteria);
        DBCriteria pCrit = this.constructRemittancePropertyCrit(pRemittanceCriteria);
        
        if (rCrit == null && dCrit == null && pCrit == null){
            throw new IllegalArgumentException("Empty Search criteria is not allowed");
        }
        //we will use this as the main criteria
        if (dCrit == null){
            dCrit = new DBCriteria();
        }
        
        if (rCrit != null){
            DBCriteria tmpCrit = new DBCriteria();
            tmpCrit.addOneOf(
                RemittanceDetailDataAccess.REMITTANCE_ID,
                RemittanceDataAccess.getSqlSelectIdOnly(RemittanceDataAccess.REMITTANCE_ID,rCrit));
            dCrit.addOrCriteria(tmpCrit);
        }
        
        //if we want everything that is not in an error state we must filter it out
        //here specifically, this has the effect of moving the parens around
        if (pRemittanceCriteria.getRemittanceInErrorState() != null && pRemittanceCriteria.getRemittanceInErrorState().equals(Boolean.FALSE)){
            DBCriteria errCrit = new DBCriteria();
            ArrayList errStatus = new ArrayList();
            errStatus.add(RefCodeNames.REMITTANCE_STATUS_CD.RECIEVED_ERROR);
            errStatus.add(RefCodeNames.REMITTANCE_STATUS_CD.PROCESS_ERP_ERROR);
            errStatus.add(RefCodeNames.REMITTANCE_STATUS_CD.INFORMATION_ONLY);
            errStatus.add(RefCodeNames.REMITTANCE_STATUS_CD.PROCESSING);
            errCrit.addNotOneOf(RemittanceDataAccess.REMITTANCE_STATUS_CD,errStatus);
            String sql = RemittanceDataAccess.getSqlSelectIdOnly(RemittanceDataAccess.REMITTANCE_ID,errCrit);
            dCrit.addNotOneOf(RemittanceDetailDataAccess.REMITTANCE_ID,sql);
        }
        
        if (pCrit != null){
            DBCriteria tmpCrit = new DBCriteria();
            tmpCrit.addOneOf(
                RemittanceDetailDataAccess.REMITTANCE_DETAIL_ID,
                RemittancePropertyDataAccess.getSqlSelectIdOnly(RemittancePropertyDataAccess.REMITTANCE_DETAIL_ID,pCrit));
            dCrit.addOrCriteria(tmpCrit);
        }
        return (dCrit);
    }
    
    /**
     *Returns populated RemittanceData objects based on some supplied criteria
     *@param pRemittanceCriteria
     *@return a <code>RemittanceDataVector</code> value
     *@throws            RemoteException Required by EJB 1.0
     */
    public RemittanceDataVector getRemittanceData(RemittanceCriteriaView pRemittanceCriteria)
    throws RemoteException{
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = constructMasterRemittanceCrit(pRemittanceCriteria);
            RemittanceDataVector rdv = RemittanceDataAccess.select(conn,crit);
            return rdv;
        } catch (Exception e) {
            throw new RemoteException("getRemitanceData: "+e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    
    /**
     *Returns populated RemittanceData object.
     *@param pRemittanceId 
     *@return a <code>RemittanceData</code> value
     *@throws            RemoteException Required by EJB 1.0
     */
    public RemittanceData getRemittanceDataById(int pRemittanceId)
    throws RemoteException,DataNotFoundException
    {
        Connection conn=null;
        try{
            conn=getConnection();
            return RemittanceDataAccess.select(conn,pRemittanceId);
        }catch (DataNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }
    
    /**
     *Returns a populated IdVector based on some supplied criteria
     *@param pRemittanceCriteria
     *@return a <code>IdVector</code>
     *@throws            RemoteException Required by EJB 1.0
     */
    public IdVector getRemittanceIds(RemittanceCriteriaView pRemittanceCriteria)
    throws RemoteException{
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = constructMasterRemittanceCrit(pRemittanceCriteria);
            IdVector idv = RemittanceDataAccess.selectIdOnly(conn,crit);
            return idv;
        } catch (Exception e) {
            throw new RemoteException("getRemittanceIds: "+e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    
    /**
     * Adds a Remittance Detail to the database
     *
     * @param pRemittanceDetailData a <code>RemittanceDetailData</code> value
     * @param pUser username
     * @return a <code>RemittanceDetailData</code> value
     * @exception RemoteException if an error occurs
     */
    public RemittanceDetailData addRemittanceDetail(RemittanceDetailData pRemittanceDetailData, String pUser)
    throws RemoteException {
        logDebug("in addRemittanceDetail method");
        Connection conn = null;
        try {
            conn = getConnection();
            if(pRemittanceDetailData.isDirty()){
                pRemittanceDetailData.setAddBy(pUser);
                pRemittanceDetailData.setAddDate(new Date());
                //look to see if there is a short pay in the db referencing this already,
                //or if this is a short pay find what it references and deduct the difference
                //based off the state.
                //first handle case where short pay comes in after invoice
                if(pRemittanceDetailData.getInvoiceType().equals(RefCodeNames.REMITTANCE_INVOICE_TYPE_CD.SHORT_PAY)
                    && Utility.isSet(pRemittanceDetailData.getReferenceInvoiceNumber()) && pRemittanceDetailData.getNetAmount() != null){
                    DBCriteria crit = new DBCriteria();
                    crit.addEqualTo(RemittanceDetailDataAccess.INVOICE_NUMBER,pRemittanceDetailData.getReferenceInvoiceNumber());
                    crit.addEqualTo(RemittanceDetailDataAccess.INVOICE_TYPE,RefCodeNames.REMITTANCE_INVOICE_TYPE_CD.INVOICE);
                    crit.addEqualTo(RemittanceDetailDataAccess.REMITTANCE_DETAIL_STATUS_CD,RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.RECIEVED);
                    RemittanceDetailDataVector rddv = RemittanceDetailDataAccess.select(conn,crit);
                    if (rddv.size()==1){
                        //apply the short paid amount and change the satus
                        RemittanceDetailData lrdd = (RemittanceDetailData) rddv.get(0);
                        lrdd.setNetAmount(lrdd.getNetAmount().subtract(pRemittanceDetailData.getNetAmount()));
                        pRemittanceDetailData.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.APPLIED_ADJUSTMENT);
                        RemittanceDetailDataAccess.update(conn, lrdd);
                        pRemittanceDetailData = RemittanceDetailDataAccess.insert(conn,pRemittanceDetailData);
                        
                        RemittancePropertyData note = RemittancePropertyData.createValue();
                        note.setAddBy(pRemittanceDetailData.getAddBy());
                        note.setAddDate(pRemittanceDetailData.getAddDate());
                        note.setModBy(pRemittanceDetailData.getModBy());
                        note.setModDate(pRemittanceDetailData.getModDate());
                        note.setRemittanceDetailId(lrdd.getRemittanceDetailId());
                        note.setRemittanceId(lrdd.getRemittanceId());
                        note.setRemittancePropertyStatusCd(RefCodeNames.REMITTANCE_PROPERTY_STATUS_CD.ACTIVE);
                        note.setRemittancePropertyTypeCd(RefCodeNames.REMITTANCE_PROPERTY_TYPE_CD.NOTE);
                        note.setValue("Applied Adjustment from short pay: " + pRemittanceDetailData.getReferenceInvoiceNumber() + " id::"+pRemittanceDetailData.getRemittanceDetailId());
                    }else{
                        //just insert the record
                        pRemittanceDetailData = RemittanceDetailDataAccess.insert(conn,pRemittanceDetailData);
                    }
                }else if(pRemittanceDetailData.getInvoiceType().equals(RefCodeNames.REMITTANCE_INVOICE_TYPE_CD.INVOICE)
                    && Utility.isSet(pRemittanceDetailData.getInvoiceNumber()) && pRemittanceDetailData.getNetAmount() != null){
                    DBCriteria crit = new DBCriteria();
                    crit.addEqualTo(RemittanceDetailDataAccess.REFERENCE_INVOICE_NUMBER,pRemittanceDetailData.getInvoiceNumber());
                    crit.addEqualTo(RemittanceDetailDataAccess.INVOICE_TYPE,RefCodeNames.REMITTANCE_INVOICE_TYPE_CD.SHORT_PAY);
                    crit.addEqualTo(RemittanceDetailDataAccess.REMITTANCE_DETAIL_STATUS_CD,RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.INFORMATION_ONLY);
                    RemittanceDetailDataVector rddv = RemittanceDetailDataAccess.select(conn,crit);
                    if (rddv.size()==1){
                        //apply the short paid amount and change the satus
                        RemittanceDetailData lrdd = (RemittanceDetailData) rddv.get(0);
                        pRemittanceDetailData.setNetAmount(pRemittanceDetailData.getNetAmount().subtract(lrdd.getNetAmount()));
                        lrdd.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.APPLIED_ADJUSTMENT);
                        RemittanceDetailDataAccess.update(conn, lrdd);
                        pRemittanceDetailData = RemittanceDetailDataAccess.insert(conn,pRemittanceDetailData);
                        
                        RemittancePropertyData note = RemittancePropertyData.createValue();
                        note.setAddBy(pRemittanceDetailData.getAddBy());
                        note.setAddDate(pRemittanceDetailData.getAddDate());
                        note.setModBy(pRemittanceDetailData.getModBy());
                        note.setModDate(pRemittanceDetailData.getModDate());
                        note.setRemittanceDetailId(pRemittanceDetailData.getRemittanceDetailId());
                        note.setRemittanceId(pRemittanceDetailData.getRemittanceId());
                        note.setRemittancePropertyStatusCd(RefCodeNames.REMITTANCE_PROPERTY_STATUS_CD.ACTIVE);
                        note.setRemittancePropertyTypeCd(RefCodeNames.REMITTANCE_PROPERTY_TYPE_CD.NOTE);
                        note.setValue("Applied Adjustment from short pay: " + lrdd.getReferenceInvoiceNumber() + " id::"+lrdd.getRemittanceDetailId());
                    }else{
                        //just insert the record
                        pRemittanceDetailData = RemittanceDetailDataAccess.insert(conn,pRemittanceDetailData);
                    }
                }else{
                    //just insert the record
                    pRemittanceDetailData = RemittanceDetailDataAccess.insert(conn,pRemittanceDetailData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("addRemittanceDetail: "+e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return pRemittanceDetailData;
    }
    
    
    /**
     *Updates the RemittanceDetail information values to be used by the request.
     *@param RemittanceDetailData to update or insert
     *@param pUser
     *@return a <code>RemittanceDetailData</code> value
     *@throws            RemoteException Required by EJB 1.0
     */
    public RemittanceDetailData updateRemittanceDetail(RemittanceDetailData pRemittanceDetailData,String pUser)
    throws RemoteException{
        logDebug("in updatePurchaseOrder method");
        Connection conn = null;
        try {
            
            logDebug("dirty flag: " + pRemittanceDetailData.isDirty());
            if (pRemittanceDetailData.isDirty()) {
                if (pRemittanceDetailData.getRemittanceDetailId() == 0) {
                    pRemittanceDetailData = addRemittanceDetail(pRemittanceDetailData, pUser);
                } else {
                    conn = getConnection();
                    pRemittanceDetailData.setModBy(pUser);
                    pRemittanceDetailData.setModDate(new Date());
                    RemittanceDetailDataAccess.update(conn, pRemittanceDetailData);
                }
            }
            
        } catch (Exception e) {
            throw new RemoteException("updateRemittanceDetail: "+e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return pRemittanceDetailData;
    }
    
    /**
     *Returns populated RemittanceDetailData objects based on the supplied criteria
     *@param pRemittanceCriteria search criteria
     *@return a <code>RemittanceDetailDataVector</code> list
     *@throws            RemoteException Required by EJB 1.0
     */
    public RemittanceDetailDataVector getRemittanceDetailData(RemittanceCriteriaView pRemittanceCriteria)
    throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = this.constructRemittanceDetailCrit(pRemittanceCriteria);
            if(crit == null){
                throw new RemoteException("empty criteria not allowed");
            }
            RemittanceDetailDataVector rddv = RemittanceDetailDataAccess.select(conn,crit);
            return rddv;
        }catch (Exception e) {
            throw new RemoteException("getRemitanceDetailData: "+e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    
    /**
     *Returns populated RemittanceDetailDescView objects based on the supplied criteria
     *@param pRemittanceCriteria search criteria
     *@return a <code>RemittanceDetailDescViewVector</code> list
     *@throws RemoteException Required by EJB 1.0
     */
    public RemittanceDetailDescViewVector getRemittanceDetailDescList(RemittanceCriteriaView pRemittanceCriteria)
    throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = this.constructMasterRemittanceDetailCrit(pRemittanceCriteria);

            RemittanceDetailDataVector rddv = RemittanceDetailDataAccess.select(conn,crit);
            RemittanceDetailDescViewVector rddViewV = new RemittanceDetailDescViewVector();
            for(int i=0,len=rddv.size();i<len;i++){
                RemittanceDetailDescView rddView = RemittanceDetailDescView.createValue();
                RemittanceDetailData rdd = (RemittanceDetailData) rddv.get(i);
                rddView.setRemittanceDetailData(rdd);
                crit = new DBCriteria();
                crit.addEqualTo(RemittancePropertyDataAccess.REMITTANCE_DETAIL_ID,rdd.getRemittanceDetailId());
                rddView.setRemittancePropertyDataVector(RemittancePropertyDataAccess.select(conn,crit));
                rddViewV.add(rddView);
            }
            return rddViewV;
        }catch (Exception e) {
            throw new RemoteException("getRemitanceDetailData: "+e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    
    /**
     *Adds or updates a RemittanceProperty object
     *@returns the updated object
     *@param pProperty a <code>RemittancePropertyData</code> object.  If the 
     *  remittancePropertyId property is 0 it will be inserted, otherwise it 
     *  will be updated.
     *@param pUser username
     *@throws RemoteException on any error
     */
    public RemittancePropertyData addUpdateRemittanceProperty(RemittancePropertyData pProperty, String pUser)
    throws RemoteException{
        Connection conn = null;
        try{
            conn = getConnection();
            if (pProperty.getRemittancePropertyId() == 0){
                pProperty.setAddBy(pUser);
                RemittancePropertyDataAccess.insert(conn,pProperty);
            } else {
                pProperty.setModBy(pUser);
                pProperty.setModDate(new Date());
                RemittancePropertyDataAccess.update(conn,pProperty);
            }
            return pProperty;
        } catch (Exception e){
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    
    /**
     *Returns a list of all of the RemittancePropertyData where the 
     *remittanceDetailId = 0, remittanceId = 0 and the type is an error.
     *This indicates a line that was not able to be processed by the loader.
     *@throws RemoteException on any error
     */
    public RemittancePropertyDataVector getUnparsableRemittanceData()
    throws RemoteException{
        Connection conn = null;
        try{
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(RemittancePropertyDataAccess.REMITTANCE_DETAIL_ID,0);
            crit.addEqualTo(RemittancePropertyDataAccess.REMITTANCE_ID,0);
            crit.addEqualToIgnoreCase(RemittancePropertyDataAccess.REMITTANCE_PROPERTY_STATUS_CD,
                RefCodeNames.REMITTANCE_PROPERTY_STATUS_CD.ACTIVE);
            crit.addEqualToIgnoreCase(RemittancePropertyDataAccess.REMITTANCE_PROPERTY_TYPE_CD,
                RefCodeNames.REMITTANCE_PROPERTY_TYPE_CD.ERROR);
            return RemittancePropertyDataAccess.select(conn,crit);
        } catch (Exception e){
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    
    
    
    /**
     *Reprocess a given remittance detail record.  Does any validation it
     *can, resets all of the type codes in the remittance properties that
     *were in an error state, and resets the state of the remittance detail
     *to be ready to send to lawson on the next load (either through the 
     *erp integration or the system batch processing)
     *@param pRemittanceDetailId the remittance detail id to reprocess
     *@throws RemoteException on any error
     *@throws DataNotFoundException if the pRemittanceDetailId supplied was invalid
     */
    public void reprocessRemittanceDetail(int pRemittanceDetailId,String pUserName)
    throws RemoteException,DataNotFoundException{
        Connection conn = null;
        try{
            conn = getConnection();
            RemittanceDetailData rdd = RemittanceDetailDataAccess.select(conn,pRemittanceDetailId);
            rdd.setModBy(pUserName);
            rdd.setModDate(new Date());
            if (rdd.getRemittanceDetailStatusCd().equalsIgnoreCase(
                RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.PROCESS_ERP_ERROR)){
                rdd.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.RECIEVED);
            }else if (rdd.getRemittanceDetailStatusCd().equalsIgnoreCase(
                RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.RECIEVED_ERROR)){
                rdd.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.RECIEVED);
            }else{
                throw new RemoteException("Cannot reprocess remittances in a(n) "+
                    rdd.getRemittanceDetailStatusCd() + " state.");
            }
            RemittanceDetailDataAccess.update(conn,rdd);
            
            //now fetch and update the properties data
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(RemittancePropertyDataAccess.REMITTANCE_DETAIL_ID,pRemittanceDetailId);
            crit.addEqualTo(RemittancePropertyDataAccess.REMITTANCE_PROPERTY_TYPE_CD, 
                RefCodeNames.REMITTANCE_PROPERTY_TYPE_CD.ERROR);
            crit.addEqualTo(RemittancePropertyDataAccess.REMITTANCE_PROPERTY_STATUS_CD, 
                RefCodeNames.REMITTANCE_PROPERTY_STATUS_CD.ACTIVE);
            RemittancePropertyDataVector rpdv = RemittancePropertyDataAccess.select(conn,crit);
            for (int i=0,len=rpdv.size();i<len;i++){
                RemittancePropertyData rpd = (RemittancePropertyData) rpdv.get(i);
                rpd.setRemittancePropertyStatusCd(RefCodeNames.REMITTANCE_PROPERTY_TYPE_CD.ERROR_RESOLVED);
                rpd.setModBy(pUserName);
                rpd.setModDate(new Date());
                RemittancePropertyDataAccess.update(conn,rpd);
            }
        } catch (DataNotFoundException e){
            throw e;
        } catch (Exception e){
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    
    /**
     *Utility method to return all of the remittance properties associated with a
     *given remittance id.
     */
    private RemittancePropertyDataVector getRemittancePropertyDataByRemitId(Connection pConn, int pRemittanceId)
    throws java.sql.SQLException
    {
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(RemittancePropertyDataAccess.REMITTANCE_ID,pRemittanceId);
        crit.addEqualTo(RemittancePropertyDataAccess.REMITTANCE_PROPERTY_STATUS_CD, 
            RefCodeNames.REMITTANCE_PROPERTY_STATUS_CD.ACTIVE);
        crit.addEqualTo(RemittancePropertyDataAccess.REMITTANCE_PROPERTY_TYPE_CD, 
            RefCodeNames.REMITTANCE_PROPERTY_TYPE_CD.ERROR);
        return RemittancePropertyDataAccess.select(pConn,crit);
    }
    
    /**
     *clears all errors for a given remittance record.  Does any validation it
     *can, resets all of the type codes in the remittance properties that
     *were in an error state, and resets the state of the remittance detail
     *objects to be sent to lawson 
     *@param pRemittanceId the remittance id to reprocess
     *@throws RemoteException on any error
     *@throws DataNotFoundException if the pRemittanceId supplied was invalid
     */
    public void clearRemittanceErrors(int pRemittanceId,String pUserName)
    throws RemoteException,DataNotFoundException{
        remittanceStatusUpdate(pRemittanceId, pUserName, false);
    }
    
    /**
     *Updates the status of a remittance and it's detail to be processed through lawson
     *the next time the remittance loader is run.
     *@param pRemittanceId the remittance id to reprocess
     *@throws RemoteException on any error
     *@throws DataNotFoundException if the pRemittanceId supplied was invalid
     */
    public void reprocessRemittance(int pRemittanceId,String pUserName)
    throws RemoteException,DataNotFoundException{
        remittanceStatusUpdate(pRemittanceId, pUserName, true);
    }
        
    private void remittanceStatusUpdate(int pRemittanceId,String pUserName, boolean reprocessing)
    throws RemoteException,DataNotFoundException{
        Connection conn = null;
        try{
            conn = getConnection();
            RemittanceData rd = RemittanceDataAccess.select(conn,pRemittanceId);
            //update the detail data
            RemittanceDetailDataVector rddv = getRemittanceDetailDataByRemitId(pRemittanceId,conn);
            for(int i=0,len=rddv.size();i<len;i++){
                RemittanceDetailData rdd = (RemittanceDetailData) rddv.get(i);
                if (rdd.getRemittanceDetailStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.INFORMATION_ONLY)){
                    if(!reprocessing){
                        rdd.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.PROCESS_INFORMATION_ONLY);
                    }
                }else if (rdd.getRemittanceDetailStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.PROCESS_ERP_ERROR)){
                    if(reprocessing){
                        rdd.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.RECIEVED);
                    }else{
                        rdd.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.PROCESS_ERP);
                    }
                }else if (rdd.getRemittanceDetailStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.RECIEVED_ERROR)){
                    if(reprocessing){
                        rdd.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.RECIEVED);
                    }else{
                        rdd.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.PROCESS_ERP);
                    }
                }else if (rdd.getRemittanceDetailStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.PROCESS_ERP)){
                    if(reprocessing){
                        rdd.setRemittanceDetailStatusCd(RefCodeNames.REMITTANCE_DETAIL_STATUS_CD.RECIEVED);
                    }
                }
                rdd.setModBy(pUserName);
                rdd.setModDate(new Date());
                RemittanceDetailDataAccess.update(conn,rdd);
            }
            //update the remittance
            if(rd.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.INFORMATION_ONLY)){
                if(!reprocessing){
                    rd.setRemittanceStatusCd(RefCodeNames.REMITTANCE_STATUS_CD.PROCESS_INFORMATION_ONLY);
                }
            }else if(rd.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.PROCESS_ERP_ERROR)){
                if(reprocessing){
                    rd.setRemittanceStatusCd(RefCodeNames.REMITTANCE_STATUS_CD.RECIEVED);
                }else{
                    rd.setRemittanceStatusCd(RefCodeNames.REMITTANCE_STATUS_CD.PROCESS_ERP);
                }
            }else if(rd.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.RECIEVED_ERROR)){
                if(reprocessing){
                    rd.setRemittanceStatusCd(RefCodeNames.REMITTANCE_STATUS_CD.RECIEVED);
                }else{
                    rd.setRemittanceStatusCd(RefCodeNames.REMITTANCE_STATUS_CD.PROCESS_ERP);
                }
            }else if(rd.getRemittanceStatusCd().equalsIgnoreCase(RefCodeNames.REMITTANCE_STATUS_CD.PROCESS_ERP)){
                if(reprocessing){
                    rd.setRemittanceStatusCd(RefCodeNames.REMITTANCE_STATUS_CD.RECIEVED);
                }
            }
            rd.setModBy(pUserName);
            rd.setModDate(new Date());
            RemittanceDataAccess.update(conn,rd);
            
            //update the properties
            if(!reprocessing){
                RemittancePropertyDataVector rpdv = getRemittancePropertyDataByRemitId(conn,pRemittanceId);
                for (int i=0,len=rpdv.size();i<len;i++){
                    RemittancePropertyData rpd = (RemittancePropertyData) rpdv.get(i);
                    rpd.setModBy(pUserName);
                    rpd.setModDate(new Date());
                    rpd.setRemittancePropertyTypeCd(RefCodeNames.REMITTANCE_PROPERTY_TYPE_CD.ERROR_RESOLVED);
                    RemittancePropertyDataAccess.update(conn,rpd);
                }
            }
        } catch (DataNotFoundException e){
            throw e;
        } catch (Exception e){
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    
    /**
     *A remittance can only be deleted if it is ina pending state and has no detail 
     *records associated with it.  This type of invoice may be deleted because of the
     *unique way that Factoring works, and CIT in particular.  A payment will come in 
     *and will be applied to an exsisting payment in the system.  This exsisting payment
     *then looses it's significance, as for remittance processing, and accounting processing
     *it has no meaning.
     *@param pRemittanceId the remittance id to reprocess
     *@throws RemoteException on any error
     *@throws DataNotFoundException if the pRemittanceId supplied was invalid
     */
    public void deleteRemittance(int pRemittanceId)
    throws RemoteException,DataNotFoundException{
        Connection conn = null;
        try{
            conn = getConnection();
            RemittanceData rd = RemittanceDataAccess.select(conn,pRemittanceId);
            if (!rd.getRemittanceStatusCd().equals(RefCodeNames.REMITTANCE_STATUS_CD.PENDING)){
                throw new RemoteException("Cannot Delete a remittance that is not in a pending state");
            }
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(RemittanceDetailDataAccess.REMITTANCE_ID,pRemittanceId);
            RemittanceDetailDataVector rddv = RemittanceDetailDataAccess.select(conn,crit);
            if (rddv.size() > 0){
                throw new RemoteException("Cannot Delete a remittance that has detail records");
            }
            //okay to delete
            //first delete properites
            crit = new DBCriteria();
            crit.addEqualTo(RemittancePropertyDataAccess.REMITTANCE_ID,pRemittanceId);
            RemittancePropertyDataAccess.remove(conn,crit);
            //now delete remittance
            RemittanceDataAccess.remove(conn,pRemittanceId);
        } catch (Exception e){
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
}
