/*
 * PipelineException.java
 *
 * Created on May 6, 2003, 4:03 PM
 */

package com.cleanwise.service.api.util;

/**
 *Thrown to indicate that something went wrong during the traversal of a pipeline.
 * @author  bstevens
 */
public class PipelineException extends Exception{
    
    /** Creates a new instance of PipelineException */
    public PipelineException(String message) {
        super(message);
    }
    
}
