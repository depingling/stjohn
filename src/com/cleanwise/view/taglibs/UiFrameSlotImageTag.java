package com.cleanwise.view.taglibs;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.UiFrameSlotView;
import com.cleanwise.view.utils.ClwCustomizer;

/**
 */
public class UiFrameSlotImageTag extends TagSupport {

    private UiFrameSlotView slot;

    public void setSlot(UiFrameSlotView v) {
        slot = v;
    }

    public UiFrameSlotView getSlot() {
        return slot;
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
            return SKIP_BODY;
        }
    }

    private int completeImageSlot(UiFrameSlotView slot) {
        JspWriter out = pageContext.getOut();
        javax.servlet.http.HttpServletRequest request = (javax.servlet.http.HttpServletRequest)pageContext.getRequest();
        byte[] image = slot.getImageData();
        if (slot.getValue() == null || slot.getValue().length() <= 0 || image.length <=0 ) {
            return SKIP_BODY;
        }

        try {
            // make temporaty file if not exists
            String
                    filename = slot.getValue(),
                    storePrefix = (String) request.getSession().getAttribute("pages.store.prefix");

            if(storePrefix == null){

            }
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
            result.append("<img src=\"");
            result.append(imgSrc);
            result.append("\">");

            out.println(result);
        } catch (Exception e) {
            return SKIP_BODY;
        }
        return EVAL_PAGE;
    }

    public void release() {
        super.release();
    }

}
