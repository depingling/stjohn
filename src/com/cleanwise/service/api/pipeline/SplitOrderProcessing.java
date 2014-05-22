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
import java.math.BigDecimal;
import java.sql.*;
import java.util.GregorianCalendar;
import java.util.Calendar;


import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Collection;
import java.util.Locale;
/**
 * Makes reference comments for split orders
 * @author  YKupershmidt (copied from IntegrationServicesBean)
 */
public class SplitOrderProcessing  implements OrderPipeline
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
    if (pBaton.getSimpleServiceOrderFl()) {
            return pBaton;
    }    
    OrderData orderD = pBaton.getOrderData();
    if (RefCodeNames.ORDER_TYPE_CD.SPLIT.equals(orderD.getOrderTypeCd()))
    	return pBaton;
    int preOrderId = orderD.getPreOrderId();
    String orderNum = orderD.getOrderNum();
    DBCriteria dbc = new DBCriteria();
    dbc.addEqualTo(OrderDataAccess.PRE_ORDER_ID,preOrderId);
    if(orderNum == null){
        dbc.addIsNotNull(OrderDataAccess.ORDER_NUM);
    }else{
        dbc.addNotEqualTo(OrderDataAccess.ORDER_NUM,orderNum);
    }
    OrderDataVector orderDV = OrderDataAccess.select(pCon,dbc);
    if(orderDV.size()==0) {
      return pBaton;
    }
    
    String refStr = "The original order has been split into "+ orderDV.size()+ 
                            " orders with numbers: "+orderNum +"(this order) ";
    for(Iterator iter=orderDV.iterator(); iter.hasNext();) {
      OrderData oD = (OrderData) iter.next();
      refStr += ", " + oD.getOrderNum();
    }

    //Save properties
    OrderPropertyData opD = OrderPropertyData.createValue();
    opD.setOrderId(orderD.getOrderId());
    opD.setShortDesc("Split order note");
    opD.setValue(refStr);
    opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
    opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
    opD.setAddBy(pBaton.getUserName());
    OrderPropertyDataAccess.insert(pCon, opD);
 
    //Return
     pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
     return pBaton;
    }
    catch(Exception exc) {
      exc.printStackTrace();
      throw new PipelineException(exc.getMessage());
    }
    finally{
    }
    }
        
}
