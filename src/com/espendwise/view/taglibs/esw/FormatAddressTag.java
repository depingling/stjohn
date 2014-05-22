package com.espendwise.view.taglibs.esw;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.espendwise.service.api.util.AddressFormat;



public class FormatAddressTag extends TagSupport {
	private String name;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String city;
    private String state;
    private String postalCode;
    private String addressFormat;
    private String country;
    
    /**
     * @return name
     */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	/**
	 * @return address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return address3
	 */
	public String getAddress3() {
		return address3;
	}

	/**
	 * @param address3 the address3 to set
	 */
	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	/**
	 * @return address4
	 */
	public String getAddress4() {
		return address4;
	}

	/**
	 * @param address4 the address4 to set
	 */
	public void setAddress4(String address4) {
		this.address4 = address4;
	}
	/**
	 * @return city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	/**
	 * @return country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * @return addressFormat
	 */
	public String getAddressFormat() {
		return addressFormat;
	}

	/**
	 * @param addressFormat the addressFormat to set
	 */
	public void setAddressFormat(String addressFormat) {
		this.addressFormat = addressFormat;
	}


	public int doStartTag() throws JspException{
		try
		{
			JspWriter out = pageContext.getOut();
			AddressFormat fmt = new AddressFormat(name, address1, address2, address3, address4, city, state, 
					postalCode, country, addressFormat);
			out.print(fmt.formatAsHTML());
		}
		catch(IOException ioe)
		{
			throw new JspException(ioe);	
		}
		return SKIP_BODY;
	}
}