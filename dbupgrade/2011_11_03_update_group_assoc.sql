update clw_group_assoc set application_function = 'Exclude Order From Budget - Requires Approval'
where group_assoc_id in (
select group_assoc_id from clw_group_assoc where application_function = 'Place Order Not Affect Budget');

commit;