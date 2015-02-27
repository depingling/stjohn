package com.espendwise.view.taglibs.esw;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductViewDefData;
import com.cleanwise.service.api.value.ProductViewDefDataVector;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.view.forms.EditOrderGuideForm;
import com.cleanwise.view.forms.UserShopForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;

public class DisplayProductCatalogItemsTag extends TagSupport {
        private static final Logger log = Logger.getLogger(DisplayProductCatalogItemsTag.class);
        static final String TEXT_CENTER = "textCenter";
        static final String TEXT_LEFT = "textLeft";
        static final String TEXT_RIGHT = "textRight";
        int DEFAULT_COLUMN_WIDTH = 25;
        int ECOFRIENDLY_COLUMN_WIDTH = 10;
        private String greenImg = null;
        HttpServletRequest request;
        HttpSession session;
        CleanwiseUser appUser;
        boolean isInventoryShoppingAllowed;
        private String name;
        private String link;// optional
        private int index;
        private String inputNameQuantity; // holds the list name where the items are stored
        private String inputNameOnHand; // holds the list name where the items are stored
        private boolean viewOptionEditCartItems; // whilst on view cart and can edit
        private static boolean viewOptionShoppingCart; // view shopping cart
        private boolean viewOptionInvShoppingCart; // view shopping cart
        private boolean viewOptionCheckout; // checkout view
        private boolean viewOptionConfirmCheckout; // checkout view
        private boolean viewOptionReorder; // checkout view
        private boolean viewOptionCatalog; // product catalog view
        private boolean viewOptionModInventory;
        private boolean viewOptionOrderStatus;
        private boolean viewOptionQuickOrderView; // the express order screen
        private boolean viewOptionAddToCartList; // category view
        private boolean viewOptionOrderGuide; // order guide view
        private boolean viewOptionEditOrderGuide; // order guide view
        private boolean viewOptionInventoryList; // if we are rendering a list of
        private boolean viewOptionItemDetail; //is we are rendering an item detail page
        private boolean viewOptionGroupItemDetail; //is we are rendering an group item detail page

        //uses the action right now
        private String altThumbImage; // if no thumbnail is present default
        private boolean notShowParOnHandCols;
        private Integer iteratorId;
        private Integer tabIteratorId;
        private Boolean _renderSelectBox = false;
        private String formName;
        //STJ - 4985
        private boolean _userShoppingList;
        
        public boolean isUserShoppingList() {
			return _userShoppingList;
		}
		public void setUserShoppingList(boolean userShoppingList) {
			_userShoppingList = userShoppingList;
		}
        
        public Boolean isRenderSelectBox() {
			return _renderSelectBox;
		}
		public void setRenderSelectBox(Boolean _renderSelectBox) {
			this._renderSelectBox = _renderSelectBox;
		}
		private int getCurrentDisplayIndex(int offset) {
        	return incrementTabIndexId();
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
            } else if (viewOptionOrderGuide) {
                result = getOrderGuideViewDefinitions(aList);
            } else if (viewOptionInvShoppingCart) {
                if (ShopTool.isPhysicalCartAvailable(request))
                    result = getPhysicalInvCartViewDefinitions(aList);
                else
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
            	int id1 = 0;
            	int id2 = 0;
            	if(o1!=null) {
            		 id1 = ((ProductViewDefData)o1).getSortOrder();
            	} 
            	if(o2!=null) {
            		 id2 = ((ProductViewDefData)o2).getSortOrder();
            	}
                return id1 - id2;
            }
        };
        // define some request scoped variable locations that the tags uses to
        // communicate
        protected static String IS_INVENTORY_LIST = DisplayProductCatalogItemsTag.class.getName() + ".IS_INVENTORY_LIST";
        private static String ITERATE_ID = DisplayProductCatalogItemsTag.class.getName() + ".ITERATE_ID";
        private static String TAB_ITERATE_ID = DisplayProductCatalogItemsTag.class.getName() + ".TAB_ITERATE_ID";
        /**
         * Called on start of tag, initializes some local variables that the rest of
         * the class uses. If re-use is required then these variable initializations
         * will need to be re-implemented
         */
        public int doStartTag() throws JspException {
                if (getItem() == null) {
                        throw new JspException("Could not find item under name " + getName());
                }
                request = (HttpServletRequest) pageContext.getRequest();
                session = request.getSession();
                appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
                
                if(viewOptionShoppingCart){
                	 //Check if Inventory Shopping is allowed or not.
                	 AccountData account = appUser.getUserAccount();
                	 boolean enableInvOrderProcessing = Utility.isTrue(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_SCHED_DELIVERY),false);
                     boolean allowOrdrInvItems = Utility.isTrue(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_INV_ITEMS),false);
                     boolean enableInvShopping = ShopTool.isInventoryShoppingOn(request);
     	             if(enableInvOrderProcessing && allowOrdrInvItems && enableInvShopping){
     	            	isInventoryShoppingAllowed = true;
     	             }
                }
               
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
			if(viewOptionGroupItemDetail)
				log.info(".... viewOptionGroupItemDetail: "+viewOptionGroupItemDetail);
		
		}
        /**
         * Retrieves the item that we are going to display the values of. That is
         * the specific item in the array (cart, checkout screen, etc) that we need
         * to display the attributes of
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
                                && !viewOptionEditCartItems
                                && !viewOptionCheckout) {
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
                	if(isViewOptionShoppingCart()){//STJ-5130
                    	//return false; reverting STJ-5130 change back for STJ-5274
                    	}        
                	if (appUser.getShowPrice()) {
                            if (viewOptionModInventory) {
                                return true;
                            }
                            if (viewOptionOrderGuide || viewOptionCatalog) {
                                return false;
                            }
                            return true;
                        }
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RE_SALE_ITEM)) {
                	if(!(viewOptionCheckout || viewOptionConfirmCheckout)){
                    	return false;
                    }
                        boolean resaleItemsAllowed = ShopTool.isResaleItemsAllowed(request);
                        if (resaleItemsAllowed && !viewOptionQuickOrderView && appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_RE_SALE_ITEMS)) {
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
                        if (!viewOptionEditOrderGuide && viewOptionEditCartItems || viewOptionQuickOrderView) {//STJ - 4419
                                return true;
                        }

                } else if (attribute.equals(ProductData.THUMBNAIL)) {
                		if (viewOptionGroupItemDetail) {
                			return false;
                		} else if ((viewOptionEditOrderGuide && !isUserShoppingList()) || viewOptionCatalog) {
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
                	if(!viewOptionEditOrderGuide){ // STJ - 4419
	                    if (viewOptionEditCartItems ||
	                            (viewOptionCheckout && !viewOptionConfirmCheckout)) {
	                        return true;
	                    }
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
                    return true;
                } else if(attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RE_SALE_ITEM )&& viewOptionShoppingCart) {
                	return true;
                }else if(attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.EMPTY )&& viewOptionShoppingCart) {
                	return true;
                }
                else {
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
         * Determines if the discretionary cart is available
         * @return true if the discretionary cart is available
         */
        private boolean allowAddToCartForInventoryItems() {
                if (appUser.getSite() != null && ShopTool.isModernInventoryShopping(request) && !ShopTool.hasDiscretionaryCartAccessOpen(request) && session.getAttribute(Constants.INVENTORY_SHOPPING_CART) != null) {
                        return false;
                }
                return true;
        }


        int lineIndex = 0;
        
        Double calculateTotalColumnWidth(ProductViewDefDataVector columnDefs) {
        	Iterator<ProductViewDefData> columns = columnDefs.iterator();
            int totalWidth = 0;
            while (columns.hasNext()) {
            	ProductViewDefData def = columns.next();
            	if (shouldAttributeBeRendered(def.getAttributename())) {
	           	 	int columnWidth = def.getWidth();
	           	 	if (columnWidth <= 0) {
	           	 		columnWidth = DEFAULT_COLUMN_WIDTH;
	           	 	}
	           	 	//account for the additional eco-friendly td used for the product short description
	           	 	if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC)) {
	           	 		columnWidth = columnWidth + ECOFRIENDLY_COLUMN_WIDTH;
	           	 	}
	           	 	totalWidth = totalWidth + columnWidth;
            	}
            }
            Double dTotalWidth = new Double(totalWidth);
            return dTotalWidth;
        }
        
        String determineColumnStyle(ProductViewDefData def, Double totalWidth, String alignment) {
        	int columnWidth = def.getWidth();
           	if (columnWidth <= 0) {
        		columnWidth = DEFAULT_COLUMN_WIDTH;
        	}
          	 //account for the additional eco-friendly td used for the product short description
          	 if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC)) {
          		 columnWidth = columnWidth + ECOFRIENDLY_COLUMN_WIDTH;
          	 }
           	Double dColumnWidth = new Double(columnWidth);
           	long percentage = Math.round((dColumnWidth/totalWidth) * 100);
        	StringBuilder style = new StringBuilder(50);
        	style.append(" style=\"width:" + percentage + "%;");
        	if (!Utility.isSet(alignment)) {
        		alignment = Utility.strNN(def.getStyleClass());
        	}
        	if (alignment.equalsIgnoreCase(TEXT_LEFT)) {
        		style.append(" text-align:left;");
        	}
        	else if (alignment.equalsIgnoreCase(TEXT_CENTER)) {
        		style.append(" text-align:center;");
        	}
        	else if (alignment.equalsIgnoreCase(TEXT_RIGHT)) {
        		style.append(" text-align:right;");
        	}
        	style.append("\"");
        	return style.toString();
        }
        
        /**
         * Renders the xpedx view.
         *
         * @param request
         * @param out
         * @throws IOException
         */
        private void renderRowItemView(Writer out) throws IOException {
       	 
       	 ProductViewDefDataVector columnDefs = getProductDefinitions(true);
       	        	 
       	 //Determine the total width of all the columns
       	Double dTotalWidth = calculateTotalColumnWidth(columnDefs);
            
       	 	Iterator<ProductViewDefData> columns = columnDefs.iterator();
            boolean isOrderTotalColumnFound = false;
            String orderTotalColumnStyle = null;
            while (columns.hasNext()) {
                ProductViewDefData def = (ProductViewDefData) columns.next();
                if (shouldAttributeBeRendered(def.getAttributename())) {
                    String style = determineColumnStyle(def, dTotalWidth, null);
                	noLinks = (getLink() == null || getLink().length() <= 0);
                	if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_SKU) ||
                			def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CUSTOMER_SKU) ||
                			def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_SKU) 
                			) {

                		out.write("<td" + style + "><span class=\"sku\">");
                        if (!getItem().getProduct().isItemGroup()) {
                        	writeElement(out, def);
                        } else {
                        	out.write("&nbsp;");
                        }
                		out.write("</span></td>");
                		
                	} else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC)) {
                     	long percentage = Math.round((ECOFRIENDLY_COLUMN_WIDTH/dTotalWidth) * 100);
                		out.write("<td style=\"width:" + percentage + "%;\" class=\"ecoFriendly\">");
            			displayEcoFriendly(out);
            			out.write("</td>");
            			
                        out.write("<td" + style + ">");
                        writeElement(out, def);
                        out.write("</td>");
                	} else if(def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE) ||
                			def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE)) {
                		
                		out.write("<td" + style + " class=\"right\" >");
            			writeElement(out, def);
                        out.write("</td>");
                		
                	} else if(!def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.GREEN)) {
                		if(viewOptionShoppingCart && def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY)) {
                			out.write("<td" + style + " class=\"qtyAlert\" >");
                			writeElement(out, def);
                            out.write("</td>");
                		} 
                		else if(!viewOptionEditOrderGuide){
                			out.write("<td" + style + " class=\"right\">");
                			writeElement(out, def);
                            out.write("</td>");
                		}
                		else if(viewOptionEditOrderGuide && 
                				!def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SELECT) ){
                			if(def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY) && isUserShoppingList()) {
                				//STJ-5802: Move Qty box from 1st column to column before the Remove / Check-box column 
                				isOrderTotalColumnFound = true;
                 				orderTotalColumnStyle = style;
                			} else {
                				out.write("<td" + style + ">");
	                			writeElement(out, def);
	                            out.write("</td>");
                			}
                		}
                	}
                }
            }
            if(viewOptionEditOrderGuide && isUserShoppingList()){
            	int itemId = getItem().getProduct().getProductId();
    			/*out.write("<td>");
    			out.write("<a href=\"javascript:submitFormForRemoving('removeSelectedItems','setItemsOperation','orderGuideItems','"+ itemId +"')\" class=\"blueBtnMed\"><span>"); 
    			out.write(ClwMessageResourcesImpl.getMessage(request,"global.action.label.remove"));
    			out.write("</span></a>");
    			out.write("</td>");*/
            	
            	 //STJ-5802: Order Quantity column should be displayed before remove item check box.
            	
            	StringBuilder propertyName = new StringBuilder();
                if(getFormName()!=null) {
	               	propertyName.append(getFormName());
	               	propertyName.append(".");
                }
                
            	//Qty text box. 
            	if(isOrderTotalColumnFound) {
            		String lineQty = getItem().getQuantityString();
            		EditOrderGuideForm theForm = getEditOrderGuideForm();
            		lineQty = theForm.getQuantityElement(getIndex());
            		
                	if (lineQty == null || lineQty.equals("0")) {//STJ-4419 qty should be set to "" if it's "0"
                		lineQty = "";
                	}
                        //STJSCR-86 commented out

                	if (!getItem().getDuplicateFlag()) {
                		String onHandVal = getItem().getInventoryQtyOnHandString();
            			boolean enableQtyInputFl =
            			  (!viewOptionInvShoppingCart || !getItem().getIsaInventoryItem() || Utility.isSet(onHandVal))?true:false;
            			
            			out.write("<td" + orderTotalColumnStyle + ">");
            			out.write("<div class=\"qtyInput\">");
            			out.write("<input id=\"IDX_" + getIndex() + "\" " +
            					  "size=\"3\" tabindex=\"" + getCurrentDisplayIndex(1000) + "\" " +
            					  "value=\"" + lineQty + "\" type=\"text\"  onkeyup=\" javascript:selectCheckBox('"+getItem().getItemId()+"','IDX_"+ getIndex()+"')\" " +
            					  "name=\"" + propertyName.toString() + getInputNameQuantity()+"\""+
            					  (enableQtyInputFl? "":" disabled=\"true\"")+
            					  "/> </div>");
            			out.write("</td>");
                	}

                	out.write("<input type=\"hidden\" name="+propertyName.toString()+"itemIdsElement[" + getIndex() + "]\" value=\"" + getItem().getItemId() + "\"/>");
                	out.write("<input type=\"hidden\" name="+propertyName.toString()+"orderNumbersElement[" + getIndex() + "]\" value=\"" + getItem().getOrderNumber() + "\"/>");
            	}
            	
            	//Remove item check box
                propertyName.append("selectBox");
                
                out.write("<td>");
            	//out.write("<label class=\"chkBox\">");
            	out.write("<input type=\"checkbox\" " +
            			"name=\""+propertyName.toString()+"\""+
            			"value=\"" + itemId+"\""+
            			"/>");
            	//out.write("</label>");
            	out.write("</td>");
            }
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
        private void writeElement(Writer out, ProductViewDefData def) throws IOException {
                ShoppingCartItemData item = getItem();

                // deal with special attributes first
                if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND)) {
//                 log.info("writeElement()___________QTY_ON_HAND_COND___________");
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
                                        } else if (item.getIsaInventoryItem() && !allowAddToCartForInventoryItems()) {
                                                out.write("&nbsp;");
                                        } else {
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
                                    	 StringBuilder propertyName = new StringBuilder();
                                         if(getFormName()!=null) {
                             	        	 propertyName.append(getFormName());
                             	        	 propertyName.append(".");
                                         }
                                         propertyName.append(getInputNameOnHand());
                                        out.write("<input id=\"IDX_" + getIndex() + "\" " +
                                                  "size=\"3\" tabindex=\"" + getCurrentDisplayIndex(1000) + "\" " +
                                                  "value=\"" + item.getInventoryQtyOnHandString() + "\" type=\"text\" " +
                                                  "name=\"" + propertyName.toString() + "\">");

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
                                                out.write("NA2A");
                                        } else {
                                                out.write("NA2B");
                                        }
                                } else if (viewOptionAddToCartList) {
                                        if (item.getProduct().isItemGroup()) {
                                                out.write("&nbsp;");
                                        //} else if (item.getIsaInventoryItem() && !allowAddToCartForInventoryItems()) {
                                        //        out.write("&nbsp;");
                                        } else {
                                                if (item.getIsaInventoryItem() && !allowAddToCartForInventoryItems()) {
                                                        out.write("&nbsp;");
                                                } else {
                                                        out.write("NA3");
                                                }
                                        }
                                } else {
                                        out.write(item.getQuantityString());
                                }
                        }//end auto distro view
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE)) {
                        String val = item.getInventoryParValue() > 0 ? String.valueOf(item.getInventoryParValue()) : "0";
                        out.write(val);
                        if( item.getAutoOrderEnable() && item.getInventoryParValue() > 0) {
                        	out.write(" <span class=\"alert\">*</span>");
                        }
                        
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
        			String image = item.getProduct().getImage();
        			String thumbnail = item.getProduct().getThumbnail();
//        			String imgPath = (item.getProduct().isItemGroup()) ? image : ((Utility.isSet(thumbnail)) ? thumbnail : image);
        			String imgPath = (Utility.isSet(thumbnail)) ? thumbnail : image;
//        			log.info("------------THUMBNAIL------------- item.getProduct().isItemGroup()=" + item.getProduct().isItemGroup()+ ", imgPath=" + imgPath);

                        // commented out for performance
                        // if (!Utility.isSet(thumbnail)){
                        // thumbnail = item.getProduct().getImage();
                        // }
                        if (imgPath!=null && Utility.isSet(imgPath.trim())) {
                                if(!noLinks){out.write("<A  HREF=\"" + getLink() + "\">");}
                            	out.write("<img  class=\"icon\"  src=\"/" + getStoreDir() + "/" + imgPath + "\"/>");
                                if(!noLinks){out.write("</A>");}
                        } else {
                                if (Utility.isSet(getAltThumbImage())) {
                                        if(!noLinks){out.write("<A class=\""+getDefaultStyle()+"\" HREF=\"" + getLink() + "\">");}
                                        out.write("<img class=\"icon\" src=\"/" + getStoreDir() + "/en/images/noManXpedxImg.gif\"/>");
                                        if(!noLinks){out.write("</A>");}
                                } else {
                                        out.write("&nbsp;<!--no thumb-->");
                                }
                        }
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU)) {
						String actualSkuNum = (item.getActualSkuNum() == null) ? "" : item.getActualSkuNum();
                        if (!item.getProduct().isItemGroup()){
                        	out.write(actualSkuNum);
                        } else {
                        	out.write("&nbsp;");
                        }
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
                    if (!item.getProduct().isItemGroup()){
                    	out.write("" + item.getProduct().getItemData().getSkuNum());
                    } else {
                    	out.write("&nbsp;");
                    }
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC)) {
                	
                    	/*log.info("CHECKING FOR CERTIFIED COMPANIES!!!!!!!!!_");
                    	if(item.getProduct().getCertifiedCompanies()==null){
                    		log.info("WAS NULL");
                    	}else{
                    		log.info("SIZE="+item.getProduct().getCertifiedCompanies().size());
                    	}
                    	if(item.getProduct().getCertifiedCompanies() != null && item.getProduct().getCertifiedCompanies().size() > 0){
                    		if(greenImg == null){
    	                	   greenImg = ClwCustomizer.getSIP(request,"ecoFriendly.png");
                    		}
    	                	out.write("<img src='"+greenImg+"'/>");
                    	}else{
                    		out.write("&nbsp;");
                    	}*/
                    
                        if(!noLinks){
                        		out.write("<A HREF=\"" + getLink() + "\">");
                        }
                        out.write(item.getProduct().getCatalogProductShortDesc());
                        if(!noLinks){out.write("</A>");}
  	            	    if ((viewOptionCatalog || viewOptionEditOrderGuide) &&
  		            		   item.getProduct().isItemGroup()) {
  		                		 String label = ClwI18nUtil.getMessage(request, "global.label.vewAllItems", null);
  		                		 out.write("<BR>&nbsp;<BR>");
  		                		 out.write("<A HREF=\"" + getLink() + "\" class=\"blueBtnMed left\" style=\"margin-left: 0px;\">");
  		                     	 out.write("<span>"+label+"</span>" );
  		                     	 out.write("</A>" );
  		   		 
  		            	  } 
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE)) {
                        if ((!item.getProduct().isItemGroup() && viewOptionAddToCartList) || !viewOptionAddToCartList) {
                            BigDecimal priceBD = new BigDecimal(item.getPrice());
                            out.write(ClwI18nUtil.getPriceShopping(request, priceBD, "&nbsp;"));
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
                                out.write(ClwI18nUtil.getPriceShopping(request, finalLineAmount, "&nbsp;"));
                        } else {
                                out.write("&nbsp;");
                        }
                }
                else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SPL)) {
                    if ((!item.getProduct().isItemGroup() && viewOptionAddToCartList) || !viewOptionAddToCartList) {
                        if (item.getProduct().getCatalogDistrMapping() != null && item.getProduct().getCatalogDistrMapping().getStandardProductList() != null) {
                                if (Utility.isTrue(item.getProduct().getCatalogDistrMapping().getStandardProductList())) {
                                        out.write(ClwI18nUtil.getMessage(request, "global.text.yes", null));
                                } else {
                                        out.write(ClwI18nUtil.getMessage(request, "global.text.no", null));
                                }
                        } else {
                                out.write(ClwI18nUtil.getMessage(request, "global.text.no", null));
                        }
                    } 
                    else {
                        out.write("&nbsp;");
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
                } /*else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DELETE)) {
                        writeDeleteCheckBoxElement(out);
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SELECT)) {
                        writeSelectBoxElement(out);
                }*/else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RE_SALE_ITEM)) {
                		writeResaleItemCheckBox(out);
                }else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.EMPTY ) && viewOptionShoppingCart) {
                	
                	long selectBoxId = (((Integer) item.getItemId()).longValue() * 10000 + ((Integer) item.getOrderNumber()).longValue());
                	// STJ-6022
                	if (!appUser.isBrowseOnly())  {
                        out.write("<a onclick=\"javascript:removeItemFromShoppingCart('"+selectBoxId+"')\" class=\"blueBtnMed\"><span>");
                        String remove = ClwI18nUtil.getMessage(request, "global.action.label.remove", null, true);
                        out.write(remove);
                        out.write("</span></a>");
                    }
                	
                }
                else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MAX_ORDER_QTY)) {
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
                            out.write("<span>");
                            if (ShopTool.getCurrentSite(request).isAnInventoryAutoOrderItem(item.getProduct().getProductId())) {
                                out.write("&nbsp;");
                            }
                            out.write("</span>");
                        }
                    }
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY) && !isUserShoppingList()) {
//	         		  log.info("writeElement() ====> ORDER_QTY"  );
	            	  if (//(viewOptionCatalog || viewOptionEditOrderGuide) &&
	            		   item.getProduct().isItemGroup()) {
	            		  out.write("&nbsp;");
//	                		 String label = ClwI18nUtil.getMessage(request, "global.label.vewAllItems", null);
//	                		 //out.write("<A HREF=\"#\" class=\"blueBtnMed right\"><span>"+label+"</span></A>" );
//	                     	 out.write("<A HREF=\"" + getLink() + "\" class=\"blueBtnMed right\">");
//	                     	 out.write("<span>"+label+"</span>" );
//	                     	 out.write("</A>" );
	   		 
	            	  } else {
	            		  
                	//STJ-5802: Move Qty box from 1st column to column before the Remove / Check-box column 
                	 StringBuilder propertyName = new StringBuilder();
                     if(getFormName()!=null) {
                    	 propertyName.append(getFormName());
                    	 propertyName.append(".");
                     }
                    // propertyName.append(getInputNameQuantity());
                     
                	//Begin: Shopping Cart view
                    if(viewOptionShoppingCart) {
                    	String lineQty = item.getQuantityString();
                    	if (!Utility.isSet(lineQty)) {
                            lineQty = "0";
                        }

                        if (!item.getDuplicateFlag()) {
                            if (viewOptionEditCartItems) {
                            	 item.getInventoryQtyOnHandString();
                                 String onHandVal = item.getInventoryQtyOnHandString();
                                 boolean enableQtyInputFl = (!viewOptionInvShoppingCart || !item.getIsaInventoryItem() ||
                                		 Utility.isSet(onHandVal))?true:false;
                                 
                                /* StringBuilder propertyName = new StringBuilder();
                                 if(getFormName()!=null) {
                                	 propertyName.append(getFormName());
                                	 propertyName.append(".");
                                 }*/
                                 propertyName.append(getInputNameQuantity());
                                 
                                 out.write("<div class=\"qtyInput\">");
                                 out.write("<input id=\"IDX_" + getIndex() + "\" " +
                                         "size=\"3\" tabindex=\"" + getCurrentDisplayIndex(1000) + "\" " +
                                         "value=\"" + lineQty + "\" type=\"text\" " +
                                         "name=\""+propertyName.toString()+"\""+
                                         (enableQtyInputFl? "":" disabled=\"true\"")+
                                         " class=\"qty\" /> ");
                                 out.write("</div>");
                                 
                                 //Begin: Implementation of PAR Activity line Item functionality.
                                 /*if(isInventoryShoppingAllowed && item.getIsaInventoryItem() && item.getInventoryParValue() > 0){
                                	 List itemHist = item.getItemShoppingCartHistory();
                                	 if ( null != itemHist && itemHist.size() > 0) {
                                		 
                                		 out.write("<div class=\"alertBox\">");
                                      	out.write("<div style=\"display: none;\" class=\"alertNotice\">");
                                         out.write("<table>");
                                             out.write("<thead>");
                                                 out.write("<tr>");
                                                 
                                                     out.write("<th>");
                                                     String action = ClwI18nUtil.getMessage(request, "shoppingCart.text.action");
                                                     out.write(action);
                                                     out.write("</th>");
                                                     
                                                     out.write("<th>");
                                                     String date = ClwI18nUtil.getMessage(request, "shoppingCart.text.date");
                                                     out.write(date);
                                                     out.write("</th>");
                                                     
                                                     out.write("<th>");
                                                     String name = ClwI18nUtil.getMessage(request, "shoppingCart.text.name");
                                                     out.write(name);
                                                     out.write("</th>");
                                                     
                                                 out.write("</tr>");
                                             out.write("</thead>");
                                             out.write("<tbody>");
                                             
                                		 int histLastIdx = itemHist.size() - 1;
                                	      for (int idx = 0; idx < itemHist.size(); idx++) {
                                	        ShoppingInfoData sid = (ShoppingInfoData) itemHist.get(idx);

                                	        if (sid.getArg0() != null && sid.getArg0().equals("nothing")) {
                                	            // get previous. If it was the same then continue
                                	            if (idx < histLastIdx) {
                                	                ShoppingInfoData sidPrev = (ShoppingInfoData) itemHist.get(idx+1);
                                	                if ((sidPrev.getArg0() != null &&
                                	                     sidPrev.getArg0().equals(sid.getArg0())) ||
                                	                    (sidPrev.getArg1() != null &&
                                	                     sidPrev.getArg1().equals(sid.getArg0()))
                                	                    ) {
                                	                    continue;
                                	                }
                                	            } else {
                                	                continue;
                                	            }
                                	        }
                                	        if (sid.getArg0() != null &&
                                	                sid.getArg1() != null &&
                                	                sid.getArg0().equals(sid.getArg1())) {
                                	            continue;
                                	        }
                                	        if (sid.getArg0() != null &&
                                	                sid.getArg0().trim().equals("0") &&
                                	                sid.getMessageKey().equals("shoppingMessages.text.onHandQtySet")) {
                                	            // get previous. If it was the same then continue
                                	            if (idx < histLastIdx) {
                                	                ShoppingInfoData sidPrev = (ShoppingInfoData) itemHist.get(idx+1);
                                	                if ((sidPrev.getArg0() != null &&
                                	                     sidPrev.getArg0().equals(sid.getArg0())) ||
                                	                    (sidPrev.getArg1() != null &&
                                	                     sidPrev.getArg1().equals(sid.getArg0()))
                                	                    ) {
                                	                    continue;
                                	                }
                                	            }
                                	        }
                                	        //String messKey = sid.getMessageKey();
                                	        String actionMessage = "";
                                	        
                                	        if(sid!=null){
                                	        	if( sid.getArg0()!=null && sid.getArg1()==null) {
                                    	        	actionMessage = ClwI18nUtil.getMessage(request, "shoppingCart.text.itemAdded");
                                    	        } 
                                	        	else if(sid.getArg0()==null && sid.getArg1()==null) {
                                    	        	actionMessage = ClwI18nUtil.getMessage(request, "shoppingCart.text.itemRemoved");
                                    	        }
                                	        	else {
                                    	        	actionMessage = ClwI18nUtil.getMessage(request, "shoppingCart.text.itemUpdated");
                                    	        }
                                	        }
                                	        
                                            out.write("<tr>");
                                                out.write("<td>"+actionMessage+"</td>");
                                                //Date and time
                                                String formatedDate = ClwI18nUtil.formatDateInp(request, sid.getModDate());
                                                String formatedTime = ClwI18nUtil.formatTimeInp(request,sid.getModDate());
                                                out.write("<td>"+formatedDate+"&nbsp;"+formatedTime+"</td>");
                                                out.write("<td>"+sid.getModBy()+"</td>");
                                            out.write("</tr>");
                            	      }
                                	      out.write("</tbody>");
                                          out.write("</table>");
                                          out.write("<div class=\"downPointer\"><span>&nbsp;</span></div>");
                                      out.write("</div>");
                                      out.write("<a href=\"#\" class=\"actionBtn\">Alert Notice</a>");
                                  out.write("</div>");
                            	 }
                             }*/
                            //End: Implementation of PAR Activity line Item functionality.
                          
                            } else {
                            	out.write(formatNumber(request,lineQty));
                            }
                        } else {
                        	out.write(formatNumber(request,lineQty));
                        }
                        out.write("<input type=\"hidden\" name="+propertyName.toString()+"itemIdsElement[" + getIndex() + "]\" value=\"" + item.getItemId() + "\"/>");
                        out.write("<input type=\"hidden\" name="+propertyName.toString()+"orderNumbersElement[" + getIndex() + "]\" value=\"" + item.getOrderNumber() + "\"/>");
                    }
                  //End: Shopping Cart view
                    else if(!viewOptionItemDetail) {
                        String lineQty = item.getQuantityString();
                        if (viewOptionEditOrderGuide) {
                            EditOrderGuideForm theForm = getEditOrderGuideForm();
                            lineQty = theForm.getQuantityElement(getIndex());
                        }
                        if (lineQty == null || lineQty.equals("0")) {//STJ-4419 qty should be set to "" if it's "0"
                            lineQty = "";
                        }
                        //STJSCR-86 commented out

                        if (!item.getDuplicateFlag()) {
                            if (viewOptionEditCartItems) {
                                item.getInventoryQtyOnHandString();
                                String onHandVal = item.getInventoryQtyOnHandString();
                                boolean enableQtyInputFl =
                                  (!viewOptionInvShoppingCart || !item.getIsaInventoryItem() || Utility.isSet(onHandVal))?true:false;
                                
                                out.write("<div class=\"qtyInput\">");
                                out.write("<input id=\"IDX_" + getIndex() + "\" " +
                                          "size=\"3\" tabindex=\"" + getCurrentDisplayIndex(1000) + "\" " +
                                          "value=\"" + lineQty + "\" type=\"text\"  onkeyup=\" javascript:selectCheckBox('"+item.getItemId()+"','IDX_"+ getIndex()+"')\" " +
                                          "name=\"" + propertyName.toString() + getInputNameQuantity()+"\""+
                                          (enableQtyInputFl? "":" disabled=\"true\"")+
                                          "/> </div>");
                            } else {
                                out.write(formatNumber(request,lineQty));
                            }
                        } else {
                            out.write(formatNumber(request,lineQty));
                        }
                        out.write("<input type=\"hidden\" name="+propertyName.toString()+"itemIdsElement[" + getIndex() + "]\" value=\"" + item.getItemId() + "\"/>");
                        out.write("<input type=\"hidden\" name="+propertyName.toString()+"orderNumbersElement[" + getIndex() + "]\" value=\"" + item.getOrderNumber() + "\"/>");

                      } else {
                            out.write("<input id=\"IDX_" + getIndex() + "\" size=\"3\" tabindex=\"" +
                                    getCurrentDisplayIndex(1000) +
                                    "\" value=\"" + item.getQuantityString() + "\" type=\"text\" name=\"" +
                                    getInputNameQuantity() + "\">");
                      }
	               } //end if group Item element
                }  
                    if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATEGORY)) {
                        out.write(item.getCategoryPath());
                     }  else {
                        String val = item.getProduct().getProductAttribute(def.getAttributename());
                        boolean isThumbnailAttribute = def.getAttributename().equals(ProductData.THUMBNAIL);
                        if (Utility.isSet(val) && !isThumbnailAttribute) {
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
     * Determines if we are rendering an group item detail view.
     */
    public boolean isViewOptionGroupItemDetail() {
    	return viewOptionGroupItemDetail;
    }

    public void setViewOptionGroupItemDetail(boolean viewOptionGroupItemDetail) {
    	this.viewOptionGroupItemDetail = viewOptionGroupItemDetail;
    }    
    protected void writeResaleItemCheckBox(Writer out)  throws IOException {
    	 ShoppingCartItemData item = getItem();
         StringBuilder propertyName = new StringBuilder();
         if(getFormName()!=null) {
        	 propertyName.append(getFormName());
        	 propertyName.append(".");
         }
         propertyName.append("reSaleSelectBox");
         
         boolean resaleItemsAllowed = ShopTool.isResaleItemsAllowed(request);
         
         if(resaleItemsAllowed && !viewOptionQuickOrderView) {
        	 if (viewOptionCheckout) {
        		 String checked="";
             	 if(item.getReSaleItem()) {
             		checked = "checked";
             	 }
            	 out.write("<label class=\"chkBoxSm\">");
                 out.write("<input type=\"checkbox\" " +checked+" " +
                		 "name=\""+propertyName.toString()+"\""+
                         "tabindex=\"" + getCurrentDisplayIndex(4000) + "\" " +
                         "value=\"" + item.getProduct().getProductId() +"\"/>");
                 out.write("</label>");
                 
             } else if ( viewOptionConfirmCheckout) { //Order Confirmation screen
            	 /*//Checkbox "Resale item" should NOT be editable 
            	 String resaleCheckboxHtml = "<input disabled=\"disabled\" type=\"checkbox\" "; //NOT a Resale item 
             	 if(item.getReSaleItem()) {
             		//Resale item
             		resaleCheckboxHtml = "<input disabled=\"disabled\" checked=\"checked\" type=\"checkbox\" "; 
             	 }

            	 out.write("<label class=\"chkBoxSm\">");            	 
            	 log.debug("resaleCheckboxHtml = " + resaleCheckboxHtml);
            	 out.write(resaleCheckboxHtml +	
                		 "name=\""+propertyName.toString()+"\""+
                         "tabindex=\"" + getCurrentDisplayIndex(4000) + "\" " +
                         "value=\"" +  item.getProduct().getProductId() +"\"/>");
                 out.write("</label>");*/
            	 
            	 //Display YES/NO on confirmation screen 
            	 if(item.getReSaleItem()){
            		 out.write(ClwI18nUtil.getMessage(request, "global.text.yes", null));
            	 }else{
            		 out.write(ClwI18nUtil.getMessage(request, "global.text.no", null));
            	 }
             } else {
            	 out.write("&nbsp;");
             }
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
        }
        
        

        /**
		 * @return the formName
		 */
		public String getFormName() {
			return formName;
		}
		/**
		 * @param pFormName the formName to set
		 */
		public void setFormName(String pFormName) {
			formName = pFormName;
		}
		
		public static ProductViewDefDataVector getShoppingCartViewDefinitions(ProductViewDefDataVector pInnerList){
			log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF ShoppingCartViewDefinitions");
                ProductViewDefDataVector defs = new ProductViewDefDataVector();
                //defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.IA, TEST_CENTER, 15));
                //defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE, TEST_CENTER, 30));
                //defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, TEST_CENTER, 30));
                //defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY, TEST_CENTER, 30));
                addInnerViewListDefs(pInnerList, defs);
                
               // addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SELECT, TEST_CENTER, 30));
                addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEXT_RIGHT, 20));
                addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY, TEXT_CENTER, 0));
                addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE, TEXT_RIGHT, 20));
                //addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RE_SALE_ITEM, TEST_CENTER, 0));
                addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.EMPTY, TEXT_CENTER, 0));
                
           return defs ;

        }

    public static ProductViewDefDataVector getPhysicalInvCartViewDefinitions(ProductViewDefDataVector pInnerList){
    	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF getPhysicalInvCartViewDefinitions");
        ProductViewDefDataVector defs = new ProductViewDefDataVector();
        defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, TEXT_CENTER, 8));
        defs.add(getViewDef(ProductData.UOM, TEXT_CENTER, 8));
        defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC, null, 30));
        defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU, TEXT_CENTER, 10));
        defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_PACK, TEXT_CENTER, 10));
        defs.add(getViewDef(ProductData.SIZE, TEXT_CENTER, 20));
       return defs ;

    }
    
    public static ProductViewDefDataVector getInvShoppingCartViewDefinitions(ProductViewDefDataVector pInnerList){
        log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF InvShoppingCartViewDefinitions");
            ProductViewDefDataVector defs = new ProductViewDefDataVector();
            //defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.IA, TEST_CENTER, 15));
            addInnerViewListDefs(pInnerList, defs);
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEXT_RIGHT, 20));
            addIfNotFound(defs,getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE, TEXT_CENTER, 30));
            addIfNotFound(defs,getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, TEXT_CENTER, 30));
            addIfNotFound(defs,getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY, TEXT_CENTER, 30));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE, TEXT_RIGHT, 20));
            //addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RESALE, TEST_CENTER, 20));
            //addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SELECT, TEST_CENTER, 30));
       return defs ;

    }


        public static ProductViewDefDataVector getCheckoutViewDefinitions(ProductViewDefDataVector pInnerList){
        	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF CheckoutViewDefinitions");
            ProductViewDefDataVector defs = new ProductViewDefDataVector();
            addInnerViewListDefs(pInnerList, defs);
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.IA, TEXT_CENTER, 15));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE, TEXT_CENTER, 30));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, TEXT_CENTER, 30));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEXT_RIGHT, 0));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY, TEXT_CENTER, 30));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE, TEXT_RIGHT, 0));
            //addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SELECT, TEST_CENTER, 30));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RE_SALE_ITEM, TEXT_CENTER, 0));
            return defs ;

        }


        public static ProductViewDefDataVector getReorderViewDefinitions(ProductViewDefDataVector pInnerList){
        	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF ReorderViewDefinitions");
            ProductViewDefDataVector defs = new ProductViewDefDataVector();
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY, TEXT_CENTER, 7));
            addInnerViewListDefs(pInnerList, defs);
            return defs ;
        }



        public static ProductViewDefDataVector getAutoDistroViewDefinitions(){
        	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF AutoDistroViewDefinitions");
                ProductViewDefDataVector autoDistroViewDefs = new ProductViewDefDataVector();
                autoDistroViewDefs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, TEXT_CENTER, 15));
                autoDistroViewDefs.add(getViewDef(ProductData.UOM, null, 7));
                autoDistroViewDefs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC, null, 0));
                autoDistroViewDefs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU, TEXT_LEFT, 0));
                //autoDistroViewDefs.add(getViewDef(ProductData.PACK, TEST_LEFT, 10));
                autoDistroViewDefs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_PACK, TEXT_LEFT, 0));
                autoDistroViewDefs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEXT_RIGHT, 0));

                return autoDistroViewDefs ;

        }

        public static ProductViewDefDataVector getOrderGuideViewDefinitions(ProductViewDefDataVector pInnerList) {
        	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF OrderGuideViewDefinitions");
                ProductViewDefDataVector defs = new ProductViewDefDataVector();
                defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.IA, TEXT_CENTER, 15));
                defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE, TEXT_CENTER, 30));
                defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, TEXT_CENTER, 30));
                if (ifConfigured(pInnerList, ProductData.THUMBNAIL )){
                	defs.add(getViewDef(ProductData.THUMBNAIL, TEXT_CENTER, 0));  //NG moving thumbnail before QTY
                }
                defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY, TEXT_CENTER, 30));
             //   defs.add(getViewDef(ProductData.THUMBNAIL, TEST_CENTER, 0));
                addInnerViewListDefs(pInnerList, defs);
                addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEXT_RIGHT, 0));
                addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE, TEXT_RIGHT, 0));
                return defs ;
        }

    public static ProductViewDefDataVector getOrderStatusDefinitions(ProductViewDefDataVector pInnerList) {
    	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF OrderStatusDefinitions");
            ProductViewDefDataVector defs = new ProductViewDefDataVector();
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.IA, TEXT_CENTER, 15));
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE, TEXT_CENTER, 30));
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, TEXT_CENTER, 30));
            defs.add(getViewDef(ProductData.THUMBNAIL, TEXT_CENTER, 0));
            addInnerViewListDefs(pInnerList, defs);
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEXT_RIGHT, 0));
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY, TEXT_CENTER, 30));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.EXTENDED_PRICE, null, 0));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.STATUS, null, 0));
            return defs ;
    }

        public static ProductViewDefDataVector getEditOrderGuideViewDefinitions(ProductViewDefDataVector pInnerList) {
        	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF EditOrderGuideViewDefinitions");
            ProductViewDefDataVector defs = new ProductViewDefDataVector();
            if (ifConfigured(pInnerList, ProductData.THUMBNAIL )){
            	defs.add(getViewDef(ProductData.THUMBNAIL, TEXT_CENTER, 0));  //NG moving thumbnail before QTY
            }
            defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY, TEXT_CENTER, 30));
            addInnerViewListDefs(pInnerList, defs);
            //defs.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATEGORY, TEST_CENTER, 0));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEXT_RIGHT, 0));
            //addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE, TEST_RIGHT, 0));
            addIfNotFound(defs, getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SELECT, TEXT_CENTER, 30));
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
            def.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_INVENTORY_FLAG, TEXT_CENTER, 0));
            def.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU, TEXT_LEFT, 0));
            def.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC, null, 0));
            def.add(getViewDef(ProductData.PACK, TEXT_LEFT, 0));
            def.add(getViewDef(ProductData.UOM, null, 0));
            //STJ-5134.
            if(!viewOptionShoppingCart) {
	            def.add(getViewDef(ProductData.SIZE, TEXT_LEFT, 0));
	            def.add(getViewDef(ProductData.COLOR, null, 0));
            }
            def.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_NAME, TEXT_CENTER, 0));
            def.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_SKU, TEXT_CENTER, 0));
            //def.add(getViewDef(ProductData.UPC_NUM, TEST_CENTER, 10));
            def.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SPL, TEXT_RIGHT, 0));
            //def.add(getViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RE_SALE_ITEM, TEST_RIGHT, 0));
            return def;
        }


    private static void addInnerViewListDefs(ProductViewDefDataVector pInnerList, ProductViewDefDataVector pOuterList) {
    	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF addInnerViewListDefs");
        if (pInnerList != null && pOuterList != null) {
            Iterator i = pInnerList.iterator();
            while (i.hasNext()) {
                ProductViewDefData def = (ProductViewDefData)i.next();
                //Re-Sale Item should be renamed to Resale Item
                if(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RESALE.equals(def.getAttributename())) {
                	def.setAttributename(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RE_SALE_ITEM);
                }
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
    private static boolean ifConfigured(ProductViewDefDataVector pList, String pAttributeName) {
    	log.debug("CommonDisplayProductAttributesTag FFFFFFFFFFFFFFFF ifConfigured");
        Iterator j = pList.iterator();
        boolean found = false;
        while (j.hasNext()) {
            ProductViewDefData ouDef = (ProductViewDefData)j.next();
            if (ouDef.getAttributename().equals(pAttributeName)) {
                found = true;
                break;
            }
        }
        return found;
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
    
    private void displayEcoFriendly(Writer out) throws IOException{
    	log.debug("CHECKING FOR CERTIFIED COMPANIES!!!!!!!!!");
    	ShoppingCartItemData item = getItem();
    	if(item.getProduct().getCertifiedCompanies()==null){
    		log.debug("WAS NULL");
    	}else{
    		log.debug("SIZE="+item.getProduct().getCertifiedCompanies().size());
    	}
    	if(item.getProduct().getCertifiedCompanies() != null && item.getProduct().getCertifiedCompanies().size() > 0){
    		if(greenImg == null){
        	   greenImg = ClwCustomizer.getSIP(request,"ecoFriendly.png");
    		}
        	//out.write("<img src='"+greenImg+"'/>");<img src="../../esw/images/ecoFriendly.png"
    		out.write("<img src=\"../../esw/images/ecoFriendly.png\" alt=\"eco-friendly\" title=\"eco-friendly\" />");
    	}else{
    		out.write("&nbsp;");
    	}
    }
    
    /**
     * Formats the given number according to the user locale.
     * @param request - the <code>HttpServletRequest</code> currently being handled
     * @param value  - a <code>String</code> containing value that needs to be formatted.
     * @return formattedValue - a <code>String</code> containing formatted Number.
     */
    public static String formatNumber(HttpServletRequest request, String value){
    	String formattedValue = ""; 
    	try {
    		Locale locale = ClwI18nUtil.getUserLocale(request);
            NumberFormat format = DecimalFormat.getNumberInstance(locale);
            formattedValue = format.format(Integer.parseInt(value));
    	} catch (Exception e) {
    		formattedValue = String.valueOf(value);
    	}
        return formattedValue;
    }
}
