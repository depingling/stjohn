<%@ page import="com.cleanwise.view.utils.*" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:storeMessage key="shop.checkout.text.doubleCheckYourOrder"/>
<br>
<app:storeMessage key="shop.checkout.text.clickViewCartForChanges"/>

<% 
CleanwiseUser appUser2 = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
if ( null != appUser2 && appUser2.canMakePurchases() ) { %>
<br>
<app:storeMessage key="shop.checkout.text.clickPlaceOrderToPlaceOrder"/>
<% } %>

