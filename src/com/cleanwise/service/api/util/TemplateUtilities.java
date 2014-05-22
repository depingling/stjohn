package com.cleanwise.service.api.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dto.template.OrderDto;
import com.cleanwise.service.api.dto.template.OrderItemDto;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.EntitySearchCriteria;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.TemplateDataExtended;
import com.cleanwise.service.api.value.TemplateDataVector;
import com.cleanwise.view.utils.Constants;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public final class TemplateUtilities {

	private static final Logger log = Logger.getLogger(TemplateUtilities.class);
	private static Configuration _emailConfiguration = new Configuration();
	
	//static initialization block to perform some initialization required by FreeMarker
	static {
		_emailConfiguration.setObjectWrapper(new DefaultObjectWrapper());
	}
	
	/*
	 * Method to retrieve the FreeMarker <code>Configuration</code> object.
	 * @return
	 */
	private static Configuration getEmailConfiguration() {
		return _emailConfiguration;
	}
    
    /**
     * Method to determine which email template should be utilized for a given email event
     * @param accountId - an integer containing the account id.
     * @param storeId - an integer containing the store id.
     * @param templateType - a <code>String</code> object containing the email event type.
     * @param locale - a <code>String</code> object containing the locale to try to match.
     * @return - the <code>TemplateDataExtended</code> object that should be used to generate
     * 		the email subject and body. 
     * @throws Exception
     */
    public static TemplateDataExtended getTemplateForEmailGeneration(int accountId, int storeId, 
    		String templateType, String locale) throws Exception {
        log.info("Determining template to use for email generation...");
    	TemplateDataExtended returnValue = null;
        APIAccess factory = new APIAccess();
    	//first see if we can find a matching template specified at the account level
        AccountData accountData = factory.getAccountAPI().getAccount(accountId, storeId);
        log.info("Checking account for template match...");
        if (isAccountHasEmailTemplateSpecified(accountData)) {
        	//if templates were specified at the account level, see if there was a template specified
        	//for the particular email event
        	String templateName = accountData.getPropertyValue(templateType);
        	if (Utility.isSet(templateName)) {
        		//if a template was specified for the particular email event then see if there is an
        		//instance of that template matching the specified locale (or is a default).
        		TemplateDataVector templates = TemplateUtilities.getAllEmailTemplatesForStore(storeId);
        	    returnValue = findTemplateMatchForLocale(templates, templateName, locale);
        	    //if so, return it
            	if (returnValue != null) {
                    log.info("A matching template was found for the account.  Template to use has id " + returnValue.getTemplateId() + ".");
            		return returnValue;
            	}
        	}
        }
    	//no matching template was found at the account level, so now see if we can find a matching
        //template at the store level
        StoreData storeData = factory.getStoreAPI().getStore(storeId);
        log.info("No match was found at the account level. Checking store for template match...");
        if (isStoreHasEmailTemplateSpecified(storeData)) {
        	//if templates were specified at the store level, see if there was a template specified
        	//for the particular email event
        	String templateName = null;
        	PropertyDataVector storeProperties = storeData.getMiscProperties();
            for (int i = 0; i < storeProperties.size(); i++) {
                PropertyData pD = (PropertyData) storeProperties.get(i);
                String propType = pD.getPropertyTypeCd();
                if (templateType.equals(propType)) {
                	templateName = pD.getValue();
                }
            }
        	if (Utility.isSet(templateName)) {
        		//if a template was specified for the particular email event then see if there is an
        		//instance of that template matching the specified locale (or is a default).
        		TemplateDataVector templates = TemplateUtilities.getAllEmailTemplatesForStore(storeId);
        	    returnValue = findTemplateMatchForLocale(templates, templateName, locale);
        	    //if so, return it
            	if (returnValue != null) {
                    log.info("A matching template was found for the store.  Template to use has id " + returnValue.getTemplateId() + ".");
            		return returnValue;
            	}
        	}
        }        	
        //No matching template was found at either the account or store level, so use the system templates
        //for the particular email event
        log.info("No match was found at the store level.  Checking system templates for template match...");
	    TemplateDataVector systemTemplates = TemplateUtilities.getAllSystemEmailTemplates();
	    String templateName = null;
	    //determine the name of the template we're looking for
	    if (templateType.equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE)) {
	    	templateName = Constants.TEMPLATE_EMAIL_SYSTEM_ORDER_CONFIRMATION_TEMPLATE_NAME;
	    }
	    else if (templateType.equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.SHIPPING_NOTIFICATION_EMAIL_TEMPLATE)) {
	    	templateName = Constants.TEMPLATE_EMAIL_SYSTEM_SHIPPING_NOTIFICATION_TEMPLATE_NAME;
	    }
	    else if (templateType.equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE)) {
	    	templateName = Constants.TEMPLATE_EMAIL_SYSTEM_PENDING_APPROVAL_TEMPLATE_NAME;
	    }
	    else if (templateType.equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.REJECTED_ORDER_EMAIL_TEMPLATE)) {
	    	templateName = Constants.TEMPLATE_EMAIL_SYSTEM_REJECTED_ORDER_TEMPLATE_NAME;
	    }
	    else if (templateType.equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.MODIFIED_ORDER_EMAIL_TEMPLATE)) {
	    	templateName = Constants.TEMPLATE_EMAIL_SYSTEM_MODIFIED_ORDER_TEMPLATE_NAME;
	    }
	    returnValue = findTemplateMatchForLocale(systemTemplates, templateName, locale);
        log.info("A matching system template was found.  Template to use has id " + returnValue.getTemplateId() + ".");
    	return returnValue;
    }
    
    /*
     * Method to determine if an account has any email templates specified for any email event.
     * @param accountData - an <code>AccountData</code> object representing the account to be
     * 		examined.
     * @return boolean - true if the specified account has any email template specified for any
     * 		email event, false otherwise.
     */
    private static boolean isAccountHasEmailTemplateSpecified(AccountData accountData) {
    	boolean returnValue = false;
    	if (Utility.isSet(accountData.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE))) {
    		returnValue = true;
    	}
    	if (Utility.isSet(accountData.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHIPPING_NOTIFICATION_EMAIL_TEMPLATE))) {
    		returnValue = true;
    	}
    	if (Utility.isSet(accountData.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE))) {
    		returnValue = true;
    	}
    	if (Utility.isSet(accountData.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REJECTED_ORDER_EMAIL_TEMPLATE))) {
    		returnValue = true;
    	}
    	if (Utility.isSet(accountData.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.MODIFIED_ORDER_EMAIL_TEMPLATE))) {
    		returnValue = true;
    	}
    	return returnValue;
    }
    
    /*
     * Method to determine if a store has any email templates specified for any email event.
     * @param storeData - a <code>StoreData</code> object representing the store to be
     * 		examined.
     * @return boolean - true if the specified store has any email template specified for any
     * 		email event, false otherwise.
     */
    private static boolean isStoreHasEmailTemplateSpecified(StoreData storeData) {
    	boolean returnValue = false;
    	PropertyDataVector storeProperties = storeData.getMiscProperties();
        for (int i = 0; i < storeProperties.size(); i++) {
            PropertyData pD = (PropertyData) storeProperties.get(i);
            String propType = pD.getPropertyTypeCd();
            String propValue = pD.getValue();
            if (RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE.equals(propType)) {
            	if (Utility.isSet(propValue)) {
            		returnValue = true;
            	}
            }
            else if (RefCodeNames.PROPERTY_TYPE_CD.SHIPPING_NOTIFICATION_EMAIL_TEMPLATE.equals(propType)) {
            	if (Utility.isSet(propValue)) {
            		returnValue = true;
            	}
            }
            else if (RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE.equals(propType)) {
            	if (Utility.isSet(propValue)) {
            		returnValue = true;
            	}
            }
            else if (RefCodeNames.PROPERTY_TYPE_CD.REJECTED_ORDER_EMAIL_TEMPLATE.equals(propType)) {
            	if (Utility.isSet(propValue)) {
            		returnValue = true;
            	}
            }
            else if (RefCodeNames.PROPERTY_TYPE_CD.MODIFIED_ORDER_EMAIL_TEMPLATE.equals(propType)) {
            	if (Utility.isSet(propValue)) {
            		returnValue = true;
            	}
            }
        }
    	return returnValue;
    }
    
    /**
     * Method to retrieve all of the Email templates defined for a given store.
     * @param storeId - an integer containing the id of the store for which templates are to be
     * 		retrieved.
     * @return - a <code>TemplateDataVector</code> object containing the email templates that have
     * 		been created for the specified store.
     * @throws Exception
     */
    public static TemplateDataVector getAllEmailTemplatesForStore(int storeId) throws Exception {
        EntitySearchCriteria searchCriteria = new EntitySearchCriteria();
        IdVector idVector = new IdVector();
        idVector.add(storeId);
        searchCriteria.setStoreBusEntityIds(idVector);
	    TemplateDataVector templateVector = new APIAccess().getTemplateAPI().getTemplateByCriteria(searchCriteria);
    	return templateVector;
    }
    
    /**
     * Method to retrieve all system defined Email templates
     * @return - a <code>TemplateDataVector</code> object containing the system email templates.
     * @throws Exception
     */
    public static TemplateDataVector getAllSystemEmailTemplates() throws Exception {
    	return new APIAccess().getTemplateAPI().getSystemDefaultTemplates();
    }
    
    /**
     * @param templateName - a <code>String</code> containing the name of the template to retrieve
     * @return - a <code>TemplateDataExtended</code> object containing the data for the requested 
     * 	template name
     * @throws Exception
     */
    public static TemplateDataExtended getSystemEmailTemplate(String templateName, String locale) throws Exception {
    	TemplateDataExtended returnValue = null;
    	if (!Utility.isSet(templateName) || !Utility.isSet(locale)) {
    		return returnValue;
    	}
    	else {
    		templateName = templateName.trim();
    		locale = locale.trim();
    	}
    	TemplateDataVector templates = TemplateUtilities.getAllSystemEmailTemplates();
    	Iterator<TemplateDataExtended> iterator = templates.iterator();
    	while (iterator.hasNext() && returnValue == null) {
    		TemplateDataExtended template = iterator.next();
    		if (templateName.equalsIgnoreCase(template.getName()) &&
    				locale.equalsIgnoreCase(template.getProperty(Constants.TEMPLATE_EMAIL_PROPERTY_LOCALE).getValue())) {
    			returnValue = template;
    		}
    	}
    	return returnValue;
    }
    
    public static String getSystemEmailTemplateName(String templateType) {
    	String returnValue = null;
    	if (Utility.isSet(templateType)) {
    		templateType = templateType.trim();
    		if (RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE.equalsIgnoreCase(templateType)) {
    			returnValue = Constants.TEMPLATE_EMAIL_SYSTEM_ORDER_CONFIRMATION_TEMPLATE_NAME;
    		}
    		if (RefCodeNames.PROPERTY_TYPE_CD.SHIPPING_NOTIFICATION_EMAIL_TEMPLATE.equalsIgnoreCase(templateType)) {
    			returnValue = Constants.TEMPLATE_EMAIL_SYSTEM_SHIPPING_NOTIFICATION_TEMPLATE_NAME;
    		}
    		if (RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE.equalsIgnoreCase(templateType)) {
    			returnValue = Constants.TEMPLATE_EMAIL_SYSTEM_PENDING_APPROVAL_TEMPLATE_NAME;
    		}
    		if (RefCodeNames.PROPERTY_TYPE_CD.REJECTED_ORDER_EMAIL_TEMPLATE.equalsIgnoreCase(templateType)) {
    			returnValue = Constants.TEMPLATE_EMAIL_SYSTEM_REJECTED_ORDER_TEMPLATE_NAME;
    		}
    		if (RefCodeNames.PROPERTY_TYPE_CD.MODIFIED_ORDER_EMAIL_TEMPLATE.equalsIgnoreCase(templateType)) {
    			returnValue = Constants.TEMPLATE_EMAIL_SYSTEM_MODIFIED_ORDER_TEMPLATE_NAME;
    		}
    	}
    	return returnValue;
    }
    
    /*
     * Method to find a template (if any) that matches a specified locale
     * @param templates - a <code>TemplateDataVector</code> containing the collection of templates
     * 		to examine
     * @param templateName - a <code>String</code> containing the name of the template we're
     * 		looking for.
     * @param locale - a <code>String</code> containing the locale we're trying to match.
     * @return - a <code>TemplateDataExtended</code> object matching the locale, if any.  An exact
     * 		match is given top priority.  If an exact match cannot be located, a language-only match
     * 		is given next priority.  If a language-only match cannot be located, a "default" template
     * 		is given priority.  If no default template can be located, null is returned.
     */
    private static TemplateDataExtended findTemplateMatchForLocale(TemplateDataVector templates, String templateName, String locale) {
    	TemplateDataExtended returnValue = null;
		TemplateDataExtended localeExactMatch = null;
		TemplateDataExtended localeLanguageMatch = null;
		TemplateDataExtended defaultMatch = null;
		Iterator<TemplateDataExtended> templateIterator = templates.iterator();
		while (templateIterator.hasNext()) {
			TemplateDataExtended template = templateIterator.next();
			if (templateName.equalsIgnoreCase(template.getName())) {
				String templateLocale = template.getProperty(Constants.TEMPLATE_EMAIL_PROPERTY_LOCALE).getValue();
				//see if we have an exact locale match
				if (locale != null && templateLocale != null &&
						locale.trim().equalsIgnoreCase(templateLocale.trim())) {
					localeExactMatch = template;
				}
				//see if we have a language match
				else if (locale != null && templateLocale != null &&
						locale.indexOf("_") > 0 &&
						locale.trim().substring(0, locale.indexOf("_")).equalsIgnoreCase(templateLocale.trim())) {
					localeLanguageMatch = template;
				}
				//otherwise see if we have a default match
				else if (Constants.TEMPLATE_EMAIL_PROPERTY_LOCALE_DEFAULT.equalsIgnoreCase(templateLocale)) {
					defaultMatch = template;
				}
			}
		}
		//if we have an exact match, return it
		if (localeExactMatch != null) {
	        log.info("A template with an exact locale match has been selected.");
			returnValue = localeExactMatch;
		}
		//otherwise if we have a language match, return it
		else if (localeLanguageMatch != null) {
	        log.info("A template with a language match has been selected.");
			returnValue = localeLanguageMatch;
		}
		//otherwise if we have a default match, return it
		else if (defaultMatch != null) {
	        log.info("A template with a default locale has been selected.");
			returnValue = defaultMatch;
		}
    	return returnValue;
    }
    
    /**
     * Method to retrieve the default object map to be passed to FreeMarker for the generation of
     * an email subject and body.
     * @param orderId - an integer containing the id of an order.
     * @param originalOrderNumber - a <code>String</code> containing the original order number for the
     * 		specified order.
     * @return - a <code>Map</code> of objects to be utilized by FreeMarker.
     * @throws Exception
     */
    public static Map getDefaultOrderEmailObjectMap(int orderId, String originalOrderNumber) throws Exception {
    	Map map = getDefaultOrderEmailObjectMap(orderId);
		((OrderDto)map.get(Constants.TEMPLATE_EMAIL_MAP_KEY_ORDER)).setOriginalOrderNumber(originalOrderNumber);
		return map;
    }
    
    /**
     * Method to retrieve the default object map to be passed to FreeMarker for the generation of
     * an email subject and body.
     * @param orderId - an integer containing the id of an order.
     * @return - a <code>Map</code> of objects to be utilized by FreeMarker.
     * @throws Exception
     */
    public static Map getDefaultOrderEmailObjectMap(int orderId) throws Exception {
    	Map parameters = new HashMap();
    	parameters.put(Constants.ORDER_ID, new BigDecimal(orderId));
    	Map templateObjects = new APIAccess().getTemplateAPI()
    		.getDefaultObjectMap(Constants.TEMPLATE_TYPE_EMAIL, parameters);
    	return templateObjects;
    }
	
	/**
	 * Method to generate template output
	 * @param objectMap - a <code>Map</code> of objects to be passed to FreeMarker
	 * @param templateString - <code>String</code> containing the template text FreeMarker is to
	 * 		use to generate output
	 * @param locale - a <code>String</code> containing the locale to use to generate the output
	 * @return - a <code>String</code> containing the FreeMarker output.
	 * @throws Exception
	 */
	public static String generateTemplateOutput(Map objectMap, String templateString, String locale) throws Exception {
		String returnValue = null;
		//bug 5280
		if (templateString == null) {
			templateString = "";
		}
    	Template subjectTemplate = new Template("",	new StringReader(templateString), getEmailConfiguration());
        Writer out = new StringWriter();
    	Environment environment = subjectTemplate.createProcessingEnvironment(objectMap, out);
    	//create a Locale object from the locale value
    	String language = null;
    	String country = null;
    	if (locale == null) {
    		locale = "";
    	}
    	int underscorePosition = locale.indexOf("_");
    	if (underscorePosition > 0) {
    		language = locale.substring(0, underscorePosition);
    		country = locale.substring(underscorePosition + 1);
    	}
    	else {
    		language = locale;
    		country = "";
    	}
    	Locale localeObject = new Locale(language, country);
    	environment.setLocale(localeObject);
    	environment.process();
        returnValue = out.toString();
        //bug 5280 - for some reason an empty value causes a null pointer exception when
        //the email is sent, so make sure the return value contains at least a space.
        if ("".equals(returnValue)) {
        	returnValue = " ";
        }
		return returnValue;
	}

	/**
	 * Method to generate the data (subject and message) for an email, utilizing email templates
	 * @param templateType - a <code>String</code> containing the templateType
	 * @param storeId - an int containing the store id
	 * @param accountId - an int containing the account id
	 * @param orderId - an int containing the order id
	 * @param recipientLocale - a <code>String</code> containing the locale of the email recipient
	 * @param defaultSubject - a <code>String</code> containing the subject to return in the event an
	 * 	error occurs when generating the subject.
	 * @param defaultMessage - a <code>String</code> containing the message to return in the event an
	 * 	error occurs when generating the message.
	 * @param localeToTemplateMap - a <code>Map</code> containing locale to template mappings.
	 * @param templateObjects - a <code>Map</code> containing data required by the template to generate
	 * 	its output.
	 * @return - a <code>Map</code> containing the email data (subject and message).
	 */
    public static Map<String, String> generateEmailData(String templateType, 
    		int storeId, int accountId, int orderId, String recipientLocale,
    		Map<String, TemplateDataExtended> localeToTemplateMap, Map templateObjects,
    		String defaultSubject, String defaultMessage) {
    	Map<String, String> returnValue = null;
		try {
			returnValue = TemplateUtilities.generateEmailData(templateType, storeId, accountId, orderId, recipientLocale,
					localeToTemplateMap, templateObjects);
	    } catch(Exception e) {
	    	//if an exception occurs using the template, try to use the system default template
	    	try {
	    		TemplateDataExtended systemTemplate = getSystemEmailTemplate(getSystemEmailTemplateName(templateType), 
	    				Constants.TEMPLATE_EMAIL_PROPERTY_LOCALE_DEFAULT);
				returnValue = TemplateUtilities.generateEmailData(systemTemplate, templateType, storeId, accountId, orderId, recipientLocale,
						localeToTemplateMap, templateObjects);
		        log.error("Error occurred utilizing template in TemplateUtilities.generateEmailData: " + e.getMessage() + ". System default template was used instead.");
	    	}
	    	catch (Exception e2) {
	        	//if an exception occurs when using the template, use the subject and message passed in.
	            returnValue = new HashMap<String, String>();
	        	returnValue.put(Constants.TEMPLATE_OUTPUT_EMAIL_SUBJECT, defaultSubject);
	        	returnValue.put(Constants.TEMPLATE_OUTPUT_EMAIL_BODY, defaultMessage);
		        log.error("Error occurred utilizing system template in TemplateUtilities.generateEmailData: " + e.getMessage() + ". Default generator was used instead.");
	    	}
	    }
	    return returnValue;
    }
    
	/**
	 * Method to generate the data (subject and message) for an email, utilizing email templates
	 * @param templateType - a <code>String</code> containing the templateType
	 * @param storeId - an int containing the store id
	 * @param accountId - an int containing the account id
	 * @param orderId - an int containing the order id
	 * @param recipientLocale - a <code>String</code> containing the locale of the email recipient
	 * @param localeToTemplateMap - a <code>Map</code> containing locale to template mappings.
	 * @param templateObjects - a <code>Map</code> containing data required by the template to generate
	 * 	its output.
	 * @return - a <code>Map</code> containing the email data (subject and message).
	 */
    public static Map<String, String> generateEmailData(String templateType, 
    		int storeId, int accountId,	int orderId, String recipientLocale,
    		Map<String, TemplateDataExtended> localeToTemplateMap, Map templateObjects) throws Exception {
    	return generateEmailData(null, templateType, storeId, accountId, orderId, recipientLocale,
    			localeToTemplateMap, templateObjects);
    }
	   
	/*
	 * Method to generate the data (subject and message) for an email, utilizing email templates
	 * @param templateData - a <code>TemplateDataExtended</code> containing the template that should be
	 * 	used to generate the output.  If specified, this template will be used to generate the output.  If 
	 * 	not specified this method will determine the template to use based on other input parameters.
	 * @param templateType - a <code>String</code> containing the templateType
	 * @param storeId - an int containing the store id
	 * @param accountId - an int containing the account id
	 * @param orderId - an int containing the order id
	 * @param recipientLocale - a <code>String</code> containing the locale of the email recipient
	 * @param localeToTemplateMap - a <code>Map</code> containing locale to template mappings.  If specified,
	 * 	this map will be examined first to see if the template for the specified recipientLocale has already
	 * 	been identified.  If not specified, or no template matching the locale has been identified, this
	 * 	method will search to find the correct template to use.
	 * @param templateObjects - a <code>Map</code> containing data required by the template to generate
	 * 	its output.  If not specified this method will retrieve the default object map/
	 * @return - a <code>Map</code> containing the email data (subject and message).
	 */
    private static Map<String, String> generateEmailData(TemplateDataExtended templateData, String templateType, 
    		int storeId, int accountId,	int orderId, String recipientLocale,
    		Map<String, TemplateDataExtended> localeToTemplateMap, Map templateObjects) throws Exception {
    	Map<String, String> returnValue = new HashMap<String, String>();
    	//if a template wasn't passed in, determine which one to use for output generation
        if (templateData == null) {
            if (localeToTemplateMap == null) {
            	templateData = TemplateUtilities.getTemplateForEmailGeneration(accountId, storeId, templateType, recipientLocale);
            }
            else {
    			//see if we've already located the specific template to use
    			templateData = localeToTemplateMap.get(recipientLocale);
    			//if not, go look it up and cache it
    			if (templateData == null) {
          	        templateData = TemplateUtilities.getTemplateForEmailGeneration(accountId, storeId, templateType, recipientLocale);
    				localeToTemplateMap.put(recipientLocale, templateData);
    			}
            }
        }
        //if an object map wasn't passed in, retrieve the default map
        if (templateObjects == null) {
        	templateObjects = TemplateUtilities.getDefaultOrderEmailObjectMap(orderId);
        }
        
    	//to allow for formatted currency values, set the locale of the applicable template objects to the 
        //recipient locale
    	OrderDto order = (OrderDto)templateObjects.get(Constants.TEMPLATE_EMAIL_MAP_KEY_ORDER);
    	order.setUserLocale(recipientLocale);
    	Iterator<OrderItemDto> itemIterator = order.getItems().iterator();
    	while (itemIterator.hasNext()) {
    		itemIterator.next().setUserLocale(recipientLocale);
    	}
        
    	//now that we have the template and data, generate the output.
        String subject = null;
        String message = null;
    	String component = null;
        try {
        	//The recipient locale is passed to the generation method because it will always
        	//be fully specified (i.e. en_US, fr_FR, etc) but the template locale may be language 
        	//only (i.e. en) or "Default".  If the template locale is fully specified it will match 
        	//the recipient locale anyway.
        	component = "subject";
        	subject = TemplateUtilities.generateTemplateOutput(templateObjects, 
    			templateData.getProperty(Constants.TEMPLATE_EMAIL_PROPERTY_SUBJECT).getValue(),
    			recipientLocale);
        	component = "message";
    		message = TemplateUtilities.generateTemplateOutput(templateObjects, 
    			templateData.getContent(), 
    			recipientLocale);
    	}
    	catch (Exception e) {
    		TemplateUtilities.sendTemplateExceptionEmail(e, component, templateData.getTemplateId(), storeId,
    				accountId, orderId);
    		throw e;
    	}
    	returnValue.put(Constants.TEMPLATE_OUTPUT_EMAIL_SUBJECT, subject);
    	returnValue.put(Constants.TEMPLATE_OUTPUT_EMAIL_BODY, message);
	    return returnValue;
    }
    
    private static void sendTemplateExceptionEmail(Exception e, String component, int templateId,
    		int storeId, int accountId, int orderId) {
		StringBuilder msg = new StringBuilder(100);
		msg.append(e.getClass().getName());
		msg.append(" occurred when generating ");
		msg.append(component);
		msg.append(" for template ");
		msg.append(templateId);
		msg.append(".  Exception text: ");
		msg.append(e.getMessage());
		log.error(msg.toString());
		StringBuilder subjectBuilder = new StringBuilder(100);
		subjectBuilder.append("Error generating email template ");
		subjectBuilder.append(templateId);
		subjectBuilder.append(" ");
		subjectBuilder.append(component);
		subjectBuilder.append(" (Timestamp: ");
		subjectBuilder.append((new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a")).format(new Date()));
		subjectBuilder.append(")");
		sendAdminEmail(orderId, accountId, storeId, subjectBuilder.toString(), msg.toString());
    }
    
    private static void sendAdminEmail(int pOrderId, int pAccountId, int pStoreId, 
    		String pSubject, String pMsg) {
        APIAccess factory = null;
        EmailClient emailClientEjb = null;
        try {
            factory = APIAccess.getAPIAccess();
            emailClientEjb = factory.getEmailClientAPI();
        } catch (Exception exc) {
            String mess = "TemplateUtilities.sendAdminEmail. No API access";
            log.error(mess);
            return;
        }
        try {
            String defEmailAddr = emailClientEjb.getDefaultEmailAddress();
			emailClientEjb.send(defEmailAddr, defEmailAddr, pSubject, pMsg, null, 0, 0);
        } catch (Exception exc) {
            String mess = "TemplateUtilities.sendAdminEmail: " + exc.getMessage();
            log.error(mess);
        }
    }
}
