package com.espendwise.view.taglibs.esw;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductViewDefData;
import com.cleanwise.service.api.value.ProductViewDefDataVector;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.taglibs.DisplayProductAttributesTag;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;

/**
 * Displays the header suitable to display above a list of item attributes. This
 * class will utilize the account for configuration information on what exactly
 * to display. All header translation are additionally preformed. Unless setting
 * up a brand new attribute that is not an existing
 *
 * @see DisplayProductAttributesTag to render the item attributes themselves.
 * @author bstevens
 */
public class DisplayProductCatalogItemsHeaderTag extends DisplayProductCatalogItemsTag {
	private static final Logger log = Logger.getLogger(DisplayProductCatalogItemsHeaderTag.class);

        private String onClickDeleteAllCheckBox;
        public String sortLink;   //optional.  If present will render sorting links for the product headers.
                                                          //will place Desc on the ends of the fields if sort was the previous action.
                                                          //the sortfield names are defined in this class for the known headers.  If not
                                  //present the field will not be sortable.
        private boolean productCatalog = true;
        private int _index;
        public int getIndex() {
			return _index;
		}

		public void setIndex(int index) {
			this._index = index;
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
                printBoolean();                
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


        //holds all the special header maps to their i18n message resource names.
        // I.e. OrderQty = "shoppingItems.text.orderQty"
        private static HashMap<String, String> headerNames = new HashMap<String, String>();
        static {
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE, "shoppingItems.text.ia");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE, "shoppingItems.text.par");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND, "shoppingItems.text.orderQty");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY, "newUI.shoppingItems.text.orderQty");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_INVENTORY_FLAG, "shoppingItems.text.distInv");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_INVENTORY_QTY, "shoppingItems.text.distInv");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU, "shoppingItems.text.sku#");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_SKU, "shoppingItems.text.distSkuNum");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CUSTOMER_SKU, "shoppingItems.text.custSkuNum");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SYSTEM_SKU, "shoppingItems.text.systemSkuNum");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC, "shoppingItems.text.productName");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_NAME, "shoppingItems.text.manufacturer");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_SKU, "shoppingItems.text.mfgSkuNum");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE, "shoppingItems.text.price");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE, "shoppingItems.text.subTotal");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.EXTENDED_PRICE, "shoppingItems.text.extendedPrice");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SPL, "shoppingItems.text.spl");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MAX_ORDER_QTY, "shoppingItems.text.maxOrderQty");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SELECT, "shoppingItems.text.selectAll");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_PACK, "shoppingItems.text.pack");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PACK, "shoppingItems.text.pack");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MAX_ORDER_QTY_ACCOUNT, "shoppingItems.text.accountMaxOrderQty");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.UNLIMITED_MAX_ORDER_QTY, "shoppingItems.text.unlimitedMaxOrderQty");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RESET_TO_ACCOUNT_MAX_ORDER_QTY, "shoppingItems.text.resetToAccountMaxOrderQty");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RESTRICTION_DAYS, "shoppingItems.text.restrictionDays");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.EMPTY,"");
                headerNames.put(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RE_SALE_ITEM, "shoppingItems.text.reSale");
        }


        private static List<String> sortableFields = new LinkedList<String>();
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
         * Will map attributes to their translation lookup values. If this is not
         * specifically coded it will default to "shoppingItems.text.<attribute>".
         * Where attribute is a lower case version of the passed in attribute. If
         * this does not have a valid translation the attribute unmodified will be
         * returned. I.e. Order Qty = "shoppingItems.text.orderQty"
         *
         * @see RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE for a list of supported
         *      special attributes
         * @param attributeSELECT
         *            the attribute to translate
         * @return the translated text suitable for display on the web site
         */
        protected String xlateHeaderName(String attribute) {
                String val = null;
                if (attribute.equals(ProductData.THUMBNAIL)) {
                        return "&nbsp;";
                } else if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.QTY_ON_HAND_COND.equals(attribute)) {
                        if (isViewOptionAutoDistro()) {
                                val = "shoppingItems.text.distroQty";
                        }else if ((isViewOptionShoppingCart() && ShopTool.isInventoryShoppingOn(request)) ||
                                    isViewOptionQuickOrderView() || isViewOptionOrderStatus())  {
                                val = "shoppingItems.text.onHand";
                        } else if (isViewOptionModInventory()) {
                                val = "shop.og.table.header.orderInvQty";
                        } else {
                                val = "shoppingItems.text.orderQty";
                        }
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.IA)) {
                            return "&nbsp;";
                } else if(attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.EMPTY)) {
                	return "&nbsp;";
                }
                else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY)) {
                    if (isViewOptionModInventory()) {
                        val = "shop.og.table.header.orderQtyToAdd";
                    } else {
                        val = (String) headerNames.get(attribute);
                    }
                } else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_NAME)) {
                    val = (String) headerNames.get(attribute);
                } /*else if (attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.GREEN)) {
                            return "&nbsp;";
                }*/ else {
                	if(attribute.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.LINE_PRICE) && isViewOptionShoppingCart()){  // STJ - 4419
                		val = "shoppingItems.text.subTotal";
                	}
                	else{
                        val = (String) headerNames.get(attribute);
                	}
                }
                if (val == null) {
                        val = "shoppingItems.text." + attribute.toLowerCase();
                }
                String retVal = ClwI18nUtil.getMessage(request, val, null, true);
                //Make sure, sub total column is displayed if it is configured.
                if(retVal == null && "shoppingItems.text.subTotal".equals(val)) {
                	retVal = Constants.COLUMN_SUB_TOTAL;
                } else if (retVal == null) {
                        retVal = attribute;
                }
                return retVal;
        }

        /**
         * Writes out a header for the item attribute. This method will handle all
         * translations based off the users locale.
         */
         private void renderHeaderView(HttpServletRequest request, Writer out) throws IOException {
        	 
        	 ProductViewDefDataVector columnDefs = getProductDefinitions(true);
             
             //Build Column group
             StringBuilder colGroupBuilder = new StringBuilder();
             colGroupBuilder.append("<colgroup>");
             
             //Build header
             StringBuilder headerBuilder = new StringBuilder();
             headerBuilder.append("<thead>");
             headerBuilder.append("<tr>");
             
             boolean isOrderTotalColumnFound = false;
             String orderTotalColumnStyle = null;
             Double dTotalWidth = calculateTotalColumnWidth(columnDefs);
             Iterator<ProductViewDefData> columns = columnDefs.iterator();
             while (columns.hasNext()) {
                ProductViewDefData def = (ProductViewDefData) columns.next();
                if (shouldAttributeBeRendered(def.getAttributename())) {
                    String style = determineColumnStyle(def, dTotalWidth, TEXT_CENTER);
                	 if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC)) {
                     	colGroupBuilder.append("<col>");
                     	long percentage = Math.round((ECOFRIENDLY_COLUMN_WIDTH/dTotalWidth) * 100);
                     	headerBuilder.append("<th style=\"width:" + percentage + "%;\" class=\"ecoFriendly\">  </th>");
                 	}  
                	if (!def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.GREEN) && 
                			!def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SELECT)&&
                			!def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATEGORY)) {
                		colGroupBuilder.append("<col class="+def.getAttributename()+">");
                 		
                 		//STJ-5802:  Order Qty column should be rendered before remove check-box for Shopping list only
                 		if (def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY) && isUserShoppingList()) {
                 			isOrderTotalColumnFound = true;
             				orderTotalColumnStyle = style;
                 		} else {
                 			String headerName = xlateHeaderName(def.getAttributename());
                 			//Some times if def.getAttributename()=="Qty or On Hand Box Conditional", there is a chance of returning 
                 			//Order Qty column value.
                 			if(isUserShoppingList() && Utility.isSet(headerName) &&  headerName.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY)) {
                 				isOrderTotalColumnFound = true;
                 				orderTotalColumnStyle = style;
                 			} else {
                 				headerBuilder.append("<th" + style + ">");
                     			headerBuilder.append(headerName);
                     			if(!def.getAttributename().equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.RE_SALE_ITEM) || getIndex() > 0) {
                         			headerBuilder.append("</th>");
                     			}
                 			}
                 			
                 		}
                 	}
                 	
                }
             }
             if(isViewOptionEditOrderGuide() && isUserShoppingList()){
            	//STJ-5802:  Move Qty box from 1st column to column before the Remove / Checkbox column 
            	if(isOrderTotalColumnFound) {
            		if (!Utility.isSet(orderTotalColumnStyle)) {
            			orderTotalColumnStyle = Constants.EMPTY;
            		}
            		headerBuilder.append("<th" + orderTotalColumnStyle + ">");
            		String value = "";
            		if (isViewOptionModInventory()) {
            			value = "shop.og.table.header.orderQtyToAdd";
            		} else {
            			value = (String) headerNames.get(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY);
            		}
            		if (value == null) {
            			value = "shoppingItems.text." + RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ORDER_QTY.toLowerCase();
            		}
            		String retVal = ClwI18nUtil.getMessage(request, value, null, true);
            		
               		headerBuilder.append(retVal);
               		headerBuilder.append("</th>");
            	}
           		
            	headerBuilder.append("<th>");
          		headerBuilder.append("&nbsp;");
          		headerBuilder.append("</th>");
             }
             colGroupBuilder.append("</colgroup>");
             headerBuilder.append("</tr>");
             headerBuilder.append("</thead>");
             
             out.write(colGroupBuilder.toString());
             out.write(headerBuilder.toString());
                
        }
        
         /**
     	 * @return the onClickDeleteAllCheckBox
     	 */
        public String getOnClickDeleteAllCheckBox() {
                return onClickDeleteAllCheckBox;
        }
        
        /**
    	 * @param onClickDeleteAllCheckBox the onClickDeleteAllCheckBox to set
    	 */
        public void setOnClickDeleteAllCheckBox(String onClickDeleteAllCheckBox) {
                this.onClickDeleteAllCheckBox = onClickDeleteAllCheckBox;
        }

        /**
     	 * @return the sortLink
     	 */
        public String getSortLink() {
                return sortLink;
        }

        /**
    	 * @param sortLink the sortLink to set
    	 */
        public void setSortLink(String sortLink) {
                this.sortLink = sortLink;
        }

		/**
		 * @return the productCatalog
		 */
		public boolean isProductCatalog() {
			return productCatalog;
		}

		/**
		 * @param pProductCatalog the productCatalog to set
		 */
		public void setProductCatalog(boolean pProductCatalog) {
			productCatalog = pProductCatalog;
		}
        
        
}
