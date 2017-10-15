package Example3;

/**
 * In this example, the server broadcasts quotes at a regular interval. The client passively listens for quotes and does so on a MulticastSocket.
 * To implement the following program, the server-side program requires a plain text file one-liners.txt, you can design, or download link example. The following example is taken from Sun's Broadcasting to Multiple Recipients.
 */

public class MulticastServer {
    public static void main(String[] args) throws java.io.IOException {
        new MulticastServerThread().start();
    }
}