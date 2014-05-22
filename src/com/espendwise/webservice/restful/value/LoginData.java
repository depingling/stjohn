package com.espendwise.webservice.restful.value;

import java.io.Serializable;

public class LoginData implements Serializable, Cloneable {
	private static final long serialVersionUID = -5660836848901633035L;
	private String userName;
    private String password;
    private boolean tracking;
    private String token;
    private String result;

    public LoginData() {
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getTracking() {
		return tracking;
	}
	
	public void setTracking(boolean tracking) {
		this.tracking = tracking;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "userName: " +  this.userName +
	           ", password: " + this.password +
	           ", token:" + this.token + 
	           ", tracking:" + this.tracking + 
	           ", result: " + this.result;
	}

	@Override
	public Object clone(){
		// TODO Auto-generated method stub
		if (this instanceof LoginData) {
		LoginData myClone = new LoginData();
		myClone.userName = userName;
		myClone.password = password;
		myClone.token = token;
		myClone.tracking = tracking;
		return myClone;
		} else {
	        try {
	            return super.clone();
	        } catch (CloneNotSupportedException cnse) {
	            throw new RuntimeException(cnse);
	        }
		}
	}

	@SuppressWarnings("rawtypes")
	public Object expand(Class clazz) {
		Object exp = null;
		Class superClass = clazz.getSuperclass();
		if (superClass.equals(this.getClass())) {
			try {
				exp = clazz.newInstance();
				
				((LoginData)exp).userName = this.userName;
				((LoginData)exp).password = this.password;
				((LoginData)exp).token = this.token;
				((LoginData)exp).tracking = this.tracking;
				
		      } catch (InstantiationException e) {
		            throw new RuntimeException(e);
		      } catch (IllegalAccessException e) {
		            throw new RuntimeException(e);
		      }    
		}
		return exp;
	}
}
