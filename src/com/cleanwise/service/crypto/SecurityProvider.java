package com.cleanwise.service.crypto;

import java.security.Security;
import java.security.Provider;
import java.util.Properties;
import java.util.ArrayList;
import java.io.File;

/**
 * A wrapper for some functions in the java.security package.
 * 
 * @author Brook Stevens
 * @author Ken Mawhinney
 * @since March 22, 2001
 */
public class SecurityProvider {
    private static boolean initialized = false;

    /**
     * Maps each Security Provider name to the name of the associated Security Class.
     */
    static Properties mSecurityClassMap = new Properties();
    public static Properties getSecurityClassMap () {
        return mSecurityClassMap;
    }

    /**
     * Intended to dynamically load the named security provider, e.g. ABA,BC Security. If the
     * provider is already loaded, it returns immediately. Otherwise, it tries to create
     * an instance of the named class and will succeed if this class can be found in the
     * current class path. If the class is found, then an instance will be created and dynamically
     * loaded.
     * <P>
     * On return, the provider will have been loaded into the JCE framework, and the services
     * of this provider should now be available to any other programming running in the same
     * JVM (yes??).
     * <P>
     * Nothing is logged, any problems are indicated by exceptions which are thrown.
     * 
     * @param providerName
     *               The name of the provider - this must match the name that Provider's class uses
     *               to identify itself - e.g. the ABA JCE package has a name of "ABA". These are presumably
     *               available in the documentation, but I found out the name in this case by writing
     *               a program that created the Provider object and then executed getName() on it and wrote
     *               out the results.
     * @exception CryptoException
     *                   All exceptions are mapped onto this one cleanwise exception. The most common exception is
     *                   "class not found" which means that the assigned class could not be found for the
     *                   given provider name.
     */
    private static void loadProvider (String providerName) 
    throws CryptoException {
        String className = null;
        try {
            if (Security.getProvider(providerName) == null) {
                if (mSecurityClassMap == null) {
                    throw new CryptoException("cwSecurityProvider: no provider-classname mappings are defined");
                }
                className = mSecurityClassMap.getProperty(providerName);
                if (className != null) {
                    Class theProviderClass = Class.forName(className);
                    if (Security.addProvider((Provider) theProviderClass.newInstance()) != -1) {
                        if (Security.getProvider(providerName) == null) {
                            throw new CryptoException ("cwSecurityProvider: provider not found" +
                                                         " even after dynamically loaded, for provider " +
                                                         providerName +
                                                         " and class name " +
                                                         className);
                        }
                    } else {
                        throw new CryptoException ("cwSecurityProvider: inconsistent results for provider " +
                                                     providerName +
                                                     " Provider was not found but the associated class is already loaded: " +
                                                     className);
                    }
                } else {
                    throw new CryptoException ("cwSecurityProvider: no class name found for this Provider: " +
                                                 providerName);
                }
            }
        } catch (IllegalAccessException e) {
            throw new CryptoException ("cwSecurityProvider: IllegalAccessException: " +
                                         e.getMessage());
        } catch (InstantiationException e) {
            throw new CryptoException ("cwSecurityProvider: InstantiationException: " +
                                         e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new CryptoException ("cwSecurityProvider: ClassNotFoundException: " +
                                         className + 
                                         " not found. Specified Provider is " +
                                         providerName);
        } catch (java.lang.NoClassDefFoundError e) {
            throw new CryptoException ("cwSecurityProvider: NoClassDefFoundError: " +
                                         className + 
                                         " not found. Specified Provider is " +
                                         providerName);
        }
    }

    /**
     * A static method to load a set of default security providers - which are
     * hardcoded into this class. At least one provider for the SUN JCE must be
     * loaded before anything else - because that provides the basic framework
     * for loading everything else.
     * <P>
     * The full path to a trusted key store is supplied, if this cannot be read an exception
     * will be thrown.
     * <P>
     * Also sets a system property that is required to be set to support SSL. The argument
     * for putting here is that enabling support SSL and loading a security provider for
     * SSL go together.
     * 
     * @param lTrustStorePath
     *               The full path to the key store file that the SSL classes are to use to check
     *               certificates received from the webserver.
     * @param lTrustStorePassword
     *               The password for the trust store file passed in the other argument.
     * @exception CryptoException
     *                   Throw if there are any exceptions loading the providers. Most common reason is that
     *                   the named class is not found in the classpath.
     *                   <P>
     *                   will also be thrown if the trusted key store cannot be read.
     */
    public static void loadSSLDefaultProviders (String lTrustStorePath,
                                                String lTrustStorePassword)
    throws CryptoException {

        File checkPath = new File (lTrustStorePath);
        if (!checkPath.canRead()) {
            throw new CryptoException ("Trusted Key Store is not readable: " +
                                         lTrustStorePath);
        }

        // Point to the handler that has support for https URLs.
        // Needed in order to use URL class below.
        System.setProperty("java.protocol.handler.pkgs",
                           "com.sun.net.ssl.internal.www.protocol");
        System.setProperty("javax.net.ssl.trustStore",         lTrustStorePath);
        System.setProperty("javax.net.ssl.trustStorePassword", lTrustStorePassword); 

        loadDefaultProviders ();
    }

    /**
     * A static method to load a security providers 
     * At least one provider for the SUN JCE must be
     * loaded before anything else - because that provides the basic framework
     * for loading everything else.
    */
    public synchronized static void loadProvider(String name, String provider)
    throws CryptoException {
        ArrayList providerNames = new ArrayList();
        if (!initialized){
            initialized = true;
            providerNames.add(name);
            //providerNames.add("SunJSSE");
            //getSecurityClassMap().setProperty("SunJSSE", "com.sun.net.ssl.internal.ssl.Provider");
        }else{
            providerNames.add(name);
        }
        
        getSecurityClassMap().setProperty(name,provider);
        
        StringBuffer errors = new StringBuffer();
        for (int i=0; i < providerNames.size(); i++) {
            try {
                loadProvider((String)providerNames.get(i));
            } catch (CryptoException e) {
                errors.append(providerNames.get(i) + " : " + e.getMessage() + "\n");
            }
        }
        if (errors.length() > 0) {
            throw new CryptoException ("Errors loading default providers:\n" +
                                         errors.toString());
        }
    }
    
    /**
     * A static method to load a set of default security providers - which are
     * hardcoded into this class. At least one provider for the SUN JCE must be
     * loaded before anything else - because that provides the basic framework
     * for loading everything else.
    */
    public static void loadDefaultProviders () 
    throws CryptoException {
        loadProvider("BC","org.bouncycastle.jce.provider.BouncyCastleProvider");
    }

}
