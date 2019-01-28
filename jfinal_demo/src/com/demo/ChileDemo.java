package com.demo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

public class ChileDemo {
	static int i = 0;
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        MulticastSocket multicastSocket = new MulticastSocket(10000); // 接收数据时需要指定监听的端口号
        InetAddress address = InetAddress.getByName("239.0.0.1");
        multicastSocket.joinGroup(address);
        byte[] buf = new byte[1024];
        
        while (true) {
            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
            multicastSocket.receive(datagramPacket); // 接收数据，同样会进入阻塞状态
            byte[] message = new byte[datagramPacket.getLength()]; // 从buffer中截取收到的数据
//            System.arraycopy(buf, 0, message, 0, datagramPacket.getLength());
//            System.out.println(datagramPacket.getAddress());
//            System.out.println(new String(message));
            ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
            for(int i =0;i<20;i++){
            	HashMap<String,Object>map = new HashMap<String,Object>();
            	list.add(map);
            }
            System.out.println(++i);
        }
	}
}
