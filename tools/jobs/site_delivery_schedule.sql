set serveroutput on;
alter session set nls_date_format='DD-MON-YYYY HH24:MI:SS';
declare
v_site_delivery_id number;
v_week number;
v_delivery_day number;
v_cutoff_day number;
v_cutoff_system_time varchar2(40);
v_dt varchar2(40);
v_ts varchar2(40);
v_dt1 varchar2(40);
v_cst  varchar2(40);
v_cst1  varchar2(40);
cursor c_s is
select site_delivery_id,week,delivery_day,cutoff_day,to_char(cutoff_system_time,'DD-MON-YYYY HH24:MI:SS'),
to_char(cutoff_system_time,'hh24:mi:ss') ts,to_char(cutoff_site_time,'hh24:mi:ss') cst
from CLW_SITE_DELIVERY where status_cd = 'ACTIVE'
and DElivery_flag = 1 
and week in(53,1,2);
--and site_delivery_id in(6959811,6959814);

begin
--dbms_output.put_line('TTTTIIIIIIII');
open c_s;
loop
fetch c_s into v_site_delivery_id,v_week,v_delivery_day,v_cutoff_day,v_cutoff_system_time,v_ts,v_cst;
exit when c_s%notfound;

if v_cutoff_day >= v_delivery_day then
v_week := v_week - 2;
end if;

if v_week = -1 then
v_week := 52;
end if;

if v_week = 53 then
v_week := 52;
end if;

if v_week = 0 then
v_week := 1;
end if;

--dbms_output.put_line('v_site_delivery_id,v_week,v_delivery_day,v_cutoff_day,v_cutoff_system_time:' ||
--v_site_delivery_id ||',' || v_week ||',' || v_delivery_day ||',' || v_cutoff_day ||',' || v_cutoff_system_time);

select to_char(dt,'DD-MON-YYYY') into v_dt from
(select dt,week,to_char(dt,'d') dayy from
(select to_date(dt,'DD-MON-YYYY') dt,week from
(select to_char(dt,'DD-MON-YYYY') dt, to_char( dt+1, 'iw' ) week
      from ( select to_date('10-DEC-2011')+rownum dt
              from all_objects
                      where rownum < 200 )))) where week = v_week and dayy = v_cutoff_day;
					  
--dbms_output.put_line('v_dt==' || v_dt);
--dbms_output.put_line('v_ts==' || v_ts);
					  
v_dt1 := v_dt || ' ' || v_ts;
v_cst1 := v_dt || ' ' || v_cst;
--dbms_output.put_line('TTTT');
--dbms_output.put_line('v_dt1==' || v_dt1 || 'v_cst1==' || v_cst1);
execute immediate 'update clw_site_delivery set CUTOFF_SYSTEM_TIME = :1,CUTOFF_SITE_TIME = :2 where site_delivery_id = :3'
using v_dt1,v_cst1,v_site_delivery_id;
end loop;
commit;
close c_s;
end;
/

alter session set nls_date_format='DD-MON-YYYY HH24:MI:SS';
declare
v_site_delivery_id number;
v_week number;
v_delivery_day number;
v_cutoff_day number;
v_cutoff_system_time varchar2(40);
v_dt varchar2(40);
v_ts varchar2(40);
v_dt1 varchar2(40);
v_cst  varchar2(40);
v_cst1  varchar2(40);
cursor c_s is
select site_delivery_id,week,delivery_day,cutoff_day,to_char(cutoff_system_time,'DD-MON-YYYY HH24:MI:SS'),
to_char(cutoff_system_time,'hh24:mi:ss') ts,to_char(cutoff_site_time,'hh24:mi:ss') cst
from CLW_SITE_DELIVERY where status_cd = 'ACTIVE'
and DElivery_flag = 1 
and week in(3,4,5,6,7,8,9,10);
--and site_delivery_id in(7045915,6940014);

begin
--dbms_output.put_line('TTTTIIIIIIII');
open c_s;
loop
fetch c_s into v_site_delivery_id,v_week,v_delivery_day,v_cutoff_day,v_cutoff_system_time,v_ts,v_cst;
exit when c_s%notfound;

if v_cutoff_day >= v_delivery_day then
v_week := v_week - 1;
end if;

if v_week = -1 then
v_week := 52;
end if;

if v_week = 53 then
v_week := 52;
end if;

if v_week = 0 then
v_week := 1;
end if;
--dbms_output.put_line('week==' || v_week);
--dbms_output.put_line('v_site_delivery_id,v_week,v_delivery_day,v_cutoff_day,v_cutoff_system_time:' ||
--v_site_delivery_id ||',' || v_week ||',' || v_delivery_day ||',' || v_cutoff_day ||',' || v_cutoff_system_time);
--dbms_output.put_line('weeek==' || v_week);
select to_char(dt,'DD-MON-YYYY') into v_dt from
(select dt,week,to_char(dt,'d') dayy from
(select to_date(dt,'DD-MON-YYYY') dt,week from
(select to_char(dt,'DD-MON-YYYY') dt, to_char( dt+1, 'iw' ) week
      from ( select to_date('10-DEC-2011')+rownum dt
              from all_objects
                      where rownum < 200 )))) where week = v_week and dayy = v_cutoff_day;
					  
--dbms_output.put_line('v_dt==' || v_dt);
--dbms_output.put_line('v_ts==' || v_ts);
					  
v_dt1 := v_dt || ' ' || v_ts;
v_cst1 := v_dt || ' ' || v_cst;
--dbms_output.put_line('TTTT');
--dbms_output.put_line('v_dt1==' || v_dt1 || 'v_cst1==' || v_cst1);
execute immediate 'update clw_site_delivery set CUTOFF_SYSTEM_TIME = :1,CUTOFF_SITE_TIME = :2 where site_delivery_id = :3'
using v_dt1,v_cst1,v_site_delivery_id;
end loop;
commit;
close c_s;
end;
/

quit;