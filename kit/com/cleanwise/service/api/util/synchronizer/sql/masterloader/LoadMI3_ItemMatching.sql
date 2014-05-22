CREATE TABLE tmp_good_uom
AS
     SELECT   clw_catalog_structure.item_id,
              MIN (clw_item_meta.clw_value) as clw_value
       FROM   clw_catalog_structure, clw_item_meta
      WHERE   (clw_catalog_structure.item_id = clw_item_meta.item_id)
              AND (clw_catalog_structure.catalog_structure_cd =
                      'CATALOG_PRODUCT')
              AND (clw_catalog_structure.catalog_id IN
                         (SELECT   DISTINCT store_catalog_id
                            FROM   #LOADING_TABLE_NAME#))
              AND (clw_item_meta.name_value = 'UOM')
   GROUP BY   clw_catalog_structure.item_id;

CREATE TABLE tmp_itmanskuuom
AS              
SELECT   clw_catalog_structure.catalog_id,
                       clw_item.item_id,
                       manuf.bus_entity_id AS manuf_id,
                       manuf.short_desc AS manuf_name,
                       map_to_manuf.item_num AS manuf_sku,
                       uom.clw_value uom
                FROM   clw_item,
                       clw_catalog_structure,
                       -- manufacturer mapping
                       clw_item_mapping map_to_manuf,
                       clw_bus_entity manuf,
                       tmp_good_uom uom
               WHERE       1 = 1
                       -- manufacturer mapping
                       AND (clw_item.item_id = map_to_manuf.item_id)
                       AND (manuf.bus_entity_id = map_to_manuf.bus_entity_id)
                       AND (clw_item.item_id = clw_catalog_structure.item_id)
                       AND (map_to_manuf.item_mapping_cd =
                               'ITEM_MANUFACTURER')
                       AND (clw_item.item_type_cd = 'PRODUCT')
                       AND (manuf.bus_entity_type_cd = 'MANUFACTURER')
                       AND clw_catalog_structure.catalog_id IN
                                (  SELECT   store_catalog_id
                                     FROM   #LOADING_TABLE_NAME#
                                 GROUP BY   store_catalog_id)
                       -- UOM
                       AND clw_item.item_id = uom.item_id(+);              

MERGE INTO   #LOADING_TABLE_NAME# loaded
     USING   tmp_itmanskuuom matched
        ON   (
              matched.catalog_id = loaded.store_catalog_id
              AND LOWER(TRIM(loaded.manufacturer)) IN 
                            	(SELECT LOWER(alias) 
                            	   FROM tmp_mnf_alias 
                            	  WHERE tmp_mnf_alias.manuf_id = matched.manuf_id)
              AND loaded.mfg_sku = matched.manuf_sku
              AND loaded.uom = matched.uom)
WHEN MATCHED
THEN
   UPDATE SET
      loaded.matched_item_id =
         DECODE
         (
            loaded.matched_item_id,
            NULL, matched.item_id,
            loaded.matched_item_id
         );