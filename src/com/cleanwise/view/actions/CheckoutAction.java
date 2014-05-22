
package com.cleanwise.view.actions;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.forms.CheckoutForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.CheckoutLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;

/**
 * Implementation of <strong>Action</strong> that processes the
 * user shopping functions.
 *
 * @author durval
 */
public final class CheckoutAction extends ActionSuper {
    private static final Logger log = Logger.getLogger(CheckoutAction.class);

    // ---------------------------------------------------- Public Methods

    protected boolean isRequiresConfidentialConnection(){
        return true;
    }

    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form an <code>ActionForm</code> value
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @return an <code>ActionForward</code> value
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet exception occurs
     */
    public ActionForward performSub(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {

        String mappingAddress = "display";

        try {

            // Is there a currently logged on user?
            SessionTool st = new SessionTool(request);
            if ( st.checkSession() == false ) {
                return mapping.findForward("/userportal/logon");
            }

            String action = (String) request.getParameter("action");

            CheckoutForm theForm = (CheckoutForm) form;
            theForm.setConfirmMessage(null);

            if (action==null || action.trim().length()==0) action = "init";

            log.info("===action: " + action);
            ActionErrors ae = new ActionErrors();

            if ( action.equals("init") || action.equals("display") ) {
                if (isUseXiPay(request)) {
                    request.getSession().setAttribute(Constants.PAYMETRICS, "true");
                } else {
                    request.getSession().setAttribute(Constants.PAYMETRICS, "false");
                }
                request.getSession().setAttribute(Constants.PAYMETRICS_CC, "false");
                ae = CheckoutLogic.init(request,theForm);
                if(ae.size()>0) {
                    saveErrors(request,ae);
                    //mappingAddress = "userHome";
                }
            }
            else if (action.equals("recalc")) {
              ae = CheckoutLogic.recalculateDependencies(request,theForm,false);
              if(ae.size()>0) {
                saveErrors(request,ae);
              }
            }
            else if(action.equals("creditCard")) {
                request.getSession().setAttribute(Constants.PAYMETRICS_CC, "true");
                request.getSession().setAttribute("amount", theForm.getHandlingAmtString());
                request.getSession().setAttribute("paymetrics_guid", System.getProperty("DataIntercept.GUID"));
                request.getSession().setAttribute("paymetrics_key", System.getProperty("DataIntercept.Key"));
                log.info("===PAYMETRICS_CC: " + request.getSession().getAttribute(Constants.PAYMETRICS_CC));
            }
            else if(action.equals("placeOrder")) {
            	if(lockPurchasing(request,ae)){
            		ae = CheckoutLogic.placeOrder(request,theForm);
            		unLockPurchasing(request);
            	}

                if(ae.size()>0) {
                    saveErrors(request,ae);
                }else{
                    CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
                    if(RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.equals(appUser.getUserAccount().getCustomerSystemApprovalCd())){
                        mappingAddress = "customerSystemPostRedirect";
                    }
                }
            } else if ("initOrderService".equals(action)) {

                    ae = CheckoutLogic.initOrderService(request,theForm);
                    if(ae.size()>0) {
                        saveErrors(request,ae);
                    }
              }
            else if (action.equals("submit")) {
               String command = request.getParameter("command");
               if(command==null) command = "";
                if("placeOrder".equals(command) ||
                    request.getParameter("placeOrder.x")!=null) {
                    if("true".equals(request.getSession().getAttribute(Constants.PAYMETRICS_CC))) {
                       ae = CheckoutLogic.paymetricAuthorization(request,theForm);
                       if(ae.size()>0) {
                         saveErrors(request,ae);
                       }else{
                          if (lockPurchasing(request, ae)) {
                             ae = CheckoutLogic.placeOrderSave(request, theForm);
                             unLockPurchasing(request);
                          }
                       }
                    } else {
                        if (lockPurchasing(request, ae)) {
                           ae = CheckoutLogic.placeOrder(request, theForm);
                           unLockPurchasing(request);
                        }
                    }
                    if(ae.size()>0) {
                        saveErrors(request,ae);
                    }else{
                        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
                        if(RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.equals(appUser.getUserAccount().getCustomerSystemApprovalCd())){
                            mappingAddress = "customerSystemPostRedirect";
                        }
                    }
                } else if("creditCard".equals(command)) {
                    ae = CheckoutLogic.placeOrderVerify(request, theForm);
                    unLockPurchasing(request);
                    if(ae.size()>0) {
                       saveErrors(request,ae);
                     }else{
                       request.getSession().setAttribute(Constants.PAYMETRICS_CC, "true");
                       request.getSession().setAttribute("paymetrics_guid", System.getProperty("DataIntercept.GUID"));
                       request.getSession().setAttribute("paymetrics_key", System.getProperty("DataIntercept.Key"));

                       BigDecimal rushOrderCharge = new BigDecimal(0);
                       if(Utility.isSet(ShopTool.getRushOrderCharge(request))) {
                           String rushOrderChargeS = theForm.getRushChargeAmtString();
                            if(rushOrderChargeS==null) rushOrderChargeS = "";
                            try {
                              rushOrderCharge = new BigDecimal(rushOrderChargeS);
                            } catch (Exception exc) {}
                       }
                       BigDecimal lTotal = theForm.getCartAmt(request);
                       lTotal = lTotal.add(theForm.getFreightAmt());
                       lTotal = lTotal.add(theForm.getFuelSurchargeAmt()!=null?theForm.getFuelSurchargeAmt():new BigDecimal(0));
                       lTotal = lTotal.add(theForm.getSmallOrderFeeAmt()!=null?theForm.getSmallOrderFeeAmt():new BigDecimal(0));
                       lTotal = lTotal.add(theForm.getHandlingAmt());
                       lTotal = lTotal.add(rushOrderCharge);
                       lTotal = lTotal.add(theForm.getDiscountAmt());
                       lTotal = lTotal.add(theForm.getSalesTax());
                       String lTotalS = ClwI18nUtil.getPriceForPaymetric(request, lTotal, "noLocale", null);
                       request.getSession().setAttribute("paymetrics_amount", lTotalS);
                       String currency = ClwI18nUtil.getCurrencyGlobalCode(request);
                       request.getSession().setAttribute("paymetrics_currency", currency);
                     }
                } else if("placeOrderForSelected".equals(command) ||
                           request.getParameter("placeOrderForSelected.x")!=null) {
                	if(lockPurchasing(request,ae)){
                		ae = CheckoutLogic.placeRushOrder(request,theForm);
                		unLockPurchasing(request);
                	}

                    if(ae.size()>0) {
                        saveErrors(request,ae);
                    }
                }
                else if("orderService".equals(command))
                {
                    ae = CheckoutLogic.orderService(request,theForm);
                    if(ae.size()>0) {
                        saveErrors(request,ae);
                    }
                }
               else if("updateCart".equals(command) ||
                          request.getParameter("updateCart.x")!=null) {
                    ae = CheckoutLogic.updateCartInfo(request,theForm);
                    if(ae.size()>0) {
                        saveErrors(request,ae);
                    }
                } else if("orderSelected".equals(command) ||
                          request.getParameter("orderSelected.x")!=null) {
                    ae = CheckoutLogic.previewOrder(request,theForm);
                    if(ae.size()>0) {
                        saveErrors(request,ae);
                    }
                    mappingAddress = "orderSelected";
                } else if("recalc".equals(command) ||
                           request.getParameter("recalc.x")!=null) {
                    ae = CheckoutLogic.recalculateDependencies(request,theForm,false);
                    if(ae.size()>0) {
                        saveErrors(request,ae);
                    }
                } else if("placeOrderForAll".equals(command)) {
                	if(lockPurchasing(request,ae)){
                		ae = CheckoutLogic.placeOrderForAll(request,theForm);
                		unLockPurchasing(request);
                	}

                    if(ae.size()>0) {
                        saveErrors(request,ae);
                    }
                }

                else if("freightTypeChanged".equals(command)) {
                    ae = CheckoutLogic.freightTypeChanged(request,theForm);
                    if(ae.size()>0) {
                        saveErrors(request,ae);
                    }
                }

                else {
                    ae = CheckoutLogic.sort(request,theForm);
                    if(ae.size()>0) {
                        saveErrors(request,ae);
                    }
                }
            }
            else if ( action.equals("sort") ) {
                ae = CheckoutLogic.sort(request,theForm);
                if(ae.size()>0) {
                    saveErrors(request,ae);
                }
            }
            else{
                ae = new ActionErrors();
                ae.add("error",new ActionError("error.systemError","Unknown action: ["+Utility.encodeForHTML(action)+"]"));                
                saveErrors(request,ae);
            }
        }
        catch ( Exception e ) {
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            e.printStackTrace();
            mappingAddress = "error";
            unLockPurchasing(request);
        }

        return mapping.findForward(mappingAddress);
    }

    private static final String lockName = "==MasterCheckOutLock==";
    private void unLockPurchasing(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	Integer lockValue = new Integer("0");
	    session.setAttribute(lockName, lockValue);
    }

    private boolean lockPurchasing(HttpServletRequest request, ActionErrors ae) {
		String masterLock = System.getProperty("MASTER_LOCK_DISABLE");
		if (Utility.isTrue(masterLock)) {
			return true;
		}
		HttpSession session = request.getSession();

		Integer lockValue = null;
		try {
			synchronized (session) {
				lockValue = (Integer) session.getAttribute(lockName);
				if (lockValue == null) {
					lockValue = new Integer(0);
					session.setAttribute(lockName, lockValue);
				}
				if (lockValue == null) {
					lockValue = new Integer(0);
					session.setAttribute(lockName, lockValue);
				}
			}
			if (lockValue.intValue() == 0) {
				lockValue = new Integer(1);
				session.setAttribute(lockName, lockValue);
				return true;
			} else {

				// The lock value is 1.  Meaning a purchase is
				// already in progress.
				String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.purchaseIsInProgress", null);
				ae.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.simpleGenericError", errorMess));
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			lockValue = new Integer(0);
		    session.setAttribute(lockName, lockValue);
			return false;
		}

	}

        protected boolean isUseXiPay(HttpServletRequest request) {
            final String USE_XI_PAY_FL = "USE_XI_PAY_FL";
            Boolean useXiPay = (Boolean) request.getSession().getAttribute(USE_XI_PAY_FL);
            if(useXiPay == null) {
                try {
                CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
                useXiPay = Utility.isTrue(PropertyUtil.getPropertyValue(appUser.getUserStore().getMiscProperties(),
                                       RefCodeNames.PROPERTY_TYPE_CD.USE_XI_PAY));
                request.getSession().setAttribute(USE_XI_PAY_FL, useXiPay);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return false;
                }
            }
            return useXiPay.booleanValue();
        }


}
