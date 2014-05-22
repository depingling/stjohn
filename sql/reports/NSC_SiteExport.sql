select distinct
'1'                       as "Version Number", 
site.bus_entity_status_cd as "Status",
bus_store.bus_entity_id   as "Store Id",
bus_store.short_desc      as "Store Name",
nvl(bus_account.erp_num,'#'||bus_account.bus_entity_id)  as "Account Ref Num",
bus_account.short_desc    as "Account Name",
site.short_desc           as "Site Name",
site_ref.clw_value        as "Site Ref Num",  
nvl((case when(UPPER(tax.clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end), 'FALSE')  
                          as "Taxable",
nvl((case when(UPPER(invsh.clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end), 'FALSE')
                          as "Enable Inventory Shopping",
nvl((case when(UPPER(share_og.clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end), 'FALSE')                      
                          as "Share buyer order guides",                      
addr.name1                as "First Name", 
addr.name2                as "Last Name", 
addr.address1             as "Address 1", 
addr.address2             as "Address 2", 
addr.address3             as "Address 3", 
addr.address4             as "Address 4", 
addr.city                 as "City", 
addr.state_province_cd    as "State", 
addr.postal_code          as "Postal Code", 
addr.country_cd           as "Country",
shipmess.clw_value        as "Shipping Message",
ogcomm.clw_value          as "Order Guide Comments",
cat.loader_field  as "Catalog Key",
site.bus_entity_id as "Site Id"

from clw_bus_entity site

join clw_bus_entity_assoc acc_assoc
on acc_assoc.bus_entity1_id = site.bus_entity_id
and acc_assoc.bus_entity_assoc_cd = 'SITE OF ACCOUNT'

join clw_bus_entity bus_account
on bus_account.bus_entity_id = acc_assoc.bus_entity2_id

join clw_bus_entity_assoc store_assoc
on store_assoc.bus_entity1_id = bus_account.bus_entity_id
and store_assoc.bus_entity_assoc_cd = 'ACCOUNT OF STORE'

join clw_bus_entity bus_store
on bus_store.bus_entity_id = store_assoc.bus_entity2_id
and bus_store.bus_entity_id = #STORE#

left join clw_property site_ref
on site_ref.bus_entity_id = site.bus_entity_id
and site_ref.short_desc = 'SITE_REFERENCE_NUMBER' 
and site_ref.property_type_cd = 'EXTRA'

left join clw_property tax
on tax.bus_entity_id = site.bus_entity_id
and tax.short_desc = 'TAXABLE_INDICATOR' 
and tax.property_type_cd = 'TAXABLE_INDICATOR'

left join clw_property invsh
on invsh.bus_entity_id = site.bus_entity_id
and invsh.short_desc = 'INVENTORY_SHOPPING' 
and invsh.property_type_cd = 'INVENTORY_SHOPPING'

left join clw_property share_og
on share_og.bus_entity_id = site.bus_entity_id
and share_og.short_desc = 'SHARE_ORDER_GUIDES'
and share_og.property_type_cd = 'SHARE_ORDER_GUIDES'

join clw_address addr
on addr.bus_entity_id = site.bus_entity_id
   
left join clw_property shipmess
on shipmess.bus_entity_id = site.bus_entity_id
and shipmess.short_desc = 'SITE_SHIP_MSG'
and shipmess.property_type_cd = 'SITE_SHIP_MSG'

left join clw_property ogcomm
on ogcomm.bus_entity_id = site.bus_entity_id
and ogcomm.short_desc = 'SITE_COMMENTS'
and ogcomm.property_type_cd = 'SITE_COMMENTS'


join clw_catalog_assoc cat_ass
on cat_ass.bus_entity_id = site.bus_entity_id
join clw_catalog cat
on cat.catalog_id = cat_ass.catalog_id

where site.bus_entity_type_cd = 'SITE'
and bus_store.bus_entity_id = #STORE#

order by site.short_desc