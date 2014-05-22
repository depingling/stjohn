
package com.cleanwise.view.logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Date;
import java.math.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.OrderOpSearchForm;
import com.cleanwise.view.forms.OrderBackfillForm;
import com.cleanwise.view.forms.OrderOpItemDetailForm;
import com.cleanwise.view.forms.OrderOpItemSearchForm;
import com.cleanwise.view.forms.OrderOpNoteForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.util.DataNotFoundException;

/**
 * <code>OrderOpLogic</code> implements the logic needed to
 * manipulate order records.
 *
 * @author durval
 */
public class OrderBackfillLogic {

    /**
     * <code>init</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void init(HttpServletRequest request,
			    ActionForm form)
	throws Exception {
//        initConstantList(request);
	//searchAll(request, form);
	return;
    }


    /**
     *  <code>sortItems</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sortItems(HttpServletRequest request,
				 ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();

        OrderBackfillForm sForm =
            (OrderBackfillForm)
            session.getAttribute("ORDER_OP_DETAIL_FORM");
	if (sForm == null) {
	    // not expecting this, but nothing to do if it is
	    return;
	}

	OrderItemDescDataVector orderItemDescList =
	   (OrderItemDescDataVector)sForm.getOrderItemDescList();

	String sortField = request.getParameter("sortField");
	DisplayListSort.sort(orderItemDescList, sortField);
    }

    /**
     * <code>getOrderStatusDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param orderId a <code>String</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors getOrderStatusDetail(
				    HttpServletRequest request,
				    ActionForm form,
				    String orderStatusId)
	throws Exception {
	OrderBackfillForm detailForm = (OrderBackfillForm) form;
    ActionErrors ae = new ActionErrors();
	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}

    int orderId = 0;
    try {
      orderId = Integer.parseInt(orderStatusId);
    } catch (Exception exc) {}
	Order orderEjb = factory.getOrderAPI();
    if(ae.size()>0) {
      return ae;
    }
    return ae;
    }

    //----------------------------------------------------------------------------
    public static ActionErrors selectOrder(
				  HttpServletRequest request,
				  ActionForm form)
	throws Exception {
	OrderBackfillForm pForm = (OrderBackfillForm) form;
    ActionErrors ae = new ActionErrors();
	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}
    String orderNumS = pForm.getStringOrderNumber();
    int orderNum = 0;
    if(orderNumS!=null && orderNumS.trim().length()>0) {
      try {
        orderNum = Integer.parseInt(orderNumS);
      } catch(Exception exc) {
        String errorMess = "Wrong web order number format: "+orderNumS;
	    ae.add("error",new ActionError("error.simpleGenericError",errorMess));
	    return ae;
      }
    }
    String erpOrderNumS = pForm.getStringErpOrderNum();
    int erpOrderNum = 0;
    if(erpOrderNumS!=null && erpOrderNumS.trim().length()>0) {
      try {
        erpOrderNum = Integer.parseInt(erpOrderNumS);
      } catch(Exception exc) {
        String errorMess = "Wrong erp order number format: "+erpOrderNumS;
	    ae.add("error",new ActionError("error.simpleGenericError",errorMess));
	    return ae;
      }
    }
    OrderStatusCriteriaData orderStatusCriteria;
    if(orderNum<=0 && erpOrderNum<=0) {
      String errorMess = "Neither order number nor erp order number was selected";
      ae.add("error",new ActionError("error.simpleGenericError",errorMess));
	  return ae;
    }
    String userId = (String)session.getAttribute(Constants.USER_ID);
    String userType = (String)session.getAttribute(Constants.USER_TYPE);
    if ( null == userId ) userId = new String("");
    if ( null == userType ) userType = new String("");
	Order orderEjb = factory.getOrderAPI();
    OrderDataVector orderDV = null;
    OrderData orderD = null;
    if(erpOrderNum>0) {
      orderStatusCriteria = new OrderStatusCriteriaData();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
          orderStatusCriteria.setStoreIdVector(appUser.getUserStoreAsIdVector());
      }
      orderStatusCriteria.setErpOrderNum(""+erpOrderNum);
      orderStatusCriteria.setUserId(userId);
      orderStatusCriteria.setUserTypeCd(userType);
      try {
        orderDV = orderEjb.getOrderStatusCollection(orderStatusCriteria);
      } catch (Exception exc) {
        String errorMess = "Failed to get order. "+exc.getMessage();
        ae.add("error",new ActionError("error.systemError",errorMess));
        return ae;
      }
      if(orderNum>0) {
        if(orderDV.size()>0) {
          int ii=0;
          String orderNumbers = "";
          for(;ii<orderDV.size(); ii++) {
            OrderData oD = (OrderData) orderDV.get(ii);
            String orderNumberFoundS = oD.getOrderNum();
            if(ii>0) orderNumbers+=", ";
            orderNumbers += orderNumberFoundS;
            int orderNumberFound = 0;
            try{
              orderNumberFound = Integer.parseInt(orderNumberFoundS);
              if(orderNumberFound==orderNum) {
                orderD = oD;
                break;
              }
            } catch(Exception exc ) {
              continue;
            }
          }
          if(ii==orderDV.size()) {
            String errorMess = "There are another orders with the erp order number. Order number(s): "+orderNumbers;
            ae.add("error",new ActionError("error.simpleGenericError",errorMess));
            return ae;
          }
        }
      } else { //orderNum <=0
        if(orderDV.size()==0) {
//           String errorMess = "No order found with the erp order number: "+erpOrderNum;
//           ae.add("error",new ActionError("error.simpleGenericError",errorMess));
//           return ae;
        }
        if(orderDV.size()>1) {
          String orderNumbers = "";
          for(int ii=0;ii<orderDV.size(); ii++) {
            OrderData oD = (OrderData) orderDV.get(ii);
            String orderNumberFoundS = oD.getOrderNum();
            if(ii>0) orderNumbers+=", ";
            orderNumbers += orderNumberFoundS;
          }
          String errorMess = "More than one order have the erp order number: "+orderNumbers;
          ae.add("error",new ActionError("error.simpleGenericError",errorMess));
	      return ae;
        }
        if(orderDV.size()==1) {
          orderD = (OrderData) orderDV.get(0);
        }
      }
    }
    if(orderD==null && orderNum>0) {
      orderStatusCriteria = new OrderStatusCriteriaData();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
          orderStatusCriteria.setStoreIdVector(appUser.getUserStoreAsIdVector());
      }
      orderStatusCriteria.setWebOrderConfirmationNum(Integer.toString(orderNum));
      orderStatusCriteria.setUserId(userId);
      orderStatusCriteria.setUserTypeCd(userType);
      try {
        orderDV = orderEjb.getOrderStatusCollection(orderStatusCriteria);
      } catch (Exception exc) {
        String errorMess = "Failed to get order. "+exc.getMessage();
        ae.add("error",new ActionError("error.systemError",errorMess));
        return ae;
      }
      if(orderDV.size()>1) {
        String errorMess = "More than one order have the order number: "+orderNum;
        ae.add("error",new ActionError("error.simpleGenericError",errorMess));
        return ae;
      }
      if(orderDV.size()==1) {
        orderD = (OrderData) orderDV.get(0);
      }
    }
    if(orderD==null && orderNum>0) {
      String errorMess = "No order found.";
      if(orderNum>0) errorMess+=" Order number: "+orderNum;
      if(erpOrderNum>0) errorMess+=" Erp order number: "+erpOrderNum;
      ae.add("error",new ActionError("error.systemError",errorMess));
      return ae;
    }
    int orderId = 0;
    if(orderD!=null) {
      orderId = orderD.getOrderId();
      if(erpOrderNum<=0) {
        erpOrderNum = orderD.getErpOrderNum();
      }
    }
    if(ae.size()>0) {
      return ae;
    }
    return ae;
  }
  //----------------------------------------------------------------------------
  private static ActionErrors loadOrder
      (OrderBackfillForm pForm, Order orderEjb,
       int orderId, int erpOrderNum)
  {
    ActionErrors ae = new ActionErrors();

    OrderStatusDescData orderStatusDetail = null;
    if(orderId>0) {
      try {
	    orderStatusDetail = orderEjb.getOrderStatusDesc(orderId);
      } catch(DataNotFoundException exc) {
      } catch(java.rmi.RemoteException exc) {
         String errorMess = "Failed to get order status. "+exc.getMessage();
  	     ae.add("error",new ActionError("error.systemError",errorMess));
	     return ae;
      }
    }
    if(orderStatusDetail==null) {
      orderStatusDetail = OrderStatusDescData.createValue();
      orderStatusDetail.setOrderDetail(OrderData.createValue());
    }
    if(orderStatusDetail.getBillTo()==null) {
      orderStatusDetail.setBillTo(OrderAddressData.createValue());
    }
    if(orderStatusDetail.getShipTo()==null) {
      orderStatusDetail.setShipTo(OrderAddressData.createValue());
    }
    if(orderStatusDetail.getCustomerShipTo()==null) {
      orderStatusDetail.setCustomerShipTo(OrderAddressData.createValue());
    }
    if(orderStatusDetail.getOrderItemList()==null) {
      orderStatusDetail.setOrderItemList(new OrderItemDataVector());
    }
    pForm.setStringOrderNumber(orderStatusDetail.getOrderDetail().getOrderNum());
    if(erpOrderNum==0) {
      pForm.setStringErpOrderNum("");
    } else {
      pForm.setStringErpOrderNum(""+erpOrderNum);
    }
//	OrderPropertyDataVector orderPropertyDetailVec = orderEjb.getOrderPropertyVec(orderId);
//    OrderPropertyData orderPropertyDetail = OrderPropertyData.createValue();
//    if ( null != orderPropertyDetailVec && 0 < orderPropertyDetailVec.size()) {
       //We want to see the notes in order of thier being written
//       DisplayListSort.sort(orderPropertyDetailVec, "propertyId");
       //set the OrderPropertyDetail to the latest object
//       int lastItem = orderPropertyDetailVec.size()-1;
//       orderPropertyDetail = (OrderPropertyData)orderPropertyDetailVec.get(lastItem);
//    }

//	OrderItemDescDataVector itemStatusDescV = orderEjb.getOrderItemDescCollection(orderId, null);

    pForm.setOrderStatusDetail(orderStatusDetail);
    OrderData orderD = orderStatusDetail.getOrderDetail();
    // sent the total order amount
    BigDecimal totalOrderAmount = new BigDecimal(0);
    if (null != orderD.getTotalPrice()) {
      pForm.setSubTotal(orderD.getTotalPrice().doubleValue());
      totalOrderAmount = totalOrderAmount.add(orderD.getTotalPrice());
    } else {
      pForm.setSubTotal(0);
    }
    if (null != orderD.getTotalFreightCost()) {
      pForm.setTotalFreightCost(orderD.getTotalFreightCost().doubleValue());
      totalOrderAmount = totalOrderAmount.add(orderD.getTotalFreightCost());
    } else {
      pForm.setTotalFreightCost(0);
    }
    if (null != orderD.getTotalTaxCost()) {
      pForm.setTotalTaxCost(orderD.getTotalTaxCost().doubleValue());
      totalOrderAmount = totalOrderAmount.add(orderD.getTotalTaxCost());
    } else {
      pForm.setTotalTaxCost(0);
    }
    if (null != orderD.getTotalMiscCost()) {
      pForm.setTotalMiscCost(orderD.getTotalMiscCost().doubleValue());
      totalOrderAmount = totalOrderAmount.add(orderD.getTotalMiscCost());
    } else {
      pForm.setTotalMiscCost(0);
    }
    pForm.setTotalAmount (totalOrderAmount.doubleValue());

    OrderStatusDescData orderDetailAfter = OrderStatusDescData.createValue();
    orderDetailAfter.setOrderDetail(OrderData.createValue());
    orderDetailAfter.setCustomerShipTo(OrderAddressData.createValue());
    orderDetailAfter.setShipTo(OrderAddressData.createValue());
    orderDetailAfter.setBillTo(OrderAddressData.createValue());
    orderDetailAfter.setOrderItemList(new OrderItemDataVector());
    pForm.setOrderStatusDetailAfter(orderDetailAfter);
    pForm.setSubTotalAfter(0);
    pForm.setTotalAmountAfter(0);
    pForm.setTotalFreightCostAfter(0);
    pForm.setTotalTaxCostAfter(0);
    pForm.setTotalMiscCostAfter(0);
    String orderNumS = orderD.getOrderNum();
    if(erpOrderNum<=0) {
      erpOrderNum = orderD.getErpOrderNum();
    }
    if(erpOrderNum==0) {
      pForm.setStringErpOrderNum("");
    } else {
      pForm.setStringErpOrderNum(""+erpOrderNum);
    }
    int orderNum = 0;
    try{
      orderNum = Integer.parseInt(orderNumS);
    } catch(Exception exc) {}
    pForm.setOrderStatusDetailAfter(orderDetailAfter);
    OrderData orderD1 = orderDetailAfter.getOrderDetail();
    // sent the total order amount
    BigDecimal totalOrderAmountAfter = new BigDecimal(0);
    if (null != orderD1.getTotalPrice()) {
      pForm.setSubTotalAfter(orderD1.getTotalPrice().doubleValue());
      totalOrderAmountAfter = totalOrderAmountAfter.add(orderD1.getTotalPrice());
    }
    if (null != orderD1.getTotalFreightCost()) {
      pForm.setTotalFreightCostAfter(orderD1.getTotalFreightCost().doubleValue());
      totalOrderAmountAfter = totalOrderAmountAfter.add(orderD1.getTotalFreightCost());
    }
    if (null != orderD1.getTotalTaxCost()) {
      pForm.setTotalTaxCostAfter(orderD1.getTotalTaxCost().doubleValue());
      totalOrderAmountAfter = totalOrderAmountAfter.add(orderD1.getTotalTaxCost());
    }
    if (null != orderD1.getTotalMiscCost()) {
      pForm.setTotalMiscCostAfter(orderD1.getTotalMiscCost().doubleValue());
      totalOrderAmountAfter = totalOrderAmountAfter.add(orderD1.getTotalMiscCost());
    }
    pForm.setTotalAmountAfter (totalOrderAmountAfter.doubleValue());
    return ae;
  }

    /**
     * <code>getOrderItemDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param OrderItemId a <code>String</code> value
     * @exception Exception if an error occurs
     */
    public static void getOrderItemDetail(
				    HttpServletRequest request,
				    ActionForm form,
				    String OrderItemId)
	throws Exception {

	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}


	Order orderEjb = factory.getOrderAPI();
	if( null == OrderItemId || "".equals(OrderItemId)) {
	    OrderItemId = (String)session.getAttribute("OrderItem.id");
	}

        OrderItemDescData orderItemDescD = orderEjb.getOrderItemDescByItem(Integer.parseInt(OrderItemId));

        InvoiceDistDataVector invoiceDistList = orderEjb.getInvoiceDistCollectionByItem(Integer.parseInt(OrderItemId));

	OrderOpItemDetailForm detailForm = (OrderOpItemDetailForm) form;

        detailForm.setOrderItemDesc(orderItemDescD);
        detailForm.setInvoiceDistList(invoiceDistList);

        int orderedNum = 0;
        int acceptedNum = 0;
        int backorderedNum = 0;
        int substitutedNum = 0;
        int shippedNum = 0;
        int invoicedNum = 0;
        Date lastDate = null;

        if (null != orderItemDescD) {
            orderedNum = orderItemDescD.getOrderItem().getTotalQuantityOrdered();
        }

        OrderItemActionDataVector itemActions = orderItemDescD.getOrderItemActionList();
        if( null != itemActions ) {
            for( int j = 0; j < itemActions.size(); j++) {
                OrderItemActionData itemActionD = (OrderItemActionData)itemActions.get(j);
                if (null == lastDate) {
                    lastDate = itemActionD.getActionDate();
                }
                else {
                    if(lastDate.before(itemActionD.getActionDate()) ) {
                        lastDate = itemActionD.getActionDate();
                    }
                }

                String actionCd = itemActionD.getActionCd();
                if ("Accepted".equals(actionCd)) {
                    acceptedNum += itemActionD.getQuantity();
                } else if ("Substituted".equals(actionCd)) {
                    substitutedNum += itemActionD.getQuantity();
                } else if ("Invoiced".equals(actionCd)) {
                    invoicedNum += itemActionD.getQuantity();
                } else if ("Shipped".equals(actionCd)) {
                    shippedNum += itemActionD.getQuantity();
                }

            }   // end of loop of itemActions
        }   //end of if null != itemActions
        if (0 == shippedNum ) {
            backorderedNum = 0;
        }
        else {
            backorderedNum = orderedNum - shippedNum;
        }
        //backorderedNum = orderedNum - shippedNum;

        detailForm.setAcceptedNum(acceptedNum);
        detailForm.setSubstitutedNum(substitutedNum);
        detailForm.setShippedNum(shippedNum);
        detailForm.setInvoicedNum(invoicedNum);
        detailForm.setBackorderedNum(backorderedNum);
        detailForm.setLastDate(lastDate);
    }
    //**************************************************************************
    //--------------------------------------------------------------------------
    public static void setItemStatus(
				  HttpServletRequest request,
				  ActionForm form)
	{
	  OrderBackfillForm pForm = (OrderBackfillForm) form;
      String status = pForm.getAllItemsStatus();
      pForm.setAllItemsStatus(null);
      if (pForm.getOrderStatusDetailAfter()==null) return;
      OrderItemDataVector oiDV = pForm.getOrderStatusDetailAfter().getOrderItemList();
      if(oiDV==null) return;
      for(int ii=0; ii<oiDV.size(); ii++) {
        OrderItemData item = (OrderItemData) oiDV.get(ii);
        item.setOrderItemStatusCd(status);
      }
    }

    /**
     * <code>getOrderStatusDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param orderId a <code>String</code> value
     * @exception Exception if an error occurs
     */
    public static void getOrderNotes(
				    HttpServletRequest request,
				    ActionForm form,
				    String orderId,
                                    String orderItemId,
                                    String noteId)
	throws Exception {

	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}


	Order orderEjb = factory.getOrderAPI();
	if( null == orderId || "".equals(orderId)) {
            OrderBackfillForm orderDetailForm = (OrderBackfillForm)session.getAttribute("ORDER_OP_DETAIL_FORM");
            if (null != orderDetailForm ) {
                orderId = String.valueOf(orderDetailForm.getOrderStatusDetail().getOrderDetail().getOrderId());
            }
            else {
                orderId = (String)session.getAttribute("OrderStatus.id");
            }
	}

        OrderPropertyDataVector orderNotes = new OrderPropertyDataVector();
        if (null != orderItemId && ! "".equals(orderItemId)) {
            orderNotes = orderEjb.getOrderItemPropertyCollection(Integer.parseInt(orderItemId),
                            RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        }
        else if (null != orderId && ! "".equals(orderId)) {
            orderNotes = orderEjb.getOrderPropertyCollection(Integer.parseInt(orderId),
                            RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        }

        OrderPropertyData note = OrderPropertyData.createValue();
        if (null != noteId && ! "".equals(noteId)) {
            note = orderEjb.getOrderProperty(Integer.parseInt(noteId), RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        }

        OrderOpNoteForm orderNoteForm = (OrderOpNoteForm)form;
        orderNoteForm.setOrderPropertyDetail(note);
        orderNoteForm.setOrderPropertyList(orderNotes);
    }



    /**
     * <code>getOrderItemList</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param orderId a <code>String</code> value
     * @exception Exception if an error occurs
     */
/*
    public static void getOrderItemList(
				    HttpServletRequest request,
				    ActionForm form,
				    String orderId,
                                    String erpPoNum)
	throws Exception {

	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}


	Order orderEjb = factory.getOrderAPI();

        OrderStatusDescData orderStatusDetail = null;
        try {
            orderStatusDetail = orderEjb.getOrderStatusDesc(Integer.parseInt(orderId));
        }
        catch (Exception e) {
            throw e;
        }

        OrderItemDescDataVector itemStatusDescV = new OrderItemDescDataVector();
        try {
            itemStatusDescV = orderEjb.getOrderItemDescCollection(Integer.parseInt(orderId), erpPoNum);
        }
        catch (Exception e) {
            throw e;
        }

	OrderOpItemSearchForm detailForm = (OrderOpItemSearchForm) form;

        detailForm.setOrderDesc(orderStatusDetail);
        detailForm.setOrderItemDescList(itemStatusDescV);
        detailForm.setOrderId(orderId);
        detailForm.setErpPoNum(erpPoNum);


    }
*/

    //--------------------------------------------------------------------------
    public static ActionErrors updateOrder(
				  HttpServletRequest request,
				  ActionForm form)
	throws Exception {
	OrderBackfillForm pForm = (OrderBackfillForm) form;
    ActionErrors ae = new ActionErrors();
	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
      throw new Exception("Without APIAccess.");
	} 
    Order orderEjb = null;
    try {
      orderEjb = factory.getOrderAPI();
    } catch(Exception exc) {
      String errorMess = "No Order Ejb access. "+exc.getMessage();
      ae.add("error",new ActionError("error.systemError",errorMess));
      return ae;
    }
    OrderStatusDescData orderStatusAfter = pForm.getOrderStatusDetailAfter();
    OrderData orderAfter = orderStatusAfter.getOrderDetail();
    if(orderAfter==null) {
      String errorMess = "No order to update";
      ae.add("error",new ActionError("error.systemError",errorMess));
      return ae;
    }
    int orderId = orderAfter.getOrderId();
    if(orderId<=0) {
      String errorMess = "Order does not have id";
      ae.add("error",new ActionError("error.systemError",errorMess));
      return ae;
    }
    if(orderId!=pForm.getOrderId()) {
      String errorMess = "Order id does not match stored order. Probably old page was used";
      ae.add("error",new ActionError("error.simpleGenericError",errorMess));
      return ae;
    }
    String user = (String) session.getAttribute(Constants.USER_NAME);
    ae =loadOrder(pForm, orderEjb,orderId, orderAfter.getErpOrderNum());
    if(ae.size()>0) {
      return ae;
    }
    return ae;
  }

    //--------------------------------------------------------------------------
    public static ActionErrors createOrder(
				  HttpServletRequest request,
				  ActionForm form)
	throws Exception {
	OrderBackfillForm pForm = (OrderBackfillForm) form;
    ActionErrors ae = new ActionErrors();
	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
      throw new Exception("Without APIAccess.");
	}
    Order orderEjb = null;
    try {
      orderEjb = factory.getOrderAPI();
    } catch(Exception exc) {
      String errorMess = "No Order Ejb access. "+exc.getMessage();
      ae.add("error",new ActionError("error.systemError",errorMess));
      return ae;
    }
    OrderStatusDescData orderStatusAfter = pForm.getOrderStatusDetailAfter();
    OrderData orderAfter = orderStatusAfter.getOrderDetail();
    if(orderAfter==null) {
      String errorMess = "No order to update";
      ae.add("error",new ActionError("error.systemError",errorMess));
      return ae;
    }
    String user = (String) session.getAttribute(Constants.USER_NAME);
    int orderId = orderStatusAfter.getOrderDetail().getOrderId();
    ae =loadOrder(pForm, orderEjb, orderId, orderAfter.getErpOrderNum());
    if(ae.size()>0) {
      return ae;
    }
    return ae;
  }

}
