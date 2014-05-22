/**
 *  Title: DebugTag Description: This is the tag class which provides debug
 *  information for a page. Purpose: Copyright: Copyright (c) 2001 Company:
 *  CleanWise, Inc.
 *
 *@author     durval
 */

package com.cleanwise.view.taglibs;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.SessionTool;

/**
 *  This tag provides Customized information for a page.  example
 *  usage:
 *
 *  <br>Get the logo configured for this account or store.
 *  <br><app:custom pageElement="pages.logo2.image" addImagePath="true"/>
 *  <br>Get the tips message configured for this account or store.
 *  <br><app:custom pageElement="pages.tips.text"/>
 *
 *
 *@author     durval
 *@created    September 12, 2001
 */
public final class CustomizeTag extends TagSupport {

	private static final long serialVersionUID = 1234034990589755783L;
	String mPageElement = "";
    boolean mAddImagePath = false;
    boolean mEncodeForHTML = false;
    String mImageSubdirectory = "";

    /**
     * <code>setAddImagePath</code>, by setting this value
     * the caller can control whether or not to prepend the
     * full image path for the element being fetched.
     *
     * @param v a <code>boolean</code> value, true =
     * prepend the image path to the string returned
     * by getPageElement.
     * false = do not prepend the image path to the string
     * returned by getPageElement.
     */
    public void setAddImagePath(boolean v) {
        mAddImagePath = v;
    }

    /**
     * Describe <code>getAddImagePath</code> method here.
     *
     * @return a <code>boolean</code> value
     */
    public boolean getAddImagePath() {
        return mAddImagePath;
    }
    
    /**
     * <code>setEncodeForHTML</code>, by setting this value
     * the caller can control converting plaing text to HTML.
     * 
     * @param v a <code>boolean</code> value, true =
     * stored value will be converted to HTML, using utility method.
     * false = wouldn't converted to HTML (will be shown as is).
     * 
     */
    public void setEncodeForHTML(boolean v) {
        mEncodeForHTML = v;
    }
    
    public boolean isEncodeForHTML() {
        return mEncodeForHTML;
    }

    /**
	 * @return the imageSubdirectory
	 */
	public String getImageSubdirectory() {
		return mImageSubdirectory;
	}

	/**
	 * @param imageSubdirectory the imageSubdirectory to set
	 */
	public void setImageSubdirectory(String imageSubdirectory) {
		mImageSubdirectory = imageSubdirectory;
	}

	/**
     *  Sets the PageElement attribute of the CustomizeTag object
     *
     *@param  v  The new PageElement value
     */
    public void setPageElement(String v) {
        mPageElement = v;
    }


    /**
     *  Gets the PageElement attribute of the CustomizeTag object
     *
     *@return    The PageElement value
     */
    public String getPageElement() {
        return mPageElement;
    }


    /**
     *  Defer our checking until the end of this tag is encountered.
     *
     *@return                   an <code>int</code> value
     *@exception  JspException  if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        return (SKIP_BODY);
    }


    /**
     *  The <code>doEndTag</code> method performs the work to find and display
     *  the attribute requested.
     *
     *@return                   an <code>int</code> value
     *@exception  JspException  if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {

        JspWriter writer = pageContext.getOut();

        if (mPageElement.length() == 0) {
            throw new JspException("CustomizeTag: pageElement not specified.");
        }

        HttpSession session = pageContext.getSession();
        String attr = mPageElement;
        String val = (String) session.getAttribute(attr);
        
        try {
            if ( null == val ) {
                writer.print("<!-- null value for: " + 
                             mPageElement + " -->");
            } else if ( val.length() == 0 ) {
                writer.print("<!-- no value for: " + 
                             mPageElement + " -->");
            } else {
                if ( mAddImagePath ) {
                	String imageSubdirectory = getImageSubdirectory();
                	if (Utility.isSet(imageSubdirectory)) {
                		val = 
                            (String)session.getAttribute
                            ("pages.root") + imageSubdirectory + val;
                	}
                	else {
                		val = 
                			(String)session.getAttribute
                			("pages.root") + "/en/images/" + val;
                	}
                }
                if (mEncodeForHTML) {
                    val = Utility.encodeForHTML(val);
                }
                boolean isNewUI = SessionTool.isNewUI((javax.servlet.http.HttpServletRequest)pageContext.getRequest());
                if (isNewUI){
                	val = encodeForAlternateUI(val, (String)session.getAttribute("pages.root"));
                }
                writer.print(val);
            }
        }
        catch (Exception e) {

        }

        return (EVAL_PAGE);
    }
    
    private String encodeForAlternateUI(String val, String pagesRoot) {
    	if (Utility.isSet(val)) {
    		StringBuffer res = new StringBuffer(200);
    		String[] tokens = val.split("href=");
    		//append the first token, since it will not need to be modified
    		res.append(tokens[0]);
    		for (int i = 1; i < tokens.length; i++) {
    			String token = tokens[i];
    			token = "href=" + token;
    			//if this is a mail to or http link, just append it as is
    			if (token.toLowerCase().contains("mailto") || token.toLowerCase().contains("http")) {
    				res.append(token);
    			}
    			else {
    				res.append(token.replaceAll("(\\.\\./)", "").replaceAll("href=\"", "href=\""+pagesRoot+"/"));
    			}
    		}
        	return res.toString();
    	}
    	else {
    		return val;
    	}
    }
}


