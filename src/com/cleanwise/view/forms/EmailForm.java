package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 *  Form bean, supporting methods to get and set the following email processing
 *  properties:
 *  <ul>
 *    <li> fromName
 *    <li> fromEmail
 *    <li> fromMessage
 *    <li> toEmail
 *    <li> action
 *  </ul>
 *
 *
 *@author     durval
 *@created    October 10, 2001
 */
public class EmailForm extends ActionForm {

    private String mFromName = "";

    private String mFromEmail = "";

    private String mFromMessage = "";

    private String mAction = "";


    /**
     *  Sets the FromName attribute of the EmailForm object
     *
     *@param  v  The new FromName value
     */
    public void setFromName(String v) {
        mFromName = v;
    }


    /**
     *  Sets the FromEmail attribute of the EmailForm object
     *
     *@param  v  The new FromEmail value
     */
    public void setFromEmail(String v) {
        mFromEmail = v;
    }


    /**
     *  Sets the FromMessage attribute of the EmailForm object
     *
     *@param  v  The new FromMessage value
     */
    public void setFromMessage(String v) {
        mFromMessage = v;
    }


    /**
     *  Sets the Action attribute of the EmailForm object
     *
     *@param  v  The new Action value
     */
    public void setAction(String v) {
        mAction = v;
    }


    /**
     *  Gets the FromName attribute of the EmailForm object
     *
     *@return    The FromName value
     */
    public String getFromName() {
        return mFromName;
    }


    /**
     *  Gets the FromEmail attribute of the EmailForm object
     *
     *@return    The FromEmail value
     */
    public String getFromEmail() {
        return mFromEmail;
    }


    /**
     *  Gets the FromMessage attribute of the EmailForm object
     *
     *@return    The FromMessage value
     */
    public String getFromMessage() {
        return mFromMessage;
    }


    /**
     *  Gets the Action attribute of the EmailForm object
     *
     *@return    The Action value
     */
    public String getAction() {
        return mAction;
    }

}

