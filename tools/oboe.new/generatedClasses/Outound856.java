import com.americancoders.edi.*;
import com.americancoders.edi.x12.*;

/** code template to build
*<br>class 856 Ship Notice/Manifest
*<br>
* This Draft Standard for Trial Use contains the format and establishes
* the data contents of the Ship Notice/Manifest Transaction Set
* (856) for use within the context of an Electronic Data Interchange
* (EDI) environment. The transaction set can be used to list the
* contents of a shipment of goods as well as additional information
* relating to the shipment, such as order information, product
* description, physical characteristics, type of packaging, marking,
* carrier information, and configuration of goods within the transportation
* equipment. The transaction set enables the sender to describe
* the contents and configuration of a shipment in various levels
* of detail and provides an ordered flexibility to convey information.
*  The sender of this transaction is the organization responsible
* for detailing and communicating the contents of a shipment, or
* shipments, to one or more receivers of the transaction set. The
* receiver of this transaction set can be any organization having
* an interest in the contents of a shipment or information about
* the contents of a shipment.
*@author OBOECodeGenerator
*/
public class Outound856
{
/** constructor for class Outound856
*@throws OBOEException - most likely transactionset not found
*/
public Outound856()
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
  TransactionSet ts = TransactionSetFactory.buildTransactionSet("856");
  fg.addTransactionSet(ts);


  Table table;
  table = ts.getHeaderTable();
  buildSegmentSTforTableHeader(table);
  buildSegmentBSNforTableHeader(table);
  // for (i = 0; i < multipletimes; i++)
  buildSegmentDTMforTableHeader(table);
  table = ts.getDetailTable();
  // for (i = 0; i < multipletimes; i++)
  buildLoopHLforTableDetail(table);
  table = ts.getSummaryTable();
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
  de.set("856");
  de = (DataElement) segment.buildDE(2);  // 329 Transaction Set Control Number
 de.set("");
/* segment.useDefault(); */
  return segment;
  }
/** builds segment BSN that is part of the TableHeader
*<br>Beginning Segment for Ship Notice used 
*<br>To transmit identifying numbers, dates, and other basic data relating to the transaction set
* @param inTable table containing this segment
* @return segment object BSN
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentBSNforTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.createSegment("BSN");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 353 Transaction Set Purpose Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 396 Shipment Identification
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 373 Date
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 337 Time
 de.set("");
  de = (DataElement) segment.buildDE(5);  // 1005 Hierarchical Structure Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 640 Transaction Type Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 641 Status Reason Code
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
/** builds loop HL that is part of the TableDetail
*<br>Hierarchical Level used 
* @param inTable table containing this segment
* @return loop object HL
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopHLforTableDetail(Table inTable)
  throws OBOEException
{
  Loop loop = inTable.createLoop("HL");
  inTable.addLoop(loop);
  buildSegmentHLforTableDetailHL(loop);
  buildSegmentLINforTableDetailHL(loop);
  buildSegmentSN1forTableDetailHL(loop);
  buildSegmentSLNforTableDetailHL(loop);
  buildSegmentPRFforTableDetailHL(loop);
  buildSegmentPO4forTableDetailHL(loop);
  buildSegmentPIDforTableDetailHL(loop);
  buildSegmentMEAforTableDetailHL(loop);
  buildSegmentPWKforTableDetailHL(loop);
  buildSegmentPKGforTableDetailHL(loop);
  buildSegmentTD1forTableDetailHL(loop);
  buildSegmentTD5forTableDetailHL(loop);
  buildSegmentTD3forTableDetailHL(loop);
  buildSegmentTD4forTableDetailHL(loop);
  buildSegmentTSDforTableDetailHL(loop);
  buildSegmentREFforTableDetailHL(loop);
  buildSegmentPERforTableDetailHL(loop);
  buildLoopLH1forTableDetailHL(loop);
  buildLoopCLDforTableDetailHL(loop);
  buildSegmentMANforTableDetailHL(loop);
  buildSegmentDTMforTableDetailHL(loop);
  buildSegmentFOBforTableDetailHL(loop);
  buildSegmentPALforTableDetailHL(loop);
  buildLoopN1forTableDetailHL(loop);
  buildSegmentSDQforTableDetailHL(loop);
  buildSegmentETDforTableDetailHL(loop);
  buildSegmentCURforTableDetailHL(loop);
  buildLoopSACforTableDetailHL(loop);
  buildSegmentGFforTableDetailHL(loop);
  buildSegmentYNQforTableDetailHL(loop);
  buildLoopLMforTableDetailHL(loop);
  buildLoopV1forTableDetailHL(loop);
  return loop;
  }
/** builds segment HL that is part of the TableDetailHL
*<br>Hierarchical Level used 
*<br>To identify dependencies among and the content of hierarchically related groups of data segments
* @param inLoop loop containing this segment
* @return segment object HL
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentHLforTableDetailHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("HL");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 628 Hierarchical ID Number
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 734 Hierarchical Parent ID Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 735 Hierarchical Level Code
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 736 Hierarchical Child Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment LIN that is part of the TableDetailHL
*<br>Item Identification used 
*<br>To specify basic item identification data
* @param inLoop loop containing this segment
* @return segment object LIN
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLINforTableDetailHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("LIN");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 350 Assigned Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 235 Product/Service ID Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 234 Product/Service ID
  //de.set("");//not required
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
/** builds segment SN1 that is part of the TableDetailHL
*<br>Item Detail (Shipment) used 
*<br>To specify line-item detail relative to shipment
* @param inLoop loop containing this segment
* @return segment object SN1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSN1forTableDetailHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("SN1");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 350 Assigned Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 382 Number of Units Shipped
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 355 Unit or Basis for Measurement Code
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 646 Quantity Shipped to Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 330 Quantity Ordered
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 728 Returnable Container Load Make-Up Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 668 Line Item Status Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment SLN that is part of the TableDetailHL
*<br>Subline Item Detail used 
*<br>To specify product subline detail item data
* @param inLoop loop containing this segment
* @return segment object SLN
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSLNforTableDetailHL(Loop inLoop)  throws OBOEException
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
/** builds segment PRF that is part of the TableDetailHL
*<br>Purchase Order Reference used 
*<br>To provide reference to a specific purchase order
* @param inLoop loop containing this segment
* @return segment object PRF
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPRFforTableDetailHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("PRF");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 324 Purchase Order Number
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 328 Release Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 327 Change Order Sequence Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 373 Date
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 350 Assigned Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 367 Contract Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 92 Purchase Order Type Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment PO4 that is part of the TableDetailHL
*<br>Item Physical Details used 
*<br>To specify the physical qualities, packaging, weights, and dimensions relating to the item
* @param inLoop loop containing this segment
* @return segment object PO4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPO4forTableDetailHL(Loop inLoop)  throws OBOEException
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
/** builds segment PID that is part of the TableDetailHL
*<br>Product/Item Description used 
*<br>To describe a product or process in coded or free-form format
* @param inLoop loop containing this segment
* @return segment object PID
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPIDforTableDetailHL(Loop inLoop)  throws OBOEException
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
/** builds segment MEA that is part of the TableDetailHL
*<br>Measurements used 
*<br>To specify physical measurements or counts, including dimensions, tolerances, variances, and weights  (See Figures Appendix for example of use of C001)
* @param inLoop loop containing this segment
* @return segment object MEA
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentMEAforTableDetailHL(Loop inLoop)  throws OBOEException
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
/** builds segment PWK that is part of the TableDetailHL
*<br>Paperwork used 
*<br>To identify the type or transmission or both of paperwork or supporting information
* @param inLoop loop containing this segment
* @return segment object PWK
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPWKforTableDetailHL(Loop inLoop)  throws OBOEException
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
/** builds segment PKG that is part of the TableDetailHL
*<br>Marking, Packaging, Loading used 
*<br>To describe marking, packaging, loading, and unloading requirements
* @param inLoop loop containing this segment
* @return segment object PKG
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPKGforTableDetailHL(Loop inLoop)  throws OBOEException
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
/** builds segment TD1 that is part of the TableDetailHL
*<br>Carrier Details (Quantity and Weight) used 
*<br>To specify the transportation details relative to commodity, weight, and quantity
* @param inLoop loop containing this segment
* @return segment object TD1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD1forTableDetailHL(Loop inLoop)  throws OBOEException
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
/** builds segment TD5 that is part of the TableDetailHL
*<br>Carrier Details (Routing Sequence/Transit Time) used 
*<br>To specify the carrier and sequence of routing and provide transit time information
* @param inLoop loop containing this segment
* @return segment object TD5
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD5forTableDetailHL(Loop inLoop)  throws OBOEException
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
/** builds segment TD3 that is part of the TableDetailHL
*<br>Carrier Details (Equipment) used 
*<br>To specify transportation details relating to the equipment used by the carrier
* @param inLoop loop containing this segment
* @return segment object TD3
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD3forTableDetailHL(Loop inLoop)  throws OBOEException
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
/** builds segment TD4 that is part of the TableDetailHL
*<br>Carrier Details (Special Handling, or Hazardous Materials, or Both) used 
*<br>To specify transportation special handling requirements, or hazardous materials information, or both
* @param inLoop loop containing this segment
* @return segment object TD4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTD4forTableDetailHL(Loop inLoop)  throws OBOEException
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
/** builds segment TSD that is part of the TableDetailHL
*<br>Trailer Shipment Details used 
*<br>To specify details of shipments on a trailer
* @param inLoop loop containing this segment
* @return segment object TSD
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentTSDforTableDetailHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("TSD");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 350 Assigned Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 219 Position
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment REF that is part of the TableDetailHL
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop loop containing this segment
* @return segment object REF
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentREFforTableDetailHL(Loop inLoop)  throws OBOEException
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
/** builds segment PER that is part of the TableDetailHL
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inLoop loop containing this segment
* @return segment object PER
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPERforTableDetailHL(Loop inLoop)  throws OBOEException
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
/** builds loop LH1 that is part of the TableDetailHL
*<br>Hazardous Identification Information used 
* @param inLoop loop
* @return loop object LH1
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopLH1forTableDetailHL(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("LH1");
  inLoop.addLoop(loop);
  buildSegmentLH1forTableDetailHLLH1(loop);
  buildSegmentLH2forTableDetailHLLH1(loop);
  buildSegmentLH3forTableDetailHLLH1(loop);
  buildSegmentLFHforTableDetailHLLH1(loop);
  buildSegmentLEPforTableDetailHLLH1(loop);
  buildSegmentLH4forTableDetailHLLH1(loop);
  buildSegmentLHTforTableDetailHLLH1(loop);
  buildSegmentLHRforTableDetailHLLH1(loop);
  buildSegmentPERforTableDetailHLLH1(loop);
  buildSegmentLHEforTableDetailHLLH1(loop);
  return loop;
  }
/** builds segment LH1 that is part of the TableDetailHLLH1
*<br>Hazardous Identification Information used 
*<br>To specify the hazardous commodity identification reference number and quantity
* @param inLoop loop containing this segment
* @return segment object LH1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLH1forTableDetailHLLH1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("LH1");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 355 Unit or Basis for Measurement Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 80 Lading Quantity
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 277 UN/NA Identification Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 200 Hazardous Materials Page
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 22 Commodity Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 595 Compartment ID Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 665 Residue Indicator Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 254 Packing Group Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 1375 Interim Hazardous Material Regulatory Number
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment LH2 that is part of the TableDetailHLLH1
*<br>Hazardous Classification Information used 
*<br>To specify the hazardous notation and endorsement information
* @param inLoop loop containing this segment
* @return segment object LH2
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLH2forTableDetailHLLH1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("LH2");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 215 Hazardous Classification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 983 Hazardous Class Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 218 Hazardous Placard Notation
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 222 Hazardous Endorsement
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 759 Reportable Quantity Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 408 Temperature
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 408 Temperature
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 408 Temperature
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment LH3 that is part of the TableDetailHLLH1
*<br>Hazardous Material Shipping Name used 
*<br>To specify the hazardous material shipping name and additional descriptive requirements
* @param inLoop loop containing this segment
* @return segment object LH3
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLH3forTableDetailHLLH1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("LH3");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 224 Hazardous Material Shipping Name
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 984 Hazardous Material Shipping Name Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 985 N.O.S. Indicator Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 1073 Yes/No Condition or Response Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment LFH that is part of the TableDetailHLLH1
*<br>Freeform Hazardous Material Information used 
*<br>To uniquely identify the variable information required by government regulation covering the transportation of hazardous material shipments
* @param inLoop loop containing this segment
* @return segment object LFH
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLFHforTableDetailHLLH1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("LFH");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 808 Hazardous Material Shipment Information Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 809 Hazardous Material Shipment Information
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 809 Hazardous Material Shipment Information
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 1023 Hazard Zone Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 380 Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 380 Quantity
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment LEP that is part of the TableDetailHLLH1
*<br>EPA Required Data used 
*<br>To specify the Environmental Protection Agency (EPA) information relating to shipments of hazardous material
* @param inLoop loop containing this segment
* @return segment object LEP
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLEPforTableDetailHLLH1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("LEP");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 806 EPA Waste Stream Number Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 807 Waste Characteristics Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 156 State or Province Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 127 Reference Identification
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment LH4 that is part of the TableDetailHLLH1
*<br>Canadian Dangerous Requirements used 
*<br>To specify additional Transport Canada requirements covering transportation of dangerous goods in Canada
* @param inLoop loop containing this segment
* @return segment object LH4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLH4forTableDetailHLLH1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("LH4");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 238 Emergency Response Plan Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 364 Communication Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 254 Packing Group Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 230 Subsidiary Classification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 230 Subsidiary Classification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 230 Subsidiary Classification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 271 Subsidiary Risk Indicator
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 267 Net Explosive Quantity
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 805 Canadian Hazardous Notation
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 986 Special Commodity Indicator Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 364 Communication Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment LHT that is part of the TableDetailHLLH1
*<br>Transborder Hazardous Requirements used 
*<br>To specify the placard information required by the second government agency when shipment is to cross into another country
* @param inLoop loop containing this segment
* @return segment object LHT
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLHTforTableDetailHLLH1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("LHT");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 215 Hazardous Classification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 218 Hazardous Placard Notation
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 222 Hazardous Endorsement
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment LHR that is part of the TableDetailHLLH1
*<br>Hazardous Material Identifying Reference Numbers used 
*<br>To transmit specific hazardous material reference numbers
* @param inLoop loop containing this segment
* @return segment object LHR
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLHRforTableDetailHLLH1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("LHR");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 128 Reference Identification Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 127 Reference Identification
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 373 Date
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment PER that is part of the TableDetailHLLH1
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inLoop loop containing this segment
* @return segment object PER
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPERforTableDetailHLLH1(Loop inLoop)  throws OBOEException
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
/** builds segment LHE that is part of the TableDetailHLLH1
*<br>Empty Equipment Hazardous Material Information used 
*<br>To specify the "last contained" hazardous shipping name, placard notation, and reference numbers for empty equipment
* @param inLoop loop containing this segment
* @return segment object LHE
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLHEforTableDetailHLLH1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("LHE");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 224 Hazardous Material Shipping Name
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 218 Hazardous Placard Notation
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 128 Reference Identification Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(4);  // 127 Reference Identification
 de.set("");
  de = (DataElement) segment.buildDE(5);  // 759 Reportable Quantity Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop CLD that is part of the TableDetailHL
*<br>Load Detail used 
* @param inLoop loop
* @return loop object CLD
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopCLDforTableDetailHL(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("CLD");
  inLoop.addLoop(loop);
  buildSegmentCLDforTableDetailHLCLD(loop);
  buildSegmentREFforTableDetailHLCLD(loop);
  buildSegmentDTPforTableDetailHLCLD(loop);
  return loop;
  }
/** builds segment CLD that is part of the TableDetailHLCLD
*<br>Load Detail used 
*<br>To specify the number of material loads shipped
* @param inLoop loop containing this segment
* @return segment object CLD
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCLDforTableDetailHLCLD(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("CLD");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 622 Number of Loads
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 382 Number of Units Shipped
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 103 Packaging Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 357 Size
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment REF that is part of the TableDetailHLCLD
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop loop containing this segment
* @return segment object REF
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentREFforTableDetailHLCLD(Loop inLoop)  throws OBOEException
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
/** builds segment DTP that is part of the TableDetailHLCLD
*<br>Date or Time or Period used 
*<br>To specify any or all of a date, a time, or a time period
* @param inLoop loop containing this segment
* @return segment object DTP
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDTPforTableDetailHLCLD(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("DTP");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 374 Date/Time Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 1250 Date Time Period Format Qualifier
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 1251 Date Time Period
 de.set("");
/* segment.useDefault(); */
  return segment;
  }
/** builds segment MAN that is part of the TableDetailHL
*<br>Marks and Numbers used 
*<br>To indicate identifying marks and numbers for shipping containers
* @param inLoop loop containing this segment
* @return segment object MAN
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentMANforTableDetailHL(Loop inLoop)  throws OBOEException
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
/** builds segment DTM that is part of the TableDetailHL
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop loop containing this segment
* @return segment object DTM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDTMforTableDetailHL(Loop inLoop)  throws OBOEException
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
/** builds segment FOB that is part of the TableDetailHL
*<br>F.O.B. Related Instructions used 
*<br>To specify transportation instructions relating to shipment
* @param inLoop loop containing this segment
* @return segment object FOB
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentFOBforTableDetailHL(Loop inLoop)  throws OBOEException
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
/** builds segment PAL that is part of the TableDetailHL
*<br>Pallet Information used 
*<br>To identify the type and physical attributes of the pallet, and, gross weight, gross volume, and height of the load and the pallet
* @param inLoop loop containing this segment
* @return segment object PAL
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPALforTableDetailHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("PAL");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 883 Pallet Type Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 884 Pallet Tiers
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 885 Pallet Blocks
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 356 Pack
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 395 Unit Weight
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 82 Length
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 189 Width
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 65 Height
  //de.set("");//not required
  de = (DataElement) segment.buildDE(10);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(11);  // 384 Gross Weight per Pack
  //de.set("");//not required
  de = (DataElement) segment.buildDE(12);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(13);  // 385 Gross Volume per Pack
  //de.set("");//not required
  de = (DataElement) segment.buildDE(14);  // 355 Unit or Basis for Measurement Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(15);  // 399 Pallet Exchange Code
  //de.set("");//not required
  de = (DataElement) segment.buildDE(16);  // 810 Inner Pack
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds loop N1 that is part of the TableDetailHL
*<br>Name used 
* @param inLoop loop
* @return loop object N1
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopN1forTableDetailHL(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("N1");
  inLoop.addLoop(loop);
  buildSegmentN1forTableDetailHLN1(loop);
  buildSegmentN2forTableDetailHLN1(loop);
  buildSegmentN3forTableDetailHLN1(loop);
  buildSegmentN4forTableDetailHLN1(loop);
  buildSegmentREFforTableDetailHLN1(loop);
  buildSegmentPERforTableDetailHLN1(loop);
  buildSegmentFOBforTableDetailHLN1(loop);
  return loop;
  }
/** builds segment N1 that is part of the TableDetailHLN1
*<br>Name used 
*<br>To identify a party by type of organization, name, and code
* @param inLoop loop containing this segment
* @return segment object N1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN1forTableDetailHLN1(Loop inLoop)  throws OBOEException
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
/** builds segment N2 that is part of the TableDetailHLN1
*<br>Additional Name Information used 
*<br>To specify additional names or those longer than 35 characters in length
* @param inLoop loop containing this segment
* @return segment object N2
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN2forTableDetailHLN1(Loop inLoop)  throws OBOEException
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
/** builds segment N3 that is part of the TableDetailHLN1
*<br>Address Information used 
*<br>To specify the location of the named party
* @param inLoop loop containing this segment
* @return segment object N3
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN3forTableDetailHLN1(Loop inLoop)  throws OBOEException
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
/** builds segment N4 that is part of the TableDetailHLN1
*<br>Geographic Location used 
*<br>To specify the geographic place of the named party
* @param inLoop loop containing this segment
* @return segment object N4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentN4forTableDetailHLN1(Loop inLoop)  throws OBOEException
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
/** builds segment REF that is part of the TableDetailHLN1
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop loop containing this segment
* @return segment object REF
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentREFforTableDetailHLN1(Loop inLoop)  throws OBOEException
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
/** builds segment PER that is part of the TableDetailHLN1
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inLoop loop containing this segment
* @return segment object PER
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentPERforTableDetailHLN1(Loop inLoop)  throws OBOEException
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
/** builds segment FOB that is part of the TableDetailHLN1
*<br>F.O.B. Related Instructions used 
*<br>To specify transportation instructions relating to shipment
* @param inLoop loop containing this segment
* @return segment object FOB
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentFOBforTableDetailHLN1(Loop inLoop)  throws OBOEException
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
/** builds segment SDQ that is part of the TableDetailHL
*<br>Destination Quantity used 
*<br>To specify destination and quantity detail
* @param inLoop loop containing this segment
* @return segment object SDQ
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSDQforTableDetailHL(Loop inLoop)  throws OBOEException
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
/** builds segment ETD that is part of the TableDetailHL
*<br>Excess Transportation Detail used 
*<br>To specify information relating to premium transportation
* @param inLoop loop containing this segment
* @return segment object ETD
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentETDforTableDetailHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("ETD");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 626 Excess Transportation Reason Code
 de.set("");
  de = (DataElement) segment.buildDE(2);  // 627 Excess Transportation Responsibility Code
 de.set("");
  de = (DataElement) segment.buildDE(3);  // 128 Reference Identification Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 127 Reference Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 743 Returnable Container Freight Payment Responsibility Code
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment CUR that is part of the TableDetailHL
*<br>Currency used 
*<br>To specify the currency (dollars, pounds, francs, etc.) used in a transaction
* @param inLoop loop containing this segment
* @return segment object CUR
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCURforTableDetailHL(Loop inLoop)  throws OBOEException
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
/** builds loop SAC that is part of the TableDetailHL
*<br>Service, Promotion, Allowance, or Charge Information used 
* @param inLoop loop
* @return loop object SAC
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopSACforTableDetailHL(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("SAC");
  inLoop.addLoop(loop);
  buildSegmentSACforTableDetailHLSAC(loop);
  buildSegmentCURforTableDetailHLSAC(loop);
  return loop;
  }
/** builds segment SAC that is part of the TableDetailHLSAC
*<br>Service, Promotion, Allowance, or Charge Information used 
*<br>To request or identify a service, promotion, allowance, or charge; to specify the amount or percentage for the service, promotion, allowance, or charge
* @param inLoop loop containing this segment
* @return segment object SAC
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentSACforTableDetailHLSAC(Loop inLoop)  throws OBOEException
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
/** builds segment CUR that is part of the TableDetailHLSAC
*<br>Currency used 
*<br>To specify the currency (dollars, pounds, francs, etc.) used in a transaction
* @param inLoop loop containing this segment
* @return segment object CUR
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentCURforTableDetailHLSAC(Loop inLoop)  throws OBOEException
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
/** builds segment GF that is part of the TableDetailHL
*<br>Furnished Goods and Services used 
*<br>To specify information related to furnished material, equipment, property, information, and services
* @param inLoop loop containing this segment
* @return segment object GF
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentGFforTableDetailHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.createSegment("GF");
  inLoop.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(1);  // 128 Reference Identification Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(2);  // 127 Reference Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(3);  // 367 Contract Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(4);  // 782 Monetary Amount
  //de.set("");//not required
  de = (DataElement) segment.buildDE(5);  // 128 Reference Identification Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(6);  // 127 Reference Identification
  //de.set("");//not required
  de = (DataElement) segment.buildDE(7);  // 328 Release Number
  //de.set("");//not required
  de = (DataElement) segment.buildDE(8);  // 128 Reference Identification Qualifier
  //de.set("");//not required
  de = (DataElement) segment.buildDE(9);  // 127 Reference Identification
  //de.set("");//not required
/* segment.useDefault(); */
  return segment;
  }
/** builds segment YNQ that is part of the TableDetailHL
*<br>Yes/No Question used 
*<br>To identify and answer yes and no questions, including the date, time, and comments further qualifying the condition
* @param inLoop loop containing this segment
* @return segment object YNQ
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentYNQforTableDetailHL(Loop inLoop)  throws OBOEException
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
/** builds loop LM that is part of the TableDetailHL
*<br>Code Source Information used 
* @param inLoop loop
* @return loop object LM
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopLMforTableDetailHL(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("LM");
  inLoop.addLoop(loop);
  buildSegmentLMforTableDetailHLLM(loop);
  buildSegmentLQforTableDetailHLLM(loop);
  return loop;
  }
/** builds segment LM that is part of the TableDetailHLLM
*<br>Code Source Information used 
*<br>To transmit standard code list identification information
* @param inLoop loop containing this segment
* @return segment object LM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLMforTableDetailHLLM(Loop inLoop)  throws OBOEException
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
/** builds segment LQ that is part of the TableDetailHLLM
*<br>Industry Code used 
*<br>Code to transmit standard industry codes
* @param inLoop loop containing this segment
* @return segment object LQ
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentLQforTableDetailHLLM(Loop inLoop)  throws OBOEException
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
/** builds loop V1 that is part of the TableDetailHL
*<br>Vessel Identification used 
* @param inLoop loop
* @return loop object V1
* @throws OBOEException - most likely segment not found
*/
public Loop buildLoopV1forTableDetailHL(Loop inLoop)  throws OBOEException
{
  Loop loop = inLoop.createLoop("V1");
  inLoop.addLoop(loop);
  buildSegmentV1forTableDetailHLV1(loop);
  buildSegmentR4forTableDetailHLV1(loop);
  buildSegmentDTMforTableDetailHLV1(loop);
  return loop;
  }
/** builds segment V1 that is part of the TableDetailHLV1
*<br>Vessel Identification used 
*<br>To provide vessel details and voyage number
* @param inLoop loop containing this segment
* @return segment object V1
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentV1forTableDetailHLV1(Loop inLoop)  throws OBOEException
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
/** builds segment R4 that is part of the TableDetailHLV1
*<br>Port or Terminal used 
*<br>Contractual or operational port or point relevant to the movement of the cargo
* @param inLoop loop containing this segment
* @return segment object R4
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentR4forTableDetailHLV1(Loop inLoop)  throws OBOEException
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
/** builds segment DTM that is part of the TableDetailHLV1
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop loop containing this segment
* @return segment object DTM
* @throws OBOEException - most likely segment not found
*/
public Segment buildSegmentDTMforTableDetailHLV1(Loop inLoop)  throws OBOEException
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
