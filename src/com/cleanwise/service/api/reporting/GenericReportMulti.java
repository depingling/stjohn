/*
 * GenericReport.java
 *
 * Created on January 31, 2003, 5:43 PM
 */

package com.cleanwise.service.api.reporting;
import java.util.Map;
import java.sql.Connection;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.util.ConnectionContainer;
/**
 *
 * @author  bstevens
 */
public interface GenericReportMulti {
    /**
     *Should return a populated GenericReportResultView object.  At a minimum the header should
     *be set so an empty report may be generated to the user.
     */
    public GenericReportResultViewVector process(ConnectionContainer pCons,GenericReportData pReportData,Map pParams)
    throws Exception;
}
