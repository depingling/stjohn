SET SERVEROUTPUT ON; 
DECLARE
    growth INTEGER := 1000;
    seq_count INTEGER;
    sql_execute VARCHAR2(256);
    CURSOR cursor1 IS
        SELECT p1.bus_entity_id        AS store_id,
               to_number(p2.clw_value) AS order_num
        FROM clw_property p1
        INNER JOIN clw_property p2 ON p1.bus_entity_id = p2.bus_entity_id
        WHERE p1.property_type_cd = 'ORDER_NUMBERING_STORE_ID' AND
              p1.bus_entity_id = to_number(p1.clw_value)       AND
              p2.property_type_cd = 'STORE_ORDER_NUM'
        UNION
        SELECT p3.bus_entity_id        AS store_id,
               to_number(p3.clw_value) AS order_num
        FROM clw_property p3
        WHERE p3.bus_entity_id = 1 AND
              p3.property_type_cd = 'STORE_ORDER_NUM';
    row1 cursor1%ROWTYPE;
BEGIN
DBMS_OUTPUT.ENABLE;
OPEN cursor1;
LOOP 
    FETCH cursor1 INTO row1;
    EXIT WHEN cursor1%NOTFOUND;
    sql_execute := 'SELECT Count(*) FROM user_sequences WHERE sequence_name = ''CLW_ORDER_SEQUENCE_' || row1.store_id || '''';
    EXECUTE IMMEDIATE sql_execute INTO seq_count;
    DBMS_OUTPUT.PUT('Sequence for store ' || row1.store_id || ' ');
    IF seq_count = 0 THEN
        sql_execute := 'CREATE SEQUENCE CLW_ORDER_SEQUENCE_' || row1.store_id ||
                    ' START WITH ' || (row1.order_num + growth) || ' INCREMENT BY 1 NOCACHE';
        EXECUTE IMMEDIATE sql_execute;
        DBMS_OUTPUT.PUT_LINE('was created.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('already exists.');
    END IF;
END LOOP;
CLOSE cursor1;
END;
/
QUIT;