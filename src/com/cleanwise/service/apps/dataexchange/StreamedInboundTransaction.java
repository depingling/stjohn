/*
 * StreamedInput.java
 *
 * Created on July 10, 2003, 8:11 PM
 */

package com.cleanwise.service.apps.dataexchange;
import java.io.InputStream;
import java.io.IOException;
import java.util.List;

/**
 * Implementing Classes that fall into the dataexchanges framework will recieve their input as
 * a stream as opposed to the fully red in String of whatever the originating stream represented.
 * This can be useful for both memory and for dealing with binary files (excel, pdf etc).
 * @author  bstevens
 */
public interface StreamedInboundTransaction extends InboundTransaction{
    /**
     *Translates a stream of varying types.  This implementation supports xls and flat text.
     *InputStream the input stream to operate on
     *String the stream type (such as xls, txt, etc.)
     *@throws IOException
     */
    public void translate(InputStream pIn,String pStreamType) throws Exception;
    
    /**
     *Gets called once for each line of text in the file.  
     */
    public abstract void parseLine(List pParsedLine) throws Exception;
}
