<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.IOUtilities" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.StringUtils" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartData" %>
<%@ page import="java.util.Iterator" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<%
    NoteJoinViewVector notes = (NoteJoinViewVector) session.getAttribute("user.notes");
    String msbNoteStr = "";

    NoteAttachmentData data;
    int msbNoteId = 0;

    if (notes != null && !notes.isEmpty()) {

        Iterator it = ((NoteJoinView) notes.get(0)).getNoteText().iterator();
        Iterator imgIt = ((NoteJoinView) notes.get(0)).getNoteAttachment().iterator();
        msbNoteId = ((NoteJoinView) notes.get(0)).getNote().getNoteId();
        int i = 0;

        //msbNoteStr += "<b>" + ((NoteJoinView) notes.get(0)).getNote().getTitle() + "</b>";

        while (it.hasNext()) {
            msbNoteStr += "<br>";
            NoteTextData text = (NoteTextData) it.next();
            msbNoteStr += StringUtils.escapeHtml(text.getNoteText());
            msbNoteStr += "<br>";
            i++;
        }

        while (imgIt.hasNext()) {
            data = (NoteAttachmentData) imgIt.next();
            String path = ClwCustomizer.getTemplateImgRelativePath() + IOUtilities.convertToTempFile(data.getBinaryData(), data.getFileName());
            msbNoteStr += "<img src=\"" + path.replace((char) 92, '/') + "\"/>";
        }
    }
    boolean showChangingSitesWarning = false;
    ShoppingCartData shoppingCartD = ShopTool.getCurrentShoppingCart(session);
    if (shoppingCartD.getWorkOrderItem() != null && shoppingCartD.getWorkOrderItem().getWorkOrderItemId() > 0) {
        showChangingSitesWarning = true;
    }
%>

<style type="text/css">
    #interContainer {
        position: absolute;
        width: 670px; /*Width of interstitial box*/
        left: 0;
        top: 0;
        padding: 15px;
        padding-top: 0;
        background-color: #d0e47e;
        border: 1px solid black;
        visibility: hidden;
        z-index: 6;
    }

    #interContainer .headerbar {
    /*CSS for header bar of interstitial box*/
        color: gray;
        padding: 5px 0;
        text-align: right;
    }

    #interContainer .headerbar a {
    /*CSS for header bar links of interstitial box*/
        font-size: 120%;
        text-decoration: none;
    }

    #interContent {
    /*CSS for div that holds the content to show*/
        border: 1px solid gray;
        background-color: white;
        scrollbar-3dlight-color: white;
        scrollbar-arrow-color: gray;
        scrollbar-highlight-color: white;
        scrollbar-face-color: #d0e47e;
        scrollbar-shadow-color: #d0e47e;
        scrollbar-darkshadow-color: white;
        scrollbar-track-color: white;
    }

    #interVeil {
    /*CSS for background veil that covers entire page while interstitial box is visible*/
        position: absolute;
        background: black url( blackdot.gif );
        right: 0;
        width: 10px;
        top: 0;
        z-index: 5;
        visibility: hidden;
        filter: progid: DXImageTransform . Microsoft . alpha( opacity = 80 );
        opacity: 0.8;
    }
</style>
<script language="JavaScript1.2">
<!--

var focusControl;

function actionSubmit(formNum, action) {
    var actions;
    actions = document.forms[formNum]["action"];
    //alert(actions.length);
    for (ii = actions.length - 1; ii >= 0; ii--) {
        if (actions[ii].value == 'BBBBBBB') {
            actions[ii].value = action;
            document.forms[formNum].submit();
            break;
        }
    }
    return false;
}

function f_tcb() {
    var tsf = document.forms[0].searchField.value;
    if ("" == tsf) {
        document.forms[0].searchType[0].checked = false;
        document.forms[0].searchType[1].checked = true;
    } else {
        if (document.forms[0].searchType[0].checked == true) {
            document.forms[0].searchType[0].checked = true;
            document.forms[0].searchType[1].checked = false;
        } else {
            document.forms[0].searchType[0].checked = false;
            document.forms[0].searchType[1].checked = true;
        }
    }
}

var buttonClose = new Image()
var buttonNext = new Image()

var interstitialBox = {
    noteId:'<%=msbNoteId%>',
    displayfrequency: ["chance", "1"],
	<%
	String visibility = (notes==null||notes.size()<1)?"hidden":"visible";
	String imgName = (notes==null||notes.size()<1)? IMGPath + "/closeit.gif": IMGPath + "/next_note.jpg";
	%>

    defineheader: '<div id="controlButton" class="headerbar" style="visibility:<%=visibility%>"><a href="#" onClick="javascript:interstitialBox.ajaxconnect(this.interContainer);return false"><img src="<%=imgName%>" border="0" title="" name="buttonOne"/></a></div>',
	<%--
    //defineheader: '<div id="closeButton" class="headerbar" style="visibility:hidden;">                                        <a href="#" onClick="javascript:interstitialBox.ajaxconnect(this.interContainer);return false"><img src="<%=IMGPath + "/closeit.gif"%>" style="border: 0" title="Close Box"/></a></div>',
    //nextbutton:'<div id="nextButton" style="visibilty:<%=notes==null||notes.size()<=1?"hidden":"visible"%>" class="headerbar"><a href="#" onClick="javascript:interstitialBox.ajaxconnect(this.interContainer);return false"><img src="<%=IMGPath + "/next_note.jpg"%>" border="0"/></a></div>',
	--%>
    ajaxbustcache: true,
    disablescrollbars: true,
    autohidetimer: 0,
    ie7: window.XMLHttpRequest && document.all && !window.opera,
    ie7offline: this.ie7 && window.location.href.indexOf("http") == -1, //check for IE7 and offline
    launch:false,
    scrollbarwidth: 16,


    ajaxconnect:function(thediv) {
        var page_request = false;
        var bustcacheparameter = "";
        if (window.XMLHttpRequest && !this.ie7offline) // if Mozilla, IE7 online, Safari etc
            page_request = new XMLHttpRequest()
        else if (window.ActiveXObject) { // if IE6 or below, or IE7 offline (for testing purposes)
            try {
                page_request = new ActiveXObject("Msxml2.XMLHTTP")
            }
            catch (e) {
                try {
                    page_request = new ActiveXObject("Microsoft.XMLHTTP")
                }
                catch (e) {
                    actionSubmit(0, "read_note");
                }
            }
        }
        else {
            actionSubmit(0, "read_note");
        }
        page_request.onreadystatechange = function() {
            interstitialBox.loadpage(page_request, thediv)
        }

        page_request.open('POST', "msbsites.do", true)
        page_request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
        page_request.send("action=read_note&actionType=ajax&userNoteId=" + this.noteId)
    },

    loadpage:function(page_request, thediv) {
        if (page_request.readyState == 4 && (page_request.status == 200 || window.location.href.indexOf("http") == -1)) {
            var str = "";
            var ajaxAction = "";
            if (page_request.responseXML != null) {

                var root = page_request.responseXML.getElementsByTagName("Notes")[0];

                if (root != null && 'undefined' != typeof root) {

                    var ajaxActionObj = root.getElementsByTagName("AjaxAction")[0];
                    if (ajaxActionObj != null && 'undefined' != typeof ajaxActionObj) {
                        ajaxAction = ajaxActionObj.firstChild.nodeValue;
                    }
                    this.noteId = root.getAttribute("Id");

                    //str += "<b>" + root.getAttribute("Title") + "</b>";
                    var txt = root.getElementsByTagName("Text");
                    if (txt != null && 'undefined' != typeof txt) {
                        for (var i = 0; i < txt.length; i++) {
                            var valTxt = txt.item(i).firstChild.nodeValue;
                            str += "<br>";
                            str += valTxt;
                            str += "<br>";
                        }
                    }

                    var img = root.getElementsByTagName("Image");
                    if (img != null && 'undefined' != typeof img) {
                        for (var i = 0; i < img.length; i++) {
                            var valImg = img.item(i).firstChild.nodeValue;
                            str += "<img src=\"" + valImg + "\"/>";
                        }
                    }
                }

            }

            interstitialBox.viewMsbNote(thediv, str, ajaxAction);
            //interstitialBox.hidescrollbar();
            interstitialBox.getscrollbarwidth();
            interstitialBox.showcontainer();

        }
    },

    viewMsbNote:function(thediv, msbNote, action) {
		buttonClose.src = '<%=IMGPath + "/closeit.gif"%>';
		buttonNext.src = '<%=IMGPath + "/next_note.jpg"%>';

        if ("LastNote" == action) {
			document.getElementById("buttonOne").src = buttonClose.src;
            //document.getElementById("closeButton").style.visibility = 'visible';
            //document.getElementById("nextButton").style.visibility = 'hidden';
        } else if ("Close" == action || action == "") {
            interstitialBox.closeit();
        }

        document.getElementById("interContent").innerHTML = msbNote;
    }
    ,

    createcontainer:function() {
        //document.write('<div id="interContainer">' + this.defineheader + '<div id="interContent"></div>' + this.nextbutton + '</div><div id="interVeil"></div>')
        document.write('<div id="interContainer">' + this.defineheader + '<div id="interContent"></div></div><div id="interVeil"></div>')
        this.interContainer = document.getElementById("interContainer") //reference interstitial container
        this.interContent = document.getElementById("interContent") //reference interstitial content
        this.interVeil = document.getElementById("interVeil") //reference veil
        this.standardbody = (document.compatMode == "CSS1Compat") ? document.documentElement : document.body //create reference to common "body" across doctypes
    }
    ,


    showcontainer:function() {
        if (this.interContainer.style.display == "none") return //if interstitial box has already closed, just exit (window.onresize event triggers function)

        var ie = document.all && !window.opera
        var dom = document.getElementById

        var docwidth = (ie) ? this.standardbody.clientWidth : window.innerWidth - this.scrollbarwidth
        var docheight = (ie) ? this.standardbody.clientHeight : window.innerHeight

        this.interContent.style.height = Math.floor(parseInt(0.9 * docheight));
        this.interContent.style.overflow = 'auto';

        var scroll_top = (ie) ? this.standardbody.scrollTop : window.pageYOffset
        var scroll_left = (ie) ? this.standardbody.scrollLeft : window.pageXOffset
        var docheightcomplete = (this.standardbody.offsetHeight > this.standardbody.scrollHeight) ? this.standardbody.offsetHeight : this.standardbody.scrollHeight
        var objwidth = this.interContainer.offsetWidth
        var objheight = this.interContainer.offsetHeight
        this.interVeil.style.width = docwidth + "px" //set up veil over page
        this.interVeil.style.height = docheightcomplete + "px" //set up veil over page
        this.interVeil.style.left = 0 //Position veil over page
        this.interVeil.style.top = 0 //Position veil over page
        this.interVeil.style.visibility = "visible" //Show veil over page
        this.interContainer.style.left = docwidth / 2 - objwidth / 2 + "px" //Position interstitial box
        var topposition = (docheight > objheight) ? scroll_top + docheight / 2 - objheight / 2 + "px" : scroll_top + 5 + "px" //Position interstitial box
        this.interContainer.style.top = Math.floor(parseInt(topposition)) + "px"
        this.interContainer.style.visibility = "visible" //Show interstitial box
        if (this.autohidetimer && parseInt(this.autohidetimer) > 0 && typeof this.timervar == "undefined")
            this.timervar = setTimeout("interstitialBox.closeit()", this.autohidetimer * 1000)
    }
    ,


    closeit:function() {
        this.interVeil.style.display = "none"
        this.interContainer.style.display = "none"
        if (this.disablescrollbars && window.XMLHttpRequest) //if disablescrollbars enabled and modern browsers- IE7, Firefox, Safari, Opera 8+ etc
            this.standardbody.style.overflow = "auto"
        if (typeof this.timervar != "undefined") clearTimeout(this.timervar)
        interstitialBox.launch = false;
        focusControl.focus();
    }
    ,

    getscrollbarwidth:function() {
        var scrollbarwidth = window.innerWidth - (this.interVeil.offsetLeft + this.interVeil.offsetWidth) //http://www.howtocreate.co.uk/emails/BrynDyment.html
        this.scrollbarwidth = (typeof scrollbarwidth == "number") ? scrollbarwidth : this.scrollbarwidth
    }
    ,

    hidescrollbar:function() {
        if (this.disablescrollbars) { //if disablescrollbars enabled
            if (window.XMLHttpRequest) //if modern browsers- IE7, Firefox, Safari, Opera 8+ etc
                this.standardbody.style.overflow = "hidden"
            else //if IE6 and below, just scroll to top of page to ensure interstitial is in focus
                window.scrollTo(0, 0)
        }
    }
    ,

    dotask:function(target, functionref, tasktype) { //assign a function to execute to an event handler (ie: onunload)
        var tasktype = (window.addEventListener) ? tasktype : "on" + tasktype
        if (target.addEventListener)
            target.addEventListener(tasktype, functionref, false)
        else if (target.attachEvent)
            target.attachEvent(tasktype, functionref)
    }
    ,

    initialize:function(msbNote) {
        this.createcontainer() //write out interstitial container
        var action = "";
    <%
    if(notes==null || notes.size()==0){
    %>
        action = "Close";
    <%
    }else if(notes.size()==1){
    %>
        action = "LastNote";
    <%
    }else{
    %>
        action = "Next";
    <%
    }
    %>
        this.viewMsbNote(this.interContainer, msbNote, action)
        this.dotask(window, function() {
            //interstitialBox.hidescrollbar();
            interstitialBox.getscrollbarwidth();
            setTimeout("interstitialBox.showcontainer()", 100)
        }, "load")
        this.dotask(window, function() {
            interstitialBox.showcontainer()
        }, "resize")
    }
}


if (Math.floor(Math.random() * interstitialBox.displayfrequency[1]) == 0)
    interstitialBox.launch = true


<%
if(notes==null || notes.isEmpty() || !Utility.isSet(msbNoteStr)){
%>
interstitialBox.launch = false;
<%
}
%>


if (interstitialBox.launch) {
    interstitialBox.initialize('<%=msbNoteStr%>')
}
//-->
</script>

<div class="text"><font color=red>
    <html:errors/>
</font></div>

<bean:define id="toolBarTab" type="java.lang.String" value="sites" toScope="request"/>
<table align=center CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH%>">
    <%
        String f_msbToolbar = ClwCustomizer.getStoreFilePath(request, "f_msbToolbar.jsp");
    %>
    <jsp:include flush='true' page="<%=f_msbToolbar%>"/>
</table>

<%
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    StoreData userStore = appUser.getUserStore();
    String hpAction = request.getParameter("hp_action");
    boolean isaStartPage = false;

    if (hpAction != null && hpAction.equals("init") && appUser.isaCustomer()) {
        isaStartPage = true;
    }
%>

<table align=center CELLSPACING=0 CELLPADDING=5 width="<%=Constants.TABLEWIDTH%>" class="tbstd">
    <tr>
        <td>
            <%
            if (showChangingSitesWarning) {
            %>
            <table width="100%">
                <tr align="center">
                    <td>
                        <div class="text">
                            <font color="blue">
                                <b><app:storeMessage key="shop.messages.changingSitesWarning"/></b>
                            </font>
                        </div>
                        <br>
                    </td>
                </tr>
            </table>
            <%
            }
            %>
            <table>
                <html:form name="SITE_SEARCH_FORM" action="/userportal/msbsites.do"
                           scope="session" type="com.cleanwise.view.forms.SiteMgrSearchForm">

                <%
                    if (appUser != null && appUser.getSiteNumber() > 1) {
                %>
                
                <tr>
                    <td align="right"><b>
                        <app:storeMessage key="msbSites.text.findSite"/>
                    </b></td>
                    <td>
                        <html:text name="SITE_SEARCH_FORM" property="searchField"
                                   onchange="javascript: f_tcb();"
                                   onblur="javascript: f_tcb();"
                                   onfocus="javascript: f_tcb();"
                                />
                    </td>
                    <td nowrap="nowrap">
                        <html:radio name="SITE_SEARCH_FORM" property="searchType" value="nameBegins"/>
                        <app:storeMessage key="msbSites.text.nameStartsWith"/>
                        <html:radio name="SITE_SEARCH_FORM" property="searchType" value="nameContains"/>
                        <app:storeMessage key="msbSites.text.nameContains"/>
                    </td>
                </tr>
                <script type="text/javascript" language="JavaScript">
                    <!--
                    focusControl = document.forms["SITE_SEARCH_FORM"].elements["searchField"];
                    if (focusControl != undefined && focusControl.type != "hidden" && !focusControl.disabled && !interstitialBox.launch) {
                        focusControl.focus();
                    }
                    // -->
                </script>

                <tr>
                    <td align="right"><b>
                        <app:storeMessage key="msbSites.text.city"/>
                    </b></td>
                    <td>
                            <html:text name="SITE_SEARCH_FORM" property="city"/>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <%
                        if (userStore.isStateProvinceRequired()) {
                    %>
                    <td align="right"><b>
                        <app:storeMessage key="msbSites.text.state"/>
                    </b></td>
                    <td>
                            <html:text name="SITE_SEARCH_FORM" property="state"/>
                            <%
                            } else {
                            %>
                    <td colspan="2">&nbsp;</td>
                    <%
                        }
                    %>
                    <td>
                        <html:button property="action" styleClass="store_fb" onclick="actionSubmit(0,'only_site_search2');">
                            <app:storeMessage key="global.action.label.search"/>
                        </html:button>
                        <%
                            }
                            if (appUser != null && appUser.canEditShipTo()) {
                                if (appUser.getSiteNumber() < 1) { %>
                        <span style="text-align: left;">
                            <br> <app:storeMessage key="msbSites.text.createShipTo"/><br>
                        </span>
                        <%
                            }
                        %>
                        <html:button styleClass="store_fb" property="action"
                                     onclick="javascript: window.location='addshipto.do?action=addShipTo'">
                            <app:storeMessage key="msbSites.text.addShipTo"/>
                        </html:button>
                        <%
                            }
                            if (appUser != null && appUser.getSiteNumber() > 1 /* && !appUser.isaCustomer()*/) {
                        %>
                    </td>
                </tr>
            </table>
                <% } %>
                <html:hidden property="action" value="BBBBBBB"/>
                <html:hidden property="userNoteId" value="<%=String.valueOf(msbNoteId)%>"/>
                <html:hidden property="actionType" value="submit"/>
            </html:form>

</table>

<logic:present name="msb.site.vector">
    <%
        if (!isaStartPage) {
    %>
    <bean:size id="rescount" name="msb.site.vector"/>

    <table align=center CELLSPACING=0 CELLPADDING=5
           width="<%=Constants.TABLEWIDTH%>" class="tbstd"
           style="{border-bottom: black 1px solid; border-top: black 1px solid;}">
        <tr align=left>
            <td class="shopcharthead">
                <div class="fivemargin">
                    <a href="msbsites.do?action=sort_sites&sortField=name">
                        <app:storeMessage key="msbSites.text.siteName"/>
                    </a></div>
            </td>
            <td class="shopcharthead">
                <div class="fivemargin">
                    <a href="msbsites.do?action=sort_sites&sortField=address">
                        <app:storeMessage key="msbSites.text.streetAddress"/>
                    </a></div>
            </td>
            <td class="shopcharthead">
                <div class="fivemargin">
                    <a href="msbsites.do?action=sort_sites&sortField=city">
                        <app:storeMessage key="msbSites.text.city"/>
                    </a></div>
            </td>
            <%
                if (userStore.isStateProvinceRequired()) {
            %>
            <td class="shopcharthead">
                <div class="fivemargin">
                    <a href="msbsites.do?action=sort_sites&sortField=state">
                        <app:storeMessage key="msbSites.text.stateProvince"/>
                    </a></div>
            </td>
            <%
                }
            %>
        </tr>

        <logic:iterate id="arrele" name="msb.site.vector" indexId="i">
            <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
                <td>
                    <bean:define id="eleid" name="arrele" property="id"/>
                    <a href="msbsites.do?action=shop_site&siteId=<%=eleid%>">
                        <bean:write name="arrele" property="name"/>
                    </a>
                </td>
                <td>
                    <bean:write name="arrele" property="address"/>
                </td>
                <td>
                    <bean:write name="arrele" property="city"/>
                </td>
                <%
                    if (userStore.isStateProvinceRequired()) {
                %>
                <td>
                    <bean:write name="arrele" property="state"/>
                </td>
                <%
                    }
                %>
            </tr>
        </logic:iterate>

    </table>
    <%
        }
    %>
</logic:present>
</td>
</tr>
</table>

<logic:notPresent name="msb.site.vector">
    <jsp:include flush='true'
                 page='<%=ClwCustomizer.getStoreFilePath(request,"f_customer_messages.jsp")%>'/>
</logic:notPresent>

<logic:present name="msb.site.vector">
    <%
        if (isaStartPage) {
    %>
    <jsp:include flush='true'
                 page='<%=ClwCustomizer.getStoreFilePath(request,"f_customer_messages.jsp")%>'/>
    <%
        }
    %>
</logic:present>

<jsp:include flush='true'
             page='<%=ClwCustomizer.getStoreFilePath(request,"f_table_bottom1.jsp")%>'/>

<script type="text/javascript" language="JavaScript">

    function kH(e) {
        var keyCode = window.event.keyCode;
        if (keyCode == 13 && !interstitialBox.launch) {
            actionSubmit(0, 'Search');
        }
    }

    document.onkeypress = kH;

</script>
