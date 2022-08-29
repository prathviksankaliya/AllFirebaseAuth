package com.itcraftsolution.allfirebaseauth.Model;

import android.net.Uri;

public class UserModel {
    String name, graduation , semester, pUri;

    public UserModel() {
    }

    public UserModel(String name, String graduation, String semester, String pUri) {
        this.name = name;
        this.graduation = graduation;
        this.semester = semester;
        this.pUri = pUri;
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

    public String getpUri() {
        return pUri;
    }

    public void setpUri(String pUri) {
        this.pUri = pUri;
    }
}
