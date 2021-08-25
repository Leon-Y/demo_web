package com;

import java.util.Scanner;

/**
 * @Auther: 36560
 * @Date: 2021/7/1 :20:27
 * @Description:
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int nextInt = scanner.nextInt();
        for (int i = 1;i<= nextInt;i++){

            for (int space = nextInt;space > 1;space--){
                if (i - space < 0){
                    System.out.print(" ");
                }else {
                    System.out.print(i -space + 1);
                }
            }
            System.out.print(i);
            for (int space = 1;space < (nextInt );space++){
                if (i - space <= 0){
                    System.out.print(" ");
                }else {
                    System.out.print(i-space);
                }
            }
            System.out.println();
        }
    }
}
