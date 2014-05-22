package com.cleanwise.service.apps.quartz;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.EventProcessView;
import org.apache.log4j.Logger;

import org.quartz.JobDetail;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class JanPakItemInvoiceFtpJob extends JanPakFtpJob {

    private static final Logger log = Logger.getLogger(JanPakItemInvoiceFtpJob.class);

    private static final String P_ITEM_LOADER_JOB_FULL_NAME    = "itemLoader";
    private static final String P_INVOICE_LOADER_JOB_FULL_NAME = "invoiceLoader";

    public void execute(JobDetail jobDetail) throws JobExecutionException {
        try {

            ArrayList<String> errors = checkData(jobDetail);
            if (errors.size() > 0) {
                throw new Exception(createErrorMessage(errors));
            }

            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();

            JobDetail invoiceJobDetail = getJobDetail(scheduler, getInvoiceLoaderJobFullName(jobDetail));
            JobDetail itemJobDetail = getJobDetail(scheduler, getItemLoaderJobFullName(jobDetail));

            JanPakFtpJob invoiceLoaderFtpJob = ((JanPakFtpJob) invoiceJobDetail.getJobClass().newInstance());
            JanPakFtpJob itemLoaderFtpJob = ((JanPakFtpJob) itemJobDetail.getJobClass().newInstance());

            errors.addAll(invoiceLoaderFtpJob.checkData(invoiceJobDetail));
            errors.addAll(itemLoaderFtpJob.checkData(itemJobDetail));
            if (errors.size() > 0) {
                throw new Exception(createErrorMessage(errors));
            }

            InboundFile invoiceFile = invoiceLoaderFtpJob.accept(invoiceJobDetail);
            InboundFile itemFile = itemLoaderFtpJob.accept(itemJobDetail);

            if (invoiceFile != null && itemFile != null) {
                byte[] itemContent = itemFile.getDecContent();
                byte[] invoiceContent = invoiceFile.getDecContent();
                if (itemContent != null && itemContent.length > 0) {
                    if (invoiceContent != null && invoiceContent.length > 0) {
                        process(itemFile, itemJobDetail, itemLoaderFtpJob, invoiceFile, invoiceJobDetail, invoiceLoaderFtpJob);
                    } else {
                        log.info("execute => No invoice data for process.");
                    }
                } else {
                    log.info("execute => No item data for process.");
                }
            }

            if (isAccepted(invoiceFile) && isRemoveFile(invoiceJobDetail)) {
                removeIFile(invoiceFile, invoiceJobDetail);
            }

            if (isAccepted(itemFile) && isRemoveFile(itemJobDetail)) {
                removeIFile(itemFile, itemJobDetail);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new JobExecutionException(e);
        }
    }

    private JobDetail getJobDetail(Scheduler scheduler, String jobFullName) throws Exception {

        JobDetail eJobDetail;

        StringTokenizer tok = new StringTokenizer(jobFullName, ".");
        String group = tok.nextToken();
        String name = tok.nextToken();
        eJobDetail = scheduler.getJobDetail(name, group);

        if(eJobDetail == null) {
            throw new Exception("Job with name '"+jobFullName+" not found.");
        }

        return eJobDetail;

    }

    public ArrayList<String> checkData(JobDetail jobDetail) {

        ArrayList<String> errors = new ArrayList<String>();

        if (jobDetail == null) {
            errors.add("Job Details is null.");
            return errors;
        }

        if (jobDetail.getJobDataMap() == null) {
            errors.add("Job Data Map is null.");
        }

        String itemLoaderJobName = getItemLoaderJobFullName(jobDetail);
        if (!Utility.isSet(itemLoaderJobName)) {
            errors.add(P_ITEM_LOADER_JOB_FULL_NAME + " a required field.");
        }
        if (itemLoaderJobName.indexOf(".") == -1) {
            errors.add(P_ITEM_LOADER_JOB_FULL_NAME + " field value has wrong format.");
        }

        String invoiceLoaderJobName = getItemLoaderJobFullName(jobDetail);
        if (!Utility.isSet(invoiceLoaderJobName)) {
            errors.add(P_INVOICE_LOADER_JOB_FULL_NAME + " a required field.");
        }
        if (itemLoaderJobName.indexOf(".") == -1) {
            errors.add(P_INVOICE_LOADER_JOB_FULL_NAME + " field value has wrong format.");
        }

        return errors;

    }

    public void process(InboundFile itemFile,
                        JobDetail itemJobDetail,
                        JanPakFtpJob itemLoaderFtpJob,
                        InboundFile invoiceFile,
                        JobDetail invoiceJobDetail,
                        JanPakFtpJob invoiceLoaderFtpJob) throws Exception {

        Event eventEjb = APIAccess.getAPIAccess().getEventAPI();

        EventProcessView invoiceLoaderEvent = invoiceLoaderFtpJob.createEvent(invoiceJobDetail, invoiceFile);
        EventProcessView itemLoaderEvent = itemLoaderFtpJob.createEvent(itemJobDetail, itemFile, getTable(invoiceJobDetail), invoiceLoaderEvent);

        eventEjb.addEventProcess(itemLoaderEvent, "JanPakItemInvoiceFtpJob");
    }

    public String getItemLoaderJobFullName(JobDetail jobDetail) {
        return (String) jobDetail.getJobDataMap().get(P_ITEM_LOADER_JOB_FULL_NAME);
    }

    public String getInvoiceLoaderJobFullName(JobDetail jobDetail) {
        return (String) jobDetail.getJobDataMap().get(P_INVOICE_LOADER_JOB_FULL_NAME);
    }

    public InboundFile accept(JobDetail jobDetail) throws Exception {
        return null;
    }

    public void process(InboundFile file, JobDetail jobDetail) throws Exception {
    }

    public EventProcessView createEvent(JobDetail pJobDetail, Object... pParams) throws Exception {
        return null;
    }
}
