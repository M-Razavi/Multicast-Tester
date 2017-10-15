package Example3;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * In this example, the server broadcasts quotes at a regular interval. The client passively listens for quotes and does so on a MulticastSocket.
 * To implement the following program, the server-side program requires a plain text file one-liners.txt, you can design, or download link example. The following example is taken from Sun's Broadcasting to Multiple Recipients.
 */

// Because in our example，Example3.MulticastServerThread inherit Example3.QuoteServerThread
// Also override its run (), so in order to simplify the original example of the run () we
// put him deleted.

public class QuoteServerThread extends Thread {
    // Notice that the server uses a DatagramSocket to broadcast packet received
    // by the client over a MulticastSocket. Alternatively, it could have used
    // a MulticastSocket. The socket used by the server to send the
    // DatagramPacket is not important. What's important when broadcasting
    // packets is the addressing information contained in the DatagramPacket,
    // and the socket used by the client to listen for it.
    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean moreQuotes = true;
    private static int TTL = 128;

    public QuoteServerThread() throws IOException {
        this("Example3.QuoteServerThread");
    }

    public QuoteServerThread(String name) throws IOException {
        super(name);

        // The port number doesn't actually matter in this example because
        // the client never send anything to the server.
        // Note DatagramSocket does not support setTimeToLive ()
        socket = new DatagramSocket(9990);

        try {
            String filePath = new File("").getAbsolutePath();
            in = new BufferedReader(new FileReader(filePath + "/src/Example3/one-liners.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("Could not open quote file. Serving time instead.");
        }
    }

    protected String getNextQuote() {
        String returnValue = null;
        try {
            if ((returnValue = in.readLine()) == null) {
                in.close();
                moreQuotes = false;
                returnValue = "No more quotes. Goodbye.";
            }
        } catch (IOException e) {
            returnValue = "IOException occurred in server.";
        }
        return returnValue;
    }
}