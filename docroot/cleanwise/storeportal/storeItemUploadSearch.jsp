<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="STORE_ITEM_LOADER_FORM" type="com.cleanwise.view.forms.StoreItemLoaderMgrForm"/>


<div class="text">
<script language="JavaScript1.2">
<!--
function SetCatChecked(val) {
 dml=document.forms; 
 for(i=0; i<dml.length; i++) {
  found = false;
  ellen = dml[i].elements.length;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    //alert(dml[i].elements[j].name);
    if (dml[i].elements[j].name=='selectedCatalogIds') {
      dml[i].elements[j].checked=val;
      found = true;
    }
  }
  if(found) break;
 }
}

//-->
</script>

  <table ID=1015 cellspacing="0" border="0" width="769" class="mainbody">
  <html:form styleId="1016"  action="/storeportal/storeitemupload.do"
            scope="session">
  <tr> <td><b>Find Tables:</b></td>
     <td colspan="3">
 	  <html:text name="STORE_ITEM_LOADER_FORM" property="fileNameTempl"/>
     </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
       <html:submit property="action" value="Search"/>
       <html:submit property="action" value="Create New"/>
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Show Processed Tables:
       <html:checkbox name="STORE_ITEM_LOADER_FORM" property="showProcessedFl"/>
    </td>
  </tr>

  <script type="text/javascript" language="JavaScript">
  <!--
     dml=document.forms; 
     for(i=0; i<dml.length; i++) {
      ellen = dml[i].elements.length;
      //alert('next_form='+ellen);
      for(j=0; j<ellen; j++) {
        //alert(dml[i].elements[j].name);
        if (dml[i].elements[j].name=='fileNameTempl') {
          dml[i].elements[j].focus();
          break;
        }
      }
     } 
  // -->
  </script>

</table>
<% 
  UploadDataVector tables = theForm.getTableList();
  if(tables!=null) {
%>    
<table ID=1017 cellspacing="0" border="0" width="769" class="results">
<tr>
<td colspan=4>Search Results Count: 
<%=tables.size()%> 
</td>
</tr>

<tr align=left>
<td><a ID=1018 class="tableheader" href="storeitemupload.do?action=sort&sortField=id">Id </td>
<td><a ID=1019 class="tableheader" href="storeitemupload.do?action=sort&sortField=name">File Name </td>
<td><a ID=1020 class="tableheader" href="storeitemupload.do?action=sort&sortField=type">Add Date </td>
<td><a ID=1021 class="tableheader" href="storeitemupload.do?action=sort&sortField=type">Mod Date </td>
<td><a ID=1022 class="tableheader" href="storeitemupload.do?action=sort&sortField=status">Status </td>
</tr>
   <logic:iterate id="utable" name="STORE_ITEM_LOADER_FORM" property="tableList"
     type="com.cleanwise.service.api.value.UploadData">
    <bean:define id="key"  name="utable" property="uploadId"/>
  	<bean:define id="addDate"  name="utable" property="addDate"/>
  	<bean:define id="modDate"  name="utable" property="modDate"/>
    <% String linkHref = new String("storeitemupload.do?action=edit&id=" + key);%>
    <tr>
  <td><bean:write name="utable" property="uploadId"/></td>
  <td><html:link href="<%=linkHref%>"><%=utable.getFileName()%></html:link></td>
	<td><i18n:formatDate value="<%=addDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/></td>
	<td><i18n:formatDate value="<%=modDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/></td>
  <td><%=utable.getUploadStatusCd()%></td>
 </tr>

 </logic:iterate>

 <tr align=center>
 <td colspan="4">&nbsp;</td>
 </tr>
 </table>
 <% } %>
 
</html:form>


</div>


