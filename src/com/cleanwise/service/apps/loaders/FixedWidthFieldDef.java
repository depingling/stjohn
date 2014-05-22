/*
 * FixedWidthParserCharRange.java
 *
 * Created on June 26, 2002, 5:11 PM
 */

package com.cleanwise.service.apps.loaders;
import java.lang.reflect.Method;

/**
 * Configuration object for the FixedWidthParser.  Contains the mapping of 
 * fields (from char to char) to the method name of the object that is going 
 * to be used.  The object name is passed into the FixedWidthParser on 
 * instantiation, and currently setup only supports file row to object mapping
 * @author  bstevens
 */
public class FixedWidthFieldDef {
    /** Default constructor */
    public FixedWidthFieldDef(){}
    
    public FixedWidthFieldDef(int pFrom, int pTo, String pProperty, 
        String pDateFormatPattern,String pLeftTrimPattern,String pRightTrimPattern){
        fromChar = pFrom;
        toChar = pTo;
        property = pProperty;
        dateFormatPattern = pDateFormatPattern;
        leftTrimPattern = pLeftTrimPattern;
        rightTrimPattern = pRightTrimPattern;
        doNotTrim = false;
    }
    
    public FixedWidthFieldDef(int pFrom, int pTo, String pProperty, 
        String pDateFormatPattern,String pLeftTrimPattern,String pRightTrimPattern,boolean pDoNotTrim){
        fromChar = pFrom;
        toChar = pTo;
        property = pProperty;
        dateFormatPattern = pDateFormatPattern;
        leftTrimPattern = pLeftTrimPattern;
        rightTrimPattern = pRightTrimPattern;
        doNotTrim = pDoNotTrim;
    }
    
    public FixedWidthFieldDef(int pFrom, int pTo, String pProperty, 
        String pDateFormatPattern,String pLeftTrimPattern,String pRightTrimPattern,
        boolean pDoNotTrim,String pRightPadString){
        fromChar = pFrom;
        toChar = pTo;
        property = pProperty;
        dateFormatPattern = pDateFormatPattern;
        leftTrimPattern = pLeftTrimPattern;
        rightTrimPattern = pRightTrimPattern;
        doNotTrim = pDoNotTrim;
        rightPadString = pRightPadString;
    }
    
    /** Holds value of property fromChar. */
    private int fromChar;
    
    /** Holds value of property toChar. */
    private int toChar;
    
    /** Holds value of property property. */
    private String property;
    
    /** Holds value of property method. */
    private Method method;
    
    /** Holds value of property dateFormatPattern. */
    private String dateFormatPattern;
    
    /** Holds value of property leftTrimPattern. */
    private String leftTrimPattern;
    
    /** Holds value of property rightTrimPattern. */
    private String rightTrimPattern;
    
    /** Holds value of property doNotTrim. */
    private boolean doNotTrim = false;
    
    /** Holds value of property rightPadString. */
    private String rightPadString;
    
    /** Getter for property fromChar.
     * @return Value of property fromChar.
     */
    public int getFromChar() {
        return this.fromChar;
    }
    
    
    /** Getter for property toChar.
     * @return Value of property toChar.
     */
    public int getToChar() {
        return this.toChar;
    }
    
    
    /** Getter for property property.
     * @return Value of property property.
     */
    public String getProperty() {
        return this.property;
    }
    
    
    /** Getter for property method.
     * @return Value of property method.
     */
    Method getMethod() {
        return this.method;
    }
    
    /** Setter for property method.
     * @param method New value of property method.
     */
    void setMethod(Method method) {
        this.method = method;
    }
    
    /** Getter for property dateFormatPattern.
     * @return Value of property dateFormatPattern.
     */
    public String getDateFormatPattern() {
        return this.dateFormatPattern;
    }
    
    
    /** Getter for property leftTrimPattern.
     * @return Value of property leftTrimPattern.
     */
    public String getLeftTrimPattern() {
        return this.leftTrimPattern;
    }
    
    /** Getter for property rightTrimPattern.
     * @return Value of property rightTrimPattern.
     */
    public String getRightTrimPattern() {
        return this.rightTrimPattern;
    }
    
    public boolean isDoNotTrim() {
        return this.doNotTrim;
    }
    
    /** Getter for property rightPadString.
     * @return Value of property rightPadString.
     *
     */
    public String getRightPadString() {
        return this.rightPadString;
    }
    
    /** Setter for property rightPadString.
     * @param rightPadString New value of property rightPadString.
     *
     */
    public void setRightPadString(String rightPadString) {
        this.rightPadString = rightPadString;
    }
    
}

