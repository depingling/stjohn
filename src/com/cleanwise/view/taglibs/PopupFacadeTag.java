package com.cleanwise.view.taglibs;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.MenuItemView;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


public class PopupFacadeTag extends TagSupport {

    private static final Logger log = Logger.getLogger(PopupFacadeTag.class);

    private static final String _defaultModule = "clw.CLW";

    String name;
    String property;
    String width;
    String module;
    MenuItemView content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public MenuItemView getContent() {
        return content;
    }

    public void setContent(MenuItemView pContent) {
        this.content = pContent;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public int doStartTag() throws JspException {

        Object obj;

        Object bean = pageContext.findAttribute(Utility.strNN(getName()));
        if (bean == null) {
            String message = "Bean not found";
            log.error(message);
            return SKIP_BODY;
        }
        String property = getProperty();
        if (property == null) {
            obj = bean;
        } else {
            try {
                obj = PropertyUtils.getProperty(bean, property);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return SKIP_PAGE;
            }
        }

        if (obj == null) {
            String message = "Bean not found";
            log.error(message);
            return SKIP_PAGE;
        }

        if (!(obj instanceof MenuItemView)) {
            String message = "Class " + obj.getClass() + " not supported.";
            log.error(message);
            return SKIP_PAGE;
        }

        MenuItemView root = (MenuItemView) obj;
        if (!MenuItemView.ATTR.ROOT.equals(root.getKey())) {
            String message = "Root item not found";
            log.error(message);
            return SKIP_PAGE;
        }

        setContent((MenuItemView) obj);

        return BodyTagSupport.EVAL_PAGE;
    }


    public int doEndTag() throws JspException {

        JspWriter out = pageContext.getOut();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        try {
            writeTo(request, out);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SKIP_BODY;
        }

        return EVAL_PAGE;
    }

    private void writeTo(HttpServletRequest request, JspWriter out) throws IOException {

        StringBuffer buffer = new StringBuffer();
        creteFacade(request, buffer);
        //log.info("buffer=> " + buffer.toString());
        out.write(buffer.toString());
    }

    private Object createJS(StringBuffer buffer, MenuItemView pContent) {

        buffer.append("<script language=\"JavaScript1.2\">");
        buffer.append("dojo.addOnLoad(function(){ new " + _defaultModule + ".PopupFacade({descriptor:");
        buffer.append(createJsDescriptor(pContent));
        buffer.append("});");
        buffer.append("});");
        buffer.append("</script>");

        return buffer;
    }

    private void creteFacade(HttpServletRequest request, StringBuffer buffer) {

        MenuItemView content = getContent();

        buffer.append("<table width=\"");
        buffer.append(Utility.strNN(getWidth()));
        buffer.append("\" id=\"");
        buffer.append(getId());
        buffer.append("_");
        buffer.append(content.getKey());
        buffer.append("\">");

        buffer.append("<tr>");
        buffer.append("<td>");

        buildDivMenuStr(request,
                content,
                buffer,
                getId(),
                0);

        buffer.append("</td>");
        buffer.append("</tr>");

        buffer.append("</table>");

        createJS(buffer, content);

    }


    private void buildDivMenuStr(HttpServletRequest pRequest,
                                MenuItemView pItem,
                                StringBuffer pBuffer,
                                String pUid,
                                int pLevel) {


        String displayStatus = pItem.getDisplayStatus();

        if (!MenuItemView.DISPLAY_STATUS.UNAVAILABLE.equals(displayStatus)) {

            String elClassName = null;

            if (MenuItemView.DISPLAY_STATUS.OPEN.equals(displayStatus) ||
                    MenuItemView.DISPLAY_STATUS.BLOCK_FOR_CLOSE.equals(displayStatus)) {
                elClassName = "display:";
            } else if (MenuItemView.DISPLAY_STATUS.CLOSE.equals(displayStatus) ||
                    MenuItemView.DISPLAY_STATUS.BLOCK_FOR_OPEN.equals(displayStatus)) {
                elClassName = "display:none";
            }

            if (!MenuItemView.ATTR.ROOT.equals(pItem.getKey())) {

                String id = Utility.strNN(pUid) + "_" + pItem.getKey();

                pBuffer.append("<div id=\"");
                pBuffer.append(id);
                pBuffer.append("\" style=\"width:100%\">");
                pBuffer.append("<div class=\"clwPopupFacade\" style=\"width:100%\">");

                StringBuffer title = new StringBuffer();
                if (pLevel == 1 && pItem.getSubItems() != null && !pItem.getSubItems().isEmpty()) {
                	title.append(pItem.getName());
                }else{
                	title.append("<a href=\"").append(pItem.getLink()).append("\"");
	                title.append("class=\"categorymenulevel_");
	                title.append(pLevel > 3 ? 3 : pLevel);
	                title.append(MenuItemView.DISPLAY_STATUS.BLOCK_FOR_CLOSE.equals(displayStatus) ? "_block" : "");
	                title.append("\"");
	                title.append(">");
	                title.append(pItem.getName());
	                title.append("</a>");
                }
                
                pBuffer.append("<div tabindex=\"0\" class=\"clwPopupFacadeTitle\">\n" +
                        "            <div class=\"dijitInline dijitArrowNode\">\n" +
                        "                <span class=\"dijitArrowNodeInner\"></span>\n" +
                        "            </div>\n" +
                        "            <div class=\"clwPopupFacadeTextNode\">");
                pBuffer.append(title.toString());
                pBuffer.append("\n" +
                        "            </div>\n" +
                        "        </div>");

                pBuffer.append("<div class=\"clwPopupFacadeContentOuter\" style=\"").append(elClassName).append("\">\n");
                pBuffer.append("<div class=\"dijitReset\">");
                pBuffer.append("<div class=\"clwPopupFacadeContentInner\" tabindex=\"-1\" style=\"width:100%\">");
            }

            pLevel++;

            if (pItem.getSubItems() != null && !pItem.getSubItems().isEmpty()) {
                for (Object o : pItem.getSubItems()) {
                    MenuItemView menuItem = (MenuItemView) o;
                    buildDivMenuStr(pRequest, menuItem, pBuffer, pUid, pLevel);
                }
            }

            if (!MenuItemView.ATTR.ROOT.equals(pItem.getKey())) {
                pBuffer.append("</div>");
                pBuffer.append("</div>\n");
                pBuffer.append("</div>\n");
                pBuffer.append("</div>\n");
                pBuffer.append("</div>");
            }
        }
    }

    private String createJsDescriptor(MenuItemView item) {

        String elDescriptor = "{";

        String displayStatus = item.getDisplayStatus();

        boolean open = MenuItemView.DISPLAY_STATUS.OPEN.equals(displayStatus)
                || MenuItemView.DISPLAY_STATUS.BLOCK_FOR_CLOSE.equals(displayStatus);

        boolean block = MenuItemView.DISPLAY_STATUS.BLOCK_FOR_OPEN.equals(displayStatus)
                || MenuItemView.DISPLAY_STATUS.BLOCK_FOR_CLOSE.equals(displayStatus);

        boolean unavailable = MenuItemView.DISPLAY_STATUS.UNAVAILABLE.equals(displayStatus);

        elDescriptor += "id:'" + getId() + "_" + item.getKey() + "'" + ", " +
                "open:" + open + ", " +
                "block:" + block + ", " +
                "unavailable:" + unavailable + ", " +
                "duration:250";

        String childes = "";
        if (item.getSubItems() != null && !item.getSubItems().isEmpty()) {
            for (Object o : item.getSubItems()) {
                MenuItemView menuItem = (MenuItemView) o;
                if (Utility.isSet(childes)) {
                    childes += ",";
                }
                childes += createJsDescriptor(menuItem);
            }

        }

        childes = "[" + childes + "]";

        elDescriptor += ",childes:" + childes;


        elDescriptor += "}";

        return elDescriptor;

    }

    public void release() {
        this.content = null;
        this.name = null;
        this.id = null;
        this.property = null;
        this.module = null;
        this.width = null;
    }
}
