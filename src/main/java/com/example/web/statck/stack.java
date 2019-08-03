package com.example.web.statck;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @Auther: 36560
 * @Date: 2019/7/1 :5:40
 * @Description:
 */
public class stack<T> {
    private LinkedList storage = new LinkedList<T>();
    public void push(T v){
        storage.addFirst(v);
    }
    public T pop(){
        return (T) storage.removeFirst();
    }
    public T peek(){
        return (T) storage.getFirst();
    }
    public boolean empty(){
        return storage.isEmpty();
    }
    public String toString(){
        return storage.toString();
    }
    public static void main(String[] args){
        stack<Character> characterstack = new stack<>();
        Stack<Character> characters = new Stack<>();
    }
}
