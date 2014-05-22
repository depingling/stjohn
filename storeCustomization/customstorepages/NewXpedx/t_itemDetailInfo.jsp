<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!--PAGESRCID=t_itemDetailInfo.jsp-->
<% String storeDir = ClwCustomizer.getStoreDir();%>
<% String formName = request.getParameter("formName"); %>
<bean:define id="theForm" name="<%=formName%>" type="com.cleanwise.view.forms.UserShopForm"/>

<bean:define id="item" name="<%=formName%>" property="itemDetail"
             type="com.cleanwise.service.api.value.ShoppingCartItemData"/>
<table>
<tr>
<td>
<table width="100%" height="100%">
<tr>
<td align="center">

<div class="itemGroupShortInfoDetail" style="background-color:#fff">

    <b class=rblacktopborder style="background-color:#fff">
        <b class=rblack1border style="background-color:#000000"></b>
        <b class=rblack2border style="background-color:#fff"></b>
        <b class=rblack3border style="background-color:#fff"></b>
        <b class=rblack4border style="background-color:#fff"></b>
    </b>

<div class="borderContent" style="border-left:1px solid #000000;border-right:1px solid #000000;background-color:white">
<table width="230">


<app:displayProdDetail name="item"/>


<tr>
    <td>
        <table>
            <tr><% String msds = item.getProduct().getMsds();
                if (msds != null && msds.trim().length() > 0) {
				   String msdsStr = "/"+storeDir+"/"+msds;
            %>
                <td> <%----- msds --%>
                    <table cellpadding="0" cellspacing="0" border="0">
                        <tr>
						<app:xpedxButton label='global.label.msds' 
						url="<%=msdsStr%>"
						target='_blank'
						/>	 
                        </tr>
                    </table>
                </td>
                <% } %>
                <% String ded = item.getProduct().getDed();
                    if (ded != null && ded.trim().length() > 0) {
					  String dedStr = "/"+storeDir+"/"+ded;
                %>
                <td><%----- ded --%>
                    <table cellpadding="0" cellspacing="0" border="0">
                        <tr>
						<app:xpedxButton label='global.label.ded' 
						url="<%=dedStr%>"
						target='_blank'
						/>	 
                        </tr>
                    </table>
                </td>
                <% } %>
                <% String spec = item.getProduct().getSpec();
                    if (spec != null && spec.trim().length() > 0) {
					   String specStr =  "/"+storeDir+"/"+spec;
                %>
                <td><%----- spec --%>
                    <table cellpadding="0" cellspacing="0" border="0">
                        <tr>
						<app:xpedxButton label='global.label.productSpec' 
						url="<%=specStr%>"
						target='_blank'
						/>	 
                        </tr>
                    </table>
                </td>
                <% } %>
            </tr>
        </table>
    </td>
</tr>
</table>
</div>
    <b class=rblackbottomborder style="background-color:#fff">
        <B class=rblack4border style="background-color:#fff"></b>
        <b class=rblack3border style="background-color:#fff"></b>
        <b class=rblack2border style="background-color:#fff"></b>
        <b class=rblack1border style="background-color:#000000"></b>
    </b>
</div>
</td>
</tr>
</table>
</td>
</tr>
</table>
