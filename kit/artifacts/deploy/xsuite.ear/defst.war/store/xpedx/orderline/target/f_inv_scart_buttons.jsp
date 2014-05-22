<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>


<bean:define id="IPTH" type="java.lang.String" name="pages.store.images"/>
<td>
    <a href="#" class="linkButton" onclick="actionMultiSubmit('submitQty','saveInventoryCart');">
        <img src='<%=IPTH + "/b_recalculate.gif"%>' border="0"/>
        <app:storeMessage key="global.label.recalculateAndSave"/>
    </a>
</td>
<td><a href="#" class="linkButton" onclick="actionMultiSubmit('submitQty','removeInvSelected');">
    <img src='<%=IPTH + "/b_remove.gif"%>' border="0"/>
    <app:storeMessage key="global.label.removeSelected"/>
</a>
</td>
<td>
    <a href="#" class="linkButton" onclick="actionMultiSubmit('submitQty','removeInvAll');">
        <img src='<%=IPTH + "/b_remove.gif"%>' border="0"/>
        <app:storeMessage key="global.label.removeAll"/>
    </a>
</td>


