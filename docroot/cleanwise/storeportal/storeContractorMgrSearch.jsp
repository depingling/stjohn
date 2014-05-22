<%@ page language="java"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.service.api.value.*"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app"%>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session" />

<div class="text">
<table ID=1037 width="769" cellspacing="0" border="0" class="mainbody">
	<html:form styleId="1038" name="STORE_CONTRACTOR_SEARCH_FORM"
		action="storeportal/contractormgr.do" scope="session"
		type="com.cleanwise.view.forms.StoreContractorMgrSearchForm">
		<tr>
			<td><b>Find Contractor:</b></td>
			<td colspan="3"><html:text name="STORE_CONTRACTOR_SEARCH_FORM"
				property="searchField" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="3"><html:radio name="STORE_CONTRACTOR_SEARCH_FORM"
				property="searchType" value="id" /> ID <html:radio
				name="STORE_CONTRACTOR_SEARCH_FORM" property="searchType"
				value="nameBegins" /> Name(starts with) <html:radio
				name="STORE_CONTRACTOR_SEARCH_FORM" property="searchType"
				value="nameContains" /> Name(contains)</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="3"><html:submit property="action">
				<app:storeMessage  key="global.action.label.search" />
			</html:submit> <html:submit property="action">
				<app:storeMessage  key="admin.button.create" />
			</html:submit> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Show Inactive Contractors
			<html:checkbox name="STORE_CONTRACTOR_SEARCH_FORM"
				property="searchShowInactiveFl" /></td>
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
  </script> <logic:present name="list.all.bsc">
	<bean:size id="rescount" name="list.all.bsc" />
 Count:  <bean:write name="rescount" />
	<!-- 
<a href="bscmgr.do?action=bscdetail&searchType=id&searchField=0">
[ +Add a Building Services Contractor ]
</a>
-->

	<table cellspacing="0" border="0" width="769" class="results">
		<thead>
			<tr align=left>
				<th class="tt"><a href="#pgsort"
					onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);"
					title="-- Sort by Id">Id</a></th>
				<th class="tt"><a href="#pgsort"
					onclick="this.blur(); return f_sortTable('resTblBdy', 1, false);"
					title="-- Sort by Name">Name</a></th>
				<th class="tt"><a href="#pgsort"
					onclick="this.blur(); return f_sortTable('resTblBdy', 2, false);"
					title="-- Sort by Description">Description</a></th>
				<th class="tt"><a href="#pgsort"
					onclick="this.blur(); return f_sortTable('resTblBdy', 3, false);"
					title="-- Sort by Order Fax Number">Order Fax Number</a></th>
				<th class="tt"><a href="#pgsort"
					onclick="this.blur(); return f_sortTable('resTblBdy', 4, false);"
					title="-- Sort by Status">Status</a></th>
			</tr>
		</thead>

		<tbody id="resTblBdy">
			<logic:iterate id="arrele" name="list.all.bsc"
				type="com.cleanwise.service.api.value.BuildingServicesContractorView">

				<tr>
					<td><bean:write name="arrele"
						property="busEntityData.busEntityId" /></td>

					<td><a
						href="contractordet.do?action=contractordetail&searchType=id&searchField=<%=arrele.getBusEntityData().getBusEntityId()%>">
					<bean:write name="arrele" property="busEntityData.shortDesc" /> </a></td>

					<td><bean:write name="arrele"
						property="busEntityData.longDesc" /></td>

					<td>
					<%
						String faxnum = "";
									if (arrele.getFaxNumber() != null) {
										faxnum = arrele.getFaxNumber().getPhoneNum();
										if (faxnum == null)
											faxnum = "";
									}
					%> <%=faxnum%></td>

					<td><bean:write name="arrele"
						property="busEntityData.busEntityStatusCd" /></td>

				</tr>

			</logic:iterate>
		</tbody>
	</table>
</logic:present></div>
</html:form>





