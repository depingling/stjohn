
package com.cleanwise.view.utils;

import org.apache.struts.util.MessageResources;

public class ReportRequest {

    public String mDateFormat = ""; 
    public CleanwiseUser mUser = null;
    public MessageResources mMessageRes = null;

    public ReportRequest(CleanwiseUser pUser,
			 MessageResources pMessageRes,
			 String pDateFormat ) {
	mDateFormat = pDateFormat; 
	mUser = pUser;
	mMessageRes = pMessageRes;
    }

}
