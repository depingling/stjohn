package com.cleanwise.service.api.process;

import java.io.Serializable;

/**
 */
public class ProcessSchema implements Serializable {

    private static final long serialVersionUID = -1L;

    Object[] schema;

    public ProcessSchema() {
        schema = new Object[0];
    }

    public ProcessSchema(Object[] objects) {
        this.schema = objects;
    }

    public Object[] getSchema() {
        return schema;
    }

    public void setSchema(Object[] shema) {
        this.schema = shema;
    }

    public void addStep(com.cleanwise.service.api.value.TaskView step) {
        addStep(new Object[]{step});
    }

    public void addStep(ProcessSchema step) {
        addStep(step.getSchema());
    }

    public void addStep(Object[] step) {
        Object[] tempSchema = new Object[schema.length + 1];
        System.arraycopy(schema, 0, tempSchema, 0, schema.length);
        schema = tempSchema;
        schema[schema.length - 1] = step;
    }
}
