package com.cleanwise.view.taglibs;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.CatalogCategoryData;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductViewDefData;
import com.cleanwise.service.api.value.ProductViewDefDataVector;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;

public class DisplayProductB2CTag extends DisplayProductHeader {
	// static variable where we can temporarily store the item
	private static String ITEM_NAME = DisplayProductB2CTag.class.getName()
			+ "ITEM_NAME";

	private int B2CElementsPerRow;
	private String listOfItems;

	/**
	 * Called on end of the tag Renders the content
	 */
	public int doEndTag() throws JspException {

		setViewOptionAddToCartList(true);
		setViewOptionEditCartItems(false);
		setViewOptionInventoryList(false);
		setViewOptionOrderGuide(false);
		setViewOptionQuickOrderView(false);
		Writer out = pageContext.getOut();
		try {
			renderB2CTableItemView(out);
		} catch (IOException e) {
			throw new JspException(e);
		}

		return BodyTagSupport.EVAL_PAGE;

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
		if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND
				.equals(attribute)) {
			String val = "shoppingItems.text.QTY";
			return ClwI18nUtil.getMessage(request, val, null);
		}
		if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE.equals(attribute)) {
			return null;
		}
		if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU
				.equals(attribute)) {
			String val = "shoppingItems.text.item#";
			return ClwI18nUtil.getMessage(request, val, null);
		}
		return super.xlateHeaderName(attribute);
	}

	/**
	 * Renders the B2C view. This is a table view Image name Attribute: Value
	 * Attribute: Value ...
	 * 
	 * @param out
	 * @param def
	 * @throws IOException
	 */
	protected void renderB2CTableItemView(Writer out) throws IOException {

		Iterator it = getProductDefinitions().iterator();
		while (it.hasNext()) {
			writeB2CTable(out, (ProductViewDefData) it.next());
		}
		// write out a couple elements that always show, or are not controlled
		// by the dynamic list
		writeResaleElement(out, true);
	}

	/**
	 * Determines if this attribute should be rendered. Mostly relies on the
	 * super implementation
	 */
	protected boolean shouldAttributeBeRendered(String attribute) {
		if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DELETE
				.equals(attribute)) {
			// never render the delete check box
			return false;
		}
		if (ProductData.UOM.equals(attribute)) {
			// The uom is rendered after the qty box
			return false;
		}
		return super.shouldAttributeBeRendered(attribute);
	}

	/**
	 * Determines if forecasted items can be ordered during scheduled cart
	 * period
	 * 
	 * @return true if the discretionary cart is avaliable
	 */
	private boolean allowOrderInventoryItems() {
		if (ShopTool.isModernInventoryShopping(request)
				&& !ShopTool.hasDiscretionaryCartAccessOpen(request)
				&& session.getAttribute(Constants.INVENTORY_SHOPPING_CART) != null) {

			if (appUser.getUserAccount().getPropertyValue(
					RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_INV_ITEMS)
					.equals("false")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Does the heavy lifting of writing out the items
	 * 
	 * @param out
	 * @param def
	 * @throws IOException
	 */
	private void writeB2CTable(Writer out, ProductViewDefData def)
			throws IOException {
		if (!shouldAttributeBeRendered(def.getAttributename())) {
			return;
		}

		List listItems = (List) pageContext.getAttribute(getListOfItems());
		out.write("<tr>");

		for (int x = 0; x < getBtwoCElementsPerRow(); x++) {
			if (!(x < listItems.size())) {
				out.write("<td valign=\"top\"></td>");
				return;
			}
			ShoppingCartItemData item = ((ShoppingCartItemData) listItems
					.get(x));
			ProductData product = item.getProduct();
			String itemLink = "shop.do?action="
					+ (!product.isItemGroup() ? "msItem&source=t_itemDetail.jsp"
							: "itemgroup&source=t_itemGroupDetail.jsp")
					+ "&itemId=" + product.getProductId() + "&qty="
					+ item.getQuantity();

			// setup some attributes for our parent tag so we can use its
			// methods
			this.setLink(itemLink);
			pageContext.setAttribute(ITEM_NAME, item);
			this.setName(ITEM_NAME);

			if (def.getAttributename().equals(ProductData.THUMBNAIL)) { // images
				out.write("<td class=\"" + getDefaultStyle()
						+ "\" align=\"center\" valign=\"top\" width=\"25%\">");
				out
						.write("<table width=\"100%\" border=0 cellspacing=0 cellpadding=0>");
				out.write("<tr>");
				if (Utility.strNN(product.getImage()).length() > 0) {
					out
							.write("<td class=\""
									+ getDefaultStyle()
									+ "\" align=\"center\"><a class=\"aimg\" href=\""
									+ itemLink
									+ "\"><img width=\"140\" height=\"140\"");
					out.write("src=\"/" + getStoreDir() + "/"
							+ product.getImage() + "\"");
					out.write("border=\"0\"/></a></td>");
				} else {
					out
							.write("<td class=\""
									+ getDefaultStyle()
									+ "\" align=\"center\"><a class=\"aimg\" href=\""
									+ itemLink
									+ "\"><img width=\"140\" height=\"140\"");
					out.write("src=\"/" + getStoreDir()
							+ "/en/images/noManXpedxImg.gif\"");
					out.write("border=\"0\"/></a></td>");
				}
				out.write("</tr>");
				out.write("</table>");
				out.write("</td>");

			} else if (def
					.getAttributename()
					.equals(
							RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC)) {
				if (product.isItemGroup()) {
					out.write("<td class=\"" + getDefaultStyle()
							+ "\" rowspan=\""
							+ (getProductDefinitionsSize() - 1)
							+ "\" valign=\"top\">");
					out.write("<table width=\"100%\" height=\"100%\">");
					out.write("<tr>");
					out.write("<td class=\"" + getDefaultStyle()
							+ "\" align=\"center\" >");
					out.write("<div class=\"itemGroupShortInfoB2C\">");
					out
							.write("<b class=rtopborder style=\"background-color:#fff\">\n"
									+ "    <b class=r1border style=\"background-color:#b5b5b5\"></b>\n"
									+ "    <b class=r2border style=\"background-color:#ECECF6\"></b>\n"
									+ "    <b class=r3border style=\"background-color:#ECECF6\"></b>\n"
									+ "    <b class=r4border style=\"background-color:#ECECF6\"></b>\n"
									+ "</b>");
					out
							.write("<div class=\"borderContent\" style=\"height:110px;border-left: 1px solid #B5B5B5;border-right:1px solid #B5B5B5\">");
					out.write("<table height=\"110\">");
					out.write("<tr>");
					out.write("<td class=\"" + getDefaultStyle()
							+ "\" colspan=\"3\">");
					this.writeElement(out, def);
					out.write("</td>");
					out.write("</tr>");
					out.write("<tr>");
					out.write("<td class=\"" + getDefaultStyle()
							+ "\" width=\"1%\"></td>");
					out.write("<td class=\"" + getDefaultStyle() + "\" >");
					out.write(ClwI18nUtil.getMessage(request,
							"shoppingItems.text.itemGroupText", null));
					out.write("<br><br></td>");
					out.write("<td class=\"" + getDefaultStyle()
							+ "\" width=\"1%\"></td>");
					out.write("</tr>");

					out.write("<tr>");
					out.write("<td class=\"" + getDefaultStyle()
							+ "\" colspan=\"3\">");
					out
							.write("<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
					out.write("<tr>");
					out
							.write("<td class=\""
									+ getDefaultStyle()
									+ "\" align=\"right\" valign=\"middle\"><img src='");
					out.write(ClwCustomizer.getSIP(request, "buttonLeft.png")
							+ "'border=\"0\"></td>");
					out
							.write("<td align=\"center\" valign=\"middle\" nowrap class=\"xpdexGradientButton\">");
					out.write("<a class=\"xpdexGradientButton\" href="
							+ itemLink + "\">");
					out.write(ClwI18nUtil.getMessage(request,
							"global.label.vewAllItems", null));
					out.write("</a>");
					out.write("</td>");
					out.write("<td class=\"" + getDefaultStyle()
							+ "\" align=\"left\" valign=\"middle\"><img src='");
					out.write(ClwCustomizer.getSIP(request, "buttonRight.png")
							+ "' border=\"0\"></td>");
					out.write("</tr>");
					out.write("</table></td>");
					out.write("</tr>");
					out.write("</table>");
					out.write("</div>");
					out
							.write("<b class=rbottomborder style=\"background-color:#fff\">\n"
									+ "    <b class=r4border style=\"background-color:#ECECF6\"></b>\n"
									+ "    <b class=r3border style=\"background-color:#ECECF6\"></b>\n"
									+ "    <b class=r2border style=\"background-color:#ECECF6\"></b>\n"
									+ "    <b class=r1border style=\"background-color:#b5b5b5\"></b>\n"
									+ "</b>");
					out.write("</div>");
					out.write("</td>");
					out.write("</tr>");
					out.write("</table>");
					out.write("</td>");

				} else { // item is not an item group
					out
							.write("<td class=\"itemRowB2CProductName\" valign=\"top\">");
					out.write("<table>");
					out.write("<tr>");
					out.write("<td class=\"" + getDefaultStyle()
							+ "\" style=\"font-size:12px;\">");
					out.write("<b>");
					out.write(product.getCatalogProductShortDesc());
					/*
					 * if (product.getCatalogCategories() != null) {
					 * 
					 * String categoryPathStr = ""; for (int cci =
					 * product.getCatalogCategories().size() - 1; cci >= 0;
					 * cci--) { categoryPathStr += " " + ((CatalogCategoryData)
					 * product
					 * .getCatalogCategories().get(cci)).getCatalogCategoryShortDesc
					 * (); out.write(categoryPathStr); }
					 */

					out.write("</b>");
					out.write("</td>");
					out.write("</tr>");
					out.write("</table>");
					out.write("</td>");
					// }
				}
			} else if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MAX_ORDER_QTY
					.equals(def.getAttributename())) {
				if (!product.isItemGroup()) {
					out.write("<td class=\"" + getDefaultStyle()
							+ "\" valign=\"top\">");
					if (item.getMaxOrderQty() >= 0) {
						String header = xlateHeaderName(def.getAttributename());
						out.write(header); // header
						out.write(":");
						writeElement(out, def);
					}
					out.write("</td>");
				} else {
				}

			} else if (def
					.getAttributename()
					.equals(
							RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND)) {
				if (!product.isItemGroup()) {
					if(!ShopTool.isPhysicalInventoryCart(request)){
						if (!item.getIsaInventoryItem()) {
							out.write("<td class=\"" + getDefaultStyle()
									+ "\" valign=\"top\">");
							String inputQty = "quantityElement["
									+ (getIndex() - (listItems.size() - 1) + x)
									+ "]";
							String itemIds = "itemIdsElement["
									+ (getIndex() - (listItems.size() - 1) + x)
									+ "]";
							String header = xlateHeaderName(def
									.getAttributename());
							out.write(header);
							out.write("&nbsp;");
							out.write("<input id=\"IDX_" + getIndex()
									+ "\" size=\"3\" value=\""
									+ item.getQuantityString()
									+ "\" type=\"text\" name=\"" + inputQty
									+ "\">");
							out.write("<input type=\"hidden\" name=\""
									+ itemIds + "\" value=\""
									+ item.getProduct().getProductId() + "\">");
							out.write("&nbsp;" + item.getProduct().getUom());
						} else {
							if (item.getIsaInventoryItem()
									&& allowOrderInventoryItems()) {
								out.write("<td class=\"" + getDefaultStyle()
										+ "\" valign=\"top\">");
								String inputQty = "quantityElement["
										+ (getIndex() - (listItems.size() - 1) + x)
										+ "]";
								String itemIds = "itemIdsElement["
										+ (getIndex() - (listItems.size() - 1) + x)
										+ "]";
								String header = xlateHeaderName(def
										.getAttributename());
								out.write(header);
								out.write("&nbsp;");
								out.write("<input id=\"IDX_" + getIndex()
										+ "\" size=\"3\" value=\""
										+ item.getQuantityString()
										+ "\" type=\"text\" name=\"" + inputQty
										+ "\">");
								out.write("<input type=\"hidden\" name=\""
										+ itemIds + "\" value=\""
										+ item.getProduct().getProductId()
										+ "\">");
								out
										.write("&nbsp;"
												+ item.getProduct().getUom());
							} else {
								out.write("<td></td>");
							}
						}
					} 
				}
			} else {
				if (!product.isItemGroup()) {
					out.write("<td class=\"" + getDefaultStyle()
							+ "\" valign=\"top\">");
					String header = xlateHeaderName(def.getAttributename());
					if (Utility.isSet(header) && !"&nbsp;".equals(header)) {
						out.write(header); // header
						out.write(":&nbsp;");
					}
					writeElement(out, def);
					out.write("</td>");
				} else {
				}
			}

			out.write("\n");
		} // for loop
		out.write("</tr>");
	}

	public ProductViewDefDataVector getProductDefinitions() {
		ProductViewDefDataVector defs = super.getProductDefinitions();
		Collections.sort(defs, PRODUCT_DEF_SORT_B2C);
		return defs;
	}

	/**
	 * Returns the default style to use. This is hardcoded as "itemRow"
	 */
	protected String getDefaultStyle() {
		return "itemRowB2C";
	}

	/**
	 * Custom comparator
	 */
	private static final Comparator PRODUCT_DEF_SORT_B2C = new Comparator() {
		public int compare(Object o1, Object o2) {
			ProductViewDefData pd1 = (ProductViewDefData) o1;
			ProductViewDefData pd2 = (ProductViewDefData) o2;
			// first always is thumbnail
			if (ProductData.THUMBNAIL.equals(pd1.getAttributename())) {
				return -1;
			}
			if (ProductData.THUMBNAIL.equals(pd2.getAttributename())) {
				return 1;
			}
			// second is always the product name
			if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC
					.equals(pd1.getAttributename())) {
				return -1;
			}
			if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC
					.equals(pd2.getAttributename())) {
				return 1;
			}
			// qty goes last
			if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND
					.equals(pd1.getAttributename())) {
				return 1;
			}
			if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND
					.equals(pd2.getAttributename())) {
				return -1;
			}

			int id1 = pd1.getSortOrder();
			int id2 = pd2.getSortOrder();
			return id1 - id2;
		}
	};

	public int getBtwoCElementsPerRow() {
		return B2CElementsPerRow;
	}

	public void setBtwoCElementsPerRow(int elementsPerRow) {
		B2CElementsPerRow = elementsPerRow;
	}

	public String getListOfItems() {
		return listOfItems;
	}

	public void setListOfItems(String listOfItems) {
		this.listOfItems = listOfItems;
	}
}
