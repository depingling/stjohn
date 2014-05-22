
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="amdf" name="STORE_ACCOUNT_DETAIL_FORM" type="com.cleanwise.view.forms.StoreAccountMgrDetailForm"/>
<%!private String contract_select="";%>



<script language="JavaScript">
    <!--
function passIdAndNameC(id,v1,v2) {
    document.forms[1].select.value=id;
    document.forms[1].submit();
    return true;
}
function hideLocate_c(id)
{
eval("document.getElementById(id)").style.display='none';
}
//-->
</script>

<tr><td> <html:form styleId="660"  name="STORE_CONTRACT_SEARCH_FORM" action="/storeportal/storeContractLocate.do" focus="searchField"
    scope="session" type="com.cleanwise.view.forms.StoreContractMgrSearchForm">
<table ID=661  width="749" class="mainbody">



  <tr> <td><b>Find Contracts:</b></td>
       <td colspan="3">
			<html:text name="STORE_CONTRACT_SEARCH_FORM" property="searchField"/>




       </td>
  </tr>

  <tr> <td>&nbsp;</td>
       <td colspan="3">
         <html:radio name="STORE_CONTRACT_SEARCH_FORM" property="searchType" value="id" />
         ID
         <html:radio name="STORE_CONTRACT_SEARCH_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="STORE_CONTRACT_SEARCH_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>

  <tr> <td>&nbsp;</td>
       <td colspan="3">
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
	<html:submit property="action">
		<app:storeMessage  key="admin.button.viewall"/>
	</html:submit>
     <html:button property="action" onclick="hideLocate_c('storeContractLocate');" value="Hide"/>
      </td>
  </tr>
  <tr><td>&nbsp;</td></tr>
  </table>
</html:form>
</td></tr>
<tr><td>

<html:form styleId="662" name="STORE_CONTRACT_SEARCH_FORM" action="/storeportal/storeContractLocate.do" focus="searchField"
    scope="session" type="com.cleanwise.view.forms.StoreContractMgrSearchForm">
            <input type="hidden" name="action" value="setSelect">
		    <input type="hidden" name="select" value="<%=contract_select%>">

<logic:greaterThan name="STORE_CONTRACT_SEARCH_FORM" property="listCount" value="0">
Search results count:&nbsp;<bean:write name="STORE_CONTRACT_SEARCH_FORM" property="listCount" filter="true"/>
    <table ID=663 cellpadding="2" cellspacing="0" border="0" width="749" class="results">
<tr align=left>
<td><a ID=664 class="tableheader" href="storeContractLocate.do?action=sort&sortField=id">Contract&nbsp;Id </td>
<td><a ID=665 class="tableheader" href="storeContractLocate.do?action=sort&sortField=name">Contract Name </td>
<td><a ID=666 class="tableheader" href="storeContractLocate.do?action=sort&sortField=catalog">Catalog Name </td>
<td><a ID=667 class="tableheader" href="storeContractLocate.do?action=sort&sortField=status">Contract Status </td>
</tr>

<logic:iterate id="contract" name="STORE_CONTRACT_SEARCH_FORM" property="resultList" >
 <bean:define id="key"  name="contract" property="contractId"/>


 <tr>
  <td><bean:write name="contract" property="contractId" filter="true"/></td>
  <td>
    <% String []contract_param = {key.toString(),"",""};%>
    <app:JSCall f_name="passIdAndNameC" param="<%=contract_param%>"><bean:write name="contract" property="contractName" filter="true"/></app:JSCall>



  </td>
  <td><bean:write name="contract" property="catalogName" filter="true"/></td>
  <td><bean:write name="contract" property="status" filter="true"/></td>
 </tr>


 </logic:iterate>

 <tr>
 	<td colspan="4">&nbsp;	</td>
 </tr>

</table>

</logic:greaterThan></html:form>
 </td></tr>




