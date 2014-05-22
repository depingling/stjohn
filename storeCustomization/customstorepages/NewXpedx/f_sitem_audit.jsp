<%@ page import="com.cleanwise.service.api.value.ShoppingInfoData" %>
<!-- START: item audit <%=IDX%> -->

<%
if (inventoryShopping) {

java.util.List itemHist = sciD.getItemShoppingCartHistory();
if ( null != itemHist && itemHist.size() > 0) {
%>
<%
      int histLastIdx = itemHist.size() - 1;
      for (int idx = 0; idx < itemHist.size(); idx++) {
        ShoppingInfoData sid = (ShoppingInfoData) itemHist.get(idx);

        if (sid.getArg0() != null && sid.getArg0().equals("nothing")) {
            // get previous. If it was the same then continue
            if (idx < histLastIdx) {
                ShoppingInfoData sidPrev = (ShoppingInfoData) itemHist.get(idx+1);
                if ((sidPrev.getArg0() != null &&
                     sidPrev.getArg0().equals(sid.getArg0())) ||
                    (sidPrev.getArg1() != null &&
                     sidPrev.getArg1().equals(sid.getArg0()))
                    ) {
                    continue;
                }
            } else {
                continue;
            }
        }
        if (sid.getArg0() != null &&
                sid.getArg1() != null &&
                sid.getArg0().equals(sid.getArg1())) {
            continue;
        }
        if (sid.getArg0() != null &&
                sid.getArg0().trim().equals("0") &&
                sid.getMessageKey().equals("shoppingMessages.text.onHandQtySet")) {
            // get previous. If it was the same then continue
            if (idx < histLastIdx) {
                ShoppingInfoData sidPrev = (ShoppingInfoData) itemHist.get(idx+1);
                if ((sidPrev.getArg0() != null &&
                     sidPrev.getArg0().equals(sid.getArg0())) ||
                    (sidPrev.getArg1() != null &&
                     sidPrev.getArg1().equals(sid.getArg0()))
                    ) {
                    continue;
                }
            }
        }
        String messKey = sid.getMessageKey();
%>
<tr>
  <td colspan=5 width="15" class="<%=styleClass%>" style="font-style: italic;">&nbsp;</td>
  <td colspan=1 width="350" class="<%=styleClass%>" style="font-style: italic;">
    <% if(messKey==null){ %>
    <%=sid.getValue()%>
    <% } else { %>
    <app:storeMessage key="<%=messKey%>"
      arg0="<%=sid.getArg0()%>" arg1="<%=sid.getArg1()%>"
      arg2="<%=sid.getArg2()%>" arg3="<%=sid.getArg3()%>" />
      <% } %>
  </td>
  <td colspan=1 class="<%=styleClass%>" style="font-style: italic;">
    <i18n:formatDate  value="<%=sid.getAddDate()%>"  pattern="MM-dd-yyyy"
      locale="<%=ClwI18nUtil.getUserLocale(request)%>"/>
  </td>
  <td colspan=1 class="<%=styleClass%>" style="font-style: italic;">
    <app:storeMessage key="shoppingItems.text.by"/>&nbsp;<%=sid.getModBy()%>
  </td>
  <td colspan=30 class="<%=styleClass%>" style="font-style: italic;">&nbsp;</td>
</tr>

<%
}  // End of for loop.
}

} // inventoryShopping is on
%>

<!-- END: item audit <%=IDX%> -->
