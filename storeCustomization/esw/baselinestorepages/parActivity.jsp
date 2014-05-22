<%@page import="com.cleanwise.view.forms.ShoppingCartForm"%>
<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@page import="com.cleanwise.service.api.value.ShoppingInfoData"%>
<%@page import="com.cleanwise.service.api.value.ShoppingCartData"%>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<bean:define id="shoppingForm" name="esw.ShoppingForm"  type="com.espendwise.view.forms.esw.ShoppingForm"/> 
<%
	ShoppingCartForm shoppingCartForm = null;
	shoppingCartForm = shoppingForm.getShoppingCartForm();
	
    
	if (ShopTool.isInventoryShoppingOn(request) ) {
	  List<ShoppingCartData.ItemChangesEntry> itemsRemoved = shoppingCartForm.getParItemActivities();;
	  if ( Utility.isSet(itemsRemoved)) {
			 // Get Item History counts list, those will be used in 
			// rowspans & columnspans of the table.
			String prevItemSku = "";
			int historyCount = 0;
			List<Integer> itemHistoryCountList = new ArrayList<Integer>();
			for ( int idx = 0; itemsRemoved != null && idx < itemsRemoved.size(); idx++ ) {
				
				 ShoppingCartData.ItemChangesEntry log = (ShoppingCartData.ItemChangesEntry)itemsRemoved.get(idx);
				 
				 if(idx==0 || log.getSku().equals(prevItemSku)) {
					 if (idx==itemsRemoved.size()-1){
						 historyCount++;
						itemHistoryCountList.add(new Integer(historyCount));
						historyCount = 1;
					} else {
						 historyCount++;
					}
					
				 } else {
					 itemHistoryCountList.add(new Integer(historyCount));
					 historyCount = 1;
				 }
				 prevItemSku = log.getSku();
			}

			//If there are no items to show in PAR Activity Table.
			if(Utility.isSet(itemHistoryCountList)) {

	%>            
             <!-- Start Box -->
              <div class="boxWrapper noBackground">
                 <div class="content">
                     <div class="left clearfix">
                         <h2><app:storeMessage  key="shoppingCart.text.parActivity" /></h2>
                         
                         <table class="actionListing noMargin">
                             <colgroup>

                                 <col />
                                 <col class="productName" />
                                 <col class="action" />
                                 <col class="date" />
                                 <col class="username" />
                             </colgroup>
                             <thead>
                                 <tr>
                                     <th>
										<app:storeMessage  key="shoppingItems.text.sku#" />
                                        
                                     </th>
                                     <th>
                                     	<app:storeMessage  key="shoppingItems.text.productName" />
                                        
                                     </th>
                                     <th>
                                     	<app:storeMessage  key="shoppingCart.text.action" />
                                         
                                     </th>
                                     <th>
                                         <app:storeMessage  key="shoppingCart.text.date" />
                                     </th>

                                     <th>
                                      	 <app:storeMessage  key="shoppingCart.text.userName" />
                                         
                                     </th>
                                 </tr>
                             </thead>
                             <tbody>
                                 <!--Start Repeat -->
                                 <%
	                                prevItemSku = "";
	                                int itemHistorySize = 0;
	                                int index = 0;
									for ( int idx = 0; itemsRemoved != null && idx < itemsRemoved.size(); idx++ ) {
									  ShoppingCartData.ItemChangesEntry log = (ShoppingCartData.ItemChangesEntry)itemsRemoved.get(idx);
									  ShoppingInfoData sid = log.getShoppingInfoData();
									  String messKey = sid.getMessageKey();
								
                               	   if (idx==0 || ! log.getSku().equals(prevItemSku)) { 
                               		  if(index<itemHistoryCountList.size()) {
                               			itemHistorySize = itemHistoryCountList.get(index);
										
                                   		index++;
                               		  }
									  if(idx!=0) { %>
											<tr class="border">
		                                     	<td colspan="5">&nbsp;</td>
		                                    </tr>
									<%  }
                               		
                               	   %>
                               		<tr class="first">
                                    <td rowspan="<%=itemHistorySize %>"><%=log.getSku()%></td>

                                    <td rowspan="<%=itemHistorySize %>">
                                        <%=log.getProductDesc()%>
                                    </td>
                                   <% } else {%>
                                   	<tr>
                                   <%} %>
                                     <td>
                                         <% if(messKey==null){ %>
											  <%=sid.getValue()%>
										 <% } else { %>
										 <app:storeMessage key="<%=messKey%>"
										    arg0="<%=sid.getArg0()%>" arg1="<%=sid.getArg1()%>"
										    arg2="<%=sid.getArg2()%>" arg3="<%=sid.getArg3()%>" />
										<% } %>
                                     </td>
                                     <td>
											<%
												String dateTime = ClwI18nUtil.formatDateTime(request,sid.getAddDate());
											%>
                                      
									  	<%=Utility.encodeForHTML(dateTime) %>
                                     </td>
                                     <td>
                                         <%=Utility.encodeForHTML(sid.getAddBy()) %>
                                     </td>
                                 </tr>
                                 <%
										 prevItemSku = log.getSku();
		                                 if (! log.getSku().equals(prevItemSku) || idx == itemsRemoved.size()-1) {  %>
											<tr class="border">
		                                     	<td colspan="5">&nbsp;</td>
		                                    </tr>
								 <%		 }
									}
								 %>
                                 <!-- End Repeat -->
                                
                                 
                             </tbody>
                         </table>

                     </div>
                 </div>
             </div>
           <%
			}
	  }
}
			%>    
 
                