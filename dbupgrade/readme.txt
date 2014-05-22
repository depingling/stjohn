Database alterations

05102002.sql, add the following columns

alter table CLW_ORDER_ITEM add(ACK_STATUS_CD VARCHAR2(30) null)
alter table CLW_INVOICE_CUST_DETAIL add(SHIP_STATUS_CD VARCHAR2(30) null)


04142002.sql

Add columns for CIT message processing.

alter table clw_invoice_cust add (PAYMENT_TERMS_CD CHAR(3) null,
	CIT_STATUS_CD VARCHAR2(30) null,
	CIT_ASSIGNMENT_NUMBER NUMBER(38,0) null,
	CIT_TRANSACTION_DATE DATE null)
