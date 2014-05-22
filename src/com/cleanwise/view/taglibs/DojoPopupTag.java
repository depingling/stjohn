package com.cleanwise.view.taglibs;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.MenuItemView;
import com.cleanwise.view.utils.Constants;
import org.apache.commons.beanutils.PropertyUtils;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;


public class DojoPopupTag extends TagSupport {

    private static String className = "DojoPopupTag";

    String name;
    String property;
    String id;
    String targets;
    private MenuItemView menuData;
    private String width;


    public int doStartTag() throws JspException {

        Object menuObj;

        Object bean = pageContext.findAttribute(Utility.strNN(getName()));
        if (bean == null) {
            String message = "Bean not found";
            error(message, new Exception(message));
            return SKIP_BODY;
        }
        String property = getProperty();
        if (property == null) {
            menuObj = bean;
        } else {
            try {
                menuObj = PropertyUtils.getProperty(bean, property);
            } catch (Exception e) {
                error(e.getMessage(), e);
                return SKIP_BODY;
            }
        }

        if (menuObj == null) {
            String message = "Bean not found";
            error(message, new Exception(message));
            return SKIP_BODY;
        }


        if (!(menuObj instanceof MenuItemView)) {
            String message = "Class " + menuObj.getClass() + " not supported.";
            error(message, new Exception(message));
            return SKIP_BODY;
        }

        setMenuData((MenuItemView) menuObj);

        return BodyTagSupport.EVAL_PAGE;
    }


    public int doEndTag() throws JspException {

        JspWriter out = pageContext.getOut();
        try {
            writeTo(out, getMenuData());
        } catch (Exception e) {
            error(e.getMessage(), e);
            return SKIP_BODY;
        }

        return EVAL_PAGE;
    }

    private void writeTo(JspWriter out, MenuItemView menuData) throws IOException {
        StringBuffer buffer = new StringBuffer();
        buildDojoPopupStr(menuData, buffer,  0);
        out.write(buffer.toString());
    }


    public void buildDojoPopupStr(MenuItemView item, StringBuffer menuContent,  int level) {
    	int uid;
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
	    Integer uidI = (Integer) request.getAttribute("DojoPopupTag.UID");
		if(uidI == null){
		   uidI = new Integer(0);
		}
		uid = uidI.intValue();

        uid++;
        uidI = new Integer(uid);
		request.setAttribute("DojoPopupTag.UID",uidI);


        if (item.getSubItems() != null && !item.getSubItems().isEmpty()) {

            Iterator it = item.getSubItems().iterator();


            level++;

            if (Constants.ROOT.equals(item.getKey())) {
                menuContent.append("\n<div dojoType=\"clw.NewXpedx.Menu\"\n" +
                        "     id=\"" + getId() + "\"\n" +
                        "     leftClickToOpen=\"true\"  \n" +
                        "     targetNodeIds=\"" + getTargets() + "\"\n" +
                        "     width=\""+Utility.strNN(getWidth())+"\"\n"+
                        "     style=\"display: none;\">");
            } else {
                menuContent.append("<div dojoType=\"clw.NewXpedx.Menu\"\n" +
                        "     id=\"" + getId() + "_" + uid + "\"\n" +
                        "     width=\""+Utility.strNN(getWidth())+"\"\n"+
                        "     style=\"display: none;\">");
            }

            while (it.hasNext()) {

                MenuItemView menuItem = (MenuItemView) it.next();

                if (menuItem.getSubItems() != null
                        && !menuItem.getSubItems().isEmpty()) {
                    menuContent.append("<div dojoType=\"clw.NewXpedx.PopupMenuItem\" onClick=\"window.location='"+menuItem.getLink()+"'\">\n");
                    menuContent.append("<span>" + menuItem.getName() + "</span>");
                } else {

                    menuContent.append("\n<div dojoType=\"clw.NewXpedx.MenuItem\" onClick=\"window.location='"+menuItem.getLink()+"'\">" + menuItem.getName() + "</div>");

                }


                buildDojoPopupStr(menuItem, menuContent, level);

                if (menuItem.getSubItems() != null
                        && !menuItem.getSubItems().isEmpty()) {
                    menuContent.append("</div>");
                }

            }
            menuContent.append("</div>");
        }


    }


    public static String getClassName() {
        return className;
    }

    public static void setClassName(String className) {
        DojoPopupTag.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTargets() {
        return targets;
    }

    public void setTargets(String targets) {
        this.targets = targets;
    }

    public void setMenuData(MenuItemView menuData) {
        this.menuData = menuData;
    }


    public MenuItemView getMenuData() {
        return menuData;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void release() {
        this.targets = null;
        this.menuData = null;
        this.name = null;
        this.id = null;
        this.property = null;
        this.width = null;
    }

    /**
     * Error logging
     *
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private static void error(String message, Exception e) {
        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);

    }

}
