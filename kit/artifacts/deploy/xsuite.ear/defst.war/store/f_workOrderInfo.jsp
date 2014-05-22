<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ page import="com.cleanwise.view.forms.UserWorkOrderDetailMgrForm" %>
<%@ page import="com.cleanwise.service.api.value.BudgetSpentAmountView" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.BudgetSpendView" %>
<%@ page import="com.cleanwise.service.api.util.WorkOrderUtil" %>
<%@ page import="java.math.BigDecimal" %>

<bean:define id="theForm" name="USER_WORK_ORDER_DETAIL_MGR_FORM"
             type="com.cleanwise.view.forms.UserWorkOrderDetailMgrForm"/>

<div id="workOrderBudgetInfo"></div>
<%
    boolean detailedBudget;
    String showModeLink;
    String showMode = (String) request.getParameter("showMode");
    
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER); 
    
    if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.DISPLAY_COST_CENTER_DETAIL)) {
        if ("budgetDetail".equals(showMode)) {
            detailedBudget = true;
            showModeLink = "<a style=\"color: white\" href=\"../userportal/userWorkOrderDetail.do?action=detail&workOrderId=" + theForm.getWorkOrderId() + "&showMode=budgetNoDetail&display=t_userWorkOrderDetail&tabs=f_userWorkOrderToolbar&secondaryToolbar=f_userSecondaryToolbar\">" +
                                Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "shop.budgetInfo.text.hideDetail")) +
                           "</a>";
        } else {
            detailedBudget = false;
            showModeLink = "<a style=\"color: white\" href=\"../userportal/userWorkOrderDetail.do?action=detail&workOrderId=" + theForm.getWorkOrderId() + "&showMode=budgetDetail&display=t_userWorkOrderDetail&tabs=f_userWorkOrderToolbar&secondaryToolbar=f_userSecondaryToolbar\">" +
                                Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "shop.budgetInfo.text.showDetail")) +
                           "</a>";
        }
    } else {
        detailedBudget = false;
        showModeLink = "";
    }

    boolean authorized = true;
    String currentBudgetMessage;
    String allocatedMessage;
    String spentMessage;
    String differenceMessage;
    String thisAmountMessage;
    String budgetWithOrderMessage;
    String ccMessage;

    currentBudgetMessage = Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.budget.currentBudget"));
    allocatedMessage = Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.budget.allocated"));
    spentMessage = Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.budget.spent"));
    differenceMessage = Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.budget.difference"));
    thisAmountMessage = Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.budget.workOrderTotal"));
    budgetWithOrderMessage = Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.budget.budgetWithOrder"));
    ccMessage = Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.costCenter"));

    BigDecimal thisWorkOrderAmount = new BigDecimal(0);
    String workOrderStatus = ((UserWorkOrderDetailMgrForm) theForm).getWorkOrderDetail().getWorkOrder().getStatusCd();
    if (RefCodeNames.WORK_ORDER_STATUS_CD.NEW_REQUEST.equals(workOrderStatus) ||
        RefCodeNames.WORK_ORDER_STATUS_CD.SENDING_TO_PROVIDER.equals(workOrderStatus) ||
        RefCodeNames.WORK_ORDER_STATUS_CD.SENT_TO_PROVIDER.equals(workOrderStatus) ||
        RefCodeNames.WORK_ORDER_STATUS_CD.ACCEPTED_BY_PROVIDER.equals(workOrderStatus) ||
        RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED.equals(workOrderStatus) ||
        RefCodeNames.WORK_ORDER_STATUS_CD.CLOSED.equals(workOrderStatus) ||
        RefCodeNames.WORK_ORDER_STATUS_CD.APPROVED.equals(workOrderStatus)) {
            thisWorkOrderAmount = WorkOrderUtil.getWorkOrderTotalCostSum(((UserWorkOrderDetailMgrForm) theForm).getWorkOrderDetail().getItemizedService());
    }
    String costCenterId = Utility.strNN(((UserWorkOrderDetailMgrForm) theForm).getCostCenterId());
%>
<bean:define id="ccId" value="<%=costCenterId%>" type="java.lang.String"/>

<script language="JavaScript1.2"><!--

var providerIdx=0;
var amountsArray;

<%if(detailedBudget) {
if(((UserWorkOrderDetailMgrForm)theForm).getDetailBudgetSpent()!=null && !((UserWorkOrderDetailMgrForm)theForm).getDetailBudgetSpent().isEmpty()){  %>

amountsArray = new Array();

<% for(int i=0;i<((UserWorkOrderDetailMgrForm)theForm).getDetailBudgetSpent().size();i++){

 BudgetSpendView budgetSpend = (BudgetSpendView)((UserWorkOrderDetailMgrForm)theForm).getDetailBudgetSpent().get(i);

 BigDecimal allocated = new BigDecimal(0);
 BigDecimal spent = new BigDecimal(0);
 BigDecimal difference = new BigDecimal(0);
 BigDecimal thisAmountValue = new BigDecimal(0);


 allocated  = budgetSpend.getAmountAllocated();
 spent = budgetSpend.getAmountSpent();
 difference = Utility.subtractAmt(allocated,spent );

%>

<logic:equal name="ccId" value="<%=String.valueOf(budgetSpend.getCostCenterId())%>">
<%thisAmountValue = thisWorkOrderAmount;%>
</logic:equal>

<%

 BigDecimal budgetWithOrderValue=Utility.subtractAmt(difference,thisAmountValue);

 %>

var bspV = new BudgetSpentInfo();

bspV.ccName               = '<%=budgetSpend.getCostCenterName()%>';
bspV.spentValue           = '<%=ClwI18nUtil.getPriceShopping(request, spent)%>';
bspV.thisAmountValue      = '<%=ClwI18nUtil.getPriceShopping(request, thisAmountValue)%>';

bspV.allocateValue        = '<%=allocated == null ? "N/A" : ClwI18nUtil.getPriceShopping(request, allocated)%>';
bspV.differenceValue      = '<%=allocated == null ? "N/A" : ClwI18nUtil.getPriceShopping(request, difference)%>';
bspV.budgetWithOrderValue = '<%=allocated == null ? "N/A" : ClwI18nUtil.getPriceShopping(request, budgetWithOrderValue)%>';


amountsArray[providerIdx] = bspV;
providerIdx++;
<%}%>
<%}%>

<%} else {%>

<%if(((UserWorkOrderDetailMgrForm)theForm).getShortBudgetSpent()!=null){%>
amountsArray = new Array();
<%
 BudgetSpentAmountView budgetSpent = ((UserWorkOrderDetailMgrForm)theForm).getShortBudgetSpent();

 BigDecimal allocated  =budgetSpent.getAmountAllocated();
 BigDecimal spent = budgetSpent.getAmountSpent();
 BigDecimal difference = budgetSpent.getAmountDifference();
 BigDecimal thisAmountValue = thisWorkOrderAmount;
 BigDecimal budgetWithOrderValue = budgetSpent.getAmountTotal();
 %>

var bspV = new BudgetSpentInfo();

bspV.ccName               = '<%=Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.budget.allCostCenters"))%>';
bspV.allocateValue        = '<%=ClwI18nUtil.getPriceShopping(request, allocated)%>';
bspV.spentValue           = '<%=ClwI18nUtil.getPriceShopping(request, spent)%>';
bspV.differenceValue      = '<%=ClwI18nUtil.getPriceShopping(request, difference)%>';
bspV.thisAmountValue      = '<%=ClwI18nUtil.getPriceShopping(request, thisAmountValue)%>';
bspV.budgetWithOrderValue = '<%=ClwI18nUtil.getPriceShopping(request, budgetWithOrderValue)%>';

amountsArray[providerIdx] = bspV;
providerIdx++;
<%}%>
<%}%>

function budgetSpentInfoInit(ccMessage,currentBudgetMessage, allocatedMessage, spentMessage, differenceMessage,
                             thisAmountMessage, budgetWithOrderMessage, budgetSpentInfoValues, authorized, showModeLink) {
    budgetSpentDynamicBox.init(currentBudgetMessage, ccMessage,allocatedMessage, spentMessage, differenceMessage,
            thisAmountMessage, budgetWithOrderMessage, budgetSpentInfoValues, authorized, showModeLink);
    budgetSpentDynamicBox.initWriter('workOrderBudgetInfo');
    budgetSpentDynamicBox.write();
}

budgetSpentInfoInit('<%=ccMessage%>','<%=currentBudgetMessage%>',
        '<%=allocatedMessage%>',
        '<%=spentMessage%>',
        '<%=differenceMessage%>',
        '<%=thisAmountMessage%>',
        '<%=budgetWithOrderMessage%>',
        amountsArray,
        '<%=authorized%>',
        '<%=showModeLink%>');

//-->
</script>