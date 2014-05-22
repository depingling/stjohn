select
'1'                         as "Version Number",
fr.freight_table_status_cd  as "Status",
bus_store.bus_entity_id     as "Store ID",
bus_store.short_desc        as "Store Name",
fr.freight_table_id         as "Freight Table Key - id",
fr.short_desc               as "Freight Table Name" ,
fr.freight_table_type_cd    as "Table Type",
frcr.short_desc             as "Charge Description",
frcr.freight_criteria_type_cd as "Type",
frcr.runtime_type_cd        as "Runtime Type",
frcr.charge_cd              as "Charge Type",
frcr.lower_amount           as "Begin Range",
frcr.higher_amount          as "End Range",
nvl(frcr.freight_amount, frcr.handling_amount) 
                            as "Charge Amount",
frcr.ui_order               as "UI Order",
frcr.freight_handler_id     as "Freight Handler Key",
frh.short_desc              as "Freight Handler Name",
prop.clw_value              as "Freight Handler EDI Routing Cd"

from clw_freight_table_criteria frcr

join clw_freight_table fr
on fr.freight_table_id = frcr.freight_table_id

join clw_bus_entity bus_store 
on bus_store.bus_entity_id = #STORE#
and bus_store.bus_entity_id = fr.store_id

left join clw_bus_entity frh
on frh.bus_entity_id = frcr.freight_handler_id

left join clw_property prop
on prop.bus_entity_id = frh.bus_entity_id
and prop.short_desc = 'FR_ROUTING_CD'
order by fr.short_desc