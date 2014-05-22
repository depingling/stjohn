package com.cleanwise.service.api.process.workflow;

import com.cleanwise.service.api.value.WorkOrderDetailView;


public class WOProcessingData extends CommonProcessingData{
    
    private WorkOrderDetailView workOrderDetailView;
    private static final long serialVersionUID = -1512549391251222451L;

    public WOProcessingData(WorkOrderDetailView workOrderDetailView) {
        super();
        this.workOrderDetailView = workOrderDetailView;
    }

    public WorkOrderDetailView getWorkOrderDetailView() {
        return workOrderDetailView;
    }

    public void setWorkOrderDetailView(WorkOrderDetailView workOrderDetailView) {
        this.workOrderDetailView = workOrderDetailView;
    }
}
