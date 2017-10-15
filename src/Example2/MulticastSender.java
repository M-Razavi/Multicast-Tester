package Example2;

import java.net.*;
import java.io.*;

/**
 * This example is divided into client and server. client is Example2.MulticastSniffer just from the group in the data will be captured down, for example, your network connected to the MBONE, you can execute java Example2.MulticastSniffer sap.mcast.net 9875 to listen to MBONE session announcements. The server is Example2.MulticastSender responsible for sending multiple messages.
 */
public class MulticastSender {
    public static void main(String[] args) {
        InetAddress ia = null;
        int port = 0;
        String s = "Here's some Group data\n";
        byte[] data = new byte[s.length()];

        // read address from the command line
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
                System.exit(1);
            }
            port = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("Usage: java MulticastSender__ multicastaddress port");
            System.exit(1);
        }

        data = s.getBytes();
        DatagramPacket dp = new DatagramPacket(data, data.length, ia, port);

        try {
            MulticastSocket ms = new MulticastSocket();

            // you can try to use different TTL value here for ms.
            //ms.setTimeToLive(16);

            ms.joinGroup(ia);
            for(int i=1; i<10; i++) {
                ms.send(dp);
                System.out.println("Send message to group.");
            }
            ms.leaveGroup(ia);
            ms.close();
        } catch (SocketException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}