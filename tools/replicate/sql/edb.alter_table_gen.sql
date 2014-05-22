select 'alter table ' || relname || ' add repl_status number(38);' from pg_class where lower(relname) like 'clw%';
