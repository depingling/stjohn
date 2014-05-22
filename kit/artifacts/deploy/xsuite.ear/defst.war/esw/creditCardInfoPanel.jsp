<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%
String sourcePanel = request.getParameter("checkOutPanel");
boolean isCheckOutPanel = false;
if(Utility.isSet(sourcePanel)) {
	try{
		isCheckOutPanel = Boolean.valueOf(sourcePanel).booleanValue();
	}finally{}
}
%>
<%if(Boolean.parseBoolean(String.valueOf(request.getSession(false).getAttribute(Constants.PAYMETRICS_CC)))){ %>
<div class="boxWrapper smallMargin squareCorners">
        <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
        <div class="content">
            <div class="left clearfix">
                <h2>Credit Card Information</h2>
                
                <hr>
                <iframe scrolling="no" height="300" frameborder="1" width="420" src="#"></iframe>
            </div>
        </div>
        <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
    						
</div>
<%}%>