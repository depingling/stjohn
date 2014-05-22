<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% 
  String portal = request.getParameter("portal");
  boolean adminPortalFl = ("adminportal".equalsIgnoreCase(portal))?true:false;
  String upperLink = "distmgr.do";
  String thisLink = "distmgrDetail.do";
  boolean readOnlyFl = (adminPortalFl)?false:true;
  String disabledStr = (readOnlyFl)?"disabled='true'":"";
  
%>  


  <bean:define id="distId" name="DIST_DETAIL_FORM" property="id" />
  <html:hidden name="DIST_DETAIL_FORM" property="wrkAddress.BusEntityId" value="<%=distId.toString()%>"/>

  <table cellspacing="0" border="0" class="mainbody" align=right>
  <tr>
  
<td rowspan=8>&nbsp;</td>
  <td><b>Address id: </b></td>
<td> <bean:write name="DIST_DETAIL_FORM" property="wrkAddress.addressId"/></td>
</tr><tr>
  <td><b>Address1: </b></td>
<td>  <html:text name="DIST_DETAIL_FORM" property="wrkAddress.address1" readonly='<%=readOnlyFl%>' /></td>
</tr><tr>
  <td><b>Address2: </b></td>
<td>  <html:text name="DIST_DETAIL_FORM" property="wrkAddress.address2" readonly='<%=readOnlyFl%>' /></td>
</tr><tr>
  <td><b>Address3: </b></td>
<td>  <html:text name="DIST_DETAIL_FORM" property="wrkAddress.address3" readonly='<%=readOnlyFl%>' /></td>
  </tr>

  <tr>
  <td><b>City: </b></td><td>  <html:text name="DIST_DETAIL_FORM" property="wrkAddress.city" readonly='<%=readOnlyFl%>' /></td>
</tr><tr>
  <td><b>Country: </b></td><td>
  <html:select tabindex="23" name="DIST_DETAIL_FORM" property="wrkAddress.countryCd" disabled='<%=readOnlyFl%>'>
  <html:options  collection="countries.vector" property="value" />
  </html:select>
  </td>
</tr><tr>
  <td><b>State/Province:</b></td>
<td><html:text maxlength="80" name="DIST_DETAIL_FORM"  property="wrkAddress.stateProvinceCd" readonly='<%=readOnlyFl%>' />
  </td>
</tr><tr>
  <td><b>Zip/Postal Code:</b></td>
<td><html:text name="DIST_DETAIL_FORM" property="wrkAddress.postalCode" maxlength="15" readonly='<%=readOnlyFl%>' />
  </td>
  </tr>

  <tr><td>
<html:submit property="action">Save Branch Address</html:submit>
</td>
<bean:define id="thisAddrId" name="DIST_DETAIL_FORM" property="wrkAddress.addressId"/>
<%
  String delAddrHref= "distmgrDetail.do?action=Delete Branch Address&branchAddrId="
   +thisAddrId;
%>
<td><%if(!readOnlyFl && !thisAddrId.toString().equals("0") ){%>
<a href="<%=delAddrHref%>">[Delete]</a>
<%} else {%>&nbsp;<%}%>
</td>

</tr>


  </table>
