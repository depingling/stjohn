package com.cleanwise.service.api.session;

/**
 * Title:        ItemInformationBean
 * Description:  Bean implementation for ItemInformation Stateless Session Bean
 * Purpose:      Provides access to the methods for retrieving and evaluating item information
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import javax.naming.NamingException;
import java.rmi.*;
import java.sql.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.math.BigDecimal;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.view.forms.MsdsSpecsForm;
import oracle.jdbc.driver.*;

import org.apache.log4j.Category;

//new for the Enhancement STJ-3778: Begin
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;
//new for the Enhancement STJ-3778: End

public class ItemInformationBean extends BusEntityServicesAPI {
    /**
     *
     */
	private static final Category log = Category.getInstance(ItemInformationBean.class);

    private final static String className = "ItemInformationBean";

	static final int SKU_MINIMUM = 10000;

	public ItemInformationBean() {}

    /**
     *
     */
    public void ejbCreate() throws CreateException, RemoteException {}


    /**
     * Gets the array-like item vector values to be used by the request.
     * @param pCatalogId  the catalog identifier
     * @return ItemDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemDataVector getItemsCollectionByCatalog(int pCatalogId)
        throws RemoteException {
        ItemDataVector idv = new ItemDataVector();

        if ( pCatalogId > 0 )
        {
            // Not implemented yet.
            throw new RemoteException( "Filter not implementer, pCatalogId="
                                       + pCatalogId );
        }
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addGreaterThan(ItemDataAccess.ITEM_ID, 0);
            dbc.addOrderBy(ItemDataAccess.SKU_NUM);
            idv = ItemDataAccess.select(conn,dbc);
        }
        catch (Exception e) {
            logError("getItemsCollectionByCatalog: " + e);
        }
        finally {
            closeConnection(conn);
        }

        return idv;
    }

    /**
     * Gets the array-like item vector values to be used by the request.
     * @param pCatalogId  the catalog identifier
     * @param pCategoryId  the category identifier
     * @return ItemDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemDataVector getItemsCollectionByCatalogCategory(int pCatalogId,
    int pCategoryId)
    throws RemoteException {
        return new ItemDataVector();
    }

    /**
     * Gets the array-like item vector values to be used by the request
     * (search by item code).
     * @param pItemCd  the item code
     * @return ItemDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemDataVector getItemsCollectionByItemCode(String pItemCd)
    throws RemoteException {
        return new ItemDataVector();
    }

    /**
     * Gets the array-like item vector values to be used by the request
     * (Search by item type).
     * @param pItemTypeCd  the item type code
     * @return ItemDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemDataVector getItemsCollectionByType(String pItemTypeCd)
    throws RemoteException {
        return new ItemDataVector();
    }

    /**
     * Gets the array-like item vector values to be used by the request.
     * (Search by item UPC).
     * @param pUPCNum  the item UPC number value
     * @return ItemDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemDataVector getItemsCollectionByUPC(String pUPCNum)
    	throws RemoteException {

	ItemDataVector itemDV = null;
	ItemMetaDataVector itemMetaDV = null;
	Connection con = null;

	try {
            con = getConnection();
	    DBCriteria dbItemMetaC=new DBCriteria();
	    dbItemMetaC.addEqualTo(ItemMetaDataAccess.NAME_VALUE, ProductData.UPC_NUM);
	    dbItemMetaC.addEqualTo(ItemMetaDataAccess.CLW_VALUE, pUPCNum);
	    dbItemMetaC.addOrderBy(ItemMetaDataAccess.ITEM_ID + "," + ItemMetaDataAccess.ITEM_META_ID);
	    itemMetaDV = ItemMetaDataAccess.select(con, dbItemMetaC);

            if (itemMetaDV != null) {
                IdVector itemIds = new IdVector();
                int size = itemMetaDV.size();
                for (int i = 0; i < size; i++) {
                    ItemMetaData itemMetaD = (ItemMetaData) itemMetaDV.get(i);
                        itemIds.add(new Integer(itemMetaD.getItemId()));
                }

                if (itemIds.size() > 0) {
                    itemDV = ItemDataAccess.select(con, itemIds);
                }
            }

        } catch (Exception e) {
            logError("getItemsCollectionByUPC: " + e.toString());
        }
        finally {
            closeConnection(con);
        }

        return itemDV;
    }

    /**
     * Gets the array-like item vector values to be used by the request.
     * (Search by item name).
     * @param pItemShortDesc  the item name or short description
     * @return ItemDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemDataVector getItemsCollectionByItemShortDesc(String pItemShortDesc)
    throws RemoteException {
        return new ItemDataVector();
    }

    /**
     * Gets the array-like item vector values to be used by the request.
     * (Search by category).
     * @param pCategoryId  the category identifier
     * @return ItemDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemDataVector getItemsCollectionByCategory(int pCategoryId)
    throws RemoteException {
        return new ItemDataVector();
    }

    /**
     * Gets item information values to be used by the request.
     * @param pItemId  the item identifier
     * @return ItemData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemData getItem(int pItemId)
    throws RemoteException {
        return ItemData.createValue();
    }

    /**
     * Gets the array-like item mapping vector values to be used by the request.
     * @param pItemId  the item identifier.
     * @return ItemMappingDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemMappingDataVector getItemMappingsCollection(int pItemId)
    throws RemoteException {
        return new ItemMappingDataVector();
    }

    /**
     * Gets the  item mapping information value to be used by the request.
     * @param pItemId  the item identifier.
     * @param pItemMappingId  the item mapping identifier.
     * @return ItemMappingData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemMappingData getItemMapping(int pItemId, int pItemMappingId)
    throws RemoteException {
        return ItemMappingData.createValue();
    }

    public ItemMappingDataVector getItemMapping(int pBusEntityItemId, String pSku)
        throws RemoteException {

        Connection conn = null;
        try {
            conn = getConnection();
	        DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, pBusEntityItemId);
            dbc.addEqualTo(ItemMappingDataAccess.ITEM_NUM, pSku);

            ItemMappingDataVector iMappgDataV = ItemMappingDataAccess.select(conn, dbc);
            return iMappgDataV;

        } catch(Exception exc) {
            exc.printStackTrace();
            if(conn!=null) {
                try {
                    conn.close();
                } catch(Exception exc1) {}
            }
            throw new RemoteException(exc.getMessage());
        }finally{
        	closeConnection(conn);
        }
    }

    /**
     * Gets the array-like item meta vector values to be used by the request.
     * @param pItemId  the item identifier.
     * @return ItemMetaDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemMetaDataVector getItemMetaValuesCollection(int pItemId)
    throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
	    DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID,pItemId);
            ItemMetaDataVector itemMetaDV =
                    ItemMetaDataAccess.select(conn,dbc);
            return itemMetaDV;
        } catch(Exception exc) {
            exc.printStackTrace();
            if(conn!=null) {
                try {
                    conn.close();
                } catch(Exception exc1) {}
            }
            throw new RemoteException(exc.getMessage());
        }finally{
        	closeConnection(conn);
        }
    }

    /**
     * Gets the  item meta information value to be used by the request.
     * @param pItemId  the item identifier.
     * @param pItemMetaId  the item meta identifier.
     * @return ItemMetaData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemMetaData getItemMetaValue(int pItemId, int pItemMetaId)
    throws RemoteException {
        return ItemMetaData.createValue();
    }

    /**
     * Gets the array-like item association vector values to be used by the request.
     * @param pItemId  the item identifier.
     * @return ItemAssocDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemAssocDataVector getItemAssociationsCollection(int pItemId)
    throws RemoteException {
        return new ItemAssocDataVector();
    }

    /**
     * Gets the  item association information value to be used by the request.
     * @param pItemId  the item identifier.
     * @param pItemAssocId  the item association identifier.
     * @return ItemAssocData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemAssocData getItemAssoc(int pItemId, int pItemAssocId)
    throws RemoteException {
        return ItemAssocData.createValue();
    }

    /**
     * Gets the array-like item keyword vector values to be used by the request.
     * @param pItemId  the item identifier.
     * @return ItemKeywordDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemKeywordDataVector getItemKeywordsCollection(int pItemId)
    throws RemoteException {
        return new ItemKeywordDataVector();
    }

    /**
     * Gets the  item keyword information value to be used by the request.
     * @param pItemId  the item identifier.
     * @param pItemKeywordId  the item keyword identifier.
     * @return ItemKeywordData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemKeywordData getItemKeyword(int pItemId, int pItemKeywordId)
    throws RemoteException {
        return ItemKeywordData.createValue();
    }

    /**
     * Gets the array-like item price rules vector values to be used by the request.
     * @param pItemId  the item identifier.
     * @return ItemPriceRuleDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemPriceRuleDataVector getItemPriceRulesCollection(int pItemId)
    throws RemoteException {
        return new ItemPriceRuleDataVector();
    }

    /**
     * Gets the  item price rule information value to be used by the request.
     * @param pItemId  the item identifier.
     * @param pItemRuleId  the item price rule identifier.
     * @return ItemPriceRuleData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemPriceRuleData getItemPriceRule(int pItemId, int pItemRuleId)
    throws RemoteException {
        return new ItemPriceRuleData();
    }

    /**
     * Gets the array-like item price rule associations vector values to be used by the request.
     * @param pItemId  the item identifier.
     * @param pRuleId  the rule identifier.
     * @return ItemPriceRuleAssocDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemPriceRuleAssocDataVector getItemPriceRuleAssociationsCollection(int pItemId,
    int pRuleId)
    throws RemoteException {
        return new ItemPriceRuleAssocDataVector();
    }

    /**
     * Gets the  item price rule association information value to be used by the request.
     * @param pItemId  the item identifier.
     * @param pPriceRuleAssocId  the item price rule association identifier.
     * @return ItemPriceRuleAssocData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemPriceRuleAssocData getItemPriceRuleAssoc(int pItemId,
    int pPriceRuleAssocId)
    throws RemoteException {
        return new ItemPriceRuleAssocData();
    }

    /**
     * Derive the item price by evaluating the business entity associations and the price weighting factors.
     * @param pItemId  the item identifier.
     * @param pItemRuleId  the item price rule identifier.
     * @return EvaluatedItemPriceData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemPriceData evaluateItemPriceRules(int pItemId,
    int pItemRuleId)
    throws RemoteException {
        return new ItemPriceData();
    }

    /**
     * Gets item information values to be used by the request.
     * @param pItemId  the item identifier
     * @return ItemContentData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemData getItemInfo(int pItemId) throws RemoteException {
        Connection conn = null;
        ItemData item = null;
        try {
            conn = getConnection();
            item = ItemDataAccess.select(conn, pItemId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.toString());
        } finally {
            closeConnection(conn);
        }
        return item;
    }

    /**
     * determines if the item effective date is within the current date.
     * @param pItemId  the item identifier
     * @param pEffDate  the user effective date
     * @param pNow  the current date
     * @return true if the item effective date is equal to or after the current date.
     * @throws            RemoteException Required by EJB 1.0
     */
    public boolean checkItemEffDate(int pItemId, Date pEffDate, Date pNow)
    throws RemoteException {
        return true;
    }

    /**
     * determines if the item expiration date is within the current date.
     * @param pItemId  the item identifier
     * @param pExpDate  the user expiration date
     * @param pNow  the current date
     * @return true if the item expiration date is equal to or before the current date.
     * @throws            RemoteException Required by EJB 1.0
     */
    public boolean checkItemExpDate(int pItemId, Date pExpDate, Date pNow)
    throws RemoteException {
        return true;
    }

    /**
     * determines if the price rule effective date is within the current date.
     * @param pRuleId  the rule identifier
     * @param pEffDate  the user effective date
     * @param pNow  the current date
     * @return true if the rule effective date is equal to or after the current date.
     * @throws            RemoteException Required by EJB 1.0
     */
    public boolean checkPriceRuleEffDate(int pRuleId, Date pEffDate, Date pNow)
    throws RemoteException {
        return true;
    }

    /**
     * determines if the price rule expiration date is within the current date.
     * @param pRuleId  the rule identifier
     * @param pExpDate  the user expiration date
     * @param pNow  the current date
     * @return true if the rule expiration date is equal to or before the current date.
     * @throws            RemoteException Required by EJB 1.0
     */
    public boolean checkPriceRuleExpDate(int pRuleId, Date pExpDate, Date pNow)
    throws RemoteException {
        return true;
    }

    public void updateDistributorItemMapping
    (int pItemId, int pDistId,
    String pDistUom, String pDistPack, String pDistSku, BigDecimal pUomConvMultiplier, String pUserName)
    throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, pItemId);
            dbc.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, pDistId);
            dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
            RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
            ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(conn,dbc);
            if(itemMappingDV.size()==0) {
                ItemMappingData itemMap = ItemMappingData.createValue();
                itemMap.setItemMappingCd
                (RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
                itemMap.setBusEntityId(pDistId);
                itemMap.setItemId(pItemId);
                itemMap.setItemPack(pDistPack);
                itemMap.setItemUom(pDistUom);
                itemMap.setItemNum(pDistSku);
                itemMap.setUomConvMultiplier(pUomConvMultiplier);
                itemMap.setStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
                itemMap.setAddBy(pUserName);
                itemMap.setModBy(pUserName);
                ItemMappingDataAccess.insert(conn, itemMap);
            } else {
                ItemMappingData itemMap = (ItemMappingData) itemMappingDV.get(0);
                itemMap.setItemPack(pDistPack);
                itemMap.setItemUom(pDistUom);
                itemMap.setItemNum(pDistSku);
                itemMap.setUomConvMultiplier(pUomConvMultiplier);
                itemMap.setStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
                itemMap.setModBy(pUserName);
                ItemMappingDataAccess.update(conn, itemMap);
            }
        }
        catch (Exception e) {
            logError("updateDistributorItemMapping: " + e);
        }
        finally {
            if ( null != conn ) {
                try { conn.close(); }
                catch (Exception e) {}
            }
        }
        return ;
    }

    public void addMasterItems(HashMap pMI, String pUser, int pStoreId, int pCatalogId) throws RemoteException {
		Connection conn = null;
		try {
		    conn = getConnection();
		    Iterator mItemIter = pMI.keySet().iterator();
		    int entriesCount = pMI.size();
            if(entriesCount==0) {
		        return;
		    }
            for (int i=0; i < entriesCount && (mItemIter.hasNext()); i++) {
                ArrayList aL = (ArrayList) (mItemIter.next());
	            Object key = (Object) aL;
	            Object value = pMI.get(key);
	            ProductData prodD = (ProductData) value;
	            ItemData masterItemData = prodD.getItemData();
	            masterItemData.setAddBy(pUser);
	            masterItemData.setModBy(pUser);

	            ItemData iData = ItemDataAccess.insert(conn, masterItemData);
	            iData.setSkuNum(iData.getItemId() + SKU_MINIMUM);
	            ItemDataAccess.update(conn, iData);
	            masterItemData.setItemId(iData.getItemId());
	        }
		    Iterator mItemIter2 = pMI.keySet().iterator();
		    for (mItemIter2 = pMI.keySet().iterator(); mItemIter2.hasNext();) {
	            ArrayList aL = (ArrayList) (mItemIter2.next());
	            Object key = (Object) aL;
	            Object value = pMI.get(key);
	            ProductData prodD = (ProductData) value;
	            ItemData masterItemData = prodD.getItemData();
		        int itemId = masterItemData.getItemId();
	        	ItemMetaDataVector imDV = prodD.getProductAttributes();
		        for (Iterator mItemMetaIter=imDV.iterator(); mItemMetaIter.hasNext();) {
		            ItemMetaData itemMetaD = (ItemMetaData) mItemMetaIter.next();
		            if (itemMetaD != null && Utility.isSet(itemMetaD.getValue())) {
		                itemMetaD.setItemId(itemId);
		                itemMetaD.setAddBy(pUser);
		                itemMetaD.setModBy(pUser);
		                if (itemMetaD.getNameValue().equalsIgnoreCase("PACK")) {
		                    if (!Utility.isSet(itemMetaD.getValue())) {
			          		    itemMetaD.setValue("-1");
			            	}
			            }
		            	if (!itemMetaD.getNameValue().equalsIgnoreCase("COST_PRICE")) {
		            		itemMetaD = ProductDAO.saveItemMetaInfo(conn, itemId, pUser, itemMetaD);
		            	}
			        }
			    }
	        }

	        for (Iterator mItemIter3 = pMI.keySet().iterator();mItemIter3.hasNext();) {
	            ArrayList aL = (ArrayList) (mItemIter3.next());
	            Object key = (Object) aL;
	            Object value = pMI.get(key);
	            ProductData prodD = (ProductData) value;
	            ItemData masterItemData = prodD.getItemData();
	            ItemMappingData manufacturerMappingD = prodD.getManuMapping();
				manufacturerMappingD.setItemId(masterItemData.getItemId());
	            manufacturerMappingD.setAddBy(pUser);
	            manufacturerMappingD.setModBy(pUser);
	            manufacturerMappingD = ItemMappingDataAccess.insert(conn, manufacturerMappingD);

	            ItemMappingDataVector dMapDV = prodD.getDistributorMappings();
	            for (Iterator dMapIter = dMapDV.iterator(); dMapIter.hasNext(); ) {
	            	ItemMappingData distrMappingData = (ItemMappingData) dMapIter.next();
	            	distrMappingData.setAddBy(pUser);
	            	distrMappingData.setModBy(pUser);
	            	distrMappingData.setItemId(masterItemData.getItemId());
	            	distrMappingData = ItemMappingDataAccess.insert(conn, distrMappingData);
	            }
	        }
	        //Add new categories
			ArrayList categories = new ArrayList();
        	for (Iterator mItemIter4 = pMI.keySet().iterator(); mItemIter4.hasNext(); ) {
	            ArrayList aL = (ArrayList) (mItemIter4.next());
	            Object key = (Object) aL;
	            Object value = pMI.get(key);
	            ProductData prodD = (ProductData) value;
				int itemId = prodD.getItemData().getItemId();

  		        int categoryId = ((CatalogCategoryData) prodD.getCatalogCategories().get(0)).getCatalogCategoryId();
		        ItemAssocData iaD = ItemAssocData.createValue();
		        iaD.setItem1Id(itemId);
		        iaD.setItem2Id(categoryId);
		        iaD.setItemAssocCd(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
		        iaD.setCatalogId(pCatalogId);
		        iaD.setAddBy(pUser);
		        iaD.setModBy(pUser);

		        ItemAssocDataAccess.insert(conn, iaD);
	        }

            int categoryId = 0;
            CatalogStructureData catalogStruc4NewProduct = null;
        	CatalogCategoryDataVector catalogCategDV = new CatalogCategoryDataVector();

        	for (Iterator mItemIter5 = pMI.keySet().iterator(); mItemIter5.hasNext(); ) {
	            ArrayList aL = (ArrayList) (mItemIter5.next());
	            Object key = (Object) aL;
	            Object value = pMI.get(key);
	            ProductData prodD = (ProductData) value;
				int itemId = prodD.getItemData().getItemId();

        	    CatalogStructureData csD = CatalogStructureData.createValue();
                csD.setCatalogId(pCatalogId);
                csD.setItemId(itemId);
                csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

                csD.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);
                csD.setAddBy(pUser);
                csD.setModBy(pUser);

                csD = CatalogStructureDataAccess.insert(conn, csD, true);
        	}
		} catch (Exception e) {
		    e.printStackTrace();
		    throw new RemoteException(e.getMessage());
		} finally {
		    closeConnection(conn);
		}
	}

    public void updateDMSIItems(HashMap pMI, String pUser, int pStoreId, int pCatalogId) throws RemoteException {
		Connection conn = null;
		try {
			conn = getConnection();
			Iterator mItemIter = pMI.keySet().iterator();
			int entriesCount = pMI.size();
            if(entriesCount==0) {
				return;
			}
	        for (int i=0; i < entriesCount && (mItemIter.hasNext()); i++) {
	            ArrayList aL = (ArrayList) (mItemIter.next());
	            Object key = (Object) aL;
	            Object value = pMI.get(key);
	            ProductData prodD = (ProductData) value;
	            ItemData masterItemData = prodD.getItemData();
	            masterItemData.setAddBy(pUser);
	            masterItemData.setModBy(pUser);

	            ItemDataAccess.update(conn, masterItemData);

	        }
			Iterator mItemIter2 = pMI.keySet().iterator();
			for (mItemIter2 = pMI.keySet().iterator(); mItemIter2.hasNext();) {
	            ArrayList aL = (ArrayList) (mItemIter2.next());
	            Object key = (Object) aL;
	            Object value = pMI.get(key);
	            ProductData prodD = (ProductData) value;
	            ItemData masterItemData = prodD.getItemData();
				int itemId = masterItemData.getItemId();
	        	ItemMetaDataVector imDV = prodD.getProductAttributes();
			    for (Iterator mItemMetaIter=imDV.iterator(); mItemMetaIter.hasNext();) {
			        ItemMetaData itemMetaD = (ItemMetaData) mItemMetaIter.next();
			        if (itemMetaD != null && Utility.isSet(itemMetaD.getValue())) {
			            itemMetaD.setItemId(itemId);
			            itemMetaD.setAddBy(pUser);
			            itemMetaD.setModBy(pUser);
			            java.util.Date current = new java.util.Date(System.currentTimeMillis());
			            itemMetaD.setAddDate(current);
			            if (itemMetaD.getNameValue().equalsIgnoreCase("PACK")) {
			            	if (!Utility.isSet(itemMetaD.getValue())) {
			            		itemMetaD.setValue("-1");
			            	}
			            }
		            	DBCriteria dbc = new DBCriteria();
		                dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID, itemId);
		                dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE, itemMetaD.getNameValue());
                        ItemMetaDataVector oldItemMetaDataV = ItemMetaDataAccess.select(conn, dbc);
                        int oldItemMetaCount = 0;
                        for (Iterator oldItemMetaIterator = oldItemMetaDataV.iterator(); oldItemMetaIterator.hasNext();) {
                        	oldItemMetaIterator.next();
                        	oldItemMetaCount++;
                        }
                        if (oldItemMetaCount==0) {
    		            	if (itemMetaD.getNameValue().equalsIgnoreCase("COLOR") || itemMetaD.getNameValue().equalsIgnoreCase("PACK")) {
                                ItemMetaDataAccess.insert(conn, itemMetaD);
    		            	}
    		            	continue;
                        }
                        ItemMetaData iMD = (ItemMetaData) (oldItemMetaDataV.get(0));
                        int itemMetaDataId = iMD.getItemMetaId();
                        itemMetaD.setItemMetaId(itemMetaDataId);

		            	if (itemMetaD.getNameValue().equalsIgnoreCase("COLOR") || itemMetaD.getNameValue().equalsIgnoreCase("PACK")) {
		            		ItemMetaDataAccess.update(conn, itemMetaD);
		            	}
			        }
			    }
	        }

	        for (Iterator mItemIter3 = pMI.keySet().iterator();mItemIter3.hasNext();) {
	            ArrayList aL = (ArrayList) (mItemIter3.next());
	            Object key = (Object) aL;
	            Object value = pMI.get(key);
	            ProductData prodD = (ProductData) value;
	            ItemData masterItemData = prodD.getItemData();
//	            ItemMappingData manufacturerMappingD = prodD.getManuMapping();
//				manufacturerMappingD.setItemId(masterItemData.getItemId());
//	            manufacturerMappingD.setAddBy(pUser);
//	            manufacturerMappingD.setModBy(pUser);
//	            manufacturerMappingD = ItemMappingDataAccess.insert(conn, manufacturerMappingD);

	            ItemMappingDataVector dMapDV = prodD.getDistributorMappings();
	            for (Iterator dMapIter = dMapDV.iterator(); dMapIter.hasNext(); ) {
	            	ItemMappingData distrMappingData = (ItemMappingData) dMapIter.next();
	            	distrMappingData.setAddBy(pUser);
	            	distrMappingData.setModBy(pUser);
	            	distrMappingData.setItemId(masterItemData.getItemId());

	            	DBCriteria dbc = new DBCriteria();
	                dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, masterItemData.getItemId());
	                dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
	                ItemMappingDataVector oldDistrMappingDataV = ItemMappingDataAccess.select(conn, dbc);
	                ItemMappingData oldDistrMappingData = (ItemMappingData) oldDistrMappingDataV.get(0);
	                oldDistrMappingData.setItemUom(distrMappingData.getItemUom());         // update only UOM
	            	ItemMappingDataAccess.update(conn, oldDistrMappingData);
	            }
	        }

            CatalogInformation catalogInfoEjb = APIAccess.getAPIAccess().getCatalogInformationAPI();
	        CatalogDataVector cV = catalogInfoEjb.getAccountAndShoppingCatalogsByStoreId(pStoreId);

	        ContractDescDataVector allContracts4Store = new ContractDescDataVector();
	        Contract contractEjb = APIAccess.getAPIAccess().getContractAPI();
            for (Iterator cVIterator = cV.iterator(); cVIterator.hasNext(); ) {
            	int catalogId = ((CatalogData) cVIterator.next()).getCatalogId();

	            ContractDescDataVector contrDescDataV = contractEjb.getContractDescsByCatalog(catalogId);
	            allContracts4Store.add(contrDescDataV.get(0));
            }
        	for (Iterator mItemIter6 = pMI.keySet().iterator(); mItemIter6.hasNext(); ) {
	            ArrayList aL = (ArrayList) (mItemIter6.next());
	            Object key = (Object) aL;
	            Object value = pMI.get(key);
	            ProductData prodD = (ProductData) value;
				int itemId = prodD.getItemData().getItemId();

				IdVector catIds = getCatalogIds(itemId);

				for (Iterator iter = catIds.iterator(); iter.hasNext();) {
				    CatalogPropertyData catalogPropertyD = catalogInfoEjb.getCatalogProperty(((Integer)iter.next()).intValue());
				    boolean updatePrice = Boolean.valueOf(catalogPropertyD.getValue()).booleanValue();

				    if (!updatePrice) {
				    	iter.remove();
				    }
				}

				for (Iterator cIter = catIds.iterator(); cIter.hasNext(); ) {
					ContractDescDataVector contrDescDataV = contractEjb.getContractDescsByCatalog(((Integer)cIter.next()).intValue());
					Iterator contractsByCatalogIter = contrDescDataV.iterator();
					int contractsByCatalogCount = 0;
					while (contractsByCatalogIter.hasNext()) {
						contractsByCatalogIter.next();
						contractsByCatalogCount++;
					}

					if (contractsByCatalogCount==0) break;
					for (Iterator c4SIter = allContracts4Store.iterator(); c4SIter.hasNext();) {
						int contractId1 = ((ContractDescData) c4SIter.next()).getContractId();
						int contractId2 = ((ContractDescData) contrDescDataV.get(0)).getContractId();
						if (contractId1==contractId2) {
							ContractItemDataVector contractItemDataV = contractEjb.getContractItemCollectionByItem(contractId1, itemId);
							ContractItemData oldContractItemData = new ContractItemData();
							for (Iterator contractItemsIterator = contractItemDataV.iterator(); contractItemsIterator.hasNext();) {
								oldContractItemData = (ContractItemData) contractItemsIterator.next();
								oldContractItemData.setDistCost(new BigDecimal(prodD.getCostPrice()));
								//oldContractItemData.setAmount(new BigDecimal(prodD.getPack()));
								oldContractItemData.setAmount(new BigDecimal(prodD.getCostPrice()));
							}

							contractEjb.updateContractItem(oldContractItemData, contractId1, itemId);
						}
					}
				}
        	}

		} catch (Exception e) {
		    e.printStackTrace();
		    throw new RemoteException(e.getMessage());
		} finally {
		    closeConnection(conn);
		}

    }

    public void removeDistributorItemMapping
    (int pItemId, int pDistId )
    throws RemoteException {

        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, pItemId);
            dbc.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, pDistId);
            dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
            RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
            ItemMappingDataAccess.remove(conn, dbc);
        }
        catch (Exception e) {
            logError("removeDistributorItemMapping: " + e);
        }
        finally {
            if ( null != conn ) {
                try { conn.close(); }
                catch (Exception e) {}
            }
        }
        return ;
    }


    /**
     *Fetches an AggregateItemViewVector populated based off the supplied catalogIds.
     *@param catalogIds filters base off the supplied catalogIds.  If empty an empty list is returned.
     *@throws RemoteException if an error occurs
     */
    public AggregateItemViewVector getAggregateItemViewList(int pItemId,IdVector pCatalogIds) throws RemoteException{
        return getAggregateItemViewList(pItemId, null,null, null, pCatalogIds, true);
    }

    /**
     *Fetches an AggregateItemViewVector populated based off the supplied pAccountIds and pDistributorIds.
     *@param pStoreIds if the pStoreIds is empty or null then it will not be used in the filtering
     *@param pAccountIds if the pAccountIds is empty or null then it will not be used in the filtering
     *@param pDistributorIds if the pDistributorIds is empty or null then it will not be used in the filtering
     *@throws RemoteException if an error occurs
     */
    public AggregateItemViewVector getAggregateItemViewList(int pItemId,
    IdVector pStoreIds, IdVector pAccountIds, IdVector pDistributorIds) throws RemoteException{
        return getAggregateItemViewList(pItemId, pStoreIds, pAccountIds, pDistributorIds, null, false);
    }

    /**
     *
     *@param pAccountIds if the pAccountIds is empty or null then filtering by accounts will not be used.
     *@param pDistributorIds if the pDistributorIds is empty or null then filtering by distributors will not be used.
     *@param catalogIds filters base off the supplied catalogIds.
     *@param boolean useCatalogIds flag indicates if the catalogIds list is used of the pAccountIds and pDistributorIds.
     */
    private AggregateItemViewVector getAggregateItemViewList(int pItemId,
    IdVector pStoreIds,IdVector pAccountIds, IdVector pDistributorIds, IdVector pCatalogIds, boolean pUseCatalogIds) throws RemoteException{
        Connection con = null;
        try{
            con = getConnection();
            //pick up the catalog ids that we are interested in, that is those that have a relationship
            //defined in the catalog assoc table to both the supplied distributors and the supplied
            //accounts
            IdVector catalogIds;
            if(pUseCatalogIds){
                catalogIds = pCatalogIds;
            }else{
                catalogIds = new IdVector();
                String rsql = null;
                //if both params are supplied then we have a special sql
                /*DBCriteria crit = new DBCriteria();
                boolean
                if(pDistributorIds != null && pDistributorIds.size() > 0){
                    crit.addJoinTableEqualTo(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE,
                                CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
                    crit.addJoinTableOneOf(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE,
                                CatalogStructureDataAccess.BUS_ENTITY_ID, pDistributorIds);
                    crit.addJoinTableEqualTo(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE,
                                CatalogStructureDataAccess.ITEM_ID, pItemId);
                }*/

                StringBuffer sqlWhere = new StringBuffer();
                HashMap sqlTables = new HashMap();
                boolean needsAnd = false;
                String aTableAlias=null;
                if(pDistributorIds != null && pDistributorIds.size() > 0){
                    if(needsAnd){
                        sqlWhere.append(" AND ");
                    }
                    needsAnd = true;
                    sqlWhere.append("d.catalog_structure_cd = 'CATALOG_PRODUCT' ");
                    sqlWhere.append("AND d.bus_entity_id in ("+IdVector.toCommaString(pDistributorIds)+") AND d.item_id = "+pItemId+" ");
                    aTableAlias = "d";
                    sqlTables.put(aTableAlias,"CLW_CATALOG_STRUCTURE");
                }
                if(pAccountIds != null && pAccountIds.size() > 0){
                    if(needsAnd){
                        sqlWhere.append(" AND ");
                    }
                    needsAnd = true;
                    sqlWhere.append("a.catalog_assoc_cd = 'CATALOG_ACCOUNT' ");
                    sqlWhere.append("AND a.bus_entity_id in ("+IdVector.toCommaString(pAccountIds)+") ");
                    aTableAlias = "a";
                    sqlTables.put(aTableAlias,"CLW_CATALOG_ASSOC");
                }
                if(pStoreIds != null && pStoreIds.size() > 0){
                    if(needsAnd){
                        sqlWhere.append(" AND ");
                    }
                    needsAnd = true;
                    sqlWhere.append("s.catalog_assoc_cd = 'CATALOG_STORE' ");
                    sqlWhere.append("AND s.bus_entity_id in ("+IdVector.toCommaString(pStoreIds)+") ");
                    aTableAlias = "s";
                    sqlTables.put(aTableAlias,"CLW_CATALOG_ASSOC");
                }

                //construct the sql
                if(sqlTables.size() > 0){
                    StringBuffer rsqlB = new StringBuffer();
                    rsqlB.append("SELECT ");
                    //doesn't matter which we use, they are all joined and they all have the catalog id in them
                    rsqlB.append(aTableAlias+".catalog_id");
                    rsqlB.append(" FROM ");
                    Iterator it = sqlTables.keySet().iterator();
                    while(it.hasNext()){
                        String alias = (String) it.next();
                        String tableName = (String) sqlTables.get(alias);
                        rsqlB.append(tableName);
                        rsqlB.append(" ");
                        rsqlB.append(alias);
                        if(it.hasNext()){
                            rsqlB.append(",");
                        }
                    }

                    rsqlB.append(" WHERE ");
                    rsqlB.append(sqlWhere);
                    if(sqlTables.size() > 1){
                        //join them
                        Iterator it1 = sqlTables.keySet().iterator();
                        Iterator it2 = sqlTables.keySet().iterator();
                        it2.next();
                        while(it1.hasNext() && it2.hasNext()){
                            String alias1 = (String) it1.next();
                            String alias2 = (String) it2.next();
                            rsqlB.append(" AND ");
                            rsqlB.append(alias1);
                            rsqlB.append(".catalog_id=");
                            rsqlB.append(alias2);
                            rsqlB.append(".catalog_id ");
                        }
                    }
                    rsql = rsqlB.toString();
                }

               /*
                if(pDistributorIds != null && pDistributorIds.size() > 0 && pAccountIds != null && pAccountIds.size() > 0){
                    rsql = "SELECT a.catalog_id FROM clw_catalog_assoc a, CLW_CATALOG_STRUCTURE d WHERE "+
                    "a.catalog_id = d.catalog_id AND d.catalog_structure_cd = 'CATALOG_PRODUCT' AND a.catalog_assoc_cd = 'CATALOG_ACCOUNT' "+
                    "AND a.bus_entity_id in ("+IdVector.toCommaString(pAccountIds)+") AND d.bus_entity_id in ("+IdVector.toCommaString(pDistributorIds)+") AND d.item_id = "+pItemId ;
                }else if(pDistributorIds != null && pDistributorIds.size() > 0){
                    rsql = "SELECT d.catalog_id FROM CLW_CATALOG_STRUCTURE d WHERE "+
                    "d.catalog_structure_cd = 'CATALOG_PRODUCT'"+
                    "AND d.bus_entity_id in ("+IdVector.toCommaString(pDistributorIds)+") AND d.item_id = "+pItemId;
                }else if(pAccountIds != null && pAccountIds.size() > 0){
                    rsql = "SELECT a.catalog_id FROM clw_catalog_assoc a WHERE "+
                    "a.catalog_assoc_cd = 'CATALOG_ACCOUNT'"+
                    "AND a.bus_entity_id in ("+IdVector.toCommaString(pAccountIds)+")";
                }*/
                if(rsql != null){
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(rsql.toString());;
                    while(rs.next()){
                        catalogIds.add(new Integer(rs.getInt("catalog_id")));
                    }
                    rs.close();
                    stmt.close();
                }
            }

            //if there is no relationship between the item, the account and the distributor...or there were no catlogs specified in
            //the serach then just return an empty vector.
            if(catalogIds == null || catalogIds.isEmpty()){
                return new AggregateItemViewVector();
            }

            StringBuffer sql =
            new StringBuffer("SELECT ");
            sql.append("cat.catalog_id, cat.short_desc AS catDesc, cat.catalog_status_cd, cat.catalog_type_cd, con.contract_id,  ");
            sql.append("con.short_desc AS conDesc, con.contract_status_cd, cats.catalog_structure_id, cats.bus_entity_id AS dist_id,  ");
            sql.append("cats.item_id, cats.customer_sku_num, coni.contract_item_id, coni.amount, coni.dist_cost, coni.dist_base_cost ");
            sql.append("FROM ");
            sql.append("CLW_CATALOG cat, ");
            sql.append("(SELECT * FROM CLW_CONTRACT WHERE contract_status_cd IN ('ACTIVE', 'LIMITED','ROUTING')) con, ");
            sql.append("(SELECT * FROM CLW_CATALOG_STRUCTURE WHERE item_id = "+pItemId+") cats, ");
            sql.append("(SELECT * FROM CLW_CONTRACT_ITEM WHERE item_id = "+pItemId+") coni ");
            sql.append("WHERE ");
            sql.append("cat.catalog_id = con.catalog_id (+) AND ");
            sql.append("cat.catalog_id = cats.catalog_id  (+) AND ");
            sql.append("con.contract_id = coni.contract_id (+) AND ");
            sql.append("cat.catalog_status_cd IN ('ACTIVE', 'LIMITED') AND ");
            sql.append("cat.catalog_type_cd IN ('SHOPPING','ACCOUNT') ");
            if(catalogIds != null && catalogIds.size() > 0){
                sql.append("AND cat.catalog_id IN ("+IdVector.toCommaString(catalogIds)+") ");
            }

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql.toString());
            AggregateItemViewVector items = new AggregateItemViewVector();
            HashMap cache = new HashMap();
            HashMap lMap = new HashMap();  //a map of catalog ids to a list of AggregateItemView objects
            while(rs.next()){
                AggregateItemView aiv = createAggregateItemView(con,rs,cache);
                aiv.setItemId(pItemId);
                items.add(aiv);
                ArrayList l = (ArrayList)lMap.get(new Integer(aiv.getCatalogId()));
                if(l==null){
                    l = new ArrayList();
                    lMap.put(new Integer(aiv.getCatalogId()),l);
                }
                l.add(aiv);
            }
            rs.close();
            stmt.close();

            {
                if(!lMap.keySet().isEmpty()){
                    //get the categories and match them up to the items
                    String catSql = "SELECT i.short_desc, cia.catalog_id FROM CLW_ITEM_ASSOC cia, CLW_ITEM i WHERE "+
                    "cia.item1_id = "+pItemId+" AND cia.catalog_id in ("+Utility.toCommaSting(lMap.keySet())+") AND cia.item_assoc_cd = 'PRODUCT_PARENT_CATEGORY' "+
                    "AND cia.item2_id = i.item_id AND i.item_type_cd = 'CATEGORY' AND i.item_status_cd = 'ACTIVE'";
                    Statement catStmt = con.createStatement();
                    rs = catStmt.executeQuery(catSql);
                    while(rs.next()){
                        String categoryName = rs.getString(1);
                        int catalogId = rs.getInt(2);
                        ArrayList i = (ArrayList) lMap.get(new Integer(catalogId));
                        if(i != null){
                            Iterator it = i.iterator();
                            while(it.hasNext()){
                                AggregateItemView aiv = (AggregateItemView) it.next();
                                aiv.getCategories().add(categoryName);
                            }
                        }
                    }
                    rs.close();
                    catStmt.close();
                }
            }

            {
                if(!lMap.keySet().isEmpty()){
                    //get the order guide info and match that up to the items
                    String ogSql =
                    "SELECT og.short_desc, og.order_guide_id, ogs.order_guide_structure_id, og.catalog_id FROM "+
                    "clw_order_guide og, (SELECT * FROM clw_order_guide_structure WHERE item_id = "+pItemId+") ogs WHERE "+
                    "og.order_guide_id   = ogs.order_guide_id (+)  AND " +
                    "og.order_guide_type_cd = 'ORDER_GUIDE_TEMPLATE' AND catalog_id IN ("+Utility.toCommaSting(lMap.keySet())+")";
                    Statement ogStmt = con.createStatement();
                    rs = ogStmt.executeQuery(ogSql);
                    while(rs.next()){
                        String orderGuideName = rs.getString(1);
                        int orderGuideId = rs.getInt(2);
                        int orderGuideStructureId = rs.getInt(3);
                        int catalogId = rs.getInt(4);
                        ArrayList i = (ArrayList) lMap.get(new Integer(catalogId));
                        ArrayList newItems = new ArrayList();
                        if(i != null){
                            Iterator it = i.iterator();
                            while(it.hasNext()){
                                AggregateItemView aiv = (AggregateItemView) it.next();
                                if(aiv.getOrderGuideId() == 0){
                                    aiv.setOrderGuideDesc(orderGuideName);
                                    aiv.setOrderGuideId(orderGuideId);
                                    aiv.setOrderGuideStructureId(orderGuideStructureId);
                                }else{
                                    AggregateItemView newAiv = aiv.copy();
                                    newAiv.setOrderGuideDesc(orderGuideName);
                                    newAiv.setOrderGuideId(orderGuideId);
                                    newAiv.setOrderGuideStructureId(orderGuideStructureId);
                                    newItems.add(newAiv);
                                }
                            }
                        }
                        //i.addAll(newItems);
                        items.addAll(newItems);
                    }
                    rs.close();
                    ogStmt.close();
                }
            }
            return items;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }



  /*
   *Creates an aggregateItems from the current position in the result set, something else is expected
   *to be calling the "next" method of the passed in result set.
   */
    private AggregateItemView createAggregateItemView(Connection con, ResultSet rs,HashMap cache) throws SQLException{
        AggregateItemView itm = AggregateItemView.createValue();
        itm.setCatalogDesc(rs.getString("catDesc"));
        itm.setCatalogId(rs.getInt("catalog_id"));
        itm.setCatalogStatus(rs.getString("catalog_status_cd"));
        itm.setCatalogStructureId(rs.getInt("catalog_structure_id"));
        itm.setCatalogType(rs.getString("catalog_type_cd"));
        itm.setContractDesc(rs.getString("conDesc"));
        itm.setContractId(rs.getInt("contract_id"));
        itm.setContractItemId(rs.getInt("contract_item_id"));
        itm.setContractStatus(rs.getString("contract_status_cd"));
        itm.setCustSku(rs.getString("customer_sku_num"));
        itm.setDistDesc(null);
        itm.setDistId(rs.getInt("dist_id"));
        itm.setItemCost(rs.getBigDecimal("dist_cost"));
        itm.setItemPrice(rs.getBigDecimal("amount"));
        itm.setCategories(new ArrayList());
        itm.setDistBaseCost(rs.getBigDecimal("dist_base_cost"));
        HashMap distCache;
        if(cache.containsKey("distributor")){
            distCache = (HashMap) cache.get("distributor");
        }else{
            distCache = new HashMap();
            cache.put("distributor", distCache);
        }
        Integer distKey = new Integer(itm.getDistId());
        BusEntityData dist = null;
        if(distCache.containsKey(distKey)){
            dist = (BusEntityData) distCache.get(distKey);
        }else{
            try{
                dist = BusEntityDataAccess.select(con,itm.getDistId());
            }catch(DataNotFoundException e){}
            distCache.put(distKey,dist);
        }
        if(dist != null){
            itm.setDistDesc(dist.getShortDesc());
        }
        //resets the state as we want to know if things have changed from this point on
        itm.reset();
        return itm;
    }


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
    throws RemoteException{
        Connection con = null;
        try{
            con = getConnection();
            StringBuffer sql = new StringBuffer("update clw_catalog_structure set ");
            sql.append("customer_sku_num = ?, bus_entity_id =?, mod_by='"+pUserName+"', ");
            sql.append("mod_date = ? ");
            sql.append("where catalog_structure_id = ?");
            PreparedStatement catStructUpdateStmt = con.prepareStatement(sql.toString());
            sql = new StringBuffer("update clw_contract_item set ");
            sql.append("amount = ?,dist_cost = ?,dist_base_cost=?, mod_by='"+pUserName+"', ");
            sql.append("mod_date = ? ");
            sql.append("where contract_item_id = ?");
            PreparedStatement conItemUpdateStmt = con.prepareStatement(sql.toString());

            sql = new StringBuffer("SELECT COUNT(*) FROM clw_catalog_assoc WHERE ");
            sql.append("catalog_assoc_cd = 'CATALOG_DISTRIBUTOR' AND catalog_id = ? AND bus_entity_id = ?");
            PreparedStatement distCatRelatedStmt = con.prepareStatement(sql.toString());

            Date nowUtil = new Date();
            java.sql.Date now = new java.sql.Date(nowUtil.getTime());
            Date today = Utility.truncateDateByDay(nowUtil);
            Iterator it = pAggregateItemViewVector.iterator();
            HashMap distCatRelatedCache = new HashMap();

            //maintain a list of what we have done as it is possible to have multiple
            //catalogs/contracts for an item.  Particularly if there is a catalog with more than
            //one contract you will see it x times for each contract.  There could also be an error
            //that we do not want to compound with the given input.
            HashSet catStructDone = new HashSet();
            HashSet conItmDone = new HashSet();
            HashSet orderGuideDone = new HashSet();
            while(it.hasNext()){
                AggregateItemView ait = (AggregateItemView) it.next();
                Integer key = new Integer(ait.getCatalogId());
                if(pItemsToCatalog && catStructDone.add(key)){
                    if(ait.getCatalogStructureId() == 0 || ait.isCustSkuChanged() || ait.isDistIdChanged()){
                        //validate that this catalog has a relationship to this distributor
                        String distCatRelatedCacheKey = ait.getCatalogId()+":"+ait.getDistId();
                        int count = 0;
                        if(!distCatRelatedCache.containsKey(distCatRelatedCacheKey)){
                            distCatRelatedStmt.setInt(1, ait.getCatalogId());
                            distCatRelatedStmt.setInt(2, ait.getDistId());
                            ResultSet rs = distCatRelatedStmt.executeQuery();
                            if(rs.next()){
                                count = rs.getInt(1);
                            }
                            rs.close();
                            if(count == 0){
                                CatalogAssocData cad = CatalogAssocData.createValue();
                                cad.setAddBy(pUserName);
                                cad.setModBy(pUserName);
                                cad.setBusEntityId(ait.getDistId());
                                cad.setCatalogAssocCd(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_DISTRIBUTOR);
                                cad.setCatalogId(ait.getCatalogId());
                                CatalogAssocDataAccess.insert(con, cad);
                                distCatRelatedCache.put(distCatRelatedCacheKey,new Integer(count));
                                count = 1;
                            }
                            distCatRelatedCache.put(distCatRelatedCacheKey,new Integer(count));
                        }else{
                            Integer countObj = (Integer) distCatRelatedCache.get(distCatRelatedCacheKey);
                            if(countObj != null){
                                count = countObj.intValue();
                            }
                        }



                            if(ait.getCatalogStructureId() == 0){
                                //add this item to this catalog
                                CatalogStructureData csd = CatalogStructureData.createValue();
                                csd.setAddBy(pUserName);
                                csd.setModBy(pUserName);
                                csd.setBusEntityId(ait.getDistId());
                                csd.setCatalogId(ait.getCatalogId());
                                csd.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
                                csd.setCustomerSkuNum(ait.getCustSku());
                                csd.setEffDate(today);
                                csd.setItemId(pItemId);
                                csd.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);

                                CatalogStructureDataAccess.insert(con,csd);
                            }else if(ait.isCustSkuChanged() || ait.isDistIdChanged()){
                                //update it as some of the pertinent info has changed
                                catStructUpdateStmt.setString(1, ait.getCustSku());
                                catStructUpdateStmt.setInt(2, ait.getDistId());
                                catStructUpdateStmt.setDate(3, now);
                                catStructUpdateStmt.setInt(4, ait.getCatalogStructureId());

                                int updateCt = catStructUpdateStmt.executeUpdate();
                                if(updateCt!=1){
                                    throw new RemoteException("Expected exactly one row to be updated, not: "+updateCt);
                                }
                            }
                        //}else{ //no relationship between this catalog and this distributor
                        //    throw new RemoteException("Catalog "+ait.getCatalogDesc()+" is not related to distributor id: "+ait.getDistId());
                        //}
                    }

                }

                //add item to contract if the contract is present and we are doing this update,
                //and we have not already added the item to the contract
                key = new Integer(ait.getContractId());

                //check for locale currency decimal places
                ContractData conD = ContractDataAccess.select(con, key.intValue());
                String locale = conD.getLocaleCd();
                DBCriteria crit = new DBCriteria();
                crit.addEqualTo(CurrencyDataAccess.LOCALE, locale);
                CurrencyDataVector currDV = CurrencyDataAccess.select(con, crit);
                int decimalPlaces = 0;
                if(currDV!=null){
                	CurrencyData currD = (CurrencyData)currDV.get(0);
                	decimalPlaces = currD.getDecimals();
                }

                List<String> errorMessLL = new ArrayList<String>();
                BigDecimal itemPriceBD = ait.getItemPrice();
                BigDecimal itemCostBD = ait.getItemCost();
                BigDecimal distBaseCostBD = ait.getDistBaseCost();

                if(itemPriceBD.scale()>decimalPlaces){
                	String errorMess = "The price for item id "+ait.getItemId()+" has too many decimal points.  " +
                	"It can only have "+decimalPlaces+" decimal points for this currency";
                	if (!errorMessLL.contains(errorMess)) {
                		errorMessLL.add(errorMess);
                	}
                }
                if(itemCostBD.scale()>decimalPlaces){
                	String errorMess = "The cost for item id "+ait.getItemId()+" has too many decimal points.  " +
                	"It can only have "+decimalPlaces+" decimal points for this currency";
                	if (!errorMessLL.contains(errorMess)) {
                		errorMessLL.add(errorMess);
                	}
                }
                if(distBaseCostBD.scale()>decimalPlaces){
                	String errorMess = "The dist base cost for item id "+ait.getItemId()+" has too many decimal points.  " +
                	"It can only have "+decimalPlaces+" decimal points for this currency";
                	if (!errorMessLL.contains(errorMess)) {
                		errorMessLL.add(errorMess);
                	}
                }

                if(errorMessLL.size()>0){
                	throw new RemoteException(makeErrorString(errorMessLL));
                }

                if(ait.getContractId() > 0 && pItemsToContract && conItmDone.add(key)){
                    if(ait.getContractItemId() == 0){
                        //add this item to the contract
                        ContractItemData cid = ContractItemData.createValue();
                        cid.setAddBy(pUserName);
                        cid.setModBy(pUserName);
                        cid.setAmount(ait.getItemPrice());
                        cid.setContractId(ait.getContractId());
                        //hardcoded in the contract bean, so hardcoded here
                        //cid.setCurrencyCd(RefCodeNames.CURRENCY_CD.USD);
                        cid.setDiscountAmount(null);
                        cid.setDistCost(ait.getItemCost());
                        cid.setDistBaseCost(ait.getDistBaseCost());
                        cid.setEffDate(null);
                        cid.setExpDate(null);
                        cid.setItemId(pItemId);
                        ContractItemDataAccess.insert(con,cid);
                    }else if(ait.isItemCostChanged() || ait.isItemPriceChanged() || ait.isDistBaseCostChanged()){
                        //update it as some of the pertinent info has changed
                        conItemUpdateStmt.setBigDecimal(1, ait.getItemPrice());
                        conItemUpdateStmt.setBigDecimal(2, ait.getItemCost());
                        conItemUpdateStmt.setBigDecimal(3, ait.getDistBaseCost());
                        conItemUpdateStmt.setDate(4, now);
                        conItemUpdateStmt.setInt(5, ait.getContractItemId());
                        int updateCt = conItemUpdateStmt.executeUpdate();
                        if(updateCt!=1){
                            throw new RemoteException("Expected exactly one row to be updated, not: "+updateCt);
                        }
                    }
                }

                key = new Integer(ait.getOrderGuideId());
                //only add it if the order guide actually exists.  We will not be creating the
                //order guide if it is not already present, and we do not assume it exists like
                //the contact
                if(pItemsToOrderGuide && ait.getOrderGuideId() > 0 && orderGuideDone.add(key)){
                    //all we are doing is adding the relationship, no updates are requiered
                    if(ait.getOrderGuideStructureId() == 0){
                        //add this item to the order guide
                        OrderGuideStructureData ogsd = OrderGuideStructureData.createValue();
                        ogsd.setAddBy(pUserName);
                        ogsd.setModBy(pUserName);
                        ogsd.setCategoryItemId(0);
                        ogsd.setItemId(pItemId);
                        ogsd.setOrderGuideId(ait.getOrderGuideId());
                        ogsd.setQuantity(0);
                        OrderGuideStructureDataAccess.insert(con, ogsd);
                    }
                }

            }

            catStructUpdateStmt.close();
            conItemUpdateStmt.close();
            distCatRelatedStmt.close();
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }

    private String makeErrorString(List pErrors) {
  	  String errorMess = "^clw^";
  	  for (int ii = 0; ii < pErrors.size(); ii++) {
  		  if (ii != 0) errorMess += "; ";
  		  errorMess += pErrors.get(ii);
  	  }
  	  errorMess += "^clw^";
  	  return errorMess;
    }

    /**
     *removes the supplied aggregate items from the catalogs and contracts that they are in.  No
     *action is preformed if they are not in a catalog or contract.
     *@param the @see AggregateItemViewVector to remove
     *@param the users name doing the update
     *@param boolean indicates if we are preforming Catalog mods
     *@param boolean indicates if we are preforming Contract mods
     *@param boolean indicates if we are preforming Order Guide mods
     *@throws RemoteException if an error occurs
     */
    public void removeAggregateItemViewList(
    AggregateItemViewVector pAggregateItemViewVector,
    boolean pItemsToCatalog,boolean pItemsToContract,boolean pItemsToOrderGuide)
    throws RemoteException{
        Connection con = null;
        try{
            con = getConnection();
            Iterator it = pAggregateItemViewVector.iterator();
            while(it.hasNext()){
                AggregateItemView ait = (AggregateItemView) it.next();
                int numRemoved;
                if(pItemsToCatalog && ait.getCatalogStructureId() != 0){
                    numRemoved = CatalogStructureDataAccess.remove(con,ait.getCatalogStructureId());
                    if(numRemoved > 1){
                        throw new RemoteException("Expected to remove 1 or 0 Catalog Structure Record not ("+numRemoved+") for catalog structure id: "+ait.getCatalogStructureId());
                    }
                    //if we are removing it out of the catalog, then remove this catalog category association
                    //for this catalog as well
                    DBCriteria remCrit = new DBCriteria();
                    remCrit.addEqualTo(ItemAssocDataAccess.CATALOG_ID, ait.getCatalogId());
                    remCrit.addEqualTo(ItemAssocDataAccess.ITEM1_ID, ait.getItemId());
                    ItemAssocDataAccess.remove(con,remCrit);
                }
                if(pItemsToContract && ait.getContractItemId() != 0){
                    numRemoved = ContractItemDataAccess.remove(con,ait.getContractItemId());
                    if(numRemoved > 1){
                        throw new RemoteException("Expected to remove 1 or 0 Contract Item Record not ("+numRemoved+") for contract item id: "+ait.getContractItemId());
                    }
                }
                if(pItemsToOrderGuide && ait.getOrderGuideStructureId() != 0){
                    numRemoved = OrderGuideStructureDataAccess.remove(con,ait.getOrderGuideStructureId());
                    if(numRemoved > 1){
                        throw new RemoteException("Expected to remove 1 or 0 Order Guide Structure Record not ("+numRemoved+") for order guide structure id: "+ait.getOrderGuideStructureId());
                    }
                }
            }
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }

    /**
     *Gets list of uploaded tables
     *@param pStoreId the store id
     *@param pFileTempl search pattern
     *@param boolean ppProcessedFl filters out processed tables if false
     *@throws RemoteException if an error occurs
     */
    public UploadDataVector getXlsTables(int pStoreId, String pFileTempl, boolean pProcessedFl)
    throws RemoteException
    {
        Connection con = null;
        DBCriteria dbc;
        try{
          con = getConnection();
          dbc = new DBCriteria();
          dbc.addEqualTo(UploadDataAccess.STORE_ID,pStoreId);
          if(Utility.isSet(pFileTempl)) {
            dbc.addContainsIgnoreCase(UploadDataAccess.FILE_NAME, pFileTempl);
          }
          if(!pProcessedFl) {
            dbc.addNotEqualTo(UploadDataAccess.UPLOAD_STATUS_CD,
                    RefCodeNames.UPLOAD_STATUS_CD.PROCESSED);
          }
          UploadDataVector udV = UploadDataAccess.select(con,dbc);
          return udV;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }

    /**
     *Gets uploaded table (with data)
     *@param pStoreId the store id
     *@param pUploadId the table id
     *@return XlsTableView object (table header +  data)
     *@throws RemoteException if an error occurs
     */
    /*
    public XlsTableView getXlsTableById(int pStoreId, int pUploadId)
    throws RemoteException
    {
        Connection con = null;
        DBCriteria dbc;
        try{
          con = getConnection();
          dbc = new DBCriteria();
          dbc.addEqualTo(UploadDataAccess.STORE_ID,pStoreId);
          dbc.addEqualTo(UploadDataAccess.UPLOAD_ID, pUploadId);
          UploadDataVector udV = UploadDataAccess.select(con,dbc);
          if(udV.size()==0) {
            String errorMess = "^clw^No uploaded tahle fould. Id: "+pUploadId+"^clw^";
            throw new Exception(errorMess);
          }
          UploadData uD = (UploadData) udV.get(0);

          dbc = new DBCriteria();
          dbc.addEqualTo(UploadValueDataAccess.UPLOAD_ID, pUploadId);
          dbc.addOrderBy(UploadValueDataAccess.ROW_NUM);
          dbc.addOrderBy(UploadValueDataAccess.COLUMN_NUM);
          UploadValueDataVector uvDV = UploadValueDataAccess.select(con,dbc);
          if(uvDV.size()==0) {
            String errorMess = "^clw^Table has no elements. Id: "+pUploadId+"^clw^";
            throw new Exception(errorMess);
          }
          int maxColNum = 0;
          int maxRowNum = 0;
          for(Iterator iter=uvDV.iterator(); iter.hasNext();) {
            UploadValueData uvD = (UploadValueData) iter.next();
            int colNum = uvD.getColumnNum();
            if(colNum<0) {
              String errorMess =
                      "^clw^Table format error. Column number value < 0. Id: "
                        +pUploadId+"^clw^";
              throw new Exception(errorMess);
            }
            if(colNum>maxColNum) maxColNum = colNum;
            maxRowNum = uvD.getRowNum();
            if(maxRowNum<0) {
              String errorMess =
                      "^clw^Table format error. Row number value < 0. Id: "
                        +pUploadId+"^clw^";
              throw new Exception(errorMess);
            }
          }
          UploadValueData[][] elements =
                  new UploadValueData[maxRowNum+1][maxColNum+1];
          int ii=0;
          int jj=0;
          for(Iterator iter=uvDV.iterator(); iter.hasNext();) {
            UploadValueData uvD = (UploadValueData) iter.next();
            int rowNum = uvD.getRowNum();
            int colNum = uvD.getColumnNum();
            elements[rowNum][colNum] = uvD;
          }

          XlsTableView table = XlsTableView.createValue();
          table.setHeader(uD);
          table.setContent(elements);
          return table;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }
   */

    /**
     *Gets uploaded skus (with data)
     *@param pStoreId the store id
     *@param pUploadId the table id
     *@param pUploadSkuIds list of ids (whole table if null)
     *@return XlsTableView object (table header +  data)
     *@throws RemoteException if an error occurs
     */
    public XlsTableView getSkuXlsTableById(int pStoreId, int pUploadId, IdVector pUploadSkuIds)
    throws RemoteException
    {
        Connection con = null;
        DBCriteria dbc;
        try{
          con = getConnection();
          dbc = new DBCriteria();
          dbc.addEqualTo(UploadDataAccess.STORE_ID,pStoreId);
          dbc.addEqualTo(UploadDataAccess.UPLOAD_ID, pUploadId);
          UploadDataVector udV = UploadDataAccess.select(con,dbc);
          if(udV.size()==0) {
            String errorMess = "^clw^No uploaded tahle fould. Id: "+pUploadId+"^clw^";
            throw new Exception(errorMess);
          }
          UploadData uD = (UploadData) udV.get(0);

          dbc = new DBCriteria();
          dbc.addEqualTo(UploadSkuDataAccess.UPLOAD_ID, pUploadId);
          if(pUploadSkuIds!=null) {
            dbc.addOneOf(UploadSkuDataAccess.UPLOAD_SKU_ID, pUploadSkuIds);
          }
          dbc.addOrderBy(UploadSkuDataAccess.ROW_NUM);
          UploadSkuDataVector usDV = UploadSkuDataAccess.select(con,dbc);
          if(usDV.size()==0) {
            String errorMess = "^clw^Table has no elements. Id: "+pUploadId+"^clw^";
            throw new Exception(errorMess);
          }
          UploadSkuView[] uploadSkusVw = new UploadSkuView[usDV.size()];
          int ind = 0;
          IdVector itemIdV = new IdVector();
          for(Iterator iter=usDV.iterator(); iter.hasNext();) {
            UploadSkuView usVw = UploadSkuView.createValue();
            UploadSkuData usD = (UploadSkuData) iter.next();
            usVw.setUploadSku(usD);
            uploadSkusVw[ind++] = usVw;
            if(usD.getItemId()>0) {
              itemIdV.add(new Integer(usD.getItemId()));
            }
          }
          if(itemIdV.size()>0) {
            APIAccess apiAccess = APIAccess.getAPIAccess();
            CatalogInformation catInfEjb = apiAccess.getCatalogInformationAPI();
            int storeCatalogId = catInfEjb.getStoreCatalogId(pStoreId);

            //Store catalog sku
            dbc = new DBCriteria();
            dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID,itemIdV);
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                    RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,storeCatalogId);
            CatalogStructureDataVector catStructDV = CatalogStructureDataAccess.select(con,dbc);

            for(int jj=0; jj<uploadSkusVw.length; jj++) {
              UploadSkuView usVw = uploadSkusVw[jj];
              UploadSkuData usD = usVw.getUploadSku();
              int itemId = usD.getItemId();
              if(itemId<=0) continue;
              for(Iterator iter1 = catStructDV.iterator(); iter1.hasNext();) {
                CatalogStructureData csD = (CatalogStructureData) iter1.next();
                if(itemId==csD.getItemId()) {
                  usVw.setSkuNum(csD.getCustomerSkuNum());
                  break;
                }
              }
            }
          }
          XlsTableView table = XlsTableView.createValue();
          table.setHeader(uD);
          table.setContent(uploadSkusVw);
          return table;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }

    /**
     *Saves xls table
     *@param pStoreId the store id
     *@param pXlsTable table to save
     *@param boolean pOwerwriteFl forces to overwrite previously uploaded file
     *@param String pUser uder login name
     *@throws RemoteException if an error occurs
     */
    /*
    public XlsTableView saveXlsSpreadsheet(int pStoreId, XlsTableView pXlsTable,
            boolean pOverwriteFl, String pUser)
    throws RemoteException
    {
        Connection con = null;
        DBCriteria dbc;
        try{
          con = getConnection();
          UploadData header = pXlsTable.getHeader();
          UploadValueData[][] valuesAA = pXlsTable.getContent();
          int uploadId = header.getUploadId();
          if(uploadId==0) {
            dbc = new DBCriteria();
            dbc.addEqualTo(UploadDataAccess.FILE_NAME, header.getFileName());
            dbc.addEqualTo(UploadDataAccess.STORE_ID, pStoreId);
            IdVector udIdV =
                    UploadDataAccess.selectIdOnly(con,UploadDataAccess.UPLOAD_ID,dbc);
            if(udIdV.size()>0) {
              if(!pOverwriteFl) {
                String errorMess = "^clw^Table Exists^clw^" ;
                throw new Exception(errorMess);
              } else {
                dbc = new DBCriteria();
                dbc.addOneOf(UploadValueDataAccess.UPLOAD_ID, udIdV);
                UploadValueDataAccess.remove(con, dbc);

                dbc = new DBCriteria();
                dbc.addOneOf(UploadDataAccess.UPLOAD_ID, udIdV);
                UploadDataAccess.remove(con,dbc);
              }
            }
            header.setStoreId(pStoreId);
            header.setAddBy(pUser);
            header.setModBy(pUser);
            header = UploadDataAccess.insert(con,header);
            uploadId = header.getUploadId();
            for(int ii=0; ii<valuesAA.length; ii++) {
              UploadValueData[] uvDA = valuesAA[ii];
              if(uvDA==null) {
                continue;
              }
              for(int jj=0; jj<uvDA.length; jj++) {
                UploadValueData uvD = uvDA[jj];
                if(uvD==null) {
                  continue;
                }
                uvD.setUploadId(uploadId);
                uvD.setAddBy(pUser);
                uvD.setModBy(pUser);
                uvD = UploadValueDataAccess.insert(con,uvD);
                uvDA[jj] = uvD;
              }
            }
          } else { //Updte
            header.setModBy(pUser);
            UploadDataAccess.update(con,header);

            dbc = new DBCriteria();
            dbc.addEqualTo(UploadValueDataAccess.UPLOAD_ID, header.getUploadId());
            dbc.addOrderBy(UploadValueDataAccess.ROW_NUM);
            dbc.addOrderBy(UploadValueDataAccess.COLUMN_NUM);
            UploadValueDataVector uvDV = UploadValueDataAccess.select(con,dbc);

            Iterator iter=uvDV.iterator();
            UploadValueData wrkUvD = null;
            for(int ii=0; ii<valuesAA.length; ii++) {
              UploadValueData[] uvDA = valuesAA[ii];
              if(uvDA==null) {
                continue;
              }
              for(int jj=0; jj<uvDA.length; jj++) {
                UploadValueData uvD = uvDA[jj];
                if(uvD==null) {
                  continue;
                }
                boolean addFl = true;
                int rowNum = uvD.getRowNum();
                int colNum = uvD.getColumnNum();
                while(wrkUvD!=null || iter.hasNext()) {
                  if(wrkUvD==null) wrkUvD = (UploadValueData) iter.next();
                  if(wrkUvD.getRowNum()< rowNum) {
                    UploadValueDataAccess.remove(con,wrkUvD.getUploadValueId());
                    wrkUvD = null;
                    continue;
                  }
                  if(wrkUvD.getRowNum()> rowNum) {
                    break;
                  }
                  if(wrkUvD.getColumnNum()< colNum) {
                    UploadValueDataAccess.remove(con,wrkUvD.getUploadValueId());
                    wrkUvD = null;
                    continue;
                  }
                  if(wrkUvD.getColumnNum()> colNum) {
                    break;
                  }
                  //Update
                  wrkUvD.setModBy(pUser);
                  wrkUvD.setUploadValue(uvD.getUploadValue());
                  UploadValueDataAccess.update(con,wrkUvD);
                  addFl = false;
                  wrkUvD = null;
                  break;
                }
                if(addFl) {
                  uvD.setUploadId(uploadId);
                  uvD.setAddBy(pUser);
                  uvD.setModBy(pUser);
                  uvD = UploadValueDataAccess.insert(con,uvD);
                  uvDA[jj] = uvD;
                }
              }
            }


          }

          return pXlsTable;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }
    */

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
    throws RemoteException
    {
        Connection con = null;
        DBCriteria dbc;
        try{
          con = getConnection();
          UploadData header = pXlsTable.getHeader();
          UploadSkuView[] uploadSkusVw = pXlsTable.getContent();
          int uploadId = header.getUploadId();
          if(uploadId==0) {
            dbc = new DBCriteria();
            dbc.addEqualTo(UploadDataAccess.FILE_NAME, header.getFileName());
            dbc.addEqualTo(UploadDataAccess.STORE_ID, pStoreId);
            IdVector udIdV =
                    UploadDataAccess.selectIdOnly(con,UploadDataAccess.UPLOAD_ID,dbc);
            if(udIdV.size()>0) {
              if(!pOverwriteFl) {
                String errorMess = "^clw^Table Exists^clw^" ;
                throw new Exception(errorMess);
              } else {
                dbc = new DBCriteria();
                dbc.addOneOf(UploadSkuDataAccess.UPLOAD_ID, udIdV);
                UploadSkuDataAccess.remove(con, dbc);

                dbc = new DBCriteria();
                dbc.addOneOf(UploadDataAccess.UPLOAD_ID, udIdV);
                UploadDataAccess.remove(con,dbc);
              }
            }
            header.setStoreId(pStoreId);
            header.setAddBy(pUser);
            header.setModBy(pUser);
            header = UploadDataAccess.insert(con,header);
            uploadId = header.getUploadId();

            for(int ii=0; ii<uploadSkusVw.length; ii++) {
              UploadSkuData usD = uploadSkusVw[ii].getUploadSku();
              if(usD==null) {
                continue;
              }
              usD.setUploadId(uploadId);
              usD.setAddBy(pUser);
              usD.setModBy(pUser);
              try {
              usD = UploadSkuDataAccess.insert(con,usD);
              } catch(Exception exc) {
                checkLargeValue(usD, exc);
                throw exc;
              }
            }
          } else { //Update
            header.setModBy(pUser);
            UploadDataAccess.update(con,header);

            dbc = new DBCriteria();
            dbc.addEqualTo(UploadSkuDataAccess.UPLOAD_ID, header.getUploadId());
            dbc.addOrderBy(UploadSkuDataAccess.ROW_NUM);
            UploadSkuDataVector usDV = UploadSkuDataAccess.select(con,dbc);

            Iterator iter=usDV.iterator();
            UploadSkuData wrkUsD = null;
            for(int ii=0; ii<uploadSkusVw.length; ii++) {
              UploadSkuData usD = uploadSkusVw[ii].getUploadSku();
              if(usD==null) {
                continue;
              }
              int rowNum = usD.getRowNum();
              boolean addFl = true;
              while(wrkUsD!=null || iter.hasNext()) {
                if(wrkUsD==null) wrkUsD = (UploadSkuData) iter.next();
                if(wrkUsD.getRowNum()< rowNum) {
                  //UploadSkuDataAccess.remove(con,wrkUsD.getUploadSkuId());
                  wrkUsD = null;
                  continue;
                }
                if(wrkUsD.getRowNum()> rowNum) {
                  break;
                }
                //Update
                usD.setUploadId(uploadId);
                usD.setUploadSkuId(wrkUsD.getUploadSkuId());
                usD.setItemId(wrkUsD.getItemId());
                usD.setModBy(pUser);
                try {
                    UploadSkuDataAccess.update(con,usD);
                } catch (Exception exc) {
                    checkLargeValue(usD, exc);
                    throw exc;
                }
                addFl = false;
                wrkUsD = null;
                break;
              }
              if(addFl) {
                usD.setUploadId(uploadId);
                usD.setAddBy(pUser);
                usD.setModBy(pUser);
                usD = UploadSkuDataAccess.insert(con,usD);
              }
            }
          }
          return uploadId;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }

    private void checkLargeValue(UploadSkuData usD, Exception exc) throws Exception {
        String mess = exc.getMessage();
        int ind = -1;
        //[ERROR,Default] java.sql.SQLException: ORA-12899: value too large for column "STJOHN"."CLW_UPLOAD_SKU"."SKU_SIZE" (actual: 44, maximum: 30)
        if(mess!=null){
          String errorTempl = "value too large for column";
          ind = mess.indexOf(errorTempl);
          if(ind >0) {
            errorTempl = "\".\"CLW_UPLOAD_SKU\".";
            ind = mess.indexOf(errorTempl);
            String newMess = "^clw^Row "+usD.getRowNum()+
            " has too large value "+mess.substring(ind+errorTempl.length())+"^clw^";
            throw new Exception (newMess);
          }
        }
    }

    /**
     *Matches uploaded skus to store catalog usind manufacturer and distributor skus
     *@param pStoreId the store id
     *@param pUploadId uploaded table id
     *@param pUploadSkuIds tables skus to match (whole table if null)
     *@throws RemoteException if an error occurs
     */

    public UploadSkuViewVector matchSkuXlsTable(int pStoreId, int pUploadId, IdVector pUploadSkuIds)
    throws RemoteException
    {
        Connection con = null;
        DBCriteria dbc;
        try{
          con = getConnection();
          dbc = new DBCriteria();
          dbc.addEqualTo(UploadDataAccess.UPLOAD_ID, pUploadId);
          dbc.addEqualTo(UploadDataAccess.STORE_ID, pStoreId);
          IdVector udIdV =
                    UploadDataAccess.selectIdOnly(con,UploadDataAccess.UPLOAD_ID,dbc);
          if(udIdV.size()==0) {
            String errorMess = "^clw^Table Not Found^clw^" ;
            throw new Exception(errorMess);
          }

          dbc = new DBCriteria();
          dbc.addEqualTo(UploadSkuDataAccess.UPLOAD_ID, pUploadId);
          if(pUploadSkuIds!=null) {
            dbc.addOneOf(UploadSkuDataAccess.UPLOAD_SKU_ID,pUploadSkuIds);
          }
          dbc.addOrderBy(UploadSkuDataAccess.ROW_NUM);

          UploadSkuDataVector uploadSkuDV =
                  UploadSkuDataAccess.select(con,dbc);

          APIAccess apiAccess = APIAccess.getAPIAccess();
          CatalogInformation catInfEjb = apiAccess.getCatalogInformationAPI();
          int storeCatalogId = catInfEjb.getStoreCatalogId(pStoreId);


          dbc = new DBCriteria();
          dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, storeCatalogId);
          dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                  RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
          CatalogStructureDataVector storeSkuDV =
            CatalogStructureDataAccess.select(con,dbc);

          IdVector itemIdV = new IdVector();
          for(Iterator iter=storeSkuDV.iterator(); iter.hasNext();) {
            CatalogStructureData csD = (CatalogStructureData) iter.next();
            itemIdV.add(new Integer(csD.getItemId()));
          }

          //filter out INACTIVE items
          dbc = new DBCriteria();
          dbc.addOneOf(ItemDataAccess.ITEM_ID, itemIdV);
          dbc.addNotEqualToIgnoreCase(ItemDataAccess.ITEM_STATUS_CD, RefCodeNames.ITEM_STATUS_CD.INACTIVE);
          IdVector idV = ItemDataAccess.selectIdOnly(con, dbc);

          dbc = new DBCriteria();
          //dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, itemIdV);
          dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, idV);
          dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                  RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
          dbc.addOrderBy(ItemMappingDataAccess.BUS_ENTITY_ID);
          ItemMappingDataVector distItemMappingDV =
              ItemMappingDataAccess.select(con,dbc);

          dbc = new DBCriteria();
          //dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, itemIdV);
          dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, idV);
          dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                  RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
          dbc.addOrderBy(ItemMappingDataAccess.BUS_ENTITY_ID);
          ItemMappingDataVector manufItemMappingDV =
              ItemMappingDataAccess.select(con,dbc);

          IdVector manufIdV = new IdVector();
          int prevManufId = 0;
          for(Iterator iter=manufItemMappingDV.iterator(); iter.hasNext();) {
            ItemMappingData imD = (ItemMappingData) iter.next();
            int manufId = imD.getBusEntityId();
            if(prevManufId!=manufId) {
              prevManufId = manufId;
              manufIdV.add(new Integer(manufId));
            }
          }


          dbc = new DBCriteria();
          dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,manufIdV);
          dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID);
          BusEntityDataVector manufDV = BusEntityDataAccess.select(con,dbc);
          HashMap manufSkuHM = new HashMap();
          BusEntityData wrkManufD = null;
          for(Iterator iter=manufItemMappingDV.iterator(),iter1=manufDV.iterator(); iter.hasNext();) {
            ItemMappingData imD = (ItemMappingData) iter.next();
            int manufId = imD.getBusEntityId();
            while(wrkManufD!=null || iter1.hasNext()) {
              if(wrkManufD==null) wrkManufD = (BusEntityData) iter1.next();
              int mId = wrkManufD.getBusEntityId();
              if(manufId>mId) {
                wrkManufD = null;
                continue;
              }
              if(manufId<mId) {
                break;
              }
              String key = wrkManufD.getShortDesc()+":"+imD.getItemNum();
              LinkedList imDLL = (LinkedList) manufSkuHM.get(key);
              if(imDLL==null) {
                imDLL = new LinkedList();
                imDLL.add(imD);
                manufSkuHM.put(key,imDLL);
              } else {
                imDLL.add(imD);
              }
              break;
            }
          }



          UploadSkuViewVector matchedItems = new UploadSkuViewVector();

          int manufSkuCol = -1;
          int distSkuCol = -1;
          for(Iterator iter = uploadSkuDV.iterator(); iter.hasNext();) {
            UploadSkuData usD = (UploadSkuData) iter.next();
            UploadSkuViewVector musVwV = new UploadSkuViewVector();
            String skuNum = usD.getSkuNum();
            if(Utility.isSet(skuNum)) {
              for(Iterator iterCatalog=storeSkuDV.iterator(); iterCatalog.hasNext();) {
                CatalogStructureData csD = (CatalogStructureData) iterCatalog.next();
                if(skuNum.equals(csD.getCustomerSkuNum())) {
                  UploadSkuView usVw = UploadSkuView.createValue();
                  UploadSkuData matchUsD = UploadSkuData.createValue();
                  usVw.setUploadSku(matchUsD);
                  matchUsD.setItemId(csD.getItemId());
                  matchUsD.setUploadId(usD.getUploadId());
                  matchUsD.setRowNum(usD.getRowNum());
                  matchUsD.setSkuNum(skuNum);
                  musVwV.add(usVw);
                  break;
                }
              }
            }
            boolean cwSkuMatchFl = (musVwV.size()>0)?true:false;
            if(!cwSkuMatchFl) { //Do not need match distributor if sku matched
              String distSku = usD.getDistSku();
              if(distSku!=null && !"N/A".equalsIgnoreCase(distSku)) {
                distSku = distSku.trim();
                if(distSku.startsWith("0")) {
                  distSku = cutLeadingZero(distSku);
                }
                for(Iterator iterDist=distItemMappingDV.iterator(); iterDist.hasNext();) {
                  ItemMappingData imD = (ItemMappingData) iterDist.next();
                  String distSku1 = imD.getItemNum();
                  if(distSku1==null) continue;
                  distSku1 = distSku1.trim();
                  if(distSku1.startsWith("0")) {
                    distSku1 = cutLeadingZero(distSku1);
                  }
                  if(distSku1.equalsIgnoreCase(distSku)) {
                    UploadSkuView usVw = UploadSkuView.createValue();
                    UploadSkuData matchUsD = UploadSkuData.createValue();
                    usVw.setUploadSku(matchUsD);
                    matchUsD.setItemId(imD.getItemId());
                    matchUsD.setUploadId(usD.getUploadId());
                    matchUsD.setRowNum(usD.getRowNum());
                    matchUsD.setDistId(imD.getBusEntityId());
                    matchUsD.setDistPack(imD.getItemPack());
                    matchUsD.setDistSku(imD.getItemNum());
                    matchUsD.setDistUom(imD.getItemUom());
                    matchUsD.setSpl(imD.getStandardProductList());
                    musVwV.add(usVw);
                  }
                }
              }
            }
            String manufName = usD.getManufName();
            String manufSku = usD.getManufSku();
            if(!cwSkuMatchFl) {
              if(manufSku!=null && !"N/A".equalsIgnoreCase(manufSku)) {
                manufSku = manufSku.trim();
                LinkedList imDLL = (LinkedList) manufSkuHM.get(manufName+":"+manufSku);
                if(imDLL!=null) {
                  for(Iterator manufMapIter=imDLL.iterator(); manufMapIter.hasNext();) {
                    ItemMappingData imD = (ItemMappingData) manufMapIter.next();
                    if(imD!=null) {
                      UploadSkuView usVw = null;
                      UploadSkuData matchUsD = null;
                      for(Iterator iter1=musVwV.iterator(); iter1.hasNext();) {
                        UploadSkuView mUsVw = (UploadSkuView) iter1.next();
                        if(imD.getItemId()==mUsVw.getUploadSku().getItemId()) {
                          usVw = mUsVw;
                          matchUsD = mUsVw.getUploadSku();
                          break;
                        }
                      }
                      if(matchUsD==null) {
                        usVw = UploadSkuView.createValue();
                        matchUsD = UploadSkuData.createValue();
                        usVw.setUploadSku(matchUsD);
                        musVwV.add(usVw);
                      }
                      matchUsD.setItemId(imD.getItemId());
                      matchUsD.setUploadId(usD.getUploadId());
                      matchUsD.setRowNum(usD.getRowNum());
                      matchUsD.setManufId(imD.getBusEntityId());
                      matchUsD.setManufPack(imD.getItemPack());
                      matchUsD.setManufSku(imD.getItemNum());
                      matchUsD.setManufUom(imD.getItemUom());
                    }
                  }
                }
                if(manufSku.startsWith("0")) {
                  manufSku = cutLeadingZero(manufSku);
                  imDLL = (LinkedList) manufSkuHM.get(manufName+":"+manufSku);
                  if(imDLL!=null) {
                    for(Iterator manufMapIter=imDLL.iterator(); manufMapIter.hasNext();) {
                      ItemMappingData imD = (ItemMappingData) manufMapIter.next();
                      if(imD!=null) {
                        UploadSkuView usVw = null;
                        UploadSkuData matchUsD = null;
                        for(Iterator iter1=musVwV.iterator(); iter1.hasNext();) {
                          UploadSkuView mUsVw = (UploadSkuView) iter1.next();
                          if(imD.getItemId()==mUsVw.getUploadSku().getItemId()) {
                            usVw = mUsVw;
                            matchUsD = mUsVw.getUploadSku();
                            break;
                          }
                        }
                        if(matchUsD==null) {
                          usVw = UploadSkuView.createValue();
                          matchUsD = UploadSkuData.createValue();
                          usVw.setUploadSku(matchUsD);
                          musVwV.add(usVw);
                        }
                        matchUsD.setItemId(imD.getItemId());
                        matchUsD.setUploadId(usD.getUploadId());
                        matchUsD.setRowNum(usD.getRowNum());
                        matchUsD.setManufId(imD.getBusEntityId());
                        matchUsD.setManufPack(imD.getItemPack());
                        matchUsD.setManufSku(imD.getItemNum());
                        matchUsD.setManufUom(imD.getItemUom());
                      }
                    }
                  }
                }
              }
            } else { //  cw sku matches
              UploadSkuView mUsVw = (UploadSkuView) musVwV.get(0);
              UploadSkuData matchUsD = mUsVw.getUploadSku();
              int itemId = matchUsD.getItemId();
              for(Iterator iterItemManuf=manufItemMappingDV.iterator();
                                                     iterItemManuf.hasNext();) {
                ItemMappingData imD = (ItemMappingData) iterItemManuf.next();
                if(imD.getItemId()==itemId) {
                  matchUsD.setManufPack(imD.getItemPack());
                  matchUsD.setManufSku(imD.getItemNum());
                  matchUsD.setManufUom(imD.getItemUom());
                  break;
                }
              }
            }
            if(musVwV.size()>0) {
              matchedItems.addAll(musVwV);
            }
          }
          matchedItems = populateItemInfo(con, matchedItems, storeCatalogId);
          return matchedItems;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }

    /**
     *Gets already matched skus
     *@param pStoreId the store id
     *@param pUploadId uploaded table id
     *@param pUploadSkuIds tables skus to match (whole table if null)
     *@throws RemoteException if an error occurs
     */

    public UploadSkuViewVector getMatchedItems(int pStoreId, int pUploadId, IdVector pUploadSkuIds)
    throws RemoteException
    {
        Connection con = null;
        DBCriteria dbc;
        try{
          con = getConnection();
          dbc = new DBCriteria();
          dbc.addEqualTo(UploadDataAccess.UPLOAD_ID, pUploadId);
          dbc.addEqualTo(UploadDataAccess.STORE_ID, pStoreId);
          IdVector udIdV =
                    UploadDataAccess.selectIdOnly(con,UploadDataAccess.UPLOAD_ID,dbc);
          if(udIdV.size()==0) {
            String errorMess = "^clw^Table Not Found^clw^" ;
            throw new Exception(errorMess);
          }


          dbc = new DBCriteria();
          dbc.addEqualTo(UploadSkuDataAccess.UPLOAD_ID, pUploadId);
          if(pUploadSkuIds!=null) {
            dbc.addOneOf(UploadSkuDataAccess.UPLOAD_SKU_ID,pUploadSkuIds);
          }
          dbc.addOrderBy(UploadSkuDataAccess.ROW_NUM);

          UploadSkuDataVector uploadSkuDV =
                  UploadSkuDataAccess.select(con,dbc);

          IdVector matchedIdV = new IdVector();
          HashSet distNameHS = new HashSet();
          for(Iterator iter=uploadSkuDV.iterator(); iter.hasNext();) {
            UploadSkuData usD = (UploadSkuData) iter.next();
            int itemId = usD.getItemId();
            if(itemId>0) {
              matchedIdV.add(new Integer(itemId));
            }
            String distName = usD.getDistName();
            if(Utility.isSet(distName)) {
              if(!distNameHS.contains(distName)) {
                distNameHS.add(distName);
              }
            }
          }
          IdVector distNames = new IdVector();
          for(Iterator iter=distNameHS.iterator(); iter.hasNext();) {
            String distName = (String) iter.next();
            distNames.add(distName);
          }
          dbc = new DBCriteria();
          dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,pStoreId);
          dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
             RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_STORE);
          String storeDistReq =
             BusEntityAssocDataAccess.
                  getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);

          dbc = new DBCriteria();
          dbc.addOneOf(BusEntityDataAccess.SHORT_DESC,distNames);
          dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                  RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
          dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,storeDistReq);
          BusEntityDataVector distDV = BusEntityDataAccess.select(con,dbc);
          IdVector distIdV = new IdVector();
          for(Iterator iter=distDV.iterator(); iter.hasNext();) {
            BusEntityData beD = (BusEntityData) iter.next();
            distIdV.add(new Integer(beD.getBusEntityId()));
          }


          APIAccess apiAccess = APIAccess.getAPIAccess();
          CatalogInformation catInfEjb = apiAccess.getCatalogInformationAPI();
          int storeCatalogId = catInfEjb.getStoreCatalogId(pStoreId);



          dbc = new DBCriteria();
          dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, storeCatalogId);
          dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID,matchedIdV);
          dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                  RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

          IdVector itemIdV =
            CatalogStructureDataAccess.selectIdOnly(con,CatalogStructureDataAccess.ITEM_ID,dbc);

          dbc = new DBCriteria();
          dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, itemIdV);
          dbc.addOneOf(ItemMappingDataAccess.BUS_ENTITY_ID,distIdV);
          dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                  RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
          ItemMappingDataVector distItemMappingDV =
              ItemMappingDataAccess.select(con,dbc);
          HashMap distItemHM = new HashMap();
          for(Iterator iter=distItemMappingDV.iterator(); iter.hasNext();) {
            ItemMappingData imD = (ItemMappingData) iter.next();
            int beId = imD.getBusEntityId();
            for(Iterator iter1=distDV.iterator(); iter1.hasNext();) {
              BusEntityData beD = (BusEntityData) iter1.next();
              if(beId==beD.getBusEntityId()) {
                distItemHM.put(beD.getShortDesc()+":"+imD.getItemId(),  imD);
                break;
              }
            }
          }

          UploadSkuViewVector matchedItems = new UploadSkuViewVector();


          int manufSkuCol = -1;
          int distSkuCol = -1;
          for(Iterator iter = uploadSkuDV.iterator(); iter.hasNext();) {
            UploadSkuData usD = (UploadSkuData) iter.next();
            int itemId = usD.getItemId();
            if(itemId<=0) {
              continue;
            }
            UploadSkuView usVw = UploadSkuView.createValue();
            UploadSkuData matchUsD = UploadSkuData.createValue();
            usVw.setUploadSku(matchUsD);
            matchUsD.setItemId(itemId);
            matchUsD.setUploadId(usD.getUploadId());
            matchUsD.setRowNum(usD.getRowNum());
            ItemMappingData imD = (ItemMappingData) distItemHM.get(usD.getDistName()+":"+itemId);
            if(imD!=null) {
               matchUsD.setDistId(imD.getBusEntityId());
               matchUsD.setDistPack(imD.getItemPack());
               matchUsD.setDistSku(imD.getItemNum());
               matchUsD.setDistUom(imD.getItemUom());
               matchUsD.setSpl(imD.getStandardProductList());
            }
            matchedItems.add(usVw);
          }

          matchedItems = populateItemInfo(con, matchedItems, storeCatalogId);
          return matchedItems;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }

    private UploadSkuViewVector
            populateItemInfo(Connection pCon, UploadSkuViewVector pUploadItems, int pStoreCatalogId)
    throws Exception {
      IdVector itemIdV = new IdVector();
      IdVector itemIdV1 = new IdVector();

      for(Iterator iter = pUploadItems.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        Integer itemIdI = new Integer(usD.getItemId());
        itemIdV.add(itemIdI);
        if(usD.getManufId()==0) {
          itemIdV1.add(itemIdI);
        }
      }

      //Item
      DBCriteria dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID,itemIdV);
      ItemDataVector itemDV = ItemDataAccess.select(pCon,dbc);
      for(Iterator iter = pUploadItems.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        int itemId = usD.getItemId();
        for(Iterator iter1 = itemDV.iterator(); iter1.hasNext();) {
          ItemData iD = (ItemData) iter1.next();
          if(itemId==iD.getItemId()) {
            usD.setShortDesc(iD.getShortDesc());
            usD.setLongDesc(iD.getLongDesc());
            break;
          }
        }
      }

      // ItemMeta
      ArrayList al = new ArrayList();
      al.add("SIZE");
      al.add("PACK");
      al.add("UOM");
      al.add("COLOR");
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMetaDataAccess.ITEM_ID,itemIdV);
      dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE, al);
      dbc.addOrderBy(ItemMetaDataAccess.ITEM_ID);
      ItemMetaDataVector itemMetaDV = ItemMetaDataAccess.select(pCon,dbc);
      for(Iterator iter = pUploadItems.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        int itemId = usD.getItemId();
        boolean doneFl = false;
        for(Iterator iter1 = itemMetaDV.iterator(); iter1.hasNext();) {
          ItemMetaData imD = (ItemMetaData) iter1.next();
          if(itemId!=imD.getItemId()) {
            if(doneFl) {
              break;
            } else {
              continue;
            }
          }
          doneFl = true;
          String nameValue = imD.getNameValue();
          if("SIZE".equals(nameValue)) {
            usD.setSkuSize(imD.getValue());
          }
          if("PACK".equals(nameValue)) {
            usD.setSkuPack(imD.getValue());
          }
          if("UOM".equals(nameValue)) {
            usD.setSkuUom(imD.getValue());
          }
          if("COLOR".equals(nameValue)) {
            usD.setSkuColor(imD.getValue());
          }
        }
      }

      //Manufacturer Mapping
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMappingDataAccess.ITEM_ID,itemIdV1);
      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
              RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
      ItemMappingDataVector manufMappingDV =
              ItemMappingDataAccess.select(pCon,dbc);

      for(Iterator iter = pUploadItems.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        if(usD.getManufId()!=0) {
          continue;
        }
        int itemId = usD.getItemId();
        for(Iterator iter1 = manufMappingDV.iterator(); iter1.hasNext();) {
          ItemMappingData imD = (ItemMappingData) iter1.next();
          if(itemId==imD.getItemId()) {
            usD.setManufId(imD.getBusEntityId());
            usD.setManufPack(imD.getItemPack());
            usD.setManufSku(imD.getItemNum());
            usD.setManufUom(imD.getItemUom());
            break;
          }
        }
      }

      //Manufacturer Name
      IdVector manufIdV = new IdVector();
      int manufIdPrev = 0;
      for(Iterator iter = pUploadItems.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        int manufId = usD.getManufId();
        if(manufId!=manufIdPrev) {
          manufIdPrev = manufId;
          manufIdV.add(new Integer(manufId));
        }
      }
      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, manufIdV);
      BusEntityDataVector manufDV = BusEntityDataAccess.select(pCon,dbc);
      for(Iterator iter = pUploadItems.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        int manufId = usD.getManufId();
        for(Iterator iter1 = manufDV.iterator(); iter1.hasNext();) {
          BusEntityData beD = (BusEntityData) iter1.next();
          if(manufId==beD.getBusEntityId()) {
            usD.setManufName(beD.getShortDesc());
            break;
          }
        }
      }

      //Distributor name
      IdVector distIdV = new IdVector();
      int distIdPrev = 0;
      for(Iterator iter = pUploadItems.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        int distId = usD.getDistId();
        if(distId==0) {
          continue;
        }
        if(distId!=distIdPrev) {
          distIdPrev = distId;
          distIdV.add(new Integer(distId));
        }
      }
      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, distIdV);
      BusEntityDataVector distDV = BusEntityDataAccess.select(pCon,dbc);
      for(Iterator iter = pUploadItems.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        int distId = usD.getDistId();
        if(distId==0) {
          continue;
        }
        for(Iterator iter1 = distDV.iterator(); iter1.hasNext();) {
          BusEntityData beD = (BusEntityData) iter1.next();
          if(distId==beD.getBusEntityId()) {
            usD.setDistName(beD.getShortDesc());
            break;
          }
        }
      }

      //Store catalog category
      dbc = new DBCriteria();
      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID,itemIdV);
      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
              RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID,pStoreCatalogId);
      dbc.addOrderBy(ItemAssocDataAccess.ITEM2_ID);
      ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(pCon,dbc);

      IdVector categIdV = new IdVector();
      int categIdPrev = 0;
      for(Iterator iter=itemAssocDV.iterator(); iter.hasNext();) {
        ItemAssocData iaD = (ItemAssocData) iter.next();
        int categId = iaD.getItem2Id();
        if(categId!=categIdPrev) {
          categIdPrev = categId;
          categIdV.add(new Integer(categId));
        }
      }
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, categIdV);
      ItemDataVector categDV = ItemDataAccess.select(pCon,dbc);
      for(Iterator iter = pUploadItems.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        int itemId = usD.getItemId();
        for(Iterator iter1 = itemAssocDV.iterator(); iter1.hasNext();) {
          ItemAssocData iaD = (ItemAssocData) iter1.next();
          if(itemId==iaD.getItem1Id()) {
            int categId = iaD.getItem2Id();
            for(Iterator iter2=categDV.iterator(); iter2.hasNext();) {
              ItemData categD = (ItemData) iter2.next();
              if(categId==categD.getItemId()) {
                usD.setCategory(categD.getShortDesc());
                break;
              }
            }
            break;
          }
        }
      }

      //Store catalog sku
      dbc = new DBCriteria();
      dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID,itemIdV);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
              RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,pStoreCatalogId);
      CatalogStructureDataVector catStructDV = CatalogStructureDataAccess.select(pCon,dbc);

      for(Iterator iter = pUploadItems.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        int itemId = usD.getItemId();
        for(Iterator iter1 = catStructDV.iterator(); iter1.hasNext();) {
          CatalogStructureData csD = (CatalogStructureData) iter1.next();
          if(itemId==csD.getItemId()) {
            usVw.setSkuNum(csD.getCustomerSkuNum());
            break;
          }
        }
      }
      //return
      return pUploadItems;
    }

    private String cutLeadingZero(String pVal) {
      char[] bbb = pVal.toCharArray();
      int pos = 0;
      for(; pos<bbb.length; pos++) {
        if(bbb[pos]!='0') break;
      }
      String retVal = new String(bbb,pos,bbb.length-pos);
      return retVal;

    }
    /**
     *Updates upload skus
     *@param uploadSkus upload skus to update
     *@param String pUser uder login name
     *@throws RemoteException if an error occurs
     */
    public void updateUploadSkus (UploadSkuView[] uploadSkus, String pUser)
    throws RemoteException
    {
        Connection con = null;
        if(uploadSkus == null) return;
        try{
          con = getConnection();
          for(int ii=0; ii<uploadSkus.length; ii++) {
           UploadSkuData usD = uploadSkus[ii].getUploadSku();
           if(usD==null) {
             continue;
           }
           UploadSkuDataAccess.update(con,usD);
          }
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }

    /**
     *  Gets sku info for matched items from the spreadSheet
     *@param pUploadSkuIds tables skus to match (all table if null)
     *@return  a set of UploadSkuView objects
     *@exception  RemoteException
     */
    public UploadSkuViewVector getMatchedUploadSkus(int pUploadId, IdVector pUploadSkuIds)
        throws RemoteException
    {
      Connection con = null;
      try {
      con = getConnection();
      UploadData uploadD = UploadDataAccess.select(con,pUploadId);
      int storeId = uploadD.getStoreId();

      APIAccess apiAccess = APIAccess.getAPIAccess();
      CatalogInformation catInfEjb = apiAccess.getCatalogInformationAPI();
      int storeCatalogId = catInfEjb.getStoreCatalogId(storeId);

      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(UploadSkuDataAccess.UPLOAD_ID, pUploadId);
      if(pUploadSkuIds!=null) {
        dbc.addOneOf(UploadSkuDataAccess.UPLOAD_SKU_ID, pUploadSkuIds);
      }
      dbc.addNotEqualTo(UploadSkuDataAccess.ITEM_ID,0);
      dbc.addOrderBy(UploadSkuDataAccess.ITEM_ID);
      UploadSkuDataVector uploadSkuDV =
              UploadSkuDataAccess.select(con,dbc);

      IdVector itemIdV = new IdVector();
      for(Iterator iter=uploadSkuDV.iterator(); iter.hasNext();) {
        UploadSkuData usD = (UploadSkuData) iter.next();
        int itemId = usD.getItemId();
        itemIdV.add(new Integer(itemId));
      }

      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID,itemIdV);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);

      ItemDataVector itemDV = ItemDataAccess.select(con,dbc);
      UploadSkuViewVector uploadSkus = new UploadSkuViewVector();
      ItemData wrkItemD = null;
      for(Iterator iter=uploadSkuDV.iterator(), iter1=itemDV.iterator(); iter.hasNext();) {
        UploadSkuView usVw = UploadSkuView.createValue();
        uploadSkus.add(usVw);
        UploadSkuData usD = (UploadSkuData) iter.next();
        usVw.setUploadSku(usD);
        usVw.setSelectFlag(false);
        int itemId = usD.getItemId();
        while(wrkItemD!=null || iter1.hasNext()) {
          if(wrkItemD==null) wrkItemD = (ItemData) iter1.next();
          int iId = wrkItemD.getItemId();
          if(iId>itemId) {
            break;
          }
          if(iId<itemId) {
            wrkItemD = null;
            continue;
          }
          usD.setShortDesc(wrkItemD.getShortDesc());
          break;
        }
      }

      // ItemMeta
      ArrayList al = new ArrayList();
      al.add("SIZE");
      al.add("PACK");
      al.add("UOM");
      al.add("COLOR");
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMetaDataAccess.ITEM_ID,itemIdV);
      dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE, al);
      dbc.addOrderBy(ItemMetaDataAccess.ITEM_ID);
      ItemMetaDataVector itemMetaDV = ItemMetaDataAccess.select(con,dbc);
      for(Iterator iter = uploadSkus.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        int itemId = usD.getItemId();
        boolean doneFl = false;
        for(Iterator iter1 = itemMetaDV.iterator(); iter1.hasNext();) {
          ItemMetaData imD = (ItemMetaData) iter1.next();
          if(itemId!=imD.getItemId()) {
            if(doneFl) {
              break;
            } else {
              continue;
            }
          }
          doneFl = true;
          String nameValue = imD.getNameValue();
          if("SIZE".equals(nameValue)) {
            usD.setSkuSize(imD.getValue());
          }
          if("PACK".equals(nameValue)) {
            usD.setSkuPack(imD.getValue());
          }
          if("UOM".equals(nameValue)) {
            usD.setSkuUom(imD.getValue());
          }
          if("COLOR".equals(nameValue)) {
            usD.setSkuColor(imD.getValue());
          }
        }
      }

      //Manufacturer Mapping
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMappingDataAccess.ITEM_ID,itemIdV);
      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
              RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
      ItemMappingDataVector manufMappingDV =
              ItemMappingDataAccess.select(con,dbc);

      for(Iterator iter = uploadSkus.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        if(usD.getManufId()!=0) {
          continue;
        }
        int itemId = usD.getItemId();
        for(Iterator iter1 = manufMappingDV.iterator(); iter1.hasNext();) {
          ItemMappingData imD = (ItemMappingData) iter1.next();
          if(itemId==imD.getItemId()) {
            usD.setManufId(imD.getBusEntityId());
            usD.setManufPack(imD.getItemPack());
            usD.setManufSku(imD.getItemNum());
            usD.setManufUom(imD.getItemUom());
            break;
          }
        }
      }

      //Manufacturer Name
      IdVector manufIdV = new IdVector();
      int manufIdPrev = 0;
      for(Iterator iter = uploadSkus.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        int manufId = usD.getManufId();
        if(manufId!=manufIdPrev) {
          manufIdPrev = manufId;
          manufIdV.add(new Integer(manufId));
        }
      }
      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, manufIdV);
      BusEntityDataVector manufDV = BusEntityDataAccess.select(con,dbc);
      for(Iterator iter = uploadSkus.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        int manufId = usD.getManufId();
        for(Iterator iter1 = manufDV.iterator(); iter1.hasNext();) {
          BusEntityData beD = (BusEntityData) iter1.next();
          if(manufId==beD.getBusEntityId()) {
            usD.setManufName(beD.getShortDesc());
            break;
          }
        }
      }

      //Distributor & Gen. Manuf. Mapping
      ArrayList distNameAL = new ArrayList();
      ArrayList genManufNameAL = new ArrayList();
      String prevDistName = "";
      String prevGenManufName = "";
      for(Iterator iter = uploadSkus.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        String distName = usD.getDistName();
        if(Utility.isSet(distName)) {
          if(!distName.equals(prevDistName)) {
            distNameAL.add(distName);
            prevDistName = distName;
          }
        }
        String genManufName = usD.getGenManufName();
        if(Utility.isSet(genManufName)) {
          if(!genManufName.equals(prevGenManufName)) {
            genManufNameAL.add(genManufName);
            prevGenManufName = genManufName;
          }
        }
      }
      //Distributor
      dbc = new DBCriteria();
      dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,storeId);
      dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
              RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_STORE);
      String distReq =
        BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID,dbc);


      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.SHORT_DESC,distNameAL);
      dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
             RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
      dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
             RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,distReq);

      BusEntityDataVector distDV = BusEntityDataAccess.select(con,dbc);

      for(Iterator iter = uploadSkus.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        String distName = usD.getDistName();
        if(!Utility.isSet(distName)) {
          continue;
        }
        for(Iterator iter1=distDV.iterator(); iter1.hasNext(); ) {
          BusEntityData distD = (BusEntityData) iter1.next();
          if(distName.equals(distD.getShortDesc())) {
            usD.setDistId(distD.getBusEntityId());
            usD.setDistStatus(distD.getBusEntityStatusCd());
            break;
          }
        }
      }

      //Generic Manufacturer
      dbc = new DBCriteria();
      dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,storeId);
      dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
              RefCodeNames.BUS_ENTITY_ASSOC_CD.MANUFACTURER_STORE);
      String genManufReq =
        BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID,dbc);


      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.SHORT_DESC,genManufNameAL);
      dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
             RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
      dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
             RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,genManufReq);
      BusEntityDataVector genManufDV = BusEntityDataAccess.select(con,dbc);

      for(Iterator iter = uploadSkus.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        String genManufName = usD.getGenManufName();
        if(!Utility.isSet(genManufName)) {
          continue;
        }
        for(Iterator iter1=genManufDV.iterator(); iter1.hasNext(); ) {
          BusEntityData genManufD = (BusEntityData) iter1.next();
          if(genManufName.equals(genManufD.getShortDesc())) {
            usD.setGenManufId(genManufD.getBusEntityId());
            break;
          }
        }
      }

      //Category
      ArrayList categAL = new ArrayList();
      IdVector findCategItemIdV = new IdVector();
      String prevCateg = "";

      for(Iterator iter = uploadSkus.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        String categ = usD.getCategory();
        if(Utility.isSet(categ)) {
          if(!categ.equals(prevCateg)) {
            categAL.add(categ);
            prevCateg = categ;
          }
        } else {
          findCategItemIdV.add(new Integer(usD.getItemId()));
        }
      }

      dbc = new DBCriteria();
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
              RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,storeCatalogId);

      String categReq =
        CatalogStructureDataAccess.getSqlSelectIdOnly(CatalogStructureDataAccess.ITEM_ID,dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID,categReq);

      ItemDataVector categDV = ItemDataAccess.select(con,dbc);
      for(Iterator iter = uploadSkus.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        String categ = usD.getCategory();
        if(Utility.isSet(categ)) {
		  categ = categ.trim();
          for(Iterator iter1 = categDV.iterator(); iter1.hasNext();) {
            ItemData categD = (ItemData) iter1.next();
            if(Utility.isSet(categD.getLongDesc())){
            	//compare contactonated names of short and admin category.  Either use the
            	//way it displays in the UI:
            	//Category Name (Admin Category)
            	//or
            	//Category Name-Admin Category
            	if(categ.equals(categD.getShortDesc() + " (" +categD.getLongDesc() + ")") || categ.equals(categD.getShortDesc() + "-" +categD.getLongDesc() )
            			|| categ.equals(categD.getShortDesc() + "(" +categD.getLongDesc() + ")") ) {
  	              usVw.setCategoryId(categD.getItemId());
  	              break;
  	            }
            }else{
	            if(categ.equals(categD.getShortDesc())) {
	              usVw.setCategoryId(categD.getItemId());
	              break;
	            }
            }
          }
        }
      }

      //Not set categories
      dbc = new DBCriteria();
      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, findCategItemIdV);
      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
              RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID,storeCatalogId);
      dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);
      ItemAssocDataVector itemCategDV =
                             ItemAssocDataAccess.select(con,dbc);

      IdVector categIdV = new IdVector();
      int prevCategId = 0;
      for(Iterator iter = itemCategDV.iterator();iter.hasNext();) {
        ItemAssocData iaD = (ItemAssocData) iter.next();
        int categId = iaD.getItem2Id();
        if(prevCategId!=categId) {
          prevCategId = categId;
          categIdV.add(new Integer(categId));
        }
      }
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, categIdV);
      ItemDataVector cagegDV = ItemDataAccess.select(con,dbc);

      ItemAssocData wrkItemAssocD = null;
      for(Iterator iter = uploadSkus.iterator(), iter1=itemCategDV.iterator();
                                                              iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        String categ = usD.getCategory();
        if(Utility.isSet(categ)) {
          continue;
        }
        int itemId = usD.getItemId();
        while (wrkItemAssocD!=null || iter1.hasNext()) {
          if(wrkItemAssocD==null) wrkItemAssocD = (ItemAssocData) iter1.next();
          int iId = wrkItemAssocD.getItem1Id();
          if(iId>itemId) {
            break;
          }
          if(iId<itemId) {
            wrkItemAssocD = null;
            continue;
          }
          int categId = wrkItemAssocD.getItem2Id();
          for(Iterator iter2 = categDV.iterator(); iter2.hasNext();) {
            ItemData categD = (ItemData) iter2.next();
            if(categId==categD.getItemId()) {
              usD.setCategory(categD.getShortDesc());
              usVw.setCategoryId(categId);
              break;
            }
          }
          wrkItemAssocD = null;
          break;
        }
      }


      //Store catalog sku
      dbc = new DBCriteria();
      dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID,itemIdV);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
              RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,storeCatalogId);
      CatalogStructureDataVector catStructDV = CatalogStructureDataAccess.select(con,dbc);

      for(Iterator iter = uploadSkus.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        int itemId = usD.getItemId();
        for(Iterator iter1 = catStructDV.iterator(); iter1.hasNext();) {
          CatalogStructureData csD = (CatalogStructureData) iter1.next();
          if(itemId==csD.getItemId()) {
            usVw.setSkuNum(csD.getCustomerSkuNum());
            break;
          }
        }
      }
      return uploadSkus;
    } catch(Exception e){
      e.printStackTrace();
        throw new RemoteException(e.getMessage());
     }finally{
            closeConnection(con);
     }
   }

    /**
     *Function returns the catalog ids witch is associated with the supplied item Id
     *exclude CatalogTypeCd equal Store and System
     * @param  itemId   Description of Parameter
     * @return   The CatalogIdVector value
     * @throws RemoteException
     */
    public   IdVector getCatalogIdsExcludeStoreAndSystem(int  itemId)  throws RemoteException
    {

        IdVector ids=new IdVector();
        Connection conn=null;
        try {
            conn=getConnection();

            DBCriteria crit=new DBCriteria();

            crit.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
            crit.addEqualTo(CatalogStructureDataAccess.ITEM_ID,itemId);

            CatalogStructureDataVector catalogSDVector = CatalogStructureDataAccess.select(conn, crit);

            Iterator it = catalogSDVector.iterator();
            while(it.hasNext())
            {
                int catalogId= ((CatalogStructureData)it.next()).getCatalogId();
                CatalogData catalogData = CatalogDataAccess.select(conn, catalogId);
                if  (!catalogData.getCatalogTypeCd().equals(RefCodeNames.CATALOG_TYPE_CD.STORE)
                        &&!catalogData.getCatalogTypeCd().equals(RefCodeNames.CATALOG_TYPE_CD.SYSTEM))
                {
                 ids.add(new Integer(catalogId));
                }
            }
            return ids;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error. ItemInformationBean.getCatalogIdsExcludeStoreAndSystem SQL Exception: "+e.getMessage());
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RemoteException("Error. ItemInformationBean.getCatalogIdsExcludeStoreAndSystem Naming Exception happened");
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            throw new RemoteException("Error. ItemInformationBean.getCatalogIdsExcludeStoreAndSystem : "+e.getMessage());
        } finally{
            closeConnection(conn);
        }
    }

    /**
     *Method returns the catalog ids witch is associated with the supplied item Id
     * @param  itemId   Description of Parameter
     * @return   The CatalogIdVector value
     * @throws RemoteException
     */

    public IdVector getCatalogIds(int itemId) throws RemoteException {

        IdVector ids=new IdVector();
        Connection conn=null;
        try {
            conn=getConnection();

            DBCriteria crit=new DBCriteria();

            crit.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
            crit.addEqualTo(CatalogStructureDataAccess.ITEM_ID,itemId);

            CatalogStructureDataVector catalogSDVector = CatalogStructureDataAccess.select(conn, crit);

            Iterator it = catalogSDVector.iterator();
            while(it.hasNext())
            {
                int catalogId= ((CatalogStructureData)it.next()).getCatalogId();
                CatalogData catalogData = CatalogDataAccess.select(conn, catalogId);

                 ids.add(new Integer(catalogId));

            }
            return ids;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error. ItemInformationBean.getCatalogIds SQL Exception: "+e.getMessage());
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RemoteException("Error. ItemInformationBean.getCatalogIds Naming Exception happened");
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            throw new RemoteException("Error. ItemInformationBean.getCatalogIds : "+e.getMessage());
        } finally{
            closeConnection(conn);
        }
    }

    public IdVector getItemsCollectionByItemShortDescription(String shortDescription)  throws RemoteException {

        Connection conn=null;
        try {
//        	shortDescription = "Vacuums";
            conn=getConnection();
            DBCriteria crit=new DBCriteria();
            crit.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.CATEGORY);
            crit.addEqualTo(ItemDataAccess.SHORT_DESC, shortDescription);
            IdVector categIds = ItemDataAccess.selectIdOnly(conn, crit);
//            Iterator it = categDV.iterator();
//            while(it.hasNext()) {
//                int categId = ((ItemData)it.next()).getItemId();
//                CatalogData catalogData = CatalogDataAccess.select(conn, categId);
//                if  (!catalogData.getCatalogTypeCd().equals(RefCodeNames.CATALOG_TYPE_CD.STORE)
//                        &&!catalogData.getCatalogTypeCd().equals(RefCodeNames.CATALOG_TYPE_CD.SYSTEM))
//                {
//                 ids.add(new Integer(categId));
//                }
//            }
            return categIds;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error. ItemInformationBean.getItemsCollectionByItemShortDescription SQL Exception: "+e.getMessage());
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RemoteException("Error. ItemInformationBean.getItemsCollectionByItemShortDescription Naming Exception happened");
        } finally{
            closeConnection(conn);
        }
    }

    /**
     * Gets the array-like item vector values to be used by the request.
     * (Search by item ids).
     * @param itemIds  the item ids
     * @return ItemDataVector
     * @throws RemoteException Required by EJB 1.0
     */
    public ItemDataVector getItemCollection(IdVector itemIds) throws RemoteException {

        IdVector ids = new IdVector();
        Connection conn = null;
        ItemDataVector itemDataVector = new ItemDataVector();
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(ItemDataAccess.ITEM_ID, itemIds);
            dbc.addOrderBy(ItemDataAccess.ITEM_ID);
            itemDataVector = ItemDataAccess.select(conn, dbc);
            return itemDataVector;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error. ItemInformationBean.getItemCollection SQL Exception: " + e.getMessage());
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RemoteException("Error. ItemInformationBean.getItemCollection Naming Exception happened");
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * Return doc urls of items for concrete site, ordered during last year.
     *
     * @param pSiteId
     *            Site's ID.
     * @return
     * @throws RemoteException
     */
    public ItemDataDocUrlsViewVector getOrderedItemDataForLastYear(int pSiteId)
        throws RemoteException {
      Connection con = null;
      try {
        con = getConnection();
        /**
         * First query, select orders ID for last year.
         */
        log.info("Before pulling out Orders placed during the last year");
        DBCriteria dbc = getLastYearCriteria();
        dbc.addEqualTo(OrderDataAccess.SITE_ID, pSiteId);
        IdVector orderIdV = OrderDataAccess.selectIdOnly(con,
            OrderDataAccess.ORDER_ID, dbc);
        log.info("After pulling out Orders placed during the last year");        
        /**
         * Second query, select ordered items for last year.
         */
        dbc = new DBCriteria();
        dbc.addOneOf(OrderItemDataAccess.ORDER_ID, orderIdV);
        IdVector orderItemIdV = OrderItemDataAccess.selectIdOnly(con,
            OrderItemDataAccess.ITEM_ID, dbc);
        /**
         * Third query, select ordered items for last year.
         */
        dbc = new DBCriteria();
        dbc.addOneOf(ItemDataAccess.ITEM_ID, orderItemIdV);

        dbc = new DBCriteria();
        ArrayList docTypes = new ArrayList();
        docTypes.add("MSDS");
        docTypes.add("SPEC");
        docTypes.add("DED");
        dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE, docTypes);
        dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, orderItemIdV);
        /**
         * Third query, select metadata of items.
         */
        ItemMetaDataVector itemMetaDataV = ItemMetaDataAccess.select(con,
            dbc);
        /**
         * Fourth query, select data for items.
         */
        dbc = new DBCriteria();
        dbc.addOneOf(ItemDataAccess.ITEM_ID, orderItemIdV);
        ItemDataVector itemDataV = ItemDataAccess.select(con, dbc);
        final ItemDataDocUrlsViewVector items = collectMetaData(itemDataV,
            itemMetaDataV);
        return items;
      } catch (NamingException exc) {
        logError("exc.getMessage");
        exc.printStackTrace();
        throw new RemoteException(
            "Error. MsdsSpecsBean.addItemsToDocCloset() Naming Exception happened. "
                + exc.getMessage());
      } catch (SQLException exc) {
        logError("exc.getMessage");
        exc.printStackTrace();
        throw new RemoteException(
            "Error. MsdsSpecsBean.addItemsToDocCloset() SQL Exception happened. "
                + exc.getMessage());
      } finally {
        closeConnection(con);
      }
    }

    /**
   * Return doc urls of items for concrete site, ordered during last year.
   *
   * @param pSiteId
   *            Site's ID.
   * @return
   * @throws RemoteException
   */
  public ItemDataDocUrlsViewVector getOrderedItemDataForLastYear(int pSiteId, String userLocale, String countryCode, String storeLocale)
      throws RemoteException {
	  
	log.info("pSiteId = " + pSiteId);  
	log.info("userLocale = " + userLocale); 
	log.info("countryCode = " + countryCode); // NOT the VALUE I'm looking for - should be US, NOT "UNITED STATES"(it is taken from clw_address table)
	log.info("storeLocale = " + storeLocale); 
	
    Connection con = null;
    
    log.info("SVC_MSDS: inside getOrderedItemDataForLastYear() method");
    
    ItemDataDocUrlsViewVector itemsAll = new ItemDataDocUrlsViewVector();
        
    try {
      con = getConnection();
      /**
       * 1.0 query, select orders ID for last year.
       */
      DBCriteria dbc = getLastYearCriteria();
      dbc.addEqualTo(OrderDataAccess.SITE_ID, pSiteId);
      IdVector orderIdV = OrderDataAccess.selectIdOnly(con,
          OrderDataAccess.ORDER_ID, dbc);
      /**
       * 2.0 query, select ordered items for the last year.
       */
      dbc = new DBCriteria();
      dbc.addOneOf(OrderItemDataAccess.ORDER_ID, orderIdV);
      IdVector orderItemIdV = OrderItemDataAccess.selectIdOnly(con,
          OrderItemDataAccess.ITEM_ID, dbc);
      log.info("Ordered items for the last year: orderItemIdV = " + orderItemIdV);
      /**
       * 2.1 query: from the list of ordered items for the last year 
       * 
       * find the items, which Manufacturer has the MSDS property = "DiverseyWebServices".
       * In terms of data in the DB the latter means, that there IS an entry in the DB table CLW_PROPERTY
       * for this Manufacturer with the following variables' values:
       * SHORT_DESC = "MSDS Plug-in"; CLW_VALUE = "DiverseyWebServices"; PROPERTY_STATUS_CD = "ACTIVE';
       * PROPERTY_TYPE_CD = "EXTRA";
       * It is customer's responsibility to set up MSDS Property to "DiverseyWebServices" for ONLY "JohnsonDiversey" Manufacturer.
       * If a customer sets up this property for another manufacturer, JohnsonDiversey Web Services will be used in Order
       * to retrieve an MSDS for this (NOT Johnson Diversey) Manufacturer (which is wrong).
       */      
    	  
       // Get Item Ids and Manufacturer Skus for these Item Ids
       // Filter: selected Item Ids are produced by a Manufacturer, which has property "DiverseyWebServices"      
       HashMap itemManufacturerHM = new HashMap();      
       HashMap itemShortDescHM = new HashMap();
      
       if (orderItemIdV.size() > 0)
       {	   
          String itemIdsManufacturersSQL =     	
    	  
          " select i.item_id, i.short_desc, im.item_num manuf_sku " + /* item_num = "Manuf. Sku" */
    	  
    	  " from clw_item i, clw_item_mapping im, clw_property p " +
    	  
    	  " where i.item_id in ("+IdVector.toCommaString(orderItemIdV)+") " + /* 92280 */
    	  
    	  " and i.item_id = im.item_id " +
    	  
    	  " and im.item_mapping_cd = '"+RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER+"' " + 
    	  
    	  " and i.item_status_cd = '"+RefCodeNames.STATUS_CD.ACTIVE+"' " +
    	  
    	  " and im.bus_entity_id = p.bus_entity_id " + /* item Manufacturer */
    	  
    	  " and p.short_desc = '"+RefCodeNames.PROPERTY_TYPE_CD.MSDS_PLUGIN+"' " +
    	  
    	  " and p.clw_value = '"+RefCodeNames.MSDS_PLUGIN_CD.DIVERSEY_WEB_SERVICES+"' " +
    	  
    	  " and p.property_type_cd = '"+RefCodeNames.PROPERTY_TYPE_CD.EXTRA+"'";
      
          log.info("getOrderedItemDataForLastYear(); itemIdsManufacturersSQL = " + itemIdsManufacturersSQL);
    	  
          //HashMap itemManufacturerHM = new HashMap();
          
          //HashMap itemShortDescHM = new HashMap();

          Statement stmt = con.createStatement();

          ResultSet rs = stmt.executeQuery(itemIdsManufacturersSQL);

          while (rs.next()) {

              Integer itemIdI = new Integer(rs.getInt("item_id"));

              String manufSku = rs.getString("manuf_sku");

              String itemShortDesc = rs.getString("short_desc");
              
              itemManufacturerHM.put(itemIdI, manufSku);
              
              itemShortDescHM.put(itemIdI, itemShortDesc);

          }

          rs.close();

          stmt.close();
      }    
      
      log.info("itemManufacturerHM_1 = " + itemManufacturerHM);
      log.info("itemShortDescHM_1 = " + itemShortDescHM);
      
      /**
       * 3.0 query, select ordered items for last year.
       */
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, orderItemIdV);

      dbc = new DBCriteria();  
      ArrayList docTypes = new ArrayList();
      docTypes.add("MSDS");
      docTypes.add("SPEC");
      docTypes.add("DED");
      dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE, docTypes);
      dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, orderItemIdV);
      /**
       * 3.0 query, select metadata of items.
       */
      ItemMetaDataVector itemMetaDataV = ItemMetaDataAccess.select(con,
          dbc);
      /**
       * 4.0 query, select data for items.
       */
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, orderItemIdV);
      ItemDataVector itemDataV = ItemDataAccess.select(con, dbc);
      //final ItemDataDocUrlsViewVector items = collectMetaData(itemDataV,
      //    itemMetaDataV);       
      
      ItemDataDocUrlsViewVector items = collectMetaData(itemDataV,
              itemMetaDataV);
      log.info("MSDS_SVC: items = " + items);
      
      // delete items from itemManufacturerHM and itemShortDescHM, which have MSDS set.
      // It means that MSDS PDF files rule (if MSDS is SET, it has priority over URL, 
      // received via JohnsonDiversey Web Services)

      // Begin 
  	  Iterator<Integer> it_idurl = itemManufacturerHM.keySet().iterator();
  	  Iterator<Integer> it_idur2 = itemShortDescHM.keySet().iterator();
  	  int j = 0;
	  while (it_idurl.hasNext() && it_idur2.hasNext()) {
		log.info("inside the loop through itemManufacturerHM " + j);
		j += 1;
        Integer key1 = (Integer)it_idurl.next(); //key = item Id
        Integer key2 = (Integer)it_idur2.next(); //key = item Id
        log.info("Integer key1 = " + key1);
        log.info("Integer key2 = " + key2);
        for(int i = 0; i < items.size(); i++) {  //loop through all the elements of "items" (array of Objects)
        	ItemDataDocUrlsView itemDocums = (ItemDataDocUrlsView) items.get(i);
        	if (key1.intValue() == itemDocums.getItemId()) {
        	  if ((itemDocums.getMsds() != null) && (itemDocums.getMsds().length()>0)) {
        		  //delete element from itemManufacturerHM and itemShortDescHM hashmaps
        		  it_idurl.remove();
        		  it_idur2.remove();
        		break;
        	  }
        	}        		
        }
	  }
      // End 
      
      log.info("itemManufacturerHM_2 = " + itemManufacturerHM);
      log.info("itemShortDescHM_2 = " + itemShortDescHM);
      
      // Find items' URLs that are supplied via JohnsonDiverseyWebServices
      HashMap itemDiverseyUrl = new HashMap();    
      if (itemManufacturerHM.size() > 0) {     
         itemDiverseyUrl = collectDiverseyWebServicesURLs(itemManufacturerHM, userLocale, countryCode, storeLocale);
      }

      log.info("itemDiverseyUrl = " + itemDiverseyUrl);
      
      /*ItemDataDocUrlsViewVector*/ itemsAll = items;
      if (itemDiverseyUrl.size() > 0 && itemDiverseyUrl != null) {
          itemsAll = addDiverseyWebServicesURLs(itemsAll, itemDiverseyUrl, itemShortDescHM);
      }      
      
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
          "Error. ItemInformationBean.getOrderedItemDataForLastYear() Naming Exception happened. "
              + exc.getMessage());
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
          "Error. ItemInformationBean.getOrderedItemDataForLastYear() SQL Exception happened. "
              + exc.getMessage());
    } finally {
      closeConnection(con);
    }
    
    log.info("itemsAll = " + itemsAll);
    
    return itemsAll;
  }

  private final static DBCriteria getLastYearCriteria() {
    DBCriteria dbc = new DBCriteria();
    GregorianCalendar gCal = new GregorianCalendar();
    Date endDate = gCal.getTime();
    gCal.add(GregorianCalendar.YEAR, -1);
    Date begDate = gCal.getTime();
    dbc.addGreaterOrEqual(OrderDataAccess.ADD_DATE, begDate);
    dbc.addLessOrEqual(OrderDataAccess.ADD_DATE, endDate);
    return dbc;
  }

  //private final static ItemDataDocUrlsViewVector collectMetaData(
  private final ItemDataDocUrlsViewVector collectMetaData (		  
      final ItemDataVector itemDataV,
      final ItemMetaDataVector itemMetaDataV) throws RemoteException {	  
    final ItemDataDocUrlsViewVector items = new ItemDataDocUrlsViewVector();
    final Map msdsMap = new HashMap();
    final Map dedMap = new HashMap();
    final Map specMap = new HashMap();
    final Map msdsStorageTypeCdMap = new HashMap();
    final Map dedStorageTypeCdMap = new HashMap();
    final Map specStorageTypeCdMap = new HashMap();
    
    
    
    for (int i = 0; itemMetaDataV != null && i < itemMetaDataV.size(); i++) {
      final ItemMetaData item = (ItemMetaData) itemMetaDataV.get(i);
      final Integer itemId = new Integer(item.getItemId());
      if ("MSDS".equals(item.getNameValue())) {
        msdsMap.put(itemId, item.getValue());
        
        //ContentData contentMsdsData = new ContentData();
        String msdsUrl = "." + item.getValue();
        ContentData contentMsdsData = getContent(msdsUrl);
    	msdsStorageTypeCdMap.put(itemId, contentMsdsData.getStorageTypeCd());
    	
      } else if ("SPEC".equals(item.getNameValue())) {
        specMap.put(itemId, item.getValue());
        
    	String specUrl = "." + item.getValue();
    	ContentData contentSpecData = getContent(specUrl);
    	specStorageTypeCdMap.put(itemId, contentSpecData.getStorageTypeCd());
    	
      } else if ("DED".equals(item.getNameValue())) {
        dedMap.put(itemId, item.getValue());
        
    	String dedUrl = "." + item.getValue();
    	ContentData contentDedData = getContent(dedUrl);
        dedStorageTypeCdMap.put(itemId, contentDedData.getStorageTypeCd());
        
      }
    }
    for (int i = 0; itemDataV != null && i < itemDataV.size(); i++) {
      final ItemData item = (ItemData) itemDataV.get(i);
      final Integer itemId = new Integer(item.getItemId());
      final ItemDataDocUrlsView itemDocs = ItemDataDocUrlsView
          .createValue();
      itemDocs.setItemId(itemId.intValue());
      itemDocs.setItemName(item.getShortDesc());
      itemDocs.setDed((String) dedMap.get(itemId));
      itemDocs.setMsds((String) msdsMap.get(itemId));
      itemDocs.setSpec((String) specMap.get(itemId));
      
      itemDocs.setDedStorageTypeCd((String) dedStorageTypeCdMap.get(itemId));
      itemDocs.setMsdsStorageTypeCd((String) msdsStorageTypeCdMap.get(itemId));
      itemDocs.setSpecStorageTypeCd((String) specStorageTypeCdMap.get(itemId));
      
      items.add(itemDocs);
    }
    return items;
  }
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
  	throws RemoteException {
		Connection con = null;

		try {
          con = getConnection();
          DBCriteria dbc = new DBCriteria();
          dbc.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, distributorId);
          dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                  RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
          dbc.addEqualTo(ItemMappingDataAccess.ITEM_NUM, distSku);
          dbc.addEqualTo(ItemMappingDataAccess.ITEM_UOM, distUom);
          ItemMappingDataVector distItemMappingDV =
              ItemMappingDataAccess.select(con,dbc);
          return distItemMappingDV;
      } catch (Exception e) {
          logError("getItemsCollectionByDistSkuAndUom: " + e.toString());
      }
      finally {
          closeConnection(con);
      }
      return null;
  }

  /**
    * Gets the list of ItemMappingData values to be used by the request.
    * (Search by DBCriteria).
    * @param DBCriteria dbc
    * @return ItemMappingDataVector
    * @throws            RemoteException Required by EJB 1.0
    */

  public ItemMappingDataVector getItemMappingsCollection(DBCriteria dbc) throws RemoteException {
      Connection con = null;

      try {
        con = getConnection();

        ItemMappingDataVector itemMappingDV =ItemMappingDataAccess.select(con, dbc);
        return itemMappingDV;
      } catch (Exception e) {
        logError("getItemsCollectionByDistSkuAndUom: " + e.toString());
      }
      finally {
        closeConnection(con);
      }
      return null;
  }

  
  public ItemViewVector getStagedItemVector (List pCriteria) throws RemoteException {
      Connection conn = null;
      StagedItemDataVector stagedItems;
      ItemViewVector stagedVV = new ItemViewVector();
      try {
          conn = getConnection();
          
          DBCriteria dbCriteria = new DBCriteria();
          SearchCriteria sc;
          String name;
          Object objValue;
          String strValue;
          int operator;
          for (Iterator it = pCriteria.iterator(); it.hasNext();) {
              sc = (SearchCriteria) it.next();
              name = sc.getName();
              objValue = sc.getObjectValue();
              strValue = (objValue instanceof String) ? ((String) objValue).trim() : "";
              operator = sc.getOperator();

              if (SearchCriteria.PRODUCT_SHORT_DESC.equals(name)) {
                  if (operator == SearchCriteria.BEGINS_WITH_IGNORE_CASE) {
                      dbCriteria.addBeginsWithIgnoreCase(StagedItemDataAccess.SHORT_DESCRIPTION, strValue);
                  } else {
                      dbCriteria.addContainsIgnoreCase(StagedItemDataAccess.SHORT_DESCRIPTION, strValue);
                  }
              } else if (SearchCriteria.CLW_SKU_NUMBER.equals(name)) {
                  if (operator == SearchCriteria.BEGINS_WITH_IGNORE_CASE) {
                      dbCriteria.addBeginsWithIgnoreCase(StagedItemDataAccess.MFG_SKU, strValue);
                  } else {
                      dbCriteria.addContainsIgnoreCase(StagedItemDataAccess.MFG_SKU, strValue);
                  }
              } else if (SearchCriteria.STAGED_SEARCH_TYPE.equals(name)) {
                  if (RefCodeNames.STAGED_ASSETS_SEARCH_TYPE_CD.MATCHED.equals(strValue)) {
                      dbCriteria.addGreaterThan(StagedItemDataAccess.MATCHED_ITEM_ID, 0);
                  } else if (RefCodeNames.STAGED_ASSETS_SEARCH_TYPE_CD.NOT_MATCHED.equals(strValue)) {
                      dbCriteria.addEqualTo(StagedItemDataAccess.MATCHED_ITEM_ID, 0);
                  }
              } else if (SearchCriteria.STORE_ID.equals(name)) {
                  dbCriteria.addEqualTo(StagedItemDataAccess.STORE_ID, objValue);
              }
          } 
          stagedItems = StagedItemDataAccess.select(conn, dbCriteria);
          
          Iterator it = stagedItems.iterator();
          StagedItemData itemD;
          ItemView itemV;
          String actionStr;
          while (it.hasNext()) {
              itemD = (StagedItemData)it.next();
              itemV = ItemView.createValue();

              itemV.setItemId(itemD.getStagedItemId());
              itemV.setName(itemD.getShortDescription());
              itemV.setCategory(itemD.getCategoryName());
              itemV.setManufName(itemD.getManufacturer());
              itemV.setManufSku(itemD.getMfgSku());
              
              actionStr = itemD.getAction();
              if (Utility.isSet(actionStr)) {
                  if (actionStr.equals("A") || actionStr.equals("C")) {
                      itemV.setStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
                  } else {
                      itemV.setStatusCd(RefCodeNames.ITEM_STATUS_CD.INACTIVE);
                  }
              } else {
                  itemV.setStatusCd(RefCodeNames.ITEM_STATUS_CD.INACTIVE);
              }
               
              stagedVV.add(itemV);
          }
      } catch (Exception e) {
          throw processException(e);
      } finally {
         closeConnection(conn);
      }
      return stagedVV;
  }
  
  public StagedItemData getStagedItemData(int stagedItemId) throws RemoteException {
        Connection conn = null;
        StagedItemData stagedItem = null;
        try {
            conn = getConnection();
            stagedItem = getStagedItemData(conn, stagedItemId);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return stagedItem;
    }

    private StagedItemData getStagedItemData(Connection conn, int stagedItemId) throws Exception {
        if (stagedItemId > 0) {
            return StagedItemDataAccess.select(conn, stagedItemId);
        } else {
            return null;
        }
    }
    
    /**
     * Updates the Staged Item information values to be used by the request.
     *
     * @param pItemData the StagedItemData.
     * @param userName   user name
     * @return a <code>StagedAssetData</code> value
     * @throws RemoteException Required by EJB 1.0
     */
    public StagedItemData updateStagedItemData(StagedItemData pItemData, String userName) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            updateStagedItemData(conn, pItemData, userName);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return pItemData;
    }
    
    /**
     * Updates the Staged Item information values to be used by the request.
     *
     * @param conn       Connection
     * @param pItemData the StagedItemData.
     * @param userName   user name
     * @return a <code>StagedItemData</code> value
     * @throws RemoteException Required by EJB 1.0
     */
    private StagedItemData updateStagedItemData(Connection conn, StagedItemData pItemData, String userName) throws RemoteException {

        try {
            StagedItemData data;
            data = pItemData;
            if (data.isDirty()) {
                if (data.getStagedItemId() == 0) {
                    data = StagedItemDataAccess.insert(conn, data);
                } else {
                    StagedItemDataAccess.update(conn, data);
                }
            }
            return data;
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }
    
    public StagedItemAssocDataVector getStagedItemAssocDataVector(int stagedItemId) throws RemoteException {
        Connection conn = null;
        StagedItemAssocDataVector pItemAssocDV = new StagedItemAssocDataVector();
        try {
            conn = getConnection();
            pItemAssocDV = getStagedItemAssocDataVector(conn, stagedItemId);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return pItemAssocDV;
    }
    
    private StagedItemAssocDataVector getStagedItemAssocDataVector(Connection conn, int stagedItemId) throws Exception {
        DBCriteria crit = new DBCriteria();
        
        if (stagedItemId > 0) {
            crit.addEqualTo(StagedItemAssocDataAccess.STAGED_ITEM_ID, stagedItemId);
            crit.addEqualTo(StagedItemAssocDataAccess.ASSOC_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_STORE);
            crit.addEqualTo(StagedItemAssocDataAccess.ASSOC_STATUS_CD, RefCodeNames.ITEM_STATUS_CD.ACTIVE);
        }
        return StagedItemAssocDataAccess.select(conn, crit);
    }
    
    public StagedItemAssocData updateStagedItemAssocData(StagedItemAssocData pItemAssocData, String userName) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            updateStagedItemAssocData(conn, pItemAssocData, userName);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return pItemAssocData;
    }

    private StagedItemAssocData updateStagedItemAssocData(Connection conn, StagedItemAssocData pItemAssocData, String userName) throws Exception {
        if (pItemAssocData != null) {
            if (pItemAssocData.getStagedItemAssocId() == 0
                    && pItemAssocData.getStagedItemId() > 0 && pItemAssocData.getBusEntityId() > 0 ) {
                pItemAssocData.setAddBy(userName);
                pItemAssocData = StagedItemAssocDataAccess.insert(conn, pItemAssocData);
            } else if (pItemAssocData.getStagedItemAssocId() > 0
                    && pItemAssocData.getStagedItemId() > 0 && pItemAssocData.getBusEntityId() > 0) {
                pItemAssocData.setModBy(userName);
                StagedItemAssocDataAccess.update(conn, pItemAssocData);
            }
        }
        return pItemAssocData;
    }

    public void removeStagedItemData(int itemId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            removeStagedItemData(conn, itemId);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    private void removeStagedItemData(Connection conn, int itemId) throws RemoteException {

        try {
                StagedItemDataAccess.remove(conn, itemId);            
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }
    
    public boolean canEditMasterItem(int itemId, int storeId) throws RemoteException {
        Connection conn = null;
        boolean canEditFlag = true;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(ItemMasterAssocDataAccess.CHILD_MASTER_ITEM_ID, itemId);
            
            crit.addJoinTable(ItemMasterAssocDataAccess.CLW_ITEM_MASTER_ASSOC);
            crit.addJoinTable(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE + " CHILD_CATALOG");
            crit.addJoinTable(CatalogDataAccess.CLW_CATALOG + " CHILD_CATALOG_TYPE");
            crit.addJoinTable(CatalogAssocDataAccess.CLW_CATALOG_ASSOC + " CHILD_STORE");
            
            crit.addJoinTable(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE + " PARENT_CATALOG");
            crit.addJoinTable(CatalogDataAccess.CLW_CATALOG + " PARENT_CATALOG_TYPE");
            crit.addJoinTable(CatalogAssocDataAccess.CLW_CATALOG_ASSOC + " PARENT_STORE");
            crit.addJoinTable(PropertyDataAccess.CLW_PROPERTY);

            DBCriteria isolCrit = new DBCriteria();
            isolCrit.addEqualTo(PropertyDataAccess.CLW_PROPERTY + "." + PropertyDataAccess.BUS_ENTITY_ID, storeId);
            isolCrit.addEqualTo(PropertyDataAccess.CLW_PROPERTY + "." + PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.PARENT_STORE_ID);
            isolCrit.addNotEqualTo(PropertyDataAccess.CLW_PROPERTY + "." + PropertyDataAccess.CLW_VALUE, 0);
            isolCrit.addEqualTo(PropertyDataAccess.CLW_PROPERTY + "." + PropertyDataAccess.PROPERTY_STATUS_CD, RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

            isolCrit.addEqualTo("CHILD_CATALOG." + CatalogStructureDataAccess.ITEM_ID, itemId);
            isolCrit.addCondition("CHILD_CATALOG." + CatalogStructureDataAccess.CATALOG_ID + " = " +  "CHILD_CATALOG_TYPE." + CatalogDataAccess.CATALOG_ID);
            isolCrit.addEqualTo("CHILD_CATALOG_TYPE." + CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.STORE);
            isolCrit.addCondition("CHILD_CATALOG_TYPE." + CatalogDataAccess.CATALOG_ID + " = " +  "CHILD_STORE." + CatalogAssocDataAccess.CATALOG_ID);
            isolCrit.addEqualTo("CHILD_STORE." + CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);        
            isolCrit.addEqualTo("CHILD_STORE." + CatalogAssocDataAccess.BUS_ENTITY_ID, storeId);

            DBCriteria parentItemsCrit = new DBCriteria();
            parentItemsCrit.addEqualTo(ItemMasterAssocDataAccess.CHILD_MASTER_ITEM_ID, itemId);
            parentItemsCrit.addEqualTo(ItemMasterAssocDataAccess.ITEM_MASTER_ASSOC_STATUS_CD, RefCodeNames.ITEM_STATUS_CD.ACTIVE);

            String parentItemsSQL = ItemMasterAssocDataAccess.getSqlSelectIdOnly(ItemMasterAssocDataAccess.PARENT_MASTER_ITEM_ID, parentItemsCrit);
            isolCrit.addOneOf("PARENT_CATALOG." + CatalogStructureDataAccess.ITEM_ID, parentItemsSQL);
            
            isolCrit.addCondition("PARENT_CATALOG." + CatalogStructureDataAccess.CATALOG_ID + " = " +  "PARENT_CATALOG_TYPE." + CatalogDataAccess.CATALOG_ID);
            isolCrit.addEqualTo("PARENT_CATALOG_TYPE." + CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.STORE);
            isolCrit.addCondition("PARENT_CATALOG_TYPE." + CatalogDataAccess.CATALOG_ID + " = " +  "PARENT_STORE." + CatalogAssocDataAccess.CATALOG_ID);
            isolCrit.addEqualTo("PARENT_STORE." + CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
            isolCrit.addCondition("PARENT_STORE." + CatalogAssocDataAccess.BUS_ENTITY_ID  + " = " +  PropertyDataAccess.CLW_PROPERTY + "." + PropertyDataAccess.CLW_VALUE);
            
            crit.addIsolatedCriterita(isolCrit);

            ItemMasterAssocDataVector parentMasterItems = ItemMasterAssocDataAccess.select(conn, crit);
            if (!parentMasterItems.isEmpty()) {
                canEditFlag = false;
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return canEditFlag;
    }
    
    public IdVector checkItemStoreUnique(ArrayList manufNames, String ManufSku, String uom, int storeId) throws RemoteException {
        IdVector itemIds = new IdVector();
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.PRODUCT);
            
            crit.addJoinTable(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE + " ITEM_CATALOG");
            crit.addJoinTable(CatalogDataAccess.CLW_CATALOG + " ITEM_CATALOG_TYPE");
            crit.addJoinTable(CatalogAssocDataAccess.CLW_CATALOG_ASSOC + " ITEM_STORE");
            crit.addJoinTable(ItemMappingDataAccess.CLW_ITEM_MAPPING + " ITEM_MAPPING");
            crit.addJoinTable(ItemMetaDataAccess.CLW_ITEM_META + " ITEM_META");
            crit.addJoinTable(BusEntityDataAccess.CLW_BUS_ENTITY + " MANUFACTURER");
            
            DBCriteria isolCrit = new DBCriteria();
            
            isolCrit.addCondition("ITEM_CATALOG." + CatalogStructureDataAccess.ITEM_ID + " = " + ItemDataAccess.CLW_ITEM + "." + ItemDataAccess.ITEM_ID);
            isolCrit.addCondition("ITEM_CATALOG." + CatalogStructureDataAccess.CATALOG_ID + " = " +  "ITEM_CATALOG_TYPE." + CatalogDataAccess.CATALOG_ID);
            isolCrit.addEqualTo("ITEM_CATALOG_TYPE." + CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.STORE);
            isolCrit.addCondition("ITEM_CATALOG_TYPE." + CatalogDataAccess.CATALOG_ID + " = " + "ITEM_STORE." + CatalogAssocDataAccess.CATALOG_ID);
            isolCrit.addEqualTo("ITEM_STORE." + CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
            isolCrit.addEqualTo("ITEM_STORE." + CatalogAssocDataAccess.BUS_ENTITY_ID, storeId);
            
            isolCrit.addCondition("ITEM_MAPPING." + ItemMappingDataAccess.ITEM_ID + " = " + ItemDataAccess.CLW_ITEM + "." + ItemDataAccess.ITEM_ID);
            isolCrit.addEqualTo("ITEM_MAPPING." + ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
            isolCrit.addEqualTo("ITEM_MAPPING." + ItemMappingDataAccess.ITEM_NUM, ManufSku);
            
            isolCrit.addCondition("ITEM_MAPPING." + ItemMappingDataAccess.BUS_ENTITY_ID + " = " +  "MANUFACTURER." + BusEntityDataAccess.BUS_ENTITY_ID);
            isolCrit.addOneOf("MANUFACTURER." + BusEntityDataAccess.SHORT_DESC, manufNames);
            
            isolCrit.addCondition("ITEM_META." + ItemMetaDataAccess.ITEM_ID + " = " + ItemDataAccess.CLW_ITEM + "." + ItemDataAccess.ITEM_ID);
            isolCrit.addEqualTo("ITEM_META." + ItemMetaDataAccess.NAME_VALUE, "UOM");
            isolCrit.addEqualTo("ITEM_META." + ItemMetaDataAccess.CLW_VALUE, uom); 
            
            crit.addIsolatedCriterita(isolCrit);
            ItemDataVector itemDV = ItemDataAccess.select(conn, crit);
            
            ItemData itemD;
            for (int i = 0; i < itemDV.size(); i++) {
                itemD = (ItemData)itemDV.get(i);
                itemIds.add(Integer.valueOf(itemD.getItemId()));
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

        return itemIds;
    }
    
    public HashMap collectDiverseyWebServicesURLs(HashMap itemManufacturerHM, String userLocale, String countryCode, String storeLocale) throws RemoteException {
    	
    	log.info("collectDiverseyWebServicesURLs() method: itemManufacturerHM = " + itemManufacturerHM);
    	
	    HashMap itemUrlHM = new HashMap();
	            
        //String userLocale = appUser.getUser().getPrefLocaleCd().trim();
        
        //split user loacale into language code and country code
        String delimiter = "_";
        String[] localeArray = userLocale.split(delimiter);
        String languageCode = localeArray[0];
        
        //find country code for the Site the user is shopping for
        //String countryCode = appUser.getSite().getSiteAddress().getCountryCd();
        
        log.info("SVC_MSDS=>: collectDiverseyWebServicesURLs()=>languageCode_1 = " + languageCode);
        log.info("SVC_MSDS=>: collectDiverseyWebServicesURLs()=>countryCode_1 = " + countryCode);
    	
        //DiverseyMSDSRetrieve dmr = new DiverseyMSDSRetrieve(); //do not need it, because DiverseyMSDSRetrieve.retrieveMsdsUrl() is a static method
    	
    	Iterator it = itemManufacturerHM.keySet().iterator();           	
    	while(it.hasNext()){
    		Integer key = (Integer) it.next();
    		String manufSku = (String) itemManufacturerHM.get(key);
    		log.info("Manuf. Sku = " + manufSku);
    	    String wsMsdsUrl = null;
    	    try {
    		   
    	       wsMsdsUrl = DiverseyMSDSRetrieve.retrieveMsdsUrl(manufSku, countryCode, languageCode);
    	       log.info("SVC_MSDS=>collectDiverseyWebServicesURLs() method: wsMsdsUrl_1 = " + wsMsdsUrl);
    	       if (wsMsdsUrl.trim().length() == 0 || wsMsdsUrl == null) {
    	    	   //use locale of the Store to set up country code and language code
    	    	   //Sting storeLocale = appUser.getUserStore().getBusEntity().getLocaleCd().trim(); 
    	           localeArray = storeLocale.split(delimiter);
    	           String storeLanguageCode = localeArray[0];
    	           String storeCountryCode = localeArray[1];
    	           log.info("SVC_MSDS=>: =>storeLanguageCode = " + storeLanguageCode);
    	           log.info("SVC_MSDS=>: =>storeCountryCode = " + storeCountryCode);
        	       wsMsdsUrl = DiverseyMSDSRetrieve.retrieveMsdsUrl(manufSku, storeCountryCode, storeLanguageCode);
           	       log.info("SVC_MSDS=>collectDiverseyWebServicesURLs() method: wsMsdsUrl_2 = " + wsMsdsUrl);
    	           if(wsMsdsUrl == null || wsMsdsUrl.trim().length() == 0){
           	          log.info("JD Web Services URL of the product item was not found.");
    	           }
    	       }  
    	       if (wsMsdsUrl.trim().length() > 0 && wsMsdsUrl != null) { //add only if MSDS URL is AVAILABLE via JD Web Services
    	          itemUrlHM.put(key, wsMsdsUrl);
    	       }
    	    } catch (ServiceException exc) {
    	        logError("exc.getMessage");
    	        exc.printStackTrace();
    	        throw new RemoteException(
    	            "Error. ItemInformationBean; DiverseyMSDSRetrieve.retrieveMsdsUrl: ServiceException happened. "
    	                + exc.getMessage());
    	    } catch (SOAPException exc) {
    	          logError("exc.getMessage");
    	          exc.printStackTrace();
    	          throw new RemoteException(
    	              "Error. ItemInformationBean; DiverseyMSDSRetrieve.retrieveMsdsUrl: SOAPException happened. "
    	                  + exc.getMessage());
    	    } catch (MalformedURLException exc) {
    	          logError("exc.getMessage");
    	          exc.printStackTrace();
    	          throw new RemoteException(
    	              "Error. ItemInformationBean; DiverseyMSDSRetrieve.retrieveMsdsUrl: MalformedURLException happened. "
    	                  + exc.getMessage());
    	    } catch (IOException exc) {
    	          logError("exc.getMessage");
    	          exc.printStackTrace();
    	          throw new RemoteException(
    	              "Error. ItemInformationBean; DiverseyMSDSRetrieve.retrieveMsdsUrl: IOException happened. "
    	                  + exc.getMessage());
    	    } catch (Exception exc) {
    	        logError("exc.getMessage");
    	        exc.printStackTrace();
    	        throw new RemoteException(
    	            "Error. ItemInformationBean; DiverseyMSDSRetrieve.retrieveMsdsUrl: Exception happened. "
    	                + exc.getMessage());
    	    }

    	} // while

    	return itemUrlHM;
    }
    
    public ItemDataDocUrlsViewVector addDiverseyWebServicesURLs(ItemDataDocUrlsViewVector itemsAll, HashMap itemDiverseyUrl, HashMap itemShortDescHM) throws RemoteException {
    	
        //loop through HashMap itemDiverseyUrl
    	Iterator it_idu = itemDiverseyUrl.keySet().iterator();
    	while (it_idu.hasNext()) {
            Integer key = (Integer) it_idu.next(); //key = item id
            ItemDataDocUrlsView itemWsMsds = ItemDataDocUrlsView
                .createValue();
            itemWsMsds.setItemId(key.intValue());
            itemWsMsds.setItemName((String) itemShortDescHM.get(key));
            itemWsMsds.setDed(null);
            itemWsMsds.setMsds((String) itemDiverseyUrl.get(key));
            itemWsMsds.setSpec(null);
            itemWsMsds.setMsdsViaJDWebService((String) itemDiverseyUrl.get(key));
            itemsAll.add(itemWsMsds);
        }
    	
    	return itemsAll;
    	
    }
    
    public ContentData getContent(String pPath)
	throws RemoteException
    {
	ContentData cdata = null;
	log.info ("start getContent: pPath=" + pPath);
	Connection con = null;
	try {
	    con = getConnection();
	    DBCriteria dbc = new DBCriteria();
	    dbc.addEqualToIgnoreCase(ContentDataAccess.PATH, pPath);

	    ContentDataVector cdv =
	    ContentDataAccess.select(con,dbc);
	    if ( null != cdv && cdv.size() > 0 ) {
		   log.info ("found content pPath=" + pPath );
		   cdata = (ContentData) cdv.get(0);
		}
	}
	catch (Exception e) {
		    e.printStackTrace();
		    throw new RemoteException("getContent error, " +
					      e.getMessage() );
	}
	finally {
		    closeConnection(con);
	}

	return cdata;
	
    }

}
