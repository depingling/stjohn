<%@ page language="java" %>
   <%@ page import="com.cleanwise.view.utils.Constants" %>
   <%@ page import="java.util.Hashtable"%>

   <%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
   <%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
   <%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
   <%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

   <script language="JavaScript1.2">
 <!--
function actionSubmit(formNum, action) {
 var actions;
 actions=document.forms[formNum]["action"];
 //alert(actions.length);
 for(ii=actions.length-1; ii>=0; ii--) {
   if(actions[ii].value=='hiddenAction') {
     actions[ii].value=action;
     document.forms[formNum].submit();
     break;
   }
 }
 return false;
 }
-->
</script>

   <head>
   <link rel="stylesheet" href="../externals/styles.css">
   </head>
   <body>
   <div class="text">
<table ID=1183 cellspacing="0" border="0" width="769"  class="mainbody">

       <tr><td><b>Account&nbsp;Id:</b></td>
       <td><bean:write name="STORE_ADJUSTMENT_LEDGER_FORM" property="accountId" scope="session"/></td>
       <td><b>Account&nbsp;Name:</b></td>
       <td><bean:write name="STORE_ADJUSTMENT_LEDGER_FORM" property="accountName" scope="session"/></td>
       </tr>
       <tr>
       <td><b>Site&nbsp;Id:</b> </td>
       <td><bean:write name="STORE_ADJUSTMENT_LEDGER_FORM" property="siteId" scope="session"/></td>
       <td><b>Site&nbsp;Name:</b> </td>
       <td><bean:write name="STORE_ADJUSTMENT_LEDGER_FORM" property="siteName" scope="session"/></td>
       </tr>
<tr><td colspan="4">&nbsp;</td></tr>
<tr><td colspan="4">&nbsp;</td></tr>
</table>

   <table ID=1184 cellspacing="0" border="0" width="769"  class="mainbody">
   <html:form styleId="1185" name="STORE_ADJUSTMENT_LEDGER_FORM" scope="session" action="storeportal/storeSiteAdjustmentLedger.do"
   scope="session" type="com.cleanwise.view.forms.StoreAdjustmentLedgerForm">
   <bean:define  id ="sbp" name="STORE_ADJUSTMENT_LEDGER_FORM" property="selectedBudgetPeriod"/>
   <bean:define id="budgetPeriods" name="STORE_ADJUSTMENT_LEDGER_FORM" property="budgetPeriods" type="java.util.HashMap<Integer, String>"/>
 <tr>
   <td width="70"><b>Budget Year:</b></td>
   <td width="145">
       <html:select name="STORE_ADJUSTMENT_LEDGER_FORM" onchange="actionSubmit(0,'changeYear');" property="selectedBudgetYear">
       <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
       <logic:iterate id="year"  name="Site.budget.year.vector" type="java.lang.String">
       <html:option  value="<%=year%>"/>
       </logic:iterate>
       </html:select></td>
   <td width="55"><b>Type:</b></td>
   <td width="87"><bean:write name="STORE_ADJUSTMENT_LEDGER_FORM" property="selectedBudgetPeriodType"/></td>
   <td width="64"><b>Period:</b></td>
   <td width="168" align="left" >

       <html:select name="STORE_ADJUSTMENT_LEDGER_FORM" onchange="actionSubmit(0,'changeBudgetPeriod');" property="selectedBudgetPeriod">
       <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
       <logic:iterate id="i"  name="Site.budget.period.vector" type="java.lang.Integer">
           <%  String budgetPeriod = budgetPeriods.get(i);%>
           <html:option  value="<%=budgetPeriod%>"/>
       </logic:iterate>
       </html:select></td>
   <td width="46">&nbsp;</td>
   <td width="51">&nbsp;</td>
 </tr>
 <tr>
   <td ><b>Cost Center:</b></td>
   <td colspan="5">
       <html:select name="STORE_ADJUSTMENT_LEDGER_FORM" onchange="actionSubmit(0,'changeCostCenter');" property="selectedCostCenter">
       <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
       <logic:iterate id="costcenter"  name="Site.budget.costcenter.vector" type="java.lang.String">
       <html:option  value="<%=costcenter%>"/>
       </logic:iterate>
       </html:select></td>
   <td>&nbsp;</td>
   <td>&nbsp;</td>
 </tr>
 <tr>
   <td ><b>Adjustment:</b></td>
   <td colspan="5" align="left"><html:text name="STORE_ADJUSTMENT_LEDGER_FORM" property="budgetAdjustment" size="20"/>
   <html:button  property="action"  value ="Set" onclick="actionSubmit(0,'Set Adjustment');">
   </html:button></td>
   <td>&nbsp;</td>
   <td>&nbsp;</td>
 </tr>
 <tr>
   <td valign="top" ><b>Comments:<span class="reqind">*</span></b></td>
   <td colspan="5" align="left" valign="top">
       <html:textarea name="STORE_ADJUSTMENT_LEDGER_FORM" cols="57" rows="5"
    property="comments"/>
   <td>&nbsp;</td>
   <td>&nbsp;</td>
 </tr>

 <tr>
   <td><b>Budget Type:</b></td>
   <td colspan="5" align="left" valign="top"><bean:write name="STORE_ADJUSTMENT_LEDGER_FORM" property="budgetTypeCd"/></td>
   <td>&nbsp;</td>
   <td>&nbsp;</td>
 </tr>

 <tr>
   <td>&nbsp;</td>
   <td colspan="5" align="left" valign="top">&nbsp</td>
   <td>&nbsp;</td>
   <td>&nbsp;</td>
 </tr>

   <tr>
   <td colspan="6" align="left" valign="top">

    <bean:define id="htBudgetInfo" name="Site.budget.info"/>
    <bean:define id="htBudgetInfoWithSpecAdj" name="Site.budget.infoWithSpecifedAdjustment"/>
    <%boolean presentBudgetData=false;%>
    <logic:present   name="STORE_ADJUSTMENT_LEDGER_FORM" property="budget">
   <%presentBudgetData=true;%>
    </logic:present>
<%boolean totalChangeFl=false;%>
     <%if(htBudgetInfo!=null&&((Hashtable)htBudgetInfo).size()>0) {%>

       <table ID=1186 width="649" border="1">
     <tr>
       <td width="20"><b>Period</b></td>
       <td width="120"><b>Start Date</b></td>
       <td width="120"> <b>Allocated</b></td>
       <td width="120"> <b>Spent</b> </td>
       <td width="120"> <b>Adjustment</b> </td>
       <td width="120"> <b>Difference</b> </td>
     </tr>

 <logic:iterate id="i"  name="Site.budget.period.vector" type="java.lang.Integer">


     <%
         String budgetPeriod = budgetPeriods.get(i);
         String[] params=null;
  String[] paramsWithSpecifedAdjustment=null;
  boolean changeFl=false;

if(budgetPeriod!=null)
{
params= ((String[])((Hashtable)htBudgetInfo).get(String.valueOf(i)));

if(htBudgetInfoWithSpecAdj!=null){
  paramsWithSpecifedAdjustment= ((String[])((Hashtable)htBudgetInfoWithSpecAdj).get(String.valueOf(i)));
}
if(params!=null&&paramsWithSpecifedAdjustment!=null)
{
 if(!params[2].equals(paramsWithSpecifedAdjustment[2]))
 {
   changeFl=true;
   if(!totalChangeFl) totalChangeFl=true;
 }
}
%>
<%if(budgetPeriod.equals((String)sbp)) {%>
<tr class="rowa">
<%} else {%><tr><%}}else{%><tr><%}%>
       <td><%=i%></td>
       <td><%=budgetPeriod!=null?budgetPeriod:"N/A"%></td>
       <%if(params!=null){%>
       <td><%=presentBudgetData?params[0]:"N/A"%></td>
       <td><%=params[1]%></td>
       <%if(!changeFl) {%>
       <td><%=params[2]%></td>
       <td><%=presentBudgetData?params[3]:"N/A"%></td>
       <%} else {%>
       <td> <font color=red><%=paramsWithSpecifedAdjustment[2]%></font> (<%=params[2]%>)</td>
       <td><%if(presentBudgetData) {%>
        <font color=red><%=paramsWithSpecifedAdjustment[3]%></font>(<%=params[3]%>)
       <%} else {%><%="N/A"%><%}%></td>

     <%}} else {%>
       <td>N/A</td>
       <td>N/A</td>
       <td>N/A</td>
       <td>N/A</td>
      <%}%>
     </tr>

       </logic:iterate>
 <%String[] totalParams = ((String[]) ((Hashtable) htBudgetInfo).get("Total"));
   String[] totalParamsWithSpecifedAdjustment = ((String[]) ((Hashtable) htBudgetInfoWithSpecAdj).get("Total"));%>
     <tr>
       <td></td>
       <td><b>Total:</b></td>
       <% if(!totalChangeFl){%>
       <td><%=presentBudgetData?totalParams[0]:"N/A"%></td>
       <td><%=totalParams[1]%></td>
       <td><%=totalParams[2]%></td>
       <td><%=presentBudgetData?totalParams[3]:"N/A"%></td>
      <%} else {%>
       <td><%=presentBudgetData?totalParams[0]:"N/A"%></td>
       <td><%=totalParams[1]%></td>
       <td> <font color=red><%=totalParamsWithSpecifedAdjustment[2]%></font> (<%=totalParams[2]%>)</td>
       <td><%if(presentBudgetData) {%>
       <font color=red><%=totalParamsWithSpecifedAdjustment[3]%></font>(<%=totalParams[3]%>)
       <%} else {%><%="N/A"%><%}%></td>
     <%}%>
     </tr>
   </table><%} else {%> <b>No info</b><%}%>
   </td>

   <td align="left" valign="top">&nbsp;</td>
 </tr>
 <tr>
   <td colspan="8" >&nbsp;</td>
 </tr>
 <tr>
   <td colspan="8" align="center" ><%if(totalChangeFl){%> <html:button  property="action"  value ="Save" onclick="actionSubmit(0,'Save Adjustments');">
   </html:button><%}%></<td>
 </tr>
<html:hidden  property="action" value="hiddenAction"/>
   </html:form>
   </table>
   </div>
   </body>
   </html>

