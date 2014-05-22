package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.PipelineDataAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.PipelineData;
import com.cleanwise.service.api.value.PipelineDataVector;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Title:        MultiOrderPipelineActor
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date:         20.11.2006
 * Time:         14:54:52
 * @author       Alexander Chickin, CleanWise, Inc.
 */
public class MultiOrderPipelineActor {
    public String PIPLINE_PROCCESS_STATUS="OFF";
    public String ON="ON";
    public String OFF="OFF";
    private static final int MAX_PIPELINE_LENGTH = 100;

    public void processPipeline(MultiOrderPipelineBaton pMBaton,
                                String pPipelineType,
                                Connection pCon,
                                APIAccess pFactory) throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:ms");
        Date startTime = new Date();
        String startTimeS = sdf.format(startTime);
        String logMessage=new String();
        PipelineDataVector pipelineDV=new PipelineDataVector();
        if(!isActivePiplineProccess()) {
            setPiplineStatusProccess(ON);

            logMessage = " Pipeline: <"+pPipelineType+
                    "> Started at: <"+startTimeS+">";
            DBCriteria dbc = new DBCriteria();
            if(pMBaton.getBypassOptional()) {
                String cond = "(nvl("+ PipelineDataAccess.OPTIONAL+",0) =0 ";
                dbc.addCondition(cond);
            }
            dbc.addEqualTo(PipelineDataAccess.PIPELINE_TYPE_CD,pPipelineType);
            dbc.addEqualTo(PipelineDataAccess.PIPELINE_STATUS_CD, RefCodeNames.PIPELINE_STATUS_CD.ACTIVE);
            dbc.addOrderBy(PipelineDataAccess.PIPELINE_ORDER);
            long startMT=System.currentTimeMillis();
            pipelineDV = PipelineDataAccess.select(pCon,dbc);
        } else {
            String mess = "Wrong OrderPipelineActor usage. Second start of pipeline"+
                    "when the first one is active";
            throw new Exception(mess);
        }

        Date threadStartTime = new Date();
        String threadStartTimeS = sdf.format(threadStartTime);
        try {
            processPipeline(pMBaton,pipelineDV, startTimeS, threadStartTimeS, pCon, pFactory);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            setPiplineStatusProccess(OFF);
        }

        Date endTime = new Date();
        double dur = (double) endTime.getTime()-startTime.getTime();
        dur /= 1000;
        logMessage += " Ended at: <"+sdf.format(endTime)+"> Duration: <"+dur+">@@PP@@";
    }


//------------------------------------------------------------------------------
private void processPipeline(MultiOrderPipelineBaton pMBaton, PipelineDataVector pipelineDV,
                             String pPipelineStartTime,
                             String pPipelineThreadStartTime,
                             Connection pCon,
                             APIAccess pFactory)
        throws Exception
{
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:ms");
    int index = pMBaton.getStepNum();
    int loop = -1;
    while(index<pipelineDV.size()) {
        loop++;
        if(loop>MAX_PIPELINE_LENGTH) {
            String errorMess = "The pipeline step = "+loop +
                    " Probably endless loop. ";
            throw new Exception(errorMess);
        }
        PipelineData pipeline = (PipelineData) pipelineDV.get(index);
        if (pipeline.getClassname() == null) {
            throw new Exception("Classname configured for pipeline: " +
                    pipeline.getPipelineId() + " cannot be null.");
        }
        Class clazz = Thread.currentThread().getContextClassLoader().loadClass(pipeline.getClassname());
        Object scratch = clazz.newInstance();
        if (scratch instanceof MultiOrderPipeline) {


            MultiOrderPipeline poc = (MultiOrderPipeline) scratch;
            String className = pipeline.getClassname();
            int lastPointInd = className.lastIndexOf('.');
            if(lastPointInd>0) className = className.substring(lastPointInd+1);

            Date startTime = new Date();
            String startTimeS = sdf.format(startTime);
            String logMessage = " Pipeline step: <"+className+
                    "> Pipeline stated at: <"+pPipelineStartTime+
                    "> Thread stated at: <"+pPipelineThreadStartTime+
                    "> Step stated at: <"+startTimeS+">";

            try {
                poc.process(pMBaton, this, pCon, pFactory);
            }catch(Exception exc) {
                Date endTime = new Date();
                String endTimeS = sdf.format(endTime);
                double dur = (double) endTime.getTime()-startTime.getTime();
                dur /= 1000;
                logMessage += " Step ended at: <"+endTimeS+
                        "> duration: <"+dur+
                        "> result: <Error>";
                throw exc;
            }
            Date endTime = new Date();
            String endTimeS = sdf.format(endTime);
            double dur = (double) endTime.getTime()-startTime.getTime();
            dur /= 1000;


            logMessage += " Step ended at: <"+endTimeS+
                    "> duration: <"+dur+
                    "> result: <OK>";

            String whatNext = pMBaton.getWhatNext();
            if(MultiOrderPipelineBaton.GO_FIRST_STEP.equals(whatNext)){
                index = 0;
                pMBaton.setStepNum(index);
                continue;
            }
            else if(MultiOrderPipelineBaton.REPEAT.equals(whatNext)){
                continue;
            }
            else if(MultiOrderPipelineBaton.STOP.equals(whatNext)){
                break;
            }
            else {
                index = pMBaton.getStepNum()+1;
                pMBaton.setStepNum(index);
                continue;
            }
        } else {
            throw new Exception("Classname configured for pipeline: " +
                    pipeline.getPipelineId() + " must implement: " + OrderPipeline.class.getName());
        }
    }

}







    private void setPiplineStatusProccess(String status) {
        if(ON.equalsIgnoreCase(status))
            this.PIPLINE_PROCCESS_STATUS=ON;
        else this.PIPLINE_PROCCESS_STATUS=OFF;
    }

    private boolean isActivePiplineProccess()
    {
        return  this.PIPLINE_PROCCESS_STATUS.equals(ON)?true:false;
    }





}
