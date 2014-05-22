   
-- IV.1 Manufacturers which already have Parent-Child store associations

MERGE INTO   clw_bus_entity child_manuf
     USING   (SELECT   link.child_bus_entity_id,
                       manuf.short_desc,
                       manuf.long_desc,
                       manuf.eff_date,
                       manuf.exp_date,
                       manuf.bus_entity_type_cd,
                       manuf.bus_entity_status_cd,
                       manuf.workflow_role_cd,
                       manuf.locale_cd,
                       manuf.erp_num,
                       manuf.time_zone_cd
                FROM   clw_bus_entity_pc_assoc link,
                       clw_bus_entity manuf,
                       clw_bus_entity_assoc
               WHERE   (manuf.bus_entity_id = link.parent_bus_entity_id)
                       AND (manuf.bus_entity_id =
                               clw_bus_entity_assoc.bus_entity1_id)
                       AND (clw_bus_entity_assoc.bus_entity2_id = #PARENT_STORE_ID#)
                       AND (manuf.bus_entity_type_cd = 'MANUFACTURER')
                       AND (clw_bus_entity_assoc.bus_entity_assoc_cd =
                               'MANUFACTURER OF STORE')) map_to_parent
        ON   (child_manuf.bus_entity_id = map_to_parent.child_bus_entity_id)
WHEN MATCHED
THEN
   UPDATE SET
      child_manuf.short_desc = map_to_parent.short_desc,
      child_manuf.long_desc = map_to_parent.long_desc,
      child_manuf.eff_date = map_to_parent.eff_date,
      child_manuf.exp_date = map_to_parent.exp_date,
      child_manuf.bus_entity_type_cd = map_to_parent.bus_entity_type_cd,
      child_manuf.bus_entity_status_cd = map_to_parent.bus_entity_status_cd,
      child_manuf.workflow_role_cd = map_to_parent.workflow_role_cd,
      child_manuf.locale_cd = map_to_parent.locale_cd,
      child_manuf.erp_num = map_to_parent.erp_num,
      child_manuf.mod_date = CURRENT_DATE,
      child_manuf.mod_by = '#USER_NAME#',
      child_manuf.time_zone_cd = map_to_parent.time_zone_cd;

-- IV.2 Manufacturers which have NOT Parent-Child store associations

CREATE TABLE tmp_new_manuf
AS
   SELECT   asset.asset_id,
            asset.manuf_id,
            manuf.short_desc AS manuf_name,
            asset_assoc.bus_entity_id AS store_id
     FROM   clw_asset asset,
            clw_bus_entity manuf,
            clw_bus_entity_assoc manuf_assoc,
            clw_asset_assoc asset_assoc,
            tmp_010
    WHERE       (asset.manuf_id = manuf.bus_entity_id)
            AND (asset_assoc.bus_entity_id = tmp_010.bus_entity_id)
            AND (manuf.bus_entity_id = manuf_assoc.bus_entity1_id)
            AND (asset.asset_id = asset_assoc.asset_id)
            AND (manuf_assoc.bus_entity2_id = #PARENT_STORE_ID#)
            AND (asset.asset_type_cd = 'MASTER_ASSET')
            AND (asset.manuf_id NOT IN
                       (SELECT   link.parent_bus_entity_id
                          FROM   clw_bus_entity_pc_assoc link,
                                 clw_bus_entity_assoc store_assoc
                         WHERE   (link.child_bus_entity_id =
                                     store_assoc.bus_entity1_id)
                                 AND (store_assoc.bus_entity2_id =
                                         asset_assoc.bus_entity_id)));

    
-- IV.3 Manufacturers which have NOT Parent-Child store associations
-- but HAVE identical manufacturer by name

CREATE TABLE tmp_new_mtchd_manuf
AS
   SELECT   asset_id,
            manuf_id,
            tmp_new_manuf.store_id,
            manuf_ident.bus_entity_id AS child_manuf_id
     FROM   tmp_new_manuf,
            (SELECT   manuf.bus_entity_id,
                      manuf_assoc.bus_entity2_id AS store_id,
                      manuf.short_desc AS manuf_name
               FROM   clw_bus_entity manuf, clw_bus_entity_assoc manuf_assoc
              WHERE   manuf.bus_entity_type_cd = 'MANUFACTURER'
                      AND manuf.bus_entity_id = manuf_assoc.bus_entity1_id
                      AND manuf_assoc.bus_entity_assoc_cd =
                            'MANUFACTURER OF STORE') manuf_ident
    WHERE   manuf_ident.store_id = tmp_new_manuf.store_id
--            AND manuf_ident.manuf_name = tmp_new_manuf.manuf_name
            AND (LOWER(manuf_ident.manuf_name) IN 
                            	(SELECT LOWER(alias) 
                            	   FROM tmp_mnf_alias 
                            	  WHERE tmp_mnf_alias.manuf_id = tmp_new_manuf.manuf_id ))

            ;
            
            
INSERT ALL
INTO clw_bus_entity_pc_assoc
           (
               bus_entity_pc_assoc_id,
               parent_bus_entity_id,
               child_bus_entity_id,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
  VALUES
           (
               clw_bus_entity_pc_assoc_seq.NEXTVAL,
               manuf_id,
               child_manuf_id,
               CURRENT_DATE,
               '#USER_NAME#',
               CURRENT_DATE,
               '#USER_NAME#'
           )
   SELECT   manuf_id, child_manuf_id
     FROM	tmp_new_mtchd_manuf
   GROUP BY manuf_id, child_manuf_id;
           
CREATE TABLE tmp_110
AS
   SELECT   asset_id, manuf_id, store_id
     FROM   tmp_new_manuf
    WHERE   asset_id NOT IN
                  (SELECT   asset_id FROM tmp_new_mtchd_manuf);

CREATE TABLE tmp_120
AS
     SELECT   manuf_id, store_id
       FROM   tmp_110
   GROUP BY   manuf_id, store_id;

CREATE TABLE tmp_130
AS
   SELECT   manuf_id, store_id, clw_bus_entity_seq.NEXTVAL AS child_manuf_id
     FROM   tmp_120;

CREATE TABLE tmp_100
AS
   SELECT   asset.asset_id,
            asset.manuf_id,
            asset.store_id,
            manuf.child_manuf_id
     FROM   tmp_110 asset,
            tmp_130 manuf
    WHERE   asset.manuf_id = manuf.manuf_id
            AND asset.store_id = manuf.store_id;

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
             child_manuf_id,
             short_desc,
             long_desc,
             eff_date,
             exp_date,
             bus_entity_type_cd,
             bus_entity_status_cd,
             workflow_role_cd,
             locale_cd,
             erp_num,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             time_zone_cd
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
             child_manuf_id,
             store_id,
             'MANUFACTURER OF STORE',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
  INTO   clw_bus_entity_pc_assoc
         (
             bus_entity_pc_assoc_id,
             parent_bus_entity_id,
             child_bus_entity_id,
             add_date,
             add_by,
             mod_date,
             mod_by
         )
VALUES
         (
             clw_bus_entity_pc_assoc_seq.NEXTVAL,
             manuf_id,
             child_manuf_id,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
   SELECT   tmp_130.manuf_id,
            tmp_130.store_id,
            tmp_130.child_manuf_id,
            manuf.short_desc,
            manuf.long_desc,
            manuf.eff_date,
            manuf.exp_date,
            manuf.bus_entity_type_cd,
            manuf.bus_entity_status_cd,
            manuf.workflow_role_cd,
            manuf.locale_cd,
            manuf.erp_num,
            manuf.time_zone_cd
     FROM   tmp_130, clw_bus_entity manuf
    WHERE   (tmp_130.manuf_id = manuf.bus_entity_id);

MERGE INTO   clw_asset
     USING   (SELECT   asset_id,
                       manuf.short_desc AS child_manuf_name,
                       child_manuf_id
                FROM   tmp_100, clw_bus_entity manuf
               WHERE   manuf_id = manuf.bus_entity_id) map
        ON   (clw_asset.asset_id = map.asset_id)
WHEN MATCHED
THEN
   UPDATE SET
      clw_asset.manuf_id = map.child_manuf_id,
      clw_asset.manuf_name = map.child_manuf_name;
      

-- IV.4 Parent-Child Manufacturers of parent store

CREATE TABLE tmp_300
AS
   SELECT   clw_bus_entity_pc_assoc.parent_bus_entity_id,
            clw_bus_entity_pc_assoc.child_bus_entity_id
     FROM   clw_bus_entity_assoc, clw_bus_entity_pc_assoc
    WHERE   (clw_bus_entity_assoc.bus_entity1_id =
                clw_bus_entity_pc_assoc.parent_bus_entity_id)
            AND (clw_bus_entity_assoc.bus_entity_assoc_cd =
                    'MANUFACTURER OF STORE')
            AND (clw_bus_entity_assoc.bus_entity2_id = #PARENT_STORE_ID#);
            
-- IV.5 Manufacturer EMAIL synchronization            

DELETE FROM   clw_email
      WHERE   clw_email.bus_entity_id IN
                    (     SELECT   child_bus_entity_id FROM tmp_300);

INSERT   ALL
  INTO   clw_email
         (
             email_id,
             bus_entity_id,
             user_id,
             short_desc,
             email_type_cd,
             email_status_cd,
             email_address,
             primary_ind,
             add_date,
             add_by,
             mod_date,
             mod_by,
             contact_id
         )
VALUES
         (
             clw_email_seq.NEXTVAL,
             child_bus_entity_id,
             user_id,
             short_desc,
             email_type_cd,
             email_status_cd,
             email_address,
             primary_ind,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             contact_id
         )
   SELECT   child_bus_entity_id,
            user_id,
            short_desc,
            email_type_cd,
            email_status_cd,
            email_address,
            primary_ind,
            contact_id
     FROM   tmp_300, clw_email
    WHERE   tmp_300.parent_bus_entity_id = clw_email.bus_entity_id;

-- VII.6 Manufacturer PHONE synchronization

DELETE FROM   clw_phone
      WHERE   clw_phone.bus_entity_id IN
                    (     SELECT   child_bus_entity_id FROM tmp_300);

INSERT   ALL
  INTO   clw_phone
         (
             phone_id,
             bus_entity_id,
             user_id,
             phone_country_cd,
             phone_area_code,
             phone_num,
             short_desc,
             phone_type_cd,
             phone_status_cd,
             primary_ind,
             add_date,
             add_by,
             mod_date,
             mod_by,
             contact_id
         )
VALUES
         (
             clw_phone_seq.NEXTVAL,
             child_bus_entity_id,
             user_id,
             phone_country_cd,
             phone_area_code,
             phone_num,
             short_desc,
             phone_type_cd,
             phone_status_cd,
             primary_ind,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             contact_id
         )
   SELECT   child_bus_entity_id,
            user_id,
            phone_country_cd,
            phone_area_code,
            phone_num,
            short_desc,
            phone_type_cd,
            phone_status_cd,
            primary_ind,
            contact_id
     FROM   tmp_300, clw_phone
    WHERE   tmp_300.parent_bus_entity_id = clw_phone.bus_entity_id;
    
-- VII.7 Manufacturer ADDRESS synchronization

DELETE FROM   clw_address
      WHERE   clw_address.bus_entity_id IN
                    (     SELECT   child_bus_entity_id FROM tmp_300);

INSERT   ALL
  INTO   clw_address
         (
             address_id,
             bus_entity_id,
             user_id,
             name1,
             name2,
             address1,
             address2,
             address3,
             address4,
             city,
             state_province_cd,
             country_cd,
             county_cd,
             postal_code,
             address_status_cd,
             address_type_cd,
             primary_ind,
             add_date,
             add_by,
             mod_date,
             mod_by
         )
VALUES
         (
             clw_address_seq.NEXTVAL,
             child_bus_entity_id,
             user_id,
             name1,
             name2,
             address1,
             address2,
             address3,
             address4,
             city,
             state_province_cd,
             country_cd,
             county_cd,
             postal_code,
             address_status_cd,
             address_type_cd,
             primary_ind,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
   SELECT   child_bus_entity_id,
            user_id,
            name1,
            name2,
            address1,
            address2,
            address3,
            address4,
            city,
            state_province_cd,
            country_cd,
            county_cd,
            postal_code,
            address_status_cd,
            address_type_cd,
            primary_ind
     FROM   tmp_300, clw_address
    WHERE   tmp_300.parent_bus_entity_id = clw_address.bus_entity_id;
    
DROP TABLE tmp_300;
