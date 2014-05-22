/*
 * CumulativeSummary.java
 *
 * Created on February 28, 2003, 12:27 PM
 */

package com.cleanwise.view.utils;
import java.util.Date;
import com.cleanwise.service.api.value.OrderItemDescData;
import com.cleanwise.service.api.value.OrderItemDescDataVector;
import com.cleanwise.service.api.value.OrderItemActionDataVector;
import com.cleanwise.service.api.value.OrderItemActionData;
import com.cleanwise.service.api.util.RefCodeNames;
/**
 *
 * @author  bstevens
 */
public class CumulativeSummary {
    
    /** Holds value of property lastDate. */
    private Date lastDate;
    
    /** Holds value of property orderedNum. */
    private int orderedNum;
    
    /** Holds value of property acceptedNum. */
    private int acceptedNum;
    
    /** Holds value of property backorderedNum. */
    private int backorderedNum;
    
    /** Holds value of property substitutedNum. */
    private int substitutedNum;
    
    /** Holds value of property invoicedNum. */
    private int invoicedNum;
    
    /** Holds value of property returnedNum. */
    private int returnedNum;
    
    /** Holds value of property shippedNum. */
    private int shippedNum;
    
    /** Creates a new instance of CumulativeSummary */
    public CumulativeSummary(OrderItemDescDataVector itemStatusDescV) {
        if (null != itemStatusDescV){
            for (int i = 0; i < itemStatusDescV.size(); i++){
                int returnedNumI = 0;
                int shippedNumI = 0;
                int invoicedNumI = 0;
                int substitutedNumI = 0;
                int acceptedNumI = 0;
                OrderItemDescData orderItemDescD = (OrderItemDescData)itemStatusDescV.get(i);
                // add the itemQuantity to orderedNum
                orderedNum += orderItemDescD.getOrderItem().getTotalQuantityOrdered();
                // get the quantity for different action
                OrderItemActionDataVector itemActions = orderItemDescD.getOrderItemActionList();
                if (null != itemActions){
                    for (int j = 0; j < itemActions.size(); j++){
                        
                        OrderItemActionData itemActionD = (OrderItemActionData)itemActions.get(j);
                        
                        Date actionDate = itemActionD.getActionDate();
                        if(actionDate == null){
                            actionDate = itemActionD.getAddDate();
                        }
                        
                        if (null == lastDate){
                            lastDate = actionDate;
                        }else{
                            if (lastDate.before(actionDate)){
                                lastDate = actionDate;
                            }
                        }
                        
                        String actionCd = itemActionD.getActionCd();
                        
                        if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACCEPTED.equals(actionCd)){
                            acceptedNumI += itemActionD.getQuantity();
                        }else if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SUBSTITUTED.equals(actionCd)){
                            substitutedNumI += itemActionD.getQuantity();
                        }else if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CUST_INVOICED.equals(actionCd)){
                            invoicedNumI += itemActionD.getQuantity();
                        }else if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SHIPPED.equals(actionCd)){
                            shippedNumI += itemActionD.getQuantity();
                        }else if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.RETURNED.equals(actionCd)){
                            returnedNumI += itemActionD.getQuantity();
                        }
                    } // end of loop of itemActions
                } //end of if null != itemActions
                //verify none of our totals went over on a per item basis...do not validate returned
                //as returned is not necessarily tied to qty ordered (may be overshipment/uom prob)
                int itmQty = orderItemDescD.getOrderItem().getTotalQuantityOrdered();
                if(itmQty < acceptedNumI){
                    acceptedNumI = itmQty;
                }
                if(itmQty < substitutedNumI){
                    substitutedNumI = itmQty;
                }
                if(itmQty < invoicedNumI){
                    invoicedNumI = itmQty;
                }
                if(itmQty < shippedNumI){
                    shippedNumI = itmQty;
                }
                acceptedNum += acceptedNumI;
                substitutedNum += substitutedNumI;
                invoicedNum += invoicedNumI;
                shippedNum += shippedNumI;
                returnedNum += returnedNumI;
            } // end of the loop for itemStatusDescV
            
            if(invoicedNum != 0){
                shippedNum = invoicedNum;
            }
            
            if (0 == shippedNum){
                backorderedNum = 0;
            }else{
                backorderedNum = orderedNum - shippedNum;
            }
        } // end of if null != itemStatusDescV
    }
    
    /** Getter for property lastDate.
     * @return Value of property lastDate.
     *
     */
    public Date getLastDate() {
        return this.lastDate;
    }
    
    /** Getter for property orderedNum.
     * @return Value of property orderedNum.
     *
     */
    public int getOrderedNum() {
        return this.orderedNum;
    }
    
    
    /** Getter for property acceptedNum.
     * @return Value of property acceptedNum.
     *
     */
    public int getAcceptedNum() {
        return this.acceptedNum;
    }
    
    
    /** Getter for property backorderedNum.
     * @return Value of property backorderedNum.
     *
     */
    public int getBackorderedNum() {
        return this.backorderedNum;
    }
    
    
    /** Getter for property substitutedNum.
     * @return Value of property substitutedNum.
     *
     */
    public int getSubstitutedNum() {
        return this.substitutedNum;
    }
    
    
    /** Getter for property shippedNum.
     * @return Value of property shippedNum.
     *
     */
    public int getShippedNum() {
        return this.shippedNum;
    }
    
    
    /** Getter for property invoicedNum.
     * @return Value of property invoicedNum.
     *
     */
    public int getInvoicedNum() {
        return this.invoicedNum;
    }
    
    
    /** Getter for property returnedNum.
     * @return Value of property returnedNum.
     *
     */
    public int getReturnedNum() {
        return this.returnedNum;
    }
    
    
}
