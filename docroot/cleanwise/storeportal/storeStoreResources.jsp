<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Hashtable"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript1.2">
 <!--
function actionSubmit(formNum, action) {
 var actions;
 actions=document.forms[formNum]["action"];
 //alert(actions.length);
 for(ii=actions.length-1; ii>=0; ii--) {
   if(actions[ii].value=='hideAction') {
     actions[ii].value=action;
     document.forms[formNum].submit();
     break;
   }
 }
 return false;
 }
-->
</script>


<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="store" type="java.lang.String" toScope="session"/>

<html:form styleId="1316" name="STORE_STORE_DETAIL_FORM" action="storeportal/storeStoreResources.do"
	scope="session" type="com.cleanwise.view.forms.StoreStoreMgrDetailForm">
	<table ID=1317 width="<%=Constants.TABLEWIDTH%>" >
<tr>
<td><b>Locale</b></td>
<td><html:select name="STORE_STORE_DETAIL_FORM" property="selectedResourceLocale">
<html:option value="">Default</html:option>
<html:options  collection="Store.locale.vector" property="value" />
</html:select></td>
</tr>
<tr>
 <td><b>Name</b></td>
 <td><html:text  styleId="mainSearchField" property="nameSearchMessage"
                 onkeypress="return submitenter(this,event)" />
		<html:button  property="action"  value ="Search" onclick="actionSubmit(0,'searchMessageResources');">
            </html:button>
 </td>
</tr>
<tr><td colspan="2">
		<b>Show Resources</b>
	 </td>

	</tr>
<tr><td colspan="2">
		<html:radio property="resMessageShowType" value="Store" />  Store
		<html:radio property="resMessageShowType" value="Default" /> Default
		<html:radio property="resMessageShowType" value="All" />  All(store+default)
	 </td>
</tr>
<tr><td colspan="2">&nbsp;</td></tr>
        <%int idx=0;%>
        <logic:present name="STORE_STORE_DETAIL_FORM" property="storeMessageResources">
         <bean:define id="resources"   name="STORE_STORE_DETAIL_FORM" property="storeMessageResources"/>
        <bean:size id="rescount"  name="resources"/>
        <bean:define id="defV" name="STORE_STORE_DETAIL_FORM" property="messageResourcesDefaultValue"/>

        <tr><td colspan="2">count : <%=rescount%></td></tr>    
        <logic:iterate id="resource" name="resources"  indexId="i">
            <bean:define id="messName" name="resource" property="name" type="java.lang.String"/>
            <%boolean idxFl=false;
                if ( (idx%2) ==0) idxFl=true;%>
            <%	if ( idxFl ) { %>
            <tr class="rowa"  >
                    <% } else { %>
            <tr class="rowc">
                    <% } %>

                    <td width="60" ><b>Name</b></td>
                    <td><bean:write name="resource" property="name"/></td>
                    </tr>

                    <% if ( idxFl ) { %>
            <tr class="rowa"  >
                    <% } else { %>
            <tr class="rowc">
                    <% } %>

                    <td widht="60" ><b>Default</b></td>
                    <td><%if(defV!=null&&((Hashtable)defV).get(messName)!=null){%>
                    <%=(String)((Hashtable)defV).get(messName)%><%} else {%>
                    <div class="text"><font color=red>!!! Error:Default value not found !!!</font></div>
                        <%}%></td>
                    </tr>

                    <% if ( idxFl ) { %>
            <tr class="rowa"  >
                    <% } else { %>
            <tr class="rowc">
                    <% } %>

                    <td width="60" ><b>Store</b></td>
                    <%String prop = "storeMessageResources["+i+"].value";%>
                  <td ><html:text  style="font-size:12px " size="140" name="STORE_STORE_DETAIL_FORM" property="<%=prop%>"/></td>
                  </tr>

            <%idx++;%>

            </logic:iterate>

    </logic:present>
	<tr>
		<td colspan="2" align="center">
            <%if(idx>0) {%>
            <html:button  property="action"  value ="Save" onclick="actionSubmit(0,'saveMessageResources');">
            </html:button>
            <%}%>
     </td>
	</tr>
</table>
<html:hidden  property="action" value="hideAction"/>
</html:form>