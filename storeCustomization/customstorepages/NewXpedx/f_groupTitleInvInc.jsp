<%
    String allocatedS = "";
    String spentS = "";
    String budgetWCardS = "";
    java.math.BigDecimal totWCart = null;
    java.math.BigDecimal cartAmt = null;
        //   calculation cost center attributes
    if (costCenterData != null){
      if (!isPendingOrderBudjet) {
        cartAmt = ShopTool.getCartBudgetTotal(costCenterData.getCostCenterId(),theForm,request);
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
        //  end of calculation
%>

     <tr> <td class="shopcategory" colspan="<%=colCount%>">
       <table cellpadding="0" cellspacing="0" align="center"   width="100%">
         <tr>
     <%  if (sciD != null && sciD.getProduct().getCostCenterId()==0) {
            prevGroup = sciD.getCategoryPath();
     %>
           <td class="shopcategory"><bean:write name="sciD" property="categoryPath"/> </td>
     <% } else {
            prevGroup = centerName;//sciD.getProduct().getCostCenterName();
     %>
           <%--<td class="shopcategory"><%=centerName%>&nbsp;-&nbsp;<%=centerId%> </td>--%>
		   <td class="shopcategory"><%=centerName%> </td>
           <% if (!filterVal.booleanValue()) { %>
              <td class="shopcategory" align="right">
                <table style="font-size:10px;font-weight:normal;" cellpadding="0" cellspacing="0" align="right">
                  <tr >
                    <td nowrap align="right" style="font-size:10px;padding-right:10px"><app:storeMessage key="shop.budgetInfo.text.allocated"/>:&nbsp;<%=allocatedS%></td>
                    <td nowrap align="right" style="font-size:10px;padding-right:10px"><app:storeMessage key="shop.budgetInfo.text.spent"/>:&nbsp;<%=spentS%></td>
                    <td nowrap align="right" style="font-size:10px;padding-right:10px">
                      <table style="font-size:10px;font-weight:normal;" cellpadding="0" cellspacing="0" width="100%"><tr>
                        <td align="right" style="font-size:10px;"><app:storeMessage key="shop.budgetInfo.text.budgetWithCart"/>:&nbsp;</td>
                        <% if (!isPendingOrderBudjet) { %>
                        <% if ( totWCart.doubleValue() < 0 && !RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCart.getOrderBudgetTypeCd())) { %>
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
           <% }%>
         <% } %>
         </tr>
       </table>
     </td></tr>
