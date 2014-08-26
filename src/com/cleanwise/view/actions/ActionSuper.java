/**
 *  Title: LogOnAction Description: This is the Struts Action class mapping the
 *  logon. Purpose: Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     Liang Li
 */

package com.cleanwise.view.actions;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;

import com.cleanwise.service.api.util.LocatePropertyNames;
import com.cleanwise.service.api.util.MobileInfo;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.ApplicationDomainNameData;
import com.cleanwise.view.forms.ReportScheduleForm;
import com.cleanwise.view.forms.StorePortalForm;
import com.cleanwise.view.i18n.ClwMessageResourcesImpl;
import com.cleanwise.view.logic.LocateReportAccountLogic;
import com.cleanwise.view.logic.LocateReportCatalogLogic;
import com.cleanwise.view.logic.LocateReportDistributorLogic;
import com.cleanwise.view.logic.LocateReportItemLogic;
import com.cleanwise.view.logic.LocateStoreAccountLogic;
import com.cleanwise.view.logic.LocateStoreAssetLogic;
import com.cleanwise.view.logic.LocateStoreCatalogLogic;
import com.cleanwise.view.logic.LocateStoreDistLogic;
import com.cleanwise.view.logic.LocateStoreFhLogic;
import com.cleanwise.view.logic.LocateStoreGroupLogic;
import com.cleanwise.view.logic.LocateStoreItemLogic;
import com.cleanwise.view.logic.LocateStoreManufLogic;
import com.cleanwise.view.logic.LocateStoreOrderGuideLogic;
import com.cleanwise.view.logic.LocateStoreServiceLogic;
import com.cleanwise.view.logic.LocateStoreSiteLogic;
import com.cleanwise.view.logic.LocateStoreUserLogic;
import com.cleanwise.view.logic.LocateUploadItemLogic;
import com.cleanwise.view.logic.ReportingLocateStoreSiteLogic;
import com.cleanwise.view.utils.BreadCrumbNavigator;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.RequestPropertyNames;
import com.cleanwise.view.utils.SessionAttributes;
import com.cleanwise.view.utils.SessionTool;
import com.espendwise.service.api.util.MessageResource;
/**
 *  Implementation of <strong>Action</strong> that processes a user logon.
 *
 *@author     dvieira
 *@created    October 16, 2001
 */

public class ActionSuper extends Action {
    private static final Logger log = Logger.getLogger(ActionSuper.class);

    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss:ms");
    private static final String HTTPS = "https";
    private static final String HTTP = "http";

    //variable to indicate the below vars have been initialized, and not to parse them out again.
    private static boolean initializedStaticVariables = false;
    //lifetime static variables, read in once from configuration sources (web.xml)
    private static int redirectServletConfidentialPort = 0;
    private static int servletConfidentialPort = 0;
    private static String sslRequestHeader = "X-SSL-Request"; //default to X-SSL-Request, but may be changed in the web.xml file

    /**
     *If subclasses need a secure/confidential connection.  In practical terms this means
     *using a ssl conection.
     */
    protected boolean isRequiresConfidentialConnection(){
        return false;
    }

    /**
     *Figures out the scheme used for this request.  First looks for any forward header
     *that are indicative of this being an ssl request, next asks the request for the scheme
     */
    private String getScheme(HttpServletRequest request){
        //if the port we are listing on is the confidential port then this is ssl
        //this allows for "back dooring" into the server if you want to get at the
        //server without using ssl, or to disable this feature entirely by setting
        //this port to your main listing port, as if the server thinks everything is
        //confidential (https) it will never switch you in and out.

        String value = request.getHeader(sslRequestHeader);
        logm(" servletConfidentialPort="
                + servletConfidentialPort
                + " request.getServerPort()="
                + request.getServerPort()
                + " ssl value=" + value
                + " request.getScheme()="
                + request.getScheme()
        );

        if(request.getServerPort() == 8443){
        	return HTTPS;
        }
        if(value != null && "true".equals(value)){
            return HTTPS;
        }

        /*if(servletConfidentialPort == request.getServerPort()){
            return HTTPS;
        }*/

        return request.getScheme();
    }


    private String getDomain(HttpServletRequest request, boolean confidential){
    	try{
	    	ApplicationDomainNameData domain = SessionTool.getApplicationDomainData(request);
	    	if(confidential){
		    	if(domain != null && Utility.isSet(domain.getSslDomainNam())){
		    		return domain.getSslDomainNam();
		    	}
	    	}else{
	    		if(domain != null && Utility.isSet(domain.getDomainName())){
		    		return domain.getDomainName();
		    	}
	    	}
	    	log.info("Error no SSL domain configured, or no app domain in the session");
	    	//have to get default SSL domain...
	    	//if all else fails:
	    	return request.getServerName();
    	}catch(Exception e){
    		e.printStackTrace();
    		//default to requested URL
    		return request.getServerName();
    	}
    }

    private void encodeCookies(HttpServletRequest request, StringBuffer queryString){
        Cookie[] cc = request.getCookies();
		if(cc == null){
			return;
		}
        for(int i=0;i<cc.length;i++){
            Cookie c = cc[i];
            //don't encode the jsession id parameter
            if(!"jsessionid".equals(c.getName())){
                if(i>0){
                    //append the "&" as we are not in the first loop
                    queryString.append("&");
                }
                queryString.append(c.getName());
                queryString.append("=");
                queryString.append(c.getValue());
            }
        }
    }


    /**
     * Convinience method to extract the specified cookie if it exists.
     * @param request
     * @param name Name of the cookie you would like the value for
     * @return The value of the Cookie or null if it was not found or there were no cookies
     */
    protected String getCookie(HttpServletRequest request, String name){
    	Cookie[] cookies = request.getCookies();
    	if(cookies == null){
    		return null;
    	}
    	for(int i=0;i<cookies.length;i++){
    		Cookie c = cookies[i];
    		if(c != null && c.getName() != null && c.getName().equals(name)){
    			log.info(name+"::"+c.getValue());
    			return c.getValue();
    		}
    	}
    	return null;
    }

    /**
     *Handles redirecting to the confidential connection.
     *@Returns true if a redirect was issued and processing should not continue.  false if
     *nothing was done and processing should continue
     */
    private boolean redirectToConfidentialConnection(HttpServletRequest request,HttpServletResponse response)
            throws IOException{

        if( redirectServletConfidentialPort > 0  &&
                HTTP.compareToIgnoreCase(getScheme(request))==0 ){

            String domain = getDomain(request,true);
            // We came in on an insecure connection, send
            // a redirect.
            StringBuffer url = new StringBuffer();
            url.append("https://");
            url.append(domain);
            if(redirectServletConfidentialPort!=443){
                url.append(":");
                url.append(redirectServletConfidentialPort);
            }
            url.append(request.getRequestURI());
            url.append(";jsessionid=");
            url.append(request.getSession().getId());

            String existingQuery = request.getQueryString();
            StringBuffer queryString;
            if(existingQuery != null){
                queryString=new StringBuffer(existingQuery);
                queryString.append("&");
            }else{
                queryString=new StringBuffer();
            }
            if(!domain.equals(request.getServerName())){
                //encode cookies
                encodeCookies(request,queryString);
            }
            //append non confidential domain, in case user bookmarks
            //url so they will use the overided domain.
            if(queryString != null && queryString.length() > 0){
                queryString.append("&");
            }
            queryString.append(Constants.REQUESTED_DOMAIN);
            queryString.append("=");
            queryString.append(getDomain(request,false));
            if(queryString != null){
            	url.append("?");
                url.append(queryString);
            }
            /*
            InputStream r = request.getInputStream();
            if(r!=null){
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            byte[] buf = new byte[4024];
	            int len;
	            while ((len = r.read(buf)) > 0) {
	                bos.write(buf, 0, len);
	            }
	            byte[] byteArray = bos.toByteArray();
	
				url.append("&"+Constants.POST_DATA+"=");
				url.append(Utility.encodeForURL(new String(byteArray)));
            }*/
            logm("redirectToConfidentialConnection, sending redirect to: "+url);
            sendRedirect(url.toString(),response);
            return true;
        }
        return false;
    }

    /**
     *Handles redirecting out of the confidential connection.
     *@Returns true if a redirect was issued and processing should not continue.  false if
     *nothing was done and processing should continue
     */
    private boolean redirectFromConfidentialConnection(HttpServletRequest request,HttpServletResponse response)
            throws IOException    {


        String sslvalue = request.getHeader(sslRequestHeader);
        if(sslvalue == null ) {
            sslvalue = "false";
        }
        logm("redirectFromConfidentialConnection, scheme="
                + getScheme(request)
                + " redirectServletConfidentialPort="
                + redirectServletConfidentialPort
                + " sslvalue=" + sslvalue );
        // For developers.
        if(redirectServletConfidentialPort == 0 &&
                servletConfidentialPort == request.getServerPort()){
            return false;
        }

        // Only need to redirect out if the last request was
        // secure.

        if("true".equals(sslvalue) &&
                redirectServletConfidentialPort > 0 &&
                servletConfidentialPort != request.getServerPort()){

            Integer ep = (Integer) request.getSession().getAttribute(Constants.ENTRY_PORT);
            logm("redirectFromConfidentialConnection, ep="
                    + ep + " sslvalue=" + sslvalue );

            StringBuffer url = new StringBuffer();
            url.append("http://");
            url.append(getDomain(request,false));
            Integer entryPort = (Integer) request.getSession().getAttribute(Constants.ENTRY_PORT);
            if(entryPort.intValue() != 80 &&
                    entryPort.intValue() != redirectServletConfidentialPort ){
                url.append(":");
                url.append(request.getSession().getAttribute(Constants.ENTRY_PORT));
            }
            url.append(request.getRequestURI());
            if(request.getQueryString() != null){
                url.append("?");
                url.append(request.getQueryString());
            }
            logm("redirectFromConfidentialConnection, sending redirect to: "+url);
            sendRedirect(url.toString(),response);
            return true;
        }
        return false;

    }

    private void sendRedirect(String url, HttpServletResponse response)
            throws IOException{
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        response.addHeader("Location", url.toString());
        response.addHeader("Connection", "close");
        response.flushBuffer();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    

    //------START convenience methods for message resource access----------
    public String getMessage(MessageResources resources, HttpServletRequest request, String key,
                             Object arg0) {
        return this.getMessage(0,resources,request, key, new Object[] { arg0 });
    }

    public String getMessage(MessageResources resources, HttpServletRequest request, String key,
                             Object arg0,Object arg1) {
        return this.getMessage(0,resources,request, key, new Object[] { arg0, arg1 });
    }

    public String getMessage(MessageResources resources, HttpServletRequest request, String key,
                             Object arg0,Object arg1,Object arg2) {
        return this.getMessage(0,resources,request, key, new Object[] { arg0, arg1, arg2 });
    }

    public String getMessage(MessageResources resources, HttpServletRequest request, String key,
                             Object arg0,Object arg1,Object arg2,Object arg3) {
        return this.getMessage(0,resources,request, key, new Object[] { arg0, arg1, arg2, arg3 });
    }

    public String getMessage(MessageResources resources, HttpServletRequest request,String key, Object[] args) {
        return this.getMessage(0,resources,request, key, args);
    }
    //------------------------------END convinience methods for message resource access----------------

    /**
     *Retrieves a store specific message resource from the message resources if it is
     *supported by the implementing Message Resource class.
     */
    protected String getMessage(MessageResources resources, HttpServletRequest request, String key){
        return getMessage(0, resources, request, key, new Object[0]);
    }

    /**
     *Retrieves a store specific message resource from the message resources if it is
     *supported by the implementing Message Resource class.
     */
    protected String getMessage(int storeId, MessageResources resources, HttpServletRequest request, String key, Object[] args){

        Locale locale = null;
        String storeOrAccountPrefix = "";
        if(!(resources instanceof ClwMessageResourcesImpl)){
            return resources.getMessage(key,args);
        }
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        if(storeId == 0){
                if(appUser != null && appUser.getUserStore() != null && appUser.getUserStore().getBusEntity() != null){
                storeId = appUser.getUserStore().getBusEntity().getBusEntityId();
                // get the locale set up for the user.
                locale = appUser.getStorePrefixLocale();
            }
        }
        ClwMessageResourcesImpl clwResources = (ClwMessageResourcesImpl) resources;
        //If this is changed make sure to change @see com.clenaiwse.view.taglibs.StoreMessageTag
        //otherwise the action class will not always know which button was pressed if it
        //has a localized version.
        if ( null == locale ) {
            locale = RequestUtils.getUserLocale(request, null);
        }
        if(appUser != null && appUser.getUserStore() != null && appUser.getUserStore().getPrefix() != null) {
        	storeOrAccountPrefix = appUser.getUserStore().getPrefix().getValue();
        }
        AccountData accountD = null;
        if (appUser != null) {
        	accountD = appUser.getUserAccount();
        }
        if((storeOrAccountPrefix == null || storeOrAccountPrefix.trim().equals("")) && accountD != null) {
          	storeOrAccountPrefix = (accountD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FOLDER) != null ) ?
          			accountD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FOLDER) : "";
        }
        		
        String message = ClwMessageResourcesImpl.getMessage(locale, key, args,new String[0]);
        if (message == null) message = "???? "+key;
        return message;
    }

    // ------------------------------------------------------------ Public Methods


    /**
     *This replaces the preform method of struts 1.0.1
     */
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException{
        return this.perform(mapping, form, request,response);
    }


    /**
     *  Process the specified HTTP request, and create the corresponding HTTP
     *  response (or forward to another web component that will create it).
     *  Return an <code>ActionForward</code> instance describing where and how
     *  control should be forwarded, or <code>null</code> if the response has
     *  already been completed.
     *
     *@param  mapping               the ActionMapping used to select this
     *      instance
     *@param  request               the HTTP request we are processing
     *@param  response              the HTTP response we are creating
     *@param  form                  Description of Parameter
     *@return                       Description of the Returned Value
     */

    public ActionForward perform(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        //only do this once, use static variables
        if(!initializedStaticVariables){
            initializedStaticVariables = true;
            String sPort = getServlet().getServletConfig().getInitParameter(Constants.REDIRECT_CONFIDENTIAL_PORT);
            redirectServletConfidentialPort = Integer.parseInt(sPort);
            sPort = getServlet().getServletConfig().getInitParameter(Constants.LISTNING_CONFIDENTIAL_PORT);
            servletConfidentialPort = Integer.parseInt(sPort);
            String sslRequestHeaderTmp = getServlet().getServletConfig().getInitParameter(Constants.SSL_FORWARDED_HEADER);
            if(sslRequestHeaderTmp != null){
                sslRequestHeader = sslRequestHeaderTmp;
            }
        }

        if(null==request.getSession().getAttribute(Constants.ENTRY_SCHEME)){
            request.getSession().setAttribute(Constants.ENTRY_SCHEME, getScheme(request));
        }
        if(null==request.getSession().getAttribute(Constants.ENTRY_PORT)){
            request.getSession().setAttribute(Constants.ENTRY_PORT, new Integer(request.getServerPort()));
        }

        if(isRequiresConfidentialConnection()){
            if(redirectToConfidentialConnection(request, response)){
                logm( "     REDIRECTING--TO--CONFIDENTIAL"
                        + getRequestCookiesInfo(request));
                return null;
            }
        }else{
            if(redirectFromConfidentialConnection(request, response)){
                logm( "     REDIRECTING--FROM--CONFIDENTIAL"
                        + getRequestCookiesInfo(request));                return null;
            }
        }

        String className = this.getClass().getName();
        int pos = className.lastIndexOf('.');
        if(pos>=0) className = className.substring(pos+1);
        java.util.Enumeration paramNames = request.getParameterNames();
        String params = "";
        while(paramNames.hasMoreElements()){
            String pn = (String) paramNames.nextElement();
            // STJ-4512
            if (pn.indexOf("password")>=0 || pn.indexOf("username")>=0) {
                continue;
            }
            String val = null;
            if(pn.indexOf("CreditCardNum")>=0 || pn.indexOf("ccNum")>=0)  {
                val = "XXXX";
            } else {
                val = request.getParameter(pn);
            }
            if(val!=null && val.length()>0) {
                params += " "+pn+": <"+val+">";
            }
        }

        Date sTime = new Date();
        String sTimeS = sdf.format(sTime);
        String action = request.getParameter("action");
        String requestId = (String) request.getAttribute("requestId");
        HttpSession session = request.getSession();
        String sessionId=session.getId();
        String cuser = (String) session.getAttribute(Constants.USER_NAME);
        String mess = "@@@@S Class: <"+className+"> Action: <"+action+"> Params: {"+params+"} Started at: <"+sTimeS+">";
        mess += " Session id : <"+sessionId+">";
        mess += " RequestId: <" + requestId +">";
        mess += " User: <"+cuser+">";
        mess += getRequestCookiesInfo(request);
        mess += "@@@@";
        log.info(mess);
        ActionForward af = null;
try {
        	
        	//set whether this is a mobile client or not if specified
        	MobileInfo mobileInfo = new MobileInfo(request);
        	SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        	boolean isClientOnMobile = mobileInfo.detectMobileQuick();
        	String mobileFlag = (String) request.getParameter(Constants.MOBILE);
        	String web = (String) request.getParameter(Constants.WEB);
        	if(isClientOnMobile && !sessionDataUtil.isWebUI()){
        	request.getSession(false).setAttribute(Constants.MOBILE_CLIENT, new Boolean(true).toString());
        	ClwCustomizer.flushFileMapCache(request.getSession(false));
        	}
        	if(Utility.isSet(mobileFlag)){
        	request.getSession(false).setAttribute(Constants.MOBILE_CLIENT, new Boolean(Utility.isTrue(mobileFlag)).toString());
        	ClwCustomizer.flushFileMapCache(request.getSession(false));
        	}
        	if(web != null && web.equalsIgnoreCase(Constants.TRUE)){
        	request.getSession(false).removeAttribute(Constants.MOBILE_CLIENT);
        	ClwCustomizer.flushFileMapCache(request.getSession(false));
        	sessionDataUtil.setWebUI(true);
        	}
        	//STJ-5358
        	if(!MessageResource.areMessageResourcesLoaded()) {
        		StringBuilder mrDierctoryPathBuilder = new StringBuilder();
        		mrDierctoryPathBuilder.append(request.getSession().getServletContext().getRealPath("/"));
        		mrDierctoryPathBuilder.append(System.getProperty("messageResourceDirectory"));
        		
        		MessageResource.loadMessages(mrDierctoryPathBuilder.toString());
        		log.info("Message Resources are loaded successfully !!!!");
        	}
        	
        	/*CleanwiseUser user = ShopTool.getCurrentUser(session);
        	Locale locale = user.getPrefLocale();
        	session.setAttribute("org.apache.struts.action.LOCALE", locale); */

        	
        	af = performLocateAction(form,request, mapping,action);
            if(af == null){
                af = performSub(mapping,form,request, response);
            }
        } catch(IOException exc) {
            Date eTime = new Date();
            String eTimeS = sdf.format(eTime);
            double dur = (double) eTime.getTime()-sTime.getTime();
            dur /= 1000;
            mess = "@@@@E Class: <"+className+"> Action: <"+action+"> Params: {"+params+"} Started at: <"+sTimeS+">";
            mess += " Ended at: <"+eTimeS+">";
            mess += " Result: <Errror>";
            mess += " Duration: <"+dur+">";
            mess += " Session id : <"+sessionId+">";
            mess += " RequestId: <" + requestId +">";
            mess += " User: <"+cuser+">";
            mess += getRequestCookiesInfo(request);
            mess += "@@@@";
            log.info(mess);
            sendErrorNotificationEmail(request,af,mess,exc);
            throw exc;
        } catch(ServletException exc) {
            Date eTime = new Date();
            String eTimeS = sdf.format(eTime);
            double dur = (double) eTime.getTime()-sTime.getTime();
            dur /= 1000;
            mess = "@@@@E Class: <"+className+"> Action: <"+action+"> Params: {"+params+"} Started at: <"+sTimeS+">";
            mess += " Ended at: <"+eTimeS+">";
            mess += " Result: <Errror>";
            mess += " Duration: <"+dur+">";
            mess += " Session id : <"+sessionId+">";
            mess += " RequestId: <" + requestId +">";
            mess += " User: <"+cuser+">";
            mess += getRequestCookiesInfo(request);
            mess += "@@@@";
            log.info(mess);
            sendErrorNotificationEmail(request,af,mess,exc);
            throw exc;
        }

        Date eTime = new Date();
        String eTimeS = sdf.format(eTime);
        double dur = (double) eTime.getTime()-sTime.getTime();
        dur /= 1000;
        mess = "@@@@E Class: <"+className+"> Action: <"+action+"> Params: {"+params+"} Started at: <"+sTimeS+">";
        mess += " Ended at: <"+eTimeS+">";
        mess += " Result: <OK>";
        mess += " Duration: <"+dur+">";
        mess += " Session id : <"+sessionId+">";
        mess += " RequestId: <" + requestId +">";
        mess += " User: <"+cuser+">";
        mess += getRequestCookiesInfo(request);
        mess += "@@@@";
        log.info(mess);
        request.setAttribute(Constants.REQUEST_ATTRIBUTE_PAGE_GENERATION_TIME, dur + "");
      //catalog link modification start
        String catView = request.getParameter("showCatalog");
        if(catView != null && catView.equals("yy")){
        	String mappingAction = "showCatLast";
        	String catToView = request.getParameter("catalogToView");
        	ActionForward actionForward = mapping.findForward(mappingAction);
        	af = new ActionForward(actionForward);
        	af.setPath(actionForward.getPath() +
        			"&locateStoreCatalogForm.selectedCatalogIds="+
        			catToView + "&locateStoreCatalogForm.name=STORE_ITEM_CATALOG_FORM" +
        			"&locateStoreCatalogForm.searchCatalogType=catalogId" +
        			"&jspSubmitIdent=%23LocateStoreCatalogForm" +
        			"&locateStoreCatalogForm.property=catalogFilter");
            }
        //catalog link modification end
//////////////
		if(response!=null) {
		    response.setHeader("Cache-Control","no-cache"); 
			response.addHeader("Cache-Control","private");
			response.setHeader("Pragma","no-cache");
			response.setHeader ("Expires", "-1");
			log.info("ActionSuper ********* no-cahe set");
		} else {
			log.info("ActionSuper ********* responce is null");
		}
/////////////		
        if(af!=null) {
        log.info("ActionSuper ********* af.getModule(): "+af.getModule());
        log.info("ActionSuper ********* af.getName(): "+af.getName());
        log.info("ActionSuper ********* af.getPath(): "+af.getPath());
        log.info("ActionSuper ********* af.getRedirect(): "+af.getRedirect());
        } else {
        log.info("ActionSuper ********* af: "+af);
        return null;
        }


        // do this only when redirected to error.do page or exception is caught by action class
        Exception MyExc = (Exception)request.getAttribute(Constants.EXCEPTION_OBJECT);
        if(MyExc==null){
        	MyExc = (Exception)request.getAttribute("errorobject");
        }
        String MyPath = af.getPath();
        log.info("Request Error: "+MyExc+" Path: "+MyPath);

        if(MyExc!=null || MyPath.indexOf("Error") >= 0){

        	sendErrorNotificationEmail(request,af,mess,MyExc);
        }

        return af;
    }

    public void navigateBreadCrumb(HttpServletRequest request, ActionForward af) {

        BreadCrumbNavigator bradCrambNav = (BreadCrumbNavigator) request.getSession().getAttribute(Constants.BREAD_CRUMB_NAVIGATOR);
        if (bradCrambNav == null) {
            CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
            if(appUser!=null)  {
                bradCrambNav = new BreadCrumbNavigator(appUser.getUser().getUserId());
            } else {
                bradCrambNav = new BreadCrumbNavigator();
            }
        }

        String actionForwardPath = "";
        if (af != null) {
            actionForwardPath = af.getPath();
        }

        bradCrambNav.set(request, actionForwardPath);

        request.getSession().setAttribute(Constants.BREAD_CRUMB_NAVIGATOR, bradCrambNav);
    }

    public void sendErrorNotificationEmail(HttpServletRequest request,
            ActionForward af, String message, Exception ex){

        	 //disabled as we are now using the process user activity reoprt for this.

    }

    private String getRequestCookiesInfo(HttpServletRequest request) {
        com.cleanwise.view.utils.SessionTool st =
                new com.cleanwise.view.utils.SessionTool(request);
        return st.mkCookieInfoString();
    }

    private String mFailForward = "failure";
    /**
     *Forward that the user will be forwarded to if an exception is left uncaught.
     */
    protected void setFailForward(String pFailForward){
        mFailForward = pFailForward;
    }
    /**
     *Forward that the user will be forwarded to if an exception is left uncaught.
     */
    protected String getFailForward(){
        return mFailForward;
    }


    public ActionForward performSub(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        return null;
    }

    private static final TreeSet<String> ACTIONS_NOT_TO_FORWARD = new TreeSet<String>();
    static{
        ACTIONS_NOT_TO_FORWARD.add("Search");
    }

    private ActionForward performLocateAction(ActionForm form,HttpServletRequest request, ActionMapping mapping, String action) throws IOException, ServletException{
        MessageResources mr = getResources(request);
        String locateItemsStr = getMessage(mr, request, "admin.button.locateItem");
        String cancelStr = getMessage(mr, request, "global.action.label.cancel");
        String returnSelectedStr = getMessage(mr, request, "admin.button.returnSelected");


    	if(!(form instanceof StorePortalForm)){
            logm("Not a StorePortalForm");
            return null;
        }
        String command = request.getParameter("command");
        StorePortalForm sForm = (StorePortalForm) form;
        sForm.setReturnLocateTypeCd(null);
        try{
            String mappingAction = "success";
            ActionErrors ae = null;
            String submitFormIdent = request.getParameter("jspSubmitIdent");
            int submitFormIdentLen = 0;
            if(submitFormIdent!=null){
                submitFormIdentLen = submitFormIdent.length();
            }
            //Search catalog actions
            if(submitFormIdent!=null &&
                    submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_CATALOG_FORM)==0){
                ae = LocateStoreCatalogLogic.processAction(request, sForm, action);
                if(ae.size()>0) {
                     saveErrors(request, ae);
                    sForm.getLocateStoreCatalogForm().setLocateCatalogFl(true);
                    return (mapping.findForward(mFailForward));
                }
                if(submitFormIdentLen == ("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_CATALOG_FORM).length()){
                    if("Return Selected".equals(action) && sForm.getLocateStoreCatalogForm().getLevel()==1) {
                        ae = LocateStoreCatalogLogic.setFilter(request, sForm);
                        sForm.getLocateStoreCatalogForm().setLocateCatalogFl(false);
                    }else if("Cancel".equals(action) && sForm.getLocateStoreCatalogForm().getLevel()==1) {
                        sForm.getLocateStoreCatalogForm().setLocateCatalogFl(false);
                    } else {
                        sForm.getLocateStoreCatalogForm().setLocateCatalogFl(true);
                    }
                }
                if(action != null && ACTIONS_NOT_TO_FORWARD.contains(action)){
                    if (sForm instanceof ReportScheduleForm){
                    	log.info("FFFFFFF [ActionSuper] :: getWeekDays().length =" + ((ReportScheduleForm)sForm).getWeekDays().length);
                    }
                    return mapping.findForward(mappingAction);
                }else{
                    return null;
                }
            }

            //Search account actions
            if(submitFormIdent!=null &&
                    submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_ACCOUNT_FORM)==0 &&
                    submitFormIdentLen == ("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_ACCOUNT_FORM).length()){
                ae = LocateStoreAccountLogic.processAction(request, sForm, action);
                if(ae.size()>0) {
                    sForm.getLocateStoreAccountForm().setLocateAccountFl(true);
                }
                if("Return Selected".equals(action) && sForm.getLocateStoreAccountForm().getLevel()==1) {
                    ae = LocateStoreAccountLogic.setFilter(request, sForm);
                    sForm.getLocateStoreAccountForm().setLocateAccountFl(false);
                }else if("Cancel".equals(action) && sForm.getLocateStoreAccountForm().getLevel()==1) {
                    sForm.getLocateStoreAccountForm().setLocateAccountFl(false);
                } else {
                    sForm.getLocateStoreAccountForm().setLocateAccountFl(true);
                }
                if(action != null && ACTIONS_NOT_TO_FORWARD.contains(action)){
                    return mapping.findForward(mappingAction);
                }else{
                    return null;
                }
            }

            //Search report account actions
            if(submitFormIdent!=null &&
                    submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_REPORT_ACCOUNT_FORM)==0 &&
                    submitFormIdentLen == ("#"+SessionAttributes.SEARCH_FORM.LOCATE_REPORT_ACCOUNT_FORM).length()) {
                ae = LocateReportAccountLogic.processAction(request, sForm, action);
                if (ae.size() > 0) {
                    sForm.getLocateReportAccountForm().setLocateReportAccountFl(true);
                }
                if (LocatePropertyNames.RETURN_SELECTED_ACTION.equals(action) && sForm.getLocateReportAccountForm().getLevel()== 1) {
                    ae = LocateReportAccountLogic.setFilter(request, sForm);
                    sForm.getLocateReportAccountForm().setLocateReportAccountFl(false);
                }
                else if(LocatePropertyNames.CANCEL_ACTION.equals(action) && sForm.getLocateReportAccountForm().getLevel() == 1) {
                    sForm.getLocateReportAccountForm().setLocateReportAccountFl(false);
                }
                else {
                    sForm.getLocateReportAccountForm().setLocateReportAccountFl(true);
                }
                if (action != null && ACTIONS_NOT_TO_FORWARD.contains(action)){
                    return mapping.findForward(mappingAction);
                }
                else {
                    return null;
                }
            }

            //Search order guide actions
            if(submitFormIdent!=null &&
                    submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_ORDER_GUIDE_FORM)==0 &&
                    submitFormIdentLen == ("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_ORDER_GUIDE_FORM).length()){
                ae = LocateStoreOrderGuideLogic.processAction(request, sForm, action);
                if(ae.size()>0) {
                    sForm.getLocateStoreOrderGuideForm().setLocateOrderGuideFl(true);
                }
                if(LocatePropertyNames.RETURN_SELECTED_ACTION.equals(action) && sForm.getLocateStoreOrderGuideForm().getLevel()==1) {
                    ae = LocateStoreOrderGuideLogic.setFilter(request, sForm);
                    sForm.getLocateStoreOrderGuideForm().setLocateOrderGuideFl(false);
                }else if(LocatePropertyNames.CANCEL_ACTION.equals(action) && sForm.getLocateStoreOrderGuideForm().getLevel()==1) {
                    sForm.getLocateStoreOrderGuideForm().setLocateOrderGuideFl(false);
                } else {
                    sForm.getLocateStoreOrderGuideForm().setLocateOrderGuideFl(true);
                }
                if(action != null && ACTIONS_NOT_TO_FORWARD.contains(action)){
                    return mapping.findForward(mappingAction);
                }else{
                    return null;
                }
            }

            //Search store group actions
            if (submitFormIdent!=null &&
                    submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_GROUP_FORM)==0 &&
                    submitFormIdentLen == ("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_GROUP_FORM).length()) {
                ae = LocateStoreGroupLogic.processAction(request, sForm, action);
                if (ae.size() > 0) {
                    sForm.getLocateStoreGroupForm().setLocateStoreGroupFl(true);
                }
                if (LocatePropertyNames.RETURN_SELECTED_ACTION.equals(action) && sForm.getLocateStoreGroupForm().getLevel() == 1) {
                    ae = LocateStoreGroupLogic.setFilter(request, sForm);
                    sForm.getLocateStoreGroupForm().setLocateStoreGroupFl(false);
                } else if (LocatePropertyNames.CANCEL_ACTION.equals(action) && sForm.getLocateStoreGroupForm().getLevel() == 1) {
                    sForm.getLocateStoreGroupForm().setLocateStoreGroupFl(false);
                } else {
                    sForm.getLocateStoreGroupForm().setLocateStoreGroupFl(true);
                }
                if (action != null && ACTIONS_NOT_TO_FORWARD.contains(action)) {
                    return mapping.findForward(mappingAction);
                } else {
                    return null;
                }
            }

            //Search Item Actions
            if(submitFormIdent!=null &&
                    submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_ITEM_FORM)==0 &&
                    submitFormIdentLen == ("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_ITEM_FORM).length()){
                ae = LocateStoreItemLogic.processAction(request, sForm, action);
                if(ae.size()>0) {
                    sForm.getLocateStoreItemForm().setLocateItemFl(true);
                }
                if(returnSelectedStr.equals(action) && sForm.getLocateStoreItemForm().getLevel()==1) {
                	log.info("================== action=" + action  + "/ returnSelectedStr=" + returnSelectedStr);
                    ae = LocateStoreItemLogic.setFilter(request, sForm);
                    sForm.getLocateStoreItemForm().setLocateItemFl(false);
                }else if(cancelStr.equals(action) && sForm.getLocateStoreItemForm().getLevel()==1) {
                	log.info("================== action=" + action  + "/ cancelStr=" + cancelStr);
                    sForm.getLocateStoreItemForm().setLocateItemFl(false);
                } else {
                    sForm.getLocateStoreItemForm().setLocateItemFl(true);
                }
                if(action != null && ACTIONS_NOT_TO_FORWARD.contains(action)){
                    return mapping.findForward(mappingAction);
                }else{
                    return null;
                }
            }

            //Search report Distributor actions
            if(submitFormIdent!=null &&
                    submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_REPORT_DISTRIBUTOR_FORM)==0 &&
                    submitFormIdentLen == ("#"+SessionAttributes.SEARCH_FORM.LOCATE_REPORT_DISTRIBUTOR_FORM).length()) {
                ae = LocateReportDistributorLogic.processAction(request, sForm, action);
                if (ae.size() > 0) {
                    sForm.getLocateReportDistributorForm().setLocateReportDistributorFl(true);
                }
                if (LocatePropertyNames.RETURN_SELECTED_ACTION.equals(action) && sForm.getLocateReportDistributorForm().getLevel()== 1) {
                    ae = LocateReportDistributorLogic.setFilter(request, sForm);
                    sForm.getLocateReportDistributorForm().setLocateReportDistributorFl(false);
                }
                else if(LocatePropertyNames.CANCEL_ACTION.equals(action) && sForm.getLocateReportDistributorForm().getLevel() == 1) {
                    sForm.getLocateReportDistributorForm().setLocateReportDistributorFl(false);
                }
                else {
                    sForm.getLocateReportDistributorForm().setLocateReportDistributorFl(true);
                }
                if (action != null && ACTIONS_NOT_TO_FORWARD.contains(action)){
                    return mapping.findForward(mappingAction);
                }
                else {
                    return null;
                }
            }

            //Search distributor actions
            if(submitFormIdent!=null &&
                    submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_DIST_FORM)==0 &&
                    submitFormIdentLen == ("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_DIST_FORM).length()){
                ae = LocateStoreDistLogic.processAction(request, sForm, action);
                if(ae.size()>0) {
                    sForm.getLocateStoreDistForm().setLocateDistFl(true);
                }
                if("Return Selected".equals(action) && sForm.getLocateStoreDistForm().getLevel()==1) {
                    ae = LocateStoreDistLogic.setFilter(request, sForm);
                    sForm.getLocateStoreDistForm().setLocateDistFl(false);
                }else if("Cancel".equals(action) && sForm.getLocateStoreDistForm().getLevel()==1) {
                    sForm.getLocateStoreDistForm().setLocateDistFl(false);
                } else {
                    sForm.getLocateStoreDistForm().setLocateDistFl(true);
                }
                if(action != null && ACTIONS_NOT_TO_FORWARD.contains(action)){
                    return mapping.findForward(mappingAction);
                }else{
                    return null;
                }
            }

            //Search manufacturers actions
            if(submitFormIdent!=null &&
                    submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_MANUF_FORM)==0 &&
                    submitFormIdentLen == ("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_MANUF_FORM).length()){
                ae = LocateStoreManufLogic.processAction(request, sForm, action);
                if(ae.size()>0) {
                    sForm.getLocateStoreManufForm().setLocateManufFl(true);
                }
                if("Return Selected".equals(action) && sForm.getLocateStoreManufForm().getLevel()==1) {
                    ae = LocateStoreManufLogic.setFilter(request, sForm);
                    sForm.getLocateStoreManufForm().setLocateManufFl(false);
                }else if("Cancel".equals(action) && sForm.getLocateStoreManufForm().getLevel()==1) {
                    sForm.getLocateStoreManufForm().setLocateManufFl(false);
                } else {
                    sForm.getLocateStoreManufForm().setLocateManufFl(true);
                }
                if(action != null && ACTIONS_NOT_TO_FORWARD.contains(action)){
                    return mapping.findForward(mappingAction);
                }else{
                    return null;
                }
            }

            //Search Upload Item Actions
            if(submitFormIdent!=null &&
                    submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_UPLOAD_ITEM_FORM)==0){
                ae = LocateUploadItemLogic.processAction(request, sForm, action);
                if(ae.size()>0) {
                    sForm.getLocateUploadItemForm().setLocateUploadItemFl(true);
                }
                if(submitFormIdentLen == ("#"+SessionAttributes.SEARCH_FORM.LOCATE_UPLOAD_ITEM_FORM).length()){
                    if("Return Selected".equals(action) && sForm.getLocateUploadItemForm().getLevel()==1) {
                        ae = LocateUploadItemLogic.setFilter(request, sForm);
                        sForm.getLocateUploadItemForm().setLocateUploadItemFl(false);
                    }else if("Cancel".equals(action) && sForm.getLocateUploadItemForm().getLevel()==1) {
                        sForm.getLocateUploadItemForm().setLocateUploadItemFl(false);
                    } else {
                        sForm.getLocateUploadItemForm().setLocateUploadItemFl(true);
                    }
                }
                if(action != null && ACTIONS_NOT_TO_FORWARD.contains(action)){
                    return mapping.findForward(mappingAction);
                }else{
                    return null;
                }
            }

            //Search users actions
            if(submitFormIdent!=null &&
                    submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_USER_FORM)==0 &&
                    submitFormIdentLen == ("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_USER_FORM).length()){
                 ae = LocateStoreUserLogic.processAction(request, sForm, action);
                if(ae.size()>0) {
                    sForm.getLocateStoreUserForm().setLocateUserFl(true);
                    saveErrors(request, ae);
                }

                if("Return Selected".equals(action) && sForm.getLocateStoreUserForm().getLevel()==1) {

                    ae = LocateStoreUserLogic.setFilter(request, sForm);
                    if(ae.size()>0) {
                        sForm.getLocateStoreUserForm().setLocateUserFl(true);
                        saveErrors(request, ae);
                    }
                    sForm.getLocateStoreUserForm().setLocateUserFl(false);
                }else if("Cancel".equals(action) && sForm.getLocateStoreUserForm().getLevel()==1) {
                    sForm.getLocateStoreUserForm().setLocateUserFl(false);
                } else {

                    sForm.getLocateStoreUserForm().setLocateUserFl(true);
                }
                if(action != null && ACTIONS_NOT_TO_FORWARD.contains(action)){

                    return mapping.findForward(mappingAction);
                }else{

                    return null;
                }
            }

            //Search FH actions
            if(submitFormIdent!=null &&
                    submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_FH_FORM)==0 &&
                    submitFormIdentLen == ("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_FH_FORM).length()){
                 ae = LocateStoreFhLogic.processAction(request, sForm, action);
                if(ae.size()>0) {
                    sForm.getLocateStoreFhForm().setLocateFhFl(true);
                    saveErrors(request, ae);
                }

                if("Return Selected".equals(action) && sForm.getLocateStoreFhForm().getLevel()==1) {

                    ae = LocateStoreFhLogic.setFilter(request, sForm);
                    if(ae.size()>0) {
                        sForm.getLocateStoreFhForm().setLocateFhFl(true);
                        saveErrors(request, ae);
                    }
                    sForm.getLocateStoreFhForm().setLocateFhFl(false);
                }else if("Cancel".equals(action) && sForm.getLocateStoreFhForm().getLevel()==1) {
                    sForm.getLocateStoreFhForm().setLocateFhFl(false);
                } else {

                    sForm.getLocateStoreFhForm().setLocateFhFl(true);
                }
                if(action != null && ACTIONS_NOT_TO_FORWARD.contains(action)){

                    return mapping.findForward(mappingAction);
                }else{

                    return null;
                }
            }

            //Search Service actions
            if(submitFormIdent!=null &&
                    submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_SERVICE_FORM)==0 &&
                    submitFormIdentLen == ("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_SERVICE_FORM).length()){
                ae = LocateStoreServiceLogic.processAction(request, sForm, action);
                if(ae.size()>0) {
                    sForm.getLocateStoreServiceForm().setLocateServiceFl(true);
                    saveErrors(request, ae);
                }

                if("Return Selected".equals(action) && sForm.getLocateStoreServiceForm().getLevel()==1) {

                    ae = LocateStoreServiceLogic.setFilter(request, sForm);
                    if(ae.size()>0) {
                        sForm.getLocateStoreServiceForm().setLocateServiceFl(true);
                        saveErrors(request, ae);
                    }
                    sForm.getLocateStoreServiceForm().setLocateServiceFl(false);
                }else if("Cancel".equals(action) && sForm.getLocateStoreServiceForm().getLevel()==1) {
                    sForm.getLocateStoreServiceForm().setLocateServiceFl(false);
                } else {

                    sForm.getLocateStoreServiceForm().setLocateServiceFl(true);
                }
                if(action != null && ACTIONS_NOT_TO_FORWARD.contains(action)){

                    return mapping.findForward(mappingAction);
                }else{

                    return null;
                }
            }

            //Search Asset actions
            if (submitFormIdent != null &&
                    submitFormIdent.indexOf("#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_ASSET_FORM) == 0 &&
                    submitFormIdentLen == ("#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_ASSET_FORM).length()) {
                ae = LocateStoreAssetLogic.processAction(request, sForm, action);
                if (ae.size() > 0) {
                    sForm.getLocateStoreAssetForm().setLocateAssetFl(true);
                    saveErrors(request, ae);
                }

                if ("Return Selected".equals(action) && sForm.getLocateStoreAssetForm().getLevel() == 1) {

                    ae = LocateStoreAssetLogic.setFilter(request, sForm);
                    if (ae.size() > 0) {
                        sForm.getLocateStoreAssetForm().setLocateAssetFl(true);
                        saveErrors(request, ae);
                    }
                    sForm.getLocateStoreAssetForm().setLocateAssetFl(false);
                } else if ("Cancel".equals(action) && sForm.getLocateStoreAssetForm().getLevel() == 1) {
                    sForm.getLocateStoreAssetForm().setLocateAssetFl(false);
                } else {

                    sForm.getLocateStoreAssetForm().setLocateAssetFl(true);
                }
                if (action != null && ACTIONS_NOT_TO_FORWARD.contains(action)) {

                    return mapping.findForward(mappingAction);
                } else {

                    return null;
                }
            }

            //Search report site actions
            if(submitFormIdent!=null &&
                    submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_REPORT_SITE_FORM)==0 &&
                    submitFormIdentLen == ("#"+SessionAttributes.SEARCH_FORM.LOCATE_REPORT_SITE_FORM).length()) {
                ae = ReportingLocateStoreSiteLogic.processAction(request, sForm, action);
                if (ae.size() > 0) {
                    sForm.getReportingLocateStoreSiteForm().setReportingLocateStoreSiteFl(true);
                }

                if (LocatePropertyNames.RETURN_SELECTED_ACTION.equals(action) && sForm.getReportingLocateStoreSiteForm().getLevel()== 1) {
                    ae = ReportingLocateStoreSiteLogic.setFilter(request, sForm);
                    sForm.getReportingLocateStoreSiteForm().setReportingLocateStoreSiteFl(false);
                }
                else if(LocatePropertyNames.CANCEL_ACTION.equals(action) && sForm.getReportingLocateStoreSiteForm().getLevel() == 1) {
                    sForm.getReportingLocateStoreSiteForm().setReportingLocateStoreSiteFl(false);
                }
                else {
                    sForm.getReportingLocateStoreSiteForm().setReportingLocateStoreSiteFl(true);
                }
                if (action != null && ACTIONS_NOT_TO_FORWARD.contains(action)){
                    return mapping.findForward(mappingAction);
                }
                else {
                    return null;
                }
            }

            //Search report item actions
            if(submitFormIdent!=null &&
                    submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_REPORT_ITEM_FORM)==0 &&
                    submitFormIdentLen == ("#"+SessionAttributes.SEARCH_FORM.LOCATE_REPORT_ITEM_FORM).length()) {
                ae = LocateReportItemLogic.processAction(request, sForm, action);
                if (ae.size() > 0) {
                    sForm.getLocateReportItemForm().setLocateReportItemFl(true);
                }
                if (LocatePropertyNames.RETURN_SELECTED_ACTION.equals(action) && sForm.getLocateReportItemForm().getLevel()== 1) {
                    ae = LocateReportItemLogic.setFilter(request, sForm);
                    sForm.getLocateReportItemForm().setLocateReportItemFl(false);
                }
                else if(LocatePropertyNames.CANCEL_ACTION.equals(action) && sForm.getLocateReportItemForm().getLevel() == 1) {
                    sForm.getLocateReportItemForm().setLocateReportItemFl(false);
                }
                else {
                    sForm.getLocateReportItemForm().setLocateReportItemFl(true);
                }
                if (action != null && ACTIONS_NOT_TO_FORWARD.contains(action)){
                    return mapping.findForward(mappingAction);
                }
                else {
                    return null;
                }
            }


            //Search sites actions
            if(submitFormIdent!=null &&
                    submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_SITE_FORM)==0 &&
                    submitFormIdentLen == ("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_SITE_FORM).length())
            {
                 ae = LocateStoreSiteLogic.processAction(request, sForm, action);
                if(ae.size()>0) {
                    sForm.getLocateStoreSiteForm().setLocateSiteFl(true);
                    saveErrors(request, ae);
                }
                if("Return Selected".equals(action) && sForm.getLocateStoreSiteForm().getLevel()==1) {

                    ae = LocateStoreSiteLogic.setFilter(request, sForm);
                    if(ae.size()>0) {
                        sForm.getLocateStoreSiteForm().setLocateSiteFl(true);
                        saveErrors(request, ae);
                    }
                    sForm.getLocateStoreSiteForm().setLocateSiteFl(false);
                }else if("Cancel".equals(action) && sForm.getLocateStoreSiteForm().getLevel()==1) {
                    sForm.getLocateStoreSiteForm().setLocateSiteFl(false);
                } else {
                    sForm.getLocateStoreSiteForm().setLocateSiteFl(true);
                }
                if(action != null && ACTIONS_NOT_TO_FORWARD.contains(action) &&
                        !"async".equalsIgnoreCase(request.getParameter("requestType"))){

                    return mapping.findForward(mappingAction);
                }else{
                    return null;
                }
            }

            //Search report catalog actions
            if(submitFormIdent!=null &&
                    submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_REPORT_CATALOG_FORM)==0 &&
                    submitFormIdentLen == ("#"+SessionAttributes.SEARCH_FORM.LOCATE_REPORT_CATALOG_FORM).length()) {
                ae = LocateReportCatalogLogic.processAction(request, sForm, action);
                if (ae.size() > 0) {
                    sForm.getLocateReportCatalogForm().setLocateReportCatalogFl(true);
                }
                if (LocatePropertyNames.RETURN_SELECTED_ACTION.equals(action) && sForm.getLocateReportCatalogForm().getLevel()== 1) {
                    ae = LocateReportCatalogLogic.setFilter(request, sForm);
                    sForm.getLocateReportCatalogForm().setLocateReportCatalogFl(false);
                }
                else if(LocatePropertyNames.CANCEL_ACTION.equals(action) && sForm.getLocateReportCatalogForm().getLevel() == 1) {
                    sForm.getLocateReportCatalogForm().setLocateReportCatalogFl(false);
                }
                else {
                    sForm.getLocateReportCatalogForm().setLocateReportCatalogFl(true);
                }
                if (action != null && ACTIONS_NOT_TO_FORWARD.contains(action)){
                    return mapping.findForward(mappingAction);
                }
                else {
                    return null;
                }
            }

            if("Locate Catalog".equals(action)) {
            	if (RequestPropertyNames.getIsLocateReportCatalogRequest(request)) {
                    ae = LocateReportCatalogLogic.processAction(request, sForm, LocatePropertyNames.INIT_SEARCH_ACTION);
                    if(ae.size() == 0) {
                        sForm.getLocateReportCatalogForm().setLocateReportCatalogFl(true);
                    }
                }
                else {
                	ae = LocateStoreCatalogLogic.processAction(request,sForm, "initSearch");
                    if(ae.size()==0) {
                        sForm.getLocateStoreCatalogForm().setLocateCatalogFl(true);
                    }
                }
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
            } else if("Locate Account".equals(action)|| "Locate Account".equals(command)) {
                if (RequestPropertyNames.getIsLocateReportAccountRequest(request)) {
                    ae = LocateReportAccountLogic.processAction(request, sForm, LocatePropertyNames.INIT_SEARCH_ACTION);
                    if(ae.size() == 0) {
                        sForm.getLocateReportAccountForm().setLocateReportAccountFl(true);
                    }
                }
                else {
                    ae = LocateStoreAccountLogic.processAction(request, sForm, "initSearch");
                    if(ae.size() == 0) {
                        sForm.getLocateStoreAccountForm().setLocateAccountFl(true);
                    }
                }
            } else if("Locate Item".equals(action)|| "Locate Item".equals(command)) {
                if (RequestPropertyNames.getIsLocateReportItemRequest(request)) {
                    ae = LocateReportItemLogic.processAction(request, sForm, LocatePropertyNames.INIT_SEARCH_ACTION);
                    if(ae.size() == 0) {
                        sForm.getLocateReportItemForm().setLocateReportItemFl(true);
                    }
                }
                else {
                    ae = LocateStoreItemLogic.processAction(request, sForm, "initSearch");
                    if(ae.size() == 0) {
                        sForm.getLocateStoreItemForm().setLocateItemFl(true);
                    }
                }
            } else if(LocatePropertyNames.LOCATE_ORDER_GUIDE_ACTION.equals(action)) {
            	ae = LocateStoreOrderGuideLogic.processAction(request,sForm, "initSearch");
            	if(ae.size()==0) {
                    sForm.getLocateStoreOrderGuideForm().setLocateOrderGuideFl(true);
                }
            } else if(LocatePropertyNames.LOCATE_STORE_GROUP_ACTION.equals(action)) {
            	ae = LocateStoreGroupLogic.processAction(request, sForm, LocatePropertyNames.INIT_SEARCH_ACTION);
            	if (ae.size() == 0) {
                    sForm.getLocateStoreGroupForm().setLocateStoreGroupFl(true);
                }
            } else if(locateItemsStr.equals(action)) {
                ae = LocateStoreItemLogic.processAction(request,sForm, "initSearch");
                if(ae.size()==0) {
                    sForm.getLocateStoreItemForm().setLocateItemFl(true);
                }
            } else if("LocateAssignDistributor".equals(action) || "LocateFilterDistributor".equals(action)) {
                HashMap vars = sForm.getFormVars();
                if(vars == null){
                    vars = new HashMap();
                    sForm.setFormVars(vars);
                }
                vars.put(StorePortalForm.FORM_VAR_ALT_LOCATE,Boolean.TRUE);
                ae = LocateStoreDistLogic.processAction(request,sForm, "initSearch");
                if(ae.size()==0) {
                    sForm.getLocateStoreDistForm().setLocateDistFl(true);
                }
            } else if("Locate Distributor".equals(action)|| "Locate Distributor".equals(command)) {

                if (RequestPropertyNames.getIsLocateReportDistributorRequest(request)) {
                    ae = LocateReportDistributorLogic.processAction(request, sForm, LocatePropertyNames.INIT_SEARCH_ACTION);
                    if(ae.size() == 0) {
                        sForm.getLocateReportDistributorForm().setLocateReportDistributorFl(true);
                    }
                }
                else {
                    HashMap vars = sForm.getFormVars();
                    if(vars == null){
                        vars = new HashMap();
                        sForm.setFormVars(vars);
                    }
                    vars.put(StorePortalForm.FORM_VAR_ALT_LOCATE,Boolean.FALSE);
                    ae = LocateStoreDistLogic.processAction(request,sForm, "initSearch");
                    if(ae.size()==0) {
                        sForm.getLocateStoreDistForm().setLocateDistFl(true);
                    }
                }

            }else if("Locate Manufacturer".equals(action)){
                HashMap vars = sForm.getFormVars();
                if(vars == null){
                    vars = new HashMap();
                    sForm.setFormVars(vars);
                }
                vars.put(StorePortalForm.FORM_VAR_ALT_LOCATE,Boolean.FALSE);
                ae = LocateStoreManufLogic.processAction(request,sForm, "initSearch");
                if(ae.size()==0) {
                    sForm.getLocateStoreManufForm().setLocateManufFl(true);
                }
            } else if("Locate User".equals(action)) {

                ae = LocateStoreUserLogic.processAction(request,sForm, "initSearch");
                if(ae.size()==0) {
                    sForm.getLocateStoreUserForm().setLocateUserFl(true);
                }
            } else if("Locate Freight Handler".equals(action)) {
                ae = LocateStoreFhLogic.processAction(request,sForm, "initSearch");
                if(ae.size()==0) {
                    sForm.getLocateStoreFhForm().setLocateFhFl(true);
                }
            } else if("Locate Service".equals(action)) {
                ae = LocateStoreServiceLogic.processAction(request,sForm, "initSearch");
                if(ae.size()==0) {
                    sForm.getLocateStoreServiceForm().setLocateServiceFl(true);
                }
            } else if("Locate Asset".equals(action)) {
                ae = LocateStoreAssetLogic.processAction(request,sForm, "initSearch");
                if(ae.size()==0) {
                    sForm.getLocateStoreAssetForm().setLocateAssetFl(true);
                }
            } else if("Locate Site".equals(action)|| "Locate Site".equals(command)) {
                if (RequestPropertyNames.getIsLocateReportSiteRequest(request)) {
                    ae = ReportingLocateStoreSiteLogic.processAction(request, sForm, LocatePropertyNames.INIT_SEARCH_ACTION);
                    if(ae.size() == 0) {
                        sForm.getReportingLocateStoreSiteForm().setReportingLocateStoreSiteFl(true);
                    }
                }
                else{
                    ae = LocateStoreSiteLogic.processAction(request,sForm, "initSearch");
                    if(ae.size() == 0) {
                        sForm.getLocateStoreSiteForm().setLocateSiteFl(true);
                    }
                }
            }

            else if("Xls Update".equals(action)){
                HashMap vars = sForm.getFormVars();
                if(vars == null){
                    vars = new HashMap();
                    sForm.setFormVars(vars);
                }
                vars.put(StorePortalForm.FORM_VAR_ALT_LOCATE,Boolean.FALSE);
                ae = LocateUploadItemLogic.processAction(request,sForm, "initSearch");
                if(ae.size()==0) {
                    sForm.getLocateUploadItemForm().setLocateUploadItemFl(true);
               }
            }else if("Clear Catalog Filter".equals(action)) {
            	if (RequestPropertyNames.getIsLocateReportCatalogRequest(request)) {
                    ae = LocateReportCatalogLogic.clearFilter(request, sForm);
                }
                else {
                	ae =LocateStoreCatalogLogic.clearFilter(request,sForm);
                }
            } else if("Clear Item Filter".equals(action)){
            	if (RequestPropertyNames.getIsLocateReportItemRequest(request)) {
                    ae = LocateReportItemLogic.clearFilter(request, sForm);
                }
                else {
                	ae = LocateStoreItemLogic.clearFilter(request,sForm);
                }
            } else if("Clear Account Filter".equals(action)) {
                if (RequestPropertyNames.getIsLocateReportAccountRequest(request)) {
                    ae = LocateReportAccountLogic.clearFilter(request, sForm);
                }
                else {
                    ae = LocateStoreAccountLogic.clearFilter(request, sForm);
                }
            } else if(LocatePropertyNames.CLEAR_ORDER_GUIDE_FILTER_ACTION.equals(action)) {
            	ae = LocateStoreOrderGuideLogic.clearFilter(request,sForm);
            } else if(LocatePropertyNames.CLEAR_STORE_GROUP_FILTER_ACTION.equals(action)) {
            	ae = LocateStoreGroupLogic.clearFilter(request, sForm);
            } else if("Clear Distributor Filter".equals(action)){
                ae = LocateStoreDistLogic.clearFilter(request,sForm);
            } else if("Clear Manufacturer Filter".equals(action)){
                ae = LocateStoreManufLogic.clearFilter(request,sForm);
            }else if("Clear User Filter".equals(action)){
                ae = LocateStoreUserLogic.clearFilter(request,sForm);
            }else if("Clear Freight Handler Filter".equals(action)){
                ae = LocateStoreFhLogic.clearFilter(request,sForm);
            }else if("Clear Site Filter".equals(action)){
                if (RequestPropertyNames.getIsLocateReportSiteRequest(request)) {
                    ae = ReportingLocateStoreSiteLogic.clearFilter(request, sForm);
                }
                else {
                    ae = LocateStoreSiteLogic.clearFilter(request, sForm);
                }
            }else if("Clear Service Filter".equals(action)){
                ae = LocateStoreServiceLogic.clearFilter(request,sForm);
            }else if("Clear Asset Filter".equals(action)){
                ae = LocateStoreAssetLogic.clearFilter(request,sForm);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }

        return null;
    }

    	public void logm(String m) {
    		log.info(this.getClass().getName() + " " + m + " " +  new java.util.Date() );

    	}

        public void saveErrorsAndMessages(HttpServletRequest request , ActionMessages pActionMessages) {
            if(pActionMessages==null) {
                return;
            }
            ActionErrors ae = new ActionErrors();
            ActionMessages am = new ActionMessages();
            boolean errorFl = false;
            boolean messFl = false;
            for(Iterator iter = pActionMessages.properties(); iter.hasNext();) {
                String prop = (String) iter.next();
                for(Iterator iter1 = pActionMessages.get(prop); iter1.hasNext();) {
                    ActionMessage mm = (ActionMessage) iter1.next();
                    if(mm instanceof ActionError) {
                        errorFl = true;
                        ae.add(prop,(ActionError) mm);
                    } else {
                        messFl = true;
                        am.add(prop,mm);
                    }
                }
            }
            if(errorFl) {
                saveErrors(request,ae);
            }
            if(messFl) {
                saveMessages(request,am);
            }
        }
        /*
        public void saveMessages(HttpServletRequest request , ActionMessages pActionMessages) {
             // Remove any messages attribute if none are required
             if ((pActionMessages == null) || pActionMessages.isEmpty()) {
                 request.removeAttribute(Globals.MESSAGE_KEY);
                 return;
             }

             // Save the messages we need
             request.setAttribute(Globals.MESSAGE_KEY, pActionMessages);

        }
       */
      //=============================================================================//
      private static String unLocked = "0";
//      private static String locked = "1";
      public static void unLockAction(HttpServletRequest request, String sessionAttrName) {
          HttpSession session = request.getSession();
          session.setAttribute(sessionAttrName,  unLocked);
      	  log.info("ActionSuper *********   UN Lock : "+sessionAttrName + "="+ session.getAttribute(sessionAttrName));
      }

      public static boolean lockAction(HttpServletRequest request, String sessionAttrName, String lockedParams, ActionErrors ae, String errorMessageKey) {
                  String masterLock = System.getProperty("MASTER_LOCK_DISABLE");
                  if (Utility.isTrue(masterLock)) {
                          return true;
                  }
                  HttpSession session = request.getSession();
                  String lockValue = null;
                  try {
                          synchronized (session) {
                                  lockValue = (String) session.getAttribute(sessionAttrName);
                                  if (lockValue == null) {
                                    lockValue = unLocked;
                                    session.setAttribute(sessionAttrName,  lockValue);
                                  }
                          }
                          log.info("ActionSuper *********   Lock : "+sessionAttrName + "="+ lockValue);
                          if (lockValue.equals(unLocked)) {
                            lockValue = lockedParams;
                            session.setAttribute(sessionAttrName, lockValue);
                            return true;
                          } else {

                                  // The lock value is !=0.  Meaning an action is
                                  // already in progress.
                                  return false;
                          }
                  } catch (Exception e) {
                          e.printStackTrace();
                          lockValue = unLocked;
                          session.setAttribute(sessionAttrName,  lockValue);
                          return false;
                  }

        }

}




