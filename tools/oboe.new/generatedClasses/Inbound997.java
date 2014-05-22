import com.americancoders.edi.*;
import com.americancoders.edi.x12.*;
import java.io.*;

/** code template to parse
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
public class Inbound997
{
/** constructor for class Inbound997
*@param inFileName - String filename to be parsed
*@throws OBOEException - most likely transactionset not found
*@throws IOException - most likely input file not found
*/
public Inbound997(String inFileName)
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
                 extractSegmentAK1fromTableHeader(table);
                 extractLoopAK2fromTableHeader(table);
                 extractSegmentAK9fromTableHeader(table);
                 extractSegmentSEfromTableHeader(table);

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
/** extract data from segment AK1 that is part of the TableHeader
*<br>Functional Group Response Header used 
*<br>To start acknowledgment of a functional group
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentAK1fromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.getSegment("AK1");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 479 Functional Identifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 28 Group Control Number
  if (de != null)
    de.get();
  }
/** extract data from loop AK2 that is part of TableHeader
*<br>Transaction Set Response Header used 
* @param inTable table containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopAK2fromTableHeader(Table inTable)
  throws OBOEException
{
  Loop loop;
  int numberInVector = inTable.getCount("AK2");
  for (int i = 0; i <  numberInVector; i++)
   {
     loop = inTable.getLoop("AK2", i);
     if (loop == null) return;
     extractSegmentAK2fromTableHeaderLoopAK2(loop);
     extractLoopAK3fromTableHeaderLoopAK2(loop);
     extractSegmentAK5fromTableHeaderLoopAK2(loop);
    }
  }
/** extract data from segment AK2 that is part of the TableHeaderLoopAK2
*<br>Transaction Set Response Header used 
*<br>To start acknowledgment of a single transaction set
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentAK2fromTableHeaderLoopAK2(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("AK2");
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
/** extract data from loop AK3 that is part of TableHeaderLoopAK2
*<br>Data Segment Note used 
* @param inLoop loop containing this loop
*@throws OBOEException - most likely loop not found
*/
public void extractLoopAK3fromTableHeaderLoopAK2(Loop inLoop)  throws OBOEException
{
  Loop loop;
  int numberInVector = inLoop.getCount("AK3");
  for (int i = 0; i <  numberInVector; i++)
   {
   loop = inLoop.getLoop("AK3", i);
   if (loop == null) return;
      extractSegmentAK3fromTableHeaderLoopAK2LoopAK3(loop);
      extractSegmentAK4fromTableHeaderLoopAK2LoopAK3(loop);
    }
  }
/** extract data from segment AK3 that is part of the TableHeaderLoopAK2LoopAK3
*<br>Data Segment Note used 
*<br>To report errors in a data segment and identify the location of the data segment
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentAK3fromTableHeaderLoopAK2LoopAK3(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("AK3");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 721 Segment ID Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 719 Segment Position in Transaction Set
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 447 Loop Identifier Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 720 Segment Syntax Error Code
  if (de != null)
    de.get();
  }
/** extract data from segment AK4 that is part of the TableHeaderLoopAK2LoopAK3
*<br>Data Element Note used 
*<br>To report errors in a data element or composite data structure and identify the location of the data element
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentAK4fromTableHeaderLoopAK2LoopAK3(Loop inLoop)  throws OBOEException
{
  Segment segment;
  int numberOfSegmentsInVector = inLoop.getCount("AK4");
  for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inLoop.getSegment("AK4", i);
       if (segment == null)
         return;
       DataElement de;
         CompositeDE  composite = (CompositeDE) segment.getCompositeDE(1);  // C030 Position in Segment
  if (composite == null)
    return;
  de = composite.getDataElement(1);  // composite element 722 Element Position in Segment
  if (de != null)
    de.get();
  de = composite.getDataElement(2);  // composite element 1528 Component Data Element Position in Composite
  if (de != null)
    de.get();
       de = segment.getDataElement(2);       // 725 Data Element Reference Number
       if (de != null)
         de.get();
       de = segment.getDataElement(3);       // 723 Data Element Syntax Error Code
       if (de != null)
         de.get();
       de = segment.getDataElement(4);       // 724 Copy of Bad Data Element
       if (de != null)
         de.get();
    }
  }

/** extract data from segment AK5 that is part of the TableHeaderLoopAK2
*<br>Transaction Set Response Trailer used 
*<br>To acknowledge acceptance or rejection and report errors in a transaction set
* @param inLoop Loop containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentAK5fromTableHeaderLoopAK2(Loop inLoop)  throws OBOEException
{
  Segment segment = inLoop.getSegment("AK5");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 717 Transaction Set Acknowledgment Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 718 Transaction Set Syntax Error Code
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 718 Transaction Set Syntax Error Code
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 718 Transaction Set Syntax Error Code
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 718 Transaction Set Syntax Error Code
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 718 Transaction Set Syntax Error Code
  if (de != null)
    de.get();
  }

/** extract data from segment AK9 that is part of the TableHeader
*<br>Functional Group Response Trailer used 
*<br>To acknowledge acceptance or rejection of a functional group and report the number of included transaction sets from the original trailer, the accepted sets, and the received sets in this functional group
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentAK9fromTableHeader(Table inTable)
  throws OBOEException
{
  Segment segment = inTable.getSegment("AK9");
  if (segment == null)
    return;
  DataElement de;
  de = segment.getDataElement(1);  // 715 Functional Group Acknowledge Code
  if (de != null)
    de.get();
  de = segment.getDataElement(2);  // 97 Number of Transaction Sets Included
  if (de != null)
    de.get();
  de = segment.getDataElement(3);  // 123 Number of Received Transaction Sets
  if (de != null)
    de.get();
  de = segment.getDataElement(4);  // 2 Number of Accepted Transaction Sets
  if (de != null)
    de.get();
  de = segment.getDataElement(5);  // 716 Functional Group Syntax Error Code
  if (de != null)
    de.get();
  de = segment.getDataElement(6);  // 716 Functional Group Syntax Error Code
  if (de != null)
    de.get();
  de = segment.getDataElement(7);  // 716 Functional Group Syntax Error Code
  if (de != null)
    de.get();
  de = segment.getDataElement(8);  // 716 Functional Group Syntax Error Code
  if (de != null)
    de.get();
  de = segment.getDataElement(9);  // 716 Functional Group Syntax Error Code
  if (de != null)
    de.get();
  }
/** extract data from segment SE that is part of the TableHeader
*<br>Transaction Set Trailer used 
*<br>To indicate the end of the transaction set and provide the count of the transmitted segments (including the beginning (ST) and ending (SE) segments)
* @param inTable Table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractSegmentSEfromTableHeader(Table inTable)
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
