package com.test;

import java.util.Scanner;

/**
 * @Auther: 36560
 * @Date: 2021/7/3 :19:21
 * @Description:
 */
public class HW{
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int len = Integer.valueOf(scanner.nextLine());
        String[] strings = new String[len];
        for (int i = 0;i< len;i++){
            strings[i] = scanner.nextLine();
        }

        Collection collection = new Collection();
        for (int j = 0;j< len;j++){
            cal(strings[j],collection);
        }

        Thread.sleep(1000);
    }


    /**
     * 计算命令
     * @param cmd
     */
    private static void cal(String cmd,Collection collection){
        String[] split = cmd.split(" ");
        String comand = split[0];
        Integer integer = Integer.valueOf(split[1]);
        if ("push".equals(comand)){
            collection.push(integer);
//            System.out.println();
        }else {
            int pop = collection.pop(integer);
            if (-1 == pop){
                System.out.println("No Element!");
            }else {
                System.out.println(pop);
            }
        }
    }

    static class Collection{

        /**
         * 头节点
         */
        private Node head;

        /**
         * 尾节点
         */
        private Node tail;

        public int pop(int m){
            Node pop = pop(null == tail? head:tail, m);
            if (null == pop){
                return -1;
            }else {
                return pop.vlaue;
            }
        }

        /**
         * 从节点中查找
         * @param node
         * @return
         */
        public Node pop(Node node,int m){
            if (null == node){
                return null;
            }
            Node result = null;
            Node cur = node;
            do{
                int compare = cur.vlaue - m;
                if (compare == 0){
                    result = cur;
                    break;
                }else if (compare < 0){
                    result = cur;
                    cur = cur.pre;
                }else {
                    cur = cur.pre;
                    continue;
                }
            }while (cur != null);

            //推出当前节点
            if (null == result){
                return null;
            }else{
                if (result.next == null){
                    result.pre.next = null;
                    tail = result.pre;
//                break;
                }else{
                    result.next.pre = result.pre;
                    if (null != result.pre){
                        result.pre.next = result.next;
                    }
                }
            }
            return result;
        }

        /**
         * 推入
         * @param node
         */
        public void push(int node){
            if(null == head){
                head = new Node(node, null,null);
//                head.next = tail;
                tail = head;
            }else {
                Node nodeInner = new Node(node, null,null);
                if (tail.equals(head)){
                    tail = nodeInner;
                    head.next =tail;
                    tail.pre = head;
                }else{
                    Node nodeMid = tail;
                    tail = nodeInner;
                    nodeMid.next =tail;
                    nodeInner.pre = nodeMid;
                }
            }
        }

        /**
         * 节点
         */
        class Node{
            private int vlaue;
            private Node next;
            private Node pre;

            public Node(int vlaue,Node pre, Node next) {
                this.vlaue = vlaue;
                this.next = next;
                this.pre = pre;
            }
        }
    }
}
