<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text"/>
<xsl:template match="beans">
/* DO NOT EDIT - Generated code from XSL file APIAcess.xsl */

package com.cleanwise.service.api;

/**
 * Title:        APIAcess
 * Description:  Access to EJB API
 * Purpose:      
 * Copyright:    Copyright (c) 2003
 * Company:      Cleanwise, Inc.
 * @author       Generated Code from XSL file APIAcess.xsl
 */

import javax.ejb.*;
import javax.naming.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;

public class APIAccess {

    protected InitialContext ctx = null;
    protected static Object lock = new Object();
    protected static APIAccess apiAccess = null;
    
     /**
     *  Constructor for the APIAccess object
     *
     *@exception  javax.naming.NamingException  Description of Exception
     */
    public APIAccess() throws javax.naming.NamingException {
        ctx = new InitialContext();
    }

     /**
     *  Retrieve access to the singleton APIAccess object.
     *
     *@return                                   The APIAccess value
     *@exception  javax.naming.NamingException  Description of Exception
     *@throws  javax.namingNamingException      Thrown when the EJBFactory
     *      object can not be initialized.
     */
    public static APIAccess getAPIAccess()
             throws javax.naming.NamingException {
        synchronized (lock) {
            if (null == apiAccess) {
                apiAccess = new APIAccess();
            }
            return apiAccess;
        }
    }

    <xsl:for-each select="bean">
    private <xsl:value-of select="@javaname"/>Home m<xsl:value-of select="@javaname"/>Home;

    /**
     *  Obtains a reference to the remote interface for the <xsl:value-of select="@javaname"/>.
     *
     *@return                                The <xsl:value-of select="@javaname"/>API value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public <xsl:value-of select="@javaname"/> get<xsl:value-of select="@javaname"/>API()
             throws APIServiceAccessException {
        <xsl:value-of select="@javaname"/> rtn = null;
        try {
            if (null == m<xsl:value-of select="@javaname"/>Home) {
                if (null == ctx) {
                    ctx = new InitialContext();
                }
                m<xsl:value-of select="@javaname"/>Home= (<xsl:value-of select="@javaname"/>Home) ctx.lookup(JNDINames.<xsl:value-of select="@JINDIname"/>_EJBHOME);
            }
            rtn = m<xsl:value-of select="@javaname"/>Home.create();
        }
        catch (Exception e) {
            try{
                ctx = new InitialContext();
                m<xsl:value-of select="@javaname"/>Home = (<xsl:value-of select="@javaname"/>Home) ctx.lookup(JNDINames.<xsl:value-of select="@JINDIname"/>_EJBHOME);
                rtn = m<xsl:value-of select="@javaname"/>Home.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }
    </xsl:for-each>
}
</xsl:template>
</xsl:stylesheet>