package com.cleanwise.service.api.session;

/**
 * Title:        ProductInformationBean
 * Description:  Bean implementation for ProductInformation Stateless Session Bean
 * Purpose:      Provides access to the methods for retrieving and evaluating product information
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.*;


import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;

import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.ItemData;

public class ProductInformationBean extends BusEntityServicesAPI
{
    public final String PRODUCT_CATEGORY="PRODUCT_CATEGORY";
    private final static int inClauseSize=500;
    private final static int maxInClauseSize=999;
    public final static String CATEGORY_ANCESTOR_CATEGORY="CATEGORY_ANCESTOR_CATEGORY";
    /**
     *
     */
    public ProductInformationBean(){
    }

    /**
     *
     */
    public void ejbCreate() throws CreateException, RemoteException {}

    /**
     * Gets the array-like product vector values to be used by the request.
     * (search by product catalog id).
     * @param pCatalogId  the catalog identifier
     * @return ProductDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ProductDataVector getProductsCollectionByCatalog(int pCatalogId)
	throws RemoteException
    {
	ItemDataVector itemDV=null;
	Connection con = null;
	try {
	    con=getConnection();
	    DBCriteria dbC=new DBCriteria();
	    //pick up catalog structure, since we have no possibie
	    dbC.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,pCatalogId);
	    dbC.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
	    CatalogStructureDataVector catalogStructureDV;
	    catalogStructureDV =CatalogStructureDataAccess.select(con, dbC);
	    //prepare product id collection
	    Vector productsProducts=new Vector();
	    Vector products = null;
	    int size =catalogStructureDV.size();
	    for (int ii=0; ii<size; ii++) {
		if(ii%inClauseSize==0) {
		    products=new Vector();
		    productsProducts.add(products);
		}
		CatalogStructureData csd = (CatalogStructureData)catalogStructureDV.get(ii);
		products.add((new Integer(csd.getItemId())));
	    }
	    //pick up items
	    itemDV=new ItemDataVector();
	    for(int ii=0; ii<productsProducts.size(); ii++) {
		DBCriteria dbcItem=new DBCriteria();
		dbcItem.addOneOf(ItemDataAccess.ITEM_ID, (List) productsProducts.get(ii));
		dbcItem.addOrderBy(ItemDataAccess.ITEM_ID);
		itemDV.addAll(itemDV.size(),ItemDataAccess.select(con,dbcItem));
	    }
	}catch(NamingException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	}catch(SQLException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	}finally{
	    closeConnection(con);
	}

	//pick up products with attributes
	ProductDataVector productDV = getProducts(itemDV);
	return productDV;
    }

     /**
     * Gets a list of BusEntityData objects based off the supplied search criteria
     * object
     *
     * @param pCrit  Description of the Parameter
     * @param typeCd type cd
     * @return bus_entity collection
     * @throws RemoteException if an error occurs
     */
    public BusEntityDataVector getBusEntityByCriteria(BusEntitySearchCriteria pCrit, String typeCd) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return BusEntityDAO.getBusEntityByCriteria(conn, pCrit, typeCd);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

    }
    /**
     * Gets the array-like product vector values to be used by the request
     * (search by product Id).
     * @param pProductId  the product identifier
     * @return ProductDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ProductDataVector getProductsCollectionByProductId(int pProductId)
	throws RemoteException
    {
	ItemDataVector itemDV=null;
	Connection con=null;
	try {

	    con=getConnection();

	    DBCriteria dbcItem=new DBCriteria();
	    dbcItem.addEqualTo(ItemDataAccess.ITEM_ID,pProductId);
	    dbcItem.addOrderBy(ItemDataAccess.ITEM_ID);
	    //pick up items
	    itemDV=ItemDataAccess.select(con,dbcItem);
	}catch(NamingException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getProductsCollectionByProductId() Naming Exception happened");
	}catch(SQLException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getProductsCollectionByProductId() SQL Exception happened");
	}finally{
	    closeConnection(con);
	}
	//pick up products with attributes
	logDebug( "1  get product info for items: " + itemDV);
	ProductDataVector productDV = getProducts(itemDV);
	logDebug( "2  got product info for items: " + itemDV);
	return productDV;
    }

    /**
     * Gets the array-like product vector values to be used by the request.
     * (Search by product UPC).
     * @param pProductUPC  the product UPC value
     * @return ProductDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ProductDataVector getProductsCollectionByUPC(String pProductUPC)
	throws RemoteException
    {
	ProductDataVector productDV = getProductsCollectionByMeta(ProductData.UPC_NUM,pProductUPC);
	return productDV;
    }

    /**
     * Gets the array-like product vector values to be used by the request.
     * (Search by category).
     * @param pCategoryId  the category identifier
     * @return ProductDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ProductDataVector getProductsCollectionByCategory(int pCategoryId)
	throws RemoteException
    {
	ItemDataVector itemDV=null;
	ItemAssocDataVector itemAssocDV=null;
	Connection con=null;
	try {
	    con=getConnection();
	    DBCriteria dbcItemAssoc=new DBCriteria();
	    dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM2_ID,pCategoryId);
	    dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,PRODUCT_CATEGORY);
	    itemAssocDV=ItemAssocDataAccess.select(con,dbcItemAssoc);

	    //prepare product id collection
	    Vector productsProducts = new Vector();
	    Vector products = null;
	    int size =itemAssocDV.size();
	    for (int ii=0; ii<size; ii++) {
		if(ii%inClauseSize==0) {
		    products = new Vector();
		    productsProducts.add(products);
		}
		ItemAssocData iaD = (ItemAssocData) itemAssocDV.get(ii);
		products.add((new Integer(iaD.getItem1Id())).toString());
	    }
	    itemDV=new ItemDataVector();
	    for(int ii=0; ii<productsProducts.size();ii++) {
		DBCriteria dbcItem=new DBCriteria();
		dbcItem.addEqualTo(ItemDataAccess.ITEM_ID,productsProducts.get(ii));
		dbcItem.addOrderBy(ItemDataAccess.ITEM_ID);
		//pick up items
		itemDV.addAll(ItemDataAccess.select(con,dbcItem));
	    }
	}catch(NamingException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getProductsCollectionByCategory() Naming Exception happened");
	}catch(SQLException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getProductsCollectionByCategory() SQL Exception happened");
	}finally{
	    closeConnection(con);
	}
	//pick up products with attributes
	ProductDataVector productDV = getProducts(itemDV);
	return productDV;
    }

    /**
     * Gets the array-like product vector values to be used by the request.
     * (Search by keyword).
     * @param pKeyword  the keyword
     * @return ProductDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ProductDataVector getProductsCollectionByKeyword(String pKeyword)
	throws RemoteException
    {
	ItemDataVector itemDV=null;
	ItemKeywordDataVector itemKeywordDV=null;
	Connection con=null;
	try {
	    con=getConnection();
	    DBCriteria dbcItemKeyword=new DBCriteria();
	    dbcItemKeyword.addEqualTo(ItemKeywordDataAccess.KEYWORD,pKeyword);
	    dbcItemKeyword.addOrderBy(ItemKeywordDataAccess.ITEM_ID);
	    itemKeywordDV=ItemKeywordDataAccess.select(con,dbcItemKeyword);

	    //prepare product id collection
	    Vector productsProducts = new Vector();
	    Vector products = null;
	    int size =itemKeywordDV.size();
	    for (int ii=0; ii<size; ii++) {
		if(ii%inClauseSize==0) {
		    products=new Vector();
		    productsProducts.add(products);
		}
		ItemKeywordData ikD = (ItemKeywordData) itemKeywordDV.get(ii);
		products.add((new Integer(ikD.getItemId())).toString());
	    }
	    itemDV=new ItemDataVector();
	    for(int ii=0; ii<productsProducts.size(); ii++) {
		DBCriteria dbcItem=new DBCriteria();
		dbcItem.addEqualTo(ItemDataAccess.ITEM_ID,productsProducts.get(ii));
		dbcItem.addOrderBy(ItemDataAccess.ITEM_ID);
		//pick up items
		itemDV.addAll(ItemDataAccess.select(con,dbcItem));
	    }
	}catch(NamingException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getProductsCollectionByKeyword() Naming Exception happened");
	}catch(SQLException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getProductsCollectionByKeyword() SQL Exception happened");
	}finally{
	    closeConnection(con);
	}
	//pick up products with attributes
	ProductDataVector productDV = getProducts(itemDV);
	return productDV;
    }

    /**
     * Gets the array-like product vector values to be used by the request.
     * (Search by product meta).
     * @param pNameId the product meta name identifier
     * @param pValueId the product meta value identifier
     * @return ProductDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ProductDataVector getProductsCollectionByMeta(String pName, String pValue)
	throws RemoteException
    {
	ItemDataVector itemDV=null;
	ItemMetaDataVector itemMetaDV=null;
	Connection con=null;
	try {
	    con=getConnection();
	    DBCriteria dbItemMetaC=new DBCriteria();
	    dbItemMetaC.addEqualTo(ItemMetaDataAccess.NAME_VALUE,pName);
	    dbItemMetaC.addEqualTo(ItemMetaDataAccess.CLW_VALUE,pValue);
	    dbItemMetaC.addOrderBy(ItemMetaDataAccess.ITEM_ID+","+ItemMetaDataAccess.ITEM_META_ID);
	    itemMetaDV=ItemMetaDataAccess.select(con,dbItemMetaC);

	    //prepare product id collection
	    Vector productsProducts = new Vector();
	    Vector products = null;
	    int size =itemMetaDV.size();
	    for (int ii=0; ii<size; ii++) {
		if(ii%inClauseSize==0) {
		    products=new Vector();
		    productsProducts.add(products);
		}
		ItemMetaData imD = (ItemMetaData) itemMetaDV.get(ii);
		products.add((new Integer(imD.getItemId())).toString());
	    }
	    itemDV=new ItemDataVector();
	    for(int ii=0; ii<productsProducts.size(); ii++) {
		DBCriteria dbItemC=new DBCriteria();
		dbItemC.addEqualTo(ItemDataAccess.ITEM_ID,productsProducts.get(ii));
		dbItemC.addOrderBy(ItemDataAccess.ITEM_ID);
		//pick up items
		itemDV.addAll(ItemDataAccess.select(con,dbItemC));
	    }
	}catch(NamingException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getProductsCollectionByMeta() Naming Exception happened");
	}catch(SQLException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getProductsCollectionByMeta() SQL Exception happened");
	}finally{
	    closeConnection(con);
	}
	//pick up products with attributes
	ProductDataVector productDV = getProducts(itemDV);
	return productDV;
    }

    /**
     * Gets the array-like product vector values to be used by the request.
     * (Search by business entity identifier, e.g.manufacturer, distributor).
     * @param pBusEntityId  the business entity identifier
     * @return ProductDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    private static Vector _pProductsCollectionByBusEntity = new Vector();
    private static Vector _rProductsCollectionByBusEntity = new Vector();
    public ProductDataVector getProductsCollectionByBusEntity(int pBusEntityId)
	throws RemoteException
    {
	//Check for cached data
	for(int ii=0; ii<_pProductsCollectionByBusEntity.size();ii++) {
	    if(((Integer)_pProductsCollectionByBusEntity.get(ii)).intValue()==pBusEntityId){
		return (ProductDataVector) _rProductsCollectionByBusEntity.get(ii);
	    }
	}
	//
	ItemDataVector itemDV=null;
	ItemMappingDataVector itemMappingDV=null;
	Connection con=null;
	try {
	    con=getConnection();
	    DBCriteria dbcItemMapping=new DBCriteria();
	    dbcItemMapping.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID,pBusEntityId);
	    dbcItemMapping.addOrderBy(ItemMappingDataAccess.ITEM_ID);
	    itemMappingDV=ItemMappingDataAccess.select(con,dbcItemMapping);

	    //prepare product id collection
	    Vector productsProducts = new Vector();
	    Vector products=null;
	    int size =itemMappingDV.size();
	    for (int ii=0; ii<size; ii++) {
		if((ii%inClauseSize)==0) {
		    products = new Vector();
		    productsProducts.add(products);
		}
		ItemMappingData imD = (ItemMappingData) itemMappingDV.get(ii);
		products.add(new Integer(imD.getItemId()));
	    }
	    itemDV=new ItemDataVector();
	    if(productsProducts.size()>0) {
		for(int ii=0; ii<productsProducts.size(); ii++) {
		    DBCriteria dbcItem=new DBCriteria();
		    dbcItem.addEqualTo(ItemDataAccess.ITEM_ID,(Collection)productsProducts.get(ii));
		    dbcItem.addOrderBy(ItemDataAccess.ITEM_ID);
		    //pick up items
		    itemDV.addAll(ItemDataAccess.select(con,dbcItem));
		}
	    }
	}catch(NamingException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getProductsCollectionByBusEntity() Naming Exception happened");
	}catch(SQLException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getProductsCollectionByBusEntity() SQL Exception happened");
	}finally{
	    closeConnection(con);
	}
	//pick up products with attributes
	ProductDataVector productDV = getProducts(itemDV);
	_pProductsCollectionByBusEntity.add(new Integer(pBusEntityId));
	_rProductsCollectionByBusEntity.add(productDV);
	return productDV;
    }

    /**
     * Gets product information values to be used by the request.
     * @param pProductId  the product identifier
     * @return ProductData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ProductData getProduct(int pProductId)
	throws RemoteException
    {
	ProductDataVector productDV;
	ProductData productD=null;
	try {
	    productDV=getProductsCollectionByProductId(pProductId);

	    if(productDV.size()>0) {
		productD=(ProductData) productDV.get(0);
	    }
	}
	catch ( Exception e ) {
	    e.printStackTrace();
	}
	return productD;
    }

    /**
     * Gets the Product content vector values to be used by the request.
     * @param pProductId  the product identifier
     * @return ProductContentDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public ContentDataVector getProductContentCollection(int pProductId)
	throws RemoteException
    {
	ContentDataVector contentDV=null;
	Connection con=null;
	try {
	    con=getConnection();
	    DBCriteria dbcContent=new DBCriteria();
	    dbcContent.addEqualTo(ContentDataAccess.ITEM_ID,pProductId);
	    contentDV=ContentDataAccess.select(con,dbcContent);
	}catch(NamingException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getProductContentCollection() Naming Exception happened");
	}catch(SQLException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getProductContentCollection() SQL Exception happened");
	}finally{
	    closeConnection(con);
	}
	return contentDV;
    }

    /**
     * Gets product sku information values to be used by the request.
     * @param pProductId  the product identifier
     * @return ProductContentData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ProductSKUData getProductSKU(int pProductId)
	throws RemoteException
    {
	return new ProductSKUData();
    }

    /**
     * Gets product price information values to be used by the request.
     * @param pProductId  the product identifier
     * @param pPriceId  the price identifier
     * @return ProductContentData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ProductPriceData getProductPrice(int pProductId, int pPriceId)
	throws RemoteException
    {
	return new ProductPriceData();
    }

    /**
     * Gets product information values to be used by the request.
     * @param pProductId  the product identifier
     * @return ProductContentData
     * @throws            RemoteException Required by EJB 1.0
     */
    public ProductData getProductInfo(int pProductId)
	throws RemoteException
    {
	return getProduct(pProductId);
    }


    /*============================================================================*/
    //Set product attributes
    private ProductDataVector getProducts(ItemDataVector itemDV) throws RemoteException {
	ProductDataVector productDV = new ProductDataVector();
	Connection con=null;
	try {
	    con=getConnection();
	    //prepare product id collection
	    //Create Product Data Vector and prepare conditions to pick up meta data
	    int size=itemDV.size();
	    Vector itemItems = new Vector();
	    IdVector itemIds= new IdVector();
	    ItemData itemD;
	    for(int ii=0; ii<size; ii++) {
		itemD=(ItemData)itemDV.get(ii);
		Integer itemId=new Integer(itemD.getItemId());
		itemIds.add(itemId);
	    }
	    ProductDAO pdao = new ProductDAO(con, itemIds);
	    productDV = pdao.getResultVector();

	}
	catch(Exception exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getProducts() Naming Exception happened");
	}
	finally{
	    closeConnection(con);
	}

	return productDV;
    }


    /**
     * Gets category information values to be used by the request.
     * @param pCategoryId  the category identifier
     * @return CatalogCategoryData
     * @throws            RemoteException Required by EJB 1.0
     */
    public CatalogCategoryData getCatalogCategory(int pCategoryId)
	throws RemoteException, DataNotFoundException
    {
	CatalogCategoryData catalogCategoryD=null;
	Connection con=null;
	try {
	    con=getConnection();
	    ItemData itemD=ItemDataAccess.select(con,pCategoryId);
	    if(!itemD.getItemTypeCd().equalsIgnoreCase(RefCodeNames.ITEM_TYPE_CD.CATEGORY)) {
		throw new DataNotFoundException("Category id: " + pCategoryId);
	    }
	    catalogCategoryD = new CatalogCategoryData();
	    catalogCategoryD.setItemData(itemD);
	}catch(NamingException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getCatalogCategory() Naming Exception happened");
	}catch(SQLException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getCatalogCategory() SQL Exception happened");
	}finally{
	    closeConnection(con);
	}
	return catalogCategoryD;
    }
    /**
     * Gets sub categoris.
     * @param pCategoryId  the category identifier
     * @return CatalogCategoryDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public CatalogCategoryDataVector getSubCategories(int pCategoryId)
	throws RemoteException
    {
	CatalogCategoryDataVector catalogCategoryDV=new CatalogCategoryDataVector() ;
	Connection con=null;
	try {
	    con=getConnection();
	    DBCriteria dbc = new DBCriteria();
	    dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID,pCategoryId);
	    dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);
	    ItemAssocDataVector itemAssocDV=ItemAssocDataAccess.select(con,dbc);
	    Vector item1Ids = new Vector();
	    int ii=0;
	    for(; ii<itemAssocDV.size(); ii++) {
		ItemAssocData itemAssocD = (ItemAssocData) itemAssocDV.get(ii);
		item1Ids.add(new Integer(itemAssocD.getItem1Id()));
	    }
	    if(ii==0) return catalogCategoryDV;
	    dbc = new DBCriteria();
	    dbc.addOneOf(ItemDataAccess.ITEM_ID,item1Ids);
	    ItemDataVector itemDV = ItemDataAccess.select(con,dbc);
	    for(ii=0; ii<itemDV.size(); ii++) {
		ItemData itemD = (ItemData) itemDV.get(ii);
		CatalogCategoryData catalogCategoryD = new CatalogCategoryData();
		catalogCategoryD.setItemData(itemD);
		catalogCategoryDV.add(catalogCategoryD);
	    }
	}catch(NamingException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getSubCategories() Naming Exception happened");
	}catch(SQLException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getSubCategories() SQL Exception happened");
	}finally{
	    closeConnection(con);
	}
	return catalogCategoryDV;
    }
    /**
     * Gets top level catalog category.
     * @param pCatalogId  the catalog identifier
     * @return CatalogCategoryData
     * @throws            RemoteException Required by EJB 1.0
     */
    public CatalogCategoryData getTopProductCategory(int pCatalogId)
	throws RemoteException, DataNotFoundException
    {
	CatalogCategoryData catalogCategoryD=null ;
	Connection con=null;
	try {
	    con=getConnection();
	    //Pick up store Id
	    DBCriteria dbc = new DBCriteria();
	    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID,pCatalogId);
	    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
	    CatalogAssocDataVector catalogAssocDV = CatalogAssocDataAccess.select(con,dbc);
	    if(catalogAssocDV.size()>1) {
		throw new RemoteException("Error. ProductInformationBean.getTopProductCategory(). More than one store for catalog. Catalog Id: "+pCatalogId);
	    }
	    if(catalogAssocDV.size()==0) {
		throw new RemoteException("Error. ProductInformationBean.getTopProductCategory(). No store for catalog. Catalog Id: "+pCatalogId);
	    }
	    CatalogAssocData catalogAssocD = (CatalogAssocData) catalogAssocDV.get(0);
	    //Pick up top product category
	    dbc = new DBCriteria();
	    dbc.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID,catalogAssocD.getBusEntityId());
	    //      dbc.addEqualTo(ItemMappingDataAccess.ITEM_NUM,RefCodeNames.CATEGORY_TYPE_CD.PRODUCT_CATALOG);
	    ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(con, dbc);
	    if(itemMappingDV.size()>1) {
		throw new RemoteException("Error. ProductInformationBean.getTopProductCategory(). More than one top level product category for store. Store Id: "+catalogAssocD.getBusEntityId());
	    }
	    if(itemMappingDV.size()==0) {
		throw new RemoteException("Error. ProductInformationBean.getTopProductCategory(). No top level product category for store. Catalog Id: "+catalogAssocD.getBusEntityId());
	    }
	    ItemMappingData itemMappingD =(ItemMappingData) itemMappingDV.get(0);
	    int topProductCategoryId = itemMappingD.getItemId();

	    //Pick up catalog product ids
	    dbc = new DBCriteria();
	    dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,pCatalogId);
	    dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
	    IdVector itemIds = CatalogStructureDataAccess.selectIdOnly(con,CatalogStructureDataAccess.ITEM_ID, dbc);

	    //Pick low level category for top category
	    dbc = new DBCriteria();
	    dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID,topProductCategoryId);
	    dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,RefCodeNames.ITEM_ASSOC_CD.CATEGORY_ANCESTOR_CATEGORY);
	    dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);
	    IdVector lowCategoryIds = ItemAssocDataAccess.selectIdOnly(con,ItemAssocDataAccess.ITEM1_ID,dbc);

	    //Select low level categories for catalog products
	    Vector categoryIdVectors = new Vector();
	    int size = itemIds.size();
	    for(int ii=0; ii<size; ii+=inClauseSize) {
		int endIndex = ii+inClauseSize;
		if(endIndex > size) endIndex=size;
		dbc = new DBCriteria();
		dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID,itemIds.subList(ii,endIndex));
		dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
		dbc.addOrderBy(ItemAssocDataAccess.ITEM2_ID);
		categoryIdVectors.add(ItemAssocDataAccess.selectIdOnly(con,ItemAssocDataAccess.ITEM2_ID,dbc));
	    }

	    for(int ii=0; ii<categoryIdVectors.size(); ii++) {
		IdVector idV =(IdVector)categoryIdVectors.get(ii);
	    }
	    //Unite the result
	    size = lowCategoryIds.size();
	    int vectorNum=categoryIdVectors.size();
	    IdVector[] vectPointers = new IdVector[vectorNum];
	    for(int ii=0; ii<vectorNum; ii++) {
		vectPointers[ii]=(IdVector) categoryIdVectors.get(ii);
	    }
	    int[] vectPos = new int[vectorNum];
	    for(int ii=0; ii<vectorNum; ii++) vectPos[ii]=0;
	    int[] vectSize = new int[vectorNum];
	    for(int ii=0; ii<vectorNum; ii++) {
		IdVector idV =(IdVector)categoryIdVectors.get(ii);
		vectSize[ii]=idV.size();
	    }
	    int rrrr=0;
	    IdVector resIdV = new IdVector();
	    boolean resume = true;
	    int compare=0;
	    for(int ii=0; ii<size; ii++) {
		Integer catId =(Integer)lowCategoryIds.get(ii);
		mmm:
		for(int jj=0; jj<vectorNum; jj++) {
		    rrrr=0;
		    while(vectPos[jj]<vectSize[jj]) {
			if(rrrr++>100000) {
			    throw new RemoteException("My errror");
			}
			Integer iInteger=(Integer) 
			    vectPointers[jj].get(vectPos[jj]);
			compare = catId.compareTo(iInteger);
			if(compare==0) {
			    vectPos[jj]++;
			    resIdV.add(catId);
			    break mmm;
			}
			if(compare>0) {
			    vectPos[jj]++;
			    continue;
			}
			if(compare<0) {
			    break;
			}
		    }
		}
	    }

	    //Pickup the rest of category tree
	    if(resIdV.size()>maxInClauseSize) {
		throw new RemoteException("Error. ProductInformationBean.getTopProductCategory(). Program limits exceeded. Found too many categories: "+resIdV.size()+" for catalog: "+pCatalogId);
	    }
	    dbc = new DBCriteria();
	    dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID,resIdV);
	    dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,RefCodeNames.ITEM_ASSOC_CD.CATEGORY_ANCESTOR_CATEGORY);
	    dbc.addOrderBy(ItemAssocDataAccess.ITEM2_ID);
	    IdVector availableCategories = ItemAssocDataAccess.selectIdOnly(con,ItemAssocDataAccess.ITEM2_ID,dbc);


	    //Prepare return
	    catalogCategoryD = getCatalogCategory(topProductCategoryId);
	}catch(NamingException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getTopProductCategory() Naming Exception happened");
	}catch(SQLException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. ProductInformationBean.getTopProductCategory() SQL Exception happened");
	}finally{
	    closeConnection(con);
	}
	return catalogCategoryD;
    }
    
    
    /**
     * Gets the array-like item id vector values to be used by the request.
     * (Search by managed item parent).
     * @param pParentItemId  the managed item parent identifier
     * @return IdVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public IdVector getItemIdCollectionByManagedParentItem(int pParentItemId)
        throws RemoteException {
        IdVector itemIdV =null;
        Connection con=null;    
        try {   
            con=getConnection();
            DBCriteria dbcItemAssoc=new DBCriteria();
            dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM2_ID,pParentItemId);
            dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT);
            itemIdV = ItemAssocDataAccess.selectIdOnly(con,ItemAssocDataAccess.ITEM1_ID, dbcItemAssoc);

    	}catch(NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
    	    throw new RemoteException("Error. ProductInformationBean.getItemIdCollectionByManagedParentItem() Naming Exception happened");
        }catch(SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. ProductInformationBean.getItemIdCollectionByManagedParentItem() SQL Exception happened");    
        }finally{
            closeConnection(con);
        }

        return itemIdV;
    }

    /**
     * Gets the array-like item id vector values to be used by the request.
     * (Search by managed item child).
     * @param pChildItemId  the managed item child identifier
     * @return IdVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public IdVector getItemIdCollectionByManagedChildItem(int pChildItemId)
        throws RemoteException {
        IdVector itemIdV =null;
        Connection con=null;    
        try {   
            con=getConnection();
            DBCriteria dbcItemAssoc=new DBCriteria();
            dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM1_ID,pChildItemId);
            dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT);
            itemIdV = ItemAssocDataAccess.selectIdOnly(con,ItemAssocDataAccess.ITEM2_ID, dbcItemAssoc);

    	}catch(NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
    	    throw new RemoteException("Error. ProductInformationBean.getItemIdCollectionByManagedChildItem() Naming Exception happened");
        }catch(SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. ProductInformationBean.getItemIdCollectionByManagedChildItem() SQL Exception happened");    
        }finally{
            closeConnection(con);
        }

        return itemIdV;
    }

    public IdVector getItemIdCollectionByManagedItemBetweenStores(int itemId)
	    throws RemoteException {
	    IdVector itemIdV = null;
	    Connection con = null;    
	    try {   
	        con = getConnection();
	        DBCriteria dbcItemAssoc = new DBCriteria();
	        dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, itemId);
	        dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.CROSS_STORE_ITEM_LINK);
	        itemIdV = ItemAssocDataAccess.selectIdOnly(con, ItemAssocDataAccess.ITEM2_ID, dbcItemAssoc);
		}
	    catch (NamingException exc) {
	        logError("exc.getMessage");
	        exc.printStackTrace();
		    throw new RemoteException("Error. ProductInformationBean.getItemIdCollectionByManagedItemBetweenStores() Naming Exception happened");
	    }
	    catch (SQLException exc) {
	        logError("exc.getMessage");
	        exc.printStackTrace();
	        throw new RemoteException("Error. ProductInformationBean.getItemIdCollectionByManagedItemBetweenStores() SQL Exception happened");    
	    }
	    finally {
	        closeConnection(con);
	    }
	    return itemIdV;
	}

    /**
     * remove item assoc for linked items of managed item.
     * (Search by managed item parent).
     * @param pParentItemId  the managed item parent identifier
     * @throws            RemoteException Required by EJB 1.0
     */
    public void removeManagedItemLinksByManagedParentItem(int pParentItemId)
        throws RemoteException {
        Connection con=null;    
        try {   
            con=getConnection();
            DBCriteria dbcItemAssoc=new DBCriteria();
            dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM2_ID,pParentItemId);
            dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT);
            ItemAssocDataAccess.remove(con, dbcItemAssoc);

    	}catch(NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
    	    throw new RemoteException("Error. ProductInformationBean.removeManagedItemLinksByManagedParentItem() Naming Exception happened");
        }catch(SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. ProductInformationBean.removeManagedItemLinksByManagedParentItem() SQL Exception happened");    
        }finally{
            closeConnection(con);
        }

    }

    /**
     * remove item assoc for linked items of managed item.
     * (Search by managed item child).
     * @param pParentItemId  the managed item child identifier
     * @throws            RemoteException Required by EJB 1.0
     */
    public void removeManagedItemLinksByManagedChildItem(int pChildItemId)
        throws RemoteException {
        Connection con=null;    
        try {   
            con=getConnection();
            DBCriteria dbcItemAssoc=new DBCriteria();
            dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM1_ID,pChildItemId);
            dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT);
            ItemAssocDataAccess.remove(con, dbcItemAssoc);

    	}catch(NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
    	    throw new RemoteException("Error. ProductInformationBean.removeManagedItemLinksByManagedChildItem() Naming Exception happened");
        }catch(SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. ProductInformationBean.removeManagedItemLinksByManagedChildItem() SQL Exception happened");    
        }finally{
            closeConnection(con);
        }

    }

    public void removeItemsLinkBetweenStores(int itemId, int linkedItemId)
		throws RemoteException {
    	Connection con = null;
    	try {   
	        con = getConnection();
	        DBCriteria dbcItemAssoc = new DBCriteria();
	        dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, itemId);
	        dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, linkedItemId);
	        dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.CROSS_STORE_ITEM_LINK);
	        ItemAssocDataAccess.remove(con, dbcItemAssoc);
		}
    	catch (NamingException exc) {
	        logError("exc.getMessage");
	        exc.printStackTrace();
		    throw new RemoteException("Error. ProductInformationBean.removeItemsLinkBetweenStores() Naming Exception happened");
	    }
		catch (SQLException exc) {
	        logError("exc.getMessage");
	        exc.printStackTrace();
	        throw new RemoteException("Error. ProductInformationBean.removeItemsLinkBetweenStores() SQL Exception happened");    
	    }
	    finally {
	        closeConnection(con);
	    }
    }

    public void removeItemsLinkBetweenStores(int itemId)
		throws RemoteException {
		Connection con = null;
		try {   
	        con = getConnection();
	        DBCriteria dbcItemAssoc = new DBCriteria();
	        dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, itemId);
	        dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.CROSS_STORE_ITEM_LINK);
	        ItemAssocDataAccess.remove(con, dbcItemAssoc);
		}
		catch (NamingException exc) {
	        logError("exc.getMessage");
	        exc.printStackTrace();
		    throw new RemoteException("Error. ProductInformationBean.removeItemsLinkBetweenStores() Naming Exception happened");
	    }
		catch (SQLException exc) {
	        logError("exc.getMessage");
	        exc.printStackTrace();
	        throw new RemoteException("Error. ProductInformationBean.removeItemsLinkBetweenStores() SQL Exception happened");    
	    }
	    finally {
	        closeConnection(con);
	    }
	}

    /**
     * add item assoc for linked item of managed item.
     * (Search by managed item parent).
     * @param pParentItemId  the managed item parent identifier
     * @throws            RemoteException Required by EJB 1.0
     */
    public void addManagedItemLinkByManagedParentItem(int pItemId, int pParentItemId, String user)
        throws RemoteException {
        Connection con=null;    
        try {   
            con=getConnection();

            ItemAssocData assocD = ItemAssocData.createValue();
            assocD.setItem1Id(pItemId);
            assocD.setItem2Id(pParentItemId);
            assocD.setItemAssocCd(RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT);
            assocD.setAddBy(user);
            assocD.setModBy(user);
            ItemAssocDataAccess.insert(con,assocD);

    	}catch(NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
    	    throw new RemoteException("Error. ProductInformationBean.addManagedItemLinkByManagedParentItem() Naming Exception happened");
        }catch(SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. ProductInformationBean.addManagedItemLinkByManagedParentItem() SQL Exception happened");    
        }finally{
            closeConnection(con);
        }

    }

    /**
     * add item assoc for linked item of managed item.
     * (Search by managed item child).
     * @param pParentItemId  the managed item child identifier
     * @throws            RemoteException Required by EJB 1.0
     */
    public void addManagedItemLinkByManagedChildItem(int pItemId, int pChildItemId, String user)
        throws RemoteException {
        Connection con=null;    
        try {   
            con=getConnection();

            ItemAssocData assocD = ItemAssocData.createValue();
            assocD.setItem2Id(pItemId);
            assocD.setItem1Id(pChildItemId);
            assocD.setItemAssocCd(RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT);
            assocD.setAddBy(user);
            assocD.setModBy(user);
            ItemAssocDataAccess.insert(con,assocD);

    	}catch(NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
    	    throw new RemoteException("Error. ProductInformationBean.addManagedItemLinkByManagedChildItem() Naming Exception happened");
        }catch(SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. ProductInformationBean.addManagedItemLinkByManagedChildItem() SQL Exception happened");    
        }finally{
            closeConnection(con);
        }

    }

    public void addItemsLinkBetweenStores(int itemId, int linkedItemId, int linkedCatalogId, String user)
	    throws RemoteException {
	    Connection con = null;    
	    try {   
	        con = getConnection();
	        ItemAssocData assocD = ItemAssocData.createValue();
	        assocD.setItem1Id(itemId);
	        assocD.setItem2Id(linkedItemId);
	        assocD.setItemAssocCd(RefCodeNames.ITEM_ASSOC_CD.CROSS_STORE_ITEM_LINK);
	        assocD.setCatalogId(linkedCatalogId);
	        assocD.setAddBy(user);
	        assocD.setModBy(user);
	        ItemAssocDataAccess.insert(con, assocD);
		}
	    catch (NamingException exc) {
	        logError("exc.getMessage");
	        exc.printStackTrace();
		    throw new RemoteException("Error. ProductInformationBean.addItemsLinkBetweenStores() Naming Exception happened");
	    }
	    catch (SQLException exc) {
	        logError("exc.getMessage");
	        exc.printStackTrace();
	        throw new RemoteException("Error. ProductInformationBean.addItemsLinkBetweenStores() SQL Exception happened");    
	    }
	    finally {
	        closeConnection(con);
	    }
	}


    /**
     * Gets all parent categoris.
     * @param   pCategoryId  the category identifier
     * @return  IdVector
     * @throws  RemoteException Required by EJB 1.0
     */
    public IdVector getAllParentCategories(int pCategoryId) throws RemoteException    {
        IdVector result = new IdVector();
        Connection con=null;
        try {
            con=getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID,pCategoryId);
            dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);
            ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(con,dbc);

            int ii=0;
            for(; ii<itemAssocDV.size(); ii++) {
                ItemAssocData itemAssocD = (ItemAssocData) itemAssocDV.get(ii);
                result.add(new Integer(itemAssocD.getItem2Id()));
            }

            if (ii == 0) return result;

            dbc = new DBCriteria();
            dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, result);
            dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);
            itemAssocDV = ItemAssocDataAccess.select(con,dbc);

            for(ii=0; ii<itemAssocDV.size(); ii++) {
                ItemAssocData itemAssocD = (ItemAssocData) itemAssocDV.get(ii);
                result.add(new Integer(itemAssocD.getItem2Id()));
            }

        } catch(NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. ProductInformationBean.getAllParentCategories() Naming Exception happened");
        } catch(SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. ProductInformationBean.getAllParentCategories() SQL Exception happened");
        } finally{
            closeConnection(con);
        }
        return result;
    }
    
    public ProductDataVector getProductsCollectionByItemIds(IdVector itemIds)
    throws RemoteException {
    	ItemDataVector itemDV=null;
    	Connection con=null;
    	try {

    	    con=getConnection();

    	    DBCriteria dbcItem=new DBCriteria();
    	    dbcItem.addOneOf(ItemDataAccess.ITEM_ID,itemIds);
    	    dbcItem.addOrderBy(ItemDataAccess.ITEM_ID);
    	    //pick up items
    	    itemDV=ItemDataAccess.select(con,dbcItem);
    	    return getProducts(itemDV);
    	}catch(NamingException exc) {
    	    logError("exc.getMessage");
    	    exc.printStackTrace();
    	    throw new RemoteException("Error. ProductInformationBean.getProductsCollectionByProductId() Naming Exception happened");
    	}catch(SQLException exc) {
    	    logError("exc.getMessage");
    	    exc.printStackTrace();
    	    throw new RemoteException("Error. ProductInformationBean.getProductsCollectionByProductId() SQL Exception happened");
    	}finally{
    	    closeConnection(con);
    	}    	
    }

}
