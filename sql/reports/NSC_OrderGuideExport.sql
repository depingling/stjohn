select distinct
'1'                     as "Version Number",
c.catalog_status_cd     as "Status",
bus_store.bus_entity_id as "Store ID",
bus_store.short_desc    as "Store Name",
nvl(bus_account.erp_num,'#'||bus_account.bus_entity_id) 
                        as "Account Ref Num",
bus_account.short_desc  as "Account Name",
''                      as "Site Name",
''                      as "Site Ref Num",
c.loader_field          as "Catalog Key",
og.short_desc           as "Order Guide Name",
'CATALOG' as "Order Guide Type",
imap_distr.item_num     as "Dist SKU"

from clw_order_guide_structure ogs

inner join clw_order_guide og
on og.order_guide_id = ogs.order_guide_id
and og.order_guide_type_cd = 'ORDER_GUIDE_TEMPLATE'

inner join clw_catalog c
on c.catalog_id = og.catalog_id
and c.catalog_type_cd in ('ACCOUNT', 'ESTIMATOR', 'GENERIC_SHOPPING', 'SHOPPING')

inner join clw_catalog_assoc cat_assoc
on cat_assoc.catalog_id = c.catalog_id
and cat_assoc.catalog_assoc_cd = 'CATALOG_ACCOUNT'

inner join clw_bus_entity_assoc ba
on ba.bus_entity1_id = cat_assoc.bus_entity_id
and ba.bus_entity_assoc_cd = 'ACCOUNT OF STORE'

inner join clw_bus_entity bus_store
on bus_store.bus_entity_id = ba.bus_entity2_id
and bus_store.bus_entity_id = #STORE#

inner join clw_bus_entity bus_account
on bus_account.bus_entity_id = ba.bus_entity1_id

inner join clw_item_mapping imap_distr
on imap_distr.item_id = ogs.item_id
and imap_distr.item_mapping_cd = 'ITEM_DISTRIBUTOR'

union
select distinct
'1' as "Version Number",
ogsite.bus_entity_status_cd   as "Status",
bus_store.bus_entity_id as "Store ID",
bus_store.short_desc    as "Store Name",
nvl(bus_account.erp_num,'#'||bus_account.bus_entity_id) 
        as "Account Ref Num",
bus_account.short_desc as "Account Name",
ogsite.short_desc as "Site Name",
(select p.clw_value from clw_property p where p.bus_entity_id = ogsite.bus_entity_id and p.short_desc = 'SITE_REFERENCE_NUMBER')
        as "Site Ref Num",
'' as "Catalog Key",
og.short_desc as "Order Guide Name",
'SITE' as "Order Guide Type",
imap_distr.item_num     as "Dist SKU"

from clw_order_guide_structure ogs

inner join clw_order_guide og
on og.order_guide_id = ogs.order_guide_id
and og.order_guide_type_cd = 'SITE_ORDER_GUIDE_TEMPLATE'

inner join clw_bus_entity_assoc basite
on basite.bus_entity1_id = og.bus_entity_id
and basite.bus_entity_assoc_cd = 'SITE OF ACCOUNT'

inner join clw_bus_entity_assoc baacc
on baacc.bus_entity1_id = basite.bus_entity2_id
and baacc.bus_entity_assoc_cd = 'ACCOUNT OF STORE'

inner join clw_bus_entity bus_store
on bus_store.bus_entity_id = baacc.bus_entity2_id
and bus_store.bus_entity_id = #STORE#

inner join clw_bus_entity bus_account
on bus_account.bus_entity_id = baacc.bus_entity2_id

inner join clw_bus_entity ogsite
on ogsite.bus_entity_id = og.bus_entity_id

inner join clw_item_mapping imap_distr
on imap_distr.item_id = ogs.item_id
and imap_distr.item_mapping_cd = 'ITEM_DISTRIBUTOR'

