  SELECT DISTINCT item.item_status_cd AS "ITEM STATUS",
                  item.sku_num AS "SKU_NUM",
                  item.short_desc AS "ITEM_DESC",
                  bus_distr.bus_entity_id AS "DIST_ID",
                  bus_distr.short_desc AS "DIST NAME",
                  bus_account.bus_entity_id AS "ACCT ID",
                  bus_account.short_desc AS "ACCT NAME",
                  
--                  mcost.clw_value AS "COST",
 --                 mprice.clw_value AS "PRICE",
                  contitem.dist_cost as "COST",
                  contitem.amount as "PRICE",
                  
                  bus_manuf.short_desc AS "MANUFACTURER",
                  imap_manuf.item_num AS "MANU_SKU",
                  mpack.clw_value AS "PACK",
                  muom.clw_value AS "UOM",
                  msize.clw_value AS "ITEM_SIZE",
                  mcolor.clw_value AS "COLOR",
                  (SELECT subcat1.short_desc
                     FROM clw_item subcat1
                    WHERE subcat1.item_id = ia.item2_id)
                     AS "CATEGORY",
                  imap_distr.item_num AS "DIST_ITEM_NUM",
                  imap_distr.item_uom AS "DIST_ITEM_UOM",
                  imap_distr.item_pack AS "DIST_ITEM_PACK",
                  imap_distr.UOM_CONV_MULTIPLIER AS "UOM_CONV_MULTIPLIER"
    FROM clw_item item
         JOIN clw_catalog_structure catstr
            ON catstr.item_id = item.item_id
               AND catstr.catalog_structure_cd = 'CATALOG_PRODUCT'
         JOIN clw_catalog_assoc cat_assoc
            ON cat_assoc.catalog_id = catstr.catalog_id
         JOIN clw_bus_entity_assoc ba
            ON ba.bus_entity1_id = cat_assoc.bus_entity_id
               AND ba.bus_entity_assoc_cd = 'ACCOUNT OF STORE'
         JOIN clw_bus_entity bus_account
            ON bus_account.bus_entity_id = ba.bus_entity1_id
         JOIN clw_catalog c
            ON c.catalog_id = catstr.catalog_id
               AND (c.catalog_status_cd = 'ACTIVE'
                    OR c.catalog_status_cd = 'INACTIVE'
                      AND c.add_date >= SYSDATE - 365)
         JOIN clw_contract contract
            ON contract.catalog_id = catstr.catalog_id
               AND contract.contract_status_cd = 'ACTIVE'
         LEFT JOIN clw_contract_item contitem
            ON contitem.contract_id = contract.contract_id
               AND contitem.item_id = item.item_id
         LEFT JOIN clw_item_mapping imap_manuf
            ON imap_manuf.item_id = catstr.item_id
               AND imap_manuf.item_mapping_cd = 'ITEM_MANUFACTURER'
         LEFT JOIN clw_bus_entity bus_manuf
            ON bus_manuf.bus_entity_id = imap_manuf.bus_entity_id
         LEFT JOIN clw_item_mapping imap_distr
            ON     imap_distr.item_id = catstr.item_id
               AND imap_distr.bus_entity_id = catstr.bus_entity_id
               AND imap_distr.item_mapping_cd = 'ITEM_DISTRIBUTOR'
         LEFT JOIN clw_bus_entity bus_distr
            ON bus_distr.bus_entity_id = catstr.bus_entity_id
               AND bus_distr.bus_entity_status_cd = 'ACTIVE'
         LEFT JOIN clw_item_meta mpack
            ON mpack.item_id = catstr.item_id AND mpack.name_value = 'PACK'
         LEFT JOIN clw_item_meta muom
            ON muom.item_id = catstr.item_id AND muom.name_value = 'UOM'
         LEFT JOIN clw_item_meta msize
            ON msize.item_id = catstr.item_id AND msize.name_value = 'SIZE'
         LEFT JOIN clw_item_meta mcolor
            ON mcolor.item_id = catstr.item_id AND mcolor.name_value = 'COLOR'
/*
         LEFT JOIN clw_item_meta mcost
            ON mcost.item_id = catstr.item_id
               AND mcost.name_value = 'COST_PRICE'

        LEFT JOIN clw_item_meta mprice
            ON mprice.item_id = catstr.item_id
               AND mprice.name_value = 'LIST_PRICE'
*/
         JOIN clw_item_assoc ia
            ON     ia.item1_id = item.item_id
               AND ia.catalog_id = catstr.catalog_id
               AND ia.item_assoc_cd = 'PRODUCT_PARENT_CATEGORY'
   WHERE ba.bus_entity2_id = 1--#STORE_ID#
 ORDER BY "DIST_ID", "SKU_NUM"