package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;

import java.io.Serializable;

/**
 */
public class TaskView extends ValueObject {

    private String opClass;
    private String opMethod;
    private Object[] inParams;
    private Object[] outParams;
    private String name;


    public TaskView() {
    }


    public TaskView(String name,String opClass, String opMethod, Object[] inParams, Object[] outParams) {
        this.opClass = opClass;
        this.opMethod = opMethod;
        this.inParams = inParams;
        this.outParams = outParams;
        this.name = name;
    }

    public void setOpClass(String opClass) {
        this.opClass = opClass;
    }

    public void setOpMethod(String opMethod) {
        this.opMethod = opMethod;
    }

    public Object[] getInParams() {
        return inParams;
    }

    public void setInParams(Object[] inParams) {
        this.inParams = inParams;
    }

    public Object[] getOutParams() {
        return outParams;
    }

    public void setOutParams(Object[] outParams) {
        this.outParams = outParams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpClass() {
        return opClass;
    }

    public String getOpMethod() {
        return opMethod;
    }
}
