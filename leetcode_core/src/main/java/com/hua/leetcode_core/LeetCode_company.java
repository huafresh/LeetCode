package com.hua.leetcode_core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2019-08-05 12:00
 */

public class LeetCode_company {

    /**
     * <a href = "https://leetcode-cn.com/problems/length-of-last-word/">最后一个单词的长度</a>
     */
    public int lengthOfLastWord(String s) {
        // 此题看似简单，实则有些边界需要考虑
        // 1、字符串可能一个空格都没有
        // 2、最后可能包含若干空格
        int len = s.length();
        int count = 0;
        for (int i = len - 1; i >= 0; i--) {
            if (s.charAt(i) != ' ') {
                count++;
            } else {
                if (count != 0) {
                    break;
                }
            }
        }
        return count;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/plus-one/submissions/">加一</a>
     */
    public int[] plusOne(int[] digits) {
        // 此题的难点在于考虑进位的问题。
        // return plusOneIndex(digits,digits.length-1);

        // 以上是递归解法，略微有些复杂。。。
        // 看了题解发现针对此题其实循环就可以了。

        int len = digits.length;
        for (int i = len - 1; i >= 0; i--) {
            int plus = digits[i] + 1;
            if (plus > 9) {
                // 这里就是只用循环就可以的根本原因，因为题目要求加1，而进位也是加1，
                // 所以这里只需把i所在位置置0，下一次循环进位就会得到体现。
                digits[i] = 0;
            } else {
                digits[i] = plus;
                // 不需要进位了，直接返回
                return digits;
            }
        }
        int[] newDigits = new int[len + 1];
        //这里都不需要copy，因为进位到这里的话，只有高位是1，后面全是0。
        newDigits[0] = 1;
        return newDigits;
    }

    private int[] plusOneIndex(int[] digits, int index) {
        if (index == -1) {
            //已经进到最高位
            int[] newDigits = new int[digits.length + 1];
            newDigits[0] = 1;
            System.arraycopy(digits, 0, newDigits, 1, digits.length);
            return newDigits;
        }
        int plus = digits[index] + 1;
        if (plus > 9) {
            digits[index] = 0;
            return plusOneIndex(digits, index - 1);
        } else {
            //不需要进位
            digits[index] = plus;
            return digits;
        }
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/add-binary/submissions/">二进制求和</a>
     */
    public String addBinary(String a, String b) {

        int aLen = a.length();
        int bLen = b.length();
        int maxLen = Math.max(aLen, bLen);
        StringBuilder result = new StringBuilder();
        // 标识当前的计算是否需要额外加1。
        // 如果前面有进位，此值就会被置为true。
        boolean needAddOne = false;
        for (int i = 0; i < maxLen; i++) {
            // 先分别计算a、b相应位上的值，如果越界则为0。
            int aValue = 0;
            if (aLen - i - 1 >= 0) {
                // 这里不能用Integer.valueOf，因为会把char当成对应的ARSCII码来计算。
                aValue = a.charAt(aLen - i - 1) - '0';
            }
            int bValue = 0;
            if (bLen - i - 1 >= 0) {
                bValue = b.charAt(bLen - i - 1) - '0';
            }

            int sum = aValue + bValue + (needAddOne ? 1 : 0);
            result.append(sum % 2);
            needAddOne = (sum / 2) > 0;
        }

        if (needAddOne) {
            result.append(1);
        }

        return result.reverse().toString();
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/binary-tree-level-order-traversal-ii/submissions/">二叉树的层次遍历 II</a>
     */
    @SuppressWarnings("ConstantConditions")
    public List<List<Integer>> levelOrderBottom(LeetCodeMain.TreeNode root) {
        // 仔细看返回值，其实倒序遍历不是重点，重点是给节点分层，分层的话可以用两个队列来做。
        // 后记：看评论区都没有双队列的解法，本来觉得双队列应该还比较巧妙的，
        // 结果打脸了，其实一个队列就够了，只需要在某次遍历的时候把所有元素取出来，
        // 刚好就是某一层的所有元素了，感觉这才是二叉树分层的标准思路吧，代码重写如下：
        final LinkedList<List<Integer>> results = new LinkedList<>();
        final Queue<LeetCodeMain.TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }
        LeetCodeMain.TreeNode curNode = null;
        while (!queue.isEmpty()) {
            int count = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                curNode = queue.poll();
                list.add(curNode.val);
                if (curNode.left != null) {
                    queue.add(curNode.left);
                }
                if (curNode.right != null) {
                    queue.add(curNode.right);
                }
            }
            results.addFirst(list);
        }
        return results;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/convert-sorted-array-to-binary-search-tree/submissions/">将有序数组转换为二叉搜索树</a>
     */
    public LeetCodeMain.TreeNode sortedArrayToBST(int[] nums) {
        // 数组已经是有序的，因此只需要每次取中间值填充二叉树即可。
        return binaryRoot(nums, 0, nums.length - 1);
    }

    private LeetCodeMain.TreeNode binaryRoot(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }

        int half = (start + end) / 2;
        LeetCodeMain.TreeNode node = new LeetCodeMain.TreeNode(nums[half]);
        node.left = binaryRoot(nums, start, half - 1);
        node.right = binaryRoot(nums, half + 1, end);

        return node;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/balanced-binary-tree/">平衡二叉树</a>
     */
    public boolean isBalanced(LeetCodeMain.TreeNode root) {
        // 注意审题：是给定某个节点，然后看它的左右两个子树的高度差的绝对值。
        return treeHeight(root) != -1;
    }

    // 返回给定子树的高度，如果左右子树高度差大于1，则返回-1。
    // 返回-1的目的是在递归的过程中就把子树的高度差判断了。
    private int treeHeight(LeetCodeMain.TreeNode root) {

        if (root == null) {
            return 1;
        }

        int leftH = treeHeight(root.left);
        // 这里就做判断，避免leftH为-1时，右子树就没必要计算了。
        if (leftH == -1) {
            return -1;
        }

        int rightH = treeHeight(root.right);
        if (rightH == -1) {
            return -1;
        }

        int deltaH = Math.abs(leftH - rightH);
        if (deltaH > 1) {
            return -1;
        } else {
            return Math.max(leftH, rightH) + 1;
        }
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/minimum-depth-of-binary-tree/submissions/">二叉树的最小深度</a>
     */
    public int minDepth(LeetCodeMain.TreeNode root) {
        // 层序遍历没跑了，当发现叶子节点就停止遍历

        if (root == null) {
            return 0;
        }

        final Queue<LeetCodeMain.TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int level = 1;
        out:
        while (!queue.isEmpty()) {
            int count = queue.size();
            for (int i = 0; i < count; i++) {
                LeetCodeMain.TreeNode node = queue.poll();
                if (node.left == null && node.right == null) {
                    break out;
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            level++;
        }

        return level;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/path-sum/submissions/">路径总和</a>
     */
    public boolean hasPathSum(LeetCodeMain.TreeNode root, int sum) {
//         // 二叉树后序遍历时，栈中元素即为当前节点到根节点的路径
//         final Stack<Element> stack = new Stack<>();
//         TreeNode cur = root;
//         // 存储某一时刻栈中元素的和
//         int tempSum = 0;
//         while(cur != null || !stack.isEmpty()){
//             while(cur!=null){
//                 stack.push(new Element(cur));
//                 tempSum += cur.val;
//                 cur = cur.left;
//             }

//             if(!stack.isEmpty()){
//                 Element element = stack.pop();
//                 TreeNode node = element.node;
//                 if(element.isFirst){
//                     element.isFirst = false;
//                     stack.push(element);
//                     cur = node.right;
//                     tempSum += node.val;
//                 } else {
//                     if(node.left==null && node.right==null && tempSum==sum){
//                         return true;
//                     }
//                 }
//                 tempSum -= node.val;
//             }
//         }
//         return false;

        // 后记：以上后序遍历的解法把所有的可能路径都遍历出来了，对于此题来说有些小题大作，
        // 其实只需要递归就很容易解决：
        // PS: 注意边界条件，最开始的root为null时，且sum=0时应该返回false。
        if (root == null) {
            return false;
        }
        int remain = sum - root.val;
        if (root.left == null && root.right == null) {
            return remain == 0;
        }
        return hasPathSum(root.left, remain) || hasPathSum(root.right, remain);
    }

    private static class Element {
        private LeetCodeMain.TreeNode node;
        private boolean isFirst = true;

        private Element(LeetCodeMain.TreeNode node) {
            this.node = node;
        }
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/pascals-triangle/">杨辉三角</a>
     */
    public List<List<Integer>> generate(int numRows) {
        // 暴力一点的话，好像没什么难度啊
        final List<List<Integer>> resultList = new ArrayList<>(numRows);
        List<Integer> preList = null;
        for (int i = 0; i < numRows; i++) {
            final List<Integer> lineList = new ArrayList<>(i + 1);
            if (i == 0) {
                lineList.add(1);
            } else {
                for (int j = 0; j < i + 1; j++) {
                    // 边界特殊处理
                    if (j == 0) {
                        lineList.add(preList.get(0));
                    } else if (j == i) {
                        lineList.add(preList.get(j - 1));
                    } else {
                        lineList.add(preList.get(j - 1) + preList.get(j));
                    }
                }
            }
            resultList.add(lineList);
            preList = lineList;
        }
        return resultList;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/valid-palindrome/submissions/">验证回文串</a>
     */
    public boolean isPalindrome(String s) {
        // 两边一起遍历，遇到特殊字符或空格就忽略

        if (s == null || s.length() == 0) {
            return true;
        }

        int start = 0, end = s.length() - 1;

        while (start < end) {
            while (start < s.length() && !isValidChar(s.charAt(start))) {
                start++;
            }
            while (end >= 0 && !isValidChar(s.charAt(end))) {
                end--;
            }
            if (start < s.length() &&
                    end >= 0 &&
                    !isEqual(s.charAt(start), s.charAt(end))) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    private boolean isValidChar(char c) {
        return isLetter(c) ||
                '0' <= c && c <= '9';
    }

    private boolean isEqual(char c1, char c2) {
        return c1 == c2 ||
                isLetter(c1) && isLetter(c2) && Math.abs(c1 - c2) == Math.abs('a' - 'A');
    }

    private static boolean isLetter(char c) {
        return 'a' <= c && c <= 'z' ||
                'A' <= c && c <= 'Z';
    }


    /**
     * <a href = "https://leetcode-cn.com/problems/single-number/submissions/">只出现一次的数字</a>
     */
    public int singleNumber(int[] nums) {
        // 线性时间复杂度那就表示只能遍历一遍，但是你要想知道后面是否有重复值就必须保存前面遍历到的值，
        // 但是这又使用了额外空间，所以难点就在这。
        // 像这种矛盾点，可以用DP的思想来处理，DP思想即：假设n已知，求n+1，
        // DP思想为什么会有线性时间是因为它在求最终结果的过程中不断利用已知的结果。
        // 针对这题就是假设[0, n]中只出现一次的元素是x，那么n+1时只需对比x和nums[n+1]是否相等就可以判断出
        // [0,n+1]这个区间是否还存在只出现一次的元素。
        // 把index=0用来存储最终的结果，就可以做到不使用额外空间了。避免麻烦我们还是用一个变量保存结果

        // 后记：上面bb了这么多，发现行不通，因为当n+1时x重复了，此时你无法知道下一个只出现一次的元素在哪里了。

        // 题解是用异或解决的，只能说666
        // 异或的含义就是相同为0，相异为1，因此对所有的元素异或，最终就会得到那个只出现一次的值。

        int result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            result = result ^ nums[i];
        }
        return result;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/linked-list-cycle/submissions/">环形链表</a>
     */
    public boolean hasCycle(ListNode head) {
        // 如果可以更改Node这个类，这个题不难，只需增加变量标记Node是否被访问过即可。
        // 这里Leecode显然不会让你修改Node类。
        // 看了题解，可以用快慢指针求解，具体思路就是一快一慢两个指针，快的每次跑两个节点，慢的每次跑一个节点，
        // 起点都一样，如果存在环，那么快指针最终肯定会重新等于慢指针。

        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                return true;
            }
        }

        return false;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/intersection-of-two-linked-lists/submissions/">相交链表</a>
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // 这个不看题解真是没思路：
        // 如果相交，后面的节点个数是相等的，因此如果A遍历到结尾，再从B的头开始遍历，B也遍历到结尾，再从A的头
        // 开始遍历，那么他们会刚好相遇在相交点。
        // ps: 以下代码参考题解，请仔细品一品

        ListNode p1 = headA;
        ListNode p2 = headB;
        if (p1 == null || p2 == null) {
            return null;
        }
        while (p1 != p2) {
            p1 = p1 == null ? headB : p1.next;
            p2 = p2 == null ? headA : p2.next;
        }

        return p1;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted/">两数之和 II - 输入有序数组</a>
     */
    public int[] twoSum(int[] numbers, int target) {
        // 开始时的思路：遍历[0, len]，先固定第一个元素，然后再从i往后挨个固定第二个元素，一旦和超过target了，则continue略过后面的元素。
        // 看了题解：用两个指针，开始时第一个指向最小值，第二个指向最大值，然后求和，如果大于target，则第二个指针前移；否则第一个指针后移，如此不断
        // 逼近唯一解。
        int i = 0;
        int j = numbers.length - 1;
        while (i < j) {
            if (numbers[i] + numbers[j] == target) {
                return new int[]{i + 1, j + 1};
            } else if (numbers[i] + numbers[j] > target) {
                j--;
            } else {
                i++;
            }
        }
        return null;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/excel-sheet-column-title/submissions/">Excel表列名称</a>
     */
    public String convertToTitle(int n) {
        // 思路：用n除以26，余数转成字母，然后用除的结果重复以上，生成的字符挨个往前排。
        // 题解后记：按照上述思路写出的代码始终在边界条件上有问题，看了题解才知道原来这道题并非严格的10进制转26进制，
        // 因为如果是标准的26进制，那么范围就是[0～25]，而这道题的范围却是[1～26]。了解了这点细微区别后，就可以在一般的进制转换
        // 代码的基础上修改了。
        int temp = n;
        final StringBuilder resultBuilder = new StringBuilder();
        while (temp > 0) {
            int mod = temp % 26;
            // 这个if就是修正代码
            if (mod == 0) {
                mod = 26; // 这个赋值则使后面插入'Z'字符
                temp--; // 这个减1无法从理论上去理解，不过用26、52等举例就会发现必须减1，非要理解的话就理解为借位吧。
            }
            temp = temp / 26;
            resultBuilder.insert(0, (char) ('A' + mod - 1));
        }
        return resultBuilder.toString();
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/majority-element/">多数元素</a>
     */
    public int majorityElement(int[] nums) {
        // 思路：想了半天没想出个啥。。。
        // 看了题解了解到原来这个题有个专业的词汇叫"求众数"，而"求众数"又有个专业的算法叫"摩尔投票"算法，
        // 该算法的思想：每次把数组中不相同的元素消除，最后剩下的就会是众数。
        // 具体代码实现：遍历数组，先把第一个元素当成"擂主"，该元素初始积分为1，每当遇到相同元素，则积分加1，遇到不同元素则减1，
        // 当积分为0时，则代表完成了一轮不同元素之间的消除，此时则再把下一个元素当成新的"擂主"，如此往复，最后剩下的"擂主"就是数组的众数。

        int major = nums[0];
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (major == nums[i]) {
                count++;
            } else {
                count--;
            }
            if (count == 0) {
                // 复位擂主
                major = nums[i + 1];
                count = 1;
                i++;
            }
        }
        return major;

        // 还有其他解法也不错，比如：先排序，然后取index = n/2处的元素即为众数。
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/excel-sheet-column-number/submissions/">Excel表列序号</a>
     */
    public int titleToNumber(String s) {
        // 其他进制转10进制问题，比如16进制“9AE”转10进制：
        // 9*16^2 + 10*16^1 + 14 = 2478
        // 之前做过序列号转列名称的题，那里提到了这类题并非严格的进制转换问题，因此需要一定的修正，但是这道题
        // 试了题目中示例的几个例子后，发现以上公式是完全正确的，不知为何。。。
        int len = s.length();
        int result = 0;
        for (int i = 0; i < len; i++) {
            char c = s.charAt(len - i - 1);
            result += Math.pow(26, i) * (c - 'Z' + 26);
        }
        return result;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/factorial-trailing-zeroes/submissions/">阶乘后的零</a>
     */
    public int trailingZeroes(int n) {
        // 这道题实在搞不懂在考什么，应该不会是先求阶乘，然后遍历统计0的个数吧，因为这样的话直接出题：O(log n)求解n的阶乘不就行了吗。
        // 看了题解，果然这道题不在于求阶乘，原因是很容易数值溢出。
        // 题解思路如下：一个数末尾有几个0，这个问题转换一下就是有几个10相乘，而阶乘的定义是这样的：
        // N! = 1*2*3*4*5*6*7*8*9*(2*5)...(3*5)...*N
        // 从中可以发现这样的规律：10的个数其实取决于5的个数，因为5*2=10，那会不会有5却没2呢？这是不可能的，仔细想想，5能被分解出来，
        // 在于加1加了5次，而加1加两次就能分解出一个2，这么说表达的真是累，看个10!的例子：
        // 1*(1*2)*3*(2*2)*5*(3*2)*7*(4*2)*9*(2*5)
        // 可以发现，每隔2个数就能分解出一个2，那么同理就是每隔5个数能分解出一个5，所以说2一定是比5多的。最后，问题其实就转化为相乘的数分解后，有几个5
        // 那到底怎么统计5的个数呢？根据前面的分析，每隔5个数能分解出一个5，那是不是5的个数就是 N/5呢？其实不然，再往后多举几个例子就知道了：
        // ...(1*5)...(2*5)...(3*5)...(4*5)...(1*5*5)...(2*5*5)...
        // 发现规律了吧，越往后，可以分解出来的5就越多：每隔5^1个数有N/5个5，每隔5^2个数有N/(5^2)个5，每隔5^3个数有N/(5^3)个5，依次类推，这些5是
        // 叠加的，因此可得如下公式：
        // count = N/(5^1) + N/(5^2) + N/(5^3) + ... + N/(5^n)  1<=n<根号N。

        int count = 0;
        while (n > 0) { // 每一次循环就是求解了上述式子的某一项。
            count += n / 5;
            n = n / 5;
        }
        return count;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/rotate-array/submissions/">旋转数组</a>
     */
    public void rotate(int[] nums, int k) {
        // 方法1: 一次只移动一个元素，用一个临时变量保存，然后systemArray拷贝
        // 方法2: 最后k个元素和最开始k个元素置换位置，此时最后k个元素位置正确，然后再对最后k个元素和[k, 2k]位置元素置换，
        // 如此往复即可。PS：此法未实现，因为置换到后面发现边界条件比较麻烦。
        // 看完题解后，感觉反转法高效且易于理解：首先将整个数组反转，然后反转前k个元素，最后反转[k, len]。

        // rotate1(nums, k);
        // rotate2(nums, k);
        k %= nums.length; // 实际k可能会大于数组长度
        reverse(nums, 0, nums.length);
        reverse(nums, 0, k);
        reverse(nums, k, nums.length - k);
    }

    private void reverse(int[] nums, int index, int count) {
        for (int i = 0; i < count / 2; i++) {
            int temp = nums[index + i];
            nums[index + i] = nums[index + count - 1 - i];
            nums[index + count - 1 - i] = temp;
        }
    }

    // 方法1
    private void rotate1(int[] nums, int k) {
        int len = nums.length;
        for (int i = 0; i < k; i++) {
            int temp = nums[len - 1];
            System.arraycopy(nums, 0, nums, 1, len - 1);
            nums[0] = temp;
        }
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/reverse-bits/">颠倒二进制位</a>
     */
    public int reverseBits(int n) {
        // 通过适当的移位应该可以实现。
        int result = 0;
        int savedN = n;
        for (int i = 0; i < 32; i++) {
            // 把要处理的bit抠出来，其余bit置0
            int j = (1 << (32 - i - 1)) & savedN;
            if (j != 0) {
                // 抠出来的bit如果是1，则result对称位置上置1
                result |= 1 << i;
            }
            // 如果要处理的bit为0，则无需特别处理，因为result本来初始就是0
        }
        return result;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/number-of-1-bits/">位1的个数</a>
     */
    public int hammingWeight(int n) {
        // jdk中就有方法可用啦，正好前两天复习了，看看现在能不能盲写出来。
        // 后记：看了题解，通过移位后的1作为mask用于确定n中对应位置上的bit是1还是0，是1的话就count加1，这种方式
        // 更容易想到，如果记不起jdk的算法，用此法简单很多。
        n = n - ((n >>> 1) & 0x55555555);
        n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);
        n = (n + (n >>> 4)) & 0x0f0f0f0f;
        n = n + (n >>> 8);
        n = n + (n >>> 16);
        return n & 0x3f;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/house-robber/solution/hua-jie-suan-fa-198-da-jia-jie-she-by-guanpengchn/">打家劫舍</a>
     */
    public int rob(int[] nums) {
        // 最优解问题，应该就是动态规划吧。
        // 动态规划在于找出状态转移公式，对于此题就是：dp[n] = MAX( dp[n-1], dp[n-2] + num )
        int max = 0;
        int preMax = 0;
        for (int num : nums) {
            int temp = Math.max(max, preMax + num);
            preMax = max;
            max = temp;
        }
        return max;

    }

    /**
     * <a href = "https://leetcode-cn.com/problems/happy-number/submissions/">快乐数</a>
     */
    public boolean isHappy(int n) {
        // 一直循环计算平方和，并保存，直到遇到相同的结果则认为是死循环，则return false。
        // 看了题解：虽然用集合保存结果判断是否进入循环这种方式在此题获得了通过，但是其实存在一个很大的问题，即循环可能足够大，
        // 以至于集合存储过大导致OOM。其实这道题考察的就是检测是否存在循环，而判断是否循环的最佳方式就是快慢指针，切记！！！
        int low = n, fast = n;
        do {
            low = bitSum(low);
            fast = bitSum(fast);
            fast = bitSum(fast);
        } while (low != fast);
        return low == 1;
    }

    private int bitSum(int n) {
        int sum = 0;
        while (n > 0) {
            sum += Math.pow(n % 10, 2);
            n = n / 10;
        }
        return sum;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/remove-linked-list-elements/submissions/">移除链表元素</a>
     */
    public ListNode removeElements(ListNode head, int val) {
        // 遍历不就完了？难道考察链表操作的基本功？
        // 看了题解后：考察的确实是链表操作的基本功，但是可以通过增加哨兵节点来使代码减少一些边界判断，
        // 所谓的哨兵节点其实就是给链表增加伪头和伪尾，这些增加的节点不保存任何数据，只是为了使节点永不为空。
        ListNode emptyHead = new ListNode(0);
        emptyHead.next = head;
        ListNode preNode = emptyHead;
        ListNode curNode = head;
        while (curNode != null) {
            if (curNode.val == val) {
                preNode.next = curNode.next;
            } else {
                preNode = curNode;
            }
            curNode = curNode.next;
        }
        return emptyHead.next;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/count-primes/submissions/">计数质数</a>
     */
    public int countPrimes(int n) {
        // 这题应该是在考察如何快速判断一个数是否是质数。
        // 质数的定义是除1和自身外，不能被其他数除尽。
        // 看提示加题解后记：判断质数这个问题，目前来说靠谱的优化点就只有一个，那就是缩小遍历区间到根号n。
        // 但是对于此题，是要统计质数个数，那么就有另一个优化方式了，该优化的思想是用空间换时间，把区间内肯定是非质数的数标记出来
        // 那么剩余的未标记的数的个数就是质数的个数了。此法有个专有名词叫埃拉托色尼筛选法。
        boolean[] array = new boolean[n];
        for (int i = 2; i < n; i++) {
            // 先所有都标为质数，后面会进行排除
            array[i] = true;
        }
        for (int i = 2; i * i < n; i++) {
            if (!array[i]) {
                continue;
            }
            // 埃拉托色尼筛选法的核心思想是：
            // 2的倍数可以排除；
            // 3的被数可以排除；
            // 4的倍数可以排除；
            // ... 依次类推。
            // 但是这里我们的for循环不能写成：for(int j=i;j<n;j+=i)，为什么呢？
            // 原因在于这么写很多重复了，我们以i=5来看：
            // 5*1,5*2,5*3,5*4, 5*5,5*6,...,5*n。
            // 可以看到其实前面4个5*1,5*2,5*3,5*4早在i=1,i=2,i=3,i=4时就标记过了，因此j的起点应该是i^2。
            for (int j = i * i; j < n; j += i) {
                array[j] = false;
            }
        }
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (array[i]) {
                count++;
            }
        }
        return count;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/isomorphic-strings/">同构字符串</a>
     */
    public boolean isIsomorphic(String s, String t) {
        // 题意是这样的：
        // 比如"egg"，那么e替换为a，g替换为d，则变为"add"
        // 可以用map保存映射的字符，遍历一遍即可。
        // 看题解后记：这道题不要想复杂了，确实就是map来搞的。
        int len = s.length();
        final HashMap<Character, Character> map = new HashMap<>(len);
        for (int i = 0; i < len; i++) {
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);
            if (map.containsKey(c1)) {
                if (map.get(c1) != c2) {
                    // 映射后和t对应的字符不一致。
                    return false;
                }
            } else {
                if (map.containsValue(c2)) {
                    // 不能映射到相同字符
                    return false;
                }
                map.put(c1, c2);
            }
        }
        return true;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/reverse-linked-list/submissions/">反转链表</a>
     */
    public ListNode reverseList(ListNode head) {
        // 此题就不要多想了，考的就是链表基本操作。
        // return method1(head);
        if (head == null) {
            return null;
        }
        return method2(head);
    }

    // 迭代法，说白了就是遍历
    private ListNode method1(ListNode head) {
        ListNode curNode = head;
        ListNode preNode = null;
        while (curNode != null) {
            // 当前节点不为空，那么下面的代码指行后当前节点就会是头
            head = curNode;
            ListNode nextNode = curNode.next;
            curNode.next = preNode;
            preNode = curNode;
            curNode = nextNode;
        }
        return head;
    }

    // 递归法
    private ListNode method2(ListNode head) {
        // 递归就是要假设已知，先假设某个方法可以完成反转。
        return reverse(head);
    }

    private ListNode reverse(ListNode head) {
        ListNode next = head.next;
        if (next == null) {
            return head;
        }
        ListNode h = reverse(next);
        next.next = head;
        head.next = null;
        return h;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/contains-duplicate/submissions/">存在重复元素</a>
     */
    public boolean containsDuplicate(int[] nums) {
        // 插入排序法对数组排序，排的过程中就能判断是否存在重复元素。
        // 提交后发现超时了，原来插入排序算法的时间复杂度是O(n^2)，并不适合大数据量的排序。
        // 看了题解后，此题的解法有：1、排序后遍历；2、利用hash表，以空间换时间。
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) {
                return true;
            }
            set.add(num);
        }
        return false;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/contains-duplicate-ii/">存在重复元素 II</a>
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        // 方法1: 第一层循环遍历每个元素，然后第二层循环遍历[i, i+k]，寻找是否有两个值相等。
        // 方法2: 用HashMap保存元素值以及他们的索引的集合，此法更快但同时也更耗内存。
        // 看完题解：思路上确实是用Hash表来做的，不过在内存上有些优化，即用HashSet保存遍历的元素，最多保存k个元素，
        // 超过k个时把最早添加的元素移除，因为该元素不可能会影响最终结果了，如此能节约一点内存，具体代码就不写了。
        final HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (map.containsKey(num)) {
                List<Integer> indexList = map.get(num);
                for (int index : indexList) {
                    if (Math.abs(index - i) <= k) {
                        return true;
                    }
                }
                indexList.add(i);
            } else {
                List<Integer> indexList = new ArrayList<>();
                indexList.add(i);
                map.put(num, indexList);
            }
        }
        return false;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/power-of-two/submissions/">2的幂</a>
     */
    public boolean isPowerOfTwo(int n) {
        // 如果是2的幂次方，那么n-1后对应的二进制低位就全是1。
        // 看了题解，虽然思路一样的，不过代码简介很多，直接 n & (n-1) == 0即可判断，666
        return n > 0 && ((n & (n - 1)) == 0);
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/palindrome-linked-list/">回文链表</a>
     */
    public boolean isPalindrome(ListNode head) {
        // 使用快慢两个指针，快指针每次走两步，慢指针一次一步，当快指针到达链表尾部时，慢指针刚好在链表中间。
        // 同时，慢指针遍历到的元素都进行反转，当慢指针到了链表中点时，再从中点向两边分散遍历，挨个比较即可。
        // 当然，如果实际链表需要保持原样，那么可以在分散遍历的时候再次反转链表即可。
        // 看题解后记：如果非要O(1)空间的话，思路和上面本人的思路是一样的，具体代码孰优孰劣就不管了。
        if (head == null) {
            return true;
        }
        ListNode slow = head;
        ListNode slowPreNode = null;
        ListNode fast = head;
        // 快指针是否刚好走到链表末尾（其实就是链表节点总数是否是奇数）
        boolean b = true;
        while (true) {
            ListNode fastNext = fast.next;
            if (fastNext != null) {
                if (fastNext.next == null) {
                    b = false;
                } else {
                    fast = fastNext.next;
                }
            } else {
                break;
            }
            // 快指针一定先到末尾，因此慢指针可以不用判空。
            // slow是当前正在处理的慢指针节点。
            // 反转链表一般操作：1、先保存next；2、修改当前node.next为preNode；3、修改相关指针开始处理下一个node。
            ListNode slowNext = slow.next;
            slow.next = slowPreNode;
            slowPreNode = slow;
            slow = slowNext;
            if (!b) {
                // b置为false时我们没有break，是为了让慢指针走最后一步，这么做是为了方便后面赋值分散遍历的左右指针
                break;
            }
        }
        ListNode left;
        ListNode right;
        if (b) {
            // 刚好走到末尾，说明链表节点个数为奇数，此时慢指针刚好在链表中点。
            // 因此分散遍历的初始节点就是preNode和slow.next
            left = slowPreNode;
            right = slow.next;
        } else {
            // 快指针走到尾部时还差一个节点，说明链表节点个数为偶数，此时慢指针在中点偏右
            left = slowPreNode;
            right = slow;
        }
        while (left != null && right != null) {
            if (left.val != right.val) {
                return false;
            }
            left = left.next;
            right = right.next;
        }
        return true;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-search-tree/">二叉搜索树的最近公共祖先</a>
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 注意题目，给定的树是二叉搜索树，因此p和q的值和祖先的值具有下述关系：
        // 1、如果p和q的值小于当前节点的值，那么p和q的最近祖先一定在当前节点的左边；反之则在右边。
        // 2、在1的基础上遍历，如果当前节点的值等于p或q中的一个，那么当前节点就是要找的最近祖先。
        // 3、如果p和q中一个大于当前节点一个小于当前节点，那么当前节点就是要找的最近祖先。
        // 看完题解，思路与上述一致。
        if (root == null) {
            return null;
        }
        TreeNode curNode = root;
        while (curNode != null) {
            if (p.val == curNode.val || q.val == curNode.val) {
                break;
            }
            if (p.val < curNode.val && q.val > curNode.val ||
                    p.val > curNode.val && q.val < curNode.val) {
                break;
            }
            if (p.val < curNode.val && q.val < curNode.val) {
                curNode = curNode.left;
            } else {
                curNode = curNode.right;
            }
        }
        return curNode;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/valid-anagram/">二叉搜索树的最近公共祖先</a>
     */
    public boolean isAnagram(String s, String t) {
        // 用哈希表保存s的每个字符，然后遍历t的每个字符，看是否能在集合中找到。
        // 对于unicode字符，目前的理解是s或t可能包含中文，这时候char不知道能否保存，如果不能可以用substring来搞。
        // 后记：char可以保存一个中文字。
        // 看了题解：原来题目中的进阶是为了配合题解的，题解中思路和上述一致，不过用的hash表固定为26，因为字母一共就26。
        // 但是其实这个优化没有必要，根本无需关心输入的字符串字符有多少个类型，往map中存就完了。
        if (s.length() != t.length()) {
            return false;
        }
        final HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            Integer value = map.get(c);
            if (value == null) {
                return false;
            } else {
                if (value - 1 > 0) {
                    map.put(c, value - 1);

                } else {
                    map.remove(c);
                }
            }
        }
        return true;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/binary-tree-paths/submissions/">二叉树的所有路径</a>
     */
    public List<String> binaryTreePaths(TreeNode root) {
        // 嗯，深度优先遍历二叉树即可。
        // 后记：深度遍历的时候栈中保存的虽然刚好是当前节点到跟节点的路径，但却不一定是叶子节点到跟节点的路径。
        // 如果难理解，就看看左边的示例，遍历到5的时候，此时5是叶子节点，但是2已经pop出栈了。。。尴尬。
        // 看了题解：哎，从一开始思路就错了，这个题和深度优先遍历二叉树无关。解法有递归和层序遍历。
        // 1、递归：我们假设一个方法可以完成统计子节点的所有路径，那么只需把当前节点插入到每个路径头部即可。
        // 2、层序遍历：每一层挨个遍历，路径个数不断裂变增多。
        return method2(root);
    }

    private List<String> method1(TreeNode root) {
        List<String> list = new ArrayList<>();
        buildPath(root, list, root.val + "->");
        return list;
    }

    // 参数path保存跟节点到第一个参数node节点的路径
    private void buildPath(TreeNode node, List<String> list, String path) {
        if (node.left == null && node.right == null) {
            list.add(path.substring(0, path.lastIndexOf("->")));
            return;
        }
        if (node.left != null) {
            buildPath(node.left, list, path + node.left.val + "->");
        }
        if (node.right != null) {
            buildPath(node.right, list, path + node.right.val + "->");
        }
    }

    private List<String> method2(TreeNode root) {
        // 层序遍历二叉树，队列中保存的不仅仅是节点，同时还保存该节点到跟节点的路径。
        final List<String> list = new ArrayList<>();
        Queue<NodeWithPath> queue = new LinkedList<>();
        if (root != null) {
            queue.add(new NodeWithPath(root, root.val + "->"));
        }
        while (!queue.isEmpty()) {
            NodeWithPath nodePath = queue.poll();
            TreeNode node = nodePath.node;
            if (node.left == null && node.right == null) {
                list.add(nodePath.path.substring(0, nodePath.path.lastIndexOf("->")));
            }
            if (node.left != null) {
                queue.add(new NodeWithPath(node.left, nodePath.path + node.left.val + "->"));
            }
            if (node.right != null) {
                queue.add(new NodeWithPath(node.right, nodePath.path + node.right.val + "->"));
            }
        }
        return list;
    }

    private static class NodeWithPath {
        private TreeNode node;
        private String path;

        private NodeWithPath(TreeNode node, String path) {
            this.node = node;
            this.path = path;
        }
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/add-digits/submissions/">各位相加</a>
     */
    public int addDigits(int num) {
        // 先用最常规的解决吧，然后看看怎么优化。
        // return addDigitsNormal(num);
        // 看题解后：
        // 首先即数字n的各位和为函数f(n)，根据123 = 1*(99+1) + 2*(9+1) + 3可知: 1+2+3 = 123-99-2*9 = 123 - (11+2)*9
        // 由此可见，每当增加一个非个位的位时，f(n)就要减去9、99、999、...等一系列值的倍数，那么抽象一下公式就是：
        // f(n) = n - 9*k (k是常数)
        // 首先可以肯定f(n)一定是小于10的，因为如果大于10，那么改变上面式子的k值就总能使f(n)的值重新变成小于10.
        // 想一想，当k等于什么的时候，会使f(n)是个位数并且取值范围是[1,9]呢？
        // 要分两种情况讨论：
        // 1、n能被9整除，此时公式可以变为：f(n)=n-9*k=9*m-9*k=9*(m-k)=9k (k为常数)，那么此时k只能取1，f(n)=9。
        // 2、n不能被9整除，那么只需令k=n/9即可，此时f(n)=n%9。
        // 因此写出如下初版O(1)算法：
        // return addDigitsFn(num);
        // 我们尝试继续改进初版O(1)算法。
        // 如果n可以被9整除，那么n-1一定不能被9整除，因此根据上述情况2有：f(n-1)=(n-1)%9，
        // 又因为f(n)=f(n-1)+1（这个公式注意理解各位和的定义就很容易得出），所以n可以被9整除时，有：f(n)=(n-1)%9+1。
        // 当n不能被9整除时，此时又可分两种情况：
        // 1、n-1可被9整除：此时有f(n) = n - 9*k = n-1-9*k+1 = 9*m-9*k+1 = 9k+1 k是常数。此时k只能为0，则f(n)为1，
        // 这和(n-1)%9+1这个式子的计算结果一致。
        // 2、n-1不可被9整除：此时有f(n) = f(n-1)+1 = (n-1)%9+1
        // 综上所述，不管n能不能被9整除，都可以用(n-1)%9+1来算各位和。因此改进的O(1)算法如下：
        return addDigitsFn2(num);
    }

    public int addDigitsNormal(int num) {
        int sum = num;
        while (true) {
            int tempSum = 0;
            while (sum > 0) {
                tempSum += sum % 10;
                sum = sum / 10;
            }
            sum = tempSum;
            if (tempSum / 10 == 0) {
                break;
            }
        }
        return sum;
    }

    // 初版O(1)解法
    public int addDigitsFn(int num) {
        // 注意输入可能是0
        if (num != 0 && num % 9 == 0) {
            return 9;
        } else {
            return num % 9;
        }
    }

    // 改进O(1)解法
    public int addDigitsFn2(int n) {
        // 注意输入可能是0
        return (n - 1) % 9 + 1;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/ugly-number/">丑数</a>
     */
    public boolean isUgly(int num) {
        // 题意的意思是：把一个正整数分解质因数，每一个质因数只能是2、3、5中的一个。
        // 所以这个问题其实就变成求num的分解质因数，算法步骤：
        // 首先分解后的质因数一定是质数，不然的话就可以进一步分解了，所以算法的第一步首先找出是质数且能整除num的数k，然后
        // num/k作为新的num重复以上步骤，即可把所有的质因数求出来。
        // PS：经过实践，质数被认为是丑数。
        // 上述思路会超时，因此后面改成下面的方式，题解也差不多这样的思路。
        int tempNum = num;
        while (tempNum > 0) {
            if (tempNum % 2 == 0) {
                tempNum = tempNum / 2;
                continue;
            } else if (tempNum % 3 == 0) {
                tempNum = tempNum / 3;
                continue;
            } else if (tempNum % 5 == 0) {
                tempNum = tempNum / 5;
                continue;
            }
            return tempNum == 1;
        }
        return false;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/missing-number/submissions/">缺失数字</a>
     */
    public int missingNumber(int[] nums) {
        // 看题的时候就注意到了，返回值只有一个数，因此说明序列中只缺了一个，这样的话只需求和就行了，跟不缺的时候的序列和
        // 的差值就是缺的那个数了。
        // 看题解后的改进：因为直接求和有可能会导致数值溢出，因此可以一边遍历一边减去数组的值
        // int sum = 0;
        // for(int i=1;i<=nums.length;i++){
        //     sum += i;
        //     sum -= nums[i-1];
        // }
        // return sum;
        // 虽然改进可以减少溢出的概率，不过并没有根除，其实此题的最优解是位运算，即对数组的每一个元素做异或运算。
        // 为什么异或就可以呢？
        // 其实对于异或有这么两个结论：1、异或满足交换律，即a^b^c = a^c^b；2、和自己异或等于0，0与任何数异或等于该数本身。
        // 因此只要0~n每一个数和nums数组中的元素两两异或，nums数组中缺的那个数因为没有和它一样的数和它配对异或，因此会保留到最后。
        // 注意理解这个初始化，如果下面的for循环异或后为0，说明数组的元素在0～nums.length-1，可见缺的就正好是最后一个自然数
        int miss = nums.length;
        for (int i = 0; i < nums.length; i++) {
            miss ^= i ^ nums[i];
        }
        return miss;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/first-bad-version/submissions/">第一个错误的版本</a>
     */
    public int firstBadVersion(int n) {
        // 尽量减少次数，目前想到的方法是二分法
        // PS: 慎用递归，容易栈溢出。
        // PS2: 完了，二分法都超时了。
        // 看了题解，原来此题解法确实是二分法，不过代码写的有问题，(start+end)/2可能溢出。。。
        int start = 0;
        int end = n;
        while (start != end) {
            int half = start + (end - start) / 2;
            if (isBadVersion(half)) {
                end = half;
            } else {
                start = half + 1;
            }
        }
        return start;
    }

    private boolean isBadVersion(int version) {
        return true;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/move-zeroes/">移动零</a>
     */
    public void moveZeroes(int[] nums) {
        // 一趟遍历，遇到0就把0抠出来使对应位置成为一个空位，然后往后遍历到非0数字时把该数字放到之前的空位上
        // 那么非0所在的位置又成为新的空位，如此往复。不需要保存抠出来的0，最后数组末尾全部补0即可。
        int emptyIndex = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                if (emptyIndex == -1) {
                    emptyIndex = i;
                }
            } else {
                if (emptyIndex != -1) {
                    nums[emptyIndex] = nums[i];
                    emptyIndex++;
                    nums[i] = 0;
                }
            }
        }
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/word-pattern/">单词规律</a>
     */
    public boolean wordPattern(String pattern, String str) {
        // 注意理解题意；是要和str中的单词有对应规律，而不是str中的字母。
        //之前做过类似的题，之前是字母对字母，而这里就是字母对单词，用哈希表即可。
        // 类似的题一定要注意以下陷阱：
        // 1、不仅pattern要对应str，str也要对应pattern。
        // 2、pattern的字母个数和str的单词个数要一致。
        // 题解后记：从str中提取每一个单词其实还有个思路就是用split，这样的话可以降低复杂度，但是增加了额外的数组保存。
        final HashMap<Character, String> map = new HashMap<>();
        int wordIndex = 0;
        int i = 0;
        for (i = 0; i < pattern.length(); i++) {
            if (wordIndex >= str.length()) {
                // 单词个数长度不够
                return false;
            }
            char c = pattern.charAt(i);
            // 下while循环获取下一个单词
            int endIndex = wordIndex;
            String word = "";
            while (endIndex < str.length()) {
                if (str.charAt(endIndex) == ' ') {
                    break;
                } else {
                    endIndex++;
                }
            }
            word = str.substring(wordIndex, endIndex);
            wordIndex = endIndex + 1;

            System.out.println("word = " + word);
            if (map.containsKey(c)) {
                if (!map.get(c).equals(word)) {
                    return false;
                }
            } else {
                if (map.containsValue(word)) {
                    return false;
                }
                map.put(c, word);
            }
        }
        // 如果循环结束时如果是匹配的，wordIndex的值便是固定的
        return wordIndex == str.length() + 1;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/bulls-and-cows/submissions/">猜数字游戏</a>
     */
    public String getHint(String secret, String guess) {
        // 用哈希表，需要2趟遍历，还有就是一点额外的空间
        // 看完题解：两趟遍历有些多余了，题解中有个思路是用大小为10的数组保存每个数字出现的次数，
        // 如果数字在secret则加2，反之在guss则减1，最后遍历数组，那么数组中为0的位置就是被猜中或不在secret中的数字。
        int aCount = 0;
        int bCount = 0;
        int[] array = new int[10];
        for (int i = 0; i < secret.length(); i++) {
            char s = secret.charAt(i);
            char g = guess.charAt(i);
            if (s == g) {
                // 顺便统计公牛的个数。
                aCount++;
            } else {
                array[s - '0'] += 1;
                array[g - '0'] -= 1;
            }
        }
        int nonZeroCount = 0;
        for (int i = 0; i < 10; i++) {
            if (array[i] > 0) {
                nonZeroCount += array[i];
            }
        }
        // 注意这个式子，是这个解法的精髓：
        // 首先，数组一开始全是0，当遍历时，secret中的数字会不断使对应index上的值增大，而guess中的数字则会不断使对应index的值减小，
        // 如果某个数字被完全猜中了，因此数组中的值会是0，和数组中的其他0混在一起了，因此无法统计。但是我们可以换个思路：数组中不为0的数的总和，
        // 就是没有被猜中的数字的总和，而被猜的数字一共有secret.length()个，那么很显然secret.length() - nonZeroCount -
        // aCount就是被猜中的数字个数。
        // 减去aCount的原因是secret.length()是总的数字个数，包含了公牛数字。
        bCount = secret.length() - nonZeroCount - aCount;
        return aCount + "A" + bCount + "B";
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/range-sum-query-immutable/">区域和检索 - 数组不可变</a>
     */
    // 这个题不难，应该考的是sumRange多次调用时如何优化。
    // 看完题解：用另一个数组保存[0,i]之间的和记为sum[k]，那么i~j范围的和就是：
    // sum[i~j] = sum[j] - sum[i];
    class NumArray {
        private int[] sumArray;

        public NumArray(int[] nums) {
            // 为保持下面代码的统一，sumArray的第一位保存0
            sumArray = new int[nums.length + 1];
            sumArray[0] = 0;
            for (int i = 1; i <= nums.length; i++) {
                sumArray[i] = sumArray[i - 1] + nums[i - 1];
            }
        }

        public int sumRange(int i, int j) {
            // 先加1是为了和sumArray对应上，而后面又减1则要理解题意：和包括了i和j。
            return sumArray[j + 1] - sumArray[i + 1 - 1];
        }
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/power-of-three/submissions/">3的幂</a>
     */
    public boolean isPowerOfThree(int n) {
        // 如果不能循环，难道还有一步到位的解法？
        // while(n>0 && n%3==0){
        //     n = n/3;
        // }
        // return n==1;
        // 看了题解：这题其实严格意义上没有一步到位的解法，题解中最巧妙的解法也是利用这题n的值为int类型的限制实现的
        // 具体如下：参数n是int型，因此3^n < Integer.MAX_VALUE，此时n最大值为19，因此，如果参数n是3的幂次方，
        // 那么可以肯定可以把3^19整除
        return n > 0 && Math.pow(3, 19) % n == 0;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/power-of-four/submissions/">4的幂</a>
     */
    public boolean isPowerOfFour(int num) {
        // 刚做完题“3的幂”，不过此题可以有别的解法：4的幂，实际上就是2的幂，然后n是偶数。
        // 对于2的幂，有个规律就是(num-1)的低位全是1，幂是奇数时则1的个数为奇数；否则反之。
        // 题解：首先判断是2的幂，然后判断num的1的位置，只需和 0x1010 1010 ..., 相与就能判断了，很巧妙。
        boolean isPowerOfTwo = ((num - 1) & num) == 0;
        boolean isDouble = (num & 0xaaaaaaaa) == 0;
        return num > 0 && isPowerOfTwo && isDouble;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/reverse-string/">反转字符串</a>
     */
    public void reverseString(char[] s) {
        // 双指针遍历，应该没有更高效的方法了吧？
        if (s == null) {
            return;
        }
        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/reverse-vowels-of-a-string/">反转字符串中的元音字母</a>
     */
    public String reverseVowels(String s) {
        // 元音字母是指：a、e、i、o、u
        // 后记：题目意思理解错误了，题意是要对应交换字符串中元音字母的位置。
        if (s == null) {
            return null;
        }
        if ("".equals(s)) {
            return "";
        }
        final StringBuilder leftBuilder = new StringBuilder();
        final StringBuilder rightBuilder = new StringBuilder();
        int leftIndex = 0;
        int rightIndex = s.length() - 1;
        while (leftIndex < rightIndex) {
            char left = s.charAt(leftIndex);
            char right = s.charAt(rightIndex);
            if ((isVowel(left)) && isVowel(right)) {
                leftBuilder.append(right);
                rightBuilder.insert(0, left);
                leftIndex++;
                rightIndex--;
            } else if (isVowel(left)) {
                rightIndex--;
                rightBuilder.insert(0, right);
            } else if (isVowel(right)) {
                leftIndex++;
                leftBuilder.append(left);
            } else {
                leftIndex++;
                rightIndex--;
                leftBuilder.append(left);
                rightBuilder.insert(0, right);
            }
        }
        if (leftIndex == rightIndex) {
            leftBuilder.append(s.charAt(leftIndex));
        }
        leftBuilder.append(rightBuilder.toString());
        return leftBuilder.toString();
    }

    private boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
                c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/intersection-of-two-arrays/submissions/">两个数组的交集</a>
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        // 用HashSet以空间换时间
        final HashSet<Integer> result = new HashSet<>();
        final HashSet<Integer> temp = new HashSet<>();
        if (nums1.length > nums2.length) {
            int[] num = nums2;
            nums2 = nums1;
            nums1 = num;
        }
        for (int i = 0; i < nums1.length; i++) {
            temp.add(nums1[i]);
        }
        for (int i = 0; i < nums2.length; i++) {
            int n = nums2[i];
            if (temp.contains(n)) {
                result.add(n);
            }
        }
        int[] ret = new int[result.size()];
        int index = 0;
        for (int num : result) {
            ret[index++] = num;
        }
        return ret;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/intersection-of-two-arrays-ii/submissions/">两个数组的交集 II</a>
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        // 方案一：省空间：先排序后遍历
        // 方案二：省时间：HashMap
        // 前面有类似的题用哈希表解过了，这里用方案一试试。
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        final List<Integer> list = new ArrayList<>();
        int p1 = 0;
        int p2 = 0;
        while (p1 < nums1.length && p2 < nums2.length) {
            if (nums1[p1] == nums2[p2]) {
                list.add(nums1[p1]);
                p1++;
                p2++;
            } else if (nums1[p1] < nums2[p2]) {
                p1++;
            } else if (nums1[p1] > nums2[p2]) {
                p2++;
            }
        }
        int[] ret = new int[list.size()];
        int index = 0;
        for (int num : list) {
            ret[index++] = num;
        }
        return ret;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/valid-perfect-square/">有效的完全平方数</a>
     */
    public boolean isPerfectSquare(int num) {
        // 所谓完全平方数，是指该数=某个整数的平方。
        // 该整数的范围是[1,n]，因此可以二分法进行定位。
        int min = 1;
        int max = num;
        while (min < max) {
            int middle = min + (max - min) / 2;
            if (middle == num / middle && num % middle == 0) {
                return true;
            }
            if (middle == min) {
                // 最终中位数会等于较小值
                break;
            }
            if (middle < num / middle) {
                min = middle;
            } else {
                max = middle;
            }
        }
        return num == 1;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/sum-of-two-integers/submissions/">两整数之和</a>
     */
    public int getSum(int a, int b) {
        // 遍历32次，每一个bit抠出来挨个处理。
        // int k = 0; // 记录上一个bit位处理产生的进位，为0或者1.
        // int result = 0;
        // for(int i=0;i<32;i++){
        //     int aBit = (a&(1<<i)) == 0 ? 0: 1;
        //     int bBit = (b&(1<<i)) == 0 ? 0: 1;
        //     // 这里用加号应该可以吧，如果不行那就if else穷举吧。
        //     int bitSum = aBit + bBit + k;
        //     if(bitSum==1 || bitSum==3){
        //         result |= 1<<i;
        //     }
        //     if(bitSum>=2){
        //         k = 1;
        //     } else {
        //         k = 0;
        //     }
        // }
        // return result;
        // 看了题解后，感觉网友的思路要更简洁巧妙一些。
        // 首先要知道以下结论：
        // 1、(a & b)<<1即为a+b时各个位的进位情况；
        // 2、(a ^ b)刚好就是不考虑进位时各个位的情况。
        // 因此 a + b可以变为 ((a & b)<<1) + (a ^ b)，即：不考虑进位+进位，
        // 分解后也是求和，因此可以重复分解，直至进位为0；

        // 这里借b存储进位值
        while (b != 0) {
            int temp = b;
            b = (a & b) << 1;
            a = (temp ^ a);
        }
        // a存储不考虑进位情况的值。
        return a;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/guess-number-higher-or-lower/submissions/">猜数字大小</a>
     */
    public int guessNumber(int n) {
        // 二分法吧
        int half;
        int start = 1;
        int end = n;
        while (true) {
            half = start + (end - start) / 2;
            int result = guess(half);
            if (result == 0) {
                return half;
            } else if (result == 1) {
                start = half + 1;
            } else {
                end = half - 1;
            }
        }
    }

    private int guess(int num) {
        return 0;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/ransom-note/submissions/">赎金信</a>
     */
    public boolean canConstruct(String ransomNote, String magazine) {
        // 本题的意思：
        // 1、ransom中出现的字符，magazine中也要出现，并且数量要足够；
        // 2、字符顺序可以不一致。
        // 用hashMap存储magazine中的字符以及字符个数即可。
        // 看了题解后，有两个其他思路可供参考：
        // 1、利用String的indexOf来判断ransomNote中的字符是否在magazine中也有，
        // 因为字符是可能重复的，因此用了一个caps数组来保存下一次index的起始位置。
        // 从结果来看此法确实最快的(1ms)，不过仔细想想就知道indexOf方法是比较耗时的，它内部的实现其实就是遍历。
        // 2、第二个解法是用数组保存magazine中每个字符出现的次数，然后遍历ransomNote，每次对应字符减1，如果为负数，则return false。
        // 解法2目前个人觉得是最靠谱的，虽然在内存消耗上和哈希表差不多，但是代码显然看起来更简洁。
        int[] countArray = new int['z' - 'a' + 1];
        for (int i = 0; i < magazine.length(); i++) {
            char c = magazine.charAt(i);
            countArray[c - 'a'] += 1;
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            char c = ransomNote.charAt(i);
            countArray[c - 'a'] -= 1;
            if (countArray[c - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/first-unique-character-in-a-string/">字符串中的第一个唯一字符</a>
     */
    public int firstUniqChar(String s) {
        // 用一个数组统计每个字符出现的次数即可。
        // 不过题意要返回字符的索引，所以需要做一些调整：可以开始让数组为0，
        // 如果第一次出现，则相应位置保存字符索引；如果第二次出现则相应位置置为-1，表示该字符不唯一。
        // 最后遍历数组，找出>0，且最小的索引即为答案。
        int[] countArray = new int['z' - 'a' + 1];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (countArray[c - 'a'] == 0) {
                countArray[c - 'a'] = i + 1; // 保存加1，和0区分开。
            } else {
                countArray[c - 'a'] = -1;
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < countArray.length; i++) {
            if (countArray[i] > 0) {
                min = Math.min(countArray[i], min);
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min - 1;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/find-the-difference/submissions/">找不同</a>
     */
    public char findTheDifference(String s, String t) {
        // 到目前为止做过蛮多这种统计字符个数的题了，这题也一样可以用一个数组来统计每个字符出现的次数。
        // 看完题解：发现此题有更巧妙的解法，那就是两两异或。嗯，记住了，消消乐就用异或。
        int n = s.length();
        int result = t.charAt(n);
        for (int i = 0; i < n; i++) {
            result ^= s.charAt(i);
            result ^= t.charAt(i);
        }
        return (char) result;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/is-subsequence/submissions/">判断子序列</a>
     */
    public boolean isSubsequence(String s, String t) {
        // 标签提到贪心算法，瞬间就有点思路了。
        // 我们可以遍历一遍t，每个字符和s的字符比较，相等则比较s的下一个字符。
        // 对于后续挑战：s有很多的情况下，对每一个s都遍历一次t显然不划算，因此可以统一在一趟遍历下完成所有s的判断，
        // 这个时候需要有个数组分别记录s已经匹配的index。
        // if("".equals(s) || s == null){
        //     return true;
        // }
        // int sIndex = 0;
        // for(int i =0;i<t.length();i++){
        //     if(t.charAt(i) == s.charAt(sIndex)){
        //         sIndex++; // 第一个字符找到了，开始匹配第二个字符
        //     }
        //     if(sIndex == s.length()){
        //         break;
        //     }
        // }
        // return sIndex == s.length();
        // 关于挑战，看了下题解，看来本人上面的思路是不行滴，其实也预感到了，10亿的数据量，搞成数组那内存要炸了吧？
        // 题解给的思路如下：先花时间对t字符串进行深度处理，使得能迅速判断s是否为t的子串(O(n)时间)。要想O(n)完成判断，也就是
        // 针对s只遍历一遍，那么就需要针对遍历的每个s的字符，都需要在O(1)时间内判断该字符是否在t中，并且要考虑顺序问题。由此，我们可以建立一个二维数组，
        // 纵轴是从'a'~'z'26个字母，横轴是[0,t.len)，举个例子，针对t="abcabc"，二维数组如下：
        //      0   1   2   3   4   5
        // 'a'  3   3   3  -1  -1  -1
        // 'b'  1   4   4   4  -1  -1
        // 'c'  2   2   5   5   5  -1
        // 让我们来解释下上面的矩阵的含义：
        // 首先考虑字符'a'，然后我们从后往前遍历，index=5处的字符为c，与a不想等，因此填-1，如此继续往前遍历，index=3时的字符为a，与a相等，
        // 因此修改后续的赋值为3，即上矩阵中0～2皆为3，当遍历到index=0时，赋值应当修改为0，不过因为前面已经没有字符了，因此0未出现在矩阵中。
        // 对于另外的字符b和c，皆有上述之算法进行填值。
        // 其实，不仅仅是index为0，每一次index的字符和a相等时，修改后的赋值总是要在下一次遍历才会体现出来，为此我们给t插入一个空字符就可以刚好解决这个问题，
        // 插入空字符后的矩阵情况如下：（注意此时横坐标index=0是空字符' '）
        //      0   1   2   3   4   5    6
        // 'a'  0   3   3   3  -1  -1   -1
        // 'b'  1   1   4   4   4  -1   -1
        // 'c'  2   2   2   5   5   5   -1
        // 仔细观察上述矩阵，你会发现矩阵中保存的值就是对应的字符在t串中下一次出现的index，如果为-1表示后续未出现了。
        // 那这样的话，判断每一个s串是否是t的子串就简单了，只需一趟遍历即可，具体往后看下代码就理解了。
        t = " " + t;
        int tLen = t.length();
        int[][] indexArray = new int[tLen]['z' - 'a' + 1];
        for (int i = 'a'; i <= 'z'; i++) {
            char c = (char) i;
            int nextPos = -1;
            // 每一个字符都要遍历t，可见是比较费时的，但是只要得到了处理后的矩阵之后，后续判断s就会很快了。
            for (int j = tLen - 1; j >= 0; j--) {
                indexArray[j][c - 'a'] = nextPos;
                if (t.charAt(j) == c) {
                    nextPos = j; // 修改赋值，下一次遍历生效。
                }
            }
        }
        // 矩阵预处理完毕，后面只需一趟遍历s串即可。
        int index = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // 看题解时这行代码一直百思不得其解，为什么上一个char的index可以作为下一个char的index呢？
            // 之所以不理解是因为把矩阵错误的想成是严格的保存了字符在t中出现的index，比如还是上例t="abcabc"来说，
            // 开始以为矩阵是这样的：
            //      0   1   2   3   4   5
            // 'a'  3  -1  -1  -1  -1  -1
            // 来解读下，首先index为0处保存了3，不为-1，因此index=0处有字符a，然后保存的值为3，即为下一个a的位置。
            // index为3时保存的是-1，表示没有下一个a了。
            // 如果是上面这样理解，那么下面这行代码就无论如何也无法理解了。
            // 后面才恍然大悟，原来遍历t时，不等于a字符的位置上保存的并非-1，而是上一次遍历出现a的index啊！！！
            // 所以这里不仅上一次遍历得到的index可以作为横坐标，index+1也可以的啊（当然前提是t中a字符不能连续）。
            index = indexArray[index][c - 'a'];
            if (index == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/binary-watch/">二进制手表</a>
     */
    public List<String> readBinaryWatch(int num) {
        // 这题貌似只能是穷举所有情况了，得到1的位置后，我们可以用移位的方式得到十进制值。
        // 后记：写代码时发现用代码不太好表达排列组合的思想，后面想到递归的方式解决。
        // 后记PS：注意led灯不能随便亮，因为小时的取值范围只有[0~11]，而4个bit的取值范围是[0,15]
        // 题解后记：递归法其实有个专有的名次叫回溯算法，百度百科这样说的：回溯算法实际上一个类似枚举的搜索尝试过程，
        // 主要是在搜索尝试过程中寻找问题的解，当发现已不满足求解条件时，就“回溯”返回，尝试别的路径。
        // 可以看到和自己写的递归代码思想上很像的，bitPosArray始终保存的是某一种可能的路径。
        // 后后记：后面优化了一下代码，用一个整型值取代之前的数组，不过没想到耗时和内存都没提升，尴尬了...
        int bitPosValue = 0; // 低10个bit保存led点亮情况。
        final List<String> resultList = new ArrayList<>();
        recursiveCollectBitPos(bitPosValue, 9, num, resultList);
        return resultList;
    }

    private void recursiveCollectBitPos(int bitPosValue, int offset, int num,
                                        List<String> resultList) {
        if (offset < num - 1) {
            // offset是当前正在处理的led等的偏移，最小是0，如果这个值比num小，
            // 那么说明剩余要分配的值比坑位多(注意要减1，因为offset可以和num相等)，说明当前路径是错误的，忽略之。
            return;
        }
        if (num == 0) {
            // 说明已完成一种可能的路径
            String str = bitPosToTimeString(bitPosValue);
            if (str != null) {
                resultList.add(0, str);
            }
            return;
        }
        // 分两个情况继续递归: 1、offset处分配；2、offset处不分配。
        recursiveCollectBitPos(setBit(bitPosValue, offset), offset - 1, num - 1, resultList);
        recursiveCollectBitPos(bitPosValue, offset - 1, num, resultList);
    }

    private int setBit(int bitValue, int pos) {
        return bitValue | (1 << pos);
    }

    private String bitPosToTimeString(int bitPosValue) {
        int hour = 0x0f & (bitPosValue >> 6);
        if (hour > 11) {
            return null;
        }
        int minute = 0x3f & bitPosValue;
        if (minute > 59) {
            return null;
        }
        if (minute < 10) {
            return hour + ":0" + minute;
        } else {
            return hour + ":" + minute;
        }
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/sum-of-left-leaves/">左叶子之和</a>
     */
    public int sumOfLeftLeaves(TreeNode root) {
        // 深度优先周游一下二叉树，广度优先的话不好判断当前节点是否是父节点的左节点。
        // 看完题解后记：其实层序遍历也可以实现的，就是在遍历的时候判断左节点是否是叶子节点就行，是就加到sum。
        int sum = 0;
        final Stack<TreeNode> stack = new Stack<>();
        TreeNode curNode = root;
        while (curNode != null || !stack.isEmpty()) {
            // 一直向左，逐个入栈
            while (curNode != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }
            // pop一个出来，取之右节点，赋值给curNode，开始下一轮深度遍历。
            if (!stack.isEmpty()) {
                TreeNode pop = stack.pop();
                TreeNode nextPop = null;
                if (!stack.isEmpty()) {
                    nextPop = stack.peek();
                }
                // System.out.println(""+pop.val+",");
                curNode = pop.right;
                if (pop.left == null && pop.right == null &&
                        nextPop != null && nextPop.left == pop) {
                    sum += pop.val;
                }
            }
        }
        return sum;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/convert-a-number-to-hexadecimal/submissions/">数字转换为十六进制数</a>
     */
    public String toHex(int num) {
        // 基础知识：计算机存储负数时是绝对值取反加1
        if (num >= 0) {
            return toHexString(num);
        } else {
            return toHexString(~Math.abs(num) + 1);
        }
    }

    private String toHexString(int value) {
        final StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (int i = 7; i >= 0; i--) {
            int low4Bit = (value >>> (i * 4)) & 0x0f;
            System.out.println("4 bit = " + low4Bit);
            if (low4Bit == 0 && first) {
                continue;
            }
            first = false;
            if (low4Bit < 10) {
                builder.append((char) (low4Bit + '0'));
            } else {
                builder.append((char) (low4Bit - 10 + 'a'));
            }
        }
        String result = builder.toString();
        if ("".equals(result)) {
            result = "0";
        }
        return result;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/longest-palindrome/">最长回文串</a>
     */
    public int longestPalindrome(String s) {
        // 事实上这个题貌似又是一个消消乐的问题
        // 后记：因为有大小写字母，所以不能用一个数组保存count。
        int[] countArray = new int['z' - 'a' + 1];
        int[] countArray2 = new int['Z' - 'A' + 1];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'a' && c <= 'z') {
                countArray[c - 'a']++;
                if (countArray[c - 'a'] == 2) {
                    countArray[c - 'a'] = 0;
                }
            } else {
                countArray2[c - 'A']++;
                if (countArray2[c - 'A'] == 2) {
                    countArray2[c - 'A'] = 0;
                }
            }
        }
        int remainCount = 0;
        for (int count : countArray) {
            remainCount += count;
        }
        for (int count : countArray2) {
            remainCount += count;
        }
        if (remainCount > 0) {
            // 如果有剩余，那么其中一个字符可以放到中间
            remainCount--;
        }
        return s.length() - remainCount;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/fizz-buzz/">Fizz Buzz</a>
     */
    public List<String> fizzBuzz(int n) {
        // 对每个数字取余，这貌似没啥考点啊。。。maybe取余比较耗时？
        // 题解后记：这题的考点应该在于进阶问题，即问题升级为：
        // 3 ---> "Fizz" , 5 ---> "Buzz", 7 ---> "Jazz"
        // 可见，随着要对应的越多，if else会成倍增加，要优雅解决的话，
        // 可以用追加法。即：能被3整除，字符串初始化为"Fizz"，能被5整除，则字符串追加"Buzz"，依此类推。
        int lastFizzIndex = 0;
        int lastBuzzIndex = 0;
        final List<String> resultList = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (i - lastFizzIndex == 3 &&
                    i - lastBuzzIndex == 5) {
                resultList.add("FizzBuzz");
                lastFizzIndex = i;
                lastBuzzIndex = i;
            } else if (i - lastFizzIndex == 3) {
                resultList.add("Fizz");
                lastFizzIndex = i;
            } else if (i - lastBuzzIndex == 5) {
                resultList.add("Buzz");
                lastBuzzIndex = i;
            } else {
                resultList.add(i + "");
            }
        }
        return resultList;
    }
}
