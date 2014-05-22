package com.cleanwise.service.apps.quartz;

import java.io.*;
import java.util.*;
import org.apache.log4j.Logger;

public class PGPDecryptEncrypt {
  private static final Logger log = Logger.getLogger(PGPDecryptEncrypt.class);
  public PGPDecryptEncrypt() {
  }

  private static byte[] getBytesFromFile(String file)  throws Exception {
    byte[] buf = new byte[8*1024];
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    InputStream in = new FileInputStream(file);
    int n;
    while ((n = in.read( buf )) > 0) {
        out.write( buf, 0, n );
    }
    in.close();
    return out.toByteArray();
  }

  public static void setProperties(String propertiesFile) {
      try {
          Properties props = new Properties();
          props.load(new FileInputStream(propertiesFile));
          for (Enumeration e = props.propertyNames() ; e.hasMoreElements() ;) {
              String key = (String)e.nextElement();
              System.setProperty(key, props.getProperty(key));
          }
      } catch (IOException ioe) {
          log.info("can not read application configuration file '" + propertiesFile + "'");
          System.exit(-1);
      }
  }


  public static void main(String[] args) throws Exception  {
    String usage = "Usage: PGPDecryptEncrypt -config FILE -pgp ACTION -credential BASE_NAME -input FILE1 -output FILE2\n" +
                    "where ACTION can be: decrypt or encrypt.";

    String propertiesFile = null;
    String pgp = null;
    String credential = null;
    String inputName = null;
    String outputName = null;
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-pgp")) {
        pgp = args[++i];
        if (!"encrypt".equalsIgnoreCase(pgp) &&
            !"decrypt".equalsIgnoreCase(pgp)) {
          pgp = null;
        }
        log.info("pgp = " + pgp);
      } else if (args[i].equals("-config")) {
        propertiesFile = args[++i];
        log.info("propertiesFile = " + propertiesFile);
      } else if (args[i].equals("-credential")) {
        credential = args[++i];
        log.info("credential = " + credential);
      } else if (args[i].equals("-input")) {
        inputName = args[++i];
        log.info("inputName = " + inputName);
      } else if (args[i].equals("-output")) {
        outputName = args[++i];
        log.info("outputName = " + outputName);
      }
    }

    if (pgp == null || propertiesFile == null ||
    credential == null || inputName == null || outputName == null) {
      log.info(usage);
      System.exit( -1);
    }
    try {
      setProperties(propertiesFile);
      PGPDecrypt pgpService = new PGPDecrypt();

      byte[] result = null;
      if ("decrypt".equalsIgnoreCase(pgp)) {
        log.info("decrypting");
        result = pgpService.decrypt(getBytesFromFile(inputName), credential);
      }
      else if ("encrypt".equalsIgnoreCase(pgp)) {
        log.info("encrypting");
        result = pgpService.encrypt(getBytesFromFile(inputName), credential);
      }
      log.info("end decoding");

      FileOutputStream fos = new FileOutputStream(outputName);
      fos.write(result);
      fos.flush();
      fos.close();
    } catch (Throwable th) {
      th.printStackTrace();
    }
  }


}
