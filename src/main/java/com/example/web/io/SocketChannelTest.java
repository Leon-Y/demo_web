package com.example.web.io;

import org.springframework.boot.ansi.AnsiOutput;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Auther: 36560
 * @Date: 2019/9/2 :19:46
 * @Description:
 */
public class SocketChannelTest {
    public static void main(String[] args) {
        try(Socket socket = new Socket("127.0.0.1", 8999);) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            Thread thread1 = new Thread(new MessageWriterRunnable(printWriter));
            thread1.start();
            while (true){
                String s = bufferedReader.readLine();
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class MessageWriterRunnable implements  Runnable{
    Writer writer;
    Scanner scanner = new Scanner(System.in);
    public MessageWriterRunnable(Writer writer){
        this.writer= writer;
    }
    @Override
    public void run() {
        while (true){
            String s = scanner.nextLine();
            try {
                writer.write(s+"\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
class MessageReaderRunnable implements Runnable {
    BufferedReader reader;
    public MessageReaderRunnable(BufferedReader reader){
        this.reader =reader;
    }
    @Override
    public void run() {
        try {
            while (true){
                String s = reader.readLine();
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class ServerSocketChannelTest{
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(8999);) {
            while (true){
                Socket accept = serverSocket.accept();
                System.out.println("客户端连接");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(accept.getOutputStream()));
                printWriter.write(accept.getLocalPort()+"已连接");
                Thread thread1 = new Thread(new MessageWriterRunnable(printWriter));
                thread1.start();
                while (true){
                    String s = bufferedReader.readLine();
                    System.out.println(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
