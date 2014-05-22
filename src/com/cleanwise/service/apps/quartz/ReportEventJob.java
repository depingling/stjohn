
package com.cleanwise.service.apps.quartz;

import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import org.quartz.JobDetail;
import org.quartz.JobExecutionException;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
/* Predefined parameters:
PROCESS_NAME - name of a process to create event for  (MANDATORY)
MULTI_EVENT_PROPERTY - name of property, which could have comma separated list of values. For each of this values separate event will be created (OPTIONAL)
*/

public class ReportEventJob extends EventJobImpl {
    final String PROCESS_NAME = "PROCESS_NAME";
    final String COMMAND = "command";
    private static final Logger log = Logger.getLogger(ReportEventJob.class);

	public void execute(JobDetail jobDetail) throws JobExecutionException {
        try {
            Event eventEjb = APIAccess.getAPIAccess().getEventAPI();
            String jobName = jobDetail.getName();
            Map jobMap = jobDetail.getJobDataMap();
            HashMap map = new HashMap(jobMap);
            Set keys = map.entrySet();
            String processName = (String) map.get(PROCESS_NAME);
            String command =  (map.get(COMMAND)!= null) ? (String)map.get(COMMAND) : (String)map.get(COMMAND.toUpperCase());
            if(processName==null) {
                throw new JobExecutionException("No PROCESS_NAME parameter for the job. Job name: "+jobName);
            } else {
				processName = processName.trim();
			}
            String multiEvent = (String) map.get("MULTI_EVENT_PROPERTY");
			String[] multiEventA = null;
            if(multiEvent!=null && map.containsKey(multiEvent.trim())) {
				String sss = (String) map.get(multiEvent.trim());
                if(sss!=null) {
					multiEventA = sss.split(",");
				}
			}
			if(multiEventA==null) {
				multiEventA = new String[1];
			}
            ProcessData processD = APIAccess.getAPIAccess().getProcessAPI().getProcessByName(processName);
            int processId = processD.getProcessId();
            TaskPropertyDataVector taskPropDV =
                    APIAccess.getAPIAccess().getProcessAPI().getTaskPropertyByTemplateProcessId(processId);

			for(int ii=0; ii<multiEventA.length; ii++) {
				EventPropertyDataVector eventPropDV = new EventPropertyDataVector();
				for(Iterator iter=taskPropDV.iterator(); iter.hasNext();) {
					TaskPropertyData tpD = (TaskPropertyData) iter.next();
					String varName = tpD.getVarName();
					String varValue = null;
					if(varName.equals(multiEvent)) {
						varValue = Utility.strNN(multiEventA[ii]).trim();
					} else {
						varValue = (String) map.get(varName);
					}
					String varType = tpD.getVarType();
					if(RefCodeNames.TASK_PROPERTY_TYPE_CD.MANDATORY.equals(tpD.getPropertyTypeCd()) &&
						varValue==null
					) {
						throw new JobExecutionException("No parameter for the job. Parmeter name: "+varName+
								" Job name: "+jobName);
					}
					EventPropertyData epD = new EventPropertyData();
					eventPropDV.add(epD);
					epD.setShortDesc(varName);
					epD.setNum(tpD.getPosition());
					if("java.lang.Integer".equals(varType)) {
						int intValue = (varValue==null)?0:Integer.parseInt(varValue);
						epD.setNumberVal(intValue);
						epD.setVarClass(varType);
					} else if("java.lang.String".equals(varType)) {
                                                String strVal = (varValue==null)?"": varValue;
						epD.setStringVal(strVal);
						epD.setVarClass(varType);
					} else {
//						throw new JobExecutionException("Unknown type for the job. Parmeter name: "+varName+
//								" Type: "+varType+
//								" Job name: "+jobName);
					}
				}
				

				EventProcessView epv = Utility.createEventProcessView(processId, priorityOverride, subProcessPriority);

				if (Utility.isSet(command)){
					epv.getProperties().add(Utility.createEventPropertyData(COMMAND,
                            Event.PROCESS_VARIABLE, command, 1));
					
				}
				epv.getProperties().add(Utility.createEventPropertyData("parameters",
						Event.PROCESS_VARIABLE, map, 2));

				if (allowedToProcessEvent(processId, processName, command, epv.getProperties())){
					eventEjb.addEventProcess(epv, "ReportEventJob");
				}

			}
        } catch (Exception e) {
            e.getStackTrace();
            throw new JobExecutionException(e);
        }
    }
	private boolean allowedToProcessEvent (int processId, String processName, String command, EventPropertyDataVector eventPropDV) throws Exception {
		boolean ok = true;
		Event eventEjb = APIAccess.getAPIAccess().getEventAPI();
		if (processName.equals(RefCodeNames.PROCESS_NAMES.PROCESS_APP_CMD_OPERATIONS)){
			if (eventEjb.activeEventExist(processId, eventPropDV)){ // any event that match process name and properties
				log.info("Event exist with status 'READY' or 'IN_PROGRESS' for type - " + processName +"(" + command+ ")"
						+ ". Will not create new event for it.");
				ok = false;
			}
		}
		return ok;
	}
}
