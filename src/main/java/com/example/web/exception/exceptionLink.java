package com.example.web.exception;

/**
 * @Auther: 36560
 * @Date: 2019/7/3 :6:08
 * @Description:
 */
public class exceptionLink {
    public static void f() throws exceptionOne {
        System.out.println("cause from f()");
        throw new exceptionOne();
    }
    public static void h() throws exceptionTwo {

        try {
            f();
        } catch (com.example.web.exception.exceptionOne exceptionOne) {
            System.out.println("from h()");
            exceptionTwo exceptionTwo = new exceptionTwo();
            exceptionTwo.initCause(exceptionOne);
            throw exceptionTwo;
        }
    }
    public static void g() throws Throwable {
        try {
            h();
        } catch (com.example.web.exception.exceptionTwo exceptionTwo) {
            System.out.println("from g()");
            throw exceptionTwo.fillInStackTrace();
        }
    }
    public static String k(){
        try {

            return "打印返回值";
        } catch (Exception exceptionOne) {
            System.out.println("from k()");
            exceptionOne.printStackTrace();
        }finally {
            System.out.println("执行清理工作");
        }
        return null;
    }
    public static void main(String[] args){
        try {
            f();
        } catch (com.example.web.exception.exceptionOne exceptionOne) {
            System.out.println("from main()");
            exceptionOne.printStackTrace(System.out);
        }
        try {
            h();
        } catch (com.example.web.exception.exceptionTwo exceptionTwo) {
            System.out.println("from main()");
            exceptionTwo.printStackTrace(System.out);
        }
        try {
            g();
        } catch (Throwable throwable) {
            System.out.println("from main()");
            throwable.printStackTrace(System.err);
        }
        System.out.println(k());
    }
}
class exceptionOne extends Exception{

}
class exceptionTwo extends Exception{

}