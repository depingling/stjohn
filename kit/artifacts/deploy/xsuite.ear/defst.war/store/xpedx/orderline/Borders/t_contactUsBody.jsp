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

<%
String countryCode = null;
try{countryCode=Utility.parseLocaleCode(appUser.getUser().getPrefLocaleCd()).getCountry();}catch(Exception e){e.printStackTrace();}
%>

Order related questions or concerns? Contact your xpedx servicing division Customer Service department directly at:<BR>
<br>
xpedx City of Industry  <br>
17411 Valley Boulevard  <br>
City of Industry, CA  91744 <br>
Margie Butler <br>
626-854-5400  <br>
<br>
xpedx Harrisburg <br>
211 House Ave<br>
Camp Hill, PA  17011 <br>
Jim Miller <br>
717-612-6100   <br>
<br>
xpedx Grand Rapids <br>
4140 East Paris Ave, SE <br>
Kentwood, MI  49512 <br>
Julie Everest <br>
616-827-5100 <br>
<br>
xpedx Greensboro <br>
3900 Spring Garden Street <br>
Greensboro, NC  27407<br>
Katie Lawson<br>
336-299-1228<br>
<br>
xpedx Paper & Graphics Irvine<br>
15301 Barranca Parkway <br>
Irvine, CA  92618 <br>
Dorothy Stone <br>
949-727-3560 <br>
<br>
<br>
<br>
Technical issues with the website should be directed to:<br>
<br>
xpedx eBusiness Support Desk<br>
<br>
xpedx eBusiness Support Desk  <br>
Email: distribution.webmaster@xpedx.com <br>
Phone: 877-269-1784 <br>

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


