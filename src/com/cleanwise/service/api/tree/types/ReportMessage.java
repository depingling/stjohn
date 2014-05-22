package com.cleanwise.service.api.tree.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportMessage {
    /**
     * Key for bundle property.
     */
    private final String key;

    private final List params;

    /**
     * 
     * Creates an instance of the object.
     * 
     * @param key
     */
    public ReportMessage(String key) {
        this(key, null);
    }

    /**
     * 
     * Creates an instance of the object.
     * 
     * @param key
     * @param params
     */
    public ReportMessage(String key, List params) {
        this.key = key;
        this.params = (params == null) ? new ArrayList() : params;
    }

    /**
     * Returns the value of <code>key</code> property.
     * 
     * @return The value of <code>key</code> property.
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the value of <code>params</code> property.
     * 
     * @return The value of <code>params</code> property.
     */
    public List getParams() {
        return Collections.unmodifiableList(params);
    }

    public ReportMessage addParam(Object param) {
        params.add(param);
        return this;
    }
}
