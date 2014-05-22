package com.cleanwise.service.apps.quartz;

import java.util.Date;

import org.apache.log4j.Logger;

import org.quartz.*;

/**
 * <p>
 * This is just a simple job
 * </p>
 *
 */
public class ClwSimpleJob implements Job {

    private static final Logger _log = Logger.getLogger(ClwSimpleJob.class);

    /**
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     */
    public ClwSimpleJob() {
    }

    /**
     * <p>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a
     * <code>{@link org.quartz.Trigger}</code> fires that is associated with
     * the <code>Job</code>.
     * </p>
     *
     * @throws JobExecutionException
     *             if there is an exception while executing the job.
     */
    public void execute(JobExecutionContext context)
        throws JobExecutionException {

      String show = "";
      String jobName = context.getJobDetail().getFullName();
      String trName = context.getTrigger().getFullName();
      show += "\n****************************************************************************\n";
      show += "ClwSimpleJob: " + jobName + "(" + trName + ") exec at " + new Date() + "\n";
      JobDataMap dataMap = context.getJobDetail().getJobDataMap();
      if (dataMap != null && dataMap.size() != 0) {
        show += "parameters:\n";
        String[] keys = dataMap.getKeys();
        for (int i = 0; i < keys.length; i++) {
        show += keys[i] + " = " + dataMap.getString(keys[i]) + "\n";
        }
      } else {
        show += "without parameters:\n";
      }
      show += "Previous Fire Time: " + context.getPreviousFireTime() + "\n";
      show += "****************************************************************************\n";

    }

}
