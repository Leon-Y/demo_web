package com.example.web.collection;

import com.example.web.array.GeneratorProducer;
import org.apache.tomcat.util.http.ResponseUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Auther: 36560
 * @Date: 2019/8/27 :6:30
 * @Description:
 */
public class MapRemove {
    public static void main(String[] args) {
        HashMap<String, Character> stringCharacterHashMap = new HashMap<>();
        GeneratorProducer.stringGenerator stringGenerator = new GeneratorProducer.stringGenerator();
        GeneratorProducer.CharGenerator charGenerator = new GeneratorProducer.CharGenerator();
        for (int i =0;i<10;i++){
            stringCharacterHashMap.put(stringGenerator.next(),charGenerator.next());
        }
        System.out.println(stringCharacterHashMap);

        Iterator<Map.Entry<String, Character>> entryIterator = stringCharacterHashMap.entrySet().iterator();
        while (entryIterator.hasNext()){
            if (entryIterator.next().getKey().contains("b")){
                entryIterator.remove();
            }
        }
        System.out.println(stringCharacterHashMap);
        Iterator<String> iterator = stringCharacterHashMap.keySet().iterator();
        while (iterator.hasNext()){
            if (iterator.next().contains("a")){
                iterator.remove();
            }
        }
        System.out.println(stringCharacterHashMap);
    }
}
