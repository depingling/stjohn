package com.cleanwise.service.crypto;

import java.io.*;
import java.util.Properties;
import java.util.jar.JarOutputStream;
import java.util.jar.JarEntry;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.jar.JarFile;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Date;
import java.util.TimeZone;
import java.text.DateFormat;

import java.security.MessageDigest;
import org.apache.log4j.*;

//import com.cleanwise.DataLoaders.cwLoaderLogger;

/**
 * <I>This version does not depend on anything from ATG - it is
 * intended to be used by E-Store owners for client programs they
 * run on at their own sites - where they do not have Dynamo installed.
 * There is another class, very similar to this one, which does use
 * Dynamo's services and which is intended to be used as a component
 * within a .JHTML page</I>
 * <P>
 * A class which provides both encryption and decryption. It provides
 * one method to encrypt a list of files and bundle them into a Jarfile.
 * And another method to unbundle the files and decrypt them.
 * The Encryptor requires a property file like the following, and this
 * describes how it is to encrypt the file. These characteristics are in
 * a Properties object which is written to the Jar File along with the file
 * it pertains to, and the Decryptor recovers all the "denv.*" properties
 * from that - it needs only to be told name of the keystore and aliases it
 * should use.
 * <P>
 * The Encryptor property file looks like this:
 * <PRE>
 * denv.symmetric.algorithm=Twofish
 * denv.block.mode=CBC
 * denv.padding=PKCS7Padding
 * denv.symmetric.key.length=256
 * denv.md.algorithm=SHA
 * denv.asymmetric.algorithm=RSA
 * denv.signing.algorithm=MD5withRSA
 * senderAlias=cleanwise
 * recipientAlias=kenway
 * keystorefile=cleanwiseks
 * </PRE>
 * <P>
 * The Decryptor property file looks like this. It has the encryption parameters
 * and encrypted session and cleartext and ciphertext digests computed by
 * the sender. It should be noted that the sender and receiver need to use
 * JCE Providers that support a common set of crypto algorithms.
 * <PRE>
 * #Encryption Parameters
 * #Sun Feb 18 00:02:22 EST 2001
 * denv.asymmetric.algorithm=RSA
 * CipherDigest=0YXOlPgzeOxQypHz8SzwxIWjTCs\=
 * SessionKeyEncrypted=U6HLxxYr4PYkub6PB2EAoZTHmk9CDfQXMhTiJaLU/r1CfXbJ0l+FPQLNA5V+b74JTCfOZEz9C7gJrbu3BExfJV+8KkFWbMCQxaTOKf3rwhEeTBYv4I3NJqXg04/rwCZZlPN5MNFAfxTGxADuy2CdJiDdxzeS3M4hHYYfl/kVsNA\=
 * denv.signing.algorithm=MD5withRSA
 * ClearDigest=Qcrugjw9mySvlbtE31YNW2V5Zh4\=
 * SessionKeySignature=CoxYmcKXNxSqtDx5Qpck++kxRPpA+A55liIEYB0rIvXjxsRGTuqneppZH9qEkQqZ5itupgvM1V6HetvbBOmHead4nWsrk2ELvk7POkqkPxxWomXIsX5vCOSVen9liqNXH8KJaIz4asgIfpnpJ1nTllm7WXemGQ7FzlQx6gWt0r0\=
 * denv.md.algorithm=SHA
 * denv.padding=PKCS7Padding
 * denv.symmetric.algorithm=Twofish
 * IVParameters=C4I0Dq5CZHD44xLyu7SEIg\=\=
 * denv.block.mode=CBC
 * </PRE>
 * 
 * @author Ken Mawhinney
 * @see EncryptionInputStream.java
 * @see cwTestEncryptionInputStream3.java
 * @see cwCryptoPackagerService.java
 * @since Feb 17, 2001
 */
public class CryptoPackager {
    private static final Logger log = Logger.getLogger(CryptoPackager.class);
    Properties mOptions = null;
    public void setOptions (Properties lOptions) {
        mOptions = lOptions;
    }
    public Properties getOptions () {
        return mOptions;
    }

    /*cwLoaderLogger mLogger;
    public void setLogger (cwLoaderLogger lLogger) {
        mLogger = lLogger;
    }
    public cwLoaderLogger getLogger() {
        return mLogger;
    }
     */

    boolean setPropertiesForEncryption (Properties lOptions)
    throws IOException {
        mOptions = lOptions;
        boolean rc = true;
        // Check for the presence of the required properties
        if (mOptions.getProperty(CryptoPropNames.kSymmetricAlgPropName) == null) {
            log.info(CryptoPropNames.kSymmetricAlgPropName + " missing");
            rc = false;
        }
        if (mOptions.getProperty(CryptoPropNames.kBlockModePropName) == null) {
            log.info (CryptoPropNames.kBlockModePropName + " missing");
            rc = false;
        }
        if (mOptions.getProperty(CryptoPropNames.kPaddingPropName) == null) {
            log.info (CryptoPropNames.kPaddingPropName + " missing");
            rc = false;
        }
        if (mOptions.getProperty(CryptoPropNames.kSymmetricKeyLenPropName) == null) {
            log.info(CryptoPropNames.kSymmetricKeyLenPropName + " missing");
            rc = false;
        }
        if (mOptions.getProperty(CryptoPropNames.kMessageDigestAlgPropName) == null) {
            log.info(CryptoPropNames.kMessageDigestAlgPropName + " missing");
            rc = false;
        }
        if (mOptions.getProperty(CryptoPropNames.kAsymmetricAlgPropName) == null) {
            log.info (CryptoPropNames.kAsymmetricAlgPropName + " missing");
            rc = false;
        }
        if (mOptions.getProperty(CryptoPropNames.kSigningAlgPropName) == null) {
            log.info (CryptoPropNames.kSigningAlgPropName + " missing");
            rc = false;
        }
        if (mOptions.getProperty(CryptoPropNames.kHostAliasPropName) == null) {
            log.info (CryptoPropNames.kHostAliasPropName + " missing");
            rc = false;
        }
        if (mOptions.getProperty(CryptoPropNames.kAssociateAliasPropName) == null) {
            log.info (CryptoPropNames.kAssociateAliasPropName + " missing");
            rc = false;
        }
        if (mOptions.getProperty(CryptoPropNames.kKeyStorefilePropName) == null) {
            log.info (CryptoPropNames.kKeyStorefilePropName + " missing");
            rc = false;
        }
        return rc;
    }

    /**
     * For decrypting, most of the parameters from the jar file itself. Just
     * need to the sender and receiver alias, and the name of the key store file.
     * Additional properties are ignored.
     * 
     * @param lOptions The Properties object to be checked.
     * @return True if the required properties are there, false otherwise.
     * @exception IOException
     */
    boolean setPropertiesForDecryption (Properties lOptions)
    throws IOException {
        mOptions = lOptions;
        boolean rc = true;
        if (mOptions.getProperty(CryptoPropNames.kHostAliasPropName) == null) {
            log.info (CryptoPropNames.kHostAliasPropName + " missing");
            rc = false;
        }
        if (mOptions.getProperty(CryptoPropNames.kAssociateAliasPropName) == null) {
            log.info (CryptoPropNames.kAssociateAliasPropName + " missing");
            rc = false;
        }
        if (mOptions.getProperty(CryptoPropNames.kKeyStorefilePropName) == null) {
            log.info (CryptoPropNames.kKeyStorefilePropName + " missing");
            rc = false;
        }
        return rc;
    }

    /**
     * Encrypts the given file using the settings given in the options file, and
     * writes the encrypted data out to the given stream. The stream object is
     * closed and then returned to the caller. The caller can access the
     * digests and session key used for the encryption.
     * 
     * @param out     The OutputStream to receive the encrypted data.
     * @param cleartext
     *                The stuff that is to be encrypted.
     * @param options A Properties object holding the options to use to set up the encrypting
     *                algorithms.
     * @return Returns the EncryptionInputStream object that was used to encrypt the data.
     * @exception IOException
     * @exception NumberFormatException
     */
    public EncryptionInputStream encryptToOutputStream (OutputStream out,
                                                   InputStream cleartext,
                                                   Properties options)
    throws IOException,NumberFormatException{
        int keylength = Integer.parseInt(options.getProperty(CryptoPropNames.kSymmetricKeyLenPropName));
        EncryptionInputStream cin = 
        new EncryptionInputStream (cleartext,
                                     options.getProperty(CryptoPropNames.kSymmetricAlgPropName),
                                     options.getProperty(CryptoPropNames.kBlockModePropName),
                                     options.getProperty(CryptoPropNames.kPaddingPropName),
                                     options.getProperty(CryptoPropNames.kMessageDigestAlgPropName),
                                     keylength);
        int bytesRead;
        byte [] input = new byte [1024];
        while ((bytesRead = cin.read(input)) > -1) {
            out.write(input, 0, bytesRead);
        }
        cin.close();
        return cin;
    }
    
    
    /**
     * Let the caller close the outputstream, they may want to append
     * more stuff. This decrypts the input stream and writes it to the output stream.
     * 
     * @param is      The input stream, the data to be decrypted is read from here.
     * @param out
     * @param optionsFromFile
     * @param keystorepass
     * @param keypass
     * @return 
     * @exception IOException
     *                   Thrown on any problem recovering the session key, e.g. decrypting or
     *                   verifying the signature.
     * @exception FileNotFoundException
     */
    boolean decryptViaStreams (InputStream is,
                               OutputStream out,
                               Properties optionsFromFile,
                               String keystorepass,
                               String keypass) 
    throws IOException, FileNotFoundException {
        boolean testresult = false;

        CryptoUtil verifier;
        byte [] sessionKey;
        // Check the signature on the session key, and decrypt it.
        try {
            verifier = new CryptoUtil();
            if (!verifier.initialize(optionsFromFile.getProperty(CryptoPropNames.kSigningAlgPropName),
                                     optionsFromFile.getProperty(CryptoPropNames.kAsymmetricAlgPropName),
                                     mOptions.getProperty(CryptoPropNames.kKeyStorefilePropName),
                                     keystorepass)) {
                throw new IOException ("cwCryptoPackager: (1) Problem recovering session key");
            }
            if (!verifier.verifyBuffer(mOptions.getProperty(CryptoPropNames.kAssociateAliasPropName), 
                                       Base64.decode(optionsFromFile.getProperty(CryptoPropNames.kSessionKeyEncPropName)),
                                       Base64.decode(optionsFromFile.getProperty(CryptoPropNames.kSessionKeySignPropName)))) {
                throw new IOException ("cwCryptoPackager: (2) Problem recovering session key");
            }

            sessionKey = 
            verifier.decryptBuffer(mOptions.getProperty(CryptoPropNames.kHostAliasPropName),
                                   keypass,
                                   Base64.decode(optionsFromFile.getProperty(CryptoPropNames.kSessionKeyEncPropName)));
            verifier = null;
            if (sessionKey == null) {
                throw new IOException ("cwCryptoPackager: (3) Problem recovering session key");
            }
        } catch (NullPointerException e) {
            throw new IOException ("cwCryptoPackager: null pointer exception, check that the JCE is in the CLASSPATH");
        } catch (CryptoException e) {
            e.printStackTrace();
            throw new IOException ("cwCryptoPackager: " + e.getMessage());
        }

        String ivBase64 = optionsFromFile.getProperty (CryptoPropNames.kIVParametersPropName);
        byte [] iv = null;
        if (ivBase64 != null) {
            iv = Base64.decode(ivBase64);
        }
        EncryptionInputStream cDecrypt = 
        new EncryptionInputStream (is,
                                     optionsFromFile.getProperty(CryptoPropNames.kSymmetricAlgPropName),
                                     optionsFromFile.getProperty(CryptoPropNames.kBlockModePropName),
                                     optionsFromFile.getProperty(CryptoPropNames.kPaddingPropName),
                                     optionsFromFile.getProperty(CryptoPropNames.kMessageDigestAlgPropName),
                                     sessionKey,
                                     iv);
        int bytesRead;
        byte [] input = new byte [1024];
        while ((bytesRead = cDecrypt.read(input)) > -1) {
            out.write (input, 0, bytesRead);
        }
        cDecrypt.close();

        // Check the digests
        return verifyDigests (cDecrypt,
                              optionsFromFile.getProperty(CryptoPropNames.kClearDigestPropName),
                              optionsFromFile.getProperty(CryptoPropNames.kCipherDigestPropName));
    }

    /**
     * Expects to get a closed decryption stream object. And compares the computed
     * digests against the values retrieved from the file
     * 
     * @param receivedClearDigest
     *               The digest for the clear text, in Base64 - as recovered from the jar file.
     * @param receivedCipherDigest
     *               The digest for the cipher text, in Base64 - as recovered from the jar file.
     *               
     * @return True if the digests match, false otherwise.
     */
    boolean verifyDigests(EncryptionInputStream cin,
                          String receivedClearDigest,
                          String receivedCipherDigest) {
        byte [] inputClearDigest   = Base64.decode(receivedClearDigest);
        byte [] inputCipherDigest  = Base64.decode(receivedCipherDigest);
        byte [] outputClearDigest  = cin.getAfterCipherDigest();
        byte [] outputCipherDigest = cin.getBeforeCipherDigest();

        boolean testresult = false;

        if (MessageDigest.isEqual(inputClearDigest, outputClearDigest)) {
            if (MessageDigest.isEqual(inputCipherDigest, outputCipherDigest)) {
                testresult = true;
            } else {
                log.info ("Received Cipher Digest and Computed Cipher Digest do not match!");
            }
        } else {
            log.info ("Received Clear Digest and Computed Clear Digest do not match!");
        }
        return testresult;
    }
    /**
     * Create a properties object for the given encryption stream,
     * and then write this to the supplied OutputStream.
     * <P>
     * The session key must be encrypted and signed before it is written out.
     * <I>The digests should be signed as well, to detect tampering.</I>
     * 
     * @param out     The output stream to receive the properties object.
     * @param cin     The Encryption input stream to be queried.
     * @param keystorepass
     *                The passphrase to open the key store, needed to get access to the public keys
     *                for encrypting.
     * @param keypass The passphrase for an individual private key, the private key is needed
     *                for signing and decrypting. Both the key store passphrase and the
     *                key passphrase are needed to access the private keys.
     * @exception IOException
     *                   Thrown if there is a problem initializing the cwSigner object. If this
     *                   cannot be initialized, the properties cannot be written to the Jarfile.
     */
    public void writeEncryptProps (OutputStream out, 
                            EncryptionInputStream cin,
                            String keystorepass,
                            String keypass,
                            Properties options) 
    throws IOException {
        Properties props = new Properties();
        String inputClearDigest  = Base64.encode(cin.getBeforeCipherDigest());
        String inputCipherDigest = Base64.encode(cin.getAfterCipherDigest());
        String iv = null;
        if (cin.getIV() != null) {
            iv = Base64.encode(cin.getIV());
        }
        CryptoUtil signer;
        byte [] sessionKeyEncrypted;
        byte [] sessionKeySignature;

        try {
            signer = new CryptoUtil();

            if (!signer.initialize(options.getProperty(CryptoPropNames.kSigningAlgPropName),
                                   options.getProperty(CryptoPropNames.kAsymmetricAlgPropName),
                                   options.getProperty(CryptoPropNames.kKeyStorefilePropName),
                                   keystorepass)) {
                throw new IOException ("cwCryptoPackager: (1) problem signing the session key");
            }

            sessionKeyEncrypted = 
            signer.encryptBuffer(options.getProperty(CryptoPropNames.kAssociateAliasPropName),
                                 cin.getSessionKey());
            if (sessionKeyEncrypted == null) {
                throw new IOException ("cwCryptoPackager: (2) problem signing the session key");
            }
            sessionKeySignature = signer.signBuffer(options.getProperty(CryptoPropNames.kHostAliasPropName),
                                                    keypass,
                                                    sessionKeyEncrypted);
            if (sessionKeySignature == null) {
                throw new IOException ("cwCryptoPackager: (3) problem signing the session key");
            }
        } catch (CryptoException e) {
            throw new IOException ("cwCryptoPackager: " +
                                   e.getMessage());
        }

        props.setProperty(CryptoPropNames.kClearDigestPropName,      inputClearDigest);
        props.setProperty(CryptoPropNames.kCipherDigestPropName,     inputCipherDigest);
        props.setProperty(CryptoPropNames.kSessionKeyEncPropName,    Base64.encode(sessionKeyEncrypted));
        props.setProperty(CryptoPropNames.kSessionKeySignPropName,   Base64.encode(sessionKeySignature));
        props.setProperty(CryptoPropNames.kSigningAlgPropName,       options.getProperty(CryptoPropNames.kSigningAlgPropName));
        props.setProperty(CryptoPropNames.kAsymmetricAlgPropName,    options.getProperty(CryptoPropNames.kAsymmetricAlgPropName));
        if (iv != null) {
            props.setProperty(CryptoPropNames.kIVParametersPropName,     iv);
        }
        props.setProperty(CryptoPropNames.kSymmetricAlgPropName,     cin.getEncryptAlgorithm());
        props.setProperty(CryptoPropNames.kBlockModePropName,        cin.getEncryptMode());
        props.setProperty(CryptoPropNames.kPaddingPropName,          cin.getPadding());
        props.setProperty(CryptoPropNames.kMessageDigestAlgPropName, cin.getDigestAlgorithm());
        props.store(out, CryptoPropNames.kJarFilePropComment);
    }


    /**
     * Packages the list of files into a jar file. Each file is encrypted with
     * a session key, and the session key is encrypted and signed, and written to
     * a properties file within the jar file. The jar file is written via the
     * given File object.
     * 
     * @param keystorepass
     *                 The key passphrase. Each private key is password protected. The private
     *                 key is needed for signing.
     * @param keypass
     * @param lOptions
     * @param lJarFile
     * @param filelist An Vector of file objects, each file to be placed inside the jar file.
     * @exception IOException
     */
    public void encryptAndPackage (String keystorepass,
                                   String keypass,
                                   Properties lOptions,
                                   File lJarFile,
                                   Vector filelist) 
    throws IOException {

        if (!setPropertiesForEncryption (lOptions)) {
            throw new IOException ("cwCryptoPackager: problem with options " +
                                   "check log for details, fix and re-try");
        }

        try {
            // Save the files to the jar file
            // Create the manifest for the jar file.
            // There is an entry for each file, and then an entry for the properties
            // associated with the file, which will follow the file to which it pertains.
            Manifest man = new Manifest();
            man.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
            man.getMainAttributes().put(Attributes.Name.SPECIFICATION_VENDOR, "Cleanwise");

            Date startTime = new Date();
            DateFormat asGMT = DateFormat.getDateTimeInstance();
            asGMT.setTimeZone(TimeZone.getTimeZone("GMT"));

            for (int i=0; i < filelist.size(); i++) {
                File pFile = (File)filelist.elementAt(i);
                Attributes attr = new Attributes();
                attr.put(new Attributes.Name("Date"), asGMT.format(startTime));
                man.getEntries().put(pFile.getName(), attr);
                man.getEntries().put(pFile.getName()+".properties", attr);
            }

            // The output stream, this is where the file will be written to.
            JarOutputStream jos = new JarOutputStream (new FileOutputStream(lJarFile), man);

            // Encrypt the files directly into the Jar File
            for (int i=0; i < filelist.size(); i++) {
                File pFile = (File)filelist.elementAt(i);
                jos.putNextEntry(new JarEntry(pFile.getName()));               
                EncryptionInputStream cDecrypt = encryptToOutputStream (jos, new FileInputStream(pFile), mOptions);
                // Create a properties object containing the digests and session key
                // and write this as the next entry in the jar file
                jos.putNextEntry(new JarEntry(pFile.getName()+".properties"));
                writeEncryptProps(jos, cDecrypt, keystorepass, keypass,mOptions);
            }
            jos.close();
        } catch (IOException e) {
            log.info ("IO Exception, perhaps opening the jar file: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Expects to receive a Jar File in Cleanwise Secure Package format. Decrypts
     * each file and writes it to a file in the specified directory, with the
     * name that it had in the jar file. It is assumed that the caller has made
     * sure the files exist, if they don't this method will throw some exceptions
     * no doubt.
     * <P>
     * This method gets the encryption details from the jar file itself, so
     * the properties file need only supply the name of the keystore file.
     * 
     * <I>TBD: check the version and so on in the Manifest to make ensure this
     * is indeed a Cleanwise Secure Package in a supported version.
     * 
     * @param keystorepass
     *                   The key store passphrase, needed to access the public keys, needed for
     *                   signature verification.
     * @param keypass    The passphrase for an individual private key, the private key is needed
     *                   for decrypting.
     * @param lOptions
     * @param lJarFile   The full path to the Jar File to take apart.
     * @param lTargetDir The directory were the extracted files are to be stored.
     * @exception IOException
     *                   TBD: List the reasons for the exceptions here.
     */
    public boolean decryptAndUnpackage (String keystorepass,
                                        String keypass,
                                        Properties lOptions,
                                        File lJarFile,
                                        File lTargetDir) 
    throws IOException, FileNotFoundException {
        boolean result = true;
        log.info ("Decrypting and unpackaging: " +
                    lJarFile.getAbsolutePath() +
                    " into " +
                    lTargetDir.getAbsolutePath());
        if (setPropertiesForDecryption (lOptions)) {
            JarFile jf = new JarFile (lJarFile);
            for (Enumeration e = jf.entries(); e.hasMoreElements(); ) {
                JarEntry je = (JarEntry)e.nextElement();
                if (!je.getName().endsWith(".properties")) {
                    JarEntry jeProps = jf.getJarEntry(je.getName()+".properties");
                    if (jeProps != null) {
                        InputStream propsStream = jf.getInputStream(jeProps);
                        Properties props        = new Properties();
                        props.load(propsStream);
                        propsStream.close();
                        InputStream is = jf.getInputStream(je);
                        File theFile = new File (lTargetDir, je.getName());
                        FileOutputStream out = new FileOutputStream (theFile);
                        if (!decryptViaStreams (is, out, props, keystorepass, keypass)) {
                            log.info ("Problem with extracted file: " +
                                        je.getName() + 
                                        " was written to " +
                                        theFile.getAbsolutePath());
                            result = false;
                        }
                        out.close();
                    }
                } else {
                    log.info ("Skipping: " + je.getName());
                }
            }
        } else {
            log.info ("Problem setting one or more properties for decryption, nothing done");
            result = false;
        }
        return result;
    }

}


