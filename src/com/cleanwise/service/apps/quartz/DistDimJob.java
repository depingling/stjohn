package com.cleanwise.service.apps.quartz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.quartz.JobDetail;
import org.quartz.JobExecutionException;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.EventProcessView;
import com.cleanwise.service.api.value.ProcessData;

public class DistDimJob extends EventJobImpl {
    public void execute(JobDetail jobDetail) throws JobExecutionException {
        try {
            Map map = jobDetail.getJobDataMap();
            List<Integer> storeIds = new ArrayList<Integer>();
            String storeIdsString = (String) map.get("STORE_IDS");
            if (storeIdsString != null) {
                String[] storeIdsStringParsed = Utility.parseStringToArray(
                        storeIdsString, ",");
                for (int i = 0; storeIdsStringParsed != null
                        && i < storeIdsStringParsed.length; i++) {
                    storeIds.add(Utility.parseInt(storeIdsStringParsed[i]));
                }
            }
            String dbSchemaGeneral = (String) map.get("DB_SCHEMA_GENERAL");
            String dbSchemaDim = (String) map.get("DB_SCHEMA_DIM");
            Integer jdStoreId = Utility.parseInt((String) map
                    .get("JD_STORE_ID"));
            Event eventEjb = APIAccess.getAPIAccess().getEventAPI();
            
            ProcessData process = APIAccess.getAPIAccess().getProcessAPI()
                    .getProcessByName(
                            RefCodeNames.PROCESS_NAMES.DISTRIBUTOR_DIM_LOAD);
            
            EventProcessView epv = Utility.createEventProcessView(process.getProcessId(), priorityOverride, subProcessPriority);
            epv.getProperties().add(
                    Utility.createEventPropertyData("dbSchemaGeneral",
                            Event.PROCESS_VARIABLE, dbSchemaGeneral, 2));
            epv.getProperties().add(
                    Utility.createEventPropertyData("dbSchemaDim",
                            Event.PROCESS_VARIABLE, dbSchemaDim, 3));
            epv.getProperties().add(
                    Utility.createEventPropertyData("storeIds",
                            Event.PROCESS_VARIABLE, storeIds, 4));
            epv.getProperties().add(
                    Utility.createEventPropertyData("jdStoreId",
                            Event.PROCESS_VARIABLE, jdStoreId, 5));
            eventEjb.addEventProcess(epv, "DistDimJob");
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }
}
