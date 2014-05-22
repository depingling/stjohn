<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="com.cleanwise.service.api.util.Utility" %>

<%@ page import="com.cleanwise.service.api.value.NoteJoinView"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.DateFormat"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="UI_FRAME_FORM" type="com.cleanwise.view.forms.UiFrameForm"/>

<script type="text/javascript" src="../externals/ckeditor_3.6/ckeditor.js"></script>

<script type="text/javascript">
<!-- //
    function silentErrorHandler() {return true;}
    window.onerror=silentErrorHandler;
// -->
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

    function Preview(user) {
        var f = document.forms['UI_FRAME_FORM'];

        appleNewsPreviewAjax.preview(
                f.topicName.value,
                user,
                f.newsEffDate.value,
                CKEDITOR.instances.articleContents.getData() )
    }

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

function showHideLocaleCD()
{
    var localeCDstyle = new String();
    localeCDstyle = document.getElementById("localeCDDiv").style.visibility;
    if(localeCDstyle.toLowerCase()=="visible" || localeCDstyle == "")
    {
        document.getElementById("localeCDDiv").style.visibility = "hidden";
    }
    else
    {
        document.getElementById("localeCDDiv").style.visibility = "visible";
    }
}

    function toggleSelect(id1, id2){
	if (document.getElementById(id2).checked == true){
	    document.getElementById(id1).checked = true;
	}
    }
</script>

<style type="text/css">
a:link, a:active, a:visited, a:hover {
    text-decoration: none;
    color: black;
    font-weight: normal;
    background-color: white;
}
</style>

<style type="text/css">
    .localeCDstyle {
    width:200px;
    margin:0px auto;
    }
</style>

<SCRIPT>function set(target) {document.forms[0].dispatch.value=target;}</SCRIPT>

<%
  String sAddNew = ClwI18nUtil.getMessage(request, "news.admin.button.addNewArticle",null);
  String sDeleteSelected = ClwI18nUtil.getMessage(request, "news.admin.button.deleteSelected",null);
  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

%>

<%--<div class="regularrz">  --%>
<table align=left CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH800%>" border=0>
<tr><td>
<%-----------------bread crumb-----------------------%>
<table class="breadcrumb" align="left">
  <tr class="breadcrumb">
    <td class="breadcrumb"><a class="breadcrumb" href="../userportal/msbsites.do?action=goHome"><app:storeMessage key="breadcrumb.label.home"/></a></td>

    <logic:equal name="UI_FRAME_FORM" property="mode2" value="viewArticles">
      <td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
      <td class="breadcrumb">
        <div class="breadcrumbCurrent"><app:storeMessage key="shop.menu.main.maintenance"/>&nbsp;:&nbsp;<app:storeMessage key="breadcrumb.label.news"/></div>
      </td>
    </logic:equal>

    <logic:notEqual name="UI_FRAME_FORM" property="mode2" value="viewArticles">
      <td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
      <td class="breadcrumb"><a class="breadcrumb" href="../userportal/maintenanceNews.do"><app:storeMessage key="shop.menu.main.maintenance"/>&nbsp;:&nbsp;<app:storeMessage key="breadcrumb.label.news"/></a>
      </td>
    </logic:notEqual>

    <logic:equal name="UI_FRAME_FORM" property="mode2" value="editArticle">
      <td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
      <td class="breadcrumb">
        <div class="breadcrumbCurrent"><app:storeMessage key="breadcrumb.label.articleMaintenance"/></div>
      </td>
    </logic:equal>
  </tr>
  <tr><td>&nbsp;</td></tr>
</table>
<%-------------end of --bread crumb-----------------------%>
</td></tr>
<tr><td>
<logic:equal name="UI_FRAME_FORM" property="mode2" value="viewArticles">
      <html:form name="UI_FRAME_FORM" scope="session" action="userportal/maintenanceNews.do" type="com.cleanwise.view.forms.UiFrameForm">
      <html:hidden property="action" value="hiddenAction"/>
	  <%
		//String isGotoAnchor = (String)request.getAttribute("gotoAnchor");
		if("true".equals(isGotoAnchor)){
      %>
			<body onload="pageScroll()">
		<%}else{%>
			<body>
		<%}%>
      <table align=left CELLSPACING=0 CELLPADDING=0  border=0  width="100%">
        <tr><td align=left>
          <table border="0" cellpadding="0" cellspacing="0" >
            <tr>
              <app:xpedxButton label='news.admin.button.addNewArticle'
			url="#"
                        onClick='actionMultiSubmit("hiddenAction", "Add New Article");'
		/>
            </tr>
          </table>
          </td>
        </tr>

        <tr><td align=left>
          <p class=regularrz><app:storeMessage key="news.admin.heading.toEditAnArticle"/></p>
        </td>
        </tr>

        <tr>
          <td>
          <table align=left width="100%" border=0>
          <tr>
            <td>&nbsp;</td>
            <td>
            <logic:present name="UI_FRAME_FORM" property="noteJoinViewVector">
            <bean:size id="rescount"  name="UI_FRAME_FORM" property="noteJoinViewVector"/>
            <logic:greaterThan name="rescount" value="0">
            <table width=100% cellpadding=0 cellspacing=0 border=0>
            <tr style="font-weight: bold; " align="left">
             <td style="padding-right:10px;"><app:storeMessage key="global.action.label.delete"/></td>
             <td style="padding-right:10px;"><app:storeMessage key="news.admin.text.datePosted"/></td>
             <td style="text-align:center;"><app:storeMessage key="news.admin.text.forced"/></td>
             <td style="padding-right:10px;"><app:storeMessage key="shop.userProfile.text.locale"/></td>
             <td ><app:storeMessage key="news.admin.text.articleTitle"/></td>
             <td  nowrap="nowrap"><app:storeMessage key="news.admin.text.lastUpdatedBy"/></td>
            </tr>
            <% int cI = 0; %>
            <logic:iterate id="noteJoinView" name="UI_FRAME_FORM" property="noteJoinViewVector" indexId="i" type="com.cleanwise.service.api.value.NoteJoinView">
            <%
            	NoteJoinView noteJVw = (NoteJoinView) theForm.getNoteJoinViewVector().get(cI);
            	int counterInt = noteJVw.getNote().getCounter();
            	String counterS = "";
            	if (counterInt > 0) {
            	    counterS = "Yes";
            	} else {
            	    counterS = " ";
            	}
		String forcedEachLogin = noteJVw.getNote().getForcedEachLogin();
		if(Utility.isTrue(forcedEachLogin)){
		    counterS = ClwI18nUtil.getMessage(request,"news.admin.text.eachLogin",null);
		}
            	cI++;

            	Date effectiveDate = noteJVw.getNote().getEffDate();
                DateFormat medium = DateFormat.getDateInstance(DateFormat.MEDIUM);
                String effectiveDateInMediumFormatS = medium.format(effectiveDate);
            %>
            <tr>
              <td  align="left">
              <html:checkbox name="UI_FRAME_FORM" property="selectorBox" value="<%=i.toString()%>"/>
              </td>
              <td  align="left">
                <%=effectiveDateInMediumFormatS%>
              </td>
              <td align="center">
                <%=counterS%>
              </td>
              <td><bean:write name="noteJoinView" property="note.localeCd"/></td>
              <td>
                <a href="maintenanceNews.do?action=Edit Article&selectedArticleId=<%=String.valueOf(noteJoinView.getNote().getNoteId())%>">
                <bean:write name="noteJoinView" property="note.title"/>
                </a>
              </td>
              <td  align="left">
               <bean:write name="noteJoinView" property="note.modBy"/>
              </td>
            </tr>
          </logic:iterate>
          </table>
          </logic:greaterThan>
          </logic:present>
          </td>
        </tr>
      </table>
      </td>
    </tr>

    <tr><td><a name="buttonSection"></a>&nbsp;</td><tr>
    <tr>
       <td  align=left>
          <table border="0" cellpadding="0" cellspacing="0" >
            <tr>
              <app:xpedxButton label='news.admin.button.deleteSelected'
			url="#"
                        onClick='actionMultiSubmit("hiddenAction", "Delete Selected");'
		/>
            </tr>
          </table>
       </td>
    </tr>
	</body>
    </html:form>
  </table>
</logic:equal>
</td></tr>

<tr><td>
 <logic:equal name="UI_FRAME_FORM" property="mode2" value="editArticle">

     <html:form name="UI_FRAME_FORM" scope="session" action="userportal/maintenanceNews.do" type="com.cleanwise.view.forms.UiFrameForm">
     <html:hidden property="action" value="hiddenAction"/>
  <table align=center  border=0>
    <tr>
      <td><app:storeMessage key="news.admin.text.title"/>:</td>
      <td ><html:text  property="topicName" size="83"/></td>
    </tr>

    <tr>
      <td>&nbsp;</td>
      <td>
        <table>
          <tr>
            <td><app:storeMessage key="news.admin.text.datePosted"/>:</td>
            <td><%--<html:text property="newsEffDate" size="8"/> --%>
				<app:dojoInputDate id="newsEffDate"
                           name="UI_FRAME_FORM"
                           property="newsEffDate"
                           module="clw.NewXpedx"
                           targets="DatePosted"/>

				<img id="DatePosted" src="../externals/images/showCalendar.gif"
					width=19  height=19 border=0
					onmouseover="window.status='Choose Date';return true"
					onmouseout="window.status='';return true">

			</td>
          </tr>
          <tr>
            <td><app:storeMessage key="news.admin.text.endDate"/>:</td>
            <td><%--<html:text property="newsExpDate" size="8"/> --%>
				<app:dojoInputDate id="newsExpDate"
                           name="UI_FRAME_FORM"
                           property="newsExpDate"
                           module="clw.NewXpedx"
                           targets="DateExp"/>

				<img id="DateExp" src="../externals/images/showCalendar.gif"
					width=19  height=19 border=0
					onmouseover="window.status='Choose Date';return true"
					onmouseout="window.status='';return true">

			</td>
          </tr>
          <tr>
            <td><app:storeMessage key="news.admin.text.forcedReadQuestion"/>:</td>
            <td>
		<html:checkbox name="UI_FRAME_FORM" property="forcedR" styleId="forceR"/> <app:storeMessage key="news.admin.text.yes"/>
		&nbsp;&nbsp;&nbsp;
		<app:storeMessage key="news.admin.text.forcedEachLoginQuestion"/>:&nbsp;
		<html:checkbox name="UI_FRAME_FORM" property="forcedEachLogin" styleId="forceEach" onclick="toggleSelect('forceR', 'forceEach')"/> <app:storeMessage key="news.admin.text.yes"/>
	    </td>
          </tr>
          <tr>
            <td><app:storeMessage key="shop.userProfile.text.locale"/></td>
            <td>
            <div id="localeCDDiv" class="localeCDstyle">
            <html:select name="UI_FRAME_FORM" property="localeCd" >
                <html:option value='<%=""%>'>Default</html:option><%
for(java.util.Iterator iter=ClwI18nUtil.getAllLocales().iterator(); iter.hasNext();) {
  String localeCd = (String) iter.next();
%>    <html:option value="<%=localeCd%>"><%=localeCd%></html:option>
    <% } %>
    </html:select>
    </div>
            </td>
          </tr>
        </table>
      </td>
    </tr>

    <tr><td>&nbsp;</td></tr>
    <tr>
      <td align="left" colspan="2"><app:storeMessage key="news.admin.text.articleContents"/>:</td>
    </tr>
    <tr>
       <td  align="center" colspan="2" style="padding-left:5px"><html:textarea  cols='600' rows='1200' styleId="articleContents"  property="paragraph.noteText"/></td>
    </tr>
<%
String sOnClick = "Preview(\""+appUser.getUser().getUserName()+"\"); ";
%>
    <tr><td></td></tr>
    <tr>
     <td align=right colspan="2">
         <table border="0" cellpadding="0" cellspacing="0" >
            <tr>
              <app:xpedxButton label='global.action.label.cancel'
			url="#"
                        onClick='actionMultiSubmit("hiddenAction", "Cancel");'
		/>

                &nbsp;
                <app:xpedxButton label='news.admin.button.preview'
			url="#"
                        onClick='<%=sOnClick%>'
               />

               &nbsp;
                <app:xpedxButton label='template.xpedx.button.publish'
			url="#"
                        onClick='actionMultiSubmit("hiddenAction", "Save");'
		/>

            </tr>
          </table>
     </td>
    </tr>
     </html:form>
     </table>

	<script type="text/javascript">
			CKEDITOR.replace( 'articleContents',
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

</logic:equal>
</td></tr>

<tr><td>
<%--------------------------Business rule applicable Warnings----------------------------------%>
<table  width="100%"  cellpadding=0 cellspacing=0>
<tr>
  <% if(request.getAttribute("org.apache.struts.action.ERROR")!=null) { %>
		<td class="text" align="center" style="color:#FF0000; "><html:errors/></td>
  <% }else if(theForm.getConfirmMessage()!=null){ %>
		<td class="text" align="center" style="color:#003399; "><%=theForm.getConfirmMessage()%></td>
  <%}%>
</tr>
</table>
</td></tr>
<%---------------------------------------------------------------------------------------------%>

</table>
<%@include file="appleNewsPreviewAjax.jsp"%>

<%--</div>--%>

