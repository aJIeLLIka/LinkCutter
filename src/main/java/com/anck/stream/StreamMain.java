package com.anck.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamMain {

    static List<Course> getUniqCourses(List<Student> studentList) {
        return studentList.stream()
                .map(Student::getAllCourses)
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    static List<Student> getMostCuriousStudents(List<Student> studentList) {
        return studentList.stream()
//                .sorted(Comparator.comparingInt(s -> s.getAllCourses().size()).reversed())
                .sorted(Collections.reverseOrder(Comparator.comparing(s -> s.getAllCourses().size())))
                .limit(3)
                .collect(Collectors.toList());

    }

    static List<Student> getStudentsOfCourse(List<Student> learnerList, Course course) {
        return learnerList.stream()
                .filter(s -> s.getAllCourses().contains(course))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Course course1 = () -> "выш мат";
        Course course2 = () -> "геометрия";
        Course course3 = () -> "физика";
        Course course4 = () -> "ООП";
        Course course5 = () -> "сист анализ";
        List<Course> allCoursesList = new ArrayList(List.of(course1, course2,
                course3, course4, course5));

        List<Student> allStudentsList = createStudents(allCoursesList);

        List<Course> uniqCourses = getUniqCourses(allStudentsList);
        System.out.print("\nУникальные курсы: ");
        uniqCourses.forEach(s -> System.out.print(s.getSubject() + ", "));

        List<Student> MostCuriousStudentsList = getMostCuriousStudents(allStudentsList);
        System.out.print("\n\n3 самых любознательных студента: ");
        MostCuriousStudentsList.forEach(s -> System.out.print(s.getName() + " " + s.getAllCourses().size() + "курсов, "));

        List<Student> studentsOfCourse = getStudentsOfCourse(allStudentsList, allCoursesList.get(3));
        System.out.print("\n\nсписок студентов курса " + course4.getSubject() + ": ");
        studentsOfCourse.forEach(s -> System.out.print(s.getName() + ", "));

    }

    static List<Student> createStudents(List<Course> allCoursesList) {//инициализация студентов для тестирования
        List<Student> allStudentsList = new ArrayList();
        Student student1 = new Student() {
            @Override
            public String getName() {
                return "Максим";
            }

            @Override
            public List<Course> getAllCourses() {
                return List.of(allCoursesList.get(0), allCoursesList.get(1));
            }
        };
        allStudentsList.add(student1);

        Student student2 = new Student() {
            @Override
            public String getName() {
                return "Андрей";
            }

            @Override
            public List<Course> getAllCourses() {
                return List.of(allCoursesList.get(1), allCoursesList.get(2));
            }
        };
        allStudentsList.add(student2);

        Student student3 = new Student() {
            @Override
            public String getName() {
                return "Антон";
            }

            @Override
            public List<Course> getAllCourses() {
                return List.of(allCoursesList.get(2), allCoursesList.get(3), allCoursesList.get(4));
            }
        };
        allStudentsList.add(student3);

        Student student4 = new Student() {
            @Override
            public String getName() {
                return "Михаил";
            }

            @Override
            public List<Course> getAllCourses() {
                return List.of(allCoursesList.get(0), allCoursesList.get(1),
                        allCoursesList.get(4), allCoursesList.get(2), allCoursesList.get(3));
            }
        };
        allStudentsList.add(student4);

        Student student5 = new Student() {
            @Override
            public String getName() {
                return "Валерий";
            }

            @Override
            public List<Course> getAllCourses() {
                return List.of(allCoursesList.get(0), allCoursesList.get(1),
                        allCoursesList.get(4), allCoursesList.get(2));
            }
        };
        allStudentsList.add(student5);

        for (Student s : allStudentsList) {
            System.out.print(s.getName() + ", курсы: ");
            for (Course c : s.getAllCourses()) {
                System.out.print(c.getSubject() + ", ");
            }
            System.out.println();
        }

        return allStudentsList;
    }

}
