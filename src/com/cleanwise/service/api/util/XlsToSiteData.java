package com.cleanwise.service.api.util;

import com.cleanwise.service.api.value.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class XlsToSiteData {
    private static final Logger log = Logger.getLogger(XlsToSiteData.class);
    public static String dateFormat = "MM/dd/yy";

    public static SiteData parseXlsToSiteData(SiteXlsData beanToSave) throws IllegalArgumentException {

        if(beanToSave == null)
          throw new IllegalArgumentException("Site bean was not populated from xls");

        SiteData sd = SiteData.createValue();
        BusEntityData bed = sd.getBusEntity();
        AddressData address = sd.getSiteAddress();
        PropertyDataVector props = sd.getMiscProperties();

        PropertyDataVector siteFields = new PropertyDataVector();
        PropertyDataVector siteFieldsRuntime = new PropertyDataVector();

        PropertyData taxableIndicator = sd.getTaxableIndicator();
        PropertyData invprop = sd.getInventoryShopping();
        PropertyData invpropHoldOrder = sd.getInventoryShoppingHoldOrderUntilDeliveryDate();
        PropertyData invpropType = sd.getInventoryShoppingType();

        String cuser = "siteLoader";
        bed.setWorkflowRoleCd("UNKNOWN");
        bed.setShortDesc(beanToSave.getCustomerLocationName2());
        bed.setBusEntityStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date runDate = null;
        try {
            runDate = sdf.parse(beanToSave.getRunDate());
        } catch(ParseException e) {
            log.info("Error while parsing the date. " + e.toString());
        }
        bed.setEffDate(runDate);
        bed.setAddDate(runDate);
        bed.setModDate(runDate);
        bed.setLocaleCd("unk");
        bed.setModBy(cuser);

        address.setModBy(cuser);
        address.setName1("N/A");
        address.setName2("N/A");
        address.setAddress1(beanToSave.getCustomerAddressLine1());
        address.setAddress2(beanToSave.getCustomerAddressLine2());
        address.setCity(beanToSave.getCustomerCity());
        address.setStateProvinceCd(beanToSave.getState());
        address.setPostalCode(beanToSave.getCustZip());
        address.setCountyCd(beanToSave.getCustomerCountry());
        address.setCountryCd("USA");
        address.setPrimaryInd(true);
        address.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
        address.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);

        if(Utility.isTrue(beanToSave.getTaxable())){
          taxableIndicator.setValue("true");
          taxableIndicator.setAddBy(cuser);
        }

        Iterator itr = props.iterator();
        while (itr.hasNext()) {
          PropertyData prop = (PropertyData) itr.next();
          if (prop.getShortDesc().equals
                     (RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER)) {
            prop.setValue(beanToSave.getCustMin());
            prop.setAddBy(cuser);
          }
        }
        return sd;
    }

    public static SiteData parseXlsToSiteData(XpedxSiteView pSiteView, SiteData pSiteData) throws IllegalArgumentException {

        if (pSiteView == null) {
          throw new IllegalArgumentException("Site bean was not populated from txt file");
        }

        Date runDate = new Date();
        boolean isNew = false;
        if (pSiteData == null) {
          pSiteData = SiteData.createValue();
          isNew = true;
        }
        String cuser = "siteLoader";

        // getting address info
        AddressData siteAddressD = pSiteData.getSiteAddress();
        siteAddressD.setAddress1(pSiteView.getAddress1());
        siteAddressD.setAddress2(pSiteView.getAddress2());
        siteAddressD.setAddress3(pSiteView.getAddress3());
        siteAddressD.setAddress4(pSiteView.getAddress4());
        siteAddressD.setCity(pSiteView.getCity());
        siteAddressD.setStateProvinceCd(pSiteView.getState());
        siteAddressD.setPostalCode(pSiteView.getPostalCode());
        siteAddressD.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
        siteAddressD.setModBy(cuser);
        siteAddressD.setModDate(runDate);
        siteAddressD.setName1(pSiteView.getFirstName());
        siteAddressD.setName2(pSiteView.getLastName());
        siteAddressD.setCountryCd(pSiteView.getCountry());
        if (isNew) {
          siteAddressD.setAddBy(cuser);
          siteAddressD.setAddDate(runDate);
          siteAddressD.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
          siteAddressD.setPrimaryInd(true);
        }

        pSiteData.setSiteAddress(siteAddressD);

        // getting short description
        BusEntityData busEntityD = pSiteData.getBusEntity();
        busEntityD.setShortDesc(pSiteView.getSiteName());

        busEntityD.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
        busEntityD.setBusEntityStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);

        busEntityD.setEffDate(runDate);
        busEntityD.setModDate(runDate);
        busEntityD.setModBy(cuser);
        if (isNew) {
          busEntityD.setAddBy(cuser);
          busEntityD.setAddDate(runDate);
          busEntityD.setLocaleCd("unk");
        }

        // account id
        BusEntityData accountEntityD = pSiteData.getAccountBusEntity();
        accountEntityD.setBusEntityId(pSiteView.getAccountId());

        // set properties
        PropertyDataVector props = pSiteData.getMiscProperties();

        // site reference number
        String budgetPrefNum = pSiteView.getSiteBudgetRefNumber();
        PropertyData nprop = PropertyData.createValue();
        nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
        nprop.setValue(budgetPrefNum);
        props.add(nprop);

        // taxable property
        PropertyData taxableIndicator = pSiteData.getTaxableIndicator();
        taxableIndicator.setValue(pSiteView.getTaxeble().toString());

        // property share_order_guides
        nprop = pSiteData.getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
        if (nprop == null) {
            nprop = PropertyData.createValue();
            nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
            nprop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
            nprop.setValue(pSiteView.getShareBuyerOrderGuides().toString());
            props.add(nprop);
        } else {
            nprop.setValue(pSiteView.getShareBuyerOrderGuides().toString());
        }

        // property enable_inventory_shopping
        if (pSiteView.getEnableInentoryShopping() != null) {
            nprop = pSiteData.getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOPPING);
            if (nprop == null) {
                nprop = PropertyData.createValue();
                nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOPPING);
                nprop.setValue(pSiteView.getEnableInentoryShopping().toString());
                props.add(nprop);
            } else {
                nprop.setValue(pSiteView.getEnableInentoryShopping().toString());
            }
        }

        return pSiteData;


    }

}
