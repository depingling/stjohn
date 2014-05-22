<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<logic:messagesPresent message="false">
	<div class="errorMessage">
		<p>
			<app:messages id="error" message="false">
				<bean:write name="error" filter="false"/><br/>
			</app:messages>	
		</p>
	</div>
	</br>
</logic:messagesPresent>
<logic:messagesPresent message="true">
	<div class="infoMessage">
		<p>
			<app:messages id="message" message="true">
				<bean:write name="message" filter="false"/><br/>
			</app:messages>
		</p>
	</div>
	</br>
</logic:messagesPresent>
