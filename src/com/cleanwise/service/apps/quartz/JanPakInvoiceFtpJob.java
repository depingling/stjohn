package com.cleanwise.service.apps.quartz;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.process.ProcessSchema;
import com.cleanwise.service.api.process.variables.InboundContent;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.JanPakInvoiceLoader;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.TaskDescriptor;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.EventProcessView;
import com.cleanwise.service.api.value.ProcessData;
import org.quartz.JobDetail;

public class JanPakInvoiceFtpJob extends JanPakFtpJob {

    /**
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     */
    public JanPakInvoiceFtpJob() {
    }

    public InboundFile accept(JobDetail jobDetail) throws Exception {

        byte[] content;

        JanPakInvoiceLoader loader = APIAccess.getAPIAccess().getJanPakInvoiceLoaderAPI();

        showStartupInfo(jobDetail);

        InboundFile iFile = getIFile(jobDetail);

        if (iFile != null) {

            content = iFile.getDecContent();

            if (saveToDb(jobDetail)) {
                saveInboundData(iFile, jobDetail); 
            }

            loader.accept(content, getTable(jobDetail), iFile.getFileName(), iFile.getLastMod(), getUser(jobDetail));

        }

        return iFile;
    }

    public void process(InboundFile file, JobDetail jobDetail) throws Exception {

        Event eventEjb = APIAccess.getAPIAccess().getEventAPI();
        EventProcessView epv = createEvent(jobDetail, file);
        eventEjb.addEventProcess(epv, "JanPakInvoiceFtpJob");

    }

    public EventProcessView createEvent(JobDetail pJobDetail, Object... pParams) throws Exception {

        ProcessData process = APIAccess.getAPIAccess().getProcessAPI().getProcessByName(RefCodeNames.PROCESS_NAMES.JAN_PACK_INVOICE_LOADER);

        ProcessSchema pSchema = createProcessSchema(pJobDetail);
        EventProcessView epv = Utility.createEventProcessView(process.getProcessId(), priorityOverride, subProcessPriority);
        
        epv.getProperties().add(Utility.createEventPropertyData(Event.PROCESS_SCHEMA,
                Event.PROCESS_SCHEMA,
                pSchema,
                1));
        epv.getProperties().add(Utility.createEventPropertyData("storeId",
                Event.PROCESS_VARIABLE,
                getStore(pJobDetail),
                1));
        epv.getProperties().add(Utility.createEventPropertyData("table",
                Event.PROCESS_VARIABLE,
                getTable(pJobDetail),
                2));
        epv.getProperties().add(Utility.createEventPropertyData("user",
                Event.PROCESS_VARIABLE,
                getUser(pJobDetail),
                3));
        if (((InboundFile) pParams[0]).getInboundId() > 0) {
            epv.getProperties().add(Utility.createEventPropertyData("dataContents",
                    Event.PROCESS_VARIABLE,
                    new InboundContent(((InboundFile) pParams[0]).getInboundId()),
                    4));
        } else {
            epv.getProperties().add(Utility.createEventPropertyData("dataContents",
                    Event.PROCESS_VARIABLE,
                    ((InboundFile) pParams[0]).getDecContent(),
                    4));
        }
        epv.getProperties().add(Utility.createEventPropertyData("propertyMap",
                Event.PROCESS_VARIABLE,
                getSafePropertyMap(pJobDetail),
                5));
        epv.getProperties().add(Utility.createEventPropertyData("reload",
                Event.PROCESS_VARIABLE,
                false,
                6));

        return epv;
    }


    private ProcessSchema createProcessSchema(JobDetail jobDetail) {

        ProcessSchema pSchema = new ProcessSchema();
        pSchema.addStep(TaskDescriptor.JAN_PACK_INVOICE_LOADER.GETDISTRIBUTORID);
        pSchema.addStep(TaskDescriptor.JAN_PACK_INVOICE_LOADER.PREPARE);
        pSchema.addStep(TaskDescriptor.JAN_PACK_INVOICE_LOADER.MATCH);
        pSchema.addStep(TaskDescriptor.JAN_PACK_INVOICE_LOADER.INSERT);
        if(Utility.isTrue(jobDetail.getJobDataMap().getString(P_REPORT))) {
            pSchema.addStep(TaskDescriptor.JAN_PACK_INVOICE_LOADER.REPORT);
        } else{
            pSchema.addStep(TaskDescriptor.JAN_PACK_INVOICE_LOADER.DROPWORKTABLES);
        }
        return pSchema;
    }

}
