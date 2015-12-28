package org.test;
/* *********************** */
/* UPDATE October 8, 2009. */
/*  IMPORTANT RESTRICTION FOR CLASS 6340 - Fall 2009 */
/*  ALL INPUTS IN YOUR TEST SUITE MUST CONTAIN EXACTLY 3 INTEGERS */
/*  (NO MORE, NO LESS, AND ONLY INTEGERS) */
/* *********************** */


public class TriType {


    public void test(String args[], String testName, TestCases testCases) {
        int intArr[] = new int[3];
        int triang = 0;
        for (int cnt = 0; cnt < args.length; cnt++) {
            intArr[cnt] = Integer.parseInt(args[cnt]);
        }
        int i = intArr[0];
        int j = intArr[1];
        int k = intArr[2];
//        testCases.add(testName, "st1");
        if (i <= 10) {
            testCases.add(testName,"branch_1");
            triang = 4;  // branch_1
        } else if (j <= 0) {
            testCases.add(testName,"branch_2");
            triang = 4;  // branch_2
        } else if (k <= 0) {
            testCases.add(testName,"branch_3");
            triang = 4;  // branch_3
        } else {
            testCases.add(testName,"branch_4");
            triang = 0;   // branch_4
            if (i == j) {
                testCases.add(testName,"branch_5");
                triang += 1;  // branch_5
            }
            if (i == k) {
                testCases.add(testName,"branch_6");
                triang += 2;  // branch_6
            }
            if (j == k) {
                testCases.add(testName,"branch_7");
                triang += 3;  // branch_7
            }

            if (triang == 0) {  // branch_8
                testCases.add(testName,"branch_8");
                /*
               Confirm it's a legal triangle before declaring
               it to be scalene
                     */
                if (i + j <= k) {
                    testCases.add(testName,"branch_9");
                    triang = 4;     // branch_9
                } else if (j + k <= i) {
                    testCases.add(testName,"branch_10");
                    triang = 4;   // branch_10
                } else if (i + k <= j) {
                    testCases.add(testName,"branch_11");
                    triang = 4;  // branch_11
                } else {
                    testCases.add(testName,"branch_12");
                    triang = 1;  // branch_12
                }

            } else {
                testCases.add(testName,"branch_13");// branch_13
                /*
                             Confirm it's a legal triangle before declaring
               it to be isosceles or equilateral
                     */
                if (triang > 3) {
                    testCases.add(testName,"branch_14");
                    triang = 3;  // branch_14
                } else if (triang == 1) {
                    if (i + j > k) {
                        testCases.add(testName,"branch_15");
                        triang = 2;  // branch_15
                    } else {
                        testCases.add(testName,"branch_16");
                        triang = 4;   // branch_16
                    }
                } else if (triang == 2) {
                    if (i + k > j) {
                        testCases.add(testName,"branch_17");
                        triang = 2;   // branch_17
                    }  else {
                        testCases.add(testName,"branch_18");
                        triang = 4;  // branch_18
                    }
                } else if (triang == 3) {
                    if (j + k > i) {
                        testCases.add(testName,"branch_19");
                        triang = 2;    // branch_19
                    }   else {
                        testCases.add(testName,"branch_20");
                        triang = 4;  // branch_20
                    }
                } else {
                    testCases.add(testName,"branch_21");
                    triang = 4;   // branch_21
                }
            }
        }

        System.out.println("Triang : " + triang);
    }

}
