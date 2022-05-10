package com.hua.leetcode_core;

import java.util.Stack;

//
// Created by clydeazhang on 2022/5/10 3:47 下午.
// Copyright (c) 2022 Tencent. All rights reserved.
//

/**
 * 中缀表达式转后缀表达式
 */
public class SuffixHelper {

    /**
     * 中缀表达式: 4*2*(2*3+5)-6
     * 后缀表达式: 4 2 * 2 3 * 5 + * 6 -
     * <p>
     * 中缀表达式转后缀表达式算法：
     * A、从左到右遍历中缀表达式的每个字符，若是操作数则输出，即成为后缀表达式的一部分；
     * B、若是字符，则细分以下情况：
     * 如果字符是左括号，则入栈；
     * 如果字符是右括号，则栈中的字符依次出栈，直到弹出左括号为止，期间弹出的运算符则输出；
     * 如果字符是运算符，则继续细分以下情况：
     * 如果当前栈为空，运算符入栈；
     * 如果运算符优先级大于栈顶字符（如遇栈顶为左括号则认为比任意运算符优先级都低），则入栈；
     * 如果运算符优先级小于等于栈顶字符，则持续弹出栈顶字符，并输出，直到当前运算符优先级高于栈顶字符，此时运算符入栈。
     */
    public static String infixToSuffix(String infix) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            if (isOperator(c)) {
                if (c == '(') {
                    stack.push(c);
                } else if (c == ')') {
                    while (stack.peek() != '(') {
                        result.append(stack.pop()).append(" ");
                    }
                    stack.pop();
                } else {
                    while (!stack.isEmpty() && priority(c) <= priority(stack.peek())) {
                        char pop = stack.pop();
                        if (pop != '(') {
                            result.append(pop).append(" ");
                        }
                    }
                    stack.push(c);
                }
            } else {
                result.append(c).append(" ");
            }
        }
        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(" ");
        }
        return result.toString();
    }

    private static int priority(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 0;
            case '*':
            case '/':
                return 1;
            case '(':
                return -1;
        }
        throw new IllegalArgumentException("invalid operator");
    }

    private static boolean isOperator(char c) {
        switch (c) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '(':
            case ')':
                return true;
        }
        return false;
    }
}