package com.cleanwise.view.taglibs;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.UiFrameSlotView;
import com.cleanwise.view.utils.ClwCustomizer;

/**
 */
public class UiFrameSlotUrlTag extends TagSupport {

    private UiFrameSlotView slot;

    public void setSlot(UiFrameSlotView v) {
        slot = v;
    }

    public UiFrameSlotView getSlot() {
        return slot;
    }

    private int slotWidth;

    public void setSlotWidth(int w) {
        slotWidth = w;
    }

    public int getSlotWidth() {
        return slotWidth;
    }

    private int slotHeight;

    public void setSlotHeight(int h) {
    	slotHeight = h;
    }

    public int getSlotHeight() {
        return slotHeight;
    }

    public int doStartTag() throws JspException {
       return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        UiFrameSlotView slot = getSlot();
        if (slot == null) {
            return SKIP_BODY;
        }
        if (slot.getSlotTypeCd().equals(RefCodeNames.SLOT_TYPE_CD.IMAGE)) {
            return completeImageSlot(slot);
        } else {
            return completeTextSlot(slot);
        }
    }

    private int completeTextSlot(UiFrameSlotView slot) {
        if (slot == null || slot.getValue() == null || slot.getValue().length() <=0) {
            return SKIP_BODY;
        }
        try {
            JspWriter out = pageContext.getOut();
            StringBuffer result = new StringBuffer();
            result.append(startLinkTag(slot));
            result.append(slot.getValue());
            result.append(endLinkTag(slot));
            out.println(result);
        } catch (Exception e) {
            return SKIP_BODY;
        }
        return EVAL_PAGE;

    }


    private int completeImageSlot(UiFrameSlotView slot) {
        if (slot.getValue() == null || slot.getValue().length() <= 0 || slot.getImageData().length <=0 ) {
            return SKIP_BODY;
        }
        JspWriter out = pageContext.getOut();
        javax.servlet.http.HttpServletRequest request = (javax.servlet.http.HttpServletRequest)pageContext.getRequest();
        byte[] image = slot.getImageData();
        try {
            // make temporaty file if not exists
            String
                    filename = slot.getValue(),
                    storePrefix = (String) request.getSession().getAttribute("pages.store.prefix");

            String basename = System.getProperty("webdeploy") + "/store/" + storePrefix + "/images/" + filename;
         
            basename =  ClwCustomizer.getSIP(request,null,filename,true);
            File imageFile = new File(basename);
            if (!imageFile.exists() || imageFile.length() == 0 ) {
                ByteArrayInputStream stream = new ByteArrayInputStream(image);
                OutputStream bos = new FileOutputStream(basename);
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                }
                bos.close();
                stream.close();
            }

            // write <img> tag with <a> tag (if exists) and target=_blank attribute (if set)
            String imgSrc = ClwCustomizer.getSIP(request, filename);
            StringBuffer result = new StringBuffer();
            result.append(startLinkTag(slot));
            result.append("<img src=\"");
            result.append(imgSrc);
            result.append("\"");
            if(slotWidth!=0){
            	result.append(" width=");
            	result.append("\""+slotWidth+"\"");
            }
            if(slotHeight!=0){
            	result.append(" height=");
            	result.append("\""+slotHeight+"\"");
            }
            result.append(">");

            out.println(result);
        } catch (Exception e) {
            return SKIP_BODY;
        }
        return EVAL_PAGE;
    }

    private StringBuffer startLinkTag(UiFrameSlotView slot) {
        StringBuffer result = new StringBuffer();
        if (slot.getUrl() != null && slot.getUrl().length() > 0) {
            result.append("<a href=\"");
            String url = slot.getUrl();
            String storeDir = ClwCustomizer.getStoreDir();
            if (!(url.startsWith("http") || url.startsWith("www.") || url.startsWith("/") || url.startsWith("../") || url.startsWith(storeDir))) {
                if (url.startsWith("store")) {
                    url = url = "/" + storeDir + "/" + url;
                } else {
                    url = "/" + storeDir + "/store/" + url;
                }
            }
            result.append(url);
            result.append("\" ");
            if (Utility.isTrue(slot.getUrlTargetBlank())) {
                result.append("target=\"_blank\" ");
            }
            result.append(">");
        }
        return result;
    }

    private StringBuffer endLinkTag(UiFrameSlotView slot) {
        StringBuffer result = new StringBuffer();
        if (slot.getUrl() != null && slot.getUrl().length() > 0) {
            result.append("</a>");
        }
        return result;
    }


    public void release() {
        super.release();
    }

}
