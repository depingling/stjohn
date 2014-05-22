<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%--
 Date: 16.10.2007
 Time: 3:43:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript1.2">
    <!--
    function actionSubmit(formNum, action) {
     var actions;
     actions=document.forms[formNum]["action"];
     for(ii=actions.length-1; ii>=0; ii--) {
       if(actions[ii].value=='hiddenAction') {
         actions[ii].value=action;
         document.forms[formNum].submit();
         break;
       }
     }
     return false;
     }
    -->
</script>
<table width="100%" cellpadding="0" cellspacing="0">
    <tr>
        <td><jsp:include flush='true' page="<%=ClwCustomizer.getStoreFilePath(request,\"t_userWorkOrderCtx.jsp\")%>"/></td>
    </tr>
    <tr>
        <td>
            <html:form name="USER_WORK_ORDER_NOTE_MGR_FORM" action="/serviceproviderportal/workOrderNote.do" scope="session">

                <logic:present name="USER_WORK_ORDER_NOTE_MGR_FORM" property="workOrder">

                    <table align="center" width="99%" cellpadding="0" cellspacing="0">


                        <tr>
                            <td class="customerltbkground" valign="top" colspan="3">
                                <div align="center" class="itemheadmargin">
                    <span class="shopitemhead">
                        <table align="center" width="100%" cellpadding="0" cellspacing="0">
                            <tr>
                                <td><b>
                                    <app:storeMessage key="userWarranty.text.note.description"/>
                                </b>:
                                    <logic:present name="USER_WORK_ORDER_NOTE_MGR_FORM" property="shortDesc">
                                        <html:text name="USER_WORK_ORDER_NOTE_MGR_FORM" property="shortDesc"/>

                                    </logic:present>
                                </td>
                                <td><b>
                                    <app:storeMessage key="userWarranty.text.note.typeCd"/>
                                </b>:
                                    <logic:present name="USER_WORK_ORDER_NOTE_MGR_FORM" property="typeCd">
                                        <html:select name="USER_WORK_ORDER_NOTE_MGR_FORM" property="typeCd">
                                            <html:option value="">
                                                <app:storeMessage  key="admin.select"/>
                                            </html:option>
                                            <html:options collection="WorkOrderNote.user.type.vector" property="value"/>
                                        </html:select>
                                    </logic:present>
                                </td>
                            </tr>
                        </table>
                    </span>
                                </div>
                            </td>

                        </tr>
                        <tr>
                            <td width="3%"></td>
                            <td align="center">
                                <logic:present name="USER_WORK_ORDER_NOTE_MGR_FORM" property="note">
                                    <html:textarea rows="7" cols="76" name="USER_WORK_ORDER_NOTE_MGR_FORM"
                                                   property="note"/>
                                </logic:present>
                            </td>
                            <td width="3%"></td>
                        </tr>
                        <tr>
                            <td colspan="3"></td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <div align="center">
                                    <html:button property="action" styleClass="store_fb"
                                                 onclick="actionSubmit(0,'save');">
                                        <app:storeMessage key="global.label.save"/>
                                    </html:button>
                                </div>
                            </td>
                        </tr>
                    </table>
                </logic:present>
                <html:hidden property="action" value="hiddenAction"/>
                <html:hidden property="tabs" value="f_serviceProviderWorkOrderToolbar"/>
                <html:hidden property="display" value="t_serviceProviderWorkOrder"/>
                <html:hidden property="secondaryToolbar" value=""/>
            </html:form>
        </td>
    </tr>
</table>
