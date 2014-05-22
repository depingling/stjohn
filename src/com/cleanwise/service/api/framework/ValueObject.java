/**
 * This is the base object will serve to identify
 * and serialize all objects in the application that are
 * used to transport data between the various application layers.
 *
 * Copyright:   Copyright (c) 2001
 * Company:     CleanWise, Inc.
 * @author      Liang Li
 */

package com.cleanwise.service.api.framework;

import java.rmi.*;
import java.io.Serializable;

/**
 * This is the base object will serve to identify
 * and serialize all objects in the application that are
 * used to transport data between the various application layers.
 */
public abstract class ValueObject implements Serializable
{
    private boolean dirty;
    
    public boolean isDirty()
    {
        return dirty;
    }
    
    public void setDirty(boolean isDirty)
    {
        this.dirty = isDirty;
    }
}

