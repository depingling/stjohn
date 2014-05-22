<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.*"%>
<%@ page import="java.util.Locale" %>

<%@ page import="java.lang.Integer" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%@ page import="org.apache.struts.action.ActionForm" %>
<%@ page import="org.apache.struts.action.ActionError" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>

<%@ page import="com.cleanwise.service.api.value.AddressData" %>
<%@ page import="com.cleanwise.service.api.value.BuildingServicesContractorViewVector" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingRestrictionsView" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingRestrictionsViewVector" %>
<%@ page import="com.cleanwise.service.api.value.SiteData" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.forms.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.*" %>
<%@ page import="com.cleanwise.service.api.util.*" %>
<%@ page import="com.cleanwise.service.api.dao.ShoppingControlDataAccess" %>
<%@ page import="com.cleanwise.service.api.session.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<script language="JavaScript1.2">
<!--
<%
String isGotoAnchor = (String)request.getAttribute("gotoAnchor");
if("true".equals(isGotoAnchor)){
%>
	
function getAnchorPosition (anchorName) {
  if (document.layers) {
    var anchor = document.anchors[anchorName];
    return { x: anchor.x, y: anchor.y };
  }
  else if (document.getElementById) {
  
    var anchor = document.getElementById(anchorName);
	if(anchor==null){
        anchor = document.anchors[anchorName];
	}
    var coords = {x: 0, y: 0 };
    while (anchor) {
      coords.x += anchor.offsetLeft;
      coords.y += anchor.offsetTop;
      anchor = anchor.offsetParent;
    }
    return coords;
  }
}

/* for Internet Explorer */
/*@cc_on @*/
/*@if (@_win32)
  document.write("<script id=__ie_onload defer><\/script>");
  var script = document.getElementById("__ie_onload");
  script.onreadystatechange = function() {
    if (this.readyState == "complete") {
      pageScroll(); // call the onload handler
    }
  };
/*@end @*/

function pageScroll() {
  // quit if this function has already been called
  if (arguments.callee.done) return;

  // flag this function so we don't do the same thing twice
  arguments.callee.done = true;

  // kill the timer
  if (_timer) clearInterval(_timer);

	//alert(getAnchorPosition("buttonSection").y);
    window.scrollTo(100,parseInt(getAnchorPosition("buttonSection").y)); 
};

/* for Mozilla/Opera9 */
if (document.addEventListener) {
  document.addEventListener("DOMContentLoaded", pageScroll, false);
}



/* for Safari */
if (/WebKit/i.test(navigator.userAgent)) { // sniff
  var _timer = setInterval(function() {
    if (/loaded|complete/.test(document.readyState)) {
      pageScroll(); // call the onload handler
    }
  }, 10);
}

<%}%>
//-->
</script>
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="currSite" name="ApplicationUser" property="site" type="com.cleanwise.service.api.value.SiteData"/>
<bean:define id="theForm" name="SITE_SHOPPING_CONTROL_FORM" type="com.cleanwise.view.forms.SiteShoppingControlForm"/>

<html:form
  name="SITE_SHOPPING_CONTROL_FORM"
  action="userportal/customerStoreManagementProfileNewXpedx.do"
  type="com.cleanwise.view.forms.SiteShoppingControlForm">

<html:hidden property="action" value="hiddenAction"/>
<%
//String isGotoAnchor = (String)request.getAttribute("gotoAnchor");
if("true".equals(isGotoAnchor)){	%>
	<body onload="pageScroll()">
<%}else{%>
<body>
<%}%>
<table align=left CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH800%>" border=0>
<tr><td>
   <table class="breadcrumb" align=left>
            <tr class="breadcrumb">
                <td class="breadcrumb"><a class="breadcrumb" href="../userportal/msbsites.do?action=goHome"><app:storeMessage key="breadcrumb.label.home"/></a></td>
                <td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
                <td class="breadcrumb">
                <div class="breadcrumbCurrent"><app:storeMessage key="newxpdex.storeProfile.header"/></div>
                </td>
            </tr>
            <tr><td>&nbsp;</td></tr>
    </table>
</td></tr>

<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.VIEW_SPECIAL_ITEMS%>">
<tr><td width="100%">
    <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"t_custAcctMgtSurveyDetail.jsp")%>' />
</td></tr>

<tr><td>
<!-- line -->
</td></tr>

<tr><td width="100%"><hr style="width:100%"/></tr>
</app:authorizedForFunction>

<tr><td width="100%">
<%
    String thisSiteId = String.valueOf(currSite.getSiteId());
    String styleClass = "evenRowColor";
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
%>
<html:hidden name="SITE_SHOPPING_CONTROL_FORM" property="siteId"   value="<%=thisSiteId%>"/>


<table cellSpacing=0 cellPadding=2 align=center border=0 width="100%">
<tr>
<td width="10">&nbsp;</td>
<td colspan=8><div class="fivemargin"><b><app:storeMessage key="newxpdex.storeProfile.maxOrderQty.header"/></b></div></td>
</tr>

<tr>
<td class="shopcharthead" width="10">&nbsp;</td>
<td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="newxpdex.storeProfile.columnHeader.sku"/></div></td>
<td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="newxpdex.storeProfile.columnHeader.productName"/></div></td>
<td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="newxpdex.storeProfile.columnHeader.size"/></div></td>
<td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="newxpdex.storeProfile.columnHeader.uom"/></div></td>
<td class="shopcharthead">&nbsp;</td>
<td class="shopcharthead" align="center"><div class="fivemargin"><app:storeMessage key="newxpdex.storeProfile.columnHeader.maxOrderQty"/></div></td>
<td class="shopcharthead" align="center"><div class="fivemargin"><app:storeMessage key="newxpdex.storeProfile.columnHeader.restrictionDays"/></div></td>
<td class="shopcharthead">&nbsp;</td>
</tr>

<%

    // new code by SVC: Begin
        ActionErrors ae = new ActionErrors();
        //HttpSession session = request.getSession();
        APIAccess factory = null;
        String cuser = (String) session.getAttribute(Constants.USER_NAME);
        List msgList = new ArrayList();
%>

<%  
        SiteSettingsData ssd = null;

        try {
    
            factory = new APIAccess();
        	//SiteShoppingControlForm sform = (SiteShoppingControlForm)form;
        	//int siteid = sform.getSiteId();
        	Site sbean = factory.getSiteAPI();
        	ShoppingControlDataVector scdv = new ShoppingControlDataVector();
        	SessionTool st = new SessionTool(request);        	
        		        	
        	ssd = st.getSiteSettings(request, currSite.getSiteId());
        	int accid = ssd.getSiteData().getAccountId();

            //int accid = ssd.getSiteData().getAccountId();
            int catid = ssd.getSiteData().getContractData().getCatalogId();
            
            //Iterator it = ssd.getShoppingControls().iterator();
            SiteData siteD = sbean.getSite(currSite.getSiteId());
            
            ShoppingControlDataVector scdV = siteD.getShoppingControls();
            Iterator it = siteD.getShoppingControls().iterator();

%>

<%          
            ssd.setShoppingControls(scdV);             
        
    }catch (Exception e){
	    e.printStackTrace();
	} 
     // new code by SVC: End
%>

<%
java.util.ArrayList sctrlv = null;
if (ssd != null && ssd.getShoppingControls() != null) {
    sctrlv = new java.util.ArrayList(ssd.getShoppingControls());
}
int j = 0;
for (int i = 0; sctrlv != null && i < sctrlv.size(); i++) {

    ShoppingControlItemView siteItemControl = (ShoppingControlItemView) sctrlv.get(i);
    String key = String.valueOf(siteItemControl.getShoppingControlData().getItemId());
%>

<%     
    // Old code: Begin
     
    //SiteSettingsData siteSettings = SessionTool.getSiteSettings(request, currSite.getSiteId());
    //java.util.ArrayList sctrlv = null;
    //if (siteSettings != null && siteSettings.getShoppingControls() != null) {
    //    sctrlv = new java.util.ArrayList(siteSettings.getShoppingControls());
    //}
    //int j = 0;
    //for (int i = 0; sctrlv != null && i < sctrlv.size(); i++) {

    //    ShoppingControlItemView siteItemControl = (ShoppingControlItemView) sctrlv.get(i);
    //    String key = String.valueOf(siteItemControl.getShoppingControlData().getItemId());

    // Old code: End    
%>

<%
	/* Product Info */
if ( siteItemControl.getSkuNum() != null &&
        siteItemControl.getSkuNum().length() > 0 ) { %>
<%
styleClass = (((j + 1) %2 )==0) ?  "evenRowColor" : "oddRowColor";
j++;
%>

<tr class="<%=styleClass%>">
    <td>&nbsp;</td>
    <td><%=siteItemControl.getSkuNum()%></td>
    <td><%=siteItemControl.getShortDesc()%></td>
    <td><%=siteItemControl.getSize()%>&nbsp;</td>
    <td><%=siteItemControl.getUOM()%></td>
    <td><%=siteItemControl.getPack()%></td>
    <td  align="center">
        <%
            //String thisFormVal  = Utility.strNN((String)((com.cleanwise.view.forms.SiteShoppingControlForm)theForm).getItemIdMaxAllowed(key));
            int intMaxOrderQty = siteItemControl.getShoppingControlData().getMaxOrderQty();        
            String thisFormVal = Integer.toString(intMaxOrderQty);             
            String thisFormTag  = "itemIdMaxAllowed("+ String.valueOf(siteItemControl.getShoppingControlData().getItemId()) + ")";
                        
            //String thisFormVal1 = Utility.strNN((String)((com.cleanwise.view.forms.SiteShoppingControlForm)theForm).getItemIdRestrictionDays(key));
            int intRestDays = siteItemControl.getShoppingControlData().getRestrictionDays();
            String thisFormVal1 = Integer.toString(intRestDays);
            String thisFormTag1 = "itemIdRestrictionDays("+ String.valueOf(siteItemControl.getShoppingControlData().getItemId()) + ")";       
        
            // New by SVC: Begin
            if (thisFormVal.trim().equals("-1")) thisFormVal = "*";
            if (thisFormVal1.trim().equals("-1")) thisFormVal1 = "*";
            // New by SVC: End
        %>
        <% if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.EDIT_SITE_SHOPPING_CONTROLS)) { %>
            <html:text name="SITE_SHOPPING_CONTROL_FORM" property="<%=thisFormTag%>"   value="<%=thisFormVal%>"  size='3'/>
        <%} else { %>
            <%=thisFormVal%>
        <%}%>
    </td>

    <td align="center">
        <% if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.EDIT_SITE_SHOPPING_CONTROLS)) { %>
            <html:text name="SITE_SHOPPING_CONTROL_FORM" property="<%=thisFormTag1%>"   value="<%=thisFormVal1%>"  size='3'/>
        <%} else { %>
            <%=thisFormVal1%>
        <%}%>
    </td>

    <td>
        <%=siteItemControl.getShoppingControlData().getModBy()%>
        <%=ClwI18nUtil.formatDate(request, siteItemControl.getShoppingControlData().getModDate())%>
    </td>
</tr>

<% } 	/* Product info */ %>

<% } %>
<tr><td colspan="9" height="4"><img src="images/spacer.gif" height="4"></td></tr>
<tr>
<td class="shopcharthead" colspan="9" height="1"><img src="images/spacer.gif" height="2"></td>
</tr>
<tr><td colspan="9" height="4"><a name="buttonSection"><img src="images/spacer.gif" height="4"></td></tr>

<script type="text/javascript">
function updateMaxOrderQty() {
    document.forms['SITE_SHOPPING_CONTROL_FORM'].action.value = "update_site_controls";
    document.forms['SITE_SHOPPING_CONTROL_FORM'].submit();
}
</script>
<% if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.EDIT_SITE_SHOPPING_CONTROLS)) { %>
<tr>
	
    <td colspan="9" align=right>
    <table border="0" cellpadding="0" cellspacing="0" ><tr>
    <app:xpedxButton label='newxpdex.storeProfile.lable.updateMaxOrderQty'
        url="#"  onClick='updateMaxOrderQty(); return false;' />
    </tr></table>
    </td>
</tr>
<%}%>

</table>
</td></tr>

<logic:equal name="lastAction" scope="request" value="update_site_controls">
<tr><td align="center"><font color="red"><html:errors/></font></td></tr>
</logic:equal>
<logic:notEmpty name="maxQtySuccessMessage" scope="request">
    <tr><td align="center"><font color="blue"><b><bean:write name="maxQtySuccessMessage" scope="request"/></b></font></td></tr>
</logic:notEmpty>




</table>
</body>
</html:form>
