package com.example.web.genericity;

/**
 * @Auther: 36560
 * @Date: 2019/7/10 :7:18
 * @Description:
 */
public class HolderTest {
    public void test(){
        Holder<Inner> innerHolder = new Holder<>(new Inner());
        innerHolder.inner = new ExtendsInner();
    }
}
  class Holder<T>{
    public  T inner;
    Holder(T inner){
        this.inner = inner;
    }
}
class Inner{}
class ExtendsInner extends Inner{}
