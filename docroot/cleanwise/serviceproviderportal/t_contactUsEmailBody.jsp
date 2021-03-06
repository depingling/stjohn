<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="IMGPath" type="java.lang.String" 
  name="pages.store.images"/>
  
<script language="JavaScript1.2"> <!--

function actionSubmit(formNum, action) {
 var actions;
 
 actions=document.forms[formNum]["action"];
 for(ii=actions.length-1; ii>=0; ii--) {
   if(actions[ii].value=='hiddenAction') {
     actions[ii].value=action;
     document.forms[formNum].submit();
     break;
   }
 }
 return false;
 }
 -->

</script>
 
<table>
<tr>
<td width="10%">&nbsp;</td>
<td width="90%">
<html:form name="EMAIL_FORM" 
type="com.cleanwise.view.forms.EmailForm"
action="serviceproviderportal/contactus_email.do">

<span class="formtext"><app:storeMessage key="contactus.text.name"/></span><br>
<bean:define id="uname1" type="java.lang.String" name="USER_DETAIL_FORM" property="detail.userData.firstName" toScope="request"/>
<bean:define id="uname2" type="java.lang.String" name="USER_DETAIL_FORM" property="detail.userData.lastName" toScope="request"/>
<bean:define id="fm" name="USER_DETAIL_FORM" type="java.lang.String" property="detail.emailData.emailAddress" toScope="request"/>

<% String wholeName = uname1 + " " + uname2; %>
<html:text  size="30" name="EMAIL_FORM" property="fromName" 
value="<%=wholeName%>"/>
<br>


<span class="formtext"><app:storeMessage key="contactus.text.emailAddress"/></span><br>
<html:text size="30"  name="EMAIL_FORM" property="fromEmail" 
value="<%=fm%>"/>
<br>
<span class="formtext"><app:storeMessage key="contactus.text.message"/></span><br>
<html:textarea name="EMAIL_FORM" property="fromMessage" 
cols="60" rows="5" /><br><br>

<html:button property="action" styleClass="store_fb" onclick="actionSubmit('0','send_email_contact_msg');">
    <app:storeMessage key="contactus.text.sendEmail"/>
</html:button>

<html:hidden property="action" value="hiddenAction"/>
</html:form>
</div>
</td>
</td>
</table>