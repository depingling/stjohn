delete from clw_bus_entity_assoc where bus_entity2_id in
(select bus_entity_id from clw_property where property_type_cd = 'STORE_TYPE_CD' and clw_value = 'MANUFACTURER');

delete from clw_bus_entity_assoc where bus_entity1_id in
(select bus_entity_id from clw_property where property_type_cd = 'STORE_TYPE_CD' and clw_value = 'MANUFACTURER');

delete from clw_item_mapping_assoc where item_mapping1_id in 
(select item_mapping_id from clw_item_mapping where bus_entity_id in
(select bus_entity_id from clw_property where property_type_cd = 'STORE_TYPE_CD' and clw_value = 'MANUFACTURER'));

delete from clw_item_mapping_assoc where item_mapping2_id in 
(select item_mapping_id from clw_item_mapping where bus_entity_id in
(select bus_entity_id from clw_property where property_type_cd = 'STORE_TYPE_CD' and clw_value = 'MANUFACTURER'));

delete from clw_item_mapping where bus_entity_id in 
(select bus_entity_id from clw_property where property_type_cd = 'STORE_TYPE_CD' and clw_value = 'MANUFACTURER');

delete from clw_address where bus_entity_id in
(select bus_entity_id from clw_property where property_type_cd = 'STORE_TYPE_CD' and clw_value = 'MANUFACTURER');

delete from clw_phone where bus_entity_id in
(select bus_entity_id from clw_property where property_type_cd = 'STORE_TYPE_CD' and clw_value = 'MANUFACTURER');

delete from clw_email where bus_entity_id in 
(select bus_entity_id from clw_property where property_type_cd = 'STORE_TYPE_CD' and clw_value = 'MANUFACTURER');

delete from clw_property where bus_entity_id in 
(select bus_entity_id from clw_property where property_type_cd = 'STORE_TYPE_CD' and clw_value = 'MANUFACTURER');

delete from clw_warranty_assoc where bus_entity_id in
(select bus_entity_id from clw_property where property_type_cd = 'STORE_TYPE_CD' and clw_value = 'MANUFACTURER');

delete from clw_trading_partner_assoc where bus_entity_id in 
(select bus_entity_id from clw_property where property_type_cd = 'STORE_TYPE_CD' and clw_value = 'MANUFACTURER');

delete from clw_catalog_assoc where bus_entity_id in 
(select bus_entity_id from clw_property where property_type_cd = 'STORE_TYPE_CD' and clw_value = 'MANUFACTURER');

delete from clw_bus_entity where bus_entity_id in 
(select bus_entity_id from clw_property where property_type_cd = 'STORE_TYPE_CD' and clw_value = 'MANUFACTURER');

commit;
