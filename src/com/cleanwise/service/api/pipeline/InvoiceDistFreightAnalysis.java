/*
 * InvoiceDistFreightAnalysis.java
 *
 * Created on August 1, 2005, 3:21 PM
 */

package com.cleanwise.service.api.pipeline;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Iterator;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.InvoiceDistDataAccess;
import com.cleanwise.service.api.dao.OrderAddressDataAccess;
import com.cleanwise.service.api.dao.OrderFreightDataAccess;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.dao.PurchaseOrderDataAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DistributorInvoiceFreightTool;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.BusEntityTerrViewVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.FreightHandlerView;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDataVector;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.OrderAddressDataVector;
import com.cleanwise.service.api.value.OrderFreightDataVector;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.PurchaseOrderData;
import com.cleanwise.service.api.value.PurchaseOrderDataVector;
/**
 *Anylizes the distributor invoice against the freight selection criteria.
 * @author bstevens
 */
public class InvoiceDistFreightAnalysis implements InvoiceDistPipeline{
    Connection mCon;

    public void process(
            InvoiceDistPipelineBaton pBaton,
            Connection pCon,
            APIAccess pFactory)
            throws PipelineException{
        try{
            InvoiceDistData invoice = pBaton.getInvoiceDistData();
            InvoiceDistDetailDataVector invoiceItems = pBaton.getInvoiceDistDetailDataVector();
            mCon = pCon;
            BigDecimal frt = invoice.getFreight();
            if(frt != null){
                pBaton.setSingularProperty(frt.toString(), RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_FREIGHT, pCon, true);
            }
            BigDecimal miscCharge = invoice.getMiscCharges();
            if(miscCharge != null){
                pBaton.setSingularProperty(miscCharge.toString(), RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_MISC_CHARGE, pCon, true);
            }

            if(pBaton.isInvoiceApproved()){
                return;
            }

            //cannot evaluate freght without distributor
            if(invoice.getBusEntityId() == 0){
                return;
            }

            //fetch the criteria we need
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(InvoiceDistDataAccess.ERP_PO_NUM,invoice.getErpPoNum());
            dbc.addEqualTo(InvoiceDistDataAccess.STORE_ID,invoice.getStoreId());
            InvoiceDistDataVector existingInvoices = InvoiceDistDataAccess.select(pCon,dbc);
            Hashtable terrCond = new Hashtable();
            String zip = null;
            if(invoice.getShipToPostalCode() != null){
              zip = invoice.getShipToPostalCode();
            }else{
              DBCriteria crit = new DBCriteria();
              crit.addEqualTo(OrderAddressDataAccess.ORDER_ID,invoice.getOrderId());
              crit.addEqualTo(OrderAddressDataAccess.ADDRESS_TYPE_CD,RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
              OrderAddressDataVector oadv = OrderAddressDataAccess.select(mCon, crit);
              if(oadv != null && !oadv.isEmpty()){
                OrderAddressData oa = (OrderAddressData) oadv.get(0);
                zip = oa.getPostalCode();
              }
            }
          if(zip != null){
            terrCond.put("postalCode", zip);
          }else{
            //add dummy zip so we don't pull it up as erroniously being part of the territory
              terrCond.put("postalCode", "00000");
          }
            BusEntityTerrViewVector distTerr =
                    pFactory.getDistributorAPI().getDistributorZipCodes(invoice.getBusEntityId(),terrCond);
            DistributorData distributor = pBaton.getDistributorForInvoice(pFactory);
            OrderItemDataVector orderItems = getOrderItems(invoiceItems);
            FreightHandlerView freightHandler = getFreightHandlerUsed(orderItems);

            OrderFreightDataVector orderFreight = null;
            PurchaseOrderData po = getPo(invoice, distributor.getBusEntity().getErpNum());
            if(po != null && po.getOrderId() != 0){
              dbc = new DBCriteria();
              dbc.addEqualTo(OrderFreightDataAccess.ORDER_ID,po.getOrderId());
              dbc.addEqualTo(OrderFreightDataAccess.BUS_ENTITY_ID,distributor.getBusEntity().getBusEntityId());
            orderFreight = OrderFreightDataAccess.select(pCon,dbc);
            }

            //anylize frieght
            //check that we are not being over charged freight
            //based off minimum order
            DistributorInvoiceFreightTool freightTool =
                    new DistributorInvoiceFreightTool(getPoItemTotal(po),orderItems,
                    invoice,distributor,
                    freightHandler,
                    existingInvoices,
                    distTerr,
                    orderFreight
                    );

            if(freightTool.isOverChargedFreight() || freightTool.isFreightOverMax() ){
                if (Boolean.TRUE.equals(distributor.getExceptionOnOverchargedFreight())){
                    //pBaton.addError("Freight was overcharged");
                    pBaton.addError("pipeline.message.overchargeFreight");
                } else if (freightTool.isFreightOverMax()) {
                    String msg = "Freight charged is over the maximum allowed";
                    pBaton.addError("pipeline.message.freightChargedOverMax");
                }else{
                    //if we are going to let it flow through the system set the frieght
                    //to be zero, as we are not going to pay it without talking to the
                    //ditributor (vendor)
                    invoice.setFreight(new BigDecimal(0));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
    }


    //parses out the order items and fetchs them for the invoice items
    private OrderItemDataVector getOrderItems(InvoiceDistDetailDataVector invoiceItems) throws Exception {
        OrderItemDataVector toReturn = new OrderItemDataVector();
        Iterator it = invoiceItems.iterator();
        while(it.hasNext()){
            InvoiceDistDetailData id = (InvoiceDistDetailData) it.next();
            if(id.getOrderItemId() > 0){
                toReturn.add(OrderItemDataAccess.select(mCon,id.getOrderItemId()));
            }
        }
        return toReturn;
    }

    //gets the fright handler used for these order items
    private FreightHandlerView getFreightHandlerUsed(OrderItemDataVector orderItems) throws Exception{
        Iterator it = orderItems.iterator();
        while ( null != it && it.hasNext() ) {
            OrderItemData oid = (OrderItemData)it.next();
            if ( oid.getFreightHandlerId() > 0 ) {
                return BusEntityDAO.getFreightHandler(mCon, oid.getFreightHandlerId());
            }
        }
        return null;
    }


    private PurchaseOrderData getPo(InvoiceDistData inv, String distErp) throws SQLException{
      DBCriteria crit = new DBCriteria();
        crit.addEqualTo(PurchaseOrderDataAccess.ERP_PO_NUM,inv.getErpPoNum());
        crit.addEqualTo(PurchaseOrderDataAccess.STORE_ID,inv.getStoreId());
        PurchaseOrderDataVector podv = PurchaseOrderDataAccess.select(mCon,crit);
        if(podv.size() > 0){
            return (PurchaseOrderData) podv.get(0);
        }
        return null;
    }

    //gets the po line totoal from the purchaseorderdata object in the db
    private BigDecimal getPoItemTotal(PurchaseOrderData po) throws SQLException{
      if(po != null){
        return po.getLineItemTotal();
      }
      return new BigDecimal(0);
    }

}
