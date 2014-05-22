<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<% 
  String cw_left_footer_shop = ClwCustomizer.getSIP(request,"cw_left_footer_shop.gif");
  String cw_middle_footer_shop = ClwCustomizer.getSIP(request,"cw_middle_footer_shop.gif");
  String cw_right_footer_shop = ClwCustomizer.getSIP(request,"cw_right_footer_shop.gif");
%>       
   
<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
<tr><td> <img src="<%=cw_left_footer_shop%>" ALIGN="top"></td
       ><td><img src="<%=cw_middle_footer_shop%>" ALIGN="top" width="<%=Constants.TABLE_BOTTOM_MIDDLE_BORDER_WIDTH%>" height="8"></td
       ><td><img src="<%=cw_right_footer_shop%>" ALIGN="top"></td>
</tr>
</table>
