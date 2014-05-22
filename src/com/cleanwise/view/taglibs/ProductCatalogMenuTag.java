/**
 * Title: ProductCatalogMenuTag
 * Description: This is a tag designed to render product catalog menu items.
 */

package com.cleanwise.view.taglibs;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.ItemAssocData;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.MenuItemView;
import com.cleanwise.service.api.value.MenuItemViewVector;
import com.cleanwise.service.api.value.ProductDataVector;

public class ProductCatalogMenuTag extends TagSupport{

	private static final long serialVersionUID = 2794610299348345700L;
	
	private MenuItemView _menuItems;
	Map<Integer,ProductDataVector> _categoryToItemMap;
	private String _parentCssStyle;
	private String _noLinkCssStyle;
	private String _rootUrl;
	private String _rootText;
	private String _menuItemParameterName;
	
    /**
	 * @return the menuItems
	 */
	public MenuItemView getMenuItems() {
		return _menuItems;
	}
	
	/**
	 * @param menuItems the menuItems to set
	 */
	public void setMenuItems(MenuItemView menuItems) {
		_menuItems = menuItems;
	}
	
	/**
	 * @return the categoryToItemMap
	 */
	public Map<Integer, ProductDataVector> getCategoryToItemMap() {
		if (_categoryToItemMap == null) {
			_categoryToItemMap = new HashMap<Integer, ProductDataVector>();
		}
		return _categoryToItemMap;
	}

	/**
	 * @param categoryToItemMap the categoryToItemMap to set
	 */
	public void setCategoryToItemMap(Map<Integer, ProductDataVector> categoryToItemMap) {
		_categoryToItemMap = categoryToItemMap;
	}

	/**
	 * @return the parentCssStyle
	 */
	public String getParentCssStyle() {
		return _parentCssStyle;
	}
	
	/**
	 * @param parentCssStyle the parentCssStyle to set
	 */
	public void setParentCssStyle(String parentCssStyle) {
		_parentCssStyle = parentCssStyle;
	}
	
	/**
	 * @return the noLinkCssStyle
	 */
	public String getNoLinkCssStyle() {
		return _noLinkCssStyle;
	}

	/**
	 * @param noLinkCssStyle the noLinkCssStyle to set
	 */
	public void setNoLinkCssStyle(String noLinkCssStyle) {
		_noLinkCssStyle = noLinkCssStyle;
	}

	/**
	 * @return the rootUrl
	 */
	public String getRootUrl() {
		return _rootUrl;
	}
	
	/**
	 * @param rootUrl the rootUrl to set
	 */
	public void setRootUrl(String rootUrl) {
		_rootUrl = rootUrl;
	}
	
	/**
	 * @return the rootText
	 */
	public String getRootText() {
		return _rootText;
	}
	
	/**
	 * @param rootText the rootText to set
	 */
	public void setRootText(String rootText) {
		_rootText = rootText;
	}
	
	/**
	 * @return the menuItemParameterName
	 */
	public String getMenuItemParameterName() {
		return _menuItemParameterName;
	}
	
	/**
	 * @param menuItemParameterName the menuItemParameterName to set
	 */
	public void setMenuItemParameterName(String menuItemParameterName) {
		_menuItemParameterName = menuItemParameterName;
	}
	
	/**
     * Render the menu items.
     */
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
	        out.print("<li");
	        if (Utility.isSet(getParentCssStyle())) {
	        	out.print(" class=\"");
	        	out.print(getParentCssStyle());
	        	out.print("\"");
	        }
	        out.println(">");
	        if (Utility.isSet(getRootUrl()) && Utility.isSet(getRootText())) {
	        	out.print("<a href=\"");
	        	out.print(getRootUrl());
	        	out.println("\">");
	        	out.println(getRootText());
	        	out.println("</a>");
	        }
	        MenuItemView menuItems = getMenuItems();
	        if (menuItems != null) {
	        	renderMenuItems(out, menuItems.getSubItems());
	        }
	        out.println("</li>");
        } catch (IOException e) {
            throw new JspException(e);
        }
        return (SKIP_BODY);
    }
    	
    private void renderMenuItems(JspWriter out, MenuItemViewVector menuItems) throws IOException {
    	if (menuItems != null && !menuItems.isEmpty()) {
	        out.println("<ul>");
	        Iterator itemIterator = menuItems.iterator();
	        while (itemIterator.hasNext()) {
	        	MenuItemView menuItem = (MenuItemView) itemIterator.next();
	        	MenuItemViewVector subItems = menuItem.getSubItems();
        		out.print("<li");
	        	if (subItems != null && !subItems.isEmpty()) {
	    	        if (Utility.isSet(getParentCssStyle())) {
	    	        	out.print(" class=\"");
	    	        	out.print(getParentCssStyle());
	    	        	out.print("\"");
	    	        }
	        	}
	        	out.println(">");
	        	int itemCount = calculateItemCount(menuItem);
	        	//determine if we should render a link for this item
	        	boolean renderLink = (itemCount <= 500) || (subItems == null) || subItems.isEmpty();
	        	if (renderLink) {
			        out.print("<a href=\"");
			        out.print(getRootUrl());
			        out.print(getParameterAppender());
			        out.print(getMenuItemParameterName());
			        out.print("=");
			        out.print(menuItem.getKey());
			        out.println("\">");
			        out.println(menuItem.getName());
			        out.println("<span>");
			        out.print("(");
			        out.print(itemCount);
			        out.println(")");
			        out.println("</span>");
			        out.println("</a>");
	        	}
	        	else {
		        	//if we are not going to render a link for this product category, instead
		        	//of creating a link here insert a <span> with a class of noLinkCssStyle.  
	        		//For example, if we're processing a product category of "Dispensing Equipment" 
	        		//with 900 items, and a value of "noLink" was passed in as the noLinkCssStyle 
	        		//the output would be:
		        	//<span class="noLink">Dispensing Equipment <span>(900)</span></span>
			        out.print("<span");
			        if (Utility.isSet(getNoLinkCssStyle())) {
			        	out.print(" class=\"");
			        	out.print(getNoLinkCssStyle());
			        	out.print("\"");
			        }
			        out.print(">");
			        out.println(menuItem.getName());
			        out.println("<span>");
			        out.print("(");
			        out.print(itemCount);
			        out.println(")");
			        out.println("</span>");
			        out.println("</span>");
	        	}
	        	if (subItems != null && !subItems.isEmpty()) {
	        		renderMenuItems(out, subItems);
	        	}
		        out.println("</li>");
	        }
	        out.print("</ul>");
    	}
    }
    
    private String getParameterAppender() {
    	if (getRootUrl().indexOf("?") > 0) {
    		return "&";	
    	}
    	else {
    		return "?";
    	}
    }
    
    private int calculateItemCount(MenuItemView menuItem) {
    	int returnValue = 0;
    	ProductDataVector directItems = getCategoryToItemMap().get(Integer.valueOf(menuItem.getKey()));
    	if (Utility.isSet(directItems)) {
    		returnValue = directItems.size();
    	}
    	MenuItemViewVector subItems = menuItem.getSubItems();
    	if (subItems != null && !subItems.isEmpty()) {
    		Iterator subItemIterator = subItems.iterator();
    		while (subItemIterator.hasNext()) {
    			MenuItemView subItem = (MenuItemView)subItemIterator.next();
    	    	returnValue = returnValue + calculateItemCount(subItem);
    		}
    	}
    	return returnValue;
    }
    
    /**
     * Release all allocated resources.
     */
    public void release() {
        super.release();
        _menuItems = null;
        _categoryToItemMap = null;
        _parentCssStyle = null;
        _noLinkCssStyle = null;
        _rootUrl = null;
        _rootText = null;
        _menuItemParameterName = null;
    }

}
