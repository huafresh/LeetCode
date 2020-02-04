package com.hua.leetcode_core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
}
