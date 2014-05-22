package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.PipelineException;

import javax.naming.NamingException;
import java.sql.Connection;

/**             InvoiceRequestPipeline
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         23.04.2007
 * Time:         13:24:13
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public interface InvoiceRequestPipeline {
   public InvoiceRequestPipelineBaton process(InvoiceRequestPipelineBaton pBaton,
                       Connection pCon,
                       APIAccess pFactory)
                       throws PipelineException;

  public InvoiceRequestPipelineBaton process(InvoiceRequestPipelineBaton pBaton) throws PipelineException, NamingException, Exception;


}
