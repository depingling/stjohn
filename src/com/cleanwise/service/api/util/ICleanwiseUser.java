package com.cleanwise.service.api.util;

import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UserData;

public interface ICleanwiseUser {
    public UserData getUser();
    public StoreData getUserStore(); 
    public boolean isAuthorizedForFunction(String pFunction);
    public java.util.Locale getPrefLocale();

}
