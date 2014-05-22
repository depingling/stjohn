
--- TABLES
--POLINE_GrpOrderNumPoNum
drop table CRT_POLINE_GrpOrderNumPoNum;
create table CRT_POLINE_GrpOrderNumPoNum as
SELECT ORDER_NBR, PO_NUMBER
FROM CRS_POLINE
GROUP BY ORDER_NBR, PO_NUMBER;


-- mkPoApprove - POAPPROVE
drop table CRT_POAPPROVE;

create table CRT_POAPPROVE as
SELECT 
po.COMPANY,VENDOR,po.INVOICE,po.SUFFIX,po.PO_NUMBER,po.PO_RELEASE,po.LINE_NBR,po.AOC_CODE,po.OPER_ID,
po.ITEM_TYPE,po.ITEM,po.APPROVE_QTY,po.ENT_UNIT_CST,po.COST_ADJ_FL,po.BATCH_NBR,po.DIST_DATE,
po.TAX_CODE,po.TAX_AMOUNT,po.TOT_DIST_AMT,po.TOT_BASE_AMT,po.UPDATE_DATE,
po.UPDATE_TIME,po.R_STATUS,po.VBUY_APPR_QTY,po.NO_TAX_FLAG,po.POASET3_SS_SW,po.POASET4_SS_SW,po.POASET5_SS_SW,
pl.ORDER_NBR
FROM CRS_POAPPROVE po, CRT_POLINE_GrpOrderNumPoNum pl
where po.PO_NUMBER = pl.PO_NUMBER;

--query PoApprove_GroupByOrderNumVendorInvoice
drop table CRT_PoApprove_OrderVenInvoice;

create table CRT_PoApprove_OrderVenInvoice as
SELECT ORDER_NBR as "Expr1", 
VENDOR as "Expr2", 
INVOICE as "Expr3"
FROM CRT_POAPPROVE
GROUP BY ORDER_NBR, VENDOR, INVOICE;

-- mkApinvoice - APINVOICE
drop table CRT_APINVOICE;

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

-- mkPoItemVen - POITEMVEN
drop table CRT_POITEMVEN;

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

-- mkPoLine - POLINE
drop table CRT_POLINE;

create table CRT_POLINE as
SELECT CRS_POLINE.*, Trim(ITEM) as "CW SKU", Trim(VENDOR) as "Vendor ID" 
FROM CRS_POLINE;

create index CRT_POLINE_I1 on CRT_POLINE (ORDER_NBR,ITEM); 

-- query PoLineGrpOnOrderNumAndPoNum
drop table CRT_PoLineOrderNumPoNum;

create table CRT_PoLineOrderNumPoNum as
SELECT ORDER_NBR, PO_NUMBER
FROM CRS_POLINE
GROUP BY ORDER_NBR, PO_NUMBER
HAVING ORDER_NBR Is Not Null;

-- mkPurchOrder - PURCHORDER
drop table CRT_PURCHORDER;

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

-- mkShipTo
drop table CRT_SHIPTO;

create table CRT_SHIPTO as
SELECT CRS_SHIPTO.* 
FROM CRS_SHIPTO;

-- mkLkpVendor lkpVendor
drop table CRT_VENDOR;

create table CRT_VENDOR as
SELECT 
  BUS_ENTITY_ID  as VENDOR_BUS_ENTITY_ID, 
  ERP_NUM  as "VendorID", 
  SHORT_DESC as VENDOR_NAME
FROM CRS_BUS_ENTITY
WHERE BUS_ENTITY_TYPE_CD='DISTRIBUTOR';

-- mkCustDesc CUSTDESC
drop table CRT_CUSTDESC;

create table CRT_CUSTDESC as
SELECT 
 Trim(CUSTOMER) as "Account ID", 
 Trim(SEARCH_NAME) as "Account Name"
FROM CRS_CUSTDESC;

-- Query - qryOeInvcline
drop table CRT_Q_OeInvcline;

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

-- mkOeInvcLine OEINVCLINE
drop table CRT_OEINVCLINE;

create table CRT_OEINVCLINE as
SELECT 
  CRT_Q_OeInvcline.*, 
  'Category'||"Major Clase ID" AS "Major Class", 
  'Category'||"Minor Class ID" AS "Minor Class" 
FROM CRT_Q_OeInvcline;

drop table CRT_COLINE;

create table CRT_COLINE as
  SELECT CRS_COLINE.*, Trim(VENDOR) as "Vendor ID" 
FROM CRS_COLINE;

create index CRT_COLINE_I1 on CRT_COLINE (ORDER_NBR,ITEM); 

-- Query qryItemCategories 
drop table CRT_Q_ItemCategories;

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

-- Query ItemManufacturer 
drop table CRT_ITEM_MANUFACTURER;

create table CRT_ITEM_MANUFACTURER as
SELECT CRS_ITEM_MAPPING.*, CRS_BUS_ENTITY.SHORT_DESC AS "Manufacturer"
FROM CRS_ITEM_MAPPING,  CRS_BUS_ENTITY 
where CRS_ITEM_MAPPING.BUS_ENTITY_ID = CRS_BUS_ENTITY.BUS_ENTITY_ID
  and CRS_ITEM_MAPPING.ITEM_MAPPING_CD='ITEM_MANUFACTURER';

-- mkItems - tmpItems
drop table CRT_ITEMS;

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

-- mkDistributorItems - tmpDistributorItems
drop table CRT_DistributorItems;

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

-- mkContracts - tmpContracts
drop table CRT_Contracts;

create table CRT_Contracts as
SELECT 
  CRS_CONTRACT.CONTRACT_ID, 
  CRS_CONTRACT.SHORT_DESC as "Contract Description", 
  CRS_CONTRACT.CATALOG_ID, 
  CRS_CATALOG.SHORT_DESC as "Catalog Name", 
  CRS_CONTRACT.CONTRACT_STATUS_CD 
FROM CRS_CONTRACT, CRS_CATALOG 
 where CRS_CONTRACT.CATALOG_ID = CRS_CATALOG.CATALOG_ID;

-- mkBusEntity - tmpBusEntity
drop table CRT_BusEntity;

create table CRT_BusEntity as
  SELECT CRS_BUS_ENTITY.* 
FROM CRS_BUS_ENTITY;

-- qryContractCatalogs
drop table CRT_Q_ContractCatalogs0;

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

drop table CRT_Q_ContractCatalogs;

create table CRT_Q_ContractCatalogs as
SELECT 
  CRT_Q_ContractCatalogs0.*, 
  CRS_ITEM_MAPPING.ITEM_NUM as "Dist SKU", 
  CRS_ITEM_MAPPING.ITEM_UOM as "Dist UOM", 
  CRS_ITEM_MAPPING.ITEM_PACK as "Dist Pack"
FROM  CRT_Q_ContractCatalogs0, CRS_ITEM_MAPPING
 where  CRT_Q_ContractCatalogs0.ITEM_ID = CRS_ITEM_MAPPING.ITEM_ID (+)
  and   CRT_Q_ContractCatalogs0.DIST_BUS_ENTITY_ID = CRS_ITEM_MAPPING.BUS_ENTITY_ID(+);


-- mkContractItems - tmpContractItems
drop table CRT_ContractItems;

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

-- mkAccount - tmpAccount
drop table CRT_Account;

create table CRT_Account as
SELECT 
  CRS_BUS_ENTITY.BUS_ENTITY_ID, 
  CRS_BUS_ENTITY.SHORT_DESC, 
  CRS_BUS_ENTITY.BUS_ENTITY_TYPE_CD, 
  CRS_BUS_ENTITY.BUS_ENTITY_STATUS_CD, 
  CRS_BUS_ENTITY.ERP_NUM as "ERP Account Num" 
FROM CRS_BUS_ENTITY
WHERE CRS_BUS_ENTITY.BUS_ENTITY_TYPE_CD='ACCOUNT';

-- mkUser - tmpUser
drop table CRT_User;

create table CRT_User as
SELECT 
  CRS_USER.USER_ID, 
  CRS_USER.FIRST_NAME, 
  CRS_USER.LAST_NAME, 
  CRS_USER.USER_TYPE_CD, 
  CRS_USER.USER_STATUS_CD 
FROM CRS_USER;

-- mkOeInvoice - OEINVOICE
drop table CRT_OEINVOICE;

create table CRT_OEINVOICE as
  SELECT CRS_OEINVOICE.* 
FROM CRS_OEINVOICE;

-- mkUserAssociation -  tmpUserAssociation
drop table CRT_UserAssociation;

create table CRT_UserAssociation as
SELECT CRS_USER_ASSOC.* 
FROM CRS_USER_ASSOC;

-- mkBusEntityAssociation -  tmpBusEntityAssociations
drop table CRT_BusEntityAssociations;

create table CRT_BusEntityAssociations as
  SELECT CRS_BUS_ENTITY_ASSOC.* 
FROM CRS_BUS_ENTITY_ASSOC;

-- mkCatalog -  tmpCatalog
drop table CRT_Catalog;

create table CRT_Catalog as
  SELECT CRS_CATALOG.*
FROM CRS_CATALOG;

-- mkCatalogAssociations - tmpCatalogAssociations
drop table CRT_CatalogAssociations;

create table CRT_CatalogAssociations as
  SELECT CRS_CATALOG_ASSOC.* 
FROM CRS_CATALOG_ASSOC;

-- mkCwManufacturer - CW_MANUFACTURER
drop table CRT_CW_MANUFACTURER;

create table CRT_CW_MANUFACTURER as
SELECT 
CRS_BUS_ENTITY.BUS_ENTITY_ID AS ID, 
CRS_BUS_ENTITY.SHORT_DESC AS NAME 
FROM CRS_BUS_ENTITY
WHERE CRS_BUS_ENTITY.BUS_ENTITY_TYPE_CD='MANUFACTURER'
  AND CRS_BUS_ENTITY.BUS_ENTITY_STATUS_CD='ACTIVE';

-- mkCatalogStructure - tmpCatalogStructure
drop table CRT_CatalogStructure;

create table CRT_CatalogStructure as
  SELECT CRS_CATALOG_STRUCTURE.* 
FROM CRS_CATALOG_STRUCTURE;

-- mkOrder - tmpOrder
drop table CRT_Order;

create table CRT_Order as
  SELECT CRS_ORDER.* 
FROM CRS_ORDER;

create index CRT_ORDER_I1 on CRT_ORDER (ERP_ORDER_NUM);

-- mkOrderItem - tmpOrderItem
drop table CRT_OrderItem;

create table CRT_OrderItem as
  SELECT CRS_ORDER_ITEM.* 
FROM CRS_ORDER_ITEM;

-- mkCategory - tmpCategory
drop table CRT_Category;

create table CRT_Category as
SELECT 
  CRS_ITEM.ITEM_ID as "Category ID", 
  CRS_ITEM.SHORT_DESC as "Category Name"
FROM CRS_ITEM, CRS_CATALOG_STRUCTURE
where CRS_ITEM.ITEM_ID = CRS_CATALOG_STRUCTURE.ITEM_ID
  and CRS_ITEM.ITEM_TYPE_CD='CATEGORY'
  and CRS_CATALOG_STRUCTURE.CATALOG_ID=4;

-- mkSites - tmpSites
drop table crt_site;

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

-- mkAccountContracts - tmpAccountContracts
drop table CRT_AccountContracts;

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

-- mkProperty - tmpProperty
drop table CRT_Property;

create table CRT_Property as
  SELECT CRS_PROPERTY.* 
FROM CRS_PROPERTY;

-- mkTargetFacility - TargetFacilty
drop table CRT_TargetFacilty;

create table CRT_TargetFacilty AS
SELECT 
  CRS_PROPERTY.*, 
  CRS_SITE_ACCOUNT.* 
FROM CRS_PROPERTY,CRS_SITE_ACCOUNT
 where CRS_PROPERTY.BUS_ENTITY_ID = CRS_SITE_ACCOUNT.SITE_ID
   and CRS_PROPERTY.SHORT_DESC='Target Facility';

create index crt_TargetFacilty on crt_TargetFacilty(ACCOUNT_ERP_NUM,SITE_ERP_NUM);


-- mkFacilityType - FacilityType
drop table CRT_FacilityType;

create table CRT_FacilityType as
SELECT 
  CRS_PROPERTY.*, 
  CRS_BUS_ENTITY.SHORT_DESC as "Site Name"
FROM CRS_PROPERTY, CRS_BUS_ENTITY
 where CRS_PROPERTY.BUS_ENTITY_ID = CRS_BUS_ENTITY.BUS_ENTITY_ID
   and CRS_PROPERTY.SHORT_DESC='Facility Type:';

drop table CRT_CUSTORDER;

create table CRT_CUSTORDER as
SELECT 
  CRS_CUSTORDER.*, 
  Trim(CUSTOMER) as "Account Number" 
FROM CRS_CUSTORDER;

create index CRT_CUSTORDER_I1 on CRT_CUSTORDER (ORDER_DATE);


-- mkLkpOpenPoItems - lkpOpenPoItems
drop table CRT_OpenPoItems;

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


-- lkpUSPSFacilityType
drop table crt_USPSFacilityType;

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

-- lkpFedStripAreaAndDistrict
drop table crt_FedStripAreaAndDistrict;

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

-- Specific Site data
drop table crt_site_data;


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


-------------------------------------------------
--- Views 
drop view crv_poline;

create view crv_poline as 
select * from crt_poline where trim(translate("CW SKU",'1234567890','          ')) is null;

--- Order tracker report
drop view CRV_QryCoLine;

create view CRV_QryCoLine as
SELECT 
CRT_COLINE.ORDER_NBR, 
CRT_COLINE.LINE_NBR, 
CRT_COLINE.ITEM, 
CRT_COLINE.ORDER_QTY, 
SELL_UNIT_PRC*ORDER_QTY as "Line Item Sell Price", 
UNIT_COST*ORDER_QTY as "Line Item Cost"
FROM CRT_COLINE;

drop view CRV_QryPoLine;

create view CRV_QryPoLine as
SELECT 
CRT_POLINE.ORDER_NBR, 
CRT_POLINE.PO_NUMBER, 
CRT_POLINE.LINE_NBR, 
CRT_POLINE.ITEM, 
CRT_POLINE.QUANTITY, 
Trim(VENDOR) AS "Vendor ID"
FROM CRT_POLINE;


drop view CRV_QryOrderPos;

create view CRV_QryOrderPos as
SELECT 
CRV_QryPoLine.ORDER_NBR, 
CRV_QryPoLine.PO_NUMBER, 
CRV_QryPoLine."Vendor ID", 
min(CRT_Vendor.VENDOR_NAME) as "Vendor Name", 
Sum(CRV_QryCoLine.ORDER_QTY) as "Num PO Items", 
Sum(CRV_QryCoLine."Line Item Sell Price") as "PO Sell Price", 
Sum(CRV_QryCoLine."Line Item Cost") as "PO Cost"
FROM CRV_QryPoLine, CRV_QryCoLine, CRT_Vendor
WHERE 1=1 
AND CRV_QryPoLine.ORDER_NBR = CRV_QryCoLine.ORDER_NBR(+)
AND CRV_QryPoLine.ITEM = CRV_QryCoLine.ITEM(+)
AND CRV_QryPoLine."Vendor ID" = CRT_Vendor."VendorID"(+)
GROUP BY 
CRV_QryPoLine.ORDER_NBR, 
CRV_QryPoLine.PO_NUMBER, 
CRV_QryPoLine."Vendor ID";


drop view CRV_OrderCost;

create view CRV_OrderCost as
SELECT CRT_POLINE.ORDER_NBR, 
Sum(CRT_POLINE.QUANTITY) AS "Total qty of items in order",
Sum(QUANTITY*ENT_UNIT_CST) AS "Total Cost"
FROM CRT_POLINE
GROUP BY CRT_POLINE.ORDER_NBR;


quit;