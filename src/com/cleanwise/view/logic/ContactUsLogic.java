package com.cleanwise.view.logic;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.view.forms.ContactUsForm;
import com.cleanwise.view.utils.Constants;

/**
 *  <code>ContactUsLogic</code> implements the logic needed to manipulate form based
 *  initial contact.
 *
 *@author     kevin
 *@created    October 30, 2001
 */
public class ContactUsLogic {

    /**
     *  Pick out the information from the form to send an email.
     *
     *@param  request
     *@param  form
     *@exception  Exception
     */
    public static ActionErrors processContactUsForm(HttpServletRequest request,
            ActionForm form)
             throws Exception {
        ActionErrors lEmailErrors = new ActionErrors();
        ContactUsForm cuf = (ContactUsForm) form;

        lEmailErrors = verifyNeededFields(cuf);
        if (lEmailErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lEmailErrors;
        }
        APIAccess factory = new APIAccess();
        String subj = "Contact Us request from: " + cuf.getFromEmail() + " <" + cuf.getFromEmail() + ">";
        String msg = "Name: "+cuf.getFirstName()+" "+
		                      cuf.getLastName()+
					 " Title: "+cuf.getTitle()+
					 " Company Name: "+cuf.getCompanyName()+
					 " Industry:  "+cuf.getIndustry()+
					 " Address: "+cuf.getAddress1()+" "+
					              cuf.getAddress2()+" "+
								  cuf.getCity()+", "+
								  cuf.getState()+" "+
								  cuf.getZip()+
                     " Phone: "+cuf.getPhone();
        EmailClient emc = factory.getEmailClientAPI();
        emc.send(cuf.getToEmail(), 
		 emc.getDefaultEmailAddress(),
		 subj, msg, Constants.EMAIL_FORMAT_PLAIN_TEXT,0,0);
        return lEmailErrors;
    }
    
	
	 public static ActionErrors processUspsContactUsForm(HttpServletRequest request,
            ActionForm form)
             throws Exception {
               
        ActionErrors lEmailErrors = new ActionErrors();
        ContactUsForm cuf = (ContactUsForm) form;
        lEmailErrors = verifyNeededFields(cuf);
       
        if (lEmailErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lEmailErrors;
        }
        APIAccess factory = new APIAccess();
        String subj = "USPS help request from: " + cuf.getFromEmail() + " <" + cuf.getFromEmail() + ">";
        String msg = "Name: "+cuf.getFirstName()+" "+
		                      cuf.getLastName()+					 
					 " Address: "+cuf.getAddress1()+" "+
					              cuf.getAddress2()+" "+
								  cuf.getCity()+", "+
								  cuf.getState()+" "+
								  cuf.getZip()+
                     " Phone: "+cuf.getPhone()+" "+
					 " Fax: "+cuf.getFax()+" "+
					 " Fedstrip #: "+cuf.getFedstrip()+" "+
					 " Type of Facility: "+cuf.getFacility()+" "+
					 " How Can We Help?: "+cuf.getHelp()+" "+
					 " Comments: "+cuf.getComments();
        EmailClient emc = factory.getEmailClientAPI();
        emc.send(cuf.getToEmail(), 
		 emc.getDefaultEmailAddress(),
		 subj, msg, Constants.EMAIL_FORMAT_PLAIN_TEXT,0,0);
        return lEmailErrors;
    }

    /**
     *  Verify that the needed fields are present. The assumption is that email
     *  addresses must be at least 3 characters in length.
     *
     *@param  pEmf           contactUs form
     *@exception  Exception  Description of Exception
     */
    private static ActionErrors verifyNeededFields(ContactUsForm pEmf)
             throws Exception {

        ActionErrors lEmailErrors = new ActionErrors();

        if (pEmf.getToEmail().length() < 3) {

            lEmailErrors.add("pEmf.getToEmail()",
				  new ActionError("email.length.error",
						  "To Email"));
        }
        if (pEmf.getFromEmail().length() < 3) {
            lEmailErrors.add("pEmf.getFromEmail()",
				  new ActionError("email.length.error",
						  "From Email"));
        }

            return lEmailErrors;
    }

}

