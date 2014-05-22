package com.cleanwise.view.taglibs;

import javax.servlet.jsp.tagext.VariableInfo;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;

/**
 */
public class ClwPagerItemTei extends TagExtraInfo {
   public VariableInfo[] getVariableInfo(TagData data) {
   String type = data.getAttributeString("type");
   if (type == null)
      type = "java.lang.Object";

   return new VariableInfo[] {
      new VariableInfo(data.getAttributeString("id"),
         type,
         true,
         VariableInfo.NESTED)
      };
   }
}
