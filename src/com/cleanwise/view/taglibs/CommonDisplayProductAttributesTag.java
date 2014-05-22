package com.cleanwise.view.taglibs;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Category;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.UserShopForm;
import com.cleanwise.view.forms.EditOrderGuideForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;

public class CommonDisplayProductAttributesTag extends TagSupport {
        private static final Category log = Category.getInstance(CommonDisplayProductAttributesTag.class);
        private static final String TEST_CENTER = "textCenter";
        private static final String TEST_LEFT = "textLeft";
        private static final String TEST_RIGHT = "textRight";

        private String greenImg = null;


        //private int colIdx = 0; // used to keep track of how many tds we have done

        private String name;

        private String link;// optional

        private int index;

        //private int renderMode;

        private String inputNameQuantity; // holds the list name where the items

        // are stored

        private String inputNameOnHand; // holds the list name where the items are

        // stored

        private boolean viewOptionEditCartItems; // whilst on view cart and can edit
        private boolean viewOptionShoppingCart; // view shopping cart
        private boolean viewOptionInvShoppingCart; // view shopping cart
        private boolean viewOptionCheckout; // checkout view
        private boolean viewOptionConfirmCheckout; // checkout view
        private boolean viewOptionReorder; // checkout view
        private boolean viewOptionCatalog; // product catalog view
        private boolean viewOptionModInventory;
        private boolean viewOptionOrderStatus;

        // change quantities

        private boolean viewOptionQuickOrderView; // the express order screen

        private boolean viewOptionAddToCartList; // category view

        private boolean viewOptionOrderGuide; // order guide view
        private boolean viewOptionEditOrderGuide; // order guide view

        private boolean viewOptionInventoryList; // if we are rendering a list of
        private boolean viewOptionItemDetail; //is we are rendering an item detail page


        //uses the action right now
        //private boolean viewOptionAutoDistro; // auto distro for apple only

        private String altThumbImage; // if no thumbnail is present default

        private boolean notShowParOnHandCols;

        private Integer iteratorId;
        private Integer tabIteratorId;

        /*private ProductViewDefDataVector shoppingCartViewDefinitions = getShoppingCartViewDefinitions();
        private ProductViewDefDataVector checkoutViewDefinitions = getCheckoutViewDefinitions();
        private ProductViewDefDataVector autoDistroViewDefs = getAutoDistroViewDefinitions();
        private ProductViewDefDataVector orderGuideViewDefs = getOrderGuideViewDefinitions();
        private ProductViewDefDataVector reorderViewDefinitions = getReorderViewDefinitions();
        private ProductViewDefDataVector editOrderGuideViewDefs = getEditOrderGuideViewDefinitions();
        private ProductViewDefDataVector standartViewDefs = getStandartViewDefinitions();*/


        private int getCurrentDisplayIndex(int offset) {
        	return incrementTabIndexId();
               /* if (iteratorId == null) {
                        return offset;
                }
                return iteratorId.intValue() + offset;*/
        }




        protected ProductViewDefDataVector getProductDefinitions(){
             return getProductDefinitions(false);
        }


        /**
         * Retrieves the actual definitions that we need to render. These are pulled
         * from the account data object for the account the usre is currently
         * shopping for.
         *
         * @return a list of ProductViewDefinition defining what item attributes
         *         that we are going to render.
         */
        protected ProductViewDefDataVector getProductDefinitions(boolean filterByShouldBeRendered) {

        	ProductViewDefDataVector defsMast;
        	if(viewOptionItemDetail){
        		defsMast = appUser.getUserAccount().getProductViewDefinitions(RefCodeNames.SHOP_UI_PRODUCT_VIEW_CD.SHOP_UI_DETAIL);
        	}else{
        		defsMast = appUser.getUserAccount().getProductViewDefinitions(RefCodeNames.SHOP_UI_PRODUCT_VIEW_CD.SHOP_UI_DEFAULT);
        	}
            ProductViewDefDataVector aList = new ProductViewDefDataVector();
            if (defsMast != null) {
                Collections.sort(defsMast,PRODUCT_DEF_SORT);
                aList = (ProductViewDefDataVector) defsMast.clone(); //clone to prevent multiple requests from sorting the list different ways
            } else {
                aList = getStandardViewDefinitions();
            }

            ProductViewDefDataVector result = new ProductViewDefDataVector();
            if (viewOptionCheckout || viewOptionConfirmCheckout) {
                result = getCheckoutViewDefinitions(aList);
                /*if (isViewOptionAutoDistro())
                    result = getautoDistroViewDefs;*/
            } else if (viewOptionOrderGuide) {
                result = getOrderGuideViewDefinitions(aList);
            } else if (viewOptionInvShoppingCart) {
                result = getInvShoppingCartViewDefinitions(aList);
            } else if (viewOptionShoppingCart) {
                result = getShoppingCartViewDefinitions(aList);
            } else if (viewOptionReorder) {
                result = getReorderViewDefinitions(aList);
            } else if (viewOptionEditOrderGuide) {
                result = getEditOrderGuideViewDefinitions(aList);
            } else if (viewOptionOrderStatus) {
                result = getOrderStatusDefinitions(aList);
            } else {
                result = getOrderGuideViewDefinitions(aList);
            }

            ProductViewDefDataVector defs = new ProductViewDefDataVector();
            Iterator it = result.iterator();
            while(it.hasNext()){
                ProductViewDefData def = (ProductViewDefData) it.next();
                if(filterByShouldBeRendered){
                    if(shouldAttributeBeRendered(def.getAttributename())){
                        defs.add(def);
                    }
                } else {
                    defs.add(def);
                }
            }
            return defs;
        }

        protected static final Comparator PRODUCT_DEF_SORT = new Comparator() {
            public int compare(Object o1, Object o2)
            {
                int id1 = ((ProductViewDefData)o1).getSortOrder();
                int id2 = ((ProductViewDefData)o2).getSortOrder();
                return id1 - id2;
            }
        };


        HttpServletRequest request;

        HttpSession session;

        CleanwiseUser appUser;

        /**
         * Based off the rendering in the header figures out if we are rendering an
         * inventory shopping cart or a regular shopping cart. The version in this
         * tag requiers this to be set by the
         *
         * @see DisplayProductHeader tag in the page
         * @return true if we are rendering an inventory cart
         */
         /*
        public boolean isViewOptionInventoryList() {
                Boolean isInvList = (Boolean) request.getAttribute(IS_INVENTORY_LIST);
                if (isInvList == null) {
                        return false;
                } else {
                        return isInvList.booleanValue();
                }
        }  */

        // define some request scoped variable locations that the tags uses to
        // communicate
        protected static String IS_INVENTORY_LIST = DisplayProductAttributesTag.class.getName() + ".IS_INVENTORY_LIST";

        private static String ITERATE_ID = DisplayProductAttributesTag.class.getName() + ".ITERATE_ID";
        private static String TAB_ITERATE_ID = DisplayProductAttributesTag.class.getName() + ".TAB_ITERATE_ID";

        private static String pStyleClass;

        /**
         * Called on start of tag, initializes some local variables that the rest of
         * the class uses. If re-use is requiered then these variable initilizations
         * will need to be re-implemented
         */
        public int doStartTag() throws JspException {
                if (getItem() == null) {
                        throw new JspException("Could not find item under name " + getName());
                }


                request = (HttpServletRequest) pageContext.getRequest();
                session = request.getSession();
                appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

                // set the iterator id, used for tab indexing
                iteratorId = (Integer) request.getAttribute(ITERATE_ID);
                if (iteratorId == null) {
                        iteratorId = new Integer(0);
                } else {
                        iteratorId = new Integer(iteratorId.intValue() + 1);
                }
                request.setAttribute(ITERATE_ID, iteratorId);

                return BodyTagSupport.EVAL_BODY_BUFFERED;
        }

        private int incrementTabIndexId(){
        	 // set the iterator id, used for tab indexing
            tabIteratorId = (Integer) request.getAttribute(TAB_ITERATE_ID);
            if (tabIteratorId == null) {
            	tabIteratorId = new Integer(1);
            } else {
            	tabIteratorId = new Integer(tabIteratorId.intValue() + 1);
            }
            request.setAttribute(TAB_ITERATE_ID, tabIteratorId);

            return tabIteratorId.intValue();
        }

        /**
         * Called on end of the tag Renders the content
         */
        public int doEndTag() throws JspException {

                Writer out = pageContext.getOut();
                try {
                        renderRowItemView(out);
                } catch (IOException e) {
                        throw new JspException(e);
                }

                return BodyTagSupport.EVAL_PAGE;

        }
//////////////////////////////////
void printBoolean() {
if(viewOptionEditCartItems)
log.info(".... viewOptionEditCartItems: "+viewOptionEditCartItems); // whilst on view cart and can edit
if(viewOptionShoppingCart)
log.info(".... viewOptionShoppingCart: "+viewOptionShoppingCart); // view shopping cart
if(viewOptionInvShoppingCart)
log.info(".... viewOptionInvShoppingCart: "+viewOptionInvShoppingCart); // view shopping cart
if(viewOptionCheckout)
log.info(".... viewOptionCheckout: "+viewOptionCheckout); // checkout view
if(viewOptionConfirmCheckout)
log.info(".... viewOptionConfirmCheckout: "+viewOptionConfirmCheckout); // checkout view
if(viewOptionReorder)
log.info(".... viewOptionReorder: "+viewOptionReorder); // checkout view
if(viewOptionCatalog)
log.info(".... viewOptionCatalog: "+viewOptionCatalog); // product catalog view
if(viewOptionModInventory)
log.info(".... viewOptionModInventory: "+viewOptionModInventory);
if(viewOptionOrderStatus)
log.info(".... viewOptionOrderStatus: "+viewOptionOrderStatus);
if(viewOptionQuickOrderView)
log.info(".... viewOptionQuickOrderView: "+viewOptionQuickOrderView); // the express order screen
if(viewOptionAddToCartList)
log.info(".... viewOptionAddToCartList: "+viewOptionAddToCartList); // category view
if(viewOptionOrderGuide)
log.info(".... viewOptionOrderGuide: "+viewOptionOrderGuide); // order guide view
if(viewOptionEditOrderGuide)
log.info(".... viewOptionEditOrderGuide: "+viewOptionEditOrderGuide); // order guide view
if(viewOptionInventoryList)
log.info(".... viewOptionInventoryList: "+viewOptionInventoryList); // if we are rendering a list of
if(viewOptionItemDetail)
log.info(".... viewOptionItemDetail: "+viewOptionItemDetail); //is we are rendering an item detail page
if(notShowParOnHandCols)
log.info(".... notShowParOnHandCols: "+notShowParOnHandCols);

}
        /**
         * Retrieves the item that we are going to display the values of. That is
         * the specific item in the array (cart, checkout screen, etc) that we need
         * to dispaly the attributes of
         *
         * @return
         */
        private ShoppingCartItemData getItem() {
                if (getName() == null) {
                        // called from DisplayProductHeader, just return null
                        return null;
                }
                return (ShoppingCartItemData) pageContext.getAttribute(getName());
        }

        String mStoreDir;

        /**
         * Gets the store dir for rendering images and other content.
         *
         * @return
         */
        protected String getStoreDir() {
                if (mStoreDir == null) {
                        mStoreDir = ClwCustomizer.getStoreDir();
                }
                return mStoreDir;
        }

        /**
         * Deals with some special case attributes that are controlled by other
         * permission settings. For example if the user is configured to not show
         * price then the price should not be rendered even if the attribute is
         * configured to show up for the account.
         *
         * @param attribute
         *            The attribute to display
         * @return true if this attribute should be rendered
         */
        protected boolean shouldAttributeBeRendered(String attribute) {
                if ( isViewOptionAutoDistro() )
                        return true;
                if (viewOptionOrderGuide){
                        if(attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DELETE)){
                                UserShopForm shopForm = getShoppingForm();
                                boolean isUserOG = false;
                                if (shopForm != null) {
                                        isUserOG = shopForm.isUserOrderGuide(shopForm.getOrderGuideId());
                                }

                                if(isUserOG){
                                        return true;
                                }else{
                                        return false;
                                }
                        }

                }
                if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND)) {
                    if(ShopTool.isInventoryShoppingOn(request)) {
                        if (notShowParOnHandCols) {
                            return false;
                        }
                        if (viewOptionShoppingCart && ShopTool.isInventoryShoppingOn(request)) {
                            return true;
                        }
                        if (appUser.canMakePurchases()
                                && !viewOptionConfirmCheckout
                                && !viewOptionItemDetail
                                && !viewOptionEditCartItems) {
                            return true;
                        }
                    } else {
                        return false;
                    }
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MAX_ORDER_QTY)){
                        if(isViewOptionInventoryList()){
                                return false;
                        }
                        return true;
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE)) {
                    if(ShopTool.isInventoryShoppingOn(request)) {
                        if (notShowParOnHandCols) {
                            return false;
                        }
                        if (viewOptionShoppingCart) {
                            if (ShopTool.isInventoryShoppingOn(request)) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                        if (ShopTool.isInventoryShoppingOn(request) && isViewOptionInventoryList()) {
                            return true;
                        }
                    } else {
                        return false;
                    }
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY)) {
                        if (viewOptionShoppingCart) {
                           // if (ShopTool.isInventoryShoppingOn(request)) {
                           //     return true;
                           // } else {
                           //     return false;
                           // }
                           return true;
                        }
                        if (viewOptionAddToCartList) {
                            return true;
                        }
                        if (ShopTool.isInventoryShoppingOn(request) && isViewOptionInventoryList()) {
                                return true;
                        }
                        if (viewOptionCheckout || viewOptionConfirmCheckout || viewOptionReorder || viewOptionEditOrderGuide
                        		|| viewOptionQuickOrderView) {
                            return true;
                        }
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.IA)) {
                        if (viewOptionShoppingCart && ShopTool.isInventoryShoppingOn(request)) {
                                return true;
                        }
                        if (ShopTool.isInventoryShoppingOn(request) && isViewOptionInventoryList()) {
                                return true;
                        }
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE)) {
                        if (appUser.getShowPrice()) {
                                return true;
                        }

                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE)) {
                        if (appUser.getShowPrice()) {
                            if (viewOptionModInventory) {
                                return true;
                            }
                            if (viewOptionOrderGuide || viewOptionCatalog) {
                                return false;
                            }
                            return true;
                        }
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RESALE)) {
                	if(!(viewOptionCheckout || viewOptionConfirmCheckout)){
                    	return false;
                    }
                        boolean resaleItemsAllowed = ShopTool.isResaleItemsAllowed(request);
                        if (resaleItemsAllowed && !viewOptionQuickOrderView) {
                                return true;
                        }
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DELETE)) {
                        UserShopForm shopForm = getShoppingForm();
                        boolean isUserOG = false;
                        if (shopForm != null) {
                                isUserOG = shopForm.isUserOrderGuide(shopForm.getOrderGuideId());
                        }

                        if(viewOptionItemDetail){
                        	return false;
                        }
                        if (viewOptionOrderGuide){
                                if(isUserOG){
                                        return true;
                                }else{
                                        return false;
                                }
                        }
                        if (isViewOptionEditCartItems() && isViewOptionInventoryList()) {
                                return false;
                        }
                        if (viewOptionEditCartItems || viewOptionQuickOrderView) {
                                return true;
                        }

                } else if (attribute.equals(ProductData.THUMBNAIL)) {
                        if (viewOptionOrderGuide || viewOptionCatalog) {
                                return true;
                        }
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_NAME)) {
                    if (viewOptionInvShoppingCart) {
                        return false;
                    }
                    if (!appUser.getUserAccount().isHideItemMfg()) {
                        return true;
                    }
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_SKU)) {
                    if (viewOptionInvShoppingCart) {
                        return false;
                    }
                    if (!appUser.getUserAccount().isHideItemMfg()) {
                        return true;
                    }
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SELECT)) {
                    if (viewOptionEditCartItems ||
                            (viewOptionCheckout && !viewOptionConfirmCheckout) ||
                            viewOptionEditOrderGuide) {
                        return true;
                    }
                } else if (attribute.equals(ProductData.COLOR) &&
                        (viewOptionModInventory || viewOptionInvShoppingCart || viewOptionOrderStatus || viewOptionEditOrderGuide)) {
                        return false;
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SPL)) {
                    if (appUser.getUserAccount().isShowSPL()) {
                        return true;
                    }
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_INVENTORY_FLAG)) {
                    String showDistInventory = ShopTool.getShowDistInventoryCode(request);
                    if(RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_FLAG.equals(showDistInventory) ||
                       RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_QUANTITIES.equals(showDistInventory)) {
                        return true;
                    }
                } else if (attribute.equals(ProductData.UOM) && viewOptionEditOrderGuide) {
                    return false;
                } else {
                        return true;
                }
                return false;
        }

        /**
         * Gets the shopping form from the session
         * @return the users Shopping form.
         */
        private UserShopForm getShoppingForm() {
                try {
                        return (UserShopForm) session.getAttribute("SHOP_FORM");
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }

        private EditOrderGuideForm getEditOrderGuideForm() {
            try {
                    return (EditOrderGuideForm) session.getAttribute("EDIT_ORDER_GUIDE_FORM");
            } catch (Exception e) {
                    e.printStackTrace();
            }
            return null;
        }

        /**
         * Determines if the discretionary cart is avaliable
         * @return true if the discretionary cart is avaliable
         */
        private boolean allowAddToCartForInventoryItems() {
                if (appUser.getSite() != null && ShopTool.isModernInventoryShopping(request) && !ShopTool.hasDiscretionaryCartAccessOpen(request) && session.getAttribute(Constants.INVENTORY_SHOPPING_CART) != null) {
                        return false;
                }
                return true;
        }


        int lineIndex = 0;
        /**
         * Renders the xpedx view.
         *
         * @param request
         * @param out
         * @throws IOException
         */
        private void renderRowItemView(Writer out) throws IOException {
            boolean first = true;
            Iterator it = getProductDefinitions(true).iterator();
            while (it.hasNext()) {
                ProductViewDefData def = (ProductViewDefData) it.next();
                if (!shouldAttributeBeRendered(def.getAttributename())) {
                        continue;
                }
                noLinks = (getLink() == null || getLink().length() <= 0);

                String style = def.getStyleClass();
                if (!Utility.isSet(def.getStyleClass())) {
                        style = getDefaultStyle();
                } else {
                        style = style + "  "+getDefaultStyle();
                }
                if(first){
                        style = "itemRowFirst "+ style;
                        first = false;
                }else if(!it.hasNext()){
                        style = "itemRowLast "+ style;
                }
                if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE) && (viewOptionShoppingCart || viewOptionQuickOrderView || viewOptionCheckout )) {
                    style = "invqty_box";
                }

                if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DELETE)) {
                	if(!viewOptionItemDetail){
                        out.write("<td class=\"" + style + "\" width=\"" + def.getWidth()/2 + "\">");
                        out.write("<!--"+def.getAttributename()+"-->");
                        writeElement(out, def);
                        out.write("</td>");
                        out.write("<td class=\"" + style +"\" width=\"" + def.getWidth()/2 + "\">");
                        out.write("&nbsp;");
                        out.write("</td>");
                	}else{
                		continue;
                	}
                } else{
                        out.write("<td class=\"" + style + "\"");
                        if (def.getWidth() > 0) {
                            out.write("width=\"" + def.getWidth() + "\"");
                        }
                        out.write(">");
                        out.write("<!--"+def.getAttributename()+"-->");
                        writeElement(out, def);
                        out.write("</td>");
                }
            }
            // write out a couple elements that always show, or are not controlled
            // by the dynamic list
            writeResaleElement(out, true);
        }


        /**
         * Returns the default style to use.  This is hardcoded as "itemRow"
         */
        protected String getDefaultStyle(){
                return "itemRow";
        }

       //called from detail tag so as not to render the links
        protected boolean noLinks = false;

        /**
         * Does the work of mapping string values to their appropriate values. No
         * internationalization is done in this method as it is dealing directly
         * with database values.
         *
         * @param out
         *            Output writter to write to
         * @param def
         *            the definition to render
         * @throws IOException
         */
        protected void writeElement(Writer out, ProductViewDefData def) throws IOException {
                ShoppingCartItemData item = getItem();

                // deal with special attributes first
                if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND)) {
                        if(isViewOptionAutoDistro()){
                                if (viewOptionEditCartItems){
                                  // shopping cart, any time editing items in the cart
                                  out.write("<input id=\"IDX_" + getIndex() + "\" size=\"3\" tabindex=\"" +
                                          getCurrentDisplayIndex(1000) + "\" value=\"" + Integer.toString(item.getInventoryParValue()) +
                                          "\" type=\"text\" name=\"" + getInputNameQuantity() + "\">");
                                } else {
                                  out.write(Integer.toString(item.getInventoryParValue()));
                                }
                        }else{
                                Date curDate = Constants.getCurrentDate();
                                Date effDate = item.getProduct().getEffDate();
                                Date expDate = item.getProduct().getExpDate();
                                if (!(effDate != null && effDate.compareTo(curDate) <= 0 && (expDate == null || expDate.compareTo(curDate) > 0))) {
                                        // item is not effective
                                        out.write("&nbsp;");
                                } else if (viewOptionOrderGuide && !viewOptionModInventory) {

                                        // order guide and category screens
                                        if (item.getProduct().isItemGroup()) {
                                                // order guide or category view for a group item is not
                                                // allowed
                                                out.write("&nbsp;");
                                                // } else if (!ShopTool.isModernInventoryShopping(request)
                                                // && item.getIsaInventoryItem() &&
                                                // ShopTool.isInventoryShoppingOn(request)) {
                                        } else if (item.getIsaInventoryItem() && !allowAddToCartForInventoryItems()) {
                                                out.write("&nbsp;");
                                        } else {
                                               // out.write("<input id=\"IDX_" + getIndex() + "\" size=\"3\" tabindex=\"" +
                                               //         getCurrentDisplayIndex(1000) + "\" value=\"" +
                                               //         item.getQuantityString() + "\" type=\"text\" name=\"" +
                                               //         getInputNameQuantity() + "\">");
                                               out.write("NA1");

                                        }
                                } else if (viewOptionModInventory) {
                                    if (ShopTool.isInventoryShoppingOn(request) && item.getIsaInventoryItem() ) {
                                        if (item.getInventoryParValue() > 0) {
                                            if (item.getInventoryQtyIsSet()) {
                                                int newOnHandVal = 0;
                                                try {
                                                    newOnHandVal = Integer.parseInt(item.getInventoryQtyOnHandString());
                                                }  catch (Exception e) {
                                                    newOnHandVal = 0;
                                                }
                                                item.setInventoryQtyOnHand(newOnHandVal);
                                            }
                                            out.write(item.getMonthlyOrderQty());
                                        } else {
                                            out.write("-");
                                        }
                                    } else {
                                      out.write("-");
                                    }
                                } else if (viewOptionInvShoppingCart) {
                                    if (ShopTool.isInventoryShoppingOn(request) &&  item.getIsaInventoryItem() ) {
                                        out.write("<input id=\"IDX_" + getIndex() + "\" " +
                                                  "size=\"3\" tabindex=\"" + getCurrentDisplayIndex(1000) + "\" " +
                                                  "value=\"" + item.getInventoryQtyOnHandString() + "\" type=\"text\" " +
                                                  "name=\"" + getInputNameOnHand() + "\">");

                                    } else {
                                        out.write("-");
                                    }
                                } else if (viewOptionQuickOrderView || viewOptionShoppingCart || viewOptionCheckout) {
                                    if (ShopTool.isInventoryShoppingOn(request) &&  item.getIsaInventoryItem() ) {
                                        out.write(item.getInventoryQtyOnHandString());
                                    } else {
                                        out.write("-");
                                    }
                                } else if (viewOptionEditCartItems) {
                                        // for non-xpedx might need to be more inteligent...this will
                                        // render one or the other, but not both qty and inventory
                                        if (item.getIsaInventoryItem() && !allowAddToCartForInventoryItems()) {
                                            /*
                                                // never do this for xpedx!
                                                out.write("<span class=\"inv_item\">i");
                                                if (item.getAutoOrderEnable()) {
                                                        out.write("a");
                                                }
                                                out.write("</span>");
                                                out.write("<input id=\"IDX_" + getIndex() + "\" size=\"3\" tabindex=\"" +
                                                        getCurrentDisplayIndex(1000) +
                                                        "\" value=\"" + item.getInventoryQtyOnHandString() + "\" type=\"text\" name=\"" +
                                                        getInputNameOnHand() + "\">");
                                                */
                                                out.write("NA2A");
                                        } else {
                                                // shopping cart, any time editing items in the cart
                                                //out.write("<input id=\"IDX_" + getIndex() + "\" size=\"3\" tabindex=\"" +
                                                //        getCurrentDisplayIndex(1000) +
                                                //        "\" value=\"" + item.getQuantityString() + "\" type=\"text\" name=\"" +
                                                //        getInputNameQuantity() + "\">");
                                                out.write("NA2B");
                                        }
                                } else if (viewOptionAddToCartList) {
                                        if (item.getProduct().isItemGroup()) {
                                                // order guide or category view for a group item is not
                                                // allowed
                                                out.write("&nbsp;");
                                        } else if (item.getIsaInventoryItem() && !allowAddToCartForInventoryItems()) {
                                                out.write("&nbsp;");
                                        } else {
                                                // shopping cart, any time editing items in the cart
                                                if (item.getIsaInventoryItem() && !allowAddToCartForInventoryItems()) {
                                                        out.write("&nbsp;");
                                                } else {
                                                        //out.write("<input id=\"IDX_" + getIndex() + "\" size=\"3\" tabindex=\"" +
                                                        //        getCurrentDisplayIndex(1000) + "\" value=\"" +
                                                        //        item.getQuantityString() + "\" type=\"text\" name=\"" +
                                                        //        getInputNameQuantity() + "\">");
                                                        out.write("NA3");
                                                }
                                        }
                                } else {
                                        // checkout screen and any others
                                        out.write(item.getQuantityString());
                                }
                        }//end auto distro view
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE)) {
                        String val = item.getInventoryParValue() > 0 ? "" + item.getInventoryParValue() : "-";
                        out.write(val);
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_INVENTORY_FLAG)) {
                        String distInvQtyS = "-";
                        int distInvQty = item.getDistInventoryQty();
                        if (distInvQty <= 0)
                                distInvQtyS = ClwI18nUtil.getMessage(request, "global.text.u", null);
                        if (distInvQty > 0)
                                distInvQtyS = ClwI18nUtil.getMessage(request, "global.text.a", null);
                        out.write(distInvQtyS);
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_INVENTORY_QTY)) {
                        out.write(item.getDistInventoryQty());
                } else if (def.getAttributename().equals(ProductData.THUMBNAIL)) {
                        String thumbnail = item.getProduct().getThumbnail();
                        // commented out for performance
                        // if (!Utility.isSet(thumbnail)){
                        // thumbnail = item.getProduct().getImage();
                        // }
                        if (Utility.isSet(thumbnail)) {
                                if(!noLinks){out.write("<A class=\""+getDefaultStyle()+"\" HREF=\"" + getLink() + "\">");}
                                out.write("<img class=\""+getDefaultStyle()+"\" width=\"50\" height=\"50\" src=\"/" + getStoreDir() + "/" + thumbnail + "\">");
                                if(!noLinks){out.write("</A>");}
                        } else {
                                if (Utility.isSet(getAltThumbImage())) {
                                        if(!noLinks){out.write("<A class=\""+getDefaultStyle()+"\" HREF=\"" + getLink() + "\">");}
                                        out.write("<img class=\""+getDefaultStyle()+"\" width=\"50\" height=\"50\" src=\"/" + getStoreDir() + "/en/images/noManXpedxImg.gif\">");
                                        if(!noLinks){out.write("</A>");}
                                } else {
                                        out.write("&nbsp;<!--no thumb-->");
                                }
                        }
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU)) {
                        //if(!noLinks){out.write("<A class=\""+getDefaultStyle()+"\" HREF=\"" + getLink() + "\">");}
						String actualSkuNum = (item.getActualSkuNum() == null) ? "" : item.getActualSkuNum();
                        out.write(actualSkuNum);
                        //if(!noLinks){out.write("</A>");}
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_SKU)) {
						String itemNum = "";
                        if (item.getProduct() != null) {
                            if (item.getProduct().getCatalogDistrMapping() != null) {
                                if (item.getProduct().getCatalogDistrMapping().getItemNum() != null) {
                                    itemNum = item.getProduct().getCatalogDistrMapping().getItemNum();
                                }
                            }
                        }
                        out.write(itemNum);                
				} else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CUSTOMER_SKU)) {
                        out.write(item.getProduct().getActualCustomerSkuNum());
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SYSTEM_SKU)) {
                        out.write("" + item.getProduct().getItemData().getSkuNum());
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC)) {
                        if(!noLinks){out.write("<A class=\""+getDefaultStyle()+"\" HREF=\"" + getLink() + "\">");}
                        out.write(item.getProduct().getCatalogProductShortDesc());
                        if(!noLinks){out.write("</A>");}
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE)) {
                        if ((!item.getProduct().isItemGroup() && viewOptionAddToCartList) || !viewOptionAddToCartList) {
                            BigDecimal priceBD = new BigDecimal(item.getPrice());
                            out.write(ClwI18nUtil.getPriceShopping(request, priceBD, "<br>"));
                        } else {
                                out.write("&nbsp;");
                        }
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE)) {

                        if ((!item.getProduct().isItemGroup() && viewOptionAddToCartList) || !viewOptionAddToCartList) {
                                BigDecimal finalLineAmount;
                                if (item.getIsaInventoryItem()) {
                                    BigDecimal priceBD = new BigDecimal(item.getPrice());
                                    priceBD = priceBD.multiply(new BigDecimal(item.getInventoryOrderQty()));
                                    finalLineAmount = priceBD;
//                                    finalLineAmount = new java.math.BigDecimal(item.getAmount());
                                } else {
                                        finalLineAmount = new java.math.BigDecimal(item.getAmount());
                                }
                                out.write(ClwI18nUtil.getPriceShopping(request, finalLineAmount, "<br>"));
                        } else {
                                out.write("&nbsp;");
                        }
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SPL)) {
                        if (item.getProduct().getCatalogDistrMapping() != null && item.getProduct().getCatalogDistrMapping().getStandardProductList() != null) {
                                if (Utility.isTrue(item.getProduct().getCatalogDistrMapping().getStandardProductList())) {
                                        out.write(ClwI18nUtil.getMessage(request, "shoppingItems.text.y", null));
                                } else {
                                        out.write(ClwI18nUtil.getMessage(request, "shoppingItems.text.n", null));
                                }
                        } else {
                                out.write(ClwI18nUtil.getMessage(request, "shoppingItems.text.n", null));
                        }
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_NAME)) {
                    String manufName = item.getProduct().getManufacturerName();
                    if (manufName != null) {
                        out.write(manufName);
                    } else {
                        out.write("&nbsp;");
                    }
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_SKU)) {
                    String manufSku = item.getProduct().getManufacturerSku();
                    if (manufSku != null) {
                        out.write(manufSku);
                    } else {
                        out.write("&nbsp;");
                    }
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RESALE)) {
                        writeResaleElement(out, false);
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DELETE)) {
                        writeDeleteCheckBoxElement(out);
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SELECT)) {
                        writeSelectBoxElement(out);
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MAX_ORDER_QTY)) {
                        String maxQtyS = "";
                        if(!(item.getMaxOrderQty()<0)){
                                maxQtyS = Integer.toString(item.getMaxOrderQty());
                        }
                        out.write(maxQtyS);
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_PACK)) {
                	String distPack = null;
                	if(item.getProduct().getCatalogDistrMapping()!=null){
                		distPack=item.getProduct().getCatalogDistrMapping().getItemPack();
                	}
                	if(distPack!=null && distPack.length()>0){
                        out.write(distPack);
                	}else{
                		out.write("&nbsp;");
                	}
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.IA)) {
                    if(isViewOptionInventoryList()){
                        if (item.getIsaInventoryItem()) {
                            out.write("<span class=\"inv_item\">i");
                            if (ShopTool.getCurrentSite(request).isAnInventoryAutoOrderItem(item.getProduct().getProductId())) {
                                out.write("&nbsp;a");
                            }
                            out.write("</span>");
                        }
                    }
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY)) {
                   // if (viewOptionShoppingCart || viewOptionCheckout || viewOptionConfirmCheckout || viewOptionReorder ||
                   //     viewOptionEditOrderGuide || viewOptionModInventory  || viewOptionQuickOrderView) {
                        //Integer lineQty = new Integer(item.getQuantity());
                    if(!viewOptionItemDetail) {
                        String lineQty = item.getQuantityString();
                        if (viewOptionEditOrderGuide) {
                            EditOrderGuideForm theForm = getEditOrderGuideForm();
                            lineQty = theForm.getQuantityElement(getIndex());
                        }
                        if (lineQty == null) {
                            lineQty = "";
                        }

                        if (!item.getDuplicateFlag()) {
                            if (viewOptionEditCartItems) {
                                item.getInventoryQtyOnHandString();
                                String onHandVal = item.getInventoryQtyOnHandString();
                                boolean enableQtyInputFl =
                                  (!viewOptionInvShoppingCart || !item.getIsaInventoryItem() || Utility.isSet(onHandVal))?true:false;

                                out.write("<input id=\"IDX_" + getIndex() + "\" " +
                                          "size=\"3\" tabindex=\"" + getCurrentDisplayIndex(1000) + "\" " +
                                          "value=\"" + lineQty + "\" type=\"text\" " +
                                          "name=\"" + getInputNameQuantity() + "\""+
                                          (enableQtyInputFl? "":" disabled=\"true\"")+
                                          "/>");
                            } else {
                                out.write(lineQty.toString());
                            }
                        } else {
                            //out.write("<input id=\"IDX_" + getIndex() + "\" " +
                            //          "size=\"3\" readonly=\"true\" styleClass=\"customerltbkground\" " +
                            //          "value=\"" + lineQty + "\" type=\"text\" " +
                            //          "name=\"" + getInputNameQuantity() + "\">");
                            out.write(lineQty.toString());

                        }
                        out.write("<input type=\"hidden\" name=\"itemIdsElement[" + getIndex() + "]\" value=\"" + item.getItemId() + "\"/>");
                        out.write("<input type=\"hidden\" name=\"orderNumbersElement[" + getIndex() + "]\" value=\"" + item.getOrderNumber() + "\"/>");

                      } else {
                      //       out.write("&nbsp;");
                            out.write("<input id=\"IDX_" + getIndex() + "\" size=\"3\" tabindex=\"" +
                                    getCurrentDisplayIndex(1000) +
                                    "\" value=\"" + item.getQuantityString() + "\" type=\"text\" name=\"" +
                                    getInputNameQuantity() + "\">");
                      }
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATEGORY)) {
                    out.write(item.getCategoryPath());
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.GREEN)) {
                	//log.info("CHECKING FOR CERTIFIED COMPANIES!!!!!!!!!_");
                	//log.info("CHECKING FOR CERTIFIED COMPANIES!!!!!!!!!_");
                	//log.info("CHECKING FOR CERTIFIED COMPANIES!!!!!!!!!_");
                	//log.info("CHECKING FOR CERTIFIED COMPANIES!!!!!!!!!_");
                	//log.info("CHECKING FOR CERTIFIED COMPANIES!!!!!!!!!_");
                	if(item.getProduct().getCertifiedCompanies()==null){
                		log.info("WAS NULL");
                	}else{
                		log.info("SIZE="+item.getProduct().getCertifiedCompanies().size());
                	}
                	if(item.getProduct().getCertifiedCompanies() != null && item.getProduct().getCertifiedCompanies().size() > 0){
                		if(greenImg == null){
	                	   greenImg = ClwCustomizer.getSIP(request,"green.jpg");
                		}
	                	out.write("<img src='"+greenImg+"' border='0'/>");
                	}else{
                		out.write("&nbsp;");
                	}
                } else {
                    String val = item.getProduct().getProductAttribute(def.getAttributename());
                    if (Utility.isSet(val)) {
                        out.write(val);
                    } else {
                        out.write("&nbsp;");
                    }
                }

        }

    /**
     * Determines if we are rendering an inventory shopping cart or a regular shopping cart.
     * This variable will also be set for access in trailing @see DisplayProductAttributesTag tags later in the page
     * @return true if we are rendering an inventory cart
     */
    public boolean isViewOptionInventoryList() {
            return viewOptionInventoryList;
    }

    public void setViewOptionInventoryList(boolean viewOptionInventoryList) {
            this.viewOptionInventoryList = viewOptionInventoryList;
    }

    /**
     * Determines if we are rendering an item detail view.
     */
    public boolean isViewOptionItemDetail() {
    	return viewOptionItemDetail;
    }

    public void setViewOptionItemDetail(boolean viewOptionItemDetail) {
    	this.viewOptionItemDetail = viewOptionItemDetail;
    }



        /**
         * Sub method to write out the check box. Broken out for size.
         *
         * @param out
         *            Output writter to write to
         * @throws IOException
         */
        private void writeDeleteCheckBoxElement(Writer out) throws IOException {
                ShoppingCartItemData item = getItem();
                if (viewOptionEditCartItems) {
                        // if (inventoryItemInfo != null && inventoryItemInfo.getItemId() >
                        // 0 && !modernInvShopping) {
                        if (item.getIsaInventoryItem() && isViewOptionInventoryList()) {
                                // if this is an inventory item and we are displaying the
                                // inventory cart then we don't display the
                                // checkbox
                                out.write("&nbsp;");
                        } else {

                                // these wierd multiplications were a relic of the old jsp
                                // implementation, I am really unclear why they work this way
                                if (allowAddToCartForInventoryItems()) {
                                        out.write("<!--opt1-->");
                                        // works for regular cart
                                        out.write("<input type=\"checkbox\" name=\"selectBox\" tabindex=\"" + getCurrentDisplayIndex(2000) +
                                                "\" value=\"" + (((Integer) item.getItemId()).longValue() * 10000 +
                                                ((Integer) item.getOrderNumber()).longValue()) +
                                                "\"/  onclick=\"UncheckDeleteAll();\">");/*javascript UncheckDeleteAll is in the lib.js*/
                                } else {
                                        out.write("<!--opt2-->");
                                        // works for inv cart but the bottom section
                                        out.write("<input type=\"checkbox\" name=\"selectBox\" tabindex=\"" +
                                                getCurrentDisplayIndex(3000) + "\" value=\"" + item.getProduct().getProductId() +
                                                "\" onclick=\"UncheckDeleteAll();\">");/*javascript UncheckDeleteAll is in the lib.js*/
                                }
                        }

                        // commented out for user order guide
                        // } else if (appUser.canMakePurchases() && viewOptionQuickOrderView
                        // == false && ShopTool.isInventoryShoppingOn(request) &&
                        // !modernInvShopping) {
                } else if (viewOptionOrderGuide || viewOptionEditOrderGuide) {
                        // deleteing from user order guides
                        // these wierd multiplications were a relic of the old jsp
                        // implementation, I am really unclear why they work this way
                        out.write("<!--opt3-->");
                        out.write("<input type=\"checkbox\" name=\"selectBox\" tabindex=\"" + getCurrentDisplayIndex(2000) + "\" value=\"" + (((Integer) item.getItemId()).longValue() * 10000 + ((Integer) item.getOrderNumber()).longValue()) + "\"/>");
                } else {
                        // default should use product id
                        out.write("<!--opt4-->");
                        out.write("<input type=\"checkbox\" name=\"orderSelectBox\" tabindex=\"" + getCurrentDisplayIndex(3000) + "\" value=\"" + item.getProduct().getProductId() + "\">");

                }
        }


        protected void writeSelectBoxElement(Writer out)  throws IOException {
            ShoppingCartItemData item = getItem();
            SiteInventoryInfoView inventoryItemInfo =  ShopTool.getInventoryItem(request, item.getProduct().getItemData().getItemId());
            boolean modernInvShopping = ShopTool.isModernInventoryShopping(request);
            if (viewOptionEditCartItems) {
                if ( inventoryItemInfo != null && inventoryItemInfo.getItemId() > 0 && !modernInvShopping && !viewOptionEditOrderGuide) {
                    out.write("&nbsp;");
                } else if (viewOptionInvShoppingCart && item.getIsaInventoryItem()) {
                    out.write("&nbsp;");
                } else {
                    out.write("<input type=\"checkbox\" " +
                            "name=\"selectBox\" " +
                            "tabindex=\"" + getCurrentDisplayIndex(2000) + "\" " +
                            "value=\"" + (((Integer) item.getItemId()).longValue() * 10000 + ((Integer) item.getOrderNumber()).longValue()) +"\"/>");
                }
            } else if ( appUser.canMakePurchases() && !viewOptionQuickOrderView &&
                            ShopTool.isInventoryShoppingOn(request)&& !modernInvShopping) {
                /*
                out.write("<input type=\"checkbox\" " +
                        "name=\"orderSelectBox\" " +
                        "tabindex=\"" + getCurrentDisplayIndex(3000) + "\" " +
                        "value=\"" + (((Integer) item.getItemId()).longValue() * 10000 + ((Integer) item.getOrderNumber()).longValue()) +"\"/>");
                 */
                out.write("<input type=\"checkbox\" " +
                        "name=\"orderSelectBox\" " +
                        "tabindex=\"" + getCurrentDisplayIndex(3000) + "\" " +
                        "value=\"" + (item.getItemId()) +"\"/>");

            }
        }



        /**
         * Render the resale checkbox, or hidden element. The Forced flag is called
         * if this was not specfically mapped (i.e. account should not see on UI).
         * In this case only the hidden input tag should be rendered.
         *
         * @param out
         *            Output writter to write to
         * @param forced
         *            wheather this was called forcibly. If true only the hidden
         *            element will be rendered (if necessary)
         * @throws IOException
         */
        protected void writeResaleElement(Writer out, boolean forced) throws IOException {
                // don't check to render as we have custom logic and render hidden
                // elements if necessary
                ShoppingCartItemData item = getItem();

                boolean itemIsResale = item.getReSaleItem();
                boolean resaleItemsAllowed = ShopTool.isResaleItemsAllowed(request);
                boolean resaleItemsAllowedViewOnly = true;


                //only two screens use this...checkout and confirm...if we are not on those screens just return
                if(!(viewOptionCheckout || viewOptionConfirmCheckout)){
                	return;
                }
                if (!forced) {
                        if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_RE_SALE_ITEMS)) {
                                resaleItemsAllowedViewOnly = false;
                        }
                        if (resaleItemsAllowed && !viewOptionEditCartItems && !viewOptionQuickOrderView) {

                                out.write("");
                                if (resaleItemsAllowedViewOnly || viewOptionConfirmCheckout) {
                                        if (itemIsResale) {
                                                out.write(ClwI18nUtil.getMessage(request, "shoppingItems.text.y", null));
                                        } else {
                                                out.write(ClwI18nUtil.getMessage(request, "shoppingItems.text.n", null));
                                        }
                                } else {

                                        out.write("<input type=\"checkbox\" name=\"reSaleSelectBox\" tabindex=\"" + getCurrentDisplayIndex(4000) + "\" value=\"" + item.getProduct().getProductId() + "\"");
                                        if(item.getReSaleItem()){
                                        	out.write(" checked ");
                                        }
                                        out.write("/>");
                                }
                                out.write("");

                        }
                }

                if (!resaleItemsAllowed && itemIsResale && !viewOptionEditCartItems && !viewOptionQuickOrderView) {
                        /*
                         * If the item is taxable, then this is always renderred.
                         */

                        String prop = "reSaleSelectBox[" + getIndex() + "]";
                        out.write("<input type=\"hidden\" name=\"" + prop + "\" value='" + item.getProduct().getProductId() + "'>");
                }
        }



         /**
         * Determines the size of items that need to be displayed.
         * @return
         */
        public int getProductDefinitionsSize(){
                Iterator it = getProductDefinitions().iterator();
                int ct = 0;
                while (it.hasNext()) {
                        ProductViewDefData def = (ProductViewDefData) it.next();
                        if(shouldAttributeBeRendered((def).getAttributename())){
                                if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DELETE)) {
                                        ct++;//delete uses 2 columns
                                        ct++;
                                }else{
                                        ct++;
                                }
                                if(Utility.isSet(getName()) && getName().equals(def.getAttributename())){
                                        return ct;
                                }
                        }
                }
                return ct;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getLink() {
                return link;
        }

        public void setLink(String link) {
                this.link = link;
        }

        /*public int getRenderMode() {
                return renderMode;
        }

        public void setRenderMode(int renderMode) {
                this.renderMode = renderMode;
        }*/

        public int getIndex() {
                return index;
        }

        public void setIndex(int index) {
                this.index = index;
        }

        public boolean isViewOptionEditCartItems() {
                return viewOptionEditCartItems;
        }

        public void setViewOptionEditCartItems(boolean viewOptionEditCartItems) {
                this.viewOptionEditCartItems = viewOptionEditCartItems;
        }

        public boolean isViewOptionShoppingCart(){
                return viewOptionShoppingCart;
        }

        public void setViewOptionShoppingCart(boolean viewOptionShoppingCart) {
                this.viewOptionShoppingCart = viewOptionShoppingCart;
        }

        public boolean isViewOptionInvShoppingCart(){
            return viewOptionInvShoppingCart;
        }

        public void setViewOptionInvShoppingCart(boolean viewOptionInvShoppingCart) {
            this.viewOptionInvShoppingCart = viewOptionInvShoppingCart;
        }

        public boolean isViewOptionCheckout() {
                return viewOptionCheckout;
        }

        public void setViewOptionCheckout(boolean viewOptionCheckout) {
                this.viewOptionCheckout = viewOptionCheckout;
        }

    public boolean isViewOptionConfirmCheckout() {
            return viewOptionConfirmCheckout;
    }

    public void setViewOptionConfirmCheckout(boolean viewOptionConfirmCheckout) {
            this.viewOptionConfirmCheckout = viewOptionConfirmCheckout;
    }

    public boolean isViewOptionReorder() {
            return viewOptionReorder;
    }

    public void setViewOptionReorder(boolean viewOptionReorder) {
            this.viewOptionReorder = viewOptionReorder;
    }

    public boolean isViewOptionCatalog() {
            return viewOptionCatalog;
    }

    public void setViewOptionCatalog(boolean viewOptionCatalog) {
            this.viewOptionCatalog = viewOptionCatalog;
    }

    public boolean isViewOptionModInventory() {
            return viewOptionModInventory;
    }

    public void setViewOptionModInventory(boolean viewOptionModInventory) {
            this.viewOptionModInventory = viewOptionModInventory;
    }

    public boolean isNotShowParOnHandCols() {
            return notShowParOnHandCols;
    }

    public void setNotShowParOnHandCols(boolean notShowParOnHandCols) {
            this.notShowParOnHandCols = notShowParOnHandCols;
    }


    public boolean isViewOptionOrderStatus() {
            return viewOptionOrderStatus;
    }

    public void setViewOptionOrderStatus(boolean viewOptionOrderStatus) {
            this.viewOptionOrderStatus = viewOptionOrderStatus;
    }

        public boolean isViewOptionQuickOrderView() {
                return viewOptionQuickOrderView;
        }

        public void setViewOptionQuickOrderView(boolean viewOptionQuickOrderView) {
                this.viewOptionQuickOrderView = viewOptionQuickOrderView;
        }

        public boolean isViewOptionAddToCartList() {
                return viewOptionAddToCartList;
        }

        public void setViewOptionAddToCartList(boolean viewOptionAddToCartList) {
                this.viewOptionAddToCartList = viewOptionAddToCartList;
        }

        public String getInputNameOnHand() {
                return inputNameOnHand;
        }

        public void setInputNameOnHand(String inputNameOnHand) {
                this.inputNameOnHand = inputNameOnHand;
        }

        public String getInputNameQuantity() {
                return inputNameQuantity;
        }

        public void setInputNameQuantity(String inputNameQuantity) {
                this.inputNameQuantity = inputNameQuantity;
        }

        public boolean isViewOptionOrderGuide() {
                return viewOptionOrderGuide;
        }

        public void setViewOptionOrderGuide(boolean viewOptionOrderGuide) {
                this.viewOptionOrderGuide = viewOptionOrderGuide;
        }

    public boolean isViewOptionEditOrderGuide() {
            return viewOptionEditOrderGuide;
    }

    public void setViewOptionEditOrderGuide(boolean viewOptionEditOrderGuide) {
            this.viewOptionEditOrderGuide = viewOptionEditOrderGuide;
    }

        public String getAltThumbImage() {
                return altThumbImage;
        }

        public void setAltThumbImage(String altThumbImage) {
                this.altThumbImage = altThumbImage;
        }

        public boolean isViewOptionAutoDistro() {
                if("showAutoDistro".equals(request.getParameter("action"))){
                        return true;
                }else{
                        return false;
                }
                //return viewOptionAutoDistro;
        }

        /*public void setViewOptionAutoDistro(boolean viewOptionAutoDistro) {
                this.viewOptionAutoDistro = viewOptionAutoDistro;
        }*/

        public static ProductViewDefDataVector getShoppingCartViewDefinitions(ProductViewDefDataVector pInnerList){

log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF ShoppingCartViewDefinitions");
                ProductViewDefDataVector defs = new ProductViewDefDataVector();
                defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.IA, TEST_CENTER, 15));
                defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE, TEST_CENTER, 30));
                defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, TEST_CENTER, 30));
                defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY, TEST_CENTER, 30));
                addInnerViewListDefs(pInnerList, defs);
                addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEST_RIGHT, 0));
                addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE, TEST_RIGHT, 0));
                addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SELECT, TEST_CENTER, 30));
                return defs ;

        }

    public static ProductViewDefDataVector getInvShoppingCartViewDefinitions(ProductViewDefDataVector pInnerList){
log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF InvShoppingCartViewDefinitions");
            ProductViewDefDataVector defs = new ProductViewDefDataVector();
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.IA, TEST_CENTER, 15));
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE, TEST_CENTER, 30));
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, TEST_CENTER, 30));
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY, TEST_CENTER, 30));
            addInnerViewListDefs(pInnerList, defs);
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEST_RIGHT, 0));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE, TEST_RIGHT, 0));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RESALE, TEST_CENTER, 20));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SELECT, TEST_CENTER, 30));
            return defs ;

    }




        public static ProductViewDefDataVector getCheckoutViewDefinitions(ProductViewDefDataVector pInnerList){
        	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF CheckoutViewDefinitions");
            ProductViewDefDataVector defs = new ProductViewDefDataVector();
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.IA, TEST_CENTER, 15));
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE, TEST_CENTER, 30));
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, TEST_CENTER, 30));
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY, TEST_CENTER, 30));
            addInnerViewListDefs(pInnerList, defs);
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEST_RIGHT, 0));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE, TEST_RIGHT, 0));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SELECT, TEST_CENTER, 30));
            return defs ;

        }


        public static ProductViewDefDataVector getReorderViewDefinitions(ProductViewDefDataVector pInnerList){
        	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF ReorderViewDefinitions");
            ProductViewDefDataVector defs = new ProductViewDefDataVector();
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY, TEST_CENTER, 7));
            addInnerViewListDefs(pInnerList, defs);
            return defs ;
        }



        public static ProductViewDefDataVector getAutoDistroViewDefinitions(){
        	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF AutoDistroViewDefinitions");
                ProductViewDefDataVector autoDistroViewDefs = new ProductViewDefDataVector();
                autoDistroViewDefs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, TEST_CENTER, 15));
                autoDistroViewDefs.add(getViewDef(ProductData.UOM, null, 7));
                autoDistroViewDefs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC, null, 0));
                autoDistroViewDefs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU, TEST_LEFT, 0));
                //autoDistroViewDefs.add(getViewDef(ProductData.PACK, TEST_LEFT, 10));
                autoDistroViewDefs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_PACK, TEST_LEFT, 0));
                autoDistroViewDefs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEST_RIGHT, 0));

                return autoDistroViewDefs ;

        }

        public static ProductViewDefDataVector getOrderGuideViewDefinitions(ProductViewDefDataVector pInnerList) {
        	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF OrderGuideViewDefinitions");
                ProductViewDefDataVector defs = new ProductViewDefDataVector();
                defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.IA, TEST_CENTER, 15));
                defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE, TEST_CENTER, 30));
                defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, TEST_CENTER, 30));
                defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY, TEST_CENTER, 30));
                defs.add(getViewDef(ProductData.THUMBNAIL, TEST_CENTER, 0));
                addInnerViewListDefs(pInnerList, defs);
                addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEST_RIGHT, 0));
                addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE, TEST_RIGHT, 0));
                return defs ;
        }

    public static ProductViewDefDataVector getOrderStatusDefinitions(ProductViewDefDataVector pInnerList) {
    	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF OrderStatusDefinitions");
            ProductViewDefDataVector defs = new ProductViewDefDataVector();
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.IA, TEST_CENTER, 15));
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE, TEST_CENTER, 30));
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, TEST_CENTER, 30));
            defs.add(getViewDef(ProductData.THUMBNAIL, TEST_CENTER, 0));
            addInnerViewListDefs(pInnerList, defs);
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEST_RIGHT, 0));
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY, TEST_CENTER, 30));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.EXTENDED_PRICE, null, 0));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.STATUS, null, 0));
            return defs ;
    }

        public static ProductViewDefDataVector getEditOrderGuideViewDefinitions(ProductViewDefDataVector pInnerList) {
        	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF EditOrderGuideViewDefinitions");
            ProductViewDefDataVector defs = new ProductViewDefDataVector();
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY, TEST_CENTER, 30));
            addInnerViewListDefs(pInnerList, defs);
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATEGORY, TEST_CENTER, 0));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEST_RIGHT, 0));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE, TEST_RIGHT, 0));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SELECT, TEST_CENTER, 30));
            return defs ;
        }

        private static ProductViewDefData getViewDef(String pAttributename, String pStyleClass, int pWidth){
        	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF ViewDef");
                ProductViewDefData prodViewDef = ProductViewDefData.createValue();
                prodViewDef.setAttributename(pAttributename);
                prodViewDef.setStyleClass(pStyleClass);
                prodViewDef.setWidth(pWidth);
                return prodViewDef;
        }

        private static ProductViewDefDataVector getStandardViewDefinitions() {
        	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF StandartViewDefinitions");
            ProductViewDefDataVector def = new ProductViewDefDataVector();
            def.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_INVENTORY_FLAG, TEST_CENTER, 0));
            def.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU, TEST_LEFT, 0));
            def.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC, null, 0));
            def.add(getViewDef(ProductData.SIZE, TEST_LEFT, 0));
            def.add(getViewDef(ProductData.PACK, TEST_LEFT, 0));
            def.add(getViewDef(ProductData.UOM, null, 0));
            def.add(getViewDef(ProductData.COLOR, null, 0));
            def.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_NAME, TEST_CENTER, 0));
            def.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_SKU, TEST_CENTER, 0));
            //def.add(getViewDef(ProductData.UPC_NUM, TEST_CENTER, 10));
            def.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SPL, TEST_RIGHT, 0));
            def.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RESALE, TEST_RIGHT, 0));
            return def;
        }


    private static void addInnerViewListDefs(ProductViewDefDataVector pInnerList, ProductViewDefDataVector pOuterList) {
    	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF addInnerViewListDefs");
        if (pInnerList != null && pOuterList != null) {
            Iterator i = pInnerList.iterator();
            while (i.hasNext()) {
                ProductViewDefData def = (ProductViewDefData)i.next();
                Iterator j = pOuterList.iterator();
                boolean notFound = true;
                while (j.hasNext()) {
                    ProductViewDefData ouDef = (ProductViewDefData)j.next();
                    if (ouDef.getAttributename().equals(def.getAttributename())) {
                        notFound = false;
                        break;
                    }
                }
                if (notFound) {
                    pOuterList.add(def);
                }
            }
        }
    }

    private static void addIfNotFound(ProductViewDefDataVector pList, ProductViewDefData pElement) {
    	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF addIfNotFound");
        Iterator j = pList.iterator();
        boolean notFound = true;
        while (j.hasNext()) {
            ProductViewDefData ouDef = (ProductViewDefData)j.next();
            if (ouDef.getAttributename().equals(pElement.getAttributename())) {
                notFound = false;
                break;
            }
        }
        if (notFound) {
            pList.add(pElement);
        }
    }

}
