package org.gatech.gui;

import java.util.ArrayList;
import java.util.HashMap;

public class ProjectInfoBean {
    private String instFileName;
    private String extFileName;
    private String projectName;
    private HashMap<String, String> sourceList;

    public ProjectInfoBean() {
        sourceList = new HashMap<String, String>();
    }

    public String getInstFileName() {
        return instFileName;
    }

    public void setInstFileName(String instFileName) {
        this.instFileName = instFileName;
    }

    public String getExtFileName() {
        return extFileName;
    }

    public void setExtFileName(String extFileName) {
        this.extFileName = extFileName;
    }

    public HashMap<String, String> getSourceList() {
        return sourceList;
    }

    public void addToList(String sourceFileName, String fileName) {
        sourceList.put(sourceFileName, fileName);
    }

    public String getFileName(String name) {
        return sourceList.get(name);
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
