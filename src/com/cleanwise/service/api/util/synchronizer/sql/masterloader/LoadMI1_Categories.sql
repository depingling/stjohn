MERGE INTO   #LOADING_TABLE_NAME#
     USING   (SELECT   clw_catalog_assoc.catalog_id,
                       clw_catalog_assoc.bus_entity_id
                FROM   clw_catalog_assoc, clw_catalog
               WHERE   (clw_catalog.catalog_id = clw_catalog_assoc.catalog_id)
                       AND (clw_catalog_assoc.catalog_assoc_cd =
                               'CATALOG_STORE')
                       AND (clw_catalog.catalog_type_cd = 'STORE'))
             store_catalogs
        ON   (#LOADING_TABLE_NAME#.store_id = store_catalogs.bus_entity_id)
WHEN MATCHED
THEN
   UPDATE SET #LOADING_TABLE_NAME#.store_catalog_id = store_catalogs.catalog_id;

CREATE TABLE tmp_itemcat1
AS
     SELECT   category_name, store_catalog_id
       FROM   #LOADING_TABLE_NAME#
      WHERE   category_name NOT IN
                    (SELECT   clw_item.short_desc
                       FROM   clw_item, clw_catalog_structure
                      WHERE   (clw_item.item_id = clw_catalog_structure.item_id)
                              AND (clw_item.item_type_cd = 'CATEGORY')
                              AND (clw_item.long_desc IS NULL)
                              AND (clw_catalog_structure.catalog_id =
                                      #LOADING_TABLE_NAME#.store_catalog_id))
   GROUP BY   category_name, store_catalog_id;

CREATE TABLE tmp_itemcat2
AS
   SELECT   clw_item_seq.NEXTVAL AS new_item_id,
            category_name,
            store_catalog_id
     FROM   tmp_itemcat1;

INSERT   ALL
  INTO   clw_item
         (
             item_id,
             short_desc,
             sku_num,
             long_desc,
             eff_date,
             exp_date,
             item_type_cd,
             item_status_cd,
             item_order_num,
             add_date,
             add_by,
             mod_date,
             mod_by
         )
VALUES
         (
             new_item_id,
             category_name,
             0,
             NULL,
             NULL,
             NULL,
             'CATEGORY',
             'AVAILABLE',
             0,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
  INTO   clw_catalog_structure
         (
             catalog_structure_id,
             catalog_id,
             bus_entity_id,
             catalog_structure_cd,
             item_id,
             customer_sku_num,
             short_desc,
             eff_date,
             exp_date,
             status_cd,
             add_date,
             add_by,
             mod_date,
             mod_by,
             cost_center_id,
             tax_exempt,
             item_group_id,
             special_permission
         )
VALUES
         (
             clw_catalog_structure_seq.NEXTVAL,
             store_catalog_id,
             NULL,
             'CATALOG_CATEGORY',
             new_item_id,
             NULL,
             NULL,
             NULL,
             NULL,
             'ACTIVE',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             0,
             NULL,
             NULL,
             NULL
         )
   SELECT   new_item_id, category_name, store_catalog_id FROM tmp_itemcat2;