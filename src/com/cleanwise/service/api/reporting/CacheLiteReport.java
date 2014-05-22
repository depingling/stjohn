package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.cachecos.CacheKey;
import com.cleanwise.service.api.cachecos.Cachecos;
import com.cleanwise.service.api.cachecos.CachecosManager;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;

import java.util.*;

import org.apache.log4j.Logger;


public class CacheLiteReport implements GenericReportMulti {

    private static final String OTHER = "Other";

    private static final Logger log = Logger.getLogger(CacheLiteReport.class);

    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {

        log.info("process => BEGIN");

        GenericReportResultViewVector resultV = new GenericReportResultViewVector();

        CachecosManager cacheManager = Cachecos.getCachecosManager();

        if (cacheManager != null && cacheManager.isStarted()) {

            log.info("process => building rep: " + pReportData.getName());

            Set<CacheKey> keys = cacheManager.getKeys();

            HashMap<String, List<String>> keyGroup = new HashMap<String, List<String>>();
            if (keys != null) {
                for (CacheKey key : keys) {
                    String keyStr = key.toString();
                    int firstDelimPos = keyStr.indexOf(CacheKey.DELIM);
                    String group;
                    if (firstDelimPos > 0) {
                        group = keyStr.substring(0, firstDelimPos);
                    } else {
                        group = OTHER;
                    }
                    if (keyGroup.containsKey(group)) {
                        List<String> keyStrList = keyGroup.get(group);
                        keyStrList.add(keyStr);
                    } else {
                        List<String> keyStrList = new ArrayList<String>();
                        keyStrList.add(keyStr);
                        keyGroup.put(group, keyStrList);
                    }
                }
            }

            for (String gKey : keyGroup.keySet()) {

                ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
                GenericReportColumnViewVector header = getHeader();
                GenericReportResultView result = GenericReportResultView.createValue();

                result.setHeader(header);
                result.setColumnCount(header.size());
                result.setName(gKey);
                List<String> cKeys = keyGroup.get(gKey);
                for (String cKey : cKeys) {
                    ArrayList<String> tkList = new ArrayList<String>();
                    tkList.add(cKey);
                    table.add(tkList);
                }
                result.setTable(table);

                resultV.add(result);
            }
        } else {
            log.info("process => cache manager is stopped or not bound.");
        }

        log.info("process => END.");

        return resultV;

    }

    private GenericReportColumnViewVector getHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Key", 0, 255, "VARCHAR2"));
        return header;
    }

}
