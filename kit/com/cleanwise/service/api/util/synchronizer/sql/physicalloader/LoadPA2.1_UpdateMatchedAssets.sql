CREATE TABLE tmp_2105
AS
SELECT   n.action,
            n.store_id,
            n.mfg_sku,
            n.manufacturer,
            n.category_name,
            n.long_description,
            n.account_ref_num,
            n.site_ref_num,
            n.asset_name,
            n.asset_number,
            n.serial_number,
            n.model_number,
            n.acquisition_date,
            n.acquisition_price,
            n.last_hour_meter_reading,
            n.date_last_hour_meter_reading,
            n.date_in_service,
            n.warranty_number,
            n.image,
            n.image_blob,
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
    WHERE   'CATEGORY' = c.asset_type_cd
            AND c.asset_id = ca.asset_id
            AND 'ASSET_STORE' = ca.asset_assoc_cd
            AND ca.bus_entity_id = n.store_id
            AND n.category_name = c.short_desc

            AND 'MANUFACTURER' = m.bus_entity_type_cd
            AND m.bus_entity_id = ma.bus_entity1_id
            AND ma.bus_entity2_id = n.store_id
            AND n.manufacturer = m.short_desc;

ALTER TABLE tmp_2105 ADD master_asset_id NUMBER(38);
ALTER TABLE tmp_2105 ADD account_id NUMBER(38);
ALTER TABLE tmp_2105 ADD site_id NUMBER(38);
ALTER TABLE tmp_2105 ADD matched_asset_id NUMBER(38);

CREATE TABLE tmp_2403
AS
SELECT   n.store_id,
         n.account_ref_num,
         n.asset_number,
         account_site.bus_entity1_id AS site_id,
         account_props.bus_entity_id AS account_id
     FROM   tmp_2105 n,
            clw_bus_entity_assoc store_account,
            clw_bus_entity_assoc account_site,
            clw_property account_props,
            clw_property site_props
    WHERE   store_account.bus_entity2_id = n.store_id
            AND store_account.bus_entity_assoc_cd = 'ACCOUNT OF STORE'
            AND account_props.bus_entity_id = store_account.bus_entity1_id
            AND account_props.short_desc = 'DIST_ACCT_REF_NUM'
            AND account_props.clw_value = n.account_ref_num
            AND account_site.bus_entity2_id = store_account.bus_entity1_id
            AND account_site.bus_entity_assoc_cd = 'SITE OF ACCOUNT'
            AND site_props.bus_entity_id = account_site.bus_entity1_id
            AND site_props.short_desc = 'SITE_REFERENCE_NUMBER'
            AND site_props.clw_value = n.site_ref_num;

MERGE INTO tmp_2105 a
      USING tmp_2403 m
      ON (a.store_id = m.store_id AND
          a.account_ref_num = m.account_ref_num AND
          a.asset_number = m.asset_number)
WHEN MATCHED
THEN UPDATE SET
     a.site_id = m.site_id,
     a.account_id = m.account_id;

CREATE TABLE tmp_2402
AS
SELECT n.store_id,
       n.account_ref_num,
       n.asset_number,
       a.asset_id AS master_asset_id
       
       FROM clw_asset a,
            clw_asset_assoc aa,
            tmp_2105 n
       WHERE a.asset_type_cd = 'MASTER_ASSET' AND
             a.asset_id = aa.asset_id AND
             'ASSET_STORE' = aa.asset_assoc_cd AND
             a.manuf_name = n.manufacturer AND 
             a.manuf_sku = n.mfg_sku AND  
             aa.bus_entity_id = n.store_id;

MERGE INTO tmp_2105 a
      USING tmp_2402 m
      ON (a.store_id = m.store_id AND
          a.account_ref_num = m.account_ref_num AND
          a.asset_number = m.asset_number)
WHEN MATCHED
THEN
   UPDATE SET
        a.master_asset_id = m.master_asset_id;

MERGE INTO tmp_2105 a
      USING (SELECT   a.asset_id,
                      n.store_id,
                      n.account_id,
                      n.asset_number
                  FROM   tmp_2105 n,
                         clw_asset a,
                         clw_bus_entity_assoc store_account,
                         clw_bus_entity_assoc account_site,
                         clw_asset_assoc a_store,
                         clw_asset_assoc a_site
                  WHERE   'ASSET' = a.asset_type_cd AND
                          a.asset_id = a_store.asset_id AND
                          'ASSET_STORE' = a_store.asset_assoc_cd AND
                          a_store.bus_entity_id = n.store_id AND
                          a.asset_id = a_site.asset_id AND
                          'ASSET_SITE' = a_site.asset_assoc_cd AND
                          a_site.bus_entity_id = account_site.bus_entity1_id AND
                          account_site.bus_entity_assoc_cd = 'SITE OF ACCOUNT' AND
                          account_site.bus_entity2_id = store_account.bus_entity1_id AND
                          store_account.bus_entity_assoc_cd = 'ACCOUNT OF STORE' AND
                          store_account.bus_entity2_id = n.store_id AND
                          store_account.bus_entity1_id = n.account_id AND
                          a.asset_num = n.asset_number) m
      ON (a.store_id = m.store_id AND
          a.account_id = m.account_id AND
          a.asset_number = m.asset_number)
WHEN MATCHED
THEN UPDATE SET
     a.matched_asset_id = m.asset_id;

CREATE TABLE tmp_2205
AS
SELECT   *
         FROM   tmp_2105
         WHERE   matched_asset_id IS NOT NULL;

MERGE INTO   clw_asset a
      USING   (SELECT   action,
                        store_id,
                        manuf_id,
                        mfg_sku,
                        manufacturer,
                        category_id,
                        category_name,
                        long_description,
                        image,
                        asset_name,
                        asset_number,
                        serial_number,
                        model_number,
                        assoc_doc_1,
                        assoc_doc_2,
                        assoc_doc_3,
                        master_asset_id,
                        matched_asset_id
                 FROM   tmp_2205) m
      ON (a.asset_id = m.matched_asset_id)
WHEN MATCHED
THEN UPDATE SET
      a.short_desc = m.asset_name,
      a.parent_id = m.category_id,
      a.status_cd = DECODE (m.action, 'D', 'INACTIVE', 'ACTIVE'),
      a.manuf_id = m.manuf_id,
      a.mod_by = '#USER_NAME#',
      a.mod_date = CURRENT_DATE,
      a.serial_num = m.serial_number,
      a.model_number = m.model_number,
      a.asset_num = m.asset_number,
      a.master_asset_id = m.master_asset_id;

MERGE INTO   clw_asset_assoc aa
      USING   (SELECT   matched_asset_id,
                        site_id
                FROM   tmp_2205) m
      ON (aa.asset_id = m.matched_asset_id AND
          aa.asset_assoc_cd = 'ASSET_SITE')
WHEN MATCHED
THEN UPDATE SET
      aa.bus_entity_id = m.site_id;
      
    DROP TABLE tmp_2402 purge;
    DROP TABLE tmp_2403 purge;
