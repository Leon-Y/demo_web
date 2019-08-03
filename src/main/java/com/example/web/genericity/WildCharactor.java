package com.example.web.genericity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 36560
 * @Date: 2019/7/14 :14:18
 * @Description:
 */
class Apple extends Fruit{}
class Fruit {}
class Orange extends Fruit{}
public class WildCharactor {
    public static void main(String[] args) {
        Fruit[] fruits = new Apple[8];
        fruits[0] = new Apple();
        new Integer(0);
        try {
            fruits[1] = new Orange();
        }catch (Exception e){
            System.out.println("异常："+e.getMessage());
        }

    }
}

class GenericAndConvariance{
    public static void main(String[] args) {
        ArrayList<? extends Fruit> fruitList = new ArrayList<Apple>();
//        boolean add = fruitList.add(new Fruit()); 泛型不支持协变
    }
}
class Generic1<T>{
    T get(List<T> list){
        return list.get(0);
    };
}
