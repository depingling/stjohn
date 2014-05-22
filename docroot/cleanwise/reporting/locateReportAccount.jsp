<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.RequestPropertyNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.view.forms.LocateReportAccountForm" %>
<%@ page import="com.cleanwise.view.forms.StorePortalForm" %>
<%@ page import="com.cleanwise.view.utils.SessionAttributes" %>

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
        jspFormNestProperty = jspFormNestProperty + ".locateReportAccountForm";
    }
    else {
        jspFormNestProperty = "locateReportAccountForm";
    }

    String jspHtmlFormId = request.getParameter("jspFormName");
    if (jspHtmlFormId == null) {
        jspHtmlFormId = "LOCATE_ACCOUNT_FORM_ID";
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
    <bean:define id="theForm" name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" type="LocateReportAccountForm"/>
    <%
    if (theForm != null && theForm.getLocateReportAccountFl()) {
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
        jspSubmitIdent += "#" + SessionAttributes.SEARCH_FORM.LOCATE_REPORT_ACCOUNT_FORM;

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
            if (reportControl.getName().contains("LOCATE_ACCOUNT_MULTI")) {
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
           Find&nbsp;<%=(titleLabel!=null)?titleLabel: "Accounts"%>
           <html:form styleId="<%=jspHtmlFormId%>" action="<%=jspFormAction%>" scope="session">
                <html:hidden property="<%=RequestPropertyNames.PROPERTY_NAME_LOCATE_ACCOUNT_TYPE%>" value="<%=RequestPropertyNames.PROPERTY_VALUE_LOCATE_ACCOUNT_REPORT%>"/>
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
                      <%--  <td>&nbsp;</td>
                      <td colspan="2">  --%>
                      <td align="right">
                            <%
                            prop = jspFormNestProperty + ".searchField";
                            %>
                            <b>Search&nbsp;<%=(label!=null)?label:"Account:"%></b></td>
                            <td><html:text  property="<%=prop%>"/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <%
                            prop = jspFormNestProperty + ".searchType";
                            %>
                            <% if (!jspDataSourceType.equals("DW")) { %>
                            <html:radio property="<%=prop%>" value="id" />
                            ID
                            <% } %>
                            <html:radio property="<%=prop%>" value="nameBegins" />
                            Name(starts with)
                            <html:radio property="<%=prop%>" value="nameContains" />
                            Name(contains)
                        </td>
                    </tr>
                    <%
                    GroupDataVector accountGroups = theForm.getAccountGroups();
                    if(!jspDataSourceType.equals("DW") && accountGroups != null && accountGroups.size() > 0) {
                    %>
                    <tr>
                        <td colspan='4'>
                            <b>Groups:</b>
                            <%
                            prop = jspFormNestProperty + ".searchGroupId";
                            %>
                            <html:radio property='<%=prop%>' value='0'>None&nbsp;</html:radio>
                            <%
                            for (Iterator iter = accountGroups.iterator(); iter.hasNext();) {
                                GroupData gD = (GroupData) iter.next();
                                String grIdS = "" + gD.getGroupId();
                                String grName = gD.getShortDesc();
                            %>
                            <html:radio property='<%=prop%>' value='<%=grIdS%>'><%=grName%>&nbsp;
                            </html:radio>
                            <%
                            }
                            %>
                        </td>
                    </tr>
                    <%
                    }
                    %>
                    <tr>
                        <td colspan='4'>
                            <html:submit property="action" value="Search"/>
                            <%
                            prop = jspFormNestProperty + ".showInactiveFl";
                            %>
                            Show Inactive: <html:checkbox property="<%=prop%>"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <html:submit property="action" value="Cancel"/>
                            <html:submit property="action" value="Return Selected"/></td>
                        </td>
                    </tr>
                </table>

                <%
                if (theForm.getAccounts() != null) {
                    int rescount = theForm.getAccounts().size();
                %>
                    Search result count:
                    <%
                    if (rescount >= Constants.MAX_ACCOUNTS_TO_RETURN) {
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
                          <% if (!jspDataSourceType.equals("DW")) { %>
                            <td><a ID=327 class="tableheader">Account Id</td>
                          <% } %>
                            <td><a ID=328 class="tableheader">Name </a></td>
                            <td>
                                <a ID=329 href="javascript:SetChecked(1)">[Check&nbsp;All]</a><br>
                                <a ID=330 href="javascript:SetChecked(0)">[&nbsp;Clear]</a>
                            </td>
                            <td><a ID=331 class="tableheader">City</a></td>
                            <td><a ID=332 class="tableheader">State/Province</a></td>
                            <td><a ID=333 class="tableheader">Type</a></td>
                            <td><a ID=334 class="tableheader">Status</a></td>
                        </tr>
                        <%
                        String propName = jspFormNestProperty + ".accounts";
                        prop = jspFormNestProperty + ".accounts";
                        String selectBoxProp = jspFormNestProperty + ".selected";
                        %>
                        <logic:iterate id="arrele" name="<%=jspFormName%>" property="<%=prop%>" type="com.cleanwise.service.api.value.AccountUIView">
                            <bean:define id="key"  name="arrele" property="busEntity.busEntityId"/>
                            <bean:define id="name" name="arrele" property="busEntity.shortDesc" type="String"/>
                            <%
                            String linkHref = "javascript: SetAndSubmit ('" + selectBoxProp + "'," + key + ");";
                            %>
                        <tr>
                            <% if (!jspDataSourceType.equals("DW")) { %>
                            <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
                            <% } %>
                            <td><a ID=335 href="<%=linkHref%>"><bean:write name="arrele" property="busEntity.shortDesc"/></a></td>
                            <td><html:multibox property="<%=selectBoxProp%>" value="<%=key.toString()%>"/></td>
                            <td><bean:write name="arrele" property="primaryAddress.city"/></td>
                            <td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
                            <td><bean:write name="arrele" property="accountType.value"/></td>
                            <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
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
    } // if (theForm.getLocateReportAccountFl())
    %>
</logic:present>
