package com.example.web.dynamicproxty;

import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Scanner;

/**
 * @Auther: 36560
 * @Date: 2019/7/8 :21:20
 * @Description:
 */
public class Transaction {
    public static void main(String[] args){
        TransactionProxy transactionProxy = new TransactionProxy(new FileWriter());
        TransactionInterface transactionInterface = (TransactionInterface) Proxy.newProxyInstance(TransactionInterface.class.getClassLoader(), new Class[]{TransactionInterface.class}, transactionProxy);
        transactionInterface.commit();
        transactionInterface.roolback();
    }
}
interface TransactionInterface{
    void commit();
    void roolback();
}
class FileWriter implements TransactionInterface{

    @Override
    public void commit() {
        try (
                Scanner scanner = new Scanner(System.in);
                PrintWriter printWriter = new PrintWriter("file.txt", "UTF-8");
        ) {
            printWriter.append("commit 操作执行");
            boolean b = printWriter.checkError();
            System.out.println(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void roolback() {
        try (
                Scanner scanner = new Scanner(System.in);
                PrintWriter printWriter = new PrintWriter("file.txt", "UTF-8");
        ) {
            printWriter.append("roolback 操作执行");
            boolean b = printWriter.checkError();
            System.out.println(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        FileWriter fileWriter = new FileWriter();
        fileWriter.roolback();
        fileWriter.commit();
    }
}
class TransactionProxy implements InvocationHandler {
    private TransactionInterface transactionInterface;
    public TransactionProxy(TransactionInterface transactionInterface){
        this.transactionInterface = transactionInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(transactionInterface,args);
    }
}

