/*
 * BlanketPoNumDescData.java
 *
 * Created on February 11, 2005, 1:29 PM
 */

package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;

/**
 *
 * @author bstevens
 */
public class BlanketPoNumDescData extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -2969287548326122790L;

    /**
     * Holds value of property BlanketPoNumData.
     */
    private BlanketPoNumData BlanketPoNumData;

    
    
    /** Creates a new instance of BlanketPoNumDescData */
    public BlanketPoNumDescData() {
    }
    
    /**
     *Inits this object with empty values
     */
    public void init(){
        BlanketPoNumData = BlanketPoNumData.createValue();
        setDirty(true);
    }

    /**
     * Getter for property BlanketPoNumData.
     * @return Value of property BlanketPoNumData.
     */
    public BlanketPoNumData getBlanketPoNumData() {

        return this.BlanketPoNumData;
    }

    /**
     * Setter for property BlanketPoNumData.
     * @param BlanketPoNumData New value of property BlanketPoNumData.
     */
    public void setBlanketPoNumData(BlanketPoNumData BlanketPoNumData) {

        this.BlanketPoNumData = BlanketPoNumData;
    }
    
}
