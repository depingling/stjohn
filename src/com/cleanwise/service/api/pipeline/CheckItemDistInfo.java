/*
 * CancelReplaceItemDuplication.java
 *
 * Created on August 25, 2003
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
import java.util.Locale;
import java.math.BigDecimal;



import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Collection;
/**
 * Pipeline class. Checks distributor data presence
 * @author  YKupershmidt
 */
public class CheckItemDistInfo  implements OrderPipeline
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
    OrderData orderD = pBaton.getOrderData();
    String orderStatusCd = orderD.getOrderStatusCd();
    pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);

    if(pBaton.getSimpleServiceOrderFl())
    {
        return pBaton;
    }

    OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
    for(int ii=0; ii<orderItemDV.size(); ii++) {
      OrderItemData orderItemD = (OrderItemData) orderItemDV.get(ii);
      int skuNum = orderItemD.getItemSkuNum();
      if(!Utility.isSet(orderItemD.getDistErpNum())){
         String custSkuNum = orderItemD.getCustItemSkuNum();
         int lineNum = orderItemD.getOrderLineNum();
         int qty = orderItemD.getTotalQuantityOrdered();
         if(custSkuNum==null) custSkuNum = "";
         
         String uom = orderItemD.getCustItemUom();
         String shortDesc = orderItemD.getCustItemShortDesc();
         BigDecimal price = orderItemD.getCustContractPrice();

         if(uom==null) uom="";
         if(shortDesc==null) shortDesc="";
         String priceStr = null;
         if(price == null) {
        	 priceStr="null"; 
         } else {
        	 priceStr=price.toString(); 
         }
        	 
         
         /*pBaton.addError(pCon, OrderPipelineBaton.NO_DISTRIBUTOR_FOR_SKU, null,
           RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW, orderItemD.getOrderLineNum(), 0,
           "pipeline.message.noDistributorForSku",
           ""+skuNum, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
           ""+lineNum, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
           custSkuNum, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);*/
         
         Object[] pArgs = new Object[6];
         String[] pArgTypes = new String[6];
         
         pArgs[0]=new Integer(skuNum);
         pArgTypes[0]=RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING;
         pArgs[1] = new Integer(lineNum);
         pArgTypes[1]=RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING;
         
         pArgs[2] = custSkuNum+","+shortDesc+","+uom+","+priceStr;
         pArgTypes[2]=RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING;
         
         pBaton.addError(pCon, OrderPipelineBaton.NO_DISTRIBUTOR_FOR_SKU, null,
                 RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW, orderItemD.getOrderLineNum(), 0,
                 "pipeline.message.noDistForSkuFullInfo",pArgs, pArgTypes);
         
      } else {
        String distItemSku = orderItemD.getDistItemSkuNum();
        if (!Utility.isSet(distItemSku)){
          String messKey = "pipeline.message.missingDistSku";
          String shortDesc ="CW Workflow Note";
           pBaton.addError(pCon, OrderPipelineBaton.MISSING_DIST_SKU,shortDesc,
                      RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW,
                      orderItemD.getOrderLineNum(), 0,
                      messKey,
                      ""+skuNum, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
        } else if(distItemSku.trim().toLowerCase(Locale.US).startsWith("cw")){
          String messKey = "pipeline.message.temporaryDistSku";
          String shortDesc ="CW Workflow Note";
           pBaton.addError(pCon, OrderPipelineBaton.MISSING_DIST_SKU,shortDesc,
                      RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW,
                      orderItemD.getOrderLineNum(),0,
                      messKey,
                      ""+skuNum, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
        }
        BigDecimal distCost = orderItemD.getDistItemCost();
      }

    }

    //Return
     pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
     return pBaton;
    }


}
