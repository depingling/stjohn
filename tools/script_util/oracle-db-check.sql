
set serverout on

declare
  fm number;
  begin
  select bytes into fm from v$sgastat where name = 'free memory' and pool = 'shared pool';

    if fm < 30000000 then
      DBMS_OUTPUT.PUT_LINE(' Memory low, at: ' || fm);
      execute immediate 'alter system flush shared_pool';
    else
      DBMS_OUTPUT.PUT_LINE(' Memory is OK, at: ' || fm);
    end if;
 end;
/
quit;

