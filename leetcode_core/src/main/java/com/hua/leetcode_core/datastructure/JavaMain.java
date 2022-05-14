package com.hua.leetcode_core.datastructure;


import java.util.ArrayList;
import java.util.Random;

/**
 * @author hua
 * @version V1.0
 * @date 2019/2/18 16:20
 */

public class JavaMain {
    private static int[] testArray = new int[]{138, 148, 9, 8, 71, 173, 111, 10, 156, 177, 39, 17
            , 8, 61, 123, 30, 28, 23, 104, 66};
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
        Graph.Vertex[] vertices = new Graph.Vertex[4];

        ArrayList<Graph.Edge> edges1 = new ArrayList<>();
        edges1.add(new Graph.Edge(1));
        edges1.add(new Graph.Edge(2));
        edges1.add(new Graph.Edge(3));
        vertices[0] = new Graph.Vertex(1, edges1);

        ArrayList<Graph.Edge> edges2 = new ArrayList<>();
        vertices[1] = new Graph.Vertex(2, edges2);

        ArrayList<Graph.Edge> edges3 = new ArrayList<>();
        edges3.add(new Graph.Edge(3));
        vertices[2] = new Graph.Vertex(3, edges3);

        ArrayList<Graph.Edge> edges4 = new ArrayList<>();
        edges4.add(new Graph.Edge(0));
        vertices[3] = new Graph.Vertex(4, edges4);

        Graph graph = new Graph(vertices);
        graph.breadthTraverse();
    }

    private static void printArray(int[] data) {
        for (int i : data) {
            System.out.print(i + ", ");
        }
        System.out.println(" ");
    }

}
