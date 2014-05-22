package com.cleanwise.service.api.process.workflow;

import com.cleanwise.service.api.value.WorkOrderDetailView;
import com.cleanwise.service.api.value.WorkflowRuleData;
import com.cleanwise.service.api.util.PipelineException;


public class WORuleRequestData extends CommonRuleRequestData{

    private WorkOrderDetailView workOrderDetailView;

    public WORuleRequestData(WorkflowRuleData ruleData,Object objectForProcessing) throws PipelineException {
        super(ruleData,objectForProcessing);
        this.setObjectForProcessing(objectForProcessing);
    }

    public void setObjectForProcessing(Object object) throws PipelineException {

        try{
            this.workOrderDetailView = (WorkOrderDetailView) object;
            objectForProcessing = object;
        } catch(ClassCastException e){
            throw new PipelineException(INCORRECT_OBJECT_TYPE);
        }
    }

    public WorkOrderDetailView getWorkOrderDetailView() {
        return workOrderDetailView;
    }
}
