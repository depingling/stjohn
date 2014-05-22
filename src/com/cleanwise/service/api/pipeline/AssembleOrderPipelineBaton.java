/*
 * AssembleOrderPipelineBaton.java
 *
 * Created on April 11, 2005
 */

package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.ItemSkuMapping;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.dao.ItemSubstitutionDefDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import java.text.SimpleDateFormat;
import com.cleanwise.view.utils.Constants;



import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Collection;
/**
 * Pipeline class that gathers data to reprocess order.
 * @author  YKupershmidt
 */
public class AssembleOrderPipelineBaton  implements OrderPipeline
{
    /** Process this pipeline.
     *
     * @param OrderRequestData the order request object to act upon
     * @param Connection a active database connection
     * @param APIAccess
     *
     */
    public OrderPipelineBaton process(OrderPipelineBaton pBaton,
                OrderPipelineActor pActor,
                Connection pCon,
                APIAccess pFactory)
    throws PipelineException
    {
     try{
       pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);

       SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
       Date currentDate = sdf.parse(sdf.format(new Date()));
       pBaton.setCurrentDate(currentDate);


       OrderData orderD = pBaton.getOrderData();
       int orderId = orderD.getOrderId();
       orderD = OrderDataAccess.select(pCon,orderId);
       pBaton.setOrderData(orderD);

       String requestedStatus = pBaton.getOrderStatus();
       if(Utility.isSet(requestedStatus)) {
         orderD.setOrderStatusCd(requestedStatus);
       }

       DBCriteria dbc = new DBCriteria();
       dbc.addEqualTo(OrderMetaDataAccess.ORDER_ID,orderId);
       OrderMetaDataVector orderMetaDV =
          OrderMetaDataAccess.select(pCon,dbc);
       pBaton.setOrderMetaDataVector(orderMetaDV);
       for(Iterator iter =  orderMetaDV.iterator(); iter.hasNext();) {
         OrderMetaData omD = (OrderMetaData) iter.next();
         if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.PENDING_DATE.equals(omD.getName())) {
           String pendingDateS = omD.getValue();
           try {
             Date pendingDate = sdf.parse(pendingDateS);
             if(currentDate.before(pendingDate)) {
               pBaton.setOrderStatus(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE);
             }
           } catch (Exception exc) {
             //Wrong pendig date format. Generate note record.
             OrderPropertyData opD = OrderPropertyData.createValue();
             opD.setOrderId(orderId);
             //opD.setValue("Wrong order pending date format: "+pendingDateS);
             String messKey = "pipeline.message.wrongOrderPendingDate";
             opD.setMessageKey(messKey);
             opD.setArg0(pendingDateS);
             opD.setArg0TypeCd(RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
             opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
             opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
             opD.setShortDesc(pBaton.WRONG_HOLD_UNTIL_DATE);
             pBaton.addOrderPropertyData(opD);
           }
         }
       }

       dbc = new DBCriteria();
       dbc.addEqualTo(OrderDataAccess.ORDER_ID,orderId);
       OrderItemDataVector oiDV = OrderItemDataAccess.select(pCon,dbc);
       pBaton.setOrderItemDataVector(oiDV);

       dbc = new DBCriteria();
       dbc.addEqualTo(OrderAddressDataAccess.ORDER_ID,orderId);
       OrderAddressDataVector oaDV = OrderAddressDataAccess.select(pCon,dbc);
       for(Iterator iter = oaDV.iterator(); iter.hasNext(); ) {
         OrderAddressData oaD = (OrderAddressData) iter.next();
         if(RefCodeNames.ADDRESS_TYPE_CD.BILLING.
            equals(oaD.getAddressTypeCd())) {
           pBaton.setBillToData(oaD);
         } else if(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING.
            equals(oaD.getAddressTypeCd())) {
           pBaton.setShipToData(oaD);
         } else if(RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_BILLING.
            equals(oaD.getAddressTypeCd())) {
           pBaton.setCustBillToData(oaD);
         } else if(RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING.
            equals(oaD.getAddressTypeCd())) {
           pBaton.setCustShipToData(oaD);
         }
       }
      return pBaton;

    }catch(Exception e){
       e.printStackTrace();
        throw new PipelineException(e.getMessage());
    }finally{
    }
  }

}
