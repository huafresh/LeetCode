package com.hua.leetcode_core.datastructure.out_sort;//package com.hua.datastructrue_java.out_sort;
//
//import android.content.Context;
//
//import java.io.BufferedReader;
//import java.io.Closeable;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
///**
// * 生成随机数据，写入文件
// * Created by hua on 2019/2/16.
// */
//
//public class Util {
//
//    private static final int RANDOM_INT_NUM = 20;
//    private static final String BAT_SEGMENT_PREFIX = "randomSegment";
//
//    public static File createRandomFile(Context context) {
//        Random random = new Random();
//        File file = new File(context.getExternalCacheDir(), "random.txt");
//        PrintWriter pw = null;
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            pw = new PrintWriter(fos);
//            for (int i = 0; i < RANDOM_INT_NUM; i++) {
//                int randomInt = random.nextInt(10000);
//                pw.println(randomInt);
//            }
//            pw.flush();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            closeQuietly(pw);
//        }
//
//        return file;
//    }
//
//    /**
//     * 从file中读取数据到内存的index段，返回实际读取的数据个数。
//     */
//    public static int readFromFile(SegmentMemory memory, int index, File file) {
//        memory.invalidSegment(index);
//
//        FileInputStream fis = null;
//        BufferedReader reader = null;
//        int num = 0;
//        try {
//            fis = new FileInputStream(file);
//            reader = new BufferedReader(new InputStreamReader(fis));
//            String line = null;
//
//            while ((line = reader.readLine()) != null) {
//                int data = Integer.valueOf(line);
//                if (!memory.add(index, data)) {
//                    break;
//                }
//                num++;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            Util.closeQuietly(reader);
//        }
//        return num;
//    }
//
//
//    public static List<File> splitAndSortSegmentFile(Context context, File source, Memory memory) {
//        List<File> result = new ArrayList<>();
//        FileInputStream fis = null;
//        BufferedReader reader = null;
//        try {
//            fis = new FileInputStream(source);
//            reader = new BufferedReader(new InputStreamReader(fis));
//            String line = null;
//            int segmentNum = 0;
//            while ((line = reader.readLine()) != null) {
//                int data = Integer.valueOf(line);
//                if (!memory.add(data)) {
//                    //如果满了则排序后写入临时文件
//                    memory.sort2();
//                    File batFile = new File(context.getExternalCacheDir(),
//                            BAT_SEGMENT_PREFIX + segmentNum + ".txt");
//                    result.add(batFile);
//                    PrintWriter writer = new PrintWriter(new FileOutputStream(batFile));
//                    try {
//                        for (int i = 0; i < memory.size(); i++) {
//                            writer.println(memory.get(i));
//                        }
//                    } finally {
//                        Util.closeQuietly(writer);
//                    }
//                    segmentNum++;
//                    memory.clear();
//                }
//            }
//
//            if (!memory.isEmpty()) {
//                //不满也写入临时文件
//                memory.sort2();
//                File batFile = new File(context.getExternalCacheDir(),
//                        BAT_SEGMENT_PREFIX + segmentNum + ".txt");
//                result.add(batFile);
//                PrintWriter writer = new PrintWriter(new FileOutputStream(batFile));
//                try {
//                    for (int i = 0; i < memory.size(); i++) {
//                        writer.println(memory.get(i));
//                    }
//                } finally {
//                    Util.closeQuietly(writer);
//                }
//                memory.clear();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            Util.closeQuietly(reader);
//        }
//
//        return result;
//    }
//
//    public static void sort(int[] data, int offset, int len) {
//        //简单起见，就采用冒泡排序
//        for (int i = offset; i < len; i++) {
//            for (int j = i + 1; j < len; j++) {
//                if (data[i] > data[j]) {
//                    int tmp = data[i];
//                    data[i] = data[j];
//                    data[j] = tmp;
//                }
//            }
//        }
//    }
//
//    static void closeQuietly(Closeable closeable) {
//        if (closeable != null) {
//            try {
//                closeable.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
