<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.value.WorkOrderNoteData" %>
<%@ page import="java.text.DateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%--
  Date: 16.10.2007
  Time: 3:42:12
--%>
<bean:define id="theForm" name="USER_WORK_ORDER_NOTE_MGR_FORM" type="com.cleanwise.view.forms.UserWorkOrderNoteMgrForm"/>

<table width="100%" cellpadding="0" cellspacing="0">
    <tr>
        <td><jsp:include flush='true' page="<%=ClwCustomizer.getStoreFilePath(request,\"t_userWorkOrderCtx.jsp\")%>"/></td>
    </tr>
    <tr>
        <td>
            <logic:present name="USER_WORK_ORDER_NOTE_MGR_FORM" property="workOrderNotes">
                <bean:size id="rescount" name="USER_WORK_ORDER_NOTE_MGR_FORM" property="workOrderNotes"/>
                <table width="99%" align=center cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="shopcharthead">
                            <div class="fivemargin">
                                <app:storeMessage key="userWorkOrder.text.note.description"/>
                            </div>
                        </td>
                        <td class="shopcharthead">
                            <div class="fivemargin">
                                <app:storeMessage key="userWorkOrder.text.note.typeCd"/>
                            </div>
                        </td>
                        <td class="shopcharthead">
                            <div class="fivemargin">
                                <app:storeMessage key="userWorkOrder.text.note.addDate"/>
                            </div>
                        </td>
                        <td class="shopcharthead">
                            <div class="fivemargin">
                                <app:storeMessage key="userWorkOrder.text.note.addBy"/>
                            </div>
                        </td>
                    </tr>


                    <logic:iterate id="arrele" name="USER_WORK_ORDER_NOTE_MGR_FORM" property="workOrderNotes"
                                   indexId="i">
                        <bean:define id="eleid" name="arrele" property="workOrderNoteId" type="java.lang.Integer"/>
                        <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
                            <td>
                                <logic:present name="arrele" property="shortDesc">
                                    <a href="../userportal/userWorkOrderNote.do?action=noteDetail&workOrderNoteId=<%=eleid%>&display=t_userWorkOrderNoteDetail&tabs=f_userWorkOrderToolbar&secondaryToolbar=f_userSecondaryToolbar">
                                        <bean:write name="arrele" property="shortDesc"/>
                                    </a>
                                </logic:present>
                            </td>

                            <td>
                                <logic:present name="arrele" property="typeCd">
                                    <bean:write name="arrele" property="typeCd"/>
                                </logic:present>
                            </td>
                            <td>
                                <logic:present name="arrele" property="addBy">
                                    <bean:write name="arrele" property="addBy"/>
                                </logic:present>
                            </td>
                            <td>
                                <logic:present name="arrele" property="addDate">
                                    <%=ClwI18nUtil.formatDate(request, ((WorkOrderNoteData) arrele).getAddDate(), DateFormat.DEFAULT)%>
                                </logic:present>
                            </td>
                        </tr>
                    </logic:iterate>
                      <tr><td colspan="4">&nbsp;</td></tr>
                         <tr>
                             <td colspan="4" align="right">
                               <div align="center" class="littleButton">
                                  <a class="linkNotLine" href="../userportal/userWorkOrderNote.do?action=create&display=t_userWorkOrderNoteDetail&tabs=f_userWorkOrderToolbar&secondaryToolbar=f_userSecondaryToolbar">
                                    <app:storeMessage key="global.action.label.create"/>
                              </a></div>
                             </td>

                         </tr>
                </table>
            </logic:present>
        </td>
    </tr>
</table>
