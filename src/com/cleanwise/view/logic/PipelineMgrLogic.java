package com.cleanwise.view.logic;

import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;

import com.cleanwise.view.forms.PipelineMgrForm;

import java.math.*;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;


/**
 * <code>PipelingMgrLogic</code> implements the logic needed to
 * manipulate pipeline records.
 *
 * @author durval
 */
public class PipelineMgrLogic
{


    /**
     * <code>search</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors init(HttpServletRequest request, 
                                      ActionForm form)
         throws Exception
    {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        PipelineMgrForm pForm = (PipelineMgrForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);
        ListService listServEjb = factory.getListServiceAPI();
        if(pForm.getPipelineTypes()==null) {
     	    RefCdDataVector pipelineTypeDV =
		            listServEjb.getRefCodesCollection("PIPELINE_CD");
          pForm.setPipelineTypes(pipelineTypeDV);
        }
        /*
        if(pForm.getPipelineRecords()==null) {
          pForm.setPipelineRecords(new PipelineDataVector());
        }
        if(pForm.getSelectedRecords()==null) {
          pForm.setSelectedRecords(new PipelineDataVector());
        }
         */
	       return ae;
    }

    
    public static ActionErrors search(HttpServletRequest request, 
                                      ActionForm form)
         throws Exception
    {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        PipelineMgrForm pForm = (PipelineMgrForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);
        Pipeline pipelineEjb = factory.getPipelineAPI();
        String pipelineTypeCd = pForm.getPipelineTypeFilter();
        PipelineDataVector pipelineDV = pipelineEjb.getPipelinesCollection(pipelineTypeCd);
        pForm.setPipelineRecords(pipelineDV);
        
	       return ae;
    }

    public static ActionErrors copy(HttpServletRequest request, 
                                      ActionForm form)
         throws Exception
    {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        PipelineMgrForm pForm = (PipelineMgrForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);
        PipelineDataVector pipelineDV = pForm.getPipelineRecords();
        int[] selected = pForm.getSelected();
        PipelineDataVector selectedPipelineDV = new PipelineDataVector();
               
        for(Iterator iter=pipelineDV.iterator(); iter.hasNext();) {
          PipelineData pD = (PipelineData) iter.next();
          int pipelineId = pD.getPipelineId();
          for(int ii=0; ii<selected.length; ii++) {
            if(pipelineId==selected[ii]) {
              PipelineData copyPipelineD = (PipelineData) pD.clone();
              copyPipelineD.setPipelineId(0);
              selectedPipelineDV.add(copyPipelineD);
            }
          }
        }
        pForm.setSelectedRecords(selectedPipelineDV);
	       return ae;
    }

    public static ActionErrors paste(HttpServletRequest request, 
                                      ActionForm form)
         throws Exception
    {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        PipelineMgrForm pForm = (PipelineMgrForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);
        PipelineDataVector selectedPipelineDV = pForm.getSelectedRecords();
        if(selectedPipelineDV==null || selectedPipelineDV.size()==0) {
          String errorMess = "No copied data";
          ae.add("error", 
                  new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
        
        PipelineDataVector pipelineDV = pForm.getPipelineRecords();
        String pipelineTypeCd = pForm.getPipelineTypeFilter();
        if(!Utility.isSet(pipelineTypeCd)) {
            String errorMess = "Pipeline type is not set";
            ae.add("error", 
                    new ActionError("error.simpleGenericError", errorMess));
            return ae;             
        }
       
        
        if(pipelineDV==null) pipelineDV = new PipelineDataVector();

        for(Iterator iter=pipelineDV.iterator(); iter.hasNext();) {
          PipelineData pD = (PipelineData) iter.next();
          if(!pipelineTypeCd.equals(pD.getPipelineTypeCd())) {
            String errorMess = "Inconsistent Pipeline Types";
            ae.add("error", 
                    new ActionError("error.simpleGenericError", errorMess));
            return ae;             
          }
        }
          
               
        for(Iterator iter=selectedPipelineDV.iterator(); iter.hasNext();) {
          PipelineData pD = (PipelineData) iter.next();
          PipelineData clonePD = (PipelineData) pD.clone();
          clonePD.setPipelineId(0);
          clonePD.setPipelineTypeCd(pipelineTypeCd);
          pipelineDV.add(clonePD);
        }
        pForm.setPipelineRecords(pipelineDV);
	       return ae;
    }

    public static ActionErrors save(HttpServletRequest request, 
                                      ActionForm form)
         throws Exception
    {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        PipelineMgrForm pForm = (PipelineMgrForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);
        PipelineDataVector pipelineDV = pForm.getPipelineRecords();
        if(pipelineDV==null || pipelineDV.size()==0) {
          String errorMess = "Nothing to save";
          ae.add("error", 
                  new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
        
        String pipelineTypeCd = pForm.getPipelineTypeFilter();
        if(!Utility.isSet(pipelineTypeCd)) {
            String errorMess = "Pipeline type is not set";
            ae.add("error", 
                    new ActionError("error.simpleGenericError", errorMess));
            return ae;             
        }
       
        
        for(Iterator iter=pipelineDV.iterator(); iter.hasNext();) {
          PipelineData pD = (PipelineData) iter.next();
          if(!pipelineTypeCd.equals(pD.getPipelineTypeCd())) {
            String errorMess = "Inconsistent Pipeline Types";
            ae.add("error", 
                    new ActionError("error.simpleGenericError", errorMess));
            return ae;             
          }
        }
          
        Pipeline pipelineEjb = factory.getPipelineAPI();
        PipelineDataVector oldPipelineDV = pipelineEjb.getPipelinesCollection(pipelineTypeCd);
        PipelineDataVector savePipelineDV = new PipelineDataVector();
        for(Iterator iter=pipelineDV.iterator(); iter.hasNext();) {
          PipelineData pD = (PipelineData) iter.next();
          if(pD.getPipelineId()==0) {
            savePipelineDV.add(pD);          
          } else {
            boolean foundFl = false;
            for(Iterator iter1=oldPipelineDV.iterator(); iter1.hasNext();) {
              PipelineData oldPD = (PipelineData) iter1.next();
              if(oldPD.getPipelineId()==pD.getPipelineId()) {
                foundFl = true;
                if(RefCodeNames.PIPELINE_STATUS_CD.ACTIVE.equals(pD.getPipelineStatusCd())) {
                  try {
                     Class clazz = Thread.currentThread().
                             getContextClassLoader().loadClass(pD.getClassname());
                     Object scratch = clazz.newInstance();
                  } catch (Exception exc) {
                    String errorMess = "Class not found: "+exc.getMessage();
                    ae.add("error", 
                            new ActionError("error.simpleGenericError", errorMess));
                    return ae;             
                  }
                }
                if(!pD.equals(oldPD)) {
                  savePipelineDV.add(pD);
                }
                break;
              }
            }
            if(!foundFl) {
              String errorMess = "Inconsistent pipeline step. Pipeline id: "+pD.getPipelineId();
              ae.add("error", 
                      new ActionError("error.simpleGenericError", errorMess));
              return ae;             
            }
          }
        }

        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
                                        "ApplicationUser");
        String userName = appUser.getUser().getUserName();
        
        for(Iterator iter=savePipelineDV.iterator(); iter.hasNext();) {
          PipelineData pD = (PipelineData) iter.next();
          pipelineEjb.savePipeline(pD, userName);
        }

        pipelineDV = pipelineEjb.getPipelinesCollection(pipelineTypeCd);
        pForm.setPipelineRecords(pipelineDV);
	       return ae;
    }
    
    
    public static ActionErrors delete(HttpServletRequest request, 
                                      ActionForm form)
         throws Exception
    {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        PipelineMgrForm pForm = (PipelineMgrForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);
        PipelineDataVector pipelineDV = pForm.getPipelineRecords();
        if(pipelineDV==null || pipelineDV.size()==0) {
          String errorMess = "Nothing to delete";
          ae.add("error", 
                  new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
               
        int[] selected = pForm.getSelected();
        
        
        PipelineDataVector deletePipelineDV = new PipelineDataVector();
        for(Iterator iter=pipelineDV.iterator(); iter.hasNext();) {
          PipelineData pD = (PipelineData) iter.next();
          int pipelineId = pD.getPipelineId();
          for(int ii=0; ii<selected.length; ii++) {
            if(pipelineId==selected[ii]) {
              deletePipelineDV.add(pD);
              if(!RefCodeNames.PIPELINE_STATUS_CD.INACTIVE.equals(pD.getPipelineStatusCd())) {
                String errorMess = "Only inactive steps could be deleted";
                ae.add("error", 
                        new ActionError("error.simpleGenericError", errorMess));
                return ae;
              }
            }
          }
        }
        
        Pipeline pipelineEjb = factory.getPipelineAPI();
        for(Iterator iter=deletePipelineDV.iterator(); iter.hasNext();) {
          PipelineData pD = (PipelineData) iter.next();        
          pipelineEjb.removePipeline(pD);
        }
          
        pipelineDV = pipelineEjb.getPipelinesCollection(pForm.getPipelineTypeFilter());
        pForm.setPipelineRecords(pipelineDV);
	       return ae;
    }
    
    
    public static ActionErrors createNew(HttpServletRequest request, 
                                      ActionForm form)
         throws Exception
    {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        PipelineMgrForm pForm = (PipelineMgrForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);
        PipelineDataVector pipelineDV = pForm.getPipelineRecords();
        if(pipelineDV==null) pipelineDV = new PipelineDataVector();
        
        PipelineData pipelineD = PipelineData.createValue();
        pipelineD.setPipelineStatusCd(RefCodeNames.PIPELINE_STATUS_CD.INACTIVE);
        pipelineD.setPipelineOrder(9999);
        pipelineD.setPipelineTypeCd(pForm.getPipelineTypeFilter());
        pipelineD.setShortDesc(pForm.getPipelineTypeFilter());
        pipelineD.setClassname("com.cleanwise.service.api.pipeline.");
        pipelineDV.add(pipelineD);
        pForm.setPipelineRecords(pipelineDV);
	       return ae;
    }

}


