DECLARE 

CURSOR storeIdCursor IS
    select bus_entity_id from clw_bus_entity where bus_entity_type_cd='STORE' and bus_entity_status_cd='ACTIVE';

BEGIN 
    FOR cursorObj IN storeIdCursor
      LOOP
           
        INSERT INTO CLW_STORE_PROFILE(STORE_PROFILE_ID, STORE_ID, SHORT_DESC, DISPLAY, EDIT, OPTION_TYPE_CD, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
        VALUES
        (clw_store_profile_seq.nextval,cursorObj.bus_entity_id,
        'PROFILE_NAME','true','true','FIELD_OPTION',
        sysdate,'STORE_PROFILE_INIT',sysdate, 'STORE_PROFILE_INIT');       
        
        INSERT INTO CLW_STORE_PROFILE(STORE_PROFILE_ID, STORE_ID, SHORT_DESC, DISPLAY, EDIT, OPTION_TYPE_CD, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
        VALUES
        (clw_store_profile_seq.nextval,cursorObj.bus_entity_id,
        'LANGUAGE','true','true','FIELD_OPTION',
        sysdate,'STORE_PROFILE_INIT',sysdate, 'STORE_PROFILE_INIT');  
        
        INSERT INTO CLW_STORE_PROFILE(STORE_PROFILE_ID, STORE_ID, SHORT_DESC, DISPLAY, EDIT, OPTION_TYPE_CD, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
        VALUES
        (clw_store_profile_seq.nextval,cursorObj.bus_entity_id,
        'COUNTRY','true','true','FIELD_OPTION',
        sysdate,'STORE_PROFILE_INIT',sysdate, 'STORE_PROFILE_INIT');  
        
        INSERT INTO CLW_STORE_PROFILE(STORE_PROFILE_ID, STORE_ID, SHORT_DESC, DISPLAY, EDIT, OPTION_TYPE_CD, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
        VALUES
        (clw_store_profile_seq.nextval,cursorObj.bus_entity_id,
        'CONTACT_ADDRESS','true','true','FIELD_OPTION',
        sysdate,'STORE_PROFILE_INIT',sysdate, 'STORE_PROFILE_INIT');  
        
        INSERT INTO CLW_STORE_PROFILE(STORE_PROFILE_ID, STORE_ID, SHORT_DESC, DISPLAY, EDIT, OPTION_TYPE_CD, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
        VALUES
        (clw_store_profile_seq.nextval,cursorObj.bus_entity_id,
        'PHONE','true','true','FIELD_OPTION',
        sysdate,'STORE_PROFILE_INIT',sysdate, 'STORE_PROFILE_INIT');  
        
        INSERT INTO CLW_STORE_PROFILE(STORE_PROFILE_ID, STORE_ID, SHORT_DESC, DISPLAY, EDIT, OPTION_TYPE_CD, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
        VALUES
        (clw_store_profile_seq.nextval,cursorObj.bus_entity_id,
        'MOBILE','true','true','FIELD_OPTION',
        sysdate,'STORE_PROFILE_INIT',sysdate, 'STORE_PROFILE_INIT');  
        
        INSERT INTO CLW_STORE_PROFILE(STORE_PROFILE_ID, STORE_ID, SHORT_DESC, DISPLAY, EDIT, OPTION_TYPE_CD, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
        VALUES
        (clw_store_profile_seq.nextval,cursorObj.bus_entity_id,
        'FAX','true','true','FIELD_OPTION',
        sysdate,'STORE_PROFILE_INIT',sysdate, 'STORE_PROFILE_INIT');  
        
        INSERT INTO CLW_STORE_PROFILE(STORE_PROFILE_ID, STORE_ID, SHORT_DESC, DISPLAY, EDIT, OPTION_TYPE_CD, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
        VALUES
        (clw_store_profile_seq.nextval,cursorObj.bus_entity_id,
        'EMAIL','true','true','FIELD_OPTION',
        sysdate,'STORE_PROFILE_INIT',sysdate, 'STORE_PROFILE_INIT');  
        
        INSERT INTO CLW_STORE_PROFILE(STORE_PROFILE_ID, STORE_ID, SHORT_DESC, DISPLAY, EDIT, OPTION_TYPE_CD, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
        VALUES
        (clw_store_profile_seq.nextval,cursorObj.bus_entity_id,
        'CHANGE_PASSWORD','true',null,'FIELD_OPTION',
        sysdate,'STORE_PROFILE_INIT',sysdate, 'STORE_PROFILE_INIT'); 
        
        DECLARE
        languageId NUMBER;
 
 
        CURSOR cursorLang IS
        SELECT language_id FROM clw_language WHERE short_desc in('CHINESE','DUTCH','ENGLISH','FRENCH','GERMAN','GREEK','HUNGARIAN',
        'ITALIAN','JAPANESE','POLISH', 'SLOVENIAN', 'SPANISH/ CASTILIAN', 'TURKISH');
         
        BEGIN
          OPEN cursorLang;
          LOOP
            FETCH cursorLang into languageId;
            EXIT WHEN cursorLang%notfound;
           
            INSERT INTO CLW_STORE_PROFILE(STORE_PROFILE_ID, STORE_ID, SHORT_DESC, DISPLAY, EDIT, OPTION_TYPE_CD, ADD_DATE, ADD_BY, MOD_DATE, MOD_BY)
            VALUES
            (clw_store_profile_seq.nextval,cursorObj.bus_entity_id,
            languageId,'true',null,'LANGUAGE_OPTION',
            sysdate,'STORE_PROFILE_INIT',sysdate, 'STORE_PROFILE_INIT'); 
         
          END LOOP;
          CLOSE cursorLang;
        END;
        
      END LOOP;
      COMMIT;
END;