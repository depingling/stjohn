package com.cleanwise.service.api.util;

/*
 * NDComparable.java
 *
 * Created on November 28, 2001, 6:14 PM
 */
import java.lang.reflect.Method;
import java.util.Comparator;
import org.apache.log4j.Logger;

/**
 *
 */
public class BeanComparator implements Comparator
{
    private static final Logger log = Logger.getLogger(BeanComparator.class);
    private String [] methodNames = null;
    
    /**
     * Constructor
     * @param methodNames String []  Array of method names returning values 
     * to be used for comparison
     *
     */
    public BeanComparator( String [] methodNames )
    {
        this.methodNames = methodNames;
    }
    
    /**
     * Overrides Object equals method
     * This method compares 2 objects based on values returned by methods 
     * passed to this object in the constructor. 
     * To invoke a method on the result of another method, pass method names 
     * as single string separated by "-"
     * For example passing string "getProduct-getCostCenterName" as methodName, 
     * will result in invoking getProduct().getCostCenterName().
     * If values returned by the properties are Comparable and not null 
     * use val1.compareTo(val2) Otherwise convert them to string and compare
     *
     * @param obj Object
     * @param obj1 Object
     */
    public int compare(java.lang.Object obj, java.lang.Object obj1)
    {
        int retValue = 0;
        try
        {
            if ( obj == obj1 || obj.equals(obj1) ) return 0;
            
            String str1 = "";
            String str2 = "";
            for ( int i = 0 ; i < methodNames.length; i++ )
            {
            	Object propertyVal1 = getPropertyVal(obj, methodNames[i]);;
                Object propertyVal2 = getPropertyVal(obj1, methodNames[i]);
            	
                if ( (propertyVal1 instanceof Comparable) && (propertyVal2 instanceof Comparable)
                    && (propertyVal1 != null) && (propertyVal2 != null) )
                {
                    Comparable comp1 = (Comparable)propertyVal1;
                    Comparable comp2 = (Comparable)propertyVal2;
                    
                    int retVal = comp1.compareTo(comp2);
                    if ( retVal == 0 ) continue;
                    else
                    {
                        retValue = retVal;
                        break;
                    }
                }
                else
                {
                    str1 = propertyVal1 + "";
                    str2 = propertyVal2 + "";
                    if ( str1.equals(str2) )
                        continue;
                    else
                    {
                        retValue = str1.compareTo(str2);
                    }
                } 
            } // end for
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        return retValue;
    }
    
    /**
     * Helper method for objects of type java.lang.Comparable
     *
     * @param obj1 java.lang.Comparable
     * @param obj2 java.lang.Comparable
     *
     * @return int difference
     */
    private int compare(java.lang.Comparable obj1, java.lang.Comparable obj2)
    {
        return obj1.compareTo(obj2);
    }
    /**
     * Invokes the method with method name "methodName" on object "obj" and 
     * returns the result of the invoked method.
     * To invoke a method on the result of another method, pass method names 
     * as single string separated by "-"
     * For example passing string "getProduct-getCostCenterName" as methodName, 
     * will result in invoking getProduct().getCostCenterName().
     * 
     * @param obj - object on which to invoke the method.
     * @param methodName - method name of the method to invoke.
     * 
     */
    private Object getPropertyVal(Object obj, String methodName) throws Exception{
    	 Class claz = null;
    	 String[] methodsInString = null;
    	 Object methodResult = obj;
    	 
    	 if(methodName.indexOf("-") > 0){
    	 	methodsInString = methodName.split("-");
    	 } else {
    		 methodsInString = new String[1];
    		 methodsInString[0] = methodName;
    	 }
    	 for(int i =0; i < methodsInString.length; i++){
    		 claz = methodResult.getClass();
    		 Method method = claz.getMethod(methodsInString[i], new Class[0]);
    		 methodResult = method.invoke(methodResult, new Object[0]);
    	}
    	return methodResult;
     }
    
    /**
     * Unit Test
     */
    public static void main(String argv)
    {
        java.io.File file1 = new java.io.File("max");
        java.io.File file2 = new java.io.File("amc");
        BeanComparator comparator = new BeanComparator(new String[]
        {"getName"});
        log.info(comparator.compare(file1, file2));
    }
    
}
