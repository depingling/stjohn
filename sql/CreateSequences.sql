select 'CREATE SEQUENCE ' || table_name || '_SEQ START WITH 1 INCREMENT BY 1 NOMINVALUE NOMAXVALUE NOCYCLE CACHE 10 NOORDER;' from dba_tables where owner = UPPER('@dbUser@');
