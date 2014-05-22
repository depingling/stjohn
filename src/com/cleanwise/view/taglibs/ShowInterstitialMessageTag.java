/**
 * Title: ShowInterstitialMessageTag
 * Description: This is a tag designed to show an interstitial message.
 * NOTE: This tag depends on the inclusion of scripts.js in the page on which it
 * 		 is used, as it generates output which includes calls to javascript
 * 		 methods defined in that file.
 */

package com.cleanwise.view.taglibs;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.StoreMessageView;
import com.cleanwise.view.utils.Constants;

public class ShowInterstitialMessageTag extends TagSupport{
	
	private static final long serialVersionUID = -6949423515581911075L;
	private StoreMessageView _message;
	private String _action;
	private String _operation;
	private String _anchorCssStyle;
	private String _anchorTitle;

	/**
	 * @return the message
	 */
	public StoreMessageView getMessage() {
		return _message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(StoreMessageView message) {
		_message = message;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return _action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		_action = action;
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return _operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		_operation = operation;
	}

	/**
	 * @return the anchorCssStyle
	 */
	public String getAnchorCssStyle() {
		return _anchorCssStyle;
	}

	/**
	 * @param anchorCssStyle the anchorCssStyle to set
	 */
	public void setAnchorCssStyle(String anchorCssStyle) {
		_anchorCssStyle = anchorCssStyle;
	}

	/**
	 * @return the anchorTitle
	 */
	public String getAnchorTitle() {
		return _anchorTitle;
	}

	/**
	 * @param anchorTitle the anchorTitle to set
	 */
	public void setAnchorTitle(String anchorTitle) {
		_anchorTitle = anchorTitle;
	}

	/**
     * Handle any specified interstitial message.
     */
    public int doStartTag() throws JspException {
    	StoreMessageView message = getMessage();
    	boolean forcedRead = message.getMessageType().equals(RefCodeNames.MESSAGE_TYPE_CD.FORCE_READ) || 
    			message.getMessageType().equals(RefCodeNames.MESSAGE_TYPE_CD.ACKNOWLEDGEMENT_REQUIRED);
    	
        if (message != null && forcedRead) {
        	JspWriter out = pageContext.getOut();
        	try {
        		//call the function in scripts.js to place a cover layer over the main page
		        out.println("<script>");
		        out.println("    showCoverLayer();");
	        	out.println("</script>");
	        	//create an anchor to show the message
		        out.print("<a href=\"");
		        out.print(buildHref(message));
		        out.print("\" id=\"interstitialMessageLink\" title=\"");
		        out.print(getAnchorTitle());
		        out.print("\"");
		        if (Utility.isSet(getAnchorCssStyle())) {
			        out.print("class=\"");
			        out.print(getAnchorCssStyle());
			        out.print("\"");
		        }
		        out.println("/>");
		        //create a script to "click" the anchor we just created after the window is
		        //loaded.
		        //NOTE - this tag assumes that there is no existing onload event for the
		        //window, which may or may not be true.  If this tag needs to be used on a
		        //page with an existing onload event, then it will need to be changed so it
		        //adds an event handler to any already existing ones.  See the following link for 
		        //changes that will need to be made (and then TESTED on ALL browsers we support).
		        //http://onlinetools.org/articles/unobtrusivejavascript/chapter4.html
		        out.println("<script>");
		        out.println("function showInterstitialMessage() {");
	        	out.println("    actuateLink(document.getElementById('interstitialMessageLink'));");
	        	out.println("}");
	        	out.println("window.onload=showInterstitialMessage;");
	        	out.println("</script>");
		    } 
		    catch (IOException e) {
		    	throw new JspException(e);
		    }
        }
        return (SKIP_BODY);
    }
    
    /*
     * Build the url for displaying the interstitial message
     */
    private String buildHref(StoreMessageView message) {
    	StringBuilder builder = new StringBuilder(100);
    	builder.append(pageContext.getSession().getAttribute("pages.root"));
    	builder.append(getAction());
    	if (getAction().indexOf(".do") < 0) {
    		builder.append(".do");
    	}
    	boolean forcedRead = message.getMessageType().equals(RefCodeNames.MESSAGE_TYPE_CD.FORCE_READ) || 
    			message.getMessageType().equals(RefCodeNames.MESSAGE_TYPE_CD.ACKNOWLEDGEMENT_REQUIRED);
    	
    	builder.append(getParameterAppender(builder.toString()));
    	builder.append(Constants.PARAMETER_OPERATION);
    	builder.append("=");
    	builder.append(getOperation());
    	builder.append(getParameterAppender(builder.toString()));
    	builder.append(Constants.PARAMETER_CURRENT_MESSAGE_MESSAGE_ID);
    	builder.append("=");
    	builder.append(message.getStoreMessageId());
    	builder.append(getParameterAppender(builder.toString()));
    	builder.append(Constants.PARAMETER_CURRENT_MESSAGE_FORCED_READ);
    	builder.append("=");
    	builder.append(forcedRead);
    	if (message.getStoreMessageId() < 0){
    		builder.append(getParameterAppender(builder.toString()));
    		builder.append(Constants.PARAMETER_CURRENT_MESSAGE_MESSAGE_BODY);
        	builder.append("=");    	
        	builder.append(message.getMessageBody());        	
    	}
    		
    	return builder.toString();
    }
    
    private String getParameterAppender(String url) {
    	if (url.indexOf("?") > 0) {
    		return "&";	
    	}
    	else {
    		return "?";
    	}
    }
    
    /**
     * Release all allocated resources.
     */
    public void release() {
        super.release();
        _message = null;
        _action = null;
        _operation = null;
        _anchorCssStyle = null;
    }

}
