CREATE TABLE tmp_good_uom
AS
     SELECT   clw_item_meta.item_id,
              clw_item_meta.name_value,
              clw_item_meta.clw_value
       FROM   clw_item_meta, clw_catalog_structure
      WHERE   clw_item_meta.item_id = clw_catalog_structure.item_id
              AND clw_catalog_structure.catalog_id IN
                       (SELECT   DISTINCT store_catalog_id
                          FROM   #LOADING_TABLE_NAME#)
              AND clw_item_meta.name_value = 'UOM'
              AND clw_item_meta.clw_value IN
                       (  SELECT   DISTINCT uom FROM #LOADING_TABLE_NAME#)
              AND clw_item_meta.item_id NOT IN
                       (  SELECT   clw_item.item_id
                            FROM   clw_item, clw_item_meta
                           WHERE   (clw_item.item_id = clw_item_meta.item_id)
                                   AND (clw_item_meta.name_value = 'UOM')
                        GROUP BY   clw_item.item_id, clw_item_meta.name_value
                          HAVING   (COUNT (DISTINCT clw_item_meta.clw_value) >
                                       1))
   GROUP BY   clw_item_meta.item_id,
              clw_item_meta.name_value,
              clw_item_meta.clw_value;

