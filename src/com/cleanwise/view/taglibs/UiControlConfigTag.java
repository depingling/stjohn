package com.cleanwise.view.taglibs;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.wrapper.UiControlViewWrapper;
import org.apache.log4j.Logger;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.*;

public class UiControlConfigTag extends BodyTagSupport {

    private static final Logger log = Logger.getLogger(UiControlConfigTag.class);

    protected String name;
    protected String template;
    protected String targets;
    protected UiControlViewWrapper controlWrapper;
    private UiPageConfigTag ancestor;
    protected boolean noDefault = false;
    private ArrayList<PairView> elementsBody;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public void setControlWrapper(UiControlViewWrapper controlWrapper) {
        this.controlWrapper = controlWrapper;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UiControlViewWrapper getControlWrapper() {
        return controlWrapper;
    }

    public UiControlView getControl() {
        return controlWrapper.getControlView();
    }

    public int doStartTag() throws JspException {

        UiPageConfigTag ancestorTag = (UiPageConfigTag) findAncestorWithClass(this, UiPageConfigTag.class);
        if (ancestorTag == null) {
            String message = "UiControl tag must be nested within a UiPageTag tag.";
            log.error(message, new Exception(message));
            throw new JspException(message);
        }

        UiPageView pageObj = ancestorTag.getPageWrapper().getUiPageView();
        UiControlView controlObj = Utility.getUiControl(pageObj.getUiControls(), this.name);
        if (controlObj == null && !ancestorTag.isConfigMode()) {
            String message = "Page '" + pageObj.getUiPage().getShortDesc() + "' page does not contain control with name " + this.name;
            log.error(message, new Exception(message));
            return SKIP_BODY;
        } else if (controlObj == null) {
            controlObj = Utility.createUiControl(ancestorTag.getPageWrapper().getUiPage(), this.name, RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE);
            pageObj.getUiControls().add(controlObj);
        }

        setAncestor(ancestorTag);
        setControlWrapper(new  UiControlViewWrapper(controlObj));
        setElementsBody(new ArrayList<PairView>());

        return BodyTagSupport.EVAL_BODY_BUFFERED;

    }

    public int doEndTag() throws JspException {
        if (getAncestor().isConfigMode()) {
            return doConfig();
        } else {
            return doView();
        }
    }

    public int doView() throws JspException {
        try {

            JspWriter out = pageContext.getOut();
            if (RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE.equals(getControl().getUiControlData().getStatusCd())) {
                out.print(getBodyContent().getString());
            } else {

                List<PairView> elsBody = getElementsBody();
                for (PairView elBody : elsBody) {
                    String elementField = (String) elBody.getObject2();
                    out.print("\r\n");
                    out.print(elementField);
                }

                if (Utility.isSet(getTemplate())) {
                    out.print(getTemplate());
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SKIP_BODY;
        } finally {
            this.release();
        }

        return EVAL_PAGE;
    }

    public int doConfig() throws JspException {
        try {

            JspWriter out = pageContext.getOut();

            String property;

            property = "uiPage.uiControlWrapper(" + this.name + ").uiControlData.shortDesc";
            out.println("<input type=\"hidden\"  name=\"" + property + "\" value=\"" + getControl().getUiControlData().getShortDesc() + "\"/>");

            property = "uiPage.uiControlWrapper(" + this.name + ").uiControlData.statusCd";
            out.println("<input id=\"" + getControl().getUiControlData().getShortDesc() + "\" type=\"hidden\"  name=\"" + property + "\" value=\"" + getControl().getUiControlData().getStatusCd() + "\"/>");

            out.println(getBodyContent().getString());

            out.println(createMenu());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SKIP_BODY;
        } finally {
            this.release();
        }

        return EVAL_PAGE;

    }

    private String createMenu() {

        StringBuffer script = new StringBuffer();

        script.append("<script language=\"Javascript\" type=\"text/javascript\">");

        script.append("\ndojo.addOnLoad(function(){");

        UiControlView control = getControl();

        String targeNodeIds = "";
        int i = 0;

        if (!Utility.isSet(targets)) {
            
            for (Object oTargetData : getControlWrapper().getUiControlElements()) {
                String target = ((UiControlElementData)oTargetData).getShortDesc();
                if (i > 0) {
                    targeNodeIds += ",";
                }
                targeNodeIds += "\"" + target + "\"";
                i++;
            }
        } else {
            String[] targetList = Utility.parseStringToArray(targets, ",");
            for (String target : targetList) {
                if (i > 0) {
                    targeNodeIds += ",";
                }
                targeNodeIds += "\"" + target + "\"";
                i++;
            }
        }

        script.append("\nmenu = new clw.admin2.UiStatusControlMenu({");
        script.append("targetNodeIds:[").append(targeNodeIds).append("]");
        script.append(",");
        script.append("functionId:\"").append(control.getUiControlData().getShortDesc()).append("\"");
        script.append(",");
        script.append("targetTabStyle:\"targetBox\"" + (i > 1 ? ", targetView:\"group\"" : ""));
        script.append("});");

        script.append("\nmenu.addChild(new clw.admin2.UiStatusControlMenuItem({label:\"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;\", onClick:function(){ document.getElementById('" + control.getUiControlData().getShortDesc() + "').value=\"" + RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE + "\"}}))");
        script.append("\nmenu.addChild(new clw.admin2.UiStatusControlMenuItem({label:\"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;\", onClick:function(){ document.getElementById('" + control.getUiControlData().getShortDesc() + "').value=\"" + RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE + "\"}}))");
        if (!noDefault) {
            script.append("\nmenu.addChild(new clw.admin2.UiStatusControlMenuItem({label:\"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;\", onClick:function(){ document.getElementById('" + control.getUiControlData().getShortDesc() + "').value=\"" + RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT + "\"}}))");
        }

        script.append("\nmenu.startup();");

        script.append("\n});");
        script.append("\n</script>");

        return script.toString();

    }

    public void release() {
        super.release();
        name = null;
        controlWrapper = null;
        template = null;
        noDefault = false;
        targets = null;
        ancestor = null;
    }

    public void setAncestor(UiPageConfigTag ancestor) {
        this.ancestor = ancestor;
    }

    public UiPageConfigTag getAncestor() {
        return ancestor;
    }

    public boolean getNoDefault() {
        return noDefault;
    }

    public void setNoDefault(boolean noDefault) {
        this.noDefault = noDefault;
    }

    public String getTargets() {
        return targets;
    }

    public void setTargets(String targets) {
        this.targets = targets;
    }

    public void addElementBody(UiControlElementData controlElement, String body) {
        this.elementsBody.add(new PairView(controlElement, body));
    }

    public ArrayList<PairView> getElementsBody() {
        return elementsBody;
    }

    public void setElementsBody(ArrayList<PairView> elementsBody) {
        this.elementsBody = elementsBody;
    }
}
