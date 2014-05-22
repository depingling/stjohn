select txt from (
select 1 rank,'declare cnt int;begin'  txt from dual
union all
select 2,
'select count(*) into cnt from '||table_name||' where log_date > sysdate-61;if cnt >1000 then delete '||table_name||' where log_date < sysdate-61; end if; commit;' 
from  all_tables where owner= 'LOG_STJOHN_PROD'
and table_name like 'CLW%LOG'
union all
select 3, 'end;' from dual
union all
select 4, '/' from dual
union all
select 5,'quit;' from dual
) order by rank;
