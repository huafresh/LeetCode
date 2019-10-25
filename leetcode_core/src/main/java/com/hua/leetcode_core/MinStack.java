package com.hua.leetcode_core;

import java.util.Stack;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2019-10-25 10:54
 * <p>
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
public class MinStack {

    // 难点在于pop后如何取出次最小值，为了节省空间又能常数时间获取最小元素，
    // 可以用数组由小到大存储元素，建立链表表达栈的逻辑结构。

    // 后记：题解思路是：用一个辅助栈存储当前的最小值，不管是push和pop都很快，缺点就是占用一倍空间。
    // 不过虽然看起多占用一倍，但是上面的解法增加了Element，最终结果来看，反而更耗内存，不过这里只是简单的整形数据，
    // 实际应用就是另一回事了。

    private Stack<Integer> data = new Stack<>();
    private Stack<Integer> helper = new Stack<>();

    /**
     * initialize your data structure here.
     */
    public MinStack() {

    }

    public void push(int x) {
        data.push(x);
        if (helper.isEmpty() || helper.peek() > x) {
            helper.push(x);
        } else {
            // 当前栈顶依旧是最小值，再push一次，后面取最小值时只需pop就行
            helper.push(helper.peek());
        }
    }

    public void pop() {
        data.pop();
        helper.pop();
    }

    public int top() {
        return data.isEmpty() ? -1 : data.peek();
    }

    public int getMin() {
        return helper.isEmpty() ? -1 : helper.peek();
    }


//     private ArrayList<Element> elements = new ArrayList<>();
//     private Element head;

//     /** initialize your data structure here. */
//     public MinStack() {

//     }

//     public void push(int x) {
//         int index = -1;
//         for(int i=0;i<elements.size();i++){
//             Element element = elements.get(i);
//             if(x < element.value){
//                 index = i;
//                 break;
//             }
//         }
//         Element e = new Element(x);
//         if(index != -1){
//             elements.add(index, e);
//         } else {
//             elements.add(e);
//         }
//         if(head == null){
//             head = e;
//         } else {
//             e.next = head;
//             head = e;
//         }
//     }

//     public void pop() {
//         if(head != null){
//             elements.remove(head);
//             head = head.next;
//         }
//     }

//     public int top() {
//         if(head != null){
//             return head.value;
//         }
//         return -1;
//     }

//     public int getMin() {
//         if(elements.size()>0){
//             return elements.get(0).value;
//         }
//         return -1;
//     }

//     private static class Element{
//         private int value;
//         private Element next;

//         private Element(int value){
//             this.value = value;
//         }
//     }
}


