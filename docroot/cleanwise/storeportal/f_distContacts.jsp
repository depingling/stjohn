<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="theForm" name="STORE_DIST_DETAIL_FORM"
  type="com.cleanwise.view.forms.StoreDistMgrDetailForm"/>

<%
  int tabIndex = 0;
  Integer tabIndexInteger = (Integer)request.getAttribute("tabIndex");
  if (tabIndexInteger != null) {
	  tabIndex = tabIndexInteger.intValue();
  }
  String portal = request.getParameter("portal");
  if ( null == portal ) { portal = "adminportal"; }
  String thisAction = request.getParameter("action");

  String thisContactId = request.getParameter("contactId");
  String thisBranchId = request.getParameter("branchAddrId");
  if ( null == thisContactId ) { thisContactId = "0"; }
  if ( null == thisBranchId ) { thisBranchId = "0"; }

String contactType = request.getParameter("contactType"),
       contactId = request.getParameter("contactId");

String addBAddrHref = "distdet.do?action=Add Branch Address"
   + "&contactType=branch address&portal=adminportal";


%>

<%! int addrId = 0; %>


<table ID=291 class="adm_panel">

<tr>
<td class="largeheader"><b>Additional Contacts</td>
  <td>
<a ID=292 tabindex='<%=tabIndex++ + ""%>' href="distdet.do?action=Add Contact&contactId=0&contactType=distributor">
  [ Add a Distributor Contact ]</a>
</td>
</tr>
<%
 ContactViewVector additionalContacts = theForm.getAdditionalContacts();
 if(additionalContacts!=null && additionalContacts.size()>0) {
%>
<tr><td colspan='4'>
  <table ID=293 cellspacing="0" border="0" width='100%'  class="mainbody">
  <tr>
  <td class="tableheader">Id</td>
  <td class="tableheader">Contact Type</td>
  <td class="tableheader">Name</td>
  <td class="tableheader">Phone</td>
  <td class="tableheader">Cellular</td>
  <td class="tableheader">Fax</td>
  <td class="tableheader">Email</td>
  <td class="tableheader">&nbsp;</td>
  <td class="tableheader">&nbsp;</td>
  </tr>
<logic:iterate id="cVw" name="STORE_DIST_DETAIL_FORM" property="additionalContacts"
  type="com.cleanwise.service.api.value.ContactView"
  indexId="idx" >
  <%
  int cId = cVw.getContactId();
  String editContactHref="distdet.do?action=Edit Contact&contactId="+cId;
  String delContactHref="distdet.do?action=Delete Contact&contactId="+cId;
  %>
  <tr>

<td><a tabindex='<%=tabIndex++ + ""%>' ID=294 href="<%=editContactHref%>"><%=cId%></a></td>

  <td><bean:write name="cVw" property="contactTypeCd" /></td>
  <td><bean:write name="cVw" property="firstName" />  <bean:write name="cVw" property="lastName" /></td>
  <td><bean:write name="cVw" property="phone" /></td>
  <td><bean:write name="cVw" property="cellPhone" /></td>
  <td><bean:write name="cVw" property="fax" /></td>
  <td><bean:write name="cVw" property="email" /></td>

 </tr>
</logic:iterate>
  </table>
  <% } %>
  <% if(("Add Contact".equals(thisAction)
      || "Edit Contact".equals(thisAction))) { %>
  <bean:define id="distId" name="STORE_DIST_DETAIL_FORM" property="id" />
  <html:hidden name="STORE_DIST_DETAIL_FORM" property="wrkContact.busEntityId" value="<%=distId.toString()%>"/>
  <tr><td colspan='4'>

  <table ID=295>
  <tr>
  <td rowspan=9>&nbsp;</td>
  <td><b>Contact id: </b> <bean:write name="STORE_DIST_DETAIL_FORM" property="wrkContact.contactId"/></td>
</tr><tr>
  <td><b>Contact type: </b>
</td><td>
  <html:select tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkContact.contactTypeCd" >

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
 <html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkContact.firstName"
  size='15' /></td>
  <td><b>Last&nbsp;Name:</b> </td><td>
<html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkContact.lastName"
  size='20' /></td>
  </tr>
  <tr>
  <td><b>Phone: </b> </td>
<td><html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkContact.phone"
  size='20' /></td>
  </tr>
  <tr>
  <td><b>Cell.Phone: </b> </td>
<td> <html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkContact.cellPhone"
  size='20' /></td>
  </tr>
  <tr>
  <td><b>Fax: </b> </td>
<td><html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkContact.fax"
size='20' /></td>
  </tr>
  <tr>
  <td><b>eMail: </b></td>
<td> <html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkContact.email"
  size='20' /></td>
  </tr>


  <tr>
<td colspan='4'><html:submit tabindex='<%=tabIndex++ + ""%>' property="action">Save Contact</html:submit></td>
<%
String tcid = request.getParameter("contactId");
String delHref="distdet.do?action=Delete Contact&contactId="+tcid;
%>
 <td><a tabindex='<%=tabIndex++ + ""%>' ID=296 href="<%=delHref%>">[Delete]</a></td>

</tr>
  </table>
  </td></tr>

  <% } %>
<%
	//add the tabIndex to the request so subsequent pages can make use of it
	tabIndexInteger = new Integer(tabIndex);
	request.setAttribute("tabIndex", tabIndexInteger);
%>
</table>
