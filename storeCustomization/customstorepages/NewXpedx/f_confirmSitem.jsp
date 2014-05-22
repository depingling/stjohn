    <%----------------Confirmation---Cart Item -------------------------------------------%>
    <%-- <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)itemsIdx)%>">--%>
     <%String styleClass = (((rowCount++) %2 )==0) ?  "evenRowColor" : "oddRowColor";%>
     <tr class="<%=styleClass%>">

     <td class="text"><div>
     <% Date curDate = Constants.getCurrentDate();
        Date effDate = sciD.getProduct().getEffDate();
        Date expDate = sciD.getProduct().getExpDate();
        if(effDate != null && effDate.compareTo(curDate)<=0 &&
          (expDate==null || expDate.compareTo(curDate)>0)) {
     %>
     <bean:write name="sciD" property="quantity"/>
     <% } else { %>
       N/A
     <% } %>
     </div></td>
     <td class="text"><div>
       <bean:write name="sciD" property="product.uom"/>&nbsp;
     </div></td>

     <td class="text"><div>
       <bean:write name="sciD" property="actualSkuNum"/>&nbsp;
     </div></td>
     <td class="text"><div>
       <bean:write name="sciD" property="product.catalogProductShortDesc"/>&nbsp;
     </div></td>
     <td class="text" align="left"><div>
       <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
         <bean:define id="price"  name="sciD" property="price"/>
         <%=ClwI18nUtil.getPriceShopping(request,price,"&nbsp;")%>&nbsp;&nbsp;
         <logic:equal name="sciD" property="contractFlag" value="true">
         <logic:equal name="<%=Constants.APP_USER%>" property="showWholeCatalog" value="true">
           *
         </logic:equal></logic:equal>
       </logic:equal>
       <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
         &nbsp;
       </logic:equal>
     </div></td>
     <td class="text" align="left"><div>
       <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
         <bean:define id="amount"  name="sciD" property="amount"/>
         <%=ClwI18nUtil.getPriceShopping(request,amount,"&nbsp;")%>
       </logic:equal>
       <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
         &nbsp;
       </logic:equal>
     </div></td>
     </tr>






