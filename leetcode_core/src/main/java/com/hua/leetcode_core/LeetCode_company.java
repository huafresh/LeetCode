package com.hua.leetcode_core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

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
        if (p1 == null || p2 == null) return null;
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
        // 更容易想到，如果记不起jdk的算法，倒是可以一试。
        n = n - ((n >> 1) & 0x55555555);
        n = (n & 0x33333333) + ((n >> 2) & 0x33333333);
        n = (n + (n >> 4)) & 0x0f0f0f0f;
        n = n + (n >> 8);
        n = n + (n >> 16);
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
        // 就是没有被猜中的数字的总和，而被猜的数字一共有secret.length()个，那么很显然secret.length() - nonZeroCount - aCount就是被猜中的数字个数。
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
}
