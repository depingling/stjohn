package com.cleanwise.service.apps.loaders;

import java.util.Date;
import java.math.BigDecimal;
/**
 * Represents a A/R Activity Transmission Total Record as specified in
 * the: "Electronic Commerce Services Proprietary A/R Activity File Format"
 * guide supplied by CIT.
 * @author  bstevens
 */
public class CITTransmissionTotal {

    /** Holds value of property groupClientNumber. */
    private String groupClientNumber;
    
    /** Holds value of property envelopeNumber. */
    private int envelopeNumber;
    
    /** Holds value of property dateOfExtract. */
    private Date dateOfExtract;
    
    /** Holds value of property formatType. */
    private String formatType;
    
    /** Holds value of property clientNumber. */
    private String clientNumber;
    
    /** Holds value of property numberNameAddrRecords. */
    private int numberNameAddrRecords;
    
    /** Holds value of property numberARDetailRecords. */
    private int numberARDetailRecords;
    
    /** Holds value of property totalAmount. */
    private BigDecimal totalAmount;
    
    /** Holds value of property dataFormatIndicator. */
    private String dataFormatIndicator;
    
    /** Creates a new instance of CITTransmissionTotal */
    public CITTransmissionTotal() {
    }

    /** Getter for property groupClientNumber.
     * @return Value of property groupClientNumber.
     */
    public String getGroupClientNumber() {
        return this.groupClientNumber;
    }
    
    /** Setter for property groupClientNumber.
     * @param groupClientNumber New value of property groupClientNumber.
     */
    public void setGroupClientNumber(String groupClientNumber) {
        this.groupClientNumber = groupClientNumber;
    }
    
    /** Getter for property envelopeNumber.
     * @return Value of property envelopeNumber.
     */
    public int getEnvelopeNumber() {
        return this.envelopeNumber;
    }
    
    /** Setter for property envelopeNumber.
     * @param envelopeNumber New value of property envelopeNumber.
     */
    public void setEnvelopeNumber(int envelopeNumber) {
        this.envelopeNumber = envelopeNumber;
    }
    
    /** Getter for property dateOfExtract.
     * @return Value of property dateOfExtract.
     */
    public Date getDateOfExtract() {
        return this.dateOfExtract;
    }
    
    /** Setter for property dateOfExtract.
     * @param dateOfExtract New value of property dateOfExtract.
     */
    public void setDateOfExtract(Date dateOfExtract) {
        this.dateOfExtract = dateOfExtract;
    }
    
    /** Getter for property formatType.
     * @return Value of property formatType.
     */
    public String getFormatType() {
        return this.formatType;
    }
    
    /** Setter for property formatType.
     * @param formatType New value of property formatType.
     */
    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }
    
    /** Getter for property clientNumber.
     * @return Value of property clientNumber.
     */
    public String getClientNumber() {
        return this.clientNumber;
    }
    
    /** Setter for property clientNumber.
     * @param clientNumber New value of property clientNumber.
     */
    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }
    
    /** Getter for property numberNameAddrRecords.
     * @return Value of property numberNameAddrRecords.
     */
    public int getNumberNameAddrRecords() {
        return this.numberNameAddrRecords;
    }
    
    /** Setter for property numberNameAddrRecords.
     * @param numberNameAddrRecords New value of property numberNameAddrRecords.
     */
    public void setNumberNameAddrRecords(int numberNameAddrRecords) {
        this.numberNameAddrRecords = numberNameAddrRecords;
    }
    
    /** Getter for property numberARDetailRecords.
     * @return Value of property numberARDetailRecords.
     */
    public int getNumberARDetailRecords() {
        return this.numberARDetailRecords;
    }
    
    /** Setter for property numberARDetailRecords.
     * @param numberARDetailRecords New value of property numberARDetailRecords.
     */
    public void setNumberARDetailRecords(int numberARDetailRecords) {
        this.numberARDetailRecords = numberARDetailRecords;
    }
    
    /** Getter for property totalAmount.
     * @return Value of property totalAmount.
     */
    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }
    
    /** Setter for property totalAmount.
     * @param totalAmount New value of property totalAmount.
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    /** Getter for property dataFormatIndicator.
     * @return Value of property dataFormatIndicator.
     */
    public String getDataFormatIndicator() {
        return this.dataFormatIndicator;
    }
    
    /** Setter for property dataFormatIndicator.
     * @param dataFormatIndicator New value of property dataFormatIndicator.
     */
    public void setDataFormatIndicator(String dataFormatIndicator) {
        this.dataFormatIndicator = dataFormatIndicator;
    }
    
}
