<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.RequestPropertyNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.view.forms.LocateReportItemForm" %>
<%@ page import="com.cleanwise.view.forms.StorePortalForm" %>
<%@ page import="com.cleanwise.view.utils.SessionAttributes" %>
<%@ page import="com.cleanwise.service.api.util.SearchCriteria" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%
    String jspFormName = request.getParameter("jspFormName");
    if (jspFormName == null) {
        throw new RuntimeException("jspFormName cannot be null");
    }

    String jspFormNestProperty = request.getParameter("jspFormNestProperty");
    if (jspFormNestProperty != null) {
        jspFormNestProperty = jspFormNestProperty + ".locateReportItemForm";
    }
    else {
        jspFormNestProperty = "locateReportItemForm";
    }

    String jspHtmlFormId = request.getParameter("jspFormName");
    if (jspHtmlFormId == null) {
        jspHtmlFormId = "LOCATE_ITEM_FORM_ID";
    }

    String jspReportIdName = request.getParameter("jspReportIdName");
    String jspReportIdValue = request.getParameter("jspReportIdValue");
%>
<logic:present name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" >

<% com.cleanwise.view.forms.StorePortalBaseForm baseForm = null;
  if (jspFormName.toUpperCase().startsWith("ANALYTIC")) {
      baseForm = (com.cleanwise.view.forms.AnalyticReportForm)session.getAttribute(jspFormName);
 } else if (jspFormName.toUpperCase().startsWith("CUSTOMER")) {
      baseForm = (com.cleanwise.view.forms.CustAcctMgtReportForm)session.getAttribute(jspFormName);
 } %>
    <bean:define id="theForm" name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" type="LocateReportItemForm"/>
    <%
    if (theForm != null && theForm.getLocateReportItemFl()) {
        String jspFormAction = request.getParameter("jspFormAction");
        if (jspFormAction == null) {
            throw new RuntimeException("jspFormAction cannot be null");
        }
        String jspSubmitIdent = request.getParameter("jspSubmitIdent");
        if (jspSubmitIdent == null) {
            throw new RuntimeException("jspSubmitIdent cannot be null");
        }
        String jspReturnFilterProperty = request.getParameter("jspReturnFilterProperty");
        if (jspReturnFilterProperty == null) {
            throw new RuntimeException("jspReturnFilterProperty cannot be null");
        }
        // To be Used for Data Werehouse
        String jspDataSourceType = request.getParameter("jspDataSourceType");
        if (jspDataSourceType == null) {
            jspDataSourceType = "";
        }
        jspSubmitIdent += "#" + SessionAttributes.SEARCH_FORM.LOCATE_REPORT_ITEM_FORM;

    %>
 <%
    String titleLabel = null;
    String label = null;
    if (baseForm != null ){
      GenericReportControlViewVector reportControls = null;
      if (baseForm instanceof com.cleanwise.view.forms.AnalyticReportForm) {
         reportControls = ((com.cleanwise.view.forms.AnalyticReportForm)baseForm).getGenericControls();
      } else if (baseForm instanceof com.cleanwise.view.forms.CustAcctMgtReportForm){
         reportControls = ((com.cleanwise.view.forms.CustAcctMgtReportForm)baseForm).getReportControls();
      }
      if (reportControls != null && reportControls.size()>0) {
        for (int i = 0; i < reportControls.size(); ++i) {
            GenericReportControlView reportControl = (GenericReportControlView) reportControls.get(i);
            if (reportControl.getName().contains("LOCATE_ITEM_MULTI")) {
               label = reportControl.getLabel();
               if (label != null && label.length()==0){
                 label = null;
               }
               titleLabel = (label!=null) ? label.replace(":", "") : label;
               break;
            }
        }
      }
    }
  %>


        <html:html>
        <script language="JavaScript1.2">
            <!--
            function SetChecked(val) {
                dml = document.forms;
                for (i = 0; i < dml.length; i++) {
                    found = false;
                    ellen = dml[i].elements.length;
                    for (j = 0; j < ellen; j++) {
                        if (dml[i].elements[j].name == '<%=jspFormNestProperty%>.selected') {
                            dml[i].elements[j].checked = val;
                            found = true;
                        }
                    }
                    if (found)
                        break;
                }
            }

            function SetAndSubmit(name, val) {
                var dml = document.forms[0];
                var ellen = dml[name].length;
                if (ellen>0) {
                    for (j = 0; j < ellen; j++) {
                        if (dml[name][j].value == val) {
                            found = true;
                            dml[name][j].checked = 1;
                        }
                        else {
                            dml[name][j].checked = 0;
                        }
                    }
                }
                else {
                    dml[name].checked = 1;
                }
                var iiLast = dml['action'].length - 1;
                dml['action'][iiLast].value = 'Return Selected';
                dml.submit();
            }
            //-->
        </script>

        <body>
        <script src="../externals/lib.js" language="javascript"></script>
        <div class="rptmid">
           Find&nbsp;<%=(titleLabel!=null)?titleLabel: "Items"%>
           <html:form styleId="<%=jspHtmlFormId%>" action="<%=jspFormAction%>" scope="session">
                <html:hidden property="<%=RequestPropertyNames.PROPERTY_NAME_LOCATE_ITEM_TYPE%>" value="<%=RequestPropertyNames.PROPERTY_VALUE_LOCATE_ITEM_REPORT%>"/>
                <html:hidden property="jspSubmitIdent" value="<%=jspSubmitIdent%>"/>
                <%
                theForm.setDataSourceType(jspDataSourceType);
                String prop = jspFormNestProperty + ".property";
                %>
                <html:hidden property="<%=prop%>" value="<%=jspReturnFilterProperty%>"/>
                <%
                prop = jspFormNestProperty + ".name";
                %>
                <html:hidden property="<%=prop%>" value="<%=jspFormName%>"/>
                <%
                if (jspReportIdName != null && jspReportIdValue != null) {
                %>
                <html:hidden property="<%=jspReportIdName%>" value="<%=jspReportIdValue%>"/>
                <%
                }
                %>
                <table ID=324 width="750" border="0"  class="mainbody">
                    <tr>
                        <td><b>Category:</b></td>
                        <td><html:text  property="locateReportItemForm.categoryTempl"/> </td>
                        <td><html:radio property="locateReportItemForm.categoryTemplType" value="nameBegins" /> Name(starts with)</td>
                        <td><html:radio property="locateReportItemForm.categoryTemplType" value="nameContains" />Name(contains)</td>
                    </tr>
                    <tr>
                        <td><b>Short Description:</b></td>
                        <td><html:text  property="locateReportItemForm.shortDescTempl"/></td>
                        <td><html:radio property="locateReportItemForm.shortDescTemplType" value="nameBegins" /> Name(starts with)</td>
                        <td><html:radio property="locateReportItemForm.shortDescTemplType" value="nameContains" />Name(contains)</td>
                    </tr>
                    <tr>
                        <td><b>Manufacturer:</b></td>
                        <td><html:text  property="locateReportItemForm.manufTempl"/></td>
                        <td><html:radio property="locateReportItemForm.manufTemplType" value="nameBegins" /> Name(starts with)</td>
                        <td><html:radio property="locateReportItemForm.manufTemplType" value="nameContains" />Name(contains)</td>
                    </tr>
                    <tr>
                        <td><b>Item Number:</b></td>
                        <td><html:text  property="locateReportItemForm.skuTempl"/></td>
                        <td colspan="2">
                           <html:radio  property="locateReportItemForm.skuTemplType" value="<%=SearchCriteria.DISTRIBUTOR_SKU_NUMBER%>"/>
                           Distributor

                           <html:radio  property="locateReportItemForm.skuTemplType" value="<%=SearchCriteria.CUST_SKU_NUMBER%>"/>
                           Customer
                           <html:radio  property="locateReportItemForm.skuTemplType" value="<%=SearchCriteria.MANUFACTURER_SKU_NUMBER%>"/>
                           Manufacturer

                        </td>
                    </tr>
                    <tr>
                        <td colspan='2'>
                            <html:submit property="action" value="Search"/>
                        <td colspan="2">
                            <html:submit property="action" value="Cancel"/>
                            <html:submit property="action" value="Return Selected"/>
                        </td>
                    </tr>
                </table>

                <%
                if (theForm.getItems() != null) {
                    int rescount = theForm.getItems().size();
                %>
                    Search result count:
                    <%
                    if (rescount >= Constants.MAX_ITEMS_TO_RETURN) {
                    %>
                    (request limit)
                    <%
                    }
                    %>
                    <%=rescount%>
                    <%
                    if (rescount > 0) {
                    %>
                    <table ID=326 width="750" border="0" class="results">
                        <tr align=left>
                            <td><a ID=327 class="tableheader">Sku</a></td>
                            <td>
                                <a ID=329 href="javascript:SetChecked(1)">[Check&nbsp;All]</a><br>
                                <a ID=330 href="javascript:SetChecked(0)">[&nbsp;Clear]</a>
                            </td>
                            <td><a ID=328 class="tableheader">Name </a></td>
                            <td><a ID=331 class="tableheader">Customer Sku</a></td>
                            <td><a ID=332 class="tableheader">Size</a></td>
                            <td><a ID=333 class="tableheader">Pack</a></td>
                            <td><a ID=334 class="tableheader">UOM</a></td>
                            <td><a ID=335 class="tableheader">Manufacturer</a></td>
                            <td><a ID=336 class="tableheader">Mfg. Sku</a></td>
                            <td><a ID=337 class="tableheader">Category</a></td>
                            <td><a ID=338 class="tableheader">Status</a></td>
                        </tr>
                        <%
                        String propName = jspFormNestProperty + ".items";
                        prop = jspFormNestProperty + ".items";
                        String selectBoxProp = jspFormNestProperty + ".selected";
                        %>
                        <logic:iterate id="arrele" name="<%=jspFormName%>" property="<%=prop%>" type="com.cleanwise.service.api.value.ItemView">
                            <bean:define id="key"  name="arrele" property="itemId"/>
                            <bean:define id="name" name="arrele" property="name" type="String"/>
                            <%
                            String linkHref = "javascript: SetAndSubmit ('" + selectBoxProp + "'," + key + ");";
                            %>
                        <tr>
                            <td><bean:write name="arrele" property="sku"/></td>
                            <td><html:multibox property="<%=selectBoxProp%>" value="<%=key.toString()%>"/></td>
                            <td><a ID=335 href="<%=linkHref%>"><bean:write name="arrele" property="name"/></a></td>
                            <td><bean:write name="arrele" property="sku"/></td>
                            <td><bean:write name="arrele" property="size"/></td>
                            <td><bean:write name="arrele" property="pack"/></td>
                            <td><bean:write name="arrele" property="uom"/></td>
                            <td><bean:write name="arrele" property="manufName"/></td>
                            <td><bean:write name="arrele" property="manufSku"/></td>
                            <td><bean:write name="arrele" property="category"/></td>
                            <td><bean:write name="arrele" property="statusCd"/></td>
                        </tr>
                        </logic:iterate>
                    </table>
                    <%
                    }
                }
                %>
                <html:hidden property="action" value="Search"/>
            </html:form>
        </div>
        </body>
        </html:html>
    <%
    } 
    %>
</logic:present>
