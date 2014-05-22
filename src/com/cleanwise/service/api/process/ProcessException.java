package com.cleanwise.service.api.process;

/**
 * Created by Vlasov Evgeny.
 * Date: 05.04.2007
 * Exception class .
 */
public class ProcessException extends Exception{

    public ProcessException() {
        super();
    }

    public ProcessException(String message) {
        super(message);
    }

    public ProcessException(Throwable cause) {
        super(cause);
    }

    public ProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
