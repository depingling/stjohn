package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.util.PhysicalInventoryPeriod;

import java.util.ArrayList;
import java.util.Date;

public class ScheduleOrderDates extends ValueObject {

    private java.util.Date
        mNextOrderDelivery,
        mNextOrderCutoff;

    private java.util.Date mNextOrderCutoffTime;
    private ArrayList<PhysicalInventoryPeriod> mPhysicalInventoryPeriods = null;
    private String mInventoryCartAccessInterval = null;
    
    public ScheduleOrderDates( java.util.Date pNextOrderDelivery,
                               java.util.Date pNextOrderCutoff ) {
        mNextOrderDelivery = pNextOrderDelivery;
        mNextOrderCutoff = pNextOrderCutoff;
    }

     public ScheduleOrderDates(java.util.Date pNextOrderDelivery,
                               java.util.Date pNextOrderCutoff,
                               java.util.Date pNextOrderCutoffTime) {

        mNextOrderDelivery   = pNextOrderDelivery;
        mNextOrderCutoff     = pNextOrderCutoff;
        mNextOrderCutoffTime = pNextOrderCutoffTime;

     }

    public java.util.Date getNextOrderDeliveryDate() {
        return mNextOrderDelivery;
    }
    public java.util.Date getNextOrderCutoffDate() {
        return mNextOrderCutoff;
    }


    public java.util.Date getNextOrderCutoffTime() {
        return mNextOrderCutoffTime;
    }

    public void setNextOrderCutoffTime(Date pVal) {
        this.mNextOrderCutoffTime = pVal;
    }

    public String toString() {
        return "[mNextOrderDelivery=" + mNextOrderDelivery +
            ", mNextOrderCutoff=" +mNextOrderCutoff +
            ", mNextOrderCutoffTime="+mNextOrderCutoffTime+"]" +
            ", mInventoryCartAccessInterval="+mInventoryCartAccessInterval+"]";
    }

    public ArrayList<PhysicalInventoryPeriod> getPhysicalInventoryPeriods() {
        return mPhysicalInventoryPeriods;
    }

    public void setPhysicalInventoryPeriods(ArrayList<PhysicalInventoryPeriod> periods) {
        mPhysicalInventoryPeriods = periods;
    }

	public void setInventoryCartAccessInterval(String mInvCartAccessInterval) {
		this.mInventoryCartAccessInterval = mInvCartAccessInterval;
	}

	public String getInventoryCartAccessInterval() {
		return mInventoryCartAccessInterval;
	}

}
