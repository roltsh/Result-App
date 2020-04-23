package com.example.revalform;

import java.util.List;
import java.util.Map;

public class dataSample {
    private String usn;
    private String name;

    private List<Map<String , Object>> subjects;

    public dataSample() {
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map<String, Object>> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Map<String, Object>> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "dataSample{" +
                "usn='" + usn + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
