insert into clw_ref_cd
(
    ref_cd_id,
    ref_cd,
    short_desc,
    clw_value,
    ref_cd_type,
    ref_status_cd,
    ADD_DATE,
    ADD_BY,
    MOD_DATE,
    MOD_BY
)
values
(
    clw_ref_cd_seq.nextval,
    'APPLICATION_FUNCTIONS',
    'NEW_UI_ACCESS',
    'Access to New UI',
    'UNKNOWN',
    'ORDERED',
    sysdate,
    'RefCodeToDB',
    sysdate,
    'RefCodeToDB'
);

COMMIT;
