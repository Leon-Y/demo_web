package com.example.web.array;


import java.util.Arrays;
import java.util.Random;

/**
 * @Auther: 36560
 * @Date: 2019/7/18 :8:02
 * @Description:
 */
interface Generator<T>{
    T next();
}
public class GeneratorProducer {
    private static Random  r=new Random(47);
    static class CharGenerator implements Generator<Character>{
        public static final char[] charSet = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM".toCharArray();
        @Override
        public Character next() {
            return charSet[r.nextInt(charSet.length)];
        }
    }
    public static class stringGenerator implements Generator<String>{
        private int length=7;
        public stringGenerator(){}
        public stringGenerator(int length){
            this.length=length;
        }
        @Override
        public String next() {
            CharGenerator charGenerator = new CharGenerator();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i =0;i<length;i++){
                stringBuilder.append(charGenerator.next());
            }
            return stringBuilder.toString();
        }
    }
    public static class FloatGenerator implements Generator<Float>{

        @Override
        public Float next() {
            int round = Math.round(r.nextFloat() * 100);
            return ((float)round)/100;
        }
    }
    public static class DoubleGenerator implements Generator<Double>{

        @Override
        public Double next() {
            long round = Math.round(r.nextDouble() * 100);
            return ((double)round)/100;
        }
    }
    public static class IntegerGenerator implements Generator<Integer>{
        Integer mod = 10000;
        public IntegerGenerator(){}
        public IntegerGenerator(Integer mod){
            this.mod = mod;
        }
        @Override
        public Integer next() {
            return r.nextInt(mod);
        }
    }
    public static class LongGenerator implements Generator<Long>{
        private int mod = 10000;
        public LongGenerator(){}
        public LongGenerator(Integer mod){
            this.mod=mod;
        }
        @Override
        public Long next() {
            return new java.lang.Long(r.nextInt(mod));
        }
    }
    public static class ShortGenerator implements Generator<Short>{
        @Override
        public Short next() {
            return (short)r.nextInt();
        }
    }
}
