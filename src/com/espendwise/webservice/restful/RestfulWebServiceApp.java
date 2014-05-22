package com.espendwise.webservice.restful;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


public class RestfulWebServiceApp extends Application {

   private Set<Object> singletons = new HashSet<Object>();
   private Set<Class<?>> empty = new HashSet<Class<?>>();

    public RestfulWebServiceApp() {
        singletons.add(new AndroidRestService());
        singletons.add(new OrderRunRestService());
    }

   @Override
   public Set<Class<?>> getClasses() {
      return empty;
   }

   @Override
   public Set<Object> getSingletons() {
      return singletons;
   }
}
