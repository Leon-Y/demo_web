package com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @Auther: 36560
 * @Date: 2021/7/4 :16:15
 * @Description:
 */
public class Nio {
    public static void main(String[] args) {
        try {
            Selector open = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(7000));
            serverSocketChannel.configureBlocking(false);
            SelectionKey register = serverSocketChannel.register(open, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
