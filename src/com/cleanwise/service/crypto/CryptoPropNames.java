package com.cleanwise.service.crypto;

/**
 * 
 * These property names must appear in several places throughout this file
 * and in the property files themselves, its important that the same name
 * is used everywhere.
 * 
 * @author Ken Mawhinney
 * @since Feb 18, 2001
 */
public class CryptoPropNames {
    public static String kSymmetricAlgPropName           = "denv.symmetric.algorithm";
    public static String kAsymmetricAlgPropName          = "denv.asymmetric.algorithm";
    public static final String kBlockModePropName        = "denv.block.mode";
    public static final String kPaddingPropName          = "denv.padding";
    public static final String kSymmetricKeyLenPropName  = "denv.symmetric.key.length";
    public static final String kMessageDigestAlgPropName = "denv.md.algorithm";
    public static final String kSigningAlgPropName       = "denv.signing.algorithm";
    public static final String kHostAliasPropName        = "crypto.hostAlias";
    public static final String kHostCCAliasPropName      = "crypto.hostCCAliasPropName";
    public static final String kAssociateAliasPropName   = "crypto.associateAlias";
    public static final String kKeyStorefilePropName     = "crypto.keystorefile";
    public static final String kClearDigestPropName      = "ClearDigest";
    public static final String kCipherDigestPropName     = "CipherDigest";
    public static final String kSessionKeyEncPropName    = "SessionKeyEncrypted";
    public static final String kSessionKeySignPropName   = "SessionKeySignature";
    public static final String kIVParametersPropName     = "IVParameters";
    public static final String kJarFilePropComment       = "Encryption Parameters";
    public static final String kProviderName             = "denv.provider.name";
    public static final String kProvider                 = "denv.provider";
}
