package com.example.web.enums;

import java.util.EnumSet;

/**
 * @Auther: 36560
 * @Date: 2019/8/12 :21:26
 * @Description:
 */
enum Activity{SITINNG,LYING,STANDING,HOPPING,RUNNING,DODGING,JUMOING,FAILING,FLYING}
public class EnumTest {
    public static void main(String[] args) {
        for (int i=0;i<20;i++){
            System.out.print(EnumGenerator.random(Activity.class)+ " ");
        }
    }
}
