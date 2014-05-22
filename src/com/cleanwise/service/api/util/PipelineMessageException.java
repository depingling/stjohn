/*
 * PipelineMessageException.java
 *
 * Created on May 6, 2003, 4:03 PM
 */

package com.cleanwise.service.api.util;

/**
 *Thrown to indicate that something went wrong during the traversal of a pipeline.
 * @author  bstevens
 */
public class PipelineMessageException extends PipelineException{
  private String code;
    /** Creates a new instance of PipelineException */
    public PipelineMessageException(String code, String message) {
        super(message);
        this.code = code;
    }
    public String getCode() {
      return this.code;
    }
}
