package com.example.web.genericity;

/**
 * @Author: Administrator
 * @Date: 2019/11/27 :14:06
 * @Description:
 */
public class Parameter {

    private void testParam(){
        String s = genericityParam(String.class);
        Integer integer = genericityParam(Integer.class);
        parameter2 parameter2 = new parameter2();
//        String s1 = parameter2.genericityParam(String.class);
//        Integer integer1 = parameter2.anotherParam(Integer.class);
//        String s2 = parameter2.anotherParam2("222");
    }
    public <T> T genericityParam(Class<T> clazz){
        try {
            T t1 = clazz.newInstance();
            return t1;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
       return null;
    }
}
class parameter2<T>{
    public <E> E genericityParam(Class<E> clazz){
        try {
            E t1 = clazz.newInstance();
            return t1;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    public <K> K anotherParam(Class<K> clazz){
        try {
            K k = clazz.newInstance();
            return k;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    public <G> G anotherParam2(G g){
        return g;
    }
}
