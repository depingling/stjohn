select 
'1'                       as "Version Number", 
acc.bus_entity_status_cd  as "Status",
bus_store.bus_entity_id   as "Store Id",
bus_store.short_desc      as "Store name",
nvl(acc.erp_num,'#'||acc.bus_entity_id)  as "Account Ref Num",
acc.short_desc            as "Account Name",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'ACCOUNT TYPE') 
                  as "Account Type",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'BUDGET_ACCRUAL_TYPE_CD') 
                  as "Budget Type",
acc.time_zone_cd  as "Time Zone",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'ALLOW_SITE_LLC'), 'FALSE') 
                  as "Allow Site LLC Override",
(select em.email_address from clw_email em where em.bus_entity_id = acc.bus_entity_id and em.email_type_cd = 'CUSTOMER SERVICE')
                  as "Customer Service Email",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'CONTACT_US_CC_ADD') 
                  as "Contact Us CC Email",
(select em.email_address from clw_email em where em.bus_entity_id = acc.bus_entity_id and em.email_type_cd = 'DEFAULT')
                  as "Default Email",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'DIST_ACCT_REF_NUM') 
                  as "Distributor Account Ref Num",

prim_addr.name1      as "Primary Contact First Name", 
prim_addr.name2      as "Primary Contact Last Name", 

(select ph.phone_num from clw_phone ph where ph.bus_entity_id = acc.bus_entity_id and ph.primary_ind = 1 and ph.phone_type_cd = 'PHONE')                  
                     as "Primary Contact Phone", 
(select ph.phone_num from clw_phone ph where ph.bus_entity_id = acc.bus_entity_id and ph.phone_type_cd = 'FAX')                  
                     as "Primary Contact Fax",
(select em.email_address from clw_email em where em.bus_entity_id = acc.bus_entity_id and em.primary_ind = 1)
                     as "Primary Contact Email",
prim_addr.address1       as "Primary Contact Address 1", 
prim_addr.address2       as "Primary Contact Address 2", 
prim_addr.address3       as "Primary Contact Address 3", 
prim_addr.city           as "Primary Contact City", 
prim_addr.state_province_cd as "Primary Contact State", 
prim_addr.country_cd     as "Primary Contact Country", 
prim_addr.postal_code    as "Primary Contact Postal Code",
bill_addr.address1          as "Billing Street Address 1", 
bill_addr.address2          as "Billing Street Address 2", 
bill_addr.address3          as "Billing Street Address 3", 
bill_addr.city              as "Billing City", 
bill_addr.state_province_cd as "Billing State", 
bill_addr.country_cd        as "Billing Country", 
bill_addr.postal_code       as "Billing Postal Code",

(select p.clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'PURCHASE_ORDER_ACCOUNT_NAME') 
                  as "Purchase Order Account Name", 
(select ph.phone_num from clw_phone ph where ph.bus_entity_id = acc.bus_entity_id and ph.phone_type_cd = 'ORDERPHONE')                  
                  as "Order Contact Phone",
(select ph.phone_num from clw_phone ph where ph.bus_entity_id = acc.bus_entity_id and ph.phone_type_cd = 'ORDERFAX')                                    
                  as "Order Contact Fax",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'COMMENTS') 
                  as "Order Guide Comments",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'ORDER_GUIDE_NOTE') 
                  as "Order Guide Notes",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'SKU_TAG') 
                  as "Order Guide Sku Heading",
'System Accepted, Canceled, Dist Shipped, Cust Shipped,Dist Invoiced, ACK Accepted, ACK Backordered, 
ACK Accepted-Changes Made, Ack Deleted, ACK On Hold, ACK Accepted-Quantity Changed, ACK Rejected, 
ACK Accepted-Substitution, Backordered, Shipped, Scheduled, Returned' as order_item_actions,
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'TAXABLE_INDICATOR'), 'FALSE') 
                  as "Taxable",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'ALLOW_USER_CHANGE_PASSWORD'), 'FALSE') 
                   as "Allow User to Change Password",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'SHOW_SCHED_DELIVERY'), 'FALSE') 
                  as "Enable Inv order processing",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'ALLOW_CUSTOMER_PO_NUMBER'), 'FALSE') 
                  as "Allow customer to enter po num",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'AUTHORIZED_FOR_RESALE'), 'FALSE')
                  as "Auth. for Re-Selling Items",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'SHOW_DIST_SKU_NUM'), 'FALSE')
                  as "Show Distributor SKU #",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'SHOW_DIST_DELIVERY_DATE'), 'FALSE')
                  as "Show Dist. Delivery Date",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'ADJUST_QTY_BY_855'), 'FALSE')
                  as "Modify Order Qty & cost(855)",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'ALLOW_REORDER'), 'FALSE') 
                  as "Allow Reorder",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'CREATE_ORDER_BY_855'), 'FALSE')
                  as "Create OrderByOrder Ack(855)",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'ACCOUNT_FOLDER_NEW')                   
                  as "Modern Shopping Folder",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'SHOW_INV_CART_TOTAL'), 'FALSE')
                  as "Show inv items price total",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'SHOW_MY_SHOPPING_LISTS'), 'FALSE')
                  as "Display My Shopping Lists",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'SHOW_EXPRESS_ORDER'), 'FALSE')
                  as "Express Order",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'SCHEDULE_CUTOFF_DAYS') 
                  as "Delivery Cutoff Days",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'ACCOUNT_FOLDER') 
                  as "Account Folder",
nvl((select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'AUTO_ORDER_FACTOR'),'1')
                  as "Auto Order Qty Factor",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'ALLOW_ORDER_INV_ITEMS'), 'FALSE') 
                  as "Allow Order Inventory Items",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end)
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'USE_PHYSICAL_INVENTORY'), 'FALSE')
                  as "Use Physical Inventory",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'SHOP_UI_TYPE') 
                  as "Shop UI Type",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'FAQ_LINK') 
                  as "FAQ Link",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'PDF_ORDER_CLASS') 
                  as "Pdf Order Class Name",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'PDF_ORDER_STATUS_CLASS') 
                  as "Pdf Order Status Class Name",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'REMINDER_EMAIL_SUBJECT') 
                  as "Reminder Email Subject",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'REMINDER_EMAIL_MSG') 
                  as "Reminder Email Message",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'CONFIRM_ORDER_EMAIL_GENERATOR') 
                  as "Order Confirm Email Generator",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'NOTIFY_ORDER_EMAIL_GENERATOR') 
                  as "Order Notif Email Generator",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'REJECT_ORDER_EMAIL_GENERATOR') 
                  as "Order Rejected Email Generator",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'PENDING_APPROV_EMAIL_GENERATOR') 
                  as "Pending Appr Email Generator",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end)
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'INVENTORY_LEDGER_SWITCH'), 'FALSE') 
                  "Inv Prop Budget(Off)",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'INVENTORY_PO_SUFFIX') 
                  as "Inv Prop PO Suffix",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'INVENTORY_OG_LIST_UI') 
                  as "Inv Prop O.G. Inventory UI",
(select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'INVENTORY_MISSING_NOTIFICATION') 
                  as "Inv Prop Send notif",
nvl((select clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'INVENTORY_CHECK_PLACED_ORDER'), '1')
                  as "Inv Prop DoNot place inv.order",
nvl((select p.clw_value from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'DISTR_PO_TYPE' AND p.property_status_cd = 'ACTIVE'), 'SYSTEM')
                  as "Inv Prop Distributor PO Type",

fiscal_cal.fiscal_year as "Fiscal Calendar Year",
fiscal_cal.eff_date as "Fiscal Calendar Start Date",
(select fd.mmdd from clw_fiscal_calender_detail fd where fd.fiscal_calender_id=fiscal_cal.fiscal_calender_id and fd.period = 1)||','||
(select fd.mmdd from clw_fiscal_calender_detail fd where fd.fiscal_calender_id=fiscal_cal.fiscal_calender_id and fd.period = 2)||','||
(select fd.mmdd from clw_fiscal_calender_detail fd where fd.fiscal_calender_id=fiscal_cal.fiscal_calender_id and fd.period = 3)||','||
(select fd.mmdd from clw_fiscal_calender_detail fd where fd.fiscal_calender_id=fiscal_cal.fiscal_calender_id and fd.period = 4)||','||
(select fd.mmdd from clw_fiscal_calender_detail fd where fd.fiscal_calender_id=fiscal_cal.fiscal_calender_id and fd.period = 5)||','||
(select fd.mmdd from clw_fiscal_calender_detail fd where fd.fiscal_calender_id=fiscal_cal.fiscal_calender_id and fd.period = 6)||','||
(select fd.mmdd from clw_fiscal_calender_detail fd where fd.fiscal_calender_id=fiscal_cal.fiscal_calender_id and fd.period = 7)||','||
(select fd.mmdd from clw_fiscal_calender_detail fd where fd.fiscal_calender_id=fiscal_cal.fiscal_calender_id and fd.period = 8)||','||
(select fd.mmdd from clw_fiscal_calender_detail fd where fd.fiscal_calender_id=fiscal_cal.fiscal_calender_id and fd.period = 9)||','||
(select fd.mmdd from clw_fiscal_calender_detail fd where fd.fiscal_calender_id=fiscal_cal.fiscal_calender_id and fd.period = 10)||','||
(select fd.mmdd from clw_fiscal_calender_detail fd where fd.fiscal_calender_id=fiscal_cal.fiscal_calender_id and fd.period = 11)||','||
(select fd.mmdd from clw_fiscal_calender_detail fd where fd.fiscal_calender_id=fiscal_cal.fiscal_calender_id and fd.period = 12)||','||
(select fd.mmdd from clw_fiscal_calender_detail fd where fd.fiscal_calender_id=fiscal_cal.fiscal_calender_id and fd.period = 13)
                  as "Fiscal Calendar Periods",


nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'ALLOW_SET_WORKORDER_PO_NUMBER'), 'FALSE')
                  as "Allow Work Order PO Number",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'WORK_ORDER_PO_NUM_REQUIRED'), 'FALSE') 
                  as "Work Order PO Num is required",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'USER_ASSIGNED_ASSET_NUMBER'), 'FALSE') 
                  as "User assigned Asset Number",
nvl((select (case when(UPPER(clw_value) in ('Y', '1', 'YES', 'ON', 'TRUE')) then 'TRUE' else 'FALSE' end) 
        from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'ALLOW_BUY_WORK_ORDER_PARTS'), 'FALSE') 
                  as "Allow to Order Parts for WO",
(select upper(clw_value) from clw_property p where p.bus_entity_id = acc.bus_entity_id and p.short_desc = 'CONTACT_INFORMATION_TYPE') 
                  as "Populate Contact Information"


from
clw_bus_entity acc

join clw_bus_entity_assoc store_assoc
on store_assoc.bus_entity1_id = acc.bus_entity_id
and store_assoc.bus_entity2_id = #STORE#
and store_assoc.bus_entity_assoc_cd = 'ACCOUNT OF STORE'

join clw_bus_entity bus_store
on bus_store.bus_entity_id = store_assoc.bus_entity2_id
and bus_store.bus_entity_id = #STORE#

join clw_address prim_addr
on prim_addr.bus_entity_id = acc.bus_entity_id
and prim_addr.primary_ind = 1

join clw_address bill_addr
on bill_addr.bus_entity_id = acc.bus_entity_id
and bill_addr.address_type_cd = 'BILLING'


left join
(select ff.*
        from clw_fiscal_calender ff,
             (select max(eff_date) as maxdate from 
                     (select f.eff_date from clw_fiscal_calender f where f.eff_date < sysdate)) maxres
        where ff.eff_date = maxres.maxdate
) fiscal_cal
on fiscal_cal.bus_entity_id = acc.bus_entity_id



where acc.bus_entity_type_cd = 'ACCOUNT'
and bus_store.bus_entity_id = #STORE#

