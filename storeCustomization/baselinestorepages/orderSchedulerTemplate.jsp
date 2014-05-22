<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="java.util.GregorianCalendar"%>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<script language="JavaScript">
<!--
dojo.require("dojox.fx.easing");
    dojo.require("dojox.fx.scroll");
    dojo.require("clw.CLW.form.DateTextBox");
    
    function setAndSubmit(fid, vv, value) {
        var aaa = document.forms[fid].elements[vv];
        aaa.value = value;
        if(dojo.isIE <= 6){
           //document.forms[fid].target = "pdf";
        }
        aaa.form.submit();
        return false;
    }
    function gotoId(id) {
        try {
            var node = dojo.byId(id);
            var anim = dojox.fx.smoothScroll({
                node: node,
                win:window,
                duration:300,
                easing:dojox.fx.easing.easeOut}).play();
        } catch(e) {
        }
    }
function setAndSubmit(fid, vv, value) {
  var aaa = document.forms[fid].elements[vv];
  aaa.value=value;
  aaa.form.submit();
  return false;

}
-->
</script>

<% String requestNumber =  (String) session.getAttribute(Constants.REQUEST_NUM);%>
<bean:define id="theForm" name="ORDER_SCHEDULER_FORM" type="com.cleanwise.view.forms.OrderSchedulerForm"/>
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>


<%
  String localeCd = "" + ClwI18nUtil.getUserLocale(request);
  boolean enUsDateFl = ("en_US".equals(localeCd))?true:false;
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
<table align="center" cellpadding="0" 
  cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
<tr>
<td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
<td><jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"t_shopToolbar.jsp")%>'/></td>
<td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>

<!-- content -->
<logic:equal name="ORDER_SCHEDULER_FORM" property="userOrderGuideNumber" value="0">
<logic:equal name="ORDER_SCHEDULER_FORM" property="templateOrderGuideNumber" value="0">
<tr>
<td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
<td align="center">
<b><app:storeMessage key="shop.orderSchedule.text.noOrderGuidesAvailable"/></b>
</td>
<td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
</logic:equal>
</logic:equal>
<!-- Order guide select section -->
<% if(theForm.getUserOrderGuideNumber()>0 || theForm.getTemplateOrderGuideNumber()>0) {%>
<html:form styleId="ost" action="/store/orderScheduler.do?action=orderSchedulerSubmit">
<tr>
  <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
  <td class="smalltext">



    <table align="center" width="767">
    <tr>
      <!-- start left half -->
      <td>
        <table>
        <tr>          
          <td class="subheaders">
            &nbsp;&nbsp;<b><app:storeMessage key="shop.orderSchedule.text.schedule:"/></b>
            <html:hidden property="scheduleTypeChange" value="" />
            <span class="reqind">*</span>
            <html:select name="ORDER_SCHEDULER_FORM" property="scheduleType" 
              onchange="document.forms[0].scheduleTypeChange.value='true'; document.forms[0].submit()">
              <html:option value="<%=RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK%>">
                 <app:storeMessage key="shop.orderSchedule.text.weekly"/></html:option>
              <html:option value="<%=RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH%>">
                 <app:storeMessage key="shop.orderSchedule.text.monthly"/></html:option>
              <html:option value="<%=RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH%>">
                 <app:storeMessage key="shop.orderSchedule.text.custom"/></html:option>
            </html:select>
            
            <span class="reqind">*</span><html:select name="ORDER_SCHEDULER_FORM" property="orderGuideId">
              <html:option value="-1">
                 <app:storeMessage key="shop.orderSchedule.text.templateOrderGuides"/></html:option>            
              <html:options  name="ORDER_SCHEDULER_FORM" property="templateOrderGuideIds" labelName="ORDER_SCHEDULER_FORM" labelProperty="templateOrderGuideNames"/>
              <html:option value="-1">
                 <app:storeMessage key="shop.orderSchedule.text.myOrderGuides"/></html:option>            
              <html:options  name="ORDER_SCHEDULER_FORM" property="userOrderGuideIds" labelName="ORDER_SCHEDULER_FORM" labelProperty="userOrderGuideNames"/>
            </html:select>
          </td>        
        </tr>
        <tr>
          <td>
            <html:radio name="ORDER_SCHEDULER_FORM" property="scheduleAction" value="<%=RefCodeNames.ORDER_SCHEDULE_CD.NOTIFY%>"/>
              <app:storeMessage key="shop.orderSchedule.text.remindMeToPlaceMyOrder"/>
            <br><span class="reqind">*</span><br>
            <html:radio name="ORDER_SCHEDULER_FORM" property="scheduleAction" value="<%=RefCodeNames.ORDER_SCHEDULE_CD.PLACE_ORDER%>"/>
              <app:storeMessage key="shop.orderSchedule.text.automaticallyPlaceMyOrder"/>
          </td>
        </tr>
        <tr>
          <td align="right">
            <span class="reqind">*</span>&nbsp;<b>
            <app:storeMessage key="shop.orderSchedule.text.startDate"/></b>
            (<%=ClwI18nUtil.getUIDateFormat(request)%>):&nbsp;
	  
	    <app:dojoInputDate id="startDate"
                       property="startDate"
                       module="clw.CLW"
                       targets="StartDate"/>


     <img id="StartDate" src="../externals/images/showCalendar.gif"
             width=19  height=19 border=0
             onmouseover="window.status='Choose Date';return true"
             onmouseout="window.status='';return true">
	    <br>            
    
          </td>
        </tr>
        <tr>
          <td align="right">
            <b><app:storeMessage key="shop.orderSchedule.text.endDate"/></b>
            (<%=ClwI18nUtil.getUIDateFormat(request)%>):&nbsp;
            <app:dojoInputDate id="endDate"
                       property="endDate"
                       module="clw.CLW"
                       targets="EndDate"/>


     <img id="EndDate" src="../externals/images/showCalendar.gif"
             width=19  height=19 border=0
             onmouseover="window.status='Choose Date';return true"
             onmouseout="window.status='';return true">
    
          </td>
        </tr>
        <tr>
          <td align="right">
            <b><app:storeMessage key="shop.orderSchedule.text.ccEmailAddress:"/>&nbsp;&nbsp;</b>
            <html:text name="ORDER_SCHEDULER_FORM" property="ccEmail" size="35"/>
          </td>
        </tr>
        <tr><td><hr></td></tr>
        <% String scheduleType = theForm.getScheduleType(); %>
  <!-- Monthly Day Schedule -->
  <% if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH.equals(scheduleType)) { %>
  <tr>
  <td>  
    <table align="center">
    <tr>
      <td colspan="15" align="left">
        <b>&nbsp;<app:storeMessage key="shop.orderSchedule.text.placeOrderMonthlyOn:"/></b>
      </td>
    </tr>
    <tr>
    <% for(int ii=1; ii<15; ii++) { %>
      <td align="center"><%=ii%></td>
    <% } %>
    </tr>
    <tr>
    <% for(int ii=1; ii<15; ii++) { %>
      <td align="center">
        <html:multibox name="ORDER_SCHEDULER_FORM" property="monthDays" value="<%=\"\"+ii%>" />
      </td>
    <% } %>
    </tr> 
    <tr>
      <% for(int ii=15; ii<29; ii++) { %>
      <td align="center"><%=ii%></td>
    <% } %>
      <td align="center"><app:storeMessage key="shop.orderSchedule.text.lastDay"/></td>
    </tr>
    <tr>
    <% for(int ii=15; ii<29; ii++) { %>
      <td align="center"><html:multibox name="ORDER_SCHEDULER_FORM" property="monthDays" value="<%=\"\"+ii%>" /></td>
    <% } %>
      <td align="center">
        <html:multibox name="ORDER_SCHEDULER_FORM" property="monthDays" value="32" /> 
      </td>
    </tr>
    </table>
  </td>  
  </tr>
  <!-- Monthly Week Schedule -->
  <% } else if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH.equals(scheduleType)) { %>
  <tr>
    <td>
      <b>&nbsp;<span class="reqind">*</span> 
       <app:storeMessage key="shop.orderSchedule.text.customizeOrderWithinEachMonth:"/></b>
    </td>
    </tr>
  <tr>
    <td>
      <table align="center">
        <tr>
          <td align="center"><app:storeMessage key="global.text.month.jan"/></td>
          <td align="center"><app:storeMessage key="global.text.month.feb"/></td>
          <td align="center"><app:storeMessage key="global.text.month.mar"/></td>
          <td align="center"><app:storeMessage key="global.text.month.apr"/></td>
          <td align="center"><app:storeMessage key="global.text.month.may"/></td>
          <td align="center"><app:storeMessage key="global.text.month.jun"/></td>
          <td align="center"><app:storeMessage key="global.text.month.jul"/></td>
          <td align="center"><app:storeMessage key="global.text.month.aug"/></td>
          <td align="center"><app:storeMessage key="global.text.month.sep"/></td>
          <td align="center"><app:storeMessage key="global.text.month.oct"/></td>
          <td align="center"><app:storeMessage key="global.text.month.nov"/></td>
          <td align="center"><app:storeMessage key="global.text.month.dec"/></td>
        </tr>
        <tr>
        <% for(int ii=0; ii<12; ii++) { %>
          <td align="center"><html:multibox name="ORDER_SCHEDULER_FORM" property="monthMonths" value="<%=\"\"+ii%>" /></td>
        <% } %>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td align="right">
      <app:storeMessage key="global.action.label.select"/>  <span class="reqind">*</span> 
     <!--  <app:storeMessage key="shop.orderSchedule.text.week:"/> -->
      <html:select name="ORDER_SCHEDULER_FORM" property="monthWeeks" styleClass="smalltext">
        <html:option value="1"><app:storeMessage key="global.text.month.first"/></html:option>
        <html:option value="2"><app:storeMessage key="global.text.month.second"/></html:option>
        <html:option value="3"><app:storeMessage key="global.text.month.third"/></html:option>
        <html:option value="4"><app:storeMessage key="global.text.month.fourth"/></html:option>
        <html:option value="5"><app:storeMessage key="global.text.month.last"/></html:option>
      </html:select>
      <span class="reqind">*</span> <!--  <app:storeMessage key="shop.orderSchedule.text.day:"/>-->
      <html:select name="ORDER_SCHEDULER_FORM" property="monthWeekDay"  styleClass="smalltext">
        <html:option value="1"><app:storeMessage key="global.text.month.sunday"/></html:option>
        <html:option value="2"><app:storeMessage key="global.text.month.monday"/></html:option>
        <html:option value="3"><app:storeMessage key="global.text.month.tuesday"/></html:option>
        <html:option value="4"><app:storeMessage key="global.text.month.wednesday"/></html:option>
        <html:option value="5"><app:storeMessage key="global.text.month.thursday"/></html:option>
        <html:option value="6"><app:storeMessage key="global.text.month.friday"/></html:option>
        <html:option value="7"><app:storeMessage key="global.text.month.saturday"/></html:option>
      </html:select>  
	  <app:storeMessage key="shop.orderSchedule.text.ofTheMonth"/>
  </td>
  </tr>

  <!-- Weekly Schedule -->
  <% } else  { %>
  <tr>
  <td>
  <table class="smalltext" align="left" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td align="right">
    <b>&nbsp;<span class="reqind">*</span> 
    <app:storeMessage key="shop.orderSchedule.text.placeOrderEveryWeekOn:"/>&nbsp;&nbsp;</b>
    <app:storeMessage key="global.text.month.sunday"/><html:multibox name="ORDER_SCHEDULER_FORM" property="weekDays" value="1" />
    &nbsp;&nbsp;
    <app:storeMessage key="global.text.month.monday"/><html:multibox name="ORDER_SCHEDULER_FORM" property="weekDays" value="2" />
    &nbsp;&nbsp;
    <app:storeMessage key="global.text.month.tuesday"/><html:multibox name="ORDER_SCHEDULER_FORM" property="weekDays" value="3"/>
    &nbsp;&nbsp;
  </td>
  <tr>
  <td align="right">
    <app:storeMessage key="global.text.month.wednesday"/><html:multibox name="ORDER_SCHEDULER_FORM" property="weekDays" value="4" />
    &nbsp;&nbsp;
    <app:storeMessage key="global.text.month.thursday"/><html:multibox name="ORDER_SCHEDULER_FORM" property="weekDays" value="5" />
    &nbsp;&nbsp;
    <app:storeMessage key="global.text.month.friday"/><html:multibox name="ORDER_SCHEDULER_FORM" property="weekDays" value="6" />
    &nbsp;&nbsp;
    <app:storeMessage key="global.text.month.saturday"/><html:multibox name="ORDER_SCHEDULER_FORM" property="weekDays" value="7" />
  </td>
  </tr>
   </table>
  </td>
  </tr>
  <% } %>
  <!-- Also Dates -->
  <tr><td>&nbsp;</td>
  </tr>
  <tr>
  <td align="right">
    <b>&nbsp;<app:storeMessage key="shop.orderSchedule.text.placeAdditionalOrdersOn"/></b>
    (<%=ClwI18nUtil.getUIDateFormat(request)%>):
  <app:dojoInputDate id="alsoDates"
                       property="alsoDates"
                       module="clw.CLW"
                       targets="AlsoDates"/>


     <img id="AlsoDates" src="../externals/images/showCalendar.gif"
             width=19  height=19 border=0
             onmouseover="window.status='Choose Date';return true"
             onmouseout="window.status='';return true">
  
  <br>
  </td>
  </tr>
  <!-- Except Dates -->
  <tr>  
  <td align="right">
    <b>&nbsp;<app:storeMessage key="shop.orderSchedule.text.deleteAdditionalOrdersOn"/></b>
    (<%=ClwI18nUtil.getUIDateFormat(request)%>):
   <app:dojoInputDate id="excludeDates"
                       property="excludeDates"
                       module="clw.CLW"
                       targets="ExcludeDates"/>


     <img id="ExcludeDates" src="../externals/images/showCalendar.gif"
             width=19  height=19 border=0
             onmouseover="window.status='Choose Date';return true"
             onmouseout="window.status='';return true">
  </td>
  </tr>
  
  <%-- Order Contact fields ------------------------ --%>
  <% 
    CleanwiseUser appUser = (CleanwiseUser)session.getAttribute("ApplicationUser");
	boolean crcManagerFl = appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.CRC_MANAGER);
	
    SiteData thisSite = appUser.getSite();
       
    String utype = appUser.getUser().getUserTypeCd();
    if(utype.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE)||
       utype.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER) ||
	   crcManagerFl){
  %>

  <tr>
    <td class="text" align="right"><span class="reqind">*</span>&nbsp;<b>
      <app:storeMessage key="shop.orderSchedule.text.contactName:"/>&nbsp;</b>
      <html:text name="ORDER_SCHEDULER_FORM" property="contactName" size="30"/>
    </td>
  </tr>

  <tr>
    <td class="text" align="right"><span class="reqind">*</span>&nbsp;<b>
    <app:storeMessage key="shop.orderSchedule.text.contactPhone#:"/>&nbsp;</b>
    <html:text name="ORDER_SCHEDULER_FORM" property="contactPhone" size="30"/>
    </td>
  </tr>

  <tr>
    <td class="text" align="right">&nbsp;<b>
    <app:storeMessage key="shop.orderSchedule.text.contactEmail:"/>&nbsp;</b>
    <html:text name="ORDER_SCHEDULER_FORM" property="contactEmail" size="30"/>
    </td>
  </tr>
  <% } %>  
        </table>
      </td>



<!-- end left half, begin right -->      
      <td valign="top">
        <table align="center">
          <tr>
            <td>
              <html:select name="ORDER_SCHEDULER_FORM" property="orderScheduleId"
                onchange="javascript:document.forms[0].submit();">
                <html:option  value="0">
                <app:storeMessage key="shop.orderSchedule.text.-createNewSchedule-"/></html:option>
                <%

                  List scheduleL = theForm.getOrderSchedules();
                  for(int ii=0; ii<scheduleL.size(); ii++) {
                    String ss = theForm.getOrderScheduleName(request,ii);
                    String idS = theForm.getOrderScheduleId(ii);
                %>
                <html:option  value="<%=idS%>"><%=ss%></html:option>
               <% } %>
             </html:select>             
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
            <td align="center"> <input type="image" name="calendarBackward" src="<%=IMGPath%>/calBackward.gif" border="0"> &nbsp;&nbsp;  <input type="image" name="calendarForward" src="<%=IMGPath%>/calForward.gif" border="0">  </td>
          </tr>
<%  
    List scheduleL = theForm.getOrderSchedules();
    int userId = 0;
    int appId = 0;
    int osId = 0;

    for(int ii=0; ii<scheduleL.size(); ii++) {
        
      OrderScheduleView osv = (OrderScheduleView)scheduleL.get(ii);      
      osId = osv.getOrderScheduleId();  

      if(osId == theForm.getOrderScheduleId()){
        userId =  osv.getUserId();
      }
      
      appId = appUser.getUser().getUserId();
    }
// Don't let users define an order schedule if inventory shopping
// is turned on.
if ( thisSite != null &&
     ! thisSite.hasInventoryShoppingOn() ) {

    //we only want the save/delete option if the schedule is being created, 
    //or if the person who created it is the one viewing it.
    // We also want to save the schedule if this site is set up to
    // shared order guides and order schedules.  durval 3/21/2006
%>
          <tr>
            <td align="center"> 
      <logic:equal name="<%=Constants.APP_USER%>" property="allowPurchase" value="true">
        <a href="#" class="linkButton" onclick="setAndSubmit('ost','command','saveSchedule');"
        ><img src='<%=IMGPath + "/b_save.gif"%>' 
        border="0"/><app:storeMessage key="shop.orderSchedule.text.saveSchedule"/></a>
        <% if(theForm.getOrderScheduleId()>0) {  %>
          <a href="#" class="linkButton" onclick="setAndSubmit('ost','command','deleteSchedule');"
          ><img src='<%=IMGPath + "/b_remove.gif"%>' 
          border="0"/><app:storeMessage key="shop.orderSchedule.text.deleteSchedule"/></a>
        <% } %> 

      </logic:equal>

            </td>
          </tr>   
<% 
}  // only create scheduled orders for those sites not on
   // inventory shopping
%> 
        </table>
        
      </td> <!-- End right half -->
    </tr>    
    </table>




  </td>
  <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
<html:hidden property="command" value="CCCCCCC"/>
</html:form>
<% } %>
</table>

<%@ include file="f_table_bottom.jsp" %>     

<!-- reset checkboxes -->
<%
   theForm.setWeekDays(new int[0]);
   theForm.setMonthDays(new int[0]);
   theForm.setMonthMonths(new int[0]);
 %>





