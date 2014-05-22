package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;

import java.util.ArrayList;

import org.apache.log4j.Logger;


/**
 * code template to parse
 * <br>class 101 Name and Address Lists
 * <br>
 * This Draft Standard for Trial Use contains the format and establishes
 * the data contents of the Name and Address Lists Transaction Set
 * (101) for use within the context of an Electronic Data Interchange
 * (EDI) environment. This transaction can be used to facilitate
 * the rental or exchange of name and address mailing lists between
 * direct marketing companies.
 *
 * @author Evgeniy Vlasov
 */
public class Inbound101Super extends InboundEdiSuper {
	protected Logger log = Logger.getLogger(this.getClass());
    public static final String IN202_AC = "AC";//Value identifier of Company Number and Account Number in IN202 segment
    public static final String IN202_NM = "NM";//Value identifier of Account Name in IN202 segment
    public static final String IN202_A1 = "A1";//Value identifier of First Address Line in IN202 segment
    public static final String IN202_A2 = "A2";//Value identifier of Second Address Line in IN202 segment
    public static final String IN202_CT = "CT";//Value identifier of City in IN202 segment
    public static final String IN202_ST = "ST";//Value identifier of State in IN202 segment
    public static final String IN202_ZI = "ZI";//Value identifier of Zip in IN202 segment
    public static final String IN202_AR = "AR";//Value identifier of Accounts Receivable Account in IN202 segment


    private Inbound101Data reciver = new Inbound101Data();

    public void extract() {
        Table table = ts.getHeaderTable();
        if (table != null) {
            BGNSegment bgnSegment = extractSegmentBGN(table);
            DTMLoopVector dtmLoops = extractLoopDTM(table);

            reciver.setBeginningSegment(bgnSegment);
            reciver.setDtmLoops(dtmLoops);
        }
    }


    /**
     * extract data from segment BGN that is part of the TableHeader
     * <br>Beginning Segment used
     * <br>To indicate the beginning of a transaction set
     *
     * @param inTable Table containing this segment
     * @return extracted segment
     * @throws OBOEException - most likely segment not found
     */
    public BGNSegment extractSegmentBGN(Table inTable) throws OBOEException {
        log.info("extractSegmentBGN => begin");
        BGNSegment receiver = new BGNSegment();

        Segment segment = inTable.getSegment("BGN");
        if (segment == null) {
            return null;
        }

        DataElement de;
        de = segment.getDataElement(1);  // 353 Transaction Set Purpose Code
        if (de != null) {
            receiver.setTransactionSetPurposeCode(de.get());
        }

        de = segment.getDataElement(2);  // 127 Reference Identification
        if (de != null) {
            receiver.setReferenceIdentificationSeq2(de.get());
        }

        de = segment.getDataElement(3);  // 373 Date
        if (de != null) {
            receiver.setDate(de.get());
        }

        de = segment.getDataElement(4);  // 337 Time
        if (de != null) {
            receiver.setTime(de.get());
        }

        de = segment.getDataElement(5);  // 623 Time Code
        if (de != null) {
            receiver.setTimeCode(de.get());
        }

        de = segment.getDataElement(6);  // 127 Reference Identification
        if (de != null) {
            receiver.setReferenceIdentificationSeq6(de.get());
        }

        de = segment.getDataElement(7);  // 640 Transaction Type Code
        if (de != null) {
            receiver.setTransactionTypeCode(de.get());
        }

        de = segment.getDataElement(8);  // 306 Action Code
        if (de != null) {
            receiver.setActionCode(de.get());
        }

        de = segment.getDataElement(9);  // 786 Security Level Code
        if (de != null) {
            receiver.setSecurityLevelCode(de.get());
        }
        log.info("extractSegmentBGN => end");
        return receiver;
    }

    /**
     * extract data from loop DTM that is part of TableHeader
     * <br>Date/Time Reference used
     *
     * @param inTable table containing this loop
     * @return extracted loop
     * @throws OBOEException - most likely loop not found
     */
    public DTMLoopVector extractLoopDTM(Table inTable) throws OBOEException {
        log.info("extractLoopDTM => begin");
        Loop loop;
        DTMLoopVector rVector = new DTMLoopVector();

        int numberInVector = inTable.getCount("DTM");
        for (int i = 0; i < numberInVector; i++) {

            loop = inTable.getLoop("DTM", i);
            if (loop == null) {
                return null;
            }

            DTMLoop reciver = new DTMLoop();

            DTMSegment dtmSegment  = extractSegmentDTM(loop);
            N1Segment n1Seqment    = extractSegmentN1(loop);
            LXLoopVector lxRVector = extractLoopLX(loop);

            reciver.setDateTimeReference(dtmSegment);
            reciver.setLxLoops(lxRVector);
            reciver.setN1Segment(n1Seqment);

            rVector.add(reciver);
        }
        log.info("extractLoopDTM => end");
        return rVector;

    }

    /**
     * extract data from segment DTM that is part of the TableHeaderLoopDTM
     * <br>Date/Time Reference used
     * <br>To specify pertinent dates and times
     *
     * @param inLoop Loop containing this segment
     * @return extracted segment
     * @throws OBOEException - most likely segment not found
     */
    public DTMSegment extractSegmentDTM(Loop inLoop) throws OBOEException {
        log.info("extractSegmentDTM => begin");
        DTMSegment receiver = new DTMSegment();

        Segment segment = inLoop.getSegment("DTM");
        if (segment == null) {
            return null;
        }

        DataElement de;
        de = segment.getDataElement(1);  // 374 Date/Time Qualifier
        if (de != null) {
            receiver.setDateTimeQualifier(de.get());
        }

        de = segment.getDataElement(2);  // 373 Date
        if (de != null) {
            receiver.setDate(de.get());
        }

        de = segment.getDataElement(3);  // 337 Time
        if (de != null) {
            receiver.setTime(de.get());
        }

        de = segment.getDataElement(4);  // 623 Time Code
        if (de != null) {
            receiver.setTimeCode(de.get());
        }

        de = segment.getDataElement(5);  // 1250 Date Time Period Format Qualifier
        if (de != null) {
            receiver.setDateTimePeriodFormatQualifier(de.get());
        }

        de = segment.getDataElement(6);  // 1251 Date Time Period
        if (de != null) {
            receiver.setDateTimePeriod(de.get());
        }
        log.info("extractSegmentDTM => end");
        return receiver;
    }

    /**
     * extract data from segment N1 that is part of the TableHeaderLoopDTM
     * <br>Name used
     * <br>To identify a party by type of organization, name, and code
     *
     * @param inLoop Loop containing this segment
     * @return extracted segment
     * @throws OBOEException - most likely segment not found
     */
    public N1Segment extractSegmentN1(Loop inLoop) throws OBOEException {
        log.info("extractSegmentN1 => begin");
        N1Segment receiver = new N1Segment();

        Segment segment;
        int numberOfSegmentsInVector = inLoop.getCount("N1");
        for (int i = 0; i < numberOfSegmentsInVector; i++) {
            segment = inLoop.getSegment("N1", i);
            if (segment == null) {
                return null;
            }
            DataElement de;
            de = segment.getDataElement(1);       // 98 Entity Identifier Code
            if (de != null) {
                receiver.setEntityIdentifierCodeSeq1(de.get());
            }

            de = segment.getDataElement(2);       // 93 Name
            if (de != null) {
                receiver.setName(de.get());
            }

            de = segment.getDataElement(3);       // 66 Identification Code Qualifier
            if (de != null) {
                receiver.setIdentificationCodeQualifier(de.get());
            }

            de = segment.getDataElement(4);       // 67 Identification Code
            if (de != null) {
                receiver.setIdentificationCode(de.get());
            }

            de = segment.getDataElement(5);       // 706 Entity Relationship Code
            if (de != null) {
                receiver.setEntityRelationshipCode(de.get());
            }

            de = segment.getDataElement(6);       // 98 Entity Identifier Code
            if (de != null) {
                receiver.setEntityIdentifierCodeSeq6(de.get());
            }
        }
        log.info("extractSegmentN1 => end");
        return receiver;
    }

    /**
     * extract data from loop LX that is part of TableHeaderLoopDTM
     * <br>Assigned Number used
     *
     * @param inLoop loop containing this loop
     * @return extracted loop
     * @throws OBOEException - most likely loop not found
     */
    public LXLoopVector extractLoopLX(Loop inLoop) throws OBOEException {
        log.info("extractLoopLX => begin");
        LXLoopVector rVector = new LXLoopVector();

        Loop loop;
        int numberInVector = inLoop.getCount("LX");
        for (int i = 0; i < numberInVector; i++) {

            loop = inLoop.getLoop("LX", i);
            if (loop == null) {
                return null;
            }

            LXLoop receiver = new LXLoop();

            LXSegment  lxSegment  = extractSegmentLX(loop);
            IN2Segment in2Segment = extractSegmentIN2(loop);
            NX2Segment nx2Segment = extractSegmentNX2(loop);
            REFSegment refSegment = extractSegmentREF(loop);
            SPASegment spaSegment = extractSegmentSPA(loop);
            COMSegment comSegment = extractSegmentCOM(loop);

            receiver.setAssignedNumber(lxSegment);
            receiver.setIndividualNameStructureComponents(in2Segment);
            receiver.setLocationIdComponent(nx2Segment);
            receiver.setReferenceIdentification(refSegment);
            receiver.setStatusProduct(spaSegment);
            receiver.setCommunicationContactInformation(comSegment);

            rVector.add(receiver);
        }
        log.info("extractLoopLX => end");
        return rVector;
    }

    /**
     * extract data from segment LX that is part of the TableHeaderLoopDTMLoopLX
     * <br>Assigned Number used
     * <br>To reference a line number in a transaction set
     *
     * @param inLoop Loop containing this segment
     * @return extracted segment
     * @throws OBOEException - most likely segment not found
     */
    public LXSegment extractSegmentLX(Loop inLoop) throws OBOEException {
        log.info("extractSegmentLX => begin");
        LXSegment receiver = new LXSegment();

        Segment segment = inLoop.getSegment("LX");
        if (segment == null) {
            return null;
        }

        DataElement de;
        de = segment.getDataElement(1);  // 554 Assigned Number
        if (de != null) {
            receiver.setAssignedNumber(de.get());
        }
        log.info("extractSegmentLX => end");
        return receiver;
    }

    /**
     * extract data from segment IN2 that is part of the TableHeaderLoopDTMLoopLX
     * <br>Individual Name Structure Components used
     * <br>To sequence individual name components for maximum specificity
     *
     * @param inLoop Loop containing this segment
     * @return extracted Segment
     * @throws OBOEException - most likely segment not found
     */
    public IN2Segment extractSegmentIN2(Loop inLoop) throws OBOEException {
        log.info("extractSegmentIN2 => begin");
        IN2Segment receiver = new IN2Segment();

        Segment segment;
        int numberOfSegmentsInVector = inLoop.getCount("IN2");
        for (int i = 0; i < numberOfSegmentsInVector; i++) {

            segment = inLoop.getSegment("IN2", i);
            if (segment == null) {
                return null;
            }

            DataElement de;
            de = segment.getDataElement(1);       // 1104 Name Component Qualifier
            if (de != null) {
                receiver.setNameComponentQualifier(de.get());
            }

            de = segment.getDataElement(2);       // 93 Name
            if (de != null) {
                receiver.setName(de.get());
            }
        }
        log.info("extractSegmentIN2 => end");
        return receiver;
    }

    /**
     * extract data from segment NX2 that is part of the TableHeaderLoopDTMLoopLX
     * <br>Location ID Component used
     * <br>To define types and values of a geographic location
     *
     * @param inLoop Loop containing this segment
     * @return extracted segment
     * @throws OBOEException - most likely segment not found
     */
    public NX2Segment extractSegmentNX2(Loop inLoop) throws OBOEException {
        log.info("extractSegmentNX2 => begin");
        NX2Segment receiver = new NX2Segment();

        Segment segment;
        int numberOfSegmentsInVector = inLoop.getCount("NX2");
        for (int i = 0; i < numberOfSegmentsInVector; i++) {

            segment = inLoop.getSegment("NX2", i);
            if (segment == null) {
                return null;
            }

            DataElement de;
            de = segment.getDataElement(1);       // 1106 Address Component Qualifier
            if (de != null) {
                receiver.setAddressComponentQualifier(de.get());
            }

            de = segment.getDataElement(2);       // 166 Address Information
            if (de != null) {
                receiver.setAddressInformation(de.get());
            }

            de = segment.getDataElement(3);       // 1096 County Designator
            if (de != null) {
                receiver.setCountyDesignator(de.get());
            }
        }
        log.info("extractSegmentNX2 => end");
        return receiver;
    }

    /**
     * extract data from segment REF that is part of the TableHeaderLoopDTMLoopLX
     * <br>Reference Identification used
     * <br>To specify identifying information
     *
     * @param inLoop Loop containing this segment
     * @return extracted segment
     * @throws OBOEException - most likely segment not found
     */
    public REFSegment extractSegmentREF(Loop inLoop) throws OBOEException {
        REFSegment receiver = new REFSegment();

        Segment segment;
        int numberOfSegmentsInVector = inLoop.getCount("REF");
        for (int i = 0; i < numberOfSegmentsInVector; i++) {
            segment = inLoop.getSegment("REF", i);
            if (segment == null){
                return null;
            }

            DataElement de;
            de = segment.getDataElement(1);       // 128 Reference Identification Qualifier
            if (de != null){
                receiver.setReferenceIdentificationQualifier(de.get());
            }

            de = segment.getDataElement(2);       // 127 Reference Identification
            if (de != null){
                receiver.setReferenceIdentification(de.get());
            }

            de = segment.getDataElement(3);       // 352 Description
            if (de != null){
                receiver.setDescription(de.get());
            }

            REFComposite refComp = new REFComposite();
            CompositeDE composite = segment.getCompositeDE(4);  // C040 Reference Identifier
            if (composite == null){
                return null;
            }

            de = composite.getDataElement(1);  // composite element 128 Reference Identification Qualifier
            if (de != null){
                refComp.setReferenceIdentificationQualifierSeq1(de.get());
            }

            de = composite.getDataElement(2);  // composite element 127 Reference Identification
            if (de != null){
                refComp.setReferenceIdentificationSeq2(de.get());
            }

            de = composite.getDataElement(3);  // composite element 128 Reference Identification Qualifier
            if (de != null){
                refComp.setReferenceIdentificationQualifierSeq3(de.get());
            }

            de = composite.getDataElement(4);  // composite element 127 Reference Identification
            if (de != null){
                refComp.setReferenceIdentificationSeq4(de.get());
            }

            de = composite.getDataElement(5);  // composite element 128 Reference Identification Qualifier
            if (de != null){
                refComp.setReferenceIdentificationQualifierSeq5(de.get());
            }

            de = composite.getDataElement(6);  // composite element 127 Reference Identification
            if (de != null){
                refComp.setReferenceIdentificationSeq6(de.get());
            }

            receiver.setReferenceIdentifier(refComp);
        }
        return receiver;

    }

    /**
     * extract data from segment SPA that is part of the TableHeaderLoopDTMLoopLX
     * <br>Status of Product or Activity used
     * <br>To indicate the details and status of a product or product activity
     *
     * @param inLoop Loop containing this segment
     * @return extracted segment
     * @throws OBOEException - most likely segment not found
     */
    public SPASegment extractSegmentSPA(Loop inLoop) throws OBOEException {
        SPASegment receiver = new SPASegment();

        Segment segment = inLoop.getSegment("SPA");
        if (segment == null){
            return null;
        }

        DataElement de;
        de = segment.getDataElement(1);  // 546 Status Code
        if (de != null){
            receiver.setStatusCode(de.get());
        }

        de = segment.getDataElement(2);  // 1250 Date Time Period Format Qualifier
        if (de != null){
            receiver.setDateTimePeriodFormatQualifier(de.get());
        }

        de = segment.getDataElement(3);  // 1251 Date Time Period
        if (de != null){
            receiver.setDateTimePeriod(de.get());
        }

        de = segment.getDataElement(4);  // 522 Amount Qualifier Code
        if (de != null){
            receiver.setAmountQualifierCode(de.get());
        }

        de = segment.getDataElement(5);  // 782 Monetary Amount
        if (de != null){
            receiver.setMonetaryAmount(de.get());
        }

        de = segment.getDataElement(6);  // 641 Status Reason Code
        if (de != null){
            receiver.setStatusReasonCodeSeq6(de.get());
        }

        de = segment.getDataElement(7);  // 641 Status Reason Code
        if (de != null){
            receiver.setStatusReasonCodeSeq7(de.get());
        }

        de = segment.getDataElement(8);  // 641 Status Reason Code
        if (de != null){
            receiver.setStatusReasonCodeSeq8(de.get());
        }

        de = segment.getDataElement(9);  // 559 Agency Qualifier Code
        if (de != null){
            receiver.setAgencyQualifierCode(de.get());
        }

        de = segment.getDataElement(10);  // 751 Product Description Code
        if (de != null){
            receiver.setProductDescriptionCode(de.get());
        }

        de = segment.getDataElement(11);  // 822 Source Subqualifier
        if (de != null){
            receiver.setSourceSubqualifier(de.get());
        }
        return receiver;
    }

    /**
     * extract data from segment COM that is part of the TableHeaderLoopDTMLoopLX
     * <br>Communication Contact Information used
     * <br>To specify a communication contact number
     *
     * @param inLoop Loop containing this segment
     * @return extracted segment
     * @throws OBOEException - most likely segment not found
     */
    public COMSegment extractSegmentCOM(Loop inLoop) throws OBOEException {
        COMSegment receiver = new COMSegment();

        Segment segment;
        int numberOfSegmentsInVector = inLoop.getCount("COM");
        for (int i = 0; i < numberOfSegmentsInVector; i++) {
            segment = inLoop.getSegment("COM", i);
            if (segment == null){
                return null;
            }

            DataElement de;
            de = segment.getDataElement(1);       // 365 Communication Number Qualifier
            if (de != null){
                receiver.setCommunicationNumberQualifier(de.get());
            }

            de = segment.getDataElement(2);       // 364 Communication Number
            if (de != null){
                receiver.setCommunicationNumber(de.get());
            }
        }
        return receiver;
    }

    public class Inbound101Data {

        BGNSegment beginningSegment;
        DTMLoopVector dtmLoops;

        public BGNSegment getBeginningSegment() {
            return beginningSegment;
        }

        public void setBeginningSegment(BGNSegment beginningSegment) {
            this.beginningSegment = beginningSegment;
        }


        public DTMLoopVector getDtmLoops() {
            return dtmLoops;
        }

        public void setDtmLoops(DTMLoopVector dtmLoop) {
            this.dtmLoops = dtmLoop;
        }
    }

    public class LXLoopVector extends ArrayList {
    }

    public class LXLoop {
        private LXSegment assignedNumber;
        private IN2Segment individualNameStructureComponents;
        private NX2Segment locationIdComponent;
        private REFSegment referenceIdentification;
        private SPASegment statusProduct;
        private COMSegment communicationContactInformation;

        public LXSegment getAssignedNumber() {
            return assignedNumber;
        }

        public void setAssignedNumber(LXSegment assignedNumber) {
            this.assignedNumber = assignedNumber;
        }

        public IN2Segment getIndividualNameStructureComponents() {
            return individualNameStructureComponents;
        }

        public void setIndividualNameStructureComponents(IN2Segment individualNameStructureComponents) {
            this.individualNameStructureComponents = individualNameStructureComponents;
        }

        public NX2Segment getLocationIdComponent() {
            return locationIdComponent;
        }

        public void setLocationIdComponent(NX2Segment locationIdComponent) {
            this.locationIdComponent = locationIdComponent;
        }

        public REFSegment getReferenceIdentification() {
            return referenceIdentification;
        }

        public void setReferenceIdentification(REFSegment referenceIdentification) {
            this.referenceIdentification = referenceIdentification;
        }

        public SPASegment getStatusProduct() {
            return statusProduct;
        }

        public void setStatusProduct(SPASegment statusProduct) {
            this.statusProduct = statusProduct;
        }

        public COMSegment getCommunicationContactInformation() {
            return communicationContactInformation;
        }

        public void setCommunicationContactInformation(COMSegment communicationContactInformation) {
            this.communicationContactInformation = communicationContactInformation;
        }
    }

    public class DTMLoopVector extends ArrayList {
    }

    public class DTMLoop {
        private DTMSegment dateTimeReference;
        private N1Segment n1Segment;
        private LXLoopVector lxLoops;

        public DTMSegment getDateTimeReference() {
            return dateTimeReference;
        }

        public void setDateTimeReference(DTMSegment dateTimeReference) {
            this.dateTimeReference = dateTimeReference;
        }

        public N1Segment getN1Segment() {
            return n1Segment;
        }

        public void setN1Segment(N1Segment n1Segment) {
            this.n1Segment = n1Segment;
        }

        public LXLoopVector getLxLoops() {
            return lxLoops;
        }

        public void setLxLoops(LXLoopVector lxLoop) {
            this.lxLoops = lxLoop;
        }
    }

    public class DTMSegment {

        private String dateTimeQualifier;
        private String timeCode;
        private String dateTimePeriodFormatQualifier;
        private String dateTimePeriod;
        private String date;
        private String time;


        public String getDateTimeQualifier() {
            return dateTimeQualifier;
        }

        public void setDateTimeQualifier(String dateTimeQualifier) {
            this.dateTimeQualifier = dateTimeQualifier;
        }

        public String getTimeCode() {
            return timeCode;
        }

        public void setTimeCode(String timeCode) {
            this.timeCode = timeCode;
        }

        public String getDateTimePeriodFormatQualifier() {
            return dateTimePeriodFormatQualifier;
        }

        public void setDateTimePeriodFormatQualifier(String dateTimePeriodFormatQualifier) {
            this.dateTimePeriodFormatQualifier = dateTimePeriodFormatQualifier;
        }

        public String getDateTimePeriod() {
            return dateTimePeriod;
        }

        public void setDateTimePeriod(String dateTimePeriod) {
            this.dateTimePeriod = dateTimePeriod;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDate() {
            return date;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTime() {
            return time;
        }
    }

    public class N1Segment {
        private String entityIdentifierCodeSeq1;
        private String entityIdentifierCodeSeq6;
        private String name;
        private String identificationCodeQualifier;
        private String identificationCode;
        private String entityRelationshipCode;

        public String getEntityIdentifierCodeSeq1() {
            return entityIdentifierCodeSeq1;
        }

        public void setEntityIdentifierCodeSeq1(String entityIdentifierCode) {
            this.entityIdentifierCodeSeq1 = entityIdentifierCode;
        }

        public String getEntityIdentifierCodeSeq6() {
            return entityIdentifierCodeSeq6;
        }

        public void setEntityIdentifierCodeSeq6(String entityIdentifierCode) {
            this.entityIdentifierCodeSeq6 = entityIdentifierCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdentificationCodeQualifier() {
            return identificationCodeQualifier;
        }

        public void setIdentificationCodeQualifier(String identificationCodeQualifier) {
            this.identificationCodeQualifier = identificationCodeQualifier;
        }

        public String getIdentificationCode() {
            return identificationCode;
        }

        public void setIdentificationCode(String identificationCode) {
            this.identificationCode = identificationCode;
        }

        public String getEntityRelationshipCode() {
            return entityRelationshipCode;
        }

        public void setEntityRelationshipCode(String entityRelationshipCode) {
            this.entityRelationshipCode = entityRelationshipCode;
        }
    }


    public Inbound101Data getReciver() {
        return reciver;
    }

    public class LXSegment {

        private String assignedNumber;

        public void setAssignedNumber(String assignedNumber) {
            this.assignedNumber = assignedNumber;
        }

        public String getAssignedNumber() {
            return assignedNumber;
        }
    }

    public class IN2Segment {
        private String nameComponentQualifier;
        private String name;


        public String getNameComponentQualifier() {
            return nameComponentQualifier;
        }

        public void setNameComponentQualifier(String valueComponentQualifier) {
            this.nameComponentQualifier = valueComponentQualifier;
        }

        public String getName() {
            return name;
        }

        public void setName(String value) {
            this.name = value;
        }
    }

    public class NX2Segment {
        private String addressComponentQualifier;
        private String addressInformation;
        private String countyDesignator;

        public String getAddressComponentQualifier() {
            return addressComponentQualifier;
        }

        public void setAddressComponentQualifier(String addressComponentQualifier) {
            this.addressComponentQualifier = addressComponentQualifier;
        }

        public String getAddressInformation() {
            return addressInformation;
        }

        public void setAddressInformation(String addressInformation) {
            this.addressInformation = addressInformation;
        }

        public String getCountyDesignator() {
            return countyDesignator;
        }

        public void setCountyDesignator(String countyDesignator) {
            this.countyDesignator = countyDesignator;
        }
    }

    public class BGNSegment {
        private String transactionSetPurposeCode;
        private String referenceIdentificationSeq2;
        private String referenceIdentificationSeq6;
        private String date;
        private String time;
        private String timeCode;
        private String transactionTypeCode;
        private String actionCode;
        private String securityLevelCode;

        public String getTransactionSetPurposeCode() {
            return transactionSetPurposeCode;
        }

        public void setTransactionSetPurposeCode(String transactionSetPurposeCode) {
            this.transactionSetPurposeCode = transactionSetPurposeCode;
        }

        public String getReferenceIdentificationSeq2() {
            return referenceIdentificationSeq2;
        }

        public void setReferenceIdentificationSeq2(String referenceIdentification) {
            this.referenceIdentificationSeq2 = referenceIdentification;
        }

        public String getReferenceIdentificationSeq6() {
            return referenceIdentificationSeq6;
        }

        public void setReferenceIdentificationSeq6(String referenceIdentification) {
            this.referenceIdentificationSeq6 = referenceIdentification;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTimeCode() {
            return timeCode;
        }

        public void setTimeCode(String timeCode) {
            this.timeCode = timeCode;
        }

        public String getTransactionTypeCode() {
            return transactionTypeCode;
        }

        public void setTransactionTypeCode(String transactionTypeCode) {
            this.transactionTypeCode = transactionTypeCode;
        }

        public String getActionCode() {
            return actionCode;
        }

        public void setActionCode(String actionCode) {
            this.actionCode = actionCode;
        }

        public String getSecurityLevelCode() {
            return securityLevelCode;
        }

        public void setSecurityLevelCode(String securityLevelCode) {
            this.securityLevelCode = securityLevelCode;
        }
    }

    public class SPASegment {
        private String statusCode;
        private String dateTimePeriodFormatQualifier;
        private String dateTimePeriod;
        private String amountQualifierCode;
        private String monetaryAmount;
        private String statusReasonCodeSeq6;
        private String statusReasonCodeSeq7;
        private String statusReasonCodeSeq8;
        private String agencyQualifierCode;
        private String productDescriptionCode;
        private String sourceSubqualifier;

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getDateTimePeriodFormatQualifier() {
            return dateTimePeriodFormatQualifier;
        }

        public void setDateTimePeriodFormatQualifier(String dateTimePeriodFormatQualifier) {
            this.dateTimePeriodFormatQualifier = dateTimePeriodFormatQualifier;
        }

        public String getDateTimePeriod() {
            return dateTimePeriod;
        }

        public void setDateTimePeriod(String dateTimePeriod) {
            this.dateTimePeriod = dateTimePeriod;
        }

        public String getAmountQualifierCode() {
            return amountQualifierCode;
        }

        public void setAmountQualifierCode(String amountQualifierCode) {
            this.amountQualifierCode = amountQualifierCode;
        }

        public String getMonetaryAmount() {
            return monetaryAmount;
        }

        public void setMonetaryAmount(String monetaryAmount) {
            this.monetaryAmount = monetaryAmount;
        }

        public String getStatusReasonCodeSeq6() {
            return statusReasonCodeSeq6;
        }

        public void setStatusReasonCodeSeq6(String statusReasonCodeSeq6) {
            this.statusReasonCodeSeq6 = statusReasonCodeSeq6;
        }

        public String getStatusReasonCodeSeq7() {
            return statusReasonCodeSeq7;
        }

        public void setStatusReasonCodeSeq7(String statusReasonCodeSeq7) {
            this.statusReasonCodeSeq7 = statusReasonCodeSeq7;
        }

        public String getStatusReasonCodeSeq8() {
            return statusReasonCodeSeq8;
        }

        public void setStatusReasonCodeSeq8(String statusReasonCodeSeq8) {
            this.statusReasonCodeSeq8 = statusReasonCodeSeq8;
        }

        public String getAgencyQualifierCode() {
            return agencyQualifierCode;
        }

        public void setAgencyQualifierCode(String agencyQualifierCode) {
            this.agencyQualifierCode = agencyQualifierCode;
        }

        public String getProductDescriptionCode() {
            return productDescriptionCode;
        }

        public void setProductDescriptionCode(String productDescriptionCode) {
            this.productDescriptionCode = productDescriptionCode;
        }

        public String getSourceSubqualifier() {
            return sourceSubqualifier;
        }

        public void setSourceSubqualifier(String sourceSubqualifier) {
            this.sourceSubqualifier = sourceSubqualifier;
        }
    }

    public class COMSegment {
        private String communicationNumberQualifier;
        private String communicationNumber;

        public String getCommunicationNumberQualifier() {
            return communicationNumberQualifier;
        }

        public void setCommunicationNumberQualifier(String communicationNumberQualifier) {
            this.communicationNumberQualifier = communicationNumberQualifier;
        }

        public String getCommunicationNumber() {
            return communicationNumber;
        }

        public void setCommunicationNumber(String communicationNumber) {
            this.communicationNumber = communicationNumber;
        }
    }

    public class REFSegment{
        private String referenceIdentificationQualifier;
        private String referenceIdentification;
        private String description;
        private REFComposite referenceIdentifier;

        public String getReferenceIdentificationQualifier() {
            return referenceIdentificationQualifier;
        }

        public void setReferenceIdentificationQualifier(String referenceIdentificationQualifier) {
            this.referenceIdentificationQualifier = referenceIdentificationQualifier;
        }

        public String getReferenceIdentification() {
            return referenceIdentification;
        }

        public void setReferenceIdentification(String referenceIdentification) {
            this.referenceIdentification = referenceIdentification;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public REFComposite getReferenceIdentifier() {
            return referenceIdentifier;
        }

        public void setReferenceIdentifier(REFComposite referenceIdentifier) {
            this.referenceIdentifier = referenceIdentifier;
        }
    }

    class REFComposite{
        private String referenceIdentificationQualifierSeq1;
        private String referenceIdentificationSeq2;
        private String referenceIdentificationQualifierSeq3;
        private String referenceIdentificationSeq4;
        private String referenceIdentificationQualifierSeq5;
        private String referenceIdentificationSeq6;

        public String getReferenceIdentificationQualifierSeq1() {
            return referenceIdentificationQualifierSeq1;
        }

        public void setReferenceIdentificationQualifierSeq1(String referenceIdentificationQualifierSeq1) {
            this.referenceIdentificationQualifierSeq1 = referenceIdentificationQualifierSeq1;
        }

        public String getReferenceIdentificationSeq2() {
            return referenceIdentificationSeq2;
        }

        public void setReferenceIdentificationSeq2(String referenceIdentificationSeq2) {
            this.referenceIdentificationSeq2 = referenceIdentificationSeq2;
        }

        public String getReferenceIdentificationQualifierSeq3() {
            return referenceIdentificationQualifierSeq3;
        }

        public void setReferenceIdentificationQualifierSeq3(String referenceIdentificationQualifierSeq3) {
            this.referenceIdentificationQualifierSeq3 = referenceIdentificationQualifierSeq3;
        }

        public String getReferenceIdentificationSeq4() {
            return referenceIdentificationSeq4;
        }

        public void setReferenceIdentificationSeq4(String referenceIdentificationSeq4) {
            this.referenceIdentificationSeq4 = referenceIdentificationSeq4;
        }

        public String getReferenceIdentificationQualifierSeq5() {
            return referenceIdentificationQualifierSeq5;
        }

        public void setReferenceIdentificationQualifierSeq5(String referenceIdentificationQualifierSeq5) {
            this.referenceIdentificationQualifierSeq5 = referenceIdentificationQualifierSeq5;
        }

        public String getReferenceIdentificationSeq6() {
            return referenceIdentificationSeq6;
        }

        public void setReferenceIdentificationSeq6(String referenceIdentificationSeq6) {
            this.referenceIdentificationSeq6 = referenceIdentificationSeq6;
        }
    }
}
