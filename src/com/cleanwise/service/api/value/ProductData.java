package com.cleanwise.service.api.value;


/**
 * Title:        ProductData
 * Description:  Value object extension for marshalling product
 * attribute value pairs.
 * Purpose:      Obtains and marshals product information among
 * session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;

import java.math.*;

import java.util.*;


/**
 * <code>ProductData</code>
 */
public class ProductData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -3063310681103709931L;

  public static final String CACHE_OBJECT_NAME = "ProductData";

    // ----------------------------------------- Instance Variables
    public String toString() {

        return "[ Item: " + _itemD + ", CatalogStructure: " +
      _catalogStructureD + " Hazmat=" + isHazmat() +
        ", CostCenterName=" + mCostCenterName
        + ", dist ItemMappingData: " + _distrMappingD
        + " ]";
    }

    public ItemData _itemD;
    public BusEntityData _manuBusEntityD;
    public ItemMappingData _manuMappingD;
    public CatalogStructureData _catalogStructureD;
    public BusEntityData _distrBusEntityD;
    public ItemMappingData _distrMappingD;
    public BusEntityDataVector _distrBusEntityDV = new BusEntityDataVector();
    public ItemMappingDataVector _distrMappingDV = new ItemMappingDataVector();
    public CatalogCategoryDataVector _catalogCategoryDV;
    public ItemMappingDataVector _certifiedCompanies=new ItemMappingDataVector();
    public BusEntityDataVector _certCompaniesBusEntityDV = new BusEntityDataVector();

    private ItemMetaDataVector _dataFieldProperties;

    private ProductSkuView mProductSku;
    private ProductPriceView mProductPrice;
    private boolean mIsContractItem;
    
    private String mProductJdWsUrl;
    
    


    /**
     * For a given name type from the item meta data will determine if this is a content value.
     * That is something that would be downloaded and not a string type.  This would then be stored
     * in the clw_content table as a blob value.  @see ContentData.binaryData
     * @param the type to check for
     * @return true if it is a content type
     */
    public static boolean isContentNameType(String pType){

    	if(pType.equalsIgnoreCase(ProductData.IMAGE) ||
    			pType.equalsIgnoreCase(ProductData.THUMBNAIL) ||
    			pType.equalsIgnoreCase(ProductData.MSDS) ||
    			pType.equalsIgnoreCase(ProductData.SPEC)){
    		return true;
    	}
    	return false;
    }

    public static final String
  COLOR = "COLOR",
  SCENT = "SCENT",
  SIZE = "SIZE",
  SHIP_WEIGHT = "SHIP_WEIGHT",
  WEIGHT_UNIT = "WEIGHT_UNIT",
  UOM = "UOM",
  PACK = "PACK",
  UPC_NUM = "UPC_NUM",
  PKG_UPC_NUM = "PKG_UPC_NUM",
  IMAGE = "IMAGE",
  THUMBNAIL = "THUMBNAIL",
  MSDS = "MSDS",
  DED = "DED",
  SPEC = "SPEC",
  LIST_PRICE = "LIST_PRICE",
  COST_PRICE = "COST_PRICE",
  UNSPSC_CD = "UNSPSC_CD",
  OTHER_DESC = "OTHER_DESC",
        PSN = "PSN",
        NSN = "NSN",
  HAZMAT = "HAZMAT",
        CUBE_SIZE = "CUBE_SIZE",
        PACK_PROBLEM_SKU = "PACK_PROBLEM_SKU",
        REBATE_BASE_COST = "REBATE_BASE_COST"
  ;

    public Hashtable _itemMetaH = new Hashtable();

    public ProductData() {    }

    // ---------------------------------------- Properties

    /**
     *Gets underlying ItemData object
     *
     *@return ItemData object
     */
    public ItemData getItemData() {

        if (_itemD == null)
            setEmptyItem();

        return _itemD;
    }

    /**
     * Sets ItemData DAO object
     * @param item
     */
    public void setItemData(ItemData item) {
        _itemD = item;

        int id = getProductId();

        if (id != 0) {
            setProductIdFK(id);
  }
    }

    /**
     *Gets manufacturer product mapping
     *@return ItemMappingData object
     */
    public ItemMappingData getManuMapping() {

        return _manuMappingD;
    }

    /**
     * Sets manufacturer product mapping
     * @param ItemMappingData
     */
    public void setManuMapping(ItemMappingData pValue) {
        _manuMappingD = pValue;

        if (_manuBusEntityD != null) {
            _manuMappingD.setBusEntityId(_manuBusEntityD.getBusEntityId());
        }
    }

    /**
     * Maps product to manufacturer
     * @param pManufacturer BusEntityData object
     * @param pManufacturerSku String
     */
    public void setManuMapping(BusEntityData pManufacturer,
                               String pManufacturerSku) {

        if (_manuMappingD == null) {
            _manuMappingD = ItemMappingData.createValue();
        }

        _manuMappingD.setItemId(getProductId());
        _manuMappingD.setBusEntityId(pManufacturer.getBusEntityId());
        _manuMappingD.setItemNum(pManufacturerSku);
        _manuMappingD.setItemMappingCd
            (RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
        _manuMappingD.setStatusCd
            (RefCodeNames.ITEM_STATUS_CD.ACTIVE);

        setManufacturer(pManufacturer);
        _manuMappingD.setItemNum(pManufacturerSku);
    }

    /**
     *Gets Manufacutrer SKU number
     *@return String
     */
    public String getManufacturerSku() {

        if (_manuMappingD == null) {

            return "";
        }

        String ret = _manuMappingD.getItemNum();

        if (ret == null) {

            return "";
        }

        return ret;
    }

    /**
     *Gets Manufacutrer type BusEntityData object
     *@return BusEntityData object
     */
    public BusEntityData getManufacturer() {

        return _manuBusEntityD;
    }

    /**
     * Sets Manufacturer BusEntityData DAO object
     * @param busness entity
     */
    public void setManufacturer(BusEntityData pBusEntityData) {
        _manuBusEntityD = pBusEntityData;

        if (_manuMappingD != null) {
            _manuMappingD.setBusEntityId(_manuBusEntityD.getBusEntityId());
        }
    }

    /**
     *Gets manufacturer name
     *@return String
     */
    public String getManufacturerName() {

        if (_manuBusEntityD == null) {

            return "";
        }

        return _manuBusEntityD.getShortDesc();
    }

    /**
     *Gets catalog distributor type BusEntityData object
     *@return BusEntityData object
     */
    public BusEntityData getCatalogDistributor() {

        return _distrBusEntityD;
    }

    /**
     * Sets catalog distributor BusEntityData DAO object
     * @param busness entity
     */
    public void setCatalogDistributor(BusEntityData pBusEntityData) {
        _distrBusEntityD = pBusEntityData;

        if (_catalogStructureD != null) {

            if (_distrBusEntityD != null) {
                _catalogStructureD.setBusEntityId
        (_distrBusEntityD.getBusEntityId());
            }
      else {
                _catalogStructureD.setBusEntityId(0);
            }
        }
    }

    /**
     *Gets distributor type ItemMappingData object rlated to
     *the product and its catalog distributor
     *@return BusEntityData object
     */
    public ItemMappingData getCatalogDistrMapping() {

        return _distrMappingD;
    }

    public java.math.BigDecimal getCatalogDistrUomMultiplier() {
  if (null == _distrMappingD ) {
      return null;
  }

  return _distrMappingD.getUomConvMultiplier();
    }

    /**
     * Sets all distributor type ItemMappingData objects related to the product
     * @param business entity
     */
    public void setCatalogDistrMapping(ItemMappingData pDistrMappingD) {
        _distrMappingD = pDistrMappingD;
    }

    /**
     * Sets catalog item name
     * @param String object
     */
    public void setCustomerProductShortDesc(String pValue) {

        if (_catalogStructureD != null) {
            _catalogStructureD.setShortDesc(pValue);
        }
    }

    /**
     *Gets catalog structure object short description
     *@return String object
     */
    public String getCustomerProductShortDesc() {

        if (_catalogStructureD == null ||
            _catalogStructureD.getShortDesc() == null)

            return "";

        return _catalogStructureD.getShortDesc();
    }

    /**
     *Gets catalog item short description, which is catalog
     *structure object short descrption if exists or it item short description
     *@return String object
     */
    public String getCatalogProductShortDesc() {

        String catalogProductShortDesc = getCustomerProductShortDesc();

        if (catalogProductShortDesc.trim().length() == 0) {
            catalogProductShortDesc = _itemD.getShortDesc();

            if (catalogProductShortDesc == null)
                catalogProductShortDesc = "";
        }

        return catalogProductShortDesc;
    }

    /**
     * Sets catalog custormer sku number
     * @param String object
     */
    public void setCustomerSkuNum(String pValue) {

        if (_catalogStructureD != null) {
            _catalogStructureD.setCustomerSkuNum(pValue);
        }
    }

    /**
     *Gets catalog customer sku number
     *@return String object
     */
    public String getCustomerSkuNum() {

        if (_catalogStructureD == null ||
            _catalogStructureD.getCustomerSkuNum() == null)

            return "";

        return _catalogStructureD.getCustomerSkuNum();
    }

    public String getActualCustomerSkuNum() {

        if (mProductSku != null) {

            if (Utility.isSet(mProductSku.getPriceListRank1SkuNum())) {
                return mProductSku.getPriceListRank1SkuNum();
            } else if (Utility.isSet(mProductSku.getPriceListRank2SkuNum())) {
                return mProductSku.getPriceListRank2SkuNum();
            } else {
                return Utility.strNN(mProductSku.getCustomerSkuNum());
            }

        } else {
            return getCustomerSkuNum();

        }
    }
    /**
     *Gets catalog sku number, which is catalog structure object
     *sku number if exists or it item sku numbner
     *@return String object
     */
    public String getCatalogSkuNum() {

        String catalogSkuNum = getCustomerSkuNum();

        if (catalogSkuNum.trim().length() == 0) {

            if (_itemD.getSkuNum() != 0) {
                catalogSkuNum = String.valueOf(_itemD.getSkuNum());
            } else {
                catalogSkuNum = "";
            }
        }

        return catalogSkuNum;
    }

    /**
     *Gets all distributor type BusEntityData objects mapped to the product
     *@return BusEntityData object
     */
    public BusEntityDataVector getMappedDistributors() {

        return _distrBusEntityDV;
    }

    /**
     * Sets all distributor type BusEntityData objects mapped to the product
     * @param business entity
     */
    public void setMappedDistributors(BusEntityDataVector pBusEntityDV) {

        if (pBusEntityDV == null) {
            _distrBusEntityDV = new BusEntityDataVector();
        } else {
            _distrBusEntityDV = pBusEntityDV;
        }
    }

    /**
     *Gets all distributor type ItemMappingData objects related to the product
     *@return BusEntityData object
     */
    public ItemMappingDataVector getDistributorMappings() {

        return _distrMappingDV;
    }

    /**
     * Sets all distributor type ItemMappingData objects related to the product
     * @param business entity
     */
    public void setDistributorMappings(ItemMappingDataVector pDistrMappingDV) {

        if (pDistrMappingDV == null) {
            _distrMappingDV = new ItemMappingDataVector();
        } else {
            _distrMappingDV = pDistrMappingDV;
        }
    }
    public void addDistributorMapping(ItemMappingData pImd) {

        if (_distrMappingDV == null) {
            _distrMappingDV = new ItemMappingDataVector();
        }
  _distrMappingDV.add(pImd);
    }

    /**
     * Adds new distributor to the list of possible distibutors
     * @param pDistrD BusEntityData object for the distributor
     */
    public void addMappedDistributor(BusEntityData pDistrD) {

        if (_distrBusEntityDV == null)
            _distrBusEntityDV = new BusEntityDataVector();

        int distrId = pDistrD.getBusEntityId();
        int ii = 0;

        for (; ii < _distrBusEntityDV.size(); ii++) {

            BusEntityData beD = (BusEntityData)_distrBusEntityDV.get(ii);

            if (beD.getBusEntityId() == distrId) {
                break;
      }
        }

        if (ii == _distrBusEntityDV.size()) {
            _distrBusEntityDV.add(0, pDistrD);
        }
    }


    /**
     * Adds new certified company to the list of possible companies
     *
     * @param pCertCompanyD BusEntityData object for the company
     */
    public void addMappedCertCompany(BusEntityData pCertCompanyD) {

        boolean founfFl = false;
        if (_certCompaniesBusEntityDV == null)
            _certCompaniesBusEntityDV = new BusEntityDataVector();

        int certCompanyId = pCertCompanyD.getBusEntityId();
        for (int ii = 0; ii < _certCompaniesBusEntityDV.size(); ii++) {
            BusEntityData beD = (BusEntityData) _certCompaniesBusEntityDV.get(ii);
            if (beD.getBusEntityId() == certCompanyId) {
                founfFl = true;
                break;
            }
        }

        if (!founfFl) {
            _certCompaniesBusEntityDV.add(pCertCompanyD);
        }
    }
    /**
     * Gets distributor SKU for the related Distributor
     * @param pDistrId distributor id
     */
    public String getDistributorSku(int pDistrId) {

        if (_distrMappingDV == null)

            return "";

        String retVal = "";

        for (int ii = 0; ii < _distrMappingDV.size(); ii++) {

            ItemMappingData itemMappingD = (ItemMappingData)
    _distrMappingDV.get(ii);

            if (itemMappingD.getBusEntityId() == pDistrId) {
                retVal = itemMappingD.getItemNum();

                if (null == retVal)
                    retVal = "";
            }
        }

        return retVal;
    }

    public void setDistributorSku(int pDistrId, String pSkuNumber) {

        if (_distrMappingDV == null)
            _distrMappingDV = new ItemMappingDataVector();

        int ii = 0;

        for (; ii < _distrMappingDV.size(); ii++) {

            ItemMappingData imD = (ItemMappingData)_distrMappingDV.get(ii);

            if (imD.getBusEntityId() == pDistrId) {
                imD.setItemNum(pSkuNumber);
                break;
            }
        }

        if (ii == _distrMappingDV.size()) {

            ItemMappingData imD = ItemMappingData.createValue();
            imD.setItemId( getProductId());
            imD.setBusEntityId( pDistrId);
            imD.setItemNum( pSkuNumber);
            imD.setItemUom( getDistributorUom(pDistrId));
            imD.setItemPack( getDistributorPack(pDistrId ));
            imD.setItemMappingCd( RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
            imD.setStatusCd( RefCodeNames.ITEM_STATUS_CD.ACTIVE);

            _distrMappingDV.add(0, imD);
        }
    }

    /**
     * Gets distributor UOM for the related Distributor
     * @param pDistrId distributor id
     */
    public String getDistributorUom(int pDistrId) {

        if (_distrMappingDV == null)

            return "";

        String retVal = "";

        for (int ii = 0; ii < _distrMappingDV.size(); ii++) {

            ItemMappingData itemMappingD = (ItemMappingData)
    _distrMappingDV.get(ii);

            if (itemMappingD.getBusEntityId() == pDistrId) {
                retVal = itemMappingD.getItemUom();

                if (null == retVal)
                    retVal = "";
            }
        }

        return retVal;
    }

    public void setDistributorUom(int pDistrId, String pUom) {

        if (_distrMappingDV == null)
            _distrMappingDV = new ItemMappingDataVector();

        int ii = 0;

        for (; ii < _distrMappingDV.size(); ii++) {

            ItemMappingData imD = (ItemMappingData)_distrMappingDV.get(ii);

            if (imD.getBusEntityId() == pDistrId) {
                imD.setItemUom(pUom);

                break;
            }
        }

        if (ii == _distrMappingDV.size()) {

            ItemMappingData imD = ItemMappingData.createValue();
            imD.setItemId( getProductId());
            imD.setBusEntityId(pDistrId);
            imD.setItemNum(getDistributorSku(pDistrId	));
            imD.setItemNum(pUom);
            imD.setItemPack(getDistributorPack(pDistrId));
            imD.setItemMappingCd
                (RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
            imD.setStatusCd
                (RefCodeNames.ITEM_STATUS_CD.ACTIVE);
            _distrMappingDV.add(0, imD);
        }
    }

    /**
     * Gets distributor Pack for the related Distributor
     * @param pDistrId distributor id
     */
    public String getDistributorPack(int pDistrId) {

        if (_distrMappingDV == null)

            return "";

        String retVal = "";

        for (int ii = 0; ii < _distrMappingDV.size(); ii++) {

            ItemMappingData itemMappingD = (ItemMappingData)
    _distrMappingDV.get(ii);

            if (itemMappingD.getBusEntityId() == pDistrId) {
                retVal = itemMappingD.getItemPack();

                if (null == retVal)
                    retVal = "";
            }
        }

        return retVal;
    }

    public void setDistributorPack(int pDistrId, String pPack) {

        if (_distrMappingDV == null)
            _distrMappingDV = new ItemMappingDataVector();

        int ii = 0;

        for (; ii < _distrMappingDV.size(); ii++) {

            ItemMappingData imD = (ItemMappingData)_distrMappingDV.get(ii);

            if (imD.getBusEntityId() == pDistrId) {
                imD.setItemPack(pPack);

                break;
            }
        }

        if (ii == _distrMappingDV.size()) {

            ItemMappingData imD = ItemMappingData.createValue();
            imD.setItemId(getProductId());
            imD.setBusEntityId(pDistrId);
            imD.setItemNum(getDistributorSku(pDistrId));
            imD.setItemUom(getDistributorUom(pDistrId));
            imD.setItemPack(pPack);
            imD.setItemMappingCd
                (RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
            imD.setStatusCd
                (RefCodeNames.ITEM_STATUS_CD.ACTIVE);

            _distrMappingDV.add(0, imD);
        }
    }

    /**
     *Gets catalog distributor name
     *@return String
     */
    public String getCatalogDistributorName() {

        if (_distrBusEntityD == null) {

            return "";
        }

        return _distrBusEntityD.getShortDesc();
    }

    /**
     * Sets CatalogStructure for the product
     * @param CatalogStructureData object
     */
    public void setCatalogStructure(CatalogStructureData pCatalogStructureD) {
        _catalogStructureD = pCatalogStructureD;
    }

    /**
     *Gets CatalogStructure object
     *@return CatalogStructureData
     */
    public CatalogStructureData getCatalogStructure() {

        return _catalogStructureD;
    }

    /**
     * Sets CatalogStructure for the product
     * @param CatalogStructureData object
     */
    public void setCatalogStructure(int pCatalogId) {

        if (_catalogStructureD == null) {

            int distrId = 0;

            if (_distrBusEntityD != null) {
                distrId = _distrBusEntityD.getBusEntityId();
            }

            java.util.Date current = new java.util.Date
                (System.currentTimeMillis());
            _catalogStructureD = CatalogStructureData.createValue();
            _catalogStructureD.setCatalogId(pCatalogId);
            _catalogStructureD.setBusEntityId(distrId);
            _catalogStructureD.setCatalogStructureCd
                (RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
            _catalogStructureD.setItemId(getProductId());
            _catalogStructureD.setEffDate(current);
            _catalogStructureD.setStatusCd
                (RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);
        } else {
            _catalogStructureD.setCatalogId(pCatalogId);
        }
    }

    /**
     *Gets categories for the catalog product
     *@return CatalogCategoryDataVector object
     */
    public CatalogCategoryDataVector getCatalogCategories() {

        if (null == _catalogCategoryDV) {
            _catalogCategoryDV = new CatalogCategoryDataVector();
        }

        return _catalogCategoryDV;
    }

    /**
     * Sets  categories for the catalog product
     * @param CatalogCategoryDataVector
     */
    public void setCatalogCategories
  (CatalogCategoryDataVector pCatalogCategoryDataVector) {
        _catalogCategoryDV = pCatalogCategoryDataVector;
    }

    /**
     * Gets all product attributes
     * @return ItemMetaDataVector object
     */
    public ItemMetaDataVector getProductAttributes() {
  ItemMetaDataVector imDV = new ItemMetaDataVector();
  Enumeration enume = _itemMetaH.elements();
  while (enume.hasMoreElements()) {
      imDV.add(enume.nextElement());
  }
  return imDV;
    }

    public ItemMetaData getItemMeta(String pNameValue) {
  if(_itemMetaH.containsKey(pNameValue)){
      return (ItemMetaData) _itemMetaH.get(pNameValue);
  }
  return null;
    }

    public void setItemMeta(ItemMetaData itemMetaD, String pNameValue) {
        _itemMetaH.put(pNameValue,itemMetaD);
    }

    public void removeItemMeta(String pNameValue) {
        if(_itemMetaH.containsKey(pNameValue)){
            _itemMetaH.remove(pNameValue);
        }
    }

    /**
     * Gets the product attribute
     * @param pValue  ItemMetaData object
     * return string value of the attribute
     */
    public String getProductAttribute(String pNameValue) {
  if(_itemMetaH.containsKey(pNameValue)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(pNameValue);
      return imD.getValue();
  }
  return "";
    }

    /**
     * Sets the product attribute
     * @param pValue  String value object
     * @param pKey String key value
     * return none
     */
    public void setProductAttribute(String pValue, String pKey) {
        if (_itemD == null)
            setEmptyItem();
        if(!_itemMetaH.containsKey(pKey)) {
            ItemMetaData imD = ItemMetaData.createValue();
            imD.setItemId(_itemD.getItemId());
            imD.setNameValue(pKey);
            imD.setValue(pValue);
            _itemMetaH.put(pKey,imD);
        } else {
            ItemMetaData imD = (ItemMetaData)_itemMetaH.get(pKey);
            imD.setValue(pValue);
        }
    }
    /**
     * Sets the product attribute
     * @param pValue  ItemMetaData object
     * return none
     */
    public void setProductAttribute(ItemMetaData pValue) {

        String attributeName = pValue.getNameValue();
        setItemMeta(pValue, attributeName);
    }

    /**
     * Sets the product attributes. Removes all existing attributes
     * before setting
     * @param pValue  ItemMetaDataVector object
     * return none
     */
    public void setProductAttributes(ItemMetaDataVector pValue) {
        _itemMetaH.clear();
        for (int ii = 0; ii < pValue.size(); ii++) {
            Object val = pValue.get(ii);
            if (val instanceof ItemMetaData) {
                setProductAttribute((ItemMetaData)val);
            }
        }
    }

    /**
     * Gets the productId (product identifier) property
     * return int
     */
    public int getProductId() {

        if (_itemD == null)
            setEmptyItem();

        return _itemD.getItemId();
    }

    /**
     * Sets the productId (product identifier) property
     * @param pValue  the value we want to set
     * return none
     */
    public void setProductId(int pValue) {

        if (_itemD == null)
            setEmptyItem();

        _itemD.setItemId(pValue);
        setProductIdFK(pValue);
    }

    /*
     */
    private void setProductIdFK(int pValue) {

        if (_manuMappingD != null) {
            _manuMappingD.setItemId(pValue);
        }

        if (_catalogStructureD != null) {
            _catalogStructureD.setItemId(pValue);
        }
        Enumeration enume = _itemMetaH.keys();
        while(enume.hasMoreElements()) {
      String nameValue = (String) enume.nextElement();
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(nameValue);
      imD.setItemId(pValue);
        }
    }

    /**
     * Gets the productShortDesc (product short description) property
     * return String
     */
    public String getShortDesc() {

        if (_itemD == null)
            setEmptyItem();

        return _itemD.getShortDesc();
    }

    /**
     * Sets the productShortDesc (product short description) property
     * @param pValue  the value we want to set
     * return none
     */
    public void setShortDesc(String pValue) {

        if (_itemD == null)
            setEmptyItem();

        _itemD.setShortDesc(pValue);
    }

    /**
     * Gets the productLongDesc (product Long description) property
     * return String
     */
    public String getLongDesc() {

        if (_itemD == null)
            setEmptyItem();

        return _itemD.getLongDesc();
    }

    /**
     * Sets the productLongDesc (product Long description) property
     * @param pValue  the value we want to set
     * return none
     */
    public void setLongDesc(String pValue) {

        if (_itemD == null)
            setEmptyItem();

        _itemD.setLongDesc(pValue);
    }

    /**
     * Gets the productTypeCd (product type code) property
     * return String
     */
    public String getTypeCd() {

        if (_itemD == null)
            setEmptyItem();

        return _itemD.getItemTypeCd();
    }

    /**
     * Sets the productTypeCd (product type code) property
     * @param pValue  the value we want to set
     * return none
     */
    public void setTypeCd(String pValue) {

        if (_itemD == null)
            setEmptyItem();

        _itemD.setItemTypeCd(pValue);
    }

    /**
     * Gets the productStatusCd (product Status code) property
     * return String
     */
    public String getStatusCd() {

        if (_itemD == null)
            setEmptyItem();

        return _itemD.getItemStatusCd();
    }

    /**
     * Sets the productStatusCd (product Status code) property
     * @param pValue  the value we want to set
     * return none
     */
    public void setStatusCd(String pValue) {

        if (_itemD == null)
            setEmptyItem();

        _itemD.setItemStatusCd(pValue);
    }

    /**
     * Gets the productOrderNum (product order number) property
     * return int
     */
    public int getOrderNum() {

        if (_itemD == null)
            setEmptyItem();

        return _itemD.getItemOrderNum();
    }

    /**
     * Sets the productOrderNum (product order number) property
     * @param pValue  the value we want to set
     * return none
     */
    public void setOrderNum(int pValue) {

        if (_itemD == null)
            setEmptyItem();

        _itemD.setItemOrderNum(pValue);
    }

    /**
     * Gets the EffDate (effective date) property
     * return Date
     */
    public Date getEffDate() {

        if (_itemD == null)
            setEmptyItem();

        return _itemD.getEffDate();
    }

    /**
     * Sets the EffDate (effective date) property
     * @param pValue  the value we want to set
     * return none
     */
    public void setEffDate(Date pValue) {

        if (_itemD == null)
            setEmptyItem();

        _itemD.setEffDate(pValue);
    }

    /**
     * Gets the ExpDate (expiration date) property
     * return Date
     */
    public Date getExpDate() {

        if (_itemD == null)
            setEmptyItem();

        return _itemD.getExpDate();
    }

    /**
     * Sets the ExpDate (expiration date) property
     * @param pValue  the value we want to set
     * return none
     */
    public void setExpDate(Date pValue) {

        if (_itemD == null)
            setEmptyItem();

        _itemD.setExpDate(pValue);
    }

    /**
     * Gets skuNum
     * return String
     */
    public int getSkuNum() {

        if (_itemD == null)
            setEmptyItem();

        return _itemD.getSkuNum();
    }

    /**
     * Sets the SkuNum property (Cleanwise SKU number)
     * @param pValue  the value we want to set
     * return none
     */
    public void setSkuNum(int pValue) {

        if (_itemD == null)
            setEmptyItem();

        _itemD.setSkuNum(pValue);
    }

    /**
     * Gets the productUpc (product Upc code) property
     * return String
     */
    public String getUpc() {
  if(_itemMetaH.containsKey(UPC_NUM)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(UPC_NUM);
      return imD.getValue();
  }
  return "";
  /*
    if (_itemMetaD[UPC_NUM_IND] == null)

    return "";

    return _itemMetaD[UPC_NUM_IND].getValue();
  */
    }

    /**
     * Sets the productUpc (product Upc code) property
     * @param pValue  the value we want to set
     * return none
     */
    public void setUpc(String pValue) {

        String key = UPC_NUM;
        setProductAttribute(pValue, key);
    }

    /**
     * Gets the productPkgUpc (product package Upc code) property
     * return String
     */
    public String getPkgUpc() {
  if(_itemMetaH.containsKey(PKG_UPC_NUM)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(PKG_UPC_NUM);
      return imD.getValue();
  }
  return null;
    }

    /**
     * Sets the productPkgUpc (product package Upc code) property
     * @param pValue  the value we want to set
     * return none
     */
    public void setPkgUpc(String pValue) {
        String key = PKG_UPC_NUM;
        setProductAttribute(pValue, key);
    }

    //===============================================================
    public void setColor(String pValue) {
        String key = COLOR;
        setProductAttribute(pValue, key);
    }

    public String getColor() {
  if(_itemMetaH.containsKey(COLOR)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(COLOR);
      return imD.getValue();
  }
  return "";
    }

    public void setScent(String scent) {

        String key = SCENT;
        setProductAttribute(scent, key);
    }

    public String getScent() {
  if(_itemMetaH.containsKey(SCENT)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(SCENT);
      return imD.getValue();
  }
  return "";
    }

    public void setPack(String pack) {

        String key = PACK;
        setProductAttribute(pack, key);
    }

    public String getPack() {
  if(_itemMetaH.containsKey(PACK)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(PACK);
      return imD.getValue();
  }
  return "";
    }


    public void setCubeSize(String cubeSize) {

        String key = CUBE_SIZE;
        setProductAttribute(cubeSize, key);
    }

    public String getCubeSize() {
  if(_itemMetaH.containsKey(CUBE_SIZE)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(CUBE_SIZE);
      return imD.getValue();
  }
  return "";
    }


    public void setRebateBaseCost(double rebateBaseCost) {

        String key = REBATE_BASE_COST;
        setProductAttribute(Double.toString(rebateBaseCost), key);
    }

    public double getRebateBaseCost() {
  double rebateBaseCost = 0;
  if(_itemMetaH.containsKey(REBATE_BASE_COST)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(REBATE_BASE_COST);
      BigDecimal rebateBaseCostBD = new BigDecimal(imD.getValue());
      try {
    rebateBaseCost = rebateBaseCostBD.doubleValue();
      } catch (NumberFormatException exc) {
      }
  }
  return rebateBaseCost;
    }

    public void setShipWeight(String shipWeight) {

        String key = SHIP_WEIGHT;
        setProductAttribute(shipWeight, key);
    }

    public String getShipWeight() {
  if(_itemMetaH.containsKey(SHIP_WEIGHT)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(SHIP_WEIGHT);
      return imD.getValue();
  }
  return "";
    }

    public void setWeightUnit(String weightUnit) {
        String key = WEIGHT_UNIT;
        setProductAttribute(weightUnit, key);
    }

    public String getWeightUnit() {
        if (_itemMetaH.containsKey(WEIGHT_UNIT)) {
            ItemMetaData imD = (ItemMetaData) _itemMetaH.get(WEIGHT_UNIT);
            return imD.getValue();
        }
        return "";
    }

    public void setSize(String size) {

        String key = SIZE;
        setProductAttribute(size, key);
    }

    public String getSize() {
  if(_itemMetaH.containsKey(SIZE)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(SIZE);
      return imD.getValue();
  }
  return "";
    }

    public void setUom(String uom) {

        String key = UOM;
        setProductAttribute(uom, key);
    }

    public String getUom() {
  if(_itemMetaH.containsKey(UOM)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(UOM);
      return imD.getValue();
  }
  return "";
    }

    public void setPackProblemSku(boolean packProblemSku) {
        String lPackProblemSku;
        if(packProblemSku){
            lPackProblemSku = "true";
        }else{
            lPackProblemSku = "false";
        }
        String key = PACK_PROBLEM_SKU;
        setProductAttribute(lPackProblemSku, key);
    }

    public boolean isPackProblemSku() {
  if(_itemMetaH.containsKey(PACK_PROBLEM_SKU)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(PACK_PROBLEM_SKU);
      return Boolean.valueOf(imD.getValue()).booleanValue();
  }
  return false;
    }

    public void setImage(String image) {

        String key = IMAGE;
        setProductAttribute(image, key);
    }

    public String getImage() {
  if(_itemMetaH.containsKey(IMAGE)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(IMAGE);
      return imD.getValue();
  }
  return "";
    }

    public void setThumbnail(String image) {
        String key = THUMBNAIL;
        setProductAttribute(image, key);
    }

    public String getThumbnail() {
  if(_itemMetaH.containsKey(THUMBNAIL)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(THUMBNAIL);
      return imD.getValue();
  }
  return "";
    }

    public void setSpec(String spec) {

        String key = SPEC;
        setProductAttribute(spec, key);
    }

    public String getSpec() {
  if(_itemMetaH.containsKey(SPEC)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(SPEC);
      return imD.getValue();
  }
  return "";
    }

    public void setDed(String ded) {

        String key = DED;
        setProductAttribute(ded, key);
    }

    public String getDed() {
  if(_itemMetaH.containsKey(DED)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(DED);
      return imD.getValue();
  }
  return "";
    }

    public void setMsds(String msds) {

        String key = MSDS;
        setProductAttribute(msds, key);
    }

    public String getMsds() {
  if(_itemMetaH.containsKey(MSDS)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(MSDS);
      return imD.getValue();
  }
  return "";
    }

    public void setListPrice(double listPrice) {

        String key = LIST_PRICE;
        setProductAttribute(""+listPrice, key);
    }

    public double getListPrice() {
  double listPrice = 0;
  if(_itemMetaH.containsKey(LIST_PRICE)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(LIST_PRICE);
      BigDecimal listPriceBD = new BigDecimal(imD.getValue());
      try {
    listPrice = listPriceBD.doubleValue();
      } catch (NumberFormatException exc) {
      }

  }
  return listPrice;
    }

    public void setCostPrice(double costPrice) {
        String key = COST_PRICE;
        setProductAttribute(""+costPrice, key);
    }

    public double getCostPrice() {
  double costPrice = 0;
  if(_itemMetaH.containsKey(COST_PRICE)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(COST_PRICE);
      BigDecimal costPriceBD = new BigDecimal(imD.getValue());
      try {
    costPrice = costPriceBD.doubleValue();
      } catch (NumberFormatException exc) {
      }
  }
  return costPrice;
    }

    public void setUnspscCd(String unspscCd) {

        String key = UNSPSC_CD;
        setProductAttribute(unspscCd, key);
    }

    public String getUnspscCd() {
  if(_itemMetaH.containsKey(UNSPSC_CD)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(UNSPSC_CD);
      return imD.getValue();
  }
  return "";
    }

    public void setOtherDesc(String otherDesc) {

        String key = OTHER_DESC;
        setProductAttribute(otherDesc, key);
    }

    public String getOtherDesc() {
  if(_itemMetaH.containsKey(OTHER_DESC)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(OTHER_DESC);
      return imD.getValue();
  }
  return "";
    }

    private void setEmptyItem() {
        _itemD = ItemData.createValue();
        _itemD.setItemTypeCd(RefCodeNames.ITEM_TYPE_CD.PRODUCT);
        _itemD.setItemStatusCd(RefCodeNames.ITEM_STATUS_CD.INACTIVE);
    }

    ArrayList mMsgList = null;

    public void addMessage(String pMsg) {

        if (mMsgList == null) {
            mMsgList = new ArrayList();
        }

        mMsgList.add(pMsg);
    }

    public boolean hasMessages() {

        if (null == mMsgList)

            return false;

        return (mMsgList.size() > 0) ? true : false;
    }

    public ArrayList getMessages() {

        if (mMsgList == null) {
            mMsgList = new ArrayList();
        }

        return mMsgList;
    }

    private int mCostCenterId = 0;

    public void setCostCenterId(int v) {
        mCostCenterId = v;
    }

    public int getCostCenterId() {

        return mCostCenterId;
    }

    private String mCostCenterName = "";

    /**
     * Get the value of CostCenterName.
     * @return value of CostCenterName.
     */
    public String getCostCenterName() {
  return mCostCenterName;
    }

    /**
     * Set the value of CostCenterName.
     * @param v  Value to assign to CostCenterName.
     */
    public void setCostCenterName(String  v) {
  this.mCostCenterName = v;
    }

    public void setHazmat(String v) {

        String key = HAZMAT;
        setProductAttribute(v, key);
    }

    public String getHazmat() {
  if(_itemMetaH.containsKey(HAZMAT)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(HAZMAT);
      return imD.getValue();
  }
  return "false";
    }



    public void setPsn(String v) {

        String key = PSN;
        setProductAttribute(v, key);
    }

    public String getPsn() {
  if(_itemMetaH.containsKey(PSN)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(PSN);
      return imD.getValue();
  }
  return "";
    }

    public void setNsn(String v) {

        String key = NSN;
        setProductAttribute(v, key);
    }

    public String getNsn() {
  if(_itemMetaH.containsKey(NSN)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(NSN);
      return imD.getValue();
  }
  return "";
    }


    public boolean isHazmat() {
  if(_itemMetaH.containsKey(HAZMAT)) {
      ItemMetaData imD = (ItemMetaData) _itemMetaH.get(HAZMAT);
      return imD.getValue().equals("true");
  }
  return false;
    }


    public static int extractWeightValueInLbs(String pWeightString) {
        if ( null != pWeightString && pWeightString.length() > 0) {
            if ( pWeightString.indexOf(' ')> 0) {
                pWeightString = pWeightString.substring(0,pWeightString.indexOf(' '));
            }
            try {
                return Integer.parseInt(pWeightString);
            } catch (Exception e) {}
        }
        return 0;
    }

    public ItemMappingDataVector getCertifiedCompanies() {
        return _certifiedCompanies;
    }

    public void setCertifiedCompanies(ItemMappingDataVector _certifiedCompanies) {
        this._certifiedCompanies = _certifiedCompanies;
    }

    public void addCertCompaniesMapping(ItemMappingData imd) {
      boolean exist=false;
        if (_certifiedCompanies == null)
            _certifiedCompanies = new ItemMappingDataVector();

        Iterator it = _certifiedCompanies.iterator();
        while (it.hasNext()) {
            ItemMappingData md = (ItemMappingData) it.next();
            if (md.getItemId() == imd.getItemId()
                    && md.getBusEntityId() == imd.getBusEntityId()) {
                exist=true;
                break;
            }
        }
       if(!exist)
        _certifiedCompanies.add(imd);
    }


    public BusEntityDataVector getCertCompaniesBusEntityDV() {
        if(_certCompaniesBusEntityDV==null)
        _certCompaniesBusEntityDV=new BusEntityDataVector();
        return _certCompaniesBusEntityDV;
    }

    public void setCertCompaniesBusEntityDV(BusEntityDataVector certCompaniesBusEntityDV) {
        this._certCompaniesBusEntityDV = certCompaniesBusEntityDV;
    }

    public void removeCertCompaniesMapping(int busEntityId) {
        if (_certifiedCompanies != null) {
            Iterator it = _certifiedCompanies.iterator();
            while (it.hasNext()) {
                ItemMappingData imd = (ItemMappingData) it.next();
                if (imd.getBusEntityId() == busEntityId) {
                    it.remove();
                    break;
                }
            }
        }
    }

    public boolean isCertificated() {
        return _certifiedCompanies != null && _certifiedCompanies.size() > 0;
    }

    public boolean isItemGroup() {
        return _itemD != null && RefCodeNames.ITEM_TYPE_CD.ITEM_GROUP.equals(_itemD.getItemTypeCd());
    }

    public int getItemGroupId() {
        if (_catalogStructureD != null) {
            return _catalogStructureD.getItemGroupId();
        } else {
            return 0;
        }
    }
    
    public void removeCertCompaniesBusEntityDV(int busEntityId) {
        if (_certCompaniesBusEntityDV != null) {
            Iterator it = _certCompaniesBusEntityDV.iterator();
            while (it.hasNext()) {
                BusEntityData busD = (BusEntityData) it.next();
                if (busD.getBusEntityId() == busEntityId) {
                    it.remove();
                    break;
                }
            }
        }
    }

    public boolean hasItemGroup() {
        return getItemGroupId() > 0;
    }


    public ItemMetaDataVector getDataFieldProperties() {
        return this._dataFieldProperties;
    }

    public void setDataFieldProperties(ItemMetaDataVector v) {
        this._dataFieldProperties = v;
    }

    public ProductSkuView getProductSku() {
        return mProductSku;
    }

    public void setProductSku(ProductSkuView pProductSku) {
        this.mProductSku = pProductSku;
    }

    public void setProductPrice(ProductPriceView pProductPrice) {
        this.mProductPrice = pProductPrice;
    }

    public ProductPriceView getProductPrice() {
        return mProductPrice;
    }

    public void setIsContractItem(boolean pIsContractItem) {
        this.mIsContractItem = pIsContractItem;
    }

    public boolean isContractItem() {
        return mIsContractItem;
    }
    
    public String getProductJdWsUrl() {
        return mProductJdWsUrl;
    }
    
    public void setProductJdWsUrl(String pProductJdWsUrl) {
        this.mProductJdWsUrl = pProductJdWsUrl;
    }
///////////////////

	public ProductData clone() {
		ProductData pd = new ProductData();
		pd._itemD = (this._itemD==null)?null:(ItemData) this._itemD.clone();
		pd._manuBusEntityD = (this._manuBusEntityD==null)?null: (BusEntityData) this._manuBusEntityD.clone();
		pd._manuMappingD = (this._manuMappingD==null)?null: (ItemMappingData) this._manuMappingD.clone();
		pd._catalogStructureD = (this._catalogStructureD==null)?null: (CatalogStructureData) this._catalogStructureD.clone();
		pd._distrBusEntityD = (this._distrBusEntityD==null)?null: (BusEntityData) this._distrBusEntityD.clone();
		pd._distrMappingD = (this._distrMappingD==null)?null: (ItemMappingData) this._distrMappingD.clone();

		pd._distrBusEntityDV = null;
		if(this._distrBusEntityDV!=null) {
		    pd._distrBusEntityDV = new BusEntityDataVector();
			for(Iterator iter = this._distrBusEntityDV.iterator(); iter.hasNext();) {
				BusEntityData bed = (BusEntityData) iter.next();
				pd._distrBusEntityDV.add((BusEntityData)bed.clone());
			}
		}

		pd._distrMappingDV = null;
		if(this._distrMappingDV!=null) {
		    pd._distrMappingDV = new ItemMappingDataVector();		
			for(Iterator iter = this._distrMappingDV.iterator();iter.hasNext();) {
				ItemMappingData imd = (ItemMappingData) iter.next();
				pd._distrMappingDV.add((ItemMappingData)imd.clone());
			}
		}
		
		pd._catalogCategoryDV = null;
		if(this._catalogCategoryDV!=null) {
			pd._catalogCategoryDV = new CatalogCategoryDataVector();
			for(Iterator iter = this._catalogCategoryDV.iterator(); iter.hasNext();) {
				CatalogCategoryData ccd = (CatalogCategoryData) iter.next();
				pd._catalogCategoryDV.add((CatalogCategoryData)ccd.clone());
			}
		}
		
		pd._certifiedCompanies = null;
		if(this._certifiedCompanies!=null) {
			pd._certifiedCompanies = new ItemMappingDataVector();
			for(Iterator iter = this._certifiedCompanies.iterator(); iter.hasNext();) {
				 ItemMappingData imd = (ItemMappingData) iter.next();
				pd._certifiedCompanies.add((ItemMappingData)imd.clone());
			}
		}
 		
		pd._certCompaniesBusEntityDV = null;
		if(this._certCompaniesBusEntityDV!=null) {
			pd._certCompaniesBusEntityDV = new BusEntityDataVector();
			for(Iterator iter = this._certCompaniesBusEntityDV.iterator(); iter.hasNext();) {
				BusEntityData bed = (BusEntityData) iter.next();
				pd._certCompaniesBusEntityDV.add((BusEntityData)bed.clone());
			}
		}
		
		pd._dataFieldProperties = null; 
		if(this._dataFieldProperties!=null) {
			pd._dataFieldProperties = new ItemMetaDataVector();
			for(Iterator iter = this._dataFieldProperties.iterator(); iter.hasNext();) {
				ItemMetaData imd = (ItemMetaData) iter.next();
				pd._dataFieldProperties.add((ItemMetaData)imd.clone());
			}
		}
         
		pd.setProductSku((this.mProductSku==null)?null: this.mProductSku.copy());
        pd.setProductPrice((this.mProductPrice==null)?null: this.mProductPrice.copy());
		pd.setIsContractItem(this.mIsContractItem);
		pd.setProductJdWsUrl(this.mProductJdWsUrl);
		return pd;
	}
	
}
