package com.example.web.test;

import java.util.*;

/**
 * @Auther: 36560
 * @Date: 2021/6/27 :20:17
 * @Description:
 */
public class Demo2 {
    /**
     * 输出排序字母数量
     * @param args
     */
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        char temp = chars[0] ;
        NumberChar numberChar =  new NumberChar(1, chars[0]);
        ArrayList<NumberChar> numberChars = new ArrayList<>();
        numberChars.add(numberChar);
        for (int i = 0;i< chars.length;i++){
            if (i == 0){
                continue;
            }
            if (temp != chars[i] && i != 0){
                temp = chars[i];
                numberChar = new NumberChar(1, chars[i]);
                numberChars.add(numberChar);
            }else {
                numberChar.setNum(numberChar.getNum() + 1);
            }
        }
        Collections.sort(numberChars);
        Iterator<NumberChar> iterator = numberChars.iterator();
        while (iterator.hasNext()){
            System.out.print(iterator.next());
        }
    }


    /**
     * 比较类
     */
    static class NumberChar implements Comparable{
        private int num;
        private char character;

        public NumberChar(int num, char character) {
            this.num = num;
            this.character = character;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public char getCharacter() {
            return character;
        }

        public void setCharacter(char character) {
            this.character = character;
        }

        @Override
        public int compareTo(Object o) {
            if (null == o){
                return 1;
            }
            if (o instanceof NumberChar){
                NumberChar numberChar = (NumberChar) o;
                if (numberChar.getNum() > this.num){
                    return 1;
                }else if (numberChar.getNum() < this.num){
                    return -1;
                }
                else {
                    if ((numberChar.getCharacter() >= 97 && this.character >= 97) || (numberChar.getCharacter() <= 90 && this.character <= 90) ){
                        if (numberChar.getCharacter() > this.character){
                            return -1;
                        }else if (numberChar.getCharacter() < this.character){
                            return 1;
                        }else {
                            return 0;
                        }
                    }else {
                        if (numberChar.getCharacter() > this.character){
                            return 1;
                        }else if (numberChar.getCharacter() < this.character){
                            return -1;
                        }else {
                            return 0;
                        }
                    }

                }
            }
            return 1;
        }

        @Override
        public String toString() {
            return character+":"+num+";";
        }
    }

}
