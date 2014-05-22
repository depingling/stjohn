INSERT INTO clw_item_meta
           (
               item_meta_id,
               item_id,
               value_id,
               name_value,
               clw_value,
               add_date,
               add_by,
               mod_date,
               mod_by
           )
   SELECT   clw_item_meta_seq.NEXTVAL,
            new_item_id,
            0,
            '#target#',
            #source#,
            CURRENT_DATE,
            '#USER_NAME#',
            CURRENT_DATE,
            '#USER_NAME#'
     FROM   tmp_newitems
    WHERE   #source# IS NOT NULL;