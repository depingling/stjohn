<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="freightTableType" name="FREIGHT_TABLE_DETAIL_FORM" property="detail.freightTableTypeCd" />

<%
	String createfrom = new String("");
	createfrom = request.getParameter("createfrom");
	if(null == createfrom) {
		createfrom = new String("");
	}

%>

<html:html>

<head>
<title>Application Administrator Home: Freight Tables</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>

<body bgcolor="#FFFFFF">

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admFreightTableToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

	
<div class="text">
<font color=red>
<html:errors/>
</font>

<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form name="FREIGHT_TABLE_DETAIL_FORM" action="/adminportal/freighttabledetail.do" focus='elements["detail.shortDesc"]'
	type="com.cleanwise.view.forms.FreightTableMgrDetailForm">

  	<tr> 
    	<td colspan="4" class="largeheader">Freight/Handling Table Detail</td>
	</tr>		
		
    <tr> 
    	<td><b>Freight/Handling Table ID:</b><html:hidden name="FREIGHT_TABLE_DETAIL_FORM" property="detail.freightTableId" /></td>
        <td><bean:write name="FREIGHT_TABLE_DETAIL_FORM" property="detail.freightTableId" filter="true"/>
			<input type="hidden" name="createfrom" value="<%=createfrom%>">
		</td>
        <td><b>Freight/Handling Table Name:</b></td>
        <td> 
        	<html:text name="FREIGHT_TABLE_DETAIL_FORM" property="detail.shortDesc" size="30" maxlength="30"/>	
            <span class="reqind">*</span>
        </td>
    </tr>
    
	<tr> 
        <td><b>Freight/Handling Table Type:</b></td>
        <td> 
			<html:hidden property="change" value="" />
        	<html:select name="FREIGHT_TABLE_DETAIL_FORM" property="detail.freightTableTypeCd" onchange="document.forms[0].change.value='type'; document.forms[0].submit();">
                                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				<html:options  collection="FreightTable.type.vector"
					property="value" />
            </html:select>
			<span class="reqind">*</span>
        </td>	
        <td><b>Freight/Handling Table Status:</b></td>
        <td> 
        	<html:select name="FREIGHT_TABLE_DETAIL_FORM" property="detail.freightTableStatusCd">
                                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				<html:options  collection="FreightTable.status.vector"
					property="value" />
            </html:select>
			<span class="reqind">*</span>
        </td>		
    </tr>
              	
	
	<tr>
		<td colspan="4">


<table width="769" border="1" class="results">
<tr>
<td colspan="3"><b>Freight/Handling Table Criteria:</b>
<bean:size id="criteriaCount" name="FREIGHT_TABLE_DETAIL_FORM" property="orgCriteriaList" />
<bean:write name="criteriaCount" />
</td>
</tr>

<logic:present name="FREIGHT_TABLE_DETAIL_FORM" property="criteriaList">

<% 
  				String valueLabelSign = new String("");
				String freightLabelSign = new String("");
	  			String valueSign = new String("");
				String freightSign = new String ("");
  				String percentageSign = new String("");	

			if( ! "".equals(freightTableType))		{		
	  			if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS.equals(freightTableType)) {
					valueLabelSign = "Dollars $";
					freightLabelSign = "$";
	  				valueSign = "$";
					freightSign = "$";
					percentageSign = "";
			/*	} else if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.WEIGHT.equals(freightTableType)) {
					valueLabelSign = "Weight #";
					freightLabelSign = "$";
  					valueSign = "";
					freightSign = "$";
					percentageSign = "";
                        */					
				} else if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS_PERCENTAGE.equals(freightTableType)) {
					valueLabelSign = "Dollars $";
					freightLabelSign = "%";
  					valueSign = "$";
					freightSign = "";
					percentageSign = "%";					
                       /*	 	} else if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.WEIGHT_PERCENTAGE.equals(freightTableType)) {
					valueLabelSign = "Weight #";
					freightLabelSign = "%";
					valueSign = "";
					freightSign = "";
					percentageSign = "%";	
                      */				
				}	
%>
<tr>
<td colspan="2" align="center"><b><%=valueLabelSign%></b></td>
<td><b>Freight <%=freightLabelSign%></b></td>
<td><b>Handling <%=freightLabelSign%></b></td>
</tr>
<tr>
<td><b>Begin <%=valueLabelSign%></b></td>
<td><b>End <%=valueLabelSign%></b></td>
<td><b>&nbsp;</b></td>
<td><b>&nbsp;</b></td>
</tr>
<% } else  {
  	valueSign = "";
	freightSign = "";
	percentageSign = "";
%>
<tr>
<td><b>Begin #</b></td>
<td><b>End #</b></td>
<td><b>Freight #</b></td>
<td><b>Handling #</b></td>
</tr>
<% }  %>


<logic:iterate id="criteriale" indexId="i" name="FREIGHT_TABLE_DETAIL_FORM" property="criteriaList" scope="session" type="com.cleanwise.service.api.value.FreightTableCriteriaDescData">

<tr>

<html:hidden 
  property='<%= "criteria[" + i + "].freightTableCriteriaId" %>'/>

<td><b><%=valueSign%></b>
<html:text size="15" maxlength="12" property='<%= "criteria[" + i + "].lowerAmount" %>'/>
</td>
<td><b><%=valueSign%></b>
<html:text size="15" maxlength="12" property='<%= "criteria[" + i + "].higherAmount" %>'/>
</td>
<td><b><%=freightSign%></b>
<html:text size="15" maxlength="12" property='<%= "criteria[" + i + "].freightAmount" %>'/>
<b><%=percentageSign%></b>
</td>
<td><b><%=freightSign%></b>
<html:text size="15" maxlength="12" property='<%= "criteria[" + i + "].handlingAmount" %>'/>
<b><%=percentageSign%></b>
</td>

</tr>

</logic:iterate>
</logic:present>


</table>

		</td>
	</tr>

    <tr> 
    	<td colspan="4" align="center"> 
			<html:reset>
        		<app:storeMessage  key="admin.button.reset"/>
      		</html:reset>
			<html:submit property="action"><app:storeMessage  key="global.action.label.save"/></html:submit>
			<html:submit property="action"><app:storeMessage  key="admin.button.addMoreCriteria"/></html:submit>
       </td>
    </tr>
	
	
</html:form>	
</table>

</div>

<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>
</html:html>
