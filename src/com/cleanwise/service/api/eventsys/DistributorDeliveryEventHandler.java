package com.cleanwise.service.api.eventsys;

import com.cleanwise.service.api.APIServiceAccessException;

import javax.naming.NamingException;

/**
 * Title:        DistributorDeliveryEventHandler
 * Description:  DistributorDeliveryEventHandler implementation for Events which are for Distributor Delivery processing
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * @author       Evgeny Vlasov, CleanWise, Inc.
 *
 */
public class DistributorDeliveryEventHandler extends EventHandler implements Runnable{

    public DistributorDeliveryEventHandler(EventData eventData) throws NamingException, APIServiceAccessException {
        super(eventData);
    }

    public void run() {
        
    }
}
