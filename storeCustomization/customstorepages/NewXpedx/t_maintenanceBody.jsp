<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="UI_FRAME_FORM" type="com.cleanwise.view.forms.UiFrameForm"/>

<table border="0" cellpadding="0" cellspacing="0" align="center"
  width="<%=Constants.TABLEWIDTH800%>"
     style="{border-left: 0; border-right:0;}">
  <tr>
    <td align="left" width="5%"><a class="breadcrumb" href="../userportal/msbsites.do?action=goHome"><app:storeMessage key="breadcrumb.label.home"/></a></td>
    <td align="left" width="2%"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
    <td align="left" ><div class="breadcrumbCurrent"><app:storeMessage key="template.xpedx.maintenance.homepageFramesHeader"/></div>
    </td>
  </tr>

</table>

<%
    String leftButtonURL = ClwCustomizer.getSIP(request,"buttonLeft.png");
    String rightButtonURL = ClwCustomizer.getSIP(request,"buttonRight.png");
    String styleClass = "evenRowColor";
%>

<logic:equal name="UI_FRAME_FORM" property="mode" value="viewFrames">
        <html:form name="UI_FRAME_FORM" scope="session" action="userportal/maintenanceTemplate.do" type="com.cleanwise.view.forms.UiFrameForm">
        <br>&nbsp;
        <table align=center CELLSPACING=0 CELLPADDING=2 width="<%=Constants.TABLEWIDTH800%>" border=0>
        <tr><td>
       <logic:present name="UI_FRAME_FORM" property="frames">
       <bean:size id="rescount"  name="UI_FRAME_FORM" property="frames"/>
       <logic:greaterThan name="rescount" value="0">
          <table width=100% cellpadding="2" cellspacing=0 border=0>
          <tr>
             <td class="shopcharthead" width="60"><div class="fivemargin"><app:storeMessage key="template.xpedx.maintenance.frameId"/></div></td>
             <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="template.xpedx.maintenance.descr"/></div></td>
             <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shop.userProfile.text.locale"/></div></td>
             <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="template.xpedx.maintenance.status"/></div></td>
          </tr>
       <logic:iterate id="frameView" name="UI_FRAME_FORM" property="frames" indexId="i" type="com.cleanwise.service.api.value.UiFrameView">
           <%
           styleClass = (((i.intValue() + 1) %2 )==0) ?  "evenRowColor" : "oddRowColor";
           %>

            <tr class="<%=styleClass%>">
            <td align=center>
                <bean:write name="frameView" property="frameData.uiFrameId"/>
            </td>
            <td>
              <a href="maintenanceTemplateDetail.do?action=detail&selectedFrameId=<%=String.valueOf(frameView.getFrameData().getUiFrameId())%>">
              <bean:write name="frameView" property="frameData.shortDesc"/> (<bean:write name="frameView" property="frameData.longDesc"/>)
              </a>
           </td>
           <td width="25" align=center><bean:write name="frameView" property="frameData.localeCd"/></td>
                <td width="25" align=center>
                    <bean:write name="frameView" property="frameData.statusCd"/>
                </td>
           </tr>
       </logic:iterate>
        </table>
       </logic:greaterThan>
       </logic:present>
       </td>
       <td width="130" valign=top align=right>
           <table cellpadding="0" cellspacing="0" border="0"><tr>
           <td align="right" valign="middle"><img src="<%=leftButtonURL%>" border="0"></td>
           <td align="center" valign="middle" nowrap class="xpdexGradientButton"><a href="#"
           class="xpdexGradientButton"
           onclick="document.forms['UI_FRAME_FORM'].submit();"><app:storeMessage key="template.xpedx.button.createNew"/></a></td>
           <td align="left" valign="middle"><img src="<%=rightButtonURL%>" border="0"></td>
           </tr></table>
           <html:hidden name="UI_FRAME_FORM" property="action" value="Create New"/>

        </td>
       </tr>
        </html:form>
</table>
</logic:equal>


 <logic:equal name="UI_FRAME_FORM" property="mode" value="viewTemplates">

     <html:form name="UI_FRAME_FORM" scope="session" action="userportal/maintenanceTemplateDetail.do" type="com.cleanwise.view.forms.UiFrameForm">
     <p class=text align=left><app:storeMessage key="template.xpedx.maintenance.selectTemplate"/></p>
         <table align=center CELLSPACING=0 CELLPADDING=2 width="<%=Constants.TABLEWIDTH800%>" border=0>
    <logic:present name="UI_FRAME_FORM" property="templates">
    <bean:size id="rescount"  name="UI_FRAME_FORM" property="templates"/>
    <logic:greaterThan name="rescount" value="0">
             <tr>
                <td class="shopcharthead" >&nbsp;</td>
                <td class="shopcharthead" width="80"><div class="fivemargin"><app:storeMessage key="template.xpedx.maintenance.templateId"/></div></td>
                <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="template.xpedx.maintenance.descr"/></div></td>
             </tr>
    <logic:iterate id="templateView" name="UI_FRAME_FORM" property="templates" indexId="i" type="com.cleanwise.service.api.value.UiFrameView">
        <%
        styleClass = (((i.intValue() + 1) %2 )==0) ?  "evenRowColor" : "oddRowColor";
        %>

         <tr class="<%=styleClass%>">

        <tr>
            <td width=25 align=center><html:radio name="UI_FRAME_FORM" property="selectedTemplateId" value="<%=String.valueOf(templateView.getFrameData().getUiFrameId())%>"/></td>
            <td width="80" align=center><bean:write name="templateView" property="frameData.uiFrameId"/></td>
            <td><bean:write name="templateView" property="frameData.shortDesc"/> (<bean:write name="templateView" property="frameData.longDesc"/>)</td>
        </tr>
    </logic:iterate>
    </logic:greaterThan>
    </logic:present>
    <tr>
     <td colspan=3 align=right>
         <table cellpadding="0" cellspacing="0" border="0"><tr>
         <td align="right" valign="middle"><img src="<%=leftButtonURL%>" border="0"></td>
         <td align="center" valign="middle" nowrap class="xpdexGradientButton"><a href="#"
         class="xpdexGradientButton"
         onclick="document.forms['UI_FRAME_DETAIL_FORM'].submit();"><app:storeMessage key="template.xpedx.button.continue"/></a></td>
         <td align="left" valign="middle"><img src="<%=rightButtonURL%>" border="0"></td>
         </tr></table>
         <html:hidden name="UI_FRAME_FORM" property="action" value="Continue"/>
     </td>
    </tr>
     </html:form>
     </table>
    </logic:equal>

