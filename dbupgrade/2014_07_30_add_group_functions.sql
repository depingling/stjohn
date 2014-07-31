insert into clw_group_assoc
select clw_group_assoc_seq.nextval, b.group_assoc_cd, sysdate, 'deping', sysdate, 'deping', null, b.group_id, 0, 'Access to Orders',0 
from clw_group a, clw_group_assoc b 
where a.group_id=b.group_id
and application_function='Access to Shopping'
and group_status_cd='ACTIVE'
and a.group_id not in (select a.group_id from clw_group a, clw_group_assoc b 
where a.group_id=b.group_id
and application_function='Access to Orders'
and group_status_cd='ACTIVE')
/ 


insert into clw_group_assoc
select clw_group_assoc_seq.nextval, b.group_assoc_cd, sysdate, 'deping', sysdate, 'deping', null, b.group_id, 0, 'Access to Dashboard',0 
from clw_group a, clw_group_assoc b 
where a.group_id=b.group_id
and application_function='Access to Shopping'
and group_status_cd='ACTIVE'
and a.group_id not in (select a.group_id from clw_group a, clw_group_assoc b 
where a.group_id=b.group_id
and application_function='Access to Dashboard'
and group_status_cd='ACTIVE')
/ 