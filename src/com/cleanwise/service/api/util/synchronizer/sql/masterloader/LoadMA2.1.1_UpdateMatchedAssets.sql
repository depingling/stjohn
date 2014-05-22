CREATE TABLE tmp_1105
AS
   SELECT   n.matched_asset_id,
   			n.asset,
            n.action,
            n.store_id,
            n.mfg_sku,
            n.manufacturer,
            n.category_name,
            n.long_description,
            n.image,
            n.image_blob,
            n.msds,
            n.msds_blob,
            n.specification,
            n.specification_blob,
            n.asset_name,
            n.model_number,
            n.assoc_doc_1,
            n.assoc_doc_1_blob,
            n.assoc_doc_2,
            n.assoc_doc_2_blob,
            n.assoc_doc_3,
            n.assoc_doc_3_blob,
            c.asset_id AS category_id,
            m.bus_entity_id AS manuf_id,
            m.short_desc AS manuf_name
     FROM   #LOADING_TABLE_NAME# n,
            clw_asset c,
            clw_asset_assoc ca,
            clw_bus_entity m,
            clw_bus_entity_assoc ma
    WHERE       n.asset = 'TRUE'
            AND 'CATEGORY' = c.asset_type_cd
            AND c.asset_id = ca.asset_id
            AND 'ASSET_STORE' = ca.asset_assoc_cd
            AND ca.bus_entity_id = n.store_id
            AND n.category_name = c.short_desc
            AND 'MANUFACTURER' = m.bus_entity_type_cd
            AND m.bus_entity_id = ma.bus_entity1_id
            AND ma.bus_entity2_id = n.store_id
            AND n.manufacturer = m.short_desc;

CREATE TABLE tmp_1205
AS
   SELECT   *
     FROM   tmp_1105;

MERGE INTO   clw_asset a
     USING   (SELECT   matched_asset_id,
     				   asset,
                       action,
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
                FROM   tmp_1205) m
        ON   (a.asset_id = m.matched_asset_id)
WHEN MATCHED
THEN
   UPDATE SET
      a.short_desc = m.asset_name,
      a.parent_id = category_id,
      a.status_cd = DECODE (m.action, 'D', 'INACTIVE', 'ACTIVE'),
      a.manuf_id = m.manuf_id,
      a.manuf_name = m.manufacturer,
      a.manuf_sku = m.mfg_sku,
      a.mod_by = '#USER_NAME#',
      a.mod_date = CURRENT_DATE,
      a.model_number = m.model_number;
