package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.value.StoreOrderChangeRequestData;

public class StoreOrderPipelineBaton   extends OrderPipelineBaton {
  StoreOrderChangeRequestData storeOrderChangeRequestData;
  public StoreOrderChangeRequestData getStoreOrderChangeRequestData(){
    return storeOrderChangeRequestData;
  }
  public void setStoreOrderChangeRequestData(StoreOrderChangeRequestData storeOrderChangeRequestData){
    this.storeOrderChangeRequestData = storeOrderChangeRequestData;
  }
}
