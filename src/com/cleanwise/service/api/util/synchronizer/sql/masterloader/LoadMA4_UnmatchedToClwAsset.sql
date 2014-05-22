CREATE TABLE tmp_1006
AS
   SELECT   clw_asset_seq.NEXTVAL AS new_asset_id, tmp_1005.* FROM tmp_1005;



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
             'MASTER_ASSET',
             category_id,
             'ACTIVE',
             NULL,
             new_asset_id,
             manuf_id,
             manufacturer,
             mfg_sku,
             'STORE',
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             model_number,
             0
         )
   SELECT   new_asset_id,
            asset,
            store_id,
            mfg_sku,
            manufacturer,
            category_name,
            long_description,
            image,
            msds,
            specification,
            asset_name,
            model_number,
            assoc_doc_1,
            assoc_doc_2,
            assoc_doc_3,
            category_id,
            manuf_id
     FROM   tmp_1006;
         
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
            asset,
            store_id,
            mfg_sku,
            manufacturer,
            category_name,
            long_description,
            image,
            msds,
            specification,
            asset_name,
            model_number,
            assoc_doc_1,
            assoc_doc_2,
            assoc_doc_3,
            category_id,
            manuf_id
     FROM   tmp_1006;
         
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
             'LONG_DESC',
             long_description,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE
         )
   SELECT   new_asset_id,
            asset,
            store_id,
            mfg_sku,
            manufacturer,
            category_name,
            long_description,
            image,
            msds,
            specification,
            asset_name,
            model_number,
            assoc_doc_1,
            assoc_doc_2,
            assoc_doc_3,
            category_id,
            manuf_id
     FROM   tmp_1006;

CREATE TABLE tmp_1226
AS
   SELECT   clw_content_seq.NEXTVAL AS new_content_id, tmp_1006.*
     FROM   tmp_1006
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
             'Image',
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
             new_asset_id,
             new_content_id,
             'ASSET_IMAGE',
             image,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
   SELECT   * FROM tmp_1226;

