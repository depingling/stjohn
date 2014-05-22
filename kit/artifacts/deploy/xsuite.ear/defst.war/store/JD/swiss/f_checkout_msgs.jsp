
<bean:define id="theForm" name="CHECKOUT_FORM" type="com.cleanwise.view.forms.CheckoutForm"/>

<%
    if (theForm != null && theForm.getWarningMessages() != null) {

        java.util.List warnings = theForm.getWarningMessages();
        theForm.setWarningMessages(null);
        
        for (int widx = 0; widx < warnings.size(); widx++) {
            String warningMsg = (String) warnings.get(widx);
%>

<table cellpadding="0" cellspacing="0" align="center" 
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
<tr>
<td class="rederror" align="center"><%= warningMsg %></td>
</tr>
</table>

<% } /* end of loop on warnings */ 

} %>

