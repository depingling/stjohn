/*
 * InboundEdiHandler.java
 *
 * Created on October 22, 2002, 9:33 AM
 */

package com.cleanwise.service.apps.edi;
import com.cleanwise.service.apps.dataexchange.*;
import com.cleanwise.service.api.util.IOUtilities;
import com.americancoders.edi.*;
import java.util.Vector;
import java.util.Iterator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collection;
import java.rmi.*;
import java.io.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
/**
 *
 * @author  bstevens
 */
public class InboundEdiHandler  extends Edi implements EDIDocumentHandler, InboundTransaction{

    private Vector groupErrorCodes = new Vector();
    private Vector setErrorCodes = new Vector();
    private InboundSuper parser = null;// point to current inbound parser
    private int setIncluded;
    private String icDate, icTime, gsDate, gsTime;
    private Outbound997 ob997 = null;
    private OutboundTranslate obTranslator;
    //private HashMap transToProc = new HashMap();
    private HashMap transErrors = new HashMap();
    private boolean suspend997Fl = true;

    //public void setTransToProc(HashMap pVal){
    //    transToProc = pVal;
    //}

    //public HashMap getTransToProc(){
    //    return transToProc;
    //}

    public void setTransErrors(HashMap pVal){
        transErrors = pVal;
    }

    public HashMap getTransErrors(){
        return transErrors;
    }

    public void setTranslator(com.cleanwise.service.apps.dataexchange.InboundTranslate pTrans){
        translator = pTrans;
    }

    OutboundTranslate getOutTranslator(){
      return obTranslator;
    }

    protected InboundTranslate getInboundTranslator(){
        return (InboundTranslate) translator;
    }

    /** Creates a new instance of InboundEdiHandler */
    public InboundEdiHandler() {
        direction = INBOUND;
    }

    /** do nothing when an envelope is ended
     */
    public void endEnvelope(Envelope inEnv){
        validate(inEnv);
    }
    /** do nothing when an fg is ended
     */
    public void endFunctionalGroup(FunctionalGroup inFG){
    }

    /** create a Valid XML document for each ts found
     */
    public void endTransactionSet(TransactionSet inTS){;}

    /** do nothing when an seg is ended
     * <br>Note that these segments are only envelope and fg segments NOT SEGMENTS inside of Transaction Sets
     */
    public void endSegment(Segment inSeg){
    }

    /** do nothing when an envelope is started */
    public void startEnvelope(Envelope inEnv) {
        env = (CwX12Envelope) inEnv;
    }



    /** called when an FunctionalGroup object is created
     * @param inFG FunctionalGroup found
     */

    public void startFunctionalGroup(FunctionalGroup inFG) {
        fg = inFG;
        env.addFunctionalGroup(inFG);
    }

    public void startParsing(java.io.Reader inReader)
    throws OBOEException {
        ;
    }

    /** called when an Segment object is created
     * <br>only called for segments at the Envelope and functionalGroup level
     * does not get called for segments within TransactionSet
     * @param inSeg  Segment found
     */
    public void startSegment(Segment inSeg) {

        if (inSeg.getID().compareTo(CwX12Envelope.idInterchangeHeader) == 0)
            env.addSegment(inSeg);
        else
            if (inSeg.getID().compareTo(CwX12Envelope.idInterchangeTrailer) == 0)
                env.addSegment(inSeg);
            else
                if (inSeg.getID().compareTo(CwX12Envelope.idGradeofServiceRequest) == 0)
                    env.addSegment(inSeg);
                else
                    if (inSeg.getID().compareTo(CwX12Envelope.idDeferredDeliveryRequest) == 0)
                        env.addSegment(inSeg);
                    else
                        if (inSeg.getID().compareTo(CwX12Envelope.idInterchangeAcknowledgment) == 0)
                            env.addSegment(inSeg);
                        else
                            if (inSeg.getID().compareTo(CwX12FunctionalGroup.idHeader) == 0)
                                fg.addSegment(inSeg);
                            else
                                if (inSeg.getID().compareTo(CwX12FunctionalGroup.idTrailer) == 0)
                                    fg.addSegment(inSeg);

    }



    /** called when an TransactionSet object is created
     * @param inTS TransactionSet found
     */
    public void startTransactionSet(TransactionSet inTS) {
        ts = inTS;
        fg.addTransactionSet(inTS);

    }

    java.util.Hashtable incomingDataParserClasses = new
	java.util.Hashtable();

    private TradingPartnerDescView getTradingPartnerDescView
    (int pProfileId, String pTsType ) 
    throws RemoteException {
    	if ( null == incomingDataParserClasses.get(pTsType) ) {
    		log(" class not cached, lookup for pTsType=" + pTsType);
    		incomingDataParserClasses.put(pTsType, ((InboundTranslate)translator).getTradingPartnerDescView(pProfileId,pTsType, "IN") 
    		);
    	}
    	return (TradingPartnerDescView)
    	incomingDataParserClasses.get(pTsType);
    }

    /**
     * do validation
     */
    public void validate(Envelope inEnv) {

        TradingProfileData ackProfile = null; // profile for writing 997 acknowledge
        boolean send997Flag;  // true if current set need to be acknowledged
        suspend997Fl = false;

        try{
            profile = TradingProfileData.createValue();
            extractHeaderISA(env.getInterchange_Header());
            extractHeaderIEA(env.getInterchange_Trailer());
            interchangeD = createInterchangeObject();

            for (int fgPosition = 0; fgPosition < env.getFunctionalGroupCount(); fgPosition++) {

                groupErrorCodes.clear();
                fg = env.getFunctionalGroup(fgPosition);
		        setGroupType(getGroupType(fg));

                extractHeaderGS(fg.getHeader());
                extractHeaderGE(fg.getTrailer());

                ackProfile = ((InboundTranslate)translator).getTradingProfile(obTranslator, this.getProfile(), "OUT", RefCodeNames.EDI_TYPE_CD.T997);

                if (ackProfile != null){
                    if(partner!=null){
                        if(RefCodeNames.TRADING_TYPE_CD.FAX.equals(partner.getTradingTypeCd())){
                            log("Error - Trading Partner Type Cd is FAX");
                            setFail(true);
                            return;
                        }
                    }else{
                        partner = translator.getPartnerSvc().getTradingPartner(ackProfile.getTradingPartnerId());
                        if(!RefCodeNames.TRADING_TYPE_CD.EDI.equals(partner.getTradingTypeCd())){
                            log("Error - Trading Partner Type Cd is not equal to EDI");
                            setFail(true);
                            return;
                        }
                    }
                }

                if (ackProfile != null){
                    interchangeD.setTradingProfileId(ackProfile.getTradingProfileId());
                    profile.setTradingProfileId(ackProfile.getTradingProfileId());
                }

                send997Flag = (ackProfile != null && !getGroupType().equals("FA"));
                if (send997Flag) {
                    if (getOutTranslator() == null) {
                        obTranslator = new OutboundTranslate();
                        getOutTranslator().setUp(getErpNum(),0, "997", (InboundTranslate)translator);
                        obTranslator.setProfile(ackProfile);  //added for new abstraction
                        getOutTranslator().openOutputStream();
                        ob997 = new Outbound997();
                        ob997.setParameter(obTranslator);
                        //b997.setProfile(ackProfile); //added for new abstraction
                    }

                    if (fgPosition == 0) {
                        ob997.buildDocumentHeader(ackProfile);
                    } else {
                        String ackGroupSender = ackProfile.getGroupSender();
                        String ackGroupReceiver = ackProfile.getGroupReceiver();
                        if(!ackGroupReceiver.equals(ob997.profile.getGroupReceiver())|| !ackGroupSender.equals(ob997.profile.getGroupSender())) {
                          ob997.profile=ackProfile;
                          ob997.translator.setProfile(ackProfile);
                          FunctionalGroup fg997 = ob997.env.createFunctionalGroup();
                          Segment fgHeader = fg997.getHeader();
                          ob997.buildHeaderGS(fgHeader);
                          ob997.fg = fg997;

                      }
                    }

                    //moved out
                    ob997.buildTransactionHeader();
                    log(" ob997.buildHeaderAK1, getGroupType()=" +  getGroupType());
                    ob997.buildHeaderAK1(getGroupType(), profile.getGroupControlNum());
                }

                int tsCount = fg.getTransactionSetCount();
                for (int tsPosition = 0; tsPosition < tsCount; tsPosition++) {
                    setErrorCodes.clear();
                    ts = fg.getTransactionSet(tsPosition);
                    transactionD = createTransactionObject();
                    transactionD.setSetStatus(RECA_MSG);// initial to receive ok
                    transactionD.setSetType(ts.getID());
                    setSetType(ts.getID());

                    if (ackProfile == null) {
                        parser = new InboundSuper();
                        parser.setParameter(this, ts);
                        parser.getErrorMsgs().add("Trading Partner profile is not set up for Group Sender Id = "
                        + profile.getGroupReceiver() + " and Group Receiver Id = "
                        + profile.getGroupSender());
                        transactionD.setSetStatus(NORT_MSG);       // marked as no route
                    }
                    else{
                        // Get the parser for the current transaction.
                        String currTsType =  ts.getID();
                        TradingPartnerDescView tpDescViewD = getTradingPartnerDescView(ackProfile.getTradingProfileId(), currTsType);
                        TradingProfileConfigData profileConfigD = tpDescViewD.getTradingProfileConfigData();
                        getInboundTranslator().setTradingPartnerDescView(tpDescViewD);

                        if (profileConfigD == null){
                            parser = new InboundSuper();
                            parser.setParameter(this, ts);
                            parser.getErrorMsgs().add("Error - Transaction Set " + ts.getID() + " Not Supported");
                            transactionD.setSetStatus(RECN_MSG);// set not support
                            setErrorCodes.add("1");
                        }
                        else{
                            Class c = Class.forName(profileConfigD.getClassname());
                            parser = (InboundSuper) c.newInstance();
                            parser.setParameter(this, ts);
                        }
                    }
                    parser.setValid(true);

                    int errorCount = 0;
                    errorCount = extractHeaderST(ts.getHeaderTable());
                    errorCount += extractHeaderSE(ts);
                    if (errorCount > 0)  // error in ST or SE segment
                    {
                        parser.setValid(false);
                        suspend997Fl = true;
                        transactionD.setSetStatus(RECR_MSG);
                    }

                    if (transactionD.getSetStatus() == RECA_MSG) {
                        translator.addLogMessage("InboundEdiHandler HHHHHHHHHHHHHHHHHHHHHHHHHHHHH =============================");
                        translator.addLogMessage("InboundEdiHandler HHHHHHHHHHHHHHHHHHHHHHHHHHHHH processing ts #: "+tsPosition);
                        translator.addLogMessage("InboundEdiHandler HHHHHHHHHHHHHHHHHHHHHHHHHHHHH transaction type: "+ts.getID());
                        DocumentErrors errs = new DocumentErrors();
                        ts.validate(errs);
                        if (errs.getErrorCount() > 0) {
                            parser.setValid(false);
                            suspend997Fl = true;
                            String notes = "";
                            for (int i = 0; i < errs.getErrorCount(); i++) {
                              IContainedObject isc = errs.getContainer(i);
                                if(isc instanceof Segment) {
                                    Segment seg = (Segment) isc;
                                    TemplateSegment template = seg.getTemplate();
                                    if(template!=null) {
                                        String segmentCode = template.getID();
                                        HashMap tErrHM = getTransErrors();
                                        Integer tsPositionI = new Integer(tsPosition);
                                        LinkedList errL = (LinkedList) tErrHM.get(tsPositionI);
                                        if(errL == null) {
                                            errL = new LinkedList();
                                            tErrHM.put(tsPositionI,errL);
                                        }
                                        errL.add(segmentCode);
                                        translator.addLogMessage("InboundEdiHandler " +
                                            "HHHHHHHHHHHHHHHHHHHHHHHHHHHHH error segment: "+
                                            tsPositionI +" -> " + segmentCode);

                                    }
                                }
                                notes += errs.getError()[i] + "\r\n";
                            }
                            transactionD.setSetStatus(RECR_MSG);
                            parser.getErrorMsgs().add(notes);
                            setErrorCodes.add("5");
                            errorCount++;
                        } else {
                            parser.extract();

                            if (!parser.getValid()) {
                                suspend997Fl = true;
                                transactionD.setSetStatus(RECR_MSG);
                                setErrorCodes.add("5");
                                Vector errV = parser.getErrorMsgs();
                                if(errV!=null) {
                                    for(Iterator iter = errV.iterator(); iter.hasNext();) {
                                        String errMess = (String) iter.next();
                                        translator.addLogMessage("InboundEdiHandler " +
                                            "HHHHHHHHHHHHHHHHHHHHHHHHHHHHH error: "+errMess);
                                    }
                                }
                                errorCount++;
                            } else {
                                parser.processTransaction();
                                if (!parser.getValid()) {
                                    suspend997Fl = true;
                                    transactionD.setSetStatus(RECR_MSG);
                                    setErrorCodes.add("5");
                                    Vector errV = parser.getErrorMsgs();
                                    if(errV!=null) {
                                        for(Iterator iter = errV.iterator(); iter.hasNext();) {
                                            String errMess = (String) iter.next();
                                            translator.addLogMessage("InboundEdiHandler " +
                                                "HHHHHHHHHHHHHHHHHHHHHHHHHHHHH error: "+errMess);
                                        }
                                    }
                                    errorCount++;
                                }
                            }
                        }
                        //=====================
                        if(parser.getValid()) {
                           translator.addLogMessage("InboundEdiHandler HHHHHHHHHHHHHHHHHHHHHHHHHHHHH trasaction processed");
                        } else {
                           translator.addLogMessage("InboundEdiHandler HHHHHHHHHHHHHHHHHHHHHHHHHHHHH trasaction processed with error(s)");
                        }
                    }
                    transactionD.setException(parser.getFormatedErrorMsgs());
                    if (transactionD.getException() != null && !transactionD.getException().equals("")){
                        logInfo(transactionD.getException());
                        transactionD.setSetData(ts.getFormattedText(env.X12_FORMAT));
                    }

                    if (send997Flag) {
                        ob997.buildHeaderAK2(transactionD.getSetType(), transactionD.getSetControlNumber());
                        String errorCode;
                        if (transactionD.getSetStatus() == RECA_MSG)
                            errorCode = "A";
                        else if (transactionD.getSetStatus() == RECE_MSG)
                            errorCode = "E";
                        else
                            errorCode = "R";

                        ob997.buildHeaderAK2AK5(errorCode, setErrorCodes);
                    }
                }
                if (send997Flag) {
                    ob997.buildHeaderAK9("", setIncluded, groupErrorCodes);
                    ob997.buildTransactionTrailer();
                }
            }

            if (ob997 != null) {
                ob997.buildDocumentTrailer();
            }

        }catch(Exception e){
            setFail(true);
            logError(e);
        }
    }



    public void extractHeaderISA(Segment isa)
  {
    profile.setInterchangeSender(isa.getDataElement(6).get());//int sender info 15-15
    profile.setInterchangeReceiver(isa.getDataElement(8).get());//int id 15-15
    icDate = isa.getDataElement(9).get();// int dt 6-6
    icTime = isa.getDataElement(10).get();//int tm 4-4
    profile.setInterchangeControlNum(Integer.parseInt(isa.getDataElement(13).get()));// int cntrl number 9-9
    profile.setAcknowledgmentRequested(isa.getDataElement(14).get());//ack req 1-1
    profile.setTestIndicator(isa.getDataElement(15).get());//test ind 1-1
  }
  public void extractHeaderGS(Segment gs)
  {
    translator.setGroupType(gs.getDataElement(1).get());
    profile.setGroupSender(gs.getDataElement(2).get());
    profile.setGroupReceiver(gs.getDataElement(3).get());
    gsDate = gs.getDataElement(4).get();
    gsTime = gs.getDataElement(5).get();
    profile.setGroupControlNum(Integer.parseInt(gs.getDataElement(6).get()));
  }
  public int extractHeaderST(Table table)
  {
    int errorCount = 0;
    Segment st = table.getSegment("ST");
    try{
      transactionD.setSetControlNumber(Integer.parseInt(st.getDataElement(2).get()));
    }catch(Exception e1){
      parser.getErrorMsgs().add("Error - Missing or Invalid Transaction Set Control Number");
      setErrorCodes.add("7");
      errorCount++;
    }
    return errorCount;
  }

  public void extractHeaderIEA(Segment iea)
  {
    //int number of function group included in current interchange
    int count = Integer.parseInt(iea.getDataElement(1).get());
    if (count != env.getFunctionalGroupCount())
      logInfo("Error - Envelope count = " + count + ", actual count = " + env.getFunctionalGroupCount() + " (Envelope # " + profile.getInterchangeControlNum() + ")");

    //int interchange control number
    int num = Integer.parseInt(iea.getDataElement(2).get());
    if (num != profile.getInterchangeControlNum())
      logInfo("Error - ISA Control number = " + profile.getInterchangeControlNum() + "   IEA Control number = " + num);
  }

  public void extractHeaderGE(Segment ge)
  {
    //int number of transaction set included in current function group
    setIncluded = Integer.parseInt(ge.getDataElement(1).get());
    if (setIncluded != fg.getTransactionSetCount())
    {
      logInfo("Error - GE count = " + setIncluded + ", actual count = " + fg.getTransactionSetCount() + " (GS # " + profile.getGroupControlNum() + ")");
      groupErrorCodes.add("5");
    }

    //int functional group control number
    int num = Integer.parseInt(ge.getDataElement(2).get());
    if (num != profile.getGroupControlNum())
    {
      logInfo("Error - GE Control number = " + num + "   GS Control number = " + profile.getGroupControlNum());
      groupErrorCodes.add("4");
    }
  }

  public int extractHeaderSE(TransactionSet ts)
  {
    int errorCount = 0;
    Segment se = null;
    Table table;

    try{
      table = ts.getSummaryTable();
      if (table != null){
        se = table.getSegment("SE");
      }
      else {
        try{
          table = ts.getHeaderTable();
          se = table.getSegment("SE");
        }catch(OBOEException e1) {
          se = null;
        }
      }
    }catch(OBOEException e) {
      se = null;
    }

    if (se == null)
    {
      parser.getErrorMsgs().add("Error - Transaction Set Trailer Missing (ST # " + transactionD.getSetControlNumber() + ")");
      setErrorCodes.add("2");
      errorCount++;
      return errorCount;
    }
    //int number of segment included in current transaction set
    int count = Integer.parseInt(se.getDataElement(1).get());
    if (count != ts.getSegmentCount())
    {
      errorCount++;
      parser.getErrorMsgs().add("Error - SE count = " + count + ", actual count = " + ts.getSegmentCount() + " (ST # " + transactionD.getSetControlNumber() + ")");
      setErrorCodes.add("4");
    }

    //int transaction set control number
    try{
      int num = Integer.parseInt(se.getDataElement(2).get());
      if (num != transactionD.getSetControlNumber())
      {
        parser.getErrorMsgs().add("Error - SE Control number = " + num + "   ST Control number = " + transactionD.getSetControlNumber());
        setErrorCodes.add("3");
        errorCount++;
      }
    }catch(Exception e1){
      parser.getErrorMsgs().add("Error - Missing or Invalid Transaction Set Control Number (7)");
      setErrorCodes.add("7");
      errorCount++;
    }
    return errorCount;
  }



  /**
    * Input ediData contain multiple envelopes, but X12DocumentParser only take
    * one envelope data. This function will seperate all the envelope and store
    * them in the vector that returned
    */
  private Vector unbundleInterchanges(String ediData)
  {
    Vector ICDatas = new Vector(); // a list of seperated envelope data

    String currIC;  // current envelope data
    int fromIndex = 0, nextIndex = 0; // beginning index of current and next envelope data

    fromIndex = getNextIndexForISA(ediData, 0);
    while (fromIndex >= 0)
    {
        nextIndex = getNextIndexForISA(ediData, fromIndex + 3);
        if (nextIndex == -1)
            currIC = ediData.substring(fromIndex);
        else
            currIC = ediData.substring(fromIndex, nextIndex);
        fromIndex = nextIndex;
        ICDatas.add(currIC);
    }

    return ICDatas;
  }
  // get index position on ediDate of next ISA segment
  private int getNextIndexForISA(String ediData, int fromIndex)
  {
    int nextIndex = ediData.indexOf("ISA", fromIndex);
    // ISA segment contain 106 characters
    if (nextIndex >= 0 && ediData.length() > nextIndex + 106)
    {
        // match element seperator
        if (ediData.charAt(nextIndex + 3) != ediData.charAt(nextIndex + 103))
            return (getNextIndexForISA(ediData, nextIndex + 3));
        // check to see if it is test or production flag
        if (ediData.charAt(nextIndex + 102) != 'P' && ediData.charAt(nextIndex + 102) != 'T')
            return (getNextIndexForISA(ediData, nextIndex + 3));
        return nextIndex;
    }
    return -1;
  }


  /**
  *@param ediData - String edi data parsed
  */
  public void translate(String ediData) throws IOException
  {
    setFail(false);
    getLogStrings().clear();
    getInterchanges().clear();

    if (ediData == null || ediData.equals(""))
    {
      logError("No input data");
      setFail(true);
      return;
    }

    CwX12DocumentParser parser = new CwX12DocumentParser(); // create a parser object
    parser.registerHandler(this); // register with the parser
    Vector ICDatas = unbundleInterchanges(ediData);

    try{
      if(ICDatas==null || ICDatas.size() == 0){
          throw new IllegalStateException("Document has data, but there are interchanges (malformed EDI)!");
      }
      for (int i = 0; i < ICDatas.size() && isFail() == false; i++)
      {
	  log( "ICDatas, i=" + i + " \n" + (String)ICDatas.get(i) );
        parser.parseDocument((String)ICDatas.get(i));
      }
    }
    catch (OBOEException e1)
    {
      //setFail(true);
      DocumentErrors errors = parser.getDocumentErrors();
      String notes = "";
      boolean fatalErrorFl = false;

      for (int i = 0; i < errors.getErrorCount(); i++)
      {
          boolean fl = true;
//          ISegmentContainer isc = errors.getContainer(i);
          IContainedObject isc = errors.getContainer(i);
          if(isc instanceof Segment) {
              Segment seg = (Segment) isc;
              String segmentCode = seg.getID();
              HashMap knownErrorsHM = getTransErrors();
              Collection col = knownErrorsHM.values();
              MMM: for(Iterator iter = col.iterator(); iter.hasNext();) {
                  LinkedList errSegCodeLL =  (LinkedList) iter.next();
                  for(Iterator iter1 = errSegCodeLL.iterator(); iter1.hasNext();) {
                      String errCode = (String) iter1.next();
                      if(errCode.equals(segmentCode)) {
                          fl = false;
                          break MMM;
                      }
                  }
              }
          }
          if(fl) fatalErrorFl = fl;
          notes = notes + errors.getError()[i] + "\r\n";
      }
      logError(notes, e1);
      if(fatalErrorFl) setFail(true);
System.out.println("InboundEdiHandler EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE fatalErrorFl: "+fatalErrorFl);
    }
    catch (Exception e2)
    {
      logError(e2);
      setFail(true);
    }
    finally{
        if (getOutTranslator() != null) {
          getOutTranslator().closeOutputStream();
        }
        translator.closeLogFile();
        if ((isFail() || suspend997Fl)
             && getOutTranslator()!=null
             && Utility.isSet(getOutTranslator().getOutputFileName()) ) {
            File ackFile = new File(getOutTranslator().getOutputFileName());
            if(ob997!=null && !ob997.isFail() && translator.getLogDirectory()!=null) {
               String logDir = translator.getLogDirectory();
               if(ackFile.exists() && ackFile.isFile()) {
                 IOUtilities.moveFile(ackFile, new File(logDir), null);
               }
            } else {
               if(ackFile.exists() && ackFile.isFile()) {
                   ackFile.delete();
               }
            }
          } else {
            translator.deleteLogFile();
          }
        }

    }


  public TradingProfileData getProfile(){
    return profile;
  }

  public String toString(){
      if(parser != null){
        return this.getClass().getName() + ":parser:" + parser.getClass().getName();
      }else{
        return this.getClass().getName();
      }
  }
}
