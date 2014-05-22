package com.cleanwise.view.utils;

import javax.servlet.http.HttpServletRequest;


/**
 * The helper class <code>RequestPropertyNames</code> provides the constants names
 */

public class RequestPropertyNames {

    public static final String PROPERTY_NAME_LOCATE_ACCOUNT_TYPE       = "locateAccountType";
    public static final String PROPERTY_NAME_LOCATE_SITE_TYPE          = "locateSiteType";
  //  public static final String PROPERTY_NAME_LOCATE_DIST_TYPE          = "locateDistType";
    public static final String PROPERTY_NAME_LOCATE_ITEM_TYPE       = "locateItemType";
    public static final String PROPERTY_NAME_LOCATE_DISTRIBUTOR_TYPE          = "locateDistributorType";
    public static final String PROPERTY_NAME_LOCATE_MANUF_TYPE          = "locateManufType";
    public static final String PROPERTY_NAME_LOCATE_CATALOG_TYPE          = "locateCatalogType";

    public static final String PROPERTY_VALUE_LOCATE_ACCOUNT_REPORT    = "locateReportAccount";
    //public static final String PROPERTY_VALUE_LOCATE_DIST_REPORT       = "locateReportDist";
    public static final String PROPERTY_VALUE_LOCATE_SITE_REPORT       = "locateReportSite";
    public static final String PROPERTY_VALUE_LOCATE_ITEM_REPORT    = "locateReportItem";
    public static final String PROPERTY_VALUE_LOCATE_DISTRIBUTOR_REPORT       = "locateReportDistributor";
    public static final String PROPERTY_VALUE_LOCATE_MANUF_REPORT       = "locateReportManuf";
    public static final String PROPERTY_VALUE_LOCATE_CATALOG_REPORT       = "locateReportCatalog";

    public static boolean getIsLocateReportAccountRequest(HttpServletRequest request) {
        boolean isLocateReportAccount = false;
        String locateReportAccount = request.getParameter(PROPERTY_NAME_LOCATE_ACCOUNT_TYPE);
        if (locateReportAccount != null) {
            if (locateReportAccount.equalsIgnoreCase(PROPERTY_VALUE_LOCATE_ACCOUNT_REPORT)) {
                isLocateReportAccount = true;
            }
        }
        return isLocateReportAccount;
    }

    public static boolean getIsLocateReportSiteRequest(HttpServletRequest request) {
        boolean isLocateReportSite = false;
        String locateReportSite = request.getParameter(PROPERTY_NAME_LOCATE_SITE_TYPE);
        if (locateReportSite != null) {
            if (locateReportSite.equalsIgnoreCase(PROPERTY_VALUE_LOCATE_SITE_REPORT)) {
                isLocateReportSite = true;
            }
        }
        return isLocateReportSite;
    }

    public static boolean getIsLocateReportItemRequest(HttpServletRequest request) {
        boolean isLocateReportItem = false;
        String locateReportItem = request.getParameter(PROPERTY_NAME_LOCATE_ITEM_TYPE);
        if (locateReportItem != null) {
            if (locateReportItem.equalsIgnoreCase(PROPERTY_VALUE_LOCATE_ITEM_REPORT)) {
                isLocateReportItem = true;
            }
        }
        return isLocateReportItem;
    }

    public static boolean getIsLocateReportDistributorRequest(HttpServletRequest request) {
        boolean isLocateReportDistributor = false;
        String locateReportDistributor = request.getParameter(PROPERTY_NAME_LOCATE_DISTRIBUTOR_TYPE);
        if (locateReportDistributor != null) {
            if (locateReportDistributor.equalsIgnoreCase(PROPERTY_VALUE_LOCATE_DISTRIBUTOR_REPORT)) {
                isLocateReportDistributor = true;
            }
        }
        return isLocateReportDistributor;
    }
    
    public static boolean getIsLocateReportCatalogRequest(HttpServletRequest request) {
        boolean isLocateReportCatalog = false;
        String locateReportCatalog = request.getParameter(PROPERTY_NAME_LOCATE_CATALOG_TYPE);
        if (locateReportCatalog != null) {
            if (locateReportCatalog.equalsIgnoreCase(PROPERTY_VALUE_LOCATE_CATALOG_REPORT)) {
                isLocateReportCatalog = true;
            }
        }
        return isLocateReportCatalog;
    }

}
