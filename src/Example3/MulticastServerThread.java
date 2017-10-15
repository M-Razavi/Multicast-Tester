package Example3;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * In this example, the server broadcasts quotes at a regular interval. The client passively listens for quotes and does so on a MulticastSocket.
 * To implement the following program, the server-side program requires a plain text file one-liners.txt, you can design, or download link example. The following example is taken from Sun's Broadcasting to Multiple Recipients.
 */

public class MulticastServerThread extends QuoteServerThread {

    private long FIVE_SECONDS = 5000;

    public MulticastServerThread() throws IOException {
        super("Example3.MulticastServerThread");
    }

    public void run() {

        String groupAddress = "230.0.0.1";
        int groupPort = 9999;
        System.out.println("Server started. Join group on " + groupAddress + ":" + groupPort);


        while (moreQuotes) {
            try {
                byte[] buf = new byte[256];

                // construct quote
                String dString = null;

                // in On behalf of open file handler。if null It does not open the file，
                // It is only sent back server The current time
                if (in == null)
                    dString = new Date().toString();
                else
                    dString = getNextQuote();

                buf = dString.getBytes();

                // The hard-coded InetAddress of the DatagramPacket is "230.0.0.1"
                // and is a group identifier (rather than the Internet address of
                // the machine on which a single client is running). This particular
                // address was arbitrarily chosen from the reserved for this purpose.
                //
                // Created in this way, the DatagramPacket is destined for all
                // clients listening to port number 4446 who are member of the
                // "230.0.0.1" group.

                InetAddress group = InetAddress.getByName(groupAddress);
                DatagramPacket packet = new DatagramPacket(buf, buf.length,
                        group, groupPort);

                // As can be seen from this example，server Can not have to join group。
                socket.send(packet);
                System.out.println("Server sent quote.");

                // sleep for a while
                try {
                    Thread.sleep((long)(Math.random() * FIVE_SECONDS));
                } catch (InterruptedException e) { }

            } catch (IOException e) {
                e.printStackTrace();
                moreQuotes = false;
            }
        }
        socket.close();
    }
}