package com.example.web.exception;

/**
 * @Auther: 36560
 * @Date: 2019/7/3 :6:50
 * @Description:
 */
public class inheritException extends inheritClass implements interfaceClass{
    @Override
    void g() throws exception2 {

    }
    void h(){

    }
    @Override
    public void f() throws exception1{

    }

    @Override
    void k() throws exception2{

    }
}
abstract class inheritClass{
    inheritClass() throws exception1{}
    void f() throws RuntimeException{};
    void h() throws exception1{};
    abstract  void k();
    abstract void  g() throws exception1;
}
interface interfaceClass{
    void f() throws exception2;
}
class exception1 extends RuntimeException{

}
class exception2 extends RuntimeException{

}
