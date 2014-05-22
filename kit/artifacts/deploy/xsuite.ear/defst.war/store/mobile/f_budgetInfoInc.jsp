
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.math.BigDecimal" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>



<%
   boolean isPendingOrderBudjet = Utility.isTrue(request.getParameter("isPendingOrderBudjet"));

   String jspFormName = request.getParameter("jspFormName");
   if(jspFormName == null && !isPendingOrderBudjet) {
     throw new RuntimeException("jspFormName cannot be null");
   }
   boolean showNotAffectBudgetButton = Utility.isTrue(request.getParameter("showNotAffectBudgetButton"));
   ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
   CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
   SiteData thisSite = appUser.getSite();

   if (isPendingOrderBudjet) {
     com.cleanwise.service.api.value.OrderJoinData theOrder =
         (com.cleanwise.service.api.value.OrderJoinData) session.getAttribute("order");
     if (theOrder != null) {
       SiteData orderSite = SessionTool.getSiteData(request,theOrder.getOrder().getSiteId());
       if (orderSite != null) {
         thisSite = orderSite;
       }
     }
  }
  if(thisSite !=null && thisSite.hasBudgets() ) {
    org.apache.struts.action.ActionForm theForm = null;
    if (!isPendingOrderBudjet) {
      theForm = (org.apache.struts.action.ActionForm)session.getAttribute(jspFormName);
    }

boolean detailedBudget=false;
if(null != appUser.getUserProperties() &&
   Utility.isTrue((String)appUser.getUserProperties().get(RefCodeNames.PROPERTY_TYPE_CD.PREF_VIEW_DETAIL_COST_CENTER))){
    detailedBudget = true;
}

%>



<div id="budget">
<ul>
<li class="listResultsHeader">
  <logic:notPresent name="<%=Constants.APP_USER%>" property="userAccount.budgetTypeCd">
    <app:storeMessage key="shop.budgetInfo.text.ytdLocationBudget"/>
  </logic:notPresent>
  <logic:present name="<%=Constants.APP_USER%>" property="userAccount.budgetTypeCd">
    <logic:equal name="<%=Constants.APP_USER%>" property="userAccount.budgetTypeCd" value="<%=RefCodeNames.BUDGET_ACCRUAL_TYPE_CD.BY_PERIOD%>">
      <app:storeMessage key="shop.budgetInfo.text.fiscalPeriodLocationBudget"/>
    </logic:equal>
    <logic:notEqual name="<%=Constants.APP_USER%>" property="userAccount.budgetTypeCd" value="<%=RefCodeNames.BUDGET_ACCRUAL_TYPE_CD.BY_PERIOD%>">
      <app:storeMessage key="shop.budgetInfo.text.ytdLocationBudget"/>
    </logic:notEqual>
  </logic:present>
    <%String currPage = SessionTool.getActualRequestedURI(request);
    if(!currPage.endsWith(".do")){
        currPage = currPage + ".do";
    }
    %>
    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.DISPLAY_COST_CENTER_DETAIL%>">
        <%if(detailedBudget){%>
            <a href="../userportal/userPrefs.do?path=<%=currPage%>&action=setPref&name=<%=RefCodeNames.PROPERTY_TYPE_CD.PREF_VIEW_DETAIL_COST_CENTER%>&value=false">
              <app:storeMessage key="shop.budgetInfo.text.hideDetail"/></a>
        <%}else{%>
            <a href="../userportal/userPrefs.do?path=<%=currPage%>&action=setPref&name=<%=RefCodeNames.PROPERTY_TYPE_CD.PREF_VIEW_DETAIL_COST_CENTER%>&value=true">
              <app:storeMessage key="shop.budgetInfo.text.detail"/></a>
        <%}%>
    </app:authorizedForFunction>

     <%
     String formId = request.getParameter("formId");
     %>
    <%if(showNotAffectBudgetButton && thisSite.getNextDeliveryDate() == null){%>
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.EXCLUDE_ORDER_FROM_BUDGET%>">
        <%if(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())){%>
        <input type='submit' property="action" class='store_fb'
                onclick="setAndSubmit('<%=formId%>','command','shop.budgetInfo.label.setBudgetedOrder');this.disabled=true;"
                 value='<app:storeMessage key="shop.budgetInfo.label.setBudgetedOrder" />'/>
        <%}else{%>
        <input type='submit' property="action" class='store_fb'
                onclick="setAndSubmit('<%=formId%>','command','shop.budgetInfo.label.setNotBudgetedOrder');this.disabled=true;"
                 value='<app:storeMessage key="shop.budgetInfo.label.setNotBudgetedOrder" />'/>
        <%}%>
        </app:authorizedForFunction>
    <%}%>
</li>


<!-- Display detil budget info -->

<%if(detailedBudget){%>

  <%
    CostCenterDataVector costCenters = ShopTool.getAllCostCenters(request);
    request.setAttribute("costCenters",costCenters);
    if(costCenters != null && costCenters.size() > 0){
  %>
  <li>
        <%
            java.math.BigDecimal cartTotalAmt = new BigDecimal(0.00);
            java.math.BigDecimal allocatedTotal = new BigDecimal(0.00);
            java.math.BigDecimal spentTotal = new BigDecimal(0.00);
          java.math.BigDecimal tdiffTotal = new BigDecimal(0.00);
          java.math.BigDecimal totWCartTotal = new BigDecimal(0.00);
        %>
        <logic:iterate id="costCenterData" name="costCenters" type="com.cleanwise.service.api.value.CostCenterData">
        <%
		
          java.math.BigDecimal cartAmt = null;
		  if(!Utility.isTrue(costCenterData.getNoBudget())){
			
          if (!isPendingOrderBudjet) {
            cartAmt = ShopTool.getCartBudgetTotal(costCenterData.getCostCenterId(),theForm,request);
          }
          java.math.BigDecimal allocated = thisSite.getBudgetAllocated(costCenterData.getCostCenterId());
          java.math.BigDecimal spent = thisSite.getBudgetSpent(costCenterData.getCostCenterId());
          java.math.BigDecimal tdiff = allocated.subtract(spent);
          java.math.BigDecimal totWCart = null;
          if (!isPendingOrderBudjet) {
            if(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())){
              totWCart = tdiff;
            }else{
              totWCart = tdiff.subtract(cartAmt);
            }
            cartTotalAmt = cartTotalAmt.add(cartAmt);
            totWCartTotal = totWCartTotal.add(totWCart);
          }
          allocatedTotal = allocatedTotal.add(allocated);
          spentTotal = spentTotal.add(spent);
          tdiffTotal = tdiffTotal.add(tdiff);
		  
        %>
        <li class="listResultsHeader">
	    <bean:write name="costCenterData" property="shortDesc"/>
        </li>
        <li>
	    <dl id="budgetLine">
              <dt><span class="textLabel"><app:storeMessage key="shop.budgetInfo.text.allocated"/></span></dt><dd><%=ClwI18nUtil.getPriceShopping(request,allocated,"&nbsp;")%></dd>
              <dt><span class="textLabel"><app:storeMessage key="shop.budgetInfo.text.spent"/></span></dt><dd><%=ClwI18nUtil.getPriceShopping(request,spent,"&nbsp;")%></dd>
              <dt><span class="textLabel"><app:storeMessage key="shop.budgetInfo.text.difference"/></span></dt><dd><%=ClwI18nUtil.getPriceShopping(request,tdiff,"&nbsp;")%></dd>
              <% if (!isPendingOrderBudjet) { %>
              <dt><span class="textLabel"><app:storeMessage key="shop.budgetInfo.text.cartTotal"/></span></dt><dd><%=ClwI18nUtil.getPriceShopping(request,cartAmt,"&nbsp;")%></dd>
              <dt><span class="textLabel"><app:storeMessage key="shop.budgetInfo.text.budgetWithCart"/></span></dt><dd><%=ClwI18nUtil.getPriceShopping(request,totWCart,"&nbsp;")%></dd>
              <% if ( totWCart.doubleValue() < 0 && !RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())) { %>
                    <dt><span class="warning">EXCEEDED</span></dt>
              <% } %>
              <%} %>
           </dl>
        </li>
	    <%} //no budget cost center %>
      </logic:iterate>
         <li class="listResultsHeader">
            Total:
         </li>
         <li>
            <dl>
              <dt><span class="textLabel"><app:storeMessage key="shop.budgetInfo.text.allocated"/></span></dt><dd><%=ClwI18nUtil.getPriceShopping(request,allocatedTotal,"&nbsp;")%></dd>
              <dt><span class="textLabel"><app:storeMessage key="shop.budgetInfo.text.spent"/></span></dt><dd><%=ClwI18nUtil.getPriceShopping(request,spentTotal,"&nbsp;")%></dd>
              <dt><span class="textLabel"><app:storeMessage key="shop.budgetInfo.text.difference"/></span></dt><dd><%=ClwI18nUtil.getPriceShopping(request,tdiffTotal,"&nbsp;")%></dd>
        <% if (!isPendingOrderBudjet) { %>
              <dt><span class="textLabel"><app:storeMessage key="shop.budgetInfo.text.cartTotal"/></span></dt><dd><%=ClwI18nUtil.getPriceShopping(request,cartTotalAmt,"&nbsp;")%></dd>
              <dt><span class="textLabel"><app:storeMessage key="shop.budgetInfo.text.budgetWithCart"/></span></dt><dd><%=ClwI18nUtil.getPriceShopping(request,totWCartTotal,"&nbsp;")%></dd>
            <% if ( totWCartTotal.doubleValue() < 0 && !RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())) { %>
                  <dt class="warning"><app:storeMessage key="shop.budgetInfo.text.withThisCartYouWillExceedYourBudget"/></dt>
            <% }else if(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())){ %>
                  <dt class="warning"><app:storeMessage key="shop.budgetInfo.text.oderWillNotAffectBudgetButWillRequireApproval"/></dt>
            <%}%>
        <%} %>
        </dl>
        </li>
  <%}/*Site has cost centers, this is probebly an unnecessary check */%>
<%}%>




<!-- Display non detail budget info -->
<%if(!detailedBudget){%>
  <%--
     java.math.BigDecimal cartTotalAmt = null;
	
     if (!isPendingOrderBudjet) {	
	   cartTotalAmt = ShopTool.getCartBudgetTotal(theForm,request);
       if(cartTotalAmt==null){cartTotalAmt=new BigDecimal(0.00);}
	 }
	   
	
     java.math.BigDecimal allocated = thisSite.getTotalBudgetAllocated();
     java.math.BigDecimal spent = thisSite.getTotalBudgetSpent();
     if(allocated==null){allocated=new BigDecimal(0.00);}
     if(spent==null){spent=new BigDecimal(0.00);}

     java.math.BigDecimal tdiff = thisSite.getTotalBudgetAllocated().subtract(thisSite.getTotalBudgetSpent());
     java.math.BigDecimal totWCart = null;
	
     if (!isPendingOrderBudjet) {
       if(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())){
         totWCart = tdiff;
       }else{
         totWCart = tdiff.subtract(cartTotalAmt);
       }
     }
  --%>
    <%
  CostCenterDataVector costCenters = ShopTool.getAllCostCenters(request);
  request.setAttribute("costCenters",costCenters);
  java.math.BigDecimal cartTotalAmt = new BigDecimal(0.00); 
  if(costCenters != null && costCenters.size() > 0){
  %>
        <logic:iterate id="costCenterData" name="costCenters" type="com.cleanwise.service.api.value.CostCenterData">
        <%
		
          java.math.BigDecimal cartAmt = null;
		  if(!Utility.isTrue(costCenterData.getNoBudget())){
			
            if (!isPendingOrderBudjet) {
              cartAmt = ShopTool.getCartBudgetTotal(costCenterData.getCostCenterId(),theForm,request);

              cartTotalAmt = cartTotalAmt.add(cartAmt);
            }
        %>
	    <%} //if(!Utility.isTrue(costCenterData.getNoBudget())){%>		
      </logic:iterate>
  <%} //if(costCenters != null && costCenters.size() > 0){ %>
  <% 
     java.math.BigDecimal allocated = thisSite.getTotalBudgetAllocated();
     java.math.BigDecimal spent = thisSite.getTotalBudgetSpent();
     if(allocated==null){allocated=new BigDecimal(0.00);}
     if(spent==null){spent=new BigDecimal(0.00);}

     java.math.BigDecimal tdiff = thisSite.getTotalBudgetAllocated().subtract(thisSite.getTotalBudgetSpent());
     java.math.BigDecimal totWCart = null;
	
     if (!isPendingOrderBudjet) {
       if(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())){
         totWCart = tdiff;
       }else{
         totWCart = tdiff.subtract(cartTotalAmt);
       }
     }
  %>


<dl>
  <dt><app:storeMessage key="shop.budgetInfo.text.allocated"/></dt>
  <dd><%=ClwI18nUtil.getPriceShopping(request,thisSite.getTotalBudgetAllocated(),"&nbsp;")%></dd>
  <dt><app:storeMessage key="shop.budgetInfo.text.spent"/></td><td  align="right"></dt>
  <dd><%=ClwI18nUtil.getPriceShopping(request,thisSite.getTotalBudgetSpent(),"&nbsp;")%></dd>
  <dt><app:storeMessage key="shop.budgetInfo.text.difference"/></td><td align="right"></dt>
  <dd><%=ClwI18nUtil.getPriceShopping(request,tdiff,"&nbsp;")%></dd>
  <% if (!isPendingOrderBudjet) { %>
    <dt><app:storeMessage key="shop.budgetInfo.text.cartTotal"/></td><td align="right"></dt>
    <dd><%=ClwI18nUtil.getPriceShopping(request,cartTotalAmt,"&nbsp;")%></dd>
    <dt><app:storeMessage key="shop.budgetInfo.text.budgetWithCart"/> </dt>
    <dd><%=ClwI18nUtil.getPriceShopping(request,totWCart,"&nbsp;")%></dd>
    <% if ( totWCart.doubleValue() < 0 && !RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())) { %>
       <dt class="warning"><app:storeMessage key="shop.budgetInfo.text.withThisCartYouWillExceedYourBudget"/></dt>
    <% }else if(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())){ %>
        <dt class="warning"><app:storeMessage key="shop.budgetInfo.text.oderWillNotAffectBudgetButWillRequireApproval"/></dt>
    <%}%>
  <%}%>
<%}%>


</dl>



<%}%>
</li>
</ul>
</div> <%--end div id=budget--%>
