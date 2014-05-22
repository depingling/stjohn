



DROP TABLE DW_ORDER_FACT purge;
DROP TABLE DW_INVOICE_FACT purge;
DROP TABLE DW_SALES_REP_DIM purge;
DROP TABLE DW_DISTRIBUTOR_DIM purge;
DROP TABLE DW_DATE_DIM purge;
DROP TABLE DW_ACCOUNT_DIM purge;
DROP TABLE DW_SITE_DIM purge;
DROP TABLE DW_MANUFACTURER_DIM purge;
DROP TABLE DW_ITEM_DIM purge;
DROP TABLE DW_store_dim purge;
DROP TABLE DW_ITEM_DISTRIBUTOR purge;

CREATE TABLE DW_ITEM_DISTRIBUTOR (
    ITEM_DISTRIBUTOR_ID NUMBER NOT NULL,
    ITEM_DIM_ID NUMBER NOT NULL,
    DISTRIBUTOR_DIM_ID NUMBER NOT NULL,
    DIST_SKU VARCHAR2(60) NULL,
    DIST_PACK VARCHAR2(30) NULL,
    DIST_UOM VARCHAR2(30) NULL,
    DIST_ID number null,
    JD_DIST_SKU VARCHAR2(60) NULL,
    JD_DIST_PACK VARCHAR2(30) NULL,
    JD_DIST_UOM VARCHAR2(30) NULL,
    JD_DIST_ID number null,
    add_by varchar2(30) NULL,
    add_Date date NOT NULL,
    mod_by varchar2(30) NULL,
    mod_date date NOT NULL,
    PRIMARY KEY(ITEM_DISTRIBUTOR_ID)   
);

CREATE TABLE DW_store_dim (
  store_dim_id NUMBER  NOT NULL,
  store_name VARCHAR2(40) NOT NULL,
  store_id NUMBER NULL,
  add_by varchar2(30) NULL,
  add_Date date NOT NULL,
  mod_by varchar2(30) NULL,
  mod_date date NOT NULL,
  last_upload_order_date date NULL,
  last_upload_invoice_date date NULL,
  PRIMARY KEY(store_dim_id)
);


CREATE TABLE DW_ITEM_DIM (
  store_dim_id NUMBER NOT NULL,
  item_dim_id NUMBER NOT NULL,
  category_dim_id NUMBER NOT NULL,
  manufacturer_dim_id NUMBER NOT NULL,
  item_id NUMBER NULL,
  item_desc VARCHAR2(255) NULL,
  item_pack VARCHAR2(30) NULL,
  item_uom VARCHAR2(30) NULL,
  item_size VARCHAR2(50) NULL,
  clw_sku NUMBER NULL,
  manuf_sku VARCHAR2(50) NULL,
  jd_item_id NUMBER NULL,
  jd_item_desc VARCHAR2(255) NULL,
  Jd_item_pack VARCHAR2(30) NULL,
  jd_item_uom VARCHAR2(30) NULL,
  Jd_item_size VARCHAR2(50) NULL,
  JD_ITEM_FL varchar2(5),
  Jd_clw_sku NUMBER NULL,
  jd_manuf_sku VARCHAR2(50) NULL,
  manuf_id number null,
  jd_manuf_id number null,
  store_catalog_id number null,
  account_catalog_id number null,
  add_by varchar2(30) NULL,
  add_Date date NOT NULL,
  mod_by varchar2(30) NULL,
  mod_date date NOT NULL,
  PRIMARY KEY(item_dim_id)
);

CREATE TABLE DW_CATEGORY_DIM (
  store_dim_id NUMBER NOT NULL,
  category_dim_id NUMBER NOT NULL,
  category1 VARCHAR2(255) NULL,
  category2 VARCHAR2(255) NULL,
  category3 VARCHAR2(255) NULL,
  jd_category1 VARCHAR2(255) NULL,
  jd_category2 VARCHAR2(255) NULL,
  jd_category3 VARCHAR2(255) NULL,
  categ1_id number null,
  categ2_id number null,
  categ3_id number null,
  jd_categ1_id number null,
  jd_categ2_id number null,
  jd_categ3_id number null,
  store_catalog_id number null,
  account_catalog_id number null,
  add_by varchar2(30) NULL,
  add_Date date NOT NULL,
  mod_by varchar2(30) NULL,
  mod_date date NOT NULL,
  PRIMARY KEY(category_dim_id)
);

CREATE TABLE DW_MANUFACTURER_DIM (
  store_dim_id NUMBER NOT NULL,
  manufacturer_dim_id NUMBER NOT NULL,
  manuf_id NUMBER NULL,
  manuf_name VARCHAR2(40) NOT NULL,
  MANUF_CITY VARCHAR2(30) NULL,
  MANUF_STATE VARCHAR2(20) NULL,
  MAMUF_ADDRESS VARCHAR2(255)  NULL,
  MANUF_STATUS_CD VARCHAR2(30) NULL,
  jd_manuf_id NUMBER NULL,
  jd_manuf_name VARCHAR2(40) NULL,
  JD_MANUF_CITY VARCHAR2(30) NULL,
  JD_MANUF_STATE VARCHAR2(20) NULL,
  JD_MANUF_ADDRESS VARCHAR2(255)  NULL,
  JD_MANUF_STATUS_CD VARCHAR2(30) NULL,
  JD_MANUF_FL varchar2(5),
  add_by varchar2(30) NULL,
  add_Date date NOT NULL,
  mod_by varchar2(30) NULL,
  mod_date date NOT NULL,
  PRIMARY KEY(manufacturer_dim_id)
);

CREATE TABLE DW_SITE_DIM (
  store_dim_id NUMBER NOT NULL,
  account_dim_id NUMBER NOT NULL,
  account_id NUMBER NULL,
  site_dim_id NUMBER NOT NULL,
  site_id NUMBER NULL,
  site_name VARCHAR2(255) NOT NULL,
  site_num VARCHAR2(30) NULL,
  SITE_DIST_REF_CD VARCHAR2(30) NULL,
  site_street_address  VARCHAR2(255)  NULL,
  site_city  VARCHAR2(255)  NULL,
  site_state  VARCHAR2(50)  NULL,
  site_postal_code  VARCHAR2(30)  NULL,
  site_country  VARCHAR2(50)  NULL,
  type VARCHAR2(30) NULL,
  site_status_cd VARCHAR2(30),
  add_by varchar2(30) NULL,
  add_Date date NOT NULL,
  mod_by varchar2(30) NULL,
  mod_date date NOT NULL,
  PRIMARY KEY(site_dim_id)
  );

CREATE TABLE DW_ACCOUNT_DIM (
  store_dim_id NUMBER NOT NULL,
  account_dim_id NUMBER NOT NULL,
  account_id NUMBER NULL,
  account_name VARCHAR2(255) NOT NULL,
  account_num VARCHAR2(30) NULL,
  account_street_address VARCHAR2(255) NULL,
  account_city VARCHAR2(30) NULL,
  account_state VARCHAR2(20) NULL,
  account_status_cd VARCHAR2(30) NULL,
  branch_name VARCHAR2(20) NULL,
  market VARCHAR2(50) NULL,
  jd_account_name VARCHAR2(255) NULL,
  jd_account_street_address VARCHAR2(255) NULL,
  jd_account_city VARCHAR2(30) NULL,
  jd_account_state VARCHAR2(20) NULL,
  jd_account_status_cd VARCHAR2(30) NULL,
  jd_account_id NUMBER NULL,
  jd_region VARCHAR2(30) NULL,
  jd_region_id NUMBER NULL,
  jd_market VARCHAR2(50) NULL,
  connection_customer VARCHAR2(5) NULL,
  add_by varchar2(30) NULL,
  add_Date date NOT NULL,
  mod_by varchar2(30) NULL,
  mod_date date NOT NULL,
  PRIMARY KEY(account_dim_id)
  );
/*
CREATE TABLE DW_CUSTOMER_DIM (
  store_dim_id NUMBER NOT NULL,
  customer_dim_id NUMBER NOT NULL,
  site_id NUMBER NULL,
  site_name VARCHAR2(255) NOT NULL,
  site_num VARCHAR2(30) NOT NULL,
  site_street_address  VARCHAR2(255) NOT NULL,
  site_city  VARCHAR2(255) NOT NULL,
  site_state  VARCHAR2(50) NOT NULL,
  site_postal_code  VARCHAR2(30) NOT NULL,
  site_country  VARCHAR2(50) NOT NULL,
  type VARCHAR2(30) NULL,
  site_status_cd VARCHAR2(30),
  region VARCHAR2(30) NULL,
  region_id NUMBER NULL,
  branch_num VARCHAR2(30) NULL,
  account_id NUMBER NULL,
  account_name VARCHAR2(255) NOT NULL,
  account_num VARCHAR2(30) NOT NULL,
  account_street_address VARCHAR2(255) NULL,
  account_city VARCHAR2(30) NULL,
  account_state VARCHAR2(20) NULL,
  account_status_cd VARCHAR2(30) NULL,
  branch_name VARCHAR2(20) NULL,
  market VARCHAR2(20) NULL,
  jd_site_name VARCHAR2(255) NULL,
  jd_site_id NUMBER NULL,
  jd_account_name VARCHAR2(255) NULL,
  jd_account_street_address VARCHAR2(255) NULL,
  jd_account_city VARCHAR2(30) NULL,
  jd_account_state VARCHAR2(20) NULL,
  jd_account_status_cd VARCHAR2(30) NULL,
  jd_account_id NUMBER NULL,
  jd_region VARCHAR2(30) NULL,
  jd_region_id NUMBER NULL,
  jd_market VARCHAR2(20) NULL,
  add_by varchar2(30) NULL,
  add_Date date NOT NULL,
  mod_by varchar2(30) NULL,
  mod_date date NOT NULL,
  PRIMARY KEY(customer_dim_id));
*/
CREATE TABLE DW_DATE_DIM (
  date_dim_id NUMBER NOT NULL,
  calendar_date DATE NOT NULL,
  year NUMBER NOT NULL,
  month VARCHAR2(20) NOT NULL,
  day_of_week VARCHAR2(20) NOT NULL,
  month_number NUMBER NOT NULL,
  week_number NUMBER NOT NULL,
  quarter NUMBER NULL,
  PRIMARY KEY(date_dim_id)
);


CREATE TABLE DW_DISTRIBUTOR_DIM (
  store_dim_id NUMBER NOT NULL,
  distributor_dim_id NUMBER NOT NULL,
  dist_id NUMBER NULL,
  dist_name VARCHAR2(255) NOT NULL,
  DIST_CITY VARCHAR2(30) NULL,
  DIST_STATE VARCHAR2(20) NULL,
  DIST_ADDRESS VARCHAR2(255)  NULL,
  DIST_STATUS_CD VARCHAR2(30) NULL,
  jd_dist_id NUMBER NULL,
  jd_dist_name VARCHAR2(255) NULL,
  JD_DIST_CITY VARCHAR2(30) NULL,
  JD_DIST_STATE VARCHAR2(20) NULL,
  JD_DIST_ADDRESS VARCHAR2(255)  NULL,
  JD_DIST_STATUS_CD VARCHAR2(30) NULL,
  add_by varchar2(30) NULL,
  add_Date date NOT NULL,
  mod_by varchar2(30) NULL,
  mod_date date NOT NULL,
  PRIMARY KEY(distributor_dim_id)
);


CREATE TABLE DW_SALES_REP_DIM (
  store_dim_id NUMBER NOT NULL,
  sales_rep_dim_id NUMBER NOT NULL,
  rep_name VARCHAR2(50) NOT NULL,
  rep_code VARCHAR2(20) NULL,
  lw_id VARCHAR2(20) NULL,
  --rep_region VARCHAR2(20) NULL,
  add_by varchar2(30) NULL,
  add_Date date NOT NULL,
  mod_by varchar2(30) NULL,
  mod_date date NOT NULL,
  PRIMARY KEY(sales_rep_dim_id)
);


CREATE TABLE DW_INVOICE_FACT (
  invoice_fact_id number not null,
  invoice_dist_id NUMBER NULL,
  invoice_dist_detail_id NUMBER NULL,
  
  manufacturer_dim_id NUMBER NULL,
  date_dim_id NUMBER NOT NULL,
  distributor_dim_id NUMBER  NULL,
  item_dim_id NUMBER  NULL,
  category_dim_id NUMBER  NULL,
  store_dim_id NUMBER NOT NULL,
  sales_rep_dim_id NUMBER NULL, --DSR
  region_dim_id NUMBER NULL,
  account_dim_id NUMBER  NULL,
  site_dim_id NUMBER  NULL,
  
  cost NUMBER(15,3) NULL,
  price NUMBER(15,3) NOT NULL,
  quantity NUMBER NOT NULL,
  erp_po_num VARCHAR2(30) NULL,
  --ship_to_name VARCHAR2(30) NULL,
  --ship_to_address1 VARCHAR2(45) NULL,
  --ship_to_address2 VARCHAR2(45) NULL,
  --ship_to_address3 VARCHAR2(45) NULL,
  --ship_to_address4 VARCHAR2(45) NULL,
  --ship_to_city VARCHAR2(30) NULL,
  --ship_to_state VARCHAR2(20) NULL,
  --ship_to_postal_code VARCHAR2(20) NULL,
  --ship_to_country VARCHAR2(30) NULL,
  invoice_source_cd VARCHAR2(20) NULL,
  erp_po_ref_num VARCHAR2(30) NULL,
  freight NUMBER(15,3) NULL,
  sales_tax NUMBER(15,3) NULL,
  misc_charges NUMBER(15,3) NULL,
  discounts NUMBER(15,3) NULL,
  credit NUMBER(15,3) NULL,
  add_date DATE NULL,
  add_by VARCHAR2(30) NOT NULL,
  exp_date DATE  NULL,
  exp_by VARCHAR2(30) NULL,
  invoice_stataus_cd VARCHAR2(30) NULL,
  invoice_num VARCHAR2(30) NULL,
  status VARCHAR2(20) NULL,
  dist_sku VARCHAR2(60) NULL,
  dist_uom VARCHAR2(30) NULL,
  dist_pack VARCHAR2(30) NULL,
  line_amount NUMBER(15,3) NULL,
  jd_conv_factor NUMBER(15,3) NULL,
   ITEM_ID                 NUMBER(38),
   CATEG1_ID               NUMBER(38),
   JD_CATEG1_ID            NUMBER(38),
   MANUF_ID                NUMBER(38),
   ACCOUNT_ID              NUMBER(38),
   SITE_ID                 NUMBER(38),
   DISTR_ID                NUMBER(38),
   REGION_NUM              VARCHAR2(15 BYTE),
   DSR_NUM                 VARCHAR2(15 BYTE),
   DSR_NAME                VARCHAR2(50 BYTE),
   JD_FLAG                 VARCHAR2(5 BYTE),                    
  PRIMARY KEY(invoice_fact_id),
  CONSTRAINT fk_DW_manufacturer_dim FOREIGN KEY (manufacturer_dim_id) REFERENCES DW_MANUFACTURER_DIM(manufacturer_dim_id),
  CONSTRAINT fk_DW_DATE_DIM FOREIGN KEY (date_dim_id) REFERENCES DW_DATE_DIM(date_dim_id),
  CONSTRAINT fk_DW_DISTRIBUTOR_DIM FOREIGN KEY (distributor_dim_id) REFERENCES DW_DISTRIBUTOR_DIM(distributor_dim_id),
  CONSTRAINT fk_DW_ITEM_DIM FOREIGN KEY (item_dim_id) REFERENCES DW_ITEM_DIM(item_dim_id),
  CONSTRAINT fk_DW_CATEGORY_DIM FOREIGN KEY (category_dim_id) REFERENCES DW_CATEGORY_DIM(category_dim_id),
  
  CONSTRAINT fk_DW_account_DIM FOREIGN KEY (ACCOUNT_dim_id) REFERENCES DW_ACCOUNT_DIM(account_dim_id),
  CONSTRAINT fk_DW_site_DIM FOREIGN KEY (SITE_dim_id) REFERENCES DW_SITE_DIM(site_dim_id),

  CONSTRAINT fk_DW_STORE_DIM FOREIGN KEY (store_dim_id) REFERENCES DW_STORE_DIM(store_dim_id),
  CONSTRAINT fk_DW_SALES_REP_DIM FOREIGN KEY (sales_rep_dim_id) REFERENCES DW_SALES_REP_DIM(sales_rep_dim_id)
  );

CREATE TABLE DW_ORDER_FACT (
  order_fact_id  NUMBER not null,
  order_id       NUMBER NULL,
  order_item_id  NUMBER NULL,
  order_mod_date DATE NULL, 
  
  manufacturer_dim_id NUMBER NOT NULL,
  date_dim_id         NUMBER NOT NULL,
  distributor_dim_id  NUMBER NULL,
  item_dim_id         NUMBER NOT NULL,
  category_dim_id     NUMBER NOT NULL,
  store_dim_id        NUMBER NOT NULL,
  sales_rep_dim_id    NUMBER NULL, --DSR
  region_dim_id       NUMBER NULL,
  account_dim_id      NUMBER NOT NULL,
  site_dim_id         NUMBER NOT NULL,
  --cost_center_dim_id NUMBER NULL,   --NEW  ????
  
  cost NUMBER(15,3) NULL,
  price NUMBER(15,3) NOT NULL, --CUST_CONTRACT_PRICE
  quantity NUMBER NOT NULL,    --TOTAL_QUANTITY_ORDERED
  
  erp_po_num VARCHAR2(30) NULL,
  order_item_status_cd VARCHAR2(30) NULL,
  -- код товара покупателя
  cust_item_sku_num VARCHAR2(30) NULL,
  -- from ITEM_DIM for manuf
  --....
  -- from order_item for distributor
  dist_sku VARCHAR2(30) NULL,
  dist_uom VARCHAR2(30) NULL,
  dist_pack VARCHAR2(30) NULL,
  
--  jd_conv_factor NUMBER(15,3) NULL,
  locale_cd VARCHAR2(20) NULL,
  currency_cd VARCHAR2(20) NULL,
  ORDER_NUM             VARCHAR2(50 BYTE), 
  REQUEST_PO_NUM        VARCHAR2(50 BYTE), 
  TAX_AMOUNT            NUMBER(15,3), 
  TOTAL_FREIGHT_COST    NUMBER(15,3),
  TOTAL_MISC_COST       NUMBER(15,3), 

  add_date DATE NULL,
  add_by VARCHAR2(30) NOT NULL,
  exp_date DATE NULL,
  exp_by VARCHAR2(30) NULL,

  PRIMARY KEY(order_fact_id),
  CONSTRAINT fk1_DW_manufacturer_dim FOREIGN KEY (manufacturer_dim_id) REFERENCES DW_MANUFACTURER_DIM(manufacturer_dim_id),
  CONSTRAINT fk1_DW_DATE_DIM FOREIGN KEY (date_dim_id) REFERENCES DW_DATE_DIM(date_dim_id),
  CONSTRAINT fk1_DW_DISTRIBUTOR_DIM FOREIGN KEY (distributor_dim_id) REFERENCES DW_DISTRIBUTOR_DIM(distributor_dim_id),
  CONSTRAINT fk1_DW_ITEM_DIM FOREIGN KEY (item_dim_id) REFERENCES DW_ITEM_DIM(item_dim_id),
  CONSTRAINT fk1_DW_CATEGORY_DIM FOREIGN KEY (category_dim_id) REFERENCES DW_CATEGORY_DIM(category_dim_id),
  
  CONSTRAINT fk1_DW_account_DIM FOREIGN KEY (ACCOUNT_dim_id) REFERENCES DW_ACCOUNT_DIM(account_dim_id),
  CONSTRAINT fk1_DW_site_DIM FOREIGN KEY (SITE_dim_id) REFERENCES DW_SITE_DIM(site_dim_id),

  CONSTRAINT fk1_DW_STORE_DIM FOREIGN KEY (store_dim_id) REFERENCES DW_STORE_DIM(store_dim_id),
  CONSTRAINT fk1_DW_SALES_REP_DIM FOREIGN KEY (sales_rep_dim_id) REFERENCES DW_SALES_REP_DIM(sales_rep_dim_id)
  );
  
CREATE SEQUENCE  DW_INVOICE_FACT_SEQ;
CREATE SEQUENCE  DW_ORDER_FACT_SEQ;

CREATE SEQUENCE  DW_SALES_REP_DIM_SEQ;
CREATE SEQUENCE  DW_DISTRIBUTOR_DIM_SEQ;
CREATE SEQUENCE  DW_DATE_DIM_SEQ;
CREATE SEQUENCE  DW_ACCOUNT_DIM_SEQ;
CREATE SEQUENCE  DW_SITE_DIM_SEQ;

CREATE SEQUENCE  DW_MANUFACTURER_DIM_SEQ;
CREATE SEQUENCE  DW_ITEM_DIM_SEQ;
CREATE SEQUENCE  DW_CATEGORY_DIM_SEQ;
CREATE SEQUENCE  DW_store_dim_SEQ;
CREATE SEQUENCE  DW_ITEM_DISTRIBUTOR_SEQ;
CREATE SEQUENCE  DW_REGION_DIM_SEQ;



CREATE TABLE DW_REGION_DIM
(
  STORE_DIM_ID      NUMBER                      NOT NULL,
  REGION_DIM_ID     NUMBER                      NOT NULL,
  REGION_NAME       VARCHAR2(50 BYTE)           NOT NULL,
  REGION_CODE       VARCHAR2(20 BYTE),
  ADD_BY            VARCHAR2(30 BYTE),
  ADD_DATE          DATE                        NOT NULL,
  MOD_BY            VARCHAR2(30 BYTE),
  MOD_DATE          DATE                        NOT NULL
);

-----------------INDEXES-ON DIM --------------------------
CREATE INDEX DW_ACCOUNT_DIM_I1 ON DW_ACCOUNT_DIM (ACCOUNT_ID);

CREATE INDEX DW_SITE_DIM_I1 ON DW_SITE_DIM (SITE_ID);

CREATE INDEX DW_ITEM_DIM_I1 ON DW_ITEM_DIM (ITEM_ID);

CREATE INDEX DW_DISTRIBUTOR_DIM_I1 ON DW_DISTRIBUTOR_DIM (DIST_ID);

CREATE INDEX DW_MANUFACTURER_DIM_I1 ON DW_MANUFACTURER_DIM (MANUF_ID);

CREATE INDEX DW_DATE_DIM_I1 ON DW_DATE_DIM (CALENDAR_DATE);


CREATE INDEX DW_ITEM_DISTRIBUTOR_I1 ON DW_ITEM_DISTRIBUTOR (ITEM_DIM_ID); 
CREATE INDEX DW_ITEM_DISTRIBUTOR_I2 ON DW_ITEM_DISTRIBUTOR (DISTRIBUTOR_DIM_ID); 

Create index DW_INVOICE_FACT_i1 on DW_INVOICE_FACT(MANUFACTURER_DIST_ID);
Create index DW_INVOICE_FACT_i2 on DW_INVOICE_FACT(ACCOUNT_DIM_ID);
Create index DW_INVOICE_FACT_i3 on DW_INVOICE_FACT(SITE_DIM_ID);
Create index DW_INVOICE_FACT_i4 on DW_INVOICE_FACT(DISTRIBUTOR_DIM_ID);
Create index DW_INVOICE_FACT_i5 on DW_INVOICE_FACT(ITEM_DIM_ID);
Create index DW_INVOICE_FACT_i7 on DW_INVOICE_FACT(DATE_DIM_ID);
Create index DW_INVOICE_FACT_i8 on DW_INVOICE_FACT(SALES_REP_DIM_ID);
Create index DW_INVOICE_FACT_i9 on DW_INVOICE_FACT(INVOICE_DIST_ID);
Create index DW_INVOICE_FACT_i10 on DW_INVOICE_FACT(INVOICE_DIST_DETAIL_ID);
