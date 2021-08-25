package com.example.web.test;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * @Auther: 36560
 * @Date: 2021/6/27 :20:17
 * @Description:
 */
public class Demo3 {

    /**
     * union题型，最大通量
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] ints = new int[n][m];
        for (int i = 0;i< n; i++){
            for (int j = 0;j < m ;j ++){
                int s = in.nextInt();
                ints[i][j] = s;
            }
        }

    }

    /**
     * 将二维数组转换为一维数组
     * @param ints
     * @return
     */
    public int[] toArray(int[][] ints){
        int[] result = new int[ints.length * ints[0].length];
        for (int i = 0;i< ints.length; i++){
            for (int j = 0;j < ints[0].length ;j ++){
                result[new BigDecimal(Math.pow(i,j)).intValue()]=ints[i][j];
            }
        }
        return result;
    }

    /**
     * 计算通量
     */
    public int[] calc(int[] array){
        int[] ints = new int[array.length];
        System.arraycopy(array,0,ints,0,array.length);
        //首位设置为0，表示该位置为0通量
        ints[0] = 0;
        //相邻值
        int a,b,c,d;
        for (int i = 1;i< array.length;i++){
            //前后两位数字为相邻数字，判断通量
            if (ints[i] != 1){
                return null;
            }
            a = i-2 < 0? -1: ints[i-2];
            b = i-1 < 0? -1: ints[i-1];
            c = i+1 >= ints.length? -1: ints[i+1];
            d = i-2 >= ints.length? -1: ints[i+2];
            if (a != -1){
                if (array[i-2] ==1 ){
                    ints[i] = ints[i-2];
                }
            }
            if (b != -1){
                if (array[i-1] ==1 ){
                    ints[i] = ints[i-1];
                }
            }
            if (c != -1){
                if (array[i+1] ==1 ){
                    ints[i+1] = ints[i];
                }
            }
            if (a != -1){
                if (array[i+2] ==1 ){
                    ints[i+2] = ints[i];
                }
            }
        }
        return  ints;
    }
}
