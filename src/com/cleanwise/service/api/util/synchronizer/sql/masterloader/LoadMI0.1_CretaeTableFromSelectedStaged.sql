CREATE TABLE #LOADING_TABLE_NAME#
AS
   SELECT   *
     FROM   clw_staged_item
    WHERE   staged_item_id = #STAGED_ITEM_ID#;

   
