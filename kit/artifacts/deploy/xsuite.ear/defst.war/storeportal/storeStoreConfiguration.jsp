<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="STORE_STORE_DETAIL_FORM" type="com.cleanwise.view.forms.StoreStoreMgrDetailForm"/>

<div class="text">

<table ID="1584" width="<%=Constants.TABLEWIDTH%>" cellspacing="0" border="0" class="mainbody">
    <tr>
        <td colspan="4" style="font-size: 3pt">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="4" align="left">
            <logic:present name="STORE_STORE_DETAIL_FORM" property="childStores">
                <bean:size id="rescount" name="STORE_STORE_DETAIL_FORM" property="childStores"/>
                &nbsp;&nbsp;Count:&nbsp;<%=rescount%>
            </logic:present>
            <logic:notPresent name="STORE_STORE_DETAIL_FORM" property="childStores">
                &nbsp;&nbsp;Count:&nbsp;0
            </logic:notPresent>
        </td>
    </tr>
    <tr>
        <td colspan="4" style="font-size: 3pt">&nbsp;</td>
    </tr> 
    <tr>
        <td>
            <table width="100%" border="0" cellpadding="2" cellspacing="1">
                <tr>
                    <td class="customerltbkground" vAlign="top" align="middle" colspan="4">
                        <span class="shopassetdetailtxt">
                            <b><app:storeMessage key="store.text.linkedStores"/></b>
                        </span>
                    </td>
                </tr>
                <tr>
                    <td width="15%" class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="admin2.account.detail.label.storeId"/>
                        </div>
                    </td>
                    <td class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="admin2.account.detail.label.storeName"/>
                        </div>
                    </td>
                </tr>
                <logic:present name="STORE_STORE_DETAIL_FORM" property="childStores">
                    <logic:iterate id="storeInfo"
                                   name="STORE_STORE_DETAIL_FORM"
                                   property="childStores"
                                   type="com.cleanwise.service.api.value.BusEntityData"
                                   indexId="j">
                        <%
                        String bgcolor = "";
                        if (j % 2 == 0 ) {  
                            bgcolor = "rowa";
                        } else {
                            bgcolor = "rowd";
                        } 
                        %>
                        <tr class='<%=bgcolor%>'>
                            <td>
                                <logic:present name="storeInfo" property="busEntityId">
                                    <bean:write name="storeInfo" property="busEntityId"/>
                                </logic:present>
                            </td>
                            <td>
                                <logic:present name="storeInfo" property="shortDesc">
                                    <bean:write name="storeInfo" property="shortDesc"/>
                                </logic:present>
                            </td>
                        </tr>
                    </logic:iterate>
                </logic:present>
            </table>
        </td>
    </tr>
    <tr>
        <td colspan="4" style="font-size: 3pt">&nbsp;</td>
    </tr>
</table>

</div>






