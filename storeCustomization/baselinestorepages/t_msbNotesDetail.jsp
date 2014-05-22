<%@ page import="com.cleanwise.view.forms.UserNoteMgrForm" %>
<%@ page import="com.cleanwise.service.api.value.NoteJoinView" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.service.api.value.NoteTextData" %>
<%@ page import="com.cleanwise.service.api.value.NoteAttachmentData" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.IOUtilities" %>
<%@ page import="com.cleanwise.view.utils.StringUtils" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%--
  Date: 04.12.2007
  Time: 13:35:52
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<script type="text/javascript" src="../externals/ajaxutil.js" language="JavaScript1.2"></script>


<script type="text/javascript" language="JavaScript1.2">
    var isNext;
    var isPrev;

    function responseXmlHandler(xml, thediv) {

        if (xml != null) {

            var root = xml.getElementsByTagName("Note")[0];
            if (root != null && 'undefined' != typeof root) {
                var display = "<table  width=\"100%\">";
                var id = root.getAttribute("Id");
                isNext = root.getAttribute("isnext");
                isPrev = root.getAttribute("isprev");

                var titleObj=root.getElementsByTagName("Title")[0];
                if (titleObj != null && 'undefined' != typeof titleObj){
                    var title = titleObj.firstChild.nodeValue;
                    var titletxt="";
                    titletxt+= "<tr><td class=\"customerltbkground\">"+
                               "<div align=\"center\" class=\"itemheadmargin\">"+
                               "<span  class=\"usernotetitletxt\">";
                    titletxt+=title;
                    titletxt+= "</span></div></td></tr>";
                    display +=titletxt;
                }

                var notetext = "";
                var textObj = root.getElementsByTagName("Text");
                if (textObj != null && 'undefined' != typeof textObj) {
                    for (var i = 0; i < textObj.length; i++) {
                        var txt = textObj.item(i).firstChild.nodeValue;
                        if (txt != null && txt != "null") {
                            notetext += "<tr><td>";
                            notetext += txt;
                            notetext += "</td></tr>";
                            notetext += "<tr><td>&nbsp;</td></tr>";
                        }
                    }
                }
                display +=notetext;
                var imgtxt = "";
                var img = root.getElementsByTagName("Image");
                if (img != null && 'undefined' != typeof img) {
                    for (var i = 0; i < img.length; i++) {
                        var valImg = img.item(i).firstChild.nodeValue;
                        if (valImg != null && valImg != "null") {
                            imgtxt += "<tr><td>";
                            imgtxt += "<img src=\"" + valImg + "\"/>";
                            imgtxt += "</td></tr>";
                        }
                    }
                }
                display += imgtxt;

                display += "</table>";
                writeNoteTxt(thediv, display);
            }
        }
    }

    function writeNoteTxt(thediv, display) {

        var divEl = document.getElementById(thediv);

        if (isNext=="true"){         
            document.getElementById("next").style.visibility = "visible";
        } else {
            document.getElementById("next").style.visibility = "hidden";
        }

        if(isPrev=="true"){
            document.getElementById("previous").style.visibility = "visible";
        } else {
            document.getElementById("previous").style.visibility = "hidden";
        }

        divEl.innerHTML = display;
    }
</script>

<style type="text/css">

    .nextnote {
        visibility: visible;
    }

    .previousnote {
        visibility: visible;
    }

    .usernotetitletxt {
        text-align: center;
        font-weight: bold;
        font-size: x-large;
        color: #000000;
    }

</style>

<%
    boolean isNext = true;
    boolean isPrev = true;

%>
<bean:define id="theForm" name="USER_NOTE_MGR_FORM" type="com.cleanwise.view.forms.UserNoteMgrForm"/>
<%
    UserNoteMgrForm form = (UserNoteMgrForm) theForm;
    NoteJoinView noteForRead = form.getNoteForRead();
    String display = "";

    if(noteForRead!=null) {
        display = "<table  width=\"100%\">";
        String title="<tr><td class=\"customerltbkground\">"+
                "<div align=\"center\" class=\"itemheadmargin\">"+
                "<span  class=\"usernotetitletxt\">";
        title+= noteForRead.getNote().getTitle();
        title+="</span></div></td></tr>";
        display += title;

        String txt = "";
        if (noteForRead.getNoteText() != null && !noteForRead.getNoteText().isEmpty()) {
            Iterator it = noteForRead.getNoteText().iterator();
            while (it.hasNext()) {
                txt += "<tr><td>";
                txt += StringUtils.escapeHtml(((NoteTextData) it.next()).getNoteText());
                txt += "</td></tr>";
                txt += "<tr><td>&nbsp;</td></tr>";
            }
        }
        display += txt;

        String imgtxt = "";
        if (noteForRead.getNoteAttachment() != null && !noteForRead.getNoteAttachment().isEmpty()) {
            Iterator it = noteForRead.getNoteAttachment().iterator();
            while (it.hasNext()) {
                NoteAttachmentData data = (NoteAttachmentData) it.next();
                String path = ClwCustomizer.getTemplateImgRelativePath();
                String tempFile = IOUtilities.convertToTempFile(data.getBinaryData(), data.getFileName());
                if (Utility.isSet(path) && Utility.isSet(tempFile)){
                    imgtxt += "<tr><td>";
                    path+=tempFile;
                    imgtxt += "<img src=\"" + path.replace((char) 92, '/') + "\"/>";
                    imgtxt += "</td></tr>"; }
            }
        }
        display += imgtxt;
        display += "</table>";

        if((form.getReadedNote() == null) || (form.getReadedNote().isEmpty())){
            isPrev = false;
        }

        if((form.getAllNoteForRead() == null) || (form.getAllNoteForRead().size()==1)){
            isNext = false;
        }
    }
%>


<table align="center" CELLSPACING="0" CELLPADDING="5" width="100%">
    <tr>
        <td>
            <html:form name="USER_NOTE_MGR_FORM"
                       action="/userportal/msbnotes.do"
                       scope="session"
                       type="com.cleanwise.view.forms.UserNoteMgrForm">
                <table width="100%">
                    <tr>
                        <td colspan="2">
                            <div id="notebody">
                                <%=display%>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" width="50%">
                            <div id="previous" class="previousnote" style="visibility:<%=isPrev?"visible":"hidden"%>">
                                <html:button styleClass="store_fb" property="action"
                                             onclick="ajaxconnect('msbnotes.do','action=prevnote&requestType=async','notebody',responseXmlHandler);">
                                    <app:storeMessage key="global.action.label.previous"/>
                                </html:button>
                            </div>    
                        </td>
                        <td align="left" width="50%">
                            <div id="next" class="nextnote" style="visibility:<%=isNext?"visible":"hidden"%>">
                                <html:button styleClass="store_fb" property="action" 
                                             onclick="ajaxconnect('msbnotes.do','action=nextnote&requestType=async','notebody',responseXmlHandler);">
                                    <app:storeMessage key="global.action.label.next"/>
                                </html:button>
                            </div>
                        </td>
                    </tr>
                </table>
            </html:form>
        </td>
    </tr>
</table>
