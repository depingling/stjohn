package com.cleanwise.service.api.util;

//import com.cleanwise.service.api.session.AddressValidator;
//import com.cleanwise.service.api.session.AddressValidatorHome;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.SiteHome;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.JNDINames;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteTxtData;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Iterator;


public class TxtToSiteData {    

    public static SiteData parseTxtToSiteData(SiteTxtData beanToSave, SiteData sd) throws IllegalArgumentException {

        if(beanToSave == null)
          throw new IllegalArgumentException("Site bean was not populated from xls");

        if(sd == null) 
            sd = SiteData.createValue();
        
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
        bed.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
        bed.setShortDesc(beanToSave.getLocationName());
        bed.setBusEntityStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);        
                
        Date runDate = new Date();
        bed.setEffDate(runDate);
        bed.setAddDate(runDate);
        bed.setModDate(runDate);
        bed.setLocaleCd("unk");
        bed.setModBy(cuser);
        
        address.setModBy(cuser);
        address.setName1("N/A");
        address.setName2("N/A");
        address.setAddress1(beanToSave.getAddress1());
        address.setAddress2(beanToSave.getAddress2());
        address.setCity(beanToSave.getCity());
        address.setStateProvinceCd(beanToSave.getState());
        address.setPostalCode(beanToSave.getZip());
        address.setCountryCd(beanToSave.getCountry());        
        address.setPrimaryInd(true);
        address.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
        address.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
        
        //site budget reference number
        PropertyData nprop = PropertyData.createValue();
        nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
        nprop.setValue(beanToSave.getLocationNumber());
        nprop.setAddBy(cuser);
        props.add(nprop);
        
        return sd;
    }
}
