/*
 * PreOrderCapture.java
 *
 * Created on May 6, 2003, 3:12 PM
 */

package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.value.OrderRequestData;
import com.cleanwise.service.api.util.PipelineException;
/**
 *
 * @author  bstevens
 */
public interface PreOrderCapturePipeline {
    /**
     *Process this pipeline.
     *
     *@param OrderRequestData the order request object to act upon
     *@param Connection a active database connection
     *@param APIAccess
     */
    public void process(OrderRequestData pOrderRequest, Connection pCon, APIAccess pFactory)
    throws PipelineException;
}
