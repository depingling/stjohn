
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="IMGPath" type="java.lang.String" 
  name="pages.store.images"/>

<script language="JavaScript1.2">
<!--
function selectCategory(pLevel) {
  document.forms[0].action = "custAcctMgtReportCategoryItemSelect.do?action=select&level=" + pLevel;  
  return document.forms[0].submit();
}

//checks or unchecks all checkboxes on a page
function checkAllBoxes()
  {                              
    len = document.forms[0].elements.length;
	
    var i=0;
	if(document.forms[0].checkAllBox.checked==true){	  
	  for( i=0; i<len; i++)
        document.forms[0].elements[i].checked=true;		
	}
	else{
	  for( i=0; i<len; i++)
        document.forms[0].elements[i].checked=false;		                                                  
    }
}

function itemReportSubmit() {
	var selected = false;
	
    len = document.forms[0].elements.length;	
    for( i=0; i<len; i++) {
		if(true == document.forms[0].elements[i].checked
			&& document.forms[0].elements[i].name != "checkAllBox"
			&& document.forms[0].elements[i].type == "checkbox") {
			selected = true;
			break;
		}
	}
	
	if (true == selected) {	
		return document.forms[0].submit();
	}
	else {
		return alert("Please select one or more product(s).");
	}
}

//-->
</script>

  
<div class="text"><font color=red><html:errors/></font></div>

<TABLE cellSpacing=0 cellPadding=0 width=769 align=center border=0>
  <TBODY>

  <TR>
    <TD bgColor=black colSpan=4><IMG height=1 
      src="<%=IMGPath%>/cw_spacer.gif" width=769></TD></TR>
      </TBODY>
      </TABLE>

<TABLE cellSpacing=0 cellPadding=0 width=769 align=center border=0>
  <TBODY>
  
  <bean:define id="toolBarTab" type="java.lang.String" value="default" toScope="request"/>
  <jsp:include flush='true' page="f_custAcctMgtToolbar.jsp"/>  
  
  <TR>
    <TD class=tableoutline width=1><IMG height=1 
      src="<%=IMGPath%>/cw_spacer.gif" width=1></TD>
    <TD class=top3dk width=5><IMG height=1 
      src="<%=IMGPath%>/cw_spacer.gif" width=5></TD>
    <TD class=text vAlign=top width=757>
    <br><br>
      <SPAN class=fivemargin>

		<html:form name="CUSTOMER_REPORT_FORM" action="/userportal/custAcctMgtReportCategoryItemSelect.do?action=next"
			type="com.cleanwise.view.forms.CustAcctMgtReportForm">
	  
      <TABLE cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR><TD rowspan=3>&nbsp;</TD></TR>
					
        <TR>		
          <TD width=151><IMG height=21 
            src="<%=IMGPath%>/cw_account_selectlocation.gif" 
            width=151></TD>
          <TD width=1><IMG height=21 
            src="<%=IMGPath%>/cw_account-formspacer.gif" 
            width=1></TD>
		
          <TD width=138><IMG height=21 
            src="<%=IMGPath%>/cw_account_selectdaterange.gif" 
            width=138></TD>
          <TD width=1><IMG height=21 
            src="<%=IMGPath%>/cw_account-formspacer.gif" 
            width=1></TD>

          <TD width=390><IMG height=21 
            src="<%=IMGPath%>/cw_account_selectcategory.gif" 
            width=390></TD>			
		</TR>		
			
        <TR>
          <TD class=text valign="top" width="151">
            <DIV class=fivemargin>
				<br>Current Location: <br>
				<logic:iterate id="selectedSite" name="CUSTOMER_REPORT_FORM" property="selectedSiteList"
						type="com.cleanwise.service.api.value.SiteData"> 
				<b>
					<bean:write name="selectedSite" property="siteAddress.address1" filter="true"/><br>
					<bean:write name="selectedSite" property="siteAddress.address2" filter="true"/><br><br>												
				</b>
				</logic:iterate>
				</DIV></TD>
          <TD width=1 background="<%=IMGPath%>/cw_account-dot.gif">
		  	<IMG height=1 src="<%=IMGPath%>/cw_spacer.gif" width=1></TD>

          <TD class=text valign="top" width=138>
            <DIV class=fivemargin>
				<br>Date Range:<br>
				<bean:define id="beginDate" name="CUSTOMER_REPORT_FORM" property="beginDate" />
				<bean:define id="endDate" name="CUSTOMER_REPORT_FORM" property="endDate" />
				<b><i18n:formatDate value="<%=beginDate%>" pattern="MMMMMMMM dd yyyy"/>
				</b> to <b><i18n:formatDate value="<%=endDate%>" pattern="MMMMMMMM dd yyyy"/>
				</b>
            </DIV></TD>
          <TD width=1 background="<%=IMGPath%>/cw_account-dot.gif">
		  	<IMG height=1 src="<%=IMGPath%>/cw_spacer.gif" width=1></TD>
			
          <TD class=text valign=top width=390>
            <DIV class=fivemargin><br>
				<bean:define id="topCategories" name="CUSTOMER_REPORT_FORM" property="topCategoryList" />
				<html:select name="CUSTOMER_REPORT_FORM" property="selectedTopCategoryId" onchange="return selectCategory(0);">
					<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
			        <html:options collection="topCategories" 
                             				property="catalogCategoryId" labelProperty="catalogCategoryShortDesc" />											
				</html:select><br><br>									

				<logic:iterate id="childCatList" indexId="i" name="CUSTOMER_REPORT_FORM" 
						property="childCategoryListList" scope="session" type="com.cleanwise.service.api.value.CatalogCategoryDataVector">
					<% 
					 	int level = i.intValue() + 1;
						String selectString = "return selectCategory(" + level + ");"; 
					%>	
					<html:select name="CUSTOMER_REPORT_FORM" property='<%= "childCategoryId[" + i + "]" %>' onchange="<%=selectString%>">
						<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				        <html:options collection="childCatList" 
                             				property="catalogCategoryId" labelProperty="catalogCategoryShortDesc" />											
					</html:select><br><br>																					
				</logic:iterate>				

				<logic:equal name="CUSTOMER_REPORT_FORM" property="reportTypeCd" value="<%=RefCodeNames.CUSTOMER_REPORT_TYPE_CD.VOLUME_BY_ITEM%>">																					
					<bean:size id="itemCount" name="CUSTOMER_REPORT_FORM" property="productList" />
					<logic:greaterThan name="itemCount"  value="0">
						<b>Select one or more product(s) below and click submit</b><br><br>
					</logic:greaterThan>																					
					<logic:iterate id="item" indexId="i" name="CUSTOMER_REPORT_FORM" 
							property="productList" scope="session" type="com.cleanwise.service.api.value.ProductData">
						<html:multibox property="selectedItems">
							<bean:write name="item" property="productId"/>
						</html:multibox>	
						&nbsp;<bean:write name="item" property="shortDesc" filter="true"/><br>
					</logic:iterate>			
					<logic:greaterThan name="itemCount"  value="0">
						<br><input type="checkbox" name="checkAllBox" onClick="checkAllBoxes()">
						&nbsp;Select All<br>
					</logic:greaterThan>																					
				</logic:equal>
				
				<logic:equal name="CUSTOMER_REPORT_FORM" property="reportTypeCd" value="<%=RefCodeNames.CUSTOMER_REPORT_TYPE_CD.VOLUME_BY_CATEGORY%>">
					<logic:notEqual name="CUSTOMER_REPORT_FORM" property="selectedTopCategoryId" value="">																					
        	    		<P><A href="javascript:document.forms[0].submit();">
						<IMG height=18 src="<%=IMGPath%>/cw_submit.gif" width=58 border=0></A>
						</P>
					</logic:notEqual>
				</logic:equal>	

				<logic:equal name="CUSTOMER_REPORT_FORM" property="reportTypeCd" value="<%=RefCodeNames.CUSTOMER_REPORT_TYPE_CD.VOLUME_BY_ITEM%>">
					<bean:size id="itemCount" name="CUSTOMER_REPORT_FORM" property="productList" />
					<logic:greaterThan name="itemCount"  value="0">
        	    		<P><A href="javascript:itemReportSubmit();">
						<IMG height=18 src="<%=IMGPath%>/cw_submit.gif" width=58 border=0></A>
						</P>
					</logic:greaterThan>
				</logic:equal>	
				
			</DIV>
		  </TD>
	  
	          </TR>
            </TBODY>
            </TABLE>
      </html:form>
      </SPAN>
    </TD>
    <TD class=top3dk width=5><IMG height=250 
      src="<%=IMGPath%>/cw_spacer.gif" width=5></TD>
    <TD class=tableoutline width=1><IMG height=1 
      src="<%=IMGPath%>/cw_spacer.gif" width=1></TD></TR>
  <TR>
    <TD width=769 colSpan=5><!-- footer graphic --><IMG height=23 
      src="<%=IMGPath%>/cw_rootfooter.gif" width=769></TD>
      </TR>

</TBODY>
      
</TABLE>

