package com.example.web.thread;

import javax.xml.stream.events.StartDocument;
import java.util.Arrays;
import java.util.Random;

/**
 * @Auther: 36560
 * @Date: 2019/8/15 :7:01
 * @Description:
 */
public class ThreadTest {
    public static void main(String[] args) {
        Thread thread = new Thread(new LightOff());
        thread.start();
    }
}
class LightOff implements Runnable{
    protected int countDown=10;
    private static int taskCount = 0;
    private final int id=taskCount++;
    public LightOff(){};
    public LightOff(int countDown){
        this.countDown=countDown;
    }
    public String status(){
        return "#"+id+"("+(countDown>0?countDown:"lightOff")+")";
    }
    @Override
    public void run() {
        while (countDown-->0){
            System.out.print(status());
            Thread.yield();
        }
    }
}
class test2{
    public static void main(String[] args) {
        for (int i=0;i<5;i++){
            Thread thread = new Thread(new LightOff(20));
            thread.start();
        }
    }
}
class Print implements Runnable{
    private int defaultNum = 10;
    private static int countDown = 0;
    private final int id = countDown++;
    public Print(){
        System.out.println("-------开始打印消息---------");
    };
    public Print(int num){
        System.out.println("-------开始打印消息---------");
        this.countDown = num;
    }
    @Override
    public void run() {
        while (defaultNum-->0){
            System.out.println("我是第"+countDown+"行");
            Thread.yield();
        }
    }

    public static void main(String[] args) {
        for (int i=0;i<3;i++){
            Thread thread = new Thread(new Print());
            thread.run();
        }
    }
}

/**
 * 创建斐波那契数组
 */
class Fibonacci implements Runnable{
    private int n ;
    public Fibonacci(int n) throws Exception {
        if (n<3){
            throw new Exception("n需要大于等于3");
        }
        this.n=n;
    }
    public int [] getFibonacci(){
        int count = 0;
        int[] fibonacci = new int[n];
        while (count < n) {
            if (count ==0){
                fibonacci[count]=1;
                count++;
                continue;
            }
            if (count==1){
                fibonacci[count]=1;
                count++;
                continue;
            }
            fibonacci[count]=fibonacci[count-1]+fibonacci[count-2];
            count++;
        }
        return fibonacci;
    }
    @Override
    public void run() {
        System.out.println(Arrays.toString(getFibonacci()));
    }

    public static void main(String[] args) {
        Random random = new Random(47);
        for (int i=0;i<100;i++){
            try {
                Thread thread = new Thread(new Fibonacci(random.nextInt(20)));
                thread.start();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
