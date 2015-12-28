package org.gatech.gui;

import falcon.data.classes.InstVariable;


public class Validationbean {
    private String testName;
    private int pass;
    private int fail;
    private int susp;
    private InstVariable var1;
    private InstVariable var2;
    private InstVariable var3;
    private String stack1;
    private String stack2;
    private String stack3;

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public int getSusp() {
        return susp;
    }

    public void setSusp(int susp) {
        this.susp = susp;
    }

    public InstVariable getVar1() {
        return var1;
    }

    public void setVar1(InstVariable var1) {
        this.var1 = var1;
    }

    public InstVariable getVar2() {
        return var2;
    }

    public void setVar2(InstVariable var2) {
        this.var2 = var2;
    }

    public String getStack1() {
        return stack1;
    }

    public void setStack1(String stack1) {
        this.stack1 = stack1;
    }

    public String getStack2() {
        return stack2;
    }

    public void setStack2(String stack2) {
        this.stack2 = stack2;
    }

    public InstVariable getVar3() {
        return var3;
    }

    public void setVar3(InstVariable var3) {
        this.var3 = var3;
    }

    public String getStack3() {
        return stack3;
    }

    public void setStack3(String stack3) {
        this.stack3 = stack3;
    }
}
