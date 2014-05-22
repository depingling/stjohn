package com.cleanwise.view.taglibs;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Category;


import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductViewDefData;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.StringUtils;

/**
 * Displays the header suitable to display above a list of item attributes. This
 * class will utilize the account for configuration information on what exactly
 * to display. All header translation are additionally preformed. Unless setting
 * up a brand new attribute that is not an existing
 *
 * @see DisplayProductAttributesTag to render the item attributes themselves.
 * @author bstevens
 */
public class DisplayProductHeader extends DisplayProductAttributesTag {
	private static final Category log = Category.getInstance(DisplayProductHeader.class);

	private String onClickDeleteAllCheckBox;

	private boolean viewOptionInventoryList; // if we are rendering a list of
												// inventory items

	public String sortLink;   //optional.  If present will render sorting links for the product headers.
							  //will place Desc on the ends of the fields if sort was the previous action.
							  //the sortfield names are defined in this class for the known headers.  If not
	                          //present the field will not be sortable.

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
	 * Called on start of tag, initializes some local variables that the rest of
	 * the class uses. If re-use is requiered then these variable initilizations
	 * will need to be re-implemented
	 */
	public int doStartTag() throws JspException {
		request = (HttpServletRequest) pageContext.getRequest();
		session = request.getSession();
		appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

		request.setAttribute(IS_INVENTORY_LIST, new Boolean(isViewOptionInventoryList()));

		return BodyTagSupport.EVAL_BODY_BUFFERED;
	}

	/**
	 * Called on end of the tag Renders the content
	 */
	public int doEndTag() throws JspException {
		Writer out = pageContext.getOut();
		try {
			renderXpedxHeaderView(request, out);
		} catch (IOException e) {
			throw new JspException(e);
		}
		return BodyTagSupport.EVAL_PAGE;
	}


    //holds all the special header maps to their i18n message reasource names.
    // I.e. OrderQty = "shoppingItems.text.orderQty"
    private static HashMap headerNames = new HashMap();
    static {
        // headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND,
        // "shoppingItems.text.orderQty");
        headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_INVENTORY_FLAG, "shoppingItems.text.distInv");
        headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_INVENTORY_QTY, "shoppingItems.text.distInv");
        headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU, "shoppingItems.text.itemNo");
        headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC, "shoppingItems.text.productName");
        headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_NAME, "shoppingItems.text.mfg");
        headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_SKU, "shoppingItems.text.mfgSkuNum");
        headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, "shoppingItems.text.price");
        headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE, "shoppingItems.text.total");
        headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SPL, "shoppingItems.text.spl");
        headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RESALE, "shoppingItems.text.reSaleItem");
        headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MAX_ORDER_QTY, "shoppingItems.text.maxOrderQty");
        headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MAX_ORDER_QTY_ACCOUNT, "shoppingItems.text.accountMaxOrderQty");
        headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.UNLIMITED_MAX_ORDER_QTY, "shoppingItems.text.unlimitedMaxOrderQty");
        headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RESET_TO_ACCOUNT_MAX_ORDER_QTY, "shoppingItems.text.resetToAccountMaxOrderQty");
        headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RESTRICTION_DAYS, "shoppingItems.text.restrictionDays");
        headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE, "shoppingItems.text.par");
      //STJ-5309
        headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CUSTOMER_SKU, "shoppingItems.text.custSkuNum");
        headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_SKU, "shoppingItems.text.distSkuNum");

    }


	private static List sortableFields = new LinkedList();
	static {
		sortableFields.add(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU);
		sortableFields.add(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC);
		sortableFields.add(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_NAME);
		sortableFields.add(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_SKU);
		sortableFields.add(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE);
		sortableFields.add(ProductData.COLOR);
		sortableFields.add(ProductData.CUBE_SIZE);
		sortableFields.add(ProductData.NSN);
		sortableFields.add(ProductData.OTHER_DESC);
		sortableFields.add(ProductData.PACK);
		sortableFields.add(ProductData.PKG_UPC_NUM);
		sortableFields.add(ProductData.PSN);
		sortableFields.add(ProductData.SCENT);
		sortableFields.add(ProductData.SIZE);
		sortableFields.add(ProductData.UNSPSC_CD);
		sortableFields.add(ProductData.UOM);
		sortableFields.add(ProductData.UPC_NUM);
		sortableFields.add(ProductData.WEIGHT_UNIT);
	}

	/**
	 * Will map attributes to their tranlation lookup values. If this is not
	 * specifically coded it will default to "shoppingItems.text.<attribute>".
	 * Where attribute is a lowercase version of the passed in attribute. If
	 * this does not have a valid translation the attribute unmodified will be
	 * returned. I.e. Order Qty = "shoppingItems.text.orderQty"
	 *
	 * @see RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE for a list of supported
	 *      special atributes
	 * @param attribute
	 *            the attribute to translate
	 * @return the translated text suitable for display on the web site
	 */
	protected String xlateHeaderName(String attribute) {
		String val;
		if (attribute.equals(ProductData.THUMBNAIL)) {
			return "&nbsp;";
		} 
		else if (attribute.equals(ProductData.PACK)) {
			return "&nbsp;";
		} 
		else if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_PACK.equals(attribute)) {
			return "&nbsp;";
		} 
		else if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND.equals(attribute)) {
			if (isViewOptionAutoDistro()) {
				val = "shoppingItems.text.distroQty";
			}
			else if (isViewOptionInventoryList()) {
				val = "shoppingItems.text.onHandQty";
			} 
			else {
				val = "shoppingItems.text.orderQty2Lines";
			}
		}
		else if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.GREEN.equalsIgnoreCase(attribute)) {
			val = "shoppingItems.text.green_item";
		}
		else if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SYSTEM_SKU.equalsIgnoreCase(attribute)) {
			val = "shoppingItems.text.system_sku";
		}
		else {
			val = (String) headerNames.get(attribute);
		}
		if (val == null) {
			val = "shoppingItems.text." + attribute.toLowerCase();
		}
		String retVal = ClwI18nUtil.getMessage(request, val, null);
		if (retVal == null) {
			retVal = attribute;
		}
		return retVal;
	}


	/**
	 * Returns the sortField for this attribute.  If none is defined returns null.
	 * @param attributeName
	 * @return
	 */
	private String getSortFieldForAttribute(String attributeName){

		return null;
	}

	/**
	 * Writes out a header for the item attribute. This method will handle all
	 * translations based off the users locale.
	 */
         private void renderXpedxHeaderView(HttpServletRequest request, Writer out) throws IOException {
             boolean first = true;
             Iterator<ProductViewDefData> it = getProductDefinitions(true).iterator();
             while (it.hasNext()) {
                ProductViewDefData def = (ProductViewDefData) it.next();
		if (!shouldAttributeBeRendered(def.getAttributename())) {
			continue;
		}

		String style = def.getStyleClass();
		String styleLinkHeader = def.getStyleClass();
		if (!Utility.isSet(def.getStyleClass())) {
			style = "shopcharthead";
			styleLinkHeader = "shopchartheadlink";
		} else {
			style = style + "  shopcharthead";
			styleLinkHeader = style + "  shopchartheadlink";
		}
                if(first){
                     first = false;
                     style = "itemRowFirst "+ style;
                     styleLinkHeader = "itemRowFirst "+styleLinkHeader;
                }else if(!it.hasNext()){
                     style = "itemRowLast "+ style;
                     styleLinkHeader = "itemRowLast "+styleLinkHeader;
                }


		if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DELETE) && Utility.isSet(getOnClickDeleteAllCheckBox())) {
			if (isViewOptionEditCartItems() && isViewOptionInventoryList()) {
				out.write("&nbsp;");
			} else {
				/*out.write("<table><tr><td class=\""+style+"\">"+ClwI18nUtil.getMessage(request, "global.action.label.delete", null)+"</td><td class=\""+style+"\">"+ClwI18nUtil.getMessage(request, "shoppingItems.text.all", null));
				out.write("<BR>");
				out.write("<input type=\"checkbox\" name=\"deleteAllFl\" value=\"false\"  onclick=\"" + getOnClickDeleteAllCheckBox() + "\"/>");
				out.write("</td></tr></table>");*/

				out.write("<td class=\""+style+"\" width=\"" + def.getWidth()/2 + "%\"><div class=\"fivemargin\">"+ClwI18nUtil.getMessage(request, "global.action.label.delete", null)+"</div></td><td class=\""+style+"\" width=\"" + def.getWidth()/2 + "%\"><div class=\"fivemargin\">"+ClwI18nUtil.getMessage(request, "shoppingItems.text.all", null));
				out.write("<BR>");
				out.write("<input type=\"checkbox\" name=\"deleteAllFl\" value=\"false\"  onclick=\"" + getOnClickDeleteAllCheckBox() + "\" id=\"HeaderDeleteAllCheckBox\"/>");
				out.write("</div></td>");
				//out.write(xlateHeaderName(def.getAttributename()));


			}
		} else {
			out.write("<td class=\"" + style + "\" width=\"" + def.getWidth() + "%\">");
			out.write("<div class=\"fivemargin\">");

			//String sortField = getSortFieldForAttribute(def.getAttributename());
			String sortField = def.getAttributename();
			if(getSortLink() != null && sortableFields.contains(sortField)){
				//orderstatus.do?action=sort&sortField=
				out.write("<a class=\""+styleLinkHeader+"\" href=\""+getSortLink()+StringUtils.getSortField(sortField,request)+"\">");
			}
			out.write(xlateHeaderName(def.getAttributename()));
			if(getSortLink() != null && sortableFields.contains(sortField)){
				// add sort image for current sorting header
				String currentSortField = request.getParameter("sortField");
				if (currentSortField == null){
					currentSortField = "Product Short Desc";
				}

				String sortImage = null;
				if (currentSortField.equals(sortField)){
					boolean isDescending = false;
					sortImage = "sortAsc.gif";
				}else if (currentSortField.endsWith("Desc") && currentSortField.equals(sortField+"Desc")){
					sortImage = "sortDesc.gif";
				}
				if (sortImage != null)
					out.write("<img src=\"" + ClwCustomizer.getSIP(request, sortImage) + "\">");
				out.write("</a>");
			}
		}
		out.write("</div>");
		out.write("</td>");
              }
	}

	public String getOnClickDeleteAllCheckBox() {
		return onClickDeleteAllCheckBox;
	}

	public void setOnClickDeleteAllCheckBox(String onClickDeleteAllCheckBox) {
		this.onClickDeleteAllCheckBox = onClickDeleteAllCheckBox;
	}

	public String getSortLink() {
		return sortLink;
	}

	public void setSortLink(String sortLink) {
		this.sortLink = sortLink;
	}
}
