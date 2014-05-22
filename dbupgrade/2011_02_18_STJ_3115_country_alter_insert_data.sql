-- Alter length of country_code column from 2 chars to 4 chars.
ALTER TABLE CLW_COUNTRY MODIFY (COUNTRY_CODE VARCHAR2(4 CHAR) );
COMMIT;

-- Insert data for pollock store's country.
INSERT INTO CLW_COUNTRY VALUES(CLW_COUNTRY_SEQ.NEXTVAL,'USA - POLLOCK','USA - Pollock','XXPO',SYSDATE,'sqlQuery',SYSDATE,'sqlQuery',null);
COMMIT;