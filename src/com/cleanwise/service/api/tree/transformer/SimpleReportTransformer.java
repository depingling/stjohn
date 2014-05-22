package com.cleanwise.service.api.tree.transformer;

import com.cleanwise.service.api.tree.ReportTransformer;

public class SimpleReportTransformer implements ReportTransformer {
    public String transform(Object value) {
        return String.valueOf(value);
    }

    /**
     * @see com.cleanwise.service.api.tree.ReportTransformer#transform(java.lang.String,
     *      java.lang.Class)
     */
    public Object transform(String value, Class type) {
        // TODO Auto-generated method stub
        return null;
    }

}
