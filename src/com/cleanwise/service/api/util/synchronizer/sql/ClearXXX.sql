DELETE FROM   clw_asset_assoc
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);

DELETE FROM   clw_asset_master_assoc
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);

DELETE FROM   clw_asset_property
      WHERE   clw_asset_property.asset_id IN (SELECT   asset_id
                                                FROM   clw_asset
                                               WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases));
      
DELETE FROM   clw_asset_content
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);

DELETE FROM   clw_asset_content
      WHERE asset_id IN (
        SELECT asset_id FROM clw_asset
        WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases));


DELETE FROM   clw_content
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);

DELETE FROM   clw_asset
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);
      
       
DELETE FROM   clw_bus_entity_pc_assoc
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);

DELETE FROM   clw_phone
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);    

DELETE FROM   clw_address
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);    

DELETE FROM   clw_email
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);    
      
DELETE FROM   clw_bus_entity_assoc
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);     

DELETE FROM   clw_item_mapping
    WHERE CLW_ITEM_MAPPING.BUS_ENTITY_ID IN
    (SELECT BUS_ENTITY_ID FROM clw_bus_entity         
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases));   

DELETE FROM   clw_email
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);   

DELETE FROM   clw_email
    WHERE BUS_ENTITY_ID IN
    (SELECT BUS_ENTITY_ID FROM clw_bus_entity         
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases));   

DELETE FROM   clw_address
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);   

DELETE FROM   clw_address
    WHERE BUS_ENTITY_ID IN
    (SELECT BUS_ENTITY_ID FROM clw_bus_entity         
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases));   

DELETE FROM   clw_phone
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);   

DELETE FROM   clw_phone
    WHERE BUS_ENTITY_ID IN
    (SELECT BUS_ENTITY_ID FROM clw_bus_entity         
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases));   

DELETE FROM   clw_property
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);   

DELETE FROM   clw_property
    WHERE BUS_ENTITY_ID IN
    (SELECT BUS_ENTITY_ID FROM clw_bus_entity         
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases));   
           
DELETE FROM   clw_bus_entity
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);   
