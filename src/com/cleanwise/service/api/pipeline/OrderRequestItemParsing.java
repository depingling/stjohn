/*
 *
 *
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;

import java.sql.Connection;

/**
 * Pipeline class. Does initial order item processing
 *
 * @author YKupershmidt
 * mod by Alexander Chickin 06.02.2007
 */
public class OrderRequestItemParsing implements OrderPipeline {


    public OrderPipelineBaton process(OrderPipelineBaton pBaton, OrderPipelineActor pActor, Connection pCon, APIAccess pFactory) throws PipelineException {

        OrderPipeline classWorker;

        if (pBaton.getSimpleServiceOrderFl()) {
            classWorker = new OrderRequestServiceParsing();
        } else if (pBaton.getSimpleProductOrderFl()) {
            classWorker = new OrderRequestProductParsing();
        } else {
            pBaton.addError(pCon, OrderPipelineBaton.NOT_SUPPORT_ORDER_TYPE, null,
                    RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                    "pipeline.message.notSupportOrderType");
            return pBaton;
        }
        return classWorker.process(pBaton, pActor, pCon, pFactory);
    }
}
