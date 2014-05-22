package com.cleanwise.view.taglibs;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Category;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductViewDefData;
import com.cleanwise.service.api.value.ProductViewDefDataVector;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.view.forms.UserShopForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;

public class DisplayProductAttributesTag extends TagSupport {
	private static final Category log = Category.getInstance(DisplayProductAttributesTag.class);
	private static final String TEST_CENTER = "textCenter";
	private static final String TEST_LEFT = "textLeft";
	private static final String TEST_RIGHT = "textRight";


	//private int colIdx = 0; // used to keep track of how many tds we have done

	private String name;

	private String link;// optional

	private int index;

	//private int renderMode;

	private String inputNameQuantity; // holds the list name where the items
	
	// are stored
	
	private String inputNameItemId;

	private String inputNameOnHand; // holds the list name where the items are

	// stored

	private boolean viewOptionEditCartItems; // whilst on view cart and can edit
	private boolean updateAutoDistro; // can update distro qtys
	private boolean viewOptionShoppingCart; // view shopping cart
	private boolean viewOptionCheckout; // checkout view

	// change quantities

	private boolean viewOptionQuickOrderView; // the express order screen

	private boolean viewOptionAddToCartList; // category view

	private boolean viewOptionOrderGuide; // order guide view
	
	private boolean viewOptionGroupedItemDetail;  //grouped item detail view
	
	private boolean physicalInvCart; 			//Physical inventory cart

	//uses the action right now
	//private boolean viewOptionAutoDistro; // auto distro for apple only

	private String altThumbImage; // if no thumbnail is present default

	private Integer iteratorId;

	private static ProductViewDefDataVector shoppingCartViewDefinitions = getShoppingCartViewDefinitions();
	private static ProductViewDefDataVector checkoutViewDefinitions = getCheckoutViewDefinitions();
	private static ProductViewDefDataVector autoDistroViewDefs = getAutoDistroViewDefinitions();
	private static ProductViewDefDataVector orderGuideViewDefs = getOrderGuideViewDefinitions();
	private static ProductViewDefDataVector defaultViewDefs = getDefaultViewDefinitions();

    private String xhrArgs;

	private int getCurrentDisplayIndex(int offset) {
		if (iteratorId == null) {
			return offset;
		}
		return iteratorId.intValue() + offset;
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
        	if (viewOptionCheckout)
        		return checkoutViewDefinitions;

        	if (isViewOptionAutoDistro())
        		return autoDistroViewDefs;

        	if (viewOptionOrderGuide)
        		return orderGuideViewDefs;

        	if (viewOptionShoppingCart)
        		return shoppingCartViewDefinitions;
        	
        	ProductViewDefDataVector defsMast = null;
        	if(viewOptionGroupedItemDetail){
        		 defsMast = appUser.getUserAccount().getProductViewDefinitions(RefCodeNames.SHOP_UI_PRODUCT_VIEW_CD.SHOP_UI_MULTI);
        	} else {
        		defsMast = appUser.getUserAccount().getProductViewDefinitions(RefCodeNames.SHOP_UI_PRODUCT_VIEW_CD.SHOP_UI_DEFAULT);
        	}
        	//default values 
        	if(defsMast == null || defsMast.size() < 1 ){
        		return defaultViewDefs;
        	}
        	ArrayList aList = (defsMast==null)? (new ArrayList()):((ArrayList) defsMast.clone()); //clone to prevent multiple requests from sorting the list different ways
        	ProductViewDefDataVector defs = new ProductViewDefDataVector();
        	Iterator it = aList.iterator();
        	while(it.hasNext()){
        		ProductViewDefData def = (ProductViewDefData) it.next();
        		if(filterByShouldBeRendered){
        			if(shouldAttributeBeRendered(def.getAttributename())){
        				defs.add(def);
        			}
        		}else{
        			defs.add(def);
        		}
        	}
        	Collections.sort(defs,PRODUCT_DEF_SORT);
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
	public boolean isViewOptionInventoryList() {
		Boolean isInvList = (Boolean) request.getAttribute(IS_INVENTORY_LIST);
		if (isInvList == null) {
			return false;
		} else {
			return isInvList.booleanValue();
		}
	}

	// define some request scoped variable locations that the tags uses to
	// communicate
	protected static String IS_INVENTORY_LIST = DisplayProductAttributesTag.class.getName() + ".IS_INVENTORY_LIST";

	private static String ITERATE_ID = DisplayProductAttributesTag.class.getName() + ".ITERATE_ID";

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

	/**
	 * Called on end of the tag Renders the content
	 */
	public int doEndTag() throws JspException {

		Writer out = pageContext.getOut();
		try {
			renderXpedxB2BRowItemView(out);
		} catch (IOException e) {
			throw new JspException(e);
		}

		return BodyTagSupport.EVAL_PAGE;

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
		if (viewOptionCheckout || isViewOptionAutoDistro() || viewOptionShoppingCart){
			if( (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE)) 
					|| (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE))) {
				if (appUser.getShowPrice()) {
					return true;
				}else{
					return false;
				}
			
			}else{
				return true;
			}
		}
		
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
			} else if( (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE)) 
					|| (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE))) {
				if (appUser.getShowPrice()) {
					return true;
				}else{
					return false;
				}
			
			}else{
				return true;
			}

		}
		if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND)) {
			boolean isUsedPhysicalInventoryAlgorithm = ShopTool.isUsedPhysicalInventoryAlgorithm(request);
			boolean isPhysicalCartAvailable = ShopTool.isPhysicalCartAvailable(request);
			if(physicalInvCart){
				return true;
			}
			if(isUsedPhysicalInventoryAlgorithm && isPhysicalCartAvailable){
				return false;
			}
	/*		
			if(!(viewOptionEditCartItems && (Utility.isTrue((String)appUser.getUserProperties().get(RefCodeNames.PROPERTY_TYPE_CD.CORPORATE_USER))))){
				return false;
			}
			*/
			if(isViewOptionInventoryList() && !viewOptionAddToCartList){

				if(ShopTool.isUsedPhysicalInventoryAlgorithm(request)){
					if(ShopTool.isScheduledCart(request, session) ){  
						if( !(Utility.isTrue((String)appUser.getUserProperties().get(RefCodeNames.PROPERTY_TYPE_CD.CORPORATE_USER))) ) {
							return false;
						}
						
					}
				}
			}
			if (appUser.canMakePurchases()) {
				return true;
			}
		} else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MAX_ORDER_QTY)){
			if(isViewOptionInventoryList()){
				return false;
			}
			return true;
		} else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE)) {
			if (ShopTool.isInventoryShoppingOn(request)) {
				return true;
			}
		} else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE)) {
			if (isViewOptionInventoryList()) {
				//this is xpedx specific
				return false;
			}
			if (appUser.getShowPrice()) {
				return true;
			}
		} else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE)) {
			if (viewOptionAddToCartList) {
				return false;
			}
			if (isViewOptionInventoryList()) {
				//this is xpedx specific
				return false;
			}
			if (appUser.getShowPrice()) {
				return true;
			}
		} else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RESALE)) {
			boolean resaleItemsAllowed = ShopTool.isResaleItemsAllowed(request);
			if (resaleItemsAllowed && !viewOptionEditCartItems && !viewOptionQuickOrderView) {
				return true;
			}
		} else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DELETE)) {
			UserShopForm shopForm = getShoppingForm();
			boolean isUserOG = false;
			if (shopForm != null) {
				isUserOG = shopForm.isUserOrderGuide(shopForm.getOrderGuideId());
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
			if (viewOptionAddToCartList) {
				return true;
			}
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

	/**
	 * Determines if forecasted items can be ordered during scheduled cart period
	 * @return true if the discretionary cart is avaliable
	 */
	private boolean allowOrderInventoryItems() {
		if (ShopTool.isModernInventoryShopping(request) && !ShopTool.hasDiscretionaryCartAccessOpen(request) && session.getAttribute(Constants.INVENTORY_SHOPPING_CART) != null) {

			if(appUser.getUserAccount().getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_INV_ITEMS).equals("false")){
				return false;
			}
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
	private void renderXpedxB2BRowItemView(Writer out) throws IOException {
            boolean first = true;
            Iterator it = getProductDefinitions(true).iterator();
            while (it.hasNext()) {
                ProductViewDefData def = (ProductViewDefData) it.next();
		if (!shouldAttributeBeRendered(def.getAttributename())) {
			continue;
		}

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

		if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DELETE)) {
			out.write("<td class=\"" + style + "\" width=\"" + def.getWidth()/2 + "%\">");
	        out.write("<!--"+def.getAttributename()+"-->");
			writeElement(out, def);
			out.write("</td>");
			out.write("<td class=\"" + style +"\" width=\"" + def.getWidth()/2 + "%\">");
			out.write("&nbsp;");
			out.write("</td>");
		}else{
			out.write("<td class=\"" + style + "\" width=\"" + def.getWidth() + "%\">");
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
         * Writes out the given string if it is not null.  If it is null nothing is written to the stream
         * @param out the writer object to write to.
         * @param s the string to write
         */
        private void safeWrite(Writer out, String s) throws IOException{
        	if(s != null){
        		out.write(s);
        	}
        }
        
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
	boolean isUsedPhysicalInventoryAlgorithm = ShopTool.isUsedPhysicalInventoryAlgorithm(request);
	boolean isPhysicalCartAvailable = ShopTool.isPhysicalCartAvailable(request);
	if (viewOptionGroupedItemDetail) {
		noLinks = true;
	}
		// deal with special attributes first
		if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND)) {
			if(isViewOptionAutoDistro()){
                                if (updateAutoDistro){
                                  // shopping cart, any time editing items in the cart
                                  out.write("<input id=\"IDX_" + getIndex() + "\" size=\"3\" tabindex=\"" + getCurrentDisplayIndex(1000) + "\" value=\"" + Integer.toString(item.getInventoryParValue()) + "\" type=\"text\" name=\"" + getInputNameQuantity() + "\">");
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
				} else if (viewOptionOrderGuide) {

					// order guide and category screens
					if (item.getProduct().isItemGroup()) {
						// order guide or category view for a group item is not
						// allowed
						out.write("&nbsp;");
						// } else if (!ShopTool.isModernInventoryShopping(request)
						// && item.getIsaInventoryItem() &&
						// ShopTool.isInventoryShoppingOn(request)) {
					} else if (item.getIsaInventoryItem() && !allowAddToCartForInventoryItems() && !allowOrderInventoryItems()) {
						out.write("&nbsp;");
					} else {
						out.write("<input id=\"IDX_" + getIndex() + "\" size=\"3\" tabindex=\"" + getCurrentDisplayIndex(1000) + "\" value=\"" + item.getQuantityString() + "\" type=\"text\" name=\"" + getInputNameQuantity() + "\">");

					}
				} else if (viewOptionGroupedItemDetail) {
					noLinks = true;
					UserShopForm form =getShoppingForm();
					 if(isUsedPhysicalInventoryAlgorithm && isPhysicalCartAvailable){
							//out.write("&nbsp;");
					} else{
					if(form.getAllowPurchase()){
						if (item.getIsaInventoryItem() && !allowAddToCartForInventoryItems() && !allowOrderInventoryItems()) {
							out.write("&nbsp;");
						} else {
							out.write("<input id=\"IDX_" + getIndex() + "\" size=\"3\" tabindex=\"" + getCurrentDisplayIndex(1000) + "\" value=\"" + item.getQuantityString() + "\" type=\"text\" name=\"" + getInputNameQuantity() + "\">");
							out.write("<input size=\"3\" tabindex=\"" + getCurrentDisplayIndex(1000) + "\" value=\"" + String.valueOf(item.getProduct().getProductId()) + "\" type=\"hidden\" name=\"" + getInputNameItemId() + "\">");
						}
					}
					}
				}else if (viewOptionQuickOrderView) {
					// express order (quick order)
					out.write(item.getQuantityString());
				} else if (viewOptionEditCartItems) {
					//If the user is a corporate user OR
					//We are in the physical inventory cart (so items should be editable for all user...purpose of phys setup) OR 
					//We are not using the physical inventory setup (use the checkbox at account page only) OR
					//The item is not an inventory item, in which case we can ignore inventory specific functionality OR
					//We are in regular shopping cart
					if((Utility.isTrue((String)appUser.getUserProperties().get(RefCodeNames.PROPERTY_TYPE_CD.CORPORATE_USER))) 
							|| (physicalInvCart) 
							|| !ShopTool.isUsedPhysicalInventoryAlgorithm(request) 
							|| !item.getIsaInventoryItem()
							|| (ShopTool.isRegularCart(request,session))){
					// for non-xpedx might need to be more inteligent...this will
					// render one or the other, but not both qty and inventory
					if (item.getIsaInventoryItem() && !allowAddToCartForInventoryItems()) {
						// never do this for xpedx!
						out.write("<span class=\"inv_item\">i"); 
						if (item.getAutoOrderEnable()) {
							out.write("a");
						}
						out.write("</span>");
						
						if(Utility.isTrue((String)appUser.getUserProperties().get(RefCodeNames.PROPERTY_TYPE_CD.CORPORATE_USER))){
							out.write("<input "+
									  (Utility.isSet(xhrArgs)?"onchange=\"if(!hrPost){ xhrPost = true; dojo.xhrPost("+xhrArgs+"); }\"":"")+
									  " id=\"IDX_" + getIndex() + "\" size=\"3\" tabindex=\"" + getCurrentDisplayIndex(1000) + "\" value=\"" +
									  item.getInventoryQtyOnHandString() + "\" type=\"text\" name=\"" + getInputNameOnHand() + "\">");;
										
						}else if(isUsedPhysicalInventoryAlgorithm && !isPhysicalCartAvailable) {
                          out.write("<input "+
						  (Utility.isSet(xhrArgs)?"onchange=\"if(!hrPost){ xhrPost = true; dojo.xhrPost("+xhrArgs+"); }\"":"")+
						  " id=\"IDX_" + getIndex() + "\" size=\"3\" tabindex=\"" + getCurrentDisplayIndex(1000) + "\" value=\"" +
						  item.getInventoryQtyOnHandString() + "\" type=\"text\" name=\"" + getInputNameOnHand() + "\" READONLY>");
						} else {
                          out.write("<input "+
						  (Utility.isSet(xhrArgs)?"onchange=\"if(!hrPost){ xhrPost = true; dojo.xhrPost("+xhrArgs+"); }\"":"")+
						  " id=\"IDX_" + getIndex() + "\" size=\"3\" tabindex=\"" + getCurrentDisplayIndex(1000) + "\" value=\"" +
						  item.getInventoryQtyOnHandString() + "\" type=\"text\" name=\"" + getInputNameOnHand() + "\">");
						}
					} else {
						// shopping cart, any time editing items in the cart
						out.write("<input id=\"IDX_" + getIndex() + "\" size=\"3\" tabindex=\"" + getCurrentDisplayIndex(1000) + "\" value=\"" + item.getQuantityString() + "\" type=\"text\" name=\"" + getInputNameQuantity() + "\">");
					}
				}
				} else if (viewOptionAddToCartList) {
					if (item.getProduct().isItemGroup()) {
						// order guide or category view for a group item is not
						// allowed
						out.write("&nbsp;");
					} else if (item.getIsaInventoryItem() && !allowAddToCartForInventoryItems() && !allowOrderInventoryItems()) {
						out.write("&nbsp;");
					} else {
						// shopping cart, any time editing items in the cart
						if (item.getIsaInventoryItem() && !allowAddToCartForInventoryItems()) {
							out.write("&nbsp;");
						} else {
							out.write("<input id=\"IDX_" + getIndex() + "\" size=\"3\" tabindex=\"" + getCurrentDisplayIndex(1000) + "\" value=\"" + item.getQuantityString() + "\" type=\"text\" name=\"" + getInputNameQuantity() + "\">");
						}
					}
				} else {
					// checkout screen and any others
					out.write(item.getQuantityString());
				}
			}//end auto distro view
		} else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE)) {
			out.write(item.getInventoryParValue());
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
			if(!noLinks){out.write("<A class=\""+getDefaultStyle()+"\" HREF=\"" + getLink() + "\">");}
			safeWrite(out,item.getActualSkuNum());
			if(!noLinks){out.write("</A>");}
		} else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC)) {
			if(!noLinks){out.write("<A class=\""+getDefaultStyle()+"\" HREF=\"" + getLink() + "\">");}
			safeWrite(out,item.getProduct().getCatalogProductShortDesc());
			if(!noLinks){out.write("</A>");}
		} else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE)) {
			if ((!item.getProduct().isItemGroup() && viewOptionAddToCartList) || !viewOptionAddToCartList) {
                BigDecimal priceBD = new BigDecimal(item.getPrice() );
                out.write(ClwI18nUtil.getPriceShopping(request, priceBD, "<br>"));
            } else {
				out.write("&nbsp;");
			}
		} else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE)) {

			if ((!item.getProduct().isItemGroup() && viewOptionAddToCartList) || !viewOptionAddToCartList) {
				BigDecimal finalLineAmount;
				if (item.getIsaInventoryItem()) {
					finalLineAmount = new java.math.BigDecimal(item.getPrice() * item.getInventoryOrderQty());
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
			out.write(item.getProduct().getManufacturerName());
		} else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_SKU)) {
			out.write(item.getProduct().getManufacturerSku());
		} else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RESALE)) {
			writeResaleElement(out, false);
		} else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DELETE)) {
			writeDeleteCheckBoxElement(out);
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
        	//STJ-5309
    	} else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CUSTOMER_SKU)) {
    	          out.write(item.getProduct().getActualCustomerSkuNum());
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
		} 
		else {
			out.write(item.getProduct().getProductAttribute(def.getAttributename()));
		}

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
					out.write("<input type=\"checkbox\" name=\"selectBox\" tabindex=\"" + getCurrentDisplayIndex(2000) + "\" value=\"" + (((Integer) item.getItemId()).longValue() * 10000 + ((Integer) item.getOrderNumber()).longValue()) + "\"/  onclick=\"UncheckDeleteAll();\">");/*javascript UncheckDeleteAll is in the lib.js*/
				} else {
					out.write("<!--opt2-->");
					// works for inv cart but the bottom section
					out.write("<input type=\"checkbox\" name=\"selectBox\" tabindex=\"" + getCurrentDisplayIndex(3000) + "\" value=\"" + item.getProduct().getProductId() + "\" onclick=\"UncheckDeleteAll();\">");/*javascript UncheckDeleteAll is in the lib.js*/
				}
			}

			// commented out for user order guide
			// } else if (appUser.canMakePurchases() && viewOptionQuickOrderView
			// == false && ShopTool.isInventoryShoppingOn(request) &&
			// !modernInvShopping) {
		} else if (viewOptionOrderGuide) {
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

		if (!forced) {
			if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_RE_SALE_ITEMS)) {
				resaleItemsAllowedViewOnly = false;
			}
			if (resaleItemsAllowed && !viewOptionEditCartItems && !viewOptionQuickOrderView) {

				out.write("<td class=\"text\" align=\"center\">");
				if (resaleItemsAllowedViewOnly) {
					if (itemIsResale) {
						out.write(ClwI18nUtil.getMessage(request, "shoppingItems.text.y", null));
					} else {
						out.write(ClwI18nUtil.getMessage(request, "shoppingItems.text.n", null));
					}
				} else {
					out.write("<input type=\"checkbox\" name=\"reSaleSelectBox\" tabindex=\"" + getCurrentDisplayIndex(4000) + "\" value=\"" + item.getProduct().getProductId() + "\"/>");
				}
				out.write("</td>");

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

	public boolean isViewOptionCheckout() {
		return viewOptionCheckout;
	}

	public void setViewOptionCheckout(boolean viewOptionCheckout) {
		this.viewOptionCheckout = viewOptionCheckout;
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
	
	public String getInputNameItemId() {
		return inputNameItemId;
	}

	public void setInputNameItemId(String inputNameItemId) {
		this.inputNameItemId = inputNameItemId;
	}

	public boolean isViewOptionOrderGuide() {
		return viewOptionOrderGuide;
	}

	public void setViewOptionOrderGuide(boolean viewOptionOrderGuide) {
		this.viewOptionOrderGuide = viewOptionOrderGuide;
	}
	
	public boolean isViewOptionGroupedItemDetail() {
		return viewOptionGroupedItemDetail;
	}

	public void setViewOptionGroupedItemDetail(boolean viewOptionGroupedItemDetail) {
		this.viewOptionGroupedItemDetail = viewOptionGroupedItemDetail;
	}
	
	public boolean isPhysicalInvCart() {
		return physicalInvCart;
	}

	public void setPhysicalInvCart(boolean physicalInvCart) {
		this.physicalInvCart = physicalInvCart;
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

	public boolean isUpdateAutoDistro() {
		return updateAutoDistro;
	}

	public void setUpdateAutoDistro(boolean updateAutoDistro) {
		this.updateAutoDistro = updateAutoDistro;
	}

    public String getXhrArgs() {
        return xhrArgs;
    }

    public void setXhrArgs(String xhrArgs) {
        this.xhrArgs = xhrArgs;
    }

    /*public void setViewOptionAutoDistro(boolean viewOptionAutoDistro) {
         this.viewOptionAutoDistro = viewOptionAutoDistro;
     }*/

	public static ProductViewDefDataVector getShoppingCartViewDefinitions(){
		ProductViewDefDataVector checkoutViewDefs = new ProductViewDefDataVector();
		checkoutViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, TEST_CENTER, 10));
		checkoutViewDefs.add(getCheckoutViewDef(ProductData.UOM, null, 7));
		checkoutViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC, null, 40));
		checkoutViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU, TEST_LEFT, 10));
		//checkoutViewDefs.add(getCheckoutViewDef(ProductData.PACK, TEST_LEFT, 10));
		checkoutViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_PACK, TEST_LEFT, 10));
		checkoutViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEST_RIGHT, 12));
		checkoutViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE, TEST_RIGHT, 10));
		checkoutViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DELETE, TEST_CENTER, 15));

		return checkoutViewDefs ;

	}

	public static ProductViewDefDataVector getCheckoutViewDefinitions(){
		ProductViewDefDataVector checkoutViewDefs = new ProductViewDefDataVector();
		checkoutViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, TEST_CENTER, 10));
		checkoutViewDefs.add(getCheckoutViewDef(ProductData.UOM, null, 7));
		checkoutViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC, null, 40));
		checkoutViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU, TEST_LEFT, 10));
		//checkoutViewDefs.add(getCheckoutViewDef(ProductData.PACK, TEST_LEFT, 10));
		checkoutViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_PACK, TEST_LEFT, 10));
		checkoutViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEST_RIGHT, 12));
		checkoutViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE, TEST_RIGHT, 10));

		return checkoutViewDefs ;

	}

	public static ProductViewDefDataVector getAutoDistroViewDefinitions(){
		ProductViewDefDataVector autoDistroViewDefs = new ProductViewDefDataVector();
		autoDistroViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, TEST_CENTER, 10));
		autoDistroViewDefs.add(getCheckoutViewDef(ProductData.UOM, null, 7));
		autoDistroViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC, null, 40));
		autoDistroViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU, TEST_LEFT, 10));
		//autoDistroViewDefs.add(getCheckoutViewDef(ProductData.PACK, TEST_LEFT, 10));
		autoDistroViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_PACK, TEST_LEFT, 10));
		autoDistroViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEST_RIGHT, 12));

		return autoDistroViewDefs ;

	}

	public static ProductViewDefDataVector getOrderGuideViewDefinitions() {
		ProductViewDefDataVector myOrderGuideViewDefs = new ProductViewDefDataVector();
		myOrderGuideViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, TEST_CENTER, 10));
		myOrderGuideViewDefs.add(getCheckoutViewDef(ProductData.THUMBNAIL, TEST_CENTER, 10));
		myOrderGuideViewDefs.add(getCheckoutViewDef(ProductData.UOM, null, 7));
		myOrderGuideViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU, TEST_LEFT, 10));
		myOrderGuideViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC, null, 40));
		myOrderGuideViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEST_RIGHT, 12));
		myOrderGuideViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE, TEST_RIGHT, 10));
		myOrderGuideViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DELETE, TEST_CENTER, 15));

		return myOrderGuideViewDefs ;
	}
	
	public static ProductViewDefDataVector getDefaultViewDefinitions(){
		ProductViewDefDataVector defaultViewDefs = new ProductViewDefDataVector();
		defaultViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, null, 10));
		defaultViewDefs.add(getCheckoutViewDef(ProductData.THUMBNAIL, null, 10));
		defaultViewDefs.add(getCheckoutViewDef(ProductData.UOM, null, 10));
		defaultViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU, null, 15));
		defaultViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC, null, 30));
		defaultViewDefs.add(getCheckoutViewDef(ProductData.SIZE, null, 10));
		defaultViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, TEST_RIGHT, 10));
		defaultViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE, TEST_RIGHT, 20));
		defaultViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DELETE, TEST_CENTER, 15));
		//STJ-5309
		defaultViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CUSTOMER_SKU, null, 15));
		defaultViewDefs.add(getCheckoutViewDef(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_SKU, null, 15));

		return defaultViewDefs ;

	}

	private static ProductViewDefData getCheckoutViewDef(String pAttributename, String pStyleClass, int pWidth){
		ProductViewDefData prodViewDef = ProductViewDefData.createValue();
		prodViewDef.setAttributename(pAttributename);
		prodViewDef.setStyleClass(pStyleClass);
		prodViewDef.setWidth(pWidth);
		return prodViewDef;
	}

}
