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
  String portal = request.getParameter("portal");
  if ( null == portal ) { portal = "adminportal"; }
  String thisAction = request.getParameter("action");
  if (thisAction == null) thisAction = "";

  String thisContactId = request.getParameter("contactId");
  String thisBranchId = request.getParameter("branchAddrId");
  if ( null == thisContactId ) { thisContactId = "0"; }
  if ( null == thisBranchId ) { thisBranchId = "0"; }

  String contactType = request.getParameter("contactType"),
         contactId = request.getParameter("contactId");

  String addBAddrHref = "distdet.do?action=Add Branch Address"
         + "&contactType=branch address&portal=adminportal";
  
  int tabIndex = 0;
  Integer tabIndexInteger = (Integer)request.getAttribute("tabIndex");
  if (tabIndexInteger != null) {
	  tabIndex = tabIndexInteger.intValue();
  }
%>

<%! int addrId = 0; %>

<% if(theForm.getBranchesCollection()!=null) { %>

<table ID=278 class="adm_panel">

<tr class="adm_panel">
  <td class="largeheader">Branches</td>
  <td><a tabindex='<%=tabIndex++ + ""%>' ID=279 href="<%=addBAddrHref%>" > [Add Branch Address]</a></td>
</tr>


<tr>
  <td class="tableheader">Id</td>
  <td class="tableheader">Address</td>
  <td class="tableheader">City</td>
  <td class="tableheader">State</td>
  <td class="tableheader">Zip/Postal</td>
  <td class="tableheader">Country</td>
  <td class="tableheader">&nbsp;</td>
  <td class="tableheader">&nbsp;</td>
</tr>

<logic:iterate id="addrD" name="STORE_DIST_DETAIL_FORM"
  property="branchesCollection"
  type="com.cleanwise.service.api.value.DistBranchData"
  indexId="idx1" >

  <%
  addrId = addrD.getBranchAddress().getAddressId();
  String editBranchStr = "Edit Branch Address";
  String editAddrHref="distdet.do?action="
   + editBranchStr + "&branchAddrId="+addrId
   + "&contactType=branch contact&portal=adminportal#pta_"
   + addrId;
  String addBranchContactStr = "Add Branch Contact";
  String addBranchContactHref="distdet.do?action="
   + addBranchContactStr + "&branchAddrId="+addrId
   + "&contactType=branch contact&portal=adminportal#pta_"
   + addrId;
  %>


<tr>
  <td><a tabindex='<%=tabIndex++ + ""%>' ID=280 href="<%=editAddrHref%>"><%=addrId%></a></td>

  <td>
    <% String nameforLink = "pta_" + + addrId; %>
    <a ID=281 name="<%=nameforLink%>" > &nbsp;</a>
    <bean:write name="addrD" property="branchAddress.address1" />
          <bean:write name="addrD" property="branchAddress.address2" />
          <bean:write name="addrD" property="branchAddress.address3" />
  </td>
  <td><bean:write name="addrD" property="branchAddress.city" /></td>
  <td><bean:write name="addrD" property="branchAddress.stateProvinceCd" /></td>
  <td><bean:write name="addrD" property="branchAddress.postalCode" /></td>
  <td><bean:write name="addrD" property="branchAddress.countryCd" /></td>

<td colspan=7 align=right><a tabindex='<%=tabIndex++ + ""%>' ID=282 href="<%=addBranchContactHref%>">[Add Branch Contact]</a></td>

</tr>


<%
ContactViewVector thisBranchContacts = addrD.getContactsCollection();
if ( null != thisBranchContacts && thisBranchContacts.size() > 0 ) {
%>

<tr>
 <td>&nbsp;</td>
 <td class="tableheader">ContactId</td>
 <td class="tableheader">ContactType</td>
 <td class="tableheader">FirstName</td>
 <td class="tableheader">LastName</td>
 <td class="tableheader">Phone</td>
 <td class="tableheader">CellPhone</td>
 <td class="tableheader">Fax</td>
 <td class="tableheader">Email</td>
</tr>

<%! String editBranchContactStr = ""; %>
<%! int v_branchContactid = 0; %>

<logic:iterate id="distContactD" name="addrD"
  property="contactsCollection"
  type="com.cleanwise.service.api.value.ContactView">

  <%
  v_branchContactid = distContactD.getContactId();
  editBranchContactStr = "Edit Branch Contact";
  String editBranchContactHref="distdet.do?action="
     + editBranchContactStr + "&contactId="
	+v_branchContactid + "&contactType=branch"
	+ "&portal=adminportal#pta_" + v_branchContactid;

  %>

<tr>
 <% String nameforLink2 = "pta_" + + v_branchContactid; %>
 <td><a ID=283 name="<%=nameforLink2%>" > &nbsp;</a></td>
 <td><a tabindex='<%=tabIndex++ + ""%>' ID=284 href="<%=editBranchContactHref%>"><%=v_branchContactid%></a></td>
 <td><%=distContactD.getContactTypeCd()%></td>
 <td><%=distContactD.getFirstName()%></td>
 <td><%=distContactD.getLastName()%></td>
 <td><%=distContactD.getPhone()%></td>
 <td><%=distContactD.getCellPhone()%></td>
 <td><%=distContactD.getFax()%></td>
 <td><%=distContactD.getEmail()%></td>
</tr>

<%

boolean v_openConfig = thisAction.equals(editBranchContactStr) &&
      String.valueOf(v_branchContactid).equals(thisContactId);

if ( v_openConfig      ) {

%>

<tr>
  <td  colspan=5>
    <b>Contact for <%=String.valueOf(distContactD.getAddressId())%></b>

    <html:hidden property="wrkBranchContact.addressId" value="<%=String.valueOf(distContactD.getAddressId())%>"/>
    <html:hidden property="wrkContactType" value="branch"/>
    <table ID=285>
      <tr>
        <td rowspan=9>&nbsp;</td>
        <td>
          <b>Contact information for <%=contactType%>.</b><br>
          <b>Contact id: </b><bean:write name="STORE_DIST_DETAIL_FORM" property="wrkBranchContact.contactId"/>
        </td>
      </tr>
      <tr>
        <td><b>Contact type: </b>
          <html:select tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM"  property="wrkBranchContact.contactTypeCd" >
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.ACCOUNTS_RECEIVABLE%>'>Accounts Receivable</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.CUSTOMER_SERVICE%>'>Customer Service</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.GPREP%>'>GP Rep</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.JDREP%>'>JD Rep</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.KCREP%>'>KC Rep</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.OPERATIONS%>'>Operations</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.OTHER%>'>Other</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.OWNER%>'>Owner</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.PRIMARY_CONTACT%>'>Primary Contact</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.PURCHASING%>'>Purchasing</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.SALES%>'>Sales</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.TECHNOLOGY%>'>Technology</html:option>
         </html:select>
        </td>
      </tr>
      <tr>
        <td><b>First&nbsp;Name:</b></td>
        <td><html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkBranchContact.firstName"  size='15' /></td>
      </tr>
      <tr>
        <td><b>Last&nbsp;Name:</b> </td>
        <td><html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkBranchContact.lastName"  size='20' /></td>
      </tr>
      <tr>
        <td><b>Phone: </b> </td>
        <td><html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkBranchContact.phone" size='20' /></td>
      </tr>
      <tr>
        <td><b>Cell.Phone: </b> </td>
        <td> <html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkBranchContact.cellPhone" size='20' /></td>
      </tr>
      <tr>
        <td><b>Fax: </b> </td>
        <td><html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkBranchContact.fax" size='20' /></td>
      </tr>
      <tr>
        <td><b>eMail: </b></td>
        <td> <html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="wrkBranchContact.email" size='20' /></td>
      </tr>
      <tr>
        <td colspan='2'><html:submit tabindex='<%=tabIndex++ + ""%>' property="action">Save Branch Contact</html:submit></td>
        <td colspan='2'>
            <%
            String delContactHref="distdet.do?action=Delete Branch Contact" + "&contactId=" +contactId;
            if( null != contactId && !contactId.trim().equals("0"))  {%>
              <a tabindex='<%=tabIndex++ + ""%>' ID=286 href="<%=delContactHref%>">[ Delete ]</a>
            <%} else {%>
              &nbsp;
            <%}%>
        </td>
      </tr>
    </table>
  </td>
</tr>
<% } %>

</logic:iterate>

<% } %>


<%
boolean v_openConfig2 = thisAction.equals(addBranchContactStr) &&  String.valueOf(addrId).equals(thisBranchId);

if ( v_openConfig2 ) {

%>
<tr>
  <td colspan=5>
  <b>Contact for <%=thisBranchId%></b>

  <html:hidden property="newBranchContact.addressId" value="<%=thisBranchId%>"/>
  <html:hidden property="wrkContactType" value="branch"/>
  <table ID=287>
    <tr>
      <td rowspan=9>&nbsp;</td>
      <td><b>New contact information for <%=contactType%>.</b><br>
          <b>Contact id: </b>
            <bean:write name="STORE_DIST_DETAIL_FORM"
                property="newBranchContact.contactId"/>
      </td>
    </tr>
    <tr>
      <td><b>Contact type: </b>
        <html:select tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM"
            property="newBranchContact.contactTypeCd" >
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.ACCOUNTS_RECEIVABLE%>'>Accounts Receivable</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.CUSTOMER_SERVICE%>'>Customer Service</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.GPREP%>'>GP Rep</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.JDREP%>'>JD Rep</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.KCREP%>'>KC Rep</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.OPERATIONS%>'>Operations</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.OTHER%>'>Other</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.OWNER%>'>Owner</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.PRIMARY_CONTACT%>'>Primary Contact</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.PURCHASING%>'>Purchasing</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.SALES%>'>Sales</html:option>
            <html:option value='<%=RefCodeNames.CONTACT_TYPE_CD.TECHNOLOGY%>'>Technology</html:option>
        </html:select>
      </td>
    </tr>
    <tr>
      <td><b>First&nbsp;Name:</b></td>
      <td><html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="newBranchContact.firstName" size='15' /></td>
    </tr>
    <tr>
      <td><b>Last&nbsp;Name:</b> </td>
      <td><html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="newBranchContact.lastName" size='20' /></td>
    </tr>
    <tr>
      <td><b>Phone: </b> </td>
      <td><html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="newBranchContact.phone"  size='20' /></td>
    </tr>
    <tr>
      <td><b>Cell.Phone: </b> </td>
      <td> <html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="newBranchContact.cellPhone" size='20' /></td>
    </tr>
    <tr>
      <td><b>Fax: </b> </td>
      <td><html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="newBranchContact.fax" size='20' /></td>
    </tr>
    <tr>
      <td><b>eMail: </b></td>
      <td> <html:text tabindex='<%=tabIndex++ + ""%>' name="STORE_DIST_DETAIL_FORM" property="newBranchContact.email" size='20' /></td>
    </tr>
    <tr>
      <td colspan='2'><html:submit tabindex='<%=tabIndex++ + ""%>' property="action">Save Branch Contact</html:submit></td>
      <td colspan='2'>
        <% String delContactHref="distdet.do?action=Delete Branch Contact" + "&contactId=" +contactId;
           if( null != contactId && !contactId.trim().equals("0"))  { %>
        <a tabindex='<%=tabIndex++ + ""%>' ID=288 href="<%=delContactHref%>">[Delete]</a>
        <%} else { %>
          &nbsp;
        <%}%>
      </td>
    </tr>
  </table>
</td></tr>
<% } %>

</logic:iterate>
<%
	//add the tabIndex to the request so subsequent pages can make use of it
	tabIndexInteger = new Integer(tabIndex);
	request.setAttribute("tabIndex", tabIndexInteger);
%>

<tr colspan=4 align=right>
  <%
  if(("Add Branch Address".equals(thisAction)
     || "Edit Branch Address".equals(thisAction)) )
     { %>
<td colspan=3>
<jsp:include flush='true' page="f_distBranch.jsp">
   <jsp:param name="contactType" 	value="distributor" />
</jsp:include>
</td>

  <% } %>

</tr>
</table>
<% } %>
