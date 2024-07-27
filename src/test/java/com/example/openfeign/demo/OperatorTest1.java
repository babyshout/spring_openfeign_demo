package com.example.openfeign.demo;

import org.junit.jupiter.api.Test;

public class OperatorTest1 {
    @Test
    void printAAndB(int a, int b) {
        System.out.println("a : " + a);
        System.out.println("b : " + b);
    }

    @Test
    void operatorTest1() {
        int a = 3;
        int b = 0;
        printAAndB(a, b);


        b = (++a) + (a++);
        printAAndB(a, b);

        a = 3;
        b = (a++) + 3;

        printAAndB(a, b);

    }
}
