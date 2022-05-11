package com.hua.leetcode_core.datastructure.out_sort;//package com.hua.datastructrue_java.out_sort;
//
//
//import java.io.File;
//import java.util.List;
//
///**
// * 外排序
// * Created by hua on 2019/2/16.
// */
//
//public class OutSort {
//
//
//
//    /**
//     * 假设可用内存大小为500，其中输入缓存大小为400，输出缓存大小为100.
//     *
//     * @param source 待排序文件(10000个数据)
//     */
//    public static void outSortWithMerge(Context context, File source) {
//        Memory memory = new Memory();
//        List<File> segmentFiles = Util.splitAndSortSegmentFile(context, source, memory);
//
//        SegmentMemory sm = new SegmentMemory(context, segmentFiles.size());
//
//        //从各个段文件中读取固定个数的数据参与归并
//        for (int i = 0; i < segmentFiles.size(); i++) {
//            File file = segmentFiles.get(i);
//            Util.readFromFile(sm, i, file);
//            sm.bindFile(i, file);
//        }
//
//        //每段先贡献一个数据，构建败者树
//        int k = sm.segmentCount();
//        int[] b = new int[k];
//        for (int i = 0; i < k; i++) {
//            b[i] = sm.popFirst(i);
//        }
//        LoserTree loserTree = LoserTree.createLoserTree(b);
//
//        //不断从败者树中取出最小值放入输出缓冲区
//        while (true) {
//            int index = loserTree.topIndex();
//            int newValue = -1;
//            do {
//                newValue = sm.popFirst(index);
//                if (newValue == -1) {
//                    //表明段内没有有效值了，则尝试从文件读取
//                    Util.readFromFile(sm, index, sm.file(index));
//                    newValue = sm.popFirst(index);
//                    if (newValue == -1) {
//                        //此段已彻底为空
//                        sm.markFinish(index);
//                    }
//                }
//                index = sm.getValidSegment();
//            } while (newValue != -1 && index != -1);
//
//            if (newValue == -1) {
//                // 此时还没新值，则说明所有段都比完了，
//                // 此时则把败者树中残留的数据一一写入输出缓存
//                loserTree.flushRemainValue(sm);
//                break;
//            } else {
//                int minimumValue = loserTree.update(newValue);
//                sm.writeOutputCache(minimumValue);
//            }
//        }
//    }
//
//
//
//}
