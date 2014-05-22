package com.cleanwise.service.api.util;
/*
 * The class is used for catalogues identification by a short and long name
 */
public class CategoryKey {
    public CategoryKey(String shortName, String adminName) {
        _shortName = shortName;
        _adminName = adminName;
    }
    
    public CategoryKey(String shortName) {
        this(shortName, "");
    }
    
    public CategoryKey() {
        this("", "");
    }
    
    public String GetShortName() {
        return _shortName;
    }
    
    public String GetAdminName() {
        return _adminName;
    }
    
    public void SetShortName(String shortName) {
        _shortName = shortName;
    }
    
    public void SetAdminName(String adminName) {
        _adminName = adminName;
    }
    
    public int hashCode() {
        if (_shortName == null && _adminName == null) {
            return 0;
        }
        if (_shortName != null && _adminName == null) {
            return 15 * _shortName.hashCode();
        }
        if (_shortName == null && _adminName != null) {
            return _adminName.hashCode();
        }
        return 15 * _shortName.hashCode() + _adminName.hashCode();
    }
    
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof CategoryKey) {
            CategoryKey otherKey = (CategoryKey)obj;
            CategoryComparatorByKey categoryComparator = new CategoryComparatorByKey();
            return (categoryComparator.compare(this, otherKey) == 0);
        }
        return false;
    }
    
    public String toString() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append((_shortName == null) ? "null" : _shortName);
        strBuff.append(",");
        strBuff.append((_adminName == null) ? "null" : _adminName);
        return strBuff.toString();
    }
    
    private String _shortName;
    private String _adminName;
}
