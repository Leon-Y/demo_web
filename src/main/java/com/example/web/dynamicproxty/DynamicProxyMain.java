package com.example.web.dynamicproxty;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Auther: 36560
 * @Date: 2019/7/8 :20:50
 * @Description:
 */
class DynamicProxy implements InvocationHandler{
    private Object proxied;
    public DynamicProxy(Object proxied){
        this.proxied = proxied;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("**** proxy:"+proxy.getClass()+"method:"+method+",args"+args);
        if (args!= null){
            for (Object o:args){
                System.out.println(args);
            }
        }
        return method.invoke(proxied,args);
    }
}
class SimpleDynamicProxy{
    public static void consumer(SimpleProxyDemo.Interface interfaces){
        interfaces.doSomething();
        interfaces.doSomethingElse("do");
    }
    public static void main(String[] args){
        SimpleProxyDemo.realObject realObject = new SimpleProxyDemo().new realObject();
        consumer(realObject);
        SimpleProxyDemo.Interface anInterface = (SimpleProxyDemo.Interface) Proxy.newProxyInstance(SimpleProxyDemo.Interface.class.getClassLoader(),
                new Class[]{SimpleProxyDemo.Interface.class},
                new DynamicProxy(realObject));
        consumer(anInterface);
    }
}
public class DynamicProxyMain {
}
