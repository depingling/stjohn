package com.cleanwise.service.api.session;

/**
 * Title:        DataWarehouse
 * Description:  RemoteInterface for DataWarehouse Stateless Session Bean
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 *
 * @author Alexey Lukovnikov
 */

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.EJBObject;

public interface DataWarehouse extends EJBObject {
   public static final String TBL_DW_SITE_DIM = "DW_SITE_DIM";
   public static final String TBL_DW_ACCOUNT_DIM = "DW_ACCOUNT_DIM";
   public static final String TBL_DW_STORE_DIM = "DW_STORE_DIM";
   public static final String TBL_DW_CATEGORY_DIM = "DW_CATEGORY_DIM";
   public static final String TBL_DW_ITEM_DIM = "DW_ITEM_DIM";
   public static final String TBL_DW_DATE_DIM = "DW_DATE_DIM";
   public static final String TBL_DW_REGION_DIM = "DW_REGION_DIM";
   public static final String TBL_DW_SALES_REP_DIM = "DW_SALES_REP_DIM";
   public static final String TBL_DW_MANUFACTURER_DIM = "DW_MANUFACTURER_DIM";
   public static final String TBL_DW_DISTRIBUTOR_DIM = "DW_DISTRIBUTOR_DIM";
   public static final String TBL_DW_ITEM_DISTRIBUTOR = "DW_ITEM_DISTRIBUTOR";
   public static final String TBL_DW_ORDER_FACT = "DW_ORDER_FACT";
   public static final String TBL_DW_INVOICE_FACT = "DW_INVOICE_FACT";

    public void updateDimDist(String dbSchemaGeneral, String dbSchemaDim,
            List<Integer> storeIds, Integer jdStoreId, int dwStoreDimId) throws RemoteException;
    public void updateDimDist(String dbSchemaGeneral,
               String dbSchemaDim, List<Integer> storeIds, Integer jdStoreId) throws RemoteException;
    public void updateDimManufacturer(String dbSchemaGeneral,
            String dbSchemaDim, List<Integer> storeIds, Integer jdStoreId, int dwStoreDimId )
            throws RemoteException;
    public void updateDimManufacturer(String dbSchemaGeneral,
               String dbSchemaDim, List<Integer> storeIds, Integer jdStoreId)
        throws RemoteException ;
    /**
     * Fills the DW_CUSTOMER_DIM table in the "Data Warehouse".
     * Use the parameter 'dbClwNamespace' to define the schema for 'CLW'-tables with data.
     * Use the parameter 'dbDwNamespace' to define the schema for the tables from 'Data Warehouse'.
     * The parameter 'storeId' - is the identifier of store (for example 'JanPak') to explore.
     * The parameter 'jdStoreId' - is the identifier of 'JohnsonDiversey' store.
     */
    public void fillDwCustomerDimension(String userName,
        String dbClwNamespace, String dbDwNamespace, int exploredStoreId,
        int linkedStoreId) throws SQLException, RemoteException, Exception;

    /**
     * Fills the DW_ITEM_DIM table in the "Data Warehouse".
     * Use the parameter 'storeSchemaName' to define the schema for 'CLW'-tables with data.
     * Use the parameter 'dbSchemaName' to define the schema for the tables from 'Data Warehouse'.
     * The parameter 'store1Id' - the identifier of the first store (for example 'JanPak') to explore.
     * The parameter 'store2Id' - the identifier of the second store (for example 'JohnsonDiversey') to explore.
     */
    public void fillDwItemDimension(String storeSchemaName, Integer store1Id, Integer store2Id, String dwSchemaName)
            throws RemoteException, Exception;

    /**
     * Fills the DW_ITEM_DISTRIBUTOR table in the "Data Warehouse".
     * Use the parameter 'storeSchemaName' to define the schema for 'CLW'-tables with data.
     * Use the parameter 'dbSchemaName' to define the schema for the tables from 'Data Warehouse'.
     */
    public void fillDwItemDistributorTable(String userName, String dbClwNamespace,
        String dbDwNamespace) throws RemoteException, Exception;

    /**
     * Fills the DW_ORDER_FACT table in the "Data Warehouse".
     * Use the parameter 'storeSchemaName' to define the schema for 'CLW'-tables with data.
     * Use the parameter 'dbSchemaName' to define the schema for the tables from 'Data Warehouse'.
     */
    public StringBuffer fillDwOrderFactTable(int pStoreId, String dbClwNamespace,
                                     String dbDwNamespace, String dbLink, int pProcessId) throws RemoteException, Exception ;
    /**
     * Fills any DW_..._DIM table in the "Data Warehouse".
     * Use the parameter 'storeSchemaName' to define the schema for 'CLW'-tables with data.
     * Use the parameter 'dbSchemaName' to define the schema for the tables from 'Data Warehouse'.
     */
    public StringBuffer fillDwAnyDimByStore(int pStoreId, int pLinkedStoreId, String dimName, String dbClwNamespace,
                              String dbDwNamespace, String dbLink, int pProcessId) throws RemoteException, Exception ;

    /**
     * Fills the DW_INVOICE_FACT table in the "Data Warehouse".
     * Use the parameter 'storeSchemaName' to define the schema for 'CLW'-tables with data.
     * Use the parameter 'dbSchemaName' to define the schema for the tables from 'Data Warehouse'.
     */
    public StringBuffer fillDwInvoiceFactTable(int pStoreId, String dbClwNamespace,
                                     String dbDwNamespace, String dbLink, String mandatoryProperties, Integer percentOfErrors, int pProcessId) throws RemoteException,Exception ;


}
