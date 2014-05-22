package com.cleanwise.view.utils;

import java.io.*;
import javax.servlet.*;

/**
 * Set character encoding so that all the jsp page will have same encoding without have to set it in jsp file.
 *
 *You would add the following to the web.xml:
 <filter>
  <filter-name>HttpCharEncodingFilter</filter-name>
  <filter-class>
   com.cleanwise.view.utils.HttpCharEncodingFilter</filter-class>
  <init-param>
    <param-name>encodingType</param-name>
    <param-value>UTF-8</param-value>
  </init-param>
 </filter>
 */
public class HttpCharEncodingFilter implements Filter {
  FilterConfig fc;
  public void doFilter(ServletRequest req,
                       ServletResponse res,
                       FilterChain chain)
                       throws IOException,
                              ServletException {    
	  req.setCharacterEncoding("UTF-8"); 
	  res.setCharacterEncoding("UTF-8"); 

	  // pass the request/response on
	  chain.doFilter(req, res);
  }
  public void init(FilterConfig filterConfig) {
    this.fc = filterConfig;
  }
  public void destroy() {
    this.fc = null;
  }
}
