/*
 * DomUniversalReport.java
 *
 * Created on May 21, 2007, 13:22
 */

package com.cleanwise.service.api.reporting;

import java.util.Map;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.tree.ReportItem;
import com.cleanwise.service.api.util.ConnectionContainer;

/**
 * 
 * @author bstevens
 */
public interface DomUniversalReport {
    /**
     * Should return a populated universal ReportItem object. At a minimum the
     * header should be set so an empty report may be generated to the user.
     */
    public ReportItem process(ConnectionContainer pCons,
            GenericReportData pReportData, Map pParams) throws Exception;
}
