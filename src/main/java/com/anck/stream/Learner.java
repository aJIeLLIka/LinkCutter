package com.anck.stream;

import java.util.ArrayList;
import java.util.List;

public class Learner implements Student {
    private final String name;

    private List<Course> courseList = new ArrayList();

    public Learner(String name) {
        this.name = name;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseList;
    }

    public void addCorese(Course course) {
        courseList.add(course);
    }

}
