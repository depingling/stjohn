
   /*
1. Tables:
clw_item,
clw_catalog_structure
clw_item_mapping,
clw_item_meta,
clw_item_assoc
clw_context,
*/

CREATE TABLE tmp_join_store AS
SELECT To_Number(p.clw_value) parent_store_id, ca.bus_entity_id child_store_id, c.catalog_id child_catalog_id
 FROM clw_property p, clw_catalog_assoc ca, clw_catalog c
WHERE 1=1
AND p.clw_value = '#PARENT_STORE_ID#'
--'408353'
--'393706'
AND p.SHORT_DESC = 'PARENT_STORE_ID'
AND Nvl(#CHILD_STORE_ID#,p.bus_entity_id) = p.bus_entity_id
AND p.bus_entity_id = ca.bus_entity_id
AND ca.catalog_id = c.catalog_id
AND c.catalog_type_cd = 'STORE'
;

--SELECT * FROM tmp_join_store
CREATE TABLE tmp_join_dist AS
SELECT js.parent_store_id, d.bus_entity_id parent_dist_id,
       js.child_store_id, chd.bus_entity_id child_dist_id
FROM tmp_join_store js, clw_bus_entity_assoc da, clw_bus_entity d,
    clw_bus_entity_assoc chda, clw_bus_entity chd
WHERE js.parent_store_id = da.bus_entity2_id
  AND da.bus_entity_assoc_cd =  'DISTRIBUTOR OF STORE'
  AND da.bus_entity1_id = d.bus_entity_id
  AND js.child_store_id = chda.bus_entity2_id
  AND js.child_store_id = chda.bus_entity2_id
  AND chda.bus_entity_assoc_cd =  'DISTRIBUTOR OF STORE'
  AND chda.bus_entity1_id = chd.bus_entity_id
  AND chd.short_desc = d.short_desc
;

CREATE TABLE tmp_item AS
SELECT ca.bus_entity_id store_id, c.catalog_id,  i.item_id,
       im.item_num manuf_sku, im.bus_entity_id manuf_id, be.short_desc manuf_name, uom.clw_value uom
FROM clw_item i, clw_catalog_structure cs, clw_catalog_assoc ca, clw_catalog c, clw_item_mapping im, clw_bus_entity be,
     clw_item_meta uom
WHERE ca.bus_entity_id = #PARENT_STORE_ID#
AND ca.catalog_id = c.catalog_id
AND c.catalog_type_cd = 'STORE'
AND cs.catalog_id = c.catalog_id
AND Nvl(#PARENT_ITEM_ID#, i.item_id) = i.item_id
AND i.item_id = cs.item_id
AND i.item_id = im.item_id
AND im.item_mapping_cd = 'ITEM_MANUFACTURER'
AND im.bus_entity_id = be.bus_entity_id
AND uom.item_id = i.item_id
AND uom.name_value = 'UOM'
AND i.item_type_cd = 'PRODUCT'
;

CREATE TABLE tmp_categ AS
SELECT DISTINCT i.store_id, i.catalog_id, c.item_id categ_id, c.short_desc categ_name,
c.long_desc categ_name1, ia.item_assoc_cd, 1 categ_level
FROM tmp_item i, clw_item c, clw_item_assoc ia
WHERE ia.catalog_id = i.catalog_id
AND ia.item1_id = i.item_id
AND ia.item2_id = c.item_id
;
CREATE TABLE tmp_join_categ AS
SELECT * FROM tmp_categ c, tmp_join_store t
;

ALTER TABLE tmp_join_categ ADD child_categ_id NUMBER (38)
;

UPDATE tmp_join_categ t SET child_categ_id = (
SELECT
i.item_id
FROM clw_item i, clw_catalog_structure cs
WHERE Lower(Trim(i.short_desc)) = Lower(Trim(t.CATEG_NAME))
AND Nvl(Lower(Trim(i.long_desc)),' ') = Nvl(Lower(Trim(t.CATEG_NAME1)),' ')
AND i.item_id = cs.item_id
AND cs.catalog_id = t.child_catalog_id
)
;

--*****************************
-- Syncronyze manufacturers
--*****************************

--SELECT DISTINCT manuf_id, manuf_name FROM tmp_item_synch
--WHERE manuf_id IS NOT null         

CREATE TABLE tmp_manuf AS
SELECT be.bus_entity_id manuf_id, be.short_desc manuf_name,
   REPLACE(Chr(10)||
   Lower(be.short_desc||Chr(10)||
         Translate( p.clw_value, Chr(13)||',', Chr(10)||Chr(10))),' ','')||
   Chr(10)  other_names
FROM clw_property p, clw_bus_entity be
WHERE p.short_desc(+) = 'OTHER_NAMES'
AND be.bus_entity_id = p.bus_entity_id (+)
AND be.bus_entity_id IN (
SELECT DISTINCT manuf_id FROM tmp_item
)
;

--SELECT * FROM tmp_manuf

CREATE TABLE tmp_child_manuf AS
SELECT tm.manuf_id parent_manuf_id, be.bus_entity_id child_manuf_id, tm.manuf_name parent_manuf_name,
       be.short_desc child_manuf_name, t.parent_store_id, t.child_store_id
FROM clw_bus_entity be, clw_bus_entity_assoc bea, tmp_join_store t, tmp_manuf tm
WHERE be.bus_entity_id = bea.bus_entity1_id
AND t.CHILD_STORE_ID = bea.bus_entity2_id
AND bea.BUS_ENTITY_ASSOC_CD = 'MANUFACTURER OF STORE'
AND InStr (tm.other_names, Lower(Chr(10)||REPLACE (be.short_desc,' ','')||Chr(10))) > 0
;

--SELECT * FROM tmp_child_manuf

CREATE TABLE tmp_manuf_to_add AS
SELECT
clw_bus_entity_seq.NEXTVAL child_manuf_id,
parent_manuf_id, manuf_name,parent_store_id, child_store_id
FROM
(
SELECT aa.MANUF_ID parent_manuf_id, aa.manuf_name, aa.child_store_id, aa.parent_store_id, c.child_store_id cs_id from
(
SELECT MANUF_ID, MANUF_NAME,CHILD_STORE_ID, parent_store_id
FROM  tmp_manuf tm, tmp_join_store s
) aa,
tmp_child_manuf c
WHERE c.parent_manuf_id (+)= aa.manuf_id
AND c.child_store_id (+)= aa.child_store_id
)
WHERE cs_id IS null
;

--SELECT * FROM  tmp_manuf_to_add

INSERT INTO clw_bus_entity (
BUS_ENTITY_ID, SHORT_DESC, LONG_DESC, EFF_DATE, EXP_DATE, BUS_ENTITY_TYPE_CD, BUS_ENTITY_STATUS_CD,
WORKFLOW_ROLE_CD, LOCALE_CD, ERP_NUM, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY, TIME_ZONE_CD
)
SELECT
CHILD_MANUF_ID BUS_ENTITY_ID,
SHORT_DESC,
LONG_DESC,
To_Date( To_Char(SYSDATE,'mm/dd/yyyy'),'mm/dd/yyyy') EFF_DATE,
NULL EXP_DATE,
BUS_ENTITY_TYPE_CD,
BUS_ENTITY_STATUS_CD,
WORKFLOW_ROLE_CD,
LOCALE_CD,
ERP_NUM,
SYSDATE ADD_DATE,
'ItemSynchronizer' ADD_BY,
SYSDATE MOD_DATE,
'ItemSynchronizer' MOD_BY,
TIME_ZONE_CD
FROM clw_bus_entity be, tmp_manuf_to_add t
WHERE t.parent_manuf_id = be.bus_entity_id
;

INSERT INTO clw_bus_entity_assoc (
BUS_ENTITY_ASSOC_ID, BUS_ENTITY1_ID, BUS_ENTITY2_ID, BUS_ENTITY_ASSOC_CD,
ADD_DATE, ADD_BY, MOD_DATE, MOD_BY
)
SELECT
clw_bus_entity_assoc_seq.NEXTVAL AS BUS_ENTITY_ASSOC_ID,
CHILD_MANUF_ID AS BUS_ENTITY1_ID,
CHILD_STORE_ID AS BUS_ENTITY2_ID,
'MANUFACTURER OF STORE' AS BUS_ENTITY_ASSOC_CD,
SYSDATE ADD_DATE,
'ItemSynchronizer' ADD_BY,
SYSDATE MOD_DATE,
'ItemSynchronizer' MOD_BY
FROM tmp_manuf_to_add
;

INSERT INTO tmp_child_manuf (
PARENT_MANUF_ID, CHILD_MANUF_ID, PARENT_MANUF_NAME, CHILD_MANUF_NAME, PARENT_STORE_ID, CHILD_STORE_ID
)
SELECT PARENT_MANUF_ID,CHILD_MANUF_ID, MANUF_NAME,MANUF_NAME,PARENT_STORE_ID,CHILD_STORE_ID
FROM tmp_manuf_to_add
;

--SELECT * FROM tmp_child_manuf

--*********************************
-- Synchronize Items
--*********************************


CREATE TABLE tmp_child_item AS
SELECT i.item_id child_item_id, c.catalog_id child_catalog_id, im.item_num child_manuf_sku, im.bus_entity_id child_manuf_id,
      be.short_desc child_manuf_name, uom.clw_value child_uom, ca.bus_entity_id child_store_id, m.parent_manuf_id
FROM clw_item i, clw_catalog_structure cs, clw_catalog_assoc ca, clw_catalog c, clw_item_mapping im, clw_bus_entity be,
     clw_item_meta uom, tmp_join_store s, tmp_child_manuf m
WHERE ca.bus_entity_id = s.child_store_id
AND ca.catalog_id = c.catalog_id
AND c.catalog_type_cd = 'STORE'
AND cs.catalog_id = c.catalog_id
AND i.item_id = cs.item_id
AND i.item_id = im.item_id
AND im.item_mapping_cd = 'ITEM_MANUFACTURER'
AND im.bus_entity_id = be.bus_entity_id
AND uom.item_id = i.item_id
AND uom.name_value = 'UOM'
AND i.item_type_cd = 'PRODUCT'
AND m.child_manuf_id = im.bus_entity_id
;

INSERT INTO tmp_child_item
SELECT i.item_id child_item_id, c.catalog_id child_catalog_id, im.item_num child_manuf_sku, im.bus_entity_id child_manuf_id,
      be.short_desc child_manuf_name, uom.clw_value child_uom, ca.bus_entity_id child_store_id, pim.bus_entity_id AS parent_manuf_id
FROM clw_item i, clw_catalog_structure cs, clw_catalog_assoc ca, clw_catalog c, clw_item_mapping im, clw_bus_entity be,
     clw_item_meta uom, tmp_join_store s, clw_item_master_assoc ima, clw_item_mapping pim
WHERE ca.bus_entity_id = s.child_store_id
AND ca.catalog_id = c.catalog_id
AND c.catalog_type_cd = 'STORE'
AND cs.catalog_id = c.catalog_id
AND i.item_id = cs.item_id
AND i.item_id = im.item_id
AND im.item_mapping_cd = 'ITEM_MANUFACTURER'
AND im.bus_entity_id = be.bus_entity_id
AND uom.item_id = i.item_id
AND uom.name_value = 'UOM'
AND i.item_type_cd = 'PRODUCT'
AND ima.child_master_item_id = i.item_id
AND ima.parent_master_item_id = pim.item_id
AND pim.item_mapping_cd = 'ITEM_MANUFACTURER'
AND i.item_id NOT IN (SELECT child_item_id FROM tmp_child_item)
;

--SELECT * FROM tmp_child_item

--remove duplicated skus
DELETE FROM tmp_item WHERE (TRIM(MANUF_SKU),MANUF_ID,UOM) IN
(
SELECT TRIM(MANUF_SKU),MANUF_ID,UOM FROM tmp_item
GROUP BY TRIM(MANUF_SKU),MANUF_ID,UOM
HAVING Count(*) > 1
)
;

--SELECT * FROM tmp_item
--delete clw_item_master_assoc


CREATE TABLE tmp_join_item AS
SELECT i.*, ci.*
   FROM tmp_item i, tmp_child_item ci, clw_item_master_assoc ma
WHERE 1=1
AND i.item_id = ma.parent_master_item_id
AND ci.child_item_id = ma.child_master_item_id
;
--SELECT * FROM tmp_join_item

INSERT INTO  tmp_join_item
SELECT * FROM tmp_item i, tmp_child_item ci
WHERE 1=1
AND i.uom = ci.child_uom
AND i.manuf_id = ci.parent_manuf_id
AND TRIM(i.manuf_sku) = TRIM(ci.child_manuf_sku)
AND NOT EXISTS
 (SELECT 1 FROM tmp_join_item j
   WHERE j.item_id = i.item_id
   AND j.child_catalog_id = ci.child_catalog_id)
;

-- *****************************
-- Create new items
-- *****************************
CREATE TABLE tmp_item_mult AS
SELECT * FROM tmp_item i, tmp_join_store s
;

CREATE TABLE tmp_item_to_add AS
SELECT
STORE_ID, CATALOG_ID, ITEM_ID, MANUF_SKU, MANUF_ID, MANUF_NAME, UOM,
clw_item_seq.NEXTVAL AS CHILD_ITEM_ID,
CHILD_CATALOG_ID, MANUF_SKU AS CHILD_MANUF_SKU, UOM AS CHILD_UOM, CHILD_STORE_ID, MANUF_ID AS PARENT_MANUF_ID
FROM (
SELECT i.*, ci.child_item_id FROM tmp_item_mult i, tmp_child_item ci
WHERE i.uom = ci.child_uom(+)
AND i.manuf_id = ci.parent_manuf_id(+)
AND TRIM(i.manuf_sku) = TRIM(ci.child_manuf_sku(+))
AND i.child_store_id = ci.child_store_id(+)
) aa
WHERE child_item_id IS null
AND NOT EXISTS
 (SELECT 1 FROM tmp_join_item j
   WHERE j.item_id = aa.item_id
   AND j.child_catalog_id = aa.child_catalog_id)
;

--SELECT * FROM tmp_item_to_add
INSERT INTO clw_item (
ITEM_ID, SHORT_DESC, SKU_NUM, LONG_DESC, EFF_DATE, EXP_DATE, ITEM_TYPE_CD, ITEM_STATUS_CD, ITEM_ORDER_NUM,
ADD_DATE, ADD_BY, MOD_DATE, MOD_BY
)
SELECT
a.child_item_id ITEM_ID,
i.SHORT_DESC,
a.child_item_id+10000 SKU_NUM,
i.LONG_DESC,
To_Date( To_Char(SYSDATE,'mm/dd/yyyy'),'mm/dd/yyyy') EFF_DATE,
NULL EXP_DATE,
i.ITEM_TYPE_CD,
i.ITEM_STATUS_CD,
0 ITEM_ORDER_NUM,
SYSDATE ADD_DATE,
'ItemSynchronizer' ADD_BY,
SYSDATE MOD_DATE,
'ItemSynchronizer' MOD_BY
FROM tmp_item_to_add a, clw_item i
WHERE i.item_id = a.item_id
;

INSERT INTO CLW_ITEM_MASTER_ASSOC (
ITEM_MASTER_ASSOC_ID, PARENT_MASTER_ITEM_ID, CHILD_MASTER_ITEM_ID, ITEM_MASTER_ASSOC_STATUS_CD,
ADD_DATE, ADD_BY, MOD_DATE, MOD_BY
)
SELECT
clw_item_master_assoc_seq.NEXTVAL AS ITEM_MASTER_ASSOC_ID,
a.item_id AS PARENT_MASTER_ITEM_ID,
a.child_item_id AS CHILD_MASTER_ITEM_ID,
'ACTIVE' AS ITEM_MASTER_ASSOC_STATUS_CD,
SYSDATE ADD_DATE,
'ItemSynchronizer' ADD_BY,
SYSDATE MOD_DATE,
'ItemSynchronizer' MOD_BY
FROM tmp_item_to_add a
;


INSERT INTO clw_catalog_structure (
CATALOG_STRUCTURE_ID, CATALOG_ID, BUS_ENTITY_ID, CATALOG_STRUCTURE_CD, ITEM_ID,
CUSTOMER_SKU_NUM, SHORT_DESC, EFF_DATE, EXP_DATE, STATUS_CD, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY,
COST_CENTER_ID, TAX_EXEMPT, ITEM_GROUP_ID, SPECIAL_PERMISSION, SORT_ORDER
)
SELECT
   clw_catalog_structure_seq.NEXTVAL CATALOG_STRUCTURE_ID,
   child_catalog_id CATALOG_ID,
   NULL BUS_ENTITY_ID,
  'CATALOG_PRODUCT'  CATALOG_STRUCTURE_CD,
  child_item_id AS ITEM_ID,
  child_item_id+10000 AS CUSTOMER_SKU_NUM,
  cs.SHORT_DESC,
  To_Date( To_Char(SYSDATE,'mm/dd/yyyy'),'mm/dd/yyyy') EFF_DATE,
  NULL EXP_DATE,
  'ACTIVE' STATUS_CD,
  SYSDATE ADD_DATE,
  'ItemSynchronizer' ADD_BY,
  SYSDATE MOD_DATE,
  'ItemSynchronizer' MOD_BY,
  NULL COST_CENTER_ID,
  NULL TAX_EXEMPT,
  NULL ITEM_GROUP_ID,
  NULL SPECIAL_PERMISSION,
  NULL SORT_ORDER
FROM tmp_item_to_add t, clw_catalog_structure cs
 WHERE t.item_id = cs.item_id
   AND t.catalog_id = cs.catalog_id
;

--SELECT Count(*) FROM tmp_item_to_add

INSERT INTO  clw_item_mapping (
ITEM_MAPPING_ID, ITEM_ID, BUS_ENTITY_ID, ITEM_NUM, ITEM_UOM, ITEM_PACK, SHORT_DESC, LONG_DESC,
ITEM_MAPPING_CD, EFF_DATE, EXP_DATE, STATUS_CD, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY, UOM_CONV_MULTIPLIER,
STANDARD_PRODUCT_LIST)
SELECT
clw_item_mapping_seq.NEXTVAL AS ITEM_MAPPING_ID,
a.child_item_id AS ITEM_ID,
m.child_manuf_id AS BUS_ENTITY_ID,
im.ITEM_NUM,
im.ITEM_UOM,
im.ITEM_PACK,
im.SHORT_DESC,
im.LONG_DESC,
im.ITEM_MAPPING_CD,
NULL EFF_DATE,
NULL EXP_DATE,
'ACTIVE' STATUS_CD,
SYSDATE ADD_DATE,
'ItemSynchronizer' ADD_BY,
SYSDATE MOD_DATE,
'ItemSynchronizer' MOD_BY,
NULL UOM_CONV_MULTIPLIER,
NULL STANDARD_PRODUCT_LIST
FROM tmp_item_to_add a, tmp_child_manuf m, clw_item_mapping im
WHERE a.manuf_id = m.PARENT_MANUF_ID
AND a.child_store_id = m.child_store_id
AND im.item_id = a.item_id
AND im.bus_entity_id = a.manuf_id
AND im.item_mapping_cd = 'ITEM_MANUFACTURER'
;

INSERT INTO  clw_item_mapping (
ITEM_MAPPING_ID, ITEM_ID, BUS_ENTITY_ID, ITEM_NUM, ITEM_UOM, ITEM_PACK, SHORT_DESC, LONG_DESC,
ITEM_MAPPING_CD, EFF_DATE, EXP_DATE, STATUS_CD, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY, UOM_CONV_MULTIPLIER,
STANDARD_PRODUCT_LIST)
SELECT
clw_item_mapping_seq.NEXTVAL AS ITEM_MAPPING_ID,
a.child_item_id AS ITEM_ID,
im.bus_entity_id AS BUS_ENTITY_ID,
im.ITEM_NUM,
im.ITEM_UOM,
im.ITEM_PACK,
im.SHORT_DESC,
im.LONG_DESC,
im.ITEM_MAPPING_CD,
NULL EFF_DATE,
NULL EXP_DATE,
'ACTIVE' STATUS_CD,
SYSDATE ADD_DATE,
'ItemSynchronizer' ADD_BY,
SYSDATE MOD_DATE,
'ItemSynchronizer' MOD_BY,
NULL UOM_CONV_MULTIPLIER,
NULL STANDARD_PRODUCT_LIST
FROM tmp_item_to_add a, clw_item_mapping im
WHERE im.item_id = a.item_id
AND im.item_mapping_cd = 'ITEM_CERTIFIED_COMPANY'
;

INSERT INTO clw_item_meta (
ITEM_META_ID, ITEM_ID, VALUE_ID, NAME_VALUE,
CLW_VALUE, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY
)
SELECT
clw_item_meta_seq.NEXTVAL AS ITEM_META_ID,
a.child_item_id AS ITEM_ID,
im.VALUE_ID,
im.NAME_VALUE,
im.CLW_VALUE,
SYSDATE ADD_DATE,
'ItemSynchronizer' ADD_BY,
SYSDATE MOD_DATE,
'ItemSynchronizer' MOD_BY
FROM tmp_item_to_add a, clw_item_meta im
WHERE 1=1
AND im.item_id = a.item_id
AND im.name_value NOT IN ('IMAGE','DED','MSDS','SPEC','THUMBNAIL')
;

CREATE TABLE tmp_meta_content AS
SELECT
a.item_id,
a.child_item_id,
im.VALUE_ID,
im.NAME_VALUE,
im.CLW_VALUE,
SubStr(im.clw_value,1, InStr(im.clw_value, '/',-1))||To_Char(a.child_item_id)||
     SubStr(im.clw_value, InStr(im.clw_value,'.',-1)) AS child_value
FROM tmp_item_to_add a, clw_item_meta im
WHERE 1=1
AND im.item_id = a.item_id
AND im.name_value IN ('IMAGE','DED','MSDS','SPEC','THUMBNAIL')
;

--SELECT * FROM tmp_meta_content

INSERT INTO clw_item_meta (
ITEM_META_ID, ITEM_ID, VALUE_ID, NAME_VALUE,
CLW_VALUE, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY
)
SELECT
clw_item_meta_seq.NEXTVAL AS ITEM_META_ID,
mc.child_item_id AS ITEM_ID,
mc.VALUE_ID,
mc.NAME_VALUE,
mc.CHILD_VALUE AS CLW_VALUE,
SYSDATE ADD_DATE,
'ItemSynchronizer' ADD_BY,
SYSDATE MOD_DATE,
'ItemSynchronizer' MOD_BY
FROM tmp_meta_content mc
;



INSERT INTO clw_content (
CONTENT_ID, SHORT_DESC, LONG_DESC, VERSION,
CONTENT_TYPE_CD, CONTENT_STATUS_CD,
SOURCE_CD, LOCALE_CD, LANGUAGE_CD, ITEM_ID,
BUS_ENTITY_ID, PATH, CONTENT_USAGE_CD, EFF_DATE,
EXP_DATE, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY,
BINARY_DATA
)
SELECT
clw_content_seq.NEXTVAL AS CONTENT_ID,
c.SHORT_DESC, c.LONG_DESC, c.VERSION,
c.CONTENT_TYPE_CD, c.CONTENT_STATUS_CD,
c.SOURCE_CD, c.LOCALE_CD, c.LANGUAGE_CD,
NULL AS ITEM_ID,
NULL AS BUS_ENTITY_ID,
SubStr(c.PATH,1, InStr(c.PATH, '/',-1))||To_Char(a.child_item_id)||
     SubStr(c.PATH, InStr(c.PATH,'.',-1)) AS PATH,
c.CONTENT_USAGE_CD,
NULL AS EFF_DATE,
NULL AS EXP_DATE,
SYSDATE ADD_DATE,
'ItemSynchronizer' ADD_BY,
SYSDATE MOD_DATE,
'ItemSynchronizer' MOD_BY,
c.BINARY_DATA
FROM clw_content c, tmp_meta_content a
WHERE path = '.'||a.clw_value
;

INSERT INTO clw_item_assoc (
  ITEM_ASSOC_ID, ITEM1_ID, ITEM2_ID, CATALOG_ID, ITEM_ASSOC_CD, ADD_DATE,
  ADD_BY, MOD_DATE, MOD_BY
)
SELECT
  clw_item_assoc_seq.NEXTVAL AS ITEM_ASSOC_ID,
  i.child_item_id AS ITEM1_ID,
  jc.child_categ_id AS ITEM2_ID,
  i.child_catalog_id AS CATALOG_ID,
  ia.ITEM_ASSOC_CD,
  SYSDATE ADD_DATE,
  'ItemSynchronizer' ADD_BY,
  SYSDATE MOD_DATE,
  'ItemSynchronizer' MOD_BY
FROM tmp_item_to_add i, clw_item_assoc ia, tmp_join_categ jc
WHERE i.item_id = ia.item1_id
AND i.catalog_id = ia.catalog_id
AND ia.item_assoc_cd = 'PRODUCT_PARENT_CATEGORY'
AND ia.item2_id = jc.categ_id
AND i.child_catalog_id = jc.child_catalog_id
;
--*****************************
-- Update Items
--*****************************
--SELECT * FROM clw_item_master_assoc
-- insert new links
INSERT INTO clw_item_master_assoc (
  ITEM_MASTER_ASSOC_ID, PARENT_MASTER_ITEM_ID, CHILD_MASTER_ITEM_ID,
  ITEM_MASTER_ASSOC_STATUS_CD,
  ADD_DATE, ADD_BY, MOD_DATE, MOD_BY
)
SELECT
  clw_item_master_assoc_seq.NEXTVAL AS ITEM_MASTER_ASSOC_ID,
  item_id AS PARENT_MASTER_ITEM_ID,
  child_item_id AS CHILD_MASTER_ITEM_ID,
  'ACTIVE' AS ITEM_MASTER_ASSOC_STATUS_CD,
  SYSDATE AS ADD_DATE,
  'ItemSynchronizer' AS ADD_BY,
  SYSDATE AS MOD_DATE,
  'ItemSynchronizer' AS MOD_BY
FROM (
SELECT j.*, ia.item_master_assoc_id
FROM tmp_join_item j, clw_item_master_assoc ia
WHERE j.item_id = ia.parent_master_item_id(+)
  AND j.child_item_id = ia.child_master_item_id(+)
) WHERE item_master_assoc_id IS null
;

-- remove inactive links (WRITE LATER)



-- update short and lond descr
CREATE TABLE tmp_item_to_upd AS
SELECT chi.item_id, i.short_desc, i.long_desc, i.item_status_cd
FROM clw_item i, clw_item chi, tmp_join_item j
WHERE i.item_id = j.item_id
  AND chi.item_id = j.child_item_id
  AND (Nvl(i.short_desc,' ')!= Nvl(chi.short_desc,' ') OR
       Nvl(i.long_desc,' ')!= Nvl(chi.long_desc,' ') OR
	   Nvl(i.item_status_cd,' ')!= Nvl(chi.item_status_cd,' '))
;

-- update CLW_ITEM
UPDATE clw_item i SET (short_desc, long_desc, item_status_cd, mod_date, mod_by) =
(SELECT t.short_desc, t.long_desc, item_status_cd, SYSDATE, 'ItemSynchronizer'
FROM tmp_item_to_upd t
WHERE t.item_id = i.item_id
) WHERE i.item_id IN (
  SELECT item_id FROM tmp_item_to_upd
)
;

-- update CLW_CATALOG_STRUCTURE
CREATE TABLE tmp_cs_to_upd AS
SELECT chcs.catalog_structure_id, chcs.customer_sku_num, cs.short_desc
 FROM tmp_join_item j, clw_catalog_structure cs, clw_catalog_structure chcs
WHERE j.item_id = cs.item_id
  AND j.catalog_id = cs.catalog_id
  AND j.child_item_id = chcs.item_id
  AND j.child_catalog_id = chcs.catalog_id
  AND (
    Nvl(cs.CUSTOMER_SKU_NUM,' ') !=   Nvl(chcs.CUSTOMER_SKU_NUM,' ') OR
    Nvl(cs.SHORT_DESC,' ') !=   Nvl(chcs.SHORT_DESC,' ')
  )
;

UPDATE clw_catalog_structure cs SET (customer_sku_num, short_desc, mod_date, mod_by) =
(SELECT t.customer_sku_num, t.short_desc, SYSDATE, 'ItemSynchronizer'
   FROM tmp_cs_to_upd t WHERE t.catalog_structure_id = cs.catalog_structure_id)
WHERE cs.catalog_structure_id IN (
   SELECT catalog_structure_id FROM tmp_cs_to_upd
)
;

--update manufacturer info
CREATE TABLE tmp_item_manuf_up AS 
   SELECT im.item_mapping_id, j.manuf_sku, chm.child_manuf_id
     FROM tmp_join_item j, tmp_child_manuf chm, clw_item_mapping im
    WHERE im.item_id = j.child_item_id
      AND im.item_mapping_cd = 'ITEM_MANUFACTURER'
      AND j.parent_manuf_id = chm.parent_manuf_id
      AND j.child_store_id = chm.child_store_id
      AND (TRIM(j.manuf_sku) != TRIM(j.child_manuf_sku) OR 
           im.bus_entity_id != chm.child_manuf_id)      
;   
UPDATE clw_item_mapping im 
 SET (bus_entity_id, item_num, mod_date, mod_by) =
(
 SELECT imu.child_manuf_id, imu.manuf_sku,  SYSDATE, 'ItemSynchronizer'
   FROM tmp_item_manuf_up imu
  WHERE imu.item_mapping_id = im.item_mapping_id
) 
  WHERE item_mapping_id IN (SELECT item_mapping_id FROM tmp_item_manuf_up)
;

-- distributors
-- insert missing distributor links
INSERT INTO clw_item_mapping (
     ITEM_MAPPING_ID, ITEM_ID, BUS_ENTITY_ID, ITEM_NUM, ITEM_UOM,
     ITEM_PACK, SHORT_DESC, LONG_DESC,
     ITEM_MAPPING_CD, EFF_DATE, EXP_DATE, STATUS_CD,
     ADD_DATE, ADD_BY, MOD_DATE, MOD_BY,
     UOM_CONV_MULTIPLIER, STANDARD_PRODUCT_LIST
)
SELECT
     clw_item_mapping_seq.NEXTVAL AS ITEM_MAPPING_ID,
     child_item_id AS ITEM_ID,
     child_dist_id AS BUS_ENTITY_ID,
     ITEM_NUM,
     ITEM_UOM,
     ITEM_PACK,
     SHORT_DESC,
     LONG_DESC,
     ITEM_MAPPING_CD,
     NULL EFF_DATE,
     NULL EXP_DATE,
     STATUS_CD,
     SYSDATE AS ADD_DATE,
     'ItemSynchronizer' AS ADD_BY,
     SYSDATE AS MOD_DATE,
     'ItemSynchronizer' AS MOD_BY,
     UOM_CONV_MULTIPLIER,
     STANDARD_PRODUCT_LIST
 FROM (
SELECT aa.*, chdm.item_mapping_id ch_id
FROM
(
SELECT j.child_item_id, jd.child_dist_id, dm.*
  FROM tmp_join_item j, clw_item_mapping dm, tmp_join_dist jd
 WHERE j.item_id = dm.item_id
   AND dm.item_mapping_cd = 'ITEM_DISTRIBUTOR'
   AND dm.bus_entity_id = jd.parent_dist_id
   AND j.child_store_id = jd.child_store_id
) aa, clw_item_mapping chdm
WHERE  aa.child_dist_id = chdm.bus_entity_id(+)
  AND aa.child_item_id = chdm.item_id(+)
  AND chdm.item_mapping_cd = 'ITEM_DISTRIBUTOR'
) WHERE ch_id IS NULL
;

--update distributor links
CREATE TABLE tmp_item_dist_upd AS
SELECT chdm.item_mapping_id, dm.item_num, dm.item_uom, dm.item_pack,
       dm.short_desc, dm.long_desc, dm.status_cd, dm.uom_conv_multiplier
  FROM tmp_join_item j, clw_item_mapping dm, tmp_join_dist jd,
       clw_item_mapping chdm
 WHERE j.item_id = dm.item_id
   AND dm.item_mapping_cd = 'ITEM_DISTRIBUTOR'
   AND dm.bus_entity_id = jd.parent_dist_id
   AND j.child_store_id = jd.child_store_id
   AND jd.child_dist_id = chdm.bus_entity_id
   AND j.child_item_id = chdm.item_id
   AND chdm.item_mapping_cd = 'ITEM_DISTRIBUTOR'
   AND (
     Nvl(dm.ITEM_NUM,' ') != Nvl(chdm.ITEM_NUM,' ') OR
     Nvl(dm.ITEM_UOM,' ') != Nvl(chdm.ITEM_UOM,' ') OR
     Nvl(dm.ITEM_PACK,' ') != Nvl(chdm.ITEM_PACK,' ')OR
     Nvl(dm.SHORT_DESC,' ') != Nvl(chdm.SHORT_DESC,' ') OR
     Nvl(dm.LONG_DESC,' ') != Nvl(chdm.LONG_DESC,' ') OR
     Nvl(dm.STATUS_CD,' ') != Nvl(chdm.STATUS_CD,' ') OR
     Nvl(dm.UOM_CONV_MULTIPLIER, -1) != Nvl(chdm.UOM_CONV_MULTIPLIER, -1) OR
     Nvl(dm.STANDARD_PRODUCT_LIST,' ') != Nvl(chdm.STANDARD_PRODUCT_LIST,' ')
   )
;

UPDATE clw_item_mapping im SET
  (item_num, item_uom, item_pack, short_desc, long_desc, status_cd,
   uom_conv_multiplier, mod_date, mod_by)=
(
SELECT
   item_num, item_uom, item_pack, short_desc, long_desc, status_cd,
   uom_conv_multiplier,  SYSDATE, 'ItemSynchronizer'
   FROM tmp_item_dist_upd tdu
   WHERE tdu.item_mapping_id = im.item_mapping_id
) WHERE im.item_mapping_id IN (
   SELECT item_mapping_id FROM tmp_item_dist_upd
)
;



-- update category
-- drop table tmp_item_assoc purge
CREATE TABLE tmp_item_assoc AS
SELECT chia.item_assoc_id, chia.item1_id, chia.catalog_id, aa.child_categ_id,
       chia.item2_id
FROM
(
SELECT
j.child_item_id, jc.child_categ_id, j.child_catalog_id
FROM tmp_join_item j, clw_item_assoc ia, tmp_join_categ jc
WHERE j.item_id = ia.item1_id
AND j.catalog_id = ia.catalog_id
AND ia.item_assoc_cd = 'PRODUCT_PARENT_CATEGORY'
AND ia.item2_id = jc.categ_id
AND j.child_catalog_id = jc.child_catalog_id
) aa,
clw_item_assoc chia
WHERE chia.item1_id = aa.child_item_id
AND chia.catalog_id = aa.child_catalog_id
AND chia.item_assoc_cd = 'PRODUCT_PARENT_CATEGORY'
AND chia.item2_id != aa.child_categ_id
;

--SELECT * FROM tmp_item_assoc

UPDATE clw_item_assoc ia SET (item2_id, mod_date, mod_by) =
(SELECT child_categ_id, SYSDATE, 'ItemSynchronizer'
 FROM tmp_item_assoc t
 WHERE t.item_assoc_id = ia.item_assoc_id)
WHERE ia.item_assoc_id IN (
SELECT item_assoc_id FROM tmp_item_assoc
)
;

-- update meta info
CREATE TABLE tmp_item_meta_upd AS
SELECT chim.item_meta_id, chim.item_id, chim.name_value,
      aa.clw_value new_value, chim.clw_value old_value
from
(
SELECT j.child_item_id, m.name_value, m.clw_value
FROM tmp_join_item j, clw_item_meta m
WHERE j.item_id  = m.item_id
AND m.name_value NOT IN ('IMAGE','DED','MSDS','SPEC','THUMBNAIL')
) aa,
clw_item_meta chim
WHERE chim.item_id = aa.child_item_id
AND chim.name_value = aa.name_value
AND chim.clw_value != aa.clw_value
;

UPDATE clw_item_meta im SET (clw_value, mod_date, mod_by) =
(SELECT t.new_value, SYSDATE, 'ItemSynchronizer'
   FROM tmp_item_meta_upd t
  WHERE t.item_meta_id = im.item_meta_id
)
WHERE im.item_meta_id IN (
  SELECT item_meta_id FROM tmp_item_meta_upd
)
;

--/////////////////////////////////////////////////
DELETE clw_content WHERE path IN (
SELECT '.'||clw_value
FROM (
SELECT chim.*, aa.child_item_id
FROM
(
  SELECT  j.child_item_id, m.name_value, m.clw_value
  FROM tmp_join_item j, clw_item_meta m
  WHERE j.item_id  = m.item_id
--  AND m.name_value NOT IN ('IMAGE','DED','MSDS','SPEC','THUMBNAIL')
) aa,
clw_item_meta chim
WHERE chim.item_id = aa.child_item_id (+)
AND chim.name_value = aa.name_value (+)
)
WHERE child_item_id IS NULL
AND item_id IN (SELECT child_item_id FROM tmp_join_item)
)
;

DELETE clw_item_meta WHERE item_meta_id IN (
SELECT item_meta_id
FROM (
SELECT chim.*, aa.child_item_id
FROM
(
  SELECT  j.child_item_id, m.name_value, m.clw_value
  FROM tmp_join_item j, clw_item_meta m
  WHERE j.item_id  = m.item_id
) aa,
clw_item_meta chim
WHERE chim.item_id = aa.child_item_id (+)
AND chim.name_value = aa.name_value (+)
)
WHERE child_item_id IS NULL
AND item_id IN (SELECT child_item_id FROM tmp_join_item)
)
;


INSERT INTO clw_item_meta im (
  ITEM_META_ID, ITEM_ID, VALUE_ID, NAME_VALUE, CLW_VALUE,
  ADD_DATE, ADD_BY, MOD_DATE, MOD_BY
)
SELECT
  clw_item_meta_seq.NEXTVAL AS ITEM_META_ID,
  child_item_id AS ITEM_ID,
  0 VALUE_ID,
  NAME_VALUE,
  CLW_VALUE,
  SYSDATE AS ADD_DATE,
  'ItemSynchronizer' AS ADD_BY,
  SYSDATE AS MOD_DATE,
  'ItemSynchronizer' AS MOD_BY
FROM
(
SELECT aa.*, chim.item_meta_id
FROM
(
  SELECT j.child_item_id, m.name_value, m.clw_value
  FROM tmp_join_item j, clw_item_meta m
  WHERE j.item_id  = m.item_id
  AND m.name_value NOT IN ('IMAGE','DED','MSDS','SPEC','THUMBNAIL')
) aa,
clw_item_meta chim
WHERE chim.item_id(+) = aa.child_item_id
AND chim.name_value(+) = aa.name_value
)
WHERE item_meta_id IS NULL
;


-- insert missing content meta
INSERT INTO  clw_item_meta (
   ITEM_META_ID, ITEM_ID, VALUE_ID, NAME_VALUE, CLW_VALUE,
   ADD_DATE, ADD_BY, MOD_DATE, MOD_BY
)
SELECT
   clw_item_meta_seq.NEXTVAL AS ITEM_META_ID,
   child_item_id AS ITEM_ID,
   0 VALUE_ID,
   NAME_VALUE,
   SubStr(clw_value,1, InStr(clw_value, '/',-1))||To_Char(child_item_id)||
     SubStr(clw_value, InStr(clw_value,'.',-1)) AS CLW_VALUE,
   SYSDATE AS ADD_DATE,
   'ItemSynchronizer' AS ADD_BY,
   SYSDATE AS MOD_DATE,
   'ItemSynchronizer' AS MOD_BY
FROM
(
SELECT
  aa.item_id, aa.child_item_id, aa.name_value, aa.clw_value, chm.item_meta_id
FROM
(
SELECT j.item_id, j.child_item_id, m.name_value, m.clw_value, c.content_id
FROM tmp_join_item j, clw_item_meta m, clw_content c
 WHERE j.item_id = m.item_id
   AND m.name_value IN ('IMAGE','DED','MSDS','SPEC','THUMBNAIL')
   AND c.path = '.'||m.clw_value
   AND c.binary_data IS NOT NULL
) aa, clw_item_meta chm
WHERE aa.child_item_id = chm.item_id(+)
  AND aa.name_value = chm.name_value(+)
) WHERE item_meta_id IS null
;


--update meta and context
CREATE TABLE tmp_cont_upd AS
SELECT c.content_id, chc.content_id child_content_id
  FROM tmp_join_item j, clw_item_meta m, clw_content c,
       clw_item_meta chm, clw_content chc
 WHERE j.item_id = m.item_id
   AND m.name_value IN ('IMAGE','DED','MSDS','SPEC','THUMBNAIL')
   AND c.path = '.'||m.clw_value
   AND j.child_item_id = chm.item_id
   AND chm.name_value = m.name_value
   AND chc.path = '.'||chm.clw_value
   AND (
      (c.binary_data IS NULL AND chc.binary_data IS NOT NULL) OR
      (c.binary_data IS NOT NULL AND chc.binary_data IS NULL) OR
      (Length(c.binary_data) != Length(chc.binary_data)) OR
      (c.mod_date > chc.mod_date)
      )
;
--SELECT * FROM tmp_cont_upd

UPDATE clw_content chc SET (binary_data, mod_date, mod_by) =
(
SELECT c.binary_data,  SYSDATE, 'ItemSynchronizer'
  FROM clw_content c, tmp_cont_upd t
  WHERE c.content_id = t.content_id AND chc.content_id = t.child_content_id
)
WHERE chc.content_id IN (
  SELECT child_content_id FROM tmp_cont_upd
)
;
-- insert missing content
INSERT INTO  clw_content (
   CONTENT_ID,
   SHORT_DESC, LONG_DESC, VERSION, CONTENT_TYPE_CD,
   CONTENT_STATUS_CD, SOURCE_CD, LOCALE_CD, LANGUAGE_CD, ITEM_ID, BUS_ENTITY_ID,
   PATH,
   CONTENT_USAGE_CD, EFF_DATE, EXP_DATE,
   ADD_DATE, ADD_BY, MOD_DATE, MOD_BY,
   BINARY_DATA
)
SELECT
   clw_content_seq.NEXTVAL AS CONTENT_ID,
   SHORT_DESC, LONG_DESC, VERSION, CONTENT_TYPE_CD,
   CONTENT_STATUS_CD, SOURCE_CD, LOCALE_CD, LANGUAGE_CD, ITEM_ID, BUS_ENTITY_ID,
   '.'||clw_value AS PATH,
   CONTENT_USAGE_CD, EFF_DATE, EXP_DATE,
   SYSDATE AS ADD_DATE,
   'ItemSynchronizer' AS ADD_BY,
   SYSDATE AS MOD_DATE,
   'ItemSynchronizer' AS MOD_BY,
   BINARY_DATA
FROM
(
SELECT c.*, chm.clw_value, chm.item_id child_item_id, chc.content_id chc_id
  FROM tmp_join_item j, clw_item_meta m, clw_content c,
       clw_item_meta chm, clw_content chc
 WHERE j.item_id = m.item_id
   AND m.name_value IN ('IMAGE','DED','MSDS','SPEC','THUMBNAIL')
   AND c.path = '.'||m.clw_value
   AND j.child_item_id = chm.item_id
   AND chm.name_value = m.name_value
   AND chc.path(+) = '.'||chm.clw_value
) WHERE chc_id IS NULL
;

-- Synchronize Sertified Companies
DELETE clw_item_mapping WHERE item_mapping_id IN (
SELECT ITEM_MAPPING_ID 
FROM (
SELECT aa.ITEM_MAPPING_ID, im2.bus_entity_id bb FROM    
   (
   SELECT im.*, ji.item_id AS parent_item_id
     FROM tmp_join_item ji, clw_item_mapping im
    WHERE ji.child_item_id = im.item_id 
      AND im.item_mapping_cd = 'ITEM_CERTIFIED_COMPANY'
   ) aa, clw_item_mapping im2  
   WHERE aa.parent_item_id = im2.item_id(+)
     AND aa.bus_entity_id = im2.bus_entity_id(+)
     AND im2.item_mapping_cd (+)= 'ITEM_CERTIFIED_COMPANY'
)
 WHERE bb IS NULL 
)
;

INSERT INTO CLW_ITEM_MAPPING (
  ITEM_MAPPING_ID, ITEM_ID, BUS_ENTITY_ID, ITEM_NUM, ITEM_UOM, ITEM_PACK, 
  SHORT_DESC, LONG_DESC, ITEM_MAPPING_CD, EFF_DATE, EXP_DATE, STATUS_CD, 
  ADD_DATE, ADD_BY, MOD_DATE, MOD_BY, 
  UOM_CONV_MULTIPLIER, STANDARD_PRODUCT_LIST
)
SELECT 
  CLW_ITEM_MAPPING_SEQ.NEXTVAL, 
  child_item_Id AS ITEM_ID, 
  BUS_ENTITY_ID, 
  ITEM_NUM, ITEM_UOM, ITEM_PACK, SHORT_DESC, LONG_DESC, ITEM_MAPPING_CD, 
  EFF_DATE, EXP_DATE, STATUS_CD, 
  SYSDATE AS ADD_DATE,
  'ItemSynchronizer' AS ADD_BY,
  SYSDATE AS MOD_DATE,
  'ItemSynchronizer' AS MOD_BY,
  UOM_CONV_MULTIPLIER, STANDARD_PRODUCT_LIST 
FROM (
SELECT aa.*, im2.bus_entity_id bb FROM    
   (SELECT im.*, ji.child_item_id
     FROM tmp_join_item ji, clw_item_mapping im
    WHERE ji.item_id = im.item_id 
      AND im.item_mapping_cd = 'ITEM_CERTIFIED_COMPANY'
   ) aa, clw_item_mapping im2  
   WHERE aa.child_item_id = im2.item_id(+)
     AND aa.bus_entity_id = im2.bus_entity_id(+)
     AND im2.item_mapping_cd (+)= 'ITEM_CERTIFIED_COMPANY'
)
 WHERE bb IS NULL 
;

-- Drop tables
DROP TABLE tmp_categ purge;
DROP TABLE tmp_child_item purge;
DROP TABLE tmp_child_manuf purge;
DROP TABLE tmp_cont_upd purge;
DROP TABLE tmp_cs_to_upd purge;
DROP TABLE tmp_item purge;
DROP TABLE tmp_item_assoc purge;
DROP TABLE tmp_item_dist_upd purge;
DROP TABLE tmp_item_meta_upd purge;
DROP TABLE tmp_item_mult purge;
DROP TABLE tmp_item_to_add purge;
DROP TABLE tmp_item_to_upd purge;
DROP TABLE tmp_join_categ purge;
DROP TABLE tmp_join_dist purge;
DROP TABLE tmp_join_item purge;
DROP TABLE tmp_join_store purge;
DROP TABLE tmp_manuf purge;
DROP TABLE tmp_manuf_to_add purge;
DROP TABLE tmp_meta_content purge;
DROP TABLE tmp_item_manuf_up purge;
