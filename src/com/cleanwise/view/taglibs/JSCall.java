package com.cleanwise.view.taglibs;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 *
 * @author Alexander Chikin
 * Date: 11.08.2006
 * Time: 16:38:50
 *
 */
public class JSCall extends BodyTagSupport {
    public String param[];
    public String f_name;

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String[] getParam() {
        return param;
    }

    public void setParam(String[] param) {
        this.param = param;
    }




    public int doEndTag() throws JspException {
        JspWriter out=pageContext.getOut();

        try {

            out.print("<a href=\"#\" onclick= \"return "+ f_name+"(");
            for(int i=0;i<param.length;i++)
            {
            if (i==0 && param.length==1) out.print("'");
            out.print(param[i]);
            if(i!=param.length-1 && param.length>1)
            out.print("','");

            }
            if (param.length>0) out.print("'");
            out.println(");\">"+getBodyContent().getString().trim()+"</a>");
        } catch (IOException e) {
            e.printStackTrace();
        }

       return   BodyTagSupport.EVAL_PAGE;
    }


}
