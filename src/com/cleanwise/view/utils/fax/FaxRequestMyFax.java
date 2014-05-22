/*
 * FaxRequestMyFax.java
 *
 * Created on January 19, 2009, 9:38 AM
 */

package com.cleanwise.view.utils.fax;

import com.cleanwise.service.api.util.Utility;

/**
 *Object that embodies the actual request made, when submitting a fax this object will typically contain
 *a number to fax the document to and will be returned with the status fields filled in.  When requesting
 *and exsiting document the object must conntain the faxId (returned when the document is faxed) and will
 *be returned with the state of the document.
 * @author  bstevens
 */
public class FaxRequestMyFax {
    
    /** Holds value of property faxNum. */
    private String receiverFaxNum;
    
    /** Holds value of property name. */
    private String receiverName;
    
    /** Holds value of property company. */
    private String receiverCompany;
    
    /** Holds value of property status. */
    private int status;
    
    /** Holds value of property statusMessage. */
    private String statusMessage;
    
    /** Holds value of property requestId. */
    private String requestId;
    
    public Object clone(){
        FaxRequestMyFax clone = new FaxRequestMyFax(this.receiverFaxNum, this.receiverName, this.receiverCompany);
        clone.setStatus(this.status);
        clone.setStatusMessage(this.statusMessage);
        clone.setRequestId(this.requestId);
        return clone;
    }
    
    /**
     *Creates a new instance of FaxRequestMyFax with a set of default values to be used for each fax
     *document this form of the constructor is intended to be used with sending outbound faxes.
     *@param faxNum fax number to send the message to. - REQUIRED
     *@param name name of the person to receive the fax. - OPTIONAL
     *@param company company fax is being sent to - OPTIONAL
     *@Throws java.lang.NullPointerException if sFaxNum is null or an empty string
     */
    public FaxRequestMyFax(String pReceiverFaxNum, String pReceiverName, String pReceiverCompany) {
        //for testing
        //if (!(pReceiverFaxNum.equals("508-597-0591"))){
        //    throw new IllegalStateException("not test data");
        //}
        //pReceiverFaxNum = "508-481-7861";
        if (pReceiverFaxNum == null || pReceiverFaxNum.equals("")){
            throw new NullPointerException("Fax Number is required");
        }
        setReceiverFaxNum(pReceiverFaxNum);
        if (pReceiverName == null){
            setReceiverName("");
        }else {
            setReceiverName(pReceiverName);
        }
        if (pReceiverCompany == null){
            setReceiverCompany("");
        }else {
            setReceiverCompany(pReceiverCompany);
        }
    }
    
    /** Creates a new instance of FaxRequestMyFax */
    public FaxRequestMyFax() {
    }
    
    /** Getter for property faxNum.
     * @return Value of property faxNum.
     */
    String getReceiverFaxNum() {
        return this.receiverFaxNum;
    }
    
    /** Setter for property faxNum.
     * @param faxNum New value of property faxNum.
     */
    public void setReceiverFaxNum(String receiverFaxNum) {
        this.receiverFaxNum = receiverFaxNum;
    }
    
    /** Getter for property name.
     * @return Value of property name.
     */
    String getReceiverName() {
        return this.receiverName;
    }
    
    /** Setter for property name.
     * @param name New value of property name.
     */
    void setReceiverName(String receiverName) {
        this.receiverName = Utility.toXMLString(receiverName);
    }
    
    /** Getter for property company.
     * @return Value of property company.
     */
    public String getReceiverCompany() {
        return this.receiverCompany;
    }
    
    /** Setter for property company.
     * @param company New value of property company.
     */
    void setReceiverCompany(String receiverCompany) {
        this.receiverCompany = Utility.toXMLString(receiverCompany);
    }
    
    /** Getter for property status.
     * @return Value of property status.
     */
    public int getStatus() {
        return this.status;
    }
    
    /** Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(int status) {
        this.status = status;
    }
    
    /** Getter for property statusMessage.
     * @return Value of property statusMessage.
     */
    public String getStatusMessage() {
        return this.statusMessage;
    }
    
    /** Setter for property statusMessage.
     * @param statusMessage New value of property statusMessage.
     */
    void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    
    /** Getter for property requestId.
     * @return Value of property requestId.
     */
    public String getRequestId() {
        return this.requestId;
    }
    
    /** Setter for property requestId.
     * @param requestId New value of property requestId.
     */
    void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    
    public String toString(){
        return "Company: " + this.receiverCompany +
            " faxNum: " + this.receiverFaxNum +
            " name: " + this.receiverName +
            " requestId: " + this.requestId +
            " status: " + this.status +
            " statusMessage: " + this.statusMessage;
    }
    
}
