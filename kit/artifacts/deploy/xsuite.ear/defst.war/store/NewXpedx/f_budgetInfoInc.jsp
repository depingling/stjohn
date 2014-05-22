
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

int tableWidthPercent=80;
%>




<table border="0" cellpadding="0" cellspacing="0" width="<%=tableWidthPercent%>%">
<tr>
  <td class="budgetHeaderText"><b>
      <%--
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
      --%>
      <app:storeMessage key="shop.budgetInfo.text.fiscalPeriodBudget"/>
  </b></td>
</tr>
<tr> <td>
    <table class="greyBorder">
  <%
     java.math.BigDecimal cartTotalAmt = new BigDecimal(0.00);
	 /*if (!isPendingOrderBudjet) {
         cartTotalAmt = ShopTool.getCartBudgetTotalWithAllCharges(request);
         if(cartTotalAmt==null){
             cartTotalAmt=new BigDecimal(0.00);
         }
     }*/

    
     CostCenterDataVector costCenterData = ShopTool.getAllCostCenters(request);
     if (!isPendingOrderBudjet) {
	     for( int i = 0; costCenterData != null && i < costCenterData.size(); i++ ) {
	     	BigDecimal cartAmt = ShopTool.getCartBudgetTotal(((CostCenterData)costCenterData.get(i)).getCostCenterId(),theForm,request);
	     	cartTotalAmt = cartTotalAmt.add(cartAmt);
	     }
	     if(cartTotalAmt==null){
             cartTotalAmt=new BigDecimal(0.00);
         }
     }
     java.math.BigDecimal allocated = ShopTool.getAllocatedBudgetFor(request, costCenterData);
     java.math.BigDecimal spent = thisSite.getTotalBudgetSpent();
     if(allocated==null){allocated=new BigDecimal(0.00);}
     if(spent==null){spent=new BigDecimal(0.00);}

     java.math.BigDecimal tdiff = allocated.subtract(spent);
     java.math.BigDecimal totWCart = null;
     String totWCartMessage = "";
     if (!isPendingOrderBudjet) {
       if(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())){
         totWCart = tdiff;
       }else{
         totWCart = tdiff.subtract(cartTotalAmt);
       }
     }
     
     String totWCartS = ClwI18nUtil.getPriceShoppingNegative(request,totWCart,"&nbsp;");
   %>
    <tr>
        <td class="bottomBorder" width="40%"><app:storeMessage key="shop.budgetInfo.text.allocated"/></td>
        <td class="bottomBorder" width=11><img src="<%=ClwCustomizer.getSIP(request,"arrow.gif")%>" alt=""></td>
        <td align="right" class="greyBorder" width="50%" style="color:#000000;"><%=ClwI18nUtil.getPriceShopping(request,allocated,"&nbsp;")%>&nbsp;&nbsp;&nbsp;</td>
    </tr>
    <tr>
        <td class="bottomBorder"><app:storeMessage key="shop.budgetInfo.text.spent"/></td>
        <td class="bottomBorder" width=11><img src="<%=ClwCustomizer.getSIP(request,"arrow.gif")%>" alt=""></td>
        <td  align="right" class="greyBorder" style="color:#000000;">
          <%=ClwI18nUtil.getPriceShopping(request,spent,"&nbsp;")%>&nbsp;&nbsp;&nbsp;
        </td>
    </tr>
    <tr>
        <td class="bottomBorder" nowrap><app:storeMessage key="shop.budgetInfo.text.budgetWithCart"/></td>
        <td class="bottomBorder" width=11><img src="<%=ClwCustomizer.getSIP(request,"arrow.gif")%>" alt=""></td>
        <td class="greyBorder">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
            <td nowrap align="left">
              <% if (totWCart.signum() < 0) { %>
                <div style="color:#FF0000;"><b><app:storeMessage key="shop.budgetInfo.text.exceeded"/>&nbsp;&nbsp;&nbsp;</b></div>
              <% }%>
            </td>
            <td nowrap align="right">
              <% if (totWCart.signum() < 0) { %>
                <div style="color:#FF0000;"><b><%=totWCartS%>&nbsp;&nbsp;&nbsp;</b></div>
              <% } else {%>
                <%=totWCartS%>&nbsp;&nbsp;
              <% }%>
            </td>
          </tr>
          </table>
        </td>
    </tr>
    </table>
 </td></tr></table>

<%}%>
