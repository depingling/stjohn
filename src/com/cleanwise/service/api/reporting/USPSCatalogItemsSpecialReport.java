package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import java.text.SimpleDateFormat;

import java.util.*;

public class USPSCatalogItemsSpecialReport extends ReportBase {

    private ArrayList resultTable = new ArrayList();
    private String VALUE_DELIM = "|";
    
    
    public USPSCatalogItemsSpecialReport() {
      Date repDate = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
      String repDateS = sdf.format(repDate);

      setFileName("cat_cleanwise");
      setExt("dat"+"." + repDateS);
      setSpecial(true);
      setUserTypesAutorized(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR+","+RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR);
    }

    protected byte[] getOutputStream()  throws Exception {
        StringBuffer resultBuff = new StringBuffer();

        Iterator i = resultTable.iterator();
        while(i.hasNext()) {
            ArrayList line = (ArrayList)i.next();
            Iterator j = line.iterator();
            int k = 1;
            while (j.hasNext()) {
                Object el = j.next();
                if (k > 1) {
                    resultBuff.append(VALUE_DELIM);
                }
                if (el == null) {
                    resultBuff.append("");
                } else {
                    resultBuff.append(el);
                }
                k++;
            }
            resultBuff.append("\r\n");
        }
        return resultBuff.toString().getBytes();
    }


    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception
    {

        USPSCatalogItemsReportUtil repUtil = new USPSCatalogItemsReportUtil();
        resultTable = repUtil.createResult(pCons, pParams, true);

        return super.process(pCons, pReportData, pParams);

    }


}
