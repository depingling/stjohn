-- Replace existing 'Exclude Order From Budget - Requires Approval' with 'Exclude Order From Budget'
update clw_group_assoc set application_function = 'Exclude Order From Budget'
where group_assoc_id in (
select group_assoc_id from clw_group_assoc where application_function = 'Exclude Order From Budget - Requires Approval');

commit;

-- Replace existing 'NON_APPLICABLE_NOT_APPROVAL' with 'NON_APPLICABLE'
update clw_order set order_budget_type_cd = 'NON_APPLICABLE' where order_budget_type_cd = 'NON_APPLICABLE_NOT_APPROVAL';

commit;

-- Remove existing 'Exclude Order From Budget - Requires Approval' Apllication function from the database.
delete from clw_ref_cd where ref_cd='APPLICATION_FUNCTIONS' and short_desc='EXCLUDE_ORDER_FROM_BUDGET_REQUIRES_APPROVAL' 
and clw_value='Exclude Order From Budget - Requires Approval'

commit;
