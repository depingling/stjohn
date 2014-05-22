package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;

public class NewXpedxContactUsForm extends ActionForm {

    private String from = "";

    private String email = "";

    private String phone = "";

    private String order = "";

    private String topic = "";

    private String comment = "";

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String v) {
        this.from = v;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
