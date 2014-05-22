package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;

import javax.servlet.http.HttpServletRequest;


public class Admin2SiteDetailMgrForm extends StorePortalBaseForm {

    public static final String CLONE_WITHOUT_RELATIONSHIPS = "CLONE_WITHOUT_RELATIONSHIPS";
    public static final String CLONE_WITH_RELATIONSHIPS = "CLONE_WITH_RELATIONSHIPS";

    private boolean init;

    private Integer blanketPoNumId;
    private String name;
    private String statusCd;
    private String typeCd;
    private String siteNumber;
    private String oldSiteNumber;
    private String effDate;
    private SiteData siteData;
    private String expDate;
    private String subContractor;
    private String targetFacilityRank;
    private String taxableIndicator;
    private String inventoryShopping;
    private String inventoryShoppingType;
    private String inventoryShoppingHoldOrderUntilDeliveryDate;
    private String accountId;
    private String accountName;
    private BusEntityFieldsData siteFields;
    private int storeId;
    private String storeName;
    private String name1;
    private String name2;
    private String postalCode;
    private String stateOrProv;
    private String county;
    private String country;
    private String city;
    private String streetAddr1;
    private String streetAddr2;
    private String streetAddr3;
    private String streetAddr4;
    private String comments;
    private String shippingMessage;
    private String siteReferenceNumber;
    private String distSiteReferenceNumber;
    private String bypassOrderRouting;
    private String consolidatedOrderWarehouse;
    private String shareBuyerOrderGuides;
    private String lineLevelCode;
    private String f1Value;
    private String f2Value;
    private String f3Value;
    private String f4Value;
    private String f5Value;
    private String f6Value;
    private String f7Value;
    private String f8Value;
    private String f9Value;
    private String f10Value;
    private IdVector availableShipto;
    private String ERPEnabled;
    private String authorizedReSaleAccount;
    private String makeShiptoBillto;
    private BlanketPoNumDataVector blanketPos;
    private String cloneCode;
    private boolean clone;

    private AccountDataVector mAccountFilter;
    private int cloneId;

    public boolean isInit() {
        return init;
    }

    public void init() {
        this.init= true;
    }

    public void setBlanketPoNumId(Integer blanketPoNumId) {
        this.blanketPoNumId = blanketPoNumId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }

    public void setSiteNumber(String siteNumber) {
        this.siteNumber = siteNumber;
    }

    public void setOldSiteNumber(String oldSiteNumber) {
        this.oldSiteNumber = oldSiteNumber;
    }

    public void setEffDate(String effDate) {
        this.effDate = effDate;
    }

    public void setSiteData(SiteData siteData) {
        this.siteData = siteData;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public void setSubContractor(String subContractor) {
        this.subContractor = subContractor;
    }

    public void setTargetFacilityRank(String targetFacilityRank) {
        this.targetFacilityRank = targetFacilityRank;
    }

    public void setTaxableIndicator(String taxableIndicator) {
        this.taxableIndicator = taxableIndicator;
    }

    public void setInventoryShopping(String inventoryShopping) {
        this.inventoryShopping = inventoryShopping;
    }

    public void setInventoryShoppingType(String inventoryShoppingType) {
        this.inventoryShoppingType = inventoryShoppingType;
    }

    public void setInventoryShoppingHoldOrderUntilDeliveryDate(String inventoryShoppingHoldOrderUntilDeliveryDate) {
        this.inventoryShoppingHoldOrderUntilDeliveryDate = inventoryShoppingHoldOrderUntilDeliveryDate;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setSiteFields(BusEntityFieldsData siteFields) {
        this.siteFields = siteFields;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }


    public void setName1(String name1) {
        this.name1 = name1;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setStateOrProv(String stateOrProv) {
        this.stateOrProv = stateOrProv;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreetAddr1(String streetAddr1) {
        this.streetAddr1 = streetAddr1;
    }

    public void setStreetAddr2(String streetAddr2) {
        this.streetAddr2 = streetAddr2;
    }

    public void setStreetAddr3(String streetAddr3) {
        this.streetAddr3 = streetAddr3;
    }

    public void setStreetAddr4(String streetAddr4) {
        this.streetAddr4 = streetAddr4;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setShippingMessage(String shippingMessage) {
        this.shippingMessage = shippingMessage;
    }

    public void setSiteReferenceNumber(String siteReferenceNumber) {
        this.siteReferenceNumber = siteReferenceNumber;
    }

    public void setDistSiteReferenceNumber(String distSiteReferenceNumber) {
        this.distSiteReferenceNumber = distSiteReferenceNumber;
    }

    public void setBypassOrderRouting(String bypassOrderRouting) {
        this.bypassOrderRouting = bypassOrderRouting;
    }

    public void setConsolidatedOrderWarehouse(String consolidatedOrderWarehouse) {
        this.consolidatedOrderWarehouse = consolidatedOrderWarehouse;
    }

    public void setShareBuyerOrderGuides(String shareBuyerOrderGuides) {
        this.shareBuyerOrderGuides = shareBuyerOrderGuides;
    }

    public void setLineLevelCode(String lineLevelCode) {
        this.lineLevelCode = lineLevelCode;
    }

    public void setF1Value(String f1Value) {
        this.f1Value = f1Value;
    }

    public void setF2Value(String f2Value) {
        this.f2Value = f2Value;
    }

    public void setF3Value(String f3Value) {
        this.f3Value = f3Value;
    }

    public void setF4Value(String f4Value) {
        this.f4Value = f4Value;
    }

    public void setF5Value(String f5Value) {
        this.f5Value = f5Value;
    }

    public void setF6Value(String f6Value) {
        this.f6Value = f6Value;
    }

    public void setF7Value(String f7Value) {
        this.f7Value = f7Value;
    }

    public void setF8Value(String f8Value) {
        this.f8Value = f8Value;
    }

    public void setF9Value(String f9Value) {
        this.f9Value = f9Value;
    }

    public void setF10Value(String f10Value) {
        this.f10Value = f10Value;
    }

    public void setAvailableShipto(IdVector availableShipto) {
        this.availableShipto = availableShipto;
    }

    public void setERPEnabled(String ERPEnabled) {
        this.ERPEnabled = ERPEnabled;
    }


    public Integer getBlanketPoNumId() {
        return blanketPoNumId;
    }

    public String getName() {
        return name;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public String getTypeCd() {
        return typeCd;
    }

    public String getSiteNumber() {
        return siteNumber;
    }

    public String getOldSiteNumber() {
        return oldSiteNumber;
    }

    public SiteData getSiteData() {
        return siteData;
    }

    public String getSubContractor() {
        return subContractor;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getEffDate() {
        return effDate;
    }

    public String getTargetFacilityRank() {
        return targetFacilityRank;
    }

    public String getTaxableIndicator() {
        return taxableIndicator;
    }

    public String getInventoryShoppingType() {
        return inventoryShoppingType;
    }

    public String getInventoryShopping() {
        return inventoryShopping;
    }

    public String isInventoryShoppingHoldOrderUntilDeliveryDate() {
        return inventoryShoppingHoldOrderUntilDeliveryDate;
    }

     public String getInventoryShoppingHoldOrderUntilDeliveryDate() {
        return inventoryShoppingHoldOrderUntilDeliveryDate;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public BusEntityFieldsData getSiteFields() {
        return siteFields;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getStateOrProv() {
        return stateOrProv;
    }

    public String getCounty() {
        return county;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getStreetAddr1() {
        return streetAddr1;
    }

    public String getStreetAddr2() {
        return streetAddr2;
    }

    public String getStreetAddr3() {
        return streetAddr3;
    }

    public String getStreetAddr4() {
        return streetAddr4;
    }

    public String getComments() {
        return comments;
    }

    public String getSiteReferenceNumber() {
        return siteReferenceNumber;
    }

    public String getShippingMessage() {
        return shippingMessage;
    }

    public String getDistSiteReferenceNumber() {
        return distSiteReferenceNumber;
    }

    public String isBypassOrderRouting() {
        return bypassOrderRouting;
    }

    public String getBypassOrderRouting() {
        return bypassOrderRouting;
    }

    public String  getConsolidatedOrderWarehouse() {
        return consolidatedOrderWarehouse;
    }

    public String isConsolidatedOrderWarehouse() {
        return consolidatedOrderWarehouse;
    }

    public String isShareBuyerOrderGuides() {
        return shareBuyerOrderGuides;
    }

    public String getShareBuyerOrderGuides() {
        return shareBuyerOrderGuides;
    }

    public String getLineLevelCode() {
        return lineLevelCode;
    }

    public String getF1Value() {
        return f1Value;
    }

    public String getF2Value() {
        return f2Value;
    }

    public String getF3Value() {
        return f3Value;
    }

    public String getF4Value() {
        return f4Value;
    }

    public String getF6Value() {
        return f6Value;
    }

    public String getF5Value() {
        return f5Value;
    }

    public String getF7Value() {
        return f7Value;
    }

    public String getF8Value() {
        return f8Value;
    }

    public String getF9Value() {
        return f9Value;
    }

    public String getF10Value() {
        return f10Value;
    }

    public IdVector getAvailableShipto() {
        return availableShipto;
    }

    public String isERPEnabled() {
        return ERPEnabled;
    }

    public void setAuthorizedReSaleAccount(String authorizedReSaleAccount) {
        this.authorizedReSaleAccount = authorizedReSaleAccount;
    }

    public void setMakeShiptoBillto(String makeShiptoBillto) {
        this.makeShiptoBillto = makeShiptoBillto;
    }

    public String isAuthorizedReSaleAccount() {
        return authorizedReSaleAccount;
    }

    public String isMakeShiptoBillto() {
        return makeShiptoBillto;
    }

    public void setBlanketPos(BlanketPoNumDataVector blanketPos) {
        this.blanketPos = blanketPos;
    }

    public BlanketPoNumDataVector getBlanketPos() {
        return blanketPos;
    }

    public AccountDataVector getAccountFilter() {
        return mAccountFilter;
    }

    public void setAccountFilter(AccountDataVector mAccountFilter) {
        this.mAccountFilter = mAccountFilter;
    }

    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {

        setBypassOrderRouting(String.valueOf(false));
        setConsolidatedOrderWarehouse(String.valueOf(false));
        setInventoryShopping(String.valueOf(false));
        setInventoryShoppingType(String.valueOf(false));
        setInventoryShoppingHoldOrderUntilDeliveryDate(String.valueOf(false));

    }

    public void setCloneCode(String cloneCode) {
        this.cloneCode = cloneCode;
    }


    public String getCloneCode() {
        return cloneCode;
    }

    public void setClone(boolean clone) {
        this.clone = clone;
    }

    public boolean isClone() {
        return clone;
    }

    public void setCloneId(int cloneId) {
        this.cloneId = cloneId;
    }

    public int getCloneId() {
        return cloneId;
    }
}
