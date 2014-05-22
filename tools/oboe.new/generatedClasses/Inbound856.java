import com.americancoders.edi.*;
import com.americancoders.edi.x12.*;
import java.io.*;

/** code template to parse
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
public class Inbound856
{
/** constructor for class Inbound856
*@param inFileName - String filename to be parsed
*@throws OBOEException - most likely transactionset not found
*@throws IOException - most likely input file not found
*/
public Inbound856(String inFileName)
  throws OBOEException, IOException
{
  File fileToParse = new File(inFileName);
  if (fileToParse.exists() == false)
     throw new OBOEException("File: "+ inFileName + " does not exist.");
  X12DocumentHandler dh = new X12DocumentHandler();
  dh.startParsing(new FileReader(fileToParse));
  X12Envelope env = (X12Envelope) dh.getEnvelope();

  Table table;
  FunctionalGroup fg;
  TransactionSet ts;
  int fgCount = env.getFunctionalGroupCount();
  int tsCount = -1;
  for (int fgPosition = 0; fgPosition < fgCount; fgPosition++)
     {
        fg = env.getFunctionalGroup(fgPosition);
        tsCount = fg.getTransactionSetCount();
        for (int tsPosition = 0; tsPosition < tsCount; tsPosition++)
           {
             ts = fg.getTransactionSet(tsPosition);
             table = ts.getHeaderTable();
             if (table != null)
               {
                 extractSegmentSTfromTableHeader(table);
                 extractSegmentBSNfromTableHeader(table);
                 extractSegmentDTMfromTableHeader(table);

              }
             table = ts.getDetailTable();
             if (table != null)
               {
                 extractLoopHLfromTableDetail(table);

              }
            table = ts.getSummaryTable();
             if (table != null)
               {
                 extractSegmentCTTfromTableSummary(table);
                 extractSegmentSEfromTableSummary(table);

              }
           }
     }

}
/** extract data from segment ST that is part of the TableHeader
*<br>Transaction Set Header used 
*<br>To indicate the start of a transaction set and to assign a control number
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSTfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.getSegment("ST");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 143 Transaction Set Identifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 329 Transaction Set Control Number
  if (de != null)
    de.get();
  }
/** extract data from segment BSN that is part of the TableHeader
*<br>Beginning Segment for Ship Notice used 
*<br>To transmit identifying numbers, dates, and other basic data relating to the transaction set
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentBSNfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.getSegment("BSN");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 353 Transaction Set Purpose Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 396 Shipment Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 337 Time
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 1005 Hierarchical Structure Code
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 640 Transaction Type Code
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 641 Status Reason Code
  if (de != null)
    de.get();
  }
/** extract data from segment DTM that is part of the TableHeader
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDTMfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("DTM");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("DTM", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 374 Date/Time Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 373 Date
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 337 Time
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 623 Time Code
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 1250 Date Time Period Format Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 1251 Date Time Period
     if (de != null)
       de.get();
    }
  }
/** extract data from loop HL that is part of TableDetail
*<br>Hierarchical Level used 
* @param inTable table containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopHLfromTableDetail(Table inTable)
  throws OBOEException
{
  Loop loop;
  int numberInVector = inTable.getCount("HL");
  for (int i = 0; i <  numberInVector; i++)
   {
     loop = inTable.getLoop("HL", i);
     if (loop == null) return;
     extractSegmentHLfromTableDetailLoopHL(loop);
     extractSegmentLINfromTableDetailLoopHL(loop);
     extractSegmentSN1fromTableDetailLoopHL(loop);
     extractSegmentSLNfromTableDetailLoopHL(loop);
     extractSegmentPRFfromTableDetailLoopHL(loop);
     extractSegmentPO4fromTableDetailLoopHL(loop);
     extractSegmentPIDfromTableDetailLoopHL(loop);
     extractSegmentMEAfromTableDetailLoopHL(loop);
     extractSegmentPWKfromTableDetailLoopHL(loop);
     extractSegmentPKGfromTableDetailLoopHL(loop);
     extractSegmentTD1fromTableDetailLoopHL(loop);
     extractSegmentTD5fromTableDetailLoopHL(loop);
     extractSegmentTD3fromTableDetailLoopHL(loop);
     extractSegmentTD4fromTableDetailLoopHL(loop);
     extractSegmentTSDfromTableDetailLoopHL(loop);
     extractSegmentREFfromTableDetailLoopHL(loop);
     extractSegmentPERfromTableDetailLoopHL(loop);
     extractLoopLH1fromTableDetailLoopHL(loop);
     extractLoopCLDfromTableDetailLoopHL(loop);
     extractSegmentMANfromTableDetailLoopHL(loop);
     extractSegmentDTMfromTableDetailLoopHL(loop);
     extractSegmentFOBfromTableDetailLoopHL(loop);
     extractSegmentPALfromTableDetailLoopHL(loop);
     extractLoopN1fromTableDetailLoopHL(loop);
     extractSegmentSDQfromTableDetailLoopHL(loop);
     extractSegmentETDfromTableDetailLoopHL(loop);
     extractSegmentCURfromTableDetailLoopHL(loop);
     extractLoopSACfromTableDetailLoopHL(loop);
     extractSegmentGFfromTableDetailLoopHL(loop);
     extractSegmentYNQfromTableDetailLoopHL(loop);
     extractLoopLMfromTableDetailLoopHL(loop);
     extractLoopV1fromTableDetailLoopHL(loop);
    }
  }
/** extract data from segment HL that is part of the TableDetailLoopHL
*<br>Hierarchical Level used 
*<br>To identify dependencies among and the content of hierarchically related groups of data segments
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentHLfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("HL");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 628 Hierarchical ID Number
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 734 Hierarchical Parent ID Number
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 735 Hierarchical Level Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 736 Hierarchical Child Code
  if (de != null)
    de.get();
  }
/** extract data from segment LIN that is part of the TableDetailLoopHL
*<br>Item Identification used 
*<br>To specify basic item identification data
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLINfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("LIN");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 350 Assigned Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(10);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(11);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(12);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(13);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(14);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(15);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(16);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(17);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(18);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(19);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(20);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(21);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(22);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(23);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(24);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(25);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(26);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(27);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(28);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(29);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(30);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(31);  // 234 Product/Service ID
  if (de != null)
    de.get();
  }
/** extract data from segment SN1 that is part of the TableDetailLoopHL
*<br>Item Detail (Shipment) used 
*<br>To specify line-item detail relative to shipment
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSN1fromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("SN1");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 350 Assigned Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 382 Number of Units Shipped
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 646 Quantity Shipped to Date
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 330 Quantity Ordered
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 728 Returnable Container Load Make-Up Code
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 668 Line Item Status Code
  if (de != null)
    de.get();
  }
/** extract data from segment SLN that is part of the TableDetailLoopHL
*<br>Subline Item Detail used 
*<br>To specify product subline detail item data
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSLNfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("SLN");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("SLN", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 350 Assigned Identification
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 350 Assigned Identification
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 662 Relationship Code
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 380 Quantity
       if (de != null)
         de.get();
         CompositeDE  composite = (CompositeDE) segment.getCompositeDE(5);  // C001 Composite Unit of Measure
  if (composite == null)
    return;
  de = composite.getDataElement(1);  // composite element 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = composite.getDataElement(2);  // composite element 1018 Exponent
  if (de != null)
    de.get();
  de = composite.getDataElement(3);  // composite element 649 Multiplier
  if (de != null)
    de.get();
  de = composite.getDataElement(4);  // composite element 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = composite.getDataElement(5);  // composite element 1018 Exponent
  if (de != null)
    de.get();
  de = composite.getDataElement(6);  // composite element 649 Multiplier
  if (de != null)
    de.get();
  de = composite.getDataElement(7);  // composite element 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = composite.getDataElement(8);  // composite element 1018 Exponent
  if (de != null)
    de.get();
  de = composite.getDataElement(9);  // composite element 649 Multiplier
  if (de != null)
    de.get();
  de = composite.getDataElement(10);  // composite element 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = composite.getDataElement(11);  // composite element 1018 Exponent
  if (de != null)
    de.get();
  de = composite.getDataElement(12);  // composite element 649 Multiplier
  if (de != null)
    de.get();
  de = composite.getDataElement(13);  // composite element 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = composite.getDataElement(14);  // composite element 1018 Exponent
  if (de != null)
    de.get();
  de = composite.getDataElement(15);  // composite element 649 Multiplier
  if (de != null)
    de.get();
       de = segment.getDataElement(6);       // 212 Unit Price
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 639 Basis of Unit Price Code
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 662 Relationship Code
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(12);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(13);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(14);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(15);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(16);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(17);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(18);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(19);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(20);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(21);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(22);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(23);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(24);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(25);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(26);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(27);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(28);       // 234 Product/Service ID
       if (de != null)
         de.get();
    }
  }
/** extract data from segment PRF that is part of the TableDetailLoopHL
*<br>Purchase Order Reference used 
*<br>To provide reference to a specific purchase order
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPRFfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("PRF");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 324 Purchase Order Number
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 328 Release Number
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 327 Change Order Sequence Number
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 350 Assigned Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 367 Contract Number
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 92 Purchase Order Type Code
  if (de != null)
    de.get();
  }
/** extract data from segment PO4 that is part of the TableDetailLoopHL
*<br>Item Physical Details used 
*<br>To specify the physical qualities, packaging, weights, and dimensions relating to the item
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPO4fromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("PO4");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 356 Pack
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 357 Size
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 103 Packaging Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 187 Weight Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 384 Gross Weight per Pack
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 385 Gross Volume per Pack
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(10);  // 82 Length
  if (de != null)
    de.get();
  de = segment.getDataElement(11);  // 189 Width
  if (de != null)
    de.get();
  de = segment.getDataElement(12);  // 65 Height
  if (de != null)
    de.get();
  de = segment.getDataElement(13);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(14);  // 810 Inner Pack
  if (de != null)
    de.get();
  de = segment.getDataElement(15);  // 752 Surface/Layer/Position Code
  if (de != null)
    de.get();
  de = segment.getDataElement(16);  // 350 Assigned Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(17);  // 350 Assigned Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(18);  // 1470 Number
  if (de != null)
    de.get();
  }
/** extract data from segment PID that is part of the TableDetailLoopHL
*<br>Product/Item Description used 
*<br>To describe a product or process in coded or free-form format
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPIDfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("PID");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("PID", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 349 Item Description Type
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 750 Product/Process Characteristic Code
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 559 Agency Qualifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 751 Product Description Code
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 352 Description
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 752 Surface/Layer/Position Code
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 822 Source Subqualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 1073 Yes/No Condition or Response Code
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 819 Language Code
       if (de != null)
         de.get();
    }
  }
/** extract data from segment MEA that is part of the TableDetailLoopHL
*<br>Measurements used 
*<br>To specify physical measurements or counts, including dimensions, tolerances, variances, and weights  (See Figures Appendix for example of use of C001)
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMEAfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("MEA");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("MEA", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 737 Measurement Reference ID Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 738 Measurement Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 739 Measurement Value
       if (de != null)
         de.get();
         CompositeDE  composite = (CompositeDE) segment.getCompositeDE(4);  // C001 Composite Unit of Measure
  if (composite == null)
    return;
  de = composite.getDataElement(1);  // composite element 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = composite.getDataElement(2);  // composite element 1018 Exponent
  if (de != null)
    de.get();
  de = composite.getDataElement(3);  // composite element 649 Multiplier
  if (de != null)
    de.get();
  de = composite.getDataElement(4);  // composite element 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = composite.getDataElement(5);  // composite element 1018 Exponent
  if (de != null)
    de.get();
  de = composite.getDataElement(6);  // composite element 649 Multiplier
  if (de != null)
    de.get();
  de = composite.getDataElement(7);  // composite element 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = composite.getDataElement(8);  // composite element 1018 Exponent
  if (de != null)
    de.get();
  de = composite.getDataElement(9);  // composite element 649 Multiplier
  if (de != null)
    de.get();
  de = composite.getDataElement(10);  // composite element 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = composite.getDataElement(11);  // composite element 1018 Exponent
  if (de != null)
    de.get();
  de = composite.getDataElement(12);  // composite element 649 Multiplier
  if (de != null)
    de.get();
  de = composite.getDataElement(13);  // composite element 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = composite.getDataElement(14);  // composite element 1018 Exponent
  if (de != null)
    de.get();
  de = composite.getDataElement(15);  // composite element 649 Multiplier
  if (de != null)
    de.get();
       de = segment.getDataElement(5);       // 740 Range Minimum
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 741 Range Maximum
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 935 Measurement Significance Code
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 936 Measurement Attribute Code
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 752 Surface/Layer/Position Code
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 1373 Measurement Method or Device
       if (de != null)
         de.get();
    }
  }
/** extract data from segment PWK that is part of the TableDetailLoopHL
*<br>Paperwork used 
*<br>To identify the type or transmission or both of paperwork or supporting information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPWKfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("PWK");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("PWK", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 755 Report Type Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 756 Report Transmission Code
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 757 Report Copies Needed
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 98 Entity Identifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 66 Identification Code Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 67 Identification Code
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 352 Description
       if (de != null)
         de.get();
         CompositeDE  composite = (CompositeDE) segment.getCompositeDE(8);  // C002 Actions Indicated
  if (composite == null)
    return;
  de = composite.getDataElement(1);  // composite element 704 Paperwork/Report Action Code
  if (de != null)
    de.get();
  de = composite.getDataElement(2);  // composite element 704 Paperwork/Report Action Code
  if (de != null)
    de.get();
  de = composite.getDataElement(3);  // composite element 704 Paperwork/Report Action Code
  if (de != null)
    de.get();
  de = composite.getDataElement(4);  // composite element 704 Paperwork/Report Action Code
  if (de != null)
    de.get();
  de = composite.getDataElement(5);  // composite element 704 Paperwork/Report Action Code
  if (de != null)
    de.get();
       de = segment.getDataElement(9);       // 1525 Request Category Code
       if (de != null)
         de.get();
    }
  }
/** extract data from segment PKG that is part of the TableDetailLoopHL
*<br>Marking, Packaging, Loading used 
*<br>To describe marking, packaging, loading, and unloading requirements
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPKGfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("PKG");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("PKG", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 349 Item Description Type
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 753 Packaging Characteristic Code
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 559 Agency Qualifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 754 Packaging Description Code
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 352 Description
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 400 Unit Load Option Code
       if (de != null)
         de.get();
    }
  }
/** extract data from segment TD1 that is part of the TableDetailLoopHL
*<br>Carrier Details (Quantity and Weight) used 
*<br>To specify the transportation details relative to commodity, weight, and quantity
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD1fromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("TD1");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("TD1", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 103 Packaging Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 80 Lading Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 23 Commodity Code Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 22 Commodity Code
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 79 Lading Description
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 187 Weight Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 81 Weight
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 355 Unit or Basis for Measurement Code
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 183 Volume
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 355 Unit or Basis for Measurement Code
       if (de != null)
         de.get();
    }
  }
/** extract data from segment TD5 that is part of the TableDetailLoopHL
*<br>Carrier Details (Routing Sequence/Transit Time) used 
*<br>To specify the carrier and sequence of routing and provide transit time information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD5fromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("TD5");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("TD5", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 133 Routing Sequence Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 66 Identification Code Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 67 Identification Code
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 91 Transportation Method/Type Code
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 387 Routing
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 368 Shipment/Order Status Code
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 309 Location Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 310 Location Identifier
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 731 Transit Direction Code
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 732 Transit Time Direction Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 733 Transit Time
       if (de != null)
         de.get();
       de = segment.getDataElement(12);       // 284 Service Level Code
       if (de != null)
         de.get();
       de = segment.getDataElement(13);       // 284 Service Level Code
       if (de != null)
         de.get();
       de = segment.getDataElement(14);       // 284 Service Level Code
       if (de != null)
         de.get();
       de = segment.getDataElement(15);       // 26 Country Code
       if (de != null)
         de.get();
    }
  }
/** extract data from segment TD3 that is part of the TableDetailLoopHL
*<br>Carrier Details (Equipment) used 
*<br>To specify transportation details relating to the equipment used by the carrier
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD3fromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("TD3");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("TD3", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 40 Equipment Description Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 206 Equipment Initial
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 207 Equipment Number
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 187 Weight Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 81 Weight
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 355 Unit or Basis for Measurement Code
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 102 Ownership Code
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 407 Seal Status Code
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 225 Seal Number
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 24 Equipment Type
       if (de != null)
         de.get();
    }
  }
/** extract data from segment TD4 that is part of the TableDetailLoopHL
*<br>Carrier Details (Special Handling, or Hazardous Materials, or Both) used 
*<br>To specify transportation special handling requirements, or hazardous materials information, or both
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD4fromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("TD4");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("TD4", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 152 Special Handling Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 208 Hazardous Material Code Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 209 Hazardous Material Class Code
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 352 Description
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 1073 Yes/No Condition or Response Code
       if (de != null)
         de.get();
    }
  }
/** extract data from segment TSD that is part of the TableDetailLoopHL
*<br>Trailer Shipment Details used 
*<br>To specify details of shipments on a trailer
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTSDfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("TSD");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 350 Assigned Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 219 Position
  if (de != null)
    de.get();
  }
/** extract data from segment REF that is part of the TableDetailLoopHL
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentREFfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("REF");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("REF", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 128 Reference Identification Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 127 Reference Identification
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 352 Description
       if (de != null)
         de.get();
         CompositeDE  composite = (CompositeDE) segment.getCompositeDE(4);  // C040 Reference Identifier
  if (composite == null)
    return;
  de = composite.getDataElement(1);  // composite element 128 Reference Identification Qualifier
  if (de != null)
    de.get();
  de = composite.getDataElement(2);  // composite element 127 Reference Identification
  if (de != null)
    de.get();
  de = composite.getDataElement(3);  // composite element 128 Reference Identification Qualifier
  if (de != null)
    de.get();
  de = composite.getDataElement(4);  // composite element 127 Reference Identification
  if (de != null)
    de.get();
  de = composite.getDataElement(5);  // composite element 128 Reference Identification Qualifier
  if (de != null)
    de.get();
  de = composite.getDataElement(6);  // composite element 127 Reference Identification
  if (de != null)
    de.get();
    }
  }
/** extract data from segment PER that is part of the TableDetailLoopHL
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPERfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("PER");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("PER", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 366 Contact Function Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 93 Name
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 365 Communication Number Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 364 Communication Number
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 365 Communication Number Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 364 Communication Number
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 365 Communication Number Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 364 Communication Number
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 443 Contact Inquiry Reference
       if (de != null)
         de.get();
    }
  }
/** extract data from loop LH1 that is part of TableDetailLoopHL
*<br>Hazardous Identification Information used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopLH1fromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("LH1");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("LH1", i);
   if (loop == null) return;
      extractSegmentLH1fromTableDetailLoopHLLoopLH1(loop);
      extractSegmentLH2fromTableDetailLoopHLLoopLH1(loop);
      extractSegmentLH3fromTableDetailLoopHLLoopLH1(loop);
      extractSegmentLFHfromTableDetailLoopHLLoopLH1(loop);
      extractSegmentLEPfromTableDetailLoopHLLoopLH1(loop);
      extractSegmentLH4fromTableDetailLoopHLLoopLH1(loop);
      extractSegmentLHTfromTableDetailLoopHLLoopLH1(loop);
      extractSegmentLHRfromTableDetailLoopHLLoopLH1(loop);
      extractSegmentPERfromTableDetailLoopHLLoopLH1(loop);
      extractSegmentLHEfromTableDetailLoopHLLoopLH1(loop);
    }
  }
/** extract data from segment LH1 that is part of the TableDetailLoopHLLoopLH1
*<br>Hazardous Identification Information used 
*<br>To specify the hazardous commodity identification reference number and quantity
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLH1fromTableDetailLoopHLLoopLH1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("LH1");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 80 Lading Quantity
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 277 UN/NA Identification Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 200 Hazardous Materials Page
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 22 Commodity Code
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 380 Quantity
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 595 Compartment ID Code
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 665 Residue Indicator Code
  if (de != null)
    de.get();
  de = segment.getDataElement(10);  // 254 Packing Group Code
  if (de != null)
    de.get();
  de = segment.getDataElement(11);  // 1375 Interim Hazardous Material Regulatory Number
  if (de != null)
    de.get();
  }
/** extract data from segment LH2 that is part of the TableDetailLoopHLLoopLH1
*<br>Hazardous Classification Information used 
*<br>To specify the hazardous notation and endorsement information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLH2fromTableDetailLoopHLLoopLH1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("LH2");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("LH2", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 215 Hazardous Classification
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 983 Hazardous Class Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 218 Hazardous Placard Notation
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 222 Hazardous Endorsement
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 759 Reportable Quantity Code
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 355 Unit or Basis for Measurement Code
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 408 Temperature
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 355 Unit or Basis for Measurement Code
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 408 Temperature
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 355 Unit or Basis for Measurement Code
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 408 Temperature
       if (de != null)
         de.get();
    }
  }
/** extract data from segment LH3 that is part of the TableDetailLoopHLLoopLH1
*<br>Hazardous Material Shipping Name used 
*<br>To specify the hazardous material shipping name and additional descriptive requirements
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLH3fromTableDetailLoopHLLoopLH1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("LH3");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("LH3", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 224 Hazardous Material Shipping Name
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 984 Hazardous Material Shipping Name Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 985 N.O.S. Indicator Code
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 1073 Yes/No Condition or Response Code
       if (de != null)
         de.get();
    }
  }
/** extract data from segment LFH that is part of the TableDetailLoopHLLoopLH1
*<br>Freeform Hazardous Material Information used 
*<br>To uniquely identify the variable information required by government regulation covering the transportation of hazardous material shipments
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLFHfromTableDetailLoopHLLoopLH1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("LFH");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("LFH", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 808 Hazardous Material Shipment Information Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 809 Hazardous Material Shipment Information
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 809 Hazardous Material Shipment Information
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 1023 Hazard Zone Code
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 355 Unit or Basis for Measurement Code
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 380 Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 380 Quantity
       if (de != null)
         de.get();
    }
  }
/** extract data from segment LEP that is part of the TableDetailLoopHLLoopLH1
*<br>EPA Required Data used 
*<br>To specify the Environmental Protection Agency (EPA) information relating to shipments of hazardous material
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLEPfromTableDetailLoopHLLoopLH1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("LEP");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("LEP", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 806 EPA Waste Stream Number Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 807 Waste Characteristics Code
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 156 State or Province Code
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 127 Reference Identification
       if (de != null)
         de.get();
    }
  }
/** extract data from segment LH4 that is part of the TableDetailLoopHLLoopLH1
*<br>Canadian Dangerous Requirements used 
*<br>To specify additional Transport Canada requirements covering transportation of dangerous goods in Canada
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLH4fromTableDetailLoopHLLoopLH1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("LH4");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 238 Emergency Response Plan Number
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 364 Communication Number
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 254 Packing Group Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 230 Subsidiary Classification
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 230 Subsidiary Classification
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 230 Subsidiary Classification
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 271 Subsidiary Risk Indicator
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 267 Net Explosive Quantity
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 805 Canadian Hazardous Notation
  if (de != null)
    de.get();
  de = segment.getDataElement(10);  // 986 Special Commodity Indicator Code
  if (de != null)
    de.get();
  de = segment.getDataElement(11);  // 364 Communication Number
  if (de != null)
    de.get();
  de = segment.getDataElement(12);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  }
/** extract data from segment LHT that is part of the TableDetailLoopHLLoopLH1
*<br>Transborder Hazardous Requirements used 
*<br>To specify the placard information required by the second government agency when shipment is to cross into another country
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLHTfromTableDetailLoopHLLoopLH1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("LHT");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("LHT", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 215 Hazardous Classification
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 218 Hazardous Placard Notation
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 222 Hazardous Endorsement
       if (de != null)
         de.get();
    }
  }
/** extract data from segment LHR that is part of the TableDetailLoopHLLoopLH1
*<br>Hazardous Material Identifying Reference Numbers used 
*<br>To transmit specific hazardous material reference numbers
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLHRfromTableDetailLoopHLLoopLH1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("LHR");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("LHR", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 128 Reference Identification Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 127 Reference Identification
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 373 Date
       if (de != null)
         de.get();
    }
  }
/** extract data from segment PER that is part of the TableDetailLoopHLLoopLH1
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPERfromTableDetailLoopHLLoopLH1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("PER");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("PER", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 366 Contact Function Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 93 Name
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 365 Communication Number Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 364 Communication Number
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 365 Communication Number Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 364 Communication Number
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 365 Communication Number Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 364 Communication Number
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 443 Contact Inquiry Reference
       if (de != null)
         de.get();
    }
  }
/** extract data from segment LHE that is part of the TableDetailLoopHLLoopLH1
*<br>Empty Equipment Hazardous Material Information used 
*<br>To specify the "last contained" hazardous shipping name, placard notation, and reference numbers for empty equipment
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLHEfromTableDetailLoopHLLoopLH1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("LHE");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 224 Hazardous Material Shipping Name
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 218 Hazardous Placard Notation
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 128 Reference Identification Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 127 Reference Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 759 Reportable Quantity Code
  if (de != null)
    de.get();
  }

/** extract data from loop CLD that is part of TableDetailLoopHL
*<br>Load Detail used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopCLDfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("CLD");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("CLD", i);
   if (loop == null) return;
      extractSegmentCLDfromTableDetailLoopHLLoopCLD(loop);
      extractSegmentREFfromTableDetailLoopHLLoopCLD(loop);
      extractSegmentDTPfromTableDetailLoopHLLoopCLD(loop);
    }
  }
/** extract data from segment CLD that is part of the TableDetailLoopHLLoopCLD
*<br>Load Detail used 
*<br>To specify the number of material loads shipped
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCLDfromTableDetailLoopHLLoopCLD(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("CLD");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 622 Number of Loads
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 382 Number of Units Shipped
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 103 Packaging Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 357 Size
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  }
/** extract data from segment REF that is part of the TableDetailLoopHLLoopCLD
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentREFfromTableDetailLoopHLLoopCLD(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("REF");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("REF", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 128 Reference Identification Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 127 Reference Identification
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 352 Description
       if (de != null)
         de.get();
         CompositeDE  composite = (CompositeDE) segment.getCompositeDE(4);  // C040 Reference Identifier
  if (composite == null)
    return;
  de = composite.getDataElement(1);  // composite element 128 Reference Identification Qualifier
  if (de != null)
    de.get();
  de = composite.getDataElement(2);  // composite element 127 Reference Identification
  if (de != null)
    de.get();
  de = composite.getDataElement(3);  // composite element 128 Reference Identification Qualifier
  if (de != null)
    de.get();
  de = composite.getDataElement(4);  // composite element 127 Reference Identification
  if (de != null)
    de.get();
  de = composite.getDataElement(5);  // composite element 128 Reference Identification Qualifier
  if (de != null)
    de.get();
  de = composite.getDataElement(6);  // composite element 127 Reference Identification
  if (de != null)
    de.get();
    }
  }
/** extract data from segment DTP that is part of the TableDetailLoopHLLoopCLD
*<br>Date or Time or Period used 
*<br>To specify any or all of a date, a time, or a time period
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDTPfromTableDetailLoopHLLoopCLD(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("DTP");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 374 Date/Time Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 1250 Date Time Period Format Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 1251 Date Time Period
  if (de != null)
    de.get();
  }

/** extract data from segment MAN that is part of the TableDetailLoopHL
*<br>Marks and Numbers used 
*<br>To indicate identifying marks and numbers for shipping containers
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMANfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("MAN");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("MAN", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 88 Marks and Numbers Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 87 Marks and Numbers
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 87 Marks and Numbers
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 88 Marks and Numbers Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 87 Marks and Numbers
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 87 Marks and Numbers
       if (de != null)
         de.get();
    }
  }
/** extract data from segment DTM that is part of the TableDetailLoopHL
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDTMfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("DTM");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("DTM", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 374 Date/Time Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 373 Date
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 337 Time
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 623 Time Code
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 1250 Date Time Period Format Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 1251 Date Time Period
       if (de != null)
         de.get();
    }
  }
/** extract data from segment FOB that is part of the TableDetailLoopHL
*<br>F.O.B. Related Instructions used 
*<br>To specify transportation instructions relating to shipment
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentFOBfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("FOB");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 146 Shipment Method of Payment
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 309 Location Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 352 Description
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 334 Transportation Terms Qualifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 335 Transportation Terms Code
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 309 Location Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 352 Description
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 54 Risk of Loss Code
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 352 Description
  if (de != null)
    de.get();
  }
/** extract data from segment PAL that is part of the TableDetailLoopHL
*<br>Pallet Information used 
*<br>To identify the type and physical attributes of the pallet, and, gross weight, gross volume, and height of the load and the pallet
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPALfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("PAL");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 883 Pallet Type Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 884 Pallet Tiers
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 885 Pallet Blocks
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 356 Pack
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 395 Unit Weight
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 82 Length
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 189 Width
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 65 Height
  if (de != null)
    de.get();
  de = segment.getDataElement(10);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(11);  // 384 Gross Weight per Pack
  if (de != null)
    de.get();
  de = segment.getDataElement(12);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(13);  // 385 Gross Volume per Pack
  if (de != null)
    de.get();
  de = segment.getDataElement(14);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(15);  // 399 Pallet Exchange Code
  if (de != null)
    de.get();
  de = segment.getDataElement(16);  // 810 Inner Pack
  if (de != null)
    de.get();
  }
/** extract data from loop N1 that is part of TableDetailLoopHL
*<br>Name used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopN1fromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("N1");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("N1", i);
   if (loop == null) return;
      extractSegmentN1fromTableDetailLoopHLLoopN1(loop);
      extractSegmentN2fromTableDetailLoopHLLoopN1(loop);
      extractSegmentN3fromTableDetailLoopHLLoopN1(loop);
      extractSegmentN4fromTableDetailLoopHLLoopN1(loop);
      extractSegmentREF_2fromTableDetailLoopHLLoopN1(loop);
      extractSegmentPER_2fromTableDetailLoopHLLoopN1(loop);
      extractSegmentFOBfromTableDetailLoopHLLoopN1(loop);
    }
  }
/** extract data from segment N1 that is part of the TableDetailLoopHLLoopN1
*<br>Name used 
*<br>To identify a party by type of organization, name, and code
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentN1fromTableDetailLoopHLLoopN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("N1");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 98 Entity Identifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 93 Name
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 66 Identification Code Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 67 Identification Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 706 Entity Relationship Code
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 98 Entity Identifier Code
  if (de != null)
    de.get();
  }
/** extract data from segment N2 that is part of the TableDetailLoopHLLoopN1
*<br>Additional Name Information used 
*<br>To specify additional names or those longer than 35 characters in length
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentN2fromTableDetailLoopHLLoopN1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("N2");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("N2", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 93 Name
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 93 Name
       if (de != null)
         de.get();
    }
  }
/** extract data from segment N3 that is part of the TableDetailLoopHLLoopN1
*<br>Address Information used 
*<br>To specify the location of the named party
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentN3fromTableDetailLoopHLLoopN1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("N3");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("N3", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 166 Address Information
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 166 Address Information
       if (de != null)
         de.get();
    }
  }
/** extract data from segment N4 that is part of the TableDetailLoopHLLoopN1
*<br>Geographic Location used 
*<br>To specify the geographic place of the named party
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentN4fromTableDetailLoopHLLoopN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("N4");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 19 City Name
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 156 State or Province Code
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 116 Postal Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 26 Country Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 309 Location Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 310 Location Identifier
  if (de != null)
    de.get();
  }
/** extract data from segment REF that is part of the TableDetailLoopHLLoopN1
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentREF_2fromTableDetailLoopHLLoopN1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("REF");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("REF", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 128 Reference Identification Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 127 Reference Identification
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 352 Description
       if (de != null)
         de.get();
         CompositeDE  composite = (CompositeDE) segment.getCompositeDE(4);  // C040 Reference Identifier
  if (composite == null)
    return;
  de = composite.getDataElement(1);  // composite element 128 Reference Identification Qualifier
  if (de != null)
    de.get();
  de = composite.getDataElement(2);  // composite element 127 Reference Identification
  if (de != null)
    de.get();
  de = composite.getDataElement(3);  // composite element 128 Reference Identification Qualifier
  if (de != null)
    de.get();
  de = composite.getDataElement(4);  // composite element 127 Reference Identification
  if (de != null)
    de.get();
  de = composite.getDataElement(5);  // composite element 128 Reference Identification Qualifier
  if (de != null)
    de.get();
  de = composite.getDataElement(6);  // composite element 127 Reference Identification
  if (de != null)
    de.get();
    }
  }
/** extract data from segment PER that is part of the TableDetailLoopHLLoopN1
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPER_2fromTableDetailLoopHLLoopN1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("PER");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("PER", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 366 Contact Function Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 93 Name
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 365 Communication Number Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 364 Communication Number
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 365 Communication Number Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 364 Communication Number
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 365 Communication Number Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 364 Communication Number
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 443 Contact Inquiry Reference
       if (de != null)
         de.get();
    }
  }
/** extract data from segment FOB that is part of the TableDetailLoopHLLoopN1
*<br>F.O.B. Related Instructions used 
*<br>To specify transportation instructions relating to shipment
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentFOBfromTableDetailLoopHLLoopN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("FOB");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 146 Shipment Method of Payment
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 309 Location Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 352 Description
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 334 Transportation Terms Qualifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 335 Transportation Terms Code
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 309 Location Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 352 Description
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 54 Risk of Loss Code
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 352 Description
  if (de != null)
    de.get();
  }

/** extract data from segment SDQ that is part of the TableDetailLoopHL
*<br>Destination Quantity used 
*<br>To specify destination and quantity detail
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSDQfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("SDQ");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("SDQ", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 355 Unit or Basis for Measurement Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 66 Identification Code Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 67 Identification Code
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 380 Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 67 Identification Code
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 380 Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 67 Identification Code
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 380 Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 67 Identification Code
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 380 Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 67 Identification Code
       if (de != null)
         de.get();
       de = segment.getDataElement(12);       // 380 Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(13);       // 67 Identification Code
       if (de != null)
         de.get();
       de = segment.getDataElement(14);       // 380 Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(15);       // 67 Identification Code
       if (de != null)
         de.get();
       de = segment.getDataElement(16);       // 380 Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(17);       // 67 Identification Code
       if (de != null)
         de.get();
       de = segment.getDataElement(18);       // 380 Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(19);       // 67 Identification Code
       if (de != null)
         de.get();
       de = segment.getDataElement(20);       // 380 Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(21);       // 67 Identification Code
       if (de != null)
         de.get();
       de = segment.getDataElement(22);       // 380 Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(23);       // 310 Location Identifier
       if (de != null)
         de.get();
    }
  }
/** extract data from segment ETD that is part of the TableDetailLoopHL
*<br>Excess Transportation Detail used 
*<br>To specify information relating to premium transportation
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentETDfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("ETD");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 626 Excess Transportation Reason Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 627 Excess Transportation Responsibility Code
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 128 Reference Identification Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 127 Reference Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 743 Returnable Container Freight Payment Responsibility Code
  if (de != null)
    de.get();
  }
/** extract data from segment CUR that is part of the TableDetailLoopHL
*<br>Currency used 
*<br>To specify the currency (dollars, pounds, francs, etc.) used in a transaction
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCURfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("CUR");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 98 Entity Identifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 100 Currency Code
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 280 Exchange Rate
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 98 Entity Identifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 100 Currency Code
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 669 Currency Market/Exchange Code
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 374 Date/Time Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 337 Time
  if (de != null)
    de.get();
  de = segment.getDataElement(10);  // 374 Date/Time Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(11);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(12);  // 337 Time
  if (de != null)
    de.get();
  de = segment.getDataElement(13);  // 374 Date/Time Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(14);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(15);  // 337 Time
  if (de != null)
    de.get();
  de = segment.getDataElement(16);  // 374 Date/Time Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(17);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(18);  // 337 Time
  if (de != null)
    de.get();
  de = segment.getDataElement(19);  // 374 Date/Time Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(20);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(21);  // 337 Time
  if (de != null)
    de.get();
  }
/** extract data from loop SAC that is part of TableDetailLoopHL
*<br>Service, Promotion, Allowance, or Charge Information used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopSACfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("SAC");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("SAC", i);
   if (loop == null) return;
      extractSegmentSACfromTableDetailLoopHLLoopSAC(loop);
      extractSegmentCURfromTableDetailLoopHLLoopSAC(loop);
    }
  }
/** extract data from segment SAC that is part of the TableDetailLoopHLLoopSAC
*<br>Service, Promotion, Allowance, or Charge Information used 
*<br>To request or identify a service, promotion, allowance, or charge; to specify the amount or percentage for the service, promotion, allowance, or charge
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSACfromTableDetailLoopHLLoopSAC(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("SAC");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 248 Allowance or Charge Indicator
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 1300 Service, Promotion, Allowance, or Charge Code
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 559 Agency Qualifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 1301 Agency Service, Promotion, Allowance, or Charge Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 610 Amount
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 378 Allowance/Charge Percent Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 332 Percent
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 118 Rate
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(10);  // 380 Quantity
  if (de != null)
    de.get();
  de = segment.getDataElement(11);  // 380 Quantity
  if (de != null)
    de.get();
  de = segment.getDataElement(12);  // 331 Allowance or Charge Method of Handling Code
  if (de != null)
    de.get();
  de = segment.getDataElement(13);  // 127 Reference Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(14);  // 770 Option Number
  if (de != null)
    de.get();
  de = segment.getDataElement(15);  // 352 Description
  if (de != null)
    de.get();
  de = segment.getDataElement(16);  // 819 Language Code
  if (de != null)
    de.get();
  }
/** extract data from segment CUR that is part of the TableDetailLoopHLLoopSAC
*<br>Currency used 
*<br>To specify the currency (dollars, pounds, francs, etc.) used in a transaction
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCURfromTableDetailLoopHLLoopSAC(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("CUR");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 98 Entity Identifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 100 Currency Code
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 280 Exchange Rate
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 98 Entity Identifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 100 Currency Code
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 669 Currency Market/Exchange Code
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 374 Date/Time Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 337 Time
  if (de != null)
    de.get();
  de = segment.getDataElement(10);  // 374 Date/Time Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(11);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(12);  // 337 Time
  if (de != null)
    de.get();
  de = segment.getDataElement(13);  // 374 Date/Time Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(14);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(15);  // 337 Time
  if (de != null)
    de.get();
  de = segment.getDataElement(16);  // 374 Date/Time Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(17);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(18);  // 337 Time
  if (de != null)
    de.get();
  de = segment.getDataElement(19);  // 374 Date/Time Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(20);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(21);  // 337 Time
  if (de != null)
    de.get();
  }

/** extract data from segment GF that is part of the TableDetailLoopHL
*<br>Furnished Goods and Services used 
*<br>To specify information related to furnished material, equipment, property, information, and services
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentGFfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("GF");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 128 Reference Identification Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 127 Reference Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 367 Contract Number
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 782 Monetary Amount
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 128 Reference Identification Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 127 Reference Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 328 Release Number
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 128 Reference Identification Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 127 Reference Identification
  if (de != null)
    de.get();
  }
/** extract data from segment YNQ that is part of the TableDetailLoopHL
*<br>Yes/No Question used 
*<br>To identify and answer yes and no questions, including the date, time, and comments further qualifying the condition
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentYNQfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("YNQ");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("YNQ", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 1321 Condition Indicator
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 1073 Yes/No Condition or Response Code
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 1250 Date Time Period Format Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 1251 Date Time Period
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 933 Free-Form Message Text
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 933 Free-Form Message Text
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 933 Free-Form Message Text
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 1270 Code List Qualifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 1271 Industry Code
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 933 Free-Form Message Text
       if (de != null)
         de.get();
    }
  }
/** extract data from loop LM that is part of TableDetailLoopHL
*<br>Code Source Information used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopLMfromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("LM");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("LM", i);
   if (loop == null) return;
      extractSegmentLMfromTableDetailLoopHLLoopLM(loop);
      extractSegmentLQfromTableDetailLoopHLLoopLM(loop);
    }
  }
/** extract data from segment LM that is part of the TableDetailLoopHLLoopLM
*<br>Code Source Information used 
*<br>To transmit standard code list identification information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLMfromTableDetailLoopHLLoopLM(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("LM");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 559 Agency Qualifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 822 Source Subqualifier
  if (de != null)
    de.get();
  }
/** extract data from segment LQ that is part of the TableDetailLoopHLLoopLM
*<br>Industry Code used 
*<br>Code to transmit standard industry codes
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLQfromTableDetailLoopHLLoopLM(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("LQ");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("LQ", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 1270 Code List Qualifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 1271 Industry Code
       if (de != null)
         de.get();
    }
  }

/** extract data from loop V1 that is part of TableDetailLoopHL
*<br>Vessel Identification used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopV1fromTableDetailLoopHL(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("V1");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("V1", i);
   if (loop == null) return;
      extractSegmentV1fromTableDetailLoopHLLoopV1(loop);
      extractSegmentR4fromTableDetailLoopHLLoopV1(loop);
      extractSegmentDTMfromTableDetailLoopHLLoopV1(loop);
    }
  }
/** extract data from segment V1 that is part of the TableDetailLoopHLLoopV1
*<br>Vessel Identification used 
*<br>To provide vessel details and voyage number
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentV1fromTableDetailLoopHLLoopV1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("V1");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 597 Vessel Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 182 Vessel Name
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 26 Country Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 55 Flight/Voyage Number
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 140 Standard Carrier Alpha Code
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 249 Vessel Requirement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 854 Vessel Type Code
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 897 Vessel Code Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 91 Transportation Method/Type Code
  if (de != null)
    de.get();
  }
/** extract data from segment R4 that is part of the TableDetailLoopHLLoopV1
*<br>Port or Terminal used 
*<br>Contractual or operational port or point relevant to the movement of the cargo
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentR4fromTableDetailLoopHLLoopV1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("R4");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("R4", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 115 Port or Terminal Function Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 309 Location Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 310 Location Identifier
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 114 Port Name
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 26 Country Code
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 174 Terminal Name
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 113 Pier Number
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 156 State or Province Code
       if (de != null)
         de.get();
    }
  }
/** extract data from segment DTM that is part of the TableDetailLoopHLLoopV1
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDTMfromTableDetailLoopHLLoopV1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("DTM");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("DTM", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 374 Date/Time Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 373 Date
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 337 Time
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 623 Time Code
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 1250 Date Time Period Format Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 1251 Date Time Period
       if (de != null)
         de.get();
    }
  }


/** extract data from segment CTT that is part of the TableSummary
*<br>Transaction Totals used 
*<br>To transmit a hash total for a specific element in the transaction set
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCTTfromTableSummary(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.getSegment("CTT");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 354 Number of Line Items
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 347 Hash Total
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 81 Weight
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 183 Volume
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 352 Description
  if (de != null)
    de.get();
  }
/** extract data from segment SE that is part of the TableSummary
*<br>Transaction Set Trailer used 
*<br>To indicate the end of the transaction set and provide the count of the transmitted segments (including the beginning (ST) and ending (SE) segments)
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSEfromTableSummary(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.getSegment("SE");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 96 Number of Included Segments
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 329 Transaction Set Control Number
  if (de != null)
    de.get();
  }

}
