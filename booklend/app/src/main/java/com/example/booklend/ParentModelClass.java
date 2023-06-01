package com.example.booklend;

import java.util.ArrayList;

public class ParentModelClass {
    String title;
    ArrayList<ChildModelClass> childModelClassesList;

    public ParentModelClass(String title, ArrayList<ChildModelClass> childModelClassesList) {
        this.title = title;
        this.childModelClassesList = childModelClassesList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ChildModelClass> getChildModelClassesList() {
        return childModelClassesList;
    }

    public void setChildModelClassesList(ArrayList<ChildModelClass> childModelClassesList) {
        this.childModelClassesList = childModelClassesList;
    }
}
