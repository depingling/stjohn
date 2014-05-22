<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript1.2">
<!--
function submitenter(myfield,e)
{
var keycode;
if (window.event) keycode = window.event.keyCode;
else if (e) keycode = e.which;
else return true;

if (keycode == 13)
   {
   myfield.form.submit();
   return false;
   }
else
   return true;
}

//-->
</script>


<html:form styleId="1601" name="LOCATE_STORE_FH_FORM" action="storeportal/fhmgr.do" scope="session" focus="mainField">

<table ID=1602 width="<%=Constants.TABLEWIDTH%>" border="0" class="mainbody">
    <tr> 
        <td  align="right">
            <b>Search Freight Handlers:&nbsp;</b>
        </td>
        <td>
            <html:text styleId="mainField" property="searchField" onkeypress="return submitenter(this,event)"/>
            <html:radio property="searchType" value="id" /> ID 
            <html:radio property="searchType" value="nameBegins" /> Name(starts with) 
            <html:radio property="searchType" value="nameContains" />   Name(contains) 
        </td>
    </tr>  
    <tr>
        <td></td>
        <td>
            <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
            </html:submit>
            <html:submit property="action">
                <app:storeMessage  key="admin.button.create"/>
            </html:submit>
            Show Inactive: <html:checkbox property="showInactiveFl" />
        </td>
    </tr>
</table>

</html:form>