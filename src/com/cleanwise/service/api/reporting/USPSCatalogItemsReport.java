package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;

import java.util.*;
import java.io.StringWriter;
import java.io.PrintWriter;

public class USPSCatalogItemsReport implements GenericReportMulti {

    private static String className = "USPSCatalogItemsReport";

    public USPSCatalogItemsReport() {
    }

    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */
    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception
    {
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();

        USPSCatalogItemsReportUtil repUtil = new USPSCatalogItemsReportUtil();
        ArrayList table = repUtil.createResult(pCons, pParams, false);

        GenericReportResultView result = GenericReportResultView.createValue();
        GenericReportColumnViewVector header = repUtil.getReportHeader();
        result.setHeader(header);
        result.setColumnCount(header.size());
        result.setName("USPS Catalog Items");
        result.setFreezePositionColumn(1);
        result.setFreezePositionRow(1);


        result.setTable(table);

        resultV.add(result);


        return resultV;


    }



}
