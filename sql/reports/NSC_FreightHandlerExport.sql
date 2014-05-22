select 
'1'                        as "Version Number",
frh.bus_entity_status_cd   as "Status",
store_assoc.bus_entity2_id as "Store ID",
frh.bus_entity_id          as "Freight Handler ID",
frh.short_desc             as "Freight Handler Name", 
(select p.clw_value from clw_property p where p.bus_entity_id = frh.bus_entity_id and p.short_desc = 'FR_ROUTING_CD')
                           as "EDI Routing Code",
nvl((select (case when(upper(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end)
        from clw_property p where p.bus_entity_id = frh.bus_entity_id and p.short_desc = 'FR_ON_INVOICE_CD'), 'FALSE') 
                           as "Accept Freight Cost",
addr.address1              as "Address 1",
addr.address2              as "Address 2",
addr.address3              as "Address 3",
addr.city                  as "City",
addr.state_province_cd     as "State",
addr.postal_code           as "Postal Code",
addr.country_cd            as "Country"

from clw_bus_entity frh

join clw_bus_entity_assoc store_assoc
on store_assoc.bus_entity1_id = frh.bus_entity_id

left join clw_address addr
on addr.bus_entity_id = frh.bus_entity_id

where frh.bus_entity_type_cd = 'FREIGHT_HANDLER'
and store_assoc.bus_entity2_id = #STORE#
