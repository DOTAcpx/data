package com.demo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocketDemo {
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        MulticastSocket multicastSocket = new MulticastSocket();
        InetAddress address = InetAddress.getByName("239.0.0.1"); // 必须使用D类地址
        multicastSocket.joinGroup(address); // 以D类地址为标识，加入同一个组才能实现广播

        while (true) {
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            byte[] buf = time.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
            datagramPacket.setAddress(address); // 接收地址和group的标识相同
            datagramPacket.setPort(10000); // 发送至的端口号

            multicastSocket.send(datagramPacket);
        }
    }
}
