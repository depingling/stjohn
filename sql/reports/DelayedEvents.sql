select  process_name, duration, event_id, status, event_priority, substr(to_char(wm_concat(params)),1,200) as params
from (
select
p.process_name, 
round((sysdate-e.mod_date)*24-0.5)||'h '||(round((sysdate-e.mod_date)*24*60)-round((sysdate-e.mod_date)*24-0.5)*60)||'m' as duration,
e.event_id, e.status, e.add_date, e.mod_date, e.event_priority,  
ep.short_desc||'='||
case ep.var_class when 'java.lang.Integer' then to_char(ep.number_val) 
                  when 'java.lang.String' then ep.string_val 
                  when 'java.lang.Date' then to_char(ep.date_val,'mm/dd/yyy hh24:mi') end as params
from clw_event e, clw_event_property ep, clw_event_property proc, clw_process p
where 1=1 
and status in  ('READY','IN_PROGRESS')
and e.event_id = ep.event_id(+)
and e.type='PROCESS'
and ep.type(+)= 'PROCESS_VARIABLE'
and ep.var_class(+) in ('java.lang.Integer','java.lang.String','java.lang.Date')
and (ep.number_val is not null or trim(ep.string_val) is not null or ep.date_val is not null or ep.string_val!= '<null>')
and proc.event_id = e.event_id
and proc.type= 'PROCESS_TEMPLATE_ID'
and p.process_id = proc.number_val
and e.event_id not in 
  (select event_id 
     from clw_event_property ep1 
    where ep1.short_desc = 'command' 
      and ep1.string_val = 'reset_cost_centers')
and (sysdate-e.mod_date)*24*60 > 10
union all
select  'EMAIL',
round((sysdate-e.mod_date)*24-0.5)||'h '||(round((sysdate-e.mod_date)*24*60)-round((sysdate-e.mod_date)*24-0.5)*60)||'m' as duration,
e.event_id, e.status, e.add_date, e.mod_date, e.event_priority, ('SUBJECT='||subject||', TO_ADDRESS='||to_address) as params
from clw_event e,  clw_event_email ee 
where 1=1 
and e.type='EMAIL'
and status in  ('READY','IN_PROGRESS')
and e.event_id = ee.event_id(+)
and (sysdate-e.mod_date)*24*60 > 10
)
group by process_name, duration, event_id, status, event_priority
