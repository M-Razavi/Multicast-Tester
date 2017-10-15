package Example2;

import java.net.*;
import java.io.*;

/**
 * This example is divided into client and server. client is Example2.MulticastSniffer just from the group in the data will be captured down, for example, your network connected to the MBONE, you can execute java Example2.MulticastSniffer sap.mcast.net 9875 to listen to MBONE session announcements. The server is Example2.MulticastSender responsible for sending multiple messages.
 */
public class MulticastSniffer {
    public static void main(String[] args) {
        InetAddress ia = null;
        byte[] buffer = new byte[65509];
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
        int port = 0;

        // read the address from the command line
        try {
            try {

                if (args.length < 2) {
                    String[] newArgs = new String[2];
                    newArgs[0] = "224.0.1.1";
                    newArgs[1] = "9999";
                    System.out.println("Argument is null So it use " + newArgs[0] + " as address and '" + newArgs[1] + "' as port");
                    args = newArgs;
                }

                ia = InetAddress.getByName(args[0]);
            } catch (UnknownHostException e) {
                System.err.println(args[0] + " is not a valid address");
                System.exit(2);
            }
            port = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("Usage: java Example2.MulticastSniffer multicastaddress port");
            System.exit(1);
        }

        try {
            MulticastSocket ms = new MulticastSocket(port);
            ms.joinGroup(ia);
            String s;
            while(true) {
                ms.receive(dp);
                s = new String(dp.getData(), 0, dp.getLength());
                System.out.println(s);
            }
        } catch (SocketException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}