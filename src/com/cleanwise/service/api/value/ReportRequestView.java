
package com.cleanwise.service.api.value;

//import org.apache.struts.util.MessageResources;

public class ReportRequestView {

    public String mDateFormat = ""; 
    //public CleanwiseUser mUser = null;
    //public MessageResources mMessageRes = null;

    public ReportRequestView(//CleanwiseUser pUser,
			 //MessageResources pMessageRes,
			 String pDateFormat ) {
	mDateFormat = pDateFormat; 
	//mUser = pUser;
	//mMessageRes = pMessageRes;
    }

}
