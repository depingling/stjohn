<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<%
  String portal = request.getParameter("portal"),
      contactType = request.getParameter("contactType"),
      contactId = request.getParameter("contactId");
  if ( null == contactId ) { contactId = "0"; } // new contact
%>


<html:hidden property="contactType" value="distributor"/>

  <table ID=289>
  <tr>
  <td rowspan=9>&nbsp;</td>
  <td><b>Contact information for <%=contactType%>.</b> <br>
  <b>Contact id: </b> <bean:write name="STORE_DIST_DETAIL_FORM" property="wrkContact.contactId"/></td>
</tr><tr>
  <td><b>Contact type: </b>
</td><td>
  <html:select name="STORE_DIST_DETAIL_FORM" property="wrkContact.contactTypeCd">

<html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.ACCOUNTS_RECEIVABLE%>'>Accounts Receivable</html:option>
<html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.CUSTOMER_SERVICE%>'>Customer Service</html:option>
<html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.GPREP%>'>GP Rep</html:option>
<html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.JDREP%>'>JD Rep</html:option>
<html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.KCREP%>'>KC Rep</html:option>
<html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.OPERATIONS%>'>Operations</html:option>
<html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.OTHER%>'>Other</html:option>
<html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.OWNER%>'>Owner</html:option>
<html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.PRIMARY_CONTACT%>'>
Primary Contact</html:option>
<html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.PURCHASING%>'>Purchasing</html:option>
<html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.SALES%>'>Sales</html:option>
<html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.TECHNOLOGY%>'>Technology</html:option>

  </html:select>
  </td>
</tr>



<tr>
  <td><b>First&nbsp;Name:</b></td>
<td>
 <html:text name="STORE_DIST_DETAIL_FORM" property="wrkContact.firstName"
  size='15' /></td>
  <td><b>Last&nbsp;Name:</b> </td><td>
<html:text name="STORE_DIST_DETAIL_FORM" property="wrkContact.lastName"
  size='20' /></td>
  </tr>
  <tr>
  <td><b>Phone: </b> </td>
<td><html:text name="STORE_DIST_DETAIL_FORM" property="wrkContact.phone"
  size='20' /></td>
  </tr>
  <tr>
  <td><b>Cell.Phone: </b> </td>
<td> <html:text name="STORE_DIST_DETAIL_FORM" property="wrkContact.cellPhone"
  size='20' /></td>
  </tr>
  <tr>
  <td><b>Fax: </b> </td>
<td><html:text name="STORE_DIST_DETAIL_FORM" property="wrkContact.fax"
size='20' /></td>
  </tr>
  <tr>
  <td><b>eMail: </b></td>
<td> <html:text name="STORE_DIST_DETAIL_FORM" property="wrkContact.email"
  size='20' /></td>
  </tr>


<td colspan='2'><html:submit property="action">Save Contact</html:submit></td>
<td colspan='2'>
<%
String delContactHref="distdet.do?action=Delete Contact&contactId="
	+contactId;

if(null != contactId
  && !contactId.trim().equals("0"))  {%>
  <a ID=290 href="<%=delContactHref%>">[ Delete ]</a>
<%} else {%>&nbsp;<%}%></td>

  </table>



