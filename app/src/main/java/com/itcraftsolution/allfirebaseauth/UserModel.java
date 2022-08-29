package com.itcraftsolution.allfirebaseauth;

public class UserModel {
    String name, graduation , semester;

    public UserModel(String name, String graduation, String semester) {
        this.name = name;
        this.graduation = graduation;
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGraduation() {
        return graduation;
    }

    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
