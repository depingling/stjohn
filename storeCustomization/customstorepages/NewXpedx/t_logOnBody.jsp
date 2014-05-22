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

    <table valign="top" align="center" border="0" cellspacing="0" cellpadding="0" width="610">
     <bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>     
	<tr>		
		<td><img src="<%=IMGPath%>/cw_spacer.gif" height="30"></td>
	</tr>
      <tr>

        <td align="left"  width="150" valign="top" >

          <table valign="top" align="left" border="0" cellspacing="0" cellpadding="0"  height="345">

            <tr><td><img src='<%=ClwCustomizer.getSIP(request,"logintab.gif")%>' width="150" HEIGHT="18" border="0"></td></tr>

            <tr>
				
              <td class="text" align="center" bgcolor="#ECECEC" height="100%" valign="top">
	
				<table border="0" valign="top">
				<tr>
				<td>&nbsp;</td>
				</tr>
				<tr><td align="left">
                  <app:storeMessage key="login.label.username"/><br>
				</td></tr>
				<tr><td>
                  <input type="text" name="j_username" value="<%=currUser%>" size="15" maxlength="255" style="width:130" tabIndex="1"/>
				</td></tr>
                  <br>
	
				<tr><td align="left">
                  <app:storeMessage  key="login.label.password"/><br>
				</td></tr>
				<tr><td>
                  <input type="password" name="j_password" size="15" maxlength="30" style="width:130" tabIndex="2"/>
				</td></tr>
                 <tr>		
					<td><img src="<%=IMGPath%>/cw_spacer.gif" height="5"></td>
				</tr>
				 <tr>
				 <td align="center">
                  <input type='image' property="action"

                    src='<%=ClwCustomizer.getSIP(request,"submitButton.gif")%>'

                          value='<app:storeMessage key="global.action.label.submit" />'

                          onClick="this.disabled=true;javascript:this.form.submit();"
                          tabIndex="3"
                          /> 
				</td>
				</tr>
                  <input type="hidden" name="PageVisitTime"

                            value="<%=new java.util.Date()%> ">



                <br>&nbsp;<br>

                  <html:errors/> <br>
				</table>
              </td>

            </tr>

          </table>

        </td>
		
		<td>
			<img src='<%=ClwCustomizer.getSIP(request,"loginImage.gif")%>' width="460" border="0">
			
		</td>
		
      </tr>

    </table>
	
	<table align="center" width="610" border="0">
	
	<tr>
	<td class="smalltext" colspan="3"><br><div class="fivemargin"><app:custom pageElement="pages.footer.msg"/></div></td>
	</tr>
	
	</table>

  </html:form>

</div>

