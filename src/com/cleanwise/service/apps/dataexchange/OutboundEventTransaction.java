package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.value.*;


public interface OutboundEventTransaction {

  public void setOutboundTransactionsToProcess(OutboundEDIRequestDataVector transactionsToProcess);
  public void build() throws Exception;
  public ProcessOutboundResultView getProcessOutboundResultData();
  public StringBuffer getTranslationReport();
  public void setTradingPartnerDescView(TradingPartnerDescView partnerDescView);
  public void setDistErpNum(String distErpNum);
  public String getDistErpNum();      

}
