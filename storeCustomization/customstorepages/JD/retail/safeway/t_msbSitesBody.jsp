<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript1.2">
 <!--
function actionSubmit(formNum, action) {
 var actions;
 actions=document.forms[formNum]["action"];
 //alert(actions.length);
 for(ii=actions.length-1; ii>=0; ii--) {
   if(actions[ii].value=='BBBBBBB') {
     actions[ii].value=action;
     document.forms[formNum].submit();
     break;
   }
 }
 return false;
 }

function f_tcb() {
  var tsf = document.forms[0].searchField.value;
  if ( "" == tsf ) {
    document.forms[0].searchType[0].checked= false;
    document.forms[0].searchType[1].checked= false;
  } else {
    if (document.forms[0].searchType[0].checked == true){
      document.forms[0].searchType[0].checked= true;
      document.forms[0].searchType[1].checked= false;
    } else {
      document.forms[0].searchType[0].checked= false;
      document.forms[0].searchType[1].checked= true;
    }
  }
}
-->
</script>

<div class="text"><font color=red><html:errors/></font></div>

<bean:define id="toolBarTab" type="java.lang.String" value="sites" toScope="request"/>
<table align=center CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH%>">
<% String f_msbToolbar = ClwCustomizer.getStoreFilePath(request,"f_msbToolbar.jsp"); %>
  <jsp:include flush='true' page="<%=f_msbToolbar%>"/>
</table>
<% CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
  StoreData userStore = appUser.getUserStore();
  String hpAction = request.getParameter("hp_action");
  boolean isaStartPage = false;
  if (hpAction != null && hpAction.equals("init") && appUser.isaCustomer())  {
    isaStartPage = true;
  }
  %>

<table align=center CELLSPACING=0 CELLPADDING=5 width="<%=Constants.TABLEWIDTH%>" class="tbstd">
<tr>
<td>
  <table>
  <html:form name="SITE_SEARCH_FORM" action="/userportal/msbsites.do"
    scope="session" type="com.cleanwise.view.forms.SiteMgrSearchForm"
   >

<% if ( appUser != null && appUser.getSiteNumber() > 1 ) {  %>

  <tr>
  <td align="right"><b><app:storeMessage key="msbSites.text.storeNumber"/></b></td>
  <td>
  <html:text name="SITE_SEARCH_FORM" property="searchField"
    onchange="javascript: f_tcb();"
    onblur="javascript: f_tcb();"
    onfocus="javascript: f_tcb();"
/>
  </td>
  <td nowrap="nowrap">
  <html:radio name="SITE_SEARCH_FORM" property="searchType"
                    value="nameBegins" /><app:storeMessage key="msbSites.text.nameStartsWith"/>
  <html:radio name="SITE_SEARCH_FORM" property="searchType"
                   value="nameContains" /><app:storeMessage key="msbSites.text.nameContains"/>
  </td>
  </tr>
<script type="text/javascript" language="JavaScript">
  <!--
  var focusControl = document.forms["SITE_SEARCH_FORM"].elements["searchField"];
  if (focusControl != undefined && focusControl.type != "hidden" && !focusControl.disabled) {
     focusControl.focus();
  }
  // -->
</script>

  <tr>
   <td align="right"><b><app:storeMessage key="msbSites.text.city"/></b></td>
   <td><html:text name="SITE_SEARCH_FORM" property="city" />
   <td>&nbsp;</td>
  </td>
  </tr>
  <tr>
    <% if (userStore.isStateProvinceRequired())  { %>
    <td align="right"><b><app:storeMessage key="msbSites.text.state"/></b></td>
    <td><html:text name="SITE_SEARCH_FORM" property="state" />
    <% } else { %>
    <td colspan="2">&nbsp;</td>
    <% } %>
    <td>
   <html:button property="action" styleClass="store_fb" onclick="actionSubmit(0,'Search');">
    <app:storeMessage key="global.action.label.search"/>
   </html:button>
<% } %>
<% if (appUser != null && appUser.canEditShipTo()) {%>
<% if ( appUser.getSiteNumber() < 1 ) {  %>
<span style="text-align: left;">
<br> <app:storeMessage key="msbSites.text.createShipTo"/><br>
</span>
<% } %>
<html:button styleClass="store_fb" property="action"
    onclick="javascript: window.location='addshipto.do?action=addShipTo'">
  <app:storeMessage key="msbSites.text.addShipTo"/>
</html:button>
<% } %>

<% if (appUser != null &&  appUser.getSiteNumber() > 1 /* && !appUser.isaCustomer()*/) { %>
    </td>
    </tr>
    </table>
<% } %>
  <html:hidden  property="action" value="BBBBBBB"/>
    </html:form>

</table>

<logic:present name="msb.site.vector">
<% if (!isaStartPage) { %>
<bean:size id="rescount"  name="msb.site.vector"/>

<table align=center CELLSPACING=0 CELLPADDING=5
  width="<%=Constants.TABLEWIDTH%>" class="tbstd"
  style="{border-bottom: black 1px solid; border-top: black 1px solid;}" >
<tr align=left>
<td class="shopcharthead"><div class="fivemargin">
<a href="msbsites.do?action=sort_sites&sortField=name"><app:storeMessage key="msbSites.text.siteName"/></a></div></td>
<td class="shopcharthead"><div class="fivemargin">
<a href="msbsites.do?action=sort_sites&sortField=address"><app:storeMessage key="msbSites.text.streetAddress"/></a></div></td>
<td class="shopcharthead"><div class="fivemargin">
<a href="msbsites.do?action=sort_sites&sortField=city"><app:storeMessage key="msbSites.text.city"/></a></div></td>
<% if (userStore.isStateProvinceRequired())  { %>
<td class="shopcharthead"><div class="fivemargin">
<a href="msbsites.do?action=sort_sites&sortField=state"><app:storeMessage key="msbSites.text.stateProvince"/></a></div></td>
<%} %>
</tr>

<logic:iterate id="arrele" name="msb.site.vector" indexId="i">
 <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
<td>
<bean:define id="eleid" name="arrele" property="id"/>
<a href="msbsites.do?action=shop_site&siteId=<%=eleid%>">
<bean:write name="arrele" property="name"/>
</a>
</td>
<td>
<bean:write name="arrele" property="address"/>
</td>
<td>
<bean:write name="arrele" property="city"/>
</td>
<% if (userStore.isStateProvinceRequired())  { %>
<td>
  <bean:write name="arrele" property="state"/>
</td>
<%}%>
</tr>

</logic:iterate>
</table>
<%} %>
</logic:present>
</td>
</tr>
</table>

<logic:notPresent name="msb.site.vector">
<jsp:include flush='true'
   page='<%=ClwCustomizer.getStoreFilePath(request,"f_customer_messages.jsp")%>'/>
</logic:notPresent>

<logic:present name="msb.site.vector">
<% if (isaStartPage) { %>
<jsp:include flush='true'
   page='<%=ClwCustomizer.getStoreFilePath(request,"f_customer_messages.jsp")%>'/>
<% } %>
</logic:present>


<jsp:include flush='true'
   page='<%=ClwCustomizer.getStoreFilePath(request,"f_table_bottom1.jsp")%>'/>

<script type="text/javascript" language="JavaScript">
<!--
function kH(e) {
  var keyCode = window.event.keyCode;
  if(keyCode==13) {
    actionSubmit(0,'Search');
  }
}

document.onkeypress = kH;
-->
</script>
