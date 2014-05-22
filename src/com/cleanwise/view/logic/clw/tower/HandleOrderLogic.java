package com.cleanwise.view.logic.clw.tower;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import com.cleanwise.view.forms.CheckoutForm;
import com.cleanwise.view.forms.OrderOpDetailForm;
import com.cleanwise.view.forms.ContactUsForm;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.pdf.PdfOrder;
import com.cleanwise.view.utils.pdf.PdfOrderStatus;
import java.io.ByteArrayOutputStream;

import java.rmi.*;

import java.text.NumberFormat;

import java.util.*;

import javax.ejb.*;

import javax.naming.*;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;


/**
 *  <code>HandleOrderLogic</code> implements the logic
 *  to manipulate Orders which require approval or rejection.
 *
 */
public class HandleOrderLogic extends com.cleanwise.view.logic.HandleOrderLogic {


    public ActionErrors validatePoNum (HttpServletRequest request, String pPoNum) {
      return StringUtils.validatePoNumFourDigits(request, pPoNum);
    }


}
