CREATE TABLE tmp_2116
AS
SELECT clw_content_seq.NEXTVAL AS new_content_id,
       matched_asset_id,
       image,
       image_blob
       FROM tmp_2205
       WHERE image_blob IS NOT NULL;
         
CREATE TABLE tmp_2117
AS
SELECT a.*, b.content_id
       FROM tmp_2116 a, clw_asset_content b
       WHERE a.matched_asset_id = b.asset_id
             AND b.type_cd = 'ASSET_IMAGE';

MERGE INTO clw_content a
      USING (SELECT  new_content_id,
                     matched_asset_id,
                     content_id,
                     image,
                     image_blob
      FROM tmp_2117 ) m
      ON (m.content_id = a.content_id)
WHEN MATCHED
THEN UPDATE SET
      a.path = m.image,
      a.mod_by = '#USER_NAME#',
      a.mod_date = CURRENT_DATE,
      a.binary_data = m.image_blob
WHEN NOT MATCHED
THEN INSERT
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
             'Asset Image',
             NULL,
             1,
             'image/' || SUBSTR (image, INSTR (image, '.', -1) + 1),
             'ACTIVE',
             'TCS LOADER',
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
         );

MERGE INTO clw_asset_content a
      USING (SELECT  new_content_id,
                     matched_asset_id,
                     content_id,
                     image,
                     image_blob
      FROM tmp_2117 ) m
      ON (m.matched_asset_id = a.asset_id AND m.content_id = a.content_id)
WHEN MATCHED
THEN UPDATE SET
      a.mod_by = '#USER_NAME#',
      a.mod_date = CURRENT_DATE
WHEN NOT MATCHED        
THEN INSERT
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
             matched_asset_id,
             new_content_id,
             'ASSET_IMAGE',
             image,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         );

    DROP TABLE tmp_2116 purge;
    DROP TABLE tmp_2117 purge;