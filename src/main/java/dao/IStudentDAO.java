package dao;

import student.Student;

import java.util.List;

public interface IStudentDAO {
    List<Student> showAllStudent();
    void addNewStudent (Student student);
    Student selectStudent (int id);
    boolean deleteStudent (int id);
    boolean updateStudent(Student student);
}
