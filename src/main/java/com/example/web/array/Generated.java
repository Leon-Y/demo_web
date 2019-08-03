package com.example.web.array;

import javax.validation.constraints.Size;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @Auther: 36560
 * @Date: 2019/7/19 :21:13
 * @Description:
 */
public class Generated {
    public static <T> T[] array(Class<T> type , Generator<T> generator, int size){
        T[] o = (T[])Array.newInstance(type, size);
        for (int i=0;i<size;i++){
            T next = generator.next();
            o[i] = next;
        }
        return o;
    }

    public static void main(String[] args) {
        String[] array = Generated.array(String.class, new GeneratorProducer.stringGenerator(), 10);
        System.out.println(Arrays.toString(array));
    }
}
