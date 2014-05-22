
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>
<bean:define id="properAction" name="SELECTSHIPPINGADDRESS" property="properAction"/>

<div class="bodyalt">

	<!--Get addresses from database, output-->
	<br><br>
<html:form name="SelectShippingAddress" 
 action="userportal/selectshippingaddress.do" focus="elements[0]"
 type="com.cleanwise.view.forms.SelectShippingAddressForm">

	<br>
	<div class="text"><font color=red><html:errors/></font></div>
	<br>
        <table align="center" border="0" cellspacing="0" cellpadding="0" width="422">
            <tr>
             <td height="1" width="7"><img src='<%=ClwCustomizer.getSIP(request,"cw_logintopleft.gif")%>' WIDTH="7" HEIGHT="1"></td>
             <td bgcolor="#003366" height="1" width="408"><img src='<%=ClwCustomizer.getSIP(request,"spacer.gif")%>' height="1" width="408"></td>
             <td height="1" width="7"><img src='<%=ClwCustomizer.getSIP(request,"cw_logintopright.gif")%>' WIDTH="7" HEIGHT="1"></td>
            </tr>
      <tr>
        <td width="7"><img src='<%=ClwCustomizer.getSIP(request,"cw_loginleftlogo.gif")%>' WIDTH="7" HEIGHT="53"></td>
        <td align="left"  width="408">
        <table align="left" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src='<%=ClwCustomizer.getSIP(request,"cw_loginlogo.gif")%>' WIDTH="260" HEIGHT="53"></td>
           <td class="text"><app:storeMessage key="logo.slogan"/></td>
        </tr>
        </table>
        </td>
        <td width="7"><img src='<%=ClwCustomizer.getSIP(request,"cw_loginrightlogo.gif")%>' WIDTH="7" HEIGHT="53"></td>
      </tr>
            <tr>
              <td width="7"><img src='<%=ClwCustomizer.getSIP(request,"cw_logincontentleft.gif")%>' WIDTH="7" HEIGHT="300"></td>
              <td bgcolor="#FFFFFF" width="408" valign="middle">
              <table align="center" border="0" cellspacing="0" cellpadding="0" width="350">
                  <tr>
                  <td></td>
                  </tr>
                    <tr>
                      <td><span class="subheadergeneric">Login:</span></td>
                  	</tr>
                  	<tr>
                      <td>&nbsp;</td>
                  	</tr>
                  	<tr>
                      <td class="text">Welcome,
                      <b>
                      <bean:write name="SELECTSHIPPINGADDRESS" property="appUser.user.firstName"/>
                      <bean:write name="SELECTSHIPPINGADDRESS" property="appUser.user.lastName"/>
                      </b>.
                      <br>Please choose your shipping address for this session:
                      </td>
                  	</tr>
                  	<tr>
                      <td>&nbsp;</td>
                  	</tr>
                  	<tr>
                   		<td class="text">

         <html:select property="shippingAddressKey" onchange="DoUpdateHome()">
  	  	 <html:option value="-1">-Select Address-</html:option>


<logic:iterate id="site" name="SELECTSHIPPINGADDRESS" property="sites"
  offset="0" indexId="vSiteIdx"
   type="com.cleanwise.service.api.value.SiteData">

  <html:option value="<%=vSiteIdx.toString()%>">

<logic:equal name="SELECTSHIPPINGADDRESS" property="multiAcctFlag" 
  value="true">
<bean:write name="site" property="accountBusEntity.shortDesc" filter="true"/>.
</logic:equal>

<bean:write name="site" property="busEntity.shortDesc" filter="true"/>.
<bean:write name="site" property="siteAddress.address1" filter="true"/>
</option>
</html:option>

          </logic:iterate>
         </html:select>

         </td>
		 </tr>



		 <tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td class="text"><b>Note:</b> You will be able to select additional shipping addresses once you enter MyCleanwise.<BR><BR></td>
		</tr>
		<tr>
			<td><input type=hidden name="action" value="submit">
			</td>
		</tr>

	<logic:present name="SELECTSHIPPINGADDRESS" property="appUser.site">
	    <tr valign="top">
            <td><b><span class="subheadergeneric">Your selected shopping address is:</span></b><hr size="1"></hr>
            </td>
        </tr>
		<tr valign="top" class="text"><td class="text">
           <bean:write name="SELECTSHIPPINGADDRESS" property="appUser.site.siteAddress.address1" filter="true"/>
        </td></tr>
    	<logic:present name="SELECTSHIPPINGADDRESS" property="appUser.site.siteAddress.address2">
    	<logic:notEqual name="SELECTSHIPPINGADDRESS" property="appUser.site.siteAddress.address2" value="">
  		  <tr valign="top" class="text"><td class="text">
          <bean:write name="SELECTSHIPPINGADDRESS" property="appUser.site.siteAddress.address2" filter="true"/>
          </td></tr>
        </logic:notEqual>
        </logic:present>
    	<logic:present name="SELECTSHIPPINGADDRESS" property="appUser.site.siteAddress.address3">
    	<logic:notEqual name="SELECTSHIPPINGADDRESS" property="appUser.site.siteAddress.address3" value="">
  		  <tr valign="top" class="text"><td class="text">
          <bean:write name="SELECTSHIPPINGADDRESS" property="appUser.site.siteAddress.address3" filter="true"/>
          </td></tr>
        </logic:notEqual>
        </logic:present>
    	<logic:present name="SELECTSHIPPINGADDRESS" property="appUser.site.siteAddress.address4">
    	<logic:notEqual name="SELECTSHIPPINGADDRESS" property="appUser.site.siteAddress.address4" value="">
  		  <tr valign="top" class="text"><td class="text">
          <bean:write name="SELECTSHIPPINGADDRESS" property="appUser.site.siteAddress.address4" filter="true"/>
          </td></tr>
        </logic:notEqual>
        </logic:present>
		<tr valign="top" class="text"><td class="text">
           <bean:write name="SELECTSHIPPINGADDRESS" property="appUser.site.siteAddress.city" filter="true"/>,
           <bean:write name="SELECTSHIPPINGADDRESS" property="appUser.site.siteAddress.stateProvinceCd" filter="true"/>
           <bean:write name="SELECTSHIPPINGADDRESS" property="appUser.site.siteAddress.postalCode" filter="true"/>
        </td></tr>
  		  <tr valign="top" class="text"><td class="text">
          <bean:define id="countryCode" name="SELECTSHIPPINGADDRESS" property="appUser.site.siteAddress.countryCd"/>
          <%=com.cleanwise.view.utils.Constants.getCountryName((String)countryCode)%>
          </td></tr>
		<tr><td>&nbsp;</td><tr>
 		<tr valign="top">
			<td class="text">
            <input type="image" SRC='<%=ClwCustomizer.getSIP(request,"cw_usethisaddress.gif")%>' BORDER="0"><br><br>
			</td>
		</tr>
  	 </logic:present>

           </table>
          </td>
          <td width="7"><img src='<%=ClwCustomizer.getSIP(request,"cw_logincontentright.gif")%>' WIDTH="7" HEIGHT="300"></td>
      </tr>
      <tr>
       <td width="7"><img src='<%=ClwCustomizer.getSIP(request,"cw_loginbl.gif")%>' WIDTH="7" HEIGHT="17"></td>
       <td bgcolor="#003366" width="408"><img src='<%=ClwCustomizer.getSIP(request,"spacer.gif")%>' height="17" width="1"></td>
       <td width="7"><img src='<%=ClwCustomizer.getSIP(request,"cw_loginbr.gif")%>' WIDTH="7" HEIGHT="17"></td>
      </tr>
     </table>
   
</html:form>

</div>



<script language="JavaScript">

function DoUpdateHome()  {
    document.forms["SELECTSHIPPINGADDRESS"].action.value = "select"
    document.forms["SELECTSHIPPINGADDRESS"].submit();
}


</script>

