<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<script language="JavaScript">
    <!--

    function setAndSubmit(fid, vv, value) {
      var aaa = document.forms[fid].elements[vv];
      aaa.value=value;
      aaa.form.submit();
      return false;

    }
    -->
</script>

<bean:define id="theForm" name="USER_WORK_ORDER_SCHEDULER_FORM"
             type="com.cleanwise.view.forms.UserWorkOrderSchedulerForm"/>
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<%
    String localeCd = "" + ClwI18nUtil.getUserLocale(request);
    boolean enUsDateFl = ("en_US".equals(localeCd));
    String browser = (String) request.getHeader("User-Agent");
    String isMSIE = "";
    if (browser != null && browser.indexOf("MSIE") >= 0) {
        isMSIE = "Y";
%>

<script language="JavaScript" src="../externals/calendar.js"></script>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
        marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
<% } else { %>
<script language="JavaScript" src="../externals/calendarNS.js"></script>
<% } %>
<table width="100%">
    <tr>
        <td><jsp:include flush='true' page="<%=ClwCustomizer.getStoreFilePath(request,\"t_userWorkOrderCtx.jsp\")%>"/></td>
    </tr>
<tr>
<td>
<html:form styleId="ost" action="/userportal/userWorkOrderScheduler.do">
<table width="100%">
<tr>
<!-- start left half -->
<td>
<table>
<tr>
    <td class="subheaders" colspan="2">
	<span class="reqind">*</span>
	<b><app:storeMessage key="shop.orderSchedule.text.schedule:"/></b>
	    <html:hidden property="scheduleTypeChange" value=""/>
        <html:select name="USER_WORK_ORDER_SCHEDULER_FORM"
		             property="scheduleType"
                     onchange="document.forms[0].scheduleTypeChange.value='true'; document.forms[0].action.value='scheduleTypeChange'; document.forms[0].submit()">
            <html:option value="<%=RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK%>">
                <app:storeMessage key="shop.orderSchedule.text.weekly"/>
            </html:option>
            <html:option value="<%=RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH%>">
                <app:storeMessage key="shop.orderSchedule.text.monthly"/>
            </html:option>
            <html:option value="<%=RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH%>">
                <app:storeMessage key="shop.orderSchedule.text.custom"/>
            </html:option>
        </html:select>
    </td>
</tr>
<tr>
    <td colspan="2">
        <html:radio name="USER_WORK_ORDER_SCHEDULER_FORM" property="scheduleAction"
                    value="<%=RefCodeNames.ORDER_SCHEDULE_CD.NOTIFY%>"/>
        <app:storeMessage key="shop.orderSchedule.text.remindMeToPlaceMyOrder"/>
		<br>
        <html:radio name="USER_WORK_ORDER_SCHEDULER_FORM" property="scheduleAction"
                    value="<%=RefCodeNames.ORDER_SCHEDULE_CD.PLACE_ORDER%>"/>
        <app:storeMessage key="shop.orderSchedule.text.automaticallyPlaceMyOrder"/>
    </td>
</tr>
<tr>
    <td>
        <span class="subheaders"><span class="reqind">*</span></span>
		<b>
            <app:storeMessage key="shop.orderSchedule.text.startDate"/>:
            (<%=ClwI18nUtil.getUIDateFormat(request)%>)
        </b>
    </td>
	<td><html:text name="USER_WORK_ORDER_SCHEDULER_FORM" property="startDate" size="10"/> <% if (enUsDateFl) {
            if ("Y".equals(isMSIE)) { %>
        <a href="#"
           onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].STARTDATE, document.forms[0].startDate, null, -7300, 7300, null, false);"
           title="Choose Date"><img name="STARTDATE"
                                    src="../externals/images/showCalendar.gif"
                                    width=19 height=19 border=0 align=absmiddle
                                    style="position:relative"
                                    onmouseover="window.status='Choose Date';return true"
                                    onmouseout="window.status='';return true"></a>
        <% } else { %>
        <a href="javascript:show_calendar('forms[0].startDate');"
           onmouseover="window.status='Choose Date';return true;"
           onmouseout="window.status='';return true;"
           title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19
                                    height=19 border=0></a>
        <% }
        } %>       </td>
</tr>
<tr>
    <td>
	   <span class="subheaders"><span class="reqind">&nbsp;&nbsp;</span></span>
        <b>
            <app:storeMessage key="shop.orderSchedule.text.endDate"/>:
            (<%=ClwI18nUtil.getUIDateFormat(request)%>)
        </b>
    </td>
	<td><html:text name="USER_WORK_ORDER_SCHEDULER_FORM" property="endDate" size="10"/>
	  <% if (enUsDateFl) {
            if ("Y".equals(isMSIE)) { %>
        <a href="#"
           onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].ENDDATE, document.forms[0].endDate, null, -7300, 7300, null, false);"
           title="Choose Date"><img name="ENDDATE"
                                    src="../externals/images/showCalendar.gif"
                                    width=19 height=19 border=0 align=absmiddle
                                    style="position:relative"
                                    onmouseover="window.status='Choose Date';return true"
                                    onmouseout="window.status='';return true"></a>
        <% } else { %>
        <a href="javascript:show_calendar('forms[0].endDate');"
           onmouseover="window.status='Choose Date';return true;"
           onmouseout="window.status='';return true;"
           title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19
                                    height=19 border=0></a>
        <% }
        } %>      </td>
</tr>
<tr>
    <td colspan="2">
        <b><app:storeMessage key="shop.orderSchedule.text.ccEmailAddress:"/></b><br>
        <html:text name="USER_WORK_ORDER_SCHEDULER_FORM" property="ccEmail" size="45"/>
    </td>
</tr>
<tr>
    <td colspan="2">
        <hr>
    </td>
</tr>
<% String scheduleType = theForm.getScheduleType(); %>
<!-- Monthly Day Schedule -->
<% if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH.equals(scheduleType)) { %>
<tr>
    <td colspan="2">
        <table align="center">
            <tr>
                <td colspan="10" align="left">
                    <b>&nbsp;
                        <app:storeMessage key="shop.orderSchedule.text.placeOrderMonthlyOn:"/>
                    </b>
                </td>
            </tr>
            <tr>
                <% for (int ii = 1; ii < 11; ii++) { %>
                <td align="center"><%=ii%>
                </td>
                <% } %>
            </tr>
            <tr>
                <% for (int ii = 1; ii < 11; ii++) { %>
                <td align="center">
                    <html:multibox name="USER_WORK_ORDER_SCHEDULER_FORM" property="monthDays" value="<%=\"\"+ii%>"/>
                </td>
                <% } %>
            </tr>
            <tr>
                <% for (int ii = 11; ii < 21; ii++) { %>
                <td align="center"><%=ii%>
                </td>
                <% } %>
            </tr>
            <tr>
                <% for (int ii = 11; ii < 21; ii++) { %>
                <td align="center">
                    <html:multibox name="USER_WORK_ORDER_SCHEDULER_FORM" property="monthDays" value="<%=\"\"+ii%>"/>
                </td>
                <% } %>

            </tr>
           <tr>
                <% for (int ii = 21; ii < 29; ii++) { %>
                <td align="center"><%=ii%>
                </td>
                <% } %>    <td align="center" colspan ="2">
                    <app:storeMessage key="shop.orderSchedule.text.lastDay"/>
                </td>
            </tr>
            <tr>
                <% for (int ii = 21; ii < 29; ii++) { %>
                <td align="center">
                    <html:multibox name="USER_WORK_ORDER_SCHEDULER_FORM" property="monthDays" value="<%=\"\"+ii%>"/>
                </td>
                <% } %>
             <td colspan ="2" align="center">
                    <html:multibox name="USER_WORK_ORDER_SCHEDULER_FORM" property="monthDays" value="32"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<!-- Monthly Week Schedule -->
<% } else if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH.equals(scheduleType)) { %>
<tr>
    <td colspan="2">
        <b>&nbsp;<span class="reqind">*</span>
            <app:storeMessage key="shop.orderSchedule.text.customizeOrderWithinEachMonth:"/>
        </b>
    </td>
</tr>
<tr>
    <td colspan="2">
        <table align="center">
            <tr>
                <td align="center">
                    <app:storeMessage key="global.text.month.jan"/>
                </td>
                <td align="center">
                    <app:storeMessage key="global.text.month.feb"/>
                </td>
                <td align="center">
                    <app:storeMessage key="global.text.month.mar"/>
                </td>
                <td align="center">
                    <app:storeMessage key="global.text.month.apr"/>
                </td>
                <td align="center">
                    <app:storeMessage key="global.text.month.may"/>
                </td>
                <td align="center">
                    <app:storeMessage key="global.text.month.jun"/>
                </td>
                <td align="center">
                    <app:storeMessage key="global.text.month.jul"/>
                </td>
                <td align="center">
                    <app:storeMessage key="global.text.month.aug"/>
                </td>
                <td align="center">
                    <app:storeMessage key="global.text.month.sep"/>
                </td>
                <td align="center">
                    <app:storeMessage key="global.text.month.oct"/>
                </td>
                <td align="center">
                    <app:storeMessage key="global.text.month.nov"/>
                </td>
                <td align="center">
                    <app:storeMessage key="global.text.month.dec"/>
                </td>
            </tr>
            <tr>
                <% for (int ii = 0; ii < 12; ii++) { %>
                <td align="center">
                    <html:multibox name="USER_WORK_ORDER_SCHEDULER_FORM" property="monthMonths" value="<%=\"\"+ii%>"/>
                </td>
                <% } %>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td  colspan="2">
        <span class="reqind">*</span>
        <app:storeMessage key="shop.orderSchedule.text.week:"/>
        <html:select name="USER_WORK_ORDER_SCHEDULER_FORM" property="monthWeeks" styleClass="smalltext">
            <html:option value="1">
                <app:storeMessage key="global.text.month.first"/>
            </html:option>
            <html:option value="2">
                <app:storeMessage key="global.text.month.second"/>
            </html:option>
            <html:option value="3">
                <app:storeMessage key="global.text.month.third"/>
            </html:option>
            <html:option value="4">
                <app:storeMessage key="global.text.month.fourth"/>
            </html:option>
            <html:option value="5">
                <app:storeMessage key="global.text.month.last"/>
            </html:option>
        </html:select>
    </td>
</tr>
<tr>
<td colspan="2">
 <span class="reqind">*</span>
        <app:storeMessage key="shop.orderSchedule.text.day:"/>
        <html:select name="USER_WORK_ORDER_SCHEDULER_FORM" property="monthWeekDay" styleClass="smalltext">
            <html:option value="1">
                <app:storeMessage key="global.text.month.sunday"/>
            </html:option>
            <html:option value="2">
                <app:storeMessage key="global.text.month.monday"/>
            </html:option>
            <html:option value="3">
                <app:storeMessage key="global.text.month.tuesday"/>
            </html:option>
            <html:option value="4">
                <app:storeMessage key="global.text.month.wednesday"/>
            </html:option>
            <html:option value="5">
                <app:storeMessage key="global.text.month.thursday"/>
            </html:option>
            <html:option value="6">
                <app:storeMessage key="global.text.month.friday"/>
            </html:option>
            <html:option value="7">
                <app:storeMessage key="global.text.month.saturday"/>
            </html:option>
        </html:select>
		</td>
</tr>

<!-- Weekly Schedule -->
<% } else { %>
<tr>
    <td colspan="2">
        <table class="smalltext" align="left" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td align="center">
                    <span class="reqind">*</span><b><app:storeMessage key="shop.orderSchedule.text.placeOrderEveryWeekOn:"/></b>
						<br>
				    <app:storeMessage key="global.text.month.sunday"/>
                    <html:multibox name="USER_WORK_ORDER_SCHEDULER_FORM" property="weekDays" value="1"/>
                    &nbsp;&nbsp;
                    <app:storeMessage key="global.text.month.monday"/>
                    <html:multibox name="USER_WORK_ORDER_SCHEDULER_FORM" property="weekDays" value="2"/>
                    &nbsp;&nbsp;
                    <app:storeMessage key="global.text.month.tuesday"/>
                    <html:multibox name="USER_WORK_ORDER_SCHEDULER_FORM" property="weekDays" value="3"/>
                    &nbsp;&nbsp;
					<br>
					<app:storeMessage key="global.text.month.wednesday"/>
                    <html:multibox name="USER_WORK_ORDER_SCHEDULER_FORM" property="weekDays" value="4"/>
                    &nbsp;&nbsp;
                    <app:storeMessage key="global.text.month.thursday"/>
                    <html:multibox name="USER_WORK_ORDER_SCHEDULER_FORM" property="weekDays" value="5"/>
                    &nbsp;&nbsp;
                    <app:storeMessage key="global.text.month.friday"/>
                    <html:multibox name="USER_WORK_ORDER_SCHEDULER_FORM" property="weekDays" value="6"/>
                    &nbsp;&nbsp;
                    <app:storeMessage key="global.text.month.saturday"/>
                    <html:multibox name="USER_WORK_ORDER_SCHEDULER_FORM" property="weekDays" value="7"/>
                </td>
            </tr>

        </table>
    </td>
</tr>
<% } %>
<!-- Also Dates -->
<tr><td colspan="2">&nbsp;</td>
</tr>
<tr>
    <td colspan="2" style="padding-left: 5px;">
        <b>
            <app:storeMessage key="shop.orderSchedule.text.placeAdditionalOrdersOn"/>:
            (<%=ClwI18nUtil.getUIDateFormat(request)%>)
        </b>
        <%  if(enUsDateFl){if ("Y".equals(isMSIE)) { %>
        <a href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].ALSODATES, document.forms[0].alsoDates, null, -7300, 7300, null, true);"
           title="Choose Date"><img name="ALSODATES" src="../externals/images/showCalendar.gif"
                                    width=19 height=19 border=0 align=absmiddle style="position:relative"
                                    onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true"></a>
        <% } else {  %>
        <a href="javascript:show_calendar('forms[0].alsoDates');"
           onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;"
           title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
        <% }}  %>
        <html:text name="USER_WORK_ORDER_SCHEDULER_FORM" property="alsoDates" size="45" /><br>
    </td>
</tr>
<!-- Except Dates -->
<tr>
    <td colspan="2" style="padding-left: 5px;">
        <b>
            <app:storeMessage key="shop.orderSchedule.text.deleteAdditionalOrdersOn"/>:
            (<%=ClwI18nUtil.getUIDateFormat(request)%>)
        </b>
        <%  if(enUsDateFl){if ("Y".equals(isMSIE)) { %>
        <a href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].EXCLUDEDATES, document.forms[0].excludeDates, null, -7300, 7300, null, true);"
           title="Choose Date"><img name="EXCLUDEDATES" src="../externals/images/showCalendar.gif"
                                    width=19 height=19 border=0 align=absmiddle style="position:relative"
                                    onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true"></a>
        <% } else {  %>
        <a href="javascript:show_calendar('forms[0].excludeDates');"
           onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;"
           title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
        <% }}  %>
        <html:text name="USER_WORK_ORDER_SCHEDULER_FORM" property="excludeDates" size="45" />
    </td>
</tr>
</table>
</td>

<!-- end left half, begin right -->
<td valign="top">
<table align="center">
<tr>
    <td>
        <%
            if(theForm.getOrderSchedules()!=null && !theForm.getOrderSchedules().isEmpty() ) {
                List scheduleL = theForm.getOrderSchedules();
                for(int ii=0; ii<scheduleL.size(); ii++) {
                    String ss = theForm.getOrderScheduleName(request,ii);
                    String idS = theForm.getOrderScheduleId(ii);
        %>
        <%=ss%>
        <html:hidden property="orderScheduleId" value ="<%=idS%>"/>
        <% } %>
        <% } else { %>
        <html:hidden property="orderScheduleId" value ="<%="0"%>"/>
        <%}%>
    </td>
</tr>
<tr><td><!-- Calendar -->

    <table align=center>
        <%

            for ( int monthIdx = 0; monthIdx < 12; monthIdx++ ) {

        %>

        <% if ( monthIdx == 0 ||  monthIdx == 3 ||
                monthIdx == 6 ||  monthIdx == 9 ) { %> <tr> <% } %>

        <td>

            <table class="smalltext" align="left" border="0" cellpadding="2" cellspacing="0">
                <tr>
                    <td colspan="7" align="center">
                        <%   String monthKey = "global.text.month."+monthIdx; %>

                        <b><app:storeMessage key="<%=monthKey%>"/> &nbsp;&nbsp;<%=theForm.getScheduleYear()%></b>
                    </td>
                </tr>
                <tr>
                    <td align="center"><b><app:storeMessage key="shop.orderSchedule.text.1-sun"/></b></td>
                    <td align="center"><b><app:storeMessage key="shop.orderSchedule.text.1-mon"/></b></td>
                    <td align="center"><b><app:storeMessage key="shop.orderSchedule.text.1-tue"/></b></td>
                    <td align="center"><b><app:storeMessage key="shop.orderSchedule.text.1-wed"/></b></td>
                    <td align="center"><b><app:storeMessage key="shop.orderSchedule.text.1-thu"/></b></td>
                    <td align="center"><b><app:storeMessage key="shop.orderSchedule.text.1-fri"/></b></td>
                    <td align="center"><b><app:storeMessage key="shop.orderSchedule.text.1-sat"/></b></td>

                </tr>


                <%
                    int [] daysInMonth = theForm.getDaysInMonth(monthIdx);
                    for ( int j = 0; j < daysInMonth.length; j++ ) {
                        int kk = daysInMonth[j];
                %>
                <% if ( j == 0 || j == 7 || j == 14 || j == 21 || j == 28 || j == 35) { %> <tr> <% } %>

                <% if(kk >= 100 && kk <10000){ %>
                <td align="right"><font color="blue"><b><%=(kk/100)%></b></font></td>
                <% } else if(kk > 10000){ %>
                <td align="right"><font color="red"><b><%=(kk/10000)%></b></font></td>
                <% } else if(kk>0) { %>
                <td align="right"><%=kk%></td>
                <% } else {%>
                <td align="right">&nbsp;</td>
                <% } %>

                <% if ( j == 6 || j == 13 || j == 20 || j == 27 || j == 34 ) { %> </tr> <% } %>
                <%                                             } %>




            </table>


        </td>




        <% if ( monthIdx == 2 ||  monthIdx == 5 ||  monthIdx == 8 ||  monthIdx == 11 ) { %>
    </tr>
        <% } %>

        <% } %>

    </table>

    <!-- End Calendar  -->  </td></tr>

<tr>
    <td align="center"><a href="#" class="linkButton" onclick="setAndSubmit('ost','action','navigateBackward');"><img border="0" alt="Backward" src="<%=IMGPath%>/calBackward.gif"></a>&nbsp;&nbsp;
        <a  href="#" class="linkButton" onclick="setAndSubmit('ost','action','navigateForward');"> <img border="0" alt="Forward" src="<%=IMGPath%>/calForward.gif"></a> </td>
</tr>

<tr>
    <td align="center">

        <a href="#" class="linkButton" onclick="setAndSubmit('ost','action','saveSchedule');">
            <img src='<%=IMGPath + "/b_save.gif"%>'
                      border="0"/><app:storeMessage key="shop.orderSchedule.text.saveSchedule"/></a>
        <% if(theForm.getOrderScheduleId()>0) {  %>
        <a href="#" class="linkButton" onclick="setAndSubmit('ost','action','deleteSchedule');">
            <img src='<%=IMGPath + "/b_remove.gif"%>' border="0"/><app:storeMessage key="shop.orderSchedule.text.deleteSchedule"/></a>
        <% } %>
    </td>
</tr>

</table>

</td> <!-- End right half -->
</tr>
</table>
<html:hidden property="action" value="CCCCCCC"/>
</html:form>
</td>
</tr>
</table>





