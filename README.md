UDP Multicast Example
=======================
# Multicast-Tester
Some example for multicast by Java


**Extracted from:** [http://web.nchu.edu.tw/~jlu/cyut/multicast.shtml](http://web.nchu.edu.tw/~jlu/cyut/multicast.shtml)

**DatagramSocket**s
===================

What is multicast?
All the socket and datagram programs that we have seen so far are for point-to-point communication and are usually called unicast. Although point-to-point communications are good for many applications, there are situations in which you need a different model. For example, in a electronic conferencing system, there is a one-to-many conversation: people may come and go as they please, bu the speaker (who may change from time to time) addresses a group rather than an individual. Although broadcast (ie. one-to-all communication) may serve for this purpose, its overhead is too large that make it an impractical solution.

Another approach is to use many unicasts for the one-to-many model. Although it is much efficient than broadcast, it is still inefficient since it duplicates data needlessly which is shown in the following figure. In the figure, one computer sends four copies of a message to four receivers. 



As a result, a model called multicast was developed. By the definition of Wihipedia, multicast is the delivery of information to multiple destinations simultaneously using the most efficient strategy to deliver the messages over the network over each link of the network only once and only create copies when the links to the destinations split. This can be shown in the following figure. As in the previous example, the message is only transmitted once across the network. 


 The word "Multicast" is typically used to refer to IP Multicast, which is a protocol for efficiently sending to multiple receivers at the same time on TCP/IP networks, by use of a multicast address. (Can you tell me now which IP protocol, TCP or UDP, is used in multicasting?) In summary, there are two issues need to be addressed more. One issue is related to how messages are transmitted. Usually, the task is handled by multicast-capable routers (or, in short, multicast routers or mrouters). With multicast routers, programmers will be relieved from complex routing tasks. However, one major disadvantage is that, if no multcast routers exist between any two hosts, there is no way for these two hosts to communicate using multicasting.

The other issue is related to the multicast address which is actually the class D address (in the range 224.0.0.0 to 239.255.255.255) that we discussed earlier. Therefore, every IP datagram whose destination address starts with "1110" is an IP Multicast datagram. The remaining 28 bits identify the multicast "group" the datagram is sent to. There are several special multicast groups, say "well known multicast groups", you should not use in your particular applications due the special purpose they are destined to:

    224.0.0.1 is the all-hosts group. If you ping that group, all multicast capable hosts on the network should answer, as every multicast capable host must join that group at start-up on all it's multicast capable interfaces. To see if there is any host that is multicast capable, do a ping 224.0.0.1 (or put an extra -s option for Solaris).
    224.0.0.2 is the all-routers group. All multicast routers must join that group on all it's multicast capable interfaces. To see if your local subnet supports multicast, do a ping all-routers.mcast.net and see if there is any response. If so, you subnet has a router that supports multicast.
    224.0.0.4 is the all DVMRP routers, 224.0.0.5 the all OSPF routers, 224.0.013 the all PIM routers, etc. 

When a host wants to send data to a multicast group, it puts that data in multicast datagrams, which are nothing more than UDP datagrams addressed to a multicast group. The only thing an application programmers needs to worry is about how long can a UDP datagram can live which is controlled by a Time-To-Live value. The valid value is in the range 0 to 255; it is roughly interpreted as the number of routers that a packet can pass through before it is discarded. However, a good TTL value is hard to determine. Usually, a TTL value of 16 limits the packet to the local area, generally one organization. A TTL of 127 sends the packet around the world. However, many organizations put hard restriction on multicast packets for performance reason.