<%@page import="com.cleanwise.service.api.value.*"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@page import="com.cleanwise.service.api.util.Utility" %>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%
NoteJoinViewVector notes = (NoteJoinViewVector) session.getAttribute("user.notes");
String labelNext = Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "global.action.label.next")).toUpperCase();
String labelPrev = Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "global.action.label.previous")).toUpperCase();
String labelClose = Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "global.action.label.close")).toUpperCase();
String labelPrint = Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "global.action.label.print")).toUpperCase();
String labelPublish = Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "global.label.publish")).toUpperCase();

%>


<div id="newsContainer">
<table id="newsContainerTable"  height="100%" width="100%" cellpadding="0"
    cellspacing="10" border="0">
    <tr><td id="newsHeader" height="1%">&nbsp;</td><td valign="top"
        align="right" width="1%"><a href="#" onclick="appleNewsPreviewAjax.closeit();return false;"><img src="<%=ClwCustomizer.getSIP(request,"notesClose.gif")%>"
        width="18" height="21" border="0"/></a></td></tr>
    <tr><td height="98%" colspan="2">
<div id="newsContent"><table><tr><td id="newsContentInfo">&nbsp;</td></tr></table></div>
    </td></tr>
    <tr><td height="1%" colspan="2">
<table id="newsFooter" width="100%" height="1%" cellpadding="5" cellspacing="0" border="0">
<tr>
<td width="99%" align="left"></td>
<td valign="middle"><div id="newsBtnPrev" style="width:110px;text-align:center;">
<table cellpadding="0" cellspacing="0">
<tr><td><a href="#" onclick="appleNewsPreviewAjax.previous();return false;"><img src="<%=ClwCustomizer.getSIP(request, "notesPrev.gif")%>"
    border="0" width="13" height="22"/></a></td>
    <td><nobr>&nbsp;<a href="#" onclick="appleNewsPreviewAjax.previous();return false;"><%=labelPrev%></a></nobr></td>
</tr></table></div></td>
<td width="1%" valign="top"><div id="newsBtnNext" style="width:110px;text-align:center;">
<table cellpadding="0" cellspacing="0">
<tr><td><nobr><a href="#" onclick="appleNewsPreviewAjax.next();return false;"><%=labelNext%>&nbsp;</a></nobr></td>
    <td><a href="#" onclick="appleNewsPreviewAjax.next();return false;"><img src="<%=ClwCustomizer.getSIP(request, "notesNext.gif")%>" border="0" width="13"
    height="22"/></a></td></tr>
</table></div><div id="newsBtnClose"
    style="width:110px;text-align:center;"><a href="#" onclick="appleNewsPreviewAjax.closeit();return false;"><%=labelClose%></a></div></td>
<td width="1%" align="right"><div style="width:100px;text-align:center;"><a href="#"
    onclick='appleNewsPreviewAjax.publish("hiddenAction", "Save");return false;'><%=labelPublish%></a></div></td></tr></table>
</td></tr>
</table>
</div>
<div id="newsBackground" onclick="appleNewsPreviewAjax.closeit();"></div>
<script src="<%=request.getContextPath()%>/externals/ajaxutil.js" language="JavaScript"></script>
<script>
//<!--
var appleNewsPreviewAjax = {
    isActive: false,
    noteId: -1,
    hasNext: <%=notes.size() > 0%>,
    hasPrev: false,
    showActiveNotes: false,
    disablescrollbars: true,
    scrollbarwidth: 16,
    viewMsbNote:function(title, user, date, content) {
        try {
            this.newsHeader.innerHTML = title;
            this.newsContentInfo.innerHTML = "DATE POSTED: " + date + "<br><br>" +
                    "Posted by: " + user + "<br><br>" + content;
            this.showcontainer();
        } catch (err) {}
    },
    createcontainer:function() {
        this.newsContainer = document.getElementById("newsContainer"); //reference interstitial container
        this.newsContent = document.getElementById("newsContent"); //reference interstitial content
        this.newsBackground = document.getElementById("newsBackground"); //reference veil
        this.standardbody = (document.compatMode == "CSS1Compat") ? document.documentElement : document.body; //create reference to common "body" across doctypes
        this.newsHeader = document.getElementById("newsHeader");
        this.newsContentInfo = document.getElementById("newsContentInfo");
    },
    showcontainer:function() {
        if (this.isActive == false) return;
        var ie = document.all && !window.opera;
        var dom = document.getElementById;
        var docwidth = (ie) ? this.standardbody.clientWidth : window.innerWidth - this.scrollbarwidth;
        var docheight = (ie) ? this.standardbody.clientHeight : window.innerHeight;
        var h = Math.max(400, Math.floor(parseInt(0.95 * docheight)));
        this.newsContainer.style.height = h + "px";
        var dH = this.newsHeader.clientHeight;
        this.newsContent.style.height = (h - this.newsHeader.clientHeight - 80) + "px";
        var scroll_top = (ie) ? this.standardbody.scrollTop : window.pageYOffset;
        var scroll_left = (ie) ? this.standardbody.scrollLeft : window.pageXOffset;
        var docheightcomplete = (this.standardbody.offsetHeight > this.standardbody.scrollHeight) ? this.standardbody.offsetHeight : this.standardbody.scrollHeight;
        var objwidth = this.newsContainer.offsetWidth;
        var objheight = this.newsContainer.offsetHeight;
        this.newsContainer.style.left = Math.max(0, (docwidth - objwidth) / 2) + "px"; //Position interstitial box
        this.newsContainer.style.top = scroll_top + Math.max(0, (docheight - objheight) / 2) + "px";
        this.newsBackground.style.width = docwidth + "px";
        this.newsBackground.style.height = docheightcomplete + "px";
        this.newsBackground.style.left = 0;
        this.newsBackground.style.top = 0;
        this.show();
    },
    show:function() {
        this.newsContainer.style.visibility = "visible";
        this.newsBackground.style.visibility = "visible";
    },
    closeit:function() {
        this.isActive = false;
        this.newsContainer.style.visibility = "hidden";
        this.newsBackground.style.visibility = "hidden";
        if (this.disablescrollbars && window.XMLHttpRequest) {
            this.standardbody.style.overflow = "auto";
        }
        if (typeof this.timervar != "undefined") clearTimeout(this.timervar);
        this.setVisProp(document.getElementById("newsBtnPrev"),  'none', 'hidden');
        this.setVisProp(document.getElementById("newsBtnNext"),  'none', 'hidden');
        this.setVisProp(document.getElementById("newsBtnClose"), 'none', 'hidden');
        showHideLocaleCD();
    },
    dotask:function(target, functionref, tasktype) { //assign a function to execute to an event handler (ie: onunload)
        var tasktype = (window.addEventListener) ? tasktype : "on" + tasktype;
        if (target.addEventListener) {
            target.addEventListener(tasktype, functionref, false);
        } else if (target.attachEvent) {
            target.attachEvent(tasktype, functionref);
        }
    },
    refreshButtons: function() {
        try{
            if (this.showActiveNotes) {
                this.setVisProp(document.getElementById("newsBtnPrev"), 'block', 'hidden');
                this.setVisProp(document.getElementById("newsBtnNext"), (this.hasNext == true) ? 'block' : 'none', 'visible');
                this.setVisProp(document.getElementById("newsBtnClose"),(this.hasNext != true) ? 'block' : 'none', 'visible');
            } else {
                this.setVisProp(document.getElementById("newsBtnClose"), 'none', 'hidden');
                this.setVisProp(document.getElementById("newsBtnPrev"), 'block', (this.hasPrev == true) ? 'visible' : 'hidden');
                this.setVisProp(document.getElementById("newsBtnNext"), 'block', (this.hasNext == true) ? 'visible' : 'hidden');
            }
        } catch (err){}
    },

    setVisProp: function(obj, display, visibility) {
        if (obj != null && obj.style != null) {
            obj.style.display = display;
            obj.style.visibility = visibility;
        }
    },

    preview: function(sTitle, sUser, sDate, sNoteText) {

      this.isActive = true;
        this.showActiveNotes = false;
        this.hasNext = false;
        this.hasPrev = false;
        this.createcontainer();
        this.dotask(window, function() {
            appleNewsPreviewAjax.showcontainer();
        }, "resize");
        this.dotask(window, function() {
            appleNewsPreviewAjax.showcontainer();
        }, "load");
        this.dotask(window, function() {
            appleNewsPreviewAjax.showcontainer();
        }, "scroll");
        this.refreshButtons();
        this.viewMsbNote(sTitle, sUser, sDate, sNoteText);
    },
    publish: function(actionDef, action) {
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
     }

}

function showcontainer() {
    appleNewsPreviewAjax.showcontainer();
}
<%if (notes.size() > 0) {%>
appleNewsPreviewAjax.initialize(true);
<%}%>
//-->
</script>
