package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.dao.ItemAssocDataAccess;
import com.cleanwise.service.api.dao.ItemDataAccess;
import com.cleanwise.service.api.dao.ItemMappingDataAccess;
import com.cleanwise.service.api.dao.ItemMetaDataAccess;
import com.cleanwise.service.apps.ClientServicesAPI;

/*
 * Utility Class that used to add managed item for a giving store.
 * If matching item found in enterprise store based on Manufacturer name, sku and uom, 
 * copy or update item meta records, short and long description from enterprise store item.
 * add MANAGED_ITEM_PARENT relation in CLW_ITEM_ASSOC 
 */

public class UpdateStoreManagedItem extends ClientServicesAPI {
	protected static Logger log = Logger.getLogger("UpdateStoreManagedItem");
	private static final String className = "UpdateStoreManagedItem";

	private int storeCatalogId = 0;
	private Connection conn = null;
	private long startTime;
	private int enterpriseStoreId = 0;
	private int enterpriseStoreCatalogId = 0;
	private static final String selectEnterpriseStoreAndCatalogIdSql = "SELECT store.bus_entity_id, c.catalog_id FROM CLW_PROPERTY p, CLW_BUS_ENTITY store, CLW_CATALOG c, CLW_CATALOG_ASSOC ca " + 
	"WHERE p.bus_entity_id = store.bus_entity_id " +
	"and ca.bus_entity_id = store.bus_entity_id " +
	"and ca.catalog_id = c.catalog_id " +
	"and p.SHORT_DESC = 'STORE_TYPE_CD' " +
	"AND p.CLW_VALUE = 'ENTERPRISE' " +
	"AND store.BUS_ENTITY_TYPE_CD = 'STORE' " +
	"AND ca.CATALOG_ASSOC_CD = 'CATALOG_STORE' " +
	"AND c.CATALOG_STATUS_CD = 'ACTIVE' " +
	"AND c.CATALOG_TYPE_CD = 'STORE'";
	private static final String selectCatalogIdSql = "select storeCatalog.catalog_id " +
	"from clw_bus_entity store, clw_catalog storeCatalog, clw_catalog_assoc storeCatAssoc " +
	"where store.bus_entity_id = ? " +
	"and storeCatalog.catalog_id = storeCatAssoc.catalog_id " +
	"and storeCatalog.catalog_type_cd = 'STORE' " +
	"and storeCatAssoc.bus_entity_id = store.bus_entity_id " +
	"and storeCatalog.catalog_status_cd = 'ACTIVE'";	

	private static final String selectItemIdFromEnterpriseStore = "select i.item_id, p.clw_value " +
	"from clw_item i, clw_item_mapping mfim, clw_catalog_structure cs, clw_item_meta im, " +
	"clw_property p, clw_bus_entity manuf, clw_bus_entity_assoc manufa " +
	"where i.item_id = mfim.item_id " +
	"and i.item_id = cs.item_id " +
	"and mfim.item_mapping_cd = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER + "' " +
	"and i.item_status_cd = '" + RefCodeNames.ITEM_STATUS_CD.ACTIVE + "' " +
	"and mfim.bus_entity_id = p.bus_entity_id " +
	"and p.short_desc = 'OTHER_NAMES' " +
	"and p.bus_entity_id = manuf.bus_entity_id " +
	"and manuf.bus_entity_id = manufa.bus_entity1_id " +
	"and manufa.bus_entity2_id = ? " +
	"and p.clw_value like ? " +
	"and cs.catalog_id = ? " +
	"and mfim.item_num = ? " +
	"and i.item_id = im.item_id " +
	"and im.name_value = 'UOM' " +
	"and im.clw_value = ? ";
	
	private static final String selectItemInfosByCatalog = "select cs.item_id, iuom.clw_value, im.item_num, b.short_desc " +
			"from clw_catalog_structure cs, clw_item_mapping im, clw_bus_entity b, " +
			"(SELECT item_id, CLW_VALUE FROM CLW_ITEM_META WHERE NAME_VALUE = 'UOM') iuom  " +
			"where catalog_id = ? " +
			"and catalog_structure_cd = '" + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT + "' " +
			"and cs.item_id = im.item_id " +
			"and cs.item_id = iuom.item_id " +
			"and im.item_mapping_cd = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER + "' " +
			"and im.bus_entity_id = b.bus_entity_id " +
			"and im.item_num is not null " +
			"and im.item_num != 'NA' ";
	
	public UpdateStoreManagedItem() throws Exception {
		Connection conn = getConnection();
		conn.setAutoCommit(false);
		setConnection(conn);
	}
	
	public UpdateStoreManagedItem(Connection pConn) throws Exception{
		setConnection(pConn);		
	}
	
	public void setConnection(Connection pConn) throws Exception{
		conn = pConn;
		try {						
			// get enterprise store id and catalog id
			Statement stmt = conn.createStatement();
			log.debug(selectEnterpriseStoreAndCatalogIdSql);
			ResultSet rs = stmt.executeQuery(selectEnterpriseStoreAndCatalogIdSql);
			if (rs.next()){
				enterpriseStoreId = rs.getInt(1);
				enterpriseStoreCatalogId = rs.getInt(2);
				if (rs.next()){
					throw new Exception("Multiple active enterprise store catalogs found.");
				}
			}else {
				log.info("No active enterprose store catalog found.");
			}

			rs.close();
			stmt.close();		
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}	
	}
	
	public void closeConnection() throws Exception{
		if (conn != null)
			conn.close();
	}

	/**
	 * Called when the object has successfully been parsed
	 */
	public void processByStore(int storeId, boolean newManagedItemOnly)  throws Exception{

		startTime = System.currentTimeMillis();		
		try {
			// get store catalog id
			PreparedStatement pstmt = conn.prepareStatement(selectCatalogIdSql);
			pstmt.setInt(1, storeId);
			log.debug("SQL:   store.bus_entity_id = " + storeId);
			log.debug(selectCatalogIdSql);
			ResultSet rs = pstmt.executeQuery();

			if (!rs.next()){
				throw new Exception("Either catalog or distributor is not setup for storeId = " + storeId);
			}else{
				storeCatalogId = rs.getInt(1);
				if (rs.next()){
					throw new Exception("Multiple store catalog is setup when suppose to have only one. Store Id: "+storeId);					
				}
			}
			rs.close();
			pstmt.close();
			
			pstmt = conn.prepareStatement(selectItemInfosByCatalog);
			pstmt.setInt(1, storeCatalogId);
			log.debug("SQL:   catalog_id = " + storeCatalogId);
			log.debug(selectItemInfosByCatalog);
			rs = pstmt.executeQuery();
			int count = 0;
			
			while (rs.next()){
				count++;
				int itemId = rs.getInt(1);
				String uom = rs.getString(2);
				String manufSku = rs.getString(3);
				String manufName = rs.getString(4);
				if (updateManagedItem(itemId, newManagedItemOnly, manufName, manufSku, uom))				
					conn.commit();
			}

			rs.close();
			pstmt.close();
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
	}

	private ItemData getItemFromEnterpriseStore(String manufName, String manufSku, String uom) throws Exception {
		ItemData itemD = null;
		if (manufSku == null || manufSku.equals(""))
			return null;
		if (enterpriseStoreCatalogId > 0){
			if ("CA".equals(uom))
				uom = "CS";
			else if ("CT".equals(uom))
				uom = "EA";
			
			log.debug("SQL::enterpriseStoreId="+enterpriseStoreId);
			log.debug("SQL::manufacturerName="+manufName);
			log.debug("SQL::enterpriseStoreCatalogId="+enterpriseStoreCatalogId);
			log.debug("SQL::manufSku="+manufSku);
			log.debug("SQL::uom="+uom);
			log.debug("SQL::"+selectItemIdFromEnterpriseStore);
			PreparedStatement pstmt = conn.prepareStatement(selectItemIdFromEnterpriseStore);
			pstmt.setInt(1, enterpriseStoreId);
			pstmt.setString(2, "%" + manufName + "%");
			pstmt.setInt(3, enterpriseStoreCatalogId);
			pstmt.setString(4, manufSku);
			pstmt.setString(5, uom);
			ResultSet rs = pstmt.executeQuery();
			try {
				if (rs.next()) {
					int itemId = rs.getInt(1);
					String manufNames = rs.getString(2);
					StringTokenizer st = new StringTokenizer(manufNames, "\r\n");
					while (st.hasMoreTokens() ) {
						String currManufName= st.nextToken().trim();
						if (currManufName.equals(manufName)){
							return ItemDataAccess.select(conn, itemId);
						}
					}
				}
			}finally{
				rs.close();
				pstmt.close();
			}
		}

		return itemD;
	}
	
	private ItemMetaData createOrUpdateItemMetaObject(String name, String value, int itemId) throws SQLException {
		if (Utility.isSet(value)){
			DBCriteria dbc = new DBCriteria();
			dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID, itemId);
			dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE, name);
			ItemMetaDataVector iaDV = ItemMetaDataAccess.select(conn, dbc);
			if (iaDV.size() == 0){
				return createItemMetaObject(name, value, itemId);
			}else{
				ItemMetaData itemMetaD = (ItemMetaData) iaDV.get(0);
				if (value.equals(itemMetaD.getValue()))
					return itemMetaD;
				else{
					itemMetaD.setValue(value);
					itemMetaD.setModBy(className);
					ItemMetaDataAccess.update(conn, itemMetaD);
					return itemMetaD;
				}
			}
		}
		return null;		
	}

	private ItemMetaData createItemMetaObject(String name, String value, int itemId) throws SQLException {
		if (Utility.isSet(value)){
			ItemMetaData itemMetaD;
			itemMetaD = ItemMetaData.createValue();			
			itemMetaD.setItemId(itemId);
			itemMetaD.setNameValue(name);
			itemMetaD.setValue(value);
			itemMetaD.setAddBy(className);
			itemMetaD.setModBy(className);
			itemMetaD = ItemMetaDataAccess.insert(conn, itemMetaD);
			return itemMetaD;
		}
		return null;
	}	

	private ItemAssocData processItemAssoc(int item2Id, 
			int item1Id, String itemAssocCd) throws SQLException {
		ItemAssocData iaD = getManagedItem(item1Id);
		if (iaD != null){
			if (iaD.getItem2Id() != item2Id){
				iaD.setItem2Id(item2Id);
				iaD.setModBy(className);
				ItemAssocDataAccess.update(conn, iaD);
			}
		}else{		
			iaD = ItemAssocData.createValue();
			iaD.setItem1Id(item1Id);
			iaD.setItem2Id(item2Id);
			iaD.setItemAssocCd(itemAssocCd);
			iaD.setAddBy(className);
			iaD.setModBy(className);
			ItemAssocDataAccess.insert(conn, iaD);
		}
		return iaD;
	}

	private boolean isManagedItem(int childItemId) throws SQLException{
		DBCriteria dbcItemAssoc=new DBCriteria();
		dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM1_ID,childItemId);
		dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT);
		IdVector itemIdV = ItemAssocDataAccess.selectIdOnly(conn, ItemAssocDataAccess.ITEM2_ID, dbcItemAssoc);	
		return itemIdV.size() > 0;
	}
	
	private ItemAssocData getManagedItem(int childItemId) throws SQLException{
		DBCriteria dbcItemAssoc=new DBCriteria();
		dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM1_ID,childItemId);
		dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT);
		ItemAssocDataVector itemADVector = ItemAssocDataAccess.select(conn,dbcItemAssoc);	
		if (itemADVector.size() > 0)
			return (ItemAssocData) itemADVector.get(0);
		else
			return null;
	}
	
	public boolean updateManagedItem(int itemId, boolean newManagedItemOnly, String manufName, String manufSku, String uom) throws Exception{
		if (newManagedItemOnly){				
			if (isManagedItem(itemId)){
				return true;
			}
		}
		
		ItemData itemDFromEnterpriseStore = getItemFromEnterpriseStore( manufName, manufSku, uom);
		if (itemDFromEnterpriseStore == null){
			return false;
		}
			
		// update item meta data
		DBCriteria dbc = new DBCriteria();
		dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID, itemDFromEnterpriseStore.getItemId());
		ItemMetaDataVector itemMetaDV = ItemMetaDataAccess.select(conn, dbc);
		for (int i = 0; i < itemMetaDV.size(); i++){
			ItemMetaData itemMetaD = (ItemMetaData) itemMetaDV.get(i);
			createOrUpdateItemMetaObject(itemMetaD.getNameValue(), itemMetaD.getValue(), itemId);					
		}
		
		// update short and long description
		ItemData itemD = ItemDataAccess.select(conn, itemId);
		itemD.setShortDesc(itemDFromEnterpriseStore.getShortDesc());
		itemD.setLongDesc(itemDFromEnterpriseStore.getLongDesc());
		itemD.setModBy(className);
		ItemDataAccess.update(conn, itemD);				
		
		// update green certified item mapping
		dbc = new DBCriteria();
		dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID, itemDFromEnterpriseStore.getItemId());
		dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY);
		ItemMappingDataVector imEnterpriseStoreDV = ItemMappingDataAccess.select(conn, dbc);
		if (imEnterpriseStoreDV.size() > 0){
			dbc = new DBCriteria();
			dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID, itemId);
			dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY);
			ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(conn, dbc);
			if (itemMappingDV.size() == 0){
				ItemMappingData itemMappingD = (ItemMappingData) imEnterpriseStoreDV.get(0);
				itemMappingD.setItemId(itemId);
				itemMappingD.setAddBy(className);
				itemMappingD.setModBy(className);
				ItemMappingDataAccess.insert(conn, itemMappingD);
			}					
		}
		// update MANAGED_ITEM_PARENT item association
		processItemAssoc(itemDFromEnterpriseStore.getItemId(), itemId, RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT);
		return true;
	}

	public static void main (String args[]) throws Exception {
		//int storeId = 196842;//janpak catalog_id = 22247
		int storeId = 172427;//Eastern Bag catalog_id = 9107 
		//int storeId = 186825;//Connexion-Online Store catalog=13307
		boolean newManagedItemOnly = true;
		
		
		String storeIdStr = System.getProperty ("storeId");
		String newManagedItemOnlyStr = System.getProperty ("newManagedItemOnly");
		if (Utility.isSet(storeIdStr)){
			storeId = new Integer(storeIdStr).intValue();
		}
		if (Utility.isSet(newManagedItemOnlyStr)){
			newManagedItemOnly = newManagedItemOnlyStr.equalsIgnoreCase("true") || newManagedItemOnlyStr.equalsIgnoreCase("t");
		}

		try {
			UpdateStoreManagedItem process = new UpdateStoreManagedItem();	
			process.processByStore(storeId, newManagedItemOnly);
			process.closeConnection();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
}

