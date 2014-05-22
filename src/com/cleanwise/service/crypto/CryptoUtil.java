package com.cleanwise.service.crypto;

import java.io.*;
import java.util.Vector;
import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import javax.crypto.*;
/**
 * Provides functions to create and verify signatures and
 * to encrypt and decrypt using Public Key algorithms.
 * Signatures require a private key, encryption requires the public
 * key.
 * 
 * The signatures are created with the private key, and verified with the public
 * key. Encryption is performed with the public key of the recipient, and
 * decryption is performed by the recipient, with the recipient's private key.
 * <P>
 * These items are necessary to initialize the Signer component
 * <PRE>
 * asymmetricAlgorithm=RSA
 * signingAlgorithm=MD5withRSA
 * keystoreFilename=cleanwiseks
 * </PRE>
 * 
 * @author Ken Mawhinney
 * @since Feb 13, 2001
 */
public class CryptoUtil {

    /**
     * This object will be used for signing, if required. It needs to be
     * initialized with an algorithm and a key.
     */
    Signature signature;
    /**
     * Manages a keystore, this is needed because it is where the
     * keys will be taken from.
     */
    KeyStore keystore;
    
    /**
     *Contains a static list of Strings that are the providers we have already
     *loaded.  This allows for outside code to be ignorant as to whether they
     *have loaded a given provider before or not.  They only need to supply the
     *name, if it is not loaded we will attempt to load the provider.
     */
    private static List loadedProviders = new ArrayList();

    /**
     * Holds the name of the key store file.
     */
    String mKeyStoreFile;
    public void setKeyStoreFile (String lKeyStoreFile) {
        mKeyStoreFile = lKeyStoreFile;
    }
    public String getKeyStoreFile () {
        return mKeyStoreFile;
    }
    /**
     * Holds the name of the signing algorithm, so it can be retrieved later.
     * The algorithm to be used to sign the encrypted session key, e.g.
     * md5withRSA. Signing involves creating a digest of some text and
     * then encrypting the digest with the sender's private key. On
     * receipt, the recipient decrypts the digest with the sender's
     * public key (which they must have acquired by some trusted
     * means), and compares the digest against a locally computed digest
     * of the same text - if the two digests are the same, then there
     * is reasonably good assurance that the sender really sent it.
     */
    String mSigningAlgorithm;
    public void setSigningAlgorithm (String lSigningAlgorithm) {
        mSigningAlgorithm = lSigningAlgorithm;
    }
    public String getSigningAlgorithm () {
        return mSigningAlgorithm;
    }
    /**
     * A string specifying the algorithm to use for encryption and decryption.
     */
    String mEncryptAlgorithm;
    public void setEncryptAlgorithm (String lEncryptAlgorithm) {
        mEncryptAlgorithm = lEncryptAlgorithm;
    }
    public String getEncryptAlgorithm () {
        return mEncryptAlgorithm;
    }

    /**
     * Wrapper for the underlying keystore function, returns a string describing the
     * kind of keystore this is.
     * 
     * @return A string describing the key store
     * @exception CryptoException
     *                   Thrown if the keystore object is not initialized.
     */
    public String getType() 
    throws CryptoException {
        if (keystore != null) {
            return keystore.getType();
        } else {
            throw new CryptoException ("keystore object not initialized");
        }
    }

    /**
     * A wrapper for
     * the underlying keystore object.
     * A String describing the security provider that created and is managing the key store
     * 
     * @return Returns a string giving the provider name.
     * @exception CryptoException
     *                   Thrown if the keystore object is null.
     */
    public String getProvider() 
    throws CryptoException {
        if (keystore != null) {
            return keystore.getProvider().toString();
        } else {
            throw new CryptoException ("keystore object not initialized");
        }
    }

    /**
     * A wrapper for the underlying keystore method, this retrieves a vector of
     * all the aliases in the key   .
     * 
     * @return Returns a vector of strings, which may be empty if there are no aliases in the keystore.
     * @exception CryptoException
     *                   Thrown if the keystore object is not initialized.
     */
    public Vector getAliases() 
    throws CryptoException {
        if (keystore != null) {
            try {
                if (keystore.size() > 0) {
                    Vector aliasList = new Vector (keystore.size());
                    for (Enumeration e = keystore.aliases(); e.hasMoreElements();) {
                        aliasList.addElement(e.nextElement());
                    }
                    return aliasList;
                } else {
                    return new Vector();
                }
            } catch (KeyStoreException e) {
                throw new CryptoException ("keystore exception: " + e.getMessage());
            }
        } else {
            throw new CryptoException ("keystore object not initialized");
        }
    }

    /**
     * Returns the creation date for the given alias - the date it was added to the
     * key store. Or null if the keystore is not initialized or if the alias does not
     * exist.
     * 
     * @param alias  A string giving the name that is to be looked up.
     * @return A Date object or null  if the alias is not found
     *         in the keystore.
     * @exception CryptoException
     *                   Throw if the keystore object is not initialized.
     */
    public Date getCreationDate (String alias) 
    throws CryptoException {
        if (keystore != null) {
            try {
                return keystore.getCreationDate(alias);
            } catch (KeyStoreException e) {
                throw new CryptoException ("keystore exception: " + e.getMessage());
            }
        } else {
            throw new CryptoException ("keystore object not initialized");
        }
    }

    /**
     * A wrapper for the underlying keystore object's containsAlias method.
     * 
     * @param alias  The name that is to be looked up.
     * @return Returns true if the alias is present in the store, and false otherwise.
     * @exception CryptoException
     *                   Thrown if the keystore object is null
     */
    public boolean containsAlias (String alias) 
    throws CryptoException {
        if (keystore != null) {
            try {
                return keystore.containsAlias(alias);
            } catch (KeyStoreException e) {
                throw new CryptoException ("keystore exception: " + e.getMessage());
            }
        } else {
            throw new CryptoException ("keystore object not initialized");
        }
    }

    /**
     * A wrapper for the underlying keystore object, determines if the given
     * object is a key entry (meaning it is associated with a private key).
     * 
     * @param alias  A string containing the name to be looked up.
     * @return Returns true if the given alias is a key entry and false otherwise.
     * @exception CryptoException
     *                   Thrown if the keystore object is null.
     */
    public boolean isKeyEntry (String alias) 
    throws CryptoException {
        if (keystore != null) {
            try {
                return keystore.isKeyEntry(alias);
            } catch (KeyStoreException e) {
                throw new CryptoException ("keystore exception: " + e.getMessage());
            }
        } else {
            throw new CryptoException ("keystore object not initialized");
        }
    }

    /**
     * A wrapper for the underlying keystore object method of the same name, determines if
     * the given alias is for a certificate entry
     * 
     * @param alias  The name to be looked up.
     * @return True if the given alias is for a certificate entry and false if it is not or if
     *         the keystore object is not initialized.
     */
    public boolean isCertificateEntry (String alias) 
    throws CryptoException {
        if (keystore != null) {
            try {
                return keystore.isCertificateEntry (alias);
            } catch (KeyStoreException e) {
                throw new CryptoException ("keystore exception: " + e.getMessage());
            }
        } else {
            throw new CryptoException ("keystore object not initialized");
        }
    }

    /**
     * Returns an X509 Certificate for the given alias. 
     * 
     * @param alias  The alias to be looked up.
     * @return An X509Certificate object for the given alias, or null if there is no match.
     * @exception CryptoException
     *                   Thrown if the keystore object is not initialized.
     */
    public X509Certificate getX509Certificate (String alias) 
    throws CryptoException {
        if (isCertificateEntry (alias)) {
            try {
                Certificate c = keystore.getCertificate(alias);
                if (c instanceof X509Certificate) {
                    return(X509Certificate)c;
                } else {
                    return null;
                }
            } catch (KeyStoreException e) {
                throw new CryptoException ("keystore exception: " + e.getMessage());
            }
        } else {
            return null;
        }
    }

    /**
     * Returns a Base64 encoded version of the X509 certificate for the given alias,
     * or null if there is no match. This method would typically be used by .JHTML page designed
     * to return the Cleanwise's sites public key certificate - with the aim that any interested
     * party would install this into their keystore.
     * 
     * @param alias  The name to be looked up in the keystore
     * @return A Base64 encoded string.
     * @exception CryptoException
     *                   Thrown if the keystore object is null.
     */
    public String getX509CertificateAsString (String alias) 
    throws CryptoException {
        X509Certificate cx = getX509Certificate (alias);
        // convert to Base64 encoded string
        return null;
    }

    /**
     * Sets the given certicate to the entry specified by the alias. If the alias already exists
     * and is for a key entry, then a KeyStoreException will be thrown by the underlying 
     * keystore object and it will be converted into a CryptoException object. If entry exists
     * and is for a certificate, then the entry will be silently overwritten. Therefore, it
     * is up to the caller to use the other functions to do their own checking, if they want
     * to prevent overwriting.
     * <P>
     * Typically, this method would be used by a webpage that is uploading and installing
     * an E-Store vendor's public key - of course, the webpage would have to verify the
     * authenticity of the public key before storing it - particularly if it is overwriting
     * an existing entry.
     * 
     * @param alias  The alias under which this certificate should be stored.
     * @param c      The certificate to be stored.
     * @exception CryptoException
     *                   Thrown if the keystore object is null.
     */
    public void setCertificateEntry (String alias, Certificate c) 
    throws CryptoException {
        if (keystore != null) {
            try {
                keystore.setCertificateEntry (alias, c);
            } catch (KeyStoreException e) {
                throw new CryptoException ("keystore exception: " + e.getMessage());
            }
        } else {
            throw new CryptoException ("keystore object not initialized");
        }
    }

    /**
     * Sets a certificate entry where the certificate is presented as a Base64 encoded
     * string.
     * 
     * @param alias     The alias under which this is to be stored.
     * @param cAsString A string containing the certificate in Base64 encoded format.
     * @exception CryptoException
     *                   Thrown if the keystore object is not initialized.
     */
    public void setCertificateEntry (String alias, String cAsString) 
    throws CryptoException {
        if (keystore != null) {
            // Convert string to a certificate, and set the entry
            //Certificate c = new Certificate();
            //setCertificateEntry (alias, c);
        } else {
            throw new CryptoException ("keystore object not initialized");
        }
    }

    /**
     * The default constructor - typically for a bean this is empty and does nothing. All
     * initialized must be performed explicitly through the property functions.
     */
    public CryptoUtil() {}

    
    
    /**
     *Initializes the supplied provider 
     */
    public static void initializeProvider(String pProviderClassName) throws CryptoException{
        if(!loadedProviders.contains(pProviderClassName)){
            try{
                Provider provider = (Provider) Class.forName(pProviderClassName).newInstance();
                Security.addProvider(provider);
            }catch(ClassNotFoundException e){
                throw new CryptoException("Could not create provider from class name: "+pProviderClassName+" ClassNotFound exception: "+e.getMessage()+" make sure it is in your class path");
            }catch(InstantiationException e){
                throw new CryptoException("Could not create provider from class name: "+pProviderClassName+" InstantiationException: "+e.getMessage());
            }catch(IllegalAccessException e){
                throw new CryptoException("Could not create provider from class name: "+pProviderClassName+" IllegalAccessException: "+e.getMessage());
            }
        }
    }
    
    /**
     * Initials the signature object and loads the key store. Offloads
     * the work to the other initialize function.
     * 
     * @param lSigningAlgorithm
     *                  The algorithm to use for initializing the signature.
     * @param lEncryptAlgorithm
     *                  The algorithm to use for encyrpting and decrypting.
     * @param keystorefile
     *                  The name of the keystore file.
     * @param storepass The passphrase for the key store.
     * @return True if initialization steps completed successfully, and false otherwise.
     * @exception CryptoException
     *                   Passes on any such exception thrown by the other initialize
     *                   function.
     */
    public boolean initialize (String lSigningAlgorithm,
                               String lEncryptAlgorithm,
                               String keystorefile,
                               String storepass) 
    throws CryptoException {
        mSigningAlgorithm = lSigningAlgorithm;
        mEncryptAlgorithm = lEncryptAlgorithm;
        mKeyStoreFile     = keystorefile;
        return initialize (storepass);
    }

    /**
     * The real initializer. Intended to be used by services
     * when the signer object is created by Nucleus in the context
     * of a Dynamo application.
     * 
     * @param storepass The passphrase for the keystore - from which the public
     *                  and private keys needed for signing and verification are
     *                  obtained.
     * @return True if all went well, and false otherwise (unless an exception
     *         was thrown).
     */
    public boolean initialize (String storepass) 
    throws CryptoException {
        boolean result = false;
        try {
            // Prepare the signature object
            boolean goodSigObject = false;
            //if (mSigningAlgorithm == null) {
                //throw new CryptoException ("Signer, mSigningAlgorithm is null");
            //}
            if (mEncryptAlgorithm == null) {
                throw new CryptoException ("Signer, mEncryptAlgorithm is null");
            }
            if(mSigningAlgorithm != null){
                signature = Signature.getInstance(mSigningAlgorithm);
            }
            try{
                //first try to load the keystore by specifying SUN as the provider,
                //as this is the prefered way.
                keystore  = KeyStore.getInstance("JKS","SUN");
            }catch (NoSuchProviderException e){
                //if we can't find the SUN provider try and load using any providers
                //defined in the security policy file, although if SUN can't be found
                //this will probebly not work.
                keystore  = KeyStore.getInstance("JKS");
                e.printStackTrace();
            }
            if(storepass == null){
                keystore.load(new FileInputStream(mKeyStoreFile), null);
            }else{
                keystore.load(new FileInputStream(mKeyStoreFile), storepass.toCharArray());
            }
            
            result = true;
        } catch (CertificateException e) {
            e.printStackTrace();
            throw new CryptoException ("Certificate problem (" +
                                         mKeyStoreFile + "): " +
                                         e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException ("No such algorithm: |" +
                                         mSigningAlgorithm + "| " +
                                         e.getMessage());
        } catch (FileNotFoundException e) {
            throw new CryptoException ("File not found: " + e.getMessage());
        } catch (IOException e) {
            throw new CryptoException ("IO Exception, check all the files exist and readble: " +
                                         e.getMessage());
        } catch (KeyStoreException e) {
            throw new CryptoException ("Some problem with the keystore: " +
                                         mKeyStoreFile + " " +
                                         e.getMessage());
        }
        return result;
    }

    /**
     * Extracts the private key for the given alias and signs the supplied
     * text, using the algorithm supplied when the class was initialized.
     * 
     * @param alias    The alias by the which key to be used is known in the keystore.
     * @param keypass  The key password
     * @param bodyText The text to be signed.
     * @return Returns an array of bytes representing the signature.
     * @exception CryptoException
     *                   All the crypto exceptions are mapped onto this one cleanwise
     *                   crypto exception
     */
    public byte [] signBuffer (String alias, String keypass, byte [] bodyText) 
    throws CryptoException {
        byte [] raw = null;
        try {
            if (alias == null) {
                throw new CryptoException ("Signer, alias is null");
            }
            PrivateKey thePrivKey = (PrivateKey)keystore.getKey(alias, keypass.toCharArray());
            if (thePrivKey != null) {
                signature.initSign(thePrivKey);
                if (bodyText != null) {
                    signature.update(bodyText);
                    raw = signature.sign();
                } else {
                    throw new CryptoException ("Signer:signBuffer, body text is null");
                }
            } else {
                throw new CryptoException ("Cannot find specified under this alias: " +
                                             alias);
            }
        } catch (InvalidKeyException e) {
            throw new CryptoException ("Invalid key: " +
                                         e.getMessage());
        } catch (KeyStoreException e) {
            throw new CryptoException ("Some problem with the keystore: " +
                                         mKeyStoreFile +
                                         " " +
                                         e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException ("No such algorithm: " +
                                         mSigningAlgorithm +
                                         " " 
                                         + e.getMessage());
        } catch (UnrecoverableKeyException e) {
            throw new CryptoException ("Cannot recover the key from keystore: (check password): " 
                                         + e.getMessage());
        } catch (SignatureException e) {
            throw new CryptoException ("Some problem with the signature: " + e.getMessage());
        }
        return raw;
    }

    /**
     * Retrieves the public key for the given alias and verifies the supplied
     * signature for the given text.
     * 
     * @param alias    The name by which the key is known in the database.
     * @param bodyText The text whose signature is to be verified.
     * @param bodySignature
     *                 The signature for the supplied text.
     * @return True if the signature is OK, and false otherwise.
     * @exception CryptoException
     *                   All the crypto exceptions are mapped onto this single
     *                   Cleanwise crypto exception
     */
    public boolean verifyBuffer (String alias,
                                 byte [] bodyText,
                                 byte [] bodySignature) 
    throws CryptoException {
        boolean result = false;
        try {
            if (alias == null) {
                throw new CryptoException ("Signer, alias is null");
            }
            Certificate theCert = keystore.getCertificate(alias);
            if (theCert != null) {
                PublicKey thePubKey = theCert.getPublicKey();
                if (thePubKey != null) {
                    signature.initVerify(thePubKey);
                    signature.update(bodyText);
                    if (signature.verify(bodySignature)) {
                        result = true;
                    }
                } else {
                    throw new CryptoException ("Cannot find key under this alias: " +
                                                 alias +
                                                 " from this store: " +
                                                 mKeyStoreFile);
                }
            } else {
                throw new CryptoException ("Cannot find the certificate for this alias: " +
                                             alias +
                                             " from this store: " +
                                             mKeyStoreFile);
            }
        } catch (KeyStoreException e) {
            throw new CryptoException ("Some problem with the keystore: " +
                                         mKeyStoreFile +
                                         " " +
                                         e.getMessage());
        } catch (InvalidKeyException e) {
            throw new CryptoException ("Problem with the key: " + e.getMessage());
        } catch (SignatureException e) {
            throw new CryptoException ("Some problem with the signature: " + e.getMessage());
        }
        return result;
    }


    /**
     * Retrieves the certificate for the given alias, and uses it to encrypt
     * the cleartext using the algorithm specified when the class instance was
     * initialized. Since the certificate is public, there is no need to supply a
     * key password.
     * 
     * @param alias     The name by which this certificate is known in the key store.
     * @param cleartext The array of bytes to be encoded. There may be a limit on the minimum size
     *                  of this array.
     * @return Returns the encrypted bytes in an array or null if there was a problem.
     * @exception CryptoException
     *                   Thrown if any crypto exception occurs.
     */
    public byte [] encryptBuffer (String alias, byte [] cleartext) 
    throws CryptoException {
        byte [] raw = null;
        try {
            if (alias == null) {
                throw new CryptoException ("Signer, alias is null");
            }
            Certificate theCert = keystore.getCertificate(alias);
            if (theCert != null) {
                PublicKey thePubKey = theCert.getPublicKey();
                Cipher cipher = Cipher.getInstance(mEncryptAlgorithm);
                cipher.init(cipher.ENCRYPT_MODE, thePubKey);
                raw = cipher.doFinal(cleartext);
            } else {
                throw new CryptoException ("Problem getting key for this alias: " +
                                             alias +
                                             " from this keystore file " +
                                             mKeyStoreFile);
            }
        } catch (IllegalBlockSizeException e) {
            throw new CryptoException ("Illegal Block Size: " +
                                         e.getMessage());
        } catch (BadPaddingException e) {
            throw new CryptoException ("Problem with the padding for this algorithm: " +
                                         mEncryptAlgorithm +
                                         " : " +
                                         e.getMessage());
        } catch (InvalidKeyException e) {
            throw new CryptoException ("Invalid key for this alias: " +
                                         alias +
                                         " and algorithm " +
                                         mEncryptAlgorithm + 
                                         " : " +
                                         e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException ("No such algorithm or provider not found for this algorithm: " +
                                         mEncryptAlgorithm +
                                         " : " +
                                         e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new CryptoException ("Padding choice is not supported for this algorithm: " +
                                         mEncryptAlgorithm +
                                         " : " +
                                         e.getMessage());
        } catch (KeyStoreException e) {
            throw new CryptoException  ("Some problem with the keystore: " +
                                          mKeyStoreFile +
                                          " " +
                                          e.getMessage());
        }
        return raw;
    }

    /**
     * Decrypts the given ciphertext using the private key for the given alias
     * and the algorithm with which the class instance was initialized. Because
     * decryption requires the private key, the key password is required.
     * 
     * @param alias      The name by which the key is known in the keystore.
     * @param keypass    The key password, needed to retrieve the key from the keystore.
     * @param ciphertext The byte array to be decrypted.
     * @return The cleartext or null (if something went wrong).
     * @exception CryptoException
     *                   Thrown if any crypto exception occurs.
     */
    public byte [] decryptBuffer (String alias, String keypass, byte [] ciphertext) 
    throws CryptoException {
        byte [] raw = null;
        try {
            if (alias == null) {
                throw new CryptoException ("Signer, alias is null");
            }
            PrivateKey thePrivKey = (PrivateKey)keystore.getKey(alias, keypass.toCharArray());
            if (thePrivKey != null) {
                Cipher cipher = Cipher.getInstance(mEncryptAlgorithm);
                cipher.init(cipher.DECRYPT_MODE, thePrivKey);
                raw = cipher.doFinal(ciphertext);
            } else {
                throw new CryptoException ("Problem getting key for this alias: " +
                                             alias +
                                             " from this keystore file " +
                                             mKeyStoreFile);
            }
        } catch (UnrecoverableKeyException e) {
            throw new CryptoException ("Cannot recover private key for this alias: " +
                                         alias +
                                         " from this keystore " +
                                         mKeyStoreFile +
                                         " Check the password " +
                                         e.getMessage());
        } catch (IllegalBlockSizeException e) {
            throw new CryptoException ("Illegal Block Size: " +
                                         e.getMessage());
        } catch (BadPaddingException e) {
            throw new CryptoException ("Problem with the padding for this algorithm: " +
                                         mEncryptAlgorithm +
                                         " : " +
                                         e.getMessage());
        } catch (InvalidKeyException e) {
            throw new CryptoException ("Invalid key for this alias: " +
                                         alias +
                                         " and algorithm " +
                                         mEncryptAlgorithm + 
                                         " : " +
                                         e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException ("No such algorithm or provider not found for this algorithm: " +
                                         mEncryptAlgorithm +
                                         " : " +
                                         e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new CryptoException ("Padding choice is not supported for this algorithm: " +
                                         mEncryptAlgorithm +
                                         " : " +
                                         e.getMessage());
        } catch (KeyStoreException e) {
            throw new CryptoException ("Some problem with the keystore: " +
                                         mKeyStoreFile +
                                         " " +
                                         e.getMessage());
        }
        return raw;
    }
}


