select S.SHORT_DESC SITE_NAME, P.SHORT_DESC QUESTION, PD.CLW_VALUE ANSWER, PD.MOD_BY, PD.MOD_DATE 
from clw_profile p 
join clw_profile_DETAIL pD on pD.profile_id = p.profile_id
JOIN CLW_BUS_ENTITY S ON PD.BUS_ENTITY_ID = S.BUS_ENTITY_ID
 where p.profile_id in (
   select profile1_id from clw_profile_assoc where profile2_id in (
     select profile_id from clw_profile where profile_id in (
       select profile1_id from clw_profile_assoc where 
       bus_entity_id in 
         (select bus_entity_id from clw_user_assoc where user_id = #CUSTOMER#) 
       and profile2_id = 0
       )
     )
   )
 and
 pd.mod_date between TO_DATE('#BEG_DATE#','mm/dd/yyyy') and TO_DATE('#END_DATE#','mm/dd/yyyy')
order by S.SHORT_DESC, p.profile_order