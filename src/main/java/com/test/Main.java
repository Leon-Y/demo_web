package com.test;

/**
 * @Auther: 36560
 * @Date: 2021/7/7 :21:01
 * @Description:
 */
public class Main {

    private static int[] data = {7, 9, 1, 2, 3, 4, 5, 6};

    public static void main(String[] args) {
        int i = get(data, 7);
        System.out.println(i);
    }

    /**
     * 旋转过的有序数组 12345679   79123456
     * 指定数字查找下标
     */
    public static int get(int[] data, int search) {
        int search1 = search(data, 0, data.length, search);

        return search1;
    }


    public static int search(int[] data, int lo, int hi, int search) {

        if (lo == hi) {
            if (data[lo] == search) {
                return lo;
            } else {
                return -1;
            }
        }
        int mid = lo + (lo + hi) / 2;
        int i = data[hi] - data[mid+1];
        if (hi == mid){

        }
        if (i > 0 && search <= data[hi] && search >= data[mid +1]) {
            int right = search(data, mid + 1, hi, search);
            return right;
        } else if (i < 0 && search >= data[lo] && search <= data[mid]){
            int left = search(data, lo, mid, search);
            return left;
        } else {
            return  -1;
        }
    }

//    public static int search(int[] data, int lo, int hi, int search) {
//
//        if (lo == hi) {
//            if (data[lo] == search) {
//                return lo;
//            } else {
//                return -1;
//            }
//        }
//        int mid = lo + (lo + hi) / 2;
//        int i = data[mid + 1] - data[mid];
//        if (i > 0 && search <= data[hi] && search >= data[mid +1]) {
//            int right = search(data, mid + 1, hi, search);
//            return right;
//        } else{
//            int left = search(data, lo, mid, search);
//            return left;
//        }
//    }


}
