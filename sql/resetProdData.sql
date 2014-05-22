--update the fax number for everyone
UPDATE clw_phone SET phone_num = '@faxnumber@' WHERE phone_type_cd IN ('FAX','POFAX');

--update every email address
UPDATE clw_email SET email_address = '@emailaddress@';

UPDATE clw_order_schedule SET cc_email = '@emailaddress@';

--update all of the passwords to 'test'
UPDATE clw_user SET password = '098f6bcd4621d373cade4e832627b4f6';

--update the fax from valu to be the test fax user
UPDATE clw_property SET clw_value = 'FAXTEST' WHERE property_type_cd = 'kFromContactName' ;

--reactivate the admin user
UPDATE clw_user SET user_status_cd = 'ACTIVE',exp_date=SYSDATE + 360 WHERE user_name = 'admin' ;

--updates all the trading profiles to be in a test state
UPDATE clw_trading_profile SET test_indicator = 'T';