SELECT a.line_number,
       a.store_id,
       a.account_ref_num,
       a.asset_number
FROM  #LOADING_TABLE_NAME# a,
      (SELECT store_id,
              account_ref_num,
              asset_number
       FROM #LOADING_TABLE_NAME#
       GROUP BY store_id,
                account_ref_num,
                asset_number
       HAVING COUNT (*) > 1) b
WHERE a.store_id = b.store_id AND
      a.account_ref_num = b.account_ref_num AND
      a.asset_number = b.asset_number
ORDER BY a.line_number