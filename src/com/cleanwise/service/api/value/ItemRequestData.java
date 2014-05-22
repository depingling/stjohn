package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>ItemRequestData</code>
 */
public class ItemRequestData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -8397428485667195052L;

  private int mItemId;// SQL type:NUMBER

  /**
   * Constructor. 
   */
  private ItemRequestData() {}

  /**
   * Constructor. 
   */
   public ItemRequestData(int parm1)
   {
      mItemId = parm1;
      
   }

  /**
   * Creates a new ItemRequestData
   *
   * @return
   *  Newly initialized ItemRequestData object.
   */
   public static ItemRequestData createValue () 
   {
     ItemRequestData valueData = new ItemRequestData();

     return valueData;
   }

  /**
   * Returns a String representation of the value object
   *
   * @return
   *  The String representation of this ItemRequestData object
   */
   public String toString()
   {
     return "[" + "ItemId=" + mItemId + "]";
   }
   
   /**
   * Sets the ItemId field.
   *
   * @param value
   *  int to use to update the field.
   */
   public void setItemId(int px){
      mItemId=px;
   }
   /**
   * Retrieves the ItemId field.
   *
   * @return
   *  int containing the ItemId field.
   */
   public int getItemId(){
      return mItemId;
   }
}
