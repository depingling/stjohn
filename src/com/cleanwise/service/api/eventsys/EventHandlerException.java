package com.cleanwise.service.api.eventsys;

/**
 * Title:        EventHandlerException
 * Description:  Exception class for Events
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 *
 * @author  Evgeny Vlasov, CleanWise, Inc.
 */
public class EventHandlerException extends Exception{

    public EventHandlerException() {
        super();
    }

    public EventHandlerException(String message) {
        super(message);
    }

    public EventHandlerException(Throwable cause) {
        super(cause);
    }

    public EventHandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
