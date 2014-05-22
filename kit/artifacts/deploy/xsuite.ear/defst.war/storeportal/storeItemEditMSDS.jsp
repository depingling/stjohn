<%
String msdsClick = "";
String msdsSubmitString = "";
%><logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="product.msds" value="">
<bean:define id="msdsName" name="STORE_ADMIN_ITEM_FORM" property="product.msds" scope="session"/>
<%//msdsClick = "window.open('/"+storeDir+"/"+msdsName+"','MSDS');";%>
<% String msdsStorageTypeCd = theForm.getMsdsStorageTypeCd(); %>
<% if (msdsStorageTypeCd==null || msdsStorageTypeCd.equals("") || msdsStorageTypeCd.equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE)){ %>
       <%    String msdsFromDbTypeLink = "item-edit.do?action=itemDocumentFromDb&path=" + msdsName;   
		     String actionMsdsSubmitStringForDb = "window.open('" + msdsFromDbTypeLink +"');"; 
		     msdsClick = actionMsdsSubmitStringForDb;
       %>
       Database<br>
<% } else { %>
       E3 Storage<br>
       <% 
                       String documentTypeLink = "item-edit.do?action=itemDocumentFromE3Storage&path=" +msdsName; 
                       msdsSubmitString = "window.open('" + documentTypeLink +"');";
                       msdsClick = msdsSubmitString;
   	   %>
       <% } %>               
</logic:notEqual>
<logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
<div id="msds_ChooseFile" style="display:none;">
<html:file name="STORE_ADMIN_ITEM_FORM" property="msdsFile"
    accept="application/pdf" styleId="msdsFile"/>
</div>
<div id="msds_Case1" xstyle="display:none;">
<table cellpadding="2" cellspacing="2">
<tr>
	<td>
This MSDS is managed through a manufacturer MSDS plug-in.
You may override this MSDS by clicking below and providing a new file.
	</td>
</tr>
<bean:define id="theForm" name="STORE_ADMIN_ITEM_FORM" type="com.cleanwise.view.forms.StoreItemMgrForm"/>
<%
String msdsWebServicesClick = "";
%>
<% 
ActionErrors ae = StoreItemMgrLogic.getMsdsUrlThroughWebService(request, theForm);
%>
<% String storeDir1=ClwCustomizer.getStoreDir(); %> 
<bean:define id="msdsWsUrl" name="STORE_ADMIN_ITEM_FORM" property="wsMsdsUrl" scope="session"/>
<% String msdsWsUrl1 = msdsWsUrl.toString().trim(); %>
<% if (msdsWsUrl1 != null) { %>
<%    if (msdsWsUrl1.toString().length() > 0) { %>
<%       msdsWebServicesClick = "window.open('/"+storeDir1+"/"+msdsWsUrl1+"','MSDS');"; //CORRECT %>
<%    } else { %>
<%       msdsWebServicesClick = "my_window=window.open('', 'MSDS');my_window.document.write('<h1>No MSDS retrieved via Web Services</h1>');my_window.document.close();";%>
<%    } %>
<% } else { %>
<%    msdsWebServicesClick = "my_window=window.open('', 'MSDS');my_window.document.write('<h1>No MSDS retrieved via Web Services</h1>');my_window.document.close();";%>
<% } %>
<tr>	
	<td>

<input type="button" class="text" value="View MSDS" onclick="<%=msdsWebServicesClick%>"/>
<html:button styleClass="text" value="Override MSDS" property="action" onclick="showChooseFile(this);"/>
	</td>
</tr>
</table>
</div>
<div id="msds_Case2" xstyle="display:none;">
<table cellpadding="2" cellspacing="2">
<tr>
	<td >
<html:button styleClass="text" value="Choose File" property="action" onclick="showChooseFile(this);"/>
	</td>
</tr>
<tr>
	<td>
<input type="button" class="text" value="View MSDS" <%if (msdsClick.length() > 0){%>onclick="<%=msdsClick%>"<%}else{%>disabled="true"<%}%>/>
	</td>
</tr>
<tr>
	<td>
There is a manufacturer MSDS plug-in but you have chosen to override the plug-in
and use a provided MSDS. To use the Manufacturer plug-in click below. NOTE: This will
delete your provided manufacturer sheet.
	</td>
</tr>
<tr>	
	<td >
<input type="submit" name="action" class="text" value="Use Manufacturer MSDS Plug-in"<%if (msdsClick.length() == 0){%>" disabled="true"<%}%>/>
	</td>
</tr>
</table>
</div>
<div id="msds_Case3" xstyle="display:none;">
<table cellpadding="2" cellspacing="2">
<tr>
	<td >
<html:button styleClass="text" value="Choose File" property="action" onclick="showChooseFile(this);"/>
	</td>
</tr>
<tr>
	<td>
<input type="button" class="text" value="View MSDS"<%if (msdsClick.length() > 0){%> onclick="<%=msdsClick%>"<%}else{%> disabled="true"<%}%>/>
<input type="submit" name="action" class="text" value="Delete MSDS"<%if (msdsClick.length() == 0){%>" disabled="true"<%}%>/>
	</td>
</tr>
</table>
</div>
</logic:equal>
<%--
             <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="product.msds" value="">
                <bean:define id="msdsName" name="STORE_ADMIN_ITEM_FORM" property="product.msds" scope="session"/>
                <% String msdsClick = new String("window.open('/"+storeDir+"/"+msdsName+"','MSDS');");%>


                <td>
				<logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                 <b>MSDS:&nbsp;&nbsp;&nbsp;</b>
                </logic:notEqual>
                Path:<bean:write name="STORE_ADMIN_ITEM_FORM" property="product.msds"/><br>
                <html:button styleClass="text" onclick="<%=msdsClick%>" value="View MSDS" property="action"/>
                <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                    <html:submit styleClass="text" value="Delete MSDS" property="action"/>
                </logic:equal>
                </td>
             </logic:notEqual>
             
             <logic:equal name="STORE_ADMIN_ITEM_FORM" property="product.msds" value="">
                <td>
                    <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                        <html:button styleClass="text" value="View MSDS" property="action" disabled="true"/>
                    </logic:equal>
                </td>
             </logic:equal>
--%>

<script>
var msdsMap = new Object();
var msdsCur = '<bean:write name="STORE_ADMIN_ITEM_FORM" property="product.msds"/>';
var msds_Case1 = document.getElementById('msds_Case1');
var msds_Case2 = document.getElementById('msds_Case2');
var msds_Case3 = document.getElementById('msds_Case3');
var msds_ChooseFile = document.getElementById('msds_ChooseFile');
function refMSDS() {
	var manList = document.getElementById('manufacturer');
	if (manList != null) {
		var opt = manList.options[manList.selectedIndex];
		msds_ChooseFile.style.display = 'none';
		msds_Case1.style.display = 'none';
		msds_Case2.style.display = 'none';
		msds_Case3.style.display = 'none';
		if (msdsMap[opt.value]) {
			if (msdsCur) {
				msds_Case2.style.display = 'block';
			} else {
				msds_Case1.style.display = 'block';
			}
		} else if (opt.value != '0') {
			msds_Case3.style.display = 'block';
//			alert(msdsCur);
		}
	}
}
<logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable"
value="true"><logic:iterate id="manuf"
name="STORE_ADMIN_ITEM_FORM" property="storeManufacturers"
type="com.cleanwise.service.api.value.ManufacturerData"><bean:define id="manufId"
name="manuf" property="busEntity.busEntityId"/><%
PropertyDataVector miscProps = manuf.getMiscProperties();
String msdsPlugin = (miscProps == null) ? null : Utility.getPropertyValue(
miscProps, RefCodeNames.PROPERTY_TYPE_CD.MSDS_PLUGIN);
if (RefCodeNames.MSDS_PLUGIN_CD.DEFAULT.equals(msdsPlugin)) msdsPlugin = null;
if (msdsPlugin != null) {%>msdsMap['<%=manufId.toString()%>'] = '<%=msdsPlugin%>';
<%}%></logic:iterate></logic:equal>

function showChooseFile(btnToHide) {
	msds_ChooseFile.style.display = 'block';
    if (btnToHide) {
        btnToHide.style.display = 'none'; //Bug STJ-4139 fix
    }
}
//-->
</script>