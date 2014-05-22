SELECT   line_number,
		 TO_NUMBER(clw_property.clw_value) parent_store_id,
         manufacturer,
         mfg_sku,
         store_id
  FROM   #LOADING_TABLE_NAME#, 
  		 clw_property 
 WHERE   (#LOADING_TABLE_NAME#.store_id = clw_property.bus_entity_id)
         AND (clw_property.short_desc = 'PARENT_STORE_ID')
         AND EXISTS
               (SELECT   *
                  FROM   clw_asset, clw_asset_assoc
                 WHERE   (   clw_asset.asset_id = clw_asset_assoc.asset_id
                         AND clw_asset_assoc.bus_entity_id =
                             clw_property.clw_value)
                         
                         --AND (clw_asset.manuf_name = #LOADING_TABLE_NAME#.manufacturer)
                         AND (LOWER(TRIM(#LOADING_TABLE_NAME#.manufacturer)) IN 
                            	(SELECT LOWER(alias) 
                            	   FROM tmp_mnf_alias 
                            	  WHERE tmp_mnf_alias.manuf_id = clw_asset.manuf_id ))
                            	  
                         AND (clw_asset.manuf_sku = #LOADING_TABLE_NAME#.mfg_sku)
                         AND (clw_asset.asset_type_cd = 'MASTER_ASSET')
                         AND (clw_asset_assoc.asset_assoc_cd = 'ASSET_STORE'))                         