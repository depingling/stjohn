UPDATE CLW_COUNTRY SET ADDRESS_FORMAT='{name}\n{address1}\n{address2}\n{address3}\n{address4}\n{city} {state} {postalCode}\n{country}'
WHERE SHORT_DESC IN ('AUSTRALIA','CANADA')

UPDATE CLW_COUNTRY SET ADDRESS_FORMAT='{name}\n{address1}\n{address2}\n{address3}\n{address4}\n{city}-{state}\n{postalCode}\n{country}'
WHERE SHORT_DESC IN ('BRAZIL')

UPDATE CLW_COUNTRY SET ADDRESS_FORMAT='{name}\n{address1}\n{address2}\n{address3}\n{address4}\n{postalCode} {city}\n{country}'
WHERE SHORT_DESC IN ('CHILE','FRANCE','GERMANY','GREECE','FINLAND','SLOVENIA','SWEDEN','SWITZERLAND','SPAIN','PORTUGAL')

UPDATE CLW_COUNTRY SET ADDRESS_FORMAT='{name}\n{address1}\n{address2}\n{address3}\n{address4}\n{postalCode} {city} {state}\n{country}'
WHERE SHORT_DESC IN ('ITALY','TURKEY')

UPDATE CLW_COUNTRY SET ADDRESS_FORMAT='{name}\n{address1}\n{address2}\n{address3}\n{address4}\n{city}\n{state}\n{postalCode}\n{country}'
WHERE SHORT_DESC IN ('UNITED KINGDOM')

UPDATE CLW_COUNTRY SET ADDRESS_FORMAT='{name}\n{address1}\n{address2}\n{address3}\n{address4}\n{city},{state} {postalCode}\n{country}'
WHERE SHORT_DESC IN ('UNITED STATES','JAPAN')

UPDATE CLW_COUNTRY SET ADDRESS_FORMAT='{name}\n{address1}\n{address2}\n{address3}\n{address4}\n{postalCode}\n{country}'
WHERE SHORT_DESC IN ('HUNGARY')

UPDATE CLW_COUNTRY SET ADDRESS_FORMAT='{name}\n{address1}\n{address2}\n{address3}\n{address4}\n{city}\n{postalCode} {state}\n{country}'
WHERE SHORT_DESC IN ('CHINA') 

UPDATE CLW_COUNTRY SET ADDRESS_FORMAT='{name}\n{address1}\n{address2}\n{address3}\n{address4}\n{postalCode} {city},{state}\n{country}'
WHERE SHORT_DESC IN ('MEXICO') 