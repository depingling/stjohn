--*****************************
-- Syncronize categories
--*****************************

CREATE TABLE tmp_join_store AS
SELECT To_Number(p.clw_value) parent_store_id, pc.catalog_id parent_catalog_id,
       ca.bus_entity_id child_store_id, c.catalog_id child_catalog_id
 FROM clw_property p, clw_catalog_assoc ca, clw_catalog c, clw_catalog_assoc pca, clw_catalog pc
WHERE 1=1
AND p.clw_value = '#PARENT_STORE_ID#'
AND p.SHORT_DESC = 'PARENT_STORE_ID'
AND Nvl(#CHILD_STORE_ID#,p.bus_entity_id) = p.bus_entity_id
AND p.bus_entity_id = ca.bus_entity_id
AND ca.catalog_id = c.catalog_id
AND c.catalog_type_cd = 'STORE'
and pca.bus_entity_id = '#PARENT_STORE_ID#'
AND pca.catalog_id = pc.catalog_id
AND pc.catalog_type_cd = 'STORE'
;



--DROP TABLE tmp_join_categ purge

-- Update with existing relations
CREATE TABLE tmp_upd_categ AS
SELECT i.item_id, chi.item_id AS child_item_id, i.short_desc, i.long_desc
FROM clw_item i, clw_item_master_assoc ima, clw_item chi
WHERE i.item_type_cd = 'CATEGORY'
  AND i.item_id IN
   (SELECT cs.item_id FROM clw_catalog_structure cs
     WHERE catalog_id IN (SELECT parent_catalog_id FROM tmp_join_store))
AND ima.parent_master_item_id = i.item_id
AND chi.item_id = ima.child_master_item_id
AND (
  (Nvl(chi.short_desc,' ') != Nvl(i.short_desc,' ')) OR
  (Nvl(chi.long_desc,' ') != Nvl (i.long_desc,' '))
    )
;

UPDATE clw_item i
   SET (short_desc, long_desc, mod_date, mod_by) =
(SELECT tuc.short_desc, tuc.long_desc, SYSDATE, 'CategSynchronizer'
   FROM tmp_upd_categ tuc WHERE i.item_id = tuc.child_item_id)
WHERE item_id IN (
  SELECT child_item_id FROM tmp_upd_categ
)
;

-- create new relations
CREATE TABLE tmp_categ_id AS 
   SELECT DISTINCT ia.item2_id AS categ_id
     FROM clw_item_assoc ia
   WHERE 1=1
     AND ia.catalog_id IN (SELECT parent_catalog_id FROM tmp_join_store)
     AND ITEM_ASSOC_CD = 'PRODUCT_PARENT_CATEGORY'
;


INSERT INTO tmp_categ_id 
    SELECT ia.item2_id 
      FROM clw_item_assoc ia
     WHERE  ia.catalog_id IN (SELECT parent_catalog_id FROM tmp_join_store)
       AND ITEM_ASSOC_CD = 'CATEGORY_PARENT_CATEGORY'
      START WITH  item1_id IN (SELECT categ_id FROM tmp_categ_id)
      CONNECT BY PRIOR ia.item2_id = ia.item1_id
;

INSERT INTO clw_item_master_assoc (
   ITEM_MASTER_ASSOC_ID, PARENT_MASTER_ITEM_ID, CHILD_MASTER_ITEM_ID,
   ITEM_MASTER_ASSOC_STATUS_CD, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY
)
SELECT
   CLW_ITEM_MASTER_ASSOC_SEQ.NEXTVAL ,
   item_id AS PARENT_MASTER_ITEM_ID,
   child_item_id AS CHILD_MASTER_ITEM_ID,
   'ACTIVE' AS ITEM_MASTER_ASSOC_STATUS_CD,
   SYSDATE ADD_DATE,
   'CategSynchronizer' ADD_BY,
   SYSDATE MOD_DATE,
   'CategSynchronizer' MOD_BY
FROM (
SELECT
  aa.item_id, aa.child_item_id, ima.item_master_assoc_id
FROM
(
SELECT i.item_id, chi.item_id child_item_id
     FROM clw_item i, clw_catalog_structure cs, tmp_join_store js,
     clw_item chi, clw_catalog_structure chcs
WHERE i.item_type_cd = 'CATEGORY'
  AND i.item_id = cs.item_id
  AND i.item_id in (select categ_id from tmp_categ_id)
  AND cs.catalog_id = js.parent_catalog_id
  AND chcs.catalog_id = js.child_catalog_id
  AND chcs.item_id = chi.item_id
  AND chi.item_type_cd = i.item_type_cd
  AND chi.short_desc = i.short_desc
  AND Nvl(chi.long_desc,' ') = Nvl(i.long_desc,' ')
) aa, clw_item_master_assoc ima
WHERE aa.item_id = ima.parent_master_item_id(+)
  AND aa.child_item_id = ima.child_master_item_id(+)
) WHERE item_master_assoc_id IS NULL
;

-- create new categories
CREATE TABLE tmp_ins_categ AS
SELECT clw_item_seq.NEXTVAL AS child_categ_id, item_id AS parent_categ_id,
    parent_catalog_id, child_catalog_id
FROM (
SELECT
  aa.item_id, aa.parent_catalog_id, aa.child_catalog_id,
  bb.item_id AS child_item_id
FROM
(
SELECT i.item_id, i.short_desc, Nvl(i.long_desc,' ') long_desc, i.item_type_cd,
    js.parent_catalog_id, js.child_catalog_id
  FROM clw_item i, clw_catalog_structure cs, tmp_join_store js
 WHERE i.item_type_cd = 'CATEGORY'
  AND i.item_id = cs.item_id
  AND cs.catalog_id = js.parent_catalog_id
  AND i.item_id in (select categ_id from tmp_categ_id)

) aa,
(
SELECT i.item_id, i.short_desc, Nvl(i.long_desc,' ') long_desc, i.item_type_cd,
       js.child_catalog_id
  FROM clw_item i, clw_catalog_structure cs, tmp_join_store js
 WHERE i.item_type_cd = 'CATEGORY'
  AND i.item_id = cs.item_id
  AND cs.catalog_id = js.child_catalog_id
) bb
WHERE aa.short_desc = bb.short_desc (+)
  AND aa.long_desc = bb.long_desc (+)
  AND aa.child_catalog_id = bb.child_catalog_id (+)
)
WHERE child_item_id IS NULL
;


INSERT INTO clw_item (
    ITEM_ID, SHORT_DESC, SKU_NUM, LONG_DESC, EFF_DATE, EXP_DATE, ITEM_TYPE_CD,
    ITEM_STATUS_CD, ITEM_ORDER_NUM, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY
)
SELECT
   tic.child_categ_id AS ITEM_ID,
   i.SHORT_DESC,
   0 SKU_NUM,
   i.LONG_DESC,
   NULL AS EFF_DATE,
   NULL AS EXP_DATE,
   i.ITEM_TYPE_CD,
   i.ITEM_STATUS_CD,
   i.ITEM_ORDER_NUM,
   SYSDATE ADD_DATE,
   'CategSynchronizer' ADD_BY,
   SYSDATE MOD_DATE,
   'CategSynchronizer' MOD_BY
 FROM tmp_ins_categ tic, clw_item i
 WHERE i.item_id = tic.parent_categ_id
;

INSERT INTO clw_catalog_structure (
   CATALOG_STRUCTURE_ID, CATALOG_ID, BUS_ENTITY_ID, CATALOG_STRUCTURE_CD,
   ITEM_ID, CUSTOMER_SKU_NUM, SHORT_DESC, EFF_DATE, EXP_DATE,
   STATUS_CD, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY,
   COST_CENTER_ID, TAX_EXEMPT, ITEM_GROUP_ID,
   SPECIAL_PERMISSION, SORT_ORDER
)
SELECT
   clw_catalog_structure_seq.NEXTVAL AS CATALOG_STRUCTURE_ID,
   tic.child_catalog_id CATALOG_ID,
   NULL BUS_ENTITY_ID,
   cs.CATALOG_STRUCTURE_CD,
   tic.child_categ_id AS ITEM_ID,
   cs.CUSTOMER_SKU_NUM,
   cs.SHORT_DESC,
   EFF_DATE,
   NULL EXP_DATE,
   cs.STATUS_CD,
   SYSDATE ADD_DATE,
   'CategSynchronizer' ADD_BY,
   SYSDATE MOD_DATE,
   'CategSynchronizer' MOD_BY,
   NULL COST_CENTER_ID,
   NULL TAX_EXEMPT,
   NULL ITEM_GROUP_ID,
   cs.SPECIAL_PERMISSION,
   cs.SORT_ORDER
FROM tmp_ins_categ tic, clw_catalog_structure cs
 WHERE tic.parent_categ_id = cs.item_id
   AND tic.parent_catalog_id = cs.catalog_id
;


INSERT INTO clw_item_master_assoc (
   ITEM_MASTER_ASSOC_ID, PARENT_MASTER_ITEM_ID, CHILD_MASTER_ITEM_ID,
   ITEM_MASTER_ASSOC_STATUS_CD, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY
)
SELECT
   CLW_ITEM_MASTER_ASSOC_SEQ.NEXTVAL ,
   parent_categ_id AS PARENT_MASTER_ITEM_ID,
   child_categ_id AS CHILD_MASTER_ITEM_ID,
   'ACTIVE' AS ITEM_MASTER_ASSOC_STATUS_CD,
   SYSDATE ADD_DATE,
   'CategSynchronizer' ADD_BY,
   SYSDATE MOD_DATE,
   'CategSynchronizer' MOD_BY
FROM tmp_ins_categ
;

-----------------------
-- Category Hierarchy
-----------------------
CREATE TABLE tmp_join_cat_ass AS
SELECT ima.child_master_item_id item1_id, ima1.child_master_item_id item2_id,
       ia.item_assoc_cd,
       cs.catalog_id
  FROM clw_item_assoc ia,
       clw_item_master_assoc ima, clw_catalog_structure cs, tmp_join_store js,
       clw_item_master_assoc ima1, clw_catalog_structure cs1
WHERE 1=1
  AND ia.item_assoc_cd = 'CATEGORY_PARENT_CATEGORY'
  AND ia.catalog_id IN (SELECT parent_catalog_id FROM tmp_join_store)
  AND ima.parent_master_item_id = ia.item1_id
  AND cs.item_Id = ima.child_master_item_id
  AND js.child_catalog_id = cs.catalog_id
  AND ima1.parent_master_item_id = ia.item2_id
  AND cs1.item_Id = ima1.child_master_item_id
  AND js.child_catalog_id = cs1.catalog_id
;
CREATE TABLE tmp_upd_item_ass AS
SELECT ia.item_assoc_id, jca.item1_id, jca.item2_id, jca.catalog_id
  FROM clw_item_assoc ia, tmp_join_cat_ass jca
 WHERE jca.item_assoc_cd  = ia.item_assoc_cd
   AND jca.item1_id  = ia.item1_id
   AND jca.catalog_id  = ia.catalog_id
   AND jca.item2_id  != ia.item2_id
   AND jca.item_assoc_cd = ia.item_assoc_cd
;

UPDATE clw_item_assoc ia
   SET (item2_id, mod_date, mod_by) =
(SELECT uia.item2_id, SYSDATE,'CategSynchronizer'
   FROM  tmp_upd_item_ass uia
  WHERE  uia.item_assoc_id = ia.item_assoc_id
) WHERE  ia.item_assoc_id IN (SELECT item_assoc_id FROM tmp_upd_item_ass)
;


DELETE clw_item_assoc WHERE item_assoc_id IN (
SELECT item_assoc_id FROM (
SELECT ia.item_assoc_id, jca.item1_id
  FROM clw_item_assoc ia, tmp_join_cat_ass jca
 WHERE jca.item_assoc_cd (+) = ia.item_assoc_cd
   AND jca.item1_id (+) = ia.item1_id
   AND jca.catalog_id (+) = ia.catalog_id
   AND jca.item2_id (+) = ia.item2_id
   AND jca.item_assoc_cd (+)= ia.item_assoc_cd
   AND ia.catalog_id IN (SELECT child_catalog_id FROM tmp_join_store)
   --AND ia.item_assoc_cd = 'PRODUCT_PARENT_CATEGORY'
   AND ia.item_assoc_cd = 'CATEGORY_PARENT_CATEGORY'
   AND ia.item1_id IN (SELECT child_master_item_id FROM clw_item_master_assoc)
 ) WHERE item1_id IS NULL
 )
;

INSERT INTO clw_item_assoc (
  ITEM_ASSOC_ID, ITEM1_ID, ITEM2_ID, CATALOG_ID,
  ITEM_ASSOC_CD, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY
)
SELECT
   clw_item_assoc_seq.NEXTVAL AS ITEM_ASSOC_ID,
   ITEM1_ID,
   ITEM2_ID,
   CATALOG_ID,
   ITEM_ASSOC_CD,
   SYSDATE ADD_DATE,
   'CategSynchronizer' ADD_BY,
   SYSDATE MOD_DATE,
   'CategSynchronizer' MOD_BY
FROM (
SELECT jca.*, item_assoc_id
 FROM tmp_join_cat_ass jca, clw_item_assoc ia
 WHERE 1=1
   AND jca.item1_id = ia.item1_id(+)
   AND jca.item2_id = ia.item2_id(+)
   AND jca.item_assoc_cd = ia.item_assoc_cd(+)
   AND jca.catalog_id = ia.catalog_id(+)
) WHERE item_assoc_id IS NULL
;

DROP TABLE tmp_join_store purge;
DROP TABLE tmp_upd_categ purge;
DROP TABLE tmp_ins_categ purge;
DROP TABLE tmp_join_cat_ass purge;
DROP TABLE tmp_upd_item_ass purge;
DROP TABLE tmp_categ_id purge;

