package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import com.americancoders.edi.x12.*;
import java.util.Vector;
import java.util.Date;
import java.math.*;
import java.rmi.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.apps.dataexchange.*;


/** code to parse 997 ElectronicTransactionData
 *<br>class 997 Functional Acknowledgment
 *<br>
 *@author Deping
 */
public class Inbound997 extends InboundSuper
{
    private ElectronicTransactionData ackTransaction = ElectronicTransactionData.createValue(); // store acknowledged document information within AK2 - AK5
    private Vector errors = new Vector();  // store a list of 997 acknowledge object
    private int ak2_5Count = 0;
    private boolean AK2NotSent = false;
    private Ack997DataVector mReqReqAck997s = new Ack997DataVector();
    private Edi997Data mEdi997D = null;
    private Edi997DataVector mEdi997DV = null;

    /** constructor for class Inbound997
     *@throws OBOEException - most likely segment or element not found
     */
    public Inbound997()
    {
    }

    // extract all segments included in ST - SE Segment
    public void extract()
    {
        mEdi997DV =  new Edi997DataVector();
        Table table = ts.getHeaderTable();
        if (table != null)
        {
            extractHeaderAK1(table);
            extractHeaderAK2(table);
            extractHeaderAK9(table);
            if (errors.size() > 0)
                mHandler.logError(getAckErrorReport());
        }
        mHandler.getTransactionDetails().addAll(mEdi997DV);
    }

    public void processTransaction()
    {
        mHandler.appendIntegrationRequests(getAck997s());
    }

    public Ack997DataVector getAck997s () {
        return mReqReqAck997s;
    }

    public void InsertReqAck997s (Ack997Data p997D) {
        mReqReqAck997s.add(p997D);
    }
    /** extract data from segment AK1 that is part of the Header
     *<br>Functional Group Response Header used
     *<br>To start acknowledgment of a functional group
     *param inTable table containing this segment
     *@throws OBOEException - most likely segment not found
     */
    public void extractHeaderAK1(Table inTable)
            throws OBOEException
    {
        Segment segment = null;
        try {
            segment = inTable.getSegment("AK1");
        }
        catch (OBOEException oe) { return; }
        DataElement de;
        de = segment.getDataElement(1);  // 479 Functional Identifier Code
        System.out.println("479: " + de.get());
        if (de == null) {
            return;
        }
        String groupType = de.get();
        String setdata = groupType+"*";
        ackTransaction.setGroupType(groupType);

        de = segment.getDataElement(2);  // 28 Group Control Number
        if (de == null){
            return;
        }
        int groupControlNumber = Integer.parseInt(de.get());
        ackTransaction.setGroupControlNumber(groupControlNumber);
        setdata += de.get();
        setReferenceData(setdata);
    }
    /** extract data from segment AK2 that is part of the Header
     *<br>Transaction Set Response Header used
     *<br>To start acknowledgment of a single transaction set
     *param inTable table containing this segment
     *@throws OBOEException - most likely segment not found
     */
    public void extractHeaderAK2(Table inTable)
            throws OBOEException
    {
        Loop loop;
        Segment segment;
        int numberOfSegmentsInVector;
        ak2_5Count++;

        try {
            numberOfSegmentsInVector = inTable.getCount("AK2");
        }catch (OBOEException oe) {
            AK2NotSent = true;
            return;
        }
        for (int i = 0; i <  numberOfSegmentsInVector; i++)
        {
            createEdi997();
            mEdi997DV.add(mEdi997D);

            loop = inTable.getLoop("AK2", i);
            segment = loop.getSegment("AK2");

            DataElement de;
            de = segment.getDataElement(1);       // 143 Transaction Set Identifier Code
            System.out.println("143: " + de.get());
            if (de == null)
                return;
            ackTransaction.setSetType(de.get());
            mEdi997D.setAckSetType(de.get());

            de = segment.getDataElement(2);       // 329 Transaction Set Control Number
            if (de == null)
                return;
            int setControlNumber = Integer.parseInt(de.get());
            ackTransaction.setSetControlNumber(setControlNumber);
            mEdi997D.setAckSetControlNumber(setControlNumber);
            setReferenceData(de.get());
            extractHeaderAK2AK5(loop);
        }
    }

    /** extract data from segment AK5 that is part of the HeaderAK2
     *<br>Transaction Set Response Trailer used
     *<br>To acknowledge acceptance or rejection and report errors in a transaction set
     *param inSegment segment containing this subsegment
     *@throws OBOEException - most likely segment not found
     */
    public void extractHeaderAK2AK5(Loop inLoop)
            throws OBOEException
    {
        Segment segment;
        String ackCode;
        Ack997Data ack997D = null;

        try { segment = inLoop.getSegment("AK5");}
        catch (OBOEException oe) { return; }
        DataElement de;

        de = segment.getDataElement(1);  // 717 Transaction Set Acknowledgment Code
        if (de == null)
            return;
        de.get();
        ackCode = de.get();
        setReferenceData(ackCode);
        String ac = ackCode;
        if(ac!=null && ac.length()>1) ac = ac.substring(0,1);
        mEdi997D.setAckStatus(ac);

        de = segment.getDataElement(2);  // 718 Transaction Set Syntax Error Code
        for (int i = 0; i < 5 && de != null; i++){
            String errorDesc = ((IDDE)de).describe(de.get());
            ack997D = CreateAck997DataObject("set", ackCode);
            ack997D.setAckError(errorDesc);
            errors.add(ack997D);
            de = segment.getDataElement(3 + i);  // 716 Functional Group Syntax Error Code
        }
        if (ack997D == null)
            ack997D = CreateAck997DataObject("set", ackCode);

        mReqReqAck997s.add(ack997D);
    }

    private void setReferenceData(String pVal) {
        ElectronicTransactionData transaction997D = mHandler.getElectronicTransactionData();
        if(pVal==null) pVal = "";
        String setdata = transaction997D.getSetData();
        if(setdata==null) setdata = "";
        if((setdata.length() + pVal.length() + 1) > 4000) return;
        if(setdata.length() >0) setdata += "*";
        setdata += pVal;
        transaction997D.setSetData(setdata);
        return;
    }

    /** extract data from segment AK9 that is part of the Header
     *<br>Functional Group Response Trailer used
     *<br>To acknowledge acceptance or rejection of a functional group and report the number of included transaction sets from the original trailer, the accepted sets, and the received sets in this functional group
     *param inTable table containing this segment
     *@throws OBOEException - most likely segment not found
     */
    public void extractHeaderAK9(Table inTable)
            throws OBOEException
    {
        Segment segment;
        Ack997Data ack997D = null;

        try { segment = inTable.getSegment("AK9");}
        catch (OBOEException oe) { return; }
        DataElement de;
        de = segment.getDataElement(1);  // 715 Functional Group Acknowledge Code
        String groupAckCode = de.get();
        if (AK2NotSent) {
            createEdi997();
            mEdi997D.setAckStatus(groupAckCode);
            mEdi997DV.add(mEdi997D);
        }
        de = segment.getDataElement(2);  // 97 Number of Transaction Sets Included
        if (de == null)
            return;

        if (ak2_5Count < Integer.parseInt(de.get())) {
            //send e-mail for warning possible error;
        }
        de = segment.getDataElement(3);  // 123 Number of Received Transaction Sets
        if (de == null)
            return;
        de.get();
        de = segment.getDataElement(4);  // 2 Number of Accepted Transaction Sets
        if (de == null)
            return;
        de.get();
        de = segment.getDataElement(5);  // 716 Functional Group Syntax Error Code
        for (int i = 0; i < 5 && de != null; i++){
            String errorDesc = ((IDDE)de).describe(de.get());
            ack997D = CreateAck997DataObject("group", groupAckCode);
            ack997D.setAckError(errorDesc);
            errors.add(ack997D);
            de = segment.getDataElement(6 + i);  // 716 Functional Group Syntax Error Code
        }

        if (AK2NotSent)
        {
            if (ack997D == null) {
                ack997D = CreateAck997DataObject("group", groupAckCode);
            }

            mReqReqAck997s.add(ack997D);
        }
    }

    Ack997Data CreateAck997DataObject(String level, String ackCode){
        Ack997Data p997 = Ack997Data.createValue();
        p997.setGroupReceiver(getTranslator().getProfile().getGroupReceiver());
        p997.setGroupSender(getTranslator().getProfile().getGroupSender());
        p997.setAckCode(ackCode);
        p997.setGroupType(ackTransaction.getGroupType());
        p997.setGroupControlNumber(ackTransaction.getGroupControlNumber());
        p997.setErrorLevel(level);
        if (level.equals("set")){
            p997.setSetType(ackTransaction.getSetType());
            p997.setSetControlNumber(ackTransaction.getSetControlNumber());
        }
        return p997;
    }
    String getAckErrorReport()
    {
        if (errors.size() == 0)
            return "";
        boolean errorFound = false;
        String str = "997 received has acknowledged error occured on following outbound transaction:\n";
        for (int i = 0; i < errors.size(); i++) {
            Ack997Data err997 = (Ack997Data) errors.get(i);
            str += "groupSender: " + err997.getGroupSender() + "\n";
            str += "groupReceiver: " + err997.getGroupReceiver() + "\n";
            str += "groupType: " + err997.getGroupType() + "\n";
            str += "groupControlNumber: " + err997.getGroupControlNumber() + "\n";
            str += "errorLevel: " + err997.getErrorLevel() + "\n";
            if (err997.getErrorLevel().equals("set"))
            {
                str += "setType: " + err997.getSetType() + "\n";
                str += "setControlNumber: " + err997.getSetControlNumber() + "\n";
            }
            str += "ackCode: " + err997.getAckCode() + " - ";
            if (err997.getAckCode().equals("E"))
                str += Edi.status[Edi.ACKE_MSG] + "\n";
            else if (err997.getAckCode().equals("R"))
                str += Edi.status[Edi.ACKR_MSG] + "\n";
            str += "ackError: " + err997.getAckError() + "\n\n";
        }
        return str;
    }
    private void createEdi997() {
        mEdi997D = Edi997Data.createValue();
        String groupType = ackTransaction.getGroupType();
        if(groupType!=null && groupType.length()>2) groupType = groupType.substring(0,2);
        mEdi997D.setAckGroupType(groupType);
        mEdi997D.setAckGroupControlNumber(ackTransaction.getGroupControlNumber());
    }

    public static void main(String arg[])throws Exception
    {
        String inFilename = "fa997_cleanwise.dat.20020129153410.decrypted";

        InboundTranslate translator = new InboundTranslate();
        try{
            translator.setUp();
            InboundTransaction inbound = translator.translateByFileName(inFilename);
            translator.processIntegrationRequests(inbound.getRequestsToProcess());

            System.out.println(inbound.getTranslationReport());
            System.out.println("************** End of Program ****************");
        }catch(Exception e){
            translator.logError("Inbound translation failed for inbound file - " + inFilename, e);
        }
    }
}
