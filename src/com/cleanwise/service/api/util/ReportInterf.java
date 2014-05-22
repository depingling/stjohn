/**
  * Title:        ReportInterf
  * Description:  Interface to support report development
  * @author       Natalia Guschina
*/

package com.cleanwise.service.api.util;

public interface ReportInterf {
    /**
     *Should return some report's properties.
     */
    public String getFileName();
    public String getExt();
    public boolean isSpecial();
    public String getUserTypesAutorized();

}
