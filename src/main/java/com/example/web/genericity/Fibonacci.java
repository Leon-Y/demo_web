package com.example.web.genericity;

import java.util.Iterator;

/**
 * @Auther: 36560
 * @Date: 2019/7/11 :6:06
 * @Description:
 */
interface Gennerator<T>{
    T next();
}
public class Fibonacci implements Gennerator<Integer>{
    private int count=0;
    @Override
    public Integer next() {
        return fib(count++);
    }
    private int fib(int n){
        if (n<2) return 1;
        return fib(n-2)+fib(n-1);
    }
}
class FibonacciAdapter implements Iterable<Integer>{
    private Fibonacci fibonacci;
    private Integer count = 0;
    public FibonacciAdapter(int count,Fibonacci fibonacci){
        this.count=count;
        this.fibonacci =fibonacci;
    }
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return count>0;
            }

            @Override
            public Integer next() {
                count--;
                return fibonacci.next();
            }
            public void remove(){
                throw new UnsupportedOperationException();
            }
        };
    }
    public static void main(String[] args){
        FibonacciAdapter integers = new FibonacciAdapter(10, new Fibonacci());
        for(int integer :integers){
            System.out.println(integer);
        }
    }
}
