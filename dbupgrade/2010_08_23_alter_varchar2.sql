SELECT 'ALTER table '||owner||'.'||table_name||' modify ('||wm_concat(column_name||' varchar2('||CHAR_LENGTH||' char)')||');'
 FROM all_tab_columns
WHERE table_name LIKE '%CLW%'
AND DATA_TYPE = 'VARCHAR2'
AND CHAR_USED = 'B'
AND owner LIKE  '%STJOHN%'
GROUP BY owner,table_name
;
