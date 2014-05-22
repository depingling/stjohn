<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<% String key = request.getParameter("searchField");
   if (null == key || "".equals(key)) {
                key = (String)session.getAttribute("User.id");
                if (null == key || "".equals(key)) {
                        key = new String("0");
                }
   }

   String action = request.getParameter("action");
   if("createuser".equals(action)) {
        key = new String("0");
   }

   session.setAttribute("User.id", key);

   String type = request.getParameter("type");
   if (null == type || "".equals(type)) {
                type = (String)session.getAttribute("User.type");
                if (null == type || "".equals(type)) {
                        type = new String("");
                }
   }

   if("createuser".equals(action)) {
        type = new String("");
   }

   session.setAttribute("User.type", type);

%>

<%
   String s = (String)request.getRequestURI();
   String style = null;

%>
<table border="0"cellpadding="0" cellspacing="0" width="769">
  <tr bgcolor="#000000">
    <td class="tbartext">

<% if ("0".equals(key)) { %>
        User Detail
        <% } else { %>

                <%
                  if(s.indexOf("Detail") > 0){
                    style="tbarOn";
                  }else{
                    style="tbar";
                  }
                %>
                <a class=<%= style %> href="usermgrDetail.do?action=userdetail&searchField=<%=key%>">User Detail</a> |

                <%
                  if(s.indexOf("relate") > 0 || s.indexOf("Relationships") > 0){
                    style="tbarOn";
                  }else{
                    style="tbar";
                  }
                %>
                <a class=<%= style %> href="related.do?action=user">User Relationships</a> |

                <%
                  if(s.indexOf("userSiteConfig") > 0){
                    style="tbarOn";
                  }else{
                    style="tbar";
                  }
                %>
<a class=<%= style %> href="userSiteConfig.do?action=init&siteconfig=true&id=<%=key%>">User Configuration</a>


  <% if (type.equals(RefCodeNames.USER_TYPE_CD.DISTRIBUTOR)){ %>
                <%
                  if(s.indexOf("userDistributorConfig") > 0){
                    style="tbarOn";
                  }else{
                    style="tbar";
                  }
                %>
                <a class=<%= style %> href="userDistributorConfig.do?action=init&siteconfig=true&id=<%=key%>">Distributor Info Configuration</a>
  <%         }  %>

<%         }  %>
    </td>
  </tr>
</table>
