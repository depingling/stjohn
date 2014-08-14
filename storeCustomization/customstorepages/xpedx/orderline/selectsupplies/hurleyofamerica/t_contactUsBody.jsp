<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.*" %>
<%@ page import="com.cleanwise.service.api.session.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<logic:notPresent name="pages.contactus.text">
    <table border="0" cellspacing="0" cellpadding="0" width="480">
        <tr>
            <td width="30%">&nbsp;</td>
            <td width="70%">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2"><img SRC="<%=IMGPath%>/spacer.gif" WIDTH="100%" HEIGHT="5"></td>
        </tr>
      <bean:size id="size" name="<%=Constants.APP_USER%>" property="contactUsList"/>
            
            <tr>
                    <td colspan="2"><img SRC="<%=IMGPath%>/spacer.gif" WIDTH="100%" HEIGHT="5"></td>
            </tr>
            
            
            
            
            <tr>
                    <td width="30%">&nbsp;</td>
                    <td width="70%" class="text"><br><span class="text">
                    
<app:storeMessage key="template.xpedx.text.contactUs1"/><br>
<br>
                                        xpedx - Wilmington, MA <br>
                    613 Main Street <br>
                                        Wilmington, MA  01887-3236 <br>
                                        Phone: 978-988-7447 <br>
                <%--    <app:storeMessage key="contactus.text.email:"/> <a href="mailto:">michele.cardwell@veritivcorp.com</a><br>
                    <app:storeMessage key="contactus.text.phone:"/> 1-800-410-2660 Ext. 249<br> --%>
                    
<br>

<br>
For technical support, please contact the xpedx eBusiness Support Desk at: <br>
<br>
Phone: 877-269-1784 <br>
Email: <%=Constants.EMAIL_ADDR_EBUSINESS%> <mailto:<%=Constants.EMAIL_ADDR_EBUSINESS%>> <br>
<br>
<%
   String webmasterLink = ClwI18nUtil.getMessage(request,"template.xpedx.apple.text.contactUs.webmasterLink",null);  
   String webmasterPhone = ClwI18nUtil.getMessage(request,"template.xpedx.apple.text.contactUs.webmasterPhone",null);  
   if(webmasterPhone!=null && webmasterPhone.startsWith("???")) webmasterPhone = "";
%>
<%--
<app:storeMessage key="contactus.text.email:"/>   <a href="mailto:<%=webmasterLink%>"><%=webmasterLink%></a><br> --%>
<%if(Utility.isSet(webmasterPhone)){%>
<%--
<app:storeMessage key="contactus.text.phone:"/>  <%=webmasterPhone%><br> --%>
<%}%>
                     </td>
            <tr>
                <td width="30%">&nbsp;</td>
                <td width="70%"><img SRC="<%=IMGPath%>/spacer.gif" WIDTH="100%" HEIGHT="5"></td>
            </tr>
            <tr>
                <td width="30%">&nbsp;</td>
                <td width="70%"><img SRC="<%=IMGPath%>/spacer.gif" WIDTH="100%" HEIGHT="5"></td>
            </tr> 
    </table>
</logic:notPresent>
<logic:present name="pages.contactus.text">
    <table border="0" cellspacing="0" cellpadding="0" width="480">
        <tr>
            <td width="30%">&nbsp;</td>
            <td width="70%"class="text"><app:custom pageElement="pages.contactus.text"/></td>
        </tr>
    </table>
</logic:present>
