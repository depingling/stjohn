       
DELETE FROM   clw_phone
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);    

DELETE FROM   clw_address
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);    

DELETE FROM   clw_email
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);    
      
DELETE FROM   clw_bus_entity_assoc
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);     
           
      
DELETE FROM   clw_asset_assoc
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);


DELETE FROM   clw_asset_property
      WHERE   clw_asset_property.asset_id IN (SELECT   asset_id
                                                FROM   clw_asset
                                               WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases));
      
DELETE FROM   clw_asset_content
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);

DELETE FROM   clw_asset_content
      WHERE   asset_id in (SELECT asset_id FROM clw_asset Where add_by IN (SELECT user_name FROM tmp_user_aliases)); 


DELETE FROM   clw_content
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);

DELETE FROM   clw_asset_master_assoc
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);

update clw_asset set parent_id = null
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);

DELETE FROM   clw_asset
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);
      
DELETE FROM clw_item_mapping
WHERE add_by IN (SELECT user_name FROM tmp_user_aliases);      

DELETE FROM   clw_item_mapping
WHERE CLW_ITEM_MAPPING.BUS_ENTITY_ID IN (SELECT bus_entity_id FROM clw_bus_entity Where add_by IN (SELECT user_name FROM tmp_user_aliases));       
      
DELETE FROM   clw_bus_entity
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);


DELETE FROM   clw_bus_entity_pc_assoc
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);
      
DELETE FROM clw_catalog_structure      
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);

DELETE FROM clw_item_assoc      
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);

DELETE FROM clw_item_assoc      
    WHERE ITEM1_ID IN
     (SELECT ITEM_ID
      FROM clw_item     
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases));

DELETE FROM clw_item_assoc      
    WHERE ITEM2_ID IN
     (SELECT ITEM_ID
      FROM clw_item     
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases));

DELETE FROM clw_item_meta      
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);
      
DELETE FROM clw_item_meta
    WHERE ITEM_ID IN
     (SELECT ITEM_ID
      FROM clw_item     
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases));
      

DELETE FROM CLW_ITEM_MASTER_ASSOC      
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);

DELETE FROM CLW_ITEM_MASTER_ASSOC
    WHERE PARENT_MASTER_ITEM_ID IN
     (SELECT ITEM_ID
      FROM clw_item     
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases));

DELETE FROM CLW_ITEM_MASTER_ASSOC
    WHERE CHILD_MASTER_ITEM_ID IN
     (SELECT ITEM_ID
      FROM clw_item     
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases));

      
DELETE FROM clw_item      
      WHERE   add_by IN (SELECT user_name FROM tmp_user_aliases);

