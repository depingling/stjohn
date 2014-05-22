SELECT a.line_number,
       a.version_number,
       a.store_id,
       a.account_ref_num,
       a.asset_number
FROM #LOADING_TABLE_NAME# a
WHERE version_number <> 1
