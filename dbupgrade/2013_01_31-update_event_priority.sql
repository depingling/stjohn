CREATE TABLE DL_EVENT_PRIORITY
(
    EVENT_ID         NUMBER(38,0) NOT NULL ENABLE,
    PROCESS_PRIORITY NUMBER,
    constraint DL_EVENT_PRIORITY_PK primary key (EVENT_ID)
);

insert into dl_event_priority ( 
	select event_id, 
	(select max(c.process_priority) from clw_event_property b, clw_process c where b.number_val = c.process_id and b.type = 'PROCESS_TEMPLATE_ID' and b.event_id=a.event_id) process_priority
	from clw_event a
	where type = 'PROCESS'
	and event_priority is null
);

update dl_event_priority set process_priority = 50 where process_priority is null;

update clw_event a set event_priority=(select PROCESS_PRIORITY from dl_event_priority b
  where a.event_id=b.event_id)
where a.event_priority is null;

update clw_event a set event_priority=50 --EMAIL TYPE
where a.event_priority is null;

ALTER TABLE clw_event MODIFY event_priority DEFAULT 50;

DROP TABLE dl_event_priority;