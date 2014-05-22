select 
'1' as "Version Number",
distr.bus_entity_status_cd as "Status",
bus_store.bus_entity_id as "Store ID",
bus_store.short_desc as "Store Name",
distr.bus_entity_id as "Distributor ID",
distr.short_desc as "Short Description",
(select p.clw_value from clw_property p where p.bus_entity_id = distr.bus_entity_id and p.short_desc = 'RUNTIME_DISPLAY_NAME')
        as "Runtime Display Name",
(select p.clw_value from clw_property p where p.bus_entity_id = distr.bus_entity_id and p.short_desc = 'DistTypeLabel')        
        as "Distributor Type",
distr.erp_num as "Distributor Number",
(select p.clw_value from clw_property p where p.bus_entity_id = distr.bus_entity_id and p.short_desc = 'DISTRIBUTORS_COMPANY_CODE')        
        as "Distributor's Company Code",
(select p.clw_value from clw_property p where p.bus_entity_id = distr.bus_entity_id and p.short_desc = 'MINIMUM_ORDER_AMOUNT')
        as "Minimum Order",
addr.name1 as "Contact First Name",
addr.name2 as "Contact Last Name",
addr.address1 as "Contact Street Address 1",
addr.address2 as "Contact Street Address 2",
addr.address3 as "Contact Street Address 3",
addr.city as "Contact City",
addr.state_province_cd as "Contact State",
addr.postal_code as "Contact Postal Code",
addr.country_cd as "Contact Country",
ph.phone_num as "Contact Phone",
fax.phone_num as "Contact Fax",
email.email_address as "Contact Email",
bill_addr.address1 as "Billing  Street Address 1",
bill_addr.address2 as "Billing Street Address 2",
bill_addr.address3 as "Billing Street Address 3",
bill_addr.city as "Billing City",
bill_addr.state_province_cd as "Billing State",
bill_addr.postal_code as "Billing Postal Code",
bill_addr.country_cd as "Billing Country",
(select p.clw_value from clw_property p where p.bus_entity_id = distr.bus_entity_id and p.short_desc = 'DIST_WEB_INFO')
        as "Distributor Web Info",
(select p.clw_value from clw_property p where p.bus_entity_id = distr.bus_entity_id and p.short_desc = 'DIST_ACCOUNT_NUMBERS')
        as "Distributor CW Account Number"

from clw_bus_entity distr

join clw_bus_entity_assoc store_assoc
on store_assoc.bus_entity1_id = distr.bus_entity_id
and store_assoc.bus_entity_assoc_cd = 'DISTRIBUTOR OF STORE'
and store_assoc.bus_entity2_id = #STORE#

join clw_bus_entity bus_store
on bus_store.bus_entity_id = store_assoc.bus_entity2_id

left join clw_address addr
on addr.bus_entity_id = distr.bus_entity_id
and addr.primary_ind = 1
and addr.address_type_cd = 'PRIMARY CONTACT'

left join clw_address bill_addr
on bill_addr.bus_entity_id = distr.bus_entity_id
and bill_addr.address_type_cd = 'BILLING'

left join clw_phone ph
on ph.bus_entity_id = distr.bus_entity_id
and ph.primary_ind = 1
and ph.phone_type_cd = 'PHONE'

left join clw_phone fax
on fax.bus_entity_id = distr.bus_entity_id
and fax.primary_ind = 1
and fax.phone_type_cd = 'FAX'

left join clw_email email
on email.bus_entity_id = distr.bus_entity_id
and email.primary_ind = 1
and email.email_type_cd = 'PRIMARY'