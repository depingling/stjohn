CREATE TABLE tmp_newitems
AS
   SELECT   action,
            store_id,
            dist_sku,
            mfg_sku,
            manufacturer,
            distributor,
            pack,
            uom,
            category_name,
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
            matched_item_id,
            store_catalog_id,
            dist.bus_entity_id AS dist_id,
            manuf.bus_entity_id AS manuf_id,
            clw_item_seq.NEXTVAL AS new_item_id
     FROM   #LOADING_TABLE_NAME#,
            clw_bus_entity dist,
            clw_bus_entity_assoc dist_assoc,
            clw_bus_entity manuf,
            clw_bus_entity_assoc manuf_assoc
    WHERE       matched_item_id IS NULL
            AND distributor = dist.short_desc
            AND dist.bus_entity_type_cd = 'DISTRIBUTOR'
            AND dist.bus_entity_id = dist_assoc.bus_entity1_id
            AND dist_assoc.bus_entity_assoc_cd = 'DISTRIBUTOR OF STORE'
            AND dist_assoc.bus_entity2_id = #LOADING_TABLE_NAME#.store_id
            AND manufacturer = manuf.short_desc
            AND manuf.bus_entity_type_cd = 'MANUFACTURER'
            AND manuf.bus_entity_id = manuf_assoc.bus_entity1_id
            AND manuf_assoc.bus_entity_assoc_cd = 'MANUFACTURER OF STORE'
            AND manuf_assoc.bus_entity2_id = #LOADING_TABLE_NAME#.store_id;
            
            
INSERT   ALL
  INTO   clw_item
         (
             item_id,
             short_desc,
             sku_num,
             long_desc,
             eff_date,
             exp_date,
             item_type_cd,
             item_status_cd,
             item_order_num,
             add_date,
             add_by,
             mod_date,
             mod_by
         )
VALUES
         (
             new_item_id,
             short_description,
             new_item_id + 10000,
             long_description,
             SYSDATE,
             NULL,
             'PRODUCT',
             DECODE (action, 'D', 'INACTIVE', 'ACTIVE'),
             0,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
   SELECT   * FROM tmp_newitems;

INSERT   ALL
  INTO   clw_catalog_structure
         (
             catalog_structure_id,
             catalog_id,
             bus_entity_id,
             catalog_structure_cd,
             item_id,
             customer_sku_num,
             short_desc,
             eff_date,
             exp_date,
             status_cd,
             add_date,
             add_by,
             mod_date,
             mod_by,
             cost_center_id,
             tax_exempt,
             item_group_id,
             special_permission
         )
VALUES
         (
             clw_catalog_structure_seq.NEXTVAL,
             store_catalog_id,
             NULL,
             'CATALOG_PRODUCT',
             new_item_id,
             new_item_id +10000,
             NULL,
             SYSDATE,
             NULL,
             DECODE (action, 'D', 'INACTIVE', 'ACTIVE'),
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             0,
             NULL,
             NULL,
             NULL
         )
   SELECT   * FROM tmp_newitems;

CREATE TABLE tmp_catassoc
AS
   SELECT   tmp_newitems.new_item_id,
            tmp_newitems.store_catalog_id,
            cat.item_id AS category_id
     FROM   tmp_newitems, clw_item cat, clw_catalog_structure cats
    WHERE   tmp_newitems.category_name = cat.short_desc
            AND cat.item_type_cd = 'CATEGORY'
			AND cats.catalog_id = tmp_newitems.store_catalog_id
			AND cats.item_id = cat.item_id
			AND cat.long_desc IS NULL
			;

INSERT INTO clw_item_assoc
           (
               item_assoc_id,
               item1_id,
               item2_id,
               catalog_id,
               item_assoc_cd,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_item_assoc_seq.NEXTVAL,
            new_item_id,
            category_id,
            store_catalog_id,
            'PRODUCT_PARENT_CATEGORY',
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#'
     FROM   tmp_catassoc;

INSERT INTO clw_item_mapping
           (
               item_mapping_id,
               item_id,
               bus_entity_id,
               item_num,
               item_uom,
               item_pack,
               short_desc,
               long_desc,
               item_mapping_cd,
               eff_date,
               exp_date,
               status_cd,
               add_date,
               add_by,
               mod_date,
               mod_by,
               uom_conv_multiplier,
               standard_product_list
           )
   SELECT   clw_item_mapping_seq.NEXTVAL,
            new_item_id,
            dist_id,
            dist_sku,
            uom,
            pack,
            short_description,
            long_description,
            'ITEM_DISTRIBUTOR',
            SYSDATE,
            NULL,
            'ACTIVE',
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#',
            NULL,
            NULL
     FROM   tmp_newitems;

INSERT INTO clw_item_mapping
           (
               item_mapping_id,
               item_id,
               bus_entity_id,
               item_num,
               item_uom,
               item_pack,
               short_desc,
               long_desc,
               item_mapping_cd,
               eff_date,
               exp_date,
               status_cd,
               add_date,
               add_by,
               mod_date,
               mod_by,
               uom_conv_multiplier,
               standard_product_list
           )
   SELECT   clw_item_mapping_seq.NEXTVAL,
            new_item_id,
            manuf_id,
            mfg_sku,
            NULL,
            NULL,
            short_description,
            long_description,
            'ITEM_MANUFACTURER',
            SYSDATE,
            NULL,
            'AVAILABLE',
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#',
            NULL,
            NULL
     FROM   tmp_newitems;

INSERT INTO clw_item_mapping(item_mapping_id,
                        item_id,
                        bus_entity_id,
                        item_num,
                        item_uom,
                        item_pack,
                        short_desc,
                        long_desc,
                        item_mapping_cd,
                        eff_date,
                        exp_date,
                        status_cd,
                        add_date,
                        add_by,
                        mod_date,
                        mod_by,
                        uom_conv_multiplier,
                        standard_product_list)
   SELECT   clw_item_mapping_seq.NEXTVAL,
            tmp_newitems.new_item_id,
            tmp_crt_comp.bus_entity_id,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             'ITEM_CERTIFIED_COMPANY',
             NULL,
             NULL,
             'ACTIVE',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             NULL,
             NULL
     FROM   tmp_crt_comp, tmp_newitems
    WHERE   tmp_crt_comp.certified_companies =
               tmp_newitems.certified_companies;		


INSERT INTO clw_item_meta
           (
               item_meta_id,
               item_id,
               value_id,
               name_value,
               clw_value,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_item_meta_seq.NEXTVAL,
            new_item_id,
            0,
            'PACK',
            pack,
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#'
     FROM   tmp_newitems
    WHERE   pack IS NOT NULL;

INSERT INTO clw_item_meta
           (
               item_meta_id,
               item_id,
               value_id,
               name_value,
               clw_value,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_item_meta_seq.NEXTVAL,
            new_item_id,
            0,
            'UOM',
            uom,
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#'
     FROM   tmp_newitems
    WHERE   uom IS NOT NULL;

INSERT INTO clw_item_meta
           (
               item_meta_id,
               item_id,
               value_id,
               name_value,
               clw_value,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_item_meta_seq.NEXTVAL,
            new_item_id,
            0,
            'SIZE',
            item_size,
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#'
     FROM   tmp_newitems
    WHERE   item_size IS NOT NULL;

INSERT INTO clw_item_meta
           (
               item_meta_id,
               item_id,
               value_id,
               name_value,
               clw_value,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_item_meta_seq.NEXTVAL,
            new_item_id,
            0,
            'UPC_NUM',
            product_upc,
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#'
     FROM   tmp_newitems
    WHERE   product_upc IS NOT NULL;

INSERT INTO clw_item_meta
           (
               item_meta_id,
               item_id,
               value_id,
               name_value,
               clw_value,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_item_meta_seq.NEXTVAL,
            new_item_id,
            0,
            'PKG_UPC_NUM',
            pack_upc,
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#'
     FROM   tmp_newitems
    WHERE   pack_upc IS NOT NULL;

INSERT INTO clw_item_meta
           (
               item_meta_id,
               item_id,
               value_id,
               name_value,
               clw_value,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_item_meta_seq.NEXTVAL,
            new_item_id,
            0,
            'COLOR',
            color,
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#'
     FROM   tmp_newitems
    WHERE   color IS NOT NULL;

INSERT INTO clw_item_meta
           (
               item_meta_id,
               item_id,
               value_id,
               name_value,
               clw_value,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_item_meta_seq.NEXTVAL,
            new_item_id,
            0,
            'UNSPSC_CD',
            unspsc_code,
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#'
     FROM   tmp_newitems
    WHERE   unspsc_code IS NOT NULL;

INSERT INTO clw_item_meta
           (
               item_meta_id,
               item_id,
               value_id,
               name_value,
               clw_value,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_item_meta_seq.NEXTVAL,
            new_item_id,
            0,
            'SHIP_WEIGHT',
            shipping_weight,
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#'
     FROM   tmp_newitems
    WHERE   shipping_weight IS NOT NULL;

INSERT INTO clw_item_meta
           (
               item_meta_id,
               item_id,
               value_id,
               name_value,
               clw_value,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_item_meta_seq.NEXTVAL,
            new_item_id,
            0,
            'WEIGHT_UNIT',
            weight_unit,
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#'
     FROM   tmp_newitems
    WHERE   weight_unit IS NOT NULL;

INSERT INTO clw_item_meta
           (
               item_meta_id,
               item_id,
               value_id,
               name_value,
               clw_value,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_item_meta_seq.NEXTVAL,
            new_item_id,
            0,
            'NSN',
            nsn,
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#'
     FROM   tmp_newitems
    WHERE   nsn IS NOT NULL;

INSERT INTO clw_item_meta
           (
               item_meta_id,
               item_id,
               value_id,
               name_value,
               clw_value,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_item_meta_seq.NEXTVAL,
            new_item_id,
            0,
            'CUBE_SIZE',
            shipping_cubic_size,
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#'
     FROM   tmp_newitems
    WHERE   shipping_cubic_size IS NOT NULL;

INSERT INTO clw_item_meta
           (
               item_meta_id,
               item_id,
               value_id,
               name_value,
               clw_value,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_item_meta_seq.NEXTVAL,
            new_item_id,
            0,
            'HAZMAT',
            LOWER(hazmat),
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#'
     FROM   tmp_newitems
    WHERE   hazmat IS NOT NULL;
-- IMAGE
INSERT INTO clw_item_meta
           (
               item_meta_id,
               item_id,
               value_id,
               name_value,
               clw_value,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_item_meta_seq.NEXTVAL,
            new_item_id,
            0,
            'IMAGE',
               '/en/products/images/'
            || new_item_id
            || '.'
            || SUBSTR (image, INSTR (image, '.', -1) + 1),
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#'
     FROM   tmp_newitems
    WHERE   image IS NOT NULL AND image_blob IS NOT NULL;

INSERT INTO clw_content
           (
               content_id,
               short_desc,
               version,
               content_type_cd,
               content_status_cd,
               source_cd,
               locale_cd,
               language_cd,
               PATH,
               content_usage_cd,
               add_date,
               add_by,
               mod_date,
               mod_by,
               binary_data
           )
   SELECT   clw_content_seq.NEXTVAL,
   			'ContentLog',
            0,
            'ContentLog',
            'ACTIVE',
            'xsuite-app',
            'x',
            'x',
            './en/products/images/'
            || new_item_id
            || '.'
            || SUBSTR (image, INSTR (image, '.', -1) + 1),
            'ContentLog',
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#',
            image_blob
     FROM   tmp_newitems
    WHERE   image IS NOT NULL AND image_blob IS NOT NULL;

    
-- MSDS
INSERT INTO clw_item_meta
           (
               item_meta_id,
               item_id,
               value_id,
               name_value,
               clw_value,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_item_meta_seq.NEXTVAL,
            new_item_id,
            0,
            'MSDS',
               '/en/products/msds/'
            || new_item_id
            || '.'
            || SUBSTR (msds, INSTR (msds, '.', -1) + 1),
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#'
     FROM   tmp_newitems
    WHERE  msds IS NOT NULL AND msds_blob IS NOT NULL;
    
INSERT INTO clw_content
           (
               content_id,
               short_desc,
               version,
               content_type_cd,
               content_status_cd,
               source_cd,
               locale_cd,
               language_cd,
               PATH,
               content_usage_cd,
               add_date,
               add_by,
               mod_date,
               mod_by,
               binary_data
           )
   SELECT   clw_content_seq.NEXTVAL,
   			'ContentLog',
            0,
            'ContentLog',
            'ACTIVE',
            'xsuite-app',
            'x',
            'x',
            './en/products/msds/'
            || new_item_id
            || '.'
            || SUBSTR (msds, INSTR (msds, '.', -1) + 1),
            'ContentLog',
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#',
            msds_blob
     FROM   tmp_newitems
    WHERE  msds IS NOT NULL AND msds_blob IS NOT NULL;    

-- Specification
INSERT INTO clw_item_meta
           (
               item_meta_id,
               item_id,
               value_id,
               name_value,
               clw_value,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_item_meta_seq.NEXTVAL,
            new_item_id,
            0,
            'SPEC',
               '/en/products/spec/'
            || new_item_id
            || '.'
            || SUBSTR (specification, INSTR (specification, '.', -1) + 1),
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#'
     FROM   tmp_newitems
    WHERE   specification IS NOT NULL AND specification_blob IS NOT NULL;
    
INSERT INTO clw_content
           (
               content_id,
               short_desc,
               version,
               content_type_cd,
               content_status_cd,
               source_cd,
               locale_cd,
               language_cd,
               PATH,
               content_usage_cd,
               add_date,
               add_by,
               mod_date,
               mod_by,
               binary_data
           )
   SELECT   clw_content_seq.NEXTVAL,
   			'ContentLog',
            0,
            'ContentLog',
            'ACTIVE',
            'xsuite-app',
            'x',
            'x',
            './en/products/spec/'
            || new_item_id
            || '.'
            || SUBSTR (specification, INSTR (specification, '.', -1) + 1),
            'ContentLog',
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#',
            specification_blob
     FROM   tmp_newitems
    WHERE   specification IS NOT NULL AND specification_blob IS NOT NULL;        