package com.espendwise.webservice.restful.security;

import com.cleanwise.service.api.util.ICleanwiseUser;

import org.apache.log4j.Logger;

import java.security.SecureRandom;
import java.util.*;

public class RestSecurity {
    private static final Logger log = Logger.getLogger(RestSecurity.class);
    public static long DEFAULT_SESSION_TIME = 60/*sec*/ * 1000/*msec*/ * 30/*min*/;

    Map<String, UserSessionLink> mKeyHolder = new HashMap<String, UserSessionLink>();

    private static RestSecurity _instance = new RestSecurity();

    public static RestSecurity getInstance() {
        return _instance;
    }

    public void register(String pSecurityKey, ICleanwiseUser pUser, String pStoreUnit, boolean pTracking) {
        synchronized (mKeyHolder) {
            UserSessionLink link = mKeyHolder.get(pSecurityKey);
            if (link == null) {
                link = new UserSessionLink(new Date(), pUser, pStoreUnit, pTracking);
                mKeyHolder.put(pSecurityKey, link);
            } else {
                link.accessDate = new Date();
                link.storeUnit = pStoreUnit;
                link.tracking = pTracking;
            }
        }
    }

    public void register(String pSecurityKey, ICleanwiseUser pUser, String pDbUnit) {
        synchronized (mKeyHolder) {
            UserSessionLink link = mKeyHolder.get(pSecurityKey);
            if (link == null) {
                link = new UserSessionLink(new Date(), pUser, pDbUnit);
                mKeyHolder.put(pSecurityKey, link);
            } else {
                link.accessDate = new Date();
                link.storeUnit = pDbUnit;
            }
        }
    }


    public ICleanwiseUser getUser(String pSecurityKey) {
        UserSessionLink link = mKeyHolder.get(pSecurityKey);
        return link == null ? null : link.user;
    }

    public String getStoreUnit(String pSecurityKey) {
        UserSessionLink link = mKeyHolder.get(pSecurityKey);
        return link == null ? null : link.storeUnit;
    }

    public void unregister(String pSecurityKey) {
        synchronized (mKeyHolder) {
            mKeyHolder.remove(pSecurityKey);
        }
    }

    public boolean verify(String pSecurityKey, boolean pTracking) {
        log.info("verify()=> verify key : " + pSecurityKey + ", mKeyHolder: " + mKeyHolder.keySet());
        if (mKeyHolder.containsKey(pSecurityKey)) {
            UserSessionLink link = mKeyHolder.get(pSecurityKey);
            if (pTracking || (link != null && link.tracking == pTracking)) {
                return true;
            }
        }
        return false;
    }

    public boolean verify(String pSecurityKey) {
        log.info("verify()=> verify key : " + pSecurityKey + ", mKeyHolder: " + mKeyHolder.keySet());
        if (mKeyHolder.containsKey(pSecurityKey)) {
            UserSessionLink link = mKeyHolder.get(pSecurityKey);
            if (link != null) {
                return true;
            }
        }
        return false;
    }

    public boolean verify(String pSecurityKey, Date date) {
        log.info("verify()=> verify key : " + pSecurityKey + ", mKeyHolder: " + mKeyHolder.keySet());
        if (mKeyHolder.containsKey(pSecurityKey)) {
            UserSessionLink link = mKeyHolder.get(pSecurityKey);
            if (link != null) {
                Boolean expired = isExpired(pSecurityKey, date);
                if (expired != null && !expired && date.after(link.getCreatedDate())) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean isExpired(String pSecurityKey, Date date) {
        if (mKeyHolder.containsKey(pSecurityKey)) {
            UserSessionLink link = mKeyHolder.get(pSecurityKey);
            if (link != null) {
                return ((date.getTime() - link.getAccessDate().getTime()) > DEFAULT_SESSION_TIME);
            }
        }
        return null;
    }

    public String getSecurityKey(String pUserName, String pPassword) {
        return
                String.valueOf(pUserName.concat(pPassword).hashCode()) +
                        "/" +
                        String.valueOf(new SecureRandom().nextLong());
    }


    public void open(String pSecurityKey) {

        if (mKeyHolder.containsKey(pSecurityKey)) {
            UserSessionLink link = mKeyHolder.get(pSecurityKey);
            link.lock();
            link.updateAccessDate(new Date());
        }

    }

    public void close(String pSecurityKey) {

        if (mKeyHolder.containsKey(pSecurityKey)) {
            UserSessionLink link = mKeyHolder.get(pSecurityKey);
            link.unlock();
            link.updateAccessDate(new Date());
        }

    }

    public void lock(String pSecurityKey) {

        if (mKeyHolder.containsKey(pSecurityKey)) {
            UserSessionLink link = mKeyHolder.get(pSecurityKey);
            if ((link != null)) {
                link.lock();
            }
        }

    }

    public void unlock(String pSecurityKey) {

        if (mKeyHolder.containsKey(pSecurityKey)) {
            UserSessionLink link = mKeyHolder.get(pSecurityKey);
            if ((link != null)) {
                link.unlock();
            }
        }

    }

    public void clearUnusedKeys() {

        log.info("clearUnusedKeys()=> BEGIN, Time: " + new Date());

        List<Map.Entry<String, UserSessionLink>> removcd = new ArrayList<Map.Entry<String, UserSessionLink>>();

        synchronized (mKeyHolder) {

            Date now = new Date();

            Iterator<Map.Entry<String, UserSessionLink>> it = mKeyHolder.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, UserSessionLink> eset = it.next();
                if (eset.getValue().locked != null && !eset.getValue().locked) {
                    if ((now.getTime() - eset.getValue().getAccessDate().getTime()) > DEFAULT_SESSION_TIME) {
                        removcd.add(eset);
                        it.remove();
                    }

                }
            }
        }

        log.info("clearUnusedKeys()=> END, removed keys: " + removcd);

    }

    private class UserSessionLink {

        private Date accessDate;
        private Date createdDate;
        private Boolean locked;
        private ICleanwiseUser user;
        private String storeUnit;
        private boolean tracking;

        private UserSessionLink(Date pAccessDate, ICleanwiseUser pUser, String pStoreUnit, boolean pTracking) {
            this.accessDate = pAccessDate;
            this.user = pUser;
            this.storeUnit = pStoreUnit;
            this.tracking = pTracking;
            this.createdDate = new Date();
        }

        private UserSessionLink(Date pAccessDate, ICleanwiseUser pUser, String pDbUnit) {
            this.accessDate = pAccessDate;
            this.user = pUser;
            this.storeUnit = pDbUnit;
            this.createdDate = new Date();
            this.locked = false;
        }

        public void lock() {
            this.locked = true;
        }

        public void unlock() {
            this.locked = false;
        }

        public Date getAccessDate() {
            return accessDate;
        }

        public void setAccessDate(Date accessDate) {
            this.accessDate = accessDate;
        }

        public void updateAccessDate(Date accessDate) {
            setAccessDate(accessDate);
        }

        public Date getCreatedDate() {
            return createdDate;
        }
    }


}
