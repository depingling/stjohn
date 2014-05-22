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
    <table border="0" cellspacing="0" cellpadding="0" width="490">
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
<p>  

<br>

Order related questions or concerns? Contact Smith & Hawken Corporate directly at:<br>
<br>
Joe Brooks<br>
4 Hamilton Landing Ste 100<br>
Novato, CA 94949<br>
Email: <a href="mailto:joe.brooks@smithandhawken.com">joe.brooks@smithandhawken.com</a><br>
Phone: 415-506-3848<br>

<br>


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
