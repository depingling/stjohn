package com.cleanwise.view.actions;


import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.TemplateUtilities;
import com.cleanwise.service.api.value.TemplateData;
import com.cleanwise.service.api.value.TemplateDataVector;
import com.cleanwise.view.forms.StoreTemplateForm;
import com.cleanwise.view.logic.StoreTemplateMgrLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;

public class StoreTemplateMgrAction extends ActionSuper {
  
  public ActionForward performSub(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    
    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if (!st.checkSession()) {
    	return mapping.findForward(Constants.GLOBAL_FORWARD_LOGON);
    }
    
    //Determine the type of template being worked with.  Currently we just have email templates,
    //so if any other value is passed throw an exception
	StoreTemplateForm templateForm = (StoreTemplateForm)form;
	String templateType = templateForm.getTemplateData().getType();
    if (!Constants.TEMPLATE_TYPE_EMAIL.equalsIgnoreCase(templateType)) {
    	throw new ServletException("Unknown template type (" + templateType + ") encountered.");
    }
    
    // Determine the action to be performed
    String action = request.getParameter("userAction");
    if (action == null) {
      action = "init";
    }
    
    // Get the form buttons as specified in the properties file.
    MessageResources mr = getResources(request);
    String searchStr = getMessage(mr, request, "global.action.label.search");
    String createStr = getMessage(mr, request, "admin.button.create");
    String saveStr = getMessage(mr, request, "global.action.label.save");
    String deleteStr = getMessage(mr, request, "global.action.label.delete");
    String createCloneStr = getMessage(mr,request,"admin.button.createClone");
    String previewStr = getMessage(mr,request,"admin.button.preview");
    String showOutputStr = getMessage(mr,request,"button.template.showOutput");
    
    //Process the action.
    //Note that for now we do not utilize the template type passed in, since we know we're
    //dealing with email templates.  If we add additional template types in the future, the
    //template type should be utilized to determine which method on the logic class to invoke.
	String actionForwardName = "";
    try {
    	if (action.equals(searchStr)) {
    	    //reset the session attribute(s).
    		HttpSession session = request.getSession();
    	    session.setAttribute(Constants.ATTRIBUTE_FOUND_TEMPLATE_VECTOR, new TemplateDataVector());
    	    //call out to the logic class to perform the work
    		ActionErrors ae = StoreTemplateMgrLogic.findEmailTemplates(templateForm);
    		handleErrors(request, ae);
    		//update the session attributes
    	    session.setAttribute(Constants.ATTRIBUTE_FOUND_TEMPLATE_VECTOR, templateForm.getTemplateDataVector());
    	    actionForwardName = Constants.FORWARD_TEMPLATE_SEARCH_EMAIL;
    	}
    	else if (action.equals(Constants.TEMPLATE_ACTION_GET_DETAIL)) {
    		ActionErrors ae = StoreTemplateMgrLogic.getEmailTemplate(templateForm);
    		handleErrors(request, ae);
    		actionForwardName = Constants.FORWARD_TEMPLATE_DETAIL_EMAIL;
    	} 
    	else if (action.equals(createStr)){
        	//initialize the template with the id of the current store
            CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
            templateForm.getTemplateData().setBusEntityId(appUser.getUserStore().getStoreId());
            //initialize the template with the appropriate type.
            templateForm.getTemplateData().setType(Constants.TEMPLATE_TYPE_EMAIL);
            //initialize the template with the appropriate id (0 to indicate a new template).
            templateForm.getTemplateData().setTemplateId(0);
            actionForwardName = Constants.FORWARD_TEMPLATE_DETAIL_EMAIL;
    	} 
    	else if (action.equals(saveStr)) {
    		ActionErrors ae = StoreTemplateMgrLogic.updateEmailTemplate(request, templateForm);
    		handleErrors(request, ae);
    		actionForwardName = Constants.FORWARD_TEMPLATE_DETAIL_EMAIL;
    	} 
    	else if (action.equals(deleteStr)) {
    		ActionErrors ae = StoreTemplateMgrLogic.deleteEmailTemplate(templateForm);
    		handleErrors(request, ae);
    		if (ae.size() > 0) {
    			actionForwardName = Constants.FORWARD_TEMPLATE_DETAIL_EMAIL;
    		}
    		else {
        		//remove the deleted template from the results of the last search before 
    			//returning to the search page.
        		HttpSession session = request.getSession();
        	    TemplateDataVector currentTemplates = (TemplateDataVector)session.getAttribute(Constants.ATTRIBUTE_FOUND_TEMPLATE_VECTOR);
        	    if (currentTemplates != null) {
        	    	Iterator<TemplateData> templateIterator = currentTemplates.iterator();
        	    	while (templateIterator.hasNext()) {
        	    		TemplateData template = templateIterator.next();
        	    		if (template.getTemplateId() == templateForm.getTemplateData().getTemplateId()) {
        	    			templateIterator.remove();
        	    		}
        	    	}
        	    }
        		actionForwardName = Constants.FORWARD_TEMPLATE_SEARCH_EMAIL;
    		}
    	} 
    	else if (action.equals(createCloneStr)) {
    		ActionErrors ae = StoreTemplateMgrLogic.cloneEmailTemplate(templateForm);
    		handleErrors(request, ae);
    		actionForwardName = Constants.FORWARD_TEMPLATE_DETAIL_EMAIL;
    	}
    	else if (action.equals(previewStr)) {
    		//no validation is done here.  Because this functionality opens a new window, any errors we returned
    		//would be shown in that new window (too late to prevent the window from opening).  Therefore, any
    		//required  validation is done on the JSP via javascript and this method just needs to return the
    		//action forward.
    		actionForwardName = Constants.FORWARD_TEMPLATE_PREVIEW_EMAIL;
    	}
    	else if (action.equals(showOutputStr)) {
    		ActionErrors ae = StoreTemplateMgrLogic.previewEmailTemplate(request, templateForm);
    		handleErrors(request, ae);
    		actionForwardName = Constants.FORWARD_TEMPLATE_PREVIEW_EMAIL;
    	}
    	else {
        	//clear any existing session attributes
        	HttpSession session = request.getSession();
    	    session.setAttribute(Constants.ATTRIBUTE_FOUND_TEMPLATE_VECTOR, null);
    	    //call out to the logic class to perform the work
    		StoreTemplateMgrLogic.initEmailTemplate(request, templateForm);
    	    //initialize the template on the form with the current store id
            CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
            templateForm.getTemplateData().setBusEntityId(appUser.getUserStore().getStoreId());
            //initialize the template on the form with the correct type
            templateForm.getTemplateData().setType(Constants.TEMPLATE_TYPE_EMAIL);
            actionForwardName = Constants.FORWARD_TEMPLATE_SEARCH_EMAIL;
    	}
    } 
    catch (Exception e) {
    	e.printStackTrace();
    	ActionErrors ae = new ActionErrors();
    	ae.add("template", new ActionError("error.systemError",e.getMessage()));
    	handleErrors(request, ae);
    	actionForwardName = Constants.FORWARD_TEMPLATE_SEARCH_EMAIL;
    }
    int storeId = templateForm.getTemplateData().getBusEntityId();
    try {
    	String storeName = new APIAccess().getStoreAPI().getStore(storeId).getBusEntity().getShortDesc();
    	templateForm.getTemplateData().setBusEntityName(storeName);
    }
    catch (Exception e) {
    	
    }
    //retrieve and populate the text shown for a sample template
    try {
    	TemplateData template = TemplateUtilities.getSystemEmailTemplate(
    			Constants.TEMPLATE_EMAIL_SYSTEM_ORDER_CONFIRMATION_TEMPLATE_NAME,
    			Constants.TEMPLATE_EMAIL_PROPERTY_LOCALE_DEFAULT);
    	templateForm.setSampleContent(template.getContent());
    }
    catch (Exception e) {
    }
	return mapping.findForward(actionForwardName);
  }
  
  private void handleErrors(HttpServletRequest request, ActionErrors ae) {
	  if(ae.size()>0) {
		  saveErrors(request, ae);
	  }	  
  }
}


