package com.cleanwise.view.taglibs;

import java.io.IOException;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.action.Action;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.utils.Constants;

/**
 *  This tag provides Store specific Elements information for a page. example
 *  usage: <br>
 *  Get the logo configured for this account or store. <br>
 *  <app:store image="store.gif"/>
 *
 *@author     durval
 *@created    September 12, 2001
 */
public final class StorePageElementTag extends TagSupport {

    String mImage = "";
    String mPageElement = "";


    /**
     *  Sets the PageElement attribute of the CustomizeTag object
     *
     *@param  v  The new PageElement value
     */
    public void setPageElement(String v) {
        mPageElement = v;
    }


    /**
     *  Sets the Image attribute of the StorePageElementTag object
     *
     *@param  v  The new Image value
     */
    public void setImage(String v) {
        mImage = v;
    }


    /**
     *  Gets the PageElement attribute of the CustomizeTag object
     *
     *@return    The PageElement value
     */
    public String getPageElement() {
        return mPageElement;
    }


    /**
     *  Gets the Image attribute of the StorePageElementTag object
     *
     *@return    The Image value
     */
    public String getImage() {
        return mImage;
    }


    /**
     *@return                   an <code>int</code> value
     *@exception  JspException  if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {

        JspWriter writer = pageContext.getOut();

        if (mImage.length() == 0 &&
                mPageElement.length() == 0) {
            throw new JspException("StorePageElementTag: Neither image or pageElement was specified.");
        }
        if (mImage.length() > 0 &&
                mPageElement.length() > 0) {
            throw new JspException("StorePageElementTag: Both image and pageElement were specified.");
        }

        HttpSession session = pageContext.getSession();
        String val = "";
        try {
            if (mImage.length() == 0) {
                val = (String) session.getAttribute
                        ("pages.root") + "/store/"
                         + (String) session.getAttribute
                        ("pages.store.locale") + "/"
                         + (String) session.getAttribute
                        ("pages.store.prefix")
                         + "/" + mPageElement;
            }
            else {
                val = (String) session.getAttribute
                        ("pages.root") + "/store/"
                         + (String) session.getAttribute
                        ("pages.store.locale") + "/"
                         + (String) session.getAttribute
                        ("pages.store.prefix")
                         + "/images/" + mImage;
            }
            writer.print(val);
        }
        catch (Exception e) {
	    e.printStackTrace();
        }
        return (SKIP_BODY);
    }

}


