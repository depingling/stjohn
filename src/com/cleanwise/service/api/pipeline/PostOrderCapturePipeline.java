/*
 * PreOrderCapture.java
 *
 * Created on May 6, 2003, 3:12 PM
 */

package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.value.AccCategoryToCostCenterView;
/**
 *
 * @author  bstevens
 */
public interface PostOrderCapturePipeline {
    /**
     *Process this pipeline.
     *
     *@param pOrderData
     *@param pOrderItemDataVector
     *@param pAccountId
     *@param Connection a active database connection
     *@param APIAccess
     */
    public void process(OrderData pOrderData, OrderItemDataVector pOrderItemDataVector,
                        int pAccountId, Connection pCon, APIAccess pFactory,
                        AccCategoryToCostCenterView pCategToCostCenterView)throws PipelineException;
}
