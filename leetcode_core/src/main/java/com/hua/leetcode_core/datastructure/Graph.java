package com.hua.leetcode_core.datastructure;

//
// Created by clydeazhang on 2022/5/14 9:27 上午.
// Copyright (c) 2022 Tencent. All rights reserved.
//

import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Graph {
    private final Vertex[] vertices;

    public Graph(Vertex[] vertices) {
        this.vertices = vertices;
    }

    /**
     * 深度优先遍历
     */
    public void deepTraverse() {
        if (vertices == null) return;
        resetVisited();
        for (Vertex vertex : vertices) {
            doDeepTraverse(vertex);
        }
    }

    private void doDeepTraverse(Vertex vertex) {
        if (vertex.visited) return;
        System.out.println("深度优先访问: " + vertex.value);
        vertex.visited = true;
        List<Edge> edges = vertex.edges;
        if (edges != null) {
            for (Edge edge : edges) {
                doDeepTraverse(vertices[edge.toVertex]);
            }
        }
    }

    /**
     * 广度优先遍历
     */
    public void breadthTraverse() {
        if (vertices == null) return;
        resetVisited();
        Queue<Vertex> queue = new LinkedBlockingQueue<>();
        for (Vertex vertex : vertices) {
            queue.add(vertex);
            while (!queue.isEmpty()) {
                Vertex poll = queue.poll();
                if (!poll.visited) {
                    System.out.println("广度优先访问: " + poll.value);
                    poll.visited = true;
                    List<Edge> edges = poll.edges;
                    if (edges != null) {
                        for (Edge edge : edges) {
                            queue.add(vertices[edge.toVertex]);
                        }
                    }
                }
            }
        }
    }

    private void resetVisited() {
        if (vertices == null) return;
        for (Vertex vertex : vertices) {
            vertex.visited = false;
        }
    }

    public static class Vertex {
        public int value;
        public List<Edge> edges;
        public boolean visited;

        public Vertex(int value, List<Edge> edges) {
            this.value = value;
            this.edges = edges;
        }
    }

    public static class Edge {
        public int toVertex;

        public Edge(int toVertex) {
            this.toVertex = toVertex;
        }
    }

}
