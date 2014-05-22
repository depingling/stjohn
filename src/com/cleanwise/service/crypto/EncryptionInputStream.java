package com.cleanwise.service.crypto;

import java.io.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.spec.*;

/**
 *  A class to encrypt and decrypt text stream using supplied algorithm.
 *  
 *  <CITE>
 *  Java Security, Scott Oaks, O'Reilly, Feb 1999 - esp. Chapter 13
 *  </CITE>
 * 
 * @author Ken Mawhinney
 * @since Feb 17, 2001
 */
public class EncryptionInputStream extends FilterInputStream {
    /**
     * Computes the digest on the bytes read from the input stream.
     * @since Feb 10, 2001
     */
    DigestInputStream din1;
    /**
     * Computes a digest on the cipher text.
     * @since Feb 10, 2001
     */
    DigestInputStream din2;
    /**
     * Computes a digest on the bytes that are read before they are
     * sent through the cipher stream. This may be the clear text or
     * the cipher text depending on how the caller is using this class.
     */
    MessageDigest digBeforeCipher;
    /**
     * Computes a digest on the bytes after they have been processed by the Cipherstream.
     */
    MessageDigest digAfterCipher;

    /**
     * The cipher object, that is used by the CipherInputStream.
     * Need this to get the IV after it is closed. If the specified mode
     * requires it.
     */
    Cipher cipher;

    /**
     * Returns the Digest Length in bytes. e.g. MD5 is 16. Both digests use
     * the same algorithm so arbitrarily picks of the private members and return
     * its length.
     * 
     * @return The length of the Message Digest in bytes, or 0 if the Provider does not
     *         support the operation of getting the digest length.
     * @exception IOException
     */
    public int getDigestLength() 
    throws IOException {
        return digBeforeCipher.getDigestLength();
    }

    /**
     * The symettric key algorithm used to encrypt of decrypt the cleartext,
     * e.g. DES or DESede. There must be a security provider installed that can
     * support the specified algorithm. The constructor will log an error immediately
     * if the algorithm is not supported.
     */
    String mEncryptAlgorithm;
    public void setEncryptAlgorithm(String lEncryptAlgorithm) {
        mEncryptAlgorithm = lEncryptAlgorithm;
    }
    public String getEncryptAlgorithm() {
        return mEncryptAlgorithm;
    }

    /**
     * The mode used for the block cipher, e.g. ECB (Electronic Cookbook). This
     * determines how the cleartext is split into blocks for encryption/decryption.
     * The encryptor and decryptor must use the same scheme. Anything other than
     * ECB requires the decryptor to be initializated with the Initialization
     * Vector the encryptor used.
     * 
     * See: Chapter 13, Java Security, Scott Oaks, O'Reilly: Feb 1999, 
     * ISBN: 1-56592-403-7
     */
    String mEncryptMode;
    public void setEncryptMode (String lEncryptMode) {
        mEncryptMode = lEncryptMode;
    }
    public String getEncryptMode () {
        return mEncryptMode;
    }

    /**
     * The padding scheme. Block ciphers require the input data to be
     * presented in a fixed block size. If a padding scheme is specified, then the
     * input data will be padded automatically. There are several schemes but
     * they are not all supported by every block cipher. If "NoPadding" is specified,
     * then the user must ensure that the input data is a multiple of the blocksize
     * of the cipher.
     */
    String mPadding;
    public void setPadding (String lPadding) {
        mPadding = lPadding;
    }
    public String getPadding() {
        return mPadding;
    }

    /**
     * The algorithm to use for computing the cleartext and ciphertext digests,
     * e.g. MD5 or SHA. 
     */
    String mDigestAlgorithm;
    public void setDigestAlgorithm (String lDigestAlgorithm) {
        mDigestAlgorithm = lDigestAlgorithm;
    }
    public String getDigestAlgorithm() {
        return mDigestAlgorithm;
    }

    /**
     * The length of the session key in bits. This is tells the KeyGenerator
     * what length of secret key to generate for the symettric encryption, it
     * must be a length supported by the specified cipher algorithm.
     */
    int mSessionKeyLengthBits;
    public void setSessionKeyLengthBits (int lSessionKeyLengthBits) {
        mSessionKeyLengthBits = lSessionKeyLengthBits;
    }
    public int getSessionKeyLengthBits() {
        return mSessionKeyLengthBits;
    }

    /**
     * The initialization vector for the cipher. For a block mode other than
     * ECB, the decryptor must be initialized with this vector so it knows how
     * to the blocks were shifted around by the encryptor. This should only
     * be accessed after the cipherstream has been closed (which should cause
     * doFinal() to be invoked on the cipher).
     * <P>
     * When the class is created for decryption, with a mode that requires it,
     * the IV must be supplied.
     */
    byte [] mIV = null;
    public void setIV(byte [] lIV) {
        mIV = lIV;
    }
    public byte[] getIV() {
        return mIV;
    }
    /**
     * Returns the session key length in bytes
     * 
     * @return An integer given the session key length
     */
    public int getSessionKeyLengthBytes() {
        return mSessionKeyLengthBits/8;
    }

    /**
     * Concatenates the cipher name, the mode and the padding scheme into
     * a single definition to use to initialize the cipher object.
     * 
     * @return A string like: DES/CBC/PKSC5Padding
     */
    String getEncryptScheme() {
        return 
        getEncryptAlgorithm() +
        "/" +
        getEncryptMode() +
        "/" +
        getPadding();
    }

    /**
      * A string representation of the innards of the object, mostly for debugging.
      * 
      * @return A string with a text description of the object.
      */
    public String toString () {
        StringBuffer buf = new StringBuffer();
        try {
            buf.append ("( cwEncryptionInputStream: ");
            buf.append ("(Encryption Algorithm = " +
                        getEncryptScheme() +
                        ") ");
            buf.append ("(Session Key Length in bits = " +
                        getSessionKeyLengthBits() +
                        ") ");
            buf.append ("(Digest Algorithm = " +
                        getDigestAlgorithm() +
                        ") ");
            buf.append ("(Digest Length = " +
                        getDigestLength() +
                        ") ");
            if (cipher != null) {
                buf.append ("Cipher Provider = " +
                            cipher.getProvider() +
                            ") ");
                buf.append ("Cipher Algorithm = " +
                            cipher.getAlgorithm() +
                            ") ");
            } else {
                buf.append ("cipher object is null");
            }
        } catch (IOException e) {
            buf.append("** IOException while creating this string: " +
                       e.getMessage());
        } finally {
            buf.append (" )");
        }
        return buf.toString();
    }

    /**
     * The digest is reset after this operation, so be careful to only call this
     * when all the data has been received through the stream
     * 
     * @return Returns an array of bytes containing the digest.
     */
    public byte[] getAfterCipherDigest() {
        return din2.getMessageDigest().digest();
    }
    /**
     * Returns the value of the before digest
     * 
     * @return Returns a byte array containing the digest.
     */
    public byte[] getBeforeCipherDigest() {
        return din1.getMessageDigest().digest();
    }

    /**
     * A byte array of length which should be equal to the specified session
     * key length. The contents are generated by the class when it is created
     * in ENCRYPT mode, but must be supplied when the class is created in DECRYPT mode.
     */
    byte [] mSessionKey;
    public void setSessionKey (byte [] lSessionKey) {
        mSessionKey = lSessionKey;
    }
    /**
     * Returns the session key that was either supplied or generated.
     * 
     * @return A byte array containing the session key that the stream is using.
     */
    public byte[] getSessionKey() {
        return mSessionKey;
    }
    /**
     * This stream does not support "mark" operation. Return false for this
     * call.
     * 
     * @return Always returns false.
     */
    public boolean markSupported () {
        return false;
    }

    /**
     * Builds an array filled with these other arrays:
     * 
     * digest after the cipher (i.e. on the cipher text)
     * digest before the cipher (i.e. the clear text)
     * session key
     * 
     * @return A byte array.
     * @exception IOException
     *                   Raised if any problem occurs during the write of the byte array stream.
     */
    public byte[] getDigestAndKey() 
    throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(getAfterCipherDigest());
        b.write(getBeforeCipherDigest());
        b.write(getSessionKey());
        return b.toByteArray();
    }


    /**
     * Sets up the cipher and inserts it into the streams
     * 
     * inputstream --> digest1 --> cipher --> digest2 .
     * 
     * <P>
     * For this constructor, encrypt mode is assumed. The cipher instance is
     * created using the specified algorithm, mode and padding scheme. A random
     * session key is generated and will be used to encrypt the data.
     * 
     * <I>
     * This class is really designed for symmetric encryption. With some further
     * work it could not doubt be used for asymmetric encryption.
     * 
     * @param in       The input stream which will supply the bytes.
     * @param kEncryptAlgorithm
     *                 A string specifying the algorithm to use. There must be a security provider
     *                 available to support this.
     * @param kEncryptMode
     *                 A string with the encrypt mode. e.g. ECB or CBC. It must be supported
     *                 by the specified algorithm.
     * @param kPadding The padding to used, e.g. NoPadding or PKCS5Padding
     * @param kDigestAlgorithm
     *                 The algorithm to use for computing digests on the clear and cipher text.
     * @param kKeyLengthInBits
     *                 The length of the key, in bits. Must be supported by the specified algorithm
     *                 and there must be a key generator available for it.
     * @exception IOException
     *                   All the crypto exceptions are mapped on IOException
     */
    public EncryptionInputStream (InputStream in,
                                    String kEncryptAlgorithm,
                                    String kEncryptMode,
                                    String kPadding,
                                    String kDigestAlgorithm,
                                    int    kKeyLengthInBits) 
    throws IOException {
        super(in);
        setEncryptAlgorithm(kEncryptAlgorithm);
        setEncryptMode(kEncryptMode);
        setPadding(kPadding);
        setDigestAlgorithm(kDigestAlgorithm);
        setSessionKeyLengthBits(kKeyLengthInBits);
        try {
            // Generate the session key to use in the encryption
            KeyGenerator kgen      = KeyGenerator.getInstance(getEncryptAlgorithm());
            kgen.init(getSessionKeyLengthBits());
            SecretKey skey         = kgen.generateKey();
            setSessionKey(skey.getEncoded());
            SecretKeySpec skeySpec = new SecretKeySpec(getSessionKey(), getEncryptAlgorithm());

            // Construct the Cipher that will do the encryption and chain
            // it to the FileInputStream - so the byte stream is encrypted as
            // it comes in from the file.
            cipher = Cipher.getInstance(getEncryptScheme());

            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

            digBeforeCipher         = MessageDigest.getInstance(getDigestAlgorithm());
            din1                    = new DigestInputStream(in, digBeforeCipher);
            CipherInputStream cin   = new CipherInputStream(din1, cipher);
            digAfterCipher          = MessageDigest.getInstance(getDigestAlgorithm());
            din2                    = new DigestInputStream(cin, digAfterCipher);
        } catch (NoSuchAlgorithmException e) {
            throw new IOException ("cwEncryptionInputStream: " +
                                   "No support for this algorithm: " +
                                   getEncryptAlgorithm() +
                                   " : " +
                                   e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new IOException ("cwEncryptionInputStream: " +
                                   "No such padding for this algorithm: " +
                                   getEncryptAlgorithm() +
                                   " " +
                                   getPadding() +
                                   " : " +
                                   e.getMessage());
        } catch (InvalidKeyException e) {
            throw new IOException ("cwEncryptionInputStream: " +
                                   "key not valid for this algorithm: " +
                                   getEncryptAlgorithm() +
                                   " (Problem with the generator or the key length?) " +
                                   e.getMessage());
        }
    }

    /**
     * Sets up the cipher and inserts it into the streams
     * 
     * inputstream --> digest1 --> cipher --> digest2
     * 
     * <P>
     * This constructor defaults the cipher to DECRYPT mode, and the secret
     * key (and might need to add support for the IV array) if the block
     * mode demands it.
     * 
     * @param in       The input stream which will supply the bytes.
     * @param kEncryptAlgorithm
     *                 A string specifying the algorithm to use. There must be a security provider
     *                 available to support this.
     * @param kEncryptMode
     *                 A string with the encrypt mode. e.g. ECB or CBC. It must be supported
     *                 by the specified algorithm.
     * @param kPadding The padding to used, e.g. NoPadding or PKCS5Padding
     * @param kDigestAlgorithm
     *                 The algorithm to use for computing digests on the clear and cipher text.
     * @param l
     *                 A byte array holding the secret key to use to decrypt the input bytes.
     * @param lIV      The initialization vector that the cipher must be initialized with, when
     *                 using an block mode other than ECB. This would have come from the original
     *                 encrypting cipher and must be delivered to the receiver along with the
     *                 session key. If the mode is ECB, then null can be supplied.
     * @exception IOException
     *                   All exceptions are mapped onto the IOException
     */
    public EncryptionInputStream (InputStream in,
                                    String kEncryptAlgorithm,
                                    String kEncryptMode,
                                    String kPadding,
                                    String kDigestAlgorithm,
                                    byte[] lSessionKey,
                                    byte[] lIV) 
    throws IOException {
        super(in);
        try {
            if (lSessionKey == null) {
                throw new IOException ("session key array is null");
            } else if (lSessionKey.length == 0) {
                throw new IOException ("session key array is empty");
            } else {
                setSessionKey (lSessionKey);
            }
            setIV (lIV);
            setEncryptAlgorithm(kEncryptAlgorithm);
            setEncryptMode(kEncryptMode);
            setPadding(kPadding);
            setDigestAlgorithm(kDigestAlgorithm);
            setSessionKeyLengthBits(lSessionKey.length * 8);

            SecretKeySpec skeySpec = new SecretKeySpec(getSessionKey(),
                                                       getEncryptAlgorithm());
            
            // Construct the Cipher that will do the encryption and chain
            // it to the FileInputStream - so the byte stream is encrypted as
            // it comes in from the file.
            cipher = Cipher.getInstance(getEncryptScheme());
            if (lIV == null) {
                cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            } else {
                IvParameterSpec dps = new IvParameterSpec (lIV);
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, dps);
            }

            digBeforeCipher         = MessageDigest.getInstance(getDigestAlgorithm());
            din1                    = new DigestInputStream(in, digBeforeCipher);
            // Provide the IV vector is it is not null
            CipherInputStream cin = new CipherInputStream(din1, cipher);
            digAfterCipher          = MessageDigest.getInstance(getDigestAlgorithm());
            din2                    = new DigestInputStream(cin, digAfterCipher);
        } catch (InvalidAlgorithmParameterException e) {
            throw new IOException("cwEncryptionInputStream: " +
                                  "Problem setting the initialization vector possibly: " +
                                  e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new IOException ("cwEncryptionInputStream: " +
                                   "No support for this algorithm: " +
                                   getEncryptScheme() +
                                   " : " +
                                   e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new IOException ("cwEncryptionInputStream: " +
                                   "No such padding for this algorithm: " +
                                   getEncryptScheme() +
                                   " : " +
                                   e.getMessage());
        } catch (InvalidKeyException e) {
            throw new IOException ("cwEncryptionInputStream: " +
                                   "key not valid for this algorithm: " +
                                   getEncryptScheme() +
                                   " : " +
                                   e.getMessage());
        }
    }

    public void close() 
    throws IOException {
        super.close();
        // Do whatever else needs to be cleaned up.
        if (cipher != null) {
            setIV(cipher.getIV());
        }
    }

    /**
     * Reading a single byte is not supported, because Twofish is block cipher
     * and can only read blocks of bytes.
     * 
     * @return 
     */
    public int read() 
    throws IOException {
        int result=1;

        if (result == 1) {
            throw new IOException ("read() not supported");
        } else {
            return 0;
        }
    }

    /**
     * Only supports reading of buffers that are multiples of the cipher block size.
     * 
     * @param buffer
     * @param offset
     * @param count
     * @return 
     * @exception IOException
     */
    public int read(byte[] buffer, int offset, int count) 
    throws IOException {
        return din2.read(buffer, offset, count);
    }

    /**
     * Returns a buffer full bytes from the cipher stream. This will be ciphertext
     * or cleartext depending on the mode that was set for the cipher. There
     * is a problem if the buffer size is less than , and IllegalBlockSize can
     * result. This is caught and turned into a bytesRead value of -1
     * 
     * @param buffer A buffer which will receive the cipher text
     * @return The number of bytes read from the stream, or -1 if nothing is read.
     * @exception IOException
     *                   Most likely reason is some problem with the buffer size.
     */
    public int read (byte[] buffer)  
    throws IOException {
        return din2.read(buffer, 0, buffer.length);
    }
}


