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

<bean:define id="AccountId" type="java.lang.String" name="pages.account.id"/>
<%CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);%>

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
      <logic:iterate id="contact" indexId="idx" name="<%=Constants.APP_USER%>" property="contactUsList" type="com.cleanwise.view.utils.ContactUsInfo">
         <%
            String email  = contact.getEmail();
            String busName = contact.getNickName();
            AddressData address = contact.getAddress();
            String pNum = contact.getPhone();
            String fNum = contact.getFax();
            String callHours = contact.getCallHours();
            String name = contact.getContactName();
          %>
            
            
            
            <tr>
                    <td colspan="2"><img SRC="<%=IMGPath%>/spacer.gif" WIDTH="100%" HEIGHT="5"></td>
            </tr>
            
            
            
            
            <tr>
                    <td width="30%">&nbsp;</td>
                    <td width="70%" class="text"><br><span class="text">
                    
Order related questions or concerns? Contact the xpedx Customer Service department directly at:
<br>
9105 Sabal Industrial Blvd<br>
Tampa, FL 33619<br>
Email: <a href="target@veritivcorp.com">target@veritivcorp.com</a><br>
Phone: 1-800-258-7976<br>
<br>
<br>
Technical issues with the website should be directed to:<br>
<br>
xpedx eBusiness Support Desk<br>
<br>
xpedx eBusiness Support Desk<br>
Email: <a href="<%=Constants.EMAIL_ADDR_EBUSINESS%>"><%=Constants.EMAIL_ADDR_EBUSINESS%></a><br>
Phone: 877-269-1784<br>
                     </td>
            <tr>
                <td width="30%">&nbsp;</td>
                <td width="70%"><img SRC="<%=IMGPath%>/spacer.gif" WIDTH="100%" HEIGHT="5"></td>
            </tr>
            <tr>
                <td width="30%">&nbsp;</td>
                <td width="70%"><img SRC="<%=IMGPath%>/spacer.gif" WIDTH="100%" HEIGHT="5"></td>
            </tr>
        </logic:iterate>
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
