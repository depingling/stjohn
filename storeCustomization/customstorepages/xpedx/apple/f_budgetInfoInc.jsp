
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
<%@ page import="java.lang.Double" %>

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
%>

<%
boolean detailedBudget=false;
int colCount=4;
int tableWidthPercent=60;
if(null != appUser.getUserProperties() &&
   Utility.isTrue((String)appUser.getUserProperties().get(RefCodeNames.PROPERTY_TYPE_CD.PREF_VIEW_DETAIL_COST_CENTER))){
    detailedBudget = true;
    colCount=8;
}
if(showNotAffectBudgetButton || detailedBudget){
    tableWidthPercent=100;
}%>




<table cellpadding="4" cellspacing="0" width="<%=tableWidthPercent%>%">

<tr>
    <td colspan=2 align="center" class="budgethead">
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
    </td>
    <%String currPage = SessionTool.getActualRequestedURI(request);
    if(!currPage.endsWith(".do")){
        currPage = currPage + ".do";
    }
    %>
    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.DISPLAY_COST_CENTER_DETAIL%>">
        <%if(detailedBudget){%>
            <td align="left"><a href="../userportal/userPrefs.do?path=<%=currPage%>&action=setPref&name=<%=RefCodeNames.PROPERTY_TYPE_CD.PREF_VIEW_DETAIL_COST_CENTER%>&value=false">
              <app:storeMessage key="shop.budgetInfo.text.hideDetail"/></a></td>
        <%}else{%>
            <td align="left"><a href="../userportal/userPrefs.do?path=<%=currPage%>&action=setPref&name=<%=RefCodeNames.PROPERTY_TYPE_CD.PREF_VIEW_DETAIL_COST_CENTER%>&value=true">
              <app:storeMessage key="shop.budgetInfo.text.detail"/></a></td>
        <%}%>
    </app:authorizedForFunction>

     <%
     String formId = request.getParameter("formId");
     %>
    <%if(showNotAffectBudgetButton && thisSite.getNextDeliveryDate() == null){%>
        <td align="right" colspan="<%=colCount%>">
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.EXCLUDE_ORDER_FROM_BUDGET%>">
        <%if(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())){%>
        <input type='submit' property="action" class='store_fb'
                onclick="setAndSubmit('<%=formId%>','command','shop.budgetInfo.label.setBudgetedOrder')"
                 value='<app:storeMessage key="shop.budgetInfo.label.setBudgetedOrder" />'/>
        <%}else{%>
        <input type='submit' property="action" class='store_fb'
                onclick="setAndSubmit('<%=formId%>','command','shop.budgetInfo.label.setNotBudgetedOrder')"
                 value='<app:storeMessage key="shop.budgetInfo.label.setNotBudgetedOrder" />'/>
        <%}%>
        </app:authorizedForFunction>
        </td>
    <%}%>

</tr>


<!-- Display detil budget info -->
<%if(detailedBudget){%>

  <%
    CostCenterDataVector costCenters = ShopTool.getAllCostCenters(request);
    request.setAttribute("costCenters",costCenters);
    if(costCenters != null && costCenters.size() > 0){
  %>
      <tr>
        <td align="right">&nbsp;</td>
        <td align="right"><b><app:storeMessage key="shop.budgetInfo.text.allocated"/></b></td>
        <td align="right"><b><app:storeMessage key="shop.budgetInfo.text.spent"/></b></td>
        <td align="right"><b><app:storeMessage key="shop.budgetInfo.text.difference"/></b></td>
      <% if (!isPendingOrderBudjet) { %>
        <td align="right"><b><app:storeMessage key="shop.budgetInfo.text.cartTotal"/></b></td>
        <td align="right"><b><app:storeMessage key="shop.budgetInfo.text.budgetWithCart"/></b></td>
      <%} %>
        <td align="right">&nbsp;</td>
        <td align="right" width="20%">&nbsp;</td>
      </tr>

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
        <tr>
            <td align="right"><b><bean:write name="costCenterData" property="shortDesc"/></b></td>
            <td align="right"><%=ClwI18nUtil.getPriceShopping(request,allocated,"&nbsp;")%></td>
            <td align="right"><%=ClwI18nUtil.getPriceShopping(request,spent,"&nbsp;")%></td>
            <td align="right"><%=ClwI18nUtil.getPriceShopping(request,tdiff,"&nbsp;")%></td>
         <% if (!isPendingOrderBudjet) { %>
            <td align="right"><%=ClwI18nUtil.getPriceShopping(request,cartAmt,"&nbsp;")%></td>
            <td align="right"><%=ClwI18nUtil.getPriceShopping(request,totWCart,"&nbsp;")%></td>
            <% if ( totWCart.doubleValue() < 0 && !RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())) { %>
                    <td style="font-weight: bold">EXCEEDED</td>
              <% }else if(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())){ %>
                    <td style="font-weight: bold"></td>
              <%}%>
          <%} %>
            </tr>
      </logic:iterate>
        <tr  class="shopcharthead">
            <td align="right">&nbsp;</td>
            <td align="right"><%=ClwI18nUtil.getPriceShopping(request,allocatedTotal,"&nbsp;")%></td>
            <td align="right"><%=ClwI18nUtil.getPriceShopping(request,spentTotal,"&nbsp;")%></td>
            <td align="right"><%=ClwI18nUtil.getPriceShopping(request,tdiffTotal,"&nbsp;")%></td>
        <% if (!isPendingOrderBudjet) { %>
            <td align="right"><%=ClwI18nUtil.getPriceShopping(request,cartTotalAmt,"&nbsp;")%></td>
            <td align="right"><%=ClwI18nUtil.getPriceShopping(request,totWCartTotal,"&nbsp;")%></td>
        </tr>
        <tr>
            <% if ( totWCartTotal.doubleValue() < 0 && !RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())) { %>
                <td colspan="6" class="budgetwarning" style="font-weight: bold; color: red">
                  <app:storeMessage key="shop.budgetInfo.text.withThisCartYouWillExceedYourBudget"/></td>
            <% }else if(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())){ %>
                <td colspan="6" style="font-weight: bold">
                  <app:storeMessage key="shop.budgetInfo.text.oderWillNotAffectBudgetButWillRequireApproval"/></td>
            <%}%>
        <%} %>
        </tr>
  <%}/*Site has cost centers, this is probebly an unnecessary check */%>
<%}%>




<!-- Display non detil budget info -->
<%if(!detailedBudget){%>
  <%
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
  %>


  <tr><td align="right">
  <app:storeMessage key="shop.budgetInfo.text.allocated"/></td><td align="right">
        <%=ClwI18nUtil.getPriceShopping(request,thisSite.getTotalBudgetAllocated(),"&nbsp;")%>
  </td></tr>
  <tr><td align="right">
  <app:storeMessage key="shop.budgetInfo.text.spent"/></td><td  align="right">
        <%=ClwI18nUtil.getPriceShopping(request,thisSite.getTotalBudgetSpent(),"&nbsp;")%>
      </td></tr>
  <tr><td align="right">
  <app:storeMessage key="shop.budgetInfo.text.difference"/></td><td align="right">
        <%=ClwI18nUtil.getPriceShopping(request,tdiff,"&nbsp;")%>
     </td></tr>
  <% if (!isPendingOrderBudjet) { %>
    <tr><td align="right">
     <app:storeMessage key="shop.budgetInfo.text.cartTotal"/></td><td align="right">
         <%=ClwI18nUtil.getPriceShopping(request,cartTotalAmt,"&nbsp;")%>
    </td></tr>
    <tr>
      <td  align="right" class="budgethead">
        <app:storeMessage key="shop.budgetInfo.text.budgetWithCart"/> </td>
	<% //Double priceShopping = new Double(ClwI18nUtil.getPriceShopping(request,totWCart,"&nbsp;"));

           if(totWCart.doubleValue() < 0){ %>
		<td align="right" class="budgethead" style="color: red">
        
                <%=ClwI18nUtil.getPriceShopping(request,totWCart,"&nbsp;")%>
                </td>
           <% } else { %>
		<td align="right" class="budgethead" style="color: green">
        
                <%=ClwI18nUtil.getPriceShopping(request,totWCart,"&nbsp;")%>
                </td>
	   <% } %>
      <% if ( totWCart.doubleValue() < 0 && !RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())) { %>
        <td class="budgetwarning" style="font-weight: bold; color: red" colspan="2">
        <app:storeMessage key="shop.budgetInfo.text.withThisCartYouWillExceedYourBudget"/></td>
      <% }else if(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())){ %>
        <td><b><app:storeMessage key="shop.budgetInfo.text.oderWillNotAffectBudgetButWillRequireApproval"/></b></td>
      <%}%>
    </tr>
  <%}%>
<%}%>


</table>



<%}%>
