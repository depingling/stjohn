package com.cleanwise.service.apps.quartz;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;


import org.quartz.JobDetail;
import org.quartz.JobExecutionException;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.Process;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.EventProcessView;
import com.cleanwise.service.api.value.ProcessData;


public class SequenceOfSqlEventJob extends EventJobImpl {

    private static final Logger log = Logger.getLogger(SequenceOfSqlEventJob.class);

    public void execute(JobDetail jobDetail) throws JobExecutionException {
        try {
            final String processName = RefCodeNames.PROCESS_NAMES.EXEC_SQL_SEQUENCE;

            String jobName = jobDetail.getName();
            log.info("Start of performing job: " + jobName);

            Map map = jobDetail.getJobDataMap();
            map.remove(Event.PRIORITY_OVERRIDE);
            map.remove(Event.SUBPROCESS_PRIORITY);

            ArrayList<String> listOfSql = parseParametersMap(map);

            Event eventEjb = APIAccess.getAPIAccess().getEventAPI();
            Process processEjb = APIAccess.getAPIAccess().getProcessAPI();

            ProcessData processD = processEjb.getProcessByName(processName);
            final int processId = processD.getProcessId();

            EventProcessView epv = Utility.createEventProcessView(processId, priorityOverride, subProcessPriority);
            
            epv.getProperties().add(Utility.createEventPropertyData("sqlList", 
                Event.PROCESS_VARIABLE, listOfSql, 1));
            eventEjb.addEventProcess(epv, "SequenceOfSqlEventJob");

            log.info("Finish of performing job: " + jobName);
        } catch (Exception ex) {
            log.error("An error occurred. " + ex.getMessage());
            throw new JobExecutionException(ex);
        }
    }

    private ArrayList<String> parseParametersMap(Map map) throws JobExecutionException {
        ArrayList<String> result = new ArrayList<String>();
        if (map == null) {
            return result;
        }
        if (map.size() == 0) {
            return result;
        }
        TreeMap<Integer, String> auxMap = new TreeMap<Integer, String>();
        Set keys = map.keySet();
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            String key = (String)it.next();
            int sqlNum = getSqlIdByName(key);
            if (sqlNum == -1) {
                throw new JobExecutionException(
                    "Invalid parameter name. Parameter name have the next format: SQL###.");
            }
            if (auxMap.containsKey(new Integer(sqlNum)) ) {
                throw new JobExecutionException(
                    "Invalid parameter name. The parameter with ");
            }
            String value = (String)map.get(key);
            if (value == null) {
                throw new JobExecutionException("");
            }
            if (value.trim().length() == 0) {
                throw new JobExecutionException("");
            }
            auxMap.put(new Integer(sqlNum), value.trim());
        }
        if (auxMap.size() > 0) {
            Set auxKeys = auxMap.keySet();
            Iterator itk = auxKeys.iterator();
            while (itk.hasNext()) {
                Integer key = (Integer)itk.next();
                String value = (String)auxMap.get(key);
                result.add(value);
            }
        }
        return result;
    }

    private int getSqlIdByName(String sqlName) {
        if (sqlName == null) {
            return -1;
        }
        final int sqlNameLen = sqlName.length();
        if (sqlNameLen < 3) {
            return -1;
        }
        if ((sqlName.charAt(0) != 'S' && sqlName.charAt(0) != 's') ||
            (sqlName.charAt(1) != 'Q' && sqlName.charAt(1) != 'q') ||
            (sqlName.charAt(2) != 'L' && sqlName.charAt(2) != 'l')) {
            return -1;
        }
        if (sqlNameLen == 3) {
            return 0;
        }
        for (int i = 3; i < sqlNameLen; ++i) {
            char chr = sqlName.charAt(i);
            if (!Character.isDigit(chr)) {
                return -1;
            }
        }
        int sqlNum = -1;
        try {
            sqlNum = Integer.parseInt(sqlName.substring(3, sqlNameLen));
        } catch (NumberFormatException ex) {
            return -1;
        }
        return sqlNum;
    }

}
