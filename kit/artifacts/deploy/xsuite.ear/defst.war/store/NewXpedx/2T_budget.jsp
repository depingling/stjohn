<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.UiFrameSlotView" %>
<%@ page import="com.cleanwise.service.api.value.UiFrameSlotViewVector" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.lang.Double" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="frameCur" name="current.homepage.frame" type="com.cleanwise.service.api.value.UiFrameView"/>

<logic:present name="frameCur">
<table width="100%" cellpadding="0" cellspacing="0">
<tr>
<td>

<%
	boolean isPendingOrderBudjet = Utility.isTrue(request.getParameter("isPendingOrderBudjet"));
	
	String jspFormName = request.getParameter("jspFormName");
   if(jspFormName == null ) {
     //throw new RuntimeException("jspFormName cannot be null");
	 jspFormName = "SHOPPING_CART_FORM";
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
	
		CostCenterDataVector costCenters = ShopTool.getSiteCostCenters(request);
		request.setAttribute("costCenters",costCenters);
		if(costCenters != null && costCenters.size() > 0){
  %>
  <table>
	<tr>
	<td style="font-size:9pt">
		<b style="font-size: 9pt !important;"><app:storeMessage key="shop.budgetInfo.text.yourBudget"/></b><br>
	</td>
	</tr>
  </table>
  
  <table width="100%" cellpadding="0" cellspacing="0">
      <tr class="shopcharthead" style="font-size:9pt">
        <td align="right">&nbsp;</td>
        <td align="right"><b><app:storeMessage key="shop.budgetInfo.text.allocated"/></b></td>
        <td align="right" ><b><app:storeMessage key="shop.budgetInfo.text.spent"/></b></td>
        
      <% if (!isPendingOrderBudjet) { %>
        
        <td align="right"><b><app:storeMessage key="shop.budgetInfo.text.budgetWithCart"/></b></td>
      <%} %>
        <td align="right">&nbsp;</td>
        
      </tr>

	  <%
	  java.math.BigDecimal allocatedTotal = new BigDecimal(0.00);
        java.math.BigDecimal spentTotal = new BigDecimal(0.00);
        java.math.BigDecimal totWCartTotal = new BigDecimal(0.00);
		%>
	  <logic:iterate id="costCenterData" name="costCenters" type="com.cleanwise.service.api.value.CostCenterData">
	  <%
		String allocatedS = "";
		String spentS = "";
		String budgetWCardS = "";
		java.math.BigDecimal totWCart = null;
		java.math.BigDecimal cartAmt = null;
		
        //   calculation cost center attributes
		if (costCenterData != null && (costCenterData.getNoBudget()).equals("false")){
			if (!isPendingOrderBudjet) {
				//cartAmt = ShopTool.getCartBudgetTotal(costCenterData.getCostCenterId(),theForm,request);
				cartAmt = ShopTool.getActualCartBudgetTotal(costCenterData.getCostCenterId(),request);
				if(cartAmt==null){
					cartAmt=new BigDecimal(0.00);
				}
			}
			java.math.BigDecimal allocated = thisSite.getBudgetAllocated(costCenterData.getCostCenterId());
			java.math.BigDecimal spent = thisSite.getBudgetSpent(costCenterData.getCostCenterId());
			java.math.BigDecimal tdiff = allocated.subtract(spent);
			if (!isPendingOrderBudjet) {
				if(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())){
					totWCart = tdiff;
				}else{
					totWCart = tdiff.subtract(cartAmt);
				}
				totWCartTotal = totWCartTotal.add(totWCart);
				
			}
			allocatedS = ClwI18nUtil.getPriceShopping(request,allocated,"&nbsp;");
			spentS = ClwI18nUtil.getPriceShopping(request,spent,"&nbsp;");
			budgetWCardS = ClwI18nUtil.getPriceShoppingNegative(request,totWCart,"&nbsp;");
			allocatedTotal = allocatedTotal.add(allocated);
            spentTotal = spentTotal.add(spent);

        //  end of calculation
	  %>

		<tr style="line-height:1.5">
            <td align="left" style="font-size:9pt"><b><bean:write name="costCenterData" property="shortDesc"/></b></td>
            <td align="right" style="font-size:9pt"><%=allocatedS%></td>
            <td align="right" style="font-size:9pt"><%=spentS%></td>
            
         <% if (!isPendingOrderBudjet) { %>
            
            <td align="right" style="font-size:9pt"><%=budgetWCardS%></td>
            <% if ( totWCart.doubleValue() < 0 && !RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())) { %>
                    <td style="font-weight: bold; font-size:9pt; color:red">EXCEEDED</td>
            <% }else if(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())){ %>
                    <td style="font-weight: bold"></td>
            <%}%>
          <%} %>
        </tr>
		
		<%}%>
	  </logic:iterate>
	  
	    <tr  class="shopcharthead" style="font-size:9pt">
            <td align="right">&nbsp;</td>
            <td align="right"><%=ClwI18nUtil.getPriceShopping(request,allocatedTotal,"&nbsp;")%></td>
            <td align="right"><%=ClwI18nUtil.getPriceShopping(request,spentTotal,"&nbsp;")%></td>
            
        <% if (!isPendingOrderBudjet) { %>
            
            <td align="right" ><%=ClwI18nUtil.getPriceShopping(request,totWCartTotal,"&nbsp;")%></td>
			<td align="right">&nbsp;</td>
			<%}%>
        </tr>
		
	   <%}
	}else{//end site has budget%>
  <table>
	<tr>
	<td>&nbsp;</td>
	</tr>
  </table>
    <table width="100%" cellpadding="0" cellspacing="0">
      <tr><td>
    <%} %>
</td>
</tr>
</table>
<br>
<table width="100%" cellpadding="0" cellspacing="0">
<tr>
<td align=center>
    <%
        UiFrameSlotViewVector slots = frameCur.getFrameSlotViewVector();
        UiFrameSlotView slot0 = (UiFrameSlotView)slots.get(0);
        
    %>
    <% if (slot0.getSlotTypeCd().equals(RefCodeNames.SLOT_TYPE_CD.IMAGE)) { %>
    <app:uiFrameSlotUrlTag slotWidth="0" slotHeight="0" slot="<%=slot0%>"/>
    <% } else { %>
    <app:uiFrameSlotUrlTag slot="<%=slot0%>" />
    <% } %>

</td>

</tr>
</table>
</logic:present>
</table>





