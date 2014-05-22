CREATE TABLE tmp_2118
AS
SELECT   clw_content_seq.NEXTVAL AS new_content_id,
         matched_asset_id,
         assoc_doc_1,
         assoc_doc_1_blob,
         mime.mime_type as content_type
    FROM   tmp_2205,
           tmp_2236 mime
    WHERE  assoc_doc_1_blob IS NOT NULL AND
           mime.content = SUBSTR (assoc_doc_1, INSTR (assoc_doc_1, '.', -1) + 1);
    
CREATE TABLE tmp_2119
AS
SELECT   clw_content_seq.NEXTVAL AS new_content_id,
         matched_asset_id,
         assoc_doc_2,
         assoc_doc_2_blob,
         mime.mime_type as content_type
    FROM   tmp_2205,
           tmp_2236 mime
    WHERE  assoc_doc_2_blob IS NOT NULL AND
           mime.content = SUBSTR (assoc_doc_2, INSTR (assoc_doc_2, '.', -1) + 1);
    
CREATE TABLE tmp_2120
AS
SELECT   clw_content_seq.NEXTVAL AS new_content_id,
         matched_asset_id,
         assoc_doc_3,
         assoc_doc_3_blob,
         mime.mime_type as content_type
    FROM   tmp_2205,
           tmp_2236 mime
    WHERE  assoc_doc_3_blob IS NOT NULL AND
           mime.content = SUBSTR (assoc_doc_3, INSTR (assoc_doc_3, '.', -1) + 1);

INSERT   ALL
  INTO   clw_content
         (   content_id,
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
         (   new_content_id,
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
         (   asset_content_id,
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
             matched_asset_id,
             new_content_id,
             'SPEC',
             assoc_doc_1,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
SELECT   new_content_id,
         matched_asset_id,
         assoc_doc_1,
         assoc_doc_1_blob,
         content_type
  FROM   tmp_2118;
  
INSERT   ALL
  INTO   clw_content
         (   content_id,
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
         (   new_content_id,
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
         (   asset_content_id,
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
             matched_asset_id,
             new_content_id,
             'SPEC',
             assoc_doc_2,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
SELECT   new_content_id,
         matched_asset_id,
         assoc_doc_2,
         assoc_doc_2_blob,
         content_type
  FROM   tmp_2119;
  
INSERT   ALL
  INTO   clw_content
         (   content_id,
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
         (   new_content_id,
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
         (   asset_content_id,
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
             matched_asset_id,
             new_content_id,
             'SPEC',
             assoc_doc_3,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
SELECT   new_content_id,
         matched_asset_id,
         assoc_doc_3,
         assoc_doc_3_blob,
         content_type
  FROM   tmp_2120;

    DROP TABLE tmp_2118 purge;
    DROP TABLE tmp_2119 purge;
    DROP TABLE tmp_2120 purge;
    DROP TABLE tmp_2205 purge;