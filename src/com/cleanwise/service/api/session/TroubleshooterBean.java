package com.cleanwise.service.api.session;

/**
 * Title:        TroubleshooterBean
 * Description:  Bean implementation for Troubleshooter Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving Troubleshooter information.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.rmi.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Iterator;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import javax.naming.NamingException;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.DBAccess;
import java.sql.Connection;
import java.sql.SQLException;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.dao.*;
import java.sql.*;
import org.apache.log4j.Category;

public class TroubleshooterBean extends ValueAddedServicesAPI
{
  /**
   *
   */
  public TroubleshooterBean() {}

  /**
   *
   */
  public void ejbCreate() throws CreateException, RemoteException {}





    /**
     * Get call data vector according to the callSearchCriteriaData
     * @param pCallSearchCriteriaData the criteria data to search the calls
     * @throws            RemoteException
     */
    public CallDataVector getCallCollection(CallSearchCriteriaData pCallSearchCriteria)
        throws RemoteException  {

	CallDataVector callVec = new CallDataVector();

	Connection conn = null;
	try {
	    conn = getConnection();
	    DBCriteria crit = new DBCriteria();

            if(pCallSearchCriteria.getAccountName().trim().length() > 0 ) {
                DBCriteria subcrit = new DBCriteria();
                subcrit.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC,
					  "%"+pCallSearchCriteria.getAccountName().trim()+"%");
                subcrit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                                            RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
                IdVector accountids = BusEntityDataAccess.selectIdOnly(conn,
					 BusEntityDataAccess.BUS_ENTITY_ID, subcrit);
                crit.addOneOf(CallDataAccess.ACCOUNT_ID, accountids);
            }

            IdVector accountIdVector = pCallSearchCriteria.getAccountIdVector();
            if(accountIdVector!=null && accountIdVector.size()>0) {
              crit.addOneOf(CallDataAccess.ACCOUNT_ID, accountIdVector);
            }

            if(pCallSearchCriteria.getSiteName().trim().length() > 0 ) {
                DBCriteria subcrit = new DBCriteria();
                subcrit.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC,
					  "%"+pCallSearchCriteria.getSiteName().trim()+"%");
                subcrit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                                            RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
                IdVector siteids = BusEntityDataAccess.selectIdOnly(conn,
					 BusEntityDataAccess.BUS_ENTITY_ID, subcrit);

                if(pCallSearchCriteria.getSiteZipCode().trim().length() > 0 ) {
                    subcrit = new DBCriteria();
                    subcrit.addOneOf(AddressDataAccess.BUS_ENTITY_ID, siteids);
                    subcrit.addLikeIgnoreCase(AddressDataAccess.POSTAL_CODE,
					  "%"+pCallSearchCriteria.getSiteZipCode().trim()+"%");
                    siteids = AddressDataAccess.selectIdOnly(conn,
					 AddressDataAccess.BUS_ENTITY_ID, subcrit);
                }

                crit.addOneOf(CallDataAccess.SITE_ID, siteids);
            }
            else {
                if(pCallSearchCriteria.getSiteZipCode().trim().length() > 0 ) {
                    DBCriteria subcrit = new DBCriteria();
                    subcrit.addLikeIgnoreCase(AddressDataAccess.POSTAL_CODE,
					  "%"+pCallSearchCriteria.getSiteZipCode().trim()+"%");
                    IdVector siteids = AddressDataAccess.selectIdOnly(conn,
					 AddressDataAccess.BUS_ENTITY_ID, subcrit);

                    subcrit = new DBCriteria();
                    subcrit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, siteids);
                    subcrit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                                            RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
                    siteids = BusEntityDataAccess.selectIdOnly(conn,
					 BusEntityDataAccess.BUS_ENTITY_ID, subcrit);

                    crit.addOneOf(CallDataAccess.SITE_ID, siteids);
                }
            }
            if(pCallSearchCriteria.getSiteData().trim().length() > 0){
                    DBCriteria subcrit = new DBCriteria();

                    subcrit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,
                                            RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                    subcrit.addLikeIgnoreCase(PropertyDataAccess.CLW_VALUE, "%"+pCallSearchCriteria.getSiteData().trim()+"%");

                    IdVector siteids = PropertyDataAccess.selectIdOnly(conn,
					 PropertyDataAccess.BUS_ENTITY_ID, subcrit);

                    siteids = PropertyDataAccess.selectIdOnly(conn,
					 PropertyDataAccess.BUS_ENTITY_ID, subcrit);

                    crit.addOneOf(CallDataAccess.SITE_ID, siteids);
            }
            if(pCallSearchCriteria.getContactName().trim().length() > 0 ) {
                crit.addLikeIgnoreCase(CallDataAccess.CONTACT_NAME,
					  "%"+pCallSearchCriteria.getContactName().trim()+"%");
            }
            if(pCallSearchCriteria.getContactPhone().trim().length() > 0 ) {
                crit.addLikeIgnoreCase(CallDataAccess.CONTACT_PHONE_NUMBER,
					  "%"+pCallSearchCriteria.getContactPhone().trim()+"%");
            }
            if(pCallSearchCriteria.getContactEmail().trim().length() > 0 ) {
                crit.addLikeIgnoreCase(CallDataAccess.CONTACT_EMAIL_ADDRESS,
					  "%"+pCallSearchCriteria.getContactEmail().trim()+"%");
            }
            if(pCallSearchCriteria.getProductName().trim().length() > 0 ) {
                crit.addLikeIgnoreCase(CallDataAccess.PRODUCT_NAME,
					  "%"+pCallSearchCriteria.getProductName().trim()+"%");
            }
            if(pCallSearchCriteria.getCustomerField1().trim().length() > 0 ) {
                crit.addLikeIgnoreCase(CallDataAccess.CUSTOMER_FIELD_1,
					  "%"+pCallSearchCriteria.getCustomerField1().trim()+"%");
            }

            if(pCallSearchCriteria.getErpOrderNum().trim().length() > 0
                || pCallSearchCriteria.getWebOrderConfirmationNum().trim().length() > 0
                || pCallSearchCriteria.getCustPONum().trim().length() > 0
                || pCallSearchCriteria.getErpPONum().trim().length() > 0
                || pCallSearchCriteria.getOrderDateRangeBegin().trim().length() > 0
                || pCallSearchCriteria.getOrderDateRangeEnd().trim().length() > 0 ) {

                DBCriteria subcrit = new DBCriteria();
                if(pCallSearchCriteria.getErpOrderNum().trim().length() > 0 ) {
                    subcrit.addLikeIgnoreCase(OrderDataAccess.ERP_ORDER_NUM,
					  "%"+pCallSearchCriteria.getErpOrderNum().trim()+"%");
                }
                if(pCallSearchCriteria.getWebOrderConfirmationNum().trim().length() > 0 ) {
                    subcrit.addLikeIgnoreCase(OrderDataAccess.ORDER_NUM,
					  "%"+pCallSearchCriteria.getWebOrderConfirmationNum().trim()+"%");
                }
                if(pCallSearchCriteria.getCustPONum().trim().length() > 0 ) {
                    subcrit.addLikeIgnoreCase(OrderDataAccess.REQUEST_PO_NUM,
					  "%"+pCallSearchCriteria.getCustPONum().trim()+"%");
                }
                if (pCallSearchCriteria.getErpPONum().trim().length() > 0 ) {
                    StringBuffer buf = new StringBuffer();
                    buf.append(OrderDataAccess.ORDER_ID);
                    buf.append(" IN (SELECT ");
                    buf.append(OrderItemDataAccess.ORDER_ID);
                    buf.append(" FROM ");
                    buf.append(OrderItemDataAccess.CLW_ORDER_ITEM);
                    buf.append(" WHERE ");
                    buf.append("UPPER(" + OrderItemDataAccess.ERP_PO_NUM + ")");
                    buf.append(" LIKE ");
                    buf.append("'%" + pCallSearchCriteria.getErpPONum().trim().toUpperCase() + "%'");
                    buf.append(")");
                    subcrit.addCondition(buf.toString());
                }

                if(pCallSearchCriteria.getOrderDateRangeBegin().trim().length() > 0
                    && pCallSearchCriteria.getOrderDateRangeEnd().trim().length() > 0
                    && pCallSearchCriteria.getOrderDateRangeBegin().trim().equals(pCallSearchCriteria.getOrderDateRangeEnd().trim())) {

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    Date orderDate =  new Date();
                    try {
                        orderDate  = simpleDateFormat.parse(pCallSearchCriteria.getOrderDateRangeBegin().trim());
                    }
                    catch (Exception e) {
                        orderDate = null;
                    }

                    if(null != orderDate ) {
                        subcrit.addEqualToIgnoreCase(OrderDataAccess.ORIGINAL_ORDER_DATE,
					  orderDate);
                    }

                }
                else {
                    if(pCallSearchCriteria.getOrderDateRangeBegin().trim().length() > 0 ) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        Date orderDate =  new Date();
                        try {
                            orderDate  = simpleDateFormat.parse(pCallSearchCriteria.getOrderDateRangeBegin().trim());
                        }
                        catch (Exception e) {
                            orderDate = null;
                        }

                        if(null != orderDate ) {
subcrit.addGreaterOrEqual(OrderDataAccess.ORIGINAL_ORDER_DATE,
					  orderDate);
                        }
                    }
                    if(pCallSearchCriteria.getOrderDateRangeEnd().trim().length() > 0 ) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        Date orderDate =  new Date();
                        try {
                            orderDate  = simpleDateFormat.parse(pCallSearchCriteria.getOrderDateRangeEnd().trim());
                        }
                        catch (Exception e) {
                            orderDate = null;
                        }

                        if(null != orderDate ) {
                            subcrit.addLessOrEqual(OrderDataAccess.ORIGINAL_ORDER_DATE,
					  orderDate);
                        }
                    }
                }

                IdVector orderstatusids = OrderDataAccess.selectIdOnly(conn,
					 OrderDataAccess.ORDER_ID, subcrit);

                crit.addOneOf(CallDataAccess.ORDER_ID, orderstatusids);
            }


            if(pCallSearchCriteria.getCallTypeCd().trim().length() > 0 ) {
                crit.addLikeIgnoreCase(CallDataAccess.CALL_TYPE_CD,
					  "%"+pCallSearchCriteria.getCallTypeCd().trim()+"%");
            }
            if(pCallSearchCriteria.getCallSeverityCd().trim().length() > 0 ) {
                crit.addLikeIgnoreCase(CallDataAccess.CALL_SEVERITY_CD,
					  "%"+pCallSearchCriteria.getCallSeverityCd().trim()+"%");
            }
            if(pCallSearchCriteria.getCallStatusCd().trim().length() > 0 ) {
                crit.addLikeIgnoreCase(CallDataAccess.CALL_STATUS_CD,
					  "%"+pCallSearchCriteria.getCallStatusCd().trim()+"%");
            }
            if(pCallSearchCriteria.getOpenedById().trim().length() > 0 ) {
                crit.addLikeIgnoreCase(CallDataAccess.OPENED_BY_ID,
					  "%"+pCallSearchCriteria.getOpenedById().trim()+"%");
            }
            if(pCallSearchCriteria.getAssignedToId().trim().length() > 0 ) {
                crit.addLikeIgnoreCase(CallDataAccess.ASSIGNED_TO_ID,
					  pCallSearchCriteria.getAssignedToId().trim());
            }


            callVec =
		CallDataAccess.select(conn, crit);

	} catch (Exception e) {
	    throw new RemoteException("getCallCollection: " + e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return callVec;
    }


    /**
     * Get call desc data vector according to the callSearchCriteriaData
     * @param pCallSearchCriteriaData the criteria data to search the calls
     * @throws            RemoteException
     */
    public CallDescDataVector getCallDescCollection(CallSearchCriteriaData pCallSearchCriteria)
        throws RemoteException  {

	CallDescDataVector callDescV = null;

	Connection conn = null;
	try {

            CallDataVector callV = getCallCollection(pCallSearchCriteria);

            if ( null != callV && callV.size() > 0 ) {
                callDescV = new CallDescDataVector();
                conn = getConnection();
                for(int i = 0; i < callV.size(); i++) {
                    CallData callD = (CallData)callV.get(i);

                    CallDescData callDescD = CallDescData.createValue();
                    callDescD.setCallDetail(callD);
                    callDescD.setAccountName(new String(""));
                    callDescD.setSiteName(new String(""));
                    callDescD.setSiteCity(new String(""));
                    callDescD.setSiteState(new String(""));
                    callDescD.setSiteZip(new String(""));
                    callDescD.setAssignedTo(new String(""));

                    DBCriteria crit = new DBCriteria();
                    if( 0 != callD.getAccountId()) {
                        BusEntityData accountD =
                                    BusEntityDataAccess.select(conn, callD.getAccountId());
                        if ( null != accountD ) {
                            callDescD.setAccountName(accountD.getShortDesc());
                        }
                    }


                    if( 0 != callD.getSiteId()) {
                        BusEntityData siteD =
                                    BusEntityDataAccess.select(conn, callD.getSiteId());
                        if ( null != siteD ) {
                            callDescD.setSiteName(siteD.getShortDesc());

                            crit = new DBCriteria();
                            crit.addEqualTo(AddressDataAccess.BUS_ENTITY_ID,
                                            siteD.getBusEntityId());
                            crit.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD,
                                            RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);

                            AddressDataVector addresses =
                                            AddressDataAccess.select(conn, crit);
                            if (addresses.size() > 0) {
                                AddressData siteShippingAddress = (AddressData) addresses.get(0);
                                callDescD.setSiteCity(siteShippingAddress.getCity());
                                callDescD.setSiteState(siteShippingAddress.getStateProvinceCd());
                                callDescD.setSiteZip(siteShippingAddress.getPostalCode());
                            }
                        }
                    }

                    if( 0 != callD.getAssignedToId()){
                        UserData userD = UserDataAccess.select(conn, callD.getAssignedToId());
                        if(null != userD){
                            callDescD.setAssignedTo(userD.getUserName());
                        }
                    }
                    callDescV.add(callDescD);
                }
            }

	} catch (Exception e) {
	    throw new RemoteException("getCallDescCollection: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return callDescV;

    }


    /**
     * Get call desc data according to the pCallId
     * @param pCallId the call Id to search the call
     * @throws            RemoteException
     */
    public CallDescData getCallDesc(int pCallId)
        throws RemoteException, DataNotFoundException  {

	CallDescData callDescD = null;

	Connection conn = null;
	try {
	    conn = getConnection();
	    CallData callD =
		CallDataAccess.select(conn, pCallId);

            callDescD = CallDescData.createValue();
            callDescD.setCallDetail(callD);

            DBCriteria crit = new DBCriteria();
            if( 0 != callD.getAccountId()) {
                BusEntityData accountD =
                                    BusEntityDataAccess.select(conn, callD.getAccountId());
                if ( null != accountD ) {
                    callDescD.setAccountName(accountD.getShortDesc());
                }
            }

            if( 0 != callD.getSiteId()) {
                BusEntityData siteD =
                                    BusEntityDataAccess.select(conn, callD.getSiteId());
                if ( null != siteD ) {
                    callDescD.setSiteName(siteD.getShortDesc());

                    crit = new DBCriteria();
                    crit.addEqualTo(AddressDataAccess.BUS_ENTITY_ID,
                                            siteD.getBusEntityId());
                    crit.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD,
                                            RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);

                    AddressDataVector addresses =
                                            AddressDataAccess.select(conn, crit);
                    if (addresses.size() > 0) {
                        AddressData siteShippingAddress = (AddressData) addresses.get(0);
                        callDescD.setSiteCity(siteShippingAddress.getCity());
                        callDescD.setSiteState(siteShippingAddress.getStateProvinceCd());
                        callDescD.setSiteZip(siteShippingAddress.getPostalCode());
                    }
                }
            }

	} catch (DataNotFoundException e) {
	    //throw e;
	} catch (Exception e) {
	    throw new RemoteException("getCallDesc: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return callDescD;
    }


    /**
     * Describe <code>addCall</code> method here.
     *
     * @param pCallData a <code>CallData</code> value
     * @return a <code>CallData</code> value
     * @exception RemoteException if an error occurs
     */
    public CallData addCall(CallData pCallData)
	throws RemoteException
    {
	return updateCall(pCallData);
    }

    /**
     * Updates the Call information values to be used by the request.
     * @param pCallData  the OrderItemDetailData .
     * @return a <code>CallData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public CallData updateCall(CallData pCallData)
	throws RemoteException
    {
	Connection conn = null;

	try {
	    conn = getConnection();

	    CallData calldetail = pCallData;
	    if (calldetail.isDirty()) {
		if (calldetail.getCallId() == 0) {
                    if ( null == calldetail.getAddTime()) {
                        Date current = new Date();
                        calldetail.setAddTime(current);
                    }
		    CallDataAccess.insert(conn, calldetail);
		} else {
		    CallDataAccess.update(conn, calldetail);
		}
	    }
	    int calldetailId = pCallData.getCallId();

	} catch (Exception e) {
	    throw new RemoteException("updateCall: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return pCallData;
    }


    /**
     * Add a property to a particular call
     * @param pCallPropertyData the call property data to set
     * @return an <code>CallPropertyData</code> value
     * @exception RemoteException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public CallPropertyData addCallProperty(CallPropertyData pCallPropertyData)
	throws RemoteException  {

	Connection conn = null;

	try {
	    conn = getConnection();

            CallPropertyData callproperty = pCallPropertyData;
	    if (callproperty.isDirty()) {
		if (callproperty.getCallPropertyId() == 0) {
                    if ( null == callproperty.getAddTime()) {
                        Date current = new Date();
                        callproperty.setAddTime(current);
                    }
		    CallPropertyDataAccess.insert(conn, callproperty);
		} else {
		    CallPropertyDataAccess.update(conn, callproperty);
		}
	    }

	} catch (Exception e) {
	    throw new RemoteException("addCallProperty: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return pCallPropertyData;
    }


    /**
     * Get call property data according to the callPropertyId
     * @param pCallPropertyId the call property Id to search the call property
     * @return an <code>CallPropertyData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public CallPropertyData getCallProperty(int pCallPropertyId)
        throws RemoteException, DataNotFoundException  {

	CallPropertyData callproperty = null;

	Connection conn = null;
	try {
	    conn = getConnection();
	    callproperty =
		CallPropertyDataAccess.select(conn, pCallPropertyId);
	} catch (DataNotFoundException e) {
	    //throw e;
	} catch (Exception e) {
	    throw new RemoteException("getCallProperty: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return callproperty;
    }


    /**
     * Get call property data vector according to the orderStatusId
     * @param pCallId the call Id to search the call property
     * @param pCallPropertyTypeCd the property type code
     * @return an <code>CallPropertyDataVector</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public CallPropertyDataVector getCallPropertyCollection(int pCallId,
                                                            String pCallPropertyTypeCd)
        throws RemoteException, DataNotFoundException  {

	CallPropertyDataVector callPropertyV = new CallPropertyDataVector();

	Connection conn = null;

	try {
	    conn = getConnection();
	    DBCriteria crit = new DBCriteria();

	    crit.addEqualTo(CallPropertyDataAccess.CALL_ID, pCallId);
            if ( null != pCallPropertyTypeCd && ! "".equals(pCallPropertyTypeCd)) {
                crit.addLikeIgnoreCase(CallPropertyDataAccess.CALL_PROPERTY_TYPE_CD,
				   pCallPropertyTypeCd);
            }
	    callPropertyV =
		CallPropertyDataAccess.select(conn, crit);
	} catch (Exception e) {
	    throw new RemoteException("getCallPropertyCollection: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return callPropertyV;
    }



    /**
     * Get knowledge data vector according to the knowledgeSearchCriteriaData
     * @param pKnowledgeSearchCriteriaData the criteria data to search the knowledges
     * @throws            RemoteException
     */
    public KnowledgeDataVector getKnowledgeCollection(KnowledgeSearchCriteriaData pKnowledgeSearchCriteria)
        throws RemoteException  {

	KnowledgeDataVector knowledgeVec = new KnowledgeDataVector();

	Connection conn = null;
	try {
	    conn = getConnection();
	    DBCriteria crit = new DBCriteria();

            if(pKnowledgeSearchCriteria.getCategoryCd().trim().length() > 0 ) {
                crit.addEqualToIgnoreCase(KnowledgeDataAccess.KNOWLEDGE_CATEGORY_CD,
					  pKnowledgeSearchCriteria.getCategoryCd().trim());
            }
            if(pKnowledgeSearchCriteria.getDescription().trim().length() > 0 ) {
                crit.addLikeIgnoreCase(KnowledgeDataAccess.LONG_DESC,
					  "%"+pKnowledgeSearchCriteria.getDescription().trim()+"%");
            }
             if(pKnowledgeSearchCriteria.getStoreId() != 0){
                    crit.addEqualTo(KnowledgeDataAccess.STORE_ID,
					  pKnowledgeSearchCriteria.getStoreId());
            }
            if(pKnowledgeSearchCriteria.getProductName().trim().length() > 0
                || pKnowledgeSearchCriteria.getSkuNum().trim().length() > 0 ) {

                DBCriteria subcrit = new DBCriteria();

                subcrit.addEqualToIgnoreCase(ItemDataAccess.ITEM_TYPE_CD,
					  RefCodeNames.ITEM_TYPE_CD.PRODUCT);

                if(pKnowledgeSearchCriteria.getProductName().trim().length() > 0 ) {
                    subcrit.addLikeIgnoreCase(ItemDataAccess.SHORT_DESC,
					  "%"+pKnowledgeSearchCriteria.getProductName().trim()+"%");
                }
                if(pKnowledgeSearchCriteria.getSkuNum().trim().length() > 0 ) {
                    subcrit.addEqualToIgnoreCase(ItemDataAccess.SKU_NUM,
					  pKnowledgeSearchCriteria.getSkuNum().trim());
                }

                IdVector itemids = ItemDataAccess.selectIdOnly(conn,
					 ItemDataAccess.ITEM_ID, subcrit);
                crit.addOneOf(KnowledgeDataAccess.ITEM_ID, itemids);
            }


            if(pKnowledgeSearchCriteria.getDateRangeBegin().trim().length() > 0
              && pKnowledgeSearchCriteria.getDateRangeEnd().trim().length() > 0
              && pKnowledgeSearchCriteria.getDateRangeBegin().trim().equals(pKnowledgeSearchCriteria.getDateRangeEnd().trim())) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Date oDate =  new Date();
                try {
                    oDate  = simpleDateFormat.parse(pKnowledgeSearchCriteria.getDateRangeBegin().trim());
                }
                catch (Exception e) {
                    oDate = null;
                }

                if(null != oDate ) {
                    crit.addEqualToIgnoreCase(KnowledgeDataAccess.ADD_DATE,
					  oDate);
                }

            }
            else {
                if(pKnowledgeSearchCriteria.getDateRangeBegin().trim().length() > 0 ) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    Date oDate =  new Date();
                    try {
                        oDate  = simpleDateFormat.parse(pKnowledgeSearchCriteria.getDateRangeBegin().trim());
                    }
                    catch (Exception e) {
                        oDate = null;
                    }

                    if(null != oDate ) {
                        crit.addGreaterOrEqual(KnowledgeDataAccess.ADD_DATE,
					  oDate);
                    }
                }
                if(pKnowledgeSearchCriteria.getDateRangeEnd().trim().length() > 0 ) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    Date oDate =  new Date();
                    try {
                        oDate  = simpleDateFormat.parse(pKnowledgeSearchCriteria.getDateRangeEnd().trim());
                    }
                    catch (Exception e) {
                        oDate = null;
                    }

                    if(null != oDate ) {
                        crit.addLessOrEqual(KnowledgeDataAccess.ADD_DATE,
					  oDate);
                    }
                }
            }


            if(pKnowledgeSearchCriteria.getKnowledgeStatusCd().trim().length() > 0 ) {
                crit.addEqualToIgnoreCase(KnowledgeDataAccess.KNOWLEDGE_STATUS_CD,
					  pKnowledgeSearchCriteria.getKnowledgeStatusCd().trim());
            }
            if(pKnowledgeSearchCriteria.getUserId().trim().length() > 0 ) {
                crit.addEqualToIgnoreCase(KnowledgeDataAccess.USER_ID,
					  pKnowledgeSearchCriteria.getUserId().trim());
            }

            knowledgeVec =
		KnowledgeDataAccess.select(conn, crit);

	} catch (Exception e) {
	    throw new RemoteException("getKnowledgeCollection: " + e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return knowledgeVec;
    }


    /**
     * Get knowledge desc data vector according to the knowledgeSearchCriteriaData
     * @param pKnowledgeSearchCriteriaData the criteria data to search the knowledges
     * @throws            RemoteException
     */
    public KnowledgeDescDataVector getKnowledgeDescCollection(KnowledgeSearchCriteriaData pKnowledgeSearchCriteria)
        throws RemoteException  {

	KnowledgeDescDataVector knowledgeDescV = null;

	Connection conn = null;
	try {

            KnowledgeDataVector knowledgeV = getKnowledgeCollection(pKnowledgeSearchCriteria);

            if ( null != knowledgeV && knowledgeV.size() > 0 ) {
                knowledgeDescV = new KnowledgeDescDataVector();
                conn = getConnection();
                for(int i = 0; i < knowledgeV.size(); i++) {
                    KnowledgeData knowledgeD = (KnowledgeData)knowledgeV.get(i);

                    KnowledgeDescData knowledgeDescD = KnowledgeDescData.createValue();
                    knowledgeDescD.setKnowledgeDetail(knowledgeD);
                    knowledgeDescD.setProductName(new String(""));
                    knowledgeDescD.setSkuNum(new String(""));

                    DBCriteria crit = new DBCriteria();
                    if( 0 != knowledgeD.getItemId()) {
                        ItemData itemD =
                                    ItemDataAccess.select(conn, knowledgeD.getItemId());
                        if ( null != itemD ) {
                            knowledgeDescD.setProductName(itemD.getShortDesc());
                            knowledgeDescD.setSkuNum(String.valueOf(itemD.getSkuNum()));
                        }
                    }

                    knowledgeDescV.add(knowledgeDescD);
                }
            }

	} catch (Exception e) {
	    throw new RemoteException("getKnowledgeDescCollection: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return knowledgeDescV;

    }


    /**
     * Get Knowledge data according to the pKnowledgeId
     * @param pKnowledgeId the Knowledge Id to search the Knowledge
     * @throws            RemoteException
     */
    public KnowledgeData getKnowledge(int pKnowledgeId)
        throws RemoteException, DataNotFoundException  {

	KnowledgeData knowledgeD = null;

	Connection conn = null;
	try {
	    conn = getConnection();
	    knowledgeD =
		KnowledgeDataAccess.select(conn, pKnowledgeId);

	} catch (DataNotFoundException e) {
	    //throw e;
	} catch (Exception e) {
	    throw new RemoteException("getKnowledge: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return knowledgeD;
    }


    /**
     * Describe <code>addKnowledge</code> method here.
     *
     * @param pKnowledgeData a <code>KnowledgeData</code> value
     * @return a <code>KnowledgeData</code> value
     * @exception RemoteException if an error occurs
     */
    public KnowledgeData addKnowledge(KnowledgeData pKnowledgeData)
	throws RemoteException
    {
	return updateKnowledge(pKnowledgeData);
    }

    /**
     * Updates the Knowledge information values to be used by the request.
     * @param pKnowledgeData  the OrderItemDetailData .
     * @return a <code>KnowledgeData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public KnowledgeData updateKnowledge(KnowledgeData pKnowledgeData)
	throws RemoteException
    {
	Connection conn = null;

	try {
	    conn = getConnection();

	    KnowledgeData knowledgedetail = pKnowledgeData;
	    if (knowledgedetail.isDirty()) {
                if(knowledgedetail.getStoreId() == 0){
                    throw new RemoteException("Cannot add/update knowledge content without a store");
                }
		if (knowledgedetail.getKnowledgeId() == 0) {
                    if ( null == knowledgedetail.getAddTime()) {
                        Date current = new Date();
                        knowledgedetail.setAddTime(current);
                    }
		    KnowledgeDataAccess.insert(conn, knowledgedetail);
		} else {
		    KnowledgeDataAccess.update(conn, knowledgedetail);
		}
	    }
	    int knowledgedetailId = pKnowledgeData.getKnowledgeId();

	} catch (Exception e) {
	    throw new RemoteException("updateKnowledge: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return pKnowledgeData;
    }


    /**
     * Add a property to a particular knowledge
     * @param pKnowledgePropertyData the knowledge property data to set
     * @return an <code>KnowledgePropertyData</code> value
     * @exception RemoteException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public KnowledgePropertyData addKnowledgeProperty(KnowledgePropertyData pKnowledgePropertyData)
	throws RemoteException  {

	Connection conn = null;

	try {
	    conn = getConnection();

            KnowledgePropertyData knowledgeproperty = pKnowledgePropertyData;
	    if (knowledgeproperty.isDirty()) {
		if (knowledgeproperty.getKnowledgePropertyId() == 0) {
                    if ( null == knowledgeproperty.getAddTime()) {
                        Date current = new Date();
                        knowledgeproperty.setAddTime(current);
                    }
		    KnowledgePropertyDataAccess.insert(conn, knowledgeproperty);
		} else {
		    KnowledgePropertyDataAccess.update(conn, knowledgeproperty);
		}
	    }

	} catch (Exception e) {
	    throw new RemoteException("addKnowledgeProperty: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return pKnowledgePropertyData;
    }


    /**
     * Get knowledge property data according to the knowledgePropertyId
     * @param pKnowledgePropertyId the knowledge property Id to search the knowledge property
     * @return an <code>KnowledgePropertyData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public KnowledgePropertyData getKnowledgeProperty(int pKnowledgePropertyId)
        throws RemoteException, DataNotFoundException  {

	KnowledgePropertyData knowledgeproperty = null;

	Connection conn = null;
	try {
	    conn = getConnection();
	    knowledgeproperty =
		KnowledgePropertyDataAccess.select(conn, pKnowledgePropertyId);
	} catch (DataNotFoundException e) {
	    //throw e;
	} catch (Exception e) {
	    throw new RemoteException("getKnowledgeProperty: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return knowledgeproperty;
    }


    /**
     * Get knowledge property data vector according to the orderStatusId
     * @param pKnowledgeId the knowledge Id to search the knowledge property
     * @param pKnowledgePropertyTypeCd the property type code
     * @return an <code>KnowledgePropertyDataVector</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public KnowledgePropertyDataVector getKnowledgePropertyCollection(int pKnowledgeId,
                                                            String pKnowledgePropertyTypeCd)
        throws RemoteException, DataNotFoundException  {

	KnowledgePropertyDataVector knowledgePropertyV = new KnowledgePropertyDataVector();

	Connection conn = null;

	try {
	    conn = getConnection();
	    DBCriteria crit = new DBCriteria();

	    crit.addEqualTo(KnowledgePropertyDataAccess.KNOWLEDGE_ID, pKnowledgeId);
            if ( null != pKnowledgePropertyTypeCd && ! "".equals(pKnowledgePropertyTypeCd)) {
                crit.addLikeIgnoreCase(KnowledgePropertyDataAccess.KNOWLEDGE_PROPERTY_TYPE_CD,
				   pKnowledgePropertyTypeCd);
            }
	    knowledgePropertyV =
		KnowledgePropertyDataAccess.select(conn, crit);
	} catch (Exception e) {
	    throw new RemoteException("getKnowledgePropertyCollection: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return knowledgePropertyV;
    }


    /**
     * Add a content to a particular knowledge
     * @param pKnowledgeContentData the knowledge content data to set
     * @return an <code>KnowledgeContentData</code> value
     * @exception RemoteException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public KnowledgeContentData addKnowledgeContent(KnowledgeContentData pKnowledgeContentData)
	throws RemoteException  {

	Connection conn = null;

	try {
	    conn = getConnection();

            KnowledgeContentData knowledgecontent = pKnowledgeContentData;
	    if (knowledgecontent.isDirty()) {
		if (knowledgecontent.getKnowledgeContentId() == 0) {
                    if ( null == knowledgecontent.getAddTime()) {
                        Date current = new Date();
                        knowledgecontent.setAddTime(current);
                    }
		    KnowledgeContentDataAccess.insert(conn, knowledgecontent);
		} else {
		    KnowledgeContentDataAccess.update(conn, knowledgecontent);
		}
	    }

	} catch (Exception e) {
	    throw new RemoteException("addKnowledgeContent: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return pKnowledgeContentData;
    }


    /**
     * Get knowledge content data according to the knowledgeContentId
     * @param pKnowledgeContentId the knowledge content Id to search the knowledge content
     * @return an <code>KnowledgeContentData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public KnowledgeContentData getKnowledgeContent(int pKnowledgeContentId)
        throws RemoteException, DataNotFoundException  {

	KnowledgeContentData knowledgecontent = null;

	Connection conn = null;
	try {
	    conn = getConnection();
	    knowledgecontent =
		KnowledgeContentDataAccess.select(conn, pKnowledgeContentId);
	} catch (DataNotFoundException e) {
	    //throw e;
	} catch (Exception e) {
	    throw new RemoteException("getKnowledgeContent: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return knowledgecontent;
    }


    /**
     * Get knowledge content data vector according to the orderStatusId
     * @param pKnowledgeId the knowledge Id to search the knowledge content
     * @param pKnowledgeContentTypeCd the content type code
     * @return an <code>KnowledgeContentDataVector</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public KnowledgeContentDataVector getKnowledgeContentCollection(int pKnowledgeId)
        throws RemoteException, DataNotFoundException  {

	KnowledgeContentDataVector knowledgeContentV = new KnowledgeContentDataVector();

	Connection conn = null;

	try {
	    conn = getConnection();
	    DBCriteria crit = new DBCriteria();

	    crit.addEqualTo(KnowledgeContentDataAccess.KNOWLEDGE_ID, pKnowledgeId);
	    knowledgeContentV =
		KnowledgeContentDataAccess.select(conn, crit);
	} catch (Exception e) {
	    throw new RemoteException("getKnowledgeContentCollection: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return knowledgeContentV;
    }




}
