package com.cleanwise.view.utils;

import java.io.InputStream;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

/**
 * The helper class <code>SessionTool</code> provides
 * access to session vaiable values.
 *
 * @author <a href="mailto:dvieira@cleanwise.com"></a>
 */
public class SessionTool {
    private static final Logger log = Logger.getLogger(SessionTool.class);
    
    public static final String COOKIE_ORIG_IP = "ORIG_IP";
    public static final String COOKIE_SERVER_KEY = "ServerKey";
    public static final String COOKIE_EXTERNAL_LOGON = "externalLogon";
    //all cookies should be added to the blow list as when dealing
    //with url redirects accross domains they need to be encoded (no problem
    //just grab all of them) but then they need to be retrieved from the query
    //string which means you need to know about them.
    public static List KNOWN_COOKIES = new ArrayList();
    static{
        KNOWN_COOKIES.add(COOKIE_EXTERNAL_LOGON);
        KNOWN_COOKIES.add(COOKIE_ORIG_IP);
        KNOWN_COOKIES.add(COOKIE_SERVER_KEY);
    }

    HttpServletRequest mRequest;

    /**
     * Creates a new <code>SessionTool</code> instance.
     *
     * @param pReq a <code>HttpServletRequest</code> value
     */
    public SessionTool(HttpServletRequest pReq) {
        mRequest = pReq;
    }

    /**
     * Describe <code>getLoginName</code> method here.
     *
     * @return a <code>String</code> value
     */
    public String getLoginName() {
        String ln = (String)mRequest.getSession().
                getAttribute(Constants.USER_NAME);
        if (null == ln) {
            ln = "";
        }
        return ln;
    }

    public CleanwiseUser getUserData() {
        CleanwiseUser cwu = (CleanwiseUser)mRequest.getSession().
                getAttribute(Constants.APP_USER);
        if ( null == cwu ) {
            return new CleanwiseUser();
        }
        return cwu;
    }

    public APIAccess getFactory() {
        HttpSession sess = mRequest.getSession();
        return (APIAccess)(sess.getAttribute(Constants.APIACCESS));

    }
    public boolean checkSession() {

        HttpSession sess = mRequest.getSession();

        if ((sess == null) ||
                (sess.getAttribute(Constants.APIACCESS) == null)
                || (sess.getAttribute(Constants.APP_USER) == null)) {
            return false;
        }

        CleanwiseUser cwu = (CleanwiseUser)mRequest.getSession().
                getAttribute(Constants.APP_USER);
        if ( cwu.getUser() == null ) {
            return false;
        }

        if ( cwu.getUser().getUserId() == 0 ) {
            return false;
        }

        return true;
    }

    public String paramDebugString() {
        Enumeration paramNames = mRequest.getParameterNames();
        ArrayList pnames = new ArrayList();

        while(paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement();
            pnames.add(paramName);
        }
        Collections.sort(pnames);
        String tostr = "";
        for (int ix = 0; ix < pnames.size(); ix++) {
            String paramName = (String)pnames.get(ix);
            tostr += "\n        " + paramName + "=" +
                    mRequest.getParameter(paramName);
        }
        return tostr;
    }

    private static String kSiteSettings = "SITE_SETTINGS";

    public static SiteSettingsData getSiteSettings
            (HttpServletRequest request,int pSiteId) {
        Hashtable siteSettings = (Hashtable)
        request.getSession().getAttribute(kSiteSettings);

        if ( siteSettings == null ) {
            return null;
        }

        return (SiteSettingsData)siteSettings.get(new Integer(pSiteId));
    }

    public SiteSettingsData mkSiteSpecificData
            (HttpServletRequest request, SiteData pSiteData) {
        if (getSiteSettings(request, pSiteData.getSiteId()) == null ) {
            SiteSettingsData ssd = new SiteSettingsData(pSiteData);
            setSiteSettings(request, ssd);
        }
        return getSiteSettings(request, pSiteData.getSiteId());
    }

    public void setSiteSettings( HttpServletRequest request,
            SiteSettingsData v) {
        if (null == v) return;

        Hashtable siteSettings = (Hashtable)
        request.getSession().getAttribute(kSiteSettings);

        if ( siteSettings == null ) {
            siteSettings = new Hashtable(1);
        }
        Integer sid = new Integer(v.getSiteData().getSiteId());
        siteSettings.put(sid, v);
        request.getSession().setAttribute(kSiteSettings, siteSettings);
    }

    public static StoreData getStoreData
            (HttpServletRequest request,
            int storeid) throws Exception {
        String k = "STORE:" + storeid;
        if (request.getSession().getAttribute(k) == null ) {
            APIAccess factory = new APIAccess();
            Store storeBean = factory.getStoreAPI();
            StoreData store_d = storeBean.getStore(storeid);
            request.getSession().setAttribute(k, store_d);
        }
        return (StoreData)request.getSession().getAttribute(k);
    }

    public static StoreData getStoreDataNoCache(HttpServletRequest request, int pStoreId) throws Exception {
        String k = "STORE:" + pStoreId;
        if (request.getSession().getAttribute(k) != null) {
            log.info("REMOVE CACHE for: " + k);
            request.getSession().removeAttribute(k);
        }

        return getStoreData(request, pStoreId);
    }

    public static AccountData getAccountData
            (HttpServletRequest request,
            int pAccountId) throws Exception {
        String kacct = "ACCOUNT:" + pAccountId;
        if (request.getSession().getAttribute(kacct) == null ) {
            APIAccess factory = new APIAccess();
            Account acctBean = factory.getAccountAPI();
            AccountData ad = acctBean.getAccount(pAccountId, 0);
            request.getSession().setAttribute(kacct, ad);
        }
        return (AccountData)request.getSession().getAttribute(kacct);
    }

    public static AccountData getAccountDataNoCache(HttpServletRequest request,
                                                    int pAccountId) throws Exception {
        String k = "ACCOUNT:" + pAccountId;
        if (request.getSession().getAttribute(k) != null) {
            log.info("REMOVE CACHE for: " + k);
            request.getSession().removeAttribute(k);
        }

        return getAccountData(request, pAccountId);
    }

    public static DistributorData getDistributorData(HttpServletRequest request, int pDistributorId) throws Exception {
        String key = "DISTRIBUTOR:" + pDistributorId;
        DistributorData d = (DistributorData) request.getSession().getAttribute(key);
        if (d == null ) {
            APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
            Distributor bean = factory.getDistributorAPI();
            d = bean.getDistributor(pDistributorId);
            request.getSession().setAttribute(key, d);
        }
        return d;
    }

    public static void setSiteData
            (HttpServletRequest request,
            SiteData  pSiteData) throws Exception {

        if ( null == pSiteData ) { return ; }

        String k = "SITE:" + pSiteData.getSiteId();
        request.getSession().setAttribute(k, pSiteData);
    }

    public static void setSiteData
            (HttpServletRequest request, SiteDataVector siteDV)
            throws Exception {
        for ( int i = 0; null != siteDV && i < siteDV.size(); i++ ) {
            setSiteData(request, (SiteData)siteDV.get(i));
        }
    }

    public static SiteData getSiteData
            (HttpServletRequest request,
            int pSiteId) throws Exception {
        String k = "SITE:" + pSiteId;
        if (request.getSession().getAttribute(k) == null ) {
            APIAccess factory = new APIAccess();
            Site siteBean = factory.getSiteAPI();
            SiteData sd = siteBean.getSite(pSiteId, 0, false,SessionTool.getCategoryToCostCenterView(request.getSession(),pSiteId));
            request.getSession().setAttribute(k, sd);
        }
        return (SiteData)request.getSession().getAttribute(k);
    }

    public static SiteData getSiteDataNoCache
            (HttpServletRequest request,
            int pSiteId) throws Exception {
        String k = "SITE:" + pSiteId;
        if (request.getSession().getAttribute(k) != null ) {
            log.info("REMOVE CACHE for: " + k );
            request.getSession().removeAttribute(k);
        }
        return getSiteData(request,pSiteId);

    }

    /**
     *retrieves the requested URI from the request.
     *@returns the requested uri. ex: /cleanwise/userportal/logon.do
     */
    public static String getActualRequestedURI(HttpServletRequest request){
        org.apache.struts.action.ActionMapping mActionConfig =
                (org.apache.struts.action.ActionMapping) request.getAttribute("org.apache.struts.action.mapping.instance");
        if(mActionConfig==null){
            //we are not in struts framework...simple jsp page or some other framework present
            return request.getRequestURI();
        }
        String mappingPath = mActionConfig.getPath();
        return request.getContextPath()+mappingPath+".do";
    }

    /**
     *retrieves the requested URI from the request sans the context.
     *@returns the requested uri. ex struts mapping: /userportal/logon.do
     */
    public static String getActualRequestedURIStrutsMapping(HttpServletRequest request){
    	org.apache.struts.action.ActionMapping mActionConfig =
            (org.apache.struts.action.ActionMapping) request.getAttribute("org.apache.struts.action.mapping.instance");
	    if(mActionConfig==null){
	        //we are not in struts framework...simple jsp page or some other framework present
	        return request.getRequestURI();
	    }
	    String mappingPath = mActionConfig.getPath();
	    return mappingPath+".do";
    }
    /**
     *retrieves the requested URI page for the request.
     *@returns the requested uri page. ex: logon.do
     */
    public static String getActualRequestedURIPage(HttpServletRequest request){
        String fullURI = getActualRequestedURI(request);
        if(fullURI == null){
            return fullURI;
        }
        int idx = fullURI.lastIndexOf("/");
        if(idx > 0){
            fullURI = fullURI.substring(idx+1);
        }

        return fullURI;
    }

    /**
     *
     * @param pUrl URL or Request string
     * @param pRemoveParam the request param name to remove
     * @return
     */
    public static String removeRequestParameter(String pUrl, String pRemoveParam){
    	StringBuffer pBuf = new StringBuffer(pUrl);

        int lSubStrLen=pRemoveParam.length();
        if ( pRemoveParam.equals(pUrl) || pBuf.length() <= 0 ){
            return "";
        }

        int origLen = pBuf.length();
        for(int i= origLen - 1; i >= 0; i--){
            if ( i+lSubStrLen > origLen ) continue;
            if ( i+lSubStrLen > pBuf.length() ) continue;
            String lSubStr = pBuf.substring(i,i+lSubStrLen);
            if (lSubStr.equals(pRemoveParam)){
                int deleteTo = pBuf.indexOf("&",i+lSubStrLen);
                if(deleteTo < 0){
                	deleteTo = origLen;
                }else{
                	deleteTo++;
                }
                pBuf.delete(i,deleteTo);
            }
        }
        return pBuf.toString();
    }

    /**
     *retrieves the requested URI from the request that was used to find the action mapping.
     *This is the getActualRequestedURI(request) with the context striped out as is the extension and any extranious
     *parameters at the end of throws request.<br>
     *For example for a request of:
     *http://somehost/cleanwise/myMapping.do?param1=5
     *this method will return:
     *myMapping
     */
    public static String getActualRequestedStrutsMapping(HttpServletRequest request){
        String s = getActualRequestedURI(request).replaceAll(request.getContextPath(), "");
        int chop = s.lastIndexOf(".do");
        if(chop < 0){
            chop = s.lastIndexOf(".jsp");
        }
        if(chop > 0){
            return s.substring(0, chop);
        }
        return s;
    }

    /**
     *Translates the status code based off the ref code names.  Add in support for additional status codes as needed.  Does not
     *currently care what type of status is used, but is more generatl in nature.  ACTIVE always becomes ACT for example.
     */
    public static String xlateAdminStatus(String status, HttpServletRequest request){
        if(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(status)){
            return "CANC";
        }else if(RefCodeNames.ORDER_ITEM_STATUS_CD.INVOICED.equals(status)){
            return "INV";
        }else if(RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_ERP_PO.equals(status)){
            return "PEND ERP";
        }else if(RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_FULFILLMENT.equals(status)){
            return "PEND FULL";
        }else if(RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_REVIEW.equals(status)){
            return "REVIEW";
        }else if(RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_ERROR.equals(status)){
            return "ACK ERR";
        }else if(RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_REJECT.equals(status)){
            return "ACK REJ";
        }else if(RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_SUCCESS.equals(status)){
            return "ACK SUC";
        }else if(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR.equals(status)){
            return "SENT";
        }else if(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED.equals(status)){
            return "SENT FAIL";
        }else if(RefCodeNames.INVOICE_STATUS_CD.CANCELLED.equals(status)){
            return "CANC";
        }else if(RefCodeNames.INVOICE_STATUS_CD.CLW_ERP_PROCESSED.equals(status)){
            return "PROC";
        }else if(RefCodeNames.INVOICE_STATUS_CD.DIST_SHIPPED.equals(status)){
            return "DSHIP";
        }else if(RefCodeNames.INVOICE_STATUS_CD.DUPLICATE.equals(status)){
            return "DUP";
        }else if(RefCodeNames.INVOICE_STATUS_CD.ERP_GENERATED.equals(status)){
            return "GEN";
        }else if(RefCodeNames.INVOICE_STATUS_CD.ERP_GENERATED_ERROR.equals(status)){
            return "ERR";
        }else if(RefCodeNames.INVOICE_STATUS_CD.ERP_REJECTED.equals(status)){
            return "ERR";
        }else if(RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED.equals(status)){
            return "REL";
        }else if(RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED_ERROR.equals(status)){
            return "ERR";
        }else if(RefCodeNames.INVOICE_STATUS_CD.MANUAL_INVOICE_RELEASE.equals(status)){
            return "REL";
        }else if(RefCodeNames.INVOICE_STATUS_CD.PENDING.equals(status)){
            return "PEND";
        }else if(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW.equals(status)){
            return "REV";
        }else if(RefCodeNames.INVOICE_STATUS_CD.PROCESS_ERP.equals(status)){
            return "PROC";
        }else if(RefCodeNames.INVOICE_STATUS_CD.REJECTED.equals(status)){
            return "REJ";
        }
        return status;
    }

    // Return this hostname and the port used for the request.
    public String getInternalHostToken() throws Exception {

        String p = System.getProperty("com.cleanwise.baseport");
        if ( p == null || p.length() == 0 ) {
            p = "8080";
            log.info("  setting com.cleanwise.baseport=" +
                    p );
        }
        return java.net.InetAddress.getLocalHost().getHostName() +
                ":" + p ;
    }

    Locale mLocale;
    /**
     *Gets the locale code for this user
     */
    public Locale getUserLocaleCode(HttpServletRequest request){
        try{
            Locale loc=null;
            String locS=null;
            try{
                locS = (String) request.getParameter("Locale");
                loc = com.cleanwise.service.api.util.Utility.parseLocaleCode(locS);
            }catch(Exception e){
                log.info("Could not parse locae from request: "+locS);
            }
            if(loc == null){
                loc = Locale.US;
            }
            return getUserData().getUserLocaleCode(loc);
        }catch(Exception e){
            e.printStackTrace();
            return  Locale.US;
        }
    }

    public String mkCookieInfoString() {
        String cstatus = "";
        javax.servlet.http.Cookie[] cs = mRequest.getCookies();
        for ( int i = 0; null != cs && i < cs.length; i++ ) {
            javax.servlet.http.Cookie thisCookie = cs[i];
            cstatus += " <Cookie[" + i + "]" +
                    " Name=" + thisCookie.getName() +
                    " Value=" + thisCookie.getValue() + "> ";
        }
        return cstatus;

    }

    public String getLogonMapping() {
	return "/userportal/logon";
    }

        private static String kAccountSettings = "Account_SETTINGS";

    public static AccountSettingsData getAccountSettings
            (HttpServletRequest request,int pAccountId) {
        Hashtable AccountSettings = (Hashtable)
        request.getSession().getAttribute(kAccountSettings);

        if ( AccountSettings == null ) {
            return null;
        }

        Integer sid = new Integer(pAccountId);
        return (AccountSettingsData)AccountSettings.get(new Integer(pAccountId));
    }

    public AccountSettingsData refreshAccountSettings(HttpServletRequest request, AccountData pAccountData) {
        Hashtable accountSettings = (Hashtable) request.getSession().getAttribute(kAccountSettings);
        if (accountSettings != null && accountSettings.containsKey(pAccountData.getAccountId())) {
            accountSettings.remove(pAccountData.getAccountId());
        }
        return mkAccountSpecificData(request, pAccountData);
    }

    public AccountSettingsData mkAccountSpecificData
            (HttpServletRequest request, AccountData pAccountData) {

        if (getAccountSettings(request, pAccountData.getAccountId()) == null ) {
            AccountSettingsData ssd = new AccountSettingsData(pAccountData);
            setAccountSettings(request, ssd);
        }
        return getAccountSettings(request, pAccountData.getAccountId());
    }

    public void setAccountSettings( HttpServletRequest request, AccountSettingsData v) {
        if (null == v) return;

        Hashtable AccountSettings = (Hashtable)
        request.getSession().getAttribute(kAccountSettings);

        if ( AccountSettings == null ) {
            AccountSettings = new Hashtable(1);
        }
        Integer sid = new Integer(v.getAccountData().getAccountId());

        AccountSettings.put(sid, v);
        request.getSession().setAttribute(kAccountSettings, AccountSettings);
    }

    public String mkReqInfoString(int pMaxInfoLen) {
	// List out all parameters, including form parameters.
	Enumeration paramNames = mRequest.getParameterNames();
	String dbp = "  ReqParamStart [";

	while (paramNames.hasMoreElements()) {

	    if ( pMaxInfoLen > 0 && dbp.length() > pMaxInfoLen ) {
		// protect against long requests.
		dbp += " ...";
		break;
	    }

	    String paramName = (String) paramNames.nextElement();
	    String[] paramValues = mRequest.getParameterValues(paramName);

	    if ( null == paramName) continue;

	    if (paramName.indexOf("quantityString") > 0 &&
		paramValues.length == 1 &&
		paramValues[0] != null &&
		((String)paramValues[0]).length() == 0 ) {
		// a lot of quantity strings are sent
		// from order guide shopping.
		// do not log these.
		continue;
	    }

        // STJ-4512
        if (paramName.indexOf("password")>=0 ) {
            continue;
        }
        if (paramName.indexOf("username")>=0 ) {
            continue;
        }


	    dbp += "\n   " + paramName;

   
	    if (paramName.indexOf("ccNumber") >= 0 ) {
		dbp += "=###";
		continue;
	    }

	    if (paramValues.length == 1) {
		String paramValue = paramValues[0];
		if (paramValue.length() == 0)
		    dbp += "=null";
		else
		    dbp += "=" + paramValue;
	    } else {
		dbp += " (multi) = ";
		for (int i = 0; i < paramValues.length; i++) {
		    dbp += " " + paramValues[i];
		}
	    }
	}
	dbp += "\n] ReqParamEnd";
	return dbp;
    }

    /**
     * Returns the requested domain.  Allows for a requestedHost parameter
     * to overide the actual requested host.
     * @param request
     * @return
     */
    public static String getRequestedDomain(HttpServletRequest request){
    	String domain = request.getParameter(Constants.REQUESTED_DOMAIN);
    	if(domain != null){
    		return domain;
    	}
    	return request.getServerName();
    }
    
    /**
     * This is cached ONCE per session!  This is important as this may change between requests due to SSL
     * redirection, if cached once per domain the user may be switched to a different store because SSL was
     * not enabled in the other stores
     */
    public static ApplicationDomainNameData getApplicationDomainData(HttpServletRequest request)
    throws Exception{
    	HttpSession session = request.getSession();
    	ApplicationDomainNameData domainData = (ApplicationDomainNameData) session.getAttribute(Constants.APPLICATION_DOMAIN);
    	if(domainData == null){
	    	APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
	    	if(factory == null){
	    		factory = new APIAccess();
	    	}
	    	String domain = getRequestedDomain(request);
            Request requestEjb = factory.getRequestAPI();
			domainData = requestEjb.getApplicationDomain(domain);
            if(domainData == null){
                log.info("Could not find configured application domain for requested domain: "+domain);
                ApplicationDomainNameData scratchDomainData = requestEjb.getDefaultApplicationDomain();
                //init the domeain to match the request if using the default
                domainData = ApplicationDomainNameData.createValue();
                domainData.setApplicationDomainName(BusEntityData.createValue());
                domainData.getApplicationDomainName().setShortDesc(request.getServerName());
                domainData.setSslDomainName(request.getServerName());
                domainData.setDefaultDomain(true);
                domainData.setDefaultStore(scratchDomainData.getDefaultStore());
            }
			session.setAttribute(Constants.APPLICATION_DOMAIN,domainData);
    	}
		return domainData;
    }

    public static void setCookie(HttpServletResponse response, HttpServletRequest request,String name, String value, String optDefaultPath){
	    if(request == null){return;}
		try{
	        Cookie[] cc = request.getCookies();
			if(cc != null){
		        for(int i=0;i<cc.length;i++){
		            Cookie c = cc[i];
		            if(name.equalsIgnoreCase(c.getName())){
		                c.setValue(value);
		                response.addCookie(c);
		                return;
		            }
		        }
			}
	        Cookie cok = new Cookie(name, value);
	        cok.setSecure(request.isSecure());
	        
	        if(optDefaultPath != null){
	            cok.setPath(optDefaultPath);
	        }else{
	            //this violates the servlet spec if spec if it is jsession id
	            //as it means the session may be shared accross multiple
	            //contexts
	            cok.setPath("/");
	        }
	        response.addCookie(cok);
		}catch(Exception e){}
    }
    public static AccCategoryToCostCenterView getCategoryToCostCenterView
          (HttpSession pSession, int pSiteId) throws Exception {
        return getCategoryToCostCenterView ( pSession,  pSiteId, 0) ;
      }

    public static AccCategoryToCostCenterView getCategoryToCostCenterView
         (HttpSession pSession, int pSiteId, int pSiteCatalogId ) throws Exception {
       log.info("[SessionTool].getCategoryToCostCenterView()==> BEGIN " );

     String attrName = "CategoryToCostCenterView" ;
     AccCategoryToCostCenterView categToCCView = (AccCategoryToCostCenterView)pSession.getAttribute(attrName);
     APIAccess factory = new APIAccess();
     Account acctBean = factory.getAccountAPI();

     boolean toUpdate = !acctBean.checkCostCentersForSite(pSiteId, pSiteCatalogId,categToCCView);

     if ( categToCCView == null || toUpdate) {
       if (pSiteId > 0) {
         categToCCView = acctBean.getCategoryToCostCenterView(pSiteId);
       } else if (pSiteCatalogId > 0){
          categToCCView = acctBean.getCategoryToCostCenterViewByCatalog(pSiteCatalogId);
       }
       pSession.setAttribute(attrName, categToCCView);
     }
     log.info("[SessionTool].getCategoryToCostCenterView()==> END ;" + ((categToCCView!= null)?("accountId =" + categToCCView.getAccountId()):" NULL") );

     return (AccCategoryToCostCenterView)pSession.getAttribute(attrName);
 }

 public static AccCategoryToCostCenterView getCategoryToCostCenterViewByAcc
      (HttpSession pSession, int pAccId ) throws Exception {
    log.info("[SessionTool].getCategoryToCostCenterViewByAcc()==> BEGIN " );
    String attrName = "CategoryToCostCenterView" ;
    AccCategoryToCostCenterView categToCCView = (AccCategoryToCostCenterView)pSession.getAttribute(attrName);
    APIAccess factory = new APIAccess();
    Account acctBean = factory.getAccountAPI();
    boolean toUpdate = (categToCCView != null )? (pAccId >0 && pAccId != categToCCView.getAccountId()): true ;
    if (toUpdate){
       categToCCView = acctBean.getCategoryToCostCenterViewByAccount(pAccId);
       pSession.setAttribute(attrName, categToCCView);
     }

    log.info("[SessionTool].getCategoryToCostCenterViewByAcc()==> END ;" + ((categToCCView!= null)?("accountId =" + categToCCView.getAccountId()):" NULL") );

    return (AccCategoryToCostCenterView)pSession.getAttribute(attrName);

  }


  public static LanguageDataVector getUserAvailableLanguages(HttpSession session)  throws Exception {
        APIAccess factory = new APIAccess();
    	Language languageBean = factory.getLanguageAPI();
    	LanguageDataVector result = null;
    	Store storeBean = factory.getStoreAPI();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        StoreProfileDataVector storeProfileDV = storeBean.getStoreProfile(appUser.getUserStore().getStoreId());
        if (storeProfileDV!=null){
            	List selectedLanguageIds = new ArrayList();
            	Iterator it = storeProfileDV.iterator();
            	while(it.hasNext()){
            		StoreProfileData prof = (StoreProfileData)it.next();
                    if(RefCodeNames.STORE_PROFILE_TYPE_CD.LANGUAGE_OPTION.equals(prof.getOptionTypeCd())){
	          		    if (Utility.isSet(prof.getShortDesc())) {
	          			    selectedLanguageIds.add(prof.getShortDesc());
                        }
	          		}
                }
            	if (selectedLanguageIds != null && selectedLanguageIds.size() > 0) {
                    result = languageBean.getLanguagesInList(selectedLanguageIds);
                }
        }
        if (result == null || result.size() == 0) {
            result = languageBean.getSupportedLanguages();
        }
        LanguageData pigLatinLanguage = languageBean.getLanguageByLanguageCode(RefCodeNames.LOCALE_CD.XX_PIGLATIN);
        result.add(pigLatinLanguage);
        if (result != null && !result.isEmpty()) {
        	DisplayListSort.sort(result, "uiName");
        }
        return result;
    	

  }
  public static boolean isNewUI(HttpServletRequest request){
	  boolean isNewUI = false;
	  if (request.getRequestURI().contains(Constants.GLOBAL_ESW_UI)){
		  isNewUI =true;
	  }
	  return isNewUI;
  }
}
