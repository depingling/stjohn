package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;

import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.BigDecimal;

/**
 *  <code>BillToData</code> is a value object that aggregates all the value
 *  objects that make up a Site.
 *
 *@author     Dvieira
 *@created    November 4, 2004
 */
public class BillToData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 3175658172709271750L;
    private BusEntityData mBusEntity;
    private BusEntityData mAccountBusEntity;
    private AddressData mBillToAddress;


    /**
     *  Creates a new <code>BillToData</code> instance. This should be a private
     *  interface callable only from the Site session bean. (Unfortunately I do
     *  not know of a way to do that)
     *
     *@param  pBusEntity         Description of the Parameter
     *@param  pAccountBusEntity  Description of the Parameter
     *@param  pBillToAddress     Description of the Parameter
     */
    public BillToData(BusEntityData pBusEntity,
            BusEntityData pAccountBusEntity,
            AddressData pBillToAddress
            ) {
        this.mBusEntity = (pBusEntity != null)
                 ? pBusEntity : BusEntityData.createValue();
        this.mAccountBusEntity = (pAccountBusEntity != null)
                 ? pAccountBusEntity : BusEntityData.createValue();
        this.mBillToAddress = (pBillToAddress != null)
                 ? pBillToAddress : AddressData.createValue();
    }


    /**
     *  <code>createValue</code>, instantiate a site data object where all
     *  values a initialized to some default, non-null value.
     *
     *@return    a <code>BillToData</code> value
     */
    public static BillToData createValue() {
        return new BillToData(null, null, null);
    }


    /**
     *  Describe <code>getBusEntity</code> method here.
     *
     *@return    a <code>BusEntityData</code> value
     */
    public BusEntityData getBusEntity() {
        return mBusEntity;
    }


    /**
     *  Gets the active attribute of the BillToData object
     *
     *@return    The active value
     */
    public boolean isActive() {

        if (getBusEntity() == null) {
            return false;
        }

        String status = getBusEntity().getBusEntityStatusCd();

        if (null == status) {
            return false;
        }

        return status.equals(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
    }


    /**
     *  Describe <code>getAccountBusEntity</code> method here.
     *
     *@return    a <code>BusEntityData</code> value
     */
    public BusEntityData getAccountBusEntity() {
        return mAccountBusEntity;
    }


    /**
     *  Describe <code>getBillToAddress</code> method here.
     *
     *@return    an <code>AddressData</code> value
     */
    public AddressData getBillToAddress() {
        return mBillToAddress;
    }


    /**
     *  Sets the billToAddress attribute of the BillToData object
     *
     *@param  v  The new billToAddress value
     */
    public void setBillToAddress(AddressData v) {
        mBillToAddress = v;
    }


    /**
     *  Returns a String representation of the value object
     *
     *@return    The String representation of this BillToData object
     */
    public String toString() {
        return "[" +
                "BusEntity=" + mBusEntity +
                ", AccountBusEntity=" + mAccountBusEntity +
                ", BillToAddress=" + mBillToAddress +
                "]";
    }

    public String toSelectString() {
	String s = mBusEntity.getShortDesc() ; 

	if ( null !=  mBillToAddress.getAddress1()
	     && mBillToAddress.getAddress1().trim().length() > 0 
	     ) {
	    s += ", " + mBillToAddress.getAddress1().trim() ;
	}
	if ( null !=  mBillToAddress.getAddress2() 
	     && mBillToAddress.getAddress2().trim().length() > 0 
	     ) {
	    s += ", " + mBillToAddress.getAddress2().trim() ;
	}
	/*
	if ( null !=  mBillToAddress.getAddress3() 
	     && mBillToAddress.getAddress3().length() > 0 
	     ) {
	    s += ", " + mBillToAddress.getAddress3() ;
	}
	if ( null !=  mBillToAddress.getAddress4() 
	     && mBillToAddress.getAddress4().length() > 0 
	     ) {
	    s += ", " + mBillToAddress.getAddress4() ;
	}
	*/
	if ( null !=  mBillToAddress.getCity() 
	     && mBillToAddress.getCity().trim().length() > 0 
	     ) {
	    s += ", " + mBillToAddress.getCity().trim();
	}
	if ( null !=  mBillToAddress.getStateProvinceCd() 
	     && mBillToAddress.getStateProvinceCd().trim().length() > 0 
	     ) {
	    s += ", " + mBillToAddress.getStateProvinceCd().trim();
	}
	if ( null !=  mBillToAddress.getPostalCode() 
	     && mBillToAddress.getPostalCode().trim().length() > 0 
	     ) {
	    s += ", " + mBillToAddress.getPostalCode().trim() ;
	}

        return s;
    }

    public String toTableRow() {
	String s = "<tr>";
	s += "<td>"+mBusEntity.getShortDesc() +"</td>"; 

	if ( null !=  mBillToAddress.getAddress1()
	     && mBillToAddress.getAddress1().trim().length() > 0 
	     ) {
	    s += "<td> " + mBillToAddress.getAddress1().trim() +"</td>";
	}
	s+= "</tr>";

	if ( null !=  mBillToAddress.getAddress2() 
	     && mBillToAddress.getAddress2().trim().length() > 0 
	     ) {
	    s += "<tr><td></td><td> " + mBillToAddress.getAddress2().trim() +"</td></tr>";
	}
	
	if ( null !=  mBillToAddress.getAddress3() 
	     && mBillToAddress.getAddress3().length() > 0 
	     ) {
	    s += "<tr><td></td><td> " + mBillToAddress.getAddress3().trim() +"</td></tr>";
	}
	if ( null !=  mBillToAddress.getAddress4() 
	     && mBillToAddress.getAddress4().length() > 0 
	     ) {
	    s += "<tr><td></td><td> " + mBillToAddress.getAddress4().trim() +"</td></tr>";
	}
	
	s+="<tr><td></td>";
	if ( null !=  mBillToAddress.getCity() 
	     && mBillToAddress.getCity().trim().length() > 0 
	     ) {
	    s += "<td> " + mBillToAddress.getCity().trim() +"</td>";
	}
	if ( null !=  mBillToAddress.getStateProvinceCd() 
	     && mBillToAddress.getStateProvinceCd().trim().length() > 0 
	     ) {
	    s += "<td> " + mBillToAddress.getStateProvinceCd().trim() +"</td>";
	}
	if ( null !=  mBillToAddress.getPostalCode() 
	     && mBillToAddress.getPostalCode().trim().length() > 0 
	     ) {
	    s += "<td> " + mBillToAddress.getPostalCode().trim() +"</td>";
	}
	s += "</tr>";

        return s;
    }


    /**
     *  Gets the billToId attribute of the BillToData object
     *
     *@return    The billToId value
     */
    public int getBillToId() {
        if (null == mBusEntity) {
            return 0;
        }
        return mBusEntity.getBusEntityId();
    }

    public String getBillToName() {
        if (null == mBusEntity) {
            return "";
        }
        return mBusEntity.getShortDesc();
    }

    /**
     *  Gets the accountId attribute of the BillToData object
     *
     *@return    The accountId value
     */
    public int getAccountId() {
        if (null == mAccountBusEntity) {
            return 0;
        }
        return mAccountBusEntity.getBusEntityId();
    }

}


