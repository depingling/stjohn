<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<% String IPTH = (String) session.getAttribute("pages.store.images"); %>


 <td>&nbsp;</td>
 <td class="text"><b><app:storeMessage key="shoppingCart.text.name"/></b></td>
 <td><html:text name="SHOPPING_CART_FORM" property="userOrderGuideName" maxlength="30"/></td>
 <td>
 <% String b_save = IPTH + "/b_save.gif"; %>
  <a href="#" class="linkButton" onclick="actionMultiSubmit('submitQty','saveOrderGuide');"
  ><img src="<%=b_save%>" border="0"/><app:storeMessage key="shoppingCart.text.saveOrderGuide"/></a>
 </td>
