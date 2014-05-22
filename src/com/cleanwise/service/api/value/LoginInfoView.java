
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

    /**
 * Title:        LoginInfoView
 * Description:  This is a ViewObject class for UI.
 * Purpose:      
 * @author       Generated Code from XSL file ViewObject.xsl
 * @version      1.0
 */

import java.lang.*;
import com.cleanwise.service.api.framework.*;

import org.w3c.dom.*;

/**
 * <code>LoginInfoView</code> is a ViewObject class for UI.
 */
public class LoginInfoView extends ValueObject  {

    private static final long serialVersionUID = -1173177252708577612L;
    private String mUserName;
    private String mPassword;
    private String mAccessToken;
    private boolean mIvrLogin;

    /**
     * Constructor.
     */
    public LoginInfoView () {
        mUserName = "";
        mPassword = "";
        mAccessToken = "";
    }

    /**
     * Constructor.
     */
    public LoginInfoView(String pUserName, String pPassword, String pAccessToken, boolean pIvrLogin) {

        mUserName = pUserName;
        mPassword = pPassword;
        mAccessToken = pAccessToken;
        mIvrLogin = pIvrLogin;
        
    }

    /**
     * Creates a new LoginInfoView
     *
     * @return Newly initialized LoginInfoView object.
     */
    public static LoginInfoView createValue() {
        return new LoginInfoView();
    }

    /**
     * Returns a String representation of the value object
     *
     * @return The String representation of this LoginInfoView object
     */
    public String toString() {
        return "[" +
                "UserName=" + mUserName +
                ", Password=" + "*******" +
                ", AccessToken=" + mAccessToken +
                ", IvrLogin=" + mIvrLogin +
               "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {

    	Element root = doc.createElement("LoginInfo");
	    root.setAttribute("Id", String.valueOf(mUserName));

	    Element node;

        node = doc.createElement("Password");
        node.appendChild(doc.createTextNode(String.valueOf(mPassword)));
        root.appendChild(node);

        node = doc.createElement("AccessToken");
        node.appendChild(doc.createTextNode(String.valueOf(mAccessToken)));
        root.appendChild(node);

        node = doc.createElement("IvrLogin");
        node.appendChild(doc.createTextNode(String.valueOf(mIvrLogin)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public LoginInfoView copy()  {

      LoginInfoView obj = new LoginInfoView();

      obj.setUserName(mUserName);
      obj.setPassword(mPassword);
      obj.setAccessToken(mAccessToken);
      obj.setIvrLogin(mIvrLogin);
      

      return obj;
    }

    
    /**
     * Sets the UserName property.
     *
     * @param pUserName <code>String</code> to use to update the property.
     */
    public void setUserName(String pUserName) {
        this.mUserName = pUserName;
    }
    /**
     * Retrieves the UserName property.
     *
     * @return String containing the UserName property.
     */
    public String getUserName() {
        return mUserName;
    }


    /**
     * Sets the Password property.
     *
     * @param pPassword <code>String</code> to use to update the property.
     */
    public void setPassword(String pPassword) {
        this.mPassword = pPassword;
    }
    /**
     * Retrieves the Password property.
     *
     * @return String containing the Password property.
     */
    public String getPassword() {
        return mPassword;
    }


    /**
     * Sets the AccessToken property.
     *
     * @param pAccessToken <code>String</code> to use to update the property.
     */
    public void setAccessToken(String pAccessToken) {
        this.mAccessToken = pAccessToken;
    }
    /**
     * Retrieves the AccessToken property.
     *
     * @return String containing the AccessToken property.
     */
    public String getAccessToken() {
        return mAccessToken;
    }


    /**
     * Sets the IvrLogin property.
     *
     * @param pIvrLogin <code>boolean</code> to use to update the property.
     */
    public void setIvrLogin(boolean pIvrLogin) {
        this.mIvrLogin = pIvrLogin;
    }
    /**
     * Retrieves the IvrLogin property.
     *
     * @return boolean containing the IvrLogin property.
     */
    public boolean getIvrLogin() {
        return mIvrLogin;
    }



}
