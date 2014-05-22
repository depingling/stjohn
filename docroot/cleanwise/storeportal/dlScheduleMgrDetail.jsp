<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>


<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<script language="JavaScript1.2">
    <!--
    function SetChecked(val) {
        var dml=document.forms[0];
        var ellen = dml.elements.length;
        for(j=0; j<ellen; j++) {
            if (dml.elements[j].name=='action') {
                dml.elements[j].value=val;
            }
        }
        dml.submit();
    }

    function setAndSubmit(fid, vv, value) {
        var aaa = document.forms[fid].elements[vv];
        aaa.value=value;
        aaa.form.submit();
        return false;
    }

    //-->
</script>
<bean:define id="theForm" name="STORE_DELIVERY_SCHEDULE_FORM" type="com.cleanwise.view.forms.StoreDeliveryScheduleMgrForm"/>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%
    String browser = (String)  request.getHeader("User-Agent");
    String isMSIE = "";
    if(browser!=null && browser.indexOf("MSIE")>=0) {
        isMSIE = "Y";
%>
<script language="JavaScript" src="../externals/calendar.js"></script>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
        marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
<% } else {  %>
<script language="JavaScript" src="../externals/calendarNS.js"></script>
<% }  %>

<div class="text">
<table ID=219 width="769" cellspacing="0" border="0" class="mainbody">

<html:form styleId="dlsmd" action="storeportal/dlschdldet.do">
<html:hidden name="STORE_DELIVERY_SCHEDULE_FORM" property="contentPage"  value="dlScheduleMgrDetail.jsp"/>
<tr>
    <td class="smalltext">
        <!-- Distributor info -->
        <table ID=220 class="smalltext" border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td><b>Distributor&nbsp;Id:</b>
                    <bean:write name="STORE_DIST_DETAIL_FORM" property="intId"/></td>
                <td><b>Name:</b>
                    <bean:write name="STORE_DIST_DETAIL_FORM" property="name"/></td>
                <td><b>Type:</b>
                    <bean:write name="STORE_DIST_DETAIL_FORM" property="typeDesc"/></td>
            </tr>
            <tr>
                <td><b>Schedule Id:</b>
                    <bean:write name="STORE_DELIVERY_SCHEDULE_FORM" property="scheduleData.scheduleId"/></td>
                <td colspan="2">&nbsp;</td>
            </tr>
        </table>
        <!-- Schdule -->
        <table ID=221 class="smalltext" border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td colspan="2"><b>Schedule Name:</b> <html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="scheduleData.shortDesc" size="30"/>
                    <font color="red">*</font>
                </td>
                <td><b>Effective Date:</b> <html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="effDate" size="10"/>
                    <% if ("Y".equals(isMSIE)) { %>
                    <a ID=222 href="#" onClick="return ShowCalendar(document.forms[0].STARTDATE, document.forms[0].effDate, null, -7300, 7300, null);" title="Choose Date"
                            ><img ID=223 name="STARTDATE" src="../externals/images/showCalendar.gif" width=19 height=19 border=0 align=middle style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true"></a>
                    <% } else {  %>
                    <a ID=224 href="javascript:show_calendar('forms[0].effDate');" onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;" title="Choose Date"><img ID=225 src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
                    <% }  %>
                    <font color="red">*</font>
                </td>
                <td><b>Expiration Date:</b> <html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="expDate" size="10"/>
                    <% if ("Y".equals(isMSIE)) { %>
                    <a ID=226 href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].ENDDATE, document.forms[0].expDate, null, -7300, 7300);" title="Choose Date"
                            ><img ID=227 name="ENDDATE" src="../externals/images/showCalendar.gif" width=19 height=19 border=0 align=middle style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true"></a>
                    <% } else {  %>
                    <a ID=228 href="javascript:show_calendar('forms[0].expDate');" onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;" title="Choose Date"><img ID=229 src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
                    <% }  %>
                </td>
            </tr>
            <tr><td colspan="4" height="5"></td></tr>
            <%
            //if (theForm.getUsePhysicalInventory()) {
            %>
            <tr>
                <td colspan="4">
                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                            <td>
                                 <b>Physical Inventory Periods:</b>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <font size="-2">(mm/dd/yyyy, mm/dd/yyyy, mm/dd/yyyy) ...</font>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <html:textarea name="STORE_DELIVERY_SCHEDULE_FORM" property="physicalInventoryPeriods" cols="85" rows="3"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <%
            //} else {
            %>
            <tr>
                <td colspan="4">
                    <b>Inventory Interval (days):</b>
                    <html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="invCartAccessInterval" size="1"/>
                </td>
            </tr>
            <%
            //}
            %>
            <tr><td colspan="4" height="5"></td></tr>
            <tr>
                <td class="smalltext">
                    &nbsp;&nbsp;<b>Schedule Type:</b>
                    <html:hidden property="scheduleRuleChange" value="" />
                    <html:select name="STORE_DELIVERY_SCHEDULE_FORM" property="scheduleData.scheduleRuleCd"
                                 onchange="document.forms[0].scheduleRuleChange.value='true'; document.forms[0].submit()" >
                        <html:option value="<%=RefCodeNames.SCHEDULE_RULE_CD.WEEK%>">Week</html:option>
                        <html:option value="<%=RefCodeNames.SCHEDULE_RULE_CD.DAY_MONTH%>">Month 1</html:option>
                        <html:option value="<%=RefCodeNames.SCHEDULE_RULE_CD.WEEK_MONTH%>">Month 2</html:option>
                        <html:option value="<%=RefCodeNames.SCHEDULE_RULE_CD.DATE_LIST%>">Date List</html:option>
                    </html:select>
                </td>
                <td><b>Status:</b>
                    <html:select name="STORE_DELIVERY_SCHEDULE_FORM" property="scheduleData.scheduleStatusCd">
                        <html:option value="<%=RefCodeNames.SCHEDULE_STATUS_CD.ACTIVE%>"><%=RefCodeNames.SCHEDULE_STATUS_CD.ACTIVE%></html:option>
                        <html:option value="<%=RefCodeNames.SCHEDULE_STATUS_CD.INACTIVE%>"><%=RefCodeNames.SCHEDULE_STATUS_CD.INACTIVE%></html:option>
                        <html:option value="<%=RefCodeNames.SCHEDULE_STATUS_CD.LIMITED%>"><%=RefCodeNames.SCHEDULE_STATUS_CD.LIMITED%></html:option>
                    </html:select>
                </td>
                <td colspan="2"><b>Cutoff Day:</b><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="cutoffDay" size="1"/>
                    <font color="red">*</font>
                    <b>Cutoff Time</b>(12:00 pm)<html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="cutoffTime" size="8"/>
                    <font color="red">*</font>
                </TD>
            </tr>
        </table>
    </td>
</tr>
<%
    String scheduleType = theForm.getScheduleData().getScheduleRuleCd();

    if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH.equals(scheduleType)) {
%>
<!-- Monthly Day Schedule -->
<tr>
    <td>
        <table ID=230 class="smalltext" border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td><b>&nbsp;Every</b><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="monthDayCycle" size="1"
                                                 styleClass="smalltext"/><b>month(s):</b></td>
                <% for(int ii=1; ii<29; ii++) { %>
                <td align="center"> <% if(ii<10){%>&nbsp;<% } %><%=ii%></td>
                <% } %>
                <td>Last Day</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <% for(int ii=1; ii<29; ii++) { %>
                <td><html:multibox name="STORE_DELIVERY_SCHEDULE_FORM" property="monthDays" value="<%=\"\"+ii%>" /></td>
                <% } %>
                <td><html:multibox name="STORE_DELIVERY_SCHEDULE_FORM" property="monthDays" value="32" /></td>
            </tr>
        </table>
    </td>
</tr>
<!-- Monthly Week Schedule -->
<%
} else if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH.equals(scheduleType)) {
%>
<tr>
    <td>
        <table ID=231 class="smalltext" align="left" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td><b>&nbsp;Every</b><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="monthWeekCycle" size="1"
                                                 styleClass="smalltext"/><b>month(s):</b></td>
                <td>&nbsp;&nbsp;&nbsp;</td>
                <td>First<html:multibox name="STORE_DELIVERY_SCHEDULE_FORM" property="monthWeeks" value="1" /></td>
                <td>&nbsp;&nbsp;&nbsp;</td>
                <td>Second<html:multibox name="STORE_DELIVERY_SCHEDULE_FORM" property="monthWeeks" value="2" /></td>
                <td>&nbsp;&nbsp;&nbsp;</td>
                <td>Third<html:multibox name="STORE_DELIVERY_SCHEDULE_FORM" property="monthWeeks" value="3" /></td>
                <td>&nbsp;&nbsp;&nbsp;</td>
                <td>Forth<html:multibox name="STORE_DELIVERY_SCHEDULE_FORM" property="monthWeeks" value="4" /></td>
                <td>&nbsp;&nbsp;&nbsp;</td>
                <td>Last<html:multibox name="STORE_DELIVERY_SCHEDULE_FORM" property="monthWeeks" value="5" /></td>
                <td>&nbsp;&nbsp;&nbsp;</td>
                <td><html:select name="STORE_DELIVERY_SCHEDULE_FORM" property="monthWeekDay" styleClass="smalltext">
                    <%--html:option value="1" disabled="true">Sunday</html:option--%>
                    <html:option value="2">Monday</html:option>
                    <html:option value="3">Tuesday</html:option>
                    <html:option value="4">Wednesday</html:option>
                    <html:option value="5">Thursday</html:option>
                    <html:option value="6">Friday</html:option>
                    <%--html:option value="7" disabled="true">Saturday</html:option--%>
                </html:select>
                </td>
            </tr>
        </table>
    </td>
</tr>
<!-- Weekly Schedule -->
<%
} else if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK.equals(scheduleType)) {
%>
<tr>
    <td>
        <table ID=232 class="smalltext" align="left" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td><b>&nbsp;Every</b><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="weekCycle" size="1"
                                                 styleClass="smalltext"/><b>week(s):</b></td>
                <td>&nbsp;&nbsp;&nbsp;</td>
                <td>Sunday<html:multibox name="STORE_DELIVERY_SCHEDULE_FORM" property="weekDays" value="1" disabled="true" /></td>
                <td>&nbsp;&nbsp;&nbsp;</td>
                <td>Monday<html:multibox name="STORE_DELIVERY_SCHEDULE_FORM" property="weekDays" value="2" /></td>
                <td>&nbsp;&nbsp;&nbsp;</td>
                <td>Tuesday<html:multibox name="STORE_DELIVERY_SCHEDULE_FORM" property="weekDays" value="3" /></td>
                <td>&nbsp;&nbsp;&nbsp;</td>
                <td>Wednesday<html:multibox name="STORE_DELIVERY_SCHEDULE_FORM" property="weekDays" value="4" /></td>
                <td>&nbsp;&nbsp;&nbsp;</td>
                <td>Thursday<html:multibox name="STORE_DELIVERY_SCHEDULE_FORM" property="weekDays" value="5" /></td>
                <td>&nbsp;&nbsp;&nbsp;</td>
                <td>Friday<html:multibox name="STORE_DELIVERY_SCHEDULE_FORM" property="weekDays" value="6" /></td>
                <td>&nbsp;&nbsp;&nbsp;</td>
                <td>Saturday<html:multibox name="STORE_DELIVERY_SCHEDULE_FORM" property="weekDays" value="7" disabled="true" /></td>
            </tr>
        </table>
    </td>
</tr>
<%
    }
    if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.DATE_LIST.equals(scheduleType)) {
%>
<!--  List of deliver dates -->
<tr><td>&nbsp;</td>
</tr>
<tr>
    <td><b>&nbsp;Delivery dates:</b>&nbsp;(mm/dd/yy, ...)</td>
</tr>
<tr>
    <td><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="alsoDates" size="120"/>
    </td>
</tr>
<%
} else {
%>
<!-- Also Dates -->
<tr><td>&nbsp;</td>
</tr>
<tr>
    <td>
        <table ID=233 class="smalltext" align="left" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td><b>&nbsp;Also delivery on:</b><br>&nbsp;&nbsp;&nbsp;(mm/dd/yy, ...)</td>
                <td>&nbsp;&nbsp;&nbsp;</td>
                <td><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="alsoDates" size="80"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<!-- Except Dates -->
<tr>
    <td>
        <table ID=234 class="smalltext" align="left" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td><b>&nbsp;No delivery on:</b><br>&nbsp;&nbsp;&nbsp;(mm/dd/yy, ...)</td>
                <td>&nbsp;&nbsp;&nbsp;</td>
                <td><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="excludeDates" size="80"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<%
    }
%>
<!-- Calendar -->
<tr>
    <td>
        <table ID=235 class="smalltext" align="left" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <%for(int mm=0; mm<6; mm++) { %>
                <td>&nbsp;&nbsp;</td>
                <td>
                    <table ID=236 class="smalltext" align="left" border="0" cellpadding="0" cellspacing="0">
                        <tr><td colspan="7" align="center"><b><%=theForm.getMonth(mm)%></b></td>
                        </tr>
                        <tr>
                            <td align="center"><b>S</b></td><td align="center"><b>M</b></td><td align="center"><b>T</b></td><td align="center"><b>W</b></td>
                            <td align="center"><b>T</b></td><td align="center"><b>F</b></td><td align="center"><b>S</b></td>
                        </tr>
                        <% for(int ii=0; ii<6; ii++) { %>
                        <tr>
                            <%
                                for(int jj=1; jj<8; jj++) {
                                    int kk = theForm.getDay(mm,ii,jj);
                                    boolean isContainedInPhysInvPeriods = theForm.isContainedInPhysicalInvPeriods(mm, ii, jj);
                            %>
                            <td align="right">
                                <% if(kk>=100){ %>
                                <font color="blue"><b><%=(kk/100)%></b>&nbsp;</font>
                                <%
                                } else if(kk > 0) {
                                    if (isContainedInPhysInvPeriods) {
                                %>
                                <font color="maroon"><b><%=kk%></b>&nbsp;</font>
                                <%
                                    } else {
                                %>
                                <%=kk%>&nbsp;
                                <%
                                    }
                                } else {
                                %>
                                &nbsp;
                                <% } %>
                            </td>
                            <% } %>
                        </tr>
                        <% } %>
                    </table>
                </td>
                <% } %>
                <td>&nbsp;</td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td align="center"  class="smalltext">
        <html:submit property="action" value="Save"/>
        <% if(theForm.getScheduleData().getScheduleId()>0) {  %>
        <html:submit property="action" value="Delete"/>
        <% } %>
    </td>
</tr>
</table>
<%
    if(theForm.getScheduleData().getScheduleId()>0) {
%>
<b>Config Function</b>
<html:select styleId="mainSearchField" name="STORE_DELIVERY_SCHEDULE_FORM" property="configFunc"
             onchange="setAndSubmit('dlsmd', 'command', 'setConfigFunc')">
    <html:option value="Territory">Territory</html:option>
    <html:option value="Account">Account</html:option>
</html:select>

<b>Config Type:</b>
<html:select styleId="mainSearchField" name="STORE_DELIVERY_SCHEDULE_FORM" property="configType"
             onchange="setAndSubmit('dlsmd', 'command', 'setConfigType')">
    <html:option value="County">County</html:option>
    <html:option value="Zip Code">Zip Code</html:option>
    <html:option value="Other">Other</html:option>
</html:select>
<%
    String configFunc = theForm.getConfigFunc();
    if("Account".equals(configFunc)) {
%>
<!-- ////////////////////////  Accounts ////////////////////////////////////////////// -->
<table ID=237 width="<%=Constants.TABLEWIDTH%>" border="0"  class="mainbody">
    <tr>
        <td><b>Find Accounts</b></td>
        <td>
            <html:text  styleId="mainSearchField" name="STORE_DELIVERY_SCHEDULE_FORM" property="acctSearchField"
                        onkeypress="return submitenter(this,event)"/>
            <html:radio name="STORE_DELIVERY_SCHEDULE_FORM" property="acctSearchType" value="id" />  ID
            <html:radio name="STORE_DELIVERY_SCHEDULE_FORM" property="acctSearchType" value="nameBegins" />  Name(starts with)
            <html:radio name="STORE_DELIVERY_SCHEDULE_FORM" property="acctSearchType" value="nameContains" />  Name(contains)
        </td>
    </tr>

    <%
        GroupDataVector accountGroups =  theForm.getAccountGroups();
        if(accountGroups!=null && accountGroups.size()>0 )
        {
    %>


    <tr><td><b>Groups</b></td><td>

        <html:radio  name="STORE_DELIVERY_SCHEDULE_FORM" property="searchGroupId" value='0'>None&nbsp;</html:radio><br>
        <% for (Iterator iter=accountGroups.iterator(); iter.hasNext();) {
            GroupData gD = (GroupData) iter.next();
            String grIdS = ""+gD.getGroupId();
            String grName = gD.getShortDesc();
        %>
        <html:radio  name="STORE_DELIVERY_SCHEDULE_FORM" property="searchGroupId" value='<%=grIdS%>'><%=grName%>&nbsp;
        </html:radio><br>
        <% } %>
    </td>
        <tr>
            <% }  /* end of groups */ %>

            <td>&nbsp;</td>  <td colspan="3">
            <html:submit styleId="search" property="action" value="Find Accounts"/>
            Configured Only
            <html:checkbox  name="STORE_DELIVERY_SCHEDULE_FORM" property="confAcctOnlyFl" value="true" />
        </td>
        </tr>
</table>
<%
    BusEntityDataVector accounts = theForm.getAcctBusEntityList();
    if(accounts!=null) {
%>
Count: <%=accounts.size()%>
<table class='stpTable_sortable' id="ts1">
    <thead>
        <tr class=stpTH>
            <th class=stpTH>Account Id</th>
            <th class=stpTH>Account Name</th>
            <th class=stpTH>Status</th>
            <th class=stpTH>
                <a ID=238 class="text" href="javascript:SetCheckedGlobal(1,'acctSelected')">[Chk.&nbsp;All]</a>
                <br>
                <a ID=239 class="text" href="javascript:SetCheckedGlobal(0,'acctSelected')">[&nbsp;Clear]</a>
            </th>
        </tr>
    </thead>
    <tbody>
        <%
            for(Iterator iter = accounts.iterator(); iter.hasNext();) {
                BusEntityData acct = (BusEntityData) iter.next();
        %>
        <tr class=stpTD>
            <td class=stpTD><%=acct.getBusEntityId()%></td>
            <td class=stpTD><%=acct.getShortDesc()%></td>
            <td class=stpTD><%=acct.getBusEntityStatusCd()%></td>
            <td class=stpTD>
                <html:multibox name="STORE_DELIVERY_SCHEDULE_FORM" property="acctSelected"
                               value='<%=String.valueOf(acct.getBusEntityId())%>' />
            </td>
        </tr>
        <%
            }
        %>
    </tbody>
</table>
<%
    if(accounts.size()>0) {
%>
<table ID=240 cellspacing="0" border="0" width="769"  class="mainbody">
    <tr class='stpTH'>
        <td class='stpTH' colspan="4">
            <html:submit property="action" value="Save Accounts"/>
        </td>
    </tr>
</table>
<%
        }
    }
} else {
%>
<!-- ///////////////////////   Zip Codes  //////////////////////////////////////////// -->
<table ID=241 cellspacing="0" border="0" width="769"  class="mainbody">
    <%
        String confType = theForm.getConfigType();
        if(!"Other".equals(confType)){
    %>
    <!--Zip Codes or County actConfType=<%=confType%>-->
    <html:hidden name="STORE_DELIVERY_SCHEDULE_FORM" property="actConfigType" value="<%=confType%>"/>
    <tr>
        <td><b>City (starts with):</b></td>
        <td><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="searchCity" size="30"/></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>

    <tr>
        <td><b>State:</b></td>
        <td><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="searchState" size="10"/></td>

        <td><b>County (starts with):</b></td>
        <td><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="searchCounty" size="30"/></td>
    </tr>

    <tr>
        <td><b>Zip Code:</b></td>
        <td><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="searchZipCode" size="6"/></td>

        <td><b>Serviced Only:</b></td>
        <td><html:checkbox name="STORE_DELIVERY_SCHEDULE_FORM" property="servicedOnly" value="true"/></td>
    </tr>

    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td colspan="3"><html:submit property="action" value="Search"/></td>
    </tr>
<%
    }else{
%>
    <!--Other actConfType=<%=confType%>-->
    <html:hidden name="STORE_DELIVERY_SCHEDULE_FORM" property="actConfigType" value="<%=confType%>"/>
    <tr>
        <td>
            <table ID=242 cellspacing="0" border="0" width="769">
                <tr>
                    <td><b>Postal code:</b></td>
                    <td><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="searchZipCode" size="6"/></td>

                    <td><b>Country:</b></td>
                    <td>
                        <html:select name="STORE_DELIVERY_SCHEDULE_FORM" property="searchCountryCd">
                            <html:option value="">
                                <app:storeMessage  key="admin.select.country"/>
                            </html:option>

                            <html:options  collection="country.vector" labelProperty="uiName" property="shortDesc"/>
                        </html:select>
                    </td>

                    <td colspan="3"><html:submit property="action" value="Search"/></td>
                </tr>
            </table>
        </td>
    </tr>

    <tr>
        <td bgcolor="white">
            <b>Add Postal code:</b>
        </td>
    </tr>

    <tr>
        <td>
            <table ID=243 cellspacing="0" border="0" width="769">
                <tr>
                    <td><b>Postal code:</b></td>
                    <td><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="addZipCode" size="6"/></td>

                    <td><b>Country:</b></td>
                    <td>
                        <html:select name="STORE_DELIVERY_SCHEDULE_FORM" property="addCountryCd">
                            <html:option value="">
                                <app:storeMessage  key="admin.select.country"/>
                            </html:option>

                            <html:options  collection="country.vector" labelProperty="uiName" property="shortDesc"/>
                        </html:select>
                    </td>

                    <td colspan="3"><html:submit property="action" value="Add Code"/></td>
                </tr>
            </table>
        </td>
    </tr>
<%
    }
%>
</table>
<%
    if("County".equals(confType)) {
        int size = theForm.getCounties().size(); %>
Count: <%=size%>
<%
    if(size>0) {
%>
<table ID=244 cellspacing="0" border="0" width="769">
    <tr>
        <th class="stpTH"><a ID=245 href="dlschdldet.do?action=sortTerr&sortField=City"><b>City</b></a></th>
        <th class="stpTH"><a ID=246 href="dlschdldet.do?action=sortTerr&sortField=CountyCd"><b>County</b></a></th>
        <th class="stpTH"><a ID=247 href="dlschdldet.do?action=sortTerr&sortField=StateProvinceCd"><b>State Cd</b></a></th>
        <th class="stpTH"><a ID=248 href="dlschdldet.do?action=sortTerr&sortField=StateProvinceName"><b>State Name</b></a></th>
        <th class="stpTH"><a ID=249 href="dlschdldet.do?action=sortTerr&sortField=CountryCd"><b>Country</b></a></th>
        <th class="stpTH">
            <a ID=250 href="javascript:SetChecked('Select All')">[Check&nbsp;All]</a><br>
            <a ID=251 href="javascript:SetChecked('Clear Selection')">[&nbsp;Clear]</a>
        </th>
    </tr>

    <logic:iterate id="confCounty" name="STORE_DELIVERY_SCHEDULE_FORM" property="counties"
                   type="com.cleanwise.service.api.value.BusEntityTerrView"
                   indexId="idx">

        <bean:define id="lCity"  name="confCounty" property="city"/>
        <bean:define id="lCounty"  name="confCounty" property="countyCd"/>
        <bean:define id="lStateCd"  name="confCounty" property="stateProvinceCd"/>
        <bean:define id="lStateName"  name="confCounty" property="stateProvinceName"/>
        <bean:define id="lCountry"  name="confCounty" property="countryCd"/>
        <bean:define id="lDistId" name="confCounty" property="busEntityId"/>
        <bean:define id="lNoModifiyFl" name="confCounty" property="noModifiyFl"/>

        <tr>
            <td class="stpTD"><bean:write name="lCity"/></td>
            <td class="stpTD"><bean:write name="lCounty"/></td>
            <td class="stpTD"><bean:write name="lStateCd"/></td>
            <td class="stpTD"><bean:write name="lStateName"/></td>
            <td class="stpTD"><bean:write name="lCountry"/></td>
            <% String countySelectFl = (String)lCity+"^"+(String)lCounty+"^"+(String)lStateCd+"^"+(String)lCountry; %>
            <td class="stpTD">
                <html:multibox name="STORE_DELIVERY_SCHEDULE_FORM" property="selected"
                               value="<%=countySelectFl%>" disabled="<%=((Boolean)lNoModifiyFl).booleanValue()%>"/>
            </td>
        </tr>
    </logic:iterate>

    <tr>
        <td colspan="5">
            <html:submit property="action" value="Save Configuration"/>
        </td>
    </tr>
</table>
<br/>
<br/>
<%
        }
    }
    if("Zip Code".equals(confType)) {
        int size = theForm.getPostalCodes().size();
%>
Count: <%=size %>
<%
    if(size>0) {
%>
<table ID=252 cellspacing="0" border="0" width="769">
    <tr align=center>
        <th class="stpTH"><a ID=253 href="dlschdldet.do?action=sortTerr&sortField=PostalCode"><b>Postal Code</b></a></th>
        <th class="stpTH"><a ID=254 href="dlschdldet.do?action=sortTerr&sortField=City"><b>City</b></a></th>
        <th class="stpTH"><a ID=255 href="dlschdldet.do?action=sortTerr&sortField=CountyCd"><b>County</b></a></th>
        <th class="stpTH"><a ID=256 href="dlschdldet.do?action=sortTerr&sortField=StateProvinceCd"><b>State Cd</b></a></th>
        <th class="stpTH"><a ID=257 href="dlschdldet.do?action=sortTerr&sortField=StateProvinceName"><b>State Name</b></a></th>
        <th class="stpTH"><a ID=258 href="dlschdldet.do?action=sortTerr&sortField=CountryCd"><b>Country</b></a></th>
        <th class="stpTH">
            <a ID=259 href="javascript:SetChecked('Select All')">[Check&nbsp;All]</a><br>
            <a ID=260 href="javascript:SetChecked('Clear Selection')">[&nbsp;Clear]</a>
        </th>
    </tr>
    <logic:iterate id="confPostalCode" name="STORE_DELIVERY_SCHEDULE_FORM" property="postalCodes"
                   type="com.cleanwise.service.api.value.BusEntityTerrView"
                   indexId="idx"
            >
        <bean:define id="lPostalCodeId"  name="confPostalCode" property="postalCodeId"/>
        <bean:define id="lPostalCode"  name="confPostalCode" property="postalCode"/>
        <bean:define id="lCounty"  name="confPostalCode" property="countyCd"/>
        <bean:define id="lCity"  name="confPostalCode" property="city"/>
        <bean:define id="lStateCd"  name="confPostalCode" property="stateProvinceCd"/>
        <bean:define id="lStateName"  name="confPostalCode" property="stateProvinceName"/>
        <bean:define id="lCountry"  name="confPostalCode" property="countryCd"/>
        <bean:define id="lDistId" name="confPostalCode" property="busEntityId"/>

        <tr>
            <td class="stpTD"><bean:write name="lPostalCode"/></td>
            <td class="stpTD"><bean:write name="lCity"/></td>
            <td class="stpTD"><bean:write name="lCounty"/></td>
            <td class="stpTD"><bean:write name="lStateCd"/></td>
            <td class="stpTD"><bean:write name="lStateName"/></td>
            <td class="stpTD"><bean:write name="lCountry"/></td>
            <% String postalCodeSelectFl = (String)lPostalCode+"^"+(String)lCountry +"^"+(String)lCity; %>
            <td class="stpTD">
                <html:multibox name="STORE_DELIVERY_SCHEDULE_FORM" property="selected"
                               value="<%=postalCodeSelectFl%>"/>
            </td>
        </tr>
    </logic:iterate>
    <tr>
        <td colspan="6">
            <html:submit property="action" value="Save Configuration"/>
        </td>
    </tr>
</table>
<br/>
<br/>
<%
                }
            }
    if("Other".equals(confType)) {
        int size = theForm.getPostalCodes().size();
%>
Count: <%=size %>
<%
    if(size>0) {
%>
<table ID=261 cellspacing="0" border="0" width="769">
    <tr align=center>
        <th class="stpTH"><a ID=262 href="dlschdldet.do?action=sortTerr&sortField=PostalCode"><b>Postal Code</b></a></th>
        <th class="stpTH"><a ID=263 href="dlschdldet.do?action=sortTerr&sortField=CountryCd"><b>Country</b></a></th>
        <th class="stpTH">
            <a ID=264 href="javascript:SetChecked('Select All')">[Check&nbsp;All]</a><br>
            <a ID=265 href="javascript:SetChecked('Clear Selection')">[&nbsp;Clear]</a>
        </th>
    </tr>
    <logic:iterate id="confPostalCode" name="STORE_DELIVERY_SCHEDULE_FORM" property="postalCodes"
                   type="com.cleanwise.service.api.value.BusEntityTerrView"
                   indexId="idx">

        <%
          int lPostalCodeId = confPostalCode.getPostalCodeId();
          String lPostalCode = Utility.strNN(confPostalCode.getPostalCode());
          String lCountry =  Utility.strNN(confPostalCode.getCountryCd());
        %>

        <tr>
            <td class="stpTD"><%=lPostalCode%></td>
            <td class="stpTD"><%=lCountry%></td>
            <%
                String postalCodeSelectFl = (String)lPostalCode + "^" + (String)lCountry;
            %>
            <td class="stpTD">
                <html:multibox name="STORE_DELIVERY_SCHEDULE_FORM" property="selected"
                               value="<%=postalCodeSelectFl%>"/>
            </td>
        </tr>
    </logic:iterate>
    <tr>
        <td colspan="6">
            <html:submit property="action" value="Save Configuration"/>
        </td>
    </tr>
</table>
<br/>
<br/>
<%
                }
            }
        }
    }
%>

<input type="hidden" name="action" value="unknown" />
<input type="hidden" name="command" value="unknown" />
</html:form>

</div>
<%
    theForm.setWeekDays(new int[0]);
    theForm.setMonthDays(new int[0]);
    theForm.setMonthWeeks(new int[0]);
%>





