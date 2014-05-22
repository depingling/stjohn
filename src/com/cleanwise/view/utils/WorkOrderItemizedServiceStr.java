package com.cleanwise.view.utils;

public class WorkOrderItemizedServiceStr {

    private String mPartPrice;
    private String mQuantity;
    private String mLabor;
    private String mTravel;
    
    /**
     * Constructor.
     */
    public WorkOrderItemizedServiceStr ()
    {
        mPartPrice = "0.00";
        mQuantity = "0";
        mLabor = "0.00";
        mTravel = "0.00";
    }

    /**
     * Constructor.
     */
    public WorkOrderItemizedServiceStr (   String parm1,
                                           String parm2,
                                           String parm3,
                                           String parm4)
    {
        mPartPrice = parm1;
        mQuantity = parm2;
        mLabor = parm3;
        mTravel = parm4;
    }

    /**
     * Creates a new WorkOrderItemizedServiceStr
     *
     * @return
     *  Newly initialized WorkOrderItemizedServiceStr object.
     */
    public static WorkOrderItemizedServiceStr createValue ()
    {
        WorkOrderItemizedServiceStr valueData = new WorkOrderItemizedServiceStr();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkOrderItemizedServiceStr object
     */
    public String toString()
    {
        return "[PartPrice = " + mPartPrice + 
               ", Quantity = " + mQuantity + 
               ", Labor = " + mLabor + 
               ", Travel = " + mTravel + "]";
    }
    
    /**
    * creates a clone of this object.
    *
    * @return WorkOrderItemizedServiceStr object
    */
    public Object clone(){
        WorkOrderItemizedServiceStr myClone = new WorkOrderItemizedServiceStr();
       
        myClone.mPartPrice = mPartPrice;
        
        myClone.mQuantity = mQuantity;
        
        myClone.mLabor = mLabor;
        
        myClone.mTravel = mTravel;
        
        return myClone;
    }

    public String getLabor() {
        return mLabor;
    }

    public void setLabor(String mLabor) {
        this.mLabor = mLabor;
    }

    public String getPartPrice() {
        return mPartPrice;
    }

    public void setPartPrice(String mPartPrice) {
        this.mPartPrice = mPartPrice;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public void setQuantity(String mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getTravel() {
        return mTravel;
    }

    public void setTravel(String mTravel) {
        this.mTravel = mTravel;
    }
}
