import com.americancoders.edi.*;
import com.americancoders.edi.x12.*;

/** code template to build
*<br>class 855 Purchase Order Acknowledgment
*<br>
* This Draft Standard for Trial Use contains the format and establishes
* the data contents of the Purchase Order Acknowledgment Transaction
* Set (855) for use within the context of an Electronic Data Interchange
* (EDI) environment. The transaction set can be used to provide
* for customary and established business and industry practice
* relative to a seller's acknowledgment of a buyer's purchase order.
* This transaction set can also be used as notification of a vendor
* generated order. This usage advises a buyer that a vendor has
* or will ship merchandise as prearranged in their partnership.
*@author OBOECodeGenerator
*/
public class Outound855
{
/** constructor for class Outound855
*@throws OBOEException - most likely transactionset not found
*/
public Outound855()
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
  TransactionSet ts = TransactionSetFactory.buildTransactionSet("855");
  fg.addTransactionSet(ts);


  Table table;
  table = ts.getHeaderTable();
  buildSegmentSTforTableHeader(table);
  buildSegmentBAKforTableHeader(table);
  buildSegmentCURforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentREFforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentPERforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentTAXforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentFOBforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentCTPforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentPAMforTableHeader(table);
  buildSegmentCSHforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildLoopSACforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentITDforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentDISforTableHeader(table);
  buildSegmentINCforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentDTMforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentLDTforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentLINforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentSIforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentPIDforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentMEAforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentPWKforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentPKGforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentTD1forTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentTD5forTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentTD3forTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentTD4forTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentMANforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentTXIforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentCTBforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildLoopN9forTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildLoopN1forTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildLoopADVforTableHeader(table);
  table = ts.getDetailTable();
  // for (i = 0; i < multipletimes; i++)
  buildLoopPO1forTableDetail(table);
  table = ts.getSummaryTable();
  buildLoopCTTforTableSummary(table);
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
  de.set("855");
  de = (DataElement) segment.buildDE(2);  // 329 Transaction Set Control Number
 de.set("");
/* segment.useDefault(); */
  return segment;
  }
/** builds segment BAK that is part of the TableHeader
*<br>Beginning Segment for Purchase Order Acknowledgment used 
*<br>To indicate the beginning of the Purchase Order Acknowledgment Transaction Set and transmit identifying numbers and dates
* @param inTable table containing this segment
* @return segment object BAK
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentBAKforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("BAK");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 353 Transaction Set Purpose Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 587 Acknowledgment Type
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 324 Purchase Order Number
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 373 Date
 de.set("");
  de = (DataElement) segment.buildDE(5);  // 328 Release Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 326 Request Reference Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 367 Contract Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 127 Reference Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 640 Transaction Type Code
  //de.set("");//not required
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
/** builds segment TAX that is part of the TableHeader
*<br>Tax Reference used 
*<br>To provide data required for proper notification/determination of applicable taxes applying to the transaction or business described in the transaction
* @param inTable table containing this segment
* @return segment object TAX
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTAXforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("TAX");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 325 Tax Identification Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 441 Tax Exempt Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 1179 Customs Entry Type Group Code
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
/** builds segment CTP that is part of the TableHeader
*<br>Pricing Information used 
*<br>To specify pricing information
* @param inTable table containing this segment
* @return segment object CTP
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCTPforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("CTP");
  inTable.addSegment(segment);
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
/** builds segment CSH that is part of the TableHeader
*<br>Sales Requirements used 
*<br>To specify general conditions or requirements of the sale
* @param inTable table containing this segment
* @return segment object CSH
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCSHforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("CSH");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 563 Sales Requirement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 306 Action Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 610 Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 508 Account Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 559 Agency Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 560 Special Services Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 566 Product/Service Substitution Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 954 Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 1004 Percent Qualifier
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop SAC that is part of the TableHeader
*<br>Service, Promotion, Allowance, or Charge Information used 
* @param inTable table containing this segment
* @return loop object SAC
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopSACforTableHeader(Table inTable)
  throws OBOEException
{
  Loop loop = inTable.createLoop("SAC");
  inTable.addLoop(loop);
  buildSegmentSACforTableHeaderSAC(loop);
  buildSegmentCURforTableHeaderSAC(loop);
  return loop;
  }
/** builds segment SAC that is part of the TableHeaderSAC
*<br>Service, Promotion, Allowance, or Charge Information used 
*<br>To request or identify a service, promotion, allowance, or charge; to specify the amount or percentage for the service, promotion, allowance, or charge
* @param inLoop loop containing this segment
* @return segment object SAC
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSACforTableHeaderSAC(Loop inLoop)  throws OBOEException
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
/** builds segment CUR that is part of the TableHeaderSAC
*<br>Currency used 
*<br>To specify the currency (dollars, pounds, francs, etc.) used in a transaction
* @param inLoop loop containing this segment
* @return segment object CUR
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCURforTableHeaderSAC(Loop inLoop)  throws OBOEException
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
/** builds segment DIS that is part of the TableHeader
*<br>Discount Detail used 
*<br>To specify the exact type and terms of various discount information
* @param inTable table containing this segment
* @return segment object DIS
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDISforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("DIS");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 653 Discount Terms Type Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 654 Discount Base Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 655 Discount Base Value
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 656 Discount Control Limit Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(5);  // 657 Discount Control Limit
 de.set("");
  de = (DataElement) segment.buildDE(6);  // 657 Discount Control Limit
  //de.set("");//not required
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
/** builds segment LDT that is part of the TableHeader
*<br>Lead Time used 
*<br>To specify lead time for availability of products and services
* @param inTable table containing this segment
* @return segment object LDT
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLDTforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("LDT");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 345 Lead Time Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 380 Quantity
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 344 Unit of Time Period or Interval
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 373 Date
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment LIN that is part of the TableHeader
*<br>Item Identification used 
*<br>To specify basic item identification data
* @param inTable table containing this segment
* @return segment object LIN
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLINforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("LIN");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 350 Assigned Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 235 Product/Service ID Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 234 Product/Service ID
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 234 Product/Service ID
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
  de = (DataElement) segment.buildDE(26);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(27);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(28);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(29);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(30);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(31);  // 234 Product/Service ID
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment SI that is part of the TableHeader
*<br>Service Characteristic Identification used 
*<br>To specify service characteristic data
* @param inTable table containing this segment
* @return segment object SI
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSIforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("SI");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 559 Agency Qualifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1000 Service Characteristics Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 234 Product/Service ID
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(17);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(18);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(19);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(20);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(21);  // 234 Product/Service ID
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
/** builds segment TD1 that is part of the TableHeader
*<br>Carrier Details (Quantity and Weight) used 
*<br>To specify the transportation details relative to commodity, weight, and quantity
* @param inTable table containing this segment
* @return segment object TD1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD1forTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("TD1");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 103 Packaging Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 80 Lading Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 23 Commodity Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 22 Commodity Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 79 Lading Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 187 Weight Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 81 Weight
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 183 Volume
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TD5 that is part of the TableHeader
*<br>Carrier Details (Routing Sequence/Transit Time) used 
*<br>To specify the carrier and sequence of routing and provide transit time information
* @param inTable table containing this segment
* @return segment object TD5
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD5forTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("TD5");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 133 Routing Sequence Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 66 Identification Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 67 Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 91 Transportation Method/Type Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 387 Routing
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 368 Shipment/Order Status Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 731 Transit Direction Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 732 Transit Time Direction Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 733 Transit Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 284 Service Level Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 284 Service Level Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 284 Service Level Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 26 Country Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TD3 that is part of the TableHeader
*<br>Carrier Details (Equipment) used 
*<br>To specify transportation details relating to the equipment used by the carrier
* @param inTable table containing this segment
* @return segment object TD3
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD3forTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("TD3");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 40 Equipment Description Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 206 Equipment Initial
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 207 Equipment Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 187 Weight Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 81 Weight
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 102 Ownership Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 407 Seal Status Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 225 Seal Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 24 Equipment Type
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TD4 that is part of the TableHeader
*<br>Carrier Details (Special Handling, or Hazardous Materials, or Both) used 
*<br>To specify transportation special handling requirements, or hazardous materials information, or both
* @param inTable table containing this segment
* @return segment object TD4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD4forTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("TD4");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 152 Special Handling Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 208 Hazardous Material Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 209 Hazardous Material Class Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 1073 Yes/No Condition or Response Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment MAN that is part of the TableHeader
*<br>Marks and Numbers used 
*<br>To indicate identifying marks and numbers for shipping containers
* @param inTable table containing this segment
* @return segment object MAN
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentMANforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("MAN");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 88 Marks and Numbers Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 87 Marks and Numbers
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 87 Marks and Numbers
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 88 Marks and Numbers Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 87 Marks and Numbers
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 87 Marks and Numbers
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TXI that is part of the TableHeader
*<br>Tax Information used 
*<br>To specify tax information
* @param inTable table containing this segment
* @return segment object TXI
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTXIforTableHeader(Table inTable)
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
/** builds segment CTB that is part of the TableHeader
*<br>Restrictions/Conditions used 
*<br>To specify restrictions/conditions (such as shipping, ordering)
* @param inTable table containing this segment
* @return segment object CTB
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCTBforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("CTB");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 688 Restrictions/Conditions Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 673 Quantity Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 522 Amount Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 610 Amount
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
  buildSegmentDTMforTableHeaderN9(loop);
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
/** builds segment DTM that is part of the TableHeaderN9
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop loop containing this segment
* @return segment object DTM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDTMforTableHeaderN9(Loop inLoop)  throws OBOEException
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
  buildSegmentNX2forTableHeaderN1(loop);
  buildSegmentREFforTableHeaderN1(loop);
  buildSegmentPERforTableHeaderN1(loop);
  buildSegmentSIforTableHeaderN1(loop);
  buildSegmentFOBforTableHeaderN1(loop);
  buildSegmentTD1forTableHeaderN1(loop);
  buildSegmentTD5forTableHeaderN1(loop);
  buildSegmentTD3forTableHeaderN1(loop);
  buildSegmentTD4forTableHeaderN1(loop);
  buildSegmentPKGforTableHeaderN1(loop);
  buildSegmentMSGforTableHeaderN1(loop);
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
/** builds segment NX2 that is part of the TableHeaderN1
*<br>Location ID Component used 
*<br>To define types and values of a geographic location
* @param inLoop loop containing this segment
* @return segment object NX2
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentNX2forTableHeaderN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("NX2");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 1106 Address Component Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 166 Address Information
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 1096 County Designator
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
/** builds segment SI that is part of the TableHeaderN1
*<br>Service Characteristic Identification used 
*<br>To specify service characteristic data
* @param inLoop loop containing this segment
* @return segment object SI
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSIforTableHeaderN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("SI");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 559 Agency Qualifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1000 Service Characteristics Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 234 Product/Service ID
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(17);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(18);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(19);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(20);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(21);  // 234 Product/Service ID
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment FOB that is part of the TableHeaderN1
*<br>F.O.B. Related Instructions used 
*<br>To specify transportation instructions relating to shipment
* @param inLoop loop containing this segment
* @return segment object FOB
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentFOBforTableHeaderN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("FOB");
  inLoop.addSegment(segment);
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
/** builds segment TD1 that is part of the TableHeaderN1
*<br>Carrier Details (Quantity and Weight) used 
*<br>To specify the transportation details relative to commodity, weight, and quantity
* @param inLoop loop containing this segment
* @return segment object TD1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD1forTableHeaderN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TD1");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 103 Packaging Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 80 Lading Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 23 Commodity Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 22 Commodity Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 79 Lading Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 187 Weight Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 81 Weight
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 183 Volume
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TD5 that is part of the TableHeaderN1
*<br>Carrier Details (Routing Sequence/Transit Time) used 
*<br>To specify the carrier and sequence of routing and provide transit time information
* @param inLoop loop containing this segment
* @return segment object TD5
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD5forTableHeaderN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TD5");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 133 Routing Sequence Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 66 Identification Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 67 Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 91 Transportation Method/Type Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 387 Routing
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 368 Shipment/Order Status Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 731 Transit Direction Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 732 Transit Time Direction Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 733 Transit Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 284 Service Level Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 284 Service Level Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 284 Service Level Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 26 Country Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TD3 that is part of the TableHeaderN1
*<br>Carrier Details (Equipment) used 
*<br>To specify transportation details relating to the equipment used by the carrier
* @param inLoop loop containing this segment
* @return segment object TD3
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD3forTableHeaderN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TD3");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 40 Equipment Description Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 206 Equipment Initial
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 207 Equipment Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 187 Weight Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 81 Weight
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 102 Ownership Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 407 Seal Status Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 225 Seal Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 24 Equipment Type
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TD4 that is part of the TableHeaderN1
*<br>Carrier Details (Special Handling, or Hazardous Materials, or Both) used 
*<br>To specify transportation special handling requirements, or hazardous materials information, or both
* @param inLoop loop containing this segment
* @return segment object TD4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD4forTableHeaderN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TD4");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 152 Special Handling Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 208 Hazardous Material Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 209 Hazardous Material Class Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 1073 Yes/No Condition or Response Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment PKG that is part of the TableHeaderN1
*<br>Marking, Packaging, Loading used 
*<br>To describe marking, packaging, loading, and unloading requirements
* @param inLoop loop containing this segment
* @return segment object PKG
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPKGforTableHeaderN1(Loop inLoop)  throws OBOEException
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
/** builds segment MSG that is part of the TableHeaderN1
*<br>Message Text used 
*<br>To provide a free-form format that allows the transmission of text information
* @param inLoop loop containing this segment
* @return segment object MSG
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentMSGforTableHeaderN1(Loop inLoop)  throws OBOEException
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
/** builds loop ADV that is part of the TableHeader
*<br>Advertising Demographic Information used 
* @param inTable table containing this segment
* @return loop object ADV
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopADVforTableHeader(Table inTable)
  throws OBOEException
{
  Loop loop = inTable.createLoop("ADV");
  inTable.addLoop(loop);
  buildSegmentADVforTableHeaderADV(loop);
  buildSegmentDTMforTableHeaderADV(loop);
  buildSegmentMTXforTableHeaderADV(loop);
  return loop;
  }
/** builds segment ADV that is part of the TableHeaderADV
*<br>Advertising Demographic Information used 
*<br>To convey advertising demographic information
* @param inLoop loop containing this segment
* @return segment object ADV
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentADVforTableHeaderADV(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("ADV");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 559 Agency Qualifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1000 Service Characteristics Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 740 Range Minimum
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 741 Range Maximum
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 729 Category
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 739 Measurement Value
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment DTM that is part of the TableHeaderADV
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop loop containing this segment
* @return segment object DTM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDTMforTableHeaderADV(Loop inLoop)  throws OBOEException
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
/** builds segment MTX that is part of the TableHeaderADV
*<br>Text used 
*<br>To specify textual data
* @param inLoop loop containing this segment
* @return segment object MTX
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentMTXforTableHeaderADV(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("MTX");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 363 Note Reference Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 1551 Message Text
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 1551 Message Text
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 934 Printer Carriage Control Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop PO1 that is part of the TableDetail
*<br>Baseline Item Data used 
* @param inTable table containing this segment
* @return loop object PO1
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopPO1forTableDetail(Table inTable)
  throws OBOEException
{
  Loop loop = inTable.createLoop("PO1");
  inTable.addLoop(loop);
  buildSegmentPO1forTableDetailPO1(loop);
  buildSegmentLINforTableDetailPO1(loop);
  buildSegmentSIforTableDetailPO1(loop);
  buildSegmentCURforTableDetailPO1(loop);
  buildSegmentPO3forTableDetailPO1(loop);
  buildSegmentCTPforTableDetailPO1(loop);
  buildSegmentPAMforTableDetailPO1(loop);
  buildSegmentMEAforTableDetailPO1(loop);
  buildLoopPIDforTableDetailPO1(loop);
  buildSegmentPWKforTableDetailPO1(loop);
  buildSegmentPO4forTableDetailPO1(loop);
  buildSegmentREFforTableDetailPO1(loop);
  buildSegmentPERforTableDetailPO1(loop);
  buildLoopSACforTableDetailPO1(loop);
  buildSegmentIT8forTableDetailPO1(loop);
  buildSegmentCSHforTableDetailPO1(loop);
  buildSegmentITDforTableDetailPO1(loop);
  buildSegmentDISforTableDetailPO1(loop);
  buildSegmentINCforTableDetailPO1(loop);
  buildSegmentTAXforTableDetailPO1(loop);
  buildSegmentFOBforTableDetailPO1(loop);
  buildSegmentSDQforTableDetailPO1(loop);
  buildSegmentDTMforTableDetailPO1(loop);
  buildSegmentLDTforTableDetailPO1(loop);
  buildSegmentTD1forTableDetailPO1(loop);
  buildSegmentTD5forTableDetailPO1(loop);
  buildSegmentTD3forTableDetailPO1(loop);
  buildSegmentTD4forTableDetailPO1(loop);
  buildLoopACKforTableDetailPO1(loop);
  buildSegmentMANforTableDetailPO1(loop);
  buildSegmentAMTforTableDetailPO1(loop);
  buildSegmentCTBforTableDetailPO1(loop);
  buildSegmentTXIforTableDetailPO1(loop);
  buildLoopQTYforTableDetailPO1(loop);
  buildLoopPKGforTableDetailPO1(loop);
  buildLoopSCHforTableDetailPO1(loop);
  buildLoopN9forTableDetailPO1(loop);
  buildLoopN1forTableDetailPO1(loop);
  buildLoopSLNforTableDetailPO1(loop);
  return loop;
  }
/** builds segment PO1 that is part of the TableDetailPO1
*<br>Baseline Item Data used 
*<br>To specify basic and most frequently used line item data
* @param inLoop loop containing this segment
* @return segment object PO1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPO1forTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("PO1");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 350 Assigned Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 330 Quantity Ordered
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
/** builds segment LIN that is part of the TableDetailPO1
*<br>Item Identification used 
*<br>To specify basic item identification data
* @param inLoop loop containing this segment
* @return segment object LIN
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLINforTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("LIN");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 350 Assigned Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 235 Product/Service ID Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 234 Product/Service ID
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 234 Product/Service ID
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
  de = (DataElement) segment.buildDE(26);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(27);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(28);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(29);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(30);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(31);  // 234 Product/Service ID
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment SI that is part of the TableDetailPO1
*<br>Service Characteristic Identification used 
*<br>To specify service characteristic data
* @param inLoop loop containing this segment
* @return segment object SI
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSIforTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("SI");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 559 Agency Qualifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1000 Service Characteristics Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 234 Product/Service ID
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(17);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(18);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(19);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(20);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(21);  // 234 Product/Service ID
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment CUR that is part of the TableDetailPO1
*<br>Currency used 
*<br>To specify the currency (dollars, pounds, francs, etc.) used in a transaction
* @param inLoop loop containing this segment
* @return segment object CUR
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCURforTableDetailPO1(Loop inLoop)  throws OBOEException
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
/** builds segment PO3 that is part of the TableDetailPO1
*<br>Additional Item Detail used 
*<br>To specify additional item-related data involving variations in normal price/quantity structure
* @param inLoop loop containing this segment
* @return segment object PO3
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPO3forTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("PO3");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 371 Change Reason Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 236 Price Identifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 212 Unit Price
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 639 Basis of Unit Price Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 380 Quantity
 de.set("");
  de = (DataElement) segment.buildDE(7);  // 355 Unit or Basis for Measurement Code
 de.set("");
  de = (DataElement) segment.buildDE(8);  // 352 Description
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment CTP that is part of the TableDetailPO1
*<br>Pricing Information used 
*<br>To specify pricing information
* @param inLoop loop containing this segment
* @return segment object CTP
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCTPforTableDetailPO1(Loop inLoop)  throws OBOEException
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
/** builds segment PAM that is part of the TableDetailPO1
*<br>Period Amount used 
*<br>To indicate a quantity, and/or amount for an identified period
* @param inLoop loop containing this segment
* @return segment object PAM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPAMforTableDetailPO1(Loop inLoop)  throws OBOEException
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
/** builds segment MEA that is part of the TableDetailPO1
*<br>Measurements used 
*<br>To specify physical measurements or counts, including dimensions, tolerances, variances, and weights  (See Figures Appendix for example of use of C001)
* @param inLoop loop containing this segment
* @return segment object MEA
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentMEAforTableDetailPO1(Loop inLoop)  throws OBOEException
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
/** builds loop PID that is part of the TableDetailPO1
*<br>Product/Item Description used 
* @param inLoop loop
* @return loop object PID
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopPIDforTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("PID");
  inLoop.addLoop(loop);
  buildSegmentPIDforTableDetailPO1PID(loop);
  buildSegmentMEAforTableDetailPO1PID(loop);
  return loop;
  }
/** builds segment PID that is part of the TableDetailPO1PID
*<br>Product/Item Description used 
*<br>To describe a product or process in coded or free-form format
* @param inLoop loop containing this segment
* @return segment object PID
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPIDforTableDetailPO1PID(Loop inLoop)  throws OBOEException
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
/** builds segment MEA that is part of the TableDetailPO1PID
*<br>Measurements used 
*<br>To specify physical measurements or counts, including dimensions, tolerances, variances, and weights  (See Figures Appendix for example of use of C001)
* @param inLoop loop containing this segment
* @return segment object MEA
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentMEAforTableDetailPO1PID(Loop inLoop)  throws OBOEException
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
/** builds segment PWK that is part of the TableDetailPO1
*<br>Paperwork used 
*<br>To identify the type or transmission or both of paperwork or supporting information
* @param inLoop loop containing this segment
* @return segment object PWK
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPWKforTableDetailPO1(Loop inLoop)  throws OBOEException
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
/** builds segment PO4 that is part of the TableDetailPO1
*<br>Item Physical Details used 
*<br>To specify the physical qualities, packaging, weights, and dimensions relating to the item
* @param inLoop loop containing this segment
* @return segment object PO4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPO4forTableDetailPO1(Loop inLoop)  throws OBOEException
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
/** builds segment REF that is part of the TableDetailPO1
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop loop containing this segment
* @return segment object REF
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentREFforTableDetailPO1(Loop inLoop)  throws OBOEException
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
/** builds segment PER that is part of the TableDetailPO1
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inLoop loop containing this segment
* @return segment object PER
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPERforTableDetailPO1(Loop inLoop)  throws OBOEException
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
/** builds loop SAC that is part of the TableDetailPO1
*<br>Service, Promotion, Allowance, or Charge Information used 
* @param inLoop loop
* @return loop object SAC
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopSACforTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("SAC");
  inLoop.addLoop(loop);
  buildSegmentSACforTableDetailPO1SAC(loop);
  buildSegmentCURforTableDetailPO1SAC(loop);
  return loop;
  }
/** builds segment SAC that is part of the TableDetailPO1SAC
*<br>Service, Promotion, Allowance, or Charge Information used 
*<br>To request or identify a service, promotion, allowance, or charge; to specify the amount or percentage for the service, promotion, allowance, or charge
* @param inLoop loop containing this segment
* @return segment object SAC
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSACforTableDetailPO1SAC(Loop inLoop)  throws OBOEException
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
/** builds segment CUR that is part of the TableDetailPO1SAC
*<br>Currency used 
*<br>To specify the currency (dollars, pounds, francs, etc.) used in a transaction
* @param inLoop loop containing this segment
* @return segment object CUR
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCURforTableDetailPO1SAC(Loop inLoop)  throws OBOEException
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
/** builds segment IT8 that is part of the TableDetailPO1
*<br>Conditions of Sale used 
*<br>To specify general conditions or requirements and to detail conditions for substitution of alternate products
* @param inLoop loop containing this segment
* @return segment object IT8
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentIT8forTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("IT8");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 563 Sales Requirement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 306 Action Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 610 Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 508 Account Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 559 Agency Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 566 Product/Service Substitution Code
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
  de = (DataElement) segment.buildDE(26);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(27);  // 234 Product/Service ID
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment CSH that is part of the TableDetailPO1
*<br>Sales Requirements used 
*<br>To specify general conditions or requirements of the sale
* @param inLoop loop containing this segment
* @return segment object CSH
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCSHforTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("CSH");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 563 Sales Requirement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 306 Action Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 610 Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 508 Account Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 559 Agency Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 560 Special Services Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 566 Product/Service Substitution Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 954 Percent
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 1004 Percent Qualifier
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment ITD that is part of the TableDetailPO1
*<br>Terms of Sale/Deferred Terms of Sale used 
*<br>To specify terms of sale
* @param inLoop loop containing this segment
* @return segment object ITD
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentITDforTableDetailPO1(Loop inLoop)  throws OBOEException
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
/** builds segment DIS that is part of the TableDetailPO1
*<br>Discount Detail used 
*<br>To specify the exact type and terms of various discount information
* @param inLoop loop containing this segment
* @return segment object DIS
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDISforTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("DIS");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 653 Discount Terms Type Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 654 Discount Base Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 655 Discount Base Value
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 656 Discount Control Limit Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(5);  // 657 Discount Control Limit
 de.set("");
  de = (DataElement) segment.buildDE(6);  // 657 Discount Control Limit
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment INC that is part of the TableDetailPO1
*<br>Installment Information used 
*<br>To specify installment billing arrangement
* @param inLoop loop containing this segment
* @return segment object INC
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentINCforTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("INC");
  inLoop.addSegment(segment);
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
/** builds segment TAX that is part of the TableDetailPO1
*<br>Tax Reference used 
*<br>To provide data required for proper notification/determination of applicable taxes applying to the transaction or business described in the transaction
* @param inLoop loop containing this segment
* @return segment object TAX
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTAXforTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TAX");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 325 Tax Identification Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 441 Tax Exempt Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 1179 Customs Entry Type Group Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment FOB that is part of the TableDetailPO1
*<br>F.O.B. Related Instructions used 
*<br>To specify transportation instructions relating to shipment
* @param inLoop loop containing this segment
* @return segment object FOB
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentFOBforTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("FOB");
  inLoop.addSegment(segment);
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
/** builds segment SDQ that is part of the TableDetailPO1
*<br>Destination Quantity used 
*<br>To specify destination and quantity detail
* @param inLoop loop containing this segment
* @return segment object SDQ
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSDQforTableDetailPO1(Loop inLoop)  throws OBOEException
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
/** builds segment DTM that is part of the TableDetailPO1
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop loop containing this segment
* @return segment object DTM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDTMforTableDetailPO1(Loop inLoop)  throws OBOEException
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
/** builds segment LDT that is part of the TableDetailPO1
*<br>Lead Time used 
*<br>To specify lead time for availability of products and services
* @param inLoop loop containing this segment
* @return segment object LDT
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLDTforTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("LDT");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 345 Lead Time Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 380 Quantity
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 344 Unit of Time Period or Interval
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 373 Date
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TD1 that is part of the TableDetailPO1
*<br>Carrier Details (Quantity and Weight) used 
*<br>To specify the transportation details relative to commodity, weight, and quantity
* @param inLoop loop containing this segment
* @return segment object TD1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD1forTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TD1");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 103 Packaging Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 80 Lading Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 23 Commodity Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 22 Commodity Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 79 Lading Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 187 Weight Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 81 Weight
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 183 Volume
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TD5 that is part of the TableDetailPO1
*<br>Carrier Details (Routing Sequence/Transit Time) used 
*<br>To specify the carrier and sequence of routing and provide transit time information
* @param inLoop loop containing this segment
* @return segment object TD5
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD5forTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TD5");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 133 Routing Sequence Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 66 Identification Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 67 Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 91 Transportation Method/Type Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 387 Routing
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 368 Shipment/Order Status Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 731 Transit Direction Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 732 Transit Time Direction Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 733 Transit Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 284 Service Level Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 284 Service Level Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 284 Service Level Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 26 Country Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TD3 that is part of the TableDetailPO1
*<br>Carrier Details (Equipment) used 
*<br>To specify transportation details relating to the equipment used by the carrier
* @param inLoop loop containing this segment
* @return segment object TD3
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD3forTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TD3");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 40 Equipment Description Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 206 Equipment Initial
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 207 Equipment Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 187 Weight Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 81 Weight
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 102 Ownership Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 407 Seal Status Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 225 Seal Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 24 Equipment Type
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TD4 that is part of the TableDetailPO1
*<br>Carrier Details (Special Handling, or Hazardous Materials, or Both) used 
*<br>To specify transportation special handling requirements, or hazardous materials information, or both
* @param inLoop loop containing this segment
* @return segment object TD4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD4forTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TD4");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 152 Special Handling Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 208 Hazardous Material Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 209 Hazardous Material Class Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 1073 Yes/No Condition or Response Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop ACK that is part of the TableDetailPO1
*<br>Line Item Acknowledgment used 
* @param inLoop loop
* @return loop object ACK
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopACKforTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("ACK");
  inLoop.addLoop(loop);
  buildSegmentACKforTableDetailPO1ACK(loop);
  buildSegmentDTMforTableDetailPO1ACK(loop);
  return loop;
  }
/** builds segment ACK that is part of the TableDetailPO1ACK
*<br>Line Item Acknowledgment used 
*<br>To acknowledge the ordered quantities and specify the ready date for a specific line item
* @param inLoop loop containing this segment
* @return segment object ACK
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentACKforTableDetailPO1ACK(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("ACK");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 668 Line Item Status Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 374 Date/Time Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 326 Request Reference Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 234 Product/Service ID
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
  de = (DataElement) segment.buildDE(27);  // 559 Agency Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(28);  // 822 Source Subqualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(29);  // 1271 Industry Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment DTM that is part of the TableDetailPO1ACK
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop loop containing this segment
* @return segment object DTM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDTMforTableDetailPO1ACK(Loop inLoop)  throws OBOEException
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
/** builds segment MAN that is part of the TableDetailPO1
*<br>Marks and Numbers used 
*<br>To indicate identifying marks and numbers for shipping containers
* @param inLoop loop containing this segment
* @return segment object MAN
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentMANforTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("MAN");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 88 Marks and Numbers Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 87 Marks and Numbers
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 87 Marks and Numbers
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 88 Marks and Numbers Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 87 Marks and Numbers
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 87 Marks and Numbers
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment AMT that is part of the TableDetailPO1
*<br>Monetary Amount used 
*<br>To indicate the total monetary amount
* @param inLoop loop containing this segment
* @return segment object AMT
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentAMTforTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("AMT");
  inLoop.addSegment(segment);
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
/** builds segment CTB that is part of the TableDetailPO1
*<br>Restrictions/Conditions used 
*<br>To specify restrictions/conditions (such as shipping, ordering)
* @param inLoop loop containing this segment
* @return segment object CTB
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCTBforTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("CTB");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 688 Restrictions/Conditions Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 673 Quantity Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 522 Amount Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 610 Amount
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TXI that is part of the TableDetailPO1
*<br>Tax Information used 
*<br>To specify tax information
* @param inLoop loop containing this segment
* @return segment object TXI
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTXIforTableDetailPO1(Loop inLoop)  throws OBOEException
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
/** builds loop QTY that is part of the TableDetailPO1
*<br>Quantity used 
* @param inLoop loop
* @return loop object QTY
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopQTYforTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("QTY");
  inLoop.addLoop(loop);
  buildSegmentQTYforTableDetailPO1QTY(loop);
  buildSegmentSIforTableDetailPO1QTY(loop);
  return loop;
  }
/** builds segment QTY that is part of the TableDetailPO1QTY
*<br>Quantity used 
*<br>To specify quantity information
* @param inLoop loop containing this segment
* @return segment object QTY
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentQTYforTableDetailPO1QTY(Loop inLoop)  throws OBOEException
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
/** builds segment SI that is part of the TableDetailPO1QTY
*<br>Service Characteristic Identification used 
*<br>To specify service characteristic data
* @param inLoop loop containing this segment
* @return segment object SI
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSIforTableDetailPO1QTY(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("SI");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 559 Agency Qualifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1000 Service Characteristics Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 234 Product/Service ID
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(17);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(18);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(19);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(20);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(21);  // 234 Product/Service ID
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop PKG that is part of the TableDetailPO1
*<br>Marking, Packaging, Loading used 
* @param inLoop loop
* @return loop object PKG
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopPKGforTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("PKG");
  inLoop.addLoop(loop);
  buildSegmentPKGforTableDetailPO1PKG(loop);
  buildSegmentMEAforTableDetailPO1PKG(loop);
  return loop;
  }
/** builds segment PKG that is part of the TableDetailPO1PKG
*<br>Marking, Packaging, Loading used 
*<br>To describe marking, packaging, loading, and unloading requirements
* @param inLoop loop containing this segment
* @return segment object PKG
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPKGforTableDetailPO1PKG(Loop inLoop)  throws OBOEException
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
/** builds segment MEA that is part of the TableDetailPO1PKG
*<br>Measurements used 
*<br>To specify physical measurements or counts, including dimensions, tolerances, variances, and weights  (See Figures Appendix for example of use of C001)
* @param inLoop loop containing this segment
* @return segment object MEA
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentMEAforTableDetailPO1PKG(Loop inLoop)  throws OBOEException
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
/** builds loop SCH that is part of the TableDetailPO1
*<br>Line Item Schedule used 
* @param inLoop loop
* @return loop object SCH
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopSCHforTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("SCH");
  inLoop.addLoop(loop);
  buildSegmentSCHforTableDetailPO1SCH(loop);
  buildSegmentTD1forTableDetailPO1SCH(loop);
  buildSegmentTD5forTableDetailPO1SCH(loop);
  buildSegmentTD3forTableDetailPO1SCH(loop);
  buildSegmentTD4forTableDetailPO1SCH(loop);
  buildSegmentREFforTableDetailPO1SCH(loop);
  return loop;
  }
/** builds segment SCH that is part of the TableDetailPO1SCH
*<br>Line Item Schedule used 
*<br>To specify the data for scheduling a specific line-item
* @param inLoop loop containing this segment
* @return segment object SCH
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSCHforTableDetailPO1SCH(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("SCH");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 380 Quantity
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 355 Unit or Basis for Measurement Code
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 98 Entity Identifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 93 Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 374 Date/Time Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(6);  // 373 Date
 de.set("");
  de = (DataElement) segment.buildDE(7);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 374 Date/Time Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 326 Request Reference Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 350 Assigned Identification
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TD1 that is part of the TableDetailPO1SCH
*<br>Carrier Details (Quantity and Weight) used 
*<br>To specify the transportation details relative to commodity, weight, and quantity
* @param inLoop loop containing this segment
* @return segment object TD1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD1forTableDetailPO1SCH(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TD1");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 103 Packaging Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 80 Lading Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 23 Commodity Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 22 Commodity Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 79 Lading Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 187 Weight Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 81 Weight
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 183 Volume
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TD5 that is part of the TableDetailPO1SCH
*<br>Carrier Details (Routing Sequence/Transit Time) used 
*<br>To specify the carrier and sequence of routing and provide transit time information
* @param inLoop loop containing this segment
* @return segment object TD5
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD5forTableDetailPO1SCH(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TD5");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 133 Routing Sequence Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 66 Identification Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 67 Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 91 Transportation Method/Type Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 387 Routing
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 368 Shipment/Order Status Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 731 Transit Direction Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 732 Transit Time Direction Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 733 Transit Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 284 Service Level Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 284 Service Level Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 284 Service Level Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 26 Country Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TD3 that is part of the TableDetailPO1SCH
*<br>Carrier Details (Equipment) used 
*<br>To specify transportation details relating to the equipment used by the carrier
* @param inLoop loop containing this segment
* @return segment object TD3
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD3forTableDetailPO1SCH(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TD3");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 40 Equipment Description Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 206 Equipment Initial
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 207 Equipment Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 187 Weight Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 81 Weight
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 102 Ownership Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 407 Seal Status Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 225 Seal Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 24 Equipment Type
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TD4 that is part of the TableDetailPO1SCH
*<br>Carrier Details (Special Handling, or Hazardous Materials, or Both) used 
*<br>To specify transportation special handling requirements, or hazardous materials information, or both
* @param inLoop loop containing this segment
* @return segment object TD4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD4forTableDetailPO1SCH(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TD4");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 152 Special Handling Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 208 Hazardous Material Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 209 Hazardous Material Class Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 1073 Yes/No Condition or Response Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment REF that is part of the TableDetailPO1SCH
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop loop containing this segment
* @return segment object REF
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentREFforTableDetailPO1SCH(Loop inLoop)  throws OBOEException
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
/** builds loop N9 that is part of the TableDetailPO1
*<br>Reference Identification used 
* @param inLoop loop
* @return loop object N9
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopN9forTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("N9");
  inLoop.addLoop(loop);
  buildSegmentN9forTableDetailPO1N9(loop);
  buildSegmentDTMforTableDetailPO1N9(loop);
  buildSegmentMSGforTableDetailPO1N9(loop);
  return loop;
  }
/** builds segment N9 that is part of the TableDetailPO1N9
*<br>Reference Identification used 
*<br>To transmit identifying information as specified by the Reference Identification Qualifier
* @param inLoop loop containing this segment
* @return segment object N9
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN9forTableDetailPO1N9(Loop inLoop)  throws OBOEException
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
/** builds segment DTM that is part of the TableDetailPO1N9
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop loop containing this segment
* @return segment object DTM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDTMforTableDetailPO1N9(Loop inLoop)  throws OBOEException
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
/** builds segment MSG that is part of the TableDetailPO1N9
*<br>Message Text used 
*<br>To provide a free-form format that allows the transmission of text information
* @param inLoop loop containing this segment
* @return segment object MSG
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentMSGforTableDetailPO1N9(Loop inLoop)  throws OBOEException
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
/** builds loop N1 that is part of the TableDetailPO1
*<br>Name used 
* @param inLoop loop
* @return loop object N1
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopN1forTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("N1");
  inLoop.addLoop(loop);
  buildSegmentN1forTableDetailPO1N1(loop);
  buildSegmentN2forTableDetailPO1N1(loop);
  buildSegmentN3forTableDetailPO1N1(loop);
  buildSegmentN4forTableDetailPO1N1(loop);
  buildSegmentNX2forTableDetailPO1N1(loop);
  buildSegmentREFforTableDetailPO1N1(loop);
  buildSegmentPERforTableDetailPO1N1(loop);
  buildSegmentSIforTableDetailPO1N1(loop);
  buildSegmentDTMforTableDetailPO1N1(loop);
  buildSegmentFOBforTableDetailPO1N1(loop);
  buildSegmentSCHforTableDetailPO1N1(loop);
  buildSegmentTD1forTableDetailPO1N1(loop);
  buildSegmentTD5forTableDetailPO1N1(loop);
  buildSegmentTD3forTableDetailPO1N1(loop);
  buildSegmentTD4forTableDetailPO1N1(loop);
  buildSegmentQTYforTableDetailPO1N1(loop);
  buildSegmentPKGforTableDetailPO1N1(loop);
  return loop;
  }
/** builds segment N1 that is part of the TableDetailPO1N1
*<br>Name used 
*<br>To identify a party by type of organization, name, and code
* @param inLoop loop containing this segment
* @return segment object N1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN1forTableDetailPO1N1(Loop inLoop)  throws OBOEException
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
/** builds segment N2 that is part of the TableDetailPO1N1
*<br>Additional Name Information used 
*<br>To specify additional names or those longer than 35 characters in length
* @param inLoop loop containing this segment
* @return segment object N2
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN2forTableDetailPO1N1(Loop inLoop)  throws OBOEException
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
/** builds segment N3 that is part of the TableDetailPO1N1
*<br>Address Information used 
*<br>To specify the location of the named party
* @param inLoop loop containing this segment
* @return segment object N3
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN3forTableDetailPO1N1(Loop inLoop)  throws OBOEException
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
/** builds segment N4 that is part of the TableDetailPO1N1
*<br>Geographic Location used 
*<br>To specify the geographic place of the named party
* @param inLoop loop containing this segment
* @return segment object N4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN4forTableDetailPO1N1(Loop inLoop)  throws OBOEException
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
/** builds segment NX2 that is part of the TableDetailPO1N1
*<br>Location ID Component used 
*<br>To define types and values of a geographic location
* @param inLoop loop containing this segment
* @return segment object NX2
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentNX2forTableDetailPO1N1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("NX2");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 1106 Address Component Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 166 Address Information
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 1096 County Designator
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment REF that is part of the TableDetailPO1N1
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop loop containing this segment
* @return segment object REF
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentREFforTableDetailPO1N1(Loop inLoop)  throws OBOEException
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
/** builds segment PER that is part of the TableDetailPO1N1
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inLoop loop containing this segment
* @return segment object PER
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPERforTableDetailPO1N1(Loop inLoop)  throws OBOEException
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
/** builds segment SI that is part of the TableDetailPO1N1
*<br>Service Characteristic Identification used 
*<br>To specify service characteristic data
* @param inLoop loop containing this segment
* @return segment object SI
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSIforTableDetailPO1N1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("SI");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 559 Agency Qualifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1000 Service Characteristics Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 234 Product/Service ID
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(17);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(18);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(19);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(20);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(21);  // 234 Product/Service ID
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment DTM that is part of the TableDetailPO1N1
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop loop containing this segment
* @return segment object DTM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDTMforTableDetailPO1N1(Loop inLoop)  throws OBOEException
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
/** builds segment FOB that is part of the TableDetailPO1N1
*<br>F.O.B. Related Instructions used 
*<br>To specify transportation instructions relating to shipment
* @param inLoop loop containing this segment
* @return segment object FOB
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentFOBforTableDetailPO1N1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("FOB");
  inLoop.addSegment(segment);
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
/** builds segment SCH that is part of the TableDetailPO1N1
*<br>Line Item Schedule used 
*<br>To specify the data for scheduling a specific line-item
* @param inLoop loop containing this segment
* @return segment object SCH
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSCHforTableDetailPO1N1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("SCH");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 380 Quantity
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 355 Unit or Basis for Measurement Code
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 98 Entity Identifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 93 Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 374 Date/Time Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(6);  // 373 Date
 de.set("");
  de = (DataElement) segment.buildDE(7);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 374 Date/Time Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 337 Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 326 Request Reference Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 350 Assigned Identification
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TD1 that is part of the TableDetailPO1N1
*<br>Carrier Details (Quantity and Weight) used 
*<br>To specify the transportation details relative to commodity, weight, and quantity
* @param inLoop loop containing this segment
* @return segment object TD1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD1forTableDetailPO1N1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TD1");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 103 Packaging Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 80 Lading Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 23 Commodity Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 22 Commodity Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 79 Lading Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 187 Weight Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 81 Weight
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 183 Volume
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TD5 that is part of the TableDetailPO1N1
*<br>Carrier Details (Routing Sequence/Transit Time) used 
*<br>To specify the carrier and sequence of routing and provide transit time information
* @param inLoop loop containing this segment
* @return segment object TD5
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD5forTableDetailPO1N1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TD5");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 133 Routing Sequence Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 66 Identification Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 67 Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 91 Transportation Method/Type Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 387 Routing
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 368 Shipment/Order Status Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 731 Transit Direction Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 732 Transit Time Direction Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 733 Transit Time
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 284 Service Level Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 284 Service Level Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 284 Service Level Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 26 Country Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TD3 that is part of the TableDetailPO1N1
*<br>Carrier Details (Equipment) used 
*<br>To specify transportation details relating to the equipment used by the carrier
* @param inLoop loop containing this segment
* @return segment object TD3
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD3forTableDetailPO1N1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TD3");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 40 Equipment Description Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 206 Equipment Initial
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 207 Equipment Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 187 Weight Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 81 Weight
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 102 Ownership Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 407 Seal Status Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 225 Seal Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 24 Equipment Type
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment TD4 that is part of the TableDetailPO1N1
*<br>Carrier Details (Special Handling, or Hazardous Materials, or Both) used 
*<br>To specify transportation special handling requirements, or hazardous materials information, or both
* @param inLoop loop containing this segment
* @return segment object TD4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD4forTableDetailPO1N1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TD4");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 152 Special Handling Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 208 Hazardous Material Code Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 209 Hazardous Material Class Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 352 Description
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 1073 Yes/No Condition or Response Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment QTY that is part of the TableDetailPO1N1
*<br>Quantity used 
*<br>To specify quantity information
* @param inLoop loop containing this segment
* @return segment object QTY
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentQTYforTableDetailPO1N1(Loop inLoop)  throws OBOEException
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
/** builds segment PKG that is part of the TableDetailPO1N1
*<br>Marking, Packaging, Loading used 
*<br>To describe marking, packaging, loading, and unloading requirements
* @param inLoop loop containing this segment
* @return segment object PKG
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPKGforTableDetailPO1N1(Loop inLoop)  throws OBOEException
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
/** builds loop SLN that is part of the TableDetailPO1
*<br>Subline Item Detail used 
* @param inLoop loop
* @return loop object SLN
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopSLNforTableDetailPO1(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("SLN");
  inLoop.addLoop(loop);
  buildSegmentSLNforTableDetailPO1SLN(loop);
  buildSegmentMSGforTableDetailPO1SLN(loop);
  buildSegmentSIforTableDetailPO1SLN(loop);
  buildSegmentPIDforTableDetailPO1SLN(loop);
  buildSegmentPO3forTableDetailPO1SLN(loop);
  buildSegmentCTPforTableDetailPO1SLN(loop);
  buildSegmentPAMforTableDetailPO1SLN(loop);
  buildSegmentACKforTableDetailPO1SLN(loop);
  buildLoopSACforTableDetailPO1SLN(loop);
  buildSegmentDTMforTableDetailPO1SLN(loop);
  buildSegmentPO4forTableDetailPO1SLN(loop);
  buildSegmentTAXforTableDetailPO1SLN(loop);
  buildSegmentADVforTableDetailPO1SLN(loop);
  buildLoopQTYforTableDetailPO1SLN(loop);
  buildLoopN9forTableDetailPO1SLN(loop);
  buildLoopN1forTableDetailPO1SLN(loop);
  return loop;
  }
/** builds segment SLN that is part of the TableDetailPO1SLN
*<br>Subline Item Detail used 
*<br>To specify product subline detail item data
* @param inLoop loop containing this segment
* @return segment object SLN
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSLNforTableDetailPO1SLN(Loop inLoop)  throws OBOEException
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
/** builds segment MSG that is part of the TableDetailPO1SLN
*<br>Message Text used 
*<br>To provide a free-form format that allows the transmission of text information
* @param inLoop loop containing this segment
* @return segment object MSG
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentMSGforTableDetailPO1SLN(Loop inLoop)  throws OBOEException
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
/** builds segment SI that is part of the TableDetailPO1SLN
*<br>Service Characteristic Identification used 
*<br>To specify service characteristic data
* @param inLoop loop containing this segment
* @return segment object SI
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSIforTableDetailPO1SLN(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("SI");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 559 Agency Qualifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1000 Service Characteristics Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 234 Product/Service ID
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(17);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(18);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(19);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(20);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(21);  // 234 Product/Service ID
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment PID that is part of the TableDetailPO1SLN
*<br>Product/Item Description used 
*<br>To describe a product or process in coded or free-form format
* @param inLoop loop containing this segment
* @return segment object PID
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPIDforTableDetailPO1SLN(Loop inLoop)  throws OBOEException
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
/** builds segment PO3 that is part of the TableDetailPO1SLN
*<br>Additional Item Detail used 
*<br>To specify additional item-related data involving variations in normal price/quantity structure
* @param inLoop loop containing this segment
* @return segment object PO3
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPO3forTableDetailPO1SLN(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("PO3");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 371 Change Reason Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 236 Price Identifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 212 Unit Price
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 639 Basis of Unit Price Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 380 Quantity
 de.set("");
  de = (DataElement) segment.buildDE(7);  // 355 Unit or Basis for Measurement Code
 de.set("");
  de = (DataElement) segment.buildDE(8);  // 352 Description
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment CTP that is part of the TableDetailPO1SLN
*<br>Pricing Information used 
*<br>To specify pricing information
* @param inLoop loop containing this segment
* @return segment object CTP
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCTPforTableDetailPO1SLN(Loop inLoop)  throws OBOEException
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
/** builds segment PAM that is part of the TableDetailPO1SLN
*<br>Period Amount used 
*<br>To indicate a quantity, and/or amount for an identified period
* @param inLoop loop containing this segment
* @return segment object PAM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPAMforTableDetailPO1SLN(Loop inLoop)  throws OBOEException
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
/** builds segment ACK that is part of the TableDetailPO1SLN
*<br>Line Item Acknowledgment used 
*<br>To acknowledge the ordered quantities and specify the ready date for a specific line item
* @param inLoop loop containing this segment
* @return segment object ACK
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentACKforTableDetailPO1SLN(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("ACK");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 668 Line Item Status Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 374 Date/Time Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 326 Request Reference Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 234 Product/Service ID
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
  de = (DataElement) segment.buildDE(27);  // 559 Agency Qualifier Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(28);  // 822 Source Subqualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(29);  // 1271 Industry Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop SAC that is part of the TableDetailPO1SLN
*<br>Service, Promotion, Allowance, or Charge Information used 
* @param inLoop loop
* @return loop object SAC
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopSACforTableDetailPO1SLN(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("SAC");
  inLoop.addLoop(loop);
  buildSegmentSACforTableDetailPO1SLNSAC(loop);
  buildSegmentCURforTableDetailPO1SLNSAC(loop);
  return loop;
  }
/** builds segment SAC that is part of the TableDetailPO1SLNSAC
*<br>Service, Promotion, Allowance, or Charge Information used 
*<br>To request or identify a service, promotion, allowance, or charge; to specify the amount or percentage for the service, promotion, allowance, or charge
* @param inLoop loop containing this segment
* @return segment object SAC
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSACforTableDetailPO1SLNSAC(Loop inLoop)  throws OBOEException
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
/** builds segment CUR that is part of the TableDetailPO1SLNSAC
*<br>Currency used 
*<br>To specify the currency (dollars, pounds, francs, etc.) used in a transaction
* @param inLoop loop containing this segment
* @return segment object CUR
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCURforTableDetailPO1SLNSAC(Loop inLoop)  throws OBOEException
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
/** builds segment DTM that is part of the TableDetailPO1SLN
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop loop containing this segment
* @return segment object DTM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDTMforTableDetailPO1SLN(Loop inLoop)  throws OBOEException
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
/** builds segment PO4 that is part of the TableDetailPO1SLN
*<br>Item Physical Details used 
*<br>To specify the physical qualities, packaging, weights, and dimensions relating to the item
* @param inLoop loop containing this segment
* @return segment object PO4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPO4forTableDetailPO1SLN(Loop inLoop)  throws OBOEException
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
/** builds segment TAX that is part of the TableDetailPO1SLN
*<br>Tax Reference used 
*<br>To provide data required for proper notification/determination of applicable taxes applying to the transaction or business described in the transaction
* @param inLoop loop containing this segment
* @return segment object TAX
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTAXforTableDetailPO1SLN(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TAX");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 325 Tax Identification Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 309 Location Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 310 Location Identifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 441 Tax Exempt Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 1179 Customs Entry Type Group Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment ADV that is part of the TableDetailPO1SLN
*<br>Advertising Demographic Information used 
*<br>To convey advertising demographic information
* @param inLoop loop containing this segment
* @return segment object ADV
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentADVforTableDetailPO1SLN(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("ADV");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 559 Agency Qualifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1000 Service Characteristics Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 740 Range Minimum
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 741 Range Maximum
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 729 Category
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 739 Measurement Value
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop QTY that is part of the TableDetailPO1SLN
*<br>Quantity used 
* @param inLoop loop
* @return loop object QTY
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopQTYforTableDetailPO1SLN(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("QTY");
  inLoop.addLoop(loop);
  buildSegmentQTYforTableDetailPO1SLNQTY(loop);
  buildSegmentSIforTableDetailPO1SLNQTY(loop);
  return loop;
  }
/** builds segment QTY that is part of the TableDetailPO1SLNQTY
*<br>Quantity used 
*<br>To specify quantity information
* @param inLoop loop containing this segment
* @return segment object QTY
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentQTYforTableDetailPO1SLNQTY(Loop inLoop)  throws OBOEException
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
/** builds segment SI that is part of the TableDetailPO1SLNQTY
*<br>Service Characteristic Identification used 
*<br>To specify service characteristic data
* @param inLoop loop containing this segment
* @return segment object SI
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSIforTableDetailPO1SLNQTY(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("SI");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 559 Agency Qualifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1000 Service Characteristics Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 234 Product/Service ID
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(17);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(18);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(19);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(20);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(21);  // 234 Product/Service ID
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop N9 that is part of the TableDetailPO1SLN
*<br>Reference Identification used 
* @param inLoop loop
* @return loop object N9
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopN9forTableDetailPO1SLN(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("N9");
  inLoop.addLoop(loop);
  buildSegmentN9forTableDetailPO1SLNN9(loop);
  buildSegmentDTMforTableDetailPO1SLNN9(loop);
  buildSegmentMSGforTableDetailPO1SLNN9(loop);
  return loop;
  }
/** builds segment N9 that is part of the TableDetailPO1SLNN9
*<br>Reference Identification used 
*<br>To transmit identifying information as specified by the Reference Identification Qualifier
* @param inLoop loop containing this segment
* @return segment object N9
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN9forTableDetailPO1SLNN9(Loop inLoop)  throws OBOEException
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
/** builds segment DTM that is part of the TableDetailPO1SLNN9
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop loop containing this segment
* @return segment object DTM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDTMforTableDetailPO1SLNN9(Loop inLoop)  throws OBOEException
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
/** builds segment MSG that is part of the TableDetailPO1SLNN9
*<br>Message Text used 
*<br>To provide a free-form format that allows the transmission of text information
* @param inLoop loop containing this segment
* @return segment object MSG
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentMSGforTableDetailPO1SLNN9(Loop inLoop)  throws OBOEException
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
/** builds loop N1 that is part of the TableDetailPO1SLN
*<br>Name used 
* @param inLoop loop
* @return loop object N1
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopN1forTableDetailPO1SLN(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("N1");
  inLoop.addLoop(loop);
  buildSegmentN1forTableDetailPO1SLNN1(loop);
  buildSegmentN2forTableDetailPO1SLNN1(loop);
  buildSegmentN3forTableDetailPO1SLNN1(loop);
  buildSegmentN4forTableDetailPO1SLNN1(loop);
  buildSegmentNX2forTableDetailPO1SLNN1(loop);
  buildSegmentREFforTableDetailPO1SLNN1(loop);
  buildSegmentPERforTableDetailPO1SLNN1(loop);
  buildSegmentSIforTableDetailPO1SLNN1(loop);
  return loop;
  }
/** builds segment N1 that is part of the TableDetailPO1SLNN1
*<br>Name used 
*<br>To identify a party by type of organization, name, and code
* @param inLoop loop containing this segment
* @return segment object N1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN1forTableDetailPO1SLNN1(Loop inLoop)  throws OBOEException
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
/** builds segment N2 that is part of the TableDetailPO1SLNN1
*<br>Additional Name Information used 
*<br>To specify additional names or those longer than 35 characters in length
* @param inLoop loop containing this segment
* @return segment object N2
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN2forTableDetailPO1SLNN1(Loop inLoop)  throws OBOEException
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
/** builds segment N3 that is part of the TableDetailPO1SLNN1
*<br>Address Information used 
*<br>To specify the location of the named party
* @param inLoop loop containing this segment
* @return segment object N3
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN3forTableDetailPO1SLNN1(Loop inLoop)  throws OBOEException
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
/** builds segment N4 that is part of the TableDetailPO1SLNN1
*<br>Geographic Location used 
*<br>To specify the geographic place of the named party
* @param inLoop loop containing this segment
* @return segment object N4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN4forTableDetailPO1SLNN1(Loop inLoop)  throws OBOEException
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
/** builds segment NX2 that is part of the TableDetailPO1SLNN1
*<br>Location ID Component used 
*<br>To define types and values of a geographic location
* @param inLoop loop containing this segment
* @return segment object NX2
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentNX2forTableDetailPO1SLNN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("NX2");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 1106 Address Component Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 166 Address Information
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 1096 County Designator
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment REF that is part of the TableDetailPO1SLNN1
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop loop containing this segment
* @return segment object REF
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentREFforTableDetailPO1SLNN1(Loop inLoop)  throws OBOEException
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
/** builds segment PER that is part of the TableDetailPO1SLNN1
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inLoop loop containing this segment
* @return segment object PER
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPERforTableDetailPO1SLNN1(Loop inLoop)  throws OBOEException
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
/** builds segment SI that is part of the TableDetailPO1SLNN1
*<br>Service Characteristic Identification used 
*<br>To specify service characteristic data
* @param inLoop loop containing this segment
* @return segment object SI
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSIforTableDetailPO1SLNN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("SI");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 559 Agency Qualifier Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1000 Service Characteristics Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 234 Product/Service ID
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(17);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(18);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(19);  // 234 Product/Service ID
  //de.set("");//not required
  de = (DataElement) segment.buildDE(20);  // 1000 Service Characteristics Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(21);  // 234 Product/Service ID
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop CTT that is part of the TableSummary
*<br>Transaction Totals used 
* @param inTable table containing this segment
* @return loop object CTT
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopCTTforTableSummary(Table inTable)
  throws OBOEException
{
  Loop loop = inTable.createLoop("CTT");
  inTable.addLoop(loop);
  buildSegmentCTTforTableSummaryCTT(loop);
  buildSegmentAMTforTableSummaryCTT(loop);
  return loop;
  }
/** builds segment CTT that is part of the TableSummaryCTT
*<br>Transaction Totals used 
*<br>To transmit a hash total for a specific element in the transaction set
* @param inLoop loop containing this segment
* @return segment object CTT
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCTTforTableSummaryCTT(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("CTT");
  inLoop.addSegment(segment);
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
/** builds segment AMT that is part of the TableSummaryCTT
*<br>Monetary Amount used 
*<br>To indicate the total monetary amount
* @param inLoop loop containing this segment
* @return segment object AMT
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentAMTforTableSummaryCTT(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("AMT");
  inLoop.addSegment(segment);
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
