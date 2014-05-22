select 
'1' as "Version Number",
bus_distr.bus_entity_status_cd as "Status",
store_assoc.bus_entity2_id     as "Store ID",
bus_store.short_desc           as "Store Name",
addr.postal_code     as "Postal Code",
bus_distr.bus_entity_id as "Distributor ID",
bus_distr.short_desc as "Distributor Name",
frh.bus_entity_id as "Freight Handler ID",
frh.short_desc as "Freight Handler Name",
cat_assoc.bus_entity_id as "Account ID",
cat.catalog_id as "Catalog ID"

from clw_bus_entity bus_distr
join clw_bus_entity_assoc store_assoc
on store_assoc.bus_entity1_id = bus_distr.bus_entity_id
and store_assoc.bus_entity_assoc_cd = 'DISTRIBUTOR OF STORE'

join clw_bus_entity bus_store
on bus_store.bus_entity_id = store_assoc.bus_entity2_id

join clw_freight_table frt
on frt.distributor_id = bus_distr.bus_entity_id

join clw_freight_table_criteria frtc
on frtc.freight_table_id = frt.freight_table_id

join clw_bus_entity frh
on frh.bus_entity_id = frtc.freight_handler_id

join clw_contract contr
on contr.freight_table_id = frt.freight_table_id

join clw_catalog cat
on cat.catalog_id = contr.catalog_id

join clw_catalog_assoc cat_assoc
on cat_assoc.catalog_id = cat.catalog_id
and cat_assoc.catalog_assoc_cd = 'CATALOG_ACCOUNT'

join clw_address addr
on addr.bus_entity_id = bus_store.bus_entity_id
and addr.primary_ind = 1

where 
bus_store.bus_entity_id = #STORE# and 
bus_distr.bus_entity_type_cd = 'DISTRIBUTOR'

order by store_assoc.bus_entity2_id, bus_distr.bus_entity_id, cat.catalog_id
