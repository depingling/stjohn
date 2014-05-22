package com.cleanwise.view.logic;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.view.forms.EmailForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

/**
 *  <code>EmailLogic</code> implements the logic needed to manipulate form based
 *  email requests.
 *
 *@author     durval
 *@created    October 10, 2001
 */
public class EmailLogic {

    /**
     *  Pick out the information from the form to send an email.
     *
     *@param  request
     *@param  form
     *@exception  Exception
     */
    public static ActionErrors processEmailForm(HttpServletRequest request,
            ActionForm form)
             throws Exception {
        ActionErrors lEmailErrors = new ActionErrors();
        String toEmail = null;         
        if(request.getSession()!=null && request.getSession().getAttribute(Constants.APP_USER)!=null){
            CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
            if(appUser.getUserStore() != null && appUser.getUserStore().getContactUsEmail()!=null){
                toEmail = appUser.getUserStore().getContactUsEmail().getEmailAddress();
            }
        }
        EmailForm emf = (EmailForm) form;
        emf.setFromName(I18nUtil.getUtf8Str(emf.getFromName()));
        emf.setFromMessage(I18nUtil.getUtf8Str(emf.getFromMessage()));
        lEmailErrors = verifyNeededFields(emf);
        if (lEmailErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lEmailErrors;
        }
        APIAccess factory = new APIAccess();
        EmailClient emc = factory.getEmailClientAPI();
        String subj = "Email request from: " + emf.getFromName() + " <" + emf.getFromEmail() + ">";
        
        if(toEmail==null){
            toEmail = emc.getDefaultEmailAddress();
            subj = "NO STORE EMAIL FOUND FOR MESSAGE: "+subj;
        }
        
        String emailAddress = emf.getFromEmail();
        int i = emailAddress.indexOf(',');
        if (i > 0) {
        	emailAddress = emailAddress.substring(0, i);
    	} else{
        	i = emailAddress.indexOf(';');
        	if (i > 0) {
            	emailAddress = emailAddress.substring(0, i);
        	}
        }

        emc.send(toEmail, emailAddress,
		 subj, emf.getFromMessage(), Constants.EMAIL_FORMAT_PLAIN_TEXT,0,0);
        return lEmailErrors;
    }


    /**
     *  Verify that the needed fields are present. The assumption is that email
     *  addresses must be at least 3 characters in length.
     *
     *@param  pEmf           email form
     *@exception  Exception  Description of Exception
     */
    private static ActionErrors verifyNeededFields(EmailForm pEmf)
             throws Exception {
        ActionErrors lEmailErrors = new ActionErrors();
        if (pEmf.getFromEmail().length() < 3) {
            //throw new Exception("Invalid sender address: " + pEmf.getFromEmail());
            lEmailErrors.add("pEmf.getFromEmail()",
				  new ActionError("email.length.error",
						  "From Email"));
        }
        return lEmailErrors;
    }

}

