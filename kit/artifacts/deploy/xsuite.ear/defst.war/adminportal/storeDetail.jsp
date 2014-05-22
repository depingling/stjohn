<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="storeId" name="STOREDETAIL" property="detail.busEntity.busEntityId" type="java.lang.Integer"/>

<html:html>
<head>
<title>Application Administrator Home: Stores</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>

<body bgcolor="#FFFFFF">
<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admStoreToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<font color=red>
<html:errors/>
</font>

<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
<html:form action="/adminportal/storedetail.do?action=save" focus='elements["detail.busEntity.longDesc"]'>
	<tr> 
  		<td colspan="4" class="largeheader"><b>Store Detail</b></td>	

  	<tr> 
    	<td><b>Store ID:</b><html:hidden name="STOREDETAIL" property="detail.busEntity.busEntityId" /></td>
	    <td><bean:write name="STOREDETAIL" property="detail.busEntity.busEntityId" filter="true"/></td>
	    <td><b>Description:</b></td>
    	<td> 
       		<html:text name="STOREDETAIL" property="detail.busEntity.longDesc" maxlength="256" size="30" />
		</td>
	</tr>
   	<tr> 
     	<td><b>Store prefix(three letters):</b></td>
     	<td> 
     		<html:text name="STOREDETAIL" property="detail.prefix.value" size="3" maxlength="3"/>	
	    </td>
     	<td><b>Name:</b></td>
     	<td> 
	 		<html:text name="STOREDETAIL" property="detail.busEntity.shortDesc" maxlength="30"/>	
			<span class="reqind">*</span>
     	</td>
   	</tr>
   	<tr>                 
   		<td><b>Type:</b></td>
        <td>
                <html:select name="STOREDETAIL" property="detail.storeType.value">
                <html:option value=""><bean:message key="admin.select"/></html:option>
                <html:options  collection="Store.type.vector" property="value" labelProperty="value" />
                </html:select>
				<span class="reqind">*</span>
        </td>
        <td><b>Status:</b></td>
	<td>
                <html:select name="STOREDETAIL" property="detail.busEntity.busEntityStatusCd">
                <html:option value=""><bean:message key="admin.select"/></html:option>
                <html:options  collection="Store.status.vector" property="shortDesc" labelProperty="value" />
                </html:select>
				<span class="reqind">*</span>
        </td>
        </tr>
        <tr> 
     		<td><b>Customer Service Email:</b></td>
            <td> 
            	<html:text name="STOREDETAIL" property="detail.customerServiceEmail.emailAddress" maxlength="255"/>	
				<span class="reqind">*</span>
            </td>
            <td><b>Default Locale:</b></td>
            <td> 
            	<html:select name="STOREDETAIL" property="detail.busEntity.localeCd">
                                        <html:option value=""><bean:message key="admin.select"/></html:option>
					<html:options  collection="locales.vector" property="value" labelProperty="shortDesc" />
                </html:select>
				<span class="reqind">*</span>
           	</td>
      	</tr>
       	<tr> 
        	<td><b>Contact Us Email:</b></td>
           	<td> 
              	<html:text name="STOREDETAIL" property="detail.contactUsEmail.emailAddress" maxlength="255"/>	
				<span class="reqind">*</span>
            </td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
        </tr>
		
		<tr>
        	<td colspan="4" class="largeheader"><br>Primary Contact</td>
		</tr>
        <tr> 
        	<td><b>First Name:</b></td>
            <td> 
				<html:text name="STOREDETAIL" property="detail.primaryAddress.name1" maxlength="30"/>		
				<span class="reqind">*</span>
            </td>
            <td><b>Phone:</b></td>
            <td> 
				<html:text name="STOREDETAIL" property="detail.primaryPhone.phoneNum" maxlength="30" />	
				<span class="reqind">*</span>
            </td>
      	</tr>
        <tr> 
        	<td><b>Last Name:</b></td>
            <td> 
              	<html:text name="STOREDETAIL" property="detail.primaryAddress.name2" maxlength="30"/>	
				<span class="reqind">*</span>
            </td>
        	<td><b>Fax:</b></td>
       		<td> 
				<html:text name="STOREDETAIL" property="detail.primaryFax.phoneNum" maxlength="30"/>	
				<span class="reqind">*</span>
        	</td>
     	</tr>
        <tr> 
        	<td><b>Street Address 1:</b></td>
            <td> 
            	<html:text name="STOREDETAIL" property="detail.primaryAddress.address1" maxlength="80" />	
				<span class="reqind">*</span>
            </td>
            <td><b>Email Address:</b></td>
            <td> 
              	<html:text name="STOREDETAIL" property="detail.primaryEmail.emailAddress" maxlength="255"/>	
            </td>
      	</tr>
       	<tr> 
        	<td><b>Street Address 2:</b></td>
            <td> 
            	<html:text name="STOREDETAIL" property="detail.primaryAddress.address2" maxlength="80"/>	
            </td>
           	<td><b>Country:</b></td>
            <td>
            	<html:select name="STOREDETAIL" property="detail.primaryAddress.countryCd">
                	<html:option value="">-Select Country-</html:option>
					<html:options  collection="countries.vector" property="value" />
                </html:select>
				<span class="reqind">*</span>
            </td>
     	</tr>
        <tr> 
         	<td><b>Street Address 3:</b></td>
           	<td> 
              	<html:text name="STOREDETAIL" property="detail.primaryAddress.address3" maxlength="80" />	
           	</td>
            <td><b>State/Province:</b></td>
            <td> 
<html:text size="20" maxlength="80" name="STOREDETAIL" 
  property="detail.primaryAddress.stateProvinceCd" />
<span class="reqind">*</span>
            </td>
      	</tr>
        <tr> 
           	<td><b>City:</b></td>
            <td> 
              	<html:text name="STOREDETAIL" property="detail.primaryAddress.city" maxlength="40" />	
				<span class="reqind">*</span>
            </td>
            <td><b>Zip/Postal Code:</b></td>
            <td>
             	<html:text name="STOREDETAIL" property="detail.primaryAddress.postalCode" maxlength="15" />	
				<span class="reqind">*</span>
            </td>
       	</tr>
		
		<tr>
			<td colspan="4">&nbsp;</td>
		</tr>
        <tr> 
         	<td colspan="4" align="center"> 
		      <html:reset>
        		<bean:message key="admin.button.reset"/>
      		  </html:reset>

<% 
	if( null != storeId && 0 != storeId.intValue() ) {		
 %>			
	              <input type="button" value="Save Store" onClick="return document.forms[0].submit();">
<% } else { %>
    	          <input type="button" value="Create Store" onClick="return document.forms[0].submit();">
<% } %>
          </td>
        </tr>
</html:form>  
</table>

</div>

<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>
</html:html>
