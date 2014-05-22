<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">
  <table ID=1037 width="769" cellspacing="0" border="0" class="mainbody">
  <html:form styleId="1038" name="STORE_MANUF_SEARCH_FORM" action="storeportal/manufmgr.do"
    scope="session" type="com.cleanwise.view.forms.StoreManufMgrSearchForm">
  <tr> <td><b>Find Manufacturer:</b></td>
       <td colspan="3">
      <html:text name="STORE_MANUF_SEARCH_FORM" property="searchField"/>
       </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
         <html:radio name="STORE_MANUF_SEARCH_FORM" property="searchType" value="id" />
         ID
         <html:radio name="STORE_MANUF_SEARCH_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="STORE_MANUF_SEARCH_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
  <html:submit property="action">
    <app:storeMessage  key="global.action.label.search"/>
  </html:submit>
  <html:submit property="action">
    <app:storeMessage  key="admin.button.create"/>
  </html:submit>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Show Inactive Manufacturers
  <html:checkbox name="STORE_MANUF_SEARCH_FORM" property="searchShowInactiveFl"/>
    </td>
  </tr>
</table>

  <script type="text/javascript" language="JavaScript">
  <!--
     dml=document.forms;
     for(i=0; i<dml.length; i++) {
      ellen = dml[i].elements.length;
      for(j=0; j<ellen; j++) {
        if (dml[i].elements[j].name=='searchField') {
          dml[i].elements[j].focus();
          break;
        }
      }
     }
  // -->
  </script>

<logic:present name="StoreManuf.found.vector">
<bean:size id="rescount"  name="StoreManuf.found.vector"/>
Count:  <bean:write name="rescount" />

<table ID=1039 cellspacing="0" border="0" width="769"  class="stpTable_sortable">
<tr>
<th class="stpTH"><a ID=1040 href="manufmgr.do?action=sort&sortField=id">Manufacturer&nbsp;Id</a></td>
<th class="stpTH"><a ID=1041 href="manufmgr.do?action=sort&sortField=name">Name</a></td>
<th class="stpTH"><a ID=1042 href="manufmgr.do?action=sort&sortField=status">Status</a></td>
</tr>

<logic:iterate id="arrele" name="StoreManuf.found.vector">
<tr>
<td class="stpTD"><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td class="stpTD">
<bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
<a ID=1043 href="manufdet.do?action=manufdetail&searchType=id&searchField=<%=eleid%>">
<bean:write name="arrele" property="busEntity.shortDesc"/>
</a>
</td>
<td class="stpTD"><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
</tr>

</logic:iterate>
</table>
</logic:present>

</div>
</html:form>





