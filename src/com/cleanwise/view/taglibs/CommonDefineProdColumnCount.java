package com.cleanwise.view.taglibs;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;


import org.apache.log4j.Category;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.ProductViewDefData;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

public class CommonDefineProdColumnCount extends CommonDisplayProductAttributesTag{
        private static final Category log = Category.getInstance(DisplayProductHeader.class);
        private String id;





        /**
         * Called on start of tag, initializes some local variables that the rest of the class
         * uses.  If re-use is requiered then these variable initilizations will need to be
         * re-implemented
         */
        public int doStartTag() throws JspException {
                request = (HttpServletRequest) pageContext.getRequest();
                session = request.getSession();
                appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

                return BodyTagSupport.EVAL_BODY_BUFFERED;
        }
        /**
         * Called on end of the tag
         * Renders the content
         */
        public int doEndTag() throws JspException {
                Integer attrCount = new Integer(getProductDefinitionsSize());
                //Expose this value as a scripting variable
        int inScope = PageContext.PAGE_SCOPE;

                pageContext.setAttribute(getId(), attrCount, inScope);

                return BodyTagSupport.EVAL_PAGE;
        }


        public String getId() {
                return id;
        }


        public void setId(String id) {
                this.id = id;
        }
}
