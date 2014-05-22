<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>

<script src="../externals/ajaxutil.js" language="JavaScript"></script>
<script language="JavaScript1.2">
    if(typeof dojo == "undefined"){
        var djConfig = {parseOnLoad: true,isDebug: false, usePlainJson: true}
        document.write("<script type=\"text/javascript\" src=\"<%=request.getContextPath()%>/externals/dojo_1.1.0/dojo/dojo.js\"><"+"/script>")
    }

    document.write("<script type=\"text/javascript\" src=\"<%=request.getContextPath()%>/externals/lib.js\"><"+"/script>")

</script>

<script language="JavaScript1.2">

    dojo.require("clw.admin2.form.AutoSuggestTextBox");
    dojo.require("dojo.data.ItemFileReadStore");

  var d = dojo;
  var theme;
  applyEvent = false;

  if (!theme) {
      theme = 'JD';
  }

  var themeCss = d.moduleUrl("clw.JD.themes", theme + "/" + theme + ".css");
  document.write('<link rel="stylesheet" type="text/css" href="' + themeCss + '"/>');

  d.addOnLoad(function() {
      if (!d.hasClass(d.body(), theme)) {
          d.addClass(d.body(), theme);
      }
  });
  </script>

<bean:define id="theForm" name="UI_HOME_MGR_FORM"  type="com.cleanwise.view.forms.UiHomeMgrForm"/>

<html:form styleId="UI_HOME_MGR_FORM_ID" name="UI_HOME_MGR_FORM" action="uimanager/uiHome.do" scope="session" type="com.cleanwise.view.forms.UiHomeMgrForm">
<table cellpadding="0" cellspacing="0">
<tr> <td>&nbsp;</td>
    <td><b>Store/Account Name:</b>&nbsp;&nbsp;</td>
    <td>
        <app:autoSuggestTextBox id="AutoSuggestAssocName"
                                name="UI_HOME_MGR_FORM"
                                formId="UI_HOME_MGR_FORM_ID"
                                property="assocName"
                                module="clw.admin2"
                                action="autosuggestAssocName"
                                searchAttr="name"
                                onEnterKeyPress="adm2Submit('UI_HOME_MGR_FORM_ID','UI_HOME_MGR_FORM_ID_HIDDEN_ACTION','Search')" />
    </td>
    <td>
       &nbsp;&nbsp;<html:submit property="action">Search</html:submit><html:button property="action">View All</html:button>
    </td>
</tr>

</table>
    <html:hidden  property="action"  value="" styleId="UI_HOME_MGR_FORM_ID_HIDDEN_ACTION"/>
</html:form>

<table cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH800%>">

    <tr><td colspan="3">&nbsp;</td></tr>
    <tr><td colspan="3"><b>Managed Groups:</b></td></tr>

    <tr>
        <td  class="aton" align=center width="20%"><b>Group Name</b></td>
        <td  class="aton" align=center width="15%"><b>Group Type</b></td>
        <td class="aton" align=center><b>Group Associations</b></td>
    </tr>

    <logic:present name="UI_HOME_MGR_FORM" property="managedGroups">
        <% int ii=0; %>
        <logic:iterate id='group' name='UI_HOME_MGR_FORM' property='managedGroups' type='com.cleanwise.service.api.value.UiGroupDataView'>

            <% if ( ( ii % 2 ) == 0 ) { %>
            <tr>
            <% } else { %>
            <tr class="rowa">
            <% } %>

                <td>
                    <a href="uiHome.do?action=changeGroup&id=<bean:write name='group' property='groupData.groupId'/>">
                        <bean:write name='group' property='groupData.shortDesc'/>
                    </a>
                </td>
                <td><bean:write name='group' property='groupData.groupTypeCd'/></td>
                <td><%=theForm.getAssocNamesShortStr(group)%></td>
            </tr>

            <% ii++; %>
        </logic:iterate>
    </logic:present>

    <tr><td cospan="3">&nbsp;</td></tr>
    <tr><td cospan="3">&nbsp;</td></tr>

</table>


