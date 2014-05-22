The JCE provides the framework for the java cryptography stuff, and is included with JDK1.4;
however much of this code originated from JDK1.2.2, and more importantly the client should 
not be reliant on Jdk1.4 without good reason.  the bcprov-jdk<version>-<provider version>
jar files are the actual JCE providers.  Any provider will do, but the client and server
have only been tested with the ABA provider (no longer available) and the Bouncy Castle 
provider (the version that is at the time of writing 1.16 (a.k.a Version 2.1.1)) in this folder and being used.  The
The only requirements are that the provider have RSA, TwoFish encryption capabilities and MD hashing.
With a little more work these can be changed to suit the desired provider by updating the
properties on both the client and the server, although this has only been tested with afore mentioned
configuration.

It has not been determined yet as to wheather different version of the provider can work with different
jdks.  It would be nice to be able to just use the lowest version as there is not a compelling need 
for hard disk space constraints.

The jsse.jar file is here for some compatiablity issues with jdk1.2.2.