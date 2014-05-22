package com.cleanwise.service.api.session;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.AddressDataAccess;
import com.cleanwise.service.api.dao.BackorderDataAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.CostCenterDataAccess;
import com.cleanwise.service.api.dao.CreditCardDAO;
import com.cleanwise.service.api.dao.DistShippingOptionsDataAccess;
import com.cleanwise.service.api.dao.GroupAssocDataAccess;
import com.cleanwise.service.api.dao.InventoryOrderLogDataAccess;
import com.cleanwise.service.api.dao.InvoiceCustDataAccess;
import com.cleanwise.service.api.dao.InvoiceCustDetailDataAccess;
import com.cleanwise.service.api.dao.InvoiceDistDataAccess;
import com.cleanwise.service.api.dao.InvoiceDistDetailDataAccess;
import com.cleanwise.service.api.dao.ItemSubstitutionDataAccess;
import com.cleanwise.service.api.dao.OrderAddressDataAccess;
import com.cleanwise.service.api.dao.OrderAssocDataAccess;
import com.cleanwise.service.api.dao.OrderCreditCardDataAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.OrderFreightDataAccess;
import com.cleanwise.service.api.dao.OrderItemActionDataAccess;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.dao.OrderItemMetaDataAccess;
import com.cleanwise.service.api.dao.OrderMetaDataAccess;
import com.cleanwise.service.api.dao.OrderPropertyDataAccess;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.dao.ShoppingDAO;
import com.cleanwise.service.api.dao.TradingProfileConfigDataAccess;
import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.dao.WorkOrderDataAccess;
import com.cleanwise.service.api.dao.WorkOrderItemDataAccess;
import com.cleanwise.service.api.dao.WorkflowQueueDataAccess;
import com.cleanwise.service.api.dao.WorkflowRuleDataAccess;
import com.cleanwise.service.api.framework.ShoppingServicesAPI;
import com.cleanwise.service.api.pipeline.OrderErpUpdater;
import com.cleanwise.service.api.util.CacheManager;
import com.cleanwise.service.api.util.CreditCardUtil;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PipelineCalculationOperations;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.TaxUtilAvalara;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.AddressDataVector;
import com.cleanwise.service.api.value.AddressInfoView;
import com.cleanwise.service.api.value.BackorderData;
import com.cleanwise.service.api.value.BackorderDataVector;
import com.cleanwise.service.api.value.BillToData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.DistOptionsForShippingView;
import com.cleanwise.service.api.value.DistOptionsForShippingViewVector;
import com.cleanwise.service.api.value.DistShippingOptionsData;
import com.cleanwise.service.api.value.DistShippingOptionsDataVector;
import com.cleanwise.service.api.value.FreightHandlerView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.InvoiceCreditCardTransViewVector;
import com.cleanwise.service.api.value.InvoiceCustCritView;
import com.cleanwise.service.api.value.InvoiceCustData;
import com.cleanwise.service.api.value.InvoiceCustDataVector;
import com.cleanwise.service.api.value.InvoiceCustDescData;
import com.cleanwise.service.api.value.InvoiceCustDescDataVector;
import com.cleanwise.service.api.value.InvoiceCustDetailData;
import com.cleanwise.service.api.value.InvoiceCustDetailDataVector;
import com.cleanwise.service.api.value.InvoiceCustDetailDescData;
import com.cleanwise.service.api.value.InvoiceCustDetailDescDataVector;
import com.cleanwise.service.api.value.InvoiceCustInfoViewVector;
import com.cleanwise.service.api.value.InvoiceCustView;
import com.cleanwise.service.api.value.InvoiceCustViewVector;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDataVector;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.InvoiceDistDetailDescData;
import com.cleanwise.service.api.value.InvoiceDistDetailDescDataVector;
import com.cleanwise.service.api.value.ItemInfoView;
import com.cleanwise.service.api.value.ItemInfoViewVector;
import com.cleanwise.service.api.value.ItemRequestData;
import com.cleanwise.service.api.value.ItemSubstitutionData;
import com.cleanwise.service.api.value.ItemSubstitutionDataVector;
import com.cleanwise.service.api.value.JanitorClosetData;
import com.cleanwise.service.api.value.JdOrderStatusView;
import com.cleanwise.service.api.value.JdOrderStatusViewVector;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.OrderAddressDataVector;
import com.cleanwise.service.api.value.OrderAssocData;
import com.cleanwise.service.api.value.OrderAssocDataVector;
import com.cleanwise.service.api.value.OrderCreditCardData;
import com.cleanwise.service.api.value.OrderCreditCardDataVector;
import com.cleanwise.service.api.value.OrderCreditCardDescData;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderDataVector;
import com.cleanwise.service.api.value.OrderFreightData;
import com.cleanwise.service.api.value.OrderFreightDataVector;
import com.cleanwise.service.api.value.OrderHistoryDataVector;
import com.cleanwise.service.api.value.OrderInfoDataView;
import com.cleanwise.service.api.value.OrderInfoView;
import com.cleanwise.service.api.value.OrderItemActionData;
import com.cleanwise.service.api.value.OrderItemActionDataVector;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderItemDescData;
import com.cleanwise.service.api.value.OrderItemDescDataVector;
import com.cleanwise.service.api.value.OrderItemJoinData;
import com.cleanwise.service.api.value.OrderItemJoinDataVector;
import com.cleanwise.service.api.value.OrderItemMetaData;
import com.cleanwise.service.api.value.OrderItemMetaDataVector;
import com.cleanwise.service.api.value.OrderJoinData;
import com.cleanwise.service.api.value.OrderMetaData;
import com.cleanwise.service.api.value.OrderMetaDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.OrderStatusCriteriaData;
import com.cleanwise.service.api.value.OrderStatusDescData;
import com.cleanwise.service.api.value.OrderStatusDescDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.ReorderItemData;
import com.cleanwise.service.api.value.ReplacedOrderItemView;
import com.cleanwise.service.api.value.ReplacedOrderItemViewVector;
import com.cleanwise.service.api.value.ReplacedOrderView;
import com.cleanwise.service.api.value.ReplacedOrderViewVector;
import com.cleanwise.service.api.value.ShoppingInfoData;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.StoreOrderChangeRequestData;
import com.cleanwise.service.api.value.TaxQuery;
import com.cleanwise.service.api.value.TaxQueryResponse;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.WorkOrderData;
import com.cleanwise.service.api.value.WorkOrderItemData;
import com.cleanwise.service.api.value.WorkflowRuleData;
import com.cleanwise.service.api.value.WorkflowRuleDataVector;
import com.cleanwise.view.utils.Constants;

/**
 * class <code>OrderBean</code> provides the methods to manipulated
 * Order objects.
 *
 * @author Yuriy
 */
public class OrderBean extends ShoppingServicesAPI
{
	static final Logger log = Logger.getLogger(OrderBean.class);

    /**
     *
     */
    public OrderBean()
    {
    }

    /**
     *
     */
    public void ejbCreate()
    throws CreateException, RemoteException
    {
    }

    /**
     * Adds the janitor closet item information
     * @param pJanitorClosetItem  the janitor closet item data.
     * @param request  the item request data.
     * @return new ItemRequestData()
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemRequestData addJanitorClosetItem(JanitorClosetData pJanitorClosetItem,
            ItemRequestData request)
    throws RemoteException
    {
        return ItemRequestData.createValue();
    }



    /**
     * Creates the order information values to be used by the request.
     * @param pData  the order  data.
     * @return OrderData
     * @throws            RemoteException Required by EJB 1.0
     */
    public OrderData createOrder(OrderData pData)
    throws RemoteException
    {
        return OrderData.createValue();
    }

    /**
     * Creates the order information values to be used by the request.
     * @param pItemId  the item identifier.
     * @param pBusEntityId  the business entity identifier.
     * @return OrderData
     * @throws            RemoteException Required by EJB 1.0
     */
    public OrderData createOrder(int pItemId, int pBusEntityId)
    throws RemoteException
    {
        return OrderData.createValue();
    }

    /**
     * Generates the order table  to be used by the request.
     * @param pOrder  the order data.
     * @return java.util.Hashtable
     * @throws            RemoteException Required by EJB 1.0
     */
    public Hashtable generateOrderTable(OrderData pOrder)
    throws RemoteException
    {
        return new Hashtable();
    }

    /**
     * Gets the item price information to be used by the request.
     * @param pOrder  the order data.
     * @return double
     * @throws            RemoteException Required by EJB 1.0
     */
    public double getItemPrice(OrderData pOrder)
    throws RemoteException
    {
        return 0D;
    }

    /**
     * Gets the item contract price information to be used by the request.
     * @param pOrder  the order data.
     * @return double
     * @throws            RemoteException Required by EJB 1.0
     */
    public double getItemContractPrice(OrderData pOrder)
    throws RemoteException
    {
        return 0D;
    }

    /**
     * Gets the order information to be used by the request.
     * @param pItem  the re-order item data.
     * @return OrderData
     * @throws            RemoteException Required by EJB 1.0
     */
    public OrderData getOrder(ReorderItemData pItem)
    throws RemoteException
    {
        return OrderData.createValue();
    }

    /**
     * Gets the item quantity information to be used by the request.
     * @param pOrder  the order data.
     * @return int
     * @throws            RemoteException Required by EJB 1.0
     */
    public int getItemQty(OrderData pOrder)
    throws RemoteException
    {
        return 0;
    }

    /*******************************************************************/
    /**
     * Initializes order number for the store, if it does not exist
     * (temporary decesion until admin service would do it)
     * @param pStoreId the store identifier
     * @param pUser  the user login name
     * @throws            RemoteException
     */
    public void initOrderNumber(int pStoreId, String pUser)
    throws RemoteException
    {

        Connection con = null;

        try
        {
            con = getConnection();

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pStoreId);
            dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,
                           RefCodeNames.PROPERTY_TYPE_CD.STORE_ORDER_NUM);

            PropertyDataVector propertyDV = PropertyDataAccess.select(con, dbc);

            if (propertyDV.size() == 0)
            {
              dbc = new DBCriteria();
              dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,pStoreId);
              dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,
                         RefCodeNames.PROPERTY_TYPE_CD.ORDER_NUMBERING_STORE_ID);
              PropertyDataVector pDV = PropertyDataAccess.select(con, dbc);
              if(pDV.size()==0 || ((PropertyData) pDV.get(0)).getBusEntityId()==pStoreId) {

                PropertyData propertyD = PropertyData.createValue();
                propertyD.setPropertyId(0); //int mPropertyId;// SQL type:NUMBER, not null
                propertyD.setBusEntityId(pStoreId); //int mBusEntityId;// SQL type:NUMBER
                propertyD.setUserId(0); //int mUserId;// SQL type:NUMBER
                propertyD.setShortDesc(
                    RefCodeNames.PROPERTY_TYPE_CD.STORE_ORDER_NUM //String mShortDesc;// SQL type:VARCHAR2
                );
                propertyD.setValue("0"); //String mValue;// SQL type:VARCHAR2
                propertyD.setPropertyStatusCd(
                    RefCodeNames.PROPERTY_STATUS_CD.ACTIVE //String mPropertyStatusCd;// SQL type:VARCHAR2, not null
                );  
                propertyD.setPropertyTypeCd(
                    RefCodeNames.PROPERTY_TYPE_CD.STORE_ORDER_NUM //String mPropertyTypeCd;// SQL type:VARCHAR2, not null
                );
                propertyD.setAddDate(null); //Date mAddDate;// SQL type:DATE, not null
                propertyD.setAddBy(pUser); //String mAddBy;// SQL type:VARCHAR2
                propertyD.setModDate(null); //Date mModDate;// SQL type:DATE, not null
                propertyD.setModBy(pUser); //String mModBy;// SQL type:VARCHAR2
                PropertyDataAccess.insert(con, propertyD);
              }
            }
        }
        catch (NamingException exc)
        {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. OrderBean.initOrderNumber() Naming Exception happened");
        }
        catch (SQLException exc)
        {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. OrderBean.initOrderNumber() SQL Exception happened");
        }
        finally
        {
            closeConnection(con);
        }
    }

    /**
     * Get order data vector according to the orderStatusCriteriaData
     * @param pOrderStatusCriteria an <code>OrderStatusCriteriaData</code> value
     * @return an <code>OrderDataVector</code> value
     * @exception            RemoteException
     */
    public OrderDataVector getOrderStatusCollection(OrderStatusCriteriaData pOrderStatusCriteria)
    throws RemoteException
    {

        OrderDataVector statusVec = new OrderDataVector();
        Connection conn = null;

        try{
            conn = getConnection();
            statusVec = getOrdersInCriteria(conn, pOrderStatusCriteria, null);
        }catch (Exception e){
            throw processException(e);

        }finally{
            closeConnection(conn);
        }

        return statusVec;
    }

    /**
     * Get exceptional order data vector according to the orderStatusCriteriaData
     * @param pOrderStatusCriteria an <code>OrderStatusCriteriaData</code> value
     * @return an <code>OrderDataVector</code> value
     * @exception            RemoteException
     */
    public OrderDataVector getExceptionOrderCollection(OrderStatusCriteriaData pOrderStatusCriteria)
    throws RemoteException
    {
        Connection conn = null;

        try
        {
            conn = getConnection();
            String exceptionTypeCd = pOrderStatusCriteria.getExceptionTypeCd().trim();

            if (null == exceptionTypeCd)
                exceptionTypeCd = "---";

            IdVector exceptionIdV = new IdVector();

            if (RefCodeNames.ORDER_EXCEPTION_TYPE_CD.INBOUND_ORDER.equals(
                        exceptionTypeCd))
            {

                IdVector subIdV = new IdVector();
                DBCriteria subcrit = new DBCriteria();
                subcrit.addEqualToIgnoreCase(OrderDataAccess.ORDER_STATUS_CD,
                                             RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
                subcrit.addEqualToIgnoreCase(OrderDataAccess.EXCEPTION_IND,
                                             "Y");
                subIdV = OrderDataAccess.selectIdOnly(conn,
                                                      OrderDataAccess.ORDER_ID,
                                                      subcrit);

                if (null != subIdV && 0 < subIdV.size())
                {
                    exceptionIdV.addAll(subIdV);
                }
            }

            if (RefCodeNames.ORDER_EXCEPTION_TYPE_CD.ORDER_TO_ERP.equals(
                        exceptionTypeCd))
            {

                IdVector subIdV = new IdVector();
                DBCriteria subcrit = new DBCriteria();
                subcrit.addEqualToIgnoreCase(OrderDataAccess.ORDER_STATUS_CD,
                                             RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED);
                subIdV = OrderDataAccess.selectIdOnly(conn,
                                                      OrderDataAccess.ORDER_ID,
                                                      subcrit);

                if (null != subIdV && 0 < subIdV.size())
                {
                    exceptionIdV.addAll(subIdV);
                }
            }

            if (RefCodeNames.ORDER_EXCEPTION_TYPE_CD.PO_FROM_ERP.equals(
                        exceptionTypeCd))
            {

                IdVector subIdV = new IdVector();
                DBCriteria subcrit = new DBCriteria();
                subcrit.addEqualToIgnoreCase(OrderDataAccess.ORDER_STATUS_CD,
                                             RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED_PO_ERROR);
                subIdV = OrderDataAccess.selectIdOnly(conn,
                                                      OrderDataAccess.ORDER_ID,
                                                      subcrit);

                if (null != subIdV && 0 < subIdV.size())
                {
                    exceptionIdV.addAll(subIdV);
                }
            }

            if (RefCodeNames.ORDER_EXCEPTION_TYPE_CD.PO_TO_VENDOR.equals(
                        exceptionTypeCd))
            {

                IdVector subIdV = new IdVector();
                DBCriteria subcrit = new DBCriteria();
                subcrit.addEqualToIgnoreCase(
                    OrderItemDataAccess.ORDER_ITEM_STATUS_CD,
                    RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED);
                subIdV = OrderItemDataAccess.selectIdOnly(conn,
                         OrderItemDataAccess.ORDER_ID,
                         subcrit);

                if (null != subIdV && 0 < subIdV.size())
                {
                    exceptionIdV.addAll(subIdV);
                }
            }

            if (RefCodeNames.ORDER_EXCEPTION_TYPE_CD.VENDOR_INVOICE.equals(
                        exceptionTypeCd))
            {

                IdVector subIdV = new IdVector();
                DBCriteria subcrit = new DBCriteria();
                subcrit.addEqualToIgnoreCase(
                    InvoiceDistDataAccess.INVOICE_STATUS_CD,
                    RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW);
                subIdV = InvoiceDistDataAccess.selectIdOnly(conn,
                         InvoiceDistDataAccess.ORDER_ID,
                         subcrit);

                if (null != subIdV && 0 < subIdV.size())
                {
                    exceptionIdV.addAll(subIdV);
                }
            }

            if (RefCodeNames.ORDER_EXCEPTION_TYPE_CD.INVOICE_TO_ERP.equals(
                        exceptionTypeCd))
            {

                IdVector subIdV = new IdVector();
                DBCriteria subcrit = new DBCriteria();
                subcrit.addEqualToIgnoreCase(
                    InvoiceDistDataAccess.INVOICE_STATUS_CD,
                    RefCodeNames.INVOICE_STATUS_CD.ERP_REJECTED);
                subIdV = InvoiceDistDataAccess.selectIdOnly(conn,
                         InvoiceDistDataAccess.ORDER_ID,
                         subcrit);

                if (null != subIdV && 0 < subIdV.size())
                {
                    exceptionIdV.addAll(subIdV);
                }
            }

            if (RefCodeNames.ORDER_EXCEPTION_TYPE_CD.INVOICE_FROM_ERP.equals(
                        exceptionTypeCd))
            {

                IdVector subIdV = new IdVector();
                DBCriteria subcrit = new DBCriteria();
                subcrit.addEqualToIgnoreCase(
                    InvoiceCustDataAccess.INVOICE_STATUS_CD,
                    RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED_ERROR);
                subIdV = InvoiceCustDataAccess.selectIdOnly(conn,
                         InvoiceCustDataAccess.ORDER_ID,
                         subcrit);

                if (null != subIdV && 0 < subIdV.size())
                {
                    exceptionIdV.addAll(subIdV);
                }

                subIdV = new IdVector();
                subcrit = new DBCriteria();
                subcrit.addEqualToIgnoreCase(
                    InvoiceCustDataAccess.INVOICE_STATUS_CD,
                    RefCodeNames.INVOICE_STATUS_CD.ERP_GENERATED_ERROR);
                subIdV = InvoiceCustDataAccess.selectIdOnly(conn,
                         InvoiceCustDataAccess.ORDER_ID,
                         subcrit);

                if (null != subIdV && 0 < subIdV.size())
                {
                    exceptionIdV.addAll(subIdV);
                }
            }

            if (RefCodeNames.ORDER_EXCEPTION_TYPE_CD.CUSTOMER_ACKNOWLEDGEMENT.equals(exceptionTypeCd)
            ||RefCodeNames.ORDER_EXCEPTION_TYPE_CD.DISTRIBUTOR_ACKNOWLEDGEMENT.equals(exceptionTypeCd) )
            {
                IdVector subIdV = new IdVector();
                DBCriteria subcrit = new DBCriteria();
                if(RefCodeNames.ORDER_EXCEPTION_TYPE_CD.DISTRIBUTOR_ACKNOWLEDGEMENT.equals(exceptionTypeCd)){
                    ArrayList types = new ArrayList();
                    types.add(RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_ERROR);
                    types.add(RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_REJECT);
                    subcrit.addOneOf(OrderItemDataAccess.ORDER_ITEM_STATUS_CD,types);
                }else{
                    ArrayList tmp = new ArrayList();
                    tmp.add(RefCodeNames.ACK_STATUS_CD.CANCELLED_ACK_FAILED);
                    tmp.add(RefCodeNames.ACK_STATUS_CD.CUST_ACK_FAILED);
                    subcrit.addOneOf(OrderItemDataAccess.ACK_STATUS_CD,tmp);
                }
                subIdV = OrderItemDataAccess.selectIdOnly(conn,OrderItemDataAccess.ORDER_ID,subcrit);

                if (null != subIdV && !subIdV.isEmpty()){
                    exceptionIdV.addAll(subIdV);
                }
            }

            if (RefCodeNames.ORDER_EXCEPTION_TYPE_CD.CUSTOMER_SHIP_NOTICE.equals(
                        exceptionTypeCd))
            {

                IdVector subIdV = new IdVector();
                IdVector sub2IdV = new IdVector();
                DBCriteria subcrit = new DBCriteria();
                subcrit.addEqualToIgnoreCase(
                    InvoiceCustDetailDataAccess.SHIP_STATUS_CD,
                    RefCodeNames.SHIP_STATUS_CD.FAILED);
                sub2IdV = InvoiceCustDetailDataAccess.selectIdOnly(conn,
                          InvoiceCustDetailDataAccess.INVOICE_CUST_ID,
                          subcrit);

                if (null != sub2IdV && 0 < sub2IdV.size())
                {
                    subcrit = new DBCriteria();
                    subcrit.addOneOf(InvoiceCustDataAccess.INVOICE_CUST_ID,
                                     sub2IdV);
                    subIdV = InvoiceCustDataAccess.selectIdOnly(conn,
                             InvoiceCustDataAccess.ORDER_ID,
                             subcrit);

                    if (null != subIdV && 0 < subIdV.size())
                    {
                        exceptionIdV.addAll(subIdV);
                    }
                }
            }

            if (RefCodeNames.ORDER_EXCEPTION_TYPE_CD.CUSTOMER_INVOICE.equals(
                        exceptionTypeCd))
            {

                IdVector subIdV = new IdVector();
                DBCriteria subcrit = new DBCriteria();
                subcrit.addEqualToIgnoreCase(
                    InvoiceCustDataAccess.INVOICE_STATUS_CD,
                    RefCodeNames.INVOICE_STATUS_CD.CUST_INVOICED_FAILED);
                subIdV = InvoiceCustDataAccess.selectIdOnly(conn,
                         InvoiceCustDataAccess.ORDER_ID,
                         subcrit);

                if (null != subIdV && 0 < subIdV.size())
                {
                    exceptionIdV.addAll(subIdV);
                }
            }

            if (RefCodeNames.ORDER_EXCEPTION_TYPE_CD.CIT_INVOICE.equals(
                        exceptionTypeCd))
            {

                IdVector subIdV = new IdVector();
                DBCriteria subcrit = new DBCriteria();
                List status = new ArrayList();
                status.add(RefCodeNames.CIT_STATUS_CD.FAILED);
                status.add(RefCodeNames.CIT_STATUS_CD.REJECTED);
                subcrit.addOneOf(InvoiceCustDataAccess.CIT_STATUS_CD, status);
                subIdV = InvoiceCustDataAccess.selectIdOnly(conn,
                         InvoiceCustDataAccess.ORDER_ID,
                         subcrit);

                if (null != subIdV && 0 < subIdV.size())
                {
                    exceptionIdV.addAll(subIdV);
                }
            }
            return getOrdersInCriteria(conn, pOrderStatusCriteria,
                                       exceptionIdV);
        }
        catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    private OrderDataVector getOrdersInCriteria(Connection conn,
            OrderStatusCriteriaData pOrderStatusCriteria,
            IdVector pOrderIdV)
    throws RemoteException,
                java.sql.SQLException,
                com.cleanwise.service.api.util.DataNotFoundException,
                Exception
    {
        return OrderDAO.getOrdersInCriteria(conn, pOrderStatusCriteria, pOrderIdV);
    }

    /**
     * Get order data according to the orderStatusId
     * @param pOrderId the order Id to search the order
     * @return an <code>OrderData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderData getOrderStatus(int pOrderId)
    throws RemoteException, DataNotFoundException
    {

        OrderData orderStatus = null;
        Connection conn = null;

        try
        {
            conn = getConnection();
            orderStatus = OrderDataAccess.select(conn, pOrderId);
        }
        catch (DataNotFoundException e)
        {
            //throw e;
        }
        catch (Exception e)
        {
            throw new RemoteException("getOrderStatus: " + e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return orderStatus;
    }

    /**
     * Get order status Desc data according to the orderId
     * @param pOrderId the order Id to search the order status
     * @throws            RemoteException, DataNotFoundException
     */

    public OrderStatusDescData getOrderStatusDesc(int pOrderId)
    throws RemoteException,
                DataNotFoundException
    {

        OrderStatusDescData orderStatusDesc = null;
        Connection conn = null;

        try
        {
            conn = getConnection();

            OrderData orderStatus = OrderDataAccess.select(conn, pOrderId);
            orderStatusDesc = OrderStatusDescData.createValue();
            orderStatusDesc.setOrderDetail(orderStatus);
            boolean allowModifFl = calcAllowModifFlag(conn,orderStatus);

            orderStatusDesc.setAllowModifFl(allowModifFl);
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderItemDataAccess.ORDER_ID, pOrderId);

            OrderItemDataVector orderItemV = OrderItemDataAccess.select(conn,
                                             crit);
            orderStatusDesc.setOrderItemList(orderItemV);

            if (null != orderItemV && 0 < orderItemV.size())   {

            	initOrderItemActionProperties(orderStatusDesc,orderItemV,conn);
                OrderItemData orderItemD = (OrderItemData)orderItemV.get(0);
                orderStatusDesc.setErpOrderNum(orderItemD.getErpOrderNum());

    int previd = 0;
    for ( int fh_i = 0; null != orderItemV && fh_i < orderItemV.size(); fh_i++ ) {
        int fhid = ((OrderItemData)orderItemV.get(fh_i)).getFreightHandlerId();
        if (fhid > 0 && fhid != previd ) {
      orderStatusDesc.addShipVia
          (BusEntityDAO.getFreightHandler(conn, fhid));
      previd = fhid;
        }
    }
      }


            //get credit card information
            crit = new DBCriteria();
            crit.addEqualTo(OrderCreditCardDataAccess.ORDER_ID, pOrderId);
            OrderCreditCardDataVector ccs = OrderCreditCardDataAccess.select(conn,crit);
            if(ccs.size() == 1){
                orderStatusDesc.setOrderCreditCardData((OrderCreditCardData)ccs.get(0));
            }else if(ccs.size() != 0){
                throw new RemoteException("Found "+ccs.size()+" for this order please contact support");
            }else{
                orderStatusDesc.setOrderCreditCardData(null);
            }

            // Order ship to and bill to information.
            crit = new DBCriteria();
            crit.addEqualTo(OrderAddressDataAccess.ORDER_ID, pOrderId);

            OrderAddressDataVector oadv = OrderAddressDataAccess.select(conn,
                                          crit);
            orderStatusDesc = setOrderAddresses( orderStatusDesc, oadv );

            // add the accountName
            try{

                BusEntityData accountD =  mCacheManager.getBusEntityDAO().getAccountById
        (conn,orderStatus.getAccountId());
                orderStatusDesc.setAccountId(accountD.getBusEntityId());
                orderStatusDesc.setAccountName(accountD.getShortDesc());
            }catch(DataNotFoundException e){
                orderStatusDesc.setAccountId(0);
                orderStatusDesc.setAccountName("Error");
            }

            // add the site information
            SiteData siteD = getSiteData(conn, orderStatus);
            orderStatusDesc.setOrderSiteData(siteD);

            // get the invoiceDistList
            crit = new DBCriteria();
            crit.addEqualTo(InvoiceDistDataAccess.ORDER_ID,
                            orderStatus.getOrderId());

            InvoiceDistDataVector invDistV =
                InvoiceDistDataAccess.select(conn,crit);

            // get the shipFrom name
            String shipFromName = new String("");
            ArrayList shipFromNameList = new ArrayList();

            for (int j = 0; null != invDistV && j < invDistV.size(); j++)
            {

                String fromName = ((InvoiceDistData)invDistV.get(j)).getShipFromName();

                if (null == fromName)
                {
                    fromName = new String("");
                }

                if (0 == j)
                {
                    shipFromNameList.add(fromName);
                }
                else
                {

                    boolean matchFlag = false;

                    for (int k = 0; k < shipFromNameList.size(); k++)
                    {

                        if (fromName.equals((String)shipFromNameList.get(k)))
                        {
                            matchFlag = true;
                            break;
                        }
                    }

                    if (false == matchFlag)
                    {
                        shipFromNameList.add(fromName);
                    }
                }
            }

            for (int j = 0;
                    null != shipFromNameList &&
                    j < shipFromNameList.size(); j++)
            {
                shipFromName += (String)shipFromNameList.get(j) + " / ";
            }

            if (3 < shipFromName.length())
            {
                orderStatusDesc.setShipFromName
                (shipFromName.substring
                 (0,shipFromName.length() - 3));
            }


            // Order reference number information.
            OrderPropertyDataVector refNumV = lookupOrderRefNumber(conn, pOrderId);
            if (null != refNumV)
            {
                orderStatusDesc.setReferenceNumList(refNumV);
            }

            crit = new DBCriteria();
            crit.addEqualTo(OrderMetaDataAccess.ORDER_ID, pOrderId);
            OrderMetaDataVector metaV = OrderMetaDataAccess.select(conn, crit);
            orderStatusDesc.setOrderMetaData(metaV);

            int refOrderId = orderStatusDesc.getOrderDetail().getRefOrderId();
            if(refOrderId > 0){
                try{
                    orderStatusDesc.setRefOrder(OrderDataAccess.select(conn,refOrderId));
                }catch(DataNotFoundException e){
                    logError("Previous Order could not be found, ref order id: "+refOrderId);
                }
            }
            //Get replaced orders if the order is a consolidated order
            String orderTypeCd = orderStatus.getOrderTypeCd();
            if(RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(orderTypeCd)) {
              ReplacedOrderViewVector replOrderVwV =
                                             getReplacedOrders(conn, pOrderId);
              orderStatusDesc.setReplacedOrders(replOrderVwV);
            }
            //Get consolidated order if the order was consolidated
            if(RefCodeNames.ORDER_STATUS_CD.CANCELLED.
                                    equals(orderStatus.getOrderStatusCd())) {
              OrderData consolidatedOrderD =
                          getConsolidatedOrderForReplaced(conn, pOrderId);
              orderStatusDesc.setConsolidatedOrder(consolidatedOrderD);
            }

      // If this order is in a state where it can be
      // modified, pull up the shipping options
      // available.
      if ( allowShippingModifications(orderStatus) ) {
    // Pull up the distributors in the order and freight
    // handlers available for shipping.
    java.util.Collection distErpNums =
        orderStatusDesc.getDistERPNumsInItems();
    if ( null != distErpNums && distErpNums.size() > 0 ) {
        java.util.Iterator it = distErpNums.iterator();

        DistOptionsForShippingViewVector shipOptionList =
      new DistOptionsForShippingViewVector();

        while ( it.hasNext() ) {
      String erpn = (String)it.next();
      logDebug (" lookup dist and freight handler for dist erp " + erpn);
      BusEntityData distbed =
          mCacheManager.getBusEntityDAO()
          .getDistributorByErpNum(conn, erpn);

      logDebug (" found dist " + distbed);
      if ( null != distbed ) {
          // look for shipping options.
          crit = new DBCriteria();
          crit.addEqualTo(DistShippingOptionsDataAccess.DISTRIBUTOR_ID, distbed.getBusEntityId() );

          DistShippingOptionsDataVector  dsov =
        DistShippingOptionsDataAccess.select(conn, crit);
          if ( dsov != null && dsov.size() > 0 ) {
        DistShippingOptionsData dso =
            (DistShippingOptionsData)dsov.get(0);
        if ( dsov.size() > 1 ) {
            logError(" More than one shipping option configured for distbe=" + distbed + " using dso=" + dso );
        }
        // Look up the freight handler.
        BusEntityData fhdata =
        BusEntityDataAccess.select(conn,dso.getFreightHandlerId() );
        if ( null != fhdata ) {
            DistOptionsForShippingView shipOpt =
          DistOptionsForShippingView.createValue();
            shipOpt.setDistBusEntity(distbed);
            shipOpt.setFreightHandlerBusEntity(fhdata);
            shipOptionList.add(shipOpt);
            logDebug(" pOrderId=" + pOrderId
               + " shipOpt=" + shipOpt );
        }

          }

      }
        }

        orderStatusDesc.setDistShippingOptions(shipOptionList);
    }
      }

        }
        catch (DataNotFoundException e)
        {
            e.printStackTrace();
            //throw e;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RemoteException("getOrderStatusDesc: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return orderStatusDesc;
    }

    private boolean allowShippingModifications( OrderData pOrderData ) {
  if ( null == pOrderData ) return false;
  String orderStatusCd = pOrderData.getOrderStatusCd();

  return
      null != orderStatusCd &&
      (RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW.equals
       (orderStatusCd) ||
       RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW.equals
       (orderStatusCd)
       )
      ;
    }

    private OrderPropertyDataVector lookupOrderRefNumber(Connection conn,
    int pOrderId) throws Exception {

        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(OrderPropertyDataAccess.ORDER_ID, pOrderId);
        crit.addNotEqualToIgnoreCase(
        OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
        RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        crit.addNotEqualToIgnoreCase(
        OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
        RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_ORDER_DATE);
        crit.addNotEqualToIgnoreCase(
        OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
        RefCodeNames.ORDER_PROPERTY_TYPE_CD.REQUESTED_SHIP_DATE);
        crit.addNotEqualToIgnoreCase(
        OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
        RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS);

        return     OrderPropertyDataAccess.select(conn, crit);
    }

   //---------------------------------------------------------------------------
   private ReplacedOrderViewVector getReplacedOrders(Connection pConn, int pOrderId)
   throws Exception
   {
     //Get replaced orders if the order is a consolidated order
     DBCriteria crit = new DBCriteria();
     crit.addEqualTo(OrderAssocDataAccess.ORDER2_ID,pOrderId);
     crit.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_CD,
                            RefCodeNames.ORDER_ASSOC_CD.CONSOLIDATED);
     crit.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_STATUS_CD,
                            RefCodeNames.ORDER_ASSOC_STATUS_CD.ACTIVE);
     String assocReq =
       OrderAssocDataAccess.getSqlSelectIdOnly(OrderAssocDataAccess.ORDER1_ID,crit);
     crit = new DBCriteria();
     crit.addOneOf(OrderDataAccess.ORDER_ID,assocReq);
     crit.addOrderBy(OrderDataAccess.ORDER_ID);
     OrderDataVector orderDV = OrderDataAccess.select(pConn,crit);
     IdVector replOrderIdV = new IdVector();
     ReplacedOrderViewVector replOrderVwV =
                                       new ReplacedOrderViewVector();
     for(Iterator iter=orderDV.iterator(); iter.hasNext(); ) {
       OrderData oD = (OrderData) iter.next();
       replOrderIdV.add(new Integer(oD.getOrderId()));
       ReplacedOrderView replOrderVw = ReplacedOrderView.createValue();
       replOrderVwV.add(replOrderVw);
       replOrderVw.setOrderId(oD.getOrderId());
       replOrderVw.setOrderNum(oD.getOrderNum());
       replOrderVw.setRefOrderNum(oD.getRefOrderNum());
       replOrderVw.setRequestPoNum(oD.getRequestPoNum());
       replOrderVw.setOrderDate(oD.getOriginalOrderDate());
       replOrderVw.setOrderSiteName(oD.getOrderSiteName());
       replOrderVw.setItems(new ReplacedOrderItemViewVector());
     }

     crit = new DBCriteria();
     crit.addOneOf(OrderItemDataAccess.ORDER_ID,replOrderIdV);
     crit.addOrderBy(OrderItemDataAccess.ORDER_ID);
     OrderItemDataVector replOrderItemDV =
                                OrderItemDataAccess.select(pConn,crit);
     OrderItemData replOrderItemD = null;
     for(Iterator iter=replOrderVwV.iterator(), iter1=replOrderItemDV.iterator();
                                                iter.hasNext();) {
       ReplacedOrderView replOrderVw = (ReplacedOrderView) iter.next();
       int replOrderId = replOrderVw.getOrderId();
       while(replOrderItemD!=null || iter1.hasNext()) {
         if(replOrderItemD==null) replOrderItemD = (OrderItemData) iter1.next();
         if(replOrderId!=replOrderItemD.getOrderId()) {
           break;
         }
         ReplacedOrderItemViewVector roiVwV = replOrderVw.getItems();
         ReplacedOrderItemView roiVw = ReplacedOrderItemView.createValue();
         roiVwV.add(roiVw);
         roiVw.setOrderItemId(replOrderItemD.getOrderItemId());
         roiVw.setOrderId(replOrderId);
         roiVw.setItemId(replOrderItemD.getItemId());
         roiVw.setItemSkuNum(replOrderItemD.getItemSkuNum());
         roiVw.setCustItemSkuNum(replOrderItemD.getCustItemSkuNum());
         roiVw.setItemShortDesc(replOrderItemD.getItemShortDesc());
		 roiVw.setManuItemSkuNum(replOrderItemD.getManuItemSkuNum());
		 roiVw.setDistItemSkuNum(replOrderItemD.getDistItemSkuNum());
         roiVw.setCustItemShortDesc(replOrderItemD.getCustItemShortDesc());
         roiVw.setQuantity(replOrderItemD.getTotalQuantityOrdered());
         roiVw.setQuantityS(""+replOrderItemD.getTotalQuantityOrdered());
         roiVw.setOrderItemStatusCd(replOrderItemD.getOrderItemStatusCd());
         replOrderItemD = null;
       }
     }
     return replOrderVwV;
   }
   //---------------------------------------------------------------------------
   private boolean calcAllowModifFlag(Connection pConn, OrderData pOrder)
   throws Exception
   {
      boolean allowModifFl = false;
      String orderSource = pOrder.getOrderSourceCd();
      if(RefCodeNames.ORDER_SOURCE_CD.EDI_850.equals(orderSource)){
        int tradingProfileId = pOrder.getIncomingTradingProfileId();
        if(tradingProfileId>0) {
          DBCriteria dbc = new DBCriteria();
          dbc.addEqualTo(TradingProfileConfigDataAccess.INCOMING_TRADING_PROFILE_ID,
                                tradingProfileId);
          dbc.addEqualTo(TradingProfileConfigDataAccess.DIRECTION,"OUT");
          dbc.addEqualTo(TradingProfileConfigDataAccess.SET_TYPE,"855");
          IdVector tpcIdV = TradingProfileConfigDataAccess.selectIdOnly(
             pConn, dbc);
          if(tpcIdV.size()>0) {
            allowModifFl = true;
          }
        }
      } else {
        allowModifFl = true;
      }
     return allowModifFl;
   }

    private OrderStatusDescData setOrderAddresses
        (OrderStatusDescData orderStatusDesc,
      OrderAddressDataVector oadv )
    {

        for (int ia = 0; null != oadv && ia < oadv.size(); ia++)
        {

            OrderAddressData oa = (OrderAddressData)oadv.get(ia);

            if (oa.getAddressTypeCd().equals(
                RefCodeNames.ADDRESS_TYPE_CD.SHIPPING))
            {
                orderStatusDesc.setShipTo(oa);
            }
            else if (oa.getAddressTypeCd().equals(
                RefCodeNames.ADDRESS_TYPE_CD.BILLING))
            {
                orderStatusDesc.setBillTo(oa);
            }
            else if (oa.getAddressTypeCd().equals(
                RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_BILLING))
            {
                orderStatusDesc.setCustomerBillTo(oa);
            }
            else if (oa.getAddressTypeCd().equals(
                RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING))
            {
                orderStatusDesc.setCustomerShipTo(oa);
            }
        }
        return orderStatusDesc;
    }
    /**
     * Get exception order status Desc data according to the orderId
     * @param pOrderId the order Id to search the order status
     * @throws            RemoteException, DataNotFoundException
     */

    public OrderStatusDescData getExceptionOrderDesc(int pOrderId)
    throws RemoteException,
                DataNotFoundException
    {

        OrderStatusDescData orderStatusDesc = null;
        Connection conn = null;
        BusEntityDAO beDAO = mCacheManager.getBusEntityDAO();

        try
        {
            conn = getConnection();

            OrderData orderStatusD = OrderDataAccess.select(conn, pOrderId);
            orderStatusDesc = OrderStatusDescData.createValue();
            orderStatusDesc.setOrderDetail(orderStatusD);

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderItemDataAccess.ORDER_ID, pOrderId);

            OrderItemDataVector orderItemV = OrderItemDataAccess.select(conn,
                                             crit);
            orderStatusDesc.setOrderItemList(orderItemV);

            // set the erpOrderNum
            if (null != orderItemV && 0 < orderItemV.size())
            {
            	initOrderItemActionProperties(orderStatusDesc,orderItemV,conn);
                OrderItemData orderItemD = (OrderItemData)orderItemV.get(0);
                orderStatusDesc.setErpOrderNum(orderItemD.getErpOrderNum());
            }

            // Order ship to and bill to information.
            crit = new DBCriteria();
            crit.addEqualTo(OrderAddressDataAccess.ORDER_ID, pOrderId);

            OrderAddressDataVector oadv = OrderAddressDataAccess.select(conn,
                                          crit);
            orderStatusDesc = setOrderAddresses( orderStatusDesc, oadv );


            // add the accountName
            try{
                BusEntityData accountD = beDAO.getAccountById
                (conn,orderStatusDesc.getAccountId());
                orderStatusDesc.setAccountId(accountD.getBusEntityId());
                orderStatusDesc.setAccountName(accountD.getShortDesc());
            }catch(DataNotFoundException e){
                orderStatusDesc.setAccountId(0);
                orderStatusDesc.setAccountName("Error");
            }

            // add the site address information
            SiteData siteD = getSiteData(conn, orderStatusD);
            orderStatusDesc.setOrderSiteData(siteD);

            // get the invoiceDistList and invoiceCustList
            crit = new DBCriteria();
            crit.addEqualTo(InvoiceDistDataAccess.ORDER_ID,
                            orderStatusD.getOrderId());

            InvoiceDistDataVector invDistV = InvoiceDistDataAccess.select(conn,
                                             crit);
            crit = new DBCriteria();
            crit.addEqualTo(InvoiceCustDataAccess.ORDER_ID,
                            orderStatusD.getOrderId());

            InvoiceCustDataVector invCustV = InvoiceCustDataAccess.select(conn,
                                             crit);

            // get the invoiceCustDetailList
            crit = new DBCriteria();
            crit.addEqualTo(InvoiceCustDataAccess.ORDER_ID,
                            orderStatusD.getOrderId());

            IdVector invCustIdV = InvoiceCustDataAccess.selectIdOnly(conn,
                                  InvoiceCustDataAccess.INVOICE_CUST_ID,
                                  crit);
            crit = new DBCriteria();
            crit.addOneOf(InvoiceCustDetailDataAccess.INVOICE_CUST_ID,
                          invCustIdV);

            InvoiceCustDetailDataVector invCustDetailV = InvoiceCustDetailDataAccess.select(
                        conn, crit);

            // set the exceptionTypeCd
            String exceptionTypeCd = new String("");
            String exceptionInd = orderStatusD.getExceptionInd();
            String orderStatus = orderStatusD.getOrderStatusCd();

            if ("Y".equalsIgnoreCase(exceptionInd) &&
                    RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW.equalsIgnoreCase(
                        orderStatus))
            {
                exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.INBOUND_ORDER + " / ";
            }

            if (RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED.equalsIgnoreCase(
                        orderStatus))
            {
                exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.ORDER_TO_ERP + " / ";
            }

            if (RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED_PO_ERROR.equalsIgnoreCase(
                        orderStatus))
            {
                exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.PO_FROM_ERP + " / ";
            }

            // test if customer acknowledgement exception
            boolean itemAckFailedFlag = false;

            if (null != orderItemV)
            {

                for (int j = 0; j < orderItemV.size(); j++)
                {

                    String ackStatus = ((OrderItemData)orderItemV.get(j)).getAckStatusCd();

                    if (RefCodeNames.ACK_STATUS_CD.CUST_ACK_FAILED.equalsIgnoreCase(
                                ackStatus) ||
                            RefCodeNames.ACK_STATUS_CD.CANCELLED_ACK_FAILED.equalsIgnoreCase(
                                ackStatus))
                    {
                        itemAckFailedFlag = true;

                        break;
                    }
                }
            }

            if (true == itemAckFailedFlag)
            {
                exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.CUSTOMER_ACKNOWLEDGEMENT + " / ";
            }

            // test if PO to vendor exception
            boolean itemPendingReviewFlag = false;

            if (null != orderItemV)
            {

                for (int j = 0; j < orderItemV.size(); j++)
                {

                    String itemStatus = ((OrderItemData)orderItemV.get(j)).getOrderItemStatusCd();

                    if (RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED.equalsIgnoreCase(
                                itemStatus))
                    {
                        itemPendingReviewFlag = true;

                        break;
                    }
                }
            }

            if (true == itemPendingReviewFlag)
            {
                exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.PO_TO_VENDOR + " / ";
            }

            // test if Vendor Invoice exception
            boolean invPendingReviewFlag = false;

            if (null != invDistV)
            {

                for (int j = 0; j < invDistV.size(); j++)
                {

                    String invDistStatus = ((InvoiceDistData)invDistV.get(j)).getInvoiceStatusCd();

                    if (RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW.equalsIgnoreCase(
                                invDistStatus))
                    {
                        invPendingReviewFlag = true;

                        break;
                    }
                }
            }

            if (true == invPendingReviewFlag)
            {
                exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.VENDOR_INVOICE + " / ";
            }

            // test if Invoice to ERP exception
            boolean invRejectedFlag = false;

            if (null != invDistV)
            {

                for (int j = 0; j < invDistV.size(); j++)
                {

                    String invDistStatus = ((InvoiceDistData)invDistV.get(j)).getInvoiceStatusCd();

                    if (RefCodeNames.INVOICE_STATUS_CD.ERP_REJECTED.equalsIgnoreCase(
                                invDistStatus))
                    {
                        invRejectedFlag = true;

                        break;
                    }
                }
            }

            if (true == invRejectedFlag)
            {
                exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.INVOICE_TO_ERP + " / ";
            }

            // test if invoice Erpexception
            boolean invErpFailedFlag = false;

            if (null != invCustV)
            {

                for (int j = 0; j < invCustV.size(); j++)
                {

                    String invCustStatus = ((InvoiceCustData)invCustV.get(j)).getInvoiceStatusCd();

                    if (RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED_ERROR.equalsIgnoreCase(
                                invCustStatus) ||
                            RefCodeNames.INVOICE_STATUS_CD.ERP_GENERATED_ERROR.equalsIgnoreCase(
                                invCustStatus))
                    {
                        invErpFailedFlag = true;

                        break;
                    }
                }
            }

            if (true == invErpFailedFlag)
            {
                exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.INVOICE_FROM_ERP + " / ";
            }

            // test if cust ship notice exception
            boolean invShipNotifFlag = false;

            if (null != invCustDetailV)
            {

                for (int j = 0; j < invCustDetailV.size(); j++)
                {

                    String shipStatus = ((InvoiceCustDetailData)invCustDetailV.get(
                                             j)).getShipStatusCd();

                    if (RefCodeNames.SHIP_STATUS_CD.FAILED.equalsIgnoreCase(
                                shipStatus))
                    {
                        invShipNotifFlag = true;

                        break;
                    }
                }
            }

            if (true == invShipNotifFlag)
            {
                exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.CUSTOMER_SHIP_NOTICE + " / ";
            }

            // test if cust invoice exception
            boolean invFailedFlag = false;

            if (null != invCustV)
            {

                for (int j = 0; j < invCustV.size(); j++)
                {

                    String invCustStatus = ((InvoiceCustData)invCustV.get(j)).getInvoiceStatusCd();

                    if (RefCodeNames.INVOICE_STATUS_CD.CUST_INVOICED_FAILED.equalsIgnoreCase(
                                invCustStatus))
                    {
                        invFailedFlag = true;

                        break;
                    }
                }
            }

            if (true == invFailedFlag)
            {
                exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.CUSTOMER_INVOICE + " / ";
            }

            // test if CIT invoice exception
            boolean citInvFailedFlag = false;

            if (null != invCustV)
            {

                for (int j = 0; j < invCustV.size(); j++)
                {

                    String citStatus = ((InvoiceCustData)invCustV.get(j)).getCitStatusCd();

                    if (RefCodeNames.CIT_STATUS_CD.FAILED.equalsIgnoreCase(
                                citStatus))
                    {
                        citInvFailedFlag = true;

                        break;
                    }
                }
            }

            if (true == citInvFailedFlag)
            {
                exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.CIT_INVOICE + " / ";
            }

            if (3 < exceptionTypeCd.length())
            {
                orderStatusDesc.setExceptionTypeCd(exceptionTypeCd.substring(0,
                                                   exceptionTypeCd.length() - 3));
            }

            // get the shipFrom name
            String shipFromName = new String("");
            ArrayList shipFromNameList = new ArrayList();

            if (null != invDistV && 0 < invDistV.size())
            {

                for (int j = 0; j < invDistV.size(); j++)
                {

                    String fromName = ((InvoiceDistData)invDistV.get(j)).getShipFromName();

                    if (null == fromName)
                    {
                        fromName = new String("");
                    }

                    if (0 == j)
                    {
                        shipFromNameList.add(fromName);
                    }
                    else
                    {

                        boolean matchFlag = false;

                        for (int k = 0; k < shipFromNameList.size(); k++)
                        {

                            if (fromName.equals(
                                        (String)shipFromNameList.get(k)))
                            {
                                matchFlag = true;

                                break;
                            }
                        }

                        if (false == matchFlag)
                        {
                            shipFromNameList.add(fromName);
                        }
                    }
                }
            }

            if (null != shipFromNameList && 0 < shipFromNameList.size())
            {

                for (int j = 0; j < shipFromNameList.size(); j++)
                {
                    shipFromName += (String)shipFromNameList.get(j) + " / ";
                }
            }

            if (3 < shipFromName.length())
            {
                orderStatusDesc.setShipFromName(shipFromName.substring(0,
                                                shipFromName.length() - 3));
            }

            // Order reference number information.
            OrderPropertyDataVector refNumV = lookupOrderRefNumber(conn, pOrderId);
            if (null != refNumV)
            {
                orderStatusDesc.setReferenceNumList(refNumV);
            }

            //Get replaced orders info
            if(RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.
                                         equals(orderStatusD.getOrderTypeCd())){
              ReplacedOrderViewVector replOrderVwV = getReplacedOrders(conn,pOrderId);
              orderStatusDesc.setReplacedOrders(replOrderVwV);
            }

      // Set any order history info.
      orderStatusDesc.setShoppingHistory
    (ShoppingDAO.getOrderHistory(conn, pOrderId));

        }
        catch (DataNotFoundException e)
        {

            //throw e;
        }
        catch (Exception e)
        {
            throw new RemoteException("getOrderStatusDesc: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return orderStatusDesc;
    }

    /**
     * Get order item data vector according to the orderId
     * @param pOrderId the order Id to search the order item
     * @return an <code>OrderItemDataVector</code> value
     * @exception            RemoteException
     */
    public OrderItemDataVector getOrderItemCollection(int pOrderId)
    throws RemoteException
    {

        OrderItemDataVector orderItemV = null;
        Connection conn = null;

        try
        {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderItemDataAccess.ORDER_ID, pOrderId);
            orderItemV = OrderItemDataAccess.select(conn, crit);
        }
        catch (Exception e)
        {
            throw new RemoteException("getOrderItemCollection: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return orderItemV;
    }

    /**
     * Get order item data vector according to the orderId and erpPoNum
     * @param pOrderId the order Id to search the order item
     * @param pErpPoNum the erpPo to search the order item
     * @return an <code>OrderItemDataVector</code> value
     * @exception            RemoteException
     */
    public OrderItemDataVector getOrderItemCollection(int pOrderId,
            String pErpPoNum)
    throws RemoteException
    {

        OrderItemDataVector orderItemV = null;
        Connection conn = null;

        try
        {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderItemDataAccess.ORDER_ID, pOrderId);

            if (null != pErpPoNum && !"".equals(pErpPoNum.trim()))
            {
                crit.addEqualTo(OrderItemDataAccess.ERP_PO_NUM, pErpPoNum);
            }

            orderItemV = OrderItemDataAccess.select(conn, crit);
        }
        catch (Exception e)
        {
            throw new RemoteException("getOrderItemCollection2: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return orderItemV;
    }


    /**
     * Get order item data vector according to the orderItemIds
     * @param pOrderItemIds the order item Ids
     * @return an <code>OrderItemDataVector</code> value
     * @exception            RemoteException
     */
    public OrderItemDataVector getOrderItemCollection(IdVector pOrderItemIds)
        throws RemoteException
        {

        OrderItemDataVector orderItemV = null;
        Connection conn = null;

        try{
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addOneOf(OrderItemDataAccess.ORDER_ITEM_ID, pOrderItemIds);


            orderItemV = OrderItemDataAccess.select(conn, crit);
        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }

        return orderItemV;
    }

    /**
     * Get order item status data according to the orderItemId
     * @param pOrderItemId the order item Id to search the order item
     * @return an <code>OrderItemData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderItemData getOrderItem(int pOrderItemId)
    throws RemoteException, DataNotFoundException
    {

        OrderItemData orderItemD = null;
        Connection conn = null;

        try
        {
            conn = getConnection();
            orderItemD = OrderItemDataAccess.select(conn, pOrderItemId);
        }
        catch (DataNotFoundException e)
        {

            //throw e;
        }
        catch (Exception e)
        {
            throw new RemoteException("getOrderItem: " + e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return orderItemD;
    }

    /**
    * Get order item desc data vector according to the orderId
    * @param pOrderId the order Id to search the order item
    * @param pErpPoNum the erpPoNum to search the order item
    * @return an <code>OrderItemDescDataVector</code> value
    * @exception            RemoteException
    */
    public OrderItemDescDataVector getOrderItemDescCollection(int pOrderId,String pErpPoNum)
    throws RemoteException
    {
        if (pOrderId == 0){
            return new OrderItemDescDataVector();
        }

        return getOrderItemDescCollection(pOrderId, pErpPoNum, 0);
    }

    /**Get order item desc data vector according to the orderId
     * @param pOrderId the order Id to search the order item
     * @param pErpPoNum the erpPoNum to search the order item
     * @return an <code>OrderItemDescDataVector</code> value
     * @exception            RemoteException
     */
    public OrderItemDescDataVector getOrderItemDescCollection(int pOrderId,
            String pErpPoNum,
            int pPurchaseOrderId)
    throws RemoteException
    {
        Connection conn = null;
        try{
            conn = getConnection();
            return OrderDAO.getOrderItemDescCollection(conn, pOrderId, pErpPoNum, pPurchaseOrderId);
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**Get order item desc data vector according to the orderId
     * @param pOrderId the order Id to search the order item
     * @return an <code>OrderItemDescDataVector</code> value
     * @exception            RemoteException
     */
    public OrderItemDescDataVector getOrderItemDescCollection(int pOrderId)
    throws RemoteException
    {

        OrderItemDescDataVector orderItemDescV = null;
        Connection conn = null;
        try{
            conn = getConnection();
            orderItemDescV = OrderDAO.getOrderItemDescCollection(conn, pOrderId, null, 0);
        }catch (Exception e){
            throw processException(e);
        }finally
        {
            closeConnection(conn);
        }

        return orderItemDescV;
    }



    /**
     *Sets the properties of the OrderStatusDescData that are based on the order item action records
     */
    private void initOrderItemActionProperties(OrderStatusDescData orderStatusDesc, OrderItemDataVector oItems, Connection con)
    throws SQLException{
    	log.info("initOrderItemActionProperties. for order id: "+orderStatusDesc.getOrderDetail().getOrderId());
    	boolean shipped = true;
    	int ackOnHold = 0; //-1 =
        Iterator it = oItems.iterator();
        while(it.hasNext()){
            OrderItemData itm = (OrderItemData) it.next();
            if(Utility.isGoodOrderItemStatus(itm.getOrderItemStatusCd())){
            	OrderItemActionData onHoldAction=null;
                int totalActionQty = 0;
                DBCriteria crit = new DBCriteria();
                crit.addEqualTo(OrderItemActionDataAccess.ORDER_ITEM_ID,itm.getOrderItemId());
                //crit.addOrderBy(OrderItemActionDataAccess.ACTION_DATE,true); //order by is used in logic later on
                //crit.addOrderBy(OrderItemActionDataAccess.ACTION_TIME,true); //order by is used in logic later on
                OrderItemActionDataVector actions = OrderItemActionDataAccess.select(con, crit);
                Collections.sort(actions,ORDER_ITEM_ACTION_ACK_ON_HOLD_COMP);

                Iterator actIt = actions.iterator();
                while(actIt.hasNext()){
                	OrderItemActionData act = (OrderItemActionData) actIt.next();
                	log.debug("Looping through order item actions for action: "+act);
                	log.debug(act.getActionDate());
                	log.debug(act.getActionTime());
                	log.debug(act.getActionCd());
                	if(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_SHIPPED.equals(act.getActionCd())){
                		totalActionQty += act.getQuantity();
                    }else if(!actIt.hasNext()){
                    	if(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACK_ON_HOLD.equals(act.getActionCd())){
                    		//if the last action code is
                    		if(ackOnHold != -1){
                    			ackOnHold = 1;
                    			onHoldAction = act;
                    		}
                    	}else{
                    		if(onHoldAction == null){
                    			ackOnHold = -1;
                    		}else{
	                    		Date a1T = act.getActionTime();
	                    		Date a2T = onHoldAction.getActionTime();
	                    		Date a1D = act.getActionDate();
	                    		Date a2D = onHoldAction.getActionDate();
	                    		if(a1D != null && a2D != null && a2T != null && a2D != null){
	                    			if(a1D.compareTo(a2D) != 0 || a1T.compareTo(a2T) != 0){
	                    				ackOnHold = -1;
	                    			}
	                    		}else if(a1D != null && a2D != null){
	                    			if(a1D.compareTo(a2D) != 0){
	                    				ackOnHold = -1;
	                    			}
	                    		}else{
	                    			ackOnHold = -1;
	                    		}
                    		}
                    	}
                    }
                }

                if(totalActionQty < itm.getTotalQuantityOrdered()){
                    //no need to continue, if one is undershipped the order is undershipped
                	shipped = false;
                }
            }
        }
        orderStatusDesc.setShipped(shipped);
        orderStatusDesc.setAckOnHold(ackOnHold == 1);
    }


    /**
     * Sorts the order item action list with the  ACK_ON_HOLD status first then in reverse order after that.  Note the
     * param reversal of 01 and 02.
     */
    private static final Comparator ORDER_ITEM_ACTION_ACK_ON_HOLD_COMP = new Comparator() {
        public int compare(Object o2, Object o1)//note the reversal of 01 and o2, this will effectivly sort this as a reversed order
        {
            Date d1 = ((OrderItemActionData)o2).getActionDate();//note reversal, sorts backwards
            Date d2 = ((OrderItemActionData)o1).getActionDate();//note reversal, sorts backwards
            if(d1 == null){d1 = ((OrderItemActionData)o2).getAddDate();}//note reversal, sorts backwards
            if(d2 == null){d2 = ((OrderItemActionData)o1).getAddDate();}//note reversal, sorts backwards
            if((d1 == null && d2 == null) || d1.compareTo(d2) == 0){
            	d1 = ((OrderItemActionData)o2).getActionTime();//note reversal, sorts backwards
                d2 = ((OrderItemActionData)o1).getActionTime();//note reversal, sorts backwards
                if((d1 == null || d2 == null) || d1.compareTo(d2) == 0){ //note or as sometime time is null
                	d1 = ((OrderItemActionData)o2).getAddDate();//note reversal, sorts backwards
                    d2 = ((OrderItemActionData)o1).getAddDate();//note reversal, sorts backwards
                    if((d1 == null && d2 == null) || d1.compareTo(d2) == 0){
	                	String s1 = ((OrderItemActionData)o2).getActionCd();//note reversal , sorts backwards
	                    String s2 = ((OrderItemActionData)o1).getActionCd();//note reversal, sorts backwards
	                    if(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACK_ON_HOLD.equals(s1)){
	                    	return 1;
	                    }
	                    if(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACK_ON_HOLD.equals(s2)){
	                    	return -1;
	                    }
	                    if(s1==null){
	                    	return 1;
	                    }
	                    if(s2==null){
	                    	return -1;
	                    }
	                    return s1.compareTo(s2);
                    }else{
                    	return d1.compareTo(d2);
                    }
                }else{
                	return d1.compareTo(d2);
                }
            }else{
            	return d1.compareTo(d2);
            }
        }
    };


    /**
     * Get order status desc data vector according to the orderStatusCriteriaData
     *  be changed to be more friendly and take into account the order item actions.
     * @return an <code>OrderStatusDescDataVector</code> value
     * @exception            RemoteException
     */

    private UserData getCachedUserByName(Connection conn,
           OrderData orderStatusD )
        throws Exception    {
  return mCacheManager.getCachedUserForOrder( conn,orderStatusD );

    }

    CacheManager mCacheManager = new CacheManager("OrderBean ");

    public OrderStatusDescDataVector getOrderStatusDescCollection(
            OrderStatusCriteriaData pOrderStatusCriteria, IdVector pStoreIds)
            throws RemoteException {
        pOrderStatusCriteria.setStoreIdVector(pStoreIds);
        return getOrderStatusDescCollection_OLD(pOrderStatusCriteria);
    }

    public OrderStatusDescDataVector getOrderStatusDescCollection_OLD(OrderStatusCriteriaData pOrderStatusCriteria)
        throws RemoteException
    {

        OrderStatusDescDataVector orderStatusDescV = new OrderStatusDescDataVector();
        Connection conn = null;

        BusEntityDAO beDAO = mCacheManager.getBusEntityDAO();
        try
        {
            OrderDataVector orderStatusV = getOrderStatusCollection(pOrderStatusCriteria);

            if (null == orderStatusV ) {
                return orderStatusDescV;
            }

            conn = getConnection();

            for (int i = 0; i < orderStatusV.size(); i++) {
                OrderData orderStatusD = (OrderData)orderStatusV.get(i);
                OrderStatusDescData orderStatusDescD = OrderStatusDescData.createValue();
                orderStatusDescD.setOrderDetail(orderStatusD);

			    // Set any order history info.
			    orderStatusDescD.setShoppingHistory
			        (ShoppingDAO.getOrderHistory(conn, orderStatusD.getOrderId() ));

                try{
                    orderStatusDescD.setPlacedBy( getCachedUserByName(conn, orderStatusD) );
                }
                catch(DataNotFoundException e){
                    //user who placed order no longer exists or
                    // the order was system generated order
                }

                //get the order item data
                DBCriteria crit = new DBCriteria();
                crit.addEqualTo(OrderItemDataAccess.ORDER_ID,orderStatusD.getOrderId());
                OrderItemDataVector orderItemV = OrderItemDataAccess.select(conn, crit);

                //figure out if all the items have been shipped according to the order
                //item actions
                initOrderItemActionProperties(orderStatusDescD,orderItemV,conn);

                orderStatusDescD.setAllowModifFl(calcAllowModifFlag(conn, orderStatusD));
                orderStatusDescD.setOrderItemList(orderItemV);
                if(RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(orderStatusD.getOrderStatusCd())) {
                    OrderData consolidatedOrderD = getConsolidatedOrderForReplaced(conn, orderStatusD.getOrderId());
                    orderStatusDescD.setConsolidatedOrder(consolidatedOrderD);
                }
                // set the erpOrderNum
                if (null != orderItemV && 0 < orderItemV.size()) {
                    OrderItemData orderItemD = (OrderItemData)orderItemV.get(0);
                    orderStatusDescD.setErpOrderNum(orderItemD.getErpOrderNum());
                }

                // Order ship to and bill to information.
                crit = new DBCriteria();
                crit.addEqualTo(OrderAddressDataAccess.ORDER_ID, orderStatusD.getOrderId());
                OrderAddressDataVector oadv = OrderAddressDataAccess.select(conn, crit);
                for (int ia = 0; null != oadv && ia < oadv.size(); ia++) {
                    OrderAddressData oa = (OrderAddressData)oadv.get(ia);
                    if (oa.getAddressTypeCd().equals(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING)) {
                        orderStatusDescD.setShipTo(oa);
                    }
                    else if (oa.getAddressTypeCd().equals(RefCodeNames.ADDRESS_TYPE_CD.BILLING)) {
                        orderStatusDescD.setBillTo(oa);
                    }
                }

                // add the account name
                try {
                    BusEntityData accountD = beDAO.getAccountById(conn,orderStatusDescD.getAccountId());
                    orderStatusDescD.setAccountId(accountD.getBusEntityId());
                    orderStatusDescD.setAccountName(accountD.getShortDesc());
                }
                catch(DataNotFoundException e) {
                    orderStatusDescD.setAccountId(0);
                    orderStatusDescD.setAccountName("Error");
                }

                // get the dist name
                OrderItemDataVector distOrderItemV = new OrderItemDataVector();
                for (int j = 0; null != orderItemV && j < orderItemV.size(); j++) {
                    OrderItemData itemD = (OrderItemData)orderItemV.get(j);
                    String distErpNum = itemD.getDistErpNum();
                    if (null != distErpNum && !"".equals(distErpNum)) {
                        distOrderItemV.add(itemD);
                    }
                }

                String distName = new String("");
                ArrayList distErpList = new ArrayList();
                for (int j = 0; null != distOrderItemV && j < distOrderItemV.size(); j++) {
                    String distErpNum = ((OrderItemData)distOrderItemV.get(j)).getDistErpNum();
                    if (0 == j) {
                        distErpList.add(distErpNum);
                    }
                    else
                    {
                        boolean matchFlag = false;
                        for (int k = 0; k < distErpList.size(); k++) {
                            if (distErpNum.equals((String)distErpList.get(k))) {
                                matchFlag = true;
                                break;
                            }
                        }
                        if (false == matchFlag) {
                            distErpList.add(distErpNum);
                        }
                    }
                }

                for (int j = 0; null != distErpList && j < distErpList.size(); j++) {
                    String thisDistErpNum = (String)distErpList.get(j);
                    BusEntityData distBE = beDAO.getDistributorByErpNum(conn, thisDistErpNum);
                    distName += distBE.getShortDesc() + " / ";
                }

                if (3 < distName.length()) {
                    orderStatusDescD.setDistName(distName.substring(0,
                    distName.length() - 3));
                }

                crit = new DBCriteria();
                crit.addEqualTo(OrderMetaDataAccess.ORDER_ID, orderStatusD.getOrderId());
                OrderMetaDataVector metaV = OrderMetaDataAccess.select(conn, crit);
                orderStatusDescD.setOrderMetaData(metaV);

                //get any order properties that are necessary
                crit = new DBCriteria();
                ArrayList filter = new ArrayList();
                filter.add(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED);
                crit.addOneOf(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,filter);
                crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD,RefCodeNames.SIMPLE_STATUS_CD.ACTIVE);
                crit.addEqualTo(OrderPropertyDataAccess.ORDER_ID,orderStatusD.getOrderId());

                OrderPropertyDataVector opdv = OrderPropertyDataAccess.select(conn, crit);
                Iterator it = opdv.iterator();
                while(it.hasNext()) {
                	OrderPropertyData opd = (OrderPropertyData) it.next();
                	if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED.equals(opd.getOrderPropertyTypeCd())){
                		orderStatusDescD.setReceived(Utility.isTrue(opd.getValue()));
                	}
                }

                crit = new DBCriteria();
                crit.addEqualTo(OrderPropertyDataAccess.SHORT_DESC,"Inventory Item Check");
                crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD,RefCodeNames.SIMPLE_STATUS_CD.ACTIVE);
                crit.addEqualTo(OrderPropertyDataAccess.ORDER_ID,orderStatusD.getOrderId());
                crit.addJoinCondition(OrderPropertyDataAccess.ORDER_ID,OrderDataAccess.CLW_ORDER,OrderDataAccess.ORDER_ID);
                crit.addJoinTableEqualTo(OrderDataAccess.CLW_ORDER,OrderDataAccess.ORDER_SOURCE_CD,RefCodeNames.ORDER_SOURCE_CD.INVENTORY);

                opdv = OrderPropertyDataAccess.select(conn, crit);
                orderStatusDescD.setAutoOrder(opdv.size() > 0);

                orderStatusDescV.add(orderStatusDescD);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RemoteException("getOrderStatusDescCollection: " + e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return orderStatusDescV;
    }
    
    public OrderStatusDescDataVector getOrderStatusDescCollection(
            OrderStatusCriteriaData pOrderStatusCriteria) throws RemoteException {

        OrderStatusDescDataVector returnValue = new OrderStatusDescDataVector();
        Connection conn = null;

        try
        {
        	//retrieve the orders matching the criteria.  If no orders are found, return.
            OrderDataVector orders = getOrderStatusCollection(pOrderStatusCriteria);
            if (!Utility.isSet(orders)) {
                return returnValue;
            }
            
            //create a list of order ids and account ids, which will be used to retrieve 
            //additional information about each order
            Set<Integer> orderIdSet = new HashSet<Integer>();
            Set<Integer> accountIdSet = new HashSet<Integer>();
            Iterator<OrderData> orderIterator = orders.iterator();
            while (orderIterator.hasNext()) {
            	OrderData order = orderIterator.next();
            	orderIdSet.add(order.getOrderId());
            	accountIdSet.add(order.getAccountId());
            }
            IdVector orderIds = new IdVector();
            IdVector accountIds = new IdVector();
            orderIds.addAll(orderIdSet);
            accountIds.addAll(accountIdSet);

            conn = getConnection();
            
            //retrieve order history info
            Map<Integer, List<ShoppingInfoData>> orderIdToShoppingHistory;
            if (pOrderStatusCriteria.isRetrieveOrderHistory()) {
            	orderIdToShoppingHistory = ShoppingDAO.getOrderHistory(conn, orderIds);
            }
            else {
            	orderIdToShoppingHistory = new HashMap<Integer, List<ShoppingInfoData>>();
            }
            
            //retrieve order item data
            Map<Integer, OrderItemDataVector> orderIdToOrderItems = new HashMap<Integer,OrderItemDataVector>();
            if (pOrderStatusCriteria.isRetrieveOrderItems()) {
	            DBCriteria crit = new DBCriteria();
	            crit.addOneOf(OrderItemDataAccess.ORDER_ID, orderIds);
	            crit.addOrderBy(OrderItemDataAccess.ORDER_ID);
	            OrderItemDataVector orderItems = OrderItemDataAccess.select(conn, crit);
	            Iterator<OrderItemData> orderItemIterator = orderItems.iterator();
	            while (orderItemIterator.hasNext()) {
	            	OrderItemData orderItem = orderItemIterator.next();
	            	int orderId = orderItem.getOrderId();
	            	OrderItemDataVector orderItemList = orderIdToOrderItems.get(orderId);
	            	if (!Utility.isSet(orderItemList)) {
	            		orderItemList = new OrderItemDataVector();
	            		orderIdToOrderItems.put(orderId, orderItemList);
	            	}
	            	orderItemList.add(orderItem);
	            }
            }
	            
            // retrieve order ship to and bill to information.
            Map<Integer, OrderAddressData> orderIdToShippingAddress = new HashMap<Integer, OrderAddressData>();
            Map<Integer, OrderAddressData> orderIdToBillingAddress = new HashMap<Integer, OrderAddressData>();
            if (pOrderStatusCriteria.isRetrieveOrderAddresses()) {
            	DBCriteria crit = new DBCriteria();
	            crit.addOneOf(OrderAddressDataAccess.ORDER_ID, orderIds);
	            crit.addOrderBy(OrderAddressDataAccess.ORDER_ID);
	            OrderAddressDataVector orderAddresses = OrderAddressDataAccess.select(conn, crit);
	            Iterator<OrderAddressData> orderAddressIterator = orderAddresses.iterator();
	            while (orderAddressIterator.hasNext()) {
	            	OrderAddressData orderAddress = orderAddressIterator.next();
	            	int orderId = orderAddress.getOrderId();
	            	String addressType = orderAddress.getAddressTypeCd();
	            	if (RefCodeNames.ADDRESS_TYPE_CD.SHIPPING.equals(addressType)) {
	            		orderIdToShippingAddress.put(orderId, orderAddress);
	            	}
	            	else if (RefCodeNames.ADDRESS_TYPE_CD.BILLING.equals(addressType)) {
	            		orderIdToBillingAddress.put(orderId, orderAddress);
	            	}
	            }
            }
            
            //retrieve account information
            Map<Integer, BusEntityData> accountIdToAccount = new HashMap<Integer, BusEntityData>();
            if (pOrderStatusCriteria.isRetrieveOrderAccount()) {
	            BusEntityDataVector accounts = mCacheManager.getBusEntityDAO().getAccountsById(conn, accountIds);
	            if (Utility.isSet(accounts)) {
	            	Iterator<BusEntityData> accountIterator = accounts.iterator();
	            	while (accountIterator.hasNext()) {
	            		BusEntityData account = accountIterator.next();
	            		accountIdToAccount.put(account.getBusEntityId(), account);
	            	}
	            }
            }
            
            //retrieve order meta data information
            Map<Integer, OrderMetaDataVector> orderIdToOrderMetaData = new HashMap<Integer,OrderMetaDataVector>();
            if (pOrderStatusCriteria.isRetrieveOrderMetaData()) {
	            DBCriteria crit = new DBCriteria();
	            crit.addOneOf(OrderMetaDataAccess.ORDER_ID, orderIds);
	            crit.addOrderBy(OrderMetaDataAccess.ORDER_ID);
	            OrderMetaDataVector orderMetaDatas = OrderMetaDataAccess.select(conn, crit);
	            Iterator<OrderMetaData> orderMetaDataIterator = orderMetaDatas.iterator();
	            while (orderMetaDataIterator.hasNext()) {
	            	OrderMetaData orderMetaData = orderMetaDataIterator.next();
	            	int orderId = orderMetaData.getOrderId();
	            	OrderMetaDataVector orderMetaDataList = orderIdToOrderMetaData.get(orderId);
	            	if (!Utility.isSet(orderMetaDataList)) {
	            		orderMetaDataList = new OrderMetaDataVector();
	            		orderIdToOrderMetaData.put(orderId, orderMetaDataList);
	            	}
	            	orderMetaDataList.add(orderMetaData);
	            }
            }
            
            //retrieve order reception information
            Map<Integer,Boolean> orderIdToOrderReception = new HashMap<Integer,Boolean>();
            if (pOrderStatusCriteria.isRetrieveOrderReceptionData()) {
	            DBCriteria crit = new DBCriteria();
	            crit.addOneOf(OrderPropertyDataAccess.ORDER_ID, orderIds);
	            crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
	            		RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED);
	            crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD,
	            		RefCodeNames.SIMPLE_STATUS_CD.ACTIVE);
	            OrderPropertyDataVector orderPropertyList = OrderPropertyDataAccess.select(conn, crit);
	            Iterator<OrderPropertyData> orderPropertyIterator = orderPropertyList.iterator();
	            while(orderPropertyIterator.hasNext()) {
	            	OrderPropertyData orderProperty = (OrderPropertyData) orderPropertyIterator.next();
	            	int orderId = orderProperty.getOrderId();
	            	orderIdToOrderReception.put(orderId, Utility.isTrue(orderProperty.getValue()));
	            }
            }

            //retrieve auto order information
            Map<Integer,Boolean> orderIdToAutoOrder = new HashMap<Integer,Boolean>();
            if (pOrderStatusCriteria.isRetrieveOrderAutoOrderData()) {
	            DBCriteria crit = new DBCriteria();
	            crit.addOneOf(OrderPropertyDataAccess.ORDER_ID, orderIds);
	            crit.addEqualTo(OrderPropertyDataAccess.SHORT_DESC, "Inventory Item Check");
	            crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD,
	            		RefCodeNames.SIMPLE_STATUS_CD.ACTIVE);
	            crit.addJoinCondition(OrderPropertyDataAccess.ORDER_ID, OrderDataAccess.CLW_ORDER,
	            		OrderDataAccess.ORDER_ID);
	            crit.addJoinTableEqualTo(OrderDataAccess.CLW_ORDER, OrderDataAccess.ORDER_SOURCE_CD,
	            		RefCodeNames.ORDER_SOURCE_CD.INVENTORY);
	            OrderPropertyDataVector orderPropertyList = OrderPropertyDataAccess.select(conn, crit);
	            Iterator<OrderPropertyData> orderPropertyIterator = orderPropertyList.iterator();
	            while(orderPropertyIterator.hasNext()) {
	            	OrderPropertyData orderProperty = (OrderPropertyData) orderPropertyIterator.next();
	            	int orderId = orderProperty.getOrderId();
	            	orderIdToAutoOrder.put(orderId, true);
	            }
            }
            
            //retrieve order property information
            Map<Integer, OrderPropertyDataVector> orderIdToOrderProperties = new HashMap<Integer,OrderPropertyDataVector>();
            if (pOrderStatusCriteria.isRetrieveOrderProperties()) {
	            DBCriteria crit = new DBCriteria();
	            crit.addOneOf(OrderPropertyDataAccess.ORDER_ID, orderIds);
	            crit.addOrderBy(OrderPropertyDataAccess.ORDER_ID);
	            if (Utility.isSet(pOrderStatusCriteria.getOrderPropertyList())) {
	                crit.addOneOf(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
	                		pOrderStatusCriteria.getOrderPropertyList());
	                crit.addOrderBy(OrderPropertyDataAccess.ORDER_PROPERTY_ID, false);
	            }
	            else {
	                crit.addEqualToIgnoreCase(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
	                    RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
	             }
	            OrderPropertyDataVector orderPropertyList = OrderPropertyDataAccess.select(conn, crit);
	            Iterator<OrderPropertyData> orderPropertyIterator = orderPropertyList.iterator();
	            while (orderPropertyIterator.hasNext()) {
	            	OrderPropertyData orderProperty = orderPropertyIterator.next();
	            	int orderId = orderProperty.getOrderId();
	            	OrderPropertyDataVector orderPropertyDataList = orderIdToOrderProperties.get(orderId);
	            	if (!Utility.isSet(orderPropertyDataList)) {
	            		orderPropertyDataList = new OrderPropertyDataVector();
	            		orderIdToOrderProperties.put(orderId, orderPropertyDataList);
	            	}
	            	orderPropertyDataList.add(orderProperty);
	            }
            }

            
            //populate the return value
            for (int i=0; i<orders.size(); i++) {
                OrderStatusDescData orderStatusDesc = OrderStatusDescData.createValue();
                
                //set the order information
                OrderData order = (OrderData)orders.get(i);
                int orderId = order.getOrderId();
                orderStatusDesc.setOrderDetail(order);
                
                //set the shopping history
                orderStatusDesc.setShoppingHistory(orderIdToShoppingHistory.get(orderId));
                
                //set the placed by information
                try{
                    orderStatusDesc.setPlacedBy(getCachedUserByName(conn, order));
                }
                catch (DataNotFoundException e) {
                    //user who placed order no longer exists or the order was system generated order
                }
                
                //set the order item information
                OrderItemDataVector items = orderIdToOrderItems.get(orderId);
                orderStatusDesc.setOrderItemList(items);
                if (pOrderStatusCriteria.isRetrieveOrderItems()) {
                	//figure out if all the items have been shipped according to the order item actions
                	initOrderItemActionProperties(orderStatusDesc, items, conn);
                }

                //set the erpOrderNum and distributor name
                if (Utility.isSet(items)) {
                	orderStatusDesc.setErpOrderNum(((OrderItemData)items.get(0)).getErpOrderNum());
                	String distributorName = determineDistributorName(items, mCacheManager.getBusEntityDAO(), conn);
                	orderStatusDesc.setDistName(distributorName);
                }
                
                //set the flag to indicate if the order can be modified
                orderStatusDesc.setAllowModifFl(calcAllowModifFlag(conn, order));
                
                //set the consolidated order information
                if (RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(order.getOrderStatusCd())) {
                    OrderData consolidatedOrderD = getConsolidatedOrderForReplaced(conn, order.getOrderId());
                    orderStatusDesc.setConsolidatedOrder(consolidatedOrderD);
                }
                
                //set the order ship to and bill to information.
                orderStatusDesc.setShipTo(orderIdToShippingAddress.get(orderId));
                orderStatusDesc.setBillTo(orderIdToBillingAddress.get(orderId));
                
                //set the account information
                BusEntityData account = accountIdToAccount.get(orderStatusDesc.getAccountId());
                if (account != null) {
                    orderStatusDesc.setAccountId(account.getBusEntityId());
                    orderStatusDesc.setAccountName(account.getShortDesc());
                }

                //set the meta data information
                orderStatusDesc.setOrderMetaData(orderIdToOrderMetaData.get(orderId));

                //set the order reception value
                Boolean isOrderReceived = orderIdToOrderReception.get(orderId);
                if (isOrderReceived != null) {
                	orderStatusDesc.setReceived(isOrderReceived);
                }
                else {
                	orderStatusDesc.setReceived(false);
                }

                //set the auto order value
                Boolean isOrderAutoOrder = orderIdToOrderReception.get(orderId);
                if (isOrderAutoOrder != null) {
                	orderStatusDesc.setAutoOrder(isOrderAutoOrder);
                }
                else {
                	orderStatusDesc.setAutoOrder(false);
                }
                
                //set the order property information
            	orderStatusDesc.setOrderPropertyList(orderIdToOrderProperties.get(orderId));
                
                //add the end result to the return value
                returnValue.add(orderStatusDesc);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RemoteException("getOrderStatusDescCollection: " + e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return returnValue;
    }
    
    private String determineDistributorName(OrderItemDataVector items, BusEntityDAO beDAO, 
    		Connection conn) throws Exception {
    	String returnValue = null;
    	
        OrderItemDataVector distOrderItems = new OrderItemDataVector();
        if (Utility.isSet(items)) {
        	Iterator<OrderItemData> itemIterator = items.iterator();
        	while (itemIterator.hasNext()) {
                OrderItemData item = itemIterator.next();
                String distErpNum = item.getDistErpNum();
                if (Utility.isSet(distErpNum)) {
                    distOrderItems.add(item);
                }
        	}
        }

        ArrayList distErpList = new ArrayList();
        for (int j = 0; null != distOrderItems && j < distOrderItems.size(); j++) {
            String distErpNum = ((OrderItemData)distOrderItems.get(j)).getDistErpNum();
            if (0 == j) {
                distErpList.add(distErpNum);
            }
            else
            {
                boolean matchFlag = false;
                for (int k = 0; k < distErpList.size(); k++) {
                    if (distErpNum.equals((String)distErpList.get(k))) {
                        matchFlag = true;
                        break;
                    }
                }
                if (false == matchFlag) {
                    distErpList.add(distErpNum);
                }
            }
        }

        String distName = new String("");
        for (int j = 0; null != distErpList && j < distErpList.size(); j++) {
            String thisDistErpNum = (String)distErpList.get(j);
            	BusEntityData distBE = beDAO.getDistributorByErpNum(conn, thisDistErpNum);
            distName += distBE.getShortDesc() + " / ";
        }

        if (3 < distName.length()) {
            returnValue = distName.substring(0, distName.length() - 3);
        }
    	return returnValue;
    }

    /*
     * Return a light weight object
     */
    public OrderStatusDescDataVector getLightOrderStatusDescCollection(OrderStatusCriteriaData pOrderStatusCriteria)
    throws RemoteException
{

    OrderStatusDescDataVector orderStatusDescV = new OrderStatusDescDataVector();
    Connection conn = null;

    BusEntityDAO beDAO = mCacheManager.getBusEntityDAO();
    try
    {
        OrderDataVector orderStatusV = getOrderStatusCollection(pOrderStatusCriteria);

        if (null == orderStatusV ) {
            return orderStatusDescV;
        }

        orderStatusDescV = new OrderStatusDescDataVector();
        conn = getConnection();

        for (int i = 0; i < orderStatusV.size(); i++){
            OrderData orderStatusD = (OrderData)orderStatusV.get(i);
            OrderStatusDescData orderStatusDescD = OrderStatusDescData.createValue();
            orderStatusDescD.setOrderDetail(orderStatusD);

			//get the order item data
			DBCriteria crit = new DBCriteria();
			crit.addEqualTo(OrderItemDataAccess.ORDER_ID,orderStatusD.getOrderId());
			OrderItemDataVector orderItemV = OrderItemDataAccess.select(conn, crit);

			//figure out if all the items have been shipped according to the order
			//item actions
			initOrderItemActionProperties(orderStatusDescD,orderItemV,conn);

			orderStatusDescD.setAllowModifFl(calcAllowModifFlag(conn, orderStatusD));
			orderStatusDescD.setOrderItemList(orderItemV);
			
			//Get site address
			crit = new DBCriteria();
			crit.addEqualTo(AddressDataAccess.BUS_ENTITY_ID,orderStatusD.getSiteId());
			AddressDataVector addressDataV = AddressDataAccess.select(conn, crit);
			if(addressDataV!=null && addressDataV.size()>0){
				AddressData siteAddressData = (AddressData)addressDataV.get(0);
				orderStatusDescD.setSiteAddress(siteAddressData);
			}

            int uid = orderStatusD.getUserId();
            if(uid > 0){

                UserData orderContact = UserDataAccess.select(conn, uid);
                orderStatusDescD.setPlacedBy(orderContact);
            }else{

            	String addBy = orderStatusD.getAddBy();
            	crit = new DBCriteria();
            	crit.addEqualTo(UserDataAccess.USER_NAME, addBy);
            	UserDataVector udV = UserDataAccess.select(conn, crit);
            	if(udV.size()>0 && udV !=null){
            		orderStatusDescD.setPlacedBy((UserData)udV.get(0));
            	}
            }

            orderStatusDescV.add(orderStatusDescD);

        }
    }
    catch (Exception e)
    {
        e.printStackTrace();
        throw new RemoteException("getLightOrderStatusDescCollection: " +
                                  e.getMessage());
    }
    finally
    {
        closeConnection(conn);
    }

    return orderStatusDescV;
}


    /**
     * Get Jd order status desc data vector according to the orderStatusCriteriaData
     * @param pOrderStatusCriteria an <code>OrderStatusCriteriaData</code> value
     * @return an <code>OrderStatusDescDataVector</code> value
     * @exception            RemoteException
     */
    public JdOrderStatusViewVector getJdOrderStatusDescCollection(OrderStatusCriteriaData pOrderStatusCriteria)
    throws RemoteException
    {
        JdOrderStatusViewVector orderStatusDescV = new JdOrderStatusViewVector();
        Connection conn = null;
        try
        {
            conn = getConnection();
            //Get orders
            OrderDataVector orderStatusV = getOrderStatusCollection(pOrderStatusCriteria);
            //Get order meta data
            IdVector orderIds = new IdVector();
            for(int ii=0; ii<orderStatusV.size(); ii++)
            {
                OrderData orderStatusD = (OrderData)orderStatusV.get(ii);
                if(!RefCodeNames.ORDER_SOURCE_CD.EXTERNAL.equals(orderStatusD.getOrderSourceCd()))
                {
                    continue;
                }
                int orderId = orderStatusD.getOrderId();
                orderIds.add(new Integer(orderId));
            }
            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(OrderMetaDataAccess.ORDER_ID,orderIds);
            dbc.addOrderBy(OrderMetaDataAccess.ORDER_ID);
            OrderMetaDataVector omDV = OrderMetaDataAccess.select(conn,dbc);

            int[] orderIdA = new int[orderIds.size()];
            String[] companyA = new String[orderIds.size()];
            int ind = 0;
            for(int ii=0; ii<omDV.size(); ii++)
            {
                OrderMetaData omD = (OrderMetaData) omDV.get(ii);
                int orderId = omD.getOrderId();
                if(ind==0)
                {
                    orderIdA[0] = orderId;
                }
                else
                {
                    if(orderIdA[ind]!=orderId)
                    {
                        ind++;
                        orderIdA[ind]=orderId;
                    }
                }
                if("....Company .......".equalsIgnoreCase(omD.getName()))
                {
                    companyA[ind] = omD.getValue();
                }
            }
            //
            for (int ii = 0; ii < orderStatusV.size(); ii++)
            {
                OrderData orderStatusD = (OrderData)orderStatusV.get(ii);
                if(!RefCodeNames.ORDER_SOURCE_CD.EXTERNAL.equals(orderStatusD.getOrderSourceCd()))
                {
                    continue;
                }
                JdOrderStatusView josV = JdOrderStatusView.createValue();
                josV.setOrderId(orderStatusD.getOrderId());
                josV.setOrderNum(orderStatusD.getOrderNum());
                josV.setOrderDate(orderStatusD.getOriginalOrderDate());
                josV.setCustomerPoNum(orderStatusD.getRequestPoNum());
                josV.setAccountNum(orderStatusD.getAccountErpNum());
                josV.setTotalPrice(orderStatusD.getTotalPrice());
                josV.setTotalWeight(orderStatusD.getGrossWeight());
                int orderId = orderStatusD.getOrderId();
                for(int jj=0; jj<orderIdA.length; jj++)
                {
                    if(orderIdA[jj]==orderId)
                    {
                        josV.setCompany(companyA[jj]);
                    }
                }
                orderStatusDescV.add(josV);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RemoteException("getJdOrderStatusDescCollection: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return orderStatusDescV;
    }

    /**
     * Get exceptional order status desc data vector according to the orderStatusCriteriaData
     * @param pOrderStatusCriteria an <code>OrderStatusCriteriaData</code> value
     * @return an <code>OrderStatusDescDataVector</code> value
     * @exception            RemoteException
     */
    public OrderStatusDescDataVector getExceptionOrderDescCollection(OrderStatusCriteriaData pOrderStatusCriteria)
    throws RemoteException
    {

        OrderStatusDescDataVector orderStatusDescV = new OrderStatusDescDataVector();
        Connection conn = null;
        BusEntityDAO beDAO = mCacheManager.getBusEntityDAO();

        try
        {

            OrderDataVector orderStatusV = getExceptionOrderCollection(
                                               pOrderStatusCriteria);

            if (null != orderStatusV && orderStatusV.size() > 0)
            {
                orderStatusDescV = new OrderStatusDescDataVector();
                conn = getConnection();

                for (int i = 0; i < orderStatusV.size(); i++)
                {

                    OrderData orderStatusD = (OrderData)orderStatusV.get(i);
                    DBCriteria crit = new DBCriteria();
                    crit.addEqualTo(OrderItemDataAccess.ORDER_ID,
                                    orderStatusD.getOrderId());

                    // for customer reports, we want all items no matter if it has dist_erp_num
                    // --Liang 06/17/2002
                    //crit.addIsNotNull(OrderItemDataAccess.DIST_ERP_NUM);
                    OrderItemDataVector orderItemV = OrderItemDataAccess.select(
                                                         conn, crit);
                    OrderStatusDescData orderStatusDescD = OrderStatusDescData.createValue();
                    orderStatusDescD.setOrderDetail(orderStatusD);
                    orderStatusDescD.setAllowModifFl(calcAllowModifFlag(conn, orderStatusD));
                    orderStatusDescD.setOrderItemList(orderItemV);

                    //set the OrderMetaData
                    crit = new DBCriteria();
                    crit.addEqualTo(OrderMetaDataAccess.ORDER_ID,
                                    orderStatusD.getOrderId());
                    OrderMetaDataVector orderMetaDV = OrderMetaDataAccess.select(conn, crit);
                    orderStatusDescD.setOrderMetaData(orderMetaDV);




                    // set the erpOrderNum
                    if (null != orderItemV && 0 < orderItemV.size())
                    {
                        initOrderItemActionProperties(orderStatusDescD,orderItemV,conn);
                        OrderItemData orderItemD = (OrderItemData)orderItemV.get(
                                                       0);
                        orderStatusDescD.setErpOrderNum(orderItemD.getErpOrderNum());
                    }

                    // Order ship to and bill to information.
                    crit = new DBCriteria();
                    crit.addEqualTo(OrderAddressDataAccess.ORDER_ID,
                                    orderStatusD.getOrderId());

                    OrderAddressDataVector oadv = OrderAddressDataAccess.select(
                                                      conn, crit);

                    if (null != oadv)
                    {

                        for (int ia = 0; ia < oadv.size(); ia++)
                        {

                            OrderAddressData oa = (OrderAddressData)oadv.get(
                                                      ia);

                            if (oa.getAddressTypeCd().equals(
                                        RefCodeNames.ADDRESS_TYPE_CD.SHIPPING))
                            {
                                orderStatusDescD.setShipTo(oa);
                            }
                            else if (oa.getAddressTypeCd().equals(
                                         RefCodeNames.ADDRESS_TYPE_CD.BILLING))
                            {
                                orderStatusDescD.setBillTo(oa);
                            }
                        }
                    }

                    // add the account name
                    try{
                        BusEntityData accountD = beDAO.getAccountById
                        (conn,orderStatusDescD.getAccountId());
                        orderStatusDescD.setAccountId(accountD.getBusEntityId());
                        orderStatusDescD.setAccountName(accountD.getShortDesc());
                    }catch(DataNotFoundException e){
                        orderStatusDescD.setAccountId(0);
                        orderStatusDescD.setAccountName("Error");
                    }

                    // get the dist name
                    OrderItemDataVector distOrderItemV = new OrderItemDataVector();

                    if (null != orderItemV && 0 < orderItemV.size())
                    {

                        for (int j = 0; j < orderItemV.size(); j++)
                        {

                            OrderItemData itemD = (OrderItemData)orderItemV.get(
                                                      j);
                            String distErpNum = itemD.getDistErpNum();

                            if (null != distErpNum &&
                                    !"".equals(distErpNum))
                            {
                                distOrderItemV.add(itemD);
                            }
                        }
                    }

                    String distName = new String("");
                    ArrayList distErpList = new ArrayList();

                    if (null != distOrderItemV &&
                            0 < distOrderItemV.size())
                    {

                        for (int j = 0; j < distOrderItemV.size(); j++)
                        {

                            String distErpNum = ((OrderItemData)distOrderItemV.get(
                                                     j)).getDistErpNum();

                            if (0 == j)
                            {
                                distErpList.add(distErpNum);
                            }
                            else
                            {

                                boolean matchFlag = false;

                                for (int k = 0; k < distErpList.size(); k++)
                                {

                                    if (distErpNum.equals(
                                                (String)distErpList.get(k)))
                                    {
                                        matchFlag = true;

                                        break;
                                    }
                                }

                                if (false == matchFlag)
                                {
                                    distErpList.add(distErpNum);
                                }
                            }
                        }
                    }

                    if (null != distErpList && 0 < distErpList.size())
                    {

                        for (int j = 0; j < distErpList.size(); j++)
                        {
                            String thisDistErpNum = (String)distErpList.get(j);
                            BusEntityData distBE = beDAO.getDistributorByErpNum(conn, thisDistErpNum);
                            distName += distBE.getShortDesc() + " / ";
                        }
                    }

                    if (3 < distName.length())
                    {
                        orderStatusDescD.setDistName(distName.substring(0,
                                                     distName.length() - 3));
                    }

                    // get the invoiceDistList and invoiceCustList
                    crit = new DBCriteria();
                    crit.addEqualTo(InvoiceDistDataAccess.ORDER_ID,
                                    orderStatusD.getOrderId());

                    InvoiceDistDataVector invDistV = InvoiceDistDataAccess.select(
                                                         conn, crit);
                    crit = new DBCriteria();
                    crit.addEqualTo(InvoiceCustDataAccess.ORDER_ID,
                                    orderStatusD.getOrderId());

                    InvoiceCustDataVector invCustV = InvoiceCustDataAccess.select(
                                                         conn, crit);

                    // get the invoiceCustDetailList
                    crit = new DBCriteria();
                    crit.addEqualTo(InvoiceCustDataAccess.ORDER_ID,
                                    orderStatusD.getOrderId());

                    IdVector invCustIdV = InvoiceCustDataAccess.selectIdOnly(
                                              conn,
                                              InvoiceCustDataAccess.INVOICE_CUST_ID,
                                              crit);
                    crit = new DBCriteria();
                    crit.addOneOf(InvoiceCustDetailDataAccess.INVOICE_CUST_ID,
                                  invCustIdV);

                    InvoiceCustDetailDataVector invCustDetailV =
                        InvoiceCustDetailDataAccess.select(conn, crit);

                    // set the exceptionTypeCd
                    String exceptionTypeCd = new String("");
                    String exceptionInd = orderStatusD.getExceptionInd();
                    String orderStatus = orderStatusD.getOrderStatusCd();

                    if ("Y".equalsIgnoreCase(exceptionInd) &&
                            RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW.equalsIgnoreCase(
                                orderStatus))
                    {
                        exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.INBOUND_ORDER + " / ";
                    }

                    if (RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED.equalsIgnoreCase(
                                orderStatus))
                    {
                        exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.ORDER_TO_ERP + " / ";
                    }

                    if (RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED_PO_ERROR.equalsIgnoreCase(
                                orderStatus))
                    {
                        exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.PO_FROM_ERP + " / ";
                    }

                    // test if customer acknowledgement exception
                    boolean itemAckFailedFlag = false;

                    if (null != orderItemV)
                    {

                        for (int j = 0; j < orderItemV.size(); j++)
                        {

                            String ackStatus = ((OrderItemData)orderItemV.get(
                                                    j)).getAckStatusCd();

                            if (RefCodeNames.ACK_STATUS_CD.CUST_ACK_FAILED.equalsIgnoreCase(
                                        ackStatus) ||
                                    RefCodeNames.ACK_STATUS_CD.CANCELLED_ACK_FAILED.equalsIgnoreCase(
                                        ackStatus))
                            {
                                itemAckFailedFlag = true;

                                break;
                            }
                        }
                    }

                    if (true == itemAckFailedFlag)
                    {
                        exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.CUSTOMER_ACKNOWLEDGEMENT + " / ";
                    }

                    // test if PO to vendor exception
                    boolean itemPendingReviewFlag = false;

                    if (null != orderItemV)
                    {

                        for (int j = 0; j < orderItemV.size(); j++)
                        {

                            String itemStatus = ((OrderItemData)orderItemV.get(
                                                     j)).getOrderItemStatusCd();

                            if (RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED.equalsIgnoreCase(
                                        itemStatus))
                            {
                                itemPendingReviewFlag = true;

                                break;
                            }
                        }
                    }

                    if (true == itemPendingReviewFlag)
                    {
                        exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.PO_TO_VENDOR + " / ";
                    }

                    // test if Vendor Invoice exception
                    boolean invPendingReviewFlag = false;

                    if (null != invDistV)
                    {

                        for (int j = 0; j < invDistV.size(); j++)
                        {

                            String invDistStatus = ((InvoiceDistData)invDistV.get(
                                                        j)).getInvoiceStatusCd();

                            if (RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW.equalsIgnoreCase(
                                        invDistStatus))
                            {
                                invPendingReviewFlag = true;

                                break;
                            }
                        }
                    }

                    if (true == invPendingReviewFlag)
                    {
                        exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.VENDOR_INVOICE + " / ";
                    }

                    // test if Invoice to ERP exception
                    boolean invRejectedFlag = false;

                    if (null != invDistV)
                    {

                        for (int j = 0; j < invDistV.size(); j++)
                        {

                            String invDistStatus = ((InvoiceDistData)invDistV.get(
                                                        j)).getInvoiceStatusCd();

                            if (RefCodeNames.INVOICE_STATUS_CD.ERP_REJECTED.equalsIgnoreCase(
                                        invDistStatus))
                            {
                                invRejectedFlag = true;

                                break;
                            }
                        }
                    }

                    if (true == invRejectedFlag)
                    {
                        exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.INVOICE_TO_ERP + " / ";
                    }

                    // test if invoice from erp exception
                    boolean invErpFailedFlag = false;

                    if (null != invCustV)
                    {

                        for (int j = 0; j < invCustV.size(); j++)
                        {

                            String invCustStatus = ((InvoiceCustData)invCustV.get(
                                                        j)).getInvoiceStatusCd();

                            if (RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED_ERROR.equalsIgnoreCase(
                                        invCustStatus) ||
                                    RefCodeNames.INVOICE_STATUS_CD.ERP_GENERATED_ERROR.equalsIgnoreCase(
                                        invCustStatus))
                            {
                                invErpFailedFlag = true;

                                break;
                            }
                        }
                    }

                    if (true == invErpFailedFlag)
                    {
                        exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.INVOICE_FROM_ERP + " / ";
                    }

                    // test if cust ship notice exception
                    boolean invShipNotifFlag = false;

                    if (null != invCustDetailV)
                    {

                        for (int j = 0; j < invCustDetailV.size(); j++)
                        {

                            String shipStatus = ((InvoiceCustDetailData)invCustDetailV.get(
                                                     j)).getShipStatusCd();

                            if (RefCodeNames.SHIP_STATUS_CD.FAILED.equalsIgnoreCase(
                                        shipStatus))
                            {
                                invShipNotifFlag = true;

                                break;
                            }
                        }
                    }

                    if (true == invShipNotifFlag)
                    {
                        exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.CUSTOMER_SHIP_NOTICE + " / ";
                    }

                    // test if cust invoice exception
                    boolean invFailedFlag = false;

                    if (null != invCustV)
                    {

                        for (int j = 0; j < invCustV.size(); j++)
                        {

                            String invCustStatus = ((InvoiceCustData)invCustV.get(
                                                        j)).getInvoiceStatusCd();

                            if (RefCodeNames.INVOICE_STATUS_CD.CUST_INVOICED_FAILED.equalsIgnoreCase(
                                        invCustStatus))
                            {
                                invFailedFlag = true;

                                break;
                            }
                        }
                    }

                    if (true == invFailedFlag)
                    {
                        exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.CUSTOMER_INVOICE + " / ";
                    }

                    // test if CIT invoice exception
                    boolean citInvFailedFlag = false;

                    if (null != invCustV)
                    {

                        for (int j = 0; j < invCustV.size(); j++)
                        {

                            String citStatus = ((InvoiceCustData)invCustV.get(
                                                    j)).getCitStatusCd();

                            if (RefCodeNames.CIT_STATUS_CD.FAILED.equalsIgnoreCase(
                                        citStatus))
                            {
                                citInvFailedFlag = true;

                                break;
                            }
                        }
                    }

                    if (true == citInvFailedFlag)
                    {
                        exceptionTypeCd += RefCodeNames.ORDER_EXCEPTION_TYPE_CD.CIT_INVOICE + " / ";
                    }

                    if (3 < exceptionTypeCd.length())
                    {
                        orderStatusDescD.setExceptionTypeCd(exceptionTypeCd.substring(
                                                                0,
                                                                exceptionTypeCd.length() - 3));
                    }

                    orderStatusDescV.add(orderStatusDescD);
                }
            }
        }
        catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }

        return orderStatusDescV;
    }

    /**
     * Add a note to a particular order
     * @param pOrderPropertyData the order property data to set
     * @return an <code>OrderPropertyData</code> value
     * @exception RemoteException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderPropertyData addNote(OrderPropertyData pOrderPropertyData)
    throws RemoteException
    {
 	   log.info("addNote() --> pOrderPropertyData.value : " + pOrderPropertyData.getValue());

        Connection conn = null;

        try
        {
            conn = getConnection();
            if(pOrderPropertyData.getOrderPropertyId() > 0){
                OrderPropertyDataAccess.update(conn, pOrderPropertyData);
            }else{
                pOrderPropertyData = OrderPropertyDataAccess.insert(conn, pOrderPropertyData);
            }
        }
        catch (Exception e)
        {
            throw new RemoteException("addNote: " + e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return pOrderPropertyData;
    }

    /**
     *Retrieves all of the order notes.  Notes are not those that strictly fall under
     *the Notes type code, but also customer cart comments, and anything else that are misc
     *order properties intended for Human consumption.
     *@param pOrderId the order Id
     * @return an <code>OrderPropertyDataVector</code> value
     * @exception RemoteException if an error occurs
     */
    public OrderPropertyDataVector getOrderNotes(int pOrderId) throws RemoteException{
        Connection conn = null;
        try{
            conn = getConnection();
            OrderPropertyDataVector orderPropertyVec = new OrderPropertyDataVector();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderPropertyDataAccess.ORDER_ID, pOrderId);
            ArrayList l = new ArrayList();
            l.add(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS);
            l.add(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
            crit.addOneOf(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,l);
            crit.addOrderBy(OrderPropertyDataAccess.ORDER_PROPERTY_ID, false);
            orderPropertyVec = OrderPropertyDataAccess.select(conn, crit);
            return orderPropertyVec;
        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**
     * Get order property data vector according to the orderId
     * @param pOrderId the order status Id to search the order property
     * @return an <code>OrderPropertyDataVector</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderPropertyDataVector getOrderPropertyCollection(int pOrderId,
            String pPropertyTypeCd)
    throws RemoteException,
                DataNotFoundException
    {

        OrderPropertyDataVector orderPropertyVec = new OrderPropertyDataVector();
        Connection conn = null;

        try
        {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderPropertyDataAccess.ORDER_ID, pOrderId);
            crit.addEqualToIgnoreCase(
                OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
                pPropertyTypeCd);
            crit.addOrderBy(OrderPropertyDataAccess.ORDER_PROPERTY_ID, false);
            orderPropertyVec = OrderPropertyDataAccess.select(conn, crit);
        }
        catch (Exception e)
        {
            throw new RemoteException("getOrderPropertyCollection: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }
        return orderPropertyVec;
    }

    /**
     * Get order property data vector according to the orderItemId
     * @param pOrderItemId the order status Id to search the order property
     * @return an <code>OrderPropertyDataVector</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderPropertyDataVector getOrderItemPropertyCollection(int pOrderItemId,
            String pPropertyTypeCd)
    throws RemoteException, DataNotFoundException
    {

        OrderPropertyDataVector orderPropertyVec = new OrderPropertyDataVector();
        Connection conn = null;

        try
        {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderPropertyDataAccess.ORDER_ITEM_ID,
                            pOrderItemId);
            crit.addEqualToIgnoreCase(
                OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
                pPropertyTypeCd);
            crit.addOrderBy(OrderPropertyDataAccess.ORDER_PROPERTY_ID, false);
            orderPropertyVec = OrderPropertyDataAccess.select(conn, crit);
        }
        catch (Exception e)
        {
            throw new RemoteException("getOrderItemPropertyCollection: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return orderPropertyVec;
    }

    /**
     * Get order property data according to the orderPropertyId
     * @param pOrderPropertyId the order status Id to search the order property
     * @return an <code>OrderPropertyDataVector</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderPropertyData getOrderProperty(int pOrderPropertyId,
            String pPropertyTypeCd)
    throws RemoteException,
                DataNotFoundException
    {

        OrderPropertyData orderProperty = null;
        Connection conn = null;

        try
        {
            conn = getConnection();
            orderProperty = OrderPropertyDataAccess.select(conn,
                            pOrderPropertyId);

            if (null != orderProperty)
            {

                if (!pPropertyTypeCd.equals(orderProperty.getOrderPropertyTypeCd()))
                {
                    orderProperty = null;
                }
            }
        }
        catch (Exception e)
        {
            throw new RemoteException("getOrderProperty: " + e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return orderProperty;
    }

    /**
     * Get order property data vector according to the orderId
     * @param pOrderId the order status Id to search the order property
     * @return an <code>OrderPropertyDataVector</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderPropertyDataVector getOrderPropertyVec(int pOrderId)
    throws RemoteException,
                DataNotFoundException
    {

        OrderPropertyDataVector orderPropertyVec = new OrderPropertyDataVector();
        Connection conn = null;

        try
        {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderPropertyDataAccess.ORDER_ID, pOrderId);
            crit.addEqualToIgnoreCase(
                OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
                RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
            orderPropertyVec = OrderPropertyDataAccess.select(conn, crit);
        }
        catch (Exception e)
        {
            throw new RemoteException("getOrderProperty: " + e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return orderPropertyVec;
    }

    /**
     * Get order property data vector of order item detail according to the orderItemDetailId
     * @param pOrderItemDetailId the order item detail Id to search the order property
     * @return an <code>OrderPropertyDataVector</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderPropertyDataVector getOrderPropertyCollectionByItemDetail(int pOrderItemDetailId)
    throws RemoteException, DataNotFoundException
    {

        OrderPropertyDataVector orderPropertyVec = new OrderPropertyDataVector();

        /*//
        Connection conn = null;

        try {
        conn = getConnection();
            DBCriteria crit = new DBCriteria();

            crit.addEqualTo(OrderPropertyDataAccess.ORDER_ITEM_DETAIL_ID, pOrderItemDetailId);
            // crit.addLikeIgnoreCase(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD, "ORDER_ITEM_DETAIL_NOTE");
        orderPropertyVec = OrderPropertyDataAccess.select(conn, crit);
    } catch (Exception e) {
        throw new RemoteException("getOrderPropertyCollectionByItemDetail: "+e.getMessage());
    } finally {
        try {if (conn != null) conn.close();} catch (Exception ex) {}
    }
         */
        return orderPropertyVec;
    }

    /**
     * Get order item status desc data according to the OrderItemId
     * @param pOrderItemId the order status Id to search the order status
     * @return an <code>OrderItemDescData</code> value
     * @exception            RemoteException
     */
    public OrderItemDescData getOrderItemDescByItem(int pOrderItemId)
    throws RemoteException
    {

        OrderItemDescData orderItemDescD = null;
        Connection conn = null;
        BusEntityDAO beDAO = mCacheManager.getBusEntityDAO();

        try
        {
            conn = getConnection();

            OrderItemData orderItemD = OrderItemDataAccess.select(conn,
                                       pOrderItemId);

            if (null != orderItemD)
            {
                orderItemDescD = OrderItemDescData.createValue();
                orderItemDescD.setOrderItem(orderItemD);

                DBCriteria crit = new DBCriteria();

                // get the itemSubstitutions for this order item
                crit.addEqualTo(ItemSubstitutionDataAccess.ORDER_ITEM_ID,
                                pOrderItemId);

                ItemSubstitutionDataVector orderItemSubstitutionV =
                    ItemSubstitutionDataAccess.select(conn, crit);

                // get the orderItemActions  for this order item
                crit = new DBCriteria();
                crit.addEqualTo(OrderItemActionDataAccess.ORDER_ITEM_ID,
                                pOrderItemId);
                crit.addOrderBy(OrderItemActionDataAccess.ACTION_DATE,false);
                crit.addOrderBy(OrderItemActionDataAccess.ACTION_TIME,false);

                OrderItemActionDataVector orderItemActionV =
                    OrderItemActionDataAccess.select(conn, crit);
                orderItemDescD.setOrderItemSubstitutionList(
                    orderItemSubstitutionV);
                orderItemDescD.setOrderItemActionList(orderItemActionV);

                // get the invoiceDistDetailList for this order
                crit = new DBCriteria();
                crit.addEqualTo(InvoiceDistDetailDataAccess.ORDER_ITEM_ID,
                                orderItemD.getOrderItemId());
                crit.addOrderBy(
                    InvoiceDistDetailDataAccess.INVOICE_DIST_DETAIL_ID);

                InvoiceDistDetailDataVector invoiceDistDetailV =
                    InvoiceDistDetailDataAccess.select(conn, crit);
                orderItemDescD.setInvoiceDistDetailList(invoiceDistDetailV);

                // get the invoiceCusts' Ids for this order
                crit = new DBCriteria();
                crit.addEqualTo(InvoiceCustDataAccess.ORDER_ID,
                                orderItemD.getOrderId());

                IdVector idV = InvoiceCustDataAccess.selectIdOnly(conn,
                               InvoiceCustDataAccess.INVOICE_CUST_ID,
                               crit);

                // get the invoiceCustDetails for this order
                crit = new DBCriteria();
                crit.addOneOf(InvoiceCustDetailDataAccess.INVOICE_CUST_ID, idV);

                InvoiceCustDetailDataVector invoiceCustDetailV =
                    InvoiceCustDetailDataAccess.select(conn, crit);

                // match the invoiceCustDetail with orderItem or itemSubstitutions
                InvoiceCustDetailDataVector tempInvoiceCustDetailV =
                    new InvoiceCustDetailDataVector();

                for (int j = 0; j < invoiceCustDetailV.size(); j++)
                {

                    InvoiceCustDetailData invoiceCustDetailD =
                        (InvoiceCustDetailData)invoiceCustDetailV.get(j);

                    if (invoiceCustDetailD.getItemSkuNum() == orderItemD.getItemSkuNum())
                    {
                        tempInvoiceCustDetailV.add(invoiceCustDetailD);
                    }
                    else
                    {

                        // match for the substitutions of this item
                        for (int k = 0; k < orderItemSubstitutionV.size(); k++)
                        {

                            ItemSubstitutionData orderItemSubstitutionD =
                                (ItemSubstitutionData)orderItemSubstitutionV.get(
                                    k);

                            if (invoiceCustDetailD.getItemSkuNum() == orderItemSubstitutionD.getItemSkuNum())
                            {
                                tempInvoiceCustDetailV.add(invoiceCustDetailD);

                                break;
                            }
                        } //end of k-loop
                    }
                } // end of j-loop

                orderItemDescD.setInvoiceCustDetailList(tempInvoiceCustDetailV);

                // get the dist name
                String distName = new String("");
                String distErpNum = orderItemD.getDistErpNum();

                if (null != distErpNum)
                {

                    BusEntityData distBE = beDAO.getDistributorByErpNum(conn, distErpNum);
                    distName += distBE.getShortDesc() + " / ";
                }

                if (3 < distName.length())
                {
                    orderItemDescD.setDistName(distName.substring(0,
                                               distName.length() - 3));
                }
            }
        }
        catch (Exception e)
        {
            throw new RemoteException("getOrderItemDescByItem: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return orderItemDescD;
    }

    /**
     * Get invoiceDistDataVector according to the OrderId
     * @param pOrderId the order status Id to search the invoiceDist
     * @return an <code>InvoiceDistDataVector</code> value
     * @exception            RemoteException
     */
    public InvoiceDistDataVector getInvoiceDistCollection(int pOrderId)
    throws RemoteException
    {

        InvoiceDistDataVector invoiceDistV = new InvoiceDistDataVector();
        Connection conn = null;

        try
        {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();

            // get the invoiceDists  for this order
            crit.addEqualTo(InvoiceDistDataAccess.ORDER_ID, pOrderId);
            crit.addOrderBy(InvoiceDistDataAccess.INVOICE_DIST_ID);
            invoiceDistV = InvoiceDistDataAccess.select(conn, crit);
        }
        catch (Exception e)
        {
            throw new RemoteException("getInvoiceDistCollection: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return invoiceDistV;
    }

    /**
     * Get invoiceDistDataVector according to the OrderItemId
     * @param pOrderItemId the order status Id to search the invoiceDist
     * @return an <code>InvoiceDistDataVector</code> value
     * @exception            RemoteException
     */
    public InvoiceDistDataVector getInvoiceDistCollectionByItem(int pOrderItemId)
    throws RemoteException
    {

        InvoiceDistDataVector invoiceDistV = null;
        Connection conn = null;

        try
        {
            conn = getConnection();

            OrderItemData orderItemD = OrderItemDataAccess.select(conn,
                                       pOrderItemId);

            if (null != orderItemD)
            {

                DBCriteria crit = new DBCriteria();

                // get the invoiceDists' Ids for this order
                crit = new DBCriteria();
                crit.addEqualTo(InvoiceDistDetailDataAccess.ORDER_ITEM_ID,
                                pOrderItemId);

                IdVector idV = InvoiceDistDetailDataAccess.selectIdOnly(conn,
                               InvoiceDistDetailDataAccess.INVOICE_DIST_ID,
                               crit);
                crit = new DBCriteria();
                crit.addOneOf(InvoiceDistDataAccess.INVOICE_DIST_ID, idV);
                crit.addOrderBy(InvoiceDistDataAccess.INVOICE_DIST_ID);
                invoiceDistV = InvoiceDistDataAccess.select(conn, crit);
            }
        }
        catch (Exception e)
        {
            throw new RemoteException("getInvoiceDistCollectionByItem: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return invoiceDistV;
    }

    /**
     * Get invoiceDist data according to the invoiceDistId
     * @param pInvoiceDistId the invoiceDist Id to search the invoiceDist
     * @return an <code>InvoiceDistData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public InvoiceDistData getInvoiceDist(int pInvoiceDistId)
    throws RemoteException,
                DataNotFoundException
    {

        InvoiceDistData invoiceDistD = null;
        Connection conn = null;

        try
        {
            conn = getConnection();
            invoiceDistD = InvoiceDistDataAccess.select(conn, pInvoiceDistId);
        }
        catch (DataNotFoundException e)
        {

            //throw e;
        }
        catch (Exception e)
        {
            throw new RemoteException("getInvoiceDist: " + e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return invoiceDistD;
    }

    /**
     * Get invoiceDistDetailDesc data vector according to the invoiceDistId
     * @param pInvoiceDistId the invoice dist Id to search the invoiceDistDetail
     * @return an <code>InvoiceDistDetailDescDataVector</code> value
     * @exception            RemoteException
     */
    public InvoiceDistDetailDescDataVector getInvoiceDistDetailDescCollection(int pInvoiceDistId)
    throws RemoteException
    {

        InvoiceDistDetailDescDataVector invoiceDistDetailDescV =
            new InvoiceDistDetailDescDataVector();
        Connection conn = null;

        try
        {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();

            // get the invoiceDistData
            InvoiceDistData invoiceDistD = InvoiceDistDataAccess.select(conn,
                                           pInvoiceDistId);
            OrderItemDataVector orderItemV = new OrderItemDataVector();

            if (null != invoiceDistD)
            {

                // geth the orderItemDataVector for this order
                crit = new DBCriteria();
                crit.addEqualTo(OrderItemDataAccess.ORDER_ID,
                                invoiceDistD.getOrderId());
                orderItemV = OrderItemDataAccess.select(conn, crit);
            }

            BusEntityDataVector distV = new BusEntityDataVector();

            // get the OrderItem data for this order status.
            crit = new DBCriteria();
            crit.addEqualTo(InvoiceDistDetailDataAccess.INVOICE_DIST_ID,
                            pInvoiceDistId);

            InvoiceDistDetailDataVector invoiceDistDetailV =
                InvoiceDistDetailDataAccess.select(conn, crit);

            if (null != invoiceDistDetailV &&
                    0 < invoiceDistDetailV.size())
            {
                invoiceDistDetailDescV = new InvoiceDistDetailDescDataVector();

                for (int i = 0; i < invoiceDistDetailV.size(); i++)
                {

                    InvoiceDistDetailDescData invoiceDistDetailDescD =
                        InvoiceDistDetailDescData.createValue();
                    InvoiceDistDetailData invoiceDistDetailD =
                        (InvoiceDistDetailData)invoiceDistDetailV.get(i);
                    invoiceDistDetailDescD.setInvoiceDistDetail(
                        invoiceDistDetailD);

                    if (null != orderItemV && 0 < orderItemV.size())
                    {

                        for (int j = 0; j < orderItemV.size(); j++)
                        {

                            OrderItemData orderItemD = (OrderItemData)orderItemV.get(
                                                           j);

                            if (invoiceDistDetailD.getOrderItemId() == orderItemD.getOrderItemId())
                            {
                                invoiceDistDetailDescD.setOrderItem(orderItemD);

                                // set the distName
                                boolean findDistFlag = false;

                                for (int k = 0; k < distV.size(); k++)
                                {

                                    BusEntityData distD = (BusEntityData)distV.get(
                                                              k);

                                    if (orderItemD.getDistErpNum().equals(distD.getErpNum()))
                                    {
                                        invoiceDistDetailDescD.setDistName(distD.getShortDesc());
                                        findDistFlag = true;

                                        break;
                                    }
                                }

                                if (false == findDistFlag)
                                {
                                    crit = new DBCriteria();
                                    crit.addEqualTo(
                                        BusEntityDataAccess.ERP_NUM,
                                        orderItemD.getDistErpNum());
                                    crit.addEqualToIgnoreCase(
                                        BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                                        RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);

                                    BusEntityDataVector subDistV =
                                        BusEntityDataAccess.select(conn,
                                                                   crit);

                                    if (null != subDistV &&
                                            0 < subDistV.size())
                                    {
                                        invoiceDistDetailDescD.setDistName(((BusEntityData)subDistV.get(
                                                                                0)).getShortDesc());
                                        distV.addAll(subDistV);
                                    }
                                }

                                break;
                            }
                        }
                    }

                    invoiceDistDetailDescV.add(invoiceDistDetailDescD);
                }
            }
        }
        catch (Exception e)
        {
            throw new RemoteException("getInvoiceDistDetailDescCollection: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return invoiceDistDetailDescV;
    }


    /**
     * Get InvoiceCustViewVector according to the supplied criteria object
     * @param pCrit the criteria
     * @param pHeavyWeight wheather you want the detail popultated or left null
     * @return an <code>InvoiceCustViewVector</code> value
     * @exception            RemoteException
     */
    public InvoiceCustViewVector getInvoiceCustViewCollection(InvoiceCustCritView pCrit,boolean pHeavyWeight)
    throws RemoteException{
        Connection conn = null;
        try{
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            boolean critSet = false;
            if(pCrit.getAccountId() > 0){
                crit.addEqualTo(InvoiceCustDataAccess.ACCOUNT_ID,pCrit.getAccountId());
                critSet = true;
            }
            if(pCrit.getWebOrderNum() > 0){
              String q = " select order_id from clw_order where order_num = '"+
                pCrit.getWebOrderNum() + "'";
                crit.addOneOf(InvoiceCustDataAccess.ORDER_ID,q);
                critSet = true;
            }
            if(pCrit.getStoreId() > 0){
                crit.addEqualTo(InvoiceCustDataAccess.STORE_ID,pCrit.getStoreId());
                critSet = true;
            }
            if(pCrit.getInvoiceDateRangeBegin() != null){
                crit.addGreaterOrEqual(InvoiceCustDataAccess.INVOICE_DATE,pCrit.getInvoiceDateRangeBegin());
                critSet = true;
            }
            if(pCrit.getInvoiceDateRangeEnd() != null){
                crit.addLessOrEqual(InvoiceCustDataAccess.INVOICE_DATE,pCrit.getInvoiceDateRangeEnd());
                critSet = true;
            }
            if(Utility.isSet(pCrit.getInvoiceNumRangeBegin())){
                crit.addGreaterOrEqual(InvoiceCustDataAccess.INVOICE_NUM,pCrit.getInvoiceNumRangeBegin());
                critSet = true;
            }
            if(Utility.isSet(pCrit.getInvoiceNumRangeEnd())){
                crit.addLessOrEqual(InvoiceCustDataAccess.INVOICE_NUM,pCrit.getInvoiceNumRangeEnd());
                critSet = true;
            }
            if(pCrit.getInvoiceCustId() > 0){
                crit.addEqualTo(InvoiceCustDataAccess.INVOICE_CUST_ID,pCrit.getInvoiceCustId());
                critSet = true;
            }
            if(!critSet){
                throw new RemoteException("Critria was not set");
            }
            InvoiceCustDataVector icdv = InvoiceCustDataAccess.select(conn,crit,500);
            InvoiceCustViewVector retVal = new InvoiceCustViewVector();
            Iterator it = icdv.iterator();
            while(it.hasNext()){
                InvoiceCustData inv = (InvoiceCustData) it.next();
                InvoiceCustView iv = InvoiceCustView.createValue();
                InvoiceCustInfoViewVector ifv =
        new InvoiceCustInfoViewVector();

                iv.setInvoiceCustData(inv);
    iv.setInvoiceCustInfoViewVector(ifv);

                if(pHeavyWeight){
                    crit = new DBCriteria();
                    crit.addEqualTo(InvoiceCustDetailDataAccess.INVOICE_CUST_ID,inv.getInvoiceCustId());
                    crit.addOrderBy(InvoiceCustDetailDataAccess.LINE_NUMBER);
                    iv.setInvoiceCustDetailDataVector(InvoiceCustDetailDataAccess.select(conn,crit));
                    if(inv.getOrderId() > 0){
                        iv.setOrderData(OrderDataAccess.select(conn,inv.getOrderId()));
                    }else{
                        iv.setOrderData(OrderData.createValue());
                    }
                }
                retVal.add(iv);
            }
            return retVal;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**
     * Get invoiceCustDataVector according to the OrderId
     * @param pOrderId the order status Id to search the invoiceCust
     * @return an <code>InvoiceCustDataVector</code> value
     * @exception            RemoteException
     */
    public InvoiceCustDataVector getInvoiceCustCollection(int pOrderId)
    throws RemoteException
    {

        InvoiceCustDataVector invoiceCustV = new InvoiceCustDataVector();
        Connection conn = null;

        try
        {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();

            // get the invoiceCusts  for this order
            crit.addEqualTo(InvoiceCustDataAccess.ORDER_ID, pOrderId);
            crit.addOrderBy(InvoiceCustDataAccess.INVOICE_CUST_ID);
            invoiceCustV = InvoiceCustDataAccess.select(conn, crit);
        }
        catch (Exception e)
        {
            throw new RemoteException("getInvoiceCustCollection: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return invoiceCustV;
    }

    /**
    * Get invoiceCustDataVector according to the pCitTransactionDate,
    * pCitAssignmentNumber and pInvoiceNum
    * @param pCitTransactionDate the CIT transaction date to search the related CIT invoices
    * @param pCitAssignmentNumber the CIT assignment num to search the related CIT invoices
    * @param pInvoiceNum the invoice num to search the related CIT invoices
    * @param pInvoiceStatusCd the invoice status cd
    * @param pCitStatusCd the cit status cd
    * @return an <code>InvoiceCustDataVector</code> value
    * @exception            RemoteException
    */
    public InvoiceCustDataVector getInvoiceCustCollection(Date pCitTransactionDate,
            int pCitAssignmentNumber,
            String pInvoiceNum,
            String pInvoiceStatusCd,
            String pCitStatusCd)
    throws RemoteException
    {

        InvoiceCustDataVector invoiceCustV = new InvoiceCustDataVector();
        logDebug(" getInvoiceCustCollection : " + " pCitTransactionDate=" +
                 pCitTransactionDate + " pCitAssignmentNumber=" +
                 pCitAssignmentNumber + " pInvoiceNum=" + pInvoiceNum +
                 " pInvoiceStatusCd="+pInvoiceStatusCd + " pCitStatusCd= " +
                 pCitStatusCd);

        Connection conn = null;
        try
        {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            boolean critSet = false;

            // get the invoiceCusts that match the search criteria here
            if (Utility.isSet(pInvoiceStatusCd))
            {
                critSet = true;
                crit.addEqualTo(InvoiceCustDataAccess.INVOICE_STATUS_CD, pInvoiceStatusCd);
            }

            if (Utility.isSet(pCitStatusCd))
            {
                critSet = true;
                crit.addEqualTo(InvoiceCustDataAccess.CIT_STATUS_CD, pCitStatusCd);
            }

            if (Utility.isSet(pInvoiceNum))
            {
                critSet = true;
                crit.addEqualTo(InvoiceCustDataAccess.INVOICE_NUM, pInvoiceNum);
            }

            if (pCitTransactionDate != null)
            {
                critSet = true;
                crit.addEqualTo(InvoiceCustDataAccess.CIT_TRANSACTION_DATE,
                                pCitTransactionDate);
            }

            if (pCitAssignmentNumber != 0)
            {
                critSet = true;
                crit.addEqualTo(InvoiceCustDataAccess.CIT_ASSIGNMENT_NUMBER,
                                pCitAssignmentNumber);
            }

            if (critSet == false)
            {
                // no parameters specified.  return an empty set
                return (new InvoiceCustDataVector());
            }

            crit.addOrderBy(InvoiceCustDataAccess.INVOICE_NUM);

            int pMaxRows = 1000;
            invoiceCustV = InvoiceCustDataAccess.select(conn, crit, pMaxRows);

            for (int idx = 0; idx < invoiceCustV.size(); idx++)
            {
                InvoiceCustData icd = (InvoiceCustData)invoiceCustV.get(idx);
                if (icd.getCitStatusCd() == null)
                {
                    icd.setCitStatusCd("-");
                }

                if (icd.getCitTransactionDate() == null)
                {
                    icd.setCitTransactionDate(new Date(0));
                }

                if (icd.getInvoiceNum() == null)
                {
                    icd.setInvoiceNum("--");
                }
            }
        }
        catch (Exception e)
        {
            throw new RemoteException("getInvoiceCustCollection: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return invoiceCustV;
    }

    /**
     * Get invoiceCust data according to the invoiceCustId
     * @param pInvoiceCustId the invoiceCust Id to search the invoiceCust
     * @return an <code>InvoiceCustData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public InvoiceCustData getInvoiceCust(int pInvoiceCustId)
    throws RemoteException,
                DataNotFoundException
    {

        InvoiceCustData invoiceCustD = null;
        Connection conn = null;

        try
        {
            conn = getConnection();
            invoiceCustD = InvoiceCustDataAccess.select(conn, pInvoiceCustId);
        }
        catch (DataNotFoundException e)
        {

            //throw e;
        }
        catch (Exception e)
        {
            throw new RemoteException("getInvoiceCust: " + e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return invoiceCustD;
    }

    /**
     * Get invoiceCustDetail data vector according to the orderId
     * @param pOrderId the order Id to search the invoiceCustDetail
     * @return an <code>InvoiceCustDetailDescDataVector</code> value
     * @exception            RemoteException
     */
    public InvoiceCustDetailDataVector getInvoiceCustDetailCollectionByOrder(int pOrderId)
    throws RemoteException
    {

        InvoiceCustDetailDataVector invoiceCustDetailV = new InvoiceCustDetailDataVector();
        Connection conn = null;

        try
        {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            IdVector invoiceCustIdV = new IdVector();

            //get the invoiceCustIdV according to the orderId
            crit.addEqualTo(InvoiceCustDataAccess.ORDER_ID, pOrderId);
            invoiceCustIdV = InvoiceCustDataAccess.selectIdOnly(conn,
                             InvoiceCustDataAccess.INVOICE_CUST_ID,
                             crit);

            // get the invoiceCustDetailV
            if (null != invoiceCustIdV && 0 < invoiceCustIdV.size())
            {
                crit = new DBCriteria();
                crit.addOneOf(InvoiceCustDetailDataAccess.INVOICE_CUST_ID,
                              invoiceCustIdV);
                invoiceCustDetailV = InvoiceCustDetailDataAccess.select(conn,
                                     crit);
            }
        }
        catch (Exception e)
        {
            throw new RemoteException("getInvoiceCustDetailCollectionByOrder: " +
                                      e.getMessage());
        }
        finally
        {

            try
            {

                if (conn != null)
                    conn.close();
            }
            catch (Exception ex)
            {
            }
        }

        return invoiceCustDetailV;
    }

    public InvoiceCustDetailDataVector getInvoiceCustDetailCollection(int pInvoiceCustId)
    throws RemoteException
    {

        InvoiceCustDetailDataVector invoiceCustDetailV = new InvoiceCustDetailDataVector();
        Connection conn = null;

        try
        {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(InvoiceCustDetailDataAccess.INVOICE_CUST_ID,
                            pInvoiceCustId);
            invoiceCustDetailV = InvoiceCustDetailDataAccess.select(conn, crit);
        }
        catch (Exception e)
        {
            throw new RemoteException("getInvoiceCustDetailCollection: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return invoiceCustDetailV;
    }

    /**
     * Get invoiceCustDetailDesc data vector according to the invoiceCustId
     * @param pInvoiceCustId the invoice dist Id to search the invoiceCustDetail
     * @return an <code>InvoiceCustDetailDescDataVector</code> value
     * @exception            RemoteException
     */
    public InvoiceCustDetailDescDataVector getInvoiceCustDetailDescCollection(int pInvoiceCustId)
    throws RemoteException
    {

        InvoiceCustDetailDescDataVector invoiceCustDetailDescV =
            new InvoiceCustDetailDescDataVector();
        Connection conn = null;

        try
        {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();

            // get the invoiceCustData
            InvoiceCustData invoiceCustD = InvoiceCustDataAccess.select(conn,
                                           pInvoiceCustId);
            OrderItemDataVector orderItemV = new OrderItemDataVector();

            if (null != invoiceCustD)
            {

                // geth the orderItemDataVector for this order
                crit = new DBCriteria();
                crit.addEqualTo(OrderItemDataAccess.ORDER_ID,
                                invoiceCustD.getOrderId());
                orderItemV = OrderItemDataAccess.select(conn, crit);
            }

            BusEntityDataVector distV = new BusEntityDataVector();

            // get the OrderItem data for this order status.
            crit = new DBCriteria();
            crit.addEqualTo(InvoiceCustDetailDataAccess.INVOICE_CUST_ID,
                            pInvoiceCustId);

            InvoiceCustDetailDataVector invoiceCustDetailV =
                InvoiceCustDetailDataAccess.select(conn, crit);

            if (null != invoiceCustDetailV &&
                    0 < invoiceCustDetailV.size())
            {
                invoiceCustDetailDescV = new InvoiceCustDetailDescDataVector();

                for (int i = 0; i < invoiceCustDetailV.size(); i++)
                {

                    InvoiceCustDetailDescData invoiceCustDetailDescD =
                        InvoiceCustDetailDescData.createValue();
                    InvoiceCustDetailData invoiceCustDetailD =
                        (InvoiceCustDetailData)invoiceCustDetailV.get(i);
                    invoiceCustDetailDescD.setInvoiceCustDetail(
                        invoiceCustDetailD);

                    if (null != orderItemV && 0 < orderItemV.size())
                    {

                        for (int j = 0; j < orderItemV.size(); j++)
                        {

                            OrderItemData orderItemD = (OrderItemData)orderItemV.get(
                                                           j);

                            if (invoiceCustDetailD.getOrderItemId() == orderItemD.getOrderItemId())
                            {
                                invoiceCustDetailDescD.setOrderItem(orderItemD);

                                // set the distName
                                boolean findDistFlag = false;

                                for (int k = 0; k < distV.size(); k++)
                                {

                                    BusEntityData distD = (BusEntityData)distV.get(
                                                              k);

                                    if (orderItemD.getDistErpNum().equals(distD.getErpNum()))
                                    {
                                        invoiceCustDetailDescD.setDistName(distD.getShortDesc());
                                        findDistFlag = true;

                                        break;
                                    }
                                }

                                if (false == findDistFlag)
                                {
                                    crit = new DBCriteria();
                                    crit.addEqualTo(
                                        BusEntityDataAccess.ERP_NUM,
                                        orderItemD.getDistErpNum());
                                    crit.addEqualToIgnoreCase(
                                        BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                                        RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);

                                    BusEntityDataVector subDistV =
                                        BusEntityDataAccess.select(conn,
                                                                   crit);

                                    if (null != subDistV &&
                                            0 < subDistV.size())
                                    {
                                        invoiceCustDetailDescD.setDistName(((BusEntityData)subDistV.get(
                                                                                0)).getShortDesc());
                                        distV.addAll(subDistV);
                                    }
                                }

                                break;
                            }
                        }
                    }

                    invoiceCustDetailDescV.add(invoiceCustDetailDescD);
                }
            }
        }
        catch (Exception e)
        {
            throw new RemoteException("getInvoiceCustDetailDescCollection: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return invoiceCustDetailDescV;
    }

    /**
     * Get order item detail data, and order status data according to
     * the date range supplied
     * Returns only items in Order_item_detail_table where action == 2.
     * @param DateRangeBegin a <code>String</code> value
     * @param DateRangeEnd a <code>String</code> value
     * @return an <code>OrderHistoryDataVector</code> value
     * @exception            RemoteException
     */
    public OrderHistoryDataVector getOrderHistoryCollection(String DateRangeBegin,
            String DateRangeEnd)
    throws RemoteException
    {

        OrderHistoryDataVector orderHistoryVec = new OrderHistoryDataVector();

        return orderHistoryVec;
    }

    /**
     * Describe <code>addOrder</code> method here.
     *
     * @param pOrderData a <code>OrderData</code> value
     * @return a <code>OrderData</code> value
     * @exception RemoteException if an error occurs
     */
    public OrderData addOrder(OrderData pOrderData)
    throws RemoteException
    {

        return updateOrder(pOrderData);
    }

    /**
     * Updates the Order information values to be used by the request.
     * @param pOrderData  the OrderData .
     * @return a <code>OrderData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public OrderData updateOrder(OrderData pOrderData)
    throws RemoteException
    {

        Connection conn = null;

        try
        {
            conn = getConnection();

            OrderData orderD = pOrderData;

            if (orderD.isDirty())
            {

                if (orderD.getOrderId() == 0)
                {
                    OrderDataAccess.insert(conn, orderD);
                }
                else
                {
                    OrderDataAccess.update(conn, orderD);
                }
            }

            int orderId = pOrderData.getOrderId();
        }
        catch (Exception e)
        {
            throw new RemoteException("updateOrder: " + e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return pOrderData;
    }

    /**
     * Describe <code>addOrderAddress</code> method here.
     *
     * @param pOrderAddressData a <code>OrderAddressData</code> value
     * @return a <code>OrderAddressData</code> value
     * @exception RemoteException if an error occurs
     */
    public OrderAddressData addOrderAddress(OrderAddressData pOrderAddressData)
    throws RemoteException
    {

        return updateOrderAddress(pOrderAddressData);
    }

    /**
     * Updates the OrderAddress information values to be used by the request.
     * @param pOrderAddressData  the OrderAddressData .
     * @return a <code>OrderAddressData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public OrderAddressData updateOrderAddress(OrderAddressData pOrderAddressData)
    throws RemoteException
    {

        Connection conn = null;

        try
        {
            conn = getConnection();

            OrderAddressData orderAddressD = pOrderAddressData;

            if (orderAddressD.isDirty())
            {

                if (orderAddressD.getOrderAddressId() == 0)
                {
                    OrderAddressDataAccess.insert(conn, orderAddressD);
                }
                else
                {
                    OrderAddressDataAccess.update(conn, orderAddressD);
                }
            }

            int orderAddressId = pOrderAddressData.getOrderAddressId();
        }
        catch (Exception e)
        {
            throw new RemoteException("updateOrderAddress: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return pOrderAddressData;
    }

    /**
     * Describe <code>addOrderItem</code> method here.
     *
     * @param pOrderItemData a <code>OrderItemData</code> value
     * @return a <code>OrderItemData</code> value
     * @exception RemoteException if an error occurs
     */
    public OrderItemData addOrderItem(OrderItemData pOrderItemData)
    throws RemoteException
    {

        return updateOrderItem(pOrderItemData);
    }

    /**
     * Updates the OrderItem information values to be used by the request.
     * @param pOrderItemData  the OrderItemData .
     * @return a <code>OrderItemData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public OrderItemData updateOrderItem(OrderItemData pOrderItemData)
    throws RemoteException
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            return updateAddOrderItemWorker(conn,pOrderItemData);
        }catch (Exception e){
           throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }


    private OrderItemData updateAddOrderItemWorker(Connection pCon, OrderItemData pOrderItemData)
    throws Exception{

            if (pOrderItemData.isDirty())
            {

                if (pOrderItemData.getOrderItemId() == 0)
                {
                    pOrderItemData = OrderItemDataAccess.insert(pCon, pOrderItemData);
                }
                else
                {
                    OrderDAO.updateOrderItem(pCon,pOrderItemData);
                }
            }

        return pOrderItemData;
    }



    /**
     * Updates the OrderItem collection information values to be used by the request.
     * @param pOrderItemDataVector  the OrderItemDataVector .
     * @return a <code>OrderItemDataVector</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public OrderItemDataVector updateOrderItemCollection(OrderItemDataVector pOrderItemDataVector)
    throws RemoteException
    {

        OrderItemDataVector newOrderItemDataV = new OrderItemDataVector();
        Connection conn = null;

        if (null != pOrderItemDataVector && !pOrderItemDataVector.isEmpty()){

            try{
                conn = getConnection();

                Iterator it = pOrderItemDataVector.iterator();
                while(it.hasNext())
                {
                    OrderItemData itemD = (OrderItemData) it.next();
                    newOrderItemDataV.add(updateAddOrderItemWorker(conn,itemD));
                }
            }catch (Exception e){
                processException(e);
            }finally{
                closeConnection(conn);
            }
        }

        return newOrderItemDataV;
    }

    /**
     * Updates consolidated order item collection
     * @param pOrderItemDataVector  the OrderItemDataVector .
     * @param pReplacedOrderItems  replaced order items with modified quantities
     * @return a <code>OrderItemDataVector</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public OrderItemDataVector updateOrderItemCollection
          (OrderItemDataVector pOrderItemDataVector,
           ReplacedOrderItemViewVector pReplacedOrderItems,
           String pUserName)
    throws RemoteException
    {

        OrderItemDataVector newOrderItemDataV = new OrderItemDataVector();
        Connection conn = null;
        try{
          conn = getConnection();
          DBCriteria dbc;

          OrderDAO.updateReplacedOrderItems(conn,pOrderItemDataVector, pReplacedOrderItems,pUserName);

          for(Iterator it = pOrderItemDataVector.iterator();it.hasNext(); )  {
            OrderItemData itemD = (OrderItemData) it.next();
            newOrderItemDataV.add(updateAddOrderItemWorker(conn,itemD));
          }
        }catch (Exception e){
           processException(e);
        }finally{
           closeConnection(conn);
        }

        return newOrderItemDataV;
    }



    /**
     * Updates the InvoiceDistDetail collection information values to be used by the request.
     * @param pInvoiceDistDetailDataVector  the InvoiceDistDetailDataVector .
     * @return a <code>InvoiceDistDetailDataVector</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public InvoiceDistDetailDataVector updateInvoiceDistDetailCollection(InvoiceDistDetailDataVector pInvoiceDistDetailDataVector)
    throws RemoteException
    {

        InvoiceDistDetailDataVector newInvoiceDistDetailDataV =
            new InvoiceDistDetailDataVector();
        Connection conn = null;

        if (null != pInvoiceDistDetailDataVector &&
                0 < pInvoiceDistDetailDataVector.size())
        {

            try
            {
                conn = getConnection();

                for (int i = 0; i < pInvoiceDistDetailDataVector.size(); i++)
                {

                    InvoiceDistDetailData detailD = (InvoiceDistDetailData)pInvoiceDistDetailDataVector.get(
                                                        i);

                    if (detailD.isDirty())
                    {
                        if (detailD.getInvoiceDistDetailId() == 0)
                        {
                            InvoiceDistDetailDataAccess.insert(conn, detailD);
                        }
                        else
                        {
                            InvoiceDistDetailDataAccess.update(conn, detailD);
                        }
                    }

                    newInvoiceDistDetailDataV.add(detailD);
                }
            }
            catch (Exception e)
            {
                throw new RemoteException("updateInvoiceDistDetailCollection: " +
                                          e.getMessage());
            }
            finally
            {
                closeConnection(conn);
            }
        }

        return newInvoiceDistDetailDataV;
    }

    /**
     * Updates the InvoiceCustDetail collection information values to be used by the request.
     * @param pInvoiceCustDetailDataVector  the InvoiceCustDetailDataVector .
     * @return a <code>InvoiceCustDetailDataVector</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public InvoiceCustDetailDataVector updateInvoiceCustDetailCollection(InvoiceCustDetailDataVector pInvoiceCustDetailDataVector)
    throws RemoteException
    {

        InvoiceCustDetailDataVector newInvoiceCustDetailDataV =
            new InvoiceCustDetailDataVector();
        Connection conn = null;

        if (null != pInvoiceCustDetailDataVector &&
                0 < pInvoiceCustDetailDataVector.size())
        {

            try
            {
                conn = getConnection();

                for (int i = 0; i < pInvoiceCustDetailDataVector.size(); i++)
                {

                    InvoiceCustDetailData detailD = (InvoiceCustDetailData)pInvoiceCustDetailDataVector.get(
                                                        i);

                    if (detailD.isDirty())
                    {

                        if (detailD.getInvoiceCustDetailId() == 0)
                        {
                            InvoiceCustDetailDataAccess.insert(conn, detailD);
                        }
                        else
                        {
                            InvoiceCustDetailDataAccess.update(conn, detailD);
                        }
                    }

                    newInvoiceCustDetailDataV.add(detailD);
                }
            }
            catch (Exception e)
            {
                throw new RemoteException("updateInvoiceCustDetailCollection: " +
                                          e.getMessage());
            }
            finally
            {
                closeConnection(conn);
            }
        }

        return newInvoiceCustDetailDataV;
    }

    /**
     * Describe <code>addOrderItemAction</code> method here.
     *
     * @param pOrderItemActionData a <code>OrderItemActionData</code> value
     * @return a <code>OrderItemActionData</code> value
     * @exception RemoteException if an error occurs
     */
    public OrderItemActionData addOrderItemAction(OrderItemActionData pOrderItemActionData)
    throws RemoteException
    {

        return updateOrderItemAction(pOrderItemActionData);
    }

    /**
     * Updates the OrderItemAction information values to be used by the request.
     * @param pOrderItemActionData  the OrderItemActionData .
     * @return a <code>OrderItemActionData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public OrderItemActionData updateOrderItemAction(OrderItemActionData pOrderItemActionData)
    throws RemoteException
    {

        Connection conn = null;

        try
        {
            conn = getConnection();

            OrderItemActionData itemAction = pOrderItemActionData;

            if (itemAction.isDirty())
            {

                if (itemAction.getOrderItemActionId() == 0)
                {
                    OrderItemActionDataAccess.insert(conn, itemAction);
                }
                else
                {
                    OrderItemActionDataAccess.update(conn, itemAction);
                }
            }

            int itemActionId = pOrderItemActionData.getOrderItemActionId();
        }
        catch (Exception e)
        {
            throw new RemoteException("updateOrderItemAction: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return pOrderItemActionData;
    }

    /**
     * Describe <code>addItemSubstitution</code> method here.
     *
     * @param pItemSubstitutionData a <code>ItemSubstitutionData</code> value
     * @return a <code>ItemSubstitutionData</code> value
     * @exception RemoteException if an error occurs
     */
    public ItemSubstitutionData addItemSubstitution(ItemSubstitutionData pItemSubstitutionData)
    throws RemoteException
    {

        return updateItemSubstitution(pItemSubstitutionData);
    }

    /**
     * Updates the ItemSubstitution information values to be used by the request.
     * @param pItemSubstitutionData  the ItemSubstitutionData .
     * @return a <code>ItemSubstitutionData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemSubstitutionData updateItemSubstitution(ItemSubstitutionData pItemSubstitutionData)
    throws RemoteException
    {

        Connection conn = null;

        try
        {
            conn = getConnection();

            ItemSubstitutionData itemSubstitution = pItemSubstitutionData;

            if (itemSubstitution.isDirty())
            {

                if (itemSubstitution.getItemSubstitutionId() == 0)
                {
                    ItemSubstitutionDataAccess.insert(conn, itemSubstitution);
                }
                else
                {
                    ItemSubstitutionDataAccess.update(conn, itemSubstitution);
                }
            }

            int itemSubstitutionId = pItemSubstitutionData.getItemSubstitutionId();
        }
        catch (Exception e)
        {
            throw new RemoteException("updateItemSubstitution: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return pItemSubstitutionData;
    }



    /**
     * Describe <code>addInvoiceDistDetail</code> method here.
     *
     * @param pInvoiceDistDetailData a <code>InvoiceDistDetailData</code> value
     * @return a <code>InvoiceDistDetailData</code> value
     * @exception RemoteException if an error occurs
     */
    public InvoiceDistDetailData addInvoiceDistDetail(InvoiceDistDetailData pInvoiceDistDetailData)
    throws RemoteException
    {

        return updateInvoiceDistDetail(pInvoiceDistDetailData);
    }

    /**
     * Updates the InvoiceDistDetail information values to be used by the request.
     * @param pInvoiceDistDetailData  the InvoiceDistDetailData .
     * @return a <code>InvoiceDistDetailData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public InvoiceDistDetailData updateInvoiceDistDetail(InvoiceDistDetailData pInvoiceDistDetailData)
    throws RemoteException
    {

        Connection conn = null;

        try
        {
            conn = getConnection();

            InvoiceDistDetailData invoiceDistDetail = pInvoiceDistDetailData;

            if (invoiceDistDetail.isDirty())
            {

                if (invoiceDistDetail.getInvoiceDistDetailId() == 0)
                {
                    InvoiceDistDetailDataAccess.insert(conn, invoiceDistDetail);
                }
                else
                {
                    InvoiceDistDetailDataAccess.update(conn, invoiceDistDetail);
                }
            }

            int invoiceDistDetailId = pInvoiceDistDetailData.getInvoiceDistDetailId();
        }
        catch (Exception e)
        {
            throw new RemoteException("updateInvoiceDistDetail: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return pInvoiceDistDetailData;
    }

    /**
     * Describe <code>addInvoiceCust</code> method here.
     *
     * @param pInvoiceCustData a <code>InvoiceCustData</code> value
     * @return a <code>InvoiceCustData</code> value
     * @exception RemoteException if an error occurs
     */
    public InvoiceCustData addInvoiceCust(InvoiceCustData pInvoiceCustData)
    throws RemoteException
    {

        return updateInvoiceCust(pInvoiceCustData);
    }

    /**
     * Updates the InvoiceCust information values to be used by the request.
     * @param pInvoiceCustData  the InvoiceCustData .
     * @return a <code>InvoiceCustData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public InvoiceCustData updateInvoiceCust(InvoiceCustData pInvoiceCustData)
    throws RemoteException
    {

        Connection conn = null;

        try
        {
            conn = getConnection();

            InvoiceCustData invoiceCust = pInvoiceCustData;

            if (invoiceCust.isDirty())
            {

                if (invoiceCust.getInvoiceCustId() == 0)
                {
                    InvoiceCustDataAccess.insert(conn, invoiceCust);
                }
                else
                {
                    InvoiceCustDataAccess.update(conn, invoiceCust);
                }
            }

            int invoiceCustId = pInvoiceCustData.getInvoiceCustId();
        }
        catch (Exception e)
        {
            throw new RemoteException("updateInvoiceCust: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return pInvoiceCustData;
    }

    /**
     * Get address data according to the addressId
     * @param pAddressId the address Id to search the address
     * @return an <code>AddressData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public AddressData getAddress(int pAddressId, String pAddressTypeCd)
    throws RemoteException, DataNotFoundException
    {

        AddressData addressD = null;
        Connection conn = null;

        try
        {
            conn = getConnection();
            addressD = AddressDataAccess.select(conn, pAddressId);

            if (null != addressD)
            {

                if (null != pAddressTypeCd &&
                        !"".equals(pAddressTypeCd.trim()))
                {

                    if (!pAddressTypeCd.equals(addressD.getAddressTypeCd()))
                    {
                        addressD = null;
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new RemoteException("getAddress: " + e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return addressD;
    }

    /**
     * Gets the current backorders in the system.
     * @param sqlBuf a <code>StringBuffer</code> value
     * @return a <code>OrderHistoryDataVector</code> value
     * @exception    RemoteException Required by EJB 1.0
     */
    public OrderHistoryDataVector getTroubledOrders(StringBuffer sqlBuf)
    throws RemoteException
    {

        Connection conn = null;
        OrderHistoryDataVector orderHistoryVec = new OrderHistoryDataVector();

        // Get all orders that have an exception indicator set to 'Y'.
        return orderHistoryVec;
    }

    private OrderJoinData readOrderFromDb(Connection pConn, int pOrderId)
        throws Exception
    {

        OrderJoinData ojd = new OrderJoinData();
        BusEntityDAO beDAO =  mCacheManager.getBusEntityDAO();
        Store storeEjb=APIAccess.getAPIAccess().getStoreAPI();

        try       {
            OrderData od = OrderDataAccess.select(pConn, pOrderId);
            ojd.setOrder(od);

            int storeId = od.getStoreId();
            StoreData storeData = null;

            try {
                storeData=storeEjb.getStore(storeId);
            } catch (DataNotFoundException e) {
                e.printStackTrace();
            }

            try{
                UserData placedBy = getCachedUserByName(pConn, od);
    if ( null != placedBy ) {
        ojd.setPlacedByFirstName(placedBy.getFirstName());
        ojd.setPlacedByLastName(placedBy.getLastName());
    } else {
        ojd.setPlacedByFirstName("");
        ojd.setPlacedByLastName("");
    }
            }catch(DataNotFoundException e){
                //user who placed order no longer exists or
                //system generated order
            }

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(OrderMetaDataAccess.ORDER_ID, pOrderId);

            OrderMetaDataVector omdv = OrderMetaDataAccess.select(pConn, dbc);
            ojd.setOrderMeta(omdv);

            //Order properties
            dbc = new DBCriteria();
            dbc.addEqualTo(OrderPropertyDataAccess.ORDER_ID, pOrderId);
            OrderPropertyDataVector opdv = OrderPropertyDataAccess.select(pConn,dbc);
            ojd.setOrderProperties(opdv);

            // Order ship to and bill to information.
            dbc = new DBCriteria();
            dbc.addEqualTo(OrderAddressDataAccess.ORDER_ID, pOrderId);

            OrderAddressDataVector oadv =
                OrderAddressDataAccess.select(pConn,dbc);

            for (int ia = 0; ia < oadv.size(); ia++)
            {

                OrderAddressData oa = (OrderAddressData)oadv.get(ia);

                if (oa.getAddressTypeCd().equals(
                            RefCodeNames.ADDRESS_TYPE_CD.SHIPPING))
                {
                    ojd.setShipTo(oa);
                }
                else if (oa.getAddressTypeCd().equals(
                             RefCodeNames.ADDRESS_TYPE_CD.BILLING))
                {
                    ojd.setBillTo(oa);
                }
            }


            // Shopping history.
            List orderHistory = ShoppingDAO.getOrderHistory(pConn, pOrderId);

            // Order items and item meta data.
            OrderItemJoinDataVector oijdv = new OrderItemJoinDataVector();
            dbc = new DBCriteria();
            dbc.addEqualTo(OrderItemDataAccess.ORDER_ID, pOrderId);
            OrderItemDataVector oidv = OrderItemDataAccess.select(pConn, dbc);

            HashSet distrErpSet = new HashSet();
            HashMap orderFreigthMap = new HashMap();
            HashMap distBusEntityMap = new HashMap();

            for (int i = 0; i < oidv.size(); i++)              {

                OrderItemData oid = (OrderItemData)oidv.get(i);
                OrderItemJoinData oijd = new OrderItemJoinData(oid);

                if(storeData!=null
                        && storeData.getStoreType()!=null
                        && RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(storeData.getStoreType().getValue())) {

                    String distErpNum = oid.getDistErpNum();
                    if(distErpNum!=null && !distrErpSet.contains(distErpNum)){
                        distrErpSet.add(distErpNum);
                        if(!distBusEntityMap.containsKey(distErpNum)){
                            BusEntityData distBusEntity = beDAO.getDistributorByErpNum(pConn, distErpNum);
                            distBusEntityMap.put(distErpNum,distBusEntity);
                        }
                    }

                    BusEntityData distBusEntity = (BusEntityData) distBusEntityMap.get(distErpNum);
                    int distId = (distBusEntity==null)?0:distBusEntity.getBusEntityId();
                    if(distId >0 && !orderFreigthMap.containsKey(new Integer(distId))) {

                        DBCriteria crit = new DBCriteria();
                        crit.addEqualTo(OrderFreightDataAccess.BUS_ENTITY_ID, distId);
                        crit.addEqualTo(OrderFreightDataAccess.ORDER_ID, pOrderId);
                        OrderFreightDataVector ofrt = OrderFreightDataAccess.select(pConn, crit);

                        if (ofrt!=null && ofrt.size()>0){
                            OrderFreightData orderFreigth= (OrderFreightData) ofrt.get(0);
                            orderFreigthMap.put(new Integer(distId),orderFreigth);
                        }
                    }
                    oijd.setOrderFreight((OrderFreightData) orderFreigthMap.get(new Integer(distId)));
                }

                oijd.setShoppingHistory(orderHistory);

                dbc = new DBCriteria();
                dbc.addEqualTo(OrderItemDataAccess.ORDER_ITEM_ID,
                               oid.getOrderItemId());

                OrderItemMetaDataVector oimdv = OrderItemMetaDataAccess.select(
                                                    pConn, dbc);
                oijd.setOrderItemMeta(oimdv);

                for(Iterator iter = oimdv.iterator(); iter.hasNext();) {
                    OrderItemMetaData oimD = (OrderItemMetaData) iter.next();
                    if(RefCodeNames.ORDER_ITEM_META_NAME.STANDARD_PRODUCT_LIST.
                                                      equals(oimD.getName())) {
                       oijd.setStandardProductList(oimD.getValue());
                       break;
                    }
                }
                oijdv.add(oijd);
            }

            // Vector of all.
            ojd.setOrderJoinItems(oijdv);
            // If consolidated order set replaced
            if(RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(od.getOrderTypeCd())){
              ReplacedOrderViewVector replOrderVwV = getReplacedOrders(pConn,pOrderId);
              ojd.setReplacedOrders(replOrderVwV);
            }
            //Get consolidated order if the order was consolidated
            if(RefCodeNames.ORDER_STATUS_CD.CANCELLED.
                                    equals(od.getOrderStatusCd())) {
              OrderData consolidatedOrderD =
                            getConsolidatedOrderForReplaced(pConn, pOrderId);

              ojd.setConsolidatedOrder(consolidatedOrderD);
            }

            //Get CC info
            dbc = new DBCriteria();
            dbc.addEqualTo(OrderCreditCardDataAccess.ORDER_ID, pOrderId);
            OrderCreditCardDataVector occDV =
                    OrderCreditCardDataAccess.select(pConn,dbc);
            if(occDV.size()>0) {
              OrderCreditCardData occD = (OrderCreditCardData) occDV.get(0);
              occD.setEncryptedCreditCardNumber(null);
              occD.setEncryptionAlgorithm(null);
              occD.setHashAlgorithm(null);
              occD.setHashedCreditCardNumber(null);
              ojd.setOrderCC(occD);
            }
        }
        catch (Exception e)
        {
            logError("readOrderFromDb error: " + e);
            throw e;
        }

        return ojd;
    }

    private OrderData getConsolidatedOrderForReplaced(Connection pConn, int pOrderId)
    throws Exception
    {
      DBCriteria dbc = new DBCriteria();
      OrderData consolidatedOrderD = null;
      dbc.addEqualTo(OrderAssocDataAccess.ORDER1_ID,pOrderId);
      dbc.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_CD,
                              RefCodeNames.ORDER_ASSOC_CD.CONSOLIDATED);
      dbc.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_STATUS_CD,
                              RefCodeNames.ORDER_ASSOC_STATUS_CD.ACTIVE);
      IdVector consolidatedOrderIdV =
           OrderAssocDataAccess.selectIdOnly(pConn,
                             OrderAssocDataAccess.ORDER2_ID,dbc);
      if(consolidatedOrderIdV.size()>0) {
        Integer consolidatedOrderIdI =
                            (Integer) consolidatedOrderIdV.get(0);
        consolidatedOrderD =
           OrderDataAccess.select(pConn,consolidatedOrderIdI.intValue());
      }
       return consolidatedOrderD;
    }

    public OrderJoinData fetchOrder(int pOrderId)
    throws RemoteException
    {

        OrderJoinData ojd = new OrderJoinData();
        Connection conn = null;

        try
        {
            conn = getConnection();
            ojd = readOrderFromDb(conn, pOrderId);
        }
        catch (Exception e)
        {
      e.printStackTrace();
            String msg = "Problem getting information for order id: " +
                         pOrderId;
            logError("fetchOrder error: " + e + " " + msg);
            throw new RemoteException(msg);
        }
        finally
        {
            closeConnection(conn);
        }

        return ojd;
    }

    public OrderJoinData setPendingDate(int pOrderId, String pStringDate,
                                         String pModUserName)
    throws RemoteException, DataNotFoundException
    {

      Connection con = null;
      try
      {
    con = getConnection();
    // Verify the current status allows an update.
    OrderData currOrderData = OrderDataAccess.select(con,pOrderId);
    String currStatus = currOrderData.getOrderStatusCd();
    if ( currStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE) ||
         currStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)
         )	    {
        // This is allowed.
        logInfo( "setPendingDate, update pOrderId=" + pOrderId +
           " to pStringDate=" + pStringDate);
    } else {
        logError( "setPendingDate, update pOrderId=" + pOrderId +
      " to pStringDate=" + pStringDate
      + " currStatus=" + currStatus );

        throw new RemoteException
      ("Error. "+
       " This order is already being processed");

    }


    //change status
    OrderJoinData ojd = updateOrderInfo(pOrderId,
                RefCodeNames.ORDER_STATUS_CD.PENDING_DATE, pModUserName);


    //Generate order_meta (remove old one first
    DBCriteria dbc = new DBCriteria();
    dbc.addEqualTo(OrderMetaDataAccess.ORDER_ID,pOrderId);
    dbc.addEqualTo(OrderMetaDataAccess.NAME,
       RefCodeNames.ORDER_PROPERTY_TYPE_CD.PENDING_DATE);
    OrderMetaDataAccess.remove(con,dbc);

    OrderMetaData omD = OrderMetaData.createValue();
    omD.setValue(pStringDate);
    omD.setOrderId(pOrderId);
    omD.setName(RefCodeNames.ORDER_PROPERTY_TYPE_CD.PENDING_DATE);
    omD.setAddBy(pModUserName);
    omD.setModBy(pModUserName);

    omD = OrderMetaDataAccess.insert(con, omD);

    dbc = new DBCriteria();
    dbc.addEqualTo(OrderMetaDataAccess.ORDER_ID,ojd.getOrder().getOrderId());
    OrderMetaDataVector omDV = OrderMetaDataAccess.select(con,dbc);
    ojd.setOrderMeta(omDV);

    return ojd;
      }catch(Exception exc) {
    exc.printStackTrace();
    throw new RemoteException("Error. "+exc.getMessage());
      }
      finally{
    closeConnection(con);
      }
    }


    public OrderJoinData updateOrderInfo(int pOrderId, String pUpdateReq,
                                         String pModUserName)
    throws RemoteException,
                DataNotFoundException
    {

        OrderJoinData ojd = new OrderJoinData();
        Connection conn = null;

        try
        {
            conn = getConnection();


            if (pUpdateReq.equals(RefCodeNames.ORDER_STATUS_CD.ORDERED) ||
                pUpdateReq.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL) ||
                pUpdateReq.equals(RefCodeNames.ORDER_STATUS_CD.CANCELLED) ||
                pUpdateReq.equals(RefCodeNames.ORDER_STATUS_CD.REJECTED) ||
                pUpdateReq.equals(RefCodeNames.ORDER_STATUS_CD.DUPLICATED) ||
                pUpdateReq.equals(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED) ||
                pUpdateReq.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION) ||
                pUpdateReq.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE) ||
                pUpdateReq.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW) ||
                pUpdateReq.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW))
            {

                OrderData currOrderData = OrderDataAccess.select(conn,pOrderId);
                String updq = "update " + OrderDataAccess.CLW_ORDER +
                    " set " + OrderDataAccess.ORDER_STATUS_CD +
                    " = '" + pUpdateReq + "', " +
                    OrderDataAccess.REVISED_ORDER_DATE + " = sysdate , " +
                    OrderDataAccess.MOD_DATE + " = sysdate , " +
                    OrderDataAccess.MOD_BY +
                    //" = '" + pModUserName + "' ";
                    " = ? ";
                if ( currOrderData.getOrderStatusCd().equals
                     (RefCodeNames.ORDER_STATUS_CD.PENDING_DATE)){
                    updq += " , " + OrderDataAccess.ORIGINAL_ORDER_DATE
                        + " = sysdate ";
                }
                updq += " where " +
                    //OrderDataAccess.ORDER_ID + " = " + pOrderId;
                    OrderDataAccess.ORDER_ID + " = ? ";

                if (pUpdateReq.equals(RefCodeNames.ORDER_STATUS_CD.ORDERED) ||
                    pUpdateReq.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE) )
                {
                    // Only allow a transition to the ordered state
                    // if the order has not been sent to Lawson.
                    updq += " and ( " + OrderDataAccess.ERP_ORDER_NUM +
                            " is null or " +
                            OrderDataAccess.ERP_ORDER_NUM + " = 0 ) ";

        // Here are the allowed initial statuses.
        // This clause is in place in case 2 updates are
        // going on for the same order at the same
        // time.
        //
        updq += " and " + OrderDataAccess.ORDER_STATUS_CD
      + " in ( '"
      + RefCodeNames.ORDER_STATUS_CD.PENDING_DATE
      + "','"
      + RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL
      + "','"
      + RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW
      + "','"
      + RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW
      + "','"
      + RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED
      + "','"
      + RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION
      + "' ) ";
                }

                PreparedStatement stmt = conn.prepareStatement(updq);
                logInfo("update SQL: " + updq);
                stmt.setString(1, pModUserName);
                stmt.setInt(2, pOrderId);

                if ( stmt.executeUpdate() > 0 )
                {

                    // Add a note to record the status update on the order.
                    OrderPropertyData OPD =
                        OrderPropertyData.createValue();
                    Date now = new Date();
                    String notemsg =
      "Status changed to: " + pUpdateReq ;

        if ( !pUpdateReq.equals(currOrderData.getOrderStatusCd())) {
      notemsg += ", the previous status was: " +
          currOrderData.getOrderStatusCd()
          ;
        }

                    OPD.setOrderId(pOrderId);
                    OPD.setShortDesc("Order Status Update");
                    OPD.setValue(notemsg);
                    OPD.setOrderPropertyStatusCd("ACTIVE");
                    OPD.setOrderPropertyTypeCd
                    (RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
                    OPD.setAddBy(pModUserName);
                    OPD.setModBy(pModUserName);
                    addNote(OPD);
                }
                if(pUpdateReq.equals(RefCodeNames.ORDER_STATUS_CD.CANCELLED)) {
                  DBCriteria dbc = new DBCriteria();
                  dbc.addEqualTo(InventoryOrderLogDataAccess.ORDER_ID,pOrderId);
                  InventoryOrderLogDataAccess.remove(conn,dbc);
                }
                if(pUpdateReq.equals(RefCodeNames.ORDER_STATUS_CD.CANCELLED) ||
                        pUpdateReq.equals(RefCodeNames.ORDER_STATUS_CD.REJECTED) ||	
                        pUpdateReq.equals(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED) ||
                        pUpdateReq.equals(RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED)) {
                    DBCriteria dbc = new DBCriteria();
                    dbc.addEqualTo(WorkflowQueueDataAccess.ORDER_ID,pOrderId);
                    
                    log.info(".updateOrderInfo()=> AAAAA Removing workflow queue record.");
                    
                    WorkflowQueueDataAccess.remove(conn,dbc);
                }
            }
            else
            {
                String msg = "Unknown update request: " + pUpdateReq;
                logError("updateOrderInfo error: " + msg);
                throw new Exception(msg);
            }

            ojd = readOrderFromDb(conn, pOrderId);
        }
        catch (Exception e)
        {

            String msg = "updateOrderInfo " +
                         "Problem updating information for order id: " +
                         pOrderId;
            logError("updateOrderInfo error: " + e + " " + msg);
            throw new RemoteException(msg);
        }
        finally
        {
            closeConnection(conn);
        }

        return ojd;
    }


    /**
     * Approves orders having PENDING_APPROVAL or PENDING_DATE status
     *
     * @param pOrderId an <code>int</code> value
     * @param pPropertyIdV list of approved properties
     * @param pPendingDate approve on date (null if immediately
     * @param pRequestPoNum customer PO number
     * @param pUserId the user id
     * @param pUserName the user login name
     * @return an <code>OrderJoinData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     */
    public OrderJoinData approveOrder(int pOrderId,
            IdVector pPropertyIdV,
            Date pPendingDate,
            String pRequestPoNum,
            int pUserId, String pUserName)
    throws RemoteException
    {
        Connection conn = null;

        log.info("approveOrder(): pPropertyIdV.size() = " + pPropertyIdV.size());
        log.info("approveOrder(): pPendingDate = " + pPendingDate);
        
        try
        {
            conn = getConnection();
            Date currDate = new Date();
            OrderData orderD = OrderDataAccess.select(conn,pOrderId);
            String oldOrderStatus = orderD.getOrderStatusCd();
            if (!RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL.equals(oldOrderStatus) &&
                !RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(oldOrderStatus)) {
              return readOrderFromDb(conn, pOrderId);
            }
            
            log.info("approveOrder(): orderD1 = " + orderD);
            
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(OrderPropertyDataAccess.ORDER_ID,pOrderId);
            dbc.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
               RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
            dbc.addEqualTo(OrderPropertyDataAccess.SHORT_DESC, Constants.WORKFLOW_NOTE);
            dbc.addIsNull(OrderPropertyDataAccess.APPROVE_DATE);

            OrderPropertyDataVector workflowNotes =
                                 OrderPropertyDataAccess.select(conn,dbc);
            
            log.info("approveOrder(): workflowNotes = " + workflowNotes);
            
            for(Iterator iter=workflowNotes.iterator(); iter.hasNext();) {
              OrderPropertyData opD = (OrderPropertyData) iter.next();
              int orderPropId = opD.getOrderPropertyId();
              for(Iterator iter1=pPropertyIdV.iterator(); iter1.hasNext();) {
                Integer opIdI = (Integer) iter1.next();
                if(orderPropId==opIdI.intValue()) {
                  opD.setApproveDate(currDate);
                  opD.setApproveUserId(pUserId);
                  opD.setModBy(pUserName);
                  log.info("approveOrder(): will update clw_order_property table =>  opD = " + opD);
                  OrderPropertyDataAccess.update(conn,opD);
                  iter1.remove();
                  iter.remove();
                  break;
                }
              }
            }
            if(workflowNotes.size()==0) {
              if(pPendingDate==null || pPendingDate.before(currDate)) {
                orderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.ORDERED);
                if(RefCodeNames.WORKFLOW_IND_CD.INTERRUPTED.equals(orderD.getWorkflowInd())) {
                  orderD.setWorkflowInd(RefCodeNames.WORKFLOW_IND_CD.TO_RESUME);
                }
                dbc = new DBCriteria();
                dbc.addEqualTo(OrderMetaDataAccess.ORDER_ID,pOrderId);
                dbc.addEqualTo(OrderMetaDataAccess.NAME,
                     RefCodeNames.ORDER_PROPERTY_TYPE_CD.PENDING_DATE);
                OrderMetaDataAccess.remove(conn,dbc);
              } else {
                log.info("approveOrder(): pPendingDate != null && pPendingDate.before(currDate)");
                orderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE);
                SimpleDateFormat sdf = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN);
                String pendingDateS = sdf.format(pPendingDate);
                dbc = new DBCriteria();
                dbc.addEqualTo(OrderMetaDataAccess.ORDER_ID,pOrderId);
                dbc.addEqualTo(OrderMetaDataAccess.NAME,
                     RefCodeNames.ORDER_PROPERTY_TYPE_CD.PENDING_DATE);
                OrderMetaDataVector omDV = OrderMetaDataAccess.select(conn,dbc);
                
                log.info("approveOrder(): omDV = " + omDV);
                
                if(omDV.size()==0) {
                  OrderMetaData omD = OrderMetaData.createValue();
                  omD.setOrderId(pOrderId);
                  omD.setName(RefCodeNames.ORDER_PROPERTY_TYPE_CD.PENDING_DATE);
                  omD.setValue(pendingDateS);
                  omD.setAddBy(pUserName);
                  omD.setModBy(pUserName);
                  omD = OrderMetaDataAccess.insert(conn,omD);

                  OrderPropertyData opD = OrderPropertyData.createValue();
                  String notemsg = "Set order approve date : " + pendingDateS;
                  opD.setOrderId(pOrderId);
                  opD.setShortDesc("Approve Date Update");
                  opD.setValue(notemsg);
                  opD.setOrderPropertyStatusCd("ACTIVE");
                  opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
                  opD.setAddBy(pUserName);
                  opD.setModBy(pUserName);
                  
                  log.info("approveOrder(): opD1 = " + opD);
                  
                  OrderPropertyDataAccess.insert(conn,opD);

                } else {
                  for(int ii=0; ii<omDV.size(); ii++) {
                    OrderMetaData omD = (OrderMetaData) omDV.get(ii);
                    String oldPendingDateS = omD.getValue();
                    if(ii==0) {
                      if(!pendingDateS.equals(oldPendingDateS)) {
                        omD.setValue(pendingDateS);
                        omD.setModBy(pUserName);
                        OrderMetaDataAccess.update(conn,omD);

                        OrderPropertyData opD = OrderPropertyData.createValue();
                        String notemsg = "Approve date changed to: " + pendingDateS+
                         ", the previous approve date was: " + oldPendingDateS;

                        opD.setOrderId(pOrderId);
                        opD.setShortDesc("Approve Date Update");
                        opD.setValue(notemsg);
                        opD.setOrderPropertyStatusCd("ACTIVE");
                        opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
                        opD.setAddBy(pUserName);
                        opD.setModBy(pUserName);
                        OrderPropertyDataAccess.insert(conn,opD);
                      }
                    } else {
                       OrderMetaDataAccess.remove(conn,omD.getOrderMetaId());
                    }
                  }
                }
              }
            }
            if(Utility.isSet(pRequestPoNum) && !pRequestPoNum.equals(orderD.getRequestPoNum())) {
              String oldRequestPoNum = orderD.getRequestPoNum();
              orderD.setRequestPoNum(pRequestPoNum);
              String notemsg = null;
              if(Utility.isSet(oldRequestPoNum)) {
                notemsg = "Order PO number changed to: " + pRequestPoNum+
                 ", the previous PO number was: " + oldRequestPoNum;
              } else {
                notemsg = "Order PO number set to: " + pRequestPoNum;
              }
              OrderPropertyData opD = OrderPropertyData.createValue();
              opD.setOrderId(pOrderId);
              opD.setShortDesc("PO Number Update");
              opD.setValue(notemsg);
              opD.setOrderPropertyStatusCd("ACTIVE");
              opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
              opD.setAddBy(pUserName);
              opD.setModBy(pUserName);
              OrderPropertyDataAccess.insert(conn,opD);
            }
            orderD.setModBy(pUserName);
            Date revDate = new Date();
            orderD.setRevisedOrderDate(revDate);
            orderD.setRevisedOrderTime(revDate);
            
            log.info("approveOrder(): orderD2 = " + orderD);
            
            OrderDataAccess.update(conn,orderD);
            if(!oldOrderStatus.equals(orderD.getOrderStatusCd())) {
              OrderPropertyData opD = OrderPropertyData.createValue();
              String notemsg = "Status changed to: " + orderD.getOrderStatusCd()+
               ", the previous status was: " + oldOrderStatus;

              opD.setOrderId(pOrderId);
              opD.setShortDesc("Order Status Update");
              opD.setValue(notemsg);
              opD.setOrderPropertyStatusCd("ACTIVE");
              opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
              opD.setAddBy(pUserName);
              opD.setModBy(pUserName);
              OrderPropertyDataAccess.insert(conn,opD);
            }
           OrderJoinData ojd = readOrderFromDb(conn, pOrderId);
           return ojd;
        }
        catch (Exception exc)
        {
          exc.printStackTrace();
          throw new RemoteException(exc.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

    }
    
    /**
     * Get InvoiceCreditCardTransViewVector according to the supplied criteria
     * @param pOrderCreditCreditCardId the criteria
     * @return an <code>InvoiceCreditCardTransViewVector</code> value
     * @exception            RemoteException
     */
    public InvoiceCreditCardTransViewVector getInvoiceCCTransList(int pOrderCreditCreditCardId)
    	throws RemoteException{
            Connection con = null;
            try {
                con = getConnection();
                CreditCardDAO ccDao = new CreditCardDAO(con);
                return ccDao.getInvoiceCCTransList(pOrderCreditCreditCardId);
            }catch(Exception e){
                e.printStackTrace();
                throw new RemoteException(e.getMessage());
            }finally {
                closeConnection(con);
            }
        }

    /**
     * Get invoiceCust desc data vector according to the orderId
     * @param pOrderId the order Id to search the order item
     * @return an <code>InvoiceCustDescDataVector</code> value
     * @exception            RemoteException
     */
    public InvoiceCustDescDataVector getInvoiceCustDescCollection(int pOrderId)
    throws RemoteException
    {

        InvoiceCustDescDataVector invoiceCustDescV = null;
        Connection conn = null;

        try
        {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();

            // get the InvoiceCust data for this order status.
            crit.addEqualTo(InvoiceCustDataAccess.ORDER_ID, pOrderId);

            InvoiceCustDataVector invoiceCustV = InvoiceCustDataAccess.select(
                                                     conn, crit);

            if (null != invoiceCustV && 0 < invoiceCustV.size())
            {
                invoiceCustDescV = new InvoiceCustDescDataVector();

                for (int i = 0; i < invoiceCustV.size(); i++)
                {

                    InvoiceCustData invoiceCustD = (InvoiceCustData)invoiceCustV.get(
                                                       i);
                    InvoiceCustDescData invoiceCustDescD = InvoiceCustDescData.createValue();

                    // get the invoice detail  for this invoice
                    crit = new DBCriteria();
                    crit.addEqualTo(
                        InvoiceCustDetailDataAccess.INVOICE_CUST_ID,
                        invoiceCustD.getInvoiceCustId());

                    InvoiceCustDetailDataVector invoiceCustDetailV =
                        InvoiceCustDetailDataAccess.select(conn, crit);
                    invoiceCustDescD.setInvoiceCust(invoiceCustD);
                    invoiceCustDescD.setInvoiceCustDetailList(
                        invoiceCustDetailV);
                    invoiceCustDescV.add(invoiceCustDescD);
                }
            }
        }
        catch (Exception e)
        {
            throw new RemoteException("getInvoiceCustDescCollection: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return invoiceCustDescV;
    }


    /**
     *<code>deleteOrderItemActions</code> "Deletes" the selected order item actions.  This
     *will remove them from the distributor and the customer display and certain reports
     *and add them as a historical status that admins and customer service users can view.
     *
     *@param pOrderItemActionIdsToDelete the order item action ids to delete
     *@param pUserName the user name of the user who is deleting the order item actions (requiered)
     *@throws RemoteException on any error
     */
    public void deleteOrderItemActions(IdVector pOrderItemActionIdsToDelete, String pUserName)
    throws RemoteException{
        Connection conn = null;
        try{
            if(!Utility.isSet(pUserName)){
                throw new RemoteException("User name must be supplied when delete order item actions");
            }
            conn = getConnection();
            Iterator it = pOrderItemActionIdsToDelete.iterator();
            while(it.hasNext()){
                int oiaId = ((Integer)it.next()).intValue();
                OrderItemActionData oia = OrderItemActionDataAccess.select(conn,oiaId);
                OrderPropertyData opd = OrderPropertyData.createValue();
                opd.setOrderId(oia.getOrderId());
                opd.setOrderItemId(oia.getOrderItemId());
                opd.setValue("["+oia.toString()+"]");
                opd.setAddBy(pUserName);
                opd.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
                opd.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
                opd.setShortDesc("Deleted Order Item Action");
                OrderPropertyDataAccess.insert(conn,opd);
                OrderItemActionDataAccess.remove(conn,oia.getOrderItemActionId());
            }
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**Updates or adds the writeable proeperties of an orderItemDesc Object.
     *@params pOrderItemDescList the OrderItemDescDataVector to be updated.
     *@param pName the user name updating the item
     *@return an updated <code>OrderItemDescDataVector</code> value (if any changes were made)
     *@exception            RemoteException
     */
    public OrderItemDescDataVector updateOrderItemDescCollection(OrderItemDescDataVector pOrderItemDescList,
            String pName)
    throws RemoteException
    {

        Connection conn = null;
        Date currentDate = new Date();

        try
        {
            conn = getConnection();
            OrderPropertyDataVector distNotes = new OrderPropertyDataVector();
            IdVector orderItemIdV = new IdVector();

            OrderItemDescDataVector oidV = new OrderItemDescDataVector();

            for (int i = 0, len = pOrderItemDescList.size(); i < len; i++)
            {
              OrderItemDescData itemDesc = (OrderItemDescData)pOrderItemDescList.get(i);
              OrderItemData itemD = itemDesc.getOrderItem();
              orderItemIdV.add(new Integer(itemD.getOrderItemId()));
              DBCriteria crit = new DBCriteria();
              crit.addEqualTo(OrderPropertyDataAccess.SHORT_DESC,RefCodeNames.ORDER_PROPERTY_TYPE_CD.OPEN_LINE_STATUS_CD);
              crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD,RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
              crit.addEqualTo(OrderPropertyDataAccess.ORDER_ITEM_ID,itemDesc.getOrderItem().getOrderItemId());
              OrderPropertyDataVector opdv = OrderPropertyDataAccess.select(conn,crit);
              if(opdv.size() == 0){
                //insert it
                OrderPropertyData opd = OrderPropertyData.createValue();
                opd.setAddBy(pName);
                opd.setModBy(pName);
                opd.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
                //set it to a note so it show up in order tracker
                opd.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
                opd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.OPEN_LINE_STATUS_CD);
                opd.setValue(itemDesc.getOpenLineStatusCd());
                opd.setOrderId(itemDesc.getOrderItem().getOrderId());
                opd.setOrderItemId(itemDesc.getOrderItem().getOrderItemId());
                if (Utility.isSet(itemDesc.getOpenLineStatusCd())){
                  OrderPropertyDataAccess.insert(conn, opd);
                }
              }else if(opdv.size() >= 1){
                  //update it
                  OrderPropertyData opd = (OrderPropertyData) opdv.get(0);
                  opd.setValue(itemDesc.getOpenLineStatusCd());
                  opd.setModBy(pName);
                  OrderPropertyDataAccess.update(conn, opd);
              }

              if (Utility.isSet(itemDesc.getShipStatus()))
              {
                // add the action
                OrderItemActionData shipStatus = OrderItemActionData.createValue();
                shipStatus.setAddBy(pName);
                shipStatus.setAddDate(new Date());
                shipStatus.setOrderId(itemD.getOrderId());
                shipStatus.setOrderItemId(itemD.getOrderItemId());
                shipStatus.setAffectedSku("" + itemD.getItemSkuNum());
                shipStatus.setActionCd(itemDesc.getShipStatus());
                shipStatus.setQuantity(itemDesc.getNewOrderItemActionQty());
                shipStatus.setActionDate(itemDesc.getTargetShipDate());
                shipStatus.setActionTime(null);
                OrderItemActionDataAccess.insert(conn, shipStatus);
                if(itemDesc.getOrderItemActionList() == null){
                    itemDesc.setOrderItemActionList(new OrderItemActionDataVector());
                }
                itemDesc.getOrderItemActionList().add(shipStatus);
                if (RefCodeNames.ORDER_PROPERTY_SHIP_STATUS.BACKORDERED.equals(itemDesc.getShipStatus()))
                {
                    //need some more inteligence here...
                    itemD.setQuantityBackordered(itemDesc.getNewOrderItemActionQty() + itemD.getQuantityBackordered());
                }


                if(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.QUANTITY_CHANGE.equals(itemDesc.getShipStatus())){

                	itemD.setTotalQuantityOrdered(itemDesc.getNewOrderItemActionQty());
                	oidV.add(itemDesc);

                }

                itemD.setQuantityConfirmed(itemD.getTotalQuantityOrdered());

              }

              if (itemD.isDirty())
              {
                  itemD.setModBy(pName);
                  this.updateOrderItem(itemDesc.getOrderItem());
              }
              //update the order item actions if they are present
                if(itemDesc.getOrderItemActionList() != null){
                    Iterator it = itemDesc.getOrderItemActionList().iterator();
                    while(it.hasNext()){
                        OrderItemActionData oia = (OrderItemActionData) it.next();
                        if(oia.getOrderItemActionId() > 0 && oia.isDirty()){
                            oia.setModBy(pName);
                            OrderItemActionDataAccess.update(conn,oia);
                        }else if(oia.getOrderItemActionId() == 0){
                            if(!RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SYSTEM_ACCEPTED.equals(oia.getActionCd())){
                                oia.setModBy(pName);
                                oia.setAddBy(pName);
                                OrderItemActionDataAccess.insert(conn,oia);
                            }
                        }
                    }
                }

              OrderPropertyData opD = itemDesc.getDistPoNote();
              if(opD!=null)  distNotes.add(opD);
            }

            //Update distributor notes
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
                    RefCodeNames.ORDER_PROPERTY_TYPE_CD.DISTRIBUTOR_PO_NOTE);
            crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD,
                    RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
            crit.addOneOf(OrderPropertyDataAccess.ORDER_ITEM_ID, orderItemIdV);
            OrderPropertyDataVector oldDistNotes =
                    OrderPropertyDataAccess.select(conn,crit);
            for(Iterator iter=oldDistNotes.iterator(); iter.hasNext();) {
              OrderPropertyData oldNote = (OrderPropertyData) iter.next();
              boolean removeFl = true;
              int orderPropertyId = oldNote.getOrderPropertyId();
              for(Iterator iter1=distNotes.iterator(); iter1.hasNext();) {
                OrderPropertyData note = (OrderPropertyData) iter1.next();
                if(orderPropertyId==note.getOrderPropertyId()) {
                  String text = note.getValue();
                  if(!Utility.isSet(text)) {
                    iter1.remove();
                    break;
                  }
                  if(text.equals(oldNote.getValue())) {
                    removeFl = false;
                    iter1.remove();
                  }
                  break;
                }
              }
              if(removeFl) {
                oldNote.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.INACTIVE);
                oldNote.setModBy(pName);
                OrderPropertyDataAccess.update(conn,oldNote);
              }
            }
            for(Iterator iter=distNotes.iterator(); iter.hasNext();) {
              OrderPropertyData note = (OrderPropertyData) iter.next();
              if(!Utility.isSet(note.getValue())) {
                continue;
              }
              note.setOrderPropertyId(0);
              note.setAddBy(pName);
              note.setModBy(pName);
              OrderPropertyData opD = OrderPropertyDataAccess.insert(conn,note);
              note.setOrderPropertyId(opD.getOrderPropertyId());
            }

            if(oidV.size()>0 || !(oidV.isEmpty())){ // item qty was changed

            	StoreOrderChangeRequestData  pChangeRequest = StoreOrderChangeRequestData.createValue();
            	OrderItemDataVector oldItems = new OrderItemDataVector();
            	OrderData theOrder = OrderData.createValue();
            	for(int i=0; i<oidV.size(); i++){
            		OrderItemDescData oidd = (OrderItemDescData)oidV.get(i);
            		OrderItemData oid = oidd.getOrderItem();
            		oldItems.add((OrderItemData)oid);

            		if(oid.getTotalQuantityOrdered() == 0 ){
            			oid.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED);
            		}
            		OrderItemDataAccess.update(conn, oid);

            		int orderId = oid.getOrderId();
            		DBCriteria oCrit = new DBCriteria();

            		oCrit.addEqualTo(OrderDataAccess.ORDER_ID, orderId);
            		theOrder = OrderDataAccess.select(conn, orderId);

            	}

            	//STORE ORDER UPDATE pipeline

        		pChangeRequest.setOrderData(theOrder);
        		pChangeRequest.setOldOrderData(theOrder);
        		pChangeRequest.setOldOrderItems(oldItems);
        		pChangeRequest.setOrderItemsDesc(oidV);
        		pChangeRequest.setUserName(pName);

        		APIAccess factory = new APIAccess();

        		factory.getStoreOrderAPI().updateOrder(pChangeRequest);

            }

            return pOrderItemDescList;
        }
        catch (Exception e)
        {
            throw new RemoteException("updateOrderItemDescCollection: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }
    }

    public InvoiceDistDataVector getInvoiceDistCollection(Date pInvoiceSearchStartDate,
                Date pInvoiceSearchEndDate,
                String pDateSearchType,
                String pErpPoNum,
                String pInvoiceNum,
                String pVoucherNumber,
                String pOptionalInvoiceStatusCd)
        throws RemoteException
    {
      return getInvoiceDistCollectionForStore (
                pInvoiceSearchStartDate,
                pInvoiceSearchEndDate,
                pDateSearchType,
                pErpPoNum,
                pInvoiceNum,
                pVoucherNumber,
                pOptionalInvoiceStatusCd,
                null);
    }

    public InvoiceDistDataVector getInvoiceDistCollectionForStore(Date pInvoiceSearchStartDate,
            Date pInvoiceSearchEndDate,
            String pDateSearchType,
            String pErpPoNum,
            String pInvoiceNum,
            String pVoucherNumber,
            String pOptionalInvoiceStatusCd,
            Integer pStoreId)
    throws RemoteException
    {

        InvoiceDistDataVector iv = new InvoiceDistDataVector();
        logInfo(
            "OrderBean.getInvoiceDistCollection:" +
            " pInvoiceSearchStartDate: " + pInvoiceSearchStartDate +
            " pInvoiceSearchEndDate: " + pInvoiceSearchEndDate +
            " pDateSearchType: " + pDateSearchType + " pErpPoNum: " +
            pErpPoNum + " pInvoiceNum: " + pInvoiceNum +
            " pVoucherNumber: " + pVoucherNumber +
            " pOptionalInvoiceStatusCd: " + pOptionalInvoiceStatusCd +
            " storeId: " + pStoreId);

        Connection con = null;

        try
        {
            con = getConnection();

            DBCriteria dbc = new DBCriteria();
            boolean ascendingFlag = false; // Show the most recent first.
            if (pStoreId != null )
             {
               // Search for entries for this store ID.
               dbc.addEqualTo(InvoiceDistDataAccess.STORE_ID, pStoreId);
            }
            if (pErpPoNum != null && pErpPoNum.length() > 0)
            {

                // Search for entries for this PO.
                dbc.addEqualTo(InvoiceDistDataAccess.ERP_PO_NUM, pErpPoNum);
                dbc.addOrderBy(InvoiceDistDataAccess.ERP_PO_NUM, ascendingFlag);
            }
            else if (pInvoiceNum != null && pInvoiceNum.length() > 0)
            {

                // Search for entries for this invoice number.
                dbc.addEqualTo(InvoiceDistDataAccess.INVOICE_NUM, pInvoiceNum);
                dbc.addOrderBy(InvoiceDistDataAccess.INVOICE_NUM,
                               ascendingFlag);
            }
            else if (pVoucherNumber != null && pVoucherNumber.length() > 0)
            {

                DBCriteria dbc2 = new DBCriteria();
                dbc2.addEqualTo(OrderPropertyDataAccess.CLW_VALUE,
                                pVoucherNumber);

                IdVector idv = OrderPropertyDataAccess.selectIdOnly(con,
                               OrderPropertyDataAccess.INVOICE_DIST_ID,
                               dbc2);

                // Search for entries for this voucher number.
                dbc.addOneOf(InvoiceDistDataAccess.INVOICE_DIST_ID, idv);
                dbc.addOrderBy(InvoiceDistDataAccess.INVOICE_NUM,
                               ascendingFlag);
            }
             else
            {

                if (null != pInvoiceSearchStartDate)
                {

                    if (pDateSearchType == null ||
                            pDateSearchType.equals("InvoiceDate"))
                    {
                        dbc.addGreaterOrEqual(
                            InvoiceDistDataAccess.INVOICE_DATE,
                            pInvoiceSearchStartDate);
                    }
                    else
                    {
                        dbc.addGreaterOrEqual(InvoiceDistDataAccess.ADD_DATE,
                                              pInvoiceSearchStartDate);
                    }

                    ascendingFlag = true;
                }

                if (null != pInvoiceSearchEndDate)
                {

                    if (pDateSearchType == null ||
                            pDateSearchType.equals("InvoiceDate"))
                    {
                        dbc.addLessOrEqual(InvoiceDistDataAccess.INVOICE_DATE,
                                           pInvoiceSearchEndDate);
                    }
                    else
                    {
                        dbc.addLessOrEqual(InvoiceDistDataAccess.ADD_DATE,
                                           pInvoiceSearchEndDate);
                    }

                    ascendingFlag = false;
                }

                if (pOptionalInvoiceStatusCd != null &&
                        !(pOptionalInvoiceStatusCd.equals("All")))
                {
                    dbc.addEqualTo(InvoiceDistDataAccess.INVOICE_STATUS_CD,
                                   pOptionalInvoiceStatusCd);
                }

                dbc.addOrderBy(InvoiceDistDataAccess.INVOICE_DATE,
                               ascendingFlag);
            }

            int maxrows = 500;
            iv = InvoiceDistDataAccess.select(con, dbc, maxrows);
        }
        catch (Exception e)
        {
            logError("getInvoiceDistCollection error: " + e);
        }
        finally
        {
            closeConnection(con);
        }

        logInfo("Found invoices: " + iv.size());

        return iv;
    }

    public InvoiceDistDetailDataVector getInvoiceDistDetailCollection(int pInvoiceDistId)
    throws RemoteException
    {

        InvoiceDistDetailDataVector iv = new InvoiceDistDetailDataVector();

        Connection con = null;

        try
        {
            con = getConnection();

            DBCriteria dbc = new DBCriteria();

            // Search for entries for this PO.
            dbc.addEqualTo(InvoiceDistDetailDataAccess.INVOICE_DIST_ID,
                           pInvoiceDistId);
            dbc.addOrderBy(InvoiceDistDetailDataAccess.INVOICE_DIST_DETAIL_ID);
            iv = InvoiceDistDetailDataAccess.select(con, dbc);
        }
        catch (Exception e)
        {
            logError("getInvoiceDistDetailCollection error: " + e);
        }
        finally
        {
            closeConnection(con);
        }

        return iv;
    }

    public OrderPropertyDataVector getNotes(int pId, String pPropertyTypeCd)
    throws RemoteException,
                DataNotFoundException
    {

        OrderPropertyDataVector notesVec = new OrderPropertyDataVector();
        Connection conn = null;

        try
        {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();

            if (pPropertyTypeCd.equals("invoiceDistNotes"))
            {
                crit.addEqualTo(OrderPropertyDataAccess.INVOICE_DIST_ID, pId);
                crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
                                RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
                crit.addOrderBy(OrderPropertyDataAccess.ORDER_PROPERTY_ID,
                                false);
                notesVec = OrderPropertyDataAccess.select(conn, crit);
            }
        }
        catch (Exception e)
        {
            throw new RemoteException("getOrderPropertyCollection: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return notesVec;
    }

    /**
     *  Description of the Method
     *@param pOrder order
     *@param  pOrderItemIdV  Description of Parameter
     *@param pUser the user login name
     *@exception  RemoteException     Description of Exception
     */
    public void cancelOrderItems(OrderData pOrder, IdVector pOrderItemIdV, String pUser,
         boolean pDoFreightUpdate)
    throws RemoteException
    {
        if (null==pOrderItemIdV || pOrderItemIdV.size()==0) {
          return;
        }
        Connection conn = null;
        ArrayList canceledLines = new ArrayList();
        try
        {
            conn = getConnection();
            //Cancel from Lawson first
            DBCriteria crit = new DBCriteria();
            crit.addOneOf(OrderItemDataAccess.ORDER_ITEM_ID,
                              pOrderItemIdV);
            OrderItemDataVector oiDV = OrderItemDataAccess.select(conn,crit);
            OrderDAO.cancelAndUpdateOrderItems(conn, oiDV, pOrder, pUser);
        }
        catch (Exception e)
        {
            throw new RemoteException("cancelOrderItems: " + e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }

        return;
    }

    public void updateInvoiceVoucherNumber(int pInvoiceDistId,
                                           String pNewVoucherNumber,
                                           String pUsername)
    throws RemoteException,
                DataNotFoundException
    {
        Connection conn = null;

        try
        {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderPropertyDataAccess.INVOICE_DIST_ID,
                            pInvoiceDistId);
            crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
                            RefCodeNames.ORDER_PROPERTY_TYPE_CD.VOUCHER_NUMBER);

            OrderPropertyDataVector numV = OrderPropertyDataAccess.select(conn,
                                           crit);

            if (null != numV && numV.size() > 0)
            {

                // Update the existing voucher number.
                OrderPropertyData opd = (OrderPropertyData)numV.get(0);
                opd.setValue(pNewVoucherNumber);
                opd.setModBy(pUsername);
                OrderPropertyDataAccess.update(conn, opd);
            }
            else
            {

                // Make a new property.
                OrderPropertyData opd = OrderPropertyData.createValue();
                opd.setValue(pNewVoucherNumber);
                opd.setInvoiceDistId(pInvoiceDistId);
                opd.setOrderPropertyTypeCd(
                    RefCodeNames.ORDER_PROPERTY_TYPE_CD.VOUCHER_NUMBER);
                opd.setShortDesc(
                    RefCodeNames.ORDER_PROPERTY_TYPE_CD.VOUCHER_NUMBER);
                opd.setOrderPropertyStatusCd(
                    RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
                opd.setModBy(pUsername);
                opd.setAddBy(pUsername);
                OrderPropertyDataAccess.insert(conn, opd);
            }

            // Now update the invoice status.
            String usql = " update " +
                          InvoiceDistDataAccess.CLW_INVOICE_DIST + " set " +
                          InvoiceDistDataAccess.INVOICE_STATUS_CD + " = '" +
                          RefCodeNames.INVOICE_STATUS_CD.MANUAL_INVOICE_RELEASE +
                          "' " + " where " +
                          InvoiceDistDataAccess.INVOICE_DIST_ID + " = '" +
                          pInvoiceDistId + "' " + " and " +
                          InvoiceDistDataAccess.INVOICE_STATUS_CD +
                          " in ( " + " '" +
                          RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW +
                          "'," + " '" +
                          RefCodeNames.INVOICE_STATUS_CD.ERP_REJECTED +
                          "'," + " '" +
                          RefCodeNames.INVOICE_STATUS_CD.ERP_GENERATED_ERROR +
                          "'," + " '" +
                          RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED_ERROR +
                          "'," + " '" +
                          RefCodeNames.INVOICE_STATUS_CD.PENDING +
                          "' " + " )";
            logDebug(usql);

            Statement stmt = conn.createStatement();
            String note = "Voucher number changed to: " + pNewVoucherNumber;

            if (stmt.executeUpdate(usql) > 0){
                note += " Status updated to: " + RefCodeNames.INVOICE_STATUS_CD.MANUAL_INVOICE_RELEASE;
            }else{
                note += " Status NOT updated to: " + RefCodeNames.INVOICE_STATUS_CD.MANUAL_INVOICE_RELEASE;
            }

            stmt.close();

            // Add a note to indicate the coucher number update.
            OrderPropertyData onote = OrderPropertyData.createValue();
            onote.setValue(note);
            onote.setInvoiceDistId(pInvoiceDistId);
            onote.setOrderPropertyTypeCd(
                RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
            onote.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
            onote.setOrderPropertyStatusCd(
                RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
            onote.setModBy(pUsername);
            onote.setAddBy(pUsername);
            OrderPropertyDataAccess.insert(conn, onote);
        }
        catch (SQLException ex)
        {

            String lmsg =
                "updateInvoiceVoucherNumber failed to set pInvoiceDistId: " +
                pInvoiceDistId + " to pNewVoucherNumber: " +
                pNewVoucherNumber;
            logError(lmsg + " exception 1: " + ex);
            throw new RemoteException(lmsg);
        }
        catch (Exception ex)
        {

            String lmsg =
                "updateInvoiceVoucherNumber failed to set pInvoiceDistId: " +
                pInvoiceDistId + " to pNewVoucherNumber: " +
                pNewVoucherNumber;
            logError(lmsg + " exception 2: " + ex);
            throw new RemoteException(lmsg);
        }
        finally
        {
            closeConnection(conn);
        }

        return;
    }


    /**
     *Returns all of the order properties associated with the specified invoice id.
     *@param pInvoiceDistId the invoice id
     *@param pPropertyType <i>Optional</i> the property type, if null all propertie types will be retrieved
     *@returns a populated OrderPropertyDataVector
     */
    public OrderPropertyDataVector getOrderPropertyCollectionByInvoiceDist(int pInvoiceDistId,String pPropertyType)
    throws RemoteException {
        Connection conn = null;

        try{
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderPropertyDataAccess.INVOICE_DIST_ID,pInvoiceDistId);
            crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD,RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
            if(pPropertyType != null){
                crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,pPropertyType);
            }
            return OrderPropertyDataAccess.select(conn,crit);
        }catch (Exception e) {
            e.printStackTrace();
            logError(e.getMessage());
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public String fetchInvoiceVoucherNumber(int pInvoiceDistId)
    throws RemoteException
    {
        Connection conn = null;

        try
        {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderPropertyDataAccess.INVOICE_DIST_ID,
                            pInvoiceDistId);
            crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
                            RefCodeNames.ORDER_PROPERTY_TYPE_CD.VOUCHER_NUMBER);

            OrderPropertyDataVector numV = OrderPropertyDataAccess.select(conn,
                                           crit);

            if (null != numV && numV.size() > 0)
            {

                // Update the existing voucher number.
                OrderPropertyData opd = (OrderPropertyData)numV.get(0);

                return opd.getValue();
            }
        }
        catch (SQLException ex)
        {

            String lmsg =
                "fetchInvoiceVoucherNumber failed to get voucher number for pInvoiceDistId: " +
                pInvoiceDistId;
            logError(lmsg + " exception 1.1: " + ex);
            throw new RemoteException(lmsg);
        }
        catch (Exception ex)
        {

            String lmsg =
                "fetchInvoiceVoucherNumber failed to get voucher number for pInvoiceDistId: " +
                pInvoiceDistId;
            logError(lmsg + " exception 2.1: " + ex);
            throw new RemoteException(lmsg);
        }
        finally
        {
            closeConnection(conn);
        }

        // No number found.
        return "";
    }
    //--------------------------------------------------------------------------
    /**
     *  Description of the Method
     *
     *@param  pOrderId
     *@param pUser the user login name
     *@exception  RemoteException     Description of Exception
     */
    public void cancelOrder(int pOrderId, String pUser)
    throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            OrderData orderD = OrderDataAccess.select(conn,pOrderId);
            OrderDAO.cancelAndUpdateOrder(conn, orderD,pUser);
        }
        catch (Exception e) {
            throw new RemoteException("cancelOrder: " + e.getMessage());
        }
        finally {
            closeConnection(conn);
        }

        return;
    }

    /**
     *  Updates order items, if they were not canceled.
     *  Initiates request to change item in Lawson (cost and quantiy so far)
     *@param pOrder order
     *@param  pOrderItemDV the list of modified OrderItemData objects
     *@param pOrderPropertyDV the list of item quntity modification requests
     *@param pUser the user login name
     *@exception  RemoteException     Description of Exception
     */
    /*
    public void updateOrderSentToErp(OrderData pOrder,
                                     OrderItemDataVector pOrderItemDV,
                                     OrderPropertyDataVector pOrderPropertyDV,
                                     String pUser)
    throws RemoteException
    {
        if(pOrderItemDV==null || pOrderItemDV.size()==0) return;
        Connection conn = null;
        try {
          conn = getConnection();

          OrderDataAccess.update(conn,pOrder);

          int erpOrderNum = pOrder.getErpOrderNum();
          OrderItemData oiD = (OrderItemData) pOrderItemDV.get(0);
          boolean lawsonFl = true;
          String erpSystemCd = pOrder.getErpSystemCd();
          if(erpSystemCd!=null &&
             !erpSystemCd.trim().equals(RefCodeNames.ERP_SYSTEM_CD.LAWSON)) {
            lawsonFl = false;
          }
          if(lawsonFl) {
            if(erpOrderNum >0) {
              APIAccess factory = APIAccess.getAPIAccess();
              Lawson lawsonEjb = factory.getLawsonAPI();
              lawsonEjb.updateOrderItems(erpOrderNum, pOrderItemDV, pUser);
              lawsonEjb.changeOrderFreight(pOrder);
            }
          }
          //Udate order items
          java.util.HashSet poNums = new java.util.HashSet();
          for(int ii=0; ii<pOrderItemDV.size(); ii++) {
            oiD = (OrderItemData) pOrderItemDV.get(ii);
            poNums.add(new Integer(oiD.getPurchaseOrderId()));
            if(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.
                equals(oiD.getOrderItemStatusCd())) {
              continue;
            }
            int orderItemId = oiD.getOrderItemId();
            //get order item data againg to minimize probability of asynchrony
            OrderItemData oiD1 = OrderItemDataAccess.select(conn,orderItemId);
            if(oiD1.getItemQty855()!=0)
              //save quantiy update request only if 855 was already sent
              for(int jj=0; jj<pOrderPropertyDV.size(); jj++) {
                OrderPropertyData opD = (OrderPropertyData) pOrderPropertyDV.get(jj);
                if(opD.getOrderItemId()==orderItemId &&
                   RefCodeNames.ORDER_PROPERTY_TYPE_CD.QUANTITY_UPDATE.
                   equals(opD.getOrderPropertyTypeCd())) {
                   OrderPropertyDataAccess.insert(conn,opD);
                }
            }
            oiD.setModBy(pUser);
            oiD = updateAddOrderItemWorker(conn, oiD);
          }
          //update the pos
          Iterator it = poNums.iterator();
          while(it.hasNext()){
              Integer poId = (Integer) it.next();
              APIAccess factory = APIAccess.getAPIAccess();
              if(poId.intValue() > 0){
                if(RefCodeNames.ERP_SYSTEM_CD.LAWSON.equals(erpSystemCd)){
                    factory.getLawsonAPI().updatePurchaseOrder(poId.intValue());
                }else{
                    factory.getPurchaseOrderAPI().updatePurchaseOrderFromOrderItems(poId.intValue());
                }
              }

          }
        }catch (Exception exc){
          throw processException(exc);
        } finally {
          closeConnection(conn);
        }
    }
*/
    /**
     *  Updates order items, if they were not canceled.
     *  Initiates request to change item in Lawson (cost and quantiy so far)
     *@param pOrder order
     *@param  pOrderItemDV the list of modified OrderItemData objects
     *@param pOrderPropertyDV the list of item quntity modification requests
     *@param pUser the user login name
     *@exception  RemoteException     Description of Exception
     */
    public void updateOrderSentToErp(OrderData pOrder,
                                     OrderItemDataVector pOrderItemDV,
                                     OrderPropertyDataVector pOrderPropertyDV,
                                     ReplacedOrderItemViewVector pReplacedOrderItemVwV,
                                     String pUser)
    throws RemoteException
    {
        if(pOrderItemDV==null || pOrderItemDV.size()==0) return;
        Connection conn = null;
        try {
          conn = getConnection();

          OrderDataAccess.update(conn,pOrder);

          OrderErpUpdater updater = new OrderErpUpdater();
          updater.updateOrderSentToErp(conn,pOrder, pOrderItemDV, pOrderPropertyDV,pReplacedOrderItemVwV, pUser);
        }catch (Exception exc){
          throw processException(exc);
        } finally {
          closeConnection(conn);
        }
    }

    public void setMetaAttribute(int pOrderId,
         String pMetaDataShortDesc,
         String pValue,
         String pUser)
  throws RemoteException

    {
        Connection conn = null;
  try {
      conn = getConnection();
      boolean add_MetaEntry = true;
      DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(OrderMetaDataAccess.ORDER_ID,pOrderId);
            OrderMetaDataVector omDV = OrderMetaDataAccess.select(conn,dbc);

      for ( int i = 0; omDV != null && i < omDV.size();
      i++ ) {
    OrderMetaData omd = (OrderMetaData)omDV.get(i);
    if ( omd.getName() != null &&
         omd.getName().equals(pMetaDataShortDesc) ) {
        omd.setValue(pValue);
        omd.setModBy(pUser);
        OrderMetaDataAccess.update(conn,omd);
        add_MetaEntry = false;
    }
      }

      if ( add_MetaEntry ) {
    // none found, add an entry.
    OrderMetaData omd = OrderMetaData.createValue();
    omd.setName(pMetaDataShortDesc);
    omd.setValue(pValue);
    omd.setModBy(pUser);
    omd.setAddBy(pUser);
    omd.setOrderId(pOrderId);
    OrderMetaDataAccess.insert(conn,omd);
      }

        }
  catch (Exception exc){
      throw new RemoteException("setMetaAttribute: " +
              exc.getMessage());
        }
  finally {
      closeConnection(conn);
        }
    }

    public void removeMetaAttribute(int pOrderId,
         String pMetaDataShortDesc,
         String pUser)
  throws RemoteException

    {
        Connection conn = null;
  try {
      conn = getConnection();
      DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(OrderMetaDataAccess.ORDER_ID,pOrderId);
        dbc.addEqualTo(OrderMetaDataAccess.NAME,pMetaDataShortDesc);
        OrderMetaDataAccess.remove(conn,dbc);

    } catch (Exception exc){
      throw new RemoteException(exc.getMessage());
    } finally {
      closeConnection(conn);
    }
    }

    private SiteData getSiteData(Connection pCon,
                                 OrderData pOrderData)
        throws Exception,
               com.cleanwise.service.api.util.DataNotFoundException{

        logDebug("======= pOrderData.getSiteId=" +
                 pOrderData.getSiteId() +
                 " pOrderData.getOrderSiteName=" +
                 pOrderData.getOrderSiteName());

        BusEntityDAO mdao = mCacheManager.getBusEntityDAO();
        SiteData sd = null;
        try {
         sd = mdao.getSiteData(pCon,
             pOrderData.getSiteId(),
             pOrderData.getOrderSiteName());
         java.util.Date ordDate = pOrderData.getRevisedOrderDate();
         if(ordDate == null){
             ordDate = pOrderData.getOriginalOrderDate();
         }
         sd.setCurrentBudgetPeriod(mdao.getAccountBudgetPeriod(pCon, pOrderData.getAccountId(), pOrderData.getSiteId(), ordDate));
        } catch ( DataNotFoundException dnf) {
          sd = SiteData.createValue();
        }
        return  sd;
    }


    /**
     * Gets approved orders
     * @return the set of approved orders
     * @throws   RemoteException, LawsonException
     */
    public OrderDataVector getApprovedOrders()
    throws RemoteException {
        Connection con = null;
        OrderDataVector orderDV = new OrderDataVector();
        try {
            DBCriteria dbc;
            con = getConnection();

            //get order information
            dbc = new DBCriteria();
            dbc.addEqualTo(OrderDataAccess.ORDER_STATUS_CD,RefCodeNames.ORDER_STATUS_CD.ORDERED);
            orderDV = OrderDataAccess.select(con,dbc);
        }
        catch (Exception exc) {
            throw processException(exc);
        }
        finally {
            closeConnection(con);
        }
        return orderDV;
    }

    /**
     * Gets orders by the type
     * @return the set of received orders
     * @throws   RemoteException, LawsonException
     */
    public OrderDataVector getOrdersByType(String pOrderType)
    throws RemoteException {
        Connection con = null;
        OrderDataVector orderDV = new OrderDataVector();
        try {
            DBCriteria dbc;
            con = getConnection();

            //get order information
            dbc = new DBCriteria();
            dbc.addEqualTo(OrderDataAccess.ORDER_STATUS_CD,pOrderType);
            orderDV = OrderDataAccess.select(con,dbc);
        }
        catch (Exception exc) {
            throw processException(exc);
        }
        finally {
            closeConnection(con);
        }
        return orderDV;
    }

    /**
     * Sets PENDING_CONSOLIDATION status for orders replaced by consolidated order,
     * and cancels the consolidated order
     * @throws   RemoteException
     */
    public void deconsolidateOrder(int pOrderId, String pUserName)
    throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            DBCriteria dbc;
            OrderData orderD = OrderDataAccess.select(con,pOrderId);
            if(!(RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.
                                        equals(orderD.getOrderTypeCd()) &&
                 (RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL.
                                        equals(orderD.getOrderStatusCd())||
                  RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.
                                        equals(orderD.getOrderStatusCd())||
                  RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION.
                                        equals(orderD.getOrderStatusCd()))
            )) {
              return; //Only consolideted pending orders
            }
            orderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.CANCELLED);
            orderD.setModBy(pUserName);
            OrderDataAccess.update(con, orderD);


            dbc = new DBCriteria();
            dbc.addEqualTo(OrderAssocDataAccess.ORDER2_ID,pOrderId);
            dbc.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_CD,
                            RefCodeNames.ORDER_ASSOC_CD.CONSOLIDATED);
            dbc.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_STATUS_CD,
                           RefCodeNames.ORDER_ASSOC_STATUS_CD.ACTIVE);
            OrderAssocDataVector orderAssocDV = OrderAssocDataAccess.select(con,dbc);

            IdVector replOrderIdV = new IdVector();
            for(Iterator iter=orderAssocDV.iterator(); iter.hasNext();) {
              OrderAssocData oaD = (OrderAssocData) iter.next();
              replOrderIdV.add(new Integer(oaD.getOrder1Id()));
              oaD.setOrderAssocStatusCd(RefCodeNames.ORDER_ASSOC_STATUS_CD.INACTIVE);
              oaD.setModBy(pUserName);
              OrderAssocDataAccess.update(con, oaD);
            }

            dbc = new DBCriteria();
            dbc.addOneOf(OrderDataAccess.ORDER_ID,replOrderIdV);

            OrderDataVector orderDV = OrderDataAccess.select(con,dbc);
            for(Iterator iter=orderDV.iterator(); iter.hasNext();) {
              OrderData oD = (OrderData) iter.next();
              if(!RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(oD.getOrderStatusCd())){
                throw new Exception("Replaced order was not cancelled. Order id: "+
                                                             oD.getOrderId());

              }
              oD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION);
              oD.setModBy(pUserName);
              OrderDataAccess.update(con, oD);

            }
        }
        catch (Exception exc) {
          exc.printStackTrace();
          throw processException(exc);
        }
        finally {
            closeConnection(con);
        }
    }

    /**
     * Gets orders replaced by consolidated orders
     * @param pOrderId the consolidated order id
     * @return a set of ReplacedOrderView objects
     * @throws   RemoteException
     */
    public ReplacedOrderViewVector getReplacedOrders(int pOrderId)
    throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            ReplacedOrderViewVector replacedOrderVwV =
               getReplacedOrders(con,pOrderId);
            return replacedOrderVwV;
        }
        catch (Exception exc) {
          exc.printStackTrace();
          throw processException(exc);
        }
        finally {
            closeConnection(con);
        }
    }


    /**
     *fetches the order credit card data and associated data
     *@param pOrderCreditCreditCardId the order creit card id.
     *@returns a populated OrderCreditCardData object
     */
    public OrderCreditCardDescData getOrderCreditCardDesc(int pOrderCreditCreditCardId)
    throws RemoteException{
        Connection con = null;
        try {
            con = getConnection();
            CreditCardDAO ccDao = new CreditCardDAO(con);
            return ccDao.getOrderCreditCardDesc(pOrderCreditCreditCardId);
        }catch(Exception e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(con);
        }
    }


    /**
     *saves a order credit card.  It is expecting that the credit card number given to it will be
     *the unencrypted card number.  A call to this method will result in an encrypted card number
     *being saved to the databse.
     */
    public OrderCreditCardData saveOrderCreditCard(String unencryptedCreditCardNumber,OrderCreditCardData pOrderCreditCardData)
    throws RemoteException{
        Connection con = null;
        try {
            con = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderCreditCardDataAccess.ORDER_ID,pOrderCreditCardData.getOrderId());
            int count = OrderCreditCardDataAccess.selectIdOnly(con,crit).size();
            //encrypt the card and set all the associated data
            CreditCardUtil ccU = new CreditCardUtil(unencryptedCreditCardNumber,pOrderCreditCardData, true);

            OrderData order = getOrder(pOrderCreditCardData.getOrderId());
            ccU.encryptOrderCreditCard(con, order.getStoreId());
            //now update/insert it into the db
            if(pOrderCreditCardData.getOrderCreditCardId() == 0){
                if(count == 0){
                    return OrderCreditCardDataAccess.insert(con,pOrderCreditCardData);
                }else{
                    throw new RemoteException("Found existing order credit card record during insert");
                }
            }else{
                if(count == 1){
                    OrderCreditCardDataAccess.update(con,pOrderCreditCardData);
                    return pOrderCreditCardData;
                }else{
                    throw new RemoteException("Found more than 1 order credit card record during update");
                }

            }
        }catch(Exception e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(con);
        }
    }

    /**
     *saves a order credit card.
     */
    public OrderCreditCardData saveOrderCreditCard(OrderCreditCardData pOrderCreditCardData)
    throws RemoteException {
                Connection con = null;
        try {
            con = getConnection();
            OrderCreditCardDataAccess.update(con,pOrderCreditCardData);
            return pOrderCreditCardData;
            }catch(Exception e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(con);
        }

    }

    public void updateBillTo( int pOrderId, int pBillToId )
    throws RemoteException {

        Connection con = null;
        try {
            APIAccess factory = new APIAccess();
            Account accountBean = factory.getAccountAPI();
            BillToData btd = accountBean.getBillToDetail(pBillToId);

            con = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderAddressDataAccess.ORDER_ID, pOrderId);
            crit.addEqualTo(OrderAddressDataAccess.ADDRESS_TYPE_CD,
            RefCodeNames.ADDRESS_TYPE_CD.BILLING);

            OrderAddressDataVector oadv = OrderAddressDataAccess.select(con,crit);

            if ( null == oadv || oadv.size() == 0 ) {
                OrderAddressData billto = OrderAddressData.createValue();
                billto.setShortDesc(btd.getBusEntity().getShortDesc());
                billto.setErpNum(btd.getBusEntity().getErpNum());
                billto.setAddress1(btd.getBillToAddress().getAddress1());
                billto.setAddress2(btd.getBillToAddress().getAddress2());
                billto.setAddress3(btd.getBillToAddress().getAddress3());
                billto.setAddress4(btd.getBillToAddress().getAddress4());
                billto.setCity(btd.getBillToAddress().getCity());
                billto.setPostalCode(btd.getBillToAddress().getPostalCode());
                billto.setStateProvinceCd(btd.getBillToAddress().getStateProvinceCd());
                billto.setCountryCd(btd.getBillToAddress().getCountryCd());
                billto.setOrderId(pOrderId);
                logDebug("  insert billto=" + billto);
                OrderAddressDataAccess.insert(con, billto);
            }
            else if ( oadv.size() == 1 ) {
                OrderAddressData billto = (OrderAddressData)oadv.get(0);
                billto.setShortDesc(btd.getBusEntity().getShortDesc());
                billto.setErpNum(btd.getBusEntity().getErpNum());
                billto.setAddress1(btd.getBillToAddress().getAddress1());
                billto.setAddress2(btd.getBillToAddress().getAddress2());
                billto.setAddress3(btd.getBillToAddress().getAddress3());
                billto.setAddress4(btd.getBillToAddress().getAddress4());
                billto.setCity(btd.getBillToAddress().getCity());
                billto.setPostalCode(btd.getBillToAddress().getPostalCode());
                billto.setStateProvinceCd(btd.getBillToAddress().getStateProvinceCd());
                billto.setCountryCd(btd.getBillToAddress().getCountryCd());
                billto.setOrderId(pOrderId);
                logDebug("  update billto=" + billto);
                OrderAddressDataAccess.update(con, billto);
            }
            else {
                String emsg = "Multiple " + RefCodeNames.ADDRESS_TYPE_CD.BILLING +
                " addresses defined for pOrderId=" + pOrderId ;
                logError(emsg);
                throw new RemoteException(emsg);
            }

        }catch(Exception e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(con);
        }
        return;
    }


    /**
     *Performs tax calculation on on the supplied data.
     *Old Tax System: deprecated.
     */
    public BigDecimal calculateTax(TaxQuery query) throws RemoteException{
        Connection conn=null;
        try{
            conn = getConnection();
            //return TaxUtil.calculateTax(conn,query);
            return TaxUtilAvalara.calculateTax(conn,query);
        }catch(Exception e){
            throw processException(e);
        }finally {
            closeConnection(conn);
        }
    }
     /**
     * If account,store and the site has taxable attribute
     * which is true in that case a method returns true
     * @param storeId   store identifier
     * @param accountId  account identifier
     * @param siteId  site identifier
     * @return taxable flag
     * @throws RemoteException if an errors
     */
    public boolean isTaxableOrder(int storeId,int accountId,int siteId) throws RemoteException {
      Connection conn=null;
        try{
            conn = getConnection();
            //return TaxUtil.isTaxable(conn, storeId, accountId, siteId);
            return TaxUtilAvalara.isTaxable(conn, storeId, accountId, siteId);
        }catch(Exception e){
            throw processException(e);
        }finally {
            closeConnection(conn);
        }
    }

    /**
     * Returns an initialized TaxQueryResponse object that can be used for
     * multiple tax querys for the same site/address
     * @param query query for the tax system.  The amount param is ignored.
     * @return an initialized TaxQueryResponse
     * @throws RemoteException
     */
    public TaxQueryResponse getTaxQueryResponse(TaxQuery query) throws RemoteException{
        Connection conn=null;
        try{
            conn = getConnection();
            //return TaxUtil.getTaxQueryResponse(conn,query);
            return TaxUtilAvalara.getTaxQueryResponse(conn,query);
        }catch(Exception e){
            throw processException(e);
        }finally {
            closeConnection(conn);
        }
    }

    /**
     *Returnes orders ready ready to resume pipeline
     */
    public OrderDataVector getOrdersPipelineToResume()
       throws RemoteException{
        Connection conn=null;
        try{
           conn = getConnection();
           DBCriteria dbc = new DBCriteria();
           dbc.addEqualTo(OrderDataAccess.WORKFLOW_IND,
                        RefCodeNames.WORKFLOW_IND_CD.TO_RESUME);
           dbc.addNotEqualTo(OrderDataAccess.ORDER_STATUS_CD,
                   RefCodeNames.ORDER_STATUS_CD.CANCELLED);
           OrderDataVector oDV = OrderDataAccess.select(conn,dbc);
           return oDV;
        }catch(Exception e){
            throw processException(e);
        }finally {
            closeConnection(conn);
        }
    }

    /**
     * Retrieve unapproved workflow notes which the user has rights to approve.
     * @param pUser the user
     * @param pOrderId the orderId
     * @return set of order property ids
     * @throws RemoteException
     */
    public IdVector getPropertiesUserCanApprove(UserData pUser, int pOrderId)
    throws RemoteException
    {
      Connection conn=null;
      try {
        conn = getConnection();
        IdVector canApproveIdV = new IdVector();
        if(!RefCodeNames.USER_STATUS_CD.ACTIVE.equals(pUser.getUserStatusCd())) {
          return canApproveIdV;
        }
        String workflowRole = pUser.getWorkflowRoleCd();
        boolean approverFl =
           (workflowRole.indexOf(RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER)>=0)?
             true:false;
        
        log.debug("getPropertiesUserCanApprove(): approverFl  = " + approverFl);

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(OrderPropertyDataAccess.ORDER_ID,pOrderId);
        dbc.addEqualTo(OrderPropertyDataAccess.SHORT_DESC, Constants.WORKFLOW_NOTE);
        dbc.addIsNull(OrderPropertyDataAccess.APPROVE_DATE);
        OrderPropertyDataVector opDV = OrderPropertyDataAccess.select(conn,dbc);

        log.debug("getPropertiesUserCanApprove(): opDV = " + opDV);
        
        IdVector workflowIdV = new IdVector();
        for(Iterator iter=opDV.iterator(); iter.hasNext();) {
          OrderPropertyData opD = (OrderPropertyData) iter.next();
          if(opD.getWorkflowRuleId()>0) {
            workflowIdV.add(new Integer(opD.getWorkflowRuleId()));
          }
        }

        log.debug("getPropertiesUserCanApprove(): workflowIdV = " + workflowIdV);
        
        dbc = new DBCriteria();
        dbc.addOneOf(WorkflowRuleDataAccess.WORKFLOW_RULE_ID,workflowIdV);
        WorkflowRuleDataVector wrDV =
           WorkflowRuleDataAccess.select(conn,dbc);

        log.debug("getPropertiesUserCanApprove(): wrDV = " + wrDV);
        
        IdVector groupIdV = new IdVector();
        for(Iterator iter=wrDV.iterator(); iter.hasNext();) {
          WorkflowRuleData wrD = (WorkflowRuleData) iter.next();
          int ruleId = wrD.getWorkflowRuleId();
          if(wrD.getApproverGroupId()>0) {
            groupIdV.add(new Integer(wrD.getApproverGroupId()));
          }
        }
        
        log.debug("getPropertiesUserCanApprove(): groupIdV1 = " + groupIdV);
        
        dbc = new DBCriteria();
        dbc.addEqualTo(GroupAssocDataAccess.USER_ID, pUser.getUserId());
        dbc.addOneOf(GroupAssocDataAccess.GROUP_ID, groupIdV);
        groupIdV =
          GroupAssocDataAccess.selectIdOnly(conn,GroupAssocDataAccess.GROUP_ID,dbc);
        
        log.debug("getPropertiesUserCanApprove(): groupIdV2 = " + groupIdV);
        
        IdVector rulesCanApprove = new IdVector();
        for(Iterator iter=wrDV.iterator(); iter.hasNext();) {
          WorkflowRuleData wrD = (WorkflowRuleData) iter.next();
          int ruleId = wrD.getWorkflowRuleId();
          if(wrD.getApproverGroupId()<=0) {
            if(approverFl) {
              rulesCanApprove.add(new Integer(ruleId));
            }
          } else {
            int groupId = wrD.getApproverGroupId();
            for(Iterator iter1=groupIdV.iterator(); iter1.hasNext();) {
              Integer grIdI = (Integer) iter1.next();
              if(groupId==grIdI.intValue()) {
                rulesCanApprove.add(new Integer(ruleId));
              }
            }
          }
        }

        log.debug("getPropertiesUserCanApprove(): rulesCanApprove = " + rulesCanApprove);
        
        for (Iterator iter=opDV.iterator(); iter.hasNext();) {
        	OrderPropertyData opD = (OrderPropertyData) iter.next();
    		if (opD.getWorkflowRuleId()>0) {
    			//if (rulesCanApprove.contains(new Integer(opD.getWorkflowRuleId()))) {
    			if (rulesCanApprove.contains(new Integer(opD.getWorkflowRuleId())) || approverFl) {
    				canApproveIdV.add(new Integer(opD.getOrderPropertyId()));
    			}
    		} 
    		else {
    			if(approverFl) {
    				canApproveIdV.add(new Integer(opD.getOrderPropertyId()));
    			}
    		}
        }
        
        log.debug("getPropertiesUserCanApprove(): canApproveIdV = " + canApproveIdV);
        
        return canApproveIdV;

      } catch (Exception exc) {
          exc.printStackTrace();
          throw new RemoteException(exc.getMessage());
      } finally {
          if(conn!=null) {
            closeConnection(conn);
          }
      }
    }

    /**
    * Describe <code>OrderPropertyDataVector</code> method here.
    * Makes order no fullfilment
    * @param pOrderPropertyDV a <code>OrderPropertyDataVector</code> value
    * @exception RemoteException if an error occurs
    */
    public void makeOrderNoFulfillment(OrderPropertyDataVector pOrderPropertyDV)
    throws RemoteException
    {
        Connection conn = null;

        try
        {
            conn = getConnection();
            OrderPropertyData orpD = (OrderPropertyData) pOrderPropertyDV.get(0);
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(OrderPropertyDataAccess.ORDER_ID, orpD.getOrderId());
            dbc.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
                           RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORDER);
            OrderPropertyDataVector propertyDV = OrderPropertyDataAccess.select(conn, dbc);
            if(propertyDV.size()==0) {
                for(int i=0; i<2; i++) {
                    OrderPropertyData opD = (OrderPropertyData) pOrderPropertyDV.get(i);
                    OrderPropertyDataAccess.insert(conn,opD);
                }
            }
            else
                logInfo( "VVVVVVVVVVVV Order was already set to billing only.");
        }
        catch (Exception e)
        {
            throw new RemoteException("MakeOrderNoFulfillment: " + e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }
    }

    public String updateOrderShippingForDist( int pOrderId,
                int pToFreightHandlerId,
                String pForDistErpNum,
                String pModUserName )
  throws RemoteException {

  String respmsg = "";
  // Update the freight handler to be used by this distributor.
        Connection conn=null;
        try{
           conn = getConnection();
     BusEntityDAO mdao = mCacheManager.getBusEntityDAO();
     BusEntityData distbe = mdao.getDistributorByErpNum(conn, pForDistErpNum);
     FreightHandlerView fhv = null;
     String fhname = "NONE";

     if ( pToFreightHandlerId > 0 ) {
         fhv = mdao.getFreightHandler(conn, pToFreightHandlerId);
         fhname = fhv.getBusEntityData().getShortDesc();
     }

     /*String usql = "update " + OrderItemDataAccess.CLW_ORDER_ITEM
         + " set " +  OrderItemDataAccess.MOD_DATE + " = sysdate , "
         + OrderItemDataAccess.MOD_BY + " = '" + pModUserName + "', "
         + OrderItemDataAccess.FREIGHT_HANDLER_ID + " = " + pToFreightHandlerId
         + " where " + OrderItemDataAccess.ORDER_ID + " = " + pOrderId
         + " and " + OrderItemDataAccess.DIST_ERP_NUM + " = '" + pForDistErpNum + "'"
         + " and " + OrderItemDataAccess.FREIGHT_HANDLER_ID +  " != " + pToFreightHandlerId;
       */
     String usql = "update " + OrderItemDataAccess.CLW_ORDER_ITEM
         + " set " +  OrderItemDataAccess.MOD_DATE + " = sysdate , "
         + OrderItemDataAccess.MOD_BY + " = ?, "
         + OrderItemDataAccess.FREIGHT_HANDLER_ID + " = ? "
         + " where " + OrderItemDataAccess.ORDER_ID + " = ? "
         + " and " + OrderItemDataAccess.DIST_ERP_NUM + " = ? "
         + " and " + OrderItemDataAccess.FREIGHT_HANDLER_ID +  " != ?";

            logDebug("updateOrderShippingForDist, executing " + usql);
      String updlog = " Order id=" + pOrderId + " freight handler modification, "
    + " the new freight handler: " + fhname + " will be used for distributor: " + distbe.getShortDesc()
    + " (erpnum " + distbe.getErpNum() + ")";

            PreparedStatement stmt = conn.prepareStatement(usql);
            stmt.setString(1, pModUserName);
            stmt.setInt(2, pToFreightHandlerId);
            stmt.setInt(3, pOrderId);
            stmt.setString(4, pForDistErpNum);
            stmt.setInt(5, pToFreightHandlerId);

            if (stmt.executeUpdate() > 0){
    respmsg = "update was successful: " + updlog ;
    OrderDAO.addOrderNote(conn, pOrderId, updlog, pModUserName);
            }else{
    respmsg = "update failed: " + updlog ;
            }

            stmt.close();
      logInfo(" updateOrderShippingForDist, " + respmsg );

        }catch(Exception e){
            throw processException(e);
        }finally {
            closeConnection(conn);
        }

  return respmsg;
    }


    /**
     * Gets items which have been bought
     * @param pOrderId order_id
     * @param pPoId    purchase_order_id
     * @return order item collection
     * @throws RemoteException if an errors
     */
    public OrderItemDataVector getOrderItemsByPoIdOrderId(int pOrderId, int pPoId) throws RemoteException {

        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(OrderItemDataAccess.PURCHASE_ORDER_ID, pPoId);
            dbc.addEqualTo(OrderItemDataAccess.ORDER_ID, pOrderId);//should be unnecessary
            return (OrderItemDataAccess.select(conn, dbc));
        } catch (Exception exc) {
            String em = "Error. getOrderItemsByPoIdOrderId. " + exc.getMessage();
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }

    }

    public OrderData getOrder(int orderId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return OrderDataAccess.select(conn, orderId);

        } catch (Exception exc) {
            String em = "Error. getOrder. " + exc.getMessage();
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }

    public OrderAddressData getOrderAddress(int pOrderId, String pAddrType) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(OrderAddressDataAccess.ORDER_ID, pOrderId);
            dbc.addEqualTo(OrderAddressDataAccess.ADDRESS_TYPE_CD, pAddrType);

            OrderAddressDataVector orderAddrDV = OrderAddressDataAccess.select(conn, dbc);

            if (orderAddrDV.size() >= 1) {
                return (OrderAddressData) orderAddrDV.get(0);
            } else {
                return null;
            }
        } catch (Exception exc) {
            String em = "Error. getOrderAddress SQL Exception happened. " + exc.getMessage();
            throw new RemoteException(em);
        }
        finally {
            closeConnection(conn);
        }
    }

    /**
     * gets  approved invoice flag
     * @param invoiceDistId
     * @return  flag
     * @throws RemoteException if an errors
     */
    public boolean getApprovedInvoiceFlag(int invoiceDistId) throws RemoteException {

        Connection conn = null;
        try {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderPropertyDataAccess.INVOICE_DIST_ID, invoiceDistId);
            crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD, RefCodeNames.ORDER_PROPERTY_TYPE_CD.INVOICE_DIST_APPROVED);
            crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD, RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
            OrderPropertyDataVector values = OrderPropertyDataAccess.select(conn, crit);
            if (values != null) {
                if (values.size() > 0) {
                    OrderPropertyData prop = (OrderPropertyData) values.get(0);
                    if (prop != null && Utility.isTrue(prop.getValue())) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception exc) {
            String em = "Error.getApprovedInvoiceFlag. " + exc.getMessage();
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }

    public CostCenterData getCostCenter(int costCenterId) throws RemoteException, DataNotFoundException {
        Connection conn = null;
        try {
            conn = getConnection();
            return CostCenterDataAccess.select(conn, costCenterId);
       } catch (DataNotFoundException exc) {
            throw new DataNotFoundException(exc.getMessage());
        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }

  public void enterOrderProperty(String pType,
                                   String pShortDesc,
                                   String pValue,
                                   int pOrderId,
                                   int pOrderItemId,
                                   int pInvoiceDistId,
                                   int pInvoiceDistDetailId,
                                   int pInvoiceCustId,
                                   int pInvoiceCustDetailId,
                                   int pOrderAddressId,
                                   String pUserName) throws RemoteException {

      Connection conn = null;
        try {
            conn = getConnection();
           OrderDAO.enterOrderProperty(conn,
                                    RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE,
                                    "Inbound EDI 810 Note", pValue,
                                    pOrderId, pOrderItemId,
                                    pInvoiceDistId,
                                    pInvoiceDistDetailId,
                                    pInvoiceCustId, pInvoiceCustDetailId, pOrderAddressId, pUserName);

        } catch (Exception exc) {
            String em = "Error. enterOrderProperty. " + exc.getMessage();
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }

    public OrderInfoDataView getOrderInfoData(int orderId) throws RemoteException {
        Connection conn = null;
        try {

            conn = getConnection();
            return getOrderInfoData(orderId, conn);

        } catch (Exception exc) {
            String em = "Error. getOrderInfoDataView. " + exc.getMessage();
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }

    }

    private OrderInfoDataView getOrderInfoData(int orderId, Connection conn)
    throws RemoteException, Exception {

        String orderSql = "select store_id, account_id, site_id, order_num,ref_order_id," +
                "(select order_num from clw_order where order_id=o.ref_order_id and o.ref_order_id>0) as ref_num," +
                "nvl(ORIGINAL_ORDER_DATE,REVISED_ORDER_DATE) order_date," +
                "REQUEST_PO_NUM po_number," +
                "order_source_cd source," +
                "order_contact_name contact_name," +
                "order_contact_phone_num contact_phone," +
                "order_contact_email contact_email," +
                "user_first_name||' '||user_last_name as placed_by," +
                "comments," +
                "nvl(ORIGINAL_AMOUNT,0) subtotal," +
                "Decode(TOTAL_FREIGHT_COST, null, '0.00', 0, '0.00', LTRIM(TO_CHAR(TOTAL_FREIGHT_COST, '99999.99'))) freight," +
                "Decode(TOTAL_MISC_COST, null, '0.00', 0, '0.00', LTRIM(TO_CHAR(TOTAL_MISC_COST, '99999.99'))) misc_charge," +
                "Decode(TOTAL_TAX_COST, null, '0.00', 0, '0.00', LTRIM(TO_CHAR(TOTAL_TAX_COST, '99999.99'))) tax," +
                "nvl(ORIGINAL_AMOUNT,0) + " +
                "nvl(TOTAL_FREIGHT_COST,0) + " +
                "nvl(TOTAL_MISC_COST,0) + " +
                "nvl(TOTAL_TAX_COST,0) + " +
                "nvl(TOTAL_RUSH_CHARGE,0) as toatal, " +
                "o.order_status_cd status," +
                "order_type_cd type2," +
//                "op.CLW_VALUE internal_comments," +
                "o.locale_cd local_cd, " +
                "order_site_name  " +
                " from clw_order o" +
                 " where o.order_id  = ? ";

 //              " from clw_order o, clw_order_property op" +
 //               " where o.order_id  =  op.order_id (+)" +
 //               " and op.short_desc(+) = '" + RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS + "'" +
 //               " and o.order_id  = ? ";

        String addressSql = "select oa.address_type_cd address_type_cd , o.account_erp_num account_erp_num," +
                "address1, address2, address3, address4, " +
                "address1 ||' '||address2||' '||address3||' '||address4 as  street_address," +
                "city, STATE_PROVINCE_CD, POSTAL_CODE, COUNTRY_CD country " +
                " from clw_order_address oa, clw_order o " +
                " where o.order_id = ? " +
                " and o.order_id = oa.order_id" +
                " and address_type_cd in ('BILLING')" +
                " union all" +
                " select oa.address_type_cd, oa.short_desc shipto_name," +
                " address1, address2, address3, address4," +
                " address1 ||' '||address2||' '||address3||' '||address4 as  street_address," +
                " city, STATE_PROVINCE_CD, POSTAL_CODE, COUNTRY_CD country " +
                " from clw_order_address oa, clw_order o " +
                " where o.order_id = ? " +
                " and o.order_id = oa.order_id " +
                " and address_type_cd in ('SHIPPING')";

         String itemSql = "select DIST_ITEM_SKU_NUM sku_num,CUST_ITEM_SKU_NUM cust_sku_num," +
                "ITEM_SHORT_DESC item_name," +
                "oi.item_size," +
                "DIST_ITEM_UOM UOM," +
                "DIST_ITEM_PACK PACK," +
                "DIST_ITEM_COST COST2," +
                "CUST_CONTRACT_PRICE CUST_COST," +
                "TOTAL_QUANTITY_ORDERED qty," +
                "MANU_ITEM_SHORT_DESC manufacturer, " +
                "oi.ITEM_ID, " +
                "oi.ORDER_ITEM_ID, " +
                "oi.ERP_PO_LINE_NUM, " +
                "oi.SERVICE_FEE " +
                " from clw_order_item oi "+
                " where order_id =  ? " +
                " and lower(nvl(ORDER_ITEM_STATUS_CD,'ordered'))!='cancelled'" +
                " order by order_line_num";


        String metaSql = "select clw_value from clw_order_meta where order_id = ? and name='" + RefCodeNames.CHARGE_CD.DISCOUNT + "'";


        PreparedStatement pstmt = conn.prepareStatement(orderSql);
        pstmt.setInt(1, orderId);
        ResultSet rs = pstmt.executeQuery();
        OrderInfoView orderInfo = null;
        while (rs.next()) {

            if (orderInfo != null) throw new Exception("Order multiple record");

            orderInfo = OrderInfoView.createValue();
            orderInfo.setOrderId(orderId);
            orderInfo.setStoreId(rs.getInt("store_id"));
            orderInfo.setAccountId(rs.getInt("account_id"));
            orderInfo.setSiteId(rs.getInt("site_id"));
            orderInfo.setRefOrderId(rs.getInt("ref_order_id"));
            orderInfo.setRefOrderNum(rs.getString("ref_num"));
            orderInfo.setOrderNum(rs.getString("order_num"));
            orderInfo.setOrderDate(rs.getDate("order_date"));
            orderInfo.setPoNumber(rs.getString("po_number"));
            orderInfo.setSource(rs.getString("source"));
            orderInfo.setContactName(rs.getString("contact_name"));
            orderInfo.setContactPhone(rs.getString("contact_phone"));
            orderInfo.setContactEmail(rs.getString("contact_email"));
            orderInfo.setPlacedBy(rs.getString("placed_by"));
            orderInfo.setComments(rs.getString("comments"));
            orderInfo.setSubtotal(rs.getBigDecimal("subtotal"));
            orderInfo.setFreight(rs.getBigDecimal("freight"));
            orderInfo.setMiscCharge(rs.getBigDecimal("misc_charge"));
            orderInfo.setTax(rs.getBigDecimal("tax"));
            orderInfo.setToatal(rs.getBigDecimal("toatal"));
            orderInfo.setOrderStatusCd(rs.getString("status"));
            orderInfo.setOrderTypeCd(rs.getString("type2"));
//            orderInfo.setInternalComments(rs.getString("internal_comments"));
            orderInfo.setLocaleCd(rs.getString("local_cd"));
            orderInfo.setOrderSiteName(rs.getString("order_site_name"));
        }

        rs.close();
        pstmt.close();

        boolean substNullWithUsaFl = true;
        String allowAlternateShipToSql = "select property_id from clw_property where bus_entity_id = ?" +
                                         " AND short_desc = '" + RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ALTERNATE_SHIP_TO_ADDRESS + 
                                         "' AND lower(clw_value) = 'true'";
        if (orderInfo.getAccountId() > 0) {
            pstmt = conn.prepareStatement(allowAlternateShipToSql);
            pstmt.setInt(1, orderInfo.getAccountId());
            rs = pstmt.executeQuery();
            int propertyId = 0;
            while (rs.next()) {
                propertyId = rs.getInt("property_id");
                if (propertyId > 0) {
                    substNullWithUsaFl = false;
                    break;
                }
            }

            rs.close();
            pstmt.close();
        }

        pstmt = conn.prepareStatement(addressSql);
        pstmt.setInt(1, orderId);
        pstmt.setInt(2, orderId);
        rs = pstmt.executeQuery();
        AddressInfoView address = null;
        AddressInfoView shippingAddress = null;
        AddressInfoView billingAddress = null;
        String country;
        while (rs.next()) {
        	String addressTypeCd = rs.getString("address_type_cd");
        	address = AddressInfoView.createValue();
        	address.setAddressTypeCd(addressTypeCd);
        	address.setAccountErpNum(rs.getString("account_erp_num"));
        	address.setAddress1(rs.getString("address1"));
        	address.setAddress2(rs.getString("address2"));
        	address.setAddress3(rs.getString("address3"));
        	address.setAddress4(rs.getString("address4"));
        	address.setStreetAddress(rs.getString("street_address"));
        	address.setCity(rs.getString("city"));
        	address.setStateProvinceCd(rs.getString("STATE_PROVINCE_CD"));
        	address.setPostalCode(rs.getString("POSTAL_CODE"));
        	country = rs.getString("country");
                if (!Utility.isSet(country) && substNullWithUsaFl) {
                    country = "UNITED STATES";
                }
                address.setCountry(country);


            if (RefCodeNames.ADDRESS_TYPE_CD.SHIPPING.equals(addressTypeCd)) {
                if (shippingAddress != null) throw new Exception("Address multiple record");
                shippingAddress = address;

            } else if (RefCodeNames.ADDRESS_TYPE_CD.BILLING.equals(addressTypeCd)) {
                if (billingAddress != null) throw new Exception("Address multiple record");
                billingAddress = address;
            } else {
                throw new Exception("Unknown address type cd");
            }
        }
        rs.close();
        pstmt.close();

        // get discount from order meta
        pstmt = conn.prepareStatement(metaSql);
        pstmt.setInt(1, orderId);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            String discountS = rs.getString("clw_value");
            try {
                BigDecimal discount = new BigDecimal(discountS);
                if (discount.doubleValue() > 0) {
                    discount.negate();
                }
                orderInfo.setDiscount(discount);
            } catch (Exception e) {
            }
        }

        pstmt = conn.prepareStatement(itemSql);
        pstmt.setInt(1, orderId);
        rs = pstmt.executeQuery();
        ItemInfoViewVector items = new ItemInfoViewVector();
        while (rs.next()) {
            ItemInfoView itemInfo = ItemInfoView.createValue();
            itemInfo.setItemId(rs.getInt("ITEM_ID"));
            itemInfo.setOrderItemId(rs.getInt("ORDER_ITEM_ID"));
            itemInfo.setPoLineNum(rs.getInt("ERP_PO_LINE_NUM"));
            itemInfo.setServiceFee(rs.getBigDecimal("SERVICE_FEE"));
            itemInfo.setSkuNum(rs.getString("sku_num"));
            itemInfo.setCustSkuNum(rs.getString("cust_sku_num"));
            itemInfo.setItemName(rs.getString("item_name"));
            itemInfo.setItemSize(rs.getString("item_size"));
            itemInfo.setUom(rs.getString("UOM"));
            itemInfo.setPack(rs.getString("PACK"));
            itemInfo.setCost(rs.getBigDecimal("COST2"));
            itemInfo.setCustCost(rs.getBigDecimal("CUST_COST"));
            itemInfo.setQty(rs.getBigDecimal("qty"));
            itemInfo.setManufacturer(rs.getString("manufacturer"));

            items.add(itemInfo);
        }
        rs.close();
        pstmt.close();

        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(OrderPropertyDataAccess.ORDER_ID, orderId);
        crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD, RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS);
        OrderPropertyDataVector orderPropDataV = OrderPropertyDataAccess.select(conn, crit);


        OrderInfoDataView infoData = OrderInfoDataView.createValue();

        infoData.setOrderInfo(orderInfo);
        infoData.setBillingAddress(billingAddress);
        infoData.setShippingAddress(shippingAddress);
        infoData.setItems(items);
        infoData.setInternalComments(orderPropDataV);



        return infoData;

    }

    public OrderPropertyData getEventOrderProperty(int pOrderId, String shortDesc) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return OrderDAO.getEventOrderProperty(conn, pOrderId, shortDesc);
        } catch (Exception exc) {
            String em = "Error. getOrderInfoDataView. " + exc.getMessage();
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }

    public Collection saveInventoryOrderQtyLog(Collection qtyLog,UserData user) throws RemoteException{
        Connection conn = null;
        try {
            conn = getConnection();
            return OrderDAO.saveInventoryOrderQtyLog(conn,qtyLog,user);
        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * Returns a list of orders that match this customer po number from a given date.
     * Can be used to check if a customer has entered this po number before since a
     * particular date.
     * @param requestPoNum the po number the customer used (exact match)
     * @param accountId the account id to look for this po number
     * @param fromDate the date from which to begin searching. Uses revised order date then defaults to original order date
     * @return list of orders
     */
    public OrderDataVector getOrderByCustomerPoNumber(String requestPoNum, int accountId, Date fromDate) throws RemoteException{
    	log.info("getOrderByCustomerPoNumber.");
    	Connection conn = null;
        try {
            conn = getConnection();
	    	DBCriteria crit = new DBCriteria();
	    	crit.addEqualTo(OrderDataAccess.REQUEST_PO_NUM, requestPoNum);
	    	crit.addEqualTo(OrderDataAccess.ACCOUNT_ID, accountId);
	    	crit.addGreaterOrEqual("NVL("+OrderDataAccess.REVISED_ORDER_DATE+","+OrderDataAccess.ORIGINAL_ORDER_DATE+") ", fromDate);
	    	OrderDataVector orders = OrderDataAccess.select(conn, crit);
	    	return orders;
        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }

    public OrderDataVector getOrdersByItem(int workOrderItemId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderAssocDataAccess.WORK_ORDER_ITEM_ID, workOrderItemId);
            crit.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_CD, RefCodeNames.ORDER_ASSOC_CD.WORK_ORDER_PART);
            crit.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_STATUS_CD, RefCodeNames.WORK_ORDER_ASSOC_STATUS_CD.ACTIVE);
            //crit.addOrderBy(OrderAssocDataAccess.ADD_DATE);
            IdVector orderIds = OrderAssocDataAccess.selectIdOnly(conn, OrderAssocDataAccess.ORDER2_ID, crit);

            OrderDataVector orders = OrderDataAccess.select(conn, orderIds);
            return orders;
        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }


    public InvoiceDistData updateInvoiceDist(InvoiceDistData pInvoiceDist) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            InvoiceDistData invoiceDist = pInvoiceDist;
            if (invoiceDist.isDirty())  {
                if (invoiceDist.getInvoiceDistId() == 0)  {
                    InvoiceDistDataAccess.insert(conn, invoiceDist);
                } else {
                    InvoiceDistDataAccess.update(conn, invoiceDist);
                }
            }
            int invoiceCustId = pInvoiceDist.getInvoiceDistId();
        } catch (Exception e) {
            throw new RemoteException("updateInvoiceDist: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return pInvoiceDist;
    }

    public BackorderDataVector getBackorderDetail(int orderId, int orderItemId)throws RemoteException{
    	Connection conn = null;
    	BackorderDataVector boDV = new BackorderDataVector();
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            
            crit.addEqualTo(OrderItemDataAccess.ORDER_ID, orderId);
            crit.addEqualTo(OrderItemDataAccess.ORDER_ITEM_ID, orderItemId);
            OrderItemDataVector oiDV = OrderItemDataAccess.select(conn, crit);
           
            OrderItemData oiD = (OrderItemData)oiDV.get(0);
            if(oiD!=null){
            	String po_num = oiD.getOutboundPoNum();
            	String dist_sku = oiD.getDistItemSkuNum();
            	
            	crit = new DBCriteria();
            	crit.addEqualTo(BackorderDataAccess.PO_NUM, po_num);
            	crit.addEqualTo(BackorderDataAccess.ITEM_NUM, dist_sku);
            	
            	BackorderDataVector bodv = BackorderDataAccess.select(conn, crit);
            	if(bodv!=null && bodv.size()>0){
            		BackorderData bod = (BackorderData)bodv.get(0);
            		boDV.add(bod);
            	}
            }
            
        } catch (Exception e) {
            throw new RemoteException("getBackorderDetail: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return boDV;
    }

    public OrderDataVector getReplacedOrdersFor(int pOrderId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getReplacedOrdersFor(conn, pOrderId);
        } catch (Exception e) {
            throw new RemoteException("getReplacedOrdersFor: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public OrderDataVector getReplacedOrdersFor(Connection pCon, int pOrderId) throws Exception {

        DBCriteria dbc = new DBCriteria();

        dbc.addJoinTableEqualTo(OrderAssocDataAccess.CLW_ORDER_ASSOC, OrderAssocDataAccess.ORDER2_ID, pOrderId);
        dbc.addJoinTableEqualTo(OrderAssocDataAccess.CLW_ORDER_ASSOC, OrderAssocDataAccess.ORDER_ASSOC_CD, RefCodeNames.ORDER_ASSOC_CD.CONSOLIDATED);
        dbc.addJoinCondition(OrderDataAccess.ORDER_ID, OrderAssocDataAccess.CLW_ORDER_ASSOC, OrderAssocDataAccess.ORDER1_ID);

        return OrderDataAccess.select(pCon, dbc);

    }
    
    public void updateOrderItemStatusCd(OrderItemData pOrderItem) throws RemoteException {
         Connection conn = null;
         int n;
         try {
             conn = getConnection();
             n = OrderItemDataAccess.update(conn, pOrderItem);
             log.info("updateOrderItemStatusCd(): quantity of changed rows in the DB table = " + n);
         } catch (Exception e) {
             throw new RemoteException("updateOrderItemStatusCd: " + e.getMessage());
         } finally {
             closeConnection(conn);
         }        
    }
    
    public void updateOrderToWorkOrderAssoc(int orderId, int workOrderItemId, String userName) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderAssocDataAccess.ORDER2_ID, orderId);
            crit.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_CD, RefCodeNames.ORDER_ASSOC_CD.WORK_ORDER_PART);
            crit.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_STATUS_CD, RefCodeNames.ORDER_ASSOC_STATUS_CD.ACTIVE);
            OrderAssocDataVector woAssocV = OrderAssocDataAccess.select(conn, crit);
            
            if (!woAssocV.isEmpty()) {
                Iterator it = woAssocV.iterator();
                OrderAssocData orderAssoc;
                while(it.hasNext()) {
                    orderAssoc = (OrderAssocData) it.next();
                    OrderAssocDataAccess.remove(conn, orderAssoc.getOrderAssocId());
                }
            }
            
            OrderAssocData orderAssocD = OrderAssocData.createValue();
            orderAssocD.setOrder2Id(orderId);
            orderAssocD.setOrderAssocCd(RefCodeNames.ORDER_ASSOC_CD.WORK_ORDER_PART);
            orderAssocD.setOrderAssocStatusCd(RefCodeNames.ORDER_ASSOC_STATUS_CD.ACTIVE);
            orderAssocD.setAddBy(userName);
            orderAssocD.setModBy(userName);
            orderAssocD.setWorkOrderItemId(workOrderItemId);
            OrderAssocDataAccess.insert(conn, orderAssocD);
        } catch (Exception e) {
             throw new RemoteException("updateOrderToWorkOrderAssoc: " + e.getMessage());
         } finally {
             closeConnection(conn);
         }
    }
    
    public WorkOrderData getAssociatedWorkOrder(int orderId) throws RemoteException {
        Connection conn = null;
        WorkOrderData woD = null;
        try {
            conn = getConnection();
            
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderAssocDataAccess.ORDER2_ID, orderId);
            crit.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_CD, RefCodeNames.ORDER_ASSOC_CD.WORK_ORDER_PART);
            crit.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_STATUS_CD, RefCodeNames.ORDER_ASSOC_STATUS_CD.ACTIVE);
            OrderAssocDataVector woAssocV = OrderAssocDataAccess.select(conn, crit);
            
            if (!woAssocV.isEmpty()) {
                OrderAssocData orderAssoc = (OrderAssocData)woAssocV.get(0);
                if (orderAssoc.getWorkOrderItemId() > 0) {
                    WorkOrderItemData woiD = WorkOrderItemDataAccess.select(conn, orderAssoc.getWorkOrderItemId());
                    if (woiD != null && woiD.getWorkOrderId() > 0) {
                        woD = WorkOrderDataAccess.select(conn, woiD.getWorkOrderId());
                    }
                }
            }
        } catch (Exception e) {
            throw new RemoteException("getAssociatedWorkOrder: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    return woD;
    }

    public IdVector getAssociatedServiceTickets(int orderId) throws RemoteException {

        Connection conn = null;
        IdVector servTicketNumbers = null;

        try {

            servTicketNumbers = new IdVector();

            conn = getConnection();

            DBCriteria crit = new DBCriteria();

            crit.addEqualTo(OrderAssocDataAccess.ORDER2_ID, orderId);
            crit.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_CD, RefCodeNames.ORDER_ASSOC_CD.SERVICE_TICKET);
            crit.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_STATUS_CD, RefCodeNames.ORDER_ASSOC_STATUS_CD.ACTIVE);
            crit.addOrderBy(OrderAssocDataAccess.SERVICE_TICKET_ID);

            OrderAssocDataVector orderAssocV = OrderAssocDataAccess.select(conn, crit);

            if (!orderAssocV.isEmpty()) {                              
                for (Object oOrderAssoc : orderAssocV) {
                    servTicketNumbers.add(((OrderAssocData) oOrderAssoc).getServiceTicketId());
                }
            }

        } catch (Exception e) {
            throw new RemoteException("getAssociatedServiceTickets: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }

        return servTicketNumbers;

    }

    public int getOrderItemQuantity(int restrictDays, int itemId, int siteId )
        throws RemoteException {

      Connection connection = null;
      int totQtyOrderedPerItem = 0;
      try {
          connection = getConnection();
          /*** working correct !!!
  		  select sum(oi.total_quantity_ordered) TOTAL_QTY_ORDERED_PER_ITEM 
	      from clw_order_item oi, clw_order o 
	      where oi.item_id = 99814
	        and oi.order_id = o.order_id 
	        and o.order_status_cd != 'Cancelled' 
	        and o.order_status_cd != 'Rejected' 
	        and o.original_order_date >= sysdate - 5
          and o.site_id = 539494;
	        
	      ***/
    	  String sql = 
    		  "select sum(oi.total_quantity_ordered) TOTAL_QTY_ORDERED_PER_ITEM " +
    	      "from clw_order_item oi, clw_order o " +
    	      "where oi.item_id = ? " +
    	      "and oi.order_id = o.order_id " +
    	      "and o.order_status_cd != 'Cancelled' " +
    	      "and o.order_status_cd != 'Rejected' " +
    	      "and o.original_order_date >= sysdate - ? " +
    	      "and o.site_id = ?";
    	  
          log.info("getOrderItemQuantity sql = " + sql);
          
          PreparedStatement stmt = connection.prepareStatement(sql);
          stmt.setInt(1, itemId);
          stmt.setInt(2, restrictDays);
          stmt.setInt(3, siteId);
          ResultSet resSet = stmt.executeQuery();

          while (resSet.next()) {
              totQtyOrderedPerItem = resSet.getInt("TOTAL_QTY_ORDERED_PER_ITEM");
          }
          resSet.close();
          stmt.close();
      }
      catch (Exception ex) {
          log.info("[OrderBean.getOrderItemQuantity] " + ex.getMessage());
          throw new RemoteException(ex.getMessage());
      }
      finally {
          try {
              if (connection != null) {
                  connection.close();
              }
          } catch (Exception ex) {
          }
      }
      return totQtyOrderedPerItem;
    }
    
    
// Location Budget
    
    /**
     * This method returns the orders in pending approval status
     * @param pOrderStatusCriteria
     * @return OrderStatusDescDataVector
     */
    public OrderStatusDescDataVector getOrderStatusDesc(OrderStatusCriteriaData pOrderStatusCriteria)
    throws RemoteException
    {

    OrderStatusDescDataVector orderStatusDescV = new OrderStatusDescDataVector();
    Connection conn = null;

    BusEntityDAO beDAO = mCacheManager.getBusEntityDAO();
    try
    {
        OrderDataVector orderStatusV = getOrderStatusCollection(pOrderStatusCriteria);

        if (null == orderStatusV ) {
            return orderStatusDescV;
        }

        conn = getConnection();

        for (int i = 0; i < orderStatusV.size(); i++)
        {
            OrderData orderStatusD = (OrderData)orderStatusV.get(i);
            OrderStatusDescData orderStatusDescD = OrderStatusDescData.createValue();
            orderStatusDescD.setOrderDetail(orderStatusD);

            //get the order item data
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderItemDataAccess.ORDER_ID,orderStatusD.getOrderId());
            OrderItemDataVector orderItemV = OrderItemDataAccess.select(conn, crit);

            //orderStatusDescD.setAllowModifFl(calcAllowModifFlag(conn, orderStatusD));
            orderStatusDescD.setOrderItemList(orderItemV);
            orderStatusDescV.add(orderStatusDescD);
        }
    }
    catch(Exception e)
    {
    	 throw new RemoteException("getOrderStatusDesc: " + e.getMessage());
    }
    finally{
    	try{
    	conn.close();
    	}
    	catch(Exception ex){
    		throw new RemoteException("getOrderStatusDesc: " + ex.getMessage());
    	}
    }
    return orderStatusDescV;
  }
    
    /**
     * Get order property data vector according to the orderId
     * @param pOrderId to seach ALL order properties for a specific Order Id
     * @return an <code>OrderPropertyDataVector</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderPropertyDataVector getOrderPropertyCollectionByOrderId(int pOrderId)
    throws RemoteException,
                DataNotFoundException
    {

        OrderPropertyDataVector orderPropertyVec = new OrderPropertyDataVector();
        Connection conn = null;

        try
        {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderPropertyDataAccess.ORDER_ID, pOrderId);            
            crit.addOrderBy(OrderPropertyDataAccess.ORDER_PROPERTY_ID, false);
            orderPropertyVec = OrderPropertyDataAccess.select(conn, crit);
        }
        catch (Exception e)
        {
            throw new RemoteException("getOrderPropertyCollectionByOrderId: " +
                                      e.getMessage());
        }
        finally
        {
            closeConnection(conn);
        }
        return orderPropertyVec;
    }
    //site data is needed for mobile All-Orders for displaying unspent budget for 
  	//orders in pending approval status.
      public SiteData getSiteData(OrderData pOrderData) throws Exception {
  			SiteData siteData = null;
  			Connection con = null;
  			try {
  				con = getConnection();
  				siteData = getSiteData(con, pOrderData);
  			} 
  			finally {
  				closeConnection(con);
  			}
  			return  siteData;
  }
      
      public BigDecimal getTotalBudgetForOrder(OrderData orderD)
      throws Exception {
    	  APIAccess factory = new APIAccess();
    	  Connection conn = null;
    	  //Calculate the order total
		  BigDecimal orderTotal = new BigDecimal(0.00);		  

    	  try
    	  {
    		  conn = getConnection();
    		  Site  siteEjb  = factory.getSiteAPI();
    		  SiteData siteD = siteEjb.getSite(orderD.getSiteId(), orderD.getAccountId());

    		  OrderItemDataVector orderItemDV = getOrderItemCollection(orderD.getOrderId());

    		  ArrayList ccParams = PipelineCalculationOperations.loadCostCentersInfo(orderItemDV);
    		  HashMap ccSum = new HashMap();
    		  if(ccParams!=null && !ccParams.isEmpty()){                
    			  ccSum = PipelineCalculationOperations.recalculteCostCenterInfo(conn,orderD,ccParams,false) ;
    			  Iterator it = ccSum.keySet().iterator();
    			  while(it.hasNext()){
    				  Integer thisCostCenterIdKey = (Integer) it.next();
    				  if (thisCostCenterIdKey.intValue() == 0)
    					  continue;
    				  if (siteD.isBudgetUnlimited(thisCostCenterIdKey.intValue()))
    					  continue;
    				  BigDecimal thisCostCenterSum = (BigDecimal) ccSum.get(thisCostCenterIdKey);
    				  orderTotal = orderTotal.add(thisCostCenterSum);
    			  }
    		  }
    	  } 
    	  finally {
    		  closeConnection(conn);
    	  }
    	  return orderTotal;
      }
  		    

}
