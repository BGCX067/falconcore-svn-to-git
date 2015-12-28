package org.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;


public class TestCases {
    HashMap<String, Testcase> map = new HashMap<String, Testcase>();
    HashMap<String, String> paths = new HashMap<String, String>();


    private void test(TriType triType) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("D:\\academics\\fall2009\\SoftwareAnalysis\\project\\Thread_visualization\\src\\org\\test\\newtest.txt"));
        String str;
        int idex = 0;
        while ((str = in.readLine()) != null) {
            String[] values = str.split("\\ ");
            triType.test(values, "" + idex, this);
            System.out.println("Test" +idex + ":      " + str);
            idex++;
        }
        /**
         * Int.MAX Int.MAX Int.MAX
         Int.MAX 1 Int.MAX
         1 Int.MAX Int.MAX
         Int.MAX Int.MAX 1
         Int.MAX 1 1
         1 Int.MAX 1
         1 Int.MAX
         */
    }

    public static void main(String[] args) throws Exception {
        TestCases tt = new TestCases();
        TriType triType = new TriType();
        tt.test(triType);
        tt.print();
    }

    public TestCases() {
    }

    public void add(String testcase, String stmt) {
        Testcase test = map.get(stmt);
        if (test == null) {
            test = new Testcase(stmt);
        }
        test.addTestCase(testcase);
        map.put(stmt, test);

        String path = paths.get(testcase);
        if (path == null) {
            path = "";
        }
        path = path + stmt + ",";
        paths.put(testcase, path);

    }

    public void print() {
        Iterator itr = map.values().iterator();
        while (itr.hasNext()) {
            Testcase testCas = (Testcase) itr.next();
            testCas.print();
        }
        HashMap<String, String> basisPath = new HashMap<String, String>();
        basisPath.put("st1,st2,st15,", "");
        basisPath.put("st1,st3,st9,st14,st15,", "");
        basisPath.put("st1,st3,st4,st9,st14,st15,", "");
        basisPath.put("st1,st3,st5,st9,st14,st15,", "");
        basisPath.put("st1,st3,st6,st9,st14,st15,", "");
        basisPath.put("st1,st3,st8,st15,", "");
        basisPath.put("st1,st3,st7,st15,", "");
        basisPath.put("st1,st3,st9,st13,st15,", "");
        basisPath.put("st1,st3,st9,st12,st15,", "");
        basisPath.put("st1,st3,st9,st11,st15,", "");
        basisPath.put("st1,st3,st9,st10,st15,", "");
        Iterator keys = paths.keySet().iterator();
        System.out.println("===================================");
        while (keys.hasNext()) {
            String key = (String) keys.next();

            String s = paths.get(key);
            String val = basisPath.get(s);
            if (val != null) {
                val = val + key + ",";
                basisPath.put(s, val);
            }
//            System.out.println(key + ":" + s);
        }
        System.out.println("====================================");
        Iterator avls = basisPath.keySet().iterator();
        while (avls.hasNext()) {
            String s = (String) avls.next();
            System.out.println(s + ":" + basisPath.get(s));
        }
    }
}
