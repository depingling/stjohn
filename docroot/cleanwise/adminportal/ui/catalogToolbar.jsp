<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="catalogNum" name="CATALOG_DETAIL_FORM" property="detail.catalogId" />
<% String linkDetail = new String("catalogdetail.do?action=edit&id="+catalogNum);%>

<%
   String s = (String)request.getRequestURI();
   String style = null;
   
%>


<table border="0" cellpadding="0" cellspacing="0" width="769">
<tr>
  <tr bgcolor="#000000">
<td class="tbartext">
<%
  if(s.indexOf("Detail") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
<a class=<%= style %> href="<%=linkDetail%>">Catalog Detail</a> |

<%
  if(s.indexOf("catalogStructure") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
<a class=<%= style %> href="catalogstructure.do?action=refresh">Catalog Structure</a> |

<%
  if(s.indexOf("relate") > 0 || s.indexOf("Relationships") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
<a class=<%= style %> href="related.do?action=catalog">Catalog Relationships</a> |


<logic:equal name="CATALOG_DETAIL_FORM" property="detail.catalogTypeCd" value="SYSTEM">

<%
  if(s.indexOf("itemSearch") > 0 || s.indexOf("itemMaster") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
    <a class=<%= style %> href="itemsearch.do">Master Items</a>
</logic:equal>


<logic:notEqual name="CATALOG_DETAIL_FORM" property="detail.catalogTypeCd" value="SYSTEM">

<%
  if(s.indexOf("catalogConfig") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
<a class=<%= style %> href="catalogConfig.do?action=init">Catalog Configuration</a> |

<%
  if(s.indexOf("itemSearch") > 0 || s.indexOf("itemCatalog") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
<a class=<%= style %> href="itemsearch.do">Items</a>
</logic:notEqual>

</td>
</tr>
</table>


