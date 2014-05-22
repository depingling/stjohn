SELECT   line_number,
		 TO_NUMBER(clw_property.clw_value) parent_store_id,
         manufacturer,
         mfg_sku,
         uom,
         store_id
  FROM   #LOADING_TABLE_NAME#, 
  		 clw_property 
 WHERE   (#LOADING_TABLE_NAME#.store_id = clw_property.bus_entity_id)
         AND (clw_property.short_desc = 'PARENT_STORE_ID')
         AND EXISTS
               (
				SELECT   *
				  FROM   clw_item,
				         clw_item_mapping,
				         clw_catalog_structure,
				         clw_catalog,
				         clw_catalog_assoc
				 WHERE       (clw_item.item_id = clw_item_mapping.item_id)
				         AND (clw_item.item_id = clw_catalog_structure.item_id)
				         AND (clw_catalog.catalog_id = clw_catalog_structure.catalog_id)
				         AND (clw_catalog.catalog_id = clw_catalog_assoc.catalog_id)
				         AND (clw_item.item_type_cd = 'PRODUCT')
				         AND (clw_item_mapping.item_mapping_cd = 'ITEM_MANUFACTURER')
				         AND (clw_item.add_by = 'yyy')
				         AND (clw_catalog_structure.catalog_structure_cd = 'CATALOG_PRODUCT')
				         AND (clw_catalog.catalog_type_cd = 'STORE')
				         AND (clw_catalog_assoc.catalog_assoc_cd = 'CATALOG_STORE')
				         AND (clw_catalog_assoc.bus_entity_id = clw_property.clw_value)
                         AND (LOWER(TRIM(#LOADING_TABLE_NAME#.manufacturer)) IN 
                            	(SELECT LOWER(alias) 
                            	   FROM tmp_mnf_alias 
                            	  WHERE tmp_mnf_alias.manuf_id = clw_item_mapping.bus_entity_id))
                         AND (#LOADING_TABLE_NAME#.mfg_sku = clw_item_mapping.item_num)
                         AND EXISTS
    				             (SELECT   clw_item_meta.*
				                    FROM   clw_item_meta
				                   WHERE   (clw_item_meta.item_id = clw_item.item_id)
				                            AND (clw_item_meta.name_value = 'UOM')
				                            AND (#LOADING_TABLE_NAME#.uom = clw_item_meta.clw_value))
               )
