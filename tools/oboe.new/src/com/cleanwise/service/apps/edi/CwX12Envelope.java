package com.cleanwise.service.apps.edi;

/**
 *OBOE - Open Business Objects for EDI
 *<P>java access to EDI
 *<p>Copyright 1998-2007 - American Coders, LTD  - Raleigh NC USA
 *<p>All rights reserved
 *<p>American Coders, Ltd
 *<br>P. O. Box 97462
 *<br>Raleigh, NC  27624  USA
 *<br>1-919-846-2014
 *<br>http://www.americancoders.com
@author Joe McVerry - American Coders, Ltd.
  @version   3.5.3

 * */

import com.americancoders.edi.x12.X12Envelope;
import com.americancoders.edi.FunctionalGroup;
import com.americancoders.edi.OBOEException;
import com.americancoders.edi.TemplateEnvelope;

/**
 * class for wrapping a X12 EDI transaction set within an EDI Envelope
 *
 */


public class CwX12Envelope extends X12Envelope
{
	/**
	   * instantiates the class from a TemplateEnvelope,
	   * creates mandatory segments ISA and IEA and creates one emtpy functional group
	   * @param inTempEnv TemplateEnvelope to build this class with
	   * @exception OBOEException missing segment definition in envelope xml.
	   */

	  public CwX12Envelope(TemplateEnvelope inTempEnv)
	    throws OBOEException
	  {
	    super(inTempEnv);
	  }

    /** creates a basic functionalgroup object
     * @return X12FunctionalGroup
     */
  public FunctionalGroup createFunctionalGroup()
    {
        if (myTemplate != null)
          return new CwX12FunctionalGroup(myTemplate.getTemplateFunctionalGroup(), this);
        else
          return new CwX12FunctionalGroup();
    }
}


