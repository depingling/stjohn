package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import com.cleanwise.service.api.value.BillToData;

/**
 * @author Alexander Chikin
 * Date: 15.08.2006
 * Time: 2:16:42
 * Form bean for the user manager page.
 */
public class StoreAccountBillToForm  extends ActionForm {

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
