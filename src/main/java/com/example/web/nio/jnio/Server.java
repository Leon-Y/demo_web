package com.example.web.nio.jnio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Auther: 36560
 * @Date: 2021/7/6 :6:34
 * @Description:
 */
public class Server {

    public static void main(String[] args) throws IOException {
        Server server = new Server(8088);
        server.start();
    }

    /**
     * 选择器
     */
    private Selector selector;

    /**
     * 端口号
     */
    private int port;

    public void start() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        this.selector = Selector.open();
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        while (true){
            int select = selector.select();
            if (select> 0){
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    handleMessage(iterator.next());
                    iterator.remove();
                }
            }
        }
    }

    public void handleMessage(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isAcceptable()){
            SocketChannel accept = ((ServerSocketChannel) selectionKey.channel()).accept();
            accept.configureBlocking(false);
            accept.register(selector,SelectionKey.OP_READ);
            System.out.println(accept.getRemoteAddress()+"上线");
        }
        if (selectionKey.isReadable()){
            SocketChannel channel = (SocketChannel) selectionKey.channel();
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            while (0 < channel.read(allocate)){
                allocate.flip();
                System.out.println("服务端收到客户端消息："+new String(allocate.array()));
                allocate.clear();
            }
            allocate.clear();
            allocate.put("服务端收到消息".getBytes());
            allocate.flip();
            channel.write(allocate);
        }
    }

    public Server(int port) {
        this.port = port;
    }
}
