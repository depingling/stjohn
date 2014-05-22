CREATE TABLE tmp_2005
AS
SELECT   *
         FROM   tmp_2105
         WHERE   matched_asset_id IS NULL;
    
    DROP TABLE tmp_2105 purge;