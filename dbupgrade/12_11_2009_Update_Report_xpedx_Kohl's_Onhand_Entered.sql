UPDATE clw_generic_report 
SET SQL_TEXT = 'SELECT 
    site.short_desc AS "Site Name", 
    dist_im.item_num AS "Distributor Sku",
    cs.customer_sku_num AS "Customer Sku", 
    i.short_desc AS "Item Description", 
    ild.CLW_VALUE as "Par Value",
    il.qty_on_hand AS "On Hand"

FROM clw_bus_entity site 
INNER JOIN clw_inventory_level il
    ON site.bus_entity_Id = il.bus_entity_Id 
INNER JOIN clw_bus_entity_assoc ba
    ON ba.bus_entity1_id = site.bus_entity_id 
INNER JOIN clw_catalog_assoc ca
    ON ca.bus_entity_id = site.bus_entity_id 
INNER JOIN clw_catalog cat
    ON cat.catalog_id = ca.catalog_Id 
INNER JOIN clw_catalog_structure cs
    ON cs.catalog_id = cat.catalog_id  AND cs.item_id = il.item_id
INNER JOIN clw_item i 
    ON cs.item_id = i.item_id
INNER JOIN clw_item_mapping dist_im
    ON dist_im.bus_entity_id = cs.bus_entity_id AND dist_im.item_id = cs.item_id
INNER JOIN clw_bus_entity_assoc store
    ON ba.bus_entity2_id = store.bus_entity1_id

INNER JOIN CLW_INVENTORY_LEVEL_DETAIL ild
    ON ild.INVENTORY_LEVEL_ID = il.INVENTORY_LEVEL_ID
    AND ild.PERIOD = (SELECT fcf.period 
                        FROM clw_fiscal_calender_flat fcf 
                        WHERE fcf.bus_entity_id = ba.bus_entity2_id 
                                AND SYSDATE BETWEEN fcf.start_date AND fcf.end_date
                      )
WHERE
    site.bus_entity_type_cd = ''SITE'' 
AND site.bus_entity_status_cd = ''ACTIVE'' 
AND ba.bus_entity2_id = 176650
AND ca.catalog_assoc_cd = ''CATALOG_SITE''
AND cat.catalog_status_cd = ''ACTIVE''
AND store.bus_entity2_id = #STORE#

ORDER BY site.short_desc, dist_im.item_num'
WHERE NAME = 'xpedx Kohl''s Onhand Entered';
