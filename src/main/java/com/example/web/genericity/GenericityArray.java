package com.example.web.genericity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @Auther: 36560
 * @Date: 2019/7/14 :10:06
 * @Description:
 */
public class GenericityArray<T> implements Iterable  {
    private ArrayList<T> innerArray = new ArrayList<T>();
    public void add(T item){
        innerArray.add(item);
    }
    public T get(int index){
        return innerArray.get(index);
    }

    @Override
    public Iterator iterator() {
        return innerArray.iterator();
    }
}
class test{
    public static void main(String[] args){
        GenericityArray<Integer> integerGenericityArray = new GenericityArray<>();
        for (int i = 0;i<10;i++){
            integerGenericityArray.add(i);
            System.out.println("添加："+i);
        }
        for (Object integer:integerGenericityArray){

        }
    }
}
class Generic<T>{}
class ArrayOfgenericRefference{
    static Generic<Integer>[] gia = new Generic[8];
    private Integer[] integerArray= new Integer[8];
    public static void main(String[] args){
        gia = (Generic<Integer>[])new Generic[8];
        gia = (Generic<Integer>[])new Object[8];
        System.out.println(gia.getClass().getSimpleName());
        gia[0]=new Generic<Integer>();
    }
}
class genericArray<T>{
    private T[] array;
    public genericArray(int size){
        this.array= (T[])new Object[size];
    }
    public void put(int index,T item){
        array[index] = item;
    }
    public T get(int index){
        return (T)array[index];
    }
    public T[] rep(){
        return array;
    }
    public static void main(String[] args){
        genericArray<Integer> integergenericArray = new genericArray<>(8);
        Integer[] rep = integergenericArray.rep();
    }

}
class GenericArrayWithTypeToken<T>{
    private T[] array;
    public GenericArrayWithTypeToken(Class<T> kind,int size){
        array=(T[])Array.newInstance(kind,size);
    }
    public void put(int index,T item){
        array[index] = item;
    }
    public T get(int index){
        return array[index];
    }
    public T[] rep(){
        return array;
    }

    public static void main(String[] args) {
        GenericArrayWithTypeToken<Integer> integerGenericArrayWithTypeToken = new GenericArrayWithTypeToken<Integer>(Integer.class,8);
        Integer[] rep = integerGenericArrayWithTypeToken.rep();
    }

}