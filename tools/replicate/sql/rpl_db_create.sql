drop table RPL_REPL_GROUP;
create table RPL_REPL_GROUP
(
  REPL_GROUP_ID      NUMBER(38) not null,
  NAME               VARCHAR2(255),
  DESCRIPTION        VARCHAR2(1000),
  STATUS             VARCHAR2(30),
  ADD_BY             VARCHAR2(255),
  ADD_DATE           DATE not null,
  MOD_BY             VARCHAR2(255),
  MOD_DATE           DATE not null
);
alter table RPL_REPL_GROUP
  add constraint RPL_REPL_GROUP_PK primary key (REPL_GROUP_ID);

drop table RPL_REPL_SUBSCRIBER;
create table RPL_REPL_SUBSCRIBER
(
  REPL_SUBSCRIBER_ID NUMBER(38) not null,
  REPL_GROUP_ID      NUMBER(38) not null,
  NAME               VARCHAR2(255),
  MASTER_CLW_SCHEMA  VARCHAR2(255),
  SLAVE_CLW_SCHEMA   VARCHAR2(255),
  MASTER_RPL_SCHEMA  VARCHAR2(255),
  SLAVE_RPL_SCHEMA   VARCHAR2(255),
  DESCRIPTION        VARCHAR2(1000),
  MASK               NUMBER(38),
  STATUS             VARCHAR2(30),
  FIRE               NUMBER(1) default 0,
  ADD_BY             VARCHAR2(255),
  ADD_DATE           DATE not null,
  MOD_BY             VARCHAR2(255),
  MOD_DATE           DATE not null
);
alter table RPL_REPL_SUBSCRIBER
  add constraint RPL_REPL_SUBSCRIBER_PK primary key (REPL_SUBSCRIBER_ID);
alter table RPL_REPL_SUBSCRIBER
  add constraint RPL_REPL_SUBSCRIBER_FK foreign key (REPL_GROUP_ID)
  references RPL_REPL_GROUP (REPL_GROUP_ID);

drop table RPL_REPL_SESSION;
create table RPL_REPL_SESSION
(
  REPL_SESSION_ID          NUMBER(38) not null,
  REPL_SUBSCRIBER_ID       NUMBER(38) not null,
  START_DATE               DATE default null,
  END_DATE                 DATE,
  PREVIOUS_START_DATE      DATE,
  ADD_BY                   VARCHAR2(255),
  ADD_DATE                 DATE not null,
  MOD_BY                   VARCHAR2(255),
  MOD_DATE                 DATE not null
);
alter table RPL_REPL_SESSION
  add constraint RPL_REPL_SESSION_PK primary key (REPL_SESSION_ID);
alter table RPL_REPL_SESSION
  add constraint RPL_REPL_SESSION_FK foreign key (REPL_SUBSCRIBER_ID)
  references RPL_REPL_SUBSCRIBER (REPL_SUBSCRIBER_ID);

drop table RPL_REPL_LOGGING;
create table RPL_REPL_LOGGING
(
  REPL_LOGGING_ID          NUMBER(38) not null,
  REPL_SESSION_ID          NUMBER(38) not null,
  LOG_MESSAGE              VARCHAR2(2000),
  ADD_BY                   VARCHAR2(255),
  ADD_DATE                 DATE not null,
  MOD_BY                   VARCHAR2(255),
  MOD_DATE                 DATE not null
);
alter table RPL_REPL_LOGGING
  add constraint RPL_REPL_LOGGING_PK primary key (REPL_LOGGING_ID);
alter table RPL_REPL_LOGGING
  add constraint RPL_REPL_LOGGING_FK foreign key (REPL_SESSION_ID)
  references RPL_REPL_SESSION (REPL_SESSION_ID);

drop table RPL_REPL_TABLE;
create table RPL_REPL_TABLE
(
  REPL_TABLE_ID            NUMBER(38) not null,
  REPL_GROUP_ID            NUMBER(38) not null,
  TABLE_NAME               VARCHAR2(255),
  REPL_ORDER               NUMBER(38),
  DIRECTION                VARCHAR2(30),
  FILTER                   VARCHAR2(1000),
  STATUS                   VARCHAR2(30),
  ADD_BY                   VARCHAR2(255),
  ADD_DATE                 DATE not null,
  MOD_BY                   VARCHAR2(255),
  MOD_DATE                 DATE not null
);
alter table RPL_REPL_TABLE
  add constraint RPL_REPL_TABLE_PK primary key (REPL_TABLE_ID);
alter table RPL_REPL_TABLE
  add constraint RPL_REPL_TABLE_FK foreign key (REPL_GROUP_ID)
  references RPL_REPL_GROUP (REPL_GROUP_ID);

drop sequence RPL_REPL_GROUP_SEQ;
create sequence RPL_REPL_GROUP_SEQ start with 1 increment by 1;
drop sequence RPL_REPL_SUBSCRIBER_SEQ;
create sequence RPL_REPL_SUBSCRIBER_SEQ start with 1 increment by 1;
drop sequence RPL_REPL_SESSION_SEQ;
create sequence RPL_REPL_SESSION_SEQ start with 1 increment by 1;
drop sequence RPL_REPL_LOGGING_SEQ;
create sequence RPL_REPL_LOGGING_SEQ start with 1 increment by 1;
drop sequence RPL_REPL_TABLE_SEQ;
create sequence RPL_REPL_TABLE_SEQ start with 1 increment by 1;
-- ********************************************************************************
insert into RPL_REPL_GROUP values(RPL_REPL_GROUP_SEQ.nextval, 'Test Replication Group', '', 'Active', 'init', current_date, 'init', current_date);
insert into RPL_REPL_SUBSCRIBER values(RPL_REPL_SUBSCRIBER_SEQ.nextval, RPL_REPL_GROUP_SEQ.currval, 'EDB', 'STJOHNSMITH', 'STJOHNSMITH','STJOHNSMITH','STJOHNSMITH','For test purpose', 1, 'Active', 0, 'init', current_date, 'init', current_date);
insert into RPL_REPL_TABLE values(RPL_REPL_TABLE_SEQ.nextval, RPL_REPL_GROUP_SEQ.currval, 'CLW_TASK', 1, 'Bidirectional', '', 'Active', 'init', current_date, 'init', current_date);
insert into RPL_REPL_TABLE values(RPL_REPL_TABLE_SEQ.nextval, RPL_REPL_GROUP_SEQ.currval, 'CLW_USER', 2, 'Bidirectional', '', 'Active', 'init', current_date, 'init', current_date);


