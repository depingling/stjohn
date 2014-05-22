
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.value.*"%>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<% CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER); %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="MSDS_FORM" type="com.cleanwise.view.forms.MsdsSpecsForm"/>
<% int[] myDocDescriptor = theForm.getMyDocDescriptor(); %>
<%
String query=request.getQueryString();
String currUri=SessionTool.getActualRequestedURIPage(request)+"?";
String submitBase=SessionTool.getActualRequestedURIStrutsMapping(request)+"?";

if(query!=null){
    query="tabs="+request.getParameter("tabs")+"&display="+request.getParameter("display")+"&";
    currUri+=query;
    submitBase+=query;
}
String submitUrl1 = submitBase+"action=control";
int colCount = 8;
if (appUser.getUserAccount().isHideItemMfg()) {
    colCount = colCount - 2;
}
%>


<table align="center" border="0" cellpadding="0" cellspacing="0" width="98%">
<tr><td>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="98%">
  <html:form styleId="sel" action="<%=submitUrl1%>">
  <tr>
  <!-- search -->
  <td align="left" valign="top" class="smalltext" width="30%">
    <jsp:include flush='true' page="<%=ClwCustomizer.getStoreFilePath(request,\"t_msdsSearch.jsp\")%>"/>
  </td>
  </tr>
  <logic:greaterThan name="MSDS_FORM" property="productListSize" value="0">
  <tr>
  <td class=text colspan="2" align="right">
    <b><app:storeMessage key="shop.msdsSpecs.text.orderBy"/>&nbsp;</b>
        <html:select name="MSDS_FORM" property="orderBy"
          onchange="javascript: { document.forms[0].submit();}">
          <html:option value="<%=\"\"+Constants.ORDER_BY_CATEGORY%>">
      <app:storeMessage key="shop.msdsSpecs.text.category"/>
          </html:option>
    <html:option value="<%=\"\"+Constants.ORDER_BY_CUST_SKU%>">
      <app:storeMessage key="shop.msdsSpecs.text.ourSku#"/>
    </html:option>
          <html:option value="<%=\"\"+Constants.ORDER_BY_NAME%>">
      <app:storeMessage key="shop.msdsSpecs.text.poductName"/>
          </html:option>
    <% if (!appUser.getUserAccount().isHideItemMfg()) { %>
      <html:option value="<%=\"\"+Constants.ORDER_BY_MFG_NAME%>">
      <app:storeMessage key="shop.msdsSpecs.text.mfgName"/>
          </html:option>
          <html:option value="<%=\"\"+Constants.ORDER_BY_MFG_SKU%>">
      <app:storeMessage key="shop.msdsSpecs.text.mfgSku#"/>
          </html:option>
    <% } %>
    </html:select>
        <input type="hidden" name="sort">
  </td>
  </tr>
  </logic:greaterThan>
  <html:hidden property="command" value="CCCCCCC"/>
  </html:form>
</table>
</td></tr>
<tr><td>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="98%">
<!-- List of items -->
<logic:greaterThan name="MSDS_FORM" property="productListSize" value="0">
<%String submitUrl =submitBase+"?action=showDoc";%>

  <!-- Page control -->
  <html:form action="<%=submitUrl%>">
  <tr>
  <td colspan="<%=colCount%>" class="msdscharthead"><div class="fivemargin">
    <logic:equal name="MSDS_FORM" property="docType" value="MSDS">
      <b><app:storeMessage key="shop.msdsSpecs.text.msds"/></b>
    </logic:equal>
    <logic:equal name="MSDS_FORM" property="docType" value="SPEC">
      <b><app:storeMessage key="shop.msdsSpecs.text.specs"/></b>
    </logic:equal>
    <logic:equal name="MSDS_FORM" property="docType" value="DED">
      <b><app:storeMessage key="shop.msdsSpecs.text.disinfectantEfficacy"/></b>
    </logic:equal>
  </div></td>
  </tr>
  <tr>
  <td class="msdscharthead" align="center"><app:storeMessage key="shoppingItems.text.ourSku#"/></td>
  <td class="msdscharthead" align="center"><app:storeMessage key="shoppingItems.text.product<sp>Name"/></td>
  <% if (!appUser.getUserAccount().isHideItemMfg()) { %>
    <td class="msdscharthead" align="center"><app:storeMessage key="shoppingItems.text.mfgSkuNum"/></td>
    <td class="msdscharthead" align="center"><app:storeMessage key="shoppingItems.text.manufacturer"/></td>
  <%} %>

  <td class="msdscharthead" align="center"><app:storeMessage key="shoppingItems.text.size"/></td>
  <td class="msdscharthead" align="center"><app:storeMessage key="shoppingItems.text.uom"/></td>
  <td class="msdscharthead" align="center"><app:storeMessage key="shoppingItems.text.pack"/></td>
  <td class="msdscharthead" align="center">&nbsp;</td>
  </tr>
  <tr>
  </tr>

<%--
  <bean:define id="offset" name="MSDS_FORM" property="offset"/>
  <bean:define id="pagesize" name="MSDS_FORM" property="pageSize"/>


  <logic:iterate id="item" name="MSDS_FORM" property="cartItems"
   offset="0" length="<%=\"\"+pagesize%>" indexId="kkk"
   type="com.cleanwise.service.api.value.ShoppingCartItemData">
     <bean:define id="itemId" name="item" property="product.productId"/>
--%>

        <logic:iterate id="item" name="MSDS_FORM" property="cartItems"
   indexId="kkk" type="com.cleanwise.service.api.value.ShoppingCartItemData">
     <bean:define id="itemId" name="item" property="product.productId"/>

     <% if(theForm.getOrderBy()==Constants.ORDER_BY_CATEGORY) { %>
     <% if(theForm.isCategoryChanged(((Integer)kkk).intValue())) { %>
       <tr>
       <td class="shopcategory" colspan = "<%=colCount%>"><bean:write name="item" property="categoryPath"/></td>
       </tr>
     <% } %>
     <% } %>
     <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)kkk)%>">
     <td class="text" align="center">
       <bean:write name="item" property="actualSkuNum"/>&nbsp;
     </td>
     <td class="text" align="center">
       <%
       String skuDesc = item.getProduct().getCustomerProductShortDesc();
       if(skuDesc==null || skuDesc.trim().length()==0) {
         skuDesc = item.getProduct().getShortDesc();
       }
       
       String docUrl = null;
       int fl_msds_plugin = 0;

       String msdsStorageType = theForm.getMsdsStorageTypeCd();
       if ((msdsStorageType == null) || msdsStorageType.trim().equals("")) {
              msdsStorageType = RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE; //Default
       }

       String dedStorageType = theForm.getDedStorageTypeCd();
       if ((dedStorageType == null) || dedStorageType.trim().equals("")) {
	          dedStorageType = RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE; //Default
       }

       String specStorageType = theForm.getSpecStorageTypeCd();
       if ((specStorageType == null) || specStorageType.trim().equals("")) {
	          specStorageType = RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE; //Default
       }
       if("MSDS".equals(theForm.getDocType())) {
         String msds=item.getProduct().getMsds();
         String msdsJdUrl=item.getProduct().getProductJdWsUrl(); 
    	 if (msdsStorageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE)) {
           if(msds!=null && msds.trim().length()>0) { //MSDS IS set: MSDS Pdf image rules, even if there IS an MSDS Plug-in
        	   docUrl = msds;
       %>    		  
       <%  } 
           if(msds==null || msds.trim().length()==0) { //MSDS IS NOT set
          	  if(msdsJdUrl!=null && msdsJdUrl.trim().length()>0) { //MSDS Plug-in IS set
          		 docUrl = msdsJdUrl;
          		 fl_msds_plugin = 1;
          	  }
           }
           if(docUrl!=null && docUrl.trim().length()>0) {
 
                if (fl_msds_plugin == 1) { //msds Plugin
       %>  
                <a href="/<%=storeDir%>/<%=docUrl%>" target="_blank">
                  <%=skuDesc%>
                </a> 
            <%  } else {        	      
          	          String documentTypeLinkFromDb = "/store/shop.do?action=itemDocumentFromDb&path=" + docUrl;   	                   
            %>  
            	      <html:link action="<%=documentTypeLinkFromDb %>" target="_blank"><%=skuDesc%>
	                  </html:link>
            <%  } 	                        
  
           } else {
       %>
        	             			<%=skuDesc%>
       <% 
           }
       %>
       <%
         } else if (msdsStorageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)) { 
             if(msds!=null && msds.trim().length()>0) { //MSDS IS set: MSDS Pdf image rules, even if there IS an MSDS Plug-in
          	   docUrl = msds;
         %>    		  
         <%  } 
             if(msds==null || msds.trim().length()==0) { //MSDS IS NOT set
            	  if(msdsJdUrl!=null && msdsJdUrl.trim().length()>0) { //MSDS Plug-in IS set
            		 docUrl = msdsJdUrl;
            		 fl_msds_plugin = 1;
            	  }
             }
             if(docUrl!=null && docUrl.trim().length()>0) {
                if (fl_msds_plugin == 1) { //msds Plugin
         %>  
                <a href="/<%=storeDir%>/<%=docUrl%>" target="_blank">
                  <%=skuDesc%>
                </a> 
            <%  } else {        	      
          	          String documentTypeLinkFromE3 = "/store/shop.do?action=itemDocumentFromE3Storage&path=" + docUrl;  	                   
            
 
            %>  
            	      <html:link action="<%=documentTypeLinkFromE3 %>" target="_blank"><%=skuDesc%>          	                    	      
	                  </html:link>
	      <%    } 	                        
             } else {
         %>
          	          <%=skuDesc%>
         <% 
             }
         %>         
      <% } //else if %>
     <%} //if("MSDS") %>
     <% 
       if("SPEC".equals(theForm.getDocType())) {
    	       docUrl=item.getProduct().getSpec();
               if (specStorageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE)) {	 
    	    	   if(docUrl!=null && docUrl.trim().length()>0) {
                             String documentTypeLinkFromDb = "/store/shop.do?action=itemDocumentFromDb&path=" + docUrl; 
	  %>                                    
	                         <html:link action="<%=documentTypeLinkFromDb %>" target="_blank"><%=skuDesc%>
	                         </html:link> 
      <% 
    	    	   } else {    	    		   
      %>
    	    		   <%=skuDesc%>
      <% 
    	    	   }
               }  else if (specStorageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)) { 
    	    	          if(docUrl!=null && docUrl.trim().length()>0) {
    	    		         String documentTypeLink = "/store/shop.do?action=itemDocumentFromE3Storage&path=" + docUrl;                                    
	  %>
	                         <html:link action="<%=documentTypeLink %>" target="_blank"><%=skuDesc%>
	                         </html:link> 
	  <% 
	                      } else {
	  %>
	                    	  <%=skuDesc%>
	  <% 
	                      }   
    	       }
         }
         if("DED".equals(theForm.getDocType())) {
          docUrl=item.getProduct().getDed();
          if (dedStorageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE)) {	 
	    	   if(docUrl!=null && docUrl.trim().length()>0) {
	    		   String documentTypeLinkFromDb = "/store/shop.do?action=itemDocumenFromDb&path=" + docUrl; 
%>                                    
                  <html:link action="<%=documentTypeLinkFromDb %>" target="_blank"><%=skuDesc%>
                  </html:link>  
 <% 
	    	   } else {    	    		   
 %>
	    		   <%=skuDesc%>
 <% 
	    	   }
          }  else if (dedStorageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)) { 
	    	         if(docUrl!=null && docUrl.trim().length()>0) {
	    		         String documentTypeLink = "/store/shop.do?action=itemDocumentFromE3Storage&path=" + docUrl;                                    
 %>
                        <html:link action="<%=documentTypeLink %>" target="_blank"><%=skuDesc%>
                        </html:link> 
 <% 
                     } else {
 %>
                   	  <%=skuDesc%>
 <% 
                     }   
	       }
         } //if("DED".equals(theForm.getDocType())) { 
 %>
     </td>
     <% if (!appUser.getUserAccount().isHideItemMfg()) { %>
     <td class="text" align="center">
       <bean:write name="item" property="product.manufacturerSku"/>
       &nbsp;
     </td>
     <td class="text" align="center">
         <bean:write name="item" property="product.manufacturerName"/>&nbsp;
     </td>
     <% } %>
     <td class="text" align="center">
       <bean:write name="item" property="product.size"/>&nbsp;
     </td>
     <td class="text" align="center">
       <bean:write name="item" property="product.uom"/>&nbsp;
     </td>
     <td class="text" align="center">
       <bean:write name="item" property="product.pack"/>&nbsp;
     </td>
     <td class="text" align="center">
         &nbsp;
     </td>
     </tr>
   </logic:iterate>

   <%--
   <logic:greaterThan name="MSDS_FORM" property="productListSize" value="<%=\"\"+pagesize%>">
     <tr>
      <td colspan="<%=colCount%>">

      <logic:notEqual name="MSDS_FORM" property="prevPage" value="-1">
        <bean:define id="prevPage" name="MSDS_FORM" property="prevPage"/>
        <% String linkHref = new String(currUri+"action=goPage&page=" + prevPage);%>
        <html:link href="<%=linkHref%>"><app:storeMessage key="shop.msdsSpecs.text.prev"/></html:link>
      </logic:notEqual>

      <logic:iterate id="pages" name="MSDS_FORM" property="pages"
      offset="0" indexId="ii" type="java.lang.Integer">
        <logic:notEqual name="MSDS_FORM" property="currentPage" value="<%=\"\"+ii%>" >
          <% String linkHref = new String(currUri+"action=goPage&page=" + ii);%>
          <html:link href="<%=linkHref%>">[<%=ii.intValue()+1%>]</html:link>
        </logic:notEqual>
        <logic:equal name="MSDS_FORM" property="currentPage" value="<%=\"\"+ii%>" >
         [<%=ii.intValue()+1%>]
        </logic:equal>
      </logic:iterate>
      <logic:notEqual name="MSDS_FORM" property="nextPage" value="-1">
        <bean:define id="nextPage" name="MSDS_FORM" property="nextPage"/>
        <% String linkHref = new String(currUri+"action=goPage&page=" + nextPage);%>
        <html:link href="<%=linkHref%>"><app:storeMessage key="global.action.label.next"/></html:link>
      </logic:notEqual>
      </td>
      </tr>
    </logic:greaterThan>
--%>
  </html:form>
</logic:greaterThan>
  <tr>
  <td colspan="<%=colCount%>" class="msdscharthead" align="center">&nbsp</td>
  </tr>
</table>
</td></tr>
<tr><td>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="98%">
  <tr>
  <td class="smalltext" valign="up" width="70%">
   <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%" >
   <tr><td><jsp:include flush='true' page="t_msdsMsdsList.jsp"/></td></tr>
   <tr><td>&nbsp;</td></tr>
   <tr><td><jsp:include flush='true' page="t_msdsSpecList.jsp"/></td></tr>
   <tr><td>&nbsp;</td></tr>
   <tr><td><jsp:include flush='true' page="t_msdsDedList.jsp"/></td></tr>
   </table>
  </td>
  </tr>
</table>

</td></tr>

</table>





