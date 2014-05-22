package com.cleanwise.view.logic.clw.tower;
import com.cleanwise.service.api.value.FreightTableData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.view.forms.CheckoutForm;
import com.cleanwise.view.forms.ShoppingCartForm;
import com.cleanwise.view.forms.UserServiceShopForm;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.CurrencyFormat;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.apps.dataexchange.OutboundTranslate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.utils.StringUtils;


/*
 import javax.crypto.*;
 import javax.crypto.interfaces.*;
 import javax.crypto.spec.*;
 import java.security.*;
 */

/**
 * Class description.
 *
 */
public class CheckoutLogic extends com.cleanwise.view.logic.CheckoutLogic {
    public ActionErrors validatePoNum (HttpServletRequest request, CheckoutForm pForm) {

    ActionErrors ae = new ActionErrors();
        String poNum = pForm.getPoNumber();
        return StringUtils.validatePoNumFourDigits(request, poNum);
    }

}

