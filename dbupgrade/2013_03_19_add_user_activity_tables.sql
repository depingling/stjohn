CREATE TABLE rpt_user_activity_wrk ( 
  user_activity_wrk_id NUMBER(38,0) NULL, 
  token_type VARCHAR2(10) NULL, 
  store_id NUMBER(38,0) NULL, 
  store_name VARCHAR2(100) NULL, 
  session_id VARCHAR2(40) NULL, 
  user_name VARCHAR2(40) NULL, 
  action_class VARCHAR2(40) NULL, 
  action VARCHAR2(80) NULL, 
  http_start_time DATE NULL, 
  action_start_time DATE NULL, 
  http_end_time DATE NULL, 
  action_end_time DATE NULL, 
  action_result VARCHAR2(10) NULL, 
  http_result VARCHAR2(10) NULL, 
  action_duration NUMBER(15,3) NULL, 
  http_duration NUMBER(15,3) NULL, 
  referer VARCHAR2(500) NULL, 
  params VARCHAR2(4000) NULL, 
  finish_file VARCHAR2(120) NULL, 
  request_id VARCHAR2(30) NULL, 
  server_name VARCHAR2(50) NULL, 
  add_date DATE NOT NULL, 
  add_by VARCHAR2(30) NULL, 
  mod_date DATE NOT NULL, 
  mod_by VARCHAR2(30) NULL, 
  CONSTRAINT rpt_user_activity_wrk_pk PRIMARY KEY (user_activity_wrk_id)
) 
/ 

CREATE SEQUENCE rpt_user_activity_wrk_seq 
  MINVALUE 1 
  MAXVALUE 999999999999999999999999999 
  INCREMENT BY 1 
  NOCYCLE 
  NOORDER 
  CACHE 20 
/ 

CREATE TABLE RPT_USER_ACTIVITY ( 
  user_activity_id NUMBER(38,0) NULL, 
  store_id NUMBER(38,0) NULL, 
  store_name VARCHAR2(100) NULL, 
  session_id VARCHAR2(40) NULL, 
  user_name VARCHAR2(40) NULL, 
  action_class VARCHAR2(40) NULL, 
  action VARCHAR2(80) NULL, 
  http_start_time DATE NULL, 
  action_start_time DATE NULL, 
  http_end_time DATE NULL, 
  action_end_time DATE NULL, 
  action_result VARCHAR2(10) NULL, 
  http_result VARCHAR2(10) NULL, 
  action_duration NUMBER(15,3) NULL, 
  http_duration NUMBER(15,3) NULL, 
  referer VARCHAR2(500) NULL, 
  params VARCHAR2(4000) NULL, 
  finish_file VARCHAR2(120) NULL, 
  request_id VARCHAR2(30) NULL, 
  server_name VARCHAR2(50) NULL, 
  add_date DATE NOT NULL, 
  add_by VARCHAR2(30) NULL, 
  mod_date DATE NOT NULL, 
  mod_by VARCHAR2(30) NULL, 
  CONSTRAINT rpt_user_activity_pk PRIMARY KEY (user_activity_id)
) 
/ 

CREATE SEQUENCE RPT_USER_ACTIVITY_SEQ
  MINVALUE 1 
  MAXVALUE 999999999999999999999999999 
  INCREMENT BY 1 
  NOCYCLE 
  NOORDER 
  CACHE 20 
/

create index RPT_USER_ACTIVITY_I on RPT_USER_ACTIVITY (session_id, request_id)

create index RPT_USER_ACTIVITY_WRK_I on RPT_USER_ACTIVITY_WRK (session_id, request_id)

create index RPT_USER_ACTIVITY_WRK_I1 on RPT_USER_ACTIVITY_WRK (server_name)
