DECLARE

CURSOR get_details IS SELECT * 
                     FROM CLW_INVENTORY_LEVEL_DETAIL ;
details_rec get_details%ROWTYPE;

CURSOR get_level IS SELECT * 
                    FROM CLW_INVENTORY_LEVEL il
                    WHERE
                        il.INVENTORY_LEVEL_ID IN
                            (SELECT INVENTORY_LEVEL_ID FROM CLW_INVENTORY_LEVEL_DETAIL);
level_rec get_level%ROWTYPE;

CURSOR get_levels_to_create IS SELECT * 
                                FROM CLW_INVENTORY_LEVEL il
                                WHERE il.INVENTORY_LEVEL_ID not in
                                    (SELECT INVENTORY_LEVEL_ID FROM CLW_INVENTORY_LEVEL_DETAIL);
level_to_create_rec get_levels_to_create%ROWTYPE;

update_block VARCHAR2(200);
v_add_date DATE;
v_mod_date DATE;
BEGIN
--cast clw_inventory_level to clw_inventory_details
FOR details_rec IN get_details LOOP
    FOR level_rec IN get_level LOOP
        IF(details_rec.INVENTORY_LEVEL_ID = level_rec.INVENTORY_LEVEL_ID) THEN
            IF(details_rec.MOD_DATE != level_rec.MOD_DATE) THEN   
                      IF (details_rec.PERIOD <= 13) THEN
                          DBMS_OUTPUT.put_line('Updating inventory_level# ' || level_rec.INVENTORY_LEVEL_ID); 
                          BEGIN
                            update_block := 'UPDATE CLW_INVENTORY_LEVEL ' ||
                            ' SET PAR_VALUE' || details_rec.PERIOD || ' = ' || details_rec.CLW_VALUE
                            || ', MOD_DATE=' || 'TO_DATE(''' || TO_CHAR(details_rec.MOD_DATE, 'MM-DD-YYYY HH12:MI:SS P.M.') || ''',' 
                                             || '''MM-DD-YYYY HH12:MI:SS P.M.''  )'
                            || ' WHERE INVENTORY_LEVEL_ID = ' || details_rec.INVENTORY_LEVEL_ID ;
                            EXECUTE IMMEDIATE update_block;
                            DBMS_OUTPUT.put_line('Success');
                          END;
                      END IF ;
            END IF; -- end of mod_date comparing
         END IF; -- end of level details comparing
    END LOOP;
END LOOP;

--create details
FOR level_to_create_rec IN get_levels_to_create LOOP
    IF(level_to_create_rec.ADD_DATE IS NULL) THEN
        v_add_date := level_to_create_rec.MOD_DATE;
	ELSE
        v_add_date := level_to_create_rec.ADD_DATE;
	END IF;
	
	IF(v_add_date is null) then
    select sysdate into v_add_date from dual;
	END IF;
	
	IF(level_to_create_rec.MOD_DATE is null) then
    select trunc(sysdate) into v_mod_date from dual;
	  
	INSERT INTO CLW_INVENTORY_LEVEL_DETAIL(INVENTORY_LEVEL_DETAIL_ID,
                                           INVENTORY_LEVEL_ID, 
                                           PERIOD, 
                                           CLW_VALUE, 
                                           ADD_DATE, 
                                           ADD_BY, 
                                           MOD_DATE, 
                                           MOD_BY)
            VALUES(CLW_INVENTORY_LEVEL_DETAIL_SEQ.nextVal,
                   level_to_create_rec.INVENTORY_LEVEL_ID, 
                   1, 
                   level_to_create_rec.PAR_VALUE1,
                   v_add_date,
                   level_to_create_rec.ADD_BY,
                   v_mod_date,
                   level_to_create_rec.MOD_BY);
				   
    INSERT INTO CLW_INVENTORY_LEVEL_DETAIL(INVENTORY_LEVEL_DETAIL_ID,INVENTORY_LEVEL_ID, 
                                           PERIOD, CLW_VALUE, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
            VALUES(CLW_INVENTORY_LEVEL_DETAIL_SEQ.nextVal,level_to_create_rec.INVENTORY_LEVEL_ID, 2 , level_to_create_rec.PAR_VALUE2,
                   v_add_date,level_to_create_rec.ADD_BY,
                   v_mod_date,level_to_create_rec.MOD_BY);
				   
    INSERT INTO CLW_INVENTORY_LEVEL_DETAIL(INVENTORY_LEVEL_DETAIL_ID,INVENTORY_LEVEL_ID, 
                                           PERIOD, CLW_VALUE, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
            VALUES(CLW_INVENTORY_LEVEL_DETAIL_SEQ.nextVal,level_to_create_rec.INVENTORY_LEVEL_ID, 3 , level_to_create_rec.PAR_VALUE3,
                   v_add_date,level_to_create_rec.ADD_BY,
                   v_mod_date,level_to_create_rec.MOD_BY);
				   
    INSERT INTO CLW_INVENTORY_LEVEL_DETAIL(INVENTORY_LEVEL_DETAIL_ID,INVENTORY_LEVEL_ID, 
                                           PERIOD, CLW_VALUE, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
            VALUES(CLW_INVENTORY_LEVEL_DETAIL_SEQ.nextVal,level_to_create_rec.INVENTORY_LEVEL_ID, 4 , level_to_create_rec.PAR_VALUE4,
                   v_add_date,level_to_create_rec.ADD_BY,
                   v_mod_date,level_to_create_rec.MOD_BY);
				   
    INSERT INTO CLW_INVENTORY_LEVEL_DETAIL(INVENTORY_LEVEL_DETAIL_ID,INVENTORY_LEVEL_ID, 
                                           PERIOD, CLW_VALUE, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
            VALUES(CLW_INVENTORY_LEVEL_DETAIL_SEQ.nextVal,level_to_create_rec.INVENTORY_LEVEL_ID, 5 , level_to_create_rec.PAR_VALUE5,
                   v_add_date,level_to_create_rec.ADD_BY,
                   v_mod_date,level_to_create_rec.MOD_BY);
				   
    INSERT INTO CLW_INVENTORY_LEVEL_DETAIL(INVENTORY_LEVEL_DETAIL_ID,INVENTORY_LEVEL_ID, 
                                           PERIOD, CLW_VALUE, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
            VALUES(CLW_INVENTORY_LEVEL_DETAIL_SEQ.nextVal,level_to_create_rec.INVENTORY_LEVEL_ID, 6 , level_to_create_rec.PAR_VALUE6,
                   v_add_date,level_to_create_rec.ADD_BY,
                   v_mod_date,level_to_create_rec.MOD_BY);
				   
    INSERT INTO CLW_INVENTORY_LEVEL_DETAIL(INVENTORY_LEVEL_DETAIL_ID,INVENTORY_LEVEL_ID, 
                                           PERIOD, CLW_VALUE, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
            VALUES(CLW_INVENTORY_LEVEL_DETAIL_SEQ.nextVal,level_to_create_rec.INVENTORY_LEVEL_ID, 7 , level_to_create_rec.PAR_VALUE7,
                   v_add_date,level_to_create_rec.ADD_BY,
                   v_mod_date,level_to_create_rec.MOD_BY);
				   
    INSERT INTO CLW_INVENTORY_LEVEL_DETAIL(INVENTORY_LEVEL_DETAIL_ID,INVENTORY_LEVEL_ID, 
                                           PERIOD, CLW_VALUE, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
            VALUES(CLW_INVENTORY_LEVEL_DETAIL_SEQ.nextVal,level_to_create_rec.INVENTORY_LEVEL_ID, 8 , level_to_create_rec.PAR_VALUE8,
                   v_add_date,level_to_create_rec.ADD_BY,
                   v_mod_date,level_to_create_rec.MOD_BY);
				   
    INSERT INTO CLW_INVENTORY_LEVEL_DETAIL(INVENTORY_LEVEL_DETAIL_ID,INVENTORY_LEVEL_ID, 
                                           PERIOD, CLW_VALUE, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
            VALUES(CLW_INVENTORY_LEVEL_DETAIL_SEQ.nextVal,level_to_create_rec.INVENTORY_LEVEL_ID, 9 , level_to_create_rec.PAR_VALUE9,
                   v_add_date,level_to_create_rec.ADD_BY,
                   v_mod_date,level_to_create_rec.MOD_BY);
				   
    INSERT INTO CLW_INVENTORY_LEVEL_DETAIL(INVENTORY_LEVEL_DETAIL_ID,INVENTORY_LEVEL_ID, 
                                           PERIOD, CLW_VALUE, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
            VALUES(CLW_INVENTORY_LEVEL_DETAIL_SEQ.nextVal,level_to_create_rec.INVENTORY_LEVEL_ID, 10 , level_to_create_rec.PAR_VALUE10,
                   v_add_date,level_to_create_rec.ADD_BY,
                   v_mod_date,level_to_create_rec.MOD_BY);
				   
    INSERT INTO CLW_INVENTORY_LEVEL_DETAIL(INVENTORY_LEVEL_DETAIL_ID,INVENTORY_LEVEL_ID, 
                                           PERIOD, CLW_VALUE, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
            VALUES(CLW_INVENTORY_LEVEL_DETAIL_SEQ.nextVal,level_to_create_rec.INVENTORY_LEVEL_ID, 11 , level_to_create_rec.PAR_VALUE11,
                   v_add_date,level_to_create_rec.ADD_BY,
                   v_mod_date,level_to_create_rec.MOD_BY);
				   
    INSERT INTO CLW_INVENTORY_LEVEL_DETAIL(INVENTORY_LEVEL_DETAIL_ID,INVENTORY_LEVEL_ID, 
                                           PERIOD, CLW_VALUE, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
            VALUES(CLW_INVENTORY_LEVEL_DETAIL_SEQ.nextVal,level_to_create_rec.INVENTORY_LEVEL_ID, 12 , level_to_create_rec.PAR_VALUE12,
                   v_add_date,level_to_create_rec.ADD_BY,
                   v_mod_date,level_to_create_rec.MOD_BY);
				   
    INSERT INTO CLW_INVENTORY_LEVEL_DETAIL(INVENTORY_LEVEL_DETAIL_ID,INVENTORY_LEVEL_ID, 
                                           PERIOD, CLW_VALUE, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
            VALUES(CLW_INVENTORY_LEVEL_DETAIL_SEQ.nextVal,level_to_create_rec.INVENTORY_LEVEL_ID, 13 , level_to_create_rec.PAR_VALUE13,
                   v_add_date,level_to_create_rec.ADD_BY,
                   v_mod_date,level_to_create_rec.MOD_BY);
				   
     DBMS_OUTPUT.put_line('Insert details for inventory_level_id: #' || level_to_create_rec.INVENTORY_LEVEL_ID);
	 
	 END IF;

END LOOP;

COMMIT;
END;
/