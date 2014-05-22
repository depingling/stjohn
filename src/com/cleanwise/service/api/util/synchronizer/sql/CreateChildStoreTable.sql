CREATE TABLE tmp_010
AS
   SELECT   clw_bus_entity.bus_entity_id
     FROM   clw_bus_entity, clw_property prop1, clw_property prop2
    WHERE       (clw_bus_entity.bus_entity_type_cd = 'STORE')
            AND (clw_bus_entity.bus_entity_id = prop1.bus_entity_id)
            AND (prop1.short_desc = 'PARENT_STORE_ID')
            AND (prop1.clw_value = TO_CHAR (#PARENT_STORE_ID#))
            AND (clw_bus_entity.bus_entity_id = prop2.bus_entity_id)
            AND (prop2.short_desc = 'ASSET_MANAGEMENT')
            AND (prop2.clw_value = 'true');

