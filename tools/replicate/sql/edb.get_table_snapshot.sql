create or replace procedure get_table_snapshot (table_name VARCHAR2,
                                                filter VARCHAR2,
                                                previous_date TIMESTAMP,
                                                start_date TIMESTAMP,
                                                repl_mask INTEGER,
                                                clw_schema in VARCHAR2,
                                                rpl_schema in VARCHAR2,
                                                session_id in INTEGER) is

temp_table VARCHAR2(100);
filter_str VARCHAR2(1000);
sql_str VARCHAR2(1000);
i PLS_INTEGER;
m_s PLS_INTEGER;
df VARCHAR2(100) := 'yyyy/mm/dd hh24:mi:ss';
BEGIN
-- set serveroutput on
-- #############################################################################
  if filter is not null and filter != '' then
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
    select count(a.relname) into i from pg_class a, pg_namespace b where a.relname = temp_table and
                                                                         b.nspname = rpl_schema and
                                                                         a.relnamespace = b.oid;
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
      else
-- ---------------- replication is based on repl_status -  ---------------------
        sql_str := 'select * from ' ||  clw_schema || '.' || lower(table_name) ||
                   ' where (cast(repl_status as integer) & ' || repl_mask ||') != 0  and repl_status is not null' || filter_str ||
                   ' for update';
        dbms_output.put_line('sql: ' || sql_str);
        EXECUTE IMMEDIATE sql_str;
        sql_str := 'insert into ' || rpl_schema || '.' || temp_table ||
                   ' select * from ' ||  clw_schema || '.' || lower(table_name) ||
                   ' where (cast(repl_status as integer) & ' || repl_mask ||') != 0  and repl_status is not null' || filter_str;

        dbms_output.put_line('sql: ' || sql_str);
        EXECUTE IMMEDIATE sql_str;
        sql_str := 'update ' ||  clw_schema || '.' || lower(table_name) || ' a' ||
                   ' set repl_status = (cast(a.repl_status as integer) # ' || repl_mask || ')' ||
                   ' where (cast(repl_status as integer) & ' || repl_mask ||') != 0  and repl_status is not null' || filter_str;
        dbms_output.put_line('sql: ' || sql_str);
        EXECUTE IMMEDIATE sql_str;
        sql_str := 'update ' ||  rpl_schema || '.' || temp_table ||
                   ' set repl_status = (31 # ' || repl_mask || ')';
        dbms_output.put_line('sql: ' || sql_str);
        EXECUTE IMMEDIATE sql_str;
      end if;
    end if;

  end loop;
-- #############################################################################
END;
