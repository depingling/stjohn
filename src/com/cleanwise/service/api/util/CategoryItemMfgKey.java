/**
 * 
 */
package com.cleanwise.service.api.util;

/**
 * @author ssharma
 *
 */
public class CategoryItemMfgKey {

	int itemId;
	String itemName;
	int mfgId;
	String mfgName;
	/**
	 * @return the itemId
	 */
	public final int getItemId() {
		return itemId;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public final void setItemId(int itemId) {
		this.itemId = itemId;
	}
	/**
	 * @return the itemName
	 */
	public final String getItemName() {
		return itemName;
	}
	/**
	 * @param itemName the itemName to set
	 */
	public final void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * @return the mfgId
	 */
	public final int getMfgId() {
		return mfgId;
	}
	/**
	 * @param mfgId the mfgId to set
	 */
	public final void setMfgId(int mfgId) {
		this.mfgId = mfgId;
	}
	/**
	 * @return the mfgName
	 */
	public final String getMfgName() {
		return mfgName;
	}
	/**
	 * @param mfgName the mfgName to set
	 */
	public final void setMfgName(String mfgName) {
		this.mfgName = mfgName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + itemId;
		result = prime * result
				+ ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result + mfgId;
		result = prime * result + ((mfgName == null) ? 0 : mfgName.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryItemMfgKey other = (CategoryItemMfgKey) obj;
		if (itemId != other.itemId)
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (mfgId != other.mfgId)
			return false;
		if (mfgName == null) {
			if (other.mfgName != null)
				return false;
		} else if (!mfgName.equals(other.mfgName))
			return false;
		return true;
	}
	
	
}
