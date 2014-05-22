<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">

<font color=red>
<html:errors/>
</font>

<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">

<%
String currentURI = request.getServletPath();
currentURI = currentURI.substring(0,(currentURI.lastIndexOf(".jsp"))) + ".do";
String fullCurrentURI = request.getContextPath() + currentURI;
String detatilAction = "view";
request.setAttribute("detailURI",request.getContextPath() + "/distributorportal/purchaseOrderDistDetail.do");
%>
<bean:define id="columnCount" value="13"/>

<html:form name="PO_OP_SEARCH_FORM" action="<%= currentURI %>"
    scope="session" type="com.cleanwise.view.forms.PurchseOrderOpSearchForm">

  <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.PO_MANIFESTING%>">
  <tr>
       <td colspan="5" align="center">
        <html:submit property="action">
            <app:storeMessage  key="distributor.button.manifesting.complete"/>
        </html:submit>
       </td>
  </tr>
  </app:authorizedForFunction>

  <tr> <td><b>Search:</b></td>
       <td colspan="4">&nbsp;
       </td>
  </tr>

  <tr> <td>&nbsp;</td>
       <td><b>Purchase Order Date:</b><br>(mm/dd/yyyy)</td>
           <td colspan="3">
                        Begin Date Range
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        End Date Range<br>
                        <html:text name="PO_OP_SEARCH_FORM" property="poDateRangeBegin" />

                        <html:text name="PO_OP_SEARCH_FORM" property="poDateRangeEnd" />
           </td>
  </tr>

<tr> <td>&nbsp;</td>
    <td><b>Erp PO #:</b></td>
    <td>
        <html:text name="PO_OP_SEARCH_FORM" property="erpPONum" />
    </td>
    <td><b>&nbsp;</b></td>
    <td>
        &nbsp;
    </td>
</tr>

<tr> <td>&nbsp;</td>
    <td><b>Outbound PO #:</b></td>
    <td>
        <html:text name="PO_OP_SEARCH_FORM" property="outboundPoNum" />
    </td>
    <td><b>Distributor Invoice Num:</b></td>
    <td>
        <html:text name="PO_OP_SEARCH_FORM" property="invoiceDistNum" />
    </td>
</tr>

  <tr> <td>&nbsp;</td>
       <td><b>Purchase Order Status:</b></td>
           <td colspan="3">
            <html:select name="PO_OP_SEARCH_FORM" property="purchaseOrderStatus">
                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                <html:option value="<%=RefCodeNames.PURCHASE_ORDER_STATUS_CD.CANCELLED%>"></html:option>
                <html:option value="<%=RefCodeNames.PURCHASE_ORDER_STATUS_CD.DIST_ACKD_PURCH_ORDER%>"></html:option>
                <html:option value="<%=RefCodeNames.PURCHASE_ORDER_STATUS_CD.PENDING_FULFILLMENT%>"></html:option>
                <html:option value="<%=RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR%>"></html:option>
            </html:select>
       </td>
       <td></td><td></td>
  </tr>
  <tr>
        <td>&nbsp;</td>
        <td><b>Orig Customer PO Number:</b></td><td><html:text name="PO_OP_SEARCH_FORM" property="orderRequestPoNum" /></td>
        <td><b>Web Order # / Confirmation #:</b></td><td><html:text name="PO_OP_SEARCH_FORM" property="webOrderConfirmationNum" /></td>
  </tr>
  
  
  <tr>
        <td>&nbsp;</td>
        <td><b>Return Number:</b></td>
        <td>
                <html:text name="PO_OP_SEARCH_FORM" property="returnRequestRefNum" />
        </td>
        <td><b>Distributor Return Number:</b></td>
        <td>
                <html:text name="PO_OP_SEARCH_FORM" property="distributorReturnRequestNum" />
        </td>
  </tr>


  <tr>
       <td colspan="5" align="center">
       <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.PO_MANIFESTING%>">
		<html:submit property="action">
		    <app:storeMessage  key="distributor.button.pending.manifest"/>
		</html:submit>
	</app:authorizedForFunction>
        <html:submit property="action">
            <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
	<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.PO_MANIFESTING%>">
		<html:submit property="action">
		    <app:storeMessage  key="global.action.label.next"/>
		</html:submit>
	</app:authorizedForFunction>
     </td>
  </tr>
  <tr><td colspan="5">&nbsp;</td>
  </tr>


<!--old form end tag-->
</table>


Search results count:&nbsp;<bean:write name="PO_OP_SEARCH_FORM" property="listCount" filter="true"/>

<logic:greaterThan name="PO_OP_SEARCH_FORM" property="listCount" value="0">


<!--deal with the result set-->



<table cellpadding="2" cellspacing="0" border="0" width="769" class="results">
<tr align=left>
<td><a href="<%= fullCurrentURI %>?action=sort&sortField=distributorname"><b>Dist Name</b></a></td>
<td class="resultscolumna"><a href="<%= fullCurrentURI %>?action=sort&sortField=accountname"><b>Acct Name</b></a></td>
<td><a href="<%= fullCurrentURI %>?action=sort&sortField=webordernum"><b>PO&nbsp;#</b></a></td>
<td class="resultscolumna"><a href="<%= fullCurrentURI %>?action=sort&sortField=podate"><b>PO Date</b></a></td>
<td><a href="<%= fullCurrentURI %>?action=sort&sortField=sitename"><b>Site Name</b></a></td>
<td class="resultscolumna"><b>Address</b></td>
<td><b>City</b></td>
<td class="resultscolumna"><b>State</b></td>
<td><a href="<%= fullCurrentURI %>?action=sort&sortField=zipcode"><b>Zip Code</b></a></td>
<td class="resultscolumna"><a href="<%= fullCurrentURI %>?action=sort&sortField=status"><b>Status</b></a></td>
<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.PO_MANIFESTING%>">
  <td><b>Manifest Status</b></td>
  <td class="resultscolumna"><b>Num Packages</b></td>
</app:authorizedForFunction>

<app:authorizedForFunction 
 name="<%=RefCodeNames.APPLICATION_FUNCTIONS.PO_ADDRESS_PRINTING%>">
  <td class="resultscolumna"><b>Num Labels</b></td>
</app:authorizedForFunction>

</tr>

<bean:define id="pagesize" name="PO_OP_SEARCH_FORM" property="listCount"/>


<logic:iterate id="po" name="PO_OP_SEARCH_FORM" property="resultList"
               offset="0" length="<%=pagesize.toString()%>" indexId="i"
               type="com.cleanwise.service.api.value.PurchaseOrderStatusDescDataView">
    <bean:define id="key" name="po" property="purchaseOrderData.purchaseOrderId"/>
    <bean:define id="poDate" name="po" property="purchaseOrderData.poDate"/>

    <% String linkHref = new String(request.getAttribute("detailURI") + "?action=" + detatilAction + "&id=" + key);%>

    <tr>
        <td colspan="<%=columnCount%>">
            <hr>
        </td>
    </tr>
    <tr>
        <td><bean:write name="po" property="distributorBusEntityData.shortDesc"/></td>
        <td class="resultscolumna"><bean:write name="po" property="accountBusEntityData.shortDesc"/></td>

        <logic:present name="po" property="purchaseOrderData.purchaseOrderStatusCd">
            <logic:equal name="po" property="purchaseOrderData.purchaseOrderStatusCd"
                         value="<%=RefCodeNames.PURCHASE_ORDER_STATUS_CD.CANCELLED%>">
                <bean:define id="goodStatus" value="false"/>
            </logic:equal>
            <logic:notEqual name="po" property="purchaseOrderData.purchaseOrderStatusCd"
                            value="<%=RefCodeNames.PURCHASE_ORDER_STATUS_CD.CANCELLED%>">
                <bean:define id="goodStatus" value="true"/>
            </logic:notEqual>
        </logic:present>
        <logic:notPresent name="po" property="purchaseOrderData.poDate">
            <bean:define id="goodStatus" value="false"/>
        </logic:notPresent>

        <logic:equal name="goodStatus" value="true">
		    <logic:present name="po" property="purchaseOrderData.outboundPoNum">
            <td><a href="<%=linkHref%>"><bean:write name="po" property="purchaseOrderData.outboundPoNum"/></a></td>
			</logic:present>
		    <logic:notPresent name="po" property="purchaseOrderData.outboundPoNum">
            <td><a href="<%=linkHref%>"><bean:write name="po" property="purchaseOrderData.erpPoNum"/></a></td>
			</logic:notPresent>
        </logic:equal>
        <logic:notEqual name="goodStatus" value="true">
            <td><bean:write name="po" property="purchaseOrderData.outboundPoNum"/></td>
        </logic:notEqual>

        <td class="resultscolumna">
            <logic:present name="po" property="purchaseOrderData.poDate">
                <i18n:formatDate value="<%=poDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
            </logic:present>
        </td>

        <td><bean:write name="po" property="shipToAddress.shortDesc"/></td>
        <td class="resultscolumna"><bean:write name="po" property="shipToAddress.address1"/></td>
        <td><bean:write name="po" property="shipToAddress.city"/></td>
        <td class="resultscolumna"><bean:write name="po" property="shipToAddress.stateProvinceCd"/></td>
        <td><bean:write name="po" property="shipToAddress.postalCode"/></td>
        <td class="resultscolumna"><bean:write name="po" property="purchaseOrderData.purchaseOrderStatusCd"/></td>

        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.PO_MANIFESTING%>">
            <td><bean:write name="po" property="purchaseOrderData.purchOrdManifestStatusCd"/></td>


            <logic:equal name="goodStatus" value="true">

                <% /*
	Allow the user to print labels for all
	POs available, regardless of whether or not
	the item was routed to Parcel Direct/Smart Post.
   */ %>

                <td class="resultscolumna">
                    <html:text name="PO_OP_SEARCH_FORM" property="quantityBarcode"
                               size="4" value=""/>
                </td>


            </logic:equal>


            <logic:notEqual name="goodStatus" value="true">
                <td class="resultscolumna"><html:hidden name="PO_OP_SEARCH_FORM" property="quantityBarcode"
                                                        value="0"/></td>
            </logic:notEqual>
        </app:authorizedForFunction>


        <app:authorizedForFunction
                name="<%=RefCodeNames.APPLICATION_FUNCTIONS.PO_ADDRESS_PRINTING%>">
            <td class="resultscolumna">
                <html:text name="PO_OP_SEARCH_FORM" property="quantityBarcode"
                           size="4" value=""/>
            </td>
        </app:authorizedForFunction>


    </tr>
</logic:iterate>

<tr>
    <td colspan="<%=columnCount%>" align="center">
        <html:submit property="action">
            <app:storeMessage  key="global.action.label.next"/>
        </html:submit>
    </td>
</tr>
</table>
</logic:greaterThan>
</html:form>
</div>




