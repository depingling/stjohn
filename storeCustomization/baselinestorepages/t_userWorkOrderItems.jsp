<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%--
  Date: 15.10.2007
  Time: 13:55:53
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<table width="100%" cellpadding="0" cellspacing="0">
<tr>
<td>
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"t_userWorkOrderCtx.jsp")%>'/>
</td>
</tr>
<tr>
<td>
    <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="allWorkOrderItems">
        <bean:size id="rescount" name="USER_WORK_ORDER_ITEM_MGR_FORM" property="allWorkOrderItems"/>
        <table width="99%" align=center cellpadding="0" cellspacing="0">
            <tr>
                <td class="shopcharthead">
                </td>
                <td class="shopcharthead">
                    <div class="fivemargin">
                        <app:storeMessage key="userWorkOrder.text.workOrderItems.sequence"/>
                    </div>
                </td>
                <td class="shopcharthead">
                    <div class="fivemargin">
                        <app:storeMessage key="userWorkOrder.text.workOrderItems.item"/>
                    </div>
                </td>
                <td class="shopcharthead">
                    <div class="fivemargin">
                        <app:storeMessage key="userWorkOrder.text.workOrderItems.description"/>
                    </div>
                </td>

            </tr>
            <logic:greaterThan name="rescount" value="0">

                <logic:iterate id="arrele" name="USER_WORK_ORDER_ITEM_MGR_FORM" property="allWorkOrderItems"
                               indexId="i">
                    <logic:present name="arrele" property="workOrderItem">
                        <bean:define id="eleid" name="arrele"
                                     property="workOrderItem.workOrderItemId"/>

                        <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
                            <td>
                                <table cellpadding="0" cellspacing="0" border="0">
                                    <tr>
                                        <td valign="top">
                                            <%String upurl = "../userportal/userWorkOrderItem.do?action=upSeq&display=t_userWorkOrderItems&tabs=f_userWorkOrderToolbar&secondaryToolbar=f_userSecondaryToolbar&workOrderItemId=" + eleid;%>
                                            <a href="<%=upurl%>">
                                                <img border="0" alt="[Move up]"
                                                     src='<%=ClwCustomizer.getSIP(request,"button_point_up.gif")%>'>
                                            </a></td>
                                    </tr>
                                    <tr>
                                        <td valign="top">
                                            <%String downurl = "../userportal/userWorkOrderItem.do?action=downSeq&display=t_userWorkOrderItems&tabs=f_userWorkOrderToolbar&secondaryToolbar=f_userSecondaryToolbar&workOrderItemId=" + eleid;%>
                                            <a href="<%=downurl%>">
                                                <img border="0" alt="[Move down]"
                                                     src='<%=ClwCustomizer.getSIP(request,"button_point_down.gif")%>'>
                                            </a>
                                       </td>
                                    </tr>
                                </table>
                            </td>
                            <td>

                                <logic:present name="arrele" property="workOrderItem.sequence">
                                    <bean:write name="arrele" property="workOrderItem.sequence"/>
                                </logic:present>

                            </td>
                            <td>
                                <bean:write name="arrele" property="workOrderItem.workOrderItemId"/>
                            </td>
                            <td>
                                <logic:present name="arrele" property="workOrderItem.shortDesc">
                                    <a href="../userportal/userWorkOrderItem.do?action=detail&workOrderItemId=<%=eleid%>&display=t_userWorkOrderItemDetail&tabs=f_userWorkOrderToolbar&secondaryToolbar=f_userSecondaryToolbar">
                                        <bean:write name="arrele" property="workOrderItem.shortDesc"/>
                                    </a>
                                </logic:present>
                            </td>
                        </tr>
                    </logic:present>
                </logic:iterate>
            </logic:greaterThan>
            <tr>
                <td colspan="6">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="6" align="right">
                    <div align="center" class="littleButton">
                        <a class="linkNotLine"
                           href="../userportal/userWorkOrderItem.do?action=create&display=t_userWorkOrderItemDetail&tabs=f_userWorkOrderToolbar&secondaryToolbar=f_userSecondaryToolbar">
                            <app:storeMessage key="global.action.label.create"/>
                        </a></div>
                </td>
            </tr>
        </table>
    </logic:present>
</td>
</tr>
</table>
