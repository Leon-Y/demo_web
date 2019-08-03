package com.example.web.collection;

import com.example.web.array.GeneratorProducer;

import java.util.*;

/**
 * @Auther: 36560
 * @Date: 2019/8/1 :6:20
 * @Description:
 */
public class SortedMap {
    class SortedTest{
        private List<String> list=new ArrayList<String>();
        public void sort(){
            list.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.length()-o2.length();
                }
            });
            list.sort((o,w)->{return 2;});
        }
    }
    static class SortedSetDemo{
        public static void main(String[] args) {
            SortedSet<String> strings = new TreeSet<>();
            Collections.addAll(strings,"qweqe qweqwe qweqwer".split(" "));
            System.out.println(strings);
            GeneratorProducer.stringGenerator stringGenerator = new GeneratorProducer.stringGenerator(10);
            for (int i=0;i<10;i++){
                strings.add(stringGenerator.next());
            }
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
            System.out.println(strings);
        }
    }
}
