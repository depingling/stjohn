package com.cleanwise.view.forms;

/**
 *
 *@author     durval
 */

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.*;
import java.util.*;

public final class AccountShoppingControlForm 
  extends ShoppingControlForm {

    private int mAccountId;
    public void setAccountId(String pAccountId) { 
	mAccountId = Integer.parseInt(pAccountId); 
    }
    public void setAccountId(int pAccountId) { mAccountId = pAccountId; }
    public int getAccountId() { return mAccountId; }

}

