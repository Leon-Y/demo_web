package com.example.web.sort;

import java.util.Random;

/**
 * @Auther: 36560
 * @Date: 2019/7/1 :6:39
 * @Description:
 */
public class QucikSort {
    public static void sort(int[] num, int left, int right){
        if (left < right){
            int key = num[left];
            int keyPosition = left;
            /*从左往右排*/
            for (int i=left;i<=right;i++){
                if (num[i]<key && keyPosition<=i){
                    int temp = num[i];
                    num[i] = key;
                    num[keyPosition]=temp;
                    keyPosition=i;
                }
            }
            /*从右往左排*/
            for (int j = right;j >=left;j--){
                if (num[j] > key && keyPosition >= j){
                    int temp = num[j];
                    num[j] = key;
                    num[keyPosition] = temp;
                    keyPosition = j;
                }
            }
            if (left<right){
                sort(num,left,keyPosition-1);
                sort(num,keyPosition,right);
            }
        }
    }

    public static void main(String[] args) {
        Random random = new Random(47);
        int[] numArray = new int[10];
        for (int i =0;i<10;i++){
            numArray[i]=random.nextInt(10);
        }
        sort(numArray,0,numArray.length-1);
        System.out.println(numArray);
    }
}
