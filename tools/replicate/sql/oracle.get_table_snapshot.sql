create or replace procedure get_table_snapshot (table_name VARCHAR2,
                                                filter VARCHAR2,
                                                previous_date DATE,
                                                start_date DATE,
                                                repl_mask NUMBER,
                                                clw_schema in VARCHAR2,
                                                rpl_schema in VARCHAR2,
                                                session_id in NUMBER) is

temp_table VARCHAR2(100);
filter_str VARCHAR2(1000);
sql_str VARCHAR2(1000);
i PLS_INTEGER;
m_s PLS_INTEGER;
df VARCHAR2(100) := 'yyyy/mm/dd hh24:mi:ss';
BEGIN
-- set serveroutput on
-- #############################################################################
  if filter is not null then
    filter_str := ' and ' || filter;
  else
    filter_str := '';
  end if;
-- =============================================================================
  for m_s in 1..2 loop
    if m_s = 1 then
-- ------------------- for domestic records ------------------------------------
      temp_table := 'rpl_d_' || lower(substr(table_name,5));
    else
-- ------------------- for external records ------------------------------------
      temp_table := 'rpl_e_' || lower(substr(table_name,5));
    end if;
    dbms_output.put_line('table: ' || temp_table);

-- =============== create table ================================================
    select count(table_name) into i from user_tables where lower(table_name) = temp_table;
    if i <> 0 then
      sql_str := 'drop table ' || rpl_schema || '.' || temp_table;
      dbms_output.put_line('sql: ' || sql_str);
      EXECUTE IMMEDIATE sql_str;
    end if;

    sql_str := 'create table ' || rpl_schema || '.' || temp_table ||
               ' as  select * from ' || clw_schema || '.' || lower(table_name) || ' where 1 = 0';
    dbms_output.put_line('sql: ' || sql_str);
    EXECUTE IMMEDIATE sql_str;
-- =============== insert into rpl_d_ === (domestic) ===========================
    if m_s = 1 then
-- ---------------- replication is based on mod_date ---------------------------
      if previous_date is not null and start_date is not null and repl_mask = 0 then
        sql_str := 'insert into ' || rpl_schema || '.' || temp_table ||
                   ' select * from ' ||  clw_schema || '.' || lower(table_name) ||
                   ' where mod_date >= to_date(''' || to_char(previous_date,df) || ''',''' || df ||
                   ''') AND mod_date < to_date(''' || to_char(start_date,df) || ''',''' || df || ''')' || filter_str;

        dbms_output.put_line('sql: ' || sql_str);
        EXECUTE IMMEDIATE sql_str;
        sql_str := 'Master DB. Table ' || table_name || ': ' || sql%rowcount || ' row(s) are ready for sending to Slave DB';
        insert into rpl_repl_logging values(rpl_repl_logging_seq.nextval, session_id, sql_str,
                                    'Replication', current_date, 'Replication', current_date);
      else
-- ---------------- replication is based on repl_status -  ---------------------
        sql_str := 'select * from ' ||  clw_schema || '.' || lower(table_name) ||
                   ' where bitand(repl_status,' || repl_mask ||') != 0  and repl_status is not null' || filter_str ||
                   ' for update';
        dbms_output.put_line('sql: ' || sql_str);
        EXECUTE IMMEDIATE sql_str;
        sql_str := 'insert into ' || rpl_schema || '.' || temp_table ||
                   ' select * from ' ||  clw_schema || '.' || lower(table_name) ||
                   ' where bitand(repl_status,' || repl_mask ||') != 0  and repl_status is not null' || filter_str;
        dbms_output.put_line('sql: ' || sql_str);
        EXECUTE IMMEDIATE sql_str;
        sql_str := 'Master DB. Table ' || table_name || ': ' || sql%rowcount || ' row(s) are ready for sending to Slave DB';
        insert into rpl_repl_logging values(rpl_repl_logging_seq.nextval, session_id, sql_str,
                                    'Replication', current_date, 'Replication', current_date);
        sql_str := 'update ' ||  clw_schema || '.' || lower(table_name) || ' a' ||
                   ' set repl_status = bitxor(a.repl_status,' || repl_mask || ')' ||
                   ' where bitand(repl_status,' || repl_mask ||') != 0  and repl_status is not null' || filter_str;
        dbms_output.put_line('sql: ' || sql_str);
        EXECUTE IMMEDIATE sql_str;
        sql_str := 'update ' ||  rpl_schema || '.' || temp_table ||
                   ' set repl_status = bitxor(31,' || repl_mask || ')';
        dbms_output.put_line('sql: ' || sql_str);
        EXECUTE IMMEDIATE sql_str;
      end if;
    end if;

  end loop;
-- #############################################################################
END;
/
