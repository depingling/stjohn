/**
 * This application service provides clients and other application services
 * with interfaces to define the workflow review and approval process and
 * routing for requests. This service is also responsible for displaying the
 * well-formed product in a WSYWYG fashion to the user. The following core
 * application business functionality will be included as part of this
 * application service:
 * 	Define the workflow;
 * 	Define the workflow routing, roles, and actions;
 * 	Search the workflow by shopping request;
 * 	Obtain detail workflow history and status about a specific shopping request;
 * 	Initiate workflow notification (service interface).
 *
 * Copyright:   Copyright (c) 2001
 * Company:     CleanWise, Inc.
 * @author      Liang Li
 *
 */

package com.cleanwise.service.api.framework;

public abstract class WorkflowServicesAPI extends ApplicationServicesAPI
{}
