
--- TABLES
--POLINE_GrpOrderNumPoNum
DROP TABLE CRT_POLINE_GrpOrderNumPoNum;
CREATE TABLE CRT_POLINE_GrpOrderNumPoNum AS
SELECT ORDER_NBR, PO_NUMBER FROM crs_purchorder;


-- mkPoApprove - POAPPROVE
DROP TABLE CRT_POAPPROVE;

CREATE TABLE CRT_POAPPROVE AS
SELECT
po.COMPANY,VENDOR,po.INVOICE,po.PO_NUMBER,po.LINE_NBR,po.ITEM,po.APPROVE_QTY,po.ENT_UNIT_CST,po.BATCH_NBR,
po.R_STATUS,(po.approve_qty) AS VBUY_APPR_QTY,
pl.ORDER_NBR
FROM CRS_POAPPROVE po, CRT_POLINE_GrpOrderNumPoNum pl
WHERE po.PO_NUMBER = pl.PO_NUMBER;


--query PoApprove_GroupByOrderNumVendorInvoice
DROP TABLE CRT_PoApprove_OrderVenInvoice;

CREATE TABLE CRT_PoApprove_OrderVenInvoice AS
SELECT ORDER_NBR AS "Expr1", 
VENDOR AS "Expr2", 
INVOICE AS "Expr3"
FROM CRT_POAPPROVE
GROUP BY ORDER_NBR, VENDOR, INVOICE;

-- mkApinvoice - APINVOICE
DROP TABLE CRT_APINVOICE;

CREATE TABLE CRT_APINVOICE AS
SELECT  
  CRS_APINVOICE.COMPANY,
  CRS_APINVOICE.VENDOR,
  CRS_APINVOICE.INVOICE,
  CRS_APINVOICE.BATCH_NUM,
  CRS_APINVOICE.VOUCHER_NBR,
  CRS_APINVOICE.INVOICE_DTE,
  CRS_APINVOICE.PO_NUM,
  CRS_APINVOICE.BASE_INV_AMT,
  CRS_APINVOICE.TRAN_INV_AMT,
  CRS_APINVOICE.CREATE_DATE,
  CRS_APINVOICE.PRODUCT_TOTAL,
  Trim(CRS_APINVOICE.VENDOR) "Vendor ID",
  CRT_PoApprove_OrderVenInvoice."Expr1" AS ORDER_NBR 
FROM CRS_APINVOICE, CRT_PoApprove_OrderVenInvoice 
WHERE  CRS_APINVOICE.VENDOR = CRT_PoApprove_OrderVenInvoice."Expr2"
  AND  CRS_APINVOICE.INVOICE = CRT_PoApprove_OrderVenInvoice."Expr3";

-- mkPoItemVen - POITEMVEN
DROP TABLE CRT_POITEMVEN;

CREATE TABLE CRT_POITEMVEN AS
SELECT CRS_POITEMVEN.COMPANY, CRS_POITEMVEN.ITEM, 
       Trim(VENDOR) AS "Vendor ID", CRS_POITEMVEN.VEN_ITEM, 
     CRS_POITEMVEN.VEN_ITEM_DESC, CRS_POITEMVEN.VBUY_UOM, 
	   Trim(ITEM) AS "Item Number" 
FROM CRS_POITEMVEN;

-- mkPoLine - POLINE
DROP TABLE CRT_POLINE;

CREATE TABLE CRT_POLINE AS
SELECT CRS_POLINE.*, Trim(ITEM) AS "CW SKU", Trim(VENDOR) AS "Vendor ID" 
FROM CRS_POLINE;

CREATE INDEX CRT_POLINE_I1 ON CRT_POLINE (ORDER_NBR,ITEM); 

-- query PoLineGrpOnOrderNumAndPoNum
DROP TABLE CRT_PoLineOrderNumPoNum;

CREATE TABLE CRT_PoLineOrderNumPoNum AS
SELECT ORDER_NBR, PO_NUMBER
FROM CRS_POLINE
GROUP BY ORDER_NBR, PO_NUMBER
HAVING ORDER_NBR IS NOT NULL;

-- mkPurchOrder - PURCHORDER
DROP TABLE CRT_PURCHORDER;

CREATE TABLE CRT_PURCHORDER AS
SELECT  
     crs_purchorder.COMPANY, crs_purchorder.PO_NUMBER, 
	   crs_purchorder.VENDOR, 
	   crs_purchorder.PO_DATE, crs_purchorder.CURRENCY_CODE,
	   crs_purchorder.SH_NAME, crs_purchorder.SH_ADDR1, crs_purchorder.SH_ADDR2, 
	   crs_purchorder.SH_ADDR3, crs_purchorder.SH_ADDR4, crs_purchorder.SH_CITY_ADDR5, 
	   crs_purchorder.SH_STATE_PROV, crs_purchorder.SH_POST_CODE, crs_purchorder.SH_COUNTRY, 
	   crs_purchorder.NBR_LINES, 
	   crs_purchorder.CLOSED_LINES, 
	   crs_purchorder.TOT_PRD_AMT, crs_purchorder.TOT_ORDER_AMT, crs_purchorder.LAST_LINE_NBR, 
	   crs_purchorder.RELEASED_FL, 
	   crs_purchorder.PRINTED_FL, crs_purchorder.REVISED_FL, crs_purchorder.CANCELLED_FL, 
	   crs_purchorder.CLOSED_FL, crs_purchorder.CLOSE_DATE, 
  Trim(crs_purchorder.VENDOR) AS "Vendor ID", 
  CRT_PoLineOrderNumPoNum.ORDER_NBR 
FROM CRS_PURCHORDER, CRT_PoLineOrderNumPoNum
WHERE CRS_PURCHORDER.PO_NUMBER = CRT_PoLineOrderNumPoNum.PO_NUMBER;

-- mkShipTo
DROP TABLE CRT_SHIPTO;

CREATE TABLE CRT_SHIPTO AS
SELECT CRS_SHIPTO.* 
FROM CRS_SHIPTO;

-- mkLkpVendor lkpVendor
DROP TABLE CRT_VENDOR;

CREATE TABLE CRT_VENDOR AS
SELECT 
  BUS_ENTITY_ID  AS VENDOR_BUS_ENTITY_ID, 
  ERP_NUM  AS "VendorID", 
  SHORT_DESC AS VENDOR_NAME
FROM CRS_BUS_ENTITY
WHERE BUS_ENTITY_TYPE_CD='DISTRIBUTOR';

-- mkCustDesc CUSTDESC
DROP TABLE CRT_CUSTDESC;

CREATE TABLE CRT_CUSTDESC AS
SELECT 
 Trim(CUSTOMER) AS "Account ID", 
 Trim(SEARCH_NAME) AS "Account Name"
FROM CRS_CUSTDESC;

-- Query - qryOeInvcline
DROP TABLE CRT_Q_OeInvcline;

CREATE TABLE CRT_Q_OeInvcline AS
SELECT 
  INVC_NUMBER AS "Invoice Number", 
  LINE_NBR  AS "Line Number", 
  Trim(ITEM) AS "Item Number", 
  DESCRIPTION, 
  ORDER_NBR  AS "Order Number", 
  QUANTITY AS "Qty", 
  SELL_UOM AS "Invoice UOM", 
  UNIT_PRICE AS "Unit Price", 
  UNIT_COST AS "Unit Cost", 
  LINE_GRS_CURR AS "Extended Item Price"
FROM CRS_OEINVCLINE;

-- mkOeInvcLine OEINVCLINE
DROP TABLE CRT_OEINVCLINE;


CREATE TABLE CRT_OEINVCLINE AS
SELECT 
  CRT_Q_OeInvcline.* 
FROM CRT_Q_OeInvcline;

DROP TABLE CRT_COLINE;

CREATE TABLE CRT_COLINE AS
  SELECT CRS_COLINE.*, Trim(VENDOR) AS "Vendor ID" 
FROM CRS_COLINE;

CREATE INDEX CRT_COLINE_I1 ON CRT_COLINE (ORDER_NBR,ITEM); 

-- Query qryItemCategories 
DROP TABLE CRT_Q_ItemCategories;

CREATE TABLE CRT_Q_ItemCategories AS
SELECT 
  CRS_ITEM_ASSOC.ITEM1_ID, 
  CRS_ITEM.SHORT_DESC AS "Category Name", 
  CRS_ITEM.ITEM_ID AS "Category Item ID", 
  CRS_ITEM_ASSOC.CATALOG_ID
FROM CRS_ITEM_ASSOC, CRS_ITEM, CRS_CATALOG
WHERE CRS_ITEM_ASSOC.ITEM2_ID = CRS_ITEM.ITEM_ID
  AND CRS_ITEM_ASSOC.CATALOG_ID=CRS_CATALOG.catalog_id
  AND CRS_CATALOG.catalog_type_cd = 'STORE'
  AND CRS_CATALOG.catalog_status_cd = 'ACTIVE'
  AND CRS_ITEM_ASSOC.ITEM_ASSOC_CD='PRODUCT_PARENT_CATEGORY';

-- Query ItemManufacturer 
DROP TABLE CRT_ITEM_MANUFACTURER;

CREATE TABLE CRT_ITEM_MANUFACTURER AS
SELECT CRS_ITEM_MAPPING.*, CRS_BUS_ENTITY.SHORT_DESC AS "Manufacturer"
FROM CRS_ITEM_MAPPING,  CRS_BUS_ENTITY 
WHERE CRS_ITEM_MAPPING.BUS_ENTITY_ID = CRS_BUS_ENTITY.BUS_ENTITY_ID
  AND CRS_ITEM_MAPPING.ITEM_MAPPING_CD='ITEM_MANUFACTURER';

-- mkItems - tmpItems
DROP TABLE CRT_ITEMS;

CREATE TABLE CRT_ITEMS AS
SELECT 
CRS_ITEM.ITEM_ID, CRS_ITEM.SKU_NUM, CRS_ITEM.SHORT_DESC, CRS_ITEM.LONG_DESC, 
itemMetaColor.clw_value AS "Color", 
itemMetaDED.clw_value AS "DED", 
itemMetaImage.clw_value AS "Image", 
itemMetaListPrice.clw_value AS "List Price", 
itemMetaMSDS.clw_value AS "MSDS", 
itemMetaPack.clw_value AS "Pack", 
itemMetaPackageUPC.clw_value "Package UPC", 
itemMetaProdSpec.clw_value AS "Product Specification", 
itemMetaShipWeight.clw_value AS "Ship Weight", 
itemMetaSize.clw_value "Size", 
itemMetaUNSPSC.clw_value "UNSPSC Code", 
itemMetaUOM.clw_value AS "UOM", 
itemMetaUPC.clw_value AS "UPC", 
ItemManufacturer.BUS_ENTITY_ID AS "Manufacturer Bus Entity ID", 
ItemManufacturer."Manufacturer" AS "Manufacturer", 
ItemManufacturer.ITEM_NUM AS "Manu SKU", 
CRT_Q_ItemCategories."Category Item ID" AS "Category Item ID", 
CRT_Q_ItemCategories."Category Name" AS "Category Name",
ItemManufacturer.bus_entity_id as "Manufacturer ID"
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
CRS_CATALOG_STRUCTURE, CRS_CATALOG storeCatalog
WHERE CRS_ITEM.ITEM_TYPE_CD='PRODUCT'
  AND CRS_CATALOG_STRUCTURE.CATALOG_STRUCTURE_CD='CATALOG_PRODUCT'
  AND storeCatalog.catalog_type_cd = 'STORE' 
  AND storeCatalog.catalog_status_cd = 'ACTIVE'
  AND CRS_CATALOG_STRUCTURE.CATALOG_ID=storeCatalog.catalog_id
  AND CRS_ITEM.ITEM_ID = CRS_CATALOG_STRUCTURE.ITEM_ID
  AND CRS_ITEM.ITEM_ID = itemMetaColor.ITEM_ID (+)
  AND itemMetaColor.name_value(+) = 'COLOR'
  AND CRS_ITEM.ITEM_ID = itemMetaDED.ITEM_ID (+)
  AND itemMetaDED.name_value(+) = 'DED' 
  AND CRS_ITEM.ITEM_ID = itemMetaImage.ITEM_ID (+)
  AND itemMetaImage.name_value(+) = 'IMAGE'
  AND CRS_ITEM.ITEM_ID = itemMetaListPrice.ITEM_ID(+)
  AND itemMetaListPrice.name_value(+) = 'LIST_PRICE'
  AND CRS_ITEM.ITEM_ID = itemMetaMSDS.ITEM_ID(+)
  AND itemMetaMSDS.name_value(+) = 'MSDS'
  AND CRS_ITEM.ITEM_ID = itemMetaPack.ITEM_ID(+)
  AND itemMetaPack.name_value(+) = 'PACK'
  AND CRS_ITEM.ITEM_ID = itemMetaPackageUPC.ITEM_ID(+)
  AND itemMetaPackageUPC.name_value(+) = 'PKG_UPC_NUM'
  AND CRS_ITEM.ITEM_ID = itemMetaProdSpec.ITEM_ID(+)
  AND itemMetaProdSpec.name_value (+)= 'SPEC'
  AND CRS_ITEM.ITEM_ID = itemMetaShipWeight.ITEM_ID(+)
  AND itemMetaShipWeight.name_value(+) = 'SHIP_WEIGHT'
  AND CRS_ITEM.ITEM_ID = itemMetaSize.ITEM_ID(+)
  AND itemMetaSize.name_value(+) = 'SIZE'
  AND CRS_ITEM.ITEM_ID = itemMetaUNSPSC.ITEM_ID(+)
  AND itemMetaUNSPSC.name_value(+) = 'UNSPSC_CD'
  AND CRS_ITEM.ITEM_ID = itemMetaUOM.ITEM_ID(+)
  AND itemMetaUOM.name_value(+) = 'UOM'
  AND CRS_ITEM.ITEM_ID = itemMetaUPC.ITEM_ID(+)
  AND itemMetaUPC.name_value(+) = 'UPC_NUM'
  AND CRS_ITEM.ITEM_ID = ItemManufacturer.ITEM_ID(+)
  AND CRS_ITEM.ITEM_ID = CRT_Q_ItemCategories.ITEM1_ID(+);

-- mkDistributorItems - tmpDistributorItems
DROP TABLE CRT_DistributorItems;

CREATE TABLE CRT_DistributorItems AS
SELECT 
  CRS_BUS_ENTITY.BUS_ENTITY_ID, 
  CRS_BUS_ENTITY.SHORT_DESC AS "Distributor Name", 
  CRS_BUS_ENTITY.ERP_NUM AS "Distributor ID", 
  CRS_ITEM_MAPPING.ITEM_NUM AS "Distributor SKU", 
  CRS_ITEM_MAPPING.ITEM_ID, 
  CRS_ITEM_MAPPING.ITEM_UOM AS "Dist UOM", 
  CRS_ITEM_MAPPING.ITEM_PACK AS "Dist_Pack" 
FROM CRS_BUS_ENTITY, CRS_ITEM_MAPPING
 WHERE CRS_BUS_ENTITY.BUS_ENTITY_ID = CRS_ITEM_MAPPING.BUS_ENTITY_ID
   AND CRS_BUS_ENTITY.BUS_ENTITY_TYPE_CD='DISTRIBUTOR'
   AND CRS_ITEM_MAPPING.ITEM_MAPPING_CD='ITEM_DISTRIBUTOR';

-- mkContracts - tmpContracts
DROP TABLE CRT_Contracts;

CREATE TABLE CRT_Contracts AS
SELECT 
  CRS_CONTRACT.CONTRACT_ID, 
  CRS_CONTRACT.SHORT_DESC AS "Contract Description", 
  CRS_CONTRACT.CATALOG_ID, 
  CRS_CATALOG.SHORT_DESC AS "Catalog Name", 
  CRS_CONTRACT.CONTRACT_STATUS_CD 
FROM CRS_CONTRACT, CRS_CATALOG 
 WHERE CRS_CONTRACT.CATALOG_ID = CRS_CATALOG.CATALOG_ID;

-- mkBusEntity - tmpBusEntity
DROP TABLE CRT_BusEntity;

CREATE TABLE CRT_BusEntity AS
  SELECT CRS_BUS_ENTITY.* 
FROM CRS_BUS_ENTITY;

-- qryContractCatalogs
DROP TABLE CRT_Q_ContractCatalogs0;

CREATE TABLE CRT_Q_ContractCatalogs0 AS
SELECT 
  CRS_CONTRACT.CONTRACT_ID, 
  CRS_CONTRACT.SHORT_DESC AS "Contract Name", 
  CRS_CONTRACT.CATALOG_ID, 
  CRS_CATALOG.SHORT_DESC AS "Catalog Name", 
  CRS_BUS_ENTITY.SHORT_DESC AS "Distributor Name", 
  CRS_ITEM.SKU_NUM, 
  CRS_ITEM.SHORT_DESC AS "Item Name", 
  CRS_CATALOG_STRUCTURE.ITEM_ID, 
  CRS_BUS_ENTITY.BUS_ENTITY_ID AS DIST_BUS_ENTITY_ID 
FROM CRS_CONTRACT, CRS_CATALOG, CRS_BUS_ENTITY, CRS_ITEM, CRS_CATALOG_STRUCTURE
WHERE  CRS_CATALOG.CATALOG_ID = CRS_CATALOG_STRUCTURE.CATALOG_ID
  AND  CRS_CONTRACT.CATALOG_ID = CRS_CATALOG.CATALOG_ID
  AND  CRS_CATALOG_STRUCTURE.ITEM_ID = CRS_ITEM.ITEM_ID
  AND  CRS_CATALOG_STRUCTURE.BUS_ENTITY_ID = CRS_BUS_ENTITY.BUS_ENTITY_ID(+);

DROP TABLE CRT_Q_ContractCatalogs;

CREATE TABLE CRT_Q_ContractCatalogs AS
SELECT 
  CRT_Q_ContractCatalogs0.*, 
  CRS_ITEM_MAPPING.ITEM_NUM AS "Dist SKU", 
  CRS_ITEM_MAPPING.ITEM_UOM AS "Dist UOM", 
  CRS_ITEM_MAPPING.ITEM_PACK AS "Dist Pack"
FROM  CRT_Q_ContractCatalogs0, CRS_ITEM_MAPPING
 WHERE  CRT_Q_ContractCatalogs0.ITEM_ID = CRS_ITEM_MAPPING.ITEM_ID (+)
  AND   CRT_Q_ContractCatalogs0.DIST_BUS_ENTITY_ID = CRS_ITEM_MAPPING.BUS_ENTITY_ID(+);


-- mkContractItems - tmpContractItems
DROP TABLE CRT_ContractItems;

CREATE TABLE CRT_ContractItems AS
SELECT 
  CRS_CONTRACT_ITEM.CONTRACT_ID, 
  CRS_CONTRACT_ITEM.ITEM_ID, 
  CRT_Q_ContractCatalogs.SKU_NUM AS "CW SKU", 
  CRT_Items.SHORT_DESC AS "Item Name", 
  CRS_CONTRACT_ITEM.AMOUNT AS "Sell Price", 
  CRS_CONTRACT_ITEM.DIST_COST AS "Cost to CW", 
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
WHERE CRS_CONTRACT_ITEM.ITEM_ID = CRT_Items.ITEM_ID 
  AND CRS_CONTRACT_ITEM.ITEM_ID = CRT_Q_ContractCatalogs.ITEM_ID 
  AND CRS_CONTRACT_ITEM.CONTRACT_ID = CRT_Q_ContractCatalogs.CONTRACT_ID;

-- mkAccount - tmpAccount
DROP TABLE CRT_Account;

CREATE TABLE CRT_Account AS
SELECT 
  CRS_BUS_ENTITY.BUS_ENTITY_ID, 
  CRS_BUS_ENTITY.SHORT_DESC, 
  CRS_BUS_ENTITY.BUS_ENTITY_TYPE_CD, 
  CRS_BUS_ENTITY.BUS_ENTITY_STATUS_CD, 
  CRS_BUS_ENTITY.ERP_NUM AS "ERP Account Num" 
FROM CRS_BUS_ENTITY
WHERE CRS_BUS_ENTITY.BUS_ENTITY_TYPE_CD='ACCOUNT';


-- mkAccount with dups 
DROP TABLE CRT_Account_With_Dups;

CREATE TABLE CRT_Account_With_Dups AS
SELECT
  CRS_BUS_ENTITY.BUS_ENTITY_ID,
  CRS_BUS_ENTITY.SHORT_DESC,
  CRS_BUS_ENTITY.BUS_ENTITY_TYPE_CD,
  CRS_BUS_ENTITY.BUS_ENTITY_STATUS_CD,
  CRS_BUS_ENTITY.ERP_NUM AS "ERP Account Num"
FROM CRS_BUS_ENTITY
WHERE CRS_BUS_ENTITY.BUS_ENTITY_TYPE_CD='ACCOUNT';

INSERT INTO CRT_Account_With_Dups (BUS_ENTITY_ID,SHORT_DESC,BUS_ENTITY_TYPE_CD,
BUS_ENTITY_STATUS_CD,"ERP Account Num")
SELECT
  CRS_BUS_ENTITY.BUS_ENTITY_ID,
  CRS_BUS_ENTITY.SHORT_DESC,
  CRS_BUS_ENTITY.BUS_ENTITY_TYPE_CD,
  CRS_BUS_ENTITY.BUS_ENTITY_STATUS_CD,
  CRS_PROPERTY.clw_value AS "ERP Account Num"
 FROM CRS_PROPERTY , CRS_BUS_ENTITY
WHERE trim(clw_value) IS NOT NULL AND property_status_cd = 'ACTIVE' AND 
property_type_cd LIKE 'RESALE_ACCOUNT_ERP_NUM'
AND CRS_PROPERTY.bus_entity_id = CRS_BUS_ENTITY.bus_entity_id;

-- mkUser - tmpUser
DROP TABLE CRT_User;

CREATE TABLE CRT_User AS
SELECT 
  CRS_USER.USER_ID, 
  CRS_USER.FIRST_NAME, 
  CRS_USER.LAST_NAME, 
  CRS_USER.USER_TYPE_CD, 
  CRS_USER.USER_STATUS_CD 
FROM CRS_USER;

-- mkOeInvoice - OEINVOICE
DROP TABLE CRT_OEINVOICE;

CREATE TABLE CRT_OEINVOICE AS
  SELECT CRS_OEINVOICE.* 
FROM CRS_OEINVOICE;

-- mkUserAssociation -  tmpUserAssociation
DROP TABLE CRT_UserAssociation;

CREATE TABLE CRT_UserAssociation AS
SELECT CRS_USER_ASSOC.* 
FROM CRS_USER_ASSOC;

-- mkBusEntityAssociation -  tmpBusEntityAssociations
DROP TABLE CRT_BusEntityAssociations;

CREATE TABLE CRT_BusEntityAssociations AS
  SELECT CRS_BUS_ENTITY_ASSOC.* 
FROM CRS_BUS_ENTITY_ASSOC;

-- mkCatalog -  tmpCatalog
DROP TABLE CRT_Catalog;

CREATE TABLE CRT_Catalog AS
  SELECT CRS_CATALOG.*
FROM CRS_CATALOG;

-- mkCatalogAssociations - tmpCatalogAssociations
DROP TABLE CRT_CatalogAssociations;

CREATE TABLE CRT_CatalogAssociations AS
  SELECT CRS_CATALOG_ASSOC.* 
FROM CRS_CATALOG_ASSOC;

-- mkCwManufacturer - CW_MANUFACTURER
DROP TABLE CRT_CW_MANUFACTURER;

CREATE TABLE CRT_CW_MANUFACTURER AS
SELECT 
CRS_BUS_ENTITY.BUS_ENTITY_ID AS ID, 
CRS_BUS_ENTITY.SHORT_DESC AS NAME 
FROM CRS_BUS_ENTITY
WHERE CRS_BUS_ENTITY.BUS_ENTITY_TYPE_CD='MANUFACTURER'
  AND CRS_BUS_ENTITY.BUS_ENTITY_STATUS_CD='ACTIVE';

-- mkCatalogStructure - tmpCatalogStructure
DROP TABLE CRT_CatalogStructure;

CREATE TABLE CRT_CatalogStructure AS
  SELECT CRS_CATALOG_STRUCTURE.* 
FROM CRS_CATALOG_STRUCTURE;

-- mkOrder - tmpOrder
DROP TABLE CRT_Order;

CREATE TABLE CRT_Order AS
  SELECT CRS_ORDER.* 
FROM CRS_ORDER;

CREATE INDEX CRT_ORDER_I1 ON CRT_ORDER (ERP_ORDER_NUM);

-- mkOrderItem - tmpOrderItem
DROP TABLE CRT_OrderItem;

CREATE TABLE CRT_OrderItem AS
  SELECT CRS_ORDER_ITEM.* 
FROM CRS_ORDER_ITEM;

-- mkCategory - tmpCategory
DROP TABLE CRT_Category;

CREATE TABLE CRT_Category AS
SELECT 
  CRS_ITEM.ITEM_ID AS "Category ID", 
  CRS_ITEM.SHORT_DESC AS "Category Name"
FROM CRS_ITEM, CRS_CATALOG_STRUCTURE, CRS_CATALOG
WHERE CRS_ITEM.ITEM_ID = CRS_CATALOG_STRUCTURE.ITEM_ID
  AND CRS_ITEM.ITEM_TYPE_CD='CATEGORY'
  AND CRS_CATALOG_STRUCTURE.CATALOG_ID=CRS_CATALOG.CATALOG_ID
  AND CRS_CATALOG.CATALOG_STATUS_CD='ACTIVE' 
  AND CRS_CATALOG.CATALOG_TYPE_CD='STORE';

-- mkSites - tmpSites
DROP TABLE crt_site;

CREATE TABLE crt_site AS
SELECT
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
addr.country_cd AS ship_country_cd,
target_facility.clw_value AS TARGET_FACILITY_RANK
FROM 
crs_bus_entity ACCT, crs_bus_entity SITE, 
crs_bus_entity_assoc assoc, crs_address addr,
(SELECT * FROM crs_property WHERE property_status_cd = 'ACTIVE' AND property_type_cd = 'TARGET_FACILITY_RANK') target_facility
WHERE
assoc.bus_entity2_id = acct.bus_entity_id AND assoc.bus_entity1_id = SITE.bus_entity_id
AND addr.bus_entity_id = SITE.bus_entity_id 
AND acct.bus_entity_status_cd = 'ACTIVE' AND SITE.bus_entity_status_cd = 'ACTIVE'
AND addr.address_type_cd = 'SHIPPING' AND addr.address_status_cd = 'ACTIVE'
AND TRIM (TRANSLATE(site.erp_num,'1234567890','          ')) IS NULL
AND SITE.bus_entity_id = target_facility.bus_entity_id (+)
;

CREATE INDEX crt_site_i1 ON crt_site (site_erp_num, acct_erp_num);

CREATE INDEX crt_site_i2 ON crt_site (site_id);

--now create the table that allows duplicate sites but maps alternate erp numbers for sites 
--i.e. resale accounts

DROP TABLE crt_site_with_dups;

CREATE TABLE crt_site_with_dups AS
SELECT
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
addr.country_cd AS ship_country_cd,
target_facility.clw_value AS TARGET_FACILITY_RANK
FROM 
crs_bus_entity ACCT, crs_bus_entity SITE, 
crs_bus_entity_assoc assoc, crs_address addr,
(SELECT * FROM crs_property WHERE property_status_cd IN ('ACTIVE','Unknown') AND property_type_cd = 'TARGET_FACILITY_RANK') target_facility
WHERE
assoc.bus_entity2_id = acct.bus_entity_id AND assoc.bus_entity1_id = SITE.bus_entity_id
AND addr.bus_entity_id = SITE.bus_entity_id 
AND acct.bus_entity_status_cd = 'ACTIVE' AND SITE.bus_entity_status_cd = 'ACTIVE'
AND addr.address_type_cd = 'SHIPPING' AND addr.address_status_cd = 'ACTIVE'
AND TRIM (TRANSLATE(site.erp_num,'1234567890','          ')) IS NULL
AND SITE.bus_entity_id = target_facility.bus_entity_id (+)
;

INSERT INTO crt_site_with_dups
SELECT
acct.bus_entity_id AS acct_id,
resale.clw_value AS acct_erp_num, 
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
addr.country_cd AS ship_country_cd,
target_facility.clw_value AS TARGET_FACILITY_RANK
FROM 
crs_bus_entity ACCT, crs_bus_entity SITE, 
crs_bus_entity_assoc assoc, crs_address addr,
(SELECT * FROM crs_property WHERE property_status_cd = 'ACTIVE' AND property_type_cd = 'TARGET_FACILITY_RANK') target_facility,
(SELECT * FROM crs_property WHERE property_status_cd = 'ACTIVE' AND property_type_cd = 'RESALE_ACCOUNT_ERP_NUM' AND clw_value IS NOT NULL) RESALE
WHERE
RESALE.bus_entity_id = acct.bus_entity_id AND
assoc.bus_entity2_id = acct.bus_entity_id AND assoc.bus_entity1_id = SITE.bus_entity_id
AND addr.bus_entity_id = SITE.bus_entity_id 
AND acct.bus_entity_status_cd = 'ACTIVE' AND SITE.bus_entity_status_cd = 'ACTIVE'
AND addr.address_type_cd = 'SHIPPING' AND addr.address_status_cd = 'ACTIVE'
AND TRIM (TRANSLATE(site.erp_num,'1234567890','          ')) IS NULL
AND SITE.bus_entity_id = target_facility.bus_entity_id (+)
;

CREATE INDEX crt_site_with_dups_i1 ON crt_site_with_dups (site_erp_num, acct_erp_num);

CREATE INDEX crt_site_with_dups_i2 ON crt_site_with_dups (site_id);

-- mkAccountContracts - tmpAccountContracts
DROP TABLE CRT_AccountContracts;

CREATE TABLE CRT_AccountContracts AS
SELECT 
  CRS_BUS_ENTITY.BUS_ENTITY_ID AS ACCOUNT_BUS_ENTITY_ID, 
  CRS_BUS_ENTITY.ERP_NUM AS "ERP Account Number", 
  CRS_BUS_ENTITY.SHORT_DESC AS "Account Name", 
  CRS_CONTRACT.CONTRACT_ID, 
  CRS_CONTRACT.SHORT_DESC, 
  CRS_CONTRACT.CONTRACT_STATUS_CD, 
  CRS_CONTRACT.EFF_DATE 
FROM CRS_BUS_ENTITY, CRS_CONTRACT,CRS_BUS_ENTITY_ASSOC, CRS_CATALOG_ASSOC,CRS_CATALOG
 WHERE CRS_BUS_ENTITY.BUS_ENTITY_ID = CRS_BUS_ENTITY_ASSOC.BUS_ENTITY2_ID
   AND CRS_CATALOG_ASSOC.CATALOG_ID = CRS_CATALOG.CATALOG_ID
   AND CRS_CATALOG.CATALOG_ID = CRS_CONTRACT.CATALOG_ID 
   AND CRS_BUS_ENTITY_ASSOC.BUS_ENTITY1_ID = CRS_CATALOG_ASSOC.BUS_ENTITY_ID
   AND CRS_BUS_ENTITY.BUS_ENTITY_TYPE_CD='ACCOUNT'
   AND CRS_BUS_ENTITY_ASSOC.BUS_ENTITY_ASSOC_CD='SITE OF ACCOUNT'
GROUP BY 
  CRS_BUS_ENTITY.BUS_ENTITY_ID, 
  CRS_BUS_ENTITY.ERP_NUM, 
  CRS_BUS_ENTITY.SHORT_DESC, 
  CRS_CONTRACT.CONTRACT_ID, 
  CRS_CONTRACT.SHORT_DESC, 
  CRS_CONTRACT.CONTRACT_STATUS_CD, 
  CRS_CONTRACT.EFF_DATE;

-- mkProperty - tmpProperty
DROP TABLE CRT_Property;

CREATE TABLE CRT_Property AS
  SELECT CRS_PROPERTY.* 
FROM CRS_PROPERTY;

-- mkTargetFacility - TargetFacilty
DROP TABLE CRT_TargetFacilty;

CREATE TABLE CRT_TargetFacilty AS
SELECT 
  CRS_PROPERTY.*, 
  CRS_SITE_ACCOUNT.* 
FROM CRS_PROPERTY,CRS_SITE_ACCOUNT
 WHERE CRS_PROPERTY.BUS_ENTITY_ID = CRS_SITE_ACCOUNT.SITE_ID
   AND CRS_PROPERTY.SHORT_DESC='Target Facility';

CREATE INDEX crt_TargetFacilty ON crt_TargetFacilty(ACCOUNT_ERP_NUM,SITE_ERP_NUM);


-- mkFacilityType - FacilityType
DROP TABLE CRT_FacilityType;

CREATE TABLE CRT_FacilityType AS
SELECT 
  CRS_PROPERTY.*, 
  CRS_BUS_ENTITY.SHORT_DESC AS "Site Name"
FROM CRS_PROPERTY, CRS_BUS_ENTITY
 WHERE CRS_PROPERTY.BUS_ENTITY_ID = CRS_BUS_ENTITY.BUS_ENTITY_ID
   AND CRS_PROPERTY.SHORT_DESC='Facility TYPE:';

DROP TABLE CRT_CUSTORDER;

CREATE TABLE CRT_CUSTORDER AS
SELECT 
  CRS_CUSTORDER.*, 
  Trim(CUSTOMER) AS "Account Number" 
FROM CRS_CUSTORDER;

CREATE INDEX CRT_CUSTORDER_I1 ON CRT_CUSTORDER (ORDER_DATE);


-- mkLkpOpenPoItems - lkpOpenPoItems
DROP TABLE CRT_OpenPoItems;

CREATE TABLE CRT_OpenPoItems AS
SELECT 
  CRS_POLINE.ORDER_NBR AS "Order Number", 
  CRS_POLINE.PO_NUMBER AS "PO Number", 
  Trim(CRS_POLINE.ITEM) AS "CW SKU", 
  CRS_POLINE.ITEM, 
  Trim(CRS_POLINE.VENDOR) AS "Vendor ID", 
  CRS_POLINE.VENDOR, 
  CRS_POLINE.LINE_NBR, 
  SUM(CRS_POAPPROVE.APPROVE_QTY) AS "Qty Ship", 
  CRS_POLINE.QUANTITY, 
  CRS_POLINE.ENT_UNIT_CST AS "Cost"
FROM CRS_POLINE, CRS_POAPPROVE
 WHERE  CRS_POLINE.LINE_NBR = CRS_POAPPROVE.LINE_NBR(+) 
   AND  CRS_POLINE.PO_NUMBER = CRS_POAPPROVE.PO_NUMBER(+)
GROUP BY 
  CRS_POLINE.ORDER_NBR, 
  CRS_POLINE.PO_NUMBER, 
  CRS_POLINE.ITEM, 
  CRS_POLINE.VENDOR, 
  CRS_POLINE.LINE_NBR, 
  CRS_POLINE.QUANTITY, 
  CRS_POLINE.ENT_UNIT_CST
HAVING SUM(CRS_POAPPROVE.APPROVE_QTY) IS NULL OR 
       SUM(CRS_POAPPROVE.APPROVE_QTY)<CRS_POLINE.QUANTITY;


-- lkpUSPSFacilityType
DROP TABLE crt_USPSFacilityType;

CREATE TABLE crt_USPSFacilityType AS 
SELECT SUBSTR(trim(be.short_desc),1,6) AS CUSTOMER_BUDGET_REF,
       ft.clw_value AS FACILITY_TYPE, be.erp_num AS SITE_ERP_NUM, be1.erp_num AS CUSTOMER_ERP_NUM  
FROM crs_bus_entity be, crt_facilitytype ft, crs_bus_entity be1, crs_bus_entity_assoc bea
 WHERE be.bus_entity_id = ft.bus_entity_id 
   AND be1.bus_entity_type_cd = 'ACCOUNT' 
   AND be1.erp_num = '10052'
   AND bea.bus_entity1_id = be.bus_entity_id
   AND bea.bus_entity2_id = be1.bus_entity_id
   AND bea.bus_entity_assoc_cd = 'SITE OF ACCOUNT'
   AND be.erp_num IS NOT NULL;

-- lkpFedStripAreaAndDistrict
DROP TABLE crt_FedStripAreaAndDistrict;

CREATE TABLE crt_FedStripAreaAndDistrict AS 
SELECT SUBSTR(trim(be.short_desc),1,6) AS CUSTOMER_BUDGET_REF,
       area.clw_value AS Area, 
       dist.clw_value AS District, 
	   be.erp_num AS SITE_ERP_NUM, be1.erp_num AS CUSTOMER_ERP_NUM  
FROM crs_bus_entity be, crs_bus_entity be1, crs_bus_entity_assoc bea, crs_property dist, crs_property area
 WHERE bea.bus_entity1_id = be.bus_entity_id
   AND bea.bus_entity2_id = be1.bus_entity_id
   AND bea.bus_entity_assoc_cd = 'SITE OF ACCOUNT'
   AND be.erp_num IS NOT NULL
   AND dist.short_desc = 'District'
   AND area.short_desc = 'Area'
   AND be.bus_entity_id = dist.bus_entity_id
   AND be.bus_entity_id = area.bus_entity_id;

-- Specific Site data
DROP TABLE crt_site_data;


/* Specific Site data */
CREATE TABLE crt_site_data ( 
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

INSERT INTO crt_site_data
SELECT bus_entity_id, 
NULL, NULL, NULL, NULL,
NULL, NULL, NULL, NULL
FROM crs_bus_entity WHERE bus_entity_type_cd = 'SITE'
;

CREATE INDEX crt_site_data_i1 ON crt_site_data (site_id);


UPDATE crt_site_data sd SET "Area"=
(
SELECT MIN(clw_value) FROM crs_property pr 
  WHERE pr.bus_entity_id = sd.site_id
    AND pr.short_desc = 'Area'
); 


UPDATE crt_site_data sd SET "District"=
(
SELECT MIN(clw_value) FROM crs_property pr 
  WHERE pr.bus_entity_id = sd.site_id
    AND pr.short_desc = 'District'
); 

UPDATE crt_site_data sd SET "Facility Type"=
(
SELECT MIN(clw_value) FROM crs_property pr 
  WHERE pr.bus_entity_id = sd.site_id
    AND pr.short_desc = 'Facility Type:'
); 


UPDATE crt_site_data sd SET "Hot Store"=
(
SELECT MIN(clw_value) FROM crs_property pr 
  WHERE pr.bus_entity_id = sd.site_id
    AND pr.short_desc = 'Hot Store:'
); 

UPDATE crt_site_data sd SET "IQ Work Order Number"=
(
SELECT MIN(clw_value) FROM crs_property pr 
  WHERE pr.bus_entity_id = sd.site_id
    AND pr.short_desc = 'IQ Work Order Number'
); 

UPDATE crt_site_data sd SET "Net Square Footage"=
(
SELECT MIN(clw_value) FROM crs_property pr 
  WHERE pr.bus_entity_id = sd.site_id
    AND pr.short_desc = 'Net Square Footage:'
); 

UPDATE crt_site_data sd SET "Store Type"=
(
SELECT MIN(clw_value) FROM crs_property pr 
  WHERE pr.bus_entity_id = sd.site_id
    AND pr.short_desc = 'Store Type:'
); 

UPDATE crt_site_data sd SET "Target Facility"=
(
SELECT MIN(clw_value) FROM crs_property pr 
  WHERE pr.bus_entity_id = sd.site_id
    AND pr.short_desc = 'Target Facility'
); 


-------------------------------------------------
--- Views 
DROP VIEW crv_poline;

CREATE VIEW crv_poline AS 
SELECT * FROM crt_poline WHERE trim(TRANSLATE("CW SKU",'1234567890','          ')) IS NULL;

--- Order tracker report
DROP VIEW CRV_QryCoLine;

CREATE VIEW CRV_QryCoLine AS
SELECT 
CRT_COLINE.ORDER_NBR, 
CRT_COLINE.LINE_NBR, 
CRT_COLINE.ITEM, 
CRT_COLINE.ORDER_QTY, 
ENTERED_PRICE*ORDER_QTY AS "Line Item Sell Price", 
UNIT_COST*ORDER_QTY AS "Line Item Cost"
FROM CRT_COLINE;

DROP VIEW CRV_QryPoLine;

CREATE VIEW CRV_QryPoLine AS
SELECT 
CRT_POLINE.ORDER_NBR, 
CRT_POLINE.PO_NUMBER, 
CRT_POLINE.LINE_NBR, 
CRT_POLINE.ITEM, 
CRT_POLINE.QUANTITY, 
Trim(VENDOR) AS "Vendor ID"
FROM CRT_POLINE;


DROP VIEW CRV_QryOrderPos;

CREATE VIEW CRV_QryOrderPos AS
SELECT 
CRV_QryPoLine.ORDER_NBR, 
CRV_QryPoLine.PO_NUMBER, 
CRV_QryPoLine."Vendor ID", 
MIN(CRT_Vendor.VENDOR_NAME) AS "Vendor Name", 
SUM(CRV_QryCoLine.ORDER_QTY) AS "Num PO Items", 
SUM(CRV_QryCoLine."Line Item Sell Price") AS "PO Sell Price", 
SUM(CRV_QryCoLine."Line Item Cost") AS "PO Cost"
FROM CRV_QryPoLine, CRV_QryCoLine, CRT_Vendor
WHERE 1=1 
AND CRV_QryPoLine.ORDER_NBR = CRV_QryCoLine.ORDER_NBR(+)
AND CRV_QryPoLine.ITEM = CRV_QryCoLine.ITEM(+)
AND CRV_QryPoLine."Vendor ID" = CRT_Vendor."VendorID"(+)
GROUP BY 
CRV_QryPoLine.ORDER_NBR, 
CRV_QryPoLine.PO_NUMBER, 
CRV_QryPoLine."Vendor ID";


DROP VIEW CRV_OrderCost;

CREATE VIEW CRV_OrderCost AS
SELECT CRT_POLINE.ORDER_NBR, 
SUM(CRT_POLINE.QUANTITY) AS "Total qty of items in order",
SUM(QUANTITY*ENT_UNIT_CST) AS "Total Cost"
FROM CRT_POLINE
GROUP BY CRT_POLINE.ORDER_NBR;


quit;
