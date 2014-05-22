<%--
 Date: 03.10.2007
  Time: 23:07:09
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="java.text.DateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table width="100%" cellpadding="0" cellspacing="0">
<tr>
    <td width="3%"> &nbsp;  </td>
    <td> &nbsp;  </td>
    <td width="3%"> &nbsp;  </td>
</tr>
<tr>
<td> &nbsp;  </td>
<td align="center">
<logic:present name="USER_WARRANTY_CONFIG_DETAIL_MGR_FORM" property="warrantyData">
<logic:present name="USER_WARRANTY_CONFIG_DETAIL_MGR_FORM" property="editAssetWarranty">
<logic:present name="USER_WARRANTY_CONFIG_DETAIL_MGR_FORM" property="editAssetWarranty.assetWarrantyData">

<bean:define id="aid" name="USER_WARRANTY_CONFIG_DETAIL_MGR_FORM" property="editAssetWarranty.assetWarrantyData.assetWarrantyId"/>
<bean:define id="wid" name="USER_WARRANTY_CONFIG_DETAIL_MGR_FORM" property="warrantyData.warrantyId"/>


<table width="70%" cellpadding="0" cellspacing="0">
<tr>
    <td valign="top"><span class="shopassetdetailtxt"><b>
        <app:storeMessage key="userAssets.text.assetNumber"/>
    </b>:</span>
        <logic:present name="USER_WARRANTY_CONFIG_DETAIL_MGR_FORM" property="editAssetWarranty.assetView">
            <logic:present name="USER_WARRANTY_CONFIG_DETAIL_MGR_FORM" property="editAssetWarranty.assetView.assetNumber">
                <bean:write name="USER_WARRANTY_CONFIG_DETAIL_MGR_FORM" property="editAssetWarranty.assetView.assetNumber"/>
            </logic:present>
        </logic:present>
    </td>
    <td valign="top"><span class="shopassetdetailtxt"><b>
        <app:storeMessage key="userAssets.text.assetName"/>
    </b>:</span>

        <logic:present name="USER_WARRANTY_CONFIG_DETAIL_MGR_FORM" property="editAssetWarranty.assetView">
            <logic:present name="USER_WARRANTY_CONFIG_DETAIL_MGR_FORM" property="editAssetWarranty.assetView.assetName">
                <bean:write name="USER_WARRANTY_CONFIG_DETAIL_MGR_FORM" property="editAssetWarranty.assetView.assetName"/>
            </logic:present>
        </logic:present>
    </td>
</tr>
    <tr><td colspan="3">&nbsp;</td></tr>
<logic:greaterThan name="USER_WARRANTY_CONFIG_DETAIL_MGR_FORM"
                   property="editAssetWarranty.assetWarrantyData.assetWarrantyId" value="0">
    <tr>
<td colspan="3">
            <table cellpadding="0" cellspacing="0" width="100%">
                <tr>
                   <td colspan="2" align="center"><span class="shopassetdetailtxt"><b>
                        <app:storeMessage key="userWarranty.text.note"/>
                    </b></span></td>
                    <td align="right"><a href="userWarrantyDetail.do?action=createNote&assetWarrantyId=<%=aid%>&display=t_userWarrantyNoteDetail&tabs=f_userAssetToolbar&secondaryToolbar=f_userSecondaryToolbar">[<app:storeMessage key="global.label.addNote"/>]</a></td>
                </tr>
                <tr>
                    <td class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userWarranty.text.note.description"/>
                        </div>
                    </td>
                    <td class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userWarranty.text.note.addDate"/>
                        </div>
                    </td>
                    <td class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userWarranty.text.note.addBy"/>
                        </div>
                    </td>
                </tr>

                <logic:present name="USER_WARRANTY_CONFIG_DETAIL_MGR_FORM"
                               property="editAssetWarranty.assetWarrantyNotes">
                    <logic:iterate id="note" name="USER_WARRANTY_CONFIG_DETAIL_MGR_FORM"
                                   property="editAssetWarranty.assetWarrantyNotes"
                                   type="com.cleanwise.service.api.value.WarrantyNoteData" indexId="j">
                        <bean:define id="wnId" name="note" property="warrantyNoteId"/>
                        <tr id="note<%=((Integer)j).intValue()%>">
                            <td>
                                <logic:present name="note" property="shortDesc">
                                    <a href="../userportal/userWarrantyNote.do?action=detail&warrantyNoteId=<%=wnId%>&display=t_userWarrantyNoteDetail&tabs=f_userAssetToolbar&secondaryToolbar=f_userSecondaryToolbar">
                                    <bean:write name="note" property="shortDesc"/></a>
                                </logic:present>
                            </td>
                            <td>
                                <logic:present name="note" property="addDate">
                                    <%=ClwI18nUtil.formatDate(request, note.getAddDate(), DateFormat.DEFAULT)%>
                                </logic:present>
                            </td>
                            <td>
                                <logic:present name="note" property="addBy">
                                    <bean:write name="note" property="addBy"/>
                                </logic:present>
                            </td>
                        </tr>
                    </logic:iterate>
                </logic:present>
            </table>
        </td>
    </tr>
</logic:greaterThan>
</table>
</logic:present>
</logic:present>
</logic:present>
</td>
 <td> &nbsp;  </td>
</tr>
</table>