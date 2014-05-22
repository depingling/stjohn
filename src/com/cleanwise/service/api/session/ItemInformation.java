package com.cleanwise.service.api.session;

/**
 * Title:        ItemInformation
 * Description:  Remote Interface for ItemInformation Stateless Session Bean
 * Purpose:      Provides access to the methods for retrieving and evaluating item information
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DBCriteria;
import java.util.ArrayList;
 
public interface ItemInformation extends javax.ejb.EJBObject {

    /**
     * Gets the array-like item vector values to be used by the request.
     * @param pCatalogId  the catalog identifier
     * @return ItemDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemDataVector getItemsCollectionByCatalog(int pCatalogId)
    throws RemoteException;

    /**
     * Gets the array-like item vector values to be used by the request.
     * @param pCatalogId  the catalog identifier
     * @param pCategoryId  the category identifier
     * @return ItemDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemDataVector getItemsCollectionByCatalogCategory(int pCatalogId,
    int pCategoryId)
    throws RemoteException;

    /**
     * Gets the array-like item vector values to be used by the request
     * (search by item code).
     * @param pItemCd  the item code
     * @return ItemDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemDataVector getItemsCollectionByItemCode(String pItemCd)
    throws RemoteException;

    /**
     * Gets the array-like item vector values to be used by the request
     * (Search by item type).
     * @param pItemTypeCd  the item type code
     * @return ItemDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemDataVector getItemsCollectionByType(String pItemTypeCd)
    throws RemoteException;

    /**
     * Gets the array-like item vector values to be used by the request.
     * (Search by item UPC).
     * @param pUPCNum  the item UPC number value
     * @return ItemDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemDataVector getItemsCollectionByUPC(String pUPCNum)
    throws RemoteException;

    /**
     * Gets the array-like item vector values to be used by the request.
     * (Search by item name).
     * @param pItemShortDesc  the item name or short description
     * @return ItemDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemDataVector getItemsCollectionByItemShortDesc(String pItemShortDesc)
    throws RemoteException;

    /**
     * Gets the array-like item vector values to be used by the request.
     * (Search by category).
     * @param pCategoryId  the category identifier
     * @return ItemDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemDataVector getItemsCollectionByCategory(int pCategoryId)
    throws RemoteException;

    /**
     * Gets item information values to be used by the request.
     * @param pItemId  the item identifier
     * @return ItemData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemData getItem(int pItemId)
    throws RemoteException;

    /**
     * Gets the array-like item mapping vector values to be used by the request.
     * @param pItemId  the item identifier.
     * @return ItemMappingDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemMappingDataVector getItemMappingsCollection(int pItemId)
    throws RemoteException;

    /**
     * Gets the  item mapping information value to be used by the request.
     * @param pItemId  the item identifier.
     * @param pItemMappingId  the item mapping identifier.
     * @return ItemMappingData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemMappingData getItemMapping(int pItemId, int pItemMappingId)
    throws RemoteException;

    /**
     * Gets the array-like item meta vector values to be used by the request.
     * @param pItemId  the item identifier.
     * @return ItemMetaDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemMetaDataVector getItemMetaValuesCollection(int pItemId)
    throws RemoteException;

    /**
     * Gets the  item meta information value to be used by the request.
     * @param pItemId  the item identifier.
     * @param pItemMetaId  the item meta identifier.
     * @return ItemMetaData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemMetaData getItemMetaValue(int pItemId, int pItemMetaId)
    throws RemoteException;

    /**
     * Gets the array-like item association vector values to be used by the request.
     * @param pItemId  the item identifier.
     * @return ItemAssocDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemAssocDataVector getItemAssociationsCollection(int pItemId)
    throws RemoteException;

    /**
     * Gets the  item association information value to be used by the request.
     * @param pItemId  the item identifier.
     * @param pItemAssocId  the item association identifier.
     * @return ItemAssocData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemAssocData getItemAssoc(int pItemId, int pItemAssocId)
    throws RemoteException;

    /**
     * Gets the array-like item keyword vector values to be used by the request.
     * @param pItemId  the item identifier.
     * @return ItemKeywordDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemKeywordDataVector getItemKeywordsCollection(int pItemId)
    throws RemoteException;

    /**
     * Gets the  item keyword information value to be used by the request.
     * @param pItemId  the item identifier.
     * @param pItemKeywordId  the item keyword identifier.
     * @return ItemKeywordData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemKeywordData getItemKeyword(int pItemId, int pItemKeywordId)
    throws RemoteException;

    /**
     * Gets the array-like item price rules vector values to be used by the request.
     * @param pItemId  the item identifier.
     * @return ItemPriceRuleDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemPriceRuleDataVector getItemPriceRulesCollection(int pItemId)
    throws RemoteException;

    /**
     * Gets the  item price rule information value to be used by the request.
     * @param pItemId  the item identifier.
     * @param pItemRuleId  the item price rule identifier.
     * @return ItemPriceRuleData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemPriceRuleData getItemPriceRule(int pItemId, int pItemRuleId)
    throws RemoteException;

    /**
     * Gets the array-like item price rule associations vector values to be used by the request.
     * @param pItemId  the item identifier.
     * @param pRuleId  the rule identifier.
     * @return ItemPriceRuleAssocDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemPriceRuleAssocDataVector getItemPriceRuleAssociationsCollection(int pItemId,
    int pRuleId)
    throws RemoteException;

    /**
     * Gets the  item price rule association information value to be used by the request.
     * @param pItemId  the item identifier.
     * @param pPriceRuleAssocId  the item price rule association identifier.
     * @return ItemPriceRuleAssocData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemPriceRuleAssocData getItemPriceRuleAssoc(int pItemId,
    int pPriceRuleAssocId)
    throws RemoteException;

    /**
     * Derive the item price by evaluating the business entity associations and the price weighting factors.
     * @param pItemId  the item identifier.
     * @param pItemRuleId  the item price rule identifier.
     * @return EvaluatedItemPriceData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemPriceData evaluateItemPriceRules(int pItemId,
    int pItemRuleId)
    throws RemoteException;

    /**
     * Gets item information values to be used by the request.
     * @param pItemId  the item identifier
     * @return ItemContentData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemData getItemInfo(int pItemId)
    throws RemoteException;

    /**
     * determines if the item effective date is within the current date.
     * @param pItemId  the item identifier
     * @param pEffDate  the user effective date
     * @param pNow  the current date
     * @return true if the item effective date is equal to or after the current date.
     * @throws            RemoteException Required by EJB 1.0
     */
    public boolean checkItemEffDate(int pItemId, Date pEffDate, Date pNow)
    throws RemoteException;

    /**
     * determines if the item expiration date is within the current date.
     * @param pItemId  the item identifier
     * @param pExpDate  the user expiration date
     * @param pNow  the current date
     * @return true if the item expiration date is equal to or before the current date.
     * @throws            RemoteException Required by EJB 1.0
     */
    public boolean checkItemExpDate(int pItemId, Date pExpDate, Date pNow)
    throws RemoteException;

    /**
     * determines if the price rule effective date is within the current date.
     * @param pRuleId  the rule identifier
     * @param pEffDate  the user effective date
     * @param pNow  the current date
     * @return true if the rule effective date is equal to or after the current date.
     * @throws            RemoteException Required by EJB 1.0
     */
    public boolean checkPriceRuleEffDate(int pRuleId, Date pEffDate, Date pNow)
    throws RemoteException;

    /**
     * determines if the price rule expiration date is within the current date.
     * @param pRuleId  the rule identifier
     * @param pExpDate  the user expiration date
     * @param pNow  the current date
     * @return true if the rule expiration date is equal to or before the current date.
     * @throws            RemoteException Required by EJB 1.0
     */
    public boolean checkPriceRuleExpDate(int pRuleId, Date pExpDate, Date pNow)
    throws RemoteException;


    public void updateDistributorItemMapping
    (int pItemId, int pDistId,
    String pDistUom, String pDistPack, String pDistSku, BigDecimal pUomConvMultiplier, String pUserName)
    throws RemoteException;

    public void removeDistributorItemMapping
    (int pItemId, int pDistId )
    throws RemoteException;

    public void addMasterItems(HashMap pMI, String pUser, int pStoreId, int pCatalogId) throws RemoteException;

    public void updateDMSIItems(HashMap pMI, String pUser, int pStoreId, int pCatalogId) throws RemoteException;

    /**
     *Fetches an AggregateItemViewVector populated based off the supplied catalogIds.
     *@param catalogIds filters base off the supplied catalogIds.  If empty an empty list is returned.
     *@throws RemoteException if an error occurs
     */
    public AggregateItemViewVector getAggregateItemViewList(int pItemId,IdVector pCatalogIds) throws RemoteException;

    /**
     *Fetches an AggregateItemViewVector populated based off the supplied pAccountIds and pDistributorIds.
     *@param pStoreIds if the pStoreIds is empty or null then it will not be used in the filtering
     *@param pAccountIds if the pAccountIds is empty or null then it will not be used in the filtering
     *@param pDistributorIds if the pDistributorIds is empty or null then it will not be used in the filtering
     *@throws RemoteException if an error occurs
     */
    public AggregateItemViewVector getAggregateItemViewList(int pItemId,
    IdVector pStoreIds, IdVector pAccountIds, IdVector pDistributorIds) throws RemoteException;

    /**
     *updates the supplied aggregate item view to the appropriate tables.  Will insert relational
     *table records if they are not there.
     *@param the @see AggregateItemViewVector to update
     *@param the item id we are talking about, this is necessary when inserting relational tables
     *@param the users name doing the update
     *@param boolean indicates if we are preforming Catalog mods
     *@param boolean indicates if we are preforming Contract mods
     *@param boolean indicates if we are preforming Order Guide mods
     *@throws RemoteException if an error occurs
     */
    public void updateAggregateItemViewList(
    AggregateItemViewVector pAggregateItemViewVector,int pItemId, String pUserName,
    boolean pItemsToCatalog,boolean pItemsToContract,boolean pItemsToOrderGuide)
    throws RemoteException;

    /**
     *removes the supplied aggregate items from the catalogs and contracts that they are in.  No
     *action is preformed if they are not in a catalog or contract.
     *@param the @see AggregateItemViewVector to remove
     *@param boolean indicates if we are preforming Catalog mods
     *@param boolean indicates if we are preforming Contract mods
     *@param boolean indicates if we are preforming Order Guide mods
     *@throws RemoteException if an error occurs
     */
    public void removeAggregateItemViewList(
    AggregateItemViewVector pAggregateItemViewVector,
    boolean pItemsToCatalog,boolean pItemsToContract,boolean pItemsToOrderGuide)
    throws RemoteException;

    /**
     *Gets list of uploaded tables
     *@param pStoreId the store id
     *@param pFileTempl search pattern
     *@param boolean ppProcessedFl filters out processed tables if false
     *@throws RemoteException if an error occurs
     */
    public UploadDataVector getXlsTables(int pStoreId, String pFileTempl, boolean pProcessedFl)
    throws RemoteException;

    /**
     *Gets uploaded table (with data)
     *@param pStoreId the store id
     *@param pUploadId the table id
     *@return XlsTableView object (table header +  data)
     *@throws RemoteException if an error occurs
     */
    //public XlsTableView getXlsTableById(int pStoreId, int pUploadId)
    //throws RemoteException;

    /**
     *Gets uploaded skus (with data)
     *@param pStoreId the store id
     *@param pUploadId the table id
     *@param pUploadSkuIds list of ids (whole table if null)
     *@return XlsTableView object (table header +  data)
     *@throws RemoteException if an error occurs
     */
    public XlsTableView getSkuXlsTableById(int pStoreId, int pUploadId, IdVector pUploadSkuIds)
    throws RemoteException;


     /**
     *Saves xls table
     *@param pStoreId the store id
     *@param pXlsTable table to save
     *@param boolean pOwerwriteFl forces to overwrite previously uploaded file
     *@param String pUser uder login name
     *@throws RemoteException if an error occurs
     */
    //public XlsTableView saveXlsSpreadsheet(int pStoreId, XlsTableView pXlsTable,
    //        boolean pOverwriteFl, String pUser)
    //throws RemoteException;


    /**
     *Saves uploadeSkus
     *@param pStoreId the store id
     *@param pXlsTable table to save
     *@param boolean pOwerwriteFl forces to overwrite previously uploaded file
     *@param String pUser uder login name
     *@return XlsTable id (uploadId)
     *@throws RemoteException if an error occurs
     */
    public int saveSkuXlsTable(int pStoreId, XlsTableView pXlsTable,
            boolean pOverwriteFl, String pUser)
    throws RemoteException;

    /**
     *Matches uploaded skus to store catalog usind manufacturer and distributor skus
     *@param pStoreId the store id
     *@param pUploadId uploaded table id
     *@param pUploadSkuIds tables skus to match (whole table if null)
     *@throws RemoteException if an error occurs
     */

    public UploadSkuViewVector matchSkuXlsTable(int pStoreId, int pUploadId, IdVector pUploadSkuIds)
    throws RemoteException;

    /**
     *Gets already matched skus
     *@param pStoreId the store id
     *@param pUploadId uploaded table id
     *@param pUploadSkuIds tables skus to match (whole table if null)
     *@throws RemoteException if an error occurs
     */
    public UploadSkuViewVector getMatchedItems(int pStoreId, int pUploadId, IdVector pUploadSkuIds)
    throws RemoteException;

    /**
     *Updates upload skus
     *@param uploadSkus upload skus to update
     *@param String pUser uder login name
     *@throws RemoteException if an error occurs
     */
    public void updateUploadSkus (UploadSkuView[] uploadSkus, String pUser)
    throws RemoteException;

    /**
     * Gets the catalog ids associated with the supplied itemId  where
     * CatalogTypeCd not Store and not System
     *@param pUploadSkuIds tables skus to match (all table if null)
     *@return  a set of UploadSkuView objects
     *@exception  RemoteException
     */
    public UploadSkuViewVector getMatchedUploadSkus(int pUploadId, IdVector pUploadSkuIds)
    throws RemoteException;

     /**
     *Function returns the catalog ids witch is associated with the supplied item Id
     *exclude CatalogTypeCd equal Store and System
     * @param  itemId   Description of Parameter
     * @return   The CatalogIdVector value
     * @throws RemoteException if an error occurs
     */
    public   IdVector getCatalogIdsExcludeStoreAndSystem(int  itemId)  throws RemoteException;

    public IdVector getCatalogIds(int itemId) throws RemoteException;

    /**
     * Gets the array-like item vector values to be used by the request.
     * (Search by item ids).
     * @param itemIds  the item ids
     * @return ItemDataVector
     * @throws RemoteException Required by EJB 1.0
     */
    public ItemDataVector getItemCollection(IdVector itemIds) throws RemoteException;

    public IdVector getItemsCollectionByItemShortDescription(String shortDescription)  throws RemoteException;

    public ItemMappingDataVector getItemMapping(int pBusEntityItemId, String pSku)
        throws RemoteException;

    /**
	 * Return doc urls of items for concrete site, ordered during last
	 * year.
	 *
	 * @param pSiteId
	 *            Site's ID.
	 * @return
	 * @throws RemoteException
	 */
	public ItemDataDocUrlsViewVector getOrderedItemDataForLastYear(int pSiteId)
			throws RemoteException;

	/**
	   * Gets the list of ItemMappingData values to be used by the request.
	   * (Search by distributor's id, sku and uom).
	   * @param distributorId  distributor id
	   * @param distSku  distributor item sku
	   * @param distUom  distributor item uom
	   * @return ItemMappingDataVector
	   * @throws            RemoteException Required by EJB 1.0
	   */
	public ItemMappingDataVector getDistItemMappingCollectionByDistSkuAndUom(int distributorId, String distSku, String distUom)
	throws RemoteException ;

    /**
       * Gets the list of ItemMappingData values to be used by the request.
       * (Search by DBCriteria).
       * @param DBCriteria dbc
       * @return ItemMappingDataVector
       * @throws            RemoteException Required by EJB 1.0
       */
        public ItemMappingDataVector getItemMappingsCollection(DBCriteria dbc) throws RemoteException;
        
        public ItemViewVector getStagedItemVector (List pCriteria) throws RemoteException;
        public StagedItemData getStagedItemData(int stagedItemId) throws RemoteException;
        public StagedItemData updateStagedItemData(StagedItemData pItemData, String userName) throws RemoteException;
        public StagedItemAssocDataVector getStagedItemAssocDataVector(int stagedItemId) throws RemoteException;
        public StagedItemAssocData updateStagedItemAssocData(StagedItemAssocData pItemAssocData, String userName) throws RemoteException;
        public void removeStagedItemData(int itemId) throws RemoteException;
        public boolean canEditMasterItem(int itemId, int storeId) throws RemoteException;
        public IdVector checkItemStoreUnique(ArrayList manufNames, String ManufSku, String uom, int storeId) throws RemoteException;
        
        public ItemDataDocUrlsViewVector getOrderedItemDataForLastYear(int pSiteId, String userLocale, String countryCode, String storeLocale)
                throws RemoteException;
        public HashMap collectDiverseyWebServicesURLs(HashMap itemManufacturerHM, String userLocale, String countryCode, String storeLocale) throws RemoteException;
        public ItemDataDocUrlsViewVector addDiverseyWebServicesURLs(ItemDataDocUrlsViewVector itemsAll, HashMap itemDiverseyUrl, HashMap itemShortDescHM) throws RemoteException;
        
        // For usage with E3 Storage vs Database
        public ContentData getContent(String pPath) throws RemoteException;
}
