package com.cleanwise.view.taglibs;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.MenuItemView;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.i18n.ClwI18nUtil;
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


public class DojoPopupProgramamticTag extends TagSupport {

    private static String className = "DojoPopupProgramamticTag";

    private static final String TARGET_SUFF = "Target";
    private static final String UID = "DojoPopupProgramamticTag.UID";
    private static final String OBJECT_SUFF = "Obj";
    public static final String ONMOUSEOVER = "ONMOUSEOVER";
    public static final String ONLOAD = "ONLOAD";

    String name;
    String property;
    String id;
    String link;
    String key;
    String module;
    private MenuItemView menuData;
    private String width;
    private String targetTabStyle;
    private String startupEvent;


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
                return SKIP_PAGE;
            }
        }

        if (menuObj == null) {
            String message = "Bean not found";
            error(message, new Exception(message));
            return SKIP_PAGE;
        }

        if (!(menuObj instanceof MenuItemView)) {
            String message = "Class " + menuObj.getClass() + " not supported.";
            error(message, new Exception(message));
            return SKIP_PAGE;
        }

        if(!starupEventSupport(getStartupEvent())){
            String message = "Startup Event " + getStartupEvent() + " not supported.";
            error(message, new Exception(message));
            return SKIP_PAGE;
        }

        setMenuData((MenuItemView) menuObj);

        return BodyTagSupport.EVAL_PAGE;
    }

    private boolean starupEventSupport(String startupEvent) {
        return !Utility.isSet(startupEvent) ||
                (Utility.isSet(startupEvent) &&
                        (ONLOAD.equals(startupEvent) || ONMOUSEOVER.equals(startupEvent)));
    }


    public int doEndTag() throws JspException {

        JspWriter out = pageContext.getOut();
        try {
            writeTo(out, getMenuData());
        } catch (Exception e) {
            error(e.getMessage(), e);
            return SKIP_PAGE;
        }

        return EVAL_PAGE;
    }

    private void writeTo(JspWriter out, MenuItemView menuData) throws IOException {
        StringBuffer buffer = new StringBuffer();
        StringBuffer djBuffer = new StringBuffer();
        String mainMenuObj = getMenuObject(getUID()+1);
        createMenuObjVar(buffer,mainMenuObj);
        creteItem(buffer,mainMenuObj);
        buildDojoPopupStr(menuData, djBuffer,  0);
        buffer.append(createJSFunction(djBuffer,mainMenuObj));
        //log("buffer=> "+buffer.toString());
        out.write(buffer.toString());
    }

    private void creteItem(StringBuffer buffer, String mainMenuObj) {
        buffer.append("<td id=\"");
        buffer.append(getTarget());
        buffer.append("\" class=\"");
        buffer.append(Utility.strNN(getTargetTabStyle()));
        buffer.append("\"");
        if(Utility.isSet(getLink())) {
            buffer.append("\" " + " onclick=\"goto('");
            buffer.append(getLink());
            buffer.append("');\"");
        }
        String startupEvent = getStartupEvent();

        if(ONMOUSEOVER.equals(startupEvent)){
            buffer.append(" onmouseover=\"if(!");
            buffer.append(mainMenuObj).append(" && ready");
            buffer.append("){ create");
            buffer.append(mainMenuObj);
            buffer.append("(); ");
            buffer.append(mainMenuObj);
            buffer.append("._openMyselfByTarget(this); }\"");
        }

        buffer.append("> ");
        buffer.append("&nbsp;&nbsp;");
        buffer.append(ClwI18nUtil.getMessage((HttpServletRequest) pageContext.getRequest(), getKey(), null));
        buffer.append("&nbsp;&nbsp;");
        buffer.append("</td> ");

    }

    private void createMenuObjVar(StringBuffer buffer,String  menuObj) {

        buffer.append("<script language=\"JavaScript1.2\"> " + "var ");
        buffer.append(menuObj);
        buffer.append(";</script>");
    }

    private StringBuffer createJSFunction(StringBuffer buffer, String menuObj) {

        buffer.insert(0, "<script language=\"JavaScript1.2\"> function create" + menuObj + "() {");
        buffer.insert(buffer.length(), "}");

        String startupEvent = getStartupEvent();
        if (!Utility.isSet(startupEvent) || ONLOAD.equals(startupEvent)) {
            buffer.insert(buffer.length(), "dojo.addOnLoad(create" + menuObj + "());");
        }

        buffer.insert(buffer.length(), "</script>");

        return buffer;
    }

    public int getUID() {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        Integer uidI = (Integer) request.getAttribute(UID);
        if (uidI == null) {
            uidI = 0;
        }
        return uidI;
    }

    public String buildDojoPopupStr(MenuItemView item, StringBuffer menuContent,  int level) {


	    Integer uid = getNextUID();

        String menuObj = getMenuObject(uid);

        if (item.getSubItems() != null && !item.getSubItems().isEmpty()) {

            Iterator it = item.getSubItems().iterator();


            level++;

            if (Constants.ROOT.equals(item.getKey())) {
                menuContent.append(menuObj);
                menuContent.append(" = new ");
                menuContent.append(getModule());
                menuContent.append(".Menu({width:\"");
                menuContent.append(Utility.strNN(getWidth()));
                menuContent.append("\", leftClickToOpen:true, targetNodeIds:[\"");
                menuContent.append(getTarget());
                menuContent.append("\"], targetTabStyle:\"");
                menuContent.append(getTargetTabStyle());
                menuContent.append("\", id:\"");
                menuContent.append(getId());
                menuContent.append("\"});");
            } else {
                menuContent.append(menuObj);
                menuContent.append(" = new ");
                menuContent.append(getModule());
                menuContent.append(".Menu({width:\"");
                menuContent.append(Utility.strNN(getWidth()));
                menuContent.append("\", id:\"");
                menuContent.append(getId());
                menuContent.append("_");
                menuContent.append(uid);
                menuContent.append("\"});");
            }

            while (it.hasNext()) {

                MenuItemView menuItem = (MenuItemView) it.next();

                if (menuItem.getSubItems() != null
                        && !menuItem.getSubItems().isEmpty()) {
                 } else {

                    menuContent.append(menuObj);
                    menuContent.append(".addChild(new ");
                    menuContent.append(getModule());
                    menuContent.append(".MenuItem({label:\"");
                    menuContent.append(menuItem.getName());
                    menuContent.append("\", onClick:function(){ goto('");
                    menuContent.append(menuItem.getLink());
                    menuContent.append("')}}));");
                }


                String popupObj = buildDojoPopupStr(menuItem, menuContent, level);

                if (menuItem.getSubItems() != null
                        && !menuItem.getSubItems().isEmpty()) {

                    menuContent.append(menuObj);
                    menuContent.append(".addChild(new ");
                    menuContent.append(getModule());
                    menuContent.append(".PopupMenuItem({label:\"");
                    menuContent.append(menuItem.getName());
                    menuContent.append("\", popup:");
                    menuContent.append(popupObj);
                    menuContent.append(",onClick:function(){ goto('");
                    menuContent.append(menuItem.getLink());
                    menuContent.append("')}}));");
                }

            }
        }

      return menuObj;
    }

    private String getMenuObject(Integer uid) {
      return   getId()+uid+OBJECT_SUFF;
    }

    private Integer getNextUID() {
        incrementUID();
        return getUID();
    }

    private void incrementUID() {
        Integer uid =getUID();
        uid++;
        HttpServletRequest request = ((HttpServletRequest) pageContext.getRequest());
        request.setAttribute(UID,uid);
    }

    private String getTarget() {
       return getId()+TARGET_SUFF;
    }


    public static String getClassName() {
        return className;
    }

    public static void setClassName(String className) {
        DojoPopupProgramamticTag.className = className;
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

    public String getTargetTabStyle() {
        return targetTabStyle;
    }

    public void setTargetTabStyle(String targetTabStyle) {
        this.targetTabStyle = targetTabStyle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }


    public String getStartupEvent() {
        return startupEvent;
    }

    public void setStartupEvent(String startupEvent) {
        this.startupEvent = startupEvent;
    }

    public void release() {
        this.menuData = null;
        this.name = null;
        this.id = null;
        this.property = null;
        this.width = null;
        this.targetTabStyle = null;
        this.link = null;
        this.key = null;
        this.module = null;
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
