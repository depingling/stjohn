/*
 * SetOrderNumber.java
 *
 * Created on June 15, 2004, 4:36 PM
 */

package com.cleanwise.service.api.pipeline;
import java.sql.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.session.IntegrationServices;
import java.rmi.RemoteException;
/**
 *sets the order number if it is not already set and saves this in the database.
 * @author  bstevens
 */
public class SetOrderNumber  implements OrderPipeline{
    
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
    throws PipelineException {
        try{
            OrderData orderD = pBaton.getOrderData();
            setOrderNumber(orderD,pFactory,pCon);
            int orderId = orderD.getOrderId();
            if(orderId <=0 ) {
             orderD = OrderDataAccess.insert(pCon, orderD);
             pBaton.setOrderData(orderD);
           }  else {
             OrderDataAccess.update(pCon, orderD);
           }
            return pBaton;
        }catch(Exception e){
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
    }
    
    public void setOrderNumber(OrderData orderD, APIAccess pFactory, Connection pCon)
    throws APIServiceAccessException, RemoteException, SQLException{
        
        int storeId = orderD.getStoreId();
        String orderNumS =  orderD.getOrderNum();
        if(storeId>0 && (orderNumS==null || orderNumS.trim().length()==0)) {
            IntegrationServices intServEjb = pFactory.getIntegrationServicesAPI();
            int orderNum = intServEjb.getNextOrderNumber(pCon, storeId);
            orderD.setOrderNum(""+orderNum);
        }
    }
    
}
