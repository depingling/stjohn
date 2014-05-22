package com.cleanwise.service.api.util;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.ServiceDAO;
import com.cleanwise.service.api.dao.UniversalDAO;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.view.utils.Constants;

public class CacheManager {
	
	private static final Logger log = Logger.getLogger(CacheManager.class);

    private static java.util.Date mPointInTime;
    private static String kVarTag = "CacheManager.mCacheVersion",
	kVarTagReq = "CacheManager.mCacheReqs";;

    public CacheManager( String pCacheHolder) {
	mPointInTime = new java.util.Date();
    }

    private static int getVersion() {
	return getV(kVarTag);
    }
    private static int getCacheReqCount() {
	return getV(kVarTagReq);
    }

    private static int getV(String pTag) {
	String t = System.getProperty(pTag);
	int v = 0;
	try {
	    if ( null != t && t.length() > 0 ) {
		v = Integer.parseInt(t);
	    }
	} catch (Exception e) {}
	return v;
    }


    private   boolean isCacheOld() {
	java.util.Date n = new java.util.Date();
	// 5+ minutes is too old
	if (( n.getTime() - mPointInTime.getTime()) > 300000 ) {
	    log(" = cache is too old, mPointInTime=" + mPointInTime);
	    return true;
	}

	// Has cache was rolled over?
	if (getVersion() != mMyCacheVersion) {
	    log(" cache is too old, this version=" + mMyCacheVersion
		+ " global version: " + getVersion() );
	    return true;
	}

        // Cached data is still useable.
	return false;
    }

    private   ProductDAO mProductDAO = null;
    private   BusEntityDAO mBusEntityDAO = new BusEntityDAO();
    private   ServiceDAO mServiceDAO=null;

    private   void checkCache() {

	CacheManager.mCacheRequests = new
	    Integer(getCacheReqCount() + 1 );

	if (isCacheOld()) {
	    CacheManager.mCacheRequests = new
		Integer(0);
	    mProductDAO = null;
	    mBusEntityDAO = null;
	    mUserscache = null;
	    mCachedUserMap = null;
        mServiceDAO = null;
        mPointInTime = new java.util.Date();
        System.gc();
	}

	if (null == mProductDAO ) {
	    mProductDAO = new ProductDAO();
	}
    if (null == mServiceDAO ) {
	    mServiceDAO = new ServiceDAO();
	}
    if ( null == mBusEntityDAO ) {
	    mBusEntityDAO = new BusEntityDAO();
	}

	if ( null == mUserscache ) {
	    mUserscache = new java.util.Hashtable();
	}
	if ( null == mCachedUserMap ) {
	    mCachedUserMap = new java.util.HashMap();
	}

	mMyCacheVersion = getVersion();
	System.setProperty(kVarTagReq, String.valueOf
			   (CacheManager.mCacheRequests));

    }

    public ProductDAO getProductDAO() {
	checkCache();
	return mProductDAO;
    }

    public ServiceDAO getServiceDAO() {
	checkCache();
	return mServiceDAO;
    }

    public BusEntityDAO getBusEntityDAO() {
	checkCache();
	if ( mMyCacheVersion > 0 ) {
	    log("getBusEntityDAO: " + " mMyCacheVersion=" + mMyCacheVersion);
	}
	return mBusEntityDAO;
    }


    private   java.util.Hashtable mUserscache = null;
    public UserInfoData getCachedUser(User userBean, int pUserId)
        throws Exception {

	checkCache();

        Integer k = new Integer(pUserId);
        UserInfoData u = null;
	if ( mUserscache.containsKey(k)){
	    u = (UserInfoData)mUserscache.get(k);
	    log(" userscache HAS " + k);
	} else {
//NG	    u = userBean.getUserContact(pUserId);
            u = userBean.getUserContactForNotification(pUserId);
	    mUserscache.put(k,u);
	    log(" userscache ADDED " + k);
	}
	return u;
    }

    private   java.util.Hashtable mSitescache = null;
    public SiteData getCachedSite(Site siteBean, int pSiteId)
        throws Exception {

	checkCache();

        Integer k = new Integer(pSiteId);
        SiteData s = null;
	if ( mSitescache.containsKey(k)){
	    s = (SiteData)mSitescache.get(k);
	    log(" sitescache HAS " + k);
	} else {
	    s = siteBean.getSite(pSiteId);
	    mSitescache.put(k,s);
	    log(" sitescache ADDED " + k);
	}
	return s;
    }


    private static  void log(String l) {
	log.info("=cm= CacheManager: " + l);
    }

    private   long mMyCacheVersion;
    public static Integer mCacheRequests = new Integer(0);

    public static  String getCacheInfo() {
	return "Cache Manager: report date " + new java.util.Date()
	    + "\n cache version    = " + getVersion()
	    + "\n cache date       = " + CacheManager.mPointInTime
	    + "\n cache requests   = " + getCacheReqCount()
	    ;
    }

    public static  String resetCache() {
        return    rollCacheVersion();
    }

    public static  String rollCacheVersion() {

	CacheManager.mPointInTime = new java.util.Date();
	CacheManager.mCacheRequests = new Integer(0);

	int v = -1;
	synchronized (CacheManager.mPointInTime) {
	    v = getVersion();
	    if ( v > 1000*1000 ) {
		v = 0;
	    } else {
		v++;
	    }
	    System.setProperty(kVarTag, String.valueOf(v));
	    System.setProperty(kVarTagReq, String.valueOf
			       (CacheManager.mCacheRequests));
	}

	String msg= "Flushed cached data\n" + getCacheInfo();
	log( msg );
	return msg;
    }

    private   java.util.HashMap mCachedUserMap = null;
    public UserData getCachedUserForOrder(java.sql.Connection conn,
					   OrderData orderStatusD )
        throws Exception    {

	checkCache();

	String k = "" + orderStatusD.getAddBy()
	    + ":" + orderStatusD.getStoreId();

	if ( null == k ) {
	    return null;
	}

	if ( mCachedUserMap.containsKey(k) ) {
	    return (UserData)mCachedUserMap.get(k) ;
	}

	try {
		String userName = orderStatusD.getAddBy();
		int userSeperatorIndex = userName.indexOf(Constants.FORWARD_SLASH);
		UserData ud = null;
		if (userSeperatorIndex < 0) {
			ud = UniversalDAO.getUserByName(conn, userName, orderStatusD.getStoreId());
		}
		else {
			//STJ-4905
			//get the information for the primary user
			ud = UniversalDAO.getUserByName(conn, userName.substring(0, userSeperatorIndex), orderStatusD.getStoreId());
			//get the information for the proxy user
			UserData proxyUser = UniversalDAO.getUserByName(conn, userName.substring(userSeperatorIndex + 1), orderStatusD.getStoreId());
			//update the information for the primary user to include the proxy user information as well
			ud.setFirstName(ud.getFirstName() + " " + ud.getLastName());
			ud.setLastName(" (" + proxyUser.getFirstName() + " " + proxyUser.getLastName() + ")");
		}
	    log("getCachedUserByName Adding k=" + k);
	    mCachedUserMap.put(k,ud);
	    return ud;
	} catch (DataNotFoundException e) {
	    log("DNFE getCachedUserByName Adding k=" + k);
	    mCachedUserMap.put(k,null);
	    throw e;
	}

    }


}

