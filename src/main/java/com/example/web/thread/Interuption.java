package com.example.web.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: 36560
 * @Date: 2019/8/28 :7:39
 * @Description:
 */
public class Interuption {
}
class SleepBlocked implements Runnable{
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("InterruptException");
        }
        System.out.println("Exiting SleepBlocked.run");
    }
}
class IOBlock implements Runnable{
    private InputStream in;
    public IOBlock(InputStream in){
        this.in = in;
    }
    @Override
    public void run() {
        try {
            System.out.println("Waiting for reading");
            in.read();
        } catch (IOException e) {
            if (Thread.currentThread().isInterrupted()){
                System.out.println("Interrupted from blocked I/O");
            }else {
                throw new RuntimeException();
            }
        }
        System.out.println("Exiting IOBlocked.run");
    }
}
class SynchronizedBlock implements Runnable{
    public synchronized void f(){
        while (true){
            Thread.yield();
        }
    }
    public SynchronizedBlock(){
        new Thread(){
            public void run(){
                f();
            }
        }.start();
    }
    @Override
    public void run() {
        System.out.println("Trying to call f()");
        f();
        System.out.println("Exiting Synchronized .run");
    }
}
class Intrupting{
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    static void test(Runnable runnable) throws InterruptedException {
        Future<?> submit = executorService.submit(runnable);
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Interrupting "+ runnable.getClass().getName());
        submit.cancel(true);
        System.out.println("Interrupt send to "+ runnable.getClass().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        test(new SleepBlocked());
        test(new IOBlock(System.in));
        test(new SynchronizedBlock());
        TimeUnit.MILLISECONDS.sleep(3);
        System.out.println("Aborting with System.exit(0)");
        System.exit(0);
    }
}
class CloseResource{
    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        Socket socket = new Socket("www.baidu.com", 80);
        InputStream inputStream = socket.getInputStream();
        exec.execute(new IOBlock(inputStream));
        exec.execute(new IOBlock(System.in));
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Shutting down all threads");
        exec.shutdown();
        TimeUnit.MILLISECONDS.sleep(1000);
        System.out.println("Closing "+socket.getClass().getName());
        socket.close();
        TimeUnit.MILLISECONDS.sleep(1000);
        System.out.println("Closing "+ System.in.getClass().getName());
        System.in.close();
    }
}
class NIOBlocked implements Runnable{
    private final SocketChannel socketChannel;
    public NIOBlocked(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
    }
    @Override
    public void run() {
        try {
            System.out.println("Waiting for reading in "+this);
            socketChannel.read(ByteBuffer.allocate(1));
        } catch (ClosedByInterruptException e) {
            System.out.println("Closed by interrupt exception");
        }catch (AsynchronousCloseException e) {
            System.out.println("Closed by asynchronous exception");
        }catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Exiting NIOBlocked run"+this);
    }
}
class NIOInterruption{
    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(8080);
        InetSocketAddress isa = new InetSocketAddress("www.baidu.com", 80);
        SocketChannel open1 = SocketChannel.open(isa);
        SocketChannel open2 = SocketChannel.open(isa);
        Future<?> submit = exec.submit(new NIOBlocked(open1));
        exec.execute(new NIOBlocked(open2));
        exec.shutdown();
        TimeUnit.MILLISECONDS.sleep(1000);
        submit.cancel(true);
        TimeUnit.SECONDS.sleep(1);
        open2.close();
    }
}
class InterruptionTesting implements Runnable{

    @Override
    public void run() {
            try {
                while (true){
                    System.out.println("运行");
                    TimeUnit.MILLISECONDS.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println("线程中断,中断状态："+ Thread.currentThread().isInterrupted());
            }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new InterruptionTesting());
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        thread.interrupt();
    }
}
class BlockedMutex{
    private Lock lock = new ReentrantLock();
    public BlockedMutex(){
        lock.lock();
    }
    public void f(){
        try {
            lock.lockInterruptibly();
            System.out.println("lock acquire in f()");
        } catch (InterruptedException e) {
            System.out.println("Interrupted from lock acquire in f()");
        }
    }
}
class Blocked2 implements Runnable{
    private BlockedMutex mutex = new BlockedMutex();
    @Override
    public void run() {
        System.out.println("Waiting for f() in BlockedMutex");
        mutex.f();
        System.out.println("Broken out of blocked call");
    }
}
class Interruption2{
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Blocked2());
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Isuring t.interrupt");
        thread.interrupt();
    }
}