<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="UI_FRAME_DETAIL_FORM" type="com.cleanwise.view.forms.UiFrameDetailForm"/>

<table border="0" cellpadding="0" cellspacing="0" align="center"
  width="<%=Constants.TABLEWIDTH800%>"
     style="{border-left: 0; border-right:0;}">
  <tr>
    <td align="left" width="5%"><a class="breadcrumb" href="../userportal/msbsites.do?action=goHome"><app:storeMessage key="breadcrumb.label.home"/></a></td>
    <td align="left" width="2%"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
    <td align="left"><div class="breadcrumbCurrent"><app:storeMessage key="template.xpedx.maintenance.homepageFramesHeader"/></div>
    </td>
  </tr>
</table>

<html:form name="UI_FRAME_DETAIL_FORM" scope="session"
    action="userportal/maintenanceTemplateDetail.do"
    type="com.cleanwise.view.forms.UiFrameDetailForm"
    enctype="multipart/form-data"
    target="previewFrameWindow">

<table align=center CELLSPACING=0 CELLPADDING=2 width="<%=Constants.TABLEWIDTH800%>" border=0>
<tr>
<td>&nbsp;</td>

<td width="520">
<table width="100%" cellspacing="2" cellpadding="0" border="0">
<tr><td>
<tr>
    <td width=110><b><app:storeMessage key="template.xpedx.maintenance.frameId"/>:</b></td>
    <td width="80" align="center"><bean:write name="UI_FRAME_DETAIL_FORM" property="frameData.uiFrameId"/></td>
    <td width="300" align="right" valign="middle"><b><app:storeMessage key="shop.userProfile.text.locale"/>:
<html:select name="UI_FRAME_DETAIL_FORM" property="frameData.localeCd" >
<html:option value='<%=""%>'>Default</html:option><%
for(java.util.Iterator iter=ClwI18nUtil.getAllLocales().iterator(); iter.hasNext();) {
  String localeCd = (String) iter.next();
%><html:option value="<%=localeCd%>"><%=localeCd%></html:option>
<% } %>
</html:select>&nbsp;&nbsp;&nbsp;<b><app:storeMessage key="template.xpedx.maintenance.status"/>:</b>&nbsp;
        <html:select name="UI_FRAME_DETAIL_FORM" property="frameData.statusCd">
            <html:options  collection="uiframe.status.vector" property="value" />
        </html:select>
    </td>
</tr>
<tr>
    <td width="110"><b><app:storeMessage key="template.xpedx.maintenance.shortDescr"/>:</b></td>
    <td colspan="2" align="right"><html:text name="UI_FRAME_DETAIL_FORM" property="frameData.shortDesc" size="58"/></td>
</tr>
<tr>
    <td width="110"><b><app:storeMessage key="template.xpedx.maintenance.longDescr"/>:</b></td>
    <td colspan="2" align="right">
        <html:text name="UI_FRAME_DETAIL_FORM" property="frameData.longDesc" size="58"/>
    </td>
</tr>
<tr><td colspan=3>&nbsp;</td></tr>
</table>

<div class="itemGroupShortInfo"  style="background-color:#fff">

<b class=rtopborder style="background-color:#fff">
    <b class=r1border style="background-color:#b5b5b5"></b>
    <b class=r2border style="background-color:#fff"></b>
    <b class=r3border style="background-color:#fff"></b>
    <b class=r4border style="background-color:#fff"></b>
</b>

<div class="borderContent" style="border-left:1px solid #B5B5B5;border-right:1px solid #B5B5B5;background-color:white">
<div>
<table width="100%" cellspacing="10" cellpadding="10">
<logic:present name="UI_FRAME_DETAIL_FORM" property="slots">

  <bean:size id="rescount"  name="UI_FRAME_DETAIL_FORM" property="slots"/>
  <logic:greaterThan name="rescount" value="0">
    <bean:define id="slots" name="UI_FRAME_DETAIL_FORM" property="slots" type="com.cleanwise.service.api.value.UiFrameSlotViewVector" />
<%
    Iterator i = slots.iterator();
    int k = 0;
    UiFrameSlotView slot = null;
    while (i.hasNext()) {
%>
      <tr>
<%        for (int j=0; j<2; j++) {
          if (i.hasNext()) {
            slot = (UiFrameSlotView) i.next();
            String slotType = "slotType[" + k + "]";
            String slotUrl  = "slotUrl[" + k + "]";
            String slotVal = "slotValue[" + k + "]";
            String slotNewWin = "slotNewWin[" + k + "]";
            String slotFile = "imageSlotFile[" + k + "]";
            String textValue = "textValue[" + k + "]";
            boolean wideStr = j==0 && !i.hasNext();
            String tdWidth = wideStr ? "width=100% colspan=2" : "width=50%";
            String imageProperty = "showProperties('imageProperty[" + k + "]', 'textProperty["+ k + "]');";
            String textProperty = "showProperties('textProperty[" + k + "]', 'imageProperty["+ k + "]');";
            String imageDisplayStyle = slot.getSlotTypeCd().equals(RefCodeNames.SLOT_TYPE_CD.IMAGE)?"display":"none";
            String textDisplayStyle = slot.getSlotTypeCd().equals(RefCodeNames.SLOT_TYPE_CD.HTML_TEXT)?"display":"none";
            String desc = "desc"+k;
%>

        <td <%=tdWidth%> valign="top">
        <div align="center">
            <html:radio name="UI_FRAME_DETAIL_FORM" property="<%=slotType%>" value="<%=RefCodeNames.SLOT_TYPE_CD.IMAGE%>" onclick="<%=imageProperty%>"/>&nbsp;Image&nbsp;
            <html:radio name="UI_FRAME_DETAIL_FORM" property="<%=slotType%>" value="<%=RefCodeNames.SLOT_TYPE_CD.HTML_TEXT%>" onclick="<%=textProperty%>"/>&nbsp;Text&nbsp;
        </div>
        <div class="itemGroupShortInfo"  style="background-color:#fff">
            <b class=rtopborder style="background-color:#fff">
                <b class=r1border style="background-color:#b5b5b5"></b>
                <b class=r2border style="background-color:#fff"></b>
                <b class=r3border style="background-color:#fff"></b>
                <b class=r4border style="background-color:#fff"></b>
            </b>
        <div class="borderContent" style="border-left:1px solid #B5B5B5;border-right:1px solid #B5B5B5;background-color:white">
        <table width="100%" cellspacing="0" cellpadding="0" height="380" border=0>
        <tr>
            <td height="32" valign="middle" valign="top">
            <div id="imageProperty[<%=k%>]" name="imageProperty[<%=k%>]" style="display:<%=imageDisplayStyle%>">
            &nbsp;&nbsp;<html:file name="UI_FRAME_DETAIL_FORM" property="<%=slotFile%>" accept="image/jpeg,image/gif" style="width:225"/><br></div>
            <nobr>&nbsp;&nbsp;<app:storeMessage key="template.xpedx.maintenance.url"/>:&nbsp;
            <html:text name="UI_FRAME_DETAIL_FORM" property="<%=slotUrl%>" style="width:190"/></nobr>
            </td>
        </tr>
        <tr>
           <td valign="bottom" height="20">
              <nobr>&nbsp;&nbsp;<app:storeMessage key="template.xpedx.maintenance.openLinkInNewWindow"/>:&nbsp;
               <html:radio name="UI_FRAME_DETAIL_FORM" property="<%=slotNewWin%>" value="1"/>&nbsp;<app:storeMessage key="global.text.yes"/>&nbsp;
                          <html:radio name="UI_FRAME_DETAIL_FORM" property="<%=slotNewWin%>" value="0"/>&nbsp;<app:storeMessage key="global.text.no"/></nobr>
           </td>
        </tr>
        <tr><td valign=middle align=center valign="top">
            <div id='textProperty[<%=k%>]'  name="textProperty[<%=k%>]" style="display:<%=textDisplayStyle%>"><div align="left">
            &nbsp;&nbsp;<html:textarea name="UI_FRAME_DETAIL_FORM" styleId="<%=desc %>" property="<%=textValue%>" rows="10" cols="60" style="width: 97%; height: 150px" />
 			<script type="text/javascript">
 			
				CKEDITOR.replace( '<%=desc %>',
						{
							
							toolbar :
										[
											['Undo','Redo'],
											['Find','Replace','-','SelectAll','RemoveFormat'],
											['Link', 'Unlink'],
											['TextColor', '-', 'Smiley','SpecialChar'],
											'/',
											['Styles','Format','Font','FontSize', 'Bold', 'Italic','Underline'],
											['NumberedList','BulletedList','-','Blockquote']
											
										],	
							fullPage : false,
							extraPlugins : 'docprops'
						});
  			</script>
            <script type="text/javascript">
              textAreaSlots[textAreaSlots.length] = "<%=textValue%>";
              textAreaSlotsWide[textAreaSlotsWide.length] = "<%=wideStr%>";
            </script>
            </div>
            </div>
            <div id="imageProperty[<%=k%>]" name="imageProperty[<%=k%>]" style="display:<%=imageDisplayStyle%>">
            <bean:define id="img1" type="java.lang.String" name="UI_FRAME_DETAIL_FORM" property="<%=slotVal%>"/>
            <% if (img1.length() > 0) { %>
                <app:uiFrameSlotImageTag slot="<%=slot%>" />
                <br><%=img1%></br>
            <% } %>
            </div>
        </td></tr>

        </table>
        </div>
            <b class=rbottomborder style="background-color:#fff">
                <B class=r4border style="background-color:#fff"></b>
                <b class=r3border style="background-color:#fff"></b>
                <b class=r2border style="background-color:#fff"></b>
                <b class=r1border style="background-color:#b5b5b5"></b>
            </b>
        </div>

        </td>
        <% } // if has next%>
        <%  k++; %>
        <% } // for  %>
      </tr>
   <% } //while has next %>
 </logic:greaterThan>

 </logic:present>

<script type="text/javascript">
function preview() {
    document.forms['UI_FRAME_DETAIL_FORM'].action.value = "Preview Template";
    document.forms['UI_FRAME_DETAIL_FORM'].target="previewFrameWindow";
    var previewFrameWindow = window.open( "", "previewFrameWindow",
            "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=600,width=780,left=100,top=165"
        );
    //setTimeout("previewFrameWindow.focus();",200);
    document.forms['UI_FRAME_DETAIL_FORM'].submit();
}
function save() {
    document.forms['UI_FRAME_DETAIL_FORM'].action.value = "save";
    document.forms['UI_FRAME_DETAIL_FORM'].target="_self";
    document.forms['UI_FRAME_DETAIL_FORM'].submit();
}
</script>
<%
    String leftButtonURL = ClwCustomizer.getSIP(request,"buttonLeft.png");
    String rightButtonURL = ClwCustomizer.getSIP(request,"buttonRight.png");
%>

<tr><td colspan=4 align=right>
    <table cellpadding="0" cellspacing="0" border="0"><tr>
    <td align="right" valign="middle"><img src="<%=leftButtonURL%>" border="0"></td>
    <td align="center" valign="middle" nowrap class="xpdexGradientButton"><a href="#"
    class="xpdexGradientButton"
    onclick="return preview();"><app:storeMessage key="template.xpedx.button.preview"/></a></td>
    <td align="left" valign="middle"><img src="<%=rightButtonURL%>" border="0"></td>

    <td>&nbsp;</td>

    <td align="right" valign="middle"><img src="<%=leftButtonURL%>" border="0"></td>
    <td align="center" valign="middle" nowrap class="xpdexGradientButton"><a href="#"
    class="xpdexGradientButton"
    onclick="return save();"><app:storeMessage key="template.xpedx.button.publish"/></a></td>
    <td align="left" valign="middle"><img src="<%=rightButtonURL%>" border="0"></td>
    </tr></table>
    <html:hidden name="UI_FRAME_DETAIL_FORM" property="action" value="save"/>

</td></tr>
</table>

</div>
</div>
<b class=rbottomborder style="background-color:#fff">
    <B class=r4border style="background-color:#fff"></b>
    <b class=r3border style="background-color:#fff"></b>
    <b class=r2border style="background-color:#fff"></b>
    <b class=r1border style="background-color:#b5b5b5"></b>
</b>
</div>

</td>
<td>&nbsp;</td>
</tr>
</table>

<%
Object[] params = new Object[1];
params[0] = 388;
String imageSize = ClwI18nUtil.getMessage(request,"template.xpedx.maxImageSize", params);
params[0] = 4000;
String textSize =  ClwI18nUtil.getMessage(request,"template.xpedx.maxTextSize", params);
%>
<table>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
	<td><b><%=imageSize%></b></td>
</tr>

</table>

</html:form>

