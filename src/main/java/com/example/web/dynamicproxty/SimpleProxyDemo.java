package com.example.web.dynamicproxty;

import com.example.web.demo.Interfae;

/**
 * @Auther: 36560
 * @Date: 2019/7/8 :20:43
 * @Description:
 */
public class SimpleProxyDemo {
    interface Interface{
       void doSomething();
       void doSomethingElse(String... args);
    }
    class realObject implements Interface {

        @Override
        public void doSomething() {
            System.out.println("do something");
        }

        @Override
        public void doSomethingElse(String... args) {
            for (String string:args){
                System.out.println(string);
            }
        }
    }
    class SimpleProxy implements Interface{
        Interface anInterface ;
        public SimpleProxy (Interface anInterface){
            this.anInterface = anInterface;
        }
        @Override
        public void doSomething() {

        }

        @Override
        public void doSomethingElse(String... args) {

        }
    }
}
