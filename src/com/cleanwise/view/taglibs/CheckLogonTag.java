/**
 * Title:        CheckLogonTag
 * Description:  This is the tag class of CheckLogon.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li
 */

package com.cleanwise.view.taglibs;


import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.view.utils.Constants;

/**
 * Check for a valid User logged on in the current session.  If there is no
 * such user, forward control to the logon page.
 */

public final class CheckLogonTag extends TagSupport {
    
    
    // --------------------------------------------------- Instance Variables
    
    
    /**
     * The key of the session-scope bean we look for.
     */
    //private String name = Constants.USER_KEY;
    private String name = Constants.APIACCESS;
    private String name1 = Constants.APP_USER;
    
    /**
     * The page to which we should forward for the user to log on.
     */
    private String page = "/userportal/logon.do";
    
    // ----------------------------------------------------------- Properties
    
    
    /**
     * Return the bean name.
     */
    public String getName() {
        return (this.name);
    }
    
    
    /**
     * Set the bean name.
     *
     * @param name The new bean name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * Return the bean name.
     */
    public String getName1() {
        return (this.name1);
    }
    
    
    /**
     * Set the bean name.
     *
     * @param name The new bean name
     */
    public void setName1(String name) {
        this.name1 = name;
    }
    
    
    /**
     * Return the forward page.
     */
    public String getPage() {
        
        return (this.page);
        
    }
    
    
    /**
     * Set the forward page.
     *
     * @param page The new forward page
     */
    public void setPage(String page) {
        
        if ( page != null ) {
            this.page = page;
        }
        
    }
    
    
    // ------------------------------------------------------- Public Methods
    
    
    /**
     * Defer our checking until the end of this tag is encountered.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        
        return (SKIP_BODY);
        
    }
    
    private boolean isUserAllowed(String pUserName,
            String pUtype,
            String pLastreq) {
        
        if ( pUtype == null ) {
            return true;
        }

        if (pLastreq.indexOf("/admin2.0") == 0) {
            // Only admins are allowed here.
            return pUtype.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR) ||
                    pUtype.equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR) ||
                    pUtype.equals(RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR) ||
                    pUtype.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR);
        }

        if ( pLastreq.indexOf("/admin") == 0 ) {
            // Only admins are allowed here.
            if (pUtype.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR) ||
                    pUtype.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR)) {
                return true;
            } else if (pUtype.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE)||
                    pUtype.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)||
                    pUtype.equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR)) {
                if (pLastreq.toUpperCase().indexOf("LOCATE") >= 0) {
                    return true;
                }
            }
            return false;
        }
        
        if ( pLastreq.indexOf("/console") == 0 ) {
            // Only admins and customer service reps are allowed here.
            if (pUtype.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR) ||
                    pUtype.equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR) ||
                    pUtype.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR) ||
                    pUtype.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE) ||
                    pUtype.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)
                    ) {
                return true;
            }
            return false;
        }
        
        if ( pLastreq.indexOf("/estoreclient") == 0 ) {
            // Only admins and estore clients are allowed here.
            if (pUtype.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR) ||
                    pUtype.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR) ||
                    pUtype.equals(RefCodeNames.USER_TYPE_CD.ESTORE_CLIENT)
                    ) {
                return true;
            }
            return false;
        }
        
        return true;
    }
    
    /**
     * Perform our logged-in user check by looking for the existence of
     * a session scope bean under the specified name.  If this bean is not
     * present, control is forwarded to the specified logon page.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {
        
        // Is there a valid user logged on?
        boolean valid = false;
        HttpSession session = pageContext.getSession();
        javax.servlet.http.HttpServletRequest request =
                (javax.servlet.http.HttpServletRequest)
                pageContext.getRequest();
        
        com.cleanwise.view.utils.SessionTool st =
                new com.cleanwise.view.utils.SessionTool(request);

        String lastReq = request.getServletPath();
        
        com.cleanwise.view.utils.CleanwiseUser appUser = 
                    com.cleanwise.view.utils.ShopTool.getCurrentUser(request);
        if(appUser == null ||
                appUser.getUser() == null ||
                appUser.getUserId() <= 0 ){
            valid = false;

        } else {
            valid = true;
            
            // Record the last request.
            if ( request.getQueryString() != null ) {
                lastReq += "?" + request.getQueryString();
            }
            
            session.setAttribute("LastRequest", lastReq);
            
            // Place the session in a global scope to allow
            // a listing of current loginSessions.
            java.util.Hashtable loginSessions =
                    (java.util.Hashtable)pageContext.getAttribute
                    ("login.session.vector",
                    PageContext.APPLICATION_SCOPE );
            if ( null == loginSessions ) {
                loginSessions = new java.util.Hashtable(10);
            } else {
		// check and clear any sessions that may have expired.
		java.util.Enumeration sesKeys = loginSessions.keys();

		while (sesKeys.hasMoreElements()) {

		    javax.servlet.http.HttpSession thisSes =
			(javax.servlet.http.HttpSession)sesKeys.nextElement();
		    String userName = null;
		      
		    try { userName = (String)thisSes.getAttribute("LoginUserName"); }
		    catch (Exception e) {}
		    if ( null == userName ) {
			loginSessions.remove(thisSes);
		    }
		}
	    }
            
            if ( ! loginSessions.containsKey(session) ) {
                loginSessions.put(session, session);
            }

            pageContext.setAttribute("login.session.vector",
                    loginSessions,
                    PageContext.APPLICATION_SCOPE);
            
        }
        
        // Forward control based on the results
        if (valid) {
            // Make sure that the user is allowed
            // in this part of the site.
            String utype = (String)session.getAttribute(Constants.USER_TYPE),
                    uname = (String)session.getAttribute(Constants.USER_NAME);
            if ( isUserAllowed(uname, utype, lastReq) ) {
                return (EVAL_PAGE);
            }
            
            throw new JspException("No access allowed to: " + lastReq +
                    " for user type: " + utype );
        }
        
        try {
            pageContext.forward(page);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JspException(e.toString());
        }
        
        return (SKIP_PAGE);
    }
    
    
    /**
     * Release any acquired resources.
     */
    public void release() {
        
        super.release();
        //this.name = Constants.USER_KEY;
        this.name = Constants.APIACCESS;
        this.name1 = Constants.APP_USER;
        this.page = "index.jsp";
        
    }
    
    
}
