delete from clw_template_property where template_id in (select template_id from clw_template where bus_entity_id is null);

delete from clw_template where bus_entity_id is null;


--Order confirmation template
insert into clw_template 
columns(template_id, bus_entity_id, name, type, content, add_by, add_date, mod_by, mod_date)
values(CLW_TEMPLATE_SEQ.NEXTVAL, null, 'SYSTEM_ORDER_CONFIRMATION_TEMPLATE', 'EMAIL', 'This email is to inform you that an order for ${order.account.name} has been approved for fulfillment as placed.  There is nothing else you need to do with this order at this time:


<#if order.originalOrderNumber??>

 Original Order Number: ${order.originalOrderNumber}

</#if>
  Web Order Number      : ${order.orderNumber}
  Order Date            : ${order.originalOrderDate?date}
  PO Number             : ${order.purchaseOrderNumber!"N/A"}
  Location              : ${order.site.name}
  Address1              : ${order.shippingAddress.street1}
  City                  : ${order.shippingAddress.city}
  State                 : ${order.shippingAddress.state!""}
  Postal Code           : ${order.shippingAddress.postalCode}
  Contact Name (USER)   : ${order.contactName}
  Order Total           : ${order.formattedTotalCost}
  Shipping Comments     : ${order.comments!""}', 'JEsielionis', sysdate, 'JEsielionis', sysdate);

insert into clw_template_property
columns(template_property_id, template_id, template_property_cd, clw_value, add_by, add_date, mod_by, mod_date)
values(CLW_TEMPLATE_PROPERTY_SEQ.NEXTVAL, (select template_id from clw_template where name = 'SYSTEM_ORDER_CONFIRMATION_TEMPLATE'), 'SUBJECT', ' -- Order Approved.  Location: ${order.site.name}, Order Number: ${order.orderNumber} --', 'JEsielionis', sysdate, 'JEsielionis', sysdate);

insert into clw_template_property
columns(template_property_id, template_id, template_property_cd, clw_value, add_by, add_date, mod_by, mod_date)
values(CLW_TEMPLATE_PROPERTY_SEQ.NEXTVAL, (select template_id from clw_template where name = 'SYSTEM_ORDER_CONFIRMATION_TEMPLATE'), 'LOCALE', 'Default', 'JEsielionis', sysdate, 'JEsielionis', sysdate);


--Shipping notification template
--this template is inserted a little differently than the others because its
--content is greater than 4000 characters
insert into clw_template
columns(template_id, bus_entity_id, name, type, content, add_by, add_date, mod_by, mod_date)
values(CLW_TEMPLATE_SEQ.NEXTVAL, null, 'SYSTEM_SHIPPING_NOTIFICATION_TEMPLATE', 'EMAIL', empty_clob(), 'JEsielionis', sysdate, 'JEsielionis', sysdate);

update clw_template set content = '<#assign msgWidth=80>
<#assign skuWidth=9>
<#assign nameWidth=22>
<#assign lineNumberWidth=7>
<#assign uomWidth=5>
<#assign qtyWidth=5>
<#assign trackingNumberWidth=20>
<#macro center text width=msgWidth>
<#local leftBufferSize=((width-text?length)/2)?floor>
<#local rightBufferSize=(width-text?length-leftBufferSize)?ceiling>
${""?left_pad(leftBufferSize)}${text}${""?right_pad(rightBufferSize)}</#macro>
<#macro centerWithNewLine text width=msgWidth>
<#local leftBufferSize=((width-text?length)/2)?floor>
<#local rightBufferSize=(width-text?length-leftBufferSize)?ceiling>
${""?left_pad(leftBufferSize)}${text}${""?right_pad(rightBufferSize)}
</#macro>
<#macro left text width=msgWidth>
<#local bufferSize=width-text?length>
${text}${""?right_pad(bufferSize)}</#macro>
<#macro leftWithNewLine text width=msgWidth>
<#local bufferSize=width-text?length>
${text}${""?right_pad(bufferSize)}
</#macro>
<#macro right text width=msgWidth>
<#local bufferSize=width-text?length>
${""?left_pad(bufferSize)}${text}</#macro>
<#macro rightWithNewLine text width=msgWidth>
<#local bufferSize=width-text?length>
${""?left_pad(bufferSize)}${text}
</#macro>
<#macro dividingLine divider width=msgWidth>
${""?left_pad(width,divider)}
</#macro>
<#macro determineNameChunk>
<#assign itemName=itemName?trim>
<#if (itemName?length < nameWidth)>
  <#assign nameChunk=itemName>
  <#assign itemName="">
<#else>
  <#assign words = itemName?word_list>
  <#assign nameChunk="">
  <#list words as word>
    <#if nameChunk="">
      <#assign candidate=word>
    <#else>
      <#assign candidate = " " + word>
    </#if>
    <#if ((nameChunk + candidate)?length < nameWidth)>
      <#assign nameChunk = nameChunk + candidate>
      <#assign itemName=itemName?substring(itemName?index_of(word) + word?length)>
    <#else>
      <#return>
    </#if>
  </#list>  
</#if>
</#macro>
<#macro outputRemainingItemName>
<#if (itemName?length > 0)>
  <@determineNameChunk/>
  <@left text="" width=skuWidth/><@leftWithNewLine text=nameChunk width=nameWidth/>
  <@outputRemainingItemName/>
</#if>
</#macro>


<@centerWithNewLine text="Order Shipment Notification"/>
<@centerWithNewLine text="Order: ${order.orderNumber} PO: ${order.purchaseOrderNumber}"/>
' where name = 'SYSTEM_SHIPPING_NOTIFICATION_TEMPLATE' and bus_entity_id is null; 

update clw_template set content = concat((select content from clw_template where name = 'SYSTEM_SHIPPING_NOTIFICATION_TEMPLATE' and bus_entity_id is null),
'<#assign thirdLine="Shipped on: ${order.shipDate?date}">
<#if order.distributor.name??>
  <#if (order.distributor.name?length > 0)>
    <#assign thirdLine=thirdLine + " Distributor: ${order.distributor.name}">
  </#if>
</#if>
<#if order.trackingNumber??>
  <#if (order.trackingNumber?length > 0)>
  <#assign thirdLine=thirdLine + " Tracking Number: ${order.trackingNumber}">
  </#if>
</#if>
<@centerWithNewLine text=thirdLine/>


<@dividingLine divider="_"/>

<@left text="Sku" width=skuWidth/><@center text="Product Name" width=nameWidth/><@center text="PO Line" width=lineNumberWidth/><@center text="Uom" width=uomWidth/><@center text="Qty" width=qtyWidth/><@right text="Tracking Number" width=trackingNumberWidth/>    

<@dividingLine divider="_"/>

<#list order.items as item>
<#assign itemName=item.name>
<@determineNameChunk/>
<@left text="${item.sku}" width=skuWidth/><@left text="${nameChunk}" width=nameWidth/><@center text="${item.lineNumber}" width=lineNumberWidth/><@center text="${item.unitOfMeasure}" width=uomWidth/><@center text="${item.quantity}" width=qtyWidth/><@rightWithNewLine text="${item.trackingNumbers!''''}" width=trackingNumberWidth/>
<@outputRemainingItemName/>

</#list>
<@dividingLine divider="_"/>

<@left text="Ship To Information" width=(msgWidth/2)/><@leftWithNewLine text="Ship From Information" width=(msgWidth/2)/>

<@left text="${order.account.erpNumber}" width=(msgWidth/2)/><@leftWithNewLine text=" " width=(msgWidth/2)/>
<@left text="${order.shippingAddress.street1}" width=(msgWidth/2)/><@leftWithNewLine text=" " width=(msgWidth/2)/>
<@left text="${order.shippingAddress.city}" width=(msgWidth/2)/><@leftWithNewLine text="${order.shipFromAddress.city}" width=(msgWidth/2)/>
<@left text="${order.shippingAddress.state!''''}" width=(msgWidth/2)/><@leftWithNewLine text="${order.shipFromAddress.state!''''}" width=(msgWidth/2)/>
<@left text="${order.shippingAddress.postalCode}" width=(msgWidth/2)/><@leftWithNewLine text="${order.shipFromAddress.postalCode}" width=(msgWidth/2)/>
<@left text="${order.shippingAddress.country}" width=(msgWidth/2)/><@leftWithNewLine text=" " width=(msgWidth/2)/>')
where name = 'SYSTEM_SHIPPING_NOTIFICATION_TEMPLATE' and bus_entity_id is null;

insert into clw_template_property
columns(template_property_id, template_id, template_property_cd, clw_value, add_by, add_date, mod_by, mod_date)
values(CLW_TEMPLATE_PROPERTY_SEQ.NEXTVAL, (select template_id from clw_template where name = 'SYSTEM_SHIPPING_NOTIFICATION_TEMPLATE'), 'SUBJECT', 'Order Shipment Notification. PO Number: ${order.purchaseOrderNumber}', 'JEsielionis', sysdate, 'JEsielionis', sysdate);

insert into clw_template_property
columns(template_property_id, template_id, template_property_cd, clw_value, add_by, add_date, mod_by, mod_date)
values(CLW_TEMPLATE_PROPERTY_SEQ.NEXTVAL, (select template_id from clw_template where name = 'SYSTEM_SHIPPING_NOTIFICATION_TEMPLATE'), 'LOCALE', 'Default', 'JEsielionis', sysdate, 'JEsielionis', sysdate);

--Pending approval template
insert into clw_template 
columns(template_id, bus_entity_id, name, type, content, add_by, add_date, mod_by, mod_date)
values(CLW_TEMPLATE_SEQ.NEXTVAL, null, 'SYSTEM_PENDING_APPROVAL_TEMPLATE', 'EMAIL', 'This email is to inform you that you have an order for ${order.account.name} that requires your approval.


<#if order.originalOrderNumber??>

 Original Order Number: ${order.originalOrderNumber}

</#if>
  Web Order Number      : ${order.orderNumber}
  Order Date            : ${order.originalOrderDate?date}
  PO Number             : ${order.purchaseOrderNumber!"N/A"}
  Location              : ${order.site.name}
  Address1              : ${order.shippingAddress.street1}
  City                  : ${order.shippingAddress.city}
  State                 : ${order.shippingAddress.state!""}
  Postal Code           : ${order.shippingAddress.postalCode}
  Contact Name (USER)   : ${order.contactName}
  Order Total           : ${order.formattedTotalCost}
  Shipping Comments     : ${order.comments!""}
', 'JEsielionis', sysdate, 'JEsielionis', sysdate);

insert into clw_template_property
columns(template_property_id, template_id, template_property_cd, clw_value, add_by, add_date, mod_by, mod_date)
values(CLW_TEMPLATE_PROPERTY_SEQ.NEXTVAL, (select template_id from clw_template where name = 'SYSTEM_PENDING_APPROVAL_TEMPLATE'), 'SUBJECT', ' -- Order approval notification. Location: ${order.site.name}, Order Number: ${order.orderNumber} (${order.ruleDate?datetime}) -- ', 'JEsielionis', sysdate, 'JEsielionis', sysdate);

insert into clw_template_property
columns(template_property_id, template_id, template_property_cd, clw_value, add_by, add_date, mod_by, mod_date)
values(CLW_TEMPLATE_PROPERTY_SEQ.NEXTVAL, (select template_id from clw_template where name = 'SYSTEM_PENDING_APPROVAL_TEMPLATE'), 'LOCALE', 'Default', 'JEsielionis', sysdate, 'JEsielionis', sysdate);

--Rejected order template
insert into clw_template 
columns(template_id, bus_entity_id, name, type, content, add_by, add_date, mod_by, mod_date)
values(CLW_TEMPLATE_SEQ.NEXTVAL, null, 'SYSTEM_REJECTED_ORDER_TEMPLATE', 'EMAIL', 'This email is to inform you that the following order has been cancelled.

If you should have any questions as to why the order was cancelled, we suggest you contact your designated ''Order Approver'' for further information.


<#if order.originalOrderNumber??>

 Original Order Number: ${order.originalOrderNumber}

</#if>
  Web Order Number      : ${order.orderNumber}
  Order Date            : ${order.originalOrderDate?date}
  PO Number             : ${order.purchaseOrderNumber!"N/A"}
  Location              : ${order.site.name}
  Address1              : ${order.shippingAddress.street1}
  City                  : ${order.shippingAddress.city}
  State                 : ${order.shippingAddress.state!""}
  Postal Code           : ${order.shippingAddress.postalCode}
  Contact Name (USER)   : ${order.contactName}
  Order Total           : ${order.formattedTotalCost}
  Shipping Comments     : ${order.comments!""}', 'JEsielionis', sysdate, 'JEsielionis', sysdate);

insert into clw_template_property
columns(template_property_id, template_id, template_property_cd, clw_value, add_by, add_date, mod_by, mod_date)
values(CLW_TEMPLATE_PROPERTY_SEQ.NEXTVAL, (select template_id from clw_template where name = 'SYSTEM_REJECTED_ORDER_TEMPLATE'), 'SUBJECT', ' -- Order Cancelled.  Location: ${order.site.name}, Order Number: ${order.orderNumber} --', 'JEsielionis', sysdate, 'JEsielionis', sysdate);

insert into clw_template_property
columns(template_property_id, template_id, template_property_cd, clw_value, add_by, add_date, mod_by, mod_date)
values(CLW_TEMPLATE_PROPERTY_SEQ.NEXTVAL, (select template_id from clw_template where name = 'SYSTEM_REJECTED_ORDER_TEMPLATE'), 'LOCALE', 'Default', 'JEsielionis', sysdate, 'JEsielionis', sysdate);

--Modified order template
insert into clw_template 
columns(template_id, bus_entity_id, name, type, content, add_by, add_date, mod_by, mod_date)
values(CLW_TEMPLATE_SEQ.NEXTVAL, null, 'SYSTEM_MODIFIED_ORDER_TEMPLATE', 'EMAIL', 'This email is to inform you that an order for ${order.account.name} has been approved for fulfillment with some modification.


<#if order.originalOrderNumber??>

 Original Order Number: ${order.originalOrderNumber}

</#if>
  Web Order Number      : ${order.orderNumber}
  Order Date            : ${order.originalOrderDate?date}
  PO Number             : ${order.purchaseOrderNumber!"N/A"}
  Location              : ${order.site.name}
  Address1              : ${order.shippingAddress.street1}
  City                  : ${order.shippingAddress.city}
  State                 : ${order.shippingAddress.state!""}
  Postal Code           : ${order.shippingAddress.postalCode}
  Contact Name (USER)   : ${order.contactName}
  Order Total           : ${order.formattedTotalCost}
  Shipping Comments     : ${order.comments!""}', 'JEsielionis', sysdate, 'JEsielionis', sysdate);

insert into clw_template_property
columns(template_property_id, template_id, template_property_cd, clw_value, add_by, add_date, mod_by, mod_date)
values(CLW_TEMPLATE_PROPERTY_SEQ.NEXTVAL, (select template_id from clw_template where name = 'SYSTEM_MODIFIED_ORDER_TEMPLATE'), 'SUBJECT', '-- Order Modified.  Location: ${order.site.name}, Order Number: ${order.orderNumber} --', 'JEsielionis', sysdate, 'JEsielionis', sysdate);

insert into clw_template_property
columns(template_property_id, template_id, template_property_cd, clw_value, add_by, add_date, mod_by, mod_date)
values(CLW_TEMPLATE_PROPERTY_SEQ.NEXTVAL, (select template_id from clw_template where name = 'SYSTEM_MODIFIED_ORDER_TEMPLATE'), 'LOCALE', 'Default', 'JEsielionis', sysdate, 'JEsielionis', sysdate);

commit;