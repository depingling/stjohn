insert into clw_country_property (
  COUNTRY_PROPERTY_ID  ,
  COUNTRY_ID           ,
  COUNTRY_PROPERTY_CD  ,
  CLW_VALUE            ,
  ADD_DATE             ,
  ADD_BY               ,
  MOD_DATE             ,
  MOD_BY               
) select 
  clw_country_property_seq.nextval,
  c.country_id,
 'USES_STATE','true',sysdate,'yuriyadm', sysdate,'yuriyadm'
  from clw_country c
  where not exists ( select 1 from clw_country_property where country_id = c.country_id )
  and COUNTRY_CODE = 'IN'
  
