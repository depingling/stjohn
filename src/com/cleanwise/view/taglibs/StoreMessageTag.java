/*
 * StoreMessageTag.java
 *
 * Created on January 19, 2005, 8:37 PM
 */

package com.cleanwise.view.taglibs;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.bean.MessageTag;
import org.apache.struts.util.MessageResources;

import com.cleanwise.view.i18n.ClwMessageResourcesImpl;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

/**
 * a version of the message tag that will take into account store customization
 * @author bstevens
 */
public class StoreMessageTag extends MessageTag{

    /**
     * Holds value of property storeId.
     */
    private String arg0TypeCd;
    private String arg1TypeCd;
    private String arg2TypeCd;
    private String arg3TypeCd;
    private String arg4TypeCd;


    public String getArg0TypeCd() {
      return arg0TypeCd;
    }
    public void setArg0TypeCd(String v) {
      arg0TypeCd = v;
    }

    public String getArg1TypeCd() {
      return arg1TypeCd;
    }
    public void setArg1TypeCd(String v) {
      arg1TypeCd = v;
    }

    public String getArg2TypeCd() {
      return arg2TypeCd;
    }
    public void setArg2TypeCd(String v) {
      arg2TypeCd = v;
    }

    public String getArg3TypeCd() {
      return arg3TypeCd;
    }
    public void setArg3TypeCd(String v) {
      arg3TypeCd = v;
    }

    public String getArg4TypeCd() {
      return arg4TypeCd;
    }
    public void setArg4TypeCd(String v) {
      arg4TypeCd = v;
    }

    /**
     * Process the start tag.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
    	
        ServletRequest request = pageContext.getRequest();
        HttpSession session = pageContext.getSession();
        TagUtils tagUtils = TagUtils.getInstance();
        Locale userLocale = null;

		CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
		//Here we try to retrieve store prefix locale.
		if (appUser != null) {
		      userLocale = appUser.getStorePrefixLocale();
		}

	    if (userLocale == null) {
	    	userLocale = tagUtils.getUserLocale(pageContext,getLocale());
	    }

	    if (getKey() == null) {
	            throw new JspException("Key was null");
	    }
	
	    // Retrieve the message string we are looking for
	    MessageResources messages = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
	    if(messages instanceof ClwMessageResourcesImpl){
	        ClwMessageResourcesImpl clwMessages = (ClwMessageResourcesImpl)messages;
	            //STJ-5359
	        if(arg0 != null && arg0TypeCd != null && arg0TypeCd.equalsIgnoreCase("STRING")){
	            arg0TypeCd = "MESSAGE_KEY";
	            arg0 = Constants.ORDER_PROPERTY_KEY + arg0;
	        }
	        Object args[] = new Object[] { arg0, arg1, arg2, arg3, arg4 };
	        String argTypes[] = new String[] {arg0TypeCd, arg1TypeCd, arg2TypeCd, arg3TypeCd, arg4TypeCd};
	        // call to getMessage method will try to look for the key in the user's store/locale specific
	        // property file, if the key is not found then the default file Message_All_en.txt file will
	        // be looked up for the key.
	        String message = clwMessages.getMessage(userLocale, getKey(), args, argTypes);
	
	        if(message == null) {
	            message = "@@@"+getKey();
	        }
	
	        TagUtils.getInstance().write(pageContext, message);
	        return (SKIP_BODY);
	        } else {
	            return super.doStartTag();
	        }
    }
}
