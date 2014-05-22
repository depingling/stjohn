UPDATE   #LOADING_TABLE_NAME#
   SET   staged_item_id = clw_staged_item_seq.NEXTVAL
 WHERE   matched_item_id IS NULL;

INSERT INTO clw_staged_item
           (
               staged_item_id,
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
               item_name,
               model_number,
               assoc_doc_1,
               assoc_doc_1_blob,
               assoc_doc_2,
               assoc_doc_2_blob,
               assoc_doc_3,
               assoc_doc_3_blob,
               matched_item_id,
               store_catalog_id
           )
   SELECT   staged_item_id,
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
            item_name,
            model_number,
            assoc_doc_1,
            assoc_doc_1_blob,
            assoc_doc_2,
            assoc_doc_2_blob,
            assoc_doc_3,
            assoc_doc_3_blob,
            matched_item_id,
            store_catalog_id
     FROM   #LOADING_TABLE_NAME# d
    WHERE   matched_item_id IS NULL
            AND NOT EXISTS 
            (SELECT *
            FROM clw_staged_item s
            WHERE s.manufacturer = d.manufacturer
                  AND s.mfg_sku = d.mfg_sku
                  AND s.uom = d.uom);

INSERT INTO clw_staged_item_assoc
           (
               staged_item_assoc_id,
               staged_item_id,
               bus_entity_id,
               assoc_cd,
               assoc_status_cd,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_staged_item_assoc_seq.NEXTVAL,
            staged_item_id,
            store_id,
            'ITEM_STORE',
            'ACTIVE',
            CURRENT_DATE,
            'yyy',
            CURRENT_DATE,
            'yyy'
     FROM   #LOADING_TABLE_NAME# d
    WHERE   matched_item_id IS NULL
            AND NOT EXISTS 
            (SELECT *
            FROM clw_staged_item s
            WHERE s.manufacturer = d.manufacturer
                  AND s.mfg_sku = d.mfg_sku
                  AND s.uom = d.uom);
