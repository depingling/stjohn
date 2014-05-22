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


import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.CatalogStructureDataAccess;
import com.cleanwise.service.api.dao.ItemAssocDataAccess;
import com.cleanwise.service.api.dao.ItemDataAccess;
import com.cleanwise.service.api.dao.ItemMappingDataAccess;
import com.cleanwise.service.api.dao.ItemMetaDataAccess;
import com.cleanwise.service.api.session.CatalogBean;


public class InboundJanPakItem extends InboundFlatFile {
	protected Logger log = Logger.getLogger(this.getClass());
	private static final String className = "InboundJanPakItem";

	private Date runDate = new Date();
	private Map manufactureMap = new HashMap(); // name and manufacturer busEntityId map
	private int storeId = -1;
	private int distributorId = -1;
	private int storeCatalogId = 0;
	private static final String CATEGORY_NOT_ASSIGN = "CATEGORY_NOT_ASSIGN";
	private Map categoryMap = new HashMap();// category name and itemId map for category in the new processed catalog
	private List itemNumList = new ArrayList();// List of dist item sku for including in the loading
	private Connection conn = null;
	private long startTime;
	private int lineCount = 0;
	private int enterpriseStoreId = 0;
	private int enterpriseStoreCatalogId = 0;
	private boolean checkItemNumList = false;// JanPak set to true, JD China set to false;
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
	private static final String selectCatalogIdAndDistributorIdSql = "select storeCatalog.catalog_id, dist.bus_entity_id " +
		"from clw_bus_entity store, clw_catalog storeCatalog, clw_catalog_assoc storeCatAssoc, " +
				"clw_bus_entity dist, clw_bus_entity_assoc distAssoc " +
		"where store.bus_entity_id = ? " +
		"and storeCatalog.catalog_id = storeCatAssoc.catalog_id " +
		"and storeCatalog.catalog_type_cd = 'STORE' " +
		"and storeCatAssoc.bus_entity_id = store.bus_entity_id " +
		"and dist.bus_entity_id = distAssoc.bus_entity1_id " +
		"and store.bus_entity_id = distAssoc.bus_entity2_id " +
		"and distAssoc.bus_entity_assoc_cd = 'DISTRIBUTOR OF STORE' " +
		"and dist.bus_entity_status_cd = 'ACTIVE' " +
		"and storeCatalog.catalog_status_cd = 'ACTIVE'";

	private static final String selectManufIdSql = "SELECT DISTINCT B.BUS_ENTITY_ID " +
			"FROM CLW_BUS_ENTITY B, CLW_BUS_ENTITY_ASSOC BA " +
			"WHERE B.BUS_ENTITY_STATUS_CD <> 'INACTIVE' " +
			"AND B.BUS_ENTITY_ID = BA.BUS_ENTITY1_ID " +
			"AND BA.BUS_ENTITY2_ID = ? " +
			"AND UPPER(B.SHORT_DESC) = ? " +
			"AND B.BUS_ENTITY_TYPE_CD = 'MANUFACTURER'";

	private static final String selectExistItemSql = "select distinct i.item_id product_id, categ.item_id category_id, mf.bus_entity_id manufacturer_id, " +
			"dim.item_uom dist_uom, " +
			"dim.item_num dist_sku, " +
			"mf.short_desc manuf_name, " +
			"mfim.item_num manuf_sku, " +
			"i.short_desc, " +
			"categ.short_desc category_name " +
			"from " +
			"clw_item i, " +
			"clw_catalog_structure cstr, " +
			"clw_item_assoc ia, " +
			"clw_item categ, " +
			"clw_item_mapping mfim, " +
			"clw_bus_entity mf, " +
			"clw_item_mapping dim, " +
			"clw_bus_entity dist " +
			"where i.item_id = cstr.item_id " +
			"and cstr.catalog_id =  ? " +
			"and cstr.catalog_structure_cd = '" + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT + "' " +
			"and categ.item_id = ia.item2_id " +
			"and ia.item1_id = i.item_id " +
			"and ia.item_assoc_cd = '" + RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY + "' " +
			"and ia.catalog_id = ? " +
			"and mfim.item_id = i.item_id " +
			"and mfim.item_mapping_cd(+) = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER + "' " +
			"and mf.bus_entity_id = mfim.bus_entity_id " +
			"and dim.item_mapping_cd = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR + "' " +
			"and dim.item_id = i.item_id " +
			"and dist.bus_entity_id = dim.bus_entity_id " +
			"and dim.item_mapping_cd = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR + "' " +
			"and dim.item_id = i.item_id " +
			"and i.item_status_cd = '" + RefCodeNames.ITEM_STATUS_CD.ACTIVE + "' " +
			"and dim.item_num = ? " +
			"and dim.item_uom = ?";
	String selectItemIdFromEnterpriseStore = "select i.item_id " +
			"from clw_item i, clw_item_mapping mfim, clw_catalog_structure cs, clw_item_meta im " +
			"where i.item_id = mfim.item_id " +
			"and i.item_id = cs.item_id " +
			"and mfim.item_mapping_cd = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER + "' " +
			"and i.item_status_cd = '" + RefCodeNames.ITEM_STATUS_CD.ACTIVE + "' " +
			"and mfim.bus_entity_id in (" +
				"select p.bus_entity_id   " +
				"from clw_property p, clw_bus_entity manuf, clw_bus_entity_assoc manufa " +
				"where p.short_desc = 'OTHER_NAMES' " +
				"and p.bus_entity_id = manuf.bus_entity_id " +
				"and manuf.bus_entity_id = manufa.bus_entity1_id " +
				"and manufa.bus_entity2_id = ? " +
				"and p.clw_value like ?) " +
			"and cs.catalog_id = ? " +
			"and mfim.item_num = ? " +
			"and i.item_id = im.item_id " +
			"and im.name_value = 'UOM' " +
			"and im.clw_value = ?";

	/**
	 * Called when the object has successfully been parsed
	 */
	protected void processParsedObject(Object pParsedObject)  throws Exception{
		if(pParsedObject == null) {
			throw new IllegalArgumentException("No parsed site object present");
		}

		if (lineCount == 0){
			startTime = System.currentTimeMillis();
		}else if (lineCount%50==0){
			log.info("*********** " + (System.currentTimeMillis()-startTime)/1000 + " Second has passed. " + "*********** ");
		}


		try {
			JanPakItemView txtData = (JanPakItemView)pParsedObject;
			if (!Utility.isSet(txtData.getCategoryName()))
				txtData.setCategoryName(CATEGORY_NOT_ASSIGN);
			log.info("Process Line " + ++lineCount + ":Dist Sku=" + txtData.getDistSku());
			if (lineCount%20==1){
				if (conn != null)
					conn.commit();
				conn = getConnection();
				conn.setAutoCommit(false);
			}

			// first time, load storeId, distributorId
			if (storeId < 0){
				//Getting trading partner id
				TradingPartnerData partner = translator.getPartner();
				if(partner == null) {
					throw new IllegalArgumentException("Trading Partner ID cann't be determined");
				}
				int tradingPartnerId = partner.getTradingPartnerId();
				int storeIds[] = translator.getTradingPartnerBusEntityIds(tradingPartnerId,
						RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
				if(storeIds == null || storeIds.length < 1) {
					throw new IllegalArgumentException("No stores present for current trading partner id = " +
							tradingPartnerId);
				} else if(storeIds.length > 1) {
					throw new Exception("Multiple stores present for current trading partner id = " +
							tradingPartnerId);
				}
				storeId = storeIds[0];

				PreparedStatement pstmt = conn.prepareStatement(selectCatalogIdAndDistributorIdSql);
				pstmt.setInt(1, storeId);
				log.debug("SQL:   store.bus_entity_id = " + storeId);
				log.debug(selectCatalogIdAndDistributorIdSql);
				ResultSet rs = pstmt.executeQuery();

				if (!rs.next()){
					throw new Exception("Either catalog or distributor is not setup for storeId = " + storeId);
				}else{
					storeCatalogId = rs.getInt(1);
					distributorId = rs.getInt(2);
					if (rs.next()){
						int catalogId = rs.getInt(1);
						if (catalogId == storeCatalogId)
							throw new Exception("Multiple store catalog is setup when suppose to have only one. Store Id: "+storeId);
						int distId = rs.getInt(1);
						if (distId == distributorId){
			                String errorMess = "Store has multiple distributors when suppose to have only one. Store Id: "+storeId;
			                throw new Exception(errorMess);
			            }
					}
				}

				rs.close();
				pstmt.close();

				Statement stmt = conn.createStatement();
				log.debug(selectEnterpriseStoreAndCatalogIdSql);
				rs = stmt.executeQuery(selectEnterpriseStoreAndCatalogIdSql);
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

				if (checkItemNumList){
					itemNumList = new ArrayList();
					String selectItemSku = "select item_num from dl_temp_item_num";
					stmt = conn.createStatement();
					rs = stmt.executeQuery(selectItemSku);
					while (rs.next()){
						itemNumList.add(rs.getString(1));
					}
					rs.close();
					stmt.close();
					log.info("Total Item in table dl_temp_item_num=" + itemNumList.size());
				}
			}
			if (!checkItemNumList || itemNumList.contains(txtData.getDistSku())){
				processItemRecord(conn, txtData);
			}
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
			conn.close();
			conn = null;
			throw e;
		}
	}

	private void processItemRecord(Connection conn, JanPakItemView beanToSave)
	throws Exception {
		int categoryId = 0;
		if (!Utility.isSet(beanToSave.getDistUom())){
			beanToSave.setDistUom("EA");
		}
		ItemView existItem = getExistItemView(conn, beanToSave.getDistSku(), beanToSave.getDistUom());
		ItemData itemDFromEnterpriseStore = getItemFromEnterpriseStore(beanToSave.getManufName(), beanToSave.getManufSku(), beanToSave.getDistUom());
		int productId = existItem != null ? existItem.productId : 0;
		IdVector managedItemIds = null;

		if (existItem != null){
			if (Utility.isEqual(beanToSave.getCategoryName(), existItem.getCategoryName())){
				categoryId = existItem.categoryId;
			}
		}else{
			if (!Utility.isSet(beanToSave.getManufSku())){
				beanToSave.setManufSku("NA");
			}
		}

		if (existItem == null || categoryId <= 0){
			categoryId = processCategory(conn, beanToSave.getCategoryName());
		}
		ItemData itemD = null;

		if (existItem == null){
			if (itemDFromEnterpriseStore != null){
				itemD = createItemDataObject(conn, itemDFromEnterpriseStore.getShortDesc(), itemDFromEnterpriseStore.getLongDesc(), RefCodeNames.ITEM_TYPE_CD.PRODUCT);
				DBCriteria dbc = new DBCriteria();
				dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID, itemDFromEnterpriseStore.getItemId());
				ItemMetaDataVector itemMetaDV = ItemMetaDataAccess.select(conn, dbc);
				for (int i = 0; i < itemMetaDV.size(); i++){
					ItemMetaData itemMetaD = (ItemMetaData) itemMetaDV.get(i);
					createItemMetaObject(conn, itemMetaD.getNameValue(), itemMetaD.getValue(), itemD.getItemId());
				}
				createItemAssoc(conn, 0, itemDFromEnterpriseStore.getItemId(), itemD.getItemId(), RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT);
			} else {
				itemD = createItemDataObject(conn, beanToSave.getDistItemDesc(), beanToSave.getDistItemDesc(), RefCodeNames.ITEM_TYPE_CD.PRODUCT);
				createItemMetaObject(conn, "UOM", beanToSave.getDistUom(), itemD.getItemId());
				createItemMetaObject(conn, "PACK", "1", itemD.getItemId());
			}

			productId = itemD.getItemId();
			// create ItemMapping object for distributor
			createItemMappingObject(conn, beanToSave.getDistSku(), null,
					beanToSave.getDistUom(), distributorId, itemD.getItemId(), RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR, beanToSave.getDistItemDesc());

			int manufacturerId = getManufactureId(beanToSave.getManufName());
			createItemMappingObject(conn, beanToSave.getManufSku(), null, null, manufacturerId, productId, RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER, null);

			// create store catalog structure
			createCatalogStructureObject(conn, storeCatalogId, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT, productId, itemD.getSkuNum()+"");

	        // create CLW_ITEM_ASSOC record for the new product
	        createItemAssoc(conn, storeCatalogId, categoryId, productId, RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
		}else{
			if (categoryId != existItem.categoryId){ // update product and category association
				// update item association to store catalog
				processItemAssoc(conn, storeCatalogId, categoryId, productId, RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
			}
			if (itemDFromEnterpriseStore != null){
				if (isManagedItem(conn, productId)){
					log.info("Item not updated. Managed Item found in enterprise store - " + beanToSave.getDistSku());
					return;
				}
			}
			if (existItem.equals(beanToSave)){
				log.info("Item has no change - " + beanToSave.getDistSku());
				return;
			}


			if (itemDFromEnterpriseStore != null){
				if (managedItemIds.size() > 0){
					log.info("Item not updated since managed Item found in enterprise store - " + beanToSave.getDistSku());
					return;
				}
			}
			if (Utility.isEqual(beanToSave.getDistItemDesc(), existItem.getDistItemDesc())
					&& Utility.isEqual(beanToSave.getManufName(), existItem.getManufName())
					&& Utility.isEqual(beanToSave.getManufSku(), existItem.getManufSku())){
				log.info("XXXXXXXXX Item has no update: " + beanToSave.getDistSku());
				return;
			}


			if (!Utility.isEqual(beanToSave.getDistItemDesc(), existItem.getDistItemDesc())){
				itemD = ItemDataAccess.select(conn, productId);
				itemD.setModBy(className);
				itemD.setShortDesc(beanToSave.getDistItemDesc());
				ItemDataAccess.update(conn, itemD);
			}

			if (!Utility.isEqual(beanToSave.getDistItemDesc(), existItem.getDistItemDesc())){
				// process ItemMappingData object for distributor
				processItemMapping(conn, distributorId, productId, beanToSave.getDistSku(), beanToSave.getDistUom(), RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR, beanToSave.getDistItemDesc());
			}

			if (!Utility.isEqual(beanToSave.getManufName(), existItem.getManufName())
					|| !Utility.isEqual(beanToSave.getManufSku(), existItem.getManufSku())){
				// process ItemMappingData object for manufacturer
				int manufacturerId = getManufactureId(beanToSave.getManufName());
				processItemMapping(conn, manufacturerId, productId, beanToSave.getManufSku(), null, RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER, null);
			}
		}

	}

	private ItemData getItemFromEnterpriseStore(String manufName, String manufSku, String uom) throws Exception {
		ItemData itemD = null;
		if (manufSku == null || manufSku.equals("") || manufSku.equals("NA"))
			return null;
		if (enterpriseStoreCatalogId > 0){
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
			rs.close();
			pstmt.close();
		}

		return itemD;
	}

	private ItemData createItemDataObject(Connection pConn, String shortDesc, String longDesc,  String itemTypeCd) throws SQLException {
		ItemData itemD;
		itemD = ItemData.createValue();
		itemD.setAddBy(className);
		itemD.setModBy(className);
		itemD.setEffDate(runDate);
		itemD.setShortDesc(shortDesc);
		itemD.setLongDesc(longDesc);
		itemD.setItemStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
		itemD.setItemTypeCd(itemTypeCd);
		itemD = ItemDataAccess.insert(pConn, itemD);

		if (itemTypeCd.equals(RefCodeNames.ITEM_TYPE_CD.PRODUCT)){
			itemD.setSkuNum(itemD.getItemId() + CatalogBean.SKU_MINIMUM);
			itemD.setModBy(className);
			ItemDataAccess.update(pConn, itemD);
		}
		return itemD;
	}

	private int processCategory(Connection pConn, String categoryName) throws SQLException {
		Integer categoryId = (Integer) categoryMap.get(categoryName);
		if (categoryId == null){
			// process category
			DBCriteria dbc = new DBCriteria();
			dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, storeCatalogId);
			dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

			DBCriteria dbc1 = new DBCriteria();
			dbc1.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.CATEGORY);
			dbc1.addEqualTo(ItemDataAccess.SHORT_DESC, categoryName);
			dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, ItemDataAccess.getSqlSelectIdOnly(ItemDataAccess.ITEM_ID, dbc1));
			CatalogStructureDataVector csDV = CatalogStructureDataAccess.select(pConn, dbc);
			CatalogStructureData csD = null;

			if (csDV.size() == 0){
	        	// create category item
	        	ItemData categoryItemD = createItemDataObject(pConn, categoryName, categoryName, RefCodeNames.ITEM_TYPE_CD.CATEGORY);
	        	// create store catalog structure object for the new category
	        	csD = CatalogStructureData.createValue();
	    		csD.setCatalogId(storeCatalogId);
	    		csD.setBusEntityId(distributorId);
	    		csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
	    		csD.setItemId(categoryItemD.getItemId());
	    		csD.setEffDate(runDate);
	    		csD.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);
	    		csD.setAddBy(className);
	    		csD.setModBy(className);
	    		csD = CatalogStructureDataAccess.insert(pConn, csD);

			}else{
				csD = (CatalogStructureData) csDV.get(0);
			}
			categoryMap.put(categoryName, new Integer(csD.getItemId()));
	        return csD.getItemId();
		}
		return categoryId.intValue();
	}

	private ItemMappingData createItemMappingObject(Connection pConn,
			String sku, String pack, String uom, int busEntityId, int itemId, String itemMappingCd, String itemDesc)
			throws SQLException {
		ItemMappingData imD = ItemMappingData.createValue();
		imD.setBusEntityId(busEntityId);
		imD.setItemId(itemId);
		imD.setItemMappingCd(itemMappingCd);
		imD.setShortDesc(itemDesc);
		imD.setItemNum(sku);
		imD.setItemPack(pack);
		imD.setItemUom(uom);
		imD.setStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
		imD.setEffDate(runDate);
		imD.setAddBy(className);
		imD.setModBy(className);
		imD = ItemMappingDataAccess.insert(pConn, imD);
		return imD;
	}

	private int getManufactureId(String manufName) throws Exception {
		Integer manufactureId = (Integer) manufactureMap.get(manufName);
		if (manufactureId != null)
			return manufactureId.intValue();

		PreparedStatement pstmt = conn.prepareStatement(selectManufIdSql);
		pstmt.setInt(1, storeId);
		pstmt.setString(2, manufName);
		log.debug("SQL:   BA.BUS_ENTITY2_ID = " + storeId);
		log.debug("SQL:   UPPER(B.SHORT_DESC) = " + manufName);
		log.debug(selectCatalogIdAndDistributorIdSql);
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()){
			manufactureId = new Integer(rs.getInt(1));
		}else{
			// set the manufacturer's bus entity
		    BusEntityData busEntity = BusEntityData.createValue();
		    busEntity.setShortDesc(manufName);
		    busEntity.setBusEntityTypeCd(RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);
		    busEntity.setBusEntityStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
		    busEntity.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
		    busEntity.setLocaleCd(RefCodeNames.LOCALE_CD.EN_US);
		    busEntity.setAddBy(className);
		    busEntity.setModBy(className);
		    busEntity.setEffDate(runDate);
		    busEntity = BusEntityDataAccess.insert(conn, busEntity);

		    // create busEntityAssoc
			BusEntityAssocData busEntityAssocD = BusEntityAssocData.createValue();
			busEntityAssocD.setBusEntity1Id(busEntity.getBusEntityId());
			busEntityAssocD.setBusEntity2Id(storeId);
			busEntityAssocD.setAddBy(className);
			busEntityAssocD.setBusEntityAssocCd(RefCodeNames.BUS_ENTITY_ASSOC_CD.MANUFACTURER_STORE);
			busEntityAssocD = BusEntityAssocDataAccess.insert(conn, busEntityAssocD);
			manufactureId = new Integer(busEntity.getBusEntityId());
			log.info("Created new manufacture = " + manufName);
		}
		rs.close();
		pstmt.close();
		manufactureMap.put(manufName, manufactureId);
		return manufactureId.intValue();
	}

	private CatalogStructureData createCatalogStructureObject(Connection pConn, int catalogId,
			String catalogStructureCd, int itemId, String customerSku) throws SQLException {
		CatalogStructureData csD;
		csD = CatalogStructureData.createValue();
		csD.setCatalogId(catalogId);
		csD.setBusEntityId(distributorId);
		csD.setCatalogStructureCd(catalogStructureCd);
		csD.setItemId(itemId);
		csD.setCustomerSkuNum(customerSku);
		csD.setEffDate(runDate);
		csD.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);
		csD.setAddBy(className);
		csD.setModBy(className);
		csD = CatalogStructureDataAccess.insert(pConn, csD);
		return csD;
	}

	private ItemMetaData createItemMetaObject(Connection pConn,	String name, String value, int itemId) throws SQLException {
		if (Utility.isSet(value)){
			ItemMetaData itemMetaD;
			itemMetaD = ItemMetaData.createValue();
			itemMetaD.setItemId(itemId);
			itemMetaD.setNameValue(name);
			itemMetaD.setValue(value);
			itemMetaD.setAddBy(className);
			itemMetaD.setModBy(className);
			itemMetaD = ItemMetaDataAccess.insert(pConn, itemMetaD);
			return itemMetaD;
		}
		return null;
	}

	private ItemAssocData createItemAssoc(Connection pConn, int catalogId, int item2Id,
			int item1Id, String itemAssocCd) throws SQLException {
		ItemAssocData iaD = ItemAssocData.createValue();
		iaD.setItem1Id(item1Id);
		iaD.setItem2Id(item2Id);
		iaD.setItemAssocCd(itemAssocCd);
		if (catalogId > 0)
			iaD.setCatalogId(catalogId);
		iaD.setAddBy(className);
		iaD.setModBy(className);
		ItemAssocDataAccess.insert(pConn, iaD);
		return iaD;
	}

	private ItemAssocData processItemAssoc(Connection pConn,
			int catalogId, int categoryId, int productId, String itemAssocCd)
			throws SQLException {
		DBCriteria dbc = new DBCriteria();
		dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, productId);
		dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, catalogId);
		dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, itemAssocCd);
		ItemAssocDataVector iaDV = ItemAssocDataAccess.select(pConn, dbc);

		ItemAssocData iaD = null;
		if (iaDV.size() > 0){
			iaD = (ItemAssocData) iaDV.get(0);
			if (iaD.getItem2Id() != categoryId){
				iaD.setItem2Id(categoryId);
				iaD.setModBy(className);
				ItemAssocDataAccess.update(pConn, iaD);
			}
		}else{
			iaD = createItemAssoc(pConn, catalogId, categoryId, productId, itemAssocCd);
		}
		return iaD;
	}

	private ItemMappingData processItemMapping(Connection conn, int busEntityId, int productId, String sku, String uom, String itemMappingCd, String itemDesc) throws SQLException, Exception {
		ItemMappingData imD = null;
		DBCriteria dbc = new DBCriteria();
		dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, productId);
		dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, itemMappingCd);
		ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(conn, dbc);
		if (itemMappingDV.size() > 1) {
			String errorMsg = "Error. Product has more then one " +
			(itemMappingCd.equals(RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER) ? "manfacturer" : "distributor") +
			". Product id: " +	productId;
			throw new Exception(errorMsg);
		}else if (itemMappingDV.size() == 1){
			imD = (ItemMappingData) itemMappingDV.get(0);
			if (!Utility.isEqual(imD.getItemNum(), sku)){
				imD.setItemNum(sku);
			}
			if (!Utility.isEqual(imD.getItemUom(), uom)){
				imD.setItemUom(uom);
			}
			if (!Utility.isEqual(imD.getShortDesc(), itemDesc)){
				imD.setShortDesc(itemDesc);
			}
			if (imD.getBusEntityId() != busEntityId){
				imD.setBusEntityId(busEntityId);
			}
			imD.setModBy(className);
			ItemMappingDataAccess.update(conn, imD);
		}else{
			imD = createItemMappingObject(conn, sku, null, null, busEntityId, productId, itemMappingCd, itemDesc);
		}
		return imD;
	}

	private ItemView getExistItemView(Connection conn, String distSku, String distUom) throws Exception{
		log.debug("SQL::storeCatalogId="+storeCatalogId);
		log.debug("SQL::distSku="+distSku);
		log.debug("SQL::"+selectExistItemSql);
		PreparedStatement pstmt = conn.prepareStatement(selectExistItemSql);
		pstmt.setInt(1, storeCatalogId);
		pstmt.setInt(2, storeCatalogId);
		pstmt.setString(3, distSku);
		pstmt.setString(4, distUom);
        ResultSet rs = pstmt.executeQuery();

        ItemView i = null;

        if (rs.next()) {
        	i = new ItemView();
        	i.productId = rs.getInt("product_id");
        	i.categoryId = rs.getInt("category_id");
        	i.manufacturerId = rs.getInt("manufacturer_id");
        	i.setDistUom(rs.getString("dist_uom"));
        	i.setDistSku(rs.getString("dist_sku"));
        	i.setManufName(rs.getString("manuf_name"));
        	i.setManufSku(rs.getString("manuf_sku"));
        	i.setDistItemDesc(rs.getString("short_desc"));
        	i.setCategoryName(rs.getString("category_name"));
        	if (!categoryMap.containsKey(i.getCategoryName()))
				categoryMap.put(i.getCategoryName(), new Integer(i.categoryId));

        	if (!manufactureMap.containsKey(i.getManufName()))
        	manufactureMap.put(i.getManufName(), new Integer(i.manufacturerId));
        	if (rs.next())
        		throw new Exception("Duplicated item found for distributor sku=" + distSku + " in catalog " + storeCatalogId);
        }
        rs.close();
        pstmt.close();
		return i;
	}

	private boolean isManagedItem(Connection pConn, int childItemId) throws SQLException{
		IdVector itemIdV =null;

        DBCriteria dbcItemAssoc=new DBCriteria();
        dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM1_ID,childItemId);
        dbcItemAssoc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.MANAGED_ITEM_PARENT);
        itemIdV = ItemAssocDataAccess.selectIdOnly(pConn,ItemAssocDataAccess.ITEM2_ID, dbcItemAssoc);
	    return itemIdV.size() > 0;
	}

	protected void doPostProcessing() throws Exception {
		if (conn != null){
			conn.commit();
			conn.close();
		}
		getTransactionObject().setException(this.getFormatedErrorMsgs());
	}

	/** Returns a report to be logged.  Should be human readable.
	 *
	 */
	public String getTranslationReport() {
		String temp = "Total item in file= " + lineCount;
		return temp + "\r\n" + getTransactionObject().getException();
	}

	class ItemView extends JanPakItemView {
		private static final long serialVersionUID = 1L;
		public int productId;
		public int categoryId;
		public int manufacturerId;

		public boolean equals(JanPakItemView item){
			return (Utility.isEqual(item.getDistSku(), getDistSku())
					&& Utility.isEqual(item.getDistUom(), getDistUom())
					&& Utility.isEqual(item.getDistItemDesc(), getDistItemDesc())
					&& Utility.isEqual(item.getManufName(), getManufName())
					&& Utility.isEqual(item.getManufSku(), getManufSku())
					&& Utility.isEqual(item.getCategoryName(), getCategoryName()) );
		}
	}
}

