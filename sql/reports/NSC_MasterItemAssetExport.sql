select 
'1' as "Version Number",
'FALSE' as "Asset",
i.item_status_cd as "Status",
bus_store.bus_entity_id as "Store ID",
bus_store.short_desc as "Store Name",
imap_distr.item_num as "Dist SKU",
imap_manuf.item_num as "Mfg SKU",
manuf.short_desc as "Manufacturer",
ddistr.short_desc as "Distributor",
(select clw_value from clw_item_meta m where m.item_id = i.item_id and m.name_value = 'PACK')
        as "Pack",
(select clw_value from clw_item_meta m where m.item_id = i.item_id and m.name_value = 'UOM')
        as "UOM",
categ.short_desc   as "Category Name",
(select subcat1.short_desc from clw_item subcat1 where subcat1.item_id = subcat1a.item2_id)
        as "SubCat1",
(select subcat2.short_desc from clw_item subcat2 where subcat2.item_id = subcat2a.item2_id)        
        as "SubCat2",
(select subcat3.short_desc from clw_item subcat3 where subcat3.item_id = subcat3a.item2_id)                
        as "SubCat3",
(select short_desc from clw_item where item_id = multipr.item_id)        
        as "Multi Product Name",
(select clw_value from clw_item_meta m where m.item_id = i.item_id and m.name_value = 'SIZE')
        as "Item Size",
i.long_desc  as "Long description",
i.short_desc as "Short description",
(select clw_value from clw_item_meta m where m.item_id = i.item_id and m.name_value = 'UPC_NUM')
        as "Product UPC",
(select clw_value from clw_item_meta m where m.item_id = i.item_id and m.name_value = 'PKG_UPC_NUM')
        as "Pack UPC",
(select clw_value from clw_item_meta m where m.item_id = i.item_id and m.name_value = 'UNSPSC_CD')       
        as "UNSPSC Code",
(select clw_value from clw_item_meta m where m.item_id = i.item_id and m.name_value = 'COLOR')
        as "Color",
(select clw_value from clw_item_meta m where m.item_id = i.item_id and m.name_value = 'SHIP_WEIGHT')
        as "Shipping Weight",
(select clw_value from clw_item_meta m where m.item_id = i.item_id and m.name_value = 'WEIGHT_UNIT')
        as "Weight Unit",
(select clw_value from clw_item_meta m where m.item_id = i.item_id and m.name_value = 'NSN')
        as "NSN",
(select clw_value from clw_item_meta m where m.item_id = i.item_id and m.name_value = 'CUBE_SIZE')        
        as "Shipping cubic Size",
nvl((select (case when(upper(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
            from clw_item_meta m where m.item_id = i.item_id and m.name_value = 'HAZMAT'), 'FALSE')
       as "HAZMAT",
grlist.green_sert  as "Certified Companies",
''     as "Image",
''     as "MSDS",
''     as "Specification",

'' as "Asset Name",
'' as "Model Number",
'' as "Associated Document 1",
'' as "Associated Document 2",
'' as "Associated Document 3"

from clw_item i

left join clw_item_mapping imap_distr
on imap_distr.item_id = i.item_id
and imap_distr.item_mapping_cd = 'ITEM_DISTRIBUTOR'

left join clw_item_mapping imap_manuf
on imap_manuf.item_id = i.item_id
and imap_manuf.item_mapping_cd = 'ITEM_MANUFACTURER'

join clw_bus_entity manuf
on manuf.bus_entity_id = imap_manuf.bus_entity_id

left join clw_bus_entity ddistr
on ddistr.bus_entity_id = imap_distr.bus_entity_id

join clw_bus_entity_assoc store_assoc
on store_assoc.bus_entity1_id = ddistr.bus_entity_id
and store_assoc.bus_entity_assoc_cd = 'DISTRIBUTOR OF STORE'

join clw_bus_entity bus_store
on bus_store.bus_entity_id = store_assoc.bus_entity2_id
and bus_store.bus_entity_id = #STORE#

join clw_item_assoc ia
on ia.item1_id = i.item_id 
and ia.catalog_id = (
select t.catalog_id from clw_catalog_assoc t where t.catalog_id in (
select catalog_id from clw_catalog where catalog_type_cd = 'STORE')
and t.bus_entity_id = #STORE#
and t.catalog_assoc_cd = 'CATALOG_STORE')

join clw_item categ
on categ.item_id = ia.item2_id

left join clw_item_assoc subcat1a
on subcat1a.item1_id = categ.item_id
and subcat1a.item_assoc_cd = 'CATEGORY_PARENT_CATEGORY'

left join clw_item_assoc subcat2a
on subcat2a.item1_id = subcat1a.item2_id
and subcat2a.item_assoc_cd = 'CATEGORY_PARENT_CATEGORY'

left join clw_item_assoc subcat3a
on subcat3a.item1_id = subcat2a.item2_id
and subcat3a.item_assoc_cd = 'CATEGORY_PARENT_CATEGORY'

left join clw_catalog_structure multipr
on multipr.catalog_id = ia.catalog_id
and multipr.catalog_structure_cd = 'CATALOG_MULTI_PRODUCT'

left join 
(SELECT ii.item_id, ii.short_desc,
        (SELECT Trim (WM_CONCAT(' '||green.short_desc)) 
           from clw_bus_entity green, clw_item_mapping ims 
              WHERE ims.item_id = ii.item_id 
                   AND ims.ITEM_MAPPING_CD = 'ITEM_CERTIFIED_COMPANY' 
                   AND green.bus_entity_id = ims.bus_entity_id
         GROUP BY ims.item_id) green_sert
 FROM clw_item ii ) grlist
 on grlist.item_id = i.item_id

union
select 
'1' as "Version Number",
'TRUE' as "Asset",
a.status_cd as "Status",
bus_store.bus_entity_id as "Store ID",
bus_store.short_desc as "Store Name",
'' as "Dist SKU",
a.manuf_sku      as "Mfg SKU",
a.manuf_name     as "Manufacturer",
'' as "Distributor",
'' as "Pack",
'' as "UOM",
'' as "Category Name",
'' as "SubCat1",
'' as "SubCat2",
'' as "SubCat3",
'' as "Multi Product Name",
'' as "Item Size",
'' as "Long description",
'' as "Short description",
'' as "Product UPC",
'' as "Pack UPC",
'' as "UNSPSC Code",
'' as "Color",
'' as "Shipping Weight",
'' as "Weight Unit",
'' as "NSN",
'' as "Shipping cubic Size",
'' as "HAZMAT",
'' as "Certified Companies",
'' as "Image",
'' as "MSDS",
'' as "Specification",
a.short_desc      as "Asset Name",
a.model_number    as "Model Number",
'' as "Associated Document 1",
'' as "Associated Document 2",
'' as "Associated Document 3"

from clw_asset a

join clw_asset_assoc a_ass
on a_ass.asset_id = a.asset_id
and a_ass.asset_assoc_cd = 'ASSET_STORE'

join clw_bus_entity bus_store
on bus_store.bus_entity_id = a_ass.bus_entity_id
and bus_store.bus_entity_id = #STORE#


where 
a.asset_type_cd = 'MASTER_ASSET'
