package com.cleanwise.service.api.eventsys;

/**
 * Title:        EventDataVector
 * Description:  Container object for EventData objects
 * Purpose:      Provides container storage for BusEntityData objects.
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * @author       Evgeny Vlasov
 */
public class EventDataVector extends java.util.ArrayList {
    private boolean useAdditional;
    /**
     * Constructor
     */
    public EventDataVector(){

    }
    public boolean isUseAdditional() {
        return useAdditional;
    }
    
    public void setUseAdditional(boolean useAdditional) {
        this.useAdditional = useAdditional;
    }
}
