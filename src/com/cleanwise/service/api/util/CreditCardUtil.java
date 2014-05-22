/*
 * CreditCardUtil.java
 *
 * Created on November 2, 2004, 3:47 PM
 */

package com.cleanwise.service.api.util;
import com.cleanwise.service.api.value.OrderCreditCardData;
import java.util.GregorianCalendar;
import com.cleanwise.service.crypto.*;
import java.util.Date;
import java.security.MessageDigest;
import java.sql.Connection;

/**
 *
 * @author  bstevens
 */
public class CreditCardUtil {
    private boolean mValid = false;
    private OrderCreditCardData mOrderCreditCardData;
    private String mApiErrorMessage;
    private String mMessageReasourceErrorMessage;
    private String mErrorField;
    private final String mUnencryptedCreditCardNumber;

    /**
     *Returns a validation error message suitable for log messages and debugging.
     */
    public String getValidationApiErrorMessage(){
        return mApiErrorMessage;
    }
    /**
     *Returns a validation error message that can be used with the jakarta
     *message reasource error reporting
     */
    public String getValidationMessageReasourceErrorMessage(){
        return mMessageReasourceErrorMessage;
    }
    /**
     *Returns a validation error message indicating the field that caused the problem
     */
    public String getValidationErrorField(){
        return mErrorField;
    }

    /**
     *Creates a new CreditCardUtil object.
     */
    public CreditCardUtil(String pUnencryptedCreditCardNumber,OrderCreditCardData pOrderCreditCardData,
      boolean isStateProvinceRequired){
        mOrderCreditCardData = pOrderCreditCardData;
        mUnencryptedCreditCardNumber = pUnencryptedCreditCardNumber;
        mValid = validateCreditCard(isStateProvinceRequired);
    }

/*    public CreditCardUtil(String pUnencryptedCreditCardNumber,OrderCreditCardData pOrderCreditCardData) {
      mOrderCreditCardData = pOrderCreditCardData;
      mUnencryptedCreditCardNumber = pUnencryptedCreditCardNumber;
      mValid = validateCreditCard(true);
    }*/

    /**
     *If the credit card this object represents is valid returns true.  Otherwise returns
     *false and the @see getValidationErrorField, @see getValidationApiErrorMessage and
     *@see getValidationMessageReasourceErrorMessage methods should be queried to determine
     *the cause.
     */
    public boolean isValid(){
        return mValid;
    }

    /**
     *Validates the supplied credit card data.  Populates the passed in @see ActionErrors
     *object upon any errors
     */
    private boolean validateCreditCard(boolean isStateProvinceRequired){
        if(!validateCreditCardCheckDigit(mUnencryptedCreditCardNumber)){
            mApiErrorMessage = "credit card failed mod 10 check";
            mMessageReasourceErrorMessage = "error.credit.card.invalid";
        }

        GregorianCalendar cal = new GregorianCalendar();
        Date today = new Date();
        cal.setTime(today);

        if(mOrderCreditCardData.getExpMonth()<GregorianCalendar.JANUARY || mOrderCreditCardData.getExpMonth()>GregorianCalendar.DECEMBER ) {
            mApiErrorMessage = mOrderCreditCardData.getExpMonth() + " is not a valid month";
            mMessageReasourceErrorMessage = "error.month.format";
            mErrorField = Integer.toString(mOrderCreditCardData.getExpMonth());
            return false;
        }

        int cardLength = mUnencryptedCreditCardNumber.trim().length();
        if(cardLength<14 || cardLength>16) {
                mApiErrorMessage = "Credit card number is not the right length ("+cardLength+")";
                mMessageReasourceErrorMessage = "error.credit.card.invalid";
                return false;

        }
        if(mOrderCreditCardData.getExpYear()<cal.get(GregorianCalendar.YEAR) ||
        mOrderCreditCardData.getExpYear()==cal.get(GregorianCalendar.YEAR) && mOrderCreditCardData.getExpMonth()<cal.get(GregorianCalendar.MONTH)) {
            mApiErrorMessage = "Credit card has expired";
            mMessageReasourceErrorMessage = "error.credit.card.expired";
            return false;
        }
        if(!Utility.isSet(mOrderCreditCardData.getName())) {
                mApiErrorMessage = "Name is empty";
                mMessageReasourceErrorMessage = "error.credit.card.nameIsEmpty";
                mErrorField = "Name";
                return false;
        }
        if(!Utility.isSet(mOrderCreditCardData.getAddress1())) {
                mApiErrorMessage = "address 1 is empty";
                mMessageReasourceErrorMessage = "error.credit.card.address1IsEmpty";
                mErrorField ="Street 1";
                return false;
        }

        if(!Utility.isSet(mOrderCreditCardData.getCity())) {
                mApiErrorMessage = "City is empty";
                mMessageReasourceErrorMessage = "error.credit.card.cityIsEmpty";
                mErrorField = "City";
                return false;
        }

        if( isStateProvinceRequired &&
            !Utility.isSet(mOrderCreditCardData.getStateProvinceCd())) {
                mApiErrorMessage = "State or Province CD is empty";
                mMessageReasourceErrorMessage = "error.credit.card.stateIsEmpty";
                mErrorField = "State";
                return false;
        }

        if(!Utility.isSet(mOrderCreditCardData.getPostalCode())) {
                mApiErrorMessage = "Postal Code is empty";
                mMessageReasourceErrorMessage = "error.credit.card.postalCodeIsEmpty";
                mErrorField = "Zip Code";
                return false;
        }

        if(mUnencryptedCreditCardNumber.startsWith("0")){
            mApiErrorMessage = "invalid card, could not validate type";
            mMessageReasourceErrorMessage = "error.credit.card.invalid";
            return false;
        }
        int str1Digits = Integer.parseInt(mUnencryptedCreditCardNumber.substring(0,1));
        int str2Digits = Integer.parseInt(mUnencryptedCreditCardNumber.substring(0,2));
        int str3Digits = Integer.parseInt(mUnencryptedCreditCardNumber.substring(0,3));
        int str4Digits = Integer.parseInt(mUnencryptedCreditCardNumber.substring(0,4));

        String cardType=null;
        //-----VISA Check----
        //starts with 4 and 14 or 16 digits long
        if(str1Digits==4 && (cardLength == 14 || cardLength == 16)){
            cardType = RefCodeNames.PAYMENT_TYPE_CD.VISA;
        }
        //-----MASTER CARD-----
        //between 51 and 55 and 16 digits long
        else if(str2Digits >= 51 && str2Digits <= 55 && cardLength == 16){
            cardType = RefCodeNames.PAYMENT_TYPE_CD.MASTER_CARD;
        }
        //-----AMERICAN EXPRESS-----
        //starts with 34 or 37 and 15 digits long
        else if(str2Digits == 34 && str2Digits == 37 && cardLength == 15){
            cardType = RefCodeNames.PAYMENT_TYPE_CD.AMERICAN_EXPRESS;
        }
        //-----DISCOVER-----
        //starts with 60 and 16 digits long
        else if(str2Digits == 60 && cardLength == 16){
            cardType = RefCodeNames.PAYMENT_TYPE_CD.AMERICAN_EXPRESS;
        }
        if(cardType == null){
                mApiErrorMessage = "Could not determine card type from credit card number";
                mMessageReasourceErrorMessage = "error.credit.card.invalid";
                return false;
        }else if(!cardType.equals(mOrderCreditCardData.getCreditCardType())){
                mApiErrorMessage = "Card type determined from cc number does not match credit card type";
                mMessageReasourceErrorMessage = "error.credit.card.wrong.type";
                return false;
        }
        return true;
    }

    public static void main(String[] args){
            OrderCreditCardData ocd = OrderCreditCardData.createValue();
            ocd.setAddress1("addr1");
            ocd.setCity("asd");
            ocd.setExpMonth(1);
            ocd.setExpYear(2099);
            ocd.setName("name");
            ocd.setPostalCode("01756");
            ocd.setStateProvinceCd("MA");
            ocd.setCreditCardType("Visa");
            CreditCardUtil u = new CreditCardUtil("4111111111111111",ocd, true);
        }



    /**
     * Validate a credit card number's checksum. This is kinda
     * neat. The last digit of all credit cards is acutally a "check
     * digit", and can be determined by performing a checksum on
     * all the digits that precede it. The checksum is referred to in
     * the credit card industry as a "MOD 10 check digit routine".
     *
     * <p>Each digit,except the last is multipled by a "weight", to
     * produce a digit "value". The weight alternates between 2 and 1,
     * starting with 2 for the last (right most) digit. The resulting
     * values are summed. To sum the results, single digit values are
     * simply added to the total. For two digit values the tens and the
     * ones digits are added separately, so 14 = 1+4 = 5. Then to test
     * the check digit take the total MOD 10 and subtract it from
     * ten. This should equal the check digit.
     *@returns true if it passes the mod 10 check, false if it does not.
     */
    private boolean validateCreditCardCheckDigit(String iNumber) {
        int checksum = 0;   // checksum total
        int value;          // the checksum value for a single digit
        int weight;         // multiplier for individual digits
        int checkDigit;     // integer value of last credit card digit

        if(iNumber == null){
            return false;
        }

        iNumber=iNumber.trim(); //it comes out of the db as a fixed length (16) so lets remove the spaces

        if(iNumber.length() == 0){
            return false;
        }

        // Checksum all the digits, except the last one, processing from right to left

        weight = 2; // starting weight

        for (int k=iNumber.length()-2; k >=0; k--) {
            // multiply the digit by the weight
            value = Character.digit(iNumber.charAt(k), 10) * weight;

            // add the tens digit and the ones digit of the result to our total
            checksum += (value / 10) + (value % 10);

            // toggle weight for next iteration(2->1, 1->2)
            weight = weight % 2 + 1;
        }

        // Okay, now check the check digit
        checkDigit = Character.digit(iNumber.charAt(iNumber.length()-1), 10);

        if ((10 - checksum % 10) % 10 != checkDigit)
            return false;

        return true;
    }

    /**
     *Sets all of the various encryption properties of the OrderCreditCard.
     */
    public void encryptOrderCreditCard(Connection pCon,  int storeId) throws Exception{
        if(!mValid){
            throw new Exception("Credit Card Not Valid"+mApiErrorMessage);
        }
        //get a hash of the credit card number
        BASE64Encoder encoder = new BASE64Encoder();
        String hashAlgo = "SHA-1";
        MessageDigest md = MessageDigest.getInstance(hashAlgo);
        md.update(mUnencryptedCreditCardNumber.getBytes("UTF8"));
        String hashedCcNum = encoder.encode(md.digest());
        //set the display num to be the last 4 digits of the cc number
        mOrderCreditCardData.setCreditCardNumberDisplay(mUnencryptedCreditCardNumber.substring(mUnencryptedCreditCardNumber.length()-4,mUnencryptedCreditCardNumber.length()));
        mOrderCreditCardData.setHashAlgorithm(hashAlgo);
        mOrderCreditCardData.setHashedCreditCardNumber(hashedCcNum);

        //encrypt the credit card number
        CryptoUtil crypto = Utility.createCryptoUtil(pCon);
        java.security.Provider[] p = java.security.Security.getProviders();
        PropertyUtil propU = new PropertyUtil(pCon);
//        String alias = propU.getProperty(CryptoPropNames.kHostCCAliasPropName);
        String alias = propU.fetchValue(0, storeId, CryptoPropNames.kHostCCAliasPropName);
        byte[] encryptedBuffer = crypto.encryptBuffer(alias,mUnencryptedCreditCardNumber.getBytes("UTF8"));
        String encryptedCcNum = encoder.encode(encryptedBuffer);
        mOrderCreditCardData.setEncryptionAlias(alias);
        mOrderCreditCardData.setEncryptedCreditCardNumber(encryptedCcNum);
        mOrderCreditCardData.setEncryptionAlgorithm(crypto.getEncryptAlgorithm());
    }


    /**
     *Charges a credit card
     */
    public void chargeCreditCard(){

    }

    /**
     *Authorizes a credit card
     */
    public void authorizeCreditCard(){

    }
}
