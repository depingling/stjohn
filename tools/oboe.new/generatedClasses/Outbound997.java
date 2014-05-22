import com.americancoders.edi.*;
import com.americancoders.edi.x12.*;

/** code template to build
*<br>class 997 Functional Acknowledgment
*<br>
* This Draft Standard for Trial Use contains the format and establishes
* the data contents of the Functional Acknowledgment Transaction
* Set (997) for use within the context of an Electronic Data Interchange
* (EDI) environment. The transaction set can be used to define
* the control structures for a set of acknowledgments to indicate
* the results of the syntactical analysis of the electronically
* encoded documents. The encoded documents are the transaction
* sets, which are grouped in functional groups, used in defining
* transactions for business data interchange. This standard does
* not cover the semantic meaning of the information encoded in
* the transaction sets.
*@author OBOECodeGenerator
*/
public class Outbound997
{
/** constructor for class Outbound997
*@throws OBOEException - most likely transactionset not found
*/
public Outbound997()
  throws OBOEException
{
  X12Envelope env = new X12Envelope(EnvelopeFactory.buildEnvelope("x12.envelope", ""));
  /** add code here to work with the headers and other envelope control segments */
  Segment interchange_Control_Header = env.createInterchange_Header();
  interchange_Control_Header.useDefault();
  //  Grade of Service Request not required
  Segment grade_of_Service_Request = env.createGrade_of_Service_Request();
  grade_of_Service_Request.useDefault();
  //  Deferred Delivery Request not required
  Segment deferred_Delivery_Request = env.createDeferred_Delivery_Request();
  deferred_Delivery_Request.useDefault();
  FunctionalGroup fg = env.createFunctionalGroup();


  /** add code here to work with the fg header and trailer segments */
  Segment fgHeader = fg.getHeader();
  fgHeader.useDefault();
  fg.addSegment(fgHeader);

  Segment fgTrailer = fg.getTrailer();
  fgTrailer.useDefault();
  fg.addSegment(fgTrailer);

  env.addFunctionalGroup(fg);
  TransactionSet ts = TransactionSetFactory.buildTransactionSet("997");
  fg.addTransactionSet(ts);


  Table table;
  table = ts.getHeaderTable();
  buildSegmentSTforTableHeader(table);
  buildSegmentAK1forTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildLoopAK2forTableHeader(table);
  buildSegmentAK9forTableHeader(table);
  buildSegmentSEforTableHeader(table);

  Segment interchange_Control_Trailer = env.createInterchange_Trailer();
  interchange_Control_Trailer.useDefault();

 for (int i = 0; i < env.getFunctionalGroupCount(); i++)
    {
     env.getFunctionalGroup(i).setCountInTrailer();
     for (int j = 0; j < env.getFunctionalGroup(i).getTransactionSetCount(); j++) {
       env.getFunctionalGroup(i).getTransactionSet(j).trim();
       env.getFunctionalGroup(i).getTransactionSet(j).setTrailerFields(); }
    }

 env.setFGCountInTrailer();
}
/** builds segment ST that is part of the TableHeader
*<br>Transaction Set Header used 
*<br>To indicate the start of a transaction set and to assign a control number
* @param inTable table containing this segment
* @return segment object ST
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSTforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("ST");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 143 Transaction Set Identifier Code
  de.set("997");
  de = (DataElement) segment.buildDE(2);  // 329 Transaction Set Control Number
 de.set("");
/* segment.useDefault(); */
  return segment;
  }
/** builds segment AK1 that is part of the TableHeader
*<br>Functional Group Response Header used 
*<br>To start acknowledgment of a functional group
* @param inTable table containing this segment
* @return segment object AK1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentAK1forTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("AK1");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 479 Functional Identifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 28 Group Control Number
 de.set("");
/* segment.useDefault(); */
  return segment;
  }
/** builds loop AK2 that is part of the TableHeader
*<br>Transaction Set Response Header used 
* @param inTable table containing this segment
* @return loop object AK2
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopAK2forTableHeader(Table inTable)
  throws OBOEException
{
  Loop loop = inTable.createLoop("AK2");
  inTable.addLoop(loop);
  buildSegmentAK2forTableHeaderAK2(loop);
  buildLoopAK3forTableHeaderAK2(loop);
  buildSegmentAK5forTableHeaderAK2(loop);
  return loop;
  }
/** builds segment AK2 that is part of the TableHeaderAK2
*<br>Transaction Set Response Header used 
*<br>To start acknowledgment of a single transaction set
* @param inLoop loop containing this segment
* @return segment object AK2
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentAK2forTableHeaderAK2(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("AK2");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 143 Transaction Set Identifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 329 Transaction Set Control Number
 de.set("");
/* segment.useDefault(); */
  return segment;
  }
/** builds loop AK3 that is part of the TableHeaderAK2
*<br>Data Segment Note used 
* @param inLoop loop
* @return loop object AK3
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopAK3forTableHeaderAK2(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("AK3");
  inLoop.addLoop(loop);
  buildSegmentAK3forTableHeaderAK2AK3(loop);
  buildSegmentAK4forTableHeaderAK2AK3(loop);
  return loop;
  }
/** builds segment AK3 that is part of the TableHeaderAK2AK3
*<br>Data Segment Note used 
*<br>To report errors in a data segment and identify the location of the data segment
* @param inLoop loop containing this segment
* @return segment object AK3
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentAK3forTableHeaderAK2AK3(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("AK3");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 721 Segment ID Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 719 Segment Position in Transaction Set
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 447 Loop Identifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 720 Segment Syntax Error Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment AK4 that is part of the TableHeaderAK2AK3
*<br>Data Element Note used 
*<br>To report errors in a data element or composite data structure and identify the location of the data element
* @param inLoop loop containing this segment
* @return segment object AK4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentAK4forTableHeaderAK2AK3(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("AK4");
  inLoop.addSegment(segment);
  DataElement de;
  CompositeDE  composite = (CompositeDE) segment.buildDE(1);  // C030 Position in Segment
  de = (DataElement) composite.buildDE(1);  // composite element 722 Element Position in Segment
  de.set("");
  de = (DataElement) composite.buildDE(2);  // composite element 1528 Component Data Element Position in Composite
  de.set("");
  de = (DataElement) segment.buildDE(2);  // 725 Data Element Reference Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 723 Data Element Syntax Error Code
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 724 Copy of Bad Data Element
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment AK5 that is part of the TableHeaderAK2
*<br>Transaction Set Response Trailer used 
*<br>To acknowledge acceptance or rejection and report errors in a transaction set
* @param inLoop loop containing this segment
* @return segment object AK5
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentAK5forTableHeaderAK2(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("AK5");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 717 Transaction Set Acknowledgment Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 718 Transaction Set Syntax Error Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 718 Transaction Set Syntax Error Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 718 Transaction Set Syntax Error Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 718 Transaction Set Syntax Error Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 718 Transaction Set Syntax Error Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment AK9 that is part of the TableHeader
*<br>Functional Group Response Trailer used 
*<br>To acknowledge acceptance or rejection of a functional group and report the number of included transaction sets from the original trailer, the accepted sets, and the received sets in this functional group
* @param inTable table containing this segment
* @return segment object AK9
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentAK9forTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("AK9");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 715 Functional Group Acknowledge Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 97 Number of Transaction Sets Included
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 123 Number of Received Transaction Sets
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 2 Number of Accepted Transaction Sets
 de.set("");
  de = (DataElement) segment.buildDE(5);  // 716 Functional Group Syntax Error Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 716 Functional Group Syntax Error Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 716 Functional Group Syntax Error Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 716 Functional Group Syntax Error Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 716 Functional Group Syntax Error Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment SE that is part of the TableHeader
*<br>Transaction Set Trailer used 
*<br>To indicate the end of the transaction set and provide the count of the transmitted segments (including the beginning (ST) and ending (SE) segments)
* @param inTable table containing this segment
* @return segment object SE
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSEforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("SE");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 96 Number of Included Segments
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 329 Transaction Set Control Number
 de.set("");
/* segment.useDefault(); */
  return segment;
  }
}
