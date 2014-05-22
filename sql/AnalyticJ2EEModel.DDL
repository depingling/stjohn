-------------------------------------------------------------------------------
--
-- Title:        Rpt_J2EEModel.DDL
-- Description:  DDL for the purpose of creating the St. John reporting schema
-- Copyright:    Copyright (c) 2001
-- Company:      CleanWise, Inc.
--
-------------------------------------------------------------------------------


-------------------------------------------------------------------------------
--
-- Create tables
--
-------------------------------------------------------------------------------

create table RPT_REPORT_RESULT (
  REPORT_RESULT_ID NUMBER(38) not null,
  GENERIC_REPORT_ID NUMBER(38) null,
  REPORT_SCHEDULE_ID NUMBER(38) null,
  REPORT_CATEGORY	VARCHAR2(255) not null,
  REPORT_NAME VARCHAR2(255) not null,
  REPORT_RESULT_STATUS_CD  VARCHAR2(30) not null,
  USER_ID NUMBER(38) not null,
  PROTECTED_FL VARCHAR2(1) null,
  ADD_DATE DATE not null,	
  ADD_BY VARCHAR2(30) null,
  MOD_DATE DATE not null,	
  MOD_BY VARCHAR2(30) null,
    constraint RPT_REPORT_RESULT_PK primary key (REPORT_RESULT_ID) ) 
  INITRANS 1 MAXTRANS 255 TABLESPACE @analyticDataTablespace@ 
  PCTFREE 10  PCTUSED 40 STORAGE 
  ( INITIAL 128K NEXT 128K MINEXTENTS 1 MAXEXTENTS 4096 PCTINCREASE 0 FREELIST GROUPS 1 FREELISTS 1); 

create table RPT_REPORT_RESULT_PARAM (
  REPORT_RESULT_PARAM_ID NUMBER(38) not null,
  REPORT_RESULT_ID NUMBER(38) not null,
  PARAM_NAME  VARCHAR2(30) not null,
  PARAM_VALUE VARCHAR2(2000) not null,
    constraint RPT_REPORT_RESULT_PARAM_PK primary key (REPORT_RESULT_PARAM_ID) ) 
  INITRANS 1 MAXTRANS 255 TABLESPACE @analyticDataTablespace@ 
  PCTFREE 10  PCTUSED 40 STORAGE 
  ( INITIAL 128K NEXT 128K MINEXTENTS 1 MAXEXTENTS 4096 PCTINCREASE 0 FREELIST GROUPS 1 FREELISTS 1); 

create table RPT_REPORT_RESULT_LINE (
  REPORT_RESULT_LINE_ID NUMBER(38) not null,
  REPORT_RESULT_ID NUMBER(38) not null,
  PAGE_NAME  VARCHAR2(50) null,
  REPORT_RESULT_LINE_CD  VARCHAR2(30) not null,
  LINE_VALUE_BLOB BLOB null,
    constraint RPT_REPORT_RESULT_LINE_PK primary key (REPORT_RESULT_LINE_ID) ) 
  INITRANS 1 MAXTRANS 255 TABLESPACE @analyticDataTablespace@ 
  PCTFREE 10  PCTUSED 40 STORAGE 
  ( INITIAL 128K NEXT 128K MINEXTENTS 1 MAXEXTENTS 4096 PCTINCREASE 0 FREELIST GROUPS 1 FREELISTS 1); 

create table RPT_REPORT_RESULT_ASSOC (
  REPORT_RESULT_ASSOC_ID NUMBER(38) not null,
  REPORT_RESULT_ID NUMBER(38) not null,
  ASSOC_REF_ID NUMBER(38) not null,
  REPORT_RESULT_ASSOC_CD  VARCHAR2(30) not null,
  ADD_DATE DATE not null,	
  ADD_BY VARCHAR2(30) null,
  MOD_DATE DATE not null,	
  MOD_BY VARCHAR2(30) null,
    constraint RPT_REPORT_RESULT_ASSOC_PK primary key (REPORT_RESULT_ASSOC_ID) ) 
  INITRANS 1 MAXTRANS 255 TABLESPACE @analyticDataTablespace@ 
  PCTFREE 10  PCTUSED 40 STORAGE 
  ( INITIAL 128K NEXT 128K MINEXTENTS 1 MAXEXTENTS 4096 PCTINCREASE 0 FREELIST GROUPS 1 FREELISTS 1); 

create table RPT_REPORT_SCHEDULE (
  REPORT_SCHEDULE_ID NUMBER(38) not null,
  SHORT_DESC  VARCHAR2(30) null,
  GENERIC_REPORT_ID NUMBER(38) null,
  REPORT_SCHEDULE_STATUS_CD  VARCHAR2(30) null,
  REPORT_SCHEDULE_RULE_CD  VARCHAR2(30) null,
  CYCLE NUMBER(38) null,
  LAST_RUN_DATE DATE null,	
  ADD_DATE DATE not null,	
  ADD_BY VARCHAR2(30) null,
  MOD_DATE DATE not null,	
  MOD_BY VARCHAR2(30) null,
    constraint RPT_REPORT_SCHEDULE_PK primary key (REPORT_SCHEDULE_ID) ) 
  INITRANS 1 MAXTRANS 255 TABLESPACE @analyticDataTablespace@ 
  PCTFREE 10  PCTUSED 40 STORAGE 
  ( INITIAL 128K NEXT 128K MINEXTENTS 1 MAXEXTENTS 4096 PCTINCREASE 0 FREELIST GROUPS 1 FREELISTS 1); 

create table RPT_REPORT_SCHEDULE_DETAIL (
  REPORT_SCHEDULE_DETAIL_ID NUMBER(38) not null,
  REPORT_SCHEDULE_ID NUMBER(38) not null,
  REPORT_SCHEDULE_DETAIL_CD  VARCHAR2(30) not null,
  DETAIL_NAME VARCHAR2(30) null,
  DETAIL_VALUE VARCHAR2(2000 CHAR) null,
  ADD_DATE DATE not null,	
  ADD_BY VARCHAR2(30) null,
  MOD_DATE DATE not null,	
  MOD_BY VARCHAR2(30) null,
    constraint RPT_REPORT_SCHEDULE_DETAIL_PK primary key (REPORT_SCHEDULE_DETAIL_ID) ) 
  INITRANS 1 MAXTRANS 255 TABLESPACE @analyticDataTablespace@ 
  PCTFREE 10  PCTUSED 40 STORAGE 
  ( INITIAL 128K NEXT 128K MINEXTENTS 1 MAXEXTENTS 4096 PCTINCREASE 0 FREELIST GROUPS 1 FREELISTS 1); 

-------------------------------------------------------------------------------
--
-- Create foreign key constraints
--
-------------------------------------------------------------------------------

alter table RPT_REPORT_RESULT_PARAM
	add constraint RPT_REPORT_RESULT_PARAM_RR_FK foreign key (
		REPORT_RESULT_ID)
	 references RPT_REPORT_RESULT (
		REPORT_RESULT_ID); 

alter table RPT_REPORT_RESULT_LINE
	add constraint RPT_REPORT_RESULT_LINE_RR_FK foreign key (
		REPORT_RESULT_ID)
	 references RPT_REPORT_RESULT (
		REPORT_RESULT_ID); 

alter table RPT_REPORT_RESULT_ASSOC
	add constraint RPT_REPORT_RESULT_ASSOC_RR_FK foreign key (
		REPORT_RESULT_ID)
	 references RPT_REPORT_RESULT (
		REPORT_RESULT_ID); 

alter table RPT_REPORT_SCHEDULE_DETAIL
	add constraint RPT_REPORT_SCHED_DETAIL_RS_FK foreign key (
		REPORT_SCHEDULE_ID)
	 references RPT_REPORT_SCHEDULE (
		REPORT_SCHEDULE_ID); 

-------------------------------------------------------------------------------
--
-- Create sequences
--
-------------------------------------------------------------------------------

-------------------------------------------------------------------------------
--
-- Title:        Rpt_J2EEModel.DDL
-- Description:  DDL for the purpose of creating the St. John reporting schema
-- Copyright:    Copyright (c) 2001
-- Company:      CleanWise, Inc.
--
-------------------------------------------------------------------------------


-------------------------------------------------------------------------------
--
-- Create tables
--
-------------------------------------------------------------------------------

create table RPT_REPORT_RESULT (
  REPORT_RESULT_ID NUMBER(38) not null,
  GENERIC_REPORT_ID NUMBER(38) null,
  REPORT_SCHEDULE_ID NUMBER(38) null,
  REPORT_CATEGORY	VARCHAR2(255) not null,
  REPORT_NAME VARCHAR2(255) not null,
  REPORT_RESULT_STATUS_CD  VARCHAR2(30) not null,
  USER_ID NUMBER(38) not null,
  PROTECTED_FL VARCHAR2(1) null,
  ADD_DATE DATE not null,	
  ADD_BY VARCHAR2(30) null,
  MOD_DATE DATE not null,	
  MOD_BY VARCHAR2(30) null,
    constraint RPT_REPORT_RESULT_PK primary key (REPORT_RESULT_ID) ) 
  INITRANS 1 MAXTRANS 255 TABLESPACE @analyticDataTablespace@ 
  PCTFREE 10  PCTUSED 40 STORAGE 
  ( INITIAL 128K NEXT 128K MINEXTENTS 1 MAXEXTENTS 4096 PCTINCREASE 0 FREELIST GROUPS 1 FREELISTS 1); 

create table RPT_REPORT_RESULT_PARAM (
  REPORT_RESULT_PARAM_ID NUMBER(38) not null,
  REPORT_RESULT_ID NUMBER(38) not null,
  PARAM_NAME  VARCHAR2(30) not null,
  PARAM_VALUE VARCHAR2(2000) not null,
    constraint RPT_REPORT_RESULT_PARAM_PK primary key (REPORT_RESULT_PARAM_ID) ) 
  INITRANS 1 MAXTRANS 255 TABLESPACE @analyticDataTablespace@ 
  PCTFREE 10  PCTUSED 40 STORAGE 
  ( INITIAL 128K NEXT 128K MINEXTENTS 1 MAXEXTENTS 4096 PCTINCREASE 0 FREELIST GROUPS 1 FREELISTS 1); 

create table RPT_REPORT_RESULT_LINE (
  REPORT_RESULT_LINE_ID NUMBER(38) not null,
  REPORT_RESULT_ID NUMBER(38) not null,
  PAGE_NAME  VARCHAR2(30) null,
  REPORT_RESULT_LINE_CD  VARCHAR2(30) not null,
  LINE_VALUE_BLOB BLOB null,
    constraint RPT_REPORT_RESULT_LINE_PK primary key (REPORT_RESULT_LINE_ID) ) 
  INITRANS 1 MAXTRANS 255 TABLESPACE @analyticDataTablespace@ 
  PCTFREE 10  PCTUSED 40 STORAGE 
  ( INITIAL 128K NEXT 128K MINEXTENTS 1 MAXEXTENTS 4096 PCTINCREASE 0 FREELIST GROUPS 1 FREELISTS 1); 

create table RPT_REPORT_RESULT_ASSOC (
  REPORT_RESULT_ASSOC_ID NUMBER(38) not null,
  REPORT_RESULT_ID NUMBER(38) not null,
  ASSOC_REF_ID NUMBER(38) not null,
  REPORT_RESULT_ASSOC_CD  VARCHAR2(30) not null,
  ADD_DATE DATE not null,	
  ADD_BY VARCHAR2(30) null,
  MOD_DATE DATE not null,	
  MOD_BY VARCHAR2(30) null,
    constraint RPT_REPORT_RESULT_ASSOC_PK primary key (REPORT_RESULT_ASSOC_ID) ) 
  INITRANS 1 MAXTRANS 255 TABLESPACE @analyticDataTablespace@ 
  PCTFREE 10  PCTUSED 40 STORAGE 
  ( INITIAL 128K NEXT 128K MINEXTENTS 1 MAXEXTENTS 4096 PCTINCREASE 0 FREELIST GROUPS 1 FREELISTS 1); 

create table RPT_REPORT_SCHEDULE (
  REPORT_SCHEDULE_ID NUMBER(38) not null,
  SHORT_DESC  VARCHAR2(30) null,
  GENERIC_REPORT_ID NUMBER(38) null,
  REPORT_SCHEDULE_STATUS_CD  VARCHAR2(30) null,
  REPORT_SCHEDULE_RULE_CD  VARCHAR2(30) null,
  CYCLE NUMBER(38) null,
  LAST_RUN_DATE DATE null,	
  ADD_DATE DATE not null,	
  ADD_BY VARCHAR2(30) null,
  MOD_DATE DATE not null,	
  MOD_BY VARCHAR2(30) null,
    constraint RPT_REPORT_SCHEDULE_PK primary key (REPORT_SCHEDULE_ID) ) 
  INITRANS 1 MAXTRANS 255 TABLESPACE @analyticDataTablespace@ 
  PCTFREE 10  PCTUSED 40 STORAGE 
  ( INITIAL 128K NEXT 128K MINEXTENTS 1 MAXEXTENTS 4096 PCTINCREASE 0 FREELIST GROUPS 1 FREELISTS 1); 

create table RPT_REPORT_SCHEDULE_DETAIL (
  REPORT_SCHEDULE_DETAIL_ID NUMBER(38) not null,
  REPORT_SCHEDULE_ID NUMBER(38) not null,
  REPORT_SCHEDULE_DETAIL_CD  VARCHAR2(30) not null,
  DETAIL_NAME VARCHAR2(30) null,
  DETAIL_VALUE VARCHAR2(255) null,
  ADD_DATE DATE not null,	
  ADD_BY VARCHAR2(30) null,
  MOD_DATE DATE not null,	
  MOD_BY VARCHAR2(30) null,
    constraint RPT_REPORT_SCHEDULE_DETAIL_PK primary key (REPORT_SCHEDULE_DETAIL_ID) ) 
  INITRANS 1 MAXTRANS 255 TABLESPACE @analyticDataTablespace@ 
  PCTFREE 10  PCTUSED 40 STORAGE 
  ( INITIAL 128K NEXT 128K MINEXTENTS 1 MAXEXTENTS 4096 PCTINCREASE 0 FREELIST GROUPS 1 FREELISTS 1); 

-------------------------------------------------------------------------------
--
-- Create foreign key constraints
--
-------------------------------------------------------------------------------

alter table RPT_REPORT_RESULT_PARAM
	add constraint RPT_REPORT_RESULT_PARAM_RR_FK foreign key (
		REPORT_RESULT_ID)
	 references RPT_REPORT_RESULT (
		REPORT_RESULT_ID); 

alter table RPT_REPORT_RESULT_LINE
	add constraint RPT_REPORT_RESULT_LINE_RR_FK foreign key (
		REPORT_RESULT_ID)
	 references RPT_REPORT_RESULT (
		REPORT_RESULT_ID); 

alter table RPT_REPORT_RESULT_ASSOC
	add constraint RPT_REPORT_RESULT_ASSOC_RR_FK foreign key (
		REPORT_RESULT_ID)
	 references RPT_REPORT_RESULT (
		REPORT_RESULT_ID); 

alter table RPT_REPORT_SCHEDULE_DETAIL
	add constraint RPT_REPORT_SCHED_DETAIL_RS_FK foreign key (
		REPORT_SCHEDULE_ID)
	 references RPT_REPORT_SCHEDULE (
		REPORT_SCHEDULE_ID); 

-------------------------------------------------------------------------------
--
-- Create indexes
--
-------------------------------------------------------------------------------


create sequence RPT_REPORT_RESULT_SEQ start with 1 increment by 1;

create sequence RPT_REPORT_RESULT_PARAM_SEQ start with 1 increment by 1;

create sequence RPT_REPORT_RESULT_LINE_SEQ start with 1 increment by 1;

create sequence RPT_REPORT_RESULT_ASSOC_SEQ start with 1 increment by 1;
