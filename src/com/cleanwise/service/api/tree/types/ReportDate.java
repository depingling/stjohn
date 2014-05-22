package com.cleanwise.service.api.tree.types;

import java.util.Date;

public class ReportDate extends Date {
    private String type;

    public ReportDate(String type) {
        this.type = type;
    }

    /**
     * Returns the value of <code>type</code> property.
     * 
     * @return The value of <code>type</code> property.
     */
    public String getType() {
        return type;
    }
}
