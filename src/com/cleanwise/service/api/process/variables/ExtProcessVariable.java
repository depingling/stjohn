package com.cleanwise.service.api.process.variables;

import java.io.Serializable;

public interface ExtProcessVariable extends Serializable {

    public Object getValue() throws Exception;

    public void updateValue(Object pValue, String pUser) throws Exception;

}
