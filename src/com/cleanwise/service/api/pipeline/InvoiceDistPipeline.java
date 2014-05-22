/*
 * InvoiceDistPipeline.java
 * a pipeline component for processing the simplified invoicing pipeline
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import java.sql.Connection;
/**
 *
 * @author bstevens
 */
public interface InvoiceDistPipeline {
    public void process(
                InvoiceDistPipelineBaton pBaton,
                Connection pCon, 
                APIAccess pFactory) 
    throws PipelineException;
}
