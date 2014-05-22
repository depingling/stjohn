/**
 *  Title: AutoOrderMgrForm Description: This is the Struts ActionForm
 *  class for running automatic order batch.
 *  Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     Yuriy Kupershmidt
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.value.*;
/**
 *  Form bean for the catalog manager configuration page.
 *
 *@author     tbesser
 *@created    October 14, 2001
 */
public final class AutoOrderMgrForm extends ActionForm {
   private String _batchDate = Constants.date2string(Constants.getCurrentDate());
   private OrderBatchLogDataVector _orderBatchLog = new OrderBatchLogDataVector();


   public void setBatchDate(String pValue) {_batchDate = pValue;}
   public String getBatchDate(){return _batchDate;}

   public void setOrderBatchLog(OrderBatchLogDataVector pValue) {_orderBatchLog = pValue;}
   public OrderBatchLogDataVector getOrderBatchLog(){return _orderBatchLog;}

    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    }


    /**
     *  <code>validate</code> method is a stub.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     *@return          an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        // No validation necessary.
        return null;
    }

}

