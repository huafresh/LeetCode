package com.hua.leetcode_core.datastructure.out_sort;//package com.hua.datastructrue_java.out_sort;
//
//import android.content.Context;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//
///**
// * 因为读取到内存的每段的数据并不是存储在不同的数组的，而是都放在一个数组里。
// * 因此使用此类记录每一段在内存中的位置信息。
// * Created by hua on 2019/2/17.
// */
//
//public class SegmentMemory extends Memory {
//    private Context context;
//    private Segment[] segments;
//
//    //输出缓冲区起始位置等信息
//    private int outputStart;
//    private int outputLen;
//    private int outputOffset;
//
//    public SegmentMemory(Context context, int segmentCount) {
//        this.context = context;
//        int segmentPerSize = MAX_SIZE / (segmentCount + 1);
//        this.segments = new Segment[segmentCount];
//        for (int i = 0; i < segmentCount; i++) {
//            segments[i] = new Segment(i * segmentPerSize, segmentPerSize);
//        }
//        this.outputStart = segmentPerSize * segmentCount;
//        this.outputLen = MAX_SIZE - outputStart;
//        this.outputOffset = 0;
//    }
//
//    public boolean add(int index, int value) {
//        Segment segment = segments[index];
//        return segment.add(this, value);
//    }
//
//    public int segmentCount() {
//        return segments.length;
//    }
//
//    public void invalidSegment(int index) {
//        Segment segment = segments[index];
//        segment.invalid();
//    }
//
//    public int popFirst(int index) {
//        Segment segment = segments[index];
//        return segment.popFirst(this);
//    }
//
//    public void bindFile(int index, File file) {
//        Segment segment = segments[index];
//        segment.bindFile(file);
//    }
//
//    public File file(int index) {
//        Segment segment = segments[index];
//        return segment.file;
//    }
//
//    public int getValidSegment() {
//        for (int i = 0; i < segments.length; i++) {
//            Segment segment = segments[i];
//            if (!segment.finish) {
//                return i;
//            }
//        }
//        return -1;
//    }
//
//    public void markFinish(int index) {
//        Segment segment = segments[index];
//        segment.markFinish();
//    }
//
//    public void writeOutputCache(int value) {
//        if (outputOffset + 1 >= outputLen) {
//            //缓冲区满，写入文件
//            writeToResultFile(context, this, outputStart, outputLen);
//            outputOffset = 0;
//        }
//        set(outputStart + outputOffset + 1, value);
//    }
//
//    private static void writeToResultFile(Context context, Memory memory, int offset, int size) {
//        File resultFile = new File(context.getExternalCacheDir(), "ordered.txt");
//        FileWriter writer = null;
//        try {
//            writer = new FileWriter(resultFile, true);
//            for (int i = 0; i < size; i++) {
//                writer.write(memory.get(offset + i));
//                writer.write('\n');
//            }
//            writer.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            Util.closeQuietly(writer);
//        }
//    }
//
//    /**
//     * 段信息
//     */
//    static class Segment {
//        private int start;
//        private int segmentSize;
//        //段内有效数据偏移范围
//        private int offsetStart;
//        private int offsetEnd;
//        private File file;
//        private boolean finish = false;
//
//        Segment(int start, int segmentSize) {
//            this.start = start;
//            this.segmentSize = segmentSize;
//            this.offsetStart = -1;
//            this.offsetEnd = -1;
//        }
//
//        boolean add(Memory memory, int value) {
//            if (offsetEnd + 1 < segmentSize) {
//                memory.set(start + offsetEnd + 1, value);
//                if (offsetStart == -1) {
//                    offsetStart++;
//                }
//                offsetEnd++;
//                return true;
//            }
//            return false;
//        }
//
//        void invalid() {
//            this.offsetStart = -1;
//            this.offsetEnd = -1;
//        }
//
//        int popFirst(Memory memory) {
//            if (offsetStart != -1 && offsetStart <= offsetEnd) {
//                offsetStart++;
//                return memory.get(start + offsetStart);
//            }
//            return -1;
//        }
//
//        void bindFile(File file) {
//            this.file = file;
//        }
//
//        void markFinish() {
//            finish = true;
//        }
//    }
//
//}
