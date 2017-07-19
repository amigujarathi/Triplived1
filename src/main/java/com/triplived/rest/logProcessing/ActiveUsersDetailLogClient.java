package com.triplived.rest.logProcessing;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * This method allows you to send and execute *nix command through SSH
 * to specified removed host and returns command output, in case incorrect
 * command will return command output error
 * 
 */

@Component
public class ActiveUsersDetailLogClient {
	
public List<String> execCommandAndGetLogs() {

    int port = 22;
    String rez = "+!";
    List<String> logArray = new ArrayList<String>();

    try {
        JSch jsch = new JSch();
        Session session = jsch.getSession("triplived", "119.81.58.12", port);
        session.setPassword("Myserver@1");
        session.setConfig("StrictHostKeyChecking", "no");

        // System.out.println("Establishing Connection...");
        session.connect();

        //String command = "sh /home/triplived/testScript.sh" + " '2015-06-01 04:39'" + " '2015-06-01 04:40'" + " '574c5118acef492c'";
        String command = "sh /home/triplived/scripts/noOfActiveUsers.sh";
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command); //setting command

        channel.setInputStream(null);

        ((ChannelExec) channel).setErrStream(System.err);

        InputStream in = channel.getInputStream();

        channel.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        int index = 0;
        
        
        while ((line = reader.readLine()) != null)
        {
            System.out.println(++index + " : " + line);
            logArray.add(line);
        }
        
        /*byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0)
                    break;
                // System.out.print(new String(tmp, 0, i));
                rez = new String(tmp, 0, i);
            }
            if (channel.isClosed()) {
                // System.out.println("exit-status: "+channel.getExitStatus());
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                rez = e.toString();
            }
        }*/
        
        channel.disconnect();
        session.disconnect();
        return logArray;
    }

    catch (Exception e) {
        rez = e.toString();
    }
    return logArray;
	}

    
}