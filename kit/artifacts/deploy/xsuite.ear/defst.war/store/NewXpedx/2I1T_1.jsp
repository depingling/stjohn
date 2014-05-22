<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.UiFrameSlotView" %>
<%@ page import="com.cleanwise.service.api.value.UiFrameSlotViewVector" %>

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
<td align=center>
    <%
        UiFrameSlotViewVector slots = frameCur.getFrameSlotViewVector();
        UiFrameSlotView slot0 = (UiFrameSlotView)slots.get(0);
        UiFrameSlotView slot1 = (UiFrameSlotView)slots.get(1);
        UiFrameSlotView slot2 = (UiFrameSlotView)slots.get(2);
    %>
    <% if (slot0.getSlotTypeCd().equals(RefCodeNames.SLOT_TYPE_CD.IMAGE)) { %>
    <app:uiFrameSlotUrlTag slotWidth="0" slotHeight="0" slot="<%=slot0%>"/>
    <% } else { %>
    <app:uiFrameSlotUrlTag slot="<%=slot0%>" />
    <% } %>

</td>

<td align=center>
    <% if (slot1.getSlotTypeCd().equals(RefCodeNames.SLOT_TYPE_CD.IMAGE)) { %>
    <app:uiFrameSlotUrlTag slotWidth="0" slotHeight="0" slot="<%=slot1%>"/>
    <% } else { %>
    <app:uiFrameSlotUrlTag slot="<%=slot1%>" />
    <% } %>
</td>

</tr>

<tr>
<td colspan="2">
    <% if (slot2.getSlotTypeCd().equals(RefCodeNames.SLOT_TYPE_CD.IMAGE)) { %>
    <app:uiFrameSlotUrlTag slotWidth="0" slotHeight="0" slot="<%=slot2%>"/>
    <% } else { %>
    <app:uiFrameSlotUrlTag slot="<%=slot2%>" />
    <% } %>
</td>
</tr>
</logic:present>
</table>





