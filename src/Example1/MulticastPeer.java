package Example1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
/**
 * This example is to let each Example1.MulticastPeer program at the same time become a server is client. It's the job of sending out a message and waiting for the end of the three messages.
 */

public class MulticastPeer {
    public static void main(String[] args) {
        // args give message contents & destination multicast group
        // ex. java Example1.MulticastPeer "hello all" all-hosts.mcast.net
        MulticastSocket s = null;
        try {
            if (args.length < 2) {
                String[] newArgs = new String[2];
                newArgs[0] = "Hello All teammate!";
                newArgs[1] = "230.0.1.1";
                System.out.println("Argument is null So it use " + newArgs[1] + " as address and '" + newArgs[0] + "' as message");
                args = newArgs;
            }


            InetAddress group = InetAddress.getByName(args[1]);

            // The hard-coded port number is 9999 (the client must have a
            // MulticastSocket bound to this port).
            s = new MulticastSocket(9999);

            // the input InetAddress is the multicast group
            s.joinGroup(group);

            byte[] m = args[0].getBytes();

            // Used to output datagram
            DatagramPacket dp = new DatagramPacket(m, m.length, group, 9999);
            s.send(dp);

            // buffer used to receive
            byte[] buffer = new byte[1000];

            // Output a pen, receive three pen after the end
            for (int i = 0; i < 3; i++) {
                DatagramPacket indp = new DatagramPacket(buffer, buffer.length);
                s.receive(indp);
                System.out.println("Received: " + new String(indp.getData(), 0,
                        indp.getLength()));
            }
            s.leaveGroup(group);
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Misc: " + e.getMessage());
        } finally {
            if (s != null)
                s.close();
        }
    }
}