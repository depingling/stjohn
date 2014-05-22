ALTER TABLE CLW_COUNTRY ADD input_day_month_format  VARCHAR2(12);

--Message_All_de (GERMANY)
update clw_country
set input_day_month_format='dd.MM'
where country_code='DE';

--Message_All_en_GB (UNITED KINGDOM)
update clw_country
set input_day_month_format='dd/MM'
where country_code='GB';

--Message_All_en (UNITED STATES)
update clw_country
set input_day_month_format='MM/dd'
where country_code='US';

--Message_All_el (GREECE)
update clw_country
set input_day_month_format='dd/MM'
where country_code='GR';

--Message_All_en_AU (AUSTRALIA)
update clw_country
set input_day_month_format='dd/MM'
where country_code='AU';

--Message_All_es_CL (CHILE)
update clw_country
set input_day_month_format='dd/MM'
where country_code='CL';

--Message_All_es (SPAIN)
update clw_country
set input_day_month_format='dd/MM'
where country_code='ES';

--Message_All_fr (FRANCE)
update clw_country
set input_day_month_format='dd/MM'
where country_code='FR';

--Message_All_fr_CH (SWITZERLAND)
update clw_country
set input_day_month_format='dd.MM'
where country_code='CH';

--Message_All_fr_CA (CANADA)
update clw_country
set input_day_month_format='dd/MM'
where country_code='CA';

--Message_All_hu (HUNGARY)
update clw_country
set input_day_month_format='MM-dd'
where country_code='HU';

--Message_All_it (ITALY)
update clw_country
set input_day_month_format='dd/MM'
where country_code='IT';

--Message_All_ja (JAPAN)
update clw_country
set input_day_month_format='MM/dd'
where country_code='JP';

--Message_All_nl (NETHERLANDS)
update clw_country
set input_day_month_format='dd/MM'
where country_code='NL';

--Message_All_pl (POLAND)
update clw_country
set input_day_month_format='dd.MM'
where country_code='PL';

--Message_All_pt_BR (BRAZIL)
update clw_country
set input_day_month_format='MM/dd'
where country_code='BR';

--Message_All_ru (RUSSIAN FEDERATION)
update clw_country
set input_day_month_format='dd.MM'
where country_code='RU';

--Message_All_sl (SLOVENIA)
update clw_country
set input_day_month_format='dd.MM'
where country_code='SI';

--Message_All_tr (TURKEY)
update clw_country
set input_day_month_format='dd/MM'
where country_code='TR';

--Message_All_zh (CHINA)
update clw_country
set input_day_month_format='MM-dd'
where country_code='CN';

commit;
