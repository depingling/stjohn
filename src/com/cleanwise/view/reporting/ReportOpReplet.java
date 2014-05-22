package com.cleanwise.view.reporting;

/**
 * This class is a replet - a servlet that creates an InetSoft generated
 * report.  It expects that a ReportOpForm has been filled with the
 * required parameters.
 *
 * Copyright:   Copyright (c) 2001
 * Company:     CleanWise, Inc.
 * @author      Tim Besser
 *
 */

import java.io.*;
import javax.servlet.http.*;
import inetsoft.report.*;
import inetsoft.report.lens.*;
import inetsoft.report.lens.xnode.XMLTableLens;
import inetsoft.report.io.*;
import inetsoft.sree.*;

import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.forms.ReportOpForm;

public class ReportOpReplet extends TemplateReplet {

    public ReportSheet createReport(RepletRequest req) throws RepletException {
	String name = (String)req.getParameter("Rname");

	
	HttpServletRequest httpReq = (HttpServletRequest)
	    req.getParameter(RepletRequest.HTTP_REQUEST);
        HttpSession session = httpReq.getSession();

	// the form values should have been set by the JSP
        ReportOpForm form = 
	    (ReportOpForm) session.getAttribute("REPORT_OP_FORM");
	if (form == null) {
	    throw new RepletException("ReportOpform is null");
	}

	String interval = form.getInterval();
	req.setParameter("interval", interval);
	String beginDate = form.getBeginDate();
	req.setParameter("begin", beginDate);
	String endDate = form.getEndDate();
	req.setParameter("end", endDate);
	String account = form.getAccountId();
	req.setParameter("account", account);
	String minAmt = form.getMinAmt();
	req.setParameter("min", minAmt);
	String maxAmt = form.getMaxAmt();
	req.setParameter("max", maxAmt);
        String vendorId = form.getVendorId();
	req.setParameter("vendor", vendorId);
        String manufacturerId = form.getManufacturerId();
	req.setParameter("manufacturer", manufacturerId );
        String accountId = form.getAccountId();
	req.setParameter("account", accountId );
	return super.createReport(req);
    }

}

