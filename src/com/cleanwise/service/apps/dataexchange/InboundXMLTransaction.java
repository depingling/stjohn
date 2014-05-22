/*
 * InboundXMLTransaction.java
 *
 * Created on July 15, 2004, 2:52 PM
 */

package com.cleanwise.service.apps.dataexchange;
import org.dom4j.Node;
import java.io.IOException;
/**
 *Implementing classes should be able to understand how to translate a subsection of a document
 *to translate.  Depending on how this class is called they should also understand the
 *translate method in the @see InboundTransaction class as depending on how this class is
 *called they may recieve a call to one or the other.  State does not need to be maintained
 *as both methods will never be called in the same transaction.
 * @author  bstevens
 */
public interface InboundXMLTransaction extends InboundTransaction{
    /**
     *Preforms the translation.  The root node that is appropriate to this transaction
     *is given if the XMLInboundTranslator knows enough about this trasnaction to give
     *you the proper node, otherwise the root node of the document is passed in.
     *At the very least the body of this method should be:
     *<code>
     *translate(nodeToOperateOn.asXML());
     *</code>
     *Or better yet the body of the @see translate(String data) method should be:
     *<code>
     *translate(DocumentHelper.parseText(s));
     *</code>
     */
    public void translate(Node nodeToOperateOn) throws IOException;
    
}
