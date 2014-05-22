CREATE TABLE tmp_2006
AS
   SELECT   clw_asset_seq.NEXTVAL AS new_asset_id, tmp_2005.* FROM tmp_2005;

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
             asset_name,
             'ASSET',
             category_id,
             DECODE (action, 'D', 'INACTIVE', 'ACTIVE'),
             serial_number,
             asset_number,
             manuf_id,
             manufacturer,
             mfg_sku,
             'STORE',
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             model_number,
             master_asset_id
         )
SELECT   new_asset_id,
         asset_name,
         category_id,
         serial_number,
         manuf_id,
         manufacturer,
         mfg_sku,
         model_number,
         master_asset_id,
         asset_number,
         action
FROM   tmp_2006;

INSERT ALL
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
SELECT   new_asset_id,
         store_id
FROM   tmp_2006;

INSERT ALL
  INTO   clw_asset_property
         (
             asset_property_id,
             asset_id,
             asset_property_cd,
             clw_value,
             add_by,
             add_date,
             mod_by,
             mod_date
         )
VALUES
         (
             clw_asset_property_seq.NEXTVAL,
             new_asset_id,
             'LONG_DESC',
             long_description,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE
         )
   SELECT   new_asset_id,
            long_description
     FROM   tmp_2006;

INSERT   ALL
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
             site_id,
             'ASSET_SITE',
             'ACTIVE',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             0
         )
SELECT   new_asset_id,
         site_id
FROM     tmp_2006;

CREATE TABLE tmp_2226
AS
   SELECT   clw_content_seq.NEXTVAL AS new_content_id, tmp_2006.*
     FROM   tmp_2006
    WHERE   image_blob IS NOT NULL;

INSERT   ALL
  INTO   clw_content
         (
             content_id,
             short_desc,
             long_desc,
             version,
             content_type_cd,
             content_status_cd,
             source_cd,
             locale_cd,
             language_cd,
             item_id,
             bus_entity_id,
             PATH,
             content_usage_cd,
             eff_date,
             exp_date,
             add_date,
             add_by,
             mod_date,
             mod_by,
             binary_data
         )
VALUES
         (
             new_content_id,
             'Asset Image',
             NULL,
             1,
             'image/' || SUBSTR (image, INSTR (image, '.', -1) + 1),
             'ACTIVE',
             'TCS PA LOADER',
             'x',
             'x',
             NULL,
             NULL,
             image,
             'N/A',
             NULL,
             NULL,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             image_blob
         )
  INTO   clw_asset_content
         (
             asset_content_id,
             asset_id,
             content_id,
             type_cd,
             url,
             add_date,
             add_by,
             mod_date,
             mod_by
         )
VALUES
         (   clw_asset_content_seq.NEXTVAL,
             new_asset_id,
             new_content_id,
             'ASSET_IMAGE',
             image,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
   SELECT   * FROM tmp_2226;
   
CREATE TABLE tmp_2227
AS
   SELECT   clw_content_seq.NEXTVAL AS new_content_id,
            tmp_2006.*,
            mime.mime_type as content_type
     FROM   tmp_2006,
            tmp_2236 mime
    WHERE   assoc_doc_1 IS NOT NULL AND
            assoc_doc_1_blob IS NOT NULL AND
            mime.content = SUBSTR (assoc_doc_1, INSTR (assoc_doc_1, '.', -1) + 1);

INSERT   ALL
  INTO   clw_content
         (
             content_id,
             short_desc,
             long_desc,
             version,
             content_type_cd,
             content_status_cd,
             source_cd,
             locale_cd,
             language_cd,
             item_id,
             bus_entity_id,
             path,
             content_usage_cd,
             eff_date,
             exp_date,
             add_date,
             add_by,
             mod_date,
             mod_by,
             binary_data
         )
VALUES
         (
             new_content_id,
             'Associated Document 1',
             NULL,
             1,
             content_type,
             'ACTIVE',
             'TCS PA LOADER',
             'x',
             'x',
             NULL,
             NULL,
             assoc_doc_1,
             'N/A',
             NULL,
             NULL,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             assoc_doc_1_blob
         )
  INTO   clw_asset_content
         (
             asset_content_id,
             asset_id,
             content_id,
             type_cd,
             url,
             add_date,
             add_by,
             mod_date,
             mod_by
         )
VALUES
         (   clw_asset_content_seq.NEXTVAL,
             new_asset_id,
             new_content_id,
             'SPEC',
             assoc_doc_1,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
   SELECT   * FROM tmp_2227;

CREATE TABLE tmp_2228
AS
   SELECT   clw_content_seq.NEXTVAL AS new_content_id,
            tmp_2006.*,
            mime.mime_type as content_type
     FROM   tmp_2006,
            tmp_2236 mime
    WHERE   assoc_doc_2 IS NOT NULL AND
            assoc_doc_2_blob IS NOT NULL AND
            mime.content = SUBSTR (assoc_doc_2, INSTR (assoc_doc_2, '.', -1) + 1);

INSERT   ALL
  INTO   clw_content
         (
             content_id,
             short_desc,
             long_desc,
             version,
             content_type_cd,
             content_status_cd,
             source_cd,
             locale_cd,
             language_cd,
             item_id,
             bus_entity_id,
             path,
             content_usage_cd,
             eff_date,
             exp_date,
             add_date,
             add_by,
             mod_date,
             mod_by,
             binary_data
         )
VALUES
         (
             new_content_id,
             'Associated Document 2',
             NULL,
             1,
             content_type,
             'ACTIVE',
             'TCS PA LOADER',
             'x',
             'x',
             NULL,
             NULL,
             assoc_doc_2,
             'N/A',
             NULL,
             NULL,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             assoc_doc_2_blob
         )
  INTO   clw_asset_content
         (
             asset_content_id,
             asset_id,
             content_id,
             type_cd,
             url,
             add_date,
             add_by,
             mod_date,
             mod_by
         )
VALUES
         (   clw_asset_content_seq.NEXTVAL,
             new_asset_id,
             new_content_id,
             'SPEC',
             assoc_doc_2,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
   SELECT   * FROM tmp_2228;

CREATE TABLE tmp_2229
AS
   SELECT   clw_content_seq.NEXTVAL AS new_content_id,
            tmp_2006.*,
            mime.mime_type as content_type
     FROM   tmp_2006,
            tmp_2236 mime
    WHERE   assoc_doc_3 IS NOT NULL AND
            assoc_doc_3_blob IS NOT NULL AND
            mime.content = SUBSTR (assoc_doc_3, INSTR (assoc_doc_3, '.', -1) + 1);

INSERT   ALL
  INTO   clw_content
         (
             content_id,
             short_desc,
             long_desc,
             version,
             content_type_cd,
             content_status_cd,
             source_cd,
             locale_cd,
             language_cd,
             item_id,
             bus_entity_id,
             path,
             content_usage_cd,
             eff_date,
             exp_date,
             add_date,
             add_by,
             mod_date,
             mod_by,
             binary_data
         )
VALUES
         (
             new_content_id,
             'Associated Document 3',
             NULL,
             1,
             content_type,
             'ACTIVE',
             'TCS PA LOADER',
             'x',
             'x',
             NULL,
             NULL,
             assoc_doc_3,
             'N/A',
             NULL,
             NULL,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             assoc_doc_3_blob
         )
  INTO   clw_asset_content
         (
             asset_content_id,
             asset_id,
             content_id,
             type_cd,
             url,
             add_date,
             add_by,
             mod_date,
             mod_by
         )
VALUES
         (   clw_asset_content_seq.NEXTVAL,
             new_asset_id,
             new_content_id,
             'SPEC',
             assoc_doc_3,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
   SELECT   * FROM tmp_2229;
   
CREATE TABLE tmp_2230
AS
   SELECT   *
     FROM   tmp_2006
    WHERE   acquisition_date IS NOT NULL;

INSERT   ALL
INTO   clw_asset_property
         (
             asset_property_id,
             asset_id,
             asset_property_cd,
             clw_value,
             add_by,
             add_date,
             mod_by,
             mod_date
         )
VALUES
         (
             clw_asset_property_seq.NEXTVAL,
             new_asset_id,
             'ACQUISITION_DATE',
             To_Char(acquisition_date,'mm/dd/yyyy'),
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE
         )
SELECT      new_asset_id,
            acquisition_date
 FROM       tmp_2230;

 CREATE TABLE tmp_2231
AS
   SELECT   *
     FROM   tmp_2006
    WHERE   acquisition_price IS NOT NULL;
    
INSERT   ALL
INTO   clw_asset_property
         (
             asset_property_id,
             asset_id,
             asset_property_cd,
             clw_value,
             add_by,
             add_date,
             mod_by,
             mod_date
         )
VALUES
         (
             clw_asset_property_seq.NEXTVAL,
             new_asset_id,
             'ACQUISITION_COST',
             acquisition_price,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE
         )
SELECT      new_asset_id,
            acquisition_price
 FROM       tmp_2230;

 CREATE TABLE tmp_2232
AS
   SELECT   *
     FROM   tmp_2006
    WHERE   last_hour_meter_reading IS NOT NULL;

INSERT   ALL
INTO   clw_asset_property
         (
             asset_property_id,
             asset_id,
             asset_property_cd,
             clw_value,
             add_by,
             add_date,
             mod_by,
             mod_date
         )
VALUES
         (
             clw_asset_property_seq.NEXTVAL,
             new_asset_id,
             'LAST_HOUR_METER_READING',
             last_hour_meter_reading,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE
         )
SELECT      new_asset_id,
            last_hour_meter_reading
 FROM       tmp_2232;
 
CREATE TABLE tmp_2233
AS
   SELECT   *
     FROM   tmp_2006
    WHERE   date_last_hour_meter_reading IS NOT NULL;
    
INSERT   ALL
INTO   clw_asset_property
         (
             asset_property_id,
             asset_id,
             asset_property_cd,
             clw_value,
             add_by,
             add_date,
             mod_by,
             mod_date
         )
VALUES
         (
             clw_asset_property_seq.NEXTVAL,
             new_asset_id,
             'DATE_LAST_HOUR_METER_READING',
             To_Char(date_last_hour_meter_reading,'mm/dd/yyyy'),
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE
         )
SELECT      new_asset_id,
            date_last_hour_meter_reading
 FROM       tmp_2233;

CREATE TABLE tmp_2234
AS
   SELECT   *
     FROM   tmp_2006
    WHERE   date_in_service IS NOT NULL;

INSERT   ALL
INTO   clw_asset_property
         (
             asset_property_id,
             asset_id,
             asset_property_cd,
             clw_value,
             add_by,
             add_date,
             mod_by,
             mod_date
         )
VALUES
         (
             clw_asset_property_seq.NEXTVAL,
             new_asset_id,
             'DATE_IN_SERVICE',
             To_Char(date_in_service,'mm/dd/yyyy'),
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE
         )
SELECT      new_asset_id,
            date_in_service
 FROM       tmp_2234;

CREATE TABLE tmp_2235
AS
   SELECT   *
     FROM   tmp_2006
    WHERE   warranty_number IS NOT NULL;

INSERT ALL
       INTO clw_asset_warranty
         (
             asset_warranty_id,
             asset_id,
             warranty_id,
             add_date,
             add_by,
             mod_date,
             mod_by   
         )
VALUES
         (
             clw_asset_warranty_seq.NEXTVAL,
             new_asset_id,
             warranty_id,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
SELECT a.new_asset_id,
       b.warranty_id
       FROM tmp_2235 a,
            clw_warranty b
       WHERE a.warranty_number = b.warranty_number;

    DROP TABLE tmp_2005 purge;
    DROP TABLE tmp_2006 purge;
    DROP TABLE tmp_2226 purge;
    DROP TABLE tmp_2227 purge;
    DROP TABLE tmp_2228 purge;
    DROP TABLE tmp_2229 purge;
    DROP TABLE tmp_2230 purge;
    DROP TABLE tmp_2231 purge;
    DROP TABLE tmp_2232 purge;
    DROP TABLE tmp_2233 purge;
    DROP TABLE tmp_2234 purge;
    DROP TABLE tmp_2235 purge;