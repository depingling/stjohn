/*
 * CustomerProfilingForm.java
 *
 * Created on August 12, 2003, 4:14 PM
 */

package com.cleanwise.view.forms;
import com.cleanwise.view.utils.*;
import org.apache.struts.action.ActionForm;
/**
 *
 * @author  bstevens
 */
public class CustomerProfilingForm extends ActionForm{
    
    /** Holds value of property profile. */
    private ProfileViewContainer profile;
    
    /** Creates a new instance of CustomerProfilingForm */
    public CustomerProfilingForm() {
    }
    
    /** Getter for property profile.
     * @return Value of property profile.
     *
     */
    public ProfileViewContainer getProfile() {
        return this.profile;
    }
    
    /** Setter for property profile.
     * @param profile New value of property profile.
     *
     */
    public void setProfile(ProfileViewContainer profile) {
        this.profile = profile;
    }
    
}
