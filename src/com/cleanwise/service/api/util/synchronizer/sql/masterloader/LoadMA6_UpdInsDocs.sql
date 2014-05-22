CREATE TABLE tmp_assoc_docs
(
   manufacturer   VARCHAR (30) NOT NULL,
   mfg_sku        VARCHAR (30) NOT NULL,
   assoc_doc      VARCHAR (255) NOT NULL,
   doc_blob       BLOB NOT NULL,
   asset_id       NUMBER (38) NOT NULL,
   store_id       NUMBER (38),
   content_id     NUMBER (38)
);

INSERT INTO tmp_assoc_docs
           (
               manufacturer,
               mfg_sku,
               assoc_doc,
               store_id,
               doc_blob, 
               asset_id
           )
   SELECT   manufacturer,
            mfg_sku,
            assoc_doc_1,
            store_id,
            assoc_doc_1_blob,
            0
     FROM   #LOADING_TABLE_NAME#
    WHERE   assoc_doc_1_blob IS NOT NULL;

INSERT INTO tmp_assoc_docs
           (
               manufacturer,
               mfg_sku,
               assoc_doc,
               store_id,
               doc_blob, 
               asset_id
           )
   SELECT   manufacturer,
            mfg_sku,
            assoc_doc_2,
            store_id,
            assoc_doc_2_blob,
            0
     FROM   #LOADING_TABLE_NAME#
    WHERE   assoc_doc_2_blob IS NOT NULL;

INSERT INTO tmp_assoc_docs
           (
               manufacturer,
               mfg_sku,
               assoc_doc,
               store_id,
               doc_blob, 
               asset_id
           )
   SELECT   manufacturer,
            mfg_sku,
            assoc_doc_3,
            store_id,
            assoc_doc_3_blob,
            0
     FROM   #LOADING_TABLE_NAME#
    WHERE   assoc_doc_3_blob IS NOT NULL;

MERGE INTO   tmp_assoc_docs target
     USING   (SELECT   clw_asset.asset_id,
                       clw_asset.manuf_id,
                       clw_asset.manuf_sku,
                       clw_asset_assoc.bus_entity_id AS store_id
                FROM   clw_asset, clw_asset_assoc
               WHERE       (clw_asset.asset_id = clw_asset_assoc.asset_id)
                       AND (clw_asset.asset_type_cd = 'MASTER_ASSET')
                       AND (clw_asset_assoc.asset_assoc_cd = 'ASSET_STORE')
                       ) source
        ON(   target.store_id = source.store_id 
              AND LOWER(TRIM(target.manufacturer)) IN 
                            	(SELECT LOWER(alias) 
                            	   FROM tmp_mnf_alias 
                            	  WHERE tmp_mnf_alias.manuf_id = source.manuf_id)
              AND target.mfg_sku = source.manuf_sku)
WHEN MATCHED
THEN
   UPDATE SET target.asset_id = source.asset_id;

MERGE INTO   clw_content
     USING   (SELECT   clw_asset_content.content_id, tmp_assoc_docs.doc_blob
                FROM   tmp_assoc_docs, clw_asset_content
               WHERE   (tmp_assoc_docs.asset_id = clw_asset_content.asset_id)
                       AND (tmp_assoc_docs.assoc_doc = clw_asset_content.url))
             source
        ON   (clw_content.content_id = source.content_id)
WHEN MATCHED
THEN
   UPDATE SET
      clw_content.binary_data = source.doc_blob,
      clw_content.mod_date = CURRENT_DATE,
      clw_content.mod_by = '#USER_NAME#';

DELETE FROM   tmp_assoc_docs
      WHERE   EXISTS
                 (SELECT   *
                    FROM   clw_asset_content
                   WHERE   (tmp_assoc_docs.asset_id =
                               clw_asset_content.asset_id)
                           AND (tmp_assoc_docs.assoc_doc =
                                   clw_asset_content.url));
DELETE FROM   tmp_assoc_docs
	  WHERE asset_id = 0;	                                   

UPDATE   tmp_assoc_docs
   SET   content_id = clw_content_seq.NEXTVAL;
   
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
             content_id,
             SUBSTR (assoc_doc, INSTR (assoc_doc, '/', -1) + 1, 30),
             NULL,
             1,
             'ContentLog',
             'ACTIVE',
             'TCS LOADER',
             'x',
             'x',
             NULL,
             NULL,
             assoc_doc,
             'N/A',
             NULL,
             NULL,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             doc_blob
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
         (
             clw_asset_content_seq.NEXTVAL,
             asset_id,
             content_id,
             'SPEC',
             assoc_doc,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
   SELECT   * FROM tmp_assoc_docs;