MERGE INTO   clw_item i
     USING   #LOADING_TABLE_NAME# s
        ON   (i.item_id = s.matched_item_id)
WHEN MATCHED
THEN
   UPDATE SET
      i.short_desc = s.short_description,
      i.long_desc = s.long_description,
      i.eff_date = SYSDATE,
      i.exp_date = NULL,
      i.item_type_cd = 'PRODUCT',
      i.item_status_cd = DECODE (action, 'D', 'INACTIVE', 'ACTIVE'),
      i.mod_date = CURRENT_DATE,
      i.mod_by = '#USER_NAME#';

CREATE TABLE tmp_item_distrs
AS
   SELECT   clw_bus_entity.bus_entity_id
     FROM   clw_catalog_structure, #LOADING_TABLE_NAME#, clw_bus_entity
    WHERE   (#LOADING_TABLE_NAME#.matched_item_id =
                clw_catalog_structure.item_id)
            AND (clw_bus_entity.bus_entity_id =
                    clw_catalog_structure.bus_entity_id)
            AND (clw_bus_entity.bus_entity_type_cd = 'DISTRIBUTOR');

CREATE TABLE tmp_store_distrs
AS
   SELECT   clw_bus_entity.bus_entity_id, clw_bus_entity.short_desc
     FROM   clw_bus_entity, clw_bus_entity_assoc
    WHERE   (clw_bus_entity.bus_entity_id =
                clw_bus_entity_assoc.bus_entity1_id)
            AND (clw_bus_entity.bus_entity_type_cd = 'DISTRIBUTOR')
            AND (clw_bus_entity_assoc.bus_entity_assoc_cd =
                    'DISTRIBUTOR OF STORE')
            AND (clw_bus_entity_assoc.bus_entity2_id IN
                       (SELECT   DISTINCT store_id FROM #LOADING_TABLE_NAME#));


CREATE TABLE tmp_catmatch
AS
   SELECT   #LOADING_TABLE_NAME#.matched_item_id, cat.item_id AS category_id
     FROM   #LOADING_TABLE_NAME#, clw_item cat, clw_catalog_structure
    WHERE       #LOADING_TABLE_NAME#.matched_item_id IS NOT NULL
            AND #LOADING_TABLE_NAME#.category_name = cat.short_desc
            AND cat.item_type_cd = 'CATEGORY'
            AND cat.item_id = clw_catalog_structure.item_id
            AND clw_catalog_structure.catalog_id =
                  #LOADING_TABLE_NAME#.store_catalog_id;

CREATE TABLE tmp_newcats
AS
   SELECT   c.matched_item_id, c.category_id
     FROM   clw_item_assoc a, tmp_catmatch c
    WHERE   c.matched_item_id = a.item1_id AND c.category_id <> a.item2_id;

MERGE INTO   clw_item_assoc a
     USING   tmp_newcats c
        ON   (c.matched_item_id = a.item1_id)
WHEN MATCHED
THEN
   UPDATE SET
      a.item2_id = c.category_id,
      a.mod_date = CURRENT_DATE,
      a.mod_by = '#USER_NAME#';

CREATE TABLE tmp_mtchitems
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
            LOWER(hazmat) AS hazmat,
            certified_companies,
            image,
            image_blob,
            specification,
            specification_blob,
            msds,
            msds_blob,
            matched_item_id,
            store_catalog_id,
            dist.bus_entity_id AS dist_id,
            manuf.bus_entity_id AS manuf_id
     FROM   #LOADING_TABLE_NAME#,
            clw_bus_entity dist,
            clw_bus_entity_assoc dist_assoc,
            clw_bus_entity manuf,
            clw_bus_entity_assoc manuf_assoc
    WHERE       matched_item_id IS NOT NULL
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

MERGE INTO   clw_item_mapping m
     USING   tmp_mtchitems i
        ON   (    i.matched_item_id = m.item_id
              AND m.item_mapping_cd = 'ITEM_DISTRIBUTOR'
              AND m.bus_entity_id = i.dist_id)
WHEN MATCHED
THEN
   UPDATE SET
      m.item_num = i.dist_sku,
      m.item_uom = i.uom,
      m.item_pack = i.pack,
      m.short_desc = i.short_description,
      m.long_desc = i.long_description,
      m.mod_date = CURRENT_DATE,
      m.mod_by = '#USER_NAME#'
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_item_mapping_seq.NEXTVAL,
                    i.matched_item_id,
                    i.dist_id,
                    i.dist_sku,
                    i.uom,
                    i.pack,
                    i.short_description,
                    i.long_description,
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
                );

MERGE INTO   clw_item_mapping m
     USING   tmp_mtchitems i
        ON   (i.matched_item_id = m.item_id
              AND m.item_mapping_cd = 'ITEM_MANUFACTURER')
WHEN MATCHED
THEN
   UPDATE SET
      m.bus_entity_id = i.manuf_id,
      m.item_num = i.mfg_sku,
      m.short_desc = i.short_description,
      m.long_desc = i.long_description,
      m.mod_date = CURRENT_DATE,
      m.mod_by = '#USER_NAME#';
      
MERGE INTO   clw_item_mapping m
     USING   (SELECT   tmp_mtchitems.matched_item_id,
                       tmp_crt_comp.bus_entity_id
                FROM   tmp_crt_comp, tmp_mtchitems
               WHERE   tmp_crt_comp.certified_companies =
                          tmp_mtchitems.certified_companies) i
        ON   (    i.matched_item_id = m.item_id
              AND i.bus_entity_id = m.bus_entity_id
              AND m.item_mapping_cd = 'ITEM_CERTIFIED_COMPANY')
WHEN NOT MATCHED
THEN
   INSERT              (item_mapping_id,
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
       VALUES   (clw_item_mapping_seq.NEXTVAL,
                 i.matched_item_id,
                 i.bus_entity_id,
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
                 NULL);
                 
MERGE INTO   clw_item_meta m
     USING   tmp_mtchitems s
        ON   (m.item_id = s.matched_item_id AND m.name_value = 'PACK')
WHEN MATCHED
THEN
   UPDATE SET
      m.clw_value = s.pack,
      m.mod_date = CURRENT_DATE,
      m.mod_by = '#USER_NAME#'
   WHERE  s.pack IS NOT null
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_item_meta_seq.NEXTVAL,
                    matched_item_id,
                    0,
                    'PACK',
                    pack,
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#'
                )
        WHERE  pack IS NOT null
		;

		
MERGE INTO   clw_item_meta m
     USING   tmp_mtchitems s
        ON   (m.item_id = s.matched_item_id AND m.name_value = 'UOM')
WHEN MATCHED
THEN
   UPDATE SET
      m.clw_value = s.uom,
      m.mod_date = CURRENT_DATE,
      m.mod_by = '#USER_NAME#'
     WHERE  s.uom IS NOT null
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_item_meta_seq.NEXTVAL,
                    matched_item_id,
                    0,
                    'UOM',
                    uom,
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#'
                )
     WHERE  uom IS NOT null
	;

MERGE INTO   clw_item_meta m
     USING   tmp_mtchitems s
        ON   (m.item_id = s.matched_item_id AND m.name_value = 'SIZE')
WHEN MATCHED
THEN
   UPDATE SET
      m.clw_value = s.item_size,
      m.mod_date = CURRENT_DATE,
      m.mod_by = '#USER_NAME#'
     WHERE  s.item_size IS NOT null	  
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_item_meta_seq.NEXTVAL,
                    matched_item_id,
                    0,
                    'SIZE',
                    item_size,
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#'
                )
     WHERE  item_size IS NOT null
		;

MERGE INTO   clw_item_meta m
     USING   tmp_mtchitems s
        ON   (m.item_id = s.matched_item_id AND m.name_value = 'UPC_NUM')
WHEN MATCHED
THEN
   UPDATE SET
      m.clw_value = s.product_upc,
      m.mod_date = CURRENT_DATE,
      m.mod_by = '#USER_NAME#'
     WHERE  s.product_upc IS NOT null
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_item_meta_seq.NEXTVAL,
                    matched_item_id,
                    0,
                    'UPC_NUM',
                    product_upc,
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#'
                )
        WHERE  product_upc IS NOT null
		;

MERGE INTO   clw_item_meta m
     USING   tmp_mtchitems s
        ON   (m.item_id = s.matched_item_id AND m.name_value = 'PKG_UPC_NUM')
WHEN MATCHED
THEN
   UPDATE SET
      m.clw_value = s.pack_upc,
      m.mod_date = CURRENT_DATE,
      m.mod_by = '#USER_NAME#'
     WHERE  s.pack_upc IS NOT null
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_item_meta_seq.NEXTVAL,
                    matched_item_id,
                    0,
                    'PKG_UPC_NUM',
                    pack_upc,
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#'
                )
     WHERE  pack_upc IS NOT null
		;

MERGE INTO   clw_item_meta m
     USING   tmp_mtchitems s
        ON   (m.item_id = s.matched_item_id AND m.name_value = 'COLOR')
WHEN MATCHED
THEN
   UPDATE SET
      m.clw_value = s.color,
      m.mod_date = CURRENT_DATE,
      m.mod_by = '#USER_NAME#'
     WHERE  s.color IS NOT null
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_item_meta_seq.NEXTVAL,
                    matched_item_id,
                    0,
                    'COLOR',
                    color,
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#'
                )
     WHERE  color IS NOT null
	;

MERGE INTO   clw_item_meta m
     USING   tmp_mtchitems s
        ON   (m.item_id = s.matched_item_id AND m.name_value = 'UNSPSC_CD')
WHEN MATCHED
THEN
   UPDATE SET
      m.clw_value = s.unspsc_code,
      m.mod_date = CURRENT_DATE,
      m.mod_by = '#USER_NAME#'
     WHERE  s.unspsc_code IS NOT null
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_item_meta_seq.NEXTVAL,
                    matched_item_id,
                    0,
                    'UNSPSC_CD',
                    unspsc_code,
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#'
                )
     WHERE  unspsc_code IS NOT null
	;

MERGE INTO   clw_item_meta m
     USING   tmp_mtchitems s
        ON   (m.item_id = s.matched_item_id AND m.name_value = 'SHIP_WEIGHT')
WHEN MATCHED
THEN
   UPDATE SET
      m.clw_value = s.shipping_weight,
      m.mod_date = CURRENT_DATE,
      m.mod_by = '#USER_NAME#'
     WHERE  s.shipping_weight IS NOT null	  
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_item_meta_seq.NEXTVAL,
                    matched_item_id,
                    0,
                    'SHIP_WEIGHT',
                    shipping_weight,
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#'
                )
     WHERE  shipping_weight IS NOT null	  
	;

MERGE INTO   clw_item_meta m
     USING   tmp_mtchitems s
        ON   (m.item_id = s.matched_item_id AND m.name_value = 'WEIGHT_UNIT')
WHEN MATCHED
THEN
   UPDATE SET
      m.clw_value = s.weight_unit,
      m.mod_date = CURRENT_DATE,
      m.mod_by = '#USER_NAME#'
     WHERE  s.weight_unit IS NOT null	  
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_item_meta_seq.NEXTVAL,
                    matched_item_id,
                    0,
                    'WEIGHT_UNIT',
                    weight_unit,
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#'
                )
     WHERE  weight_unit IS NOT null	  
	;

MERGE INTO   clw_item_meta m
     USING   tmp_mtchitems s
        ON   (m.item_id = s.matched_item_id AND m.name_value = 'NSN')
WHEN MATCHED
THEN
   UPDATE SET
      m.clw_value = s.nsn,
      m.mod_date = CURRENT_DATE,
      m.mod_by = '#USER_NAME#'
     WHERE  s.nsn IS NOT null	  
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_item_meta_seq.NEXTVAL,
                    matched_item_id,
                    0,
                    'NSN',
                    nsn,
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#'
                )
     WHERE  nsn IS NOT null	  
	;

MERGE INTO   clw_item_meta m
     USING   tmp_mtchitems s
        ON   (m.item_id = s.matched_item_id AND m.name_value = 'CUBE_SIZE')
WHEN MATCHED
THEN
   UPDATE SET
      m.clw_value = s.shipping_cubic_size,
      m.mod_date = CURRENT_DATE,
      m.mod_by = '#USER_NAME#'
     WHERE  s.shipping_cubic_size IS NOT null	  
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_item_meta_seq.NEXTVAL,
                    matched_item_id,
                    0,
                    'CUBE_SIZE',
                    shipping_cubic_size,
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#'
                )
     WHERE  shipping_cubic_size IS NOT null	  
	;

MERGE INTO   clw_item_meta m
     USING   tmp_mtchitems s
        ON   (m.item_id = s.matched_item_id AND m.name_value = 'HAZMAT')
WHEN MATCHED
THEN
   UPDATE SET
      m.clw_value = s.hazmat,
      m.mod_date = CURRENT_DATE,
      m.mod_by = '#USER_NAME#'
     WHERE  s.hazmat IS NOT null	  
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_item_meta_seq.NEXTVAL,
                    matched_item_id,
                    0,
                    'HAZMAT',
                    hazmat,
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#'
                )
     WHERE  hazmat IS NOT null	  
		;

-- Image

MERGE INTO   clw_item_meta m
     USING   (SELECT   *
                FROM   tmp_mtchitems
               WHERE   image_blob IS NOT NULL) i
        ON   (i.matched_item_id = m.item_id AND m.name_value = 'IMAGE')
WHEN MATCHED
THEN
   UPDATE SET m.mod_date = CURRENT_DATE, m.mod_by = '#USER_NAME#'
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_item_meta_seq.NEXTVAL,
                    i.matched_item_id,
                    0,
                    'IMAGE',
                       '/en/products/images/'
                    || i.matched_item_id
                    || '.'
                    || SUBSTR (i.image, INSTR (i.image, '.', -1) + 1),
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#'
                );

MERGE INTO   clw_content c
     USING   (SELECT   clw_item_meta.item_id,
                       clw_item_meta.clw_value,
                       tmp_mtchitems.image_blob,
                       tmp_mtchitems.image
                FROM   tmp_mtchitems, clw_item_meta
               WHERE   image_blob IS NOT NULL
                       AND tmp_mtchitems.matched_item_id =
                             clw_item_meta.item_id
                       AND name_value = 'IMAGE') i
        ON   (c.PATH = '.' || i.clw_value)
WHEN MATCHED
THEN
   UPDATE SET c.binary_data = i.image_blob
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_content_seq.NEXTVAL,
                    'ContentLog',
                    0,
                    'ContentLog',
                    'ACTIVE',
                    'xsuite-app',
                    'x',
                    'x',
                       './en/products/images/'
                    || i.item_id
                    || '.'
                    || SUBSTR (i.image, INSTR (i.image, '.', -1) + 1),
                    'ContentLog',
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#',
                    i.image_blob
                );

-- MSDS
MERGE INTO   clw_item_meta m
     USING   (SELECT   *
                FROM   tmp_mtchitems
               WHERE   msds_blob IS NOT NULL) i
        ON   (i.matched_item_id = m.item_id AND m.name_value = 'MSDS')
WHEN MATCHED
THEN
   UPDATE SET m.mod_date = CURRENT_DATE, m.mod_by = '#USER_NAME#'
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_item_meta_seq.NEXTVAL,
                    i.matched_item_id,
                    0,
                    'MSDS',
                       '/en/products/msds/'
                    || i.matched_item_id
                    || '.'
                    || SUBSTR (i.msds, INSTR (i.msds, '.', -1) + 1),
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#'
                );

MERGE INTO   clw_content c
     USING   (SELECT   clw_item_meta.item_id,
                       clw_item_meta.clw_value,
                       tmp_mtchitems.msds_blob,
                       tmp_mtchitems.msds
                FROM   tmp_mtchitems, clw_item_meta
               WHERE   image_blob IS NOT NULL
                       AND tmp_mtchitems.matched_item_id =
                             clw_item_meta.item_id
                       AND name_value = 'MSDS') i
        ON   (SUBSTR (c.PATH, instr ('/', -1)) =
                 '.' || SUBSTR (i.clw_value, instr ('/', -1)))
WHEN MATCHED
THEN
   UPDATE SET c.binary_data = i.msds_blob
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_content_seq.NEXTVAL,
                    'ContentLog',
                    0,
                    'ContentLog',
                    'ACTIVE',
                    'xsuite-app',
                    'x',
                    'x',
                       './en/products/msds/'
                    || i.item_id
                    || '.'
                    || SUBSTR (i.msds, INSTR (i.msds, '.', -1) + 1),
                    'ContentLog',
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#',
                    i.msds_blob
                );



-- Specification

MERGE INTO   clw_item_meta m
     USING   (SELECT   *
                FROM   tmp_mtchitems
               WHERE   specification_blob IS NOT NULL) i
        ON   (i.matched_item_id = m.item_id AND m.name_value = 'SPEC')
WHEN MATCHED
THEN
   UPDATE SET m.mod_date = CURRENT_DATE, m.mod_by = '#USER_NAME#'
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_item_meta_seq.NEXTVAL,
                    i.matched_item_id,
                    0,
                    'SPEC',
                    '/en/products/spec/' || i.matched_item_id || '.'
                    || SUBSTR
                       (
                          i.specification,
                          INSTR (i.specification, '.', -1) + 1
                       ),
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#'
                );

MERGE INTO   clw_content c
     USING   (SELECT   clw_item_meta.item_id,
                       clw_item_meta.clw_value,
                       tmp_mtchitems.specification_blob,
                       tmp_mtchitems.specification
                FROM   tmp_mtchitems, clw_item_meta
               WHERE   specification_blob IS NOT NULL
                       AND tmp_mtchitems.matched_item_id =
                             clw_item_meta.item_id
                       AND name_value = 'SPEC') i
        ON   (SUBSTR (c.PATH, instr ('/', -1)) =
                 '.' || SUBSTR (i.clw_value, instr ('/', -1)))
WHEN MATCHED
THEN
   UPDATE SET c.binary_data = i.specification_blob
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_content_seq.NEXTVAL,
                    'ContentLog',
                    0,
                    'ContentLog',
                    'ACTIVE',
                    'xsuite-app',
                    'x',
                    'x',
                    './en/products/spec/' || i.item_id || '.'
                    || SUBSTR
                       (
                          i.specification,
                          INSTR (i.specification, '.', -1) + 1
                       ),
                    'ContentLog',
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#',
                    specification_blob
                );