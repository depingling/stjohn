/**
 * Status_x0020_ReportsSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.WebServices.Reports._2005._9;

public interface Status_x0020_ReportsSoap extends java.rmi.Remote {

    /**
     * Returns Summary Information About Voice Broadcast
     */
    public com.protus.www.Reports.GetVoiceStatusResult getVoiceStatus(int userID, java.lang.String userPassword, boolean allUsersFlag, java.lang.String billingCodeFilter, java.lang.String transactionIDListFilter, java.lang.String startTimeStampFilter, java.lang.String endTimeStampFilter, java.lang.String voiceNumbersListFilter) throws java.rmi.RemoteException;

    /**
     * Returns Summary Information About Voice Broadcast in CSV Format
     */
    public java.lang.String getVoiceStatusCSV(int userID, java.lang.String userPassword, boolean allUsersFlag, java.lang.String billingCodeFilter, java.lang.String transactionIDListFilter, java.lang.String startTimeStampFilter, java.lang.String endTimeStampFilter, java.lang.String voiceNumbersListFilter) throws java.rmi.RemoteException;

    /**
     * Returns Detail Information About Voice Broadcast
     */
    public com.protus.www.Reports.GetDetailVoiceStatusResult getDetailVoiceStatus(int userID, java.lang.String userPassword, boolean allUsersFlag, java.lang.String billingCodeFilter, java.lang.String transactionIDListFilter, java.lang.String startTimeStampFilter, java.lang.String endTimeStampFilter, java.lang.String voiceNumbersListFilter) throws java.rmi.RemoteException;

    /**
     * Returns Detail Information About Voice Broadcast in CSV Format
     */
    public java.lang.String getDetailVoiceStatusCSV(int userID, java.lang.String userPassword, boolean allUsersFlag, java.lang.String billingCodeFilter, java.lang.String transactionIDListFilter, java.lang.String startTimeStampFilter, java.lang.String endTimeStampFilter, java.lang.String voiceNumbersListFilter) throws java.rmi.RemoteException;

    /**
     * Returns Summary Information About Fax Single & Broadcast
     */
    public com.protus.www.Reports.GetFaxStatusResult getFaxStatus(int userID, java.lang.String userPassword, boolean allUsersFlag, java.lang.String billingCodeFilter, java.lang.String transactionIDListFilter, java.lang.String startTimeStampFilter, java.lang.String endTimeStampFilter, java.lang.String faxNumbersListFilter) throws java.rmi.RemoteException;

    /**
     * Returns Summary Information About Fax Single & Broadcast in
     * CSV Format
     */
    public java.lang.String getFaxStatusCSV(int userID, java.lang.String userPassword, boolean allUsersFlag, java.lang.String billingCodeFilter, java.lang.String transactionIDListFilter, java.lang.String startTimeStampFilter, java.lang.String endTimeStampFilter, java.lang.String faxNumbersListFilter) throws java.rmi.RemoteException;

    /**
     * Returns Detail Information About Fax Single & Broadcast
     */
    public com.protus.www.Reports.GetDetailFaxStatusResult getDetailFaxStatus(int userID, java.lang.String userPassword, boolean allUsersFlag, java.lang.String billingCodeFilter, java.lang.String transactionIDListFilter, java.lang.String startTimeStampFilter, java.lang.String endTimeStampFilter, java.lang.String faxNumbersListFilter) throws java.rmi.RemoteException;

    /**
     * Returns Detail Information About Fax Single & Broadcast in
     * CSV Format
     */
    public java.lang.String getDetailFaxStatusCSV(int userID, java.lang.String userPassword, boolean allUsersFlag, java.lang.String billingCodeFilter, java.lang.String transactionIDListFilter, java.lang.String startTimeStampFilter, java.lang.String endTimeStampFilter, java.lang.String faxNumbersListFilter) throws java.rmi.RemoteException;

    /**
     * Returns Information About Received Faxes
     */
    public com.protus.www.Reports.GetReceivedFaxesHistoryResult getReceivedFaxesHistory(int userID, java.lang.String userPassword, boolean allUsersFlag, java.lang.String startTimeStampFilter, java.lang.String endTimeStampFilter, java.lang.String faxNumbersListFilter) throws java.rmi.RemoteException;

    /**
     * Returns Information About Received Faxes in CSV Format
     */
    public java.lang.String getReceivedFaxesHistoryCSV(int userID, java.lang.String userPassword, boolean allUsersFlag, java.lang.String startTimeStampFilter, java.lang.String endTimeStampFilter, java.lang.String faxNumbersListFilter) throws java.rmi.RemoteException;

    /**
     * Returns all of available cover pages info.
     */
    public com.protus.www.Reports.GetCoverPageNameListResult getCoverPageNameList(long userID, java.lang.String userPassword) throws java.rmi.RemoteException;
}
