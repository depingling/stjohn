MERGE INTO   clw_item_meta m
     USING   tmp_mtchitems s
        ON   (m.item_id = s.matched_item_id AND m.name_value = '#target#')
WHEN MATCHED
THEN
   UPDATE SET
      m.clw_value = s.#source#,
      m.mod_date = CURRENT_DATE,
      m.mod_by = '#USER_NAME#'
      WHERE  s.#source# IS NOT null
WHEN NOT MATCHED
THEN
   INSERT
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
       VALUES
                (
                    clw_item_meta_seq.NEXTVAL,
                    matched_item_id,
                    0,
                    '#target#',
                    #source#,
                    CURRENT_DATE,
                    '#USER_NAME#',
                    CURRENT_DATE,
                    '#USER_NAME#'
                )
      WHERE  #source# IS NOT null
	;