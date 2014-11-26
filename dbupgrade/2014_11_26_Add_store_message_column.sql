alter table clw_store_message add MESSAGE_MANAGED_BY VARCHAR2(30);
update clw_store_message set MESSAGE_MANAGED_BY='ADMINISTRATOR' where MESSAGE_MANAGED_BY is null;
commit;