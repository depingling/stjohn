select 'alter table ' || table_name || ' add (repl_status number(38));' from user_tables where lower(table_name) like 'clw%';


