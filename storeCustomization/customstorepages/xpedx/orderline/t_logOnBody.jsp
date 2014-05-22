<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<style>
  td.systemLoginHeader {
  color: #ffffff;
  background-color:  #003399;
  font-size: 14px;
  font-weight: bold;
  font-style: Italic;
  text-align: center;
}

.welcomeLogin {
 color: #000000;
 font-size: 14px;
 text-align: center;

}

td.systemLoginCopyright {
 color: #ffffff;
 background-color: #003399;
 font-size: 10px;
 text-align: center;
}

.systemLoginInputField {
  border: solid 1px #6699CC;
  width: 150px;
}
  </style>

<app:setLocaleAndImages/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="currUser"  type="java.lang.String" name="LOGON" property="j_username"/>

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
  <html:form action="userportal/logon.do" focus="j_username" onsubmit="return validate(this)">

    <br>&nbsp;<br>
    <table align="center" border="0" cellspacing="0" cellpadding="0" width="660">
      <tr>
        <td colspan="3" align="center">
        <img src='<%=ClwCustomizer.getSIP(request,"logo.gif")%>' WIDTH="464" HEIGHT="97">
        </td>
      </tr>
      <tr><td height="14" colspan="3">&nbsp;</td></tr>
      <tr><td colspan="3" class="systemLoginHeader"><app:storeMessage key="login.text.systemLogin"/></td></tr>

      <tr>
        <td colspan="3">
          <p class="welcomeLogin">
          <br>&nbsp;Welcome to OrderLine - your direct link to the inventory you need.</p>
          <br><html:errors/><br />

          <table width="350" cellpadding="2" cellspacing="2" align="center" style="border: solid 5px #66CC66;">
          <tr><td valign="middle" align="center">
              <table cellpadding="5" cellspacing="5" border="0" align="center">
                <tr>
                  <td class="text"><b><app:storeMessage key="login.label.username"/></b></td>
                  <td><input type="text" name="j_username" maxlength="255" size="18"
                    value="<%=currUser%>" class="systemLoginInputField" tabIndex="1">
                  </td>
                </tr>
                <tr>
                  <td class="text"><b><app:storeMessage  key="login.label.password"/></b></td>
                  <td><input type="password" name="j_password" maxlength="20" size="18" value="" class="systemLoginInputField" tabIndex="2"></td>
                </tr>
                <tr>
                  <td align="right">
                    <input type='image'
                      src='<%=ClwCustomizer.getSIP(request,"login.gif")%>'
                      property="action"
                      onClick="javascript:this.form.submit();"
                      value='<app:storeMessage key="global.action.label.submit" />'
                      tabIndex="3"/>
                  </td>
                  <td align="right">
                    <input type='image'
                      property="action"
                      src='<%=ClwCustomizer.getSIP(request,"reset.gif")%>'
                      property="action"
                      onclick="javascript:this.form.reset();return false;"
                      value='<app:storeMessage key="global.action.label.reset" />'
                      tabIndex="4"/>
                  </td>
                </tr>
              </table>

          </td></tr>
          </table>
        </td>
      </tr>
      <tr>
        <td class="text" width="43%">
          <p>&nbsp;</p>
          <p>If you have any questions please contact the OrderLine support team.
          <p>Contact your local xpedx sales professional to learn more about how OrderLine can help your
            operation be more profitable.
            <br>&nbsp;
        </td>
        <td width="9%">&nbsp;</td>
        <td class="text" width="43%">
          <b>Email:</b> <a href='mailto:distribution.webmaster@ipaper.com' tabIndex="5">distribution.webmaster@ipaper.com</a><br>
          <b>Phone:</b><br>
          <i>United States:</i> 877 269-1784<br>
          <i>Europe:</i> (31) 24 37 22 380
        </td>
      </tr>
    </table>
    <br>
    <table width="700" height="40" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td class="systemLoginCopyright">
          &copy; 2007 xpedx, an International Paper Company. All Rights Reserved.
        </td>
        <td class="systemLoginCopyright">
          <img src='<%=ClwCustomizer.getSIP(request,"xpedxLogo.gif")%>'>
        </td>
      </tr>
    </table>
   <input type="hidden" name="PageVisitTime" value="<%=new java.util.Date()%> ">
  </html:form>
</div>
