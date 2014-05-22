<%--
 Date: 01.10.2007
 Time: 13:45:32
 @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
--%>
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.view.forms.StorePortalForm" %>
<%@ page import="com.cleanwise.view.utils.SessionAttributes" %>
<%@ page import="com.cleanwise.view.forms.AggregateItemMgrForm"%>
<%@ page import="com.cleanwise.view.forms.LocateStoreAssetForm"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%
    String jspFormName = request.getParameter("jspFormName");
    if (jspFormName == null) {
        throw new RuntimeException("jspFormName cannot be null");
    }

    //LocateStoreSiteForm theForm = null;
    String jspFormNestProperty = request.getParameter("jspFormNestProperty");
    if (jspFormNestProperty != null) {
        jspFormNestProperty = jspFormNestProperty + ".locateStoreAssetForm";
    } else {
        jspFormNestProperty = "locateStoreAssetForm";
    }

%>
<logic:present name="<%=jspFormName%>" property="<%=jspFormNestProperty%>">
<bean:define id="theForm" name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" type="LocateStoreAssetForm"/>
<%
    if (theForm != null && theForm.getLocateAssetFl()) {
        String jspFormAction = request.getParameter("jspFormAction");
        if (jspFormAction == null) {
            throw new RuntimeException("jspFormAction cannot be null");
        }
        String jspSubmitIdent = request.getParameter("jspSubmitIdent");
        if (jspSubmitIdent == null) {
            throw new RuntimeException("jspSubmitIdent cannot be null");
        }
        String jspReturnFilterProperty = request.getParameter("jspReturnFilterProperty");
        if (jspReturnFilterProperty == null) {
            throw new RuntimeException("jspReturnFilterProperty cannot be null");
        }
        jspSubmitIdent += "#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_ASSET_FORM;
%>


<html:html>
<script language="JavaScript1.2">
    <!--
    function SetCheckedAssets(val) {
        var dml = document.forms['locateStoreAssetForm'];
        var ellen = dml.elements.length;
        //alert('next_form='+ellen);
        for (j = 0; j < ellen; j++) {
            if (dml.elements[j].name == '<%=jspFormNestProperty%>.selected') {
                dml.elements[j].checked = val;

            }
        }

    }


    function SetAndSubmitAssets(name, val) {
        var dml = document.forms['locateStoreAssetForm'];
        var ellen = dml[name].length;
        if (ellen > 0) {
            for (j = 0; j < ellen; j++) {
                if (dml[name][j].value == val) {
                    found = true;
                    dml[name][j].checked = 1;
                } else {
                    dml[name][j].checked = 0;
                }
            }
        } else {
            dml[name].checked = 1;
        }
        var iiLast = dml['action'].length - 1;
        dml['action'][iiLast].value = 'Return Selected';
        dml.submit();
    }

    function submitenter(myfield, e)
    {
        var keycode;
        if (window.event) keycode = window.event.keyCode;
        else if (e) keycode = e.which;
        else return true;

        if (keycode == 13)
        {
            myfield.form.submit();
            return false;
        }
        else
            return true;
    }

    //-->
</script>


<body>

<script src="../externals/lib.js" language="javascript"></script>
<html:form styleId="locateStoreAssetForm" action="<%=jspFormAction%>" scope="session" focus="mainField">
<div class="rptmid">
Find Assets
<table ID=1470 width="750" border="0" class="mainbody">

    <html:hidden property="jspSubmitIdent" value="<%=jspSubmitIdent%>"/>
    <%String prop = jspFormNestProperty + ".property";%>
    <html:hidden property="<%=prop%>" value="<%=jspReturnFilterProperty%>"/>
    <%prop = jspFormNestProperty + ".name";%>
    <html:hidden property="<%=prop%>" value="<%=jspFormName%>"/>

    <tr>
        <td align="right">
            <%prop = jspFormNestProperty + ".searchField";%>

            <b>Search Asset:</b></td>
        <td>
            <html:text styleId="mainField" property="<%=prop%>" onkeypress="return submitenter(this,event)"/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <%prop = jspFormNestProperty + ".searchType";%>
            <html:radio property="<%=prop%>" value="<%=RefCodeNames.SEARCH_TYPE_CD.ID%>"/>
            ID
            <html:radio property="<%=prop%>" value="<%=RefCodeNames.SEARCH_TYPE_CD.BEGINS%>"/>
            Name(starts with)
            <html:radio property="<%=prop%>" value="<%=RefCodeNames.SEARCH_TYPE_CD.CONTAINS%>"/>
            Name(contains)
        </td>
    </tr>
     <tr>
        <td align="right">
            <%prop = jspFormNestProperty + ".searchFieldAssetNumber";%>

            <b>Asset#:</b></td>
        <td>
            <html:text property="<%=prop%>" onkeypress="return submitenter(this,event)"/>
       </td>
    </tr>
    <tr>
        <td colspan='4'>
            <html:submit property="action" value="Search"/>
            <%prop = jspFormNestProperty + ".showInactiveFl";%>
            Show Inactive:
            <html:checkbox property="<%=prop%>"/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <html:submit property="action" value="Cancel"/>
            <html:submit property="action" value="Return Selected"/>
        </td>

    </tr>
</table>


<!-- logic:present name="locateStoreAssetForm.Assets" -->
<% if (theForm.getAssets() != null) {
    int rescount = theForm.getAssets().size();

%>
Search result count: <%=rescount%>
<% if (rescount > 0) {
    if (rescount >= Constants.MAX_ASSETS_TO_RETURN) { %>
<div>&nbsp;1000 (Request Maximum)&nbsp;</div>
<%}%>
<table ID=1471 width="750" border="0" class="results">
    <tr align=left>
        <td><a ID=1472 class="tableheader">Asset Id</a></td>
        <td><a ID=1473 class="tableheader">Asset Name</a></td>
        <td>
            <a ID=1474 href="javascript:SetCheckedAssets(1)">[Check&nbsp;All]</a><br>
            <a ID=1475 href="javascript:SetCheckedAssets(0)">[&nbsp;Clear]</a>
        </td>
        <td class="tableheader">Asset Type</td>
        <td class="tableheader">Asset#</td>
        <td class="tableheader">Serial#</td>
        <td class="tableheader">Site id</td>
        <td class="tableheader">Site Name</td>
        <td class="tableheader">Status</td>
    </tr>

    <%
        String propName = jspFormNestProperty + ".assets";
        prop = jspFormNestProperty + ".assets";
        String selectBoxProp = jspFormNestProperty + ".selected";

    %>

    <logic:iterate id="arrele" name="<%=jspFormName%>" property="<%=prop%>"
                   type="com.cleanwise.service.api.value.AssetView">
        <bean:define id="key" name="arrele" property="assetId"/>
        <% String linkHref = "javascript: SetAndSubmitAssets('" + selectBoxProp + "'," + key + ");";%>
        <tr>
            <td>
                <bean:write name="arrele" property="assetId"/>
            </td>
            <td><a ID=1476 href="<%=linkHref%>">
                <bean:write name="arrele" property="assetName"/>
            </a></td>
            <td>
                <html:multibox property="<%=selectBoxProp%>" value="<%=key.toString()%>"/>
            </td>
            <td>
                <bean:write name="arrele" property="assetTypeCd"/>
            </td>
            <logic:equal name="arrele" property="assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>">
                <td>
                    <bean:write name="arrele" property="assetNumber"/>
                </td>
                <td>
                    <bean:write name="arrele" property="serialNumber"/>
                </td>
            </logic:equal>
            <logic:equal name="arrele" property="assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.CATEGORY%>">
                <td><%="N/A"%>
                </td>
                <td><%="N/A"%>
                </td>
            </logic:equal>
            <%
                String siteIdsStr = "error";
                String siteNamesStr = "error";
                int i = 0;
            %>
            <logic:present name="arrele" property="siteInfo">
                <bean:define id="siteInfo" name="arrele" property="siteInfo" type="java.util.HashMap"/>
                <%
                    ArrayList siteIds = (ArrayList) ((HashMap) siteInfo).get(AssetView.SITE_INFO.SITE_IDS);
                    ArrayList siteNames = (ArrayList) ((HashMap) siteInfo).get(AssetView.SITE_INFO.SITE_NAMES);
                    siteIdsStr = "";
                    siteNamesStr = "";
                    if (siteIds != null) {
                        Iterator iterator = siteIds.iterator();
                        while (iterator.hasNext()) {
                            if (i != 0) {
                                siteIdsStr = siteIdsStr.concat("<br>");
                                siteNamesStr = siteNamesStr.concat("<br>");
                            }
                            siteIdsStr = siteIdsStr.concat(String.valueOf((Integer) iterator.next()));
                            siteNamesStr = siteNamesStr.concat((String) siteNames.get(i));
                            i++;

                        }
                    } else {
                        siteIdsStr = "-";
                        siteNamesStr = "-";
                    }
                %>
            </logic:present>
            <td>
                <logic:equal name="arrele" property="assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>">
                    <%=siteIdsStr%>
                </logic:equal>
                <logic:equal name="arrele" property="assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.CATEGORY%>">
                    <%="N/A"%>
                </logic:equal>
            </td>
            <td>
                <logic:equal name="arrele" property="assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>">
                    <%=siteNamesStr%>
                </logic:equal>
                <logic:equal name="arrele" property="assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.CATEGORY%>">
                    <%="N/A"%>
                </logic:equal>
            </td>
            <td>
                <bean:write name="arrele" property="status"/>
            </td>
        </tr>
    </logic:iterate>
</table>
<%}%>
<%}%>
<!-- /logic:present -->
</div>
<html:hidden property="action" value="Search"/>
</html:form>
</body>

</html:html>

<%}//if(theForm.getLocateAssetFl())%>
</logic:present>
<%--main form--%>