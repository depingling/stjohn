SELECT DISTINCT * FROM 

    (SELECT n.line_number,
            n.store_id,
            n.account_ref_num,
            n.site_ref_num,
            n.asset_number,
            COUNT (a.store_id) AS occurance
            
            FROM #LOADING_TABLE_NAME# n,
                (SELECT store_account.bus_entity2_id store_id,
                        account_props.clw_value acct_ref_num,
                        site_props.clw_value site_ref_num
                        
                        FROM  clw_bus_entity_assoc store_account,
                              clw_bus_entity_assoc account_site,
                              clw_property account_props,
                              clw_property site_props
                        WHERE     store_account.bus_entity_assoc_cd = 'ACCOUNT OF STORE'
                              AND account_props.bus_entity_id = store_account.bus_entity1_id
                              AND account_props.short_desc = 'DIST_ACCT_REF_NUM'
                              AND account_site.bus_entity2_id = store_account.bus_entity1_id
                              AND account_site.bus_entity_assoc_cd = 'SITE OF ACCOUNT'
                              AND site_props.bus_entity_id = account_site.bus_entity1_id
                              AND site_props.short_desc = 'SITE_REFERENCE_NUMBER') a
            WHERE     n.account_ref_num = a.acct_ref_num (+)
                  AND n.site_ref_num = a.site_ref_num (+)
                  AND n.store_id = a.store_id (+)
            GROUP BY n.line_number,
                     n.store_id,
                     n.account_ref_num,
                     n.site_ref_num,
                     n.asset_number) b
WHERE occurance = 0
