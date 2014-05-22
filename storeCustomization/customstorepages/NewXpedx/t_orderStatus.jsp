<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ page import="com.cleanwise.service.api.value.PairView"%>
<%@ page import="com.cleanwise.view.forms.UserOrderStatusForm"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<style>
input{
width:150px;
}
select{
width:150px;
}
</style>





<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<%

    SessionTool st = new SessionTool(request);
    CleanwiseUser appUser = st.getUserData();

    String userType = (String)session.getAttribute(Constants.USER_TYPE);
    if ( null == userType || "".equals(userType)) {
        userType = RefCodeNames.USER_TYPE_CD.CUSTOMER;
    }

    String reqAction = request.getParameter("action");
    if (null == reqAction) {
        reqAction="--none--";
    }
    String sc_source = (String)session.getAttribute("sc_source");
    if ( null == sc_source ) sc_source = "";

    String  prefix  = null;
    if(appUser.getUserStore().getPrefix()!=null){
        prefix = appUser.getUserStore().getPrefix().getValue();
    }


String siteSelectObjectId     = "os_site";
String stateSelectObjectId    = "os_state";
String countrySelectObjectId  = "os_contry";

%>

<bean:define id="theForm" name="USER_ORDER_STATUS_FORM" type="com.cleanwise.view.forms.UserOrderStatusForm"/>
<%boolean showLocationCriteria = ((UserOrderStatusForm) theForm).getShowLocation() || true;%>

<%if (showLocationCriteria) {%>

        <script language="JavaScript1.2"><!--

        var siteArray;
        var siteIdx=0;
        siteArray = new Array();
        <%if(((UserOrderStatusForm)theForm).getSiteValuePairs()!=null
    && !(((UserOrderStatusForm)theForm).getSiteValuePairs()).isEmpty()) {  %>

        <% for(int i=0;i<((UserOrderStatusForm)theForm).getSiteValuePairs().size();i++){%>
        siteArray[siteIdx] = new Array();
        siteArray[siteIdx][0] ='<%=((PairView)(((UserOrderStatusForm)theForm).getSiteValuePairs()).get(i)).getObject1().toString()%>';
        siteArray[siteIdx][1] ='<%=Utility.replaceString(((PairView)(((UserOrderStatusForm)theForm).getSiteValuePairs()).get(i)).getObject2().toString(),"'","\\'")%>';
        siteIdx++;
        <%}%>
        <%}%>

        function siteInit(array, currentValue, siteEmptyMessage, selectElementId) {
            osSiteListDynamicBox.init(array, currentValue, siteEmptyMessage, selectElementId);
        }

        //-->
        </script>

        <script language="JavaScript1.2"><!--

        var countryArray;
        var countryIdx=0;

        <%if(((UserOrderStatusForm)theForm).getCountryValueList()!=null
    && !(((UserOrderStatusForm)theForm).getCountryValueList()).isEmpty()) {  %>
        countryArray = new Array();
        <% for(int i=0;i<((UserOrderStatusForm)theForm).getCountryValueList().size();i++){%>
        countryArray[countryIdx] = new Array();

        <%
        String countryStr = (String)(((UserOrderStatusForm)theForm).getCountryValueList()).get(i);
        if(countryStr.indexOf("'")>0){ 		//escape single quotes
        	countryStr = countryStr.replace("'", "\\'");
        }
        %>
        countryArray[countryIdx][0] ='<%=countryStr%>';
        countryArray[countryIdx][1] ='<%=countryStr%>';
        countryIdx++;
        <%}%>
        <%}%>

        function countryInit(array, currentValue, countryEmptyMessage, selectElementId) {
            osCountryListDynamicBox.init(array, currentValue, countryEmptyMessage, selectElementId);
        }

        //-->
        </script>


        <script language="JavaScript1.2"><!--

        var stateArray;
        var stateIdx=0;

        <%if(((UserOrderStatusForm)theForm).getStateValueList()!=null
    && !(((UserOrderStatusForm)theForm).getStateValueList()).isEmpty()) {  %>
        stateArray = new Array();
        <% for(int i=0;i<((UserOrderStatusForm)theForm).getStateValueList().size();i++){%>
        stateArray[stateIdx] = new Array();
        stateArray[stateIdx][0] ='<%=(String)(((UserOrderStatusForm)theForm).getStateValueList()).get(i)%>';
        stateArray[stateIdx][1] ='<%=(String)(((UserOrderStatusForm)theForm).getStateValueList()).get(i)%>';
        stateIdx++;
        <%}%>
        <%}%>

        function stateInit(array, currentValue, stateEmptyMessage, selectElementId) {
            osStateListDynamicBox.init(array, currentValue, stateEmptyMessage, selectElementId);
        }

        //-->
        </script>
       <script language="JavaScript1.2"><!--
      addLoadEvent(stateInitOrderStatus);
	  function stateInitOrderStatus(){
          stateInit(stateArray,"<%=Utility.strNN(((UserOrderStatusForm)theForm).getSiteState())%>","<%=ClwI18nUtil.getMessage(request,"shop.orderStatus.label.selectaState",null)%>","<%=stateSelectObjectId%>");
          countryInit(countryArray,"<%=Utility.strNN(((UserOrderStatusForm)theForm).getSiteCountry())%>","<%=ClwI18nUtil.getMessage(request,"shop.orderStatus.label.selectaCountry",null)%>","<%=countrySelectObjectId%>");
          siteInit(siteArray,"<%=Utility.strNN(((UserOrderStatusForm)theForm).getSiteSiteId())%>","<%=ClwI18nUtil.getMessage(request,"shop.orderStatus.label.selectaStore",null)%>","<%=siteSelectObjectId%>");
      }

      function siteSelectOnChange(){
          osSiteListDynamicBox.onChange();
      }

      function stateSelectOnChange(){
          osStateListDynamicBox.onChange();
      }

      function countrySelectOnChange(){
          osCountryListDynamicBox.onChange();
      }
       //--></script>

 <%}%>

<table align=center CELLSPACING=0 CELLPADDING=2 width="<%=Constants.TABLEWIDTH800%>">
<tr><td>
 <table class="breadcrumb">
            <tr class="breadcrumb">
                <td class="breadcrumb"><a class="breadcrumb" href="../userportal/msbsites.do?action=goHome"><app:storeMessage key="breadcrumb.label.home"/></a></td>
                <td class="breadcrumb"><span class="breadcrumb">&nbsp;>&nbsp;</span></td>
                <td class="breadcrumb">
					<%--<a class="breadcrumb" href="orderstatus.do?action=searchXPEDXOrders"><app:storeMessage key="breadcrumb.label.trackOrder"/></a>--%>
					<span class="breadcrumbCurrent"><app:storeMessage key="breadcrumb.label.trackOrder"/></a>
				</td>
                <%--
				<td><span class="breadcrumb">&nbsp;>&nbsp;</span></td>
                <td><a class="breadcrumb" href="#"><%=ShopTool.i18nStatus(((UserOrderStatusForm)theForm).getOrderStatus(),request)%></a></td>
				--%>
			</tr>
            <tr><td>&nbsp;</td></tr>

        </table>
</td></tr>
<tr><td>
<table width="100%">

<html:form name="USER_ORDER_STATUS_FORM" action="/store/orderstatus.do"
           scope="session"
           type="com.cleanwise.view.forms.UserOrderStatusForm">

<tr>
    <td>&nbsp;</td>
    <td align="right"><b><app:storeMessage key="shop.shop.orderStatus.text.orderStartDate"/>:&nbsp;</b></td>
    <td>
        <app:dojoInputDateProgrammaticTag id="orderDateRangeBegin"
                                          name="USER_ORDER_STATUS_FORM"
                                          property="orderDateRangeBegin"
                                          module="clw.NewXpedx"/>
    </td>
    <td align="right"><b><app:storeMessage key="shop.orderStatus.text.orderEndDate"/>:&nbsp;</b></td>
    <td>
        <app:dojoInputDateProgrammaticTag id="orderDateRangeEnd"
                                          name="USER_ORDER_STATUS_FORM"
                                          property="orderDateRangeEnd"
                                          module="clw.NewXpedx"/>
    </td>
    <td><%if (showLocationCriteria) {%>
        <b><app:storeMessage key="shop.orderStatus.text.location"/>:</b>
        <%}%>
    </td>
    <td>
        <%if (showLocationCriteria) {%>
        <select id="<%=countrySelectObjectId%>" name="siteCountry" onchange="countrySelectOnChange();">
            <option selected="true" value=""><app:storeMessage key="shop.orderStatus.label.selectaCountry"/></option>
        </select>
        <%}%>
    </td>
    <td>&nbsp;</td>
</tr>

<tr>
    <td>&nbsp;</td>
    <td align="right"><b> <app:storeMessage key="shop.orderStatus.text.order#"/>:&nbsp;</b></td>
    <td><html:text name="USER_ORDER_STATUS_FORM" property="webOrderConfirmationNum"/></td>
    <td align="right"><b><app:storeMessage key="shop.orderStatus.text.orderStatus"/>:&nbsp;</b></td>
    <td>
        <html:select  name="USER_ORDER_STATUS_FORM" property="orderStatus">

            <logic:present name="USER_ORDER_STATUS_FORM" property="statusValueList">

                <logic:iterate id="statusVal" name="USER_ORDER_STATUS_FORM"
                               property="statusValueList"
                               type="java.lang.String">
                    <html:option value="<%=statusVal%>">
                        <%=com.cleanwise.view.utils.ShopTool.i18nStatus(prefix, statusVal, request)%>
                    </html:option>
                </logic:iterate>
            </logic:present>
            <html:option value="<%=Constants.ORDER_STATUS_ALL%>">
                <%=com.cleanwise.view.utils.ShopTool.i18nStatus(prefix, Constants.ORDER_STATUS_ALL, request)%>
            </html:option>
        </html:select></td>
    <td></td>
    <td>
       <%if (showLocationCriteria) {%>
        <select id="<%=stateSelectObjectId%>" name="siteState" onchange="stateSelectOnChange()">
            <option selected="true" value=""><app:storeMessage key="shop.orderStatus.label.selectaState"/></option>
        </select>
        <%}%>
    </td>

    <td>&nbsp;</td>
</tr>

<tr>
    <td>&nbsp;</td>
    <td align="right"><b><app:storeMessage key="shop.orderStatus.text.PO#"/>:&nbsp;</b></td>
    <td><html:text  name="USER_ORDER_STATUS_FORM" property="custPONum"/></td>
    <td></td>
    <td></td>
    <td><!-- site --> </td>
    <td>
        <%if (showLocationCriteria) {%>
        <select  id="<%=siteSelectObjectId%>" name="siteSite" onchange="siteSelectOnChange();">
            <option selected="true" value=""><app:storeMessage key="shop.orderStatus.label.selectaStore"/></option>
        </select>
        <%}%>
     </td>
    <td>&nbsp;</td>
</tr>
<tr>
    <td colspan="8" align="center">
        <table border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
                <td align="center" valign="middle" nowrap class="xpdexGradientButton">
                    <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('hiddenAction', '<%="searchXPEDXOrders"%>');">
                        <app:storeMessage key="shop.orderStatus.text.viewOrders"/>
                    </a>
                </td>
                <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
            </tr>
        </table>
    </td>
</tr>
<html:hidden property="action" value="hiddenAction"/>
</html:form>
</table>
</td></tr>
<%
//logs to track changes to session variable "pages.css. Logs are
//added as part of bug "2485 - Orderline reverting back to old style"
String val = (String) session.getAttribute("pages.css");
//end of logs for bug 2485
%>



<logic:present name="USER_ORDER_STATUS_FORM" property="resultList">
<bean:size id="rescount"  name="USER_ORDER_STATUS_FORM" property="resultList"/>
<tr><td>
<table align=center CELLSPACING=0 CELLPADDING=0   width="<%=Constants.TABLEWIDTH800%>">
    <tr>
        <td></td>
    </tr>
</table>
<table align=center CELLSPACING=0 CELLPADDING=2    width="<%=Constants.TABLEWIDTH800%>">
<tr align=left>
	<td class="shopcharthead">&nbsp;</td>
    <td class="shopcharthead">
        <div class="fivemargin">
            <a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=<%=StringUtils.getSortField("webordernum",request)%>">
                <app:storeMessage key="shop.orderStatus.text.order#"/></a>
        </div></td>
    <td width=80 class="shopcharthead"><div class="fivemargin">
        <a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=<%=StringUtils.getSortField("orderdate",request)%>">
            <app:storeMessage key="shop.orderStatus.text.date"/></a>
    </div></td>
    <td class="shopcharthead"><div class="fivemargin">
        <a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=<%=StringUtils.getSortField("custponum",request)%>">
            <app:storeMessage key="shop.orderStatus.text.PO#"/></a>
    </div></td>
    <td class="shopcharthead"><div class="fivemargin">
        <a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=<%=StringUtils.getSortField("sitename",request)%>">
            <app:storeMessage key="shop.orderStatus.text.location"/></a>
    </div></td>

    <td class="shopcharthead"><div class="fivemargin">
        <a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=<%=StringUtils.getSortField("status",request)%>">
            <app:storeMessage key="shop.orderStatus.text.status"/></a>
    </div></td>
    <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
        <td class="shopcharthead" align="right">
            <div class="fivemargin">
                <a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=<%=StringUtils.getSortField("amount",request)%>">
                    <app:storeMessage key="shop.orderStatus.text.total"/></a>
            </div></td>
    </logic:equal>
    <td align="left" class="shopcharthead">
        <div class="fivemargin">
            <a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=<%=StringUtils.getSortField("ordertype",request)%>">
                &nbsp;&nbsp;<app:storeMessage key="shop.orderStatus.text.orderType"/></a>
        </div></td>
    <td class="shopcharthead">
        <div class="fivemargin">
            <a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=<%=StringUtils.getSortField("placedby",request)%>">
                <app:storeMessage key="shop.orderStatus.text.orderedBy"/></a>
        </div></td>

	<td class="shopcharthead">
	&nbsp;
	</td>
</tr>

<logic:iterate id="arrele"
               name="USER_ORDER_STATUS_FORM"
               property="resultList"
               indexId="i"
               type="com.cleanwise.service.api.value.OrderStatusDescData">

    <%String styleClass = (((i) %2 )==0) ?  "evenRowColor" : "oddRowColor";%>
    <tr class="<%=styleClass%>">

	<td>&nbsp;</td>
        <td>

            <%
                // All items were added by the system, this indicates and auto order
// placed by the inventory logic.  durval, 3/16/2006
            %>
            <logic:equal name="arrele" property="allOrderItemsAddedBySystemFlag"
                         value="true">
<span class="inv_item">
&nbsp;A&nbsp;
</span>
            </logic:equal>

            <%
                String orderLocale = arrele.getOrderDetail().getLocaleCd();
                if(orderLocale==null) orderLocale = "";
            %>

            <bean:define id="oid" name="arrele" property="orderDetail.orderId"/>
            <!-- bean:define id="orderLocale" name="arrele" property="orderDetail.localeCd"/ -->
            <bean:define id="temps"  type="java.lang.String"  name="arrele" property="orderDetail.orderStatusCd"/>

            <%

                String xlateStatus = com.cleanwise.view.utils.ShopTool.xlateStatus(arrele);
                boolean bakedFl = (!(xlateStatus == null || xlateStatus.equals("Ordered-Processing")));
                String viewOrderUrl = "userOrderDetail.do?action=view&amp;" + "id=" + oid;

            %>

            <% if (!bakedFl) { %>
            <bean:write name="arrele" property="orderDetail.orderNum"/>
            <% } else { %>
            <a href="<%=viewOrderUrl%>">
                <bean:write name="arrele" property="orderDetail.orderNum"/>
            </a>
            <% } %>

        </td>
        <td><bean:write name="arrele" property="orderDetail.originalOrderDate" /></td>
        <td style="padding: 3px;">
            <% if (!bakedFl) { %>
            <bean:write name="arrele" property="orderDetail.requestPoNum"/>
            <% } else { %>
            <a href="<%=viewOrderUrl%>">
                <bean:write name="arrele" property="orderDetail.requestPoNum"/>
            </a>
            <% } %>
        </td>
        <td><bean:write name="arrele" property="orderDetail.orderSiteName" /></td>
        <td> <%=com.cleanwise.view.utils.ShopTool.xlateStatus(arrele,request,prefix)%></td>

        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
            <td style="text-align: right;">
                <%
                    java.math.BigDecimal otot =  arrele.getSumOfAllOrderCharges();
                    if(bakedFl) {
                %>
                <%=ClwI18nUtil.priceFormat(request, otot, (String) orderLocale," ")%>
                <% } else { %>
                &nbsp;
                <% } %>
            </td>
        </logic:equal>
        <td align="left" style="padding: 10px;">
            <logic:present name="arrele" property="orderDetail.orderSourceCd">
                <bean:write name="arrele" property="orderDetail.orderSourceCd" />
            </logic:present>
        </td>
        <td style="padding-left: 3px;">
            <logic:present name="arrele" property="placedBy">
                <logic:present name="arrele" property="placedBy.firstName"><bean:write name="arrele" property="placedBy.firstName" /> </logic:present>
                <br>
                <logic:present name="arrele" property="placedBy.lastName"><bean:write name="arrele" property="placedBy.lastName" /></logic:present>
            </logic:present>
            (<bean:write name="arrele" property="orderDetail.addBy" />)</td>

		<td  class="itemRow">
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
                <app:xpedxButton label='global.action.label.select' url='<%=viewOrderUrl%>'/>
				</td>
			</tr>
		</table>
		</td>
    </tr>

</logic:iterate>
</table>
</td>
<tr>  <td class="tableoutline"><img src="images/spacer.gif" height="1"></td>  </tr>
</tr>


</logic:present>

<%
		String messCd = "";
        String messStyle = "color:#FFFFFF;";
        String mess = "&nbsp;";

		if (ShopTool.requestContainsErrors(request)) {

            messCd = "errors";
            messStyle = "color:#FF0000; white-space: normal; ";

        } else if (Utility.isSet(((UserOrderStatusForm) theForm).getConfirmationMessage())) {

            messCd = "confirmation";
            messStyle = "color:#003399; ";
            mess = ((UserOrderStatusForm) theForm).getConfirmationMessage();

        }

%>
<tr>
   <td align="center" style="<%=messStyle%>">
	<%if (messCd.equals("errors")) {%>
		<html:errors/>
    <%} else {%>
		<%=mess%>
	<%} %>
   </td>
</tr>

        <script language="JavaScript1.2">
            window.location = "#errors";
        </script>
</table>
<script language="JavaScript1.2"> <!--

 dojo.require(xmodule+".form.DateTextBox");

function actionMultiSubmit(actionDef, action) {
  var actionElements = document.getElementsByName('action');
  if(actionElements.length){
  for ( var i = actionElements.length-1; i >=0; i-- ) {

    var element = actionElements[i];
    if(actionDef == element.value){
    element.value = action;
    element.form.submit();
    break;
     }
  }
   }  else if(actionElements){
        actionElements.value = action;
     actionElements.form.submit();
   }

 return false;
}
--></script>

<script language="JavaScript" src="../externals/ajaxutil.js"></script>
<script language="JavaScript" src="../externals/orderStatusUtil.js"></script>
