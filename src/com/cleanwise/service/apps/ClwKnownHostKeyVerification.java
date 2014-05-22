package com.cleanwise.service.apps;

import com.sshtools.j2ssh.transport.ConsoleKnownHostsKeyVerification;
import com.sshtools.j2ssh.transport.publickey.SshPublicKey;
import com.sshtools.j2ssh.transport.InvalidHostFileException;

/**
*Overides the default implementation of ConsoleKnownHostsKeyVerification so that whenever
*a prompt is normally presented to the user instead "allways" is efectively chosen
*/
public class ClwKnownHostKeyVerification extends ConsoleKnownHostsKeyVerification{
     public ClwKnownHostKeyVerification () throws InvalidHostFileException {
        super();
     }

     /**
     */
    public void onHostKeyMismatch(String host, SshPublicKey pk,SshPublicKey actual) {
        try {
            allowHost(host, pk, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
     }
     /**
     */
    public void onUnknownHost(String host, SshPublicKey pk) {
        try {
            allowHost(host, pk, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
