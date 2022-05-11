package com.hua.leetcode_core.datastructure.binary_tree;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉树
 *
 * @author hua
 * @version V1.0
 * @date 2019/2/19 14:22
 */

public abstract class BaseTree<T> implements ITreeCrud<T> {

    public static final String KEY_GRAPH_INFO = "graphInfo";
    protected TreeNode<T> root;
    private static final String KEY_IS_FIRST = "isFirst";
    private static final String KEY_STACK = "stack";
    private static final String KEY_LEVEL = "level";

    /**
     * 层序遍历，回调每个结点
     */
    public static <T> void levelTraversal(TreeNode<T> node,
                                          final ITraversal<T> traversal) {
        levelTraversal(node, new ITraversalWithExtras<T>() {
            @Override
            public void onTraversal(TreeNode<T> node, HashMap<String, Object> extras) {
                if (traversal != null) {
                    traversal.onTraversal(node);
                }
            }
        });
    }

    public static <T> void levelTraversal(TreeNode<T> node,
                                          ITraversalWithExtras<T> traversal) {
        if (node == null) {
            return;
        }
        Queue<TreeNode<T>> queue = new ArrayDeque<>();
        queue.add(node);
        node.setExtra(KEY_LEVEL, 1);
        while (!queue.isEmpty()) {
            TreeNode<T> curNode = queue.poll();
            TreeNode<T> left = curNode.left;
            if (left != null) {
                left.setExtra(KEY_LEVEL, getLevel(curNode) + 1);
                queue.add(left);
            }
            TreeNode<T> right = curNode.right;
            if (right != null) {
                right.setExtra(KEY_LEVEL, getLevel(curNode) + 1);
                queue.add(right);
            }
            if (traversal != null) {
                HashMap<String, Object> extras = new HashMap<>(1);
                extras.put(KEY_LEVEL, getLevel(curNode));
                traversal.onTraversal(curNode, extras);
            }
        }
    }

    private static void setLevel(TreeNode node, int level) {
        node.setExtra(KEY_LEVEL, level);
    }

    private static int getLevel(TreeNode node) {
        return (Integer) node.getExtra(KEY_LEVEL);
    }

    /**
     * 中序遍历
     */
    public static <T> void middleTraversal(TreeNode<T> node, final ITraversal<T> traversal) {
        middleTraversal(node, new ITraversalWithExtras<T>() {
            @Override
            public void onTraversal(TreeNode<T> node, HashMap<String, Object> extras) {
                if (traversal != null) {
                    traversal.onTraversal(node);
                }
            }
        });
    }

    public static <T> void middleTraversal(TreeNode<T> root, ITraversalWithExtras<T> traversal) {
        if (root == null) {
            return;
        }

        Stack<TreeNode<T>> stack = new Stack<>();
        TreeNode<T> curNode = root;
        while (curNode != null || !stack.isEmpty()) {
            while (curNode != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }

            curNode = stack.pop();
            if (traversal != null) {
                HashMap<String, Object> extras = new HashMap<>(1);
                extras.put(KEY_STACK, stack);
                traversal.onTraversal(curNode, extras);
            }
            curNode = curNode.right;
        }
    }

    /**
     * 后续遍历
     */
    public void behindTraversal(ITraversal<T> traversal) {
        behindTraversalInternal(root, traversal, null);
    }

    /**
     * 后续遍历，回调栈数据
     */
    public static <T extends Comparable<T>> void behindTraversalWithStack(
            TreeNode<T> node,
            ITraversalWithStack<T> traversal) {
        behindTraversalInternal(node, null, traversal);
    }


    private static <T> void behindTraversalInternal(
            TreeNode<T> root,
            ITraversal<T> traversal,
            ITraversalWithStack<T> traversalWithStack) {

        Stack<TreeNode<T>> stack = new Stack<>();
        TreeNode<T> curNode = root;
        while (curNode != null || !stack.isEmpty()) {

            while (curNode != null) {
                stack.push(curNode);
                curNode.setExtra(KEY_IS_FIRST, false);
                curNode = curNode.left;
            }

            TreeNode<T> topNode = stack.pop();
            if (Boolean.FALSE.equals(topNode.getExtra(KEY_IS_FIRST))) {
                stack.push(topNode);
                topNode.setExtra(KEY_IS_FIRST, true);
                curNode = topNode.right;
            } else {
                if (traversal != null) {
                    traversal.onTraversal(topNode);
                }
                if (traversalWithStack != null) {
                    traversalWithStack.onTraversal(stack, topNode);
                }
            }
        }

    }

    private static int level = 0;

    private int calculateTreeLevel() {
        levelTraversal(root, new ITraversalWithExtras<T>() {
            @Override
            public void onTraversal(TreeNode<T> node, HashMap<String, Object> extras) {
                Integer curLevel = (Integer) extras.get(KEY_LEVEL);
                if (level < curLevel) {
                    level = curLevel;
                }
            }
        });
        return level;
    }

    protected boolean isLeftChild(TreeNode node1, TreeNode node2) {
        return node2.left.equals(node1);
    }

    /**
     * 首先初始化一个和二叉树一样高的字符串数组，数组的每一个字符串代表了对应层二叉树要打印的字符串。
     * 然后中序遍历二叉树，逐步完善字符串数组的内容，最后统一输出。
     */
    public void graphPrint() {
        int level = calculateTreeLevel();
        final String[] result = new String[level * 2];
        for (int i = 0; i < result.length; i++) {
            result[i] = "";
        }

        levelTraversal(root, new ITraversalWithExtras<T>() {
            @Override
            public void onTraversal(TreeNode<T> node, HashMap<String, Object> extras) {
                int level = (int) extras.get(KEY_LEVEL);
                int curLine = (level - 1) * 2;
                int middleLine = curLine + 1;
                int nextLine = curLine + 2;

                //结点左边'-'字符的个数=左结点的右结点的总个数
                int leftCount = 0;
                TreeNode<T> curNode = node.left;
                while (curNode != null) {
                    leftCount += 2 + curNode.value.toString().length() / 2;
                    curNode = curNode.right;
                }

                //计算结点右边'-'字符的个数
                int rightCount = 0;
                curNode = node.right;
                while (curNode != null) {
                    rightCount += 2 + nodeHalf(curNode);
                    curNode = curNode.left;
                }

                //计算追加的空格
                int spaceCount = 0;
                TreeNode<T> parent = node.parent;
                if (parent == null) {
                    //根结点左边空格的个数=根结点的左结点的总个数
                    curNode = node.left;
                    while (curNode != null) {
                        spaceCount += nodeHalf(curNode) + 2 + nodeHalf(curNode.left);
                        curNode = curNode.left;
                    }
                } else {
                    GraphInfo info = (GraphInfo) parent.getExtra(KEY_GRAPH_INFO);
                    if (isLeftChild(node, parent)) {
                        spaceCount = info.spaceCount - 2 - nodeHalf(node);
                    } else {
                        spaceCount = 4 + nodeHalf(parent.left) + nodeHalf(parent.right);
                    }
                }

                //图形信息存储到node中
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.leftCount = leftCount;
                graphInfo.rightCount = rightCount;
                graphInfo.spaceCount = spaceCount;
                node.setExtra(KEY_GRAPH_INFO, graphInfo);

                //拼接完整一行字符串
                StringBuilder curLineStr = new StringBuilder();
                StringBuilder middleLineStr = new StringBuilder();
                for (int i = 0; i < spaceCount; i++) {
                    curLineStr.append(' ');
                    middleLineStr.append(' ');
                }
                if (leftCount > 0) {
                    middleLineStr.append('|');
                }
                for (int i = 0; i < leftCount; i++) {
                    curLineStr.append('-');
                    middleLineStr.append(' ');
                }
                curLineStr.append(node.value);
                for (int i = 0; i < rightCount; i++) {
                    curLineStr.append('-');
                    middleLineStr.append(' ');
                }
                if (rightCount > 0) {
                    middleLineStr.append('|');
                }
                String oldStr = result[curLine];
                if (oldStr != null) {
                    curLineStr.insert(0, oldStr);
                }
                String middleOldStr = result[middleLine];
                if (middleOldStr != null) {
                    middleLineStr.insert(0, middleOldStr);
                }

                result[curLine] = curLineStr.toString();
                result[middleLine] = middleLineStr.toString();

//
//                TreeNode<T> preNode = node.left;
//                boolean hasRight = node.right != null;
//                if (preNode != null) {
//                    //每一个结点字符串的起始位置要以上一个遍历结点为基础进行计算
//                    int start = result[nextLine].length() - 2 - preNode.value.toString().length() / 2;
//                    StringBuilder curStr = new StringBuilder(result[curLine]);
//                    while (curStr.length() < start) {
//                        curStr.append(' ');
//                    }
//                    curStr.append('-').append('-')
//                            .append(node.value);
//                    if (hasRight) {
//                        curStr.append('-').append('-');
//                    }
//                    result[curLine] = curStr.toString();
//
//                    //处理结点作右两边的竖线
//                    StringBuilder middleStr = new StringBuilder(result[middleLine]);
//                    while (middleStr.length() < start) {
//                        middleStr.append(' ');
//                    }
//                    middleStr.append('|');
//                    if (hasRight) {
//                        while (middleStr.length() < curStr.length()) {
//                            middleStr.append(" ");
//                        }
//                        middleStr.append('|');
//                    }
//                }
            }
        });

        for (String line : result) {
            System.out.println(line);
        }

    }

    private static int nodeHalf(TreeNode node) {
        return node != null ? node.value.toString().length() / 2 : 0;
    }

    @Deprecated
    interface ITraversal<T> {
        void onTraversal(TreeNode<T> node);
    }

    interface ITraversalWithExtras<T> {
        void onTraversal(TreeNode<T> node, HashMap<String, Object> extras);
    }

    @Deprecated
    interface ITraversalWithStack<T> {
        void onTraversal(Stack<TreeNode<T>> stack, TreeNode<T> node);
    }

    static class GraphInfo {
        int spaceCount;
        int leftCount;
        int rightCount;
    }

}
