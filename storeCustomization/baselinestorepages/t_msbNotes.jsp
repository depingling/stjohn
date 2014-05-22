<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.cleanwise.service.api.value.NoteJoinView" %>
<%--
   Date: 04.12.2007
  Time: 10:47:46
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script type="text/javascript" language="JavaScript1.2">
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

    function actionSubmit(formNum, action) {
        var actions;
        actions = document.forms[formNum]["action"];
        for (var ii = actions.length - 1; ii >= 0; ii--) {
            if (actions[ii].value == 'hiddenAction') {
                actions[ii].value = action;
                document.forms[formNum].submit();
                break;
            }
        }
        return false;
    }

    function setAndSubmit(name,actionVal) {
        var dml=document.forms['USER_NOTE_MGR_FORM'];
        dml[name].checked=1;
        var iiLast = dml['action'].length-1;
        dml['action'][iiLast].value=actionVal;
        dml.submit();
    }
</script>

<table align=center CELLSPACING=0 CELLPADDING=5 width="100%">
<tr>
<td>
<html:form name="USER_NOTE_MGR_FORM"
           action="/userportal/msbnotes.do"
           scope="session"
           type="com.cleanwise.view.forms.UserNoteMgrForm">

<table  width="100%">

<tr>
    <td width="10%"><b>
        <app:storeMessage key="msbNotes.text.findNote"/>
    </b>
    </td>
    <td width="30%">
        <html:text name="USER_NOTE_MGR_FORM" property="searchField"
                   onchange="javascript: f_tcb();"
                   onblur="javascript: f_tcb();"
                   onfocus="javascript: f_tcb();"/>
    </td>
    <td nowrap="nowrap" width="60%">
        <html:radio name="USER_NOTE_MGR_FORM" property="searchType"
                    value="<%=RefCodeNames.SEARCH_TYPE_CD.BEGINS%>"/>
        <app:storeMessage key="msbNotes.text.nameStartsWith"/>
        <html:radio name="USER_NOTE_MGR_FORM" property="searchType"
                    value="<%=RefCodeNames.SEARCH_TYPE_CD.CONTAINS%>"/>
        <app:storeMessage key="msbNotes.text.nameContains"/>
    </td>
</tr>

<script type="text/javascript" language="JavaScript">
    <!--
    var focusControl = document.forms["USER_NOTE_MGR_FORM"].elements["searchField"];
    if (focusControl != undefined && focusControl.type != "hidden" && !focusControl.disabled) {
        focusControl.focus();
    }
    // -->
</script>

<tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>
        <html:button property="action" styleClass="store_fb" onclick="actionSubmit(0,'Search');">
            <app:storeMessage key="global.action.label.search"/>
        </html:button>
    </td>
</tr>

<tr style="visibility:hidden;">
    <td>
        <b><app:storeMessage key="msbNotes.text.effDate"/></b>
    </td>
    <td>
        <html:text name="USER_NOTE_MGR_FORM" property="effDate"/>
    </td>
    <td>&nbsp;</td>
</tr>

<tr style="visibility:hidden;">
    <td>
        <b><app:storeMessage key="msbNotes.text.expDate"/></b>
    </td>
    <td>
        <html:text name="USER_NOTE_MGR_FORM" property="expDate"/>
    </td>
    <td>&nbsp;</td>
</tr>

<logic:present name="USER_NOTE_MGR_FORM" property="searchResult.values">
<tr>
    <td colspan="3">
        <table width="100%">
            <tr>
                <td class="shopcharthead">
                    <div class="fivemargin">
                        <app:storeMessage key="msbNotes.text.title"/>
                    </div>
                </td>

                <td class="shopcharthead">
                    <div class="fivemargin">
                        <app:storeMessage key="msbNotes.text.effDate"/>
                    </div>
                </td>

                <td class="shopcharthead">
                    <div class="fivemargin">
                        <app:storeMessage key="msbNotes.text.expDate"/>
                    </div>
                </td>

                <td class="shopcharthead">
                    <div class="fivemargin">
                        <app:storeMessage key="msbNotes.text.addDate"/>
                    </div>
                </td>
                
                <td class="shopcharthead">
                    <div class="fivemargin"/>
                </td>
            </tr>
            <logic:present name="USER_NOTE_MGR_FORM" property="searchResult.values">
            <logic:iterate id="note"
                           name="USER_NOTE_MGR_FORM"
                           property="searchResult.values"
                           indexId="i"
                           type="com.cleanwise.service.api.value.NoteJoinView">
                <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
                    <%
                        String selectedStr = "searchResult.selected[" + i + "]";
                        String actionVal = ClwI18nUtil.getMessageOrNull(request,"msbNotes.text.read");
                        if(actionVal==null){
                            actionVal="Read";
                        }
                    %>
                    <td>
                        <logic:present name="note" property="note.title">
                            <a href="msbnotes.do?action=<%=actionVal%>&noteId=<%=((NoteJoinView)note).getNote().getNoteId()%>">  <bean:write name="note" property="note.title"/></a>
                        </logic:present>
                    </td>

                    <td>
                        <logic:present name="note" property="note.effDate">
                            <bean:define id="effDate"  name="note" property="note.effDate" type="java.util.Date"/>
                            <%=ClwI18nUtil.formatDate(request, effDate, DateFormat.DEFAULT)%>
                        </logic:present>
                    </td>
                    <td>
                        <logic:present name="note" property="note.expDate">
                            <bean:define id="expDate"  name="note" property="note.expDate" type="java.util.Date"/>
                            <%=ClwI18nUtil.formatDate(request, expDate, DateFormat.DEFAULT)%>
                        </logic:present>
                    </td>

                    <td>
                        <logic:present name="note" property="note.addDate">
                            <bean:define id="addDate"  name="note" property="note.addDate" type="java.util.Date"/>
                            <%=ClwI18nUtil.formatDate(request, addDate, DateFormat.DEFAULT)%>
                        </logic:present>
                    </td>
                    <td>
                        <html:multibox name="USER_NOTE_MGR_FORM" property="<%=selectedStr%>" value="true"/>
                    </td>
                </tr>

            </logic:iterate>
            </logic:present>
            <tr>
                <td colspan="7" align="center">
                    <html:submit property="action" styleClass="store_fb">
                        <app:storeMessage key="msbNotes.text.read"/>
                    </html:submit></td>
                <td>&nbsp;</td>
            </tr>
        </table>
    </td>
</tr>          </logic:present>
</table>


    <html:hidden property="action" value="hiddenAction"/>
    <html:hidden property="requestType" value="submit"/>
</html:form>
</table>
