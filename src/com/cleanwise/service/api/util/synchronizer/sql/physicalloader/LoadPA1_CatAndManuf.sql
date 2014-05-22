CREATE TABLE tmp_2001
AS
     SELECT   loaded.category_name, loaded.store_id
       FROM   #LOADING_TABLE_NAME# loaded
      WHERE   NOT EXISTS
                 (SELECT   *
                    FROM   clw_asset, clw_asset_assoc
                   WHERE       (clw_asset.asset_id = clw_asset_assoc.asset_id)
                           AND (clw_asset_assoc.asset_assoc_cd = 'ASSET_STORE')
                           AND (clw_asset.asset_type_cd = 'CATEGORY')
                           AND loaded.category_name = clw_asset.short_desc
                           AND loaded.store_id = clw_asset_assoc.bus_entity_id)
   GROUP BY   category_name, store_id;

CREATE TABLE tmp_2002
AS
   SELECT   clw_asset_seq.NEXTVAL AS new_asset_id, category_name, store_id
     FROM   tmp_2001;

INSERT   ALL
  INTO   clw_asset
         (
             asset_id,
             short_desc,
             asset_type_cd,
             parent_id,
             status_cd,
             serial_num,
             asset_num,
             manuf_id,
             manuf_name,
             manuf_sku,
             manuf_type_cd,
             add_by,
             add_date,
             mod_by,
             mod_date,
             model_number,
             master_asset_id
         )
VALUES
         (
             new_asset_id,
             category_name,
             'CATEGORY',
             0,
             'ACTIVE',
             NULL,
             NULL,
             0,
             NULL,
             NULL,
             NULL,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             NULL,
             NULL
         )
  INTO   clw_asset_assoc
         (
             asset_assoc_id,
             asset_id,
             bus_entity_id,
             asset_assoc_cd,
             asset_assoc_status_cd,
             add_date,
             add_by,
             mod_date,
             mod_by,
             item_id
         )
VALUES
         (
             clw_asset_assoc_seq.NEXTVAL,
             new_asset_id,
             store_id,
             'ASSET_STORE',
             'ACTIVE',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             0
         )
   SELECT   new_asset_id, category_name, store_id FROM tmp_2002;



CREATE TABLE tmp_2003
AS
     SELECT   manufacturer, store_id
       FROM   #LOADING_TABLE_NAME# loaded
      WHERE   NOT EXISTS
                 (SELECT   *
                    FROM   clw_bus_entity, clw_bus_entity_assoc
                   WHERE   (clw_bus_entity.bus_entity_id =
                               clw_bus_entity_assoc.bus_entity1_id)
                           AND (clw_bus_entity.bus_entity_type_cd =
                                   'MANUFACTURER')
                           AND (clw_bus_entity_assoc.bus_entity_assoc_cd =
                                   'MANUFACTURER OF STORE')
                           AND (loaded.manufacturer = clw_bus_entity.short_desc)
                           AND (loaded.store_id =
                                   clw_bus_entity_assoc.bus_entity2_id))
   GROUP BY   manufacturer, store_id;

CREATE TABLE tmp_2004
AS
   SELECT   clw_bus_entity_seq.NEXTVAL AS new_bus_entity_id,
            manufacturer,
            store_id
     FROM   tmp_2003;

INSERT   ALL
  INTO   clw_bus_entity
         (
             bus_entity_id,
             short_desc,
             long_desc,
             eff_date,
             exp_date,
             bus_entity_type_cd,
             bus_entity_status_cd,
             workflow_role_cd,
             locale_cd,
             erp_num,
             add_date,
             add_by,
             mod_date,
             mod_by,
             time_zone_cd
         )
VALUES
         (
             new_bus_entity_id,
             manufacturer,
             NULL,
             NULL,
             NULL,
             'MANUFACTURER',
             'ACTIVE',
             'UNKNOWN',
             'en_US',
             NULL,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             NULL
         )
  INTO   clw_bus_entity_assoc
         (
             bus_entity_assoc_id,
             bus_entity1_id,
             bus_entity2_id,
             bus_entity_assoc_cd,
             add_date,
             add_by,
             mod_date,
             mod_by
         )
VALUES
         (
             clw_bus_entity_assoc_seq.NEXTVAL,
             new_bus_entity_id,
             store_id,
             'MANUFACTURER OF STORE',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
   SELECT   new_bus_entity_id, manufacturer, store_id FROM tmp_2004;
   
   CREATE TABLE tmp_2236 (
        content     VARCHAR2(255) NOT NULL,
        mime_type   VARCHAR2(255)  NOT NULL);
    
   INSERT INTO tmp_2236 VALUES ('pdf', 'application/pdf');
   INSERT INTO tmp_2236 VALUES ('doc', 'application/msword');
   INSERT INTO tmp_2236 VALUES ('xls', 'application/vnd.ms-excel');
   INSERT INTO tmp_2236 VALUES ('docx', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document');
   INSERT INTO tmp_2236 VALUES ('xlsx', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');

   DROP TABLE tmp_2001 purge;
   DROP TABLE tmp_2002 purge;
   DROP TABLE tmp_2003 purge;
   DROP TABLE tmp_2004 purge;
