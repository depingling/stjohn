package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.cachecos.CacheInformation;
import com.cleanwise.service.api.cachecos.Cachecos;
import com.cleanwise.service.api.cachecos.CachecosManager;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.forms.AdmCachecosMgrForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * Title:        AdmCachecosMgrLogic
 * Description:  Cachecos admin logic manager.
 * Purpose:      Getting information about cache.
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         14.01.2009
 * Time:         22:33:48
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class AdmCachecosMgrLogic {

    private static final Logger log = Logger.getLogger(AdmCachecosMgrLogic.class);

    public static ActionErrors init(HttpServletRequest request, AdmCachecosMgrForm form) {
        log.info("init => BEGIN");

        form = new AdmCachecosMgrForm();

        ActionErrors ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        request.getSession().setAttribute(AdmCachecosMgrForm.ADM_CACHECOS_MGR_FORM, form);

        log.info("init => END.");

        return ae;
    }

    public static ActionErrors getCacheDescription(HttpServletRequest request, AdmCachecosMgrForm form) throws Exception {

        log.info("getCacheDescription => BEGIN");

        ActionErrors ae;

        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        String cacheDescription = Utility.strNN(Cachecos.getCachecosManager().getCacheDescription());
        form.setCacheDescription(cacheDescription);


        log.info("getCacheDescription => END.");

        return ae;
    }

    private static ActionErrors checkRequest(HttpServletRequest request, AdmCachecosMgrForm form) {
        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);

        if (form == null) {
            ae.add("error", new ActionError("error.systemError", "Form not initialized"));
            return ae;
        }
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }
        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No user info"));
            return ae;
        }

        try {
            if (Cachecos.getCachecosManager() == null || !Cachecos.getCachecosManager().isReadyToWork()) {
                ae.add("error", new ActionError("error.systemError", "Cachecos manger unavailable"));
                return ae;
            }
        } catch (Exception e) {
            ae.add("error", new ActionError("error.systemError", "Error getting cachecos manger.Error message:" + e.getMessage()));
            e.printStackTrace();
            return ae;
        }

        return ae;
    }


    public static ActionErrors initManagement(HttpServletRequest request, AdmCachecosMgrForm form) {

        log.info("initManagement => BEGIN");

        ActionErrors ae;

        ae = init(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        form = (AdmCachecosMgrForm) request.getSession().getAttribute(AdmCachecosMgrForm.ADM_CACHECOS_MGR_FORM);

        CachecosManager manager = Cachecos.getCachecosManager();

        CacheInformation cInfo = manager.info();

        form.setCacheInformation(cInfo);

        log.info("initManagement => END.");

        return ae;
    }


    public static ActionErrors executeOp(HttpServletRequest request, AdmCachecosMgrForm form) throws Exception {

        log.info("executeOp => BEGIN");

        ActionErrors ae;

        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        CachecosManager manager = Cachecos.getCachecosManager();

        String op = request.getParameter("op");
        if ("clear".equals(op)) {
            manager.clear();
        } else if ("stop".equals(op)){
            manager.stop();
        } else if ("start".equals(op)){
            manager.start();            
        } else {
            manager.setGarbageCollectionInterval(form.getCacheInformation().getGarbageCollectionInterval());
            manager.setObjectLifetime(form.getCacheInformation().getObjectLifetime());
        }

        CacheInformation cInfo = manager.info();
        form.setCacheInformation(cInfo);

        log.info("executeOp => manager state: "+manager.getState());
        log.info("executeOp => END.");

        return ae;
    }

    private BigDecimal convertToMb(long l) {
        return new BigDecimal(((double) (l)) / (double) (1024 * 1024)).setScale(4, BigDecimal.ROUND_HALF_UP);
    }

}
