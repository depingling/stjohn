<%@ page import="com.cleanwise.service.api.value.PairView" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.MenuItemView" %>
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
                	String obj1 = (String)((PairView) path.get(i)).getObject1();
                	if (MenuItemView.ATTR.ROOT.equals(((PairView) path.get(i)).getObject1())){

                    s += "<a class=\"linkButton\" href=\"../store/shop.do?action=catalog\">" + ClwI18nUtil.getMessage(request, "shop.catalog.text.shopByCategory", new Object[]{}) + "</a>";
                    if(path.size()>1){
                        s+=" > ";
                    }
                }else if(((i == 0 && !MenuItemView.ATTR.ROOT.equals(obj1))
                	||(i== 1) ) && (i != path.size() - 1))
                {
                	/*
                	if this is a top level category(means category just under root) 
                	and if it have subcategory, then it shouldn't be clickable. 
                	*/
                	s += "<span class=\"linkButton\" >" + obj1 + " > </span>";
                    
                }else if (i == path.size() - 1) {
                    //last one
                    s += "<span class=\"linkButton\" >" + ((PairView) path.get(i)).getObject1() + "</span>";
                } else {
                    String link = (String) ((PairView) path.get(i)).getObject2();
                    link = link == null ? "#" : link;

                    s += "<a class=\"linkButton\" href=\"" + link + "\">" + ((PairView) path.get(i)).getObject1() + "</a>";
                    s += "<span class=\"linkButton\"> > </span>";
                }

                }
                %><%=s%>
        </td>
    </tr>
    </logic:present>

    <logic:notPresent name="<%=name%>" property="<%=property%>">
        <div class="linkButton"><app:storeMessage key="linkButton.label.home" /></div>
    </logic:notPresent>
</table>

