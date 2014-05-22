/*
 * PurchaseOrderProcessor.java
 *
 * Created on September 8, 2004, 1:37 PM
 */

package com.cleanwise.service.api.pipeline;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.APIAccess;
import java.sql.SQLException;
import java.util.List;
/**
 * Deals with any logic attached to the OrderData.requestPoNum property
 * @author  bstevens
 */
public class CustomerPurchaseOrderNumProcessor implements OrderPipeline{
    
    public OrderPipelineBaton process(OrderPipelineBaton pBaton, OrderPipelineActor pActor, java.sql.Connection pCon, APIAccess pFactory) throws PipelineException {
        

        //Do no processing if this order already has a purchase order number assigned
        if(Utility.isSet(pBaton.getOrderData().getRequestPoNum())){
            return pBaton;
        }

        if(pBaton.hasErrors()){
            return pBaton;
        }
        
        try{
            DBCriteria crit = new DBCriteria();
            int siteId = 0;
            siteId = pBaton.getOrderData().getSiteId();
            if(siteId == 0){
                siteId = pBaton.getPreOrderData().getSiteId();
            }
            
            if(siteId == 0){
                if(!pBaton.hasErrors()){
                    //if the baton does not have any errors and the site id is not set
                    //this is probebly running before the site id is set.  This is a
                    //pipeline misconfiguration
                    throw new PipelineException(this.getClass().getName() +" Could not find site id.");
                }
            }
            
            /*if(RefCodeNames.PIPELINE_CD.ASYNCH.equals(pBaton.getPipelineTypeCd()) ||
                    RefCodeNames.PIPELINE_CD.ASYNCH_PRE_PROCESSED.equals(pBaton.getPipelineTypeCd())){
                throw new PipelineException(this.getClass().getName() +" In wrong spot on the piepline.");
            }*/
            
            crit.addJoinTableEqualTo(BlanketPoNumAssocDataAccess.CLW_BLANKET_PO_NUM_ASSOC,BlanketPoNumAssocDataAccess.BUS_ENTITY_ID,siteId);
            crit.addJoinTableEqualTo(BlanketPoNumDataAccess.CLW_BLANKET_PO_NUM,BlanketPoNumDataAccess.STATUS_CD, RefCodeNames.SIMPLE_STATUS_CD.ACTIVE);
            crit.addJoinCondition(BlanketPoNumAssocDataAccess.CLW_BLANKET_PO_NUM_ASSOC,
                    BlanketPoNumAssocDataAccess.BLANKET_PO_NUM_ID,
                    BlanketPoNumDataAccess.CLW_BLANKET_PO_NUM,
                    BlanketPoNumDataAccess.BLANKET_PO_NUM_ID);
            crit.addDataAccessForJoin(new BlanketPoNumDataAccess());
            List pos = JoinDataAccess.select(pCon,crit);
            if(pos.size() > 1){
                throw new PipelineException("multiple blanket request po numbers for this order");
            }
            if(pos.size() == 1){
                BlanketPoNumData bpo = (BlanketPoNumData)((List) pos.get(0)).get(0);
                StringBuffer poNum = new StringBuffer(bpo.getPoNumber());
                if(RefCodeNames.BLANKET_PO_NUM_TYPE_CD.PO_RELEASE.equals(bpo.getBlanketCustPoNumberTypeCd())){
                    //we do not need to lock the row as we may be running in a syncronous mode
                    //if this switches you will need to obtain a lock somehow OUTSIDE the context
                    //of this transaction, or make sure that you are not also running this in an
                    //asyncronous mode as that could result in deadlocks.
                    poNum.append(bpo.getSeperator());
                    poNum.append(bpo.getCurrentRelease());
                    
                    bpo.setCurrentRelease(bpo.getCurrentRelease()+1);
                    BlanketPoNumDataAccess.update(pCon,bpo);
                }else if(RefCodeNames.BLANKET_PO_NUM_TYPE_CD.STATIC.equals(bpo.getBlanketCustPoNumberTypeCd())){
                    //nothing to do, the poNum variable is already set to the base value
                }else{
                    throw new PipelineException("Unknown BlanketCustPoNumberTypeCd: "+bpo.getBlanketCustPoNumberTypeCd());
                }
                pBaton.getOrderData().setRequestPoNum(poNum.toString());
                pBaton.getPreOrderData().setCustomerPoNumber(poNum.toString());
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
        return pBaton;
    }
    
}
