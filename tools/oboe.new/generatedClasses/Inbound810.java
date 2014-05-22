import com.americancoders.edi.*;
import com.americancoders.edi.x12.*;
import java.io.*;

/** code template to parse
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
public class Inbound810
{
/** constructor for class Inbound810
*@param inFileName - String filename to be parsed
*@throws OBOEException - most likely transactionset not found
*@throws IOException - most likely input file not found
*/
public Inbound810(String inFileName)
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
                 extractSegmentBIGfromTableHeader(table);
                 extractSegmentNTEfromTableHeader(table);
                 extractSegmentCURfromTableHeader(table);
                 extractSegmentREFfromTableHeader(table);
                 extractSegmentYNQfromTableHeader(table);
                 extractSegmentPERfromTableHeader(table);
                 extractLoopN1fromTableHeader(table);
                 extractSegmentITDfromTableHeader(table);
                 extractSegmentDTMfromTableHeader(table);
                 extractSegmentFOBfromTableHeader(table);
                 extractSegmentPIDfromTableHeader(table);
                 extractSegmentMEAfromTableHeader(table);
                 extractSegmentPWKfromTableHeader(table);
                 extractSegmentPKGfromTableHeader(table);
                 extractSegmentL7fromTableHeader(table);
                 extractSegmentBALfromTableHeader(table);
                 extractSegmentINCfromTableHeader(table);
                 extractSegmentPAMfromTableHeader(table);
                 extractLoopLMfromTableHeader(table);
                 extractLoopN9fromTableHeader(table);
                 extractLoopV1fromTableHeader(table);
                 extractLoopFA1fromTableHeader(table);

              }
             table = ts.getDetailTable();
             if (table != null)
               {
                 extractLoopIT1fromTableDetail(table);

              }
            table = ts.getSummaryTable();
             if (table != null)
               {
                 extractSegmentTDSfromTableSummary(table);
                 extractSegmentTXIfromTableSummary(table);
                 extractSegmentCADfromTableSummary(table);
                 extractSegmentAMTfromTableSummary(table);
                 extractLoopSACfromTableSummary(table);
                 extractLoopISSfromTableSummary(table);
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
/** extract data from segment BIG that is part of the TableHeader
*<br>Beginning Segment for Invoice used 
*<br>To indicate the beginning of an invoice transaction set and transmit identifying numbers and dates
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentBIGfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.getSegment("BIG");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 76 Invoice Number
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 324 Purchase Order Number
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 328 Release Number
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 327 Change Order Sequence Number
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 640 Transaction Type Code
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 353 Transaction Set Purpose Code
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 306 Action Code
  if (de != null)
    de.get();
  de = segment.getDataElement(10);  // 76 Invoice Number
  if (de != null)
    de.get();
  }
/** extract data from segment NTE that is part of the TableHeader
*<br>Note/Special Instruction used 
*<br>To transmit information in a free-form format, if necessary, for comment or special instruction
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentNTEfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("NTE");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("NTE", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 363 Note Reference Code
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 352 Description
     if (de != null)
       de.get();
    }
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
/** extract data from segment YNQ that is part of the TableHeader
*<br>Yes/No Question used 
*<br>To identify and answer yes and no questions, including the date, time, and comments further qualifying the condition
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentYNQfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("YNQ");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("YNQ", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 1321 Condition Indicator
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 1073 Yes/No Condition or Response Code
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 1250 Date Time Period Format Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 1251 Date Time Period
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 933 Free-Form Message Text
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 933 Free-Form Message Text
     if (de != null)
       de.get();
     de = segment.getDataElement(7);     // 933 Free-Form Message Text
     if (de != null)
       de.get();
     de = segment.getDataElement(8);     // 1270 Code List Qualifier Code
     if (de != null)
       de.get();
     de = segment.getDataElement(9);     // 1271 Industry Code
     if (de != null)
       de.get();
     de = segment.getDataElement(10);     // 933 Free-Form Message Text
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
     extractSegmentDMGfromTableHeaderLoopN1(loop);
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
/** extract data from segment DMG that is part of the TableHeaderLoopN1
*<br>Demographic Information used 
*<br>To supply demographic information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDMGfromTableHeaderLoopN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("DMG");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 1250 Date Time Period Format Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 1251 Date Time Period
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 1068 Gender Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 1067 Marital Status Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 1109 Race or Ethnicity Code
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 1066 Citizenship Status Code
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 26 Country Code
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 659 Basis of Verification Code
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 380 Quantity
  if (de != null)
    de.get();
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
/** extract data from segment FOB that is part of the TableHeader
*<br>F.O.B. Related Instructions used 
*<br>To specify transportation instructions relating to shipment
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentFOBfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.getSegment("FOB");
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
/** extract data from segment L7 that is part of the TableHeader
*<br>Tariff Reference used 
*<br>To reference details of the tariff used to arrive at applicable rates or charge
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentL7fromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.getSegment("L7");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 213 Lading Line Item Number
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 168 Tariff Agency Code
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 171 Tariff Number
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 172 Tariff Section
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 169 Tariff Item Number
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 170 Tariff Item Part
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 59 Freight Class Code
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 173 Tariff Supplement Identifier
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 46 Ex Parte
  if (de != null)
    de.get();
  de = segment.getDataElement(10);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(11);  // 119 Rate Basis Number
  if (de != null)
    de.get();
  de = segment.getDataElement(12);  // 227 Tariff Column
  if (de != null)
    de.get();
  de = segment.getDataElement(13);  // 294 Tariff Distance
  if (de != null)
    de.get();
  de = segment.getDataElement(14);  // 295 Distance Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(15);  // 19 City Name
  if (de != null)
    de.get();
  de = segment.getDataElement(16);  // 156 State or Province Code
  if (de != null)
    de.get();
  }
/** extract data from segment BAL that is part of the TableHeader
*<br>Balance Detail used 
*<br>To identify the specific monetary balances associated with a particular account
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentBALfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("BAL");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("BAL", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 951 Balance Type Code
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 522 Amount Qualifier Code
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 782 Monetary Amount
     if (de != null)
       de.get();
    }
  }
/** extract data from segment INC that is part of the TableHeader
*<br>Installment Information used 
*<br>To specify installment billing arrangement
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentINCfromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.getSegment("INC");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 336 Terms Type Code
  if (de != null)
    de.get();
    CompositeDE  composite = (CompositeDE) segment.getCompositeDE(2);  // C001 Composite Unit of Measure
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
  de = segment.getDataElement(3);  // 380 Quantity
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 380 Quantity
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 782 Monetary Amount
  if (de != null)
    de.get();
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
/** extract data from loop LM that is part of TableHeader
*<br>Code Source Information used 
* @param inTable table containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopLMfromTableHeader(Table inTable)
  throws OBOEException
{
  Loop loop;
  int numberInVector = inTable.getCount("LM");
  for (int i = 0; i <  numberInVector; i++)
   {
     loop = inTable.getLoop("LM", i);
     if (loop == null) return;
     extractSegmentLMfromTableHeaderLoopLM(loop);
     extractSegmentLQfromTableHeaderLoopLM(loop);
    }
  }
/** extract data from segment LM that is part of the TableHeaderLoopLM
*<br>Code Source Information used 
*<br>To transmit standard code list identification information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLMfromTableHeaderLoopLM(Loop inLoop)  throws OBOEException
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
/** extract data from segment LQ that is part of the TableHeaderLoopLM
*<br>Industry Code used 
*<br>Code to transmit standard industry codes
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLQfromTableHeaderLoopLM(Loop inLoop)  throws OBOEException
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

/** extract data from loop N9 that is part of TableHeader
*<br>Reference Identification used 
* @param inTable table containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopN9fromTableHeader(Table inTable)
  throws OBOEException
{
  Loop loop = inTable.getLoop("N9");
    if (loop == null) return;
  extractSegmentN9fromTableHeaderLoopN9(loop);
  extractSegmentMSGfromTableHeaderLoopN9(loop);
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

/** extract data from loop V1 that is part of TableHeader
*<br>Vessel Identification used 
* @param inTable table containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopV1fromTableHeader(Table inTable)
  throws OBOEException
{
  Loop loop;
  int numberInVector = inTable.getCount("V1");
  for (int i = 0; i <  numberInVector; i++)
   {
     loop = inTable.getLoop("V1", i);
     if (loop == null) return;
     extractSegmentV1fromTableHeaderLoopV1(loop);
     extractSegmentR4fromTableHeaderLoopV1(loop);
     extractSegmentDTMfromTableHeaderLoopV1(loop);
    }
  }
/** extract data from segment V1 that is part of the TableHeaderLoopV1
*<br>Vessel Identification used 
*<br>To provide vessel details and voyage number
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentV1fromTableHeaderLoopV1(Loop inLoop)  throws OBOEException
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
/** extract data from segment R4 that is part of the TableHeaderLoopV1
*<br>Port or Terminal used 
*<br>Contractual or operational port or point relevant to the movement of the cargo
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentR4fromTableHeaderLoopV1(Loop inLoop)  throws OBOEException
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
/** extract data from segment DTM that is part of the TableHeaderLoopV1
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDTMfromTableHeaderLoopV1(Loop inLoop)  throws OBOEException
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

/** extract data from loop FA1 that is part of TableHeader
*<br>Type of Financial Accounting Data used 
* @param inTable table containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopFA1fromTableHeader(Table inTable)
  throws OBOEException
{
  Loop loop;
  int numberInVector = inTable.getCount("FA1");
  for (int i = 0; i <  numberInVector; i++)
   {
     loop = inTable.getLoop("FA1", i);
     if (loop == null) return;
     extractSegmentFA1fromTableHeaderLoopFA1(loop);
     extractSegmentFA2fromTableHeaderLoopFA1(loop);
    }
  }
/** extract data from segment FA1 that is part of the TableHeaderLoopFA1
*<br>Type of Financial Accounting Data used 
*<br>To specify the organization controlling the content of the accounting citation, and the purpose associated with the accounting citation
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentFA1fromTableHeaderLoopFA1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("FA1");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 559 Agency Qualifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 1300 Service, Promotion, Allowance, or Charge Code
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 248 Allowance or Charge Indicator
  if (de != null)
    de.get();
  }
/** extract data from segment FA2 that is part of the TableHeaderLoopFA1
*<br>Accounting Data used 
*<br>To specify the detailed accounting data
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentFA2fromTableHeaderLoopFA1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("FA2");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("FA2", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 1196 Breakdown Structure Detail Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 1195 Financial Information Code
       if (de != null)
         de.get();
    }
  }

/** extract data from loop IT1 that is part of TableDetail
*<br>Baseline Item Data (Invoice) used 
* @param inTable table containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopIT1fromTableDetail(Table inTable)
  throws OBOEException
{
  Loop loop;
  int numberInVector = inTable.getCount("IT1");
  for (int i = 0; i <  numberInVector; i++)
   {
     loop = inTable.getLoop("IT1", i);
     if (loop == null) return;
     extractSegmentIT1fromTableDetailLoopIT1(loop);
     extractSegmentCRCfromTableDetailLoopIT1(loop);
     extractSegmentQTYfromTableDetailLoopIT1(loop);
     extractSegmentCURfromTableDetailLoopIT1(loop);
     extractSegmentIT3fromTableDetailLoopIT1(loop);
     extractSegmentTXIfromTableDetailLoopIT1(loop);
     extractSegmentCTPfromTableDetailLoopIT1(loop);
     extractSegmentPAMfromTableDetailLoopIT1(loop);
     extractSegmentMEAfromTableDetailLoopIT1(loop);
     extractLoopPIDfromTableDetailLoopIT1(loop);
     extractSegmentPWKfromTableDetailLoopIT1(loop);
     extractSegmentPKGfromTableDetailLoopIT1(loop);
     extractSegmentPO4fromTableDetailLoopIT1(loop);
     extractSegmentITDfromTableDetailLoopIT1(loop);
     extractSegmentREFfromTableDetailLoopIT1(loop);
     extractSegmentYNQfromTableDetailLoopIT1(loop);
     extractSegmentPERfromTableDetailLoopIT1(loop);
     extractSegmentSDQfromTableDetailLoopIT1(loop);
     extractSegmentDTMfromTableDetailLoopIT1(loop);
     extractSegmentCADfromTableDetailLoopIT1(loop);
     extractSegmentL7fromTableDetailLoopIT1(loop);
     extractSegmentSRfromTableDetailLoopIT1(loop);
     extractLoopSACfromTableDetailLoopIT1(loop);
     extractLoopSLNfromTableDetailLoopIT1(loop);
     extractLoopN1fromTableDetailLoopIT1(loop);
     extractLoopLMfromTableDetailLoopIT1(loop);
     extractLoopV1fromTableDetailLoopIT1(loop);
     extractLoopFA1fromTableDetailLoopIT1(loop);
    }
  }
/** extract data from segment IT1 that is part of the TableDetailLoopIT1
*<br>Baseline Item Data (Invoice) used 
*<br>To specify the basic and most frequently used line item data for the invoice and related transactions
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentIT1fromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("IT1");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 350 Assigned Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 358 Quantity Invoiced
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
/** extract data from segment CRC that is part of the TableDetailLoopIT1
*<br>Conditions Indicator used 
*<br>To supply information on conditions
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCRCfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("CRC");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 1136 Code Category
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 1073 Yes/No Condition or Response Code
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 1321 Condition Indicator
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 1321 Condition Indicator
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 1321 Condition Indicator
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 1321 Condition Indicator
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 1321 Condition Indicator
  if (de != null)
    de.get();
  }
/** extract data from segment QTY that is part of the TableDetailLoopIT1
*<br>Quantity used 
*<br>To specify quantity information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentQTYfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
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
/** extract data from segment CUR that is part of the TableDetailLoopIT1
*<br>Currency used 
*<br>To specify the currency (dollars, pounds, francs, etc.) used in a transaction
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCURfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
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
/** extract data from segment IT3 that is part of the TableDetailLoopIT1
*<br>Additional Item Data used 
*<br>To specify additional item details relating to variations between ordered and shipped quantities, or to specify alternate units of measures and quantities
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentIT3fromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("IT3");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("IT3", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 382 Number of Units Shipped
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 355 Unit or Basis for Measurement Code
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 368 Shipment/Order Status Code
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 383 Quantity Difference
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 371 Change Reason Code
       if (de != null)
         de.get();
    }
  }
/** extract data from segment TXI that is part of the TableDetailLoopIT1
*<br>Tax Information used 
*<br>To specify tax information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTXIfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("TXI");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("TXI", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 963 Tax Type Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 782 Monetary Amount
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 954 Percent
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 955 Tax Jurisdiction Code Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 956 Tax Jurisdiction Code
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 441 Tax Exempt Code
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 662 Relationship Code
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 828 Dollar Basis For Percent
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 325 Tax Identification Number
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 350 Assigned Identification
       if (de != null)
         de.get();
    }
  }
/** extract data from segment CTP that is part of the TableDetailLoopIT1
*<br>Pricing Information used 
*<br>To specify pricing information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCTPfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
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
/** extract data from segment PAM that is part of the TableDetailLoopIT1
*<br>Period Amount used 
*<br>To indicate a quantity, and/or amount for an identified period
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPAMfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
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
/** extract data from segment MEA that is part of the TableDetailLoopIT1
*<br>Measurements used 
*<br>To specify physical measurements or counts, including dimensions, tolerances, variances, and weights  (See Figures Appendix for example of use of C001)
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMEAfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
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
/** extract data from loop PID that is part of TableDetailLoopIT1
*<br>Product/Item Description used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopPIDfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("PID");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("PID", i);
   if (loop == null) return;
      extractSegmentPIDfromTableDetailLoopIT1LoopPID(loop);
      extractSegmentMEAfromTableDetailLoopIT1LoopPID(loop);
    }
  }
/** extract data from segment PID that is part of the TableDetailLoopIT1LoopPID
*<br>Product/Item Description used 
*<br>To describe a product or process in coded or free-form format
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPIDfromTableDetailLoopIT1LoopPID(Loop inLoop)  throws OBOEException
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
/** extract data from segment MEA that is part of the TableDetailLoopIT1LoopPID
*<br>Measurements used 
*<br>To specify physical measurements or counts, including dimensions, tolerances, variances, and weights  (See Figures Appendix for example of use of C001)
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentMEAfromTableDetailLoopIT1LoopPID(Loop inLoop)  throws OBOEException
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

/** extract data from segment PWK that is part of the TableDetailLoopIT1
*<br>Paperwork used 
*<br>To identify the type or transmission or both of paperwork or supporting information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPWKfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
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
/** extract data from segment PKG that is part of the TableDetailLoopIT1
*<br>Marking, Packaging, Loading used 
*<br>To describe marking, packaging, loading, and unloading requirements
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPKGfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
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
/** extract data from segment PO4 that is part of the TableDetailLoopIT1
*<br>Item Physical Details used 
*<br>To specify the physical qualities, packaging, weights, and dimensions relating to the item
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPO4fromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
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
/** extract data from segment ITD that is part of the TableDetailLoopIT1
*<br>Terms of Sale/Deferred Terms of Sale used 
*<br>To specify terms of sale
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentITDfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
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
/** extract data from segment REF that is part of the TableDetailLoopIT1
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentREFfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
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
/** extract data from segment YNQ that is part of the TableDetailLoopIT1
*<br>Yes/No Question used 
*<br>To identify and answer yes and no questions, including the date, time, and comments further qualifying the condition
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentYNQfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
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
/** extract data from segment PER that is part of the TableDetailLoopIT1
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPERfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
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
/** extract data from segment SDQ that is part of the TableDetailLoopIT1
*<br>Destination Quantity used 
*<br>To specify destination and quantity detail
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSDQfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
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
/** extract data from segment DTM that is part of the TableDetailLoopIT1
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDTMfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
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
/** extract data from segment CAD that is part of the TableDetailLoopIT1
*<br>Carrier Detail used 
*<br>To specify transportation details for the transaction
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCADfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("CAD");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("CAD", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 91 Transportation Method/Type Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 206 Equipment Initial
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 207 Equipment Number
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 140 Standard Carrier Alpha Code
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 387 Routing
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 368 Shipment/Order Status Code
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 128 Reference Identification Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 127 Reference Identification
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 284 Service Level Code
       if (de != null)
         de.get();
    }
  }
/** extract data from segment L7 that is part of the TableDetailLoopIT1
*<br>Tariff Reference used 
*<br>To reference details of the tariff used to arrive at applicable rates or charge
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentL7fromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("L7");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("L7", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 213 Lading Line Item Number
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 168 Tariff Agency Code
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 171 Tariff Number
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 172 Tariff Section
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 169 Tariff Item Number
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 170 Tariff Item Part
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 59 Freight Class Code
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 173 Tariff Supplement Identifier
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 46 Ex Parte
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 373 Date
       if (de != null)
         de.get();
       de = segment.getDataElement(11);       // 119 Rate Basis Number
       if (de != null)
         de.get();
       de = segment.getDataElement(12);       // 227 Tariff Column
       if (de != null)
         de.get();
       de = segment.getDataElement(13);       // 294 Tariff Distance
       if (de != null)
         de.get();
       de = segment.getDataElement(14);       // 295 Distance Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(15);       // 19 City Name
       if (de != null)
         de.get();
       de = segment.getDataElement(16);       // 156 State or Province Code
       if (de != null)
         de.get();
    }
  }
/** extract data from segment SR that is part of the TableDetailLoopIT1
*<br>Requested Service Schedule used 
*<br>To identify requested service schedules
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSRfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("SR");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 350 Assigned Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 1430 Day Rotation
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 337 Time
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 337 Time
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 3 Free Form Message
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 212 Unit Price
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 380 Quantity
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 373 Date
  if (de != null)
    de.get();
  de = segment.getDataElement(10);  // 234 Product/Service ID
  if (de != null)
    de.get();
  de = segment.getDataElement(11);  // 234 Product/Service ID
  if (de != null)
    de.get();
  }
/** extract data from loop SAC that is part of TableDetailLoopIT1
*<br>Service, Promotion, Allowance, or Charge Information used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopSACfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("SAC");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("SAC", i);
   if (loop == null) return;
      extractSegmentSACfromTableDetailLoopIT1LoopSAC(loop);
      extractSegmentTXIfromTableDetailLoopIT1LoopSAC(loop);
    }
  }
/** extract data from segment SAC that is part of the TableDetailLoopIT1LoopSAC
*<br>Service, Promotion, Allowance, or Charge Information used 
*<br>To request or identify a service, promotion, allowance, or charge; to specify the amount or percentage for the service, promotion, allowance, or charge
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSACfromTableDetailLoopIT1LoopSAC(Loop inLoop)  throws OBOEException
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
/** extract data from segment TXI that is part of the TableDetailLoopIT1LoopSAC
*<br>Tax Information used 
*<br>To specify tax information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTXIfromTableDetailLoopIT1LoopSAC(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("TXI");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("TXI", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 963 Tax Type Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 782 Monetary Amount
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 954 Percent
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 955 Tax Jurisdiction Code Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 956 Tax Jurisdiction Code
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 441 Tax Exempt Code
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 662 Relationship Code
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 828 Dollar Basis For Percent
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 325 Tax Identification Number
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 350 Assigned Identification
       if (de != null)
         de.get();
    }
  }

/** extract data from loop SLN that is part of TableDetailLoopIT1
*<br>Subline Item Detail used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopSLNfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("SLN");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("SLN", i);
   if (loop == null) return;
      extractSegmentSLNfromTableDetailLoopIT1LoopSLN(loop);
      extractSegmentDTMfromTableDetailLoopIT1LoopSLN(loop);
      extractSegmentREFfromTableDetailLoopIT1LoopSLN(loop);
      extractSegmentPID_2fromTableDetailLoopIT1LoopSLN(loop);
      extractSegmentSAC_2fromTableDetailLoopIT1LoopSLN(loop);
      extractSegmentTC2fromTableDetailLoopIT1LoopSLN(loop);
      extractSegmentTXI_2fromTableDetailLoopIT1LoopSLN(loop);
    }
  }
/** extract data from segment SLN that is part of the TableDetailLoopIT1LoopSLN
*<br>Subline Item Detail used 
*<br>To specify product subline detail item data
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSLNfromTableDetailLoopIT1LoopSLN(Loop inLoop)  throws OBOEException
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
/** extract data from segment DTM that is part of the TableDetailLoopIT1LoopSLN
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDTMfromTableDetailLoopIT1LoopSLN(Loop inLoop)  throws OBOEException
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
/** extract data from segment REF that is part of the TableDetailLoopIT1LoopSLN
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentREFfromTableDetailLoopIT1LoopSLN(Loop inLoop)  throws OBOEException
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
/** extract data from segment PID that is part of the TableDetailLoopIT1LoopSLN
*<br>Product/Item Description used 
*<br>To describe a product or process in coded or free-form format
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPID_2fromTableDetailLoopIT1LoopSLN(Loop inLoop)  throws OBOEException
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
/** extract data from segment SAC that is part of the TableDetailLoopIT1LoopSLN
*<br>Service, Promotion, Allowance, or Charge Information used 
*<br>To request or identify a service, promotion, allowance, or charge; to specify the amount or percentage for the service, promotion, allowance, or charge
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSAC_2fromTableDetailLoopIT1LoopSLN(Loop inLoop)  throws OBOEException
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
/** extract data from segment TC2 that is part of the TableDetailLoopIT1LoopSLN
*<br>Commodity used 
*<br>To identify a commodity or a group of commodities or a tariff page commodity
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTC2fromTableDetailLoopIT1LoopSLN(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("TC2");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("TC2", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 23 Commodity Code Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 22 Commodity Code
       if (de != null)
         de.get();
    }
  }
/** extract data from segment TXI that is part of the TableDetailLoopIT1LoopSLN
*<br>Tax Information used 
*<br>To specify tax information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTXI_2fromTableDetailLoopIT1LoopSLN(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("TXI");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("TXI", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 963 Tax Type Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 782 Monetary Amount
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 954 Percent
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 955 Tax Jurisdiction Code Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 956 Tax Jurisdiction Code
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 441 Tax Exempt Code
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 662 Relationship Code
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 828 Dollar Basis For Percent
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 325 Tax Identification Number
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 350 Assigned Identification
       if (de != null)
         de.get();
    }
  }

/** extract data from loop N1 that is part of TableDetailLoopIT1
*<br>Name used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopN1fromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("N1");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("N1", i);
   if (loop == null) return;
      extractSegmentN1fromTableDetailLoopIT1LoopN1(loop);
      extractSegmentN2fromTableDetailLoopIT1LoopN1(loop);
      extractSegmentN3fromTableDetailLoopIT1LoopN1(loop);
      extractSegmentN4fromTableDetailLoopIT1LoopN1(loop);
      extractSegmentREF_2fromTableDetailLoopIT1LoopN1(loop);
      extractSegmentPERfromTableDetailLoopIT1LoopN1(loop);
      extractSegmentDMGfromTableDetailLoopIT1LoopN1(loop);
    }
  }
/** extract data from segment N1 that is part of the TableDetailLoopIT1LoopN1
*<br>Name used 
*<br>To identify a party by type of organization, name, and code
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentN1fromTableDetailLoopIT1LoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment N2 that is part of the TableDetailLoopIT1LoopN1
*<br>Additional Name Information used 
*<br>To specify additional names or those longer than 35 characters in length
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentN2fromTableDetailLoopIT1LoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment N3 that is part of the TableDetailLoopIT1LoopN1
*<br>Address Information used 
*<br>To specify the location of the named party
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentN3fromTableDetailLoopIT1LoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment N4 that is part of the TableDetailLoopIT1LoopN1
*<br>Geographic Location used 
*<br>To specify the geographic place of the named party
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentN4fromTableDetailLoopIT1LoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment REF that is part of the TableDetailLoopIT1LoopN1
*<br>Reference Identification used 
*<br>To specify identifying information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentREF_2fromTableDetailLoopIT1LoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment PER that is part of the TableDetailLoopIT1LoopN1
*<br>Administrative Communications Contact used 
*<br>To identify a person or office to whom administrative communications should be directed
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPERfromTableDetailLoopIT1LoopN1(Loop inLoop)  throws OBOEException
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
/** extract data from segment DMG that is part of the TableDetailLoopIT1LoopN1
*<br>Demographic Information used 
*<br>To supply demographic information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDMGfromTableDetailLoopIT1LoopN1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("DMG");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 1250 Date Time Period Format Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 1251 Date Time Period
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 1068 Gender Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 1067 Marital Status Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 1109 Race or Ethnicity Code
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 1066 Citizenship Status Code
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 26 Country Code
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 659 Basis of Verification Code
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 380 Quantity
  if (de != null)
    de.get();
  }

/** extract data from loop LM that is part of TableDetailLoopIT1
*<br>Code Source Information used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopLMfromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("LM");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("LM", i);
   if (loop == null) return;
      extractSegmentLMfromTableDetailLoopIT1LoopLM(loop);
      extractSegmentLQfromTableDetailLoopIT1LoopLM(loop);
    }
  }
/** extract data from segment LM that is part of the TableDetailLoopIT1LoopLM
*<br>Code Source Information used 
*<br>To transmit standard code list identification information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLMfromTableDetailLoopIT1LoopLM(Loop inLoop)  throws OBOEException
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
/** extract data from segment LQ that is part of the TableDetailLoopIT1LoopLM
*<br>Industry Code used 
*<br>Code to transmit standard industry codes
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentLQfromTableDetailLoopIT1LoopLM(Loop inLoop)  throws OBOEException
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

/** extract data from loop V1 that is part of TableDetailLoopIT1
*<br>Vessel Identification used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopV1fromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("V1");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("V1", i);
   if (loop == null) return;
      extractSegmentV1fromTableDetailLoopIT1LoopV1(loop);
      extractSegmentR4fromTableDetailLoopIT1LoopV1(loop);
      extractSegmentDTM_2fromTableDetailLoopIT1LoopV1(loop);
    }
  }
/** extract data from segment V1 that is part of the TableDetailLoopIT1LoopV1
*<br>Vessel Identification used 
*<br>To provide vessel details and voyage number
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentV1fromTableDetailLoopIT1LoopV1(Loop inLoop)  throws OBOEException
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
/** extract data from segment R4 that is part of the TableDetailLoopIT1LoopV1
*<br>Port or Terminal used 
*<br>Contractual or operational port or point relevant to the movement of the cargo
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentR4fromTableDetailLoopIT1LoopV1(Loop inLoop)  throws OBOEException
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
/** extract data from segment DTM that is part of the TableDetailLoopIT1LoopV1
*<br>Date/Time Reference used 
*<br>To specify pertinent dates and times
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentDTM_2fromTableDetailLoopIT1LoopV1(Loop inLoop)  throws OBOEException
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

/** extract data from loop FA1 that is part of TableDetailLoopIT1
*<br>Type of Financial Accounting Data used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopFA1fromTableDetailLoopIT1(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("FA1");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("FA1", i);
   if (loop == null) return;
      extractSegmentFA1fromTableDetailLoopIT1LoopFA1(loop);
      extractSegmentFA2fromTableDetailLoopIT1LoopFA1(loop);
    }
  }
/** extract data from segment FA1 that is part of the TableDetailLoopIT1LoopFA1
*<br>Type of Financial Accounting Data used 
*<br>To specify the organization controlling the content of the accounting citation, and the purpose associated with the accounting citation
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentFA1fromTableDetailLoopIT1LoopFA1(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("FA1");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 559 Agency Qualifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 1300 Service, Promotion, Allowance, or Charge Code
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 248 Allowance or Charge Indicator
  if (de != null)
    de.get();
  }
/** extract data from segment FA2 that is part of the TableDetailLoopIT1LoopFA1
*<br>Accounting Data used 
*<br>To specify the detailed accounting data
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentFA2fromTableDetailLoopIT1LoopFA1(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("FA2");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("FA2", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 1196 Breakdown Structure Detail Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 1195 Financial Information Code
       if (de != null)
         de.get();
    }
  }


/** extract data from segment TDS that is part of the TableSummary
*<br>Total Monetary Value Summary used 
*<br>To specify the total invoice discounts and amounts
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTDSfromTableSummary(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.getSegment("TDS");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 610 Amount
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 610 Amount
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 610 Amount
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 610 Amount
  if (de != null)
    de.get();
  }
/** extract data from segment TXI that is part of the TableSummary
*<br>Tax Information used 
*<br>To specify tax information
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTXIfromTableSummary(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("TXI");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("TXI", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 963 Tax Type Code
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 782 Monetary Amount
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 954 Percent
     if (de != null)
       de.get();
     de = segment.getDataElement(4);     // 955 Tax Jurisdiction Code Qualifier
     if (de != null)
       de.get();
     de = segment.getDataElement(5);     // 956 Tax Jurisdiction Code
     if (de != null)
       de.get();
     de = segment.getDataElement(6);     // 441 Tax Exempt Code
     if (de != null)
       de.get();
     de = segment.getDataElement(7);     // 662 Relationship Code
     if (de != null)
       de.get();
     de = segment.getDataElement(8);     // 828 Dollar Basis For Percent
     if (de != null)
       de.get();
     de = segment.getDataElement(9);     // 325 Tax Identification Number
     if (de != null)
       de.get();
     de = segment.getDataElement(10);     // 350 Assigned Identification
     if (de != null)
       de.get();
    }
  }
/** extract data from segment CAD that is part of the TableSummary
*<br>Carrier Detail used 
*<br>To specify transportation details for the transaction
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentCADfromTableSummary(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.getSegment("CAD");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 91 Transportation Method/Type Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 206 Equipment Initial
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 207 Equipment Number
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 140 Standard Carrier Alpha Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 387 Routing
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 368 Shipment/Order Status Code
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 128 Reference Identification Qualifier
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 127 Reference Identification
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 284 Service Level Code
  if (de != null)
    de.get();
  }
/** extract data from segment AMT that is part of the TableSummary
*<br>Monetary Amount used 
*<br>To indicate the total monetary amount
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentAMTfromTableSummary(Table inTable)
  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inTable.getCount("AMT");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
   {
     segment = inTable.getSegment("AMT", i);
     if (segment == null)
       return;
     DataElement de;
     de = segment.getDataElement(1);     // 522 Amount Qualifier Code
     if (de != null)
       de.get();
     de = segment.getDataElement(2);     // 782 Monetary Amount
     if (de != null)
       de.get();
     de = segment.getDataElement(3);     // 478 Credit/Debit Flag Code
     if (de != null)
       de.get();
    }
  }
/** extract data from loop SAC that is part of TableSummary
*<br>Service, Promotion, Allowance, or Charge Information used 
* @param inTable table containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopSACfromTableSummary(Table inTable)
  throws OBOEException
{
  Loop loop;
  int numberInVector = inTable.getCount("SAC");
  for (int i = 0; i <  numberInVector; i++)
   {
     loop = inTable.getLoop("SAC", i);
     if (loop == null) return;
     extractSegmentSACfromTableSummaryLoopSAC(loop);
     extractSegmentTXIfromTableSummaryLoopSAC(loop);
    }
  }
/** extract data from segment SAC that is part of the TableSummaryLoopSAC
*<br>Service, Promotion, Allowance, or Charge Information used 
*<br>To request or identify a service, promotion, allowance, or charge; to specify the amount or percentage for the service, promotion, allowance, or charge
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSACfromTableSummaryLoopSAC(Loop inLoop)  throws OBOEException
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
/** extract data from segment TXI that is part of the TableSummaryLoopSAC
*<br>Tax Information used 
*<br>To specify tax information
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentTXIfromTableSummaryLoopSAC(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("TXI");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("TXI", i);
       if (segment == null)
         return;
       DataElement de;
       de = segment.getDataElement(1);       // 963 Tax Type Code
       if (de != null)
         de.get();
       de = segment.getDataElement(2);       // 782 Monetary Amount
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 954 Percent
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 955 Tax Jurisdiction Code Qualifier
       if (de != null)
         de.get();
       de = segment.getDataElement(5);       // 956 Tax Jurisdiction Code
       if (de != null)
         de.get();
       de = segment.getDataElement(6);       // 441 Tax Exempt Code
       if (de != null)
         de.get();
       de = segment.getDataElement(7);       // 662 Relationship Code
       if (de != null)
         de.get();
       de = segment.getDataElement(8);       // 828 Dollar Basis For Percent
       if (de != null)
         de.get();
       de = segment.getDataElement(9);       // 325 Tax Identification Number
       if (de != null)
         de.get();
       de = segment.getDataElement(10);       // 350 Assigned Identification
       if (de != null)
         de.get();
    }
  }

/** extract data from loop ISS that is part of TableSummary
*<br>Invoice Shipment Summary used 
* @param inTable table containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopISSfromTableSummary(Table inTable)
  throws OBOEException
{
  Loop loop;
  int numberInVector = inTable.getCount("ISS");
  for (int i = 0; i <  numberInVector; i++)
   {
     loop = inTable.getLoop("ISS", i);
     if (loop == null) return;
     extractSegmentISSfromTableSummaryLoopISS(loop);
     extractSegmentPIDfromTableSummaryLoopISS(loop);
    }
  }
/** extract data from segment ISS that is part of the TableSummaryLoopISS
*<br>Invoice Shipment Summary used 
*<br>To specify summary details of total items shipped in terms of quantity, weight, and volume
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentISSfromTableSummaryLoopISS(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("ISS");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 382 Number of Units Shipped
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 355 Unit or Basis for Measurement Code
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
  de = segment.getDataElement(7);  // 380 Quantity
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 81 Weight
  if (de != null)
    de.get();
  }
/** extract data from segment PID that is part of the TableSummaryLoopISS
*<br>Product/Item Description used 
*<br>To describe a product or process in coded or free-form format
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentPIDfromTableSummaryLoopISS(Loop inLoop)  throws OBOEException
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
