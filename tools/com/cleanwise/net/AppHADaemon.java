package com.cleanwise.net;

import java.net.*;
import java.lang.*;
import java.io.*;
import java.util.*;

/**
 *
 *@author     dvieira
 *@created    October 8, 2005
 */
public class AppHADaemon {

    private static Date mStartDate = null;
    static {
        mStartDate = new Date();
    }
    
    public void performReq(Socket pReq) {
        NetClientReq req = new NetClientReq(pReq);
        req.setName(" # " + ct);
        req.start();
    }

    private Date mReqDate = null;

    int ct = 0;
    private static int mListenPort = 0;
    public void runService() {
        try {
            System.out.println("AppHADaemon: waitting for connection");
            ServerSocket srv = new ServerSocket(mListenPort, 10);
            do {
                Socket clientSocket = srv.accept(); ++ct;
                performReq(clientSocket);
                Thread.sleep(1000);
            } while (true);
        }
        catch (Exception e) {
            System.out.println("AppHADaemon: Exception: " + e.getMessage()
		+ "\n\n usage: java classname portnumber");
        }

    }


    /**
     *  Description of the Method
     *
     *@param  args  Description of Parameter
     */
    private static String
        kUsage    = "Usage: AppHADaemon portNumber";
    

    
    public static void main(String[] args) {
        if ( args.length < 1 )
        {
            System.out.println(kUsage);
            return;
        }
        mListenPort = Integer.parseInt(args[0]);

        AppHADaemon srv = new AppHADaemon();
        srv.runService();
    }


    class NetClientReq extends Thread {
        Socket mSock;
        InputStream m_in;
        OutputStream m_out;


        /**
         *  Constructor for the NetClientReq object
         *
         *@param  client  Description of Parameter
         */
        public NetClientReq(Socket client) {
            mSock = client;
            mReqDate = new Date();

        }


        public void run() {
            
            try {
                m_in = mSock.getInputStream();
                m_out = mSock.getOutputStream();
		String rx = "";
		byte[] ibyte = new byte[1];
		do {
		    if ( m_in.read(ibyte) < 0 ) { 
                        return;
                    }
		    rx += new String(ibyte);
		} while (ibyte[0] != 10);
		
		logMsg("rx=" + rx);
		if (rx.startsWith("stop") || rx.startsWith("GET /stop") ) {
		    logMsg("stopping");
		    if ( rx.startsWith("GET /stop" ) ) {
			String stopres = "<html><title>stop received"
			    + "</title><body>stop received</body></html>";
			
			String respmsg = "HTTP/1.0 200 OK\n";
			respmsg += "Server: NCSA/1.3 faximilie\n";
			respmsg += "MIME-version: 1.0\n";
			respmsg += "Content-type: ";
			respmsg += "text/html";
			respmsg += "\n";
			respmsg += "Content-length: ";
			respmsg += stopres.length();
			respmsg += "\n\n";
			respmsg += stopres;
			m_out.write(respmsg.getBytes());
			m_out.flush();
			mSock.close();
		    }
		    System.exit(1);
		}
		return;

            }
            catch (Exception e) {
                // end of client interaction.
                return;
            }
            finally {
                try { mSock.close(); } catch(Exception e) {}
            }
        }

    }
    
    private static int mReqCt = 0, mIdx = 0;

    public synchronized void logMsg(String pMsg) {

        System.out.println("  " + pMsg);
    }

}

