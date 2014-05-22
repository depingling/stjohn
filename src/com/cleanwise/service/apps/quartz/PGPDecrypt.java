package com.cleanwise.service.apps.quartz;

import java.io.*;
import java.security.*;
import java.security.cert.*;
import java.util.*;

import org.apache.log4j.Logger;
import cryptix.message.*;
import cryptix.openpgp.*;
import cryptix.pki.*;

public class PGPDecrypt {
    protected static Logger log = Logger.getLogger(PGPDecrypt.class);
    ExtendedKeyStore privateRing;
    ExtendedKeyStore publicRing;

    public PGPDecrypt() {
        //**********************************************************************
        // Dynamically register both the Cryptix JCE and Cryptix OpenPGP
        // providers.
        //**********************************************************************
        java.security.Security.addProvider(new cryptix.jce.provider.CryptixCrypto() );
        java.security.Security.addProvider(new cryptix.openpgp.provider.CryptixOpenPGP() );
        privateRing = readKeyRing(System.getProperty("secret.key.ring"));
        publicRing = readKeyRing(System.getProperty("public.key.ring"));
    }

   public byte[] decrypt(byte[] encrypted, String baseNameCredential) {
       byte[] dec = encrypted;
       try {
           MessageFactory mf = MessageFactory.getInstance("OpenPGP");
           ByteArrayInputStream in = new ByteArrayInputStream(encrypted);
           Collection msgs = mf.generateMessages(in);
           Message msg = (Message) msgs.iterator().next();
           if (msg instanceof EncryptedMessage) {
               msg = ((EncryptedMessage) msg).decrypt(privateRing.getKeyBundle(
                       System.getProperty(baseNameCredential + ".secret.alias.id")),
                       System.getProperty(baseNameCredential + ".secret.passphrase").toCharArray());
               dec = ((LiteralMessage)msg).getBinaryData();
           }
       } catch (Exception e) {
           log.error("can not decrypt message.", e);
       }
       return dec;
  }

  public byte[] encrypt(byte[] decrypted, String baseNameCredential) {
      byte[] enc = decrypted;
      try {
          LiteralMessageBuilder lmb = LiteralMessageBuilder.getInstance("OpenPGP");
          lmb.init(decrypted);
          Message msg = lmb.build();
          EncryptedMessageBuilder emb = EncryptedMessageBuilder.getInstance("OpenPGP");
          emb.init(msg);
          emb.addRecipient(publicRing.getKeyBundle(System.getProperty(baseNameCredential + ".secret.alias.id")));
          msg = emb.build();
          enc = (new PGPArmouredMessage(msg)).getEncoded();
          log.info("is encrypted.");
      } catch (Exception e) {
          log.error("can not encrypt message.", e);
      }
      return enc;
  }


    protected ExtendedKeyStore readKeyRing(String filename) {
        ExtendedKeyStore ring = null;
        log.info("read file " + filename);
        try {
            FileInputStream in = new FileInputStream(filename);
            ring = (ExtendedKeyStore) ExtendedKeyStore.getInstance("OpenPGP/KeyRing");
            ring.load(in, null);
            in.close();
        } catch (IOException ioe) {
            log.error("can not read key store file.", ioe);
        } catch (NoSuchAlgorithmException nsae) {
            log.error("cannot find the OpenPGP KeyRing.", nsae);
        } catch (KeyStoreException kse) {
            log.error("reading keyring failed.", kse);
        } catch (CertificateException ce) {
            log.error("reading keyring failed.", ce);
        }
        return ring;
    }

}
