CREATE TABLE tmp_1116
AS
SELECT   clw_content_seq.NEXTVAL AS new_content_id,
         a.asset_id,
         n.manufacturer,
         n.mfg_sku,
         n.image,
         image_blob
  FROM   (SELECT   a.manuf_name,
                   a.manuf_sku,
                   a.asset_id,
                   a.manuf_id,
                   aa.bus_entity_id AS store_id
            FROM   clw_asset a, clw_asset_assoc aa
           WHERE       a.asset_id = aa.asset_id
                   AND 'MASTER_ASSET' = a.asset_type_cd
                   AND 'ASSET_STORE' = aa.asset_assoc_cd) a,
         #LOADING_TABLE_NAME# n
 WHERE       a.store_id = n.store_id
         AND LOWER(TRIM(n.manufacturer)) IN 
                            	(SELECT LOWER(alias) 
                            	   FROM tmp_mnf_alias 
                            	  WHERE tmp_mnf_alias.manuf_id = a.manuf_id)
         AND a.manuf_sku = n.mfg_sku
         AND n.image_blob IS NOT NULL;
         
CREATE TABLE tmp_1117
AS
   SELECT   a.asset_id, c.content_id
     FROM   tmp_1116 a, clw_asset_content c
    WHERE   a.asset_id = c.asset_id;

DELETE FROM   clw_asset_content
      WHERE   asset_id IN (     SELECT   asset_id FROM tmp_1117)
      		  AND type_cd = 'ASSET_IMAGE';

DELETE FROM   clw_content
      WHERE   content_id IN (     SELECT   content_id FROM tmp_1117)
			  AND INSTR(LOWER(content_type_cd),'image') > 0;

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
             asset_id,
             new_content_id,
             'ASSET_IMAGE',
             image,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
SELECT   new_content_id,
         asset_id,
         manufacturer,
         mfg_sku,
         image,
         image_blob
  FROM   tmp_1116;
