<%@ page language="java" %>

<%@ page import="com.cleanwise.view.utils.*" %>

<%@ page contentType="text/html; charset=UTF-8" %>



<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>



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

    <table align="center" border="0" cellspacing="0" cellpadding="0" width="622">

      <tr>

        <td colspan="2">

        <img src='<%=ClwCustomizer.getSIP(request,"introheader.gif")%>' WIDTH="209" HEIGHT="49">

        </td>

      </tr>

      <tr>

        <td align="left"  width="177" valign="top" >

          <table align="left" border="0" cellspacing="0" cellpadding="0"  height="312">

            <tr><td><img src='<%=ClwCustomizer.getSIP(request,"logintab.gif")%>' width="177" HEIGHT="18" border="0"></td></tr>

            <tr><td height="3"></td></tr>

            <tr>

              <td class="text" bgcolor="#ECECEC" height="100%" valign="top">

                  <app:storeMessage key="login.label.username"/><br>

                  <input type="text" name="j_username" value="<%=currUser%>" size="15" maxlength="255" style="width:130" tabIndex="1"/>

                  <br>

                  <app:storeMessage  key="login.label.password"/><br>

                  <input type="password" name="j_password" size="15" maxlength="30" style="width:130" tabIndex="2"/>

                  <br/>&nbsp;<br/>



                  <input type='image' property="action"

                    src='<%=ClwCustomizer.getSIP(request,"introsignin.gif")%>'

                          value='<app:storeMessage key="global.action.label.submit" />'

                          onClick="javascript:this.form.submit();"
                          tabIndex="3"
                          />

                  <input type="hidden" name="PageVisitTime"

                            value="<%=new java.util.Date()%> ">



                <br>&nbsp;<br>

                  <html:errors/> <br>

              </td>

            </tr>

          </table>

        </td>

        <td width="416"><img src='<%=ClwCustomizer.getSIP(request,"intromain.jpg")%>' WIDTH="416" HEIGHT="312"></td>

      </tr>

    </table>

  </html:form>

</div>

