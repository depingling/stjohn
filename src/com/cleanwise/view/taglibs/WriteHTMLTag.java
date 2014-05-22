/*
 * WriteHTMLTag.java
 *
 * Created on April 13, 2004, 4:29 PM
 */

package com.cleanwise.view.taglibs;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;
/**
 *Writes a property as simple HTML, similar to a pre tag would; However when a pre tag has issues with
 *formatting as Internet Explorer sometimes does if it is within a cell this tag may be used instead.
 * @author  bstevens
 */
public class WriteHTMLTag extends TagSupport {
    
    
    
    /**
     * Holds value of property name.
     */
    private String name;
    
    /**
     * Holds value of property scope.
     */
    private String scope;
    
    /**
     * Holds value of property property.
     */
    private String property;
    
    /**
     * Holds value of property ignore.
     */
    private boolean ignore;
    
    // --------------------------------------------------------- Public Methods
    
    
    /**
     * Process the start tag.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        
        // Look up the requested bean (if necessary)
        Object bean = null;
        if (ignore) {
            if (RequestUtils.lookup(pageContext, name, scope) == null){
                return (SKIP_BODY);  // Nothing to output
            }
        }
        
        // Look up the requested property value
        Object value = RequestUtils.lookup(pageContext, name, property, scope);
        if (value == null){
            return (SKIP_BODY);  // Nothing to output
        }
        
        String output = value.toString();
        //first do the jakarta filter:
        output = ResponseUtils.filter(output);
        String[] search = {"\n"};
        String[] replace = {"<BR>"};
        for(int j=0; j < search.length;j++){
            output = output.replaceAll(search[j], replace[j]);
        }
        
        output.toCharArray();
        
        ResponseUtils.write(pageContext, output);
        
        // Continue processing this page
        return (SKIP_BODY);
        
    }
    
    
    /**
     * Release all allocated resources.
     */
    public void release() {
        super.release();
        ignore = false;
        name = null;
        property = null;
        scope = null;
    }
    
    /**
     * Getter for property name.
     * @return Value of property name.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Setter for property name.
     * @param name New value of property name.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Getter for property scope.
     * @return Value of property scope.
     */
    public String getScope() {
        return this.scope;
    }
    
    /**
     * Setter for property scope.
     * @param scope New value of property scope.
     */
    public void setScope(String scope) {
        this.scope = scope;
    }
    
    /**
     * Getter for property property.
     * @return Value of property property.
     */
    public String getProperty() {
        return this.property;
    }
    
    /**
     * Setter for property property.
     * @param property New value of property property.
     */
    public void setProperty(String property) {
        this.property = property;
    }
    
    /**
     * Getter for property ignore.
     * @return Value of property ignore.
     */
    public boolean setIgnore() {
        return this.ignore;
    }
    
    /**
     * Setter for property ignore.
     * @param ignore New value of property ignore.
     */
    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }
    
}
