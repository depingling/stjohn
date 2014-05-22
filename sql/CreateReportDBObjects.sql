-- select 'Create synonyms' from dual;

create synonym CRS_COST_CENTER  for @stjohnUser@.CLW_COST_CENTER;

create synonym CRS_SITE_LEDGER  for @stjohnUser@.CLW_SITE_LEDGER;

-- select 'CRS_SITE_ACCOUNT' from dual;
create synonym CRS_SITE_ACCOUNT  for @stjohnUser@.CLV_SITE_ACCOUNT;

-- select 'CRS_ADDRESS' from dual;
create synonym CRS_ADDRESS  for @stjohnUser@.CLW_ADDRESS;

-- select 'CRS_BUS_ENTITY' from dual;
create synonym CRS_BUS_ENTITY  for @stjohnUser@.CLW_BUS_ENTITY;

-- select 'CRS_BUS_ENTITY_ASSOC' from dual;
create synonym CRS_BUS_ENTITY_ASSOC  for @stjohnUser@.CLW_BUS_ENTITY_ASSOC;

-- select 'CRS_CATALOG' from dual;
create synonym CRS_CATALOG  for @stjohnUser@.CLW_CATALOG;

-- select 'CRS_CATALOG_ASSOC' from dual;
create synonym CRS_CATALOG_ASSOC  for @stjohnUser@.CLW_CATALOG_ASSOC;

-- select 'CRS_CATALOG_STRUCTURE' from dual;
create synonym CRS_CATALOG_STRUCTURE  for @stjohnUser@.CLW_CATALOG_STRUCTURE;

-- select 'CRS_CONTRACT' from dual;
create synonym CRS_CONTRACT  for @stjohnUser@.CLW_CONTRACT;

-- select 'CRS_CONTRACT_ITEM' from dual;
create synonym CRS_CONTRACT_ITEM  for @stjohnUser@.CLW_CONTRACT_ITEM;

-- select 'CRS_CONTRACT_ITEM_SUBST' from dual;
create synonym CRS_CONTRACT_ITEM_SUBST  for @stjohnUser@.CLW_CONTRACT_ITEM_SUBST;

-- select 'CRS_INVOICE_CUST' from dual;
create synonym CRS_INVOICE_CUST  for @stjohnUser@.CLW_INVOICE_CUST;

-- select 'CRS_INVOICE_CUST_DETAIL' from dual;
create synonym CRS_INVOICE_CUST_DETAIL  for @stjohnUser@.CLW_INVOICE_CUST_DETAIL;

-- select 'CRS_INVOICE_DIST' from dual;
create synonym CRS_INVOICE_DIST  for @stjohnUser@.CLW_INVOICE_DIST;

-- select 'CRS_INVOICE_DIST_DETAIL' from dual;
create synonym CRS_INVOICE_DIST_DETAIL  for @stjohnUser@.CLW_INVOICE_DIST_DETAIL;

-- select 'CRS_ITEM' from dual;
create synonym CRS_ITEM  for @stjohnUser@.CLW_ITEM;

-- select 'CRS_ITEM_ASSOC' from dual;
create synonym CRS_ITEM_ASSOC  for @stjohnUser@.CLW_ITEM_ASSOC;

-- select 'CRS_ITEM_KEYWORD' from dual;
create synonym CRS_ITEM_KEYWORD  for @stjohnUser@.CLW_ITEM_KEYWORD;

-- select 'CRS_ITEM_LOG' from dual;
create synonym CRS_ITEM_LOG  for @stjohnUser@.CLW_ITEM_LOG;

-- select 'CRS_ITEM_MAPPING' from dual;
create synonym CRS_ITEM_MAPPING  for @stjohnUser@.CLW_ITEM_MAPPING;

-- select 'CRS_ITEM_META' from dual;
create synonym CRS_ITEM_META  for @stjohnUser@.CLW_ITEM_META;

-- select 'CRS_ITEM_SUBSTITUTION' from dual;
create synonym CRS_ITEM_SUBSTITUTION  for @stjohnUser@.CLW_ITEM_SUBSTITUTION;

-- select 'CRS_ORDER' from dual;
create synonym CRS_ORDER  for @stjohnUser@.CLW_ORDER;

-- select 'CRS_ORDER_ADDRESS' from dual;
create synonym CRS_ORDER_ADDRESS  for @stjohnUser@.CLW_ORDER_ADDRESS;

-- select 'CRS_ORDER_ASSOC' from dual;
create synonym CRS_ORDER_ASSOC  for @stjohnUser@.CLW_ORDER_ASSOC;

-- select 'CRS_ORDER_ITEM' from dual;
create synonym CRS_ORDER_ITEM  for @stjohnUser@.CLW_ORDER_ITEM;

-- select 'CRS_ORDER_ITEM_ACTION' from dual;
create synonym CRS_ORDER_ITEM_ACTION  for @stjohnUser@.CLW_ORDER_ITEM_ACTION;

-- select 'CRS_ORDER_ITEM_META' from dual;
create synonym CRS_ORDER_ITEM_META  for @stjohnUser@.CLW_ORDER_ITEM_META;

-- select 'CRS_ORDER_META' from dual;
create synonym CRS_ORDER_META  for @stjohnUser@.CLW_ORDER_META;

-- select 'CRS_ORDER_PROCESSING' from dual;
create synonym CRS_ORDER_PROCESSING  for @stjohnUser@.CLW_ORDER_PROCESSING;

-- select 'CRS_PROPERTY' from dual;
create synonym CRS_PROPERTY  for @stjohnUser@.CLW_PROPERTY;

-- select 'CRS_REF_CD' from dual;
create synonym CRS_REF_CD  for @stjohnUser@.CLW_REF_CD;

-- select 'CRS_USER' from dual;
create synonym CRS_USER  for @stjohnUser@.CLW_USER;

-- select 'CRS_USER_ASSOC' from dual;
create synonym CRS_USER_ASSOC  for @stjohnUser@.CLW_USER_ASSOC;

-- select 'CRS_APINVOICE' from dual;
create synonym CRS_APINVOICE  for @stjohnUser@.LAW_APINVOICE;

-- select 'CRS_APVENMAST' from dual;
create synonym CRS_APVENMAST  for @stjohnUser@.LAW_APVENMAST;

-- select 'CRS_COLINE' from dual;
create synonym CRS_COLINE  for @stjohnUser@.LAW_COLINE;

-- select 'CRS_CUSTDESC' from dual;
create synonym CRS_CUSTDESC  for @stjohnUser@.LAW_CUSTDESC;

-- select 'CRS_CUSTORDER' from dual;
create synonym CRS_CUSTORDER  for @stjohnUser@.LAW_CUSTORDER;

-- select 'CRS_OEINVCLINE' from dual;
create synonym CRS_OEINVCLINE  for @stjohnUser@.LAW_OEINVCLINE;

-- select 'CRS_OEINVOICE' from dual;
create synonym CRS_OEINVOICE  for @stjohnUser@.LAW_OEINVOICE;

-- select 'CRS_POAPPROVE' from dual;
create synonym CRS_POAPPROVE  for @stjohnUser@.LAW_POAPPROVE;

-- select 'CRS_POITEMVEN' from dual;
create synonym CRS_POITEMVEN  for @stjohnUser@.LAW_POITEMVEN;

-- select 'CRS_POLINE' from dual;
create synonym CRS_POLINE  for @stjohnUser@.LAW_POLINE;

-- select 'CRS_PURCHORDER' from dual;
create synonym CRS_PURCHORDER  for @stjohnUser@.LAW_PURCHORDER;

-- select 'CRS_SHIPTO' from dual;
create synonym CRS_SHIPTO  for @stjohnUser@.LAW_SHIPTO;

-- select 'CRS_INVOICE_CUST_REENG' from dual;
create synonym CRS_INVOICE_CUST_REENG  for @stjohnUser@.CLW_INVOICE_CUST_REENG;

--select 'CRS_TRADING_PARTNER_ASSOC' from dual;
create synonym CRS_TRADING_PARTNER_ASSOC  for @stjohnUser@.CLW_TRADING_PARTNER_ASSOC;

--select 'CRS_TRADING_PARTNER' from dual;
create synonym CRS_TRADING_PARTNER  for @stjohnUser@.CLW_TRADING_PARTNER;

--select 'CRS_TRADING_PROFILE' from dual;
create synonym CRS_TRADING_PROFILE  for @stjohnUser@.CLW_TRADING_PROFILE;

--select 'CRS_INTERCHANGE' from dual;
create synonym CRS_INTERCHANGE for @stjohnUser@.CLW_INTERCHANGE;

--select 'CRS_ELECTRONIC_TRANSACTION' from dual;
create synonym CRS_ELECTRONIC_TRANSACTION for @stjohnUser@.CLW_ELECTRONIC_TRANSACTION;

--select 'CRS_EDI_997' from dual;
create synonym CRS_EDI_997 for @stjohnUser@.CLW_EDI_997;

--- TABLES
/*
--POLINE_GrpOrderNumPoNum
SELECT LAWDB_POLINE.ORDER_NBR, LAWDB_POLINE.PO_NUMBER
FROM LAWDB_POLINE
GROUP BY LAWDB_POLINE.ORDER_NBR, LAWDB_POLINE.PO_NUMBER;
*/
-- select 'CRT_POLINE_GrpOrderNumPoNum' from dual;
create table CRT_POLINE_GrpOrderNumPoNum as
SELECT ORDER_NBR, PO_NUMBER
FROM CRS_POLINE
GROUP BY ORDER_NBR, PO_NUMBER;


/*
mkPoApprove - POAPPROVE
------------
SELECT LAWDB_POAPPROVE.*, POLINE_GrpOrderNumPoNum.ORDER_NBR INTO POAPPROVE
FROM LAWDB_POAPPROVE INNER JOIN POLINE_GrpOrderNumPoNum ON LAWDB_POAPPROVE.PO_NUMBER = POLINE_GrpOrderNumPoNum.PO_NUMBER;
*/
-- select 'CRT_POAPPROVE' from dual;
create table CRT_POAPPROVE as
SELECT 
po.COMPANY,VENDOR,po.INVOICE,po.SUFFIX,po.PO_NUMBER,po.PO_RELEASE,po.LINE_NBR,po.AOC_CODE,po.OPER_ID,
po.ITEM_TYPE,po.ITEM,po.APPROVE_QTY,po.ENT_UNIT_CST,po.COST_ADJ_FL,po.BATCH_NBR,po.DIST_DATE,
po.TAX_CODE,po.TAX_AMOUNT,po.TOT_DIST_AMT,po.TOT_BASE_AMT,po.UPDATE_DATE,
po.UPDATE_TIME,po.R_STATUS,po.VBUY_APPR_QTY,po.NO_TAX_FLAG,po.POASET3_SS_SW,po.POASET4_SS_SW,po.POASET5_SS_SW,
pl.ORDER_NBR
FROM CRS_POAPPROVE po, CRT_POLINE_GrpOrderNumPoNum pl
where po.PO_NUMBER = pl.PO_NUMBER;

/*
query PoApprove_GroupByOrderNumVendorInvoice
SELECT [POAPPROVE].[ORDER_NBR] AS Expr1, [POAPPROVE].[VENDOR] AS Expr2, [POAPPROVE].[INVOICE] AS Expr3
FROM POAPPROVE
GROUP BY [POAPPROVE].[ORDER_NBR], [POAPPROVE].[VENDOR], [POAPPROVE].[INVOICE]
ORDER BY [POAPPROVE].[ORDER_NBR], [POAPPROVE].[VENDOR], [POAPPROVE].[INVOICE];
*/

-- select 'CRT_PoApprove_OrderVenInvoice' from dual;
create table CRT_PoApprove_OrderVenInvoice as
SELECT ORDER_NBR as "Expr1", 
VENDOR as "Expr2", 
INVOICE as "Expr3"
FROM CRT_POAPPROVE
GROUP BY ORDER_NBR, VENDOR, INVOICE;

/*
mkApinvoice - APINVOICE
-----------
SELECT LAWDB_APINVOICE.*, Trim([LAWDB_APINVOICE].[VENDOR]) AS [Vendor ID], [PoApprove_GroupByOrderNumVendorInvoice].[ORDER_NBR] AS Expr1 INTO APINVOICE
FROM LAWDB_APINVOICE, PoApprove_GroupByOrderNumVendorInvoice;
-- select 'CRT_APINVOICE' from dual;
*/

create table CRT_APINVOICE as
SELECT 
  CRS_APINVOICE.COMPANY, 
  CRS_APINVOICE.VENDOR, 
  CRS_APINVOICE.INVOICE, 
  CRS_APINVOICE.SUFFIX, CANCEL_SEQ, 
  CRS_APINVOICE.CANCEL_DATE, 
  CRS_APINVOICE.BATCH_NUM, 
  CRS_APINVOICE.BATCH_DATE, 
  CRS_APINVOICE.VOUCHER_NBR, 
  CRS_APINVOICE.AUTH_CODE, 
  CRS_APINVOICE.PROC_LEVEL, 
  CRS_APINVOICE.ACCR_CODE, 
  CRS_APINVOICE.INVOICE_TYPE, 
  CRS_APINVOICE.INV_CURRENCY, 
  CRS_APINVOICE.PAY_CURRENCY, 
  CRS_APINVOICE.INVOICE_DTE, 
  CRS_APINVOICE.PURCH_FR_LOC, 
  CRS_APINVOICE.PO_NUM, 
  CRS_APINVOICE.PO_RELEASE, 
  CRS_APINVOICE.PO_INVOICE_FL, 
  CRS_APINVOICE.DESCRIPTION, 
  CRS_APINVOICE.BASE_INV_AMT, 
  CRS_APINVOICE.BASE_ACT_AMT, 
  CRS_APINVOICE.BASE_ND, 
  CRS_APINVOICE.TRAN_INV_AMT, 
  CRS_APINVOICE.TRAN_ALOW_AMT, 
  CRS_APINVOICE.TRAN_TXBL_AMT, 
  CRS_APINVOICE.TRAN_ND, 
  CRS_APINVOICE.TRAN_TAX_AMT, 
  CRS_APINVOICE.BASE_DISC_AMT, 
  CRS_APINVOICE.TRAN_DISC_AMT, 
  CRS_APINVOICE.BASE_TOT_PMT, 
  CRS_APINVOICE.TRAN_TOT_PMT, 
  CRS_APINVOICE.BASE_TOT_DIST, 
  CRS_APINVOICE.TRAN_TOT_DIST, 
  CRS_APINVOICE.TRAN_TOT_TAX, 
  CRS_APINVOICE.TRAN_PAID_AMT, 
  CRS_APINVOICE.ORIG_CNV_RATE, 
  CRS_APINVOICE.ANTICIPATION, 
  CRS_APINVOICE.DISCOUNT_RT, 
  CRS_APINVOICE.DISC_DATE, 
  CRS_APINVOICE.DUE_DATE, 
  CRS_APINVOICE.NBR_SPLIT_PMT, 
  CRS_APINVOICE.SPLIT_PMT_SCH, 
  CRS_APINVOICE.NBR_RECUR_PMT, 
  CRS_APINVOICE.RECUR_FREQ, 
  CRS_APINVOICE.REMIT_TO_CODE, 
  CRS_APINVOICE.CASH_CODE, 
  CRS_APINVOICE.BANK_INST_CODE, 
  CRS_APINVOICE.CURR_RECALC, 
  CRS_APINVOICE.TAX_CODE, 
  CRS_APINVOICE.INCOME_CODE, 
  CRS_APINVOICE.DIST_CODE, 
  CRS_APINVOICE.REC_STATUS, 
  CRS_APINVOICE.CREATE_DATE, 
  CRS_APINVOICE.DISTRIB_DATE, 
  CRS_APINVOICE.OPERATOR, 
  CRS_APINVOICE.CREATION_TIME, 
  CRS_APINVOICE.VENDOR_GROUP, 
  CRS_APINVOICE.PAY_VENDOR, 
  CRS_APINVOICE.PAY_GROUP, 
  CRS_APINVOICE.INVOICE_GROUP, 
  CRS_APINVOICE.LAST_DIST_SEQ, 
  CRS_APINVOICE.LAST_PMT_SEQ, 
  CRS_APINVOICE.DISCOUNT_CODE, 
  CRS_APINVOICE.INVOICE_SOURCE, 
  CRS_APINVOICE.INVC_REF_TYPE, 
  CRS_APINVOICE.APPROVED_FLAG, 
  CRS_APINVOICE.APPRV_OPERATOR, 
  CRS_APINVOICE.RETURN_NUMBER, 
  CRS_APINVOICE.JRNL_BOOK_NBR, 
  CRS_APINVOICE.TAX_POINT, 
  CRS_APINVOICE.OBJ_ID, 
  CRS_APINVOICE.RECON_DATE, 
  CRS_APINVOICE.JBK_SEQ_NBR, 
  CRS_APINVOICE.FINAL_DST_FLAG, 
  CRS_APINVOICE.INTRASTAT_NBR, 
  CRS_APINVOICE.SHIP_TO_CTRY, 
  CRS_APINVOICE.SHIP_TO_REGION, 
  CRS_APINVOICE.APISET12_SS_SW, 
  CRS_APINVOICE.APISET2_SS_SW, 
  CRS_APINVOICE.APISET7_SS_SW, 
  CRS_APINVOICE.APISET9_SS_SW,
  Trim(CRS_APINVOICE.VENDOR) "Vendor ID",
  CRT_PoApprove_OrderVenInvoice."Expr1" as ORDER_NBR 
FROM CRS_APINVOICE, CRT_PoApprove_OrderVenInvoice 
where  CRS_APINVOICE.VENDOR = CRT_PoApprove_OrderVenInvoice."Expr2"
  and  CRS_APINVOICE.INVOICE = CRT_PoApprove_OrderVenInvoice."Expr3";

/*
mkPoItemVen - POITEMVEN
-----------
SELECT LAWDB_POITEMVEN.COMPANY, LAWDB_POITEMVEN.ITEM, Trim([VENDOR]) AS [Vendor ID], LAWDB_POITEMVEN.VEN_ITEM, LAWDB_POITEMVEN.VEN_ITEM_DESC, LAWDB_POITEMVEN.VBUY_UOM, LAWDB_POITEMVEN.VBUY_UOM_MULT, LAWDB_POITEMVEN.VBUY_UOM_CONV, LAWDB_POITEMVEN.VPRI_UOM, LAWDB_POITEMVEN.VPRI_UOM_MULT, LAWDB_POITEMVEN.VPRI_UOM_CONV, LAWDB_POITEMVEN.MANUF_CODE, LAWDB_POITEMVEN.MANUF_DIVISION, LAWDB_POITEMVEN.MANUF_NBR, LAWDB_POITEMVEN.LIC_CODE, LAWDB_POITEMVEN.LAST_LEADTIME, LAWDB_POITEMVEN.L_INDEX, LAWDB_POITEMVEN.L_ATPIV_SS_SW, Trim([ITEM]) AS [Item Number] INTO POITEMVEN
FROM LAWDB_POITEMVEN;
*/
-- select 'CRT_POITEMVEN' from dual;
create table CRT_POITEMVEN as
SELECT CRS_POITEMVEN.COMPANY, CRS_POITEMVEN.ITEM, 
       Trim(VENDOR) as "Vendor ID", CRS_POITEMVEN.VEN_ITEM, 
     CRS_POITEMVEN.VEN_ITEM_DESC, CRS_POITEMVEN.VBUY_UOM, 
	   CRS_POITEMVEN.VBUY_UOM_MULT, CRS_POITEMVEN.VBUY_UOM_CONV, CRS_POITEMVEN.VPRI_UOM, 
	   CRS_POITEMVEN.VPRI_UOM_MULT, CRS_POITEMVEN.VPRI_UOM_CONV, CRS_POITEMVEN.MANUF_CODE, 
	   CRS_POITEMVEN.MANUF_DIVISION, CRS_POITEMVEN.MANUF_NBR, CRS_POITEMVEN.LIC_CODE, CRS_POITEMVEN.LAST_LEADTIME, 
	   CRS_POITEMVEN.L_INDEX, CRS_POITEMVEN.L_ATPIV_SS_SW, 
	   Trim(ITEM) as "Item Number" 
FROM CRS_POITEMVEN;

/*
-- mkPoLine - POLINE
SELECT LAWDB_POLINE.*, Trim([ITEM]) AS [CW SKU], Trim([VENDOR]) AS [Vendor ID] INTO POLINE
FROM LAWDB_POLINE;
*/
-- select 'CRT_POLINE' from dual;
create table CRT_POLINE as
SELECT CRS_POLINE.*, Trim(ITEM) as "CW SKU", Trim(VENDOR) as "Vendor ID" 
FROM CRS_POLINE;

create index CRT_POLINE_I1 on CRT_POLINE (ORDER_NBR,ITEM); 

/*
-- query PoLineGrpOnOrderNumAndPoNum
SELECT LAWDB_POLINE.ORDER_NBR, LAWDB_POLINE.PO_NUMBER
FROM LAWDB_POLINE
GROUP BY LAWDB_POLINE.ORDER_NBR, LAWDB_POLINE.PO_NUMBER
HAVING (((LAWDB_POLINE.ORDER_NBR) Is Not Null));
*/
-- select 'CRT_PoLineOrderNumPoNum' from dual;
create table CRT_PoLineOrderNumPoNum as
SELECT ORDER_NBR, PO_NUMBER
FROM CRS_POLINE
GROUP BY ORDER_NBR, PO_NUMBER
HAVING ORDER_NBR Is Not Null;

/*
-- mkPurchOrder - PURCHORDER
SELECT LAWDB_PURCHORDER.*, Trim([VENDOR]) AS [Vendor ID], PoLineGrpOnOrderNumAndPoNum.ORDER_NBR INTO PURCHORDER
FROM LAWDB_PURCHORDER INNER JOIN PoLineGrpOnOrderNumAndPoNum ON LAWDB_PURCHORDER.PO_NUMBER = PoLineGrpOnOrderNumAndPoNum.PO_NUMBER;
*/
-- select 'CRT_PURCHORDER' from dual;
create table CRT_PURCHORDER as
SELECT  
     crs_purchorder.COMPANY, crs_purchorder.PO_NUMBER, crs_purchorder.PO_RELEASE, 
     crs_purchorder.PO_REVISION, crs_purchorder.ORDER_NAME, crs_purchorder.RETURN_NUMBER, 
	   crs_purchorder.VENDOR, crs_purchorder.PURCH_FR_LOC, crs_purchorder.BUYER_CODE, 
	   crs_purchorder.PO_DATE, crs_purchorder.CURRENCY_CODE, crs_purchorder.ENT_CNV_RATE, 
	   crs_purchorder.REC_CNV_RATE, crs_purchorder.CURRENCY_FLAG, crs_purchorder.CURR_SET_FLAG, 
	   crs_purchorder.CURR_TRAN_ND, crs_purchorder.REVALUE_FLAG, crs_purchorder.DFLT_DL_DATE, 
	   crs_purchorder.LOCATION, crs_purchorder.SH_NAME, crs_purchorder.SH_ADDR1, crs_purchorder.SH_ADDR2, 
	   crs_purchorder.SH_ADDR3, crs_purchorder.SH_ADDR4, crs_purchorder.SH_CITY_ADDR5, 
	   crs_purchorder.SH_STATE_PROV, crs_purchorder.SH_POST_CODE, crs_purchorder.SH_COUNTRY, 
	   crs_purchorder.SH_PHONE_PREF, crs_purchorder.SH_PHONE, crs_purchorder.SH_PHONE_EXT, 
	   crs_purchorder.SH_CONTACT, crs_purchorder.CONTRACT_NBR, crs_purchorder.NBR_LINES, 
	   crs_purchorder.CLOSED_LINES, crs_purchorder.DROPSHIP_FL, crs_purchorder.PRT_PO_FLAG, 
	   crs_purchorder.PRT_REC_FLAG, crs_purchorder.PRT_REV_FLAG, crs_purchorder.REPRINT_FLAG, 
	   crs_purchorder.EDI_PO_FLAG, crs_purchorder.EDI_REV_FLAG, crs_purchorder.BLANKET_CD, 
	   crs_purchorder.FAX_PREFIX, crs_purchorder.FAX_NUMBER, crs_purchorder.FAX_EXT, 
	   crs_purchorder.HDR_COMM_CODE_01, crs_purchorder.HDR_COMM_CODE_02, crs_purchorder.TRL_COMM_CODE_01, 
	   crs_purchorder.TRL_COMM_CODE_02, crs_purchorder.PROCESS_LEVEL, crs_purchorder.WEIGHT, 
	   crs_purchorder.CUBIC_FEET, crs_purchorder.FREIGHT_TERMS, crs_purchorder.FOB_CODE, 
	   crs_purchorder.FOB_DESC, crs_purchorder.SHIP_VIA, crs_purchorder.DUE_DAYS, 
	   crs_purchorder.DISC_DAYS, crs_purchorder.DISC_RATE, crs_purchorder.TERM_CODE, 
	   crs_purchorder.TERMS, crs_purchorder.TAX_CODE, crs_purchorder.TOT_LND_AMT, 
	   crs_purchorder.TOT_PRD_AMT, crs_purchorder.TOT_TAXBL_AMT, crs_purchorder.NBR_TAXBL_LN, 
       crs_purchorder.LETTER_OF_CR, crs_purchorder.TOT_ORDER_AMT, crs_purchorder.USER_DATE_1, 
	   crs_purchorder.USER_DATE_2, crs_purchorder.LAST_LINE_NBR, crs_purchorder.LAST_HDR_SEQ, 
	   crs_purchorder.LAST_TRLR_SEQ, crs_purchorder.DFLT_TAXBL_FL, crs_purchorder.RELEASED_FL, 
	   crs_purchorder.PRINTED_FL, crs_purchorder.REVISED_FL, crs_purchorder.CANCELLED_FL, 
	   crs_purchorder.CLOSED_FL, crs_purchorder.LAST_ACTIVITY, crs_purchorder.CLOSE_DATE, 
	   crs_purchorder.PO_USER_FLD_1, crs_purchorder.PO_USER_FLD_3, crs_purchorder.PO_USER_FLD_5, 
	   crs_purchorder.AMOUNT_CL_FL, crs_purchorder.INVC_MTHD_CODE, crs_purchorder.INVC_AUTO_REL, 
	   crs_purchorder.INTRASTAT_NBR, crs_purchorder.TO_CNTRY_CD, crs_purchorder.SHIP_TO_REGION, 
	   crs_purchorder.PCRSET1_SS_SW, crs_purchorder.PCRSET2_SS_SW, crs_purchorder.PCRSET3_SS_SW, 
	   crs_purchorder.PCRSET4_SS_SW, crs_purchorder.PCRSET5_SS_SW, crs_purchorder.PCRSET6_SS_SW, 
	   crs_purchorder.PCRSET7_SS_SW, crs_purchorder.PCRSET8_SS_SW,
  Trim(crs_purchorder.VENDOR) as "Vendor ID", 
  CRT_PoLineOrderNumPoNum.ORDER_NBR 
FROM CRS_PURCHORDER, CRT_PoLineOrderNumPoNum
where CRS_PURCHORDER.PO_NUMBER = CRT_PoLineOrderNumPoNum.PO_NUMBER;

/*
mkShipTo
--------
SELECT LAWDB_SHIPTO.* INTO SHIPTO
FROM LAWDB_SHIPTO;
*/
-- select 'CRT_SHIPTO' from dual;
create table CRT_SHIPTO as
SELECT CRS_SHIPTO.* 
FROM CRS_SHIPTO;

/*
mkLkpVendor lkpVendor
-----------
SELECT STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_ID AS VENDOR_BUS_ENTITY_ID, STJOHN_PROD_CLW_BUS_ENTITY.ERP_NUM AS VendorID, STJOHN_PROD_CLW_BUS_ENTITY.SHORT_DESC AS VENDOR_NAME INTO lkpVendor
FROM STJOHN_PROD_CLW_BUS_ENTITY
WHERE (((STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_TYPE_CD)="DISTRIBUTOR"));
*/
-- select 'CRT_VENDOR' from dual;
create table CRT_VENDOR as
SELECT 
  BUS_ENTITY_ID  as VENDOR_BUS_ENTITY_ID, 
  ERP_NUM  as "VendorID", 
  SHORT_DESC as VENDOR_NAME
FROM CRS_BUS_ENTITY
WHERE BUS_ENTITY_TYPE_CD='DISTRIBUTOR';

create index crt_vendor_i1 on crt_vendor ("VendorID");

/*
mkCustDesc CUSTDESC
-----------
SELECT Trim([CUSTOMER]) AS [Account ID], Trim([SEARCH_NAME]) AS [Account Name] INTO CUSTDESC
FROM LAWDB_CUSTDESC;
*/

-- select 'CRT_CUSTDESC' from dual;
create table CRT_CUSTDESC as
SELECT 
 Trim(CUSTOMER) as "Account ID", 
 Trim(SEARCH_NAME) as "Account Name"
FROM CRS_CUSTDESC;

/*
-- Query - qryOeInvcline
SELECT LAWDB_OEINVCLINE.INVC_NUMBER AS [Invoice Number], LAWDB_OEINVCLINE.LINE_NBR AS [Line Number], Trim([ITEM]) AS [Item Number], LAWDB_OEINVCLINE.DESCRIPTION, LAWDB_OEINVCLINE.ORDER_NBR AS [Order Number], LAWDB_OEINVCLINE.QUANTITY AS Qty, LAWDB_OEINVCLINE.SELL_UOM AS [Invoice UOM], LAWDB_OEINVCLINE.UNIT_PRICE AS [Unit Price], LAWDB_OEINVCLINE.UNIT_COST AS [Unit Cost], LAWDB_OEINVCLINE.LINE_GRS_CURR AS [Extended Item Price], Trim([SALES_MAJCL]) AS [Major Clase ID], Trim([SALES_MINCL]) AS [Minor Class ID]
FROM LAWDB_OEINVCLINE;
*/
-- select 'CRT_Q_OeInvcline' from dual;
create table CRT_Q_OeInvcline as
select 
  INVC_NUMBER as "Invoice Number", 
  LINE_NBR  as "Line Number", 
  Trim(ITEM) as "Item Number", 
  DESCRIPTION, 
  ORDER_NBR  as "Order Number", 
  QUANTITY as "Qty", 
  SELL_UOM as "Invoice UOM", 
  UNIT_PRICE as "Unit Price", 
  UNIT_COST as "Unit Cost", 
  LINE_GRS_CURR as "Extended Item Price", 
  Trim(SALES_MAJCL) as "Major Clase ID", 
  Trim(SALES_MINCL) as "Minor Class ID"
FROM CRS_OEINVCLINE;

/*
mkOeInvcLine OEINVCLINE
-----------
SELECT qryOeInvcline.*, 
       ES_CATALOG2_DRS_CATEGORY.NAME AS [Major Class], ES_CATALOG2_DRS_CATEGORY_1.NAME AS [Minor Class] INTO OEINVCLINE
FROM (qryOeInvcline LEFT JOIN ES_CATALOG2_DRS_CATEGORY ON qryOeInvcline.[Major Clase ID] = ES_CATALOG2_DRS_CATEGORY.ID) LEFT JOIN ES_CATALOG2_DRS_CATEGORY AS ES_CATALOG2_DRS_CATEGORY_1 ON qryOeInvcline.[Minor Class ID] = ES_CATALOG2_DRS_CATEGORY_1.ID;
*/
-- select 'CRT_OEINVCLINE' from dual;
create table CRT_OEINVCLINE as
SELECT 
  CRT_Q_OeInvcline.*, 
  'Category'||"Major Clase ID" AS "Major Class", 
  'Category'||"Minor Class ID" AS "Minor Class" 
FROM CRT_Q_OeInvcline;

-- select 'CRT_COLINE' from dual;
create table CRT_COLINE as
  SELECT CRS_COLINE.*, Trim(VENDOR) as "Vendor ID" 
FROM CRS_COLINE;

create index CRT_COLINE_I1 on CRT_COLINE (ORDER_NBR,ITEM); 

/*
-- Query qryItemCategories 
SELECT STJOHN_PROD_CLW_ITEM_ASSOC.ITEM1_ID, STJOHN_PROD_CLW_ITEM_ASSOC.CATALOG_ID, STJOHN_PROD_CLW_ITEM.SHORT_DESC AS [Category Name], STJOHN_PROD_CLW_ITEM.ITEM_ID AS [Category Item ID], STJOHN_PROD_CLW_ITEM_ASSOC.CATALOG_ID
FROM STJOHN_PROD_CLW_ITEM_ASSOC INNER JOIN STJOHN_PROD_CLW_ITEM ON STJOHN_PROD_CLW_ITEM_ASSOC.ITEM2_ID = STJOHN_PROD_CLW_ITEM.ITEM_ID
WHERE (((STJOHN_PROD_CLW_ITEM_ASSOC.CATALOG_ID)='4') AND ((STJOHN_PROD_CLW_ITEM_ASSOC.ITEM_ASSOC_CD)="PRODUCT_PARENT_CATEGORY"));
*/

-- select 'CRT_Q_ItemCategories' from dual;
create table CRT_Q_ItemCategories as
SELECT 
  CRS_ITEM_ASSOC.ITEM1_ID, 
  CRS_ITEM.SHORT_DESC as "Category Name", 
  CRS_ITEM.ITEM_ID as "Category Item ID", 
  CRS_ITEM_ASSOC.CATALOG_ID
FROM CRS_ITEM_ASSOC, CRS_ITEM 
where CRS_ITEM_ASSOC.ITEM2_ID = CRS_ITEM.ITEM_ID
  and CRS_ITEM_ASSOC.CATALOG_ID=4
  AND CRS_ITEM_ASSOC.ITEM_ASSOC_CD='PRODUCT_PARENT_CATEGORY';

/*
-- Query ItemManufacturer 
SELECT STJOHN_PROD_CLW_ITEM_MAPPING.*, STJOHN_PROD_CLW_BUS_ENTITY.SHORT_DESC AS Manufacturer
FROM STJOHN_PROD_CLW_ITEM_MAPPING INNER JOIN STJOHN_PROD_CLW_BUS_ENTITY ON STJOHN_PROD_CLW_ITEM_MAPPING.BUS_ENTITY_ID = STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_ID
WHERE (((STJOHN_PROD_CLW_ITEM_MAPPING.ITEM_MAPPING_CD)="ITEM_MANUFACTURER"));
*/
-- select 'CRT_ITEM_MANUFACTURER' from dual;
create table CRT_ITEM_MANUFACTURER as
SELECT CRS_ITEM_MAPPING.*, CRS_BUS_ENTITY.SHORT_DESC AS "Manufacturer"
FROM CRS_ITEM_MAPPING,  CRS_BUS_ENTITY 
where CRS_ITEM_MAPPING.BUS_ENTITY_ID = CRS_BUS_ENTITY.BUS_ENTITY_ID
  and CRS_ITEM_MAPPING.ITEM_MAPPING_CD='ITEM_MANUFACTURER';
/*
mkItems - tmpItems
-----------
SELECT STJOHN_PROD_CLW_ITEM.ITEM_ID, STJOHN_PROD_CLW_ITEM.SKU_NUM, STJOHN_PROD_CLW_ITEM.SHORT_DESC, STJOHN_PROD_CLW_ITEM.LONG_DESC, itemMetaColor.Color, itemMetaDED.DED, itemMetaImage.Image, itemMetaListPrice.[List Price], itemMetaMSDS.MSDS, itemMetaPack.Pack, itemMetaPackageUPC.[Package UPC], itemMetaProdSpec.[Product Specification], itemMetaShipWeight.[Ship Weight], itemMetaSize.Size, itemMetaUNSPSC.[UNSPSC Code], itemMetaUOM.UOM, itemMetaUPC.UPC, ItemManufacturer.BUS_ENTITY_ID AS [Manufacturer Bus Entity ID], ItemManufacturer.Manufacturer, ItemManufacturer.ITEM_NUM AS [Manu SKU], qryItemCategories.[Category Item ID], qryItemCategories.[Category Name] INTO tmpItems
FROM (((((((((((((((STJOHN_PROD_CLW_ITEM LEFT JOIN itemMetaColor ON STJOHN_PROD_CLW_ITEM.ITEM_ID = itemMetaColor.ITEM_ID) LEFT JOIN itemMetaDED ON STJOHN_PROD_CLW_ITEM.ITEM_ID = itemMetaDED.ITEM_ID) LEFT JOIN itemMetaImage ON STJOHN_PROD_CLW_ITEM.ITEM_ID = itemMetaImage.ITEM_ID) LEFT JOIN itemMetaListPrice ON STJOHN_PROD_CLW_ITEM.ITEM_ID = itemMetaListPrice.ITEM_ID) LEFT JOIN itemMetaMSDS ON STJOHN_PROD_CLW_ITEM.ITEM_ID = itemMetaMSDS.ITEM_ID) LEFT JOIN itemMetaPack ON STJOHN_PROD_CLW_ITEM.ITEM_ID = itemMetaPack.ITEM_ID) LEFT JOIN itemMetaPackageUPC ON STJOHN_PROD_CLW_ITEM.ITEM_ID = itemMetaPackageUPC.ITEM_ID) LEFT JOIN itemMetaProdSpec ON STJOHN_PROD_CLW_ITEM.ITEM_ID = itemMetaProdSpec.ITEM_ID) LEFT JOIN itemMetaShipWeight ON STJOHN_PROD_CLW_ITEM.ITEM_ID = itemMetaShipWeight.ITEM_ID) LEFT JOIN itemMetaSize ON STJOHN_PROD_CLW_ITEM.ITEM_ID = itemMetaSize.ITEM_ID) LEFT JOIN itemMetaUNSPSC ON STJOHN_PROD_CLW_ITEM.ITEM_ID = itemMetaUNSPSC.ITEM_ID) LEFT JOIN itemMetaUOM ON STJOHN_PROD_CLW_ITEM.ITEM_ID = itemMetaUOM.ITEM_ID) LEFT JOIN itemMetaUPC ON STJOHN_PROD_CLW_ITEM.ITEM_ID = itemMetaUPC.ITEM_ID) LEFT JOIN ItemManufacturer ON STJOHN_PROD_CLW_ITEM.ITEM_ID = ItemManufacturer.ITEM_ID) LEFT JOIN qryItemCategories ON STJOHN_PROD_CLW_ITEM.ITEM_ID = qryItemCategories.ITEM1_ID) INNER JOIN STJOHN_PROD_CLW_CATALOG_STRUCTURE ON STJOHN_PROD_CLW_ITEM.ITEM_ID = STJOHN_PROD_CLW_CATALOG_STRUCTURE.ITEM_ID
WHERE (((STJOHN_PROD_CLW_ITEM.ITEM_TYPE_CD)='PRODUCT') AND ((STJOHN_PROD_CLW_CATALOG_STRUCTURE.CATALOG_STRUCTURE_CD)="CATALOG_PRODUCT") AND ((STJOHN_PROD_CLW_CATALOG_STRUCTURE.CATALOG_ID)="4"));
*/
-- select 'CRT_ITEMS' from dual;
create table CRT_ITEMS as
SELECT 
CRS_ITEM.ITEM_ID, CRS_ITEM.SKU_NUM, CRS_ITEM.SHORT_DESC, CRS_ITEM.LONG_DESC, 
itemMetaColor.clw_value as "Color", 
itemMetaDED.clw_value as "DED", 
itemMetaImage.clw_value as "Image", 
itemMetaListPrice.clw_value as "List Price", 
itemMetaMSDS.clw_value as "MSDS", 
itemMetaPack.clw_value as "Pack", 
itemMetaPackageUPC.clw_value "Package UPC", 
itemMetaProdSpec.clw_value as "Product Specification", 
itemMetaShipWeight.clw_value as "Ship Weight", 
itemMetaSize.clw_value "Size", 
itemMetaUNSPSC.clw_value "UNSPSC Code", 
itemMetaUOM.clw_value as "UOM", 
itemMetaUPC.clw_value as "UPC", 
ItemManufacturer.BUS_ENTITY_ID AS "Manufacturer Bus Entity ID", 
ItemManufacturer."Manufacturer" as "Manufacturer", 
ItemManufacturer.ITEM_NUM as "Manu SKU", 
CRT_Q_ItemCategories."Category Item ID" as "Category Item ID", 
CRT_Q_ItemCategories."Category Name" as "Category Name"
FROM CRS_ITEM, 
CRS_ITEM_META itemMetaColor, 
CRS_ITEM_META itemMetaDED, 
CRS_ITEM_META itemMetaImage, 
CRS_ITEM_META itemMetaListPrice, 
CRS_ITEM_META itemMetaMSDS, 
CRS_ITEM_META itemMetaPack, 
CRS_ITEM_META itemMetaPackageUPC, 
CRS_ITEM_META itemMetaProdSpec, 
CRS_ITEM_META itemMetaShipWeight, 
CRS_ITEM_META itemMetaSize, 
CRS_ITEM_META itemMetaUNSPSC, 
CRS_ITEM_META itemMetaUOM, 
CRS_ITEM_META itemMetaUPC, 
CRT_Q_ItemCategories,
CRT_Item_Manufacturer ItemManufacturer,
CRS_CATALOG_STRUCTURE
where CRS_ITEM.ITEM_TYPE_CD='PRODUCT'
  and CRS_CATALOG_STRUCTURE.CATALOG_STRUCTURE_CD='CATALOG_PRODUCT'
  and CRS_CATALOG_STRUCTURE.CATALOG_ID=4
  and CRS_ITEM.ITEM_ID = CRS_CATALOG_STRUCTURE.ITEM_ID
  and CRS_ITEM.ITEM_ID = itemMetaColor.ITEM_ID (+)
  and itemMetaColor.name_value(+) = 'COLOR'
  and CRS_ITEM.ITEM_ID = itemMetaDED.ITEM_ID (+)
  and itemMetaDED.name_value(+) = 'DED' 
  and CRS_ITEM.ITEM_ID = itemMetaImage.ITEM_ID (+)
  and itemMetaImage.name_value(+) = 'IMAGE'
  and CRS_ITEM.ITEM_ID = itemMetaListPrice.ITEM_ID(+)
  and itemMetaListPrice.name_value(+) = 'LIST_PRICE'
  and CRS_ITEM.ITEM_ID = itemMetaMSDS.ITEM_ID(+)
  and itemMetaMSDS.name_value(+) = 'MSDS'
  and CRS_ITEM.ITEM_ID = itemMetaPack.ITEM_ID(+)
  and itemMetaPack.name_value(+) = 'PACK'
  and CRS_ITEM.ITEM_ID = itemMetaPackageUPC.ITEM_ID(+)
  and itemMetaPackageUPC.name_value(+) = 'PKG_UPC_NUM'
  and CRS_ITEM.ITEM_ID = itemMetaProdSpec.ITEM_ID(+)
  and itemMetaProdSpec.name_value (+)= 'SPEC'
  and CRS_ITEM.ITEM_ID = itemMetaShipWeight.ITEM_ID(+)
  and itemMetaShipWeight.name_value(+) = 'SHIP_WEIGHT'
  and CRS_ITEM.ITEM_ID = itemMetaSize.ITEM_ID(+)
  and itemMetaSize.name_value(+) = 'SIZE'
  and CRS_ITEM.ITEM_ID = itemMetaUNSPSC.ITEM_ID(+)
  and itemMetaUNSPSC.name_value(+) = 'UNSPSC_CD'
  and CRS_ITEM.ITEM_ID = itemMetaUOM.ITEM_ID(+)
  and itemMetaUOM.name_value(+) = 'UOM'
  and CRS_ITEM.ITEM_ID = itemMetaUPC.ITEM_ID(+)
  and itemMetaUPC.name_value(+) = 'UPC_NUM'
  and CRS_ITEM.ITEM_ID = ItemManufacturer.ITEM_ID(+)
  and CRS_ITEM.ITEM_ID = CRT_Q_ItemCategories.ITEM1_ID(+);

/*
mkDistributorItems - tmpDistributorItems
-----------
SELECT STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_ID, STJOHN_PROD_CLW_BUS_ENTITY.SHORT_DESC AS [Distributor Name], STJOHN_PROD_CLW_BUS_ENTITY.ERP_NUM AS [Distributor ID], STJOHN_PROD_CLW_ITEM_MAPPING.ITEM_NUM AS [Distributor SKU], STJOHN_PROD_CLW_ITEM_MAPPING.ITEM_ID, STJOHN_PROD_CLW_ITEM_MAPPING.ITEM_UOM AS [Dist UOM], STJOHN_PROD_CLW_ITEM_MAPPING.ITEM_PACK AS [Dist Pack] INTO tmpDistributorItems
FROM STJOHN_PROD_CLW_BUS_ENTITY INNER JOIN STJOHN_PROD_CLW_ITEM_MAPPING ON STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_ID = STJOHN_PROD_CLW_ITEM_MAPPING.BUS_ENTITY_ID
WHERE (((STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_TYPE_CD)="DISTRIBUTOR") AND ((STJOHN_PROD_CLW_ITEM_MAPPING.ITEM_MAPPING_CD)="ITEM_DISTRIBUTOR"));
*/
-- select 'CRT_DistributorItems' from dual;
create table CRT_DistributorItems as
SELECT 
  CRS_BUS_ENTITY.BUS_ENTITY_ID, 
  CRS_BUS_ENTITY.SHORT_DESC as "Distributor Name", 
  CRS_BUS_ENTITY.ERP_NUM as "Distributor ID", 
  CRS_ITEM_MAPPING.ITEM_NUM as "Distributor SKU", 
  CRS_ITEM_MAPPING.ITEM_ID, 
  CRS_ITEM_MAPPING.ITEM_UOM as "Dist UOM", 
  CRS_ITEM_MAPPING.ITEM_PACK as "Dist_Pack" 
FROM CRS_BUS_ENTITY, CRS_ITEM_MAPPING
 where CRS_BUS_ENTITY.BUS_ENTITY_ID = CRS_ITEM_MAPPING.BUS_ENTITY_ID
   and CRS_BUS_ENTITY.BUS_ENTITY_TYPE_CD='DISTRIBUTOR'
   and CRS_ITEM_MAPPING.ITEM_MAPPING_CD='ITEM_DISTRIBUTOR';

/*
mkContracts - tmpContracts
-----------
SELECT STJOHN_PROD_CLW_CONTRACT.CONTRACT_ID, STJOHN_PROD_CLW_CONTRACT.SHORT_DESC AS [Contract Description], STJOHN_PROD_CLW_CONTRACT.CATALOG_ID, STJOHN_PROD_CLW_CATALOG.SHORT_DESC AS [Catalog Name], STJOHN_PROD_CLW_CONTRACT.CONTRACT_STATUS_CD INTO tmpContracts
FROM STJOHN_PROD_CLW_CONTRACT INNER JOIN STJOHN_PROD_CLW_CATALOG ON STJOHN_PROD_CLW_CONTRACT.CATALOG_ID = STJOHN_PROD_CLW_CATALOG.CATALOG_ID;
*/
-- select 'CRT_Contracts' from dual;
create table CRT_Contracts as
SELECT 
  CRS_CONTRACT.CONTRACT_ID, 
  CRS_CONTRACT.SHORT_DESC as "Contract Description", 
  CRS_CONTRACT.CATALOG_ID, 
  CRS_CATALOG.SHORT_DESC as "Catalog Name", 
  CRS_CONTRACT.CONTRACT_STATUS_CD 
FROM CRS_CONTRACT, CRS_CATALOG 
 where CRS_CONTRACT.CATALOG_ID = CRS_CATALOG.CATALOG_ID;

/*
mkBusEntity - tmpBusEntity
-----------
SELECT STJOHN_PROD_CLW_BUS_ENTITY.* INTO tmpBusEntity
FROM STJOHN_PROD_CLW_BUS_ENTITY;
*/
-- select 'CRT_BusEntity' from dual;
create table CRT_BusEntity as
  SELECT CRS_BUS_ENTITY.* 
FROM CRS_BUS_ENTITY;

/*
-- qryContractCatalogs
SELECT STJOHN_PROD_CLW_CONTRACT.CONTRACT_ID, STJOHN_PROD_CLW_CONTRACT.SHORT_DESC AS [Contract Name], STJOHN_PROD_CLW_CONTRACT.CATALOG_ID, STJOHN_PROD_CLW_CATALOG.SHORT_DESC AS [Catalog Name], STJOHN_PROD_CLW_BUS_ENTITY.SHORT_DESC AS [Distributor Name], STJOHN_PROD_CLW_ITEM.SKU_NUM, STJOHN_PROD_CLW_ITEM.SHORT_DESC AS [Item Name], STJOHN_PROD_CLW_CATALOG_STRUCTURE.ITEM_ID, STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_ID AS DIST_BUS_ENTITY_ID, STJOHN_PROD_CLW_ITEM_MAPPING.ITEM_NUM AS [Dist SKU], STJOHN_PROD_CLW_ITEM_MAPPING.ITEM_UOM AS [Dist UOM], STJOHN_PROD_CLW_ITEM_MAPPING.ITEM_PACK AS [Dist Pack]
FROM (((STJOHN_PROD_CLW_CONTRACT INNER JOIN (STJOHN_PROD_CLW_CATALOG INNER JOIN STJOHN_PROD_CLW_CATALOG_STRUCTURE ON STJOHN_PROD_CLW_CATALOG.CATALOG_ID = STJOHN_PROD_CLW_CATALOG_STRUCTURE.CATALOG_ID) ON STJOHN_PROD_CLW_CONTRACT.CATALOG_ID = STJOHN_PROD_CLW_CATALOG.CATALOG_ID) INNER JOIN STJOHN_PROD_CLW_ITEM ON STJOHN_PROD_CLW_CATALOG_STRUCTURE.ITEM_ID = STJOHN_PROD_CLW_ITEM.ITEM_ID) INNER JOIN STJOHN_PROD_CLW_BUS_ENTITY ON STJOHN_PROD_CLW_CATALOG_STRUCTURE.BUS_ENTITY_ID = STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_ID) INNER JOIN STJOHN_PROD_CLW_ITEM_MAPPING ON (STJOHN_PROD_CLW_ITEM.ITEM_ID = STJOHN_PROD_CLW_ITEM_MAPPING.ITEM_ID) AND (STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_ID = STJOHN_PROD_CLW_ITEM_MAPPING.BUS_ENTITY_ID)
ORDER BY STJOHN_PROD_CLW_CONTRACT.CONTRACT_ID, STJOHN_PROD_CLW_CONTRACT.CATALOG_ID, STJOHN_PROD_CLW_ITEM.SKU_NUM;
*/
-- select 'CRT_Q_ContractCatalogs0' from dual;
create table CRT_Q_ContractCatalogs0 as
SELECT 
  CRS_CONTRACT.CONTRACT_ID, 
  CRS_CONTRACT.SHORT_DESC as "Contract Name", 
  CRS_CONTRACT.CATALOG_ID, 
  CRS_CATALOG.SHORT_DESC as "Catalog Name", 
  CRS_BUS_ENTITY.SHORT_DESC as "Distributor Name", 
  CRS_ITEM.SKU_NUM, 
  CRS_ITEM.SHORT_DESC as "Item Name", 
  CRS_CATALOG_STRUCTURE.ITEM_ID, 
  CRS_BUS_ENTITY.BUS_ENTITY_ID as DIST_BUS_ENTITY_ID 
FROM CRS_CONTRACT, CRS_CATALOG, CRS_BUS_ENTITY, CRS_ITEM, CRS_CATALOG_STRUCTURE
where  CRS_CATALOG.CATALOG_ID = CRS_CATALOG_STRUCTURE.CATALOG_ID
  and  CRS_CONTRACT.CATALOG_ID = CRS_CATALOG.CATALOG_ID
  and  CRS_CATALOG_STRUCTURE.ITEM_ID = CRS_ITEM.ITEM_ID
  and  CRS_CATALOG_STRUCTURE.BUS_ENTITY_ID = CRS_BUS_ENTITY.BUS_ENTITY_ID(+);

-- select 'CRT_Q_ContractCatalogs' from dual;
create table CRT_Q_ContractCatalogs as
SELECT 
  CRT_Q_ContractCatalogs0.*, 
  CRS_ITEM_MAPPING.ITEM_NUM as "Dist SKU", 
  CRS_ITEM_MAPPING.ITEM_UOM as "Dist UOM", 
  CRS_ITEM_MAPPING.ITEM_PACK as "Dist Pack"
FROM  CRT_Q_ContractCatalogs0, CRS_ITEM_MAPPING
 where  CRT_Q_ContractCatalogs0.ITEM_ID = CRS_ITEM_MAPPING.ITEM_ID (+)
  and   CRT_Q_ContractCatalogs0.DIST_BUS_ENTITY_ID = CRS_ITEM_MAPPING.BUS_ENTITY_ID(+);


/*
mkContractItems - tmpContractItems
-----------
SELECT STJOHN_PROD_CLW_CONTRACT_ITEM.CONTRACT_ID, STJOHN_PROD_CLW_CONTRACT_ITEM.ITEM_ID, qryContractCatalogs.SKU_NUM AS [CW SKU], tmpItems.SHORT_DESC AS [Item Name], STJOHN_PROD_CLW_CONTRACT_ITEM.AMOUNT AS [Sell Price], STJOHN_PROD_CLW_CONTRACT_ITEM.DIST_COST AS [Cost to CW], tmpItems.[List Price], tmpItems.UOM, tmpItems.Pack, tmpItems.Manufacturer, tmpItems.[Manu SKU], tmpItems.[Category Item ID], tmpItems.[Category Name], qryContractCatalogs.[Catalog Name], qryContractCatalogs.[Distributor Name], qryContractCatalogs.[Dist SKU], qryContractCatalogs.[Dist UOM], qryContractCatalogs.[Dist Pack] INTO tmpContractItems
FROM (STJOHN_PROD_CLW_CONTRACT_ITEM INNER JOIN tmpItems ON STJOHN_PROD_CLW_CONTRACT_ITEM.ITEM_ID = tmpItems.ITEM_ID) INNER JOIN qryContractCatalogs ON (STJOHN_PROD_CLW_CONTRACT_ITEM.ITEM_ID = qryContractCatalogs.ITEM_ID) AND (STJOHN_PROD_CLW_CONTRACT_ITEM.CONTRACT_ID = qryContractCatalogs.CONTRACT_ID);
*/
-- select 'CRT_ContractItems' from dual;
create table CRT_ContractItems as
SELECT 
  CRS_CONTRACT_ITEM.CONTRACT_ID, 
  CRS_CONTRACT_ITEM.ITEM_ID, 
  CRT_Q_ContractCatalogs.SKU_NUM as "CW SKU", 
  CRT_Items.SHORT_DESC as "Item Name", 
  CRS_CONTRACT_ITEM.AMOUNT as "Sell Price", 
  CRS_CONTRACT_ITEM.DIST_COST as "Cost to CW", 
  CRT_Items."List Price", 
  CRT_Items.UOM, 
  CRT_Items."Pack", 
  CRT_Items."Manufacturer", 
  CRT_Items."Manu SKU" , 
  CRT_Items."Category Item ID", 
  CRT_Items."Category Name", 
  CRT_Q_ContractCatalogs."Catalog Name", 
  CRT_Q_ContractCatalogs."Distributor Name", 
  CRT_Q_ContractCatalogs."Dist SKU", 
  CRT_Q_ContractCatalogs."Dist UOM", 
  CRT_Q_ContractCatalogs."Dist Pack"
FROM CRS_CONTRACT_ITEM, CRT_Q_ContractCatalogs,CRT_Items
where CRS_CONTRACT_ITEM.ITEM_ID = CRT_Items.ITEM_ID 
  and CRS_CONTRACT_ITEM.ITEM_ID = CRT_Q_ContractCatalogs.ITEM_ID 
  and CRS_CONTRACT_ITEM.CONTRACT_ID = CRT_Q_ContractCatalogs.CONTRACT_ID;

/*
mkAccount - tmpAccount
-----------
SELECT STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_ID, STJOHN_PROD_CLW_BUS_ENTITY.SHORT_DESC, STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_TYPE_CD, STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_STATUS_CD, STJOHN_PROD_CLW_BUS_ENTITY.ERP_NUM AS [ERP Account Num] INTO tmpAccount
FROM STJOHN_PROD_CLW_BUS_ENTITY
WHERE (((STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_TYPE_CD)='ACCOUNT'));
*/
-- select 'CRT_Account' from dual;
create table CRT_Account as
SELECT 
  CRS_BUS_ENTITY.BUS_ENTITY_ID, 
  CRS_BUS_ENTITY.SHORT_DESC, 
  CRS_BUS_ENTITY.BUS_ENTITY_TYPE_CD, 
  CRS_BUS_ENTITY.BUS_ENTITY_STATUS_CD, 
  CRS_BUS_ENTITY.ERP_NUM as "ERP Account Num" 
FROM CRS_BUS_ENTITY
WHERE CRS_BUS_ENTITY.BUS_ENTITY_TYPE_CD='ACCOUNT';

/*
mkUser - tmpUser
-----------
SELECT STJOHN_PROD_CLW_USER.USER_ID, STJOHN_PROD_CLW_USER.FIRST_NAME, STJOHN_PROD_CLW_USER.LAST_NAME, STJOHN_PROD_CLW_USER.USER_TYPE_CD, STJOHN_PROD_CLW_USER.USER_STATUS_CD INTO tmpUser
FROM STJOHN_PROD_CLW_USER;
*/
-- select 'CRT_User' from dual;
create table CRT_User as
SELECT 
  CRS_USER.USER_ID, 
  CRS_USER.FIRST_NAME, 
  CRS_USER.LAST_NAME, 
  CRS_USER.USER_TYPE_CD, 
  CRS_USER.USER_STATUS_CD 
FROM CRS_USER;

/*
mkOeInvoice - OEINVOICE
-----------
SELECT LAWDB_OEINVOICE.* INTO OEINVOICE
FROM LAWDB_OEINVOICE;
*/
-- select 'CRT_OEINVOICE' from dual;
create table CRT_OEINVOICE as
  SELECT CRS_OEINVOICE.* 
FROM CRS_OEINVOICE;

/*
mkUserAssociation -  tmpUserAssociation
-----------
SELECT STJOHN_PROD_CLW_USER_ASSOC.* INTO tmpUserAssociation
FROM STJOHN_PROD_CLW_USER_ASSOC;
*/
-- select 'CRT_UserAssociation' from dual;
create table CRT_UserAssociation as
SELECT CRS_USER_ASSOC.* 
FROM CRS_USER_ASSOC;

/*
mkBusEntityAssociation -  tmpBusEntityAssociations
-----------
SELECT STJOHN_PROD_CLW_BUS_ENTITY_ASSOC.* INTO tmpBusEntityAssociations
FROM STJOHN_PROD_CLW_BUS_ENTITY_ASSOC;
*/
-- select 'CRT_BusEntityAssociations' from dual;
create table CRT_BusEntityAssociations as
  SELECT CRS_BUS_ENTITY_ASSOC.* 
FROM CRS_BUS_ENTITY_ASSOC;

/*
mkCatalog -  tmpCatalog
-----------
SELECT STJOHN_PROD_CLW_CATALOG.* INTO tmpCatalog
FROM STJOHN_PROD_CLW_CATALOG;
*/
-- select 'CRT_Catalog' from dual;
create table CRT_Catalog as
  SELECT CRS_CATALOG.*
FROM CRS_CATALOG;

/*
mkCatalogAssociations - tmpCatalogAssociations
-----------
SELECT STJOHN_PROD_CLW_CATALOG_ASSOC.* INTO tmpCatalogAssociations
FROM STJOHN_PROD_CLW_CATALOG_ASSOC;
*/
-- select 'CRT_CatalogAssociations' from dual;
create table CRT_CatalogAssociations as
  SELECT CRS_CATALOG_ASSOC.* 
FROM CRS_CATALOG_ASSOC;

/*
mkCwManufacturer - CW_MANUFACTURER
-----------
SELECT STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_ID AS ID, STJOHN_PROD_CLW_BUS_ENTITY.SHORT_DESC AS NAME INTO CW_MANUFACTURER
FROM STJOHN_PROD_CLW_BUS_ENTITY
WHERE (((STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_TYPE_CD)="MANUFACTURER") AND ((STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_STATUS_CD)="ACTIVE"));
*/
-- select 'CRT_CW_MANUFACTURER' from dual;
create table CRT_CW_MANUFACTURER as
SELECT 
CRS_BUS_ENTITY.BUS_ENTITY_ID AS ID, 
CRS_BUS_ENTITY.SHORT_DESC AS NAME 
FROM CRS_BUS_ENTITY
WHERE CRS_BUS_ENTITY.BUS_ENTITY_TYPE_CD='MANUFACTURER'
  AND CRS_BUS_ENTITY.BUS_ENTITY_STATUS_CD='ACTIVE';

/*
mkCatalogStructure - tmpCatalogStructure
-----------
SELECT STJOHN_PROD_CLW_CATALOG_STRUCTURE.* INTO tmpCatalogStructure
FROM STJOHN_PROD_CLW_CATALOG_STRUCTURE;
*/
-- select 'CRT_CatalogStructure' from dual;
create table CRT_CatalogStructure as
  SELECT CRS_CATALOG_STRUCTURE.* 
FROM CRS_CATALOG_STRUCTURE;

/*
mkOrder - tmpOrder
-----------
SELECT STJOHN_PROD_CLW_ORDER.* INTO tmpOrder
FROM STJOHN_PROD_CLW_ORDER;
*/
-- select 'CRT_Order' from dual;
create table CRT_Order as
  SELECT CRS_ORDER.* 
FROM CRS_ORDER;

create index CRT_ORDER_I1 on CRT_ORDER (ERP_ORDER_NUM);

/*
mkOrderItem - tmpOrderItem
-----------
SELECT STJOHN_PROD_CLW_ORDER_ITEM.* INTO tmpOrderItem
FROM STJOHN_PROD_CLW_ORDER_ITEM;
*/
-- select 'CRT_OrderItem' from dual;
create table CRT_OrderItem as
  SELECT CRS_ORDER_ITEM.* 
FROM CRS_ORDER_ITEM;

/*
mkCategory - tmpCategory
-----------
SELECT STJOHN_PROD_CLW_ITEM.ITEM_ID AS [Category ID], STJOHN_PROD_CLW_ITEM.SHORT_DESC AS [Category Name] INTO tmpCategory
FROM STJOHN_PROD_CLW_ITEM INNER JOIN STJOHN_PROD_CLW_CATALOG_STRUCTURE ON STJOHN_PROD_CLW_ITEM.ITEM_ID = STJOHN_PROD_CLW_CATALOG_STRUCTURE.ITEM_ID
WHERE (((STJOHN_PROD_CLW_ITEM.ITEM_TYPE_CD)="CATEGORY") AND ((STJOHN_PROD_CLW_CATALOG_STRUCTURE.CATALOG_ID)="4"));
*/
-- select 'CRT_Category' from dual;
create table CRT_Category as
SELECT 
  CRS_ITEM.ITEM_ID as "Category ID", 
  CRS_ITEM.SHORT_DESC as "Category Name"
FROM CRS_ITEM, CRS_CATALOG_STRUCTURE
where CRS_ITEM.ITEM_ID = CRS_CATALOG_STRUCTURE.ITEM_ID
  and CRS_ITEM.ITEM_TYPE_CD='CATEGORY'
  and CRS_CATALOG_STRUCTURE.CATALOG_ID=4;

/*
mkSites - tmpSites
-----------
SELECT STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_ID AS SITE_BUS_ENTITY_ID, STJOHN_PROD_CLW_BUS_ENTITY.SHORT_DESC AS [Site Name] INTO tmpSites
FROM STJOHN_PROD_CLW_BUS_ENTITY
WHERE (((STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_TYPE_CD)="SITE"));
*/
-- select 'CRT_Sites' from dual;
/*
create table CRT_Sites as
SELECT 
  CRS_BUS_ENTITY.BUS_ENTITY_ID AS SITE_BUS_ENTITY_ID, 
  CRS_BUS_ENTITY.SHORT_DESC as "SiteName"
FROM CRS_BUS_ENTITY
WHERE CRS_BUS_ENTITY.BUS_ENTITY_TYPE_CD='SITE';
*/
/*
CREATE TABLE SITE ( 
  ACCOUNT_ERP_NUM         VARCHAR2 (30), 
  SITE_ERP_NUM            VARCHAR2 (30), 
  SITE_ID                 NUMBER (38), 
  ACCT_ID                 NUMBER (38), 
  SHIP_NAME1              VARCHAR2 (255), 
  SHIP_NAME2              VARCHAR2 (255), 
  SHIP_ADDRESS1           VARCHAR2 (80), 
  SHIP_ADDRESS2           VARCHAR2 (80), 
  SHIP_ADDRESS3           VARCHAR2 (80), 
  SHIP_ADDRESS4           VARCHAR2 (80), 
  SHIP_CITY               VARCHAR2 (40), 
  SHIP_STATE_PROVINCE_CD  VARCHAR2 (30), 
  SHIP_POSTAL_CODE        VARCHAR2 (15), 
  SHIP_COUNTRY_CD         VARCHAR2 (30), 
  PROP1                   VARCHAR2 (1024), 
  PROP2                   VARCHAR2 (1024), 
  PROP3                   VARCHAR2 (1024) ) ; 
*/
create table crt_site as
select
acct.bus_entity_id AS acct_id,
acct.erp_num AS acct_erp_num, 
acct.short_desc AS acct_name,
site.bus_entity_id AS site_id, 
site.erp_num AS site_erp_num, 
site.short_desc AS site_name,
addr.name1 AS ship_name1, 
addr.name2 AS ship_name2, 
addr.address1 AS ship_address1,
addr.address2 AS ship_address2,
addr.address3 AS ship_address3,
addr.address4 AS ship_address4, 
addr.city AS ship_city, 
addr.state_province_cd AS ship_state_province_cd,
addr.postal_code AS ship_postal_code, 
addr.country_cd AS ship_country_cd
FROM 
crs_bus_entity ACCT, crs_bus_entity SITE, 
crs_bus_entity_assoc assoc, crs_address addr
WHERE
assoc.bus_entity2_id = acct.bus_entity_id AND assoc.bus_entity1_id = SITE.bus_entity_id
AND addr.bus_entity_id = SITE.bus_entity_id 
AND acct.bus_entity_status_cd = 'ACTIVE' AND SITE.bus_entity_status_cd = 'ACTIVE'
AND addr.address_type_cd = 'SHIPPING' AND addr.address_status_cd = 'ACTIVE'
AND TRIM (translate(site.erp_num,'1234567890','          ')) is null
;

create index crt_site_i1 on crt_site (site_erp_num, acct_erp_num);

create index crt_site_i2 on crt_site (site_id);

/*
mkAccountContracts - tmpAccountContracts
-----------
SELECT STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_ID AS ACCOUNT_BUS_ENTITY_ID, STJOHN_PROD_CLW_BUS_ENTITY.ERP_NUM AS [ERP Account Number], STJOHN_PROD_CLW_BUS_ENTITY.SHORT_DESC AS [Account Name], STJOHN_PROD_CLW_CONTRACT.CONTRACT_ID, STJOHN_PROD_CLW_CONTRACT.SHORT_DESC, STJOHN_PROD_CLW_CONTRACT.CONTRACT_STATUS_CD, STJOHN_PROD_CLW_CONTRACT.EFF_DATE INTO tmpAccountContracts
FROM (STJOHN_PROD_CLW_BUS_ENTITY INNER JOIN STJOHN_PROD_CLW_BUS_ENTITY_ASSOC ON STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_ID = STJOHN_PROD_CLW_BUS_ENTITY_ASSOC.BUS_ENTITY2_ID) INNER JOIN ((STJOHN_PROD_CLW_CATALOG_ASSOC INNER JOIN STJOHN_PROD_CLW_CATALOG ON STJOHN_PROD_CLW_CATALOG_ASSOC.CATALOG_ID = STJOHN_PROD_CLW_CATALOG.CATALOG_ID) INNER JOIN STJOHN_PROD_CLW_CONTRACT ON STJOHN_PROD_CLW_CATALOG.CATALOG_ID = STJOHN_PROD_CLW_CONTRACT.CATALOG_ID) ON STJOHN_PROD_CLW_BUS_ENTITY_ASSOC.BUS_ENTITY1_ID = STJOHN_PROD_CLW_CATALOG_ASSOC.BUS_ENTITY_ID
WHERE (((STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_TYPE_CD)="ACCOUNT") AND ((STJOHN_PROD_CLW_BUS_ENTITY_ASSOC.BUS_ENTITY_ASSOC_CD)="SITE OF ACCOUNT"))
GROUP BY STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_ID, STJOHN_PROD_CLW_BUS_ENTITY.ERP_NUM, STJOHN_PROD_CLW_BUS_ENTITY.SHORT_DESC, STJOHN_PROD_CLW_CONTRACT.CONTRACT_ID, STJOHN_PROD_CLW_CONTRACT.SHORT_DESC, STJOHN_PROD_CLW_CONTRACT.CONTRACT_STATUS_CD, STJOHN_PROD_CLW_CONTRACT.EFF_DATE;
*/
-- select 'CRT_AccountContracts' from dual;
create table CRT_AccountContracts as
SELECT 
  CRS_BUS_ENTITY.BUS_ENTITY_ID as ACCOUNT_BUS_ENTITY_ID, 
  CRS_BUS_ENTITY.ERP_NUM as "ERP Account Number", 
  CRS_BUS_ENTITY.SHORT_DESC as "Account Name", 
  CRS_CONTRACT.CONTRACT_ID, 
  CRS_CONTRACT.SHORT_DESC, 
  CRS_CONTRACT.CONTRACT_STATUS_CD, 
  CRS_CONTRACT.EFF_DATE 
FROM CRS_BUS_ENTITY, CRS_CONTRACT,CRS_BUS_ENTITY_ASSOC, CRS_CATALOG_ASSOC,CRS_CATALOG
 where CRS_BUS_ENTITY.BUS_ENTITY_ID = CRS_BUS_ENTITY_ASSOC.BUS_ENTITY2_ID
   and CRS_CATALOG_ASSOC.CATALOG_ID = CRS_CATALOG.CATALOG_ID
   and CRS_CATALOG.CATALOG_ID = CRS_CONTRACT.CATALOG_ID 
   and CRS_BUS_ENTITY_ASSOC.BUS_ENTITY1_ID = CRS_CATALOG_ASSOC.BUS_ENTITY_ID
   and CRS_BUS_ENTITY.BUS_ENTITY_TYPE_CD='ACCOUNT'
   and CRS_BUS_ENTITY_ASSOC.BUS_ENTITY_ASSOC_CD='SITE OF ACCOUNT'
GROUP BY 
  CRS_BUS_ENTITY.BUS_ENTITY_ID, 
  CRS_BUS_ENTITY.ERP_NUM, 
  CRS_BUS_ENTITY.SHORT_DESC, 
  CRS_CONTRACT.CONTRACT_ID, 
  CRS_CONTRACT.SHORT_DESC, 
  CRS_CONTRACT.CONTRACT_STATUS_CD, 
  CRS_CONTRACT.EFF_DATE;

/*
mkProperty - tmpProperty
-----------
SELECT STJOHN_PROD_CLW_PROPERTY.* INTO tmpProperty
FROM STJOHN_PROD_CLW_PROPERTY;
*/
-- select 'CRT_Property' from dual;
create table CRT_Property as
  SELECT CRS_PROPERTY.* 
FROM CRS_PROPERTY;

/*
mkTargetFacility - TargetFacilty
-----------
SELECT STJOHN_PROD_CLW_PROPERTY.*, STJOHN_PROD_CLV_SITE_ACCOUNT.* INTO TargetFacilty
FROM STJOHN_PROD_CLW_PROPERTY INNER JOIN STJOHN_PROD_CLV_SITE_ACCOUNT ON STJOHN_PROD_CLW_PROPERTY.BUS_ENTITY_ID = STJOHN_PROD_CLV_SITE_ACCOUNT.SITE_ID
WHERE (((STJOHN_PROD_CLW_PROPERTY.SHORT_DESC)="Target Facility"));
*/
-- select 'CRT_TargetFacilty' from dual;
create table CRT_TargetFacilty AS
SELECT 
  CRS_PROPERTY.*, 
  CRS_SITE_ACCOUNT.* 
FROM CRS_PROPERTY,CRS_SITE_ACCOUNT
 where CRS_PROPERTY.BUS_ENTITY_ID = CRS_SITE_ACCOUNT.SITE_ID
   and CRS_PROPERTY.SHORT_DESC='Target Facility';

create index crt_TargetFacilty on crt_TargetFacilty(ACCOUNT_ERP_NUM,SITE_ERP_NUM);


/*
mkFacilityType - FacilityType
-----------
SELECT STJOHN_PROD_CLW_PROPERTY.*, STJOHN_PROD_CLW_BUS_ENTITY.SHORT_DESC AS [Site Name] INTO FacilityType
FROM STJOHN_PROD_CLW_PROPERTY INNER JOIN STJOHN_PROD_CLW_BUS_ENTITY ON STJOHN_PROD_CLW_PROPERTY.BUS_ENTITY_ID = STJOHN_PROD_CLW_BUS_ENTITY.BUS_ENTITY_ID
WHERE (((STJOHN_PROD_CLW_PROPERTY.SHORT_DESC)="Facility Type:"));
*/
-- select 'CRT_FacilityType' from dual;
create table CRT_FacilityType as
SELECT 
  CRS_PROPERTY.*, 
  CRS_BUS_ENTITY.SHORT_DESC as "Site Name"
FROM CRS_PROPERTY, CRS_BUS_ENTITY
 where CRS_PROPERTY.BUS_ENTITY_ID = CRS_BUS_ENTITY.BUS_ENTITY_ID
   and CRS_PROPERTY.SHORT_DESC='Facility Type:';

-- select 'CRT_CUSTORDER' from dual;
create table CRT_CUSTORDER as
SELECT 
  CRS_CUSTORDER.*, 
  Trim(CUSTOMER) as "Account Number" 
FROM CRS_CUSTORDER;

create index CRT_CUSTORDER_I1 on CRT_CUSTORDER (ORDER_DATE);


/*
mkLkpOpenPoItems - lkpOpenPoItems
-----------
SELECT LAWDB_POLINE.ORDER_NBR AS [Order Number], LAWDB_POLINE.PO_NUMBER AS [PO Number], Trim([LAWDB_POLINE].[ITEM]) AS [CW SKU], LAWDB_POLINE.ITEM, Trim([LAWDB_POLINE].[VENDOR]) AS [Vendor ID], LAWDB_POLINE.VENDOR, LAWDB_POLINE.LINE_NBR, Sum(LAWDB_POAPPROVE.APPROVE_QTY) AS [Qty Ship], LAWDB_POLINE.QUANTITY, LAWDB_POLINE.ENT_UNIT_CST AS Cost INTO lkpOpenPoItems
FROM LAWDB_POLINE LEFT JOIN LAWDB_POAPPROVE ON (LAWDB_POLINE.LINE_NBR = LAWDB_POAPPROVE.LINE_NBR) AND (LAWDB_POLINE.PO_NUMBER = LAWDB_POAPPROVE.PO_NUMBER)
GROUP BY LAWDB_POLINE.ORDER_NBR, LAWDB_POLINE.PO_NUMBER, Trim([LAWDB_POLINE].[ITEM]), LAWDB_POLINE.ITEM, Trim([LAWDB_POLINE].[VENDOR]), LAWDB_POLINE.VENDOR, LAWDB_POLINE.LINE_NBR, LAWDB_POLINE.QUANTITY, LAWDB_POLINE.ENT_UNIT_CST
HAVING (((Sum(LAWDB_POAPPROVE.APPROVE_QTY)) Is Null Or (Sum(LAWDB_POAPPROVE.APPROVE_QTY))<[LAWDB_POLINE].[QUANTITY]))
ORDER BY LAWDB_POLINE.PO_NUMBER;
*/
-- select 'CRT_OpenPoItems' from dual;
create table CRT_OpenPoItems as
SELECT 
  CRS_POLINE.ORDER_NBR as "Order Number", 
  CRS_POLINE.PO_NUMBER as "PO Number", 
  Trim(CRS_POLINE.ITEM) as "CW SKU", 
  CRS_POLINE.ITEM, 
  Trim(CRS_POLINE.VENDOR) as "Vendor ID", 
  CRS_POLINE.VENDOR, 
  CRS_POLINE.LINE_NBR, 
  Sum(CRS_POAPPROVE.APPROVE_QTY) as "Qty Ship", 
  CRS_POLINE.QUANTITY, 
  CRS_POLINE.ENT_UNIT_CST as "Cost"
FROM CRS_POLINE, CRS_POAPPROVE
 where  CRS_POLINE.LINE_NBR = CRS_POAPPROVE.LINE_NBR(+) 
   AND  CRS_POLINE.PO_NUMBER = CRS_POAPPROVE.PO_NUMBER(+)
GROUP BY 
  CRS_POLINE.ORDER_NBR, 
  CRS_POLINE.PO_NUMBER, 
  CRS_POLINE.ITEM, 
  CRS_POLINE.VENDOR, 
  CRS_POLINE.LINE_NBR, 
  CRS_POLINE.QUANTITY, 
  CRS_POLINE.ENT_UNIT_CST
HAVING Sum(CRS_POAPPROVE.APPROVE_QTY) Is Null Or 
       Sum(CRS_POAPPROVE.APPROVE_QTY)<CRS_POLINE.QUANTITY;


/*
lkpUSPSFacilityType
*/
create table crt_USPSFacilityType as 
select substr(trim(be.short_desc),1,6) as CUSTOMER_BUDGET_REF,
       ft.clw_value as FACILITY_TYPE, be.erp_num as SITE_ERP_NUM, be1.erp_num as CUSTOMER_ERP_NUM  
from crs_bus_entity be, crt_facilitytype ft, crs_bus_entity be1, crs_bus_entity_assoc bea
 where be.bus_entity_id = ft.bus_entity_id 
   and be1.bus_entity_type_cd = 'ACCOUNT' 
   and be1.erp_num = '10052'
   and bea.bus_entity1_id = be.bus_entity_id
   and bea.bus_entity2_id = be1.bus_entity_id
   and bea.bus_entity_assoc_cd = 'SITE OF ACCOUNT'
   and be.erp_num is not null;

/*
lkpFedStripAreaAndDistrict
*/
create table crt_FedStripAreaAndDistrict as 
select substr(trim(be.short_desc),1,6) as CUSTOMER_BUDGET_REF,
       area.clw_value as Area, 
       dist.clw_value as District, 
	   be.erp_num as SITE_ERP_NUM, be1.erp_num as CUSTOMER_ERP_NUM  
from crs_bus_entity be, crs_bus_entity be1, crs_bus_entity_assoc bea, crs_property dist, crs_property area
 where bea.bus_entity1_id = be.bus_entity_id
   and bea.bus_entity2_id = be1.bus_entity_id
   and bea.bus_entity_assoc_cd = 'SITE OF ACCOUNT'
   and be.erp_num is not null
   and dist.short_desc = 'District'
   and area.short_desc = 'Area'
   and be.bus_entity_id = dist.bus_entity_id
   and be.bus_entity_id = area.bus_entity_id;


/* Specific Site data */
create table crt_site_data ( 
SITE_ID              NUMBER (38), 
"Area"                 VARCHAR2 (128), 
"District"             VARCHAR2 (128), 
"Facility Type"        VARCHAR2 (128), 
"Hot Store"            VARCHAR2 (128), 
"IQ Work Order Number" VARCHAR2 (128), 
"Net Square Footage"   VARCHAR2 (128), 
"Store Type"           VARCHAR2 (128), 
"Target Facility"      VARCHAR2 (128) 
);

insert into crt_site_data
select bus_entity_id, 
null, null, null, null,
null, null, null, null
from crs_bus_entity where bus_entity_type_cd = 'SITE'
;

create index crt_site_data_i1 on crt_site_data (site_id);


update crt_site_data sd set "Area"=
(
select min(clw_value) from crs_property pr 
  where pr.bus_entity_id = sd.site_id
    and pr.short_desc = 'Area'
); 


update crt_site_data sd set "District"=
(
select min(clw_value) from crs_property pr 
  where pr.bus_entity_id = sd.site_id
    and pr.short_desc = 'District'
); 

update crt_site_data sd set "Facility Type"=
(
select min(clw_value) from crs_property pr 
  where pr.bus_entity_id = sd.site_id
    and pr.short_desc = 'Facility Type'
); 


update crt_site_data sd set "Hot Store"=
(
select min(clw_value) from crs_property pr 
  where pr.bus_entity_id = sd.site_id
    and pr.short_desc = 'Hot Store'
); 

update crt_site_data sd set "IQ Work Order Number"=
(
select min(clw_value) from crs_property pr 
  where pr.bus_entity_id = sd.site_id
    and pr.short_desc = 'IQ Work Order Number'
); 

update crt_site_data sd set "Net Square Footage"=
(
select min(clw_value) from crs_property pr 
  where pr.bus_entity_id = sd.site_id
    and pr.short_desc = 'Net Square Footage'
); 

update crt_site_data sd set "Store Type"=
(
select min(clw_value) from crs_property pr 
  where pr.bus_entity_id = sd.site_id
    and pr.short_desc = 'Store Type'
); 

update crt_site_data sd set "Target Facility"=
(
select min(clw_value) from crs_property pr 
  where pr.bus_entity_id = sd.site_id
    and pr.short_desc = 'Target Facility'
); 




/* tbHolidays */
create table crt_holidays (HoliDate date,HolidayName varchar2(50));

insert into crt_holidays values (to_date('1/1/2000','mm/dd/yyyy'),	'New Year''s Day');

insert into crt_holidays values (to_date('1/17/2000','mm/dd/yyyy'),	'Martin Luther King Day');

insert into crt_holidays values (to_date('2/21/2000','mm/dd/yyyy'),	'President''s Day');

insert into crt_holidays values (to_date('7/4/2000','mm/dd/yyyy'),	'Independence Day');

insert into crt_holidays values (to_date('9/4/2000','mm/dd/yyyy'),	'Labor Day');

insert into crt_holidays values (to_date('11/23/2000','mm/dd/yyyy'),	'Thanksgiving');

insert into crt_holidays values (to_date('12/25/2000','mm/dd/yyyy'),	'Christmas');

insert into crt_holidays values (to_date('12/31/2000','mm/dd/yyyy'),	'New Year''s Eve');

insert into crt_holidays values (to_date('5/27/2002','mm/dd/yyyy'),	'Memorial Day');


-------------------------------------------------
--- Views 
create view crv_poline as 
select * from crt_poline where trim(translate("CW SKU",'1234567890','          ')) is null;



---------------------------------------------
-- Report tables
drop table crw_numbers;

-- parameter tables
CREATE GLOBAL TEMPORARY TABLE CRW_account ( 
 ACCOUNT_ID      NUMBER (38) 
 ACCOUNT_ERP_NUM VARCHAR (32) 
)
ON COMMIT delete ROWS; 

CREATE GLOBAL TEMPORARY TABLE CRW_dist ( 
 DIST_ID      NUMBER (38), 
 DIST_ERP_NUM VARCHAR (32) 
)
ON COMMIT delete ROWS; 


-- Begin Order Tracker 
create global temporary table crw_numbers 
(nbr number(38))
ON COMMIT delete ROWS; 


drop table crw_order_tracker_coline;

create global temporary table crw_order_tracker_coline 
( 
ORDER_NBR  NUMBER (38), 
LINE_NBR  NUMBER (38), 
ITEM   VARCHAR2 (32), 
ORDER_QTY  NUMBER (13,4), 
"Line Item Sell Price" NUMBER (13,4), 
"Line Item Cost"       NUMBER (13,4) 
)
ON COMMIT delete ROWS; 


drop table crw_order_tracker_poline ;

create global temporary table crw_order_tracker_poline 
(
ORDER_NBR NUMBER (38), 
PO_NUMBER VARCHAR2 (7), 
LINE_NBR  NUMBER (6), 
ITEM      VARCHAR2 (32), 
QUANTITY  NUMBER (13,4), 
"Vendor ID" VARCHAR2 (9)
)
ON COMMIT delete ROWS; 

  
drop table crw_order_tracker_pos;

create global temporary table crw_order_tracker_pos
(
ORDER_NBR     NUMBER (8), 
PO_NUMBER     VARCHAR (7), 
"Vendor ID"     VARCHAR (9), 
"Vendor Name"   VARCHAR (30), 
"Num PO Items"  NUMBER (13,4), 
"PO Sell Price" NUMBER (13,4), 
"PO Cost"       NUMBER (13,4)
) 
ON COMMIT delete ROWS; 
  

drop table crw_order_tracker_cost;

create global temporary table crw_order_tracker_cost
(
ORDER_NBR                   NUMBER (8), 
"Total qty of items in order" NUMBER (13,4), 
"Total Cost"                  NUMBER (13,4) 
)
ON COMMIT delete ROWS; 


drop table crw_order_tracker_result;

create global temporary table crw_order_tracker_result
(
"Date Ordered"           DATE, 
"Cust Account Number"    VARCHAR (9), 
"Ship To"                NUMBER (4), 
"Account Name"           VARCHAR (30), 
"Location Name"          VARCHAR (30), 
"Ship Address 1"         VARCHAR (30), 
"Ship Address 2"         VARCHAR (30), 
"Ship Address 3"         VARCHAR (30), 
"Ship City"              VARCHAR (18), 
"Ship State"             VARCHAR (2), 
"Ship Zip"               VARCHAR (10), 
"ERP Order Number"       NUMBER (8), 
"PO_NUMBER"              VARCHAR (7), 
"Vendor ID"              VARCHAR (9), 
"Vendor Name"            VARCHAR (30), 
"PO Sell Price$"         NUMBER (13,4), 
"Num PO Items"           NUMBER (13,4), 
"Total Qty Order Items$" NUMBER (13,4), 
"Total Order Price$"     NUMBER (15,2), 
"Total Order Cost$"      NUMBER (13,4), 
"PO Cost$"               NUMBER (13,4), 
"SITE_ID"                NUMBER (38), 
"Area"                   VARCHAR (1024), 
"District"               VARCHAR (1024), 
"Facility Type"          VARCHAR (1024), 
"Hot Store"              VARCHAR (1024), 
"IQ Work Order Number"   VARCHAR (1024), 
"Net Square Footage"     VARCHAR (1024), 
"Store Type"             VARCHAR (1024), 
"Target Facility"        VARCHAR (1024) 
)
ON COMMIT preserve ROWS; 
-- End Order Tracker 


-- Begin Invoice Detail
CREATE GLOBAL TEMPORARY TABLE CRW_Invoice_Detail_Sales ( 
  INVC_PREFIX          VARCHAR2 (2), 
  INVC_NUMBER          NUMBER (8), 
  INV_QTY              NUMBER (13,4), 
  INV_LINE_TOTAL       NUMBER (15,2), 
  INVOICE_DATE         DATE, 
  INVOICE_TYPE		   VARCHAR2 (10), 
  SHIP_DATE            DATE, 
  INV_SUBTOTAL         NUMBER (15,2), 
  MISC_TOTAL           NUMBER (15,2), 
  TAX_TOTAL            NUMBER (15,2), 
  ORDER_NBR            NUMBER (8), 
  ORDER_DATE           DATE, 
  OPR_CODE             VARCHAR2 (10), 
  CUST_PO_NBR          VARCHAR2 (20), 
  SHIP_TO              VARCHAR2 (40), 
  CUSTOMER             VARCHAR2 (9), 
  SHIP_TO_NAME         VARCHAR2 (30), 
  SHIP_TO_ADDR1        VARCHAR2 (30), 
  SHIP_TO_ADDR2        VARCHAR2 (30), 
  SHIP_TO_ADDR3        VARCHAR2 (30), 
  SHIP_TO_ADDR4        VARCHAR2 (30), 
  SHIP_TO_CITY         VARCHAR2 (18), 
  SHIP_TO_STATE        VARCHAR2 (2), 
  SHIP_TO_ZIP          VARCHAR2 (10), 
  CONFIRMATION_NUMBER  VARCHAR2 (10), 
  VENDOR               VARCHAR2 (9), 
  LINE_NBR             NUMBER (6), 
  SALES_MAJCL          VARCHAR2 (4), 
  SALES_MAJCL_NAME     VARCHAR2 (255),
  SALES_MINCL          VARCHAR2 (4), 
  SALES_MINCL_NAME         VARCHAR2 (255),
  ITEM                 VARCHAR2 (32), 
  ITEM_DESC            VARCHAR2 (30), 
  SELL_UOM             VARCHAR2 (4), 
  UNIT_PRICE           NUMBER (13,5), 
  UNIT_COST            NUMBER (13,5), 
  ORDER_QTY            NUMBER (13,4), 
  INVOICE_QTY          NUMBER (13,4), 
  batch_nbr			   NUMBER(6)) 
ON COMMIT delete ROWS; 

CREATE GLOBAL TEMPORARY TABLE CRW_Invoice_Detail_Items ( 
  MANU_ID          NUMBER (38), 
  MANU_SDESC       VARCHAR2 (30), 
  MANU_LDESC       VARCHAR2 (255), 
  MANU_STATUS      VARCHAR2 (30), 
  ITEM_SDESC       VARCHAR2 (255), 
  SKU_NUM          VARCHAR2 (38), 
  ITEM_LDESC       VARCHAR2 (2000), 
  ITEM_STATUS_CD   VARCHAR2 (30), 
  ITEM_MAPPING_ID  NUMBER (38), 
  ITEM_ID          NUMBER (38), 
  MANU_SKU         VARCHAR2 (256),
  CATEGORY         VARCHAR2 (256),
  CATEGORY_ID	   NUMBER (38)
)
ON COMMIT delete ROWS; 

create index CRW_Invoice_Detail_Items_i1 on CRW_Invoice_Detail_Items (sku_num);

create global temporary table  CRW_BUDGET_PERIODS (
COST_CENTER_ID NUMBER (38),
BUDGET_PERIOD NUMBER (38),
BUDGET_PERIOD_BEG VARCHAR2(5),
NEXT_BUDGET_PERIOD_BEG VARCHAR2(5) 
) on commit delete rows;


create global temporary table CRW_Invoice_Detail_Result (
NVC_PREFIX            VARCHAR2 (2), 
INVC_NUMBER            NUMBER (8), 
INV_QTY                NUMBER (13,4), 
INV_LINE_TOTAL$         NUMBER (15,2), 
INVOICE_DATE           DATE, 
INVOICE_TYPE           VARCHAR2 (10), 
SHIP_DATE              DATE, 
INV_SUBTOTAL$           NUMBER (15,2), 
MISC_TOTAL$             NUMBER (15,2), 
TAX_TOTAL$              NUMBER (15,2), 
ORDER_NBR              NUMBER (8), 
ORDER_DATE             DATE, 
OPR_CODE               VARCHAR2 (10), 
CUST_PO_NBR            VARCHAR2 (20), 
SHIP_TO                VARCHAR2 (40), 
CUSTOMER               VARCHAR2 (9), 
SHIP_TO_NAME           VARCHAR2 (30), 
SHIP_TO_ADDR1          VARCHAR2 (30), 
SHIP_TO_ADDR2          VARCHAR2 (30), 
SHIP_TO_ADDR3          VARCHAR2 (30), 
SHIP_TO_ADDR4          VARCHAR2 (30), 
SHIP_TO_CITY           VARCHAR2 (18), 
SHIP_TO_STATE          VARCHAR2 (2), 
SHIP_TO_ZIP            VARCHAR2 (10), 
CONFIRMATION_NUMBER    VARCHAR2 (10), 
VENDOR                 VARCHAR2 (9), 
LINE_NBR               NUMBER (6), 
SALES_MAJCL            VARCHAR2 (4), 
SALES_MAJCL_NAME       VARCHAR2 (255), 
SALES_MINCL            VARCHAR2 (4), 
SALES_MINCL_NAME       VARCHAR2 (255), 
ITEM                   VARCHAR2 (32), 
ITEM_DESC              VARCHAR2 (30), 
SELL_UOM               VARCHAR2 (4), 
UNIT_PRICE$             NUMBER (13,5), 
UNIT_COST$              NUMBER (13,5), 
ORDER_QTY              NUMBER (13,4), 
INVOICE_QTY            NUMBER (13,4), 
BATCH_NBR              NUMBER (6), 
MANU_ID                NUMBER (38), 
MANU_SDESC             VARCHAR2 (30), 
MANU_LDESC             VARCHAR2 (255), 
MANU_STATUS            VARCHAR2 (30), 
ITEM_SDESC             VARCHAR2 (255), 
SKU_NUM                VARCHAR2 (38), 
ITEM_LDESC             VARCHAR2 (2000), 
ITEM_STATUS_CD         VARCHAR2 (30), 
ITEM_MAPPING_ID        NUMBER (38), 
ITEM_ID                NUMBER (38), 
MANU_SKU               VARCHAR2 (256), 
CATEGORY               VARCHAR2 (256), 
CATEGORY_ID            NUMBER (38), 
ACCT_ID                NUMBER (38), 
ACCT_ERP_NUM           VARCHAR2 (30), 
ACCT_NAME              VARCHAR2 (30), 
SITE_ID                NUMBER (38), 
SITE_ERP_NUM           VARCHAR2 (30), 
SITE_NAME              VARCHAR2 (30), 
SHIP_NAME1             VARCHAR2 (255), 
SHIP_NAME2             VARCHAR2 (255), 
SHIP_ADDRESS1          VARCHAR2 (80), 
SHIP_ADDRESS2          VARCHAR2 (80), 
SHIP_ADDRESS3          VARCHAR2 (80), 
SHIP_ADDRESS4          VARCHAR2 (80), 
SHIP_CITY              VARCHAR2 (40), 
SHIP_STATE_PROVINCE_CD VARCHAR2 (30), 
SHIP_POSTAL_CODE       VARCHAR2 (15), 
SHIP_COUNTRY_CD        VARCHAR2 (30), 
"Area"                   VARCHAR2 (128), 
"District"               VARCHAR2 (128), 
"Facility Type"          VARCHAR2 (128), 
"Hot Store"              VARCHAR2 (128), 
"IQ Work Order Number"   VARCHAR2 (128), 
"Net Square Footage"     VARCHAR2 (128), 
"Store Type"             VARCHAR2 (128), 
"Target Facility"        VARCHAR2 (128) 
)	  
ON COMMIT preserve ROWS; 

-- End Invoice Detail

-- Begin Open Lines
create global temporary table crw_Open_Lines_Item_Ven (
ITEM           VARCHAR2 (32), 
VENDOR         VARCHAR2 (9), 
VEN_ITEM       VARCHAR2 (32), 
VEN_ITEM_DESC  VARCHAR2 (30), 
VBUY_UOM       VARCHAR2 (4), 
VPRI_UOM       VARCHAR2 (4)
)
ON COMMIT delete ROWS
;
   
create index crw_Open_Lines_Item_Ven_i1 on crw_Open_Lines_Item_Ven (vendor,item);


CREATE GLOBAL TEMPORARY TABLE crw_Open_Lines_Result (  
"Account Erp Num" VARCHAR2 (9), 
"Target Facility" VARCHAR2 (128), 
"Goods$"          NUMBER (15,2), 
"Vendor"          VARCHAR2 (9), 
"Vendor Name"     VARCHAR2 (30), 
"Po Date"         DATE, 
"Item"            VARCHAR2 (32), 
"Unit Cost$"      NUMBER (13,5), 
"PO Number"       VARCHAR2 (7), 
"Line Number"     NUMBER (6), 
"Order Number"    NUMBER (8), 
"Vendor Item"     VARCHAR2 (32), 
"Descritption"    VARCHAR2 (30),
"Ship To"         VARCHAR2 (30),
"Ship Name"       VARCHAR2 (18), 
"State"           VARCHAR2 (2), 
"Zip Code"        VARCHAR2 (10), 
"Quantity"        NUMBER (13,4), 
"Open Quantity"   NUMBER (15,2), 
"Open Cost$"      NUMBER (15,2) 
)  
ON COMMIT preserve ROWS
;

-- End Open Lines

-- Begin Account Contract Items

CREATE GLOBAL TEMPORARY TABLE crw_acct_contract_items_result (
ACCOUNT_ID NUMBER (38), 
ACCOUNT_NAME VARCHAR2(50),
ACCOUNT_ERP_NUM VARCHAR2(30),
ITEM_ID NUMBER (38),
ITEM_SHORT_DESC VARCHAR2(255),
MANUF_ITEM_NO VARCHAR2(30),
UOM  VARCHAR2(30), 
UNSPSC VARCHAR2(10), 
LIST_PRICE$ NUMBER(13,4), 
MANUF_NAME  VARCHAR2(30), 
MANUF_ID NUMBER (38),
MAX_CONTRACT_PRICE$ NUMBER (15,4),
MIN_CONTRACT_PRICE$ NUMBER (15,4),
PRICE_DIFF$ NUMBER (15,4)
)
ON COMMIT preserve ROWS;

-- End Account Contract Items

-- Begin Customer Invoice Mismatch

CREATE GLOBAL TEMPORARY TABLE crw_cust_invc_mismatch_result ( 
MISMATCH_CODE             VARCHAR (30), 
CLW_INVOICE_DATE          DATE, 
LAW_INVOICE_DATE          DATE, 
CLW_INVOICE_TYPE          VARCHAR (4), 
CLW_INVOICE_NUM           VARCHAR (50), 
LAW_INVC_PREFIX           VARCHAR (2), 
LAW_INVC_NUMBER           NUMBER (8), 
CLW_ORDER_NUM             VARCHAR (50), 
CLW_ERP_ORDER_NUM         NUMBER (8), 
LAW_ORDER_NBR             NUMBER (8), 
CLW_ACCOUNT_ERP_NUM       VARCHAR (30), 
LAW_CUSTOMER              VARCHAR (9), 
CLW_SITE_ERP_NUM          VARCHAR (30), 
LAW_SHIP_TO               NUMBER (4), 
CLW_SUB_TOTAL$             NUMBER (15,3), 
LAW_NON_INV_GOODS$         NUMBER (15,2), 
CLW_FREIGHT$               NUMBER (15,3), 
LAW_FRT_CHARGE$            NUMBER (15,2), 
CLW_SALES_TAX$             NUMBER (15,3), 
LAW_TAX_TOTAL$             NUMBER (15,2), 
CLW_DISCOUNTS$             NUMBER (15,3), 
LAW_ORD_DISCOUNT$          NUMBER (15,2), 
CLW_MISC_CHARGES$          NUMBER (15,3), 
LAW_MISC_TOTAL$            NUMBER (15,2), 
CLW_CREDITS$               NUMBER (15,3), 
CLW_ACCOUNT_ID            NUMBER (38), 
CLW_SITE_ID               NUMBER (38), 
CLW_ORDER_ID              NUMBER (38), 
CLW_ERP_PO_NUM            VARCHAR (50), 
CLW_INVOICE_STATUS_CD     VARCHAR (30), 
CLW_BILL_TO_NAME          VARCHAR (80), 
CLW_SHIPPING_NAME         VARCHAR (80), 
CLW_SHIPPING_ADDRESS_1    VARCHAR (50), 
CLW_SHIPPING_ADDRESS_3    VARCHAR (50), 
CLW_NET_DUE$               NUMBER (15,3), 
CLW_BATCH_NUMBER          NUMBER (38), 
CLW_BATCH_DATE            DATE, 
CLW_BATCH_TIME            DATE, 
CLW_PAYMENT_TERMS_CD      VARCHAR (3), 
CLW_ORIGINAL_INVOICE_NUM  VARCHAR (50), 
CLW_CIT_STATUS_CD         VARCHAR (30), 
CLW_CIT_ASSIGNMENT_NUMBER NUMBER (38), 
CLW_CIT_TRANSACTION_DATE  DATE, 
LAW_ORIG_INVC_PRE         VARCHAR (2), 
LAW_ORIG_INVC_NBR         NUMBER (8), 
LAW_INVC_TYPE             VARCHAR (4), 
LAW_INVC_SOURCE           VARCHAR (1), 
LAW_R_STATUS              NUMBER (1), 
LAW_ENTERED_DISC$          NUMBER (15,2), 
LAW_ADD_ON_DISC$           NUMBER (15,2), 
LAW_CIA_AMT$               NUMBER (15,2), 
LAW_CIA_AMT_BASE$          NUMBER (15,2), 
LAW_OPEN_ORD_AMT$          NUMBER (15,2), 
LAW_TAX_CODE              VARCHAR (10), 
LAW_COMM_RATE_1           NUMBER (7,7), 
LAW_FREIGHT_CODE          VARCHAR (2), 
LAW_FRT_TAXABLE_FL        VARCHAR (1), 
LAW_DISCOUNTABLE$          NUMBER (15,2), 
LAW_TAXABLE_TOTAL$         NUMBER (15,2), 
LAW_CUST_PO_NBR           VARCHAR (20), 
LAW_SHIP_DATE             DATE, 
LAW_AR_CODE               VARCHAR (4), 
LAW_TRANS_USER1           VARCHAR (10), 
LAW_TAX_EXEMPT_CD         VARCHAR (1)
) 
ON COMMIT preserve ROWS;

-- End Custormer Invoice Mismatch

-- Begin Stjohn-Lawson Order Mismatch
create global temporary table crw_stj_lawson_order_rel
(
FLAG  NUMBER (38),
ORDER_ID NUMBER (38), 
ERP_ORDER_NUM NUMBER (38),
ITEM_SUM  NUMBER (38),
QTY_SM NUMBER (38),
ITEM_QTY_SUM  NUMBER (38)
)
ON COMMIT delete ROWS
;

create global temporary table crw_stj_lawson_order_result (
S_ORDER_ID            NUMBER (38), 
S_ORDER_NUM           VARCHAR2 (50), 
L_ORDER_NBR           NUMBER (8), 
L_STJ_ORDER_NUM       VARCHAR2 (10), 
S_ORDER_STATUS_CD     VARCHAR2 (30), 
L_ORDER_STATUS        VARCHAR2 (40), 
S_ORIGINAL_ORDER_DATE DATE, 
S_ADD_DATE            DATE, 
L_ORDER_DATE          DATE, 
S_EXCEPTION_IND       VARCHAR2 (1), 
ACCOUNT_ERP_NUM       VARCHAR2 (30), 
L_ACCOUNT_NUM         VARCHAR2 (9), 
S_SITE_ERP_NUM        VARCHAR2 (30), 
L_SITE_NUM            NUMBER (4), 
S_TOTAL_PRICE         NUMBER (15,3), 
L_OPEN_ORD_AMT        NUMBER (15,2), 
L_PASS_ORD_AMT        NUMBER (15,2), 
S_TOTAL_HANDLE_COST   NUMBER (15,2), 
L_MISC                NUMBER (15,2), 
S_TOTAL_TAX_COST      NUMBER (15,3), 
L_TAX                 NUMBER (15,2), 
L_ORDER_TYPE          VARCHAR2 (2), 
S_ORDER_SOURCE_CD     VARCHAR2 (30), 
L_ORDER_SOURCE        VARCHAR2 (2)
) 
ON COMMIT preserve ROWS;
-- End Stjohn-Lawson Order Mismatch

-- Begin Stjohn-Lawson Order Item Quantity Mismatch

create global temporary table crw_stj_law_order_item_sum
(
FLAG  NUMBER (38),
ORDER_ID NUMBER (38), 
ERP_ORDER_NUM NUMBER (38),
ITEM_SUM  NUMBER (38),
QTY_SUM NUMBER (38),
ITEM_QTY_SUM  NUMBER (38)
)
ON COMMIT delete ROWS
;

create index crw_stj_law_order_item_sum_i1 on crw_stj_law_order_item_sum (order_id);

create index crw_stj_law_order_item_sum_i2 on crw_stj_law_order_item_sum (ERP_ORDER_NUM);


create global temporary table crw_stj_law_order_item_rel
(
FLAG  NUMBER (38),
ORDER_ID NUMBER (38), 
ERP_ORDER_NUM NUMBER (38),
ORDER_ITEM_ID  NUMBER (38),
ITEM  NUMBER (38),
LINE NUMBER (38)
)
ON COMMIT delete ROWS
;




create global temporary table crw_stj_law_order_item_result1(
S_ORDER_ID               NUMBER (38), 
S_ORDER_NUM              VARCHAR2 (50), 
L_ORDER_NBR              NUMBER (8), 
L_STJ_ORDER_NUM          VARCHAR2 (10), 
S_ORDER_STATUS_CD        VARCHAR2 (30), 
L_ORDER_STATUS           VARCHAR2 (40), 
S_ORIGINAL_ORDER_DATE    DATE, 
S_ADD_DATE               DATE, 
L_ORDER_DATE             DATE, 
S_EXCEPTION_IND          VARCHAR2 (1), 
ACCOUNT_ERP_NUM          VARCHAR2 (30), 
L_ACCOUNT_NUM            VARCHAR2 (9), 
S_SITE_ERP_NUM           VARCHAR2 (30), 
L_SITE_NUM               NUMBER (4), 
S_TOTAL_PRICE$            NUMBER (15,3), 
L_OPEN_ORD_AMT$           NUMBER (15,2), 
L_PASS_ORD_AMT$           NUMBER (15,2), 
S_TOTAL_HANDLE_COST$      NUMBER (15,2), 
L_MISC$                   NUMBER (15,2), 
S_TOTAL_TAX_COST$         NUMBER (15,3), 
L_TAX$                    NUMBER (15,2), 
L_ORDER_TYPE             VARCHAR2 (2), 
S_ORDER_SOURCE_CD        VARCHAR2 (30), 
L_ORDER_SOURCE           VARCHAR2 (2), 
S_ITEM_ID                NUMBER (38), 
LINE_NUM                NUMBER (38), 
ITEM_SKU_NUM             NUMBER (38), 
S_ITEM_DESC              VARCHAR2 (255), 
L_ITEM_DESC              VARCHAR2 (30), 
S_ORDER_ITEM_STATUS_CD   VARCHAR2 (30), 
L_ITEM_STATUS            VARCHAR2 (40), 
S_ITEM_UOM               VARCHAR2 (30), 
L_SELL_UOM               VARCHAR2 (4), 
S_ITEM_PRICE$             NUMBER (15,3), 
L_ITEM_PRICE$             NUMBER (13,5), 
S_ITEM_COST$              NUMBER (15,3), 
L_ITEM_COST$              NUMBER (13,5), 
S_TOTAL_QUANTITY_ORDERED  NUMBER (38), 
L_ORDER_QTY              NUMBER (38), 
S_TOTAL_QUANTITY_SHIPPED NUMBER (38), 
L_INVOICE_QTY            NUMBER (38) 
)
on commit preserve rows;

-- End Stjohn-Lawson Order Item Quantity Mismatch

-- Begin Turnaround Report

create global temporary table CRW_TURNAROUND_ORDER(
CUSTOMER      VARCHAR2 (9), 
ORDER_NBR         NUMBER (38), 
ORD_STATUS        NUMBER (38), 
ORD_DATE          DATE, 
ORD_SHIP_TO_CITY  VARCHAR2 (32), 
ORD_SHIP_TO_STATE VARCHAR2 (2), 
ORD_SHIP_TO_ZIP   VARCHAR2 (15), 
VENDOR            VARCHAR2 (32), 
QUANTITY      NUMBER (38) 
) on commit delete rows;



create global temporary table  CRW_TURNAROUND_INVOICE (
INVOICE_DTE DATE, 
CANCEL_SEQ  NUMBER (4), 
ORDER_NBR   VARCHAR2 (38), 
VENDOR      VARCHAR2 (9), 
INVOICE     VARCHAR2 (22), 
APPROVE_QTY NUMBER (38), 
LINE_STATUS   NUMBER (38) 
) on commit delete rows;


drop table CRW_TURNAROUND_RESULT;


create global temporary table CRW_TURNAROUND_RESULT (
CUSTOMER           VARCHAR2 (9), 
ORDER_NBR          NUMBER (38), 
VENDOR             VARCHAR2 (32), 
QUANTITY           NUMBER (38), 
DELIVERED_QTY      NUMBER (38), 
FIRST_DELIVERY_QTY NUMBER (38), 
ORD_DATE           DATE, 
FIRST_INVOICE_DTE  DATE, 
LAST_INVOICE_DTE   DATE, 
TUNRAROUND         NUMBER (38), 
COLPLETE_FL        VARCHAR (1) 
) on commit preserve rows;

-- End Turnaround Report

-- Begin Turnaround Detail Report

create global temporary table CRW_TURNAROUND_ORDER_DETAIL (
CUSTOMER      VARCHAR2 (9), 
ORDER_NBR         NUMBER (38), 
ORD_STATUS        NUMBER (38), 
ORD_DATE          DATE, 
ORD_SHIP_TO_CITY  VARCHAR2 (32), 
ORD_SHIP_TO_STATE VARCHAR2 (2), 
ORD_SHIP_TO_ZIP   VARCHAR2 (15), 
VENDOR            VARCHAR2 (32),
PO_NUMBER        VARCHAR2 (32),
ORDER_LINE_NBR NUMBER (38),
ORDER_LINE_STATUS NUMBER (38),
PO_LINE_NBR NUMBER (38),
ITEM           VARCHAR2 (32),
QUANTITY      NUMBER (38) 
) on commit delete rows;



create global temporary table  CRW_TURNAROUND_INVOICE_DETAIL (
INVOICE_DTE DATE, 
CANCEL_SEQ  NUMBER (4), 
ORDER_NBR   VARCHAR2 (38), 
VENDOR      VARCHAR2 (9), 
INVOICE     VARCHAR2 (22), 
APPROVE_QTY NUMBER (38), 
LINE_NBR NUMBER (38),
ITEM           VARCHAR2 (32),
LINE_STATUS   NUMBER (38) 
) on commit delete rows;


drop table CRW_TURNAROUND_RESULT;


create global temporary table CRW_TURNAROUND_RESULT_DETAIL (
CUSTOMER           VARCHAR2 (9), 
ORDER_NBR          NUMBER (38), 
VENDOR             VARCHAR2 (32), 
PO_NUMBER          VARCHAR2 (32),
ITEM               VARCHAR2 (32),
SHORT_DESC         VARCHAR2(255),
UOM               VARCHAR2 (32),
PACK               VARCHAR2 (32),
QUANTITY           NUMBER (38), 
DELIVERED_QTY      NUMBER (38), 
FIRST_DELIVERY_QTY NUMBER (38), 
ORD_DATE           DATE, 
FIRST_INVOICE_DTE  DATE, 
LAST_INVOICE_DTE   DATE, 
TUNRAROUND         NUMBER (38), 
COLPLETE_FL        VARCHAR (1),
ITEM_ID        NUMBER(38),
MANU_ID        NUMBER(38),
MANU_NAME      VARCHAR2(255)
) on commit preserve rows;

-- End Turnaround Detail Report

-- Begin Mismatch Contract Cost Report
create global temporary table CRW_cat_con (
CATALOG_ID  NUMBER (38), 
CONTRACT_ID NUMBER (38), 
ACCOUNT_ID  NUMBER (38),
ACCOUNT_NAME VARCHAR2(30),
CONTRACT_NAME VARCHAR2 (30),
CONTRACT_STATUS VARCHAR2 (30),
CATALOG_NAME VARCHAR2(30),
CATALOG_STATUS VARCHAR2(30)
) on commit delete rows;

create global temporary table CRW_mismatch_contr_cost_agr
(
ACCOUNT_ID NUMBER (38), 
ITEM_ID    NUMBER (38), 
DIST_COST  NUMBER (15,3), 
DIST_ID    NUMBER (38)
) on commit delete rows;

create global temporary table CRW_mismatch_contr_cost_res (
ACCOUNT_ID NUMBER (38),
ACCOUNT_NAME VARCHAR2(30), 
ITEM_ID    NUMBER (38),
SKU_NUM NUMBER(38),
ITEM_NAME VARCHAR2(255), 
DIST_COST  NUMBER (15,3), 
DIST_ID    NUMBER (38),
DIST_NAME  VARCHAR2(30),
CONTRACT_ID NUMBER(38),
CONTRACT_NAME VARCHAR2(30),
CONTRACT_STATUS VARCHAR2(30),
CATALOG_ID NUMBER(38),
CATALOG_NAME VARCHAR2(30),
CATALOG_STATUS VARCHAR2(30)
) on commit preserve rows;

-- End Mismatch Contract Cost Report


-- Begin Customer Invoice Verification Report

create global temporary table crw_cust_inv_verif_invoice
(
INVOICE_CUST_ID   NUMBER (38),
ACCOUNT_ID        NUMBER (38), 
SHORT_DESC        VARCHAR2 (255), 
INVOICE_NUM       VARCHAR2 (50), 
INVOICE_TYPE      VARCHAR2 (4), 
INVOICE_DATE      DATE, 
INVOICE_STATUS_CD VARCHAR2 (30)
) 
ON COMMIT delete ROWS; 
  
create global temporary table crw_cust_inv_verif_reeng
(
INVOICE_NUM VARCHAR2 (50), 
INVOICE_TYPE VARCHAR2 (4), 
AMOUNT NUMBER(15,3),
FILE_NAME   VARCHAR2 (255)
) 
ON COMMIT delete ROWS; 


create global temporary table crw_cust_inv_verif_trans
( 
INVOICE_NUM   VARCHAR2 (50), 
SET_STATUS    NUMBER (2), 
EDI_FILE_NAME VARCHAR2 (255), 
ELECTRONIC_TRANSACTION_ID NUMBER (38), 
INTERCHANGE_SENDER      VARCHAR2 (15), 
INTERCHANGE_RECEIVER    VARCHAR2 (15), 
GROUP_SENDER              VARCHAR2 (15), 
GROUP_RECEIVER            VARCHAR2 (15), 
GROUP_CONTROL_NUMBER      NUMBER (9), 
SET_TYPE                  VARCHAR2 (10), 
SET_CONTROL_NUMBER        NUMBER (9), 
CONF_EDI_FILE_NAME VARCHAR2(255));
)
ON COMMIT delete ROWS; 


create global temporary table crw_cust_inv_verif_result1
( 
ACCOUNT_ID        NUMBER (38), 
SHORT_DESC        VARCHAR2 (255), 
INVOICE_NUM       VARCHAR2 (50), 
INVOICE_TYPE      VARCHAR2 (4), 
INVOICE_DATE      DATE, 
INVOICE_STATUS_CD VARCHAR2 (30), 
SET_STATUS        NUMBER (38), 
EDI_FILE_NAME     VARCHAR2 (1000), 
FILE_VERIFICATION VARCHAR2 (30),
ACK_FILE_NAME   VARCHAR2 (255),  
ELECTRONIC_TRANSACTION_ID NUMBER (38)
)
ON COMMIT preserve ROWS; 
-- End Customer Invoice Verification Report



-- Begin Transaction Acknowledgement Report
create global temporary table crw_trans_ack
( 
INTERCHANGE_ID NUMBER (38), 
GROUP_CONTROL_NUMBER      NUMBER (9), 
ACK_EDI_FILE_NAME VARCHAR2(255)
)
ON COMMIT delete ROWS; 

create global temporary table crw_trans_ack_group
(
  INTERCHANGE_ID       NUMBER (38), 
  GROUP_CONTROL_NUMBER NUMBER (9), 
  INTERCHANGE_SENDER   VARCHAR2 (15), 
  INTERCHANGE_RECEIVER VARCHAR2 (15) 
)
ON COMMIT delete ROWS; 


create global temporary table crw_trans_ack_res
( 
ELECTRONIC_TRANSACTION_ID NUMBER (38), 
INTERCHANGE_ID            NUMBER (38), 
TRANSACTION_DATE          VARCHAR2 (10), 
GROUP_CONTROL_NUMBER        NUMBER (9), 
SET_TYPE                  VARCHAR2 (10), 
SET_CONTROL_NUMBER        NUMBER (9), 
SET_STATUS                NUMBER (2), 
TRANSACTION_PROBLEM       VARCHAR2 (27), 
KEY_STRING                VARCHAR2 (128), 
INTERCHANGE_SENDER        VARCHAR2 (15), 
INTERCHANGE_RECEIVER      VARCHAR2 (15), 
EDI_FILE_NAME             VARCHAR2 (255), 
TRADING_PARTNER           VARCHAR2 (30), 
TRADING_PARTNER_TYPE_CD   VARCHAR2 (30), 
ACK_997_FILE_NAME             VARCHAR2 (255) 
)
ON COMMIT preserve ROWS; 

-- End Transaction Acknowledgement Report
