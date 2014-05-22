<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>

<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% String IMGPath = (String) session.getAttribute("pages.store.images");
   String formId = request.getParameter("formId");
   String commandName = request.getParameter("commandName");
   String shopAction = request.getParameter("shopAction");
   String invShopAction = request.getParameter("invShopAction");
%>

    <td>
    <a href="#" class="linkButton" onclick="setAndSubmit('<%=formId%>','<%=commandName%>', '<%=shopAction%>');"
    ><img src='<%=IMGPath + "/b_addtocart.gif"%>' border="0"/><app:storeMessage key="global.label.addToFillInCart"/></a>
    </td>
    <%if (ShopTool.isModernInventoryCartAvailable(request)) {%>
        <td>
            <% String add_img = IMGPath + "/b_addtocart.gif"; %>
            <a href="#" class="linkButton" onclick="setAndSubmit('<%=formId%>','<%=commandName%>', '<%=invShopAction%>');">
                <img src="<%=add_img%>" border="0"/>
                <app:storeMessage key="global.label.addToMonthlyCart"/>
            </a>
        </td>
        <%}%>
        <td>



