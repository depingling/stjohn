package com.cleanwise.service.api.meta;

import com.cleanwise.service.api.cachecos.ObjectMeta;
import com.cleanwise.service.api.cachecos.TableField;
import com.cleanwise.service.api.cachecos.VarMeta;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.CountryPropertyData;
import com.cleanwise.service.api.value.StoreData;

public class StoreDataMeta extends ObjectMeta {

    public StoreDataMeta(StoreData pData) {

        super();

        {
            VarMeta storeVarMeta = new VarMeta();
            storeVarMeta.add(new BusEntityDataMeta(pData.getBusEntity()));
            add(storeVarMeta);
        }

        {
            VarMeta propertyVarMeta = new VarMeta();
            propertyVarMeta.add(new PropertyDataMeta(new TableField(PropertyDataAccess.BUS_ENTITY_ID, pData.getBusEntity().getBusEntityId())));
            add(propertyVarMeta);
        }

        {
            VarMeta emailVarMeta = new VarMeta();
            emailVarMeta.add(new EmailDataMeta(new TableField(EmailDataAccess.BUS_ENTITY_ID, pData.getBusEntity().getBusEntityId())));
            add(emailVarMeta);
        }

        {
            VarMeta addressVarMeta = new VarMeta();
            addressVarMeta.add(new AddressDataMeta(new TableField(AddressDataAccess.BUS_ENTITY_ID, pData.getBusEntity().getBusEntityId())));
            add(addressVarMeta);
        }

        {
            VarMeta phoneVarMeta = new VarMeta();
            phoneVarMeta.add(new PhoneDataMeta(new TableField(PhoneDataAccess.BUS_ENTITY_ID, pData.getBusEntity().getBusEntityId())));
            add(phoneVarMeta);
        }

        {
            VarMeta countriesVarMeta = new VarMeta();
            countriesVarMeta.add(new CountryDataMeta(new TableField(CountryDataAccess.COUNTRY_CODE, pData.getPrimaryAddress().getCountryCd())));
            if (pData.getCountryProperties() != null) {
                for (Object oCountryProp : pData.getCountryProperties()) {
                    CountryPropertyData countryProperty = (CountryPropertyData) oCountryProp;
                    countriesVarMeta.add(new CountryPropertyDataMeta(new TableField(CountryPropertyDataAccess.COUNTRY_ID, countryProperty.getCountryId())));
                }
            }
            add(countriesVarMeta);
        }
    }
}
