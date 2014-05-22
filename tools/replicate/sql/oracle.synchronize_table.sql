create or replace procedure synchronize_table (table_name in VARCHAR2,
                                               repl_mask in NUMBER,
                                               clw_schema in VARCHAR2,
                                               rpl_schema in VARCHAR2,
                                               session_id in NUMBER) is

temp_table_e VARCHAR2(100);
temp_table_d VARCHAR2(100);
sql_str VARCHAR2(4000);
temp_key VARCHAR2(100);
field_list_1 VARCHAR2(4000);
field_list_2 VARCHAR2(4000);
flag boolean;
df VARCHAR2(100) := 'yyyy/mm/dd hh24:mi:ss';
BEGIN
-- ========== fill column list =================================================
        temp_table_e := 'rpl_e_' || lower(substr(table_name,5));
        temp_table_d := 'rpl_d_' || lower(substr(table_name,5));
        temp_key := lower(substr(table_name,5)) || '_id';
        dbms_output.put_line(temp_key);
        field_list_1 := '';
        field_list_2 := '';
        flag := false;
        for x in (select lower(column_name) column_name from user_tab_columns
                  where lower(table_name) = temp_table_d order by column_id)
        loop
          if (x.column_name != temp_key) then
            if flag then
              field_list_1 := field_list_1 || ',';
              field_list_2 := field_list_2 || ',';
            end if;
            field_list_1 := field_list_1 || x.column_name;
            field_list_2 := field_list_2 || 'b.' || x.column_name;
            flag := true;
          end if;
        end loop;
        dbms_output.put_line(field_list_1);
        dbms_output.put_line(field_list_2);
-- =============================================================================
        sql_str := 'update ' ||  clw_schema || '.' || lower(table_name) || ' a' ||
                  ' set (' || field_list_1 || ') = (select ' || field_list_2 ||
                  ' from ' || rpl_schema || '.' || temp_table_e || ' b where a.' || temp_key || ' = b.' || temp_key || ')' ||
                  ' where (((bitand(a.repl_status,' || repl_mask || ')) = 0 or a.repl_status is null) and' ||
                  ' exists (select c.' || temp_key || ' from ' || rpl_schema || '.' || temp_table_e || ' c where a.' || temp_key || ' = c.' || temp_key || ') and' ||
                  ' (not exists (select d.' || temp_key || ' from ' || rpl_schema || '.' || temp_table_d || ' d where a.' || temp_key || ' = d.' || temp_key || ') or' ||
                  ' exists (select e.' || temp_key || ' from ' || rpl_schema || '.' || temp_table_e || ' e where a.' || temp_key || ' = e.' || temp_key ||
                  ' and a.mod_date < e.mod_date)))';
--        dbms_output.put_line('sql: ' || sql_str);
        EXECUTE IMMEDIATE sql_str;
        sql_str := 'Master DB. Table ' || table_name || ': ' || sql%rowcount || ' row(s) updated';
        insert into rpl_repl_logging values(rpl_repl_logging_seq.nextval, session_id, sql_str,
                                    'Replication', current_date, 'Replication', current_date);

        sql_str := 'insert into ' ||  clw_schema || '.' || lower(table_name) || ' select * from ' || rpl_schema || '.' || temp_table_e || ' a ' ||
                  'where a.' || temp_key || ' not in ' || '(select ' || temp_key ||
                  ' from ' || clw_schema || '.' || lower(table_name) || ' b where a.' || temp_key || ' = b.' || temp_key || ')';
--        dbms_output.put_line('sql: ' || sql_str);
        EXECUTE IMMEDIATE sql_str;
        sql_str := 'Master DB. Table ' || table_name || ': ' || sql%rowcount || ' row(s) inserted';
        insert into rpl_repl_logging values(rpl_repl_logging_seq.nextval, session_id, sql_str,
                                    'Replication', current_date, 'Replication', current_date);
END;
/
