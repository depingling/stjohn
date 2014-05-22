<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<table border="0" cellspacing="0" cellpadding="0" width="250px">

    <tr valign="bottom"  bgcolor="white">
        <td valign="bottom" align="left" width="8px" height="8px"><img height="8px" src='<%=ClwCustomizer.getSIP(request,"cw_left_header_shop.png")%>'></td>
        <td valign="top" width="100%"><img height="1px" width="100%" src='<%=ClwCustomizer.getSIP(request,"cw_border_point.png")%>'></td>
        <td valign="bottom" align="right" width="8px" height="8px"><img height="8px" src='<%=ClwCustomizer.getSIP(request,"cw_right_header_shop.png")%>'></td>
    </tr>

    <tr  bgcolor="white">
        <td valign="top" align="left" width="8px" height="100%"><img width="1px" height="100%" src='<%=ClwCustomizer.getSIP(request,"cw_border_point.png")%>'></td>
        <td>
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td valign="top">
                        <jsp:include page='<%=ClwCustomizer.getStoreFilePath(request, request.getParameter("content"))%>'/>
                    </td>
                </tr>
            </table>
        </td>
        <td valign="top" align="right" width="8px" style="border:5px" height="100%"><img border="0"  width="1px" height="100%" src='<%=ClwCustomizer.getSIP(request,"cw_border_point.png")%>'></td>
    </tr>

    <tr  valign="top"  bgcolor="white">
        <td  valign="top"  align="right" width="8px" height="8px"><img height="8px" src='<%=ClwCustomizer.getSIP(request,"cw_left_footer_shop.png")%>'></td>
        <td  valign="bottom" width="100%"><img height="1px" width="100%" src='<%=ClwCustomizer.getSIP(request,"cw_border_point.png")%>'></td>
        <td  valign="top" align="left" width="8px" height="8px"><img height="8px" src='<%=ClwCustomizer.getSIP(request,"cw_right_footer_shop.png")%>'></td>
    </tr>


</table>



                                                  