package com.cleanwise.service.api.value;

import java.io.Serializable;
import java.util.ArrayList;


public class InboundEmailData implements Serializable {

    //Do not remove or modify next line. It would break object DB saving
    private static final long serialVersionUID = -1442244815699479212L;

    private String subject;
    private String text;
    private String from;
    private ArrayList<Attachment> attachments;

    public InboundEmailData() {
        this.attachments = new ArrayList<Attachment>();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }


}
