package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.AddressData;

public class CustomerAddressForm extends ActionForm {

    private AddressData mAddress;
    public void setAddress(AddressData v) {
        mAddress = v;
    }
    public AddressData getAddress() {
        if ( null == mAddress ) {
            mAddress = AddressData.createValue();
        }
        return mAddress;
    }

}

