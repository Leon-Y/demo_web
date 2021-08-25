package com.example.web.test;

import java.util.Scanner;

/**
 * @Auther: 36560
 * @Date: 2021/6/27 :20:17
 * @Description:
 */
public class Demo1 {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        String[] split = s.split(" ");

        //设定步长初始为0,步长限制为1 - len/2;
        int stepLength;
        int minStep = 1000;
        for (stepLength = 1;stepLength < split.length/2;stepLength++){
            int result = result(split, stepLength);
            if ( -1 == result){
                continue;
            }else {
                if (minStep > result){
                    minStep = result;
                }
            }
        }

        System.out.println(minStep == 1000 ? -1 :minStep);

    }


    /**
     * 计算给步长的数字到达尾部所需要的步数
     * @param array 数组
     * @param step 步长
     * @return -1 无结果    else 完成步数
     */
    public static int result(String[] array,int step){
        int nextPosition = step;
        int steps = 2;
        while (nextPosition <= array.length -1){
//            steps++;
            nextPosition = Integer.valueOf(array[nextPosition]) + nextPosition;
            if (nextPosition > array.length - 1){
                break;
            }else if(nextPosition == (array.length - 1)){
                System.out.println(step+":"+steps);
                return steps;
            }else {
                steps++;
            }
        }
        return -1;
    }
}
