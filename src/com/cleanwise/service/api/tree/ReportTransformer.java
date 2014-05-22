package com.cleanwise.service.api.tree;

public interface ReportTransformer {
    public String transform(Object value);

    public Object transform(String value, Class type);
}
