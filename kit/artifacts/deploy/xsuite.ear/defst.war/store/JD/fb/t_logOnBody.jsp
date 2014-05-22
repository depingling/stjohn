<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<app:setLocaleAndImages/>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<script language="JavaScript1.2">
  function validate(frm) {
  if (frm.j_username.value == "") {
  alert('<app:storeMessage  key="login.errors.username"/>');
  frm.j_username.focus();
  return false;
  }
  if (frm.j_password.value == "") {
  alert('<app:storeMessage  key="login.errors.password"/>');
  frm.j_password.focus();
  return false;
  }
  return true;
  }
</script>

<div class="bodyalt">
  <!-- copied (with some changes) from f_login.jhtml -->
  <br><br>
  <html:form action="userportal/logon.do" focus="j_username" onsubmit="return validate(this)">
<br><br>
<table width="1%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td colspan="3" align="center">
<img src="<%=ClwCustomizer.getSIP(request,"logo_header.gif")%>" width="759" height="87"></td>
</tr>
<tr>
<td colspan="3" bgcolor="82AE8B"><br><br><br><br></td>
</tr>
<tr>
<td></td>
<td><br><br>
    <table align="center" border="0" cellspacing="0" cellpadding="0" width="422">
      <tr>
        <td height="1" width="7"><img src="<%=ClwCustomizer.getSIP(request,"cw_logintopleft.gif")%>" WIDTH="7" HEIGHT="1"></td>
        <td class="changeshippingdk" height="1" width="408"><img src="<%=ClwCustomizer.getSIP(request,"spacer.gif")%>" height="1" width="408"></td>
        <td height="1" width="7"><img src="<%=ClwCustomizer.getSIP(request,"cw_logintopright.gif")%>" WIDTH="7" HEIGHT="1"></td>
      </tr>
      <tr>
        <td width="7"><img src="<%=ClwCustomizer.getSIP(request,"cw_loginleftlogo.gif")%>" WIDTH="7" HEIGHT="63"></td>
        <td align="left"  width="408">
<center><img src="<%=ClwCustomizer.getSIP(request,"jd_logo2.gif")%>" WIDTH="246" HEIGHT="63"></center>
        </td>
        <td width="7"><img src="<%=ClwCustomizer.getSIP(request,"cw_loginrightlogo.gif")%>" WIDTH="7" HEIGHT="63"></td>
      </tr>
      <tr>
        <td width="7"><img src="<%=ClwCustomizer.getSIP(request,"cw_logincontentleft.gif")%>" WIDTH="7" HEIGHT="224"></td>
        <td width="408" valign="middle">
          <table align="center" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td colspan="2"></td>
            </tr>
            <tr>
              <td colspan="2"><span class="subheadergeneric">
                <app:storeMessage key="login.label.title"/>
              </td>
            </tr>
            <tr>
              <td colspan="2">&nbsp;</td>
            </tr>
            <tr>
              <td class="text"><app:storeMessage key="login.label.username"/></td>
              <td><html:text name="LOGON" property="j_username" size="15" maxlength="255" tabindex="1"/></td>
            </tr>
            <tr>
              <td class="text"><app:storeMessage  key="login.label.password"/></td>
              <td><html:password property="j_password" size="15" maxlength="20" tabindex="2"/></td>
            </tr>
            <tr>
              <td colspan="2">&nbsp;</td>
            </tr>
            <tr>

              <td colspan="2">
              <input type='submit' property="action" class='loginSubmit'
                 value='<app:storeMessage key="global.action.label.submit" />' tabIndex="3"/>

                <input type="hidden" name="PageVisitTime"
                value="<%=new java.util.Date()%> ">
              </td>
            </tr>


            <tr><td colspan=2>
              <br><html:errors/> <br>
              <bean:define id="currUser"  type="java.lang.String" name="LOGON" property="j_username"/>
              <% if (currUser.length() > 0) {  %>
              <logic:present name="LoginFailureCount">
                <logic:greaterThan name="LoginFailureCount" value="0">
                  <a href="pwdAccess.do?userName=<%=Utility.encodeForURL(currUser)%>&action=send" tabIndex="99">[<app:storeMessage key="login.text.forgotPasswordQst"/>]</a><br><br>
                </logic:greaterThan>
              </logic:present>
              <% } // End of user name length check. %>
            </td>
            </tr>
            <tr><td colspan="2" align="center"><img src="<%=ClwCustomizer.getSIP(request, "connexion.gif")%>" width="250" height="62"></tr>
            <tr>
              <td colspan="2"></td>
            </tr>
          </table><br>
        </td>
        <td width="7"><img src="<%=ClwCustomizer.getSIP(request,"cw_logincontentright.gif")%>" WIDTH="7" HEIGHT="224"></td>
      </tr>
      <tr>
        <td width="7"><img src="<%=ClwCustomizer.getSIP(request,"cw_loginbl.gif")%>" WIDTH="7" HEIGHT="17"></td>
        <td class="changeshippingdk" width="408"><img src="<%=ClwCustomizer.getSIP(request,"spacer.gif")%>" height="17" width="1"></td>
        <td width="7"><img src="<%=ClwCustomizer.getSIP(request,"cw_loginbr.gif")%>" WIDTH="7" HEIGHT="17"></td>
      </tr>
    </table>
</td>
<td></td>
<tr>
<td width="179" align="left"><img src="<%=ClwCustomizer.getSIP(request,"logo_bottom_left.gif")%>"></td>
<td class="smalltext" width="422"><br><div class="fivemargin"><app:custom pageElement="pages.footer.msg"/></div></td>
<td width="179" align="right"><img src="<%=ClwCustomizer.getSIP(request,"jd_logo2.gif")%>" WIDTH="164" HEIGHT="42"></td>
</tr>
</table>
  </html:form>
</div>
