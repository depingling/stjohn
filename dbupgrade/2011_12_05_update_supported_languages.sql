update clw_language
set supported='true', 
mod_by='sqlQuery', mod_date=sysdate
where language_code in
('de','el','en','es','fr','hu','it','ja','nl','pl','tr','zh');

commit;