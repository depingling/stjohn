<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.forms.StoreAccountMgrDetailForm" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.*" %>
<%@ page import="com.cleanwise.service.api.util.FiscalCalendarUtility" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<html:html>

<table ID=483 class="stpTable">

<tr>
<td class=stpLabel>Account&nbsp;Id</td>
<td><bean:write name="STORE_ACCOUNT_DETAIL_FORM"
   property="accountData.busEntity.busEntityId"
   scope="session"/>
</td>

<td class=stpLabel>Account&nbsp;Name</td>
<td><bean:write name="STORE_ACCOUNT_DETAIL_FORM"
   property="accountData.busEntity.shortDesc"
   scope="session"/>
</td>

</tr>
</table>

<bean:define id="thisAccountId" name="STORE_ACCOUNT_DETAIL_FORM"
   property="accountData.accountId" type="java.lang.Integer" />
<bean:define id="thisForm" name="STORE_ACCOUNT_DETAIL_FORM"
   type="StoreAccountMgrDetailForm" />

<table class="stpTable_sortable" id="ts1" width="<%=Constants.TABLEWIDTH%>">

<thead>
<tr>
<th class="stpTH">Fiscal Year</th>
<th class="stpTH">Period</th>
<%int maxSizeofBudgetPeriods = FiscalCalendarUtility.getMaxSizeofBudgetPeriods(thisForm.getAccountData().getFiscalCalenders());%>
<%for(int i= 0; i< maxSizeofBudgetPeriods;i++){%>
    <th class="stpTH"><%=(i+1)%></th>
<%}%>
<th class="stpTH">Effective Date</th>
</tr>
</thead>


<bean:parameter id="editYear" name="year" value="-1"/>

<tbody id="resTblBdy">
<%
int numOfFiscalCalendars = 0;
numOfFiscalCalendars = thisForm.getAccountData().getFiscalCalenders().size();
final Set<Integer> ids = possibleToEdit(thisForm.getAccountData().getFiscalCalenders());
%>
<%--logic:iterate id="fiscalCal"
               name="STORE_ACCOUNT_DETAIL_FORM"
               property="accountData.fiscalCalenders"
               type="com.cleanwise.service.api.value.FiscalCalenderView"><%
if ( editYear.equals( String.valueOf(fiscalCal.getFiscalCalender().getFiscalYear()) ) ) {
//  thisForm.setFiscalCalUpdate(fiscalCal);
}
%>
</logic:iterate--%>
<%
Calendar rightNow = Calendar.getInstance();
int thisYear = rightNow.get(Calendar.YEAR);
boolean isDataAdd = thisForm.getFiscalCalUpdate().getFiscalCalender().getFiscalCalenderId() == 0;
boolean isDataEdit = thisForm.getFiscalCalUpdate().getFiscalCalender().getFiscalCalenderId() > 0;
%>
<logic:iterate id="fiscalCal"
               name="STORE_ACCOUNT_DETAIL_FORM"
               property="accountData.fiscalCalenders"
               type="com.cleanwise.service.api.value.FiscalCalenderView">
<tr class=stpTD>
<td class=stpTD>

<%

boolean canEdit = ids.contains(fiscalCal.getFiscalCalender().getFiscalCalenderId());
int fiscalYear = fiscalCal.getFiscalCalender().getFiscalYear();
if (canEdit) {%>
<a ID=484 href="accountFiscalCalendar.do?action=editFiscalCalendarData&fiscalCalendarId=<%=fiscalCal.getFiscalCalender().getFiscalCalenderId()%>">
 <% } 
	if(fiscalYear==0) {%>
			<%=Constants.ALL %>
		<%
	}else {%>

	<%=fiscalYear %>

	<%}
if (canEdit) {%> </a> <% } %>

</td>
<td class=stpTD><%=fiscalCal.getFiscalCalender().getPeriodCd() %></td>

    <%
        for(int i =0; i<maxSizeofBudgetPeriods;i++) {
            String mmdd = FiscalCalendarUtility.getMmdd(fiscalCal.getFiscalCalenderDetails(),(i+1));
   %>

<td id="i<%=(i+1)%>_<%=fiscalCal.getFiscalCalender().getFiscalCalenderId()%>" class=stpTD><%=StringUtils.toNonNull(mmdd) %></td>

<%}%> 

<td class=stpTD><%=fiscalCal.getFiscalCalender().getEffDate()%> 
	<%  if ( fiscalYear == thisForm.getCurrentFiscalYear() || (numOfFiscalCalendars==1 && fiscalYear==0)) {
	boolean disableCopyBudgets = !(thisForm.isCopyBudgetButtonShown());
%>
	<html:form styleId="485" name="STORE_ACCOUNT_DETAIL_FORM"
	  action="/storeportal/accountFiscalCalendar.do"
	  scope="session"
	  type="com.cleanwise.view.forms.StoreAccountMgrDetailForm">

				<html:submit property="action" disabled="<%=disableCopyBudgets %>" >
								<app:storeMessage  key="admin.button.copyBudgetToNextYear"/>
							</html:submit>
		</html:form>
	<% } %>
</td>

<%if (isDataEdit || isDataAdd) {%>
<td class=stpTD><a href="#" onclick="return copyMonths(<%=fiscalCal.getFiscalCalender().getFiscalCalenderId()%>);">copy</a></td>
<%}%>
</tr>
</logic:iterate>

</tbody>

 </table>
<html:form styleId="485" name="STORE_ACCOUNT_DETAIL_FORM"
  action="/storeportal/accountFiscalCalendar.do"
  scope="session"
  type="com.cleanwise.view.forms.StoreAccountMgrDetailForm">
<html:submit property="action"><app:storeMessage  key="admin.button.createFiscalCal"/></html:submit>
&nbsp;<b>Number Of Periods:</b> <html:text name="STORE_ACCOUNT_DETAIL_FORM"
  property="numberOfBudgetPeriods" size='4'
  styleId="numberOfBudgetPeriods"/>   
</html:form>
<%if (isDataAdd || isDataEdit) {%>
<br>
<html:form styleId="485" name="STORE_ACCOUNT_DETAIL_FORM"
  action="/storeportal/accountFiscalCalendar.do"
  scope="session"
  type="com.cleanwise.view.forms.StoreAccountMgrDetailForm">

<html:hidden name="STORE_ACCOUNT_DETAIL_FORM"
  property="fiscalBusEntityId"
  value="<%=thisAccountId.toString()%>"/>

<html:hidden name="STORE_ACCOUNT_DETAIL_FORM" property="fiscalCalUpdate.fiscalCalender.fiscalCalenderId"/>

<table ID=486 class="stpTable" >
<tr>
<td class=stpLabel>Fiscal year</td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM"
  property="fiscalYearFormat" size='4' styleId="fiscalYear"/>
</td>
<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<html:link action="/storeportal/accountFiscalCalendar.do?action=setForAllFiscalYears">
	<font color="blue"><u><app:storeMessage  key="admin.accounts.fiscalCalendar.link.setForAllFiscalYears"/></u></font>
	
</html:link>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
</td>
<td class=stpLabel>Period</td>
<td>
<html:hidden name="STORE_ACCOUNT_DETAIL_FORM"
  property="fiscalCalUpdate.fiscalCalender.periodCd"
  value="<%=RefCodeNames.BUDGET_PERIOD_CD.MONTHLY%>"/>
  <%=RefCodeNames.BUDGET_PERIOD_CD.MONTHLY%>
</td>
<td class=stpLabel>Effective Date</td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM"
  property="fiscalEffDate"  size='8' styleId="fiscalEffDate"/>
</td>
</tr>

</table>

<table ID=487>


<tr>
<td class=stpLabel></td>
    <%
            for(int i =0;i<thisForm.getFiscalCalUpdate().getFiscalCalenderDetails().size();i++) {
    %>
    <td class=stpLabel><%=(i + 1)%>
    </td>
    <%
            }
    %>
</tr>
<tr>
<td class=stpLabel>mm/dd</td>
    <%
            for(int i =0;i<thisForm.getFiscalCalUpdate().getFiscalCalenderDetails().size();i++) {
                String property = "fiscalCalUpdate.fiscalCalenderDetails["+i+"].mmdd";
                String styleId = "i"+(i+1);
    %>
    <td><html:text styleId="<%=styleId%>" name="STORE_ACCOUNT_DETAIL_FORM" property="<%=property%>" size='3'/></td>
    <%
    }%>
</tr>
<tr>
	<td>&nbsp;</td>
</tr>
<tr>
<table border='0'> 
	<tr>
	<td>
		<i><app:storeMessage  key="admin.accounts.fiscalCalendar.help.text"/></i>
	</td>
	<td colspan=<%=thisForm.getFiscalCalUpdate().getFiscalCalenderDetails().size()%> align=right>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:hidden name="STORE_ACCOUNT_DETAIL_FORM" property="isSaveFiscalCal" styleId="isSaveFiscalCal"/>
		<html:submit property="action" onclick="return checkYear();">
		<app:storeMessage  key="admin.button.saveFiscalCal"/>
		</html:submit>
	</td>
</tr>
</table>

</tr>
 </table>

</html:form>
<script type="text/javascript">
var existFiscalYear = new Object();<logic:iterate id="fiscalCal"
 name="STORE_ACCOUNT_DETAIL_FORM" property="accountData.fiscalCalenders" type="com.cleanwise.service.api.value.FiscalCalenderView">
existFiscalYear['<%=fiscalCal.getFiscalCalender().getFiscalYear()%>'] = true;</logic:iterate>
function checkYear() {
	document.getElementById("isSaveFiscalCal").value = "yes";
	var obj = document.getElementById('fiscalYear');

  <%if(isDataAdd) {%>
//  var obj = document.getElementById('fiscalYear');
  var effObj = document.getElementById('fiscalEffDate');
  if (obj) {
    var year = parseInt(obj.value);

    if (obj.value.length == 0 || ((new String(obj.value).toLowerCase())!='all' && isNaN(year))) {
      alert("Only 'All' or integer value is allowed in Fiscal Year text field!");
      return false;
    } else if (false && existFiscalYear['' + year]) {
      alert("Fiscal year '" + year + "' already exists!");
      return false;
    } else if (false && (effObj == null || (effObj != null && effObj.value.length == 0)  )) {
      alert ("Please enter correct Effective Date!");
      return false;
    } else if (false && !(verifyDate(effObj.value))){
      return false;
    } else if (false && year == 0){
      var period = document.getElementById('i1');
      if (period.value != "1/1"){
        alert ("Incorrect date of 1-st period.");
        return false;
      }
      //special case year 0 is allowed
      return true;
     } else if (false && year < <%=thisYear%>) {
      alert("Fiscal Calendar Year " + year + " updates are not allowed.\nOnly years <%=thisYear%> and later can be modified.");
      return false;
   }

  }<%} else {%>
  if (false && obj.value == 0){
     var period = document.getElementById('i1');
     if (period.value != "1/1"){
       alert ("Incorrect date of 1-st period.");
       return false;
     }
   }<%}%>
  return true;
}

function copyMonths(id) {
  for (var i = 1; i < 14; i++) {
    var o1 = document.getElementById("i" + i + "_" + id);
    var o2 = document.getElementById("i" + i);
    if (o1 && o2) {
       o2.value = o1.innerHTML;
    }
  }
return false;
}
function verifyDate (dateStr){
  var date_array = dateStr.split('/');
  var month = date_array[0]-1 ;
  var day = date_array[1];
  var year = date_array[2];
  var source_date = new Date(year,month,day);
  if(  year != source_date.getFullYear() ||
     month != source_date.getMonth() ||
     day != source_date.getDate() ){
     alert('Date format is not valid!');
     return false;
  }
  return true;
}

</script><%}%>
</html:html>

<%!
//FiscalCalenderDataVector
public final static Set<Integer> possibleToEdit(
        List data) {
    final long curTime = System.currentTimeMillis();
    final Set<Integer> res = new HashSet<Integer>();
    boolean wasEqual = false;
    int currentId = -1;
    long min = Long.MAX_VALUE;
    for (int i = 0; data != null && i < data.size(); i++) {
        FiscalCalenderView item = (FiscalCalenderView) data.get(i);
        if (item.getFiscalCalender().getEffDate().getTime() >= curTime) {
            res.add(item.getFiscalCalender().getFiscalCalenderId());
            if (item.getFiscalCalender().getEffDate().getTime() == curTime) {
                wasEqual = true;
            }
        } else {
            long diff = curTime - item.getFiscalCalender().getEffDate().getTime();
            min = Math.min(diff, min);
            if (min == diff) {
                currentId = item.getFiscalCalender().getFiscalCalenderId();
            }
        }
    }
    if (currentId > 0 && wasEqual == false) {
        res.add(currentId);
    }
    return res;
}
%>