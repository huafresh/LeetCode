package com.hua.leetcode_core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class LeetCodeMain {

    public static void main(String[] args) {
//        int[] array = new int[]{-9, -7, -5, -3, -1, 2, 4, 4, 7, 10};
//        int[] result = new LeetCodeMain().sortedSquares(array);
//        System.out.println("end sort");
//
//        int[] a1 = new int[]{3, 1, 2, 4};
//        int[] result2 = new LeetCodeMain().sortArrayByParity(a1);

//        List<Integer> list = new LeetCodeMain().selfDividingNumbers(21, 22);
//
//        int[] nums = new int[]{-10,-3,0,5,9};
//        TreeNode node = new LeetCode_company().sortedArrayToBST(nums);

//        boolean palindrome = new LeetCode_company().isPalindrome("0P");
//        int result = new LeetCode_company().reverseBits(0b11111111111111111111111111111101);

        String infix = SuffixHelper.infixToSuffix("2*(9+6/3-5)+4");
        System.out.println("infix=" + infix);
    }


    /**
     * <a href = "https://leetcode-cn.com/problems/jewels-and-stones/">宝石与石头</a>
     */
    public int numJewelsInStones(String strJ, String strS) {
        int count = 0;
        for (int i = 0; i < strS.length(); i++) {
            if (strJ.indexOf(strS.charAt(i)) != -1) {
                count++;
            }
        }
        return count;
    }

    public static final int LOWER_CASE_MIN_VALUE = 0x61;
    public static final int LOWER_CASE_MAX_VALUE = 0x7A;
    public static final int UPPER_CASE_MIN_VALUE = 0x41;
    public static final int UPPER_CASE_MAX_VALUE = 0x5A;
    public static final int LOWER_UPPER_CASE_INTERVAL_VALUE = 0x20;

    /**
     * <a href = "https://leetcode-cn.com/problems/to-lower-case/submissions/">转换成小写字母</a>
     */
    public String toLowerCase(String str) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (isNeedTransform(c)) {
                c += LOWER_UPPER_CASE_INTERVAL_VALUE;
                builder.append(c);
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    private static boolean isNeedTransform(char c) {
        return isCharacter(c) && isUpperCase(c);
    }

    private static boolean isUpperCase(char c) {
        return c < LOWER_CASE_MIN_VALUE;
    }

    private static boolean isCharacter(char c) {
        return LOWER_CASE_MIN_VALUE <= c && c <= LOWER_CASE_MAX_VALUE ||
                UPPER_CASE_MIN_VALUE <= c && c <= UPPER_CASE_MAX_VALUE;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/squares-of-a-sorted-array/">有序数组的平方，即平方后排序</a>
     * 注意此题的关键并非排序。
     */
    public int[] sortedSquares(int[] a) {
        int len = a.length;
        int[] result = new int[len];

        //先找到最小的正数和最大的负数的位置
        int right = 0;
        while (right < len && a[right] < 0) {
            right++;
        }
        int left = right - 1;

        //向两边遍历，比较他们平方的大小，较小的输出到结果数组
        int index = 0;
        while (left >= 0 && right < len) {
            int leftSquare = a[left] * a[left];
            int rightSquare = a[right] * a[right];
            if (leftSquare < rightSquare) {
                result[index++] = leftSquare;
                left--;
            } else {
                result[index++] = rightSquare;
                right++;
            }
        }

        //最后走完剩下的数据
        while (left >= 0) {
            result[index++] = a[left] * a[left];
            left--;
        }

        while (right < len) {
            result[index++] = a[right] * a[right];
            right++;
        }


        return result;
    }


    /**
     * <a href = "https://leetcode-cn.com/problems/delete-node-in-a-linked-list/">删除链表中的节点</a>
     * 说明：
     * 链表至少包含两个节点。
     * 链表中所有节点的值都是唯一的。
     * 给定的节点为非末尾节点并且一定是链表中的一个有效节点。
     * 不要从你的函数中返回任何结果。
     * <p>
     * 批注：开始觉得是不是没给root节点，其实是故意的，正是此题用意。
     */
    public void deleteNode(ListNode node) {
//        ListNode curNode = node;
//        ListNode next = curNode.next;
//        ListNode pre = null;
//        while (next != null) {
//            curNode.val = next.val;
//            pre = curNode;
//            curNode = next;
//            next = curNode.next;
//        }
//
//        if (pre != null) {
//            pre.next = null;
//        }

        //以上注释是开始的解法，看了评论才发现其实没必要所有节点值都进行移动，
        //只需改变当前节点的next指向next的next就行了。
        node.val = node.next.val;
        node.next = node.next.next;


    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/flipping-an-image/">翻转图像</a>
     * 二维数组，先水平翻转，再所有元素取反
     * 输入: [[1,1,0,0],[1,0,0,1],[0,1,1,1],[1,0,1,0]]
     * 输出: [[1,1,0,0],[0,1,1,0],[0,0,0,1],[1,0,1,0]]
     */
    public int[][] flipAndInvertImage(int[][] a) {
        int len = a.length;
        int left, right, temp;
        for (int row = 0; row < len; row++) {
            left = 0;
            right = len - 1;
            while (left < right) {
                temp = a[row][left];
                a[row][left] = invertInt(a[row][right]);
                a[row][right] = invertInt(temp);
                left++;
                right--;
            }
            if (left == right) {
                a[row][left] = invertInt(a[row][left]);
            }
        }

        return a;

        //以下是评论区解法，看起来是更简洁，但是多了一倍的内存

//        if(a == null || a.length == 0) return new int[0][0];
//        int len = a.length;
//        int[][] ans = new int[len][len];
//
//        for(int i = 0; i < len; i++){
//            for(int j = 0; j < len; j++){
//                ans[i][j] = a[i][len-1-j] ^ 1;
//            }
//        }
//        return ans;
    }

    private static int invertInt(int value) {
        return value ^ 1;
    }

    /**
     * <a href="https://leetcode-cn.com/problems/unique-morse-code-words/submissions/">摩尔斯密码词</a>
     * 解题关键：
     * 1、利用HashSet去重
     * 2、字母的ascII码记不住时可用字符代替
     */
    public int uniqueMorseRepresentations(String[] words) {
        final HashSet<String> set = new HashSet<>();
        for (String word : words) {
            //翻译成摩尔斯密码
            final StringBuilder morsePwdStrBuilder = new StringBuilder();
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                morsePwdStrBuilder.append(MORSE_PWD[c - 'a']);
            }
            final String morsePwdStr = morsePwdStrBuilder.toString();

            //加入set即可，内部会判断去重
            set.add(morsePwdStr);
        }

        return set.size();
    }

    private static final String[] MORSE_PWD = new String[]{".-", "-...", "-.-.", "-..", ".", "..-" +
            ".", "--.",
            "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...",
            "-", "..-", "...-",
            ".--", "-..-", "-.--", "--.."};


    /**
     * <a href="https://leetcode-cn.com/problems/unique-email-addresses/">独特的电子邮件地址</a>
     * 此题与上一题比较类似，都是借助set去重。
     */
    public int numUniqueEmails(String[] emails) {
        //思路：把邮件按题中规则进行转换，然后加入HashSet即可。

        final HashSet<String> set = new HashSet<String>();

        for (String email : emails) {
            String[] splits = email.split("@");
            String local = splits[0];
            final String domain = splits[1];
            //去本地名称点号
            local = local.replace(".", "");
            //去本地名称+号
            int firstIndex = local.indexOf("+");
            if (firstIndex > 0) {
                local = local.substring(0, firstIndex);
            }

            set.add(local + "@" + domain);
        }

        return set.size();

    }

    /**
     * <a href="https://leetcode-cn.com/problems/di-string-match/submissions/">增减字符串匹配</a>
     * 给定只含 "I"（增大）或 "D"（减小）的字符串 s ，令 N = s.length。
     * 返回 [0, 1, ..., N] 的任意排列 A 使得对于所有 i = 0, ..., N-1，都有：
     * 如果 s[i] == "I"，那么 A[i] < A[i+1]
     * 如果 s[i] == "D"，那么 A[i] > A[i+1]
     * <p>
     * 示例：
     * 输出："IDID"
     * 输出：[0,4,1,3,2]
     * <p>
     * 比如i=0，那么s[i]='I'，因此A[0]<A[1]，即0<4，依次类推
     */
    public int[] diStringMatch(String s) {

        //观察示例可知：每次遇I则取最小值，遇D则取最大值
        //输出的序列虽不能保证是唯一满足条件的，但是却是绝对符合题意的

        int len = s.length();
        int curMin = 0;
        int curMax = len;
        int[] result = new int[len + 1];
        int index = 0;
        for (index = 0; index < len; index++) {
            char c = s.charAt(index);
            if (c == 'I') {
                result[index] = curMin++;
            } else {
                result[index] = curMax--;
            }
        }

        result[index] = curMin;

        return result;
    }


    /**
     * <a href="https://leetcode-cn.com/problems/robot-return-to-origin/">机器人能否返回原点</a>
     */
    public boolean judgeCircle(String moves) {
        final int[] position = new int[]{0, 0};

        for (int i = 0; i < moves.length(); i++) {
            char c = moves.charAt(i);
            switch (c) {
                case 'R':
                    position[0] -= 1;
                    break;
                case 'L':
                    position[0] += 1;
                    break;
                case 'U':
                    position[1] += 1;
                    break;
                case 'D':
                    position[1] -= 1;
                    break;
                default:
                    break;
            }
        }

        return position[0] == 0 && position[1] == 0;
    }


    private static final int INVALID_VALUE = -1;

    /**
     * <a href="https://leetcode-cn.com/problems/merge-two-binary-trees/submissions/">合并二叉树</a>
     * 解此题也可谓一波三折，主要是开始思路不对。
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        //选择一个二叉树周游的方式，在同一时间对两个二叉树合并，然后结果存于某课二叉树即可。
        //题意要从根节点开始，因此选在中序遍历。对于中序遍历，节点入栈顺序即为中序遍历顺序。

        //注意：上述思路是不对的，因为两棵树各自遍历，根本无法做合并。
        //正解应该是层序遍历，为空也放入队列。

//         final Stack<TreeNode> stack1 = new Stack<>();
//         final Stack<TreeNode> stack2 = new Stack<>();

//         TreeNode curNode1 = t1;
//         while(curNode1!=null || !stack1.isEmpty()){

//             while(curNode1!=null){
//                 stack1.push(curNode1);
//                 curNode1 = curNode1.left;
//             }

//             if(!stack1.isEmpty()){
//                 TreeNode top = stack1.pop();
//                 curNode1 = top.right;
//             }
//         }


//        final Queue<TreeNode> queue1 = new LinkedBlockingQueue<>();
//        final Queue<TreeNode> queue2 = new LinkedBlockingQueue<>();
//
//        queue1.add(t1);
//        queue2.add(t2);
//
//        TreeNode node1 = null;
//        TreeNode node2 = null;
//        while (!queue1.isEmpty()) {
//            node1 = queue1.poll();
//            node2 = queue2.poll();
//
//            node1.val = nodeValue(node1) + nodeValue(node2);
//
//            if (node1.val != INVALID_VALUE) {
//                if (node1.left != null) {
//                    queue1.add(node1.left);
//                } else {
//                    queue1.add(new TreeNode(INVALID_VALUE));
//                }
//
//                if (node1.right != null) {
//                    queue1.add(node1.right);
//                } else {
//                    queue1.add(new TreeNode(INVALID_VALUE));
//                }
//            }
//
//
//            if (node2.val != INVALID_VALUE) {
//                if (node2.left != null) {
//                    queue2.add(node2.left);
//                } else {
//                    queue2.add(new TreeNode(INVALID_VALUE));
//                }
//
//
//                if (node2.left != null) {
//                    queue2.add(node2.left);
//                } else {
//                    queue2.add(new TreeNode(INVALID_VALUE));
//                }
//            }
//        }

        //上述层序遍历的解法没验证，因为网站上总是报LinkedBlockingQueue不存在，可能是网站预判了解法，所以没导入？？
        //最后看了评论，大概知道解法是递归，于是有了下面的代码。

        if (t1 == null) {
            return t2;
        } else if (t2 == null) {
            return t1;
        } else {
            t1.val = t1.val + t2.val;
            t1.left = mergeTrees(t1.left, t2.left);
            t1.right = mergeTrees(t1.right, t2.right);
            return t1;
        }

    }

    private static int nodeValue(TreeNode node) {
        return node.val == INVALID_VALUE ? 0 : node.val;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    private static final int INTEGER_MAX_BIT = 32;

    /**
     * <a href="https://leetcode-cn.com/problems/hamming-distance/comments/">汉明距离</a>
     * 此题答的比较low，可以学习看下JDK的Integer.bitCount方法的实现
     */
    public int hammingDistance(int x, int y) {
        //题目可转化为求给定二进制数“1”的个数。

        final int xor = x ^ y;
        int distance = 0;

        for (int i = 0; i < INTEGER_MAX_BIT; i++) {
            if (((xor >>> i) & 1) == 1) {
                distance++;
            }
        }

        return distance;
    }

    public int jumpFloorDp(int n) {
        // dp(n) = dp(n-1)+dp(n-2)
        if (n <= 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;
        int dp1 = 1, dp2 = 2, dp = 0;
        for (int i = 3; i <= n; i++) {
            dp = dp1 + dp2;
            dp1 = dp2;
            dp2 = dp;
        }
        return dp;
    }

    /**
     * <a href="https://leetcode-cn.com/problems/sort-array-by-parity/comments/">按奇偶排序数组</a>
     */
    public int[] sortArrayByParity(int[] a) {
        //思路：像快速排序那样，两边遍历，偶数放左边，奇数放右边，最后一个放空位上。
        final int len = a.length;
        int temp = a[len - 1];
        int left = 0, right = len - 1;

        while (true) {

            while (left < right) {
                if (!isOuShu(a[left])) {
                    a[right] = a[left];
                    right--;
                    break;
                }
                left++;
            }

            while (left < right) {
                if (isOuShu(a[right])) {
                    a[left] = a[right];
                    left++;
                    break;
                }
                right--;
            }

            if (left == right) {
                a[left] = temp;
                break;
            }

        }
        return a;
    }


    private static boolean isOuShu(int value) {
        return value % 2 == 0;
    }


    /**
     * 去掉外层括号。
     */
    public String removeOuterParentheses(String s) {

        //咋一看题意较难理解：其实输入就是一连串的括号，我们要做的就是把外面的括号去掉
        //以示例来理解：输入："(( )( ))  (( ))  (( )(( )))"
        //比如第一段，是一个外层括号包含了两个内层括号，所以是可以去掉的
        //前面两段都好理解，最后一段不太一样，我们把外层括号去掉后就是：()(())
        //此时，后半段也是外层括号包裹内层括号，是不是也可以去掉呢？
        //答案是不行的，因为去外层括号首先要做分解，这里如果做分解的话，前面一对括号是没外层括号可以去掉的，
        //因此该段就是题中的原语

        //后话：提交后发现题意没那么复杂，只需做一次分解即可

        //后后话：想整理下代码再提交，怎么leetCode上题目都没了。。。。。。

        //理解了题意，那么解题思路就是：基于栈，当遇到“（”时入栈，“）”时出栈，栈空时即得到一个分解后的字串，
        //然后去外层括号，如此往复，直至无外层括号可去。

        final Stack<Character> stack = new Stack<>();
        final List<String> list = new ArrayList<>();
        int start = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                stack.push(c);
            } else {
                stack.pop();
            }

            if (stack.isEmpty()) {
                String str = s.substring(start, i + 1);
                list.add(str);
                start = i + 1;
            }
        }

        //以上得到了分解后的列表，接下来是去括号
        StringBuilder builder = new StringBuilder();
        for (String item : list) {
            builder.append(item, 1, item.length() - 1);
        }
        return builder.toString();
    }

    /**
     * <a href="https://leetcode-cn.com/problems/invert-binary-tree/submissions/">翻转二叉树</a>
     */
    public TreeNode invertTree(TreeNode root) {
        //首先想到的就是递归
        //写递归代码有两个要点：
        //1、假设完成
        //2、递归终止

        //递归终止
        if (root == null) {
            return root;
        }

        //假设方法能完成左右子树的翻转
        if (root.left != null) {
            invertTree(root.left);
        }
        if (root.right != null) {
            invertTree(root.right);
        }

        //翻转左右孩子
        TreeNode node = root.left;
        root.left = root.right;
        root.right = node;

        return root;
    }

    /**
     * <a href="https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/comments/">二叉树最大深度</a>
     */
    public int maxDepth(TreeNode root) {
        // 解法一：递归，代码最简洁，同时速度也最快
        // 解法二：层序遍历
        if (root == null) return 0;
        int depth = 0;
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int count = queue.size();
            while (count > 0) {
                TreeNode node = queue.poll();
                if (node != null) {
                    if (node.left != null) {
                        queue.add(node.left);
                    }
                    if (node.right != null) {
                        queue.add(node.right);
                    }
                }
                count--;
            }
            depth++;
        }
        return depth;
    }

    /**
     * <a href="https://leetcode-cn.com/problems/nim-game/submissions/">Nim游戏</a>
     */
    public boolean canWinNim(int n) {
        //思路：每次拿掉1～3块石头，有点像二叉树的分叉（当然这里应该是三叉树），
        //如果对给定数值建立三叉树，当节点值<=3时停止分叉，那么最终树的高度是偶数则输，奇数则赢
        //可以在建树的过程就计算树的高度，减少遍历次数

//        final Queue<Node> queue = new LinkedList();
//
//        queue.add(new Node(n));
//        int level = 0, count = 0;
//        Node curNode;
//
//        while (!queue.isEmpty()) {
//            count = queue.size();
//            while (count > 0) {
//                curNode = queue.poll();
//                if (curNode.value > 3) {
//                    //这里我们建立的“树”没有子树，是因为解此题并不需要
//                    //但是思路上要有树的概念
//                    queue.add(new Node(curNode.value - 1));
//                    queue.add(new Node(curNode.value - 2));
//                    queue.add(new Node(curNode.value - 3));
//                } else if (isWin(level)) {
//                    return true;
//                }
//                count--;
//            }
//            level++;
//        }
//        System.out.println(level);
//        return false;

        //后话：此题知道巴什博弈就很简单

        return n % 4 != 0;

    }

    private static boolean isWin(int level) {
        return level % 2 != 0;
    }

    private static class Node {
        private int value;

        Node(int value) {
            this.value = value;
        }
    }

    /**
     * <a href="https://leetcode-cn.com/problems/peak-index-in-a-mountain-array/comments/">山脉数组的峰值索引</a>
     */
    public int peakIndexInMountainArray(int[] a) {
        //峰值应该就是唯一的啊，为什么题中说“任何满足”呢？
        //计算峰值索引直接遍历找最大值不就行了吗？不会这么简单吧？？？

        final int len = a.length;
        int max = a[0];
        int i = 1;
        for (; i < len; i++) {
            if (a[i] > max) {
                max = a[i];
            } else {
                //到顶
                break;
            }
        }

        //maybe此题的意义在于二分法提高效率？

        return i - 1;

    }

    /**
     * <a href="https://leetcode-cn.com/problems/self-dividing-numbers/">自除数</a>
     */
    public List<Integer> selfDividingNumbers(int left, int right) {

        //遍历，分解，判断是否是自除数。
        //这么简单？？？

        final List<Integer> result = new ArrayList<>();

        boolean isSelfDivider;
        int temp;
        for (int i = left; i <= right; i++) {
            isSelfDivider = true;
            temp = i;
            while ((temp % 10 > 0) || (temp / 10 > 0)) {
                if (!isCanDivided(i, temp % 10)) {
                    isSelfDivider = false;
                    break;
                }
                temp /= 10;
            }
            if (isSelfDivider) {
                result.add(i);
            }
        }

        //以上代码写的比较简陋，但是思路和评论区是一致的，只用了一趟遍历，应该是效率最高的

        return result;
    }

    private static boolean isCanDivided(int value, int divisor) {
        if (divisor > 0) {
            return (value % divisor) == 0;
        }
        return false;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/range-sum-of-bst/">二叉搜索树的范围和</a>
     */
    public int rangeSumBST(TreeNode root, int L, int R) {
        //看半天总算明白题意了：
        //是要求L到R之间数的和，比如示例中，在7到15之间的节点有：7，10，15，所以和就是32.

        //那解法就简单了，周游二叉树，然后判断一下累加即可。
        //因为是二叉搜索树，所以应该存在优化的地方

        //todo
        return 0;


    }


    public List<Integer> preorder(NodeN root) {


        final List<Integer> result = new ArrayList<Integer>();

        //递归解法
        //printWithDiGui(root, result);
        //非递归，需借助栈，选择合适的出栈入栈的时机打印节点
        printWithoutDiGui(root, result);

        return result;
    }

    private void printWithoutDiGui(NodeN root, List<Integer> result) {
        if (root == null) {
            return;
        }

        final Stack<NodeN> stack = new Stack<>();
        //存储对应的节点遍历的index
        final Stack<Integer> stackIndex = new Stack<>();

        NodeN curNode = root;
        while (!stack.isEmpty()) {

            //node左孩子深度入栈
            while (curNode.children != null && curNode.children.size() > 0) {
                NodeN leftFirst = curNode.children.get(0);
                stack.push(leftFirst);
                stackIndex.push(1);
                curNode = leftFirst;
            }

            //出栈node，取兄弟节点
            curNode = stack.pop();
            int sibIndex = stackIndex.pop();
            if (curNode.children != null && curNode.children.size() > sibIndex) {
                curNode = curNode.children.get(sibIndex);
                stack.push(curNode);
                stackIndex.push(sibIndex + 1);
            }
        }
    }

    private void printWithDiGui(NodeN root, List<Integer> result) {
        if (root == null) {
            return;
        }
        result.add(root.val);
        int childCount = root.children.size();
        NodeN child = null;
        for (int i = 0; i < childCount; i++) {
            child = root.children.get(i);
            printWithDiGui(child, result);
        }
    }

    class NodeN {
        public int val;
        public List<NodeN> children;

        public NodeN() {
        }

        public NodeN(int _val, List<NodeN> _children) {
            val = _val;
            children = _children;
        }
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/reverse-integer/submissions/">整数反转</a>
     */
    private int reverse(int x) {
        int n = x;
        int ret = 0;
        while (n != 0) {
            int tmp = ret * 10 + n % 10;
            // 这里判断相乘后是否溢出，官方的题解中关于溢出的判断有一堆的证明逻辑，比较复杂。
            // 这里选择的是相乘后再除回去看能否还原，不能话说明溢出了。
            if (tmp / 10 != ret) {
                return 0;
            }
            ret = tmp;
            n = n / 10;
        }
        return ret;
    }

}




