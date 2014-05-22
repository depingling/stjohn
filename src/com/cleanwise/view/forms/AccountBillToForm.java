package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;

public class AccountBillToForm extends ActionForm {

    private BillToData mBillToData;
    public void setBillTo(BillToData v) {
        mBillToData = v;
    }
    public BillToData getBillTo() {
        if ( null == mBillToData ) {
            mBillToData = BillToData.createValue();
        }
        return mBillToData;
    }

}

