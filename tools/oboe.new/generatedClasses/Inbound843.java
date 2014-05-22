import com.americancoders.edi.*;
import com.americancoders.edi.x12.*;
import java.io.*;

/** code template to parse
*<br>class 843 Response to Request for Quotation
*<br>
* This Draft Standard for Trial Use contains the format and establishes
* the data contents of the Response to Request for Quotation Transaction
* Set (843) for use within the context of an Electronic Data Interchange
* (EDI) environment. The transaction set can be used to provide
* potential buyers with price, delivery schedule, and other terms
* from potential sellers of goods and services, in response to
* a request for such information.
*@author OBOECodeGenerator
*/
public class Inbound843
{
/** constructor for class Inbound843
*@param inFileName - String filename to be parsed
*@throws OBOEException - most likely transactionset not found
*@throws IOException - most likely input file not found
*/
public Inbound843(String inFileName)
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
                 extractSegmentBQRfromTableHeader(table);
                 extractSegmentCURfromTableHeader(table);
                 extractSegmentREFfromTableHeader(table);
                 extractSegmentPERfromTableHeader(table);
                 extractSegmentTAXfromTableHeader(table);
                 extractSegmentFOBfromTableHeader(table);
                 extractSegmentCTPfromTableHeader(table);
                 extractSegmentPAMfromTableHeader(table);
                 extractSegmentCSHfromTableHeader(table);
                 extractSegmentSACfromTableHeader(table);
                 extractSegmentITDfromTableHeader(table);
                 extractSegmentDISfromTableHeader(table);
                 extractSegmentDTMfromTableHeader(table);
                 extractSegmentLINfromTableHeader(table);
                 extractSegmentPIDfromTableHeader(table);
                 extractSegmentMEAfromTableHeader(table);
                 extractSegmentPWKfromTableHeader(table);
                 extractSegmentPKGfromTableHeader(table);
                 extractSegmentTD1fromTableHeader(table);
                 extractSegmentTD5fromTableHeader(table);
                 extractSegmentTD3fromTableHeader(table);
                 extractSegmentTD4fromTableHeader(table);
                 extractSegmentMANfromTableHeader(table);
                 extractSegmentCTBfromTableHeader(table);
                 extractSegmentCPRfromTableHeader(table);
                 extractSegmentPCTfromTableHeader(table);
                 extractLoopN9fromTableHeader(table);
                 extractLoopN1fromTableHeader(table);
                 extractLoopAMTfromTableHeader(table);
                 extractLoopADVfromTableHeader(table);
                 extractLoopLDTfromTableHeader(table);

              }
             table = ts.getDetailTable();
             if (table != null)
               {
                 extractLoopPO1fromTableDetail(table);

              }
            table = ts.getSummaryTable();
             if (table != null)
               {
                 extractLoopCTTfromTableSummary(table);
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
/** extract data from segment BQR that is part of the TableHeader
*<br>Beginning Segment for Response to Request for Quotation used 
*<br>To indicate the beginning of a Response to Request for Quote Transaction Set and transmit identifying numbers and dates
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentBQRfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.getSegment("BQR");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 353 Transaction Set Purpose Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 586 Request for Quote Reference Number
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 374 Date/Time Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 379 Bid Type Response Code
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 786 Security Level Code
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 327 Change Order Sequence Number
  if (de != null)
    de.get();
  }
/** extract data from segment CUR that is part of the TableHeader
*<br>Currency used 
*<br>To specify the currency (dollars, pounds, francs, etc.) used in a transaction
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCURfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.getSegment("CUR");
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
/** extract data from segment REF that is part of the TableHeader
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentREFfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("REF");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("REF", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 128 Reference Identification Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 127 Reference Identification
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 352 Description
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
/** extract data from segment PER that is part of the TableHeader
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPERfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("PER");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("PER", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 366 Contact Function Code
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 93 Name
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 365 Communication Number Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 364 Communication Number
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 365 Communication Number Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 364 Communication Number
     if (de != null)
       de.get();
     de = segment.getDataElement(7);     // 365 Communication Number Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(8);     // 364 Communication Number
     if (de != null)
       de.get();
     de = segment.getDataElement(9);     // 443 Contact Inquiry Reference
     if (de != null)
       de.get();
    }
  }
/** extract data from segment TAX that is part of the TableHeader
*<br>Tax Reference used 
*<br>To provide data required for proper notification/determination of applicable taxes applying to the transaction or business described in the transaction
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTAXfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("TAX");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("TAX", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 325 Tax Identification Number
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 309 Location Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 310 Location Identifier
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 309 Location Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 310 Location Identifier
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 309 Location Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(7);     // 310 Location Identifier
     if (de != null)
       de.get();
     de = segment.getDataElement(8);     // 309 Location Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(9);     // 310 Location Identifier
     if (de != null)
       de.get();
     de = segment.getDataElement(10);     // 309 Location Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(11);     // 310 Location Identifier
     if (de != null)
       de.get();
     de = segment.getDataElement(12);     // 441 Tax Exempt Code
     if (de != null)
       de.get();
     de = segment.getDataElement(13);     // 1179 Customs Entry Type Group Code
     if (de != null)
       de.get();
    }
  }
/** extract data from segment FOB that is part of the TableHeader
*<br>F.O.B. Related Instructions used 
*<br>To specify transportation instructions relating to shipment
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentFOBfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("FOB");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("FOB", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 146 Shipment Method of Payment
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 309 Location Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 352 Description
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 334 Transportation Terms Qualifier Code
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 335 Transportation Terms Code
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 309 Location Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(7);     // 352 Description
     if (de != null)
       de.get();
     de = segment.getDataElement(8);     // 54 Risk of Loss Code
     if (de != null)
       de.get();
     de = segment.getDataElement(9);     // 352 Description
     if (de != null)
       de.get();
    }
  }
/** extract data from segment CTP that is part of the TableHeader
*<br>Pricing Information used 
*<br>To specify pricing information
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCTPfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("CTP");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("CTP", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 687 Class of Trade Code
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 236 Price Identifier Code
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 212 Unit Price
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 380 Quantity
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
     de = segment.getDataElement(6);     // 648 Price Multiplier Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(7);     // 649 Multiplier
     if (de != null)
       de.get();
     de = segment.getDataElement(8);     // 782 Monetary Amount
     if (de != null)
       de.get();
     de = segment.getDataElement(9);     // 639 Basis of Unit Price Code
     if (de != null)
       de.get();
     de = segment.getDataElement(10);     // 499 Condition Value
     if (de != null)
       de.get();
     de = segment.getDataElement(11);     // 289 Multiple Price Quantity
     if (de != null)
       de.get();
    }
  }
/** extract data from segment PAM that is part of the TableHeader
*<br>Period Amount used 
*<br>To indicate a quantity, and/or amount for an identified period
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPAMfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("PAM");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("PAM", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 673 Quantity Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 380 Quantity
     if (de != null)
       de.get();
       CompositeDE  composite = (CompositeDE) segment.getCompositeDE(3);  // C001 Composite Unit of Measure
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
     de = segment.getDataElement(4);     // 522 Amount Qualifier Code
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 782 Monetary Amount
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 344 Unit of Time Period or Interval
     if (de != null)
       de.get();
     de = segment.getDataElement(7);     // 374 Date/Time Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(8);     // 373 Date
     if (de != null)
       de.get();
     de = segment.getDataElement(9);     // 337 Time
     if (de != null)
       de.get();
     de = segment.getDataElement(10);     // 374 Date/Time Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(11);     // 373 Date
     if (de != null)
       de.get();
     de = segment.getDataElement(12);     // 337 Time
     if (de != null)
       de.get();
     de = segment.getDataElement(13);     // 1004 Percent Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(14);     // 954 Percent
     if (de != null)
       de.get();
     de = segment.getDataElement(15);     // 1073 Yes/No Condition or Response Code
     if (de != null)
       de.get();
    }
  }
/** extract data from segment CSH that is part of the TableHeader
*<br>Sales Requirements used 
*<br>To specify general conditions or requirements of the sale
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCSHfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("CSH");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("CSH", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 563 Sales Requirement Code
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 306 Action Code
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 610 Amount
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 508 Account Number
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 373 Date
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 559 Agency Qualifier Code
     if (de != null)
       de.get();
     de = segment.getDataElement(7);     // 560 Special Services Code
     if (de != null)
       de.get();
     de = segment.getDataElement(8);     // 566 Product/Service Substitution Code
     if (de != null)
       de.get();
     de = segment.getDataElement(9);     // 954 Percent
     if (de != null)
       de.get();
     de = segment.getDataElement(10);     // 1004 Percent Qualifier
     if (de != null)
       de.get();
    }
  }
/** extract data from segment SAC that is part of the TableHeader
*<br>Service, Promotion, Allowance, or Charge Information used 
*<br>To request or identify a service, promotion, allowance, or charge; to specify the amount or percentage for the service, promotion, allowance, or charge
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSACfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("SAC");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("SAC", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 248 Allowance or Charge Indicator
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 1300 Service, Promotion, Allowance, or Charge Code
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 559 Agency Qualifier Code
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 1301 Agency Service, Promotion, Allowance, or Charge Code
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 610 Amount
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 378 Allowance/Charge Percent Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(7);     // 332 Percent
     if (de != null)
       de.get();
     de = segment.getDataElement(8);     // 118 Rate
     if (de != null)
       de.get();
     de = segment.getDataElement(9);     // 355 Unit or Basis for Measurement Code
     if (de != null)
       de.get();
     de = segment.getDataElement(10);     // 380 Quantity
     if (de != null)
       de.get();
     de = segment.getDataElement(11);     // 380 Quantity
     if (de != null)
       de.get();
     de = segment.getDataElement(12);     // 331 Allowance or Charge Method of Handling Code
     if (de != null)
       de.get();
     de = segment.getDataElement(13);     // 127 Reference Identification
     if (de != null)
       de.get();
     de = segment.getDataElement(14);     // 770 Option Number
     if (de != null)
       de.get();
     de = segment.getDataElement(15);     // 352 Description
     if (de != null)
       de.get();
     de = segment.getDataElement(16);     // 819 Language Code
     if (de != null)
       de.get();
    }
  }
/** extract data from segment ITD that is part of the TableHeader
*<br>Terms of Sale/Deferred Terms of Sale used 
*<br>To specify terms of sale
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentITDfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("ITD");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("ITD", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 336 Terms Type Code
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 333 Terms Basis Date Code
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 338 Terms Discount Percent
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 370 Terms Discount Due Date
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 351 Terms Discount Days Due
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 446 Terms Net Due Date
     if (de != null)
       de.get();
     de = segment.getDataElement(7);     // 386 Terms Net Days
     if (de != null)
       de.get();
     de = segment.getDataElement(8);     // 362 Terms Discount Amount
     if (de != null)
       de.get();
     de = segment.getDataElement(9);     // 388 Terms Deferred Due Date
     if (de != null)
       de.get();
     de = segment.getDataElement(10);     // 389 Deferred Amount Due
     if (de != null)
       de.get();
     de = segment.getDataElement(11);     // 342 Percent of Invoice Payable
     if (de != null)
       de.get();
     de = segment.getDataElement(12);     // 352 Description
     if (de != null)
       de.get();
     de = segment.getDataElement(13);     // 765 Day of Month
     if (de != null)
       de.get();
     de = segment.getDataElement(14);     // 107 Payment Method Code
     if (de != null)
       de.get();
     de = segment.getDataElement(15);     // 954 Percent
     if (de != null)
       de.get();
    }
  }
/** extract data from segment DIS that is part of the TableHeader
*<br>Discount Detail used 
*<br>To specify the exact type and terms of various discount information
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDISfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("DIS");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("DIS", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 653 Discount Terms Type Code
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 654 Discount Base Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 655 Discount Base Value
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 656 Discount Control Limit Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 657 Discount Control Limit
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 657 Discount Control Limit
     if (de != null)
       de.get();
    }
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
/** extract data from segment LIN that is part of the TableHeader
*<br>Item Identification used 
*<br>To specify basic item identification data
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLINfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("LIN");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("LIN", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 350 Assigned Identification
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 235 Product/Service ID Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 234 Product/Service ID
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 235 Product/Service ID Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 234 Product/Service ID
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 235 Product/Service ID Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(7);     // 234 Product/Service ID
     if (de != null)
       de.get();
     de = segment.getDataElement(8);     // 235 Product/Service ID Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(9);     // 234 Product/Service ID
     if (de != null)
       de.get();
     de = segment.getDataElement(10);     // 235 Product/Service ID Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(11);     // 234 Product/Service ID
     if (de != null)
       de.get();
     de = segment.getDataElement(12);     // 235 Product/Service ID Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(13);     // 234 Product/Service ID
     if (de != null)
       de.get();
     de = segment.getDataElement(14);     // 235 Product/Service ID Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(15);     // 234 Product/Service ID
     if (de != null)
       de.get();
     de = segment.getDataElement(16);     // 235 Product/Service ID Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(17);     // 234 Product/Service ID
     if (de != null)
       de.get();
     de = segment.getDataElement(18);     // 235 Product/Service ID Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(19);     // 234 Product/Service ID
     if (de != null)
       de.get();
     de = segment.getDataElement(20);     // 235 Product/Service ID Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(21);     // 234 Product/Service ID
     if (de != null)
       de.get();
     de = segment.getDataElement(22);     // 235 Product/Service ID Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(23);     // 234 Product/Service ID
     if (de != null)
       de.get();
     de = segment.getDataElement(24);     // 235 Product/Service ID Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(25);     // 234 Product/Service ID
     if (de != null)
       de.get();
     de = segment.getDataElement(26);     // 235 Product/Service ID Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(27);     // 234 Product/Service ID
     if (de != null)
       de.get();
     de = segment.getDataElement(28);     // 235 Product/Service ID Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(29);     // 234 Product/Service ID
     if (de != null)
       de.get();
     de = segment.getDataElement(30);     // 235 Product/Service ID Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(31);     // 234 Product/Service ID
     if (de != null)
       de.get();
    }
  }
/** extract data from segment PID that is part of the TableHeader
*<br>Product/Item Description used 
*<br>To describe a product or process in coded or free-form format
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPIDfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("PID");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("PID", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 349 Item Description Type
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 750 Product/Process Characteristic Code
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 559 Agency Qualifier Code
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 751 Product Description Code
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 352 Description
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 752 Surface/Layer/Position Code
     if (de != null)
       de.get();
     de = segment.getDataElement(7);     // 822 Source Subqualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(8);     // 1073 Yes/No Condition or Response Code
     if (de != null)
       de.get();
     de = segment.getDataElement(9);     // 819 Language Code
     if (de != null)
       de.get();
    }
  }
/** extract data from segment MEA that is part of the TableHeader
*<br>Measurements used 
*<br>To specify physical measurements or counts, including dimensions, tolerances, variances, and weights  (See Figures Appendix for example of use of C001)
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMEAfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("MEA");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("MEA", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 737 Measurement Reference ID Code
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 738 Measurement Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 739 Measurement Value
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
     de = segment.getDataElement(5);     // 740 Range Minimum
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 741 Range Maximum
     if (de != null)
       de.get();
     de = segment.getDataElement(7);     // 935 Measurement Significance Code
     if (de != null)
       de.get();
     de = segment.getDataElement(8);     // 936 Measurement Attribute Code
     if (de != null)
       de.get();
     de = segment.getDataElement(9);     // 752 Surface/Layer/Position Code
     if (de != null)
       de.get();
     de = segment.getDataElement(10);     // 1373 Measurement Method or Device
     if (de != null)
       de.get();
    }
  }
/** extract data from segment PWK that is part of the TableHeader
*<br>Paperwork used 
*<br>To identify the type or transmission or both of paperwork or supporting information
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPWKfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("PWK");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("PWK", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 755 Report Type Code
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 756 Report Transmission Code
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 757 Report Copies Needed
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 98 Entity Identifier Code
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 66 Identification Code Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 67 Identification Code
     if (de != null)
       de.get();
     de = segment.getDataElement(7);     // 352 Description
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
     de = segment.getDataElement(9);     // 1525 Request Category Code
     if (de != null)
       de.get();
    }
  }
/** extract data from segment PKG that is part of the TableHeader
*<br>Marking, Packaging, Loading used 
*<br>To describe marking, packaging, loading, and unloading requirements
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPKGfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("PKG");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("PKG", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 349 Item Description Type
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 753 Packaging Characteristic Code
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 559 Agency Qualifier Code
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 754 Packaging Description Code
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 352 Description
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 400 Unit Load Option Code
     if (de != null)
       de.get();
    }
  }
/** extract data from segment TD1 that is part of the TableHeader
*<br>Carrier Details (Quantity and Weight) used 
*<br>To specify the transportation details relative to commodity, weight, and quantity
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD1fromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("TD1");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("TD1", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 103 Packaging Code
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 80 Lading Quantity
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 23 Commodity Code Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 22 Commodity Code
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 79 Lading Description
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 187 Weight Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(7);     // 81 Weight
     if (de != null)
       de.get();
     de = segment.getDataElement(8);     // 355 Unit or Basis for Measurement Code
     if (de != null)
       de.get();
     de = segment.getDataElement(9);     // 183 Volume
     if (de != null)
       de.get();
     de = segment.getDataElement(10);     // 355 Unit or Basis for Measurement Code
     if (de != null)
       de.get();
    }
  }
/** extract data from segment TD5 that is part of the TableHeader
*<br>Carrier Details (Routing Sequence/Transit Time) used 
*<br>To specify the carrier and sequence of routing and provide transit time information
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD5fromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("TD5");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("TD5", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 133 Routing Sequence Code
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 66 Identification Code Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 67 Identification Code
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 91 Transportation Method/Type Code
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 387 Routing
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 368 Shipment/Order Status Code
     if (de != null)
       de.get();
     de = segment.getDataElement(7);     // 309 Location Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(8);     // 310 Location Identifier
     if (de != null)
       de.get();
     de = segment.getDataElement(9);     // 731 Transit Direction Code
     if (de != null)
       de.get();
     de = segment.getDataElement(10);     // 732 Transit Time Direction Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(11);     // 733 Transit Time
     if (de != null)
       de.get();
     de = segment.getDataElement(12);     // 284 Service Level Code
     if (de != null)
       de.get();
     de = segment.getDataElement(13);     // 284 Service Level Code
     if (de != null)
       de.get();
     de = segment.getDataElement(14);     // 284 Service Level Code
     if (de != null)
       de.get();
     de = segment.getDataElement(15);     // 26 Country Code
     if (de != null)
       de.get();
    }
  }
/** extract data from segment TD3 that is part of the TableHeader
*<br>Carrier Details (Equipment) used 
*<br>To specify transportation details relating to the equipment used by the carrier
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD3fromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("TD3");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("TD3", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 40 Equipment Description Code
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 206 Equipment Initial
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 207 Equipment Number
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 187 Weight Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 81 Weight
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 355 Unit or Basis for Measurement Code
     if (de != null)
       de.get();
     de = segment.getDataElement(7);     // 102 Ownership Code
     if (de != null)
       de.get();
     de = segment.getDataElement(8);     // 407 Seal Status Code
     if (de != null)
       de.get();
     de = segment.getDataElement(9);     // 225 Seal Number
     if (de != null)
       de.get();
     de = segment.getDataElement(10);     // 24 Equipment Type
     if (de != null)
       de.get();
    }
  }
/** extract data from segment TD4 that is part of the TableHeader
*<br>Carrier Details (Special Handling, or Hazardous Materials, or Both) used 
*<br>To specify transportation special handling requirements, or hazardous materials information, or both
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD4fromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("TD4");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("TD4", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 152 Special Handling Code
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 208 Hazardous Material Code Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 209 Hazardous Material Class Code
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 352 Description
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 1073 Yes/No Condition or Response Code
     if (de != null)
       de.get();
    }
  }
/** extract data from segment MAN that is part of the TableHeader
*<br>Marks and Numbers used 
*<br>To indicate identifying marks and numbers for shipping containers
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMANfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("MAN");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("MAN", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 88 Marks and Numbers Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 87 Marks and Numbers
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 87 Marks and Numbers
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 88 Marks and Numbers Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 87 Marks and Numbers
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 87 Marks and Numbers
     if (de != null)
       de.get();
    }
  }
/** extract data from segment CTB that is part of the TableHeader
*<br>Restrictions/Conditions used 
*<br>To specify restrictions/conditions (such as shipping, ordering)
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCTBfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("CTB");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("CTB", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 688 Restrictions/Conditions Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 352 Description
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 673 Quantity Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 380 Quantity
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 522 Amount Qualifier Code
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 610 Amount
     if (de != null)
       de.get();
    }
  }
/** extract data from segment CPR that is part of the TableHeader
*<br>Commodity Price Reference used 
*<br>To provide a specific price on a specified date for a given commodity
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCPRfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("CPR");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("CPR", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 1053 Market Exchange Identifier
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 373 Date
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 212 Unit Price
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 1054 Commodity Identification
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 1073 Yes/No Condition or Response Code
     if (de != null)
       de.get();
    }
  }
/** extract data from segment PCT that is part of the TableHeader
*<br>Percent Amounts used 
*<br>To qualify percent amounts and supply percent amounts
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPCTfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("PCT");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("PCT", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 1004 Percent Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 954 Percent
     if (de != null)
       de.get();
    }
  }
/** extract data from loop N9 that is part of TableHeader
*<br>Reference Identification used 
* @param inTable table containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopN9fromTableHeader(Table inTable)
  throws OBOEException
{
  Loop loop;
  int numberInVector = inTable.getCount("N9");
  for (int i = 0; i <  numberInVector; i++)
   {
     loop = inTable.getLoop("N9", i);
     if (loop == null) return;
     extractSegmentN9fromTableHeaderLoopN9(loop);
     extractSegmentDTMfromTableHeaderLoopN9(loop);
     extractSegmentMSGfromTableHeaderLoopN9(loop);
    }
  }
/** extract data from segment N9 that is part of the TableHeaderLoopN9
*<br>Reference Identification used 
*<br>To transmit identifying information as specified by the Reference Identification Qualifier
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentN9fromTableHeaderLoopN9(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("N9");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 128 Reference Identification Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 127 Reference Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 369 Free-form Description
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 337 Time
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 623 Time Code
  if (de != null)
    de.get();
    CompositeDE  composite = (CompositeDE) segment.getCompositeDE(7);  // C040 Reference Identifier
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
/** extract data from segment DTM that is part of the TableHeaderLoopN9
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDTMfromTableHeaderLoopN9(Loop inLoop)  throws OBOEException
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
/** extract data from segment MSG that is part of the TableHeaderLoopN9
*<br>Message Text used 
*<br>To provide a free-form format that allows the transmission of text information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMSGfromTableHeaderLoopN9(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("MSG");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("MSG", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 933 Free-Form Message Text
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 934 Printer Carriage Control Code
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 1470 Number
       if (de != null)
         de.get();
    }
  }

/** extract data from loop N1 that is part of TableHeader
*<br>Name used 
* @param inTable table containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopN1fromTableHeader(Table inTable)
  throws OBOEException
{
  Loop loop;
  int numberInVector = inTable.getCount("N1");
  for (int i = 0; i <  numberInVector; i++)
   {
     loop = inTable.getLoop("N1", i);
     if (loop == null) return;
     extractSegmentN1fromTableHeaderLoopN1(loop);
     extractSegmentN2fromTableHeaderLoopN1(loop);
     extractSegmentN3fromTableHeaderLoopN1(loop);
     extractSegmentN4fromTableHeaderLoopN1(loop);
     extractSegmentREFfromTableHeaderLoopN1(loop);
     extractSegmentPERfromTableHeaderLoopN1(loop);
     extractSegmentSIfromTableHeaderLoopN1(loop);
     extractSegmentFOBfromTableHeaderLoopN1(loop);
     extractSegmentTD1fromTableHeaderLoopN1(loop);
     extractSegmentTD5fromTableHeaderLoopN1(loop);
     extractSegmentTD3fromTableHeaderLoopN1(loop);
     extractSegmentTD4fromTableHeaderLoopN1(loop);
     extractSegmentPKGfromTableHeaderLoopN1(loop);
     extractSegmentMSG_2fromTableHeaderLoopN1(loop);
    }
  }
/** extract data from segment N1 that is part of the TableHeaderLoopN1
*<br>Name used 
*<br>To identify a party by type of organization, name, and code
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentN1fromTableHeaderLoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment N2 that is part of the TableHeaderLoopN1
*<br>Additional Name Information used 
*<br>To specify additional names or those longer than 35 characters in length
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentN2fromTableHeaderLoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment N3 that is part of the TableHeaderLoopN1
*<br>Address Information used 
*<br>To specify the location of the named party
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentN3fromTableHeaderLoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment N4 that is part of the TableHeaderLoopN1
*<br>Geographic Location used 
*<br>To specify the geographic place of the named party
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentN4fromTableHeaderLoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment REF that is part of the TableHeaderLoopN1
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentREFfromTableHeaderLoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment PER that is part of the TableHeaderLoopN1
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPERfromTableHeaderLoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment SI that is part of the TableHeaderLoopN1
*<br>Service Characteristic Identification used 
*<br>To specify service characteristic data
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSIfromTableHeaderLoopN1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("SI");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("SI", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 559 Agency Qualifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(12);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(13);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(14);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(15);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(16);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(17);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(18);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(19);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(20);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(21);       // 234 Product/Service ID
       if (de != null)
         de.get();
    }
  }
/** extract data from segment FOB that is part of the TableHeaderLoopN1
*<br>F.O.B. Related Instructions used 
*<br>To specify transportation instructions relating to shipment
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentFOBfromTableHeaderLoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment TD1 that is part of the TableHeaderLoopN1
*<br>Carrier Details (Quantity and Weight) used 
*<br>To specify the transportation details relative to commodity, weight, and quantity
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD1fromTableHeaderLoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment TD5 that is part of the TableHeaderLoopN1
*<br>Carrier Details (Routing Sequence/Transit Time) used 
*<br>To specify the carrier and sequence of routing and provide transit time information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD5fromTableHeaderLoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment TD3 that is part of the TableHeaderLoopN1
*<br>Carrier Details (Equipment) used 
*<br>To specify transportation details relating to the equipment used by the carrier
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD3fromTableHeaderLoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment TD4 that is part of the TableHeaderLoopN1
*<br>Carrier Details (Special Handling, or Hazardous Materials, or Both) used 
*<br>To specify transportation special handling requirements, or hazardous materials information, or both
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD4fromTableHeaderLoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment PKG that is part of the TableHeaderLoopN1
*<br>Marking, Packaging, Loading used 
*<br>To describe marking, packaging, loading, and unloading requirements
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPKGfromTableHeaderLoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment MSG that is part of the TableHeaderLoopN1
*<br>Message Text used 
*<br>To provide a free-form format that allows the transmission of text information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMSG_2fromTableHeaderLoopN1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("MSG");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("MSG", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 933 Free-Form Message Text
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 934 Printer Carriage Control Code
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 1470 Number
       if (de != null)
         de.get();
    }
  }

/** extract data from loop AMT that is part of TableHeader
*<br>Monetary Amount used 
* @param inTable table containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopAMTfromTableHeader(Table inTable)
  throws OBOEException
{
  Loop loop;
  int numberInVector = inTable.getCount("AMT");
  for (int i = 0; i <  numberInVector; i++)
   {
     loop = inTable.getLoop("AMT", i);
     if (loop == null) return;
     extractSegmentAMTfromTableHeaderLoopAMT(loop);
     extractSegmentPCTfromTableHeaderLoopAMT(loop);
    }
  }
/** extract data from segment AMT that is part of the TableHeaderLoopAMT
*<br>Monetary Amount used 
*<br>To indicate the total monetary amount
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentAMTfromTableHeaderLoopAMT(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("AMT");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 522 Amount Qualifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 782 Monetary Amount
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 478 Credit/Debit Flag Code
  if (de != null)
    de.get();
  }
/** extract data from segment PCT that is part of the TableHeaderLoopAMT
*<br>Percent Amounts used 
*<br>To qualify percent amounts and supply percent amounts
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPCTfromTableHeaderLoopAMT(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("PCT");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("PCT", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 1004 Percent Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 954 Percent
       if (de != null)
         de.get();
    }
  }

/** extract data from loop ADV that is part of TableHeader
*<br>Advertising Demographic Information used 
* @param inTable table containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopADVfromTableHeader(Table inTable)
  throws OBOEException
{
  Loop loop;
  int numberInVector = inTable.getCount("ADV");
  for (int i = 0; i <  numberInVector; i++)
   {
     loop = inTable.getLoop("ADV", i);
     if (loop == null) return;
     extractSegmentADVfromTableHeaderLoopADV(loop);
     extractSegmentDTM_2fromTableHeaderLoopADV(loop);
     extractSegmentMTXfromTableHeaderLoopADV(loop);
    }
  }
/** extract data from segment ADV that is part of the TableHeaderLoopADV
*<br>Advertising Demographic Information used 
*<br>To convey advertising demographic information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentADVfromTableHeaderLoopADV(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("ADV");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 559 Agency Qualifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 1000 Service Characteristics Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 740 Range Minimum
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 741 Range Maximum
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 729 Category
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 1000 Service Characteristics Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 739 Measurement Value
  if (de != null)
    de.get();
  }
/** extract data from segment DTM that is part of the TableHeaderLoopADV
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDTM_2fromTableHeaderLoopADV(Loop inLoop)  throws OBOEException
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
/** extract data from segment MTX that is part of the TableHeaderLoopADV
*<br>Text used 
*<br>To specify textual data
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMTXfromTableHeaderLoopADV(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("MTX");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("MTX", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 363 Note Reference Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 1551 Message Text
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 1551 Message Text
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 934 Printer Carriage Control Code
       if (de != null)
         de.get();
    }
  }

/** extract data from loop LDT that is part of TableHeader
*<br>Lead Time used 
* @param inTable table containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopLDTfromTableHeader(Table inTable)
  throws OBOEException
{
  Loop loop;
  int numberInVector = inTable.getCount("LDT");
  for (int i = 0; i <  numberInVector; i++)
   {
     loop = inTable.getLoop("LDT", i);
     if (loop == null) return;
     extractSegmentLDTfromTableHeaderLoopLDT(loop);
     extractSegmentQTYfromTableHeaderLoopLDT(loop);
     extractSegmentMTX_2fromTableHeaderLoopLDT(loop);
    }
  }
/** extract data from segment LDT that is part of the TableHeaderLoopLDT
*<br>Lead Time used 
*<br>To specify lead time for availability of products and services
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLDTfromTableHeaderLoopLDT(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("LDT");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 345 Lead Time Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 380 Quantity
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 344 Unit of Time Period or Interval
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 373 Date
  if (de != null)
    de.get();
  }
/** extract data from segment QTY that is part of the TableHeaderLoopLDT
*<br>Quantity used 
*<br>To specify quantity information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentQTYfromTableHeaderLoopLDT(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("QTY");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("QTY", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 673 Quantity Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 380 Quantity
       if (de != null)
         de.get();
         CompositeDE  composite = (CompositeDE) segment.getCompositeDE(3);  // C001 Composite Unit of Measure
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
       de = segment.getDataElement(4);       // 61 Free-Form Message
       if (de != null)
         de.get();
    }
  }
/** extract data from segment MTX that is part of the TableHeaderLoopLDT
*<br>Text used 
*<br>To specify textual data
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMTX_2fromTableHeaderLoopLDT(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("MTX");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("MTX", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 363 Note Reference Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 1551 Message Text
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 1551 Message Text
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 934 Printer Carriage Control Code
       if (de != null)
         de.get();
    }
  }

/** extract data from loop PO1 that is part of TableDetail
*<br>Baseline Item Data used 
* @param inTable table containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopPO1fromTableDetail(Table inTable)
  throws OBOEException
{
  Loop loop;
  int numberInVector = inTable.getCount("PO1");
  for (int i = 0; i <  numberInVector; i++)
   {
     loop = inTable.getLoop("PO1", i);
     if (loop == null) return;
     extractSegmentPO1fromTableDetailLoopPO1(loop);
     extractSegmentLINfromTableDetailLoopPO1(loop);
     extractSegmentCURfromTableDetailLoopPO1(loop);
     extractSegmentPO3fromTableDetailLoopPO1(loop);
     extractSegmentCTPfromTableDetailLoopPO1(loop);
     extractSegmentPAMfromTableDetailLoopPO1(loop);
     extractSegmentMEAfromTableDetailLoopPO1(loop);
     extractLoopPIDfromTableDetailLoopPO1(loop);
     extractSegmentPWKfromTableDetailLoopPO1(loop);
     extractSegmentPKGfromTableDetailLoopPO1(loop);
     extractSegmentPO4fromTableDetailLoopPO1(loop);
     extractSegmentREFfromTableDetailLoopPO1(loop);
     extractSegmentPERfromTableDetailLoopPO1(loop);
     extractSegmentSACfromTableDetailLoopPO1(loop);
     extractSegmentIT8fromTableDetailLoopPO1(loop);
     extractSegmentCSHfromTableDetailLoopPO1(loop);
     extractSegmentITDfromTableDetailLoopPO1(loop);
     extractSegmentDISfromTableDetailLoopPO1(loop);
     extractSegmentTAXfromTableDetailLoopPO1(loop);
     extractSegmentFOBfromTableDetailLoopPO1(loop);
     extractSegmentSDQfromTableDetailLoopPO1(loop);
     extractSegmentDTMfromTableDetailLoopPO1(loop);
     extractSegmentFSTfromTableDetailLoopPO1(loop);
     extractSegmentTD1fromTableDetailLoopPO1(loop);
     extractSegmentTD5fromTableDetailLoopPO1(loop);
     extractSegmentTD3fromTableDetailLoopPO1(loop);
     extractSegmentTD4fromTableDetailLoopPO1(loop);
     extractSegmentMANfromTableDetailLoopPO1(loop);
     extractSegmentPCTfromTableDetailLoopPO1(loop);
     extractSegmentMSGfromTableDetailLoopPO1(loop);
     extractSegmentCTBfromTableDetailLoopPO1(loop);
     extractSegmentSPIfromTableDetailLoopPO1(loop);
     extractLoopQTYfromTableDetailLoopPO1(loop);
     extractLoopSCHfromTableDetailLoopPO1(loop);
     extractLoopCSTfromTableDetailLoopPO1(loop);
     extractLoopSLNfromTableDetailLoopPO1(loop);
     extractLoopPDfromTableDetailLoopPO1(loop);
     extractLoopLDTfromTableDetailLoopPO1(loop);
     extractLoopN9fromTableDetailLoopPO1(loop);
     extractLoopN1fromTableDetailLoopPO1(loop);
     extractLoopAMTfromTableDetailLoopPO1(loop);
    }
  }
/** extract data from segment PO1 that is part of the TableDetailLoopPO1
*<br>Baseline Item Data used 
*<br>To specify basic and most frequently used line item data
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPO1fromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("PO1");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 350 Assigned Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 330 Quantity Ordered
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 212 Unit Price
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 639 Basis of Unit Price Code
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
  }
/** extract data from segment LIN that is part of the TableDetailLoopPO1
*<br>Item Identification used 
*<br>To specify basic item identification data
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLINfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("LIN");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("LIN", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 350 Assigned Identification
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(12);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(13);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(14);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(15);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(16);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(17);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(18);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(19);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(20);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(21);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(22);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(23);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(24);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(25);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(26);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(27);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(28);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(29);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(30);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(31);       // 234 Product/Service ID
       if (de != null)
         de.get();
    }
  }
/** extract data from segment CUR that is part of the TableDetailLoopPO1
*<br>Currency used 
*<br>To specify the currency (dollars, pounds, francs, etc.) used in a transaction
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCURfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
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
/** extract data from segment PO3 that is part of the TableDetailLoopPO1
*<br>Additional Item Detail used 
*<br>To specify additional item-related data involving variations in normal price/quantity structure
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPO3fromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("PO3");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("PO3", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 371 Change Reason Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 373 Date
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 236 Price Identifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 212 Unit Price
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 639 Basis of Unit Price Code
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 380 Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 355 Unit or Basis for Measurement Code
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 352 Description
       if (de != null)
         de.get();
    }
  }
/** extract data from segment CTP that is part of the TableDetailLoopPO1
*<br>Pricing Information used 
*<br>To specify pricing information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCTPfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("CTP");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("CTP", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 687 Class of Trade Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 236 Price Identifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 212 Unit Price
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
       de = segment.getDataElement(6);       // 648 Price Multiplier Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 649 Multiplier
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 782 Monetary Amount
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 639 Basis of Unit Price Code
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 499 Condition Value
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 289 Multiple Price Quantity
       if (de != null)
         de.get();
    }
  }
/** extract data from segment PAM that is part of the TableDetailLoopPO1
*<br>Period Amount used 
*<br>To indicate a quantity, and/or amount for an identified period
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPAMfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("PAM");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("PAM", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 673 Quantity Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 380 Quantity
       if (de != null)
         de.get();
         CompositeDE  composite = (CompositeDE) segment.getCompositeDE(3);  // C001 Composite Unit of Measure
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
       de = segment.getDataElement(4);       // 522 Amount Qualifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 782 Monetary Amount
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 344 Unit of Time Period or Interval
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 374 Date/Time Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 373 Date
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 337 Time
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 374 Date/Time Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 373 Date
       if (de != null)
         de.get();
       de = segment.getDataElement(12);       // 337 Time
       if (de != null)
         de.get();
       de = segment.getDataElement(13);       // 1004 Percent Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(14);       // 954 Percent
       if (de != null)
         de.get();
       de = segment.getDataElement(15);       // 1073 Yes/No Condition or Response Code
       if (de != null)
         de.get();
    }
  }
/** extract data from segment MEA that is part of the TableDetailLoopPO1
*<br>Measurements used 
*<br>To specify physical measurements or counts, including dimensions, tolerances, variances, and weights  (See Figures Appendix for example of use of C001)
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMEAfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
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
/** extract data from loop PID that is part of TableDetailLoopPO1
*<br>Product/Item Description used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopPIDfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("PID");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("PID", i);
   if (loop == null) return;
      extractSegmentPIDfromTableDetailLoopPO1LoopPID(loop);
      extractSegmentMEAfromTableDetailLoopPO1LoopPID(loop);
    }
  }
/** extract data from segment PID that is part of the TableDetailLoopPO1LoopPID
*<br>Product/Item Description used 
*<br>To describe a product or process in coded or free-form format
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPIDfromTableDetailLoopPO1LoopPID(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("PID");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 349 Item Description Type
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 750 Product/Process Characteristic Code
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 559 Agency Qualifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 751 Product Description Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 352 Description
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 752 Surface/Layer/Position Code
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 822 Source Subqualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 1073 Yes/No Condition or Response Code
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 819 Language Code
  if (de != null)
    de.get();
  }
/** extract data from segment MEA that is part of the TableDetailLoopPO1LoopPID
*<br>Measurements used 
*<br>To specify physical measurements or counts, including dimensions, tolerances, variances, and weights  (See Figures Appendix for example of use of C001)
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMEAfromTableDetailLoopPO1LoopPID(Loop inLoop)  throws OBOEException
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

/** extract data from segment PWK that is part of the TableDetailLoopPO1
*<br>Paperwork used 
*<br>To identify the type or transmission or both of paperwork or supporting information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPWKfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
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
/** extract data from segment PKG that is part of the TableDetailLoopPO1
*<br>Marking, Packaging, Loading used 
*<br>To describe marking, packaging, loading, and unloading requirements
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPKGfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
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
/** extract data from segment PO4 that is part of the TableDetailLoopPO1
*<br>Item Physical Details used 
*<br>To specify the physical qualities, packaging, weights, and dimensions relating to the item
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPO4fromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("PO4");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("PO4", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 356 Pack
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 357 Size
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 355 Unit or Basis for Measurement Code
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 103 Packaging Code
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 187 Weight Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 384 Gross Weight per Pack
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 355 Unit or Basis for Measurement Code
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 385 Gross Volume per Pack
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 355 Unit or Basis for Measurement Code
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 82 Length
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 189 Width
       if (de != null)
         de.get();
       de = segment.getDataElement(12);       // 65 Height
       if (de != null)
         de.get();
       de = segment.getDataElement(13);       // 355 Unit or Basis for Measurement Code
       if (de != null)
         de.get();
       de = segment.getDataElement(14);       // 810 Inner Pack
       if (de != null)
         de.get();
       de = segment.getDataElement(15);       // 752 Surface/Layer/Position Code
       if (de != null)
         de.get();
       de = segment.getDataElement(16);       // 350 Assigned Identification
       if (de != null)
         de.get();
       de = segment.getDataElement(17);       // 350 Assigned Identification
       if (de != null)
         de.get();
       de = segment.getDataElement(18);       // 1470 Number
       if (de != null)
         de.get();
    }
  }
/** extract data from segment REF that is part of the TableDetailLoopPO1
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentREFfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
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
/** extract data from segment PER that is part of the TableDetailLoopPO1
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPERfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
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
/** extract data from segment SAC that is part of the TableDetailLoopPO1
*<br>Service, Promotion, Allowance, or Charge Information used 
*<br>To request or identify a service, promotion, allowance, or charge; to specify the amount or percentage for the service, promotion, allowance, or charge
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSACfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("SAC");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("SAC", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 248 Allowance or Charge Indicator
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 1300 Service, Promotion, Allowance, or Charge Code
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 559 Agency Qualifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 1301 Agency Service, Promotion, Allowance, or Charge Code
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 610 Amount
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 378 Allowance/Charge Percent Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 332 Percent
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 118 Rate
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 355 Unit or Basis for Measurement Code
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 380 Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 380 Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(12);       // 331 Allowance or Charge Method of Handling Code
       if (de != null)
         de.get();
       de = segment.getDataElement(13);       // 127 Reference Identification
       if (de != null)
         de.get();
       de = segment.getDataElement(14);       // 770 Option Number
       if (de != null)
         de.get();
       de = segment.getDataElement(15);       // 352 Description
       if (de != null)
         de.get();
       de = segment.getDataElement(16);       // 819 Language Code
       if (de != null)
         de.get();
    }
  }
/** extract data from segment IT8 that is part of the TableDetailLoopPO1
*<br>Conditions of Sale used 
*<br>To specify general conditions or requirements and to detail conditions for substitution of alternate products
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentIT8fromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("IT8");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("IT8", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 563 Sales Requirement Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 306 Action Code
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 610 Amount
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 508 Account Number
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 373 Date
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 559 Agency Qualifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 566 Product/Service Substitution Code
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(12);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(13);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(14);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(15);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(16);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(17);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(18);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(19);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(20);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(21);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(22);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(23);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(24);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(25);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(26);       // 235 Product/Service ID Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(27);       // 234 Product/Service ID
       if (de != null)
         de.get();
    }
  }
/** extract data from segment CSH that is part of the TableDetailLoopPO1
*<br>Sales Requirements used 
*<br>To specify general conditions or requirements of the sale
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCSHfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("CSH");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("CSH", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 563 Sales Requirement Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 306 Action Code
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 610 Amount
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 508 Account Number
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 373 Date
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 559 Agency Qualifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 560 Special Services Code
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 566 Product/Service Substitution Code
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 954 Percent
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 1004 Percent Qualifier
       if (de != null)
         de.get();
    }
  }
/** extract data from segment ITD that is part of the TableDetailLoopPO1
*<br>Terms of Sale/Deferred Terms of Sale used 
*<br>To specify terms of sale
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentITDfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("ITD");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("ITD", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 336 Terms Type Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 333 Terms Basis Date Code
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 338 Terms Discount Percent
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 370 Terms Discount Due Date
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 351 Terms Discount Days Due
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 446 Terms Net Due Date
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 386 Terms Net Days
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 362 Terms Discount Amount
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 388 Terms Deferred Due Date
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 389 Deferred Amount Due
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 342 Percent of Invoice Payable
       if (de != null)
         de.get();
       de = segment.getDataElement(12);       // 352 Description
       if (de != null)
         de.get();
       de = segment.getDataElement(13);       // 765 Day of Month
       if (de != null)
         de.get();
       de = segment.getDataElement(14);       // 107 Payment Method Code
       if (de != null)
         de.get();
       de = segment.getDataElement(15);       // 954 Percent
       if (de != null)
         de.get();
    }
  }
/** extract data from segment DIS that is part of the TableDetailLoopPO1
*<br>Discount Detail used 
*<br>To specify the exact type and terms of various discount information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDISfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("DIS");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("DIS", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 653 Discount Terms Type Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 654 Discount Base Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 655 Discount Base Value
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 656 Discount Control Limit Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 657 Discount Control Limit
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 657 Discount Control Limit
       if (de != null)
         de.get();
    }
  }
/** extract data from segment TAX that is part of the TableDetailLoopPO1
*<br>Tax Reference used 
*<br>To provide data required for proper notification/determination of applicable taxes applying to the transaction or business described in the transaction
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTAXfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("TAX");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("TAX", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 325 Tax Identification Number
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 309 Location Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 310 Location Identifier
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 309 Location Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 310 Location Identifier
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 309 Location Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 310 Location Identifier
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 309 Location Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 310 Location Identifier
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 309 Location Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 310 Location Identifier
       if (de != null)
         de.get();
       de = segment.getDataElement(12);       // 441 Tax Exempt Code
       if (de != null)
         de.get();
       de = segment.getDataElement(13);       // 1179 Customs Entry Type Group Code
       if (de != null)
         de.get();
    }
  }
/** extract data from segment FOB that is part of the TableDetailLoopPO1
*<br>F.O.B. Related Instructions used 
*<br>To specify transportation instructions relating to shipment
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentFOBfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("FOB");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("FOB", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 146 Shipment Method of Payment
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 309 Location Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 352 Description
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 334 Transportation Terms Qualifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 335 Transportation Terms Code
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 309 Location Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 352 Description
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 54 Risk of Loss Code
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 352 Description
       if (de != null)
         de.get();
    }
  }
/** extract data from segment SDQ that is part of the TableDetailLoopPO1
*<br>Destination Quantity used 
*<br>To specify destination and quantity detail
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSDQfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
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
/** extract data from segment DTM that is part of the TableDetailLoopPO1
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDTMfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
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
/** extract data from segment FST that is part of the TableDetailLoopPO1
*<br>Forecast Schedule used 
*<br>To specify the forecasted dates and quantities
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentFSTfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("FST");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("FST", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 380 Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 680 Forecast Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 681 Forecast Timing Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 373 Date
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 373 Date
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 374 Date/Time Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 337 Time
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 128 Reference Identification Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 127 Reference Identification
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 783 Planning Schedule Type Code
       if (de != null)
         de.get();
    }
  }
/** extract data from segment TD1 that is part of the TableDetailLoopPO1
*<br>Carrier Details (Quantity and Weight) used 
*<br>To specify the transportation details relative to commodity, weight, and quantity
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD1fromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("TD1");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 103 Packaging Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 80 Lading Quantity
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 23 Commodity Code Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 22 Commodity Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 79 Lading Description
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 187 Weight Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 81 Weight
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 183 Volume
  if (de != null)
    de.get();
  de = segment.getDataElement(10);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  }
/** extract data from segment TD5 that is part of the TableDetailLoopPO1
*<br>Carrier Details (Routing Sequence/Transit Time) used 
*<br>To specify the carrier and sequence of routing and provide transit time information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD5fromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
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
/** extract data from segment TD3 that is part of the TableDetailLoopPO1
*<br>Carrier Details (Equipment) used 
*<br>To specify transportation details relating to the equipment used by the carrier
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD3fromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
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
/** extract data from segment TD4 that is part of the TableDetailLoopPO1
*<br>Carrier Details (Special Handling, or Hazardous Materials, or Both) used 
*<br>To specify transportation special handling requirements, or hazardous materials information, or both
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD4fromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
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
/** extract data from segment MAN that is part of the TableDetailLoopPO1
*<br>Marks and Numbers used 
*<br>To indicate identifying marks and numbers for shipping containers
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMANfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
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
/** extract data from segment PCT that is part of the TableDetailLoopPO1
*<br>Percent Amounts used 
*<br>To qualify percent amounts and supply percent amounts
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPCTfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("PCT");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("PCT", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 1004 Percent Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 954 Percent
       if (de != null)
         de.get();
    }
  }
/** extract data from segment MSG that is part of the TableDetailLoopPO1
*<br>Message Text used 
*<br>To provide a free-form format that allows the transmission of text information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMSGfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("MSG");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("MSG", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 933 Free-Form Message Text
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 934 Printer Carriage Control Code
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 1470 Number
       if (de != null)
         de.get();
    }
  }
/** extract data from segment CTB that is part of the TableDetailLoopPO1
*<br>Restrictions/Conditions used 
*<br>To specify restrictions/conditions (such as shipping, ordering)
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCTBfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("CTB");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("CTB", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 688 Restrictions/Conditions Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 352 Description
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 673 Quantity Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 380 Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 522 Amount Qualifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 610 Amount
       if (de != null)
         de.get();
    }
  }
/** extract data from segment SPI that is part of the TableDetailLoopPO1
*<br>Specification Identifier used 
*<br>To provide a description of the included specification or technical data items
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSPIfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("SPI");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("SPI", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 786 Security Level Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 128 Reference Identification Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 127 Reference Identification
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 790 Entity Title
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 791 Entity Purpose
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 792 Entity Status Code
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 353 Transaction Set Purpose Code
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 755 Report Type Code
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 786 Security Level Code
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 559 Agency Qualifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 822 Source Subqualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(12);       // 554 Assigned Number
       if (de != null)
         de.get();
       de = segment.getDataElement(13);       // 1322 Certification Type Code
       if (de != null)
         de.get();
       de = segment.getDataElement(14);       // 1401 Proposal Data Detail Identifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(15);       // 1005 Hierarchical Structure Code
       if (de != null)
         de.get();
    }
  }
/** extract data from loop QTY that is part of TableDetailLoopPO1
*<br>Quantity used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopQTYfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("QTY");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("QTY", i);
   if (loop == null) return;
      extractSegmentQTYfromTableDetailLoopPO1LoopQTY(loop);
      extractSegmentSIfromTableDetailLoopPO1LoopQTY(loop);
    }
  }
/** extract data from segment QTY that is part of the TableDetailLoopPO1LoopQTY
*<br>Quantity used 
*<br>To specify quantity information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentQTYfromTableDetailLoopPO1LoopQTY(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("QTY");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 673 Quantity Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 380 Quantity
  if (de != null)
    de.get();
    CompositeDE  composite = (CompositeDE) segment.getCompositeDE(3);  // C001 Composite Unit of Measure
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
  de = segment.getDataElement(4);  // 61 Free-Form Message
  if (de != null)
    de.get();
  }
/** extract data from segment SI that is part of the TableDetailLoopPO1LoopQTY
*<br>Service Characteristic Identification used 
*<br>To specify service characteristic data
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSIfromTableDetailLoopPO1LoopQTY(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("SI");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("SI", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 559 Agency Qualifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(12);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(13);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(14);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(15);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(16);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(17);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(18);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(19);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(20);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(21);       // 234 Product/Service ID
       if (de != null)
         de.get();
    }
  }

/** extract data from loop SCH that is part of TableDetailLoopPO1
*<br>Line Item Schedule used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopSCHfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("SCH");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("SCH", i);
   if (loop == null) return;
      extractSegmentSCHfromTableDetailLoopPO1LoopSCH(loop);
      extractSegmentTD1fromTableDetailLoopPO1LoopSCH(loop);
      extractSegmentTD5fromTableDetailLoopPO1LoopSCH(loop);
      extractSegmentTD3fromTableDetailLoopPO1LoopSCH(loop);
      extractSegmentTD4fromTableDetailLoopPO1LoopSCH(loop);
      extractSegmentREFfromTableDetailLoopPO1LoopSCH(loop);
    }
  }
/** extract data from segment SCH that is part of the TableDetailLoopPO1LoopSCH
*<br>Line Item Schedule used 
*<br>To specify the data for scheduling a specific line-item
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSCHfromTableDetailLoopPO1LoopSCH(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("SCH");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 380 Quantity
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 98 Entity Identifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 93 Name
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 374 Date/Time Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 337 Time
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 374 Date/Time Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(10);  // 337 Time
  if (de != null)
    de.get();
  de = segment.getDataElement(11);  // 326 Request Reference Number
  if (de != null)
    de.get();
  de = segment.getDataElement(12);  // 350 Assigned Identification
  if (de != null)
    de.get();
  }
/** extract data from segment TD1 that is part of the TableDetailLoopPO1LoopSCH
*<br>Carrier Details (Quantity and Weight) used 
*<br>To specify the transportation details relative to commodity, weight, and quantity
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD1fromTableDetailLoopPO1LoopSCH(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("TD1");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 103 Packaging Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 80 Lading Quantity
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 23 Commodity Code Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 22 Commodity Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 79 Lading Description
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 187 Weight Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 81 Weight
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 183 Volume
  if (de != null)
    de.get();
  de = segment.getDataElement(10);  // 355 Unit or Basis for Measurement Code
  if (de != null)
    de.get();
  }
/** extract data from segment TD5 that is part of the TableDetailLoopPO1LoopSCH
*<br>Carrier Details (Routing Sequence/Transit Time) used 
*<br>To specify the carrier and sequence of routing and provide transit time information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD5fromTableDetailLoopPO1LoopSCH(Loop inLoop)  throws OBOEException
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
/** extract data from segment TD3 that is part of the TableDetailLoopPO1LoopSCH
*<br>Carrier Details (Equipment) used 
*<br>To specify transportation details relating to the equipment used by the carrier
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD3fromTableDetailLoopPO1LoopSCH(Loop inLoop)  throws OBOEException
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
/** extract data from segment TD4 that is part of the TableDetailLoopPO1LoopSCH
*<br>Carrier Details (Special Handling, or Hazardous Materials, or Both) used 
*<br>To specify transportation special handling requirements, or hazardous materials information, or both
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD4fromTableDetailLoopPO1LoopSCH(Loop inLoop)  throws OBOEException
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
/** extract data from segment REF that is part of the TableDetailLoopPO1LoopSCH
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentREFfromTableDetailLoopPO1LoopSCH(Loop inLoop)  throws OBOEException
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

/** extract data from loop CST that is part of TableDetailLoopPO1
*<br>Cost Analysis used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopCSTfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("CST");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("CST", i);
   if (loop == null) return;
      extractSegmentCSTfromTableDetailLoopPO1LoopCST(loop);
      extractSegmentPID_2fromTableDetailLoopPO1LoopCST(loop);
      extractSegmentCURfromTableDetailLoopPO1LoopCST(loop);
      extractSegmentDTMfromTableDetailLoopPO1LoopCST(loop);
    }
  }
/** extract data from segment CST that is part of the TableDetailLoopPO1LoopCST
*<br>Cost Analysis used 
*<br>To provide detailed cost information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCSTfromTableDetailLoopPO1LoopCST(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("CST");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 964 Cost Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 782 Monetary Amount
  if (de != null)
    de.get();
    CompositeDE  composite = (CompositeDE) segment.getCompositeDE(3);  // C001 Composite Unit of Measure
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
  de = segment.getDataElement(4);  // 380 Quantity
  if (de != null)
    de.get();
  }
/** extract data from segment PID that is part of the TableDetailLoopPO1LoopCST
*<br>Product/Item Description used 
*<br>To describe a product or process in coded or free-form format
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPID_2fromTableDetailLoopPO1LoopCST(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("PID");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 349 Item Description Type
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 750 Product/Process Characteristic Code
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 559 Agency Qualifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 751 Product Description Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 352 Description
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 752 Surface/Layer/Position Code
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 822 Source Subqualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 1073 Yes/No Condition or Response Code
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 819 Language Code
  if (de != null)
    de.get();
  }
/** extract data from segment CUR that is part of the TableDetailLoopPO1LoopCST
*<br>Currency used 
*<br>To specify the currency (dollars, pounds, francs, etc.) used in a transaction
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCURfromTableDetailLoopPO1LoopCST(Loop inLoop)  throws OBOEException
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
/** extract data from segment DTM that is part of the TableDetailLoopPO1LoopCST
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDTMfromTableDetailLoopPO1LoopCST(Loop inLoop)  throws OBOEException
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

/** extract data from loop SLN that is part of TableDetailLoopPO1
*<br>Subline Item Detail used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopSLNfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("SLN");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("SLN", i);
   if (loop == null) return;
      extractSegmentSLNfromTableDetailLoopPO1LoopSLN(loop);
      extractSegmentMSGfromTableDetailLoopPO1LoopSLN(loop);
      extractSegmentPID_3fromTableDetailLoopPO1LoopSLN(loop);
      extractSegmentADVfromTableDetailLoopPO1LoopSLN(loop);
      extractLoopQTY_2fromTableDetailLoopPO1LoopSLN(loop);
      extractLoopCST_2fromTableDetailLoopPO1LoopSLN(loop);
    }
  }
/** extract data from segment SLN that is part of the TableDetailLoopPO1LoopSLN
*<br>Subline Item Detail used 
*<br>To specify product subline detail item data
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSLNfromTableDetailLoopPO1LoopSLN(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("SLN");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 350 Assigned Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 350 Assigned Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 662 Relationship Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 380 Quantity
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
  de = segment.getDataElement(6);  // 212 Unit Price
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 639 Basis of Unit Price Code
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 662 Relationship Code
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(10);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(11);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(12);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(13);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(14);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(15);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(16);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(17);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(18);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(19);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(20);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(21);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(22);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(23);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(24);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(25);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(26);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(27);  // 235 Product/Service ID Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(28);  // 234 Product/Service ID
  if (de != null)
    de.get();
  }
/** extract data from segment MSG that is part of the TableDetailLoopPO1LoopSLN
*<br>Message Text used 
*<br>To provide a free-form format that allows the transmission of text information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMSGfromTableDetailLoopPO1LoopSLN(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("MSG");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("MSG", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 933 Free-Form Message Text
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 934 Printer Carriage Control Code
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 1470 Number
       if (de != null)
         de.get();
    }
  }
/** extract data from segment PID that is part of the TableDetailLoopPO1LoopSLN
*<br>Product/Item Description used 
*<br>To describe a product or process in coded or free-form format
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPID_3fromTableDetailLoopPO1LoopSLN(Loop inLoop)  throws OBOEException
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
/** extract data from segment ADV that is part of the TableDetailLoopPO1LoopSLN
*<br>Advertising Demographic Information used 
*<br>To convey advertising demographic information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentADVfromTableDetailLoopPO1LoopSLN(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("ADV");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("ADV", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 559 Agency Qualifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 740 Range Minimum
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 741 Range Maximum
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 729 Category
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 739 Measurement Value
       if (de != null)
         de.get();
    }
  }
/** extract data from loop QTY that is part of TableDetailLoopPO1LoopSLN
*<br>Quantity used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopQTY_2fromTableDetailLoopPO1LoopSLN(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("QTY");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("QTY", i);
   if (loop == null) return;
      extractSegmentQTYfromTableDetailLoopPO1LoopSLNLoopQTY(loop);
      extractSegmentSIfromTableDetailLoopPO1LoopSLNLoopQTY(loop);
    }
  }
/** extract data from segment QTY that is part of the TableDetailLoopPO1LoopSLNLoopQTY
*<br>Quantity used 
*<br>To specify quantity information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentQTYfromTableDetailLoopPO1LoopSLNLoopQTY(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("QTY");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 673 Quantity Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 380 Quantity
  if (de != null)
    de.get();
    CompositeDE  composite = (CompositeDE) segment.getCompositeDE(3);  // C001 Composite Unit of Measure
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
  de = segment.getDataElement(4);  // 61 Free-Form Message
  if (de != null)
    de.get();
  }
/** extract data from segment SI that is part of the TableDetailLoopPO1LoopSLNLoopQTY
*<br>Service Characteristic Identification used 
*<br>To specify service characteristic data
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSIfromTableDetailLoopPO1LoopSLNLoopQTY(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("SI");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("SI", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 559 Agency Qualifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(12);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(13);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(14);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(15);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(16);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(17);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(18);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(19);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(20);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(21);       // 234 Product/Service ID
       if (de != null)
         de.get();
    }
  }

/** extract data from loop CST that is part of TableDetailLoopPO1LoopSLN
*<br>Cost Analysis used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopCST_2fromTableDetailLoopPO1LoopSLN(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("CST");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("CST", i);
   if (loop == null) return;
      extractSegmentCSTfromTableDetailLoopPO1LoopSLNLoopCST(loop);
      extractSegmentPIDfromTableDetailLoopPO1LoopSLNLoopCST(loop);
      extractSegmentCURfromTableDetailLoopPO1LoopSLNLoopCST(loop);
      extractSegmentDTMfromTableDetailLoopPO1LoopSLNLoopCST(loop);
    }
  }
/** extract data from segment CST that is part of the TableDetailLoopPO1LoopSLNLoopCST
*<br>Cost Analysis used 
*<br>To provide detailed cost information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCSTfromTableDetailLoopPO1LoopSLNLoopCST(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("CST");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 964 Cost Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 782 Monetary Amount
  if (de != null)
    de.get();
    CompositeDE  composite = (CompositeDE) segment.getCompositeDE(3);  // C001 Composite Unit of Measure
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
  de = segment.getDataElement(4);  // 380 Quantity
  if (de != null)
    de.get();
  }
/** extract data from segment PID that is part of the TableDetailLoopPO1LoopSLNLoopCST
*<br>Product/Item Description used 
*<br>To describe a product or process in coded or free-form format
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPIDfromTableDetailLoopPO1LoopSLNLoopCST(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("PID");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 349 Item Description Type
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 750 Product/Process Characteristic Code
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 559 Agency Qualifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 751 Product Description Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 352 Description
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 752 Surface/Layer/Position Code
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 822 Source Subqualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 1073 Yes/No Condition or Response Code
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 819 Language Code
  if (de != null)
    de.get();
  }
/** extract data from segment CUR that is part of the TableDetailLoopPO1LoopSLNLoopCST
*<br>Currency used 
*<br>To specify the currency (dollars, pounds, francs, etc.) used in a transaction
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCURfromTableDetailLoopPO1LoopSLNLoopCST(Loop inLoop)  throws OBOEException
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
/** extract data from segment DTM that is part of the TableDetailLoopPO1LoopSLNLoopCST
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDTMfromTableDetailLoopPO1LoopSLNLoopCST(Loop inLoop)  throws OBOEException
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


/** extract data from loop PD that is part of TableDetailLoopPO1
*<br>Pricing Data used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopPDfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("PD");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("PD", i);
   if (loop == null) return;
      extractSegmentPDfromTableDetailLoopPO1LoopPD(loop);
      extractSegmentPDDfromTableDetailLoopPO1LoopPD(loop);
    }
  }
/** extract data from segment PD that is part of the TableDetailLoopPO1LoopPD
*<br>Pricing Data used 
*<br>To describe the pricing basic input detail
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPDfromTableDetailLoopPO1LoopPD(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("PD");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 344 Unit of Time Period or Interval
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 373 Date
  if (de != null)
    de.get();
    CompositeDE  composite = (CompositeDE) segment.getCompositeDE(3);  // C001 Composite Unit of Measure
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
  de = segment.getDataElement(4);  // 380 Quantity
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 93 Name
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 352 Description
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 1196 Breakdown Structure Detail Code
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 127 Reference Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 352 Description
  if (de != null)
    de.get();
  de = segment.getDataElement(10);  // 1401 Proposal Data Detail Identifier Code
  if (de != null)
    de.get();
  }
/** extract data from segment PDD that is part of the TableDetailLoopPO1LoopPD
*<br>Pricing Data Detail used 
*<br>To provide the rates, direct input, and pricing factors for each element of work, cross-referenced to an applicable request or submission
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPDDfromTableDetailLoopPO1LoopPD(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("PDD");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("PDD", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 350 Assigned Identification
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 380 Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 782 Monetary Amount
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 954 Percent
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 1401 Proposal Data Detail Identifier Code
       if (de != null)
         de.get();
    }
  }

/** extract data from loop LDT that is part of TableDetailLoopPO1
*<br>Lead Time used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopLDTfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("LDT");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("LDT", i);
   if (loop == null) return;
      extractSegmentLDTfromTableDetailLoopPO1LoopLDT(loop);
      extractSegmentQTY_3fromTableDetailLoopPO1LoopLDT(loop);
      extractSegmentMSG_2fromTableDetailLoopPO1LoopLDT(loop);
      extractLoopLMfromTableDetailLoopPO1LoopLDT(loop);
    }
  }
/** extract data from segment LDT that is part of the TableDetailLoopPO1LoopLDT
*<br>Lead Time used 
*<br>To specify lead time for availability of products and services
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLDTfromTableDetailLoopPO1LoopLDT(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("LDT");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 345 Lead Time Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 380 Quantity
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 344 Unit of Time Period or Interval
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 373 Date
  if (de != null)
    de.get();
  }
/** extract data from segment QTY that is part of the TableDetailLoopPO1LoopLDT
*<br>Quantity used 
*<br>To specify quantity information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentQTY_3fromTableDetailLoopPO1LoopLDT(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("QTY");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("QTY", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 673 Quantity Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 380 Quantity
       if (de != null)
         de.get();
         CompositeDE  composite = (CompositeDE) segment.getCompositeDE(3);  // C001 Composite Unit of Measure
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
       de = segment.getDataElement(4);       // 61 Free-Form Message
       if (de != null)
         de.get();
    }
  }
/** extract data from segment MSG that is part of the TableDetailLoopPO1LoopLDT
*<br>Message Text used 
*<br>To provide a free-form format that allows the transmission of text information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMSG_2fromTableDetailLoopPO1LoopLDT(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("MSG");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 933 Free-Form Message Text
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 934 Printer Carriage Control Code
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 1470 Number
  if (de != null)
    de.get();
  }
/** extract data from loop LM that is part of TableDetailLoopPO1LoopLDT
*<br>Code Source Information used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopLMfromTableDetailLoopPO1LoopLDT(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("LM");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("LM", i);
   if (loop == null) return;
      extractSegmentLMfromTableDetailLoopPO1LoopLDTLoopLM(loop);
      extractSegmentLQfromTableDetailLoopPO1LoopLDTLoopLM(loop);
    }
  }
/** extract data from segment LM that is part of the TableDetailLoopPO1LoopLDTLoopLM
*<br>Code Source Information used 
*<br>To transmit standard code list identification information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLMfromTableDetailLoopPO1LoopLDTLoopLM(Loop inLoop)  throws OBOEException
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
/** extract data from segment LQ that is part of the TableDetailLoopPO1LoopLDTLoopLM
*<br>Industry Code used 
*<br>Code to transmit standard industry codes
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLQfromTableDetailLoopPO1LoopLDTLoopLM(Loop inLoop)  throws OBOEException
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


/** extract data from loop N9 that is part of TableDetailLoopPO1
*<br>Reference Identification used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopN9fromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("N9");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("N9", i);
   if (loop == null) return;
      extractSegmentN9fromTableDetailLoopPO1LoopN9(loop);
      extractSegmentDTM_2fromTableDetailLoopPO1LoopN9(loop);
      extractSegmentMSG_3fromTableDetailLoopPO1LoopN9(loop);
    }
  }
/** extract data from segment N9 that is part of the TableDetailLoopPO1LoopN9
*<br>Reference Identification used 
*<br>To transmit identifying information as specified by the Reference Identification Qualifier
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentN9fromTableDetailLoopPO1LoopN9(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("N9");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 128 Reference Identification Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 127 Reference Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 369 Free-form Description
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 337 Time
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 623 Time Code
  if (de != null)
    de.get();
    CompositeDE  composite = (CompositeDE) segment.getCompositeDE(7);  // C040 Reference Identifier
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
/** extract data from segment DTM that is part of the TableDetailLoopPO1LoopN9
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDTM_2fromTableDetailLoopPO1LoopN9(Loop inLoop)  throws OBOEException
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
/** extract data from segment MSG that is part of the TableDetailLoopPO1LoopN9
*<br>Message Text used 
*<br>To provide a free-form format that allows the transmission of text information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMSG_3fromTableDetailLoopPO1LoopN9(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("MSG");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("MSG", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 933 Free-Form Message Text
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 934 Printer Carriage Control Code
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 1470 Number
       if (de != null)
         de.get();
    }
  }

/** extract data from loop N1 that is part of TableDetailLoopPO1
*<br>Name used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopN1fromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("N1");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("N1", i);
   if (loop == null) return;
      extractSegmentN1fromTableDetailLoopPO1LoopN1(loop);
      extractSegmentN2fromTableDetailLoopPO1LoopN1(loop);
      extractSegmentN3fromTableDetailLoopPO1LoopN1(loop);
      extractSegmentN4fromTableDetailLoopPO1LoopN1(loop);
      extractSegmentQTY_4fromTableDetailLoopPO1LoopN1(loop);
      extractSegmentREF_2fromTableDetailLoopPO1LoopN1(loop);
      extractSegmentPERfromTableDetailLoopPO1LoopN1(loop);
      extractSegmentSI_2fromTableDetailLoopPO1LoopN1(loop);
      extractSegmentDTM_3fromTableDetailLoopPO1LoopN1(loop);
      extractSegmentFOBfromTableDetailLoopPO1LoopN1(loop);
      extractSegmentSCH_2fromTableDetailLoopPO1LoopN1(loop);
      extractSegmentTD1_2fromTableDetailLoopPO1LoopN1(loop);
      extractSegmentTD5_2fromTableDetailLoopPO1LoopN1(loop);
      extractSegmentTD3_2fromTableDetailLoopPO1LoopN1(loop);
      extractSegmentTD4_2fromTableDetailLoopPO1LoopN1(loop);
      extractSegmentPKGfromTableDetailLoopPO1LoopN1(loop);
      extractLoopLDT_2fromTableDetailLoopPO1LoopN1(loop);
    }
  }
/** extract data from segment N1 that is part of the TableDetailLoopPO1LoopN1
*<br>Name used 
*<br>To identify a party by type of organization, name, and code
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentN1fromTableDetailLoopPO1LoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment N2 that is part of the TableDetailLoopPO1LoopN1
*<br>Additional Name Information used 
*<br>To specify additional names or those longer than 35 characters in length
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentN2fromTableDetailLoopPO1LoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment N3 that is part of the TableDetailLoopPO1LoopN1
*<br>Address Information used 
*<br>To specify the location of the named party
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentN3fromTableDetailLoopPO1LoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment N4 that is part of the TableDetailLoopPO1LoopN1
*<br>Geographic Location used 
*<br>To specify the geographic place of the named party
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentN4fromTableDetailLoopPO1LoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment QTY that is part of the TableDetailLoopPO1LoopN1
*<br>Quantity used 
*<br>To specify quantity information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentQTY_4fromTableDetailLoopPO1LoopN1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("QTY");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("QTY", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 673 Quantity Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 380 Quantity
       if (de != null)
         de.get();
         CompositeDE  composite = (CompositeDE) segment.getCompositeDE(3);  // C001 Composite Unit of Measure
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
       de = segment.getDataElement(4);       // 61 Free-Form Message
       if (de != null)
         de.get();
    }
  }
/** extract data from segment REF that is part of the TableDetailLoopPO1LoopN1
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentREF_2fromTableDetailLoopPO1LoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment PER that is part of the TableDetailLoopPO1LoopN1
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPERfromTableDetailLoopPO1LoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment SI that is part of the TableDetailLoopPO1LoopN1
*<br>Service Characteristic Identification used 
*<br>To specify service characteristic data
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSI_2fromTableDetailLoopPO1LoopN1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("SI");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("SI", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 559 Agency Qualifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(12);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(13);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(14);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(15);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(16);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(17);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(18);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(19);       // 234 Product/Service ID
       if (de != null)
         de.get();
       de = segment.getDataElement(20);       // 1000 Service Characteristics Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(21);       // 234 Product/Service ID
       if (de != null)
         de.get();
    }
  }
/** extract data from segment DTM that is part of the TableDetailLoopPO1LoopN1
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDTM_3fromTableDetailLoopPO1LoopN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("DTM");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 374 Date/Time Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 337 Time
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 623 Time Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 1250 Date Time Period Format Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 1251 Date Time Period
  if (de != null)
    de.get();
  }
/** extract data from segment FOB that is part of the TableDetailLoopPO1LoopN1
*<br>F.O.B. Related Instructions used 
*<br>To specify transportation instructions relating to shipment
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentFOBfromTableDetailLoopPO1LoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment SCH that is part of the TableDetailLoopPO1LoopN1
*<br>Line Item Schedule used 
*<br>To specify the data for scheduling a specific line-item
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSCH_2fromTableDetailLoopPO1LoopN1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("SCH");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("SCH", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 380 Quantity
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 355 Unit or Basis for Measurement Code
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 98 Entity Identifier Code
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 93 Name
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 374 Date/Time Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 373 Date
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 337 Time
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 374 Date/Time Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 373 Date
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 337 Time
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 326 Request Reference Number
       if (de != null)
         de.get();
       de = segment.getDataElement(12);       // 350 Assigned Identification
       if (de != null)
         de.get();
    }
  }
/** extract data from segment TD1 that is part of the TableDetailLoopPO1LoopN1
*<br>Carrier Details (Quantity and Weight) used 
*<br>To specify the transportation details relative to commodity, weight, and quantity
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD1_2fromTableDetailLoopPO1LoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment TD5 that is part of the TableDetailLoopPO1LoopN1
*<br>Carrier Details (Routing Sequence/Transit Time) used 
*<br>To specify the carrier and sequence of routing and provide transit time information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD5_2fromTableDetailLoopPO1LoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment TD3 that is part of the TableDetailLoopPO1LoopN1
*<br>Carrier Details (Equipment) used 
*<br>To specify transportation details relating to the equipment used by the carrier
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD3_2fromTableDetailLoopPO1LoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment TD4 that is part of the TableDetailLoopPO1LoopN1
*<br>Carrier Details (Special Handling, or Hazardous Materials, or Both) used 
*<br>To specify transportation special handling requirements, or hazardous materials information, or both
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTD4_2fromTableDetailLoopPO1LoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment PKG that is part of the TableDetailLoopPO1LoopN1
*<br>Marking, Packaging, Loading used 
*<br>To describe marking, packaging, loading, and unloading requirements
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPKGfromTableDetailLoopPO1LoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from loop LDT that is part of TableDetailLoopPO1LoopN1
*<br>Lead Time used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopLDT_2fromTableDetailLoopPO1LoopN1(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("LDT");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("LDT", i);
   if (loop == null) return;
      extractSegmentLDTfromTableDetailLoopPO1LoopN1LoopLDT(loop);
      extractSegmentMANfromTableDetailLoopPO1LoopN1LoopLDT(loop);
      extractSegmentQTYfromTableDetailLoopPO1LoopN1LoopLDT(loop);
      extractSegmentMSGfromTableDetailLoopPO1LoopN1LoopLDT(loop);
    }
  }
/** extract data from segment LDT that is part of the TableDetailLoopPO1LoopN1LoopLDT
*<br>Lead Time used 
*<br>To specify lead time for availability of products and services
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLDTfromTableDetailLoopPO1LoopN1LoopLDT(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("LDT");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 345 Lead Time Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 380 Quantity
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 344 Unit of Time Period or Interval
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 373 Date
  if (de != null)
    de.get();
  }
/** extract data from segment MAN that is part of the TableDetailLoopPO1LoopN1LoopLDT
*<br>Marks and Numbers used 
*<br>To indicate identifying marks and numbers for shipping containers
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMANfromTableDetailLoopPO1LoopN1LoopLDT(Loop inLoop)  throws OBOEException
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
/** extract data from segment QTY that is part of the TableDetailLoopPO1LoopN1LoopLDT
*<br>Quantity used 
*<br>To specify quantity information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentQTYfromTableDetailLoopPO1LoopN1LoopLDT(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("QTY");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("QTY", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 673 Quantity Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 380 Quantity
       if (de != null)
         de.get();
         CompositeDE  composite = (CompositeDE) segment.getCompositeDE(3);  // C001 Composite Unit of Measure
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
       de = segment.getDataElement(4);       // 61 Free-Form Message
       if (de != null)
         de.get();
    }
  }
/** extract data from segment MSG that is part of the TableDetailLoopPO1LoopN1LoopLDT
*<br>Message Text used 
*<br>To provide a free-form format that allows the transmission of text information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMSGfromTableDetailLoopPO1LoopN1LoopLDT(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("MSG");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 933 Free-Form Message Text
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 934 Printer Carriage Control Code
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 1470 Number
  if (de != null)
    de.get();
  }


/** extract data from loop AMT that is part of TableDetailLoopPO1
*<br>Monetary Amount used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopAMTfromTableDetailLoopPO1(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("AMT");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("AMT", i);
   if (loop == null) return;
      extractSegmentAMTfromTableDetailLoopPO1LoopAMT(loop);
      extractSegmentPCTfromTableDetailLoopPO1LoopAMT(loop);
    }
  }
/** extract data from segment AMT that is part of the TableDetailLoopPO1LoopAMT
*<br>Monetary Amount used 
*<br>To indicate the total monetary amount
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentAMTfromTableDetailLoopPO1LoopAMT(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("AMT");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 522 Amount Qualifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 782 Monetary Amount
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 478 Credit/Debit Flag Code
  if (de != null)
    de.get();
  }
/** extract data from segment PCT that is part of the TableDetailLoopPO1LoopAMT
*<br>Percent Amounts used 
*<br>To qualify percent amounts and supply percent amounts
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPCTfromTableDetailLoopPO1LoopAMT(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("PCT");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("PCT", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 1004 Percent Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 954 Percent
       if (de != null)
         de.get();
    }
  }


/** extract data from loop CTT that is part of TableSummary
*<br>Transaction Totals used 
* @param inTable table containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopCTTfromTableSummary(Table inTable)
  throws OBOEException
{
  Loop loop = inTable.getLoop("CTT");
    if (loop == null) return;
  extractSegmentCTTfromTableSummaryLoopCTT(loop);
  extractSegmentAMTfromTableSummaryLoopCTT(loop);
  }
/** extract data from segment CTT that is part of the TableSummaryLoopCTT
*<br>Transaction Totals used 
*<br>To transmit a hash total for a specific element in the transaction set
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCTTfromTableSummaryLoopCTT(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("CTT");
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
/** extract data from segment AMT that is part of the TableSummaryLoopCTT
*<br>Monetary Amount used 
*<br>To indicate the total monetary amount
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentAMTfromTableSummaryLoopCTT(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("AMT");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 522 Amount Qualifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 782 Monetary Amount
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 478 Credit/Debit Flag Code
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
