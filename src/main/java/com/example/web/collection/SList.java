package com.example.web.collection;

import com.sun.org.apache.xerces.internal.xs.StringList;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @Auther: 36560
 * @Date: 2019/7/26 :7:44
 * @Description: 自定义实现堆栈
 */
public class SList<T> implements Iterable<T>{
    private static class Node<U>{
        Node<U> node ;
        U item =null;
        Node(){};
        Node(Node node,U item){
            this.node= node;
            this.item = item;
        }
        boolean end(){
            return item==null && node==null;
        }
    }
    private Node<T> top = new Node<T>();
    public void push(T item){
        top=new Node<T>(top,item);
    }
    public T pop(){
        T result = top.item;
        if (!top.end()){
            top=top.node;
        }
        return result;
    }
    private class iter implements Iterator<T>{
        @Override
        public boolean hasNext() {
            return !top.end();
        }

        @Override
        public T next() {
            T result = top.item;
            if (!top.end()){
                top=top.node;
            }
            return result;
        }

    }
    private Iterator<T> iter = new iter();
    public Iterator<T> iterator(){
        return iter;
    }
    public static void main(String[] args) {
        SList<String> stringSList = new SList<>();
        stringSList.push("wo");
        stringSList.push("www");
//        System.out.println(stringSList.pop()+stringSList.pop()+stringSList.pop()+stringSList.pop());
        for (String string:stringSList ) {
            System.out.println(string);
        }
        Iterator iterator = stringSList.iterator();
        Object next = iterator.next();
        System.out.println(next);
    }
}
