package com.cleanwise.service.apps.edi;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.apps.dataexchange.InboundSuper;
import com.americancoders.edi.*;

/** Super class for parsing any transaction set
*<br>
*@author Deping
*/
public class InboundEdiSuper extends InboundSuper
{
    
  protected TransactionSet ts;      // current transaction set
  protected InboundEdiHandler ediHandler;
  
  public void setParameter(InboundEdiHandler handler, TransactionSet set)
  {
    ediHandler = handler;
    super.mHandler = handler;
    ts = set;
  }

  public void extract() throws Exception {
  }
  
  public boolean isSet(DataElement de){
	  return (de != null && !de.get().equals(""));
  }
  
  public boolean isQualiferExistsInRefSegment(String qualifier, Table inTable){
	  Segment segment;
	  try {
		  int numberOfSegmentsInVector = inTable.getSegmentCount("REF");
		  for (int i = 0; i <  numberOfSegmentsInVector; i++)
		  {
			  segment = inTable.getSegment("REF", i);
			  DataElement de;
			  de = segment.getDataElement(1);     // 128 Reference Identification Qualifier
			  if (Utility.isSet(de.get())) {
				  String identQualifier =  de.get();
				  if (qualifier.equals(identQualifier)) {
					  return true;
				  }
			  }
		  }
	  } catch (OBOEException oe) { ; }
	  return false;	  
  }
  
  public boolean isQualiferExistsInRefSegment(String qualifier, Loop inTable){
	  Segment segment;
	  try {
		  int numberOfSegmentsInVector = inTable.getSegmentCount("REF");
		  for (int i = 0; i <  numberOfSegmentsInVector; i++)
		  {
			  segment = inTable.getSegment("REF", i);
			  DataElement de;
			  de = segment.getDataElement(1);     // 128 Reference Identification Qualifier
			  if (Utility.isSet(de.get())) {
				  String identQualifier =  de.get();
				  if (qualifier.equals(identQualifier)) {
					  return true;
				  }
			  }
		  }
	  } catch (OBOEException oe) { ; }
	  return false;	  
  }
  
}

