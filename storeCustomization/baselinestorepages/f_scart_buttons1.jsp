<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% String IPTH = (String) session.getAttribute("pages.store.images"); %>

  <a href="#" class="linkButton" onclick="actionMultiSubmit('submitQty','recalc');"
  ><img src='<%=IPTH + "/b_recalculate.gif"%>' border="0"/><app:storeMessage key="global.label.recalculate"/></a>

<a href="../store/checkout.do" class="linkButton"><img src=<%=IPTH%>/b_checkout.gif
  border=0><app:storeMessage key="global.action.label.checkout"/></a>

  <a href="#" class="linkButton" onclick="actionMultiSubmit('submitQty','removeSelected');"
  ><img src='<%=IPTH + "/b_remove.gif"%>' border="0"/><app:storeMessage key="global.label.removeSelected"/></a>

  <a href="#" class="linkButton" onclick="actionMultiSubmit('submitQty','removeAll');"
  ><img src='<%=IPTH + "/b_remove.gif"%>' border="0"/><app:storeMessage key="global.label.removeAll"/></a>



