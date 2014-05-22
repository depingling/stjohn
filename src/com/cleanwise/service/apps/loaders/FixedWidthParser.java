/*
 * TextParser.java
 *
 * Created on June 25, 2002, 12:52 PM
 */

package com.cleanwise.service.apps.loaders;
import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.apps.ClientServicesAPI;
import com.cleanwise.service.apps.dataexchange.InterchangeSuper;
/**
 * Given an object name and a valid mapping will map an inbound flat file to 
 * a java beans properties.  It will currently hadle mapping to java types String, 
 * int and Integer. 
 * @see FixedWidthParserCharRanges
 * @author  bstevens
 */
public abstract class FixedWidthParser extends ClientServicesAPI {//extends InterchangeInboundSuper
    private int currentLineNumber = 0;
    private ArrayList mFilters = new ArrayList();
    //private FixedWidthParserCharRange[] mCharRanges;
    //private String mClassName;
    //prefix for a setter as a char array (as defined in the bean specification)
    static final char[] setPrefix={'s','e','t'};
    
    /** Holds value of property dateFormatPattern. */
    private String dateFormatPattern = "MMddyyyy";
    
    /** Holds value of property workingFile. */
    private File workingFile;
    
    /** Holds value of property workingLine. */
    private String workingLine;
    
  //default fromat value
    
     /*
      *Constructs a set methdod name based on the passed in string according
      *to the bean specification.
      *As of 6/25/02 this will only handle properties whose first letter is
      *a lowercase value.
      */
    private String constuctMethodName(String pStr){
        char[] lChar = pStr.toCharArray();
        lChar[0]=Character.toUpperCase(lChar[0]);
        char[] lTotChar = new char[lChar.length + setPrefix.length];
        //create the return array
        System.arraycopy(setPrefix,0,lTotChar,0,setPrefix.length);
        System.arraycopy(lChar,0,lTotChar,setPrefix.length,lChar.length);
        return new String(lTotChar);
    }
    
    
    
    /**
     *Actually sets up the object, making setX calls into the object bassed on the
     *property file that is defined for this component.
     *XXX:
     *The configuration that takes place should be intelligent enough to only
     *try to set the public property, not the private one.  Currently this is
     *unsupported.
     *Also the full bean name specification is unsupported at this time,
     *@see constructMethodName for deatils.
     *@throws ClassNotFoundException if class name given during instantiation is not valid
     *@throws InstantiationException if any problems ocured creating object from class
     *@throws IllegalAccessException if setX method is not accessable (private, package private etc.)
     *@throws InvocationTargetException if it could not call the setX method for any reason
     */
    protected void parse(String pFileName) throws 
        IOException, ClassNotFoundException, InstantiationException, 
        IllegalAccessException, InvocationTargetException{
            parse(new File(pFileName));
    }
    
    /**
     *Returns the current line number in the file that is being parsed.
     */
    protected int getCurrentLineNumber(){
        return currentLineNumber;
    }
    
    protected void parse(File pFile) throws 
    IOException, ClassNotFoundException, InstantiationException, 
    IllegalAccessException, InvocationTargetException{
    	//set the working file variable, so sub classes have access to it
        setWorkingFile(pFile);
        parse(new FileInputStream(pFile));
    }
    
    /**
     *Actually sets up the object, making setX calls into the object bassed on the
     *property file that is defined for this component.
     *XXX:
     *The configuration that takes place should be intelligent enough to only
     *try to set the public property, not the private one.  Currently this is
     *unsupported.
     *Also the full bean name specification is unsupported at this time,
     *@see constructMethodName for deatils.
     *@throws ClassNotFoundException if class name given during instantiation is not valid
     *@throws InstantiationException if any problems ocured creating object from class
     *@throws IllegalAccessException if setX method is not accessable (private, package private etc.)
     *@throws InvocationTargetException if it could not call the setX method for any reason
     */
    protected void parse(InputStream pInputStream) throws 
        IOException, ClassNotFoundException, InstantiationException, 
        IllegalAccessException, InvocationTargetException{
        
        
        if (mFilters.size() == 0){
            throw new IllegalStateException("must add at least one filter, or empty filter before parsing file");
        }
        //cache all of the method objects into the mCharRanges object
        for (int k=0,len=mFilters.size();k<len;k++){
            Filter lFilter = (Filter) mFilters.get(k);
            Class lClass = Thread.currentThread().getContextClassLoader().loadClass(lFilter.getClassName());
            Method[] m = lClass.getMethods();
            for(int i=0;i<lFilter.getCharRanges().length;i++){
                int numMatched = 0;
                for(int j=0;j<m.length;j++){
                    if (lFilter.getCharRanges()[i] == null){
                        throw new NullPointerException("Empty char range at index: "+i);
                    }
                    String methName = constuctMethodName(lFilter.getCharRanges()[i].getProperty());
                    if (methName == null){
                        throw new NullPointerException("Could not construct method name for configured char range: " + lFilter.getCharRanges()[i]);
                    }
                    if(methName.equals(m[j].getName())){
                        lFilter.getCharRanges()[i].setMethod(m[j]);
                        numMatched = numMatched + 1;
                    }
                }
                if (numMatched > 1){
                    throw new IllegalArgumentException("Class: " + lFilter.getClassName() + " has multiple matching method invocation "+
                    "for the property: " + lFilter.getCharRanges()[i].getProperty());
                } else if (numMatched < 1){
                    throw new IllegalArgumentException("Class: " + lFilter.getClassName() + " has no matching method invocation "+
                    "for the property: " + lFilter.getCharRanges()[i].getProperty());
                }
            }
        }
        
        //now parse out the file into its objects/methods as defined by mCharRanges
        Reader scratchRdr = new InputStreamReader(pInputStream);
        BufferedReader rdr = new BufferedReader(scratchRdr);
        String curLine = rdr.readLine();
        currentLineNumber = currentLineNumber + 1;
        while (curLine != null){
            //set the workingLine property if subclasses need to access it.
            this.setWorkingLine(curLine);
            boolean foundFilter = false;
            for (int k=0,len=mFilters.size();k<len;k++){
                Filter lFilter = (Filter) mFilters.get(k);
                //if this is an empty filter, or the filter matches populate object
                //and call proces line with the populated object
                if(!Utility.isSet(lFilter.getFilterValue()) || curLine.substring(lFilter.getFrom(),lFilter.getTo()).equals(lFilter.getFilterValue())){
                    boolean ignore = false;
                    for(int i=0,len2=lFilter.getIgnoreChunks().size();i<len2;i++){
                        FixedWidthFieldFilterDef fd = (FixedWidthFieldFilterDef) lFilter.getIgnoreChunks().get(i);
                        if(curLine.substring(fd.getFrom(),fd.getTo()).equals(fd.getValue())){
                            ignore = true;
                            break;
                        }
                    }
                    if(!ignore){
                        foundFilter = true;
                        Object lObj = Utility.createInstanceFromString(lFilter.getClassName());

                        for(int i=0;i<lFilter.getCharRanges().length;i++){
                            FixedWidthFieldDef lDef = lFilter.getCharRanges()[i];

                            String lChunk = getTextFromFieldDef(curLine,lDef);

                            String lDatePattern;
                            //use the date pattern provided with the field def, or get the 
                            //default date pattern to be used for this file.
                            if (lDef.getDateFormatPattern() == null){
                                lDatePattern = getDateFormatPattern();
                            }else{
                                lDatePattern = lDef.getDateFormatPattern();
                            }
                            Utility.populateJavaBeanSetterMethod(lObj,lDef.getMethod(),lChunk,lDatePattern);
                            
                        }
                        processLine(lObj);
                    }
                }
            }
	    //usupported line, let subclasses know
            if (!foundFilter){
               processUnfilteredLine(curLine);
            }

            curLine = rdr.readLine();
            currentLineNumber = currentLineNumber + 1;
        }
        postProcessFile();
        return;
    }
    
    private String getTextFromFieldDef(String pCurLine,FixedWidthFieldDef pDef){
        String lChunk = pCurLine.substring(pDef.getFromChar(),pDef.getToChar());
        //Left trim and right trim the values if these are set
        //Don't use Utility.isSet() here, " " is a valid trim val
        if (pDef.getLeftTrimPattern() != null && pDef.getLeftTrimPattern().length()>0){
            lChunk = Utility.trimLeft(lChunk,pDef.getLeftTrimPattern());
        }
        if (pDef.getRightTrimPattern() != null && pDef.getRightTrimPattern().length()>0){
            lChunk = Utility.trimRight(lChunk,pDef.getRightTrimPattern());
        }
        if (!pDef.isDoNotTrim()){
            lChunk = lChunk.trim();
        }
        if(pDef.getRightPadString() != null){
            lChunk = lChunk + pDef.getRightPadString();
        }
        return lChunk;
    }
    
    protected abstract void  processLine(Object pObj);
    
    protected abstract void  processUnfilteredLine(String pLine);
    
    protected abstract void  postProcessFile();
    
    
    public Filter addFilter(FixedWidthFieldDef[] pCharRanges,String pClassName,int pFrom, int pTo,String pFilterValue){
        Filter filter = new Filter(pCharRanges,pClassName,pFrom,pTo,pFilterValue);
        mFilters.add(filter);
        return filter;
    }
    
    public void addEmptyFilter(FixedWidthFieldDef[] pCharRanges,String pClassName){
        addFilter(pCharRanges,pClassName,0,0,null);
    }
    
    /** Getter for property dateFormatPattern.
     * @return Value of property dateFormatPattern.
     */
    public String getDateFormatPattern() {
        return this.dateFormatPattern;
    }
    
    /** Setter for property dateFormatPattern.
     * @param dateFormatPattern New value of property dateFormatPattern.
     */
    public void setDateFormatPattern(String dateFormatPattern) {
        this.dateFormatPattern = dateFormatPattern;
    }
    
    /** Getter for property workingFile.
     * @return Value of property workingFile.
     */
    protected File getWorkingFile() {
        return this.workingFile;
    }
    
    /** Setter for property workingFile.
     * @param workingFile New value of property workingFile.
     */
    protected void setWorkingFile(File workingFile) {
        this.workingFile = workingFile;
    }
    
    /** Getter for property workingLine.
     * @return Value of property workingLine.
     */
    protected String getWorkingLine() {
        return this.workingLine;
    }
    
    /** Setter for property workingLine.
     * @param workingLine New value of property workingLine.
     */
    protected void setWorkingLine(String workingLine) {
        this.workingLine = workingLine;
    }
    
    public class Filter {
        private Filter(FixedWidthFieldDef[] pCharRanges,String pClassName,int pFrom, int pTo,String pFilterValue){
            this.charRanges = pCharRanges;
            this.className = pClassName;
            this.from = pFrom;
            this.to = pTo;
            this.filterValue = pFilterValue;
        }
        
        /** Holds value of property charRanges. */
        private FixedWidthFieldDef[] charRanges;
        
        /** Holds value of property className. */
        private String className;
        
        /** Holds value of property to. */
        private int to;
        
        /** Holds value of property from. */
        private int from;
        
        /** Holds value of property filterValue. */
        private String filterValue;
        
        /** Holds value of property ignoreChunks. */
        private ArrayList mIgnoreChunks = new ArrayList();
        
        /** Getter for property charRanges.
         * @return Value of property charRanges.
         */
        private FixedWidthFieldDef[] getCharRanges() {
            return this.charRanges;
        }
        
        
        /** Getter for property className.
         * @return Value of property className.
         */
        private String getClassName() {
            return this.className;
        }
        
        
        /** Getter for property to.
         * @return Value of property to.
         */
        private int getTo() {
            return this.to;
        }
        
        
        /** Getter for property from.
         * @return Value of property from.
         */
        private int getFrom() {
            return this.from;
        }
        
        
        /** Getter for property filterValue.
         * @return Value of property filterValue.
         */
        private String getFilterValue() {
            return this.filterValue;
        }
        
        /**
         *Anything in this list will be ignored by the parser.
         */
        public void addIgnoreFilter(int pFrom, int pTo,String pValue){
            FixedWidthFieldFilterDef filter = new FixedWidthFieldFilterDef(pFrom,pTo,pValue);
            mIgnoreChunks.add(filter);
        }
        
        private ArrayList getIgnoreChunks(){
            return mIgnoreChunks;
        }
    }
    
    private class FixedWidthFieldFilterDef {
        
        private FixedWidthFieldFilterDef(int pFrom,int pTo,String pValue){
            to = pTo;
            from = pFrom;
            value = pValue;
        }
        
        /** Holds value of property from. */
        private int from;
        
        /** Holds value of property to. */
        private int to;
        
        /** Holds value of property value. */
        private String value;
        
        /** Getter for property from.
         * @return Value of property from.
         *
         */
        public int getFrom() {
            return this.from;
        }
        
        /** Setter for property from.
         * @param from New value of property from.
         *
         */
        public void setFrom(int from) {
        }
        
        /** Getter for property to.
         * @return Value of property to.
         *
         */
        public int getTo() {
            return this.to;
        }
        
        /** Setter for property to.
         * @param to New value of property to.
         *
         */
        public void setTo(int to) {
        }
        
        /** Getter for property value.
         * @return Value of property value.
         *
         */
        public String getValue() {
            return this.value;
        }
        
        /** Setter for property value.
         * @param value New value of property value.
         *
         */
        public void setValue(String value) {
        }
        
    }
    
}
