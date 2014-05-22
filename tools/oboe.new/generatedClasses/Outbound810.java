import com.americancoders.edi.*;
import com.americancoders.edi.x12.*;

/** code template to build
*<br>class 810 Invoice
*<br>
* This Draft Standard for Trial Use contains the format and establishes
* the data contents of the Invoice Transaction Set (810) for use
* within the context of an Electronic Data Interchange (EDI) environment.
* The transaction set can be used to provide for customary and
* established business and industry practice relative to the billing
* for goods and services provided.
*@author OBOECodeGenerator
*/
public class Outbound810
{
/** constructor for class Outbound810
*@throws OBOEException - most likely transactionset not found
*/
public Outbound810()
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
  TransactionSet ts = TransactionSetFactory.buildTransactionSet("810");
  fg.addTransactionSet(ts);


  Table table;
  table = ts.getHeaderTable();
  buildSegmentSTforTableHeader(table);
  buildSegmentBIGforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentNTEforTableHeader(table);
  buildSegmentCURforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentREFforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentYNQforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentPERforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildLoopN1forTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentITDforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentDTMforTableHeader(table);
  buildSegmentFOBforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentPIDforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentMEAforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentPWKforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentPKGforTableHeader(table);
  buildSegmentL7forTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentBALforTableHeader(table);
  buildSegmentINCforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentPAMforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildLoopLMforTableHeader(table);
  buildLoopN9forTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildLoopV1forTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildLoopFA1forTableHeader(table);
  table = ts.getDetailTable();
  // for (i = 0; i < multipletimes; i++)
  buildLoopIT1forTableDetail(table);
  table = ts.getSummaryTable();
  buildSegmentTDSforTableSummary(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentTXIforTableSummary(table);
  buildSegmentCADforTableSummary(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentAMTforTableSummary(table);
  // for (i = 0; i < multipletimes; i++)
  buildLoopSACforTableSummary(table);
  // for (i = 0; i < multipletimes; i++)
  buildLoopISSforTableSummary(table);
  buildSegmentCTTforTableSummary(table);
  buildSegmentSEforTableSummary(table);

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
  de.set("810");
  de = (DataElement) segment.buildDE(2);  // 329 Transaction Set Control Number
 de.set("");
/* segment.useDefault(); */
  return segment;
  }
/** builds segment BIG that is part of the TableHeader
*<br>Beginning Segment for Invoice used 
*<br>To indicate the beginning of an invoice transaction set and transmit identifying numbers and dates
* @param inTable table containing this segment
* @return segment object BIG
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentBIGforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("BIG");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 373 Date
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 76 Invoice Number
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 324 Purchase Order Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 328 Release Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 327 Change Order Sequence Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 640 Transaction Type Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 353 Transaction Set Purpose Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 306 Action Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 76 Invoice Number
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment NTE that is part of the TableHeader
*<br>Note/Special Instruction used 
*<br>To transmit information in a free-form format, if necessary, for comment or special instruction
* @param inTable table containing this segment
* @return segment object NTE
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentNTEforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("NTE");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 363 Note Reference Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 352 Description
 de.set("");
/* segment.useDefault(); */
  return segment;
  }
/** builds segment CUR that is part of the TableHeader
*<br>Currency used 
*<br>To specify the currency (dollars, pounds, francs, etc.) used in a transaction
* @param inTable table containing this segment
* @return segment object CUR
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCURforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("CUR");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 98 Entity Identifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 100 Currency Code
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 280 Exchange Rate
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 98 Entity Identifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 100 Currency Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 669 Currency Market/Exchange Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 374 Date/Time Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 374 Date/Time Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 374 Date/Time Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 374 Date/Time Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(17);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(18);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(19);  // 374 Date/Time Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(20);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(21);  // 337 Time
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment REF that is part of the TableHeader
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inTable table containing this segment
* @return segment object REF
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentREFforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("REF");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 128 Reference Identification Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 127 Reference Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 352 Description
  //de.set("");//not required
  CompositeDE  composite = (CompositeDE) segment.buildDE(4);  // C040 Reference Identifier
  de = (DataElement) composite.buildDE(1);  // composite element 128 Reference Identification Qualifier
  de.set("");
  de = (DataElement) composite.buildDE(2);  // composite element 127 Reference Identification
  de.set("");
  de = (DataElement) composite.buildDE(3);  // composite element 128 Reference Identification Qualifier
  de.set("");
  de = (DataElement) composite.buildDE(4);  // composite element 127 Reference Identification
  de.set("");
  de = (DataElement) composite.buildDE(5);  // composite element 128 Reference Identification Qualifier
  de.set("");
  de = (DataElement) composite.buildDE(6);  // composite element 127 Reference Identification
  de.set("");
/* segment.useDefault(); */
  return segment;
  }
/** builds segment YNQ that is part of the TableHeader
*<br>Yes/No Question used 
*<br>To identify and answer yes and no questions, including the date, time, and comments further qualifying the condition
* @param inTable table containing this segment
* @return segment object YNQ
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentYNQforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("YNQ");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 1321 Condition Indicator
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 1073 Yes/No Condition or Response Code
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 1250 Date Time Period Format Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 1251 Date Time Period
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 933 Free-Form Message Text
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 933 Free-Form Message Text
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 933 Free-Form Message Text
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 1270 Code List Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 1271 Industry Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 933 Free-Form Message Text
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment PER that is part of the TableHeader
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inTable table containing this segment
* @return segment object PER
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPERforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("PER");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 366 Contact Function Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 93 Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 365 Communication Number Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 364 Communication Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 365 Communication Number Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 364 Communication Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 365 Communication Number Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 364 Communication Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 443 Contact Inquiry Reference
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop N1 that is part of the TableHeader
*<br>Name used 
* @param inTable table containing this segment
* @return loop object N1
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopN1forTableHeader(Table inTable)
  throws OBOEException
{
  Loop loop = inTable.createLoop("N1");
  inTable.addLoop(loop);
  buildSegmentN1forTableHeaderN1(loop);
  buildSegmentN2forTableHeaderN1(loop);
  buildSegmentN3forTableHeaderN1(loop);
  buildSegmentN4forTableHeaderN1(loop);
  buildSegmentREFforTableHeaderN1(loop);
  buildSegmentPERforTableHeaderN1(loop);
  buildSegmentDMGforTableHeaderN1(loop);
  return loop;
  }
/** builds segment N1 that is part of the TableHeaderN1
*<br>Name used 
*<br>To identify a party by type of organization, name, and code
* @param inLoop loop containing this segment
* @return segment object N1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN1forTableHeaderN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("N1");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 98 Entity Identifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 93 Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 66 Identification Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 67 Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 706 Entity Relationship Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 98 Entity Identifier Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment N2 that is part of the TableHeaderN1
*<br>Additional Name Information used 
*<br>To specify additional names or those longer than 35 characters in length
* @param inLoop loop containing this segment
* @return segment object N2
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN2forTableHeaderN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("N2");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 93 Name
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 93 Name
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment N3 that is part of the TableHeaderN1
*<br>Address Information used 
*<br>To specify the location of the named party
* @param inLoop loop containing this segment
* @return segment object N3
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN3forTableHeaderN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("N3");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 166 Address Information
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 166 Address Information
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment N4 that is part of the TableHeaderN1
*<br>Geographic Location used 
*<br>To specify the geographic place of the named party
* @param inLoop loop containing this segment
* @return segment object N4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN4forTableHeaderN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("N4");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 19 City Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 156 State or Province Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 116 Postal Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 26 Country Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 310 Location Identifier
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment REF that is part of the TableHeaderN1
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop loop containing this segment
* @return segment object REF
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentREFforTableHeaderN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("REF");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 128 Reference Identification Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 127 Reference Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 352 Description
  //de.set("");//not required
  CompositeDE  composite = (CompositeDE) segment.buildDE(4);  // C040 Reference Identifier
  de = (DataElement) composite.buildDE(1);  // composite element 128 Reference Identification Qualifier
  de.set("");
  de = (DataElement) composite.buildDE(2);  // composite element 127 Reference Identification
  de.set("");
  de = (DataElement) composite.buildDE(3);  // composite element 128 Reference Identification Qualifier
  de.set("");
  de = (DataElement) composite.buildDE(4);  // composite element 127 Reference Identification
  de.set("");
  de = (DataElement) composite.buildDE(5);  // composite element 128 Reference Identification Qualifier
  de.set("");
  de = (DataElement) composite.buildDE(6);  // composite element 127 Reference Identification
  de.set("");
/* segment.useDefault(); */
  return segment;
  }
/** builds segment PER that is part of the TableHeaderN1
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inLoop loop containing this segment
* @return segment object PER
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPERforTableHeaderN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("PER");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 366 Contact Function Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 93 Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 365 Communication Number Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 364 Communication Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 365 Communication Number Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 364 Communication Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 365 Communication Number Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 364 Communication Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 443 Contact Inquiry Reference
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment DMG that is part of the TableHeaderN1
*<br>Demographic Information used 
*<br>To supply demographic information
* @param inLoop loop containing this segment
* @return segment object DMG
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDMGforTableHeaderN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("DMG");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 1250 Date Time Period Format Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 1251 Date Time Period
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 1068 Gender Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 1067 Marital Status Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 1109 Race or Ethnicity Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 1066 Citizenship Status Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 26 Country Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 659 Basis of Verification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 380 Quantity
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment ITD that is part of the TableHeader
*<br>Terms of Sale/Deferred Terms of Sale used 
*<br>To specify terms of sale
* @param inTable table containing this segment
* @return segment object ITD
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentITDforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("ITD");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 336 Terms Type Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 333 Terms Basis Date Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 338 Terms Discount Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 370 Terms Discount Due Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 351 Terms Discount Days Due
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 446 Terms Net Due Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 386 Terms Net Days
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 362 Terms Discount Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 388 Terms Deferred Due Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 389 Deferred Amount Due
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 342 Percent of Invoice Payable
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 765 Day of Month
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 107 Payment Method Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 954 Percent
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment DTM that is part of the TableHeader
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inTable table containing this segment
* @return segment object DTM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDTMforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("DTM");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 374 Date/Time Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 623 Time Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 1250 Date Time Period Format Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 1251 Date Time Period
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment FOB that is part of the TableHeader
*<br>F.O.B. Related Instructions used 
*<br>To specify transportation instructions relating to shipment
* @param inTable table containing this segment
* @return segment object FOB
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentFOBforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("FOB");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 146 Shipment Method of Payment
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 334 Transportation Terms Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 335 Transportation Terms Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 54 Risk of Loss Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 352 Description
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment PID that is part of the TableHeader
*<br>Product/Item Description used 
*<br>To describe a product or process in coded or free-form format
* @param inTable table containing this segment
* @return segment object PID
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPIDforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("PID");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 349 Item Description Type
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 750 Product/Process Characteristic Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 559 Agency Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 751 Product Description Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 752 Surface/Layer/Position Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 822 Source Subqualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 1073 Yes/No Condition or Response Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 819 Language Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment MEA that is part of the TableHeader
*<br>Measurements used 
*<br>To specify physical measurements or counts, including dimensions, tolerances, variances, and weights  (See Figures Appendix for example of use of C001)
* @param inTable table containing this segment
* @return segment object MEA
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentMEAforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("MEA");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 737 Measurement Reference ID Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 738 Measurement Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 739 Measurement Value
  //de.set("");//not required
  CompositeDE  composite = (CompositeDE) segment.buildDE(4);  // C001 Composite Unit of Measure
  de = (DataElement) composite.buildDE(1);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(2);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(3);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(4);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(5);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(6);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(7);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(8);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(9);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(10);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(11);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(12);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(13);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(14);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(15);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) segment.buildDE(5);  // 740 Range Minimum
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 741 Range Maximum
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 935 Measurement Significance Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 936 Measurement Attribute Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 752 Surface/Layer/Position Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 1373 Measurement Method or Device
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment PWK that is part of the TableHeader
*<br>Paperwork used 
*<br>To identify the type or transmission or both of paperwork or supporting information
* @param inTable table containing this segment
* @return segment object PWK
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPWKforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("PWK");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 755 Report Type Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 756 Report Transmission Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 757 Report Copies Needed
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 98 Entity Identifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 66 Identification Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 67 Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 352 Description
  //de.set("");//not required
  CompositeDE  composite = (CompositeDE) segment.buildDE(8);  // C002 Actions Indicated
  de = (DataElement) composite.buildDE(1);  // composite element 704 Paperwork/Report Action Code
  de.set("");
  de = (DataElement) composite.buildDE(2);  // composite element 704 Paperwork/Report Action Code
  de.set("");
  de = (DataElement) composite.buildDE(3);  // composite element 704 Paperwork/Report Action Code
  de.set("");
  de = (DataElement) composite.buildDE(4);  // composite element 704 Paperwork/Report Action Code
  de.set("");
  de = (DataElement) composite.buildDE(5);  // composite element 704 Paperwork/Report Action Code
  de.set("");
  de = (DataElement) segment.buildDE(9);  // 1525 Request Category Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment PKG that is part of the TableHeader
*<br>Marking, Packaging, Loading used 
*<br>To describe marking, packaging, loading, and unloading requirements
* @param inTable table containing this segment
* @return segment object PKG
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPKGforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("PKG");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 349 Item Description Type
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 753 Packaging Characteristic Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 559 Agency Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 754 Packaging Description Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 400 Unit Load Option Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment L7 that is part of the TableHeader
*<br>Tariff Reference used 
*<br>To reference details of the tariff used to arrive at applicable rates or charge
* @param inTable table containing this segment
* @return segment object L7
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentL7forTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("L7");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 213 Lading Line Item Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 168 Tariff Agency Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 171 Tariff Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 172 Tariff Section
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 169 Tariff Item Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 170 Tariff Item Part
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 59 Freight Class Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 173 Tariff Supplement Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 46 Ex Parte
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 119 Rate Basis Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 227 Tariff Column
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 294 Tariff Distance
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 295 Distance Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 19 City Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 156 State or Province Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment BAL that is part of the TableHeader
*<br>Balance Detail used 
*<br>To identify the specific monetary balances associated with a particular account
* @param inTable table containing this segment
* @return segment object BAL
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentBALforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("BAL");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 951 Balance Type Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 522 Amount Qualifier Code
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 782 Monetary Amount
 de.set("");
/* segment.useDefault(); */
  return segment;
  }
/** builds segment INC that is part of the TableHeader
*<br>Installment Information used 
*<br>To specify installment billing arrangement
* @param inTable table containing this segment
* @return segment object INC
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentINCforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("INC");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 336 Terms Type Code
 de.set("");
  CompositeDE  composite = (CompositeDE) segment.buildDE(2);  // C001 Composite Unit of Measure
  de = (DataElement) composite.buildDE(1);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(2);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(3);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(4);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(5);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(6);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(7);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(8);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(9);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(10);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(11);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(12);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(13);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(14);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(15);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) segment.buildDE(3);  // 380 Quantity
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 380 Quantity
 de.set("");
  de = (DataElement) segment.buildDE(5);  // 782 Monetary Amount
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment PAM that is part of the TableHeader
*<br>Period Amount used 
*<br>To indicate a quantity, and/or amount for an identified period
* @param inTable table containing this segment
* @return segment object PAM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPAMforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("PAM");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 673 Quantity Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 380 Quantity
  //de.set("");//not required
  CompositeDE  composite = (CompositeDE) segment.buildDE(3);  // C001 Composite Unit of Measure
  de = (DataElement) composite.buildDE(1);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(2);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(3);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(4);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(5);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(6);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(7);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(8);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(9);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(10);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(11);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(12);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(13);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(14);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(15);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) segment.buildDE(4);  // 522 Amount Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 782 Monetary Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 344 Unit of Time Period or Interval
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 374 Date/Time Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 374 Date/Time Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 1004 Percent Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 954 Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 1073 Yes/No Condition or Response Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop LM that is part of the TableHeader
*<br>Code Source Information used 
* @param inTable table containing this segment
* @return loop object LM
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopLMforTableHeader(Table inTable)
  throws OBOEException
{
  Loop loop = inTable.createLoop("LM");
  inTable.addLoop(loop);
  buildSegmentLMforTableHeaderLM(loop);
  buildSegmentLQforTableHeaderLM(loop);
  return loop;
  }
/** builds segment LM that is part of the TableHeaderLM
*<br>Code Source Information used 
*<br>To transmit standard code list identification information
* @param inLoop loop containing this segment
* @return segment object LM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLMforTableHeaderLM(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("LM");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 559 Agency Qualifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 822 Source Subqualifier
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment LQ that is part of the TableHeaderLM
*<br>Industry Code used 
*<br>Code to transmit standard industry codes
* @param inLoop loop containing this segment
* @return segment object LQ
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLQforTableHeaderLM(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("LQ");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 1270 Code List Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 1271 Industry Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop N9 that is part of the TableHeader
*<br>Reference Identification used 
* @param inTable table containing this segment
* @return loop object N9
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopN9forTableHeader(Table inTable)
  throws OBOEException
{
  Loop loop = inTable.createLoop("N9");
  inTable.addLoop(loop);
  buildSegmentN9forTableHeaderN9(loop);
  buildSegmentMSGforTableHeaderN9(loop);
  return loop;
  }
/** builds segment N9 that is part of the TableHeaderN9
*<br>Reference Identification used 
*<br>To transmit identifying information as specified by the Reference Identification Qualifier
* @param inLoop loop containing this segment
* @return segment object N9
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN9forTableHeaderN9(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("N9");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 128 Reference Identification Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 127 Reference Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 369 Free-form Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 623 Time Code
  //de.set("");//not required
  CompositeDE  composite = (CompositeDE) segment.buildDE(7);  // C040 Reference Identifier
  de = (DataElement) composite.buildDE(1);  // composite element 128 Reference Identification Qualifier
  de.set("");
  de = (DataElement) composite.buildDE(2);  // composite element 127 Reference Identification
  de.set("");
  de = (DataElement) composite.buildDE(3);  // composite element 128 Reference Identification Qualifier
  de.set("");
  de = (DataElement) composite.buildDE(4);  // composite element 127 Reference Identification
  de.set("");
  de = (DataElement) composite.buildDE(5);  // composite element 128 Reference Identification Qualifier
  de.set("");
  de = (DataElement) composite.buildDE(6);  // composite element 127 Reference Identification
  de.set("");
/* segment.useDefault(); */
  return segment;
  }
/** builds segment MSG that is part of the TableHeaderN9
*<br>Message Text used 
*<br>To provide a free-form format that allows the transmission of text information
* @param inLoop loop containing this segment
* @return segment object MSG
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentMSGforTableHeaderN9(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("MSG");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 933 Free-Form Message Text
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 934 Printer Carriage Control Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 1470 Number
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop V1 that is part of the TableHeader
*<br>Vessel Identification used 
* @param inTable table containing this segment
* @return loop object V1
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopV1forTableHeader(Table inTable)
  throws OBOEException
{
  Loop loop = inTable.createLoop("V1");
  inTable.addLoop(loop);
  buildSegmentV1forTableHeaderV1(loop);
  buildSegmentR4forTableHeaderV1(loop);
  buildSegmentDTMforTableHeaderV1(loop);
  return loop;
  }
/** builds segment V1 that is part of the TableHeaderV1
*<br>Vessel Identification used 
*<br>To provide vessel details and voyage number
* @param inLoop loop containing this segment
* @return segment object V1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentV1forTableHeaderV1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("V1");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 597 Vessel Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 182 Vessel Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 26 Country Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 55 Flight/Voyage Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 140 Standard Carrier Alpha Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 249 Vessel Requirement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 854 Vessel Type Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 897 Vessel Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 91 Transportation Method/Type Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment R4 that is part of the TableHeaderV1
*<br>Port or Terminal used 
*<br>Contractual or operational port or point relevant to the movement of the cargo
* @param inLoop loop containing this segment
* @return segment object R4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentR4forTableHeaderV1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("R4");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 115 Port or Terminal Function Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 114 Port Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 26 Country Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 174 Terminal Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 113 Pier Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 156 State or Province Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment DTM that is part of the TableHeaderV1
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop loop containing this segment
* @return segment object DTM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDTMforTableHeaderV1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("DTM");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 374 Date/Time Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 623 Time Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 1250 Date Time Period Format Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 1251 Date Time Period
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop FA1 that is part of the TableHeader
*<br>Type of Financial Accounting Data used 
* @param inTable table containing this segment
* @return loop object FA1
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopFA1forTableHeader(Table inTable)
  throws OBOEException
{
  Loop loop = inTable.createLoop("FA1");
  inTable.addLoop(loop);
  buildSegmentFA1forTableHeaderFA1(loop);
  buildSegmentFA2forTableHeaderFA1(loop);
  return loop;
  }
/** builds segment FA1 that is part of the TableHeaderFA1
*<br>Type of Financial Accounting Data used 
*<br>To specify the organization controlling the content of the accounting citation, and the purpose associated with the accounting citation
* @param inLoop loop containing this segment
* @return segment object FA1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentFA1forTableHeaderFA1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("FA1");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 559 Agency Qualifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1300 Service, Promotion, Allowance, or Charge Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 248 Allowance or Charge Indicator
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment FA2 that is part of the TableHeaderFA1
*<br>Accounting Data used 
*<br>To specify the detailed accounting data
* @param inLoop loop containing this segment
* @return segment object FA2
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentFA2forTableHeaderFA1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("FA2");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 1196 Breakdown Structure Detail Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1195 Financial Information Code
 de.set("");
/* segment.useDefault(); */
  return segment;
  }
/** builds loop IT1 that is part of the TableDetail
*<br>Baseline Item Data (Invoice) used 
* @param inTable table containing this segment
* @return loop object IT1
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopIT1forTableDetail(Table inTable)
  throws OBOEException
{
  Loop loop = inTable.createLoop("IT1");
  inTable.addLoop(loop);
  buildSegmentIT1forTableDetailIT1(loop);
  buildSegmentCRCforTableDetailIT1(loop);
  buildSegmentQTYforTableDetailIT1(loop);
  buildSegmentCURforTableDetailIT1(loop);
  buildSegmentIT3forTableDetailIT1(loop);
  buildSegmentTXIforTableDetailIT1(loop);
  buildSegmentCTPforTableDetailIT1(loop);
  buildSegmentPAMforTableDetailIT1(loop);
  buildSegmentMEAforTableDetailIT1(loop);
  buildLoopPIDforTableDetailIT1(loop);
  buildSegmentPWKforTableDetailIT1(loop);
  buildSegmentPKGforTableDetailIT1(loop);
  buildSegmentPO4forTableDetailIT1(loop);
  buildSegmentITDforTableDetailIT1(loop);
  buildSegmentREFforTableDetailIT1(loop);
  buildSegmentYNQforTableDetailIT1(loop);
  buildSegmentPERforTableDetailIT1(loop);
  buildSegmentSDQforTableDetailIT1(loop);
  buildSegmentDTMforTableDetailIT1(loop);
  buildSegmentCADforTableDetailIT1(loop);
  buildSegmentL7forTableDetailIT1(loop);
  buildSegmentSRforTableDetailIT1(loop);
  buildLoopSACforTableDetailIT1(loop);
  buildLoopSLNforTableDetailIT1(loop);
  buildLoopN1forTableDetailIT1(loop);
  buildLoopLMforTableDetailIT1(loop);
  buildLoopV1forTableDetailIT1(loop);
  buildLoopFA1forTableDetailIT1(loop);
  return loop;
  }
/** builds segment IT1 that is part of the TableDetailIT1
*<br>Baseline Item Data (Invoice) used 
*<br>To specify the basic and most frequently used line item data for the invoice and related transactions
* @param inLoop loop containing this segment
* @return segment object IT1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentIT1forTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("IT1");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 350 Assigned Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 358 Quantity Invoiced
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 212 Unit Price
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 639 Basis of Unit Price Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(17);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(18);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(19);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(20);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(21);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(22);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(23);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(24);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(25);  // 234 Product/Service ID
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment CRC that is part of the TableDetailIT1
*<br>Conditions Indicator used 
*<br>To supply information on conditions
* @param inLoop loop containing this segment
* @return segment object CRC
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCRCforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("CRC");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 1136 Code Category
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1073 Yes/No Condition or Response Code
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 1321 Condition Indicator
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 1321 Condition Indicator
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 1321 Condition Indicator
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 1321 Condition Indicator
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 1321 Condition Indicator
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment QTY that is part of the TableDetailIT1
*<br>Quantity used 
*<br>To specify quantity information
* @param inLoop loop containing this segment
* @return segment object QTY
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentQTYforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("QTY");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 673 Quantity Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 380 Quantity
  //de.set("");//not required
  CompositeDE  composite = (CompositeDE) segment.buildDE(3);  // C001 Composite Unit of Measure
  de = (DataElement) composite.buildDE(1);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(2);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(3);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(4);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(5);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(6);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(7);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(8);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(9);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(10);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(11);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(12);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(13);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(14);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(15);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) segment.buildDE(4);  // 61 Free-Form Message
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment CUR that is part of the TableDetailIT1
*<br>Currency used 
*<br>To specify the currency (dollars, pounds, francs, etc.) used in a transaction
* @param inLoop loop containing this segment
* @return segment object CUR
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCURforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("CUR");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 98 Entity Identifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 100 Currency Code
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 280 Exchange Rate
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 98 Entity Identifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 100 Currency Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 669 Currency Market/Exchange Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 374 Date/Time Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 374 Date/Time Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 374 Date/Time Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 374 Date/Time Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(17);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(18);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(19);  // 374 Date/Time Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(20);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(21);  // 337 Time
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment IT3 that is part of the TableDetailIT1
*<br>Additional Item Data used 
*<br>To specify additional item details relating to variations between ordered and shipped quantities, or to specify alternate units of measures and quantities
* @param inLoop loop containing this segment
* @return segment object IT3
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentIT3forTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("IT3");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 382 Number of Units Shipped
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 368 Shipment/Order Status Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 383 Quantity Difference
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 371 Change Reason Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TXI that is part of the TableDetailIT1
*<br>Tax Information used 
*<br>To specify tax information
* @param inLoop loop containing this segment
* @return segment object TXI
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTXIforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TXI");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 963 Tax Type Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 782 Monetary Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 954 Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 955 Tax Jurisdiction Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 956 Tax Jurisdiction Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 441 Tax Exempt Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 662 Relationship Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 828 Dollar Basis For Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 325 Tax Identification Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 350 Assigned Identification
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment CTP that is part of the TableDetailIT1
*<br>Pricing Information used 
*<br>To specify pricing information
* @param inLoop loop containing this segment
* @return segment object CTP
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCTPforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("CTP");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 687 Class of Trade Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 236 Price Identifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 212 Unit Price
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 380 Quantity
  //de.set("");//not required
  CompositeDE  composite = (CompositeDE) segment.buildDE(5);  // C001 Composite Unit of Measure
  de = (DataElement) composite.buildDE(1);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(2);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(3);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(4);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(5);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(6);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(7);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(8);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(9);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(10);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(11);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(12);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(13);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(14);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(15);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) segment.buildDE(6);  // 648 Price Multiplier Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 649 Multiplier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 782 Monetary Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 639 Basis of Unit Price Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 499 Condition Value
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 289 Multiple Price Quantity
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment PAM that is part of the TableDetailIT1
*<br>Period Amount used 
*<br>To indicate a quantity, and/or amount for an identified period
* @param inLoop loop containing this segment
* @return segment object PAM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPAMforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("PAM");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 673 Quantity Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 380 Quantity
  //de.set("");//not required
  CompositeDE  composite = (CompositeDE) segment.buildDE(3);  // C001 Composite Unit of Measure
  de = (DataElement) composite.buildDE(1);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(2);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(3);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(4);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(5);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(6);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(7);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(8);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(9);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(10);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(11);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(12);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(13);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(14);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(15);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) segment.buildDE(4);  // 522 Amount Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 782 Monetary Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 344 Unit of Time Period or Interval
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 374 Date/Time Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 374 Date/Time Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 1004 Percent Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 954 Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 1073 Yes/No Condition or Response Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment MEA that is part of the TableDetailIT1
*<br>Measurements used 
*<br>To specify physical measurements or counts, including dimensions, tolerances, variances, and weights  (See Figures Appendix for example of use of C001)
* @param inLoop loop containing this segment
* @return segment object MEA
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentMEAforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("MEA");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 737 Measurement Reference ID Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 738 Measurement Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 739 Measurement Value
  //de.set("");//not required
  CompositeDE  composite = (CompositeDE) segment.buildDE(4);  // C001 Composite Unit of Measure
  de = (DataElement) composite.buildDE(1);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(2);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(3);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(4);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(5);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(6);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(7);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(8);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(9);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(10);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(11);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(12);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(13);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(14);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(15);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) segment.buildDE(5);  // 740 Range Minimum
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 741 Range Maximum
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 935 Measurement Significance Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 936 Measurement Attribute Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 752 Surface/Layer/Position Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 1373 Measurement Method or Device
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop PID that is part of the TableDetailIT1
*<br>Product/Item Description used 
* @param inLoop loop
* @return loop object PID
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopPIDforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("PID");
  inLoop.addLoop(loop);
  buildSegmentPIDforTableDetailIT1PID(loop);
  buildSegmentMEAforTableDetailIT1PID(loop);
  return loop;
  }
/** builds segment PID that is part of the TableDetailIT1PID
*<br>Product/Item Description used 
*<br>To describe a product or process in coded or free-form format
* @param inLoop loop containing this segment
* @return segment object PID
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPIDforTableDetailIT1PID(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("PID");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 349 Item Description Type
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 750 Product/Process Characteristic Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 559 Agency Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 751 Product Description Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 752 Surface/Layer/Position Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 822 Source Subqualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 1073 Yes/No Condition or Response Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 819 Language Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment MEA that is part of the TableDetailIT1PID
*<br>Measurements used 
*<br>To specify physical measurements or counts, including dimensions, tolerances, variances, and weights  (See Figures Appendix for example of use of C001)
* @param inLoop loop containing this segment
* @return segment object MEA
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentMEAforTableDetailIT1PID(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("MEA");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 737 Measurement Reference ID Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 738 Measurement Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 739 Measurement Value
  //de.set("");//not required
  CompositeDE  composite = (CompositeDE) segment.buildDE(4);  // C001 Composite Unit of Measure
  de = (DataElement) composite.buildDE(1);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(2);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(3);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(4);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(5);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(6);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(7);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(8);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(9);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(10);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(11);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(12);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(13);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(14);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(15);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) segment.buildDE(5);  // 740 Range Minimum
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 741 Range Maximum
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 935 Measurement Significance Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 936 Measurement Attribute Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 752 Surface/Layer/Position Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 1373 Measurement Method or Device
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment PWK that is part of the TableDetailIT1
*<br>Paperwork used 
*<br>To identify the type or transmission or both of paperwork or supporting information
* @param inLoop loop containing this segment
* @return segment object PWK
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPWKforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("PWK");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 755 Report Type Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 756 Report Transmission Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 757 Report Copies Needed
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 98 Entity Identifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 66 Identification Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 67 Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 352 Description
  //de.set("");//not required
  CompositeDE  composite = (CompositeDE) segment.buildDE(8);  // C002 Actions Indicated
  de = (DataElement) composite.buildDE(1);  // composite element 704 Paperwork/Report Action Code
  de.set("");
  de = (DataElement) composite.buildDE(2);  // composite element 704 Paperwork/Report Action Code
  de.set("");
  de = (DataElement) composite.buildDE(3);  // composite element 704 Paperwork/Report Action Code
  de.set("");
  de = (DataElement) composite.buildDE(4);  // composite element 704 Paperwork/Report Action Code
  de.set("");
  de = (DataElement) composite.buildDE(5);  // composite element 704 Paperwork/Report Action Code
  de.set("");
  de = (DataElement) segment.buildDE(9);  // 1525 Request Category Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment PKG that is part of the TableDetailIT1
*<br>Marking, Packaging, Loading used 
*<br>To describe marking, packaging, loading, and unloading requirements
* @param inLoop loop containing this segment
* @return segment object PKG
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPKGforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("PKG");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 349 Item Description Type
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 753 Packaging Characteristic Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 559 Agency Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 754 Packaging Description Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 400 Unit Load Option Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment PO4 that is part of the TableDetailIT1
*<br>Item Physical Details used 
*<br>To specify the physical qualities, packaging, weights, and dimensions relating to the item
* @param inLoop loop containing this segment
* @return segment object PO4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPO4forTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("PO4");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 356 Pack
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 357 Size
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 103 Packaging Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 187 Weight Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 384 Gross Weight per Pack
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 385 Gross Volume per Pack
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 82 Length
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 189 Width
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 65 Height
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 810 Inner Pack
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 752 Surface/Layer/Position Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 350 Assigned Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(17);  // 350 Assigned Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(18);  // 1470 Number
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment ITD that is part of the TableDetailIT1
*<br>Terms of Sale/Deferred Terms of Sale used 
*<br>To specify terms of sale
* @param inLoop loop containing this segment
* @return segment object ITD
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentITDforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("ITD");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 336 Terms Type Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 333 Terms Basis Date Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 338 Terms Discount Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 370 Terms Discount Due Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 351 Terms Discount Days Due
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 446 Terms Net Due Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 386 Terms Net Days
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 362 Terms Discount Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 388 Terms Deferred Due Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 389 Deferred Amount Due
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 342 Percent of Invoice Payable
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 765 Day of Month
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 107 Payment Method Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 954 Percent
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment REF that is part of the TableDetailIT1
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop loop containing this segment
* @return segment object REF
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentREFforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("REF");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 128 Reference Identification Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 127 Reference Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 352 Description
  //de.set("");//not required
  CompositeDE  composite = (CompositeDE) segment.buildDE(4);  // C040 Reference Identifier
  de = (DataElement) composite.buildDE(1);  // composite element 128 Reference Identification Qualifier
  de.set("");
  de = (DataElement) composite.buildDE(2);  // composite element 127 Reference Identification
  de.set("");
  de = (DataElement) composite.buildDE(3);  // composite element 128 Reference Identification Qualifier
  de.set("");
  de = (DataElement) composite.buildDE(4);  // composite element 127 Reference Identification
  de.set("");
  de = (DataElement) composite.buildDE(5);  // composite element 128 Reference Identification Qualifier
  de.set("");
  de = (DataElement) composite.buildDE(6);  // composite element 127 Reference Identification
  de.set("");
/* segment.useDefault(); */
  return segment;
  }
/** builds segment YNQ that is part of the TableDetailIT1
*<br>Yes/No Question used 
*<br>To identify and answer yes and no questions, including the date, time, and comments further qualifying the condition
* @param inLoop loop containing this segment
* @return segment object YNQ
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentYNQforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("YNQ");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 1321 Condition Indicator
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 1073 Yes/No Condition or Response Code
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 1250 Date Time Period Format Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 1251 Date Time Period
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 933 Free-Form Message Text
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 933 Free-Form Message Text
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 933 Free-Form Message Text
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 1270 Code List Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 1271 Industry Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 933 Free-Form Message Text
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment PER that is part of the TableDetailIT1
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inLoop loop containing this segment
* @return segment object PER
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPERforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("PER");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 366 Contact Function Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 93 Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 365 Communication Number Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 364 Communication Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 365 Communication Number Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 364 Communication Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 365 Communication Number Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 364 Communication Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 443 Contact Inquiry Reference
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment SDQ that is part of the TableDetailIT1
*<br>Destination Quantity used 
*<br>To specify destination and quantity detail
* @param inLoop loop containing this segment
* @return segment object SDQ
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSDQforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("SDQ");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 355 Unit or Basis for Measurement Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 66 Identification Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 67 Identification Code
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 380 Quantity
 de.set("");
  de = (DataElement) segment.buildDE(5);  // 67 Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 67 Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 67 Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 67 Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 67 Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 67 Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(17);  // 67 Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(18);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(19);  // 67 Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(20);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(21);  // 67 Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(22);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(23);  // 310 Location Identifier
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment DTM that is part of the TableDetailIT1
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop loop containing this segment
* @return segment object DTM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDTMforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("DTM");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 374 Date/Time Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 623 Time Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 1250 Date Time Period Format Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 1251 Date Time Period
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment CAD that is part of the TableDetailIT1
*<br>Carrier Detail used 
*<br>To specify transportation details for the transaction
* @param inLoop loop containing this segment
* @return segment object CAD
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCADforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("CAD");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 91 Transportation Method/Type Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 206 Equipment Initial
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 207 Equipment Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 140 Standard Carrier Alpha Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 387 Routing
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 368 Shipment/Order Status Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 128 Reference Identification Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 127 Reference Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 284 Service Level Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment L7 that is part of the TableDetailIT1
*<br>Tariff Reference used 
*<br>To reference details of the tariff used to arrive at applicable rates or charge
* @param inLoop loop containing this segment
* @return segment object L7
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentL7forTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("L7");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 213 Lading Line Item Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 168 Tariff Agency Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 171 Tariff Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 172 Tariff Section
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 169 Tariff Item Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 170 Tariff Item Part
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 59 Freight Class Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 173 Tariff Supplement Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 46 Ex Parte
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 119 Rate Basis Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 227 Tariff Column
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 294 Tariff Distance
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 295 Distance Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 19 City Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 156 State or Province Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment SR that is part of the TableDetailIT1
*<br>Requested Service Schedule used 
*<br>To identify requested service schedules
* @param inLoop loop containing this segment
* @return segment object SR
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSRforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("SR");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 350 Assigned Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 1430 Day Rotation
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 3 Free Form Message
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 212 Unit Price
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 234 Product/Service ID
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop SAC that is part of the TableDetailIT1
*<br>Service, Promotion, Allowance, or Charge Information used 
* @param inLoop loop
* @return loop object SAC
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopSACforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("SAC");
  inLoop.addLoop(loop);
  buildSegmentSACforTableDetailIT1SAC(loop);
  buildSegmentTXIforTableDetailIT1SAC(loop);
  return loop;
  }
/** builds segment SAC that is part of the TableDetailIT1SAC
*<br>Service, Promotion, Allowance, or Charge Information used 
*<br>To request or identify a service, promotion, allowance, or charge; to specify the amount or percentage for the service, promotion, allowance, or charge
* @param inLoop loop containing this segment
* @return segment object SAC
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSACforTableDetailIT1SAC(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("SAC");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 248 Allowance or Charge Indicator
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1300 Service, Promotion, Allowance, or Charge Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 559 Agency Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 1301 Agency Service, Promotion, Allowance, or Charge Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 610 Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 378 Allowance/Charge Percent Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 332 Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 118 Rate
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 331 Allowance or Charge Method of Handling Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 127 Reference Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 770 Option Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 819 Language Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TXI that is part of the TableDetailIT1SAC
*<br>Tax Information used 
*<br>To specify tax information
* @param inLoop loop containing this segment
* @return segment object TXI
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTXIforTableDetailIT1SAC(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TXI");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 963 Tax Type Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 782 Monetary Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 954 Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 955 Tax Jurisdiction Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 956 Tax Jurisdiction Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 441 Tax Exempt Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 662 Relationship Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 828 Dollar Basis For Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 325 Tax Identification Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 350 Assigned Identification
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop SLN that is part of the TableDetailIT1
*<br>Subline Item Detail used 
* @param inLoop loop
* @return loop object SLN
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopSLNforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("SLN");
  inLoop.addLoop(loop);
  buildSegmentSLNforTableDetailIT1SLN(loop);
  buildSegmentDTMforTableDetailIT1SLN(loop);
  buildSegmentREFforTableDetailIT1SLN(loop);
  buildSegmentPIDforTableDetailIT1SLN(loop);
  buildSegmentSACforTableDetailIT1SLN(loop);
  buildSegmentTC2forTableDetailIT1SLN(loop);
  buildSegmentTXIforTableDetailIT1SLN(loop);
  return loop;
  }
/** builds segment SLN that is part of the TableDetailIT1SLN
*<br>Subline Item Detail used 
*<br>To specify product subline detail item data
* @param inLoop loop containing this segment
* @return segment object SLN
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSLNforTableDetailIT1SLN(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("SLN");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 350 Assigned Identification
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 350 Assigned Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 662 Relationship Code
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 380 Quantity
  //de.set("");//not required
  CompositeDE  composite = (CompositeDE) segment.buildDE(5);  // C001 Composite Unit of Measure
  de = (DataElement) composite.buildDE(1);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(2);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(3);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(4);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(5);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(6);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(7);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(8);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(9);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(10);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(11);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(12);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) composite.buildDE(13);  // composite element 355 Unit or Basis for Measurement Code
  de.set("");
  de = (DataElement) composite.buildDE(14);  // composite element 1018 Exponent
  de.set("");
  de = (DataElement) composite.buildDE(15);  // composite element 649 Multiplier
  de.set("");
  de = (DataElement) segment.buildDE(6);  // 212 Unit Price
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 639 Basis of Unit Price Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 662 Relationship Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(17);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(18);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(19);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(20);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(21);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(22);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(23);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(24);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(25);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(26);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(27);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(28);  // 234 Product/Service ID
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment DTM that is part of the TableDetailIT1SLN
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop loop containing this segment
* @return segment object DTM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDTMforTableDetailIT1SLN(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("DTM");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 374 Date/Time Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 623 Time Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 1250 Date Time Period Format Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 1251 Date Time Period
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment REF that is part of the TableDetailIT1SLN
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop loop containing this segment
* @return segment object REF
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentREFforTableDetailIT1SLN(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("REF");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 128 Reference Identification Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 127 Reference Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 352 Description
  //de.set("");//not required
  CompositeDE  composite = (CompositeDE) segment.buildDE(4);  // C040 Reference Identifier
  de = (DataElement) composite.buildDE(1);  // composite element 128 Reference Identification Qualifier
  de.set("");
  de = (DataElement) composite.buildDE(2);  // composite element 127 Reference Identification
  de.set("");
  de = (DataElement) composite.buildDE(3);  // composite element 128 Reference Identification Qualifier
  de.set("");
  de = (DataElement) composite.buildDE(4);  // composite element 127 Reference Identification
  de.set("");
  de = (DataElement) composite.buildDE(5);  // composite element 128 Reference Identification Qualifier
  de.set("");
  de = (DataElement) composite.buildDE(6);  // composite element 127 Reference Identification
  de.set("");
/* segment.useDefault(); */
  return segment;
  }
/** builds segment PID that is part of the TableDetailIT1SLN
*<br>Product/Item Description used 
*<br>To describe a product or process in coded or free-form format
* @param inLoop loop containing this segment
* @return segment object PID
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPIDforTableDetailIT1SLN(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("PID");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 349 Item Description Type
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 750 Product/Process Characteristic Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 559 Agency Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 751 Product Description Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 752 Surface/Layer/Position Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 822 Source Subqualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 1073 Yes/No Condition or Response Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 819 Language Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment SAC that is part of the TableDetailIT1SLN
*<br>Service, Promotion, Allowance, or Charge Information used 
*<br>To request or identify a service, promotion, allowance, or charge; to specify the amount or percentage for the service, promotion, allowance, or charge
* @param inLoop loop containing this segment
* @return segment object SAC
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSACforTableDetailIT1SLN(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("SAC");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 248 Allowance or Charge Indicator
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1300 Service, Promotion, Allowance, or Charge Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 559 Agency Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 1301 Agency Service, Promotion, Allowance, or Charge Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 610 Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 378 Allowance/Charge Percent Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 332 Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 118 Rate
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 331 Allowance or Charge Method of Handling Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 127 Reference Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 770 Option Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 819 Language Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TC2 that is part of the TableDetailIT1SLN
*<br>Commodity used 
*<br>To identify a commodity or a group of commodities or a tariff page commodity
* @param inLoop loop containing this segment
* @return segment object TC2
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTC2forTableDetailIT1SLN(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TC2");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 23 Commodity Code Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 22 Commodity Code
 de.set("");
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TXI that is part of the TableDetailIT1SLN
*<br>Tax Information used 
*<br>To specify tax information
* @param inLoop loop containing this segment
* @return segment object TXI
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTXIforTableDetailIT1SLN(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TXI");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 963 Tax Type Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 782 Monetary Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 954 Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 955 Tax Jurisdiction Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 956 Tax Jurisdiction Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 441 Tax Exempt Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 662 Relationship Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 828 Dollar Basis For Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 325 Tax Identification Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 350 Assigned Identification
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop N1 that is part of the TableDetailIT1
*<br>Name used 
* @param inLoop loop
* @return loop object N1
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopN1forTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("N1");
  inLoop.addLoop(loop);
  buildSegmentN1forTableDetailIT1N1(loop);
  buildSegmentN2forTableDetailIT1N1(loop);
  buildSegmentN3forTableDetailIT1N1(loop);
  buildSegmentN4forTableDetailIT1N1(loop);
  buildSegmentREFforTableDetailIT1N1(loop);
  buildSegmentPERforTableDetailIT1N1(loop);
  buildSegmentDMGforTableDetailIT1N1(loop);
  return loop;
  }
/** builds segment N1 that is part of the TableDetailIT1N1
*<br>Name used 
*<br>To identify a party by type of organization, name, and code
* @param inLoop loop containing this segment
* @return segment object N1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN1forTableDetailIT1N1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("N1");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 98 Entity Identifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 93 Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 66 Identification Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 67 Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 706 Entity Relationship Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 98 Entity Identifier Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment N2 that is part of the TableDetailIT1N1
*<br>Additional Name Information used 
*<br>To specify additional names or those longer than 35 characters in length
* @param inLoop loop containing this segment
* @return segment object N2
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN2forTableDetailIT1N1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("N2");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 93 Name
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 93 Name
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment N3 that is part of the TableDetailIT1N1
*<br>Address Information used 
*<br>To specify the location of the named party
* @param inLoop loop containing this segment
* @return segment object N3
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN3forTableDetailIT1N1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("N3");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 166 Address Information
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 166 Address Information
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment N4 that is part of the TableDetailIT1N1
*<br>Geographic Location used 
*<br>To specify the geographic place of the named party
* @param inLoop loop containing this segment
* @return segment object N4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN4forTableDetailIT1N1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("N4");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 19 City Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 156 State or Province Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 116 Postal Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 26 Country Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 310 Location Identifier
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment REF that is part of the TableDetailIT1N1
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop loop containing this segment
* @return segment object REF
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentREFforTableDetailIT1N1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("REF");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 128 Reference Identification Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 127 Reference Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 352 Description
  //de.set("");//not required
  CompositeDE  composite = (CompositeDE) segment.buildDE(4);  // C040 Reference Identifier
  de = (DataElement) composite.buildDE(1);  // composite element 128 Reference Identification Qualifier
  de.set("");
  de = (DataElement) composite.buildDE(2);  // composite element 127 Reference Identification
  de.set("");
  de = (DataElement) composite.buildDE(3);  // composite element 128 Reference Identification Qualifier
  de.set("");
  de = (DataElement) composite.buildDE(4);  // composite element 127 Reference Identification
  de.set("");
  de = (DataElement) composite.buildDE(5);  // composite element 128 Reference Identification Qualifier
  de.set("");
  de = (DataElement) composite.buildDE(6);  // composite element 127 Reference Identification
  de.set("");
/* segment.useDefault(); */
  return segment;
  }
/** builds segment PER that is part of the TableDetailIT1N1
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inLoop loop containing this segment
* @return segment object PER
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPERforTableDetailIT1N1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("PER");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 366 Contact Function Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 93 Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 365 Communication Number Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 364 Communication Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 365 Communication Number Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 364 Communication Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 365 Communication Number Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 364 Communication Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 443 Contact Inquiry Reference
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment DMG that is part of the TableDetailIT1N1
*<br>Demographic Information used 
*<br>To supply demographic information
* @param inLoop loop containing this segment
* @return segment object DMG
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDMGforTableDetailIT1N1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("DMG");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 1250 Date Time Period Format Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 1251 Date Time Period
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 1068 Gender Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 1067 Marital Status Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 1109 Race or Ethnicity Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 1066 Citizenship Status Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 26 Country Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 659 Basis of Verification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 380 Quantity
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop LM that is part of the TableDetailIT1
*<br>Code Source Information used 
* @param inLoop loop
* @return loop object LM
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopLMforTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("LM");
  inLoop.addLoop(loop);
  buildSegmentLMforTableDetailIT1LM(loop);
  buildSegmentLQforTableDetailIT1LM(loop);
  return loop;
  }
/** builds segment LM that is part of the TableDetailIT1LM
*<br>Code Source Information used 
*<br>To transmit standard code list identification information
* @param inLoop loop containing this segment
* @return segment object LM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLMforTableDetailIT1LM(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("LM");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 559 Agency Qualifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 822 Source Subqualifier
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment LQ that is part of the TableDetailIT1LM
*<br>Industry Code used 
*<br>Code to transmit standard industry codes
* @param inLoop loop containing this segment
* @return segment object LQ
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLQforTableDetailIT1LM(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("LQ");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 1270 Code List Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 1271 Industry Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop V1 that is part of the TableDetailIT1
*<br>Vessel Identification used 
* @param inLoop loop
* @return loop object V1
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopV1forTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("V1");
  inLoop.addLoop(loop);
  buildSegmentV1forTableDetailIT1V1(loop);
  buildSegmentR4forTableDetailIT1V1(loop);
  buildSegmentDTMforTableDetailIT1V1(loop);
  return loop;
  }
/** builds segment V1 that is part of the TableDetailIT1V1
*<br>Vessel Identification used 
*<br>To provide vessel details and voyage number
* @param inLoop loop containing this segment
* @return segment object V1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentV1forTableDetailIT1V1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("V1");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 597 Vessel Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 182 Vessel Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 26 Country Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 55 Flight/Voyage Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 140 Standard Carrier Alpha Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 249 Vessel Requirement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 854 Vessel Type Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 897 Vessel Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 91 Transportation Method/Type Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment R4 that is part of the TableDetailIT1V1
*<br>Port or Terminal used 
*<br>Contractual or operational port or point relevant to the movement of the cargo
* @param inLoop loop containing this segment
* @return segment object R4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentR4forTableDetailIT1V1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("R4");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 115 Port or Terminal Function Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 114 Port Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 26 Country Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 174 Terminal Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 113 Pier Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 156 State or Province Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment DTM that is part of the TableDetailIT1V1
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop loop containing this segment
* @return segment object DTM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDTMforTableDetailIT1V1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("DTM");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 374 Date/Time Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 623 Time Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 1250 Date Time Period Format Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 1251 Date Time Period
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop FA1 that is part of the TableDetailIT1
*<br>Type of Financial Accounting Data used 
* @param inLoop loop
* @return loop object FA1
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopFA1forTableDetailIT1(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("FA1");
  inLoop.addLoop(loop);
  buildSegmentFA1forTableDetailIT1FA1(loop);
  buildSegmentFA2forTableDetailIT1FA1(loop);
  return loop;
  }
/** builds segment FA1 that is part of the TableDetailIT1FA1
*<br>Type of Financial Accounting Data used 
*<br>To specify the organization controlling the content of the accounting citation, and the purpose associated with the accounting citation
* @param inLoop loop containing this segment
* @return segment object FA1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentFA1forTableDetailIT1FA1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("FA1");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 559 Agency Qualifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1300 Service, Promotion, Allowance, or Charge Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 248 Allowance or Charge Indicator
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment FA2 that is part of the TableDetailIT1FA1
*<br>Accounting Data used 
*<br>To specify the detailed accounting data
* @param inLoop loop containing this segment
* @return segment object FA2
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentFA2forTableDetailIT1FA1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("FA2");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 1196 Breakdown Structure Detail Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1195 Financial Information Code
 de.set("");
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TDS that is part of the TableSummary
*<br>Total Monetary Value Summary used 
*<br>To specify the total invoice discounts and amounts
* @param inTable table containing this segment
* @return segment object TDS
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTDSforTableSummary(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("TDS");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 610 Amount
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 610 Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 610 Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 610 Amount
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TXI that is part of the TableSummary
*<br>Tax Information used 
*<br>To specify tax information
* @param inTable table containing this segment
* @return segment object TXI
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTXIforTableSummary(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("TXI");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 963 Tax Type Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 782 Monetary Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 954 Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 955 Tax Jurisdiction Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 956 Tax Jurisdiction Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 441 Tax Exempt Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 662 Relationship Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 828 Dollar Basis For Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 325 Tax Identification Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 350 Assigned Identification
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment CAD that is part of the TableSummary
*<br>Carrier Detail used 
*<br>To specify transportation details for the transaction
* @param inTable table containing this segment
* @return segment object CAD
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCADforTableSummary(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("CAD");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 91 Transportation Method/Type Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 206 Equipment Initial
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 207 Equipment Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 140 Standard Carrier Alpha Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 387 Routing
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 368 Shipment/Order Status Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 128 Reference Identification Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 127 Reference Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 284 Service Level Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment AMT that is part of the TableSummary
*<br>Monetary Amount used 
*<br>To indicate the total monetary amount
* @param inTable table containing this segment
* @return segment object AMT
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentAMTforTableSummary(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("AMT");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 522 Amount Qualifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 782 Monetary Amount
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 478 Credit/Debit Flag Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop SAC that is part of the TableSummary
*<br>Service, Promotion, Allowance, or Charge Information used 
* @param inTable table containing this segment
* @return loop object SAC
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopSACforTableSummary(Table inTable)
  throws OBOEException
{
  Loop loop = inTable.createLoop("SAC");
  inTable.addLoop(loop);
  buildSegmentSACforTableSummarySAC(loop);
  buildSegmentTXIforTableSummarySAC(loop);
  return loop;
  }
/** builds segment SAC that is part of the TableSummarySAC
*<br>Service, Promotion, Allowance, or Charge Information used 
*<br>To request or identify a service, promotion, allowance, or charge; to specify the amount or percentage for the service, promotion, allowance, or charge
* @param inLoop loop containing this segment
* @return segment object SAC
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSACforTableSummarySAC(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("SAC");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 248 Allowance or Charge Indicator
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1300 Service, Promotion, Allowance, or Charge Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 559 Agency Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 1301 Agency Service, Promotion, Allowance, or Charge Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 610 Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 378 Allowance/Charge Percent Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 332 Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 118 Rate
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 331 Allowance or Charge Method of Handling Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 127 Reference Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 770 Option Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 819 Language Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TXI that is part of the TableSummarySAC
*<br>Tax Information used 
*<br>To specify tax information
* @param inLoop loop containing this segment
* @return segment object TXI
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTXIforTableSummarySAC(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TXI");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 963 Tax Type Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 782 Monetary Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 954 Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 955 Tax Jurisdiction Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 956 Tax Jurisdiction Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 441 Tax Exempt Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 662 Relationship Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 828 Dollar Basis For Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 325 Tax Identification Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 350 Assigned Identification
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop ISS that is part of the TableSummary
*<br>Invoice Shipment Summary used 
* @param inTable table containing this segment
* @return loop object ISS
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopISSforTableSummary(Table inTable)
  throws OBOEException
{
  Loop loop = inTable.createLoop("ISS");
  inTable.addLoop(loop);
  buildSegmentISSforTableSummaryISS(loop);
  buildSegmentPIDforTableSummaryISS(loop);
  return loop;
  }
/** builds segment ISS that is part of the TableSummaryISS
*<br>Invoice Shipment Summary used 
*<br>To specify summary details of total items shipped in terms of quantity, weight, and volume
* @param inLoop loop containing this segment
* @return segment object ISS
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentISSforTableSummaryISS(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("ISS");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 382 Number of Units Shipped
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 81 Weight
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 183 Volume
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 81 Weight
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment PID that is part of the TableSummaryISS
*<br>Product/Item Description used 
*<br>To describe a product or process in coded or free-form format
* @param inLoop loop containing this segment
* @return segment object PID
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPIDforTableSummaryISS(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("PID");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 349 Item Description Type
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 750 Product/Process Characteristic Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 559 Agency Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 751 Product Description Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 752 Surface/Layer/Position Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 822 Source Subqualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 1073 Yes/No Condition or Response Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 819 Language Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment CTT that is part of the TableSummary
*<br>Transaction Totals used 
*<br>To transmit a hash total for a specific element in the transaction set
* @param inTable table containing this segment
* @return segment object CTT
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCTTforTableSummary(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("CTT");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 354 Number of Line Items
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 347 Hash Total
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 81 Weight
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 183 Volume
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 352 Description
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment SE that is part of the TableSummary
*<br>Transaction Set Trailer used 
*<br>To indicate the end of the transaction set and provide the count of the transmitted segments (including the beginning (ST) and ending (SE) segments)
* @param inTable table containing this segment
* @return segment object SE
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSEforTableSummary(Table inTable)
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
