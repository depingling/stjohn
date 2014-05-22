package com.cleanwise.view.logic;

import java.util.*;
import java.util.List;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;

import java.math.BigDecimal;

/**
 *@author     YKupershmidt
 *@created    September 11, 2003
 */
public class CostLocateMgrLogic {





    /**
     *  <code>init</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors init(HttpServletRequest request,
			    ActionForm form)
 	throws Exception {
        try{
        APIAccess factory = new APIAccess();
        Distributor distributorEjb = factory.getDistributorAPI();
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        String feedField = request.getParameter("feedField");
        String itemSkuS = request.getParameter("itemSku");
        int itemSku = 0;
        try{
          itemSku = Integer.parseInt(itemSkuS);
        }catch(Exception exc) {
          ae.add("error", new ActionError("error.simpleGenericError", "Wrong sku number: " + itemSkuS));
          return ae;
        }
        String distIdS = request.getParameter("distId");
        int distId = 0;
        if(distIdS!=null && distIdS.trim().length()>0) {
        try{
          distId = Integer.parseInt(distIdS);
        }catch(Exception exc) {
          ae.add("error", new ActionError("error.simpleGenericError", "Wrong distributor id: " + distIdS));
          exc.printStackTrace();
          return ae;
        }
        }
        String distErpNum = request.getParameter("distErpNum");
        String accountIdS = request.getParameter("accountId");
        int accountId = 0;
        try{
          accountId = Integer.parseInt(accountIdS);
        }catch(Exception exc) {
          ae.add("error", new ActionError("error.simpleGenericError", "Wrong account id: " + accountIdS));
          return ae;
        }
        DistributorData distD = null;
        if(distId>0) {
          distD = distributorEjb.getDistributor(distId);
        }else {
          distD = distributorEjb.getDistributorByErpNum(distErpNum);  
        }
        CostLocateMgrForm sForm = (CostLocateMgrForm) form;
        sForm.setFeedBackFieldName(feedField);
        sForm.setAccountId(accountId);
        sForm.setSkuNum(itemSku);
        sForm.setDistId(distD.getBusEntity().getBusEntityId());
        sForm.setDistErpNum(distD.getBusEntity().getErpNum());
        sForm.setDistDesc(distD.getBusEntity().getShortDesc());
        sForm.setItemCostVector(new ItemContractCostViewVector());  
	//ae = search(request,form);
	    
        return ae;
        }catch(Exception exc) {
          exc.printStackTrace();
          throw exc;
        }
    }

    
    public static ActionErrors search(HttpServletRequest request,
			    ActionForm form)
    throws Exception
    {
      ActionErrors ae = new ActionErrors();
      CostLocateMgrForm sForm = (CostLocateMgrForm) form;
      try {
      APIAccess factory = new APIAccess();
      Contract contractEjb = factory.getContractAPI();
      ItemContractCostViewVector itemContrCostVwV = 
         contractEjb.getItemContractCost(
         sForm.getAccountId(), sForm.getSkuNum(),sForm.getDistId(),  sForm.getDistErpNum()); 

      sForm.setItemCostVector(itemContrCostVwV);  
      return ae;
      }catch(Exception exc) {
        exc.printStackTrace();
        throw exc;
      }
    }

    public static ActionErrors getAll(HttpServletRequest request,
			    ActionForm form)
    throws Exception
    {
      ActionErrors ae = new ActionErrors();
      CostLocateMgrForm sForm = (CostLocateMgrForm) form;
      APIAccess factory = new APIAccess();
      Contract contractEjb = factory.getContractAPI();
      ItemContractCostViewVector itemContrCostVwV = 
         contractEjb.getItemContractCost(
         sForm.getAccountId(), sForm.getSkuNum(),0,  null); 
      sForm.setItemCostVector(itemContrCostVwV);  
      return ae;
    }



    /**
     *  <code>sort</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sort(HttpServletRequest request,
			    ActionForm form)
	throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        CostLocateMgrForm sForm = (CostLocateMgrForm) form;
	String sortField = request.getParameter("sortField");
        ItemContractCostViewVector iccVwV = sForm.getItemCostVector();
        iccVwV.sort(sortField);
    }






    


}


