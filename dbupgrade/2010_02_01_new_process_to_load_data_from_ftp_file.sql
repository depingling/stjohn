INSERT INTO CLW_PROCESS
(
    PROCESS_ID,
    PROCESS_NAME,
    PROCESS_TYPE_CD,
    PROCESS_STATUS_CD,
    ADD_DATE,
    ADD_BY,
    MOD_DATE,
    MOD_BY
)
SELECT
    CLW_PROCESS_SEQ.NEXTVAL,
    'PROCESS_LOAD_TABLE_DATA_FROM_FTP_FILE',
    'TEMPLATE',
    'ACTIVE',
    SYSDATE,
    'VLAD',
    SYSDATE,
    'VLAD'
FROM DUAL;

INSERT INTO CLW_TASK
(
    TASK_ID,
    TASK_TEMPLATE_ID,
    PROCESS_ID,
    TASK_NAME,
    VAR_CLASS,
    METHOD,
    TASK_TYPE_CD,
    TASK_STATUS_CD,
    ADD_DATE,
    ADD_BY,
    MOD_DATE,
    MOD_BY
)
SELECT 
    CLW_TASK_SEQ.NEXTVAL,
    NULL,
    (
        SELECT PROCESS_ID FROM CLW_PROCESS 
        WHERE 
            PROCESS_NAME='PROCESS_LOAD_TABLE_DATA_FROM_FTP_FILE' AND 
            PROCESS_TYPE_CD='TEMPLATE'
    ),
    'FtpFileToDbTableLoader',
    'com.cleanwise.service.api.process.operations.FtpFileToDbTableLoader',
    'loadFile',
    'TEMPLATE',
    'ACTIVE',
    SYSDATE,
    'VLAD',
    SYSDATE,
    'VLAD'
FROM DUAL;

INSERT INTO CLW_TASK_PROPERTY
(
    TASK_PROPERTY_ID,
    TASK_ID,
    PROPERTY_TYPE_CD,
    POSITION,
    VAR_TYPE,
    VAR_NAME,
    ADD_DATE,
    ADD_BY,
    MOD_DATE,
    MOD_BY,
    TASK_PROPERTY_STATUS_CD
)
SELECT 
    CLW_TASK_PROPERTY_SEQ.NEXTVAL,
    (
        SELECT TASK_ID FROM CLW_TASK 
        WHERE 
            TASK_NAME='FtpFileToDbTableLoader' AND 
            TASK_TYPE_CD='TEMPLATE' 
    ),
    'MANDATORY',
    1,
    'java.lang.String',
    'fromhost',
    SYSDATE,
    'VLAD',
    SYSDATE,
    'VLAD',
    'ACTIVE'
FROM DUAL;

INSERT INTO CLW_TASK_PROPERTY
(
    TASK_PROPERTY_ID,
    TASK_ID,
    PROPERTY_TYPE_CD,
    POSITION,
    VAR_TYPE,
    VAR_NAME,
    ADD_DATE,
    ADD_BY,
    MOD_DATE,
    MOD_BY,
    TASK_PROPERTY_STATUS_CD
)
SELECT 
    CLW_TASK_PROPERTY_SEQ.NEXTVAL,
    (
        SELECT TASK_ID FROM CLW_TASK 
        WHERE 
            TASK_NAME='FtpFileToDbTableLoader' AND 
            TASK_TYPE_CD='TEMPLATE' 
    ),
    'MANDATORY',
    2,
    'java.lang.String',
    'port',
    SYSDATE,
    'VLAD',
    SYSDATE,
    'VLAD',
    'ACTIVE'
FROM DUAL;

INSERT INTO CLW_TASK_PROPERTY
(
    TASK_PROPERTY_ID,
    TASK_ID,
    PROPERTY_TYPE_CD,
    POSITION,
    VAR_TYPE,
    VAR_NAME,
    ADD_DATE,
    ADD_BY,
    MOD_DATE,
    MOD_BY,
    TASK_PROPERTY_STATUS_CD
)
SELECT 
    CLW_TASK_PROPERTY_SEQ.NEXTVAL,
    (
        SELECT TASK_ID FROM CLW_TASK 
        WHERE 
            TASK_NAME='FtpFileToDbTableLoader' AND 
            TASK_TYPE_CD='TEMPLATE' 
    ),
    'MANDATORY',
    3,
    'java.lang.String',
    'user',
    SYSDATE,
    'VLAD',
    SYSDATE,
    'VLAD',
    'ACTIVE'
FROM DUAL;

INSERT INTO CLW_TASK_PROPERTY
(
    TASK_PROPERTY_ID,
    TASK_ID,
    PROPERTY_TYPE_CD,
    POSITION,
    VAR_TYPE,
    VAR_NAME,
    ADD_DATE,
    ADD_BY,
    MOD_DATE,
    MOD_BY,
    TASK_PROPERTY_STATUS_CD
)
SELECT 
    CLW_TASK_PROPERTY_SEQ.NEXTVAL,
    (
        SELECT TASK_ID FROM CLW_TASK 
        WHERE 
            TASK_NAME='FtpFileToDbTableLoader' AND 
            TASK_TYPE_CD='TEMPLATE' 
    ),
    'MANDATORY',
    4,
    'java.lang.String',
    'password',
    SYSDATE,
    'VLAD',
    SYSDATE,
    'VLAD',
    'ACTIVE'
FROM DUAL;

INSERT INTO CLW_TASK_PROPERTY
(
    TASK_PROPERTY_ID,
    TASK_ID,
    PROPERTY_TYPE_CD,
    POSITION,
    VAR_TYPE,
    VAR_NAME,
    ADD_DATE,
    ADD_BY,
    MOD_DATE,
    MOD_BY,
    TASK_PROPERTY_STATUS_CD
)
SELECT 
    CLW_TASK_PROPERTY_SEQ.NEXTVAL,
    (
        SELECT TASK_ID FROM CLW_TASK 
        WHERE 
            TASK_NAME='FtpFileToDbTableLoader' AND 
            TASK_TYPE_CD='TEMPLATE' 
    ),
    'MANDATORY',
    5,
    'java.lang.String',
    'fromdir',
    SYSDATE,
    'VLAD',
    SYSDATE,
    'VLAD',
    'ACTIVE'
FROM DUAL;

INSERT INTO CLW_TASK_PROPERTY
(
    TASK_PROPERTY_ID,
    TASK_ID,
    PROPERTY_TYPE_CD,
    POSITION,
    VAR_TYPE,
    VAR_NAME,
    ADD_DATE,
    ADD_BY,
    MOD_DATE,
    MOD_BY,
    TASK_PROPERTY_STATUS_CD
)
SELECT 
    CLW_TASK_PROPERTY_SEQ.NEXTVAL,
    (
        SELECT TASK_ID FROM CLW_TASK 
        WHERE 
            TASK_NAME='FtpFileToDbTableLoader' AND 
            TASK_TYPE_CD='TEMPLATE' 
    ),
    'MANDATORY',
    6,
    'java.lang.String',
    'transfer_type',
    SYSDATE,
    'VLAD',
    SYSDATE,
    'VLAD',
    'ACTIVE'
FROM DUAL;

INSERT INTO CLW_TASK_PROPERTY
(
    TASK_PROPERTY_ID,
    TASK_ID,
    PROPERTY_TYPE_CD,
    POSITION,
    VAR_TYPE,
    VAR_NAME,
    ADD_DATE,
    ADD_BY,
    MOD_DATE,
    MOD_BY,
    TASK_PROPERTY_STATUS_CD
)
SELECT 
    CLW_TASK_PROPERTY_SEQ.NEXTVAL,
    (
        SELECT TASK_ID FROM CLW_TASK 
        WHERE 
            TASK_NAME='FtpFileToDbTableLoader' AND 
            TASK_TYPE_CD='TEMPLATE' 
    ),
    'MANDATORY',
    7,
    'java.lang.String',
    'removefile',
    SYSDATE,
    'VLAD',
    SYSDATE,
    'VLAD',
    'ACTIVE'
FROM DUAL;

INSERT INTO CLW_TASK_PROPERTY
(
    TASK_PROPERTY_ID,
    TASK_ID,
    PROPERTY_TYPE_CD,
    POSITION,
    VAR_TYPE,
    VAR_NAME,
    ADD_DATE,
    ADD_BY,
    MOD_DATE,
    MOD_BY,
    TASK_PROPERTY_STATUS_CD
)
SELECT 
    CLW_TASK_PROPERTY_SEQ.NEXTVAL,
    (
        SELECT TASK_ID FROM CLW_TASK 
        WHERE 
            TASK_NAME='FtpFileToDbTableLoader' AND 
            TASK_TYPE_CD='TEMPLATE' 
    ),
    'MANDATORY',
    8,
    'java.lang.String',
    'pattern',
    SYSDATE,
    'VLAD',
    SYSDATE,
    'VLAD',
    'ACTIVE'
FROM DUAL;

INSERT INTO CLW_TASK_PROPERTY
(
    TASK_PROPERTY_ID,
    TASK_ID,
    PROPERTY_TYPE_CD,
    POSITION,
    VAR_TYPE,
    VAR_NAME,
    ADD_DATE,
    ADD_BY,
    MOD_DATE,
    MOD_BY,
    TASK_PROPERTY_STATUS_CD
)
SELECT 
    CLW_TASK_PROPERTY_SEQ.NEXTVAL,
    (
        SELECT TASK_ID FROM CLW_TASK 
        WHERE 
            TASK_NAME='FtpFileToDbTableLoader' AND 
            TASK_TYPE_CD='TEMPLATE' 
    ),
    'MANDATORY',
    9,
    'java.lang.String',
    'table',
    SYSDATE,
    'VLAD',
    SYSDATE,
    'VLAD',
    'ACTIVE'
FROM DUAL;

INSERT INTO CLW_TASK_PROPERTY
(
    TASK_PROPERTY_ID,
    TASK_ID,
    PROPERTY_TYPE_CD,
    POSITION,
    VAR_TYPE,
    VAR_NAME,
    ADD_DATE,
    ADD_BY,
    MOD_DATE,
    MOD_BY,
    TASK_PROPERTY_STATUS_CD
)
SELECT 
    CLW_TASK_PROPERTY_SEQ.NEXTVAL,
    (
        SELECT TASK_ID FROM CLW_TASK 
        WHERE 
            TASK_NAME='FtpFileToDbTableLoader' AND 
            TASK_TYPE_CD='TEMPLATE' 
    ),
    'OPTIONAL',
    10,
    'java.lang.String',
    'database_schema',
    SYSDATE,
    'VLAD',
    SYSDATE,
    'VLAD',
    'ACTIVE'
FROM DUAL;

INSERT INTO CLW_TASK_PROPERTY
(
    TASK_PROPERTY_ID,
    TASK_ID,
    PROPERTY_TYPE_CD,
    POSITION,
    VAR_TYPE,
    VAR_NAME,
    ADD_DATE,
    ADD_BY,
    MOD_DATE,
    MOD_BY,
    TASK_PROPERTY_STATUS_CD
)
SELECT 
    CLW_TASK_PROPERTY_SEQ.NEXTVAL,
    (
        SELECT TASK_ID FROM CLW_TASK 
        WHERE 
            TASK_NAME='FtpFileToDbTableLoader' AND 
            TASK_TYPE_CD='TEMPLATE' 
    ),
    'OPTIONAL',
    11,
    'java.lang.String',
    'field_qty',
    SYSDATE,
    'VLAD',
    SYSDATE,
    'VLAD',
    'ACTIVE'
FROM DUAL;

INSERT INTO CLW_TASK_PROPERTY
(
    TASK_PROPERTY_ID,
    TASK_ID,
    PROPERTY_TYPE_CD,
    POSITION,
    VAR_TYPE,
    VAR_NAME,
    ADD_DATE,
    ADD_BY,
    MOD_DATE,
    MOD_BY,
    TASK_PROPERTY_STATUS_CD
)
SELECT 
    CLW_TASK_PROPERTY_SEQ.NEXTVAL,
    (
        SELECT TASK_ID FROM CLW_TASK 
        WHERE 
            TASK_NAME='FtpFileToDbTableLoader' AND 
            TASK_TYPE_CD='TEMPLATE' 
    ),
    'MANDATORY',
    12,
    'java.lang.String',
    'field_separator',
    SYSDATE,
    'VLAD',
    SYSDATE,
    'VLAD',
    'ACTIVE'
FROM DUAL;

INSERT INTO CLW_TASK_PROPERTY
(
    TASK_PROPERTY_ID,
    TASK_ID,
    PROPERTY_TYPE_CD,
    POSITION,
    VAR_TYPE,
    VAR_NAME,
    ADD_DATE,
    ADD_BY,
    MOD_DATE,
    MOD_BY,
    TASK_PROPERTY_STATUS_CD
)
SELECT 
    CLW_TASK_PROPERTY_SEQ.NEXTVAL,
    (
        SELECT TASK_ID FROM CLW_TASK 
        WHERE 
            TASK_NAME='FtpFileToDbTableLoader' AND 
            TASK_TYPE_CD='TEMPLATE' 
    ),
    'MANDATORY',
    13,
    'java.lang.String',
    'process_id',
    SYSDATE,
    'VLAD',
    SYSDATE,
    'VLAD',
    'ACTIVE'
FROM DUAL;

INSERT INTO CLW_TASK_PROPERTY
(
    TASK_PROPERTY_ID,
    TASK_ID,
    PROPERTY_TYPE_CD,
    POSITION,
    VAR_TYPE,
    VAR_NAME,
    ADD_DATE,
    ADD_BY,
    MOD_DATE,
    MOD_BY,
    TASK_PROPERTY_STATUS_CD
)
SELECT 
    CLW_TASK_PROPERTY_SEQ.NEXTVAL,
    (
        SELECT TASK_ID FROM CLW_TASK 
        WHERE 
            TASK_NAME='FtpFileToDbTableLoader' AND 
            TASK_TYPE_CD='TEMPLATE' 
    ),
    'MANDATORY',
    14,
    'java.lang.String',
    'date_pattern',
    SYSDATE,
    'VLAD',
    SYSDATE,
    'VLAD',
    'ACTIVE'
FROM DUAL;

INSERT INTO CLW_TASK_PROPERTY
(
    TASK_PROPERTY_ID,
    TASK_ID,
    PROPERTY_TYPE_CD,
    POSITION,
    VAR_TYPE,
    VAR_NAME,
    ADD_DATE,
    ADD_BY,
    MOD_DATE,
    MOD_BY,
    TASK_PROPERTY_STATUS_CD
)
SELECT 
    CLW_TASK_PROPERTY_SEQ.NEXTVAL,
    (
        SELECT TASK_ID FROM CLW_TASK 
        WHERE 
            TASK_NAME='FtpFileToDbTableLoader' AND 
            TASK_TYPE_CD='TEMPLATE' 
    ),
    'OPTIONAL',
    15,
    'java.lang.String',
    'storeId',
    SYSDATE,
    'VLAD',
    SYSDATE,
    'VLAD',
    'ACTIVE'
FROM DUAL;

INSERT INTO CLW_TASK_REF
( 
    TASK_REF_ID,
    TASK_ID1,
    TASK_ID2,
    PROCESS_ID,
    ADD_DATE, 
    ADD_BY, 
    MOD_DATE,
    MOD_BY
)
SELECT 
    CLW_TASK_REF_SEQ.NEXTVAL,
    NULL,
    (
        SELECT TASK_ID FROM CLW_TASK 
        WHERE 
            TASK_NAME = 'FtpFileToDbTableLoader' AND 
            TASK_TYPE_CD = 'TEMPLATE'
    ),
    (
        SELECT PROCESS_ID FROM CLW_PROCESS 
        WHERE 
            PROCESS_NAME='PROCESS_LOAD_TABLE_DATA_FROM_FTP_FILE' AND 
            PROCESS_TYPE_CD = 'TEMPLATE'
    ),
    SYSDATE, 
    'VLAD', 
    SYSDATE, 
    'VLAD'
FROM DUAL;

COMMIT;
