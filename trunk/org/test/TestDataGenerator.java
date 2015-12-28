package org.test;

import java.util.Random;


public class TestDataGenerator {

    private void generateTestCases() {
        String testName;
        for (int i = 0; i < 15; i++) {
            int a = new Random().nextInt();
            int b = new Random().nextInt();
            int c = new Random().nextInt();

            if ((a > 0) && (b > 0) && (c > 0) && (a + b > c) && (a + c > b) && (b + c > a)) { // checking whether the input is valid
                if ((a == b) && (b == c)) {  // checking whether the input forms a equilateral
                    testName = "equilateral";
                } else if ((a == b) || (a == c) || (b == c)) {// checking whether it is isosceles
                    testName = "isosceles";
                } else {
                    testName = "scalene";
                }
            } else {
                testName = "undefiend";
            }
            System.out.println(testName + " " + a + " " + b + " " + c + " " + testName);
        }
    }

    public static void main(String[] args) {
        new TestDataGenerator().generateTestCases();
    }
}
