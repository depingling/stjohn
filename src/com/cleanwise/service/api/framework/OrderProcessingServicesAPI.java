/**
 * This application service provides clients with an interface for all
 * the tasks associated with order processing from the shopping cart,
 * excluding order posting to the target ERP application or customer order
 * notification via e-mail. Unlike the Shopping service, functionality in
 * this service will use secure sockets to perform business functions. The
 * following core application business functionality will be included as
 * part of this application service:
 * 	Order information capture, including billing/shipping address and credit card information;
 * 	Apply special pricing functionality based on mail code or customer;
 * 	Save order to the order capture database;
 * 	Post the order to either the target ERP or email, including auto-posting if applicable;
 * 	Customer email order notification.
 *
 * This service has a customer view, whereas the shopping service does not.
 *
 * Copyright:   Copyright (c) 2001
 * Company:     CleanWise, Inc.
 * @author      Liang Li
 *
 */
 
package com.cleanwise.service.api.framework;

public abstract class OrderProcessingServicesAPI extends ApplicationServicesAPI
{}
