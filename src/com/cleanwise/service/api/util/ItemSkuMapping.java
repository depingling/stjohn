package com.cleanwise.service.api.util;

import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.session.IntegrationServicesBean;
import com.cleanwise.service.api.value.IdVector;

import java.sql.*;

import org.apache.log4j.Category;

/**
 * The <code>ItemSkuMapping</code> class provides the
 * funtionality needed to transalate from some sku value
 * to an item id (CLW_ITEM.ITEM_ID).
 *
 * @author <a href="mailto:dvieira@DVIEIRA"></a>
 */
public class ItemSkuMapping {
	private static final Category log = Category.getInstance(ItemSkuMapping.class);

    /**
     * Describe <code>mapToItemId</code> method here.
     *
     * @param pConn a <code>Connection</code> value
     * @param pSku a <code>String</code> value
     * @param pSkuTypeCd a <code>String</code> value
     *   @see RefCodeNames.SKU_TYPE_CD
     * @param pOptionalCatalogId an <code>int</code> value
     * specified the catalog to search in, this value is
     * required when a customer, distributor, or
     * manufacturer sku is provided.  This version assumes case of the item is important
     *
     * @return an <code>int</code> value
     * @exception SQLException if an error occurs
     * @exception Exception if an error occurs
     */
    public static int mapToItemId( Connection pConn,
				   String pSku,
				   String pSkuTypeCd,
				   int pOptionalCatalogId )throws SQLException, Exception {
        return mapToItemId(pConn, pSku, pSkuTypeCd, pOptionalCatalogId, false);
    }
    
    public static int mapToItemId( Connection pConn,
			   String pSku,
			   String pUom,
			   String pSkuTypeCd,
			   int pOptionalCatalogId )throws SQLException, Exception {
    	return mapToItemId(pConn, pSku, pUom, pSkuTypeCd, pOptionalCatalogId, false, null, false);
    }


    /**
     * Describe <code>mapToItemId</code> method here.
     *
     * @param pConn a <code>Connection</code> value
     * @param pSku a <code>String</code> value
     * @param pSkuTypeCd a <code>String</code> value
     *   @see RefCodeNames.SKU_TYPE_CD
     * @param pOptionalCatalogId an <code>int</code> value
     * specified the catalog to search in, this value is
     * required when a customer, distributor, or
     * manufacturer sku is provided.
     *
     * @return an <code>int</code> value
     * @exception SQLException if an error occurs
     * @exception Exception if an error occurs
     */
    public static int mapToItemId( Connection pConn,
				   String pSku,
				   String pSkuTypeCd,
				   int pOptionalCatalogId,
                   boolean ignoreCase)
    	throws SQLException, Exception {
    	return mapToItemId(pConn,pSku, null, pSkuTypeCd, pOptionalCatalogId, ignoreCase, null,false);
    }

    /**
     * Describe <code>mapToItemId</code> method here.
     *
     * @param pConn a <code>Connection</code> value
     * @param pSku a <code>String</code> value
     * @param pSkuTypeCd a <code>String</code> value
     *   @see RefCodeNames.SKU_TYPE_CD
     * @param pOptionalCatalogId an <code>int</code> value
     * specified the catalog to search in, this value is
     * required when a customer, distributor, or
     * manufacturer sku is provided.
     * @param pOptionalBusEntityIdVector optional bus entity id list
     * used for dist and manufacurer mappings.  Will only be used
     * when looking up distributor or manufacturer skus.
     *
     * @return an <code>int</code> value
     * @exception SQLException if an error occurs
     * @exception Exception if an error occurs
     */
    public static int mapToItemId( Connection pConn,
				   String pSku,
				   String pUom,
				   String pSkuTypeCd,
				   int pOptionalCatalogId,
                   boolean ignoreCase,
                   IdVector pOptionalBusEntityIdVector,
                   boolean activeItemsOnly)
    	throws SQLException, Exception {

    log.info("mapToItemId.");
	log.debug("debug enable");
	if ( pSku == null || pSku.length() <= 0 ) {
	    throw new Exception
		( "mapToItemId, missing sku value." );
	}
    if(ignoreCase){
        pSku = pSku.toUpperCase();
    }
	int itemid =  0;
	if ( pSkuTypeCd.equals
	     (RefCodeNames.SKU_TYPE_CD.CLW)) {
	    itemid = 0;
	    String sql = null;
	    if(!activeItemsOnly){
	    	sql = "select max (" +
	    	ItemDataAccess.ITEM_ID + ") from " +
	    	ItemDataAccess.CLW_ITEM + " where " +
	    	ItemDataAccess.SKU_NUM + " = ?";
	    }
	    else{
	    	sql = "select max (" +
	    	ItemDataAccess.ITEM_ID + ") from " +
	    	ItemDataAccess.CLW_ITEM + " where " +
	    	ItemDataAccess.ITEM_STATUS_CD + " = 'ACTIVE' and " +
	    	ItemDataAccess.SKU_NUM + " = ? ";
	    }
	    log.debug(sql);
	    PreparedStatement stmt = pConn.prepareStatement(sql);
	    stmt.setString(1, pSku);
	    ResultSet rs = stmt.executeQuery();
	    if ( rs.next() ) {
		itemid = rs.getInt(1);
	    }

	    stmt.close();
	    return itemid;

	}
	else if ( pSkuTypeCd.equals
		  (RefCodeNames.SKU_TYPE_CD.CUSTOMER)) {
	    // Look in the catalog structure table for a matching
	    // customer sku entry.
	    if ( pOptionalCatalogId <= 0 ) {
		throw new Exception
		    ( "mapToItemId, no catalog id specified " );
	    }

	    String sql = "select max (" +
		CatalogStructureDataAccess.ITEM_ID + ") from " +
		CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE +
		" where ";

        if(ignoreCase){
            sql = sql+"UPPER("+CatalogStructureDataAccess.CUSTOMER_SKU_NUM +")";
        }else{
            sql = sql+CatalogStructureDataAccess.CUSTOMER_SKU_NUM;
        }
		sql = sql + " = ? and "
		+ CatalogStructureDataAccess.CATALOG_ID
		+ " = ?";
	    PreparedStatement stmt = pConn.prepareStatement(sql);
	    stmt.setString(1, pSku);
	    stmt.setInt(2, pOptionalCatalogId);
	    log.debug(sql);
	    ResultSet rs = stmt.executeQuery();
	    if ( rs.next() ) {
		itemid = rs.getInt(1);
	    }
	    stmt.close();

	    if ( itemid > 0 ) {
		// Found an item for this customer sku.
		return itemid;
	    }

	    // no customer sku specified, look for the
	    // cleanwise sku entry.
	    sql = "select max (" +
		ItemDataAccess.ITEM_ID + ") from " +
		ItemDataAccess.CLW_ITEM + " where " +
		ItemDataAccess.SKU_NUM + " = ?";
	    PreparedStatement stmt2 = pConn.prepareStatement(sql);
	    stmt2.setString(1, pSku);
	    ResultSet rs2 = stmt2.executeQuery();
	    if ( rs2.next() ) {
		itemid = rs2.getInt(1);
	    }
	    stmt2.close();

	    return itemid;
	}
	else if ( pSkuTypeCd.equals
		  (RefCodeNames.SKU_TYPE_CD.DISTRIBUTOR)) {
	    // Look in the item mapping table.
	    // ZZZ unique dist sku? per catalog
	    String sql = "select max (im." +
		ItemMappingDataAccess.ITEM_ID + ") from " +
		ItemDataAccess.CLW_ITEM + " i, "+
		ItemMappingDataAccess.CLW_ITEM_MAPPING + " im where ";
	    sql = sql+"im."+ItemMappingDataAccess.ITEM_ID+" = i."+ItemDataAccess.ITEM_ID+" AND ";
        if(ignoreCase){
           sql = sql+ "UPPER("+ItemMappingDataAccess.ITEM_NUM+")";
        }else{
            sql = sql+ItemMappingDataAccess.ITEM_NUM;
        }
        sql = sql + " = ? and "; // pSku
        if (Utility.isSet(pUom)){
	    	sql += ItemMappingDataAccess.ITEM_UOM + " = ? and ";
	    }
        if(pOptionalBusEntityIdVector != null  && pOptionalBusEntityIdVector.size() > 0){
        	sql = sql+" "+ItemMappingDataAccess.BUS_ENTITY_ID+" IN ("+Utility.toCommaSting(pOptionalBusEntityIdVector)+") and ";
        }


        sql = sql +
		ItemMappingDataAccess.ITEM_MAPPING_CD +
		" = '" +
		RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR
		+ "' ";
        if(activeItemsOnly){
        	sql = sql +
        	"and im.item_id=i.item_id AND i.item_status_cd='ACTIVE'";
        }
        if(pOptionalCatalogId > 0){
        	sql = sql +
        	"and im.item_id IN (select item_id from clw_catalog_structure where catalog_id = "+pOptionalCatalogId+")";
        }
	    PreparedStatement stmt = pConn.prepareStatement(sql);
	    log.debug(sql);
	    stmt.setString(1, pSku);
	    if (Utility.isSet(pUom)){
	    	stmt.setString(2, pUom);
	    }
	    ResultSet rs = stmt.executeQuery();
	    if ( rs.next() ) {
		itemid = rs.getInt(1);
	    }
	    stmt.close();
	    return itemid;
	}
	else if ( pSkuTypeCd.equals
		  (RefCodeNames.SKU_TYPE_CD.MANUFACTURER)) {
	    // Look in the item mapping table.
	    // ZZZ unique manuf sku? per catalog
	    String sql = "select max (" +
		ItemMappingDataAccess.ITEM_ID + ") from " +
		ItemMappingDataAccess.CLW_ITEM_MAPPING + "  where ";
		if(ignoreCase){
            sql = sql+"UPPER("+ItemMappingDataAccess.ITEM_NUM+")";
        }else{
            sql = sql+ItemMappingDataAccess.ITEM_NUM;
        }
		if(pOptionalBusEntityIdVector != null){
        	sql = sql+" "+ItemMappingDataAccess.BUS_ENTITY_ID+" IN ("+Utility.toCommaSting(pOptionalBusEntityIdVector)+") ";
        }
		sql = sql + " = ? and " + //pSku
		ItemMappingDataAccess.ITEM_MAPPING_CD +
		" = '" +
		RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER
		+ "' ";

		log.debug(sql);
	    PreparedStatement stmt = pConn.prepareStatement(sql);
	    stmt.setString(1, pSku);
	    ResultSet rs = stmt.executeQuery();
	    if ( rs.next() ) {
		itemid = rs.getInt(1);
	    }
	    stmt.close();
	    return itemid;
	}

	// No other known SKU types.
	throw new Exception
	    ( "mapToItemId, unknown SKU type: " + pSkuTypeCd );
    }

}

