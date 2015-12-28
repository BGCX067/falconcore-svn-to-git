package org.test;

import java.util.ArrayList;

public class Testcase {
    private ArrayList<String> values = new ArrayList<String>();
    private String lineNumber;

    public Testcase(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void addTestCase(String testcase){
       values.add(testcase);
    }

    public void print() {
        String str = "";
        for (int i = 0; i < values.size(); i++) {
            String s = values.get(i);
            str = str + s + ",";
        }
        System.out.println(lineNumber + ":" + str);
    }
}
