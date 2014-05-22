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
import com.cleanwise.view.utils.*;

/**
 * Displays the header suitable to display above a list of item attributes. This
 * class will utilize the account for configuration information on what exactly
 * to display. All header translation are additionally preformed. Unless setting
 * up a brand new attribute that is not an existing
 *
 * @see DisplayProductAttributesTag to render the item attributes themselves.
 * @author bstevens
 */
public class CommonDisplayProductHeader extends CommonDisplayProductAttributesTag {
        private static final Category log = Category.getInstance(CommonDisplayProductHeader.class);

        private String onClickDeleteAllCheckBox;

                                                                                                // inventory items

        public String sortLink;   //optional.  If present will render sorting links for the product headers.
                                                          //will place Desc on the ends of the fields if sort was the previous action.
                                                          //the sortfield names are defined in this class for the known headers.  If not
                                  //present the field will not be sortable.



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
//////////////////////////////////
printBoolean();                
//////////////////////////////////                

                return BodyTagSupport.EVAL_BODY_BUFFERED;
        }

        /**
         * Called on end of the tag Renders the content
         */
        public int doEndTag() throws JspException {
                Writer out = pageContext.getOut();
                try {
                        renderHeaderView(request, out);
                } catch (IOException e) {
                        throw new JspException(e);
                }
                return BodyTagSupport.EVAL_PAGE;
        }


        //holds all the special header maps to their i18n message reasource names.
        // I.e. OrderQty = "shoppingItems.text.orderQty"
        private static HashMap headerNames = new HashMap();
        static {
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE, "shoppingItems.text.ia");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE, "shoppingItems.text.par");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, "shoppingItems.text.orderQty");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY, "shoppingItems.text.orderQty");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_INVENTORY_FLAG, "shoppingItems.text.distInv");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_INVENTORY_QTY, "shoppingItems.text.distInv");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU, "shoppingItems.text.ourSkuNum");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_SKU, "shoppingItems.text.distSkuNum");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CUSTOMER_SKU, "shoppingItems.text.custSkuNum");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SYSTEM_SKU, "shoppingItems.text.systemSkuNum");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC, "shoppingItems.text.productName");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_NAME, "shoppingItems.text.manufacturer");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_SKU, "shoppingItems.text.mfgSkuNum");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, "shoppingItems.text.price");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE, "shoppingItems.text.amount");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.EXTENDED_PRICE, "shoppingItems.text.extendedPrice");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SPL, "shoppingItems.text.spl");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RESALE, "shoppingItems.text.reSaleItem");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MAX_ORDER_QTY, "shoppingItems.text.maxOrderQty");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SELECT, "shoppingItems.text.selectAll");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_PACK, "shoppingItems.text.pack");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PACK, "shoppingItems.text.pack");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MAX_ORDER_QTY_ACCOUNT, "shoppingItems.text.accountMaxOrderQty");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.UNLIMITED_MAX_ORDER_QTY, "shoppingItems.text.unlimitedMaxOrderQty");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RESET_TO_ACCOUNT_MAX_ORDER_QTY, "shoppingItems.text.resetToAccountMaxOrderQty");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RESTRICTION_DAYS, "shoppingItems.text.restrictionDays");
                //headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.COLOR, "shoppingItems.text.color");
                // headerNames.put("","");

        }


        private static List sortableFields = new LinkedList();
        static {
                sortableFields.add(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.IA);
                sortableFields.add(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE);
                sortableFields.add(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND);
                sortableFields.add(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY);
                sortableFields.add(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU);
                sortableFields.add(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_SKU);
                sortableFields.add(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CUSTOMER_SKU);
                sortableFields.add(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SYSTEM_SKU);
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
                sortableFields.add(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SELECT);
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
         * @param attributeSELECT
         *            the attribute to translate
         * @return the translated text suitable for display on the web site
         */
        protected String xlateHeaderName(String attribute) {
                String val = null;
                if (attribute.equals(ProductData.THUMBNAIL)) {
                        return "&nbsp;";
                /*} else if (attribute.equals(ProductData.PACK)) {
                        return "&nbsp;"; xpedx specific */
                //} else if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_PACK.equals(attribute)) {
                //        return "&nbsp;";
                } else if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND.equals(attribute)) {
                        if (isViewOptionAutoDistro()) {
                                val = "shoppingItems.text.distroQty";
                        }else if ((isViewOptionShoppingCart() && ShopTool.isInventoryShoppingOn(request)) ||
                                    isViewOptionQuickOrderView() || isViewOptionCheckout() || isViewOptionOrderStatus())  {
                                val = "shoppingItems.text.onHand";
                        } else if (isViewOptionModInventory()) {
                                val = "shop.og.table.header.orderInvQty";
                        } else {
                                val = "shoppingItems.text.orderQty";
                        }
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.IA)) {
                            return "&nbsp;";
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY)) {
                    if (isViewOptionModInventory()) {
                        val = "shop.og.table.header.orderQtyToAdd";
                    } else {
                        val = (String) headerNames.get(attribute);
                    }
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_NAME)) {
                      val = (String) headerNames.get(attribute);
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.GREEN)) {
                            return "&nbsp;";
                } else {
                        val = (String) headerNames.get(attribute);
                }
                if (val == null) {
                        val = "shoppingItems.text." + attribute.toLowerCase();
                }
                String retVal = ClwI18nUtil.getMessage(request, val, null, true);
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
         private void renderHeaderView(HttpServletRequest request, Writer out) throws IOException {
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

                                out.write("<td class=\""+style+"\" width=\"" + def.getWidth()/2 + "\"><div class=\"fivemargin\">"+ClwI18nUtil.getMessage(request, "global.action.label.delete", null)+"</div></td><td class=\""+style+"\" width=\"" + def.getWidth()/2 + "\"><div class=\"fivemargin\">"+ClwI18nUtil.getMessage(request, "shoppingItems.text.all", null));
                                out.write("<BR>");
                                out.write("<input type=\"checkbox\" name=\"deleteAllFl\" value=\"false\"  onclick=\"" + getOnClickDeleteAllCheckBox() + "\" id=\"HeaderDeleteAllCheckBox\"/>");
                                out.write("</div></td>");
                                //out.write(xlateHeaderName(def.getAttributename()));


                        }
                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RESALE)) {
                     boolean resaleItemsAllowed = ShopTool.isResaleItemsAllowed(request);
                     if (resaleItemsAllowed && !isViewOptionEditCartItems() && !isViewOptionQuickOrderView()) {
                         out.write("<td class=\"" + style + "\" width=\"" + def.getWidth()/2 + "\">");
                         out.write("<!--"+def.getAttributename()+"-->");
                         out.write("<font color=\"red\">" + ClwI18nUtil.getMessage(request, "shoppingItems.text.reSaleItem", null) + "</font>");
                         if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_RE_SALE_ITEMS) && !isViewOptionConfirmCheckout())  {
                             out.write("<br><a href=\"javascript:f_SetChecked(1,'reSaleSelectBox')\">" +
                                     ClwI18nUtil.getMessage(request, "shoppingItems.text.checkAll", null) +
                                     "</a><a href=\"javascript:f_SetChecked(0,'reSaleSelectBox')\">" +
                                     ClwI18nUtil.getMessage(request, "shoppingItems.text.clear", null) +
                                     "</a>");
                         }
                         out.write("</td>");
                     }

                } else if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SELECT)) {
                    if (isViewOptionEditCartItems() || isViewOptionEditOrderGuide()) {
                        out.write("<td class=\"" + style + "\" width=\"" + def.getWidth()/2 + "\">");
                        out.write("<!--"+def.getAttributename()+"-->");
                        out.write(ClwI18nUtil.getMessage(request, "global.action.label.select", null));
                        out.write("</td>");
                    } else if (ShopTool.isInventoryShoppingOn(request) && !isViewOptionQuickOrderView()) {
                        out.write("<td class=\"" + style + "\"");
                        if (def.getWidth() > 0) {
                            out.write("width=\"" + def.getWidth() + "\"");
                        }
                        out.write(">");
                        out.write("<!--"+def.getAttributename()+"-->");
                        out.write("<a href=\"javascript:f_SetChecked(1,'orderSelectBox')\">");
                        out.write(ClwI18nUtil.getMessage(request, "shoppingItems.text.checkAll", null));
                        out.write("</a><br>");
                        out.write("<a href=\"javascript:f_SetChecked(0,'orderSelectBox')\">");
                        out.write(ClwI18nUtil.getMessage(request, "shoppingItems.text.clear", null));
                        out.write("</a>");
                        out.write("</td>");
                    } else {
                      out.write("<td class=\"" + style + "\">&nbsp;</td>");
                    }
                } else {
                        out.write("<td class=\"" + style + "\"");
                        if (def.getWidth() > 0) {
                            out.write("width=\"" + def.getWidth() + "\"");
                        }
                        out.write(">");
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
