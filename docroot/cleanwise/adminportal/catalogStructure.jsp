<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>


<bean:define id="catalog" name="CATALOG_DETAIL_FORM" property="detail" />




<html:html>
<head>
<title>Create New Catalog</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals//styles.css">
</head>

<body>
<div>
<html:form action="/adminportal/catalogstructure.do" >
<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/catalogToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>


<font color=red>
<html:errors/>
</font>
<table border="0" cellpadding="0" cellspacing="1" width="769" bgcolor="#cccccc">
  <tr>
  <td> <h3><font color="#000000">Edit Catalog Structure </font></h3>
  </td>
  </tr>
</table>

<jsp:include flush='true' page="ui/catalogInfo.jsp"/>

<table border="0" cellpadding="0" cellspacing="1" width="769" bgcolor="#cccccc">
    <tr><td>
    <html:submit property="action" value="Delete" 
		 onclick="return confirm('You are trying to remove these selected items from catalog,\\nthose items will be removed from related contracts and order guides as well.\\nDo you wish to remove them?');"/>
    <html:submit property="action" value="Add"/>
    <html:submit property="action" value="Edit"/>
    <html:submit property="action" value="Replace"/>
    <html:submit property="action" value="Move"/>
    <html:submit property="action" value="Copy"/>
    <html:submit property="action" value="Find Item"/>
    <html:submit property="action" value="Expand/Collapse Tree"/>
    <logic:equal name="CATALOG_DETAIL_FORM" property="detail.catalogTypeCd" value="SYSTEM">
      <html:submit property="action" value="Create Item"/>
    </logic:equal>
    </td></tr>
    <tr><td>
    <html:text name="CATALOG_STRUCTURE_FORM" property="wrkField" size="64"/>
     <html:select name="CATALOG_STRUCTURE_FORM" property="majorCategoryId">
         <html:option value="0">Major Category</html:option>
         <logic:present  name="CATALOG_STRUCTURE_FORM" property="majorCategories">
         <logic:iterate id="mjc" name="CATALOG_STRUCTURE_FORM" property="majorCategories"
           type="com.cleanwise.service.api.value.ItemData"  indexId="catIdx">
           <% String mjcVal = ""+mjc.getItemId();
              String mjcName = ""+mjc.getShortDesc();
           %>
           <html:option value="<%=mjcVal%>"><%=mjcName%></html:option>
         </logic:iterate>
         </logic:present>
     </html:select>

    </td></tr>
</table>


<!-- Category tree -->
<table border="0" width="769" class="results" >
<tr><td>
<b>Top</b>
<html:radio name="CATALOG_STRUCTURE_FORM" property="backBox" value="-1"/>
<logic:greaterThan name="CATALOG_STRUCTURE_FORM" property="catalogTreeSize" value="0">
   <bean:define id="pagesize" name="CATALOG_STRUCTURE_FORM" property="catalogTreeSize"/>
   <logic:iterate id="node" name="CATALOG_STRUCTURE_FORM" property="catalogTree"
    offset="0" length="<%=pagesize.toString()%>" type="com.cleanwise.service.api.util.CatalogNode"
    indexId="nodeIdx">
    <br>
    <bean:define id="key"  name="node" property="id"/>
    <bean:define id="productSku"  name="node" property="productSku"/>
    <bean:define id="name"  name="node" property="name"/>
    <bean:define id="type"  name="node" property="type"/>
    <% type=((String)type).substring(0,1); %>
    <bean:define id="level" name="node" property="level"/>
    <% String linkHref = new String("catalogstructure.do?action=node&nodeId=" +nodeIdx);%>
    <% String exp = new String(""+nodeIdx); %>
    <% String leftShift = ""; String tab=new String(new char[]{'\t'});
       for(int ii=0; ii<((Integer)level).intValue(); ii++) %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <%;%>
    <bean:define id="shift" value="<%=leftShift%>"/>

    <bean:write name="shift"/>
    <html:multibox name="CATALOG_STRUCTURE_FORM" 
      property="sourceNodes" value="<%=nodeIdx.toString()%>"/>

    <logic:equal name="node" property="hasSub" value="true">
      <html:link href="<%=linkHref%>">
      <bean:write name="name"/>
       <logic:present  name="node" property="majorCategoryName">
       &nbsp;-&nbsp; <bean:write name="node" property="majorCategoryName"/>
       </logic:present>
      </html:link>
    </logic:equal>

    <% /* Eliminate the hyperlink on the name since
          there are no subcategories.
       */ %>
    <logic:notEqual name="node" property="hasSub" value="true">
        <span style="background-color: white;">
      <bean:write name="name"/>
       <logic:present  name="node" property="majorCategoryName">
       &nbsp;-&nbsp; <bean:write name="node" property="majorCategoryName"/>
       </logic:present>
        </span>
      <logic:notEqual name="type" value="CATEGORY">
        <span style="background-color: #cccccc;">
         &nbsp;CW.Sku <bean:write name="productSku"/>
        </span>
      </logic:notEqual>
    </logic:notEqual>

<logic:equal name="type" value="CATEGORY">
  <html:radio name="CATALOG_STRUCTURE_FORM" property="backBox" 
  value="<%=nodeIdx.toString()%>"/>
</logic:equal>

 </logic:iterate>
 </logic:greaterThan>
</td></tr>
</table>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</html:form>
</div>
</body>

</html:html>
