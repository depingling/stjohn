
package com.cleanwise.view.taglibs;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;
import org.apache.struts.taglib.html.*;

/**
 * Convenience base class for the various input tags for text fields.
 */

public class TextDisplayTag extends BaseInputTag {


    // ----------------------------------------------------- Instance Variables


    /**
     * Construct a new instance of this tag.
     */
    public TextDisplayTag() {

	super();
    }

    /**
     * Comma-delimited list of content types that a server processing this form
     * will handle correctly.  This property is defined only for the
     * <code>file</code> tag, but is implemented here because it affects the
     * rendered HTML of the corresponding &lt;input&gt; tag.
     */
    protected String accept = null;
    
    public String getAccept() {
	return (this.accept);
    }

    public void setAccept(String accept) {
	this.accept = accept;
    }


    /**
     * The user level for this field, default should be 0, means this field will be
     * displayed as a input field, otherwise should be just displayed as a label.
     */
    protected String level  = "0";
    
    public String getLevel() {
	return (this.level);
    }

    public void setLevel(String level) {
	this.level = level;
    }

    /**
     * The name of the bean containing our underlying property.
     */
    protected String name = Constants.BEAN_KEY;

    public String getName() {
	return (this.name);
    }

    public void setName(String name) {
	this.name = name;
    }


    /**
     * The "redisplay contents" flag (used only on <code>password</code>).
     */
    protected boolean redisplay = true;

    public boolean getRedisplay() {
        return (this.redisplay);
    }

    public void setRedisplay(boolean redisplay) {
        this.redisplay = redisplay;
    }


    /**
     * The type of input field represented by this tag, should be text
     */
    protected String type = "text";


    // --------------------------------------------------------- Public Methods


    /**
     * Generate the required input tag.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {

        StringBuffer results = new StringBuffer("");
        if("0".equals(level)) {
            // Create an appropriate "input" element based on our parameters
            results.append("<input type=\"");
            results.append(type);
            results.append("\" name=\"");
            results.append(property);
            results.append("\"");
            if (accesskey != null) {
                results.append(" accesskey=\"");
                results.append(accesskey);
                results.append("\"");
            }
            if (accept != null) {
                results.append(" accept=\"");
                results.append(accept);
                results.append("\"");
            }
            if (maxlength != null) {
                results.append(" maxlength=\"");
                results.append(maxlength);
                results.append("\"");
            }
            if (cols != null) {
                results.append(" size=\"");
                results.append(cols);
                results.append("\"");
            }
            if (tabindex != null) {
                results.append(" tabindex=\"");
                results.append(tabindex);
                results.append("\"");
            }
            results.append(" value=\"");
            if (value != null) {
                results.append(ResponseUtils.filter(value));
            } else if (redisplay || !"password".equals(type)) {
                Object value = RequestUtils.lookup(pageContext, name, property,
                                               null);
                if (value == null) {
                    value = "";
                }    
                results.append(ResponseUtils.filter(value.toString()));
            }
            results.append("\"");
            results.append(prepareEventHandlers());
            results.append(prepareStyles());
            results.append(">");
        }
        else {
            if (value != null) {
                results.append(ResponseUtils.filter(value));
            } else if (redisplay || !"password".equals(type)) {
                Object value = RequestUtils.lookup(pageContext, name, property,
                                               null);
                if (value == null) {
                    value = "";
                }    
                results.append(ResponseUtils.filter(value.toString()));
            }
        }
            
	// Print this field to our output writer
        ResponseUtils.write(pageContext, results.toString());

	// Continue processing this page
	return (EVAL_BODY_TAG);

    }


    /**
     * Release any acquired resources.
     */
    public void release() {

	super.release();
	accept = null;
	name = Constants.BEAN_KEY;
        level = new String("0");
        redisplay = true;

    }


}
