<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="theForm"
             name="STORE_WORKFLOW_DETAIL_FORM"
             type="com.cleanwise.view.forms.StoreWorkflowDetailForm"/>

<script language="JavaScript1.2">

function f_hideBox(boxid) {
  document.getElementById(boxid).style.display = 'none';
}

function f_hideAll() {
  /* Reset the radio button for rule type selection. */
  STORE_WORKFLOW_DETAIL_FORM.ruleTypeCd.value="";
  f_hideBox("BOX_userlocate");
  f_hideBox("BOX_agerule");
}

function f_showBox(boxid) {
  document.getElementById(boxid).style.display = 'block';
}

function f_userbox1(actionVal) {
  switch (actionVal) {
  case "Forward for approval":
    f_hideBox("BOX_userlocate");
    f_showBox("BOX_agerule");
  break;
  case "Hold order for review":
    f_hideBox("BOX_userlocate");
    f_showBox("BOX_agerule");
  break;
  case "Send Email":
    f_showBox("BOX_userlocate");
    f_hideBox("BOX_agerule");
  break;
  default:
    f_hideBox("BOX_userlocate");
    f_hideBox("BOX_agerule");
  }
}

function f_userbox2(actionVal) {
  switch (actionVal) {
  case "Forward for approval":
    f_hideBox("BOX_userlocate");
    f_showBox("BOX_agerule");
  break;
  case "Hold order for review":
    f_hideBox("BOX_userlocate");
    f_showBox("BOX_agerule");
  break;
  case "Send Email":
    f_showBox("BOX_userlocate");
    f_hideBox("BOX_agerule");
  break;
  default:
    f_hideBox("BOX_userlocate");
    f_hideBox("BOX_agerule");
  }
}

</script>

<%
 String fwdmsg = RefCodeNames.WORKFLOW_RULE_ACTION.FWD_FOR_APPROVAL;
%>

<div id="BOX_orderTotal">
    <span class="wkrule">
        &nbsp;This&nbsp;Work&nbsp;Order&nbsp;will&nbsp;be<br>&nbsp;forwarded&nbsp;for&nbsp;approval.
        <br>
        <html:hidden name="STORE_WORKFLOW_DETAIL_FORM" property="ruleAction" value="<%=fwdmsg%>" />
        <html:hidden name="STORE_WORKFLOW_DETAIL_FORM" property="totalExp" value=">" />
        <html:hidden name="STORE_WORKFLOW_DETAIL_FORM" property="totalValue" value="-1" />
    </span>
</div>
