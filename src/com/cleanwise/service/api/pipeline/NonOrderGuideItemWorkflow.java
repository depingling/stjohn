/*
 * OffOrderGuideWorkflow.java
 *
 * Created on May 25, 2005, 9:20 PM
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.OrderGuideStructureDataAccess;
import com.cleanwise.service.api.session.OrderGuide;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.PipelineUtil;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderGuideDescData;
import com.cleanwise.service.api.value.OrderGuideDescDataVector;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.WorkflowRuleData;
import com.cleanwise.service.api.value.WorkflowRuleDataVector;


import java.sql.Connection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

import  org.apache.log4j.*;
/**
 *
 * @author bstevens
 */
public class NonOrderGuideItemWorkflow implements OrderPipeline{
    private static final Logger log = Logger.getLogger(NonOrderGuideItemWorkflow.class);

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
    	log.info("process()===> Begin.");
        try{
            pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            OrderData orderD = pBaton.getOrderData();
            int siteId = orderD.getSiteId();
            int userId = orderD.getUserId();
            if(siteId<=0) {
                return pBaton;
            }
            if(pBaton.hasErrors()) {
                return pBaton;
            }
            String bypassWkflRuleActionCd = pBaton.getBypassWkflRuleActionCd();
            
            // Check the workflow role.  Workflow's apply only to
            // customers which are not APPROVERs.
            String wfrcd = pBaton.getUserWorkflowRoleCd();
            if (RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER.equals(wfrcd)) {
                // No need to check the rules since this user
                // has the authority to overide them.
                return pBaton;
            }
            
            WorkflowRuleDataVector wfrv =
                    pBaton.getWorkflowRuleDataVector(
                    RefCodeNames.WORKFLOW_RULE_TYPE_CD.NON_ORDER_GUIDE_ITEM);
            
            if (wfrv.size() <= 0) {
                return pBaton;
            }
            boolean incBuyerListFlg = isIncludeBuyerListProperty(wfrv);
            
            OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
            if(orderItemDV==null) {
                return pBaton;
            }
            Collections.sort(orderItemDV,new Comparator() {
	              public int compare(Object o1, Object o2) {
	                  if (o1 != null && o2 != null) {
	              		int val1 = ((OrderItemData)o1).getOrderLineNum();
	    				int val2 = ((OrderItemData)o2).getOrderLineNum();
	    				return val1 - val2;
	                  }
	                  return 0;
	              }
	          });
            
            PropertyUtil pu = new PropertyUtil(pCon);
            String storeType = pu.fetchValueIgnoreMissing(0, orderD.getStoreId(), RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE);
            
            OrderGuideDescDataVector odv = pFactory.getOrderGuideAPI().getCollectionBySiteUser(siteId, 0, OrderGuide.TYPE_TEMPLATE);
            //OrderGuideDescDataVector odv = pFactory.getOrderGuideAPI().getCollectionBySite(siteId, OrderGuide.TYPE_BUYER_AND_TEMPLATE);
            
            OrderGuideDescDataVector odvB = null;
            HashSet buyerItemIds = new HashSet();
            if (incBuyerListFlg){
            	odvB = pFactory.getOrderGuideAPI().getCollectionBySiteUser(siteId, userId, OrderGuide.TYPE_BUYER);
                
            	Iterator it = odvB.iterator();
                while(it.hasNext()){
                    OrderGuideDescData guide = (OrderGuideDescData) it.next();
                    //iterate through all of the buyer shopping lists for the site and build a list of items
                    DBCriteria crit = new DBCriteria();
                    crit.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,guide.getOrderGuideId());
                    buyerItemIds.addAll(OrderGuideStructureDataAccess.selectIdOnly(pCon, OrderGuideStructureDataAccess.ITEM_ID,crit));
                }
            }
            log.info("process() ==> incBuyerListFlg = " + incBuyerListFlg + ", buyerItemIds= "+ buyerItemIds);
            HashSet itemIds = new HashSet();
            Iterator it = odv.iterator();
            while(it.hasNext()){
                OrderGuideDescData guide = (OrderGuideDescData) it.next();
                //iterate through all of the template order guides for the site and build a list of items
                DBCriteria crit = new DBCriteria();
                crit.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,guide.getOrderGuideId());
                itemIds.addAll(OrderGuideStructureDataAccess.selectIdOnly(pCon, OrderGuideStructureDataAccess.ITEM_ID,crit));
            }
            
            
            log.info("process() ==> itemIds = " + itemIds );
            
            // Iterate through the rules for the site.
            for (int ruleidx = 0; ruleidx < wfrv.size(); ruleidx++) {
                WorkflowRuleData rd = (WorkflowRuleData)wfrv.get(ruleidx);
                if (ruleidx > 0)continue;
                // Iterate through the items in the order.
               List<Object[]> errMessageArgs = new LinkedList<Object[]>();
               String errMessageKey =null;
                for ( int oi_idx = 0; oi_idx < orderItemDV.size(); oi_idx++ ) {
                    OrderItemData oid = (OrderItemData) orderItemDV.get(oi_idx);
                    Integer itemId = new Integer(oid.getItemId());
  
                    log.info("process() ==> itemId = " + itemIds );
                    if ( !itemIds.contains(itemId) &&
                    	  (	buyerItemIds.isEmpty() ||
                    	   (incBuyerListFlg && !buyerItemIds.isEmpty() && !buyerItemIds.contains(itemId)) )) {
                        String lineNum = "" + oid.getOrderLineNum();
                        String sku = Utility.getActualSkuNumber(storeType, oid);
                                           	
                    	////String errorMess = "The order has sku "+Utility.getActualSkuNumber(storeType, oid);
                        ////OrderPipelineActor.performRuleAction(pCon, pBaton,rd, errorMess,null, bypassWkflRuleActionCd);
                        errMessageKey = "pipeline.message.nonShoppingListItem";
                        errMessageArgs.add(new Object[]{lineNum, sku});
                    	//STJ-5359
                        //String errorMess = "pipeline.message.orderHasSku";
                        //OrderPipelineActor.performRuleAction(pCon, pBaton, rd, errorMess, new Object[]{Utility.getActualSkuNumber(storeType, oid)}, new String[]{RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING}, null, bypassWkflRuleActionCd);
                        //break;
                    }
                }

                
                log.info("process() ==> errMessageArgs.size() = " + errMessageArgs.size() );
                if (Utility.isSet(errMessageKey)){
                	String[] argTypes = new String[]{RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING };
                   	OrderPipelineActor.performRuleAction(pCon, pBaton, rd, errMessageKey, (Object[])errMessageArgs.get(0), argTypes, null, bypassWkflRuleActionCd);
                   	if (errMessageArgs.size() > 1 && !RefCodeNames.PIPELINE_CD.CHECKOUT_CAPTURE.equals(pBaton.getPipelineTypeCd())){
	                	for (int i=1; i<errMessageArgs.size(); i++){
	                		Object[] args = (Object[])errMessageArgs.get(i);
	                		performRuleNote(pCon, pBaton, rd, errMessageKey, args , argTypes);
	                	}
                	}
                }
            }
            return pBaton;
        } catch(Exception exc) {
        	exc.printStackTrace();
            throw new PipelineException(exc.getMessage());
        }
    }
    
    private boolean isIncludeBuyerListProperty (WorkflowRuleDataVector wfrv){
    	boolean b = false;
        for (int ruleidx = 0; ruleidx < wfrv.size(); ruleidx++) {
            WorkflowRuleData rd = (WorkflowRuleData)wfrv.get(ruleidx);
    	    if (RefCodeNames.WORKFLOW_RULE_EXPRESSION.INCLUDE_BUYER_LIST.equals(rd.getRuleExp())){
    	    	log.info("isIncludeBuyerListProperty()===>rd.getWorkflowRuleId()="+ rd.getWorkflowRuleId() + ", " +rd.getRuleExp() +"="+ rd.getRuleExpValue());
    	        b= new Boolean(rd.getRuleExpValue()).booleanValue();
            	break;
            }
        }    
    	return b;
    }
	public static void  performRuleNote (Connection pCon, OrderPipelineBaton pBaton, WorkflowRuleData pRule, String errMessageKey, Object[] errMessageArgs, String[] errMessageArgTypes) {   
	    //String stringType = RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING;
		log.info("performRuleNote()===> Begin. pRule.getRuleAction()="+ pRule.getRuleAction());
	    OrderPropertyData opD = OrderPropertyData.createValue();
	    OrderData oD = pBaton.getOrderData();
	    opD.setOrderId(oD.getOrderId());
	    opD.setWorkflowRuleId(pRule.getWorkflowRuleId());
	    opD.setAddBy("System (user: SYNCH_ASYNCH)");
	    opD.setModBy("System");
	    opD.setAddDate(new Date());
	    opD.setModDate(new Date());
	    //opD.setApproveDate(new Date());
	    opD.setShortDesc("Workflow Note");
	    
	    String mess = PipelineUtil.translateMessage(errMessageKey, pBaton.getOrderData().getLocaleCd(),
	      errMessageArgs[0], errMessageArgTypes[0],
	      errMessageArgs[1], errMessageArgTypes[1],
	      null, null, null, null);
	
	    opD.setValue(mess);
	    opD.setOrderPropertyStatusCd("ACTIVE");
	    opD.setOrderPropertyTypeCd("Notes");
	    opD.setMessageKey(getRuleActionMessageKey(pRule));
	    opD.setArg0(errMessageKey);
	    opD.setArg0TypeCd(RefCodeNames.PIPELINE_MESSAGE_ARG_CD.MESSAGE_KEY);
	    for (int i=0; i < errMessageArgs.length; i++) {
	    	switch (i){
	    		case 0 : opD.setArg1((String)errMessageArgs[i]); opD.setArg1TypeCd(errMessageArgTypes[i]);
	    		case 1 : opD.setArg2((String)errMessageArgs[i]); opD.setArg2TypeCd(errMessageArgTypes[i]);
	    	}
	    }
		log.info("performRuleNote()===> pBaton.getOrderStatus()= " + pBaton.getOrderStatus());
	    
	    if (RefCodeNames.WORKFLOW_RULE_ACTION.REJECT_ORDER.equals(pRule.getRuleAction())) {
	    	pBaton.addError(pCon,
	    		OrderPipelineBaton.WORKFLOW_RULE_ALARM,
	    		pRule.getRuleTypeCd(),
                pBaton.getOrderStatus(), 0, pRule.getWorkflowRuleId(),
                "pipeline.message.commonFail",
                errMessageKey, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.MESSAGE_KEY,
                errMessageArgs[0], errMessageArgTypes[0],
                errMessageArgs[1], errMessageArgTypes[1],
                null, null);
   
	    } else {
		    pBaton.addOrderPropertyData(opD);
	    }
		log.info("performRuleNote()===> End.");

	}
	
	private static String getRuleActionMessageKey(WorkflowRuleData pRule){
		String key = "";
	    if (RefCodeNames.WORKFLOW_RULE_ACTION.REJECT_ORDER.equals(pRule.getRuleAction())) {
	    	key = "pipeline.message.rejectOrder";
		}else if (RefCodeNames.WORKFLOW_RULE_ACTION.FWD_FOR_APPROVAL.equals(pRule.getRuleAction())) {
			key = "pipeline.message.fwdForApproval";
		}else if (RefCodeNames.WORKFLOW_RULE_ACTION.STOP_ORDER.equals(pRule.getRuleAction())) {
			key = "pipeline.message.stopOrder";
		}else if (RefCodeNames.WORKFLOW_RULE_ACTION.SEND_EMAIL.equals( pRule.getRuleAction())) {
			key = "";
		}	
		return key;
	}
}
