package com.cleanwise.service.api.process;

import java.util.HashMap;

/**
 * Title:
 * Description:  Classs for storing all parameters which are used for task executing
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * Date:         16.07.2007
 *
 * @author  Evgeny Vlasov, TrinitySoft, Inc.
 */

public class TaskParams extends HashMap {
    HashMap pClasses;

    public TaskParams() {
        super();
        pClasses = new HashMap();
    }

    public Object put(String name, Class pClass, Object value) {
        this.pClasses.put(name, pClass);
        return super.put(name, value);
    }

    public Object updateObject(String name, Object value){
        this.remove(name);
        return  super.put(name, value);
    }

    public Class[] getPClasses() {
        return (Class[]) pClasses.values().toArray();
    }

    public Class getClass(String name) {
        return (Class) pClasses.get(name);
    }

    public Object getValue(String name) {
        return this.get(name);
    }
}
