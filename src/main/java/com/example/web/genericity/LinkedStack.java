package com.example.web.genericity;

/**
 * @Auther: 36560
 * @Date: 2019/7/10 :7:47
 * @Description:
 */
public class LinkedStack<T> {
    /**
     * 静态内部类
     * 1.不可访问外部类的非静态参数；
     * 2.不可访问外部的泛型类型
     * 非静态内部类以上都可以
     * @param <U>
     */
    private static class Node<U>{
        U item;
        Node next;
        Node(){
            item=null;
            next = null;
        }
        Node(U item,Node next){
            this.item = item;
            this.next=next;
        }
        boolean end(){
            return item==null&&next==null;
        }
    }
    private Node<T> top = new Node<T>();
    public T pop(){
        T result = top.item;
        if (!top.end()){
            top=top.next;
        }
        return result;
    }
    public void push(T item){
        top = new Node<T>(item,top);
    }

    public static void main(String[] args) {
        LinkedStack<String> stringLinkedStack = new LinkedStack<>();
        stringLinkedStack.push("big boss");
        stringLinkedStack.push("snopy");
        stringLinkedStack.push("wiledwolf");
        String s;
        while ((s =stringLinkedStack.pop())!=null){
            System.out.println(s);
        }
    }
}
