package com.cleanwise.view.logic;

import java.rmi.RemoteException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.ContractInformation;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.ConsolidatedCartView;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.ContractDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteDataVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.WorkOrderItemData;
import com.cleanwise.view.forms.SelectShippingAddressForm;
import com.cleanwise.view.forms.ShoppingCartForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;

public class SelectShippingAddressLogic {
    private static final Logger log = Logger.getLogger(SelectShippingAddressLogic.class);

  /**
   * <code>init</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors init(HttpServletRequest request,
			  SelectShippingAddressForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    SiteDataVector siteDV = (SiteDataVector) session.getAttribute(Constants.APP_USER_SITES);
    if(siteDV == null) {
       ae.add("error", new ActionError("error.simpleGenericError","No user site information. Probably wrong logon sequence"));
       return ae;
    }
    //Check accounts
    int accountId = 0;
    boolean multiAcctFlag = false;
    SiteData[] sites = new SiteData[siteDV.size()];
    for(int ii=0; ii<siteDV.size(); ii++) {
      SiteData siteD = (SiteData) siteDV.get(ii);
      BusEntityData acctBusEntityD = siteD.getAccountBusEntity();
      if(acctBusEntityD==null) {
        ae.add("error", new ActionError("error.systemError","No account infromation for site: "+siteD.getBusEntity().getBusEntityId()));
        return ae;
      }
      sites[ii]=siteD;
      if(ii==0) {
        accountId = acctBusEntityD.getBusEntityId();
      } else {
        if(accountId!=acctBusEntityD.getBusEntityId())
         multiAcctFlag = true;
      }
    }
    pForm.setMultiAcctFlag(multiAcctFlag);
    pForm.setSites(siteDV);

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if(appUser==null) {
       ae.add("error", new ActionError("error.systemError","No registered user"));
       return ae;
    }
    pForm.setAppUser(appUser);
    return ae;
  }

  public static ActionErrors select(HttpServletRequest request,
			  SelectShippingAddressForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    String addressIndS = pForm.getShippingAddressKey();
    if(addressIndS == null) {
      ae.add("error",new ActionError("error.systemError","No address selection found"));
      return ae;
    }
    int addressInd = 0;
    try{
      addressInd = Integer.parseInt(addressIndS);
    }catch(NumberFormatException exc) {
      ae.add("error",new ActionError("error.systemError","Wrong select index format"));
      return ae;
    }
    if(addressInd==-1) {
      ae.add("error",new ActionError("error.simpleError","No address selected"));
      return ae;
    }

    SiteDataVector siteDV = pForm.getSites();
    if(siteDV.size()<addressInd || addressInd<-1) {
      ae.add("error",new ActionError("error.systemError","No address selection found"));
      return ae;
    }
    SiteData siteChosen = (SiteData) siteDV.get(addressInd);
    BusEntityData bed = siteChosen.getBusEntity();
    String siteStatus = bed.getBusEntityStatusCd();
    java.util.Date curDate = new java.util.Date();
    java.util.Date effDate = bed.getEffDate();
    java.util.Date expDate = bed.getExpDate();
    if( !(siteStatus.equals("ACTIVE")) ||
       (effDate!=null && effDate.after(curDate)) ||
       (expDate!=null && expDate.before(curDate))
    ){
      ae.add("error",new ActionError("error.systemError","Site Selected is inactive"));
      return ae;
    }
    pForm.getAppUser().setSite(siteChosen);
    ae = setShoppingSessionObjects(session,pForm.getAppUser());

    // Initialize the site shopping info.
    try {
	LogOnLogic.siteShop(request, siteChosen.getSiteId());
    }
    catch (Exception e) {
	e.printStackTrace();
    }

    return ae;
  }

  /**
   *Validates a site to verify that it has excactly one active catalog and contract.
   *@Returns an array of length 2, first position is the Catalog, 2nd position is the Contract.
   *    if the user is not contract only then only the catalog object is returned.  If this site is
   *    not confgured correctly the passed in ActionErrors is populated and null is returned.
   */
  static Object[] getCatalogAndContractForSite(HttpServletRequest request,
                                               ActionErrors ae,
                                               CatalogInformation catalogInfEjb,
                                               ContractInformation contractInfEjb,
                                               SiteData site,
                                               String pUserRoleCd) {
    CatalogData catalog = null;
    int siteId = site.getBusEntity().getBusEntityId();
    try {
	catalog = catalogInfEjb.getSiteCatalog(siteId);
    } catch(RemoteException exc) {
      exc.printStackTrace();
      if (ae != null) {
          String errorMessage = "Exception during Site Catalog access. Site ID: " + siteId;
          if (request != null) {
            errorMessage = ClwI18nUtil.getMessage(request, "login.errors.siteCatalogAccessException", new Integer[] {Integer.valueOf(siteId)});
          }
          ae.add("error", new ActionError("error.systemError", errorMessage));
      }
      return null;
    }

    if ( catalog == null ) {
      if (ae != null) {
        String errorMessage = "Shopping catalog setup is incomplete. Please contact your system administrator.";
        if (request != null) {
            errorMessage = ClwI18nUtil.getMessage(request, "login.errors.shoppingCatalogSetupIncomplete");
        }
        ae.add("error", new ActionError("error.simpleGenericError", errorMessage));

      }
      return null;
    }

    int catalogId = catalog.getCatalogId();

    //Contract Validation
    ContractDataVector contractV = null;
    try {
      contractV = contractInfEjb.getContractsCollectionByCatalog(catalogId);
    } catch(RemoteException exc) {
      exc.printStackTrace();
      if (ae != null) {
          String errorMessage = "Exception during Contract access. Catalog ID: " + catalogId;
          if (request != null) {
            errorMessage = ClwI18nUtil.getMessage(request, "login.errors.catalogContractAccessException", new Integer[] {Integer.valueOf(siteId)});
          }
          ae.add("error",new ActionError("error.systemError", errorMessage));
      }
      return null;
    }

    String roleCd = pUserRoleCd;
    boolean contractOnly = (roleCd.indexOf(Constants.UserRole.CONTRACT_ITEMS_ONLY)>=0)?true:false;
    ContractData contract = null;
    ContractDataVector ActiveCont = new ContractDataVector();



      //If we have more than one active contract, we will default to the oldest one.
      //If all contracts are inactive, a user cannot log in.
      //This is how we will lock users out of the system.
      //We do not pay attention to a contracts expiration date, that is (or will be) only used for
      //alerting customer service that the contract needs to either be extended or renegotiated.
      //Finally, if a contract is set to active, but its effective date is in the future,
      //it is considered inactive until that date.
      //find all active contracts
      for(int j=0; j<contractV.size(); j++){
        ContractData temp = (ContractData) contractV.get(j);
        Date effDate = temp.getEffDate();
        Date currDate = Constants.getCurrentDate();

        String status = temp.getContractStatusCd();

        if(status.equals(RefCodeNames.CONTRACT_STATUS_CD.ACTIVE)
           && effDate!=null
           && effDate.compareTo(currDate)<=0){
               ActiveCont.add(temp);

        }
      } //end for
      contractV = ActiveCont;

    if(contractV == null  || contractV.size() == 0) {
      if(contractOnly) {
        String errorMessage = "Shopping contract setup is incomplete. Please contact your system administrator.";
        if (request != null) {
            errorMessage = ClwI18nUtil.getMessage(request, "login.errors.shoppingContractSetupIncomplete");
        }
        ae.add("error", new ActionError("error.simpleGenericError", errorMessage));
        return null;
      }
    }else{
      contract = (ContractData) contractV.get(0);
    }

    Object[] returnVal = {catalog,contract};
    int cid = 0;
    if ( null != contract ) {
	cid = contract.getContractId();
    }
    log.info(" SelectShippingAddressLogic: "
		       + " siteId=" + siteId
		       + ", catalogId=" + catalogId
		       + ", contractId=" + cid);
    return returnVal;
  }

  /**
   *Sets up the shopping objects for this session (catalog, contract etc).
   */
  public static ActionErrors setShoppingSessionObjects(HttpSession pSession, CleanwiseUser pAppUser)
  {
    ActionErrors ae = new ActionErrors();

    APIAccess factory = (APIAccess) pSession.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }

    // Account
    Account accountBean = null;

    //Catalog
    CatalogInformation catalogInfEjb = null;
    ContractInformation contractInfEjb = null;
    try {
      catalogInfEjb = factory.getCatalogInformationAPI();
      contractInfEjb = factory.getContractInformationAPI();
      accountBean = factory.getAccountAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","System error 101"));
      return ae;
    }


    SiteData site = pAppUser.getSite();
    int siteId = site.getBusEntity().getBusEntityId();

    CatalogData catalog;
    Object[] catalogContract = getCatalogAndContractForSite(null,
                                                            ae,
                                                            catalogInfEjb,
                                                            contractInfEjb,
                                                            site,
                                                            pAppUser.getUser().getUserRoleCd());

    if(catalogContract == null){
        ae.add("error",new ActionError("error.systemError","Catalog/Contract missing for " +
        site.getBusEntity().getShortDesc()));
        return ae;
    }
    catalog = (CatalogData) catalogContract[0];

    //CatalogData catalog = (CatalogData) catalogV.get(0);
    int catalogId = catalog.getCatalogId();
    pSession.setAttribute(Constants.CATALOG_ID,new Integer(catalogId));

    //Contract
    ContractData contract = (ContractData) catalogContract[1];
    int contractId = 0;
    if(contract != null){
        contractId = contract.getContractId();
        pSession.setAttribute(Constants.CATALOG_LOCALE,contract.getLocaleCd());
        pSession.setAttribute(Constants.CATALOG_DECIMALS,new Integer(-1));
    }
    pSession.setAttribute(Constants.CONTRACT_ID,new Integer(contractId));

    StoreData store = pAppUser.getUserStore();
    if(store==null) {
      ae.add("error",new ActionError("error.systemError","No order Ejb pointer"));
      return ae;
    }

   if (pAppUser.getUserAccount() == null ||
           (pAppUser.getUserAccount().getBusEntity().getBusEntityId() != site.getAccountId() && site.getAccountId() > 0)) {
          // set/reset the account
          try {
              AccountData ad = accountBean.getAccount(site.getAccountId(), 0);
              pAppUser.setUserAccount(ad);
          } catch (Exception exc) {
              ae.add("error", new ActionError("error.systemError", "Exception, account "  + site.getAccountId() +" is not available."));
              return ae;
          }
      }

    AccountData account = pAppUser.getUserAccount();
    if (account == null) {
        ae.add("error", new ActionError("error.systemError", "No account information was loaded"));
        return ae;
    }

    Order orderEjb = null;
    try {
      orderEjb = factory.getOrderAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error",new ActionError("error.systemError","No order Ejb pointer"));
      return ae;
    }
    try {
      orderEjb.initOrderNumber(store.getBusEntity().getBusEntityId(), pAppUser.getUser().getUserName());
    } catch(RemoteException exc) {
      ae.add("error",new ActionError("error.systemError","Exception in getShoppingCart mathod of ShoppingServiceBean"));
      return ae;
    }

    //Shopping cart
    ShoppingServices shoppingServEjb = null;
    try {
      shoppingServEjb = factory.getShoppingServicesAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error",new ActionError("error.systemError","No shopping services Ejb pointer"));
      return ae;
    }

    PropertyData storeTypeProperty = store.getStoreType();
    if(storeTypeProperty==null) {
      ae.add("error",new ActionError("error.systemError","No store type information was loaded"));
      return ae;
    }


    ShoppingCartData shoppingCartD =
    (ShoppingCartData)pSession.getAttribute(Constants.SHOPPING_CART);
    boolean reloadFl = true;
    if(reloadFl) {
      if(shoppingCartD instanceof ConsolidatedCartView) reloadFl = false;
    }
    if(reloadFl) {
      if(!ShopTool.canSaveShoppingCart(pSession)) reloadFl = false;
    }
    if(reloadFl) {
        if (shoppingCartD != null) {
            WorkOrderItemData workOrderItemD = shoppingCartD.getWorkOrderItem();
            if (workOrderItemD != null && workOrderItemD.getWorkOrderItemId() > 0) {
                reloadFl = false;
            }
        }
    }
    if(reloadFl && shoppingCartD!=null) {
      OrderData oD = shoppingCartD.getPrevOrderData();
      SiteData sD = shoppingCartD.getSite();
      if(oD!=null && sD!=null) {
        if(siteId==oD.getSiteId() && siteId==sD.getBusEntity().getBusEntityId()) {
          reloadFl = false;
        }
      }
    }

    if(reloadFl) {
     ShoppingCartData oldcart = shoppingCartD;
      try {
    	  String userName2 = (String) pSession.getAttribute(Constants.UNIQUE_NAME);
        shoppingCartD = shoppingServEjb.getShoppingCart
            (storeTypeProperty.getValue(),
             pAppUser.getUser(),
             userName2,
             pAppUser.getSite(),
             catalogId,
             contractId,
            SessionTool.getCategoryToCostCenterView(pSession, pAppUser.getSite().getSiteId(), catalogId));
      } catch(Exception exc) {
        ae.add("error",new ActionError("error.systemError",
        "Failed to get the shopping cart data"));
        return ae;
      }

      if ( oldcart != null &&
          oldcart.getPrevOrderData() != null &&
          oldcart.getSite() != null &&
          oldcart.getSite().getBusEntity() != null &&
          oldcart.getSite().getBusEntity().getBusEntityId() == siteId ) {
          shoppingCartD.setPrevOrderData(oldcart.getPrevOrderData());
	      shoppingCartD.setPrevRushCharge(oldcart.getPrevRushCharge());
      }
      if ( oldcart != null) {
          shoppingCartD.setWarningMessages(oldcart.getWarningMessages());
      }

      pSession.setAttribute(Constants.SHOPPING_CART, shoppingCartD);
    }

    ShopTool.reloadInvShoppingCart(shoppingServEjb,
            pSession,
           (ShoppingCartData)pSession.getAttribute(Constants.INVENTORY_SHOPPING_CART),
            pAppUser.getSite(), pAppUser.getUser(),storeTypeProperty.getValue());

    ShoppingCartForm scf = new ShoppingCartForm();
    scf.setShoppingCart(shoppingCartD);
    scf.setSiteInventory(pAppUser.getSite().getSiteInventory());
    pSession.setAttribute(Constants.SHOPPING_CART_FORM,scf);

    log.info(" ---- Shopping objects retrieved. ---- " +
		       " siteId=" + siteId +
		       " contractId=" + contractId +
		       " catalogId=" + catalogId +
		       " userId=" + pAppUser.getUser().getUserId() +
		       " accountId=" + site.getAccountId() +
		       " storeId=" + store.getStoreId()
		       );
    return ae;
  }
}





