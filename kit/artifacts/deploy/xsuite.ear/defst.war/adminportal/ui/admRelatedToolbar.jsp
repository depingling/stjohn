<%! String lAction = "init"; %>
<% lAction = request.getParameter("action"); %>
<% if (lAction.equals("orderguide") ) { %>
<jsp:include flush='true' page="orderguidesToolbar.jsp"/>
<% } else if (lAction.equals("site") ) { %>
<jsp:include flush='true' page="admSiteToolbar.jsp"/>
<% } else if (lAction.equals("account") ) { %>
<jsp:include flush='true' page="admAccountToolbar.jsp"/>
<% } else if (lAction.equals("catalog") ) { %>
<jsp:include flush='true' page="catalogToolbar.jsp"/>
<% } else if (lAction.equals("store") ) { %>
<jsp:include flush='true' page="admStoreToolbar.jsp"/>
<% } else if (lAction.equals("contract") ) { %>
<jsp:include flush='true' page="admContractToolbar.jsp"/>
<% } else if (lAction.equals("user") ) { %>
<jsp:include flush='true' page="admUserToolbar.jsp"/>
<% } %>


