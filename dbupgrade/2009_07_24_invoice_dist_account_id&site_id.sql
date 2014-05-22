update clw_invoice_dist id set account_id = (select account_id from clw_order o where id.order_id = o.order_id)
where order_id is not null and (account_id is null or account_id=0);

update clw_invoice_dist id set site_id = (select site_id from clw_order o where id.order_id = o.order_id)
where order_id is not null and (site_id is null or site_id=0);
