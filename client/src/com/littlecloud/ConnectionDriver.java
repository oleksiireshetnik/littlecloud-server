package com.littlecloud;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by getupandgo on 7/19/15.
 */
public class ConnectionDriver
{
    public void initConnection()
    {
        ftp = new FTPClient();
        FTPClientConfig config = new FTPClientConfig();
        //config.setXXX(YYY); // change required options
        ftp.configure(config);

    }

    public void connectToServer(String url) throws IOException
    {
        int reply;
        ftp.connect(url);
        LOGGER.info("Connected to " + url);
        //System.out.print(ftp.getReplyString());

        // After connection attempt, you should check the reply code to verify
        // success.
        reply = ftp.getReplyCode();

        if(!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            LOGGER.info("FTP server refused connection.");
        }
        //... // transfer files
        ftp.logout();
    }

    public void disconnect()
    {
        if(ftp.isConnected()) {
            try {
                ftp.disconnect();
            } catch(IOException ioe) {
                // do nothing
            }
        }
    }

    FTPClient ftp;
	private static Logger LOGGER = Logger.getLogger(ConnectionDriver.class.getName());

}
