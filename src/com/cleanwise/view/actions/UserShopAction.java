
package com.cleanwise.view.actions;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.view.forms.ShoppingCartForm;
import com.cleanwise.view.forms.UserShopForm;
import com.cleanwise.view.logic.ShoppingCartLogic;
import com.cleanwise.view.logic.UserShopLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import org.apache.struts.action.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.espendwise.view.logic.esw.ShoppingLogic;


/**
 * Implementation of <strong>Action</strong> that processes the
 * user shopping functions.
 *
 * @author durval
 */
public final class UserShopAction extends ActionSuper
{

    // ---------------------------------------------------- Public Methods


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
    throws IOException, ServletException
    {
		ActionForward af = 
			performSubSynch(mapping, form, request, response);
		return af;

    }
	 
    public synchronized ActionForward performSubSynch(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
    throws IOException, ServletException
	{

        String action = request.getParameter("action");
        String command = request.getParameter("command");

        UserShopForm theForm = (UserShopForm) form;
        //Token  Request.  Begin
		String requestTokenSubmit = request.getParameter("requestTokenSubmit");
		int requestToken = theForm.getRequestToken();
        theForm.setRequestToken(requestToken+1);	
        requestToken = theForm.getRequestToken();		
		boolean tokenMatchFl =
		   (requestTokenSubmit==null || ((requestToken -1) == Integer.parseInt(requestTokenSubmit)))?
           true:false;		   
        //Token  Request. End

        // Is there a currently logged on user?

        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false )
        {
            return mapping.findForward("/userportal/logon");
        }

        ActionErrors ae = new ActionErrors();
        String retAction = "display";

        if (action==null || action.trim().length()==0)
            action = "init";

        try
        {
           CleanwiseUser user = theForm.getAppUser();
           if(user==null || action.equals("init")) {

               // Re-init any cached info.
               //theForm.setShopMethod(-1);

               ae = UserShopLogic.init(request,theForm);
               if(ae.size()>0)
               {
                   saveErrors(request,ae);
                   return mapping.findForward(retAction);
               }
           }
           if (action.equals("init")) {
               //alreatdy done
           } else if (action.equals("openCategory"))
           {
               ae = UserShopLogic.openCategory(request,theForm);
               if(ae.size()>0){
                   saveErrors(request,ae);
               }
               return mapping.findForward(retAction);
           }else if (action.equals("openList"))
           {
               ae = UserShopLogic.initOrderGuide(request,theForm);
               if(ae.size()>0){
                   saveErrors(request,ae);
               }
               //return mapping.findForward(retAction);
           }else if (action.equals("updateList"))
           {
               ae = UserShopLogic.updateOrderGuide(request,theForm);
               if(ae.size()>0){
                   saveErrors(request,ae);
               }
               //retAction="viewButtonSection";
               request.setAttribute("gotoAnchor","true");
           }else if (action.equals("deleteList"))
           {
               ae = UserShopLogic.deleteOrderGuide(request,theForm);
               if(ae.size()>0){
                   saveErrors(request,ae);
               }
               //return mapping.findForward(retAction);
           }
           else if (action.equals("removeSelected"))
           {
               ae = UserShopLogic.removeSelected(request,theForm);
               if(ae.size()>0){
                   saveErrors(request,ae);
               }
               //retAction="viewButtonSection";
               request.setAttribute("gotoAnchor","true");
           }
           else if (action.equals("getPage"))
           {
               ae = UserShopLogic.getPage(request,theForm);
               if(ae.size()>0){
                   saveErrors(request,ae);
               }
               return mapping.findForward(retAction);
           }
           else if (action.equals("sortItems"))
           {
               ae = UserShopLogic.openCategory(request,theForm);
               if(ae.size()>0){
                   saveErrors(request,ae);
               }
               return mapping.findForward(retAction);
           }
           else if (action.equals("newSort"))
           {
               ae = UserShopLogic.sort(request,theForm);
               if(ae.size()>0){
                   saveErrors(request,ae);
               }
               return mapping.findForward(retAction);
           }
           else if (action.equals("catalog"))
           {
               // Re-init any cached info.
               theForm.setShopMethod(-1);
               ae = UserShopLogic.initCatalogShopping(request,theForm,false);
               if(ae.size()>0)
               {
                   saveErrors(request,ae);
               }
           } else if (action.equals("greencleaning")) {
               ae = UserShopLogic.shopByGreenProduct(request, theForm);
                   if (ae.size() > 0) {
                       saveErrors(request, ae);
              }
           }
           else if (action.equals("guide"))
           {
               // Re-init any cached info.
               theForm.setShopMethod(-1);
               ae = UserShopLogic.initOrderGuide(request,theForm);
               if(ae.size()>0)
               {
                   saveErrors(request,ae);
               }
           }
           else if (action.equals("saveUserOrderGuide")) {
               ae = UserShopLogic.saveUserOrderGuide(request, theForm);
               if (ae.size() > 0) {
                   saveErrors(request, ae);
               }
               //retAction="viewButtonSection";
               request.setAttribute("gotoAnchor","true");
               return mapping.findForward(retAction);
           } else if ("saveUserOrderGuide.NewXpedx".equals(action)) {
               ae = UserShopLogic.saveUserOrderGuide(request, theForm, true);
               if (ae.size() > 0) {
                   saveErrors(request, ae);
               }
               request.setAttribute("gotoAnchor","true");
               return mapping.findForward(retAction);
           } else if (action.equals("updateUserOrderGuide")) {
               ae = UserShopLogic.updateUserOrderGuide(request, theForm);
               if (ae.size() > 0) {
                   saveErrors(request, ae);
               }
               //retAction="viewButtonSection";
               request.setAttribute("gotoAnchor","true");
               return mapping.findForward(retAction);
           } else if (action.equals("addItemToOrderGuide")) {
               ae = UserShopLogic.addItemToOrderGuide(request, theForm);
               if (ae.size() > 0) {
                   saveErrors(request, ae);
               }
               //retAction="viewButtonSection";
               request.setAttribute("gotoAnchor","true");
               return mapping.findForward(retAction);
           } else if (action.equals("navigatev2")) {
               ae = UserShopLogic.navigatev2(request, theForm);
               if (ae.size() > 0) {
                   saveErrors(request, ae);
               }
               return mapping.findForward(retAction);
           }
           else if(action.equals("control"))
           {
               if("search".equals(command) ||
                  request.getParameter("search.x")!=null)
               {
                   ae = UserShopLogic.search(request,theForm);
               }
               else if("listAll".equals(command) ||
                       request.getParameter("listAll.x")!=null)
               {
                   ae = UserShopLogic.listAll(request,theForm);
               }
               else if("sort".equals(command) ||
                       request.getParameter("sort.x")!=null)
               {
                   ae = UserShopLogic.sortCatalogItems(request,theForm);
               }
               else if(theForm.getNavigateCategoryId()>=0)
               {
                   ae = UserShopLogic.navigate(request,theForm);
               }
               else
               {
                   ae = UserShopLogic.sortCatalogItems(request,theForm);
               }

               if(ae.size()>0)
               {
                   saveErrors(request,ae);
               }
           }
           else if (action.equals("goPage"))
           {
               ae = UserShopLogic.goPage(request,theForm);
               if(ae.size()>0)
               {
                   saveErrors(request,ae);
               }
           }
           else if (action.equals("addToCart"))
           {
               if ("sc_addToModInvCart".equals(command)) {
            	   try {
            		   //STJ-5627
            		   ae = ShoppingCartLogic.inventoryInit(request, new ShoppingCartForm());
            	   } catch(Exception e) {
            	   }
            	  
                   ae = UserShopLogic.addToInventoryCart(request, theForm);
                   if (ae.size() > 0) saveErrors(request, ae);
               } else {

                   ae = UserShopLogic.addToCart(request, theForm);
                   if (ae.size() > 0) {
                       saveErrors(request, ae);
                   }
               }

               //retAction="viewButtonSection";
               request.setAttribute("gotoAnchor","true");
           }
           else if (action.equals("addToCartViewcart"))
           {
		       if(UserShopLogic.isSomethingSelected(request,theForm)) {
				  ae = UserShopLogic.addToActiveXpedxCart(request,theForm);
                   if(ae.size()>0) {
                       saveErrors(request,ae);
                       request.setAttribute("gotoAnchor", "true");
                   } else {
                      // no errors, no gotoAnchor. User will be redirected to shopping cart page.
                   }
			   }

			   if(ae.size()==0) {
                   if(ShopTool.hasDiscretionaryCartAccessOpen(request)) {
						retAction="viewcart";
				   } else {
						retAction="viewScheduledCart";
					}
			   }
           }
           else if (action.equals("addToActiveXpedxCart"))
           {
             ae = UserShopLogic.addToActiveXpedxCart(request,theForm);
             if(ae.size()>0) saveErrors(request,ae);

             //retAction="viewButtonSection";
             request.setAttribute("gotoAnchor","true");
           }
           else if (action.equals("update_inventory"))
           {
             ae = UserShopLogic.updateInventory(request,theForm);
             if ( ae == null || (ae != null && ae.size() == 0 )) {
                 //ShoppingCartLogic.saveShoppingCart(request.getSession());
             }
             if(ae.size()>0) saveErrors(request,ae);
           }
           else if (action.equals("og_addToCart"))
           {
			 if(tokenMatchFl) {
				ae = UserShopLogic.addItemsToCart(request,theForm);
				if(ae.size()>0) saveErrors(request,ae);
				//retAction="viewButtonSection";
				request.setAttribute("gotoAnchor","true");
			 }
           }
           else if (action.equals("og_addToCartViewCart"))
           {
             //ae = UserShopLogic.addItemsToCart(request,theForm);
        	   ae = UserShopLogic.addToActiveNewXpedxCart(request, theForm);
             if(ae.size()>0) saveErrors(request,ae);
             if(ae.size()==0) {
                 if(ShopTool.hasDiscretionaryCartAccessOpen(request)) {
						retAction="viewcart";
				   } else {
						retAction="viewScheduledCart";
					}
			   }

           }
           else if (action.equals("og_addToNewXpedxInvCart"))
           {
             ae = UserShopLogic.addToNewXpedxInvCart(request,theForm);
             if(ae.size()>0) saveErrors(request,ae);
             //retAction="viewButtonSection";
             request.setAttribute("gotoAnchor","true");
           }
           else if (action.equals("og_addToModInvCart"))
           {
             ae = UserShopLogic.addItemsToInventoryCart(request,theForm);

             if(ae.size()>0) saveErrors(request,ae);
           }
           else if (action.equals("og_recalc"))
           {
             ae = UserShopLogic.recalculateQuantities(request,theForm);
             if(ae.size()>0) saveErrors(request,ae);
           }
           else if (action.equals("orderGuideInput"))
           {
                if(request.getParameter("addToCart.x")!=null) {
                    ae = UserShopLogic.addToCart(request,theForm);
                } else if(request.getParameter("recalc.x")!=null) {
                    ae = UserShopLogic.recalculate(request,theForm);
                } else if(request.getParameter("items_addToCart.x")!=null) {
                   ae = UserShopLogic.addItemsToCart(request,theForm);
                } else if(request.getParameter("recalc_items.x")!=null) {
                   ae = UserShopLogic.recalculateQuantities(request,theForm);
                } else if(request.getParameter("update_inventory.x")!=null) {
                    ae = UserShopLogic.updateInventory(request,theForm);
                    // refresh the shooping cart.
                   if ( ae == null || (ae != null && ae.size() == 0 )) {
                       ShoppingCartLogic.saveShoppingCart(request.getSession());
                   }
                }
               if(ae.size()>0)
               {
                   saveErrors(request,ae);
               }
           }
           else if (action.equals("clear"))
           {
               ae = UserShopLogic.clear(request,theForm);
               if(ae.size()>0) saveErrors(request,ae);
               //theForm.setShopMethod(Constants.SHOP_BY_CATEGORY);
               //retAction="viewButtonSection";
               request.setAttribute("gotoAnchor","true");
           }
           else if (action.equals("select"))
           {
               ae = UserShopLogic.select(request,theForm);
               if(ae.size()>0)
               {
                   saveErrors(request,ae);
               }
           }
           else if (action.equals("item"))
           {
               ae = UserShopLogic.item(request,theForm);
               if(ae.size()>0)
               {
                   saveErrors(request,ae);
               }
           }
           else if (action.equals("itemDocumentFromDb"))
           {
               ae = ShoppingLogic.showItemDocumentFromDb(request, theForm, response);
               if(ae.size()>0)
               {
                   saveErrors(request,ae);
               }             
               return null;
           }
           else if (action.equals("itemDocumentFromE3Storage"))
           {
               ae = ShoppingLogic.showItemDocumentFromE3Storage(request, theForm, response);
               if(ae.size()>0)
               {
                   saveErrors(request,ae);
               }             
               return null;
           }
           else if (action.equals("msItem"))
           {
               ae = UserShopLogic.msItem(request,theForm);
               if(ae.size()>0)
               {
                   saveErrors(request,ae);
               }
           }else if (action.equals("itemgroup"))
           {
               ae = UserShopLogic.itemGroup(request,theForm);
               if(ae.size()>0)
               {
                   saveErrors(request,ae);
               }
           }
           else if (action.equals("itemClear"))
           {
               ae = UserShopLogic.itemClear(request,theForm);
               if(ae.size()>0)
               {
                   saveErrors(request,ae);
               }
           }

           else if (action.equals("itemToCart"))
           {
               if ("toInvCart".equals(command)) {
                   ae = UserShopLogic.itemToInvCart(request, theForm);
                   if (ae.size() > 0) {
                       saveErrors(request, ae);
                   }
               } else {
                   ae = UserShopLogic.itemToCart(request, theForm);
                   if (ae.size() > 0) {
                       saveErrors(request, ae);
                   }
               }
           }
           else if (action.equals("addItemToActiveXpedxCart"))
           {
              ae = UserShopLogic.addItemToActiveXpedxCart(request, theForm);
              if (ae.size() > 0) {
                  saveErrors(request, ae);
              }
           }

           else if (action.equals("addItemToActiveXpedxCartViewCart"))
           {
				boolean qtyFl = false;
				try {
					String qtyS = theForm.getQuantityDetail();
					if(Utility.isSet(qtyS)) {
						int qty = Integer.parseInt(qtyS);
						if(qty!=0) qtyFl = true;
					}
				} catch (Exception exc) {
				    qtyFl = true;
				}
		        if(qtyFl) {

					ae = UserShopLogic.addItemToActiveXpedxCart(request, theForm);
					if (ae.size() > 0) {
						saveErrors(request, ae);
					}
				}
  			    if(ae.size()==0) {
                   if(ShopTool.hasDiscretionaryCartAccessOpen(request)) {
						retAction="viewcart";
				   } else {
						retAction="viewScheduledCart";
					}
			    }
           }

           else if (action.equals("orderGuideSelect"))
           {
               ae = UserShopLogic.orderGuideSelect(request,theForm);
               if(ae.size()>0)
               {
                   saveErrors(request,ae);
               }
           }
           else if (action.equals("refreshGuide"))
           {
               ae = UserShopLogic.refreshGuide(request,theForm);
               if(ae.size()>0)
               {
                   saveErrors(request,ae);
               }
           }
           else if (action.startsWith("pdfPrint") ||
        action.startsWith("excelPrint") )
           {

               ae = UserShopLogic.printDetail(response, request,
                theForm, action, getResources(request));
               if(ae.size()>0)
               {
                   saveErrors(request,ae);
               }
               else
               {
                   return null;
               }

           }
           else if (action.equals("initAndSelectOrderGuide"))
           {
               theForm.setShopMethod(-1);
               ae = UserShopLogic.initAndSelectOrderGuide(request,theForm);
               if(ae.size()>0)
               {
                   saveErrors(request,ae);
               }
           }
           else if(action.equals("og_control"))
           {
               if("search".equals(command) ||
                  request.getParameter("search.x")!=null)
               {
                   ae = UserShopLogic.searchOG(request,theForm);
               }
               if(ae.size()>0)
               {
                   saveErrors(request,ae);
               }
           }
           else if(action.equals("integratedSearch"))
           {
               ae = UserShopLogic.integratedSearch(request,theForm);
               if(ae.size()>0)
               {
                   saveErrors(request,ae);
               }else if(theForm.getProductList() != null && theForm.getProductList().size() == 1){
                   ProductData theProd = (ProductData) theForm.getProductList().get(0);
                   /*request.setParameter("itemId",Integer.toString(theProd.getProductId()));
                   request.setParameter("qty","0");
                   request.setParameter("action","msItem");*/

                   if(theProd.isItemGroup()){
                	   ActionForward detail = mapping.findForward("itemGroupDetail") ;
                	   detail = new ActionForward( detail.getPath() + "&itemId=" + theProd.getProductId()+"&qty=0&action=itemgroup", detail.getRedirect() ) ;
                       return detail ;

                   }else{
                	   ActionForward detail = mapping.findForward("itemdetail") ;

                   //String path = detail.getPath();
                   //path = path.replaceFirst(".jsp",".do");
                   detail = new ActionForward( detail.getPath() + "&itemId=" + theProd.getProductId()+"&qty=0&action=msItem", detail.getRedirect() ) ;
                   return detail ;
                   }
                   //retAction="itemdetail";
               }
           }

           else
           {
        	   ae.add("error",new ActionError("error.systemError","Unknown action: ["+Utility.encodeForHTML(action)+"]"));
               saveErrors(request,ae);
           }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            retAction="error";
        }

        return mapping.findForward(retAction);
	}
}
