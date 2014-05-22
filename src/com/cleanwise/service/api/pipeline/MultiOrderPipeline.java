package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.PipelineException;

import java.sql.Connection;

/**
 * Title:        MultiOrderPipeline
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date:         20.11.2006
 * Time:         14:54:52
 * @author       Alexander Chickin, TriniSoft, Inc.
 */
public interface MultiOrderPipeline  {
    /**
     * Process this pipeline.
     * @param pMBaton
     * @param pMActor
     * @param pCon
     * @param pFactory
     * @return
     * @throws PipelineException
     */
    public void process(MultiOrderPipelineBaton pMBaton, 
                MultiOrderPipelineActor pMActor,
                Connection pCon,
                APIAccess pFactory)
    throws PipelineException;
}
