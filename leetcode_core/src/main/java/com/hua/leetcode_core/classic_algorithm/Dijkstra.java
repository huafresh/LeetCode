package com.hua.leetcode_core.classic_algorithm;

//
// Created by clydeazhang on 2022/5/14 10:02 上午.
// Copyright (c) 2022 Tencent. All rights reserved.
//

import java.util.ArrayList;
import java.util.List;

/**
 * 迪杰斯特拉算法，用于求解顶点的最短路径问题
 */
public class Dijkstra {
    private final Vertex[] vertices;

    public Dijkstra() {
        Vertex[] vertices = new Vertex[6];

        List<Edge> edges1 = new ArrayList<>();
        edges1.add(new Edge(5, 100));
        edges1.add(new Edge(4, 30));
        edges1.add(new Edge(2, 10));
        Vertex vertex1 = new Vertex("v1", edges1);
        vertices[0] = vertex1;

        List<Edge> edges2 = new ArrayList<>();
        edges2.add(new Edge(2, 5));
        Vertex vertex2 = new Vertex("v2", edges2);
        vertices[1] = vertex2;

        List<Edge> edges3 = new ArrayList<>();
        edges3.add(new Edge(3, 50));
        Vertex vertex3 = new Vertex("v3", edges3);
        vertices[2] = vertex3;

        List<Edge> edges4 = new ArrayList<>();
        edges4.add(new Edge(5, 10));
        Vertex vertex4 = new Vertex("v4", edges4);
        vertices[3] = vertex4;

        List<Edge> edges5 = new ArrayList<>();
        edges5.add(new Edge(5, 60));
        edges5.add(new Edge(3, 20));
        Vertex vertex5 = new Vertex("v5", edges5);
        vertices[4] = vertex5;

        List<Edge> edges6 = new ArrayList<>();
        Vertex vertex6 = new Vertex("v6", edges6);
        vertices[5] = vertex6;

        this.vertices = vertices;
    }

    public static void main(String[] args) {
        Dijkstra dijkstra = new Dijkstra();
        Distance[] distances = dijkstra.calculateMinDistance();
        dijkstra.printMinDistancePath(distances);
    }

    /**
     * 计算v1顶点到其他顶点的最短距离
     */
    public Distance[] calculateMinDistance() {
        Distance[] disArray = new Distance[vertices.length];
        // 初始化Dis数组
        disArray[0] = new Distance(0);
        disArray[0].confirmed = true;
        for (int i = 1; i < disArray.length; i++) {
            disArray[i] = new Distance(-1);
        }
        // 初始化v1到其他顶点的直线距离
        List<Edge> edges = vertices[0].edges;
        for (Edge edge : edges) {
            disArray[edge.toVertex].value = edge.weight;
        }

        while (true) {
            // 寻找最小距离的索引
            int curMinIndex = -1;
            for (int i = 1; i < disArray.length; i++) {
                Distance distance = disArray[i];
                if (distance.confirmed || !distance.reachable()) {
                    continue;
                }
                if (curMinIndex == -1 || distance.value < disArray[curMinIndex].value) {
                    curMinIndex = i;
                }
            }
            // 找到当前最小的距离后，执行松弛过程
            if (curMinIndex != -1) {
                disArray[curMinIndex].confirmed = true;
                for (Edge edge : vertices[curMinIndex].edges) {
                    int toVertexIndex = edge.toVertex;
                    int newDistance = disArray[curMinIndex].value + edge.weight;
                    Distance toVertexDistance = disArray[toVertexIndex];
                    if (!toVertexDistance.reachable() || newDistance < toVertexDistance.value) {
                        toVertexDistance.value = newDistance;
                        toVertexDistance.preVertex = curMinIndex;
                    }
                }
            } else break;
        }
        return disArray;
    }

    /**
     * 打印最短路径
     */
    public void printMinDistancePath(Distance[] disArray) {
        for (int i = 1; i < disArray.length; i++) {
            Distance distance = disArray[i];
            StringBuilder path = new StringBuilder();
            int curVertexIndex = i;
            while (curVertexIndex != -1) {
                path.insert(0, vertices[curVertexIndex].value);
                path.insert(0, "->");
                curVertexIndex = disArray[curVertexIndex].preVertex;
            }
            path.insert(0, vertices[0].value);
            if (distance.reachable()) {
                System.out.println("v1到" + vertices[i].value + "的最短距离是: " + distance.value + ", " +
                        "路径: " + path);
            } else {
                System.out.println("v1到" + vertices[i].value + "的最短距离是: 不可达, 路径: 无");
            }
        }
    }

    public static class Distance {
        public int value;
        public int preVertex;
        public boolean confirmed;

        public Distance(int value) {
            this.value = value;
            this.preVertex = -1;
            this.confirmed = false;
        }

        public boolean reachable() {
            return value != -1;
        }
    }

    private static class Vertex {
        public String value;
        public List<Edge> edges;

        public Vertex(String value, List<Edge> edges) {
            this.value = value;
            this.edges = edges;
        }
    }

    private static class Edge {
        public int weight;
        public int toVertex;

        public Edge(int toVertex, int weight) {
            this.weight = weight;
            this.toVertex = toVertex;
        }
    }
}
