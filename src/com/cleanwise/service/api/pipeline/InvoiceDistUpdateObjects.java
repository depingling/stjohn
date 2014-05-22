/*
 * InvoiceDistUpdateObjects.java
 *
 * Created on August 1, 2005, 9:44 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.InvoiceDistDataAccess;
import com.cleanwise.service.api.dao.InvoiceDistDetailDataAccess;
import com.cleanwise.service.api.session.Pipeline;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import java.sql.Connection;
import java.util.Iterator;

/**
 *Updates all of the objects that are run through the pipeline.  The erp processing currently has its own update
 *statements, so care needs to be taken that whatever modifications made by that are preserved.
 * @author bstevens
 */
public class InvoiceDistUpdateObjects implements InvoiceDistPipeline{
    
    public void process(
            InvoiceDistPipelineBaton pBaton,
            Connection pCon,
            APIAccess pFactory)
            throws PipelineException{
        pBaton.saveBatonObjects(pCon);
    }
    
}
