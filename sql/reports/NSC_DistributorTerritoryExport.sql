select 
'1' as "Version Number",
dist.bus_entity_status_cd as "Status",
bus_store.bus_entity_id as "Store ID",
bus_store.short_desc as "Store Name",
dist.bus_entity_id as "Distributor ID",
dist.short_desc as "Distributor Name",
terr.postal_code as "Postal Code",
terr.bus_entity_terr_freight_cd as "Freight Code"

from 
clw_bus_entity_terr terr

join clw_bus_entity dist
on dist.bus_entity_id = terr.bus_entity_id
and dist.bus_entity_type_cd = 'DISTRIBUTOR'

join clw_bus_entity_assoc store_assoc
on store_assoc.bus_entity1_id = dist.bus_entity_id

join clw_bus_entity bus_store
on bus_store.bus_entity_id = store_assoc.bus_entity2_id
and store_assoc.bus_entity2_id = #STORE#
and store_assoc.bus_entity_assoc_cd = 'DISTRIBUTOR OF STORE'

