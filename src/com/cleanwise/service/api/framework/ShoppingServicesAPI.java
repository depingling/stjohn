/**
 * This application service is responsible for providing services pertaining
 * to shopping cart functionality. This includes session and container
 * management. This service also provides clients with interfaces to add an
 * order template or order guide to the shopping cart. This service will
 * interface with the product workflow services for review and approval
 * processing. The following core application business functionality will be
 * included as part of this application service:
 * 	Search order guide by short description;
 * 	Add items to the shopping cart from the Janitor's Closet;
 * 	Add items to the shopping cart;
 * 	Remove items from the shopping cart;
 * 	Modify items in the shopping cart;
 * 	View shopping cart contents;
 * 	Calculate order price;
 * 	Save shopping cart items in persistent storage;
 * 	Reorder a previously ordered product;
 * 	Initiate workflow coordination (service interface).
 *
 * Copyright:   Copyright (c) 2001
 * Company:     CleanWise, Inc.
 * @author      Liang Li
 *
 */
 
package com.cleanwise.service.api.framework;

public abstract class ShoppingServicesAPI extends ApplicationServicesAPI
{}
