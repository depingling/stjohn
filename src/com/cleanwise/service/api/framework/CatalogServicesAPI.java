/**
 * This application service provides clients with interfaces to retrieve
 * catalog items from either a drilldown and search perspective. The following
 * core application business functionality will be included as part of this
 * application service:
 * 	Establish and maintain categories;
 * 	Establish and maintain products;
 * 	Establish and maintain SKUs and product-SKU relationships;
 * 	Establish and maintain catalogs;
 * 	Display catalog items for viewing by category and product type drilldown;
 * 	Display a particular catalog item by Product and SKU;
 * 	Obtain detailed information about a specific product or SKU;
 * 	Search the product catalog by keyword or other search criteria;
 * 	Retrieve specific product information (service interface);
 *      Display contract catalog filtering;
 *      Send contract prices to the catalog service;
 *      Send contract prices to the shopping cart service;
 *      Establish and maintain contract.
 *
 * Copyright:   Copyright (c) 2001
 * Company:     CleanWise, Inc.
 * @author      Liang Li
 *
 */
 
package com.cleanwise.service.api.framework;

public abstract class CatalogServicesAPI extends ApplicationServicesAPI
{}
