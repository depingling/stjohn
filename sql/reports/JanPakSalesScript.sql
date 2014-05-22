DROP TABLE kuper_ttt purge;

CREATE TABLE kuper_ttt
AS SELECT
DIST_ITEM_SKU_NUM, BUS_ENTITY_id AS dist_id, DIST_ITEM_SHORT_DESC, INVOICE_NUM,INVOICE_DATE,ADJUSTED_COST,ITEM_RECEIVED_COST,DIST_ITEM_QTY_RECEIVED, DIST_INTO_STOCK_COST
 FROM clw_invoice_dist id , clw_invoice_dist_detail idd
WHERE id.invoice_dist_id = idd.invoice_dist_id
and id.invoice_Date BETWEEN '2010.08.01' AND '2010.08.31' --!!!!!!!!!!!!!!!! SET DATE INTERVAL HERE
AND store_id = 196842
AND invoice_status_cd = 'Invoice_History'
ORDER BY ITEM_RECEIVED_COST desc
;

ALTER TABLE kuper_ttt ADD (item_id NUMBER (38), manuf_id NUMBER(38), jd_flag VARCHAR2(1))
;

CREATE INDEX kuper_ttt_i ON kuper_ttt (dist_id, dist_item_sku_num);

DROP TABLE kuper_ttt1 purge;

CREATE TABLE kuper_ttt1 AS
SELECT * FROM clw_item_mapping WHERE bus_entity_id IN (
SELECT dist_id FROM kuper_ttt
);

CREATE INDEX kuper_ttt1_i ON kuper_ttt1 (bus_entity_id, item_num)
;

UPDATE kuper_ttt k SET item_id = (SELECT item_id FROM kuper_ttt1 im WHERE im.item_num = dist_item_sku_num AND k.dist_id = im.bus_entity_id)
;

COMMIT;

UPDATE kuper_ttt k SET manuf_id = (SELECT bus_entity_id FROM clw_item_mapping im WHERE im.item_id = k.item_id AND im.item_mapping_cd = 'ITEM_MANUFACTURER')
;

UPDATE kuper_ttt k SET jd_flag = 'Y'
WHERE k.manuf_id IN (SELECT bus_entity_id FROM clw_group_assoc ga WHERE ga.group_id = 412)
;

COMMIT;

SELECT
 min(To_Char(INVOICE_DATE,'mm/dd/yyyy')) min_inv_date,
 max(To_Char(INVOICE_DATE,'mm/dd/yyyy')) max_inv_date,
To_char (Sum(adjusted_cost),'99,999,999') all_products,
To_char (Sum(CASE WHEN jd_flag = 'Y' THEN adjusted_cost ELSE 0 END ),'99,999,999')   jd_products
FROM kuper_ttt
;
