
INSERT INTO clw_pipeline(
PIPELINE_ID, SHORT_DESC, PIPELINE_STATUS_CD, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY,
BUS_ENTITY_ID, PIPELINE_TYPE_CD, CLASSNAME, PIPELINE_ORDER, OPTIONAL
)
SELECT
clw_PIPELINE_seq.nextval,
SHORT_DESC,
PIPELINE_STATUS_CD,
SYSDATE ADD_DATE,
'YKupershmidt' ADD_BY,
SYSDATE MOD_DATE,
'YKupershmidt' MOD_BY,
0 BUS_ENTITY_ID,
PIPELINE_TYPE_CD,
'com.cleanwise.service.api.pipeline.CheckForDuplication' CLASSNAME,
PIPELINE_ORDER+1, OPTIONAL
FROM clw_pipeline WHERE PIPELINE_TYPE_CD = 'SYNCH_ASYNCH'
AND classname = 'com.cleanwise.service.api.pipeline.OrderRequestItemParsing'
AND NOT EXISTS (
SELECT * FROM clw_pipeline WHERE classname = 'com.cleanwise.service.api.pipeline.CheckForDuplication'
)
;
COMMIT;