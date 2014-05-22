<%@ page language="java"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="java.util.*"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@include file="/externals/calendar/calendar.jsp"%>

<%@page import="com.cleanwise.service.api.util.I18nUtil"%><bean:define id="theForm" name="STORE_ADMIN_MSG_FORM"
	type="com.cleanwise.view.forms.StoreMsgMgrForm" />
<table>
	<tr>
		<td><html:form name="STORE_ADMIN_MSG_FORM"
			action="storeportal/msgsearch.do">
			<table width="<%=Constants.TABLEWIDTH%>" class="mainbody">
				<tr>
					<td align="right"><b><app:storeMessage key="storemessages.text.messageTitle" /></b></td>
					<td ><html:text property="searchField" /> <html:radio
						property="searchType" value="<%=Constants.ID%>" /><app:storeMessage key="storemessages.text.id" /><html:radio
						property="searchType" value="<%=Constants.NAME_BEGINS%>" /><app:storeMessage key="storemessages.text.nameStartsWith" /><html:radio property="searchType"
						value="<%=Constants.NAME_CONTAINS%>" /><app:storeMessage key="storemessages.text.nameContains" /></td>
				</tr>
				<tr>
					<td align="right"><b><app:storeMessage key="storemessages.text.datePostedFrom" />:</b></td>
					<td><nobr><html:text styleId="searchPostedDateFrom"
						name="STORE_ADMIN_MSG_FORM" property="searchPostedDateFrom"
						maxlength="10" /><a href="#"
						onClick="showCalendar('searchPostedDateFrom', event);return false;"
						title="<%=ClwI18nUtil.getMessage(request, "storemessages.text.chooseDate", null)%>"><img name="DATEFROM"
						src="../externals/images/showCalendar.gif" width=19 height=19
						border=0 align="absmiddle" style="position: relative"
						onmouseover="window.status='<%=ClwI18nUtil.getMessage(request, "storemessages.text.chooseDate", null)%>';return true"
						onmouseout="window.status='';return true"></a>&nbsp;&nbsp;&nbsp;
					<b><app:storeMessage key="storemessages.text.to" />: </b><html:text styleId="searchPostedDateTo"
						name="STORE_ADMIN_MSG_FORM" property="searchPostedDateTo"
						maxlength="10" /><a href="#"
						onClick="showCalendar('searchPostedDateTo', event);return false;"
						title="<%=ClwI18nUtil.getMessage(request, "storemessages.text.chooseDate", null)%>"><img name="DATEFROM"
						src="../externals/images/showCalendar.gif" width=19 height=19
						border=0 align="absmiddle" style="position: relative"
						onmouseover="window.status='<%=ClwI18nUtil.getMessage(request, "storemessages.text.chooseDate", null)%>';return true"
						onmouseout="window.status='';return true"></a></nobr></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td colspan="3"><html:submit property="action">
						<app:storeMessage key="global.action.label.search" />
					</html:submit> <html:submit property="action">
						<app:storeMessage key="global.label.createNew" />
					</html:submit>&nbsp;&nbsp;&nbsp;
					<app:storeMessage key="storemessages.text.showInactive"/> <html:checkbox name="STORE_ADMIN_MSG_FORM" property="showInactive"/>

                    </td>
				</tr>
			</table>
		</html:form> <logic:present name="STORE_ADMIN_MSG_FORM" property="messages">
			<bean:define id="messages" name="STORE_ADMIN_MSG_FORM"
				property="messages"
				type="com.cleanwise.service.api.value.StoreMessageDataVector" />		
		<app:storeMessage key="storemessages.text.searchResultCount" />: <%=messages.size()%>
			<logic:notEmpty name="STORE_ADMIN_MSG_FORM" property="messages">
				<table width="<%=Constants.TABLEWIDTH%>" class="stpTable_sortable"
					id="ts1">
					<thead>
						<tr>
							<th class="stpTH"><app:storeMessage key="storemessages.text.messageId" /></th>
							<th class="stpTH"><app:storeMessage key="storemessages.text.messageTitle" /></th>
							<th class="stpTH"><app:storeMessage key="storemessages.text.datePosted" /></th>
							<th class="stpTH"><app:storeMessage key="storemessages.text.forced" /></th>
							<th class="stpTH"><app:storeMessage key="storemessages.text.lastModBy" /></th>
							<th class="stpTH"><app:storeMessage key="storemessages.text.language" /></th>
							<th class="stpTH"><app:storeMessage key="storemessages.text.country" /></th>
							<th class="stpTH"><app:storeMessage key="storemessages.text.status" /></th>
						</tr>
					</thead>
					<tbody><% if (messages.size() == 1000) {%>(<app:storeMessage key="storemessages.text.requestLimit" />request limit)<%}%>
						<logic:iterate id="message" name="STORE_ADMIN_MSG_FORM"
							property="messages">
							<tr>
								<td class="stpTD"><bean:write name="message"
									property="storeMessageId" />&nbsp;</td>
								<td class="stpTD"><a
									href="msgdetail.do?id=<bean:write name="message" property="storeMessageId" />"><bean:write
									name="message" property="messageTitle" /></a>&nbsp;</td>
								<td class="stpTD"><bean:write name="message"
									property="postedDate" />&nbsp;</td>
								<td class="stpTD"><bean:write name="message"
									property="forcedRead" />&nbsp;</td>
								<td class="stpTD"><bean:write name="message"
									property="modBy" />&nbsp;</td>
								<td class="stpTD"><bean:write name="message"
									property="languageCd" />&nbsp;</td>
								<td class="stpTD"><bean:write name="message"
									property="country" />&nbsp;</td>
								<td class="stpTD"><bean:write name="message"
									property="storeMessageStatusCd" />&nbsp;</td>
							</tr>
						</logic:iterate>
					</tbody>
				</table>
			</logic:notEmpty>
		</logic:present></td>
	</tr>
</table>
<script type="text/javascript" language="JavaScript">
  <!--
  var focusControl = document.forms[0]['searchField'];
  if('undefined' != typeof focusControl) {
     focusControl.focus();
  }
  
function kH(e) {
  var keyCode = window.event.keyCode;
  if(keyCode==13) {
    var actionButton = document.forms[0]['action'];
    if('undefined' != typeof actionButton) {
      var len = actionButton.length;
      for(ii=0; ii<len; ii++) {
        if('Search' == actionButton[ii].value) {
          actionButton[ii].select();
          actionButton[ii].click();
          break;
        }      
      }
    }
  }
}
document.onkeypress = kH;
<%Locale userLocale = ClwI18nUtil.getUserLocale(request);%>
CalendarMetaInfo.init('<%=userLocale.toString()%>', '<%=I18nUtil.getDatePattern(userLocale)%>');
  // -->
</script>