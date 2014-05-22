<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.cleanwise.view.forms.UserShopForm"%>
<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@ page import="com.cleanwise.service.api.value.PairView" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.MenuItemView" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<bean:define id="myForm" name="esw.ShoppingForm"  type="com.espendwise.view.forms.esw.ShoppingForm"/>
<%
	UserShopForm userShopForm = Utility.getSessionDataUtil(request).getUserShopForm();
	pageContext.setAttribute("userShopForm",userShopForm);
    String name = "SHOP_FORM";
    String property = "shopNavigatorInfo";
    String link ="";
	StringBuilder shoppingCatalogLink = new StringBuilder(50);
	shoppingCatalogLink.append(request.getSession().getAttribute("pages.root"));
	shoppingCatalogLink.append("/userportal/esw/shopping.do?");
	shoppingCatalogLink.append(Constants.PARAMETER_OPERATION);
	shoppingCatalogLink.append("=");
	shoppingCatalogLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOP_BY_CATALOG);
	shoppingCatalogLink.append("&");
	shoppingCatalogLink.append(Constants.PARAMETER_CATALOG_ITEM_KEY);
	shoppingCatalogLink.append("=");
	StringBuilder shoppingCatalogAllProductsLink = new StringBuilder(50);
	shoppingCatalogAllProductsLink.append(request.getSession().getAttribute("pages.root"));
	shoppingCatalogAllProductsLink.append("/userportal/esw/shopping.do?");
	shoppingCatalogAllProductsLink.append(Constants.PARAMETER_OPERATION);
	shoppingCatalogAllProductsLink.append("=");
	shoppingCatalogAllProductsLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOP_BY_CATALOG);
	shoppingCatalogAllProductsLink.append("&");
	shoppingCatalogAllProductsLink.append(Constants.PARAMETER_CATALOG_ITEM_KEY);
	shoppingCatalogAllProductsLink.append("=");
	shoppingCatalogAllProductsLink.append("0");
	Map<String,Integer> categoryItemsCountMap = (HashMap<String,Integer>)request.getAttribute("categoryItemsCountMap");
%>
            <logic:present name="userShopForm" property="<%=property%>">
            <bean:define id="path"
                         name="userShopForm"
                         property="<%=property%>"
                         type="com.cleanwise.service.api.value.PairViewVector"/>
            <%
                String s = "";
            SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
            String allProducts = ClwMessageResourcesImpl.getMessage(request,"productCatalog.label.allProducts");
        	String allProductsLink = "";
        	Integer allProductsCount = sessionDataUtil.getAllProductsCount();

            if((sessionDataUtil.getPreviousCategory() == null || sessionDataUtil.getPreviousCategory().trim().equals("") ||
            		myForm.getCatalogItemKey() == null || myForm.getCatalogItemKey().trim().equals("") || myForm.getCatalogItemKey().equals("0"))
            		&& !Constants.PARAMETER_OPERATION_VALUE_SEARCH_PRODUCTS.equals(myForm.getOperation())){
            	if(allProductsCount <= 500){
            		link ="<a href=\""+ shoppingCatalogAllProductsLink.toString()+"\">";
            		allProductsLink = link + allProducts +" </a>";
            	}
            	else{
            		allProductsLink = ClwMessageResourcesImpl.getMessage(request,"productCatalog.label.allProducts");
            	}
            	s = allProductsLink;
            }
            else  if(Constants.PARAMETER_OPERATION_VALUE_SEARCH_PRODUCTS.equals(myForm.getOperation())){
            	s = ClwMessageResourcesImpl.getMessage(request,"productCatalog.label.product.search");
            }
            else{ 
                for (int i = 0; path != null && i < path.size(); i++) {
                	
                	if (path.get(i) == null) continue;
                	String obj1 = (String)((PairView) path.get(i)).getObject1();
                	if(allProductsCount <= 500){
                		link ="<a href=\""+ shoppingCatalogAllProductsLink.toString()+"\">";
                		allProductsLink = link + allProducts +" </a>";
                	}
                	else{
                		allProductsLink = ClwMessageResourcesImpl.getMessage(request,"productCatalog.label.allProducts");
                	}
                	String obj2 = (String)((PairView) path.get(i)).getObject2();
                	String[] strs = null;
                	if (obj2 != null){
                		obj2.split("=");
                	}	
                	String itemId =  (strs != null && strs.length > 1) ? strs[strs.length-1] : null;
                	int itemsCount = (itemId != null && !itemId.equals("")?categoryItemsCountMap.get(itemId):501);
                	if(itemsCount <= 500){
                		link ="<a href=\""+ shoppingCatalogLink.toString()+ itemId+"\">";
                		link = link + obj1 + "</a>";
                	}
                	else{
                		link = obj1;
                	}
                if(((i == 0 && !MenuItemView.ATTR.ROOT.equals(obj1))
                	||(i== 1) ))
                {
                	/*
                	if this is a top level category(means category just under root) 
                	and if it have subcategory, then it shouldn't be clickable. 
                	*/
                	s += allProductsLink + " > " + link  ;
                }else if (i == path.size() - 1) {
                    //last one
                    //String obj2 = ((String) ((PairView) path.get(i)).getObject2());
                    if(obj2!=null && !obj2.trim().equalsIgnoreCase("")){
                    	s += " > " + link + "";
                    }
                    else
                    	s+="";
                } else {
//                     if(((PairView) path.get(i)).getObject2() != null && 
//                        ((String) ((PairView) path.get(i)).getObject2()).trim().equalsIgnoreCase("")){
                    if(obj2==null || (obj2!=null && obj2.trim().equalsIgnoreCase("")) ){
                    	s+="";
                    }
                    else{
                    s += " > " + link ;
                    }
                    s += " ";
                }

                }
            }
                %><%=s%>
    </logic:present>



