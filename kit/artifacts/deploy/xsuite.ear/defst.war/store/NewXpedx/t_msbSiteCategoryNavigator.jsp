<%@ page import="com.cleanwise.service.api.value.PairView" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%
    String name = "SHOP_FORM";
    String property = "shopNavigatorInfo";
%>


<table class="breadcrumb">
    <tr class="breadcrumb">
        <td class="breadcrumb">


            <logic:present name="<%=name%>" property="<%=property%>">
            <bean:define id="path"
                         name="<%=name%>"
                         property="<%=property%>"
                         type="com.cleanwise.service.api.value.PairViewVector"/>


            <%
                String s = "";
                for (int i = 0; i < path.size(); i++) {
                    if (Constants.ROOT.equals(((PairView) path.get(i)).getObject1())){

                        s += "<a class=\"breadcrumb\" href=\"../userportal/msbsites.do?action=goHome\">" + ClwI18nUtil.getMessage(request, "breadcrumb.label.home", new Object[]{}) + "</a> > ";

                    }else if (i == path.size() -1) { 
                        //last one
                        s += "<span class=\"breadcrumbCurrent\" >" + ((PairView) path.get(i)).getObject1() + "</span>";
                    }else{
                        String link = (String)((PairView) path.get(i)).getObject2();
                        link = link == null ? "#" : link;

                        s += "<a class=\"breadcrumb\" href=\""+link+"\">" + ((PairView) path.get(i)).getObject1() + "</a>";
                        s += "<span class=\"breadcrumb\"> > </span>";
                    }

                }%><%=s%>
        </td>
    </tr>
    </logic:present>

    <logic:notPresent name="<%=name%>" property="<%=property%>">
        <div class="breadcrumb"><app:storeMessage key="breadcrumb.label.home" /></div>
    </logic:notPresent>
</table>

