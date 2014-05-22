package com.cleanwise.service.apps.quartz;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.process.ProcessSchema;
import com.cleanwise.service.api.process.variables.InboundContent;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.JanPakItemLoader;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.TaskDescriptor;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.EventProcessView;
import com.cleanwise.service.api.value.ProcessData;
import org.quartz.JobDetail;

public class JanPakItemFtpJob extends JanPakFtpJob {

    /**
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     */
    public JanPakItemFtpJob() {
    }

    public InboundFile accept(JobDetail jobDetail) throws Exception {

        byte[] content;

        JanPakItemLoader loader = APIAccess.getAPIAccess().getJanPakItemLoaderAPI();

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
        eventEjb.addEventProcess(epv, "JanPakItemFtpJob");

    }


    private ProcessSchema createProcessSchema(JobDetail jobDetail, boolean sendEvent) {

        ProcessSchema pSchema = new ProcessSchema();
        pSchema.addStep(TaskDescriptor.JAN_PACK_ITEM_LOADER.GETDISTRIBUTORID);
        pSchema.addStep(TaskDescriptor.JAN_PACK_ITEM_LOADER.GETSTORECATALOGID);
        pSchema.addStep(TaskDescriptor.JAN_PACK_ITEM_LOADER.PREPARE);
        pSchema.addStep(TaskDescriptor.JAN_PACK_ITEM_LOADER.MATCH);
        pSchema.addStep(TaskDescriptor.JAN_PACK_ITEM_LOADER.INSERT);
        pSchema.addStep(TaskDescriptor.JAN_PACK_ITEM_LOADER.UPDATE);
        pSchema.addStep(TaskDescriptor.JAN_PACK_ITEM_LOADER.DELETE);
        if (!Utility.isTrue(jobDetail.getJobDataMap().getString(P_REPORT))) {
            pSchema.addStep(TaskDescriptor.JAN_PACK_ITEM_LOADER.DROPWORKTABLES);
        }
        if (sendEvent) {
            pSchema.addStep(TaskDescriptor.JAN_PACK_ITEM_LOADER.SENDEVENT);
        }
        return pSchema;
    }

    public EventProcessView createEvent(JobDetail pJobDetail, Object... pParams) throws Exception {

        ProcessData process = APIAccess.getAPIAccess().getProcessAPI().getProcessByName(RefCodeNames.PROCESS_NAMES.JAN_PACK_ITEM_LOADER);

        InboundFile iFile = pParams.length > 0 ? (InboundFile) pParams[0] : null;
        String filterTable = pParams.length > 1 ? (String) pParams[1] : null;
        EventProcessView invoiceEvent = pParams.length > 2 ? (EventProcessView) pParams[2] : null;

        boolean sendEventFlag = invoiceEvent != null;

        ProcessSchema pSchema = createProcessSchema(pJobDetail, sendEventFlag);

        int i = 1;
        EventProcessView epv = Utility.createEventProcessView(process.getProcessId(), priorityOverride, subProcessPriority);
        epv.getProperties().add(Utility.createEventPropertyData(Event.PROCESS_SCHEMA,
                Event.PROCESS_SCHEMA,
                pSchema,
                i));
        epv.getProperties().add(Utility.createEventPropertyData("storeId",
                Event.PROCESS_VARIABLE,
                getStore(pJobDetail),
                i));
        epv.getProperties().add(Utility.createEventPropertyData("table",
                Event.PROCESS_VARIABLE,
                getTable(pJobDetail),
                ++i));
        epv.getProperties().add(Utility.createEventPropertyData("user",
                Event.PROCESS_VARIABLE,
                getUser(pJobDetail),
                ++i));
        if (iFile.getInboundId() > 0) {
            epv.getProperties().add(Utility.createEventPropertyData("dataContents",
                    Event.PROCESS_VARIABLE,
                    new InboundContent(iFile.getInboundId()),
                    ++i));
        } else {
            epv.getProperties().add(Utility.createEventPropertyData("dataContents",
                    Event.PROCESS_VARIABLE,
                    iFile.getDecContent(),
                    ++i));
        }
        epv.getProperties().add(Utility.createEventPropertyData("propertyMap",
                Event.PROCESS_VARIABLE,
                getSafePropertyMap(pJobDetail),
                ++i));
        epv.getProperties().add(Utility.createEventPropertyData("reload",
                Event.PROCESS_VARIABLE,
                false,
                ++i));
        if (filterTable != null) {
            epv.getProperties().add(Utility.createEventPropertyData("filterTableName",
                    Event.PROCESS_VARIABLE,
                    filterTable,
                    ++i));
        }
        if (invoiceEvent != null) {
            epv.getProperties().add(Utility.createEventPropertyData("invoiceLoaderEvent",
                    Event.PROCESS_VARIABLE,
                    invoiceEvent,
                    ++i));
        }

        return epv;
    }
}
