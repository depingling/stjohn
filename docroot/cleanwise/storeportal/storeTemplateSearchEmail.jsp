<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="myForm" name="STORE_TEMPLATE_FORM" type="com.cleanwise.view.forms.StoreTemplateForm"/>

<html:form styleId="1038" action="storeportal/storeTemplate.do">

	<!-- create hidden fields for the template type and store -->
	<html:hidden property="templateData.type"/>
	<html:hidden property="templateData.busEntityId"/>
	
	<div class="text">
		<table ID=1037 width="769" cellspacing="0" border="0" class="mainbody">
  			<tr> 
  				<td>
  					<b>Find Template:&nbsp;</b>
  				</td>
       			<td colspan="3">
      				<html:text property="searchField"/>
       			</td>
  			</tr>
  			<tr> 
  				<td>
  					&nbsp;
  				</td>
       			<td colspan="3">
         			<html:radio property="searchType" value="id"/>&nbsp;ID
         			<html:radio property="searchType" value="nameBegins"/>&nbsp;Name(starts with)
         			<html:radio property="searchType" value="nameContains"/>&nbsp;Name(contains)
         		</td>
 			</tr>
			<tr> 
				<td>
					&nbsp;
				</td>
       			<td colspan="3">
  					<html:submit property="userAction">
    					<app:storeMessage  key="global.action.label.search"/>
  					</html:submit>
  					<html:submit property="userAction">
    					<app:storeMessage  key="admin.button.create"/>
  					</html:submit>
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

		<logic:present name="<%=Constants.ATTRIBUTE_FOUND_TEMPLATE_VECTOR%>">
			<bean:size id="count" name="<%=Constants.ATTRIBUTE_FOUND_TEMPLATE_VECTOR%>"/>
			Count:&nbsp;<bean:write name="count"/>
			<table ID=1039 cellspacing="0" border="0" width="769"  class="stpTable_sortable">
				<thead>
					<tr class="stpTH">
						<th class="stpTH">Template&nbsp;Id&nbsp;</th>
						<th class="stpTH">Template&nbsp;Name&nbsp;</th>
						<th class="stpTH">Locale/Language&nbsp;</th>
					</tr>
				</thead>
				<logic:iterate id="template" name="<%=Constants.ATTRIBUTE_FOUND_TEMPLATE_VECTOR%>"
					type="com.cleanwise.service.api.value.TemplateDataExtended">
					<tr class="stpTD">
						<td class="stpTD" align="center">
							<bean:write name="template" property="templateId"/>
						</td>
						<td class="stpTD" align="center">
							<%
								StringBuilder href = new StringBuilder(50);
								href.append("storeTemplate.do?userAction=getDetail");
								href.append("&templateData.type=");
								href.append(template.getType());
								href.append("&templateData.busEntityId=");
								href.append(template.getBusEntityId());
								href.append("&templateData.templateId=");
								href.append(template.getTemplateId());
							%>
							<a ID=1043 href="<%=href.toString()%>">
								<bean:write name="template" property="name"/>
							</a>
						</td>
						<td class="stpTD" align="center">
							<%
								StringBuilder property = new StringBuilder(50);
								property.append("property(");
								property.append(Constants.TEMPLATE_EMAIL_PROPERTY_LOCALE);
								property.append(").value");
							%>
							<bean:write name="template" property="<%=property.toString()%>"/>
						</td>
					</tr>
				</logic:iterate>
			</table>
		</logic:present>
	</div>
</html:form>