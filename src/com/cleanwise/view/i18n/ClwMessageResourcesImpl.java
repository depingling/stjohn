/*
 * ClwMessageResourcesImpl.java
 *
 * Created on January 18, 2005, 2:21 PM
 */

package com.cleanwise.view.i18n;
import java.io.Serializable;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.PropertyMessageResources;

import com.cleanwise.service.api.util.I18nUtil;
import com.espendwise.service.api.util.MessageResource;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;

/**
 *
 * @author bstevens
 */
public class ClwMessageResourcesImpl extends PropertyMessageResources implements Serializable {

    /**
     * @param factory The MessageResourcesFactory that created us
     * @param config The configuration parameter for this MessageResources
     */
    public ClwMessageResourcesImpl(MessageResourcesFactory factory, String config,boolean returnNull) {
        super(factory, config, returnNull);
    }

    /*
     * Gets i18n Message for passed key.
     */
    public static String getMessage(HttpServletRequest request, String key) {
    	String message = "";
    	if(isKeySet(request,key)) {
    		message = getMessage(request,key,new Object[0]);
    	} else {
    		//If no key found return the following message.
    		message = "Error. No key";
    	}
    	
    	return message;
    }
    
    /*
     * Gets i18n Message for passed key with arguments array.
     */
    public static String getMessage(HttpServletRequest request, String key, Object[] args ) {
		MessageResources messages = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
		String message = null;
		if(messages!=null && messages instanceof ClwMessageResourcesImpl){
			CleanwiseUser user = ShopTool.getCurrentUser(request.getSession());
                        Locale userLocale = Constants.DEFAULT_LOCALE;
                        if (user != null) {
                            userLocale = user.getStorePrefixLocale();
                        }
			
			if(args==null) {
				args = new Object[0];
			}
			message = getMessage(userLocale, key, args, new String[0]);

			if (message == null && !Constants.DEFAULT_LOCALE.equals(userLocale)) {
				message = getMessage(Constants.DEFAULT_LOCALE, key, args, new String[0]);
			}

		}
		
		return message;
	}
    
   /* public static String getMessage(String storePrefix, String key, Object[] args) {
    	return getMessage(storePrefix, Constants.DEFAULT_LOCALE, key, args);
    }*/
    
    /*public static String getMessage(Locale locale, String key, Object[] args) {
        return getMessage(storePrefix, locale, key, args, new String[0]);
    }*/
    
    /**
     *Gets a locale specific message for the store
     */
    public static String getMessage(Locale locale, String key, Object[] args, String[] types) {
    	return I18nUtil.getMessage(locale, key, args,types);
	}
    
    /*
     * Determines whether Message Resources are loaded and passed key is set or not.
     */
    public static boolean isKeySet(HttpServletRequest request, String key) {
    	boolean keyFound = false;
    	if(Utility.isSet(key)) {
    		try {
    			MessageResources messages = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    			 if(messages instanceof ClwMessageResourcesImpl){
    				 keyFound = true;
    			 }
    		}catch (Exception e) {
    		}
    		
    	}
    	return keyFound;
    }
    
    /**
     *  This method will be called from systemHome.jsp to Flush Message Resources
     *
     */
    public static void requestReload(HttpServletRequest request){
    	String messageResourceDirectoryPath = 
        		request.getSession().getServletContext().getRealPath("/")+""+System.getProperty("messageResourceDirectory");
        		
    	String fileNamePrefix = (System.getProperty("messageResourceFilePrefix") != null) 
        		? System.getProperty("messageResourceFilePrefix").trim() : "Message_All";
        		
    	String fileExtension = (System.getProperty("messageResourceFileExt") != null) 
        		? System.getProperty("messageResourceFileExt").trim() : ".txt";
        		
    	boolean readTextFilesOnly = true;
    	MessageResource.loadMessages(messageResourceDirectoryPath, fileNamePrefix, fileExtension);
    }

}
