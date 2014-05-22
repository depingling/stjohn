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

 * */

import com.americancoders.edi.x12.X12Envelope;
import com.americancoders.edi.FunctionalGroup;
import com.americancoders.edi.OBOEException;
import com.americancoders.edi.TemplateEnvelope;
import com.americancoders.edi.Envelope;

import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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

    public void setDelimitersInHeader() throws OBOEException {

        if (getInterchange_Header() == null) {
            throw new OBOEException("header not set yet");
        }

        if (this.repeatDelimiter.length() > 0) {
            getInterchange_Header().getDataElement("I10").set(this.repeatDelimiter);
        }

        if (this.groupDelimiter.length() > 0) {
            getInterchange_Header().getDataElement("I15").set(this.groupDelimiter);
        } else {
            getInterchange_Header().getDataElement("I15").set(Envelope.X12_GROUP_DELIMITER);
        }
    }

    public String getFormattedText(int format) throws OBOEException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            super.writeFormattedText(new OutputStreamWriter(outputStream), format);
            return new String(outputStream.toByteArray());
        } catch (IOException e) {
            throw new OBOEException(e.getMessage());
        } finally {
            closeOutputStream(outputStream);
        }
    }

    public void closeOutputStream(OutputStream pOutputStream) {
        if (pOutputStream != null) {
            try {
                pOutputStream.flush();
                pOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


