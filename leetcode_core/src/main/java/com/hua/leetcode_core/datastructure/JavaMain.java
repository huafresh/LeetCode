package com.hua.leetcode_core.datastructure;


import java.util.Random;

/**
 * @author hua
 * @version V1.0
 * @date 2019/2/18 16:20
 */

public class JavaMain {
private static int[] testArray = new int[]{138, 148, 9, 8, 71, 173, 111, 10, 156, 177, 39, 17, 8, 61, 123, 30, 28, 23, 104, 66};
    private static int[] array = new int[]{
            97,
            38,
            73,
            192,
            87,
            196,
            162,
            132,
            10,
            80
//            112,
//            93,
//            51,
//            122,
//            20,
//            124,
//            188,
//            110,
//            43,
//            99,
    };

    public static void main(String[] args) {
        int[] data = new int[20];
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            data[i] = random.nextInt(200);
        }

        System.out.println("before sort: ");
        printArray(data);
        SortUtil.radixSort(data);
        System.out.println("after sort: ");
        printArray(data);

//        System.out.println("before rb sort:");
//        printArray(data);
//        SearchTree.sortWithRBTree(array);
//        System.out.println("after rb sort:");
//        printArray(array);

//        SearchTree<Integer> st = new SearchTree<>();
//        for (int i : array) {
//            st.insert(new TreeNode<Integer>(i));
//        }
//        st.graphPrint();

//        System.out.println(" __20__ ");
//        System.out.println(" |     |");
//        System.out.println("12     40");
    }

    public static void printArray(int[] data) {
        for (int i : data) {
            System.out.print(i + ", ");
        }
        System.out.println(" ");
    }

}
