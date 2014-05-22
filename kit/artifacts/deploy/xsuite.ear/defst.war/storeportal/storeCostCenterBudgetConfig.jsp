<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.view.utils.SessionAttributes" %>
<%@ page import="com.cleanwise.service.api.util.FiscalCalendarUtility" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<script language="JavaScript1.2">
    <!--
    function Submit (val) {
     dml=document.forms;
     for(i=0; i<dml.length; i++) {
      ellen = dml[i].elements.length;
      //alert('next_form='+ellen);
      for(j=0; j<ellen; j++) {
        if (dml[i].elements[j].name=='action' &&
            dml[i].elements[j].value=='BBBBBBB') {
          //alert('action.value='+dml[i].elements[j].value+' -> '+val);
          dml[i].elements[j].value=val;
          dml[i].submit();
        }
      }
     }
    }
    -->
</script>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="account" type="java.lang.String" toScope="session"/>
<bean:define id="theForm"   name="STORE_ADMIN_COST_CENTER_FORM" type="com.cleanwise.view.forms.StoreCostCenterMgrForm"/>

<html:html>

<head>
    <link rel="stylesheet" href="../externals/styles.css">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<%
    FiscalCalenderView fcV = theForm.getFiscalCalenderView();
%>
<jsp:include flush='true' page="locateStoreAccount.jsp">
    <jsp:param name="jspFormAction" 	value="storeportal/costcenterbudget.do" />
    <jsp:param name="jspFormName" 	value="STORE_ADMIN_COST_CENTER_FORM" />
    <jsp:param name="jspSubmitIdent" value="" />
    <jsp:param name="jspReturnFilterProperty" value="accountFilter"/>
</jsp:include>
<jsp:include flush='true' page="locateStoreSite.jsp">
    <jsp:param name="jspFormAction" 	value="storeportal/costcenterbudget.do" />
    <jsp:param name="jspFormName" 	value="STORE_ADMIN_COST_CENTER_FORM" />
    <jsp:param name="jspSubmitIdent" 	        value="" />
    <jsp:param name="jspReturnFilterProperty" 	value="siteFilter" />
</jsp:include>
<div class="text">

<table ID=1603 width="769"  class="mainbody">
<html:form styleId="1604" name="STORE_ADMIN_COST_CENTER_FORM" action="storeportal/costcenterbudget" scope="session" type="com.cleanwise.view.forms.StoreCostCenterMgrForm">
<tr>
    <td colspan='2'><b>Account:</b>
        &nbsp;
        <html:select name="STORE_ADMIN_COST_CENTER_FORM" property="budgetAccountIdInp" onchange="Submit('changeBudgetAccount');">
            <%
                BusEntityDataVector accounts = theForm.getBudgetAccounts();
                for(Iterator iter= accounts.iterator(); iter.hasNext(); ) {
                    BusEntityData beD = (BusEntityData) iter.next();
                    String accountIdS = ""+beD.getBusEntityId();
                    String shortDesc = beD.getShortDesc();
            %>
            <html:option value="<%=accountIdS%>"><%=shortDesc%></html:option>
            <% } %>
            <html:option value="0">--Select--</html:option>
        </html:select>
        <html:submit property="action" value="Locate Account" styleClass='text'/>
    </td>
    <td colspan="2">
        <logic:equal name="STORE_ADMIN_COST_CENTER_FORM" property="costCenter.costCenterTypeCd" value="<%=RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET%>">
            <%
                if (((com.cleanwise.view.forms.StoreCostCenterMgrForm) theForm).getAccountFilter() != null &&
                        !((com.cleanwise.view.forms.StoreCostCenterMgrForm) theForm).getAccountFilter().isEmpty()) { %>
            <b>Site:</b>
            <html:select name="STORE_ADMIN_COST_CENTER_FORM" property="budgetSiteIdInp"
                         onchange="Submit('changeBudgetSite');">
                <%
                    if (((com.cleanwise.view.forms.StoreCostCenterMgrForm) theForm).getLocateStoreSiteForm() != null) {
                        SiteViewVector sites = ((com.cleanwise.view.forms.StoreCostCenterMgrForm) theForm).getSiteFilter();
                        if (sites != null && !sites.isEmpty()) { %>
                <html:option value="0">--All--</html:option>
                <%
                    for (Iterator iter = sites.iterator(); iter.hasNext();) {
                        SiteView site = (SiteView) iter.next();
                        String siteIdS = String.valueOf(site.getId());
                        String shortDesc = site.getName();
                %>
                <html:option value="<%=siteIdS%>"><%=shortDesc%> </html:option>
                <% }
                }
                }  else { %>
                <html:option value="0">--Select--</html:option>
                <%
                    }
                %>
            </html:select>
            <html:submit property="action" value="Locate Site" styleClass='text'/>
            <%}%>
        </logic:equal>
    </td>
</tr>
<%if( fcV!=null) {%>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Date effDate = fcV.getFiscalCalender().getEffDate();
    String effDateS = (effDate!=null)? sdf.format(effDate):"";
    int year = fcV.getFiscalCalender().getFiscalYear();
    String yearS = (year!=0)?""+year:"";
%>
<tr><td colspan="4">
    <table ID=1605 border="0"cellpadding="2" cellspacing="1" width="769"  bgcolor="#cccccc">
        <tr><td class='tableheader' colspan='15' align='center'>Budget Periods</td>
        </tr>
        <tr>
            <td class='tableheader'>Effective Date</td>
            <td class='tableheader' align='center'>Year</td>
            <td class='tableheader' align='center'>Type</td>
            <td class='tableheader' colspan='13' align='center'>Period Start Dates</td>
        </tr>
        <tr>
            <td><%=Utility.strNN(effDateS)%></td>
            <td><%=Utility.strNN(yearS)%></td>
            <td><%=Utility.strNN(fcV.getFiscalCalender().getPeriodCd())%></td>
            <td><table><tr>
                <%for (int j = 0, i = 1; i <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fcV); i++, j++) {%>
                <td><%=FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), i)%></td>
                <%}%>
            </tr></table>
            </td>
        </tr>
    </table>
</td></tr>

<tr>
    <td colspan='4'><b>Cost Center&nbsp;Id:</b>&nbsp;
        <bean:write name="STORE_ADMIN_COST_CENTER_FORM" property="costCenter.costCenterId"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Name:</b>&nbsp;
        <bean:write name="STORE_ADMIN_COST_CENTER_FORM" property="costCenter.shortDesc"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Status:</b>&nbsp;
        <bean:write name="STORE_ADMIN_COST_CENTER_FORM" property="costCenter.costCenterStatusCd"/>       
    </td>
</tr>

<tr>
    <td><b>Cost Center Type:</b></td>
    <td>
        <html:select name="STORE_ADMIN_COST_CENTER_FORM" property="costCenter.costCenterTypeCd" onchange="Submit('changeBudgetType');">
            <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
            <html:option value="<%=RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET%>"><%=RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET%></html:option>
            <html:option value="<%=RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET%>"><%=RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET%></html:option>
        </html:select>
        <span class="reqind">*</span>
    </td>
    <td colspan='2'>&nbsp;</td>
</tr>

<logic:equal name="STORE_ADMIN_COST_CENTER_FORM" property="costCenter.costCenterTypeCd" value="<%=RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET%>">
    <!--///////////////  begin site search form /////////////-->
    <logic:notEqual name="STORE_ADMIN_COST_CENTER_FORM" property="costCenter.costCenterId" value="0">
        <logic:present name="STORE_ADMIN_COST_CENTER_FORM" property="siteBudgetList">
            <bean:size id="sitesfound" name="STORE_ADMIN_COST_CENTER_FORM" property="siteBudgetList"/>
            Count: <bean:write name="sitesfound"/>
            <tr><td  colspan='4'>
                <table ID=1606 width="769"  class="results">
                    <tr align=left>
                        <td>Site Id</td><td>Site Name</td><td>City</td><td>State</td>
                        <td>Budget</td>
                    </tr>
                    <logic:iterate id="budgetsele" name="STORE_ADMIN_COST_CENTER_FORM" indexId="i" property="siteBudgetList">
                        <% if (( i.intValue() % 2 ) == 0 ) { %>
                        <tr class="rowa">
                                <% } else { %>
                        <tr class="rowb">
                            <% } %>
                            <td><bean:write name="budgetsele" property="id"/></td>
                            <td><bean:write name="budgetsele" property="name"/></td>
                            <td><bean:write name="budgetsele" property="city"/></td>
                            <td><bean:write name="budgetsele" property="state"/></td>
                            <!-- ///////////////////////////// -->
                            <td><table>
                            <tr>
                                <%for (int j = 0, p = 1; p <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fcV); p++, j++) {%>
                                <%if (j % 7 == 0) {%></tr><tr><%}%>
                                <% if (Utility.isSet(FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), p))) {%>
                                    <td><%=FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), p)%>
                                         <html:text size="5" property='<%= "siteBudget[" + i + "].budgetAmount["+p+"]"%>'/>
                                    </td>
                                <% } %>
                                <% } %>
                            </tr>
                            </table></td>
                            <!-- ///////////////////////////// -->

                        </tr>
                    </logic:iterate>
                </table></td></tr>
        </logic:present>
    </logic:notEqual>
</logic:equal>
<!-- ///////// -->
<logic:equal name="STORE_ADMIN_COST_CENTER_FORM" property="costCenter.costCenterTypeCd"   value="<%=RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET%>">
    <tr><td colspan='4'><table ID=1608>
        <tr>
            <td valign="top"><b>Amounts By Period:</b></td>
            <td><table> <tr><%for (int j = 0, i = 1; i <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fcV); i++, j++) {%>
                <%if (j % 7 == 0){%></tr><tr><%}%>
                <% if (Utility.isSet(FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), i))) {%>
                <td><%=FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), i)%><html:text size="5" property='<%= "budgetAmounts["+i+"]"%>'/></td>
                <% } %>
                <% } %>
            </tr>
            </table>
            </td>
            </tr>
    </table>
    </td>
    </tr>
</logic:equal>

<tr><td colspan='4'>
    <html:submit property="action">
    <app:storeMessage  key="costcenter.button.savebudgets"/>
</html:submit>    
</td></tr>

<%}%>   </table>
<!-- End Display Sites -->
<html:hidden  property="action" value="BBBBBBB"/>
</html:form>

</div>

</body>

</html:html>






