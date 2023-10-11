package dao;

import student.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements IStudentDAO {
    private String URL = "jdbc:mysql://localhost:3306/student";
    private String userName = "root";
    private String password = "1234";

    public static final String SHOW_ALL_STUDENT = "SELECT * FROM sinhvien";
    public static final String ADD_NEW_STUDENT = "INSERT INTO sinhvien" + "(name, age, address, email) VALUE" + "(?,?,?,?)";
    public static final String SELECT_STUDENT = "SELECT * FROM students WHERE id = ?";
    public static final String DELETE_STUDENT = "DELETE FROM sinhvien WHERE id = ?";
    public static final String UPDATE_STUDENT = "UPDATE sinhvien set name = ?, age = ?, address = ?, email = ? WHERE id = ?";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }


    @Override
    public List<Student> showAllStudent() {
        List<Student> students= new ArrayList<>();
        try (Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SHOW_ALL_STUDENT);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String address= resultSet.getString("address");
                String email = resultSet.getString("email");
                students.add(new Student(id, name, age, address, email));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    @Override
    public void addNewStudent(Student student) {
        try (Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_STUDENT);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());
            preparedStatement.setString(3, student.getAddress());
            preparedStatement.setString(4, student.getEmail());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student selectStudent(int id) {
        Student student = null;
        try (Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT);
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String address= rs.getString("address");
                String email = rs.getString("email");
                student = new Student(id, name, age, address, email);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }

    @Override
    public boolean deleteStudent(int id) {
        boolean rowDelete;
        try (Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT);
            preparedStatement.setInt(1, id);
            rowDelete = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowDelete;
    }

    @Override
    public boolean updateStudent(Student student) {
        boolean updateRow;
        try (Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STUDENT);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());
            preparedStatement.setString(3, student.getAddress());
            preparedStatement.setString(4, student.getEmail());
            preparedStatement.setInt(5, student.getId());

            updateRow = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updateRow;
    }
}
