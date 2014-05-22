<%@ page import="com.cleanwise.view.forms.CheckoutForm"%>
<%@ page import="org.apache.struts.action.ActionForm"%>
<%@ page import="java.math.BigDecimal"%>

<%
    String allocatedS = "";
    String spentS = "";
    String budgetWCardS = "";
    java.math.BigDecimal totWCart = null;
    java.math.BigDecimal cartAmt = new BigDecimal(0);
    //java.math.BigDecimal cartAmt = null;
    if (costCenterData != null){
    	  if ( !isPendingOrderBudjet) {
        	boolean calculate = true;
        	ActionForm aForm = (ActionForm)theForm;
         	if(aForm instanceof CheckoutForm){
        		CheckoutForm sForm = (CheckoutForm) aForm;
            ProcessOrderResultData orderRes = sForm.getOrderResult();
            if(orderRes != null){
            	if(!RefCodeNames.ORDER_STATUS_CD.ORDERED.equals(orderRes.getOrderStatusCd())){
            		calculate = false;
            	}
            }
         	}
        	if(calculate){
        		cartAmt = ShopTool.getCartBudgetTotal(costCenterData.getCostCenterId(),theForm,request);
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
        }
        allocatedS = ClwI18nUtil.getPriceShopping(request,allocated,"&nbsp;");
        spentS = ClwI18nUtil.getPriceShopping(request,spent,"&nbsp;");
        budgetWCardS = ClwI18nUtil.getPriceShoppingNegative(request,totWCart,"&nbsp;");
    }
%>
<tr> <td class="shopcategory" colspan="<%=COL_COUNT%>">
       <table cellpadding="0" cellspacing="0" align="center"   width="100%">
         <tr>
     <%  if (sciD != null && sciD.getProduct().getCostCenterId()==0) {
            prevGroup = sciD.getCategoryPath();
     %>
           <td class="shopcategory"><bean:write name="sciD" property="categoryPath"/> </td>
     <% } else {
            prevGroup = centerName;//sciD.getProduct().getCostCenterName();
     %>
           <%--<td class="shopcategory">&nbsp;<%=centerName%>&nbsp;-&nbsp;<%=centerId%> </td>--%>
		   <td class="shopcategory">&nbsp;<%=centerName%> </td>
           <td class="shopcategory" align="right">

           <table style="font-size:10px;font-weight:normal;" cellpadding="0" cellspacing="0" align="right">
           <tr >
            <td nowrap align="right" style="font-size:10px;padding-right:10px"><app:storeMessage key="shop.budgetInfo.text.allocated"/>:&nbsp;<%=allocatedS%></td>
            <td nowrap align="right" style="font-size:10px;padding-right:10px"><app:storeMessage key="shop.budgetInfo.text.spent"/>:&nbsp;<%=spentS%></td>
            <td nowrap align="right" style="font-size:10px;padding-right:10px">
            <table style="font-size:10px;font-weight:normal;" cellpadding="0" cellspacing="0" width="100%"><tr>
              <td align="right" style="font-size:10px;"><app:storeMessage key="shop.budgetInfo.text.budgetWithCart"/>:&nbsp;</td>
            <% if (!isPendingOrderBudjet) { %>
              <% if (totWCart!= null && totWCart.doubleValue() < 0 && !RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())) { %>
                <td nowrap align="left" style="font-size:10px;color:#FF0000; padding:0px;" ><app:storeMessage key="shop.budgetInfo.text.exceeded"/>&nbsp;<%=budgetWCardS%></td>
              <%}else {%>
                <td align="right" style="font-size:10px;"><%=budgetWCardS%></td>
              <% } %>
            </tr></table>
            </td>
            <%} %>
           </tr>
           </table>

           </td>
         <% } %>
         </tr>
       </table>
     </td></tr>





