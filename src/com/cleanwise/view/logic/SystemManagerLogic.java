/*
 * SystemManagerLogic.java
 *
 * Created on October 4, 2002, 10:47 AM
 */

package com.cleanwise.view.logic;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Content;
import com.cleanwise.service.api.session.SystemAgent;
import com.cleanwise.view.utils.Constants;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

/**
 *
 * @author  bstevens
 */
public class SystemManagerLogic {
    
    /**
     *Tries to free up any availiable memory by preforming a garabage collection
     *and runing finalizations.  Note this assumes that the EJB container is running
     *on the same VM as the servlet engine, which is not necessarily true, but
     *in the current situation this is the case.
     */
    public static void freeMemory() {
        Runtime.getRuntime().gc();
        Runtime.getRuntime().runFinalization();
    }
    
    
    /**
     *Uses the system agent bean to verify that the dao layer is the same as the underlying database.
     */
    public static ActionErrors verifyDaoLayer(HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        SystemAgent systemAgentEJB;
        try{
            // Get a reference to the admin facade
            APIAccess factory = new APIAccess();
            systemAgentEJB = factory.getSystemAgentAPI();
        }catch (Exception e){
            e.printStackTrace();
            errors.add("SystemManagerLogic",new ActionError("error.genericError",e.getMessage()));
            //without ejb access we can't proceed any farther
            return errors;
        }
        try{
            List daoErrors = systemAgentEJB.verifyDaoLayer();
            request.setAttribute("daoErrors", daoErrors);
        }catch (Exception e){
            e.printStackTrace();
            errors.add("SystemManagerLogic",new ActionError("error.genericError",e.getMessage()));
        }
        return errors;
    }
    
    public static ActionErrors refreshContent(HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        HttpSession session = request.getSession();
    
        try{
            APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
            Content cont = factory.getContentAPI();
            cont.refreshBinaryData(false);
//            APIAccess factory = new APIAccess();
//            systemAgentEJB = factory.getSystemAgentAPI();
        } catch (Exception e){
            e.printStackTrace();
            errors.add("SystemManagerLogic",new ActionError("error.genericError", e.getMessage()));
            return errors;
        }

        return errors;
    }
    
    /**
     *Gets all of the various failed data and puts it into the HttpServletRequest object.
     */
    public static ActionErrors getFailedData(HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        SystemAgent systemAgentEJB;
        try{
            // Get a reference to the admin facade
            APIAccess factory = new APIAccess();
            systemAgentEJB = factory.getSystemAgentAPI();
        }catch (Exception e){
            e.printStackTrace();
            errors.add("SystemManagerLogic",new ActionError("error.genericError",e.getMessage()));
            //without ejb access we can't proceed any farther
            return errors;
        }
        
        try{
            
            request.setAttribute("freeMemory", new Long(Runtime.getRuntime().freeMemory()));
            request.setAttribute("totalMemory", new Long(Runtime.getRuntime().totalMemory()));
            
            OrderDataVector odv = systemAgentEJB.getFailedOrders();
            request.setAttribute("failedOrders", odv);
            
            odv = systemAgentEJB.getPendingOrders();
            request.setAttribute("pendingOrders", odv);
            
            InvoiceCustDataVector icdv = systemAgentEJB.getFailedCustomerInvoices();
            request.setAttribute("failedCustInvoice", icdv);
            
            InvoiceCustDataVector icdvcit = systemAgentEJB.getFailedCITCustomerInvoices();
            request.setAttribute("failedCITCustInvoice", icdvcit);
            
            InvoiceDistDataVector iddv = systemAgentEJB.getFailedDistributorInvoices();
            request.setAttribute("failedDistInvoice", iddv);
            
            OrderBatchLogDataVector obldv = systemAgentEJB.getFailedOrderBatchEntries();
            request.setAttribute("failedOrderBatchEntries", obldv);
            
            PurchaseOrderDataVector podv = systemAgentEJB.getFailedPurchaseOrders();
            request.setAttribute("failedPurchaseOrders", podv);
            
            RemittanceDataVector rdv = systemAgentEJB.getFailedRemittanceData();
            request.setAttribute("failedRemittanceData", rdv);
            
            RemittanceDetailDataVector rddv = systemAgentEJB.getFailedRemittanceDetailData();
            request.setAttribute("failedRemittanceDetailData", rddv);
            
            RemittancePropertyDataVector rpdv = systemAgentEJB.getUnparsableRemittanceData();
            request.setAttribute("unparsableRemittanceData", rpdv);
        }catch (Exception e){
            e.printStackTrace();
            errors.add("SystemManagerLogic",new ActionError("error.genericError",e.getMessage()));
        }
        return errors;
    }
}
