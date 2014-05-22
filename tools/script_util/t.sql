set echo on
set serverout on size 1000000

declare 
 cnt number; 
 stmt varchar2(1024);
 cursor tabs is select table_name from all_tables where
   table_name like 'CLW%LOG' and owner like 'LOG_%';

begin
FOR tab_rec IN tabs LOOP

dbms_output.put_line(tab_rec.table_name);
stmt := 'select count(*) from ' || tab_rec.table_name;
dbms_output.put_line('stmt '||stmt);
execute immediate stmt into cnt;

if cnt >1000 then 
  dbms_output.put_line('1 found '||cnt);
  stmt := 'delete from ' || tab_rec.table_name || ' where log_date < sysdate-100 '; 
  execute immediate stmt;
  dbms_output.put_line('2 deleted records from '||tab_rec.table_name);
  commit;
end if;


END LOOP;

END; -- main
/

quit;

