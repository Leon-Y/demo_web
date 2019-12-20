package com.example.web.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Auther: 36560
 * @Date: 2019/8/15 :21:19
 * @Description:
 */
public class Callable {
    static class Fibonacci2 extends Fibonacci implements java.util.concurrent.Callable {
        public Fibonacci2(int n) throws Exception {
            super(n);
        }

        @Override
        public Object call() throws Exception {
            return this.getFibonacci();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayList<Future> futures = new ArrayList<>();
        for (int i = 0;i<20;i++){
            try {
                Future<?> submit = executorService.submit((java.util.concurrent.Callable<? extends Object>) (new Fibonacci2(10)));
                futures.add(submit);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Future o:futures){
            try {
                while (true){
                    if (o.isDone()){
                        System.out.println(Arrays.toString((int[])(o.get())));
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }
}
