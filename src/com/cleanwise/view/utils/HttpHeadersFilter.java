package com.cleanwise.view.utils;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

/**
 * Taken from:
 * http://onjava.com/pub/a/onjava/2004/03/03/filters.html
 *
 * Adds in any name value pairs as http response headers to all configured requests.
 * example to produce:
HEADER: Some value
 *
 *You would add the following to the web.xml:
 <filter>
  <filter-name>HttpHeadersFilter</filter-name>
  <filter-class>
   com.cleanwise.view.utils.HttpHeadersFilter</filter-class>
  <init-param>
    <param-name>HEADER</param-name>
    <param-value>Some value</param-value>
  </init-param>
</filter>
 */
public class HttpHeadersFilter implements Filter {
  FilterConfig fc;
  public void doFilter(ServletRequest req,
                       ServletResponse res,
                       FilterChain chain)
                       throws IOException,
                              ServletException {
    HttpServletResponse response =
      (HttpServletResponse) res;
    // set the provided HTTP response parameters
    for (Enumeration e=fc.getInitParameterNames();
        e.hasMoreElements();) {
      String headerName = (String)e.nextElement();
      response.addHeader(headerName,
                 fc.getInitParameter(headerName));
    }
    // pass the request/response on
    chain.doFilter(req, response);
  }
  public void init(FilterConfig filterConfig) {
    this.fc = filterConfig;
  }
  public void destroy() {
    this.fc = null;
  }
}
