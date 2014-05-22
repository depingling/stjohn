/*
 * InvoiceDistPipelineBaton.java
 *
 * Created on August 1, 2005, 10:08 PM
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.InvoiceDistDataAccess;
import com.cleanwise.service.api.dao.InvoiceDistDetailDataAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.dao.OrderPropertyDataAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.PipelineUtil;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDataVector;
import com.cleanwise.service.api.value.InvoiceDistDescData;
import com.cleanwise.service.api.value.InvoiceDistDescDataVector;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;



/**
 * The baton that is passed through the invoice dist pipeline
 * @author bstevens
 */
public class InvoiceDistPipelineBaton {
    private boolean isInvoiceApproved = false;
    private LinkedList invoiceErrors = new LinkedList();
    private String mWhatNext = null;
    private HashMap cache = new HashMap();

    //Return codedes
    public static final String STOP = "STOP_AND_RETURN";
    public static final String GO_NEXT = "GO_NEXT";
    public static final String REPEAT = "REPEAT";
    public static final String GO_FIRST_STEP = "GO_FIRST_STEP";

    /**
     *Returns what the pipeline should do next.
     */
    public String getWhatNext(){
        if(mWhatNext == null){
            mWhatNext = GO_NEXT;
        }
        return mWhatNext;
    }
    /**
     *Sets the what next step in the pipeline.  By default the next step in the pipeline is executed.  It is only
     *necessary to set this if you want to overide this behavior.
     *valid arguments are: @see STOP, @see GO_NEXT, @see REPEAT, @see GO_FIRST_STEP
     */
    public void setWhatNext(String val){
        mWhatNext = val;
    }

    /** Creates a new instance of InvoiceDistPipelineBaton */
    public InvoiceDistPipelineBaton() {
    }


    public OrderItemDataVector getOrderItems(Connection pCon) throws Exception{
      OrderItemDataVector prev = (OrderItemDataVector) cache.get("orderItems");
        if(prev != null){
            return prev;
        }
        prev = new OrderItemDataVector();
        cache.put("orderItems",prev);
        if(getInvoiceDistDetailDataVector()==null){
          return prev;
        }
        IdVector oids = new IdVector();
        Iterator it = getInvoiceDistDetailDataVector().iterator();
        while(it.hasNext()){
          InvoiceDistDetailData idd = (InvoiceDistDetailData) it.next();
          oids.add(new Integer(idd.getOrderItemId()));
        }

        DBCriteria crit = new DBCriteria();
        crit.addOneOf(OrderItemDataAccess.ORDER_ITEM_ID,oids);
        prev.addAll(OrderItemDataAccess.select(pCon, crit));
        return prev;
    }

    /**
     * Holds value of property invoiceDistData.
     */
    private InvoiceDistData invoiceDistData;

    /**
     * Getter for property invoiceDistData.
     * @return Value of property invoiceDistData.
     */
    public InvoiceDistData getInvoiceDistData() {

        return this.invoiceDistData;
    }

    /**
     * Setter for property invoiceDistData.
     * @param invoiceDistData New value of property invoiceDistData.
     */
    public void setInvoiceDistData(InvoiceDistData invoiceDistData) {

        this.invoiceDistData = invoiceDistData;
    }

    /**
     * Holds value of property invoiceDistDetailDataVector.
     */
    private InvoiceDistDetailDataVector invoiceDistDetailDataVector;

    /**
     * Getter for property invoiceDistDetailDataVector.
     * @return Value of property invoiceDistDetailDataVector.
     */
    public InvoiceDistDetailDataVector getInvoiceDistDetailDataVector() {

        return this.invoiceDistDetailDataVector;
    }

    /**
     * Setter for property invoiceDistDetailDataVector.
     * @param invoiceDistDetailDataVector New value of property invoiceDistDetailDataVector.
     */
    public void setInvoiceDistDetailDataVector(InvoiceDistDetailDataVector invoiceDistDetailDataVector) {

        this.invoiceDistDetailDataVector = invoiceDistDetailDataVector;
    }


    /**
     *Returns true if this invoice has already been looked at by a user and the approval flag was set.
     */
    public boolean isInvoiceApproved(){
        return isInvoiceApproved;
    }

    private HashMap getOrderPropertiesCache(){
        HashMap orderProperties = (HashMap) cache.get("orderProperties");
        if(orderProperties == null){
            orderProperties = new HashMap();
            cache.put("orderProperties",orderProperties);
        }
        return orderProperties;
    }

    /**
     *Returns the order property as a String if it exists.  Implements lazy loading.
     */
    public String getOrderProperty(String pType, Connection pCon) throws SQLException{
        HashMap orderProperties =getOrderPropertiesCache();
        OrderPropertyData oppie;
        if(orderProperties.containsKey(pType)){
            oppie = (OrderPropertyData)orderProperties.get(pType);
        }else{
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(OrderPropertyDataAccess.ORDER_ID, this.invoiceDistData.getOrderId());
            dbc.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD, pType);

            OrderPropertyDataVector orderPropertyDV = OrderPropertyDataAccess.select(pCon, dbc);
            if (orderPropertyDV.size() >= 1) {
                 oppie = (OrderPropertyData)orderPropertyDV.get(0);
            }else{
                oppie = null;
            }
            orderProperties.put(pType,oppie);
        }
        if(oppie == null){
            return null;
        }else{
            return oppie.getValue();
        }
    }

    /**
     *Populates the baton based off he supplied invoice dist id
     */
    public void populateBaton(Connection pCon, int pInvoiceDistId) throws PipelineException{
        try{
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(InvoiceDistDetailDataAccess.INVOICE_DIST_ID,pInvoiceDistId);
            InvoiceDistDetailDataVector iddv = InvoiceDistDetailDataAccess.select(pCon,crit);
            this.setInvoiceDistDetailDataVector(iddv);
            this.setInvoiceDistData(InvoiceDistDataAccess.select(pCon,pInvoiceDistId));
            try{this.setOrder(OrderDataAccess.select(pCon,getInvoiceDistData().getOrderId()));}catch(DataNotFoundException e){}


            crit = new DBCriteria();
            crit.addEqualTo(OrderPropertyDataAccess.INVOICE_DIST_ID,pInvoiceDistId);
            crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,RefCodeNames.ORDER_PROPERTY_TYPE_CD.INVOICE_DIST_APPROVED);
            OrderPropertyDataVector opdv = OrderPropertyDataAccess.select(pCon,crit);
            //should really only be one, but if there are multiple, as long as one of them indicates that the freigh has already
            //been approved then we assume that the freight is approved.
            Iterator it = opdv.iterator();
            while(it.hasNext()){
                OrderPropertyData d = (OrderPropertyData) it.next();
                if(Utility.isTrue(d.getValue(),false)){
                    isInvoiceApproved = true;
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
    }

    /**
     *Saves the objects in the baton that the baton is managing.  That is currently identified as the invoiceDist
     *and invoiceDistDetailDataVector
     */
    public void saveBatonObjects(Connection pCon) throws PipelineException{
        try{
            //save the invoice
            InvoiceDistData invoice = this.getInvoiceDistData();
            if(isHasError()){
                invoice.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW);
            }
            if(invoice.getInvoiceDistId() == 0){
                InvoiceDistData newInvoice = InvoiceDistDataAccess.insert(pCon,invoice);
                invoice.setInvoiceDistId(newInvoice.getInvoiceDistId());
                invoice.setAddDate(newInvoice.getAddDate());
                invoice.setModDate(newInvoice.getModDate());
            }else{
                InvoiceDistDataAccess.update(pCon,invoice);
            }


            //save the invoice items
            InvoiceDistDetailDataVector invoiceItems = this.getInvoiceDistDetailDataVector();
            Iterator it = invoiceItems.iterator();
            while(it.hasNext()){
                InvoiceDistDetailData det = (InvoiceDistDetailData) it.next();
                if(det.getInvoiceDistDetailId() == 0){
                    InvoiceDistDetailData newDet = InvoiceDistDetailDataAccess.insert(pCon,det);
                    det.setInvoiceDistId(newDet.getInvoiceDistId());
                    det.setAddDate(newDet.getAddDate());
                    det.setModDate(newDet.getModDate());
                }else{
                    InvoiceDistDetailDataAccess.update(pCon,det);
                }
            }
            //save any error messages
            if(invoiceErrors != null){
                it = invoiceErrors.iterator();
                while(it.hasNext()){
                    AnError error = (AnError) it.next();
                    if(!error.isInserted()){
                    	//STJ-5604
                        String errMess =
                          PipelineUtil.translateMessage(error.messageKey, null,
                            error.arg0, error.arg0TypeCd,
                            error.arg1, error.arg1TypeCd,
                            error.arg2, error.arg2TypeCd,
                            null, null);
                        OrderDAO.enterOrderProperty(pCon,
                                                    RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE,
                                                    "Invoice Error",
                                                    errMess,
                                                    invoice.getOrderId(),
                                                    error.orderItemId,
                                                    invoice.getInvoiceDistId(),
                                                    error.invoiceDistDetailId,
                                                    0,0,0,
                                                    "System",
                                                    error.messageKey,
                                                    error.arg0, error.arg0TypeCd,
                                                    error.arg1, error.arg1TypeCd,
                                                    error.arg2,  error.arg2TypeCd);
                        error.setInserted(true);
                    }
                }
            }


        }catch(Exception e){
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
    }

    /**
     *Returns true if this invoice is in an error state.  Classes that should only run when there are no errors are
     *responsible for checking this property before running.
     */
    public boolean isHasError(){
        return !invoiceErrors.isEmpty();
    }


    public void addError(String messageKey) {
      addError(messageKey, null, null, null, null, null, null);
    }

    /**
     *Adds an error to this baton.  This will result in the invoice going into a pending review status
     *and any of the messages that were added being logged with the invoice.
     */
    public void addError(String messageKey,
                         String arg0, String arg0TypeCd,
                         String arg1, String arg1TypeCd,
                         String arg2, String arg2TypeCd){
      AnError error = new AnError(messageKey, arg0, arg0TypeCd, arg1, arg1TypeCd, arg2, arg2TypeCd, 0, 0);
      invoiceErrors.add(error);
    }

    public void addError(String messageKey,
                         String arg0, String arg0TypeCd,
                         String arg1, String arg1TypeCd,
                         String arg2, String arg2TypeCd,
                         int orderItemId, int invoiceDistDetailId){
      AnError error = new AnError(messageKey, arg0, arg0TypeCd, arg1, arg1TypeCd, arg2, arg2TypeCd, orderItemId, invoiceDistDetailId);
      invoiceErrors.add(error);
    }

    /**
     * Holds value of property order.
     */
    private OrderData order;

    /**
     * Getter for property order.
     * @return Value of property order.
     */
    public OrderData getOrder() {

        return this.order;
    }

    /**
     * Setter for property order.
     * @param order New value of property order.
     */
    public void setOrder(OrderData order) {

        this.order = order;
    }

    private DistributorData mDist;
    public DistributorData getDistributorForInvoice(APIAccess pFactory)
    throws Exception{
      if(mDist == null){
        int distId = getInvoiceDistData().getBusEntityId();
        mDist = pFactory.getDistributorAPI().getDistributor(distId);
      }
      return mDist;
    }

    /**
     * Returns any invoices that were created prior to the date of this invoice
     */
    public InvoiceDistDescDataVector getPreviousInvoices(Connection pCon) throws SQLException{
        InvoiceDistDescDataVector prev = (InvoiceDistDescDataVector) cache.get("previousInvoices");
        if(prev != null){
            return prev;
        }
        prev = new InvoiceDistDescDataVector();
        cache.put("previousInvoices",prev);
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(InvoiceDistDataAccess.BUS_ENTITY_ID,getInvoiceDistData().getBusEntityId());
        crit.addEqualTo(InvoiceDistDataAccess.ORDER_ID,getInvoiceDistData().getOrderId());
        crit.addLessOrEqual(InvoiceDistDataAccess.INVOICE_DATE,getInvoiceDistData().getInvoiceDate());
        crit.addNotEqualTo(InvoiceDistDataAccess.INVOICE_DIST_ID,getInvoiceDistData().getInvoiceDistId());
        InvoiceDistDataVector iddv = InvoiceDistDataAccess.select(pCon, crit);
        Iterator it = iddv.iterator();
        while(it.hasNext()){
            InvoiceDistData id = (InvoiceDistData) it.next();
            InvoiceDistDescData desc = InvoiceDistDescData.createValue();
            crit = new DBCriteria();
            crit.addEqualTo(InvoiceDistDetailDataAccess.INVOICE_DIST_ID,id.getInvoiceDistId());
            desc.setInvoiceDist(id);
            desc.setInvoiceDistDetailList(InvoiceDistDetailDataAccess.select(pCon,crit));
            prev.add(desc);
        }
        return prev;
    }


    /**
     *Sets an invoice property where there can only be one per invoice.  If that property already exists it is updated,
     *otherwise it is created.  If the addOnly attribute is set then the property will not be created if it exsists.
     *This allows components to log origianl values, and not update the values on subsequent run throughs of the pipeline
     */
    public void setSingularProperty(String value, String propertyName, Connection pCon, boolean addOnly)
      throws SQLException{
        DBCriteria crit = new DBCriteria();
        InvoiceDistData invoiceDistD = getInvoiceDistData();
        String user = invoiceDistD.getModBy();
        crit.addEqualTo(OrderPropertyDataAccess.INVOICE_DIST_ID,invoiceDistD.getInvoiceDistId());
        crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD,RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,propertyName);
        OrderPropertyDataVector opdv = OrderPropertyDataAccess.select(pCon,crit);
        if(opdv.size() == 0){
            if (value != null){
                OrderPropertyData opd = OrderPropertyData.createValue();
                opd.setAddBy(user);
                opd.setModBy(user);
                opd.setInvoiceDistId(invoiceDistD.getInvoiceDistId());
                opd.setOrderId(invoiceDistD.getOrderId());
                opd.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
                opd.setOrderPropertyTypeCd(propertyName);
                opd.setShortDesc(propertyName);
                opd.setValue(value);
                OrderPropertyDataAccess.insert(pCon, opd);
            }
        }else{
            if(!addOnly){
                OrderPropertyData opd = (OrderPropertyData) opdv.get(0);
                opd.setValue(propertyName);
                opd.setModBy(user);
                opd.setOrderId(invoiceDistD.getOrderId());
                OrderPropertyDataAccess.update(pCon, opd);
            }
        }
    }

    /**
     *Container class to hold the internal representation of a note and track the state  of it.
     */
    private class AnError{
        String messageKey;
        public String arg0;
        public String arg0TypeCd;
        public String arg1;
        public String arg1TypeCd;
        public String arg2;
        public String arg2TypeCd;

        public int orderItemId;
        public int invoiceDistDetailId;

        boolean inserted = false;

        private AnError(String pMessageKey,
                        String pArg0, String pArg0TypeCd,
                        String pArg1, String pArg1TypeCd,
                        String pArg2, String pArg2TypeCd,
                        int pOrderItemId, int pInvoiceDistDetailId) {
            messageKey = pMessageKey;
            arg0 = pArg0;
            arg0TypeCd = pArg0TypeCd;
            arg1 = pArg1;
            arg1TypeCd = pArg1TypeCd;
            arg2 = pArg2;
            arg2TypeCd = pArg2TypeCd;
            orderItemId = pOrderItemId;
            invoiceDistDetailId = pInvoiceDistDetailId;

        }
        public String toString(){
            return messageKey;
        }

        private boolean isInserted(){
            return inserted;
        }
        private void setInserted(boolean pInserted){
            inserted = pInserted;
        }
    }

}
