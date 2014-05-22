



/*
 * PipelineUtil.java
 *
 * Created on August 3, 2005, 2:31 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.cleanwise.service.api.util;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.blob.storage.util.BlobStorageAccess;
import com.cleanwise.service.api.value.*;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.*;
import java.text.ParseException;
import com.cleanwise.service.api.session.Process;

/**
 *
 * @author nguschina
 */
public class ProcessUtil {

    /** Creates a new instance of ProcessUtil */
   public ProcessUtil() {
    }
    //------------------------------------------------------------------------------------
   public static void verifySimilarProcessesRunning(int pProcessId) throws Exception{
     APIAccess factory = new APIAccess();
     Process processEjb = factory.getProcessAPI();
     IdVector processIdV = processEjb.getSimilarProcessesRunning(pProcessId);
     IdVector eventIdV = null;
     if (processIdV != null && processIdV.size() > 0) {
       eventIdV = processEjb.getEventsByProcessIds(processIdV);
       String errorMsg = "Process " +pProcessId + " was REJECTED. " +
           "The following list of Processes with 'IN_PROGRESS' status was found : " +
           processIdV.toString() + "." +
           "The corresponding Events are: " + eventIdV.toString();
       throw new Exception(errorMsg);
     }
   }
   public static Object getProcessPropertyVal(ProcessPropertyData ppD){
     Object value = null;
     if (ppD != null) {
       //-----------------------------------------------------------------------
       //value = ppD.getVarValue();
       if ( (RefCodeNames.PROCESS_VAR_CLASS.INTEGER).equals(ppD.getVarClass())) {
            value = new Integer(ppD.getNumberVal());
       } else if ( (RefCodeNames.PROCESS_VAR_CLASS.STRING).equals(ppD.getVarClass())) {
            value = ppD.getStringVal();
       } else if ( (RefCodeNames.PROCESS_VAR_CLASS.DATE).equals(ppD.getVarClass())) {
            value = ppD.getDateVal();
       } else {
            // STJ-6037
            if (BlobStorageAccess.STORAGE_FILESYSTEM.equals(ppD.getStorageTypeCd()) ||
				BlobStorageAccess.STORAGE_NFS.equals(ppD.getStorageTypeCd()) ||
                BlobStorageAccess.STORAGE_FTP.equals(ppD.getStorageTypeCd())) {
                BlobStorageAccess bsa = new BlobStorageAccess();
                if (Utility.isSet(ppD.getBlobValueSystemRef())) {
                    value = bsa.readBlob(ppD.getStorageTypeCd(),
                                         ppD.getBinaryDataServer(),
                                         ppD.getBlobValueSystemRef());
                }
            } else {
                value = ppD.getVarValue();
            }
      }
     }
     return value;
   }

   public static PairViewVector getProcessParameters(ProcessPropertyDataVector ppdV, HashSet excludeNames){
     PairViewVector processParamsV = new PairViewVector();
     if (ppdV != null){
       for (Iterator iter = ppdV.iterator(); iter.hasNext(); ) {
         ProcessPropertyData ppD = (ProcessPropertyData) iter.next();
         if (ppD != null){
           PairView param = PairView.createValue();
           if ((excludeNames == null) ||
               (excludeNames != null && !excludeNames.contains(ppD.getTaskVarName()))){
             param.setObject1(ppD.getTaskVarName());
             param.setObject2(ProcessUtil.getProcessPropertyVal(ppD));
             processParamsV.add(param);
           }
         }
       }
     }
     return processParamsV;
   }

   public static Object getParamValue(PairViewVector params, String varName) {
    if (params != null) {
        Iterator it = params.iterator();
        while (it.hasNext()) {
            PairView param = (PairView) it.next();
            if (param.getObject1().equals(varName)) {
                return param.getObject2();
            }
        }
    }
    return null;
}

 }


