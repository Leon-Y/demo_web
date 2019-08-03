package com.example.web.reflection;

/**
 * @Auther: 36560
 * @Date: 2019/7/6 :6:55
 * @Description:
 */
public class ReflectionTest {
    public void testReflect(){
        try {
            Class<?> aClass = Class.forName("com.example.web.reflection");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
