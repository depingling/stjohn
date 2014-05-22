package com.cleanwise.service.api.cachecos;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CachecosOffice {      

   public static final String JNDI_MANAGER_NAME = "CachecosManager";                                               

   public static CachecosManager getCachecosManager() {
       try {
           InitialContext ctx = new InitialContext();
           return (CachecosManager) ctx.lookup(JNDI_MANAGER_NAME);
       } catch (Exception e) {
           return null;
       }
   }
}
