/**
 * 
 */
package com.cleanwise.service.api.pipeline;

import java.sql.Connection;
import java.util.Iterator;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.dao.ShoppingControlDataAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.RefCdData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.ShoppingControlData;
import com.cleanwise.service.api.value.ShoppingControlDataVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.WorkflowRuleData;
import com.cleanwise.service.api.value.WorkflowRuleDataVector;

import java.rmi.RemoteException;

/**
 * @author ssharma
 *
 */
public class ShoppingControlsWorkflow implements OrderPipeline {
	
	private static final String className = "ShoppingControlsWorkflow";

	 /**
     * Process this pipeline.
     * @param pBaton
     * @param pActor
     * @param pCon
     * @param pFactory
     * @return
     * @throws PipelineException
     */
    public OrderPipelineBaton process(OrderPipelineBaton pBaton,
                                      OrderPipelineActor pActor,
                                      Connection pCon,
                                      APIAccess pFactory)
            throws PipelineException {
    	
    	try{
    		pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            OrderData orderD = pBaton.getOrderData();
            OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
            if(orderItemDV==null) {
                return pBaton;
            }
            
            int acctId = orderD.getAccountId();
            int siteId = orderD.getSiteId();
            
            if(pBaton.hasErrors()) {
                return pBaton;
            }
            
            APIAccess factory = APIAccess.getAPIAccess();
            Order orderEjb = factory.getOrderAPI();
            User userEjb = factory.getUserAPI();
            Group groupEjb = factory.getGroupAPI();
            
            String bypassWkflRuleActionCd = pBaton.getBypassWkflRuleActionCd();
            
         // Check the workflow role.  Workflow's apply only to
            // customers which are not APPROVERs.
            String wfrcd = pBaton.getUserWorkflowRoleCd();
            if (RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER.equals(wfrcd)) {
                // No need to check the rules since this user
                // has the authority to overide them.
                return pBaton;
            }
            
            //If user has function 'override shopping restriction', skip 
            String user =pBaton.getOrderData().getAddBy();
            if(Utility.isSet(user)){
            	UserData userD = userEjb.getUserByName(user, 0);
            	if(userD!=null){
            		int userId = userD.getUserId();
            		
            		RefCdDataVector functions = userEjb.getAuthorizedFunctions(userId, null);
            		Iterator it = functions.iterator();
            		while(it.hasNext()){
            			RefCdData func = (RefCdData)it.next();
            			if(func.getValue().equalsIgnoreCase(RefCodeNames.APPLICATION_FUNCTIONS.OVERRIDE_SHOPPING_RESTRICTION)){
            				return pBaton;
            			}
            		}
            		
            	}
            }
            
            
            WorkflowRuleDataVector wfrv;
            wfrv = pBaton.getWorkflowRuleDataVector(RefCodeNames.WORKFLOW_RULE_TYPE_CD.SHOPPING_CONTROLS);

            if (wfrv.size() <= 0) {
                return pBaton;
            }
            
            boolean splitOrder = false;
            WorkflowRuleData rdAction = null;
            for (int ruleidx = 0; ruleidx < wfrv.size(); ruleidx++) {

                WorkflowRuleData rd = (WorkflowRuleData)wfrv.get(ruleidx);
                String ruleAction = rd.getRuleAction();
                if(rdAction==null && Utility.isSet(ruleAction)) {
                    rdAction = rd;
                }
                String ruleVal = rd.getRuleExpValue();
                String ruleExp = rd.getRuleExp();
                
                if(RefCodeNames.RULE_EXPRESSION.SPLIT_ORDER.equals(ruleExp)) {
                    try {
                    	splitOrder = Utility.isTrue(ruleVal) && pBaton.getOrderStatus().equals(RefCodeNames.ORDER_STATUS_CD.ORDERED);
                    }catch(Exception exc){}
                }
                
            }
            
            boolean applyShoppingControlsWkflw = false;
            IdVector wrkflwItemIds = new IdVector();
            for(int i=0; i<orderItemDV.size(); i++){

 			   OrderItemData oid = (OrderItemData)orderItemDV.get(i);	
 			   int thisItemId = oid.getItemId();
 			   int qty = oid.getTotalQuantityOrdered();
 					   
 			   DBCriteria crit = new DBCriteria();
 			   //site level
 			   crit.addEqualTo(ShoppingControlDataAccess.ITEM_ID, thisItemId);
 			   crit.addEqualTo(ShoppingControlDataAccess.SITE_ID, siteId);
 			   ShoppingControlDataVector siteDV = ShoppingControlDataAccess.select(pCon, crit);
 			
 			   //account level
 			   crit = new DBCriteria();
 			   crit.addEqualTo(ShoppingControlDataAccess.ITEM_ID, thisItemId);
 			   crit.addEqualTo(ShoppingControlDataAccess.ACCOUNT_ID, acctId);
 			   ShoppingControlDataVector acctDV = ShoppingControlDataAccess.select(pCon, crit);
 			   
 			   int maxOrderQty = -1;
 			   int restrictDays = -1;
 			   String actionCd = "";
 			   
 			   //get max qty and rest days from site level
 			   if(siteDV!=null && siteDV.size()>0){
 				   ShoppingControlData scD = (ShoppingControlData)siteDV.get(0);
 				   maxOrderQty = scD.getMaxOrderQty();
 				   restrictDays = scD.getRestrictionDays();
 			   }else{
 				   //if no site level controls found, use acct level
 				  if(acctDV!=null && acctDV.size()>0){
 	 				   ShoppingControlData scD = (ShoppingControlData)acctDV.get(0);
 	 				   maxOrderQty = scD.getMaxOrderQty();
 	 				   restrictDays = scD.getRestrictionDays();
 				  }
 			   }
 			   
 			   //get action from acct level
 			   if(acctDV!=null && acctDV.size()>0){
 				   ShoppingControlData scD = (ShoppingControlData)acctDV.get(0);
 				   actionCd = scD.getActionCd();
 			   }
 			   
 			   //unlimited
 			   if(maxOrderQty < 0){
 				   continue;
 			   }
 			   
 			   // qtyPerOrderItem is calculated based on the previously placed Orders
 			   int qtyPerOrderItem = 0;
 			   try {
 				   qtyPerOrderItem = orderEjb.getOrderItemQuantity(restrictDays, thisItemId, siteId);
			   } catch (RemoteException exc) {
				   exc.printStackTrace();
				   return pBaton;
			   }

 			   if(!(restrictDays < 0)){
 				   maxOrderQty = maxOrderQty - qtyPerOrderItem; //Max Order Quantity - Quantity per Order item from the placed Orders
 			   }
 			   
 			  if(qty > maxOrderQty){
 				  if(Utility.isSet(actionCd) &&
 						  actionCd.equalsIgnoreCase(RefCodeNames.SHOPPING_CONTROL_ACTION_CD.WORKFLOW)){
 					  applyShoppingControlsWkflw = true;
 					  
 					  if(splitOrder){
 						  wrkflwItemIds.add(thisItemId);
 					  }
 				  }
 			  }
 			   
            }
            
            if(applyShoppingControlsWkflw){
	            boolean evalNextRule = OrderPipelineActor.performRuleAction(pCon,
	                    pBaton, rdAction, null, null, null, null,
	                    bypassWkflRuleActionCd);
	            
	            if (!evalNextRule){
	            	if (wrkflwItemIds.size() > 0 && wrkflwItemIds.size() < pBaton.getOrderItemDataVector().size()){
	                	pActor.splitOrder(pCon, pBaton, wrkflwItemIds, pFactory);
	                } 
	            }
            }
            
            return pBaton;
            
    	}catch(Exception exc) {
            exc.printStackTrace();
            throw new PipelineException(exc.getMessage());
        }
    }
}
