<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.service.api.util.FiscalCalendarUtility" %>
<%@ page import="com.cleanwise.view.forms.SiteBudget" %>
<%@ page import="java.math.BigDecimal" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>


<style>
    .bb {
        background-color: #cccccc;
        font-weight: bold;
        padding-left: 1em;
        padding-right: 1em;
    }
</style>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>

<body>

<script>
    function f_setAllBudgetValue(pForm, index, numperiods) {
        var newValue = pForm.elements["setAllBudgetList[" + index + "]"].value;
        var pattern = "siteBudgetList[" + index + "].budgetAmount";
        for (var i = 1; i <= numperiods; i++) {
            var name = pattern + "[" + i + "]";
            pForm.elements[name].value = newValue;
        }
        return false;
    }
</script>

<bean:define id="theForm" name="ADMIN2_SITE_BUDGET_MGR_FORM" type="com.cleanwise.view.forms.Admin2SiteBudgetMgrForm"/>

<% String property;%>
<% java.math.BigDecimal siteTotalBudget = new java.math.BigDecimal(0); %>
<% FiscalPeriodView fiscalInfo = theForm.getFiscalInfo(); %>

<div class="text">

<table width="<%=Constants.TABLEWIDTH800%>" class="mainbody">
<html:form name="ADMIN2_SITE_BUDGET_MGR_FORM"
           scope="session"
           action="admin2.0/admin2SiteBudgets.do"
           scope="session"
           type="com.cleanwise.view.forms.Admin2SiteBudgetMgrForm">

<tr>
<td><b>
    <app:storeMessage key="admin2.site.budgets.label.accountId"/>
</b></td>
<td>
    <html:hidden name="ADMIN2_SITE_BUDGET_MGR_FORM" property="accountId"/>
    <bean:write name="ADMIN2_SITE_BUDGET_MGR_FORM" property="accountId"/>
</td>

<td><b>
    <app:storeMessage key="admin2.site.budgets.label.accountName"/>
</b></td>
<td>
    <bean:write name="ADMIN2_SITE_BUDGET_MGR_FORM" property="accountName"/>
    <html:hidden name="ADMIN2_SITE_BUDGET_MGR_FORM" property="accountName"/>
</td>

<tr>
    <td><b>
        <app:storeMessage key="admin2.site.budgets.label.siteId"/>
    </b></td>
    <td>
        <bean:write name="ADMIN2_SITE_BUDGET_MGR_FORM" property="siteId"/>
    </td>
    <td><b>
        <app:storeMessage key="admin2.site.budgets.label.siteName"/>
    </b></td>
    <td>
        <bean:write name="ADMIN2_SITE_BUDGET_MGR_FORM" property="siteName"/>
    </td>
</tr>

<table width="<%=Constants.TABLEWIDTH800%>" class="results">
<tr align=left>
    <td class="tableheader">
        <app:storeMessage key="admin2.site.budgets.label.costCenterInfo"/>
    </td>
    <td class="tableheader">
        <app:storeMessage key="admin2.site.budgets.label.budgetAssigned"/>
    </td>
    <td class="tableheader">
        <app:storeMessage key="admin2.site.budgets.label.fiscalPeriod"/>
    </td>
    <td class="tableheader">
        <app:storeMessage key="admin2.site.budgets.label.fiscalYear"/>
    </td>
    <td class="tableheader">
        <app:storeMessage key="admin2.site.budgets.label.budgetRemaining"/>
        <br></td>
</tr>
    <% boolean siteBudgetFl = false; %>
<logic:iterate id="budgetsele" name="ADMIN2_SITE_BUDGET_MGR_FORM"
               indexId="i" property="siteBudgetList"
               type="com.cleanwise.view.forms.SiteBudget">
    <%


        BudgetView budgetV = budgetsele.getBudgetView();
        String CostCntrTypeCD = budgetsele.getCostCenterData().getCostCenterTypeCd();

        String budgetType = budgetV.getBudgetData().getBudgetTypeCd();

        boolean ccBudgetReadOnlyFl = true;
        if (RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET.equals(budgetType)) {
            ccBudgetReadOnlyFl = false;
            siteBudgetFl = true;
        }
        boolean showTextBox = false;
        if (RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET.equals(CostCntrTypeCD)) {
            showTextBox = true;
        }


    %>
<tr>
<td>
    <table>
        <tr>
            <td class="bb">
                <app:storeMessage key="admin2.site.budgets.label.name"/>
            </td>
            <bean:define id="lsid" name="ADMIN2_SITE_BUDGET_MGR_FORM" property="siteId" type="java.lang.Integer"/>
            <bean:define id="eleid" name="budgetsele" property="budgetView.budgetData.costCenterId"/>
            <td>
                <bean:write name="budgetsele" property="name"/>
            </td>
        </tr>
        <tr>
            <td class="bb"> <app:storeMessage key="admin2.site.budgets.label.type"/></td>
            <td>
                <bean:write name="budgetsele"
                            property="costCenterData.costCenterTypeCd"/>
            </td>
        </tr>
        <tr>
            <td class="bb"> <app:storeMessage key="admin2.site.budgets.label.id"/></td>
            <td>
                <bean:write name="budgetsele"
                            property="costCenterData.costCenterId"/>
            </td>
        </tr>
    </table>
</td>

<td>

    <% String s = ""; %>

    <table bgcolor="white" cellpadding=2>
        <%
            if (!showTextBox) {
        %>
        <% s = "siteBudgetList[" + i + "].id";%>
        <html:hidden name="ADMIN2_SITE_BUDGET_MGR_FORM" property="<%=s%>" value="<%=String.valueOf(lsid)%>"/>
        <tr>

            <%for (int j = 0, p = 1; p <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalInfo.getFiscalCalenderView()); p++, j++) {%>
            <%if (j % 7 == 0) {%></tr>
        <tr><%}%>
            <% if (Utility.isSet(FiscalCalendarUtility.getMmdd(fiscalInfo.getFiscalCalenderView().getFiscalCalenderDetails(), p))) {%>
            <td class="bb"><%=p%>
            </td>
            <td align="left" width="68">
                    <%property = "budgetAmount["+p+"]";%>
            <td>
                <bean:write name="budgetsele" property="<%=property%>"/>
            </td>
            <% } %>
            <% } %>

        </tr>

        <%} else { %>
        <tr>
            <% s = "siteBudgetList[" + i + "].id";%>
            <html:hidden name="ADMIN2_SITE_BUDGET_MGR_FORM" property="<%=s%>" value="<%=String.valueOf(lsid)%>"/>

            <%int j = 0;%>
            <%for (int p = 1; p <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalInfo.getFiscalCalenderView()); p++, j++) {%>
            <%if (j % 7 == 0) {%></tr>
        <tr><%}%>
            <% if (Utility.isSet(FiscalCalendarUtility.getMmdd(fiscalInfo.getFiscalCalenderView().getFiscalCalenderDetails(), p))) {%>
            <td class="bb"><%=p%>
            </td>
            <td align="left" width="68">
                <% s = "siteBudgetList[" + i + "].budgetAmount[" + p + "]";%>
                <html:text name="ADMIN2_SITE_BUDGET_MGR_FORM" property="<%=s%>" size="5"
                           readonly="<%=ccBudgetReadOnlyFl%>"/>
            </td>
            <% } %>
            <% } %>

        </tr>

        <tr>
            <td align="right" colspan="14">
                <table ID=1192 border="0" cellpadding="1" cellspacing="1" bgcolor="#cccccc">
                    <tr>
                        <td><input type="text" name="setAllBudgetList[<%=i%>]" size="4"/></td>
                        <td><a ID=1193 href="#"
                               onClick="return f_setAllBudgetValue(document.forms['ADMIN2_SITE_BUDGET_MGR_FORM'], '<%=i%>', <%=FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalInfo.getFiscalCalenderView())%>)"><b>Set
                            All</b></a></td>
                    </tr>
                </table>
            </td>
        </tr>
        <%} %>
    </TABLE>
</td>

<%for (int j = 0, p = 1; p <= FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalInfo.getFiscalCalenderView()); p++, j++) {%>
<% if (Utility.isSet(FiscalCalendarUtility.getMmdd(fiscalInfo.getFiscalCalenderView().getFiscalCalenderDetails(), p))) {
    String amount = ((SiteBudget) budgetsele).getBudgetAmount(p);
    if (Utility.isSet(amount)) {
        siteTotalBudget = Utility.addAmt(siteTotalBudget, new BigDecimal(amount));
    }
}
}%>

<td>
    <bean:write name="budgetsele" property="currentBudgetPeriod"/>
</td>
<td><%=budgetsele.getCurrentBudgetYear()%>
</td>


<bean:define id="remainingBudget" type="java.math.BigDecimal"
             name="budgetsele" property="siteBudgetRemaining"/>
<td>
    <i18n:formatCurrency value="<%= remainingBudget %>"
                         locale="<%= Locale.US %>"/>
</td>
</tr>
</logic:iterate>
    <% if (siteBudgetFl) { %>
<tr>
    <td colspan=5 align="right">
        <html:submit property="action">
            <app:storeMessage key="admin2.button.updateBudgets"/>
        </html:submit>
    </td>
</tr>
    <% } %>
</html:form>
</table>

<table width="<%=Constants.TABLEWIDTH800%>" class="results">
    <tr align=left>
        <td align=right colspan=5 class="tableheader">
            <app:storeMessage key="admin2.site.budgets.text.totalBudget"/>
            :
        </td>
        <td><%=siteTotalBudget%>
        </td>
    </tr>
</table>

</div>

</body>

</html:html>






