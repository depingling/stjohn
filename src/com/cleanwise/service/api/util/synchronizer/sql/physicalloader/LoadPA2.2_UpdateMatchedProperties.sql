CREATE TABLE tmp_2305
AS
SELECT matched_asset_id,
       long_description
       FROM   tmp_2205;

CREATE TABLE tmp_2306
AS
SELECT matched_asset_id,
       warranty_number
       FROM tmp_2205
       WHERE warranty_number IS NOT NULL;

CREATE TABLE tmp_2307
AS
SELECT matched_asset_id,
       acquisition_date
       FROM tmp_2205
       WHERE acquisition_date IS NOT NULL;

CREATE TABLE tmp_2308
AS
SELECT matched_asset_id,
       acquisition_price
       FROM tmp_2205
       WHERE acquisition_price IS NOT NULL;

CREATE TABLE tmp_2309
AS
SELECT matched_asset_id,
       last_hour_meter_reading
       FROM tmp_2205
       WHERE last_hour_meter_reading IS NOT NULL;

CREATE TABLE tmp_2310
AS
SELECT matched_asset_id,
       date_last_hour_meter_reading
       FROM tmp_2205
       WHERE date_last_hour_meter_reading IS NOT NULL;

CREATE TABLE tmp_2311
AS
SELECT matched_asset_id,
       date_in_service
       FROM tmp_2205
       WHERE date_in_service IS NOT NULL;

DELETE FROM clw_asset_warranty
       WHERE asset_id IN (SELECT matched_asset_id FROM tmp_2306);
       
DELETE FROM clw_asset_property
       WHERE asset_id IN (SELECT matched_asset_id FROM tmp_2305) AND asset_property_cd = 'LONG_DESC';

DELETE FROM clw_asset_property
       WHERE asset_id IN (SELECT matched_asset_id FROM tmp_2307) AND asset_property_cd = 'ACQUISITION_DATE';
      
DELETE FROM clw_asset_property
       WHERE asset_id IN (SELECT matched_asset_id FROM tmp_2308) AND asset_property_cd = 'ACQUISITION_COST';
      
DELETE FROM clw_asset_property
       WHERE asset_id IN (SELECT matched_asset_id FROM tmp_2309) AND asset_property_cd = 'LAST_HOUR_METER_READING';
      
DELETE FROM clw_asset_property
       WHERE asset_id IN (SELECT matched_asset_id FROM tmp_2310) AND asset_property_cd = 'DATE_LAST_HOUR_METER_READING';

DELETE FROM clw_asset_property
       WHERE asset_id IN (SELECT matched_asset_id FROM tmp_2311) AND asset_property_cd = 'DATE_IN_SERVICE';


INSERT ALL
       INTO clw_asset_property
         (
             asset_property_id,
             asset_id,
             asset_property_cd,
             clw_value,
             add_by,
             add_date,
             mod_by,
             mod_date
         )
VALUES
         (
             clw_asset_property_seq.NEXTVAL,
             matched_asset_id,
             'LONG_DESC',
             long_description,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE
         )
SELECT   matched_asset_id, long_description FROM tmp_2305;

INSERT ALL
       INTO clw_asset_property
         (
             asset_property_id,
             asset_id,
             asset_property_cd,
             clw_value,
             add_by,
             add_date,
             mod_by,
             mod_date
         )
VALUES
         (
             clw_asset_property_seq.NEXTVAL,
             matched_asset_id,
             'ACQUISITION_DATE',
             To_Char(acquisition_date,'mm/dd/yyyy'),
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE
         )
SELECT   matched_asset_id, acquisition_date FROM tmp_2307;

INSERT ALL
       INTO clw_asset_property
         (
             asset_property_id,
             asset_id,
             asset_property_cd,
             clw_value,
             add_by,
             add_date,
             mod_by,
             mod_date
         )
VALUES
         (
             clw_asset_property_seq.NEXTVAL,
             matched_asset_id,
             'ACQUISITION_COST',
             acquisition_price,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE
         )
SELECT   matched_asset_id, acquisition_price FROM tmp_2308; 

INSERT ALL
       INTO clw_asset_property
         (
             asset_property_id,
             asset_id,
             asset_property_cd,
             clw_value,
             add_by,
             add_date,
             mod_by,
             mod_date
         )
VALUES
         (
             clw_asset_property_seq.NEXTVAL,
             matched_asset_id,
             'LAST_HOUR_METER_READING',
             last_hour_meter_reading,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE
         )
SELECT   matched_asset_id, last_hour_meter_reading FROM tmp_2309;

INSERT ALL
       INTO clw_asset_property
         (
             asset_property_id,
             asset_id,
             asset_property_cd,
             clw_value,
             add_by,
             add_date,
             mod_by,
             mod_date
         )
VALUES
         (
             clw_asset_property_seq.NEXTVAL,
             matched_asset_id,
             'DATE_LAST_HOUR_METER_READING',
             To_Char(date_last_hour_meter_reading,'mm/dd/yyyy'),
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE
         )
SELECT   matched_asset_id, date_last_hour_meter_reading FROM tmp_2310;

INSERT ALL
       INTO clw_asset_property
         (
             asset_property_id,
             asset_id,
             asset_property_cd,
             clw_value,
             add_by,
             add_date,
             mod_by,
             mod_date
         )
VALUES
         (
             clw_asset_property_seq.NEXTVAL,
             matched_asset_id,
             'DATE_IN_SERVICE',
             To_Char(date_in_service,'mm/dd/yyyy'),
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE
         )
SELECT   matched_asset_id, date_in_service FROM tmp_2311;

INSERT ALL
       INTO clw_asset_warranty
         (
             asset_warranty_id,
             asset_id,
             warranty_id,
             add_date,
             add_by,
             mod_date,
             mod_by
         )
VALUES
         (
             clw_asset_warranty_seq.NEXTVAL,
             matched_asset_id,
             warranty_id,
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#'
         )
SELECT a.matched_asset_id,
       b.warranty_id 
       FROM tmp_2306 a,
            clw_warranty b
       WHERE a.warranty_number = b.warranty_number;
       
    DROP TABLE tmp_2305 purge;
    DROP TABLE tmp_2306 purge;
    DROP TABLE tmp_2307 purge;
    DROP TABLE tmp_2308 purge;
    DROP TABLE tmp_2309 purge;
    DROP TABLE tmp_2310 purge;
    DROP TABLE tmp_2311 purge;