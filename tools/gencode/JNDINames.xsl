<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text"/>
<xsl:template match="beans">
/* DO NOT EDIT - Generated code from XSL file JNDINames.xsl */

package com.cleanwise.service.api.util;

/**
 * This class is the central location to store the internal 
 * JNDI names of various entities. Any change here should 
 * also be reflected in the deployment descriptors. 
 *
 * Copyright:   Copyright (c) 2003
 * Company:     Cleanwise, Inc.
 * @author      Generated Code from XSL file APIAcess.xsl
 *
 */

import java.util.*;
import javax.ejb.*;
import javax.naming.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;

public interface JNDINamesSuper {
  // 
  // JNDI names of EJB home objects
  //
    <xsl:for-each select="bean">
    public static final String <xsl:value-of select="@JINDIname"/>_EJBHOME =
    "cleanwise/<xsl:value-of select="@javaname"/>";
    </xsl:for-each>
}
</xsl:template>
</xsl:stylesheet>