-- I. Master/Physical ASSETS Syncronization
-- I.05 Update manufactur names in clw_asset_table

MERGE INTO   clw_asset asset
     USING   (SELECT   aa.asset_id,
                       be.short_desc,
                       be.bus_entity_id
                FROM   clw_asset aa,
                       clw_bus_entity be
               WHERE   aa.manuf_id = be.bus_entity_id
                 AND   Nvl(aa.manuf_name,' ') != be.short_desc) upd
        ON   (asset.asset_id = upd.asset_id)
WHEN MATCHED
THEN
   UPDATE SET
      asset.manuf_name = upd.short_desc,
      asset.mod_date = SYSDATE,
      asset.mod_by = 'Synchronizer'
;

-- I.1 Update already linked objects
-- I.1.1 Update already linked objects. clw_asset

MERGE INTO   clw_asset child
     USING   (SELECT   clw_asset_master_assoc.child_master_asset_id,
                       parent.short_desc AS parent_short_desc,
                       parent.asset_type_cd AS parent_asset_type_cd,
                       parent.parent_id AS parent_parent_id,
                       parent.status_cd AS parent_status_cd,
                       parent.serial_num AS parent_serial_num,
                       parent.asset_num AS parent_asset_num,
                       parent.manuf_id AS parent_manuf_id,
                       parent.manuf_name AS parent_manuf_name,
                       parent.manuf_sku AS parent_manuf_sku,
                       parent.manuf_type_cd AS parent_manuf_type_cd,
                       parent.mod_by AS parent_mod_by,
                       parent.mod_date AS parent_mod_date,
                       parent.model_number AS parent_model_number,
                       parent.master_asset_id AS parent_master_asset_id
                FROM   clw_asset_master_assoc,
                       clw_asset parent,
                       clw_asset_assoc parent_assoc,
                       tmp_010,
                       clw_asset_assoc child_assoc
               WHERE   clw_asset_master_assoc.parent_master_asset_id =
                          parent.asset_id
                      #AND parent.asset_id = current-parent-asset-id#    
                       AND parent.asset_type_cd = 'MASTER_ASSET'
                       AND clw_asset_master_assoc.child_master_asset_id =
                             child_assoc.asset_id
                       AND parent_assoc.asset_assoc_cd = 'ASSET_STORE'
                       AND child_assoc.asset_assoc_cd = 'ASSET_STORE'
                       AND parent.asset_id = parent_assoc.asset_id
                       -- filter on child stores
                       AND child_assoc.bus_entity_id =
                             tmp_010.bus_entity_id
                       -- filter on parent store
                       AND parent_assoc.bus_entity_id = #PARENT_STORE_ID#) linked
        ON   (child.asset_id = linked.child_master_asset_id)
WHEN MATCHED
THEN
   UPDATE SET
      child.short_desc = linked.parent_short_desc,
      child.asset_type_cd = linked.parent_asset_type_cd,
      child.parent_id = linked.parent_parent_id,
      child.status_cd = linked.parent_status_cd,
      child.serial_num = linked.parent_serial_num,
      -- child.asset_num = linked.parent_asset_num,
      child.manuf_id = linked.parent_manuf_id,
      child.manuf_name = linked.parent_manuf_name,
      child.manuf_sku = linked.parent_manuf_sku,
      child.manuf_type_cd = linked.parent_manuf_type_cd,
      child.mod_by = '#USER_NAME#',
      child.mod_date = CURRENT_DATE,
      child.model_number = linked.parent_model_number,
      child.master_asset_id = linked.parent_master_asset_id;


-- I.2 Export not linked and not mathched objects to child stores

CREATE TABLE tmp_030
(
   child_store_id,
   parent_master_asset_id,
   child_master_asset_id,
   parent_store_id
)
AS
   SELECT   child_store.bus_entity_id AS child_store_id,
            parent.asset_id AS parent_asset_id,
            clw_asset_seq.NEXTVAL,
            #PARENT_STORE_ID# AS parent_store_id
     FROM   clw_asset parent, clw_asset_assoc, tmp_010 child_store
    WHERE       (parent.asset_id = clw_asset_assoc.asset_id)
    		#AND parent.asset_id = current-parent-asset-id#
            AND (parent.asset_type_cd = 'MASTER_ASSET')
            AND (clw_asset_assoc.bus_entity_id = #PARENT_STORE_ID#)
            AND (clw_asset_assoc.asset_assoc_cd = 'ASSET_STORE')
            AND NOT EXISTS
                  (SELECT   *
                     FROM   clw_asset_master_assoc,
                            clw_asset_assoc child_assoc
                    WHERE   clw_asset_master_assoc.parent_master_asset_id =
                               parent.asset_id
                            AND clw_asset_master_assoc.child_master_asset_id =
                                  child_assoc.asset_id
                            AND child_assoc.bus_entity_id =
                                  child_store.bus_entity_id)
            AND NOT EXISTS
                  (SELECT   *
                     FROM   clw_asset child, clw_asset_assoc child_assoc
                    WHERE   (child.asset_id = child_assoc.asset_id)
                            AND (child_assoc.bus_entity_id =
                                    child_store.bus_entity_id)
                            --AND (parent.manuf_name = child.manuf_name)
                            AND (LOWER(child.manuf_name) IN 
                            	(SELECT LOWER(alias) 
                            	   FROM tmp_mnf_alias 
                            	  WHERE tmp_mnf_alias.manuf_id = parent.manuf_id ))
                            AND (TRIM(parent.manuf_sku) = TRIM(child.manuf_sku)));
                            
CREATE TABLE tmp_030a
AS
   SELECT   tmp_030.parent_store_id,
            tmp_030.child_store_id,
            tmp_030.parent_master_asset_id,
            tmp_030.child_master_asset_id,
            clw_asset.short_desc,
            clw_asset.asset_type_cd,
            clw_asset.parent_id,
            clw_asset.status_cd,
            clw_asset.serial_num,
            clw_asset.asset_num,
            clw_asset.manuf_id,
            clw_asset.manuf_name,
            clw_asset.manuf_sku,
            clw_asset.manuf_type_cd,
            clw_asset.add_by,
            clw_asset.add_date,
            clw_asset.mod_by,
            clw_asset.mod_date,
            clw_asset.model_number,
            clw_asset.master_asset_id,
            clw_asset_assoc.asset_assoc_cd,
            clw_asset_assoc.asset_assoc_status_cd,
            clw_asset_assoc.item_id
     FROM   tmp_030, clw_asset, clw_asset_assoc
    WHERE   (tmp_030.parent_master_asset_id =
                clw_asset.asset_id)
            AND (tmp_030.parent_store_id =
                    clw_asset_assoc.bus_entity_id)
            AND (tmp_030.parent_master_asset_id =
                    clw_asset_assoc.asset_id);


INSERT   ALL
  INTO   clw_asset
         (
             asset_id,
             short_desc,
             asset_type_cd,
             parent_id,
             status_cd,
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
             child_master_asset_id,
             short_desc,
             asset_type_cd,
             parent_id,
             status_cd,
             child_master_asset_id,                              -- asset_num,
             manuf_id,
             manuf_name,
             manuf_sku,
             manuf_type_cd,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             model_number,
             master_asset_id
         )
         SELECT * FROM tmp_030a;

INSERT ALL         
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
             child_master_asset_id,
             child_store_id,
             asset_assoc_cd,
             asset_assoc_status_cd,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             item_id
         )
  INTO   clw_asset_master_assoc
         (
             asset_master_assoc_id,
             parent_master_asset_id,
             child_master_asset_id,
             asset_master_assoc_status_cd,
             add_date,
             add_by,
             mod_date,
             mod_by
         )
VALUES
         (
             clw_asset_master_assoc_seq.NEXTVAL,
             parent_master_asset_id,
             child_master_asset_id,
             'ACTIVE',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
         SELECT * FROM tmp_030a;

-- I.3 Export not linked BUT mathched objects to child stores

CREATE TABLE tmp_020a AS
   SELECT   parent_assoc.bus_entity_id AS parent_store_id,
            parent.manuf_id AS parent_manuf_id,
            parent.asset_id AS parent_asset_id,
            child.asset_id AS child_asset_id,
            child.manuf_name AS child_manuf_name
     FROM   clw_asset parent,
            clw_asset child,
            clw_asset_assoc parent_assoc
    WHERE   parent.asset_id = parent_assoc.asset_id
    	      #AND parent.asset_id = current-parent-asset-id#
            AND parent.asset_type_cd = 'MASTER_ASSET'
            AND parent_assoc.bus_entity_id = #PARENT_STORE_ID#
            AND parent_assoc.asset_assoc_cd = 'ASSET_STORE'
            AND child.asset_type_cd = 'MASTER_ASSET'
            AND (TRIM(parent.manuf_sku) = TRIM(child.manuf_sku))
;

CREATE TABLE tmp_020
(
   parent_store_id,
   child_store_id,
   parent_master_asset_id,
   child_master_asset_id
)
AS
   SELECT   tmp_020a.parent_store_id,
            child_store.bus_entity_id AS child_store_id,
            tmp_020a.parent_asset_id,
            tmp_020a.child_asset_id
     FROM   tmp_020a,
            tmp_010 child_store,
            clw_asset_assoc child_assoc
    WHERE   1=1
      AND tmp_020a.child_asset_id = child_assoc.asset_id
      AND child_assoc.bus_entity_id = child_store.bus_entity_id
      AND (LOWER(tmp_020a.child_manuf_name) IN 
          (SELECT LOWER(alias) 
             FROM tmp_mnf_alias 
            WHERE tmp_mnf_alias.manuf_id = tmp_020a.parent_manuf_id))
      AND NOT EXISTS
                  (SELECT   1
                     FROM   clw_asset_master_assoc
                    WHERE   clw_asset_master_assoc.parent_master_asset_id =
                               tmp_020a.parent_asset_id
                      AND   clw_asset_master_assoc.child_master_asset_id =
                               tmp_020a.child_asset_id)
;

INSERT INTO clw_asset_master_assoc
           (
               asset_master_assoc_id,
               parent_master_asset_id,
               child_master_asset_id,
               asset_master_assoc_status_cd,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_asset_master_assoc_seq.NEXTVAL,
            parent_master_asset_id,
            child_master_asset_id,
            'ACTIVE',
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#'
     FROM   tmp_020;

MERGE INTO   clw_asset child
     USING   (SELECT   tmp_020.child_master_asset_id,
                       parent.short_desc AS parent_short_desc,
                       parent.asset_type_cd AS parent_asset_type_cd,
                       parent.parent_id AS parent_parent_id,
                       parent.status_cd AS parent_status_cd,
                       parent.serial_num AS parent_serial_num,
                       parent.asset_num AS parent_asset_num,
                       parent.manuf_id AS parent_manuf_id,
                       parent.manuf_name AS parent_manuf_name,
                       parent.manuf_sku AS parent_manuf_sku,
                       parent.manuf_type_cd AS parent_manuf_type_cd,
                       parent.mod_by AS parent_mod_by,
                       parent.mod_date AS parent_mod_date,
                       parent.model_number AS parent_model_number,
                       parent.master_asset_id AS parent_master_asset_id
                FROM   tmp_020, clw_asset parent
               WHERE   parent.asset_id =
                          tmp_020.parent_master_asset_id)
             matched
        ON   (child.asset_id = matched.child_master_asset_id)
WHEN MATCHED
THEN
   UPDATE SET
      child.short_desc = matched.parent_short_desc,
      child.asset_type_cd = matched.parent_asset_type_cd,
      child.parent_id = matched.parent_parent_id,
      child.status_cd = matched.parent_status_cd,
      child.serial_num = matched.parent_serial_num,
      -- child.asset_num = matched.parent_asset_num,
      child.manuf_id = matched.parent_manuf_id,
      child.manuf_name = matched.parent_manuf_name,
      child.manuf_sku = matched.parent_manuf_sku,
      child.manuf_type_cd = matched.parent_manuf_type_cd,
      child.mod_by = '#USER_NAME#',
      child.mod_date = CURRENT_DATE,
      child.model_number = matched.parent_model_number,
      child.master_asset_id = matched.parent_master_asset_id;

-- I.4 Find physical assets matched to masters and link found

MERGE INTO   clw_asset physical_matched
     USING   (SELECT   physical.asset_id AS physical_asset_id,
                       master.asset_id AS master_asset_id
                FROM   clw_asset physical,
                       clw_asset_assoc physical_assoc,
                       clw_asset master,
                       clw_asset_assoc master_assoc
               WHERE       physical.asset_id = physical_assoc.asset_id
                       AND physical.asset_type_cd = 'ASSET'
                       AND physical.master_asset_id IN (NULL, 0)
                       AND physical_assoc.bus_entity_id IN
                                (SELECT   bus_entity_id
                                   FROM   tmp_010)
                       AND master.asset_id = master_assoc.asset_id
                       AND master.asset_type_cd = 'MASTER_ASSET'
                       AND master_assoc.bus_entity_id IN
                                (SELECT   bus_entity_id
                                   FROM   tmp_010)
                       AND physical_assoc.bus_entity_id =
                             master_assoc.bus_entity_id
                       -- identity criteria
                       --AND physical.manuf_name = master.manuf_name
                       AND LOWER(physical.manuf_name) in 
                       (SELECT LOWER(alias)
                          FROM tmp_mnf_alias
                         WHERE tmp_mnf_alias.manuf_id = master.manuf_id) 
                       AND TRIM(physical.manuf_sku) = TRIM(master.manuf_sku))
             master_matched
        ON   (physical_matched.asset_id = master_matched.physical_asset_id)
WHEN MATCHED
THEN
   UPDATE SET
      physical_matched.master_asset_id = master_matched.master_asset_id,
      physical_matched.mod_by = '#USER_NAME#',
      physical_matched.mod_date = CURRENT_DATE;

-- II. Category synchronization

-- II.1) Process child master assets. Update categories
--    with category configured for parent store (after previous steps)
--    and this category already have assotiations with child store

MERGE INTO   clw_asset child_category
     USING   (SELECT   clw_asset_master_assoc.child_master_asset_id
                          category_id,
                       clw_asset.short_desc,
                       clw_asset.asset_type_cd,
                       clw_asset.parent_id,
                       clw_asset.status_cd,
                       clw_asset.serial_num,
                       clw_asset.asset_num,
                       clw_asset.manuf_id,
                       clw_asset.manuf_name,
                       clw_asset.manuf_sku,
                       clw_asset.manuf_type_cd,
                       clw_asset.mod_by,
                       clw_asset.mod_date,
                       clw_asset.model_number,
                       clw_asset.master_asset_id
                FROM   clw_asset_master_assoc, clw_asset, clw_asset_assoc,
                		clw_asset_assoc asset_assoc_child  
               WHERE   (clw_asset.asset_id =
                           clw_asset_master_assoc.parent_master_asset_id)
                       #AND clw_asset.asset_id IN (SELECT parent_id FROM clw_asset WHERE clw_asset.asset_id = current-parent-asset-id)#
                       
                       AND (clw_asset.asset_id = clw_asset_assoc.asset_id)
                       AND (clw_asset.asset_type_cd = 'CATEGORY')
                       AND (clw_asset_assoc.asset_assoc_cd = 'ASSET_STORE')
                       AND (clw_asset_assoc.bus_entity_id = #PARENT_STORE_ID#)
                       AND (asset_assoc_child.asset_assoc_cd = 'ASSET_STORE')
                       AND (asset_assoc_child.asset_id = clw_asset_master_assoc.child_master_asset_id)
                       AND (asset_assoc_child.bus_entity_id IN (SELECT bus_entity_id FROM tmp_010))
             ) parent_category
        ON   (child_category.asset_id = parent_category.category_id)
WHEN MATCHED
THEN
   UPDATE SET
      child_category.short_desc = parent_category.short_desc,
      child_category.asset_type_cd = parent_category.asset_type_cd,
      child_category.parent_id = parent_category.parent_id,
      child_category.status_cd = parent_category.status_cd,
      child_category.serial_num = parent_category.serial_num,
      -- child_category.asset_num = parent_category.asset_num,
      child_category.manuf_id = parent_category.manuf_id,
      child_category.manuf_name = parent_category.manuf_name,
      child_category.manuf_sku = parent_category.manuf_sku,
      child_category.manuf_type_cd = parent_category.manuf_type_cd,
      child_category.mod_by = '#USER_NAME#',
      child_category.mod_date = CURRENT_DATE,
      child_category.model_number = parent_category.model_number,
      child_category.master_asset_id = parent_category.master_asset_id;

-- II.2) Process master assets
--    with categories configured for parent store
--    and this categories have NOT assotiations with child store and
--    and this categories have not identical categories in appropriate child store

CREATE TABLE tmp_040
AS
   SELECT   master.asset_id,
            master.parent_id AS category_id,
            master_assoc.bus_entity_id AS store_id
     FROM   clw_asset master,
            clw_asset_assoc master_assoc,
            clw_asset category,
            clw_asset_assoc category_assoc
    WHERE       (master.asset_id = master_assoc.asset_id)
            AND (master.parent_id = category.asset_id)
            AND (category.asset_id = category_assoc.asset_id)
            AND (master_assoc.bus_entity_id IN (SELECT   bus_entity_id FROM tmp_010))
            AND (category_assoc.bus_entity_id = #PARENT_STORE_ID#)
            AND NOT EXISTS
                  (SELECT   *
                     FROM   clw_asset_master_assoc, clw_asset_assoc asset_assoc_child
                    WHERE   clw_asset_master_assoc.parent_master_asset_id =
                               category.asset_id
                            AND (asset_assoc_child.asset_assoc_cd = 'ASSET_STORE')
                            AND (asset_assoc_child.asset_id = clw_asset_master_assoc.child_master_asset_id)
                            AND (asset_assoc_child.bus_entity_id = master_assoc.bus_entity_id) 
                  )
            AND NOT EXISTS
                  (SELECT   *
                     FROM   clw_asset child_category,
                            clw_asset_assoc child_assoc
                    WHERE   child_category.asset_id = child_assoc.asset_id
                            AND child_assoc.bus_entity_id =
                                  master_assoc.bus_entity_id
                            AND child_category.asset_type_cd = 'CATEGORY'
                            AND child_category.short_desc =
                                  category.short_desc);

CREATE TABLE tmp_050
AS
     SELECT   category_id,             -- existing asset_id from parent store)
                          store_id -- child store where clone of category should be created
       FROM   tmp_040
   GROUP BY   category_id, store_id;

CREATE TABLE tmp_060
AS
   SELECT   category_id, store_id, clw_asset_seq.NEXTVAL AS new_category_id
     FROM   tmp_050;

INSERT   ALL
  INTO   clw_asset
         (
             asset_id,
             short_desc,
             asset_type_cd,
             parent_id,
             status_cd,
             -- asset_num,
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
             new_category_id,
             short_desc,
             asset_type_cd,
             parent_id,
             status_cd,
             -- asset_num,
             manuf_id,
             manuf_name,
             manuf_sku,
             manuf_type_cd,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             model_number,
             master_asset_id
         )
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
             new_category_id,
             store_id,
             asset_assoc_cd,
             asset_assoc_status_cd,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             item_id
         )
  INTO   clw_asset_master_assoc
         (
             asset_master_assoc_id,
             parent_master_asset_id,
             child_master_asset_id,
             asset_master_assoc_status_cd,
             add_date,
             add_by,
             mod_date,
             mod_by
         )
VALUES
         (
             clw_asset_master_assoc_seq.NEXTVAL,
             asset_id,
             new_category_id,
             'ACTIVE',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
   SELECT   tmp_060.new_category_id,
            tmp_060.store_id,
            clw_asset.asset_id,
            clw_asset.short_desc,
            clw_asset.asset_type_cd,
            clw_asset.parent_id,
            clw_asset.status_cd,
            clw_asset.serial_num,
            clw_asset.asset_num,
            clw_asset.manuf_id,
            clw_asset.manuf_name,
            clw_asset.manuf_sku,
            clw_asset.manuf_type_cd,
            clw_asset.add_by,
            clw_asset.add_date,
            clw_asset.mod_by,
            clw_asset.mod_date,
            clw_asset.model_number,
            clw_asset.master_asset_id,
            clw_asset_assoc.asset_assoc_cd,
            clw_asset_assoc.asset_assoc_status_cd,
            clw_asset_assoc.item_id
     FROM   tmp_060, clw_asset, clw_asset_assoc
    WHERE   tmp_060.category_id = clw_asset.asset_id
            AND clw_asset.asset_id = clw_asset_assoc.asset_id;

-- Update parent_id's (id of category) in appropriate Master Assets with
-- ids of new created(or already existing) child store categories

MERGE INTO   clw_asset target
     USING   (SELECT   child_asset.asset_id,
                       clw_asset_master_assoc.child_master_asset_id
                          child_category_id
                FROM   clw_asset child_asset,
                       clw_asset_assoc child_asset_assoc,
                       clw_asset_assoc parent_category_assoc,
                       clw_asset_master_assoc,
                       clw_asset_assoc
               WHERE   (child_asset.asset_id = child_asset_assoc.asset_id)
                       AND (child_asset.parent_id =
                               parent_category_assoc.asset_id)
                       AND (clw_asset_master_assoc.parent_master_asset_id =
                               child_asset.parent_id)
                       AND (clw_asset_master_assoc.child_master_asset_id =
                               clw_asset_assoc.asset_id)
                       AND (clw_asset_assoc.bus_entity_id =
                               child_asset_assoc.bus_entity_id)
                       AND (child_asset_assoc.bus_entity_id IN (SELECT bus_entity_id FROM tmp_010))
                       AND (child_asset.asset_type_cd = 'MASTER_ASSET')
                       AND (parent_category_assoc.bus_entity_id =  #PARENT_STORE_ID#))
             source
        ON   (target.asset_id = source.asset_id)
WHEN MATCHED
THEN
   UPDATE SET target.parent_id = source.child_category_id;   
   
-- II.3) Process master assets
--    with categories configured for parent store
--    and this categories have NOT assotiations with child store and
--    and this categories HAVE identical categories in appropriate child store

CREATE TABLE tmp_070
AS
   SELECT   master.asset_id,
            child_category.asset_id AS identical_child_category_id,
            master.parent_id AS parent_category_id
     FROM   clw_asset master,
            clw_asset_assoc master_assoc,
            clw_asset category,
            clw_asset_assoc category_assoc,
            clw_asset child_category,
            clw_asset_assoc child_category_assoc
    WHERE       (master.asset_id = master_assoc.asset_id)
            AND (master.parent_id = category.asset_id)
            AND (category.asset_id = category_assoc.asset_id)
            AND (master_assoc.bus_entity_id IN
                       (SELECT   bus_entity_id FROM tmp_010))
            AND (category_assoc.bus_entity_id = #PARENT_STORE_ID#)
            AND child_category.asset_id = child_category_assoc.asset_id
            AND child_category_assoc.bus_entity_id =
                  master_assoc.bus_entity_id
            AND child_category.asset_type_cd = 'CATEGORY'
            AND child_category.short_desc = category.short_desc
            AND NOT EXISTS
                  (SELECT   *
                     FROM   clw_asset_master_assoc
                    WHERE   clw_asset_master_assoc.parent_master_asset_id =
                               category.asset_id
                            AND clw_asset_master_assoc.child_master_asset_id =
                                  child_category.asset_id);

MERGE INTO   clw_asset target
     USING   tmp_070 source
        ON   (target.asset_id = source.asset_id)
WHEN MATCHED
THEN
   UPDATE SET target.parent_id = source.identical_child_category_id;

CREATE TABLE tmp_080
AS
     SELECT   identical_child_category_id, parent_category_id
       FROM   tmp_070
   GROUP BY   identical_child_category_id, parent_category_id;

INSERT   ALL
  INTO   clw_asset_master_assoc
         (
             asset_master_assoc_id,
             parent_master_asset_id,
             child_master_asset_id,
             asset_master_assoc_status_cd,
             add_date,
             add_by,
             mod_date,
             mod_by
         )
VALUES
         (
             clw_asset_master_assoc_seq.NEXTVAL,
             parent_category_id,
             identical_child_category_id,
             'ACTIVE',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
   SELECT   parent_category_id, identical_child_category_id
     FROM   tmp_080;

-- IV. Manufacturers synchronization

-- IV.1 Replace parent manufacturers by child's in child master assets
-- where it possible(matched by manuf name or alias)
MERGE INTO   clw_asset
     USING   (SELECT   child.asset_id AS child_asset_id,
                       MAX(child_manuf.bus_entity_id) AS child_manuf_id
                FROM   (SELECT   clw_asset.asset_id,
                                 clw_asset.manuf_id AS parent_manuf_id,
                                 clw_asset.manuf_name,
                                 clw_asset_assoc.bus_entity_id AS child_store_id 
                          FROM   clw_asset, clw_asset_assoc
                         WHERE   (clw_asset.asset_id =
                                     clw_asset_assoc.asset_id)
                                 AND(clw_asset.asset_type_cd = 'MASTER_ASSET')
                                 AND(clw_asset_assoc.asset_assoc_cd =
                                        'ASSET_STORE')
                                 AND(clw_asset_assoc.bus_entity_id IN
                                           (SELECT   bus_entity_id
                                              FROM   tmp_010)))
                       child,
                       (  SELECT   clw_asset.manuf_id
                            FROM   clw_asset, clw_asset_assoc
                           WHERE   (clw_asset.asset_id =
                                       clw_asset_assoc.asset_id)
                                   AND(clw_asset.asset_type_cd = 'MASTER_ASSET')
                                   AND(clw_asset_assoc.asset_assoc_cd =
                                          'ASSET_STORE')
                                   AND(clw_asset_assoc.bus_entity_id = 
                                             #PARENT_STORE_ID#)
                        GROUP BY   clw_asset.manuf_id) parent,
                       (SELECT   clw_bus_entity.bus_entity_id,
                                 clw_bus_entity.short_desc,
                                 clw_bus_entity_assoc.bus_entity2_id AS child_store_id
                          FROM   clw_bus_entity, clw_bus_entity_assoc
                         WHERE   (clw_bus_entity.bus_entity_id =
                                     clw_bus_entity_assoc.bus_entity1_id)
                                 AND(clw_bus_entity.bus_entity_type_cd =
                                        'MANUFACTURER')
                                 AND(clw_bus_entity_assoc.bus_entity_assoc_cd =
                                        'MANUFACTURER OF STORE')
                                 AND(clw_bus_entity_assoc.bus_entity2_id IN
                                           (SELECT   bus_entity_id
                                              FROM   tmp_010)))
                       child_manuf
               WHERE   child.parent_manuf_id = parent.manuf_id
                       AND child.child_store_id = child_manuf.child_store_id 
                       AND(LOWER(child_manuf.short_desc) IN
                                 (SELECT   LOWER(alias)
                                    FROM   tmp_mnf_alias
                                   WHERE   tmp_mnf_alias.manuf_id =
                                              child.parent_manuf_id))
            GROUP BY   child.asset_id) child_asset_manuf
        ON   (clw_asset.asset_id = child_asset_manuf.child_asset_id)
WHEN MATCHED
THEN
   UPDATE SET
      clw_asset.manuf_id = child_asset_manuf.child_manuf_id,
      clw_asset.manuf_name = 
                 (SELECT   short_desc
                    FROM   clw_bus_entity
                   WHERE   bus_entity_id = child_asset_manuf.child_manuf_id);
      
-- IV.2 Add new manufacturers in child stores and update manuf_id in appropriate child assets

CREATE TABLE tmp_manuf_to_add
AS
     SELECT   0 AS child_manuf_id,
              clw_asset.manuf_id AS parent_manuf_id,
              clw_asset_assoc.bus_entity_id AS child_store_id
       FROM   clw_asset, clw_asset_assoc
      WHERE   (clw_asset.asset_id = clw_asset_assoc.asset_id)
              AND(clw_asset.asset_type_cd = 'MASTER_ASSET')
              AND(clw_asset_assoc.asset_assoc_cd = 'ASSET_STORE')
              AND(clw_asset_assoc.bus_entity_id IN
                        (  SELECT   bus_entity_id FROM tmp_010))
              AND clw_asset.manuf_id IN
                       (SELECT   clw_bus_entity_assoc.bus_entity1_id
                          FROM   clw_bus_entity_assoc
                         WHERE   (clw_bus_entity_assoc.bus_entity_assoc_cd =
                                     'MANUFACTURER OF STORE')
                                 AND(clw_bus_entity_assoc.bus_entity2_id =
                                        #PARENT_STORE_ID#))
   GROUP BY   clw_asset.manuf_id, clw_asset_assoc.bus_entity_id;
   
UPDATE tmp_manuf_to_add SET child_manuf_id = clw_bus_entity_seq.NEXTVAL;     
   
INSERT INTO clw_bus_entity(bus_entity_id,
                           short_desc,
                           long_desc,
                           eff_date,
                           exp_date,
                           bus_entity_type_cd,
                           bus_entity_status_cd,
                           workflow_role_cd,
                           locale_cd,
                           erp_num,
                           add_date,
                           add_by,
                           mod_date,
                           mod_by,
                           time_zone_cd)
   SELECT   child_manuf_id bus_entity_id,
            short_desc,
            long_desc,
            TO_DATE(TO_CHAR(SYSDATE, 'mm/dd/yyyy'), 'mm/dd/yyyy') eff_date,
            NULL exp_date,
            bus_entity_type_cd,
            bus_entity_status_cd,
            workflow_role_cd,
            locale_cd,
            erp_num,
            SYSDATE add_date,
            'ItemSynchronizer' add_by,
            SYSDATE mod_date,
            'ItemSynchronizer' mod_by,
            time_zone_cd
     FROM   clw_bus_entity be, tmp_manuf_to_add t
    WHERE   t.parent_manuf_id = be.bus_entity_id;

INSERT INTO clw_bus_entity_assoc(bus_entity_assoc_id,
                                 bus_entity1_id,
                                 bus_entity2_id,
                                 bus_entity_assoc_cd,
                                 add_date,
                                 add_by,
                                 mod_date,
                                 mod_by)
   SELECT   clw_bus_entity_assoc_seq.NEXTVAL AS bus_entity_assoc_id,
            child_manuf_id AS bus_entity1_id,
            child_store_id AS bus_entity2_id,
            'MANUFACTURER OF STORE' AS bus_entity_assoc_cd,
            SYSDATE add_date,
            'ItemSynchronizer' add_by,
            SYSDATE mod_date,
            'ItemSynchronizer' mod_by
     FROM   tmp_manuf_to_add;   
         
MERGE INTO   clw_asset
     USING   (SELECT   tmp_manuf_to_add.child_manuf_id,
                       clw_asset_assoc.asset_id
                FROM   tmp_manuf_to_add, clw_asset_assoc, clw_asset
               WHERE   (tmp_manuf_to_add.child_store_id =
                           clw_asset_assoc.bus_entity_id)
                       AND(clw_asset.asset_id = clw_asset_assoc.asset_id)
                       AND(clw_asset_assoc.asset_assoc_cd = 'ASSET_STORE')
                       AND(clw_asset.asset_type_cd = 'MASTER_ASSET')
                       AND(tmp_manuf_to_add.parent_manuf_id =
                              clw_asset.manuf_id)) new_manuf
        ON   (clw_asset.asset_id = new_manuf.asset_id)
WHEN MATCHED
THEN
   UPDATE SET clw_asset.manuf_id = new_manuf.child_manuf_id;      
      

      
-- V. Asset content synchronization
CREATE TABLE tmp_150
AS
   SELECT   asset_content.asset_content_id, asset_content.content_id
     FROM   clw_asset_master_assoc parent_to_child,
            clw_asset_assoc store_assoc,
            clw_asset_content asset_content
    WHERE   (parent_to_child.parent_master_asset_id = store_assoc.asset_id)
    		#AND store_assoc.asset_id = current-parent-asset-id#
            AND (parent_to_child.child_master_asset_id =
                    asset_content.asset_id)
            AND (store_assoc.bus_entity_id = #PARENT_STORE_ID#)
            AND (store_assoc.asset_assoc_cd = 'ASSET_STORE');

DELETE FROM   clw_asset_content
      WHERE   asset_content_id IN
                    (SELECT   asset_content_id
                       FROM   tmp_150);

DELETE FROM   clw_content
      WHERE   content_id IN
                    (SELECT   content_id
                       FROM   tmp_150);



CREATE TABLE tmp_160
AS
   SELECT   parent_to_child.parent_master_asset_id,
            parent_to_child.child_master_asset_id,
            asset_content.content_id,
            clw_content_seq.NEXTVAL AS child_content_id,
            asset_content.type_cd,
            asset_content.url
     FROM   clw_asset_master_assoc parent_to_child,
            clw_asset_assoc store_assoc,
            clw_asset_content asset_content
    WHERE   (parent_to_child.parent_master_asset_id = store_assoc.asset_id)
            #AND store_assoc.asset_id = current-parent-asset-id#
            AND (parent_to_child.parent_master_asset_id =
                    asset_content.asset_id)
            AND (store_assoc.bus_entity_id = #PARENT_STORE_ID#);

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
             child_content_id,
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
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             binary_data
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
             child_master_asset_id,
             child_content_id,
             type_cd,
             url,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
   SELECT   to_clone.child_content_id,
            to_clone.child_master_asset_id,
            to_clone.type_cd,
            to_clone.url,
            parent_content.short_desc,
            parent_content.long_desc,
            parent_content.version,
            parent_content.content_type_cd,
            parent_content.content_status_cd,
            parent_content.source_cd,
            parent_content.locale_cd,
            parent_content.language_cd,
            parent_content.item_id,
            parent_content.bus_entity_id,
            parent_content.PATH,
            parent_content.content_usage_cd,
            parent_content.eff_date,
            parent_content.exp_date,
            parent_content.binary_data
     FROM   tmp_160 to_clone, clw_content parent_content
    WHERE   to_clone.content_id = parent_content.content_id;
    
-- VI. Asset Properties synchronization

CREATE TABLE tmp_200
AS
   SELECT   clw_asset_master_assoc.parent_master_asset_id,
            clw_asset_master_assoc.child_master_asset_id,
            clw_asset_property.asset_property_cd,
            clw_asset_property.clw_value
     FROM   clw_asset_property,
            clw_asset_master_assoc,
            clw_asset_assoc,
            clw_asset_assoc child_asset_assoc
    WHERE   (clw_asset_assoc.asset_id = clw_asset_property.asset_id)
            AND (clw_asset_assoc.asset_id =
                    clw_asset_master_assoc.parent_master_asset_id)
            AND (clw_asset_master_assoc.child_master_asset_id =
                    child_asset_assoc.asset_id)
            AND (clw_asset_assoc.bus_entity_id = #PARENT_STORE_ID#)
            #AND clw_asset_assoc.asset_id = current-parent-asset-id#
            AND (child_asset_assoc.bus_entity_id IN
                       (SELECT   bus_entity_id FROM tmp_010));

DELETE FROM   clw_asset_property
      WHERE   clw_asset_property.asset_id IN
                    (     SELECT   child_master_asset_id FROM tmp_200);

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
             child_master_asset_id,
             asset_property_cd,
             clw_value,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE
         )
   SELECT   child_master_asset_id, asset_property_cd, clw_value FROM tmp_200;

DROP TABLE tmp_200 PURGE;      
DROP TABLE tmp_020 PURGE;
DROP TABLE tmp_020a PURGE;
DROP TABLE tmp_030 PURGE;
DROP TABLE tmp_030a PURGE;
DROP TABLE tmp_010 PURGE;
DROP TABLE tmp_040 PURGE;
DROP TABLE tmp_050 PURGE;
DROP TABLE tmp_060 PURGE;
DROP TABLE tmp_070 PURGE;
DROP TABLE tmp_080 PURGE;
DROP TABLE tmp_150 PURGE;
DROP TABLE tmp_160 PURGE;
