package com.example.web.enums;

import java.util.Random;

/**
 * @Auther: 36560
 * @Date: 2019/8/12 :21:09
 * @Description:枚举类生成器
 */
public class EnumGenerator {
    private static Random random = new Random(47);
    public static <T extends Enum<T>> T random(Class<T> ec){
        return random(ec.getEnumConstants());
    }
    public static <T> T random(T[] values){
        return values[random.nextInt(values.length)];
    }
}
