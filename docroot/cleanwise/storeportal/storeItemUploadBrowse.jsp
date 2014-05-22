        


<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js"
  CHARSET="ISO-8859-1"></SCRIPT>
<SCRIPT TYPE="text/javascript" SRC="../externals/lib.js"
  CHARSET="ISO-8859-1"></SCRIPT>

<script language="JavaScript1.2">
<!--
function Submit (val) {
 dml=document.forms; 
 for(i=0; i<dml.length; i++) {
  ellen = dml[i].elements.length;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml[i].elements[j].name=='action' && 
        dml[i].elements[j].value=='BBBBBBB') {
      //alert('action.value='+dml[i].elements[j].value+' -> '+val);
      dml[i].elements[j].value=val;
      dml[i].submit();
      break;
    }
  }
 }
}
function sortSubmit(val) {
 dml=document.forms; 
 for(i=0; i<dml.length; i++) {
  ellen = dml[i].elements.length;
  actionFl = false;
  fieldFl = false;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml[i].elements[j].name=='action' && 
        dml[i].elements[j].value=='BBBBBBB') {
          dml[i].elements[j].value="sort";
          actionFl = true;
        }
      //alert('action.value='+dml[i].elements[j].value+' -> '+val);
    if (dml[i].elements[j].name=='sortField') {  
      dml[i].elements[j].value=val;
      fieldFl = true;
    }
    if(actionFl && fieldFl) {
      dml[i].submit();
      break;
    }
  }
 }
}

function cpoSubmit(catId, ogId, cpoAction) {
 dml=document.forms; 
 for(i=0; i<dml.length; i++) {
  ellen = dml[i].elements.length;
  actionFl = false;
  catalogFl = false;
  ogFl = false;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml[i].elements[j].name=='action' && 
        dml[i].elements[j].value=='BBBBBBB') {
          dml[i].elements[j].value=cpoAction;
          actionFl = true;
        }
      //alert('action.value='+dml[i].elements[j].value+' -> '+val);
    if (dml[i].elements[j].name=='catalogCpoId') {  
      dml[i].elements[j].value=catId;
      catalogFl = true;
    }
    if (dml[i].elements[j].name=='ogCpoId') {  
      dml[i].elements[j].value=ogId;
      ogFl = true;
    }
    if(actionFl && catalogFl && ogFl) {
      dml[i].submit();
      break;
    }
  }
 }
}


-->
</script>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="STORE_ITEM_LOADER_FORM" type="com.cleanwise.view.forms.StoreItemLoaderMgrForm"/>

<%   
  int[] itemPropInd = theForm.getItemPropertiesAbcOrder(); 
  String[] itemProperties = theForm.getItemProperties();
%> 

<div class="text">




  <table ID=1010 cellspacing="0" border="0" width="769" class="mainbody">
  <html:form styleId="1011"  action="/storeportal/storeitemupload.do"  scope="session"  enctype="multipart/form-data">
  <tr>
  <td colspan='4'><b>Select File:</b>
               <html:file name="STORE_ITEM_LOADER_FORM" property="xlsFile" size='80'/>
              
              
<html:submit property="action" value="Upload"   />
         
  </td>
  </tr>
  
  <tr>
  <td colspan='4' align='center'>
  </td>
  </tr>
  </table>
  <% 
  UploadValueData[][] table = theForm.getTable();
  if(table==null) {
  Object[] xlsCont = theForm.getSourceTable();
  if(xlsCont!=null) {
    boolean[] emptyColumnFl = theForm.getEmptyColumnFl();
  %>
  <table ID=1012 cellspacing="1" border="0" width="960" class="mainbody">
  <%
   if(xlsCont.length>0) { 
      String[] row0 = (String[]) xlsCont[0];
      int colQty = 2;
      for(int ii=0; ii<row0.length; ii++) {
        if(!emptyColumnFl[ii]) colQty++;
      }
  %>
<tr><td colspan='<%=colQty%>' class='results'>
<div class='tableheader'><%=theForm.getFileName()%>
  <html:submit property="action" value="Get Subtable" styleClass='text'/>
</div></td></tr>  
<td colspan="2">
<a ID=1013 class="text" href="javascript:SetCheckedGlobal(1,'selected')">[Chk.&nbsp;All]</a>
<br>
<a ID=1014 class="text" href="javascript:SetCheckedGlobal(0,'selected')">[&nbsp;Clear]</a>
</td>
<%   
  for(int ii=0; ii<row0.length; ii++) {
    if(emptyColumnFl[ii]) continue;
    String columnTypeProp = "columnType["+ii+"]";
%>
           
<td>
     <html:select name="STORE_ITEM_LOADER_FORM" property="<%=columnTypeProp%>"  styleClass="text">
        <html:option value="">&nbsp;</html:option>     
        <% for(int jj=0; jj<itemPropInd.length; jj++) { %>
        <html:option value="<%=itemProperties[itemPropInd[jj]]%>"><%=itemProperties[itemPropInd[jj]]%></html:option>     
        <% } %>
     </html:select>
</td>     
<%
  }
%>

  <%
     int relRow = -1;
     for(int jj=0; jj<xlsCont.length; jj++) {     
       String[] row = (String[]) xlsCont[jj];
       if(row==null) continue;
       relRow ++;
  %>
  <tr>
  <td class='results'><%=jj%></td>
  <td>
    <% if(relRow>0) { %>
     <html:checkbox name="STORE_ITEM_LOADER_FORM" property="selectedRows" value='<%=""+jj%>'/>
     <% } else { %>
     &nbsp;
     <% } %>
  </td>

    <%  
       int[] colspan = new int[row.length]; 
       for(int ii=0,kk=0; ii<row.length; ii++) {
         if(emptyColumnFl[ii]) continue;
         colspan[ii]=0;
         if(row[ii]==null) {
            colspan[kk]++;
         } else {
            kk=ii;
            colspan[ii]=1;
         }
       }
       for(int ii=0; ii<row.length; ii++) {
         if(emptyColumnFl[ii]) continue;
         if(colspan[ii]>0) {
    %>    
    <td colspan='<%=colspan[ii]%>' class='results' >
        <% if(row[ii]==null) {%>
           &nbsp;
        <% } else { %> 
        <%=row[ii]%>
        <% } %> 
    </td>
  <% }} %>
</tr>
  <% } %>
  <% } %>
  </table>
  <% } %>
  <% } %>

  
  <input type="hidden" name="action" value="BBBBBBB">
  <input type="hidden" name="sortField" value="BBBBBBB">
  <input type="hidden" name="catalogCpoId" value="BBBBBBB">
  <input type="hidden" name="ogCpoId" value="BBBBBBB">

</html:form>
</body>


</div>
