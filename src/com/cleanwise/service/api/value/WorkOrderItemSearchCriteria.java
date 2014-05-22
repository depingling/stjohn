package com.cleanwise.service.api.value;

import java.io.Serializable;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         13.04.2011
 * Time:         12:43:25
 *
 * @author  Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class WorkOrderItemSearchCriteria implements Serializable {
    private int workOrderId;
    private int assetId;

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public int getAssetId() {
        return assetId;
    }
}
