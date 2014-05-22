CREATE TABLE tmp_1006
AS
   SELECT   clw_staged_asset_seq.NEXTVAL AS new_asset_id, tmp_1005.*
     FROM   tmp_1005;

DROP TABLE tmp_1005;


INSERT   ALL
  INTO   clw_staged_asset
         (
             staged_asset_id,
             version_number,
             action,
             asset,
             store_id,
             store_name,
             dist_sku,
             mfg_sku,
             manufacturer,
             distributor,
             pack,
             uom,
             category_name,
             sub_cat_1,
             sub_cat_2,
             sub_cat_3,
             multi_product_name,
             item_size,
             long_description,
             short_description,
             product_upc,
             pack_upc,
             unspsc_code,
             color,
             shipping_weight,
             weight_unit,
             nsn,
             shipping_cubic_size,
             hazmat,
             certified_companies,
             image,
             image_blob,
             msds,
             msds_blob,
             specification,
             specification_blob,
             asset_name,
             model_number,
             assoc_doc_1,
             assoc_doc_1_blob,
             assoc_doc_2,
             assoc_doc_2_blob,
             assoc_doc_3,
             assoc_doc_3_blob,
             matched_asset_id
         )
VALUES
         (
             new_asset_id,
             version_number,
             action,
             asset,
             store_id,
             store_name,
             dist_sku,
             mfg_sku,
             manufacturer,
             distributor,
             pack,
             uom,
             category_name,
             sub_cat_1,
             sub_cat_2,
             sub_cat_3,
             multi_product_name,
             item_size,
             long_description,
             short_description,
             product_upc,
             pack_upc,
             unspsc_code,
             color,
             shipping_weight,
             weight_unit,
             nsn,
             shipping_cubic_size,
             hazmat,
             certified_companies,
             image,
             image_blob,
             msds,
             msds_blob,
             specification,
             specification_blob,
             asset_name,
             model_number,
             assoc_doc_1,
             assoc_doc_1_blob,
             assoc_doc_2,
             assoc_doc_2_blob,
             assoc_doc_3,
             assoc_doc_3_blob,
             matched_asset_id
         )
  INTO   clw_staged_asset_assoc
         (
             staged_asset_assoc_id,
             staged_asset_id,
             bus_entity_id,
             assoc_cd,
             assoc_status_cd,
             add_date,
             add_by,
             mod_date,
             mod_by
         )
VALUES
         (
             clw_staged_asset_assoc_seq.NEXTVAL,
             new_asset_id,
             store_id,
             'ASSET_STORE',
             'ACTIVE',
             CURRENT_DATE,
             'yyy',
             CURRENT_DATE,
             'yyy'
         )
   SELECT   new_asset_id, s.*
     FROM   tmp_1006, #LOADING_TABLE_NAME# s
    WHERE   tmp_1006.manufacturer = s.manufacturer
            AND tmp_1006.mfg_sku = s.mfg_sku
            AND NOT EXISTS 
            (SELECT *
            FROM clw_staged_asset g
            WHERE g.manufacturer = s.manufacturer
                  AND g.mfg_sku = s.mfg_sku);

            
            
    