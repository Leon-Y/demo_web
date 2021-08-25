package com.example.web.nio.jnio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @Auther: 36560
 * @Date: 2021/7/6 :6:35
 * @Description:
 */
public class Client {

    private int port;
    private String host;
    private Selector selector;
    private SocketChannel socketChannel;

    public Client(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void write(String data) throws IOException {
        ByteBuffer allocate = ByteBuffer.wrap(data.getBytes());
        socketChannel.write(allocate);
    }

    public void start() throws IOException {
        socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
        socketChannel.configureBlocking(false);
//        socketChannel.connect(new InetSocketAddress(host, port));
        System.out.println("客户端连接成功："+socketChannel.getRemoteAddress());
        selector = Selector.open();
        socketChannel.register(selector,SelectionKey.OP_READ);

        while (true){
            int select = selector.select();
            if (select > 0){
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    handle(iterator.next());
                    iterator.remove();
                }
            }
        }
    }

    public void handle(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isReadable()){
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            while (((SocketChannel) selectionKey.channel()).read(allocate) > 0){
                allocate.flip();
                System.out.println("收到服务端字节："+new String(allocate.array()));
                allocate.clear();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client(8088, "127.0.0.1");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Scanner scanner = new Scanner(System.in);
        while (true){
            String s = scanner.nextLine();
            client.write(s);
            System.out.println("写入："+s);
        }
    }
}
