<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>


<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table cellspacing="0" border="0" class="mainbody" width="769">
<html:form action="/adminportal/blanketPoConfig.do">
 <table>
  <tr> <td><b>Find</b></td>
       <td> 
          <html:select name="BLANKET_PO_FORM" property="configType">
	     <html:option value="Store">Store</html:option>
	     <html:option value="Account">Account</html:option>
             <html:option value="Site">Site</html:option>
          </html:select>
       </td>
       <td colspan="2"> 
	  <html:text name="BLANKET_PO_FORM" property="searchField"/>	
       </td>
  </tr>
  
  <tr> <td colspan="2">&nbsp;</td>
       <td colspan="3">
         <html:radio name="BLANKET_PO_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="BLANKET_PO_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>  
    
  <tr> <td colspan="2">&nbsp;</td>
       <td colspan="3">
        <html:hidden property="siteconfig" value="true"/>
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
     </td>
  </tr>

  <tr><td colspan="4">&nbsp;</td>
  </tr>
  
</table>


<logic:present name="BLANKET_PO_FORM" property="results">
<bean:size id="rescount"  name="BLANKET_PO_FORM" property="results.values"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">

<table width="769"  class="results">
<tr align=left>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 1, false);">Id</th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 2, false);">Name</th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 3, false);">Address</th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 4, false);">City</th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 5, false);">State</th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 6, false);">Zip Code</th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 7, false);">Status</th>
<th>
	<a class="tableheader" href="javascript:SetCheckedGlobal(1,'results.selected')">[Check&nbsp;All]</a>
	<br>
	<a class="tableheader" href="javascript:SetCheckedGlobal(0,'results.selected')">[&nbsp;Clear]</a>
</th>
</tr>
<%-- display the sites --%>
<logic:equal name="BLANKET_PO_FORM" property="resultsType" value="Site">
<logic:iterate id="arrele" indexId="i" name="BLANKET_PO_FORM" property="results.values" type="com.cleanwise.service.api.value.SiteData">
<tr>
    
    <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
    <td>
      <bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
      <a href="sitemgr.do?action=sitedetail&searchType=id&searchField=<%=eleid%>">
      <bean:write name="arrele" property="busEntity.shortDesc"/>
      </a>
    </td>
    <td><bean:write name="arrele" property="siteAddress.address1"/></td>
    <td><bean:write name="arrele" property="siteAddress.city"/></td>
    <td><bean:write name="arrele" property="siteAddress.stateProvinceCd"/></td>
    <td><bean:write name="arrele" property="siteAddress.postalCode"/></td>
    <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
    <td>
      <%String prop = "results.selected["+i+"]";%>
      <html:multibox  name="BLANKET_PO_FORM" property="<%=prop%>" value="true"/>
    </td>
    
  </tr>
</logic:iterate>
</logic:equal>

<%-- display the accounts --%>
<logic:equal name="BLANKET_PO_FORM" property="resultsType" value="Account">
<logic:iterate id="arrele" indexId="i" name="BLANKET_PO_FORM" property="results.values" type="com.cleanwise.service.api.value.AccountSearchResultView">
<tr>
    
    <td><bean:write name="arrele" property="accountId"/></td>
    <td>
      <bean:define id="eleid" name="arrele" property="accountId"/>
      <a href="accountmgr.do?action=accountdetail&searchType=id&searchField=<%=eleid%>">
      <bean:write name="arrele" property="shortDesc"/>
      </a>
    </td>
    <td><bean:write name="arrele" property="address1"/></td>
    <td><bean:write name="arrele" property="city"/></td>
    <td><bean:write name="arrele" property="stateProvinceCd"/></td>
    <td><bean:write name="arrele" property="postalCode"/></td>
    <td><bean:write name="arrele" property="busEntityStatusCd"/></td>
    <td>
      <%String prop = "results.selected["+i+"]";%>
      <html:multibox  name="BLANKET_PO_FORM" property="<%=prop%>" value="true"/>
    </td>
    
  </tr>
</logic:iterate>
</logic:equal>

<%-- display the stores --%>
<logic:equal name="BLANKET_PO_FORM" property="resultsType" value="Store">
<logic:iterate id="arrele" indexId="i" name="BLANKET_PO_FORM" property="results.values" type="com.cleanwise.service.api.value.StoreData">
<tr>
    
    <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
    <td>
      <bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
      <a href="storemgr.do?action=storedetail&searchType=id&searchField=<%=eleid%>">
      <bean:write name="arrele" property="busEntity.shortDesc"/>
      </a>
    </td>
    <td><bean:write name="arrele" property="primaryAddress.address1"/></td>
    <td><bean:write name="arrele" property="primaryAddress.city"/></td>
    <td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
    <td><bean:write name="arrele" property="primaryAddress.postalCode"/></td>
    <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
    <td>
      <%String prop = "results.selected["+i+"]";%>
      <html:multibox  name="BLANKET_PO_FORM" property="<%=prop%>" value="true"/>
    </td>
    
  </tr>
</logic:iterate>
</logic:equal>


<%-- end rendering of detail --%>
<td colspan="6"></td>
<td>
<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>
</td>
</table>

</logic:greaterThan>
</logic:present>

</html:form>
</table>
</div>
