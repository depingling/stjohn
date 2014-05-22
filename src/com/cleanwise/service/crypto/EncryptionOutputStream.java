package com.cleanwise.service.crypto;

import java.io.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.spec.*;

/**
 *  A class to encrypt and decrypt text stream using the Twofish algorithm.
 *  <H3>Issues</H3>
 *  <OL>
 *  <LI>Should switch to CBC (cipher block chaining) to avoid problems caused
 *  by patterns in the text data. More secure than ECB.
 *  <LI>Files that are less than 32 bytes, cause an IllegalBlockException when
 *  they are decrypted. Cannot figure this out, the cipher is supposedly a
 *  padding cipher so why should it complain?
 *  </OL>
 * 
 * @author Ken Mawhinney
 * @see http://www.counterpane.com/twofish.html
 * @see cwEncryptionInputStream
 * @since Feb 10, 2001
 */
public class EncryptionOutputStream extends FilterOutputStream {
    /**
     * Holds the session key, generated on the fly for each instantiation of the class
     * @since Feb 10, 2001
     */
    byte [] sessionKey;
    /**
     * Computes the digest on the bytes written to the stream.
     * @since Feb 10, 2001
     */
    DigestOutputStream dout1;
    /**
     * Computes a digest on the cipher text.
     * @since Feb 10, 2001
     */
    DigestOutputStream dout2;

    /**
     * Computes a digest on the bytes that are written before they are
     * sent through the cipher stream. This may be the clear text or
     * the cipher text depending on how the caller is using this class.
     */
    MessageDigest digBeforeCipher;
    /**
     * Computes a digest on the bytes after they have been processed by the Cipherstream.
     */
    MessageDigest digAfterCipher;

    /**
     * The name of the digest algorithm. This is SHA-1, it produces a 160 bit
     * digest and is supposed to be superior to md5
     */
    static final String kDigestAlgorithm = new String("SHA");
    /**
     * The name of the encryption algorithm. The program will not work unless
     * there is an installed Crypto provider that supports the named algorithm.
     */
    static final String kEncryptAlgorithm = new String ("Twofish");
    /**
     * The full encryption scheme: this is a combination of an encryption
     * algorithm and a padding scheme. The encryption algorithm needs to be
     * match the name specified in kEncryptAlgorithm
     * 
     * @see kEncryptScheme
     */
    static final String kEncryptScheme    = new String("Twofish/ECB/PKCS7Padding");
    /**
     * The desired session key length in bits. The key generator will be initialized to
     * generate a key of this length.
     * <P>
     * Twofish accepts a variable length key up to 256 bits.
     */
    static final int kSessionKeyLength     = 256;

    /**
     * Because of the difficultly of retrieving a single byte from the
     * Base64 encoded file, and because the digest and key lengths are constant
     * in any case, simply provide the digest length to the caller rather than
     * force them to retrieve it from the file.
     * <P>
     * Note: md5 produces a 128 bit digest (16 bytes). SHA produces a 160 bit
     * digest (20 bytes).
     * 
     * @return The length of the Message Digest in bytes.
     * @exception IOException
     *                   If the algorithm is unsupported, throws this exception
     */
    static public int getDigestLength() 
    throws IOException {
        int length = 0;
        if (kDigestAlgorithm.equals("SHA")) {
            length = 20;
        } else if (kDigestAlgorithm.equals("MD5")) {
            length = 16;
        } else {
            throw new IOException ("cwEncryptionOutputStream: Unsupported digest algorithm");
        }
        return length;
    }

    /**
     * Returns the length of the session key, caller needs this to know how
     * many bytes to read from the file. A version string is required, since
     * the key length may change.
     * 
     * @return The length of the session key in bytes.
     */
    static public int getSessionKeyLength(String lVersion) {
        if (lVersion.equals("1.0")) {
            return kSessionKeyLength/8;
        } else {
            return kSessionKeyLength/8;
        }
    }
    /**
     * The digest is reset after this operation, so be careful to only call this
     * when all the data has been received through the stream
     * 
     * @return Returns an array of bytes containing the digest.
     */
    public byte[] getAfterCipherDigest() {
        return dout1.getMessageDigest().digest();
    }
    /**
     * Returns the value of the before digest
     * 
     * @return Returns a byte array containing the digest.
     */
    public byte[] getBeforeCipherDigest() {
        return dout2.getMessageDigest().digest();
    }
    /**
     * Returns the session key that was either supplied or generated.
     * 
     * @return A byte array containing the session key that the stream is using.
     */
    public byte[] getSessionKey() {
        return sessionKey;
    }
    /**
     * Return false to indicate this class does not support the mark feature
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
     * --> digest1 --> cipher --> digest2 --> outputstream
     * 
     * @param out    The Output Filter stream this is to be attached to.
     * @exception IOException
     *                   All exceptions are mapped onto this one. NoSuchAlgorithm, NoSuchPadding,
     *                   InvalidKey
     */
    public EncryptionOutputStream (OutputStream out) 
    throws IOException {
        super(out);
        try {
            // Generate the session key to use in the encryption
            KeyGenerator kgen      = KeyGenerator.getInstance(kEncryptAlgorithm);
            kgen.init(kSessionKeyLength);
            SecretKey skey         = kgen.generateKey();
            sessionKey             = skey.getEncoded();
            SecretKeySpec skeySpec = new SecretKeySpec(sessionKey, kEncryptAlgorithm);
            // Construct the Cipher that will do the encryption and chain
            // it to the FileOutputStream - so the byte stream is encrypted as
            // it comes out 
            Cipher cipher = Cipher.getInstance(kEncryptScheme);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);


            digAfterCipher          = MessageDigest.getInstance(kDigestAlgorithm);
            dout1                   = new DigestOutputStream(out, digAfterCipher);
            CipherOutputStream cout = new CipherOutputStream(dout1, cipher);
            digBeforeCipher         = MessageDigest.getInstance(kDigestAlgorithm);
            dout2                   = new DigestOutputStream(cout, digBeforeCipher);


        } catch (NoSuchAlgorithmException e) {
            throw new IOException ("cwEncryptionOutputStream: " +
                                   "No support for this algorithm: " +
                                   kEncryptAlgorithm +
                                   " : " +
                                   e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new IOException ("cwEncryptionOutputStream: " +
                                   "No such padding for this algorithm: " +
                                   kEncryptAlgorithm +
                                   " : " +
                                   e.getMessage());
        } catch (InvalidKeyException e) {
            throw new IOException ("cwEncryptionOutputStream: " +
                                   "key not valid for this algorithm: " +
                                   kEncryptAlgorithm +
                                   " : " +
                                   e.getMessage());
        }
    }

    /**
     * Sets up the cipher and inserts it into the streams
     * 
     * digest1 --> cipher --> digest2 --> outputstream
     * 
     * @param out     The output stream which will supply the bytes.
     * @exception IOException
     *                   All exceptions are mapped onto this one. NoSuchAlgorithm, NoSuchPadding,
     *                   InvalidKey
     */
    public EncryptionOutputStream (OutputStream out, byte[] lSessionKey) 
    throws IOException {
        super(out);
        try {
            sessionKey             = lSessionKey;
            SecretKeySpec skeySpec = new SecretKeySpec(sessionKey, kEncryptAlgorithm);

            // Construct the Cipher that will do the encryption and chain
            // it to the FileInputStream - so the byte stream is encrypted as
            // it comes in from the file.
            Cipher cipher = Cipher.getInstance(kEncryptScheme);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);

            digAfterCipher          = MessageDigest.getInstance(kDigestAlgorithm);
            dout1                   = new DigestOutputStream(out, digAfterCipher);
            CipherOutputStream cout = new CipherOutputStream(dout1, cipher);
            digBeforeCipher         = MessageDigest.getInstance(kDigestAlgorithm);
            dout2                   = new DigestOutputStream(cout, digBeforeCipher);
        } catch (NoSuchAlgorithmException e) {
            throw new IOException ("cwEncryptionOutputStream: " +
                                   "No support for this algorithm: " +
                                   kEncryptAlgorithm +
                                   " : " +
                                   e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new IOException ("cwEncryptionOutputStream: " +
                                   "No such padding for this algorithm: " +
                                   kEncryptAlgorithm +
                                   " : " +
                                   e.getMessage());
        } catch (InvalidKeyException e) {
            throw new IOException ("cwEncryptionOutputStream: " +
                                   "key not valid for this algorithm: " +
                                   kEncryptAlgorithm +
                                   " : " +
                                   e.getMessage());
        }
    }

    public void close() 
    throws IOException {
        dout2.flush();
        super.close();
    }

    /**
     * writing a single byte is not supported, because Twofish is block cipher
     * and can only write blocks of bytes.
     * 
     * @return 
     */
    public void write(int oneByte) 
    throws IOException {
        dout2.write(oneByte);
    }

    /**
     * 
     * @param buffer
     * @param offset
     * @param count
     * @exception IOException
     */
    public void write(byte[] buffer, int offset, int count) 
    throws IOException {
        dout2.write(buffer, 0, count);
    }

    /**
     * Writes a buffer full bytes to the cipher stream. This will be ciphertext
     * or cleartext depending on the mode that was set for the cipher. There
     * is a problem if the buffer size is less than , and IllegalBlockSize can
     * result. 
     * 
     * @param buffer A buffer which holding the text to be output
     * @exception IOException
     *                   Most likely reason is some problem with the buffer size.
     */
    public void write (byte[] buffer) 
    throws IOException {
        dout2.write(buffer);
    }
}


