package com.example.web.collection;

import java.util.*;

/**
 * @Auther: 36560
 * @Date: 2019/7/25 :7:06
 * @Description:
 */
public class Countries {
    public static final String[][] countries= {
            {"china","beijin"},
            {"hongkong","hongkong"}
    };
    private static class CountryMap extends AbstractMap<String,String> {
        private static class Entry implements Map.Entry<String,String> {
            int index;
            Entry(int index){this.index=index;}
            @Override
            public boolean equals(Object o){
                return countries[index][0].equals(o);
            }
            @Override
            public int hashCode(){
                return countries[index][0].hashCode();
            }
            @Override
            public String getKey() {
                return countries[index][0];
            }

            @Override
            public String getValue() {
                return countries[index][1];
            }

            @Override
            public String setValue(String value) {
                throw new UnsupportedOperationException();
            }
        }
        static class EntrySet extends AbstractSet<Map.Entry<String,String>> {
            int size;
            EntrySet(int size) {
                //can't be bigger than countries
                if (size < 0) {
                    this.size = 0;
                } else if (size > countries.length) {
                    this.size = countries.length;
                } else {
                    this.size = size;
                }
            }
            private class Iter implements Iterator<Map.Entry<String,String>>{
                private Entry entry = new Entry(-1);
                @Override
                public boolean hasNext() {
                    return entry.index < (size -1);
                }

                @Override
                public Map.Entry<String, String> next() {
                    entry.index++;
                    return entry;
                }
                public void remove(){
                    throw new UnsupportedOperationException();
                }
            }
            @Override
            public Iterator<Map.Entry<String, String>> iterator() {
                return new Iter();
            }

            @Override
            public int size() {
                return this.size;
            }
        }
        private static Set<Map.Entry<String,String>> entries = new EntrySet(countries.length);
        @Override
        public Set<Map.Entry<String,String>> entrySet(){
            return entries;
        }
        static Map<String,String> select(final int size){
            return new CountryMap(){
                public Set<Map.Entry<String,String>> entrySet(){
                    return new EntrySet(size);
                }
            };
        }
        static Map<String,String> map = new CountryMap();
        public static Map<String,String> countries(){
            return map;
        }
        public static Map<String,String> countries(int size){
            return select(size);
        }
        static List<String> names = new ArrayList<String>(map.keySet());
        public static List<String> names(){
            return names;
        }
        public static List<String> names(int size){
            return new ArrayList<String>(select(size).keySet());
        }
        public static void main(String[] args){
            System.out.println(countries(10));
        }
    }
}
