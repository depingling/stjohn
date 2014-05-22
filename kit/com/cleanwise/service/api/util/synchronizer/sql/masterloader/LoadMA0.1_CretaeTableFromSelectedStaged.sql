CREATE TABLE #LOADING_TABLE_NAME#
AS
   SELECT   *
     FROM   clw_staged_asset
    WHERE   staged_asset_id = #STAGED_ASSET_ID#;

   
