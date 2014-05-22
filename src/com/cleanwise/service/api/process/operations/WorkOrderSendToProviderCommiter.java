package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.WorkOrderDetailView;
import com.cleanwise.service.api.session.WorkOrder;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.WorkOrderData;


/**
 */
public class WorkOrderSendToProviderCommiter {
    private String className = "WOSendToProviderCommiter";
    private String methodName = "process";

    public void process(WorkOrderDetailView workOrderdetail, UserData user) throws Exception {
        WorkOrder wrkEjb = APIAccess.getAPIAccess().getWorkOrderAPI();
        
        WorkOrderData actualWOD = wrkEjb.getWorkOrder(workOrderdetail.getWorkOrder().getWorkOrderId());
        if (actualWOD != null && 
                (RefCodeNames.WORK_ORDER_STATUS_CD.SENDING_TO_PROVIDER.equals(actualWOD.getStatusCd()) ||
                 RefCodeNames.WORK_ORDER_STATUS_CD.NEW_REQUEST.equals(actualWOD.getStatusCd()) ||
                 RefCodeNames.WORK_ORDER_STATUS_CD.APPROVED.equals(actualWOD.getStatusCd()))) {
            wrkEjb.updateStatus(workOrderdetail.getWorkOrder().getWorkOrderId(), RefCodeNames.WORK_ORDER_STATUS_CD.SENT_TO_PROVIDER, user.getUserName());
        }

    }

}
