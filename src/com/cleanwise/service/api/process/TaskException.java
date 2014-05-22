package com.cleanwise.service.api.process;

import com.cleanwise.service.api.process.workflow.WorkfolowProcessingData;

/**
 * Created by Vlasov Evgeny.
 * Date: 05.04.2007
 * Exception class .
 */
public class TaskException extends Exception{

    private int code = 0;
    private WorkfolowProcessingData workfolowProcessingData;

    public TaskException() {
        super();
    }

    public TaskException(int code) {
        super();
        this.code = code;
    }

    public TaskException(int code, WorkfolowProcessingData workfolowProcessingData) {
        this.code = code;
        this.workfolowProcessingData = workfolowProcessingData;
    }

    public TaskException(String message) {
        super(message);
    }

    public TaskException(Throwable cause) {
        super(cause);
    }

    public TaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getCode() {
        return code;
    }

    public WorkfolowProcessingData getWorkfolowProcessingData() {
        return workfolowProcessingData;
    }
}
