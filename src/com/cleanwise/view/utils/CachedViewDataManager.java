package com.cleanwise.view.utils;

import java.rmi.RemoteException;
import java.util.HashMap;

import javax.naming.NamingException;

import org.apache.log4j.Category;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.RefCdData;
import com.cleanwise.service.api.value.RefCdDataVector;


/**
 * Very simple cache manager.  This class is suitable for storing non voilitile data.
 * There is no exipry for the data in the cache, it is assumed that only content that
 * is semi static is included.  Provisions are made for a seperate voilitile cache as well.
 *
 */
public class CachedViewDataManager{
	private static final Category log = Category.getInstance(CachedViewDataManager.class);

	private static CachedViewDataManager cache; //the cache
	private HashMap cacheMap; //internal hashmap actually used to store cache
	private static APIAccess factory; //access to EJB


	/**
	 * Private constructor.  Static methods getCacheManagerViewNonVolitile should
	 * be used to create a new CachedViewDataManager.  This is a singlton class.
	 */
	private CachedViewDataManager(){
		cacheMap = new HashMap();
	}

	/**
	 * Flushes the cache.  Anything stored in cache will be removed
	 */
	public static void flushCache(){
		log.info("flushCache.");
		cache = new CachedViewDataManager();
	}

	/**
	 * Return the cached data.  This method is intended for non volitile data, and will
	 * neve expire unless specifically told to do so.
	 */
	public static CachedViewDataManager getCacheManagerViewNonVolitile(){
		if(cache == null){
			cache = new CachedViewDataManager();
		}
		return cache;
	}

	/**
	 * Convinience method for accessing data out of the cache map
	 * @param key the data key
	 * @return the cached object
	 */
	public static Object getCachedNonVolitileObj(String key){
		return getCacheManagerViewNonVolitile().getCacheMap().get(key);
	}

	/**
	 * Convinience method for setting the data in the cache map
	 * @param key the data key
	 * param the object to cache
	 */
	public static void putCachedNonVolitileObj(String key, Object cachedObj){
		getCacheManagerViewNonVolitile().getCacheMap().put(key,cachedObj);
	}

	/**
	 * Returns a hashmap suitable for storing arbitrary cache values for
	 * String lookup.
	 */
	public HashMap getCacheMap() {
		return cacheMap;
	}

	/**
	 * Gets a APIAccess object.  Should not be made public.
	 * @return an initialized APIAccess object
	 * @throws NamingException
	 */
	private static APIAccess getAPIAccess() throws NamingException{
		if(factory == null){
			factory = new APIAccess();
		}
		return factory;
	}


	/**
	 * Retrieves the refcode name data collection for the specified ref code name.
	 * Uses lazy loading.
	 * @param pRefCd the ref code name you need (ORDER_STATUS_CD for example)
	 * @return populated RefCdDataVector
	 * @throws NamingException
	 * @throws APIServiceAccessException
	 * @throws RemoteException
	 */
	public static RefCdDataVector getRefCdDataVector(String pRefCd) throws NamingException, APIServiceAccessException, RemoteException{
		String key = "RefCdDataVector."+pRefCd;
		RefCdDataVector refCds = (RefCdDataVector) getCachedNonVolitileObj(key);
		if(refCds == null){
			ListService lsvc = getAPIAccess().getListServiceAPI();
			refCds = lsvc.getRefCodesCollection(pRefCd);
			putCachedNonVolitileObj(key, refCds);
		}
		return refCds;
	}
}
