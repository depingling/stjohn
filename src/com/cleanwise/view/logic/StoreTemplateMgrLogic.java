package com.cleanwise.view.logic;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dto.template.OrderDto;
import com.cleanwise.service.api.dto.template.OrderItemDto;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.TemplateUtilities;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.EntitySearchCriteria;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderDataVector;
import com.cleanwise.service.api.value.OrderStatusCriteriaData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.RefCdData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.TemplateData;
import com.cleanwise.service.api.value.TemplateDataExtended;
import com.cleanwise.service.api.value.TemplateDataVector;
import com.cleanwise.view.forms.StoreTemplateForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

public class StoreTemplateMgrLogic {
	
	//the property under which errors will be returned
	private static final String ERROR_PROPERTY = "error";
	
    /**
     * Method used to initialize the application for template management.
     * @param request - the <code>HttpServletRequest</code>
     * @param templateForm - the Struts action form
     * @throws Exception
     */
    public static void initEmailTemplate(HttpServletRequest request, StoreTemplateForm templateForm) throws Exception {
		
	    // Cache the lists needed for templates.
    	HttpSession session = request.getSession();
	    if (session.getAttribute(Constants.ATTRIBUTE_TEMPLATE_LOCALES_VECTOR) == null) {
	    	RefCdDataVector locales =
		        new APIAccess().getListServiceAPI().getRefCodesCollection("LOCALE_CD");
		    //add language-only options and sort the list
	    	updateLocaleChoices(locales);
		    session.setAttribute(Constants.ATTRIBUTE_TEMPLATE_LOCALES_VECTOR, locales);
		}
	    if (session.getAttribute(Constants.ATTRIBUTE_TEMPLATE_FULL_LOCALES_VECTOR) == null) {
	    	RefCdDataVector locales =
		        new APIAccess().getListServiceAPI().getRefCodesCollection("LOCALE_CD");
		    //sort the list
	    	Map refCodeMap = new HashMap();
	    	Iterator<RefCdData> iterator = locales.iterator();
	    	while (iterator.hasNext()) {
	    		RefCdData data = iterator.next();
	    		String value = data.getValue();
	    		refCodeMap.put(value, data);
	    	}
	    	TreeMap orderedSet = new TreeMap(refCodeMap);
	    	locales.clear();
	    	Iterator keyIterator = orderedSet.keySet().iterator();
	    	while (keyIterator.hasNext()) {
	    		String key = (String)keyIterator.next();
	    		locales.add(orderedSet.get(key));
	    	}
		    session.setAttribute(Constants.ATTRIBUTE_TEMPLATE_FULL_LOCALES_VECTOR, locales);
		}
    }
    
    /*
     * Method that modifies a list of locale choices to include "language-only" options.
     * @param locales - a vector of <code>RefCdData</code> objects representing the locale choices.
     */
    private static void updateLocaleChoices(RefCdDataVector locales) {
    	Map refCodeMap = new HashMap();
    	Iterator<RefCdData> iterator = locales.iterator();
    	while (iterator.hasNext()) {
    		RefCdData data = iterator.next();
    		String value = data.getValue();
    		refCodeMap.put(value, data);
    		int underscoreLocation = value.indexOf("_");
    		if (underscoreLocation > 0) {
    			String language = value.substring(0, underscoreLocation);
    			RefCdData newData = new RefCdData();
    			newData.setShortDesc(language);
    			newData.setValue(language);
    			refCodeMap.put(language, newData);
    		}
    	}
    	//order the choices by creating a treemap
    	TreeMap orderedSet = new TreeMap(refCodeMap);
    	//clear the original list of choices
    	locales.clear();
    	//add the default choice
		RefCdData newData = new RefCdData();
		newData.setShortDesc(Constants.TEMPLATE_EMAIL_PROPERTY_LOCALE_DEFAULT);
		newData.setValue(Constants.TEMPLATE_EMAIL_PROPERTY_LOCALE_DEFAULT);
    	locales.add(newData);
    	//now add the locale choices
    	Iterator keyIterator = orderedSet.keySet().iterator();
    	while (keyIterator.hasNext()) {
    		String key = (String)keyIterator.next();
    		locales.add(orderedSet.get(key));
    	}
    }
    
	/**
	 * Method to search for and retrieve Email templates.
     * @param templateForm - the Struts action form
	 * @return - a collection of Struts <code>ActionError</code> objects
	 * @throws Exception
	 */
	public static ActionErrors findEmailTemplates(StoreTemplateForm templateForm) throws Exception {
		ActionErrors returnValue = new ActionErrors();
	    
	    //make sure a store id has been specified - return an error if not
	    int storeId = templateForm.getTemplateData().getBusEntityId();
	    if (storeId <= 0) {
	        String errorMessage = "No store id was specified for the template search.";
	        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage));
	    }
	    
	    //if we found any problems, return the errors without performing a search.
	    if (returnValue.size() > 0) {
	    	return returnValue;
	    }

	    //perform the search.
	    String searchField = templateForm.getSearchField();
	    String searchType = templateForm.getSearchType();

        EntitySearchCriteria searchCriteria = new EntitySearchCriteria();
        
        //specify the store id as an implicit search criteria
        IdVector idVector = new IdVector();
        idVector.add(storeId);
        searchCriteria.setStoreBusEntityIds(idVector);

        //specify any explicit search criteria
        if (Utility.isSet(searchField)) {
            if (searchType.equals(Constants.TEMPLATE_SEARCH_BY_ID)) {
            	//bug 5254 - make sure the searchField is numeric.  If not, there is no possibility of a match
            	//so return an empty vector
            	if (Utility.isInteger(searchField)) {
            		searchCriteria.setSearchId(searchField);
            	}
            	else {
            	    templateForm.setTemplateDataVector(new TemplateDataVector());
            	    return returnValue;
            	}
            }
            else if (searchType.equals(Constants.TEMPLATE_SEARCH_BY_NAME_CONTAINS)) {
                searchCriteria.setSearchName(searchField);
                searchCriteria.setSearchNameType(EntitySearchCriteria.NAME_CONTAINS);
            }
            else if (searchType.equals(Constants.TEMPLATE_SEARCH_BY_NAME_BEGINS)) {
                searchCriteria.setSearchName(searchField);
                searchCriteria.setSearchNameType(EntitySearchCriteria.NAME_STARTS_WITH);
            }
        }

        //perform the search
	    TemplateDataVector templateVector = new APIAccess().getTemplateAPI().getTemplateByCriteria(searchCriteria);
	    
	    templateForm.setTemplateDataVector(templateVector);
	    return returnValue;
	}

	/**
	 * Method to delete an Email template object
     * @param templateForm - the Struts action form
	 * @return - a collection of Struts <code>ActionError</code> objects
	 * @throws Exception
	 */
	public static ActionErrors deleteEmailTemplate(StoreTemplateForm templateForm) throws Exception {
		ActionErrors returnValue = new ActionErrors();
    	
    	//verify the template and store ids are greater than 0
    	TemplateDataExtended template = templateForm.getTemplateData();
    	int templateId = template.getTemplateId();
    	int storeId = template.getBusEntityId();
    	if (templateId <= 0) {
	        String errorMessage = "Invalid template id (" + templateId + ") encountered during delete.";
	        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage));
    	}
    	if (storeId <= 0) {
	        String errorMessage = "Invalid store id (" + storeId + ") encountered during delete.";
	        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage));
    	}
    	//if we have valid template and store ids, make sure the template can be deleted.
    	if (storeId > 0 && templateId > 0) {
        	TemplateDataExtended existingTemplate = null;
        	StoreTemplateForm localForm = new StoreTemplateForm();
        	TemplateDataExtended localTemplate = new TemplateDataExtended();
        	localTemplate.setBusEntityId(storeId);
        	localTemplate.setTemplateId(templateId);
        	localForm.setTemplateData(localTemplate);
        	StoreTemplateMgrLogic.getEmailTemplate(localForm);
        	existingTemplate = localForm.getTemplateData();
    		if (!StoreTemplateMgrLogic.isEmailTemplateDeletable(existingTemplate)) {
    	        StringBuilder errorMessage = new StringBuilder(150);
    	        errorMessage.append("This template has been specified for usage in email generation and cannot be deleted.");
    	        errorMessage.append("To delete the template you must first remove the specification of its usage for email generation.");
    	        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage.toString()));
    		}
    	}
	    
	    //if errors were found, return without attempting a deletion of the template.
	    if (returnValue.size() > 0) {
	    	return returnValue;
	    }
	    
	    //delete the template
	    new APIAccess().getTemplateAPI().deleteTemplate(template);
	    
	    return returnValue;
	}

	/**
	 * Method to clone an Email template object
     * @param templateForm - the Struts action form
	 * @return - a collection of Struts <code>ActionError</code> objects
	 * @throws Exception
	 */
	public static ActionErrors cloneEmailTemplate(StoreTemplateForm templateForm) throws Exception {
		ActionErrors returnValue = new ActionErrors();
		
    	//verify the template id is > 0 - if not then return the error
    	TemplateDataExtended template = templateForm.getTemplateData();
    	int templateId = template.getTemplateId();
    	if (templateId <= 0) {
	        String errorMessage = "Invalid template id (" + templateId + ") encountered during clone.";
	        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage));
		    return returnValue;
    	}
    	
	    //call out to the get method to retrieve the template, returning any errors encountered
    	returnValue = getEmailTemplate(templateForm);
    	if (returnValue.size() > 0 ) {
    		return returnValue;
    	}
    	
		//set the id of the template to 0 and modify the name of the template to indicate it is a clone.
		template = templateForm.getTemplateData();
		template.setTemplateId(0);
		template.setName("Clone of >>> " + template.getName());
		
	    return returnValue;
	}

	/**
	 * Method to retrieve a specific Email template object
     * @param templateForm - the Struts action form
	 * @return - a collection of Struts <code>ActionError</code> objects
	 * @throws Exception
	 */
	public static ActionErrors getEmailTemplate(StoreTemplateForm templateForm) throws Exception {
		ActionErrors returnValue = new ActionErrors();
    	
    	//verify the template id is > 0 - if not then return the error
    	TemplateDataExtended template = templateForm.getTemplateData();
    	int templateId = template.getTemplateId();
    	if (templateId <= 0) {
	        String errorMessage = "Invalid template id (" + templateId + ") encountered during get.";
	        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage));
		    return returnValue;
    	}

		//call out to the find method to perform a search
		StoreTemplateForm localForm = new StoreTemplateForm();
		TemplateDataExtended localTemplate = new TemplateDataExtended();
		localTemplate.setBusEntityId(templateForm.getTemplateData().getBusEntityId());
		localForm.setTemplateData(localTemplate);
		localForm.setSearchField(templateForm.getTemplateData().getTemplateId() + "");
		localForm.setSearchType(Constants.TEMPLATE_SEARCH_BY_ID);
		returnValue = findEmailTemplates(localForm);
		
		//if there were problems performing the search, return the errors.
		if (returnValue.size() > 0) {
			return returnValue;
		}
		
		//if any number of templates other than 1 was found, return an error.
		if (localForm.getTemplateDataVector().size() == 0) {
	        String errorMessage = "No template matching the specified criteria was found.";
	        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage));
		}
		if (localForm.getTemplateDataVector().size() > 1) {
	        String errorMessage = "Multiple templates matching the specified criteria were found.";
	        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage));
		}
		
		//retrieve the located template and place it on the form
		templateForm.setTemplateData((TemplateDataExtended)localForm.getTemplateDataVector().get(0));

	    return returnValue;
	}

    /**
     * Method to update a specific Email template object
     * @param request - the HttpServletRequest
     * @param templateForm - the Struts action form
	 * @return - a collection of Struts <code>ActionError</code> objects
     * @throws Exception
     */
    public static ActionErrors updateEmailTemplate(HttpServletRequest request, StoreTemplateForm templateForm) throws Exception {
    	ActionErrors returnValue = null;
        TemplateDataExtended templateData = templateForm.getTemplateData();
        if (templateData.getTemplateId() == 0) {
        	returnValue = createEmailTemplate(request, templateForm);
        }
        else {
        	returnValue = modifyEmailTemplate(request, templateForm);
        }
        return returnValue;
    }
    
    /*
     * Method to create an Email template object
     * @param request - the HttpServletRequest
     * @param templateForm - the Struts action form
	 * @return - a collection of Struts <code>ActionError</code> objects
     * @throws Exception
     */
    private static ActionErrors createEmailTemplate(HttpServletRequest request, StoreTemplateForm templateForm) throws Exception {
    	//verify required data has been specified and is valid.  First call out to the common
    	//validation method, and then perform creation specific checks
    	ActionErrors returnValue = performCommonTemplateValidation(request, templateForm);
    	
    	//verify the template id is 0
    	TemplateDataExtended template = templateForm.getTemplateData();
    	int templateId = template.getTemplateId();
    	if (templateId != 0) {
	        String errorMessage = "Invalid template id (" + templateId + ") encountered during create.";
	        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage));
    	}

	    //verify there isn't already a template for this store with the specified name and locale, if 
	    //all values have been specified
    	int storeId = template.getBusEntityId();
    	String templateName = template.getName();
    	String templateLocale = template.getProperty(Constants.TEMPLATE_EMAIL_PROPERTY_LOCALE).getValue();
	    if (storeId > 0 && Utility.isSet(templateName) && Utility.isSet(templateLocale)) {
	    	TemplateDataVector foundTemplates = TemplateUtilities.getAllEmailTemplatesForStore(storeId);
	    	Iterator<TemplateDataExtended> templateIterator = foundTemplates.iterator();
	    	boolean found = false;
	    	while (templateIterator.hasNext() && !found) {
	    		TemplateDataExtended foundTemplate = templateIterator.next();
	    		if (templateName.equalsIgnoreCase(foundTemplate.getName()) &&
	    				templateLocale.equalsIgnoreCase(foundTemplate.getProperty(Constants.TEMPLATE_EMAIL_PROPERTY_LOCALE).getValue())) {
			        String errorMessage = "A template for this store with the specified name and locale already exists.";
			        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage));
			        found = true;
	    		}
	    	}
	    }
	    
	    //if errors were found, return without attempting a creation of the template.
	    if (returnValue.size() > 0) {
	    	return returnValue;
	    }
	    
        //set the created by and modified by values.  The created by date, modified by date, and 
	    //template id values are all intentionally left blank, as they are automatically populated 
	    //by the code that performs the database work (TemplateDataAccess.insert())
        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
	    template.setAddBy(appUser.getUserName());
	    template.setModBy(appUser.getUserName());
	    	    
        //create the template and its properties
	    TemplateDataExtended result = new APIAccess().getTemplateAPI().createTemplate(template);
	    //update the form to hold the newly created template
	    templateForm.setTemplateData(result);

    	return returnValue;
    }
    
    /*
     * Method to update an Email template object
     * @param request - the HttpServletRequest
     * @param templateForm
     * @return
     * @throws Exception
     */
    private static ActionErrors modifyEmailTemplate(HttpServletRequest request, StoreTemplateForm templateForm) throws Exception {
    	//verify required data has been specified and is valid.  First call out to the common
    	//validation method, and then perform modification specific checks
    	ActionErrors returnValue = performCommonTemplateValidation(request, templateForm);
    	
    	//verify the template id is greater than 0
    	TemplateDataExtended template = templateForm.getTemplateData();
    	int templateId = template.getTemplateId();
    	if (templateId <= 0) {
	        String errorMessage = "Invalid template id (" + templateId + ") encountered during modify.";
	        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage));
    	}
    	
    	TemplateDataExtended existingTemplate = null;
    	
    	//verify the store id isn't being changed.
    	int storeId = template.getBusEntityId();
    	if (storeId > 0 && templateId > 0) {
        	StoreTemplateForm localForm = new StoreTemplateForm();
        	TemplateDataExtended localTemplate = new TemplateDataExtended();
        	localTemplate.setBusEntityId(storeId);
        	localTemplate.setTemplateId(templateId);
        	localForm.setTemplateData(localTemplate);
        	StoreTemplateMgrLogic.getEmailTemplate(localForm);
        	existingTemplate = localForm.getTemplateData();
        	if (storeId != existingTemplate.getBusEntityId()) {
    	        String errorMessage = "The store to which a template belongs cannot be altered.";
    	        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage));
        	}
    	}
    	
    	//if the name is being changed, make sure that is ok to do
    	if (existingTemplate != null && Utility.isSet(template.getName())
    			&& !existingTemplate.getName().equalsIgnoreCase(template.getName())) {
    		if (!StoreTemplateMgrLogic.isEmailTemplateRenamable(existingTemplate)) {
    	        StringBuilder errorMessage = new StringBuilder(150);
    	        errorMessage.append("This template has been specified for usage in email generation and cannot be renamed.");
    	        errorMessage.append("To rename the template you must first remove the specification of its usage for email generation.");
    	        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage.toString()));
    		}
    	}
    	
	    //verify there isn't already a template for this store with the specified name and locale, if 
	    //all values have been specified.  Because this is an update, ignore the found template if it
    	//has the same id as this one.
    	String templateName = template.getName();
    	String templateLocale = template.getProperty(Constants.TEMPLATE_EMAIL_PROPERTY_LOCALE).getValue();
	    if (storeId > 0 && Utility.isSet(templateName) && Utility.isSet(templateLocale)) {
	    	TemplateDataVector foundTemplates = TemplateUtilities.getAllEmailTemplatesForStore(storeId);
	    	Iterator<TemplateDataExtended> templateIterator = foundTemplates.iterator();
	    	boolean found = false;
	    	while (templateIterator.hasNext() && !found) {
	    		TemplateDataExtended foundTemplate = templateIterator.next();
	    		if (templateName.equalsIgnoreCase(foundTemplate.getName()) &&
	    				templateLocale.equalsIgnoreCase(foundTemplate.getProperty(Constants.TEMPLATE_EMAIL_PROPERTY_LOCALE).getValue()) &&
	    				templateId != foundTemplate.getTemplateId()) {
			        String errorMessage = "A template for this store with the specified name and locale already exists.";
			        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage));
			        found = true;
	    		}
	    	}
	    }
	    
	    //if errors were found, return without attempting a modification of the template.
	    if (returnValue.size() > 0) {
	    	return returnValue;
	    }
	    
        //set the modified by value.  The modified by date value is intentionally left blank, as it is
	    //automatically populated by the code that performs the database work (TemplateDataAccess.update())
        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
	    template.setModBy(appUser.getUserName());
	    
	    //set the add by/date values from the existing version of the template.  This is done so that
	    //the code actually performing the modification doesn't overwrite these values with blanks (it
	    //just uses whatever values are on the template passed to it).
	    template.setAddBy(existingTemplate.getAddBy());
	    template.setAddDate(existingTemplate.getAddDate());
	    
	    //set the modified by value on the properties
	    	    
        //modify the template and its properties
	    TemplateDataExtended result = new APIAccess().getTemplateAPI().modifyTemplate(template);
	    templateForm.setTemplateData(result);

    	return returnValue;
    }
    
	/**
	 * Method to clone an Email template object
     * @param templateForm - the Struts action form
	 * @return - a collection of Struts <code>ActionError</code> objects
	 * @throws Exception
	 */
	public static ActionErrors previewEmailTemplate(HttpServletRequest request, StoreTemplateForm templateForm) throws Exception {
		ActionErrors returnValue = new ActionErrors();
		
    	//verify a valid order number/id has been specified.
    	String orderIdString = templateForm.getId();
		OrderData order = null;
    	if (Utility.isSet(orderIdString)) {
    		Order orderBean = APIAccess.getAPIAccess().getOrderAPI();
    		try {
    			//first try to find the order assuming the user entered an order id
    			order = orderBean.getOrder(new BigInteger(orderIdString).intValue());
    		}
    		catch (Exception e) {
    			//if we couldn't find the order by assuming the user entered an order id, try to 
    			//find it assuming the user entered an order number
    			OrderStatusCriteriaData orderStatusCriteria = new OrderStatusCriteriaData();
    			orderStatusCriteria.setWebOrderConfirmationNum(orderIdString);
    		    try {
    		        OrderDataVector orderDV = orderBean.getOrderStatusCollection(orderStatusCriteria);
    		        if (orderDV != null && orderDV.size() >= 1) {
    		        	order = (OrderData) orderDV.get(0);
    		        }
    		    } 
    		    catch (Exception exc) {
    		    }
    		}
    	}
    	
    	//if we couldn't find the order, return a message
        String invalidOrderErrorMessage = "A valid order number/id must be specified to show output.";
    	if (order == null) {
	        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", invalidOrderErrorMessage));
    	}
    	
    	//if we found the order but the user should not have access to it, return a message
    	if (order != null) {
            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            if(appUser == null){
            	returnValue.add(ERROR_PROPERTY, new ActionError("simple.generic.error", "Could not find user in the session."));
            }
            else {
                boolean unauthorizedAccess = false;
                if (appUser.isaCustomer() || appUser.isaMSB()) {
                    if(!APIAccess.getAPIAccess().getUserAPI().isSiteOfUser(order.getSiteId(),
                    		appUser.getUser().getUserId())) {
                    	unauthorizedAccess = true;
                    }
                }
                IdVector stores = appUser.getUserStoreAsIdVector();
                if (!stores.contains(new Integer(order.getStoreId()))){
                	unauthorizedAccess = true;
                }
                if (unauthorizedAccess){
                    returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", invalidOrderErrorMessage));
                }
            }
    	}
    	
    	//verify a locale has been specified.
    	TemplateDataExtended template = templateForm.getTemplateData();
    	String localeValue = template.getProperty(Constants.TEMPLATE_EMAIL_PROPERTY_LOCALE).getValue();
    	if (!Utility.isSet(localeValue)) {
	        String errorMessage = "A locale must be specified to show output.";
	        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage));
    	}
		
    	//return any errors
    	if (returnValue.size() > 0 ){
    		return returnValue;
    	}

    	//retrieve the data objects required for template output
    	Map templateObjects = TemplateUtilities.getDefaultOrderEmailObjectMap(order.getOrderId());
    	
    	//to allow for formatted currency values, set the locale of the order object to the locale passed in
    	OrderDto orderDto = (OrderDto)templateObjects.get(Constants.TEMPLATE_EMAIL_MAP_KEY_ORDER);
    	orderDto.setUserLocale(localeValue);
    	Iterator<OrderItemDto> itemIterator = orderDto.getItems().iterator();
    	while (itemIterator.hasNext()) {
    		itemIterator.next().setUserLocale(localeValue);
    	}
    	
    	String subject = null;
    	try {
    		subject = TemplateUtilities.generateTemplateOutput(templateObjects, 
    			template.getProperty(Constants.TEMPLATE_EMAIL_PROPERTY_SUBJECT).getValue(),
    			localeValue);
    	}
    	catch (Exception e) {
    		StringBuilder msg = new StringBuilder(100);
    		msg.append(e.getClass().getName());
    		msg.append(" occurred when generating subject for template.  Exception text: ");
    		msg.append(e.getMessage());
    		subject = msg.toString();
    	}
    	String message = null;
    	try {
    		message = TemplateUtilities.generateTemplateOutput(templateObjects, 
    			template.getContent(), localeValue);
    	}
    	catch (Exception e) {
    		StringBuilder msg = new StringBuilder(100);
    		msg.append(e.getClass().getName());
    		msg.append(" occurred when generating message for template.  Exception text: ");
    		msg.append(e.getMessage());
    		message = msg.toString();
    	}
    	Map output = templateForm.getTemplateOutput();
    	output.put(Constants.TEMPLATE_OUTPUT_EMAIL_SUBJECT, subject);
    	output.put(Constants.TEMPLATE_OUTPUT_EMAIL_BODY, message);
    	return returnValue;
	}
	
    /*
     * Method to perform common template validation
     * @param request - the HttpServletRequest
     * @param templateForm - the Struts action form
	 * @return - a collection of Struts <code>ActionError</code> objects
     * @throws Exception
     */
    private static ActionErrors performCommonTemplateValidation(HttpServletRequest request, StoreTemplateForm templateForm) throws Exception {
    	ActionErrors returnValue = new ActionErrors();
    	TemplateDataExtended template = templateForm.getTemplateData();
    	//store id - must be specified and be valid
	    int storeId = template.getBusEntityId();
	    if (storeId <= 0) {
	        String errorMessage = "An invalid store id (" + storeId + ") was specified for the template.";
	        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage));
	    }
	    else {
	    	StoreData store = new APIAccess().getStoreAPI().getStore(storeId);
	    	if (store == null || store.getStoreId() == 0) {
		        String errorMessage = "An invalid store id (" + storeId + ") was specified for the template.";
		        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage));
	    	}
	    }
	    //template name - must be specified and under a certain size
	    String templateName = template.getName();
	    if (!Utility.isSet(templateName)) {
	        returnValue.add(ERROR_PROPERTY, new ActionError("variable.empty.error", "Template Name"));
	    }
	    else {
		    template.setName(template.getName().trim());
		    templateName = template.getName();
	    	if (templateName.length() > Constants.TEMPLATE_MAX_SIZE_NAME) {
		        returnValue.add(ERROR_PROPERTY, new ActionError("variable.to.large.error", "Template Name"));
	    	}
	    }
	    //template type - must be specified and be a valid value
	    String templateType = template.getType();
	    if (!Utility.isSet(templateType)) {
	        String errorMessage = "No type was specified for the template.";
	        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage));
	    }
	    else {
	    	if (!Constants.TEMPLATE_TYPE_EMAIL.equals(templateType)) {
		        String errorMessage = "An invalid type (" + templateType + ") was specified for the template.";
		        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage));
	    	}
	    }
	    //template content - must be specified
	    String templateContent = template.getContent();
	    if (!Utility.isSet(templateContent)) {
	        returnValue.add(ERROR_PROPERTY, new ActionError("variable.empty.error", "Email Body"));
	    }
	    //template locale - must be specified and be a valid value
	    String templateLocale = template.getProperty(Constants.TEMPLATE_EMAIL_PROPERTY_LOCALE).getValue();
	    if (!Utility.isSet(templateLocale)) {
	        returnValue.add(ERROR_PROPERTY, new ActionError("variable.empty.error", "Locale/Language"));
	    }
	    else {
		    RefCdDataVector validLocales = (RefCdDataVector) request.getSession().getAttribute(Constants.ATTRIBUTE_TEMPLATE_LOCALES_VECTOR);
		    if (validLocales != null) {
		    	boolean found = false;
		    	Iterator<RefCdData> validLocalesIterator = validLocales.iterator();
		    	while (validLocalesIterator.hasNext() && !found) {
		    		String localeCode = validLocalesIterator.next().getValue();
		    		found = (templateLocale.equalsIgnoreCase(localeCode));
		    	}
		    	if (!found) {
			        String errorMessage = "An invalid Locale/Language (" + templateLocale + ") was specified for the template.";
			        returnValue.add(ERROR_PROPERTY, new ActionError("error.simpleGenericError", errorMessage));
		    	}
		    }

	    }
	    //template subject - optional, but must be under a certain size
	    String templateSubject = template.getProperty(Constants.TEMPLATE_EMAIL_PROPERTY_SUBJECT).getValue();
	    if (templateSubject.length() > Constants.TEMPLATE_MAX_SIZE_EMAIL_SUBJECT) {
	        returnValue.add(ERROR_PROPERTY, new ActionError("variable.to.large.error", "Email Subject"));
	    }
    	return returnValue;
    }

    /*
     * Method to determine if a template can be renamed.
     * @param template - a <code>TemplateData</code> object representing an email template
     * @return boolean - true if the template can be renamed, false otherwise.  Because a rename
     * is the same as a deletion followed by a creation, see if the template can be removed.
     * @throws Exception
     */
    private static boolean isEmailTemplateRenamable(TemplateData template) throws Exception {
    	return canTemplateBeRemoved(template);
    }

    /*
     * Method to determine if a template can be deleted.
     * @param template - a <code>TemplateData</code> object representing an email template
     * @return boolean - true if the template can be deleted, false otherwise.
     * @throws Exception
     */
    private static boolean isEmailTemplateDeletable(TemplateData template) throws Exception {
    	return canTemplateBeRemoved(template);
    }
    
    /*
     * Method to determine if a template can be removed
     * @param template - a <code>TemplateData</code> object representing an email template
     * @return boolean - true if the template can be removed, false otherwise.  If the template is
     * a system template it cannot be removed.  If the template is not a system template, then if it
     * is the only one with the original name and has been specified for usage in any email 
     * generation event for a store or account it cannot be removed.
     * @throws Exception
     */
    private static boolean canTemplateBeRemoved(TemplateData template) throws Exception {
    	boolean returnValue = true;
    	//first, see if the template is a system template (has no store id)
    	if (template.getBusEntityId() <= 0) {
    		returnValue = false;
    		return returnValue;
    	}
    	//next, see if this template is the only one with the original name
  	  	TemplateDataVector existingTemplates = TemplateUtilities.getAllEmailTemplatesForStore(template.getBusEntityId());
		String originalTemplateName = null;
    	boolean isOnlyInstance = false;
    	boolean isUsedByStore = false;
    	boolean isUsedByAccount = false;
    	if (existingTemplates != null) {
    		Map<String, Integer> nameToCountMap = new HashMap<String, Integer>();
    		Iterator<TemplateData> existingTemplateIterator = existingTemplates.iterator();
    		while (existingTemplateIterator.hasNext()) {
    			TemplateData existingTemplate = existingTemplateIterator.next();
    			String templateName = existingTemplate.getName();
    			if (existingTemplate.getTemplateId() == template.getTemplateId()) {
    				originalTemplateName = templateName;
    			}
    			Integer count = nameToCountMap.get(templateName);
    			if (count == null) {
    				nameToCountMap.put(templateName, new Integer(1));
    			}
    			else {
    				nameToCountMap.put(templateName, new Integer(count.intValue() + 1));
    			}
    		}
    		if (Utility.isSet(originalTemplateName)) {
    			isOnlyInstance = nameToCountMap.get(originalTemplateName).intValue() == 1;
    		}
    	}
    	//if this is the only instance, see if it is has been specified for usage at either
    	//the store or account level
    	if (isOnlyInstance) {
    		StoreData store = new APIAccess().getStoreAPI().getStore(template.getBusEntityId());
    		String storeOrderConfirmationEmailTemplate = null;
    		String storeShippingNotificationEmailTemplate = null;
    		String storePendingApprovalEmailTemplate = null;
    		String storeRejectedOrderEmailTemplate = null;
    		String storeModifiedOrderEmailTemplate = null;
    	    PropertyDataVector miscPropV = store.getMiscProperties();
    	    for (int ii = 0; ii < miscPropV.size(); ii++) {
    	    	PropertyData pD = (PropertyData) miscPropV.get(ii);
    	        String propType = pD.getPropertyTypeCd();
    	      	if (RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE.equals(propType)) {
    	      		storeOrderConfirmationEmailTemplate = pD.getValue();
    	      	} 
    	      	else if (RefCodeNames.PROPERTY_TYPE_CD.SHIPPING_NOTIFICATION_EMAIL_TEMPLATE.equals(propType)) {
    	      		storeShippingNotificationEmailTemplate = pD.getValue();
    	      	} 
    	      	else if (RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE.equals(propType)) {
    	      		storePendingApprovalEmailTemplate = pD.getValue();
    	      	} 
    	      	else if (RefCodeNames.PROPERTY_TYPE_CD.REJECTED_ORDER_EMAIL_TEMPLATE.equals(propType)) {
    	      		storeRejectedOrderEmailTemplate = pD.getValue();
    	      	} 
    	      	else if (RefCodeNames.PROPERTY_TYPE_CD.MODIFIED_ORDER_EMAIL_TEMPLATE.equals(propType)) {
    	      		storeModifiedOrderEmailTemplate = pD.getValue();
    	      	}
    	    }
    	    isUsedByStore = (Utility.isSet(storeOrderConfirmationEmailTemplate) &&
    	    		storeOrderConfirmationEmailTemplate.equalsIgnoreCase(originalTemplateName)) ||
    	    		(Utility.isSet(storeShippingNotificationEmailTemplate) &&
    	    				storeShippingNotificationEmailTemplate.equalsIgnoreCase(originalTemplateName)) ||
    	    		(Utility.isSet(storePendingApprovalEmailTemplate) && 
    	    				storePendingApprovalEmailTemplate.equalsIgnoreCase(originalTemplateName)) ||
    	    		(Utility.isSet(storeRejectedOrderEmailTemplate) && 
    	    				storeRejectedOrderEmailTemplate.equalsIgnoreCase(originalTemplateName)) ||
    				(Utility.isSet(storeModifiedOrderEmailTemplate) &&
    						storeModifiedOrderEmailTemplate.equalsIgnoreCase(originalTemplateName));
    	    if (!isUsedByStore) {
    	    	Account accountBean = new APIAccess().getAccountAPI();
    	    	IdVector accountIds = (IdVector) accountBean.getAccountsForStore(template.getBusEntityId());
    	    	List propertyTypes = new ArrayList();
    	    	propertyTypes.add(RefCodeNames.PROPERTY_TYPE_CD.EXTRA);
    	    	List shortDescriptions = new ArrayList();
    	    	shortDescriptions.add(RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE);
    	    	shortDescriptions.add(RefCodeNames.PROPERTY_TYPE_CD.SHIPPING_NOTIFICATION_EMAIL_TEMPLATE);
    	    	shortDescriptions.add(RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE);
    	    	shortDescriptions.add(RefCodeNames.PROPERTY_TYPE_CD.REJECTED_ORDER_EMAIL_TEMPLATE);
    	    	shortDescriptions.add(RefCodeNames.PROPERTY_TYPE_CD.MODIFIED_ORDER_EMAIL_TEMPLATE);
    	    	Map<Integer, List> accountToProperties = accountBean.getPropertiesForAccounts(accountIds, shortDescriptions, propertyTypes, true);
    	    	Iterator<Integer> accountIterator = accountToProperties.keySet().iterator();
    	    	while (accountIterator.hasNext() && !isUsedByAccount) {
    	    		Integer accountId = accountIterator.next();
    	    		List properties = accountToProperties.get(accountId);
    	    		if (properties != null) {
    	    			Iterator<PropertyData> propertyIterator = properties.iterator();
    	    			while (propertyIterator.hasNext() && !isUsedByAccount) {
    	    				PropertyData property = propertyIterator.next();
    	    				isUsedByAccount = originalTemplateName.equalsIgnoreCase(property.getValue());
    	    			}
    	    		}
    	    	}
    	    }
    	}
    	//this template can be removed if it isn't the only instance or it isn't used by either the store or an account
    	returnValue = !isOnlyInstance || (!isUsedByStore && !isUsedByAccount);
    	return returnValue;
    }
}
