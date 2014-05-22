package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.RefCodeNames;
import java.lang.*;
import java.util.*;
import java.io.*;


/**
 * <code>InterchangeRequestData</code> is a ValueObject class 
 *  representing an interchange request.
 */
public class InterchangeRequestData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 4108890287223636692L;
	private InterchangeData interchangeD;
	private ElectronicTransactionDataVector transactionV;
	private ArrayList transactionDetailAL; //details for each element of transactionV (depends on type of trnasaction)

	/**
	 * Constructor.
	 */
	private InterchangeRequestData ()
	{
		transactionV = new ElectronicTransactionDataVector();
		transactionDetailAL = new ArrayList();
	}

	/**
	 * Creates a new InterchangeData
	 *
	 * @return
	 *  Newly initialized InterchangeData object.
	 */
	public static InterchangeRequestData createValue ()
	{
		InterchangeRequestData valueData = new InterchangeRequestData();

		return valueData;
	}

	/**
	 * add a edi transaction object to transaction vector
	 * @param pTransaction  transaction object to insert.
	 */
	public void addTransaction(ElectronicTransactionData pTransaction) {
		transactionV.add(pTransaction);
	}

	/**
	 * add a edi transaction object to transaction vector
	 * @param pTransaction  transaction object to insert.
	 */
	public void addTransactionDetails(ArrayList pDetails) {
		if (transactionDetailAL == null)
			transactionDetailAL = new ArrayList();
		transactionDetailAL.add(pDetails);
	}

	/**
	 * Set the interchangeD object.
	 * @param pInterchange  interchange data object.
	 */
	public void setInterchangeData(InterchangeData  pInterchange) {
		interchangeD = pInterchange;
	}

	/**
	 * Get interchangeD field.
	 * @return interchangeD interchange data object.
	 */
	public InterchangeData getInterchangeData() {
		return interchangeD;
	}

	/**
	 * Set the transactionV field.
	 * @param pTransactionV  transaction vector object.
	 */
	public void setTransactionDataVector(ElectronicTransactionDataVector  pTransactionV) {
		transactionV = pTransactionV;
	}

	/**
	 * Set the transactionV field.
	 * @param pTransactionV  transaction vector object.
	 * @param pDetails vectror of vectors of trnasaction details. One vector of details for each trnasaction
	 */
	public void setTransactionDataVector(ElectronicTransactionDataVector  pTransactionV,
			ArrayList pDetails
	) {
		transactionV = pTransactionV;
		transactionDetailAL = pDetails;
	}


	/**
	 * Get transactionV field.
	 * @return transactionV transaction vector object.
	 */
	public ElectronicTransactionDataVector getTransactionDataVector() {
		return transactionV;
	}

	/**
	 * Get transactionDetails vector.
	 * @param index index of transaction
	 * @return ArrayList of transaction detail objects
	 */
	public ArrayList getTransactionDetails(int pInd ) {            
		if(pInd>=transactionDetailAL.size()) {
			return null;
		}
		return (ArrayList) transactionDetailAL.get(pInd);
	}

	/**
	 * Describe <code>toString</code> method here.
	 *
	 * @return a <code>String</code> value
	 */
	public String toString() {
		String ss =  interchangeD.toString() + "\n";
		if(transactionV!=null) {
			for(int ii=0; ii<transactionV.size(); ii++) {
				ss+=transactionV.get(ii)+"\n"+getTransactionDetails(ii);
			}
		}
		return (ss);
	}
}
