/*
 * OutboundTranslatorLogic.java
 *
 * Created on September 8, 2006
 */

package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import javax.servlet.http.*;
import java.io.*;
import com.cleanwise.service.apps.dataexchange.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;

import java.util.Iterator;
import com.cleanwise.service.api.value.TradingPartnerAssocDataVector;
import java.util.HashSet;
import java.util.ArrayList;
import com.cleanwise.service.api.value.TradingPartnerAssocData;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.TradingPartner;

/**
 * The entry point for http requests into the outbound translation/trading partner sub system.
 * @author  vdenega
 */
public class OutboundTranslatorLogic {



  /**
   *Translates a post request using the outbound translation sub system.
   */
  public static void performTranslation(HttpServletRequest request,
                                        HttpServletResponse response) throws
    Exception {

    String setType = request.getParameter("setType");
    String busEntityIdS = request.getParameter("busEntId");
    String authUser = request.getParameter("authUser");
    String authPass = request.getParameter("authPass");
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    try {
      response.setContentType(request.getContentType());

      if (!Utility.isSet(busEntityIdS)) {
        throw new Exception("busEntId must be specified");
      }
      if (!Utility.isSet(setType)) {
          throw new Exception("setType must be specified");
        }
      if (!Utility.isSet(authUser)) {
          throw new Exception("authUser must be specified");
        }
      if (!Utility.isSet(authPass)) {
          throw new Exception("authPass must be specified");
        }
      APIAccess factory = APIAccess.getAPIAccess();

      int busEntityId = Integer.parseInt(busEntityIdS);

      OutboundTranslate translator =
        new  OutboundTranslate(null, busEntityId, setType,
                         factory.getIntegrationServicesAPI(), factory.getTradingPartnerAPI(), out);

      if(!translator.verifyAuthorization(authUser,authPass)){
    	  throw new Exception("error code a1");
      }
      translator.translateToOutputStream(busEntityId, setType);
      if (out.size() > 0) {
        String fileName = translator.getOutputResponseFileName();
        response.setContentLength(out.size());
        response.setHeader("content-disposition",  "inline; filename=" + fileName);
        out.writeTo(response.getOutputStream());
      }
      response.flushBuffer();
      response.getOutputStream().close();
    } catch (Exception e) {
      e.printStackTrace(response.getWriter());
      throw new RuntimeException(e.getMessage());
    }
  }


}
