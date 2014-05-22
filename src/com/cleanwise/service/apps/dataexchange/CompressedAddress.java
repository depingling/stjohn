package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Represents a compressed address sutible for EDi presentation.  Will combine the address
 * name with line 1 if appropriate, eliminates empty lines, and concatonates any remaing lines
 * when appropriate.  It will combine only lines that are adjasent to one anouther, and it
 * deterines the shortest possible set of lines, in other words sometime address line 2 and 3
 * will be combined while other times address lines 3 and 4 will be combined.  If it is necessary
 * to do any concatonation the first lines to be combined will always be the address name and
 * address line 1 such that the address line 1 comes first followed by a space then the address name.
 *
 * @author  bstevens
 */
public class CompressedAddress {
    private String city;
    private String state;
    private String zip;
    private String country;
    private List theAddressLines;
    private int addressLinesSize;
    
    /**
     *Returns Address Line 1 or null if this address line does not exists
     */
    public String getAddress1(){
        return getAddressLine(0);
    }
    
    /**
     *Returns Address Line 2 or null if this address line does not exists
     */
    public String getAddress2(){
        return getAddressLine(1);
    }
    
    /**
     *Returns Address Line 3 or null if this address line does not exists
     */
    public String getAddress3(){
        return getAddressLine(2);
    }
    
    /**
     *Returns Address Line 4 or null if this address line does not exists
     */
    public String getAddress4(){
        return getAddressLine(3);
    }
    
    /**
     *Returns the City Property
     */
    public String getCity(){
        return city;
    }
    
    /**
     *Returns the StateProvinceCode Property
     */
    public String getStateProvinceCode(){
        return state;
    }
    
    /**
     *Returns the PostalCode Property
     */
    public String getPostalCode(){
        return zip;
    }
    
    /**
     *Returns the CountryCode Property
     */
    public String getCountryCode(){
        return country;
    }
    
    /**
     *Creates a new CompressedAddress object from an order address object with the specified number
     *of lines in the address
     */
    public CompressedAddress(OrderAddressData orderAddr, int numAddressLines){
        init(orderAddr.getShortDesc(),orderAddr.getAddress1(), orderAddr.getAddress2(),
        orderAddr.getAddress3(), orderAddr.getAddress4(), orderAddr.getCity(), orderAddr.getStateProvinceCd(),
        orderAddr.getPostalCode(),orderAddr.getCountryCd(),numAddressLines);
    }
    
    /**
     *Creates a new CompressedAddress object with the specified number
     *of lines in the address
     */
    public CompressedAddress(String pName,String pAddr1,String pAddr2,String pAddr3,
    String pAddr4,String pCity,String pState,String pPostalCode,String pCountry, int numAddressLines){
        init(pName, pAddr1, pAddr2, pAddr3, pAddr4, pCity, pState, pPostalCode, pCountry,numAddressLines);
    }
    
    /**
     *Returns the address line at the specified index.  If there is no address line at the given index null
     *is returned.  This method will <b>NOT</b> throw an indexOutOfBounds exception if an address line greater than
     *the number of address lines availiable is requested.
     */
    private String getAddressLine(int line){
        StringBuffer value = null;
        if(line < addressLinesSize){
            value = (StringBuffer) theAddressLines.get(line);
        }
        if (value != null){
            return value.toString();
        }else{
            return null;
        }
    }
    
    /*
     *Does all the work of initializing this object.  This method is basically called from the constructors
     */
    private void init(String pName,String pAddr1,String pAddr2,String pAddr3,
    String pAddr4,String pCity,String pState,String pZip,String pCountry, int numAddressLines){
        city = pCity;
        state = pState;
        zip = pZip;
        country = pCountry;
        if(numAddressLines <= 0){
            return;
        }
        java.util.LinkedList addressLines = new java.util.LinkedList();
        if(Utility.isSet(pAddr1)){
            addressLines.add(new StringBuffer(pAddr1));
        }
        if(Utility.isSet(pAddr2)){
            addressLines.add(new StringBuffer(pAddr2));
        }
        if(Utility.isSet(pAddr3)){
            addressLines.add(new StringBuffer(pAddr3));
        }
        if(Utility.isSet(pAddr4)){
            addressLines.add(new StringBuffer(pAddr4));
        }
        int len = addressLines.size();
        if(Utility.isSet(pName)){
            if(len < numAddressLines){
                len++;
                addressLines.addFirst(new StringBuffer(pName));
            }else{
                //the first address may not be addressLine1, so use first value in list
                StringBuffer line1=(StringBuffer) addressLines.getFirst();
                line1.append(" ");
                line1.append(pName);
            }
        }
        while(addressLines.size() > numAddressLines){
            condenseList(addressLines);
        }
        //put the lines into an arraylist for random access
        theAddressLines = new ArrayList();
        theAddressLines.addAll(addressLines);
        addressLinesSize = theAddressLines.size();
    }
    
    
    /**
     *Takes in a list of StringBuffers and combines the 2 shortest stringBuffer objects with a ","
     *as the seperator between the 2 lines.
     */
    private void condenseList(List l){
        Iterator it = l.iterator();
        int toRemoveIdx = -1;
        StringBuffer lastBuf = null;
        int minLength = -1;
        int ct = 0;
        while(it.hasNext()){
            StringBuffer sb = (StringBuffer) it.next();
            if(lastBuf != null){
                int cominedLen = sb.length() + lastBuf.length();
                if((minLength < 0) || (minLength > cominedLen)){
                    minLength = cominedLen;
                    toRemoveIdx = ct;
                }
            }
            lastBuf = sb;
            ct++;
        }
        StringBuffer sb = (StringBuffer) l.get(toRemoveIdx - 1);
        sb.append(',');
        sb.append(l.get(toRemoveIdx));
        l.remove(toRemoveIdx);
    }
    
    /**
     *Returns a sting representation of this object.
     */
    public String toString(){
        StringBuffer sb = new StringBuffer();
        if(theAddressLines != null){
            Iterator it = theAddressLines.iterator();
            while(it.hasNext()){
                sb.append(it.next());
                sb.append("\n");
            }
        }
        sb.append(city + "," + state + "," + zip + "\n");
        sb.append(country);
        return sb.toString();
    }
    
    /**
    *Main method to debug an address.
    */
    /*public static void main(String[] args){
        CompressedAddress a = 
        new CompressedAddress("2279 - JCI - JCPenney", "Shawnee Mall","4901 N Kickapoo Rd", "WORK ORDER #1160018230", "ATTN: Retail Technician", "Shawnee", "OK", "74801", "US", 3);

    }*/
}
