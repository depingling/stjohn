package com.cleanwise.service.api.session;

/**
 * Title:        Language
 * Description:  Remote Interface for Language Stateless Session Bean
 * Purpose:      Provides access to the services for managing language information.
 * Copyright:    Copyright (c) 2011
 * Company:      eSpendWise, Inc.
 * @author       Srinivas Chittibomma.
 */

import java.util.List;

import com.cleanwise.service.api.value.HistoryData;
import com.cleanwise.service.api.value.HistoryObjectData;
import com.cleanwise.service.api.value.HistorySecurityData;

/**
 * Remote interface for the <code>History</code> stateless session bean.
 */
public interface History extends javax.ejb.EJBObject {
    
    public void addHistory(HistoryData historyData, 
    		List<HistoryObjectData> historyObjectDatas, 
    		List <HistorySecurityData> historySecurityDatas) throws Exception;
}
