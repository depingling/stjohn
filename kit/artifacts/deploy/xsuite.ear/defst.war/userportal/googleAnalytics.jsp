<%@ page import="com.cleanwise.service.api.value.StoreData" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%
CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
if (appUser != null) {                                  // Check appUser
        StoreData userStore = appUser.getUserStore();
        if (userStore != null) {                            // Check userStore
            String googleAnalyticsId = Utility.getPropertyValue(
                        userStore.getMiscProperties(),
                        RefCodeNames.PROPERTY_TYPE_CD.GOOGLE_ANALYTICS_ID);
                if (Utility.isSet(googleAnalyticsId) == true) { // Check GoogleAnalyticsId property
%>
<script type="text/javascript">
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', '<%=googleAnalyticsId%>']);
  _gaq.push(['_setDomainName', 'none']);
  _gaq.push(['_setAllowLinker', true]);
  _gaq.push(['_setCustomVar',1,'storeid','<%=userStore.getStoreId()%>',2]);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script><%
                } // Check GoogleAnalyticsId property 
    }     // Check appUser
}         // Check userStore
%>

