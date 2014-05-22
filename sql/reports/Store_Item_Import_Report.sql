  SELECT   c.catalog_id AS "Catalog Id",
           c.short_desc AS "Catalog Name",
           i.sku_num AS "Sku Num",
           i.short_desc AS "Short Description",
           i.long_desc AS "Long Description",
           cate.short_desc AS "Category",
           be.short_desc AS "Manufacturer",
           map.item_num AS "Manuf. Sku",
           uom.clw_value AS "Uom",
           pack.clw_value AS "Pack",
           i_size.clw_value AS "Size",
           color.clw_value AS "Color",
           price.clw_value AS "List Price",
           dist.short_desc AS "Dist Name",
           im.item_num AS "Dist SKU",
           im.item_pack AS "Dist Pack",
           im.item_uom AS "Dist UOM"
    FROM   clw_catalog c,
           clw_catalog_structure ci,
           clw_item i,
           clw_item cate,
           clw_item_assoc ia,
           clw_item_mapping map,
           clw_bus_entity be,
           (SELECT   *
              FROM   clw_item_mapping
             WHERE   item_mapping_cd = 'ITEM_DISTRIBUTOR') im,
           clw_catalog_structure cs,
           clw_bus_entity dist,
           (SELECT   *
              FROM   clw_item_meta im
             WHERE   im.name_value = 'UOM') uom,
           (SELECT   *
              FROM   clw_item_meta im
             WHERE   im.name_value = 'PACK') pack,
           (SELECT   *
              FROM   clw_item_meta im
             WHERE   im.name_value = 'SIZE') i_size,
           (SELECT   *
              FROM   clw_item_meta im
             WHERE   im.name_value = 'COLOR') color,
           (SELECT   *
              FROM   clw_item_meta im
             WHERE   im.name_value = 'LIST_PRICE') price
   WHERE   c.catalog_id =
              (SELECT   CATALOG.CATALOG_ID
                 FROM   CLW_CATALOG_ASSOC CATALOG_ASSOC, CLW_CATALOG CATALOG
                WHERE       (CATALOG.CATALOG_ID = CATALOG_ASSOC.CATALOG_ID)
                        AND (CATALOG_ASSOC.BUS_ENTITY_ID = #STORE#)
                        AND (CATALOG_ASSOC.CATALOG_ASSOC_CD = 'CATALOG_STORE')
                        AND (CATALOG.CATALOG_TYPE_CD = 'STORE'))
           AND ci.catalog_id = c.catalog_id
           AND i.item_id = uom.item_id(+)
           AND i.item_id = pack.item_id(+)
           AND i.item_id = i_size.item_id(+)
           AND i.item_id = color.item_id(+)
           AND i.item_id = price.item_id(+)
           AND i.item_id = ci.item_id
           AND i.item_id = map.item_id
           AND map.item_mapping_cd = 'ITEM_MANUFACTURER'
           AND map.bus_entity_id = be.bus_entity_id
           AND cs.item_id = im.item_id(+)
           AND cs.bus_entity_id = im.bus_entity_id(+)
           AND cs.bus_entity_id = dist.bus_entity_id(+)
           AND cs.catalog_id = c.catalog_id
           AND cs.item_id = i.item_id
           AND i.item_id = ia.item1_id
           AND cate.item_id = ia.item2_id
           AND ia.catalog_id = c.catalog_id
           AND i.item_id = NVL ('#ITEM_OPT#', i.item_id)
           AND be.bus_entity_id = NVL ('#MANUFACTURER_OPT#', be.bus_entity_id)
           AND (cs.bus_entity_id = '#DISTRIBUTOR_OPT#'
                OR '#DISTRIBUTOR_OPT#' IS NULL)
ORDER BY   i.sku_num