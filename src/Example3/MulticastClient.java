package Example3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * In this example, the server broadcasts quotes at a regular interval. The client passively listens for quotes and does so on a MulticastSocket.
 * To implement the following program, the server-side program requires a plain text file one-liners.txt, you can design, or download link example. The following example is taken from Sun's Broadcasting to Multiple Recipients.
 */

public class MulticastClient {
    public static void main(String[] args) throws IOException {

        String groupAddress = "230.0.0.1";
        int groupPort = 9999;
        System.out.println("Client started. Join group on " + groupAddress + ":" + groupPort);

        MulticastSocket socket = new MulticastSocket(groupPort);
        InetAddress address = InetAddress.getByName(groupAddress);
        socket.joinGroup(address);

        DatagramPacket packet;

        System.out.println("Listen to quotes...");
        // get a few quotes
//        for (int i = 0; i < 10; i++) {
        boolean breakLoop = false;
        while(!breakLoop){
            byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            String received = new String(packet.getData());
            System.out.println("Quote of the Moment: " + received);
        }

        socket.leaveGroup(address);
        socket.close();

        System.out.println("Client ends.");
    }
}