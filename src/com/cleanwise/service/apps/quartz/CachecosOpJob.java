package com.cleanwise.service.apps.quartz;

import com.cleanwise.service.api.cachecos.Cachecos;
import com.cleanwise.service.api.cachecos.CachecosManager;
import com.cleanwise.service.api.util.Utility;
import org.apache.log4j.Logger;

import org.quartz.JobDetail;
import org.quartz.JobExecutionException;

public class CachecosOpJob extends EventJobImpl {

    private static final Logger log = Logger.getLogger(CachecosOpJob.class);

    private static final String OP = "op";
    private static final String OBJECT_LIFETIME = "object_lifetime";
    private static final String CLEAR = "clear";
    private static final String GC = "gc";

    public void execute(JobDetail jobDetail) throws JobExecutionException {

        log.info("execute => BEGIN");

        try {

            String show = "";
            String jobName = jobDetail.getFullName();
            String trName = jobDetail.getFullName();
            show += "\n****************************************************************************\n";
            show += "CachecosOpJob: " + jobName + "(" + trName + ") exec at " + new java.util.Date() + "\n";
            show += "****************************************************************************\n";

            log.info(show);

            CachecosManager manager = Cachecos.getCachecosManager();
            log.info("execute => cache manager: " + manager);
            if (manager != null && manager.isStarted()) {
                execOp(manager, jobDetail);
            }

        } catch (Exception e) {
            throw new JobExecutionException(e);
        }

        log.info("execute => END.");
    }

    private void execOp(CachecosManager manager, JobDetail jobDetail) {

        log.info("execOp => BEGIN");

        String op = jobDetail.getJobDataMap().getString(OP);
        log.info("execOp => op: " + op);
        if (Utility.isSet(op)) {
            if (CLEAR.equals(op)) {
                manager.clear();
            } else if (GC.equals(op)) {
                manager.gc();
            }
        }

        log.info("execOp => END.");

    }

}
